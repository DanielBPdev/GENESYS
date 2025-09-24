package com.asopagos.comunicados.ejb;

import java.util.Date;
import java.util.List;
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

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ObtenerGrupoFamiliar extends ConsultaReporteComunicadosAbs {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ObtenerGrupoFamiliar.class);

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
            logger.info("Inicia el método getReporte(EntityManager em) ");
            logger.info("Data solicitud: "+ params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));
            @SuppressWarnings("unchecked")
            List<Object[]> result = (List<Object[]>) em.createNamedQuery(NamedQueriesConstants.CONSULTA_GRUPO_FAMILIAR)
                    .setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD,
                            params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD))
                    .getResultList();
            
            StringBuilder htmlContent = new StringBuilder(); 
            htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>Nombre</th><th>Tipo identificaci&oacute;n</th><th>N&uacute;mero Identificaci&oacute;n</th><th>Fechas de ingreso</th><th>Fecha de retiro</th></tr>");
            
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
            logger.debug("Finaliza el método getReporte(EntityManager em)");
            return htmlContent.toString();
        } catch (Exception e) {
            logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            throw new TechnicalException(e.getMessage());
        }
    }
}
