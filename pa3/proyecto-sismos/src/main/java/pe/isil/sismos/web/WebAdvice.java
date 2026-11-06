package pe.isil.sismos.web;
import jakarta.servlet.http.HttpServletRequest;
import pe.isil.sismos.service.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class WebAdvice {
 @ModelAttribute("csrf") public String csrf(HttpServletRequest request){return SeguridadWebConfig.token(request);}
 @ExceptionHandler(NoEncontradoException.class)
 public ModelAndView noEncontrado(NoEncontradoException e){return error(HttpStatus.NOT_FOUND,"No encontramos esta página",e.getMessage());}
 @ExceptionHandler(ReglaNegocioException.class)
 public ModelAndView regla(ReglaNegocioException e){return error(HttpStatus.CONFLICT,"No se pudo completar la operación",e.getMessage());}
 @ExceptionHandler(SolicitudInvalidaException.class)
 public ModelAndView solicitud(SolicitudInvalidaException e){return error(HttpStatus.FORBIDDEN,"Vuelve a abrir el formulario",e.getMessage());}
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ModelAndView duplicado(DataIntegrityViolationException e){return error(HttpStatus.CONFLICT,"No se pudo guardar el registro","El código ya existe o un dato relacionado cambió mientras editabas. Revisa los datos e inténtalo nuevamente.");}
 @ExceptionHandler(MaxUploadSizeExceededException.class)
 public ModelAndView tamanio(MaxUploadSizeExceededException e){return error(HttpStatus.PAYLOAD_TOO_LARGE,"El archivo es demasiado grande","Elige un archivo CSV de hasta 2 MB.");}
 private ModelAndView error(HttpStatus status,String titulo,String mensaje){ModelAndView mv=new ModelAndView("error/domain");mv.setStatus(status);mv.addObject("titulo",titulo);mv.addObject("mensaje",mensaje);mv.addObject("error",mensaje);mv.addObject("codigo",status.value());return mv;}
}
