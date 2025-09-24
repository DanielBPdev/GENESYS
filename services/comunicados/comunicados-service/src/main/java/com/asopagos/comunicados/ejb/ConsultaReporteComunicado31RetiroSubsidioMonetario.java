package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;

/**
 * <b>Descripcion:</b> Clase que representa el proceso de generacion del cuerpo para el comunicado: <br/>
 * 31-202 Retirar por ventanilla
 * <b>Módulo:</b> Asopagos - HU-31-202 <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 */

public class ConsultaReporteComunicado31RetiroSubsidioMonetario extends ConsultaReporteComunicadosAbs {

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
        
        String usuarioTransaccion = (String)params.get(ConstantesComunicado.KEY_MAP_USUARIO_TRANSACCION_PAGOS);
        StringBuilder strBHtmlContent = null;
        String personAutorizada = null;
        Query queryPersonaAutorizada = null;
        Query queryEmpleadores = null;
        List<Object[]> lstEmpleadoresResult = null;

        //se obtiene la persona autorizada (si se realiza por medio de un retiro con persona autorizada en ventanilla)
        queryPersonaAutorizada = em
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_AUTORIZADA_RETIRO_POR_VENTANILLA_PROCESO_DE_PAGOS);
        queryPersonaAutorizada.setParameter(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR,
                params.get(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR));
        //se obtienen los empleadores relacionados a los abonos cobrados por el retiro
        queryEmpleadores = em
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_COMUNICADO_RETIRO_POR_VENTANILLA_PROCESO_DE_PAGOS);
        queryEmpleadores.setParameter(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR,
                params.get(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR));
        lstEmpleadoresResult = queryEmpleadores.getResultList();

        try {
            personAutorizada = queryPersonaAutorizada.getSingleResult().toString();
        } catch (NoResultException e) {

        }
        strBHtmlContent = new StringBuilder();
        strBHtmlContent.append("<table style='width:60%' ><tr>");
        if (personAutorizada != null) {
            strBHtmlContent.append("<td style='width:30%;'><b>" + "Autorizado:</b></td><td colspan='2'>" + personAutorizada + "</td></tr>");
        }
        if (lstEmpleadoresResult != null && !lstEmpleadoresResult.isEmpty()) {
            strBHtmlContent.append(
                    "<tr><td colspan='3'><b>Empleador(es):</b></td></tr>" + "<tr><td style='width:30%;'><b>Tipo de Identificación:</b></td>"
                            + "<td style='width:25%;'><b>Número de Identificación:</b></td>" + "<td><b>Nombre/Razón Social:</b></td></tr>");

            for (Object[] empleador : lstEmpleadoresResult) {
                strBHtmlContent.append("<tr><td>" + empleador[1].toString() + "</td>");
                strBHtmlContent.append("<td>" + empleador[0].toString() + "</td>");
                strBHtmlContent.append("<td>" + empleador[2].toString() + "</td>");
                strBHtmlContent.append("</tr>");
            }
        }
        strBHtmlContent.append("<tr><td><b>Usuario que registro la transacción de retiro:</b></td><td colspan='2'>"
                + usuarioTransaccion+"</td></tr><tr><td colspan='3'>&nbsp;</td></tr>"
                + "<tr><td colspan='3'>&nbsp;</td></tr><tr><td colspan='3'>_________________________________________</td>"
                + "</tr><tr><td colspan='3'><b>Firma afiliado/administrador del subsidio</b></td></tr><tr><td><b>"
                + "Tipo y número de identificación:</b></td><td colspan='2'>_________________________________________</td></tr></table>");

        return strBHtmlContent.toString();
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
