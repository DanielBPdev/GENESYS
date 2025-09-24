package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class UltimoSalarioAfiliadoOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	* Tipo de identificación de la persona
	*/
	private String tipoId;
	
	/**
	* Número de identificación de la persona
	*/
	private String identificacion;
	
	/**
	* Valor de la mesada salarial de la persona pagado por una empresa
	*/
	private String salario;
	
	/**
	* Fecha Novedad
	*/
	private String fechaNovedad;

	/**
	 * 
	 */
	public UltimoSalarioAfiliadoOutDTO(){}
	
	/**
	 * @param tipoId
	 * @param identificacion
	 * @param salario
	 * @param fechaNovedad
	 */
	public UltimoSalarioAfiliadoOutDTO(String tipoId, String identificacion, String salario, String fechaNovedad) {
		this.tipoId = tipoId;
		this.identificacion = identificacion;
		this.salario = salario;
		this.fechaNovedad = fechaNovedad;
	}

	/**
	 * @return the tipoId
	 */
	public String getTipoId() {
		return tipoId;
	}

	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the salario
	 */
	public String getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(String salario) {
		this.salario = salario;
	}

	/**
	 * @return the fechaNovedad
	 */
	public String getFechaNovedad() {
		return fechaNovedad;
	}

	/**
	 * @param fechaNovedad the fechaNovedad to set
	 */
	public void setFechaNovedad(String fechaNovedad) {
		this.fechaNovedad = fechaNovedad;
	}
}
