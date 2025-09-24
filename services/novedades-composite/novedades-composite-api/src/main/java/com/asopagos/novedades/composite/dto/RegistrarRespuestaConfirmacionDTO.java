package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que define los datos de entrada para la HU de Registrar respuesta de
 * confirmación de retiro
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
@XmlRootElement
public class RegistrarRespuestaConfirmacionDTO implements Serializable {

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
	 * Variable resultadoConfirmacion
	 */
	private Integer resultadoConfirmacion;
	
	/**
	 * Id que identifica el documento de soporte de gestión en el ECM
	 */
	private String idDocumento;

	/**
	 * nombre que hace alusión al documento cargado
	 */
	private String nombreDocumento;
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
	 * Método encargado de retornar el valor del campo resultadoConfirmacion
	 * @return el campo resultadoConfirmacion
	 */
	public Integer getResultadoConfirmacion() {
		return resultadoConfirmacion;
	}

	/**
	 * Método encargado de asignar el valor al campo resultadoConfirmacion
	 * @param resultadoConfirmacion resultadoConfirmacion a asignar
	 */
	public void setResultadoConfirmacion(Integer resultadoConfirmacion) {
		this.resultadoConfirmacion = resultadoConfirmacion;
	}

	/**
	 * Método que retorna el valor de idDocumento.
	 * @return valor de idDocumento.
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * Método encargado de modificar el valor de idDocumento.
	 * @param valor para modificar idDocumento.
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * Método que retorna el valor de nombreDocumento.
	 * @return valor de nombreDocumento.
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * Método encargado de modificar el valor de nombreDocumento.
	 * @param valor para modificar nombreDocumento.
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
}
