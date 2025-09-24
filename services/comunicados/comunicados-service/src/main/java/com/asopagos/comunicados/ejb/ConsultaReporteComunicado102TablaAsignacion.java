package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-323-050 Generar, imprimir, firmar, escanear y adjuntar acta asignación FOVIS <br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co"> ecastano</a>
 */

public class ConsultaReporteComunicado102TablaAsignacion extends ConsultaReporteComunicadosAbs {	


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
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_HOGARES_ASIGNADOS);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));
        
        List<Object[]> result = query.getResultList();
        
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<style>table{border-collapse:collapse;width:100%;font-size: inherit;font-weight:inherit;font-style:inherit;font-variant:inherit;}table,th,td{border:1px solid black;}</style>");
        htmlContent.append("<table><tr>");
        htmlContent.append("<th style='width:5%; text-align: center;'>No</th>");
        htmlContent.append("<th style='width:20%; text-align: center;'>Identificación</th>");
        htmlContent.append("<th style='width:25%; text-align: center;'>Nombre</th>");
        htmlContent.append("<th style='width:20%; text-align: center;'>Modalidad</th>");
        htmlContent.append("<th style='width:12%; text-align: center;'>Puntaje</th>");
        htmlContent.append("<th style='width:18%; text-align: center;'>Valor Subsidio</th>");
        htmlContent.append("<th style='width:25%; text-align: center;'>Recurso Prioridad</th>");
        htmlContent.append("</tr>");
        
        DecimalFormat format = new DecimalFormat("###,###.##");
        int temporalIndiceTabla = 0;
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (Object[] objects : result) {
            temporalIndiceTabla++;
            htmlContent.append("<tr><td>");
            htmlContent.append(temporalIndiceTabla);
            htmlContent.append("</td><td>");
            htmlContent.append(TipoIdentificacionEnum.valueOf(objects[0].toString()).getDescripcion());
            htmlContent.append(" ");
            htmlContent.append(objects[1].toString());
            htmlContent.append("</td><td>");
            htmlContent.append(objects[2].toString());
            htmlContent.append("</td><td>");
            htmlContent.append(ModalidadEnum.valueOf(objects[3].toString()).getDescripcion());
            htmlContent.append("</td><td style='text-align: right;'>");
            htmlContent.append(objects[4].toString());
            htmlContent.append("</td><td style='text-align: right;'>");
            htmlContent.append(format.format((BigDecimal)objects[5]));
            htmlContent.append("</td><td style='text-align: right;'>");
            htmlContent.append(objects[6].toString());
            htmlContent.append("</td></tr>");
            valorTotal = valorTotal.add((BigDecimal)(objects[5]));
        }
        htmlContent.append("<tr><td colspan='4' style='text-align: center;'>");
        htmlContent.append("Total");
        htmlContent.append("</td><td colspan='2' style='text-align: right;'>");
        htmlContent.append("$ ");
        htmlContent.append(format.format(valorTotal));
        htmlContent.append("</td></tr></table>");
        System.out.println("Comunicado carta htmlContent.toString() " + htmlContent);
        return htmlContent.toString();
    }

    /**
     * Metodo encargado de obtener el valor del elemento
     * @param valorCampo
     *        registro con el valor del elemento
     * @return BigDecimal
     */
    private BigDecimal obtenerValorCampo(String valorCampo) {

        String campoProcesar = (String) valorCampo.toString();
        campoProcesar = campoProcesar.replace(".", "");
        if (campoProcesar.contains(",")) {
            return new BigDecimal(campoProcesar.replace(",", "."));
        }
        else {
            return new BigDecimal(campoProcesar);
        }
    }
}