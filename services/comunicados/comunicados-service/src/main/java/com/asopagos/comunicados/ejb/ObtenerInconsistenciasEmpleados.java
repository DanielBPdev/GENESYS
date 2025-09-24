package com.asopagos.comunicados.ejb;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.aportes.clients.ConsultarRegistroDetalladoPorId;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ObtenerInconsistenciasEmpleados extends ConsultaReporteComunicadosAbs {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ObtenerPeriodosCartera.class);

    /**
     * Mapa con los parametros adicionales para resolver el comunicado
     */
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
        setParams(params);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
     */
    @Override
    public String getReporte(EntityManager em) {
        try {
            logger.debug("Inicia el método getReporte(EntityManager em) ");
            String tipoIdentificacion = ((TipoIdentificacionEnum)params.get(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION)).name();

            
            
            @SuppressWarnings("unchecked")
            List<Object[]> result = (List<Object[]>) em.createNamedQuery(NamedQueriesConstants.OBTENER_INCONSISTENCIAS_EMPLEADOS)
                    .setParameter(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION,
                            params.get(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION))
                    .setParameter(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION,tipoIdentificacion)
                    .getResultList();

            List<String> lstCedulas = new ArrayList<String>();
            
            StringBuilder htmlContent = new StringBuilder(); 
            htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;text-align: left;}</style><table><tr><th>PERÍODO DE DEUDA</th><th>TIPO DE IDENTIFICACIÓN</th><th>NÚMERO DE IDENTIFICACIÓN</th><th>TIPO DE INCONSISTENCIA</th><th>DEUDA PRESUNTA</th></tr>");            
            for (Object[] objects : result) {
                if (lstCedulas.contains(objects[2].toString())) {
                    continue;
                }
                lstCedulas.add(objects[2].toString());
                htmlContent.append("<tr>");
                htmlContent.append("<td>" + objects[1].toString() + "</td>");
                htmlContent.append("<td>" + objects[2].toString() + "</td>");
                htmlContent.append("<td>" + objects[3].toString() + "</td>");
                if (objects[0].toString().equals("LC3") || objects[0].toString().equals("LC30") || objects[0].toString().equals("LC3A") || objects[0].toString().equals("LC3B")){

                    try {
                        RegistroDetalladoModeloDTO registroDetalladoDTO = new RegistroDetalladoModeloDTO();
                        registroDetalladoDTO.setOutEstadoValidacionV3(null);
                        registroDetalladoDTO.setOutEstadoValidacionV2(null);
                        registroDetalladoDTO.setOutEstadoValidacionV1(null);
                        registroDetalladoDTO.setOutEstadoValidacionV0(null);
                        ConsultarRegistroDetalladoPorId consultarRegistroDetallado = new ConsultarRegistroDetalladoPorId(Long.valueOf(objects[4].toString()));
                        consultarRegistroDetallado.execute();
                        registroDetalladoDTO = consultarRegistroDetallado.getResult();
                        if (registroDetalladoDTO.getOutEstadoValidacionV3() != null) {
                            htmlContent.append("<td>" + "Días trabajados" + "</td>");
                        } else if (registroDetalladoDTO.getOutEstadoValidacionV2() != null) {
                            htmlContent.append("<td>" + "Clase de trabajador" + "</td>");
                        } else if (registroDetalladoDTO.getOutEstadoValidacionV1() != null) {
                            htmlContent.append("<td>" + "Tarifa" + "</td>");
                        } else if (registroDetalladoDTO.getOutEstadoValidacionV0() != null) {
                            htmlContent.append("<td>" + "Tipo aportante" + "</td>");
                        } else {
                            htmlContent.append("<td>" + "" + "</td>");
                        }
                    } catch (Exception e) {
                        htmlContent.append("<td>" + "" + "</td>");
                    }

                } else {
                    htmlContent.append("<td>" + "IBC" + "</td>");

                }
                htmlContent.append("<td>" + objects[5].toString() + "</td>");
                htmlContent.append("</tr>");
            
            }
            htmlContent.append("</table>"); 
            logger.debug("Finaliza el método getReporte(EntityManager em)");
            return htmlContent.toString();
        } catch (Exception e) {
            logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            throw new TechnicalException(e.getMessage());
        }
    }
}
