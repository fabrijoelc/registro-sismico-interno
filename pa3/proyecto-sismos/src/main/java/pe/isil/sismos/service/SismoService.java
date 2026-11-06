package pe.isil.sismos.service;

import pe.isil.sismos.dto.SismoForm;
import pe.isil.sismos.model.*;
import pe.isil.sismos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@Transactional(readOnly=true)
public class SismoService {
 private final SismoRepository sismos;
 private final UbigeoRepository ubigeos;
 private final TipoAlertaRepository alertas;
 private final ReporteDanoRepository danos;
 private final Validacion validacion;
 public SismoService(SismoRepository s,UbigeoRepository u,TipoAlertaRepository a,ReporteDanoRepository d,Validacion v){sismos=s;ubigeos=u;alertas=a;danos=d;validacion=v;}
 public List<Sismo> listar(String codigo,String estado){
  EstadoSismo estadoFiltro=Validacion.limpiar(estado).isEmpty()?null:Validacion.valor(EstadoSismo.class,estado,"estado");
  return sismos.buscar(Validacion.limpiar(codigo),estadoFiltro);
 }
 public Sismo obtener(Long id){return sismos.findByIdAndActivoTrue(id).orElseThrow(()->new NoEncontradoException("El reporte sísmico no existe o fue dado de baja."));}
 @Transactional
 public Sismo guardar(Long id,SismoForm form){
  form.setCodigo(Validacion.codigo(form.getCodigo()));form.setReferencia(Validacion.limpiar(form.getReferencia()));form.setObservaciones(Validacion.limpiar(form.getObservaciones()));
  validacion.comprobar(form);
  if(form.getFechaHora().isBefore(LocalDateTime.of(1900,1,1,0,0)))throw new ReglaNegocioException("fechaHora","La fecha y hora no pueden ser anteriores al año 1900.");
  if(form.getFechaHora().isAfter(LocalDateTime.now(ZoneId.of("America/Lima"))))throw new ReglaNegocioException("fechaHora","La fecha y hora no pueden estar en el futuro.");
  if((form.getLatitud()==null)!=(form.getLongitud()==null))throw new ReglaNegocioException("longitud","Ingresa latitud y longitud juntas, o deja ambas vacías.");
  Optional<Sismo> repetido=sismos.findByCodigo(form.getCodigo());
  if(repetido.isPresent()&&!Objects.equals(repetido.get().getId(),id))throw new ReglaNegocioException("codigo","Este código ya está registrado, incluso si el reporte fue dado de baja.");
  Ubigeo ubigeo=ubigeos.findById(form.getUbigeoId()).filter(CatalogoMaestro::isActivo).orElseThrow(()->new ReglaNegocioException("ubigeoId","Selecciona un ubigeo activo del catálogo."));
  TipoAlerta alerta=alertas.findById(form.getTipoAlertaId()).filter(CatalogoMaestro::isActivo).orElseThrow(()->new ReglaNegocioException("tipoAlertaId","Selecciona un tipo de alerta activo del catálogo."));
  Sismo s=id==null?new Sismo():obtener(id);
  s.setCodigo(form.getCodigo());s.setFechaHora(form.getFechaHora());s.setMagnitud(form.getMagnitud());s.setProfundidadKm(form.getProfundidadKm());s.setReferencia(form.getReferencia());s.setUbigeo(ubigeo);s.setTipoAlerta(alerta);
  s.setCanal(Validacion.valor(CanalRecepcion.class,form.getCanal(),"canal"));s.setEstado(Validacion.valor(EstadoSismo.class,form.getEstado(),"estado"));
  s.setLatitud(form.getLatitud());s.setLongitud(form.getLongitud());s.setObservaciones(form.getObservaciones());
  return sismos.saveAndFlush(s);
 }
 @Transactional
 public void darBaja(Long id){
  Sismo s=obtener(id);
  if(!danos.findBySismoIdOrderByFechaRegistroDescIdDesc(id).isEmpty())throw new ReglaNegocioException("Este sismo tiene reportes de daño asociados y debe conservarse disponible para consulta.");
  s.setActivo(false);sismos.saveAndFlush(s);
 }
 public SismoForm formulario(Sismo s){
  SismoForm f=new SismoForm();f.setCodigo(s.getCodigo());f.setFechaHora(s.getFechaHora());f.setMagnitud(s.getMagnitud());f.setProfundidadKm(s.getProfundidadKm());f.setReferencia(s.getReferencia());f.setUbigeoId(s.getUbigeo().getId());f.setTipoAlertaId(s.getTipoAlerta().getId());f.setCanal(s.getCanal().name());f.setEstado(s.getEstado().name());f.setLatitud(s.getLatitud());f.setLongitud(s.getLongitud());f.setObservaciones(s.getObservaciones());return f;
 }
}

