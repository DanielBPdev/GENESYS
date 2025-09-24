package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.DescripcionCruceFovisDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.CausalCruceEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-323-050 Generar, imprimir, firmar, escanear y adjuntar acta asignación FOVIS <br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co"> ecastano</a>
 */

public class ConsultaReporteComunicado102TablaCruce extends ConsultaReporteComunicadosAbs {

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
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_CRUCE_INFORMACION);
        query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));
        
        List<Object[]> result = query.getResultList();
        String htmlContent = "<style>table{border-collapse:collapse;width:100%;font-size:inherit;font-weight:inherit;font-style:inherit;font-variant:inherit;}table,th,td{border:1px solid black;}</style>"
                + "<table><tr>"
                + "<th style='text-align: center;'>#</th>"
                + "<th style='text-align: center;'>Identificación</th>"
                + "<th style='text-align: center;'>Nombre</th>"
                + "<th style='text-align: center;'>Descripción cruce</th></tr>";
        Map<BigInteger, List<DescripcionCruceFovisDTO>> variablesPostulacion = new HashMap<>();

        int temporalIndiceTabla = 0;
        for (Object[] objects : result) {
        	temporalIndiceTabla++;
        	BigInteger key = (BigInteger)objects[8];
        	DescripcionCruceFovisDTO descripcion = new DescripcionCruceFovisDTO();
        	descripcion.setCausalCruce(CausalCruceEnum.valueOf(objects[7].toString()));
        	descripcion.setNombreJefeHogar(objects[2].toString());
        	descripcion.setNombreMiembroHogar(objects[4].toString());
        	descripcion.setNumeroIdentificacionJefeHogar(objects[1].toString());
        	descripcion.setNumeroIdentificacionMiembro(objects[6].toString());
        	descripcion.setParentesco(ClasificacionEnum.valueOf(objects[3].toString()));
        	descripcion.setTipoIdentificacionJefeHogar(TipoIdentificacionEnum.valueOf(objects[0].toString()));
        	descripcion.setTipoIdentificacionMiembro(TipoIdentificacionEnum.valueOf(objects[5].toString()));
        	
        	if (variablesPostulacion.containsKey(key)) {
				variablesPostulacion.get(key).add(descripcion);
			} else {
				List<DescripcionCruceFovisDTO> listCruce = new ArrayList<>();
				listCruce.add(descripcion);
				variablesPostulacion.put(key, listCruce);
			}
        	
        	
        }
        for (Object[] objects : result) {
        	BigInteger key = (BigInteger)objects[8];
        	htmlContent += "<tr><td>" + temporalIndiceTabla + "</td>";
            htmlContent += "<td>" + TipoIdentificacionEnum.valueOf(objects[0].toString()).getDescripcion() + " " + objects[1].toString() + "</td>";
            htmlContent += "<td>" + objects[2].toString() + "</td>";
            htmlContent += "<td>";
            for (DescripcionCruceFovisDTO infoCruce : variablesPostulacion.get(key)) {
            	htmlContent += "<p>"+infoCruce.getParentesco().getDescripcion() +" - "+ infoCruce.getNombreMiembroHogar() + " identificado con " + infoCruce.getTipoIdentificacionMiembro().getDescripcion()
            			+ " " + infoCruce.getNumeroIdentificacionMiembro() + ", present&#243; " + infoCruce.getCausalCruce().getDescripcion()+"</p>";
			}
            htmlContent += "</td>";
            htmlContent += "</tr>";
		}
        htmlContent += "</table>";
        return htmlContent;
    }
}
