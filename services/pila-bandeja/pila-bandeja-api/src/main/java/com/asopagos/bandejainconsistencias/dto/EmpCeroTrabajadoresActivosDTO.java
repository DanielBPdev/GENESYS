package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * 
 * <b>Descripcion:</b> DTO que recibe el registro de la consulta para la 404 <br/>
 * <b>Módulo:</b> Asopagos - HU 401<br/>
 *
 * @author <a href="mailto:roarboleda@heinsohn.com.co"> roarboleda</a>
 */
public class EmpCeroTrabajadoresActivosDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String razonSocial;
    private Short digitoVerificacion;
    private Long idEmpleador;
    private Date fechaCambioEstadoAfiliacion;
    private EstadoCarteraEnum estadoAportesEmpleador;
    private Short cantIngresoBandejaCeroTrabajadores;
    private Date fechaRetiroTotalTrabajadores;
    private Date fechaGestionDesafiliacion;
    private Date fechaUltimorecaudo;
    private String peridoUltimoRecaudo;
    private Long historicoAportes;
    private Long historicoAfiliaciones;
    private Long cantidadActivos;
    private Long cantidadRolafiliados;
    private Boolean tieneTodosLosTrabajadoresRetirados;
    
    /**
     * Metodo constructor para el DTO
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param razonSocial
     * @param digitoVerificacion
     * @param fechaCambioEstadoAfiliacion
     * @param estadoAportesEmpleador
     * @param cantIngresoBandejaCeroTrabajadores
     * @param fechaRetiroTotalTrabajadores
     * @param fechaGestionDesafiliacion
     * @param fechaUltimorecaudo
     * @param peridoUltimoRecaudo
     * @param historicoAportes
     * @param historicoAfiliaciones
     * @param cantidadActivos
     * @param cantidadRolafiliados
     */
    public EmpCeroTrabajadoresActivosDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
            Short digitoVerificacion, Date fechaCambioEstadoAfiliacion, Long idEmpleador,  
            Short cantIngresoBandejaCeroTrabajadores, Date fechaRetiroTotalTrabajadores, Date fechaGestionDesafiliacion,
            Date fechaUltimorecaudo, String peridoUltimoRecaudo, Long historicoAportes, Long historicoAfiliaciones, Long cantidadActivos,
            Long cantidadRolafiliados, String estadoAportesEmpleador) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.razonSocial = razonSocial;
        this.digitoVerificacion = digitoVerificacion;
        this.fechaCambioEstadoAfiliacion = fechaCambioEstadoAfiliacion;
        this.idEmpleador = idEmpleador;
        this.cantIngresoBandejaCeroTrabajadores = cantIngresoBandejaCeroTrabajadores;
        this.fechaRetiroTotalTrabajadores = fechaRetiroTotalTrabajadores;
        this.fechaGestionDesafiliacion = fechaGestionDesafiliacion;
        this.fechaUltimorecaudo = fechaUltimorecaudo;
        this.peridoUltimoRecaudo = peridoUltimoRecaudo;
        this.historicoAportes = historicoAportes;
        this.historicoAfiliaciones = historicoAfiliaciones;
        this.cantidadActivos = cantidadActivos;
        this.cantidadRolafiliados = cantidadRolafiliados;
        this.estadoAportesEmpleador = EstadoCarteraEnum.valueOf(estadoAportesEmpleador);
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
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *        the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @param digitoVerificacion
     *        the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the fechaCambioEstadoAfiliacion
     */
    public Date getFechaCambioEstadoAfiliacion() {
        return fechaCambioEstadoAfiliacion;
    }

    /**
     * @param fechaCambioEstadoAfiliacion
     *        the fechaCambioEstadoAfiliacion to set
     */
    public void setFechaCambioEstadoAfiliacion(Date fechaCambioEstadoAfiliacion) {
        this.fechaCambioEstadoAfiliacion = fechaCambioEstadoAfiliacion;
    }

    /**
     * @return the cantIngresoBandejaCeroTrabajadores
     */
    public Short getCantIngresoBandejaCeroTrabajadores() {
        return cantIngresoBandejaCeroTrabajadores;
    }

    /**
     * @param cantIngresoBandejaCeroTrabajadores
     *        the cantIngresoBandejaCeroTrabajadores to set
     */
    public void setCantIngresoBandejaCeroTrabajadores(Short cantIngresoBandejaCeroTrabajadores) {
        this.cantIngresoBandejaCeroTrabajadores = cantIngresoBandejaCeroTrabajadores;
    }

    /**
     * @return the fechaRetiroTotalTrabajadores
     */
    public Date getFechaRetiroTotalTrabajadores() {
        return fechaRetiroTotalTrabajadores;
    }

    /**
     * @param fechaRetiroTotalTrabajadores
     *        the fechaRetiroTotalTrabajadores to set
     */
    public void setFechaRetiroTotalTrabajadores(Date fechaRetiroTotalTrabajadores) {
        this.fechaRetiroTotalTrabajadores = fechaRetiroTotalTrabajadores;
    }

    /**
     * @return the fechaGestionDesafiliacion
     */
    public Date getFechaGestionDesafiliacion() {
        return fechaGestionDesafiliacion;
    }

    /**
     * @param fechaGestionDesafiliacion
     *        the fechaGestionDesafiliacion to set
     */
    public void setFechaGestionDesafiliacion(Date fechaGestionDesafiliacion) {
        this.fechaGestionDesafiliacion = fechaGestionDesafiliacion;
    }

    /**
     * @return the fechaUltimorecaudo
     */
    public Date getFechaUltimorecaudo() {
        return fechaUltimorecaudo;
    }

    /**
     * @param fechaUltimorecaudo
     *        the fechaUltimorecaudo to set
     */
    public void setFechaUltimorecaudo(Date fechaUltimorecaudo) {
        this.fechaUltimorecaudo = fechaUltimorecaudo;
    }

    /**
     * @return the peridoUltimoRecaudo
     */
    public String getPeridoUltimoRecaudo() {
        return peridoUltimoRecaudo;
    }

    /**
     * @param peridoUltimoRecaudo
     *        the peridoUltimoRecaudo to set
     */
    public void setPeridoUltimoRecaudo(String peridoUltimoRecaudo) {
        this.peridoUltimoRecaudo = peridoUltimoRecaudo;
    }

    /**
     * @return the historicoAportes
     */
    public Long getHistoricoAportes() {
        return historicoAportes;
    }

    /**
     * @param historicoAportes
     *        the historicoAportes to set
     */
    public void setHistoricoAportes(Long historicoAportes) {
        this.historicoAportes = historicoAportes;
    }

    /**
     * @return the historicoAfiliaciones
     */
    public Long getHistoricoAfiliaciones() {
        return historicoAfiliaciones;
    }

    /**
     * @param historicoAfiliaciones
     *        the historicoAfiliaciones to set
     */
    public void setHistoricoAfiliaciones(Long historicoAfiliaciones) {
        this.historicoAfiliaciones = historicoAfiliaciones;
    }

    /**
     * @return the cantidadActivos
     */
    public Long getCantidadActivos() {
        return cantidadActivos;
    }

    /**
     * @param cantidadActivos
     *        the cantidadActivos to set
     */
    public void setCantidadActivos(Long cantidadActivos) {
        this.cantidadActivos = cantidadActivos;
    }

    /**
     * @return the cantidadRolafiliados
     */
    public Long getCantidadRolafiliados() {
        return cantidadRolafiliados;
    }

    /**
     * @param cantidadRolafiliados
     *        the cantidadRolafiliados to set
     */
    public void setCantidadRolafiliados(Long cantidadRolafiliados) {
        this.cantidadRolafiliados = cantidadRolafiliados;
    }

    /**
     * @return the tieneTodosLosTrabajadoresRetirados
     */
    public Boolean getTieneTodosLosTrabajadoresRetirados() {
        return tieneTodosLosTrabajadoresRetirados;
    }

    /**
     * @param tieneTodosLosTrabajadoresRetirados
     *        the tieneTodosLosTrabajadoresRetirados to set
     */
    public void setTieneTodosLosTrabajadoresRetirados(Boolean tieneTodosLosTrabajadoresRetirados) {
        this.tieneTodosLosTrabajadoresRetirados = tieneTodosLosTrabajadoresRetirados;
    }
    
    public EmpCeroTrabajadoresActivosDTO() {}

    /**
     * @return the estadoAportesEmpleador
     */
    public EstadoCarteraEnum getEstadoAportesEmpleador() {
        return estadoAportesEmpleador;
    }

    /**
     * @param estadoAportesEmpleador the estadoAportesEmpleador to set
     */
    public void setEstadoAportesEmpleador(EstadoCarteraEnum estadoAportesEmpleador) {
        this.estadoAportesEmpleador = estadoAportesEmpleador;
    }
}
