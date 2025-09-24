/**
 * 
 */
package com.asopagos.cartera.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * Clase DTO con los datos para realizar una actualización de datos de la HU167.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ActualizacionDatosDTO implements Serializable{

    /**
     * Id de persona.
     */
    private Long idPersona;
    
    /**
     * TipoAportante.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;
    
    /**
     * fecha de radicación 
     */
    private Long fechaRadicacion;

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método que retorna el valor de tipoAportante.
     * @return valor de tipoAportante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoAportante.
     * @param valor para modificar tipoAportante.
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }

    /**
     * Método que retorna el valor de fechaRadicacion.
     * @return valor de fechaRadicacion.
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * Método encargado de modificar el valor de fechaRadicacion.
     * @param valor
     *        para modificar fechaRadicacion.
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }
    
}
