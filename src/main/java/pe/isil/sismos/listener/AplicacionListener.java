package pe.isil.sismos.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pe.isil.sismos.model.EstadoSismo;
import pe.isil.sismos.model.Sismo;
import pe.isil.sismos.repository.SismoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebListener
public class AplicacionListener implements ServletContextListener {
    public static final String REPOSITORIO = "sismoRepository";

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        SismoRepository repositorio = new SismoRepository();
        repositorio.crear(new Sismo(0, "SIS-2026-001", LocalDateTime.of(2026, 9, 18, 9, 20),
                "Lima, Lima", new BigDecimal("4.2"), new BigDecimal("38.0"),
                EstadoSismo.PENDIENTE, "Reporte inicial de prueba", true));
        repositorio.crear(new Sismo(0, "SIS-2026-002", LocalDateTime.of(2026, 9, 19, 14, 5),
                "Arequipa, Camaná", new BigDecimal("5.1"), new BigDecimal("62.5"),
                EstadoSismo.EN_VERIFICACION, "Ubicación pendiente de revisión", true));
        repositorio.crear(new Sismo(0, "SIS-2026-003", LocalDateTime.of(2026, 9, 20, 18, 40),
                "Piura, Paita", new BigDecimal("3.7"), new BigDecimal("22.0"),
                EstadoSismo.VERIFICADO, "Dato de prueba revisado", true));
        evento.getServletContext().setAttribute(REPOSITORIO, repositorio);
    }
}
