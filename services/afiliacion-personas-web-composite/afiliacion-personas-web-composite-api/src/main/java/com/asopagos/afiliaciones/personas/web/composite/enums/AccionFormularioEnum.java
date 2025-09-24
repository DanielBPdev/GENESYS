package com.asopagos.afiliaciones.personas.web.composite.enums;

/**
 * <b>Descripción:</b> Enum con las posibles acciones al momento de imprimir el formulario de afiliacion
 * independiente o pensionado
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate@heinsohn.com.co</a>
 */
public enum AccionFormularioEnum {

    CANCELAR(1),
    
    CONTINUAR(2);
    
    private int valor;

    private AccionFormularioEnum(int valor) {
        this.valor = valor;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
    
}
