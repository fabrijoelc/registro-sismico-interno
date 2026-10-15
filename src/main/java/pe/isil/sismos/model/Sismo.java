package pe.isil.sismos.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="sismo", uniqueConstraints=@UniqueConstraint(name="uk_sismo_codigo", columnNames="codigo"))
public class Sismo {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false, length=40)
    private String codigo;
    @Column(name="fecha_hora", nullable=false)
    private LocalDateTime fechaHora;
    @Column(name="referencia", nullable=false, length=120)
    private String ubicacion;
    @Column(nullable=false, precision=4, scale=2)
    private BigDecimal magnitud;
    @Column(name="profundidad_km", nullable=false, precision=7, scale=2)
    private BigDecimal profundidadKm;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
    private EstadoSismo estado;
    @Column(length=500)
    private String observaciones;
    @Column(nullable=false)
    private boolean activo = true;
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="ubicacion_id", nullable=false, foreignKey=@ForeignKey(name="fk_sismo_ubicacion"))
    private Ubicacion ubicacionGeografica;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
    private CanalRecepcion canal;
    @Column(precision=9, scale=6)
    private BigDecimal latitud;
    @Column(precision=9, scale=6)
    private BigDecimal longitud;

    public Ubicacion getUbicacionGeografica() { return ubicacionGeografica; }
    public void setUbicacionGeografica(Ubicacion valor) { ubicacionGeografica = valor; }
    public CanalRecepcion getCanal() { return canal; }
    public void setCanal(CanalRecepcion valor) { canal = valor; }
    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal valor) { latitud = valor; }
    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal valor) { longitud = valor; }

    public Sismo() { }

    public Sismo(long id, String codigo, LocalDateTime fechaHora, String ubicacion,
                 BigDecimal magnitud, BigDecimal profundidadKm, EstadoSismo estado,
                 String observaciones, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.magnitud = magnitud;
        this.profundidadKm = profundidadKm;
        this.estado = estado;
        this.observaciones = observaciones;
        this.activo = activo;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getFechaHoraTexto() { return fechaHora == null ? "" : fechaHora.format(FORMATO); }
    public String getFechaHoraFormulario() { return fechaHora == null ? "" : fechaHora.toString(); }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public BigDecimal getMagnitud() { return magnitud; }
    public void setMagnitud(BigDecimal magnitud) { this.magnitud = magnitud; }
    public BigDecimal getProfundidadKm() { return profundidadKm; }
    public void setProfundidadKm(BigDecimal profundidadKm) { this.profundidadKm = profundidadKm; }
    public EstadoSismo getEstado() { return estado; }
    public void setEstado(EstadoSismo estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
