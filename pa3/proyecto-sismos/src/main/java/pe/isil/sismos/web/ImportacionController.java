package pe.isil.sismos.web;
import pe.isil.sismos.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/importaciones")
public class ImportacionController {
 private final ImportacionService importaciones;
 public ImportacionController(ImportacionService i){importaciones=i;}
 @GetMapping public String inicio(Model model){model.addAttribute("tipoSeleccionado","UBIGEO");return "importaciones/index";}
 @PostMapping public String importar(@RequestParam String tipo,@RequestParam MultipartFile archivo,Model model)throws IOException{
  model.addAttribute("tipoSeleccionado",tipo);
  try{model.addAttribute("resultado",importaciones.importar(tipo,archivo.getBytes()));}
  catch(ImportacionException e){model.addAttribute("errorImportacion",e.getMessage());model.addAttribute("errores",e.getErrores());}
  return "importaciones/index";
 }
}
