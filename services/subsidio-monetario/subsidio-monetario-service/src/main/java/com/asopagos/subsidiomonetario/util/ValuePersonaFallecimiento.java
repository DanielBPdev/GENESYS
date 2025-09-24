package com.asopagos.subsidiomonetario.util;

import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa el valor de las persona beneficiario en la liquidación por fallecimiento
 * <b>Módulo:</b> Asopagos - 317-509 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ValuePersonaFallecimiento {

    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;

    /**
     * Nombre de la apersona
     */
    private String nombre;

    /**
     * Fecha de fallecimiento
     */
    private Date fecha;

    /**
     * Constructor con los atributos requeridos para la persona fallecida
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @param nombre
     *        Nombre de la persona
     * @param fecha
     *        Fecha de fallecimiento
     */
    public ValuePersonaFallecimiento(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String nombre, Date fecha) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.fecha = fecha;
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
