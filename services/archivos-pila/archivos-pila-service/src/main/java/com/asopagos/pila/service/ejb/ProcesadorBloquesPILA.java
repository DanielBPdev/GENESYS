package com.asopagos.pila.service.ejb;

import java.util.ArrayList;
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
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.dto.UbicacionCampoArchivoPilaDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2;
import com.asopagos.pila.validadores.IProcesadorBloque;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;

@Stateless
public class ProcesadorBloquesPILA extends ProcesadorBloque { 

    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private GestorStoredProceduresLocal gestorUsp;
    
    @Inject
    private IConsultaModeloDatosCore consultaDatosCore;
    
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;
    
    @Inject
    private IGestorPila2 gestorPila2;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorBloquesPILA.class);
    
    
    /**
     * Procesamiento de lista de archivos de una planilla
     * @param listaPlanillasOIRelacionadas
     * @param usuario
     * @param idProcesoAgrupador
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RespuestaServicioDTO> procesarPlanillasOI (List<IndicePlanilla> listaPlanillasOIRelacionadas, String usuario, Long idProcesoAgrupador){
        String firmaMetodo = "ProcesadorBloquesPILA.procesarPlanillasOI(List<IndicePlanilla>, String, Long)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<RespuestaServicioDTO> listaRespuestasServicio = new ArrayList<>();
        /*
         * se recorre la lista de índices, esta puede variar su tamaño con base en el intercambio de control
         * en el orden de la validación (reanudación de proceso de validación
         */
        for (int i = 0; i < listaPlanillasOIRelacionadas.size(); i++) {
            IndicePlanilla indice = listaPlanillasOIRelacionadas.get(i);

            try {
                
                RespuestaServicioDTO respuestaServicio = ejecutarValidacionesOI(indice, usuario, listaPlanillasOIRelacionadas);
                
                if (respuestaServicio != null) {
                    listaRespuestasServicio.add(respuestaServicio);
                }
                
            } catch (Exception e) {
                LogErrorPilaM1ModeloDTO log = FuncionesValidador.prepararLogError(indice, e);
                persistencia.registrarLogError(log);
                logger.error(firmaMetodo, e);
            }

            // se indica que el archivo ha sido revisado en este ciclo y "sale" de la lista de procesamiento
            if (indice != null) {
                IndicePlanilla indice2 = persistencia.consultarPlanillaOIPorId(indice.getId()); 
                indice2.setEnLista(false);
                persistencia.actualizarIndicePlanillas(indice2);
                
                indice.setEnLista(false);
            }
        }

        // una vez finaliza el recorrido de validación de bloque 1 a 6, se solicita la ejecución del USP para bloques 7 a 10
        logger.info("Ejecucion pila 2 con planillas:" );
        gestorPila2.iniciarPila2(listaPlanillasOIRelacionadas, idProcesoAgrupador);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaRespuestasServicio;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesarPlanillasOISinValidacion (List<IndicePlanilla> listaPlanillasOIRelacionadas, Long idProcesoAgrupador){
        String firmaMetodo = "ProcesadorBloquesPILA.procesarPlanillasOISinValidacion(List<IndicePlanilla>, String, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // una vez finaliza el recorrido de validación de bloque 1 a 6, se solicita la ejecución del USP para bloques 7 a 10
        logger.info("Ejecucion pila 2 con planillas:" );
        gestorPila2.iniciarPila2(listaPlanillasOIRelacionadas, idProcesoAgrupador);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método para inciar la ejecución de las validaciones de Operador Financiero
     * 
     * @param indicePlanilla
     *        Entrada de Indice de planilla de Operador Financiero a validar
     * @param usuario
     *        Usuario que ejecuta la validación
     * @param bloqueActual 
     * @throws FileProcessingException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaServicioDTO procesarPlanillasOF (IndicePlanillaOF indicePlanilla, String usuario) {
        String firmaMetodo = "ProcesadorBloquesPILA.procesarPlanillasOF(IndicePlanillaOF, String)";
        System.out.print(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaServicioDTO     respuestaServicio = new RespuestaServicioDTO();
        RespuestaValidacionDTO respuestaValidacion = new RespuestaValidacionDTO();
        
        BloqueValidacionEnum bloqueProcesar = BloqueValidacionEnum.BLOQUE_1_OF;
        
        if (indicePlanilla != null) {
           
            // se diligencia la respuesta del servicio
            respuestaServicio.setFileName(indicePlanilla.getNombreArchivo());
            respuestaServicio.setIdIndicePlanilla(indicePlanilla.getId());
            
            // en esta variable, se construye el ID del documento para la consulta del flujo de datos
            String idDocumentoParam = indicePlanilla.getIdDocumento();
            // sí se cuenta con una versión, se le agrega separandola con "_"
            if (indicePlanilla.getVersionDocumento() != null) {
                idDocumentoParam += IProcesadorBloque.VERSION_CLIENT_SEPARATOR + indicePlanilla.getVersionDocumento();
            }

            respuestaServicio.setIdDocumento(idDocumentoParam);

            // dependiendo del tipo de índice, se determina el bloque en el que inicia la ejecución y la secuencia de la misma

            // se determina el bloque inicial de ejecución a partir del estado del índice
            EstadoArchivoPorBloqueOF estadoBloque = null;
 
            try {
                estadoBloque = persistenciaEstados.consultarEstadoOF(indicePlanilla);
            } catch (ErrorFuncionalValidacionException e) {
                // no se encuentran estados previos, se debe generar respuesta y pasar el error
                
                respuestaValidacion = FuncionesValidador.agregarError(respuestaValidacion, indicePlanilla, bloqueProcesar, e.getMessage(),
                        TipoErrorValidacionEnum.ERROR_TECNICO, null);
            }

            if (estadoBloque != null) {
                bloqueProcesar = getBloqueInicial(estadoBloque);
            }

            Map<String, Object> parametros = new HashMap<>(); 
            parametros.put(IProcesadorBloque.INDICE_PLANILLA_KEY, indicePlanilla);
            parametros.put(IProcesadorBloque.USUARIO_KEY, usuario);
            parametros.put(IProcesadorBloque.ID_DOCUMENTO_KEY, idDocumentoParam);
            parametros.put(IProcesadorBloque.RESPUESTA_GENERAL_KEY, respuestaValidacion);
            
         System.out.print("**__**procesarPlanillasOF -> indicePlanilla: "+indicePlanilla+" bloqueProcesar"+bloqueProcesar+"idDocumentoParam: "+idDocumentoParam);
            //Inicia el procesamiento desde el bloque 1 (o el bloque donde haya quedado de acuerdo al estadoBloque) hasta el bloque 6
            procesarBloque(bloqueProcesar, parametros, null);
        System.out.print("**__**procesarPlanillasOF finalizarCicloValidacionArchivoOI");
            // se finaliza la validación del archivo OF
            respuestaServicio = finalizarCicloValidacionArchivoOF(respuestaValidacion, respuestaServicio, indicePlanilla);
         
            if (respuestaServicio != null) {
                respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
            }
            
        } else {
            respuestaServicio.setEstado(EstadoProcesoArchivoEnum.ANULADO);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaServicio;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaValidacionDTO ejecutarBloqueCeroPorPerfil(ArchivoPilaDTO archivoPila, RespuestaServicioDTO respuestaServicio, Boolean validarBloque0) {
        String firmaMetodo = "ProcesadorBloquesPILA.ejecutarBloqueCeroPorPerfil(ArchivoPilaDTO, RespuestaServicioDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO respuestaValidacion = new RespuestaValidacionDTO();
        EstadoProcesoArchivoEnum estado = null;
        Long idPlanilla = null;

        Map<String, Object> parametros = new HashMap<>(); 
        parametros.put(IProcesadorBloque.ARCHIVO_PILA_KEY, archivoPila);
        parametros.put(IProcesadorBloque.RESPUESTA_GENERAL_KEY, respuestaValidacion);
        
        if (PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(archivoPila.getPerfilArchivo())) {
            //procesa el bloque cero de OF
            procesarBloque(BloqueValidacionEnum.BLOQUE_0_OF, parametros, null);
            respuestaValidacion = (RespuestaValidacionDTO) parametros.get(IProcesadorBloque.RESPUESTA_GENERAL_KEY);
            
            if (respuestaValidacion.getIndicePlanillaOF() != null) {
                estado     = respuestaValidacion.getIndicePlanillaOF().getEstado();
                idPlanilla = respuestaValidacion.getIndicePlanillaOF().getId();
            }
        }
        else {
            parametros.put(IProcesadorBloque.VALIDA_BLOQUE_CERO_KEY, validarBloque0);
            //procesa el bloque 0 de OI
            procesarBloque(BloqueValidacionEnum.BLOQUE_0_OI, parametros, null);
            respuestaValidacion = (RespuestaValidacionDTO) parametros.get(IProcesadorBloque.RESPUESTA_GENERAL_KEY);
            
            if (respuestaValidacion.getIndicePlanilla() != null) {
                estado = respuestaValidacion.getIndicePlanilla().getEstadoArchivo();
                idPlanilla = respuestaValidacion.getIndicePlanilla().getId();
            }
            else {
                estado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS;
            }
        }

        if (estado != null) {
            respuestaServicio.setEstado(estado);
            respuestaServicio.setIdIndicePlanilla(idPlanilla);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaValidacion;
    }
    
    
    /**
     * Método para inciar la ejecución de las validaciones de Operador de Información
     * 
     * @param indicePlanilla
     *        Entrada de Indice de planilla de Operador de Información a validar
     * @param usuario
     *        Usuario que ejecuta la validación
     * @param indicesOI 
     * @param bloqueActual 
     * @return RespuestaServicioDTO
     *         DTO con la respuesta de la ejecución
     */
    private RespuestaServicioDTO ejecutarValidacionesOI(IndicePlanilla indicePlanilla, String usuario, List<IndicePlanilla> indicesOI) {
        
        String firmaMetodo = "ProcesadorBloquesPILA.ejecutarValidacionesOI(IndicePlanilla, String, List<IndicePlanilla>)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        RespuestaServicioDTO respuestaServicio = new RespuestaServicioDTO();
        
        BloqueValidacionEnum bloqueProcesar = BloqueValidacionEnum.BLOQUE_1_OI;
        
        if (indicePlanilla != null) {
        
            RespuestaValidacionDTO respuestaValidacion = new RespuestaValidacionDTO();
    
            // se diligencia la respuesta del servicio
            respuestaServicio.setFileName(indicePlanilla.getNombreArchivo());
            respuestaServicio.setIdIndicePlanilla(indicePlanilla.getId());
    
            // en esta variable, se construye el ID del documento para la consulta del flujo de datos
            String idDocumentoParam = indicePlanilla.getIdDocumento();
            // sí se cuenta con una versión, se le agrega separandola con "_"
            if (indicePlanilla.getVersionDocumento() != null) {
                idDocumentoParam += IProcesadorBloque.VERSION_CLIENT_SEPARATOR + indicePlanilla.getVersionDocumento();
            }
    
            respuestaServicio.setIdDocumento(idDocumentoParam);
    
            // se determina el bloque inicial de ejecución a partir del estado del índice
            EstadoArchivoPorBloque estadoBloque = null;
            try {
                estadoBloque = persistenciaEstados.consultarEstadoEspecificoOI(indicePlanilla);
            } catch (ErrorFuncionalValidacionException e) {
                // no se encuentran estados previos, se debe generar respuesta y pasar el error
                respuestaValidacion = FuncionesValidador.agregarError(respuestaValidacion, indicePlanilla, bloqueProcesar, e.getMessage(), TipoErrorValidacionEnum.ERROR_TECNICO, null);
            }
    
            if (estadoBloque != null) {
                bloqueProcesar = getBloqueInicial(estadoBloque);
            }
            // se pasa el archivo por los diferentes bloques de validación
            
            Map<String, Object> parametros = new HashMap<>(); 
            parametros.put(IProcesadorBloque.INDICE_PLANILLA_KEY, indicePlanilla);
            parametros.put(IProcesadorBloque.USUARIO_KEY, usuario);
            parametros.put(IProcesadorBloque.ID_DOCUMENTO_KEY, idDocumentoParam);
            parametros.put(IProcesadorBloque.RESPUESTA_GENERAL_KEY, respuestaValidacion);
            parametros.put(IProcesadorBloque.LISTA_INDICES_OI_KEY, indicesOI);
    logger.info("**__**ejecutarValidacionesOI -> indicePlanilla: "+indicePlanilla+" bloqueProcesar"+bloqueProcesar+"idDocumentoParam: "+idDocumentoParam);
            //Inicia el procesamiento desde el bloque 1 (o el bloque donde haya quedado de acuerdo al estadoBloque) hasta el bloque 6
            procesarBloque(bloqueProcesar, parametros, null);
        logger.info("**__**ejecutarValidacionesOI finalizarCicloValidacionArchivoOI");
            // se inician las tareas de finalización de ciclo de validación para el archivo
            respuestaServicio = finalizarCicloValidacionArchivoOI(respuestaValidacion, indicePlanilla, respuestaServicio);
                
            if (respuestaServicio != null) {
                respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
            }
            
        } else {
            respuestaServicio.setEstado(EstadoProcesoArchivoEnum.ANULADO);
            respuestaServicio = FuncionesValidador.prepararMensajeRespuesta(respuestaServicio);
            return respuestaServicio;
        }  
            
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.warn("respuesta de " + firmaMetodo);
        logger.warn(respuestaServicio);
        return respuestaServicio;
    }
    
    /**
     * Método para realizar las tareas de finalización de ciclo de validación de un archivo OI
     * @param respuestaGeneral
     *        DTO con el resultado general de validaciones para el archivo
     * @param indicePlanilla
     *        Índice de planilla validado
     * @param respuestaServicio
     *        DTO con la respuesta del servicio
     * @return <b>RespuestaServicioDTO</b>
     *         DTO actualizado de la respuesta del servicio
     */
    private RespuestaServicioDTO finalizarCicloValidacionArchivoOI(RespuestaValidacionDTO respuestaGeneral, IndicePlanilla indicePlanilla,
            RespuestaServicioDTO respuestaServicio) {
        //String firmaMetodo = "ProcesadorBloquesPILA.finalizarCicloValidacionArchivoOI(RespuestaValidacionDTO, IndicePlanilla, RespuestaServicioDTO)";
        //logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (respuestaGeneral != null) {
            respuestaGeneral.setIndicePlanilla(indicePlanilla);

            // se ordenan los errores de acuerdo a la línea en la que se presentan
            respuestaGeneral.ordenarListaErrores();

            // se agregan los detalles finales a los DTO de error y se solicita su registro
            List<ErrorValidacionLog> errores = new ArrayList<>();
            List<Long> ids = gestorUsp.obtenerValoresSecuencia(respuestaGeneral.getErrorDetalladoValidadorDTO().size(), IProcesadorBloque.SECUENCIA_LOG_ERRORES);
            Set<String> camposPorUbicar = new HashSet<>();

            // se agregan los detalles finales a los DTO de error y se solicita su registro
            for (ErrorDetalladoValidadorDTO error : respuestaGeneral.getErrorDetalladoValidadorDTO()) {
                // si el error ya contiene el ID de planilla se conserva
                if (error.getIdIndicePlanilla() == null) {
                    error.setIdIndicePlanilla(indicePlanilla.getId());
                }

                // sí no se cuenta con el tipo de archivo, se agrega a partir del índice
                if (error.getTipoArchivo() == null) {
                    error.setTipoArchivo(indicePlanilla.getTipoArchivo());
                }

                ErrorValidacionLog errorLog = null;

                try {
                    errorLog = FuncionesValidador.generarEntityError(error, ids.get(0));
                    camposPorUbicar.add(error.getIdCampoError());
                    ids.remove(0);

                    errores.add(errorLog);
                } catch (ErrorFuncionalValidacionException e) {
                    //logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + e.getMessage());
                    return null;
                }
            }

            if (!errores.isEmpty()) {
                // se consultan las etiquetas de los campos con error para agregar ubicaciones
                List<UbicacionCampoArchivoPilaDTO> ubicaciones = consultaDatosCore.consultarUbicaciones(camposPorUbicar);
                FuncionesValidador.asignarUbicacionCampo(errores, ubicaciones);

                persistencia.registrarError(errores);
            }
        }

        respuestaServicio.setEstado(indicePlanilla.getEstadoArchivo());

        //logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaServicio;
    }
    
    /**
     * Función para obtener el bloque de validación para ejecución del estado de planilla
     * @param estado
     *        Estado por bloque del índice del archivo (OI - OF)
     * @return
     *         Bloque de validación para iniciar la ejecución
     */
    private BloqueValidacionEnum getBloqueInicial(Object estado) {
        //String firmaMetodo = "ProcesadorBloquesPILA.getBloqueInicial(Object)";
        //logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BloqueValidacionEnum bloqueInicialProcesamiento = null;

        EstadoArchivoPorBloque estadoOI   = null;
        EstadoArchivoPorBloqueOF estadoOF = null;

        // se determina el tipo de estado recibido
        if (estado != null && estado instanceof EstadoArchivoPorBloque) {
            estadoOI = (EstadoArchivoPorBloque) estado;
        }
        else if (estado != null && estado instanceof EstadoArchivoPorBloqueOF) {
            estadoOF = (EstadoArchivoPorBloqueOF) estado;
        }

        // se compara el estado para OI
        if (estadoOI != null) {
            int bloqueFinal = numeroBloqueFinal(estadoOI);
            bloqueInicialProcesamiento = evaluarEstadosOI(estadoOI, bloqueFinal);
        }

        // se evalua el estado OF
        if (estadoOF != null) {
            int bloqueFinal = numeroBloqueFinal(estadoOF);
            bloqueInicialProcesamiento = evaluarEstadosOF(estadoOF, bloqueFinal);
        }
        
        //logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return bloqueInicialProcesamiento;
    }
    
    /**
     * Método para establecer cuál fue el último bloque que se ejecuto para un archivo (OI u OF)
     * @param estado
     *        Entity de estados por bloque de archivo PILA para validar
     * @return <b>Integer</b>
     *         Número del último bloque validado
     */
    private Integer numeroBloqueFinal(Object estado) {
        //String firmaMetodo = "ProcesadorBloquesPILA.numeroBloqueFinal(Object)";
        //logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<EstadoProcesoArchivoEnum> estadosIncompletos = EstadoProcesoArchivoEnum.getEstadosReintentables();

        Integer result = -1;

        if (estado instanceof EstadoArchivoPorBloque) {
            EstadoArchivoPorBloque estadoOI = (EstadoArchivoPorBloque) estado;

            if (estadoOI.getEstadoBloque0() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque0())) {
                result = 0;
            }
            else if (estadoOI.getEstadoBloque1() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque1())) {
                result = 1;
            }
            else if (estadoOI.getEstadoBloque2() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque2())) {
                result = 2;
            }
            else if (estadoOI.getEstadoBloque3() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque3())) {
                result = 3;
            }
            else if (estadoOI.getEstadoBloque4() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque4())) {
                result = 4;
            }
            else if (estadoOI.getEstadoBloque5() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque5())) {
                result = 5;
            }
            else if (EstadoProcesoArchivoEnum.NO_REQUIERE_CONCILIACION.equals(estadoOI.getEstadoBloque6())
                    || estadoOI.getEstadoBloque6() == null || estadosIncompletos.contains(estadoOI.getEstadoBloque6())) {
                result = 6;
            }
        }
        else if (estado instanceof EstadoArchivoPorBloqueOF) {
            EstadoArchivoPorBloqueOF estadoOF = (EstadoArchivoPorBloqueOF) estado;

            if (estadoOF.getEstadoBloque0() == null) {
                result = 0;
            }
            else if (estadoOF.getEstadoBloque1() == null) {
                result = 1;
            }
        }

        //logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método para evaluar el estado de un archivo OI en un bloque específico para determinar sí es válido para iniciar desde ese punto
     * @param estadoOI
     *        Entity de estados por bloque de archivo OI para validar
     * @param control
     *        Número del bloque a validar
     * @return <b>BloqueValidacionEnum</b>
     *         Bloque inicial de validación
     */
    private BloqueValidacionEnum evaluarEstadosOI(EstadoArchivoPorBloque estadoOI, Integer control) {
        //String firmaMetodo = "ProcesadorBloquesPILA.evaluarEstadosOI(EstadoArchivoPorBloque, Integer)";
        //logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BloqueValidacionEnum result = null;

        switch (control) {
            case 0:
                if (AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_0.equals(estadoOI.getAccionBloque0())) {

                    result = BloqueValidacionEnum.BLOQUE_0_OI;
                }
                break;
            case 1:
                if (AccionProcesoArchivoEnum.EN_ESPERA.equals(estadoOI.getAccionBloque1()) || (estadoOI.getEstadoBloque1() == null
                        && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_1.equals(estadoOI.getAccionBloque0()))) {

                    result = BloqueValidacionEnum.BLOQUE_1_OI;
                }
                break;
            case 2:
                if (estadoOI.getAccionBloque2() == null && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_2.equals(estadoOI.getAccionBloque1())) {

                    result = BloqueValidacionEnum.BLOQUE_2_OI;
                }
                break;
            case 3:
                if (AccionProcesoArchivoEnum.EN_ESPERA.equals(estadoOI.getAccionBloque3()) || (estadoOI.getAccionBloque3() == null
                        && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_3.equals(estadoOI.getAccionBloque2()))) {

                    result = BloqueValidacionEnum.BLOQUE_3_OI;
                }
                break;
            case 4:
                if (estadoOI.getAccionBloque4() == null && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_4.equals(estadoOI.getAccionBloque3())) {

                    result = BloqueValidacionEnum.BLOQUE_4_OI;
                }
                break;
            case 5:
                // los archivos de información de aportante sólo llegan a bloque 4
                if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(estadoOI.getTipoArchivo().getSubtipo())
                        && AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS.equals(estadoOI.getAccionBloque4())) {

                    result = BloqueValidacionEnum.FINALIZAR_CICLO;
                }
                else if (estadoOI.getAccionBloque5() == null
                        && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_5.equals(estadoOI.getAccionBloque4())) {

                    result = BloqueValidacionEnum.BLOQUE_5_OI;
                }
                break;
            case 6:
                if (AccionProcesoArchivoEnum.EN_ESPERA.equals(estadoOI.getAccionBloque6()) || (estadoOI.getAccionBloque6() == null
                        && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_6.equals(estadoOI.getAccionBloque5())
                        || EstadoProcesoArchivoEnum.GESTIONAR_DIFERENCIA_EN_CONCILIACION.equals(estadoOI.getEstadoBloque6()))) {

                    result = BloqueValidacionEnum.BLOQUE_6_OI;
                }
                break;
            default:
                result = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        //logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método para evaluar el estado de un archivo OF en un bloque específico para determinar sí es válido para iniciar desde ese punto
     * @param estadoOF
     *        Entity de estados por bloque de archivo OF para validar
     * @param control
     *        Número del bloque a validar
     * @return <b>BloqueValidacionEnum</b>
     *         Bloque inicial de validación
     */
    private BloqueValidacionEnum evaluarEstadosOF(EstadoArchivoPorBloqueOF estadoOF, Integer control) {
        //String firmaMetodo = "ProcesadorBloquesPILA.evaluarEstadosOF(EstadoArchivoPorBloqueOF, Integer)";
        //logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se recorre cada estado, con el fin de establecer cuál es el bloque inicial de ejecución
        BloqueValidacionEnum result = null;

        if (control == 1) {
            if (estadoOF.getAccionBloque1() == null && AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_1.equals(estadoOF.getAccionBloque0())) {

                result = BloqueValidacionEnum.BLOQUE_1_OF;
            }
        }
        else {
            result = BloqueValidacionEnum.FINALIZAR_CICLO;
        }

        //logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método encargado de la finalización del ciclo de validación para un archivo OF
     * @param respuestaGeneral
     *        DTO con el resultado general de validaciones para el archivo
     * @param respuestaServicio
     *        DTO con la respuesta del servicio
     * @param indicePlanilla
     *        Índice de planilla validado
     * @return <b>RespuestaServicioDTO</b>
     *         DTO actualizado de la respuesta del servicio
     */
    private RespuestaServicioDTO finalizarCicloValidacionArchivoOF(RespuestaValidacionDTO respuestaGeneral,
            RespuestaServicioDTO respuestaServicio, IndicePlanillaOF indicePlanilla) {
        //String firmaMetodo = "ProcesadorBloquesPILA.finalizarCicloValidacionArchivoOF(RespuestaValidacionDTO, RespuestaServicioDTO, IndicePlanillaOF)";
        //logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanilla> indicesOI = null;
        if (respuestaGeneral != null) {
            respuestaGeneral.setIndicePlanillaOF(indicePlanilla);
            indicesOI = respuestaGeneral.getIndicesOIenOF();

            // se ordenan los errores de acuerdo a la línea en la que se presentan
            respuestaGeneral.ordenarListaErrores();

            // se agregan los detalles finales a los DTO de error y se solicita su registro
            List<ErrorValidacionLog> errores = new ArrayList<>();
            List<Long> ids = gestorUsp.obtenerValoresSecuencia(respuestaGeneral.getErrorDetalladoValidadorDTO().size(), IProcesadorBloque.SECUENCIA_LOG_ERRORES);
            Set<String> camposPorUbicar = new HashSet<>();

            // se agregan los detalles finales a los DTO de error y se solicita su registro
            for (ErrorDetalladoValidadorDTO error : respuestaGeneral.getErrorDetalladoValidadorDTO()) {
                error.setIdIndicePlanilla(indicePlanilla.getId());

                ErrorValidacionLog errorLog = null;
                if (BloqueValidacionEnum.FINALIZAR_CICLO.equals(error.getBloque())) {
                    error.setBloque(BloqueValidacionEnum.BLOQUE_1_OF);
                }

                try {
                    errorLog = FuncionesValidador.generarEntityError(error, ids.get(0));
                    camposPorUbicar.add(error.getIdCampoError());
                    ids.remove(0);

                    errores.add(errorLog);
                } catch (ErrorFuncionalValidacionException e) {
                    //logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + e.getMessage());
                    return null;
                }
            }

            if (!errores.isEmpty()) {
                // se consultan las etiquetas de los campos con error para agregar ubicaciones
                List<UbicacionCampoArchivoPilaDTO> ubicaciones = consultaDatosCore.consultarUbicaciones(camposPorUbicar);
                FuncionesValidador.asignarUbicacionCampo(errores, ubicaciones);

                persistencia.registrarError(errores);
            }
        }

        if (respuestaServicio != null) {
            respuestaServicio.setEstado(indicePlanilla.getEstado());
            respuestaServicio.setIndicesOIenOF(indicesOI);
        }

        //logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaServicio;
    }
    
}
