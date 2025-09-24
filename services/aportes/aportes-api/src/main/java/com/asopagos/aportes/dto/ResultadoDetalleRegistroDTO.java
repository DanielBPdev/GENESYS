package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class ResultadoDetalleRegistroDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = -4365540063098691740L;
    /**
     * Aportante que aplica para cierre de recaudo de aportes
     */
    private DetalleRegistroAportanteDTO aportante;
    /**
     * Cotizantes que aplica para cierre de recaudo de aportes y se encuentran relacionados al aportante
     */
    private List<DetalleRegistroCotizanteDTO> cotizantes;

    /**
     * @return the aportante
     */
    public DetalleRegistroAportanteDTO getAportante() {
        return aportante;
    }

    /**
     * @param aportante
     *        the aportante to set
     */
    public void setAportante(DetalleRegistroAportanteDTO aportante) {
        this.aportante = aportante;
    }

    /**
     * @return the cotizantes
     */
    public List<DetalleRegistroCotizanteDTO> getCotizantes() {
        return cotizantes;
    }

    /**
     * @param cotizantes
     *        the cotizantes to set
     */
    public void setCotizantes(List<DetalleRegistroCotizanteDTO> cotizantes) {
        this.cotizantes = cotizantes;
    }
}
