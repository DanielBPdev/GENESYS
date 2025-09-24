package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.EstadosUtils;

/**
 * <b>Descripcion:</b> Clase que consulta <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa Salamanca</a>
 */

public class ConsultaReporteComunicado003TablaPersonas extends ConsultaReporteComunicadosAbs {
    
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
        // TODO Auto-generated method stub
        setParams(params);
    }

    /** (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getReporte(EntityManager em) {
        Query query =  em.createNamedQuery(NamedQueriesConstants.CONSULTA_TABLA_CARTA_ENTIDAD_PAGADORA);
        if (params.containsKey(ConstantesComunicado.KEY_MAP_IDES_SOLICITUD)) {
            query.setParameter(ConstantesComunicado.KEY_MAP_IDES_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_IDES_SOLICITUD));
        }        
        List<Object[]> result = query.getResultList();
        String htmlContent = "<table><tr><th>#</th><th>Tipo identificación</th><th>Número identificación</th><th>Nombre</th><th>Tipo afiliación</th><th>Estado de la persona con respecto a la CCF</th><th>Fecha novedad</th><th>Número de solicitud</th><th>Estado de alta en la entidad</th><th>Estado de la solicitud</th></tr>";
        List<ConsultarEstadoDTO> listConsulta= new ArrayList<>();
        int temporalIndiceTabla=0;
        
        for (Object[] objects : result) {
            ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();
            consultEstado.setEntityManager(em);
            consultEstado.setNumeroIdentificacion(objects[1].toString());
            consultEstado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(objects[0].toString()));
            consultEstado.setTipoPersona(ConstantesComunes.PERSONAS);
            listConsulta.add(consultEstado);
        }
        /* Se calcula el estado de la persona con respecto a la ccf */
        List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
        
        for (Object[] objects : result) {
            temporalIndiceTabla++;
            htmlContent += "<tr><td>" + temporalIndiceTabla + "</td>"; 
            htmlContent += recorrerVectorObjeto(estados, objects);
            htmlContent += "</tr>";
        }
        htmlContent += "</table>"; 
        return htmlContent;
    }

    /**
     * @param htmlContent estructua html de la tabla para el comunicado
     * @param estados lista de estados de la persona con respecto a la ccf
     * @param objects colección que contiene la variable para mostrar en el comunicado
     * @return todo el contenido de la tabla para el comunicado
     */
    private String recorrerVectorObjeto(List<EstadoDTO> estados, Object[] objects) {
        String htmlContent = "";
        
        for (int i = 0; i < objects.length; i++) {
            if(objects[i]!=null){
                /* se setea al html el estado de la persona */
                if(i == 4){
                    htmlContent += "<td>" + cambiarEstadoPersona(estados, objects) + "</td>";
                }else{
                    htmlContent += "<td>" + objects[i].toString() + "</td>";
                }
            } else {
                htmlContent += "<td> </td>";
            }
        }
        return htmlContent;
    }

  /**
   * Metodo que cambia el estado de la persona
   * @param estados lista de estados de la persona con respecto a la ccf
   * @param objects colección que contiene la variable para mostrar en el comunicado
   * @return una cadena con el valor a mostrar en el comunicado
   */
    private String cambiarEstadoPersona(List<EstadoDTO> estados, Object[] objects) {
        String estado = "";
        for (EstadoDTO estadoDTO : estados) {
            if (estadoDTO.getNumeroIdentificacion().equals(objects[1].toString())) {
                 objects[4]=(estadoDTO.getEstado());
                 estado = objects[4].toString();
                 break;
            }                            
        }
        return estado;
    }
}
