package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.ActividadNotificacionCicloCarteraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de una notificación personal para cartera
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 27-Febr-2018 03:23:45 p.m.
 */
@XmlRootElement
public class NotificacionPersonalModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -4098226764046639267L;

    /**
     * Actividad que se realiza en la notificación
     */
    private ActividadNotificacionCicloCarteraEnum actividad;

    /**
     * Comentario perteneciente a la notificación
     */
    private String comentario;

    /**
     * Listado de documentos de soporte modelo
     */
    private List<DocumentoSoporteModeloDTO> lstDocumentosSoporteModelo;

    /**
     * Tipo de Identificación Persona.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de Identificación Persona.
     */
    private String numeroIdentificacion;
    
    /**
     * Identificador de la notificación personal
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Identificador de la notificación personal
     */
    private Long idPersona;
    
    /**
     * Identificador de cartera
     */
    private Long idCartera;

    /**
     * Método que retorna el valor de comentario.
     * @return valor de comentario.
     */
    public String getComentario() {
        return comentario;
    }



    /**
     * Método que retorna el valor de lstDocumentosSoporteModelo.
     * @return valor de lstDocumentosSoporteModelo.
     */
    public List<DocumentoSoporteModeloDTO> getLstDocumentosSoporteModelo() {
        return lstDocumentosSoporteModelo;
    }

    /**
     * Método encargado de modificar el valor de comentario.
     * @param valor
     *        para modificar comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Método encargado de modificar el valor de lstDocumentosSoporteModelo.
     * @param valor
     *        para modificar lstDocumentosSoporteModelo.
     */
    public void setLstDocumentosSoporteModelo(List<DocumentoSoporteModeloDTO> lstDocumentosSoporteModelo) {
        this.lstDocumentosSoporteModelo = lstDocumentosSoporteModelo;
    }

    /**
     * @return the actividad
     */
    public ActividadNotificacionCicloCarteraEnum getActividad() {
        return actividad;
    }

    /**
     * @param actividad
     *        the actividad to set
     */
    public void setActividad(ActividadNotificacionCicloCarteraEnum actividad) {
        this.actividad = actividad;
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
     * @return the tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de idCartera.
     * @return valor de idCartera.
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * Método encargado de modificar el valor de idCartera.
     * @param valor para modificar idCartera.
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }
    
}