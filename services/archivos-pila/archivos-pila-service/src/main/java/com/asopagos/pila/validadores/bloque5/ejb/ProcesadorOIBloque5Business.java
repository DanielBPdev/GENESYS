package com.asopagos.pila.validadores.bloque5.ejb;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque5.interfaces.IValidacionOIBloque5;
import com.asopagos.pila.validadores.bloque5.interfaces.ProcesadorOIBloque5Local;

/**
 * Bean de sesión que procesa la lógica de Bloque 5 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque5Business extends ProcesadorBloque implements ProcesadorOIBloque5Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private IValidacionOIBloque5 validacionOIBloque5;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque5Business.class);
    
    private void init() {
        super.preparadorContexto  = this.preparadorContexto;
        super.gestorEstados       = this.gestorEstados;
        super.persistencia        = this.persistencia;
        super.persistenciaEstados = this.persistenciaEstados;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar(Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque5Business.procesar(Map<String, Object>, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        logger.info("MANTIS0259506  Inicio Bloque 5 indice: "+ indicePlanilla.getId());//TODO Quitar al finalizar revision
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(indicePlanilla);
        procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);
        
        logger.info("MANTIS0259506 Fin Bloque 5 indice: "+ indicePlanilla.getId());//TODO Quitar al finalizar revision
        
        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método para la ejecución del Bloque de validación 5 (B5) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "ProcesadorOIBloque5Business.ejecutarBloque(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_5_OI;

        if (indicePlanilla == null || !SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
            // se actualiza el bloque para detener al orquestador
            result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
        
        // Este bloque sólo se ejecuta para archivos de aporte detallado, contando con un índice
        // independientemente al resultado del bloque 4, salvo que se haya anulado por fallo en la persistencia
        
         Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndice(indicePlanilla, result,
                BloqueValidacionEnum.BLOQUE_4_OI, bloqueActual);

        EstadoProcesoArchivoEnum estadoB4 = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
        
        logger.info("MANTIS0259506  Bloque 5 indice: "+ indicePlanilla.getId() +" estadoB4 "+ estadoB4);//TODO Quitar al finalizar revision
        
        result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
        bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);

        if (estadoB4 == null || !EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_COMPLETADA.equals(estadoB4)) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
        
        // Se prepara el contexto para la validación
        Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, bloqueActual);

        boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
        result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
        Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
        bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);

        Map<String, Object> resultadoConsultaR1OI = consultarRegistro1OI(indicePlanilla, result, iniciar,
                parametrosContexto, bloqueActual);

        result  = (RespuestaValidacionDTO) resultadoConsultaR1OI.get(RESULTADO_VALIDACION);
        iniciar = (boolean) resultadoConsultaR1OI.get(INICIAR_VALIDACION);

        // se agrega indicador de aporte propio para determinar como se consulta al aportante en BD
        parametrosContexto.put(ConstantesContexto.ES_APORTE_PROPIO, persistencia.consultarAportePropio(indicePlanilla.getId()));

        if (iniciar) {
            parametrosContexto.put(ConstantesContexto.INDICE_PLANILLA, indicePlanilla);

            EstadoProcesoArchivoEnum estado = null;

            try {
                validacionOIBloque5.validarBloque5(parametrosContexto);
                estado = EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE;
                logger.info("MANTIS0259506  Bloque 5 try indice: "+ indicePlanilla.getId() +" bloqueActual "+ bloqueActual);//TODO Quitar al finalizar revision
                // se actualiza el bloque de ejecución para el DTO del archivo
                bloqueActual = BloqueValidacionEnum.BLOQUE_6_OI;

            } catch (ErrorFuncionalValidacionException e) {
                // se agrega la inconsistencia en la respuesta
                logger.info("MANTIS0259506  Bloque 5 catch indice: "+ indicePlanilla.getId() +" bloqueActual "+ bloqueActual);//TODO Quitar al finalizar revision
                result = FuncionesValidador.agregarError(result, indicePlanilla, bloqueActual, e.getMessage(), null, null);
                estado = EstadoProcesoArchivoEnum.APORTANTE_NO_CREADO_EN_BD;
                // se actualiza el bloque para detener al orquestador
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }

            result.setBloqueSiguente(bloqueActual);
            result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 5, null);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método encargado de la consulta del registro tipo 1 de un archivo de detalle de aporte para su inclusión en el contexto
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param result
     *        DTO con el resultado de la validación
     * @param iniciar
     *        Indicador que habilita la ejecución del validador
     * @param parametrosContexto 
     * @param bloqueActual 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación y la habilitación para ejecutar el validador actualizado en caso de
     *         error
     */
    private Map<String, Object> consultarRegistro1OI(IndicePlanilla indicePlanilla, RespuestaValidacionDTO result, Boolean iniciar,
            Map<String, Object> parametrosContexto, BloqueValidacionEnum bloqueActual) {
        String firmaMetodo = "consultarRegistro1OI(IndicePlanilla, RespuestaValidacionDTO, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        RespuestaValidacionDTO resultTemp = result;
        Boolean iniciarTemp = iniciar;

        // sólo se consulta para archivo de detalle de aporte
        if (iniciarTemp && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
            Object registro1 = persistencia.consultarRegistro1ArchivoI(indicePlanilla);
            parametrosContexto.put(ConstantesContexto.REGISTRO_1, registro1);

            if (registro1 == null) {
                // en el caso de no encontrar un registro tipo 1 para el índice de planilla, se debe reportar el error
                resultTemp = FuncionesValidador
                        .agregarError(
                                resultTemp, indicePlanilla, bloqueActual, MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS
                                        .getReadableMessage("registro Tipo 1 de Operador de Información"),
                                TipoErrorValidacionEnum.ERROR_TECNICO, null);

                // se actualiza el bloque para detener al orquestador
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
                iniciarTemp = false;
            }
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(INICIAR_VALIDACION, iniciarTemp);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
}
