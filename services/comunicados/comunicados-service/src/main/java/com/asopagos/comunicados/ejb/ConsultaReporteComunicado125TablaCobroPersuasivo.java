package com.asopagos.comunicados.ejb;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ConsultaReporteComunicado125TablaCobroPersuasivo extends ConsultaReporteComunicadosAbs {

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
    	EstadoOperacionCarteraEnum estado = EstadoOperacionCarteraEnum.VIGENTE;
    	String tipoIdentificacion = ((TipoIdentificacionEnum)params.get(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION)).name();
    	
        Query query =  em.createNamedQuery(NamedQueriesConstants.CONSULTA_COMUNICADO_COBRO_PERSUASIVO);
        query.setParameter(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION,
                params.get(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION));
        query.setParameter(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION,tipoIdentificacion);
        query.setParameter("estado", estado.name());
        
        List<Object[]> result = query.getResultList();
        StringBuilder htmlContent = new StringBuilder(); 
        htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>PERIODO</th><th>VALOR MORA</th></tr>");
        
        for (Object[] objects : result) {
            htmlContent.append("<tr>");
            for (int i = 0; i < objects.length; i++) {
                if(objects[i]!=null){
                    htmlContent.append("<td>" + objects[i].toString() + "</td>");        
                } else {
                    htmlContent.append("<td> </td>");
                }
            }
            htmlContent.append("</tr>");
        }
        htmlContent.append("</table>"); 
        return htmlContent.toString();
    }
}
