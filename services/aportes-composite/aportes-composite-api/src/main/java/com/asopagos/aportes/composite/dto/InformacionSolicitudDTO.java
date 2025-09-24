package com.asopagos.aportes.composite.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.rest.security.dto.UserDTO;

/**
 * DTO que tiene la infomarción que se envia al servicio FinalizarCorreccionAsync 
 * datos de la solicitud, tarea y el usuario que genstiona la corrección
 * 
 * @author clmarin
 *
 */
@XmlRootElement
public class InformacionSolicitudDTO implements Serializable{

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la solicitud
	 */
	private Long idSolicitud;
	
	/**
	 * Identificador de la tarea 
	 */
	private Long idTarea;
	
	/**
	 * Instancia de proceso relacionado a la solicitud 
	 */
	private Long instaciaProceso; 
	
	/**
	 * Usuario que gestiona la solicitud
	 */
	private UserDTO userDTO;

	/**
	 * Método constructor
	 */
	public InformacionSolicitudDTO() {
	}

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea the idTarea to set
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the instaciaProceso
	 */
	public Long getInstaciaProceso() {
		return instaciaProceso;
	}

	/**
	 * @param instaciaProceso the instaciaProceso to set
	 */
	public void setInstaciaProceso(Long instaciaProceso) {
		this.instaciaProceso = instaciaProceso;
	}

	/**
	 * @return the userDTO
	 */
	public UserDTO getUserDTO() {
		return userDTO;
	}

	/**
	 * @param userDTO the userDTO to set
	 */
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
}
