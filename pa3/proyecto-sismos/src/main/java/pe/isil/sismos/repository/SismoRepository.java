package pe.isil.sismos.repository;
import pe.isil.sismos.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface SismoRepository extends JpaRepository<Sismo,Long>{
 @EntityGraph(attributePaths={"ubigeo","tipoAlerta"}) Optional<Sismo> findByIdAndActivoTrue(Long id);
 Optional<Sismo> findByCodigo(String codigo);
 @EntityGraph(attributePaths={"ubigeo","tipoAlerta"})
 @Query("select s from Sismo s where s.activo=true and (:codigo='' or lower(s.codigo) like lower(concat('%',:codigo,'%'))) and (:estado is null or s.estado=:estado) order by s.fechaHora desc,s.id desc")
 List<Sismo> buscar(@Param("codigo")String codigo,@Param("estado")EstadoSismo estado);
 boolean existsByUbigeoIdAndActivoTrue(Long id);
 boolean existsByTipoAlertaIdAndActivoTrue(Long id);
}
