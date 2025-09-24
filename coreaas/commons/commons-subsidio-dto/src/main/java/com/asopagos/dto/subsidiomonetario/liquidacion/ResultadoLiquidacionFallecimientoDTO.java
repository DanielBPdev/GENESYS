package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la consulta para una liquidación específica de fallecimiento<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-317-510<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoLiquidacionFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo que indica el estado de finalización del proceso de liquidación de fallecimiento (no se tiene beneficiario o afiliado con
     * derecho)
     */
    private Boolean resultadoProceso;

    /**
     * Lista con los resultados de la evaluación para el proceso de liquidación
     */
    private ResumenResultadosEvaluacionDTO resumenEvaluacion;

    /**
     * Lista de items para los resultados de la evaluación
     */
    private List<ItemResultadoLiquidacionFallecimientoDTO> itemsResultadoEvaluacion;

    /**
     * Lista de DTO´s con el resultado de la liquidación por administrador de subsidio
     */
    private List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador;

    /**
     * Periodo de fallecimiento del afiliado o beneficiario
     */
    private Date periodoFallecimiento;

    /**
     * Año de fallecimiento del beneficiario
     */
    private Integer anioFallecimiento;

    /**
     * Indicador de aportes del afiliado en el periodo
     */
    private Boolean existenciaAportesPeriodo;

    /**
     * Indicador del estado del aporte
     */
    private EstadoAporteSubsidioEnum estadoAporte;

    /**
     * Indicador de consideración por parte de la caja para realizar el desembolso
     */
    private Boolean consideracionAportes;

    /**
     * Indicador de la forma en que se realiza el desembolso (una sola cuota o mes por mes)
     */
    private ModoDesembolsoEnum tipoDesembolso;
    
    /**
     * Indicador si la causal es por APORTE_MINIMO
     */
    private Boolean isAporteMinimo;

    /**
     * @return the resultadoProceso
     */
    public Boolean getResultadoProceso() {
        return resultadoProceso;
    }

    /**
     * @param resultadoProceso
     *        the resultadoProceso to set
     */
    public void setResultadoProceso(Boolean resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

    /**
     * @return the resumenEvaluacion
     */
    public ResumenResultadosEvaluacionDTO getResumenEvaluacion() {
        return resumenEvaluacion;
    }

    /**
     * @param resumenEvaluacion
     *        the resumenEvaluacion to set
     */
    public void setResumenEvaluacion(ResumenResultadosEvaluacionDTO resumenEvaluacion) {
        this.resumenEvaluacion = resumenEvaluacion;
    }

    /**
     * @return the itemsResultadoEvaluacion
     */
    public List<ItemResultadoLiquidacionFallecimientoDTO> getItemsResultadoEvaluacion() {
        return itemsResultadoEvaluacion;
    }

    /**
     * @param itemsResultadoEvaluacion
     *        the itemsResultadoEvaluacion to set
     */
    public void setItemsResultadoEvaluacion(List<ItemResultadoLiquidacionFallecimientoDTO> itemsResultadoEvaluacion) {
        this.itemsResultadoEvaluacion = itemsResultadoEvaluacion;
    }

    /**
     * @return the resultadosPorAdministrador
     */
    public List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> getResultadosPorAdministrador() {
        return resultadosPorAdministrador;
    }

    /**
     * @param resultadosPorAdministrador
     *        the resultadosPorAdministrador to set
     */
    public void setResultadosPorAdministrador(List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador) {
        this.resultadosPorAdministrador = resultadosPorAdministrador;
    }

    /**
     * @return the periodoFallecimiento
     */
    public Date getPeriodoFallecimiento() {
        return periodoFallecimiento;
    }

    /**
     * @param periodoFallecimiento
     *        the periodoFallecimiento to set
     */
    public void setPeriodoFallecimiento(Date periodoFallecimiento) {
        this.periodoFallecimiento = periodoFallecimiento;
    }

    /**
     * @return the anioFallecimiento
     */
    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    /**
     * @param anioFallecimiento
     *        the anioFallecimiento to set
     */
    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    /**
     * @return the existenciaAportesPeriodo
     */
    public Boolean getExistenciaAportesPeriodo() {
        return existenciaAportesPeriodo;
    }

    /**
     * @param existenciaAportesPeriodo
     *        the existenciaAportesPeriodo to set
     */
    public void setExistenciaAportesPeriodo(Boolean existenciaAportesPeriodo) {
        this.existenciaAportesPeriodo = existenciaAportesPeriodo;
    }

    /**
     * @return the estadoAporte
     */
    public EstadoAporteSubsidioEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * @param estadoAporte
     *        the estadoAporte to set
     */
    public void setEstadoAporte(EstadoAporteSubsidioEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * @return the consideracionAportes
     */
    public Boolean getConsideracionAportes() {
        return consideracionAportes;
    }

    /**
     * @param consideracionAportes
     *        the consideracionAportes to set
     */
    public void setConsideracionAportes(Boolean consideracionAportes) {
        this.consideracionAportes = consideracionAportes;
    }

    /**
     * @return the tipoDesembolso
     */
    public ModoDesembolsoEnum getTipoDesembolso() {
        return tipoDesembolso;
    }

    /**
     * @param tipoDesembolso
     *        the tipoDesembolso to set
     */
    public void setTipoDesembolso(ModoDesembolsoEnum tipoDesembolso) {
        this.tipoDesembolso = tipoDesembolso;
    }

	public Boolean getIsAporteMinimo() {
		return isAporteMinimo;
	}

	public void setIsAporteMinimo(Boolean isAporteMinimo) {
		this.isAporteMinimo = isAporteMinimo;
	}

    @Override
    public String toString() {
        return "{" +
            " resultadoProceso='" + getResultadoProceso() + "'" +
            ", resumenEvaluacion='" + getResumenEvaluacion() + "'" +
            ", itemsResultadoEvaluacion='" + getItemsResultadoEvaluacion() + "'" +
            ", resultadosPorAdministrador='" + getResultadosPorAdministrador() + "'" +
            ", periodoFallecimiento='" + getPeriodoFallecimiento() + "'" +
            ", anioFallecimiento='" + getAnioFallecimiento() + "'" +
            ", existenciaAportesPeriodo='" + getExistenciaAportesPeriodo() + "'" +
            ", estadoAporte='" + getEstadoAporte() + "'" +
            ", consideracionAportes='" + getConsideracionAportes() + "'" +
            ", tipoDesembolso='" + getTipoDesembolso() + "'" +
            ", isAporteMinimo='" + getIsAporteMinimo() + "'" +
            "}";
    }

}
