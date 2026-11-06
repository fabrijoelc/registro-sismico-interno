package pe.isil.sismos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Entity @Table(name="tipo_dano")
public class TipoDano extends CatalogoMaestro {
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private SectorDano sector;
    @Column(nullable=false,length=500) private String descripcion;
}
