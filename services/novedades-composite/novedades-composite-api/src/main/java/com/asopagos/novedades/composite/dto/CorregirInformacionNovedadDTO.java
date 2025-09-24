package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Clase que define los datos de entrada para el servicio de Corregir
 * Información por parte del usuario
 * 
 * @author Jorge Leonardo Camargo Cuervo<jcamargo@heinsohn.com.co>
 *
 */
public class CorregirInformacionNovedadDTO implements Serializable {
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
	 * Variable tipoTransaccionEnum
	 */
	private TipoTransaccionEnum tipoTransaccionEnum;

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
	 * Método encargado de retornar el valor del campo tipoTransaccionEnum
	 * @return el campo tipoTransaccionEnum
	 */
	public TipoTransaccionEnum getTipoTransaccionEnum() {
		return tipoTransaccionEnum;
	}

	/**
	 * Método encargado de asignar el valor al campo tipoTransaccionEnum
	 * @param tipoTransaccionEnum tipoTransaccionEnum a asignar
	 */
	public void setTipoTransaccionEnum(TipoTransaccionEnum tipoTransaccionEnum) {
		this.tipoTransaccionEnum = tipoTransaccionEnum;
	}
}
