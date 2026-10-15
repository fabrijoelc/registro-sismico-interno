package pe.isil.sismos.servlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pe.isil.sismos.model.Ubicacion;
import java.io.IOException;
import java.util.*;

@WebServlet({"/ubicaciones","/ubicaciones/nueva","/ubicaciones/ver","/ubicaciones/editar","/ubicaciones/eliminar"})
public class UbicacionServlet extends ServletUtil {
    private void mostrar(HttpServletRequest req,HttpServletResponse res,String pagina) throws ServletException,IOException {
        req.getRequestDispatcher("/WEB-INF/views/ubicaciones/"+pagina+".jsp").forward(req,res);
    }
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
        String ruta=req.getServletPath();
        if(ruta.equals("/ubicaciones")) { req.setAttribute("ubicaciones",ubicaciones().listar()); mostrar(req,res,"lista"); return; }
        if(ruta.endsWith("/nueva")) {
            req.setAttribute("valores",Map.of()); req.setAttribute("accion","nueva"); mostrar(req,res,"formulario"); return;
        }
        Long id=leerId(req);
        Ubicacion u=id==null?null:ubicaciones().buscarActivo(id).orElse(null);
        if(u==null) { res.sendError(404,"La ubicación no existe o está inactiva."); return; }
        req.setAttribute("ubicacion",u);
        if(ruta.endsWith("/editar")) {
            req.setAttribute("id",id); req.setAttribute("accion","editar");
            req.setAttribute("valores",Map.of("codigo",u.getCodigo(),"departamento",u.getDepartamento(),"provincia",u.getProvincia(),"distrito",u.getDistrito()));
            mostrar(req,res,"formulario");
        } else mostrar(req,res,ruta.endsWith("/eliminar")?"confirmar":"detalle");
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String ruta=req.getServletPath();
        if(!Set.of("/ubicaciones/nueva","/ubicaciones/editar","/ubicaciones/eliminar").contains(ruta)) { res.sendError(405); return; }
        Long id=leerId(req);
        if(!ruta.endsWith("/nueva") && (id==null || ubicaciones().buscarActivo(id).isEmpty())) { res.sendError(404); return; }
        if(ruta.endsWith("/eliminar")) {
            try {
                if(!ubicaciones().eliminarLogicamente(id)) { res.sendError(404); return; }
                res.sendRedirect(req.getContextPath()+"/ubicaciones?baja=1");
            } catch(IllegalArgumentException ex) {
                res.setStatus(409); req.setAttribute("error",ex.getMessage());
                req.setAttribute("ubicacion",ubicaciones().buscarActivo(id).orElseThrow()); mostrar(req,res,"confirmar");
            }
            return;
        }
        Map<String,String> valores=new LinkedHashMap<>(),errores=new LinkedHashMap<>();
        for(String campo:new String[]{"codigo","departamento","provincia","distrito"}) {
            String valor=Optional.ofNullable(req.getParameter(campo)).orElse("").trim();
            valores.put(campo,valor);
            if(valor.isBlank()) errores.put(campo,"Completa este campo.");
            else if(valor.length()>(campo.equals("codigo")?20:80)) errores.put(campo,"Supera la longitud permitida.");
        }
        Ubicacion datos=new Ubicacion(valores.get("codigo"),valores.get("departamento"),valores.get("provincia"),valores.get("distrito"));
        if(errores.isEmpty()) {
            try {
                if(ruta.endsWith("/nueva")) id=ubicaciones().crear(datos).getId();
                else if(!ubicaciones().actualizar(id,datos)) { res.sendError(404); return; }
                res.sendRedirect(req.getContextPath()+"/ubicaciones/ver?id="+id+"&guardado=1"); return;
            } catch(IllegalArgumentException ex) { errores.put("codigo",ex.getMessage()); }
        }
        req.setAttribute("id",id); req.setAttribute("accion",ruta.endsWith("/nueva")?"nueva":"editar");
        req.setAttribute("valores",valores); req.setAttribute("errores",errores);
        res.setStatus(400); mostrar(req,res,"formulario");
    }
}
