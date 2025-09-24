package com.asopagos.clienteanibol.dto;

import java.util.List;

public class ResultadoDispersionAnibolDTO {

	private String idSolicitud;
	private List<ResultadoProcesamientoDTO> listadoResultadosProcesamiento;
	
	public ResultadoDispersionAnibolDTO() {
	}
	
	public ResultadoDispersionAnibolDTO(String idSolicitud,
			List<ResultadoProcesamientoDTO> listadoResultadosProcesamiento) {
		this.idSolicitud = idSolicitud;
		this.listadoResultadosProcesamiento = listadoResultadosProcesamiento;
	}
	
	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}
	
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	/**
	 * @return the listadoResultadosProcesamiento
	 */
	public List<ResultadoProcesamientoDTO> getListadoResultadosProcesamiento() {
		return listadoResultadosProcesamiento;
	}
	
	/**
	 * @param listadoResultadosProcesamiento the listadoResultadosProcesamiento to set
	 */
	public void setListadoResultadosProcesamiento(List<ResultadoProcesamientoDTO> listadoResultadosProcesamiento) {
		this.listadoResultadosProcesamiento = listadoResultadosProcesamiento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultadoDispersionAnibolDTO [");
		if (idSolicitud != null) {
			builder.append("idSolicitud=");
			builder.append(idSolicitud);
			builder.append(", ");
		}
		if (listadoResultadosProcesamiento != null) {
			builder.append("listadoResultadosProcesamiento=");
			builder.append(listadoResultadosProcesamiento);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}