package pe.isil.sismos.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class SismoForm {
 @NotBlank(message="Ingresa el código del reporte.") @Size(max=30,message="El código admite hasta 30 caracteres.") @Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9_-]*",message="Usa letras, números, guiones o guiones bajos en el código.") private String codigo;
 @NotNull(message="Ingresa la fecha y hora del sismo.") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") private LocalDateTime fechaHora;
 @NotNull(message="Ingresa la magnitud.") @DecimalMin(value="0",message="La magnitud no puede ser negativa.") @DecimalMax(value="10",message="La magnitud no puede superar 10.") @Digits(integer=2,fraction=2,message="La magnitud admite dos decimales.") private BigDecimal magnitud;
 @NotNull(message="Ingresa la profundidad.") @DecimalMin(value="0",message="La profundidad no puede ser negativa.") @DecimalMax(value="1000",message="La profundidad no puede superar 1000 km.") @Digits(integer=4,fraction=2,message="La profundidad admite dos decimales.") private BigDecimal profundidadKm;
 @NotBlank(message="Ingresa una referencia de ubicación.") @Size(max=200,message="La referencia admite hasta 200 caracteres.") private String referencia;
 @NotNull(message="Selecciona un ubigeo activo.") private Long ubigeoId;
 @NotNull(message="Selecciona un tipo de alerta activo.") private Long tipoAlertaId;
 @NotBlank(message="Selecciona el canal de recepción.") private String canal="LLAMADA";
 @NotBlank(message="Selecciona el estado del reporte.") private String estado="PENDIENTE";
 @DecimalMin(value="-90",message="La latitud debe estar entre -90 y 90.") @DecimalMax(value="90",message="La latitud debe estar entre -90 y 90.") @Digits(integer=2,fraction=6,message="La latitud admite seis decimales.") private BigDecimal latitud;
 @DecimalMin(value="-180",message="La longitud debe estar entre -180 y 180.") @DecimalMax(value="180",message="La longitud debe estar entre -180 y 180.") @Digits(integer=3,fraction=6,message="La longitud admite seis decimales.") private BigDecimal longitud;
 @Size(max=1000,message="Las observaciones admiten hasta 1000 caracteres.") private String observaciones;
}
