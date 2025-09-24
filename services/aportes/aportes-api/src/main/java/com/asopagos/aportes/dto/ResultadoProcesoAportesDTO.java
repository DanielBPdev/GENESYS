package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado del proceso de registro de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-211 y HU-212 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoProcesoAportesDTO implements Serializable {
    private static final long serialVersionUID = 3747834832666311987L;

    /** Listado de registros detallados procesados */
    private List<Long> listaRegistrosDetalladosProcesados;
    
    /** Listado de DTOs con datos de aportes a procesar actualizado */
    private List<AporteDTO> aportesActualizados;

    /**
     * @return the listaRegistrosDetalladosProcesados
     */
    public List<Long> getListaRegistrosDetalladosProcesados() {
        return listaRegistrosDetalladosProcesados;
    }

    /**
     * @param listaRegistrosDetalladosProcesados the listaRegistrosDetalladosProcesados to set
     */
    public void setListaRegistrosDetalladosProcesados(List<Long> listaRegistrosDetalladosProcesados) {
        this.listaRegistrosDetalladosProcesados = listaRegistrosDetalladosProcesados;
    }

    /**
     * @return the aportesActualizados
     */
    public List<AporteDTO> getAportesActualizados() {
        return aportesActualizados;
    }

    /**
     * @param aportesActualizados the aportesActualizados to set
     */
    public void setAportesActualizados(List<AporteDTO> aportesActualizados) {
        this.aportesActualizados = aportesActualizados;
    }

    @Override
    public String toString() {
        return "{" +
            " listaRegistrosDetalladosProcesados='" + getListaRegistrosDetalladosProcesados() + "'" +
            ", aportesActualizados='" + getAportesActualizados() + "'" +
            "}";
    }
}
