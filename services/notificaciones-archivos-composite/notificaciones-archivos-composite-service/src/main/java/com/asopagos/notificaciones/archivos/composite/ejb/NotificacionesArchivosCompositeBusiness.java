package com.asopagos.notificaciones.archivos.composite.ejb;

import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ConvertHTMLtoPDF;
import com.asopagos.archivos.clients.GuardarRespuestaECMExterno;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.archivos.dto.RespuestaECMExternoDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.comunicados.clients.ConstruirPersistirComunicado;
import com.asopagos.comunicados.clients.ResolverPlantillaConstantesComunicado;
import com.asopagos.comunicados.clients.ResolverPlantillaVariablesComunicado;
import com.asopagos.comunicados.clients.ResolverPlantillaVariablesComunicadoPorSolicitud;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.MetadataArchivoDTO;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.service.NotificacionesArchivosCompositeService;
import com.asopagos.notificaciones.clients.BuscarTipoTransaccionSolicitud;
import com.asopagos.notificaciones.clients.EnviarCorreo;
import com.asopagos.notificaciones.clients.EnviarCorreoParametrizado;
import com.asopagos.notificaciones.clients.EnviarMultiplesCorreosPorConexion;
import com.asopagos.notificaciones.dto.ArchivoAdjuntoDTO;
import com.asopagos.notificaciones.dto.ComunicadoPersistenciaDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.ValidacionCamposUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;
import com.asopagos.notificaciones.clients.EnviarCorreoParametrizadoCartera;
/**
 * Servicio de NotificacionesArchivosCompositeService
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
@Stateless
public class NotificacionesArchivosCompositeBusiness implements NotificacionesArchivosCompositeService {

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "usuarios_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(NotificacionesArchivosCompositeBusiness.class);
    
    /**
     * Cantidad de notificaciones a procesar por hilo
     */
    private final Integer NOTIFICACIONES_POR_HILO = 20;
    
    /**
     * Cantidad de notificaciones a procesar por grupo en paralelo.
     * Permite reducir los llamados al productor de la cola de correos,
     * disminuyendo con esto la cantidad de peticiones REST 
     */
    private final Integer TAMAÑO_PAQUETE_NOTIFICACIONES_PARALELAS = 1000;
    
    /**
     * Formato de fecha estandar para el componente Gson
     */
    private static final String DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    
    @Resource
    private ManagedExecutorService managedExecutorService;
    
    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.archivos.composite.service.
     *      NotificacionesArchivosCompositeService#enviarNotificacionAdjuntos(com.
     *      asopagos.notificaciones.dto.NotificacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void enviarNotificacionAdjuntos(NotificacionDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarNotificacionAdjuntos(NotificacionDTO, Long)");

        try {
            ObtenerArchivo consultarArchivo;
            InformacionArchivoDTO archivo = new InformacionArchivoDTO();
            ArchivoAdjuntoDTO attachFile;
            if (notificacion.getArchivosAdjuntosIds() != null) {
                for (String archivoId : notificacion.getArchivosAdjuntosIds()) {

                    consultarArchivo = new ObtenerArchivo(archivoId);
                    consultarArchivo.execute();
                    archivo = (InformacionArchivoDTO) consultarArchivo.getResult();
                    attachFile = new ArchivoAdjuntoDTO();
                    attachFile.setContent(archivo.getDataFile());
                    attachFile.setFileName(archivo.getFileName());
                    attachFile.setFileType(archivo.getFileType());
                    attachFile.setIntegratedImage(false);
                    attachFile.setNameDate(false);

                    if (notificacion.getArchivosAdjuntos() == null) {
                        notificacion.setArchivosAdjuntos(new ArrayList<ArchivoAdjuntoDTO>());
                    }
                    notificacion.getArchivosAdjuntos().add(attachFile);
                }
            }
        } catch (Exception e) {
            logger.error("Finaliza enviarNotificacionAdjuntos(NotificacionDTO, Long): Error al procesar el adjunto", e);
            logger.debug("Finaliza enviarNotificacionAdjuntos(NotificacionDTO, Long): Error al procesar el adjunto");
            if (e instanceof TechnicalException) {
                throw e;
            }
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        try {
            EnviarCorreo enviarCorreo = new EnviarCorreo(notificacion);
            enviarCorreo.execute();
        } catch (Exception e) {
            logger.error("Finaliza enviarNotificacionAdjuntos(NotificacionDTO, Long): Error al procesarla notificación", e);
            logger.debug("Finaliza enviarNotificacionAdjuntos(NotificacionDTO, Long): Error al procesarla notificación");
            if (e instanceof TechnicalException) {
                throw e;
            }
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        logger.debug("Finaliza enviarCorreo(NotificacionDTO)");
    }

    @Asynchronous
    public void enviarNotificacionComunicadoAsincrono(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        enviarNotificacionComunicado(notificacion, userDTO);
    }

/** 
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.archivos.composite.service.
     *      NotificacionesArchivosCompositeService#enviarNotificacionComunicado(
     *      com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    public void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarNotificacionComunicado(NotificacionParametrizadaDTO)");
        
        if (!EtiquetaPlantillaComunicadoEnum.COM_PAG_SUB_INC_ARC_CON.equals(notificacion.getEtiquetaPlantillaComunicado())) {
            construirNotificacion(notificacion);
        }else
            construirNotificacionAdjuntoTxt(notificacion);

        try {
            // se envía el correo con el comunicado como un archivo adjunto
            EnviarCorreoParametrizado enviarCorreo = new EnviarCorreoParametrizado(notificacion);
            enviarCorreo.execute();
        } catch (Exception e) {
            logger.error("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO): Error al procesarla notificación", e);
            logger.debug("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO): Error al procesarla notificación");
            if (e instanceof TechnicalException) {
                throw e;
            }
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        logger.debug("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO)");
    }
     /* Comentado por glpi 64983 ya que por el flujo de notificacion en SendNotificacionesBusiness metodo enviarEmailImplementacion ya los inserta en comunicado, no es necesario insertar ConstruirPersistirComunicado
     se deja por si en algun momento se necesita persistir en comunicado, el codigo sirve como ejemplo 
      public void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.info("Inicia enviarNotificacionComunicado(NotificacionParametrizadaDTO) - "+notificacion.getEtiquetaPlantillaComunicado());
        NotificacionEnviada notEnv = new NotificacionEnviada();
        notEnv.setFechaEnvio(Calendar.getInstance().getTime());
        notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIADA);
        notEnv.setIdSedeCajaCompensacion(
                userDTO.getSedeCajaCompensacion() != null ? Long.valueOf(userDTO.getSedeCajaCompensacion()) : null);
        notEnv.setProcesoEvento(notificacion.getProcesoEvento());
        notEnv.setRemitente(notificacion.getRemitente());
        if (!EtiquetaPlantillaComunicadoEnum.COM_PAG_SUB_INC_ARC_CON.equals(notificacion.getEtiquetaPlantillaComunicado())) {
            construirNotificacion(notificacion);
        }else
            construirNotificacionAdjuntoTxt(notificacion);

        try {
            // se envía el correo con el comunicado como un archivo adjunto
            EnviarCorreoParametrizado enviarCorreo = new EnviarCorreoParametrizado(notificacion); 
            enviarCorreo.execute();
            if (EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA.equals(notificacion.getEtiquetaPlantillaComunicado()) ||
                    EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA.equals(notificacion.getEtiquetaPlantillaComunicado()) ||
                    EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA.equals(notificacion.getEtiquetaPlantillaComunicado())) {
                PlantillaComunicado plantilla = new PlantillaComunicado();
                plantilla.setEtiqueta(notificacion.getEtiquetaPlantillaComunicado());
                NotificacionParametrizadaDTO notifiP = (NotificacionParametrizadaDTO) notificacion;
                ComunicadoPersistenciaDTO comunicadoPersistencia = new ComunicadoPersistenciaDTO(notifiP, notEnv, plantilla);
                logger.info("**__**Ingreso  - ConstruirPersistirComunicado getEtiquetaPlantillaComunicado: "+notificacion.getEtiquetaPlantillaComunicado());
                ConstruirPersistirComunicado comunicadoSrv = new ConstruirPersistirComunicado(comunicadoPersistencia);
                comunicadoSrv.execute();
            }
        } catch (Exception e) {
            logger.error("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO): Error al procesarla notificación", e);
            logger.debug("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO): Error al procesarla notificación");
            if (e instanceof TechnicalException) {
                throw e;
            }
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        logger.debug("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO)");
    }*/

    
    
    /**
     * Metodo encargado de procesar la información (variables, constantes y PDF) de una lista de notificaciones
     * Se convertirá en una tarea a ejecutar en paralelo.
     * @param notificaciones
     * @return Lista de notificaciones 
     */
    private List<NotificacionParametrizadaDTO> procesarNotificacion(List<NotificacionParametrizadaDTO> notificaciones){
    	for (NotificacionParametrizadaDTO notificacion : notificaciones) {
    		construirNotificacion(notificacion);
    	}
    	return notificaciones;
    }
    /**
     * Metodo que envía una lista de notificaciones al productor de la cola
     * controlando la cantidad de llamados REST a dicho productor (TAMAÑO_PAQUETE_NOTIFICACIONES_PARALELAS)
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.archivos.composite.service.NotificacionesArchivosCompositeService#enviarListaNotificacionComunicados(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    public void enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO> notificaciones, UserDTO userDTO) {
        logger.debug("Inicia enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>)");
        List<NotificacionParametrizadaDTO> notificacionesTemp = new ArrayList<>();
        int count = 0;
        for (NotificacionParametrizadaDTO notificacionParametrizadaDTO : notificaciones) {
			notificacionesTemp.add(notificacionParametrizadaDTO);
			count++;
			//Cuando el tamaño de la lista de notificaciones a procesar alcance el valor parametrizado
			//se envía para su procesamiento en paralelo.
			if (count == TAMAÑO_PAQUETE_NOTIFICACIONES_PARALELAS) {
				//Se envía la lista de notificaciones para su procesamiento.
				List<NotificacionParametrizadaDTO> notificacionesACola = procesarNotificacionesParalelo(notificacionesTemp);
				try {
		            // se envía la lista de notificaciones procesadas al productor
		            EnviarMultiplesCorreosPorConexion enviarCorreos = new EnviarMultiplesCorreosPorConexion(notificacionesACola);
		            enviarCorreos.execute();
		            notificacionesTemp = new ArrayList<>();
		            count = 0;
		        }catch (TechnicalException te){
		        	logger.error(
		                    "Finaliza enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", te);
		        	throw te;
		        }catch (Exception e) {
		            logger.error(
		                    "Finaliza enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", e);
		            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		        }
			}
		}
        //Se evalua si el paquete final de notificaciones no acaparó el tamañado predeterminado del paquete (TAMAÑO_PAQUETE_NOTIFICACIONES_PARALELAS)
        //para asi procesarlas y enviarlas al productor de la cola.
        if (count > 0) {
			List<NotificacionParametrizadaDTO> notificacionesACola = procesarNotificacionesParalelo(notificacionesTemp);
			try {
	            // se envía la lista de notificaciones procesadas al productor
	            EnviarMultiplesCorreosPorConexion enviarCorreos = new EnviarMultiplesCorreosPorConexion(notificacionesACola);
	            enviarCorreos.execute();
	        }catch (TechnicalException te){
	        	logger.error(
	                    "Finaliza enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", te);
	        	throw te;
	        }catch (Exception e) {
	            logger.error(
	                    "Finaliza enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", e);
	            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
	        }
		}
        logger.debug("Finaliza enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO>)");
    }
    
    /**
     * Metodo que procesa (resuelve constantes, variables y documentos adjuntos) una lista de notificaciones en paralelo
     * @param notificaciones
     * @return
     */
	private List<NotificacionParametrizadaDTO> procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO> notificaciones) {
	    logger.debug("Incia procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>)");
		int count = 0;
        List<NotificacionParametrizadaDTO> tempNotificaciones = new ArrayList<>();
        List<Callable<List<NotificacionParametrizadaDTO>>> tareasParalelas = new LinkedList<>();
        for (NotificacionParametrizadaDTO notificacion : notificaciones) {
        	count++;
        	tempNotificaciones.add(notificacion);
        	//Se crea una tarea nueva con la cantidad de notificaciones indicada por hilo
        	if (count == NOTIFICACIONES_POR_HILO) {
        		final List<NotificacionParametrizadaDTO> finalNotificaciones = tempNotificaciones;
        		Callable<List<NotificacionParametrizadaDTO>> parallelTask = () -> {
        			return procesarNotificacion(finalNotificaciones);
                };
                //Se agrega la tarea creada a la lista de tareas que se ejecutarén en paralelo
				tareasParalelas.add(parallelTask);
				count = 0;
				tempNotificaciones = new ArrayList<>();
			}
        }
        
        //Se verifica el grupo de notificaciones (de existir) que no acaparó la cantidad necesaria (NOTIFICACIONES_POR_HILO)
        //para crear una nueva tarea. (Este hilo tendra entonces, menos notificaciones a procesar)
        if (count > 0) {
        	//Se crea la tarea con las notificaciones restantes
        	final List<NotificacionParametrizadaDTO> finalNotificaciones = tempNotificaciones;
    		Callable<List<NotificacionParametrizadaDTO>> parallelTask = () -> {
    			return procesarNotificacion(finalNotificaciones);
            };
          //Se agrega la tarea creada a la lista de tareas que se ejecutarén en paralelo
			tareasParalelas.add(parallelTask);
		}
        List<Future<List<NotificacionParametrizadaDTO>>> resultadosFuturos =  new ArrayList<>();
        //El M.E.S. invoca la lista de tareas que se ejecutaran en paralelo 
        try{
        resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
        }catch (InterruptedException e){
        	logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesar la notificación", e);
        }
        List<NotificacionParametrizadaDTO> outDTO = new ArrayList<>();
        for (Future<List<NotificacionParametrizadaDTO>> future : resultadosFuturos) {
			// Se obtiene el resultado de la ejecución paralela de cada tarea
			try {
				outDTO.addAll(future.get());
			} catch (InterruptedException e) {
				logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", e);
			} catch (ExecutionException e) {
				logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesarla notificación", e);
			}
        }
        logger.debug("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>)");
        return outDTO;
	}

    /**
     * Método que se encarga de contruir una notificación
     * 
     * @param notificacion
     */
    private void construirNotificacion(NotificacionParametrizadaDTO notificacion) {
    	logger.debug("Inicia construirNotificacion(NotificacionParametrizadaDTO)");
    	
    	//Se adjuntan el/los documentos que vienen armados desde la pantalla
        if (Boolean.TRUE.equals(notificacion.getComunicadoEditado())) {
            ObtenerArchivo consultarArchivo;
            InformacionArchivoDTO archivo = new InformacionArchivoDTO();
            ArchivoAdjuntoDTO attachFile;
            if (notificacion.getArchivosAdjuntosIds() != null) {
                for (String archivoId : notificacion.getArchivosAdjuntosIds()) {

                    consultarArchivo = new ObtenerArchivo(archivoId);
                    consultarArchivo.execute();
                    archivo = (InformacionArchivoDTO) consultarArchivo.getResult();
                    attachFile = new ArchivoAdjuntoDTO();
                    attachFile.setContent(archivo.getDataFile());
                    attachFile.setFileName(archivo.getFileName());
                    attachFile.setFileType(archivo.getFileType());
                    attachFile.setIntegratedImage(false);
                    attachFile.setNameDate(false);

                    if (notificacion.getArchivosAdjuntos() == null) {
                        notificacion.setArchivosAdjuntos(new ArrayList<ArchivoAdjuntoDTO>());
                    }
                    notificacion.getArchivosAdjuntos().add(attachFile);
                }
            }
            logger.debug("Finaliza construirNotificacion(NotificacionParametrizadaDTO)");
            return;
        }
    	
        // se validan los datos mínimos y obligatorios
        validarParametros(notificacion);

        // se consulta el comunicado con todas sus variables resueltas tanto en
        // el archivo ajunto como en el mensaje del correo
        PlantillaComunicado plaCom = construirPlantillaComunicado(notificacion);
        
        if (notificacion.getParams() == null || !notificacion.getParams().containsKey("enviarSinAlmacenarComunicado")
                || !Boolean.parseBoolean(notificacion.getParams().get("enviarSinAlmacenarComunicado"))) {
            List<ArchivoAdjuntoDTO> attachFiles = crearAdjuntosNotificacion(notificacion, plaCom);
            notificacion.setArchivosAdjuntos(attachFiles);
        }

        notificacion.setAsunto(plaCom.getAsunto());
        notificacion.setMensaje(plaCom.getMensaje());
        // el destinatario debe venir desde la petición
        logger.debug("Finaliza construirNotificacion(NotificacionParametrizadaDTO)");
    }

    /**
     * Método que se encarga de cosumir el cliente que consulta la planilla con
     * las variables resueltas
     * 
     * @param etiquetaPlantillaComunicadoEnum
     * @param idInstancia
     * @param map
     */
    private PlantillaComunicado resolverPlantillaVariablesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            Long idInstancia, Map<String, Object> map) {
        ResolverPlantillaVariablesComunicado resolverCom = new ResolverPlantillaVariablesComunicado(etiquetaPlantillaComunicadoEnum,
                idInstancia, map);
        PlantillaComunicado plaCom = new PlantillaComunicado();
        resolverCom.execute();
        plaCom = (PlantillaComunicado) resolverCom.getResult();
        return plaCom;
    }

    /**
     * Método que se encarga de cosumir el cliente que consulta la planilla con
     * las variables resueltas
     * 
     * @param etiquetaPlantillaComunicadoEnum
     * @param idInstancia
     * @param map
     */
    private PlantillaComunicado resolverPlantillaVariablesComunicadoPorSolicitud(
            EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolicitud, Map<String, Object> map) {
    	logger.debug("Inicia resolverPlantillaVariablesComunicadoPorSolicitud(EtiquetaPlantillaComunicadoEnum,Long,Map<String, Object>)");
        ResolverPlantillaVariablesComunicadoPorSolicitud resolverCom = new ResolverPlantillaVariablesComunicadoPorSolicitud(idSolicitud,
                etiquetaPlantillaComunicadoEnum, map);
        PlantillaComunicado plaCom = new PlantillaComunicado();
        resolverCom.execute();
        plaCom = (PlantillaComunicado) resolverCom.getResult();
        logger.debug("Finaliza resolverPlantillaVariablesComunicadoPorSolicitud(EtiquetaPlantillaComunicadoEnum,Long,Map<String, Object>)");
        return plaCom;
    }

    /**
     * Método que se encarga de cosumir el cliente convierte el html a pdf
     * 
     * @param objInformacionConvertDTO
     * @return
     */
    private byte[] convertHTMLtoPDF(InformacionConvertDTO objInformacionConvertDTO) {
        ConvertHTMLtoPDF convertHTMLtoPDF = new ConvertHTMLtoPDF(objInformacionConvertDTO);
        convertHTMLtoPDF.execute();
        byte[] bytes = (byte[]) convertHTMLtoPDF.getResult();
        return bytes;
    }

    /**
     * 
     * @param infoFile
     * @return
     */
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();

        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        return idECM.toString();
    }

    /**
     * Método que se encarga de cosumir el cliente que consulta la planilla con
     * las variables resueltas
     * 
     * @param etiquetaPlantillaComunicadoEnum
     * @param map
     */
    private PlantillaComunicado resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            ParametrosComunicadoDTO parametrosComunicadoDTO) {
    	logger.debug("Inicia resolverPlantillaConstantesComunicado(etiquetaPlantillaComunicadoEnum,ParametrosComunicadoDTO)");
        ResolverPlantillaConstantesComunicado resolverCom = new ResolverPlantillaConstantesComunicado(etiquetaPlantillaComunicadoEnum,
                parametrosComunicadoDTO);
        PlantillaComunicado plaCom = new PlantillaComunicado();
        resolverCom.execute();
        plaCom = (PlantillaComunicado) resolverCom.getResult();
        logger.debug("Finaliza resolverPlantillaConstantesComunicado(etiquetaPlantillaComunicadoEnum,ParametrosComunicadoDTO)");
        return plaCom;
    }

    /**
     * Se encarga de validar si se especificaron los datos mínimos de la
     * notificación
     * 
     * @param notificacion
     */
    private void validarParametros(NotificacionParametrizadaDTO notificacion) {
        if ((notificacion.getProcesoEvento() == null && !notificacion.isReplantearDestinatarioTO())
                || notificacion.getEtiquetaPlantillaComunicado() == null) {
            logger.error("Error no se indicaron todos los parámetros necesarios");
            logger.debug("Finaliza enviarNotificacionComunicado(NotificacionParametrizadaDTO)");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
    }

    /**
     * Se encarga de convertir los parametros de notificación en parametros de
     * plantilla comunicados
     * 
     * @param notificacion
     * @return
     */
    private Map<String, Object> transformarParametrosNotificacion(NotificacionParametrizadaDTO notificacion) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (notificacion.getParams() != null) {
            for (Entry<String, String> entry : notificacion.getParams().entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    /**
     * Método que se encarga de construir la plantilla y resolver sus variables
     * 
     * @param notificacion
     * @return plaCom
     */
    private PlantillaComunicado construirPlantillaComunicado(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia construirPlantillaComunicado(NotificacionParametrizadaDTO)");
        PlantillaComunicado plaCom;
        Map<String, Object> params = transformarParametrosNotificacion(notificacion);
        if (notificacion.getIdInstanciaProceso() != null) {
            // de acuerdo al id de instancia del proceso (bpm)
            Long idInstanciaProceso = new Long(notificacion.getIdInstanciaProceso());
            plaCom = resolverPlantillaVariablesComunicado(notificacion.getEtiquetaPlantillaComunicado(), idInstanciaProceso, params);
        }
        else if (notificacion.getIdSolicitud() != null) {
            // de acuerdo al id de la solicitud
            plaCom = resolverPlantillaVariablesComunicadoPorSolicitud(notificacion.getEtiquetaPlantillaComunicado(),
                    notificacion.getIdSolicitud(), params);
        }
        else {
            ParametrosComunicadoDTO parametroComunicado = notificacion.getParametros();
            if (parametroComunicado == null) {
                parametroComunicado = new ParametrosComunicadoDTO();
            }

            parametroComunicado.setParams(params);

            if (params.containsKey(ParametrosComunicadoDTO.TIPO_IDENTIFICACION)) {
                parametroComunicado.setTipoIdentificacion(
                        TipoIdentificacionEnum.valueOf(params.get(ParametrosComunicadoDTO.TIPO_IDENTIFICACION).toString()));
            }

            if (params.containsKey(ParametrosComunicadoDTO.NUMERO_IDENTIFICACION)) {
                parametroComunicado.setNumeroIdentificacion(params.get(ParametrosComunicadoDTO.NUMERO_IDENTIFICACION).toString());
            }

            if (params.containsKey(ParametrosComunicadoDTO.ID_PERSONA)) {
                parametroComunicado.setIdPersona(new Long(params.get(ParametrosComunicadoDTO.ID_PERSONA).toString()));
            }

            if (params.containsKey(ParametrosComunicadoDTO.NUMERO_RADICACION)) {
                parametroComunicado.setNumeroRadicacion(params.get(ParametrosComunicadoDTO.NUMERO_RADICACION).toString());
            }

            plaCom = resolverPlantillaConstantesComunicado(notificacion.getEtiquetaPlantillaComunicado(), parametroComunicado);
        }
        logger.debug("Finaliza construirPlantillaComunicado(NotificacionParametrizadaDTO)");
        return plaCom;
    }

    /**
     * Método que construye InformacionConvertDTO objeto para convertir a pdf un comunicado
     * @param plaCom
     * @return
     */ 
    private byte[] construirPDF(PlantillaComunicado plaCom) {
        logger.info("Inicia construirPDF(PlantillaComunicado)");
        List<Float> magenesX = new ArrayList<Float>();
        magenesX.add(56f);
        magenesX.add(56f);
        List<Float> magenesY = new ArrayList<Float>();
        magenesY.add(40f);
        magenesY.add(40f);

        InformacionConvertDTO infoConv = new InformacionConvertDTO();
        infoConv.setHtmlHeader(plaCom.getEncabezado());
        infoConv.setHtmlContenido(plaCom.getCuerpo());
        infoConv.setHtmlFooter(plaCom.getPie());
        // infoConv.setHtmlSello(htmlSello);
        infoConv.setMargenesx(magenesX);
        infoConv.setMargenesy(magenesY);
        infoConv.setAltura(100f);
        infoConv.setRequiereSello(true);
        logger.info("**__**construirPDF convertHTMLtoPDF-> "+plaCom.getEncabezado() );
        byte[] bytes = convertHTMLtoPDF(infoConv);
        if (bytes == null) {
            logger.error("Error la plantilla del comunicado no fue convertida a PDF");
            logger.debug("Finaliza construirPDF(PlantillaComunicado)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
        }
        logger.debug("Finaliza construirPDF(PlantillaComunicado)");
        return bytes;
    }

    /**
     * Método que se encarga de almacenar el comunicocado en pfd en el ECM y
     * crear el objeto de archivo adjunto
     * 
     * @param notificacion
     * @param bytes
     * @return
     */
    private ArchivoAdjuntoDTO crearArchivoAdjunto(NotificacionParametrizadaDTO notificacion, byte[] bytes) {
        logger.debug("Inicio crearArchivoAdjunto(almacenarArchivoECM(NotificacionParametrizadaDTO, byte[])");
        InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
        infoArch.setDataFile(bytes);
        infoArch.setDescription("Comunicado");
        /*
         * se Agrega id de solicitud para manejar versionamiento diferente por
         * cada comunicado.
         */
        if (EtiquetaPlantillaComunicadoEnum.REC_PLZ_LMT_PAG_PER.equals(notificacion.getEtiquetaPlantillaComunicado())
                || EtiquetaPlantillaComunicadoEnum.REC_PLZ_LMT_PAG.equals(notificacion.getEtiquetaPlantillaComunicado())) {
            infoArch.setDocName(
                    notificacion.getEtiquetaPlantillaComunicado() + "_" + notificacion.getIdSolicitud().toString() + "_comunicado.pdf");
            infoArch.setFileName(
                    notificacion.getEtiquetaPlantillaComunicado() + "_" + notificacion.getIdSolicitud().toString() + "_comunicado.pdf");
        }
        else {
            infoArch.setDocName(notificacion.getEtiquetaPlantillaComunicado() + "_comunicado.pdf");
            infoArch.setFileName(notificacion.getEtiquetaPlantillaComunicado() + "_comunicado.pdf");
        }
        infoArch.setFileType("application/pdf");
        infoArch.setProcessName(notificacion.getProcesoEvento());
        infoArch.setIdInstanciaProceso(notificacion.getIdInstanciaProceso());
        // se realiza el upload del archivo al ECM
        String idECM = almacenarArchivo(infoArch);

        // se crae el achivo adjunto
        ArchivoAdjuntoDTO attachFile = new ArchivoAdjuntoDTO();
        attachFile.setContent(infoArch.getDataFile());
        attachFile.setFileName(infoArch.getFileName());
        attachFile.setFileType(infoArch.getFileType());
        attachFile.setIntegratedImage(false);
        attachFile.setNameDate(false);
        attachFile.setIdECM(idECM);
        logger.debug("Finaliza crearArchivoAdjunto(almacenarArchivoECM(NotificacionParametrizadaDTO, byte[])");
        return attachFile;
    }

    /**
     * Se encarga de crear la lista de los archivos adjuntos
     * 
     * @param notificacion
     * @param plaCom
     * @return
     */
    private List<ArchivoAdjuntoDTO> crearAdjuntosNotificacion(NotificacionParametrizadaDTO notificacion, PlantillaComunicado plaCom) {
        List<ArchivoAdjuntoDTO> attachFiles = new ArrayList<ArchivoAdjuntoDTO>();
        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error la plantilla del comunicado no tiene datos");
            logger.debug("Se asume que no tiene documento adjunto");
        }
        else {
            if (!notificacion.isNoEnviarAdjunto()) {
                // se convierte a pdf la plantilla de comunicado
                byte[] bytes = construirPDF(plaCom);

                // se almacena el pdf en el ECM
                ArchivoAdjuntoDTO attachFile = crearArchivoAdjunto(notificacion, bytes);
                attachFiles.add(attachFile);
            }
        }
        return attachFiles;
    }

    @Override
    public void buscarTransaccionEnviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        try{
            BuscarTipoTransaccionSolicitud buscarTipoTransaccionSolicitud = new BuscarTipoTransaccionSolicitud(notificacion.getIdSolicitud());
            buscarTipoTransaccionSolicitud.execute();
            TipoTransaccionEnum tipoTx = buscarTipoTransaccionSolicitud.getResult();
            notificacion.setTipoTx(tipoTx);
            enviarNotificacionComunicado(notificacion, userDTO);
        }catch(NoResultException nre){
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.archivos.composite.service.NotificacionesArchivosCompositeService#envioExitosoComunicados(java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Boolean envioExitosoComunicados(List<NotificacionParametrizadaDTO> notificaciones, UserDTO userDTO) {
        logger.info("Inicio de método envioExitosoComunicados(List<NotificacionParametrizadaDTO>,UserDTO)");
        Boolean envioExitoso = Boolean.TRUE;
        if ((notificaciones.isEmpty()) || (!notificaciones.isEmpty() && !notificaciones.get(0).getEnvioExitoso())) {
            envioExitoso = Boolean.FALSE;
            return envioExitoso;
        }
        for (NotificacionParametrizadaDTO notificacion : notificaciones) {
            if (destinatariosValidos(notificacion)) {
                try {
                    enviarNotificacionComunicado(notificacion, userDTO);
                } catch (Exception e) {
                    envioExitoso = Boolean.FALSE;
                }
            }else{
                envioExitoso = Boolean.FALSE;
            }
        }
        logger.info("Fin de método envioExitosoComunicados(List<NotificacionParametrizadaDTO>,UserDTO) envioExitoso: "+envioExitoso);
        return envioExitoso;
    }

    public ResultadoEnvioComunicadoCarteraDTO envioExitosoComunicadosCartera(List<NotificacionParametrizadaDTO> notificaciones) {
        logger.info("Inicio de método envioExitosoComunicadosCartera(List<NotificacionParametrizadaDTO>, UserDTO)");
        
        ResultadoEnvioComunicadoCarteraDTO resultadoEnvioComunicadoCartera = new ResultadoEnvioComunicadoCarteraDTO();
        resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(true);
    
        if (notificaciones.isEmpty() || (!notificaciones.isEmpty() && !notificaciones.get(0).getEnvioExitoso())) {
            resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
            return resultadoEnvioComunicadoCartera;
        }
    
        for (NotificacionParametrizadaDTO notificacion : notificaciones) {
            if (destinatariosValidos(notificacion)) {
                try {
                    if (!EtiquetaPlantillaComunicadoEnum.COM_PAG_SUB_INC_ARC_CON.equals(notificacion.getEtiquetaPlantillaComunicado())) {
                        construirNotificacion(notificacion);
                    } else {
                        construirNotificacionAdjuntoTxt(notificacion);
                    }
                    
                    try {
                        EnviarCorreoParametrizadoCartera enviarCorreoCartera = new EnviarCorreoParametrizadoCartera(notificacion);
                        enviarCorreoCartera.execute();
                        resultadoEnvioComunicadoCartera = enviarCorreoCartera.getResult();
                    } catch (Exception e) {
                        logger.error("Error al procesar la notificación", e);
                        resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
                        
                        if (e instanceof TechnicalException) {
                            throw e;
                        }
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                    }
                } catch (Exception e) {
                    resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
                }
            } else {
                resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
            }
        }
        
        logger.info("Fin de método envioExitosoComunicadosCartera(List<NotificacionParametrizadaDTO>)");
        return resultadoEnvioComunicadoCartera;
    }    
    
    /**
     * Método que realiza la validación de los destinatarios de una notificación
     * parametrizada
     * 
     * @param notificacionParametrizadaDTO
     *        Información de la notificación parametrizada
     * @return <code>true</code> si todas las direcciones de correo son válidas.
     *         <code>false</code> en otro caso
     */
    private Boolean destinatariosValidos(NotificacionParametrizadaDTO notificacionParametrizadaDTO) {
        logger.debug("Inicio de método validarListaDestinatarios");
        List<String> destinatarios = new ArrayList<String>();

        if (notificacionParametrizadaDTO.getDestinatarioTO() != null) {
            destinatarios.addAll(notificacionParametrizadaDTO.getDestinatarioTO());
        }

        if (notificacionParametrizadaDTO.getDestinatarioBCC() != null) {
            destinatarios.addAll(notificacionParametrizadaDTO.getDestinatarioBCC());
        }

        if (notificacionParametrizadaDTO.getDestinatarioCC() != null) {
            destinatarios.addAll(notificacionParametrizadaDTO.getDestinatarioCC());
        }

        if (destinatarios.isEmpty()) {
            return Boolean.FALSE;
        }

        for (String destinatario : destinatarios) {
            if (!ValidacionCamposUtil.validarEmail(destinatario)) {
                logger.debug("Fin de método validarListaDestinatarios. Uno o más destinatarios presentan correo eletrónico no válido: "
                        + destinatario);
                logger.info("**__** CORREO NO APROBO VALIDACIONES EN SUS EXPRESIONES REGULARES: "+destinatario);
                return Boolean.FALSE;
            }
        }

        logger.debug("Fin de método validarListaDestinatarios");
        return Boolean.TRUE;
    }
    
    /**
     * Método que se encarga de contruir una notificación
     * 
     * @param notificacion
     */
    private void construirNotificacionAdjuntoTxt(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia construirNotificacion(NotificacionParametrizadaDTO)");
        // se validan los datos mínimos y obligatorios
        validarParametros(notificacion);

        // se consulta el comunicado con todas sus variables resueltas tanto en
        // el archivo ajunto como en el mensaje del correo
        PlantillaComunicado plaCom = construirPlantillaComunicado(notificacion);

        if (notificacion.getParams() == null || !notificacion.getParams().containsKey("enviarSinAlmacenarComunicado")
                || !Boolean.parseBoolean(notificacion.getParams().get("enviarSinAlmacenarComunicado"))) {
            //parmetrizada
            List<ArchivoAdjuntoDTO> attachFiles = crearAdjuntosTxtNotificacion(notificacion, plaCom);
            notificacion.setArchivosAdjuntos(attachFiles);
        }

        notificacion.setAsunto(plaCom.getAsunto());
        notificacion.setMensaje(plaCom.getMensaje());
        // el destinatario debe venir desde la petición
        logger.debug("Finaliza construirNotificacion(NotificacionParametrizadaDTO)");
    }
    
    /**
     * Método que se encarga de almacenar el comunicocado en txt en el ECM y
     * crear el objeto de archivo adjunto
     * 
     * @param notificacion
     * @param bytes
     * @return
     */
    private ArchivoAdjuntoDTO crearArchivoAdjuntoTxt(NotificacionParametrizadaDTO notificacion, byte[] bytes) {
        logger.debug("Inicio crearArchivoAdjunto(almacenarArchivoECM(NotificacionParametrizadaDTO, byte[])");
        InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
        infoArch.setDataFile(bytes);
        infoArch.setDescription("Comunicado");
        infoArch.setDocName(notificacion.getEtiquetaPlantillaComunicado() + "_comunicado.txt");
        infoArch.setFileName(notificacion.getEtiquetaPlantillaComunicado() + "_comunicado.txt");
        infoArch.setFileType("text/plain");
        infoArch.setProcessName(notificacion.getProcesoEvento());
        infoArch.setIdInstanciaProceso(notificacion.getIdInstanciaProceso());
        // se realiza el upload del archivo al ECM
        String idECM = almacenarArchivo(infoArch);

        // se crae el achivo adjunto
        ArchivoAdjuntoDTO attachFile = new ArchivoAdjuntoDTO();
        attachFile.setContent(infoArch.getDataFile());
        attachFile.setFileName(infoArch.getFileName());
        attachFile.setFileType(infoArch.getFileType());
        attachFile.setIntegratedImage(false);
        attachFile.setNameDate(false);
        attachFile.setIdECM(idECM);
        logger.debug("Finaliza crearArchivoAdjunto(almacenarArchivoECM(NotificacionParametrizadaDTO, byte[])");
        return attachFile;
    }
    
    /**
     * Se encarga de crear la lista de los archivos adjuntos
     * 
     * @param notificacion
     * @param plaCom
     * @return
     */
    private List<ArchivoAdjuntoDTO> crearAdjuntosTxtNotificacion(NotificacionParametrizadaDTO notificacion, PlantillaComunicado plaCom) {
        List<ArchivoAdjuntoDTO> attachFiles = new ArrayList<ArchivoAdjuntoDTO>();
        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error la plantilla del comunicado no tiene datos");
            logger.warn("Se asume que no tiene documento adjunto");
        }
        else {
            if (!notificacion.isNoEnviarAdjunto()) {
                // se convierte a pdf la plantilla de comunicado
                List<String[]> encabezadoList = new ArrayList<>();
                List<String[]> contenido  = new ArrayList<>();
                String[] encabezado = {"A continuacion se presentan las inconsistencias encontradas :"};
                String cuerpoStr = plaCom.getCuerpo();
                cuerpoStr = cuerpoStr.replace("<p>", "");
                cuerpoStr = cuerpoStr.replace("</p>", "");
                String[] filas = cuerpoStr.split(",");
                String[] columna;
                for (String fila : filas) {
                    columna = fila.split("/");
                    contenido.add(columna);
                    
                }
                encabezadoList.add(encabezado);
                
                byte[] bytes = generarArchivoPlano(encabezadoList, contenido, " ");

                // se almacena el pdf en el ECM
                ArchivoAdjuntoDTO attachFile = crearArchivoAdjuntoTxt(notificacion, bytes);
                attachFiles.add(attachFile);
            }
        }
        return attachFiles;
    }
    
    /**
     * Genera el array de bits que contiene la informacion del archivo
     * @param encabezado
     * @param data
     * @param caracterSeparador
     * @return
     */
    public static byte[] generarArchivoPlano(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(baos)) {
            if (encabezado != null && !encabezado.isEmpty()) {
                construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
            }
            if (data != null && !data.isEmpty()) {
                construirSeccionArchivoPlano(writer, data, caracterSeparador);
            }
            writer.flush();
        }
        return baos.toByteArray();
    }
    
    /**
     * Contruye cada fila del archivo plano
     * @param writer
     * @param datosSeccion
     * @param caracterSeparador
     */
    private static void construirSeccionArchivoPlano(PrintWriter writer, List<String[]> datosSeccion, String caracterSeparador) {
        int i;
        for (Object[] valores : datosSeccion) {
            i = 0;
            for (Object valor : valores) {
                i++;
                writer.print(valor != null?valor.toString():"");
                if (i < valores.length) {
                    writer.print(caracterSeparador);
                }
            }
            writer.print('\r');
            writer.print('\n');
        }        
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistirRespuestaECMExterno(RespuestaECMExternoDTO respuesta) {
		String firma = "persistirRespuestaECMExterno(RespuestaECMExternoDTO)";
		
		logger.debug("Inicia método " + firma + "con parámetros: " + respuesta.toString());
		
		GuardarRespuestaECMExterno guardarRespuestaECMExterno = new GuardarRespuestaECMExterno(respuesta);
		guardarRespuestaECMExterno.execute();

		String destinatario = (respuesta.getCode() != null && respuesta.getCode() != 0) 
				? CacheManager.getParametro(ParametrosSistemaConstants.EMAIL_ADMINISTRADOR_DEL_SISTEMA).toString()
						: CacheManager.getParametro(ParametrosSistemaConstants.EMAIL_NOTIFICACION_ERROR_ECM_EXTERNO).toString();
		
		String cuerpoCorreo = construirCuerpoNotificacionRespuestECMExterno(respuesta.getPayload());
		
		enviarNotificacionRespuestaECMExterno(destinatario, cuerpoCorreo, EtiquetaPlantillaComunicadoEnum.NTF_FLL_ENV_INFO_FOL);
		
		logger.debug("Finaliza método " + firma); 

	}

	private void enviarNotificacionRespuestaECMExterno(String destinatario, String cuerpoCorreo, EtiquetaPlantillaComunicadoEnum etiquetaPlantilla) {
		
		NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
		
		HashMap<String, String> parametros = new HashMap<String,String>();
		
		parametros.put("contenido", cuerpoCorreo);
		parametros.put("enviarSinAlmacenarComunicado", "false");
		
		List<String> destinatarios = new ArrayList<>(); 
		destinatarios.add(destinatario);
		
		notificacionParametrizadaDTO.setParams(parametros);
		notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
		notificacionParametrizadaDTO.setDestinatarioTO(destinatarios);
		notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(etiquetaPlantilla);
		
		construirNotificacion(notificacionParametrizadaDTO);
		
		// se envía el correo con el comunicado como un archivo adjunto
        EnviarCorreoParametrizado enviarCorreo = new EnviarCorreoParametrizado(notificacionParametrizadaDTO);
        enviarCorreo.execute();
		
	}
	
	@Override
	public void enviarNotificacionSeven(NotificacionParametrizadaDTO notificacion){
		String firma = "enviarNotificacionSeven(NotificacionParametrizadaDTO)";
		logger.info("**__**Inicia método " + firma);
		
		if(notificacion != null){
			
			System.out.println("en la notificación llegaron " + notificacion.getArchivosAdjuntos().size() + " archivos adjuntos.");
			
			List<String> destinatarios = new ArrayList<>();
			destinatarios.add(CacheManager.getParametro(ParametrosSistemaConstants.EMAIL_CONTACTO_SEVEN).toString());
			notificacion.setDestinatarioTO(destinatarios);
			notificacion.setReplantearDestinatarioTO(true);
			notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_FLL_PRCS_SEVEN);
			HashMap<String, String> parametros = new HashMap<>();
			parametros.put("enviarSinAlmacenarComunicado", "false");
			
			EnviarCorreoParametrizado enviarCorreo = new EnviarCorreoParametrizado(notificacion);
			enviarCorreo.execute();
		}
		logger.info("Finaliza método " + firma);
	}
	
	private void construirNotificacionSeven(NotificacionParametrizadaDTO notificacion){
		// se validan los datos mínimos y obligatorios
        validarParametros(notificacion);

        // se consulta el comunicado con todas sus variables resueltas tanto en
        // el archivo ajunto como en el mensaje del correo
        PlantillaComunicado plaCom = construirPlantillaComunicado(notificacion);

        notificacion.setAsunto(plaCom.getAsunto());
        notificacion.setMensaje(plaCom.getMensaje());
	}
	
	
	public String construirCuerpoCorreoNotificacionSEVEN(List<String> destinatarios){
		
		StringBuilder sb = new StringBuilder();
		sb.append("<p><strong>Para:&nbsp;</strong>");
		destinatarios.forEach(destinatario -> sb.append(destinatario + " "));
		sb.append("</p><p><strong>Asunto:&nbsp;</strong>Notificaci&oacute;n - Fallo en el env&iacute;o de informaci&oacute;n a ERP.</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>Se present&oacute; un error al env&iacute;ar el registro de informaci&oacute;n a ERP.</p>");
		sb.append("<p>Los datos no procesados se env&iacute;an en archivo adjunto a este correo.</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>Cordial saludo,</p>");
		sb.append("<p>Administrador del sistema.</p>");
		return sb.toString();
	}
	
	public String construirCuerpoNotificacionRespuestECMExterno(String objeto){
		
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		MetadataArchivoDTO metadata = gson.fromJson(objeto, MetadataArchivoDTO.class);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Se presentó un error al enviar el registro de información a Folium correspondiente a:</p>"
				+ "<table><tbody><tr><th>Parámetro</th><th>Tipo Identificación</th><th>Número Identificación</th><th>Nombre</th>"
				+ "<th>Ciudad</th><th>Dirección</th><th>Teléfono</th><th>Celular</th><th>Email</th><th>Observacion</th></tr><tr>");
		sb.append("<th>Descripción Genesys</th>");
		sb.append("<td>"+ metadata.getTipoIdentificacion() +"</td>");
		sb.append("<td>"+ metadata.getNumeroIdentificacion() +"</td>");
		sb.append("<td>"+ metadata.getNombre() +"</td>");
		sb.append("<td>"+ metadata.getCiudad() +"</td>");
		sb.append("<td>"+ metadata.getDireccion() +"</td>");
		sb.append("<td>"+ metadata.getTelefono() +"</td>");
		sb.append("<td>"+ metadata.getCelular() +"</td>");
		sb.append("<td>"+ metadata.getEmail() +"</td>");
		sb.append("<td>"+ metadata.getObservacion() +"</td>");
		sb.append("</tr></tbody></table>");
		
		return sb.toString();
	}
}
