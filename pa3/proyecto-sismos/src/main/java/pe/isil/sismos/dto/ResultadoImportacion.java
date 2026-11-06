package pe.isil.sismos.dto;
import lombok.Getter;
@Getter
public class ResultadoImportacion {
 private final String tipo;
 private final int total,importados,omitidos;
 public ResultadoImportacion(String tipo,int total,int importados,int omitidos){this.tipo=tipo;this.total=total;this.importados=importados;this.omitidos=omitidos;}
}
