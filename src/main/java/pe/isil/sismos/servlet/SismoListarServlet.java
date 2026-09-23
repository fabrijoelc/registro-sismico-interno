package pe.isil.sismos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.isil.sismos.model.EstadoSismo;
import java.io.IOException;

@WebServlet("/sismos")
public class SismoListarServlet extends ServletUtil {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busqueda = request.getParameter("q");
        String estadoTexto = request.getParameter("estado");
        EstadoSismo estado = null;
        if (estadoTexto != null && !estadoTexto.isBlank()) {
            try { estado = EstadoSismo.valueOf(estadoTexto); }
            catch (IllegalArgumentException ex) { response.sendError(400, "Estado inválido."); return; }
        }
        request.setAttribute("sismos", repositorio().listar(busqueda, estado));
        request.setAttribute("q", busqueda == null ? "" : busqueda.trim());
        request.setAttribute("estadoSeleccionado", estadoTexto == null ? "" : estadoTexto);
        request.setAttribute("estados", EstadoSismo.values());
        vista(request, response, "lista");
    }
}
