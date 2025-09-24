package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripcion:</b> DTO que representa el resultado de la consulta de novedades existentes en Core<br/>
 * <b>Módulo:</b> Asopagos - HU-211 y HU-212 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoConsultaNovedadesExistentesDTO implements Serializable {
    private static final long serialVersionUID = -7158723079621444988L;
    
    /** Mapa de resultados */
    private Map<Long, Set<TipoTransaccionEnum>> novedadesRegistro;

    /**
     * @return the novedadesRegistro
     */
    public Map<Long, Set<TipoTransaccionEnum>> getNovedadesRegistro() {
        return novedadesRegistro;
    }

    /**
     * @param novedadesRegistro the novedadesRegistro to set
     */
    public void setNovedadesRegistro(Map<Long, Set<TipoTransaccionEnum>> novedadesRegistro) {
        this.novedadesRegistro = novedadesRegistro;
    }
}
