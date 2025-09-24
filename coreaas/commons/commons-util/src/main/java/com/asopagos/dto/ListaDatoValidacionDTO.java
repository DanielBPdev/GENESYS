package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;

/**
 * <b>Descripción:</b> DTO que transporta la lista de datos de validacion de
 * archivo de cargue multiple Proceso: 122 HU: 363-364
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class ListaDatoValidacionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista con los candidatos aprobados.
	 */
	private List<TrabajadorCandidatoNovedadDTO> candidatoNovedadDTOAprobado;

	/**
	 * Lista con los candidatos no aprobados.
	 */
	private List<TrabajadorCandidatoNovedadDTO> candidatoNovedadDTONoAprobado;

	/**
	 * Lista con los candidatos aprobados.
	 */
	private List<AfiliarTrabajadorCandidatoDTO> candidatoAfiliacionDTOAprobado;

	/**
	 * Lista con los candidatos no aprobados.
	 */
	private List<AfiliarTrabajadorCandidatoDTO> candidatoAfiliacionDTONoAprobado;

	/**
	 * Método que retorna el valor de candidatoNovedadDTOAprobado.
	 * @return valor de candidatoNovedadDTOAprobado.
	 */
	public List<TrabajadorCandidatoNovedadDTO> getCandidatoNovedadDTOAprobado() {
		return candidatoNovedadDTOAprobado;
	}

	/**
	 * Método encargado de modificar el valor de candidatoNovedadDTOAprobado.
	 * @param valor para modificar candidatoNovedadDTOAprobado.
	 */
	public void setCandidatoNovedadDTOAprobado(List<TrabajadorCandidatoNovedadDTO> candidatoNovedadDTOAprobado) {
		this.candidatoNovedadDTOAprobado = candidatoNovedadDTOAprobado;
	}

	/**
	 * Método que retorna el valor de candidatoNovedadDTONoAprobado.
	 * @return valor de candidatoNovedadDTONoAprobado.
	 */
	public List<TrabajadorCandidatoNovedadDTO> getCandidatoNovedadDTONoAprobado() {
		return candidatoNovedadDTONoAprobado;
	}

	/**
	 * Método encargado de modificar el valor de candidatoNovedadDTONoAprobado.
	 * @param valor para modificar candidatoNovedadDTONoAprobado.
	 */
	public void setCandidatoNovedadDTONoAprobado(List<TrabajadorCandidatoNovedadDTO> candidatoNovedadDTONoAprobado) {
		this.candidatoNovedadDTONoAprobado = candidatoNovedadDTONoAprobado;
	}

	/**
	 * Método que retorna el valor de candidatoAfiliacionDTOAprobado.
	 * @return valor de candidatoAfiliacionDTOAprobado.
	 */
	public List<AfiliarTrabajadorCandidatoDTO> getCandidatoAfiliacionDTOAprobado() {
		return candidatoAfiliacionDTOAprobado;
	}

	/**
	 * Método encargado de modificar el valor de candidatoAfiliacionDTOAprobado.
	 * @param valor para modificar candidatoAfiliacionDTOAprobado.
	 */
	public void setCandidatoAfiliacionDTOAprobado(List<AfiliarTrabajadorCandidatoDTO> candidatoAfiliacionDTOAprobado) {
		this.candidatoAfiliacionDTOAprobado = candidatoAfiliacionDTOAprobado;
	}

	/**
	 * Método que retorna el valor de candidatoAfiliacionDTONoAprobado.
	 * @return valor de candidatoAfiliacionDTONoAprobado.
	 */
	public List<AfiliarTrabajadorCandidatoDTO> getCandidatoAfiliacionDTONoAprobado() {
		return candidatoAfiliacionDTONoAprobado;
	}

	/**
	 * Método encargado de modificar el valor de candidatoAfiliacionDTONoAprobado.
	 * @param valor para modificar candidatoAfiliacionDTONoAprobado.
	 */
	public void setCandidatoAfiliacionDTONoAprobado(List<AfiliarTrabajadorCandidatoDTO> candidatoAfiliacionDTONoAprobado) {
		this.candidatoAfiliacionDTONoAprobado = candidatoAfiliacionDTONoAprobado;
	}



}
