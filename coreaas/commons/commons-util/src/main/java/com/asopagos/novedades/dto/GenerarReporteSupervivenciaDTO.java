/**
 * 
 */
package com.asopagos.novedades.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

/**
 * DTO que contiene los criterios de busquedad para generar reporte de supervivencia
 * 
 * @author jzambrano <jzambrano@heinsohn.com.co>
 *
 */
@XmlRootElement
public class GenerarReporteSupervivenciaDTO {

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

	
}
