package com.asopagos.pila.service.ejb;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.archivos.util.ComprimidoUtil;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.AportePeriodoCertificadoDTO;
import com.asopagos.dto.DiasFestivosModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.EstadoArchivoPorBloqueModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.TemAporteModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.temporal.TemAportante;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.MotivoProcesoPilaManualEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IConsultasModeloCore;
import com.asopagos.pila.business.interfaces.IConsultasModeloPILA;
import com.asopagos.pila.business.interfaces.IConsultasModeloStaging;
import com.asopagos.pila.constants.ConstantesComunesPila;
import com.asopagos.pila.constants.ConstantesParaMensajes;
import com.asopagos.pila.constants.MensajesErrorComunesEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import com.asopagos.pila.dto.BloquesValidacionArchivoDTO;
import com.asopagos.pila.dto.CabeceraDetalleArchivoDTO;
import com.asopagos.pila.dto.CabeceraPestanaAporteNovedadDTO;
import com.asopagos.pila.dto.ComparacionRegistrosDTO;
import com.asopagos.pila.dto.ConjuntoResultadoRegistroCorreccionADTO;
import com.asopagos.pila.dto.ConjuntoResultadoRegistroCorreccionCDTO;
import com.asopagos.pila.dto.ConsultaNovedadesPorRegistroDTO;
import com.asopagos.pila.dto.ConsultasArchivosOperadorFinancieroDTO;
import com.asopagos.pila.dto.CriterioConsultaDTO;
import com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados;
import com.asopagos.pila.dto.DetalleAporteVista360DTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;
import com.asopagos.pila.dto.InformacionAporteAdicionDTO;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import com.asopagos.pila.dto.ResultadoFinalizacionPlanillaAsistidaDTO;
import com.asopagos.pila.dto.ResultadoSimulacionAporteDetalladoDTO;
import com.asopagos.pila.dto.ResultadoSimulacionNovedadDTO;
import com.asopagos.pila.dto.ResultadoValidacionRegistrosAdicionCorrecionDTO;
import com.asopagos.pila.service.PilaService;
import com.asopagos.pila.util.FuncionesUtilitarias;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;

import co.com.heinsohn.lion.common.util.CalendarUtil;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import com.asopagos.archivos.util.ExcelUtil;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import com.asopagos.pila.dto.DatosArchvioReporteDTO;

/**
 * <b>Descripcion:</b> Clase que contiene la implementacion del microservicio de PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-401, HU211--410. <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel </a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E. </a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago </a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda </a>
 * 
 */
