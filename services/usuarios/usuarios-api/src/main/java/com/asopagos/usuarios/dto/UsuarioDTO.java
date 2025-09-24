package com.asopagos.usuarios.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnore; 

/**
 * <b>Descripción:</b> DTO para el servicio de autenticación usuario
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@XmlRootElement
public class UsuarioDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String idUsuario;
	
	protected boolean usuarioActivo;
	
	protected boolean emailVerified;

	protected String nombreUsuario;

	protected String primerNombre;

	protected String segundoNombre;

	protected String primerApellido;

	protected String segundoApellido;

	protected Date fechaUltimoAcceso;

	protected String email;
	
	protected String tipoIdentificacion;
	
	protected String numIdentificacion;
	
	protected List<String> grupos;
	
	protected List<String> roles;
	
	protected String telefono;
	
	protected String codigoSede;
	
    protected String ciudadSede;

	protected Long fechaExpiracionPassword;
	
	protected Long fechaFinContrato;
	
	protected Integer diasRestantesExpiracionPassword;

	@JsonIgnore
	protected Long fechaCreacion; 

	@JsonIgnore
    protected String creadoPor;   

	@JsonIgnore
    protected Long fechaModificacion; 

	@JsonIgnore
    protected String modificadoPor;
	
	public UsuarioDTO(){
	    
	}

	/**
     * @return the fechaExpiracionPassword
     */
    public Long getFechaExpiracionPassword() {
        return fechaExpiracionPassword;
    }

    /**
     * @param fechaExpiracionPassword the fechaExpiracionPassword to set
     */
    public void setFechaExpiracionPassword(Long fechaExpiracionPassword) {
        this.fechaExpiracionPassword = fechaExpiracionPassword;
    }

    public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumIdentificacion() {
		return numIdentificacion;
	}

	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the fechaUltimoAcceso
	 */
	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	/**
	 * @param fechaUltimoAcceso the fechaUltimoAcceso to set
	 */
	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	
	public List<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 * @return the codigoSede
	 */
	public String getCodigoSede() {
		return codigoSede;
	}

	/**
	 * @param codigoSede the codigoSede to set
	 */
	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}

	/**
     * @return the ciudadSede
     */
    public String getCiudadSede() {
        return ciudadSede;
    }

    /**
     * @param ciudadSede the ciudadSede to set
     */
    public void setCiudadSede(String ciudadSede) {
        this.ciudadSede = ciudadSede;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null)
				return false;
		} else if (!nombreUsuario.equals(other.nombreUsuario))
			return false;
		return true;
	}

	/**
	 * @return the idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the usuarioActivo
	 */
	public boolean isUsuarioActivo() {
		return usuarioActivo;
	}

	/**
	 * @param usuarioActivo the usuarioActivo to set
	 */
	public void setUsuarioActivo(boolean usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}

	/**
	 * @return the emailVerified
	 */
	public boolean isEmailVerified() {
		return emailVerified;
	}

	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

    /**
     * @return the fechaFinContrato
     */
    public Long getFechaFinContrato() {
        return fechaFinContrato;
    }

    /**
     * @param fechaFinContrato the fechaFinContrato to set
     */
    public void setFechaFinContrato(Long fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    /**
     * @return the diasRestantesExpiracionPassword
     */
    public Integer getDiasRestantesExpiracionPassword() {
        return diasRestantesExpiracionPassword;
    }

    /**
     * @param diasRestantesExpiracionPassword the diasRestantesExpiracionPassword to set
     */
    public void setDiasRestantesExpiracionPassword(Integer diasRestantesExpiracionPassword) {
        this.diasRestantesExpiracionPassword = diasRestantesExpiracionPassword;
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

	@Override
	public String toString() {
		return "UsuarioDTO{" +
				"idUsuario='" + idUsuario + '\'' +
				", usuarioActivo=" + usuarioActivo +
				", emailVerified=" + emailVerified +
				", nombreUsuario='" + nombreUsuario + '\'' +
				", primerNombre='" + primerNombre + '\'' +
				", segundoNombre='" + segundoNombre + '\'' +
				", primerApellido='" + primerApellido + '\'' +
				", segundoApellido='" + segundoApellido + '\'' +
				", fechaUltimoAcceso=" + fechaUltimoAcceso +
				", email='" + email + '\'' +
				", tipoIdentificacion='" + tipoIdentificacion + '\'' +
				", numIdentificacion='" + numIdentificacion + '\'' +
				", grupos=" + grupos +
				", roles=" + roles +
				", telefono='" + telefono + '\'' +
				", codigoSede='" + codigoSede + '\'' +
				", ciudadSede='" + ciudadSede + '\'' +
				", fechaExpiracionPassword=" + fechaExpiracionPassword +
				", fechaFinContrato=" + fechaFinContrato +
				", diasRestantesExpiracionPassword=" + diasRestantesExpiracionPassword +
				", fechaCreacion=" + fechaCreacion +
				", creadoPor=" + creadoPor +
				", fechaModificacion=" + fechaModificacion +
				", modificadoPor=" + modificadoPor +
				'}';
	}
}
