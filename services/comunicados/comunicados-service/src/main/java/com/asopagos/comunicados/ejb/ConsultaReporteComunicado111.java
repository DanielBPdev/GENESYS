package com.asopagos.comunicados.ejb;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class ConsultaReporteComunicado111 extends ConsultaReporteComunicadosAbs {
    
    /**
     * Mapa que contiene los parametros adicionales para el comunicado
     */
    private Map<String, Object> params = null;
    
    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /** (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#init(java.util.Map)
     */
    @Override
    public void init(Map<String, Object> params) {
        setParams(params);
    }

    /** (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
     */
    @Override
    public String getReporte(EntityManager em) {
        Query query =  em.createNamedQuery(NamedQueriesConstants.CONSULTA_COMUNICADO_SOL_DEV_APORTES);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));

        List<Object[]> result = query.getResultList();
        String htmlContent = "<table><tr><th>Número</th><th>Número Operación de Recaudo</th><th>Fecha de Recaudo</th><th>Método de Recaudo</th><th>Número de Planilla</th><th>Tipo de Planilla</th><th>Monto Devolución</th><th>Monto Interes</th><th>Total Devolución</th></tr>";
        
        int temporalIndiceTabla=0;
        for (Object[] objects : result) {
            temporalIndiceTabla++;
            htmlContent += "<tr><td>" + temporalIndiceTabla + "</td>"; 
            for (int i = 0; i < objects.length; i++) {
                if(objects[i]!=null){
                    if(i==3){
                        htmlContent += ("<td>${"+objects[i].toString()+objects[0].toString()+"}</td>");        
                    } else if(i==4){
                        htmlContent += ("<td>${"+objects[i].toString()+objects[0].toString()+"}</td>");        
                    } else {
                        htmlContent += "<td>" + objects[i].toString() + "</td>";        
                    }
                } else {
                    htmlContent += "<td> </td>";
                }
            }
            htmlContent += "</tr>";
        }
        htmlContent += "</table>"; 
        return htmlContent;
    }
}
