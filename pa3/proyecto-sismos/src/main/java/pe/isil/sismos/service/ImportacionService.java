package pe.isil.sismos.service;

import pe.isil.sismos.dto.*;
import pe.isil.sismos.model.*;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.nio.charset.*;
import java.util.*;

@Service
public class ImportacionService {
 private final CatalogoService catalogos;
 public ImportacionService(CatalogoService catalogos){this.catalogos=catalogos;}

 /** Una transacción por archivo: cualquier fila inválida revierte todas las altas. */
 @Transactional
 public ResultadoImportacion importar(String tipo,byte[] bytes){
  String catalogo=switch(Validacion.limpiar(tipo)){case "UBIGEO"->"ubigeos";case "ALERTA"->"alertas";case "DANO"->"danos";default->throw new ImportacionException(List.of("Selecciona el catálogo que corresponde al archivo."));};
  if(bytes==null||bytes.length==0)throw new ImportacionException(List.of("Selecciona un archivo CSV con datos."));
  if(bytes.length>2*1024*1024)throw new ImportacionException(List.of("El archivo supera el máximo de 2 MB."));
  List<String> cabecera=switch(catalogo){case "ubigeos"->List.of("codigo","nombre","tipo","estado");case "alertas"->List.of("codigo","nombre","color","prioridad","descripcion","estado");default->List.of("codigo","nombre","sector","descripcion","estado");};
  List<String> errores=new ArrayList<>();Set<String> codigos=new HashSet<>();int total=0,importados=0,omitidos=0;
  CharsetDecoder decoder=StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
  try(PushbackReader reader=new PushbackReader(new InputStreamReader(new ByteArrayInputStream(bytes),decoder),1)){
   int primero=reader.read();if(primero!=-1&&primero!='\uFEFF')reader.unread(primero);
   CSVFormat formato=CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreEmptyLines(true).setAllowMissingColumnNames(false).build();
   try(CSVParser parser=formato.parse(reader)){
    if(!parser.getHeaderNames().equals(cabecera))throw new ImportacionException(List.of("La cabecera debe ser exactamente: "+String.join(",",cabecera)+". Usa el archivo del catálogo seleccionado."));
    // Se lee el archivo completo antes de modificar datos para detectar comillas mal cerradas.
    List<CSVRecord> filas=parser.getRecords();
    if(filas.isEmpty())throw new ImportacionException(List.of("El archivo tiene cabecera, pero no contiene registros."));
    for(CSVRecord fila:filas){
     total++;String prefijo="Fila "+(fila.getRecordNumber()+1)+": ";
     if(!fila.isConsistent()){errores.add(prefijo+"la cantidad de columnas no coincide con la cabecera.");continue;}
     CatalogoForm f=new CatalogoForm();f.setCodigo(Validacion.codigo(fila.get("codigo")));f.setNombre(Validacion.limpiar(fila.get("nombre")));f.setEstado(Validacion.limpiar(fila.get("estado")));
     if(!codigos.add(f.getCodigo())){errores.add(prefijo+"el código "+f.getCodigo()+" se repite dentro del archivo.");continue;}
     try{
      if(catalogo.equals("ubigeos"))f.setTipo(fila.get("tipo").trim());
      if(catalogo.equals("alertas")){
       f.setColor(fila.get("color").trim());f.setDescripcion(fila.get("descripcion").trim());
       try{f.setPrioridad(Integer.valueOf(fila.get("prioridad").trim()));}catch(NumberFormatException e){throw new ReglaNegocioException("prioridad","La prioridad debe ser un entero mayor que cero.");}
      }
      if(catalogo.equals("danos")){f.setSector(fila.get("sector").trim());f.setDescripcion(fila.get("descripcion").trim());}
      Optional<? extends CatalogoMaestro> existente=catalogos.buscarCodigo(catalogo,f.getCodigo());
      if(existente.isPresent()){
       if(!iguales(catalogo,catalogos.formulario(existente.get()),f))throw new ReglaNegocioException("El código "+f.getCodigo()+" ya existe con datos diferentes. Corrige el archivo o edita el registro desde el catálogo.");
       omitidos++;continue;
      }
      catalogos.guardar(catalogo,null,f);importados++;
     }catch(ReglaNegocioException e){errores.add(prefijo+e.getMessage());}
    }
   }
  }catch(ImportacionException e){throw e;}catch(IOException|IllegalArgumentException|UncheckedIOException e){throw new ImportacionException(List.of("No se pudo leer el CSV. Verifica que esté codificado en UTF-8, separado por comas y que sus comillas estén cerradas."));}
  if(!errores.isEmpty())throw new ImportacionException(errores);
  return new ResultadoImportacion(tipo,total,importados,omitidos);
 }

 private boolean iguales(String tipo,CatalogoForm a,CatalogoForm b){
  if(!Objects.equals(a.getCodigo(),b.getCodigo())||!Objects.equals(a.getNombre(),b.getNombre())||!Objects.equals(a.getEstado(),b.getEstado()))return false;
  return switch(tipo){case "ubigeos"->Objects.equals(a.getTipo(),b.getTipo());case "alertas"->Objects.equals(a.getColor(),b.getColor())&&Objects.equals(a.getPrioridad(),b.getPrioridad())&&Objects.equals(a.getDescripcion(),b.getDescripcion());default->Objects.equals(a.getSector(),b.getSector())&&Objects.equals(a.getDescripcion(),b.getDescripcion());};
 }
}
