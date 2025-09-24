/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que contiene los datos que se presentan en la pestaña de novedades para el proceso 
 * asistido de planillas PILA de adición y corrección<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero</a>
 */
public class ContenidoPestanaNovedadAdicionCorreccionDTO implements Serializable {
    private static final long serialVersionUID = 4859933575992386087L;

    /**
     * Listado de las novedades vigentes encontradas en BD Core
     * */
    private List<ResumenNovedadVigenteDTO> novedadesVigentes;

    /**
     * @return the novedadesVigentes
     */
    public List<ResumenNovedadVigenteDTO> getNovedadesVigentes() {
        return novedadesVigentes;
    }

    /**
     * @param novedadesVigentes the novedadesVigentes to set
     */
    public void setNovedadesVigentes(List<ResumenNovedadVigenteDTO> novedadesVigentes) {
        this.novedadesVigentes = novedadesVigentes;
    }
}
