package pe.isil.sismos.repository;

import pe.isil.sismos.model.EstadoSismo;
import pe.isil.sismos.model.Sismo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class SismoRepository {
    private final Map<Long, Sismo> registros = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public synchronized List<Sismo> listar(String busqueda, EstadoSismo estado) {
        String filtro = busqueda == null ? "" : busqueda.trim().toLowerCase();
        List<Sismo> resultado = new ArrayList<>();
        for (Sismo sismo : registros.values()) {
            if (!sismo.isActivo()) continue;
            if (!filtro.isEmpty() && !sismo.getCodigo().toLowerCase().contains(filtro)) continue;
            if (estado != null && sismo.getEstado() != estado) continue;
            resultado.add(sismo);
        }
        resultado.sort(Comparator.comparingLong(Sismo::getId).reversed());
        return resultado;
    }

    public synchronized Optional<Sismo> buscarActivo(long id) {
        Sismo sismo = registros.get(id);
        return sismo != null && sismo.isActivo() ? Optional.of(sismo) : Optional.empty();
    }

    public synchronized boolean existeCodigo(String codigo, Long exceptuarId) {
        for (Sismo sismo : registros.values()) {
            if (sismo.isActivo() && sismo.getCodigo().equalsIgnoreCase(codigo)
                    && (exceptuarId == null || sismo.getId() != exceptuarId)) return true;
        }
        return false;
    }

    public synchronized Sismo crear(Sismo sismo) {
        if (existeCodigo(sismo.getCodigo(), null)) throw new IllegalArgumentException("El código ya está registrado.");
        sismo.setId(secuencia.incrementAndGet());
        sismo.setActivo(true);
        registros.put(sismo.getId(), sismo);
        return sismo;
    }

    public synchronized boolean actualizar(long id, Sismo datos) {
        Sismo actual = registros.get(id);
        if (actual == null || !actual.isActivo()) return false;
        if (existeCodigo(datos.getCodigo(), id)) throw new IllegalArgumentException("El código pertenece a otro reporte.");
        datos.setId(id);
        datos.setActivo(true);
        registros.put(id, datos);
        return true;
    }

    public synchronized boolean eliminarLogicamente(long id) {
        Sismo actual = registros.get(id);
        if (actual == null || !actual.isActivo()) return false;
        actual.setActivo(false);
        return true;
    }
}
