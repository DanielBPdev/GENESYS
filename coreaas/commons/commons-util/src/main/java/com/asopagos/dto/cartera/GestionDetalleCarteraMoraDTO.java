package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;

/**
 * DTO que contiene el detalle de mora por acción
 * @author Silvio Alexander López Herrera <silopez@heinsohn.com.co>
 * @created 21-feb-2017 3:31:37 p.m.
 */
public class GestionDetalleCarteraMoraDTO implements Serializable {

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = 5921690068883832144L;
    
    /**
     * Acción de cobro
     */
    private TipoAccionCobroEnum accion;
    
    /**
     * Cantidad de entidades asociadas a la acción
     */
    private Integer cantidad;
    
    
    /**
     * Deuda total para la acción
     */
    private BigDecimal deuda;

    /**
     * Método que retorna el valor de accion.
     * @return valor de accion.
     */
    public TipoAccionCobroEnum getAccion() {
        return accion;
    }

    /**
     * Método encargado de modificar el valor de accion.
     * @param valor para modificar accion.
     */
    public void setAccion(TipoAccionCobroEnum accion) {
        this.accion = accion;
    }

    /**
     * Método que retorna el valor de cantidad.
     * @return valor de cantidad.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Método encargado de modificar el valor de cantidad.
     * @param valor para modificar cantidad.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Método que retorna el valor de deuda.
     * @return valor de deuda.
     */
    public BigDecimal getDeuda() {
        return deuda;
    }

    /**
     * Método encargado de modificar el valor de deuda.
     * @param valor para modificar deuda.
     */
    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }
       

}
