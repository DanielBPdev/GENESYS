package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionLiquidacionSubsidio;

/**
 * <b>Descripcion:</b> Clase DTO que representa la parametrizacion de liquidacion de subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU 431<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy Lopez Cardona</a>
 */

public class ParametrizacionLiquidacionSubsidioModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria
     */
    private Long idParametrizacionLiquidacionSubsidio;

    /**
     * Indica el año de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Integer anioVigenciaParametrizacion;

    /**
     * Fecha inicial del perido de vigencia de la parametrización de liquidación de subsidio monerario
     */
    @NotNull
    private Date fechaPeriodoInicial;

    /**
     * Fecha final del perido de vigencia de la parametrización de liquidación de subsidio monerario
     */
    @NotNull
    private Date fechaPeriodoFinal;

    /**
     * Valor de cuota anual base de la parametrización de liquidación de subsidio monerario
     */
    @NotNull
    private BigDecimal factorCuotaInvalidez;

    /**
     * Valor de cuota anual base de la parametrización de liquidación de subsidio monerario
     */
    @NotNull
    private BigDecimal factorPorDefuncion;

    /**
     * Indica el numero de horas trabajadas de la parametrización de liquidación de subsidio monerario
     */
    @NotNull
    private Integer horasTrabajadas;

    /**
     * Valor del salario minimo legan mensual vigente, parametrizado para el correspondiente periodo
     */
    @NotNull
    private BigDecimal smlmv;

    private Date fechaInicioConyugeCuidador;

    /**
     * Método que permite realizar la conversión de un DTO de parametrización a una Entidad
     * @param parametrizacionDTO
     *        DTO con la información de la parametrización
     * @return entidad de parametrización
     */
    public static ParametrizacionLiquidacionSubsidio convertToEntity(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacionDTO) {
        ParametrizacionLiquidacionSubsidio parametrizacion = new ParametrizacionLiquidacionSubsidio();

        parametrizacion.setIdParametrizacionLiquidacionSubsidio(parametrizacionDTO.getIdParametrizacionLiquidacionSubsidio());
        parametrizacion.setAnioVigenciaParametrizacion(parametrizacionDTO.getAnioVigenciaParametrizacion());
        parametrizacion.setFechaPeriodoInicial(parametrizacionDTO.getFechaPeriodoInicial());
        parametrizacion.setFechaPeriodoFinal(parametrizacionDTO.getFechaPeriodoFinal());
        parametrizacion.setFactorCuotaInvalidez(parametrizacionDTO.getFactorCuotaInvalidez());
        parametrizacion.setFactorPorDefuncion(parametrizacionDTO.getFactorPorDefuncion());
        parametrizacion.setHorasTrabajadas(parametrizacionDTO.getHorasTrabajadas());
        parametrizacion.setSmlmv(parametrizacionDTO.getSmlmv());
        parametrizacion.setFechaInicioConyugeCuidador(parametrizacionDTO.getFechaInicioConyugeCuidador());

        

        return parametrizacion;
    }

    /**
     * Método que permite realizar la conversión de una Entidad de parametrización a una DTO
     * @param parametrizacion
     *        entidad de parametrización
     * @return DTO con la información de parametrización
     */
    public static ParametrizacionLiquidacionSubsidioModeloDTO convertToDTO(ParametrizacionLiquidacionSubsidio parametrizacion) {
        ParametrizacionLiquidacionSubsidioModeloDTO parametrizacionDTO = new ParametrizacionLiquidacionSubsidioModeloDTO();

        parametrizacionDTO.setIdParametrizacionLiquidacionSubsidio(parametrizacion.getIdParametrizacionLiquidacionSubsidio());
        parametrizacionDTO.setAnioVigenciaParametrizacion(parametrizacion.getAnioVigenciaParametrizacion());
        parametrizacionDTO.setFechaPeriodoInicial(parametrizacion.getFechaPeriodoInicial());
        parametrizacionDTO.setFechaPeriodoFinal(parametrizacion.getFechaPeriodoFinal());
        parametrizacionDTO.setFactorCuotaInvalidez(parametrizacion.getFactorCuotaInvalidez());
        parametrizacionDTO.setFactorPorDefuncion(parametrizacion.getFactorPorDefuncion());
        parametrizacionDTO.setHorasTrabajadas(parametrizacion.getHorasTrabajadas());
        parametrizacionDTO.setSmlmv(parametrizacion.getSmlmv());
        parametrizacionDTO.setFechaInicioConyugeCuidador(parametrizacion.getFechaInicioConyugeCuidador());

        return parametrizacionDTO;
    }

    /**
     * @return the idParametrizacionLiquidacionSubsidio
     */
    public Long getIdParametrizacionLiquidacionSubsidio() {
        return idParametrizacionLiquidacionSubsidio;
    }

    /**
     * @param idParametrizacionLiquidacionSubsidio
     *        the idParametrizacionLiquidacionSubsidio to set
     */
    public void setIdParametrizacionLiquidacionSubsidio(Long idParametrizacionLiquidacionSubsidio) {
        this.idParametrizacionLiquidacionSubsidio = idParametrizacionLiquidacionSubsidio;
    }

    /**
     * @return the anioVigenciaParametrizacion
     */
    public Integer getAnioVigenciaParametrizacion() {
        return anioVigenciaParametrizacion;
    }

    /**
     * @param anioVigenciaParametrizacion
     *        the anioVigenciaParametrizacion to set
     */
    public void setAnioVigenciaParametrizacion(Integer anioVigenciaParametrizacion) {
        this.anioVigenciaParametrizacion = anioVigenciaParametrizacion;
    }

    /**
     * @return the fechaPeriodoInicial
     */
    public Date getFechaPeriodoInicial() {
        return fechaPeriodoInicial;
    }

    /**
     * @param fechaPeriodoInicial
     *        the fechaPeriodoInicial to set
     */
    public void setFechaPeriodoInicial(Date fechaPeriodoInicial) {
        this.fechaPeriodoInicial = fechaPeriodoInicial;
    }

    /**
     * @return the fechaPeriodoFinal
     */
    public Date getFechaPeriodoFinal() {
        return fechaPeriodoFinal;
    }

    /**
     * @param fechaPeriodoFinal
     *        the fechaPeriodoFinal to set
     */
    public void setFechaPeriodoFinal(Date fechaPeriodoFinal) {
        this.fechaPeriodoFinal = fechaPeriodoFinal;
    }

    /**
     * @return the factorCuotaInvalidez
     */
    public BigDecimal getFactorCuotaInvalidez() {
        return factorCuotaInvalidez;
    }

    /**
     * @param factorCuotaInvalidez
     *        the factorCuotaInvalidez to set
     */
    public void setFactorCuotaInvalidez(BigDecimal factorCuotaInvalidez) {
        this.factorCuotaInvalidez = factorCuotaInvalidez;
    }

    /**
     * @return the factorPorDefuncion
     */
    public BigDecimal getFactorPorDefuncion() {
        return factorPorDefuncion;
    }

    /**
     * @param factorPorDefuncion
     *        the factorPorDefuncion to set
     */
    public void setFactorPorDefuncion(BigDecimal factorPorDefuncion) {
        this.factorPorDefuncion = factorPorDefuncion;
    }

    /**
     * @return the horasTrabajadas
     */
    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    /**
     * @param horasTrabajadas
     *        the horasTrabajadas to set
     */
    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    /**
     * @return the smlmv
     */
    public BigDecimal getSmlmv() {
        return smlmv;
    }

    /**
     * @param smlmv
     *        the smlmv to set
     */
    public void setSmlmv(BigDecimal smlmv) {
        this.smlmv = smlmv;
    }

    public Date getFechaInicioConyugeCuidador() {
        return this.fechaInicioConyugeCuidador;
    }

    public void setFechaInicioConyugeCuidador(Date fechaInicioConyugeCuidador) {
        this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador;
    }

}