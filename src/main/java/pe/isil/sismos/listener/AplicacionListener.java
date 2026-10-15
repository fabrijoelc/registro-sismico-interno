package pe.isil.sismos.listener;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import pe.isil.sismos.repository.*;
import java.util.Map;
@WebListener
public class AplicacionListener implements ServletContextListener {
    public static final String REPOSITORIO="sismoRepository";
    public static final String UBICACIONES="ubicacionRepository";
    private EntityManagerFactory fabrica;
    private String variable(String nombre,String defecto) {
        String valor=System.getenv(nombre); return valor==null || valor.isBlank()?defecto:valor;
    }
    @Override public void contextInitialized(ServletContextEvent evento) {
        String clave=System.getenv("DB_PASSWORD");
        if(clave==null || clave.isBlank()) throw new IllegalStateException("Configura DB_PASSWORD antes de iniciar Tomcat.");
        fabrica=Persistence.createEntityManagerFactory("sismos",Map.of(
            "jakarta.persistence.jdbc.driver","com.mysql.cj.jdbc.Driver",
            "jakarta.persistence.jdbc.url",variable("DB_URL","jdbc:mysql://localhost:13308/sismos_pa2?connectionTimeZone=America/Lima"),
            "jakarta.persistence.jdbc.user",variable("DB_USER","sismos"),
            "jakarta.persistence.jdbc.password",clave));
        evento.getServletContext().setAttribute(REPOSITORIO,new SismoRepository(fabrica));
        evento.getServletContext().setAttribute(UBICACIONES,new UbicacionRepository(fabrica));
    }
    @Override public void contextDestroyed(ServletContextEvent evento) {
        if(fabrica!=null && fabrica.isOpen()) fabrica.close();
    }
}
