package com.asopagos.aportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoServiciosEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos a presentar para el servicio de 
 * estado de servicios para independientes y pensionados<br/>
 * <b>Módulo:</b> Asopagos - HU-211-394 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class EstadoServiciosIndPenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación del aportante
     * */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación del aportante
     * */
    private String numeroIdentificacion;
    
    /**
     * Nombre del aportante
     * */
    private String nombreAportante;
    
    /**
     * Tipo de afiliado (valor de consulta)
     * */
    private TipoAfiliadoEnum tipoAfiliadoConsulta;
    
    /**
     * Estado de afiliación de acuerdo al tipo de afiliación consultada
     * */
    private EstadoAfiliadoEnum estadoAfiliacion;
    
    /**
     * Estado de los servicios para el aportante independiente o pensionado
     * */
    private EstadoServiciosEnum estadoServicios;
    
    /**
     * Mensaje de respuesta
     * */
    private String mensajeRespuesta;
    
    /** Constructor por defecto */
    public EstadoServiciosIndPenDTO(){}

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
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * @param nombreAportante the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * @return the tipoAfiliadoConsulta
     */
    public TipoAfiliadoEnum getTipoAfiliadoConsulta() {
        return tipoAfiliadoConsulta;
    }

    /**
     * @param tipoAfiliadoConsulta the tipoAfiliadoConsulta to set
     */
    public void setTipoAfiliadoConsulta(TipoAfiliadoEnum tipoAfiliadoConsulta) {
        this.tipoAfiliadoConsulta = tipoAfiliadoConsulta;
    }

    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the estadoServicios
     */
    public EstadoServiciosEnum getEstadoServicios() {
        return estadoServicios;
    }

    /**
     * @param estadoServicios the estadoServicios to set
     */
    public void setEstadoServicios(EstadoServiciosEnum estadoServicios) {
        this.estadoServicios = estadoServicios;
    }

    /**
     * @return the mensajeRespuesta
     */
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    /**
     * @param mensajeRespuesta the mensajeRespuesta to set
     */
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
}
