package pe.isil.sismos.repository;

import org.junit.jupiter.api.Test;
import pe.isil.sismos.model.EstadoSismo;
import pe.isil.sismos.model.Sismo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SismoRepositoryTest {
    private Sismo nuevo(String codigo, EstadoSismo estado) {
        return new Sismo(0, codigo, LocalDateTime.of(2026, 9, 23, 10, 0),
                "Lima", new BigDecimal("4.2"), new BigDecimal("35"), estado, "Prueba", true);
    }

    @Test
    void creaBuscaYFiltraPorEstadoYCodigo() {
        SismoRepository repositorio = new SismoRepository();
        Sismo primero = repositorio.crear(nuevo("SIS-001", EstadoSismo.PENDIENTE));
        Sismo segundo = repositorio.crear(nuevo("SIS-002", EstadoSismo.VERIFICADO));

        assertEquals(1, primero.getId());
        assertEquals(2, segundo.getId());
        assertEquals(2, repositorio.listar(null, null).size());
        assertEquals("SIS-002", repositorio.listar("002", null).getFirst().getCodigo());
        assertEquals("SIS-001", repositorio.listar(null, EstadoSismo.PENDIENTE).getFirst().getCodigo());
        assertEquals(2, repositorio.listar(null, null).getFirst().getId());
    }

    @Test
    void impideCodigoDuplicadoYPermiteConservarloAlEditar() {
        SismoRepository repositorio = new SismoRepository();
        Sismo primero = repositorio.crear(nuevo("SIS-001", EstadoSismo.PENDIENTE));
        Sismo segundo = repositorio.crear(nuevo("SIS-002", EstadoSismo.PENDIENTE));

        assertThrows(IllegalArgumentException.class,
                () -> repositorio.crear(nuevo("sis-001", EstadoSismo.PENDIENTE)));
        assertTrue(repositorio.actualizar(primero.getId(), nuevo("SIS-001", EstadoSismo.VERIFICADO)));
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.actualizar(segundo.getId(), nuevo("SIS-001", EstadoSismo.VERIFICADO)));
        assertEquals(EstadoSismo.VERIFICADO, repositorio.buscarActivo(primero.getId()).orElseThrow().getEstado());
    }

    @Test
    void bajaLogicaOcultaElRegistroSinReutilizarSuId() {
        SismoRepository repositorio = new SismoRepository();
        Sismo primero = repositorio.crear(nuevo("SIS-001", EstadoSismo.PENDIENTE));

        assertTrue(repositorio.eliminarLogicamente(primero.getId()));
        assertFalse(repositorio.eliminarLogicamente(primero.getId()));
        assertTrue(repositorio.buscarActivo(primero.getId()).isEmpty());
        assertTrue(repositorio.listar(null, null).isEmpty());
        assertEquals(2, repositorio.crear(nuevo("SIS-001", EstadoSismo.PENDIENTE)).getId());
    }
}
