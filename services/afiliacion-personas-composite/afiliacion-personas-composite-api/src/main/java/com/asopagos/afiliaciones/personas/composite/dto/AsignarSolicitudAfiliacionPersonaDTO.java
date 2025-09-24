package com.asopagos.afiliaciones.personas.composite.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;

/**
 *
 * @author sbrinez
 */
public class AsignarSolicitudAfiliacionPersonaDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String numeroRadicado;

    private MetodoAsignacionBackEnum metodoAsignacion;

    private String destinatario;

    private Long idSolicitudGlobal;
    
    private String observacion;

    private Long idTarea;
    
    private String idInstanciaProceso;
    
    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado
     *        the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the metodoAsignacion
     */
    public MetodoAsignacionBackEnum getMetodoAsignacion() {
        return metodoAsignacion;
    }

    /**
     * @param metodoAsignacion
     *        the metodoAsignacion to set
     */
    public void setMetodoAsignacion(MetodoAsignacionBackEnum metodoAsignacion) {
        this.metodoAsignacion = metodoAsignacion;
    }

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario
     *        the destinatario to set
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea the idTarea to set
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}
}
