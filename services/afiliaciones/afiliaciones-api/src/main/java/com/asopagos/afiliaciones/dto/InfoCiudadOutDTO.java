package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class InfoCiudadOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Nombre del departamento
	*/
	private String nombreDepartamento;

	/**
	* Nombre del municipio
	*/
	private String nombreCiudad;

	/**
	* Código del departamento
	*/
	private String codigoDepartamento;

	/**
	* Código del municipio
	*/
	private String codigoMunicipio;

	/**
	 * 
	 */
	public InfoCiudadOutDTO() {}
	
	/**
	 * @param nombreDepartamento
	 * @param nombreCiudad
	 * @param codigoDepartamento
	 * @param codigoMunicipio
	 */
	public InfoCiudadOutDTO(String nombreDepartamento, String nombreCiudad, String codigoDepartamento,
			String codigoMunicipio) {
		this.nombreDepartamento = nombreDepartamento;
		this.nombreCiudad = nombreCiudad;
		this.codigoDepartamento = codigoDepartamento;
		this.codigoMunicipio = codigoMunicipio;
	}

	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * @param nombreDepartamento the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	/**
	 * @return the nombreCiudad
	 */
	public String getNombreCiudad() {
		return nombreCiudad;
	}

	/**
	 * @param nombreCiudad the nombreCiudad to set
	 */
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	/**
	 * @return the codigoDepartamento
	 */
	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	/**
	 * @param codigoDepartamento the codigoDepartamento to set
	 */
	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	/**
	 * @return the codigoMunicipio
	 */
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	/**
	 * @param codigoMunicipio the codigoMunicipio to set
	 */
	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}
}
