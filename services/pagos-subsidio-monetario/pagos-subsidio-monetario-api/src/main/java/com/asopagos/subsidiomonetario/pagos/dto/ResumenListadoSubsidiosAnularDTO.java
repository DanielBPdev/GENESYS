package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO para la comunicación con pantallas al generar el resumen del listado de subsidios a anular
 * por prescripción o por vencimiento
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class ResumenListadoSubsidiosAnularDTO implements Serializable{
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -374315509352578291L;
    
    /**
     * Atributo que representa la lista de subsidios a anular agrupados
     */
    private List <SubsidioAnularDTO> subsidiosAnularDTO;
    
    /**
     * Atributo que representa el total de subsidios a anular agrupados
     */
    private String totalRegistros;
    
    /**
     * Constructor
     */
    public ResumenListadoSubsidiosAnularDTO() {
        
    }
    
    /**
     * Constructor
     * @param subsidiosAnularDTO
     * @param totalRegistros
     */
    public ResumenListadoSubsidiosAnularDTO(List<SubsidioAnularDTO> subsidiosAnularDTO, String totalRegistros) {
        this.subsidiosAnularDTO = subsidiosAnularDTO;
        this.totalRegistros = totalRegistros;
    }
   
    /**
     * @return the subsidiosAnularDTO
     */
    public List<SubsidioAnularDTO> getSubsidiosAnularDTO() {
        return subsidiosAnularDTO;
    }
    /**
     * @param subsidiosAnularDTO the subsidiosAnularDTO to set
     */
    public void setSubsidiosAnularDTO(List<SubsidioAnularDTO> subsidiosAnularDTO) {
        this.subsidiosAnularDTO = subsidiosAnularDTO;
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
