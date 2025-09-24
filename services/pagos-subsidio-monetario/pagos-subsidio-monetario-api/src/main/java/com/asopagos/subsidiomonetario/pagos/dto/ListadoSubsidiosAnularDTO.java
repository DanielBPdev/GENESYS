package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO para la comunicación con pantallas al generar el listado de subsidios a anular
 * por prescripción o por vencimiento
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class ListadoSubsidiosAnularDTO implements Serializable{

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 8476370752412801784L;
    
    /**
     * Atributo que representa la lista de subsidios a anular
     */
    private List <SubsidioMonetarioPrescribirAnularFechaDTO> subsidiosMonetariosPrescribirAnularFechaDTO;
    
    /**
     * Atributo que representa el total de subsidios a anular
     */
    private String totalRegistros;
    
    /**
     * Constructor
     */
    public ListadoSubsidiosAnularDTO() {
        
    }
    
    /**
     * Constructor 
     * @param subsidiosMonetariosPrescribirAnularFechaDTO lista de subsidios a anular
     * @param totalRegistros total de subsidios a anular
     */
    public ListadoSubsidiosAnularDTO(List<SubsidioMonetarioPrescribirAnularFechaDTO> subsidiosMonetariosPrescribirAnularFechaDTO,
            String totalRegistros) {
        super();
        this.subsidiosMonetariosPrescribirAnularFechaDTO = subsidiosMonetariosPrescribirAnularFechaDTO;
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the subsidiosMonetariosPrescribirAnularFechaDTO
     */
    public List<SubsidioMonetarioPrescribirAnularFechaDTO> getSubsidiosMonetariosPrescribirAnularFechaDTO() {
        return subsidiosMonetariosPrescribirAnularFechaDTO;
    }
    
    /**
     * @param subsidiosMonetariosPrescribirAnularFechaDTO the subsidiosMonetariosPrescribirAnularFechaDTO to set
     */
    public void setSubsidiosMonetariosPrescribirAnularFechaDTO(
            List<SubsidioMonetarioPrescribirAnularFechaDTO> subsidiosMonetariosPrescribirAnularFechaDTO) {
        this.subsidiosMonetariosPrescribirAnularFechaDTO = subsidiosMonetariosPrescribirAnularFechaDTO;
    }
    
    /**
     * @return the totalRegistros
     */
    public String getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(String totalRegistros) {
        this.totalRegistros = totalRegistros;
    }    
}
