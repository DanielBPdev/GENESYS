package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>Descripcion:</b> DTO que representa la salida de los identificadores de los registros persistidos en 
 * las tablas Cartera y CarteraDependiente con base en información temporal<br/>
 * <b>Módulo:</b> Asopagos - HU-169 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoPersistenciaCarteraDTO implements Serializable {
    private static final long serialVersionUID = -7063228288656113470L;

    /** Listado de Ids de Cartera generados */
    private List<Long> idsCartera;
    
    /** Listado de Ids de CarteraDependiente generados */
    private List<Long> idsCarteraDependiente;

    public List<Long> getIdsCartera() {
        return idsCartera;
    }

    public void setIdsCartera(Map<Long, Long> idsCartera) {
        this.idsCartera = new ArrayList<>(idsCartera.values());
    }

    public List<Long> getIdsCarteraDependiente() {
        return idsCarteraDependiente;
    }

    public void setIdsCarteraDependiente(Map<Long, Long> idsCarteraDependiente) {
        this.idsCarteraDependiente = new ArrayList<>(idsCarteraDependiente.values());
    }
}
