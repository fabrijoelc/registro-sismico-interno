package pe.isil.sismos.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CatalogoForm {
 @NotBlank(message="Ingresa el código.") @Size(max=20,message="El código admite hasta 20 caracteres.") @Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9_-]*",message="Usa letras, números, guiones o guiones bajos en el código.") private String codigo;
 @NotBlank(message="Ingresa el nombre.") @Size(max=100,message="El nombre admite hasta 100 caracteres.") private String nombre;
 private String tipo="DEPARTAMENTO";
 private String color="VERDE";
 private Integer prioridad;
 private String sector="SALUD";
 @Size(max=500,message="La descripción admite hasta 500 caracteres.") private String descripcion;
 @NotBlank(message="Selecciona un estado válido.") private String estado="ACTIVO";
}
