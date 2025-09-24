package com.asopagos.pila.validadores.bloque3.ejb;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoParejaArchivosEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaRegistroEstadoDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque3.interfaces.IValidacionOIBloque3;
import com.asopagos.pila.validadores.bloque3.interfaces.ProcesadorOIBloque3Local;
import com.asopagos.novedades.composite.clients.InsercionMonitoreoLogs;

/**
 * Bean de sesión que procesa la lógica de Bloque 3 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque3Business extends ProcesadorBloque implements ProcesadorOIBloque3Local{

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IValidacionOIBloque3 validacionOIBloque3;

    private InsercionMonitoreoLogs logsMonitoreo;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque3Business.class);
    
    private void init() {
        super.preparadorContexto = this.preparadorContexto;
        super.gestorEstados      = this.gestorEstados;
        super.persistencia       = this.persistencia;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar(Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque3Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(indicePlanilla);
        procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);
        
        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para la ejecución del Bloque de validación 3 (B3) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     */
    @SuppressWarnings("unchecked")
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "ProcesadorOIBloque3Business.ejecutarBloque(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_3_OI;

        if (indicePlanilla != null) {
            IndicePlanilla indiceA = null;
            IndicePlanilla indiceI = null;

            if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
                indiceA = indicePlanilla;
                indiceI = buscarArchivoHermano(indicePlanilla);
            }
            else {
                indiceI = indicePlanilla;
                indiceA = buscarArchivoHermano(indicePlanilla);
            }

            Map<String, Object> valEstadosPareja = comprobarEstadoParejaArchivos(indicePlanilla, 2, result, bloqueActual);

            RespuestaRegistroEstadoDTO resultadoComparacionPareja = (RespuestaRegistroEstadoDTO) valEstadosPareja.get(ESTADO_PAREJA);
            result = (RespuestaValidacionDTO) valEstadosPareja.get(RESULTADO_VALIDACION);

            if (resultadoComparacionPareja != null
                    && EstadoParejaArchivosEnum.CONCUERDAN.equals(resultadoComparacionPareja.getEstadoPareja())) {

                // se prepara el contexto para la validación
                Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, /*false,*/ bloqueActual);

                boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
                result = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
                Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);
                bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);

                if (iniciar) {
                    Map<String, Object> resultadoValizacionB3 = realizarValidacionB3(indiceI, indiceA, indicePlanilla, result,
                            parametrosContexto);

                    EstadoProcesoArchivoEnum estado = (EstadoProcesoArchivoEnum) resultadoValizacionB3.get(ESTADO_ARCHIVO);
                    result = (RespuestaValidacionDTO) resultadoValizacionB3.get(RESULTADO_VALIDACION);
                    bloqueActual = (BloqueValidacionEnum) resultadoValizacionB3.get(BLOQUE_SIGUIENTE);

                    result.setBloqueSiguente(bloqueActual);
                    result = actualizarEstadosParejaArchivosB3(indiceI, indiceA, indicePlanilla, result, estado);
                }
                else {
                    // se pone al archivo en espera

                    EstadoProcesoArchivoEnum estado = EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_EN_ESPERA;

                    // se actualiza el bloque para detener al orquestador
                    result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
                    result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 3, null);
                }
            }
            else {

                // se actualiza el bloque para detener al orquestador
                result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método encargado de realizar comparación de pareja de archivos
     * @param indiceI
     *        Índice de planilla de archivo de detalle de aporte
     * @param indiceA
     *        Índice de planilla de archivo de información del aportante
     * @param indiceInicial
     *        Índice de planilla en flujo de validación
     * @param result
     *        DTO con el resultado de la validación
     * @param parametrosContexto 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación y el estado para el archivo luego de la comparación de pareja de
     *         archivos
     */
    private Map<String, Object> realizarValidacionB3(IndicePlanilla indiceI, IndicePlanilla indiceA, IndicePlanilla indiceInicial,
            RespuestaValidacionDTO result, Map<String, Object> parametrosContexto) {
        String firmaMetodo = "realizarValidacionB3(IndicePlanilla, IndicePlanilla, IndicePlanilla, RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_3_OI;

        RespuestaValidacionDTO resultTemp = result;
        EstadoProcesoArchivoEnum estado = null;

        try {
            // se valida el bloque 3
            resultTemp = validacionOIBloque3.validarBloque3(parametrosContexto, resultTemp);

            if (resultTemp == null || resultTemp.getErrorDetalladoValidadorDTO().isEmpty()) {
                estado = EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_CONSISTENTES;

                // se actualiza el bloque de ejecución para el DTO del archivo
                bloqueActual = BloqueValidacionEnum.BLOQUE_4_OI;
            }
            else {
                estado = EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES;

                // se actualiza el bloque para detener al orquestador
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }
        } catch (ErrorFuncionalValidacionException e) {
            // se captura e ingresa a la respuesta cualquier inconsistencia encontrada durante la validación

            resultTemp = FuncionesValidador.agregarError(resultTemp, indiceInicial, bloqueActual, e.getMessage(), null, null);

            estado = EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES;

//            this.logsMonitoreo = new InsercionMonitoreoLogs("realizarValidacionB3(IndicePlanilla: "+indiceI+", IndicePlanilla: "+indiceA+", IndicePlanilla: "+indiceInicial+",RespuestaValidacionDTO: , Map<String, Object> parametrosContexto)","ProcesadorOIBloque3 Catch con excepcion: "+ e.getMessage());
//            this.logsMonitoreo.execute();

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        if (resultTemp != null) {
            // sí se han presentado errores, se les agrega el id de índice de planilla correspondiente
            for (ErrorDetalladoValidadorDTO error : resultTemp.getErrorDetalladoValidadorDTO()) {
            	if(error != null && error.getTipoArchivo() != null) {
	                if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(error.getTipoArchivo().getSubtipo()) && indiceI != null) {
	                    error.setIdIndicePlanilla(indiceI.getId());
	                }
	                else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(error.getTipoArchivo().getSubtipo()) && indiceA != null) {
	                    error.setIdIndicePlanilla(indiceA.getId());
	                }
            	}
            }
        }

        // se eliminan las variables de paso
        persistencia.eliminarVariables(indiceInicial.getIdPlanilla(), indiceInicial.getCodigoOperadorInformacion());

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estado);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * 
     * @param indiceI
     *        Índice de planilla de archivo de detalle de aporte
     * @param indiceA
     *        Índice de planilla de archivo de información del aportante
     * @param indiceInicial
     *        Índice de planilla en flujo de validación
     * @param result
     *        DTO con el resultado de la validación
     * @param estado
     *        Estado a asignar a la pareja de archivos
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado en el caso de presentar errores
     */
    private RespuestaValidacionDTO actualizarEstadosParejaArchivosB3(IndicePlanilla indiceI, IndicePlanilla indiceA,
            IndicePlanilla indiceInicial, RespuestaValidacionDTO result, EstadoProcesoArchivoEnum estado) {
        String firmaMetodo = "realizarValidacionB3(IndicePlanilla, IndicePlanilla, IndicePlanilla, RespuestaValidacionDTO, "
                + "EstadoProcesoArchivoEnum)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;
        BloqueValidacionEnum bloqueActual = resultTemp.getBloqueSiguente();
        
        try {
            // se actualiza el estado de bloque para ambos índices
            gestorEstados.registrarEstadoParejaArchivos(indiceA, indiceI, 3, estado,
                    FuncionesValidador.determinarAccion(estado, indiceInicial.getTipoArchivo()), "");

            // se actualizan ambos índices
            if (indiceA != null) {
                indiceA.setEstadoArchivo(estado);
                persistencia.actualizarIndicePlanillas(indiceA);
            }

            if (indiceI != null) {
                indiceI.setEstadoArchivo(estado);
                persistencia.actualizarIndicePlanillas(indiceI);
            }
        } catch (ErrorFuncionalValidacionException e) {
            // en este caso, se presenta un error por estado inválido al momento de actualizar el estado por bloque
            // se debe agregar el error a la respuesta y anular los índices

            if (indiceA != null) {
                indiceA.setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);
                persistencia.actualizarIndicePlanillas(indiceA);
            }

            if (indiceI != null) {
                indiceI.setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);
                persistencia.actualizarIndicePlanillas(indiceI);
            }

            resultTemp = FuncionesValidador.agregarError(resultTemp, indiceInicial, BloqueValidacionEnum.BLOQUE_3_OI, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        resultTemp.setBloqueSiguente(bloqueActual);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    
}
