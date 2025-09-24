package com.asopagos.dto.pila;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que posee la respuesta para la busqueda de control
 * sobre los registros de un aportante <br/>
 * <b>Módulo:</b> Asopagos - HU-211-389 y HU-211-410<br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */
@XmlRootElement
public class RespuestaConsultaEmpleadorDTO {

    private String nombreEmpleador;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String periodoAporte;
    private List<DetalleTablaAportanteDTO> registros;
    private Long totalAportes;
    private Integer cantidadAportes;
    private Short digitoVerificacionAportante;

    /** Número de la planilla */
    private String numeroPlanilla;

    /** Tipo de la planilla */
    private String tipoPlanilla;

    /** Fecha proceso planilla */
    private Long fechaProceso;

    /** Identificador del registro general de la corrección en BD */
    private Long idRegistroGeneralCorreccion;

    /** Identificador del registro general del original en BD */
    private Long idRegistroGeneralOriginal;

    /** ID de índice de planilla */
    private Long idIndicePlanilla;

    /** Valor total de los aportes */
    private BigDecimal valorTotalAportes;
    
    /**
     * Constructor para NamedQuery <code>PilaService.RegistroGeneral.ConsultarEstadoGeneralPlanilla</code>
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idRegistroGeneralOriginal
     * @param tipoPlanilla
     * @param idIndicePlanilla
     */
    public RespuestaConsultaEmpleadorDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            Long idRegistroGeneralOriginal, String tipoPlanilla, Long idIndicePlanilla, String periodoAporte,
            String numeroPlanilla) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.idRegistroGeneralOriginal = idRegistroGeneralOriginal;
        this.tipoPlanilla = tipoPlanilla;
        this.idIndicePlanilla = idIndicePlanilla;
        this.periodoAporte = periodoAporte;
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the nombreEmpleador
     */
    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    /**
     * @param nombreEmpleador
     *        the nombreEmpleador to set
     */
    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    /**
     * @return the registros
     */
    public List<DetalleTablaAportanteDTO> getRegistros() {
        return registros;
    }

    /**
     * @param registros
     *        the registros to set
     */
    public void setRegistros(List<DetalleTablaAportanteDTO> registros) {
        this.registros = registros;
    }

    /**
     * @return the totalAportes
     */
    public Long getTotalAportes() {
        return totalAportes;
    }

    /**
     * @param totalAportes
     *        the totalAportes to set
     */
    public void setTotalAportes(Long totalAportes) {
        this.totalAportes = totalAportes;
    }

    /**
     * @return the cantidadAportes
     */
    public Integer getCantidadAportes() {
        return cantidadAportes;
    }

    /**
     * @param cantidadAportes
     *        the cantidadAportes to set
     */
    public void setCantidadAportes(Integer cantidadAportes) {
        this.cantidadAportes = cantidadAportes;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte
     *        the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the idRegistroGeneralCorreccion
     */
    public Long getIdRegistroGeneralCorreccion() {
        return idRegistroGeneralCorreccion;
    }

    /**
     * @param idRegistroGeneralCorreccion
     *        the idRegistroGeneralCorreccion to set
     */
    public void setIdRegistroGeneralCorreccion(Long idRegistroGeneralCorreccion) {
        this.idRegistroGeneralCorreccion = idRegistroGeneralCorreccion;
    }

    /**
     * @return the idRegistroGeneralOriginal
     */
    public Long getIdRegistroGeneralOriginal() {
        return idRegistroGeneralOriginal;
    }

    /**
     * @param idRegistroGeneralOriginal
     *        the idRegistroGeneralOriginal to set
     */
    public void setIdRegistroGeneralOriginal(Long idRegistroGeneralOriginal) {
        this.idRegistroGeneralOriginal = idRegistroGeneralOriginal;
    }

    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the tipoPlanilla
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * @param tipoPlanilla
     *        the tipoPlanilla to set
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * @return the fechaProceso
     */
    public Long getFechaProceso() {
        return fechaProceso;
    }

    /**
     * @param fechaProceso
     *        the fechaProceso to set
     */
    public void setFechaProceso(Long fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    /**
     * @return the idIndicePlanilla
     */
    public Long getIdIndicePlanilla() {
        return idIndicePlanilla;
    }

    /**
     * @param idIndicePlanilla
     *        the idIndicePlanilla to set
     */
    public void setIdIndicePlanilla(Long idIndicePlanilla) {
        this.idIndicePlanilla = idIndicePlanilla;
    }

    /**
     * @return the valorTotalAportes
     */
    public BigDecimal getValorTotalAportes() {
        return valorTotalAportes;
    }

    /**
     * @param valorTotalAportes the valorTotalAportes to set
     */
    public void setValorTotalAportes(BigDecimal valorTotalAportes) {
        this.valorTotalAportes = valorTotalAportes;
    }

    /**
     * @return the digitoVerificacionAportante
     */
    public Short getDigitoVerificacionAportante() {
        return digitoVerificacionAportante;
    }

    /**
     * @param digitoVerificacionAportante the digitoVerificacionAportante to set
     */
    public void setDigitoVerificacionAportante(Short digitoVerificacionAportante) {
        this.digitoVerificacionAportante = digitoVerificacionAportante;
    }

    /** Constructor por defecto para JSON */
    public RespuestaConsultaEmpleadorDTO() {
    }
}
