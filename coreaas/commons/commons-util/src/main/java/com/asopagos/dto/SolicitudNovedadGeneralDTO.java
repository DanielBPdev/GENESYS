package com.asopagos.dto;

import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Contiene la información basica de cualquier tiop de novedad
 * @author mamonroy
 *
 */
public class SolicitudNovedadGeneralDTO {
    
    private Long idSolicitudGlobal;
    
    private String numeroRadicado;
    
    private EstadoSolicitudNovedadEnum estado;
    
    private TipoTransaccionEnum tipoTransaccion;
    
    public SolicitudNovedadGeneralDTO() {
    }

    public SolicitudNovedadGeneralDTO(Long idSolicitudGlobal, String numeroRadicado, EstadoSolicitudNovedadEnum estado,
            TipoTransaccionEnum tipoTransaccion) {
        super();
        this.idSolicitudGlobal = idSolicitudGlobal;
        this.numeroRadicado = numeroRadicado;
        this.estado = estado;
        this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
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

    /**
     * @return the estado
     */
    public EstadoSolicitudNovedadEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoSolicitudNovedadEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
}
