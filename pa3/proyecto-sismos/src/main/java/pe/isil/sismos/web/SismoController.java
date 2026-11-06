package pe.isil.sismos.web;
import jakarta.validation.Valid;
import pe.isil.sismos.dto.*;
import pe.isil.sismos.model.*;
import pe.isil.sismos.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sismos")
public class SismoController {
 private final SismoService sismos;
 private final CatalogoService catalogos;
 private final ReporteDanoService danos;
 public SismoController(SismoService s,CatalogoService c,ReporteDanoService d){sismos=s;catalogos=c;danos=d;}
 @GetMapping
 public String listar(@RequestParam(defaultValue="")String q,@RequestParam(defaultValue="")String estado,Model model){
  var lista=sismos.listar(q,estado);model.addAttribute("sismos",lista);model.addAttribute("q",q);model.addAttribute("estadoSeleccionado",estado);model.addAttribute("estados",EstadoSismo.values());model.addAttribute("total",lista.size());return "sismos/lista";
 }
 @GetMapping("/nuevo")
 public String nuevo(Model model){model.addAttribute("form",new SismoForm());preparar(model,null);return "sismos/formulario";}
 @PostMapping("/nuevo")
 public String crear(@Valid @ModelAttribute("form")SismoForm form,BindingResult result,Model model,RedirectAttributes flash){
  if(!result.hasErrors())try{Sismo s=sismos.guardar(null,form);flash.addFlashAttribute("mensaje","El reporte sísmico se registró correctamente.");return "redirect:/sismos/"+s.getId();}catch(ReglaNegocioException e){FormErrors.agregar(result,e);}
  preparar(model,null);return "sismos/formulario";
 }
 @GetMapping("/{id}")
 public String detalle(@PathVariable Long id,Model model){model.addAttribute("sismo",sismos.obtener(id));model.addAttribute("danos",danos.listar(id));return "sismos/detalle";}
 @GetMapping("/{id}/editar")
 public String editar(@PathVariable Long id,Model model){model.addAttribute("form",sismos.formulario(sismos.obtener(id)));preparar(model,id);return "sismos/formulario";}
 @PostMapping("/{id}/editar")
 public String actualizar(@PathVariable Long id,@Valid @ModelAttribute("form")SismoForm form,BindingResult result,Model model,RedirectAttributes flash){
  sismos.obtener(id);
  if(!result.hasErrors())try{sismos.guardar(id,form);flash.addFlashAttribute("mensaje","Los cambios del reporte se guardaron correctamente.");return "redirect:/sismos/"+id;}catch(ReglaNegocioException e){FormErrors.agregar(result,e);}
  preparar(model,id);return "sismos/formulario";
 }
 @GetMapping("/{id}/baja")
 public String confirmarBaja(@PathVariable Long id,Model model){model.addAttribute("sismo",sismos.obtener(id));return "sismos/baja";}
 @PostMapping("/{id}/baja")
 public String baja(@PathVariable Long id,RedirectAttributes flash){sismos.darBaja(id);flash.addFlashAttribute("mensaje","El reporte fue dado de baja. Sus datos se conservan en el historial.");return "redirect:/sismos";}
 @GetMapping("/{id}/danos/nuevo")
 public String nuevoDano(@PathVariable Long id,Model model){model.addAttribute("form",new ReporteDanoForm());prepararDano(model,id);return "sismos/dano-formulario";}
 @PostMapping("/{id}/danos/nuevo")
 public String crearDano(@PathVariable Long id,@Valid @ModelAttribute("form")ReporteDanoForm form,BindingResult result,Model model,RedirectAttributes flash){
  sismos.obtener(id);
  if(!result.hasErrors())try{danos.registrar(id,form);flash.addFlashAttribute("mensaje","El reporte de daño quedó asociado al sismo.");return "redirect:/sismos/"+id+"#danos";}catch(ReglaNegocioException e){FormErrors.agregar(result,e);}
  prepararDano(model,id);return "sismos/dano-formulario";
 }
 private void preparar(Model model,Long id){model.addAttribute("id",id);model.addAttribute("editando",id!=null);model.addAttribute("ubigeos",catalogos.ubigeosActivos());model.addAttribute("tiposAlerta",catalogos.alertasActivas());model.addAttribute("canales",CanalRecepcion.values());model.addAttribute("estados",EstadoSismo.values());}
 private void prepararDano(Model model,Long id){model.addAttribute("sismo",sismos.obtener(id));model.addAttribute("tiposDano",catalogos.danosActivos());}
}
