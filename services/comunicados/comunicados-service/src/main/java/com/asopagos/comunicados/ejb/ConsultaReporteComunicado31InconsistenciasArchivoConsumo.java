package com.asopagos.comunicados.ejb;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaArchivoConsumoAnibolEnum;

/**
 * <b>Descripcion:</b> Clase que representa el proceso de generacion del cuerpo para el comunicado: <br/>
 * Noficación de inconsistencias en procesamiento de archivo de consumo ANIBOL
 * <b>Módulo:</b> Asopagos - ANEXO-Validacion y cargue archivo consumos_validado V.2<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 */

public class ConsultaReporteComunicado31InconsistenciasArchivoConsumo extends ConsultaReporteComunicadosAbs {

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

        StringBuilder strBHtmlContent = new StringBuilder();
        Query queryInconsistencias = null;
        List<Object[]> lstInconsistenciasResult = null;

        //se obtienen las inconsistencias relacionadas al archivo de consumo de anibol
        queryInconsistencias = em
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_INCONSISTENCIAS_ARCHIVO_CONSUMOS_PROCESO_DE_PAGOS);
        queryInconsistencias.setParameter(ConstantesComunicado.KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL,
                params.get(ConstantesComunicado.KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL));
        lstInconsistenciasResult = queryInconsistencias.getResultList();

        if (lstInconsistenciasResult != null && !lstInconsistenciasResult.isEmpty()) {
            if (lstInconsistenciasResult.get(0)[0] != null && lstInconsistenciasResult.get(0)[1] != null) {
                strBHtmlContent.append("CAMPO CON INCONSISTENCIA/VALOR DEL CAMPO,");
                for (Object[] inconsistenciaDetectada : lstInconsistenciasResult) {
                    strBHtmlContent.append(inconsistenciaDetectada[0].toString() + "/" + inconsistenciaDetectada[1].toString() + ",");
                }
            }
            else {
                for (Object[] inconsistenciaDetectada : lstInconsistenciasResult) {
                    strBHtmlContent.append("Inconsistencia general:/"
                            + TipoInconsistenciaArchivoConsumoAnibolEnum.valueOf(inconsistenciaDetectada[2].toString()).getDescripcion()
                            + ",");
                }
            }

        }
        return strBHtmlContent.toString();
    }

}
