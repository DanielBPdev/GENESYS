package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionSubsidioAjuste;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson Andrés Arboleda</a>
 */

public class ParametrizacionSubsidioAjusteModeloDTO implements Serializable {

    private static final long serialVersionUID = 1912001453467563575L;

    /**
     * Identificador de la llave primaria de la tabla
     */
    private Long idParametrizacionSubsidioAjuste;

    /**
     * Periodo de liquidacion asociado
     */
    private Long idPeriodoLiquidacion;

    /**
     * Valor de ajuste de la cuota
     */
    private BigDecimal valorCuotaAjuste;

    /**
     * Valor del ajuste de la cuota agraria
     */
    private BigDecimal valorCuotaAgrariaAjuste;

    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param parametrizacionSubsidioAjuste
     *        entidad a convertir
     */
    public void convertToDTO(ParametrizacionSubsidioAjuste parametrizacionSubsidioAjuste) {
        this.setIdParametrizacionSubsidioAjuste(parametrizacionSubsidioAjuste.getIdParametrizacionSubsidioAjuste());
        this.setIdPeriodoLiquidacion(parametrizacionSubsidioAjuste.getIdPeriodoLiquidacion());
        this.setValorCuotaAjuste(parametrizacionSubsidioAjuste.getValorCuotaAjuste());
        this.setValorCuotaAgrariaAjuste(parametrizacionSubsidioAjuste.getValorCuotaAgrariaAjuste());
    }

    /**
     * Metodo encargado de convertir de DTO a entidad
     * @return la entidad a partir del DTO
     */
    public ParametrizacionSubsidioAjuste convertToEntity() {
        ParametrizacionSubsidioAjuste parametrizacionSubsidioAjuste = new ParametrizacionSubsidioAjuste();

        parametrizacionSubsidioAjuste.setIdParametrizacionSubsidioAjuste(this.getIdParametrizacionSubsidioAjuste());
        parametrizacionSubsidioAjuste.setIdPeriodoLiquidacion(this.getIdPeriodoLiquidacion());
        parametrizacionSubsidioAjuste.setValorCuotaAjuste(this.getValorCuotaAjuste());
        parametrizacionSubsidioAjuste.setValorCuotaAgrariaAjuste(this.getValorCuotaAgrariaAjuste());

        return parametrizacionSubsidioAjuste;
    }

    /**
     * @return the idParametrizacionSubsidioAjuste
     */
    public Long getIdParametrizacionSubsidioAjuste() {
        return idParametrizacionSubsidioAjuste;
    }

    /**
     * @param idParametrizacionSubsidioAjuste
     *        the idParametrizacionSubsidioAjuste to set
     */
    public void setIdParametrizacionSubsidioAjuste(Long idParametrizacionSubsidioAjuste) {
        this.idParametrizacionSubsidioAjuste = idParametrizacionSubsidioAjuste;
    }

    /**
     * @return the idPeriodoLiquidacion
     */
    public Long getIdPeriodoLiquidacion() {
        return idPeriodoLiquidacion;
    }

    /**
     * @param idPeriodoLiquidacion
     *        the idPeriodoLiquidacion to set
     */
    public void setIdPeriodoLiquidacion(Long idPeriodoLiquidacion) {
        this.idPeriodoLiquidacion = idPeriodoLiquidacion;
    }

    /**
     * @return the valorCuotaAjuste
     */
    public BigDecimal getValorCuotaAjuste() {
        return valorCuotaAjuste;
    }

    /**
     * @param valorCuotaAjuste
     *        the valorCuotaAjuste to set
     */
    public void setValorCuotaAjuste(BigDecimal valorCuotaAjuste) {
        this.valorCuotaAjuste = valorCuotaAjuste;
    }

    /**
     * @return the valorCuotaAgrariaAjuste
     */
    public BigDecimal getValorCuotaAgrariaAjuste() {
        return valorCuotaAgrariaAjuste;
    }

    /**
     * @param valorCuotaAgrariaAjuste
     *        the valorCuotaAgrariaAjuste to set
     */
    public void setValorCuotaAgrariaAjuste(BigDecimal valorCuotaAgrariaAjuste) {
        this.valorCuotaAgrariaAjuste = valorCuotaAgrariaAjuste;
    }
}
