package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para los servicios de usuarios 
 * 
 */
@XmlRootElement
public class UsuarioGestionDTO extends UsuarioDTO{

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;
    
	protected Long fechaCreacion; 

    protected String creadoPor;   

    protected Long fechaModificacion; 

    protected String modificadoPor;

    private String razonSocial;

	private String nombreConvenio;

	private Long idConvenio;

	private String medioPago;

	private String estadoConvenio;

    private String tipoUsuario;

    private TipoIdentificacionEnum tipoIdentificacionArchivo;
    
    public UsuarioGestionDTO(){
        
    }
    
    public UsuarioGestionDTO(UsuarioDTO usuarioDTO){
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

    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Long getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Long fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

	public String getNombreConvenio() {
		return this.nombreConvenio;
	}

	public void setNombreConvenio(String nombreConvenio) {
		this.nombreConvenio = nombreConvenio;
	}

	public Long getIdConvenio() {
		return this.idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public String getMedioPago() {
		return this.medioPago;
	}

	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	public String getEstadoConvenio() {
		return this.estadoConvenio;
	}

	public void setEstadoConvenio(String estadoConvenio) {
		this.estadoConvenio = estadoConvenio;
	}
    
    public String getTipoUsuario() {
        return this.tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoIdentificacionEnum getTipoIdentificacionArchivo() {
        return this.tipoIdentificacionArchivo;
    }

    public void setTipoIdentificacionArchivo(TipoIdentificacionEnum tipoIdentificacionArchivo) {
        this.tipoIdentificacionArchivo = tipoIdentificacionArchivo;
    }

}