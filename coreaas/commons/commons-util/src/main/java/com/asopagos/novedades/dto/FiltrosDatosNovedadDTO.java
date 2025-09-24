package com.asopagos.novedades.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que contiene los filtros usados en las consultas de la pestaña novedades de la vista 360<br/>
 * <b>Módulo:</b> Asopagos - HU TRA 488<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

public class FiltrosDatosNovedadDTO {

    /**
     * Tipo de identificación de la persona.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación de la persona.
     */
    private String numeroIdentificacion;
    /**
     * Indicador de si es beneficiario
     */
    private Boolean esBeneficiario;
    /**
     * Parámetros de consulta.
     */
    private Map<String, List<String>> params;

    public FiltrosDatosNovedadDTO() {
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
     * @return the esBeneficiario
     */
    public Boolean getEsBeneficiario() {
        return esBeneficiario;
    }

    /**
     * @param esBeneficiario
     *        the esBeneficiario to set
     */
    public void setEsBeneficiario(Boolean esBeneficiario) {
        this.esBeneficiario = esBeneficiario;
    }

    /**
     * @return the params
     */
    public Map<String, List<String>> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    /**
     * @param params
     *        the params to set
     */
    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }

}
