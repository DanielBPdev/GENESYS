package com.asopagos.dto;

import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;

/**
 * Clase que representa los metadatos para actualizar los estados de archivos y registros de procesamiento de planilla PILA
 * 
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> </a>
 */
public class ActualizacionEstadosPlanillaDTO {

    @Override
	public String toString() {
		return "ActualizacionEstadosPlanillaDTO [idRegistroGeneral=" + idRegistroGeneral + ", estadoProceso="
				+ estadoProceso + ", accionProceso=" + accionProceso + ", bloqueValidacion=" + bloqueValidacion
				+ ", marcaHabilitacionGestionManual=" + marcaHabilitacionGestionManual + ", actualizaRegistroGeneral="
				+ actualizaRegistroGeneral + ", esReproceso399=" + esReproceso399 + ", usuario=" + usuario + "]";
	}

	/**
     * El identificador del registro general de procesamiento de la planilla
     */
    @NotNull
    private Long idRegistroGeneral;

    /**
     * El estado de proceso del archivo de planilla a actualizar
     */
    @NotNull
    private EstadoProcesoArchivoEnum estadoProceso;

    /**
     * La accion del proceso del archivo de planilla a actualizar
     */
    @NotNull
    private AccionProcesoArchivoEnum accionProceso;

    /**
     * El bloque de validacion donde se requiere actualizar el estado y accion correspondiente
     */
    @NotNull
    private BloqueValidacionEnum bloqueValidacion;

    /**
     * La marca que indica que el archivo esta disponible para la gestión manual
     */
    @NotNull
    private Boolean marcaHabilitacionGestionManual;

    /**
     * Indica si de sebe actualizar el estado de archivo del registro general de procesamiento de planilla PILA
     */
    @NotNull
    private Boolean actualizaRegistroGeneral;
    
    /**
     * Indica que la actualización de planilla se da por reproceso en bandeja de gestión 399
     * */
    private Boolean esReproceso399 = Boolean.FALSE;
    
    /**
     * Usuario que realiza la actualización
     * */
    private String usuario;

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral
     *        the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the estadoProceso
     */
    public EstadoProcesoArchivoEnum getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso
     *        the estadoProceso to set
     */
    public void setEstadoProceso(EstadoProcesoArchivoEnum estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the accionProceso
     */
    public AccionProcesoArchivoEnum getAccionProceso() {
        return accionProceso;
    }

    /**
     * @param accionProceso
     *        the accionProceso to set
     */
    public void setAccionProceso(AccionProcesoArchivoEnum accionProceso) {
        this.accionProceso = accionProceso;
    }

    /**
     * @return the bloqueValidacion
     */
    public BloqueValidacionEnum getBloqueValidacion() {
        return bloqueValidacion;
    }

    /**
     * @param bloqueValidacion
     *        the bloqueValidacion to set
     */
    public void setBloqueValidacion(BloqueValidacionEnum bloqueValidacion) {
        this.bloqueValidacion = bloqueValidacion;
    }

    /**
     * @return the marcaHabilitacionGestionManual
     */
    public Boolean getMarcaHabilitacionGestionManual() {
        return marcaHabilitacionGestionManual;
    }

    /**
     * @param marcaHabilitacionGestionManual
     *        the marcaHabilitacionGestionManual to set
     */
    public void setMarcaHabilitacionGestionManual(Boolean marcaHabilitacionGestionManual) {
        this.marcaHabilitacionGestionManual = marcaHabilitacionGestionManual;
    }

    /**
     * @return the actualizaRegistroGeneral
     */
    public Boolean getActualizaRegistroGeneral() {
        return actualizaRegistroGeneral;
    }

    /**
     * @param actualizaRegistroGeneral
     *        the actualizaRegistroGeneral to set
     */
    public void setActualizaRegistroGeneral(Boolean actualizaRegistroGeneral) {
        this.actualizaRegistroGeneral = actualizaRegistroGeneral;
    }

	/**
	 * @return the esReproceso399
	 */
	public Boolean getEsReproceso399() {
		return esReproceso399;
	}

	/**
	 * @param esReproceso399 the esReproceso399 to set
	 */
	public void setEsReproceso399(Boolean esReproceso399) {
		this.esReproceso399 = esReproceso399;
	}

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
}