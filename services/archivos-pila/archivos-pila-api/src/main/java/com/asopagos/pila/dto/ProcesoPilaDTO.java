package com.asopagos.pila.dto;

import java.util.Date;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;

/**
 * DTO para el paso de datos de un proceso PILA
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
public class ProcesoPilaDTO {
    /** ID del proceso */
    private Long id;

    /** # de solicitud del proceso */
    private String numeroRadicado;

    /** Tipo de proceso */
    private TipoProcesoPilaEnum tipoProceso;

    /** Fecha y hora de inicio */
    private Date fechaInicioProceso;

    /** Fecha y hora de finalización */
    private Date fechaFinProceso;

    /** Usuario que inicia el proceso */
    private String usuarioProceso;

    private EstadoProcesoValidacionEnum estadoProceso;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @return the tipoProceso
     */
    public TipoProcesoPilaEnum getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso
     *        the tipoProceso to set
     */
    public void setTipoProceso(TipoProcesoPilaEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * @return the fechaInicioProceso
     */
    public Date getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    /**
     * @param fechaInicioProceso
     *        the fechaInicioProceso to set
     */
    public void setFechaInicioProceso(Date fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    /**
     * @return the fechaFinProceso
     */
    public Date getFechaFinProceso() {
        return fechaFinProceso;
    }

    /**
     * @param fechaFinProceso
     *        the fechaFinProceso to set
     */
    public void setFechaFinProceso(Date fechaFinProceso) {
        this.fechaFinProceso = fechaFinProceso;
    }

    /**
     * @return the estadoProceso
     */
    public EstadoProcesoValidacionEnum getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso
     *        the estadoProceso to set
     */
    public void setEstadoProceso(EstadoProcesoValidacionEnum estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the usuarioProceso
     */
    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    /**
     * @param usuarioProceso
     *        the usuarioProceso to set
     */
    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }
}
