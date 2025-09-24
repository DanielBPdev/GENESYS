package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que define los datos de entrada para la HU de consultar el análisis del
 * escalamiento de la solicitud de novedad
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
@XmlRootElement
public class ConsultarAnalisisNovedadDTO implements Serializable {

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
	 * Variable resultadoAnalisisEsp
	 */
	private Integer resultadoAnalisisEsp;


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
	 * Método encargado de retornar el valor del campo resultadoAnalisisEsp
	 * 
	 * @return el campo resultadoAnalisisEsp
	 */
	public Integer getResultadoAnalisisEsp() {
		return resultadoAnalisisEsp;
	}

	/**
	 * Método encargado de asignar el valor al campo resultadoAnalisisEsp
	 * 
	 * @param resultadoAnalisisEsp
	 *            resultadoAnalisisEsp a asignar
	 */
	public void setResultadoAnalisisEsp(Integer resultadoAnalisisEsp) {
		this.resultadoAnalisisEsp = resultadoAnalisisEsp;
	}

	
}