@Stateless
public class PilaBusiness implements PilaService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PilaBusiness.class);

    /** Inject del EJB para consultas en modelo PILA */
    @Inject
    private IConsultasModeloPILA consultasPila;

    /** Inject del EJB para consultas en modelo Staging */
    @Inject
    private IConsultasModeloStaging consultasStaging;

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasCore;
    
    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosPendientesPorProcesarInformacion()
     */
    @Override
    public Long archivosPendientesPorProcesarInformacion() {
        logger.debug("Inicia archivosPendientesPorProcesarInformacion");

        Long result = consultasPila.archivosPendientesPorProcesarInformacion();

        logger.debug("Finaliza archivosPendientesPorProcesarInformacion");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosProcesoFinalizado()
     */
    @Override
    public Long archivosProcesoFinalizado() {
        logger.debug("Inicia archivosProcesoFinalizado");

        Long result = consultasPila.archivosProcesoFinalizado();

        logger.debug("Finaliza archivosProcesoFinalizado");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosInconsistentesOI()
     */
    @Override
    public Long archivosInconsistentesOI() {
        logger.debug("Inicia archivosInconsistentesOI");

        Long result = consultasPila.archivosInconsistentesOI();

        logger.debug("Finaliza archivosInconsistentesOI");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosBandejaGestionOI()
     */
    @Override
    public Long archivosBandejaGestionOI() {
        logger.debug("Inicia archivosBandejaGestionOI");

        Long result = consultasPila.archivosBandejaGestionOI();

        logger.debug("Finaliza archivosBandejaGestionOI");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosPendientesPorProcesarInformacionManualOI()
     */
    @Override
    public Long archivosPendientesPorProcesarInformacionManualOI() {
        logger.debug("Inicia archivosPendientesPorProcesarInformacionManualOI");

        Long result = consultasPila.archivosPendientesPorProcesarInformacionManualOI();

        logger.debug("Finaliza archivosPendientesPorProcesarInformacionManualOI");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosProcesoFinalizadoManual()
     */
    @Override
    public Long archivosProcesoFinalizadoManual() {
        logger.debug("Inicia archivosProcesoFinalizadoManual");

        Long result = consultasPila.archivosProcesoFinalizadoManual();

        logger.debug("Finaliza archivosProcesoFinalizadoManual");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosCargados()
     */
    @Override
    public Long archivosCargados() {
        logger.debug("Inicia archivosCargados");

        Long result = consultasPila.archivosCargados();

        logger.debug("Finaliza archivosCargados");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosEnProcesoControl()
     */
    @Override
    public Long archivosEnProcesoControl() {
        logger.debug("Inicia archivosCargados");

        Long result = consultasPila.archivosEnProcesoControl();

        logger.debug("Finaliza archivosCargados");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosProcesoFinalizadoControl()
     */
    @Override
    public Long archivosProcesoFinalizadoControl() {
        logger.debug("Inicia archivosProcesoFinalizadoControl");

        Long result = consultasPila.archivosProcesoFinalizadoControl();

        logger.debug("Finaliza archivosProcesoFinalizadoControl");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosInconsistentesControl()
     */
    @Override
    public Long archivosInconsistentesControl() {
        logger.debug("Inicia archivosInconsistentesControl");

        Long result = consultasPila.archivosInconsistentesControl();

        logger.debug("Finaliza archivosInconsistentesControl");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosEnGestionControl()
     */
    @Override
    public Long archivosEnGestionControl() {
        logger.debug("Inicia archivosEnGestionControl");

        Long result = consultasPila.archivosEnGestionControl();

        logger.debug("Finaliza archivosEnGestionControl");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosEnProcesoControl()
     */
    @Override
    public Long archivosEnProcesoManualControl() {
        logger.debug("Inicia archivosEnProcesoManualControl");

        Long result = consultasPila.archivosEnProcesoManualControl();

        logger.debug("Finaliza archivosEnProcesoManualControl");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosProcesoFinalizadoManualControl()
     */
    @Override
    public Long archivosProcesoFinalizadoManualControl() {
        logger.debug("Inicia archivosProcesoFinalizadoManualControl");

        Long result = consultasPila.archivosProcesoFinalizadoManualControl();

        logger.debug("Finaliza archivosProcesoFinalizadoManualControl");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#totalAportesRelacionados(java.lang.Long)
     */
    @Override
    public BigDecimal totalAportesRelacionados(Long idRegistroGeneral) {
        logger.debug("Inicia totalAportesRelacionados(Long idRegistroGeneral)");

        BigDecimal result = consultasCore.totalAportesRelacionados(idRegistroGeneral);

        logger.debug("Finaliza totalAportesRelacionados(Long idRegistroGeneral)");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#totalAportesRegistrados(java.lang.Long)
     */
    @Override
    public BigDecimal totalAportesRegistrados(Long idRegistroGeneral) {
        logger.debug("Inicia totalAportesRegistrados(Long idRegistroGeneral)");

        BigDecimal result = consultasCore.totalAportesRegistrados(idRegistroGeneral);

        logger.debug("Finaliza totalAportesRegistrados(Long idRegistroGeneral)");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarDetalleAportesPorPlanilla(java.lang.Long)
     */
    @Override
    public List<AporteDetalladoPlanillaDTO> consultarDetalleAportesPorPlanilla(Long idRegistroGeneral, UriInfo uri,
            HttpServletResponse response) {
        String firmaServicio = "PilaBusiness.consultarDetalleAportesPorPlanilla(Long idRegistroGeneral, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<AporteDetalladoPlanillaDTO> lstAporteDetalladoPlanillaDTO = null;
        
        // los parámetros "uri" y "response" determinan sí se hace consulta paginada o sencilla
        if (uri == null && response == null) {
            lstAporteDetalladoPlanillaDTO = consultasCore.detalleAportesPlanilla(idRegistroGeneral);
        }else{
            lstAporteDetalladoPlanillaDTO = consultasCore.detalleAportesPlanillaPaginada(idRegistroGeneral, uri, response);
        }
        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstAporteDetalladoPlanillaDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#detallePestanaNovedades(java.lang.Long)
     */
    @Override
    public List<DetallePestanaNovedadesDTO> consultarDetalleNovedadesPorPlanilla(Long idRegistroGeneral) {
        String firmaServicio = "PilaBusiness.consultarDetalleNovedadesPorPlanilla(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        // datos necesarios para construir el dto de respuesta
        List<DetallePestanaNovedadesDTO> result = new ArrayList<>();
        
        
        List<DetallePestanaNovedadesDTO> lstDetallePestanaNovedadesDTO = new ArrayList<>();
        RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneral(idRegistroGeneral);
        List<RegistroDetallado> lstRegistros = null;
        lstRegistros = consultasStaging.obtenerRegistroDetallado(idRegistroGeneral);
        
        // se listan los ID de registro detallado para obtener las novedades
        List<Long> idsRegDet = new ArrayList<>();
        for (RegistroDetallado registroDetallado : lstRegistros) {
            if(!idsRegDet.contains(registroDetallado.getId())){
                idsRegDet.add(registroDetallado.getId());
            }
        }

        // se obtienen las novedades
        result = consultasPila.obtenerTodasNovedades(idRegistroGeneral);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Metodo que establece el tipo de cotizante asociado a una novedad
     * @param novedades
     * @param tipoCotizante
     * @param esAportePensionados
     * @param parametrosIndependiente 
     * @return novedades lista con los datos actualizados
     * 
     */
    private List<DetallePestanaNovedadesDTO> establecerTipoCotizante(List<DetallePestanaNovedadesDTO> novedades, String tipoCotizante,
            Boolean esAportePensionados, String[] parametrosIndependiente) {
        List<DetallePestanaNovedadesDTO> lstDetallePestanaNovedadesDTO = new ArrayList<>();
        for (DetallePestanaNovedadesDTO detalle : novedades) {
            if (esAportePensionados) {
                detalle.setTipoCotizante(TipoAfiliadoEnum.PENSIONADO);
            }
            else {
                if (Arrays.asList(parametrosIndependiente).contains(tipoCotizante)) {
                    detalle.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
                }
                else {
                    detalle.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                }
            }
            lstDetallePestanaNovedadesDTO.add(detalle);
        }

        return lstDetallePestanaNovedadesDTO;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#archivosOFProcesadosFinalizados()
     */
    @Override
    public ConsultasArchivosOperadorFinancieroDTO consultasArchivosOperadorFinanciero() {
        logger.debug("Inicia archivosOFProcesadosFinalizados");

        ConsultasArchivosOperadorFinancieroDTO consultas = new ConsultasArchivosOperadorFinancieroDTO();

        // Fecha del inicio de los tiempos
        Date epochAsopagos = new GregorianCalendar(1900, 1, 1).getTime();

        // Archivos OI relacionados que tienen “Estado” igual a “Recaudo notificado” los últimos 7 dias,
        consultas.setNumArchivosOFProcesadosFinalizados(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PROCESADOS_FINALIZADOS, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OF que tienen "Estado" igual a "Estructura con inconsistencia"
        consultas.setNumArchivosOFConInconsistencias(consultasPila
                .consultarArchivosOperadorFinanciero(NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_CON_INCONSISTENCIAS, epochAsopagos));

        /*
         * Archivos OI relacionados que tienen estado igual a Pendiente por gestionar error en validación vs BD o
         * Pendiente por registro y relación de aportes se calcula desde el origen de los tiempos
         */
        consultas.setNumArchivosOFBandejasGestion(consultasPila
                .consultarArchivosOperadorFinanciero(NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_BANDEJAS_GESTION, epochAsopagos));

        // Archivos OI relacionados al OF que tiene el estado Cargado - Manual
        consultas.setNumArchivosOFPendientesProcesarManual(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PENDIENTES_PROCESAR_MANUAL, epochAsopagos));

        // Archivos OI relacionados al OF con estado de archivo igual a Recaudo Notificado Manual (Últimos 7 días)
        consultas.setNumArchivosOFProcesadosFinalizadosManual(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PROCESADO_FINALIZADO_MANUAL, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OF con estado Cargado Exitosamente (Últimos 7 días)
        consultas.setNumArchivosOFCargadosExitosamenteCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_CARGADOS_EXITOSAMENTE, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OF con estado igual a Archivo Consistente (Últimos 7 días)
        consultas.setNumArchivosOFEnProcesoCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTASR_ARCHIVOS_OF_EN_PROCESO, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OI relacionados que tienen “Estado” igual a “Recaudo notificado” las ultimas (Últimos 7 días),
        consultas.setNumArchivosOFProcesadosFinalizadosCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PROCESADOS_FINALIZADOS, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OF que tienen "Estado" igual a "Estructura con inconsistencia" (Últimos 7 días)
        consultas.setNumArchivosOFConInconsistenciasCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_CON_INCONSISTENCIAS, CalendarUtils.restarDias(new Date(), 7)));

        /*
         * Archivos OI relacionados que tienen estado igual a Pendiente por gestionar error en validación vs BD
         * o Pendiente por registro y relación de aportes (Últimos 7 dias)
         */
        consultas.setNumArchivosOFBandejasGestionCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_BANDEJAS_GESTION, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OI relacionados al OF que tiene el estado Cargado - Manual (Últimos 7 dias)
        consultas.setNumAarchivosOFEnProcesoManualCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PENDIENTES_PROCESAR_MANUAL, CalendarUtils.restarDias(new Date(), 7)));

        // Archivos OI relacionados al OF con estado de archivo igual a Recaudo Notificado Manual (Últimos 7 dias)
        consultas.setNumArchivosOFProcesadosFinalizadosManualCtrl(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_PROCESADO_FINALIZADO_MANUAL, CalendarUtils.restarDias(new Date(), 7)));

        // Registros F cargados
        consultas.setNumRegistrosFcargados(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_REGISTROS_F_CARGADOS, CalendarUtils.restarDias(new Date(), 7)));

        // Registros F en proceso
        consultas.setNumRegistrosFEnProceso(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_REGISTROS_F_EN_PROCESO, CalendarUtils.restarDias(new Date(), 7)));

        // Registros F procesados
        consultas.setNumRegistrosFProcesados(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_REGISTROS_F_PROCESADOS, CalendarUtils.restarDias(new Date(), 7)));

        // Registros F con inconsistencias
        consultas.setNumRegistrosFInconsistencias(consultasPila.consultarArchivosOperadorFinanciero(
                NamedQueriesConstants.CONSULTAR_REGISTROS_F_INCONSISTENCIAS, CalendarUtils.restarDias(new Date(), 7)));

        logger.debug("Inicia archivosOFProcesadosFinalizados");
        return consultas;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#buscarArchivosOIProcesadosFinalizados(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.aportes.TipoOperadorEnum,
     *      java.lang.String, java.lang.Long, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizados(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, Short digitoVerificacion, Long fechaInicio, Long fechaFin, TipoOperadorEnum tipoOperador,
            Long numeroPlanilla, String idBanco, UriInfo uri, HttpServletResponse response) {

        String firmaServicio = "PilaBusiness.buscarArchivosOIProcesadosFinalizados(TipoIdentificacionEnum , "
                + "String , Short , Long , Long , " + "TipoOperadorEnum , String , Long , UriInfo , HttpServletResponse )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

        CriteriosBusquedaArchivosProcesados criterios = new CriteriosBusquedaArchivosProcesados();
        if (tipoIdentificacion != null) {
            criterios.setTipoIdentificacion(tipoIdentificacion.getValorEnPILA());
        }
        criterios.setNumeroIdentificacion(numeroIdentificacion);
        criterios.setDigitoVerificacion(digitoVerificacion);
        criterios.setFechaInicio(fechaInicio);
        criterios.setFechaFin(fechaFin);
        criterios.setTipoOperador(tipoOperador);
        criterios.setNumeroPlanilla(numeroPlanilla);
        criterios.setIdBanco(idBanco);

        result = consultasPila.buscarArchivosOIProcesadosFinalizados(criterios, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#buscarArchivosOIProcesadosFinalizadosManual(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.aportes.TipoOperadorEnum,
     *      java.lang.Long, java.lang.String, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizadosManual(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, Short digitoVerificacion, Long fechaInicio, Long fechaFin, TipoOperadorEnum tipoOperador,
            Long numeroPlanilla, String idBanco, UriInfo uri, HttpServletResponse response) {

        String firmaServicio = "PilaBusiness.buscarArchivosOIProcesadosFinalizados(TipoIdentificacionEnum , "
                + "String , Short , Long , Long , " + "TipoOperadorEnum , String , Long , UriInfo , HttpServletResponse )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

        CriteriosBusquedaArchivosProcesados criterios = new CriteriosBusquedaArchivosProcesados();
        if (criterios.getTipoIdentificacion() != null) {
            criterios.setTipoIdentificacion(tipoIdentificacion.getValorEnPILA());
        }
        criterios.setNumeroIdentificacion(numeroIdentificacion);
        criterios.setDigitoVerificacion(digitoVerificacion);
        criterios.setFechaInicio(fechaInicio);
        criterios.setFechaFin(fechaFin);
        criterios.setTipoOperador(tipoOperador);
        criterios.setNumeroPlanilla(numeroPlanilla);
        criterios.setIdBanco(idBanco);

        result = consultasPila.buscarArchivosOIProcesadosFinalizadosManual(criterios, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#verArchivosProcesadosFinalizados(javax.ws.rs.core.UriInfo,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizados() {
        String firmaServicio = "PilaBusiness.verArchivosProcesadosFinalizados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();
        result = consultasPila.verArchivosProcesadosFinalizados();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#verDetalleBloquesValidacionArchivo(java.lang.Long)
     */
    @Override
    public List<BloquesValidacionArchivoDTO> verDetalleBloquesValidacionArchivo(Long idPlanilla, TipoOperadorEnum tipoOperador) {
        String firmaServicio = "PilaBusiness.verDetalleBloquesValidacionArchivo(Long, TipoOperadorEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<BloquesValidacionArchivoDTO> result = null;
        result = consultasPila.verDetalleBloquesValidacionArchivo(idPlanilla, tipoOperador);

        // se limpian las entradas repetidas de estado
        result = limpiezaEstadosRepetidos(result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Método que revisa sí se presenta un cabio de estado repetido en el historial
     */
    private List<BloquesValidacionArchivoDTO> limpiezaEstadosRepetidos(List<BloquesValidacionArchivoDTO> result) {
        String firmaServicio = "PilaBusiness.limpiezaEstadosRepetidos(List<BloquesValidacionArchivoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<BloquesValidacionArchivoDTO> resultTemp = result;

        for (BloquesValidacionArchivoDTO bloqueValidacion : resultTemp) {
            EstadoProcesoArchivoEnum estadoActual = bloqueValidacion.getBloqueEstadoArchivo();

            for (int i = bloqueValidacion.getHistorialEstados().size() - 1; i >= 0; i--) {
                HistorialEstadoBloque historial = bloqueValidacion.getHistorialEstados().get(i);
                if (estadoActual.equals(historial.getEstado())) {
                    bloqueValidacion.getHistorialEstados().remove(historial);
                    i = bloqueValidacion.getHistorialEstados().size() - 1;
                }
                else {
                    break;
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultTemp;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#verCabeceraDetalleBloquesValidacionArchivo(java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.TipoOperadorEnum)
     */
    @Override
    public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivo(Long idPlanilla, TipoOperadorEnum tipoOperador) {
        String firmaServicio = "PilaBusiness.verDetalleBloquesValidacionArchivo(Long, TipoOperadorEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Long idRegistroGeneral = null;
        CabeceraDetalleArchivoDTO cabeceraDetalleArchivoDTO = null;

        if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(tipoOperador)) {
            cabeceraDetalleArchivoDTO = consultasPila.verCabeceraDetalleBloquesValidacionArchivo(idPlanilla);
            idRegistroGeneral = consultasStaging.obtenerIdRegistroGeneral(idPlanilla);

            if (idRegistroGeneral != null) {
                cabeceraDetalleArchivoDTO.setIdRegistroGeneral(idRegistroGeneral);
            }
            else {
                return cabeceraDetalleArchivoDTO;
            }
        }
        else if (TipoOperadorEnum.OPERADOR_FINANCIERO.equals(tipoOperador)) {
            cabeceraDetalleArchivoDTO = consultasPila.verCabeceraDetalleBloquesValidacionArchivoOF(idPlanilla);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cabeceraDetalleArchivoDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarBancosParametrizados()
     */
    @Override
    public List<BancoModeloDTO> consultarBancosParametrizados() {
        logger.debug("Inicia PilaBusiness.consultarBancosParametrizados");
        List<BancoModeloDTO> lstBancoModeloDTO = null;
        BancoModeloDTO bancoModeloDTO = null;
        List<Banco> lstBancos = null;
        lstBancos = consultasCore.consultarBancos();
        if (lstBancos != null && !lstBancos.isEmpty()) {
            lstBancoModeloDTO = new ArrayList<>();
            for (Banco banco : lstBancos) {
                bancoModeloDTO = new BancoModeloDTO();
                bancoModeloDTO.convertToDTO(banco);
                lstBancoModeloDTO.add(bancoModeloDTO);
            }
        }
        logger.debug("Finaliza PilaBusiness.consultarBancosParametrizados");
        return lstBancoModeloDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarCantidadPlanillasPendientesGestionManual()
     */
    @Override
    public Integer consultarCantidadPlanillasPendientesGestionManual() {

        String firmaServicio = "PilaBusiness.consultarCantidadPlanillasPendientesGestionManual()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Integer result = null;

        result = consultasPila.consultarCantidadPlanillasPendientesgestionManual();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultaPlanillasGestionManual(java.lang.Long, java.lang.Long, javax.ws.rs.core.UriInfo,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<PlanillaGestionManualDTO> consultaPlanillasGestionManual(Long numeroPlanilla, Long fechaIngreso, UriInfo uri,
            HttpServletResponse response) {

        String firmaServicio = "PilaBusiness.consultaPlanillasGetionManual(Long, Long, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<PlanillaGestionManualDTO> result = null;

        CriterioConsultaDTO criterios = new CriterioConsultaDTO();
        criterios.setFechaIngreso(fechaIngreso);
        criterios.setNumeroPlanilla(numeroPlanilla);

        result = consultasPila.consultarPlanillasParaGestionManual(criterios, uri, response);
        
        // se revisa la presencia de registros anulados en las planillas originales
        
        // primero se listan los ID de planilla original encontrados
        List<Long> idsPlanillaOriginal = new ArrayList<>();
        List<Long> idsPlanillaActual = new ArrayList<>();
        for (PlanillaGestionManualDTO planilla : result) {
            if(planilla.getIdPlanillaOriginal() != null && !idsPlanillaOriginal.contains(planilla.getIdPlanillaOriginal())){
                idsPlanillaOriginal.add(planilla.getIdPlanillaOriginal());
                idsPlanillaActual.add(planilla.getIdIndicePlanilla());
            }
        }
        
        Map<Long, List<Long>> mapaIdsOriginal = consultasStaging.consultarIdsRegistrosDetalladosPorIdPlanilla(idsPlanillaOriginal, Boolean.TRUE);
        Map<Long, List<Long>> mapaIdsActual = consultasStaging.consultarIdsRegistrosDetalladosPorIdPlanilla(idsPlanillaActual, Boolean.TRUE);

        // se consultan las anulaciones de los aportes originales
        List<Long> idsRegDetTotal = new ArrayList<>();
        for (List<Long> registrosPlanilla : mapaIdsOriginal.values()) {
            idsRegDetTotal.addAll(registrosPlanilla);
        }

        Map<Long, Boolean> mapaAnulados = consultasCore.consultarAnulacionAportesOriginales(idsRegDetTotal);

        // se actualizan las marcas de presencia de anulación
        for (PlanillaGestionManualDTO planilla : result) {
            if (planilla.getIdPlanillaOriginal() != null && mapaIdsActual.containsKey(planilla.getIdIndicePlanilla())) {
            	if(mapaIdsOriginal.containsKey(planilla.getIdPlanillaOriginal())) {
	                List<Long> idsPlanilla = mapaIdsOriginal.get(planilla.getIdPlanillaOriginal());
	                Integer cantidadAnulados = 0;
	
	                for (Long idRegDetPlanilla : idsPlanilla) {
	                    if (mapaAnulados.containsKey(idRegDetPlanilla) && mapaAnulados.get(idRegDetPlanilla)) {
	                        cantidadAnulados++;
	                    }
	                }
	
	                if (cantidadAnulados.compareTo(idsPlanilla.size()) == 0) {
	                    planilla.setPresenciaAnulaciones((short) 1);
	                }
	                else if (cantidadAnulados > 0) {
	                    planilla.setPresenciaAnulaciones((short) 2);
	                }
            	}
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#marcarNoProcesamientoArchivoR(com.asopagos.pila.dto.PlanillaGestionManualDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void marcarNoProcesamientoArchivoR(PlanillaGestionManualDTO planillaDTO, UserDTO userDTO) {
        String firmaServicio = "PilaBusiness.marcarNoProcesamientoArchivoR(PlanillaGestionManualDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        consultasPila.actualizarEstado(planillaDTO.getIdIndicePlanilla(), BloqueValidacionEnum.BLOQUE_7_OI,
                EstadoProcesoArchivoEnum.NO_VALIDADO_REEMPLAZO, AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarContenidoPlanillaOriginal(java.lang.Long, java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public RespuestaConsultaEmpleadorDTO consultarContenidoPlanillaOriginal(Long idPlanillaOriginal, Long idPlanillaCorrecion,
            UserDTO userDTO) {
        String firmaServicio = "PilaBusiness.consultarContenidoPlanillaOriginal(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        RespuestaConsultaEmpleadorDTO result = null;

        if (idPlanillaOriginal != null) {
            // Caso de planillas de corrección

            // se consulta el contenido de la planilla original
            result = consultasStaging.consultarEstadoGeneralPlanilla(idPlanillaOriginal);

            if (result != null) {
                // se agrega la fecha de proceso de la planilla original
                result.setFechaProceso(consultasPila.consultarFechaProcesoIndicePlanilla(result.getIdIndicePlanilla()).getTime());

                result.setRegistros(consultasStaging.consultarEstadoDetalladoPlanilla(idPlanillaOriginal));

                BigDecimal valorTotalAporteObligatorio = new BigDecimal(0);
                Set<String> listaNumerosIdCotizantes = new HashSet<>();

                // se recorren los registros detallados para las variables de control del registro general
                for (DetalleTablaAportanteDTO registroDetallado : result.getRegistros()) {
                    valorTotalAporteObligatorio = valorTotalAporteObligatorio.add(registroDetallado.getAporteObligatorio());

                    // se emplea un set para que sólo se cuenten números de identificación diferentes
                    listaNumerosIdCotizantes.add(registroDetallado.getIdCotizante());
                }

                result.setCantidadAportes(listaNumerosIdCotizantes.size());
                result.setTotalAportes(valorTotalAporteObligatorio.longValue());
            }
        }
        
       //IndicePlanilla planilla = consultasPila.consultarIndicePlanilla(idPlanillaCorrecion);
       //if (planilla != null
       //        && MotivoProcesoPilaManualEnum.ARCHIVO_CORRECCION.equals(planilla.getMotivoProcesoManual())) {
       //	
       //	if(userDTO == null) {
       //		consultasPila.ejecutarUSPCopiarPlanillaporFaseSimulada(idPlanillaCorrecion, "SISTEMA");
       //	}else {
       //		consultasPila.ejecutarUSPCopiarPlanillaporFaseSimulada(idPlanillaCorrecion, userDTO.getNombreUsuario());
       //	}
       //}

        //Se deben agrupar sin importar si tienen original o no
        consultasStaging.agruparAutomaticamentePlanillaN(idPlanillaCorrecion);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#copiarAgruparPlanilla(java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void copiarAgruparPlanilla(Long idPlanillaOriginal, Long idPlanillaCorrecion,
            UserDTO userDTO) {
        String firmaServicio = "PilaBusiness.copiarAgruparPlanilla(Long, Long) : con parámetros  idPlanillaOriginal: , idPlanillaCorrecion: " + idPlanillaCorrecion;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        IndicePlanilla planilla = consultasPila.consultarIndicePlanilla(idPlanillaCorrecion);
        
       // if (planilla != null
       //         && MotivoProcesoPilaManualEnum.ARCHIVO_CORRECCION.equals(planilla.getMotivoProcesoManual())) {
       // 	
       // 	if(userDTO == null) {
       // 		consultasPila.ejecutarUSPCopiarPlanillaporFaseSimulada(idPlanillaCorrecion, "SISTEMA");
       // 	}else {
       // 		consultasPila.ejecutarUSPCopiarPlanillaporFaseSimulada(idPlanillaCorrecion, userDTO.getNombreUsuario());
       // 	}
       // }

        //Se deben agrupar sin importar si tienen original o no
        consultasStaging.agruparAutomaticamentePlanillaN(idPlanillaCorrecion);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarResultadoFase1SimuladaCorrecion(java.lang.Long, java.lang.Long)
     */
    @Override
    public ResultadoValidacionRegistrosAdicionCorrecionDTO consultarResultadoFase1SimuladaCorrecion(Long idPlanillaOriginal,
            Long idPlanillaCorrecion) {
        String firmaMetodo = "PilaBusiness.consultarResultadoFase1SimuladaCorrecion(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoValidacionRegistrosAdicionCorrecionDTO result = new ResultadoValidacionRegistrosAdicionCorrecionDTO();

        // se consultan los registros detallados para el archivo de corrección
        List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion = null;

        // se consultan los registros detallados para el archivo original
        List<RegistroDetalladoModeloDTO> registrosDetalladosOriginal = null;

        if (idPlanillaOriginal != null) {
            registrosDetalladosOriginal = consultasStaging.consultarRegistrosDetalladosPorIdPlanilla(idPlanillaOriginal, false, false,
                    null);

            registrosDetalladosCorreccion = consultasStaging.consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false, false,
                    false);
        }
        else {
            registrosDetalladosOriginal = Collections.emptyList();

            registrosDetalladosCorreccion = consultasStaging.consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false, false,
                    true);
            
            // sí el resultado es vacío, se consulta sin discriminar (planilla sin original ni anulación)
            if(registrosDetalladosCorreccion.isEmpty()){
                registrosDetalladosCorreccion = consultasStaging.consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false,
                        false, null);
            }
        }

        if ((registrosDetalladosCorreccion != null && !registrosDetalladosCorreccion.isEmpty())) {
            // se agrega el ID de registro general original (sí lo hay)
            if(registrosDetalladosOriginal != null && !registrosDetalladosOriginal.isEmpty()){
                result.setIdRegGeneralOriginal(registrosDetalladosOriginal.get(0).getRegistroGeneral());
            }

            // se consultan el tipo y numero de ID del aportante para validaciones V3
            RespuestaConsultaEmpleadorDTO registroGeneralCorreccion = consultasStaging
                    .consultarEstadoGeneralPlanilla(idPlanillaCorrecion);

            // se inicia el diligenciamiento el DTO de respuesta, campos generales
            result.setNumeroPlanilla(registroGeneralCorreccion.getNumeroPlanilla());
            result.setTipoPlanilla(registroGeneralCorreccion.getTipoPlanilla());
            result.setFechaProceso(
                    consultasPila.consultarFechaProcesoIndicePlanilla(registroGeneralCorreccion.getIdIndicePlanilla()).getTime());

            List<ConjuntoResultadoRegistroCorreccionCDTO> resultados = new ArrayList<>();
            List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosC = null;

            // se actualiza el estado del registro de corrección
            ConjuntoResultadoRegistroCorreccionADTO resultado = null;

            for (RegistroDetalladoModeloDTO registroDetalladoDTO : registrosDetalladosCorreccion) {
                TipoAfiliadoEnum tipoAfiliadoEnum = null;
                TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum
                        .obtenerTipoCotizante(registroDetalladoDTO.getTipoCotizante().intValue());

                if (tipoCotizanteEnum != null) {
                    tipoAfiliadoEnum = tipoCotizanteEnum.getTipoAfiliado();
                }

                /*if (ConstantesComunesPila.CORRECCIONES_A.equals(registroDetalladoDTO.getCorrecciones())) {
                    // se agrega el ID del registro general de la corrección en la salida
                    if (result.getIdRegGeneralAdicionCorreccion() == null) {
                        result.setIdRegGeneralAdicionCorreccion(registroDetalladoDTO.getRegistroGeneral());
                    }

                    // se diligencia un DTO de conjunto de resultado para la salida
                    resultado = new ConjuntoResultadoRegistroCorreccionADTO();

                    resultado.setTipoIdCotizante(registroDetalladoDTO.getTipoIdentificacionCotizante());
                    resultado.setNombreCotizante(registroDetalladoDTO.componerNombreCotizante());
                    resultado.setIdCotizanteA(registroDetalladoDTO.getNumeroIdentificacionCotizante());
                    resultado.setSecuenciaA(registroDetalladoDTO.getRegistroControl());
                    resultado.setEstadoEvaluacionA(registroDetalladoDTO.getEstadoEvaluacion());
                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
                        resultado.setAporteObligatorioA(registroDetalladoDTO.getAporteObligatorio().intValue());
                    }
                    else {
                        resultado.setAporteObligatorioA(0);
                    }
                    resultado.setTipoAfiliadoA(tipoAfiliadoEnum);
                    resultado.setEstadoV0O(registroDetalladoDTO.getOutEstadoValidacionV0());
                    resultado.setEstadoV1O(registroDetalladoDTO.getOutEstadoValidacionV1());
                    resultado.setEstadoV2O(registroDetalladoDTO.getOutEstadoValidacionV2());
                    resultado.setEstadoV3O(registroDetalladoDTO.getOutEstadoValidacionV3());
                    resultado.setResultadoValidacionCorreccionA(registroDetalladoDTO.getEstadoValidacionCorreccion());
                    resultado.setIdRegDetA(registroDetalladoDTO.getId());
                    resultado.setIdRegDetOriginal(registroDetalladoDTO.getOutIdRegDetOriginal());

                    // se incializa lista de resultados C
                    resultadosC = new ArrayList<>();
                    resultado.setResultadosC(resultadosC);

                    resultados.add(resultado);
                }
                else {*/
                    ConjuntoResultadoRegistroCorreccionCDTO resultadoC = new ConjuntoResultadoRegistroCorreccionCDTO();
                    resultadoC.setIdCotizanteC(registroDetalladoDTO.getNumeroIdentificacionCotizante());
                    resultadoC.setSecuenciaC(registroDetalladoDTO.getRegistroControl());
                    resultadoC.setEstadoEvaluacionC(registroDetalladoDTO.getEstadoEvaluacion());
                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
                        resultadoC.setAporteObligatorioC(registroDetalladoDTO.getAporteObligatorio().intValue());
                    }
                    else {
                        resultadoC.setAporteObligatorioC(0);
                    }
                    if (result.getIdRegGeneralAdicionCorreccion() == null) {
                    	
                        result.setIdRegGeneralAdicionCorreccion(registroDetalladoDTO.getRegistroGeneral());
                    }
                    resultadoC.setTipoAfiliadoC(tipoAfiliadoEnum);
                    resultadoC.setEstadoV0C(registroDetalladoDTO.getOutEstadoValidacionV0());
                    resultadoC.setEstadoV1C(registroDetalladoDTO.getOutEstadoValidacionV1());
                    resultadoC.setEstadoV2C(registroDetalladoDTO.getOutEstadoValidacionV2());
                    resultadoC.setEstadoV3C(registroDetalladoDTO.getOutEstadoValidacionV3());
                    resultadoC.setResultadoValidacionCorreccionC(registroDetalladoDTO.getEstadoValidacionCorreccion());
                    resultadoC.setIdRegDetC(registroDetalladoDTO.getId());
                    resultadoC.setRegistradoFase1(registroDetalladoDTO.getOutRegistrado());
                    resultadoC.setRegistradoFase2(registroDetalladoDTO.getOutRegistradoAporte());
                    resultadoC.setRegistradoFase3(registroDetalladoDTO.getOutRegistradoNovedad());
                    resultadoC.setAporteAnulado(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO
                            .equals(registroDetalladoDTO.getEstadoValidacionCorreccion()));

                    // se agrega el resultado C
                    resultados.add(resultadoC);

                    // se determina sí el USP de la fase 2 ya se ejecutó
                    if (result.getHayDatosFase2() == null) {
                        if (EstadoRegistroAportesArchivoEnum.PROCESADO_APORTE.equals(registroDetalladoDTO.getOutEstadoRegistroAporte())
                                || EstadoRegistroAportesArchivoEnum.PENDIENTE_POR_REGISTO_RELACION_APORTE
                                        .equals(registroDetalladoDTO.getOutEstadoRegistroAporte())) {
                            result.setHayDatosFase2(true);
                        }
                    }
                /* }*/ 
            }

            result.setResultados(resultados);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

//    /**
//     * (non-Javadoc)
//     * @see com.asopagos.pila.service.PilaService#solicitarEjecucionFase1SimuladaCorreccion(java.lang.Long, java.lang.Long,
//     *      com.asopagos.rest.security.dto.UserDTO)
//     */
//    @Override
//    public ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaCorreccion(Long idPlanillaOriginal,
//            Long idPlanillaCorrecion, UserDTO userDTO) {
//    	long timeStart = System.nanoTime();
//        String firmaServicio = "PilaBusiness.solicitarEjecucionFase1SimuladaCorreccion(Long "+idPlanillaOriginal+", Long "+idPlanillaCorrecion+", UserDTO)";
//        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//
//        ResultadoValidacionRegistrosAdicionCorrecionDTO result = new ResultadoValidacionRegistrosAdicionCorrecionDTO();
//        // se solicita la ejecución inicial del USP de la fase 1 de PILA 2 para contar con registros generales y detallados
//        consultasPila.ejecutarUSPporFaseSimulada(idPlanillaCorrecion, userDTO.getNombreUsuario(), FasePila2Enum.PILA2_FASE_1);
//
//        // se consultan los registros detallados para el archivo de corrección
//        List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion = consultasStaging
//                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false, false, null);
//        
//        // se consultan los registros detallados para el archivo original
//        List<RegistroDetalladoModeloDTO> registrosDetalladosOriginal = consultasStaging
//                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaOriginal, false, true, null);
//        
//        // se hace la consulta de los aportes originales para establecer sí se encuentran anulados por correcciones
//        Map<Long, Boolean> aportesAnulados = identificarAnulacionAportesOriginales(registrosDetalladosOriginal);
//        
//        Long idRegGenCorreccion = null;
//
//        // se realiza la validación interna V1 sí los SP generaron registros detallados
//        if (registrosDetalladosCorreccion != null && !registrosDetalladosCorreccion.isEmpty()) {
//            // se consultan el tipo y numero de ID del aportante para validaciones V3
//            RespuestaConsultaEmpleadorDTO registroGeneralCorreccion = consultasStaging
//                    .consultarEstadoGeneralPlanilla(idPlanillaCorrecion);
//                        
//            idRegGenCorreccion = registroGeneralCorreccion.getIdRegistroGeneralOriginal();
//
//            // se prepara el mapa de cotizantes para establecer sí cuentan con subsidio
//            Map<String, Boolean> subsidioCotizantes = new HashMap<>();
//
//            aplicarValidacionRegistroCorreccion1(registroGeneralCorreccion, registrosDetalladosCorreccion, registrosDetalladosOriginal,
//                    consultasCore.consultarFestivos(), subsidioCotizantes, aportesAnulados);
//     
//            // luego de evaluar los registros de la planilla N, se recalculan los valores de aporte
//            registrosDetalladosCorreccion = calcularDatosValidacion(registrosDetalladosCorreccion);
//           
//            // se aplican de nuevo las validaciones de fase 1
//            consultasPila.ejecutarUSPporFaseSimulada(idPlanillaCorrecion, userDTO.getNombreUsuario(), FasePila2Enum.PILA2_FASE_1);
//      
//            // se inicia el diligenciamiento el DTO de respuesta, campos generales
//            result.setNumeroPlanilla(registroGeneralCorreccion.getNumeroPlanilla());
//            result.setTipoPlanilla(registroGeneralCorreccion.getTipoPlanilla());
//            result.setFechaProceso(consultasPila.consultarFechaProcesoIndicePlanilla(idPlanillaCorrecion).getTime());
//
//            List<ConjuntoResultadoRegistroCorreccionADTO> resultados = new ArrayList<>();
//            List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosC = null;
//
//            // se actualiza el estado del registro de corrección
//            ConjuntoResultadoRegistroCorreccionADTO resultado = null;
//
//            TipoAfiliadoEnum tipoAfiliadoEnum;
//            TipoCotizanteEnum tipoCotizanteEnum;
//            for (RegistroDetalladoModeloDTO registroDetalladoDTO : registrosDetalladosCorreccion) {
//                tipoAfiliadoEnum = null;
//                tipoCotizanteEnum = TipoCotizanteEnum
//                        .obtenerTipoCotizante(registroDetalladoDTO.getTipoCotizante().intValue());
//
//                if (tipoCotizanteEnum != null) {
//                    tipoAfiliadoEnum = tipoCotizanteEnum.getTipoAfiliado();
//                }
//
//                if (ConstantesComunesPila.CORRECCIONES_A.equals(registroDetalladoDTO.getCorrecciones())) {
//                    // se agrega el ID del registro general de la corrección en la salida
//                    if (result.getIdRegGeneralAdicionCorreccion() == null) {
//                        result.setIdRegGeneralAdicionCorreccion(registroDetalladoDTO.getRegistroGeneral());
//                    }
//
//                    // se diligencia un DTO de conjunto de resultado para la salida
//                    resultado = new ConjuntoResultadoRegistroCorreccionADTO();
//
//                    resultado.setTipoIdCotizante(registroDetalladoDTO.getTipoIdentificacionCotizante());
//                    resultado.setNombreCotizante(registroDetalladoDTO.componerNombreCotizante());
//                    resultado.setIdCotizanteA(registroDetalladoDTO.getNumeroIdentificacionCotizante());
//                    resultado.setSecuenciaA(registroDetalladoDTO.getRegistroControl());
//                    resultado.setEstadoEvaluacionA(registroDetalladoDTO.getEstadoEvaluacion());
//                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
//                        resultado.setAporteObligatorioA(registroDetalladoDTO.getAporteObligatorio().intValue());
//                    }
//                    else {
//                        resultado.setAporteObligatorioA(0);
//                    }
//                    resultado.setTipoAfiliadoA(tipoAfiliadoEnum);
//                    resultado.setEstadoV0O(registroDetalladoDTO.getOutEstadoValidacionV0());
//                    resultado.setEstadoV1O(registroDetalladoDTO.getOutEstadoValidacionV1());
//                    resultado.setEstadoV2O(registroDetalladoDTO.getOutEstadoValidacionV2());
//                    resultado.setEstadoV3O(registroDetalladoDTO.getOutEstadoValidacionV3());
//                    resultado.setResultadoValidacionCorreccionA(registroDetalladoDTO.getEstadoValidacionCorreccion());
//                    resultado.setIdRegDetA(registroDetalladoDTO.getId());
//                    resultado.setIdRegDetOriginal(registroDetalladoDTO.getOutIdRegDetOriginal());
//
//                    // se incializa lista de resultados C
//                    resultadosC = new ArrayList<>();
//                    resultado.setResultadosC(resultadosC);
//
//                    resultados.add(resultado);
//                }
//                else {
//                    ConjuntoResultadoRegistroCorreccionCDTO resultadoC = new ConjuntoResultadoRegistroCorreccionCDTO();
//                    resultadoC.setIdCotizanteC(registroDetalladoDTO.getNumeroIdentificacionCotizante());
//                    resultadoC.setSecuenciaC(registroDetalladoDTO.getRegistroControl());
//                    resultadoC.setEstadoEvaluacionC(registroDetalladoDTO.getEstadoEvaluacion());
//                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
//                        resultadoC.setAporteObligatorioC(registroDetalladoDTO.getAporteObligatorio().intValue());
//                    }
//                    else {
//                        resultadoC.setAporteObligatorioC(0);
//                    }
//                    resultadoC.setTipoAfiliadoC(tipoAfiliadoEnum);
//                    resultadoC.setEstadoV0C(registroDetalladoDTO.getOutEstadoValidacionV0());
//                    resultadoC.setEstadoV1C(registroDetalladoDTO.getOutEstadoValidacionV1());
//                    resultadoC.setEstadoV2C(registroDetalladoDTO.getOutEstadoValidacionV2());
//                    resultadoC.setEstadoV3C(registroDetalladoDTO.getOutEstadoValidacionV3());
//                    resultadoC.setResultadoValidacionCorreccionC(registroDetalladoDTO.getEstadoValidacionCorreccion());
//                    resultadoC.setIdRegDetC(registroDetalladoDTO.getId());
//                    resultadoC.setRegistradoFase1(registroDetalladoDTO.getOutRegistrado());
//                    resultadoC.setAporteAnulado(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO
//                            .equals(registroDetalladoDTO.getEstadoValidacionCorreccion()));
//
//                    // se agrega el resultado C
//                    resultadosC.add(resultadoC);
//                }
//            }
//            result.setResultados(resultados);
//        }
//        /* se valida el estado de los registros con posibilidad de actualizar el estado en caso de que 
//         * ningún registro C haya sido aprobado o viable (sí se encontró al Registro General de la corrección)*/
//        if (idRegGenCorreccion != null) {
//            verificarEstadoRegistrosAdicionCorreccion(idRegGenCorreccion, FasePila2Enum.PILA2_FASE_1, true, userDTO);
//        }
//
//        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
//        return result;
//    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#solicitarEjecucionFase1SimuladaCorreccion(java.lang.Long, java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaCorreccion(Long idPlanillaOriginal,
            Long idPlanillaCorrecion, UserDTO userDTO) {       
    	//long timeStart = System.nanoTime();
        String firmaServicio = "PilaBusiness.solicitarEjecucionFase1SimuladaCorreccion(Long "+idPlanillaOriginal+", Long "+idPlanillaCorrecion+", UserDTO)";
        //System.out.println("********************************************************************************************************");
        //System.out.println("********************************************************************************************************");
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionRegistrosAdicionCorrecionDTO result = new ResultadoValidacionRegistrosAdicionCorrecionDTO();
        
        // se solicita la ejecución inicial del USP de la fase 1 de PILA 2 para contar con registros generales y detallados
        // coiar la planilla y realizar el evaluación integral B7
        Long idTx = consultasPila.ejecutarUSPporFaseSimulada410(idPlanillaCorrecion, userDTO.getNombreUsuario(), 0L, false, FasePila2Enum.PILA2_FASE_1);

        // se consultan los registros detallados para el archivo de corrección
        List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false, false, null);

        // se consultan los registros detallados para el archivo original
        List<RegistroDetalladoModeloDTO> registrosDetalladosOriginal = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaOriginal, false, true, null);
        
        // se hace la consulta de los aportes originales para establecer sí se encuentran anulados por correcciones
        Map<Long, Boolean> aportesAnulados = identificarAnulacionAportesOriginales(registrosDetalladosOriginal);
        
        Long idRegGenCorreccion = null;
        
        // se realiza la validación interna V1 sí los SP generaron registros detallados
        if (registrosDetalladosCorreccion != null && !registrosDetalladosCorreccion.isEmpty()) {
        	
        	//logger.info("... se realiza la validación interna V1 porque los SP generaron registros detallados ...");
        	
            // se consultan el tipo y numero de ID del aportante para validaciones V3
            RespuestaConsultaEmpleadorDTO registroGeneralCorreccion = consultasStaging
                    .consultarEstadoGeneralPlanilla(idPlanillaCorrecion);
            
            //long timeE = System.nanoTime();
            //logger.info("consultasStaging.consultarEstadoGeneralPlanilla tardó " + CalendarUtils.calcularTiempoEjecucion(timeD, timeE) + " segundos en ejecutarse");
            
            idRegGenCorreccion = registroGeneralCorreccion.getIdRegistroGeneralOriginal();

            //logger.info("idRegGenCorreccion = registroGeneralCorreccion.getIdRegistroGeneralOriginal() es igual a: " + idRegGenCorreccion);
            
            // se prepara el mapa de cotizantes para establecer sí cuentan con subsidio
            Map<String, Boolean> subsidioCotizantes = new HashMap<>();

            aplicarValidacionRegistroCorreccion1(registroGeneralCorreccion, registrosDetalladosCorreccion, registrosDetalladosOriginal,
                    consultasCore.consultarFestivos(), subsidioCotizantes, aportesAnulados);

            //long timeF = System.nanoTime();
            //logger.info("aplicarValidacionRegistroCorreccion1 tardó " + CalendarUtils.calcularTiempoEjecucion(timeE, timeF) + " segundos en ejecutarse");
            
            //logger.info("los registrosDetalladosCorreccion luego de las validaciones quedaron:: " + registrosDetalladosCorreccion.toString());
            
            // luego de evaluar los registros de la planilla N, se recalculan los valores de aporte
            registrosDetalladosCorreccion = calcularDatosValidacion(registrosDetalladosCorreccion);

            //long timeG = System.nanoTime();
            //logger.info("calcularDatosValidacion tardó " + CalendarUtils.calcularTiempoEjecucion(timeF, timeG) + " segundos en ejecutarse : el listado de registrosDetalladosCorreccion tiene " + registrosDetalladosCorreccion.size() + "registros");
            
            // se aplican de nuevo las validaciones de fase 1
            
            //Long idTransaccion = consultasPila.ejecutarUSPporFaseSimulada410(idPlanillaCorrecion, userDTO.getNombreUsuario(), 0L, true, FasePila2Enum.PILA2_FASE_1);

            logger.info("EL ID TRANSACCIÓN CALCULADO PARA LA PLANILLA " + idPlanillaCorrecion + "ES: " + idTx);
            
            result.setIdTransaccion(idTx);
            //long timeH = System.nanoTime();
            //logger.info("ejecutarUSPporFaseSimulada tardó " + CalendarUtils.calcularTiempoEjecucion(timeG, timeH) + " segundos en ejecutarse");
            
            // se inicia el diligenciamiento el DTO de respuesta, campos generales
            result.setNumeroPlanilla(registroGeneralCorreccion.getNumeroPlanilla());
            result.setTipoPlanilla(registroGeneralCorreccion.getTipoPlanilla());
            result.setFechaProceso(consultasPila.consultarFechaProcesoIndicePlanilla(idPlanillaCorrecion).getTime());

            List<ConjuntoResultadoRegistroCorreccionCDTO> resultados = new ArrayList<>();
            List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosC = null;

            // se actualiza el estado del registro de corrección
            ConjuntoResultadoRegistroCorreccionADTO resultado = null;

            TipoAfiliadoEnum tipoAfiliadoEnum;
            TipoCotizanteEnum tipoCotizanteEnum;
            //logger.info("se procede a armar el resultado del servicio");
            for (RegistroDetalladoModeloDTO registroDetalladoDTO : registrosDetalladosCorreccion) {
            	
            	//logger.info("el registroDetalladoDTO a procesar es =  registroControl: " + registroDetalladoDTO.getRegistroControl() + ", correcciones: " + registroDetalladoDTO.getCorrecciones() + 
            	//", registroGeneral: " + registroDetalladoDTO.getRegistroGeneral() + ", aporteObligatorio: " + registroDetalladoDTO.getAporteObligatorio());
            	
            	
                tipoAfiliadoEnum = null;
                tipoCotizanteEnum = TipoCotizanteEnum
                        .obtenerTipoCotizante(registroDetalladoDTO.getTipoCotizante().intValue());

                if (tipoCotizanteEnum != null) {
                    tipoAfiliadoEnum = tipoCotizanteEnum.getTipoAfiliado();
                }

                /*if (ConstantesComunesPila.CORRECCIONES_A.equals(registroDetalladoDTO.getCorrecciones())) {
                    // se agrega el ID del registro general de la corrección en la salida
                    if (result.getIdRegGeneralAdicionCorreccion() == null) {
                    	
                        result.setIdRegGeneralAdicionCorreccion(registroDetalladoDTO.getRegistroGeneral());
                    }

                    // se diligencia un DTO de conjunto de resultado para la salida
                    resultado = new ConjuntoResultadoRegistroCorreccionADTO();

                    resultado.setTipoIdCotizante(registroDetalladoDTO.getTipoIdentificacionCotizante());
                    resultado.setNombreCotizante(registroDetalladoDTO.componerNombreCotizante());
                    resultado.setIdCotizanteA(registroDetalladoDTO.getNumeroIdentificacionCotizante());
                    resultado.setSecuenciaA(registroDetalladoDTO.getRegistroControl());
                    resultado.setEstadoEvaluacionA(registroDetalladoDTO.getEstadoEvaluacion());
                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
                        resultado.setAporteObligatorioA(registroDetalladoDTO.getAporteObligatorio().intValue());
                    }
                    else {
                        resultado.setAporteObligatorioA(0);
                    }
                    resultado.setTipoAfiliadoA(tipoAfiliadoEnum);
                    resultado.setEstadoV0O(registroDetalladoDTO.getOutEstadoValidacionV0());
                    resultado.setEstadoV1O(registroDetalladoDTO.getOutEstadoValidacionV1());
                    resultado.setEstadoV2O(registroDetalladoDTO.getOutEstadoValidacionV2());
                    resultado.setEstadoV3O(registroDetalladoDTO.getOutEstadoValidacionV3());
                    resultado.setResultadoValidacionCorreccionA(registroDetalladoDTO.getEstadoValidacionCorreccion());
                    resultado.setIdRegDetA(registroDetalladoDTO.getId());
                    resultado.setIdRegDetOriginal(registroDetalladoDTO.getOutIdRegDetOriginal());

                    // se incializa lista de resultados C
                    resultadosC = new ArrayList<>();
                    resultado.setResultadosC(resultadosC);

                    resultados.add(resultado);
                }
                else {*/

                    
                    ConjuntoResultadoRegistroCorreccionCDTO resultadoC = new ConjuntoResultadoRegistroCorreccionCDTO();
                    resultadoC.setIdCotizanteC(registroDetalladoDTO.getNumeroIdentificacionCotizante());
                    resultadoC.setSecuenciaC(registroDetalladoDTO.getRegistroControl());
                    resultadoC.setEstadoEvaluacionC(registroDetalladoDTO.getEstadoEvaluacion());
                    if (result.getIdRegGeneralAdicionCorreccion() == null) {
                    	
                        result.setIdRegGeneralAdicionCorreccion(registroDetalladoDTO.getRegistroGeneral());
                    }
                    if (registroDetalladoDTO.getAporteObligatorio() != null) {
                        resultadoC.setAporteObligatorioC(registroDetalladoDTO.getAporteObligatorio().intValue());
                    }
                    else {
                        resultadoC.setAporteObligatorioC(0);
                    }
                    resultadoC.setTipoAfiliadoC(tipoAfiliadoEnum);
                    resultadoC.setEstadoV0C(registroDetalladoDTO.getOutEstadoValidacionV0());
                    resultadoC.setEstadoV1C(registroDetalladoDTO.getOutEstadoValidacionV1());
                    resultadoC.setEstadoV2C(registroDetalladoDTO.getOutEstadoValidacionV2());
                    resultadoC.setEstadoV3C(registroDetalladoDTO.getOutEstadoValidacionV3());
                    resultadoC.setResultadoValidacionCorreccionC(registroDetalladoDTO.getEstadoValidacionCorreccion());
                    resultadoC.setIdRegDetC(registroDetalladoDTO.getId());
                    resultadoC.setRegistradoFase1(registroDetalladoDTO.getOutRegistrado());
                    resultadoC.setAporteAnulado(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO
                            .equals(registroDetalladoDTO.getEstadoValidacionCorreccion()));

                    // se agrega el resultado C
                    resultados.add(resultadoC);
                    //resultadosC.add(resultadoC);
               /* }*/
            }

            result.setResultados(resultados);
            
            //long timeI = System.nanoTime();
            //logger.info("MAPEO DE RESULTADOS tardó " + CalendarUtils.calcularTiempoEjecucion(timeH, timeI) + " segundos en ejecutarse");
        }
        
        /* se valida el estado de los registros con posibilidad de actualizar el estado en caso de que 
         * ningún registro C haya sido aprobado o viable (sí se encontró al Registro General de la corrección)*/
        if (idRegGenCorreccion != null) {
        	//long timeJ = System.nanoTime();
            verificarEstadoRegistrosAdicionCorreccion(idRegGenCorreccion, FasePila2Enum.PILA2_FASE_1, true, userDTO);
            //long timeK = System.nanoTime();
            //logger.info("MAPEO DE RESULTADOS tardó " + CalendarUtils.calcularTiempoEjecucion(timeJ, timeK) + " segundos en ejecutarse");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        //long timeEnd = System.nanoTime();
        //logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
        //System.out.println("********************************************************************************************************");
        //System.out.println("********************************************************************************************************");
        return result;
        
        
    }
    
    
    

    /**
     * Método encargado de la identificación de los aportes orginales anulados por correcciones
     * @param registrosDetallados
     * @return
     */
    private Map<Long, Boolean> identificarAnulacionAportesOriginales(List<RegistroDetalladoModeloDTO> registrosDetallados) {
        String firmaServicio = "PilaBusiness.identificarAnulacionAportesOriginales(List<RegistroDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<Long> idsRegistroDetallado = new ArrayList<>();
        
        for (RegistroDetalladoModeloDTO registro : registrosDetallados) {
            if(!idsRegistroDetallado.contains(registro.getId())){
                idsRegistroDetallado.add(registro.getId());
            }
        }
        
        Map<Long, Boolean> result = null;
        
        if(!idsRegistroDetallado.isEmpty()){
            result = consultasCore.consultarAnulacionAportesOriginales(idsRegistroDetallado);
        }else{
            result = Collections.emptyMap();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Método encargado de llevar a cabo la validación V1 de los registros corregidos de la planilla
     * @param registroGeneralCorreccion
     *        Registro general de la planilla de corrección
     * @param registrosDetalladosCorreccion
     *        Listado de DTO con el contenido de los registros detallados de corrección
     * @param registrosDetalladosOriginal
     *        Listado de DTO con el contenido de los registros detallados originales
     * @param festivos
     *        Listado de los días festivos parametrizados en el sistema
     * @param subsidioCotizantes
     *        Mapa en el que se ha de llevar el control del derecho a subsidio por cotizante
     * @param aportesAnulados 
     *        Mapa que contiene la validación de aportes originales anulados
     */
    private void aplicarValidacionRegistroCorreccion1(RespuestaConsultaEmpleadorDTO registroGeneralCorreccion,
            List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion, List<RegistroDetalladoModeloDTO> registrosDetalladosOriginal,
            List<DiasFestivosModeloDTO> festivos, Map<String, Boolean> subsidioCotizantes, Map<Long, Boolean> aportesAnulados) {
        String firmaServicio = "PilaBusiness.aplicarValidacionRegistroCorreccion1(RespuestaConsultaEmpleadorDTO, "
                + "List<RegistroDetalladoModeloDTO>, List<RegistroDetalladoModeloDTO>, List<DiasFestivosModeloDTO>,"
                + "Map<String, Boolean>), Map<Long, Boolean>";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // indicador de un registro A encontrado a satisfacción en archivo original
        Boolean registroAValido = null;
        
        // se emplea para almacenar temporalmente el registro "A" contra el cual comparar valores del registro "C"
        //RegistroDetalladoModeloDTO registroA = null;
        
        // indica sí se cuenta con original
        Boolean hayOriginal = registrosDetalladosOriginal != null && !registrosDetalladosOriginal.isEmpty();
        
        // indica sí el registro original A está anulado
        Boolean registroAnulado = Boolean.FALSE;
        
        // indica que se puede asignar estado de registro automático
        Boolean registrar = null;

        // se recorren los registros detallados del archivo de corrección
        ComparacionRegistrosDTO registrosIguales;
        for (RegistroDetalladoModeloDTO regDetallado : registrosDetalladosCorreccion) {
            registrar = Boolean.TRUE;
            /*
             * se toman sólo los registros detallados en el archivo de corrección que presenten
             * valor "A" en el campo "Correcciones", con el fin de ubicar un registro igual en
             * el archivo original
             */
            registrosIguales = null;

           /*  if (ConstantesComunesPila.CORRECCIONES_A.equals(regDetallado.getCorrecciones())) {
                registroAnulado = Boolean.FALSE;
                
                registrosIguales = registrosDetalladosIguales(regDetallado, registrosDetalladosOriginal);

                // más de un registro idéntico
                if (hayOriginal && registrosIguales.getCantidadOcurrencias() > 1) {
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_MULTIPLES_COINCIDENCIAS);
                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                    registroAValido = Boolean.FALSE;
                }
                // no se encuentra al menos un registro idéntico
                else if (hayOriginal && registrosIguales.getCantidadOcurrencias() == 0) {
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_SIN_COINCIDENCIA);
                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                    registroAValido = Boolean.FALSE;
                }
                // el original se encuentra anulado por corrección (CC mayo 2019)
                else if (aportesAnulados.containsKey(registrosIguales.getIdRegDetOriginal()) && 
                        aportesAnulados.get(registrosIguales.getIdRegDetOriginal())){
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO);
                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                    registroAValido = Boolean.FALSE;
                    registroAnulado = Boolean.TRUE;
                    registroA = regDetallado;
                }
                else{
                    // se marca el registro "A" como válido y se continúa la revisión en los registros "C"
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.APLICA);
                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
                    regDetallado.setOutIdRegDetOriginal(registrosIguales.getIdRegDetOriginal());
                    registroAValido = Boolean.TRUE;
                    registroA = regDetallado;
                    registrar = Boolean.FALSE;
                }
            } // los registros con corrección "C", sólo se evaluan con un "A" válido 
            else*/ if (ConstantesComunesPila.CORRECCIONES_C.equals(regDetallado.getCorrecciones())) {
                // validación de registro C con planilla original
                if (hayOriginal){
                    
                    // el registro A está anulado
                    if(registroAnulado){
                        regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO);
                        regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                        registrar = Boolean.FALSE;
                    }
                    // sí se presenta el caso de no encontrar coincidencia en el registro "A", se invalida el registro "C" 
                    //else if (/*!registroAValido && */!registroAnulado){
                    //    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_SIN_COINCIDENCIA);
                    //    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                    //}
                    // el cotizante no es igual
                   //else if (!registroA.getTipoIdentificacionCotizante().equals(regDetallado.getTipoIdentificacionCotizante())
                   //        || !registroA.getNumeroIdentificacionCotizante().equals(regDetallado.getNumeroIdentificacionCotizante())) {
                   //    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_COTIZANTE_DIFERENTE);
                   //    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                   //} // el “Tipo de cotizante” no es igual
                   //else if (!registroA.getTipoCotizante().equals(regDetallado.getTipoCotizante())) {
                   //    regDetallado
                   //            .setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_TIPO_COTIZANTE_DIFERENTE);
                   //    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                   // } // la “Tarifa” a corregir es menor
                   // else if (regDetallado.getTarifa().compareTo(registroA.getTarifa()) < 0) {
                   //     regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_TARIFA_MENOR);
                   //     regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                   // } // en este punto, se toma como registro que aplica y se pasa a la V2
                    else {
                        regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.APLICA);
                        
                        // se aplican las validaciones V2
                        aplicarValidacionRegistroCorreccion2(registroGeneralCorreccion, regDetallado, festivos, subsidioCotizantes);
                    }
                    
                    if(regDetallado.getEstadoValidacionCorreccion().getEstadoAplica()){
                        registrar = Boolean.FALSE;
                    }
                }
                // validación de registro C sin planilla original
                else if (!hayOriginal /*&& registroAValido*/) {
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.APLICA);
                    registrar = Boolean.FALSE;
                }
               // else if (!hayOriginal && !registroAValido) {
               //     // sí se presenta el caso de no encontrar coincidencia en el registro "A", se invalida el registro "C" 
               //     regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_SIN_COINCIDENCIA);
               //     regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
               // }
            }

            // se actualiza la marca de registro en caso de evaluación de corrección fallida
            if (registrar) {
                regDetallado.setOutRegistrado(Boolean.FALSE);
            }

            // se actualiza el registro en BD
            consultasStaging.actualizarRegistroDetallado(regDetallado.convertToEntity());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Método encargado de llevar a cabo la validación V2 de los registros corregidos de la planilla
     * @param registroGeneralCorreccion
     *        Registro general de la planilla de corrección
     * @param regDetallado
     *        DTO con el contenido del registro detallado de corrección
     * @param festivos
     *        Listado de los días festivos parametrizados en el sistema
     * @param subsidioCotizantes
     *        Mapa en el que se ha de llevar el control del derecho a subsidio por cotizante
     */
    private void aplicarValidacionRegistroCorreccion2(RespuestaConsultaEmpleadorDTO registroGeneralCorreccion,
            RegistroDetalladoModeloDTO regDetallado, List<DiasFestivosModeloDTO> festivos, Map<String, Boolean> subsidioCotizantes) {
        String firmaServicio = "PilaBusiness.aplicarValidacionRegistroCorreccion2(RespuestaConsultaEmpleadorDTO, "
                + "RegistroDetalladoModeloDTO, List<DiasFestivosModeloDTO>, Map<String, Boolean>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        if (regDetallado.getNovIGE() == null && regDetallado.getNovLMA() == null && regDetallado.getNovSLN() == null
                && regDetallado.getNovSUS() == null && regDetallado.getNovVACLR() == null && regDetallado.getNovVSP() == null
                && regDetallado.getNovVST() == null
                && (regDetallado.getDiasIRL() == null || Short.parseShort(regDetallado.getDiasIRL()) == 0)) {

            // sólo se aplica sí se presenta novedad de ingreso o retiro
            if (regDetallado.getNovIngreso() != null || regDetallado.getNovRetiro() != null) {
                // indica que el cotizante es dependiente
                Boolean esDependiente = true;

                // Sí la novedad es de retiro (“RET”), validar según el “Tipo de cotizante”
                if (regDetallado.getNovRetiro() != null) {
                    TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(regDetallado.getTipoCotizante().intValue());

                    switch (tipoCotizante.getTipoAfiliado()) {
                        case TRABAJADOR_INDEPENDIENTE:
                            esDependiente = false;
                            /*
                             * para cotizantes independientes sólo se puede presentar en la planilla “N” novedades de Retiro
                             * (“RET”) hasta los primeros 5 días hábiles período siguiente al período regular (es decir, del
                             * período actual)
                             */

                            if (regDetallado.getFechaRetiro() != null) {
                                Calendar fechaNovedad = Calendar.getInstance();
                                fechaNovedad.setTimeInMillis(regDetallado.getFechaRetiro());

                                Calendar fechaReferencia = Calendar.getInstance();
                                fechaReferencia.set(Calendar.YEAR, fechaNovedad.get(Calendar.YEAR));
                                fechaReferencia.set(Calendar.MONTH, fechaNovedad.get(Calendar.MONTH));
                                fechaReferencia.set(Calendar.DAY_OF_MONTH, 1);

                                // mes siguiente al período regular (año y mes de la novedad presentada)
                                fechaReferencia.add(Calendar.MONTH, 1);

                                Integer dias = 0;
                                if (!FuncionesUtilitarias.isFestivo(fechaReferencia, festivos)) {
                                    dias = 4;
                                }
                                else {
                                    dias = 5;
                                }
                                // quinto día hábil del mes siguiente al período regular
                                fechaReferencia = FuncionesUtilitarias.modificarFechaHabil(fechaReferencia, dias, festivos);

                                if (CalendarUtil.compararFechas(fechaNovedad.getTime(), fechaReferencia.getTime()) > 0) {
                                    regDetallado.setEstadoValidacionCorreccion(
                                            EstadoValidacionRegistroCorreccionEnum.NO_APLICA_RETIRO_FUERA_DE_PLAZO);
                                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                                }
                            }
                            else {
                                // fecha de novedad no diligenciada: caso no especificado
                            }

                            break;
                        case TRABAJADOR_DEPENDIENTE:
                            /*
                             * para cotizantes dependientes sólo se puede presentar en la planilla “N” novedades de Retiro
                             * (“RET”) hasta el último día hábil del período siguiente al período regular (es decir, del
                             * período actual)
                             */

                            if (regDetallado.getFechaRetiro() != null) {
                                Calendar fechaNovedad = Calendar.getInstance();
                                fechaNovedad.setTimeInMillis(regDetallado.getFechaRetiro());

                                Calendar fechaReferencia = Calendar.getInstance();
                                fechaReferencia.set(Calendar.YEAR, fechaNovedad.get(Calendar.YEAR));
                                fechaReferencia.set(Calendar.MONTH, fechaNovedad.get(Calendar.MONTH));
                                fechaReferencia.set(Calendar.DAY_OF_MONTH, 1);

                                // 2 meses siguientes al período regular (año y mes de la novedad presentada)
                                fechaReferencia.add(Calendar.MONTH, 2);

                                // último día hábil del mes siguiente al período regular
                                fechaReferencia = FuncionesUtilitarias.modificarFechaHabil(fechaReferencia, -1, festivos);

                                if (CalendarUtil.compararFechas(fechaNovedad.getTime(), fechaReferencia.getTime()) > 0) {
                                    regDetallado.setEstadoValidacionCorreccion(
                                            EstadoValidacionRegistroCorreccionEnum.NO_APLICA_RETIRO_FUERA_DE_PLAZO);
                                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                                }
                            }
                            else {
                                // fecha de novedad no diligenciada: caso no especificado
                            }
                            break;
                        default:
                            break;
                    }
                }

                // se llevan a cabo las validaciones de V3
                aplicarValidacionRegistroCorreccion3(registroGeneralCorreccion, regDetallado, esDependiente);
            }

            // sí el registro no ha sido invalidado en V3, se evalúa V4
            if (!EstadoAporteEnum.EVALUACION_NO_APLICADA.equals(regDetallado.getEstadoEvaluacion())) {
                /*
                 * antes de iniciar la validación del subsdio del cotizante, se verifica si dicha validación no se ha hecho en
                 * registros previos
                 */
                if (subsidioCotizantes.containsKey(regDetallado.getNumeroIdentificacionCotizante())
                        && subsidioCotizantes.get(regDetallado.getNumeroIdentificacionCotizante())) {
                    // el cotizante tiene subsidio
                    regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_TIENE_CUOTA_MONETARIA);
                    regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
                }
                else if (!subsidioCotizantes.containsKey(regDetallado.getNumeroIdentificacionCotizante())) {
                    aplicarValidacionRegistroCorreccion4(registroGeneralCorreccion, regDetallado, subsidioCotizantes);
                }
            }
        }
        else {
            // la novedad no es de ingreso (“ING”) o retiro (“RET”)
            regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_NOVEDAD_NO_APLICABLE);
            regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

    }

    /**
     * Método encargado de llevar a cabo la validación V3 de los registros corregidos de la planilla
     * @param registroGeneralCorreccion
     *        Registro general de la planilla de corrección
     * @param regDetallado
     *        DTO con el contenido del registro detallado de corrección
     * @param esDependiente
     *        Indicador para saber que el cotizante es dependiente
     */
    private void aplicarValidacionRegistroCorreccion3(RespuestaConsultaEmpleadorDTO registroGeneralCorreccion,
            RegistroDetalladoModeloDTO regDetallado, Boolean esDependiente) {
        String firmaServicio = "PilaBusiness.aplicarValidacionRegistroCorreccion3(RespuestaConsultaEmpleadorDTO, "
                + "RegistroDetalladoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CriterioConsultaDTO criteriosConsulta = new CriterioConsultaDTO();

        TipoIdentificacionEnum tipoIdCotizante = regDetallado.getTipoIdentificacionCotizante();
        String numeroIdCotizante = regDetallado.getNumeroIdentificacionCotizante();
        criteriosConsulta.setTipoIdCotizante(tipoIdCotizante);
        criteriosConsulta.setNumeroIdCotizante(numeroIdCotizante);
        criteriosConsulta.setEsDependiente(esDependiente);

        // los criterios del aportante sólo se emplean para dependientes
        if (esDependiente) {
            criteriosConsulta.setTipoIdAportante(registroGeneralCorreccion.getTipoIdentificacion());
            criteriosConsulta.setNumeroIdAportante(registroGeneralCorreccion.getNumeroIdentificacion());
        }

        /*
         * Validar sí se ha registrado una novedad más reciente (que modifique las condiciones del cotizante)
         * que la novedad reportada en la planilla “N” (sólo para novedades ING y RET que presenten fechas)
         */
        if (regDetallado.getNovIngreso() != null && regDetallado.getFechaIngreso() != null) {
            // se consultan novedades de ingreso radicadas y aplicadas para el cotizante respecto al aportante
            criteriosConsulta.setFechaIngresoReferencia(new Date(regDetallado.getFechaIngreso()));

            // hay una novedad más reciente
            if (consultasCore.consultarNovedadesIngRetCotizante(criteriosConsulta) > 0) {
                regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_NOVEDAD_MAS_RECIENTE);
                regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
            }
        }
        else if (regDetallado.getNovRetiro() != null && regDetallado.getFechaRetiro() != null) {
            // se consultan novedades de retiro radicadas y aplicadas para el cotizante respecto al aportante
            criteriosConsulta.setFechaRetiroReferencia(new Date(regDetallado.getFechaRetiro()));

            // hay una novedad más reciente
            if (consultasCore.consultarNovedadesIngRetCotizante(criteriosConsulta) > 0) {
                regDetallado.setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_NOVEDAD_MAS_RECIENTE);
                regDetallado.setEstadoEvaluacion(EstadoAporteEnum.EVALUACION_NO_APLICADA);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Método encargado de determinar sí un cotizante ha generado derecho de pago de subsidios
     * @param registroGeneralCorreccion
     *        Registro general de la planilla de corrección
     * @param regDetallado
     *        DTO con el contenido del registro detallado de corrección
     * @param subsidioCotizantes
     *        Mapa en el que se ha de llevar el control del derecho a subsidio por cotizante
     */
    private void aplicarValidacionRegistroCorreccion4(RespuestaConsultaEmpleadorDTO registroGeneralCorreccion,
            RegistroDetalladoModeloDTO regDetallado, Map<String, Boolean> subsidioCotizantes) {
        String firmaMetodo = "PilaBusiness.aplicarValidacionRegistroCorreccion4(RespuestaConsultaEmpleadorDTO, RegistroDetalladoModeloDTO, "
                + "Map<String, Boolean>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        subsidioCotizantes.put(regDetallado.getNumeroIdentificacionCotizante(),
                consultasCore.cotizanteConSubsidioPeriodo(regDetallado.getTipoIdentificacionCotizante(),
                        regDetallado.getNumeroIdentificacionCotizante(), registroGeneralCorreccion.getPeriodoAporte()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método que compara un registro "A" de archivo de corrección con los registros de un archivo orginal
     * con el fin de retornar los que le correspondan exactamente
     * 
     * @param regDetalladoA
     *        Registro detallado de tipo "A" en archivo de corrección para buscar en original
     * @param registrosDetalladosOriginal
     *        Listado de los registros del archivo original
     * @return <b>ComparacionRegistrosDTO</b>
     *         DTO con la cantidad de registros originales que coinciden con el registro "A" de la corrección y su ID
     */
    private ComparacionRegistrosDTO registrosDetalladosIguales(RegistroDetalladoModeloDTO regDetalladoA,
            List<RegistroDetalladoModeloDTO> registrosDetalladosOriginal) {
        String firmaServicio = "PilaBusiness.registrosDetalladosIguales(RegistroDetalladoModeloDTO, List<RegistroDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        ComparacionRegistrosDTO result = new ComparacionRegistrosDTO();

        Integer cuenta = 0;

        // se recorren los registros del archivo original buscando coincidencias con el registro del archivo de corrección
        for (int i = 0; i < registrosDetalladosOriginal.size(); i++) {
            RegistroDetalladoModeloDTO registroOriginal = registrosDetalladosOriginal.get(i);
            if (registrosIguales(regDetalladoA, registroOriginal)) {
                cuenta++;
                result.setIdRegDetOriginal(registroOriginal.getId());
                registrosDetalladosOriginal.remove(i);
                i--;
            }
        }
        
        result.setCantidadOcurrencias(cuenta);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Método que compara el contenido de 2 DTOs de registros detallado para establecer sí son iguales en términos de una corrección
     * 
     * @param regDetalladoA
     *        Registro detallado en archivo de corrección
     * @param registroOriginal
     *        Registro detallado en archivo original
     * @return <b>Boolean</b>
     *         Indica que los registros son iguales o no
     */
    private Boolean registrosIguales(RegistroDetalladoModeloDTO regDetalladoA, RegistroDetalladoModeloDTO registroOriginal) {
        String firmaServicio = "PilaBusiness.registrosDetalladosIguales(RegistroDetalladoModeloDTO, List<RegistroDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String valorA = "";
        String valorO = "";

        if (regDetalladoA.getTipoIdentificacionCotizante() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getTipoIdentificacionCotizante().name();
        }

        if (registroOriginal.getTipoIdentificacionCotizante() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getTipoIdentificacionCotizante().name();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNumeroIdentificacionCotizante() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNumeroIdentificacionCotizante();
        }

        if (registroOriginal.getNumeroIdentificacionCotizante() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNumeroIdentificacionCotizante();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getTipoCotizante() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getTipoCotizante().toString();
        }

        if (registroOriginal.getTipoCotizante() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getTipoCotizante().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getCodDepartamento() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getCodDepartamento();
        }

        if (registroOriginal.getCodDepartamento() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getCodDepartamento();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getCodMunicipio() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getCodMunicipio();
        }

        if (registroOriginal.getCodMunicipio() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getCodMunicipio();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getPrimerApellido() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getPrimerApellido();
        }

        if (registroOriginal.getPrimerApellido() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getPrimerApellido();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getSegundoApellido() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getSegundoApellido();
        }

        if (registroOriginal.getSegundoApellido() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getSegundoApellido();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getPrimerNombre() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getPrimerNombre();
        }

        if (registroOriginal.getPrimerNombre() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getPrimerNombre();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getSegundoNombre() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getSegundoNombre();
        }

        if (registroOriginal.getSegundoNombre() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getSegundoNombre();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovIngreso() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovIngreso();
        }

        if (registroOriginal.getNovIngreso() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovIngreso();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovRetiro() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovRetiro();
        }

        if (registroOriginal.getNovRetiro() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovRetiro();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovVSP() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovVSP();
        }

        if (registroOriginal.getNovVSP() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovVSP();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovVST() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovVST();
        }

        if (registroOriginal.getNovVST() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovVST();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovSLN() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovSLN();
        }

        if (registroOriginal.getNovSLN() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovSLN();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovIGE() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovIGE();
        }

        if (registroOriginal.getNovIGE() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovIGE();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovLMA() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovLMA();
        }

        if (registroOriginal.getNovLMA() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovLMA();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovVACLR() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovVACLR();
        }

        if (registroOriginal.getNovVACLR() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovVACLR();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getNovSUS() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getNovSUS();
        }

        if (registroOriginal.getNovSUS() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getNovSUS();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getDiasIRL() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getDiasIRL();
        }

        if (registroOriginal.getDiasIRL() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getDiasIRL();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getDiasCotizados() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getDiasCotizados().toString();
        }

        if (registroOriginal.getDiasCotizados() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getDiasCotizados().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getSalarioBasico() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getSalarioBasico().toString();
        }

        if (registroOriginal.getSalarioBasico() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getSalarioBasico().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getValorIBC() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getValorIBC().toString();
        }

        if (registroOriginal.getValorIBC() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getValorIBC().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getTarifa() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getTarifa().toString();
        }

        if (registroOriginal.getTarifa() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getTarifa().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getAporteObligatorio() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getAporteObligatorio().toString();
        }

        if (registroOriginal.getAporteObligatorio() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getAporteObligatorio().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getSalarioIntegral() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getSalarioIntegral();
        }

        if (registroOriginal.getSalarioIntegral() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getSalarioIntegral();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaIngreso() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaIngreso().toString();
        }

        if (registroOriginal.getFechaIngreso() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaIngreso().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaRetiro() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaRetiro().toString();
        }

        if (registroOriginal.getFechaRetiro() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaRetiro().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioVSP() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioVSP().toString();
        }

        if (registroOriginal.getFechaInicioVSP() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioVSP().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioSLN() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioSLN().toString();
        }

        if (registroOriginal.getFechaInicioSLN() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioSLN().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinSLN() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinSLN().toString();
        }

        if (registroOriginal.getFechaFinSLN() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinSLN().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioIGE() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioIGE().toString();
        }

        if (registroOriginal.getFechaInicioIGE() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioIGE().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinIGE() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinIGE().toString();
        }

        if (registroOriginal.getFechaFinIGE() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinIGE().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioLMA() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioLMA().toString();
        }

        if (registroOriginal.getFechaInicioLMA() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioLMA().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinLMA() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinLMA().toString();
        }

        if (registroOriginal.getFechaFinLMA() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinLMA().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioVACLR() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioVACLR().toString();
        }

        if (registroOriginal.getFechaInicioVACLR() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioVACLR().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinVACLR() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinVACLR().toString();
        }

        if (registroOriginal.getFechaFinVACLR() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinVACLR().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioVCT() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioVCT().toString();
        }

        if (registroOriginal.getFechaInicioVCT() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioVCT().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinVCT() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinVCT().toString();
        }

        if (registroOriginal.getFechaFinVCT() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinVCT().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioIRL() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioIRL().toString();
        }

        if (registroOriginal.getFechaInicioIRL() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioIRL().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinIRL() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinIRL().toString();
        }

        if (registroOriginal.getFechaFinIRL() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinIRL().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaInicioSuspension() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaInicioSuspension().toString();
        }

        if (registroOriginal.getFechaInicioSuspension() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaInicioSuspension().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        if (regDetalladoA.getFechaFinSuspension() == null) {
            valorA = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorA = regDetalladoA.getFechaFinSuspension().toString();
        }

        if (registroOriginal.getFechaFinSuspension() == null) {
            valorO = ConstantesComunesPila.SIN_VALOR;
        }
        else {
            valorO = registroOriginal.getFechaFinSuspension().toString();
        }

        if (!valorA.equalsIgnoreCase(valorO)) {
            return (false);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return true;
    }

    /**
     * Método encargado de llevar a cabo la actualización de los registros detallados con base en el
     * resultado de evaluación de los registros de planilla N
     * @param registrosDetalladosCorreccion
     *        Listado de registros detallads de la planilla de corrección
     * @return <b>List<RegistroDetalladoModeloDTO></b>
     *         Listado de registros actualizado
     */
    private List<RegistroDetalladoModeloDTO> calcularDatosValidacion(List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion) {
        String firmaMetodo = "PilaBusiness.calcularDatosValidacion(List<RegistroDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // variables para cálculo
        BigDecimal aporteObligatorioCalculado = BigDecimal.ZERO;
        BigDecimal tarifaMaxima = BigDecimal.ZERO;
        BigDecimal ibcCalculado = BigDecimal.ZERO;
        BigDecimal moraCalculada = BigDecimal.ZERO;
        Short diasCotizadosCalculados = (short) 0;
        
        RegistroDetalladoModeloDTO primerRegistroC = null;

        // se recorren los registros detallados
        Boolean hayCValido = Boolean.FALSE;
        RegistroDetalladoModeloDTO primerCGeneral = null;
        for (RegistroDetalladoModeloDTO registro : registrosDetalladosCorreccion) {
            
            /*if (ConstantesComunesPila.CORRECCIONES_A.equals(registro.getCorrecciones())) {

                // se actualiza el primer registro C del registro A anterior en BD y se reinician las cuentas (restando los valores de A)
                if(!hayCValido){
                    primerRegistroC = primerCGeneral;
                    
                    if(primerRegistroC != null){
                        primerRegistroC.setOutAporteObligatorioMod(aporteObligatorioCalculado);
                        primerRegistroC.setOutTarifaMod(tarifaMaxima);
                        primerRegistroC.setOutDiasCotizadosMod(diasCotizadosCalculados);
                        primerRegistroC.setOutValorIBCMod(ibcCalculado);
                        primerRegistroC.setOutValorMoraCotizanteMod(moraCalculada);
                    }
                }
                if (primerRegistroC != null) {
                	// mantis 0254978
                    if (primerRegistroC.getOutDiasCotizadosMod() < 0) {
                    	primerRegistroC.setOutDiasCotizadosMod((short) 0);
                    }
                    consultasStaging.actualizarRegistroDetallado(primerRegistroC.convertToEntity());

                    aporteObligatorioCalculado = BigDecimal.ZERO;
                    tarifaMaxima = BigDecimal.ZERO;
                    ibcCalculado = BigDecimal.ZERO;
                    moraCalculada = BigDecimal.ZERO;
                    diasCotizadosCalculados = (short) 0;
                    
                    hayCValido = Boolean.FALSE;
                    primerCGeneral = null;
                }

                // los valores de A se deben restar (siempre que haya registro A)
                aporteObligatorioCalculado = aporteObligatorioCalculado.subtract(registro.getAporteObligatorio());
                ibcCalculado = ibcCalculado.subtract(registro.getValorIBC());
                moraCalculada = moraCalculada.subtract(registro.getOutValorMoraCotizante());
                diasCotizadosCalculados = (short) (diasCotizadosCalculados - registro.getDiasCotizados());
                primerRegistroC = null;
            }
            else*/ if (ConstantesComunesPila.CORRECCIONES_C.equals(registro.getCorrecciones())) {
                if(primerCGeneral == null){
                    primerCGeneral = registro;
                }

                // se actualizan los valores del primer registro C y se asigna valor cero al registro leído
                aporteObligatorioCalculado = aporteObligatorioCalculado.add(registro.getAporteObligatorio());
                tarifaMaxima = tarifaMaxima.compareTo(registro.getTarifa()) < 0 ? registro.getTarifa() : tarifaMaxima;
                ibcCalculado = ibcCalculado.add(registro.getValorIBC());
                moraCalculada = moraCalculada.add(registro.getOutValorMoraCotizante());
                diasCotizadosCalculados = (short) (diasCotizadosCalculados + registro.getDiasCotizados());

                // se define la referencia al primer registro C
                if (primerRegistroC == null
                        && (EstadoValidacionRegistroCorreccionEnum.APLICA.equals(registro.getEstadoValidacionCorreccion()))
                        && !EstadoAporteEnum.EVALUACION_NO_APLICADA.equals(registro.getEstadoEvaluacion())) {
                    primerRegistroC = registro;
                    hayCValido = Boolean.TRUE;
                }
                else {
                    registro.setOutAporteObligatorioMod(BigDecimal.ZERO);
                    registro.setOutTarifaMod(BigDecimal.ZERO);
                    registro.setOutDiasCotizadosMod((short) 0);
                    registro.setOutValorIBCMod(BigDecimal.ZERO);
                    registro.setOutValorMoraCotizanteMod(BigDecimal.ZERO);

                    // se solicita la actualización del registro
                    consultasStaging.actualizarRegistroDetallado(registro.convertToEntity());
                }

                if(primerRegistroC != null){
                    primerRegistroC.setOutAporteObligatorioMod((aporteObligatorioCalculado != null && aporteObligatorioCalculado.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : aporteObligatorioCalculado);
                    primerRegistroC.setOutTarifaMod(tarifaMaxima);
                    primerRegistroC.setOutDiasCotizadosMod(diasCotizadosCalculados);
                    primerRegistroC.setOutValorIBCMod(ibcCalculado);
                    primerRegistroC.setOutValorMoraCotizanteMod(moraCalculada);
                }
            }
        }
        
        // sí el último paquete no tiene un C válido, se actualiza el primer encontrado
        if(!hayCValido && primerCGeneral != null){
            primerRegistroC = primerCGeneral;
            
            primerRegistroC.setOutAporteObligatorioMod((aporteObligatorioCalculado != null && aporteObligatorioCalculado.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : aporteObligatorioCalculado);
            primerRegistroC.setOutTarifaMod(tarifaMaxima);
            primerRegistroC.setOutDiasCotizadosMod(diasCotizadosCalculados);
            primerRegistroC.setOutValorIBCMod(ibcCalculado);
            primerRegistroC.setOutValorMoraCotizanteMod(moraCalculada);
        }

        // se actualiza el último primer registro C encontrado en el archivo
        if (primerRegistroC != null) {
        	// mantis 0254978
            if (primerRegistroC.getOutDiasCotizadosMod() < 0) {
            	primerRegistroC.setOutDiasCotizadosMod((short) 0);
            }
            consultasStaging.actualizarRegistroDetallado(primerRegistroC.convertToEntity());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return registrosDetalladosCorreccion;
    }

    /**
     * (non-Javadoc)
     * @return
     * @see com.asopagos.pila.service.PilaService#registrarNoRegistrarCorreccion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> registrarNoRegistrarCorreccion(RegistrarCorreccionAdicionDTO registrarCorreccionDTO, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.registrarNoRegistrarCorreccion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        EstadoAporteEnum nuevoEstado = null;

        if (registrarCorreccionDTO.getRegistrar()) {
            // se solicita el registro de la corrección
            nuevoEstado = EstadoAporteEnum.VIGENTE;

            // Registrar los resultados de esta nueva “Evaluación vs BD”, asociados al registro original.
            // Marcar “Evaluación vs BD” igual a “Vigente” para el registro original asociado (si ya tiene esta marca, se mantiene).
            actualizarDatosRegistro(registrarCorreccionDTO.getIdRegistroDetalladoCorreccionC(),
                    registrarCorreccionDTO.getIdRegistroDetalladoOriginal(), nuevoEstado, true, userDTO.getNombreUsuario());

            // Marcar “Evaluación vs BD” del registro A del archivo como “Corregido”.
            actualizarDatosRegistro(null, registrarCorreccionDTO.getIdRegistroDetalladoCorreccionA(), EstadoAporteEnum.CORREGIDO, true, userDTO.getNombreUsuario());
        }
        else {
            nuevoEstado = EstadoAporteEnum.EVALUACION_NO_APLICADA;
            
            actualizarDatosRegistro(null, registrarCorreccionDTO.getIdRegistroDetalladoCorreccionC(), nuevoEstado, false, userDTO.getNombreUsuario());

        }

        Map<String, String> result = verificarEstadoRegistrosAdicionCorreccion(registrarCorreccionDTO.getIdRegistroGeneralCorreccion(),
                FasePila2Enum.PILA2_FASE_1, true, userDTO);
        
        result.put(ConstantesParaMensajes.NUEVO_ESTADO_EVALUACION, nuevoEstado.name());
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de llevar a cabo la actualización de un registro detallado para marcar la aprovación o rechazo del mismo,
     * así como su actualización con base en el contenido de otro registro detallado
     * @param registroOrigen
     *        Registro detallado desde el se toma la información para la actualización del destino
     * @param registroDestino
     *        Resgistro detallado que será actualizado
     * @param nuevoEstado
     *        Estado de evaluación de corrección que será aplicado al registro
     * @param registrar
     *        Indicador para el registro como "Registrado" (true) o "No Registrado" (false)
     */
    private Byte actualizarDatosRegistro(Long registroOrigen, Long registroDestino, EstadoAporteEnum nuevoEstado, boolean registrar, String usuarioModificacion) {
        String firmaMetodo = "PilaBusiness.actualizarDatosRegistro(Long, Long, EstadoAporteEnum, Date, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        Date fechaActual = Calendar.getInstance().getTime();
        
        RegistroDetallado registroDetalladoOrigen = null;
        RegistroDetallado registroDetalladoDestino = consultasStaging.obtenerEntidadRegistroDetalladoEspecifico(registroDestino);

        if (registroDetalladoDestino != null) {
            if (registroOrigen != null) {
                // cuando se presenta un ID de registro origen, se actualiza la información aplicable en una corrección

                registroDetalladoOrigen = consultasStaging.obtenerEntidadRegistroDetalladoEspecifico(registroOrigen);

                if (registroDetalladoOrigen != null) {
                    /*
                     * en correciones, los campos que cambian se relacionan con novedades ING y RET, junto con sus fechas, días cotizados,
                     * IBC y aporte obligatorio
                     */
//                    registroDestinoDTO.setNovIngreso(registroOrigenDTO.getNovIngreso());
//                    registroDestinoDTO.setFechaIngreso(registroOrigenDTO.getFechaIngreso());
//                    registroDestinoDTO.setNovRetiro(registroOrigenDTO.getNovRetiro());
//                    registroDestinoDTO.setFechaRetiro(registroOrigenDTO.getFechaRetiro());
//                    registroDestinoDTO.setDiasCotizados(registroOrigenDTO.getDiasCotizados());
//                    registroDestinoDTO.setValorIBC(registroOrigenDTO.getValorIBC());
//                    registroDestinoDTO.setAporteObligatorio(registroOrigenDTO.getAporteObligatorio());

                    // se marca el origen como registrado
                    registroDetalladoOrigen.setOutRegistrado(registrar);
                    
                    registroDetalladoOrigen.setUsuarioAccion(usuarioModificacion);
                    registroDetalladoOrigen.setFechaAccion(fechaActual);
                    
                    // se actualiza el registro origen
                    consultasStaging.actualizarRegistroDetallado(registroDetalladoOrigen);
                }
                else {
                    // sin un registro origen, se lanza excepción ténica
                    mensaje = MensajesErrorComunesEnum.ERROR_REGISTRO_DETALLADO_FALTANTE.getReadableMessage(registroOrigen.toString());
                    throw new TechnicalException(mensaje);
                }
            }
            else {
                // se marca el destino como registrado
                registroDetalladoDestino.setOutRegistrado(registrar);
            }

            // se asigna el estado de evaluación, la fecha de actualización y el usuario que la lleva a cabo
            registroDetalladoDestino.setEstadoEvaluacion(nuevoEstado);
            
            registroDetalladoDestino.setUsuarioAccion(usuarioModificacion);
            registroDetalladoDestino.setFechaAccion(fechaActual);
            
            // se actualiza el registro destino
            consultasStaging.actualizarRegistroDetallado(registroDetalladoDestino);
        }
        // sin un registro destino, sólo se actualiza la marca de registro
        else if (registroOrigen != null) {
            registroDetalladoOrigen = consultasStaging.obtenerEntidadRegistroDetalladoEspecifico(registroOrigen);

            if (registroDetalladoOrigen != null) {
                // se marca el origen como registrado
                registroDetalladoOrigen.setOutRegistrado(registrar);

                registroDetalladoOrigen.setUsuarioAccion(usuarioModificacion);
                registroDetalladoOrigen.setFechaAccion(fechaActual);
                
                // se actualiza el registro origen
                consultasStaging.actualizarRegistroDetallado(registroDetalladoOrigen);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return 0;
    }

    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#validoParaContinuarAFase2Correccion(java.lang.Long)
     */
    @Override
    @Deprecated
    public Map<String, String> validoParaContinuarAFase2Correccion(Long idPlanillaCorreccion) {
        String firmaMetodo = "PilaBusiness.validoParaContinuarAFase2Correccion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, String> result = new HashMap<>();
        String mensaje = ConstantesParaMensajes.CONTINUAR;

        // se consultan los registros detallados de la planilla indicada
        List<RegistroDetalladoModeloDTO> registros = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorreccion, false, false, null);

        // se recorren para establecer que son válidos para continuar a la siguiente fase
        for (RegistroDetalladoModeloDTO registro : registros) {
            // sólo para registros C 
            if (!ConstantesComunesPila.CORRECCIONES_A.equals(registro.getCorrecciones())) {
                if (registro.getOutRegistrado() == null) {
                    // no se ha marcado opción de registro, no es válido para continuar
                    mensaje = ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION;
                    break;
                }

                if (EstadoRegistroAportesArchivoEnum.NO_OK.equals(registro.getOutEstadoRegistroAporte())
                        || EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD.equals(registro.getOutEstadoRegistroAporte())) {
                    // se presentan inconsistencias sin tratar en validación vs BD, no es válido para continuar
                    mensaje = ConstantesParaMensajes.NO_CONTINUAR_INCONSISTENCIAS_SIN_GESTIONAR;
                }
            }
        }

        result.put(ConstantesParaMensajes.RESULTADO_VALIDACION, mensaje);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#cambiarMarcaHabilitacionPila2Manual(java.lang.Long, java.lang.Boolean)
     */
    @Override
    public void cambiarMarcaHabilitacionPila2Manual(Long idIndicePlanilla, Boolean nuevaMarca) {
        String firmaMetodo = "PilaBusiness.cambiarMarcaHabilitacionPila2Manual(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.actualizarHabilitacionPila2Manual(idIndicePlanilla, nuevaMarca);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarDatosFase2SimuladaCorreccion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO)
     */
    @Override
    public ResultadoSimulacionAporteDetalladoDTO consultarDatosFase2SimuladaCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion) {
        String firmaMetodo = "PilaBusiness.consultarDatosFase2SimuladaCorreccion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoSimulacionAporteDetalladoDTO result = new ResultadoSimulacionAporteDetalladoDTO();

        // se consulta el registro detallado A
        RegistroDetalladoModeloDTO registroA = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionA());

        // se consulta el registro detallado C indicado
        RegistroDetalladoModeloDTO registroC = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

        CabeceraPestanaAporteNovedadDTO cabecera = null;
        if (registroC != null) {
            // se diligencia la información de la cabecera con base en el registro de corrección
            cabecera = new CabeceraPestanaAporteNovedadDTO();
            cabecera.diligenciarPorRegistroDetallado(registroC);
        }

        // se consulta la tabla de aportes de la planilla original
        List<AporteDetalladoPlanillaDTO> detalleAportesOriginal = consultarDetalleAportesPorPlanilla(
                criteriosSimulacion.getIdRegistroGeneralOriginal(), null, null);

        List<AporteDetalladoPlanillaDTO> detalleAportesOriginalCotizante = new ArrayList<>();

        // se toman sólo los datos relevantes para el cotizante
        for (AporteDetalladoPlanillaDTO detalleAporteOriginal : detalleAportesOriginal) {
            if (detalleAporteOriginal.getNumeroIdentificacionCotizante().equals(registroA.getNumeroIdentificacionCotizante())) {
                detalleAportesOriginalCotizante.add(detalleAporteOriginal);
            }
        }

        // se agregan los subtipos de cotizante de cada registro (sólo para dependientes / independientes)
        if (registroA.getOutTipoAfiliado() != null && !TipoAfiliadoEnum.PENSIONADO.equals(registroA.getOutTipoAfiliado())) {
            registroA.setSubTipoCotizante(SubTipoCotizanteEnum
                    .obtenerSubTipoCotizante(consultasPila.consultarSubtipoCotizante(registroA.getRegistroControl()).intValue()));
        }

        if (registroA.getOutTipoAfiliado() != null && !TipoAfiliadoEnum.PENSIONADO.equals(registroA.getOutTipoAfiliado())) {
            registroA.setSubTipoCotizante(SubTipoCotizanteEnum
                    .obtenerSubTipoCotizante(consultasPila.consultarSubtipoCotizante(registroA.getRegistroControl()).intValue()));
        }

        if (registroC.getOutTipoAfiliado() != null && !TipoAfiliadoEnum.PENSIONADO.equals(registroC.getOutTipoAfiliado())) {
            registroC.setSubTipoCotizante(SubTipoCotizanteEnum
                    .obtenerSubTipoCotizante(consultasPila.consultarSubtipoCotizante(registroC.getRegistroControl()).intValue()));
        }

        // se unifica la información en el DTO de salida
        result.setCabecera(cabecera);
        result.setAportesPorPlanilla(detalleAportesOriginalCotizante);
        result.setRegDetalladoA(registroA);
        result.setRegDetalladoC(registroC);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**  
     * (non-Javadoc)
     * @param userDTO
     * @return
     * @see com.asopagos.pila.service.PilaService#aprobarCambioAportesCorrecciones(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO)
     */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO aprobarCambioAportesCorrecciones(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.aprobarCambioAportesCorrecciones(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        //logger.debug("al servicio " + firmaMetodo + "llegó:");
        //logger.debug("el registro de corrección C recibido es: " + criteriosSimulacion.toString());
        ResultadoAprobacionCorreccionAporteDTO result = null;

        // se consulta el registro detallado C
        RegistroDetalladoModeloDTO registroC = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

        //BORRAR ESTE IF ANTES DE ENTREGAR
//        if(registroC != null){
//        	System.out.println("el valor del monto del registroC es ::: " +
//        			"getOutAporteObligatorioMod: " + registroC.getOutAporteObligatorioMod() +
//        			" - IdRegistroDetalladoOriginal: " + criteriosSimulacion.getIdRegistroDetalladoOriginal());
//        }
//        else{
//        	logger.debug("no se encontró el registro C para " + criteriosSimulacion.getIdRegistroDetalladoCorreccionC());
//        }

        // manejo para correcciones con original para aportes con valor diferente a cero
        if(criteriosSimulacion.getIdRegistroDetalladoOriginal() != null && registroC.getOutAporteObligatorioMod().compareTo(BigDecimal.ZERO) > 0){
            // se consulta el aporte detalado original
            AporteDetalladoModeloDTO aporteDetalladoOriginal = consultasCore
                    .consultarAporteDetalladoPorTransaccion(criteriosSimulacion.getIdRegistroDetalladoOriginal());

            AporteGeneralModeloDTO aporteGeneralOriginal = null;

            // sólo se procede cuando se cuenta con un aporte original, caso contrario se lanza excepción técnica
            if (aporteDetalladoOriginal != null) {
                // se consulta el aporte general asociado
                aporteGeneralOriginal = consultasCore.consultarAporteGeneralPorId(aporteDetalladoOriginal.getIdAporteGeneral());
            } 
            else {
                String mensaje = MensajesErrorComunesEnum.ERROR_APORTE_ORIGINAL_FALTANTE
                        .getReadableMessage(criteriosSimulacion.getIdRegistroDetalladoOriginal().toString());
                throw new TechnicalException(mensaje);
            }

            // se actualiza el valor del aporte original (general y detallado) con el valor de registro C
            if (registroC.getOutAporteObligatorioMod() != null) { 
                aporteDetalladoOriginal = ajustarAporteDetallado(aporteDetalladoOriginal, registroC);
                aporteGeneralOriginal = ajustarAporteGeneral(aporteGeneralOriginal, registroC);
            }

            aporteDetalladoOriginal.setEstadoAporteAjuste(EstadoAporteEnum.CORREGIDO);

            // se marca el registro del aporte en el registro C como aprobado y se actualiza
            registroC.setOutRegistradoAporte(true);
            consultasStaging.actualizaRegistroDetallado(registroC.convertToEntity());

            // se revisan los registros para determinar sí es válido continuar a la siguiente fase y actualizar el estado de archivo
            Map<String, String> verificacion = verificarEstadoRegistrosAdicionCorreccion(criteriosSimulacion.getIdRegistroGeneralCorreccion(),
                    FasePila2Enum.PILA2_FASE_2, true, userDTO);
            
            logger.debug("la verificación para " + criteriosSimulacion.getIdRegistroDetalladoCorreccionC() + " es = " + verificacion != null ? " no null" : "null");
            
            if (verificacion != null) {
                //  se crea un DTO de aporte detallado temporal con el fin de reportar el ajuste a la cuenta de aportes
                AporteDetalladoModeloDTO aporteAjuste = new AporteDetalladoModeloDTO();

                aporteAjuste.setId(aporteDetalladoOriginal.getId());
                aporteAjuste.setIdAporteGeneral(aporteGeneralOriginal.getId());
                aporteAjuste.setAporteObligatorio(registroC.getOutAporteObligatorioMod());
                aporteAjuste.setValorMora(registroC.getOutValorMoraCotizanteMod());
                aporteAjuste.setTarifa(registroC.getOutTarifaMod());

                // se actualizan los registros en BD (el registro de TemAporte se debe eliminar para evitar que sea procesado por ESB)
                consultasCore.actualizarAporteDetallado(aporteDetalladoOriginal.convertToEntity());
                consultasCore.actualizarAporteGeneral(aporteGeneralOriginal.convertToEntity());
                consultasPila.eliminarAporteTemporal(registroC.getId());

                result = new ResultadoAprobacionCorreccionAporteDTO();
                result.setResultadoValidacion(verificacion.get(ConstantesParaMensajes.RESULTADO_VALIDACION));
                // cuando se tiene original, el tipo de movimiento es una corrección a la alta
                result.setTipoAjuste(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA);

                result.setAporteDetalladoActualizado(aporteAjuste);
            }
            else {
                // sí falla la verificación, se debe indicar nuevamente que el registro no ha sido aprobado
                registroC.setOutRegistradoAporte(false);
                consultasStaging.actualizaRegistroDetallado(registroC.convertToEntity());
            }
        }
        // manejo para correcciones sin original para aportes con valor diferente a cero
        else if (criteriosSimulacion.getIdRegistroDetalladoOriginal() == null
                && registroC.getOutAporteObligatorioMod().compareTo(BigDecimal.ZERO) > 0) {
            // en el caso de correcciones sin original, simplemente se consulta el temporal para cambiar la marca de simulado
            // se consulta el temporal
            TemAporteModeloDTO aporteTemporal = consultasPila.contultarAporteTemporal(registroC.getId());
            if(aporteTemporal != null){
                // se marca el registro del aporte en el registro como aprobado y vigente
                registroC.setOutRegistradoAporte(true);
                registroC.setOutEstadoEvaluacionAporte(EstadoAporteEnum.VIGENTE);

                // se quita la marca de simulación del aporte tenmporal
                aporteTemporal.setMarcaAporteSimulado(false);

                // se actualizan los registros en BD
                consultasStaging.actualizaRegistroDetallado(registroC.convertToEntity());
                consultasPila.actualizarAporteTemporal(aporteTemporal.convertToEntity());
                
                // se revisan los registros para determinar sí es válido continuar a la siguiente fase y actualizar el estado de archivo
                Map<String, String> verificacion = verificarEstadoRegistrosAdicionCorreccion(criteriosSimulacion.getIdRegistroGeneralCorreccion(),
                        FasePila2Enum.PILA2_FASE_2, true, userDTO);
                
                result = new ResultadoAprobacionCorreccionAporteDTO();
                result.setResultadoValidacion(verificacion.get(ConstantesParaMensajes.RESULTADO_VALIDACION));
            }
            // cuando no se encuentra un aporte temporal se lanza excepción
            else {
                String mensaje = MensajesErrorComunesEnum.ERROR_APORTE_TEMPORAL_FALTANTE
                        .getReadableMessage(registroC.getId().toString());
                TechnicalException e = new TechnicalException(mensaje);
                throw e;
            }
        }
        // manejo para correcciones por valor cero
        else if(registroC.getOutAporteObligatorioMod().compareTo(BigDecimal.ZERO) == 0){
            // se marca el registro del aporte en el registro C como aprobado y se actualiza
            registroC.setOutRegistradoAporte(true);
            consultasStaging.actualizaRegistroDetallado(registroC.convertToEntity());
            
            // se revisan los registros para determinar sí es válido continuar a la siguiente fase y actualizar el estado de archivo
            Map<String, String> verificacion = verificarEstadoRegistrosAdicionCorreccion(criteriosSimulacion.getIdRegistroGeneralCorreccion(),
                    FasePila2Enum.PILA2_FASE_2, true, userDTO);
            
            result = new ResultadoAprobacionCorreccionAporteDTO();
            result.setResultadoValidacion(verificacion.get(ConstantesParaMensajes.RESULTADO_VALIDACION));
        }

        // en el caso de que sea válido el paso a fase 3, se ejecuta el SP respectivo
//        if (ConstantesParaMensajes.CONTINUAR.equals(result.getResultadoValidacion())) {
//        	
//        	logger.debug("al sp se le envió un idIndicePlanilla = " + criteriosSimulacion.getIdIndicePlanillaCorreccion());
//        	
//            consultasPila.ejecutarUSPporFaseSimulada410(criteriosSimulacion.getIdIndicePlanillaCorreccion(), userDTO.getNombreUsuario(),
//                    FasePila2Enum.PILA2_FASE_3);
//        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que actualiza el DTO del aporte detallado original con los datos del registro de corrección
     * @param aporteDetallado
     *        DTO con la información del aporte detallado
     * @param registroC
     *        DTO con la información del registro de corrección
     * @return <b>AporteDetalladoModeloDTO</b>
     *         DTO con la información del aporte detallado actualizado
     */
    private AporteDetalladoModeloDTO ajustarAporteDetallado(AporteDetalladoModeloDTO aporteDetallado,
            RegistroDetalladoModeloDTO registroC) {
        String firmaMetodo = "PilaBusiness.ajustarAporteDetallado(AporteDetalladoModeloDTO, RegistroDetalladoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AporteDetalladoModeloDTO aporteDetalladoTemp = aporteDetallado;

        aporteDetalladoTemp.setAporteObligatorio(aporteDetalladoTemp.getAporteObligatorio().add(registroC.getOutAporteObligatorioMod()));
        aporteDetalladoTemp.setValorIBC(aporteDetalladoTemp.getValorIBC().add(registroC.getOutValorIBCMod()));
        aporteDetalladoTemp.setValorMora(aporteDetalladoTemp.getValorMora().add(registroC.getOutValorMoraCotizanteMod()));
        aporteDetalladoTemp.setDiasCotizados((short) (aporteDetalladoTemp.getDiasCotizados() + registroC.getOutDiasCotizadosMod()));
        aporteDetalladoTemp.setTarifa(registroC.getOutTarifaMod());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aporteDetalladoTemp;
    }

    /**
     * Metodo que actualiza el DTO del aporte general original con base en los datos del registro de corrección
     * @param aporteGeneral
     *        DTO con la información del aporte general
     * @param registroC
     *        DTO con la información del registro de corrección
     * @return <b>AporteDetalladoModeloDTO</b>
     *         DTO con la información del aporte general actualizado
     */
    private AporteGeneralModeloDTO ajustarAporteGeneral(AporteGeneralModeloDTO aporteGeneral, RegistroDetalladoModeloDTO registroC) {
        String firmaMetodo = "PilaBusiness.ajustarAporteGeneral(AporteDetalladoModeloDTO, RegistroDetalladoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AporteGeneralModeloDTO aporteGeneralTemp = aporteGeneral;

        aporteGeneralTemp.setValorTotalAporteObligatorio(
                aporteGeneralTemp.getValorTotalAporteObligatorio().add(registroC.getOutAporteObligatorioMod()));
        aporteGeneralTemp.setValorInteresesMora(aporteGeneralTemp.getValorInteresesMora().add(registroC.getOutValorMoraCotizanteMod()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aporteGeneralTemp;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarDatosFase3SimuladaAdicionCorreccion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO)
     */
    @Override
    public ResultadoSimulacionNovedadDTO consultarDatosFase3SimuladaAdicionCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion) {
        String firmaMetodo = "PilaBusiness.consultarDatosFase3SimuladaAdicionCorreccion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoSimulacionNovedadDTO result = new ResultadoSimulacionNovedadDTO();

        // se consulta el registro detallado C indicado
        RegistroDetalladoModeloDTO registroC = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

        CabeceraPestanaAporteNovedadDTO cabecera = null;
        if (registroC != null) {
            // se diligencia la información de la cabecera con base en el registro de corrección
            cabecera = new CabeceraPestanaAporteNovedadDTO();
            cabecera.diligenciarPorRegistroDetallado(registroC);

            result.setCabecera(cabecera);
        }

        // se consultan las novedades vigentes del cotizante y se agregan al DTO de salida
        result.setNovedadesVigentes(consultasCore.consultarNovedadesVigentesCotizante(registroC.getTipoIdentificacionCotizante(),
                registroC.getNumeroIdentificacionCotizante()));

        // se consultan las novedades presentes en el registro de corrección y se agregan al DTO de salida
        result.setNovedadesRegistroDetallado(consultasStaging.consultarNovedadesRegistroDetallado(registroC.getId()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#aprobarProcesoNovedadesAdicionCorreccion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO aprobarProcesoNovedadesAdicionCorreccion(
            RegistrarCorreccionAdicionDTO criteriosSimulacion, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.aprobarProcesoNovedadesAdicionCorreccion(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //logger.info("PROCESANDO CRITERIOS DE SIMULACION: " + criteriosSimulacion.toString());
        
        ResultadoAprobacionCorreccionAporteDTO result = null;

        // se comprueba que se reciba un ID de Regisstro Detallado
        if (criteriosSimulacion.getIdRegistroDetalladoCorreccionC() != null) {
        	
            // se consulta el registro detallado
            RegistroDetalladoModeloDTO registroDetallado = consultasStaging
                    .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

            // se consultan las novedades temporales generadas
            List<TemNovedadModeloDTO> novedadesTemporales = consultasPila
                    .consultarNovedadesTemporalesPorRegistroDetallado(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

            //logger.info("LAS NOVEDADES ASOCIADAS AL REGISTRO DETALLADO DE CORRECCION " + criteriosSimulacion.getIdRegistroDetalladoCorreccionC() + " SON : " + novedadesTemporales.toString());
            
            // se actualiza el estado de las novedades temporales marcadas para aplicación
            consultasPila.actualizarNovedadesTemporales(novedadesTemporales);

            // se marca el registro detallado con la marca de fase 3
            registroDetallado.setOutRegistradoNovedad(true);
            consultasStaging.actualizaRegistroDetallado(registroDetallado.convertToEntity());
        }
        else {
            // caso contrario, se retorna null
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    
    public Long aprobarProcesamientoNovedadesAdicionCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.aprobarProcesoNovedadesAdicionCorreccion(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //logger.info("PROCESANDO CRITERIOS DE SIMULACION: " + criteriosSimulacion.toString());

        // se comprueba que se reciba un ID de Regisstro Detallado
        if (criteriosSimulacion.getIdRegistroDetalladoCorreccionC() != null) {
        	
            // se consulta el registro detallado
            RegistroDetalladoModeloDTO registroDetallado = consultasStaging
                    .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

            // se consultan las novedades temporales generadas
            List<TemNovedadModeloDTO> novedadesTemporales = consultasPila
                    .consultarNovedadesTemporalesPorRegistroDetallado(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

            //logger.info("LAS NOVEDADES ASOCIADAS AL REGISTRO DETALLADO DE CORRECCION " + criteriosSimulacion.getIdRegistroDetalladoCorreccionC() + " SON : " + novedadesTemporales.toString());
            
            // se actualiza el estado de las novedades temporales marcadas para aplicación
            consultasPila.actualizarNovedadesTemporales(novedadesTemporales);

            //logger.info("LAS NOVEDADES ACTUALIZADAS ASOCIADAS AL REGISTRO DETALLADO DE CORRECCION " + criteriosSimulacion.getIdRegistroDetalladoCorreccionC() + " SON : " + novedadesTemporales.toString());
            
            // se marca el registro detallado con la marca de fase 3
            registroDetallado.setOutRegistradoNovedad(true);
            consultasStaging.actualizaRegistroDetallado(registroDetallado.convertToEntity());
        }
        else {
            // caso contrario, se retorna null
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return 0l;
    }
    
//    /**
//     * Método encargado de ubicar una novedad temporal en listado que corresponda a una novedad detallada
//     * @param novedadDetallada
//     *        DTO de novedad detallada a ubicar entre las temporales
//     * @param novedadesTemporales
//     *        Listado de las novedades temporales asociadas a la planilla
//     * @return <b>TemNovedadModeloDTO</b>
//     *         DTO de novedad temporal en la lista que coincide con la novedad detallada
//     */
//    @Deprecated
//    private TemNovedadModeloDTO ubicarNovedadTemporal(RegistroDetalladoNovedadModeloDTO novedadDetallada,
//            List<TemNovedadModeloDTO> novedadesTemporales) {
//        String firmaMetodo = "PilaBusiness.ubicarNovedadTemporal(RegistroDetalladoNovedadModeloDTO, List<TemNovedadModeloDTO>)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
//
//        // se recorren las novedades temporales
//        for (TemNovedadModeloDTO novedadTemporal : novedadesTemporales) {
//            if (novedadDetallada.getRegistroDetallado().equals(novedadTemporal.getRegistroDetallado())
//                    && ((novedadDetallada.getTipoTransaccion() != null && novedadTemporal.getTipoTransaccion() != null
//                            && novedadDetallada.getTipoTransaccion().equals(novedadTemporal.getTipoTransaccion()))
//                            || (ConstantesComunesPila.NOVEDAD_ING.equals(novedadDetallada.getTipoNovedad())
//                                    && novedadTemporal.getEsIngreso() != null && novedadTemporal.getEsIngreso())
//                            || (ConstantesComunesPila.NOVEDAD_RET.equals(novedadDetallada.getTipoNovedad())
//                                    && novedadTemporal.getEsRetiro() != null && novedadTemporal.getEsRetiro()))) {
//
//                return novedadTemporal;
//            }
//        }
//
//        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//        return null;
//    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#finalizarPlanillaAsistida(java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoFinalizacionPlanillaAsistidaDTO finalizarPlanillaAsistida(Long idRegistroGeneral, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.finalizarPlanillaAsistida(Long:"+idRegistroGeneral+", UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoFinalizacionPlanillaAsistidaDTO respuesta = new ResultadoFinalizacionPlanillaAsistidaDTO();

        // se consulta el registro general para identificar la presencia de un registro tipo 4
        RegistroGeneralModeloDTO regGeneral = consultasStaging.consultarRegistroGeneralDTO(idRegistroGeneral);

        // se consultan los registros detallados del registro general
        List<RegistroDetalladoModeloDTO> registrosDetallados = consultasStaging
                .consultarRegistrosDetalladosPorRegistroGeneral(idRegistroGeneral);

        // se establece si se trata de un archivo de adición o corrección
        respuesta.setEsAdicion(true);
        if (regGeneral != null && !MotivoProcesoPilaManualEnum.ARCHIVO_ADICION.equals(regGeneral.getOutMotivoProcesoManual())) {
            respuesta.setEsAdicion(false);
        }

        // se establece la cantidad de aportes afectados
        respuesta.setCantidadAportesAfectados(calcularCantidadAportes(registrosDetallados, respuesta.getEsAdicion()));

        // se valida que todos los aportes temporales que aplicaran hayan sido procesados por el ESB
        respuesta.setAportesPendientes(false);
        if (!consultasPila.consultarAportesTemporalesPorRegistroGeneral(idRegistroGeneral).isEmpty()) {
            respuesta.setAportesPendientes(true);
        }

        // se valida que todos las novedades temporales que aplicaran hayan sido procesadas por el ESB
        respuesta.setNovedadesPendientes(false);
        if (!consultasPila.consultarNovedadesTemporalesPorRegistroGeneral(idRegistroGeneral).isEmpty()) {
            respuesta.setNovedadesPendientes(true);
        }

        //logger.info("respuestaresultadoFinalizacion.getAportesPendientes()" + respuesta.getAportesPendientes());
        //logger.info("respuesta.getNovedadesPendientes()" + respuesta.getNovedadesPendientes());
        
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    /**
     * Método encargado de calcular la cantidad de los aportes causados o corregidos de una planilla
     * @param registrosDetallados
     *        Listado de los registros detallados de la planilla
     * @param esAdicion
     *        Indica que es planilla de Adición, caso contrario, se le considera planilla de corrección
     * @return <b>Integer</b>
     *         Cantidad de aportes causados o corregidos de una planilla
     */
    private Integer calcularCantidadAportes(List<RegistroDetalladoModeloDTO> registrosDetallados, Boolean esAdicion) {
        String firmaMetodo = "PilaBusiness.calcularCantidadAportes(List<RegistroDetalladoModeloDTO>, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Integer cantidadAportes = 0;
        for (RegistroDetalladoModeloDTO registroDetallado : registrosDetallados) {

            /*
             * en una adición, todos los registros detallados con valor de aporte obligatorio mayor a cero cuentan
             * en una corrección, sólo cuenta el primer registro por cotizante que aplique en precondición de fase1
             */
            if ((esAdicion && registroDetallado.getAporteObligatorio().compareTo(BigDecimal.ZERO) > 0)) {
                cantidadAportes++;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cantidadAportes;
    }

    /**
     * Método que indica que un registro detallado de corrección cuenta como modificador de aporte real
     * @param tipoIdCotizanteAnterior
     *        Tipo de identificación del cotizante del registro anterior
     * @param idCotizanteAnterior
     *        Número de identificación del cotizante del registro anterior
     * @param registroDetallado
     *        Registro detallado evaluado
     * @return <b>Boolean</b>
     *         Indicador de aplicabilidad para la cuentad el registro
     */
    //    private Boolean cuentaCorreccion(TipoIdentificacionEnum tipoIdCotizanteAnterior, String idCotizanteAnterior,
    //            RegistroDetalladoModeloDTO registroDetallado) {
    //        String firmaMetodo = "PilaBusiness.calcularCantidadAportes(List<RegistroDetalladoModeloDTO>, Boolean)";
    //        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    //
    //        if (ConstantesComunesPila.CORRECCIONES_C.equals(registroDetallado.getCorrecciones())
    //                && EstadoValidacionRegistroCorreccionEnum.APLICA.equals(registroDetallado.getEstadoValidacionCorreccion())
    //                && (tipoIdCotizanteAnterior == null || !tipoIdCotizanteAnterior.equals(registroDetallado.getTipoIdentificacionCotizante()))
    //                && (idCotizanteAnterior == null || !idCotizanteAnterior.equals(registroDetallado.getNumeroIdentificacionCotizante()))) {
    //            return true;
    //        }
    //
    //        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    //        return false;
    //    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#solicitarEjecucionFase1SimuladaAdicion(java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaAdicion(Long idIndicePlanilla,
            UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.solicitarEjecucionFase1SimuladaAdicion(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Long idTx = consultasPila.ejecutarUSPporFaseSimulada410Adicion(idIndicePlanilla, userDTO.getNombreUsuario(), 0L, true, FasePila2Enum.SIN_PARAMETRO);
        		
        ResultadoValidacionRegistrosAdicionCorrecionDTO result = consultarResultadoProcesamientoPlanillaAdicion(idIndicePlanilla, userDTO);
        result.setIdTransaccion(idTx);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#actualizarEstadosRegistroPlanilla(com.asopagos.dto.ActualizacionEstadosPlanillaDTO)
     */
    @Override
    public Boolean actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO) {
        logger.debug(
                "Inicia PilaBusiness.actualizarEstadosRegistroPlanilla (Long, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, BloqueValidacionEnum )");
        boolean success = actualizarEstadosRegistroPlanilla(actualizacionEstadosPlanillaDTO,
                actualizacionEstadosPlanillaDTO.getActualizaRegistroGeneral());
        logger.debug(
                "Finaliza PilaBusiness.actualizarEstadosRegistroPlanilla (Long, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, BloqueValidacionEnum )");
        return success;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarResultadoProcesamientoPlanillaAdicion(java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionRegistrosAdicionCorrecionDTO consultarResultadoProcesamientoPlanillaAdicion(Long idIndicePlanilla,
            UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.consultarResultadoProcesamientoPlanillaAdicion(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoValidacionRegistrosAdicionCorrecionDTO result = new ResultadoValidacionRegistrosAdicionCorrecionDTO();

        // se consultan los registros detallados para el archivo de adición
        List<RegistroDetalladoModeloDTO> registrosDetalladosAdicion = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idIndicePlanilla, true, false, true);

        if (registrosDetalladosAdicion != null && !registrosDetalladosAdicion.isEmpty()) {

            // se consultan el tipo y numero de ID del aportante para validaciones V3
            RespuestaConsultaEmpleadorDTO registroGeneralAdicion = consultasStaging.consultarEstadoGeneralPlanilla(idIndicePlanilla);

            // se inicia el diligenciamiento el DTO de respuesta, campos generales
            result.setNumeroPlanilla(registroGeneralAdicion.getNumeroPlanilla());
            result.setTipoPlanilla(registroGeneralAdicion.getTipoPlanilla());
            result.setFechaProceso(
                    consultasPila.consultarFechaProcesoIndicePlanilla(registroGeneralAdicion.getIdIndicePlanilla()).getTime());

            List<ConjuntoResultadoRegistroCorreccionCDTO> resultados = new ArrayList<>();

            for (RegistroDetalladoModeloDTO regDet : registrosDetalladosAdicion) {
                TipoAfiliadoEnum tipoAfiliadoEnum = null;
                TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum
                        .obtenerTipoCotizante(regDet.getTipoCotizante().intValue());

                if (tipoCotizanteEnum != null) {
                    tipoAfiliadoEnum = tipoCotizanteEnum.getTipoAfiliado();
                }

                // se agrega el ID del registro general de la corrección en la salida
                if (result.getIdRegGeneralAdicionCorreccion() == null) {
                    result.setIdRegGeneralAdicionCorreccion(regDet.getRegistroGeneral());
                }

                // se diligencia un DTO de conjunto de resultado para la salida
                ConjuntoResultadoRegistroCorreccionCDTO resultadoC = new ConjuntoResultadoRegistroCorreccionCDTO();

                resultadoC.setIdCotizanteC(regDet.getNumeroIdentificacionCotizante());
                    resultadoC.setSecuenciaC(regDet.getRegistroControl());
                    resultadoC.setEstadoEvaluacionC(regDet.getEstadoEvaluacion());
                    if (regDet.getAporteObligatorio() != null) {
                        resultadoC.setAporteObligatorioC(regDet.getAporteObligatorio().intValue());
                    }
                    else {
                        resultadoC.setAporteObligatorioC(0);
                    }
                    resultadoC.setTipoAfiliadoC(tipoAfiliadoEnum);
                    resultadoC.setEstadoV0C(regDet.getOutEstadoValidacionV0());
                    resultadoC.setEstadoV1C(regDet.getOutEstadoValidacionV1());
                    resultadoC.setEstadoV2C(regDet.getOutEstadoValidacionV2());
                    resultadoC.setEstadoV3C(regDet.getOutEstadoValidacionV3());
                    resultadoC.setResultadoValidacionCorreccionC(regDet.getEstadoValidacionCorreccion());
                    resultadoC.setIdRegDetC(regDet.getId());
                    resultadoC.setRegistradoFase1(regDet.getOutRegistrado());
                    resultadoC.setAporteAnulado(EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO
                            .equals(regDet.getEstadoValidacionCorreccion()));

                    // se agrega el resultado C
                    resultados.add(resultadoC);
               /* }*/

                // se determina sí el USP de la fase 2 ya se ejecutó
                if (result.getHayDatosFase2() == null) {
                    if (EstadoRegistroAportesArchivoEnum.PROCESADO_APORTE.equals(regDet.getOutEstadoRegistroAporte())
                            || EstadoRegistroAportesArchivoEnum.PENDIENTE_POR_REGISTO_RELACION_APORTE
                                    .equals(regDet.getOutEstadoRegistroAporte())) {
                        result.setHayDatosFase2(true);
                    }
                }
            }

            result.setResultados(resultados);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#aplicarRegistroPlanillasAdicion(com.asopagos.dto.pila.DetalleTablaAportanteDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    public void aplicarRegistroPlanillasAdicion(DetalleTablaAportanteDTO registroPlanillaAdicion, UserDTO userDTO) {
        logger.debug("Inicia PilaCompositeBusiness.registrarPlanillasAdicion(DetalleTablaAportanteDTO, UserDTO)");
        actualizarRegistroPlanillasAdicion(registroPlanillaAdicion, EstadoAporteEnum.VIGENTE, userDTO);
        logger.debug("Finaliza PilaCompositeBusiness.registrarPlanillasAdicion(DetalleTablaAportanteDTO, UserDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.composite.service.PilaCompositeService#noAplicarRegistroPlanillasAdicion(com.asopagos.dto.pila.DetalleTablaAportanteDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    public void noAplicarRegistroPlanillasAdicion(DetalleTablaAportanteDTO registroPlanillaAdicion, UserDTO userDTO) {
        logger.debug("Inicia PilaCompositeBusiness.noRegistrarPlanillasAdicion(DetalleTablaAportanteDTO, UserDTO)");
        actualizarRegistroPlanillasAdicion(registroPlanillaAdicion, EstadoAporteEnum.EVALUACION_NO_APLICADA, userDTO);
        logger.debug("Finaliza PilaCompositeBusiness.noRegistrarPlanillasAdicion(DetalleTablaAportanteDTO, UserDTO)");
    }

    /**
     * Método que se encarga de actualizar el estado de una aporte detallado
     * @param registroPlanillaAdicion
     * @param estadoAporte
     * @param userDTO
     */
    private void actualizarRegistroPlanillasAdicion(DetalleTablaAportanteDTO registroPlanillaAdicion, EstadoAporteEnum estadoAporte,
            UserDTO userDTO) {
        logger.debug("Inicia PilaCompositeBusiness.actualizarPlanillasAdicion(DetalleTablaAportanteDTO, EstadoAporteEnum, UserDTO)");
        RegistroDetallado registroDetallado = consultasStaging.consultarRegistroDetallado(registroPlanillaAdicion.getIdRegistroDetallado());
        registroDetallado.setEstadoEvaluacion(estadoAporte);
        consultasStaging.actualizaRegistroDetallado(registroDetallado);
        logger.debug("Finaliza PilaCompositeBusiness.actualizarPlanillasAdicion(DetalleTablaAportanteDTO, EstadoAporteEnum, UserDTO)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarComparativoAporte(java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    public InformacionAporteAdicionDTO consultarComparativoAporte(Long idRegistroDetallado, UserDTO userDTO) {
        logger.debug("Inicia PilaCompositeBusiness.consultarComparativoAporte(Long, UserDTO)");
        InformacionAporteAdicionDTO infoAporteAdd = new InformacionAporteAdicionDTO();
        infoAporteAdd.setInfoRegistroAporte(consultasStaging.obtenerRegistroDetalladoEspecifico(idRegistroDetallado));
        infoAporteAdd.setSimulacionRegistroAporte(consultasPila.contultarAporteTemporal(idRegistroDetallado));
        logger.debug("Finaliza PilaCompositeBusiness.consultarComparativoAporte(Long, UserDTO)");
        return infoAporteAdd;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#verificarEstadoRegistrosAdicionCorreccion(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.FasePila2Enum, java.lang.Boolean, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> verificarEstadoRegistrosAdicionCorreccion(Long idRegistroGeneral, FasePila2Enum fase,
            Boolean actualizarEstado, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.verificarEstadoRegistrosAdicionCorreccion(Long, FasePila2Enum, Boolean, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " con los parámetros:: " + 
        		"idRegistroGeneral: " + (idRegistroGeneral != null ? idRegistroGeneral : "null") + 
        		",fase: " + (fase != null ? fase.name() : "null") + 
        		",actualizarEstado: " + actualizarEstado);
        Map<String, String> result = new HashMap<>();
        
        // se consulta el registro general
        RegistroGeneralModeloDTO regGen = consultasStaging.consultarRegistroGeneralDTO(idRegistroGeneral);
        
        // sí el registro general aún presenta marca de "en proceso" por parte de los SP, se le quita
        if(regGen != null && regGen.getOutEnProceso()){
            regGen.setOutEnProceso(Boolean.FALSE);
            consultasStaging.actualizaRegistroGeneral(regGen.convertToEntity());
        }

        List<RegistroDetalladoModeloDTO> resultList = consultasStaging.consultarRegistrosDetalladosPorRegistroGeneral(idRegistroGeneral);

        if (resultList != null && !resultList.isEmpty()) {
            // se determina sí todos los registros que presentan marca en una corrección tienen anulación
            Boolean marcarMixta = comprobacionAnulaciones(resultList);
            
            // variables de control
            Boolean marcaRegistrar = null;
            String mensajeSalida = ConstantesParaMensajes.CONTINUAR;
            List<EstadoRegistroAportesArchivoEnum> estadosNoAceptables = new ArrayList<>();
            BloqueValidacionEnum bloqueValidacion = null;
            boolean estadoPositivo = true;
            boolean procesoNovedad = false;
            EstadoProcesoArchivoEnum estadoProceso = null;
            AccionProcesoArchivoEnum accionProceso = null;

            // se definen los estados NO aceptables para cambio de fase
            switch (fase) {
                case PILA2_FASE_1:
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD);
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_OK);
                    bloqueValidacion = BloqueValidacionEnum.BLOQUE_7_OI;
                    break;
                case PILA2_FASE_2:
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.PENDIENTE_POR_REGISTO_RELACION_APORTE);
                    bloqueValidacion = BloqueValidacionEnum.BLOQUE_8_OI;
                    break;
                case PILA2_FASE_3:
                    // la fase 3 no presenta estados NO válidos
                    bloqueValidacion = BloqueValidacionEnum.BLOQUE_9_OI;
                    break;
                case PILA2_FASE_4:
                    // la fase 4 no presenta estados NO válidos
                    bloqueValidacion = BloqueValidacionEnum.BLOQUE_10_OI;
                    break;
                default:
                    break;
            }

            for (RegistroDetalladoModeloDTO detalle : resultList) {
                // sólo para registros detallados que no tengan marca de correcciones "A"
                if (!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones())) {

                    // se establece el valor de la marca de acción para registro
                    switch (fase) {
                        case PILA2_FASE_1:
                            marcaRegistrar = detalle.getOutRegistrado();
                            break;
                        case PILA2_FASE_2:
                            marcaRegistrar = detalle.getOutRegistradoAporte();
                            break;
                        case PILA2_FASE_3:
                            marcaRegistrar = detalle.getOutRegistradoNovedad();
                            break;
                        default:
                            break;
                    }

                    if (estadosNoAceptables.contains(detalle.getOutEstadoRegistroAporte())) {
                        // caso especial, en la fase 1, no se considera cuando se marca como "NO REGISTRAR"
                        if (!FasePila2Enum.PILA2_FASE_1.equals(fase) || (marcaRegistrar != null && marcaRegistrar)) {
                            mensajeSalida = ConstantesParaMensajes.NO_CONTINUAR_INCONSISTENCIAS_SIN_GESTIONAR;
                            estadoPositivo = false;
                        }
                    }
                    else {
                        // caso especial, cuando en la fase 3 procesa novedad
                        if (EstadoRegistroAportesArchivoEnum.PROCESADO_NOVEDADES.equals(detalle.getOutEstadoRegistroAporte())) {
                            procesoNovedad = true;
                        }
                    }

                    // cuando no se cuenta con marcas de registrar en una fase diferente a la 4
                    //if (marcaRegistrar == null && !FasePila2Enum.PILA2_FASE_4.equals(fase)) {
                    //    mensajeSalida = !marcarMixta ? ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION : 
                    //        ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION_MIXTA;
                    //    estadoProceso = null;
                    //    accionProceso = AccionProcesoArchivoEnum.EN_ESPERA;
                    //    break;
                    //}
                }
            }

            // sí todos los registros tienen acción y se ha solitado incluir el cambio de estados de archivos
            if (!ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION.equals(mensajeSalida)
                    && !ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION_MIXTA.equals(mensajeSalida) && actualizarEstado) {
                ActualizacionEstadosPlanillaDTO actualizacionEstadosDTO = new ActualizacionEstadosPlanillaDTO();
                actualizacionEstadosDTO.setIdRegistroGeneral(idRegistroGeneral);
                actualizacionEstadosDTO.setBloqueValidacion(bloqueValidacion);

                Boolean marcaHabilitacion = true;

                switch (fase) {
                    case PILA2_FASE_1:
                        if (estadoPositivo) {
                            estadoProceso = EstadoProcesoArchivoEnum.PROCESADO_VS_BD;
                            accionProceso = AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE_MANUAL;
                        }
                        else {
                            estadoProceso = EstadoProcesoArchivoEnum.PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD;
                            accionProceso = AccionProcesoArchivoEnum.PASAR_A_BANDEJA;
                        }
                        break;
                    case PILA2_FASE_2:
                        if (estadoPositivo) {
                            estadoProceso = EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES;
                            accionProceso = AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA_MANUAL;
                        }
                        else {
                            estadoProceso = EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES;
                            accionProceso = AccionProcesoArchivoEnum.PASAR_A_BANDEJA;
                        }
                        break;
                    case PILA2_FASE_3:
                        if (estadoPositivo) {
                            if (procesoNovedad) {
                                estadoProceso = EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES;
                            }
                            else {
                                estadoProceso = EstadoProcesoArchivoEnum.PROCESADO_SIN_NOVEDADES;
                            }
                            accionProceso = AccionProcesoArchivoEnum.NOTIFICAR_RECAUDO_MANUAL;
                        }
                        break;
                    case PILA2_FASE_4:
                        estadoProceso = EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO;
                        accionProceso = AccionProcesoArchivoEnum.PROCESO_FINALIZADO_MANUAL;
                        marcaHabilitacion = false;
                        break;
                    default:
                        break;
                }

                if (userDTO.getNombreUsuario() != null) {
                	actualizacionEstadosDTO.setUsuario(userDTO.getNombreUsuario());
                }
              
                actualizacionEstadosDTO.setEstadoProceso(estadoProceso);
                actualizacionEstadosDTO.setAccionProceso(accionProceso);
                actualizacionEstadosDTO.setMarcaHabilitacionGestionManual(marcaHabilitacion);

                actualizarEstadosRegistroPlanilla(actualizacionEstadosDTO, true);
            }

            result.put(ConstantesParaMensajes.RESULTADO_VALIDACION, mensajeSalida);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        }
        else {
            // no se tienen registros detallados asociados al número de planilla, lanza excepción técnica
            String mensaje = MensajesErrorComunesEnum.ERROR_REGISTROS_DETALLADOS_FALTANTE.getReadableMessage(idRegistroGeneral.toString());
            throw new TechnicalException(mensaje);
        }
    }

    /**
     * Método encargado de determinar sí una planilla tiene anulaciones
     * @param registros 
     * @param fase 
     * @return
     */
    private Boolean comprobacionAnulaciones(List<RegistroDetalladoModeloDTO> registros) {
        String firmaMetodo = "PilaBusiness.comprobacionAnulaciones(List<RegistroDetallado>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Boolean result = Boolean.FALSE;
        
        registros.parallelStream().anyMatch(detalle -> 
        	(!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones()) && EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO.equals(detalle.getEstadoValidacionCorreccion())) == true);
        
//        for (RegistroDetalladoModeloDTO detalle : registros) {
//            // sólo para registros detallados que no tengan marca de correcciones "A"
//            if (!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones()) && 
//                    EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO.equals(detalle.getEstadoValidacionCorreccion())) {
//                result = Boolean.TRUE;
//                break;
//            }
//        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de determinar sí una planilla tiene anulaciones
     * @param registros 
     * @param fase 
     * @return
     */
    private Boolean comprobacionAnulacionesRegistroDetallado(List<RegistroDetallado> registros) {
        String firmaMetodo = "PilaBusiness.comprobacionAnulacionesRegistroDetallado(List<RegistroDetallado>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Boolean result = Boolean.FALSE;
        
        registros.parallelStream().anyMatch(detalle -> 
        	(!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones()) && EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO.equals(detalle.getEstadoRegistroCorreccion())) == true);
        
//        for (RegistroDetalladoModeloDTO detalle : registros) {
//            // sólo para registros detallados que no tengan marca de correcciones "A"
//            if (!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones()) && 
//                    EstadoValidacionRegistroCorreccionEnum.NO_APLICA_REGISTRO_ANULADO.equals(detalle.getEstadoValidacionCorreccion())) {
//                result = Boolean.TRUE;
//                break;
//            }
//        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#actualizarEstadosRegistroPlanillaEstadoBloque(java.lang.String)
     */
    public void actualizarEstadosRegistroPlanillaEstadoBloque(String numeroPlanilla) {
        logger.debug("Inicia PilaBusiness.actualizarEstadosRegistroPlanillaEstadoBloque(String)");
        RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneralPorNumeroPlanilla(numeroPlanilla);
        if (registroGeneral != null) {

            EstadoProcesoArchivoEnum estadoProceso = registroGeneral.getOutEstadoArchivo();
            ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO = new ActualizacionEstadosPlanillaDTO();
            actualizacionEstadosPlanillaDTO.setIdRegistroGeneral(registroGeneral.getId());
            actualizacionEstadosPlanillaDTO.setEstadoProceso(registroGeneral.getOutEstadoArchivo());
            // 397 == REGISTRADO_O_RELACIONADO_LOS_APORTES bloque 8  
            // 398 == PROCESADO_SIN_NOVEDADES o  PROCESADO_NOVEDADES bloque 9
            // default no se actualiza
            switch (estadoProceso) {
                case REGISTRADO_O_RELACIONADO_LOS_APORTES:
                    actualizacionEstadosPlanillaDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_8_OI);
                    actualizacionEstadosPlanillaDTO.setAccionProceso(AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA_MANUAL);
                    actualizarEstadosRegistroPlanilla(actualizacionEstadosPlanillaDTO, false);
                    break;
                case PROCESADO_SIN_NOVEDADES:
                case PROCESADO_NOVEDADES:
                    actualizacionEstadosPlanillaDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_9_OI);
                    actualizacionEstadosPlanillaDTO.setAccionProceso(AccionProcesoArchivoEnum.NOTIFICAR_RECAUDO_MANUAL);
                    actualizarEstadosRegistroPlanilla(actualizacionEstadosPlanillaDTO, false);
                    break;
                default:
                    logger.debug("Finaliza PilaBusiness.actualizarEstadosRegistroPlanillaEstadoBloque(String) :  no se actualiza estado");
            }
        }
        logger.debug("Finaliza PilaBusiness.actualizarEstadosRegistroPlanillaEstadoBloque(String)");
    }

    /**
     * Método que actualiza el estado y la acción de un indice de planilla con su estado por bloque, si se indica tambien actualiza el
     * estado del registro general
     * @param idRegistroGeneral
     * @param estadoProceso
     * @param accionProceso
     * @param bloqueValidacion
     * @param actualizaRegistroGeneral
     * @return
     */
    private boolean actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO,
            boolean actualizaRegistroGeneral) {
        logger.info(
                "Inicia PilaBusiness.actualizarEstadosRegistroPlanilla (ActualizacionEstadosPlanillaDTO -> EstadoProceso"
                +actualizacionEstadosPlanillaDTO.getEstadoProceso()+" | AccionProceso"
                +actualizacionEstadosPlanillaDTO.getAccionProceso()+
                ", boolean "+actualizaRegistroGeneral+")");
                logger.info("**__**actualizarEstadosRegistroPlanilla registro general: "+actualizacionEstadosPlanillaDTO.getIdRegistroGeneral());
        boolean success = false;
        Long idIndicePlanilla = null;
        IndicePlanilla indicePlanilla = null;
        RegistroGeneral registroGeneral = null;
        EstadoArchivoPorBloque estadoArchivoPorBloque = null;
        EstadoProcesoArchivoEnum estadoPrevio = null;
        AccionProcesoArchivoEnum accionPrevia = null;
        Short claseUsuario = null;
        String usuario = ConstantesComunesPila.USUARIO_SISTEMA;
        Date fechaPrevia = null;
        HistorialEstadoBloque historialEstado = null;
        EstadoProcesoArchivoEnum estadoProceso = actualizacionEstadosPlanillaDTO.getEstadoProceso();
        AccionProcesoArchivoEnum accionProceso = actualizacionEstadosPlanillaDTO.getAccionProceso();

        registroGeneral = consultasStaging.consultarRegistroGeneral(actualizacionEstadosPlanillaDTO.getIdRegistroGeneral());
        if (registroGeneral != null) {
            idIndicePlanilla = registroGeneral.getRegistroControl();
            indicePlanilla = consultasPila.consultarIndicePlanilla(idIndicePlanilla);
                         logger.info("**__**pilabussinesidIndicePlanilla: "+idIndicePlanilla);
            if (indicePlanilla != null) {
                estadoArchivoPorBloque = consultasPila.consultarEstadoArchivoPorBloque(idIndicePlanilla);
                if (estadoArchivoPorBloque != null) {
                    switch (actualizacionEstadosPlanillaDTO.getBloqueValidacion()) {
                        case BLOQUE_0_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque0();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque0();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque0();

                            estadoArchivoPorBloque.setEstadoBloque0(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque0(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque0(new Date());
                            break;
                        case BLOQUE_1_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque1();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque1();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque1();

                            estadoArchivoPorBloque.setEstadoBloque1(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque1(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque1(new Date());
                            break;
                        case BLOQUE_2_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque2();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque2();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque2();

                            estadoArchivoPorBloque.setEstadoBloque2(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque2(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque2(new Date());
                            break;
                        case BLOQUE_3_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque3();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque3();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque3();

                            estadoArchivoPorBloque.setEstadoBloque3(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque3(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque3(new Date());
                            break;
                        case BLOQUE_4_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque4();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque4();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque4();

                            estadoArchivoPorBloque.setEstadoBloque4(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque4(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque4(new Date());
                            break;
                        case BLOQUE_5_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque5();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque5();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque5();

                            estadoArchivoPorBloque.setEstadoBloque5(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque5(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque5(new Date());
                            break;
                        case BLOQUE_6_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque6();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque6();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque6();

                            estadoArchivoPorBloque.setEstadoBloque6(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque6(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque6(new Date());
                            break;
                        case BLOQUE_7_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque7();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque7();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque7();
                            usuario = actualizacionEstadosPlanillaDTO.getUsuario();
                            claseUsuario = actualizacionEstadosPlanillaDTO.getEsReproceso399() ? (short) 5 : (short) 3;

                            estadoArchivoPorBloque.setEstadoBloque7(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque7(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque7(new Date());
                            break;
                        case BLOQUE_8_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque8();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque8();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque8();
                            usuario = actualizacionEstadosPlanillaDTO.getUsuario();
                            claseUsuario = actualizacionEstadosPlanillaDTO.getEsReproceso399() ? (short) 5 : (short) 3;

                            estadoArchivoPorBloque.setEstadoBloque8(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque8(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque8(new Date());
                            break;
                        case BLOQUE_9_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque9();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque9();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque9();

                            estadoArchivoPorBloque.setEstadoBloque9(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque9(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque9(new Date());
                            break;
                        case BLOQUE_10_OI:
                            estadoPrevio = estadoArchivoPorBloque.getEstadoBloque10();
                            accionPrevia = estadoArchivoPorBloque.getAccionBloque10();
                            fechaPrevia = estadoArchivoPorBloque.getFechaBloque10();

                            estadoArchivoPorBloque.setEstadoBloque10(estadoProceso);
                            estadoArchivoPorBloque.setAccionBloque10(accionProceso);
                            estadoArchivoPorBloque.setFechaBloque10(new Date());
                            break;
                        default:
                            logger.error(
                                    "PilaBusiness.actualizarEstadosRegistroPlanilla (Long, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, "
                                            + "BloqueValidacionEnum ) :: no es bloque de validacion valido para transicion de estados OI");
                            return success;
                    }
                    if (actualizaRegistroGeneral) {
                        registroGeneral.setFechaActualizacion(Calendar.getInstance().getTime());
                        registroGeneral.setOutEstadoArchivo(estadoProceso);
                        consultasStaging.actualizaRegistroGeneral(registroGeneral);
                    }

                    historialEstado = new HistorialEstadoBloque();
                    historialEstado.setIdIndicePlanilla(idIndicePlanilla);
                    historialEstado.setEstado(estadoPrevio);
                    historialEstado.setAccion(accionPrevia);
                    historialEstado.setFechaEstado(fechaPrevia);
                    historialEstado.setBloque(actualizacionEstadosPlanillaDTO.getBloqueValidacion());
                    historialEstado.setUsuarioEspecifico(usuario);
                    historialEstado.setClaseUsuario(claseUsuario);

                    consultasPila.actualizarEstadoArchivoPorBloque(estadoArchivoPorBloque, historialEstado);

                    indicePlanilla.setEstadoArchivo(estadoProceso);
                    indicePlanilla.setHabilitadoProcesoManual(actualizacionEstadosPlanillaDTO.getMarcaHabilitacionGestionManual());

                    /*
                     * en el caso de que se haya finalizado el procesamiento de la planilla, se actualiza la fecha de procesamiento
                     * y se aplica el mismo estado al archivo A
                     */
                    if (EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO.equals(estadoProceso)
                            || EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO_MANUAL.equals(estadoProceso)) {
                        indicePlanilla.setFechaProceso(new Date());
                        finalizarArchivoA(indicePlanilla.getIdPlanilla(), estadoProceso, accionProceso);
                    }
 logger.info("antes de  consultasPila.actualizarIndicePlanilla indicePlanilla:"+indicePlanilla);
                    consultasPila.actualizarIndicePlanilla(indicePlanilla);

                    success = true;
                }
            }
        }
        logger.info(
                "Finaliza PilaBusiness.actualizarEstadosRegistroPlanilla (Long, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, BloqueValidacionEnum, boolean) la consulta a stagin viene null");
        return success;
    }

    /**
     * Método encargado de la finalización del archivo A en el B10
     * @param idPlanilla
     * @param estadoProceso
     * @param accionProceso
     */
    private void finalizarArchivoA(Long idPlanilla, EstadoProcesoArchivoEnum estadoProceso, AccionProcesoArchivoEnum accionProceso) {
        String firmaMetodo = "PilaBusiness.finalizarArchivoA(Long: "+idPlanilla+", EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // listado con los tipos de archivo A
        List<TipoArchivoPilaEnum> listaTiposA = new ArrayList<>();
        listaTiposA.add(TipoArchivoPilaEnum.ARCHIVO_OI_A);
        listaTiposA.add(TipoArchivoPilaEnum.ARCHIVO_OI_AP);
        listaTiposA.add(TipoArchivoPilaEnum.ARCHIVO_OI_AR);
        listaTiposA.add(TipoArchivoPilaEnum.ARCHIVO_OI_APR);

        // listado de los estados deseados de archivo
        List<EstadoProcesoArchivoEnum> estadosA = new ArrayList<>();
        estadosA.add(EstadoProcesoArchivoEnum.VALIDACIONES_FINALIZADAS);

        List<EstadoArchivoPorBloque> estados = consultasPila.consultarEstadoArchivoPorBloquePorNumeroYTipo(idPlanilla, listaTiposA,
                estadosA);

        if (estados.size() > 1) {
            throw new TechnicalException(
                    MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + " :: Se presentan multiples resultados de archivo A activos");
        }

        EstadoArchivoPorBloque estadoA = !estados.isEmpty() ? estados.get(0) : null;

        // se actualiza tanto el estado por bloque como el estado de índice para el archivo A
        if (estadoA != null) {
            estadoA.setEstadoBloque10(estadoProceso);
            estadoA.setAccionBloque10(accionProceso);
            estadoA.setFechaBloque10(new Date());

            estadoA.getIndicePlanilla().setEstadoArchivo(estadoProceso);
            estadoA.getIndicePlanilla().setFechaProceso(new Date());

            consultasPila.actualizarEstadoArchivoPorBloque(estadoA, null);
            consultasPila.actualizarIndicePlanilla(estadoA.getIndicePlanilla());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#obtenerRegistroGeneral(java.lang.Long)
     */
    @Override
    public RegistroGeneralModeloDTO obtenerRegistroGeneral(Long idRegistroGeneral) {
        String firmaMetodo = "PilaBusiness.obtenerRegistroGeneral(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroGeneralModeloDTO result = null;
        RegistroGeneral resultEntity = consultasStaging.consultarRegistroGeneral(idRegistroGeneral);
        if (resultEntity != null) {
            result = new RegistroGeneralModeloDTO();
            result.convertToDTO(resultEntity);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#registrarNoRegistrarAdicion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> registrarNoRegistrarAdicion(RegistrarCorreccionAdicionDTO registrarAdicionDTO, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.registrarNoRegistrarAdicion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        EstadoAporteEnum nuevoEstado = null;

        if (registrarAdicionDTO.getRegistrar()) {
            // se solicita el registro de la adición
            nuevoEstado = EstadoAporteEnum.VIGENTE;

            // Registrar los resultados de esta “Evaluación vs BD”, asociados a este nuevo registro.
            actualizarDatosRegistro(null, registrarAdicionDTO.getIdRegistroDetalladoCorreccionA(), nuevoEstado, true, userDTO.getNombreUsuario());
        }
        else {
            nuevoEstado = EstadoAporteEnum.EVALUACION_NO_APLICADA;

            actualizarDatosRegistro(null, registrarAdicionDTO.getIdRegistroDetalladoCorreccionA(), nuevoEstado, false, userDTO.getNombreUsuario());

        }

        Map<String, String> result = verificarEstadoRegistrosAdicionCorreccion(registrarAdicionDTO.getIdRegistroGeneralCorreccion(), FasePila2Enum.PILA2_FASE_1,
                true, userDTO);
        
        result.put(ConstantesParaMensajes.NUEVO_ESTADO_EVALUACION, nuevoEstado.name());
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarDatosFase2SimuladaAdicion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO)
     */
    @Override
    public ResultadoSimulacionAporteDetalladoDTO consultarDatosFase2SimuladaAdicion(RegistrarCorreccionAdicionDTO criteriosSimulacion) {
        String firmaMetodo = "PilaBusiness.consultarDatosFase2SimuladaAdicion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoSimulacionAporteDetalladoDTO result = new ResultadoSimulacionAporteDetalladoDTO();

        // se consulta el registro detallado A
        RegistroDetalladoModeloDTO registroA = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionA());

        CabeceraPestanaAporteNovedadDTO cabecera = null;
        if (registroA != null) {
            // se diligencia la información de la cabecera con base en el registro de corrección
            cabecera = new CabeceraPestanaAporteNovedadDTO();
            cabecera.diligenciarPorRegistroDetallado(registroA);
        }

        // se unifica la información en el DTO de salida
        result.setCabecera(cabecera);
        result.setRegDetalladoA(registroA);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /** 
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#aprobarAportesAdicion(com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoAprobacionCorreccionAporteDTO aprobarAportesAdicion(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.aprobarAportesAdicion(RegistrarCorreccionDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoAprobacionCorreccionAporteDTO result = null;

        // se consulta el registro detallado
        RegistroDetalladoModeloDTO registro = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionA());

        // se confirma que se cuente con un registro detallado
        if (registro != null) {
            // se consulta el aporte temporal
            TemAporteModeloDTO aporteTemporal = consultasPila.contultarAporteTemporal(registro.getId());

            // sólo se procede cuando se cuenta con un aporte original, caso contrario se lanza excepción técnica
            if (aporteTemporal != null) {
                // se marca el registro del aporte en el registro como aprobado y vigente
                registro.setOutRegistradoAporte(true);
                registro.setOutEstadoEvaluacionAporte(EstadoAporteEnum.VIGENTE);

                // se quita la marca de simulación del aporte tenmporal
                aporteTemporal.setMarcaAporteSimulado(false);

                // se actualizan los registros en BD
                consultasStaging.actualizaRegistroDetallado(registro.convertToEntity());
                consultasPila.actualizarAporteTemporal(aporteTemporal.convertToEntity());

                // se revisan los registros para determinar sí es válido continuar a la siguiente fase y actualizar el estado de archivo
//                Map<String, String> verificacion = verificarEstadoRegistrosAdicionCorreccion(
//                        criteriosSimulacion.getIdRegistroGeneralCorreccion(), FasePila2Enum.PILA2_FASE_2, true, userDTO);
//                if (verificacion != null) {
//                    result = new ResultadoAprobacionCorreccionAporteDTO();
//                    result.setResultadoValidacion(verificacion.get(ConstantesParaMensajes.RESULTADO_VALIDACION));
//
//                    // en el caso de que sea válido el paso a fase 3, se ejecuta el SP respectivo
//                    if (ConstantesParaMensajes.CONTINUAR.equals(result.getResultadoValidacion())) {
//                        consultasPila.ejecutarUSPporFaseSimulada410(criteriosSimulacion.getIdIndicePlanillaCorreccion(),
//                                userDTO.getNombreUsuario(), FasePila2Enum.PILA2_FASE_3);
//
//                        // se verifica que se hayan generado novedades para la planilla
//                        comprobarExistenciaNovedades(criteriosSimulacion.getIdRegistroGeneralCorreccion(), userDTO);
//                    }
//                }
            }
            // cuando no se encuentra un aporte temporal para un recaudo con valor mayor a cero, se lanza excepción
            else if (registro.getAporteObligatorio().compareTo(BigDecimal.valueOf(0L)) > 0) {
                String mensaje = MensajesErrorComunesEnum.ERROR_APORTE_TEMPORAL_FALTANTE.getReadableMessage(registro.getId().toString());
                throw new TechnicalException(mensaje);
            }
        }
        else {
            String mensaje = MensajesErrorComunesEnum.ERROR_REGISTRO_DETALLADO_FALTANTE
                    .getReadableMessage(criteriosSimulacion.getIdRegistroDetalladoCorreccionA().toString());
            throw new TechnicalException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	/**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarIndicePlanilla(java.lang.Long)
     */
    @Override
    public IndicePlanilla consultarIndicePlanillaEntidad(Long idIndicePlanilla) {
        //Se realiza la consulta a partir del indice planilla
        return consultasPila.consultarIndicePlanilla(idIndicePlanilla);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#comprobarExistenciaNovedades(java.lang.Long)
     */
    @Override
    public void comprobarExistenciaNovedades(Long idRegistroGeneral, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.comprobarExistenciaNovedades(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConsultaNovedadesPorRegistroDTO> registros = consultasStaging
                .consultarCantidadNovedadesPorRegistroDetallado(idRegistroGeneral);

        List<Long> registrosSinNovedad = new ArrayList<>();

        if (!registros.isEmpty()) {
            for (ConsultaNovedadesPorRegistroDTO registro : registros) {
                if (registro.getCantidadNovedadesAsociadas().compareTo(0L) == 0) {
                    registrosSinNovedad.add(registro.getIdRegistroDetallado());
                }
            }
        }

        // se marcan como registrados para novedades aquellos que no tengan novedades asociadas
        if(!registrosSinNovedad.isEmpty()){
        	consultasStaging.marcarRegistrosSinNovedades(registrosSinNovedad);
        }

        // finalmente, se procesa el estado general del archivo, en caso de no presentarse ninguna novedad
        verificarEstadoRegistrosAdicionCorreccion(idRegistroGeneral, FasePila2Enum.PILA2_FASE_3, true, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#marcarPlanillaAsistidaNotificada(java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void marcarPlanillaAsistidaNotificada(Long idRegistroGeneral, UserDTO userDTO) {
        String firmaMetodo = "PilaBusiness.marcarPlanillaAsistidaNotificada(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        verificarEstadoRegistrosAdicionCorreccion(idRegistroGeneral, FasePila2Enum.PILA2_FASE_4, true, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizadosOI(UriInfo uri, HttpServletResponse response) {
        String firmaServicio = "PilaBusiness.buscarArchivosOIProcesadosFinalizados(UriInfo , HttpServletResponse )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivosProcesadosFinalizadosOFDTO> result = consultasPila.verArchivosProcesadosFinalizadosOI(uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarEstadosIndicePlanillaPorId(java.lang.Long)
     */
    @Override
    public EstadoArchivoPorBloqueModeloDTO consultarEstadosIndicePlanillaPorId(Long idPlanilla) {
        String firmaServicio = "PilaBusiness.consultarEstadosIndicePlanillaPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EstadoArchivoPorBloqueModeloDTO result = null;

        EstadoArchivoPorBloque estadoEntity = consultasPila.consultarEstadoArchivoPorBloque(idPlanilla);

        if (estadoEntity != null) {
            result = new EstadoArchivoPorBloqueModeloDTO();
            result.convertToDTO(estadoEntity);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarProcesoPilaActivo()
     */
    @Override
    public Boolean consultarProcesoPilaActivo() {
        String firmaServicio = "PilaBusiness.consultarProcesoPilaActivo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TipoProcesoPilaEnum> listaTipos = new ArrayList<>();
        listaTipos.add(TipoProcesoPilaEnum.CARGA);
        listaTipos.add(TipoProcesoPilaEnum.DESCARGA_CARGA_AUTOMATICA);
        listaTipos.add(TipoProcesoPilaEnum.VALIDACION_BATCH);
        listaTipos.add(TipoProcesoPilaEnum.VALIDACION_WEB);

        Boolean result = consultasPila.consultarExistenciaProcesosPilaPorEstadoYTipo(listaTipos,
                EstadoProcesoValidacionEnum.PROCESO_ACTIVO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /** -----Inicio métodos para vistas 360--------------------- */
    /**
     * @param idRegistroGeneral
     * @return
     */
    @Override
    public DetalleAporteVista360DTO consultarDetalleAporte(Long idRegistroDetallado) {
        String firmaServicio = "PilaBusiness.consultarDetalleAporte(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DetalleAporteVista360DTO detalleAporte = new DetalleAporteVista360DTO();
        try {
            detalleAporte = consultasStaging.consultarDetalleAporte(idRegistroDetallado);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return detalleAporte;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            return detalleAporte;
        }
    }

    @Override
    public List<AportePeriodoCertificadoDTO> consultarAportePeriodo(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,
            Short anio) {
        String firmaServicio = "PilaBusiness.consultarAportePeriodo(TipoIdentificacionEnum, numeroIdentificacion, Anio)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<AportePeriodoCertificadoDTO> aporte = new ArrayList<>();
        try {
            aporte = consultasStaging.consultarAportePeriodo(tipoIdentificacion, numeroIdentificacion, anio);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return aporte;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            return aporte;
        }
    }
    /** -----Fin métodos para vistas 360------------------------ */
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarRegistrosPlanillasParaAgrupar(java.lang.Long, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetalladoModeloDTO> consultarRegistrosPlanillasParaAgrupar(Long idIndicePlanilla, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
    	String firmaServicio = "PilaBusiness.consultarRegistrosPlanillasParaAgrupar(Long, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<RegistroDetalladoModeloDTO> result = consultasStaging.consultarRegistrosPlanillasParaAgrupar(idIndicePlanilla, tipoIdentificacion, numeroIdentificacion);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return result;
    }
    
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#gestionarRegistrosPlanillasParaAgrupar(java.lang.Boolean, java.lang.Long, java.util.List)
	 */
    @Override
	public void gestionarRegistrosPlanillasParaAgrupar(Boolean agrupar, Long idIndicePlanilla, List<Long> idsRegistrosDetallados) {
		String firmaServicio = "PilaBusiness.gestionarRegistrosPlanillasParaAgrupar(Long, TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		consultasStaging.gestionarRegistrosPlanillasParaAgrupar(idsRegistrosDetallados, agrupar, idIndicePlanilla);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarRegistroGeneralPorIdPlanilla(java.lang.Long)
     */
    @Override
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorIdPlanilla(Long idIndicePlanilla) {
        String firmaServicio = "PilaBusiness.consultarRegistroGeneralPorIdPlanilla(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        RegistroGeneralModeloDTO result = consultasStaging.consultarRegistroGeneralPorIdPlanilla(idIndicePlanilla);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#comprobarAsignacionValoresCorreccion(java.lang.Long)
     */
    @Override
    public void comprobarAsignacionValoresCorreccion(Long idPlanillaCorrecion){
        String firmaServicio = "PilaBusiness.comprobarAsignacionValoresCorreccion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // se consultan los registros detallados para el archivo de corrección
        List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorrecion, false, false, null);
        
        calcularDatosValidacion(registrosDetalladosCorreccion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#aprobarDetallesNoRegistrados(java.lang.Long)
     */
    @Override
    public void aprobarDetallesNoRegistrados(Long idPlanillaCorreccion){
        String firmaServicio = "PilaBusiness.aprobarDetallesNoRegistrados(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // se consultan los registros detallados para el archivo de corrección
        List<RegistroDetalladoModeloDTO> registrosDetalladosCorreccion = consultasStaging
                .consultarRegistrosDetalladosPorIdPlanilla(idPlanillaCorreccion, false, false, null);
        
        List<Long> idsRegistrosPorAprobar = new ArrayList<>();
        
        for (RegistroDetalladoModeloDTO registro : registrosDetalladosCorreccion){
            if(EstadoRegistroAportesArchivoEnum.NO_OK.equals(registro.getOutEstadoRegistroAporte()) 
                    || EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD.equals(registro.getOutEstadoRegistroAporte())){
                idsRegistrosPorAprobar.add(registro.getId());
            }
        }
        
        consultasStaging.marcarRegistrosDetalladosAprobados(idsRegistrosPorAprobar);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#limpiarAgrupacionesPlanillaCorreccion(java.lang.Long)
     */
    @Override
    public Response consultarAgrupaciones(Long idPlanilla){
        String firmaServicio = "PilaBusiness.consultarAgrupaciones(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        byte[] result = consultasStaging.consultarAgrupaciones(idPlanilla);       
        Response comprimido = null;
       
        HashMap<String, byte[]> zip = new HashMap<>(); 
    	zip.put("AgrupacionesPila.csv", result);
    	try {
    		comprimido = ComprimidoUtil.comprimirZipResponse(1,zip);
			
		} catch (Exception e) {			
			 throw new TechnicalException(MensajesGeneralConstants.ERROR_EXPORTANDO_ARCHIVO);
		}finally {			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		}    	

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return comprimido;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#limpiarAgrupacionesPlanillaCorreccion(java.lang.Long)
     */
    @Override
    public void limpiarAgrupacionesPlanillaCorreccion(Long idPlanilla){
        String firmaServicio = "PilaBusiness.limpiarAgrupacionesPlanillaCorreccion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        consultasStaging.limpiarAgrupacionesPlanillaCorreccion(idPlanilla);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#actualizarEjecucionGestion
     */
	@Override
	public void actualizarEjecucionGestion(String proceso, Boolean activo, String estado) {
		logger.info("Inicia actualizarEjecucionGestion");

        consultasPila.actualizarEjecucionGestion(proceso, activo, estado);

        logger.info("Finaliza actualizarEjecucionGestion");		
	}

	/** (non-Javadoc)
     * @see com.asopagos.pila.service.PilaService#consultarEstadoEjecucionGestion
     */
	@Override
	public Boolean consultarEstadoEjecucionGestion(String proceso) {
		logger.debug("Inicia consultarEstadoEjecucionGestion");

        Boolean estadoEjecucion = consultasPila.consultarEjecucionGestion(proceso);

        logger.debug("Finaliza consultarEstadoEjecucionGestion");
        
        return estadoEjecucion;
		
	}

    @Override
    public List<Long> indicesPlanillaPorRegistroGeneral(List<Long> regGenerales){
        List<Long> result = new ArrayList<>();
        String firmaServicio = "PilaBusiness.indicesPlanillaPorRegistroGeneral(List<Long> regGenerales)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        result.addAll(consultasPila.consultarIndicePlanillaRegistroGeneral(regGenerales));
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        
        return result;
    }
    // AJUSTES REFACTOR PILA MAYO 2020
    
	
	
	/**
     * Método para reprocesar planillas por indice
     */
	@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List <PersonaModeloDTO> consultarAportanteReprocesoMundoUno(List<PlanillaGestionManualDTO> planillas) {
		logger.debug("Inicia consultarAportanteReprocesoMundoUno ");
		
		//Se listan los tipos y numeros de identificacion
		if (!planillas.isEmpty()) {
			
			List <PersonaModeloDTO> personasABuscar = new ArrayList<>();
			for(PlanillaGestionManualDTO planilla : planillas) {
				if(planilla.getTipoIdAportante()!=null && planilla.getNumeroIdAportante()!= null) {
					PersonaModeloDTO personaBuscar = new PersonaModeloDTO();
					personaBuscar.setTipoIdentificacion(planilla.getTipoIdAportante());
					personaBuscar.setNumeroIdentificacion(planilla.getNumeroIdAportante());
					personasABuscar.add(personaBuscar);
				}
			}
			
			List<PersonaModeloDTO> listaPersonasPlanilla = new ArrayList<>();
			
			//Se consultan las personas listadas si son existentes
			if(!personasABuscar.isEmpty()) {
				listaPersonasPlanilla = consultasCore.consultarPersonas(personasABuscar);
				
			}
			return listaPersonasPlanilla;
		}

        logger.debug("Finaliza consultarAportanteReprocesoMundoUno");
		return null;
	}

	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#definirBloqueAProcesarPilaMundoDos(java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FasePila2Enum definirBloqueAProcesarPilaMundoDos(Long idRegGeneralAdicionCorreccion, UserDTO userDTO) {
		String firma = "definirBloqueAProcesarPilaMundoDos(Long, UserDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firma + "con idRegGeneralAdicionCorreccion = " + idRegGeneralAdicionCorreccion);

		Map<FasePila2Enum, String> estados = consultarEstadoRegistrosAdicionCorreccionTodasFases(idRegGeneralAdicionCorreccion, userDTO);
		
		if(ConstantesParaMensajes.CONTINUAR.equals(estados.get(FasePila2Enum.PILA2_FASE_3))){
			logger.info(ConstantesComunes.FIN_LOGGER + firma + "con resultado " + FasePila2Enum.PILA2_FASE_3.name());
			return FasePila2Enum.PILA2_FASE_3;
		}
		else if(ConstantesParaMensajes.CONTINUAR.equals(estados.get(FasePila2Enum.PILA2_FASE_2))){
			logger.info(ConstantesComunes.FIN_LOGGER + firma + "con resultado " + FasePila2Enum.PILA2_FASE_2.name());
			return FasePila2Enum.PILA2_FASE_2;
		}
		else if(ConstantesParaMensajes.CONTINUAR.equals(estados.get(FasePila2Enum.PILA2_FASE_1))){
			logger.info(ConstantesComunes.FIN_LOGGER + firma + "con resultado " + FasePila2Enum.PILA2_FASE_1.name());
			return FasePila2Enum.PILA2_FASE_1;
		}
		else{
			logger.info(ConstantesComunes.FIN_LOGGER + firma + "con resultado " + FasePila2Enum.SIN_PARAMETRO.name());
			return FasePila2Enum.SIN_PARAMETRO;
		}
	}
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#consultarIdRegistroGeneralPorNumeroPlanilla(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long consultarIdRegistroGeneralPorNumeroPlanilla(String numeroPlanilla){
		return consultasStaging.consultarIdRegistroGeneralPorPlanilla(numeroPlanilla);
	}
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#validarEstadoEjecucion410Automatico()
	 */
	@Override
	public boolean validarEstadoEjecucion410Automatico(){
		return consultasPila.validarEstadoEjecucion410();
	}
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#aprobarBloqueAportesAdicion(java.util.List, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void aprobarBloqueAportesAdicion(List<RegistrarCorreccionAdicionDTO> criteriosSimulacion,
            UserDTO userDTO){
		for (RegistrarCorreccionAdicionDTO registroCorreccionAdicion : criteriosSimulacion) {
			aprobarAportesAdicion(registroCorreccionAdicion, userDTO);
		}
	}
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#aprobarBloqueNovedadesAdicionCorreccionCompuesto(java.util.List, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void aprobarBloqueNovedadesAdicionCorreccionCompuesto(List<Long> idsRegDetCorA, Long idRegistroGeneralAdicionCorreccion, UserDTO userDTO){
		
		long timeStart = System.nanoTime();
		String firma = "aprobarBloqueNovedadesAdicionCorreccionCompuesto(List<Long>, Long, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

		//logger.info("Listado ids registros detallados corrección A: " + idsRegDetCorA);
		
		List<Callable<Long>> tareasParalelas = new LinkedList<>();
		
		//se realiza un procesamiento en paralelo de cada planilla pendiente por gestionar.
		for (Long idRegDetCorA : idsRegDetCorA) {
			Callable<Long> parallelTask = () -> {
				RegistrarCorreccionAdicionDTO criteriosSimulacion = new RegistrarCorreccionAdicionDTO(null, null, idRegDetCorA, null, null, null);
				ResultadoSimulacionNovedadDTO resultadoSimulacionFase3 = consultarDatosFase3Simulada(criteriosSimulacion);
				criteriosSimulacion.setIdRegistroGeneralCorreccion(idRegistroGeneralAdicionCorreccion);
				criteriosSimulacion.setRegistrosNovedades(resultadoSimulacionFase3.getNovedadesRegistroDetallado());
				criteriosSimulacion.getRegistrosNovedades().forEach(novedad -> novedad.setMarcaAplicarNovedad(true));
				
				return aprobarProcesamientoNovedadesAdicionCorreccion(criteriosSimulacion, userDTO);
	        };
	        tareasParalelas.add(parallelTask);
		}
		
		try {
			managedExecutorService.invokeAll(tareasParalelas);
			
			// se evaluan las marcas del archivo para determinar el cambio de estado del archivo
            verificarEstadoRegistrosAdicionCorreccion(idRegistroGeneralAdicionCorreccion, FasePila2Enum.PILA2_FASE_3, true, userDTO);
			
	    } catch (InterruptedException e) {
	    	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
	        e.printStackTrace();
	    }
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	private ResultadoSimulacionNovedadDTO consultarDatosFase3Simulada(RegistrarCorreccionAdicionDTO criteriosSimulacion) {
        String firmaMetodo = "PilaBusiness.consultarDatosFase3Simulada(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoSimulacionNovedadDTO result = new ResultadoSimulacionNovedadDTO();

        // se consulta el registro detallado C indicado
        RegistroDetalladoModeloDTO registroC = consultasStaging
                .obtenerRegistroDetalladoEspecifico(criteriosSimulacion.getIdRegistroDetalladoCorreccionC());

        // se consultan las novedades presentes en el registro de corrección y se agregan al DTO de salida
        result.setNovedadesRegistroDetallado(consultasStaging.consultarNovedadesRegistroDetallado(registroC.getId()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
	
	
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#registrarNoRegistrarAdicionEnBloque(java.util.List, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void registrarNoRegistrarAdicionEnBloque(List<RegistrarCorreccionAdicionDTO> registrosAdicion, UserDTO userDTO){

		long timeStart = System.nanoTime();
		String firma = "registrarNoRegistrarAdicionEnBloque(List<RegistrarCorreccionAdicionDTO>, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<Callable<Byte>> tareasParalelas = new LinkedList<>();
		
		Long idRegistroGeneralAdicion = (registrosAdicion != null && !registrosAdicion.isEmpty()) ? registrosAdicion.get(0).getIdRegistroGeneralCorreccion() : null; 
		
		for (RegistrarCorreccionAdicionDTO registrarAdicionDTO : registrosAdicion) {
	        
			Callable<Byte> parallelTask = () -> {
				if (registrarAdicionDTO.getRegistrar()) {
		            // Registrar los resultados de esta “Evaluación vs BD”, asociados a este nuevo registro.
		            return actualizarDatosRegistro(null, registrarAdicionDTO.getIdRegistroDetalladoCorreccionA(), EstadoAporteEnum.VIGENTE, true, userDTO.getNombreUsuario());
		        }
		        else {
		            return actualizarDatosRegistro(null, registrarAdicionDTO.getIdRegistroDetalladoCorreccionA(), EstadoAporteEnum.EVALUACION_NO_APLICADA, false, userDTO.getNombreUsuario());
		        }
			};
			tareasParalelas.add(parallelTask);
		}
		try {
			managedExecutorService.invokeAll(tareasParalelas);
			if(idRegistroGeneralAdicion != null){
				verificarEstadoRegistrosAdicionCorreccion(idRegistroGeneralAdicion, FasePila2Enum.PILA2_FASE_1,
		                true, userDTO);
			}
	    } catch (InterruptedException e) {
	    	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
	        e.printStackTrace();
	    }
		
		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#registrarNoRegistrarCorreccionesEnBloque(java.util.List, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void registrarNoRegistrarCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO> registrarCorreccionDTO, UserDTO userDTO){
		
		//long timeStart = System.nanoTime();
		String firma = "registrarNoRegistrarCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO>, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<Callable<Long>> tareasParalelas = new LinkedList<>();

		Long idRegGeneralCorreccion = (registrarCorreccionDTO != null && !registrarCorreccionDTO.isEmpty()) ? registrarCorreccionDTO.get(0).getIdRegistroGeneralCorreccion() : null;

		for (RegistrarCorreccionAdicionDTO registroCorreccionAdicionDTO : registrarCorreccionDTO) {
			Callable<Long> parallelTask = () -> {
				return registrarCorreccion(registroCorreccionAdicionDTO, userDTO);
	        };
	        tareasParalelas.add(parallelTask);
		}
		try {
			managedExecutorService.invokeAll(tareasParalelas);
			
			if(idRegGeneralCorreccion != null){
				verificarEstadoRegistrosAdicionCorreccion(idRegGeneralCorreccion, FasePila2Enum.PILA2_FASE_1, true, userDTO);
			}
			
	    } catch (InterruptedException e) {
	    	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
	        e.printStackTrace();
	    }

		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		//long timeEnd = System.nanoTime();
		//logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
	}
	
	public long registrarCorreccion(RegistrarCorreccionAdicionDTO registrarCorreccionDTO, UserDTO userDTO){
		String firmaMetodo = "PilaBusiness.registrarNoRegistrarCorreccion(RegistrarCorreccionDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        EstadoAporteEnum nuevoEstado = null;

        if (registrarCorreccionDTO.getRegistrar()) {
            // se solicita el registro de la corrección
            nuevoEstado = EstadoAporteEnum.VIGENTE;

            // Registrar los resultados de esta nueva “Evaluación vs BD”, asociados al registro original.
            // Marcar “Evaluación vs BD” igual a “Vigente” para el registro original asociado (si ya tiene esta marca, se mantiene).
            actualizarDatosRegistro(registrarCorreccionDTO.getIdRegistroDetalladoCorreccionC(), registrarCorreccionDTO.getIdRegistroDetalladoOriginal(), nuevoEstado, true, userDTO.getNombreUsuario());

            // Marcar “Evaluación vs BD” del registro A del archivo como “Corregido”.
            actualizarDatosRegistro(null, registrarCorreccionDTO.getIdRegistroDetalladoCorreccionA(), EstadoAporteEnum.CORREGIDO, true, userDTO.getNombreUsuario());
        }
        else {
            nuevoEstado = EstadoAporteEnum.EVALUACION_NO_APLICADA;
            
            actualizarDatosRegistro(null, registrarCorreccionDTO.getIdRegistroDetalladoCorreccionC(), nuevoEstado, false, userDTO.getNombreUsuario());

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return 0L;
	}
	
	
	@Override
    public List<ResultadoAprobacionCorreccionAporteDTO> aprobarCambioAportesCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO> listadoCriteriosSimulacion,
            UserDTO userDTO){
		long timeStart = System.nanoTime();
		String firma = "aprobarCambioAportesCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO>, UserDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		List<ResultadoAprobacionCorreccionAporteDTO> result = new ArrayList<>();
		List<Callable<ResultadoAprobacionCorreccionAporteDTO>> tareasParalelas = new LinkedList<>();
		
		//se realiza un procesamiento en paralelo de cada planilla pendiente por gestionar.
		for (RegistrarCorreccionAdicionDTO criterioSimulacion : listadoCriteriosSimulacion) {
			
			Callable<ResultadoAprobacionCorreccionAporteDTO> parallelTask = () -> {
				return aprobarCambioAportesCorrecciones(criterioSimulacion, userDTO);
	        };
	        tareasParalelas.add(parallelTask);
		}
		
		try {
			List<Future<ResultadoAprobacionCorreccionAporteDTO>> resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
			
			for (Future<ResultadoAprobacionCorreccionAporteDTO> future : resultadosFuturos) {
                //posible correccion condicional
				result.add(future.get());
			}			
			
	    } catch (InterruptedException | ExecutionException e) {
	    	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
	        e.printStackTrace();
	    }

		long timeEnd = System.nanoTime();
		logger.debug(ConstantesComunes.FIN_LOGGER + firma + ConstantesComunes.TIEMPO_EJECUCION + CalendarUtils.calcularTiempoEjecucion(timeStart, timeEnd));
		return result;
    }
	
	/* (non-Javadoc)
	 * @see com.asopagos.pila.service.PilaService#registrarPlanillaCandidataReproceso(java.lang.Long, java.lang.String)
	 */
	@Override
	public void registrarPlanillaCandidataReproceso(Long idRegistroGeneral, String motivoBloqueo){
		
		System.out.println("al servicio registrarPlanillaCandidataReproceso llegó el registroGeneral " + idRegistroGeneral);
		
		consultasStaging.registrarPlanillaCandidataReproceso(idRegistroGeneral, motivoBloqueo);
	}
	
	private Map<FasePila2Enum, String> consultarEstadoRegistrosAdicionCorreccionTodasFases(Long idRegistroGeneral, UserDTO userDTO) {
		String firmaMetodo = "PilaBusiness.consultarEstadoRegistrosAdicionCorreccionTodasFases(Long, FasePila2Enum, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " con los parámetros:: " + 
        		"idRegistroGeneral: " + (idRegistroGeneral != null ? idRegistroGeneral : "null"));
        Map<FasePila2Enum, String> result = new HashMap<>();
        
        // se consulta el registro general
        RegistroGeneralModeloDTO regGen = consultasStaging.consultarRegistroGeneralDTO(idRegistroGeneral);
         
        // sí el registro general aún presenta marca de "en proceso" por parte de los SP, se le quita
        if(regGen != null && regGen.getOutEnProceso()){
            regGen.setOutEnProceso(Boolean.FALSE);
            consultasStaging.actualizaRegistroGeneral(regGen.convertToEntity());
        }

        List<RegistroDetallado> resultList = consultasStaging.consultarInfoBasicaRegistrosDetalladosPorRegistroGeneral(idRegistroGeneral);

        if (resultList != null && !resultList.isEmpty()) {
            // se determina sí todos los registros que presentan marca en una corrección tienen anulación
            Boolean marcarMixta = comprobacionAnulacionesRegistroDetallado(resultList);
            
            result.put(FasePila2Enum.PILA2_FASE_3, resultadoFase(resultList, marcarMixta, FasePila2Enum.PILA2_FASE_3));
            result.put(FasePila2Enum.PILA2_FASE_2, resultadoFase(resultList, marcarMixta, FasePila2Enum.PILA2_FASE_2));
            result.put(FasePila2Enum.PILA2_FASE_1, resultadoFase(resultList, marcarMixta, FasePila2Enum.PILA2_FASE_1));
            
            //se limpia la lista para ahorrar espacio de memoria
            resultList.clear();
            return result;
        }
        else {
            // no se tienen registros detallados asociados al número de planilla, lanza excepción técnica
            String mensaje = MensajesErrorComunesEnum.ERROR_REGISTROS_DETALLADOS_FALTANTE.getReadableMessage(idRegistroGeneral.toString());
            throw new TechnicalException(mensaje);
        }
	}
	
	private String resultadoFase(List<RegistroDetallado> resultList, Boolean marcarMixta, FasePila2Enum fase) {
		// variables de control
	    Boolean marcaRegistrar = null;
	    String mensajeSalida = ConstantesParaMensajes.CONTINUAR;
	    List<EstadoRegistroAportesArchivoEnum> estadosNoAceptables = new ArrayList<>();
	
	    // se definen los estados NO aceptables para cambio de fase
	    switch (fase) {
	        case PILA2_FASE_1:
	            estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD);
	            estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_OK);
	            break;
	        case PILA2_FASE_2:
	            estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.PENDIENTE_POR_REGISTO_RELACION_APORTE);
	            break;
	        default:
	            break;
	    }
    
    	for (RegistroDetallado detalle : resultList) {
            // sólo para registros detallados que no tengan marca de correcciones "A"
            if (!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones())) {
                // se establece el valor de la marca de acción para registro
                switch (fase) {
                    case PILA2_FASE_1:
                        marcaRegistrar = detalle.getOutRegistrado();
                        break;
                    case PILA2_FASE_2:
                        marcaRegistrar = detalle.getOutRegistradoAporte();
                        break;
                    case PILA2_FASE_3:
                        marcaRegistrar = detalle.getOutRegistradoNovedad();
                        break;
                    default:
                        break;
                }

                if (estadosNoAceptables.contains(detalle.getOutEstadoRegistroAporte())) {
                    // caso especial, en la fase 1, no se considera cuando se marca como "NO REGISTRAR"
                    if (!FasePila2Enum.PILA2_FASE_1.equals(fase) || (marcaRegistrar != null && marcaRegistrar)) {
                        mensajeSalida = ConstantesParaMensajes.NO_CONTINUAR_INCONSISTENCIAS_SIN_GESTIONAR;
                    }
                }

                // cuando no se cuenta con marcas de registrar en una fase diferente a la 4
                //if (marcaRegistrar == null && !FasePila2Enum.PILA2_FASE_4.equals(fase)) {
                //    logger.info("ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION"+ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION);
                //    mensajeSalida = !marcarMixta ? ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION : ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION_MIXTA;
                //    break;
                //}
            }
        }
    	return mensajeSalida;
    }

    @Override
	public Map<String, String> consultarEstadoRegistrosAdicionCorreccion(Long idRegistroGeneral, FasePila2Enum fase, UserDTO userDTO) {
        
		long start = System.nanoTime();
		String firmaMetodo = "PilaBusiness.consultarEstadoRegistrosAdicionCorreccion(Long, FasePila2Enum, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " con los parámetros:: " + 
        		"idRegistroGeneral: " + (idRegistroGeneral != null ? idRegistroGeneral : "null") + 
        		",fase: " + (fase != null ? fase.name() : "null"));
        Map<String, String> result = new HashMap<>();
        
        // se consulta el registro general
        RegistroGeneralModeloDTO regGen = consultasStaging.consultarRegistroGeneralDTO(idRegistroGeneral);
         
        // sí el registro general aún presenta marca de "en proceso" por parte de los SP, se le quita
        if(regGen != null && regGen.getOutEnProceso()){
            regGen.setOutEnProceso(Boolean.FALSE);
            consultasStaging.actualizaRegistroGeneral(regGen.convertToEntity());
        }
        List<RegistroDetallado> resultList = consultasStaging.consultarInfoBasicaRegistrosDetalladosPorRegistroGeneral(idRegistroGeneral);

        if (resultList != null && !resultList.isEmpty()) {
            // se determina sí todos los registros que presentan marca en una corrección tienen anulación
            Boolean marcarMixta = comprobacionAnulacionesRegistroDetallado(resultList);
            
            // variables de control
            Boolean marcaRegistrar = null;
            String mensajeSalida = ConstantesParaMensajes.CONTINUAR;
            List<EstadoRegistroAportesArchivoEnum> estadosNoAceptables = new ArrayList<>();

            // se definen los estados NO aceptables para cambio de fase
            switch (fase) {
                case PILA2_FASE_1:
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD);
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.NO_OK);
                    break;
                case PILA2_FASE_2:
                    estadosNoAceptables.add(EstadoRegistroAportesArchivoEnum.PENDIENTE_POR_REGISTO_RELACION_APORTE);
                    break;
                default:
                    break;
            }

            for (RegistroDetallado detalle : resultList) {
                // sólo para registros detallados que no tengan marca de correcciones "A"
                if (!ConstantesComunesPila.CORRECCIONES_A.equals(detalle.getCorrecciones())) {

                    // se establece el valor de la marca de acción para registro
                    switch (fase) {
                        case PILA2_FASE_1:
                            marcaRegistrar = detalle.getOutRegistrado();
                            break;
                        case PILA2_FASE_2:
                            marcaRegistrar = detalle.getOutRegistradoAporte();
                            break;
                        case PILA2_FASE_3:
                            marcaRegistrar = detalle.getOutRegistradoNovedad();
                            break;
                        default:
                            break;
                    }

                    if (estadosNoAceptables.contains(detalle.getOutEstadoRegistroAporte())) {
                        // caso especial, en la fase 1, no se considera cuando se marca como "NO REGISTRAR"
                        if (!FasePila2Enum.PILA2_FASE_1.equals(fase) || (marcaRegistrar != null && marcaRegistrar)) {
                            mensajeSalida = ConstantesParaMensajes.NO_CONTINUAR_INCONSISTENCIAS_SIN_GESTIONAR;
                        }
                    }

                    // cuando no se cuenta con marcas de registrar en una fase diferente a la 4
                    //if (marcaRegistrar == null && !FasePila2Enum.PILA2_FASE_4.equals(fase)) {
                    //    mensajeSalida = !marcarMixta ? ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION : ConstantesParaMensajes.NO_CONTINUAR_REGISTROS_SIN_ACCION_MIXTA;
                    //    break;
                    //}
                }
            }
            
            //se limpia la lista para ahorrar espacio de memoria
            resultList.clear();
            result.put(ConstantesParaMensajes.RESULTADO_VALIDACION, mensajeSalida);
            
            
            long end = System.nanoTime();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " : tardó: " + CalendarUtils.calcularTiempoEjecucion(start, end) + " segundos");
            return result;
        }
        else {
            // no se tienen registros detallados asociados al número de planilla, lanza excepción técnica
            String mensaje = MensajesErrorComunesEnum.ERROR_REGISTROS_DETALLADOS_FALTANTE.getReadableMessage(idRegistroGeneral.toString());
            throw new TechnicalException(mensaje);
        }
    }

	@Override
	public Long ejecutarUSPporFasePila(Long idIndicePlanilla, String usuario, Long idTransaccion, boolean reanudarTransaccion, FasePila2Enum fase){
		return consultasPila.ejecutarUSPporFaseSimulada410(idIndicePlanilla, usuario, idTransaccion, reanudarTransaccion, fase);
	}


    @Override
	public Object[] consultarDatosEmpleadorByRegistroDetallado(Long idRegDetallado){
		return consultasPila.consultarDatosEmpleadorByRegistroDetallado(idRegDetallado);
	}

    @Override
	public Object[] consultarDatosAfiliacionByRegistroDetallado(Long idRegDetallado){
		return consultasPila.consultarDatosAfiliacionByRegistroDetallado(idRegDetallado);
	}
	
    
	/**
     * @see com.asopagos.pila.service.PilaService#consultarListaBlancaAportantes(java.util.List)
     */
	@Override
	public List<ListasBlancasAportantes> consultarListaBlancaAportantes(List<PersonaModeloDTO> aportantes, UserDTO userDTO) {
		List<String> numerosIds = new ArrayList<>();
		List<ListasBlancasAportantes> listaBlanca = new ArrayList<ListasBlancasAportantes>();
		
		for(PersonaModeloDTO aportante : aportantes) {
			if(aportante.getTipoIdentificacion()!=null && aportante.getNumeroIdentificacion()!= null) {
				numerosIds.add(aportante.getNumeroIdentificacion());
				if(numerosIds.size() == 2000) {
					listaBlanca.addAll(consultasCore.consultarListasBlancasAportantes(numerosIds));
					numerosIds = new ArrayList<>();
				}
			}
		}
		
		if(numerosIds.size() > 0) {
			listaBlanca.addAll(consultasCore.consultarListasBlancasAportantes(numerosIds));
			numerosIds = new ArrayList<>();
		}
		
		return listaBlanca;
	}

    // Metodo que realiza una busqueda y en base a la busqueda genera un archivo Excel
    @Override
    public Response exportarArchivosPila(DatosArchvioReporteDTO datosReporte) throws IOException {

        List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();
        if (datosReporte.getDatos() != null) {
            if(datosReporte.getDatos().getTipoBusqueda().equals("ARCHIVOS_PROCESADOS_FINALIZADOS")){
                result = consultasPila.verArchivosProcesadosFinalizados();  
            }else if(datosReporte.getDatos().getTipoBusqueda().equals("ARCHIVOS_OI_PROCESADOS_FINALIZADOS")){
                result = consultasPila.buscarArchivosOIProcesadosFinalizados(datosReporte.getDatos(), null, null);  
            }else if(datosReporte.getDatos().getTipoBusqueda().equals("ARCHIVOS_OI_PROCESADOS_FINALIZADOS_MANUAL")){
                result = null;
            }
        }

        String[] cabeceras = new String[0];
        if (datosReporte.getCabeceras() != null) {
            cabeceras = new String[datosReporte.getCabeceras().size()];
            datosReporte.getCabeceras().toArray(cabeceras);
        }

        List<String[]> datosCuerpoExcel = result.stream().map(ArchivosProcesadosFinalizadosOFDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("planillas", datosCuerpoExcel, datosReporte.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
		inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
		res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();

    }

    // Metodo depreciado que mediante un post genera un archivo Excel con datos que recibe
    /*
    public Response exportarArchivosPilaOLD(DatosArchvioReporteDTO datosReporte) throws IOException {

        List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();
        if (datosReporte.getDatos() != null) {
            result = datosReporte.getDatos();
        }

        String[] cabeceras = new String[0];
        if (datosReporte.getCabeceras() != null) {
            cabeceras = new String[datosReporte.getCabeceras().size()];
            datosReporte.getCabeceras().toArray(cabeceras);
        }


        List<String[]> datosCuerpoExcel = result.stream().map(ArchivosProcesadosFinalizadosOFDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("planillas", datosCuerpoExcel, datosReporte.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
		inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
		res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();

    }
    */

    @Override
    public List<Long> reprocesarPlanillasM1(List<Long> idPlanilla) {
        return consultasPila.reprocesarPlanillasM1(idPlanilla);
    }

    @Override
    public List<Long> reprocesarPlanillasB3() {
        return consultasPila.reprocesarPlanillasB3();
    }
    @Override
    public List<ListasBlancasAportantes> consultarlistasBlancas(){

        return consultasCore.consultarlistasBlancas();
    }

    @Override
    public Boolean agregarEditarlistasBlancas(ListasBlancasAportantes listaBlancaAportante){

        return consultasCore.agregarEditarlistasBlancas(listaBlancaAportante);
    }

    @Override
    public void editarEstadolistasBlancas(ListasBlancasAportantes listaBlancaAportante){

        consultasCore.editarEstadolistasBlancas(listaBlancaAportante);
    }
}
