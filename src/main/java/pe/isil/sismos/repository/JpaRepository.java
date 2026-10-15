package pe.isil.sismos.repository;
import jakarta.persistence.*;
import java.util.function.Function;
/** Una fábrica por aplicación; un EntityManager por operación, nunca compartido entre peticiones. */
public abstract class JpaRepository {
    protected final EntityManagerFactory fabrica;
    protected JpaRepository(EntityManagerFactory fabrica) { this.fabrica=fabrica; }
    protected <T> T leer(Function<EntityManager,T> trabajo) {
        try(EntityManager em=fabrica.createEntityManager()) { return trabajo.apply(em); }
    }
    protected <T> T escribir(Function<EntityManager,T> trabajo) {
        try(EntityManager em=fabrica.createEntityManager()) {
            EntityTransaction tx=em.getTransaction();
            try {
                tx.begin(); T resultado=trabajo.apply(em); tx.commit(); return resultado;
            } catch(RuntimeException ex) {
                if(tx.isActive()) tx.rollback();
                if(ex instanceof PersistenceException)
                    throw new IllegalArgumentException("No se pudo guardar. Verifica que el código sea único y la ubicación siga disponible.",ex);
                throw ex;
            }
        }
    }
}
