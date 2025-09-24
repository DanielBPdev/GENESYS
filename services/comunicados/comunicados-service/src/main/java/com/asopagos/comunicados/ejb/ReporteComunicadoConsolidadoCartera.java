package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.cartera.clients.ConsultarEtiquetaPorAccion;
import com.asopagos.comunicados.clients.ResolverPlantillaCarteraConsolidadoComunicado;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.DatosIdEmpleadorDTO;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;


/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ReporteComunicadoConsolidadoCartera extends ConsultaReporteComunicadosAbs {

    /**
     * Cantidad de notificaciones a procesar por hilo
     */
    private final Integer PROCESAMIENTO_POR_HILO = 20;
    
	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(ReporteComunicadoConsolidadoCartera.class);

	/**
	 * Mapa con los parametros de consulta
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
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, Object> params) {
		setParams(params);
	}

	@Override
    public String getReporte(EntityManager em, ManagedExecutorService managedExecutorService) {
        try {
            logger.debug("Inicia el método getReporte(EntityManager em) ");
            
            Long idSolicitud = (Long) params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD);
            
            Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTA_TIPO_ACCION_COBRO);
            query.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD));
            String tipoAccionCobro = query.getSingleResult().toString();
            
            if (tipoAccionCobro == null) {
                return "";
            }
            
            EtiquetaPlantillaComunicadoEnum etiqueta = consultarEtiquetaPorAccion(TipoAccionCobroEnum.valueOf(tipoAccionCobro));
            
            if (etiqueta == null) {
                return "";
            }
            
            String htmlContent = obtenerPlantillasResueltasOrdenadas(idSolicitud, etiqueta, em, managedExecutorService); 
            
            logger.debug("Finaliza el método getReporte(EntityManager em)");
            return htmlContent;
            
        } catch (Exception e) {
            logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
            throw new TechnicalException(e.getMessage());
        }

    }
	
	@SuppressWarnings("unchecked")
    private String obtenerPlantillasResueltasOrdenadas (Long idSolicitud, EtiquetaPlantillaComunicadoEnum etiqueta, EntityManager em, ManagedExecutorService managedExecutorService) {
	    
	    List<DatosIdEmpleadorDTO> listaOrdenamiento = new ArrayList<>();
	    String filtro = null;
	    String htmlContent = "";
	    
	    try {
    	    // Realiza el ordenamiento de la lista, de acuerdo a los números de identificación enviados como parámetro
            if (params.containsKey("ordenamiento")) {
                List<String> listaOrdenamientoString = Arrays.asList(params.get("ordenamiento").toString().split(","));
                int orden = 1;
                for (String numeroIdentificacion : listaOrdenamientoString) {
                    DatosIdEmpleadorDTO datosIdEmpleadorDTO = new DatosIdEmpleadorDTO(null, numeroIdentificacion, orden);
                    listaOrdenamiento.add(datosIdEmpleadorDTO);
                    orden++;
                }
                ObjectMapper mapper = new ObjectMapper();
                filtro = mapper.writeValueAsString(listaOrdenamiento);
            }
                
            synchronized (em) {
                StoredProcedureQuery spQuery = em.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_PREGENERAR_CONSOLIDADO_CARTERA)
                        .setParameter("idSolicitud", idSolicitud)
                        .setParameter("etiqueta",    etiqueta.toString());
                spQuery.execute();
            }
                
            List<Object[]> resultado = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICACION_RESUELTA_CONSOLIDADO_CARTERA)
                    .setParameter("ordenamiento",     filtro)
                    .setParameter("idSolicitud", idSolicitud)
                    .getResultList();
            
            ParametrosComunicadoDTO parametros = null;
            
            List<PlantillaComunicado> tempPlantillas = new ArrayList<>();
            List<Callable<List<PlantillaComunicado>>> tareasParalelas = new LinkedList<>();
            List<ParametrosComunicadoDTO> tempParametros = new ArrayList<>();
            int count = 0;
            for(Object[] fila :resultado){


                 PlantillaComunicado plantillaResuelta = new PlantillaComunicado();
                 plantillaResuelta.setAsunto(fila[0].toString());
                 plantillaResuelta.setCuerpo(fila[1].toString());
                 plantillaResuelta.setEncabezado(fila[2].toString());
                 plantillaResuelta.setEtiqueta(EtiquetaPlantillaComunicadoEnum.valueOf(fila[3].toString()));
                 plantillaResuelta.setMensaje(fila[4].toString());
                 plantillaResuelta.setNombre(fila[5].toString());
                 plantillaResuelta.setPie(fila[6].toString());
                 plantillaResuelta.setIdPlantillaComunicado(new Long(fila[7].toString()));

                 parametros = new ParametrosComunicadoDTO();

                 if (EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR.equals(etiqueta)) {
                     parametros.setIdCartera(new Long(fila[8].toString()));
                 } else {
                     parametros.setNumeroIdentificacion(fila[9].toString());
                     parametros.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(fila[10].toString()));
                 }

                 count++;
                 tempParametros.add(parametros);
                 tempPlantillas.add(plantillaResuelta);

                 if (count == PROCESAMIENTO_POR_HILO) {
                     paralelizarProcesamientoPlanillas(etiqueta, tempPlantillas, tempParametros, tareasParalelas, idSolicitud);
                     count = 0;
                     tempPlantillas = new ArrayList<>();
                     tempParametros = new ArrayList<>();
                 }

                 //plantillasResueltas.add(plantillaResuelta);
            }
            
            if (count > 0) {
                paralelizarProcesamientoPlanillas(etiqueta, tempPlantillas, tempParametros, tareasParalelas, idSolicitud); 
            }
            
            List<PlantillaComunicado> plantillasComunicado = obtenerResultadosFuturos(tareasParalelas, managedExecutorService); 
            htmlContent = procesarGeneracionReporte(plantillasComunicado);
            
	    } catch (Exception e) {
	        logger.error("Finaliza obtenerPlantillasResueltasOrdenadas(Long, EntityManager): Error al procesar la notificación", e);
            throw new TechnicalException(e);
        }
           
	    return htmlContent;
	}
	
	
	/**
	 * Se encarga de procesar la generación del reporte consolidado a partir del
	 * listado de plantillas previamente procesadas. 
	 * @param plantillasComunicado
	 * @return
	 */
    private String procesarGeneracionReporte(List<PlantillaComunicado> plantillasComunicado) {


        StringBuilder htmlContent = new StringBuilder("<div>");
        int indiceTemporal = 0;


        for (PlantillaComunicado plantillaComunicado : plantillasComunicado) {
        
            if (indiceTemporal == 0) {
                indiceTemporal++;
            } else if (indiceTemporal == 1) {
                htmlContent.append("<div style=\"page-break-before: always\">");
                indiceTemporal++;
            } else {
                htmlContent.append("</div>");
                htmlContent.append("<div style=\"page-break-before: always\">");
            }

            if (plantillaComunicado != null) {
                htmlContent.append(plantillaComunicado.getEncabezado());
                htmlContent.append(plantillaComunicado.getCuerpo());
                htmlContent.append(plantillaComunicado.getPie());
            }
            
        }
        
        if (indiceTemporal > 1) {
        	htmlContent.append("</div>");
        }
        
        htmlContent.append("</div>");

        return htmlContent.toString();
    }
	
    /**
     * Se encarga de obtener los resultado futuros relacionados con la ejecución en paralelo del 
     * procesamiento de plantillas.
     * @param tareasParalelas
     * @param managedExecutorService
     * @return
     */
	private List<PlantillaComunicado> obtenerResultadosFuturos(List<Callable<List<PlantillaComunicado>>> tareasParalelas, ManagedExecutorService managedExecutorService) {

	    List<Future<List<PlantillaComunicado>>> resultadosFuturos =  new ArrayList<>();
        //El M.E.S. invoca la lista de tareas que se ejecutaran en paralelo 
        try{
            resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
        }catch (InterruptedException e){
            logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesar la notificación", e);
        }
        List<PlantillaComunicado> outDTO = new ArrayList<>();

        resultadosFuturos.parallelStream().forEach(e->{
            try {
                outDTO.addAll(e.get());
            } catch (InterruptedException ex) {
                logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", ex);
            } catch (ExecutionException ex) {
                logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", ex);
            }
        });
       return outDTO;
	    
	}
	
	/**
	 * Método que se encarga de cosumir el cliente que consulta la planilla con
	 * las variables resueltas
	 * 
	 * @param etiquetaPlantillaComunicadoEnum
	 * @param map
	 */
	private EtiquetaPlantillaComunicadoEnum consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro) {
		ConsultarEtiquetaPorAccion etiquetaService = new ConsultarEtiquetaPorAccion(accionCobro);
		etiquetaService.execute();
		return etiquetaService.getResult();
	}
	
	
	/**
     * Se encarga de la lógica que permite paralelizar el proceso de generación de plantillas
     * @param tempParametros
     * @param tempEtiquetas
     * @param tareasParalelas
     */
    private void paralelizarProcesamientoPlanillas(EtiquetaPlantillaComunicadoEnum etiqueta, List<PlantillaComunicado> tempPlantillas,  
            List<ParametrosComunicadoDTO> tempParametros, List<Callable<List<PlantillaComunicado>>> tareasParalelas, Long idSolicitud) {
        final EtiquetaPlantillaComunicadoEnum etiquetaFinal = etiqueta;
        final List<PlantillaComunicado>  plantillasResueltasFinal = tempPlantillas;
        final List<ParametrosComunicadoDTO> parametrosFinal = tempParametros;
        
        final Long idSolicitudFinal = idSolicitud;
        Callable<List<PlantillaComunicado>> parallelTask = () -> {
            return procesarPlantillas(etiquetaFinal, parametrosFinal, plantillasResueltasFinal, idSolicitudFinal);
        };
        tareasParalelas.add(parallelTask);
    }
	
    
    /**
     * Se encarga de procesar las plantillas del reporte para completarla con las imagenes y los subreportes.
     * @param etiquetasList
     * @param parametrosList
     * @return
     */
    private List<PlantillaComunicado> procesarPlantillas(EtiquetaPlantillaComunicadoEnum etiqueta,  
            List<ParametrosComunicadoDTO> parametros, List<PlantillaComunicado> plantillasResueltas, Long idSolicitud){
        
        List<PlantillaComunicado> plantillas = new LinkedList<>();

        for (int i = 0; i < plantillasResueltas.size(); i++) {
            plantillas.add(completarPlantillaComunicadoResuelta(plantillasResueltas.get(i), parametros.get(i), etiqueta, idSolicitud));
        }
        return plantillas;
    }
    
    /**
     * Método que se encarga de completar el reemplazo en la plantilla que llega como parámetro
     * @param plantillaResuelta
     * @param etiquetaPlantillaComunicadoEnum
     * @param idSolicitud
     */
    private PlantillaComunicado completarPlantillaComunicadoResuelta(PlantillaComunicado plantillaResuelta, 
            ParametrosComunicadoDTO parametrosComunicadoDTO, EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolicitud) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("plantillaResuelta", plantillaResuelta);
        map.put("solicitud", idSolicitud);
        map.put("parametros", parametrosComunicadoDTO);
        
        ResolverPlantillaCarteraConsolidadoComunicado resolverCom = new ResolverPlantillaCarteraConsolidadoComunicado(etiquetaPlantillaComunicadoEnum, map);
        PlantillaComunicado plaCom = new PlantillaComunicado();
        resolverCom.execute();
        plaCom = (PlantillaComunicado) resolverCom.getResult();
        return plaCom;
    }
    
}
