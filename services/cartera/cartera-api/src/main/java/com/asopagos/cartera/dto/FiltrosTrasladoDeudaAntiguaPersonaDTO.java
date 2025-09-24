package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * <b>Descripción: </b> Clase que contiene los filtros para la consulta de traspasar deuda antigua de personas ya seleccionadas.<br/>
 * 
 * @author <a href="mailto:silopez@heinsohn.com.co">Silvio Alexander López Herrera</a>
 */
public class FiltrosTrasladoDeudaAntiguaPersonaDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 3750802442930995479L;
    
    /**
     * Lista de ids personas
     */
    private List<Long> idPersonas;
    
    /**
     * Tipo solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Parámetros de consulta.
     */
    private Map<String,List<String>> params;

    /**
     * Método que retorna el valor de idPersonas.
     * @return valor de idPersonas.
     */
    public List<Long> getIdPersonas() {
        return idPersonas;
    }

    /**
     * Método encargado de modificar el valor de idPersonas.
     * @param valor para modificar idPersonas.
     */
    public void setIdPersonas(List<Long> idPersonas) {
        this.idPersonas = idPersonas;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de params.
     * @return valor de params.
     */
    public Map<String, List<String>> getParams() {
        if(params == null){
            params = new HashMap<>();
        }
        return params;
    }

    /**
     * Método encargado de modificar el valor de params.
     * @param valor para modificar params.
     */
    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }    

}
