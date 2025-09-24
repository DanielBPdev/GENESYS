package com.asopagos.comunicados.dto;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.CausalCruceEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class DescripcionCruceFovisDTO {
	
	/**
	 * Tipo de identificacion del Jefe de hogar
	 */
	private TipoIdentificacionEnum tipoIdentificacionJefeHogar;
	/**
	 * Numero de identificacion del jefe de Hogar
	 */
	private String numeroIdentificacionJefeHogar;
	/**
	 * Nombre del jefe de hogar
	 */
	private String nombreJefeHogar;
	/**
	 * Parentesco del miembro respecto al jefe de hogar
	 */
	private ClasificacionEnum parentesco;
	/**
	 * Nombre del miembro de hogar asociado al cruce 
	 */
	private String nombreMiembroHogar;
	/**
	 * Tipo de identificacion del miembro de hogar asociado al cruce
	 */
	private TipoIdentificacionEnum tipoIdentificacionMiembro;
	/**
	 * Numero de identificacion del miembro de hogar asociado al cruce
	 */
	private String numeroIdentificacionMiembro;
	/**
	 * Causal del cruce
	 */
	private CausalCruceEnum causalCruce;
	/**
	 * Id de la postulacion
	 */
	private Long idPostulacion;
	
	public DescripcionCruceFovisDTO() {
		// TODO Auto-generated constructor stub
	}

	public DescripcionCruceFovisDTO(TipoIdentificacionEnum tipoIdentificacionJefeHogar,
			String numeroIdentificacionJefeHogar, String nombreJefeHogar, ClasificacionEnum parentesco,
			String nombreMiembroHogar, TipoIdentificacionEnum tipoIdentificacionMiembro,
			String numeroIdentificacionMiembro, CausalCruceEnum causalCruce) {
		super();
		this.tipoIdentificacionJefeHogar = tipoIdentificacionJefeHogar;
		this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
		this.nombreJefeHogar = nombreJefeHogar;
		this.parentesco = parentesco;
		this.nombreMiembroHogar = nombreMiembroHogar;
		this.tipoIdentificacionMiembro = tipoIdentificacionMiembro;
		this.numeroIdentificacionMiembro = numeroIdentificacionMiembro;
		this.causalCruce = causalCruce;
	}



	/**
	 * @return the tipoIdentificacionJefeHogar
	 */
	public TipoIdentificacionEnum getTipoIdentificacionJefeHogar() {
		return tipoIdentificacionJefeHogar;
	}

	/**
	 * @param tipoIdentificacionJefeHogar the tipoIdentificacionJefeHogar to set
	 */
	public void setTipoIdentificacionJefeHogar(TipoIdentificacionEnum tipoIdentificacionJefeHogar) {
		this.tipoIdentificacionJefeHogar = tipoIdentificacionJefeHogar;
	}

	/**
	 * @return the numeroIdentificacionJefeHogar
	 */
	public String getNumeroIdentificacionJefeHogar() {
		return numeroIdentificacionJefeHogar;
	}

	/**
	 * @param numeroIdentificacionJefeHogar the numeroIdentificacionJefeHogar to set
	 */
	public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
		this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
	}

	/**
	 * @return the nombreJefeHogar
	 */
	public String getNombreJefeHogar() {
		return nombreJefeHogar;
	}

	/**
	 * @param nombreJefeHogar the nombreJefeHogar to set
	 */
	public void setNombreJefeHogar(String nombreJefeHogar) {
		this.nombreJefeHogar = nombreJefeHogar;
	}

	/**
	 * @return the parentesco
	 */
	public ClasificacionEnum getParentesco() {
		return parentesco;
	}

	/**
	 * @param parentesco the parentesco to set
	 */
	public void setParentesco(ClasificacionEnum parentesco) {
		this.parentesco = parentesco;
	}

	/**
	 * @return the nombreMiembroHogar
	 */
	public String getNombreMiembroHogar() {
		return nombreMiembroHogar;
	}

	/**
	 * @param nombreMiembroHogar the nombreMiembroHogar to set
	 */
	public void setNombreMiembroHogar(String nombreMiembroHogar) {
		this.nombreMiembroHogar = nombreMiembroHogar;
	}

	/**
	 * @return the tipoIdentificacionMiembro
	 */
	public TipoIdentificacionEnum getTipoIdentificacionMiembro() {
		return tipoIdentificacionMiembro;
	}

	/**
	 * @param tipoIdentificacionMiembro the tipoIdentificacionMiembro to set
	 */
	public void setTipoIdentificacionMiembro(TipoIdentificacionEnum tipoIdentificacionMiembro) {
		this.tipoIdentificacionMiembro = tipoIdentificacionMiembro;
	}

	/**
	 * @return the numeroIdentificacionMiembro
	 */
	public String getNumeroIdentificacionMiembro() {
		return numeroIdentificacionMiembro;
	}

	/**
	 * @param numeroIdentificacionMiembro the numeroIdentificacionMiembro to set
	 */
	public void setNumeroIdentificacionMiembro(String numeroIdentificacionMiembro) {
		this.numeroIdentificacionMiembro = numeroIdentificacionMiembro;
	}

	/**
	 * @return the causalCruce
	 */
	public CausalCruceEnum getCausalCruce() {
		return causalCruce;
	}

	/**
	 * @param causalCruce the causalCruce to set
	 */
	public void setCausalCruce(CausalCruceEnum causalCruce) {
		this.causalCruce = causalCruce;
	}
	
	
	
	

}
