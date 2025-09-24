package com.asopagos.pila.validadores;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoLineaTipoRegistroEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaRegistroEstadoDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque0.interfaces.ProcesadorOFBloque0Local;
import com.asopagos.pila.validadores.bloque0.interfaces.ProcesadorOIBloque0Local;
import com.asopagos.pila.validadores.bloque1.interfaces.ProcesadorOFBloque1Local;
import com.asopagos.pila.validadores.bloque1.interfaces.ProcesadorOIBloque1Local;
import com.asopagos.pila.validadores.bloque2.interfaces.ProcesadorOIBloque2Local;
import com.asopagos.pila.validadores.bloque3.interfaces.ProcesadorOIBloque3Local;
import com.asopagos.pila.validadores.bloque4.interfaces.ProcesadorOIBloque4Local;
import com.asopagos.pila.validadores.bloque5.interfaces.ProcesadorOIBloque5Local;
import com.asopagos.pila.validadores.bloque6.interfaces.ProcesadorOIBloque6Local;

/**
 * Clase padre de todos los bloques que serán procesados. </p>
 * Esta clase y todos los bean de bloques, implementan el patrón
 * pipe and filter, en donde cada bloque actúa como filter y el método procesarBloque
 * de esta clase actúa como el pipe, que invoca el siguiente filtro de la cadena de procesamiento. 
 * @author jrico
 *
 */
public class ProcesadorBloque {

	@Inject
    protected IPrepararContexto preparadorContexto;
    
    @Inject
    protected IGestorEstadosValidacion gestorEstados;
    
    @Inject
    protected IPersistenciaDatosValidadores persistencia;
    
