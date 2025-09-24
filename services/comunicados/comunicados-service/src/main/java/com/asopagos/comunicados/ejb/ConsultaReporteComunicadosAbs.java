package com.asopagos.comunicados.ejb;

import java.util.Map;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public abstract class ConsultaReporteComunicadosAbs {
    
    
    public abstract void init(Map<String, Object> params);
    
    public String getReporte(EntityManager em) {
        return "";
    }
    
    public String getReporte(EntityManager em, ManagedExecutorService managedExecutorService) {
        return "";
    }
    
    public String getCertificado(EntityManager ...em){
    	return "";
    }
    
}
