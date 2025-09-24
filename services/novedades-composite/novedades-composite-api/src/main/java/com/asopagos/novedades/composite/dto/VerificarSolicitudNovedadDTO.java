package com.asopagos.novedades.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EscalamientoSolicitudDTO;

/**
 * Clase que define los datos necesarios para la historia de usuario Verificar
 * Solicitud de novedad
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
@XmlRootElement
public class VerificarSolicitudNovedadDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable idSolicitudNovedad
	 */
	private Long idSolicitud;

	/**
	 * Variable resultadoVerificacionBack
	 */
	private Integer resultadoVerificacionBack;

	/**
	 * Variable escalamientoSolicitud
	 */
	private EscalamientoSolicitudDTO escalamientoSolicitud;
	
	/**
	 * Variable idTarea
	 */
	private Long idTarea;
	
	/**
	 * Variable instancia proceso
	 */
	private Long instanciaProceso;
	

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
	 * Método encargado de retornar el valor del campo idSolicitudNovedad
	 * 
	 * @return el campo idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de asignar el valor al campo idSolicitudNovedad
	 * 
	 * @param idSolicitud
	 *            idSolicitudNovedad a asignar
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método encargado de retornar el valor del campo resultadoVerificacionBack
	 * 
	 * @return el campo resultadoVerificacionBack
	 */
	public Integer getResultadoVerificacionBack() {
		return resultadoVerificacionBack;
	}

	/**
	 * Método encargado de asignar el valor al campo resultadoVerificacionBack
	 * 
	 * @param resultadoVerificacionBack
	 *            resultadoVerificacionBack a asignar
	 */
	public void setResultadoVerificacionBack(Integer resultadoVerificacionBack) {
		this.resultadoVerificacionBack = resultadoVerificacionBack;
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

    /**
     * @return the instanciaProceso
     */
    public Long getInstanciaProceso() {
        return instanciaProceso;
    }

    /**
     * @param instanciaProceso the instanciaProceso to set
     */
    public void setInstanciaProceso(Long instanciaProceso) {
        this.instanciaProceso = instanciaProceso;
    }
	
}
