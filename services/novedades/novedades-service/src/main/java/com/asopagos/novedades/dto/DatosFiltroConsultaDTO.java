package com.asopagos.novedades.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lista adicional de afiliados
	 */
	private List<AfiliadoModeloDTO> afiliadosDto;

	/**
	 * Lista de criterios de busquedad
	 */
	@NotNull
	private List<EstadoAfiliadoEnum> criteriosBusquedad;

	/**
	 * Código de la caja de compensación
	 */
	private String codigoEntidad;
	
	/**
	 * Total de registros en la consulta
	 */
	private Long totalRegistros;

	/**
	 * @return the afiliadosDto
	 */
	public List<AfiliadoModeloDTO> getAfiliadosDto() {
		return afiliadosDto;
	}

	/**
	 * @param afiliadosDto the afiliadosDto to set
	 */
	public void setAfiliadosDto(List<AfiliadoModeloDTO> afiliadosDto) {
		this.afiliadosDto = afiliadosDto;
	}

	/**
	 * @return the criteriosBusquedad
	 */
	public List<EstadoAfiliadoEnum> getCriteriosBusquedad() {
		return criteriosBusquedad;
	}

	/**
	 * @param criteriosBusquedad the criteriosBusquedad to set
	 */
	public void setCriteriosBusquedad(List<EstadoAfiliadoEnum> criteriosBusquedad) {
		this.criteriosBusquedad = criteriosBusquedad;
	}

	/**
	 * @return the codigoEntidad
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	/**
	 * @param codigoEntidad the codigoEntidad to set
	 */
	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	/**
	 * @return the totalRegistros
	 */
	public Long getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	
}
