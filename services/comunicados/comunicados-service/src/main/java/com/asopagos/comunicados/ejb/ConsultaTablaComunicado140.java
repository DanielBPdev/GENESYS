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
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ConsultaTablaComunicado140 extends ConsultaReporteComunicadosAbs {

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
        Query query =  em.createNamedQuery(NamedQueriesConstants.CONSULTA_COMUNICADO_NTF_CCL_PUB_EDC);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));

        List<Object[]> result = query.getResultList();
        String htmlContent = "<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>Número</th><th>Tipo de identificación</th><th>Número de identificación</th><th>Razón social / Nombre</th><th>N° de documento de liquidación de aportes</th><th>Fecha comunicado de liquidación de aportes</th><th>Observaciones</th><th>Periodo deuda</th><th>Valor deuda presunta</th><th>Consecutivo liquidación</th></tr>";
        
        int temporalIndiceTabla=0;
        for (Object[] objects : result) {
            temporalIndiceTabla++;
            htmlContent += "<tr><td>" + temporalIndiceTabla + "</td>"; 
            for (int i = 0; i < objects.length; i++) {
                if(objects[i]!=null){
                    htmlContent += "<td>" + objects[i].toString() + "</td>";        
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
