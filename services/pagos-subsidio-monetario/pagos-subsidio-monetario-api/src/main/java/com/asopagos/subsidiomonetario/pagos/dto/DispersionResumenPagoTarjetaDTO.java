package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumida del pago de tarjeta de la dispersion del monto liquidado.<br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResumenPagoTarjetaDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2191257276318130407L;
    
    /**Variable que contiene el total de abonos realizados a tarjetas*/
    private Long numeroRegistros;
    
    /**Variable que contiene el monto total abonado a tarjetas*/
    private BigDecimal montoTotal;
    
    /**Variable que contiene el numero de cuotas cuyo pago fue abonado a tarjtas*/
    private Long cantidadCuotas;
    
    /**Variable que contiene el numero total de administradores de subsidio 
     * a los que se le abono el pago a la tarjeta*/
    private Long numeroAdministradoresSubsidio;
    
    /**Variable que contiene el numero de inconsistencias
     * reportadas al abonar el pago a la tarjeta*/
    private Long numeroInconsistencias;
    
    

    /**
     * Metodo que permite obtener el valor del numero de registros
     * @return valor del numero de registros
     */
    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * Metodo que modifica el valor del numero de registros.
     * @param numeroRegistros  variable que contiene el nuevo valor del numero de registros.
     */
    public void setNumeroRegistros(Long numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     * Metodo que permite obtener el valor del monto total.
     * @return valor del monto total
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Metodo que modifica el valor del monto total.
     * @param montoTotal variable que contiene el nuevo valor del monto total.
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Metodo que permite obtener el valor de la cantidad de cuotas.
     * @return valor de la cantidad de cuotas.
     */
    public Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    /**
     * Metodo que modifica el valor de la cantidad de cuotas.
     * @param cantidadCuotas variable que contiene el nuevo valor de la cantidad de cuotas.
     */
    public void setCantidadCuotas(Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    /**
     * Metodo que permite obtener el valor del numero de administradores de subsidio.
     * @return valor del numero de administradores de subsidio
     */
    public Long getNumeroAdministradoresSubsidio() {
        return numeroAdministradoresSubsidio;
    }

    /**
     * Metodo que modifica el valor del numero de administradores de subsidio.
     * @param numeroAdministradoresSubsidio variable que contiene el nuevo valor de administradores de subsidio.
     */
    public void setNumeroAdministradoresSubsidio(Long numeroAdministradoresSubsidio) {
        this.numeroAdministradoresSubsidio = numeroAdministradoresSubsidio;
    }

    /**
     * Metodo que permite obtener el valor del numero de incosistencias.
     * @return valor que contiene el numero de incosistencias.
     */
    public Long getNumeroInconsistencias() {
        return numeroInconsistencias;
    }

    /**
     * Metodo que modifica el valor del numero inconsistencias.
     * @param numeroInconsistencias variable que contiene el nuevo valor del numero de inconsistencias
     */
    public void setNumeroInconsistencias(Long numeroInconsistencias) {
        this.numeroInconsistencias = numeroInconsistencias;
    }
    
    
    
    

}
