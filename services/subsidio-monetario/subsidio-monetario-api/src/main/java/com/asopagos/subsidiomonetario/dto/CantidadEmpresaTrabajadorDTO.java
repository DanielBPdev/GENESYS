package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ItemsCantidadEmpresaTrabajadorEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la consulta de la segunda sección del la liquidación masiva<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-436 y 438<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class CantidadEmpresaTrabajadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre del item asociado a la cantidad de empresas o trabajadores de la liquidación */
    private ItemsCantidadEmpresaTrabajadorEnum nombre;

    /** Umbral correspondiente al item de cantidad empresa/trabajador */
    private BigDecimal umbral;

    /** Valor correspondiente al item de cantidad empresa/trabajador */
    private BigDecimal valor;

    /**
     * Método que permite obtener el nombre del item
     * @return nombre del item
     */
    public ItemsCantidadEmpresaTrabajadorEnum getNombre() {
        return nombre;
    }

    /**
     * Método que permite establecer el nombre del item
     * @param nombre
     *        del item
     */
    public void setNombre(ItemsCantidadEmpresaTrabajadorEnum nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el umbral para el item
     * @return umbral del item
     */
    public BigDecimal getUmbral() {
        return umbral;
    }

    /**
     * Método que permite establecer el umbral para el item
     * @param umbral
     *        valor del umbral
     */
    public void setUmbral(BigDecimal umbral) {
        this.umbral = umbral;
    }

    /**
     * Método que permite obtener el valor del item
     * @return valor del item
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Método que permite establecer el valor del item
     * @param valor
     *        del item
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
