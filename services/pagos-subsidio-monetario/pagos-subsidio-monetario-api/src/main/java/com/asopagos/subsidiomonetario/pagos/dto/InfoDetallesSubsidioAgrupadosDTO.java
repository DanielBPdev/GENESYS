package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InfoDetallesSubsidioAgrupadosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long idGrupoFamiliar;
	
	private Long idAdministradorSubsidio;
	
	private BigDecimal valorTotal;

	private Long idCuentaAdministradorSubsidio;
	
	public InfoDetallesSubsidioAgrupadosDTO() {
	}

	public InfoDetallesSubsidioAgrupadosDTO(Long idGrupoFamiliar, Long idAdministradorSubsidio, String valorTotal) {
		this.idGrupoFamiliar = idGrupoFamiliar;
		this.idAdministradorSubsidio = idAdministradorSubsidio;
		//this.valorTotal = valorTotal != null ? BigDecimal.;
	}

	/**
	 * @return the valorTotal
	 */
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * @return the idGrupoFamiliar
	 */
	public Long getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	/**
	 * @param idGrupoFamiliar the idGrupoFamiliar to set
	 */
	public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	/**
	 * @return the idAdministradorSubsidio
	 */
	public Long getIdAdministradorSubsidio() {
		return idAdministradorSubsidio;
	}

	/**
	 * @param idAdministradorSubsidio the idAdministradorSubsidio to set
	 */
	public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
		this.idAdministradorSubsidio = idAdministradorSubsidio;
	}

	/**
	 * @return the idCuentaAdministradorSubsidio
	 */
	public Long getIdCuentaAdministradorSubsidio() {
		return idCuentaAdministradorSubsidio;
	}

	/**
	 * @param idCuentaAdministradorSubsidio the idCuentaAdministradorSubsidio to set
	 */
	public void setIdCuentaAdministradorSubsidio(Long idCuentaAdministradorSubsidio) {
		this.idCuentaAdministradorSubsidio = idCuentaAdministradorSubsidio;
	}
}