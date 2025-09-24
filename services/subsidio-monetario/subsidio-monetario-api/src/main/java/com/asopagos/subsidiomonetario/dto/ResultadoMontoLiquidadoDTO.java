package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ItemsMontoLiquidadoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la consulta de la primera sección de la liquidación masiva<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-436 y 438<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoMontoLiquidadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre del item de los resultados asociados al monto de liquidación */
    private ItemsMontoLiquidadoEnum nombre;

    /** Valor general correspondiente al item del monto de liquidacion */
    private BigDecimal valor;

    /** Valor promedio correspondiente al item del monto de liquidacion */
    private BigDecimal valorPromedio;

    /** Valor anterior del periodo acumulado correspondiente al item del monto de liquidacion */
    private BigDecimal valorAnteriores;

    /** Valor acumulado correspondiente al item del monto de liquidacion */
    private BigDecimal valorAcumulado;

    /**
     * Método que permite obtener el nombre correspondiente al item de la consulta de liquidación
     * @return nombre del item
     */
    public ItemsMontoLiquidadoEnum getNombre() {
        return nombre;
    }

    /**
     * Método que permite establecer el nombre del item de la sección de resultado del monto
     * @param nombre
     *        valor para el item
     */
    public void setNombre(ItemsMontoLiquidadoEnum nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el valor general
     * @return valor del item general
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Método que permite definir el valor general
     * @param valor
     *        del item general
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Método que permite obtener el valor promedio en los ultimos 12 meses
     * @return valor del promedio
     */
    public BigDecimal getValorPromedio() {
        return valorPromedio;
    }

    /**
     * Método que permite definir el valor promedio
     * @param valorPromedio
     *        valor del promedio
     */
    public void setValorPromedio(BigDecimal valorPromedio) {
        this.valorPromedio = valorPromedio;
    }

    /**
     * Método que permite obtener el valor para el mismo periodo en años anteriores
     * @return valor en años anteriores
     */
    public BigDecimal getValorAnteriores() {
        return valorAnteriores;
    }

    /**
     * Método que permite definir el valor para el mismo periodo en años anteriores
     * @param valorAnteriores
     *        valor en el mismo periodo para años anteriores
     */
    public void setValorAnteriores(BigDecimal valorAnteriores) {
        this.valorAnteriores = valorAnteriores;
    }

    /**
     * Método que permite obtener el valor acumulado del periodo
     * @return valor del periodo acumulado
     */
    public BigDecimal getValorAcumulado() {
        return valorAcumulado;
    }

    /**
     * Método que permite definir el valor acumulado del periodo
     * @param valorAcumulado
     *        valor acumulado del periodo liquidado
     */
    public void setValorAcumulado(BigDecimal valorAcumulado) {
        this.valorAcumulado = valorAcumulado;
    }

}
