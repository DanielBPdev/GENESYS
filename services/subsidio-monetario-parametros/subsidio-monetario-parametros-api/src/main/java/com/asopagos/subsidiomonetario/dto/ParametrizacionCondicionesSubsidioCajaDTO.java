package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene la información de la parametrización de condiciones de subsidio<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-431<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ParametrizacionCondicionesSubsidioCajaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Código de la caja de compensación */
    private String codigoCaja;

    /** Nombre de la caja de compensación */
    private String caja;

    /** Lista de DTO´s con la información de las condiciones relacionadas a los periodos parametrizados */
    @NotNull
    @Valid
    private List<ParametrizacionCondicionesSubsidioModeloDTO> conceptos;

    /**
     * @return the codigoCaja
     */
    public String getCodigoCaja() {
        return codigoCaja;
    }

    /**
     * @param codigoCaja
     *        the codigoCaja to set
     */
    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    /**
     * @return the caja
     */
    public String getCaja() {
        return caja;
    }

    /**
     * @param caja
     *        the caja to set
     */
    public void setCaja(String caja) {
        this.caja = caja;
    }

    /**
     * @return the conceptos
     */
    public List<ParametrizacionCondicionesSubsidioModeloDTO> getConceptos() {
        return conceptos;
    }

    /**
     * @param conceptos
     *        the conceptos to set
     */
    public void setConceptos(List<ParametrizacionCondicionesSubsidioModeloDTO> conceptos) {
        this.conceptos = conceptos;
    }

}
