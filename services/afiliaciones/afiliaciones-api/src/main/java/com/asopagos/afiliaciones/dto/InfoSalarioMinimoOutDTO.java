package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class InfoSalarioMinimoOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	* Periodo al que corresponde el Salario
	*/
	private String periodo;

	/**
	* Valor del salario mensual vigente
	*/
	private String salarioMinimo;

	/**
	 * 
	 */
	public InfoSalarioMinimoOutDTO() {
	}
	
	/**
	 * @param periodo
	 * @param salarioMinimo
	 */
	public InfoSalarioMinimoOutDTO(String periodo, String salarioMinimo) {
		this.periodo = periodo;
		this.salarioMinimo = salarioMinimo;
	}

	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the salarioMinimo
	 */
	public String getSalarioMinimo() {
		return salarioMinimo;
	}

	/**
	 * @param salarioMinimo the salarioMinimo to set
	 */
	public void setSalarioMinimo(String salarioMinimo) {
		this.salarioMinimo = salarioMinimo;
	}
}
