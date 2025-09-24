package com.asopagos.cartera.composite.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.cartera.FiscalizacionAportanteDTO;
import com.asopagos.dto.modelo.CicloCarteraModeloDTO;

/**
 * DTO que contiene los datos de la fiscalizacion relacionada a un aportante.
 * @author Claudia Milena Marín <clmarin@heinsohn.com.co>
 * @created 29-sept.-2017 3:37:37 p.m.
 */
public class CicloCarteraAportanteDTO implements Serializable {

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = -2822968556810200988L;

    /**
     * Ciclo de fiscalización.
     */
    private CicloCarteraModeloDTO cicloFiscalizacion;

    /**
     * Aportantes asociados al ciclo.
     */
    private List<FiscalizacionAportanteDTO> aportantes;

    /**
     * Método que retorna el valor de cicloFiscalizacion.
     * @return valor de cicloFiscalizacion.
     */
    public CicloCarteraModeloDTO getCicloFiscalizacion() {
        return cicloFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de cicloFiscalizacion.
     * @param valor
     *        para modificar cicloFiscalizacion.
     */
    public void setCicloFiscalizacion(CicloCarteraModeloDTO cicloFiscalizacion) {
        this.cicloFiscalizacion = cicloFiscalizacion;
    }

    /**
     * Método que retorna el valor de aportantes.
     * @return valor de aportantes.
     */
    public List<FiscalizacionAportanteDTO> getAportantes() {
        return aportantes;
    }

    /**
     * Método encargado de modificar el valor de aportantes.
     * @param valor
     *        para modificar aportantes.
     */
    public void setAportantes(List<FiscalizacionAportanteDTO> aportantes) {
        this.aportantes = aportantes;
    }

}