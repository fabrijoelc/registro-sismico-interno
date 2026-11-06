package pe.isil.sismos.web;
import pe.isil.sismos.service.ReglaNegocioException;
import org.springframework.validation.BindingResult;
final class FormErrors {
 private FormErrors(){}
 static void agregar(BindingResult result,ReglaNegocioException e){
  if(e.getErrores().isEmpty())result.reject("regla",e.getMessage());
  else e.getErrores().forEach((campo,mensaje)->result.rejectValue(campo,"regla",mensaje));
 }
}
