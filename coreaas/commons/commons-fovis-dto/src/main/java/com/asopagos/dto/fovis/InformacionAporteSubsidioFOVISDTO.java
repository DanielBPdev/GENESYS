package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.ccf.fovis.AhorroPrevio;
import com.asopagos.entidades.ccf.fovis.RecursoComplementario;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion del subsidio FOVIS<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-053<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class InformacionAporteSubsidioFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nombre del ahorro o recurso complementario
     */
    private String nombre;

    /**
     * Nombre de la entidad donde se tiene el ahorro previo o recurso Complementario
     */
    private String entidad;

    /**
     * Fecha del ahorro previo o recurso complementario
     */
    private String fecha;

    /**
     * Valor del ahorro previo o recurso complementario
     */
    private BigDecimal valor;

    /**
     * Constructor por Defecto
     */
    public InformacionAporteSubsidioFOVISDTO() {
        super();
    }

    /**
     * Constructor de la informacion de ahorro
     * @param ahorroPrevio
     */
    public InformacionAporteSubsidioFOVISDTO(AhorroPrevio ahorroPrevio) {
        super();
        this.setNombre(ahorroPrevio.getNombreAhorro().name());
        this.setEntidad(ahorroPrevio.getEntidad());
        this.setValor(ahorroPrevio.getValor());
        Date fecha = null;
        if (ahorroPrevio.getFechaInicial() != null) {
            fecha = ahorroPrevio.getFechaInicial();
        }
        else if (ahorroPrevio.getFechaAdquisicion() != null) {
            fecha = ahorroPrevio.getFechaAdquisicion();
        }
        else if (ahorroPrevio.getFechaInmovilizacion() != null) {
            fecha = ahorroPrevio.getFechaInmovilizacion();
        }
        if (fecha != null) {
            this.setFecha(CalendarUtils.darFormatoYYYYMMDD(fecha));    
        }
    }

    /**
     * Constructor de la informacion de recurso complementario
     * @param recursoComplementario
     */
    public InformacionAporteSubsidioFOVISDTO(RecursoComplementario recursoComplementario) {
        super();
        this.setEntidad(recursoComplementario.getEntidad());
        this.setNombre(recursoComplementario.getNombre().name());
        this.setValor(recursoComplementario.getValor());
        if (recursoComplementario.getFecha() != null) {
            this.setFecha(CalendarUtils.darFormatoYYYYMMDD(recursoComplementario.getFecha()));
        }
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
     * @return the entidad
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * @param entidad
     *        the entidad to set
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *        the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor
     *        the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
