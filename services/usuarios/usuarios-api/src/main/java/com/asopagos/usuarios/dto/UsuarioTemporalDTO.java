package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO para los servicios de usuarios temporales
 * <b>Historia de Usuario:</b> HU-SEG-154
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class UsuarioTemporalDTO extends UsuarioDTO implements Serializable{

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;
    
    private Long fechaInicio;
    
    private Long fechaFin;
    
    public UsuarioTemporalDTO(){
        
    }
    
    public UsuarioTemporalDTO(UsuarioDTO usuarioDTO){
        this.email=usuarioDTO.getEmail();
        this.fechaUltimoAcceso=usuarioDTO.getFechaUltimoAcceso();
        this.nombreUsuario=usuarioDTO.getNombreUsuario();
        this.numIdentificacion=usuarioDTO.getNumIdentificacion();
        this.primerApellido=usuarioDTO.getPrimerApellido();
        this.primerNombre=usuarioDTO.getPrimerNombre();
        this.segundoApellido=usuarioDTO.getSegundoApellido();
        this.segundoNombre=usuarioDTO.getSegundoNombre();
        this.tipoIdentificacion=usuarioDTO.getTipoIdentificacion();
        this.codigoSede=usuarioDTO.getCodigoSede();
        this.telefono=usuarioDTO.getTelefono();
        this.idUsuario = usuarioDTO.getIdUsuario();
        this.usuarioActivo = usuarioDTO.isUsuarioActivo();
        this.emailVerified = usuarioDTO.isEmailVerified();
        this.roles=usuarioDTO.getRoles();
        this.grupos=usuarioDTO.getGrupos();
        this.fechaFinContrato = usuarioDTO.getFechaFinContrato();
        this.fechaExpiracionPassword = usuarioDTO.getFechaExpiracionPassword();
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

}