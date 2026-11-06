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
@RequestMapping("/catalogos")
public class CatalogoController {
 private final CatalogoService catalogos;
 public CatalogoController(CatalogoService c){catalogos=c;}
 @GetMapping public String inicio(){return "redirect:/catalogos/ubigeos";}
 @GetMapping("/{catalogo}")
 public String lista(@PathVariable String catalogo,Model model){model.addAttribute("items",catalogos.listar(catalogo));preparar(model,catalogo,null);return "catalogos/lista";}
 @GetMapping("/{catalogo}/nuevo")
 public String nuevo(@PathVariable String catalogo,Model model){preparar(model,catalogo,null);model.addAttribute("form",new CatalogoForm());return "catalogos/formulario";}
 @PostMapping("/{catalogo}/nuevo")
 public String crear(@PathVariable String catalogo,@Valid @ModelAttribute("form")CatalogoForm form,BindingResult result,Model model,RedirectAttributes flash){
  preparar(model,catalogo,null);
  if(!result.hasErrors())try{catalogos.guardar(catalogo,null,form);flash.addFlashAttribute("mensaje","El registro se guardó correctamente.");return "redirect:/catalogos/"+catalogo;}catch(ReglaNegocioException e){FormErrors.agregar(result,e);}
  return "catalogos/formulario";
 }
 @GetMapping("/{catalogo}/{id}/editar")
 public String editar(@PathVariable String catalogo,@PathVariable Long id,Model model){model.addAttribute("form",catalogos.formulario(catalogos.obtener(catalogo,id)));preparar(model,catalogo,id);return "catalogos/formulario";}
 @PostMapping("/{catalogo}/{id}/editar")
 public String actualizar(@PathVariable String catalogo,@PathVariable Long id,@Valid @ModelAttribute("form")CatalogoForm form,BindingResult result,Model model,RedirectAttributes flash){
  catalogos.obtener(catalogo,id);preparar(model,catalogo,id);
  if(!result.hasErrors())try{catalogos.guardar(catalogo,id,form);flash.addFlashAttribute("mensaje","Los cambios del catálogo se guardaron correctamente.");return "redirect:/catalogos/"+catalogo;}catch(ReglaNegocioException e){FormErrors.agregar(result,e);}
  return "catalogos/formulario";
 }
 @GetMapping("/{catalogo}/{id}/estado")
 public String estado(@PathVariable String catalogo,@PathVariable Long id,Model model){CatalogoMaestro item=catalogos.obtener(catalogo,id);preparar(model,catalogo,id);model.addAttribute("item",item);model.addAttribute("estadoDestino",item.isActivo()?EstadoMaestro.INACTIVO:EstadoMaestro.ACTIVO);return "catalogos/estado";}
 @PostMapping("/{catalogo}/{id}/estado")
 public String cambiarEstado(@PathVariable String catalogo,@PathVariable Long id,RedirectAttributes flash){catalogos.cambiarEstado(catalogo,id);flash.addFlashAttribute("mensaje","El estado del catálogo fue actualizado.");return "redirect:/catalogos/"+catalogo;}
 private void preparar(Model model,String tipo,Long id){
  String titulo=switch(tipo){case "ubigeos"->"Ubigeos";case "alertas"->"Tipos de alerta";case "danos"->"Tipos de daño";default->throw new NoEncontradoException("El catálogo solicitado no existe.");};
  model.addAttribute("catalogo",tipo);model.addAttribute("titulo",titulo);model.addAttribute("singular",switch(tipo){case "ubigeos"->"ubigeo";case "alertas"->"tipo de alerta";default->"tipo de daño";});
  model.addAttribute("id",id);model.addAttribute("editando",id!=null);model.addAttribute("tiposUbigeo",TipoUbigeo.values());model.addAttribute("colores",ColorAlerta.values());model.addAttribute("sectores",SectorDano.values());model.addAttribute("estadosMaestro",EstadoMaestro.values());
 }
}
