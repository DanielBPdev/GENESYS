package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de resultado histórico para las liquidaciones de fallecimiento dispersadas o
 * canceladas
 * <b>Módulo:</b> Asopagos - HU-317-509 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoHistoricoLiquidacionFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Número de radicado de la solicitud histórica
     */
    private String numeroRadicacion;

    /**
     * Tipo de identificación del afiliado principal
     */
    private TipoIdentificacionEnum tipoIdentificacionAfiliado;

    /**
     * Número de identificación del afiliado principal
     */
    private String numeroIdentificacionAfiliado;

    /**
     * Nombre del afiliado principla
     */
    private String nombreAfiliado;

    /**
     * Tipo de identificación del fallecido
     */
    private List<TipoIdentificacionEnum> tiposIdentificacionFallecidos;

    /**
     * Número de identificación del fallecido
     */
    private List<String> numerosIdentificacionFallecidos;

    /**
     * Nombre del fallecido
     */
    private List<String> nombresFallecidos;

    /**
     * Período en el que se presenta el fallecimiento de la persona
     */
    private Date periodoFallecimiento;

    /**
     * Valor total dispersado por concepto de fallecimiento
     */
    private BigDecimal montoDispersado;

    /**
     * Fecha en la que se realiza el registro de la solicitud de liquidación
     */
    private Date fechaRadicacion;

    /**
     * Fecha en la que se modifica el estado del derecho a derecho asignado
     */
    private Date fechaDispersion;

    /**
     * Estado de la liquidación de fallecimiento
     */
    private ResultadoProcesoEnum estado;

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the tipoIdentificacionAfiliado
     */
    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return tipoIdentificacionAfiliado;
    }

    /**
     * @param tipoIdentificacionAfiliado
     *        the tipoIdentificacionAfiliado to set
     */
    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }

    /**
     * @param numeroIdentificacionAfiliado
     *        the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    /**
     * @return the nombreAfiliado
     */
    public String getNombreAfiliado() {
        return nombreAfiliado;
    }

    /**
     * @param nombreAfiliado
     *        the nombreAfiliado to set
     */
    public void setNombreAfiliado(String nombreAfiliado) {
        this.nombreAfiliado = nombreAfiliado;
    }

    /**
     * @return the tiposIdentificacionFallecidos
     */
    public List<TipoIdentificacionEnum> getTiposIdentificacionFallecidos() {
        return tiposIdentificacionFallecidos;
    }

    /**
     * @param tiposIdentificacionFallecidos
     *        the tiposIdentificacionFallecidos to set
     */
    public void setTiposIdentificacionFallecidos(List<TipoIdentificacionEnum> tiposIdentificacionFallecidos) {
        this.tiposIdentificacionFallecidos = tiposIdentificacionFallecidos;
    }

    /**
     * @return the numerosIdentificacionFallecidos
     */
    public List<String> getNumerosIdentificacionFallecidos() {
        return numerosIdentificacionFallecidos;
    }

    /**
     * @param numerosIdentificacionFallecidos
     *        the numerosIdentificacionFallecidos to set
     */
    public void setNumerosIdentificacionFallecidos(List<String> numerosIdentificacionFallecidos) {
        this.numerosIdentificacionFallecidos = numerosIdentificacionFallecidos;
    }

    /**
     * @return the nombresFallecidos
     */
    public List<String> getNombresFallecidos() {
        return nombresFallecidos;
    }

    /**
     * @param nombresFallecidos
     *        the nombresFallecidos to set
     */
    public void setNombresFallecidos(List<String> nombresFallecidos) {
        this.nombresFallecidos = nombresFallecidos;
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
     * @return the montoDispersado
     */
    public BigDecimal getMontoDispersado() {
        return montoDispersado;
    }

    /**
     * @param montoDispersado
     *        the montoDispersado to set
     */
    public void setMontoDispersado(BigDecimal montoDispersado) {
        this.montoDispersado = montoDispersado;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the fechaDispersion
     */
    public Date getFechaDispersion() {
        return fechaDispersion;
    }

    /**
     * @param fechaDispersion
     *        the fechaDispersion to set
     */
    public void setFechaDispersion(Date fechaDispersion) {
        this.fechaDispersion = fechaDispersion;
    }

    /**
     * @return the estado
     */
    public ResultadoProcesoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(ResultadoProcesoEnum estado) {
        this.estado = estado;
    }

}
