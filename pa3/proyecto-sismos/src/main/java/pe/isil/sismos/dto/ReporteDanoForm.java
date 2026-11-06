package pe.isil.sismos.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class ReporteDanoForm {
 @NotNull(message="Selecciona un tipo de daño activo.") private Long tipoDanoId;
 @NotNull(message="Ingresa la cantidad afectada.") @Min(value=1,message="La cantidad debe ser un entero mayor que cero.") @Max(value=1000000000,message="La cantidad supera el máximo permitido.") private Integer cantidadAfectada;
 @NotBlank(message="Describe el daño y la unidad de la cantidad: personas, viviendas u otra.") @Size(max=1000,message="La descripción admite hasta 1000 caracteres.") private String descripcion;
}
