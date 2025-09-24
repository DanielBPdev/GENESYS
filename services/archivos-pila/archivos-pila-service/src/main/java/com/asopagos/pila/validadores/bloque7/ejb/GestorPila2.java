package com.asopagos.pila.validadores.bloque7.ejb;

import com.asopagos.aportes.composite.clients.ProcesarAportesNovedadesByIdPlanilla;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.LinkedList;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.annotation.Resource;


import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.MotivoProcesoPilaManualEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.clients.ProcesarSeleccionOI;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que implementa el servicio EJB para el llamado a USP de ejecución de validaciones de PILA 2<br/>
 * <b>Módulo:</b> Asopagos - HU-211-395, HU-211-396, HU-211-480, HU-211-397, HU-211-398 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class GestorPila2 implements IGestorPila2, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(GestorPila2.class);

    /**
     * Referencia al EJB de persistencia de datos de validador
     */
    @Inject
    private IPersistenciaDatosValidadores persistencia;

    /**
     * Referencia al EJB de gestión de estados de planillas
     */
    @Inject
    private IGestorEstadosValidacion gestorEstados;

    /**
     * Referencia al EJB de gestion de procedimientos almacenados
     */
    @Inject
    private GestorStoredProceduresLocal gestorUsp;


    @Resource
    private ManagedExecutorService managedExecutorService;
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2#iniciarPila2(java.util.List,
	 *      java.lang.Long)
	 */
	@Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void iniciarPila2(List<IndicePlanilla> indicesPlanilla, Long idProcesoAgrupador) {
        String firmaMetodo = "GestorPila2.iniciarPila2(List<IndicePlanilla>) con tareas paralelas";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //IndicePlanilla indice2;
        Set<Long> idsParaPila2 = new HashSet<>();
        for (IndicePlanilla indicePlanilla : indicesPlanilla) {
            idsParaPila2.addAll(evaluarInicioPila2(indicePlanilla));
        }
 
        // se agregan los datos para el paquete de ejecución
        if(!idsParaPila2.isEmpty()){
	        gestorUsp.almacenarPaquetePlanillas(new ArrayList<>(idsParaPila2), idProcesoAgrupador);
            List<Callable<Void>> tareasParalelas = new LinkedList<>();
	        // Se solicita la ejecución de PILA 2
            logger.info(firmaMetodo + "llegan al metodo  iniciarPila2 cantidad: " +indicesPlanilla.size());
	        logger.info(firmaMetodo + "evaluarInicioPila2 idsParaPila2 cantidad:: " +idsParaPila2.size());
	        for(Long indicePlanilla : idsParaPila2) {
	        	//if(esAutomatica(indicePlanilla)) { //comentado porque el objeto execute pila 2 contempla esa logica
						  //   procesarAportesNovedades(indicePlanilla);
                        Callable<Void> parallelTask = () -> {
                            try {
                                logger.info(firmaMetodo + " iniciarPila2 paso 2: " +indicePlanilla  );
                                gestorUsp.ejecutarProcesamientoPila2(indicePlanilla, FasePila2Enum.SIN_PARAMETRO, false, null, 0L);
                                IndicePlanilla indice2 = persistencia.consultarPlanillaOIPorId(indicePlanilla);
                                logger.info(firmaMetodo + " iniciarPila2 paso 3: " +indicePlanilla + "indice2.getEstadoArchivo: "+indice2.getEstadoArchivo() );
                                if (EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES.equals(indice2.getEstadoArchivo())
                                        || EstadoProcesoArchivoEnum.PROCESADO_SIN_NOVEDADES.equals(indice2.getEstadoArchivo())) {
                                    logger.info("procesarAportesNovedades(indicePlanilla): " + indicePlanilla);
                                ProcesarAportesNovedadesByIdPlanilla servicio;
                                servicio = new ProcesarAportesNovedadesByIdPlanilla(indicePlanilla);
                                servicio.execute();
                                        }
                            } catch (Exception e) {
                                logger.error(ConstantesComunes.FIN_LOGGER_ERROR+ "Planillaproceso: "+ indicePlanilla + firmaMetodo + e.getMessage());
                            }
                            return null;
                        };
                        tareasParalelas.add(parallelTask);
					}
               
	        	//}
            try {
                managedExecutorService.invokeAll(tareasParalelas);
            } catch (InterruptedException e) {
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
                e.printStackTrace();
            }
	        //gestorUsp.ejecutarProcesamientoPila2(0L, FasePila2Enum.SIN_PARAMETRO, false, null, idProcesoAgrupador);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
	@Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void iniciarPila2SinValidaciones(List<Long> indicesPlanilla) {
        String firmaMetodo = "GestorPila2.iniciarPila2SinValidaciones(List<IndicePlanilla>) con tareas paralelas";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        // se agregan los datos para el paquete de ejecución

        if(!indicesPlanilla.isEmpty()){
            List<Callable<Void>> tareasParalelas = new LinkedList<>();
	        // Se solicita la ejecución de PILA 2
            logger.info(firmaMetodo + "llegan al metodo  iniciarPila2SinValidaciones cantidad: " +indicesPlanilla.size());
	        for(Long indicePlanilla : indicesPlanilla) {
	        	//if(esAutomatica(indicePlanilla)) { //comentado porque el objeto execute pila 2 contempla esa logica
						  //   procesarAportesNovedades(indicePlanilla);
                Callable<Void> parallelTask = () -> {
                try {
                    logger.info(firmaMetodo + " iniciarPila2SinValidaciones paso 2: " +indicePlanilla  );
                    gestorUsp.ejecutarProcesamientoPila2(indicePlanilla, FasePila2Enum.SIN_PARAMETRO, false, null, 0L);
                    IndicePlanilla indice2;
                    indice2 = persistencia.consultarPlanillaOIPorId(indicePlanilla);
                    logger.info(firmaMetodo + " iniciarPila2SinValidaciones paso 3: " +indicePlanilla + "indice2.getEstadoArchivo: "+indice2.getEstadoArchivo() );
                    if (EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES.equals(indice2.getEstadoArchivo())
                    || EstadoProcesoArchivoEnum.PROCESADO_SIN_NOVEDADES.equals(indice2.getEstadoArchivo())) {
                    logger.info("INICIA ProcesarAportesNovedadesByIdPlanilla (indicePlanilla): " + indicePlanilla);
                        ProcesarAportesNovedadesByIdPlanilla servicio;
                        servicio = new ProcesarAportesNovedadesByIdPlanilla(indicePlanilla);
                        servicio.execute();
                    }   
                    } catch (Exception e) {
                        logger.error(ConstantesComunes.FIN_LOGGER_ERROR+ "Planillaproceso semana 4: "+ indicePlanilla + firmaMetodo + e.getMessage());
                    }
                    return null;
                };
                tareasParalelas.add(parallelTask);
            }
               
	        	//}
            try {
                managedExecutorService.invokeAll(tareasParalelas);
            } catch (InterruptedException e) {
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
                e.printStackTrace();
            }
	        //gestorUsp.ejecutarProcesamientoPila2(0L, FasePila2Enum.SIN_PARAMETRO, false, null, idProcesoAgrupador);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void procesarAportesNovedades(Long indicePlanilla){
        String firmaMetodo = "GestorPila2.procesarAportesNovedades(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            ProcesarAportesNovedadesByIdPlanilla servicio;
            servicio = new ProcesarAportesNovedadesByIdPlanilla(indicePlanilla);
            servicio.execute();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR+ "Planillaproceso: "+ indicePlanilla + firmaMetodo + e.getMessage());
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * @param indicePlanilla
     * 
     */
    private List<Long> evaluarInicioPila2(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "GestorPila2.evaluarInicioPila2(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Set<Long> idsParaPila2 = new HashSet<>();
        
        EstadoProcesoArchivoEnum estado = indicePlanilla.getEstadoArchivo();

        Boolean procesar = true;
        Boolean habilitarManual = true;

        // esta evaluación sólo se lleva a cabo para archivos de detalle de aporte que hayan superado el B6
        if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())
                && (EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO.equals(estado)
                        || EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO.equals(estado))) {

            // en primer lugar se verifica el tipo de archivo para tratar con reprocesos
            if (indicePlanilla.getTipoArchivo().isReproceso()) {
                procesar = verificarReprocesos(indicePlanilla);
            }

            // luego se verifica el tipo de planilla, sí es que aún no se marca el archivo como proceso manual
            if (procesar) {
                Boolean[] resultadoVerAdiCorrec = verificarAdicionCorreccion(indicePlanilla);
                procesar = resultadoVerAdiCorrec[0];
                habilitarManual = resultadoVerAdiCorrec[1];
            }

            if (!procesar) {
                // se actualiza la acción para el archivo con el fin de marcar un procesamiento asistido

                try {
                    // se actualiza la habilitación para proceso manual
                    indicePlanilla.setHabilitadoProcesoManual(habilitarManual);
                    persistencia.actualizarIndicePlanillas(indicePlanilla);

                    gestorEstados.actualizarAccionManual(indicePlanilla);
                } catch (ErrorFuncionalValidacionException e) {
                    // fallo en la actualización de la acción del estado, se lanza un error técnico
                    logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + e.getMessage());

                    throw new TechnicalException(e);
                }
            }
            
            idsParaPila2.add(indicePlanilla.getId());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return new ArrayList<>(idsParaPila2);
    }

    /**
     * Método que verifica primero la carga de archivo original u otros reprocesos
     * @param indicePlanilla
     *        Índice de planilla procesado
     * @return <b>Boolean</b>
     *         Indica que se continue con proceso automático o no
     */
    private Boolean verificarReprocesos(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "GestorPila2.verificarReprocesos(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean procesar = true;

        List<EstadoArchivoPorBloque> indicesAnteriores = persistencia
                .consultarEntradasAnterioresConEstado(indicePlanilla.getIdPlanilla(), indicePlanilla.getCodigoOperadorInformacion());

        if (!indicesAnteriores.isEmpty()) {
            /*
             * sí se presentan múltiples resultados, se revisan para establecer sí aún sigue siendo válido procesar la
             * planilla automáticamente
             * se consulta el estado del B6, sí está aprobado no se debe ejecutar
             * automáticamente
             */
            for (EstadoArchivoPorBloque indiceAnterior : indicesAnteriores) {
                if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indiceAnterior.getTipoArchivo().getSubtipo())
                        && (indiceAnterior.getIndicePlanilla().getId().compareTo(indicePlanilla.getId()) != 0)
                        && (EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO.equals(indiceAnterior.getEstadoBloque6())
                                || EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO.equals(indiceAnterior.getEstadoBloque6()))) {
                    procesar = false;
                    indicePlanilla.setMotivoProcesoManual(MotivoProcesoPilaManualEnum.REPROCESO_PREVIO);
                }
            }
        }
    

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return procesar;
    }

    /**
     * Método encargado de determinar sí un archivo PILA se procesa manualmente en PILA 2 por motivo de ser
     * una planilla de adición o corrección
     * @param indicePlanilla
     *        Índice de planilla procesado
     * @return <b>Boolean[]</b>
     *         Indica que se continue con proceso automático o no y sí ese proceso automático está habilitado de inmediato
     */
    private Boolean[] verificarAdicionCorreccion(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "GestorPila2.verificarAdicionCorreccion(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean[] resultado = { true, true };
        Boolean procesar = true;

        PilaArchivoIRegistro1 registro1 = null;
        switch (indicePlanilla.getTipoArchivo()) {
            case ARCHIVO_OI_I:
            case ARCHIVO_OI_IR:
                registro1 = (PilaArchivoIRegistro1) persistencia.consultarRegistro1ArchivoI(indicePlanilla);

                if(registro1 != null){
                    TipoPlanillaEnum tipoPlanillaRegistro1 = TipoPlanillaEnum.obtenerTipoPlanilla(registro1.getTipoPlanilla());

                    Boolean tieneR4 = false;
                    if (indicePlanilla.getPresentaRegistro4() != null) {
                        tieneR4 = indicePlanilla.getPresentaRegistro4();
                    }

                    // se comprueba el tipo de planilla “M”, “N” (UGPP), “J”, “X” y “U” para adición
                    if (((TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaRegistro1) || TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaRegistro1) || TipoPlanillaEnum.ACUERDOS.equals(tipoPlanillaRegistro1)) 
                    		&& tieneR4)
                            || (TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaRegistro1)
                                    || TipoPlanillaEnum.MORA.equals(tipoPlanillaRegistro1)
                                    || TipoPlanillaEnum.SENTENCIA_JUDICIAL.equals(tipoPlanillaRegistro1)
                                    || TipoPlanillaEnum.EMPRESA_EN_PROCESO_LIQUIDACION.equals(tipoPlanillaRegistro1))) {

                        indicePlanilla.setMotivoProcesoManual(MotivoProcesoPilaManualEnum.ARCHIVO_ADICION);
                        procesar = false;

                    } // sólo para correcciones (NO UGPP)
                    else if ((TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaRegistro1) || TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaRegistro1) || TipoPlanillaEnum.ACUERDOS.equals(tipoPlanillaRegistro1)) 
                    		&& !tieneR4) {
                        indicePlanilla.setMotivoProcesoManual(MotivoProcesoPilaManualEnum.ARCHIVO_CORRECCION);
                        procesar = false;
                    }
                }
                break;
            case ARCHIVO_OI_IP:
            case ARCHIVO_OI_IPR:
                // los archivos de pensionados sólo se afectan por reproceso, no por corrección ni adición
                break;
            default:
                break;
        }

        resultado[0] = procesar;

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.validadores.bloque7.interfaces.IGestorPila2#reprocesarPlanilla(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.FasePila2Enum, java.lang.Boolean, java.lang.String)
     */
    @Override
    public void reprocesarPlanilla(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso) {
        logger.debug("Inicia GestorPila2.reprocesarPlanilla(IndicePlanilla)");
        gestorUsp.ejecutarProcesamientoPila2(idIndicePlanilla, faseProceso, esSimulado, usuarioProceso, 0L);
        //se comenta para ajuste de la HU 410 automática
        //procesarAportesNovedades(idIndicePlanilla);
        logger.debug("Finaliza GestorPila2.reprocesarPlanilla(IndicePlanilla)");
    }

    /**
     * Valida si es una planilla de dependientes tipo E o de pensionado las cuales se considara para ejecuci[on automatica 
     * @param idPlanilla
     * @return
     */
    private Boolean esAutomatica(Long idPlanilla) {
    	IndicePlanilla planilla = persistencia.consultarPlanillaAutomaticaPorId(idPlanilla);
    	if(planilla != null) {
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }
}
