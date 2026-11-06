package pe.isil.sismos.model;
public enum EstadoMaestro { ACTIVO("Activo"), INACTIVO("Inactivo"); private final String etiqueta; EstadoMaestro(String e){etiqueta=e;} public String getEtiqueta(){return etiqueta;} @Override public String toString(){return etiqueta;} }
