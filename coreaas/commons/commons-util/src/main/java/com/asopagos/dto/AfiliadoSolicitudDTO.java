package com.asopagos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class AfiliadoSolicitudDTO {
    
    private Long idAfiliado;
    
    private Long idSolicitud;
    
    private Long idRolAfiliado;
    
    private String numeroIdentificacion;
    
    private TipoIdentificacionEnum tipoIdentificacion;
    
    public AfiliadoSolicitudDTO() {
    }
    
    /**
     * @param idAfiliado
     * @param idSolicitud
     * @param idRolAfiliado
     * @param numeroIdentificacion
     * @param tipoIdentificacion
     */
    public AfiliadoSolicitudDTO(Long idAfiliado, Long idSolicitud, Long idRolAfiliado, String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        super();
        this.idAfiliado = idAfiliado;
        this.idSolicitud = idSolicitud;
        this.idRolAfiliado = idRolAfiliado;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
    }



    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
}
