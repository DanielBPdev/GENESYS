package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-323-050 Generar, imprimir, firmar, escanear y adjuntar acta asignación FOVIS <br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co"> ecastano</a>
 */

public class ConsultaReporteComunicado103CartaAsignacion extends ConsultaReporteComunicadosAbs {

    private Map<String, Object> params = null;

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params
     *        the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#init(java.util.Map)
     */
    @Override
    public void init(Map<String, Object> params) {
        // TODO Auto-generated method stub
        setParams(params);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
     */
    @Override
    public String getReporte(EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTA_FOVIS_TABLA_CARTA_ASIGNACION);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));

        List<Object[]> result = query.getResultList();
        String htmlContent = "<table><tr><th>Hogar beneficiario</th><th>Identificación</th></tr>";

        for (Object[] objects : result) {

            htmlContent += "<tr><td>" + objects[0].toString() + "</td>";
            htmlContent += "<td>" + objects[1].toString() + " " + objects[2].toString() + "</td>";
            htmlContent += "</tr>";
        }

        htmlContent += "</table>";
        return htmlContent;
    }

}
