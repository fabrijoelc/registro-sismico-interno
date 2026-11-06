package pe.isil.sismos.model;
public enum CanalRecepcion { LLAMADA("Llamada"), CORREO("Correo"), MENSAJE("Mensaje"); private final String etiqueta; CanalRecepcion(String e){etiqueta=e;} public String getEtiqueta(){return etiqueta;} @Override public String toString(){return etiqueta;} }
