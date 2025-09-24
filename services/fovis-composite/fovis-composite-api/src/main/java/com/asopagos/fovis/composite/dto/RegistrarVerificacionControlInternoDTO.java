package com.asopagos.fovis.composite.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para el registro de la verificación de control interno.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-031 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class RegistrarVerificacionControlInternoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private List<SolicitudPostulacionModeloDTO> solicitudesPostulacion;
    
    private List<UserDTO> usuariosControlInterno;

	/**
	 * @return the solicitudesPostulacion
	 */
	public List<SolicitudPostulacionModeloDTO> getSolicitudesPostulacion() {
		return solicitudesPostulacion;
	}

	/**
	 * @param solicitudesPostulacion the solicitudesPostulacion to set
	 */
	public void setSolicitudesPostulacion(List<SolicitudPostulacionModeloDTO> solicitudesPostulacion) {
		this.solicitudesPostulacion = solicitudesPostulacion;
	}

	/**
	 * @return the usuariosControlInterno
	 */
	public List<UserDTO> getUsuariosControlInterno() {
		return usuariosControlInterno;
	}

	/**
	 * @param usuariosControlInterno the usuariosControlInterno to set
	 */
	public void setUsuariosControlInterno(List<UserDTO> usuariosControlInterno) {
		this.usuariosControlInterno = usuariosControlInterno;
	}
    
}
