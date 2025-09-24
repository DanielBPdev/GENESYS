package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.pila.TipoRegistroArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la versión formateada de un mensaje de 
 * error de estructura del componente FileProcessing<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */

public class ComposicionMensajeEstructuraDTO implements Serializable {
    private static final long serialVersionUID = 1609644716350006262L;

    /** Tipo del registro en el que se presenta el mensaje*/
    private TipoRegistroArchivoEnum tipoRegistro;
    
    /** Mensaje de estructura formateado */
    private String mensaje;
    
    /** Indicador para establecer que el error es de estructura excluyente */
    private Boolean errorExcluyente = Boolean.FALSE;
    
    /** Indicador de error general de estructura de línea */
    private Boolean errorGeneralEstructura = Boolean.FALSE;

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

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the errorExcluyente
     */
    public Boolean getErrorExcluyente() {
        return errorExcluyente;
    }

    /**
     * @param errorExcluyente the errorExcluyente to set
     */
    public void setErrorExcluyente(Boolean errorExcluyente) {
        this.errorExcluyente = errorExcluyente;
    }

    /**
     * @return the errorGeneralEstructura
     */
    public Boolean getErrorGeneralEstructura() {
        return errorGeneralEstructura;
    }

    /**
     * @param errorGeneralEstructura the errorGeneralEstructura to set
     */
    public void setErrorGeneralEstructura(Boolean errorGeneralEstructura) {
        this.errorGeneralEstructura = errorGeneralEstructura;
    }
}
