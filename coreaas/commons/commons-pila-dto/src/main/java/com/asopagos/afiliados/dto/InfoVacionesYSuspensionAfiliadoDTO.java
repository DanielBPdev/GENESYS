package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.Date;

public class InfoVacionesYSuspensionAfiliadoDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Boolean vacaciones;
    private Date fechaInicioVacaciones;
    private Date fechaFinVacaciones;
    private Boolean suspensionTempTrabajo;
    private Date fechaInicioSuspencion;
    private Date fechaFinSuspencion;
    
    /**
     * 
     */
    public InfoVacionesYSuspensionAfiliadoDTO() {
    }

    /**
     * @param vacaciones
     * @param fechaInicioVacaciones
     * @param fechaFinVacaciones
     * @param suspensionTempTrabajo
     * @param fechaInicioSuspencion
     * @param fechaFinSuspencion
     */
    public InfoVacionesYSuspensionAfiliadoDTO(Boolean vacaciones, Date fechaInicioVacaciones, Date fechaFinVacaciones,
            Boolean suspensionTempTrabajo, Date fechaInicioSuspencion, Date fechaFinSuspencion) {
        this.vacaciones = vacaciones;
        this.fechaInicioVacaciones = fechaInicioVacaciones;
        this.fechaFinVacaciones = fechaFinVacaciones;
        this.suspensionTempTrabajo = suspensionTempTrabajo;
        this.fechaInicioSuspencion = fechaInicioSuspencion;
        this.fechaFinSuspencion = fechaFinSuspencion;
    }

    /**
     * @return the vacaciones
     */
    public Boolean getVacaciones() {
        return vacaciones;
    }

    /**
     * @param vacaciones the vacaciones to set
     */
    public void setVacaciones(Boolean vacaciones) {
        this.vacaciones = vacaciones;
    }

    /**
     * @return the fechaInicioVacaciones
     */
    public Date getFechaInicioVacaciones() {
        return fechaInicioVacaciones;
    }

    /**
     * @param fechaInicioVacaciones the fechaInicioVacaciones to set
     */
    public void setFechaInicioVacaciones(Date fechaInicioVacaciones) {
        this.fechaInicioVacaciones = fechaInicioVacaciones;
    }

    /**
     * @return the fechaFinVacaciones
     */
    public Date getFechaFinVacaciones() {
        return fechaFinVacaciones;
    }

    /**
     * @param fechaFinVacaciones the fechaFinVacaciones to set
     */
    public void setFechaFinVacaciones(Date fechaFinVacaciones) {
        this.fechaFinVacaciones = fechaFinVacaciones;
    }

    /**
     * @return the suspensionTempTrabajo
     */
    public Boolean getSuspensionTempTrabajo() {
        return suspensionTempTrabajo;
    }

    /**
     * @param suspensionTempTrabajo the suspensionTempTrabajo to set
     */
    public void setSuspensionTempTrabajo(Boolean suspensionTempTrabajo) {
        this.suspensionTempTrabajo = suspensionTempTrabajo;
    }

    /**
     * @return the fechaInicioSuspencion
     */
    public Date getFechaInicioSuspencion() {
        return fechaInicioSuspencion;
    }

    /**
     * @param fechaInicioSuspencion the fechaInicioSuspencion to set
     */
    public void setFechaInicioSuspencion(Date fechaInicioSuspencion) {
        this.fechaInicioSuspencion = fechaInicioSuspencion;
    }

    /**
     * @return the fechaFinSuspencion
     */
    public Date getFechaFinSuspencion() {
        return fechaFinSuspencion;
    }

    /**
     * @param fechaFinSuspencion the fechaFinSuspencion to set
     */
    public void setFechaFinSuspencion(Date fechaFinSuspencion) {
        this.fechaFinSuspencion = fechaFinSuspencion;
    }
}
