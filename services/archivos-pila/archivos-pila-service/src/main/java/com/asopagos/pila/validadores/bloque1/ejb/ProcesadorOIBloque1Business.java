package com.asopagos.pila.validadores.bloque1.ejb;

import java.util.Calendar;
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
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.dto.RespuestaRegistroEstadoDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque1.interfaces.IValidacionOIBloque1;
import com.asopagos.pila.validadores.bloque1.interfaces.ProcesadorOIBloque1Local;

/**
 * Bean de sesión que procesa la lógica de Bloque 1 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque1Business extends ProcesadorBloque implements ProcesadorOIBloque1Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IValidacionOIBloque1 validacionOIBloque1;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque1Business.class);
    
    private void init() {
        super.preparadorContexto = this.preparadorContexto;
        super.gestorEstados      = this.gestorEstados;
        super.persistencia       = this.persistencia;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar (Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque1Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        String usuario = (String) parametros.get(USUARIO_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        
        if (indicePlanilla.getFechaProceso() == null) {
            indicePlanilla.setFechaProceso(Calendar.getInstance().getTime());
            indicePlanilla.setUsuarioProceso(usuario);
        }
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(indicePlanilla);
        procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);

        parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método para la ejecución del Bloque de validación 1 (B1) del Operador de Información
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @return <b>RespuestaValidacionDTO</b>
     *         Resultado de la validación
     */
    private RespuestaValidacionDTO ejecutarBloque(IndicePlanilla indicePlanilla) {
        
        String firmaMetodo = "ProcesadorOIBloque1Business.ejecutarBloque(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_1_OI;

        if (indicePlanilla != null) {
            Map<String, Object> valEstadosPareja = comprobarEstadoParejaArchivos(indicePlanilla, 0, result, bloqueActual);

            RespuestaRegistroEstadoDTO resultadoComparacionPareja = (RespuestaRegistroEstadoDTO) valEstadosPareja.get(ESTADO_PAREJA);
            result = (RespuestaValidacionDTO) valEstadosPareja.get(RESULTADO_VALIDACION);
            bloqueActual = (BloqueValidacionEnum) valEstadosPareja.get(BLOQUE_SIGUIENTE);

            if (resultadoComparacionPareja != null) {

                EstadoProcesoArchivoEnum estado = null;

                // marca de control de archivo en espera
                Boolean enEspera = Boolean.FALSE;

                if (EstadoParejaArchivosEnum.CONCUERDAN.equals(resultadoComparacionPareja.getEstadoPareja())) {
                    Map<String, Object> valB1 = validarArchivosConcordantesB1(result, indicePlanilla);

                    result = (RespuestaValidacionDTO) valB1.get(RESULTADO_VALIDACION);
                    estado = (EstadoProcesoArchivoEnum) valB1.get(ESTADO_ARCHIVO);
                    bloqueActual = (BloqueValidacionEnum) valB1.get(BLOQUE_SIGUIENTE);
                }
                else {
                    enEspera = Boolean.TRUE;
                }

                // se busca el archivo AX cuando se procesa un archivo IX y si ha ejecutado el bloque 2
                if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(indicePlanilla.getTipoArchivo().getGrupo())
                        && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {

                    IndicePlanilla indiceHermano = buscarArchivoHermano(indicePlanilla);
                    EstadoProcesoArchivoEnum estadoB2Hermano = null;
                    if (indiceHermano != null) {
                        Map<String, Object> resultadoConsultaEstado = consultarEstadoBloqueIndice(indiceHermano, result,
                                BloqueValidacionEnum.BLOQUE_2_OI, bloqueActual);

                        estadoB2Hermano = (EstadoProcesoArchivoEnum) resultadoConsultaEstado.get(ESTADO_ARCHIVO);
                        result = (RespuestaValidacionDTO) resultadoConsultaEstado.get(RESULTADO_VALIDACION);
                        bloqueActual = (BloqueValidacionEnum) resultadoConsultaEstado.get(BLOQUE_SIGUIENTE);
                    }

                    if (estadoB2Hermano == null || indiceHermano.getEstadoArchivo().getReportarBandejaInconsistencias()) {
                        enEspera = Boolean.TRUE;
                    }
                }

                if (enEspera) {

                    estado = EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_EN_ESPERA;

                    // se actualiza el bloque para detener al orquestador
                    bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
                }

                result.setBloqueSiguente(bloqueActual);
                result = actualizarIndiceYEstadoBloque(indicePlanilla, estado, result, 1, null);
            }
        }
        else {
            result.setBloqueSiguente(BloqueValidacionEnum.FINALIZAR_CICLO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return result;
    }
    
    
    /**
     * Método que prepara contexto, lanza la validación para el archivo y establece el estado para el archivo OI en el B1
     * @param result
     *        DTO con el resultado de la validación
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación y el estado resultante de la misma
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> validarArchivosConcordantesB1(RespuestaValidacionDTO result, IndicePlanilla indicePlanilla) {
        String firmaMetodo = "ProcesadorOIBloque1Business.validarArchivosConcordantesB1(RespuestaValidacionDTO, IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        EstadoProcesoArchivoEnum estado = null;

        Map<String, Object> respuesta = new HashMap<>();

        // se ejecuta la verificación del bloque 1
        RespuestaValidacionDTO resultTemp = null;

        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_1_OI;
        
        // se prepara el contexto para la validación
        Map<String, Object> resultadoPrepararcionContexto = prepararContextoBloque(indicePlanilla, result, /*true,*/ bloqueActual);

        boolean iniciar = (boolean) resultadoPrepararcionContexto.get(INICIAR_VALIDACION);
        resultTemp   = (RespuestaValidacionDTO) resultadoPrepararcionContexto.get(RESULTADO_VALIDACION);
        bloqueActual = (BloqueValidacionEnum) resultadoPrepararcionContexto.get(BLOQUE_SIGUIENTE);
        Map<String, Object> parametrosContexto = (Map<String, Object>) resultadoPrepararcionContexto.get(CONTEXTO);

        if (indicePlanilla != null && iniciar) {
            try {
                resultTemp = validacionOIBloque1.validacionNombreArchivo(indicePlanilla.getNombreArchivo(), parametrosContexto,
                        indicePlanilla.getTipoArchivo().getPerfilArchivo(), resultTemp);

                if (resultTemp == null || resultTemp.getErrorDetalladoValidadorDTO().isEmpty()) {
                    estado = EstadoProcesoArchivoEnum.EN_PROCESO;

                    // se actualiza el siguiente bloque de ejecución
                    bloqueActual = BloqueValidacionEnum.BLOQUE_2_OI;
                }
                else {
                    estado = EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO;

                    // se actualiza el bloque para detener al orquestador
                    bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
                }

            } catch (ErrorFuncionalValidacionException e) {
                // se captura e ingresa a la respuesta cualquier inconsistencia encontrada durante la validación

                resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(), null, null);

                estado = EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO;

                // se actualiza el bloque para detener al orquestador
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }
        }

        respuesta.put(RESULTADO_VALIDACION, resultTemp);
        respuesta.put(ESTADO_ARCHIVO, estado);
        respuesta.put(BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    
}
