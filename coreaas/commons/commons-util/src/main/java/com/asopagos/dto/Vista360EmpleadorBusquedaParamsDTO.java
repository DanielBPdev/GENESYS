package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que recibe los parametros de busqueda de la pantalla vista 360 <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class Vista360EmpleadorBusquedaParamsDTO implements Serializable {

    private static final long serialVersionUID = -2473359595917447929L;
    
    private String numeroRadicado;
    private Long fechaRadicado;
    private EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitudAfilEmpleador;
    private TipoTransaccionEnum tipoTransaccion;
    private EstadoSolicitudNovedadEnum estadoSolicitudNovedad;
    private EstadoSolicitudAporteEnum estadoSolicitudAporte;
    private Long fechaInicioRadicado;
    private Long fechaFinRadicado;
    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Esta variable potencialmente recoge cualquier valor de enumeracion para retornarla como String
     */
    private String estadoTransaccion;
    
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
     * @return the fechaRadicado
     */
    public Long getFechaRadicado() {
        return fechaRadicado;
    }
    /**
     * @param fechaRadicado the fechaRadicado to set
     */
    public void setFechaRadicado(Long fechaRadicado) {
        this.fechaRadicado = fechaRadicado;
    }
    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }
    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    /**
     * @return the fechaInicioRadicado
     */
    public Long getFechaInicioRadicado() {
        return fechaInicioRadicado;
    }
    /**
     * @param fechaInicioRadicado the fechaInicioRadicado to set
     */
    public void setFechaInicioRadicado(Long fechaInicioRadicado) {
        this.fechaInicioRadicado = fechaInicioRadicado;
    }
    /**
     * @return the fechaFinRadicado
     */
    public Long getFechaFinRadicado() {
        return fechaFinRadicado;
    }
    /**
     * @param fechaFinRadicado the fechaFinRadicado to set
     */
    public void setFechaFinRadicado(Long fechaFinRadicado) {
        this.fechaFinRadicado = fechaFinRadicado;
    }
    /**
     * @return the estadoSolicitudAfilEmpleador
     */
    public EstadoSolicitudAfiliacionEmpleadorEnum getEstadoSolicitudAfilEmpleador() {
        return estadoSolicitudAfilEmpleador;
    }
    /**
     * @param estadoSolicitudAfilEmpleador the estadoSolicitudAfilEmpleador to set
     */
    public void setEstadoSolicitudAfilEmpleador(EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitudAfilEmpleador) {
        this.estadoSolicitudAfilEmpleador = estadoSolicitudAfilEmpleador;
    }
    /**
     * @return the estadoSolicitudNovedad
     */
    public EstadoSolicitudNovedadEnum getEstadoSolicitudNovedad() {
        return estadoSolicitudNovedad;
    }
    /**
     * @param estadoSolicitudNovedad the estadoSolicitudNovedad to set
     */
    public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum estadoSolicitudNovedad) {
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
    }
    /**
     * @return the estadoSolicitudAporte
     */
    public EstadoSolicitudAporteEnum getEstadoSolicitudAporte() {
        return estadoSolicitudAporte;
    }
    /**
     * @param estadoSolicitudAporte the estadoSolicitudAporte to set
     */
    public void setEstadoSolicitudAporte(EstadoSolicitudAporteEnum estadoSolicitudAporte) {
        this.estadoSolicitudAporte = estadoSolicitudAporte;
    }
    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    /**
     * @return the estadoTransaccion
     */
    public String getEstadoTransaccion() {
        return estadoTransaccion;
    }
    /**
     * @param estadoTransaccion the estadoTransaccion to set
     */
    public void setEstadoTransaccion(String estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }
    
    
    
}
