package pe.isil.sismos.service;

import pe.isil.sismos.dto.CatalogoForm;
import pe.isil.sismos.model.*;
import pe.isil.sismos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(readOnly=true)
public class CatalogoService {
 private final UbigeoRepository ubigeos;
 private final TipoAlertaRepository alertas;
 private final TipoDanoRepository danos;
 private final SismoRepository sismos;
 private final ReporteDanoRepository reportes;
 private final Validacion validacion;
 public CatalogoService(UbigeoRepository u,TipoAlertaRepository a,TipoDanoRepository d,SismoRepository s,ReporteDanoRepository r,Validacion v){ubigeos=u;alertas=a;danos=d;sismos=s;reportes=r;validacion=v;}

 public List<? extends CatalogoMaestro> listar(String tipo){return switch(tipo){case "ubigeos"->ubigeos.findAllByOrderByCodigoAsc();case "alertas"->alertas.findAllByOrderByPrioridadAscCodigoAsc();case "danos"->danos.findAllByOrderByCodigoAsc();default->throw desconocido();};}
 public CatalogoMaestro obtener(String tipo,Long id){return switch(tipo){case "ubigeos"->ubigeos.findById(id).orElseThrow(()->falta());case "alertas"->alertas.findById(id).orElseThrow(()->falta());case "danos"->danos.findById(id).orElseThrow(()->falta());default->throw desconocido();};}
 public List<Ubigeo> ubigeosActivos(){return ubigeos.findByEstadoOrderByNombreAsc(EstadoMaestro.ACTIVO);}
 public List<TipoAlerta> alertasActivas(){return alertas.findByEstadoOrderByPrioridadAsc(EstadoMaestro.ACTIVO);}
 public List<TipoDano> danosActivos(){return danos.findByEstadoOrderByNombreAsc(EstadoMaestro.ACTIVO);}

 @Transactional
 public CatalogoMaestro guardar(String tipo,Long id,CatalogoForm form){
  normalizar(form);
  validacion.comprobar(form);
  CatalogoMaestro item=id==null?nuevo(tipo):obtener(tipo,id);
  EstadoMaestro estado=Validacion.valor(EstadoMaestro.class,form.getEstado(),"estado");
  Optional<? extends CatalogoMaestro> repetido=buscarCodigo(tipo,form.getCodigo());
  if(repetido.isPresent()&&!Objects.equals(repetido.get().getId(),id))throw new ReglaNegocioException("codigo","Este código ya está registrado. Usa otro.");
  // Se comprueban las relaciones antes de cambiar el estado de un maestro.
  if(id!=null&&item.isActivo()&&estado==EstadoMaestro.INACTIVO)protegerRelaciones(tipo,id);
  item.setCodigo(form.getCodigo());item.setNombre(form.getNombre());item.setEstado(estado);
  if(item instanceof Ubigeo u)u.setTipo(Validacion.valor(TipoUbigeo.class,form.getTipo(),"tipo"));
  if(item instanceof TipoAlerta a){
   a.setColor(Validacion.valor(ColorAlerta.class,form.getColor(),"color"));
   if(form.getPrioridad()==null||form.getPrioridad()<1)throw new ReglaNegocioException("prioridad","La prioridad debe ser un entero mayor que cero.");
   a.setPrioridad(form.getPrioridad());a.setDescripcion(form.getDescripcion());
  }
  if(item instanceof TipoDano d){d.setSector(Validacion.valor(SectorDano.class,form.getSector(),"sector"));d.setDescripcion(form.getDescripcion());}
  return persistir(item);
 }
 @Transactional
 public CatalogoMaestro cambiarEstado(String tipo,Long id){
  CatalogoMaestro item=obtener(tipo,id);
  if(item.isActivo())protegerRelaciones(tipo,id);
  item.setEstado(item.isActivo()?EstadoMaestro.INACTIVO:EstadoMaestro.ACTIVO);
  return persistir(item);
 }
 public CatalogoForm formulario(CatalogoMaestro item){
  CatalogoForm f=new CatalogoForm();f.setCodigo(item.getCodigo());f.setNombre(item.getNombre());f.setEstado(item.getEstado().name());
  if(item instanceof Ubigeo u)f.setTipo(u.getTipo().name());
  if(item instanceof TipoAlerta a){f.setColor(a.getColor().name());f.setPrioridad(a.getPrioridad());f.setDescripcion(a.getDescripcion());}
  if(item instanceof TipoDano d){f.setSector(d.getSector().name());f.setDescripcion(d.getDescripcion());}
  return f;
 }
 private void protegerRelaciones(String tipo,Long id){
  boolean asociado=switch(tipo){case "ubigeos"->sismos.existsByUbigeoIdAndActivoTrue(id);case "alertas"->sismos.existsByTipoAlertaIdAndActivoTrue(id);case "danos"->reportes.existsByTipoDanoId(id);default->throw desconocido();};
  if(asociado)throw new ReglaNegocioException("estado","No se puede desactivar: este registro tiene reportes asociados. Conserva su estado para mantener la trazabilidad.");
 }
 Optional<? extends CatalogoMaestro> buscarCodigo(String tipo,String codigo){return switch(tipo){case "ubigeos"->ubigeos.findByCodigo(codigo);case "alertas"->alertas.findByCodigo(codigo);case "danos"->danos.findByCodigo(codigo);default->throw desconocido();};}
 CatalogoMaestro persistir(CatalogoMaestro item){if(item instanceof Ubigeo u)return ubigeos.saveAndFlush(u);if(item instanceof TipoAlerta a)return alertas.saveAndFlush(a);if(item instanceof TipoDano d)return danos.saveAndFlush(d);throw desconocido();}
 private CatalogoMaestro nuevo(String tipo){return switch(tipo){case "ubigeos"->new Ubigeo();case "alertas"->new TipoAlerta();case "danos"->new TipoDano();default->throw desconocido();};}
 private void normalizar(CatalogoForm f){f.setCodigo(Validacion.codigo(f.getCodigo()));f.setNombre(Validacion.limpiar(f.getNombre()));f.setDescripcion(Validacion.limpiar(f.getDescripcion()));}
 private NoEncontradoException falta(){return new NoEncontradoException("No encontramos el registro del catálogo.");}
 private NoEncontradoException desconocido(){return new NoEncontradoException("El catálogo solicitado no existe.");}
}

