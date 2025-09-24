package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de entrada para consumo del
 * servicio <code>finalizarEvaluacionAnalista</code> <br/>
 * <b>Historia de Usuario: </b> HU-008
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class EvaluacionAnalistaDTO implements Serializable {
	/**
	 * Serial
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
	 * Resultado de evaluación de la solicitud
	 */
	private ResultadoProcesoEnum evaluacionSolicitud;

	/**
	 * Comentario ingresado por el analista
	 */
	private String observacion;

	/**
	 * Indica el proceso de negocio ligado a la solicitud
	 */
	private ProcesoEnum proceso;
	
	/**
	 * Usuario supervisor al que se le asigna la solicitud.
	 */
	private String usuarioSupervisor;

	/**
     * Método que retorna el valor de usuarioSupervisor.
     * @return valor de usuarioSupervisor.
     */
    public String getUsuarioSupervisor() {
        return usuarioSupervisor;
    }

    /**
     * Método encargado de modificar el valor de usuarioSupervisor.
     * @param valor para modificar usuarioSupervisor.
     */
    public void setUsuarioSupervisor(String usuarioSupervisor) {
        this.usuarioSupervisor = usuarioSupervisor;
    }

    /**
	 * Obtiene el valor de idSolicitud
	 * 
	 * @return El valor de idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Establece el valor de idSolicitud
	 * 
	 * @param idSolicitud
	 *            El valor de idSolicitud por asignar
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Obtiene el valor de idTarea
	 * 
	 * @return El valor de idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * Establece el valor de idTarea
	 * 
	 * @param idTarea
	 *            El valor de idTarea por asignar
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * Obtiene el valor de evaluacionSolicitud
	 * 
	 * @return El valor de evaluacionSolicitud
	 */
	public ResultadoProcesoEnum getEvaluacionSolicitud() {
		return evaluacionSolicitud;
	}

	/**
	 * Establece el valor de evaluacionSolicitud
	 * 
	 * @param evaluacionSolicitud
	 *            El valor de evaluacionSolicitud por asignar
	 */
	public void setEvaluacionSolicitud(ResultadoProcesoEnum evaluacionSolicitud) {
		this.evaluacionSolicitud = evaluacionSolicitud;
	}

	/**
	 * Obtiene el valor de observacion
	 * 
	 * @return El valor de observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * Establece el valor de observacion
	 * 
	 * @param observacion
	 *            El valor de observacion por asignar
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}


	/**
	 * Obtiene el valor de proceso
	 * 
	 * @return El valor de proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/**
	 * Establece el valor de proceso
	 * 
	 * @param proceso
	 *            El valor de proceso por asignar
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}
}
