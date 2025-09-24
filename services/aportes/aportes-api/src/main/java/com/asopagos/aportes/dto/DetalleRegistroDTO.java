package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class DetalleRegistroDTO implements Serializable {

    /**
     * Serial version 
     */
    private static final long serialVersionUID = 8694719392627043261L;

    /**
     * Resultados por registro de los aportes, devoluciones, registros legalizados, correcciones anuladas 
     * y correcciones origen de un aporte
     */
    private List<ResultadoDetalleRegistroDTO> resultadoporRegistros;
    
    /**
     * Tipo de registro para los aportes
     */
    private TipoRegistroEnum tipoRegistro;

    /**
     * @return the resultadoporRegistros
     */
    public List<ResultadoDetalleRegistroDTO> getResultadoporRegistros() {
        return resultadoporRegistros;
    }

    /**
     * @param resultadoporRegistros the resultadoporRegistros to set
     */
    public void setResultadoporRegistros(List<ResultadoDetalleRegistroDTO> resultadoporRegistros) {
        this.resultadoporRegistros = resultadoporRegistros;
    }

    /**
     * @return the tipoRegistro
     */
    public TipoRegistroEnum getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * @param tipoRegistro the tipoRegistro to set
     */
    public void setTipoRegistro(TipoRegistroEnum tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
