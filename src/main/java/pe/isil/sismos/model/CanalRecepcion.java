package pe.isil.sismos.model;
public enum CanalRecepcion {
    LLAMADA("Llamada"), CORREO("Correo"), MENSAJE("Mensaje");
    private final String etiqueta;
    CanalRecepcion(String etiqueta) { this.etiqueta=etiqueta; }
    public String getEtiqueta() { return etiqueta; }
}
