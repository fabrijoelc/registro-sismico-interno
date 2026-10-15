package pe.isil.sismos.repository;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import pe.isil.sismos.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class PersistenciaTest {
    static EntityManagerFactory emf;
    SismoRepository sismos;
    UbicacionRepository ubicaciones;
    Ubicacion lima;
    @BeforeAll static void iniciar() {
        emf=Persistence.createEntityManagerFactory("sismos",Map.of(
            "jakarta.persistence.jdbc.driver","org.h2.Driver",
            "jakarta.persistence.jdbc.url","jdbc:h2:mem:repo;MODE=MySQL;DB_CLOSE_DELAY=-1",
            "jakarta.persistence.jdbc.user","sa",
            "jakarta.persistence.jdbc.password","",
            "hibernate.hbm2ddl.auto","create-drop"));
    }
    @AfterAll static void cerrar() { emf.close(); }
    @BeforeEach void preparar() {
        try(var em=emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("delete from Sismo").executeUpdate();
            em.createQuery("delete from Ubicacion").executeUpdate();
            em.getTransaction().commit();
        }
        sismos=new SismoRepository(emf); ubicaciones=new UbicacionRepository(emf);
        lima=ubicaciones.crear(new Ubicacion("LIM","Lima","Lima","Jesús María"));
    }
    Sismo nuevo(String codigo) {
        Sismo s=new Sismo(0,codigo,LocalDateTime.of(2026,9,23,10,0),"Próximo al malecón",
            new BigDecimal("4.20"),new BigDecimal("35.00"),EstadoSismo.PENDIENTE,"Prueba académica",true);
        s.setUbicacionGeografica(lima); s.setCanal(CanalRecepcion.CORREO);
        s.setLatitud(new BigDecimal("-12.046374")); s.setLongitud(new BigDecimal("-77.042793"));
        return s;
    }
    @Test void creaYRecuperaRelacionCanalYCoordenadas() {
        Sismo s=sismos.crear(nuevo("SIS-001"));
        assertTrue(s.getId()>0);
        Sismo rec=new SismoRepository(emf).buscarActivo(s.getId()).orElseThrow();
        assertEquals("Jesús María",rec.getUbicacionGeografica().getDistrito());
        assertEquals(CanalRecepcion.CORREO,rec.getCanal());
        assertEquals(0,rec.getLatitud().compareTo(new BigDecimal("-12.046374")));
    }
    @Test void filtraCodigoYEstadoConJpql() {
        sismos.crear(nuevo("ABC-001")); Sismo b=nuevo("ABC-002"); b.setEstado(EstadoSismo.VERIFICADO); sismos.crear(b);
        assertEquals(2,sismos.listar("",null).size());
        assertEquals(1,sismos.listar("abc",EstadoSismo.VERIFICADO).size());
        assertTrue(sismos.listar("%",null).isEmpty());
    }
    @Test void rechazaCodigoDuplicadoSinDistinguirMayusculas() {
        sismos.crear(nuevo("SIS-001"));
        assertThrows(IllegalArgumentException.class,()->sismos.crear(nuevo("sis-001")));
        assertEquals(1,sismos.listar("",null).size());
    }
    @Test void editaYCambiaEstado() {
        Sismo s=sismos.crear(nuevo("SIS-001"));
        Sismo editado=nuevo("SIS-001"); editado.setEstado(EstadoSismo.VERIFICADO); editado.setCanal(CanalRecepcion.MENSAJE);
        assertTrue(sismos.actualizar(s.getId(),editado));
        assertEquals(CanalRecepcion.MENSAJE,sismos.buscarActivo(s.getId()).orElseThrow().getCanal());
        assertEquals(EstadoSismo.VERIFICADO,sismos.buscarActivo(s.getId()).orElseThrow().getEstado());
    }
    @Test void rollbackNoDejaCambiosSiCodigoDuplicado() {
        Sismo a=sismos.crear(nuevo("A")); sismos.crear(nuevo("B"));
        assertThrows(IllegalArgumentException.class,()->sismos.actualizar(a.getId(),nuevo("B")));
        assertEquals("A",sismos.buscarActivo(a.getId()).orElseThrow().getCodigo());
    }
    @Test void bajaLogicaConservaFilaYCodigoReservado() {
        Sismo s=sismos.crear(nuevo("A"));
        assertTrue(sismos.eliminarLogicamente(s.getId()));
        assertTrue(sismos.buscarActivo(s.getId()).isEmpty()); assertTrue(sismos.listar("",null).isEmpty());
        try(var em=emf.createEntityManager()) { assertFalse(em.find(Sismo.class,s.getId()).isActivo()); }
        assertThrows(IllegalArgumentException.class,()->sismos.crear(nuevo("A")));
    }
    @Test void crudUbicacionYTextoUtf8() {
        Ubicacion u=ubicaciones.crear(new Ubicacion("ARE","Arequipa","Camaná","José María Quimper"));
        assertTrue(ubicaciones.actualizar(u.getId(),new Ubicacion("ARE","Arequipa","Camaná","Nicolás de Piérola")));
        assertEquals("Nicolás de Piérola",ubicaciones.buscarActivo(u.getId()).orElseThrow().getDistrito());
        assertTrue(ubicaciones.eliminarLogicamente(u.getId()));
        assertTrue(ubicaciones.buscarActivo(u.getId()).isEmpty());
    }
    @Test void noPermiteBajaDeUbicacionUtilizada() {
        sismos.crear(nuevo("A"));
        assertThrows(IllegalArgumentException.class,()->ubicaciones.eliminarLogicamente(lima.getId()));
        assertTrue(ubicaciones.buscarActivo(lima.getId()).isPresent());
    }
    @Test void permiteBajaDeUbicacionSinReportesActivosConservandoHistoria() {
        Sismo s=sismos.crear(nuevo("A")); sismos.eliminarLogicamente(s.getId());
        assertTrue(ubicaciones.eliminarLogicamente(lima.getId()));
        try(var em=emf.createEntityManager()) { assertNotNull(em.find(Sismo.class,s.getId()).getUbicacionGeografica()); }
    }
    @Test void rechazaRelacionInactiva() {
        ubicaciones.eliminarLogicamente(lima.getId());
        assertThrows(IllegalArgumentException.class,()->sismos.crear(nuevo("A")));
    }
    @Test void ubicacionDuplicadaYEdicionConCodigoPropio() {
        assertThrows(IllegalArgumentException.class,()->ubicaciones.crear(new Ubicacion("lim","Lima","Lima","Lima")));
        assertTrue(ubicaciones.actualizar(lima.getId(),new Ubicacion("LIM","Lima","Lima","Lima")));
    }
    @Test void operacionesConIdInexistenteNoInventanFilas() {
        assertTrue(sismos.buscarActivo(99999).isEmpty());
        assertFalse(sismos.actualizar(99999,nuevo("A")));
        assertFalse(sismos.eliminarLogicamente(99999));
        assertFalse(ubicaciones.actualizar(99999,lima));
    }
}
