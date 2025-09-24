package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Clase que define los atributos necesarios para terminar la tarea de asiganar
 * solicitud al back
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
/**
 * Clase que contiene la lógica para validar 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class AsignarSolicitudNovedadDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable usuarioBack
	 */
	private String usuarioBack;

	/**
	 * Variable idSolicitud
	 */
	private Long idSolicitud;

	/**
	 * Variable documentosFisicos
	 */
	private Boolean documentosFisicos;
	/**
	 * Variable idTarea
	 */
	private Long idTarea;
	
	/**
	 * Variable proceso
	 */
	private TipoTransaccionEnum tipoTransaccion;
	
	/**
	 * Variable que contiene el metodo de asignación.
	 */
	private MetodoAsignacionBackEnum metodoAsignacion;
	
	/**
	 * Variable que contiene la observación.
	 */
	private String observacion;
	

	/**
	 * Método encargado de retornar el valor del campo idTarea
	 * @return el campo idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * Método encargado de asignar el valor al campo idTarea
	 * @param idTarea idTarea a asignar
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * Método encargado de retornar el valor del campo documentosFisicos
	 * 
	 * @return el campo documentosFisicos
	 */
	public Boolean getDocumentosFisicos() {
		return documentosFisicos;
	}

	/**
	 * Método encargado de asignar el valor al campo documentosFisicos
	 * 
	 * @param documentosFisicos
	 *            documentosFisicos a asignar
	 */
	public void setDocumentosFisicos(Boolean documentosFisicos) {
		this.documentosFisicos = documentosFisicos;
	}

	/**
	 * Método encargado de retornar el valor del campo usuarioBack
	 * 
	 * @return el campo usuarioBack
	 */
	public String getUsuarioBack() {
		return usuarioBack;
	}

	/**
	 * Método encargado de asignar el valor al campo usuarioBack
	 * 
	 * @param usuarioBack
	 *            usuarioBack a asignar
	 */
	public void setUsuarioBack(String usuarioBack) {
		this.usuarioBack = usuarioBack;
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
	 * Método que retorna el valor de tipoTransaccion.
	 * @return valor de tipoTransaccion.
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * Método encargado de modificar el valor de tipoTransaccion.
	 * @param valor para modificar tipoTransaccion.
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Método que retorna el valor de metodoAsignacion.
	 * @return valor de metodoAsignacion.
	 */
	public MetodoAsignacionBackEnum getMetodoAsignacion() {
		return metodoAsignacion;
	}

	/**
	 * Método encargado de modificar el valor de metodoAsignacion.
	 * @param valor para modificar metodoAsignacion.
	 */
	public void setMetodoAsignacion(MetodoAsignacionBackEnum metodoAsignacion) {
		this.metodoAsignacion = metodoAsignacion;
	}

	/**
	 * Método que retorna el valor de observacion.
	 * @return valor de observacion.
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * Método encargado de modificar el valor de observacion.
	 * @param valor para modificar observacion.
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
