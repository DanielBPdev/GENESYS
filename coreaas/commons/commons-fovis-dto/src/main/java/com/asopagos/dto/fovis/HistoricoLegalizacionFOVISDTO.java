package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta
 * de las solicitudes de legalizacion para la vista 360<b>TR</b>
 * 
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa Salamanca</a>
 */
@XmlRootElement
public class HistoricoLegalizacionFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String numeroRadicacion;

    private Date fechaRadicacion;

    private FormaPagoEnum formaPago;

    private BigDecimal valorADesembolsar;

    private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;

    private Date fechaOperacion;

    private Long idSolicitudLegalizacionDesembolso;

    private Long idPostulacionFOVIS;

    private SolicitudPostulacionFOVISDTO datosPostulacionFovis;

    private Long idLegalizacionDesembolso;

    private TipoMedioDePagoEnum tipoMedioPago;

    private Date fechaLimitePago;

    private Boolean subsidioDesembolsado;

    private TipoTransaccionEnum tipoTransaccion;

    private ResultadoProcesoEnum resultadoProceso;

    private Long idVisita;

    private Boolean incluyeCertificadoExistenciaHabitabilidad;

    private Boolean noCumplioCondicionesHabitablidad;

    private Boolean registroPNCNoResuelto;

    private String numeroResolucionAsignacion;

    private Date fechaResolucionAsignacion;
    
    private ClasificacionEnum clasificacion;

    private Long idSolicitudGlobal;

    /**
     * 
     */
    public HistoricoLegalizacionFOVISDTO() {
        super();
    }

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
     * @return the formaPago
     */
    public FormaPagoEnum getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago
     *        the formaPago to set
     */
    public void setFormaPago(FormaPagoEnum formaPago) {
        this.formaPago = formaPago;
    }

    /**
     * @return the valorADesembolsar
     */
    public BigDecimal getValorADesembolsar() {
        return valorADesembolsar;
    }

    /**
     * @param valorADesembolsar
     *        the valorADesembolsar to set
     */
    public void setValorADesembolsar(BigDecimal valorADesembolsar) {
        this.valorADesembolsar = valorADesembolsar;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the fechaOperacion
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion
     *        the fechaOperacion to set
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the idSolicitudLegalizacionDesembolso
     */
    public Long getIdSolicitudLegalizacionDesembolso() {
        return idSolicitudLegalizacionDesembolso;
    }

    /**
     * @param idSolicitudLegalizacionDesembolso
     *        the idSolicitudLegalizacionDesembolso to set
     */
    public void setIdSolicitudLegalizacionDesembolso(Long idSolicitudLegalizacionDesembolso) {
        this.idSolicitudLegalizacionDesembolso = idSolicitudLegalizacionDesembolso;
    }

    /**
     * @return the idPostulacionFOVIS
     */
    public Long getIdPostulacionFOVIS() {
        return idPostulacionFOVIS;
    }

    /**
     * @param idPostulacionFOVIS
     *        the idPostulacionFOVIS to set
     */
    public void setIdPostulacionFOVIS(Long idPostulacionFOVIS) {
        this.idPostulacionFOVIS = idPostulacionFOVIS;
    }

    /**
     * @return the datosPostulacionFovis
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacionFovis() {
        return datosPostulacionFovis;
    }

    /**
     * @param datosPostulacionFovis
     *        the datosPostulacionFovis to set
     */
    public void setDatosPostulacionFovis(SolicitudPostulacionFOVISDTO datosPostulacionFovis) {
        this.datosPostulacionFovis = datosPostulacionFovis;
    }

    /**
     * @return the idLegalizacionDesembolso
     */
    public Long getIdLegalizacionDesembolso() {
        return idLegalizacionDesembolso;
    }

    /**
     * @param idLegalizacionDesembolso
     *        the idLegalizacionDesembolso to set
     */
    public void setIdLegalizacionDesembolso(Long idLegalizacionDesembolso) {
        this.idLegalizacionDesembolso = idLegalizacionDesembolso;
    }

    /**
     * @return the tipoMedioPago
     */
    public TipoMedioDePagoEnum getTipoMedioPago() {
        return tipoMedioPago;
    }

    /**
     * @param tipoMedioPago
     *        the tipoMedioPago to set
     */
    public void setTipoMedioPago(TipoMedioDePagoEnum tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    /**
     * @return the fechaLimitePago
     */
    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    /**
     * @param fechaLimitePago
     *        the fechaLimitePago to set
     */
    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    /**
     * @return the subsidioDesembolsado
     */
    public Boolean getSubsidioDesembolsado() {
        return subsidioDesembolsado;
    }

    /**
     * @param subsidioDesembolsado
     *        the subsidioDesembolsado to set
     */
    public void setSubsidioDesembolsado(Boolean subsidioDesembolsado) {
        this.subsidioDesembolsado = subsidioDesembolsado;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the resultadoProceso
     */
    public ResultadoProcesoEnum getResultadoProceso() {
        return resultadoProceso;
    }

    /**
     * @param resultadoProceso
     *        the resultadoProceso to set
     */
    public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

    /**
     * @return the idVisita
     */
    public Long getIdVisita() {
        return idVisita;
    }

    /**
     * @param idVisita
     *        the idVisita to set
     */
    public void setIdVisita(Long idVisita) {
        this.idVisita = idVisita;
    }

    /**
     * @return the incluyeCertificadoExistenciaHabitabilidad
     */
    public Boolean getIncluyeCertificadoExistenciaHabitabilidad() {
        return incluyeCertificadoExistenciaHabitabilidad;
    }

    /**
     * @param incluyeCertificadoExistenciaHabitabilidad
     *        the incluyeCertificadoExistenciaHabitabilidad to set
     */
    public void setIncluyeCertificadoExistenciaHabitabilidad(Boolean incluyeCertificadoExistenciaHabitabilidad) {
        this.incluyeCertificadoExistenciaHabitabilidad = incluyeCertificadoExistenciaHabitabilidad;
    }

    /**
     * @return the cumplioCondicionesHabitablidad
     */
    public Boolean getNoCumplioCondicionesHabitablidad() {
        return noCumplioCondicionesHabitablidad;
    }

    /**
     * @param cumplioCondicionesHabitablidad
     *        the cumplioCondicionesHabitablidad to set
     */
    public void setNoCumplioCondicionesHabitablidad(Boolean noCumplioCondicionesHabitablidad) {
        this.noCumplioCondicionesHabitablidad = noCumplioCondicionesHabitablidad;
    }

    /**
     * @return the registroPNCNoResuelto
     */
    public Boolean getRegistroPNCNoResuelto() {
        return registroPNCNoResuelto;
    }

    /**
     * @param registroPNCNoResuelto
     *        the registroPNCNoResuelto to set
     */
    public void setRegistroPNCNoResuelto(Boolean registroPNCNoResuelto) {
        this.registroPNCNoResuelto = registroPNCNoResuelto;
    }

    /**
     * @return the numeroResolucionAsignacion
     */
    public String getNumeroResolucionAsignacion() {
        return numeroResolucionAsignacion;
    }

    /**
     * @param numeroResolucionAsignacion
     *        the numeroResolucionAsignacion to set
     */
    public void setNumeroResolucionAsignacion(String numeroResolucionAsignacion) {
        this.numeroResolucionAsignacion = numeroResolucionAsignacion;
    }

    /**
     * @return the fechaResolucionAsignacion
     */
    public Date getFechaResolucionAsignacion() {
        return fechaResolucionAsignacion;
    }

    /**
     * @param fechaResolucionAsignacion
     *        the fechaResolucionAsignacion to set
     */
    public void setFechaResolucionAsignacion(Date fechaResolucionAsignacion) {
        this.fechaResolucionAsignacion = fechaResolucionAsignacion;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

}
