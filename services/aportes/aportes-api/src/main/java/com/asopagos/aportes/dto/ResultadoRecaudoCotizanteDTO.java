package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoRegistroRecaudoEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class ResultadoRecaudoCotizanteDTO implements Serializable{

    /**
     * Serial version 
     */
    private static final long serialVersionUID = 3642900147057700987L;
    
    /**
     * Lista que contiene los registros resultado de recaudos de los cotizantes
     */
    private List<RecaudoCotizanteDTO> recaudosCotizante;
    
    /**
     * Tipo de registro que representa que valores son contenidos en la lista de recaudos cotizante
     */
    private TipoRegistroRecaudoEnum tipoRegistro;
    
    /**
     * 
     */
    public ResultadoRecaudoCotizanteDTO() {
    }

    /**
     * @return the recaudosCotizante
     */
    public List<RecaudoCotizanteDTO> getRecaudosCotizante() {
        return recaudosCotizante;
    }

    /**
     * @param recaudosCotizante the recaudosCotizante to set
     */
    public void setRecaudosCotizante(List<RecaudoCotizanteDTO> recaudosCotizante) {
        this.recaudosCotizante = recaudosCotizante;
    }

    /**
     * @return the tipoRegistro
     */
    public TipoRegistroRecaudoEnum getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * @param tipoRegistro the tipoRegistro to set
     */
    public void setTipoRegistro(TipoRegistroRecaudoEnum tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
