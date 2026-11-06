package pe.isil.sismos.repository;
import pe.isil.sismos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TipoAlertaRepository extends JpaRepository<TipoAlerta,Long>{
 Optional<TipoAlerta> findByCodigo(String codigo);
 List<TipoAlerta> findAllByOrderByPrioridadAscCodigoAsc();
 List<TipoAlerta> findByEstadoOrderByPrioridadAsc(EstadoMaestro estado);
}
