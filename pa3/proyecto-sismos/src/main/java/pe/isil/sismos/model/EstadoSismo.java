package pe.isil.sismos.model;
public enum EstadoSismo { PENDIENTE("Pendiente"), EN_VERIFICACION("En verificación"), VERIFICADO("Verificado"), DESCARTADO("Descartado"); private final String etiqueta; EstadoSismo(String e){etiqueta=e;} public String getEtiqueta(){return etiqueta;} @Override public String toString(){return etiqueta;} }
