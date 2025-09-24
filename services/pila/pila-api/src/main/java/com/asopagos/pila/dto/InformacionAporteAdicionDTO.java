package com.asopagos.pila.dto;

import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.TemAporteModeloDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU 211-410<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class InformacionAporteAdicionDTO {

    private RegistroDetalladoModeloDTO infoRegistroAporte;
    private TemAporteModeloDTO simulacionRegistroAporte;

    /**
     * @return the infoRegistroAporte
     */
    public RegistroDetalladoModeloDTO getInfoRegistroAporte() {
        return infoRegistroAporte;
    }

    /**
     * @param infoRegistroAporte
     *        the infoRegistroAporte to set
     */
    public void setInfoRegistroAporte(RegistroDetalladoModeloDTO infoRegistroAporte) {
        this.infoRegistroAporte = infoRegistroAporte;
    }

    /**
     * @return the simulacionRegistroAporte
     */
    public TemAporteModeloDTO getSimulacionRegistroAporte() {
        return simulacionRegistroAporte;
    }

    /**
     * @param simulacionRegistroAporte
     *        the simulacionRegistroAporte to set
     */
    public void setSimulacionRegistroAporte(TemAporteModeloDTO simulacionRegistroAporte) {
        this.simulacionRegistroAporte = simulacionRegistroAporte;
    }

}
