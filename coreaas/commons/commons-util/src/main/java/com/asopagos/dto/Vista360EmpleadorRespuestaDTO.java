package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripcion:</b> DTO que representa la respuesta a la busqueda realizada
 * desde vista 360 para empleador<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class Vista360EmpleadorRespuestaDTO implements Serializable {

    private static final long serialVersionUID = -5576524954836629916L;
    
    // Variables para solicitud de afiliacion, novedades y aportes
    private Long idSolicitud;
    private String numeroRadicado;
    private Date fechaRadicacion;
    private EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitudAfilEmpleador;
    private TipoTransaccionEnum tipoTransaccion;
    private EstadoSolicitudNovedadEnum estadoSolicitudNovedad;
    private EstadoSolicitudAporteEnum estadoSolicitudAporte;
    
    // Variables comunicado
    private String nombreComunicado;
    private Date fechaComunicado;
    private String emailDestinatario;
    private EstadoEnvioComunicadoEnum estadoEnvioComunicado;
    
    /** Constructor por defecto */
    public Vista360EmpleadorRespuestaDTO(){}
    
    /**
     * Constructor consulta nativa para afiliacion empleador
     * @param idSolicitud
     * @param numeroRadicado
     * @param fechaRadicacion
     * @param estadoSolicitudAfilEmpleador
     * @param tipoTransaccion
     */
    public Vista360EmpleadorRespuestaDTO(Long idSolicitud, String numeroRadicado, Date fechaRadicacion,
            EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitudAfilEmpleador, TipoTransaccionEnum tipoTransaccion) {
        this.idSolicitud = idSolicitud;
        this.numeroRadicado = numeroRadicado;
        this.fechaRadicacion = fechaRadicacion;
        this.estadoSolicitudAfilEmpleador = estadoSolicitudAfilEmpleador;
        this.tipoTransaccion = tipoTransaccion;
    }
    
    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }
    /**
     * @param fechaRadicacion the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
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
     * @return the nombreComunicado
     */
    public String getNombreComunicado() {
        return nombreComunicado;
    }

    /**
     * @param nombreComunicado the nombreComunicado to set
     */
    public void setNombreComunicado(String nombreComunicado) {
        this.nombreComunicado = nombreComunicado;
    }

    /**
     * @return the fechaComunicado
     */
    public Date getFechaComunicado() {
        return fechaComunicado;
    }

    /**
     * @param fechaComunicado the fechaComunicado to set
     */
    public void setFechaComunicado(Date fechaComunicado) {
        this.fechaComunicado = fechaComunicado;
    }

    /**
     * @return the emailDestinatario
     */
    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    /**
     * @param emailDestinatario the emailDestinatario to set
     */
    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    /**
     * @return the estadoEnvioComunicado
     */
    public EstadoEnvioComunicadoEnum getEstadoEnvioComunicado() {
        return estadoEnvioComunicado;
    }

    /**
     * @param estadoEnvioComunicado the estadoEnvioComunicado to set
     */
    public void setEstadoEnvioComunicado(EstadoEnvioComunicadoEnum estadoEnvioComunicado) {
        this.estadoEnvioComunicado = estadoEnvioComunicado;
    }
    
    
}
