package pe.isil.sismos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Entity @Table(name="ubigeo")
public class Ubigeo extends CatalogoMaestro {
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private TipoUbigeo tipo;
}
