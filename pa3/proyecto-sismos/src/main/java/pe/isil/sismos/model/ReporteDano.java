package pe.isil.sismos.model;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Entity @Table(name="reporte_dano")
public class ReporteDano {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="sismo_id",nullable=false) private Sismo sismo;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="tipo_dano_id",nullable=false) private TipoDano tipoDano;
    @Column(name="cantidad_afectada",nullable=false) private Integer cantidadAfectada;
    @Column(nullable=false,length=1000) private String descripcion;
    @Column(name="fecha_registro",nullable=false) private LocalDateTime fechaRegistro;
}
