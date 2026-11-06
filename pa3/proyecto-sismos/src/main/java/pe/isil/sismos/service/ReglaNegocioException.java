package pe.isil.sismos.service;
import java.util.*;
public class ReglaNegocioException extends RuntimeException {
 private final Map<String,String> errores;
 public ReglaNegocioException(String mensaje){super(mensaje);this.errores=Map.of();}
 public ReglaNegocioException(String campo,String mensaje){super(mensaje);this.errores=Map.of(campo,mensaje);}
 public ReglaNegocioException(Map<String,String> errores){super(String.join(" ",errores.values()));this.errores=Map.copyOf(errores);}
 public Map<String,String> getErrores(){return errores;}
}
