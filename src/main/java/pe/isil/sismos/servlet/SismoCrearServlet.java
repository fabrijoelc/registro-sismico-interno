package pe.isil.sismos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.isil.sismos.model.EstadoSismo;
import java.io.IOException;

@WebServlet("/sismos/nuevo")
public class SismoCrearServlet extends ServletUtil {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrar(request, response, SismoForm.vacio());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        SismoForm form = SismoForm.validar(request, repositorio(), null);
        if (!form.isValido()) { response.setStatus(400); mostrar(request, response, form); return; }
        try {
            long id = repositorio().crear(form.getSismo()).getId();
            response.sendRedirect(request.getContextPath() + "/sismos/ver?id=" + id + "&creado=1");
        } catch (IllegalArgumentException ex) {
            form.getErrores().put("codigo", ex.getMessage());
            response.setStatus(400);
            mostrar(request, response, form);
        }
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response, SismoForm form) throws ServletException, IOException {
        request.setAttribute("form", form);
        request.setAttribute("titulo", "Nuevo reporte sísmico");
        request.setAttribute("accion", "nuevo");
        request.setAttribute("estados", EstadoSismo.values());
        vista(request, response, "formulario");
    }
}
