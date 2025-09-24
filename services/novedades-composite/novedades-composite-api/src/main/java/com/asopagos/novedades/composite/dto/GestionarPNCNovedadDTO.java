package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Clase que define los datos de entrada para la HU de Gestionar producto no
 * conforme subsanable de la la solicitud de novedad
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
@XmlRootElement
public class GestionarPNCNovedadDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable idTarea
	 */
	private Long idTarea;

	/**
	 * Variable idSolicitud
	 */
	private Long idSolicitud;
	
	/**
	 * Variable tipoTransanccion
	 */
	private TipoTransaccionEnum tipoTransaccion;

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
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

}
