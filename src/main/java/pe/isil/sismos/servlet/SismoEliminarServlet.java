package pe.isil.sismos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.isil.sismos.model.Sismo;
import java.io.IOException;

@WebServlet("/sismos/eliminar")
public class SismoEliminarServlet extends ServletUtil {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = leerId(request);
        Sismo sismo = id == null ? null : repositorio().buscarActivo(id).orElse(null);
        if (sismo == null) { noEncontrado(response); return; }
        request.setAttribute("sismo", sismo);
        vista(request, response, "confirmar-eliminacion");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = leerId(request);
        if (id == null || !repositorio().eliminarLogicamente(id)) { noEncontrado(response); return; }
        response.sendRedirect(request.getContextPath() + "/sismos?eliminado=1");
    }
}
