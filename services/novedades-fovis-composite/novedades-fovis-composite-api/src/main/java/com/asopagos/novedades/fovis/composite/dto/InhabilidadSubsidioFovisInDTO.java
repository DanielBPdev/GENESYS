/**
 * 
 */
package com.asopagos.novedades.fovis.composite.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Contiene la información de Inhabilidad subsidio FOVIS - Procesos FOVIS 3.2.5
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class InhabilidadSubsidioFovisInDTO {

    /**
     * Identificador postulacion
     */
    private Long idPostulacion;

    /**
     * Tipo de Identificación de Jefe Hogar
     */
    private TipoIdentificacionEnum tipoIdJefeHogar;

    /**
     * Número de Identificación de Jefe de Hogar
     */
    private String numeroIdJefeHogar;

    /**
     * Tipo de Identificación de Integrante Hogar
     */
    private TipoIdentificacionEnum tipoIdIntegrante;

    /**
     * Número de Identificación de Integrante Hogar
     */
    private String numeroIdIntegrante;

    /**
     * Constructor por defecto
     */
    public InhabilidadSubsidioFovisInDTO() {
        super();
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion
     *        the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the tipoIdJefeHogar
     */
    public TipoIdentificacionEnum getTipoIdJefeHogar() {
        return tipoIdJefeHogar;
    }

    /**
     * @param tipoIdJefeHogar
     *        the tipoIdJefeHogar to set
     */
    public void setTipoIdJefeHogar(TipoIdentificacionEnum tipoIdJefeHogar) {
        this.tipoIdJefeHogar = tipoIdJefeHogar;
    }

    /**
     * @return the numeroIdJefeHogar
     */
    public String getNumeroIdJefeHogar() {
        return numeroIdJefeHogar;
    }

    /**
     * @param numeroIdJefeHogar
     *        the numeroIdJefeHogar to set
     */
    public void setNumeroIdJefeHogar(String numeroIdJefeHogar) {
        this.numeroIdJefeHogar = numeroIdJefeHogar;
    }

    /**
     * @return the tipoIdIntegrante
     */
    public TipoIdentificacionEnum getTipoIdIntegrante() {
        return tipoIdIntegrante;
    }

    /**
     * @param tipoIdIntegrante
     *        the tipoIdIntegrante to set
     */
    public void setTipoIdIntegrante(TipoIdentificacionEnum tipoIdIntegrante) {
        this.tipoIdIntegrante = tipoIdIntegrante;
    }

    /**
     * @return the numeroIdIntegrante
     */
    public String getNumeroIdIntegrante() {
        return numeroIdIntegrante;
    }

    /**
     * @param numeroIdIntegrante
     *        the numeroIdIntegrante to set
     */
    public void setNumeroIdIntegrante(String numeroIdIntegrante) {
        this.numeroIdIntegrante = numeroIdIntegrante;
    }

}
