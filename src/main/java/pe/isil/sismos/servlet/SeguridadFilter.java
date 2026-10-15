package pe.isil.sismos.servlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HexFormat;

/** UTF-8 y token CSRF: evita mutaciones mediante peticiones de sitios ajenos. No reemplaza autenticación. */
@WebFilter("/*")
public class SeguridadFilter implements Filter {
    private static final SecureRandom RANDOM=new SecureRandom();
    @Override public void doFilter(ServletRequest entrada,ServletResponse salida,FilterChain cadena) throws IOException,ServletException {
        HttpServletRequest req=(HttpServletRequest)entrada;
        HttpServletResponse res=(HttpServletResponse)salida;
        req.setCharacterEncoding("UTF-8"); res.setCharacterEncoding("UTF-8");
        res.setHeader("X-Content-Type-Options","nosniff");
        res.setHeader("X-Frame-Options","SAMEORIGIN");
        HttpSession sesion=req.getSession();
        String token;
        synchronized(sesion) {
            token=(String)sesion.getAttribute("csrf");
            if(token==null) {
                byte[] bytes=new byte[32]; RANDOM.nextBytes(bytes);
                token=HexFormat.of().formatHex(bytes); sesion.setAttribute("csrf",token);
            }
        }
        if(req.getMethod().equals("POST") && !token.equals(req.getParameter("csrf"))) {
            res.sendError(403,"La sesión del formulario no es válida. Recarga la página."); return;
        }
        cadena.doFilter(req,res);
    }
}
