package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;

/**
 * <b>Descripcion:</b> DTO que retpresenta la cabecera de la pestaña de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CabeceraPestanaAporteNovedadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tipo ID del cotizante
     */
    private TipoIdentificacionEnum tipoIdCotizante;

    /**
     * Número identificación del cotizante
     */
    private String idCotizante;

    /**
     * Nombre del cotizante
     */
    private String nombreCotizante;

    /**
     * Valor de los aportes registrados
     */
    private BigDecimal valorAportesRegistrados;

    /**
     * Valor de los aportes relacionados
     */
    private BigDecimal valorAportesRelacionados;

    /**
     * Valor total de los aportes
     */
    private BigDecimal valorTotalAportes;

    /**
     * Estado de evaluación del aporte
     */
    private EstadoAporteEnum estadoEvaluacionAporte;

    /**
     * Estado de evaluación vs BD
     */
    private EstadoAporteEnum estadoEvaluacionBD;

    /**
     * Resultado de la evaluación de corrección
     */
    private EstadoValidacionRegistroCorreccionEnum estadoEvaluacionCorreccion;

    /**
     * Estado del cotizante
     */
    private EstadoAfiliadoEnum estadoAfiliacionCotizante;

    /**
     * Fecha de ingreso del cotizante (Core)
     */
    private Long fechaIngresoCotizante;

    /**
     * Fecha de retiro del cotizante (Core)
     */
    private Long fechaRetiroCotizante;

    /**
     * Estado del solicitante
     */
    private EstadoAfiliadoEnum estadoSolicitante;

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante
     *        the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the idCotizante
     */
    public String getIdCotizante() {
        return idCotizante;
    }

    /**
     * @param idCotizante
     *        the idCotizante to set
     */
    public void setIdCotizante(String idCotizante) {
        this.idCotizante = idCotizante;
    }

    /**
     * @return the nombreCotizante
     */
    public String getNombreCotizante() {
        return nombreCotizante;
    }

    /**
     * @param nombreCotizante
     *        the nombreCotizante to set
     */
    public void setNombreCotizante(String nombreCotizante) {
        this.nombreCotizante = nombreCotizante;
    }

    /**
     * @return the valorAportesRegistrados
     */
    public BigDecimal getValorAportesRegistrados() {
        return valorAportesRegistrados;
    }

    /**
     * @param valorAportesRegistrados
     *        the valorAportesRegistrados to set
     */
    public void setValorAportesRegistrados(BigDecimal valorAportesRegistrados) {
        this.valorAportesRegistrados = valorAportesRegistrados;
    }

    /**
     * @return the valorAportesRelacionados
     */
    public BigDecimal getValorAportesRelacionados() {
        return valorAportesRelacionados;
    }

    /**
     * @param valorAportesRelacionados
     *        the valorAportesRelacionados to set
     */
    public void setValorAportesRelacionados(BigDecimal valorAportesRelacionados) {
        this.valorAportesRelacionados = valorAportesRelacionados;
    }

    /**
     * @return the valorTotalAportes
     */
    public BigDecimal getValorTotalAportes() {
        return valorTotalAportes;
    }

    /**
     * @param valorTotalAportes
     *        the valorTotalAportes to set
     */
    public void setValorTotalAportes(BigDecimal valorTotalAportes) {
        this.valorTotalAportes = valorTotalAportes;
    }

    /**
     * @return the estadoEvaluacionAporte
     */
    public EstadoAporteEnum getEstadoEvaluacionAporte() {
        return estadoEvaluacionAporte;
    }

    /**
     * @param estadoEvaluacionAporte
     *        the estadoEvaluacionAporte to set
     */
    public void setEstadoEvaluacionAporte(EstadoAporteEnum estadoEvaluacionAporte) {
        this.estadoEvaluacionAporte = estadoEvaluacionAporte;
    }

    /**
     * @return the estadoEvaluacionCorreccion
     */
    public EstadoValidacionRegistroCorreccionEnum getEstadoEvaluacionCorreccion() {
        return estadoEvaluacionCorreccion;
    }

    /**
     * @param estadoEvaluacionCorreccion
     *        the estadoEvaluacionCorreccion to set
     */
    public void setEstadoEvaluacionCorreccion(EstadoValidacionRegistroCorreccionEnum estadoEvaluacionCorreccion) {
        this.estadoEvaluacionCorreccion = estadoEvaluacionCorreccion;
    }

    /**
     * @return the estadoEvaluacionBD
     */
    public EstadoAporteEnum getEstadoEvaluacionBD() {
        return estadoEvaluacionBD;
    }

    /**
     * @param estadoEvaluacionBD
     *        the estadoEvaluacionBD to set
     */
    public void setEstadoEvaluacionBD(EstadoAporteEnum estadoEvaluacionBD) {
        this.estadoEvaluacionBD = estadoEvaluacionBD;
    }

    /**
     * @return the estadoAfiliacionCotizante
     */
    public EstadoAfiliadoEnum getEstadoAfiliacionCotizante() {
        return estadoAfiliacionCotizante;
    }

    /**
     * @param estadoAfiliacionCotizante
     *        the estadoAfiliacionCotizante to set
     */
    public void setEstadoAfiliacionCotizante(EstadoAfiliadoEnum estadoAfiliacionCotizante) {
        this.estadoAfiliacionCotizante = estadoAfiliacionCotizante;
    }

    /**
     * @return the fechaIngresoCotizante
     */
    public Long getFechaIngresoCotizante() {
        return fechaIngresoCotizante;
    }

    /**
     * @param fechaIngresoCotizante
     *        the fechaIngresoCotizante to set
     */
    public void setFechaIngresoCotizante(Long fechaIngresoCotizante) {
        this.fechaIngresoCotizante = fechaIngresoCotizante;
    }

    /**
     * @return the fechaRetiroCotizante
     */
    public Long getFechaRetiroCotizante() {
        return fechaRetiroCotizante;
    }

    /**
     * @param fechaRetiroCotizante
     *        the fechaRetiroCotizante to set
     */
    public void setFechaRetiroCotizante(Long fechaRetiroCotizante) {
        this.fechaRetiroCotizante = fechaRetiroCotizante;
    }

    /**
     * @return the estadoSolicitante
     */
    public EstadoAfiliadoEnum getEstadoSolicitante() {
        return estadoSolicitante;
    }

    /**
     * @param estadoSolicitante
     *        the estadoSolicitante to set
     */
    public void setEstadoSolicitante(EstadoAfiliadoEnum estadoSolicitante) {
        this.estadoSolicitante = estadoSolicitante;
    }

    /**
     * Método para diligenciar la cabecera con base en un Registro Detallado
     * @param registroDetallado
     *        DTO del registro detallado tomando como base para diligenciar la cabecera
     */
    public void diligenciarPorRegistroDetallado(RegistroDetalladoModeloDTO registroDetallado) {
        this.tipoIdCotizante = registroDetallado.getTipoIdentificacionCotizante();
        this.idCotizante = registroDetallado.getNumeroIdentificacionCotizante();
        this.nombreCotizante = registroDetallado.componerNombreCotizante();
        this.estadoEvaluacionCorreccion = registroDetallado.getEstadoValidacionCorreccion();

        if (EstadoRegistroAporteEnum.REGISTRADO.equals(registroDetallado.getOutEstadoRegistroRelacionAporte())) {
            this.valorAportesRegistrados = registroDetallado.getAporteObligatorio();
            this.valorAportesRelacionados = BigDecimal.valueOf(0);
        }
        else if (EstadoRegistroAporteEnum.RELACIONADO.equals(registroDetallado.getOutEstadoRegistroRelacionAporte())) {
            this.valorAportesRelacionados = registroDetallado.getAporteObligatorio();
            this.valorAportesRegistrados = BigDecimal.valueOf(0);
        }

        this.valorTotalAportes = registroDetallado.getAporteObligatorio();
        this.estadoEvaluacionAporte = registroDetallado.getOutEstadoEvaluacionAporte();
        this.estadoEvaluacionBD = registroDetallado.getEstadoEvaluacion();
        this.estadoEvaluacionCorreccion = registroDetallado.getEstadoValidacionCorreccion();
        this.fechaIngresoCotizante = registroDetallado.getOutFechaIngresoCotizante();
        this.estadoSolicitante = registroDetallado.getOutEstadoSolicitante() != null
                ? EstadoAfiliadoEnum.valueOf(registroDetallado.getOutEstadoSolicitante()) : null;
    }
}
