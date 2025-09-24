package com.asopagos.cartera.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos de los filtros de la consulta de detalle de gestión de cobro.
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 *
 */
public class FiltroDetalleSolicitudGestionCobroDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1390565701787292674L;
    /**
     * Tipo de identificación para buscar en cartera.
     */
    @NotNull
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación
     */
    @NotNull
    private String numeroIdentificacion;
    /**
     * Número de radicación
     */
    @NotNull
    private String numeroRadicacion;
    
    @NotNull
    private String observacionPrimeraRemision;
    
    @NotNull
    private Boolean enviarPrimeraRemision;

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor
     *        para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public String getObservacionPrimeraRemision() {
        return observacionPrimeraRemision;
    }

    public void setObservacionPrimeraRemision(String observacionPrimeraRemision) {
        this.observacionPrimeraRemision = observacionPrimeraRemision;
    }

    public Boolean getEnviarPrimeraRemision() {
        return enviarPrimeraRemision;
    }

    public void setEnviarPrimeraRemision(Boolean enviarPrimeraRemision) {
        this.enviarPrimeraRemision = enviarPrimeraRemision;
    }

}
