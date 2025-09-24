package com.asopagos.dto;

import java.io.Serializable;

public class MetadataArchivoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String canal;
	private String CodigoRegistroCanal;
	private String tipoDocumental; 
	private String tema;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String nombre;
	private String ciudad;
	private String barrio;
	private String direccion;
	private String telefono;
	private String celular; 
	private String email;
	private String observacion;
	private Boolean generarArchivo;
	private String usuario;
	
	public MetadataArchivoDTO() {
	}
	
	public MetadataArchivoDTO(String canal, String codigoRegistroCanal, String tipoDocumental, String tema,
			String tipoIdentificacion, String numeroIdentificacion, String nombre, String ciudad, String barrio,
			String direccion, String telefono, String celular, String email, String observacion, Boolean generarArchivo,
			String usuario) {
		super();
		this.canal = canal;
		CodigoRegistroCanal = codigoRegistroCanal;
		this.tipoDocumental = tipoDocumental;
		this.tema = tema;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.barrio = barrio;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.email = email;
		this.observacion = observacion;
		this.generarArchivo = generarArchivo;
		this.usuario = usuario;
	}

	/**
	 * @return the canal
	 */
	public String getCanal() {
		return canal;
	}

	/**
	 * @param canal the canal to set
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}

	/**
	 * @return the codigoRegistroCanal
	 */
	public String getCodigoRegistroCanal() {
		return CodigoRegistroCanal;
	}

	/**
	 * @param codigoRegistroCanal the codigoRegistroCanal to set
	 */
	public void setCodigoRegistroCanal(String codigoRegistroCanal) {
		CodigoRegistroCanal = codigoRegistroCanal;
	}

	/**
	 * @return the tipoDocumental
	 */
	public String getTipoDocumental() {
		return tipoDocumental;
	}

	/**
	 * @param tipoDocumental the tipoDocumental to set
	 */
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	/**
	 * @return the tema
	 */
	public String getTema() {
		return tema;
	}

	/**
	 * @param tema the tema to set
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the barrio
	 */
	public String getBarrio() {
		return barrio;
	}

	/**
	 * @param barrio the barrio to set
	 */
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the generarArchivo
	 */
	public Boolean isGenerarArchivo() {
		return generarArchivo;
	}

	/**
	 * @param generarArchivo the generarArchivo to set
	 */
	public void setGenerarArchivo(Boolean generarArchivo) {
		this.generarArchivo = generarArchivo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetadataArchivoDTO [canal=");
		builder.append(canal);
		builder.append(", CodigoRegistroCanal=");
		builder.append(CodigoRegistroCanal);
		builder.append(", tipoDocumental=");
		builder.append(tipoDocumental);
		builder.append(", tema=");
		builder.append(tema);
		builder.append(", tipoIdentificacion=");
		builder.append(tipoIdentificacion);
		builder.append(", numeroIdentificacion=");
		builder.append(numeroIdentificacion);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", ciudad=");
		builder.append(ciudad);
		builder.append(", barrio=");
		builder.append(barrio);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", celular=");
		builder.append(celular);
		builder.append(", email=");
		builder.append(email);
		builder.append(", observacion=");
		builder.append(observacion);
		builder.append(", generarArchivo=");
		builder.append(generarArchivo);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}
	
	
}
