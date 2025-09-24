package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class EstadoCategoriaPersonaInDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoID;

    /**
     * Número de identificación de la persona
     */
    private String identificacion;

    /**
     * Por defecto: Inicio del día actual
     */
    private Date fechaInicio;

    /**
     * Por defecto: Final del día actual
     */
    private Date fechaFinal;
    
    /**
     * 
     */
    public EstadoCategoriaPersonaInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param fechaInicio
     * @param fechaFinal
     */
    public EstadoCategoriaPersonaInDTO(TipoIdentificacionEnum tipoID, String identificacion, Date fechaInicio, Date fechaFinal) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
