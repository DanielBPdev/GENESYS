package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionCondicionesSubsidio;

/**
 * <b>Descripcion:</b> Clase DTO que representa la parametrizacion de condiciones de subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona.</a>
 */

public class ParametrizacionCondicionesSubsidioModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria de la parametrización de las condiciones de subsidio monerario
     */
    private Long idParametrizacionCondicionesSubsidio;

    /**
     * Indica el año de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Integer anioVigenciaParametrizacion;

    /**
     * Fecha inicial del perido de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Date fechaPeriodoInicial;

    /**
     * Fecha final del perido de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Date fechaPeriodoFinal;

    /**
     * Valor de cuota anual base de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private BigDecimal valorCuotaAnualBase;

    /**
     * Valor de cuota anual agraria de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private BigDecimal valorCuotaAnualAgraria;

    /**
     * Marca de Referencia que indica si la caja es acogida al programa decreto 4904
     * en el periodo de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Boolean esProgramaDecreto4904;

    /**
     * Marca de Referencia que indica si la caja aplica retroactivo novededad de invalidez o discapacidad
     * en el periodo de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Boolean esRetroactivoNovInvalidez;

    /**
     * Marca de Referencia que indica si la caja aplica Pago de Subsidio Retroactivo por reingresos de empleadores
     * en el periodo de vigencia de la parametrización de las condiciones de subsidio monerario
     */
    @NotNull
    private Boolean esRetroactivoReingresoEmpleadores;

    /**
     * Valor de la cantidad de subsidios liquidados a validar en las consultas que referencian históricos
     * sobre el proceso de liquidación
     */
    @NotNull
    private Integer cantidadSubsidiosLiquidados;

    /**
     * Valor del monto de subsidios liquidados a validar en las consultas que referencian históricos
     * sobre el proceso de liquidación
     */
    @NotNull
    private BigDecimal montoSubsidiosLiquidados;

    /**
     * Valor de la cantidad de subsidios liquidados con invalidez a validar en las consultas que referencian históricos
     * sobre el proceso de liquidación
     */
    @NotNull
    private Integer cantidadSubsidiosLiquidadosInvalidez;

    /**
     * Valor de la cantidad de periodos retroactivos en el mes a validar en las consultas que referencian históricos
     * sobre el proceso de liquidacion
     */
    @NotNull
    private Integer cantidadPeriodosRetroactivosMes;

    /**
     * Valor de la cantidad de días máximos para el registro de una novedad de fallecimiento
     */
    @NotNull
    private Integer diasNovedadFallecimiento;

    /**
     * Valor del código de la caja de compensación
     */
    @NotNull
    private String codigoCajaCompensacion;

    /**
     * Valor del calendario de pagos para los subsidios programados en una liquidación de fallecimiento
     */
    @NotNull
    private Byte calendarioPagoFallecimiento;

    /**
     * valor que será evaluado al ejecutar cualquier tipo de liquidación en las condiciones del trabajador 
     * en las validaciones correspondientes al Salario del trabajador.
     */
    private String baseIngresos;
    
    /**
     * valida si debe considerar la suma de salarios del cónyuge activo
     * para denegar el derecho a los Beneficiarios tipo Padre
     */
    private Boolean validarSumatoriaConyugeBenPadre;
    
    /**
     * valida si debe considerar la suma de salarios del cónyuge activo
     * para denegar el derecho a los Beneficiarios tipo hijo
     */
    private Boolean validarSumatoriaConyugeBenHijo;
    
    /**
     * valida si debe considerar la suma de salarios del cónyuge activo
     * para denegar el derecho a los Beneficiarios tipo hijo con clasificación igual a “Hermano Huérfano”
     */
    private Boolean validarSumatoriaConyugeBenHermanoHuerfano;
    
    /**
     * @return the idParametrizacionCondicionesSubsidio
     */
    public Long getIdParametrizacionCondicionesSubsidio() {
        return idParametrizacionCondicionesSubsidio;
    }

    /**
     * @param idParametrizacionCondicionesSubsidio
     *        the idParametrizacionCondicionesSubsidio to set
     */
    public void setIdParametrizacionCondicionesSubsidio(Long idParametrizacionCondicionesSubsidio) {
        this.idParametrizacionCondicionesSubsidio = idParametrizacionCondicionesSubsidio;
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
     * @return the valorCuotaAnualBase
     */
    public BigDecimal getValorCuotaAnualBase() {
        return valorCuotaAnualBase;
    }

    /**
     * @param valorCuotaAnualBase
     *        the valorCuotaAnualBase to set
     */
    public void setValorCuotaAnualBase(BigDecimal valorCuotaAnualBase) {
        this.valorCuotaAnualBase = valorCuotaAnualBase;
    }

    /**
     * @return the valorCuotaAnualAgraria
     */
    public BigDecimal getValorCuotaAnualAgraria() {
        return valorCuotaAnualAgraria;
    }

    /**
     * @param valorCuotaAnualAgraria
     *        the valorCuotaAnualAgraria to set
     */
    public void setValorCuotaAnualAgraria(BigDecimal valorCuotaAnualAgraria) {
        this.valorCuotaAnualAgraria = valorCuotaAnualAgraria;
    }

    /**
     * @return the esProgramaDecreto4904
     */
    public Boolean getEsProgramaDecreto4904() {
        return esProgramaDecreto4904;
    }

    /**
     * @param esProgramaDecreto4904
     *        the esProgramaDecreto4904 to set
     */
    public void setEsProgramaDecreto4904(Boolean esProgramaDecreto4904) {
        this.esProgramaDecreto4904 = esProgramaDecreto4904;
    }

    /**
     * @return the esRetroactivoNovInvalidez
     */
    public Boolean getEsRetroactivoNovInvalidez() {
        return esRetroactivoNovInvalidez;
    }

    /**
     * @param esRetroactivoNovInvalidez
     *        the esRetroactivoNovInvalidez to set
     */
    public void setEsRetroactivoNovInvalidez(Boolean esRetroactivoNovInvalidez) {
        this.esRetroactivoNovInvalidez = esRetroactivoNovInvalidez;
    }

    /**
     * @return the esRetroactivoReingresoEmpleadores
     */
    public Boolean getEsRetroactivoReingresoEmpleadores() {
        return esRetroactivoReingresoEmpleadores;
    }

    /**
     * @param esRetroactivoReingresoEmpleadores
     *        the esRetroactivoReingresoEmpleadores to set
     */
    public void setEsRetroactivoReingresoEmpleadores(Boolean esRetroactivoReingresoEmpleadores) {
        this.esRetroactivoReingresoEmpleadores = esRetroactivoReingresoEmpleadores;
    }

    /**
     * @return the cantidadSubsidiosLiquidados
     */
    public Integer getCantidadSubsidiosLiquidados() {
        return cantidadSubsidiosLiquidados;
    }

    /**
     * @param cantidadSubsidiosLiquidados
     *        the cantidadSubsidiosLiquidados to set
     */
    public void setCantidadSubsidiosLiquidados(Integer cantidadSubsidiosLiquidados) {
        this.cantidadSubsidiosLiquidados = cantidadSubsidiosLiquidados;
    }

    /**
     * @return the montoSubsidiosLiquidados
     */
    public BigDecimal getMontoSubsidiosLiquidados() {
        return montoSubsidiosLiquidados;
    }

    /**
     * @param montoSubsidiosLiquidados
     *        the montoSubsidiosLiquidados to set
     */
    public void setMontoSubsidiosLiquidados(BigDecimal montoSubsidiosLiquidados) {
        this.montoSubsidiosLiquidados = montoSubsidiosLiquidados;
    }

    /**
     * @return the cantidadSubsidiosLiquidadosInvalidez
     */
    public Integer getCantidadSubsidiosLiquidadosInvalidez() {
        return cantidadSubsidiosLiquidadosInvalidez;
    }

    /**
     * @param cantidadSubsidiosLiquidadosInvalidez
     *        the cantidadSubsidiosLiquidadosInvalidez to set
     */
    public void setCantidadSubsidiosLiquidadosInvalidez(Integer cantidadSubsidiosLiquidadosInvalidez) {
        this.cantidadSubsidiosLiquidadosInvalidez = cantidadSubsidiosLiquidadosInvalidez;
    }

    /**
     * @return the cantidadPeriodosRetroactivosMes
     */
    public Integer getCantidadPeriodosRetroactivosMes() {
        return cantidadPeriodosRetroactivosMes;
    }

    /**
     * @param cantidadPeriodosRetroactivosMes
     *        the cantidadPeriodosRetroactivosMes to set
     */
    public void setCantidadPeriodosRetroactivosMes(Integer cantidadPeriodosRetroactivosMes) {
        this.cantidadPeriodosRetroactivosMes = cantidadPeriodosRetroactivosMes;
    }

    /**
     * @return the diasNovedadFallecimiento
     */
    public Integer getDiasNovedadFallecimiento() {
        return diasNovedadFallecimiento;
    }

    /**
     * @param diasNovedadFallecimiento
     *        the diasNovedadFallecimiento to set
     */
    public void setDiasNovedadFallecimiento(Integer diasNovedadFallecimiento) {
        this.diasNovedadFallecimiento = diasNovedadFallecimiento;
    }

    /**
     * @return the codigoCajaCompensacion
     */
    public String getCodigoCajaCompensacion() {
        return codigoCajaCompensacion;
    }

    /**
     * @param codigoCajaCompensacion
     *        the codigoCajaCompensacion to set
     */
    public void setCodigoCajaCompensacion(String codigoCajaCompensacion) {
        this.codigoCajaCompensacion = codigoCajaCompensacion;
    }

    /**
     * @return the calendarioPagoFallecimiento
     */
    public Byte getCalendarioPagoFallecimiento() {
        return calendarioPagoFallecimiento;
    }

    /**
     * @param calendarioPagoFallecimiento
     *        the calendarioPagoFallecimiento to set
     */
    public void setCalendarioPagoFallecimiento(Byte calendarioPagoFallecimiento) {
        this.calendarioPagoFallecimiento = calendarioPagoFallecimiento;
    }
    
    public String getBaseIngresos() {
		return baseIngresos;
	}

	public void setBaseIngresos(String baseIngresos) {
		this.baseIngresos = baseIngresos;
	}

	/**
     * @return the validarSumatoriaconyugeBenPadre
     */
    public Boolean getValidarSumatoriaConyugeBenPadre() {
        return validarSumatoriaConyugeBenPadre;
    }

    /**
     * @param validarSumatoriaconyugeBenPadre the validarSumatoriaconyugeBenPadre to set
     */
    public void setValidarSumatoriaConyugeBenPadre(Boolean validarSumatoriaConyugeBenPadre) {
        this.validarSumatoriaConyugeBenPadre = validarSumatoriaConyugeBenPadre;
    }

    /**
     * @return the validarSumatoriaconyugeBenHijo
     */
    public Boolean getValidarSumatoriaConyugeBenHijo() {
        return validarSumatoriaConyugeBenHijo;
    }

    /**
     * @param validarSumatoriaconyugeBenHijo the validarSumatoriaconyugeBenHijo to set
     */
    public void setValidarSumatoriaConyugeBenHijo(Boolean validarSumatoriaConyugeBenHijo) {
        this.validarSumatoriaConyugeBenHijo = validarSumatoriaConyugeBenHijo;
    }

    /**
     * @return the validarSumatoriaconyugeBenHermanoHuerfano
     */
    public Boolean getValidarSumatoriaConyugeBenHermanoHuerfano() {
        return validarSumatoriaConyugeBenHermanoHuerfano;
    }

    /**
     * @param validarSumatoriaconyugeBenHermanoHuerfano the validarSumatoriaconyugeBenHermanoHuerfano to set
     */
    public void setValidarSumatoriaConyugeBenHermanoHuerfano(Boolean validarSumatoriaConyugeBenHermanoHuerfano) {
        this.validarSumatoriaConyugeBenHermanoHuerfano = validarSumatoriaConyugeBenHermanoHuerfano;
    }

    /**
     * Metodo que convierte una entidad a DTO
     * @param parametrizacionCondicionesSubsidio
     *        Entidad que corresponde a ParametrizacionCondicionesSubsidio
     */
    public ParametrizacionCondicionesSubsidioModeloDTO convertToDTO(ParametrizacionCondicionesSubsidio parametrizacionCondicionesSubsidio) {
        this.anioVigenciaParametrizacion = parametrizacionCondicionesSubsidio.getAnioVigenciaParametrizacion();
        this.esProgramaDecreto4904 = parametrizacionCondicionesSubsidio.getEsProgramaDecreto4904();
        this.esRetroactivoNovInvalidez = parametrizacionCondicionesSubsidio.getEsRetroactivoNovInvalidez();
        this.esRetroactivoReingresoEmpleadores = parametrizacionCondicionesSubsidio.getEsRetroactivoReingresoEmpleadores();
        if (parametrizacionCondicionesSubsidio.getFechaPeriodoFinal() != null) {
            this.fechaPeriodoFinal = parametrizacionCondicionesSubsidio.getFechaPeriodoFinal();
        }
        if (parametrizacionCondicionesSubsidio.getFechaPeriodoInicial() != null) {
            this.fechaPeriodoInicial = parametrizacionCondicionesSubsidio.getFechaPeriodoInicial();
        }
        this.idParametrizacionCondicionesSubsidio = parametrizacionCondicionesSubsidio.getIdParametrizacionCondicionesSubsidio();
        this.valorCuotaAnualAgraria = parametrizacionCondicionesSubsidio.getValorCuotaAnualAgraria();
        this.valorCuotaAnualBase = parametrizacionCondicionesSubsidio.getValorCuotaAnualBase();
        this.cantidadSubsidiosLiquidados = parametrizacionCondicionesSubsidio.getCantidadSubsidiosLiquidados();
        this.montoSubsidiosLiquidados = parametrizacionCondicionesSubsidio.getMontoSubsidiosLiquidados();
        this.cantidadSubsidiosLiquidadosInvalidez = parametrizacionCondicionesSubsidio.getCantidadSubsidiosLiquidadosInvalidez();
        this.cantidadPeriodosRetroactivosMes = parametrizacionCondicionesSubsidio.getCantidadPeriodosRetroactivosMes();
        this.diasNovedadFallecimiento = parametrizacionCondicionesSubsidio.getDiasNovedadFallecimiento();
        this.codigoCajaCompensacion = parametrizacionCondicionesSubsidio.getCodigoCajaCompensacion();
        this.calendarioPagoFallecimiento = parametrizacionCondicionesSubsidio.getCalendarioPagoFallecimiento();
        this.baseIngresos = parametrizacionCondicionesSubsidio.getBaseIngresos();
        this.validarSumatoriaConyugeBenPadre = parametrizacionCondicionesSubsidio.getValidarSumatoriaConyugeBenPadre();
        this.validarSumatoriaConyugeBenHijo = parametrizacionCondicionesSubsidio.getValidarSumatoriaConyugeBenHijo();
        this.validarSumatoriaConyugeBenHermanoHuerfano = parametrizacionCondicionesSubsidio.getValidarSumatoriaConyugeBenHermanoHuerfano();
        return this;
    }

    /**
     * Metodo que convierte DTO a entidad
     * @return Entidad convertida
     */
    public ParametrizacionCondicionesSubsidio convertToEntity() {
        ParametrizacionCondicionesSubsidio parametrizacionCondicionesSubsidio = new ParametrizacionCondicionesSubsidio();
        parametrizacionCondicionesSubsidio.setAnioVigenciaParametrizacion(this.getAnioVigenciaParametrizacion());
        parametrizacionCondicionesSubsidio.setEsProgramaDecreto4904(this.getEsProgramaDecreto4904());
        parametrizacionCondicionesSubsidio.setEsRetroactivoNovInvalidez(this.getEsRetroactivoNovInvalidez());
        parametrizacionCondicionesSubsidio.setEsRetroactivoReingresoEmpleadores(this.getEsRetroactivoReingresoEmpleadores());
        if (this.getFechaPeriodoFinal() != null) {
            parametrizacionCondicionesSubsidio.setFechaPeriodoFinal(this.getFechaPeriodoFinal());
        }
        if (this.getFechaPeriodoInicial() != null) {
            parametrizacionCondicionesSubsidio.setFechaPeriodoInicial(this.getFechaPeriodoInicial());
        }
        parametrizacionCondicionesSubsidio.setIdParametrizacionCondicionesSubsidio(this.getIdParametrizacionCondicionesSubsidio());
        parametrizacionCondicionesSubsidio.setValorCuotaAnualAgraria(this.getValorCuotaAnualAgraria());
        parametrizacionCondicionesSubsidio.setValorCuotaAnualBase(this.getValorCuotaAnualBase());
        parametrizacionCondicionesSubsidio.setCantidadSubsidiosLiquidados(this.getCantidadSubsidiosLiquidados());
        parametrizacionCondicionesSubsidio.setMontoSubsidiosLiquidados(this.getMontoSubsidiosLiquidados());
        parametrizacionCondicionesSubsidio.setCantidadSubsidiosLiquidadosInvalidez(this.getCantidadSubsidiosLiquidadosInvalidez());
        parametrizacionCondicionesSubsidio.setCantidadPeriodosRetroactivosMes(this.getCantidadPeriodosRetroactivosMes());
        parametrizacionCondicionesSubsidio.setDiasNovedadFallecimiento(this.getDiasNovedadFallecimiento());
        parametrizacionCondicionesSubsidio.setCodigoCajaCompensacion(this.getCodigoCajaCompensacion());
        parametrizacionCondicionesSubsidio.setCalendarioPagoFallecimiento(this.getCalendarioPagoFallecimiento());
        parametrizacionCondicionesSubsidio.setBaseIngresos(this.getBaseIngresos());
        parametrizacionCondicionesSubsidio.setValidarSumatoriaConyugeBenPadre(this.getValidarSumatoriaConyugeBenPadre());
        parametrizacionCondicionesSubsidio.setValidarSumatoriaConyugeBenHijo(this.getValidarSumatoriaConyugeBenHijo());
        parametrizacionCondicionesSubsidio.setValidarSumatoriaConyugeBenHermanoHuerfano(this.getValidarSumatoriaConyugeBenHermanoHuerfano());
        return parametrizacionCondicionesSubsidio;
    }

}
