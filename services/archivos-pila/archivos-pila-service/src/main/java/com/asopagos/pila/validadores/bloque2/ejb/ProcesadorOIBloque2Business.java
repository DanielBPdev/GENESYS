package com.asopagos.pila.validadores.bloque2.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque1.interfaces.IValidacionOIBloque1;
import com.asopagos.pila.validadores.bloque2.interfaces.ProcesadorOIBloque2Local;
import com.asopagos.rest.exception.TechnicalException;

import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;

/**
 * Bean de sesión que procesa la lógica de Bloque 2 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque2Business extends ProcesadorBloque implements ProcesadorOIBloque2Local{

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private FileLoaderInterface fileLoader;
    
    @Inject
    private IValidacionOIBloque1 validacionOIBloque1;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque2Business.class);
    
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
        String firmaMetodo = "ProcesadorOIBloque2Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        String idDocumentoParam = (String) parametros.get(ID_DOCUMENTO_KEY);
        List<IndicePlanilla> indicesOI = (List<IndicePlanilla>) parametros.get(LISTA_INDICES_OI_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        byte[] dataFile = obtenerContenidoArchivo(idDocumentoParam);

        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(indicePlanilla, dataFile, indicesOI);
        procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);
        
        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método para la ejecución del Bloque de validación 2 (B2) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @param dataFile
     *        Flujo de bits con el contenido del archivo
     * @param indicesOI 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla, byte[] dataFile, List<IndicePlanilla> indicesOI) {
        String firmaMetodo = "ProcesadorOIBloque2Business.ejecutarBloque(IndicePlanilla, byte[], List<IndicePlanilla>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;

        // Se verifica que se cuente con una entrada de índice de planilla y el estado del bloque 1 antes
        if (indicePlanilla == null) {
            result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
            
        Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndice(indicePlanilla, result, BloqueValidacionEnum.BLOQUE_1_OI, bloqueActual);

        EstadoProcesoArchivoEnum estadoB1 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
        result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
        bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

        if (estadoB1 == null || !EstadoProcesoArchivoEnum.EN_PROCESO.equals(estadoB1)) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
        
        // Al confirmar que el B1 se aprobó, se localiza el índice hermano
        IndicePlanilla indiceHermano = buscarArchivoHermano(indicePlanilla);
        EstadoProcesoArchivoEnum estadoB2Hermano = null;
        if (indiceHermano != null) {
            resultadoConsultaEstado = consultarEstadoBloqueIndice(indiceHermano, result,
                    BloqueValidacionEnum.BLOQUE_2_OI, bloqueActual);

            estadoB2Hermano = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
            result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
            bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

            agregarALista(indiceHermano, estadoB2Hermano, indicesOI);
        }

        if(BloqueValidacionEnum.FINALIZAR_CICLO.equals(bloqueActual)) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
        
        // se prepara el contexto para la validación
        Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, bloqueActual);

        boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
        result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
        Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
        bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);

        try {
            result = validacionOIBloque1.validacionNombreArchivo(indicePlanilla.getNombreArchivo(), parametrosContexto, 
                    indicePlanilla.getTipoArchivo().getPerfilArchivo(), result);
        } catch (ErrorFuncionalValidacionException e) {
            result = FuncionesValidador.agregarError(result, indicePlanilla, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);
            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            iniciar = false;
        }
        
        if (iniciar) {

            // se ejecuta el componente
            Map<String, Object> resultadoEjecucionComponenteBloque2OI = ejecutarComponenteBloque2OI(indicePlanilla, dataFile,
                    result, parametrosContexto);

            FileLoaderOutDTO fileOutDTO = (FileLoaderOutDTO) resultadoEjecucionComponenteBloque2OI.get(DTO_COMPONENTE);
            result = (RespuestaValidacionDTO) resultadoEjecucionComponenteBloque2OI.get(RESULTADO_VALIDACION);
            bloqueActual = (BloqueValidacionEnum) resultadoEjecucionComponenteBloque2OI.get(BLOQUE_SIGUIENTE);
            
            parametrosContexto = fileOutDTO.getContext();

            // se trasladan los errores del fileOutDTO a la respuesta
            result = agregarErroresDeComponente(result, parametrosContexto, indicePlanilla);

            /*
             * independientemente al resultado del bloque de validación, se verifica el intercambio de valores
             * entre archivos Ax e Ix
             */
            verificarIntercambioValoresEntreArchivos(indicePlanilla, parametrosContexto);

            // se finaliza la validación estableciendo el estado a asignar al archivo en el bloque 2
            EstadoProcesoArchivoEnum estado = finalizarValidacionB2OI(indicePlanilla, fileOutDTO,
                    result.getErrorDetalladoValidadorDTO(), result, parametrosContexto);

            result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 2, null);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    
    /**
     * Método encargado de la ejecución del componente de lectura de archivos para el Bloque 2 para OI
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param dataFile
     *        Flujo de bytes con el contenido del archivo
     * @param result
     *        DTO con el resultado de la validación
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación y la autorización para iniciar proceso bloque 2
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> ejecutarComponenteBloque2OI(IndicePlanilla indicePlanilla, byte[] dataFile, RespuestaValidacionDTO result,
            Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ejecutarComponenteBloque2OI(IndicePlanilla, byte[], RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        FileLoaderOutDTO fileOutDTO = null;
        RespuestaValidacionDTO resultTemp = result;
        
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;

        // marca que indica que se presentaron inconsistencias
        Boolean tieneInconsistencias = Boolean.FALSE;

        Long idLectura = FuncionesValidador.getIdPerfilLectura(indicePlanilla.getTipoArchivo().getPerfilArchivo());
        logger.info("id lectura "+idLectura);
        if (idLectura == null) {
            // no se tiene un ID de lectura, se lanza excepción técnica
            String mensaje = " :: " + MensajesFTPErrorComunesEnum.ERROR_PARAMTRO_ID_LECTURA
                    .getReadableMessage(indicePlanilla.getTipoArchivo().getPerfilArchivo().getDescripcion());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
            throw new TechnicalException(mensaje, new Throwable());
        }
        
        try {
            
            // se envía el arreglo de errores por contexto
            parametrosContexto.put(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, resultTemp);

            logger.info("prueba mantis 266741 linea ");
            fileOutDTO = fileLoader.validate(parametrosContexto, FileFormat.FIXED_TEXT_PLAIN, dataFile, idLectura,
                    indicePlanilla.getNombreArchivo(), bloqueActual.getValidatorProfile());
            
            if(fileOutDTO.getDetailedErrors() != null)
            for (DetailedErrorDTO d : fileOutDTO.getDetailedErrors()) {
            	logger.info("prueba mantis 266741 linea " + d.getLineNumber());
            	logger.info("prueba mantis 266741 mensaje " + d.getMessage());
            }
            
            if(fileOutDTO.getDetailedToleratedErrors() != null)
                for (DetailedErrorDTO d : fileOutDTO.getDetailedToleratedErrors()) {
                	logger.info("prueba mantis 266741 linea " + d.getLineNumber());
                	logger.info("prueba mantis 266741 mensaje " + d.getMessage());
                }

            // se actualiza el contexto
            parametrosContexto = fileOutDTO.getContext();

            if (parametrosContexto.get(ConstantesContexto.ERRORES_DETALLADOS) != null
                    && !((List<ErrorDetalladoValidadorDTO>) parametrosContexto.get(ConstantesContexto.ERRORES_DETALLADOS)).isEmpty()) {
                tieneInconsistencias = Boolean.TRUE;
            }

            resultTemp = (RespuestaValidacionDTO) parametrosContexto.get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);
            
        } catch (FileProcessingException e) {
            // al presentarse un error no controlado en el componente, se le agrega a la respuesta
            String mensaje = MensajesValidacionEnum.ERROR_EXCEPCION_COMPONENTE.getReadableMessage(e.getMessage());

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        // se revisa el conteo de registros (validación de registros únicos)
        resultTemp = validarRegistrosUnicos(resultTemp, indicePlanilla, parametrosContexto, bloqueActual);

        // se añade la marca de presencia de registro tipo 4 al índice de planilla
        Boolean presentaR4 = (Boolean) parametrosContexto.get(ConstantesContexto.HAY_REGISTRO_4);
        indicePlanilla.setPresentaRegistro4(presentaR4 == null ? false : true);

        /*
         * los archivos de detalle de aporte de dependientes e independientes, deben ejecutar la validación de valor
         * mora al finalizar la ejecución del componente debido a que no siempre se cuenta con registro tipo 4
         * 
         * Igualmente, se aplica la validación de obligatoriedad de los datos de número y fecha de pago de planilla asociada
         * para archivos de corrección (tipo planilla N)
         */
        if (PerfilLecturaPilaEnum.DETALLE_INDEPENDIENTE_DEPENDIENTE.equals(indicePlanilla.getTipoArchivo().getPerfilArchivo())) {
            resultTemp = validarValorMora(resultTemp, indicePlanilla, parametrosContexto);
            resultTemp = validarObligatoriedadDatosAsociadosCorreccion(resultTemp, indicePlanilla, parametrosContexto);
        }

        // sí se han presentado errores que no lanzan excepción, se cambia el estado de fileOutDTO
        if (resultTemp != null && resultTemp.getErrorDetalladoValidadorDTO() != null
                && !resultTemp.getErrorDetalladoValidadorDTO().isEmpty() && fileOutDTO != null) {
            fileOutDTO.setState(FileLoadedState.TOLERATED_ERRORS);
        }
        /*
         * sí no se tienen errores pero el estado es fallido, significa que hubo líneas vacías que no afectan la lectura realmente
         * (tampoco se tienen errores en el contexto)
         */
        else if ((resultTemp == null || resultTemp.getErrorDetalladoValidadorDTO().isEmpty()) && fileOutDTO != null
                && FileLoadedState.FAILED.equals(fileOutDTO.getState()) && !tieneInconsistencias) {
            fileOutDTO.setState(FileLoadedState.SUCCESFUL);
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(DTO_COMPONENTE, fileOutDTO);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    
    /**
     * Método encargado de llevar a cabo la validación de obligatoriedad del número y fecha de pago de la planilla
     * asociada a una planilla de corrección
     * @param result
     *        DTO con el resultado de la validación
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param parametrosContexto 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado
     */
    private RespuestaValidacionDTO validarObligatoriedadDatosAsociadosCorreccion(RespuestaValidacionDTO result,
            IndicePlanilla indicePlanilla, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ArchivosPILAEjecucion.validarObligatoriedadDatosAsociadosCorreccion(RespuestaValidacionDTO, IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;

        String numeroPlanillaAsociada = (String) parametrosContexto.get(ConstantesContexto.NUMERO_PLANILLA_ASOCIADA);
        Object fechaPagoPlanillaAsociada = parametrosContexto.get(ConstantesContexto.FECHA_PAGO_PLANILLA_ASOCIADA);
        String tipoPlanillaString = (String) parametrosContexto.get(ConstantesContexto.TIPO_PLANILLA);
        TipoPlanillaEnum tipoPlanillaEnum = null;
        if (tipoPlanillaString != null) {
            tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanillaString);
        }

        String mensaje = null;
        String idCampo = EtiquetaArchivoIEnum.I121.name();
        String tipoError = TipoErrorValidacionEnum.TIPO_2.name();
        String nombreCampo = "Archivo Tipo I - Registro 1 - Campo 21: Nº de la planilla asociada a esta planilla";
        logger.info("tipo de planilla"+tipoPlanillaEnum);
        if(!TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum)){
            logger.info("Entro IF B2 ANDRES");
            if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum) && !indicePlanilla.getPresentaRegistro4()
                    && numeroPlanillaAsociada == null) { 

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo, "", tipoError,
                        nombreCampo, tipoPlanillaEnum.getDescripcion());

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2, 1L);
            }
            else if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum) && indicePlanilla.getPresentaRegistro4()
                    && numeroPlanillaAsociada != null) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo,
                        numeroPlanillaAsociada, tipoError, nombreCampo, tipoPlanillaEnum.getDescripcion());

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2, 1L);
            }
        }
        
        //Para planilla O debe ser obligatorio
        //GLPI 68360
        // if (TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum) && numeroPlanillaAsociada == null) {

        //     mensaje = MensajesValidacionEnum.ERROR_CAMPO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo, "", tipoError,
        //             nombreCampo, tipoPlanillaEnum.getDescripcion());

        //     resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2, 1L);
        // }

        
        nombreCampo = "Archivo Tipo I - Registro 1 - Campo 19: Fecha de pago de la planilla asociada a esta planilla";
        idCampo = EtiquetaArchivoIEnum.I119.name();
        if(!TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum)){
            if ((TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) 
            		&& !indicePlanilla.getPresentaRegistro4()
                    && fechaPagoPlanillaAsociada == null) {

                mensaje = MensajesValidacionEnum.ERROR_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo, "", tipoError, nombreCampo);

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2,
                        1L);
            }
            else if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum) && indicePlanilla.getPresentaRegistro4()
                    && fechaPagoPlanillaAsociada != null) {

                mensaje = MensajesValidacionEnum.ERROR_NO_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo,
                        FuncionesValidador.formatoFecha(fechaPagoPlanillaAsociada), tipoError, nombreCampo);

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2,
                        1L);
            }
        }
        
        //Debe ser obligatorio para planillas O
        //GLPI 68360
        // if (TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum) && fechaPagoPlanillaAsociada == null) {

        //     mensaje = MensajesValidacionEnum.ERROR_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo, "", tipoError, nombreCampo);

        //     resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, TipoErrorValidacionEnum.TIPO_2,
        //                 1L);
        //     }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return resultTemp;
        }
    
    /**
     * Función que da inicio a la validación del valor de mora para los tipos de planilla que pueden tener registro tipo 4
     * @param result
     *        DTO con el resultado de la validación
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param parametrosContexto 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO validarValorMora(RespuestaValidacionDTO result, IndicePlanilla indicePlanilla,
            Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ArchivosPILAEjecucion.validarValorMora(RespuestaValidacionDTO, IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;
        String mensaje = null;

        String nombreCampo = (String) parametrosContexto.get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = (String) parametrosContexto.get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = (String) parametrosContexto.get(ConstantesParametroValidador.ID_CAMPO);
        Long numeroLinea = (Long) parametrosContexto.get(ConstantesContexto.LINEA_VALOR_MORA);
        
        String tipoPlanilla = (String) parametrosContexto.get(ConstantesParametroValidador.TIPO_PLANILLA_NOMBRE);
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);


        // se toman los valores requeridos desde el contexto
        BigDecimal valorMora = (BigDecimal) parametrosContexto.get(ConstantesContexto.VALOR_MORA);
        BigDecimal valorTotalMoraCalculado = (BigDecimal) parametrosContexto.get(ConstantesContexto.VALOR_MORA_CALCULADO);
        BigDecimal toleranciaValorMora = (BigDecimal) parametrosContexto.get(ConstantesContexto.TOLERANCIA_VALOR_MORA);

        List<DescuentoInteresMoraModeloDTO> casosDescuento = (List<DescuentoInteresMoraModeloDTO>) parametrosContexto
                .get(ConstantesContexto.CASOS_DESCUENTO_INTERES);

        Short indicadorUGPP = null;
        if (parametrosContexto.get(ConstantesContexto.INDICADOR_UGPP) != null) {
            indicadorUGPP = ((Integer) parametrosContexto.get(ConstantesContexto.INDICADOR_UGPP)).shortValue();
        }
        Date fechaPago = FuncionesValidador.convertirDate((String) parametrosContexto.get(ConstantesContexto.NOMBRE_FECHA_PAGO));      
        Date fechaVencimiento = (Date) parametrosContexto.get(ConstantesContexto.FECHA_VENCIMIENTO);
        String periodoPago = (String) parametrosContexto.get(ConstantesContexto.NOMBRE_PERIODO_PAGO);
        Set<String> tiposCotizantes = (HashSet<String>) parametrosContexto.get(ConstantesContexto.TIPOS_COTIZANTES_ENCONTRADOS);
        
        if (aplicarValidacionMora(resultTemp, indicePlanilla, idCampo, tipoError, nombreCampo, numeroLinea, valorMora,
                valorTotalMoraCalculado, toleranciaValorMora, casosDescuento, fechaPago, periodoPago, tiposCotizantes)) {

            // se calculan descuentos para el valor de mora
            valorTotalMoraCalculado = FuncionesValidador.aplicarDescuentoMora(valorTotalMoraCalculado, casosDescuento,
                    indicePlanilla.getTipoArchivo().getPerfilArchivo(), indicadorUGPP, fechaPago.getTime(), periodoPago, tiposCotizantes);

            //No se valida ya que no existe fecha de vencimiento y el total de la mora calculado fue 0
            if(fechaVencimiento == null && valorTotalMoraCalculado.compareTo(new BigDecimal(0))== 0 ) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return resultTemp;
            }
            
            if (!FuncionesValidador.validarValorMora(valorMora, valorTotalMoraCalculado, toleranciaValorMora)
            		&& !TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)) {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                df.setMinimumFractionDigits(0);
                df.setGroupingUsed(false);
                                
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_DIFERENCIA_RESPECTO_A_VALOR_CALCULADO_PLANILLA
                        .getReadableMessage(idCampo, df.format(valorMora), tipoError, nombreCampo, df.format(valorTotalMoraCalculado));

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, BloqueValidacionEnum.BLOQUE_2_OI, mensaje,
                        TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    
    /**
     * @param result
     * @param indicePlanilla
     * @param idCampo
     * @param tipoError
     * @param nombreCampo
     * @param numeroLinea
     * @param valorMora
     * @param valorTotalMoraCalculado
     * @param toleranciaValorMora
     * @param casosDescuento
     * @param fechaPago
     * @param periodoPago
     * @param tiposCotizantes
     * @return
     */
    private Boolean aplicarValidacionMora(RespuestaValidacionDTO result, IndicePlanilla indicePlanilla, String idCampo, String tipoError,
            String nombreCampo, Long numeroLinea, BigDecimal valorMora, BigDecimal valorTotalMoraCalculado, BigDecimal toleranciaValorMora,
            List<DescuentoInteresMoraModeloDTO> casosDescuento, Date fechaPago, String periodoPago, Set<String> tiposCotizantes) {

        Boolean continuar = Boolean.TRUE;
        String mensaje = null;
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;

        RespuestaValidacionDTO resultTemp = result;

        if (valorMora == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.VALOR_MORA_PLANILLA);

            result = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (valorTotalMoraCalculado == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.VALOR_MORA_CALCULADO);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (toleranciaValorMora == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.VALOR_TOLERANCIA_VALOR_MORA);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (casosDescuento == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.CASOS_DESCUENTO_MORA);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (fechaPago == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.FECHA_APORTE);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (periodoPago == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.PERIODO_APORTE);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }

        if (tiposCotizantes == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.TIPOS_COTIZANTES_PLANILLA);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje,
                    TipoErrorValidacionEnum.obtenerTipoError(tipoError), numeroLinea);

            continuar = Boolean.FALSE;
        }
        return continuar;
    }
    
    /**
     * Método encargado de verificar el intercambio de valores de un tipo de archivo a otro a través de la tabla PilaPasoValores
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param parametrosContexto 
     */
    private void verificarIntercambioValoresEntreArchivos(IndicePlanilla indicePlanilla, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ArchivosPILAEjecucion.verificarIntercambioValoresEntreArchivos(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // paso de la clase de aportante y la naturaleza jurídica
        if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(indicePlanilla.getTipoArchivo().getGrupo())) {

            gestionarPasoValor(indicePlanilla, ConstantesContexto.CLASE_APORTANTE, parametrosContexto);
            gestionarPasoValor(indicePlanilla, ConstantesContexto.NATURALEZA_JURIDICA, parametrosContexto);
            gestionarPasoValor(indicePlanilla, ConstantesContexto.TIPO_PERSONA, parametrosContexto);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método encargado de la gestión del paso de valores entre archivos
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param claveValor
     *        Clave de la variable que se gestionará en el paso de valor
     * @param parametrosContexto 
     */
    @SuppressWarnings("unchecked")
    private void gestionarPasoValor(IndicePlanilla indicePlanilla, String claveValor, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ArchivosPILAEjecucion.gestionarPasoValor(IndicePlanilla, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // sí ya se cuenta con un paso de valores, se debe eliminar
        if (parametrosContexto.get(ConstantesContexto.PASO_VARIABLES) != null
                && !((List<PasoValores>) parametrosContexto.get(ConstantesContexto.PASO_VARIABLES)).isEmpty()) {

            List<PasoValores> valoresEnviados = (List<PasoValores>) parametrosContexto.get(ConstantesContexto.PASO_VARIABLES);
        //MANTIS 266507 Se deja la ejecucion como estaba antes de mejoras pila mundo 2
	//persistencia.eliminarVariableEspecificaBatch(claveValor, valoresEnviados);
            
            for (PasoValores pasoValores : valoresEnviados) {
                if (pasoValores.getNombreVariable().equals(claveValor)) {
                    persistencia.eliminarVariableEspecifica(pasoValores.getId());
                }
            }
        }
        else {

            // sí no ha habido un paso de variables previo, se agrega
            // se prepara un mapa con el campos y su valores
            HashMap<String, String[]> variables = new HashMap<>();
            Object valor = parametrosContexto.get(claveValor);
            if (valor != null) {
                variables.put(claveValor,
                        new String[] { "", valor instanceof String ? (String) valor : Long.toString(((Number) valor).longValue()) });

                // se solicita la persistencia de la variable
                persistencia.almacenarVariablesBatch(variables, BloqueValidacionEnum.BLOQUE_2_OI, indicePlanilla.getTipoArchivo(),
                        indicePlanilla.getIdPlanilla(), indicePlanilla.getCodigoOperadorInformacion());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método encargado de llevar a cabo los pasos finales de la validación del bloque 2 OI
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param fileOutDTO
     *        DTO con el resultado de la ejecución del componente de lectura
     * @param errores
     *        Listado de errores encontrados durante la validación
     * @param result
     *        DTO con el resultado del bloque de validación
     * @param parametrosContexto 
     * @return <b>EstadoProcesoArchivoEnum</b>
     *         Estado a registrar para el archivo en el bloque 2
     */
    @SuppressWarnings("unchecked")
    private EstadoProcesoArchivoEnum finalizarValidacionB2OI(IndicePlanilla indicePlanilla, FileLoaderOutDTO fileOutDTO,
            List<ErrorDetalladoValidadorDTO> errores, RespuestaValidacionDTO result, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "finalizarValidacionB2OI(IndicePlanilla, FileLoaderOutDTO, List<ErrorDetalladoValidadorDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se determina sí se presentó error de tipo 1 a partir del contexto
        Boolean errorT1 = Boolean.FALSE;
        Boolean onlyT0 = Boolean.TRUE;
        
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;

        // se revisan los tipos de inconsistencia presentados en la validación
        for (ErrorDetalladoValidadorDTO error : errores) {
            if (!TipoErrorValidacionEnum.TIPO_0.equals(error.getTipoError())
                    || ConstantesComunesProcesamientoPILA.CODIGO_FALTA_PARAMETRO.equals(error.getCodigoError())) {
                onlyT0 = Boolean.FALSE;
            }

            if (TipoErrorValidacionEnum.TIPO_1.equals(error.getTipoError())
                    && !ConstantesComunesProcesamientoPILA.CODIGO_FALTA_PARAMETRO.equals(error.getCodigoError())) {
                errorT1 = Boolean.TRUE;
            }
        }

        EstadoProcesoArchivoEnum estado = null;

        // sí el proceso termina con éxito
        if (FileLoadedState.SUCCESFUL.equals(fileOutDTO.getState()) || onlyT0) {
            // persiste la información requerida por el bloque 3
            if (parametrosContexto.get(ConstantesContexto.INFORMACION_BLOQUE_3) != null) {

                persistencia.almacenarVariablesBatch((Map<String, String[]>) parametrosContexto.get(ConstantesContexto.INFORMACION_BLOQUE_3),
                        BloqueValidacionEnum.BLOQUE_3_OI, indicePlanilla.getTipoArchivo(), indicePlanilla.getIdPlanilla(),
                        indicePlanilla.getCodigoOperadorInformacion());

                estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA;

                // se actualiza el bloque de ejecución para el DTO del archivo
                bloqueActual = BloqueValidacionEnum.BLOQUE_3_OI;
            }
            else {
                String mensaje = MensajesValidacionEnum.ERROR_NO_SE_PUEDE_PREPARAR_INFO_PARA_BLOQUE_3
                        .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.name());

                result = FuncionesValidador.agregarError(result, indicePlanilla, bloqueActual, mensaje,
                        TipoErrorValidacionEnum.ERROR_TECNICO, null);

                estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA;
                logger.info("MANTIS252559 PUNTO 1");
                // se actualiza el bloque de ejecución para el DTO del archivo
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }
        }
        else {
            if (errorT1) {
                if (parametrosContexto.get(ConstantesContexto.INFORMACION_BLOQUE_3) != null) {

                    persistencia.almacenarVariablesBatch((Map<String, String[]>) parametrosContexto.get(ConstantesContexto.INFORMACION_BLOQUE_3),
                            BloqueValidacionEnum.BLOQUE_3_OI, indicePlanilla.getTipoArchivo(), indicePlanilla.getIdPlanilla(),
                            indicePlanilla.getCodigoOperadorInformacion());

                    estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA;
                    logger.info("MANTIS252559 PUNTO 2");
                    // se actualiza el bloque para detener al orquestador
                    bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
                }
                else {
                    String mensaje = MensajesValidacionEnum.ERROR_NO_SE_PUEDE_PREPARAR_INFO_PARA_BLOQUE_3
                            .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.name());

                    result = FuncionesValidador.agregarError(result, indicePlanilla, bloqueActual, mensaje,
                            TipoErrorValidacionEnum.ERROR_TECNICO, null);

                    estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA;
                    logger.info("MANTIS252559 PUNTO 3");
                    // se actualiza el bloque de ejecución para el DTO del archivo
                    bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
                }
            }
            else {
                logger.info("MANTIS252559 PUNTO 4");
                estado = EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA;

                // se actualiza el bloque para detener al orquestador
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }
        }
        
        result.setBloqueSiguente(bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return estado;
    }
    
}
