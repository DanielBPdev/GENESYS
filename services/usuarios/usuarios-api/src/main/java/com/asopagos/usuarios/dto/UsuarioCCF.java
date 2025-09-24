package com.asopagos.usuarios.dto;

public class UsuarioCCF extends UsuarioDTO{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String dependencia;
	
	private Boolean reintegro;

	private String razonSocial;

	private String nombreConvenio;

	private Long idConvenio;

	private String medioPago;

	private String estadoConvenio;
	

	public UsuarioCCF(){
		
	}

	public UsuarioCCF(UsuarioDTO usuarioDTO){
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
		this.ciudadSede=usuarioDTO.getCiudadSede();
		this.telefono=usuarioDTO.getTelefono();
		this.idUsuario = usuarioDTO.getIdUsuario();
		this.usuarioActivo = usuarioDTO.isUsuarioActivo();
		this.emailVerified = usuarioDTO.isEmailVerified();
		this.fechaFinContrato = usuarioDTO.getFechaFinContrato();
		this.fechaExpiracionPassword = usuarioDTO.getFechaExpiracionPassword();
		this.fechaCreacion = usuarioDTO.getFechaCreacion();
        this.creadoPor = usuarioDTO.getCreadoPor();
        this.fechaModificacion = usuarioDTO.getFechaModificacion();
        this.modificadoPor = usuarioDTO.getModificadoPor();
	}


	/**
	 * @return the dependencia
	 */
	public String getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia the dependencia to set
	 */
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

    /**
     * @return the reintegro
     */
    public Boolean getReintegro() {
        return reintegro;
    }

    /**
     * @param reintegro the reintegro to set
     */
    public void setReintegro(Boolean reintegro) {
        this.reintegro = reintegro;
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
	
}
