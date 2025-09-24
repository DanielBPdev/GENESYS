package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * DTO para representar los datos que deben procesarse en el servicio finalizarEvaluacionSupervisor.
 * @author <a href="mailto:criparra@heinsohn.com.co> Cristian David Parra Zuluaga </a> 
 */
@XmlRootElement
public class EvaluacionSupervisorDTO implements Serializable {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la solicitud.
	 */
	@NotNull
	private Long idSolicitud;
	
	/**
	 * Identificador de la tarea.
	 */
	@NotNull
	private Long idTarea;
	
	/**
	 * Evaluación de la solicitud.
	 */
	@NotNull
	private ResultadoProcesoEnum evaluacionSolicitud;
	
	/**
	 * Observaciones del supervisor cuando la evaluación es rechazada.
	 */
	@NotNull
	private String observacionesSupervisor;
	
	 /**
     * Indica el proceso de negocio ligado a la solicitud 
     */
	@NotNull
    private ProcesoEnum proceso;

	/**
	 * Método que retorna el valor de idSolicitud.
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de idTarea.
	 * @return valor de idTarea.
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * Método encargado de modificar el valor de idTarea.
	 * @param valor para modificar idTarea.
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * Método que retorna el valor de evaluacionSolicitud.
	 * @return valor de evaluacionSolicitud.
	 */
	public ResultadoProcesoEnum getEvaluacionSolicitud() {
		return evaluacionSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de evaluacionSolicitud.
	 * @param valor para modificar evaluacionSolicitud.
	 */
	public void setEvaluacionSolicitud(ResultadoProcesoEnum evaluacionSolicitud) {
		this.evaluacionSolicitud = evaluacionSolicitud;
	}

	/**
	 * Método que retorna el valor de observacionesSupervisor.
	 * @return valor de observacionesSupervisor.
	 */
	public String getObservacionesSupervisor() {
		return observacionesSupervisor;
	}

	/**
	 * Método encargado de modificar el valor de observacionesSupervisor.
	 * @param valor para modificar observacionesSupervisor.
	 */
	public void setObservacionesSupervisor(String observacionesSupervisor) {
		this.observacionesSupervisor = observacionesSupervisor;
	}

	/**Obtiene el valor de proceso
	 * @return El valor de proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/** Establece el valor de proceso
	 * @param proceso El valor de proceso por asignar
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}
}
