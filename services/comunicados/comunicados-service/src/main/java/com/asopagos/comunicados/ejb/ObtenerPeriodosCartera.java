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

public class ObtenerPeriodosCartera extends ConsultaReporteComunicadosAbs {

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
            EstadoCarteraEnum estadocartera = EstadoCarteraEnum.MOROSO;
            EstadoOperacionCarteraEnum estadoOperacion = EstadoOperacionCarteraEnum.VIGENTE;
            String tipoIdentificacion = ((TipoIdentificacionEnum)params.get(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION)).name();
            
            @SuppressWarnings("unchecked")
            List<Object[]> result = (List<Object[]>) em.createNamedQuery(NamedQueriesConstants.CONSULTA_CARTERA_PERIODOS)
                    .setParameter(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION,
                            params.get(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION))
                    .setParameter(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION,tipoIdentificacion)
                    .setParameter("estadoCartera", estadocartera.name())
                    .setParameter("estadoOperacion", estadoOperacion.name())
                    .getResultList();
            
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
            logger.debug("Finaliza el método getReporte(EntityManager em)");
            return htmlContent.toString();
        } catch (Exception e) {
            logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            throw new TechnicalException(e.getMessage());
        }
    }
}
