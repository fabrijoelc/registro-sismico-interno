package pe.isil.sismos.repository;
import pe.isil.sismos.model.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;
public interface ReporteDanoRepository extends JpaRepository<ReporteDano,Long>{
 @EntityGraph(attributePaths={"tipoDano"}) List<ReporteDano> findBySismoIdOrderByFechaRegistroDescIdDesc(Long id);
 boolean existsByTipoDanoId(Long id);
}
