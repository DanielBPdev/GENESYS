package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripción: </b> DTO que almacena toda la información resultate del
 * proceso de ejecución de asignación FOVIS <br/>
 * <b>Historia de Usuario: </b> HU-047
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class EjecucionAsignacionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 18798745757454L;

	/**
	 * Lista de resultados de asignación
	 */
	private List<ResultadoAsignacionDTO> listaResultadoAsignacionDTO;

	/**
	 * Lista de resumen de asignación, por modalidad
	 */
	private List<ResumenAsignacionDTO> listaResumenAsignacionDTO;

	/**
	 * Comentarios ingresados por el coordinador FOVIS
	 */
	private String comentarioCoordinador;

	/**
	 * Obtiene el valor de listaResultadoAsignacionDTO
	 * 
	 * @return El valor de listaResultadoAsignacionDTO
	 */
	public List<ResultadoAsignacionDTO> getListaResultadoAsignacionDTO() {
		return listaResultadoAsignacionDTO;
	}

	/**
	 * Establece el valor de listaResultadoAsignacionDTO
	 * 
	 * @param listaResultadoAsignacionDTO
	 *            El valor de listaResultadoAsignacionDTO por asignar
	 */
	public void setListaResultadoAsignacionDTO(List<ResultadoAsignacionDTO> listaResultadoAsignacionDTO) {
		this.listaResultadoAsignacionDTO = listaResultadoAsignacionDTO;
	}

	/**
	 * Obtiene el valor de listaResumenAsignacionDTO
	 * 
	 * @return El valor de listaResumenAsignacionDTO
	 */
	public List<ResumenAsignacionDTO> getListaResumenAsignacionDTO() {
		return listaResumenAsignacionDTO;
	}

	/**
	 * Establece el valor de listaResumenAsignacionDTO
	 * 
	 * @param listaResumenAsignacionDTO
	 *            El valor de listaResumenAsignacionDTO por asignar
	 */
	public void setListaResumenAsignacionDTO(List<ResumenAsignacionDTO> listaResumenAsignacionDTO) {
		this.listaResumenAsignacionDTO = listaResumenAsignacionDTO;
	}

	/**
	 * Obtiene el valor de comentarioCoordinador
	 * 
	 * @return El valor de comentarioCoordinador
	 */
	public String getComentarioCoordinador() {
		return comentarioCoordinador;
	}

	/**
	 * Establece el valor de comentarioCoordinador
	 * 
	 * @param comentarioCoordinador
	 *            El valor de comentarioCoordinador por asignar
	 */
	public void setComentarioCoordinador(String comentarioCoordinador) {
		this.comentarioCoordinador = comentarioCoordinador;
	}
}
