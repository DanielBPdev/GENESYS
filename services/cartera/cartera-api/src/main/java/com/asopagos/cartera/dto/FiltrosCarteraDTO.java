/**
 * 
 */
package com.asopagos.cartera.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos de los filtros de la consulta.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class FiltrosCarteraDTO implements Serializable{


    /**
     * Tipo de identificación para buscar en cartera.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación
     */
    private String numeroIdentificacion;
    
    /**
     * Estado de cartera.
     */
    private EstadoCarteraEnum estadoCartera;
    
    /**
     * Tipo de deuda.
     */
    private TipoDeudaEnum tipoDeuda;
    
    /**
     * TipoAportante.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;

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
     * Método que retorna el valor de estadoCartera.
     * @return valor de estadoCartera.
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * Método que retorna el valor de tipoDeuda.
     * @return valor de tipoDeuda.
     */
    public TipoDeudaEnum getTipoDeuda() {
        return tipoDeuda;
    }

    /**
     * Método que retorna el valor de tipoAportante.
     * @return valor de tipoAportante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de estadoCartera.
     * @param valor para modificar estadoCartera.
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Método encargado de modificar el valor de tipoDeuda.
     * @param valor para modificar tipoDeuda.
     */
    public void setTipoDeuda(TipoDeudaEnum tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    /**
     * Método encargado de modificar el valor de tipoAportante.
     * @param valor para modificar tipoAportante.
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }
    
    
}
