package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;

/**
 * <b>Descripción:</b> DTO que transporta los datos para el procesamiento de la sucursal que entra por PILA
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
@XmlRootElement
public class DatosRegistroSucursalPilaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO;
	private Long idPersonaCotizante;

	/**
	 * 
	 */
	public DatosRegistroSucursalPilaDTO() {
	}
	
	/**
	 * @param sucursalEmpresaModeloDTO
	 * @param idPersonaCotizante
	 */
	public DatosRegistroSucursalPilaDTO(SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO, Long idPersonaCotizante) {
		this.sucursalEmpresaModeloDTO = sucursalEmpresaModeloDTO;
		this.idPersonaCotizante = idPersonaCotizante;
	}

	/**
	 * Método que retorna el valor de sucursalEmpresaModeloDTO.
	 * @return valor de sucursalEmpresaModeloDTO.
	 */
	public SucursalEmpresaModeloDTO getSucursalEmpresaModeloDTO() {
		return sucursalEmpresaModeloDTO;
	}

	/**
	 * Método encargado de modificar el valor de sucursalEmpresaModeloDTO.
	 * @param valor para modificar sucursalEmpresaModeloDTO.
	 */
	public void setSucursalEmpresaModeloDTO(SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO) {
		this.sucursalEmpresaModeloDTO = sucursalEmpresaModeloDTO;
	}

	/**
	 * Método que retorna el valor de idPersonaCotizante.
	 * @return valor de idPersonaCotizante.
	 */
	public Long getIdPersonaCotizante() {
		return idPersonaCotizante;
	}

	/**
	 * Método encargado de modificar el valor de idPersonaCotizante.
	 * @param valor para modificar idPersonaCotizante.
	 */
	public void setIdPersonaCotizante(Long idPersonaCotizante) {
		this.idPersonaCotizante = idPersonaCotizante;
	}
}
