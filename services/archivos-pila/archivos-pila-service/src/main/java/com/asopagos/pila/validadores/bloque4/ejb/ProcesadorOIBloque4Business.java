package com.asopagos.pila.validadores.bloque4.ejb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.staging.PreliminarArchivoPila;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque4.interfaces.ProcesadorOIBloque4Local;
import com.asopagos.rest.exception.TechnicalException;

import co.com.heinsohn.lion.common.util.FileUtilities;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;
import co.com.heinsohn.lion.fileprocessing.util.ConstantsProperties;

/**
 * Bean de sesión que procesa la lógica de Bloque 4 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque4Business extends ProcesadorBloque implements ProcesadorOIBloque4Local{

    private Integer intento = 0;
    
    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private GestorStoredProceduresLocal persistenciaStaging;
    
    @Inject
    private FileLoaderInterface fileLoader;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque4Business.class);
    
    private void init() {
        super.preparadorContexto = this.preparadorContexto;
        super.gestorEstados      = this.gestorEstados;
        super.persistencia       = this.persistencia;
        super.persistenciaEstados  = persistenciaEstados;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar (Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque4Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        this.intento = intento != null ? intento : 1 ;
        init();
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        String idDocumentoParam = (String) parametros.get(ID_DOCUMENTO_KEY);
        List<IndicePlanilla> indicesOI = (List<IndicePlanilla>) parametros.get(LISTA_INDICES_OI_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        // TODO: consultar si tiene datos
        
        byte[] dataFile = obtenerContenidoArchivo(idDocumentoParam);
        
        RespuestaValidacionDTO resultadoValidacionBloque = ejecutarBloque(indicePlanilla, dataFile, indicesOI);
        
        logger.info("BLOQUE SIGUIENTE ---> "+ resultadoValidacionBloque.getBloqueSiguente());
        logger.info("NUMERO DE REINTENTO A PROCESAR ---> "+ this.intento);
        procesarBloque(resultadoValidacionBloque.getBloqueSiguente(), parametros, this.intento);
        
        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoValidacionBloque));
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método para la ejecución del Bloque de validación 4 (B4) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @param intento
     *        Número de intento de persistencia de archivo a ejecutar
     * @param dataFile
     *        Flujo de bits con el contenido del archivo
     * @param indicesOI 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla, byte[] dataFile, List<IndicePlanilla> indicesOI) {
        String firmaMetodo = "ProcesadorOIBloque4Business.ejecutarBloque(IndicePlanilla, byte[], List<IndicePlanilla>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_4_OI;

        if (indicePlanilla != null) {
            // se prepara el contexto para la validación
            Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, /*false,*/ bloqueActual);

            boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
            result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
            Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
            bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);
            
           if (iniciar && dataFile != null) {
                parametrosContexto.put(ConstantesContexto.INDICE_PLANILLA, indicePlanilla);

                // se agregan al contexto el período y la fecha de pago a partir del nombre del archivo
                parametrosContexto.put(ConstantesContexto.NOMBRE_FECHA_PAGO, FuncionesValidador.obtenerCampoNombreArchivo(
                        indicePlanilla.getTipoArchivo(), CamposNombreArchivoEnum.FECHA_PAGO_OI, indicePlanilla.getNombreArchivo()));

                parametrosContexto.put(ConstantesContexto.NOMBRE_PERIODO_PAGO, FuncionesValidador.obtenerCampoNombreArchivo(
                        indicePlanilla.getTipoArchivo(), CamposNombreArchivoEnum.PERIODO_PAGO_OI, indicePlanilla.getNombreArchivo()));

                parametrosContexto.put(ConstantesContexto.NOMBRE_ID_APORTANTE,
                        FuncionesValidador.obtenerCampoNombreArchivo(indicePlanilla.getTipoArchivo(),
                                CamposNombreArchivoEnum.IDENTIFICACION_APORTANTE_OI, indicePlanilla.getNombreArchivo()));

                parametrosContexto.put(ConstantesContexto.NOMBRE_TIPO_DOCUMENTO,
                        FuncionesValidador.obtenerCampoNombreArchivo(indicePlanilla.getTipoArchivo(),
                                CamposNombreArchivoEnum.TIPO_DOCUMENTO_APORTANTE_OI, indicePlanilla.getNombreArchivo()));

                // se agrega el código del tipo de archivo
                parametrosContexto.put(ConstantesContexto.NOMBRE_TIPO_ARCHIVO, indicePlanilla.getTipoArchivo().getCodigo());

                Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndice(indicePlanilla, result,
                        BloqueValidacionEnum.BLOQUE_3_OI, bloqueActual);

                EstadoProcesoArchivoEnum estadoB3 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
                result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
                bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

                // se confirma el estado del bloque 3 haya sido aprobado
                if (estadoB3 != null && EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_CONSISTENTES.equals(estadoB3)) {

                    // al confirmar que el B3 se aprobó, se localiza el índice hermano

                    IndicePlanilla indiceHermano = buscarArchivoHermano(indicePlanilla);

                    if (indiceHermano != null) {
                        resultadoConsultaEstado = consultarEstadoBloqueIndice(indiceHermano, result,
                                BloqueValidacionEnum.BLOQUE_4_OI, bloqueActual);

                        EstadoProcesoArchivoEnum estadoB4Hermano = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
                        result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
                        bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

                        agregarALista(indiceHermano, estadoB4Hermano, indicesOI);
                    }

                    // ---------------------------------------------------------------------------------------------------------------

                    Map<String, Object> resultadoEjecucionComponenteB4 = ejecutarComponenteBloque4OI(indicePlanilla, dataFile, result, parametrosContexto);

                    FileLoaderOutDTO fileOutDTO = (FileLoaderOutDTO) resultadoEjecucionComponenteB4.get(DTO_COMPONENTE);
                    AccionProcesoArchivoEnum accion = (AccionProcesoArchivoEnum) resultadoEjecucionComponenteB4.get(ACCION_ARCHIVO);
                    result = (RespuestaValidacionDTO) resultadoEjecucionComponenteB4.get(RESULTADO_VALIDACION);
                    bloqueActual = (BloqueValidacionEnum) resultadoEjecucionComponenteB4.get(BLOQUE_SIGUIENTE);

                    // se ejecutan las tareas de finalización del validación de B4
                    Map<String, Object> resultadoFinalizacionB4 = finalizarValidacionB4(indicePlanilla, fileOutDTO, accion, result,
                            parametrosContexto);
                    EstadoProcesoArchivoEnum estado = (EstadoProcesoArchivoEnum) resultadoFinalizacionB4.get(ESTADO_ARCHIVO);
                    accion = (AccionProcesoArchivoEnum) resultadoFinalizacionB4.get(ACCION_ARCHIVO);
                    result = (RespuestaValidacionDTO) resultadoFinalizacionB4.get(RESULTADO_VALIDACION);

                    // se trasladan los errores del fileOutDTO a la respuesta
                    result = agregarErroresDeComponente(result, parametrosContexto, indicePlanilla);

                    result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 4, accion);
                }
            }
            else if (dataFile == null) {
                // al presentarse un error no controlado en el componente, se le agrega a la respuesta
                String mensaje = MensajesValidacionEnum.ERROR_CONTENIDO_DE_ARCHIVO_PERDIDO_EN_ECM
                        .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.name());

                result = FuncionesValidador.agregarError(result, indicePlanilla, bloqueActual, mensaje,
                        TipoErrorValidacionEnum.ERROR_TECNICO, null);

                EstadoProcesoArchivoEnum estado = EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_FALLIDA;
                AccionProcesoArchivoEnum accion = AccionProcesoArchivoEnum.PASAR_A_BANDEJA;

                result.setBloqueSiguente(bloqueActual);
                result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 4, accion);
            }
        }
        else{
            result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    
    /**
     * Método encargado de la ejecución del componente de lectura de archivos para el Bloque 4 para OI
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param dataFile
     *        Flujo de bytes con el contenido del archivo
     * @param result
     *        DTO con el resultado de la validación
     * @param intento
     *        Intento actual de persistencia
     * @param reintentos
     *        Límite de intentos consecutivos de persistencia parametrizados para la CCF
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación, el DTO de respuesta del componente de lectura y la acción para el
     *         archivo luego de intentar la persistencia del archivo
     */
    private Map<String, Object> ejecutarComponenteBloque4OI(IndicePlanilla indicePlanilla, byte[] dataFile, RespuestaValidacionDTO result, Map<String, Object> parametrosContexto) {
        
        String firmaMetodo = "ejecutarComponenteBloque4OI(IndicePlanilla, byte[], RespuestaValidacionDTO, Integer, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_4_OI;

        FileLoaderOutDTO fileOutDTO = null;
        AccionProcesoArchivoEnum accion = null;

        RespuestaValidacionDTO resultTemp = result;
        try {
            Long idLectura = FuncionesValidador.getIdPerfilLectura(indicePlanilla.getTipoArchivo().getPerfilArchivo());

            if (idLectura != null) {

            	// TODO: validate an load se quita el load
                fileOutDTO = fileLoader.validate(parametrosContexto, FileFormat.FIXED_TEXT_PLAIN, dataFile, idLectura,
                        indicePlanilla.getNombreArchivo(), bloqueActual.getValidatorProfile());
                // se actualiza el contexto
                parametrosContexto = fileOutDTO.getContext();
                persistenciaStaging.almacenarPreliminarArchivoPila(dataFile, indicePlanilla, null);
                persistenciaStaging.executePILA1Persistencia(indicePlanilla.getId());
            }
            else {
                // no se tiene un ID de lectura, se lanza excepción técnica
                String mensaje = MensajesFTPErrorComunesEnum.ERROR_PARAMTRO_ID_LECTURA
                        .getReadableMessage(indicePlanilla.getTipoArchivo().getPerfilArchivo().getDescripcion());

                logger.debug("Finaliza ejecutarBloque1OF(IndicePlanillaOF, byte[]) - " + mensaje);
                throw new TechnicalException(mensaje, new Throwable());
            }
        } catch (FileProcessingException e) {
            // al presentarse un error no controlado en el componente, se le agrega a la respuesta
            String mensaje = MensajesValidacionEnum.ERROR_EXCEPCION_COMPONENTE.getReadableMessage(e.getMessage());

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;

            // la acción depende del estado anterior, en el caso de que el fallo se presente por primera vez, 
            // se reintenta, sino se pasa a bandeja
            if (EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_FALLIDA.equals(indicePlanilla.getEstadoArchivo())) {
                // se verifica el número del intento actual contra el tope re reintentos
                // con el fin de determinar la acción
                // se toma la cantidad de reintentos por ejecución para realizar la persistencia del contenido del archivo
                Integer reintentos = Integer.parseInt((String) parametrosContexto.get(ConstantesContexto.REINTENTOS));
                
                logger.info("CANTIDAD TOTAL DE REINTENTOS" + reintentos);
                
                if (this.intento.compareTo(reintentos) < 0) {
                    accion = AccionProcesoArchivoEnum.REINTENTAR_BLOQUE;

                    // se actualiza el bloque de ejecución para el DTO del archivo
                    bloqueActual = BloqueValidacionEnum.BLOQUE_4_OI;
                    this.intento++;
                }
                else {
                    accion = AccionProcesoArchivoEnum.PASAR_A_BANDEJA;
                    this.intento = 1;
                }
                logger.info("NUMERO DEL REINTENTO" +  this.intento);
            }
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(DTO_COMPONENTE, fileOutDTO);
        respuesta.put(ACCION_ARCHIVO, accion);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param fileOutDTO
     *        DTO de respuesta del componente de lectura
     * @param accion
     *        Acción para el archivo luego de intentar la persistencia del archivo
     * @param result
     *        DTO con el resultado de la validación
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación, la acción actualizada en caso de cambio y el estado a asignar al
     *         archivo
     */
    private Map<String, Object> finalizarValidacionB4(IndicePlanilla indicePlanilla, FileLoaderOutDTO fileOutDTO,
            AccionProcesoArchivoEnum accion, RespuestaValidacionDTO result, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "finalizarValidacionB4(IndicePlanilla, FileLoaderOutDTO, AccionProcesoArchivoEnum, RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_4_OI;

        /*
         * el resultado del bloque 4, no afecta el procesamiento de los archivos no obstante, un estado fallido del componente
         * en este punto, indica un error al momento de persistir la información
         */

        EstadoProcesoArchivoEnum estado = null;
        AccionProcesoArchivoEnum accionTemp = accion;
        RespuestaValidacionDTO resultTemp = result;

        if (fileOutDTO != null && !FileLoadedState.FAILED.equals(fileOutDTO.getState())) {
            // este caso indica que el proceso de validación finalizó con la persistencia del contenido

            // el estado para avance, depende del tipo de archivo, los archivos de información de aportante, sólo se 
            // procesan hasta el bloque 4
            if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
                estado = EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_COMPLETADA;
                accionTemp = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_5;
            }
            else {
                estado = EstadoProcesoArchivoEnum.VALIDACIONES_FINALIZADAS;
                accionTemp = AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS;
            }

            // se actualiza el bloque de ejecución para el DTO del archivo
            bloqueActual = BloqueValidacionEnum.BLOQUE_5_OI;

            // adicional al estado del bloque 4, sí se ha detectado que el valor de la planilla es cero,
            // se adiciona el estado de bloque 6 NO_REQUIERE_CONCILIACION
            if (parametrosContexto.get(ConstantesContexto.EJECUTAR_BLOQUE_6) != null
                    && !((boolean) parametrosContexto.get(ConstantesContexto.EJECUTAR_BLOQUE_6))) {
                try {
                    EstadoProcesoArchivoEnum estadoB6 = EstadoProcesoArchivoEnum.NO_REQUIERE_CONCILIACION;

                    // se registra el estado del bloque
                    gestorEstados.registrarEstadoArchivo(indicePlanilla, estadoB6, AccionProcesoArchivoEnum.EN_ESPERA, "", 6, null);
                } catch (ErrorFuncionalValidacionException e) {
                    // en este caso, se presenta un error por estado inválido al momento de actualizar el estado por bloque
                    // se debe agregar el error a la respuesta y anular el índice

                    indicePlanilla.setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);
                    persistencia.actualizarIndicePlanillas(indicePlanilla);

                    resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(),
                            TipoErrorValidacionEnum.ERROR_TECNICO, null);
                }
            }
        }
        else {
            logger.info("Entra a error de  PERSISTENCIA_ARCHIVO_FALLIDA");
            // en este caso, se presento un fallo en la persistencia
            estado = EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_FALLIDA;

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }
        
        resultTemp.setBloqueSiguente(bloqueActual);
        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estado);
        respuesta.put(ACCION_ARCHIVO, accionTemp);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
}
