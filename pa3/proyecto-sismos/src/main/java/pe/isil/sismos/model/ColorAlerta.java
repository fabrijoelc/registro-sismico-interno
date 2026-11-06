package pe.isil.sismos.model;
public enum ColorAlerta { VERDE("Verde"), AMARILLO("Amarillo"), NARANJA("Naranja"), ROJO("Rojo"); private final String etiqueta; ColorAlerta(String e){etiqueta=e;} public String getEtiqueta(){return etiqueta;} @Override public String toString(){return etiqueta;} }
