package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU 209<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class ProgramacionFiscalizacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 158251423536783638L;
    
    /**
     * Lista con las agendas de fiscalizacioón modelo DTO
     */
    private List<AgendaCarteraModeloDTO> agendaFiscalizacionModeloDTOs;
    /**
     * Lista con las actividades de fiscalizacioón modelo DTO
     */
    private List<ActividadCarteraModeloDTO> actividadFiscalizacionModeloDTOs;

    /**
     * Contructor de la clase ProgramacionFiscalizacionDTO
     */
    public ProgramacionFiscalizacionDTO() {
    }

    

    /**
     * Método que retorna el valor de agendaFiscalizacionModeloDTOs.
     * @return valor de agendaFiscalizacionModeloDTOs.
     */
    public List<AgendaCarteraModeloDTO> getAgendaFiscalizacionModeloDTOs() {
        return agendaFiscalizacionModeloDTOs;
    }

    /**
     * Método encargado de modificar el valor de agendaFiscalizacionModeloDTOs.
     * @param valor
     *        para modificar agendaFiscalizacionModeloDTOs.
     */
    public void setAgendaFiscalizacionModeloDTOs(List<AgendaCarteraModeloDTO> agendaFiscalizacionModeloDTOs) {
        this.agendaFiscalizacionModeloDTOs = agendaFiscalizacionModeloDTOs;
    }

    /**
     * Método que retorna el valor de actividadFiscalizacionModeloDTOs.
     * @return valor de actividadFiscalizacionModeloDTOs.
     */
    public List<ActividadCarteraModeloDTO> getActividadFiscalizacionModeloDTOs() {
        return actividadFiscalizacionModeloDTOs;
    }

    /**
     * Método encargado de modificar el valor de actividadFiscalizacionModeloDTOs.
     * @param valor
     *        para modificar actividadFiscalizacionModeloDTOs.
     */
    public void setActividadFiscalizacionModeloDTOs(List<ActividadCarteraModeloDTO> actividadFiscalizacionModeloDTOs) {
        this.actividadFiscalizacionModeloDTOs = actividadFiscalizacionModeloDTOs;
    }

}
