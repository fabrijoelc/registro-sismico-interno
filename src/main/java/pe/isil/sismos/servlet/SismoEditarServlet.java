package pe.isil.sismos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.isil.sismos.model.EstadoSismo;
import pe.isil.sismos.model.Sismo;
import java.io.IOException;

@WebServlet("/sismos/editar")
public class SismoEditarServlet extends ServletUtil {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = leerId(request);
        Sismo actual = id == null ? null : repositorio().buscarActivo(id).orElse(null);
        if (actual == null) { noEncontrado(response); return; }
        mostrar(request, response, id, SismoForm.desde(actual));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Long id = leerId(request);
        if (id == null || repositorio().buscarActivo(id).isEmpty()) { noEncontrado(response); return; }
        SismoForm form = SismoForm.validar(request, repositorio(), id);
        if (!form.isValido()) { response.setStatus(400); mostrar(request, response, id, form); return; }
        try {
            if (!repositorio().actualizar(id, form.getSismo())) { noEncontrado(response); return; }
            response.sendRedirect(request.getContextPath() + "/sismos/ver?id=" + id + "&editado=1");
        } catch (IllegalArgumentException ex) {
            form.getErrores().put("codigo", ex.getMessage());
            response.setStatus(400);
            mostrar(request, response, id, form);
        }
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response, long id, SismoForm form) throws ServletException, IOException {
        request.setAttribute("form", form);
        request.setAttribute("id", id);
        request.setAttribute("titulo", "Editar reporte sísmico");
        request.setAttribute("accion", "editar");
        request.setAttribute("estados", EstadoSismo.values());
        vista(request, response, "formulario");
    }
}
