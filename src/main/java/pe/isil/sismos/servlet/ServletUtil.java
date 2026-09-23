package pe.isil.sismos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.isil.sismos.listener.AplicacionListener;
import pe.isil.sismos.repository.SismoRepository;

import java.io.IOException;

abstract class ServletUtil extends HttpServlet {
    protected SismoRepository repositorio() {
        return (SismoRepository) getServletContext().getAttribute(AplicacionListener.REPOSITORIO);
    }

    protected Long leerId(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            return id > 0 ? id : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    protected void vista(HttpServletRequest request, HttpServletResponse response, String nombre)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/sismos/" + nombre + ".jsp").forward(request, response);
    }

    protected void noEncontrado(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "El reporte no existe o ya fue dado de baja.");
    }
}
