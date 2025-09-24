package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EscalamientoSolicitudDTO;

/**
 * Clase que define los datos de entrada de la HU de verificación de la gestión
 * del producto no conforme subsanable
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
@XmlRootElement
public class VerificarGestionPNCNovedadDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Variable idSolicitud
	 */
	private Long idSolicitud;
	/**
	 * Variable idTarea
	 */
	private Long idTarea;
	/**
	 * Variable resultadoVerifPNC
	 */
	private Integer resultadoVerifPNC;
	/**
	 * Variable escalamientoSolicitud
	 */
	private EscalamientoSolicitudDTO escalamientoSolicitud;


	/**
	 * Método encargado de retornar el valor del campo idSolicitud
	 * 
	 * @return el campo idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de asignar el valor al campo idSolicitud
	 * 
	 * @param idSolicitud
	 *            idSolicitud a asignar
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método encargado de retornar el valor del campo idTarea
	 * 
	 * @return el campo idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * Método encargado de asignar el valor al campo idTarea
	 * 
	 * @param idTarea
	 *            idTarea a asignar
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * Método encargado de retornar el valor del campo resultadoVerifPNC
	 * 
	 * @return el campo resultadoVerifPNC
	 */
	public Integer getResultadoVerifPNC() {
		return resultadoVerifPNC;
	}

	/**
	 * Método encargado de asignar el valor al campo resultadoVerifPNC
	 * 
	 * @param resultadoVerifPNC
	 *            resultadoVerifPNC a asignar
	 */
	public void setResultadoVerifPNC(Integer resultadoVerifPNC) {
		this.resultadoVerifPNC = resultadoVerifPNC;
	}

	/**
	 * Método encargado de retornar el valor del campo escalamientoSolicitud
	 * 
	 * @return el campo escalamientoSolicitud
	 */
	public EscalamientoSolicitudDTO getEscalamientoSolicitud() {
		return escalamientoSolicitud;
	}

	/**
	 * Método encargado de asignar el valor al campo escalamientoSolicitud
	 * 
	 * @param escalamientoSolicitud
	 *            escalamientoSolicitud a asignar
	 */
	public void setEscalamientoSolicitud(EscalamientoSolicitudDTO escalamientoSolicitud) {
		this.escalamientoSolicitud = escalamientoSolicitud;
	}

}
