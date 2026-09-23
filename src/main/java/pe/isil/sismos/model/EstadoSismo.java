package pe.isil.sismos.model;

public enum EstadoSismo {
    PENDIENTE("Pendiente"),
    EN_VERIFICACION("En verificación"),
    VERIFICADO("Verificado"),
    DESCARTADO("Descartado");

    private final String etiqueta;

    EstadoSismo(String etiqueta) { this.etiqueta = etiqueta; }
    public String getEtiqueta() { return etiqueta; }
}
