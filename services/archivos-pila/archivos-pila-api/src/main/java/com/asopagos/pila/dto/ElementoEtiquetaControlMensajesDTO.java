package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.pila.TipoRegistroArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la versión resumida del contenido de 
 * una etiqueta de campo PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */

public class ElementoEtiquetaControlMensajesDTO implements Serializable {
    
    /** Nombre del campo de acuerdo a la configuración de lectura de PILA */
    private String nombreCampo;
    
    /** Código del campo en etiqueta */
    private String codigoCampo;
    
    /** Descripción del campo */
    private String descripcionCampo;
    
    /** Tipo de registro al cual corresponde el campo */
    private TipoRegistroArchivoEnum tipoRegistro;

    /**
     * Constructor de clase con campos
     * */
    public ElementoEtiquetaControlMensajesDTO(String nombreCampo, String codigoCampo, String descripcionCampo,
            TipoRegistroArchivoEnum tipoRegistro) {
        this.nombreCampo = nombreCampo;
        this.codigoCampo = codigoCampo;
        this.descripcionCampo = descripcionCampo;
        this.tipoRegistro = tipoRegistro;
    }

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
     * @return the codigoCampo
     */
    public String getCodigoCampo() {
        return codigoCampo;
    }

    /**
     * @param codigoCampo the codigoCampo to set
     */
    public void setCodigoCampo(String codigoCampo) {
        this.codigoCampo = codigoCampo;
    }

    /**
     * @return the descripcionCampo
     */
    public String getDescripcionCampo() {
        return descripcionCampo;
    }

    /**
     * @param descripcionCampo the descripcionCampo to set
     */
    public void setDescripcionCampo(String descripcionCampo) {
        this.descripcionCampo = descripcionCampo;
    }

    /**
     * @return the tipoRegistro
     */
    public TipoRegistroArchivoEnum getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * @param tipoRegistro the tipoRegistro to set
     */
    public void setTipoRegistro(TipoRegistroArchivoEnum tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
