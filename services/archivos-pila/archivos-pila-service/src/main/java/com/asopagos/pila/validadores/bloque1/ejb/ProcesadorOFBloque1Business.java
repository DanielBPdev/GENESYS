package com.asopagos.pila.validadores.bloque1.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque1.interfaces.ProcesadorOFBloque1Local;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;

/**
 * Bean de sesión que procesa la lógica de Bloque 1 para archivos OF
 * @author jrico
 */
@Stateless
public class ProcesadorOFBloque1Business extends ProcesadorBloque implements ProcesadorOFBloque1Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private FileLoaderInterface fileLoader;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOFBloque1Business.class);
    
    private void init() {
        super.preparadorContexto  = this.preparadorContexto;
        super.gestorEstados       = this.gestorEstados;
        super.persistencia        = this.persistencia;
        super.persistenciaEstados = this.persistenciaEstados;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar(Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOFBloque1Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanillaOF indicePlanilla = (IndicePlanillaOF) parametros.get(INDICE_PLANILLA_KEY);
        String idDocumentoParam = (String) parametros.get(ID_DOCUMENTO_KEY);
        String usuario = (String) parametros.get(USUARIO_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        byte[] dataFile = obtenerContenidoArchivo(idDocumentoParam);
        
        // se agrega el usuario y fecha que inician el proceso
        indicePlanilla.setFechaProceso(Calendar.getInstance().getTime());
        indicePlanilla.setUsuario(usuario);
        
        RespuestaValidacionDTO resultadoValidacionBloque = ejecutarBloque (indicePlanilla, dataFile);
        procesarBloque(resultadoValidacionBloque.getBloqueSiguente(), parametros, intento);
        
        respuestaGeneralTemp = addErrores(respuestaGeneralTemp, resultadoValidacionBloque);
        respuestaGeneralTemp.setIndicesOIenOF(resultadoValidacionBloque.getIndicesOIenOF());
        parametros.put(RESPUESTA_GENERAL_KEY, respuestaGeneralTemp);
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    
    /**
     * Método para la ejecución del Bloque de validación 1 (B1) del Operador Financiero
     * @param indicePlanillaOF
     *        Instancia del índice de planilla de Operador Financiero
     * @param dataFile
     *        Flujo de bits con el contenido del archivo
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque (IndicePlanillaOF indicePlanillaOF, byte[] dataFile) {
        String firmaMetodo = "ProcesadorOFBloque1Business.ejecutarBloque(IndicePlanillaOF, byte[])";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        logger.info(firmaMetodo + "paso 1 " + indicePlanillaOF.getId());

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_1_OF;

        if (indicePlanillaOF != null) {

            Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndiceOF(indicePlanillaOF, result,
                    BloqueValidacionEnum.BLOQUE_0_OF, bloqueActual);

            EstadoProcesoArchivoEnum estadoB0 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);

            if (EstadoProcesoArchivoEnum.CARGADO_EXITOSAMENTE.equals(estadoB0)) {
                
                result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
                bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

                // se prepara el contexto para la validación
                Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanillaOF, result, /*false,*/ bloqueActual);

                boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
                if (iniciar) {
                    
                    result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
                    Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
                    bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);

                    
                    parametrosContexto.put(ConstantesContexto.INDICE_PLANILLA, indicePlanillaOF);
                    
                    for(ErrorDetalladoValidadorDTO res :result.getErrorDetalladoValidadorDTO()) {
                        logger.info("MANTIS252559 tipo error "+res.getTipoError());
                        logger.info("MANTIS252559 numero linea error "+res.getLineNumber());
                        logger.info("MANTIS252559 valor campo error "+res.getValorCampo());
                    }

                    Map<String, Object> resultadoEjecucionComponenteB1OF = ejecutarComponenteB1OF(indicePlanillaOF, result, dataFile,
                            parametrosContexto);
                    EstadoProcesoArchivoEnum estado = (EstadoProcesoArchivoEnum) resultadoEjecucionComponenteB1OF.get(ESTADO_ARCHIVO);
                    // estado para el bloque 6
                    EstadoProcesoArchivoEnum estadoB6 = (EstadoProcesoArchivoEnum) resultadoEjecucionComponenteB1OF.get(ESTADO_ARCHIVO_B6);
                    result = (RespuestaValidacionDTO) resultadoEjecucionComponenteB1OF.get(RESULTADO_VALIDACION);
                    bloqueActual = (BloqueValidacionEnum) resultadoEjecucionComponenteB1OF.get(BLOQUE_SIGUIENTE);
                    //result.setIndicesOIenOF((List<IndicePlanilla>) resultadoEjecucionComponenteB1OF.get(INDICES_OI_RELACIONADOS_OF));

                    // se trasladan los errores del fileOutDTO a la respuesta
                    result = agregarErroresDeComponente(result, parametrosContexto, indicePlanillaOF);
                    result.setIndicesOIenOF((List<IndicePlanilla>) resultadoEjecucionComponenteB1OF.get(INDICES_OI_RELACIONADOS_OF));
                    
                    if(result.getIndicesOIenOF() != null)
                        logger.info(firmaMetodo + "paso 2 " + result.getIndicesOIenOF().size());
                    else
                        logger.info(firmaMetodo + "paso 2 null" );

                    result.setBloqueSiguente(bloqueActual);
                    actualizarIndiceYestadoBloqueOF(result, indicePlanillaOF, estado, estadoB6, 1);
                }
            }
        }

        // se actualiza el bloque para detener al orquestador
        result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método encargado de consultar el estado de un índice de planilla OF en un bloque específico
     * @param indicePlanillaOF
     *        Índice de planilla que está siendo validado
     * @param result
     *        DTO con el resultado de la validación
     * @param bloque
     *        Número del bloque a validar
     * @param bloqueActual 
     * @return <b>Map<String, Object></b>
     *         Resultado de la comprobación (DTO de resultado de validación actualizado en caso de errores y estado del archivo en el
     *         bloque indicado)
     */
    private Map<String, Object> consultarEstadoBloqueIndiceOF(IndicePlanillaOF indicePlanillaOF, RespuestaValidacionDTO result,
            BloqueValidacionEnum bloque, BloqueValidacionEnum bloqueActual) {
        String firmaMetodo = "ProcesadorOFBloque1Business.consultarEstadoBloqueIndice(IndicePlanilla, RespuestaValidacionDTO, BloqueValidacionEnum, BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();

        EstadoArchivoPorBloqueOF estadosPlanilla = null;
        RespuestaValidacionDTO resultTemp = result;
        EstadoProcesoArchivoEnum estado = null;
        try {
            estadosPlanilla = persistenciaEstados.consultarEstadoOF(indicePlanillaOF);
        } catch (ErrorFuncionalValidacionException e) {
            // no se encuentran estados previos, se debe generar respuesta y pasar el error

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanillaOF, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        if (estadosPlanilla != null) {
            switch (bloque) {
                case BLOQUE_0_OF:
                    estado = estadosPlanilla.getEstadoBloque0();
                    break;
                case BLOQUE_1_OF:
                    estado = estadosPlanilla.getEstadoBloque1();
                    break;
                default:
                    break;
            }
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estado);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * @param indicePlanillaOF
     *        Instancia del índice de planilla de Operador Financiero
     * @param resultTemp
     *        DTO con el resultado de la validación
     * @param dataFile
     *        Flujo de bits con el contenido del archivo
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación actualizado en caso de error y estado a asignar al archivo para B1 y
     *         B6
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> ejecutarComponenteB1OF(IndicePlanillaOF indicePlanillaOF, RespuestaValidacionDTO result, byte[] dataFile,
            Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ProcesadorOFBloque1Business.ejecutarComponenteB1OF(IndicePlanillaOF, RespuestaValidacionDTO, byte[], Map<String, Object>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Map<String, Object> respuesta = new HashMap<>();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_1_OF;

        FileLoaderOutDTO fileOutDTO = null;
        EstadoProcesoArchivoEnum estado = null;
        // estado para el bloque 6
        EstadoProcesoArchivoEnum estadoB6 = null;
        RespuestaValidacionDTO resultTemp = result;

        Long idLectura = FuncionesValidador.getIdPerfilLectura(indicePlanillaOF.getTipoArchivo().getPerfilArchivo());

        try {
            logger.info("paso 6");
            
            // se reinicia el control de registros para evitar inconsistencias
            parametrosContexto.replace(ConstantesContexto.LISTA_CONTROL_REGISTROS,
                    FuncionesValidador.inicializarListaControlRegistros());

            fileOutDTO = fileLoader.validateAndLoad(parametrosContexto, FileFormat.FIXED_TEXT_PLAIN, dataFile, idLectura,
                    indicePlanillaOF.getNombreArchivo(), bloqueActual.getValidatorProfile());

            // se actualiza el contexto
            parametrosContexto = fileOutDTO.getContext();
            
            logger.info("MANTIS252559 1.estado: "+fileOutDTO.getState());

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;

        } catch (FileProcessingException e) {
            logger.info("paso 7");
            // al presentarse un error no controlado en el componente, se le agrega a la respuesta
            String mensaje = MensajesValidacionEnum.ERROR_EXCEPCION_COMPONENTE.getReadableMessage(e.getMessage());

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanillaOF, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        List<IndicePlanilla> indicesOI = null;
        logger.info("MANTIS252559 estado: "+fileOutDTO.getState());
        if (fileOutDTO != null && FileLoadedState.SUCCESFUL.equals(fileOutDTO.getState())) {
            logger.info("paso 8");
            estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA;
            estadoB6 = EstadoProcesoArchivoEnum.ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION;

            logger.info(firmaMetodo + "paso 8 indicePlanillaOF.getId() " + indicePlanillaOF.getId());
            
            Set<Long> a  = (Set<Long>) parametrosContexto.get(ConstantesContexto.MAPA_NUMEROS_PLANILLA_EN_OF);
            logger.info(firmaMetodo + "paso 8 MAPA_NUMEROS_PLANILLA_EN_OF " + a.size());
            
            // se comparan lo registros nuevos para establecer si están duplicados y proceder a anular
            persistencia.ejecutarRevisionRegistrosTipo6(indicePlanillaOF.getId());
            logger.info("Finaliza persistencia ejecutarRevisionRegistrosTipo6 -> USP_ValidateHU407Registro6Duplicado");
            try{
                 indicesOI = prepararEjecucionOIDerivada((Set<String>) parametrosContexto.get(ConstantesContexto.MAPA_NUMEROS_PLANILLA_EN_OF));
            }catch(Exception e ){
             logger.error("ERROR ProcesadorOFBloque1Business OF ->"+e);   
            }

        }
        else {
            logger.info("paso 9");
            estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA;
            
            if(fileOutDTO.getDetailedToleratedErrors() != null) {
                for(DetailedErrorDTO d : fileOutDTO.getDetailedToleratedErrors()) {
                    logger.info("dt.getLineNumber() " + d.getLineNumber());
                    logger.info("dt.getMessage() " + d.getMessage());
                }
            }
            
            if(fileOutDTO.getDetailedErrors() != null) {
                for(DetailedErrorDTO d : fileOutDTO.getDetailedErrors()) {
                    logger.info("d.getLineNumber() " + d.getLineNumber());
                    logger.info("d.getMessage() " + d.getMessage());
                }
            }
        }
        ArrayList<ErrorDetalladoValidadorDTO> erroresPerdidos = new ArrayList<ErrorDetalladoValidadorDTO>();
        if(fileOutDTO.getDetailedToleratedErrors() == null 
                && fileOutDTO.getDetailedErrors() == null 
                && EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.equals(estado)
                && TipoArchivoPilaEnum.ARCHIVO_OF.equals(indicePlanillaOF.getTipoArchivo())) {
            
            logger.info("Mantis 2550");
            erroresPerdidos = (ArrayList<ErrorDetalladoValidadorDTO>) parametrosContexto.get(ConstantesContexto.ERRORES_DETALLADOS);
            
            if(erroresPerdidos.size()>0 && resultTemp.getErrorDetalladoValidadorDTO().size()==0) {
                for(ErrorDetalladoValidadorDTO error : erroresPerdidos) {
                    resultTemp.addErrorDetalladoValidadorDTO(error);
                }
            }
            
        }

        // se revisa el conteo de registros (validación de registros únicos)
        resultTemp = validarRegistrosUnicos(resultTemp, indicePlanillaOF, parametrosContexto, bloqueActual);

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estado);
        respuesta.put(ESTADO_ARCHIVO_B6, estadoB6);
        respuesta.put(INDICES_OI_RELACIONADOS_OF, indicesOI);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);
        
        logger.info("MANTIS252559 resultado temporal "+resultTemp);
        logger.info("MANTIS252559 resultado estado "+estado);
        logger.info("MANTIS252559 resultado estadoB6 "+estadoB6);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    
}
