package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos de control de proceso de descarga por FTP<br/>
 * <b>Módulo:</b> Asopagos - HU-211-387 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosProcesoFtpDTO implements Serializable {
    private static final long serialVersionUID = -2700816747637774435L;
    
    /** ID del proceso de carga de archivos */
    private Long idProceso;
    
    /** Nombre del usuario que inicia el proceso */
    private String usuario;
    
    /** Tipo de proceso de carga de archivos */
    private TipoCargaArchivoEnum tipoCarga;
    
    /**
     * Constructor por campos
     * @param idProceso
     * @param usuario
     * @param tipoCarga
     */
    public DatosProcesoFtpDTO(Long idProceso, String usuario, TipoCargaArchivoEnum tipoCarga) {
        super();
        this.idProceso = idProceso;
        this.usuario = usuario;
        this.tipoCarga = tipoCarga;
    }

    /**
     * @return the idProceso
     */
    public Long getIdProceso() {
        return idProceso;
    }

    /**
     * @param idProceso the idProceso to set
     */
    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the tipoCarga
     */
    public TipoCargaArchivoEnum getTipoCarga() {
        return tipoCarga;
    }

    /**
     * @param tipoCarga the tipoCarga to set
     */
    public void setTipoCarga(TipoCargaArchivoEnum tipoCarga) {
        this.tipoCarga = tipoCarga;
    }
}
