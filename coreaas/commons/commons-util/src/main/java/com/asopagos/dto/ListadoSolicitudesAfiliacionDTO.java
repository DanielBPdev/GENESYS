package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO encargado de tener el listado de solicitudes de afiliacion persona
 * 
 * @author jusanchez
 *
 */
@XmlRootElement
public class ListadoSolicitudesAfiliacionDTO implements Serializable {

	/**
	 * Lista de solicitudes de afiliacion
	 */
	private List<SolicitudAfiliacionPersonaDTO> lstSolicitudes;

	public ListadoSolicitudesAfiliacionDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the lstSolicitudes
	 */
	public List<SolicitudAfiliacionPersonaDTO> getLstSolicitudes() {
		return lstSolicitudes;
	}

	/**
	 * @param lstSolicitudes
	 *            the lstSolicitudes to set
	 */
	public void setLstSolicitudes(List<SolicitudAfiliacionPersonaDTO> lstSolicitudes) {
		this.lstSolicitudes = lstSolicitudes;
	}

}
