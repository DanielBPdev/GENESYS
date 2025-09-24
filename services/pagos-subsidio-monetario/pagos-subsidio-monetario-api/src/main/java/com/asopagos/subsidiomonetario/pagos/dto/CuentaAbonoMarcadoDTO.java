package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;

import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;

public class CuentaAbonoMarcadoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idCuenta;
	private CuentaAdministradorSubsidio cuenta;
	private String marca;
	
	public CuentaAbonoMarcadoDTO(Long idCuenta, CuentaAdministradorSubsidio cuenta, String marca) {
		this.idCuenta = idCuenta;
		this.cuenta = cuenta;
		this.marca = marca;
	}

	/**
	 * @return the cuenta
	 */
	public CuentaAdministradorSubsidio getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(CuentaAdministradorSubsidio cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the idCuenta
	 */
	public Long getIdCuenta() {
		return idCuenta;
	}

	/**
	 * @param idCuenta the idCuenta to set
	 */
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
}
