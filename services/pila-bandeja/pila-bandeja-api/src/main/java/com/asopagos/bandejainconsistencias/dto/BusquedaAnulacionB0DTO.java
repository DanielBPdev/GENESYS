package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class BusquedaAnulacionB0DTO implements Serializable {
    private static final long serialVersionUID = 4427171631966087380L;

    private IndicePlanilla indicePlanilla;
    private EstadoArchivoPorBloque estadoArchivoPorBloque;
    private ErrorValidacionLog errorValidacionLog;

    public BusquedaAnulacionB0DTO(IndicePlanilla indicePlanilla, EstadoArchivoPorBloque estadoArchivoPorBloque,
            ErrorValidacionLog errorValidacionLog) {
        this.indicePlanilla = indicePlanilla;
        this.estadoArchivoPorBloque = estadoArchivoPorBloque;
        this.errorValidacionLog = errorValidacionLog;
    }

    /**
     * @return the indicePlanilla
     */
    public IndicePlanilla getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanilla indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the estadoArchivoPorBloque
     */
    public EstadoArchivoPorBloque getEstadoArchivoPorBloque() {
        return estadoArchivoPorBloque;
    }

    /**
     * @param estadoArchivoPorBloque the estadoArchivoPorBloque to set
     */
    public void setEstadoArchivoPorBloque(EstadoArchivoPorBloque estadoArchivoPorBloque) {
        this.estadoArchivoPorBloque = estadoArchivoPorBloque;
    }

    /**
     * @return the errorValidacionLog
     */
    public ErrorValidacionLog getErrorValidacionLog() {
        return errorValidacionLog;
    }

    /**
     * @param errorValidacionLog the errorValidacionLog to set
     */
    public void setErrorValidacionLog(ErrorValidacionLog errorValidacionLog) {
        this.errorValidacionLog = errorValidacionLog;
    }

	public BusquedaAnulacionB0DTO() {}
}
