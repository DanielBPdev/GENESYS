package com.asopagos.aportes.dto;

/**
 * DTO con los datos de la planilla para el envio del comunicado.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 *
 */
public class DatosComunicadoPlanillaDTO {
	
	private Long idPlanilla;
	private int numeroAportesEnPlanilla;
	private Boolean planillaManual = Boolean.FALSE;
	
	/**
	 * 
	 */
	public DatosComunicadoPlanillaDTO() {
	}

	/**
	 * @param idPlanilla
	 * @param numeroAportesEnPlanilla
	 * @param tipoAfiliadoEnum
	 */
	public DatosComunicadoPlanillaDTO(Long idPlanilla, int numeroAportesEnPlanilla) {
		this.idPlanilla = idPlanilla;
		this.numeroAportesEnPlanilla = numeroAportesEnPlanilla;
	}

	/**
	 * @return the idPlanilla
	 */
	public Long getIdPlanilla() {
		return idPlanilla;
	}

	/**
	 * @param idPlanilla the idPlanilla to set
	 */
	public void setIdPlanilla(Long idPlanilla) {
		this.idPlanilla = idPlanilla;
	}

	/**
	 * @return the numeroAportesEnPlanilla
	 */
	public int getNumeroAportesEnPlanilla() {
		return numeroAportesEnPlanilla;
	}

	/**
	 * @param numeroAportesEnPlanilla the numeroAportesEnPlanilla to set
	 */
	public void setNumeroAportesEnPlanilla(int numeroAportesEnPlanilla) {
		this.numeroAportesEnPlanilla = numeroAportesEnPlanilla;
	}

    /**
     * @return the planillaManual
     */
    public Boolean getPlanillaManual() {
        return planillaManual;
    }

    /**
     * @param planillaManual the planillaManual to set
     */
    public void setPlanillaManual(Boolean planillaManual) {
        this.planillaManual = planillaManual;
    }
}
