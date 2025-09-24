package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.RazonRechazoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información relacionada al rechazo o aprobación de un subsidio<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-438<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class AprobacionRechazoSubsidioMonetarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Razon de rechazo para la solicitud de liquidación */

    private RazonRechazoEnum razonRechazo;

    /** Observaciones de rechazo o aprobación sobre la solicitud de liquidación */
    private String observaciones;

    /**
     * @return the razonRechazo
     */
    public RazonRechazoEnum getRazonRechazo() {
        return razonRechazo;
    }

    /**
     * @param razonRechazo
     *        the razonRechazo to set
     */
    public void setRazonRechazo(RazonRechazoEnum razonRechazo) {
        this.razonRechazo = razonRechazo;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
