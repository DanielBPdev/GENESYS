package com.asopagos.dto.afiliaciones;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO para el servicio generarListadoSolicitudesRemisionBack HU
 * <b>Historia de Usuario:</b> HU-086
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@XmlRootElement
public class RemisionBackDTO {
	
	private List<ItemResumenRemisionBackDTO> resumen;
	
	private List<ListaDetalleRemisionBackDTO> detalle;

	/**
	 * @return the resumen
	 */
	public List<ItemResumenRemisionBackDTO> getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(List<ItemResumenRemisionBackDTO> resumen) {
		this.resumen = resumen;
	}

	/**
	 * @return the detalle
	 */
	public List<ListaDetalleRemisionBackDTO> getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(List<ListaDetalleRemisionBackDTO> detalle) {
		this.detalle = detalle;
	}
}
