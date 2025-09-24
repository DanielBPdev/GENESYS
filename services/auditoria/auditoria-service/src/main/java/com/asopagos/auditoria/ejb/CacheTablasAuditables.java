package com.asopagos.auditoria.ejb;

import java.util.Map;
import java.util.TreeMap;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class CacheTablasAuditables {

    private Map<String, String> listaTablasAuditables = new TreeMap<>();
    
    private static CacheTablasAuditables instance;

    /**
     * @return the listaTablasAuditables
     */
    public Map<String, String> getListaTablasAuditables() {
        return listaTablasAuditables;
    }

    /**
     * @param listaTablasAuditables the listaTablasAuditables to set
     */
    public void setListaTablasAuditables(Map<String, String> listaTablasAuditables) {
        this.listaTablasAuditables = listaTablasAuditables;
    }

    /**
     * @return the instance
     */
    public static CacheTablasAuditables getInstance() {
        if (instance == null) {
            instance = new CacheTablasAuditables();
        }
        return instance;
    }   
    
}
