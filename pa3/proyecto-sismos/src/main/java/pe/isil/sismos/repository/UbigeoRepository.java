package pe.isil.sismos.repository;
import pe.isil.sismos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UbigeoRepository extends JpaRepository<Ubigeo,Long>{
 Optional<Ubigeo> findByCodigo(String codigo);
 List<Ubigeo> findAllByOrderByCodigoAsc();
 List<Ubigeo> findByEstadoOrderByNombreAsc(EstadoMaestro estado);
}
