package com.asopagos.pila.service.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.pila.parametrizacion.NormatividadFechaVencimiento;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.soporte.ProcesoPila;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContexto;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.DatosProcesoFtpDTO;
import com.asopagos.pila.dto.ErrorValidacionValorMoraDTO;
import com.asopagos.pila.dto.OperadorInformacionDTO;
import com.asopagos.pila.dto.ProcesoPilaDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.pila.service.ArchivosPILAService;
import com.asopagos.pila.service.IArchivosPILAEjecucion;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
/**
 * Servicio de archivos PILA
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 * @author Ricardo Hernandez Cediel. <hhernandez@heinsohn.com.co>
 * @author Alfonso Baquero E. <abaquero@heinsohn.com.co>
 */
@Stateless
public class ArchivosPILABusiness implements ArchivosPILAService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ArchivosPILAService.class);

    @Inject
    private IPersistenciaDatosValidadores persistencia;

    @Inject
    private IArchivosPILAEjecucion archivosPilaEjecucionService;

    @Inject
    private IPersistenciaPreparacionContexto persistenciaPreparacion;

    @Context
    private ServletContext context;

    @Inject
    private IGestorPila2 gestorPila2SinValidacion;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.service.ArchivosPILAService#consultarPlanillas(com.
     * asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum)
     */
    @Override
    public List<ArchivoPilaDTO> consultarPlanillas(UserDTO userDTO) {
        logger.debug("Inicia consultarPlanillas(EstadoProcesoArchivoEnum, UserDTO)");
        List<ArchivoPilaDTO> result = new ArrayList<ArchivoPilaDTO>();

        List<IndicePlanilla> indices_OI = null;

        List<EstadoProcesoArchivoEnum> estados = new ArrayList<EstadoProcesoArchivoEnum>();
        estados.add(EstadoProcesoArchivoEnum.DESCARGADO);
        
        List<TipoCargaArchivoEnum> tiposCarga = new ArrayList<TipoCargaArchivoEnum>();
        tiposCarga.add(TipoCargaArchivoEnum.AUTOMATICA_WEB);

        indices_OI = persistencia.consultarIndicePorEstadoMultipleOI(estados, tiposCarga, null);

        ArchivoPilaDTO dto = null;
        for (IndicePlanilla indicePlanilla : indices_OI) {
            // sí el índice es procesable y no se encuentra presente en una
            // lista de ejecución, se agrega
            if (indicePlanilla.getProcesar() && !indicePlanilla.getEnLista()) {
                dto = new ArchivoPilaDTO();
                
                dto.setIdIndicePlanillaOI(indicePlanilla.getId());
                dto.setFileName(indicePlanilla.getNombreArchivo());
                dto.setFechaModificacion(indicePlanilla.getFechaFtp().getTime());

                result.add(dto);
            }
        }

        logger.debug("Finaliza consultarPlanillas(EstadoProcesoArchivoEnum, UserDTO)");
        return result;
    }

    @Override
    public List<RespuestaServicioDTO> cargarArchivoPilaManual(List<ArchivoPilaDTO> listaArchivosPila, UserDTO userDTO) {
        logger.info("Inicia cargarArchivoPilaManual(List<ArchivoPilaDTO>, UserDTO)");
        
        listaArchivosPila = FuncionesValidador.ordenarListadoDatosArchivos(listaArchivosPila, false);

        // se inicializan las listas de índices para proceso
        List<Long>             indicesOI = new ArrayList<>();
        List<IndicePlanillaOF> indicesOF = new ArrayList<>();

        // se instancia el proceso
        Long idProceso = archivosPilaEjecucionService.instanciarProceso(userDTO.getNombreUsuario(), TipoProcesoPilaEnum.CARGA);

        //se procesa la carga de los archivos
        List<RespuestaServicioDTO> listaRespuestasServicio = procesarCargaArchivos(listaArchivosPila, userDTO, indicesOI, indicesOF,
                idProceso);

        //se procesan los indices tipo OI
        logger.info("**__** indicesOF: "+indicesOF.size());
        procesarIndicesOI(userDTO, indicesOI, idProceso, listaRespuestasServicio);
        
        //se procesan los indices tipo OF
        if (!indicesOF.isEmpty()) {
        	logger.info("Mantis 0254707 archivosPilaEjecucionService.validarArchivosOF incio");
            archivosPilaEjecucionService.validarArchivosOF(indicesOF, userDTO.getNombreUsuario());
            logger.info("Mantis 0254707 archivosPilaEjecucionService.validarArchivosOF fin");
        } 
 
        //se finaliza el proceso
        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info("Finaliza cargarArchivoPilaManual(List<ArchivoPilaDTO>, UserDTO)");
        return listaRespuestasServicio;
    }
    

    private void procesarIndicesOI(UserDTO userDTO, List<Long> indicesOI, Long idProceso,
            List<RespuestaServicioDTO> listaRespuestasServicio) {
        // una vez finaliza el recorrido de carga, se invoca la ejecución
        // asíncrona del procesamiento de los índices válidos
        if (indicesOI.isEmpty()) {
            return;
        }
        
        List<IndicePlanilla> indicesProceso = persistencia.consultarIndicesPorListaIds(indicesOI);
        
        // se actualiza el listado de los índices que no fueron anulados
        for (IndicePlanilla indicePlanilla : indicesProceso) {
            indicesOI.remove(indicePlanilla.getId());
        }
        
        // se actualizan los estados en la respuesta
        for (Long indice : indicesOI) {
            for (RespuestaServicioDTO respuestaServicio : listaRespuestasServicio) {
                TipoArchivoPilaEnum tipoArchivo = FuncionesValidador.getTipoArchivo(respuestaServicio.getFileName());
                if(respuestaServicio.getIdIndicePlanilla().equals(indice) && tipoArchivo.isReproceso()){
                    respuestaServicio.setEstado(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR);
                    // se actualiza el mensaje de respuesta
                    respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
                }else if(respuestaServicio.getIdIndicePlanilla().equals(indice) && !tipoArchivo.isReproceso()){
                    logger.info("Entra DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO 1");
                    respuestaServicio.setEstado(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO);
                    // se actualiza el mensaje de respuesta
                    respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
                }
            }
        } 
         logger.info("procesarIndicesOI -> archivosPilaEjecucionService.validarArchivosOIAsincrono idProceso: "+idProceso);
        archivosPilaEjecucionService.validarArchivosOIAsincrono(indicesProceso, userDTO.getNombreUsuario(), idProceso);
        
    }

    @Override
    public void reprocesarB0F(List<Long> indicesPlanilla,UserDTO userDTO){
        // se instancia el proceso
       
        Long idProceso = archivosPilaEjecucionService.instanciarProceso(userDTO.getNombreUsuario(), TipoProcesoPilaEnum.CARGA);
        List<IndicePlanillaOF> indicesOF =  new ArrayList<IndicePlanillaOF>();        
        for(Long ids : indicesPlanilla){
            IndicePlanillaOF indiceOF = null;
            indiceOF = persistencia.consultarPlanillaOFPorId(ids);
            indicesOF.add(indiceOF);
        }
        if (!indicesOF.isEmpty()) {
        	logger.info("Mantis 0254707 archivosPilaEjecucionService.validarArchivosOF incio");
            archivosPilaEjecucionService.validarArchivosOF(indicesOF, userDTO.getNombreUsuario());
            logger.info("Mantis 0254707 archivosPilaEjecucionService.validarArchivosOF fin");
        } 
        
        
        logger.info("procesarIndicesOI -> archivosPilaEjecucionService.validarArchivosOIAsincrono idProceso: "+idProceso);
        //se finaliza el proceso
        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }
    }

    
    private List<RespuestaServicioDTO> procesarCargaArchivos(List<ArchivoPilaDTO> listaArchivosPila, UserDTO userDTO, List<Long> indicesOI,
            List<IndicePlanillaOF> indicesOF, Long idProceso) {
        List<RespuestaServicioDTO> listaRespuestasServicio = new ArrayList<RespuestaServicioDTO>();
        
        // se recorre el listado
        for (ArchivoPilaDTO archivoPilaDTO : listaArchivosPila) {
            
            IndicePlanilla   indice   = null;
            IndicePlanillaOF indiceOF = null;
            RespuestaServicioDTO respuestaServicio = null;

            try {
                respuestaServicio = archivosPilaEjecucionService.cargarArchivo(archivoPilaDTO, userDTO.getNombreUsuario(),
                        TipoCargaArchivoEnum.MANUAL, Boolean.TRUE);
            } catch (TechnicalException exception) {
                // se captura el error técnico para marcar la finalización fallida del proceso
                archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
                logger.debug("Finaliza cargarArchivoPilaManual(List<ArchivoPilaDTO>, UserDTO) - " + exception.getMessage());
                // inmediatamente, se lanza de nuevo la misma excepción para que sea presentada en pantalla
                throw exception;
            }

            // si la respuesta es positiva para la carga del archivo, se agrega a la lista de archivos a procesar
            if (respuestaServicio == null) {
                continue;
            }
            
            switch (respuestaServicio.getEstado()) {
                case CARGADO:
                case CARGADO_REPROCESO:
                case CARGADO_REPROCESO_ACTUAL:
                    // estados de OI
                	logger.info("Mantis 0254707 persistencia.consultarPlanillaOIPorId incio " + respuestaServicio.getIdIndicePlanilla());
                    indice = persistencia.consultarPlanillaOIPorId(respuestaServicio.getIdIndicePlanilla());
                    logger.info("Mantis 0254707 persistencia.consultarPlanillaOIPorId fin " + respuestaServicio.getIdIndicePlanilla());

                    if (indice != null) {
                        indicesOI.add(indice.getId());

                        // se indica que el archivo entra en lista de procesamiento para que no sea presentado nuevamente
                        // en la pantalla de la HU-211-390
                        indice.setEnLista(true);
                        persistencia.actualizarIndicePlanillas(indice);
                    }
                    break;
                case CARGADO_EXITOSAMENTE:
                    // estados de OF
                	logger.info("Mantis 0254707 persistencia.consultarPlanillaOFPorId incio " + respuestaServicio.getIdIndicePlanilla());
                    indiceOF = persistencia.consultarPlanillaOFPorId(respuestaServicio.getIdIndicePlanilla());
                    logger.info("Mantis 0254707 persistencia.consultarPlanillaOFPorId fin " + respuestaServicio.getIdIndicePlanilla() +"indiceOF.getId: "+indiceOF.getId());

                    if (indiceOF != null) {
                        indicesOF.add(indiceOF);
                    }
                    break;
                default:
                    break;
            }

            listaRespuestasServicio.add(respuestaServicio);
        }
        return listaRespuestasServicio;
    }
    
    @Override
    public void reprocesarMundo1(List<Long> indicesPlanilla){
        logger.info("Inicia reprocesarMundo1 "+ indicesPlanilla.toString());
        Long idProceso = null;
        idProceso = archivosPilaEjecucionService.instanciarProceso("SISTEMA-SERVICIO", TipoProcesoPilaEnum.CARGA);
        try {
	        if (!indicesPlanilla.isEmpty()) {
	            List<IndicePlanilla> indicesProceso = persistencia.consultarIndicesPorListaIds(indicesPlanilla);
	            archivosPilaEjecucionService.validarArchivosOIAsincrono(indicesProceso, "SISTEMA-SERVICIO", idProceso);
	        }
        } catch (Exception e) {
        	logger.error(ConstantesComunes.INICIO_LOGGER + "Inicia reprocesarMundo1", e);
        	archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
        }
        
        archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        logger.info("Inicia finaliza ");
    }
    
    @Override
    public void reprocesarB0(List<Long> indicesPlanilla,UserDTO userDTO){
        // se instancia el proceso
        Long idProceso = archivosPilaEjecucionService.instanciarProceso(userDTO.getNombreUsuario(), TipoProcesoPilaEnum.CARGA);
        for(Long ids : indicesPlanilla){
         // estados de OI
         IndicePlanilla   indice   = null;
                    logger.info("Mantis 0254707 persistencia.consultarPlanillaOIPorId incio " + ids);
                    indice = persistencia.consultarPlanillaOIPorId(ids);
                    logger.info("Mantis 0254707 persistencia.consultarPlanillaOIPorId fin " + ids);

                    if (indice != null) {
                        //indicesOI.add(indice.getId());

                        // se indica que el archivo entra en lista de procesamiento para que no sea presentado nuevamente
                        // en la pantalla de la HU-211-390
                        indice.setEnLista(true);
                        persistencia.actualizarIndicePlanillas(indice);
                    }
        }
        List<IndicePlanilla> indicesProceso = persistencia.consultarIndicesPorListaIds(indicesPlanilla);
        
        
         logger.info("procesarIndicesOI -> archivosPilaEjecucionService.validarArchivosOIAsincrono idProceso: "+idProceso);
        archivosPilaEjecucionService.validarArchivosOIAsincrono(indicesProceso, userDTO.getNombreUsuario(), idProceso);
        //se finaliza el proceso
        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }
    }



    private void reprocesarMundo1SinValidacion(List<Long> indicesPlanilla){
        logger.info("Inicia reprocesarMundo1SinValidacion "+ indicesPlanilla.toString());
        Long idProceso = null;
        idProceso = archivosPilaEjecucionService.instanciarProceso("SISTEMA-SERVICIO", TipoProcesoPilaEnum.CARGA);
        try {
	        if (!indicesPlanilla.isEmpty()) {
	            List<IndicePlanilla> indicesProceso = persistencia.consultarIndicesPorListaIds(indicesPlanilla);
	            archivosPilaEjecucionService.validarArchivosOISinValidacionAsincrono(indicesProceso, "SISTEMA-SERVICIO", idProceso);
	        } 
        } catch (Exception e) {
        	logger.error(ConstantesComunes.INICIO_LOGGER + "Inicia reprocesarMundo1", e);
        	archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);
        }
        
        archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        logger.info("Inicia finaliza ");
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.service.ArchivosPILAService#inactivarArchivoPila(java.
     * util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<RespuestaServicioDTO> inactivarArchivoPila(List<ArchivoPilaDTO> listaArchivoPila, UserDTO userDTO) {
        logger.debug("Inicia inactivarArchivoPila(List<ArchivoPilaDTO>, UserDTO)");
        List<RespuestaServicioDTO> result = new ArrayList<RespuestaServicioDTO>();

        Long idProceso = null;

        // se instancia el proceso
        idProceso = archivosPilaEjecucionService.instanciarProceso(userDTO.getNombreUsuario(), TipoProcesoPilaEnum.ELIMINACION);

        // se recorre el listado
        for (ArchivoPilaDTO archivoPilaDTO : listaArchivoPila) {
            try {
                result.add(inactivarArchivoPila(archivoPilaDTO, userDTO));
            } catch (Exception exception) {
                // se captura el error técnico para marcar la finalización
                // fallida del proceso
                archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

                logger.error("Finaliza inactivarArchivoPila(List<ArchivoPilaDTO>, UserDTO)", exception);
                // inmediatamente, se lanza de nuevo la misma excepción para que
                // sea presentada en pantalla
                throw exception;
            }
        }

        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.debug("Finaliza inactivarArchivoPila(List<ArchivoPilaDTO>, UserDTO)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.service.ArchivosPILAService#procesarSeleccionManualOI(
     * java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void procesarSeleccionOI(List<ArchivoPilaDTO> listaArchivoPila, UserDTO userDTO) {
    	logger.info("Inicia procesarSeleccionOI(List<ArchivoPilaDTO>, UserDTO)");
        //List<RespuestaServicioDTO> result = new ArrayList<RespuestaServicioDTO>();

        Long idProceso = null;

        // se instancia el proceso
        idProceso = archivosPilaEjecucionService.instanciarProceso(userDTO.getNombreUsuario(), TipoProcesoPilaEnum.VALIDACION_WEB);
        
        // listado de los IDs de planilla
        
        // limpia repetidos? por qué es necesario esto?
        List<Long> idsPlanillas = new ArrayList<>();
        for (ArchivoPilaDTO archivoPilaDTO : listaArchivoPila) {
            if(!idsPlanillas.contains(archivoPilaDTO.getIdIndicePlanillaOI())){
                idsPlanillas.add(archivoPilaDTO.getIdIndicePlanillaOI());
            }
        }
        
        int contador = 0;
        int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
        List<Long> idsPlanillasPaginado = new ArrayList<>();
        for (Long id : idsPlanillas) {
        	idsPlanillasPaginado.add(id);
        	contador++;
            if(contador == pagina){
            	try {
            		procesarSeleccionOIPaginado(idsPlanillasPaginado, idProceso, userDTO);
            	} catch (Exception e) {
            		logger.error("error de intento por selección");
				}
            	
            	contador = 0;
            	idsPlanillasPaginado = new ArrayList<>();
            }
        }
        if(contador > 0){
        	try {
        		procesarSeleccionOIPaginado(idsPlanillasPaginado, idProceso, userDTO);
        	} catch (Exception e) {
				logger.error("error de intento por selección");
			}
        }
        
        
        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }

        logger.info("Finaliza procesarSeleccionOI(List<ArchivoPilaDTO>, UserDTO)");
        // y que retorna acá?
        //return result;
    }
    
    /**
     * Metodo que extrae la logica del procesamiento de las planillas seleccionadas luego de una carga por ftp.
     * @param idsPlanillas
     * @param idProceso
     * @param userDTO
     */
    private void procesarSeleccionOIPaginado(List<Long> idsPlanillas, Long idProceso, UserDTO userDTO) {
    	logger.info("Inicia procesarSeleccionOIPaginado(List<Long> idsPlanillas, Long idProceso, UserDTO userDTO)");
        
        // se consultan los índices
        List<IndicePlanilla> indicesOI = null;
        if(idsPlanillas.isEmpty()){
            indicesOI = Collections.emptyList();
        }else{
            indicesOI = persistencia.consultarIndicesPorListaIds(idsPlanillas);
        }
        
        // se les marca como parte de un listado y se preparan nuevos DTOs para la descarga de los archivos
        for (IndicePlanilla indice : indicesOI) {
            indice.setEnLista(Boolean.TRUE);
        }
        
        // se actualiza la lista en BD
        persistencia.actualizarListadoIndicePlanillas(indicesOI);
        

        // una vez finaliza el recorrido de carga, se invoca la ejecución
        // asíncrona del procesamiento de los índices válidos
        try {
            archivosPilaEjecucionService.validarArchivosOISeleccion(indicesOI, userDTO.getNombreUsuario(), idProceso);
        } catch (TechnicalException exception) {
            // se captura el error técnico para marcar la finalización fallida
            // del proceso
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

            logger.info("Finaliza procesarSeleccionOIPaginado(List<Long> idsPlanillas, Long idProceso, UserDTO userDTO) - " + exception.getMessage());
            // inmediatamente, se lanza de nuevo la misma excepción para que sea
            // presentada en pantalla
            throw exception;
        }

        logger.info("Finaliza procesarSeleccionOIPaginado(List<Long> idsPlanillas, Long idProceso, UserDTO userDTO)");
        //return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#
     * consultarOperadoresInformacion(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<OperadorInformacionDTO> consultarOperadoresInformacion(UserDTO userDTO) {
        logger.debug("Inicia consultarOperadoresInformacion(UserDTO)");

        List<OperadorInformacionDTO> result = new ArrayList<OperadorInformacionDTO>();

        List<OperadorInformacion> operadores = null;

        try {
            operadores = persistencia.consultarOperadoresInformacionCcf(
                    (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
        } catch (ErrorFuncionalValidacionException e) {
            logger.debug("Finaliza consultarOperadoresInformacion(UserDTO) - " + e.getMessage());
            return null;
        }

        if (operadores != null) {
            for (OperadorInformacion operadorInformacion : operadores) {
                OperadorInformacionDTO dto = new OperadorInformacionDTO();

                dto.setCodigo(operadorInformacion.getCodigo());
                dto.setId(operadorInformacion.getId());
                dto.setNombre(operadorInformacion.getNombre());

                result.add(dto);
            }
        }

        logger.debug("Finaliza consultarOperadoresInformacion(UserDTO)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#
     * iniciarProcesamientoAutomatico(com.asopagos.pila.dto.
     * OperadorInformacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public RespuestaServicioDTO iniciarDescargaAutomatica(OperadorInformacionDTO operadorInformacionDTO, UserDTO userDTO) {
        String firmaMetodo = "iniciarDescargaAutomatica(OperadorInformacionDTO, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaServicioDTO result = new RespuestaServicioDTO();

        List<ProcesoPila> procesos = null;

        List<TipoProcesoPilaEnum> tipos = new ArrayList<>();
        tipos.add(TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA);
        tipos.add(TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA_WEB);
        
        procesos = persistencia.consultarProcesoEstado(tipos, EstadoProcesoValidacionEnum.PROCESO_ACTIVO);

        if (procesos == null || procesos.isEmpty()) {
            // sí no hay procesos activos, se procede

            archivosPilaEjecucionService.descargarYCargarArchivosAsincrono(operadorInformacionDTO.getId(), userDTO.getNombreUsuario(),
                    TipoCargaArchivoEnum.AUTOMATICA_WEB);

            result.setMensajeRespuesta(MessagesConstants.PROCESO_INICIADO);
            result.setEstado(EstadoProcesoArchivoEnum.EN_PROCESO);
        }
        else {
            result.setMensajeRespuesta(MessagesConstants.PROCESO_EN_CURSO);
            result.setEstado(EstadoProcesoArchivoEnum.ANULADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#
     * consultarProcesosValidacionActivos(com.asopagos.rest.security.dto.
     * UserDTO)
     */
    @Override
    public List<ProcesoPilaDTO> consultarProcesosValidacionActivos(UserDTO userDTO) {
        logger.debug("Inicia consultarProcesosValidacionActivos(UserDTO)");

        List<ProcesoPilaDTO> result = new ArrayList<ProcesoPilaDTO>();

        List<ProcesoPila> procesos = null;

        List<TipoProcesoPilaEnum> tipos = new ArrayList<>();
        tipos.add(TipoProcesoPilaEnum.VALIDACION_WEB);
        procesos = persistencia.consultarProcesoEstado(tipos, EstadoProcesoValidacionEnum.PROCESO_ACTIVO);
    
        if (procesos != null) {
            for (ProcesoPila procesoPila : procesos) {
                ProcesoPilaDTO dto = new ProcesoPilaDTO();

                dto.setEstadoProceso(procesoPila.getEstadoProceso());
                dto.setFechaFinProceso(procesoPila.getFechaFinProceso());
                dto.setFechaInicioProceso(procesoPila.getFechaInicioProceso());
                dto.setId(procesoPila.getId());
                dto.setNumeroRadicado(procesoPila.getNumeroRadicado());
                dto.setTipoProceso(procesoPila.getTipoProceso());
                dto.setUsuarioProceso(procesoPila.getUsuarioProceso());

                result.add(dto);
            }
        }

        logger.debug("Finaliza consultarProcesosValidacionActivos(UserDTO)");
        return result;
    }

    /**
     * Método para obtener la entrada de indice de planilla
     * 
     * @param archivoPila
     *        <code>ArchivoPilaDTO </code> El DTO de tranferencia de datos
     *        para el consumo de los servicios de PILA
     * 
     * @return <code>IndicePlanilla</code> Entrada de índice de planilla o null
     */
    private IndicePlanilla getIndicePlanilla(ArchivoPilaDTO archivoPila) {
        IndicePlanilla indicePlanilla = null;

        // se revisa sí el DTO cuenta con el ID de la planilla
        if (archivoPila.getIdIndicePlanillaOI() != null) {
            indicePlanilla = persistencia.consultarPlanillaOIPorId(archivoPila.getIdIndicePlanillaOI());
        }

        // sí no se encontró algún dato en contexto ni un ID en el DTO, se
        // buscan en el nombre del archivo (sí se cuenta con un nombre de archivo)
        if (indicePlanilla == null && archivoPila.getFileName() != null) {
            TipoArchivoPilaEnum tipoArchivo = FuncionesValidador.getTipoArchivo(archivoPila.getFileName());
            Long numeroPlanilla = (Long) FuncionesValidador.obtenerCampoNombreArchivo(tipoArchivo,
                    CamposNombreArchivoEnum.NUMERO_PLANILLA_OI, archivoPila.getFileName());
            String codOperador = (String) FuncionesValidador.obtenerCampoNombreArchivo(tipoArchivo,
                    CamposNombreArchivoEnum.CODIGO_OPERADOR_OI, archivoPila.getFileName());

            // sí se cuenta con ambos datos, se lleva a cabo la búsqueda de la
            // entrada de índice de planilla
            if (numeroPlanilla != null && tipoArchivo != null && codOperador != null) {
                // sólo sí el archivo no es de OF
                if (!TipoArchivoPilaEnum.ARCHIVO_OF.equals(tipoArchivo))
                    indicePlanilla = persistencia.consultarIndicePlanillaTipo(numeroPlanilla, tipoArchivo, codOperador);
            }
        }

        return indicePlanilla;
    }

    /**
     * Método para obtener la entrada de indice de planilla de Operador
     * Financiero
     * 
     * @param <code>archivoPila</code>
     *        DTO con la información del archivo recibida desde pantalla
     */
    private IndicePlanillaOF getIndicePlanillaOF(ArchivoPilaDTO archivoPila) {
        logger.debug("Inicia getIndicePlanillaOF(ArchivoPilaDTO)");
        IndicePlanillaOF indicePlanillaOF = null;

        // se revisa sí el DTO cuenta con el ID de la planilla
        if (archivoPila.getIdIndicePlanillaOF() != null) {
            indicePlanillaOF = persistencia.consultarPlanillaOFPorId(archivoPila.getIdIndicePlanillaOF());
        }
        else {
            // sí no se encontró algún dato en contexto ni un ID en el DTO, se
            // buscan en el nombre del archivo
            try {
                TipoArchivoPilaEnum tipoArchivo = FuncionesValidador.getTipoArchivo(archivoPila.getFileName());

                Date fechaRecaudo = ((Calendar) FuncionesValidador.obtenerCampoNombreArchivo(tipoArchivo,
                        CamposNombreArchivoEnum.FECHA_RECAUDO_OF, archivoPila.getFileName())).getTime();

                String codBanco = (String) FuncionesValidador.obtenerCampoNombreArchivo(tipoArchivo,
                        CamposNombreArchivoEnum.CODIGO_BANCO_OF, archivoPila.getFileName());

                // se indaga a partir del nombre del archivo recibido
                indicePlanillaOF = persistencia.consultarArchivoOFEnIndice(fechaRecaudo, codBanco);
            } catch (NullPointerException e) {
            }
        }

        logger.debug("Finaliza getIndicePlanillaOF(ArchivoPilaDTO)");
        return indicePlanillaOF;
    }

    /**
     * Método para llevar a cabo la inactivación de una entrada del índice de
     * planillas
     * 
     * @param archivoPila
     *        DTO que contiene los datos requeridos para ubicar el índice a
     *        inactivar
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación,
     *        traido desde el contexto de la aplicación
     * @return RespuestaServicioDTO Respuesta entregada por la operación
     */
    private RespuestaServicioDTO inactivarArchivoPila(ArchivoPilaDTO archivoPila, UserDTO userDTO) {
        RespuestaServicioDTO respuesta = new RespuestaServicioDTO();

        logger.debug("Inicia inactivarArchivoPila(ArchivoPilaDTO, UserDTO)");

        Object indicePlanilla = null;


        indicePlanilla = getIndicePlanilla(archivoPila);

        if (indicePlanilla == null) {
            indicePlanilla = getIndicePlanillaOF(archivoPila);
        }

        // sí se cuenta con un índice, se solicita su eliminación
        if (indicePlanilla != null) {

            persistencia.eliminarIndicePlanilla(indicePlanilla, Calendar.getInstance().getTime(), userDTO.getNombreUsuario());

            String nombreArchivo = null;
            if (indicePlanilla instanceof IndicePlanilla) {
                nombreArchivo = ((IndicePlanilla) indicePlanilla).getNombreArchivo();
            }
            else if (indicePlanilla instanceof IndicePlanillaOF) {
                nombreArchivo = ((IndicePlanillaOF) indicePlanilla).getNombreArchivo();
            }

            respuesta.setFileName(nombreArchivo);
            respuesta.setMensajeRespuesta(MessagesConstants.getMensajeCargaArchivo(nombreArchivo, MessagesConstants.ELIMINACION_EXITOSA));
        }

        logger.debug("Finaliza inactivarArchivoPila(ArchivoPilaDTO, UserDTO)");
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#consultarOperadoresInformacionCcf(java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<OperadorInformacionDTO> consultarOperadoresInformacionCcf(String codigoCcf, UserDTO userDTO) {
        logger.debug("Inicia consultarOperadoresInformacionCcf(String, UserDTO)");

        List<OperadorInformacionDTO> result = null;

        try {
            List<OperadorInformacion> operadoresInformacion = persistencia.consultarOperadoresInformacionCcf(codigoCcf);

            result = new ArrayList<OperadorInformacionDTO>();

            for (OperadorInformacion operadorInformacion : operadoresInformacion) {
                OperadorInformacionDTO operador = new OperadorInformacionDTO();

                operador.setId(operadorInformacion.getId());

                result.add(operador);
            }
        } catch (ErrorFuncionalValidacionException e) {
            // que no se encuentren OI parametrizados para la CCF se considera
            // error técnico
            logger.debug("Finaliza consultarOperadoresInformacionCcf(String, UserDTO) - " + e.getMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }

        logger.debug("Finaliza consultarOperadoresInformacionCcf(String, UserDTO)");
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.service.ArchivosPILAService#ejecutarProcesoAutomatico(com.asopagos.pila.dto.OperadorInformacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void ejecutarProcesoAutomatico(OperadorInformacionDTO operadorInformacionDTO, UserDTO userDTO) {
        logger.info("Inicia ejecutarProcesoAutomatico(UserDTO)");

        archivosPilaEjecucionService.descargarCargarYValidarArchivos(operadorInformacionDTO.getId(), userDTO.getNombreUsuario());

        logger.info("Finaliza ejecutarProcesoAutomatico(UserDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#validarArchivosOF(java.util.List, java.lang.String)
     */
    @Override
    public void validarArchivosOF(List<IndicePlanillaOF> indicesOF, UserDTO userDTO) {
        logger.debug("Inicia ArchivosPILABusiness.validarArchivosOF(List<IndicePlanillaOF>, UserDTO)");

        archivosPilaEjecucionService.validarArchivosOF(indicesOF, userDTO.getNombreUsuario());

        logger.debug("Finaliza ArchivosPILABusiness.validarArchivosOF(List<IndicePlanillaOF>, UserDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#validarArchivosOI(java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void validarArchivosOI(List<IndicePlanilla> indices, UserDTO usuario) {
        // se instancia el proceso
        Long idProceso = archivosPilaEjecucionService.instanciarProceso(usuario.getNombreUsuario(), TipoProcesoPilaEnum.VALIDACION_WEB);
		try {
			archivosPilaEjecucionService.validarArchivosOISincrono(indices, usuario.getNombreUsuario(), idProceso);
		} catch (Exception e) {
			// se captura el error técnico para marcar la finalización fallida
			// del proceso
			archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

			logger.error("Finaliza validarArchivosOI(List<ArchivoPilaDTO>, UserDTO)", e);
			throw e;
		}
		
        if (idProceso != null) {
            archivosPilaEjecucionService.finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#reprocesarPlanilla(com.asopagos.dto.ArchivoPilaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void reprocesarPlanilla(ArchivoPilaDTO archivoPilaDTO, UserDTO usuario) {
        logger.debug("Inicia ArchivosPILABusiness.reprocesarPlanillaOI( ArchivoPilaDTO )");
        Long idIndicePlanilla = archivoPilaDTO.getIdIndicePlanillaOI();
        FasePila2Enum faseProceso = archivoPilaDTO.getFaseProceso();
        Boolean esSimulado = archivoPilaDTO.getEsSimulado();
        archivosPilaEjecucionService.reprocesarPlanilla(idIndicePlanilla, faseProceso, esSimulado, usuario.getNombreUsuario());
        logger.debug("Finaliza ArchivosPILABusiness.reprocesarPlanillaOI( ArchivoPilaDTO )");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#reprocesarPlanillaSincrono(com.asopagos.dto.ArchivoPilaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void reprocesarPlanillaSincrono(ArchivoPilaDTO archivoPilaDTO, UserDTO usuario) {
        logger.debug("Inicia ArchivosPILABusiness.reprocesarPlanillaSincrono( ArchivoPilaDTO )");
        Long idIndicePlanilla = archivoPilaDTO.getIdIndicePlanillaOI();
        FasePila2Enum faseProceso = archivoPilaDTO.getFaseProceso();
        Boolean esSimulado = archivoPilaDTO.getEsSimulado();
        archivosPilaEjecucionService.reprocesarPlanillaSinc(idIndicePlanilla, faseProceso, esSimulado, usuario.getNombreUsuario());
        logger.debug("Finaliza ArchivosPILABusiness.reprocesarPlanillaSincrono( ArchivoPilaDTO )");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#calcularFechaVencimiento(java.lang.String,
     *      com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum, java.lang.String, com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum,
     *      java.lang.Integer, com.asopagos.enumeraciones.aportes.ClaseAportanteEnum)
     */
    @Override
    public Long calcularFechaVencimiento(String periodo, PeriodoPagoPlanillaEnum oportunidad, String numeroDocumentoAportante,
            TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, ClaseAportanteEnum claseAportante,
            NaturalezaJuridicaEnum naturalezaJuridica) {
        String firmaServicio = "ArchivosPILABusiness.calcularFechaVencimiento(String, PeriodoPagoPlanillaEnum, String, TipoArchivoPilaEnum, "
                + "NaturalezaJuridicaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long result = null;

        NormatividadFechaVencimiento casoEspecifico = consultarCasoNormatividadAportante(numeroDocumentoAportante, periodo, tipoArchivo,
                cantidadPersonas, claseAportante, naturalezaJuridica);

        List<DiasFestivos> festivos = null;
        festivos = persistenciaPreparacion.consultarFestivos();

        if (casoEspecifico != null) {
            Calendar fechaVencimiento = FuncionesValidador.calcularFechaVencimiento(periodo, oportunidad, casoEspecifico, festivos);
            if(fechaVencimiento!=null){
            	result = fechaVencimiento.getTimeInMillis();	
            }            
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Metodo encargado de determinar el caso de normatividad para fecha de vencimiento de aporte
     * @param periodo
     *        Período para el cual se consulta la fecha de vencimiento
     * @param oportunidad
     *        Indicador de oportunidad en para el pago (Mes vencido o mes actual)
     * @param numeroDocumentoAportante
     *        Número de identificación del aportante
     * @param tipoArchivo
     *        Tipo de archivo evaluado (I para dependientes independientes - IP parpensionados)
     * @param cantidadPersonas
     *        Cantidad de trabajadores / independientes / pensionados relacionados
     * @param claseAportante
     *        Clase de aportante de acuerdo a PILA para el aportante
     * @param naturalezaJuridica
     *        Naturaleza Jurídica del aportante
     * @return <b><NormatividadFechaVencimiento></b>
     *         Entrada de normatividad de fecha de vencimiento de aportes aplicable
     */
    private NormatividadFechaVencimiento consultarCasoNormatividadAportante(String numeroDocumentoAportante, String periodo,
            TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, ClaseAportanteEnum claseAportante,
            NaturalezaJuridicaEnum naturalezaJuridica) {
        String firmaServicio = "ArchivosPILABusiness.consultarCasoNormatividadAportante(String, String periodo, "
                + "TipoArchivoPilaEnum, Integer, ClaseAportanteEnum)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        NormatividadFechaVencimiento casoEspecifico = null;

        List<NormatividadFechaVencimiento> casosNormatividad = null;
        casosNormatividad = persistenciaPreparacion.consultarNormatividadVencimiento();

        if (casosNormatividad != null) {
            casoEspecifico = FuncionesValidador.elegirNormatividad(casosNormatividad, numeroDocumentoAportante, periodo, tipoArchivo,
                    cantidadPersonas, claseAportante.getCodigo(), naturalezaJuridica, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return casoEspecifico;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#calcularDiaHabilVencimientoAporte(java.lang.String, java.lang.String,
     *      com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum, java.lang.Integer, com.asopagos.enumeraciones.aportes.ClaseAportanteEnum,
     *      com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum)
     */
    @Override
    public Short calcularDiaHabilVencimientoAporte(String numeroDocumentoAportante, String periodo, TipoArchivoPilaEnum tipoArchivo,
            Integer cantidadPersonas, ClaseAportanteEnum claseAportante, NaturalezaJuridicaEnum naturalezaJuridica) {

        String firmaServicio = "ArchivosPILABusiness.calcularDiaHabilVencimientoAporte(String, String, TipoArchivoPilaEnum, Integer, "
                + "ClaseAportanteEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Short diaVencimiento = null;

        NormatividadFechaVencimiento casoEspecifico = consultarCasoNormatividadAportante(numeroDocumentoAportante, periodo, tipoArchivo,
                cantidadPersonas, claseAportante, naturalezaJuridica);

        if (casoEspecifico != null) {
            diaVencimiento = casoEspecifico.getDiaVencimiento();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return diaVencimiento;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#calcularInteresesDeMora(java.lang.String, java.lang.Long, java.math.BigDecimal)
     */
    @Override
    public BigDecimal calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte) {
        String firmaServicio = "ArchivosPILABusiness.calcularInteresesDeMora(String, Long, BigDecimal)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        BigDecimal result = null;
        Date fechaActual = new Date();
		if (fechaVencimiento != null) {
        	Date fechaVen = new Date(fechaVencimiento);

            // se consultan los períodos de tasa de interes comprendidos entre la fecha de vencimiento y la fecha actual
            List<TasasInteresMora> periodosInteres = persistenciaPreparacion.consultarTasasInteresPorRango(fechaVen, fechaActual);

            ErrorValidacionValorMoraDTO consultaMora = FuncionesValidador.calcularValorMora(periodosInteres, fechaVen, fechaActual,
                    valorAporte);

            if (consultaMora.getIndicioMensaje() == null) {
                result = consultaMora.getValorMoraCalculado();
            }
            else {
                // se presenta un error en el cálculo de la mora

                String mensaje = MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + " :: " + consultaMora.getIndicioMensaje();
                throw new TechnicalException(mensaje);
            }
        } else {
        	result = BigDecimal.ZERO;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#esAportePropio(java.lang.Long)
     */
    @Override
    public Boolean esAportePropio(Long id) {
        String firmaServicio = "ArchivosPILABusiness.esAportePropio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Boolean result = null;

        result = archivosPilaEjecucionService.esAportePropio(id);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#procesoDescargaActivo()
     */
    @Override
    public Boolean procesoDescargaActivo(){
        String firmaServicio = "ArchivosPILABusiness.procesoDescargaActivo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Boolean result = Boolean.TRUE;
        List<TipoProcesoPilaEnum> tipos = new ArrayList<>();
        tipos.add(TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA_WEB);

        List<ProcesoPila> procesos = persistencia.consultarProcesoEstado(tipos, EstadoProcesoValidacionEnum.PROCESO_ACTIVO);

        if (procesos == null || procesos.isEmpty()) {
            result = Boolean.FALSE;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.aportes.service.AportesService#ejecutarPila1SinCarga(java.lang.String)
     */
    @Override
    @Deprecated
    public void ejecutarPila1SinCarga(String usuario) {
        String firmaServicio = "ArchivosPILABusiness.ejecutarPila1SinCarga(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

//        archivosPilaEjecucionService.ejecutarPila1SinCarga(usuario);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#conciliarArchivosOIyOF(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
	public void conciliarArchivosOIyOF(UserDTO userDTO) {
    	String firmaServicio = "ArchivosPILABusiness.conciliarArchivosOIyOF(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> indicesPlanilla =new ArrayList<>();
		archivosPilaEjecucionService.conciliarArchivosOIyOF(userDTO.getNombreUsuario(),indicesPlanilla);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}
    
    /**
     * Método que llama iniciarVariablesGenerales()
     */
    @Override
    public void iniciarVariablesGenerales() {
        
        archivosPilaEjecucionService.iniciarVariablesGenerales();
        
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#conciliarArchivosOIyOFSincrono(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
	public void conciliarArchivosOIyOFSincrono(UserDTO userDTO) {
    	String firmaServicio = "ArchivosPILABusiness.conciliarArchivosOIyOFSincrono(UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> indicesPlanilla = new ArrayList<>();
        try {
	      indicesPlanilla = persistencia.consultarPlanillasReproceso();
	        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " planillas "+indicesPlanilla.size());
	        if(!indicesPlanilla.isEmpty()) {
	        	reprocesarMundo1(indicesPlanilla);
	        }
        } catch (Exception e) {
        	logger.error(ConstantesComunes.INICIO_LOGGER + firmaServicio, e);
		}
        
		archivosPilaEjecucionService.conciliarArchivosOIyOFSincrono(userDTO.getNombreUsuario(),indicesPlanilla);
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}  

    /** (non-Javadoc)
     * @see com.asopagos.pila.service.ArchivosPILAService#reprocesarPlanillasPendientesConciliacionSincrono(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void reprocesarPlanillasPendientesConciliacionSincrono(UserDTO userDTO) {
    	String firmaServicio = "ArchivosPILABusiness.reprocesarPlanillasPendientesConciliacionSincrono(UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        //Long idProceso = null;
            // Se buscan las planillas en la tabla PilaArchivoFRegistro6 las cuales salgan como conciliadas
            // Y cuyo archivo I aun siga buscando el F correspondiente
	      /*  List<PilaArchivoFRegistro6> indicesPlanillasF = persistencia.consultarPlanillasPendientesPorConciliacionF();
	        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " planillas "+indicesPlanillasF.size());
            persistencia.actualizarConciliacionPlanillasF(indicesPlanillasF);
            // Despues de actualizar las f se deberia continuar el flujo de planillas desde bloque 6 asociadas a los f acualizador

             
            // Execute pila 2
            List<Long> idPlanillass = persistencia.consultarPlanillasOIPorF(indicesPlanillasF);
            logger.info("Ajuste de planillas para mundo 1" + idPlanillass.toString());


            */ 
            try {
                List<Long> indicesPlanilla = persistencia.consultarPlanillasConciliadasB6aB8();
                logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " planillas "+indicesPlanilla.size());
                if(!indicesPlanilla.isEmpty()) {
                   // reprocesarMundo1SinValidacion(indicesPlanilla);
                   gestorPila2SinValidacion.iniciarPila2SinValidaciones(indicesPlanilla);

                }
            } catch (Exception e) {
                logger.error(ConstantesComunes.INICIO_LOGGER + firmaServicio, e);
            }
            // Se reprocesan las planillas para eliminar persistencias de pila
            //for (Long idPlanilla: idPlanillass) {
            //    persistencia.eliminarPersistenciasPila(idPlanilla);
            //}
          //  List<Long> lstIdPlanillasOI = persistencia.consultarPlanillasOIPorPlanillas(idPlanillass);
            // Sp execure pila2
          //  reprocesarMundo1SinValidacion(lstIdPlanillasOI);

            //archivosPilaEjecucionService.validarArchivosOISeleccion(indicesPlanillasOI, userDTO.getNombreUsuario(), idProceso);

      
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

    
    /// mejoras carga ftp
    @Override
    public Long instanciarProceso(String usuarioProceso, TipoProcesoPilaEnum tipoProceso, UserDTO userDTO) {
        logger.warn("inicia metodo instanciarProceso(String usuarioProceso, TipoProcesoPilaEnum tipoProceso, UserDTO userDTO)");
    	return archivosPilaEjecucionService.instanciarProceso(usuarioProceso, tipoProceso);
    }
    
    @Override
    public void finalizarProceso(Long idProceso, EstadoProcesoValidacionEnum estadoProceso, UserDTO userDTO) {
    	archivosPilaEjecucionService.finalizarProceso(idProceso, estadoProceso);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConexionOperadorInformacion> consultarDatosConexionOperadorInformacion(Long idOperadorInformacion, UserDTO userDTO) {
	    try {
	        return persistencia.consultarDatosConexionOperadorInformacion(idOperadorInformacion);
	    } catch (ErrorFuncionalValidacionException e) {
	        throw new TechnicalException(e.getMessage(), e.getCause());
	    }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoPilaDTO> filtrarExistentes(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos, UserDTO userDTO) {
    	List<ArchivoPilaDTO> archivosDescargados = persistencia.filtrarExistentes(archivosFTP, tipoArchivos);
    	if(!archivosDescargados.isEmpty()){
            archivosDescargados = FuncionesValidador.ordenarListadoDatosArchivos(archivosDescargados, false);
    	}
    	return archivosDescargados;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoPilaDTO> filtrarExistentesPlanillas(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos, UserDTO userDTO) {
    	List<ArchivoPilaDTO> archivosDescargados = persistencia.filtrarExistentesPlanillas(archivosFTP, tipoArchivos);
    	if(!archivosDescargados.isEmpty()){
            archivosDescargados = FuncionesValidador.ordenarListadoDatosArchivos(archivosDescargados, false);
    	}
    	return archivosDescargados;
    }
    
    @Override
	public void cargarArchivosParalelo(List<ArchivoPilaDTO> archivosDescargados, TipoCargaArchivoEnum tipoCarga,
			Boolean ejecutarB0, Long idProceso, UserDTO userDTO) {

		String usuarioCarga = userDTO.getNombreUsuario();
		if (usuarioCarga == null) {
			usuarioCarga = ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO;
		}

		DatosProcesoFtpDTO datosControl = new DatosProcesoFtpDTO(idProceso, usuarioCarga, tipoCarga);

		archivosPilaEjecucionService.cargarArchivosParalelo(archivosDescargados, datosControl, ejecutarB0,
				usuarioCarga);
	}
      
}
