package com.asopagos.pila.composite.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;

import com.asopagos.aportes.clients.ConsultarAportesGeneralesPorIdRegGeneral;
import com.asopagos.aportes.clients.ConsultarCuentaAporte;
import com.asopagos.aportes.clients.CrearActualizarMovimientoAporte;
import com.asopagos.aportes.clients.CrearTransaccion;
import com.asopagos.aportes.clients.EjecutarArmadoStaging;
import com.asopagos.aportes.clients.EjecutarBorradoStaging;
import com.asopagos.aportes.clients.RegistrarRelacionarAportes;
import com.asopagos.aportes.clients.RegistrarRelacionarNovedades;
import com.asopagos.aportes.clients.OrganizarNovedadesSucursal;
import com.asopagos.aportes.clients.SimularFasePila2;
import com.asopagos.aportes.composite.clients.EnviarComunicadoPila;
import com.asopagos.aportes.composite.clients.PrepararYProcesarPlanillas;
import com.asopagos.aportes.composite.clients.ProcesarAportesNovedadesByIdPlanilla;
import com.asopagos.aportes.composite.clients.ProcesarAportesNovedadesByIdPlanillaSincrono;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.bandejainconsistencias.clients.ActualizarEstadoPlanillaNuevoAportante;
import com.asopagos.bandejainconsistencias.clients.AnularPlanillaOF;
import com.asopagos.bandejainconsistencias.clients.AprobarRegistrosBandeja399;
import com.asopagos.bandejainconsistencias.clients.AprobarDetalles;
import com.asopagos.bandejainconsistencias.clients.AprobarSolicitudCambioIden;
import com.asopagos.bandejainconsistencias.clients.ConsultarArchivosInconsistentesResumen;
import com.asopagos.bandejainconsistencias.clients.ConsultarDatosActualizacionPlanilla;
import com.asopagos.bandejainconsistencias.clients.ConsultarPlanillasPorAprobarConInconsistenciasValidacion;
import com.asopagos.bandejainconsistencias.clients.CrearAportante;
import com.asopagos.bandejainconsistencias.clients.EnviarSolicitudCambioIdenPila;
import com.asopagos.bandejainconsistencias.clients.RecalcularEstadoRegistroGeneral;
import com.asopagos.bandejainconsistencias.clients.ValidarEstructuraPlanilla;
import com.asopagos.bandejainconsistencias.clients.PersistirHistoricoBloque2;
import com.asopagos.bandejainconsistencias.clients.ValidarExistenciaPersona;
import com.asopagos.bandejainconsistencias.dto.CreacionAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.bandejainconsistencias.dto.PreparacionAprobacion399DTO;
import com.asopagos.bandejainconsistencias.dto.ResultadoActualizacionEstadoDTO;
import com.asopagos.bandejainconsistencias.dto.ResultadoAprobacionCambioIdentificacionDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.empresas.clients.CrearSucursalEmpresaPila;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.ccf.general.Constante;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.pila.clients.ActualizarEjecucionGestion;
import com.asopagos.pila.clients.ActualizarEstadosRegistroPlanilla;
import com.asopagos.pila.clients.AprobarAportesAdicion;
import com.asopagos.pila.clients.AprobarBloqueAportesAdicion;
import com.asopagos.pila.clients.AprobarBloqueNovedadesAdicionCorreccionCompuesto;
import com.asopagos.pila.clients.AprobarCambioAportesCorrecciones;
import com.asopagos.pila.clients.AprobarCambioAportesCorreccionesEnBloque;
import com.asopagos.pila.clients.AprobarDetallesNoRegistrados;
import com.asopagos.pila.clients.AprobarProcesoNovedadesAdicionCorreccion;
import com.asopagos.pila.clients.CargarArchivosParalelo;
import com.asopagos.pila.clients.ComprobarAsignacionValoresCorreccion;
import com.asopagos.pila.clients.ComprobarExistenciaNovedades;
import com.asopagos.pila.clients.ConciliarArchivosOIyOFSincrono;
import com.asopagos.pila.clients.ReprocesarPlanillasPendientesConciliacionSincrono;
import com.asopagos.pila.clients.ConsultaPlanillasGestionManual;
import com.asopagos.pila.clients.ConsultarAportanteReprocesoMundoUno;
import com.asopagos.pila.clients.ConsultarDatosConexionOperadorInformacion;
import com.asopagos.pila.clients.ConsultarEstadoRegistrosAdicionCorreccion;
import com.asopagos.pila.clients.ConsultarIdRegistroGeneralPorNumeroPlanilla;
import com.asopagos.pila.clients.ConsultarIndicePlanillaEntidad;
import com.asopagos.pila.clients.ConsultarListaBlancaAportantes;
import com.asopagos.pila.clients.ConsultarRegistroGeneralPorIdPlanilla;
import com.asopagos.pila.clients.ConsultarResultadoFase1SimuladaCorrecion;
import com.asopagos.pila.clients.ConsultarResultadoProcesamientoPlanillaAdicion;
import com.asopagos.pila.clients.CopiarAgruparPlanilla;
import com.asopagos.pila.clients.DefinirBloqueAProcesarPilaMundoDos;
import com.asopagos.pila.clients.EjecutarUSPporFasePila;
import com.asopagos.pila.clients.EsAportePropio;
import com.asopagos.pila.clients.FiltrarExistentes;
import com.asopagos.pila.clients.FinalizarPlanillaAsistida;
import com.asopagos.pila.clients.FinalizarProceso;
import com.asopagos.pila.clients.IndicesPlanillaPorRegistroGeneral;
import com.asopagos.pila.clients.InstanciarProceso;
import com.asopagos.pila.clients.MarcarNoProcesamientoArchivoR;
import com.asopagos.pila.clients.MarcarPlanillaAsistidaNotificada;
import com.asopagos.pila.clients.RegistrarNoRegistrarAdicionEnBloque;
import com.asopagos.pila.clients.RegistrarNoRegistrarCorreccionesEnBloque;
import com.asopagos.pila.clients.RegistrarPlanillaCandidataReproceso;
import com.asopagos.pila.clients.ReprocesarPlanillaSincrono;
import com.asopagos.pila.clients.SolicitarEjecucionFase1SimuladaAdicion;
import com.asopagos.pila.clients.SolicitarEjecucionFase1SimuladaCorreccion;
import com.asopagos.pila.clients.ValidarArchivosOI;
import com.asopagos.pila.clients.ValidarEstadoEjecucion410Automatico;
import com.asopagos.pila.clients.VerificarEstadoRegistrosAdicionCorreccion;
import com.asopagos.pila.composite.service.PilaCompositeService;
import com.asopagos.pila.composite.service.util.FuncionesUtilitarias;
import com.asopagos.pila.constants.ConstantesParaMensajes;
import com.asopagos.pila.dto.ConjuntoResultadoRegistroCorreccionADTO;
import com.asopagos.pila.dto.ConjuntoResultadoRegistroCorreccionCDTO;
import com.asopagos.pila.dto.DatosProcesoFtpDTO;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import com.asopagos.pila.dto.ResultadoFinalizacionPlanillaAsistidaDTO;
import com.asopagos.pila.dto.ResultadoValidacionRegistrosAdicionCorrecionDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;
import com.asopagos.pila.clients.ReprocesarMundo1;
import com.asopagos.pila.clients.ReprocesarPlanillasM1;
import com.asopagos.pila.clients.ReprocesarPlanillasB3;

