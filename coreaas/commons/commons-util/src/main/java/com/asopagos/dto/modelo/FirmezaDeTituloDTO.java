package com.asopagos.dto.modelo;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO que contiene los datos de una notificación personal para cartera
 *
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 27-Febr-2018 03:23:45 p.m.
 */
@XmlRootElement
public class FirmezaDeTituloDTO {

    /**
     * Identificador de la notificación personal
     */
    private Long idPersona;

    /**
     * Identificador de cartera
     */
    private Long idCartera;

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
     * Actividad que se realiza en la notificación
     */
    private TipoActividadBitacoraEnum actividad;
    /**
     * numero de operacion de la cartera
     */
    private String numeroOperacion;
    /**
     * metodo
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * fecha de bitacora
     */
    private Long fecha;

    /**
     * resultado en bitacora
     */
    private ResultadoBitacoraCarteraEnum resultado;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public TipoActividadBitacoraEnum getActividad() {
        return actividad;
    }

    public void setActividad(TipoActividadBitacoraEnum actividad) {
        this.actividad = actividad;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }


    public ResultadoBitacoraCarteraEnum getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoBitacoraCarteraEnum resultado) {
        this.resultado = resultado;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }
}
