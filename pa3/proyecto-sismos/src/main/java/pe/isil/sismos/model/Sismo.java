package pe.isil.sismos.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Entity @Table(name="sismo")
public class Sismo {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=30) private String codigo;
    @Column(name="fecha_hora",nullable=false) private LocalDateTime fechaHora;
    @Column(nullable=false,precision=4,scale=2) private BigDecimal magnitud;
    @Column(name="profundidad_km",nullable=false,precision=6,scale=2) private BigDecimal profundidadKm;
    @Column(nullable=false,length=200) private String referencia;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ubigeo_id",nullable=false) private Ubigeo ubigeo;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="tipo_alerta_id",nullable=false) private TipoAlerta tipoAlerta;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=15) private CanalRecepcion canal;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private EstadoSismo estado=EstadoSismo.PENDIENTE;
    @Column(precision=9,scale=6) private BigDecimal latitud;
    @Column(precision=9,scale=6) private BigDecimal longitud;
    @Column(length=1000) private String observaciones;
    @Column(nullable=false) private boolean activo=true;
}
