package pe.isil.sismos.repository;
import pe.isil.sismos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TipoDanoRepository extends JpaRepository<TipoDano,Long>{
 Optional<TipoDano> findByCodigo(String codigo);
 List<TipoDano> findAllByOrderByCodigoAsc();
 List<TipoDano> findByEstadoOrderByNombreAsc(EstadoMaestro estado);
}
