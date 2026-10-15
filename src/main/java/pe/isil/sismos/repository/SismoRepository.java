package pe.isil.sismos.repository;
import jakarta.persistence.*;
import pe.isil.sismos.model.*;
import java.util.*;
public class SismoRepository extends JpaRepository {
    public SismoRepository(EntityManagerFactory fabrica) { super(fabrica); }
    public List<Sismo> listar(String busqueda,EstadoSismo estado) {
        String filtro=busqueda==null?"":busqueda.trim().toUpperCase(Locale.ROOT);
        return leer(em -> {
            String jpql="select s from Sismo s join fetch s.ubicacionGeografica where s.activo=true and locate(:q, upper(s.codigo))>0";
            if(estado!=null) jpql+=" and s.estado=:estado";
            var query=em.createQuery(jpql+" order by s.id desc",Sismo.class).setParameter("q",filtro);
            if(estado!=null) query.setParameter("estado",estado);
            return query.getResultList();
        });
    }
    public Optional<Sismo> buscarActivo(long id) {
        return leer(em -> Optional.ofNullable(em.find(Sismo.class,id)).filter(Sismo::isActivo));
    }
    public boolean existeCodigo(String codigo,Long exceptuarId) {
        return leer(em -> duplicado(em,codigo,exceptuarId));
    }
    private boolean duplicado(EntityManager em,String codigo,Long id) {
        return em.createQuery("select count(s) from Sismo s where upper(s.codigo)=:codigo and s.id<>:id",Long.class)
            .setParameter("codigo",codigo.trim().toUpperCase(Locale.ROOT)).setParameter("id",id==null?0L:id).getSingleResult()>0;
    }
    private void preparar(EntityManager em,Sismo s,Long id) {
        if(duplicado(em,s.getCodigo(),id)) throw new IllegalArgumentException("El código ya existe, incluso entre reportes dados de baja.");
        Ubicacion u=s.getUbicacionGeografica()==null?null:em.find(Ubicacion.class,s.getUbicacionGeografica().getId(),LockModeType.PESSIMISTIC_WRITE);
        if(u==null || !u.isActivo()) throw new IllegalArgumentException("Selecciona una ubicación activa del catálogo.");
        s.setCodigo(s.getCodigo().trim().toUpperCase(Locale.ROOT)); s.setUbicacionGeografica(u);
    }
    public Sismo crear(Sismo sismo) {
        return escribir(em -> { preparar(em,sismo,null); sismo.setId(0); sismo.setActivo(true); em.persist(sismo); return sismo; });
    }
    public boolean actualizar(long id,Sismo datos) {
        return escribir(em -> {
            Sismo actual=em.find(Sismo.class,id,LockModeType.PESSIMISTIC_WRITE);
            if(actual==null || !actual.isActivo()) return false;
            preparar(em,datos,id); datos.setId(id); datos.setActivo(true); em.merge(datos); return true;
        });
    }
    public boolean eliminarLogicamente(long id) {
        return escribir(em -> {
            Sismo actual=em.find(Sismo.class,id,LockModeType.PESSIMISTIC_WRITE);
            if(actual==null || !actual.isActivo()) return false;
            actual.setActivo(false); return true;
        });
    }
}
