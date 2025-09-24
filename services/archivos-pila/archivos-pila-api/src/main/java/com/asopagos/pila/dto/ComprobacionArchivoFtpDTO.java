package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripcion:</b> DTO empleado para la comprobación de la existencia previa de una archivo
 *  del FTP en el índice de planillas<br/>
 * <b>Módulo:</b> Asopagos - HU-211-387 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ComprobacionArchivoFtpDTO implements Serializable {
    private static final long serialVersionUID = 1637394182336704352L;

    /** Nombre del archivo */
    private String nombreArchivo;
    
    /** Fecha de modificación */
    private Long fechaModificacion;
    
    /** Llave temporal */
    private Long llaveTemporal;
    
    /** Constructor por defecto */
    public ComprobacionArchivoFtpDTO(){
        super();
    }

    /**
     * @param nombreArchivo
     * @param fechaModificacion
     * @param llaveTemporal
     */
    public ComprobacionArchivoFtpDTO(String nombreArchivo, Date fechaModificacion, Long llaveTemporal) {
        super();
        this.nombreArchivo = nombreArchivo;
        this.fechaModificacion = fechaModificacion.getTime();
        this.llaveTemporal = llaveTemporal;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the fechaModificacion
     */
    public Long getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Long fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the llaveTemporal
     */
    public Long getLlaveTemporal() {
        return llaveTemporal;
    }

    /**
     * @param llaveTemporal the llaveTemporal to set
     */
    public void setLlaveTemporal(Long llaveTemporal) {
        this.llaveTemporal = llaveTemporal;
    }
}
