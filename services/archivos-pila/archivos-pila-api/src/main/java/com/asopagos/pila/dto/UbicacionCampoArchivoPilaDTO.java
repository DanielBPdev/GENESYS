package com.asopagos.pila.dto;

/**
 * <b>Descripcion:</b> DTO empleado para el paso de información de la configuración de la lectura de 
 * archivos en el componente FileProccesor <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-391, HU-211-393, HU-211-407 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class UbicacionCampoArchivoPilaDTO {
    /** Nombre del campo */
    private String nombreCampo;
    
    /** Poscición inicial del campo */
    private Short posicionInicial;
    
    /** Posición final del campo */
    private Short posicionFinal;
    
    /** Nombre interno del campo */
    private String nombreInterno;

    /**
     * @return the nombreCampo
     */
    public String getNombreCampo() {
        return nombreCampo;
    }

    /**
     * @param nombreCampo the nombreCampo to set
     */
    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    /**
     * @return the posicionInicial
     */
    public Short getPosicionInicial() {
        return posicionInicial;
    }

    /**
     * @param posicionInicial the posicionInicial to set
     */
    public void setPosicionInicial(Short posicionInicial) {
        this.posicionInicial = posicionInicial;
    }

    /**
     * @return the posicionFinal
     */
    public Short getPosicionFinal() {
        return posicionFinal;
    }

    /**
     * @param posicionFinal the posicionFinal to set
     */
    public void setPosicionFinal(Short posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    /**
     * @return the nombreInterno
     */
    public String getNombreInterno() {
        return nombreInterno;
    }

    /**
     * @param nombreInterno the nombreInterno to set
     */
    public void setNombreInterno(String nombreInterno) {
        this.nombreInterno = nombreInterno;
    }
}
