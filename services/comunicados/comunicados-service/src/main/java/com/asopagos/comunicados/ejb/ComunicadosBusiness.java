package com.asopagos.comunicados.ejb;

import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ConvertHTMLtoPDF;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.constants.TemporalJsonConstants;
import com.asopagos.comunicados.dto.InfoJsonTemporalDTO;
import com.asopagos.comunicados.service.ComunicadosService;
import com.asopagos.comunicados.service.PlantillasService;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ContextoComunicadoDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.JsonPayloadDatoTemporalComunicadoDTO;
import com.asopagos.dto.PlantillaComunicadoDTO;
import com.asopagos.dto.modelo.ComunicadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalComunicado;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.comunicados.DetalleComunicadoEnviado;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.MedioComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ConsultarSolicitudPorRadicado;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.ComunicadoPersistenciaDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.ValidacionCamposUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión comunicados en la afiliacion de empleadores <br/>
 * <b>Módulo:</b> Asopagos - HU-331 <br/>
 *
 * @author <a href="mailto:jerodriguez@heinsohn.com.co"> jerodriguez</a>
 */
@Stateless
public class ComunicadosBusiness implements ComunicadosService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ComunicadosBusiness.class);

    @Inject
    private PlantillasService plantilla;

    @PersistenceContext(unitName = "comunicados_PU")
    private EntityManager entityManager;

    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * <b>Descripción</b>Método que se encarga de crear un comunicado<br/>
     * <code> comunicado es el objeto que se va a guardar </code>
     *
     * @param comunicado informaciòn a registrar
     * @return Retorna el id del nuevo comunicado
     */
    @Override
    public Long crearComunicado(Comunicado comunicado) {

        // Verifica que el comunicado tenga email y que sea válido
        // verifica que el comunicado tenga texto adicional
        if (comunicado.getEmail() != null && ValidacionCamposUtil.validarEmail(comunicado.getEmail())
                && (comunicado.getTextoAdicionar() != null || !comunicado.getTextoAdicionar().equals(ConstantesComunicado.VACIO))) {

            entityManager.persist(comunicado);

            return comunicado.getIdComunicado();

        } else {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#resolverPlantillaVariablesComunicado(com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum,
     * java.lang.Long)
     */
    @Override
    public PlantillaComunicado resolverPlantillaVariablesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                    Long idInstancia, Map<String, Object> map) {
        return resolverPlantillaVariablesComunicadoPrivate(etiquetaPlantillaComunicadoEnum, idInstancia, null, map, null);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#resolverPlantillaVariablesComunicadoPorSolicitud(com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum,
     * java.lang.Long, java.util.Map)
     */
    @Override
    public PlantillaComunicado resolverPlantillaVariablesComunicadoPorSolicitud(
            EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolictud, Map<String, Object> map) {
        return resolverPlantillaVariablesComunicadoPrivate(etiquetaPlantillaComunicadoEnum, null, idSolictud, map, null);
    }

    /**
     * Método con la lógica para resolver y reemplazar las variables del
     * comunicado
     *
     * @param etiquetaPlantillaComunicadoEnum
     * @param idInstancia
     * @param idSolicitud
     * @param map
     * @return
     */
    private PlantillaComunicado resolverPlantillaVariablesComunicadoPrivate(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                            Long idInstancia, Long idSolicitud, Map<String, Object> map, ParametrosComunicadoDTO parametros) {
        logger.info("Inicia resolverPlantillaVariablesComunicado(EtiquetaPlantillaComunicadoEnum,Long,Long,Map<String, Object>,ParametrosComunicadoDTO)");
        // Se consulta la plantilla
        logger.info("plantilla resolver:" + plantilla);
        AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteConsolidadoCarteraSingleton = AuxiliarReporteConsolidadoCarteraSingleton.getSingletonInstance();
        PlantillaComunicado plantillaComunicado = auxiliarReporteConsolidadoCarteraSingleton.obtenerPlanillaComunicado(etiquetaPlantillaComunicadoEnum, plantilla);

        // Se consultan las variables
        Map<String, Object> valoresVariable = new HashMap<>();

        if (map != null && !map.isEmpty() && map.containsKey("ordenamiento") && parametros == null) {
            String ordenamiento = null;
            if (map != null && map.containsKey("ordenamiento")) {
                ordenamiento = map.get("ordenamiento").toString();
            }
            valoresVariable = plantilla.resolverVariablesComunicadoIdPlantilla(etiquetaPlantillaComunicadoEnum, plantillaComunicado.getIdPlantillaComunicado(), idInstancia, idSolicitud, ordenamiento);
        } else if (parametros == null) {
            valoresVariable = plantilla.resolverVariablesComunicadoIdPlantilla(etiquetaPlantillaComunicadoEnum, plantillaComunicado.getIdPlantillaComunicado(), idInstancia, idSolicitud, null);
        } else {
            parametros.setIdPlantillaComunicado(plantillaComunicado.getIdPlantillaComunicado());
            valoresVariable = plantilla.resolverVariablesComunicadoParametros(etiquetaPlantillaComunicadoEnum, idSolicitud, parametros);
            if (map == null && parametros.getParams() != null) {
                map = new HashMap<>();
                map.putAll(parametros.getParams());
            }
        }

        // Se recorre y reemplaza en el mapa que llegó por payload
        if (map != null) {
            StringBuilder key = new StringBuilder();
            for (Entry<String, Object> mapa : map.entrySet()) {
                key.setLength(0);
                key.append(ConstantesComunicado.VARIABLE_ABRIR);
                key.append(mapa.getKey());
                key.append(ConstantesComunicado.VARIABLE_CERRAR);
                // se valida pues prevalece la información de la
                // base de datos sobre la enviada en el payload
                if (!valoresVariable.containsKey(key)) {
                    valoresVariable.put(key.toString(), mapa.getValue());
                } else {
                    if (valoresVariable.get(key.toString()) == null) {
                        valoresVariable.put(key.toString(), mapa.getValue());
                    }
                }
            }
        }

        // Se recorre y reemplaza en el mapa consultado
        replaceFromMap(plantillaComunicado, valoresVariable, auxiliarReporteConsolidadoCarteraSingleton);
        auxiliarReporteConsolidadoCarteraSingleton.destruir();

        logger.info("Finaliza resolverPlantillaVariablesComunicado(EtiquetaPlantillaComunicadoEnum,Long,Long,Map<String, Object>,ParametrosComunicadoDTO)");
        return plantillaComunicado;
    }

    /**
     * (non-Javadoc)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PlantillaComunicado resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                     ParametrosComunicadoDTO parametrosComunicadoDTO) {
        Long idsol = null;
        logger.info("IdsSolicitud::" + parametrosComunicadoDTO.getIdsSolicitud());
        if (parametrosComunicadoDTO.getIdsSolicitud()!=null)
            for (Long idSolicitud : parametrosComunicadoDTO.getIdsSolicitud()) {
                idsol = idSolicitud;
                logger.info("idSolicitud::" + idSolicitud);
                logger.info("idsol::" + idsol);
            }
        return resolverPlantillaVariablesComunicadoPrivate(etiquetaPlantillaComunicadoEnum, null, idsol, null, parametrosComunicadoDTO);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#consultarComunicado(java.lang.Long)
     */
    @Override
    public ComunicadoModeloDTO consultarComunicado(Long idComunicado) {
        try {
            logger.debug("Inicio de método consultarComunicado(Long idComunicado)");
            Comunicado comunicado = (Comunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_ID)
                    .setParameter("idComunicado", idComunicado).getSingleResult();
            ComunicadoModeloDTO comunicadoDTO = new ComunicadoModeloDTO();
            comunicadoDTO.convertToDTO(comunicado);
            logger.debug("Fin de método consultarComunicado(Long idComunicado)");
            return comunicadoDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Error al consultarComunicado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#consultarComunicadoPorSolicitud(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ComunicadoModeloDTO> consultarComunicadoPorSolicitud(Long idSolicitud) {
        try {
            logger.debug("Inicio del método consultarComunicadoPorSolicitud(Long idSolicitud)");
            List<Comunicado> comunicados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_ID_SOLICITUD)
                    .setParameter("idSolicitud", idSolicitud).getResultList();
            List<ComunicadoModeloDTO> comunicadosDTO = new ArrayList<ComunicadoModeloDTO>();
            for (Comunicado comunicado : comunicados) {
                ComunicadoModeloDTO comunicadoDTO = new ComunicadoModeloDTO();
                comunicadoDTO.convertToDTO(comunicado);
                comunicadosDTO.add(comunicadoDTO);
            }
            logger.debug("Fin del método consultarComunicadoPorSolicitud(Long idSolicitud)");
            return comunicadosDTO;
        } catch (Exception e) {
            logger.error("Error en el servicio consultarComunicadoPorSolicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#consultarComunicadoPorRadicado(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ComunicadoModeloDTO> consultarComunicadoPorRadicado(String numeroRadicado) {
        try {
            logger.debug("Inicio del método consultarComunicadoPorRadicado");
            List<Object[]> comunicados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_RADICADO)
                    .setParameter("numeroRadicado", numeroRadicado).getResultList();
            List<ComunicadoModeloDTO> comunicadosDTO = new ArrayList<ComunicadoModeloDTO>();

            for (Object[] registro : comunicados) {
                ComunicadoModeloDTO comunicadoDTO = new ComunicadoModeloDTO();
                comunicadoDTO.setIdentificadorArchivoComunicado(registro[0] != null ? registro[0].toString() : null);
                comunicadoDTO.setFechaComunicado(registro[1] != null ? ((Date) registro[1]).getTime() : null);
                comunicadoDTO.setDestinatario(registro[2] != null ? registro[2].toString() : null);
                comunicadoDTO.setEstadoEnvio(registro[3] != null ? EstadoEnvioComunicadoEnum.valueOf(registro[3].toString()) : null);
                PlantillaComunicado plantilla = new PlantillaComunicado();
                plantilla.setEtiqueta(registro[4] != null ? EtiquetaPlantillaComunicadoEnum.valueOf(registro[4].toString()) : null);
                comunicadoDTO.setMensajeEnvio(registro[5] != null ? registro[5].toString() : null);
                comunicadoDTO.setPlantillaComunicado(plantilla);
                comunicadosDTO.add(comunicadoDTO);
            }

            logger.debug("Fin del método consultarComunicadoPorRadicado");
            return comunicadosDTO;
        } catch (Exception e) {
            logger.error("Error en el servicio consultarComunicadoPorRadicado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#guardarDatoTemporalComunicado(com.asopagos.entidades.ccf.afiliaciones.
     * DatoTemporalComunicado)
     */
    @Override
    public void guardarDatoTemporalComunicado(DatoTemporalComunicado datoTemporalComunicado) {
        logger.debug("Inicio del método guardarDatoTemporalComunicado(DatoTemporalComunicado)");

        DatoTemporalComunicado dato = null;
        try{
            dato = (DatoTemporalComunicado) entityManager
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_COMUNICADO)
            .setParameter("idTarea", datoTemporalComunicado.getIdTarea())
            .getSingleResult();
        }catch(NoResultException e){
        }
        
        try {
            if(dato == null){
            entityManager.persist(datoTemporalComunicado);
            }else{
                datoTemporalComunicado.setIdDatoTemporalComunicado(dato.getIdDatoTemporalComunicado());
                entityManager.merge(datoTemporalComunicado);
            }
        }catch (Exception e) {
            logger.debug("Fin del método guardarDatoTemporalComunicado(DatoTemporalComunicado)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#obtenerDatoTemporalComunicado(java.lang.Long)
     */
    @Override
    public DatoTemporalComunicado obtenerDatoTemporalComunicado(Long idTarea) {
        logger.debug("Inicio del método obtenerDatoTemporalComunicado(Long) IdTarea: " + idTarea);
        try {
            logger.debug("Fin del método obtenerDatoTemporalComunicado(Long)");
            return (DatoTemporalComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_COMUNICADO)
                    .setParameter("idTarea", idTarea).getSingleResult();

        } catch (Exception e) {
            logger.debug("Error: Fin del método obtenerDatoTemporalComunicado(Long)" + idTarea);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#eliminarDatoTemporalComunicado(java.lang.Long)
     */
    @Override
    public void eliminarDatoTemporalComunicado(Long idTarea) {
        logger.info("Inicio del método eliminarDatoTemporalComunicado(Long) idTarea: " + idTarea);
        try {
            DatoTemporalComunicado dato = (DatoTemporalComunicado) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_COMUNICADO)
                    .setParameter("idTarea", idTarea)
                    .getSingleResult();
            entityManager.remove(dato);
            logger.debug("Fin del método eliminarDatoTemporalComunicado(Long)");

        } catch (Exception e) {
            logger.debug("Error: Fin del método eliminarDatoTemporalComunicado(Long)" + idTarea);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#guardarObtenerComunicadoECM(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     * com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum, com.asopagos.notificaciones.dto.ParametrosComunicadoDTO)
     */
    @Override
    public InformacionArchivoDTO guardarObtenerComunicadoECM(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,
                                                             ParametrosComunicadoDTO parametroComunicadoDTO) {
        logger.info("Inicio de método guardarObtenerComunicadoECM");

        Map<String, Object> params = parametroComunicadoDTO.getParams();
        String identificador = "";

        if (params.containsKey("tipoIdentificacion")) {
            logger.info("entro1");
            parametroComunicadoDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(params.get("tipoIdentificacion").toString()));
        }

        if (params.containsKey("numeroIdentificacion")) {
            parametroComunicadoDTO.setNumeroIdentificacion(params.get("numeroIdentificacion").toString());
            identificador = params.get("numeroIdentificacion").toString();
        }
        logger.info("antes de if1");
        if (params.containsKey("idCartera") && params.get("idCartera") != null) {
            parametroComunicadoDTO.setIdCartera(new Long(params.get("idCartera").toString()));
            identificador = params.get("idCartera").toString();
        }

        logger.debug("Procesando GuardarComunicadoECM: TI:" + parametroComunicadoDTO.getTipoIdentificacion() + "NI:"
                + parametroComunicadoDTO.getNumeroIdentificacion() + "IdCartera:" + parametroComunicadoDTO.getIdCartera());
        logger.info("antes plaCom::");

        PlantillaComunicado plaCom = resolverPlantillaConstantesComunicado(plantilla, parametroComunicadoDTO);
        logger.info("despues plaCom::" + plaCom);
        InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error: la plantilla del comunicado no tiene datos");
            logger.info("La plantilla del comunicado no tiene datos");
        } else {
            List<Float> margenesX = new ArrayList<Float>();
            margenesX.add(56f);
            margenesX.add(56f);
            List<Float> margenesY = new ArrayList<Float>();
            margenesY.add(40f);
            margenesY.add(40f);

            InformacionConvertDTO infoConv = new InformacionConvertDTO();
            infoConv.setHtmlHeader(plaCom.getEncabezado());
            infoConv.setHtmlContenido(plaCom.getCuerpo());
            infoConv.setHtmlFooter(plaCom.getPie());
            infoConv.setMargenesx(margenesX);
            infoConv.setMargenesy(margenesY);
            infoConv.setAltura(100f);
            infoConv.setRequiereSello(true);

            // Se convierte a pdf la plantilla del comunicado
            byte[] bytes = convertHTMLtoPDF(infoConv);

            if (bytes == null) {
                logger.error("Error la plantilla del comunicado no fue convertida a PDF");
                logger.debug("Finaliza guardarComunicadoSinNotificacionEmail");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
            }
            logger.info("sale del if");
            infoArch.setDataFile(bytes);
            infoArch.setDescription("Comunicado");

            // Se agrega nombre del documento
            infoArch.setDocName(plantilla + identificador + "_comunicado.pdf");
            infoArch.setFileName(plantilla + identificador + "_comunicado.pdf");
            infoArch.setFileType("application/pdf");
            infoArch.setProcessName(tipoTransaccion.getProceso().name());
            infoArch.setIdInstanciaProceso(null);

            // Se realiza el upload del archivo al ECM
            infoArch.setIdentificadorDocumento(almacenarArchivo(infoArch));
        }
        logger.info("Finaliza guardarComunicadoSinNotificacionEmail");
        return infoArch;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#guardarObtenerInfoArchivoComunicado(com.asopagos.enumeraciones.core.TipoTransaccionEnum, com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum, com.asopagos.notificaciones.dto.ParametrosComunicadoDTO)
     */
    @Override
    public InformacionArchivoDTO guardarObtenerInfoArchivoComunicado(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,
                                                                     ParametrosComunicadoDTO parametroComunicadoDTO) {
        logger.info("Inicio de método guardarObtenerComunicadoECM");
        InformacionArchivoDTO informacionArchivo = guardarObtenerComunicadoECM(tipoTransaccion, plantilla, parametroComunicadoDTO);
        informacionArchivo.setDataFile(null);
        return informacionArchivo;

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#guardarComunicadoECM(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     * com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum, com.asopagos.notificaciones.dto.ParametrosComunicadoDTO)
     */
    @Override
    public String guardarComunicadoECM(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,
                                       ParametrosComunicadoDTO parametroComunicadoDTO) {
        return guardarObtenerComunicadoECM(tipoTransaccion, plantilla, parametroComunicadoDTO).getIdentificadorDocumento();
    }

    /**
     * Método que invoca el servicio que almacena un archivo en el ECM
     *
     * @param infoFile Información del archivo a almacenar
     * @return El identificador generado en el ECM
     */
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();

        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        infoFile.setVersionDocumento(archivo.getVersionDocumento());
        return idECM.toString();
    }

    /**
     * Método que convierte una trama HTML a PDF
     *
     * @param objInformacionConvertDTO La información de la trama HTML
     * @return EL arreglo de bytes equivalente en PDF
     */
    private byte[] convertHTMLtoPDF(InformacionConvertDTO objInformacionConvertDTO) {
        ConvertHTMLtoPDF convertHTMLtoPDF = new ConvertHTMLtoPDF(objInformacionConvertDTO);
        convertHTMLtoPDF.execute();
        byte[] bytes = (byte[]) convertHTMLtoPDF.getResult();
        return bytes;
    }

    @Override
    public List<Object> obtenerNotificacion(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, String numIdentificacion,
                                            TipoIdentificacionEnum tipoIdentificacion, String fechaEnvio, Boolean esEmpleador) {
        // TODO Auto-generated method stub
        logger.debug(
                "Inicia obtenerNotificacion(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, String numIdentificacion, String fechaEnvio)");
        List<Object> arrayRequest = new ArrayList<>();
        List<Comunicado> notificaciones = new ArrayList<Comunicado>();

        try {
            if (esEmpleador) {
                // Se consultan los comunicados realizados en procesos de
                // empleador
                notificaciones = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADOS_ENVIADOS_EMPLEADOR, Comunicado.class)
                        .setParameter("numeroIdentificacion", numIdentificacion)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                        .setParameter("etiqueta", EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP.name()).getResultList();

            } else {
                // Se consultan los comunicados realizados en procesos de
                // persona
                notificaciones = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADOS_ENVIADOS_PERSONA, Comunicado.class)
                        .setParameter("numeroIdentificacion", numIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                        .getResultList();
            }
            if (!notificaciones.isEmpty()) {
                arrayRequest = filtrarCriterioBusqueda(arrayRequest, notificaciones, etiquetaPlantillaComunicadoEnum, fechaEnvio);
            }
        } catch (Exception e) {
            logger.error(
                    "Finaliza obtenerNotificacion(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, String numIdentificacion, String fechaEnvio)");
            throw new TechnicalException(e.getMessage());
        }
        logger.debug(
                "Finaliza obtenerNotificacion(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, String numIdentificacion, String fechaEnvio)");
        return arrayRequest;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#obtenerNotificacionPila(java.lang.String, java.lang.String,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<Object> obtenerNotificacionPila(String etiquetaPlantillaComunicadoEnum, String numIdentificacion,
                                                TipoIdentificacionEnum tipoIdentificacion, String fechaEnvio, Boolean esEmpleador) {
        // TODO Auto-generated method stub
        logger.debug("Inicia obtenerNotificacionPila(String etiquetaPlantillaComunicadoEnum, String numIdentificacion, String fechaEnvio)");
        List<Object> arrayRequest = new ArrayList<>();
        List<Comunicado> notificaciones = new ArrayList<>();
        try {
            if (esEmpleador) {
                notificaciones = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOTIFICACIONES_PILA_EMPLEADOR, Comunicado.class)
                        .setParameter("numIdentificacion", numIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                        .setParameter("etiqueta", etiquetaPlantillaComunicadoEnum).getResultList();
            } else {
                notificaciones = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOTIFICACIONES_PILA_PERSONA, Comunicado.class)
                        .setParameter("numIdentificacion", numIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                        .setParameter("etiqueta", etiquetaPlantillaComunicadoEnum).getResultList();
            }

            if (notificaciones == null || notificaciones.isEmpty()) {
                return arrayRequest;
            }

            // Se realiza el filtro Etiqueta - Fecha
            List<Comunicado> arrayRequestTmp = new ArrayList<>();
            SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfResp = new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
            Map<String, Object> request = null;

            if (fechaEnvio != null) {
                arrayRequestTmp = notificaciones.stream().filter(com -> sdfReq.format(com.getFechaComunicado()).equals(fechaEnvio))
                        .collect(Collectors.toList());
            } else
                arrayRequestTmp = notificaciones;

            for (Comunicado comunicado : arrayRequestTmp) {
                request = new HashMap<>();
                request.put("fechaEnvioComunicado", sdfResp.format(comunicado.getFechaComunicado()));
                request.put("nombreComunicado", comunicado.getPlantillaComunicado().getNombre());
                request.put("canalEnvio", comunicado.getMedioComunicado());
                request.put("comunicadoEnviado", comunicado.getIdentificadorArchivoComunicado());
                request.put("mensajeEnvio", comunicado.getMensajeEnvio());
                request.put("plantillaComunicado", comunicado.getPlantillaComunicado().getEtiqueta());
                arrayRequest.add(request);
            }

        } catch (Exception e) {
            logger.error(
                    "Finaliza obtenerNotificacionPila(String etiquetaPlantillaComunicadoEnum, String numIdentificacion, String fechaEnvio)");
            throw new TechnicalException(e.getMessage());
        }
        return arrayRequest;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> comunicadosEnviados(TipoIdentificacionEnum tipoIdentificacion, String numIdentificacion, Boolean esEmpleador) {
        logger.debug("Incia comunicadosEnviados(TipoIdentificacionEnum, String, Boolean)");
        List<Object> result = new ArrayList<Object>();
        List<Object[]> comunicadoTemp = new ArrayList<Object[]>();

        try {
            if (esEmpleador) {
                //Se consultan los comunicados realizados en procesos de empleador
                //La consulta contempla los comunicados enviados en el proceso de dispersion de pagos por medio de la etiqueta (union en consulta)
                comunicadoTemp = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ETIQUETA_COMUNICADOS_ENVIADOS_EMPLEADOR)
                        .setParameter("numeroIdentificacion", numIdentificacion)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                        .setParameter("etiqueta", EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP.name()).getResultList();
            } else {
                //Se consultan los comunicados realizados en procesos de persona
                comunicadoTemp = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ETIQUETA_COMUNICADOS_ENVIADOS_PERSONA, Object[].class)
                        .setParameter("numeroIdentificacion", numIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                        .getResultList();
            }
        } catch (Exception e) {
            logger.error("Finaliza con error comunicadosEnviados(TipoIdentificacionEnum, String, Boolean)");
            e.printStackTrace();
        }
        if (comunicadoTemp != null && !comunicadoTemp.isEmpty()) {
            for (Object[] objects : comunicadoTemp) {
                result.add(objects);
            }
        }
        logger.debug("Finaliza comunicadosEnviados(TipoIdentificacionEnum, String, Boolean)");
        return result;
    }

    @Override
    public List<String> obtenerCorreoFront(Long idSolicitud) {
        logger.info("Incia obtenerCorreoFront(Long");
        List<String> correos = new ArrayList<>();

        correos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CORREO_FRONT, String.class)
                .setParameter("idSolicitud", idSolicitud).getResultList();
        logger.info("Finaliza obtenerCorreoFront(Long");
        return correos;
    }

    private List<Object> filtrarCriterioBusqueda(List<Object> arrayRequest, List<Comunicado> notificaciones,
                                                 EtiquetaPlantillaComunicadoEnum etiqueta, String fechaEnvio) {
        List<Comunicado> arrayRequestTmp = new ArrayList<>();
        SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfResp = new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
        Map<String, Object> request = null;
        if (etiqueta != null && fechaEnvio != null) {
            arrayRequestTmp = notificaciones.stream().filter(com -> com.getPlantillaComunicado().getEtiqueta().equals(etiqueta)
                    && sdfReq.format(com.getFechaComunicado()).equals(fechaEnvio)).collect(Collectors.toList());
        } else if (etiqueta == null && fechaEnvio != null) {
            arrayRequestTmp = notificaciones.stream().filter(com -> sdfReq.format(com.getFechaComunicado()).equals(fechaEnvio))
                    .collect(Collectors.toList());
        } else if (etiqueta != null && fechaEnvio == null) {
            arrayRequestTmp = notificaciones.stream().filter(com -> com.getPlantillaComunicado().getEtiqueta().equals(etiqueta))
                    .collect(Collectors.toList());
        }
        if (etiqueta == null && fechaEnvio == null) {
            arrayRequestTmp = notificaciones.stream().limit(20).collect(Collectors.toList());
        }

        for (Comunicado comunicado : arrayRequestTmp) {
            request = new HashMap<>();
            request.put("fechaEnvioComunicado", sdfResp.format(comunicado.getFechaComunicado()));
            request.put("nombreComunicado", comunicado.getPlantillaComunicado().getNombre());
            request.put("canalEnvio", comunicado.getMedioComunicado());
            request.put("comunicadoEnviado", comunicado.getIdentificadorArchivoComunicado());
            request.put("mensajeEnvio", comunicado.getMensajeEnvio());
            request.put("plantillaComunicado", comunicado.getPlantillaComunicado().getEtiqueta());
            arrayRequest.add(request);
        }
        return arrayRequest;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#persistirComunicado(com.asopagos.notificaciones.dto.NotificacionDTO,
     * com.asopagos.rest.security.dto.UserDTO, com.asopagos.entidades.ccf.comunicados.PlantillaComunicado)
     */
    public void persistirComunicado(NotificacionDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia persistirComunicado(NotificacionDTO, UserDTO, PlantillaComunicado)");

        Comunicado comunicado = new Comunicado();
        comunicado.setMedioComunicado(MedioComunicadoEnum.IMPRESO);
        comunicado.setRemitente(userDTO.getNombreUsuario());
        comunicado.setSedeCajaCompensacion(userDTO.getSedeCajaCompensacion());
        comunicado.setFechaComunicado(Calendar.getInstance().getTime());
        comunicado.setIdSolicitud(notificacion.getIdSolicitud());
        if (notificacion.getEtiquetaPlantilla() != null) {
            Map<String, Object> map = transformarParametrosNotificacion(notificacion);
            PlantillaComunicado plantilla = resolverPlantillaVariablesComunicadoPorSolicitud(notificacion.getEtiquetaPlantilla(),
                    notificacion.getIdSolicitud(), map);
            comunicado.setPlantillaComunicado(plantilla);
            comunicado.setMensajeEnvio(plantilla.getMensaje());
        }
        // se obtiene el id del archivo adjunto (comunicado PDF)
        if (notificacion.getArchivosAdjuntos() != null && !notificacion.getArchivosAdjuntos().isEmpty()
                && notificacion.getArchivosAdjuntos().get(0).getIdECM() != null) {
            comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntos().get(0).getIdECM());
        } else if (notificacion.getArchivosAdjuntosIds() != null && !notificacion.getArchivosAdjuntosIds().isEmpty()) {
            comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntosIds().get(0));
        }
        entityManager.merge(comunicado);
        logger.debug("Finaliza persistirComunicado(NotificacionDTO, UserDTO, PlantillaComunicado)");
    }

    /**
     * Se encarga de convertir los parametros de notificación en parametros de
     * plantilla comunicados
     *
     * @param notificacion
     * @return
     */
    private Map<String, Object> transformarParametrosNotificacion(NotificacionDTO notificacion) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (notificacion.getParams() != null) {
            for (Entry<String, String> entry : notificacion.getParams().entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    /**
     * @see com.asopagos.comunicados.service.ComunicadosService#obtenerGenerarDatoTemporalComunicado(com.asopagos.comunicados.dto.InfoJsonTemporalDTO)
     */
    @Override
    public DatoTemporalComunicado obtenerGenerarDatoTemporalComunicado(InfoJsonTemporalDTO info) {
        logger.info("Inicio del método obtenergenrarDatoTemporalComunicado(Long,Long) IdTarea: " + info.getIdTarea() + " IdSolicitud: " + info.getIdSolicitud());

        DatoTemporalComunicado temporal = new DatoTemporalComunicado();
        DatoTemporalComunicado temporal2 = new DatoTemporalComunicado();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso() != null ? info.getIdInstanciaProceso().toString() : null;
        try {
            temporal = (DatoTemporalComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_COMUNICADO_ID_TAREA_O_ID_INSTANCIA_PROCESO)
                    .setParameter("idTarea", idTarea)
                    .setParameter("idInstanciaProceso", idInstanciaProceso)
                    .getSingleResult();
            if (temporal.getIdTarea() == null && temporal.getJsonPayload() != null && info.getIdTarea() != null) {
                temporal.setIdTarea(idTarea);
                String jsonPayload = temporal.getJsonPayload();
                temporal.setJsonPayload(jsonPayload.replaceAll("\"idTarea\":null", "\"idTarea\":" + info.getIdTarea().toString()));
                entityManager.merge(temporal);
            } else if (temporal.getJsonPayload() != null) {
                logger.debug("Fin del método obtenerDatoTemporalComunicado(Long)");
                return temporal;
            } else {
                temporal2 = (DatoTemporalComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_COMUNICADO_ID_INSTANCIA_PROCESO)
                    .setParameter("idInstanciaProceso", idInstanciaProceso)
                    .getSingleResult();
                if (temporal2.getJsonPayload() != null) {
                    JsonElement jsonElement = new JsonParser().parse(temporal2.getJsonPayload());
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    // Modificar el idTarea
                    JsonObject contexto = jsonObject.getAsJsonObject("contexto");
                    contexto.addProperty("idTarea", idTarea); 
                    // Convertir de nuevo a String
                    String updatedJsonString = jsonObject.toString();

                    // Convertir de nuevo a String
                    logger.info("updatedJsonString "+updatedJsonString);
                    temporal.setJsonPayload(updatedJsonString);
                    logger.debug("Fin del método obtenerDatoTemporalComunicado(Long)");
                    return temporal;
                }
                temporal.setJsonPayload(this.generarJson(info));
                logger.debug("Fin del método obtenerDatoTemporalComunicado(Long)");
                return temporal;
            }
        } catch (Exception e) {
            logger.error(e);
            String jsonPayload = generarJson(info);
            temporal.setIdTarea(idTarea);
            temporal.setJsonPayload(jsonPayload);
            entityManager.merge(temporal);
        }
        return temporal;
    }

    private String generarJson(InfoJsonTemporalDTO info) {

        SolicitudModeloDTO solicitud = null;
        if (info != null && info.getNumeroRadicado() != null) {

            ConsultarSolicitudPorRadicado solicitudSrv = new ConsultarSolicitudPorRadicado(info.getNumeroRadicado());
            solicitudSrv.execute();
            solicitud = solicitudSrv.getResult();
        }
        else if (info != null && info.getIdSolicitud() != null) {

            ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
				info.getIdSolicitud());
            consultarSolicitudGlobal.execute();
            solicitud = consultarSolicitudGlobal.getResult();
        }

        info.setIdInstanciaProceso(solicitud.getIdInstanciaProceso());
        info.setResultadoProceso(solicitud.getResultadoProceso());
        info.setNumeroRadicado(solicitud.getNumeroRadicacion());
        info.setClasificacion(solicitud.getClasificacion());

        ProcesoEnum proceso = solicitud.getTipoTransaccion().getProceso();

        String jsonPayload = null;

        switch (proceso) {
            case AFILIACION_PERSONAS_PRESENCIAL:
                jsonPayload = this.generarJsonTemporalAfiliacionPersona(info);
                break;
            case NOVEDADES_PERSONAS_PRESENCIAL:
                jsonPayload = this.generarJsonTemporalNovedadesPersona(info);
                break;
            case DEVOLUCION_APORTES:
                jsonPayload = this.generarJsonTemporalDevolucion(info);
                break;
            case CORRECCION_APORTES:
                jsonPayload = this.generarJsonTemporalCorrecion(info);
                break;
            case PAGO_APORTES_MANUAL:
                jsonPayload = this.generarJsonTemporalAporteManual(info);
                break;
            case AFILIACION_EMPRESAS_PRESENCIAL:
                jsonPayload = this.generarJsonTemporalAfiliacionEmpleador(info);
                break;
            case NOVEDADES_EMPRESAS_PRESENCIAL:
                jsonPayload = this.generarJsonTemporalNovedadEmpresa(info);
                break;
            case NOVEDADES_DEPENDIENTE_WEB:
                jsonPayload = this.generarJsonTemporalNovedadDependienteWeb(info);
                break;
            default:
                break;
        }
        return jsonPayload;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de novedad de personas presencial
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalNovedadesPersona(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        String numeroRadicado = info.getNumeroRadicado();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String url = info.getUrl();
        String jsonTemp = null;


        //Se consulta el estado de la solicitud de novedad
        String estadoSolicitudObj = (String) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_NOVEDAD)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();

        EstadoSolicitudNovedadEnum estadoSolicitud = EstadoSolicitudNovedadEnum.valueOf(estadoSolicitudObj);

        //Se consulta el número de comunicados que se han enviado asociados a la solcitud
        Integer cantidadComunicados = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_NOV_PER)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        switch (estadoSolicitud) {
            case ASIGNADA_AL_BACK:
                if (cantidadComunicados == 0) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RADICACION_SOLICITUD_NOVEDAD, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                } else
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_FRONT, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, randomUUIDString);
                break;
            case RADICADA:
                if (cantidadComunicados == 0) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RADICACION_SOLICITUD_NOVEDAD, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                } else
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_FRONT, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, randomUUIDString);
                break;
            case CERRADA:
            case APROBADA:
                if (ResultadoProcesoEnum.RECHAZADA.equals(resultadoProceso) || ResultadoProcesoEnum.APROBADA.equals(resultadoProceso) || ResultadoProcesoEnum.CANCELADA.equals(resultadoProceso)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_RECHAZADA, idInstanciaProceso, idSolicitud, idTarea, url, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                }
                break;
            default:
                break;
        }
        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de afiliación de personas presencial
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalAfiliacionPersona(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String jsonTemp = null;

        //Se consulta el estado de la solicitud de novedad
        String estadoSolicitudObj = (String) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_AFILIACION)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();

        EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud = EstadoSolicitudAfiliacionPersonaEnum.valueOf(estadoSolicitudObj);

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        switch (estadoSolicitud) {
            case RADICADA:
            case PRE_RADICADA:
                jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RADICACION_SOLICITUD_AFILIACION, idInstanciaProceso, idSolicitud, idTarea,
                        idSolicitud, randomUUIDString);
                break;
            case ASIGNADA_AL_BACK:
                if (ResultadoProcesoEnum.APROBADA.equals(resultadoProceso)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_AFILIACION_RECHAZADA, idInstanciaProceso, idSolicitud,
                            idTarea, idSolicitud, randomUUIDString);
                }
            case CERRADA:
                if (ResultadoProcesoEnum.RECHAZADA.equals(resultadoProceso)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_AFILIACION_RECHAZADA, idInstanciaProceso, idSolicitud,
                            idTarea, idSolicitud, randomUUIDString);
                }
                break;
            case REGISTRO_INTENTO_AFILIACION:
                ClasificacionEnum tipoAfiliado = info.getClasificacion();
                if (ClasificacionEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_INTENTO_AFILIACION_PERSONA, idInstanciaProceso, idSolicitud, tipoAfiliado.name(), idTarea,
                            idSolicitud, randomUUIDString);
                }
                break;
            default:
                break;
        }
        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de devolución de aportes
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalDevolucion(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        String jsonTemp = null;

        //Se consulta el estado de la solicitud de novedad
        Object[] solicitudObj = (Object[]) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_DEVOLUCION)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();

        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum.valueOf(solicitudObj[0].toString());
        ;
        ResultadoProcesoEnum resultadoAnalista = ResultadoProcesoEnum.valueOf(solicitudObj[1].toString());
        ResultadoProcesoEnum resultadoSupervisor = ResultadoProcesoEnum.valueOf(solicitudObj[2].toString());
        ;

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        if (EstadoSolicitudAporteEnum.GESTIONAR_PAGO.equals(estadoSolicitud)
                && ResultadoProcesoEnum.APROBADA.equals(resultadoAnalista)
                && ResultadoProcesoEnum.APROBADA.equals(resultadoSupervisor)) {
            jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_SOLICITUD_APROBADA_ANALISTA_DEVOLUCION, idSolicitud, idInstanciaProceso, idTarea,
                    idSolicitud, idInstanciaProceso, randomUUIDString);
        }

        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de correción de aportes
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalCorrecion(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String jsonTemp = null;

        //Se consulta el estado de la solicitud de novedad
        Object[] solicitudObj = (Object[]) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_CORRECCION)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();

        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum.valueOf(solicitudObj[0].toString());
        ;
        ResultadoProcesoEnum resultadoSupervisor = ResultadoProcesoEnum.valueOf(solicitudObj[1].toString());
        ;

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        if (ResultadoProcesoEnum.APROBADA.equals(resultadoProceso)
                && EstadoSolicitudAporteEnum.CERRADA.equals(estadoSolicitud)
                && ResultadoProcesoEnum.APROBADA.equals(resultadoSupervisor)) {
            jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_SOLICITUD_APROBADA_ANALISTA_CORRECCION, idSolicitud, idInstanciaProceso, idTarea,
                    idSolicitud, idInstanciaProceso, randomUUIDString);
        }

        if (ResultadoProcesoEnum.RECHAZADA.equals(resultadoProceso)
                && EstadoSolicitudAporteEnum.CERRADA.equals(estadoSolicitud)
                && ResultadoProcesoEnum.RECHAZADA.equals(resultadoSupervisor)) {
            jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_SOLICITUD_RECHAZADA_ANALISTA_CORRECCION, idSolicitud, idInstanciaProceso, idTarea,
                    idSolicitud, idInstanciaProceso, randomUUIDString);
        }

        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de correción de aportes
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalAporteManual(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String jsonTemp = null;

        //Se consulta el estado de la solicitud de novedad
        Object[] solicitudObj = (Object[]) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_APORTE_MANUAL)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();

        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum.valueOf(solicitudObj[0].toString());
        ;
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.valueOf(solicitudObj[1].toString());

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        if (ResultadoProcesoEnum.APROBADA.equals(resultadoProceso)
                && EstadoSolicitudAporteEnum.CERRADA.equals(estadoSolicitud)
                && TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {

            Object[] solicitudAporteObj = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_JSON_DETALLE_SOLICITUD_APORTE_MANUAL)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();

            JsonObject jsonObject = new JsonParser().parse(solicitudAporteObj[0].toString()).getAsJsonObject();
            JsonArray cotizantes = jsonObject.getAsJsonArray("cotizantesTemporales");
            String etiqueta;

            // 0 -> no tiene ctz
            if ((cotizantes != null && cotizantes.size() == 0) || !(Boolean) solicitudAporteObj[1]) {
                etiqueta = EtiquetaPlantillaComunicadoEnum.NTF_PAG_APT_DEP_APTE.name();
            } else {
                etiqueta = EtiquetaPlantillaComunicadoEnum.NTF_PAG_APT_DEP_APTE_CTZ.name();
            }

            jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_SOLICITUD_APROBADA_APORTE_MANUAL, idSolicitud, idInstanciaProceso,
                    idTarea, etiqueta, idSolicitud, randomUUIDString);
        }

        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de correción de aportes
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalNovedadEmpresa(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        String numeroRadicado = info.getNumeroRadicado();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String url = info.getUrl();
        String jsonTemp = null;


        //Se consulta el estado de la solicitud de novedad
        String estadoSolicitudObj = (String) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_NOVEDAD)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();

        EstadoSolicitudNovedadEnum estadoSolicitud = EstadoSolicitudNovedadEnum.valueOf(estadoSolicitudObj);

        //Se consulta el número de comunicados que se han enviado asociados a la solcitud
        Integer cantidadComunicados = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_NOV_EMP)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        switch (estadoSolicitud) {
            case CERRADA:
            case APROBADA:
                if (ResultadoProcesoEnum.APROBADA.equals(resultadoProceso)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_CIERRE_NOVEDAD_EMPLEADOR, idInstanciaProceso, idSolicitud, idTarea, url, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                }
                break;
            case ESPERANDO_CONFIRMACION_RETIRO:
                if (ClasificacionEnum.TRABAJADOR_DEPENDIENTE.equals(info.getClasificacion())) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_ESPERA_CONFIRMACION, idInstanciaProceso, idSolicitud, idTarea, url, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                }
            case RADICADA:
                if (cantidadComunicados == 0) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RADICACION_SOLICITUD_NOVEDAD_EMPLEADOR, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                } else
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_FRONT_EMPLEADOR, idInstanciaProceso, idSolicitud, idSolicitud, idInstanciaProceso, idSolicitud, idInstanciaProceso, idSolicitud, numeroRadicado, idTarea, randomUUIDString);
                break;
            default:
                break;
        }
        return jsonTemp;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de novedad de personas presencial
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalAfiliacionEmpleador(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadesPersona");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        String numeroRadicado = info.getNumeroRadicado();
        String jsonTemp = null;

        //Se consulta el estado de la solicitud de afiliación
        String estadoSolicitudObj = (String) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_AFILIACION_EMPLEADOR)
                .setParameter("numeroRadicado", numeroRadicado).getSingleResult();

        //Se consulta el número de comunicados que se han enviado asociados a la solcitud
        Integer cantidadComunicados = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_AFI_EMP)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();

        EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitud = EstadoSolicitudAfiliacionEmpleadorEnum.valueOf(estadoSolicitudObj);

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        switch (estadoSolicitud) {
            case PRE_RADICADA:
            case RADICADA:
            case PENDIENTE_ENVIO_AL_BACK:
            case ASIGNADA_AL_BACK:
                if (cantidadComunicados == 0) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_BIENVENIDA_EMPLEADOR, idInstanciaProceso, idSolicitud, idTarea,
                            idSolicitud, idInstanciaProceso, idSolicitud, idInstanciaProceso, randomUUIDString);
                }
                break;
            default:
                break;
        }
        return jsonTemp;
    }


    public PlantillaComunicado consultarPlantillaComunicadoPorEtiqueta(EtiquetaPlantillaComunicadoEnum etiquetaPlantilla) {
        logger.debug("Inicializa consultarPlantillaComunicadoPorEtiqueta(Long, PlantillaComunicado): etiquetaPlantilla:" + etiquetaPlantilla);
        PlantillaComunicado plantillaComunicado=null;
        try {
            plantillaComunicado = (PlantillaComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO).setParameter("etiqueta", etiquetaPlantilla)
                    .getSingleResult();

        } catch (NoResultException nre) {
            logger.debug("*** NoResultException----Finaliza consultarPlantillaComunicadoPorEtiqueta(Long, PlantillaComunicado)");
            //throw null;
        }
        logger.info("Finaliza consultarPlantillaComunicadoPorEtiqueta(Long, PlantillaComunicado)etiquetaPlantilla:" + etiquetaPlantilla);
        return plantillaComunicado;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Comunicado construirPersistirComunicado(ComunicadoPersistenciaDTO comunicadoPersistencia, UserDTO userDTO) {
        logger.debug("Inicia construirPersistirComunicado(ComunicadoPersistenciaDTO, UserDTO) etiqueta: "+comunicadoPersistencia.getPlantilla().getEtiqueta());
        NotificacionParametrizadaDTO notificacion = comunicadoPersistencia.getNotificacion();
        NotificacionEnviada notEnv = comunicadoPersistencia.getNotEnv();
        PlantillaComunicado plantilla = null;
        Comunicado comunicado = new Comunicado();
        if (comunicadoPersistencia.getPlantilla() != null ) {
            if (comunicadoPersistencia.getPlantilla().getIdPlantillaComunicado() != null ) {
            plantilla = entityManager.find(PlantillaComunicado.class, comunicadoPersistencia.getPlantilla().getIdPlantillaComunicado());
            }else{
                plantilla = consultarPlantillaComunicadoPorEtiqueta(comunicadoPersistencia.getPlantilla().getEtiqueta());
            }
        }
        if (!EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA.equals(comunicadoPersistencia.getPlantilla().getEtiqueta()) &&
                    !EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA.equals(comunicadoPersistencia.getPlantilla().getEtiqueta()) &&
                    !EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA.equals(comunicadoPersistencia.getPlantilla().getEtiqueta())) {
                comunicado.setIdSolicitud(notificacion.getIdSolicitud());
           }
        comunicado.setNumeroCorreoMasivo(notEnv.getIdEnvioNot());
        comunicado.setMedioComunicado(MedioComunicadoEnum.ENVIADO);
        comunicado.setRemitente(userDTO.getNombreUsuario());
        comunicado.setSedeCajaCompensacion(userDTO.getSedeCajaCompensacion());

        comunicado.setEmpleador(notificacion.getIdEmpleador() != null ? notificacion.getIdEmpleador() : null);
        comunicado.setEmpresa(notificacion.getIdEmpresa() != null ? notificacion.getIdEmpresa() : null);
        if (notificacion.getIdPersona() != null) {
            Persona persona = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA, Persona.class)
                    .setParameter("idPersona", notificacion.getIdPersona())
                    .getSingleResult();
            comunicado.setPersonaComunicado(persona);
        } else {
            logger.debug("No Se Encuentra La Persona A La Cual Se Enviara El Comunicado, En La Plantilla: " + notificacion.getProcesoEvento());
        }
        if (notificacion.getTextoAdicionar() != null && !notificacion.getTextoAdicionar().isEmpty()) {
            comunicado.setTextoAdicionar(notificacion.getTextoAdicionar());
        }
        if (notificacion.getEmail() != null && !notificacion.getEmail().isEmpty()) {
            comunicado.setEmail(notificacion.getEmail());
        }
        if (EstadoEnvioNotificacionEnum.FALLIDA.equals(notEnv.getEstadoEnvioNot())) {
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
        }
        comunicado.setFechaComunicado(Calendar.getInstance().getTime());
        comunicado.setMensajeEnvio(notificacion.getMensaje());

        if (plantilla != null) {
            comunicado.setPlantillaComunicado(plantilla);
            //Para Validar Que Es Un Certificado
            if (plantilla.getEtiqueta().name().startsWith("COM_GEN_CER_")) {
                comunicado.setComCertificado(notificacion.getIdSolicitud());
                comunicado.setIdSolicitud(null);
            }
        }

        // se obtiene el id del archivo adjunto (comunicado PDF)
        if (notificacion.getArchivosAdjuntos() != null && notificacion.getArchivosAdjuntos().size() > 0
                && notificacion.getArchivosAdjuntos().get(0).getIdECM() != null) {
            comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntos().get(0).getIdECM());
        } else if (notificacion.getArchivosAdjuntosIds() != null && !notificacion.getArchivosAdjuntosIds().isEmpty()) {
            comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntosIds().get(0));
        }

        // almacena el destinatario
        StringBuilder emails = new StringBuilder();
        if (notificacion.getDestinatarioTO() != null) {
            for (String email : notificacion.getDestinatarioTO()) {
                if (emails.length() > 0) {
                    emails.append(", ");
                }
                emails.append(email);
            }
        }
        if (notificacion.getDestinatarioCC() != null) {
            for (String email : notificacion.getDestinatarioCC()) {
                if (emails.length() > 0) {
                    emails.append(", ");
                }
                emails.append(email);
            }
        }
        if (notificacion.getDestinatarioBCC() != null) {
            for (String email : notificacion.getDestinatarioBCC()) {
                if (emails.length() > 0) {
                    emails.append(", ");
                }
                emails.append(email);
            }
        }
        if (emails.length() > 0) {
            comunicado.setDestinatario(emails.toString());
        }

        if (notEnv.getEstadoEnvioNot() != null && !notEnv.getEstadoEnvioNot().equals(EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO)) {
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.EXITOSO);
        } else
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.NO_AUTORIZA_ENVIO);

        try {
            comunicado = entityManager.merge(comunicado);

            // Se almacena un registro en la tabla DetalleComunicadoEnviado. Aplica únicamente cuando el comunicado no va asociado a ninguna solicitud.
            // Si llega el parámetro "identificador", indica que es un comunicado que no va asociado a ninguna solicitud 
            if (notificacion.getParams() != null && notificacion.getParams().containsKey("identificador")) {
                DetalleComunicadoEnviado detalleComunicadoEnviado = new DetalleComunicadoEnviado();
                detalleComunicadoEnviado.setIdComunicado(comunicado.getIdComunicado());
                detalleComunicadoEnviado.setTipoTransaccion(notificacion.getTipoTx());
                detalleComunicadoEnviado.setIdentificador(Long.parseLong(notificacion.getParams().get("identificador").toString()));
                entityManager.merge(detalleComunicadoEnviado);
            }

        } catch (Exception e) {
            logger.error("No es posible persistir el comunicado", e);
            logger.debug("Finaliza construirPersistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado) ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(
                "Finaliza construirPersistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado)");
        return comunicado;
    }

    /**
     * Genera el JSON asociado al dato temporal del comunicado para una solicitud de novedad de dependientes web
     *
     * @param info
     * @return
     */
    private String generarJsonTemporalNovedadDependienteWeb(InfoJsonTemporalDTO info) {
        logger.info("Incia el metodo generarJsonTemporalNovedadDependienteWeb");

        Long idSolicitud = info.getIdSolicitud();
        Long idTarea = info.getIdTarea();
        String idInstanciaProceso = info.getIdInstanciaProceso();
        //String numeroRadicado = info.getNumeroRadicado();
        ResultadoProcesoEnum resultadoProceso = info.getResultadoProceso();
        String url = info.getUrl();
        String jsonTemp = null;


        //Se consulta el estado de la solicitud de novedad
        String estadoSolicitudObj = (String) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_NOVEDAD)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();

        EstadoSolicitudNovedadEnum estadoSolicitud = EstadoSolicitudNovedadEnum.valueOf(estadoSolicitudObj);

        //Se genera el UUID
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        switch (estadoSolicitud) {
            case CERRADA:
            case APROBADA:
                if (ResultadoProcesoEnum.RECHAZADA.equals(resultadoProceso) || ResultadoProcesoEnum.APROBADA.equals(resultadoProceso) || ResultadoProcesoEnum.CANCELADA.equals(resultadoProceso)) {
                    jsonTemp = String.format(TemporalJsonConstants.COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_DEPENDIENTE_WEB, idInstanciaProceso, idSolicitud, idTarea, url, idSolicitud, idInstanciaProceso, idSolicitud, randomUUIDString);
                }
                break;
            default:
                break;
        }
        return jsonTemp;
    }

    /******************************************************************************************************************************************************/
    /******************************************************************************************************************************************************/

    @Override
    public byte[] generarComunicadoCarteraConsolidado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolicitud, Map<String, Object> map) {
        logger.info("Inicia generarComunicadoCarteraConsolidado " + "etiquetaPlantillaComunicadoEnum" + etiquetaPlantillaComunicadoEnum + "id" + idSolicitud + "map " + map);
        map.put("solicitud", (int) (long) idSolicitud);
        PlantillaComunicado plantilla = resolverPlantillaCarteraConsolidadoComunicado(etiquetaPlantillaComunicadoEnum, map);
        logger.info("antes de comunicadoPdF");
        byte[] comunicadoPdF = generarPDF(plantilla);
        logger.info("despues de comunicadoPdF");
        return comunicadoPdF;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public PlantillaComunicado resolverPlantillaCarteraConsolidadoComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Map<String, Object> map) {
        logger.info("Inicia resolverComunicadoCarteraConsolidado");

        AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteConsolidadoCarteraSingleton = AuxiliarReporteConsolidadoCarteraSingleton.getSingletonInstance();
        PlantillaComunicado plantillaComunicado = null;
        ParametrosComunicadoDTO parametrosComunicadoDTO = null;
        String ordenamiento = null;
        Long idSolicitud = null;

        if (map != null && !map.isEmpty()) {
            if (map.containsKey("plantillaResuelta")) {
                Map plantillaMap = (Map) map.get("plantillaResuelta");
                plantillaComunicado = new PlantillaComunicado();
                plantillaComunicado.setAsunto(plantillaMap.get("asunto").toString());
                plantillaComunicado.setCuerpo(plantillaMap.get("cuerpo").toString());
                plantillaComunicado.setEncabezado(plantillaMap.get("encabezado").toString());
                plantillaComunicado.setEtiqueta(EtiquetaPlantillaComunicadoEnum.valueOf(plantillaMap.get("etiqueta").toString()));
                plantillaComunicado.setMensaje(plantillaMap.get("mensaje").toString());
                plantillaComunicado.setNombre(plantillaMap.get("nombre").toString());
                plantillaComunicado.setPie(plantillaMap.get("pie").toString());
                plantillaComunicado.setIdPlantillaComunicado(new Long(plantillaMap.get("idPlantillaComunicado").toString()));
            }
            if (map.containsKey("ordenamiento")) {
                ordenamiento = map.get("ordenamiento").toString();
                logger.info("entra ordenamiento");
            }
            if (map.containsKey("solicitud")) {
                idSolicitud = new Long((Integer) map.get("solicitud"));
                logger.info("entra solicitud");
            }
            if (map.containsKey("parametros")) {
                parametrosComunicadoDTO = obtenerParametros((Map) map.get("parametros"));
            }
        }

        // Se consulta la plantilla
        if (plantillaComunicado == null) {
            logger.info("entra plantillaComunicado null");
            plantillaComunicado = auxiliarReporteConsolidadoCarteraSingleton.obtenerPlanillaComunicado(etiquetaPlantillaComunicadoEnum, plantilla);
        }

        // Se consultan las variables
        Map<String, Object> valoresVariable = plantilla.resolverVariablesConsolidadoCartera(etiquetaPlantillaComunicadoEnum, plantillaComunicado.getIdPlantillaComunicado(), parametrosComunicadoDTO, idSolicitud, ordenamiento);
    
        // Se recorre y reemplaza en el mapa consultado
        replaceFromMap(plantillaComunicado, valoresVariable, auxiliarReporteConsolidadoCarteraSingleton);
        auxiliarReporteConsolidadoCarteraSingleton.destruir();

        logger.info("Finaliza resolverComunicadoCarteraConsolidado");
        return plantillaComunicado;
    }

    @SuppressWarnings("rawtypes")
    private ParametrosComunicadoDTO obtenerParametros(Map paramMap) {
        ParametrosComunicadoDTO parametrosComunicadoDTO = new ParametrosComunicadoDTO();

        parametrosComunicadoDTO.setIdArchivoConsumosAnibol(paramMap.get("idArchivoConsumosAnibol") != null ? paramMap.get("idArchivoConsumosAnibol").toString() : null);
        parametrosComunicadoDTO.setIdCartera(paramMap.get("idCartera") != null ? new Long(paramMap.get("idCartera").toString()) : null);
        parametrosComunicadoDTO.setIdPersona(paramMap.get("idPersona") != null ? new Long(paramMap.get("idPersona").toString()) : null);
        parametrosComunicadoDTO.setIdPlantillaComunicado(paramMap.get("idPlantillaComunicado") != null ? new Long(paramMap.get("idPlantillaComunicado").toString()) : null);
        parametrosComunicadoDTO.setNumeroIdentificacion(paramMap.get("numeroIdentificacion") != null ? paramMap.get("numeroIdentificacion").toString() : null);
        parametrosComunicadoDTO.setNumeroRadicacion(paramMap.get("numeroRadicacion") != null ? paramMap.get("numeroRadicacion").toString() : null);

        if (paramMap.get("tipoIdentificacion") != null) {
            TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(paramMap.get("tipoIdentificacion").toString());
            parametrosComunicadoDTO.setTipoIdentificacion(tipoIdentificacionEnum);
        }

        if (paramMap.get("tipoSolicitante") != null) {
            TipoTipoSolicitanteEnum tipoTipoSolicitanteEnum = TipoTipoSolicitanteEnum.valueOf(paramMap.get("tipoSolicitante").toString());
            parametrosComunicadoDTO.setTipoSolicitante(tipoTipoSolicitanteEnum);
        }

        return parametrosComunicadoDTO;
    }

    /**
     * Genera el documento pdf a partir de la plantilla comunicado que llega como parámetro
     *
     * @param plantilla
     * @return
     */
    private byte[] generarPDF(PlantillaComunicado plantilla) {
        try {
            logger.info("Inicia metodo generarPDF " + new ObjectMapper().writeValueAsString(plantilla));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Float> margenesX = new ArrayList<Float>();
        margenesX.add(40f);
        margenesX.add(40f);
        List<Float> margenesY = new ArrayList<Float>();
        margenesY.add(40f);
        margenesY.add(40f);

        InformacionConvertDTO infoConv = new InformacionConvertDTO();
        infoConv.setHtmlHeader(plantilla.getEncabezado());
        infoConv.setHtmlContenido(plantilla.getCuerpo());
        infoConv.setHtmlFooter(plantilla.getPie());
        infoConv.setMargenesx(margenesX);
        infoConv.setMargenesy(margenesY);
        infoConv.setAltura(100f);
        infoConv.setRequiereSello(true);
        logger.info("Antes metodo  convertHTMLtoPDF");
        // Se convierte a pdf la plantilla del comunicado
        byte[] bytes = convertHTMLtoPDF(infoConv);

        if (bytes == null) {
            logger.error("Error la plantilla del comunicado no fue convertida a PDF");
            logger.debug("Finaliza guardarComunicadoSinNotificacionEmail");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
        }

        return bytes;
    }

    private void replaceFromMap(PlantillaComunicado plantillaComunicado, Map<String, Object> replacements, AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteConsolidadoCarteraSingleton) {
        //GLPI 57946 - 2022-06-15 .parellel() removido por separación de hilos
        replacements.entrySet().forEach(e -> {
                    String key = e.getKey();
                    String value = null;
                    String valorImgLogo = null;
                    try{
                        valorImgLogo = auxiliarReporteConsolidadoCarteraSingleton.obtenerValorClaveImagen(e.getKey(), e.getValue() != null ? e.getValue().toString() : "");
                    }catch(Exception er){
                         logger.error("Error al consultar el logo de la CCF error:"+er);
                    }
                 
                    if (valorImgLogo != null) {
                        value = valorImgLogo;
                    } else {
                        value = (e.getValue() != null) ? e.getValue().toString() : ConstantesComunicado.VACIO;
                    }

                    if (!plantillaComunicado.getEtiqueta().equals(EtiquetaPlantillaComunicadoEnum.CONSOLIDADO)) {
                        value = value.replace("&", "&amp;");
                    }

                    if (plantillaComunicado.getAsunto() != null) {
                        plantillaComunicado.setAsunto(replace(plantillaComunicado.getAsunto(), key, value));
                    }
                    if (plantillaComunicado.getCuerpo() != null) {
                        plantillaComunicado.setCuerpo(replace(plantillaComunicado.getCuerpo(), key, value));
                    }
                    if (plantillaComunicado.getEncabezado() != null) {
                        plantillaComunicado.setEncabezado(replace(plantillaComunicado.getEncabezado(), key, value));
                    }
                    if (plantillaComunicado.getMensaje() != null) {
                        plantillaComunicado.setMensaje(replace(plantillaComunicado.getMensaje(), key, value));
                    }
                    if (plantillaComunicado.getPie() != null) {
                        plantillaComunicado.setPie(replace(plantillaComunicado.getPie(), key, value));
                    }
                }
        );
    }

    private String replace(String string, String key, String value) {
        StringBuilder sb = new StringBuilder(string);
        int start = sb.indexOf(key, 0);
        while (start > -1) {
            int end = start + key.length();
            int nextSearchStart = start + value.length();
            sb.replace(start, end, value);
            start = sb.indexOf(key, nextSearchStart);
        }
        return sb.toString();
    }

    @Override
    public String generarYGuardarDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado) {
        String firmaServicio = "generarYGuardarDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        DatoTemporalComunicado datoTemporalComunicado = new DatoTemporalComunicado();
        try {
            datoTemporalComunicado.setIdSolicitud(jsonPayloadDatoTemporalComunicado.getIdSolicitud());
            datoTemporalComunicado.setIdInstanciaProceso(jsonPayloadDatoTemporalComunicado.getIdInstanciaProceso() != null ? jsonPayloadDatoTemporalComunicado.getIdInstanciaProceso().toString() : null);
            datoTemporalComunicado.setIdTarea(jsonPayloadDatoTemporalComunicado.getIdTarea());
            datoTemporalComunicado.setJsonPayload(generarJsonPayloadDatoTemporalComunicado(jsonPayloadDatoTemporalComunicado));
            guardarDatoTemporalComunicado(datoTemporalComunicado);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return datoTemporalComunicado.getJsonPayload();
    }

    /**
     * Método encagardo de generar el json del dato temporal del comunicado
     *
     * @param jsonPayloadDatoTemporalComunicadoDTO Objeto con toda la información necesaria para crear el dato temporal del comunicado
     * @return jsonPayload del dato temporal del comunicado
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    private String generarJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO) {
        String firmaServicio = " generarJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Map<String, Object>> plantillas = agregarInformacionCompartidaPlantillas(generarPlantillasJsonPayloadDatoTemporalComunicado(jsonPayloadDatoTemporalComunicadoDTO), jsonPayloadDatoTemporalComunicadoDTO.getInformacionCompartidaPlantillas());
        Map<String, Object> contexto = generarContextoJsonPayloadDatoTemporalComunicado(jsonPayloadDatoTemporalComunicadoDTO);
        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put(ConstantesComunes.HU331, plantillas);
        jsonPayload.put(ConstantesComunes.CONTEXTO, contexto);
        jsonPayload.put(ConstantesComunes.UUID, UUID.randomUUID().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(jsonPayload);
        } catch (JsonProcessingException e) {
            logger.error("Ocurrió un error al generar el jsonPayload del dato temporal del comunicado: " + firmaServicio + e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return json;
    }

    /**
     * Método encagardo agregar información compartida en todas las plantillas de los comunicados a enviar
     *
     * @param plantillas                      lista de plantillas
     * @param informacionCompartidaPlantillas Información a agregar
     * @return Lista que contiene la lista de plantillas con la información compartida
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    private List<Map<String, Object>> agregarInformacionCompartidaPlantillas(List<Map<String, Object>> plantillas, Map<String, Object> informacionCompartidaPlantillas) {
        String firmaServicio = "agregarInformacionCompartidaPlantillas(List<Map<String, Object>> plantillas, Map<String, Object> informacionCompartidaPlantillas) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        for (Map<String, Object> plantilla : plantillas) {
            informacionCompartidaPlantillas.forEach((llave, valor) -> plantilla.put(llave, valor));
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return plantillas;
    }

    /**
     * Método encargado de generar las plantillas del JSON del dato temporal del comunicado
     *
     * @param jsonPayloadDatoTemporalComunicadoDTO Objeto que contiene las plantillas a agregar al JSON del dato temporal del comunicado
     * @return Lista que contiene la lista de plantillas
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    private List<Map<String, Object>> generarPlantillasJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO) {
        String firmaServicio = "generarPlantillasJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Map<String, Object>> plantillas = new ArrayList<>();
        if (jsonPayloadDatoTemporalComunicadoDTO != null) {
            for (PlantillaComunicadoDTO plantilla : jsonPayloadDatoTemporalComunicadoDTO.getPlantillas()) {
                Map<String, Object> plantillaGenerada = new HashMap<>();
                if (plantilla.getPlantilla() != null) {
                    plantillaGenerada.put("plantilla", plantilla.getPlantilla().getEtiqueta());
                    plantillaGenerada.put("processName", plantilla.getPlantilla().getProceso());
                }
                if (plantilla.getUrlRedireccion() != null) {
                    plantillaGenerada.put("urlRedirect", plantilla.getUrlRedireccion());
                }
                if (plantilla.getAvanzarTarea() != null) {
                    plantillaGenerada.put("avanzarTarea", plantilla.getAvanzarTarea());
                }
                if (plantilla.getInformacionAdicional() != null) {
                    plantilla.getInformacionAdicional().forEach((llave, valor) -> plantillaGenerada.put(llave, valor));
                }
                plantillas.add(plantillaGenerada);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return plantillas;
    }

    /**
     * Método encargado de generar el contexto del JSON del dato temporal del comunicado
     *
     * @param jsonPayloadDatoTemporalComunicadoDTO Objeto con toda la información necesaria para crear el contexto del dato temporal del comunicado
     * @return Mapa con el contexto del dato temporal del comunicado
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    private Map<String, Object> generarContextoJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado) {
        String firmaServicio = "generarContextoJsonPayloadDatoTemporalComunicado(JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicado) ";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, Object> contextoGenerado = new HashMap<>();
        if (jsonPayloadDatoTemporalComunicado != null) {
            ContextoComunicadoDTO contexto = jsonPayloadDatoTemporalComunicado.getContexto();
            if (contexto.getIdInstanciaProceso() != null) {
                contextoGenerado.put(ConstantesComunes.ID_INSTANCIA_PROCESO, contexto.getIdInstanciaProceso());
            }
            if (contexto.getIdSolicitud() != null) {
                contextoGenerado.put(ConstantesComunes.ID_SOLICITUD, contexto.getIdSolicitud());
            }
            if (contexto.getInformacionAdicional() != null) {
                contexto.getInformacionAdicional().forEach((llave, valor) -> contextoGenerado.put(llave, valor));
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return contextoGenerado;
    }
    /**INICIA COMUNICADOS PRESCRIPCION CUOTA MONETARIO GLPI 45388 */
    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.ComunicadosService#guardarObtenerComunicadoECM(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     * com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum, com.asopagos.notificaciones.dto.ParametrosComunicadoDTO)
     */
    @Override
    public InformacionArchivoDTO guardarObtenerComunicadoPrescripcion(EtiquetaPlantillaComunicadoEnum plantilla,
                                                                      Long idCuentaAdmonSubsidio) {
        logger.info("**__**Inicia guardarObtenerComunicadoPrescripcion idCuentaAdmonSubsidio" + idCuentaAdmonSubsidio);
        String identificador = "";
        PlantillaComunicado plaCom = resolverPlantillaVariablesComunicadoPrivate(plantilla, null, idCuentaAdmonSubsidio, null, null);
        // PlantillaComunicado plaCom = resolverPlantillaConstantesComunicado(plantilla, params);
        InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error: la plantilla del comunicado no tiene datos");
            logger.debug("La plantilla del comunicado no tiene datos");
        } else {
            List<Float> margenesX = new ArrayList<Float>();
            margenesX.add(56f);
            margenesX.add(56f);
            List<Float> margenesY = new ArrayList<Float>();
            margenesY.add(40f);
            margenesY.add(40f);

            InformacionConvertDTO infoConv = new InformacionConvertDTO();
            infoConv.setHtmlHeader(plaCom.getEncabezado());
            infoConv.setHtmlContenido(plaCom.getCuerpo());
            infoConv.setHtmlFooter(plaCom.getPie());
            infoConv.setMargenesx(margenesX);
            infoConv.setMargenesy(margenesY);
            infoConv.setAltura(100f);
            infoConv.setRequiereSello(true);

            // Se convierte a pdf la plantilla del comunicado
            byte[] bytes = convertHTMLtoPDF(infoConv);

            if (bytes == null) {
                logger.error("Error la plantilla del comunicado no fue convertida a PDF");
                logger.debug("Finaliza guardarComunicadoSinNotificacionEmail");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
            }

            infoArch.setDataFile(bytes);
            infoArch.setDescription("Comunicado");

            // Se agrega nombre del documento
            infoArch.setDocName(plantilla + identificador + "_comunicado.pdf");
            infoArch.setFileName(plantilla + identificador + "_comunicado.pdf");
            infoArch.setFileType("application/pdf");
            infoArch.setProcessName("COMUNICADO_PRESCRIPCION");
            infoArch.setIdInstanciaProceso(null);

            // Se realiza el upload del archivo al ECM
            infoArch.setIdentificadorDocumento(almacenarArchivo(infoArch));
        }
        logger.debug("Finaliza guardarComunicadoSinNotificacionEmail");
        return infoArch;
    }

    /**FIN COMUNICADOS PRESCRIPCION CUOTA MONETARIO GLPI 45388 */
}
