package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class InfoPadresBiologicosOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Tipo de identificación del padre biológico
	*/
	private String tipoIdPadreBiologico;

	/**
	* Número de identificación del padre biológico
	*/
	private String identificacionPadreBiologico;

	/**
	* Nombre completo del padre biológico
	*/
	private String nombrePadreBiologico;

	/**
	* Tipo Identificación madre biológica
	*/
	private String tipoIdMadreBiologica;

	/**
	* Número de identificación de la madre biológica
	*/
	private String identificacionMadreBiologica;

	/**
	* Nombre completo de la madre biológica
	*/
	private String nombreMadreBiologica;

	/**
	* Fecha inicio de exclusion de sumatoria de salarios
	*/
	private String fechaInicioExclusionSumatoriaPadre;

	/**
	* Fecha fin de exclusion de sumatoria de salarios
	*/
	private String fechaFinExclusionSumatoriaPadre;
	/**
	* Fecha inicio de exclusion de sumatoria de salarios
	*/
	private String fechaInicioExclusionSumatoriaMadre;

	/**
	* Fecha fin de exclusion de sumatoria de salarios
	*/
	private String fechaFinExclusionSumatoriaMadre;

	/**
	 * 
	 */
	public InfoPadresBiologicosOutDTO() {
	}

	/**
	 * @param tipoIdPadreBiologico
	 * @param identificacionPadreBiologico
	 * @param nombrePadreBiologico
	 * @param tipoIdMadreBiologica
	 * @param identificacionMadreBiologica
	 * @param nombreMadreBiologica
	 */
	public InfoPadresBiologicosOutDTO(String tipoIdPadreBiologico, String identificacionPadreBiologico,
			String nombrePadreBiologico, String tipoIdMadreBiologica, String identificacionMadreBiologica,
			String nombreMadreBiologica, String fechaInicioExclusionSumatoriaMadre, String fechaFinExclusionSumatoriaMadre,
			String fechaInicioExclusionSumatoriaPadre, String fechaFinExclusionSumatoriaPadre) {
		this.tipoIdPadreBiologico = tipoIdPadreBiologico;
		this.identificacionPadreBiologico = identificacionPadreBiologico;
		this.nombrePadreBiologico = nombrePadreBiologico;
		this.tipoIdMadreBiologica = tipoIdMadreBiologica;
		this.identificacionMadreBiologica = identificacionMadreBiologica;
		this.nombreMadreBiologica = nombreMadreBiologica;
		this.fechaInicioExclusionSumatoriaMadre = fechaInicioExclusionSumatoriaMadre;
		this.fechaFinExclusionSumatoriaMadre = fechaFinExclusionSumatoriaMadre;
		this.fechaInicioExclusionSumatoriaPadre = fechaInicioExclusionSumatoriaPadre;
		this.fechaFinExclusionSumatoriaPadre = fechaFinExclusionSumatoriaPadre;
	}

	/**
	 * @return the tipoIdPadreBiologico
	 */
	public String getTipoIdPadreBiologico() {
		return tipoIdPadreBiologico;
	}

	public String getFechaInicioExclusionSumatoriaPadre() {
		return this.fechaInicioExclusionSumatoriaPadre;
	}

	public void setFechaInicioExclusionSumatoriaPadre(String fechaInicioExclusionSumatoriaPadre) {
		this.fechaInicioExclusionSumatoriaPadre = fechaInicioExclusionSumatoriaPadre;
	}

	public String getFechaFinExclusionSumatoriaPadre() {
		return this.fechaFinExclusionSumatoriaPadre;
	}

	public void setFechaFinExclusionSumatoriaPadre(String fechaFinExclusionSumatoriaPadre) {
		this.fechaFinExclusionSumatoriaPadre = fechaFinExclusionSumatoriaPadre;
	}

	public String getFechaInicioExclusionSumatoriaMadre() {
		return this.fechaInicioExclusionSumatoriaMadre;
	}

	public void setFechaInicioExclusionSumatoriaMadre(String fechaInicioExclusionSumatoriaMadre) {
		this.fechaInicioExclusionSumatoriaMadre = fechaInicioExclusionSumatoriaMadre;
	}

	public String getFechaFinExclusionSumatoriaMadre() {
		return this.fechaFinExclusionSumatoriaMadre;
	}

	public void setFechaFinExclusionSumatoriaMadre(String fechaFinExclusionSumatoriaMadre) {
		this.fechaFinExclusionSumatoriaMadre = fechaFinExclusionSumatoriaMadre;
	}
	/**
	 * @param tipoIdPadreBiologico the tipoIdPadreBiologico to set
	 */
	public void setTipoIdPadreBiologico(String tipoIdPadreBiologico) {
		this.tipoIdPadreBiologico = tipoIdPadreBiologico;
	}

	/**
	 * @return the identificacionPadreBiologico
	 */
	public String getIdentificacionPadreBiologico() {
		return identificacionPadreBiologico;
	}

	/**
	 * @param identificacionPadreBiologico the identificacionPadreBiologico to set
	 */
	public void setIdentificacionPadreBiologico(String identificacionPadreBiologico) {
		this.identificacionPadreBiologico = identificacionPadreBiologico;
	}

	/**
	 * @return the nombrePadreBiologico
	 */
	public String getNombrePadreBiologico() {
		return nombrePadreBiologico;
	}

	/**
	 * @param nombrePadreBiologico the nombrePadreBiologico to set
	 */
	public void setNombrePadreBiologico(String nombrePadreBiologico) {
		this.nombrePadreBiologico = nombrePadreBiologico;
	}

	/**
	 * @return the tipoIdMadreBiologica
	 */
	public String getTipoIdMadreBiologica() {
		return tipoIdMadreBiologica;
	}

	/**
	 * @param tipoIdMadreBiologica the tipoIdMadreBiologica to set
	 */
	public void setTipoIdMadreBiologica(String tipoIdMadreBiologica) {
		this.tipoIdMadreBiologica = tipoIdMadreBiologica;
	}

	/**
	 * @return the identificacionMadreBiologica
	 */
	public String getIdentificacionMadreBiologica() {
		return identificacionMadreBiologica;
	}

	/**
	 * @param identificacionMadreBiologica the identificacionMadreBiologica to set
	 */
	public void setIdentificacionMadreBiologica(String identificacionMadreBiologica) {
		this.identificacionMadreBiologica = identificacionMadreBiologica;
	}

	/**
	 * @return the nombreMadreBiologica
	 */
	public String getNombreMadreBiologica() {
		return nombreMadreBiologica;
	}

	/**
	 * @param nombreMadreBiologica the nombreMadreBiologica to set
	 */
	public void setNombreMadreBiologica(String nombreMadreBiologica) {
		this.nombreMadreBiologica = nombreMadreBiologica;
	}
}
