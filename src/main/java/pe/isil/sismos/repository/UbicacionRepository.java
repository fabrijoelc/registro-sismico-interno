package pe.isil.sismos.repository;
import jakarta.persistence.*;
import pe.isil.sismos.model.Ubicacion;
import java.util.*;
public class UbicacionRepository extends JpaRepository {
    public UbicacionRepository(EntityManagerFactory fabrica) { super(fabrica); }
    public List<Ubicacion> listar() {
        return leer(em -> em.createQuery("select u from Ubicacion u where u.activo=true order by u.departamento,u.provincia,u.distrito",Ubicacion.class).getResultList());
    }
    public Optional<Ubicacion> buscarActivo(long id) {
        return leer(em -> Optional.ofNullable(em.find(Ubicacion.class,id)).filter(Ubicacion::isActivo));
    }
    private void validar(EntityManager em,Ubicacion u,long id) {
        u.setCodigo(u.getCodigo().trim().toUpperCase(Locale.ROOT));
        if(em.createQuery("select count(u) from Ubicacion u where upper(u.codigo)=:c and u.id<>:id",Long.class)
            .setParameter("c",u.getCodigo()).setParameter("id",id).getSingleResult()>0)
            throw new IllegalArgumentException("El código de ubicación ya existe, incluso si fue dado de baja.");
    }
    public Ubicacion crear(Ubicacion u) {
        return escribir(em -> { validar(em,u,0); u.setId(0); u.setActivo(true); em.persist(u); return u; });
    }
    public boolean actualizar(long id,Ubicacion datos) {
        return escribir(em -> {
            Ubicacion actual=em.find(Ubicacion.class,id,LockModeType.PESSIMISTIC_WRITE);
            if(actual==null || !actual.isActivo()) return false;
            validar(em,datos,id); actual.setCodigo(datos.getCodigo()); actual.setDepartamento(datos.getDepartamento());
            actual.setProvincia(datos.getProvincia()); actual.setDistrito(datos.getDistrito()); return true;
        });
    }
    public boolean eliminarLogicamente(long id) {
        return escribir(em -> {
            Ubicacion actual=em.find(Ubicacion.class,id,LockModeType.PESSIMISTIC_WRITE);
            if(actual==null || !actual.isActivo()) return false;
            long usados=em.createQuery("select count(s) from Sismo s where s.activo=true and s.ubicacionGeografica.id=:id",Long.class).setParameter("id",id).getSingleResult();
            if(usados>0) throw new IllegalArgumentException("No se puede dar de baja: existen reportes activos asociados. Reasígnalos primero.");
            actual.setActivo(false); return true;
        });
    }
}
