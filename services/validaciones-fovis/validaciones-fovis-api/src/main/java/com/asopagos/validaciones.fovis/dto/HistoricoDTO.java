/**
 * 
 */
package com.asopagos.validaciones.fovis.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;

/**
 * Contiene la información para el registro historico de ejecución de validaciones
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class HistoricoDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = -1870069461747082868L;

    /**
     * Lista de validaciones ejecutadas
     */
    private List<ValidacionDTO> resultadoValidacion;

    /**
     * Datos usados en las validaciones
     */
    private Map<String, String> datosValidacion;

    /**
     * Constructor por defecto
     */
    public HistoricoDTO() {
        super();
    }

    /**
     * @return the resultadoValidacion
     */
    public List<ValidacionDTO> getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion
     *        the resultadoValidacion to set
     */
    public void setResultadoValidacion(List<ValidacionDTO> resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the datosValidacion
     */
    public Map<String, String> getDatosValidacion() {
        return datosValidacion;
    }

    /**
     * @param datosValidacion
     *        the datosValidacion to set
     */
    public void setDatosValidacion(Map<String, String> datosValidacion) {
        this.datosValidacion = datosValidacion;
    }

}
