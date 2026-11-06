package pe.isil.sismos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @MappedSuperclass
public abstract class CatalogoMaestro {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=20) private String codigo;
    @Column(nullable=false,length=100) private String nombre;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private EstadoMaestro estado=EstadoMaestro.ACTIVO;
    public boolean isActivo(){return estado==EstadoMaestro.ACTIVO;}
}
