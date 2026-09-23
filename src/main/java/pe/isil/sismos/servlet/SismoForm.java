package pe.isil.sismos.servlet;

import jakarta.servlet.http.HttpServletRequest;
import pe.isil.sismos.model.EstadoSismo;
import pe.isil.sismos.model.Sismo;
import pe.isil.sismos.repository.SismoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SismoForm {
    private final Map<String, String> valores = new LinkedHashMap<>();
    private final Map<String, String> errores = new LinkedHashMap<>();
    private Sismo sismo;

    public static SismoForm vacio() { return new SismoForm(); }

    public static SismoForm desde(Sismo sismo) {
        SismoForm form = new SismoForm();
        form.valores.put("codigo", sismo.getCodigo());
        form.valores.put("fechaHora", sismo.getFechaHoraFormulario());
        form.valores.put("ubicacion", sismo.getUbicacion());
        form.valores.put("magnitud", sismo.getMagnitud().toPlainString());
        form.valores.put("profundidadKm", sismo.getProfundidadKm().toPlainString());
        form.valores.put("estado", sismo.getEstado().name());
        form.valores.put("observaciones", sismo.getObservaciones());
        return form;
    }

    public static SismoForm validar(HttpServletRequest request, SismoRepository repository, Long idActual) {
        SismoForm form = new SismoForm();
        for (String campo : new String[]{"codigo", "fechaHora", "ubicacion", "magnitud", "profundidadKm", "estado", "observaciones"}) {
            String valor = request.getParameter(campo);
            form.valores.put(campo, valor == null ? "" : valor.trim());
        }
        String codigo = form.valores.get("codigo");
        String ubicacion = form.valores.get("ubicacion");
        if (codigo.isBlank()) form.errores.put("codigo", "Ingresa el código.");
        else if (codigo.length() > 40) form.errores.put("codigo", "Máximo 40 caracteres.");
        else if (repository.existeCodigo(codigo, idActual)) form.errores.put("codigo", "El código ya está registrado.");
        if (ubicacion.isBlank()) form.errores.put("ubicacion", "Ingresa la ubicación.");
        else if (ubicacion.length() > 120) form.errores.put("ubicacion", "Máximo 120 caracteres.");
        if (form.valores.get("observaciones").length() > 500) form.errores.put("observaciones", "Máximo 500 caracteres.");

        LocalDateTime fechaHora = null;
        try { fechaHora = LocalDateTime.parse(form.valores.get("fechaHora")); }
        catch (DateTimeParseException ex) { form.errores.put("fechaHora", "Ingresa una fecha y hora válidas."); }

        BigDecimal magnitud = decimal(form, "magnitud", "magnitud");
        if (magnitud != null && (magnitud.compareTo(BigDecimal.ZERO) < 0 || magnitud.compareTo(new BigDecimal("10")) > 0))
            form.errores.put("magnitud", "Usa un valor entre 0 y 10.");
        BigDecimal profundidad = decimal(form, "profundidadKm", "profundidad");
        if (profundidad != null && (profundidad.compareTo(BigDecimal.ZERO) < 0 || profundidad.compareTo(new BigDecimal("1000")) > 0))
            form.errores.put("profundidadKm", "Usa un valor entre 0 y 1000 km.");

        EstadoSismo estado = null;
        try { estado = EstadoSismo.valueOf(form.valores.get("estado")); }
        catch (IllegalArgumentException ex) { form.errores.put("estado", "Selecciona un estado válido."); }

        if (form.errores.isEmpty())
            form.sismo = new Sismo(0, codigo, fechaHora, ubicacion, magnitud, profundidad,
                    estado, form.valores.get("observaciones"), true);
        return form;
    }

    private static BigDecimal decimal(SismoForm form, String campo, String etiqueta) {
        try { return new BigDecimal(form.valores.get(campo)); }
        catch (NumberFormatException ex) { form.errores.put(campo, "Ingresa una " + etiqueta + " numérica."); return null; }
    }

    public Map<String, String> getValores() { return valores; }
    public Map<String, String> getErrores() { return errores; }
    public boolean isValido() { return errores.isEmpty(); }
    public Sismo getSismo() { return sismo; }
}
