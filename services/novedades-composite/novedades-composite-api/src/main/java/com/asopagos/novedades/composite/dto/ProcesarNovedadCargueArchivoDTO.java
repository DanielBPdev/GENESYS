package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;

public class ProcesarNovedadCargueArchivoDTO implements Serializable{
    
    /**
     * Variable serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private Date fechaActual;
    
    private InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO;
    
    String numeroRadicado;
    
    public ProcesarNovedadCargueArchivoDTO() {
    }

    public ProcesarNovedadCargueArchivoDTO(Date fechaActual,
            InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO, String numeroRadicado) {
        super();
        this.fechaActual = fechaActual;
        this.informacionActualizacionNovedadDTO = informacionActualizacionNovedadDTO;
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the informacionActualizacionNovedadDTO
     */
    public InformacionActualizacionNovedadDTO getInformacionActualizacionNovedadDTO() {
        return informacionActualizacionNovedadDTO;
    }

    /**
     * @param informacionActualizacionNovedadDTO the informacionActualizacionNovedadDTO to set
     */
    public void setInformacionActualizacionNovedadDTO(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO) {
        this.informacionActualizacionNovedadDTO = informacionActualizacionNovedadDTO;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

}
