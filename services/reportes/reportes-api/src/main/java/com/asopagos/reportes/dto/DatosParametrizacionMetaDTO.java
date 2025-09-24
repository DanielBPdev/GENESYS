package com.asopagos.reportes.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;

/**
 * <b>Descripción</b> DTO que representa los valores parametrizados de una determinada configuración de metas.
 * <b></b>
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
@XmlRootElement
public class DatosParametrizacionMetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Canal de la parametrización
     */
    private CanalRecepcionEnum canal;
    /**
     * Valor del periodo de la paramtrización
     */
    private String periodo;
    /**
     * Valor del código que representa el periodo de la paramtrización
     */
    private Byte codigoPeriodo;
    /**
     * Valor parametrizado
     */
    private Integer valor;

    public DatosParametrizacionMetaDTO() {
    }

    public DatosParametrizacionMetaDTO(CanalRecepcionEnum canal, String periodo, Byte codigoPeriodo, Integer valor) {
        this.setCanal(canal);
        this.setPeriodo(periodo);
        this.setCodigoPeriodo(codigoPeriodo);
        this.setValor(valor);
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal
     *        the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the codigoPeriodo
     */
    public Byte getCodigoPeriodo() {
        return codigoPeriodo;
    }

    /**
     * @param codigoPeriodo
     *        the codigoPeriodo to set
     */
    public void setCodigoPeriodo(Byte codigoPeriodo) {
        this.codigoPeriodo = codigoPeriodo;
    }

    /**
     * @return the valor
     */
    public Integer getValor() {
        return valor;
    }

    /**
     * @param valor
     *        the valor to set
     */
    public void setValor(Integer valor) {
        this.valor = valor;
    }

}
