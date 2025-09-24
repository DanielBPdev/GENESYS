package com.asopagos.entidades.pagadoras.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO para el método 
 * consultarSolicitudesAsociacionPersonas del servicio entidades pagadoras 
 * <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class PersonaInDTO {
	
	private TipoIdentificacionEnum tipoIdentificacion;
	
	private String numeroIdentificacion;
	
	private String primerNombre;
	
	private String segundoNombre;
	
	private String primerApellido;
	
	private String segundoApellido;
	
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
}
