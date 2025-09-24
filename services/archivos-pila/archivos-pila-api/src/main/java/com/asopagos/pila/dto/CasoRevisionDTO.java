package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b>CONTROL DE CAMBIO 219141 y 224118 - Superclase que representa un caso de revisión de multiples 
 * registros tipo 2 para un mismo cotizante.<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CasoRevisionDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    /** Tipo de identificación del cotizante */
    private TipoIdentificacionEnum tipoIdCotizante;
    
    /** Numero de identificación del cotizante */
    private String numIdCotizante;

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the numIdCotizante
     */
    public String getNumIdCotizante() {
        return numIdCotizante;
    }

    /**
     * @param numIdCotizante the numIdCotizante to set
     */
    public void setNumIdCotizante(String numIdCotizante) {
        this.numIdCotizante = numIdCotizante;
    }

}
