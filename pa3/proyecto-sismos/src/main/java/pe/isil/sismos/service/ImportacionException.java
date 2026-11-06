package pe.isil.sismos.service;
import java.util.*;
public class ImportacionException extends RuntimeException {
 private final List<String> errores;
 public ImportacionException(List<String> errores){super("El archivo contiene errores. No se guardó ninguna fila.");this.errores=List.copyOf(errores);}
 public List<String> getErrores(){return errores;}
}
