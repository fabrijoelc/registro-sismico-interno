package pe.isil.sismos.service;
import pe.isil.sismos.dto.ReporteDanoForm;
import pe.isil.sismos.model.*;
import pe.isil.sismos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
@Service
@Transactional(readOnly=true)
public class ReporteDanoService {
 private final SismoService sismos;
 private final TipoDanoRepository tipos;
 private final ReporteDanoRepository reportes;
 private final Validacion validacion;
 public ReporteDanoService(SismoService s,TipoDanoRepository t,ReporteDanoRepository r,Validacion v){sismos=s;tipos=t;reportes=r;validacion=v;}
 public List<ReporteDano> listar(Long sismoId){sismos.obtener(sismoId);return reportes.findBySismoIdOrderByFechaRegistroDescIdDesc(sismoId);}
 @Transactional
 public ReporteDano registrar(Long sismoId,ReporteDanoForm form){
  form.setDescripcion(Validacion.limpiar(form.getDescripcion()));validacion.comprobar(form);
  Sismo s=sismos.obtener(sismoId);
  TipoDano tipo=tipos.findById(form.getTipoDanoId()).filter(CatalogoMaestro::isActivo).orElseThrow(()->new ReglaNegocioException("tipoDanoId","Selecciona un tipo de daño activo del catálogo."));
  ReporteDano dano=new ReporteDano();dano.setSismo(s);dano.setTipoDano(tipo);dano.setCantidadAfectada(form.getCantidadAfectada());dano.setDescripcion(form.getDescripcion());dano.setFechaRegistro(LocalDateTime.now(ZoneId.of("America/Lima")));
  return reportes.saveAndFlush(dano);
 }
}

