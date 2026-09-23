package pe.isil.sismos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sismo {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private long id;
    private String codigo;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private BigDecimal magnitud;
    private BigDecimal profundidadKm;
    private EstadoSismo estado;
    private String observaciones;
    private boolean activo = true;

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
