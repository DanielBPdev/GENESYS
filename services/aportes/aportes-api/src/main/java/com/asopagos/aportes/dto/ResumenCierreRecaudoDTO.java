package com.asopagos.aportes.dto;

import java.util.List;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;

/**
 * DTO que contiene la información del cuadro de resumen que se debe presentar en el proceso
 * 215 para la HU 246 en la generación del cierre, precierre
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ResumenCierreRecaudoDTO {

    private List<RegistroAporteDTO> registros;
    
    private MarcaPeriodoEnum periodo;

    /**
     * @return the registros
     */
    public List<RegistroAporteDTO> getRegistros() {
        return registros;
    }

    /**
     * @param registros the registros to set
     */
    public void setRegistros(List<RegistroAporteDTO> registros) {
        this.registros = registros;
    }

    /**
     * @return the periodo
     */
    public MarcaPeriodoEnum getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(MarcaPeriodoEnum periodo) {
        this.periodo = periodo;
    }
    
    
}
