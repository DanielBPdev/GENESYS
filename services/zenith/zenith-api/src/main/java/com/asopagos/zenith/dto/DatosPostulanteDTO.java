package com.asopagos.zenith.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
public class DatosPostulanteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MiembroGrupoPostulanteDTO> miembrosGrupo;
	private List<RegistroDetalleAporteDTO> detallesAportes;
	
	
	/**
	 * 
	 */
	public DatosPostulanteDTO() {
	}
	
	/**
	 * @param miembrosGrupo
	 * @param detallesAportes
	 */
	public DatosPostulanteDTO(List<MiembroGrupoPostulanteDTO> miembrosGrupo,
			List<RegistroDetalleAporteDTO> detallesAportes) {
		this.miembrosGrupo = miembrosGrupo;
		this.detallesAportes = detallesAportes;
	}
	
	/**
	 * @return the miembrosGrupo
	 */
	public List<MiembroGrupoPostulanteDTO> getMiembrosGrupo() {
		return miembrosGrupo;
	}
	/**
	 * @param miembrosGrupo the miembrosGrupo to set
	 */
	public void setMiembrosGrupo(List<MiembroGrupoPostulanteDTO> miembrosGrupo) {
		this.miembrosGrupo = miembrosGrupo;
	}
	/**
	 * @return the detallesAportes
	 */
	public List<RegistroDetalleAporteDTO> getDetallesAportes() {
		return detallesAportes;
	}
	/**
	 * @param detallesAportes the detallesAportes to set
	 */
	public void setDetallesAportes(List<RegistroDetalleAporteDTO> detallesAportes) {
		this.detallesAportes = detallesAportes;
	}
}
