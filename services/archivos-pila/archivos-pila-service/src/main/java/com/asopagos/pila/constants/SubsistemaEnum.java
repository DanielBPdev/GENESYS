package com.asopagos.pila.constants;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los subsistemas a los cuales se aporta definidos en el 
 * Decreto 2388 de 2016 <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum SubsistemaEnum {
    SUBSISTEMA_SAL ("SAL", "Salud"),
    SUBSISTEMA_PEN ("PEN", "Pensiones"),
    SUBSISTEMA_ARL ("ARL", "Administradoras de riesgos laborales"),
    SUBSISTEMA_CCF ("CCF", "Cajas de Compensación Familiar"),
    SUBSISTEMA_SEN ("SEN", "SENA"),
    SUBSISTEMA_ICBF ("ICBF", "Instituto Colombiano de Bienestar Familiar"),
    SUBSISTEMA_ESAP ("ESAP", "Escuela Superior de Administración Pública"),
    SUBSISTEMA_MIN ("MIN", "Ministerio de Educación Nacional");

    
    /** Código */
    private String codigo;
    
    /** Descripción del tipo */
    private String descripcion;
    
    /** Constructor de la enumeración */
    private SubsistemaEnum (String codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
