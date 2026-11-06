package pe.isil.sismos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Entity @Table(name="tipo_alerta")
public class TipoAlerta extends CatalogoMaestro {
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private ColorAlerta color;
    @Column(nullable=false) private Integer prioridad;
    @Column(nullable=false,length=500) private String descripcion;
}
