package com.asopagos.aportes.composite.dto;

import java.util.List;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class RegistrarNovedadesPilaServiceDTO {
	private CanalRecepcionEnum canal;
	private TipoIdentificacionEnum tipoIdAportante;
	private String numeroIdAportante;
	private PersonaModeloDTO personaCotizante;
	private Boolean esTrabajadorReintegrable;
	private Boolean esEmpleadorReintegrable;
	
	private List<NovedadPilaDTO> novedades;
	/**
	 * @return the novedades
	 */
	public List<NovedadPilaDTO> getNovedades() {
		return novedades;
	}
	/**
	 * @param novedades the novedades to set
	 */
	public void setNovedades(List<NovedadPilaDTO> novedades) {
		this.novedades = novedades;
	}
	/**
	 * @return the canal
	 */
	public CanalRecepcionEnum getCanal() {
		return canal;
	}
	/**
	 * @param canal the canal to set
	 */
	public void setCanal(CanalRecepcionEnum canal) {
		this.canal = canal;
	}
	/**
	 * @return the tipoIdAportante
	 */
	public TipoIdentificacionEnum getTipoIdAportante() {
		return tipoIdAportante;
	}
	/**
	 * @param tipoIdAportante the tipoIdAportante to set
	 */
	public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
		this.tipoIdAportante = tipoIdAportante;
	}
	/**
	 * @return the numeroIdAportante
	 */
	public String getNumeroIdAportante() {
		return numeroIdAportante;
	}
	/**
	 * @param numeroIdAportante the numeroIdAportante to set
	 */
	public void setNumeroIdAportante(String numeroIdAportante) {
		this.numeroIdAportante = numeroIdAportante;
	}
	/**
	 * @return the personaCotizante
	 */
	public PersonaModeloDTO getPersonaCotizante() {
		return personaCotizante;
	}
	/**
	 * @param personaCotizante the personaCotizante to set
	 */
	public void setPersonaCotizante(PersonaModeloDTO personaCotizante) {
		this.personaCotizante = personaCotizante;
	}
	/**
	 * @return the esTrabajadorReintegrable
	 */
	public Boolean getEsTrabajadorReintegrable() {
		return esTrabajadorReintegrable;
	}
	/**
	 * @param esTrabajadorReintegrable the esTrabajadorReintegrable to set
	 */
	public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
		this.esTrabajadorReintegrable = esTrabajadorReintegrable;
	}
	/**
	 * @return the esEmpleadorReintegrable
	 */
	public Boolean getEsEmpleadorReintegrable() {
		return esEmpleadorReintegrable;
	}
	/**
	 * @param esEmpleadorReintegrable the esEmpleadorReintegrable to set
	 */
	public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
		this.esEmpleadorReintegrable = esEmpleadorReintegrable;
	}
}
