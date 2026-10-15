package pe.isil.sismos.servlet;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import pe.isil.sismos.repository.*;
import pe.isil.sismos.model.*;
import java.lang.reflect.Proxy;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class SismoFormTest {
    private Map<String,String> datos;
    private final SismoRepository sismos=new SismoRepository(null) {
        @Override public boolean existeCodigo(String codigo,Long id) { return codigo.equals("DUPLICADO"); }
    };
    private final UbicacionRepository ubicaciones=new UbicacionRepository(null) {
        @Override public Optional<Ubicacion> buscarActivo(long id) {
            Ubicacion u=new Ubicacion("LIM","Lima","Lima","Lima"); u.setId(1);
            return id==1?Optional.of(u):Optional.empty();
        }
    };
    @BeforeEach void preparar() {
        datos=new HashMap<>(Map.of("codigo","SIS-001","fechaHora","2026-09-23T10:00","ubicacion","Lima",
            "magnitud","4.20","profundidadKm","30","estado","PENDIENTE","observaciones","Prueba",
            "ubicacionId","1","canal","LLAMADA"));
    }
    SismoForm validar() {
        var request=(HttpServletRequest)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{HttpServletRequest.class},
            (obj,method,args)->method.getName().equals("getParameter")?datos.get(args[0]):null);
        return SismoForm.validar(request,sismos,ubicaciones,null);
    }
    @Test void formularioValidoSinCoordenadas() { assertTrue(validar().isValido()); }
    @Test void coordenadasLimiteValidas() {
        datos.put("latitud","-90"); datos.put("longitud","180"); assertTrue(validar().isValido());
    }
    @Test void rechazaLatitudFueraDeRango() {
        datos.put("latitud","91"); datos.put("longitud","-77"); assertTrue(validar().getErrores().containsKey("latitud"));
    }
    @Test void rechazaLongitudFueraDeRango() {
        datos.put("latitud","-12"); datos.put("longitud","181"); assertTrue(validar().getErrores().containsKey("longitud"));
    }
    @Test void rechazaCoordenadasIncompletas() {
        datos.put("latitud","-12"); assertTrue(validar().getErrores().containsKey("longitud"));
    }
    @Test void rechazaExcesoDecimales() {
        datos.put("magnitud","4.222"); datos.put("latitud","-12.1234567"); datos.put("longitud","-77");
        assertEquals(2,validar().getErrores().size());
    }
    @Test void rechazaNumerosInvalidos() {
        datos.put("magnitud","NaN"); datos.put("profundidadKm","-1"); assertEquals(2,validar().getErrores().size());
    }
    @Test void rechazaEnumsManipulados() {
        datos.put("estado","ADMIN"); datos.put("canal","OTRO"); assertEquals(2,validar().getErrores().size());
    }
    @Test void rechazaFechaFutura() {
        datos.put("fechaHora","2999-01-01T10:00"); assertTrue(validar().getErrores().containsKey("fechaHora"));
    }
    @Test void rechazaCamposVaciosDuplicadosYRelacionInexistente() {
        datos.put("codigo","DUPLICADO"); datos.put("ubicacion",""); datos.put("ubicacionId","999");
        assertEquals(3,validar().getErrores().size());
    }
    @Test void conservaLosValoresTrasError() {
        datos.put("latitud","91"); datos.put("longitud","-77");
        assertEquals("91",validar().getValores().get("latitud"));
    }
}
