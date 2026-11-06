package pe.isil.sismos.service;
import jakarta.validation.Validator;
import java.util.*;
import org.springframework.stereotype.Component;
@Component
public class Validacion {
 private final Validator validator;
 public Validacion(Validator validator){this.validator=validator;}
 public void comprobar(Object form){
  Map<String,String> errores=new LinkedHashMap<>();
  validator.validate(form).forEach(v->errores.putIfAbsent(v.getPropertyPath().toString(),v.getMessage()));
  if(!errores.isEmpty())throw new ReglaNegocioException(errores);
 }
 public static String limpiar(String s){return s==null?"":s.trim();}
 public static String codigo(String s){return limpiar(s).toUpperCase(Locale.ROOT);}
 public static <E extends Enum<E>> E valor(Class<E> tipo,String valor,String campo){
  try{return Enum.valueOf(tipo,limpiar(valor));}catch(Exception e){throw new ReglaNegocioException(campo,"Selecciona un valor válido para "+campo+".");}
 }
}
