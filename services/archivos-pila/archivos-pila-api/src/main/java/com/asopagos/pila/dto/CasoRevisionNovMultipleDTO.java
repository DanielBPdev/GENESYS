package com.asopagos.pila.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que se emplea para la recopilación de los información para la 
 * validación de novedades en múltiples líneas
 * 
 * CONTROL DE CAMBIO 224118 - Anexo 2.1.1 <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CasoRevisionNovMultipleDTO extends CasoRevisionDTO {
    private static final long serialVersionUID = 1L;
    
    /** Listados de registros tipo 2 resumidos */
    private List<ResumenRegistro2DTO> registrosTipo2 = new ArrayList<ResumenRegistro2DTO>();

    /**
     * @return the registrosTipo2
     */
    public List<ResumenRegistro2DTO> getRegistrosTipo2() {
        return registrosTipo2;
    }
    
    /**
     * @param registro2Resumido to add
     */
    public void addRegistro2(ResumenRegistro2DTO registro2Resumido){
        registrosTipo2.add(registro2Resumido);
    }
}
