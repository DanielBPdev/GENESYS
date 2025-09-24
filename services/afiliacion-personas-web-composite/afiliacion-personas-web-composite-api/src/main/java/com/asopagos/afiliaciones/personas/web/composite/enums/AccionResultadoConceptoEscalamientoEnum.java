package com.asopagos.afiliaciones.personas.web.composite.enums;

/**
 * <b>Descripción:</b> Enum con las posibles acciones al momento de la consulta del resultado de analisis
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate@heinsohn.com.co</a>
 */
public enum AccionResultadoConceptoEscalamientoEnum {

    EXITOSO(1),
    
    FALLIDO(2);
    
    /**
     * Valor resultado
     */
    private Integer resultadoAnalisis;
    
    /**
     * Metodo Constructor
     * @param resultadoAnalisis
     */
    private AccionResultadoConceptoEscalamientoEnum(Integer resultadoAnalisis) {
        this.resultadoAnalisis=resultadoAnalisis;
    }

    /**
     * @return the resultadoAnalisis
     */
    public Integer getResultadoAnalisis() {
        return resultadoAnalisis;
    }

    /**
     * @param resultadoAnalisis the resultadoAnalisis to set
     */
    public void setResultadoAnalisis(Integer resultadoAnalisis) {
        this.resultadoAnalisis = resultadoAnalisis;
    }
}
