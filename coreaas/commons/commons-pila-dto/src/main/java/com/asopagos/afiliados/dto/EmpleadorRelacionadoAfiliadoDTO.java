package com.asopagos.afiliados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class EmpleadorRelacionadoAfiliadoDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String razonSocial;
    private EstadoEmpleadorEnum estadoEmpleador;
    private EstadoAfiliadoEnum estadoAfiliado;
    private Long idEmpleador;
    private Long idAfiliado;
    private Long idRolAfiliado;
    private Long idEmpresa;
    
    /**
     * 
     */
    public EmpleadorRelacionadoAfiliadoDTO() {
    }
    
    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param razonSocial
     * @param estadoEmpleador
     * @param estadoAfiliado
     * @param idEmpleador
     * @param idAfiliado
     * @param idRolAfiliado
     */
    public EmpleadorRelacionadoAfiliadoDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
            EstadoEmpleadorEnum estadoEmpleador, EstadoAfiliadoEnum estadoAfiliado, Long idEmpleador, Long idAfiliado, Long idRolAfiliado, 
            Long idEmpresa) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.razonSocial = razonSocial;
        this.estadoEmpleador = estadoEmpleador;
        this.estadoAfiliado = estadoAfiliado;
        this.idEmpleador = idEmpleador;
        this.idAfiliado = idAfiliado;
        this.idRolAfiliado = idRolAfiliado;
        this.idEmpresa = idEmpresa;
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
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }
    
    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    /**
     * @return the estadoEmpleador
     */
    public EstadoEmpleadorEnum getEstadoEmpleador() {
        return estadoEmpleador;
    }
    
    /**
     * @param estadoEmpleador the estadoEmpleador to set
     */
    public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
        this.estadoEmpleador = estadoEmpleador;
    }
    
    /**
     * @return the estadoAfiliado
     */
    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return estadoAfiliado;
    }
    
    /**
     * @param estadoAfiliado the estadoAfiliado to set
     */
    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }
    
    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }
    
    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
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
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
