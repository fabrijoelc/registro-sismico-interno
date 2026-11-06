package pe.isil.sismos.web;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
@Configuration
public class SeguridadWebConfig implements WebMvcConfigurer {
 public static String token(HttpServletRequest request){
  HttpSession session=request.getSession();String token=(String)session.getAttribute("csrf");
  if(token==null){token=UUID.randomUUID().toString();session.setAttribute("csrf",token);}return token;
 }
 @Override public void addInterceptors(InterceptorRegistry registry){
  registry.addInterceptor(new HandlerInterceptor(){
   @Override public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
    response.setHeader("X-Content-Type-Options","nosniff");response.setHeader("Referrer-Policy","strict-origin-when-cross-origin");response.setHeader("Cache-Control","no-store");
    if("POST".equals(request.getMethod())){
     String esperado=token(request),recibido=request.getParameter("_csrf");
     if(recibido==null||!MessageDigest.isEqual(esperado.getBytes(StandardCharsets.UTF_8),recibido.getBytes(StandardCharsets.UTF_8)))throw new SolicitudInvalidaException("La sesión del formulario venció. Abre la pantalla nuevamente e inténtalo otra vez.");
    }return true;
   }
  }).excludePathPatterns("/css/**","/js/**","/images/**","/error");
 }
}
