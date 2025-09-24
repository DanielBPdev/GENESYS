package com.asopagos.pila.validadores.bloque6.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque6.interfaces.IValidacionOIBloque6;
import com.asopagos.pila.validadores.bloque6.interfaces.ProcesadorOIBloque6Local;

/**
 * Bean de sesión que procesa la lógica de Bloque 6 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque6Business extends ProcesadorBloque implements ProcesadorOIBloque6Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private IValidacionOIBloque6 validacionOIBloque6;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque6Business.class);
    
    private void init() {
        super.preparadorContexto  = this.preparadorContexto;
        super.gestorEstados       = this.gestorEstados;
        super.persistencia        = this.persistencia;
        super.persistenciaEstados = this.persistenciaEstados;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar(Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque6Business.procesar(Map<String, Object>, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(indicePlanilla);
        procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);
        
        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método para la ejecución del Bloque de validación 6 (B6) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla) {
        
        String firmaMetodo = "ProcesadorOIBloque6Business.ejecutarBloque(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_6_OI;

        // este bloque sólo se ejecuta para archivos de aporte detallado, contando con un índice
        if (indicePlanilla != null && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {

            // el estado del bloque 5, se emplea para determinar que se evaluó el ID del aportante
            // y que cualquier acción ejecutada desde bandeja termina con estado ARCHIVO_CONSISTENTE

            Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndice(indicePlanilla, result,
                    BloqueValidacionEnum.BLOQUE_5_OI, bloqueActual);

            EstadoProcesoArchivoEnum estadoB5 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
            result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
            bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

            resultadoConsultaEstado = consultarEstadoBloqueIndice(indicePlanilla, result,
                    BloqueValidacionEnum.BLOQUE_6_OI, bloqueActual);

            EstadoProcesoArchivoEnum estadoB6 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
            
            logger.info("MANTIS0259506  Bloque 6 indice: "+ indicePlanilla.getId() +" estadoB6 "+ estadoB6);//TODO Quitar al finalizar revision
            
            result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
            bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

            if (estadoB5 != null && EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE.equals(estadoB5)) {

                EstadoProcesoArchivoEnum estadoOI = null;

                // antes de iniciar la validación, se comprueba que el bloque 6 no tenga el estado NO_REQUIERE_CONCILIACION
                if (!EstadoProcesoArchivoEnum.NO_REQUIERE_CONCILIACION.equals(estadoB6)) {

                    // se prepara el contexto para la validación
                    Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, /*false,*/ bloqueActual);

                    boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
                    result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
                    Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
                    bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);
                    logger.info("MANTIS0259506  Bloque 6 indice: "+ indicePlanilla.getId() +" bloque actual: "+ bloqueActual);//TODO Quitar al finalizar revision
                    if (iniciar) {
                        parametrosContexto.put(ConstantesContexto.INDICE_PLANILLA, indicePlanilla);

                        // se ejecuta la validación de conciliación con log finaciero
                        Map<String, Object> resultadoEjecucionValidacionB6 = ejecutarValidacionB6(indicePlanilla, result, parametrosContexto);
                        result = (RespuestaValidacionDTO) resultadoEjecucionValidacionB6.get(RESULTADO_VALIDACION);
                        estadoOI = (EstadoProcesoArchivoEnum) resultadoEjecucionValidacionB6.get(ESTADO_ARCHIVO);
                        bloqueActual = (BloqueValidacionEnum) resultadoEjecucionValidacionB6.get(BLOQUE_SIGUIENTE);
                    }
                }
                else {
                    // los archivos que presentan aporte cero se marcan con el estado "RECAUDO_VALOR_CERO_CONCILIADO"
                    estadoOI = EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO;

                    // se actualiza el siguiente bloque de ejecución
                    bloqueActual = BloqueValidacionEnum.BLOQUE_7_OI;
                }

                // se comprueba sí el archivo habilita a otro archivo de corrección en espera
                comprobarHabilitacionCorreccion(indicePlanilla, estadoOI);

                // se actualiza el estado del archivo
                result.setBloqueSiguente(bloqueActual);
                result = actualizarIndiceYEstadoBloque(indicePlanilla, estadoOI, result, 6, null);
            }
        }
        else{
            result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    
    /**
     * Método que ejecuta la validación de conciliación con log financiero
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param result
     *        DTO con el resultado de la validación
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación actualizado en caso de error y estado a asignar al archivo
     */
    private Map<String, Object> ejecutarValidacionB6(IndicePlanilla indicePlanilla, RespuestaValidacionDTO result,
            Map<String, Object> parametrosContexto) {
        String firmaMetodo = "ejecutarValidacionB6(IndicePlanilla, RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_6_OI;

        PilaArchivoFRegistro6 registro6 = null;
        EstadoProcesoArchivoEnum estadoOI = null;
        EstadoConciliacionArchivoFEnum estadoOF = null;

        RespuestaValidacionDTO resultTemp = null;
        resultTemp = validacionOIBloque6.validarBloque6(parametrosContexto, resultTemp);

        // se revisa la respuesta en busqueda de errores, sí no tiene o sólo es de tipo 0, el archivo pasa
        if (resultTemp.getErrorDetalladoValidadorDTO() == null || resultTemp.getErrorDetalladoValidadorDTO().isEmpty()
                || !presentaErrorT1(resultTemp.getErrorDetalladoValidadorDTO())) {
            // en el caso de una validación exitosa, se cambia el estado del bloque 6 para el archivo I y el
            // registro 6 de archivo F
        	
            estadoOI = EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO;
            estadoOF = EstadoConciliacionArchivoFEnum.REGISTRO_6_CONCILIADO;

            registro6 = (PilaArchivoFRegistro6) parametrosContexto.get(ConstantesContexto.REGISTRO_6);

            // se actualiza el siguiente bloque de ejecución
            bloqueActual = BloqueValidacionEnum.BLOQUE_7_OI;
            logger.info("MANTIS0269772 bloqueActual " + bloqueActual);
            // se comprueban errores de conciliación pendiente por falta de archivo OF para su gestión automática
            gestionarInconsistenciaConciliacionPendiente(indicePlanilla.getId());
            logger.info("MANTIS0269772 fin gestionarInconsistenciaConciliacionPendiente indicePlanilla.getId() " + indicePlanilla.getId());
        }
        else {
            registro6 = (PilaArchivoFRegistro6) parametrosContexto.get(ConstantesContexto.REGISTRO_6);

            if (registro6 != null) {
                // se marcan los estados de inconsistencia
                estadoOI = EstadoProcesoArchivoEnum.GESTIONAR_DIFERENCIA_EN_CONCILIACION;
                estadoOF = EstadoConciliacionArchivoFEnum.REGISTRO_6_GESTIONAR_DIFERENCIAS;

                // se comprueban errores de conciliación pendiente por falta de archivo OF para su gestión automática
                gestionarInconsistenciaConciliacionPendiente(indicePlanilla.getId());
            }
            else {
                // el archivo OI se pone en espera de conciliación
                estadoOI = EstadoProcesoArchivoEnum.PENDIENTE_CONCILIACION;
            }

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        // si se tiene un registro tipo 6 OF, se actualiza su estado
        if (registro6 != null) {
            // se actualiza el estado de OF
            gestorEstados.actualizarEstadosOF(registro6, estadoOF);
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estadoOI);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * Método que comprueba que el procesamiento de un archivo para el B6, habilita el procesamiento asistido de PILA 2
     * para un archivo de corrección
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param estado
     *        Estado del índice de planilla que está siendo validado
     */
    private void comprobarHabilitacionCorreccion(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado) {
        String firmaMetodo = "comprobarHabilitacionCorreccion(IndicePlanilla, EstadoProcesoArchivoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // sí el estado del bloque 6 es válido para PILA 2, se debe validar sí habilita a alguna corrección en pausa
        if (EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO.equals(estado)
                || EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO.equals(estado)) {

            persistencia.activarCorreccionPila2(indicePlanilla.getIdPlanilla().toString(), indicePlanilla.getCodigoOperadorInformacion());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método encargado de determinar sí un listado de inconsistencias presenta algún error T1 o superior
     * @param listaErrores
     * @return
     */
    private Boolean presentaErrorT1(List<ErrorDetalladoValidadorDTO> listaErrores) {
        String firmaMetodo = "ArchivosPILAEjecucion.presentaErrorT1(List<ErrorDetalladoValidadorDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (ErrorDetalladoValidadorDTO error : listaErrores) {
            if (!TipoErrorValidacionEnum.TIPO_0.equals(error.getTipoError())
                    || ConstantesComunesProcesamientoPILA.CODIGO_FALTA_PARAMETRO.equals(error.getCodigoError())) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return Boolean.TRUE;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.FALSE;
    }
    
    /**
     * Método encargado de finalizar las inconsistencias asociadas a la conciliación con OF del índice de planilla
     * @param idIndicePlanilla
     *        Id de entrada de índice de planilla para el cual se gestionaran las inconsistencias de bloque 6
     */
    private void gestionarInconsistenciaConciliacionPendiente(Long idIndicePlanilla) {
        String firmaMetodo = "ArchivosPILAEjecucion.gestionarInconsistenciaConciliacionPendiente(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        persistencia.autogestionarInconsistenciasOIPorBloque(idIndicePlanilla, BloqueValidacionEnum.BLOQUE_6_OI,
                EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
}
