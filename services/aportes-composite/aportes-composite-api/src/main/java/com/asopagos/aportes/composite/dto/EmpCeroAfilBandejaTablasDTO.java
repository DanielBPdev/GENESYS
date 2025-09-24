/**
 * 
 */
package com.asopagos.aportes.composite.dto;

/**
 * @author anbuitrago
 *
 */
public class EmpCeroAfilBandejaTablasDTO {
	
	
	private Long numeroRegistro;
	private  String tipoIdentificacion;
	private  Long numIdentificacion;
	private  String empresa;
	private  String historicoAportes;
	private  String conAfiliacionTrabajadores;
	private  String mantenerAfiliacionPor;
	
	/**
	 * @return the numeroRegistro
	 */
	public Long getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * @param numeroRegistro the numeroRegistro to set
	 */
	public void setNumeroRegistro(Long numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
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
	 * @return the numIdentificacion
	 */
	public Long getNumIdentificacion() {
		return numIdentificacion;
	}
	/**
	 * @param numIdentificacion the numIdentificacion to set
	 */
	public void setNumIdentificacion(Long numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the historicoAportes
	 */
	public String getHistoricoAportes() {
		return historicoAportes;
	}
	/**
	 * @param historicoAportes the historicoAportes to set
	 */
	public void setHistoricoAportes(String historicoAportes) {
		this.historicoAportes = historicoAportes;
	}
	/**
	 * @return the conAfiliacionTrabajadores
	 */
	public String getConAfiliacionTrabajadores() {
		return conAfiliacionTrabajadores;
	}
	/**
	 * @param conAfiliacionTrabajadores the conAfiliacionTrabajadores to set
	 */
	public void setConAfiliacionTrabajadores(String conAfiliacionTrabajadores) {
		this.conAfiliacionTrabajadores = conAfiliacionTrabajadores;
	}
	
	/**
	 * @return the mantenerAfiliacionPor
	 */
	public String getMantenerAfiliacionPor() {
		return mantenerAfiliacionPor;
	}
	/**
	 * @param mantenerAfiliacionPor the mantenerAfiliacionPor to set
	 */
	public void setMantenerAfiliacionPor(String mantenerAfiliacionPor) {
		this.mantenerAfiliacionPor = mantenerAfiliacionPor;
	}
	public EmpCeroAfilBandejaTablasDTO() {
		
	}
	/**
	 * @param numeroRegistro
	 * @param tipoIdentificacion
	 * @param numIdentificacion
	 * @param empresa
	 * @param historicoAportes
	 * @param conAfiliacionTrabajadores
	 * @param mantenerAfiliacionPor
	 */
	public EmpCeroAfilBandejaTablasDTO(Long numeroRegistro, String tipoIdentificacion, Long numIdentificacion,
			String empresa, String historicoAportes, String conAfiliacionTrabajadores, String mantenerAfiliacionPor) {
		this.numeroRegistro = numeroRegistro;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numIdentificacion = numIdentificacion;
		this.empresa = empresa;
		this.historicoAportes = historicoAportes;
		this.conAfiliacionTrabajadores = conAfiliacionTrabajadores;
		this.mantenerAfiliacionPor = mantenerAfiliacionPor;
	}
	
	
}
