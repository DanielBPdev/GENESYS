package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumida del total de pagos de la dispersion del monto liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResumenTotalDTO  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3668451762059312650L;
    
    /**total de pagos dispersados*/
    private BigDecimal montoTotalDispersado;
    
    /**Numero total de cuotas que corresponden a los pagos dispersados*/
    private Long cantidadCuotas;
    
    /**Total de administradores de subsidios que se les dispersaron pagos*/
    private Long cantidadAdministradoresSubsidio;
    
    /**Monto total por concepto de subsidios retenidos*/
    private Long totalRetenido;

    
    /**
     * Metodo que obtiene el valor del monto total dispersado.
     * @return valor del monto total dispersado.
     */
    public BigDecimal getMontoTotalDispersado() {
        return montoTotalDispersado;
    }

    /**
     * Metodo que permite cambiar el valor del monto total dispersado
     * @param montoTotalDispersado the montoTotalDispersado to set
     */
    public void setMontoTotalDispersado(BigDecimal montoTotalDispersado) {
        this.montoTotalDispersado = montoTotalDispersado;
    }

    /**
     * Metodo que obtiene el valor de la cantidad de cuotas.
     * @return valor de la cantidad de cuotas.
     */
    public Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    /**
     * Metodo que permite cambiar el valor de la cantidad de cuotas
     * @param cantidadCuotas the cantidadCuotas to set
     */
    public void setCantidadCuotas(Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    /**
     * Metodo que obtiene el valor de la cantidad de administradores de subsidio.
     * @return valor de la cantidad de administradores de subsidio.
     */
    public Long getCantidadAdministradoresSubsidio() {
        return cantidadAdministradoresSubsidio;
    }

    /**
     * Metodo que permite cambiar el valor de la cantidad de administradores de subsidio
     * @param cantidadAdministradoresSubsidio the cantidadAdministradoresSubsidio to set
     */
    public void setCantidadAdministradoresSubsidio(Long cantidadAdministradoresSubsidio) {
        this.cantidadAdministradoresSubsidio = cantidadAdministradoresSubsidio;
    }

    /**
     * Metodo que obtiene el valor del total retenido.
     * @return valor del total retenido.
     */
    public Long getTotalRetenido() {
        return totalRetenido;
    }

    /**
     * Metodo que permite cambiar el valor del total retenido.
     * @param totalRetenido the totalRetenido to set
     */
    public void setTotalRetenido(Long totalRetenido) {
        this.totalRetenido = totalRetenido;
    }    
    

}
