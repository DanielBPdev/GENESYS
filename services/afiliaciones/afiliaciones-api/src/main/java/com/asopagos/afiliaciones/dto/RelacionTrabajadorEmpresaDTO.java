package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class RelacionTrabajadorEmpresaDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    
    private String numeroIdentificacionAfiliado;
    
    private TipoIdentificacionEnum tipoIdentificacionEmpleador;
    
    private String numeroIdentificacionEmpleador;
    
    private EstadoAfiliadoEnum estadoTrabRespectoEmpl;
    
    private CanalRecepcionEnum canal;
    
    private Date fechaRetiro;
    
    private Long idEmpresa;
    
    private Long idPersona;
    
    private Long idRolAfiliado;
    
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;

    /**
     * 
     */
    public RelacionTrabajadorEmpresaDTO() {}

    /**
     * @return the tipoIdentificacionAfiliado
     */
    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return tipoIdentificacionAfiliado;
    }

    /**
     * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
     */
    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }

    /**
     * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    /**
     * @return the tipoIdentificacionEmpleador
     */
    public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
        return tipoIdentificacionEmpleador;
    }

    /**
     * @param tipoIdentificacionEmpleador the tipoIdentificacionEmpleador to set
     */
    public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    /**
     * @return the numeroIdentificacionEmpleador
     */
    public String getNumeroIdentificacionEmpleador() {
        return numeroIdentificacionEmpleador;
    }

    /**
     * @param numeroIdentificacionEmpleador the numeroIdentificacionEmpleador to set
     */
    public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
    }

    /**
     * @return the estadoTrabRespectoEmpl
     */
    public EstadoAfiliadoEnum getEstadoTrabRespectoEmpl() {
        return estadoTrabRespectoEmpl;
    }

    /**
     * @param estadoTrabRespectoEmpl the estadoTrabRespectoEmpl to set
     */
    public void setEstadoTrabRespectoEmpl(EstadoAfiliadoEnum estadoTrabRespectoEmpl) {
        this.estadoTrabRespectoEmpl = estadoTrabRespectoEmpl;
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
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

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

}