    @Inject
    protected IPersistenciaEstadosValidacion persistenciaEstados;
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ProcesadorBloque.class);
    
    /**
     * Método que procesa el bloque indicado por el parámetro <code>bloqueProcesar</code>, con lo cual
     * se ejecuta el siguiente filtro (bloque) del proceso. Este método actúa como el Pipe (Patrón Pipe and Filter)
     * que invoca el siguiente filtro de la cadena de procesamiento.  
     * @param bloqueProcesar
     * @param parametros
     * @param intento
     */
    protected void procesarBloque (BloqueValidacionEnum bloqueProcesar, Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorBloque.procesarBloque (BloqueValidacionEnum, Map<String, Object>, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        IProcesadorBloque procesadorBloque = null;
        
        if (bloqueProcesar != null) {

        switch (bloqueProcesar) {
            case BLOQUE_0_OI: 
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque0Local.class);
                break;
            case BLOQUE_1_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque1Local.class);
                break;
            case BLOQUE_2_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque2Local.class);
                break;
            case BLOQUE_3_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque3Local.class);
                break;
            case BLOQUE_4_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque4Local.class);
                break;
            case BLOQUE_5_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque5Local.class);
                break;
            case BLOQUE_6_OI:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOIBloque6Local.class);
                break;
            case BLOQUE_0_OF:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOFBloque0Local.class);
                break;
            case BLOQUE_1_OF:
                procesadorBloque = (IProcesadorBloque) ResourceLocator.lookupEJBReference(ProcesadorOFBloque1Local.class);
                break;
            default:
                break;
        }
        
        }
        
        if(procesadorBloque != null) {
            procesadorBloque.procesar(parametros, intento);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param result
     *        DTO con el resultado de la validación
     * @param adicionarNombre
     *        Indicador para determinar que se deben agregar las variables del nombre del archivo
     * @param bloqueActual 
     * @return <b>Map<String, Object></b>
     *         Mapa con el DTO del resultado general de la validación y la autorización para iniciar proceso bloque actual
     */
    protected Map<String, Object> prepararContextoBloque(Object indicePlanilla, RespuestaValidacionDTO result, /*Boolean adicionarNombre,*/ BloqueValidacionEnum bloqueActual) {
        
        String firmaMetodo = "ProcesadorBloque.prepararContextoBloque(IndicePlanilla, RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean iniciar = true;

        Map<String, Object> respuesta       = new HashMap<>();
        Map<String, Object> parametrosContexto = null;
        RespuestaValidacionDTO resultTemp      = result;
        Long idPlanilla      = null;
        
        if (indicePlanilla != null && indicePlanilla instanceof IndicePlanilla) {
            idPlanilla = ((IndicePlanilla) indicePlanilla).getIdPlanilla();
        }
        
        try {
            // se prepara el contexto
            parametrosContexto = preparadorContexto.prepararContexto(bloqueActual, idPlanilla, indicePlanilla);
        } catch (ErrorFuncionalValidacionException e) {
            // se presentan problemas al preparar el contexto para la validación, en este caso se debe agregar el error

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            iniciar = false;
        }

        respuesta.put(IProcesadorBloque.RESULTADO_VALIDACION, resultTemp);
        respuesta.put(IProcesadorBloque.INICIAR_VALIDACION, iniciar);
        respuesta.put(IProcesadorBloque.CONTEXTO, parametrosContexto);
        respuesta.put(IProcesadorBloque.BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param bloque
     *        Número de bloque que se está consultando
     * @param result
     *        DTO con el resultado de la validación
     * @param bloqueActual 
     * @return <b>Map<String, Object></b>
     *         Resultado de la comprobación (DTO de resultado de validación actualizado en caso de errores y estado de pareja de archivos)
     */
    protected Map<String, Object> comprobarEstadoParejaArchivos(IndicePlanilla indicePlanilla, Integer bloque,
            RespuestaValidacionDTO result, BloqueValidacionEnum bloqueActual) {
        
        String firmaMetodo = "ProcesadorBloque.comprobarEstadoParejaArchivos(IndicePlanilla, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> resultado = new HashMap<>();

        RespuestaValidacionDTO resultTemp = result;
        RespuestaRegistroEstadoDTO resultadoComparacionPareja = null;

        try {
            // se revisa que se cuente con una pareja de archivos con estado DESCARGADO para B0
            resultadoComparacionPareja = gestorEstados.parejaArchivosAprobadaPorBloque(indicePlanilla, bloque);
        } catch (ErrorFuncionalValidacionException e) {
            // no se encuentran estados previos, se debe generar respuesta y pasar el error

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        resultado.put(IProcesadorBloque.RESULTADO_VALIDACION, resultTemp);
        resultado.put(IProcesadorBloque.ESTADO_PAREJA, resultadoComparacionPareja);
        resultado.put(IProcesadorBloque.BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    
    /**
     * Función para ubicar un archivo "hermano" en el índice de planillas
     * 
     * @param indiceInicial
     *        Entrada de índice de planilla base para buscar el archivo "hermano"
     * @return IndicePlanilla
     *         Entrada de índice de planilla del archivo "hermano". Null en caso de no encontrarlo
     */
    protected IndicePlanilla buscarArchivoHermano(IndicePlanilla indiceInicial) {
        String firmaMetodo = "ProcesadorBloque.buscarArchivoHermano(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IndicePlanilla result = null;

        // de acuerdo al tipo de archivo del índice inicial, se determina el tipo del archivo del archivo hermano
        TipoArchivoPilaEnum tipoHermano = null;
        switch (indiceInicial.getTipoArchivo()) {
            case ARCHIVO_OI_A:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_I;
                break;
            case ARCHIVO_OI_I:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_A;
                break;
            case ARCHIVO_OI_AP:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_IP;
                break;
            case ARCHIVO_OI_IP:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_AP;
                break;
            case ARCHIVO_OI_AR:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_IR;
                break;
            case ARCHIVO_OI_IR:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_AR;
                break;
            case ARCHIVO_OI_APR:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_IPR;
                break;
            case ARCHIVO_OI_IPR:
                tipoHermano = TipoArchivoPilaEnum.ARCHIVO_OI_APR;
                break;
            default:
                break;
        }

        // con el tipo de archivo esperado del hermano y el # de planilla, se busca el índice
        if (tipoHermano != null) {
            result = persistencia.consultarIndicePlanillaTipo(indiceInicial.getIdPlanilla(), tipoHermano,
                    indiceInicial.getCodigoOperadorInformacion());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método encargado de consultar el estado de un índice de planilla en un bloque específico
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param result
     *        DTO con el resultado de la validación
     * @param bloqueEvaluado
     *        Número del bloque a validar
     * @param bloqueActual 
     * @return <b>Map<String, Object></b>
     *         Resultado de la comprobación (DTO de resultado de validación actualizado en caso de errores y estado del archivo en el
     *         bloque indicado)
     */
    protected Map<String, Object> consultarEstadoBloqueIndice(IndicePlanilla indicePlanilla, RespuestaValidacionDTO result,
            BloqueValidacionEnum bloqueEvaluado, BloqueValidacionEnum bloqueActual) {
        String firmaMetodo = "ProcesadorBloque.consultarEstadoBloqueIndice(IndicePlanilla, RespuestaValidacionDTO, BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> respuesta = new HashMap<>();

        EstadoArchivoPorBloque estadosPlanilla = null;
        RespuestaValidacionDTO resultTemp = result;
        EstadoProcesoArchivoEnum estado = null;
        try {
            estadosPlanilla = persistenciaEstados.consultarEstadoEspecificoOI(indicePlanilla);
        } catch (ErrorFuncionalValidacionException e) {
            // no se encuentran estados previos, se debe generar respuesta y pasar el error

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        if (estadosPlanilla != null) {
            switch (bloqueEvaluado) {
                case BLOQUE_0_OI:
                    estado = estadosPlanilla.getEstadoBloque0();
                    break;
                case BLOQUE_1_OI:
                    estado = estadosPlanilla.getEstadoBloque1();
                    break;
                case BLOQUE_2_OI:
                    estado = estadosPlanilla.getEstadoBloque2();
                    break;
                case BLOQUE_3_OI:
                    estado = estadosPlanilla.getEstadoBloque3();
                    break;
                case BLOQUE_4_OI:
                    estado = estadosPlanilla.getEstadoBloque4();
                    break;
                case BLOQUE_5_OI:
                    estado = estadosPlanilla.getEstadoBloque5();
                    break;
                case BLOQUE_6_OI:
                    estado = estadosPlanilla.getEstadoBloque6();
                    break;
                case BLOQUE_7_OI:
                    estado = estadosPlanilla.getEstadoBloque7();
                    break;
                case BLOQUE_8_OI:
                    estado = estadosPlanilla.getEstadoBloque8();
                    break;
                case BLOQUE_9_OI:
                    estado = estadosPlanilla.getEstadoBloque9();
                    break;
                case BLOQUE_10_OI:
                    estado = estadosPlanilla.getEstadoBloque10();
                    break;
                default:
                    break;
            }
        }

        respuesta.put(IProcesadorBloque.RESULTADO_VALIDACION, resultTemp);
        respuesta.put(IProcesadorBloque.ESTADO_ARCHIVO, estado);
        respuesta.put(IProcesadorBloque.BLOQUE_SIGUIENTE, bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }
    
    /**
     * Método para agregar un índice a la lista de proceso
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param estado
     *        Estado del archivo en un bloque específico para determinar sí es válido agregarlo a la lista
     * @param indicesOI 
     */
    protected void agregarALista(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado, List<IndicePlanilla> indicesOI) {
        String firmaMetodo = "ProcesadorBloque.agregarALista(IndicePlanilla, EstadoProcesoArchivoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // si el estado del archivo para el bloque está vacío y no está en lista, se le agrega
        if (estado == null && estaEnLista(indicePlanilla, indicesOI)) {
            indicePlanilla.setEnLista(true);
            persistencia.actualizarIndicePlanillas(indicePlanilla);
            indicesOI.add(indicePlanilla);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método para determinar si un índice se encuentra en la lista de procesamiento sin haber sido procesado
     * @param indiceNuevo
     *        Entrada de índice de planilla que se desea agregar
     * @param indicesOI 
     * @return Boolean
     *         Indica sí la entrada de índice es válida para ser agregada
     */
    private boolean estaEnLista(IndicePlanilla indiceNuevo, List<IndicePlanilla> indicesOI) {
        String firmaMetodo = "ProcesadorBloque.estaEnLista(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se recorre el listado en us estado actual
        for (IndicePlanilla indicePlanilla : indicesOI) {
            // se comparan los ID de índice y el estado enLista. Sí aún figura en lista, no se debe agregar de nuevo
            if (indicePlanilla.getId().compareTo(indiceNuevo.getId()) == 0 && indicePlanilla.getEnLista()) {
                return false;
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return true;
    }
    
    /**
     * Método encargado de tomar los errores detallados de componente y agregarlos a la lsita de errores de una respuesta de validación
     * 
     * @param result
     *        DTO con la respuesta de validación a la cual se le van a agregar los errores de componente
     * @param contexto
     *        Mapa de variables de contexto desde el cual se tomará la lista de errores de validación del componente
     * @param indicePlanilla
     *        Entrada del índice de planillas que se está evaluando (OI u OF)
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO de respuesta de validador actualizado
     */
    @SuppressWarnings("unchecked")
    protected RespuestaValidacionDTO agregarErroresDeComponente(RespuestaValidacionDTO result, Map<String, Object> contexto,
            Object indicePlanilla) {
        String firmaMetodo = "ProcesadorBloque.agregarErroresDeComponente(RespuestaValidacionDTO, FileLoaderOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;

        // si el resultado no existe, se le inicializa
        if (resultTemp == null) {
            resultTemp = new RespuestaValidacionDTO();
        }
        
        if (contexto != null && contexto.get(ConstantesContexto.ERRORES_DETALLADOS) != null) {
            List<ErrorDetalladoValidadorDTO> listaErrores = (List<ErrorDetalladoValidadorDTO>) contexto
                    .get(ConstantesContexto.ERRORES_DETALLADOS);

            for (ErrorDetalladoValidadorDTO error : listaErrores) {
                
                Boolean existeErrorOF = false;
                // por medio del índice de planilla recibido por parámetro, se especifica el tipo de archivo
                if (indicePlanilla != null) {
                    if (indicePlanilla instanceof IndicePlanilla) {
                        error.setTipoArchivo(((IndicePlanilla) indicePlanilla).getTipoArchivo());
                    }
                    else if (indicePlanilla instanceof IndicePlanillaOF) {
                        error.setTipoArchivo(((IndicePlanillaOF) indicePlanilla).getTipoArchivo());
                        
                        //Revisar si ya existe error ya agregado anteriormente
                        for (ErrorDetalladoValidadorDTO error2 : resultTemp.getErrorDetalladoValidadorDTO()) {
                        	if(error.getCodigoError().contentEquals(error2.getCodigoError())) {
                              existeErrorOF = true; 
                              error2 = error;
                          } 
                        }
                    }
                }
                
                if(!existeErrorOF) {
                    resultTemp.addErrorDetalladoValidadorDTO(error);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    
    
    /**
     * Método encargado de la validación de registros únicos en la planilla
     * 
     * @param result
     *        DTO con el resultado de la validación
     * @param indicePlanilla
     *        Índice de planilla que está siendo validado
     * @param parametrosContexto 
     * @param bloqueActual 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado
     */
    @SuppressWarnings("unchecked")
    protected RespuestaValidacionDTO validarRegistrosUnicos(RespuestaValidacionDTO result, Object indicePlanilla,
            Map<String, Object> parametrosContexto, BloqueValidacionEnum bloqueActual) {
        String firmaMetodo = "ProcesadorBloque.validarRegistrosUnicos(RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;

        Map<TipoLineaTipoRegistroEnum, Integer> listaControlRegistros = (EnumMap<TipoLineaTipoRegistroEnum, Integer>) parametrosContexto
                .get(ConstantesContexto.LISTA_CONTROL_REGISTROS);

        TipoErrorValidacionEnum tipoError = TipoErrorValidacionEnum.TIPO_2;

        String mensaje = null;

        Integer cuentaRegistros;
        if (indicePlanilla instanceof IndicePlanilla) {
            switch (((IndicePlanilla) indicePlanilla).getTipoArchivo()) {
                case ARCHIVO_OI_A:
                case ARCHIVO_OI_AR:
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.A1)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.A1);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.A1.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    break;
                case ARCHIVO_OI_AP:
                case ARCHIVO_OI_APR:
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.AP1)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.AP1);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.AP1.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    break;
                case ARCHIVO_OI_I:
                case ARCHIVO_OI_IR:
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.I1)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.I1);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.I1.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.I3_1)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.I3_1);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.I3_1.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.I3_2)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.I3_2);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.I3_2.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.I3_3)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.I3_3);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.I3_3.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.I4)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.I4);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.I4.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    break;
                case ARCHIVO_OI_IP:
                case ARCHIVO_OI_IPR:
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.IP1)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.IP1);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.IP1.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.IP3)) {
                        cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.IP3);
                        if (cuentaRegistros.compareTo(1) > 0) {
                            mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(
                                    tipoError.name(), TipoLineaTipoRegistroEnum.IP3.getDescripcionLinea(), cuentaRegistros.toString());
    
                            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.F1)) {
                cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.F1);
                if (cuentaRegistros.compareTo(1) > 0) {
                    mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(tipoError.name(),
                            TipoLineaTipoRegistroEnum.F1.getDescripcionLinea(), cuentaRegistros.toString());
    
                    resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                }
            }
            if(listaControlRegistros.containsKey(TipoLineaTipoRegistroEnum.F9)) {
                cuentaRegistros = listaControlRegistros.get(TipoLineaTipoRegistroEnum.F9);
                if (cuentaRegistros.compareTo(1) > 0) {
                    mensaje = MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS.getReadableMessage(tipoError.name(),
                            TipoLineaTipoRegistroEnum.F9.getDescripcionLinea(), cuentaRegistros.toString());
    
                    resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanilla, bloqueActual, mensaje, tipoError, null);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IArchivosPILAEjecucion#actualizarIndiceYEstadoBloque(com.asopagos.entidades.pila.procesamiento.IndicePlanilla,
     *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum, com.asopagos.pila.dto.RespuestaValidacionDTO, java.lang.Integer,
     *      com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum)
     */
    protected RespuestaValidacionDTO actualizarIndiceYEstadoBloque(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado,
            RespuestaValidacionDTO result, Integer bloque, AccionProcesoArchivoEnum accionPredeterminada) {
        String firmaMetodo = "ProcesadorBloque.actualizarIndiceYEstadoBloque(IndicePlanilla, EstadoProcesoArchivoEnum, Integer, AccionProcesoArchivoEnum)";
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
    
    
    /**
     * Método para obtener el contenido de un archivo para su verificación
     * 
     * @param idDocumento
     *        ID del documento en el ECM
     * @return byte[]
     *         Flujo de bytes con el contenido del archivo
     */
    protected byte[] obtenerContenidoArchivo(String idDocumento) {
        String firmaMetodo = "ProcesadorBloque.obtenerContenidoArchivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        byte[] result = null;

        InformacionArchivoDTO archivoDTOSalida = null;
        ObtenerArchivo obtenerArchivoService;
        obtenerArchivoService = new ObtenerArchivo(idDocumento);
        obtenerArchivoService.execute();
        archivoDTOSalida = obtenerArchivoService.getResult();

        if (archivoDTOSalida != null) {
            result = archivoDTOSalida.getDataFile();
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método para agregar los errores que se generan en cada bloque de validación en un único DTO
     * 
     * @param respuestaGeneral
     *        DTO en el que se van a acumular la totalidad de los errores
     * @param respuestaValidacion
     *        DTO con el resultado del bloque específico
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO general actualizado
     */
    protected RespuestaValidacionDTO addErrores(RespuestaValidacionDTO respuestaGeneral, RespuestaValidacionDTO respuestaValidacion) {
        String firmaMetodo = "ProcesadorBloque.addErrores(RespuestaValidacionDTO, RespuestaValidacionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // en primer lugar, si el DTO general es null se crea una nueva instancia
        if (respuestaGeneral == null) {
            respuestaGeneral = new RespuestaValidacionDTO();
        }

        // si la respuesta de la validación actual es diferente a null, se recorren sus errores para agregarlos al listado general
        if (respuestaValidacion != null) {
            respuestaGeneral.addListaErroresDetalladoValidadorDTO(respuestaValidacion.getErrorDetalladoValidadorDTO());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaGeneral;
    }
    
    
    /**
     * Método encargado de la actualización del índice de planilla y el estado por bloque del archivo OF
     * @param result
     *        DTO con el resultado de la validación
     * @param indicePlanillaOF
     *        Instancia del índice de planilla de Operador Financiero
     * @param estado
     *        Estado a actualizar
     * @param estadoB6
     *        Estado para aplicar a bloque 6 (opcional)
     * @param bloque
     *        Bloque de validación que se está actualizando
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado
     * 
     */
    protected RespuestaValidacionDTO actualizarIndiceYestadoBloqueOF(RespuestaValidacionDTO result, IndicePlanillaOF indicePlanillaOF,
            EstadoProcesoArchivoEnum estado, EstadoProcesoArchivoEnum estadoB6, Integer bloque) {

        String firmaMetodo = "ProcesadorBloque.actualizarIndiceYestadoBloqueOF(RespuestaValidacionDTO, IndicePlanillaOF, "
                + "EstadoProcesoArchivoEnum, EstadoProcesoArchivoEnum, Integer";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO resultTemp = result;
        BloqueValidacionEnum bloqueActual = resultTemp.getBloqueSiguente();

        try {
            // se actualiza el estado del índice
            indicePlanillaOF.setEstado(estado);
            persistencia.actualizarIndicePlanillasOF(indicePlanillaOF);

            // se registra el estado del bloque
            gestorEstados.registrarEstadoArchivoOF(indicePlanillaOF, indicePlanillaOF.getEstado(),
                    FuncionesValidador.determinarAccion(estado, indicePlanillaOF.getTipoArchivo()), "", bloque);

            // se registra el estado del B6 sí aplica
            if (estadoB6 != null) {
                gestorEstados.registrarEstadoArchivoOF(indicePlanillaOF, estadoB6,
                        FuncionesValidador.determinarAccion(estadoB6, indicePlanillaOF.getTipoArchivo()), "", 6);

                // se actualiza el estado del índice
                indicePlanillaOF.setEstado(estadoB6);
                persistencia.actualizarIndicePlanillasOF(indicePlanillaOF);
            }
        } catch (ErrorFuncionalValidacionException e) {
            // en este caso, se presenta un error por estado inválido al momento de actualizar el estado por bloque
            // se debe agregar el error a la respuesta y anular el índice

            indicePlanillaOF.setEstado(EstadoProcesoArchivoEnum.ANULADO);
            persistencia.actualizarIndicePlanillasOF(indicePlanillaOF);

            resultTemp = FuncionesValidador.agregarError(resultTemp, indicePlanillaOF, bloqueActual, e.getMessage(),
                    TipoErrorValidacionEnum.ERROR_TECNICO, null);

            // se actualiza el bloque para detener al orquestador
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }
        
        resultTemp.setBloqueSiguente(bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }
    
    /**
     * Método encargado de tomar el listado de planillas leídas en un archivo OF para consultar
     * los índices de planilla asociados que apliquen para su ejecución
     * @param listaPlanillas
     *        Lista de números de planilla leídos en el archivo OF
     * @param indicesOI 
     */
    protected List<IndicePlanilla> prepararEjecucionOIDerivada(Set<String> listaPlanillas) {
        String firmaMetodo = "ProcesadorBloque.prepararEjecucionOIDerivada(Set<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // se inicializan los listados de estados
        List<EstadoProcesoArchivoEnum> estadosOI = EstadoProcesoArchivoEnum.getEstadosReintentables();

        // se consultan los índices de planilla relacionados
        List<IndicePlanilla> indicesOI = persistencia.consultarIndicesOIPorNumeroPlanilla(listaPlanillas, estadosOI);
        
       // los índices que no estén marcados como "en lista", reciben la marca
        for (IndicePlanilla indicePlanilla : indicesOI) {
            //logger.info(firmaMetodo + "indicesOI b ->" + indicePlanilla.getEnLista());
            if (!indicePlanilla.getEnLista()) {
                indicePlanilla.setEnLista(true);
                persistencia.actualizarIndicePlanillas(indicePlanilla);
            }
        }

        indicesOI = limpiarIndicesRepetidos(indicesOI, true);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return indicesOI;
    }
    
    
    /**
     * Método encargado de eliminar las entradas de índice de planilla que se encuentren repetidos y anulados en un listado.
     * Se retorna el listado ordenado, de manera que primero van los maestros y luego los detalles. 
     * @param listaOriginal
     *        Listado de entradas de índice de planilla a depurar
     * @param excluirA
     *        Indicador para determinar que la limpieza de la lista de índices también quita los archivos de informacion de aportante
     * @return <b>List<IndicePlanilla></b>
     *         Listado depurado de índices de planilla
     */
    public static List<IndicePlanilla> limpiarIndicesRepetidos(List<IndicePlanilla> listaOriginal, Boolean excluirA) {
        String firmaMetodo = "ProcesadorBloque.limpiarIndicesRepetidos(List<IndicePlanilla>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Set<Long>          listaIds = new HashSet<>();

        List<IndicePlanilla> listaArchivosInformacion = new ArrayList<>();
        List<IndicePlanilla> listaArchivosDetalle     = new ArrayList<>();
        
        for (IndicePlanilla indicePlanilla : listaOriginal) {
            /*
             * los índices se agregan de nuevo a la lista cuando no se encuentran ya listados y cuando son tipo I-IP(R) con indicador
             * de exclusión de archivos A-AP(R) y no están anulados
             */
            if (!listaIds.contains(indicePlanilla.getId())
                    && (!excluirA || SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo()))
                    && !EstadoProcesoArchivoEnum.ANULADO.equals(indicePlanilla.getEstadoArchivo())) {
                
                //se organiza el listado con la intención de que primero se evaluen los archivos A - AP - AR - APR
                if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
                    listaArchivosInformacion.add(indicePlanilla);
                }
                if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
                    listaArchivosDetalle.add(indicePlanilla);
                }
                
                listaIds.add(indicePlanilla.getId());
            }
        }
        
        //Se agrupan los archivos
        listaArchivosInformacion.addAll(listaArchivosDetalle);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return  listaArchivosInformacion;
    }
}
