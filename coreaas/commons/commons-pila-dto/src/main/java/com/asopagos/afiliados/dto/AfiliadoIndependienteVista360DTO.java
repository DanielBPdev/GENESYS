package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

public class AfiliadoIndependienteVista360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private EstadoAfiliadoEnum estadoIndependiente;
    private String ultimaFechaIngreso;
    private CanalRecepcionEnum canal;
    private String fechaRetiro;
    private Boolean estadoServicios;
    private String ultimoAporteRecibido;
    private BigDecimal valorUltimoSalarioRecibido;
    private String fechaUltimoAporteRecibido;
    private String periodoPagado;
    private ClaseIndependienteEnum claseIndependiente;
    private String porcentajePagoAportes;
    private BigDecimal ingresosMensuales;
    private String numeroRadicado;
    private String idInstanciaProceso;
    private String idSolicitud;
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    private String fechaRecepcionDocumento;
    /**
     * 
     */
    public AfiliadoIndependienteVista360DTO() {
    }

    /**
     * @param estadoIndependiente
     * @param ultimaFechaIngreso
     * @param canal
     * @param fechaRetiro
     * @param estadoServicios
     * @param ultimoAporteRecibido
     * @param valorUltimoSalarioRecibido
     * @param fechaUltimoAporteRecibido
     * @param periodoPagado
     * @param claseIndependiente
     * @param porcentajePagoAportes
     * @param ingresosMensuales
     * @param motivoDesafiliacion
     */
    public AfiliadoIndependienteVista360DTO(EstadoAfiliadoEnum estadoIndependiente, String ultimaFechaIngreso, CanalRecepcionEnum canal,
            String fechaRetiro, Boolean estadoServicios, String ultimoAporteRecibido, BigDecimal valorUltimoSalarioRecibido,
            String fechaUltimoAporteRecibido, String periodoPagado, ClaseIndependienteEnum claseIndependiente, String porcentajePagoAportes,
            BigDecimal ingresosMensuales, MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion, String fechaRecepcionDocumento) {
        this.estadoIndependiente = estadoIndependiente;
        this.ultimaFechaIngreso = ultimaFechaIngreso;
        this.canal = canal;
        this.fechaRetiro = fechaRetiro;
        this.estadoServicios = estadoServicios;
        this.ultimoAporteRecibido = ultimoAporteRecibido;
        this.valorUltimoSalarioRecibido = valorUltimoSalarioRecibido;
        this.fechaUltimoAporteRecibido = fechaUltimoAporteRecibido;
        this.periodoPagado = periodoPagado;
        this.claseIndependiente = claseIndependiente;
        this.porcentajePagoAportes = porcentajePagoAportes;
        this.ingresosMensuales = ingresosMensuales;
        this.motivoDesafiliacion = motivoDesafiliacion;
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }

    /**
     * @return the estadoIndependiente
     */
    public EstadoAfiliadoEnum getEstadoIndependiente() {
        return estadoIndependiente;
    }

    /**
     * @param estadoIndependiente the estadoIndependiente to set
     */
    public void setEstadoIndependiente(EstadoAfiliadoEnum estadoIndependiente) {
        this.estadoIndependiente = estadoIndependiente;
    }

    /**
     * @return the ultimaFechaIngreso
     */
    public String getUltimaFechaIngreso() {
        return ultimaFechaIngreso;
    }

    /**
     * @param ultimaFechaIngreso the ultimaFechaIngreso to set
     */
    public void setUltimaFechaIngreso(String ultimaFechaIngreso) {
        this.ultimaFechaIngreso = ultimaFechaIngreso;
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the fechaRetiro
     */
    public String getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the estadoServicios
     */
    public Boolean getEstadoServicios() {
        return estadoServicios;
    }

    /**
     * @param estadoServicios the estadoServicios to set
     */
    public void setEstadoServicios(Boolean estadoServicios) {
        this.estadoServicios = estadoServicios;
    }

    /**
     * @return the ultimoAporteRecibido
     */
    public String getUltimoAporteRecibido() {
        return ultimoAporteRecibido;
    }

    /**
     * @param ultimoAporteRecibido the ultimoAporteRecibido to set
     */
    public void setUltimoAporteRecibido(String ultimoAporteRecibido) {
        this.ultimoAporteRecibido = ultimoAporteRecibido;
    }

    /**
     * @return the valorUltimoSalarioRecibido
     */
    public BigDecimal getValorUltimoSalarioRecibido() {
        return valorUltimoSalarioRecibido;
    }

    /**
     * @param valorUltimoSalarioRecibido the valorUltimoSalarioRecibido to set
     */
    public void setValorUltimoSalarioRecibido(BigDecimal valorUltimoSalarioRecibido) {
        this.valorUltimoSalarioRecibido = valorUltimoSalarioRecibido;
    }

    /**
     * @return the fechaUltimoAporteRecibido
     */
    public String getFechaUltimoAporteRecibido() {
        return fechaUltimoAporteRecibido;
    }

    /**
     * @param fechaUltimoAporteRecibido the fechaUltimoAporteRecibido to set
     */
    public void setFechaUltimoAporteRecibido(String fechaUltimoAporteRecibido) {
        this.fechaUltimoAporteRecibido = fechaUltimoAporteRecibido;
    }

    /**
     * @return the periodoPagado
     */
    public String getPeriodoPagado() {
        return periodoPagado;
    }

    /**
     * @param periodoPagado the periodoPagado to set
     */
    public void setPeriodoPagado(String periodoPagado) {
        this.periodoPagado = periodoPagado;
    }

    /**
     * @return the claseIndependiente
     */
    public ClaseIndependienteEnum getClaseIndependiente() {
        return claseIndependiente;
    }

    /**
     * @param claseIndependiente the claseIndependiente to set
     */
    public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
    }

    /**
     * @return the porcentajePagoAportes
     */
    public String getPorcentajePagoAportes() {
        return porcentajePagoAportes;
    }

    /**
     * @param porcentajePagoAportes the porcentajePagoAportes to set
     */
    public void setPorcentajePagoAportes(String porcentajePagoAportes) {
        this.porcentajePagoAportes = porcentajePagoAportes;
    }

    /**
     * @return the ingresosMensuales
     */
    public BigDecimal getIngresosMensuales() {
        return ingresosMensuales;
    }

    /**
     * @param ingresosMensuales the ingresosMensuales to set
     */
    public void setIngresosMensuales(BigDecimal ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the fechaRecepcionDocumento
     */
    public String getFechaRecepcionDocumento() {
        return fechaRecepcionDocumento;
    }

    /**
     * @param fechaRecepcionDocumento the fechaRecepcionDocumento to set
     */
    public void setFechaRecepcionDocumento(String fechaRecepcionDocumento) {
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }
    
    
}
