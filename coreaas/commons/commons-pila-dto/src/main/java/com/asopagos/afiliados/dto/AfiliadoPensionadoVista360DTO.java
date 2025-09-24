package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

public class AfiliadoPensionadoVista360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private EstadoAfiliadoEnum estadoPensionado;
    private String ultimaFechaIngreso;
    private CanalRecepcionEnum canal;
    private String fechaRetiro;
    private Boolean estadoServicios;
    private String ultimoAporteRecibido;
    private String fechaUltimoAporteRecibido;
    private String periodoPagado;
    private String pagadorPensiones;
    private ClasificacionEnum clasePensionado;
    private String entidadPagadoraAportes;
    private EstadoActivoInactivoEnum estadoConEntidadPagadora;
    private BigDecimal valorMesada;
    private String idAnteEntidadPagadora;
    private String numeroRadicado;
    private String idInstanciaProceso;
    private String idSolicitud;
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    private String fechaRecepcionDocumento;
    
    /**
     * 
     */
    public AfiliadoPensionadoVista360DTO() {
    }

    /**
     * @param estadoPensionado
     * @param ultimaFechaIngreso
     * @param canal
     * @param fechaRetiro
     * @param estadoServicios
     * @param ultimoAporteRecibido
     * @param fechaUltimoAporteRecibido
     * @param periodoPagado
     * @param pagadorPensiones
     * @param clasePensionado
     * @param entidadPagadoraAportes
     * @param estadoConEntidadPagadora
     * @param valorMesada
     * @param idAnteEntidadPagadora
     */
    public AfiliadoPensionadoVista360DTO(EstadoAfiliadoEnum estadoPensionado, String ultimaFechaIngreso, CanalRecepcionEnum canal,
            String fechaRetiro, Boolean estadoServicios, String ultimoAporteRecibido, String fechaUltimoAporteRecibido,
            String periodoPagado, String pagadorPensiones, ClasificacionEnum clasePensionado, String entidadPagadoraAportes,
            EstadoActivoInactivoEnum estadoConEntidadPagadora, BigDecimal valorMesada, String idAnteEntidadPagadora, String fechaRecepcionDocumento) {
        this.estadoPensionado = estadoPensionado;
        this.ultimaFechaIngreso = ultimaFechaIngreso;
        this.canal = canal;
        this.fechaRetiro = fechaRetiro;
        this.estadoServicios = estadoServicios;
        this.ultimoAporteRecibido = ultimoAporteRecibido;
        this.fechaUltimoAporteRecibido = fechaUltimoAporteRecibido;
        this.periodoPagado = periodoPagado;
        this.pagadorPensiones = pagadorPensiones;
        this.clasePensionado = clasePensionado;
        this.entidadPagadoraAportes = entidadPagadoraAportes;
        this.estadoConEntidadPagadora = estadoConEntidadPagadora;
        this.valorMesada = valorMesada;
        this.idAnteEntidadPagadora = idAnteEntidadPagadora;
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }

    /**
     * @return the estadoPensionado
     */
    public EstadoAfiliadoEnum getEstadoPensionado() {
        return estadoPensionado;
    }

    /**
     * @param estadoPensionado the estadoPensionado to set
     */
    public void setEstadoPensionado(EstadoAfiliadoEnum estadoPensionado) {
        this.estadoPensionado = estadoPensionado;
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
     * @return the pagadorPensiones
     */
    public String getPagadorPensiones() {
        return pagadorPensiones;
    }

    /**
     * @param pagadorPensiones the pagadorPensiones to set
     */
    public void setPagadorPensiones(String pagadorPensiones) {
        this.pagadorPensiones = pagadorPensiones;
    }

    /**
     * @return the clasePensionado
     */
    public ClasificacionEnum getClasePensionado() {
        return clasePensionado;
    }

    /**
     * @param clasePensionado the clasePensionado to set
     */
    public void setClasePensionado(ClasificacionEnum clasePensionado) {
        this.clasePensionado = clasePensionado;
    }

    /**
     * @return the entidadPagadoraAportes
     */
    public String getEntidadPagadoraAportes() {
        return entidadPagadoraAportes;
    }

    /**
     * @param entidadPagadoraAportes the entidadPagadoraAportes to set
     */
    public void setEntidadPagadoraAportes(String entidadPagadoraAportes) {
        this.entidadPagadoraAportes = entidadPagadoraAportes;
    }

    /**
     * @return the estadoConEntidadPagadora
     */
    public EstadoActivoInactivoEnum getEstadoConEntidadPagadora() {
        return estadoConEntidadPagadora;
    }

    /**
     * @param estadoConEntidadPagadora the estadoConEntidadPagadora to set
     */
    public void setEstadoConEntidadPagadora(EstadoActivoInactivoEnum estadoConEntidadPagadora) {
        this.estadoConEntidadPagadora = estadoConEntidadPagadora;
    }

    /**
     * @return the valorMesada
     */
    public BigDecimal getValorMesada() {
        return valorMesada;
    }

    /**
     * @param valorMesada the valorMesada to set
     */
    public void setValorMesada(BigDecimal valorMesada) {
        this.valorMesada = valorMesada;
    }

    /**
     * @return the idAnteEntidadPagadora
     */
    public String getIdAnteEntidadPagadora() {
        return idAnteEntidadPagadora;
    }

    /**
     * @param idAnteEntidadPagadora the idAnteEntidadPagadora to set
     */
    public void setIdAnteEntidadPagadora(String idAnteEntidadPagadora) {
        this.idAnteEntidadPagadora = idAnteEntidadPagadora;
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
