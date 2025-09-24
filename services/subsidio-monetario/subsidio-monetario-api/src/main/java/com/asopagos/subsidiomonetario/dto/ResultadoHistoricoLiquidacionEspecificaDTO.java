package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información de resultado para una consulta de una liquidación histórica de tipo especifica<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoHistoricoLiquidacionEspecificaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de identificación del afiliado */
    private TipoIdentificacionEnum tipoIdentificacion;

    /** Número de identificación del afiliado */
    private Long numeroIdentificacion;

    /** Nombre del afiliado */
    private String nombre;

    /** Número que identifica la operación de liquidación */
    private String numeroOperacion;

    /** Fecha en la cual se generó el proceso de dispersion para la liquidación */
    private Date fecha;

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
    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion
     *        the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *        the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
