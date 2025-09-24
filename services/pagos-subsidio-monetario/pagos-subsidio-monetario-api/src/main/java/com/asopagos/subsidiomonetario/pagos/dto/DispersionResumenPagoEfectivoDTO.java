package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumida del pago en efectivo de la dispersión del monto liquidado <br/>
 * <b>Módulo:</b> Asopagos - HU 311-441 <br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResumenPagoEfectivoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4436350576834474801L;
    
    /**Variable que contien el valor del total a pagar en efectivo*/
    private BigDecimal montoTotal;
    
    /**Numero total de cuotas cuyo pago se debe realizar en efectivo*/
    private Long cantidadCuotas;
    
    /**Total de administradores de subsidio a los que se les debe pagar en efectivo*/
    private Long numeroAdministradoresSubsidio;

    
    
    /**
     * Metodo que permite obtener el valor del monto total.
     * @return el valor del monto total
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Metodo que modifica el valor del monto total
     * @param montoTotal valor del nuevo monto total
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Metodo que permite obtener el valor de la cantidad de cuotas.
     * @return el valor de la cantidad de cuotas
     */
    public Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    /**
     * Metodo que modifica el valor de la cantidad de cuotas
     * @param cantidadCuotas valor de la nueva cantidad de cuotas
     */
    public void setCantidadCuotas(Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    /**
     * Metodo que permite obtener el valor del numero de administradores del subsidio.
     * @return el valor del numero de administradores del subsidio.
     */
    public Long getNumeroAdministradoresSubsidio() {
        return numeroAdministradoresSubsidio;
    }

    /**
     * Metodo que modifica el valor del numero de administradores de subsidio.
     * @param numeroAdministradoresSubsidio valor del nuevo numero de administradores de subsidio.
     */
    public void setNumeroAdministradoresSubsidio(Long numeroAdministradoresSubsidio) {
        this.numeroAdministradoresSubsidio = numeroAdministradoresSubsidio;
    } 
    
    
    

}
