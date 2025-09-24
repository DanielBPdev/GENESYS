package com.asopagos.legalizacionfovis.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.legalizacionfovis.composite.enums.TipoAsignacionLegalizacionFovisEnum;

/**
 * Clase que define los atributos necesarios para terminar la tarea de asignar
 * solicitud al back
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class AsignarSolicitudLegalizacionDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable usuario
	 */
	private String usuario;

	/**
	 * Tipo de Asignación a realizar.
	 */
	private TipoAsignacionLegalizacionFovisEnum tipoAsignacion;
	
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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

	/**
     * @return the tipoAsignacion
     */
    public TipoAsignacionLegalizacionFovisEnum getTipoAsignacion() {
        return tipoAsignacion;
    }

    /**
     * @param tipoAsignacion the tipoAsignacion to set
     */
    public void setTipoAsignacion(TipoAsignacionLegalizacionFovisEnum tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

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
