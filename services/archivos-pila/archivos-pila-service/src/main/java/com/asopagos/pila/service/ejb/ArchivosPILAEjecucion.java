package com.asopagos.pila.service.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import com.asopagos.aportes.clients.EjecutarCalculoCategoriasMasiva;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.soporte.ProcesoPila;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContexto;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.DatosProcesoFtpDTO;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.dto.UbicacionCampoArchivoPilaDTO;
import com.asopagos.pila.service.IArchivosPILAEjecucion;
import com.asopagos.pila.service.IProcesosDescarga;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;
import com.asopagos.constants.ConstantesDatosAportesPila;

/**
 * Servicio de ejecución de procesos PILA
 * Este servicio se encarga de la ejecución asincrona de tareas de procesamiento de archivos
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
@Stateless
public class ArchivosPILAEjecucion implements IArchivosPILAEjecucion {

    /**
     * Instancia del Excecutor Manager
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/pila")
    private ManagedExecutorService mes;

    @Inject
    private ProcesadorBloquesPILA procesadorBloquesPILA;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;

    @Inject
    private GestorStoredProceduresLocal gestorUsp;

    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;

    @Inject
    private IPersistenciaDatosValidadores persistencia;

    @Inject
    private IConsultaModeloDatosCore consultaDatosCore;

    @Inject
    private IPersistenciaPreparacionContexto persistenciaPreparacion;

    @Inject
    private IGestorPila2 gestorPila2;

    @Inject
    private IProcesosDescarga procesosDescarga;

    private static final String VERSION_CLIENT_SEPARATOR = "_";

    private static final String USUARIO_NO_IDENTIFICADO = "USUARIO_NO_IDENTIFICADO";

    private static final String SECUENCIA_LOG_ERRORES = "Sec_PilaErrorValidacionLog";

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ArchivosPILAEjecucion.class);

    /**
     * Listado de índices de planilla OF que se procesan automáticamente
     */
    private List<IndicePlanillaOF> indicesOF;

    /*
     * (non-Javadoc) 
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#validarArchivosOI(java.util.List, java.lang.String)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void validarArchivosOIAsincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador) {
        String firmaMetodo = "validarArchivosOIAsincrono(List<IndicePlanilla>, String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        validarArchivosOI(indices, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO, idProcesoAgrupador, true, false, false);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#validarArchivosOI(java.util.List, java.lang.String)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void validarArchivosOISinValidacionAsincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador) {
        String firmaMetodo = "validarArchivosOISinValidacionAsincrono(List<IndicePlanilla>, String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        validarArchivosOISinValidacion(indices, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO, idProcesoAgrupador, true, false, false);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#validarArchivosOF(java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Future<List<RespuestaServicioDTO>> validarArchivosOF(List<IndicePlanillaOF> indices, String usuario) {
        String firmaMetodo = "validarArchivosOF(List<IndicePlanillaOF>, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaServicioDTO> result = validarArchivosOFProceso(indices, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO);

        logger.info("**__**fin validarArchivosOF "+ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return new AsyncResult<>(result);
    }

    /**
     * Procesa los archivos OF
     * @param indices
     * @param usuario
     * @return
     */
    private List<RespuestaServicioDTO> validarArchivosOFProceso(List<IndicePlanillaOF> indices, String usuario) {
    	long timeStart = System.nanoTime();
    	
        String firmaMetodo = "validarArchivosOFProceso(List<IndicePlanillaOF>, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        indicesOF = indices;

        List<RespuestaServicioDTO> result = new ArrayList<>();

        Long idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.VALIDACION_WEB);

        if (indicesOF != null) {
        	logger.info(firmaMetodo + "indices " + indices.size());
        	logger.info(firmaMetodo + "indicesOF " + indicesOF.size());
        	List<IndicePlanilla> indicesOI = new ArrayList<IndicePlanilla>();
        	
        	List<Callable<RespuestaServicioDTO>> parallelTaskList = new ArrayList<>();
        	List<Future<RespuestaServicioDTO>> resultadosFuturos = new ArrayList<>();
        
        	for (IndicePlanillaOF indice : indicesOF) {
            	
            	Callable<RespuestaServicioDTO> parallelTask = () -> {
            		RespuestaServicioDTO respuesta = null;
					try {
						
					    respuesta = procesadorBloquesPILA.procesarPlanillasOF(indice, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO);
					    
					} catch (Exception e) {
						LogErrorPilaM1ModeloDTO log = FuncionesValidador.prepararLogError(indice, e);
						persistencia.registrarLogError(log);
						if(idProceso != null) {
							finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
						}
						logger.error(firmaMetodo + " :: " + e);
					}
					return respuesta;
            	};
            	
            	parallelTaskList.add(parallelTask);
            }
            
            try {
				resultadosFuturos = mes.invokeAll(parallelTaskList);
				for (Future<RespuestaServicioDTO> future : resultadosFuturos){
	            	if(future != null) {
		            	RespuestaServicioDTO respuesta = future.get();
	                	if(respuesta.getIndicesOIenOF() != null) {
	                		indicesOI.addAll(respuesta.getIndicesOIenOF());
	                		logger.info(firmaMetodo + "respuesta.getIndicesOIenOF() " + respuesta.getIndicesOIenOF().size());
	                		logger.info(firmaMetodo + "indicesOI " + indicesOI.size());
	                	}
	                    respuesta.setIndicesOIenOF(null);
	                    logger.info(firmaMetodo + "respuesta.setIndicesOIenOF(null)");
                		logger.info(firmaMetodo + "indicesOI " + indicesOI.size());
	                    result.add(respuesta);
	            	}
	    		}
			} catch (InterruptedException e) {
				//MANTIS293710
				if(idProceso != null) {
					finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
				}
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			} catch (ExecutionException e) {
				//MANTIS293710
				if(idProceso != null) {
					finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
				}
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			}

            // una vez se termina la validación de los archivos OF, se solicita la validación de archivos OI que apliquen
            /*// se quita la ejecución del bloque 6 de manera automatica luego de cargar el archivo F
            if (indicesOI != null && !indicesOI.isEmpty()) {
            	logger.info(firmaMetodo + "validarArchivosOI");
                validarArchivosOI(indicesOI, usuario, idProceso, true, true);
            } else {
            	logger.info(firmaMetodo + "validarArchivosOI sin datos");
            }
            */

            // se limpia la lista de índices
            indicesOF = null;
        }

        if (idProceso != null) {
        	logger.info(firmaMetodo + "finalizarProceso");
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        long timeEnd = System.nanoTime();
    	logger.info(firmaMetodo + " tiempo ejecución en : " + TimeUnit.SECONDS.convert((timeEnd - timeStart), TimeUnit.NANOSECONDS));
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#instanciarProceso(java.lang.String,
     * com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum)
     */
    @Override
    public Long instanciarProceso(String usuarioProceso, TipoProcesoPilaEnum tipoProceso) {
        String firmaMetodo = "instanciarProceso(String, TipoProcesoPilaEnum)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        // se crea la instancia del nuevo proceso
        // TODO primero se obtiene el número de radicado de solicitud
        String radicado = "000000000001";

        ProcesoPila proceso = new ProcesoPila();
        proceso.setTipoProceso(tipoProceso);
        proceso.setEstadoProceso(EstadoProcesoValidacionEnum.PROCESO_ACTIVO);
        proceso.setFechaInicioProceso(Calendar.getInstance().getTime());
        proceso.setNumeroRadicado(radicado);
        proceso.setUsuarioProceso(usuarioProceso != null ? usuarioProceso : USUARIO_NO_IDENTIFICADO);

        logger.warn("Objeto proceso, metodo instanciarProceso");
        logger.warn(proceso.toString());

        // se persiste el proceso
        persistencia.registrarProceso(proceso);

        logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return proceso.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#finalizarProceso(com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum)
     */
    @Override
    public void finalizarProceso(Long idProceso, EstadoProcesoValidacionEnum estadoProceso) {
        String firmaMetodo = "finalizarProceso(Long, EstadoProcesoValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        // se finaliza el proceso
        try {

            ProcesoPila proceso = persistencia.consultarProcesoId(idProceso);

            proceso.setFechaFinProceso(Calendar.getInstance().getTime());
            proceso.setEstadoProceso(estadoProceso);
            logger.warn("proceso a actualizar");
            logger.warn(proceso.toString());
            persistencia.actualizarProceso(proceso);
        } catch (ErrorFuncionalValidacionException e) {
            // en este caso, el no encontrar el proceso se considera un error técnico
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + e.getMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#cargarArchivo(com.asopagos.pila.dto.ArchivoPilaDTO, java.lang.String,
     * com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum)
     */
    @Override
    public RespuestaServicioDTO cargarArchivo(ArchivoPilaDTO archivoPila, String usuario, TipoCargaArchivoEnum tipoCarga, Boolean validarBloque0) {
        
        String firmaMetodo = "cargarArchivo(ArchivoPilaDTO, String, TipoCargaArchivoEnum, Boolean) :: Archivo: " + archivoPila.getFileName() + " Tipo Carga: " + tipoCarga.name();
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaServicioDTO respuestaServicio = new RespuestaServicioDTO();
        respuestaServicio.setFileName(archivoPila.getFileName());

        if (FuncionesValidador.valoresGeneralesValidacion == null) {
            iniciarVariablesGenerales();
        }

        // se verifica sí se cuenta con la versión al interior del id de documento 
        String identificadorArchivo = archivoPila.getIdentificadorDocumento();

        if (identificadorArchivo != null) {
            String[] srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
            if (srtIdAndVersion.length == 2) {
                archivoPila.setIdentificadorDocumento(srtIdAndVersion[0]);
                archivoPila.setVersionDocumento(srtIdAndVersion[1]);
            }
            respuestaServicio.setIdDocumento(archivoPila.getIdentificadorDocumento());
        }

        // se complementa la información de archivoPila con la info del usuario que realiza la carga
        archivoPila.setUsuario(usuario != null ? usuario : USUARIO_NO_IDENTIFICADO);
        archivoPila.setModo(tipoCarga);

        // se solicita el tipo de archivo a partir del nombre sí no se tiene desde el comienzo
        if (archivoPila.getPerfilArchivo() == null) {
            agregarPerfilArchivo(archivoPila);
        }

        if (archivoPila.getPerfilArchivo() == null) {
            
            String mensaje = MessagesConstants.getMensajeCargaArchivo(archivoPila.getFileName(), MessagesConstants.ERROR_NOMBRE_ARCHIVO);
            logger.error(firmaMetodo + " :: " + mensaje);
            respuestaServicio.setMensajeRespuesta(mensaje);
            respuestaServicio.setEstado(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO);
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo + respuestaServicio.getMensajeRespuesta());
            return respuestaServicio;
        } 
        
        // se solicita la ejecución del B0 dependiendo del perfil de lectura
        RespuestaValidacionDTO respuestaValidacionDTO = procesadorBloquesPILA.ejecutarBloqueCeroPorPerfil(archivoPila, respuestaServicio, validarBloque0);
        
        // se prepara el mensaje de respuesta
        respuestaServicio.setIndice(respuestaValidacionDTO.getIndicePlanilla() != null ? respuestaValidacionDTO.getIndicePlanilla()
                : respuestaValidacionDTO.getIndicePlanillaOF());
        respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
        respuestaServicio.setEsReproceso(respuestaValidacionDTO.getEsReproceso());

        // se agregan los detalles finales a los DTO de error y se solicita su registro
        List<ErrorValidacionLog> errores = new ArrayList<>();
        List<Long> ids = gestorUsp.obtenerValoresSecuencia(respuestaValidacionDTO.getErrorDetalladoValidadorDTO().size(), SECUENCIA_LOG_ERRORES);
        Set<String> camposPorUbicar = new HashSet<>();

        for (ErrorDetalladoValidadorDTO error : respuestaValidacionDTO.getErrorDetalladoValidadorDTO()) {

            /*
             * los errores de fecha de pago de OF no generan índice planilla, por esa razón se pueden presentar
             * errores que no tienen ningún índice asociado. Estos no se deben persistir
             */
            if (respuestaServicio.getIdIndicePlanilla() != null) {
                error.setIdIndicePlanilla(respuestaServicio.getIdIndicePlanilla());

                ErrorValidacionLog errorLog = null;

                try {
                    errorLog = FuncionesValidador.generarEntityError(error, ids.get(0));
                    camposPorUbicar.add(error.getIdCampoError());
                    ids.remove(0);
                } catch (ErrorFuncionalValidacionException e) {
                    logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + e.getMessage());
                    return null;
                }

                errores.add(errorLog);
            }
        }
        if (!errores.isEmpty()) {
            // se consultan las etiquetas de los campos con error para agregar ubicaciones
            List<UbicacionCampoArchivoPilaDTO> ubicaciones = consultaDatosCore.consultarUbicaciones(camposPorUbicar);
            FuncionesValidador.asignarUbicacionCampo(errores, ubicaciones);

            persistencia.registrarError(errores);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo + respuestaServicio.getMensajeRespuesta());
        return respuestaServicio;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#consultarIndicesParaProcesarOI()
     */
    @Override
    @Deprecated
    public List<IndicePlanilla> consultarIndicesParaProcesarOI() {
        String firmaMetodo = "consultarIndicesParaProcesarOI()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanilla> result = null;

        // se inicializan los listados de estados
        List<EstadoProcesoArchivoEnum> estadosOI = EstadoProcesoArchivoEnum.getEstadosIncompletos();

        result = persistencia.consultarIndicePorEstadoMultipleOI(estadosOI, null, null);

        // se indica que los archivos consultados quedan en lista de procesamiento
        if (result != null && !result.isEmpty()) {
            for (IndicePlanilla indicePlanilla : result) {
                indicePlanilla.setEnLista(true);
                persistencia.actualizarIndicePlanillas(indicePlanilla);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#consultarIndicesParaProcesarOF()
     */
    @Override
    public List<IndicePlanillaOF> consultarIndicesParaProcesarOF() {
        String firmaMetodo = "consultarIndicesParaProcesarOF()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanillaOF> result = null;

        // se inicializan los listados de estados
        List<EstadoProcesoArchivoEnum> estadosOF = new ArrayList<>();

        // se agregan los estados para OF
        estadosOF.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);

        try {
            this.indicesOF = persistencia.consultarIndicePorEstadoMultipleOF(estadosOF);
        } catch (ErrorFuncionalValidacionException e) {
            // no encontrar índices no representa un error en este punto
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#descargarYCargarArchivos(java.lang.Long, java.lang.String)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void descargarYCargarArchivosAsincrono(Long idOperadorInformacion, String usuario, TipoCargaArchivoEnum tipoCarga) {
        String firmaMetodo = "descargarYCargarArchivosAsincrono(Long, String, TipoCargaArchivoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idProceso = null;

        // se marca el inicio del nuevo proceso
        idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA_WEB);

		try {
            descargarYCargar(idOperadorInformacion, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO, tipoCarga, idProceso,
                    Boolean.FALSE);
		} catch (Exception e) {
			// se captura el error técnico para marcar la finalización fallida
			// del proceso
			finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			// inmediatamente, se lanza de nuevo la misma excepción para que sea
			// presentada en pantalla
			throw e;
		}

		if (idProceso != null) {
			finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
		}

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#descargarCargarYValidarArchivos(java.lang.Long, java.lang.String)
     */
    @Override
    public void descargarCargarYValidarArchivos(Long idOperadorInformacion, String usuario) {
        String firmaMetodo = "descargarCargarYValidarArchivos(Long, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String usuarioCarga = usuario;

        Long idProceso = null;

        // se marca el inicio del nuevo proceso
        idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA);

        if (usuarioCarga == null) {
            usuarioCarga = ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO;
        }
        
		try {
            descargarYCargar(idOperadorInformacion, usuarioCarga, TipoCargaArchivoEnum.AUTOMATICA_BATCH, idProceso, Boolean.TRUE);
		} catch (Exception e) {
            // se captura el error técnico para marcar la finalización fallida del proceso
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            // inmediatamente, se lanza de nuevo la misma excepción para que sea presentada en pantalla
            throw e;
		}

        if (idProceso != null) {
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#esAportePropio(java.lang.Long)
     */
    public Boolean esAportePropio(Long id) {
        String firmaMetodo = "esAportePropio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = persistencia.consultarAportePropio(id);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método que permite identificar el tipo archivo a cargar
     * 
     * @param archivoPila
     *        <code>ArchivoPilaDTO </code> El DTO de tranferencia de
     *        datos para el consumo de los servicios de PILA
     */
    private void agregarPerfilArchivo(ArchivoPilaDTO archivoPila) {
        String firmaMetodo = "agregarPerfilArchivo(ArchivoPilaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoArchivoPilaEnum tipoArchivo = FuncionesValidador.getTipoArchivo(archivoPila.getFileName());

        if (tipoArchivo != null) {
            archivoPila.setPerfilArchivo(tipoArchivo.getPerfilArchivo());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Este metodo se encarga de obtener los archivos del FTP y cargarlos en índices de planilla
     * @param idOperadorInformacion
     *        ID del operador de información seleccionado
     * @param datosControl
     *        DTO con los datos de control del proceso
     * @param ejecutarB0 
     */
    private void obtencionArchivos(Long idOperadorInformacion, DatosProcesoFtpDTO datosControl, Boolean ejecutarB0, String usuario, Long idProceso) {
        String firmaMetodo = "ArchivosPILAEjecucion.obtencionArchivos(Long, DatosProcesoFtpDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConexionOperadorInformacion> conexionesOperadores = null;
        try {
            conexionesOperadores = persistencia.consultarDatosConexionOperadorInformacion(idOperadorInformacion);
        } catch (ErrorFuncionalValidacionException e) {
            // en este punto, no contar con parámetros de conexión con el OI, se considera un error crítico
            // se le trata como excepción técnica

            throw new TechnicalException(e.getMessage(), e.getCause());
        }
        
        logger.info("conexiones:" + conexionesOperadores);

        if (conexionesOperadores != null) {
            for (ConexionOperadorInformacion conexionOperadorInformacion : conexionesOperadores) {
                conectarYDescargarArchivos(conexionOperadorInformacion, datosControl, ejecutarB0, usuario, idProceso);
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método que descarga los archivos de una conexión FTP y los prepara para su carga
     * @param conexionOperadorInformacion
     *        Conexión con el servidor del cliente
     * @param datosControl
     *        DTO con los datos de control del proceso
     * @param ejecutarB0 
     */
    private void conectarYDescargarArchivos(ConexionOperadorInformacion conexionOperadorInformacion, DatosProcesoFtpDTO datosControl,
            Boolean ejecutarB0, String usuario, Long idProceso) {
        String firmaMetodo = "conectarYDescargarArchivos(ConexionOperadorInformacion, List<ArchivoPilaDTO>, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ArchivoPilaDTO> archivosDescargados;      
        List<ArchivoPilaDTO> archivosDescargadosPaginado = new ArrayList<ArchivoPilaDTO>();
        List<ArchivoPilaDTO> archivosDescargado = new ArrayList<ArchivoPilaDTO>();

        ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP = prepararConexionFtp(conexionOperadorInformacion);

        conexionFTP.conectarRecorrerNoDescargar();        
    
        // Control de cambios 708 Mejorar rendimiento descargas FTP PILA ****
        // se elimnan los archivos de la carpeta "descargado"
        logger.warn("Inicia remover If");
        conexionFTP.getArchivosDescargados().removeIf(n -> n.getPathFile().contains("/" + ConstantesDatosAportesPila.CARPETA_DESCARGADOS_POR_OPERADOR_INFO+"/"));
        logger.warn("Finaliza remover If");
        archivosDescargados = conexionFTP.getArchivosDescargados();
        archivosDescargado = persistencia.filtrarExistentesPlanillas(archivosDescargados, 0);
        //archivosDescargados =conexionFTP.getArchivosDescargados();
        logger.warn("Finaliza Filtrar Existentes");
        if (!archivosDescargado.isEmpty()) {
			// se organiza la lista antes de cargarla
            logger.warn("Entra en if");
			archivosDescargado = FuncionesValidador.ordenarListadoDatosArchivos(archivosDescargado, false);
            logger.warn("archivosDescargado");
	        int cont = 0;
	        //int pagina = 20;
	        int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
	        logger.warn("paginas: "+ pagina);
         

	        for (ArchivoPilaDTO a : archivosDescargado) {
	        	archivosDescargadosPaginado.add(a);
	        	cont ++;
	        	if(cont == pagina) {
                    
	        		conectarYDescargarArchivosPaginado(archivosDescargadosPaginado, conexionFTP, datosControl, ejecutarB0, usuario, idProceso);
                    archivosDescargadosPaginado = new ArrayList<ArchivoPilaDTO>();
	        		cont = 0;
	        	}	
	        }
	        
	        if(cont > 0) {
                //archivosDescargado = persistencia.filtrarExistentesPlanillas(archivosDescargadosPaginado, 0);
	    		conectarYDescargarArchivosPaginado(archivosDescargadosPaginado, conexionFTP, datosControl, ejecutarB0, usuario, idProceso);
	    	}
        }

        logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }  
    
    /**
     * Método que descarga los archivos de una conexión FTP y los prepara para su carga
     * 
     */
	private void conectarYDescargarArchivosPaginado(List<ArchivoPilaDTO> archivosDescargados,
			ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP, DatosProcesoFtpDTO datosControl, Boolean ejecutarB0,
			String usuario, Long idProceso) {

		String firmaMetodo = "conectarYDescargarArchivosPaginado(ConexionOperadorInformacion, List<ArchivoPilaDTO>, Boolean)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (!archivosDescargados.isEmpty()) {
			// se organiza la lista antes de cargarla
			//archivosDescargados = FuncionesValidador.ordenarListadoDatosArchivos(archivosDescargados, false);

			if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga())) {
				// procesamiento normal de la descarga del contenido
				archivosDescargados = procesosDescarga.descargarYguardarListadoFTPSincrono(conexionFTP,
						archivosDescargados);
			}

			// se recorre el arreglo de DTOs para realizar la carga
			try {
				List<IndicePlanilla> indicesOICarga = new ArrayList<>();
				for (ArchivoPilaDTO archivoPilaDTO : archivosDescargados) {
					cargaDeArchivoParalela(archivoPilaDTO, datosControl, ejecutarB0);

					if (archivoPilaDTO.getIndicePlanilla() != null) {
						indicesOICarga.add(archivoPilaDTO.getIndicePlanilla());

						if (archivoPilaDTO.getIndicePlanilla() != null) {

							if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga())
									&& ejecutarB0 != false) {
								archivoPilaDTO.getIndicePlanilla().setEnLista(true);
							}

							IndicePlanilla indice2 = persistencia
									.consultarPlanillaOIPorId(archivoPilaDTO.getIndicePlanilla().getId());

							if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga())
									&& ejecutarB0 != false) {
								indice2.setEnLista(true);
							}

							persistencia.actualizarIndicePlanillas(indice2);
						}
					}
				}
				if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga()) && ejecutarB0 != false) {
					validarArchivosOI(indicesOICarga, usuario, idProceso, true, false, false);
				}

			} catch (Exception e) {
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}

			// se organiza la lista antes de retornarla
			archivosDescargados = FuncionesValidador.ordenarListadoDatosArchivos(archivosDescargados, true);

			if (TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga()) && ejecutarB0 == false) {
				// procesamiento asíncrono de la descarga del contenido de los archivos
				procesosDescarga.descargarYguardarListadoFTPAsincrono(conexionFTP, archivosDescargados, usuario,
						idProceso);
			}
		}

		logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
		
     

    /**
     * Método para la carga de archivos en paralelo
     */
    private RespuestaServicioDTO cargaDeArchivoParalela(ArchivoPilaDTO archivoPilaDTO, DatosProcesoFtpDTO datosControl,
            Boolean ejecutarB0) {
        logger.warn("Inicia cargaDeArchivoParalela(ArchivoPilaDTO archivoPilaDTO, DatosProcesoFtpDTO datosControl Boolean ejecutarB0)");
        try {
            RespuestaServicioDTO resultadoCarga = cargarArchivo(archivoPilaDTO, datosControl.getUsuario(), datosControl.getTipoCarga(),
                    ejecutarB0);

            if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(FuncionesValidador.getTipoArchivo(archivoPilaDTO.getFileName()))) {
                archivoPilaDTO.setIndicePlanillaOF((IndicePlanillaOF) resultadoCarga.getIndice());
                archivoPilaDTO.setIdIndicePlanillaOF(resultadoCarga.getIdIndicePlanilla());
            }
            else {
                archivoPilaDTO.setIndicePlanilla((IndicePlanilla) resultadoCarga.getIndice());
                archivoPilaDTO.setIdIndicePlanillaOI(resultadoCarga.getIdIndicePlanilla());
            }
            logger.warn("respuesta resultado Carga");
            logger.warn(resultadoCarga);
            return resultadoCarga;
        } catch (Exception exception) {
            // se captura el error técnico para marcar la finalización fallida del proceso
            finalizarProceso(datosControl.getIdProceso(), EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
            // inmediatamente, se lanza de nuevo la misma excepción para que sea presentada en pantalla
            throw exception;
        }
    }

    /**
     * Método encargado de la preparación de una conexión con FTP
     * @return
     */
    private ConexionServidorFTPUtil<ArchivoPilaDTO> prepararConexionFtp(ConexionOperadorInformacion conexionOperadorInformacion) {
        String firmaMetodo = "ArchivosPILAEjecucion.prepararConexionFtp(ConexionOperadorInformacion)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP = new ConexionServidorFTPUtil<>("PILA", ArchivoPilaDTO.class);
        conexionFTP.setNombreHost(conexionOperadorInformacion.getHost());
        conexionFTP.setPuerto(conexionOperadorInformacion.getPuerto().toString());
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance().decrypt(conexionOperadorInformacion.getUsuario()));
        conexionFTP.setContrasena(DesEncrypter.getInstance().decrypt(conexionOperadorInformacion.getContrasena()));
        conexionFTP.setUrlArchivos(conexionOperadorInformacion.getUrl());
        conexionFTP.setProtocolo(conexionOperadorInformacion.getProtocolo());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return conexionFTP;
    }

    /**
     * Método síncrono para la descarga y carga de archivos
     * 
     * @param idOperadorInformacion
     *        ID del operador de información seleccionado. Un valor nulo indica que el proceso se realiza para todos los OI
     * @param usuario
     *        Usuario que inicia el proceso
     * @param tipoCarga
     *        Tipo de carga con la cual serán marcados los archivos en el índice de planillas
     * @param idProceso 
     * @param ejecutarB0 
     */
    private void descargarYCargar(Long idOperadorInformacion, String usuario, TipoCargaArchivoEnum tipoCarga, Long idProceso,
            Boolean ejecutarB0) {
        String firmaMetodo = "descargarYCargarArchivos(Long, String, TipoCargaArchivoEnum, Boolean)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.warn("idOperadorInformacion:" + idOperadorInformacion);        
        logger.warn("tipoCarga:" + tipoCarga);
        logger.warn("idProceso:" + idProceso);
        logger.warn("ejecutarB0:" + ejecutarB0);
        

        // se hace la lectura del FTP del OI selecionado
        DatosProcesoFtpDTO datosControl = new DatosProcesoFtpDTO(idProceso, usuario, tipoCarga);
        obtencionArchivos(idOperadorInformacion, datosControl, ejecutarB0, usuario, idProceso);
    
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla
     * 
     * @param indices
     *        Listado de índices de planilla OI a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @param idProcesoAgrupador
     * 		  ID del proceso que solicita la validación
     * @return List<RespuestaServicioDTO>
     *         Respuesta con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
	private void validarArchivosOI(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador, 
    		Boolean finalizar, Boolean procesoIniciadoPorArchivoF, Boolean ejecutarB6) {
        
	    String firmaMetodo = "validarArchivosOI(List<IndicePlanilla>, String, Boolean)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // se hace una última limpieza en el listado de índices antes de iniciar al validación
        List<IndicePlanilla> indicesOI = ProcesadorBloque.limpiarIndicesRepetidos(indices, false);

        List<RespuestaServicioDTO> result = new ArrayList<>();

        Long idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.VALIDACION_WEB);
        logger.info("idProceso" + idProceso);
        
        
        if (FuncionesValidador.valoresGeneralesValidacion == null) {
            iniciarVariablesGenerales();
        }
        logger.warn("Fin iniciarVariablesGenerales");
        if (indicesOI != null) {
        	// se crean los paquetes de archivos (A/I) por número de planilla y OI
        	Map<String, List<IndicePlanilla>> planillas = organizarPlanillas(indicesOI);
        	
        	// se lanzan procesos paralelos por cada paquete de planillas
        	List<Callable<List<RespuestaServicioDTO>>> parallelTaskList = new ArrayList<>();
        	List<Future<List<RespuestaServicioDTO>>> resultadosFuturos  = new ArrayList<>();
        	 logger.warn("validarArchivosOI listaPlanillasRelacionadas");
        	// Cada iteración procesa una planilla por operador financiero, primero el archivo A y luego el I
        	for (List<IndicePlanilla> listaPlanillasRelacionadas : planillas.values()){
        		Callable<List<RespuestaServicioDTO>> parallelTask = () -> {
                    logger.warn("Inicio de pila fase 2");
					return procesadorBloquesPILA.procesarPlanillasOI(listaPlanillasRelacionadas, usuario, idProcesoAgrupador);
				};
				parallelTaskList.add(parallelTask);
        	}
        	
        	try{
        		resultadosFuturos = mes.invokeAll(parallelTaskList);
        		
        		/*for (Future<List<RespuestaServicioDTO>> future : resultadosFuturos){
        			result.addAll(future.get());
        		}*/
        	}catch(Exception e){
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
                //MANTIS293710
                logger.info("MANTIS293710 ln4402 "+ idProceso);
                if(idProceso != null) {
                	logger.info("MANTIS293710 ln4403 "+ idProceso);
					finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
				}
        	}
        	
        }

        if (idProceso != null && Boolean.TRUE.equals(finalizar)) {
        	logger.info(ConstantesComunes.FIN_LOGGER + "finalizarProceso");
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    private void validarArchivosOISinValidacion(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador, 
    		Boolean finalizar, Boolean procesoIniciadoPorArchivoF, Boolean ejecutarB6) {
        
	    String firmaMetodo = "validarArchivosOISinValidacion(List<IndicePlanilla>, String, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // se hace una última limpieza en el listado de índices antes de iniciar al validación
        List<IndicePlanilla> indicesOI = ProcesadorBloque.limpiarIndicesRepetidos(indices, false);

        List<RespuestaServicioDTO> result = new ArrayList<>();

        Long idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.VALIDACION_WEB);
        logger.info("idProceso" + idProceso);
        
        

        if (indicesOI != null) {
        	// se crean los paquetes de archivos (A/I) por número de planilla y OI
        	Map<String, List<IndicePlanilla>> planillas = organizarPlanillas(indicesOI);
        	
        	// se lanzan procesos paralelos por cada paquete de planillas
        	List<Callable<Void>> parallelTaskList = new ArrayList<>();
        	List<Future<Void>> resultadosFuturos  = new ArrayList<>();
            logger.info("**__** validarArchivosOISinValidacion cantidad a procesas en tareas paralelas: "+planillas.size());
        	// Cada iteración procesa una planilla por operador financiero, primero el archivo A y luego el I
        	for (List<IndicePlanilla> listaPlanillasRelacionadas : planillas.values()){
        		Callable<Void> parallelTask = () -> {
                                        logger.info("Inicio de pila fase 2 sin validaciones");
					procesadorBloquesPILA.procesarPlanillasOISinValidacion(listaPlanillasRelacionadas, idProcesoAgrupador);
                                        return null;
				};
				parallelTaskList.add(parallelTask);
        	}
        	
        	try{
        		resultadosFuturos = mes.invokeAll(parallelTaskList);
        		
        		/*for (Future<List<RespuestaServicioDTO>> future : resultadosFuturos){
        			result.addAll(future.get());
        		}*/
        	}catch(Exception e){
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
                //MANTIS293710
                logger.info("MANTIS293710 ln4402 "+ idProceso);
                if(idProceso != null) {
                	logger.info("MANTIS293710 ln4403 "+ idProceso);
					finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
				}
        	}
        	
        }

        if (idProceso != null && Boolean.TRUE.equals(finalizar)) {
        	logger.info(ConstantesComunes.FIN_LOGGER + "finalizarProceso");
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Ordenamiento de los archivos en paquetes por planilla para proceso paralelo
	 * @param indicesOI
	 * @return
	 */
	private Map<String, List<IndicePlanilla>> organizarPlanillas(List<IndicePlanilla> indicesOI) {
		Map<String, List<IndicePlanilla>> result = new HashMap<>();
		
		String llave = null;
		List<IndicePlanilla> planilla = null;
		for (IndicePlanilla archivo : indicesOI){
        
			llave = archivo.getIdPlanilla() + "_" + archivo.getCodigoOperadorInformacion();
			    //logger.info("**__** organizarPlanillas llave: "+llave);
			if(!result.containsKey(llave)) {
			    planilla = new ArrayList<>();
                result.put(llave, planilla);
			}
			planilla.add(archivo);
		}
		
		return result;
	}
	
	/**
     * Método encargado de poblar el mapa con los valores de constantes, parámetros y tablas de parametrización
     * que serán empleadas por el ciclo de validación
     */
    public void iniciarVariablesGenerales() {
        String firmaMetodo = "ArchivosPILAEjecucion.iniciarVariablesGenerales()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> mapaValores = new HashMap<>();

        // se llenan los valores desde cache
        mapaValores.put(ConstantesContexto.CODIGO_CCF, CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
        mapaValores.put(ConstantesContexto.MINIMO_DIAS_PAGO, CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_MINIMO_PLANILLA));
        mapaValores.put(ConstantesContexto.REINTENTOS,
                CacheManager.getParametro(ParametrosSistemaConstants.REINTENTOS_PERSISTENCIA_CONTENIDO));
        mapaValores.put(ConstantesContexto.TOLERANCIA_VALOR_MORA,
                CacheManager.getParametro(ParametrosSistemaConstants.MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA));
        mapaValores.put(ConstantesContexto.MODIFICADOR_SALARIO_INTEGRAL,
                CacheManager.getParametro(ParametrosSistemaConstants.MULTIPLICADOR_VALOR_MINIMO_SALARIO_INTEGRAL));
        mapaValores.put(ConstantesContexto.CODIGO_DPTO_CCF,
                CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_DEPTO_ID));
        mapaValores.put(ConstantesContexto.CODIGO_MUNI_CCF,
                CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID));
        mapaValores.put(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL,
                CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_ARCHIVOS_PILA_EN_MEGABYTES));
        mapaValores.put(ConstantesContexto.SALARIO_MINIMO, CacheManager.getParametro(ParametrosSistemaConstants.SMMLV));

        mapaValores.put(ConstantesSistemaConstants.PILA_ARCHIVO_FINANCIERO,
                CacheManager.getConstante(ConstantesSistemaConstants.PILA_ARCHIVO_FINANCIERO));
        mapaValores.put(ConstantesSistemaConstants.PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE,
                CacheManager.getConstante(ConstantesSistemaConstants.PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE));
        mapaValores.put(ConstantesSistemaConstants.PILA_DETALLE_PENSIONADO,
                CacheManager.getConstante(ConstantesSistemaConstants.PILA_DETALLE_PENSIONADO));
        mapaValores.put(ConstantesSistemaConstants.PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE,
                CacheManager.getConstante(ConstantesSistemaConstants.PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE));
        mapaValores.put(ConstantesSistemaConstants.PILA_INFORMACION_PENSIONADO,
                CacheManager.getConstante(ConstantesSistemaConstants.PILA_INFORMACION_PENSIONADO));

        // se llenan los valores desde tablas de parametrización
        mapaValores.put(ConstantesContexto.OPERADORES_INFORMACION, consultarOperadoresInformacion());
        mapaValores.put(ConstantesContexto.DEPARTAMENTOS, consultaDatosCore.consultarDepartamentoMunicipio(1));
        mapaValores.put(ConstantesContexto.MUNICIPIOS, consultaDatosCore.consultarDepartamentoMunicipio(2));
        mapaValores.put(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS, consultaDatosCore.consultarCombinacionDepartamentoMunicipio());
        mapaValores.put(ConstantesContexto.CODIGOS_CIIU, consultaDatosCore.consultarCodigosCIIU());
        mapaValores.put(ConstantesContexto.FESTIVOS, persistenciaPreparacion.consultarFestivos());
        mapaValores.put(ConstantesContexto.NORMATIVIDAD, persistenciaPreparacion.consultarNormatividadVencimiento());
        mapaValores.put(ConstantesContexto.OPORTUNIDAD_VENCIMIENTO, persistenciaPreparacion.consultarOportunidadVencimiento());
        mapaValores.put(ConstantesContexto.TASAS_INTERES, persistenciaPreparacion.consultarTasasInteres());
        mapaValores.put(ConstantesContexto.CASOS_DESCUENTO_INTERES, consultaDatosCore.consultarDescuentosInteres());
        mapaValores.put(ConstantesContexto.OPERADORES_FINANCIEROS, consultarOperadoresFinancieros());
        mapaValores = consultarVigencia1429(mapaValores);

        FuncionesValidador.valoresGeneralesValidacion = mapaValores;
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Función para consultar el listado de operadores de información para el contexto
     * 
     * @return Set<String>
     *         Listado de Operadores de Información
     */
    private Set<String> consultarOperadoresInformacion() {
        logger.debug("Inicia consultarOperadoresInformacion()");

        Set<String> codigosOperadoresInformacion = new HashSet<>();

        List<OperadorInformacion> result = persistencia.consultarOperadoresInformacion();

        for (OperadorInformacion operadorInformacion : result) {
            codigosOperadoresInformacion.add(operadorInformacion.getCodigo());
        }

        logger.debug("Finaliza consultarOperadoresInformacion()");
        return codigosOperadoresInformacion;
    }

    /**
     * Función para consultar el listado de operadores financieros para el contexto, se obtienen los 3 últimos
     * dígitos del codigo de ruta y transito correspondiente
     * 
     * @return Set<String>
     *         Listado de Operadores Financieros
     */
    private Set<String> consultarOperadoresFinancieros() {
        logger.debug("Inicia consultarOperadoresFinacieros()");

        Set<String> codigosOperadoresFinancieros = new HashSet<>();

        List<Banco> result = consultaDatosCore.consultarOperadoresFinancieros();

        for (Banco operadorFinanciero : result) {
            String codigoRutaTransito = operadorFinanciero.getCodigoPILA();
            if (codigoRutaTransito != null && codigoRutaTransito.length() >= 3) {
                codigosOperadoresFinancieros.add(codigoRutaTransito.substring(codigoRutaTransito.length() - 3));
            }
        }

        logger.debug("Finaliza consultarOperadoresFinancieros()");
        return codigosOperadoresFinancieros;
    }

    /**
     * @param mapa
     * @return
     */
    private Map<String, Object> consultarVigencia1429(Map<String, Object> mapa) {
        String firmaMetodo = "ArchivosPILAEjecucion.consultarVigencia1429(Map<String, Object>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> contextoTemp = mapa;

        Beneficio beneficio1429 = consultaDatosCore.consultarBeneficio(TipoBeneficioEnum.LEY_1429);

        contextoTemp.put(ConstantesContexto.INICIO_1429, beneficio1429.getFechaVigenciaInicio());
        contextoTemp.put(ConstantesContexto.FIN_1429, beneficio1429.getFechaVigenciaFin());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return contextoTemp;
    }

    /** 
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#validarArchivosOISincrono(java.util.List, java.lang.String, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void validarArchivosOISincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador) {
        String firmaMetodo = "validarArchivosOISincrono(List<IndicePlanilla>, String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		validarArchivosOI(indices, usuario != null ? usuario : USUARIO_NO_IDENTIFICADO, idProcesoAgrupador, true, false, false);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#validarArchivosOFSincrono(java.util.List, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RespuestaServicioDTO> validarArchivosOFSincrono(List<IndicePlanillaOF> indices, String usuario) {
        String firmaMetodo = "validarArchivosOFSincrono(List<IndicePlanillaOF>, String)";
        logger.info("**__**validarArchivosOFSincrono "+ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<RespuestaServicioDTO> result = validarArchivosOFProceso(indices, usuario);
        logger.info("**__**Fin validarArchivosOFSincrono "+ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#reprocesarPlanilla(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.FasePila2Enum, java.lang.Boolean, java.lang.String)
     */
    @Override
    @Asynchronous
    public void reprocesarPlanilla(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso) {
        String firmaMetodo = "reprocesarPlanilla(Long, FasePila2Enum, Boolean, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        gestorPila2.reprocesarPlanilla(idIndicePlanilla, faseProceso, esSimulado,
                usuarioProceso != null ? usuarioProceso : USUARIO_NO_IDENTIFICADO);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#reprocesarPlanillaSinc(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.FasePila2Enum, java.lang.Boolean, java.lang.String)
     */
    @Override
    public void reprocesarPlanillaSinc(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso) {
        String firmaMetodo = "reprocesarPlanillaSinc(Long, FasePila2Enum, Boolean, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        gestorPila2.reprocesarPlanilla(idIndicePlanilla, faseProceso, esSimulado,
                usuarioProceso != null ? usuarioProceso : USUARIO_NO_IDENTIFICADO);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#validarArchivosOISeleccion(java.util.List, java.lang.String, java.lang.Long)
     */
    @Override
	public void validarArchivosOISeleccion(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador){
        String firmaMetodo = "validarArchivosOISeleccion(List, String, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // se preparan los DTO para la validación del B0 de los archivos seleccionados y se procede con la validación
        ArchivoPilaDTO indiceDTO = null;
        for (IndicePlanilla indicePlanilla : indices) {
            indiceDTO = new ArchivoPilaDTO();
            indiceDTO.setUsuario(usuario);
            indiceDTO.setIndicePlanilla(indicePlanilla);
            indiceDTO.setFileName(indicePlanilla.getNombreArchivo());
            indiceDTO.setIdentificadorDocumento(indicePlanilla.getIdDocumento());
            indiceDTO.setVersionDocumento(indicePlanilla.getVersionDocumento());
            
            cargarArchivo(indiceDTO, usuario, indicePlanilla.getTipoCargaArchivo(), Boolean.TRUE);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        validarArchivosOISincrono(indices, usuario, idProcesoAgrupador);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#ejecutarPila1SinCarga(java.lang.String)
     */
    @Override
    @Deprecated
    public void ejecutarPila1SinCarga(String usuarioCarga) {
        String firmaMetodo = "ejecutarPila1SinCarga(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void conciliarArchivosOIyOFSincrono(String usuario,List<Long> indicesPlanilla){
    	conciliarArchivosOIyOF(usuario,indicesPlanilla);
    }
    
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#conciliarArchivosOIyOF(java.lang.String)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void conciliarArchivosOIyOF(String usuario,List<Long> indicesPlanilla) {
    	long timeStart = System.nanoTime();
    	
        String firmaMetodo = "conciliarArchivosOIyOF(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<IndicePlanilla> indicesOI = new ArrayList<IndicePlanilla>();
        if(indicesPlanilla == null || indicesPlanilla.isEmpty()){
            logger.info(" Ingresos a conultar consultarPlanilalsOIDetenidasPorConciliar");
            indicesOI = persistenciaEstados.consultarPlanilalsOIDetenidasPorConciliar();
        }else{
            logger.info(" Ingresos a conultar consultarPlanilalsOISegunSpReiniciarPlanillas cant: "+indicesPlanilla.size());
            indicesOI = persistenciaEstados.consultarPlanilalsOISegunSpReiniciarPlanillas(indicesPlanilla);
        }
       	
        // una vez se termina la validación de los archivos OF, se solicita la validación de archivos OI que apliquen
       	if (indicesOI != null && !indicesOI.isEmpty()) {
       		Long idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.VALIDACION_BATCH);
            validarArchivosOI(indicesOI, usuario, idProceso, true, true, true);
            if (idProceso != null) {
                finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
            }
        } else {
        	logger.info(firmaMetodo + " no hay planillas detenidas");
        }
       	
       	
       	List<IndicePlanilla> indicesOIVAlorCero = persistenciaEstados.consultarPlanilalsOIDetenidasValorCero();
       	List<IndicePlanilla> indicesOIVAlorCeroP = new ArrayList<IndicePlanilla>();
       	for (IndicePlanilla indicePlanilla : indicesOIVAlorCero ) {
       		EstadoProcesoArchivoEnum estadoB6 = EstadoProcesoArchivoEnum.NO_REQUIERE_CONCILIACION;

            // se registra el estado del bloque
            try {
				gestorEstados.registrarEstadoArchivo(indicePlanilla, estadoB6, AccionProcesoArchivoEnum.EN_ESPERA, "", 6, null);
				indicesOIVAlorCeroP.add(indicePlanilla);
			} catch (ErrorFuncionalValidacionException e) {
				// TODO Auto-generated catch block
			}
       	}
       	
        // una vez se termina la validación de los archivos OF, se solicita la validación de archivos OI que apliquen
       	if (indicesOIVAlorCeroP != null && !indicesOIVAlorCeroP.isEmpty()) {
       		Long idProceso = instanciarProceso(usuario, TipoProcesoPilaEnum.VALIDACION_BATCH);
            validarArchivosOI(indicesOIVAlorCeroP, usuario, idProceso, true, false, true);
            if (idProceso != null) {
                finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
            }
        } else {
        	logger.info(firmaMetodo + " no hay planillas detenidas valor cero");
        }
       	  /*COMENTADI POR NUEVO PROCESO DE CATEGORIAS GLPI 58490 24/05/2022
       	EjecutarCalculoCategoriasMasiva ejecutar = new EjecutarCalculoCategoriasMasiva();
        ejecutar.execute();
        */

        long timeEnd = System.nanoTime();
    	logger.info(firmaMetodo + " tiempo ejecución en : " + TimeUnit.SECONDS.convert((timeEnd - timeStart), TimeUnit.NANOSECONDS));
    	
    	logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#actualizarIndiceYEstadoBloque(com.asopagos.entidades.pila.procesamiento.IndicePlanilla,
     *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum, com.asopagos.pila.dto.RespuestaValidacionDTO, java.lang.Integer,
     *      com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum)
     */
    @Override
    public RespuestaValidacionDTO actualizarIndiceYEstadoBloque(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado,
            RespuestaValidacionDTO result, Integer bloque, AccionProcesoArchivoEnum accionPredeterminada) {
        String firmaMetodo = "actualizarIndiceYEstadoBloque(IndicePlanilla, EstadoProcesoArchivoEnum, Integer, AccionProcesoArchivoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        BloqueValidacionEnum bloqueActual = result.getBloqueSiguente();

        // se verifica sí se recibe la acción por aparte
        AccionProcesoArchivoEnum accion = null;
        if (accionPredeterminada != null) {
            accion = accionPredeterminada;
        }
        else {
            accion = FuncionesValidador.determinarAccion(estado, indicePlanilla.getTipoArchivo());
        }

        RespuestaValidacionDTO resultTemp = result;
        try {
            // se registra el estado del bloque
            gestorEstados.registrarEstadoArchivo(indicePlanilla, estado, accion, "", bloque, null);

            // se actualiza el estado del índice
            indicePlanilla.setEstadoArchivo(estado);
            persistencia.actualizarIndicePlanillas(indicePlanilla);
        } catch (ErrorFuncionalValidacionException e) {
            // en este caso, se presenta un error por estado inválido al momento de actualizar el estado por bloque
            // se debe agregar el error a la respuesta y anular el índice

            indicePlanilla.setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);
            persistencia.actualizarIndicePlanillas(indicePlanilla);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        } catch(Exception e){
            logger.error("Error al actualizar el indice planilla: "+ indicePlanilla.getId() + " Exception: "+e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    

    @Override
	public void cargarArchivosParalelo(List<ArchivoPilaDTO> archivosDescargados, DatosProcesoFtpDTO datosControl,
			Boolean ejecutarB0, String usuario) {
		String firmaMetodo = "conectarYDescargarArchivos(ConexionOperadorInformacion, List<ArchivoPilaDTO>, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (!archivosDescargados.isEmpty()) {
			// se recorre el arreglo de DTOs para realizar la carga
			try {
				List<IndicePlanilla> indicesOICarga = new ArrayList<>();
				for (ArchivoPilaDTO archivoPilaDTO : archivosDescargados) {
					cargaDeArchivoParalela(archivoPilaDTO, datosControl, ejecutarB0);

					if (archivoPilaDTO.getIndicePlanilla() != null) {
						indicesOICarga.add(archivoPilaDTO.getIndicePlanilla());

						if (archivoPilaDTO.getIndicePlanilla() != null) {

							if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga())
									&& ejecutarB0 != false) {
								archivoPilaDTO.getIndicePlanilla().setEnLista(true);
							}

							IndicePlanilla indice2 = persistencia
									.consultarPlanillaOIPorId(archivoPilaDTO.getIndicePlanilla().getId());

							if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga())
									&& ejecutarB0 != false) {
								indice2.setEnLista(true);
							}

							persistencia.actualizarIndicePlanillas(indice2);
						}
					}
				}
				if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(datosControl.getTipoCarga()) && ejecutarB0 != false) {
					validarArchivosOI(indicesOICarga, usuario, datosControl.getIdProceso(), true, false, false);
				}

			} catch (Exception e) {
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
}
