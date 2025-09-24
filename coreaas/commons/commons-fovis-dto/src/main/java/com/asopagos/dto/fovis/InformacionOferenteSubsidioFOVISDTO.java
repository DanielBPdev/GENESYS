package com.asopagos.dto.fovis;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.ProyectoSolucionVivienda;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;
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
public class InformacionOferenteSubsidioFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación de la empresa oferente
     */
    private TipoIdentificacionEnum tipoIdOferente;

    /**
     * Identificación de la empresa oferente
     */
    private String identificacionOferente;

    /**
     * Razón Social de la empresa oferente
     */
    private String razonSocial;

    /**
     * Nombre del Proyecto o Solución de vivienda
     */
    private String nombreProyecto;

    /**
     * Dirección del Proyecto o Solución de vivienda
     */
    private String direccionFisicaProyecto;

    /**
     * Dirección de la vivienda
     */
    private String direccionFisicaVivienda;

    /**
     * Constructor por defecto
     */
    public InformacionOferenteSubsidioFOVISDTO() {
        super();
    }

    public InformacionOferenteSubsidioFOVISDTO(ProyectoSolucionVivienda proyectoSolucionVivienda, Persona oferente,
            String direccionProyecto, String direccionVivienda) {
        super();
        this.setTipoIdOferente(oferente.getTipoIdentificacion());
        this.setIdentificacionOferente(oferente.getNumeroIdentificacion());
        this.setRazonSocial(PersonasUtils.obtenerNombreORazonSocial(oferente));
        this.setNombreProyecto(proyectoSolucionVivienda.getNombreProyecto());
        this.setDireccionFisicaProyecto(direccionProyecto);
        this.setDireccionFisicaVivienda(direccionVivienda);
    }

    /**
     * @return the tipoIdOferente
     */
    public TipoIdentificacionEnum getTipoIdOferente() {
        return tipoIdOferente;
    }

    /**
     * @param tipoIdOferente
     *        the tipoIdOferente to set
     */
    public void setTipoIdOferente(TipoIdentificacionEnum tipoIdOferente) {
        this.tipoIdOferente = tipoIdOferente;
    }

    /**
     * @return the identificacionOferente
     */
    public String getIdentificacionOferente() {
        return identificacionOferente;
    }

    /**
     * @param identificacionOferente
     *        the identificacionOferente to set
     */
    public void setIdentificacionOferente(String identificacionOferente) {
        this.identificacionOferente = identificacionOferente;
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
     * @return the nombreProyecto
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     * @param nombreProyecto
     *        the nombreProyecto to set
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     * @return the direccionFisicaProyecto
     */
    public String getDireccionFisicaProyecto() {
        return direccionFisicaProyecto;
    }

    /**
     * @param direccionFisicaProyecto
     *        the direccionFisicaProyecto to set
     */
    public void setDireccionFisicaProyecto(String direccionFisicaProyecto) {
        this.direccionFisicaProyecto = direccionFisicaProyecto;
    }

    /**
     * @return the direccionFisicaVivienda
     */
    public String getDireccionFisicaVivienda() {
        return direccionFisicaVivienda;
    }

    /**
     * @param direccionFisicaVivienda
     *        the direccionFisicaVivienda to set
     */
    public void setDireccionFisicaVivienda(String direccionFisicaVivienda) {
        this.direccionFisicaVivienda = direccionFisicaVivienda;
    }

}
