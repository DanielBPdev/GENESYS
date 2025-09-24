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

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-323-050 Generar, imprimir, firmar, escanear y adjuntar acta asignación FOVIS <br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co"> ecastano</a>
 */

public class ConsultaReporteComunicado102TablaModalidad extends ConsultaReporteComunicadosAbs {

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
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_MODALIDAD);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));
        
        DecimalFormat format = new DecimalFormat("###,###.##");
        List<Object[]> result = query.getResultList();
        String htmlContent = "<style>table{border-collapse:collapse;width:100%;font-size:inherit;font-weight:inherit;font-style:inherit;font-variant:inherit;}table,th,td{border:1px solid black;}</style>"
                + "<table><tr>"
                + "<th style='text-align: center;'>Modalidad</th>"
                + "<th style='text-align: center;'>Cantidad</th>"
                + "<th style='text-align: center;'>Valor</th></tr>";

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalAsignados = BigDecimal.ZERO;
        for (Object[] objects : result) {

            htmlContent += "<tr><td>" + ModalidadEnum.valueOf(objects[0].toString()).getDescripcion() + "</td>";
            htmlContent += "<td style='text-align: right;'>" + objects[1].toString() + "</td>";
            htmlContent += "<td style='text-align: right;'>" + format.format(objects[2]) + "</td>";
            totalAsignados = totalAsignados.add(obtenerValorCampo(objects[1].toString()));
            valorTotal = valorTotal.add((BigDecimal)objects[2]);

            htmlContent += "</tr>";
        }
        htmlContent += "<tr> <td> Total </td> <td style='text-align: right;'>" + totalAsignados.toString() + "</td> <td style='text-align: right;'>" + format.format(valorTotal) + "</td> </tr>";
        htmlContent += "<tr> </tr>";
        htmlContent += "</table>";
        return htmlContent;
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
