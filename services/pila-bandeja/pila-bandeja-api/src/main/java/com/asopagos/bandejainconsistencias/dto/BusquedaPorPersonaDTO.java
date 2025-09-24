package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class BusquedaPorPersonaDTO implements Serializable {
    private static final long serialVersionUID = 2025959377199381942L;

    /** Registro detallado ID */
    private Long detId;

    /** Número de identificacion del cotizante */
    private String detNumeroIdentificacionCotizante;

    /** Registro de control detalle */
    private Long detRegistroControl;

    /** Número de planilla */
    private String genNumPlanilla;

    /** Tipo de planilla */
    private String genTipoPlanilla;

    /** Fecha de procesamiento del aporte */
    private Long detOutFechaProcesamientoValidRegAporte;

    /** Aporte obligatorio */
    private BigDecimal detAporteObligatorio;

    /** Tipo de cotizante */
    private Short detTipoCotizante;

    /** Estado de valiacion V0 */
    private EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV0;

    /** Estado de valiacion V1 */
    private EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV1;

    /** Estado de valiacion V2 */
    private EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV2;

    /** Estado de valiacion V3 */
    private EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV3;

    /** Tipo de identificacion del aportante */
    private TipoIdentificacionEnum genTipoIdentificacionAportante;

    /** Número de identificacion del aportante */
    private String genNumeroIdentificacionAportante;

    /** Periodo del aporte */
    private String genPeriodoAporte;

    /** Tipo identificacion del cotizante */
    private TipoIdentificacionEnum detTipoIdentificacionCotizante;

    /** Registro de control general */
    private Long genRegistroControl;

    /** Estado del registro aporte */
    private EstadoRegistroAportesArchivoEnum detOutEstadoRegistroAporte;

    /** Estado del solicitante */
    private String detOutEstadoSolicitante;

    /** Valor total de los aportes */
    private BigDecimal genValTotalApoObligatorio;

    /** Digito de verificacion del aportante */
    private Short genDigVerAportante;

    /** Nombre del aportante */
    private String genNombreAportante;

    /** Sumatoria de aportes (varia de acuerdo a la consulta) */
    private BigDecimal genSumatoriaAportes;

    /** Conteo del total de cotizantes en el registro */
    private Long genTotalCotizantes;

    /** Valor del interes mora */
    private BigDecimal genValorInteresMora;

    /**
     * Constructor por defecto
     */
    public BusquedaPorPersonaDTO() {
    }

    /**
     * Constructor para la consulta PilaBandejaService.PilaStaging.BuscarPorPersonaCriterios
     * @param detId
     * @param detNumeroIdentificacionCotizante
     * @param detRegistroControl
     * @param genNumPlanilla
     * @param genTipoPlanilla
     * @param detOutFechaProcesamientoValidRegAporte
     * @param detAporteObligatorio
     * @param detTipoCotizante
     * @param detOutEstadoValidacionV0
     * @param detOutEstadoValidacionV1
     * @param detOutEstadoValidacionV2
     * @param detOutEstadoValidacionV3
     * @param genTipoIdentificacionAportante
     * @param genNumeroIdentificacionAportante
     * @param genPeriodoAporte
     * @param detTipoIdentificacionCotizante
     * @param genRegistroControl
     * @param detOutEstadoRegistroAporte
     * @param detOutEstadoSolicitante
     * @param genValTotalApoObligatorio
     * @param genDigVerAportante
     */
    public BusquedaPorPersonaDTO(Long detId, String detNumeroIdentificacionCotizante, Long detRegistroControl, String genNumPlanilla,
            String genTipoPlanilla, Date detOutFechaProcesamientoValidRegAporte, BigDecimal detAporteObligatorio, Short detTipoCotizante,
            EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV0, EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV1,
            EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV2, EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV3,
            TipoIdentificacionEnum genTipoIdentificacionAportante, String genNumeroIdentificacionAportante, String genPeriodoAporte,
            TipoIdentificacionEnum detTipoIdentificacionCotizante, Long genRegistroControl,
            EstadoRegistroAportesArchivoEnum detOutEstadoRegistroAporte, String detOutEstadoSolicitante,
            BigDecimal genValTotalApoObligatorio, Short genDigVerAportante, String genNombreAportante, BigDecimal genValorInteresMora) {
        this.detId = detId;
        this.detNumeroIdentificacionCotizante = detNumeroIdentificacionCotizante;
        this.detRegistroControl = detRegistroControl;
        this.genNumPlanilla = genNumPlanilla;
        this.genTipoPlanilla = genTipoPlanilla;
        if (detOutFechaProcesamientoValidRegAporte != null) {
            this.detOutFechaProcesamientoValidRegAporte = detOutFechaProcesamientoValidRegAporte.getTime();
        }
        this.detAporteObligatorio = detAporteObligatorio;
        this.detTipoCotizante = detTipoCotizante;
        this.detOutEstadoValidacionV0 = detOutEstadoValidacionV0;
        this.detOutEstadoValidacionV1 = detOutEstadoValidacionV1;
        this.detOutEstadoValidacionV2 = detOutEstadoValidacionV2;
        this.detOutEstadoValidacionV3 = detOutEstadoValidacionV3;
        this.genTipoIdentificacionAportante = genTipoIdentificacionAportante;
        this.genNumeroIdentificacionAportante = genNumeroIdentificacionAportante;
        this.genPeriodoAporte = genPeriodoAporte;
        this.detTipoIdentificacionCotizante = detTipoIdentificacionCotizante;
        this.genRegistroControl = genRegistroControl;
        this.detOutEstadoRegistroAporte = detOutEstadoRegistroAporte;
        this.detOutEstadoSolicitante = detOutEstadoSolicitante;
        this.genValTotalApoObligatorio = genValTotalApoObligatorio;
        this.genDigVerAportante = genDigVerAportante;
        this.genNombreAportante = genNombreAportante;
        this.genValorInteresMora = genValorInteresMora;
    }

    /**
     * Constructor para la consulta PilaBandejaService.PilaStaging.BuscarPorPersonaPensionadoCriterios
     * @param genNombreAportante
     * @param detNumeroIdentificacionCotizante
     * @param detRegistroControl
     * @param genNumPlanilla
     * @param genTipoPlanilla
     * @param detOutFechaProcesamientoValidRegAporte
     * @param detAporteObligatorio
     * @param detTipoCotizante
     * @param detOutEstadoValidacionV1
     * @param genTipoIdentificacionAportante
     * @param genNumeroIdentificacionAportante
     * @param genPeriodoAporte
     * @param detTipoIdentificacionCotizante
     * @param genRegistroControl
     * @param detOutEstadoRegistroAporte
     * @param genDigVerAportante
     * @param genValTotalApoObligatorio
     */
    public BusquedaPorPersonaDTO(String genNombreAportante, String detNumeroIdentificacionCotizante, Long detRegistroControl,
            String genNumPlanilla, String genTipoPlanilla, Date detOutFechaProcesamientoValidRegAporte, BigDecimal detAporteObligatorio,
            Short detTipoCotizante, EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV1,
            TipoIdentificacionEnum genTipoIdentificacionAportante, String genNumeroIdentificacionAportante, String genPeriodoAporte,
            TipoIdentificacionEnum detTipoIdentificacionCotizante, Long genRegistroControl,
            EstadoRegistroAportesArchivoEnum detOutEstadoRegistroAporte, Short genDigVerAportante, BigDecimal genValTotalApoObligatorio, 
            String detOutEstadoSolicitante) {
        this.detNumeroIdentificacionCotizante = detNumeroIdentificacionCotizante;
        this.detRegistroControl = detRegistroControl;
        this.genNumPlanilla = genNumPlanilla;
        this.genTipoPlanilla = genTipoPlanilla;
        if (detOutFechaProcesamientoValidRegAporte != null) {
            this.detOutFechaProcesamientoValidRegAporte = detOutFechaProcesamientoValidRegAporte.getTime();
        }
        this.detAporteObligatorio = detAporteObligatorio;
        this.detTipoCotizante = detTipoCotizante;
        this.detOutEstadoValidacionV1 = detOutEstadoValidacionV1;
        this.genTipoIdentificacionAportante = genTipoIdentificacionAportante;
        this.genNumeroIdentificacionAportante = genNumeroIdentificacionAportante;
        this.genPeriodoAporte = genPeriodoAporte;
        this.detTipoIdentificacionCotizante = detTipoIdentificacionCotizante;
        this.genRegistroControl = genRegistroControl;
        this.detOutEstadoRegistroAporte = detOutEstadoRegistroAporte;
        this.genNombreAportante = genNombreAportante;
        this.genDigVerAportante = genDigVerAportante;
        this.genValTotalApoObligatorio = genValTotalApoObligatorio;
        this.detOutEstadoSolicitante = detOutEstadoSolicitante;
    }

    /*
     * registroD.redOUTEstadoRegistroAporte
     */

    /**
     * @return the detId
     */
    public Long getDetId() {
        return detId;
    }

    /**
     * @param detId
     *        the detId to set
     */
    public void setDetId(Long detId) {
        this.detId = detId;
    }

    /**
     * @return the detNumeroIdentificacionCotizante
     */
    public String getDetNumeroIdentificacionCotizante() {
        return detNumeroIdentificacionCotizante;
    }

    /**
     * @param detNumeroIdentificacionCotizante
     *        the detNumeroIdentificacionCotizante to set
     */
    public void setDetNumeroIdentificacionCotizante(String detNumeroIdentificacionCotizante) {
        this.detNumeroIdentificacionCotizante = detNumeroIdentificacionCotizante;
    }

    /**
     * @return the detRegistroControl
     */
    public Long getDetRegistroControl() {
        return detRegistroControl;
    }

    /**
     * @param detRegistroControl
     *        the detRegistroControl to set
     */
    public void setDetRegistroControl(Long detRegistroControl) {
        this.detRegistroControl = detRegistroControl;
    }

    /**
     * @return the genNumPlanilla
     */
    public String getGenNumPlanilla() {
        return genNumPlanilla;
    }

    /**
     * @param genNumPlanilla
     *        the genNumPlanilla to set
     */
    public void setGenNumPlanilla(String genNumPlanilla) {
        this.genNumPlanilla = genNumPlanilla;
    }

    /**
     * @return the genTipoPlanilla
     */
    public String getGenTipoPlanilla() {
        return genTipoPlanilla;
    }

    /**
     * @param genTipoPlanilla
     *        the genTipoPlanilla to set
     */
    public void setGenTipoPlanilla(String genTipoPlanilla) {
        this.genTipoPlanilla = genTipoPlanilla;
    }

    /**
     * @return the detOutFechaProcesamientoValidRegAporte
     */
    public Long getDetOutFechaProcesamientoValidRegAporte() {
        return detOutFechaProcesamientoValidRegAporte;
    }

    /**
     * @param detOutFechaProcesamientoValidRegAporte
     *        the detOutFechaProcesamientoValidRegAporte to set
     */
    public void setDetOutFechaProcesamientoValidRegAporte(Long detOutFechaProcesamientoValidRegAporte) {
        this.detOutFechaProcesamientoValidRegAporte = detOutFechaProcesamientoValidRegAporte;
    }

    /**
     * @return the detAporteObligatorio
     */
    public BigDecimal getDetAporteObligatorio() {
        return detAporteObligatorio;
    }

    /**
     * @param detAporteObligatorio
     *        the detAporteObligatorio to set
     */
    public void setDetAporteObligatorio(BigDecimal detAporteObligatorio) {
        this.detAporteObligatorio = detAporteObligatorio;
    }

    /**
     * @return the detTipoCotizante
     */
    public Short getDetTipoCotizante() {
        return detTipoCotizante;
    }

    /**
     * @param detTipoCotizante
     *        the detTipoCotizante to set
     */
    public void setDetTipoCotizante(Short detTipoCotizante) {
        this.detTipoCotizante = detTipoCotizante;
    }

    /**
     * @return the detOutEstadoValidacionV0
     */
    public EstadoValidacionRegistroAporteEnum getDetOutEstadoValidacionV0() {
        return detOutEstadoValidacionV0;
    }

    /**
     * @param detOutEstadoValidacionV0
     *        the detOutEstadoValidacionV0 to set
     */
    public void setDetOutEstadoValidacionV0(EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV0) {
        this.detOutEstadoValidacionV0 = detOutEstadoValidacionV0;
    }

    /**
     * @return the detOutEstadoValidacionV1
     */
    public EstadoValidacionRegistroAporteEnum getDetOutEstadoValidacionV1() {
        return detOutEstadoValidacionV1;
    }

    /**
     * @param detOutEstadoValidacionV1
     *        the detOutEstadoValidacionV1 to set
     */
    public void setDetOutEstadoValidacionV1(EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV1) {
        this.detOutEstadoValidacionV1 = detOutEstadoValidacionV1;
    }

    /**
     * @return the detOutEstadoValidacionV2
     */
    public EstadoValidacionRegistroAporteEnum getDetOutEstadoValidacionV2() {
        return detOutEstadoValidacionV2;
    }

    /**
     * @param detOutEstadoValidacionV2
     *        the detOutEstadoValidacionV2 to set
     */
    public void setDetOutEstadoValidacionV2(EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV2) {
        this.detOutEstadoValidacionV2 = detOutEstadoValidacionV2;
    }

    /**
     * @return the detOutEstadoValidacionV3
     */
    public EstadoValidacionRegistroAporteEnum getDetOutEstadoValidacionV3() {
        return detOutEstadoValidacionV3;
    }

    /**
     * @param detOutEstadoValidacionV3
     *        the detOutEstadoValidacionV3 to set
     */
    public void setDetOutEstadoValidacionV3(EstadoValidacionRegistroAporteEnum detOutEstadoValidacionV3) {
        this.detOutEstadoValidacionV3 = detOutEstadoValidacionV3;
    }

    /**
     * @return the genTipoIdentificacionAportante
     */
    public TipoIdentificacionEnum getGenTipoIdentificacionAportante() {
        return genTipoIdentificacionAportante;
    }

    /**
     * @param genTipoIdentificacionAportante
     *        the genTipoIdentificacionAportante to set
     */
    public void setGenTipoIdentificacionAportante(TipoIdentificacionEnum genTipoIdentificacionAportante) {
        this.genTipoIdentificacionAportante = genTipoIdentificacionAportante;
    }

    /**
     * @return the genNumeroIdentificacionAportante
     */
    public String getGenNumeroIdentificacionAportante() {
        return genNumeroIdentificacionAportante;
    }

    /**
     * @param genNumeroIdentificacionAportante
     *        the genNumeroIdentificacionAportante to set
     */
    public void setGenNumeroIdentificacionAportante(String genNumeroIdentificacionAportante) {
        this.genNumeroIdentificacionAportante = genNumeroIdentificacionAportante;
    }

    /**
     * @return the genPeriodoAporte
     */
    public String getGenPeriodoAporte() {
        return genPeriodoAporte;
    }

    /**
     * @param genPeriodoAporte
     *        the genPeriodoAporte to set
     */
    public void setGenPeriodoAporte(String genPeriodoAporte) {
        this.genPeriodoAporte = genPeriodoAporte;
    }

    /**
     * @return the detTipoIdentificacionCotizante
     */
    public TipoIdentificacionEnum getDetTipoIdentificacionCotizante() {
        return detTipoIdentificacionCotizante;
    }

    /**
     * @param detTipoIdentificacionCotizante
     *        the detTipoIdentificacionCotizante to set
     */
    public void setDetTipoIdentificacionCotizante(TipoIdentificacionEnum detTipoIdentificacionCotizante) {
        this.detTipoIdentificacionCotizante = detTipoIdentificacionCotizante;
    }

    /**
     * @return the genRegistroControl
     */
    public Long getGenRegistroControl() {
        return genRegistroControl;
    }

    /**
     * @param genRegistroControl
     *        the genRegistroControl to set
     */
    public void setGenRegistroControl(Long genRegistroControl) {
        this.genRegistroControl = genRegistroControl;
    }

    /**
     * @return the detOutEstadoRegistroAporte
     */
    public EstadoRegistroAportesArchivoEnum getDetOutEstadoRegistroAporte() {
        return detOutEstadoRegistroAporte;
    }

    /**
     * @param detOutEstadoRegistroAporte
     *        the detOutEstadoRegistroAporte to set
     */
    public void setDetOutEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum detOutEstadoRegistroAporte) {
        this.detOutEstadoRegistroAporte = detOutEstadoRegistroAporte;
    }

    /**
     * @return the detOutEstadoSolicitante
     */
    public String getDetOutEstadoSolicitante() {
        return detOutEstadoSolicitante;
    }

    /**
     * @param detOutEstadoSolicitante
     *        the detOutEstadoSolicitante to set
     */
    public void setDetOutEstadoSolicitante(String detOutEstadoSolicitante) {
        this.detOutEstadoSolicitante = detOutEstadoSolicitante;
    }

    /**
     * @return the genValTotalApoObligatorio
     */
    public BigDecimal getGenValTotalApoObligatorio() {
        return genValTotalApoObligatorio;
    }

    /**
     * @param genValTotalApoObligatorio
     *        the genValTotalApoObligatorio to set
     */
    public void setGenValTotalApoObligatorio(BigDecimal genValTotalApoObligatorio) {
        this.genValTotalApoObligatorio = genValTotalApoObligatorio;
    }

    /**
     * @return the genDigVerAportante
     */
    public Short getGenDigVerAportante() {
        return genDigVerAportante;
    }

    /**
     * @param genDigVerAportante
     *        the genDigVerAportante to set
     */
    public void setGenDigVerAportante(Short genDigVerAportante) {
        this.genDigVerAportante = genDigVerAportante;
    }

    /**
     * @return the genNombreAportante
     */
    public String getGenNombreAportante() {
        return genNombreAportante;
    }

    /**
     * @param genNombreAportante
     *        the genNombreAportante to set
     */
    public void setGenNombreAportante(String genNombreAportante) {
        this.genNombreAportante = genNombreAportante;
    }

    /**
     * @return the sumatoriaAportes
     */
    public BigDecimal getGenSumatoriaAportes() {
        return genSumatoriaAportes;
    }

    /**
     * @param sumatoriaAportes
     *        the sumatoriaAportes to set
     */
    public void setGenSumatoriaAportes(BigDecimal sumatoriaAportes) {
        this.genSumatoriaAportes = sumatoriaAportes;
    }

    /**
     * @return the genTotalCotizantes
     */
    public Long getGenTotalCotizantes() {
        return genTotalCotizantes;
    }

    /**
     * @param genTotalCotizantes
     *        the genTotalCotizantes to set
     */
    public void setGenTotalCotizantes(Long genTotalCotizantes) {
        this.genTotalCotizantes = genTotalCotizantes;
    }

    /**
     * @return the genValorInteresMora
     */
    public BigDecimal getGenValorInteresMora() {
        return genValorInteresMora;
    }

    /**
     * @param genValorInteresMora
     *        the genValorInteresMora to set
     */
    public void setGenValorInteresMora(BigDecimal genValorInteresMora) {
        this.genValorInteresMora = genValorInteresMora;
    }
}
