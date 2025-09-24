/**
 * 
 */
package com.asopagos.novedades.dto;

import java.util.List;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos asociados a las Novedades de una Persona o Empleador
 * para la Vista 360.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class DatosNovedadVista360DTO {

    /**
     * Tipo de Identificación 
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de Identificación
     */
    private String numeroIdentificacion;

    /**
     * Tiene Novedad de Licencia Maternidad Vigente.
     */
    private Boolean licenciaMaternidad;
    
    /**
     * Fecha Inicio de Licencia de Maternidad
     */
    private Long fechaInicioLicencia;
    
    /**
     * Fecha Fin de Licencia de Maternidad
     */
    private Long fechaFinLicencia;
    
    /**
     * La persona tiene Novedad de Incapacidad 
     */
    private Boolean incapacidad;
    
    /**
     * Tipo de Incapacidad: Enfermedad General/Laboral
     */
    private TipoTransaccionEnum tipoIncapacidad;
    
    /**
     * Fecha de Inicio de la Incapacidad
     */
    private Long fechaInicioIncapacidad;
    
    /**
     * Fecha Fin de la Incapacidad
     */
    private Long fechaFinIncapacidad;
    
    /**
     * Si es Beneficiario y tiene condición de Invalidez
     */
    private Boolean condicionInvalidez;
    
    /**
     * Fecha Inicio de la Condición de Invalidez
     */
    private Long fechaInicioInvalidez;
    
    /**
     * Datos Novedades Registradas Persona
     */
    private List<DatosNovedadRegistradaPersonaDTO> novedadesRegistradas;

    /**
     * Datos Novedades Registradas Beneficiario
     */
    private List<DatosNovedadRegistradaPersonaDTO> novedadesBeneficiarios;
    
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the licenciaMaternidad
     */
    public Boolean getLicenciaMaternidad() {
        return licenciaMaternidad;
    }

    /**
     * @param licenciaMaternidad the licenciaMaternidad to set
     */
    public void setLicenciaMaternidad(Boolean licenciaMaternidad) {
        this.licenciaMaternidad = licenciaMaternidad;
    }

    /**
     * @return the fechaInicioLicencia
     */
    public Long getFechaInicioLicencia() {
        return fechaInicioLicencia;
    }

    /**
     * @param fechaInicioLicencia the fechaInicioLicencia to set
     */
    public void setFechaInicioLicencia(Long fechaInicioLicencia) {
        this.fechaInicioLicencia = fechaInicioLicencia;
    }

    /**
     * @return the fechaFinLicencia
     */
    public Long getFechaFinLicencia() {
        return fechaFinLicencia;
    }

    /**
     * @param fechaFinLicencia the fechaFinLicencia to set
     */
    public void setFechaFinLicencia(Long fechaFinLicencia) {
        this.fechaFinLicencia = fechaFinLicencia;
    }

    /**
     * @return the incapacidad
     */
    public Boolean getIncapacidad() {
        return incapacidad;
    }

    /**
     * @param incapacidad the incapacidad to set
     */
    public void setIncapacidad(Boolean incapacidad) {
        this.incapacidad = incapacidad;
    }

    /**
     * @return the tipoIncapacidad
     */
    public TipoTransaccionEnum getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    /**
     * @param tipoIncapacidad the tipoIncapacidad to set
     */
    public void setTipoIncapacidad(TipoTransaccionEnum tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    /**
     * @return the fechaInicioIncapacidad
     */
    public Long getFechaInicioIncapacidad() {
        return fechaInicioIncapacidad;
    }

    /**
     * @param fechaInicioIncapacidad the fechaInicioIncapacidad to set
     */
    public void setFechaInicioIncapacidad(Long fechaInicioIncapacidad) {
        this.fechaInicioIncapacidad = fechaInicioIncapacidad;
    }

    /**
     * @return the fechaFinIncapacidad
     */
    public Long getFechaFinIncapacidad() {
        return fechaFinIncapacidad;
    }

    /**
     * @param fechaFinIncapacidad the fechaFinIncapacidad to set
     */
    public void setFechaFinIncapacidad(Long fechaFinIncapacidad) {
        this.fechaFinIncapacidad = fechaFinIncapacidad;
    }

    /**
     * @return the condicionInvalidez
     */
    public Boolean getCondicionInvalidez() {
        return condicionInvalidez;
    }

    /**
     * @param condicionInvalidez the condicionInvalidez to set
     */
    public void setCondicionInvalidez(Boolean condicionInvalidez) {
        this.condicionInvalidez = condicionInvalidez;
    }

    /**
     * @return the fechaInicioInvalidez
     */
    public Long getFechaInicioInvalidez() {
        return fechaInicioInvalidez;
    }

    /**
     * @param fechaInicioInvalidez the fechaInicioInvalidez to set
     */
    public void setFechaInicioInvalidez(Long fechaInicioInvalidez) {
        this.fechaInicioInvalidez = fechaInicioInvalidez;
    }

    /**
     * @return the novedadesRegistradas
     */
    public List<DatosNovedadRegistradaPersonaDTO> getNovedadesRegistradas() {
        return novedadesRegistradas;
    }

    /**
     * @param novedadesRegistradas the novedadesRegistradas to set
     */
    public void setNovedadesRegistradas(List<DatosNovedadRegistradaPersonaDTO> novedadesRegistradas) {
        this.novedadesRegistradas = novedadesRegistradas;
    }
    
    /**
     * @return the novedadesBeneficiarios
     */
    public List<DatosNovedadRegistradaPersonaDTO> getNovedadesBeneficiarios() {
        return novedadesBeneficiarios;
    }

    /**
     * @param novedadesBeneficiarios the novedadesBeneficiarios to set
     */
    public void setNovedadesBeneficiarios(List<DatosNovedadRegistradaPersonaDTO> novedadesBeneficiarios) {
        this.novedadesBeneficiarios = novedadesBeneficiarios;
    }

}
