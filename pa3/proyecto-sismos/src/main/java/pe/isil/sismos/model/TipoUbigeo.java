package pe.isil.sismos.model;
public enum TipoUbigeo { DEPARTAMENTO("Departamento"), PROVINCIA_CONSTITUCIONAL("Provincia constitucional"); private final String etiqueta; TipoUbigeo(String e){etiqueta=e;} public String getEtiqueta(){return etiqueta;} @Override public String toString(){return etiqueta;} }
