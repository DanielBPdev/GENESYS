package com.asopagos.clienteanibol.enums;

public enum TipoNovedadServicioEnum {
    
    NOVEDAD_01("novedad_01", "01"),
    NOVEDAD_02("novedad_02", "2"),
    NOVEDAD_04("novedad_04", "4"),
    NOVEDAD_06("novedad_06", "6"),
    NOVEDAD_10("novedad_10", "10");
    
    private final String nombre;
    private final String valor;
    
    /**
     * @param nombre
     * @param valor
     */
    private TipoNovedadServicioEnum(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }
}
