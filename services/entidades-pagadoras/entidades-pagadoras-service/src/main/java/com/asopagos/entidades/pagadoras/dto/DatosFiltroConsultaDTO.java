package com.asopagos.entidades.pagadoras.dto;

import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;

/**
 * Objeto con los filtros de consulta
 * 
 * 
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU 109<br/>
 *
 * @author <a href="mailto:halzate@heinsohn.com.co"> halzate</a>
 */

public class DatosFiltroConsultaDTO extends QueryFilterInDTO {

    private Long idEntidadPagadora;

    private String consecutivoGestion;

	/**
	 * @return the idEntidadPagadora
	 */
	public Long getIdEntidadPagadora() {
		return idEntidadPagadora;
	}

	/**
	 * @param idEntidadPagadora the idEntidadPagadora to set
	 */
	public void setIdEntidadPagadora(Long idEntidadPagadora) {
		this.idEntidadPagadora = idEntidadPagadora;
	}

	/**
	 * @return the consecutivoGestion
	 */
	public String getConsecutivoGestion() {
		return consecutivoGestion;
	}

	/**
	 * @param consecutivoGestion the consecutivoGestion to set
	 */
	public void setConsecutivoGestion(String consecutivoGestion) {
		this.consecutivoGestion = consecutivoGestion;
	}
}
