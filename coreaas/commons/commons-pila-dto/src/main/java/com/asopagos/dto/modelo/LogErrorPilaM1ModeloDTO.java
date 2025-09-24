package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.pila.soporte.LogErrorPilaM1;

/**
 * <b>Descripcion:</b> DTO que representa la entidad que representa la tabla LogErrorPilaM1<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class LogErrorPilaM1ModeloDTO implements Serializable {
    private static final long serialVersionUID = -2549777864784703136L;

    /**
     * Identificador único del registro
     */
    private Long id;

    /**
     * Identificador del índice de planilla OI en el que se presenta el error no controlado
     */
    private Long indicePlanillaOI;

    /**
     * Identificador del índice de planilla OF en el que se presenta el error no controlado
     */
    private Long indicePlanillaOF;

    /**
     * Fecha y hora en la que se presenta el error
     */
    private Long fechaHoraError;

    /**
     * Nombre del archivo en el que ocurre el error
     */
    private String nombreArchivo;

    /**
     * Mensaje de la excepción presentada
     */
    private String mensaje;

    /** Método para convertir de entidad a DTO */
    public void convertToDTO(LogErrorPilaM1 logErrorPilaM1) {
        this.id = logErrorPilaM1.getId();
        this.indicePlanillaOI = logErrorPilaM1.getIndicePlanillaOI();
        this.indicePlanillaOI = logErrorPilaM1.getIndicePlanillaOI();
        if (logErrorPilaM1.getFechaHoraError() != null) {
            this.fechaHoraError = logErrorPilaM1.getFechaHoraError().getTime();
        }
        this.nombreArchivo = logErrorPilaM1.getNombreArchivo();
        this.mensaje = logErrorPilaM1.getMensaje();
    }

    /** Método para convertir de DTO a Entity */
    public LogErrorPilaM1 convertToEntity() {
        LogErrorPilaM1 logErrorPilaM1 = new LogErrorPilaM1();
        logErrorPilaM1.setId(this.getId());
        logErrorPilaM1.setIndicePlanillaOI(this.getIndicePlanillaOI());
        logErrorPilaM1.setIndicePlanillaOI(this.getIndicePlanillaOI());
        if (this.getFechaHoraError() != null) {
            logErrorPilaM1.setFechaHoraError(new Date(this.getFechaHoraError()));
        }
        logErrorPilaM1.setNombreArchivo(this.getNombreArchivo());
        logErrorPilaM1.setMensaje(this.getMensaje().length() <= 2000 ? this.getMensaje() : this.getMensaje().substring(0, 2000));
        return logErrorPilaM1;
    }

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
     * @return the indicePlanillaOI
     */
    public Long getIndicePlanillaOI() {
        return indicePlanillaOI;
    }

    /**
     * @param indicePlanillaOI
     *        the indicePlanillaOI to set
     */
    public void setIndicePlanillaOI(Long indicePlanillaOI) {
        this.indicePlanillaOI = indicePlanillaOI;
    }

    /**
     * @return the indicePlanillaOF
     */
    public Long getIndicePlanillaOF() {
        return indicePlanillaOF;
    }

    /**
     * @param indicePlanillaOF
     *        the indicePlanillaOF to set
     */
    public void setIndicePlanillaOF(Long indicePlanillaOF) {
        this.indicePlanillaOF = indicePlanillaOF;
    }

    /**
     * @return the fechaHoraError
     */
    public Long getFechaHoraError() {
        return fechaHoraError;
    }

    /**
     * @param fechaHoraError
     *        the fechaHoraError to set
     */
    public void setFechaHoraError(Long fechaHoraError) {
        this.fechaHoraError = fechaHoraError;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje
     *        the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