/**
 * <b>Descripción: Clase que immplementa los servicios para...</b> <b>Historia
 * de Usuario: HU-399 HU-411</b>,
 *
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co> Andres Felipe Buitrago</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class PilaCompositeBusiness implements PilaCompositeService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PilaCompositeBusiness.class);
    
    @Resource
    private ManagedExecutorService managedExecutorService;
    
    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.composite.service.PilaCompositeService#aprobarSolicitudCambioIdentificacion(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void aprobarSolicitudCambioIdentificacion(List<SolicitudCambioNumIdentAportante> solicitudes, UserDTO userDTO) {

        AprobarSolicitudCambioIden aprobarSolicitudCambioIdService = new AprobarSolicitudCambioIden(solicitudes);
        aprobarSolicitudCambioIdService.execute();
        ResultadoAprobacionCambioIdentificacionDTO listasIndices = aprobarSolicitudCambioIdService.getResult();

        ValidarArchivosOI validarArchivosOIService = new ValidarArchivosOI(listasIndices.getIndicesOI());
        validarArchivosOIService.execute();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#reprocesarRegistrosAporteConInconsistencias(java.lang.String,
     *      java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void reprocesarRegistrosAporteConInconsistencias(String fase,
            List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO, UserDTO user) {
        String firmaServicio = "PilaBandejaBusiness.reprocesarRegistrosAporteConInconsistencias (String, List<InconsistenciaRegistroAporteDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        FasePila2Enum faseEnum = FasePila2Enum.valueOf(fase);
        if (fase == null) {
            String mensaje = "No se ha especificado una fase correcta para el reproceso de los registros";
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + " :: " + mensaje);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, mensaje);
        }

        // se construyen listados con los ID de registro general y detallado en el listado de inconsistencias (para normal y 410)
        List<Long> idsRegistrosGeneralesNormal = new ArrayList<>();
        List<Long> idsRegistrosDetalladosNormal = new ArrayList<>();
        List<Long> idsRegistrosGenerales410 = new ArrayList<>();
        List<Long> idsRegistrosDetallados410 = new ArrayList<>();

        for (InconsistenciaRegistroAporteDTO inconsistencia : lstInconsistenciaRegistroAporteDTO) {
            if (!idsRegistrosGeneralesNormal.contains(inconsistencia.getIdRegistroGeneralAporte())
                    && (inconsistencia.getEsSimulado() == null || !inconsistencia.getEsSimulado())) {
                idsRegistrosGeneralesNormal.add(inconsistencia.getIdRegistroGeneralAporte());
            }

            if (!idsRegistrosDetalladosNormal.contains(inconsistencia.getIdRegistroDetalladoAporte())
                    && (inconsistencia.getEsSimulado() == null || !inconsistencia.getEsSimulado())) {
                idsRegistrosDetalladosNormal.add(inconsistencia.getIdRegistroDetalladoAporte());
            }

            if (!idsRegistrosGenerales410.contains(inconsistencia.getIdRegistroGeneralAporte())
                    && (inconsistencia.getEsSimulado() != null && inconsistencia.getEsSimulado())) {
                idsRegistrosGenerales410.add(inconsistencia.getIdRegistroGeneralAporte());
            }

            if (!idsRegistrosDetallados410.contains(inconsistencia.getIdRegistroDetalladoAporte())
                    && (inconsistencia.getEsSimulado() != null && inconsistencia.getEsSimulado())) {
                idsRegistrosDetallados410.add(inconsistencia.getIdRegistroDetalladoAporte());
            }
        }
        for(Long ids : idsRegistrosGeneralesNormal){
            PreparacionAprobacion399DTO normales = prepararDatosAprobacion(ids,
                user.getNombreUsuario(), Boolean.TRUE, Boolean.FALSE, faseEnum);
            
                procesarCasosAprobacionReproceso(normales, Boolean.FALSE, faseEnum);
        }
        for(Long ids : idsRegistrosGenerales410){
            PreparacionAprobacion399DTO planillas410 = prepararDatosAprobacion(ids,
            user.getNombreUsuario(), Boolean.TRUE, Boolean.TRUE, faseEnum); 
            
            procesarCasosAprobacionReproceso(planillas410, Boolean.TRUE, faseEnum);
        }
        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#desafiliarEmpleadoresCeroTrabajadores(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void desafiliarEmpleadoresCeroTrabajadores(List<Long> idEmpleadores, UserDTO user) {
        logger.debug("Inicia PilaCompositeBusiness.desafiliarEmpleadoresCeroTrabajadores (List<Long>)");

        // DTO de entrada para el servicio de novedades
        SolicitudNovedadDTO dtoEntrada = new SolicitudNovedadDTO();
        dtoEntrada.setTipoTransaccion(TipoTransaccionEnum.DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_CERO_TRABAJADORES);
        DatosNovedadAutomaticaDTO datosNovedad = new DatosNovedadAutomaticaDTO();
        datosNovedad.setIdEmpleadores(idEmpleadores);
        /* Se asigna el empleador a desafiliar. */
        dtoEntrada.setDatosNovedadMasiva(datosNovedad);
        
        RadicarSolicitudNovedadMasiva radicarDesafiliacion = new RadicarSolicitudNovedadMasiva(dtoEntrada);
        radicarDesafiliacion.execute();

        logger.debug("Inicia PilaCompositeBusiness.desafiliarEmpleadoresCeroTrabajadores (List<Long>)");
    }

    
    /** (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#registrarRelacionarAportesPlanilla(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void registrarRelacionarAportesPlanilla(Long idIndicePlanilla, Long idPlanillaOriginal, Long idRegistroGeneral, Long idTransaccion,
            UserDTO userDTO) {
        String firmaServicio = "PilaCompositeBusiness.registrarRelacionarAportesPlanilla(Long, Long, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        // en el caso de que se trate de una planilla sin original, se valida la asignación de valores para la corrección
        if(idPlanillaOriginal == null){
            ComprobarAsignacionValoresCorreccion comprobarValores = new ComprobarAsignacionValoresCorreccion(idIndicePlanilla);
            comprobarValores.execute();
        }
        
        /* se ajusta que los registros detallados que hayan sido rechazados y tengan estado de validación "NO_OK" o "NO_VALIDADO_BD", 
         * sean aprobados para que no impidan la creación de los registros temporales */
        AprobarDetallesNoRegistrados aprobarDetallesNoRegistrados = new AprobarDetallesNoRegistrados(idIndicePlanilla);
        aprobarDetallesNoRegistrados.execute();
        
        //if (verificarValidezPorFase(idRegistroGeneral, FasePila2Enum.PILA2_FASE_1)) {
        if(isFaseExitosa(FasePila2Enum.PILA2_FASE_1, idRegistroGeneral, true)){
            // se solicita la ejecución de la fase 2 de pila para la planilla de corrección
        	logger.info("válido para PILA2_FASE_1 - is fase exitosa");
            reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, userDTO != null ? userDTO.getNombreUsuario() : "SISTEMA", FasePila2Enum.PILA2_FASE_2);
        }
        else {
        	reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, userDTO != null ? userDTO.getNombreUsuario() : "SISTEMA", FasePila2Enum.FINALIZAR_TRANSACCION);
        }

        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#registrarAporteSimuladoCorreccion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO registrarAporteSimuladoCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            UserDTO userDTO) {
        String firmaServicio = "PilaCompositeBusiness.registrarAporteSimuladoCorreccion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        logger.debug("al servicio " + firmaServicio + " el registro de corrección C enviado es: " + criteriosSimulacion.toString());
        
        AprobarCambioAportesCorrecciones aprobarCambioAportesCorrecciones = new AprobarCambioAportesCorrecciones(criteriosSimulacion);
        aprobarCambioAportesCorrecciones.execute();

        ResultadoAprobacionCorreccionAporteDTO result = aprobarCambioAportesCorrecciones.getResult();

        logger.debug("el objeto result es: " + result.toString());
        // sí se obtuvo un resultado de la aprobación, se procede con el registro del movimiento de la cuenta de aporte
        if (result != null && result.getAporteDetalladoActualizado() != null) {
            AporteDetalladoModeloDTO aporteDetallado = result.getAporteDetalladoActualizado();

            MovimientoAporteModeloDTO movimientoCorreccion = new MovimientoAporteModeloDTO(
            		result.getTipoAjuste(),
                    TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES, 
                    EstadoAporteEnum.CORREGIDO,
                    aporteDetallado.getAporteObligatorio(), 
                    aporteDetallado.getValorMora(), 
                    new Date(), 
                    new Date(), 
                    aporteDetallado.getId(),
                    aporteDetallado.getIdAporteGeneral());

            logger.debug("se va a crear/actualizar el movimiento: " + movimientoCorreccion.toString());
            
            CrearActualizarMovimientoAporte crearActualizarMovimientoAporte = new CrearActualizarMovimientoAporte(movimientoCorreccion);
            crearActualizarMovimientoAporte.execute();
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#registrarRelacionarNovedadesPlanilla(java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void registrarRelacionarNovedadesPlanilla(Long idIndicePlanilla, Long idRegistroGeneral, UserDTO userDTO) {
        String firmaServicio = "PilaCompositeBusiness.registrarRelacionarNovedadesPlanilla(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        if (verificarValidezPorFase(idRegistroGeneral, FasePila2Enum.PILA2_FASE_2)) {
            // se solicita la ejecución de la fase 3 de pila para la planilla de corrección
            reprocesarPlanillaSimulada(idIndicePlanilla, FasePila2Enum.PILA2_FASE_3);

            // se verifica que se hayan generado novedades para la planilla
            ComprobarExistenciaNovedades comprobarExistenciaNovedades = new ComprobarExistenciaNovedades(idRegistroGeneral);
            comprobarExistenciaNovedades.execute();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#finalizarPlanillaAdicionCorreccion(java.lang.Long, java.lang.Boolean,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> finalizarPlanillaAdicionCorreccion(Long idRegistroGeneral, UserDTO userDTO) {
        long timeStart = System.nanoTime();
    	String firmaServicio = "PilaCompositeBusiness.finalizarPlanillaAdicionCorreccion(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String llaveRespuesta = "mensajeResultadoFinalizacion";

        Map<String, String> respuesta = new HashMap<>();

        // se verifica que el archivo es válido para ser finalizado
        //if (verificarValidezPorFase(idRegistroGeneral, FasePila2Enum.PILA2_FASE_3)) {
            // se solicita la actualización del estado de la planilla
            List<Long> regGenerales = new ArrayList<>();
            List<Long> idPlanillas = new ArrayList<>();
            regGenerales.add(idRegistroGeneral);
            //Busqueda de indices de planilla a partir de registro general
            IndicesPlanillaPorRegistroGeneral indicesPlanillaPorRegistroGeneral = new IndicesPlanillaPorRegistroGeneral(regGenerales);
            
            indicesPlanillaPorRegistroGeneral.execute();
            idPlanillas.addAll(indicesPlanillaPorRegistroGeneral.getResult());
            
            long timeIndicesPlanillaPorRegistroGeneral = System.nanoTime();
            logger.debug("La invocación al servicio IndicesPlanillaPorRegistroGeneral tardó: " + CalendarUtils.calcularTiempoEjecucion(timeStart, timeIndicesPlanillaPorRegistroGeneral));
            
            if(!idPlanillas.isEmpty()){
                ProcesarAportesNovedadesByIdPlanillaSincrono procesarPlanilla = new ProcesarAportesNovedadesByIdPlanillaSincrono(idPlanillas.get(0));
                procesarPlanilla.execute();
                
                long timeProcesarAportesNovedadesByIdPlanillaSincrono = System.nanoTime();
                logger.debug("La invocación al servicio ProcesarAportesNovedadesByIdPlanillaSincrono tardó: " + CalendarUtils.calcularTiempoEjecucion(timeIndicesPlanillaPorRegistroGeneral, timeProcesarAportesNovedadesByIdPlanillaSincrono));
            }

            long timeInicioFinalizarPlanillaAsistida = System.nanoTime();
            FinalizarPlanillaAsistida finalizarPlanilla = new FinalizarPlanillaAsistida(idRegistroGeneral);
            finalizarPlanilla.execute();

            ResultadoFinalizacionPlanillaAsistidaDTO resultadoFinalizacion = finalizarPlanilla.getResult();
            
            long timeFinalizarPlanillaAsistida = System.nanoTime();
            logger.debug("La invocación al servicio FinalizarPlanillaAsistida tardó: " + CalendarUtils.calcularTiempoEjecucion(timeInicioFinalizarPlanillaAsistida, timeFinalizarPlanillaAsistida));
            
            //Temporal
            logger.info("APORTES PENDIENTES ? " + resultadoFinalizacion.getAportesPendientes());
            logger.info("NOVEDADES PENDIENTES ? " + resultadoFinalizacion.getNovedadesPendientes());
            
            
            
            // se comprueba que el ESB ya procesó los aportes y/o novedades
            if (!resultadoFinalizacion.getAportesPendientes() && !resultadoFinalizacion.getNovedadesPendientes()) {
                // se solicita la notificación del aporte
                DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO = new DatosComunicadoPlanillaDTO();
                datosComunicadoPlanillaDTO.setIdPlanilla(idRegistroGeneral);
                datosComunicadoPlanillaDTO.setNumeroAportesEnPlanilla(resultadoFinalizacion.getCantidadAportesAfectados());
                datosComunicadoPlanillaDTO.setPlanillaManual(Boolean.TRUE);

                // indica que el proceso fue notificado
                Boolean notificado = true;

                EnviarComunicadoPila enviarComunicado = new EnviarComunicadoPila(datosComunicadoPlanillaDTO);
                try {
                    //TEMPORAL
                    logger.info("ENVIANDO COMUNICADO...");
                    
                    enviarComunicado.execute();
                } catch (Exception e) {
                    // falla el servicio de notificación, se cambia el indicador para evitar cambiar el estado en el archivo
                    logger.error(firmaServicio, e);
                    
                    notificado = false;
                    respuesta.put(llaveRespuesta, ConstantesParaMensajes.NO_FINALIZADO_ERROR_NOTIFICACION);
                    logger.info("NO FINALIZADO POR ERROR EN NOTIFICACION...");
                }

                if (notificado) {
                    MarcarPlanillaAsistidaNotificada marcarPlanillaAsistidaNotificada = new MarcarPlanillaAsistidaNotificada(
                            idRegistroGeneral);
                    marcarPlanillaAsistidaNotificada.execute();
                    respuesta.put(llaveRespuesta, ConstantesParaMensajes.FINALIZADO_EXITOSO);
                    logger.info("FINALIZADO EXITOSO ...");
                }
            }
            else {
            	String mensajeError = "NO FINALIZADO POR APORTES O NOVEDADES SIN PROCESAR PARA REGISTRO GENERAL";
            	
            	RegistrarPlanillaCandidataReproceso registrarPlanillaCandidataReproceso = new RegistrarPlanillaCandidataReproceso(mensajeError, idRegistroGeneral);
            	registrarPlanillaCandidataReproceso.execute();
            	
                respuesta.put(llaveRespuesta, ConstantesParaMensajes.NO_FINALIZADO_APORTES_NOVEDADES_SIN_PROCESAR);
                logger.info(mensajeError + " " + idRegistroGeneral + " ...");
            }
        //}

        long timeEnd = System.nanoTime();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
        return respuesta;
    }

    /**
     * @param idRegistroGeneral
     *        ID del registro general a validar
     * @param faseAValidar
     *        Fase que se desea validar
     * @return <b>Boolean</b>
     *         Indica que el archivo es válido para la fase indicada
     */
    private Boolean verificarValidezPorFase(Long idRegistroGeneral, FasePila2Enum faseAValidar) {
        String firmaServicio = "PilaCompositeBusiness.verificarValidezPorFase(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        VerificarEstadoRegistrosAdicionCorreccion verificar = new VerificarEstadoRegistrosAdicionCorreccion(false, idRegistroGeneral,
                faseAValidar);

        verificar.execute();
        Map<String, String> resultadoValidacion = verificar.getResult();
        if (resultadoValidacion != null && resultadoValidacion.containsKey(ConstantesParaMensajes.RESULTADO_VALIDACION)) {
            String resultado = resultadoValidacion.get(ConstantesParaMensajes.RESULTADO_VALIDACION);
            if (ConstantesParaMensajes.CONTINUAR.equals(resultado)) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                return true;
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return false;
    }

    /**
     * Método que invoca el procesamiento de una planilla en modo simulado
     * @param idPlanilla
     *        identificador de la planilla
     * @param faseProceso
     *        fase que debe ejecutar el proceso
     */
    private void reprocesarPlanillaSimulada(Long idPlanilla, FasePila2Enum faseProceso) {
        logger.debug("Inicia PilaCompositeBusiness.reprocesarPlanillaSimulada(Long, FasePila2Enum)");
        ArchivoPilaDTO archivoPilaDTO = new ArchivoPilaDTO();
        archivoPilaDTO.setIdIndicePlanillaOI(idPlanilla);
        archivoPilaDTO.setFaseProceso(faseProceso);
        archivoPilaDTO.setEsSimulado(true);
        ReprocesarPlanillaSincrono reprocesarPlanillaOIService = new ReprocesarPlanillaSincrono(archivoPilaDTO);
        reprocesarPlanillaOIService.execute();
        logger.debug("Finaliza PilaCompositeBusiness.reprocesarPlanillaSimulada(Long, FasePila2Enum)");
    }
    
    /**
     * Método que invoca el procesamiento de una planilla en modo simulado
     * @param idPlanilla
     *        identificador de la planilla
     * @param faseProceso
     *        fase que debe ejecutar el proceso
     */
    private void reprocesarPlanillaSimulada410(Long idPlanilla, Long idTransaccion, boolean reanudarTransaccion, String usuario, FasePila2Enum faseProceso) {
        logger.info("Inicia PilaCompositeBusiness.reprocesarPlanillaSimulada410(Long, FasePila2Enum)");
        
        EjecutarUSPporFasePila ejecutarUSPporFase = new EjecutarUSPporFasePila( reanudarTransaccion, faseProceso, idPlanilla, idTransaccion, usuario);
        ejecutarUSPporFase.execute();

        logger.info("Finaliza PilaCompositeBusiness.reprocesarPlanillaSimulada410(Long, FasePila2Enum)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#aprobarRegistrosAporteConInconsistencias(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<InconsistenciaRegistroAporteDTO> aprobarRegistrosAporteConInconsistencias(
            List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO, UserDTO user) {
        String firmaServicio = "PilaBandejaBusiness.aprobarRegistrosAporteConInconsistencias (List<InconsistenciaRegistroAporteDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // se construyen listados con los ID de registro general y detallado en el listado de inconsistencias (para normal y 410)
        List<Long> idsRegistrosGeneralesNormal = new ArrayList<>();
        List<Long> idsRegistrosDetalladosNormal = new ArrayList<>();
        List<Long> idsRegistrosGenerales410 = new ArrayList<>();
        List<Long> idsRegistrosDetallados410 = new ArrayList<>();

        for (InconsistenciaRegistroAporteDTO inconsistencia : lstInconsistenciaRegistroAporteDTO) {
            if (!idsRegistrosGeneralesNormal.contains(inconsistencia.getIdRegistroGeneralAporte())
                    && (inconsistencia.getEsSimulado() == null || !inconsistencia.getEsSimulado())) {
                idsRegistrosGeneralesNormal.add(inconsistencia.getIdRegistroGeneralAporte());
            }

            if (!idsRegistrosDetalladosNormal.contains(inconsistencia.getIdRegistroDetalladoAporte())
                    && (inconsistencia.getEsSimulado() == null || !inconsistencia.getEsSimulado())) {
                idsRegistrosDetalladosNormal.add(inconsistencia.getIdRegistroDetalladoAporte());
            }

            if (!idsRegistrosGenerales410.contains(inconsistencia.getIdRegistroGeneralAporte())
                    && (inconsistencia.getEsSimulado() != null && inconsistencia.getEsSimulado())) {
                idsRegistrosGenerales410.add(inconsistencia.getIdRegistroGeneralAporte());
            }

            if (!idsRegistrosDetallados410.contains(inconsistencia.getIdRegistroDetalladoAporte())
                    && (inconsistencia.getEsSimulado() != null && inconsistencia.getEsSimulado())) {
                idsRegistrosDetallados410.add(inconsistencia.getIdRegistroDetalladoAporte());
            }
        }
    
        if(idsRegistrosDetalladosNormal != null){
            AprobarDetalles detalles = new AprobarDetalles(idsRegistrosDetalladosNormal,Boolean.FALSE, user.getNombreUsuario());
        detalles.execute();
        }
        if(idsRegistrosDetallados410 != null){
            AprobarDetalles detalles2 = new AprobarDetalles(idsRegistrosDetallados410,Boolean.FALSE,user.getNombreUsuario());
        detalles2.execute();
        }
        
        
        for(Long ids : idsRegistrosGeneralesNormal){
            PreparacionAprobacion399DTO normales = prepararDatosAprobacion(ids,
            user.getNombreUsuario(), Boolean.FALSE, Boolean.FALSE, FasePila2Enum.PILA2_FASE_1);  
            
            procesarCasosAprobacionReproceso(normales, Boolean.FALSE, FasePila2Enum.PILA2_FASE_1);
        }
        for(Long ids : idsRegistrosGenerales410){
             // se prepara la aprobación de planillas HU-410
        PreparacionAprobacion399DTO planillas410 = prepararDatosAprobacion(ids,
        user.getNombreUsuario(), Boolean.FALSE, Boolean.TRUE, FasePila2Enum.PILA2_FASE_1); 
            
        procesarCasosAprobacionReproceso(planillas410, Boolean.TRUE, FasePila2Enum.PILA2_FASE_1);
        
 
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstInconsistenciaRegistroAporteDTO;
    }

    /**
     * Método encargado de la solicitar la preparación de registros generales y detallados para su aprobación y continuidad de proceso
     * @param idsRegistrosGenerales
     *        Listado de IDs de registros generales a preparar
     * @param idsRegistrosDetallados
     *        Listado de IDs de registros detallados a preparar
     * @param usuarioAprobador
     *        Nombre del usuario aprobador
     * @param esReproceso
     *        Indicador de reproceso
     * @param esSimulado
     *        Indicador de proceso simulado
     * @param fase
     *        Fase que se está aprobando / reprocesando
     * @return <b>PreparacionAprobacion399DTO</b>
     *         DTO que contiene la información conjunta de la preparación y la marca de proceso exitoso
     */
    private PreparacionAprobacion399DTO prepararDatosAprobacion(Long idsRegistrosGeneral,
            String usuarioAprobador, Boolean esReproceso, Boolean esSimulado, FasePila2Enum fase) {
        String firmaMetodo = "prepararDatosAprobacion(List<Long>, List<Long>, String, Boolean "+esReproceso+",Boolean "+esSimulado+", FasePila2Enum "+fase.name()+")";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        
        List<Long> idsRegistrosGenerales = new ArrayList();
        idsRegistrosGenerales.add(idsRegistrosGeneral);
        PreparacionAprobacion399DTO datosAprobacion = new PreparacionAprobacion399DTO();

        if (idsRegistrosGenerales != null && !idsRegistrosGenerales.isEmpty() ) {
            // se solicita un número de ID de transacción
            Long idTransaccion = crearTransaccion();
            logger.info("idTransaccion");
            logger.info(idTransaccion);

            // se solicita la preparación de los datos de registros generales y detallados previos a la ejecución de SPs
            datosAprobacion.setIdsRegistrosGenerales(idsRegistrosGenerales);
            datosAprobacion.setIdTransaccion(idTransaccion);
            datosAprobacion.setEsReproceso(esReproceso);
            datosAprobacion.setEsSimulado(esSimulado);
            datosAprobacion.setFase(fase);
            datosAprobacion.setUsuarioAprobador(
                    usuarioAprobador != null ? usuarioAprobador : ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO);

            logger.info("idTransaccion");
            AprobarRegistrosBandeja399 preparacionAprobacion = new AprobarRegistrosBandeja399(datosAprobacion);
            preparacionAprobacion.execute();
            datosAprobacion = preparacionAprobacion.getResult();
        }
        else {
            datosAprobacion.setSucess(Boolean.FALSE);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return datosAprobacion;
    }

    /**
     * Método encargado de ejecutar los procedimientos almacenados que le siguen a la aprobación de los registros
     */
    private void procesarCasosAprobacionReproceso(PreparacionAprobacion399DTO aprobaciones, Boolean esSimulado, FasePila2Enum fase) {
        String firmaMetodo = "procesarCasosAprobacionReproceso(PreparacionAprobacion399DTO "+aprobaciones.toString()+", Boolean "+esSimulado+", FasePila2Enum "+fase+")";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (aprobaciones.getSucess()) {
            // cuando se trata de un reproceso, se vuelve a generar el staging
            if (aprobaciones.getEsReproceso()) {
                ejecutarArmadoStaging(aprobaciones.getIdTransaccion());
            }

            Map<Long, ResultadoActualizacionEstadoDTO> resultadosActualizacion = null;

            // se simula la fase 1 (en una aprobación sólo cambia el estado del archivo en el registro general)
            if (FasePila2Enum.PILA2_FASE_1.equals(fase)) {
                simularFasePila2(aprobaciones.getIdTransaccion());

                // en el caso de planilla manual, se recalcula el estado del archivo en registro general antes de
                // actualizar el índice de planilla
				if (esSimulado) {
					recalcularEstadoRegistroGeneral(aprobaciones.getIdTransaccion());
				}
				
                // se actualiza el estado luego de la simulación
				resultadosActualizacion = actualizarIndicePlanilla(aprobaciones.getIdsRegistrosGenerales(),
						esSimulado, aprobaciones.getEsReproceso(), null, aprobaciones.getUsuarioAprobador());
            }

            // las fases 2 y 3 de PILA 2 luego de una aprobación, sólo aplican para casos no simulados
            if (!esSimulado) {
                // se ejecuta el registro o relación de aportes
                registrarRelacionarAportes(aprobaciones.getIdTransaccion());

                // se actualiza el estado luego del registro o relación de los aportes
                resultadosActualizacion = actualizarIndicePlanilla(aprobaciones.getIdsRegistrosGenerales(), esSimulado,
                        aprobaciones.getEsReproceso(), resultadosActualizacion, aprobaciones.getUsuarioAprobador());

                        
                // se ejecuta el registro o relación de novedades
                registrarRelacionarNovedades(aprobaciones.getIdTransaccion());
                
                // se actualiza el estado luego del registro o relación de las novedades
                actualizarIndicePlanilla(aprobaciones.getIdsRegistrosGenerales(), esSimulado, aprobaciones.getEsReproceso(),
                resultadosActualizacion, aprobaciones.getUsuarioAprobador());
                
                // Se ejecuta el sp organizar novedades sucursal
                organizarNovedadesSucursal(aprobaciones.getIdTransaccion());

                //Inicia procesamiento de aportes y novedades para planillas que salen de bandejas
                this.procesarAportesNovedadesReProceso(aprobaciones.getIdsRegistrosGenerales());
                
                
            }

            // se borra el staging
            ejecutarBorradoStaging(aprobaciones.getIdTransaccion());
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    
    private void procesarAportesNovedadesReProceso(List<Long> idreGenerales){
        logger.info("Inicio de metodo procesarAportesNovedadesReProceso");
        List<Long> idsPlanillas = new ArrayList<>();
        IndicesPlanillaPorRegistroGeneral indicesPlanillaPorRegistroGeneral = new IndicesPlanillaPorRegistroGeneral(idreGenerales);
        indicesPlanillaPorRegistroGeneral.execute();
        idsPlanillas.addAll(indicesPlanillaPorRegistroGeneral.getResult());
        
        for (Long idPlanilla : idsPlanillas) {
           ProcesarAportesNovedadesByIdPlanilla aportesNovedadesByIdPlanilla = new ProcesarAportesNovedadesByIdPlanilla(idPlanilla);
           aportesNovedadesByIdPlanilla.execute();
        }
        
        
    }
    /**
     * Método para actualizar el estado del registro general luego de un reproceso de planilla manual
	 * @param aprobaciones
	 */
	private void recalcularEstadoRegistroGeneral(Long idTransaccion) {
		RecalcularEstadoRegistroGeneral recalculo = new RecalcularEstadoRegistroGeneral(idTransaccion);
		recalculo.execute();
	}

	/**
     * Método encargado de acualizar los índices de planilla asociados a un listado de ID de registro general
     */
    private Map<Long, ResultadoActualizacionEstadoDTO> actualizarIndicePlanilla(List<Long> idsRegistrosGenerales, Boolean esSimulado,
            Boolean esReproceso, Map<Long, ResultadoActualizacionEstadoDTO> estadosPrevios, String usuario) {
        // se consultan los datos para la actualización de los índices de planilla moficados
        List<ActualizacionEstadosPlanillaDTO> datosActualizacion = null;

        Map<Long, ResultadoActualizacionEstadoDTO> result = new HashMap<>();

        ConsultarDatosActualizacionPlanilla consultarDatosActualizacionPlanilla = new ConsultarDatosActualizacionPlanilla(esReproceso,
                esSimulado, idsRegistrosGenerales);
        consultarDatosActualizacionPlanilla.execute();
        datosActualizacion = consultarDatosActualizacionPlanilla.getResult();

        // se actualiza el estado del índice de planilla PILA (por cada registro general procesado)
        for (ActualizacionEstadosPlanillaDTO actualizacionDTO : datosActualizacion) {
            if (estadosPrevios == null || !estadosPrevios.containsKey(actualizacionDTO.getIdRegistroGeneral())
                    || (!estadosPrevios.get(actualizacionDTO.getIdRegistroGeneral()).getEstado().getReportarBandejaInconsistencias())
                    || (esReproceso && !estadosPrevios.get(actualizacionDTO.getIdRegistroGeneral()).getAplicado())) {
            	
            	actualizacionDTO.setEsReproceso399(esReproceso);
            	actualizacionDTO.setUsuario(usuario);
                actualizarEstadosRegistroPlanilla(actualizacionDTO);
                ResultadoActualizacionEstadoDTO resAct = new ResultadoActualizacionEstadoDTO();
                resAct.setAplicado(Boolean.TRUE);
                resAct.setEstado(actualizacionDTO.getEstadoProceso());
                resAct.setIdRegistroGeneral(actualizacionDTO.getIdRegistroGeneral());
                result.put(actualizacionDTO.getIdRegistroGeneral(), resAct);
            }
        }

        if (estadosPrevios != null && result.isEmpty()) {
            result = estadosPrevios;
        }

        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#verificarEstadoRegistrosAdicionRegistrarRelacionarAporte(java.lang.Long,
     *      java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> verificarEstadoRegistrosAdicionRegistrarRelacionarAporte(Long idIndicePlanilla, Long idRegistroGeneral,
            UserDTO userDTO) {
        logger.debug("Inicia PilaCompositeBusiness.verificarEstadoRegistrosAdicionRegistrarRelacionarAporte(Long, Long, UserDTO)");
        Map<String, String> result;

        VerificarEstadoRegistrosAdicionCorreccion verificar = new VerificarEstadoRegistrosAdicionCorreccion(false, idRegistroGeneral,
                FasePila2Enum.PILA2_FASE_2);
        verificar.execute();
        result = verificar.getResult();
        if (result != null && result.containsKey(ConstantesParaMensajes.RESULTADO_VALIDACION)) {
            String resultado = result.get(ConstantesParaMensajes.RESULTADO_VALIDACION);
            if (ConstantesParaMensajes.CONTINUAR.equals(resultado)) {
                reprocesarPlanillaSimulada(idIndicePlanilla, FasePila2Enum.PILA2_FASE_2);
            }
        }
        logger.debug("Finaliza PilaCompositeBusiness.verificarEstadoRegistrosAdicionRegistrarRelacionarAporte(Long, Long, UserDTO)");
        return result;
    }

    /**
     * Método encargado de invocar el servicio que crea una transacción.
     * 
     * @return id de la transacción.
     */
    private Long crearTransaccion() {
        logger.debug("Inicio de método crearTransaccion()");
        CrearTransaccion crearTransaccionService = new CrearTransaccion();
        crearTransaccionService.execute();
        Long idTransaccion = crearTransaccionService.getResult();
        logger.debug("Fin de método crearTransaccion()");
        return idTransaccion;
    }

    /**
     * Método encargado de invocar el servicio del armado staging.
     * 
     * @param idTransaccion
     *        id de la transaccion.
     */
    private void ejecutarArmadoStaging(Long idTransaccion) {
        logger.debug("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
        EjecutarArmadoStaging ejecutarArmadoStagingService = new EjecutarArmadoStaging(idTransaccion);
        ejecutarArmadoStagingService.execute();
        logger.debug("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
    }

    /**
     * Método encargado de invocar el servicio.
     * 
     * @param idTransaccion
     *        id de la transaccion.
     */
    private void simularFasePila2(Long idTransaccion) {
        logger.debug("Inicio de método simularFasePila2(Long idTransaccion)");
        SimularFasePila2 simularService = new SimularFasePila2(idTransaccion);
        simularService.execute();
        logger.debug("Fin de método simularFasePila2(Long idTransaccion)");
    }

    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de relacionar o registrar aportes
     * @param idTransaccion
     *        id de la transacción.
     */
    private void registrarRelacionarAportes(Long idTransaccion) {
        logger.debug("Inicio de método registrarRelacionarAportes(Long idTransaccion)");
        RegistrarRelacionarAportes registrarRelacionarService = new RegistrarRelacionarAportes(idTransaccion, Boolean.FALSE, Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.debug("Fin de método registrarRelacionarAportes(Long idTransaccion)");
    }

    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de relacionar o registrar novedades.
     * @param idTransaccion
     *        id de la transacción.
     */
    private void registrarRelacionarNovedades(Long idTransaccion) {
        logger.debug("Inicio de método registrarRelacionarNovedades(Long idTransaccion)");
        RegistrarRelacionarNovedades registrarRelacionarService = new RegistrarRelacionarNovedades(idTransaccion, Boolean.FALSE,
                Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.debug("Fin de método registrarRelacionarNovedades(Long idTransaccion)");
    }

    /**
     * Método que invoca el servicio que actualiza los estados de archivo de planilla PILA.
     * 
     * @param actualizacionDTO
     *        DTO con los datos para la actualización
     * 
     */
    private Boolean actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO actualizacionDTO) {
        logger.debug("Inicio de método PilaCompositeBusiness.actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO)");
        Boolean success = false;

        ActualizarEstadosRegistroPlanilla actualizarEstadosRegistroPlanillaService = new ActualizarEstadosRegistroPlanilla(
                actualizacionDTO);
        actualizarEstadosRegistroPlanillaService.execute();
        success = actualizarEstadosRegistroPlanillaService.getResult();

        logger.debug("Fin de método PilaCompositeBusiness.actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO)");
        return success;
    }

    /**
     * Método encargado de invocar el servicio del borrado staging.
     * 
     * @param idTransaccion
     *        id de la transaccion.
     */
    private void ejecutarBorradoStaging(Long idTransaccion) {
        logger.info("Inicio de método ejecutarBorradoStaging(Long idTransaccion)");
        EjecutarBorradoStaging ejecutarBorradoStagingService = new EjecutarBorradoStaging(idTransaccion);
        ejecutarBorradoStagingService.execute();
        logger.info("Fin de método ejecutarBorradoStaging(Long idTransaccion)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#enviarSolicitudCambioIden(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public InconsistenciaDTO enviarSolicitudCambioIden(InconsistenciaDTO inconsistencias, Long numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, UserDTO user) {
        ValidarExistenciaPersona buscarPersona = new ValidarExistenciaPersona(numeroIdentificacion.toString(), tipoIdentificacion);
        buscarPersona.execute();
        if (buscarPersona.getResult()) {

            EnviarSolicitudCambioIdenPila enviarSolicitudCambioIdenService = new EnviarSolicitudCambioIdenPila(numeroIdentificacion,
                    inconsistencias);
            enviarSolicitudCambioIdenService.execute();
            inconsistencias.setRegistroProcesado(true);
            return inconsistencias;
        }
        else {
            inconsistencias.setRegistroProcesado(false);
            return inconsistencias;
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#crearAportantePila(java.lang.Long,
     *      com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public InconsistenciaDTO crearAportantePila(Long numeroPlanilla, InconsistenciaDTO inconsistencia) {
        String firmaServicio = "PilaCompositeBusiness.crearAportantePila(Long, InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // en primer lugar se comprueba la existencia del índice de planilla de la inconsistencia
        IndicePlanilla indice;
        ConsultarIndicePlanillaEntidad consultarIndicePlanillaService = new ConsultarIndicePlanillaEntidad(
                inconsistencia.getIndicePlanilla());
        consultarIndicePlanillaService.execute();
        indice = consultarIndicePlanillaService.getResult();
        if (indice == null) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio
                    + " :: No se encontro en la BD el indice planilla asociado a la inconsistencia");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }

        EmpresaModeloDTO empresa = null;
        List<IndicePlanilla> indices = new ArrayList<>();

        // se consulta si la planilla consiste en un aporte propio y se agrega a DTO de inconsistencia
        EsAportePropio esAportePropioService = new EsAportePropio(inconsistencia.getIndicePlanilla());
        esAportePropioService.execute();
        inconsistencia.setEsAportePropio(esAportePropioService.getResult());

        // se solicita la creación de la persona y la preparación de la empresa y sucursal en caso de que aplique
        CreacionAportanteDTO creacionAportante = null;
        CrearAportante crearAportanteService = new CrearAportante(numeroPlanilla, inconsistencia);
        crearAportanteService.execute();

        creacionAportante = crearAportanteService.getResult();
        if (creacionAportante != null && creacionAportante.getEmpresa() != null) {
            // con el entity que se preparó, se crea la empresa
            Long idEmpresa = null;
            empresa = creacionAportante.getEmpresa();
            CrearEmpresa crearEmpresaService = new CrearEmpresa(empresa);
            crearEmpresaService.execute();
            idEmpresa = crearEmpresaService.getResult();

            // con el ID de la empresa creada, se crea la sucursal especificada en el archivo, sí aplica
            if (idEmpresa != null && creacionAportante.getSucursal() != null) {
                CrearSucursalEmpresaPila crearSucursalEmpresaPila = new CrearSucursalEmpresaPila(idEmpresa,
                        creacionAportante.getSucursal());

                crearSucursalEmpresaPila.execute();
            }
        }

        if (creacionAportante != null && creacionAportante.getCreacionExitosa()) {
            // se solicita la actualización de los estados del índice de planilla y la gestión de la inconsistencia
            ActualizarEstadoPlanillaNuevoAportante actualizarEstados = new ActualizarEstadoPlanillaNuevoAportante(indice);
            actualizarEstados.execute();

            // se toma el índice de planilla actualizado
            indice = actualizarEstados.getResult();

            // se añade el índice de planilla a la lista para solicitar la continuación del proceso de validación
            indices.add(indice);
            ValidarArchivosOI validarArchivosOIService = new ValidarArchivosOI(indices);
            validarArchivosOIService.execute();

            inconsistencia.setRegistroProcesado(true);
        }
        else {
            inconsistencia.setRegistroProcesado(false);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return inconsistencia;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#anularRegistroFinanciero(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void anularRegistroFinanciero(InconsistenciaDTO inconsistencia, UserDTO user) {
        String firmaServicio = "PilaCompositeBusiness.anularRegistroFinanciero(InconsistenciaDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // se solicita la anulación del registro OF
        AnularPlanillaOF anulacionOF = new AnularPlanillaOF(inconsistencia);
        anulacionOF.execute();
        
        reprocesarIndiceInconsistencia(inconsistencia);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /**
     * Método encargado del llevar a cabo el reprocesamiento de una planilla pila en M1
     * @param inconsistencia 
     */
    private void reprocesarIndiceInconsistencia(InconsistenciaDTO inconsistencia) {
        String firmaMetodo = "PilaCompositeBusiness.anularRegistroFinanciero(InconsistenciaDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // sí el tipo de archivo que llega no es OF, simboliza una anulación por un error de conciliación
        if (!TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())) {

            // se consulta el índice de planilla asociado a la inconsistencia
            ConsultarIndicePlanillaEntidad consultaOI = new ConsultarIndicePlanillaEntidad(inconsistencia.getIndicePlanilla());
            consultaOI.execute();
            IndicePlanilla indice = consultaOI.getResult();

            if (indice != null) {

                // se solicita el reprocesamiento de la planilla
                List<IndicePlanilla> indices = new ArrayList<>();
                indices.add(indice);

                ValidarArchivosOI validarArchivosOIService = new ValidarArchivosOI(indices);
                validarArchivosOIService.execute();
            }
            else {
                logger.error(
                        ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: No existe el índice de planilla de la inconsistencia");
                throw new TechnicalException(
                        MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + "No existe el índice de planilla de la inconsistencia");
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#continuarProcesoBandeja392(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public void continuarProcesoBandeja392(InconsistenciaDTO inconsistencia){
        String firmaServicio = "PilaCompositeBusiness.continuarProcesoBandeja392(InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        ValidarEstructuraPlanilla validarEstructuraPlanilla = new ValidarEstructuraPlanilla(inconsistencia);
        validarEstructuraPlanilla.execute();
        
        reprocesarIndiceInconsistencia(inconsistencia);

        // Persistir historico bloque 2
        PersistirHistoricoBloque2 service = new PersistirHistoricoBloque2(inconsistencia.getIndicePlanilla());
        service.execute();


        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#consultarCuentaAportePlanillaOriginal(java.lang.Long)
     */
    @Override
    public List<CuentaAporteDTO> consultarCuentaAportePlanillaOriginal(Long idPlanillaOriginal){
        String firmaServicio = "PilaCompositeBusiness.consultarCuentaAportePlanillaOriginal(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<CuentaAporteDTO> result = null;
        
        // se consulta el registro general de la planilla para formar el DTO de consulta de la cuenta aportes
        ConsultarRegistroGeneralPorIdPlanilla consultaRegGen = new ConsultarRegistroGeneralPorIdPlanilla(idPlanillaOriginal);
        consultaRegGen.execute();
        RegistroGeneralModeloDTO regGen = consultaRegGen.getResult();
        
        Long periodoLong = FuncionesUtilitarias.obtenerFechaMillis(regGen.getPeriodoAporte() + "-01");
        
        if(regGen != null){
         // se consultan los aportes relacionados al registro general
            ConsultarAportesGeneralesPorIdRegGeneral consultaAporteGeneral = new ConsultarAportesGeneralesPorIdRegGeneral(regGen.getId());
            consultaAporteGeneral.execute();
            List<AporteGeneralModeloDTO> aportes = consultaAporteGeneral.getResult();
            
            // se arman los DTO requeridos para la consulta de cuenta aportes
            List<AnalisisDevolucionDTO> datosPeticion = new ArrayList<>();
            for (AporteGeneralModeloDTO aporte : aportes) {
                AnalisisDevolucionDTO datosPlanilla = new AnalisisDevolucionDTO();

                datosPlanilla.setIdAporte(aporte.getId());
                datosPlanilla.setNumOperacion(aporte.getId().toString());
                datosPlanilla.setMetodo(ModalidadRecaudoAporteEnum.PILA);
                datosPlanilla.setConDetalle(Boolean.TRUE);
                datosPlanilla.setIdRegistroGeneral(aporte.getIdRegistroGeneral());
                if(aporte.getIdEmpresa() != null){
                    datosPlanilla.setIdEmpresa(aporte.getIdEmpresa());
                }else{
                    datosPlanilla.setIdPersona(aporte.getIdPersona());
                }
                datosPlanilla.setNumPlanilla(regGen.getNumPlanilla());
                datosPlanilla.setTipoPlanilla(TipoPlanillaEnum.obtenerTipoPlanilla(regGen.getTipoPlanilla()));
                datosPlanilla.setPeriodo(periodoLong);
                
                datosPeticion.add(datosPlanilla);
            }
            
            // se consulta la cuenta aportes
            ConsultarCuentaAporte consultarCuentaAporte = new ConsultarCuentaAporte(null, datosPeticion);
            consultarCuentaAporte.execute();
            
            result = consultarCuentaAporte.getResult();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
	 * (non-Javadoc)
	 * @see com.asopagos.pila.composite.service.PilaCompositeService#aprobarAportesAdicionCompuesto(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO aprobarAportesAdicionCompuesto(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            UserDTO userDTO) {
        String firmaMetodo = "PilaCompositeBusiness.aprobarAportesAdicionCompuesto(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        ResultadoAprobacionCorreccionAporteDTO result = null;
        AprobarAportesAdicion aprobarAportesAdicion = new AprobarAportesAdicion(criteriosSimulacion);
        aprobarAportesAdicion.execute();
        result = aprobarAportesAdicion.getResult();
        //Metodo deprecado por mejoras pila
        //procesarPlanillas();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
	 * ( non-Javadoc) 
	 * @see com.asopagos.pila.composite.service.PilaCompositeService#aprobarProcesoNovedadesAdicionCorreccionCompuesto(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO aprobarProcesoNovedadesAdicionCorreccionCompuesto(
            RegistrarCorreccionAdicionDTO criteriosSimulacion, UserDTO userDTO) {
        String firmaMetodo = "PilaCompositeBusiness.aprobarProcesoNovedadesAdicionCorreccionCompuesto(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AprobarProcesoNovedadesAdicionCorreccion aprobarNovedades = new AprobarProcesoNovedadesAdicionCorreccion(criteriosSimulacion);
        aprobarNovedades.execute();        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aprobarNovedades.getResult();
        
        //Metodo reprecado por mejoras pila
        //procesarPlanillas();
    }

    /**
	 * Método para la invocación de los procesos finales de PILA 
	 */
    @Deprecated
	private void procesarPlanillas() {
        String firmaServicio = "PilaCompositeBusiness.procesarPlanillas()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        PrepararYProcesarPlanillas procesarPlanillas = new PrepararYProcesarPlanillas();
        procesarPlanillas.execute();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
     * Método que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser aprobadas en la bandeja de
     * aportes y aprobar estos registros
     * 
     */
	@Override
	@Asynchronous
	public void aprobarTodasConInconsistenciasValidacion(TipoIdentificacionEnum tipoIdentificacionAportante,
			String numeroIdentificacionAportante, Short digitoVerificacionAportante, Long fechaInicio, Long fechaFin,
			UserDTO user) {
		
		String firmaServicio = "PilaCompositeBusiness.aprobarTodasConInconsistenciasValidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        String proceso = "21_399";
        try {
        	
    		String estadoEnProceso = "EN_PROCESO";
    		
    		//Se inicia ejecucion de proceso de gestion inconsistencias
    		ActualizarEjecucionGestion actualizarEjecucionGestion = new ActualizarEjecucionGestion(proceso, true, estadoEnProceso);
    		actualizarEjecucionGestion.execute();
        	
    		ConsultarPlanillasPorAprobarConInconsistenciasValidacion consultarPlanillasPorAprobarConInconsistenciasValidacion = new ConsultarPlanillasPorAprobarConInconsistenciasValidacion(numeroIdentificacionAportante, tipoIdentificacionAportante, fechaFin, digitoVerificacionAportante, fechaInicio);
    		consultarPlanillasPorAprobarConInconsistenciasValidacion.execute();
    		
    		List<InconsistenciaRegistroAporteDTO> result = consultarPlanillasPorAprobarConInconsistenciasValidacion.getResult();
    		List<InconsistenciaRegistroAporteDTO> resultTmp = new ArrayList<InconsistenciaRegistroAporteDTO>();    		
    		if(result.size() > 0) {
    			int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
    			int cont = 0;
    			for(InconsistenciaRegistroAporteDTO r : result) {
    				resultTmp.add(r);
    				cont++;
    				if(cont == pagina) {
    					aprobarRegistrosAporteConInconsistencias(resultTmp, user);
    					resultTmp = new ArrayList<InconsistenciaRegistroAporteDTO>();
    					cont = 0;
    				}
    			}
    			if(cont > 0) {
					aprobarRegistrosAporteConInconsistencias(resultTmp, user);
				}
    			
    			//List<InconsistenciaRegistroAporteDTO> listaAprobados = aprobarRegistrosAporteConInconsistencias(result, user);
    		}
    		
    		//Se finaliza ejecucion de proceso de gestion inconsistencias
    		String estadoFinalizado = "FINALIZADO_EXITOSO";
    		ActualizarEjecucionGestion actualizarEjecucionGestionFin = new ActualizarEjecucionGestion(proceso, false, estadoFinalizado);
    		actualizarEjecucionGestionFin.execute();
    		     		
    		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        	//Se finaliza ejecucion de proceso de gestion inconsistencias
    		String estadoFinalizado = "FINALIZADO_FALLIDO";
    		ActualizarEjecucionGestion actualizarEjecucionGestionFallido = new ActualizarEjecucionGestion(proceso, false, estadoFinalizado);
    		actualizarEjecucionGestionFallido.execute();
            logger.error("Finaliza aprobarTodasConInconsistenciasValidacion:Error técnico inesperado");
            logger.debug("Finaliza aprobarTodasConInconsistenciasValidacion:Error técnico inesperado");
            throw new TechnicalException(e);
        }
				
	}

	/**
     * Método que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser reprocesados en la bandeja de
     * aportes y reporcesar estos registros
     * 
     */
	@Override
	@Asynchronous
	public void reprocesarTodasConInconsistenciasValidacion(TipoIdentificacionEnum tipoIdentificacionAportante,
			String numeroIdentificacionAportante, Short digitoVerificacionAportante, Long fechaInicio, Long fechaFin,
			UserDTO user) {
		String firmaServicio = "PilaCompositeBusiness.aprobarTodasConInconsistenciasValidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
                
        String proceso = "21_399";
        
        try {
        	
    		String estado = "EN_PROCESO";
    		
    		//Se inicia ejecucion de proceso de gestion inconsistencias
			ActualizarEjecucionGestion actualizarEjecucionGestion = new
			ActualizarEjecucionGestion(proceso, true, estado);
			actualizarEjecucionGestion.execute();
			 
    		ConsultarPlanillasPorAprobarConInconsistenciasValidacion consultarPlanillasPorAprobarConInconsistenciasValidacion = new ConsultarPlanillasPorAprobarConInconsistenciasValidacion(numeroIdentificacionAportante, tipoIdentificacionAportante, fechaFin, digitoVerificacionAportante, fechaInicio);
    		consultarPlanillasPorAprobarConInconsistenciasValidacion.execute();
    		
    		List<InconsistenciaRegistroAporteDTO> result = consultarPlanillasPorAprobarConInconsistenciasValidacion.getResult();
    		List<InconsistenciaRegistroAporteDTO> resultTmp = new ArrayList<InconsistenciaRegistroAporteDTO>(); 
    		if(result.size() > 0) {
    			String fase = "PILA2_FASE_1";
    			int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
    			int cont = 0;
    			for (InconsistenciaRegistroAporteDTO r : result) {
    				cont++;
    				resultTmp.add(r);
    				if(cont == pagina) {
    					reprocesarRegistrosAporteConInconsistencias(fase, resultTmp, user);
    					cont = 0;
    					resultTmp = new ArrayList<InconsistenciaRegistroAporteDTO>();
    				}
    			}
    			if(cont > 0) {
					reprocesarRegistrosAporteConInconsistencias(fase, resultTmp, user);
				}
    			//reprocesarRegistrosAporteConInconsistencias(fase, result, user);
    		}
    		
    		//Se finaliza ejecucion de proceso de gestion inconsistencias
			String estadoFinalizado = "FINALIZADO_EXITOSO"; 
			ActualizarEjecucionGestion actualizarEjecucionGestionFin = new ActualizarEjecucionGestion(proceso,false, estadoFinalizado); 
			actualizarEjecucionGestionFin.execute();
    		     		
    		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        	String estadoFinalizado = "FINALIZADO_FALLIDO"; 
        	ActualizarEjecucionGestion actualizarEjecucionGestionFallido = new ActualizarEjecucionGestion(proceso,
			false, estadoFinalizado); actualizarEjecucionGestionFallido.execute();
			
            logger.error("Finaliza aprobarTodasConInconsistenciasValidacion:Error técnico inesperado");
            logger.debug("Finaliza aprobarTodasConInconsistenciasValidacion:Error técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
	}
	

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void reprocesarB3Aut( UserDTO user) {
        String firmaServicio = "PilaCompositeBusiness.reprocesarPlanillasB3Aut()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        String proceso = "21_392";
         try {
    		String estado = "EN_PROCESO";
    		
    		//Se inicia ejecucion de proceso de gestion inconsistencias
			ActualizarEjecucionGestion actualizarEjecucionGestion = new
			ActualizarEjecucionGestion(proceso, true, estado);
			actualizarEjecucionGestion.execute();

             ReprocesarPlanillasB3 servicio = new ReprocesarPlanillasB3();
                servicio.execute();
                List<Long> idsPlanillasParaReproceso = servicio.getResult();

                if (idsPlanillasParaReproceso != null) {
                    int inicio = 0;
                    int fin = 0;
                    while (inicio < idsPlanillasParaReproceso.size()) {
                        fin += 20;
                        if (fin > idsPlanillasParaReproceso.size()) fin = idsPlanillasParaReproceso.size();
                        ReprocesarMundo1 servicio2 = new ReprocesarMundo1(idsPlanillasParaReproceso.subList(inicio, fin));
                        servicio2.execute();
                        inicio += 20;
                    }
                }
                //Se finaliza ejecucion de proceso de gestion inconsistencias
			String estadoFinalizado = "FINALIZADO_EXITOSO"; 
			ActualizarEjecucionGestion actualizarEjecucionGestionFin = new ActualizarEjecucionGestion(proceso,false, estadoFinalizado); 
			actualizarEjecucionGestionFin.execute();
    		     		
    		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        	String estadoFinalizado = "FINALIZADO_FALLIDO"; 
        	ActualizarEjecucionGestion actualizarEjecucionGestionFallido = new ActualizarEjecucionGestion(proceso,
			false, estadoFinalizado); actualizarEjecucionGestionFallido.execute();
			
            logger.error("Finaliza reprocesarPlanillasB3Aut técnico inesperado");
            logger.debug("Finaliza reprocesarPlanillasB3Aut técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
    }
	/**
     * Método que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser reprocesados en la bandeja de
     * aportes y reporcesar estos registros
     * 
     */
	@Override
	@Asynchronous
	public void reprocesarPlanillasMundoUno(List<PlanillaGestionManualDTO> indicesPlanilla, UserDTO user) {
		String firmaServicio = "PilaCompositeBusiness.reprocesarPlanillasMundoUno()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
                
        String proceso = "21_392";
        
        try {
    		String estado = "EN_PROCESO";
    		
    		//Se inicia ejecucion de proceso de gestion inconsistencias
			ActualizarEjecucionGestion actualizarEjecucionGestion = new
			ActualizarEjecucionGestion(proceso, true, estado);
			actualizarEjecucionGestion.execute();
			
			if (!indicesPlanilla.isEmpty()) {
				
				// mantis 0267969
				indicesPlanilla = verificarListaBlancaAportantes(indicesPlanilla);

                List<Long> indices = indicesPlanilla.stream().map(PlanillaGestionManualDTO::getIdIndicePlanilla).collect(Collectors.toList());
                logger.info(indices);
                // Reporsesar planilas M1

                ReprocesarPlanillasM1 servicio = new ReprocesarPlanillasM1(indices);
                servicio.execute();
                List<Long> idsPlanillasParaReproceso = servicio.getResult();

                if (idsPlanillasParaReproceso != null) {
                    int inicio = 0;
                    int fin = 0;
                    while (inicio < idsPlanillasParaReproceso.size()) {
                        fin += 20;
                        if (fin > idsPlanillasParaReproceso.size()) fin = idsPlanillasParaReproceso.size();
                        ReprocesarMundo1 servicio2 = new ReprocesarMundo1(idsPlanillasParaReproceso.subList(inicio, fin));
                        servicio2.execute();
                        inicio += 20;
                    }
                }
				
				// ConsultarAportanteReprocesoMundoUno aportantesAReprocesarMundoUno = new ConsultarAportanteReprocesoMundoUno(indicesPlanilla);
				// aportantesAReprocesarMundoUno.execute();
				// List <PersonaModeloDTO> personasAReprocesar =  aportantesAReprocesarMundoUno.getResult();
				
                /*
				if (!personasAReprocesar.isEmpty()) {
					// List<IndicePlanilla> indicesActualizar = new ArrayList<>();
					// List<IndicePlanilla> indicesActualizados = new ArrayList<>();
					
					//Se comparan los tipos de identificacion de las planillas que llegaron y las que se consultaron para así solo reprocesar las que existen sus aportantes
					for(PlanillaGestionManualDTO personaEnPlanillas  : indicesPlanilla) {
						for(PersonaModeloDTO personaConsultada : personasAReprocesar) {
							if(personaEnPlanillas.getTipoIdAportante().equals(personaConsultada.getTipoIdentificacion())
									&& personaEnPlanillas.getNumeroIdAportante().equals(personaConsultada.getNumeroIdentificacion())) {
								
								IndicePlanilla indiceActualizar = new IndicePlanilla();
								indiceActualizar.setId(personaEnPlanillas.getIdIndicePlanilla());
								indicesActualizar.add(indiceActualizar);
							}
						}
					}
					
					for(IndicePlanilla indiceActualizarEstado : indicesActualizar) {
						
						// en primer lugar se comprueba la existencia del índice de planilla de la inconsistencia
				        IndicePlanilla indiceCompleto;
				        ConsultarIndicePlanillaEntidad consultarIndicePlanillaService = new ConsultarIndicePlanillaEntidad(
				        		indiceActualizarEstado.getId());
				        consultarIndicePlanillaService.execute();
				        indiceCompleto = consultarIndicePlanillaService.getResult();
						
						// se solicita la actualización de los estados del índice de planilla y la gestión de la inconsistencia
				        if(indiceCompleto != null) {
				        	ActualizarEstadoPlanillaNuevoAportante actualizarEstados = new ActualizarEstadoPlanillaNuevoAportante(indiceCompleto);
				            actualizarEstados.execute();
				            
				            // se toma el índice de planilla actualizado
				            indicesActualizados.add(actualizarEstados.getResult());
				        }			            
					}
	            
		            if(!indicesActualizados.isEmpty()) {
		            	ValidarArchivosOI validarArchivosOIService = new ValidarArchivosOI(indicesActualizados);
			            validarArchivosOIService.execute();
		            }
				}
                */
			}
    		
    		//Se finaliza ejecucion de proceso de gestion inconsistencias
			String estadoFinalizado = "FINALIZADO_EXITOSO"; 
			ActualizarEjecucionGestion actualizarEjecucionGestionFin = new ActualizarEjecucionGestion(proceso,false, estadoFinalizado); 
			actualizarEjecucionGestionFin.execute();
    		     		
    		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        	String estadoFinalizado = "FINALIZADO_FALLIDO"; 
        	ActualizarEjecucionGestion actualizarEjecucionGestionFallido = new ActualizarEjecucionGestion(proceso,
			false, estadoFinalizado); actualizarEjecucionGestionFallido.execute();
			
            logger.error("Finaliza reprocesarPlanillasMundoUno técnico inesperado");
            logger.debug("Finaliza reprocesarPlanillasMundoUno técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
	}
	
	/**
     * Método que permite crar los aportantes de manera masiva 
     * de las planillas seleccionadas en bloque 5
     *  
     */
	@Override
	@Asynchronous
	public void crearAportantesPlanillasMundoUno(List<PlanillaGestionManualDTO> indicesPlanilla, UserDTO user) {
		String firmaServicio = "PilaCompositeBusiness.crearAportantesPlanillasMundoUno()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
                
        String proceso = "21_392";
        
        try {
    		String estado = "EN_PROCESO";
    		
    		//Se inicia ejecucion de proceso de gestion inconsistencias
			ActualizarEjecucionGestion actualizarEjecucionGestion = new
			ActualizarEjecucionGestion(proceso, true, estado);
			actualizarEjecucionGestion.execute();
			
			if (!indicesPlanilla.isEmpty()) {
				for(PlanillaGestionManualDTO planillaACrearAportante : indicesPlanilla) {
					
					InconsistenciaDTO inconsistencia = new InconsistenciaDTO();
					
					inconsistencia.setTipoIdentificacion(planillaACrearAportante.getTipoIdAportante());
					inconsistencia.setNumeroIdAportante(planillaACrearAportante.getNumeroIdAportante());
					inconsistencia.setTipoArchivo(planillaACrearAportante.getTipoArchivo());
					inconsistencia.setIndicePlanilla(planillaACrearAportante.getIdIndicePlanilla());
					
					Long numeroPlanilla = Long.parseLong(planillaACrearAportante.getNumeroPlanilla());
					
					if(TipoIdentificacionEnum.NIT.equals(planillaACrearAportante.getTipoIdAportante())) {
						
						crearAportantePila(numeroPlanilla, inconsistencia);
						
					}else if(planillaACrearAportante.getTipoIdAportante() != null) {
						String nombreRazonSocial = planillaACrearAportante.getNombreAportante();

						String[] nombreSeparado = nombreRazonSocial.split(" ");
						
						PersonaModeloDTO persona = new PersonaModeloDTO();
						
						if(nombreSeparado.length == 1) {
							persona.setPrimerNombre(nombreSeparado[0]);
							persona.setPrimerApellido(nombreSeparado[0]);
						}else if(nombreSeparado.length == 2) {
							persona.setPrimerNombre(nombreSeparado[0]);
							persona.setPrimerApellido(nombreSeparado[1]);
						}else if(nombreSeparado.length == 3) {
							persona.setPrimerNombre(nombreSeparado[0]);
							persona.setSegundoNombre(nombreSeparado[1]);
							persona.setPrimerApellido(nombreSeparado[2]);
						}else if(nombreSeparado.length == 4) {
							persona.setPrimerNombre(nombreSeparado[0]);
							persona.setSegundoNombre(nombreSeparado[1]);
							persona.setPrimerApellido(nombreSeparado[2]);
							persona.setSegundoApellido(nombreSeparado[3]);
						}else if(nombreSeparado.length > 4) {
							persona.setPrimerNombre(nombreSeparado[0]);
							persona.setSegundoNombre(nombreSeparado[1]);
							persona.setPrimerApellido(nombreSeparado[2]);
							
							//se deben unir las palabras sobrantes 
							String segundoApellido = "";
													
							for (int i = 0; i < nombreSeparado.length; i++) {
								if(i>2) {
									segundoApellido += nombreSeparado[i];
								}
							}
							
							if(segundoApellido.length()<50) {
								persona.setSegundoApellido(nombreSeparado[3]);
							}else {
								segundoApellido = segundoApellido.substring(0, 49);
								persona.setSegundoApellido(segundoApellido);
							}
							
							
						}
						
						inconsistencia.setNombresNuevoAportante(persona);
						
						crearAportantePila(numeroPlanilla, inconsistencia);
					}
				}
			}
    		
    		//Se finaliza ejecucion de proceso de gestion inconsistencias
			String estadoFinalizado = "FINALIZADO_EXITOSO"; 
			ActualizarEjecucionGestion actualizarEjecucionGestionFin = new ActualizarEjecucionGestion(proceso,false, estadoFinalizado); 
			actualizarEjecucionGestionFin.execute();
    		     		
    		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        	String estadoFinalizado = "FINALIZADO_FALLIDO"; 
        	ActualizarEjecucionGestion actualizarEjecucionGestionFallido = new ActualizarEjecucionGestion(proceso,
			false, estadoFinalizado); actualizarEjecucionGestionFallido.execute();
			
            logger.error("Finaliza crearPlanillasMundoUno técnico inesperado");
            logger.debug("Finaliza crearPlanillasMundoUno técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
	}
	
	 /**
	  * Metodo para reprocesar todos las planillas del blq 5 que cumplan con los criterios de busqueda
     */
    @Override
    public void reprocesarTodasPlanillasMundoUno(TipoIdentificacionEnum tipoIdentificacion, String numeroPlanilla,
            Long fechaInicio, Long fechaFin, String numeroIdentificacion, TipoOperadorEnum operador, Short digitoVerificacion, String bloqueValidacion, Boolean ocultarBlq5, UserDTO user) {
        String firmaMetodo = "reprocesarTodasPlanillasMundoUno(TipoIdentificacionEnum, String, Long, Long, String, TipoOperadorEnum, Short, String, Boolean, UserDTO user)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                
        if(ocultarBlq5 != true && (bloqueValidacion == null || (bloqueValidacion != null && bloqueValidacion.equals("BLOQUE_5")))) {
        	ConsultarArchivosInconsistentesResumen archivosInconsistenciasBloq5 = new ConsultarArchivosInconsistentesResumen(digitoVerificacion, numeroPlanilla, bloqueValidacion, operador, numeroIdentificacion, tipoIdentificacion, fechaFin, fechaInicio, ocultarBlq5);  	
        	archivosInconsistenciasBloq5.execute();
      
        	List <InconsistenciaDTO> resultadoConsulta = archivosInconsistenciasBloq5.getResult();
        	
        	if(!resultadoConsulta.isEmpty()){
        		List<PlanillaGestionManualDTO> listaReprocesar = new ArrayList<>();
        		
        		for(InconsistenciaDTO registroReproceso : resultadoConsulta) {
        			PlanillaGestionManualDTO registroGestion = new PlanillaGestionManualDTO();
        			registroGestion.setTipoIdAportante(registroReproceso.getTipoIdentificacion());
        			registroGestion.setNumeroIdAportante(registroReproceso.getNumeroIdAportante());
        			registroGestion.setIdIndicePlanilla(registroReproceso.getIndicePlanilla());
        			
        			listaReprocesar.add(registroGestion);
        		}
        		
        		if(!listaReprocesar.isEmpty()) {
        			reprocesarPlanillasMundoUno(listaReprocesar, user);
        		}
        	}
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
	 /**
	  * Metodo para reprocesar todos las planillas del blq 5 que cumplan con los criterios de busqueda
    */
   @Override
   public void crearAportantesTodosMundoUno(TipoIdentificacionEnum tipoIdentificacion, String numeroPlanilla,
           Long fechaInicio, Long fechaFin, String numeroIdentificacion, TipoOperadorEnum operador, Short digitoVerificacion, String bloqueValidacion, Boolean ocultarBlq5, UserDTO user) {
       String firmaMetodo = "crearAportantesTodosMundoUno(TipoIdentificacionEnum, String, Long, Long, String, TipoOperadorEnum, Short, String, Boolean, UserDTO user)";
       logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
               
       if(ocultarBlq5 != true && (bloqueValidacion == null || (bloqueValidacion != null && bloqueValidacion.equals("BLOQUE_5")))) {
       	ConsultarArchivosInconsistentesResumen archivosInconsistenciasBloq5 = new ConsultarArchivosInconsistentesResumen(digitoVerificacion, numeroPlanilla, bloqueValidacion, operador, numeroIdentificacion, tipoIdentificacion, fechaFin, fechaInicio, ocultarBlq5);  	
       	archivosInconsistenciasBloq5.execute();
     
       	List <InconsistenciaDTO> resultadoConsulta = archivosInconsistenciasBloq5.getResult();
       	
       	if(!resultadoConsulta.isEmpty()){
       		List<PlanillaGestionManualDTO> listaCrear = new ArrayList<>();
       		
       		for(InconsistenciaDTO registroReproceso : resultadoConsulta) {
       			PlanillaGestionManualDTO registroGestion = new PlanillaGestionManualDTO();
       			registroGestion.setTipoIdAportante(registroReproceso.getTipoIdentificacion());
       			registroGestion.setNumeroIdAportante(registroReproceso.getNumeroIdAportante());
       			registroGestion.setIdIndicePlanilla(registroReproceso.getIndicePlanilla());
       			registroGestion.setNumeroPlanilla(registroReproceso.getNumeroPlanilla().toString());
       			registroGestion.setNombreAportante(registroReproceso.getRazonSocialAportante());
       			registroGestion.setTipoArchivo(registroReproceso.getTipoArchivo());
       			
       			listaCrear.add(registroGestion);
       		}
       		
       		if(!listaCrear.isEmpty()) {
       			crearAportantesPlanillasMundoUno(listaCrear, user);
       		}
       	}
       }
       
       logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
   }

   @Override
   @Asynchronous
   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public void conciliarOIOFyProcesarPilaManual(UserDTO userDTO){
	   String firma = "conciliarOIOFyProcesarPilaManual(UserDTO)";
	   logger.info(ConstantesComunes.INICIO_LOGGER + firma);
	   
	   // la taera ya se lanza cada 10 min por otro proceso programado
	   try {
		ConciliarArchivosOIyOFSincrono conciliarArchivosOIyOF = new ConciliarArchivosOIyOFSincrono();
		   conciliarArchivosOIyOF.execute();
		} catch (Exception e) {
			logger.error("error en la ejecución del servicio archivosPILABusiness.conciliarArchivosOIyOF", e);
			e.printStackTrace();
		}
		
		   
	   procesarAutomaticoPlanillaManual(null, userDTO);
	   
	   logger.info(ConstantesComunes.FIN_LOGGER + firma);
   }

    @Override
    public void reprocesarPlanillasPendientesConciliacion(UserDTO userDTO) {
        String firma = "reprocesarPlanillasPendientesConciliacion(UserDTO)";
	   logger.info(ConstantesComunes.INICIO_LOGGER + firma);

       try {
        
           ReprocesarPlanillasPendientesConciliacionSincrono reprocesarPlanillasPendientesConciliacionSincrono =
           new ReprocesarPlanillasPendientesConciliacionSincrono();    
           reprocesarPlanillasPendientesConciliacionSincrono.execute();
       } catch (Exception e) {
            logger.error("error en la ejecución del servicio archivosPILABusiness.conciliarArchivosOIyOF", e);
            e.printStackTrace();
       }

    }
   
   /**
	 * Método para el procesamiento automático de planillas manuales 
	 * (Creado para el control de cambios sobre la HU-410).
	 */
	@Override
	@Asynchronous
	public void procesarAutomaticoPlanillaManual(Long numeroPlanilla, UserDTO user) {
		long timeTopProcessStart = System.nanoTime();
		long timeTopProcessEnd;
		String firma = "procesarAutomaticoPlanillaManual(Long, UserDTO): " + numeroPlanilla;
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		try {
			if(!tieneEjecucionActiva() || numeroPlanilla != null){
				//Se consultan las planillas manuales que estén pendientes de procesamiento
				List<PlanillaGestionManualDTO> listadoPlanillasGestionManual = consultarPlanillasPendientes(numeroPlanilla);

				if(listadoPlanillasGestionManual != null && !listadoPlanillasGestionManual.isEmpty()){

					List<Callable<Long>> tareasParalelas = new LinkedList<>();
					
					//se realiza un procesamiento en paralelo de cada planilla pendiente por gestionar.
					for (PlanillaGestionManualDTO planillaManual : listadoPlanillasGestionManual) {
						
						Callable<Long> parallelTask = () -> {
							return procesarPlanillaManual(user, planillaManual);
				        };
				        tareasParalelas.add(parallelTask);
					}
					try {
						managedExecutorService.invokeAll(tareasParalelas);
				    } catch (InterruptedException e) {
				    	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
				        e.printStackTrace();
				    }
					
					actualizarEstadoEjecucion(numeroPlanilla != null ? true : false);
					timeTopProcessEnd = System.nanoTime();
					logger.info(ConstantesComunes.FIN_LOGGER + firma + CalendarUtils.calcularTiempoEjecucion(timeTopProcessStart, timeTopProcessEnd));
				}
				else{
					actualizarEstadoEjecucion(numeroPlanilla != null ? true : false);
					timeTopProcessEnd = System.nanoTime();
					logger.info(ConstantesComunes.FIN_LOGGER + firma + " :: No se encontraron planillas pendientes de gestión manual. " + CalendarUtils.calcularTiempoEjecucion(timeTopProcessStart, timeTopProcessEnd));
				}
				
			}else{
				logger.info(ConstantesComunes.FIN_LOGGER + firma + " :: Ya hay un proceso activo.");
			}
		} catch (Exception e) {
			
			actualizarEstadoEjecucion(numeroPlanilla != null ? true : false);
			
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
			e.printStackTrace();
		}
	}

	private Long procesarPlanillaManual(UserDTO user, PlanillaGestionManualDTO planillaManual) {
		//se valida el tipo de archivo que se está procesando para darle el tratamiento adecuado.
		
		logger.info("PROCESANDO PLANILLA DE " + planillaManual.getMotivoProcesoManual().name() + " : " + planillaManual.getNumeroPlanilla());
		
		switch (planillaManual.getMotivoProcesoManual()) {
		case ARCHIVO_CORRECCION:
			procesarArchivoCorreccion(planillaManual, user);
			break;
		case ARCHIVO_ADICION:
			procesarArchivoAdicion(planillaManual, user);
			break;
		case REPROCESO_PREVIO:
			procesarArchivosReproceso(planillaManual, user);
			break;
		}
		return 0L;
	}

	/**
	 * Actualiza el estado de ejecución del proceso automático de planillas manuales (HU 410)
	 */
	private void actualizarEstadoEjecucion(boolean tienePlanillaIndividual) {
		String firma = "actualizarEstadoEjecucion()";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		if(!tienePlanillaIndividual){
			ActualizarEjecucionGestion actualizarEjecucionGestion = new ActualizarEjecucionGestion("410", Boolean.FALSE, "FINALIZADO_EXITOSO");
			actualizarEjecucionGestion.execute();
		}
		
		logger.info(ConstantesComunes.FIN_LOGGER +  firma);
	}

	/**
	 * Verifica el estado actual de ejecución del proceso automático de planillas manuales (HU 410)  
	 * 
	 * @return boolean true si hay una ejecución activa del proceso, false en caso contrario.
	 */
	private boolean tieneEjecucionActiva() {
		String firma = "tieneEjecucionActiva()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		ValidarEstadoEjecucion410Automatico validarEstadoEjecucion410Automatico = new ValidarEstadoEjecucion410Automatico();
		validarEstadoEjecucion410Automatico.execute();
		boolean estadoEjecucion = validarEstadoEjecucion410Automatico.getResult();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + " :: " + estadoEjecucion);
		return estadoEjecucion;
	}

	/**
     * Método que obtiene un listado de planillas marcadas para procesamiento asistido de PILA mundo 2 pendientes de
     * procesamiento.
     * 
     * @return <b>List<PlanillaGestionManualDTO></b>
     *         Listado de los resultados obtenidos
     */
	private List<PlanillaGestionManualDTO> consultarPlanillasPendientes(Long numeroPlanilla) {
		String firma = "consultarPlanillasPendientes(Long)";
		
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		ConsultaPlanillasGestionManual consultaPlanillasGestionManual = new ConsultaPlanillasGestionManual(numeroPlanilla != null ? numeroPlanilla : null, null);  
		consultaPlanillasGestionManual.execute();
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		return consultaPlanillasGestionManual.getResult(); 
	}

	/**
	 * Método encargado de definir el tratamiento para la planilla de corrección en base a si cuenta o no con 
	 * planilla original.
	 * 
	 * @param planillaManual
	 * 			la planilla de corrección a ser procesada.
	 */
	private void procesarArchivoCorreccion(PlanillaGestionManualDTO planillaManual, UserDTO user){
		String firma = "procesarArchivoCorreccion(PlanillaGestionManualDTO, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		if(planillaManual.getIdPlanillaOriginal() != null){
			procesarArchivoCorreccionConOriginal(planillaManual, user);}
		else {
            logger.error("La planilla N es sin original, indice planilla numero : "+planillaManual.getIdIndicePlanilla());
            //procesarArchivoCorreccionSinOriginal(planillaManual, user);}
    }
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
	}
	
	/**
	 * Método encargado de procesar las planillas de adición.
	 * 
	 * @param planillaManual
	 * 			la planilla de corrección a ser procesada.
	 */
	private void procesarArchivoAdicion(PlanillaGestionManualDTO planillaManual, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "procesarArchivoAdicion(PlanillaGestionManualDTO, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		//Se solicita ejecutar/consultar el proceso de la fase 1 simulada corrección automatica
		ResultadoValidacionRegistrosAdicionCorrecionDTO resultadoEjecucionFase1Simulada = solicitarEjecucionFase1SimuladaAdicionAutomatico(planillaManual.getIdIndicePlanilla()); 
		
		Long idTransaccion = resultadoEjecucionFase1Simulada.getIdTransaccion();
		logger.info("Id trnsacción es = " + idTransaccion);
		
		switch (definirBloqueAProcesar(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user)) {
			case SIN_PARAMETRO:
				//En caso de que retorne SIN_PARAMETRO se asume que aún no ha terminado de procesar el bloque PILA2_FASE_1 pasando a procesar los registros de adición 
				registrarNoRegistrarAdicionAutomatico(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), planillaManual.getIdIndicePlanilla());

			case PILA2_FASE_1:
				//BLOQUE DE EJECUCIÓN PILA2_FASE_2: se ejecuta si ya se ha hecho el registro de las adiciones (Ejecución satisfactoria del bloque PILA_2_FASE_1) 
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_1, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase2PlanillaAdicion(planillaManual.getIdIndicePlanilla(), resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), resultadoEjecucionFase1Simulada.getResultados(), idTransaccion, user);
				} else{
					break;
				}
				
			case PILA2_FASE_2:	
				//BLOQUE DE EJECUCIÓN PILA2_FASE_3: Se ejecuta si ya se haya realizado el registro/relación de aportes (Ejecución satisfactoria del bloque PILA_2_FASE_2)
				//if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), true)){
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase3PlanillaAdicion(resultadoEjecucionFase1Simulada.getResultados(), resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
				} else{
					break;
				}
				
			case PILA2_FASE_3:
				//BLOQUE DE EJECUCIÓN PILA2_FASE_4: Se ejecuta si ya se haya realizado el registro/relación de novedades (Ejecución satisfactoria del bloque PILA_2_FASE_3)
				//if(isFaseExitosa(FasePila2Enum.PILA2_FASE_3, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), true)){
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_3, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					finalizarPlanillaAdicionCorreccion(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
				} else{
					break;
				}
				
			default:
				break;
		}
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}

	/**
	 * Método encargado de procesar las planillas de corrección N que cuentan con planilla original
	 * 
	 * @param planillaManual
	 * 			la planilla de corrección a ser procesada.
	 */
	private void procesarArchivoCorreccionConOriginal(PlanillaGestionManualDTO planillaManual, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "procesarArchivoCorreccionConOriginal(PlanillaGestionManualDTO, UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		//Se consulta el contenido de la planilla original y se agrupan los registros 
		//copiarAgruparPlanillaAutomatico(planillaManual.getIdPlanillaOriginal(), planillaManual.getIdIndicePlanilla());
		ResultadoValidacionRegistrosAdicionCorrecionDTO resultadoEjecucionFase1Simulada = solicitarEjecucionFase1SimuladaCorreccionAutomatica(null, planillaManual.getIdIndicePlanilla());
		Long idRegistroGeneral = buscarIdRegistroGeneralPorPlanilla(planillaManual.getNumeroPlanilla());
        if(idRegistroGeneral != null){
		//En caso de que retorne SIN_PARAMETRO se asume que aún no ha terminado de procesar el bloque PILA2_FASE_1 pasando a procesar los registros de corrección 
		if(!isFaseExitosa(FasePila2Enum.PILA2_FASE_1, idRegistroGeneral, false)){
			//Se solicita ejecutar la fase 1 simulada correccion automatica
			//resultadoEjecucionFase1Simulada = solicitarEjecucionFase1SimuladaCorreccionAutomatica(planillaManual.getIdPlanillaOriginal(), planillaManual.getIdIndicePlanilla());
			ejecutarPila2Fase1PlanillaN(resultadoEjecucionFase1Simulada, planillaManual.getIdPlanillaOriginal(), planillaManual.getIdIndicePlanilla());
		}
		//else{
		//	resultadoEjecucionFase1Simulada = solicitarConsultaResultadoFase1SimuladaCorrecion(planillaManual.getIdPlanillaOriginal(), planillaManual.getIdIndicePlanilla());
		//}
		
		Long idTransaccion = resultadoEjecucionFase1Simulada.getIdTransaccion(); 
		FasePila2Enum fase = definirBloqueAProcesar(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
        
		switch (fase) {
			case SIN_PARAMETRO:	
			case PILA2_FASE_1:
				//BLOQUE DE EJECUCIÓN PILA2_FASE_2: se ejecuta si ya se haya hecho la validación respecto a la base de datos (Ejecución satisfactoria del bloque PILA_2_FASE_1) 
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_1, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase2PlanillaN(planillaManual.getIdIndicePlanilla(), planillaManual.getIdPlanillaOriginal(), resultadoEjecucionFase1Simulada.getResultados(), resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), idTransaccion, user);
				} else{
					break;
				}
				
			case PILA2_FASE_2:	
				//BLOQUE DE EJECUCIÓN PILA2_FASE_3: Se ejecuta si ya se haya realizado el registro/relación de aportes (Ejecución satisfactoria del bloque PILA_2_FASE_2)
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase3PlanillaN(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), resultadoEjecucionFase1Simulada.getResultados(), user);
				} else{
					break;
				}
				
			case PILA2_FASE_3:
            
				//BLOQUE DE EJECUCIÓN PILA2_FASE_4: Se ejecuta si ya se haya realizado el registro/relación de novedades (Ejecución satisfactoria del bloque PILA_2_FASE_3) 
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_3, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					finalizarPlanillaAdicionCorreccion(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
				} else{
					break;
				}
			default:
				break;
		}
    }
    else{
        logger.error("El registro A de la planilla N y la original no coinsiden o tienen alguna inconsistencia, planilla con numero:"+planillaManual.getNumeroPlanilla());
    }
		
		long timeEnd = System.nanoTime();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}

	/**
     * Método encargado de consultar el id del registro general, asociado a una planilla en staging.
     * 
     * @param numeroPlanilla
     * 			numero de la planilla a consultar.
     * 
     * @return Long con el id en tabla del registro general asociado a la planilla. Null en caso de no encontrar coincidencias.
     */
	private Long buscarIdRegistroGeneralPorPlanilla(String numeroPlanilla) {
		String firma = "buscarIdRegistroGeneralPorPlanilla(String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		ConsultarIdRegistroGeneralPorNumeroPlanilla consultarIdRegistroGeneralPorPlanilla = new ConsultarIdRegistroGeneralPorNumeroPlanilla(numeroPlanilla);
		consultarIdRegistroGeneralPorPlanilla.execute();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		return consultarIdRegistroGeneralPorPlanilla.getResult();
	}

	/**
	 * Método encargado de procesar las planillas de corrección N que no cuentan con planilla original.
	 * 
	 * @param planillaManual
	 * 			la planilla de corrección a ser procesada.
	 */
	private void procesarArchivoCorreccionSinOriginal(PlanillaGestionManualDTO planillaManual, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "procesarArchivoCorreccionSinOriginal(PlanillaGestionManualDTO, UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		//Se consulta contenido de la planilla original, se agrupan los registros A y C de forma automatica
		//llamado a pila2 llamado #1 SIN_PARAMETRO
		//copiarAgruparPlanillaAutomatico(null, planillaManual.getIdIndicePlanilla());
		
		//Se solicita ejecutar la fase 1 simulada correccion automatica
		//llamado a pila2 llamado #2 PILA2_FASE_1 para ejecución validación integral
		ResultadoValidacionRegistrosAdicionCorrecionDTO resultadoEjecucionFase1Simulada = solicitarEjecucionFase1SimuladaCorreccionAutomatica(null, planillaManual.getIdIndicePlanilla());
		Long idTransaccion = resultadoEjecucionFase1Simulada.getIdTransaccion();
        if(idTransaccion != null){
		
		//logger.info("EL RESULTADO DE LA EJECUCIÓN EN FASE 1 SIMULADA ES: " + resultadoEjecucionFase1Simulada.toString());
		FasePila2Enum fase = definirBloqueAProcesar(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
		
		logger.info("NumeroPlanilla = " + planillaManual.getNumeroPlanilla());
		logger.info("Id trnsacción es = " + idTransaccion);
		logger.info("fase = " + fase);
		
		switch (fase) {
			case SIN_PARAMETRO:
				//En caso de que retorne SIN_PARAMETRO se asume que aún no ha terminado de procesar el bloque PILA2_FASE_1 pasando a procesar los registros de corrección 
				if(!isFaseExitosa(FasePila2Enum.PILA2_FASE_1, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					logger.info("ejecutarPila2Fase1PlanillaN = " + planillaManual.getNumeroPlanilla());
					ejecutarPila2Fase1PlanillaN(resultadoEjecucionFase1Simulada, null, planillaManual.getIdIndicePlanilla());
				}
			
			case PILA2_FASE_1:
				//llamado a pila2 llamado #3 PILA2_FASE_2 
				//BLOQUE DE EJECUCIÓN PILA2_FASE_2: se ejecuta si ya se ha hecho el registro de las correcciones (Ejecución satisfactoria del bloque PILA_2_FASE_1) 
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_1, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase2PlanillaN(planillaManual.getIdIndicePlanilla(), null, resultadoEjecucionFase1Simulada.getResultados(), resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), idTransaccion, user);
				} else{
					break;
				}
				
			case PILA2_FASE_2:	
				//BLOQUE DE EJECUCIÓN PILA2_FASE_3: Se ejecuta si ya se haya realizado el registro/relación de aportes (Ejecución satisfactoria del bloque PILA_2_FASE_2)
				//if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), true)){
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					ejecutarPila2Fase3PlanillaN(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), resultadoEjecucionFase1Simulada.getResultados(), user);
				} else{
					break;
				}
				
			case PILA2_FASE_3:
				//BLOQUE DE EJECUCIÓN PILA2_FASE_4: Se ejecuta si ya se haya realizado el registro/relación de novedades (Ejecución satisfactoria del bloque PILA_2_FASE_3)
				//if(isFaseExitosa(FasePila2Enum.PILA2_FASE_3, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), true)){
				if(isFaseExitosa(FasePila2Enum.PILA2_FASE_3, resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), false)){
					finalizarPlanillaAdicionCorreccion(resultadoEjecucionFase1Simulada.getIdRegGeneralAdicionCorreccion(), user);
				} else{
					break;
				}
			default:
				break;
		}
    }else{
        logger.error("No se genero registro general por ser una planilla N sin original"+planillaManual.getNumeroPlanilla());
    }
		long timeEnd = System.nanoTime();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	/**
	 * Método encargado de procesar las planillas reporceso.
	 * 
	 * @param planillaManual
	 * 			la planilla de corrección a ser procesada.
	 */
	private void procesarArchivosReproceso(PlanillaGestionManualDTO planillaManual, UserDTO user) {
		String firma = "procesarArchivosReproceso(PlanillaGestionManualDTO, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		PlanillaGestionManualDTO planillaGestionManualDTO = new PlanillaGestionManualDTO();
		planillaGestionManualDTO.setIdIndicePlanilla(planillaManual.getIdIndicePlanilla());
		marcarNoProcesamientoArchivoRAutomatico(planillaGestionManualDTO);
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
	}

	/**
     * Método encargado de invocar el servicio.
     */
    private void marcarNoProcesamientoArchivoRAutomatico(PlanillaGestionManualDTO planillaGestionManualDTO) {
        String firma = "marcarNoProcesamientoArchivoRAutomatico(PlanillaGestionManualDTO)";
    	logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        MarcarNoProcesamientoArchivoR simularService = new MarcarNoProcesamientoArchivoR(planillaGestionManualDTO);
        simularService.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    }
    
    /**
     * Método encargado de saber el contenido de la planilla original y generar la agrupacion
     */
    private void copiarAgruparPlanillaAutomatico(Long idPlanillaOriginal, Long idPlanillaCorreccion) {
        String firma = "consultarContenidoPlanillaOriginalAutomatico(Long, Long)";
    	logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

    	CopiarAgruparPlanilla simularService = new CopiarAgruparPlanilla(idPlanillaOriginal, idPlanillaCorreccion);
        simularService.execute();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    }
    
    /**
     * Método encargado de ejecutar la Fase1 simulada correccion automatica
     */
    private ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaCorreccionAutomatica(Long idPlanillaOriginal,
            Long idPlanillaCorreccion) {
    	String firma = "solicitarEjecucionFase1SimuladaCorreccionAutomatica(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        
        SolicitarEjecucionFase1SimuladaCorreccion simularService = new SolicitarEjecucionFase1SimuladaCorreccion(idPlanillaOriginal, idPlanillaCorreccion);
        simularService.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return simularService.getResult();
    }
    
    private ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarConsultaResultadoFase1SimuladaCorrecion(Long idPlanillaOriginal,
            Long idPlanillaCorreccion){
    	String firma = "solicitarConsultaResultadoFase1SimuladaCorrecion(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
    	
    	ConsultarResultadoFase1SimuladaCorrecion consultarService = new ConsultarResultadoFase1SimuladaCorrecion(idPlanillaOriginal, idPlanillaCorreccion);
    	consultarService.execute();
    	
    	logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    	return consultarService.getResult();
    }
    
    /**
     * Método encargado de ejecutar el registro de correccion
     */
    private void registrarNoRegistrarCorreccionAutomatico(List<RegistrarCorreccionAdicionDTO> registrosCCorreccion) {
        String firma = "registrarNoRegistrarCorreccionAutomatico(List<RegistrarCorreccionAdicionDTO>)";
    	logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
                
        RegistrarNoRegistrarCorreccionesEnBloque simularService = new RegistrarNoRegistrarCorreccionesEnBloque(registrosCCorreccion);
        simularService.execute();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    }

	private String verificarEstadoRegistrosAdicionCorreccion(Long idRegistroGeneral,
			FasePila2Enum fase, boolean actualizarEstado) {
		String firma = "verificarEstadoRegistrosAdicionCorreccion(Long, FasePila2Enum, boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		//VerificarEstadoRegistrosAdicionCorreccion estadoRegistros = new VerificarEstadoRegistrosAdicionCorreccion(actualizarEstado, idRegistroGeneral, fase);
        
		ConsultarEstadoRegistrosAdicionCorreccion  estadoRegistros = new ConsultarEstadoRegistrosAdicionCorreccion(idRegistroGeneral, fase);
		estadoRegistros.execute();

        String resultadoValidacionEstado = estadoRegistros.getResult().get(ConstantesParaMensajes.RESULTADO_VALIDACION); 
        
        logger.debug("la planilla con registro general = " + idRegistroGeneral + ", en la fase = " + fase.name() + " tiene el estado " + resultadoValidacionEstado);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return resultadoValidacionEstado;
	}
	
	/**
     * Método encargado de invocar el servicio que ejecuta los USP encargados de la fase 1 de pila 2 en modo simulado. 
     * Esta capacidad retorna el DTO con la información que se presenta para evaluar la simulación de los registros de adición.
     * 
     * @param idIndicePlanilla
     *        ID de la entrada de índice de planilla para la cual se ejecuta la simulación
     *        
     * @return <b>ResultadoValidacionRegistrosAdicionCorrecionDTO</b>
     *         DTO con el listado del resultado de procesamiento de planilla de adición para Fase 1
     */
	private ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaAdicionAutomatico(
			Long idIndicePlanilla) {
		String firma = "solicitarEjecucionFase1SimuladaAdicionAutomatico(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma + " con Parametro " + idIndicePlanilla);
		SolicitarEjecucionFase1SimuladaAdicion solicitarEjecucionFase1SimuladaAdicion = new SolicitarEjecucionFase1SimuladaAdicion(idIndicePlanilla);
		solicitarEjecucionFase1SimuladaAdicion.execute();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		return solicitarEjecucionFase1SimuladaAdicion.getResult();
	}
	
	
	/**
	 * Capacidad encargada de consumir el servicio en PilaService que define el registro o el rechazo de una entrada de adición
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			id del registro general asociado a la planilla
	 * 
	 * @param idIndicePlanilla
	 * 			identifcador del registro en indice planilla
	 */
	private void registrarNoRegistrarAdicionAutomatico(Long idRegGeneralAdicionCorreccion, Long idIndicePlanilla) {
		String firma = "registrarNoRegistrarAdicionAutomatico(Long, Long)";
		
		long timeStart = System.nanoTime();
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<RegistrarCorreccionAdicionDTO> registrosAdicion = new ArrayList<>();
		
		ConsultarResultadoProcesamientoPlanillaAdicion consultarResultadosValidacion = new ConsultarResultadoProcesamientoPlanillaAdicion(idIndicePlanilla);
		consultarResultadosValidacion.execute();
		ResultadoValidacionRegistrosAdicionCorrecionDTO resultadosValidacion = consultarResultadosValidacion.getResult();
		
		//for (ConjuntoResultadoRegistroCorreccionADTO resultado : resultadosValidacion.getResultados()) {
		//	logger.debug("" + resultado.getRegistradoFase1());
		//	if(resultado.getRegistradoFase1() != null){
		//		registrosAdicion.add(new RegistrarCorreccionAdicionDTO(null, resultado.getIdRegDetA(),
		//	    		null, idRegGeneralAdicionCorreccion, null, resultado.getRegistradoFase1()));
		//	}
		//	else{
		//		registrosAdicion.add(new RegistrarCorreccionAdicionDTO(null, resultado.getIdRegDetA(),
		//	    		null, idRegGeneralAdicionCorreccion, null, true));
		//	}
		//}
		
		RegistrarNoRegistrarAdicionEnBloque registrarNoRegistrarAdicionEnBloque = new RegistrarNoRegistrarAdicionEnBloque(registrosAdicion);
		registrarNoRegistrarAdicionEnBloque.execute();
		
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	private void ejecutarPila2Fase2PlanillaAdicion(Long idIndicePlanilla, Long idRegGeneralAdicionCorreccion,
			List<ConjuntoResultadoRegistroCorreccionCDTO> resultados, Long idTransaccion, UserDTO user) {
		long timeStart = System.nanoTime(); 
		String firma = "ejecutarPila2Fase2PlanillaAdicion(Long, Long, List<ConjuntoResultadoRegistroCorreccionCDTO>, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		registrarRelacionarAportesPlanilla(idIndicePlanilla, null, idRegGeneralAdicionCorreccion, idTransaccion, user);
		
		List<RegistrarCorreccionAdicionDTO> registrosSimulacion = new ArrayList<>();
		
		for (ConjuntoResultadoRegistroCorreccionCDTO resultado : resultados) {
			RegistrarCorreccionAdicionDTO datosSimulacion = new RegistrarCorreccionAdicionDTO(null,resultado.getIdRegDetC(),null,idRegGeneralAdicionCorreccion,null,null);
			datosSimulacion.setIdIndicePlanillaCorreccion(idIndicePlanilla);
			registrosSimulacion.add(datosSimulacion);
		}
		
		AprobarBloqueAportesAdicion aprobarBloqueAportesAdicion = new AprobarBloqueAportesAdicion(registrosSimulacion);
		aprobarBloqueAportesAdicion.execute();
		
		if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, idRegGeneralAdicionCorreccion, true)){
			reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.PILA2_FASE_3);
			reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.FINALIZAR_TRANSACCION);
			ComprobarExistenciaNovedades comprobarExistenciaNovedades = new ComprobarExistenciaNovedades(idRegGeneralAdicionCorreccion);
			comprobarExistenciaNovedades.execute();
		}
		else {
        	reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.FINALIZAR_TRANSACCION);
        }
		
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}

	private void ejecutarPila2Fase3PlanillaAdicion(List<ConjuntoResultadoRegistroCorreccionCDTO> resultados, Long idRegistroGeneralAdicionCorreccion, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "ejecutarPila2Fase3PlanillaAdicion(List<ConjuntoResultadoRegistroCorreccionADTO>, Long, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<Long> idsRegDetCorA = new ArrayList<>();
		
		for (ConjuntoResultadoRegistroCorreccionCDTO resultado : resultados) {
			idsRegDetCorA.add(resultado.getIdRegDetC());
		}
		
		AprobarBloqueNovedadesAdicionCorreccionCompuesto aprobacionBloqueProcesoNovedades = new AprobarBloqueNovedadesAdicionCorreccionCompuesto(idRegistroGeneralAdicionCorreccion, idsRegDetCorA);
		aprobacionBloqueProcesoNovedades.execute();
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	/**
	 * Método encargado de consultar si la fase dada fue exitosa y se puede continuar
	 * 
	 * @param fase
	 * 			la fase a verificar.
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			
	 * @param actualizarEstado
	 * 			indica si se debe actualizar o no el estado de ejecución de la planilla.
	 * 
	 * @return boolean 
	 * 				true si la planilla completó satisfactoriamente la fase dada, false en caso contrario
	 */
	private boolean isFaseExitosa(FasePila2Enum fase, Long idRegGeneralAdicionCorreccion, Boolean actualizarEstado) {
		String firma = "isFaseExitosa(FasePila2Enum, Long, Boolean)";
		logger.debug(ConstantesComunes.EJECUCION_LOGGER + firma + " para el idRegGeneralAdicionCorreccion " + idRegGeneralAdicionCorreccion + ", en la fase " + fase.name());
		return ConstantesParaMensajes.CONTINUAR.equals(verificarEstadoRegistrosAdicionCorreccion(idRegGeneralAdicionCorreccion, fase, actualizarEstado));
	}
	
	/**
	 * Método encargado de consumir el servicio en pilaService que identifica los procesos que se han ejecutado 
	 * sobre la planilla e indica con cual se debe continuar.
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			identificador del registro general.
	 * 
	 * @return FasePila2Enum 
	 * 				con la fase en la que se debe continuar. si retorna FasePila2Enum.SIN_PARAMETRO quiere decir
	 * 				que aún no ha terminado la fase 1 de pila mundo 2.
	 */
	private FasePila2Enum definirBloqueAProcesar(Long idRegGeneralAdicionCorreccion, UserDTO user) {
		String firma = "definirBloqueAProcesar(Long, UserDTO)"; 
		logger.info(ConstantesComunes.INICIO_LOGGER + firma + " con idRegGeneralAdicionCorreccion = " + idRegGeneralAdicionCorreccion);
		DefinirBloqueAProcesarPilaMundoDos bloqueAProcesar = new DefinirBloqueAProcesarPilaMundoDos(idRegGeneralAdicionCorreccion);
		bloqueAProcesar.execute();
		
		FasePila2Enum fase = bloqueAProcesar.getResult();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + " con fase " + fase.name());
		return fase;
	}

	
	/**
	 * Método encargado de ejecutar los procesos asociados a la fase 1 de pila mundo 2 para 
	 * las planillas N con/sin Original (validación vs DB)
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			identificador del registro general de adición corrección.
	 * 
	 * @param idPlanillaOriginal
	 * 			identificador de la planilla original.
	 * 
	 * @param idIndicePlanilla
	 * 			identificador de la planilla de corrección.
	 */
	private void ejecutarPila2Fase1PlanillaN(ResultadoValidacionRegistrosAdicionCorrecionDTO resultadosSimulacion, Long idPlanillaOriginal,  Long idIndicePlanilla) {
		long timeStart = System.nanoTime();
		String firma = "ejecutarPila2Fase1PlanillaN(Long ,Long, Long)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<RegistrarCorreccionAdicionDTO> registrosCCorreccion = new ArrayList<>();
		
		//se procede a gestionar los registros C de cada grupo.
		//if(resultadosSimulacion != null && resultadosSimulacion.getResultados() != null && !resultadosSimulacion.getResultados().isEmpty()) {
		//	
		//	for (ConjuntoResultadoRegistroCorreccionDTO resultado : resultadosSimulacion.getResultados()) {
		//		if(resultado.getResultadosC() != null) {
		//			for(ConjuntoResultadoRegistroCorreccionCDTO resultadoC : resultado.getResultadosC()) {	
		//				
		//				if(resultadoC.getRegistradoFase1() != null){
		//					RegistrarCorreccionAdicionDTO registroCCorreccion = new RegistrarCorreccionAdicionDTO(resultado.getIdRegDetOriginal(), resultado.getIdRegDetA(), resultadoC.getIdRegDetC(), resultadosSimulacion.getIdRegGeneralAdicionCorreccion(), null, resultadoC.getRegistradoFase1());
		//					registrosCCorreccion.add(registroCCorreccion);
		//				}
		//				else{
		//					RegistrarCorreccionAdicionDTO registroCCorreccion = new RegistrarCorreccionAdicionDTO(resultado.getIdRegDetOriginal(), resultado.getIdRegDetA(), resultadoC.getIdRegDetC(), resultadosSimulacion.getIdRegGeneralAdicionCorreccion(), null, true);
		//					registrosCCorreccion.add(registroCCorreccion);
		//				}
		//			}
		//		}
		//	}
		//}
		
		//registrarNoRegistrarCorreccionAutomatico(registrosCCorreccion);
		
		long timeEnd = System.nanoTime();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	/**
	 * Método encargado de ejecutar los procesos asociados a la fase 2 de pila mundo 2 para 
	 * planilla N con/sin original (registro / relación de aportes)
	 * 
	 * @param idIndicePlanilla
	 * 			identificador del indice de planilla asociada a la planilla manual.
	 * 
	 * @param idPlanillaOriginal
	 * 			identificador de la planilla original asociada a la planilla manual.
	 * 
	 * @param resultados
	 * 			listado con los resultados de la ejecución de la fase 1 simulada.
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			identificador del registro general de adición corrección.
	 * 
	 * @param user
	 * 			usuario que ejecuta la acciòn.
	 */
	private void ejecutarPila2Fase2PlanillaN(Long idIndicePlanilla, Long idPlanillaOriginal,
			List<ConjuntoResultadoRegistroCorreccionCDTO> resultados, Long idRegGeneralAdicionCorreccion, Long idTransaccion, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "ejecutarPila2Fase2PlanillaN(Long, Long, List<ConjuntoResultadoRegistroCorreccionADTO>, Long, UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		
		//Se solicita registrar la relación de aportes en la planilla
		registrarRelacionarAportesPlanilla(idIndicePlanilla, idPlanillaOriginal, idRegGeneralAdicionCorreccion, idTransaccion, user);

		List<RegistrarCorreccionAdicionDTO> listaRegistrosCorreccion;
		//se procede a gestionar los registros C de cada grupo.
		if(resultados != null) {
			for (ConjuntoResultadoRegistroCorreccionCDTO resultado : resultados) {
				if(resultado != null) {
					listaRegistrosCorreccion = new ArrayList<>();
					
						RegistrarCorreccionAdicionDTO registroCCorreccion = new RegistrarCorreccionAdicionDTO(null, null, resultado.getIdRegDetC(), idRegGeneralAdicionCorreccion, null, null);
						
						if(idPlanillaOriginal != null){
                                                        registroCCorreccion.setIdRegistroDetalladoOriginal(resultado.getIdRegDetC());
							//registroCCorreccion.setIdRegistroDetalladoCorreccionA(resultado.getIdRegDetA());
						}
						registroCCorreccion.setIdIndicePlanillaCorreccion(idIndicePlanilla);
						
						listaRegistrosCorreccion.add(registroCCorreccion);
					
					registrarAporteSimuladoCorreccionEnBloque(listaRegistrosCorreccion, user);
				}
			}
		}
		
		logger.info(firma + " - idIndicePlanilla " + idIndicePlanilla + " - idPlanillaOriginal " + idPlanillaOriginal + " - idRegGeneralAdicionCorreccion " + idRegGeneralAdicionCorreccion + " - idTransaccion " + idTransaccion);
		if(isFaseExitosa(FasePila2Enum.PILA2_FASE_2, idRegGeneralAdicionCorreccion, true)){
			logger.info(firma + " if isFaseExitosa(FasePila2Enum.PILA2_FASE_2, idRegGeneralAdicionCorreccion, true) ");
			reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.PILA2_FASE_3);
			reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.FINALIZAR_TRANSACCION);
		}
		else {
			logger.info(firma + " else isFaseExitosa(FasePila2Enum.PILA2_FASE_2, idRegGeneralAdicionCorreccion, true) ");
        	reprocesarPlanillaSimulada410(idIndicePlanilla, idTransaccion, true, user != null ? user.getNombreUsuario() : "SISTEMA", FasePila2Enum.FINALIZAR_TRANSACCION);
        }
		
		long timeEnd = System.nanoTime();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}

    private void registrarAporteSimuladoCorreccionEnBloque(List<RegistrarCorreccionAdicionDTO> listaCriteriosSimulacion,
            UserDTO userDTO) {
        String firmaServicio = "PilaCompositeBusiness.registrarAporteSimuladoCorreccionEnBloque(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        logger.debug("al servicio " + firmaServicio + " el listado de registros de corrección C enviado es: " + listaCriteriosSimulacion.toString());
        
    	AprobarCambioAportesCorreccionesEnBloque aprobarCambioAportesCorrecciones = new AprobarCambioAportesCorreccionesEnBloque(listaCriteriosSimulacion);
        aprobarCambioAportesCorrecciones.execute();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
	
	
	/**
	 * Método encargado de ejecutar los procesos asociados a la fase 3 de pila mundo 2 para 
	 * planilla N con/sin original(registro/relación de novedades)
	 * 
	 * @param idRegGeneralAdicionCorreccion
	 * 			identificador del registro general de adición corrección.
	 * 
	 * @param resultadosRegistrosCorreccion
	 * 			listado con los resultados de la ejecución de la fase 1 simulada.
	 * 
	 * @param user
	 * 			usuario que ejecuta la acción.
	 */
	private void ejecutarPila2Fase3PlanillaN(Long idRegGeneralAdicionCorreccion,
			List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosRegistrosCorreccion, UserDTO user) {
		long timeStart = System.nanoTime();
		String firma = "ejecutarPila2Fase3PlanillaN(Long, List<ConjuntoResultadoRegistroCorreccionADTO>, UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		
		//logger.info("el conjunto de resultados para los registros de corrección son: " + resultadosRegistrosCorreccion.toString());
		
		if(resultadosRegistrosCorreccion != null && !resultadosRegistrosCorreccion.isEmpty()){
			
			List<Long> idsRegistrosDetalladosCorreccionC = new ArrayList<>();

			resultadosRegistrosCorreccion.forEach(resultado -> {				
			
					idsRegistrosDetalladosCorreccionC.add(resultado.getIdRegDetC());
				
			});
			
			AprobarBloqueNovedadesAdicionCorreccionCompuesto aprobacionBloqueProcesoNovedades = new AprobarBloqueNovedadesAdicionCorreccionCompuesto(idRegGeneralAdicionCorreccion, idsRegistrosDetalladosCorreccionC);
			aprobacionBloqueProcesoNovedades.execute();
		}
		long timeEnd = System.nanoTime();
		logger.info(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	
	/**
	 * metodo que se encarga de verificar y reemplazar aportantes en lista blanca
	 * @param indicesPlanilla
	 * @return
	 */
	private List<PlanillaGestionManualDTO> verificarListaBlancaAportantes(List<PlanillaGestionManualDTO> indicesPlanilla) {
		List <PersonaModeloDTO> personasABuscar = new ArrayList<>();
		for(PlanillaGestionManualDTO planilla : indicesPlanilla) {
			if(planilla.getTipoIdAportante()!=null && planilla.getNumeroIdAportante()!= null) {
				PersonaModeloDTO personaBuscar = new PersonaModeloDTO();
				personaBuscar.setTipoIdentificacion(planilla.getTipoIdAportante());
				personaBuscar.setNumeroIdentificacion(planilla.getNumeroIdAportante());
				personasABuscar.add(personaBuscar);
			}
		}
		
		ConsultarListaBlancaAportantes consultarListaBlancaAportantes = new ConsultarListaBlancaAportantes(personasABuscar);
		consultarListaBlancaAportantes.execute();
		List<ListasBlancasAportantes> listaBlanca = consultarListaBlancaAportantes.getResult();
		
		Map<String, ListasBlancasAportantes> listablancaMap = new HashMap<String, ListasBlancasAportantes>(); 
		if(!listaBlanca.isEmpty()) {
			for (ListasBlancasAportantes l : listaBlanca) {
				listablancaMap.put(l.getNumeroIdentificacionPlanilla(), l);
			}
		}
		
		ListasBlancasAportantes l;
		for(PlanillaGestionManualDTO planilla : indicesPlanilla) {
			if(planilla.getTipoIdAportante()!=null && planilla.getNumeroIdAportante()!= null) {
				if(listablancaMap.containsKey(planilla.getNumeroIdAportante())) {
					l = listablancaMap.get(planilla.getNumeroIdAportante());
					planilla.setTipoIdAportante(l.getTipoIdentificacionEmpleador());
					planilla.setNumeroIdAportante(l.getNumeroIdentificacionEmpleador());
				}
			}
		}
		
		return indicesPlanilla;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cargarArchivosPilaFtp(Long idOperadorInformacion, UserDTO userDTO) throws Exception {
		String firma = "CargarArchivosPilaFtp(UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma);
		
		String usuarioCarga = userDTO.getNombreUsuario();
        if (usuarioCarga == null) {
            usuarioCarga = ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO;
        }
        
        Long idProceso = instanciarProceso(usuarioCarga, TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA);
        TipoCargaArchivoEnum tipoCarga = TipoCargaArchivoEnum.AUTOMATICA_BATCH;
    	Boolean ejecutarB0 = true;
        
		try {
			List<ArchivoPilaDTO> archivosDescargados;
			List<ConexionOperadorInformacion> conexionesOperadores = consultarDatosConexionOperadorInformacion(idOperadorInformacion);
			if (conexionesOperadores != null) {
	            for (ConexionOperadorInformacion conexionOperadorInformacion : conexionesOperadores) {
	            	
	            	ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP = prepararConexionFtp(conexionOperadorInformacion);
	                conexionFTP.conectarRecorrerNoDescargar();
	                archivosDescargados = filtrarExistentes(conexionFTP.getArchivosDescargados(), 0);
	                
	                if(!archivosDescargados.isEmpty()){
	                	List<ArchivoPilaDTO> archivosADescargar = new ArrayList<ArchivoPilaDTO>();
	                	for(ArchivoPilaDTO archivo : archivosDescargados) {
	                		archivosADescargar.add(archivo);
	                		if(archivosADescargar.size() == 20) {
	                			cargarArchivosParalelo(descargarFTPyEnviarAlRepositorio(conexionFTP, archivosADescargar), tipoCarga, idProceso, ejecutarB0);
	                			archivosADescargar = new ArrayList<ArchivoPilaDTO>();
	                		}
	                	}
	                	
	                	if(archivosADescargar.size() > 0) {
	                		cargarArchivosParalelo(descargarFTPyEnviarAlRepositorio(conexionFTP, archivosADescargar), tipoCarga, idProceso, ejecutarB0);
                			archivosADescargar = new ArrayList<ArchivoPilaDTO>();
                		}
	                }
	            }
			}
		} catch (Exception e) {
            // se captura el error técnico para marcar la finalización fallida del proceso
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_FALLIDO);

            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
            // inmediatamente, se lanza de nuevo la misma excepción para que sea presentada en pantalla
            throw e;
		}

        if (idProceso != null) {
            finalizarProceso(idProceso, EstadoProcesoValidacionEnum.PROCESO_EXITOSO);
        }
        
		logger.info(ConstantesComunes.FIN_LOGGER + firma);
	}
	
	/** 
	 * envia de manera paralela los archovos al gestor de documentos por medio del micro servicio de archivos
	 * @param conexionFTP
	 * @param archivosADescargar
	 * @return
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	private List<ArchivoPilaDTO> descargarFTPyEnviarAlRepositorio(
			ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP, List<ArchivoPilaDTO> archivosADescargar)
			throws InterruptedException, ExecutionException {
		String firmaMetodo = "PilaCompositeBusiness.descargarFTPyEnviarAlRepositorio(ConexionServidorFTPUtil<ArchivoPilaDTO>, List<ArchivoPilaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
		conexionFTP.setArchivosDescargados(archivosADescargar);
        conexionFTP.conectarYDescargar();
        
        // se prepara la carga paralela en el servicio de archivos
        List<Callable<ArchivoPilaDTO>> tareasParalelas = new LinkedList<>();
        for (ArchivoPilaDTO archivoPilaDTO : conexionFTP.getArchivosDescargados()) {
            Callable<ArchivoPilaDTO> parallelTaskArchivos = () -> {
                return almacenarArchivo(archivoPilaDTO);
            };
            tareasParalelas.add(parallelTaskArchivos);
        }
        
        List<ArchivoPilaDTO> resultado = new ArrayList<>();
        List<Future<ArchivoPilaDTO>> resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);	 
		for (Future<ArchivoPilaDTO> future : resultadosFuturos) {
            resultado.add(future.get());
        }
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
	}
	
	private Long instanciarProceso(String usuarioProceso, TipoProcesoPilaEnum tipoProceso) {
		InstanciarProceso isp = new InstanciarProceso(usuarioProceso, tipoProceso);
		isp.execute();
		return isp.getResult();
	}
	
	private void finalizarProceso(Long idProceso, EstadoProcesoValidacionEnum estadoProceso) {
		FinalizarProceso fp = new FinalizarProceso(estadoProceso, idProceso);
		fp.execute();
	}
	
	private List<ConexionOperadorInformacion> consultarDatosConexionOperadorInformacion(Long idOperadorInformacion) {
		ConsultarDatosConexionOperadorInformacion cop = new ConsultarDatosConexionOperadorInformacion(idOperadorInformacion);
		cop.execute();
		return cop.getResult();
	}
	
	/**
     * Método encargado de la preparación de una conexión con FTP
     * @return
     */
    private ConexionServidorFTPUtil<ArchivoPilaDTO> prepararConexionFtp(ConexionOperadorInformacion conexionOperadorInformacion) {
        String firmaMetodo = "ArchivosPILAEjecucion.prepararConexionFtp(ConexionOperadorInformacion)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP = new ConexionServidorFTPUtil<>("PILA", ArchivoPilaDTO.class);
        conexionFTP.setNombreHost(conexionOperadorInformacion.getHost());
        conexionFTP.setPuerto(conexionOperadorInformacion.getPuerto().toString());
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance().decrypt(conexionOperadorInformacion.getUsuario()));
        conexionFTP.setContrasena(DesEncrypter.getInstance().decrypt(conexionOperadorInformacion.getContrasena()));
        conexionFTP.setUrlArchivos(conexionOperadorInformacion.getUrl());
        conexionFTP.setProtocolo(conexionOperadorInformacion.getProtocolo());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return conexionFTP;
    }
    
    // TODO: CAMBIAR CONSULTA QUE USA JSON
    private List<ArchivoPilaDTO> filtrarExistentes(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos) {
    	FiltrarExistentes fe = new FiltrarExistentes(tipoArchivos, archivosFTP);
    	fe.execute();
    	return fe.getResult();
    }
    
    /**
     * Método para almacenar un archivo PILA en el ECM
     * 
     * @param archivoCarga
     *        DTO que contiene la información del archivo a almacenar
     * @return ArchivoPilaDTO
     *         DTO de entrada con el ID de documento de ECM
     */
    private ArchivoPilaDTO almacenarArchivo(ArchivoPilaDTO archivoCarga) {
        String firmaMetodo = "ProcesosDescarga.almacenarArchivo(ArchivoPilaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se inicializa el DTO de entrada
        InformacionArchivoDTO archivoDTOEntrada = new InformacionArchivoDTO();
        archivoDTOEntrada.setDocName(archivoCarga.getDocName());
        archivoDTOEntrada.setFileName(archivoCarga.getFileName());
        archivoDTOEntrada.setDataFile(archivoCarga.getDataFile());
        archivoDTOEntrada.setProcessName(archivoCarga.getProcessName());
        archivoDTOEntrada.setFileType(archivoCarga.getFileType());

        AlmacenarArchivo almacenarArchivoPilaService;
        almacenarArchivoPilaService = new AlmacenarArchivo(archivoDTOEntrada);

        // se define la salida
        InformacionArchivoDTO salida = null;

        almacenarArchivoPilaService.execute();

        salida = almacenarArchivoPilaService.getResult();

        if (salida != null) {
            archivoCarga.setIdentificadorDocumento(salida.getIdentificadorDocumento());
            archivoCarga.setVersionDocumento(salida.getVersionDocumento());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return archivoCarga;
    }
    
    private void cargarArchivosParalelo(List<ArchivoPilaDTO> archivosADescargar, TipoCargaArchivoEnum tipoCarga, Long idProceso, Boolean ejecutarB0) {
    	CargarArchivosParalelo cap = new CargarArchivosParalelo(ejecutarB0, idProceso, tipoCarga, archivosADescargar);
    	cap.execute();
    }

    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de Ordenamiento de sucursales.
     * @param idTransaccion
     *        id de la transacción.
     */
    private void organizarNovedadesSucursal(Long idTransaccion) {
        logger.info("Inicio de método organizarNovedadesSucursal(Long idTransaccion)" + idTransaccion);
        OrganizarNovedadesSucursal organizarNovedadesSucursal = new OrganizarNovedadesSucursal(idTransaccion);
        organizarNovedadesSucursal.execute();
        logger.info("Fin de método organizarNovedadesSucursal(Long idTransaccion)");
    }
    
}
