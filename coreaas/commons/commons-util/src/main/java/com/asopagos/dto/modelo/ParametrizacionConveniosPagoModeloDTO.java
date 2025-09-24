package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.ParametrizacionConveniosPago;

/**
 * Representa los valores de parametrización para el proceso 2.2.4 Convenios de
 * pago
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ParametrizacionConveniosPagoModeloDTO implements Serializable{

    /**
     * Identificador de la entidad Parametrización Convenios de Pago
     */
    private Long idParametrizacionConveniosPago;
    /**
     * Representa el número de convenios de pago permitidos por aportante, que puede
     * tomar un valor de 0 a 6
     */
    private Short numeroConveniosPermitido;
    /**
     * Representa la cantidad de periodos minimos y maximos. (0-36)
     */
    private Short cantidadPeriodos;

    /**
     * Método constructor
     */
    public ParametrizacionConveniosPagoModeloDTO(){

    }

    /**
     * @return the idParametrizacionConveniosPago
     */
    public Long getIdParametrizacionConveniosPago() {
        return idParametrizacionConveniosPago;
    }

    /**
     * @param idParametrizacionConveniosPago the idParametrizacionConveniosPago to set
     */
    public void setIdParametrizacionConveniosPago(Long idParametrizacionConveniosPago) {
        this.idParametrizacionConveniosPago = idParametrizacionConveniosPago;
    }

    /**
     * @return the numeroConveniosPermitido
     */
    public Short getNumeroConveniosPermitido() {
        return numeroConveniosPermitido;
    }

    /**
     * @param numeroConveniosPermitido the numeroConveniosPermitido to set
     */
    public void setNumeroConveniosPermitido(Short numeroConveniosPermitido) {
        this.numeroConveniosPermitido = numeroConveniosPermitido;
    }

    /**
     * @return the cantidadPeriodos
     */
    public Short getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    /**
     * @param cantidadPeriodos the cantidadPeriodos to set
     */
    public void setCantidadPeriodos(Short cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }

    /**
     * Método encargado de convertir una entidad a DTO
     * 
     * @param parametrizacionConvenioPago parametrización del convenio de pago 
     */
    public void convertToDTO(ParametrizacionConveniosPago parametrizacionConvenioPago) {
        this.setIdParametrizacionConveniosPago(parametrizacionConvenioPago.getIdParametrizacionConveniosPago());
        this.setCantidadPeriodos(parametrizacionConvenioPago.getCantidadPeriodos());
        this.setNumeroConveniosPermitido(parametrizacionConvenioPago.getNumeroConveniosPermitido());
    }
    
    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public ParametrizacionConveniosPago convertToEntity() {
        ParametrizacionConveniosPago parametrizacionConveniosPago = new ParametrizacionConveniosPago();
        parametrizacionConveniosPago.setIdParametrizacionConveniosPago(this.getIdParametrizacionConveniosPago());
        parametrizacionConveniosPago.setCantidadPeriodos(this.getCantidadPeriodos());
        parametrizacionConveniosPago.setNumeroConveniosPermitido(this.getNumeroConveniosPermitido());
        return parametrizacionConveniosPago;

    }
}
