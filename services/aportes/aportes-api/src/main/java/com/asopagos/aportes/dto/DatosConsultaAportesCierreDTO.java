package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;

/**
 * <b>Descripcion:</b> DTO que agrupa los criterioas para la consulta de datos para cierre de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-215 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosConsultaAportesCierreDTO implements Serializable {
    private static final long serialVersionUID = -524365128667258611L;

    /** Indica que se consultan correcciones */
    private Boolean esCorreccion;

    /** Indica que se está evaluando la fecha de reconocimiento del aporte */
    private Boolean evaluarReconocimiento;

    /** Indica que se está evaluando el estado del aporte */
    private Boolean evaluarEstado;

    /** Indica que se está filtrando por fecha de reconocimiento */
    private Boolean filtrarReconocimiento;

    /** Modificador de signo para los valores */
    private BigDecimal signo;

    /** Fecha de inicio del corte del pre/cierre */
    private Date fechaInicio;

    /** Fecha de fin del corte del pre/cierre */
    private Date fechaFin;

    /** Listado de los tipos de movimiento de aportes a consultar */
    private List<String> tipoMovimiento;

    /** Listado de las formas de reconocimiento del aporte a consultar */
    private List<String> reconocimiento;

    /** Listado de los estados de registro del aporte a consultar */
    private List<String> estadoRegistro;

    /** Tipo de registro en el cierre */
    private TipoRegistroEnum tipoRegistro;

    /**
     * Constructor a partir de campos para resumen
     * @param esCorreccion
     * @param evaluarReconocimiento
     * @param signo
     * @param fechaInicio
     * @param fechaFin
     * @param tipoMovimiento
     * @param reconocimiento
     * @param estadoRegistro
     * @param evaluarEstado
     * @param filtrarReconocimiento
     */
    public DatosConsultaAportesCierreDTO(Boolean esCorreccion, Boolean evaluarReconocimiento, BigDecimal signo, Date fechaInicio,
            Date fechaFin, List<String> tipoMovimiento, List<String> reconocimiento, List<String> estadoRegistro, Boolean evaluarEstado,
            Boolean filtrarReconocimiento) {
        super();
        this.esCorreccion = esCorreccion;
        this.evaluarReconocimiento = evaluarReconocimiento;
        this.signo = signo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoMovimiento = tipoMovimiento;
        this.reconocimiento = reconocimiento;
        this.estadoRegistro = estadoRegistro;
        this.evaluarEstado = evaluarEstado;
        this.filtrarReconocimiento = filtrarReconocimiento;
    }

    /**
     * @return the esCorreccion
     */
    public Boolean getEsCorreccion() {
        return esCorreccion;
    }

    /**
     * @param esCorreccion
     *        the esCorreccion to set
     */
    public void setEsCorreccion(Boolean esCorreccion) {
        this.esCorreccion = esCorreccion;
    }

    /**
     * @return the evaluarReconocimiento
     */
    public Boolean getEvaluarReconocimiento() {
        return evaluarReconocimiento;
    }

    /**
     * @param evaluarReconocimiento
     *        the evaluarReconocimiento to set
     */
    public void setEvaluarReconocimiento(Boolean evaluarReconocimiento) {
        this.evaluarReconocimiento = evaluarReconocimiento;
    }

    /**
     * @return the signo
     */
    public BigDecimal getSigno() {
        return signo;
    }

    /**
     * @param signo
     *        the signo to set
     */
    public void setSigno(BigDecimal signo) {
        this.signo = signo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the tipoMovimiento
     */
    public List<String> getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento
     *        the tipoMovimiento to set
     */
    public void setTipoMovimiento(List<String> tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the reconocimiento
     */
    public List<String> getReconocimiento() {
        return reconocimiento;
    }

    /**
     * @param reconocimiento
     *        the reconocimiento to set
     */
    public void setReconocimiento(List<String> reconocimiento) {
        this.reconocimiento = reconocimiento;
    }

    /**
     * @return the estadoRegistro
     */
    public List<String> getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro
     *        the estadoRegistro to set
     */
    public void setEstadoRegistro(List<String> estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the tipoRegistro
     */
    public TipoRegistroEnum getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * @param tipoRegistro
     *        the tipoRegistro to set
     */
    public void setTipoRegistro(TipoRegistroEnum tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    /**
     * @return the evaluarEstado
     */
    public Boolean getEvaluarEstado() {
        return evaluarEstado;
    }

    /**
     * @param evaluarEstado
     *        the evaluarEstado to set
     */
    public void setEvaluarEstado(Boolean evaluarEstado) {
        this.evaluarEstado = evaluarEstado;
    }

    /**
     * @return the filtrarReconocimiento
     */
    public Boolean getFiltrarReconocimiento() {
        return filtrarReconocimiento;
    }

    /**
     * @param filtrarReconocimiento
     *        the filtrarReconocimiento to set
     */
    public void setFiltrarReconocimiento(Boolean filtrarReconocimiento) {
        this.filtrarReconocimiento = filtrarReconocimiento;
    }
}
