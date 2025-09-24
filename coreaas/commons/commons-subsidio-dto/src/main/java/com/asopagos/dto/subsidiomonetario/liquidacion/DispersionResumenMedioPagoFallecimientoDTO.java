package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resumen de los pagos para un determinado medio en un subsidio de fallecimiento
 * <br/>
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DispersionResumenMedioPagoFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Total de abonos al medio de pago tarjeta, en la liquidación de fallecimiento
     */
    private Long numeroRegistros;

    /**
     * Número de abonos a realizar al medio de pago tarjeta
     */
    private Long cantidadCuotas;

    /**
     * Monto a liquidar para el medio de pago tarjeta
     */
    private BigDecimal montoTotalLiquidar;

    /**
     * Cantidad de administradores de subsidio a los que se debe abonar el pago
     */
    private Long cantidadAdministradorSubsidios;

    /**
     * Resultados de dispersión por administrador de subsidio para el medio de pago tarjeta
     */
    private List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdministrador;

    /**
     * Lista de identificadores de condiciones
     */
    private List<Long> identificadoresCondiciones;

    /**
     * @return the numeroRegistros
     */
    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * @param numeroRegistros
     *        the numeroRegistros to set
     */
    public void setNumeroRegistros(Long numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     * @return the cantidadCuotas
     */
    public Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    /**
     * @param cantidadCuotas
     *        the cantidadCuotas to set
     */
    public void setCantidadCuotas(Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    /**
     * @return the montoTotalLiquidar
     */
    public BigDecimal getMontoTotalLiquidar() {
        return montoTotalLiquidar;
    }

    /**
     * @param montoTotalLiquidar
     *        the montoTotalLiquidar to set
     */
    public void setMontoTotalLiquidar(BigDecimal montoTotalLiquidar) {
        this.montoTotalLiquidar = montoTotalLiquidar;
    }

    /**
     * @return the cantidadAdministradorSubsidios
     */
    public Long getCantidadAdministradorSubsidios() {
        return cantidadAdministradorSubsidios;
    }

    /**
     * @param cantidadAdministradorSubsidios
     *        the cantidadAdministradorSubsidios to set
     */
    public void setCantidadAdministradorSubsidios(Long cantidadAdministradorSubsidios) {
        this.cantidadAdministradorSubsidios = cantidadAdministradorSubsidios;
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
     * @return the identificadoresCondiciones
     */
    public List<Long> getIdentificadoresCondiciones() {
        return identificadoresCondiciones;
    }

    /**
     * @param identificadoresCondiciones
     *        the identificadoresCondiciones to set
     */
    public void setIdentificadoresCondiciones(List<Long> identificadoresCondiciones) {
        this.identificadoresCondiciones = identificadoresCondiciones;
    }

}
