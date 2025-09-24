/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.BeneficiarioMPCEnum;


/**
 * @author squintero
 *
 */
public class BeneficiariosSubsidioFosfecDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BeneficiarioMPCEnum beneficiarioMPC;
	private String fechaLiquidacion;
	private String periodoLiquidado;
	private Integer noBeneficiosLiq;
	private Integer noCuotasMonetariasLiq;
	private Integer noPersonasCargo;
	private Integer noBonosLiq;
	private Integer montoSaludLiq;
	private Integer montoPensionLiq;
	private Integer montoCuotaLiq;
	private Integer montoBonoAlimentacionLiq;
	private Integer totalPrestacionesEco;
	private Integer renunciaVoluntaria;
	private String fechaRenuncia;
	private Integer perdidaBeneficio;
	private String fechaPerdida;
	private Integer terminacionBeneficio;
	private String fechaTerminacion;
	private String vigecia;
	
	/**
	 * 
	 */
	public BeneficiariosSubsidioFosfecDTO() {
	}

	public BeneficiariosSubsidioFosfecDTO(String fechaLiquidacion,
			String periodoLiquidado, Integer noBeneficiosLiq, Integer noCuotasMonetariasLiq, Integer noPersonasCargo,
			Integer noBonosLiq, Integer montoSaludLiq, Integer montoPensionLiq, Integer montoCuotaLiq,
			Integer montoBonoAlimentacionLiq, Integer totalPrestacionesEco, Integer renunciaVoluntaria,
			String fechaRenuncia, Integer perdidaBeneficio, String fechaPerdida, Integer terminacionBeneficio,
			String fechaTerminacion, String vigecia) {
		this.fechaLiquidacion = fechaLiquidacion;
		this.periodoLiquidado = periodoLiquidado;
		this.noBeneficiosLiq = noBeneficiosLiq;
		this.noCuotasMonetariasLiq = noCuotasMonetariasLiq;
		this.noPersonasCargo = noPersonasCargo;
		this.noBonosLiq = noBonosLiq;
		this.montoSaludLiq = montoSaludLiq;
		this.montoPensionLiq = montoPensionLiq;
		this.montoCuotaLiq = montoCuotaLiq;
		this.montoBonoAlimentacionLiq = montoBonoAlimentacionLiq;
		this.totalPrestacionesEco = totalPrestacionesEco;
		this.renunciaVoluntaria = renunciaVoluntaria;
		this.fechaRenuncia = fechaRenuncia;
		this.perdidaBeneficio = perdidaBeneficio;
		this.fechaPerdida = fechaPerdida;
		this.terminacionBeneficio = terminacionBeneficio;
		this.fechaTerminacion = fechaTerminacion;
		this.vigecia = vigecia;
	}



	/**
	 * @return the beneficiarioMPC
	 */
	public BeneficiarioMPCEnum getBeneficiarioMPC() {
		return beneficiarioMPC;
	}

	/**
	 * @param beneficiarioMPC the beneficiarioMPC to set
	 */
	public void setBeneficiarioMPC(BeneficiarioMPCEnum beneficiarioMPC) {
		this.beneficiarioMPC = beneficiarioMPC;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the periodoLiquidado
	 */
	public String getPeriodoLiquidado() {
		return periodoLiquidado;
	}

	/**
	 * @param periodoLiquidado the periodoLiquidado to set
	 */
	public void setPeriodoLiquidado(String periodoLiquidado) {
		this.periodoLiquidado = periodoLiquidado;
	}

	/**
	 * @return the noBeneficiosLiq
	 */
	public Integer getNoBeneficiosLiq() {
		return noBeneficiosLiq;
	}

	/**
	 * @param noBeneficiosLiq the noBeneficiosLiq to set
	 */
	public void setNoBeneficiosLiq(Integer noBeneficiosLiq) {
		this.noBeneficiosLiq = noBeneficiosLiq;
	}

	/**
	 * @return the noCuotasMonetariasLiq
	 */
	public Integer getNoCuotasMonetariasLiq() {
		return noCuotasMonetariasLiq;
	}

	/**
	 * @param noCuotasMonetariasLiq the noCuotasMonetariasLiq to set
	 */
	public void setNoCuotasMonetariasLiq(Integer noCuotasMonetariasLiq) {
		this.noCuotasMonetariasLiq = noCuotasMonetariasLiq;
	}

	/**
	 * @return the noPersonasCargo
	 */
	public Integer getNoPersonasCargo() {
		return noPersonasCargo;
	}

	/**
	 * @param noPersonasCargo the noPersonasCargo to set
	 */
	public void setNoPersonasCargo(Integer noPersonasCargo) {
		this.noPersonasCargo = noPersonasCargo;
	}

	/**
	 * @return the noBonosLiq
	 */
	public Integer getNoBonosLiq() {
		return noBonosLiq;
	}

	/**
	 * @param noBonosLiq the noBonosLiq to set
	 */
	public void setNoBonosLiq(Integer noBonosLiq) {
		this.noBonosLiq = noBonosLiq;
	}

	/**
	 * @return the montoSaludLiq
	 */
	public Integer getMontoSaludLiq() {
		return montoSaludLiq;
	}

	/**
	 * @param montoSaludLiq the montoSaludLiq to set
	 */
	public void setMontoSaludLiq(Integer montoSaludLiq) {
		this.montoSaludLiq = montoSaludLiq;
	}

	/**
	 * @return the montoPensionLiq
	 */
	public Integer getMontoPensionLiq() {
		return montoPensionLiq;
	}

	/**
	 * @param montoPensionLiq the montoPensionLiq to set
	 */
	public void setMontoPensionLiq(Integer montoPensionLiq) {
		this.montoPensionLiq = montoPensionLiq;
	}

	/**
	 * @return the montoCuotaLiq
	 */
	public Integer getMontoCuotaLiq() {
		return montoCuotaLiq;
	}

	/**
	 * @param montoCuotaLiq the montoCuotaLiq to set
	 */
	public void setMontoCuotaLiq(Integer montoCuotaLiq) {
		this.montoCuotaLiq = montoCuotaLiq;
	}

	/**
	 * @return the montoBonoAlimentacionLiq
	 */
	public Integer getMontoBonoAlimentacionLiq() {
		return montoBonoAlimentacionLiq;
	}

	/**
	 * @param montoBonoAlimentacionLiq the montoBonoAlimentacionLiq to set
	 */
	public void setMontoBonoAlimentacionLiq(Integer montoBonoAlimentacionLiq) {
		this.montoBonoAlimentacionLiq = montoBonoAlimentacionLiq;
	}

	/**
	 * @return the totalPrestacionesEco
	 */
	public Integer getTotalPrestacionesEco() {
		return totalPrestacionesEco;
	}

	/**
	 * @param totalPrestacionesEco the totalPrestacionesEco to set
	 */
	public void setTotalPrestacionesEco(Integer totalPrestacionesEco) {
		this.totalPrestacionesEco = totalPrestacionesEco;
	}

	/**
	 * @return the renunciaVoluntaria
	 */
	public Integer getRenunciaVoluntaria() {
		return renunciaVoluntaria;
	}

	/**
	 * @param renunciaVoluntaria the renunciaVoluntaria to set
	 */
	public void setRenunciaVoluntaria(Integer renunciaVoluntaria) {
		this.renunciaVoluntaria = renunciaVoluntaria;
	}

	/**
	 * @return the fechaRenuncia
	 */
	public String getFechaRenuncia() {
		return fechaRenuncia;
	}

	/**
	 * @param fechaRenuncia the fechaRenuncia to set
	 */
	public void setFechaRenuncia(String fechaRenuncia) {
		this.fechaRenuncia = fechaRenuncia;
	}

	/**
	 * @return the perdidaBeneficio
	 */
	public Integer getPerdidaBeneficio() {
		return perdidaBeneficio;
	}

	/**
	 * @param perdidaBeneficio the perdidaBeneficio to set
	 */
	public void setPerdidaBeneficio(Integer perdidaBeneficio) {
		this.perdidaBeneficio = perdidaBeneficio;
	}

	/**
	 * @return the fechaPerdida
	 */
	public String getFechaPerdida() {
		return fechaPerdida;
	}

	/**
	 * @param fechaPerdida the fechaPerdida to set
	 */
	public void setFechaPerdida(String fechaPerdida) {
		this.fechaPerdida = fechaPerdida;
	}

	/**
	 * @return the terminacionBeneficio
	 */
	public Integer getTerminacionBeneficio() {
		return terminacionBeneficio;
	}

	/**
	 * @param terminacionBeneficio the terminacionBeneficio to set
	 */
	public void setTerminacionBeneficio(Integer terminacionBeneficio) {
		this.terminacionBeneficio = terminacionBeneficio;
	}

	/**
	 * @return the fechaTerminacion
	 */
	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	/**
	 * @param fechaTerminacion the fechaTerminacion to set
	 */
	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	/**
	 * @return the vigecia
	 */
	public String getVigecia() {
		return vigecia;
	}

	/**
	 * @param vigecia the vigecia to set
	 */
	public void setVigecia(String vigecia) {
		this.vigecia = vigecia;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return beneficiarioMPC + "," + fechaLiquidacion + "," + periodoLiquidado + "," + noBeneficiosLiq + ","
				+ noCuotasMonetariasLiq + "," + noPersonasCargo + "," + noBonosLiq + "," + montoSaludLiq + ","
				+ montoPensionLiq + "," + montoCuotaLiq + "," + montoBonoAlimentacionLiq + "," + totalPrestacionesEco
				+ "," + renunciaVoluntaria + "," + fechaRenuncia + "," + perdidaBeneficio + "," + fechaPerdida + ","
				+ terminacionBeneficio + "," + fechaTerminacion + "," + vigecia;
	}
}