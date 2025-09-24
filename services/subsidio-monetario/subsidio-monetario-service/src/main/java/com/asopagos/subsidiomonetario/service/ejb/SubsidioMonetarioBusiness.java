package com.asopagos.subsidiomonetario.service.ejb;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

import com.asopagos.subsidiomonetario.clients.GenerarArchivoResultadoPersonasSinDerechoAsync;
import com.asopagos.archivos.util.ComprimidoUtil;
import com.asopagos.archivos.util.TextReaderUtil;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.RolAfiliadoDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionEntidadDescuentoLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoHistoricoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.CampoArchivoBloquedoCMEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.GrupoAplicacionValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoEjecucionProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoInconsistenciaArchivoBloqueoCMEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.subsidiomonetario.clients.GestionarArchivosLiquidacion;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.CargueBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.CondicionesEspecialesLiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaDescuentosSubsidioTrabajadorGrupoDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaValidacionesLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.DatosComunicadoDTO;
import com.asopagos.subsidiomonetario.dto.DetalleCantidadEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.DetalleLiquidacionSubsidioEspecificoFallecimientoDTO;
import com.asopagos.subsidiomonetario.dto.DetalleResultadoPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.ExportarInconsistenciasDTO;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ItemBeneficiarioPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RegistroSinDerechoSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoHistoricoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.TemporalAsignacionDerechoDTO;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.EspecieLiquidacionManualDTO;
import com.asopagos.subsidiomonetario.load.source.ArchivoLiquidacionFilterDTO;
import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.service.SubsidioMonetarioService;
import com.asopagos.subsidiomonetario.util.ArchivosSubsidioUtils;
import com.asopagos.util.CalendarUtils;
import com.asopagos.archivos.clients.AlmacenarArchivo;

import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.enums.FileGeneratedState;
import com.asopagos.subsidiomonetario.dto.CuotaMonetariaIVRDTO;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;
import com.asopagos.subsidiomonetario.constants.ConstantesSubsidioMonetario;
import com.asopagos.subsidiomonetario.composite.clients.GenerarRegistroArchivoLiquidacion;
import com.asopagos.subsidiomonetario.composite.clients.GenerarArchivoLiquidacion;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoNivelBloqueoEnum;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoDocumentoBloqueoBeneficiarioEnum;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Stateless
public class SubsidioMonetarioBusiness implements SubsidioMonetarioService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SubsidioMonetarioBusiness.class);

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasCore;

    /** inject del EJB para consultas en modelo Subsidio */
    @Inject
    private IConsultasModeloSubsidio consultasSubsidio;

    /** Interfaz de generación de archivos mediante Lion Framework */
    @Inject
    private FileGenerator fileGenerator;

    @Resource
    TimerService timerService;

    private boolean add;

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#ejecutarLiquidacionMasiva(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo) {
        String firmaMetodo = "SubsidioBusiness.ejecutarLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = consultasCore.ejecutarLiquidacionMasiva(liquidacion, periodo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que guarda una liquidacion masiva para su posterior ejecución
     * @param liquidacion
     * @author rarboleda
     * @return Boolean Estado de exito o fracaso en la operacion
     */
    @Override
    public RespuestaGenericaDTO persistirLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo,
            UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.guardarLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Si la ejecucion es programada
        if (liquidacion.getTipoEjecucionProceso().equals(TipoEjecucionProcesoLiquidacionEnum.PROGRAMADA)
                && liquidacion.getFechaHoraEjecucionProgramada() != null) {

            // Crear objeto date para configurar el timerService
            Date fechaEjecucionProgramada = new Date(liquidacion.getFechaHoraEjecucionProgramada());
            TimerConfig timerConfig = new TimerConfig();

            // Al reiniciar servidor se mantiene el timer
            timerConfig.setPersistent(true);
            timerService.createSingleActionTimer(fechaEjecucionProgramada, timerConfig);
        }

        RespuestaGenericaDTO result = consultasCore.guardarLiquidacionMasiva(liquidacion, periodo, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que llama a una liquidacion programada
     */
    @Timeout
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void llamarLiquidacionMasivaProgramada() {
        String firmaMetodo = "SubsidioBusiness.guardarLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug("Inicia liquidacion programada a las " + new Date());

        RespuestaGenericaDTO result = consultasCore.consultarLiquidacionesProgramadasAbiertas();

        if ((result.getNumeroRadicado() != null) && (result.getPeriodo() != null)) {
            iniciarLiquidacionMasiva(result.getNumeroRadicado(), result.getPeriodo().getTime());
            
            try {

                GenerarRegistroArchivoLiquidacion servicio1 = new GenerarRegistroArchivoLiquidacion(result.getNumeroRadicado());
                servicio1.execute();
                Long idArchivoLiquidacion = servicio1.getResult();
                //Archivo sin derecho
                GenerarArchivoResultadoPersonasSinDerechoAsync servicio2 = new GenerarArchivoResultadoPersonasSinDerechoAsync(idArchivoLiquidacion, result.getNumeroRadicado());
                servicio2.execute();
                //Archivo con derecho
                GenerarArchivoLiquidacion servicio3 = new GenerarArchivoLiquidacion(idArchivoLiquidacion, result.getNumeroRadicado());
                servicio3.execute();
            } catch (Exception e) {
                logger.error("Fallo en la ejecucion de los procesos de generacion arhcivos de liquidacion", e);
            }
        }

        consultasCore.actualizarEstadoSolicitudLiquidacionXNumRadicado(result.getNumeroRadicado(),
                EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);
        
        //Ejecucion automatica de los archivos sin y con derecho de manera asincrona
        // Persistencia temprana de archivo liquidacion subsidio
        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#cancelarLiquidacionMasiva()
     */
    @Override
    public Boolean cancelarLiquidacionMasiva() {
        String firmaMetodo = "SubsidioBusiness.cancelarLiquidacionMasiva()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = consultasCore.cancelarLiquidacionMasiva();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#consultarResultadosLiquidacionMasiva(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionMasiva(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.consultarResultadoLiquidaciónMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //Date periodoLiquidacion = consultasCore.consultarPeriodoLiquidacionRadicacion(numeroSolicitud);
        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        ResultadoLiquidacionMasivaDTO result = consultasSubsidio.consultarResultadoLiquidacionMasiva(numeroSolicitud, periodoLiquidacion);
        logger.info("Resultado de la consulta consultarResultadoLiquidacionMasiva es: " + result);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#subsidiosLiquidadosPorTrabajadores(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosLiquidadosPorTrabajadores(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.subsidiosLiquidadosPorTrabajadores(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.subsidiosLiquidadosPorTrabajadores(numeroSolicitud,
                periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#montoSubsidiosLiquidadosPorTrabajadores(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO montoSubsidiosLiquidadosPorTrabajadores(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.montoSubsidiosLiquidadosPorTrabajadores(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.montoSubsidiosLiquidadosPorTrabajadores(numeroSolicitud,
                periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#subsidiosInvalidezPorTrabajadores(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosInvalidezPorTrabajadores(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.subsidiosInvalidezPorTrabajadores(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.subsidiosInvalidezPorTrabajadores(numeroSolicitud,
                periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#periodosRetroactivosMes(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO periodosRetroactivosMes(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.periodosRetroactivosMes(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.periodosRetroactivosMes(numeroSolicitud, periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#empresasBeneficio1429(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO empresasBeneficio1429(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.empresasBeneficio1429(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.empresasBeneficio1429(numeroSolicitud, periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidios.service.SubsidioService#trabajadoresDescuentos(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleCantidadEmpresaTrabajadorDTO trabajadoresDescuentos(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.trabajadoresDescuentos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        DetalleCantidadEmpresaTrabajadorDTO result = consultasSubsidio.personasConDescuentos(numeroSolicitud, periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarHistoricoLiquidacionMasiva(java.lang.Long,
     *      java.lang.Long, java.lang.Long, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionMasiva(Long periodoRegular, Long fechaInicio,
            Long fechaFin, String numeroOperacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioBusiness.consultarHistoricoLiquidacionMasiva(Long, Long, Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ResultadoHistoricoLiquidacionMasivaDTO> result = consultasCore.consultarHistoricoLiquidacionMasiva(periodoRegular, fechaInicio,
                fechaFin, numeroOperacion, uri, response);
        if(result != null) {
            for(int i = 0; i<result.size();i++){
                result.get(i).setNumero(Long.parseLong(String.valueOf((i+1))));
            }
        }
        logger.info("El resultado de la consultarHistoricoLiquidacionMasiva es: " + result);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarHistoricoLiquidacionEspecifica(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum,
     *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, javax.ws.rs.core.UriInfo,
     *      javax.servlet.http.HttpServletResponse)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionEspecifica(TipoProcesoLiquidacionEnum tipoLiquidacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, Long periodoRegular, Long fechaInicio, Long fechaFin,
            String numeroOperacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioBusiness.consultarHistoricoLiquidacionEspecifica(Long, Long, Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ResultadoHistoricoLiquidacionMasivaDTO> result = consultasCore.consultarHistoricoLiquidacionEspecifica(tipoLiquidacion,
                tipoLiquidacionEspecifica, periodoRegular, fechaInicio, fechaFin, numeroOperacion, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarResultadoLiquidacionEspecifica(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionEspecifica(String numeroSolicitud) {
        String firmaMetodo = "SubsidioBusiness.consultarResultadoLiquidacionEspecifica(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        logger.debug("String numeroSolicitud: " + numeroSolicitud + " - seguimiento undefined");

        //Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        Date periodoLiquidacion = consultasCore.consultarPeriodoRegularRadicacion(numeroSolicitud);
        ResultadoLiquidacionMasivaDTO result = consultasSubsidio.consultarResultadoLiquidacionEspecifica(numeroSolicitud,
                periodoLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#inicializarPantallaSolicitudLiquidacion()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacion(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.inicializarPantallaSolicitudLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = inicializarPantallaSolicitudLiquidacionConHoraCorteAportes(userDTO);
        if(result.getNumeroRadicado() != null && !result.getNumeroRadicado().isEmpty() 
                && TipoProcesoLiquidacionEnum.MASIVA.equals(result.getTipoProcesoLiquidacion())
                && EstadoProcesoLiquidacionEnum.EN_PROCESO.equals(result.getEstadoProcesoLiquidacion())
                ) {
            try{
            Thread.sleep(2500); //dos segundos y medio
            result.setPorcentajeActual(consultasSubsidio.consultarPorcentajeAvanceProcesoLiquidacion(result.getNumeroRadicado()));
            } catch (Exception e) {
            System.out.println(e);
            result.setPorcentajeActual(0);
         }
        }


        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /*@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerrada(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.inicializarPantallaSolicitudLiquidacionCerrada()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = inicializarPantallaSolicitudLiquidacionCerradaConHoraCorteAportes(userDTO);
        if(result.getNumeroRadicado() != null && !result.getNumeroRadicado().isEmpty() 
                && TipoProcesoLiquidacionEnum.MASIVA.equals(result.getTipoProcesoLiquidacion())
                && EstadoProcesoLiquidacionEnum.EN_PROCESO.equals(result.getEstadoProcesoLiquidacion())
                ) {
            result.setPorcentajeActual(consultasSubsidio.consultarPorcentajeAvanceProcesoLiquidacion(result.getNumeroRadicado()));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }*/

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarSolicitud(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarIdSolicitud(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitud(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.consultarIdSolicitud(numeroRadicado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#aprobarLiquidacionMasivaPrimerNivel(java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long aprobarLiquidacionMasivaPrimerNivel(String numeroSolicitud, UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.aprobarLiquidacionMasivaPrimerNivel(String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.aprobarLiquidacionMasivaPrimerNivel(numeroSolicitud, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#rechazarLiquidacionMasivaPrimerNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long rechazarLiquidacionMasivaPrimerNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.rechazarLiquidacionMasivaPrimerNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        /*Mantis 261498: Se eliminan datos de liquidaciones rechazadas en Específicas.*/
        consultasSubsidio.eliminarLiquidacionSP(numeroSolicitud, Boolean.TRUE);
        	
        consultasSubsidio.activarEnColaProcesoLiquidacion(numeroSolicitud);                    
        
        Long result = consultasCore.rechazarLiquidacionMasivaPrimerNivel(numeroSolicitud, aprobacionRechazoSubsidioMonetarioDTO, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#aprobarLiquidacionMasivaSegundoNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudLiquidacionSubsidioModeloDTO aprobarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.aprobarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        SolicitudLiquidacionSubsidioModeloDTO result = null;
        String tipoLiquidacionAprobar;
        Integer cantBeneficiarioDerechoAsigando = null;
        String MensajeNoAprobado;
        
        tipoLiquidacionAprobar = consultasCore.consultarTipoLiquidacionParalela(numeroSolicitud);
        if (tipoLiquidacionAprobar.equals("RECONOCIMIENTO_DE_SUBSIDIOS")){
            cantBeneficiarioDerechoAsigando = consultasSubsidio.consultarBeneficiarioConDerechoAsignadoEnMismoPeriodo(numeroSolicitud);
        }
        if (cantBeneficiarioDerechoAsigando == null){
            result = consultasCore.aprobarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                    aprobacionRechazoSubsidioMonetarioDTO, userDTO);
    
            consultasSubsidio.activarEnColaProcesoLiquidacion(numeroSolicitud);
        } else if (cantBeneficiarioDerechoAsigando.equals(0)) {
            result = consultasCore.aprobarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                    aprobacionRechazoSubsidioMonetarioDTO, userDTO);
    
            consultasSubsidio.activarEnColaProcesoLiquidacion(numeroSolicitud);
        } else {
            MensajeNoAprobado = "Beneficiario ya liquidado para este trabajador por el período";
            consultasSubsidio.actualizarResultadoValidacionLiquidacionDerechoNoAsignado(numeroSolicitud, MensajeNoAprobado);
            result = null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#rechazarLiquidacionMasivaSegundoNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudLiquidacionSubsidioModeloDTO rechazarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, Boolean isAsync, UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.rechazarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        if(!isAsync && consultasSubsidio.consultarEjecucionProcesoSubsidio(numeroSolicitud)){
        	SolicitudLiquidacionSubsidioModeloDTO resultado = new SolicitudLiquidacionSubsidioModeloDTO();
        	resultado.setLiquidacionORechazOStagingEnProceso(Boolean.TRUE);
        	return resultado;
        }
        

        SolicitudLiquidacionSubsidioModeloDTO result = consultasCore.rechazarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                aprobacionRechazoSubsidioMonetarioDTO, userDTO);

        if (result.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION)) {
            consultasSubsidio.eliminarLiquidacionSP(numeroSolicitud, Boolean.FALSE);
        }
        else {
            consultasSubsidio.activarEnColaProcesoLiquidacion(numeroSolicitud);
        }
        if (result.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.MASIVA)) {
        	consultasSubsidio.eliminarLiquidacionSP(numeroSolicitud, isAsync);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarInstanciaSolicitudGlobal(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public void actualizarInstanciaSolicitudGlobal(Long idInstancia, Long idSolicitudGlobal) {
        String firmaMetodo = "SubsidioBusiness.actualizarInstanciaSolicitudGlobal(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.actualizarInstanciaSolicitudGlobal(idInstancia, idSolicitudGlobal);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarEstadoSolicitudLiquidacion(java.lang.Long,
     *      com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum)
     */
    @Override
    public void actualizarEstadoSolicitudLiquidacion(Long idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum estado) {
        String firmaMetodo = "SubsidioBusiness.actualizarEstadoSolicitudLiquidacion(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, estado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarSolicitudLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public SolicitudLiquidacionSubsidioModeloDTO consultarSolicitudLiquidacion(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO result = consultasCore.consultarSolicitudLiquidacion(numeroRadicado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarEstadoSolicitudLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public EstadoProcesoLiquidacionEnum consultarEstadoSolicitudLiquidacion(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        EstadoProcesoLiquidacionEnum result = consultasCore.consultarSolicitudLiquidacion(numeroRadicado).getEstadoLiquidacion();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarEstadoSolicitudLiquidacionEnProceso()
     */
    @Override
    public void actualizarEstadoSolicitudLiquidacionEnProceso(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.actualizarEstadoSolicitudLiquidacionEnProceso(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudLiquidacion = consultasCore.consultarSolicitudLiquidacion(numeroRadicado).getIdProcesoLiquidacionSubsidio();
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.EN_APROBACION);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RespuestaGenericaDTO seleccionarPersona(Long personaId) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = consultasCore.seleccionarPersona(personaId);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#seleccionarEmpleador(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RespuestaGenericaDTO seleccionarEmpleador(Long empleadorId) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = consultasCore.seleccionarEmpleador(empleadorId);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarBeneficiariosAfiliado(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliado(Long idPersona) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> result = consultasCore.consultarBeneficiariosAfiliado(idPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#iniciarLiquidacionMasiva(java.lang.String, java.util.Date)
     */
    @Asynchronous
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void iniciarLiquidacionMasiva(String numeroRadicado, Long periodo) {
        String firmaMetodo = "SubsidioMonetarioBusiness.iniciarLiquidacionMasiva(String, Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (numeroRadicado != null && periodo != null) {
            GenerarRegistroArchivoLiquidacion servicio1 = new GenerarRegistroArchivoLiquidacion(numeroRadicado);
            servicio1.execute();
            Long idArchivoLiquidacion = servicio1.getResult();
            consultasSubsidio.iniciarLiquidacionMasiva(numeroRadicado, periodo);
            
            Long idSolicitudLiquidacion = consultarIdSolicitud(numeroRadicado);
            if(Boolean.FALSE.equals(consultasSubsidio.consultarCancelacionProcesoLiquidacion(numeroRadicado))) {
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);
            }

            //Ejecucion automatica de los archivos sin y con derecho de manera asincrona
            // Persistencia temprana de archivo liquidacion subsidio
            try {
                //Archivo sin derecho
                GenerarArchivoResultadoPersonasSinDerechoAsync servicio2 = new GenerarArchivoResultadoPersonasSinDerechoAsync(idArchivoLiquidacion, numeroRadicado);
                servicio2.execute();
                //Archivo con derecho
                GenerarArchivoLiquidacion servicio3 = new GenerarArchivoLiquidacion(idArchivoLiquidacion, numeroRadicado);
                servicio3.execute();
            } catch (Exception e) {
                logger.error("Fallo en la ejecucion de los procesos de generacion arhcivos de liquidacion", e);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarPeriodo()
     */
    @Override
    public void generarNuevoPeriodo() {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarNuevoPeriodo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.generarNuevoPeriodo();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarPeriodo()
     */
    @Override
    public void generarNuevoPeriodoL(Long periodoL) { 
        String firmaMetodo = "SubsidioMonetarioBusiness.generarNuevoPeriodoL(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.generarNuevoPeriodo(periodoL);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarOrquestadorStagin()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarOrquestadorStagin(Long fechaActual) {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarNuevoPeriodo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarOrquestadorStagin(fechaActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

        /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarOrquestadorStaginFallecimiento()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarOrquestadorStaginFallecimiento(Long fechaActual, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarOrquestadorStaginFallecimiento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarOrquestadorStaginFallecimiento(fechaActual, tipoIdentificacion, numeroIdentificacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarOrquestadorStaginIntervaloFechas(java.lang.Long,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarOrquestadorStaginIntervaloFechas(Long fechaInicio, Long fechaFin) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarOrquestadorStaginIntervaloFechas(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarOrquestadorStaginIntervaloFechas(fechaInicio, fechaFin);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#eliminarLiquidacionSP(java.lang.String)
     */
    @Override
    public Boolean eliminarLiquidacionSP(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.eliminarLiquidacionSP(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean resultado = consultasSubsidio.eliminarLiquidacionSP(numeroRadicado, Boolean.FALSE);        
        		
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;        
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarEstadoSolicitudLiquidacionXNumRadicado(java.lang.String)
     */
    @Override
    public void actualizarEstadoSolicitudLiquidacionXNumRadicado(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.actualizarEstadoSolicitudLiquidacionXNumRadicado(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.actualizarEstadoSolicitudLiquidacionXNumRadicado(numeroRadicado, EstadoProcesoLiquidacionEnum.CERRADA);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#generarArchivoResultadoLiquidacion(java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionArchivoDTO generarArchivoResultadoLiquidacion(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarArchivoResultadoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, String> descuentosBeneficiarios = obtenerDescuentosBeneficiarios(numeroRadicacion,
                tipoIdentificacion, numeroIdentificacion);

        InformacionArchivoDTO archivoLiquidacion = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_RESULTADOS_LIQUIDACION).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.DELIMITED_TEXT_PLAIN };
        try {
            ArchivoLiquidacionFilterDTO filtro = new ArchivoLiquidacionFilterDTO();
            filtro.setNumeroRadicacion(numeroRadicacion);
            filtro.setDescuentosBeneficiarios(descuentosBeneficiarios);
            filtro.setTipoIdentificacion(tipoIdentificacion);
            filtro.setNumeroIdentificacion(numeroIdentificacion);

            FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);
            if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                outDTO.setDelimitedTxtFilename("LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".csv");
                archivoLiquidacion = convertirOutDTOXlsxType(outDTO);
            }

        } catch (AsopagosException e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw e;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacion;
    }

    /**
     * Método que se encarga de obtener los descuentos descuentos agrupados por beneficiarios
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param tipoIdentificacion 
     *          el tipo de identificación del empleador
     * @param numeroIdentificacion
     *          el número de identificación del empleador        
     * @return descuentos agrupados
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Map<String, String> obtenerDescuentosBeneficiarios(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerDescuentosBeneficiarios(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Map<String, String> descuentosBeneficiarios = null;
        if (tipoIdentificacion != null && numeroIdentificacion != null) {
            descuentosBeneficiarios = consultasSubsidio.obtenerDescuentosBeneficiarios(numeroRadicacion,
                    tipoIdentificacion, numeroIdentificacion);
        } else if (tipoIdentificacion != null && numeroIdentificacion != null) {
            descuentosBeneficiarios = consultasSubsidio.obtenerDescuentosBeneficiarios(numeroRadicacion, null, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return descuentosBeneficiarios;
    }


    private InformacionArchivoDTO generarArchivoSinDerecho(String numeroRadicacion, String nombreArchivo) {

        InformacionArchivoDTO archivoSinDerecho = new InformacionArchivoDTO();

        archivoSinDerecho.setFileType(MediaType.TEXT_PLAIN);
        archivoSinDerecho.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());
        

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaActualStr = fechaActual.format(formatter);

        // Se genera el archivo sin derecho
        String tipoIdentificacion = "null";
        String numeroIdentificacion = "null";
        Integer rows = 100000;
        Integer page = 0;
        List<RegistroSinDerechoSubsidioDTO> resultadosSinDerecho = new ArrayList<>();
        logger.info("Ingreso a iteracion del while de generarData");
        BufferedWriter bw = null;
        File comprimidoSinDerecho = null;
        InformacionArchivoDTO archivoGuardado;

        try{
            File archivo = File.createTempFile(nombreArchivo, ".csv");
            archivo.deleteOnExit();

            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.append(ConstantesSubsidioMonetario.CABECERA_SIN_DERECHO);
            do {
                resultadosSinDerecho = consultasSubsidio.generarDataLiquidaciomSinDerecho(
                    numeroRadicacion, page, rows, null, null);

                    
                for (RegistroSinDerechoSubsidioDTO registro : resultadosSinDerecho) {
                    bw.append(registro.toStringDerecho(fechaActualStr, "|"));
                }
                if (resultadosSinDerecho.size() == 0) {
                    bw.flush();
                    break;
                }
                
                page += rows;
                bw.flush();
            } while (resultadosSinDerecho.size() == rows);

            Map<String, File> mapSinDerecho = new HashMap<>();
            mapSinDerecho.put(nombreArchivo + ".csv", archivo);

            comprimidoSinDerecho = ComprimidoUtil.comprimirZip(6, mapSinDerecho, nombreArchivo + ".zip");
            
            archivoSinDerecho.setDataFile(Files.readAllBytes(comprimidoSinDerecho.toPath()));
            archivoSinDerecho.setFileName(comprimidoSinDerecho.getName());
            archivoSinDerecho.setDocName(comprimidoSinDerecho.getName());
            AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(archivoSinDerecho);
            almacenarArchivo.execute();
            archivoGuardado = almacenarArchivo.getResult();

            bw.close();
                
        } catch (IOException e) {
            logger.error("Error al escribir el archivo", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        } catch (Exception e) {
            logger.error("Error al escribir el archivo", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        } finally {
            logger.info("Finaliza el while de generarData");
        }
        return archivoGuardado;
    }

    /**
     * Método que se encarga de convertir un FileGeneratorOutDTO a un InformacionArchivoDTO
     * @param outDTO
     *        información del archivo generado por lion
     * @return DTO con la información del archivo para enviar al ECM
     */
    private InformacionArchivoDTO convertirOutDTOXlsxType(FileGeneratorOutDTO outDTO) {
        String firmaMetodo = "SubsidioMonetarioBusiness.convertirOutDTO(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();

        informacionArchivoDTO.setDataFile(outDTO.getDelimitedTxt());
        informacionArchivoDTO.setFileName(outDTO.getDelimitedTxtFilename());
        informacionArchivoDTO.setDocName(outDTO.getDelimitedTxtFilename());
        informacionArchivoDTO.setFileType(MediaType.TEXT_PLAIN);
        informacionArchivoDTO.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return informacionArchivoDTO;
    }
    
    /**
     * Método que se encarga de convertir un FileGeneratorOutDTO a un InformacionArchivoDTO
     * @param outDTO
     *        información del archivo generado por lion
     * @return DTO con la información del archivo para enviar al ECM
     */
    private InformacionArchivoDTO convertirOutDTOXlsxTypeZIP(FileGeneratorOutDTO outDTO,String nombreArchivozip) {
        String firmaMetodo = "SubsidioMonetarioBusiness.convertirOutDTOXlsxTypeZIP(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        
        
        File comprimido = null;
        byte[] contenidoArchivoComp = null;

        HashMap<String, byte[]> zip = new HashMap<>();
        zip.put(outDTO.getDelimitedTxtFilename(), outDTO.getDelimitedTxt());


        try {
            comprimido = ComprimidoUtil.comprimirZip(1, zip);
            contenidoArchivoComp = Files.readAllBytes(comprimido.toPath());
        } catch (Exception e) {
            logger.error("Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)", e);
        }
        

        InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();

        informacionArchivoDTO.setDataFile(contenidoArchivoComp);
        informacionArchivoDTO.setFileName(nombreArchivozip);
        informacionArchivoDTO.setDocName(nombreArchivozip);
        informacionArchivoDTO.setFileType(MediaType.TEXT_PLAIN);
        informacionArchivoDTO.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return informacionArchivoDTO;
    }
    
    /**
     * Construye el archivo zip a partir de los pares string-bite[] contenidos en el mapa
     * @param compression
     * @param data
     * @return
     * @throws Exception
     */
    private static File comprimirZip(int compression, HashMap<String, byte[]> data) throws Exception {
       
        File tempZipFile = File.createTempFile("test-data" + compression, ".zip");
        tempZipFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempZipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.setLevel(compression);
            Iterator it = data.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String text = (String) pair.getKey();
                ZipEntry entry = new ZipEntry(text);
                zos.putNextEntry(entry);
                try {
                    zos.write((byte[]) pair.getValue());
                } finally {
                    zos.closeEntry();
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
       
        return tempZipFile;
    }

    /*
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, String> iniciarProcesoGeneracionArchivoSinDerecho(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.iniciarProcesoGeneracionArchivoSinDerecho(String, TipoIdentificacionEnum, String)";
        Long maxRegistrosSinconos = 1000000L;
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Map<String, String> resultado = new HashMap<>();
        //Se consulta si ya existe el archivo ecm
        ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacion = consultarArchivosLiquidacion(numeroRadicacion);
        if (archivoLiquidacion != null && archivoLiquidacion.getIdentificadorECMPersonasSinDerecho() != null) {
            resultado.put("archivoGenerado", archivoLiquidacion.getIdentificadorECMPersonasSinDerecho());
            return resultado;
        }

        // Inicia las insersiones a la tabla temporal de sin derecho
        //Retorna el numero de registros asociados a esta liquidacion
        consultasSubsidio.iniciarProcesoGeneracionArchivoSinDerecho(numeroRadicacion);
        
        Long numeroRegistros = consultasSubsidio.consultarNumeroRegistrosSinDerecho(numeroRadicacion);
        
        if (numeroRegistros > maxRegistrosSinconos) {
            //Inicia la generacion del archivo
            GenerarArchivoResultadoPersonasSinDerechoAsync task = new GenerarArchivoResultadoPersonasSinDerechoAsync(numeroRadicacion);
            task.execute();
            resultado.put("archivoGenerado", "false");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String nombreArchivo = "SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date());
        
            InformacionArchivoDTO archivoGuardado = generarArchivoSinDerecho(numeroRadicacion, nombreArchivo);

            // Se setea el ecm en ArchivoLiquidacionSubsidio
            consultasCore.actualizarEcmArchivoLiquidacionSubsidio(numeroRadicacion, archivoGuardado.getFileName());
            consultasSubsidio.limpiarGeneracionArchivoSinDerecho(numeroRadicacion);
            resultado.put("archivoGenerado", archivoGuardado.getFileName());
        }
        return resultado;
    }
     */

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    public void generarArchivoResultadoPersonasSinDerechoAsync(Long idArchivoLiquidacion, String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarArchivoResultadoPersonasSinDerechoAsync(String, TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String enProceso = "EN_PROCESO";
        // Se valida si el archivo ya fue generado o esta EN_PROCESO
        ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultasCore.consultarArchivosLiquidacionPorId(idArchivoLiquidacion);
        if (archivosLiquidacionDTO == null || enProceso.equals(archivosLiquidacionDTO.getIdentificadorECMPersonasSinDerecho())) {
            return;
        }

        try {
            // Se actualiza/crea registro de liquidacion de archivo EN_PROCESO
            // archivosLiquidacionDTO.setIdentificadorECMPersonasSinDerecho(enProceso);
            consultasCore.actualizarEcmArchivoLiquidacionSubsidio(numeroRadicacion, enProceso);

            // // Se usa el servicio de forma que se actualize antes de finalizar el metodo
            // GestionarArchivosLiquidacion servicio = new GestionarArchivosLiquidacion(archivosLiquidacionDTO);
            // servicio.execute();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String nombreArchivo = "SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date());


            InformacionArchivoDTO archivoGuardado = generarArchivoSinDerecho(numeroRadicacion, nombreArchivo);

            // Se setea el ecm en ArchivoLiquidacionSubsidio
            consultasCore.actualizarEcmArchivoLiquidacionSubsidio(numeroRadicacion, archivoGuardado.getFileName());

            // archivosLiquidacionDTO.setIdentificadorECMPersonasSinDerecho(archivoGuardado.getFileName());
            // gestionarArchivosLiquidacion(archivosLiquidacionDTO);
            consultasSubsidio.limpiarGeneracionArchivoSinDerecho(numeroRadicacion);
        } catch (Exception e) {
            logger.info("Sucedio un error al almacenar el archivo de liquidacion sin derecho");
            e.printStackTrace();
            logger.info("Entra a actualizar el archivo generarArchivoResultadoPersonasSinDerechoAsync");
            archivosLiquidacionDTO = consultasCore.consultarArchivosLiquidacionPorId(idArchivoLiquidacion);
            archivosLiquidacionDTO.setIdentificadorECMPersonasSinDerecho(null);
            GestionarArchivosLiquidacion servicio2 = new GestionarArchivosLiquidacion(archivosLiquidacionDTO);
            servicio2.execute();
            logger.info("Finaliza actualizar el archivo generarArchivoResultadoPersonasSinDerechoAsync");
            consultasSubsidio.limpiarGeneracionArchivoSinDerecho(numeroRadicacion);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionArchivoDTO generarArchivoResultadoPersonasSinDerecho(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarArchivoResultadoPersonasSinDerecho(String, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<RegistroSinDerechoSubsidioDTO> registroSinDerechoSubsidioo = new ArrayList<>();
        InformacionArchivoDTO archivoSinDerecho = new InformacionArchivoDTO();
        String resultado;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_PERSONAS_SIN_DERECHO).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.DELIMITED_TEXT_PLAIN };
        try {
            ArchivoLiquidacionFilterDTO filtro = new ArchivoLiquidacionFilterDTO();
            filtro.setNumeroRadicacion(numeroRadicacion);
            filtro.setTipoIdentificacion(tipoIdentificacion);
            filtro.setNumeroIdentificacion(numeroIdentificacion);

            /*   FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);*/
            logger.debug("Acabo de generar el fileGenerator.generate en el metodo generarArchivoResultadoPersonasSinDerecho");
            logger.info("Esta recibiendo los siguientes datos de filtro:  " + filtro);
           /*  if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                logger.info("Ingreso a la validacion del estado de outDTO: " + outDTO.getState());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                outDTO.setDelimitedTxtFilename("SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".csv");
                String nombreArchivozip = "SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".zip";
                logger.debug("Entra a la validacion cuando el getState es igual a succesful");
                archivoSinDerecho = convertirOutDTOXlsxTypeZIP(outDTO,nombreArchivozip);
            }*/
            logger.info("Envio para proceso de registros en List<RegistroSinDerechoSubsidioDTO>");
            registroSinDerechoSubsidioo = consultasSubsidio.generarDataLiquidaciomSinDerecho(numeroRadicacion, 0,100000, tipoIdentificacion,  numeroIdentificacion);

            logger.info("Ingresa para realizar el agregado del separador |");

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaActualStr = fechaActual.format(formatter);
            StringBuilder sb = new StringBuilder();
            //Subsidio monetario Cabecera del Registro sin derecho, GLPI 62038
            sb.append("Fecha de liquidación| Tipo de liquidación| Subtipo de liquidación| Tipo de identificación del Empleador|" +
                    "Número de identificación del Empleador| Nombre del Empleador| CIIU | Condición Agraria| Código sucursal | Año del beneficio 1429 |" +
                    " Tipo de identificación del Trabajador | Número de identificación del Trabajador | Nombre del Trabajador | Tipo de identificación del beneficiario|" +
                    "Número de identificación del beneficiario | Nombre del beneficiario | Tipo de solicitante | Clasificación | Causal de no cumplimiento | Periodo liquidado|" +
                    "Tipo de periodo liquidado\n");

            for (RegistroSinDerechoSubsidioDTO persona : registroSinDerechoSubsidioo) {
                sb.append(fechaActualStr)
                        .append("|")
                        .append(persona.getTipoLiquidacion())
                        .append("|")
                        .append(persona.getSubtipoLiquidacion())
                        .append("|")
                        .append(persona.getTipoIdentificacionEmpleador())
                        .append("|")
                        .append(persona.getNumeroIdentificacionEmpleador())
                        .append("|")
                        .append(persona.getNombreEmpleador())
                        .append("|")
                        .append(persona.getCiiu())
                        .append("|")
                        .append(persona.getCondicionAgraria())
                        .append("|")
                        .append(persona.getCodigoSucursal())
                        .append("|")
                        .append(persona.getAnioBeneficio1429())
                        .append("|")
                        .append(persona.getTipoIdentificacionTrabajador())
                        .append("|")
                        .append(persona.getNumeroIdentificacionTrabajador())
                        .append("|")
                        .append(persona.getNombreTrabajador())
                        .append("|")
                        .append(persona.getTipoIdentificacionBeneficiario())
                        .append("|")
                        .append(persona.getNumeroIdentificacionBeneficiario())
                        .append("|")
                        .append(persona.getNombreBeneficiario())
                        .append("|")
                        .append(persona.getTipoSolicitante())
                        .append("|")
                        .append(persona.getClasificacion())
                        .append("|")
                        .append(persona.getRazonesSinDerecho())
                        .append("|")
                        .append(persona.getPeriodoLiquidado())
                        .append("|")
                        .append(persona.getTipoPeriodo())
                        .append("\n"); // Agrega un salto de línea después de cada registro
            }

            resultado = sb.toString();
            byte[] csvBytes = resultado.getBytes();

            //Imprime la cadena resultante
            logger.info("Se esta obteniaendo la informacion de la iteraccion de la siguiente manera .");

            //consultasSubsidio.guardarListaComoCSV(resultado, numeroRadicacion);
            logger.info("Se esta obteniaendo la informacion de Bytes: ");
            logger.info("Se a guardado archivo de liquidacion.");

            //Proceso de compresion archivo nuevo sin derecho
            HashMap<String, byte[]> mapArchivoSinDerecho = new HashMap<String, byte[]>();
            mapArchivoSinDerecho.put("SD_LIQUIDACION_" + numeroRadicacion +"_"+ fechaActualStr +  ".csv", csvBytes);
            File comprimido = null;
            byte[] contenidoArchivoComp = null;

            try {
                comprimido = ComprimidoUtil.comprimirZip(1, mapArchivoSinDerecho);
                contenidoArchivoComp = Files.readAllBytes(comprimido.toPath());
            } catch (Exception e) {
                logger.error("Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)", e);
            }


            String nombreArchivozip = "SD_LIQUIDACION_" + numeroRadicacion +"_"+ fechaActualStr +  ".zip";
            archivoSinDerecho.setDataFile(contenidoArchivoComp);
            archivoSinDerecho.setFileName(nombreArchivozip);
            archivoSinDerecho.setDocName(nombreArchivozip);
            archivoSinDerecho.setFileType(MediaType.TEXT_PLAIN);
            archivoSinDerecho.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());

        } catch (AsopagosException e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            logger.info("Salio error y uingreso al catch de metodo  generarArchivoResultadoPersonasSinDerecho.");
            throw e;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            logger.info("Salio error y uingreso al catch de metodo  generarArchivoResultadoPersonasSinDerecho de error tecnico.");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.debug("Retorno del metodo generarArchivoResultadoPersonasSinDerecho es: ");
        return archivoSinDerecho;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#generarArchivoResultadoPersonasSinDerecho(java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionArchivoDTO generarArchivoResultadoPersonasSinDerecho(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.generarArchivoResultadoPersonasSinDerecho(String, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO archivoSinDerecho = new InformacionArchivoDTO();
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_PERSONAS_SIN_DERECHO).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.DELIMITED_TEXT_PLAIN };
        try {
            ArchivoLiquidacionFilterDTO filtro = new ArchivoLiquidacionFilterDTO();
            filtro.setNumeroRadicacion(numeroRadicacion);
            filtro.setTipoIdentificacion(tipoIdentificacion);
            filtro.setNumeroIdentificacion(numeroIdentificacion);

            FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);
            
            if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                outDTO.setDelimitedTxtFilename("SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".csv");
                String nombreArchivozip = "SD_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".zip";
                archivoSinDerecho = convertirOutDTOXlsxTypeZIP(outDTO,nombreArchivozip);
            }

        } catch (FileGeneratorException e) {
            logger.error(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            logger.error("Error al generar el archivo de personas sin derecho: " + e.getMessage());
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        } catch (Exception e) {
            logger.error(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            logger.error("Error al generar el archivo de personas sin derecho: " + e.getMessage());
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoSinDerecho;
    }
    */

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#temporalDerechoBeneficiarios(java.lang.String)
     */
    @Override
    public List<TemporalAsignacionDerechoDTO> temporalDerechoBeneficiarios(String numeroRadicacion) {
        // TODO Eliminar este servicio
        return consultasSubsidio.temporalDerechoBeneficiarios(numeroRadicacion);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarArchivosLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarArchivosLiquidacion(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidioModeloDTO result = consultasCore.consultarArchivosLiquidacion(numeroRadicacion);
        logger.debug("Este es el resultado del llamado a consultas Core desde subsidio monetario bussiness: " + result);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarArchivosLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionPorId(Long idArchivoLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarArchivosLiquidacionPorId(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidioModeloDTO result = consultasCore.consultarArchivosLiquidacionPorId(idArchivoLiquidacion);
        logger.debug("Este es el resultado del llamado a consultas Core desde subsidio monetario bussiness: " + result);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#gestionarArchivosLiquidacion(com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO)
     */
    @Override
    public Long gestionarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarArchivosLiquidacion(FileGeneratorOutDTO) - 2";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long identificadorArchivoLiquidacion = null;
        if (archivoLiquidacionDTO != null) {
            if (archivoLiquidacionDTO.getIdArchivoLiquidacionSubsidio() != null) {
                //Se procede a realizar la actualización de la información
                identificadorArchivoLiquidacion = consultasCore.actualizarArchivosLiquidacion(archivoLiquidacionDTO);
            }
            else {
                //Se procede a realizar el registro de la información
                identificadorArchivoLiquidacion = consultasCore.registrarArchivosLiquidacion(archivoLiquidacionDTO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return identificadorArchivoLiquidacion;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarLiquidacionEspecifica(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarLiquidacionEspecifica(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = consultasCore.ejecutarLiquidacionEspecifica(liquidacionEspecifica);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValorCuotaPeriodo(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal consultarValorCuotaPeriodo(Long periodo) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValorCuotaPeriodo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal valorCuota = consultasCore.consultarValorCuotaPeriodo(periodo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorCuota;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValorCuotaPeriodos(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ValorPeriodoDTO> consultarValorCuotaPeriodos(List<Long> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarLiquidacionEspecifica(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();

        for (Long periodo : periodos) {
            valoresPeriodos.add(new ValorPeriodoDTO(periodo, consultasCore.consultarValorCuotaPeriodo(periodo)));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valoresPeriodos;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarPeriodoRegularLiquidacionPorRadicado(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarPeriodoRegularLiquidacionPorRadicado(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarPeriodoRegularLiquidacionPorRadicado(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoRegular = consultasCore.consultarPeriodoRegularRadicacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoRegular;
    }

    /**
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean verificarLiquidacionEnProceso() {
        String firmaMetodo = "SubsidioMonetarioBusiness.verificarLiquidacionEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean hayLiquidacionEnProceso = consultasCore.verificarLiquidacionEnProceso();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return hayLiquidacionEnProceso;
    }

    /**
     * @param liquidacionEspecifica
     * @return
     */
    @Override
    public RespuestaGenericaDTO guardarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarLiquidacionEspecifica(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = consultasCore.guardarLiquidacionEspecifica(liquidacionEspecifica, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * @param liquidacionEspecifica
     * @return
     */
    @Override
    public List<RolAfiliadoDTO> consultarEmpleadorPorPersonaTrabajador(Long idPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarEmpleadorPorPersonaTrabajador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RolAfiliadoDTO> result = consultasCore.consultareEmpleadorPorPersonaTrabajador(idPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValorCuotaAnualYAgrariaPeriodos(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ValorPeriodoDTO> consultarValorCuotaAnualYAgrariaPeriodos(List<Long> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValorCuotaAnualYAgrariaPeriodos(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();

        for (Long periodo : periodos) {
            valoresPeriodos.add(consultasCore.consultarValorCuotaAnualYAgrariaPeriodos(periodo));

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valoresPeriodos;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#enviarResultadoLiquidacionAPagos(java.lang.String,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void enviarResultadoLiquidacionAPagos(String nombreUsuario, String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.enviarResultadoLiquidacionAPagos(String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        consultasSubsidio.enviarResultadoLiquidacionAPagos(nombreUsuario, numeroRadicado);
        logger.info("termina enviarResultadoLiquidacionAPagos");

        CompletableFuture.runAsync(() -> {
            consultasSubsidio.insercionCondicionesDbo(numeroRadicado);
        }, executor);
        logger.info("termina insercionCondicionesDbo");

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#verificarExistenciaPeriodo(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean verificarExistenciaPeriodo(List<ValorPeriodoDTO> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValorCuotaAnualYAgrariaPeriodos(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean periodoExiste = consultasCore.verificarExistenciaPeriodo(periodos);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoExiste;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarPeriodosLiquidacion(java.util.List, java.lang.Long)
     */
    @Override
    public Boolean guardarPeriodosLiquidacion(List<ValorPeriodoDTO> periodos, Long idSolicitudLiquidacion,
            TipoLiquidacionEspecificaEnum tipoAjuste) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValorCuotaAnualYAgrariaPeriodos(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean isPeriodosGuardados = consultasCore.guardarPeriodosLiquidacion(periodos, idSolicitudLiquidacion, tipoAjuste);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return isPeriodosGuardados;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarFactorCuotaDiscapacidadPeriodos(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ValorPeriodoDTO> consultarFactorCuotaDiscapacidadPeriodos(List<Long> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarFactorCuotaDiscapacidadPeriodos(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();

        for (Long periodo : periodos) {
            ValorPeriodoDTO valorCuotaAnualYAgrariaPeriodos = consultasCore.consultarValorCuotaAnualYAgrariaPeriodos(periodo);
            //valorCuotaAnualYAgrariaPeriodos.setValorPeriodo(consultasCore.consultarFactorCuotaDiscapacidadPeriodos(periodo)); 
            valorCuotaAnualYAgrariaPeriodos.setValorDiscapacidad(consultasCore.consultarFactorCuotaDiscapacidadPeriodos(periodo));
            valoresPeriodos.add(valorCuotaAnualYAgrariaPeriodos);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valoresPeriodos;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValorCuotaAgrariaPeriodos(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ValorPeriodoDTO> consultarValorCuotaAgrariaPeriodos(List<Long> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarFactorCuotaDiscapacidadPeriodos(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();

        for (Long periodo : periodos) {
            valoresPeriodos.add(new ValorPeriodoDTO(periodo, consultasCore.consultarValorCuotaAgrariaPeriodo(periodo)));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valoresPeriodos;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarPersonasLiquidacionEspecifica(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    public Boolean guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        //Seguimiento undefined
        logger.debug("Long idSolicitudLiquidacion: " + idSolicitudLiquidacion + " - seguimiento undefined");
        logger.debug("LiquidacionEspecificaDTO liquidacionEspecifica: " + liquidacionEspecifica + " - seguimiento undefined");

        Boolean personasGuardadas = consultasCore.guardarPersonasLiquidacionEspecifica(liquidacionEspecifica, idSolicitudLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return personasGuardadas;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarSPLiquidacionEspecifica(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarSPLiquidacionEspecifica(tipoLiquidacionEspecifica, numeroRadicado);
        Long idSolicitudLiquidacion = consultarIdSolicitud(numeroRadicado);        
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);  
        try {

            GenerarRegistroArchivoLiquidacion servicio1 = new GenerarRegistroArchivoLiquidacion(numeroRadicado);
            servicio1.execute();
            Long idArchivoLiquidacion = servicio1.getResult();
            //Archivo sin derecho
            GenerarArchivoResultadoPersonasSinDerechoAsync servicio2 = new GenerarArchivoResultadoPersonasSinDerechoAsync(idArchivoLiquidacion, numeroRadicado);
            servicio2.execute();
            //Archivo con derecho
            GenerarArchivoLiquidacion servicio3 = new GenerarArchivoLiquidacion(idArchivoLiquidacion, numeroRadicado);
            servicio3.execute();
        } catch (Exception e) {
            logger.error("Fallo en la ejecucion de los procesos de generacion arhcivos de liquidacion", e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarFechaDispersion(java.lang.Long)
     */
    @Override
    public void actualizarFechaDispersion(Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.actualizarFechaDispersion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.actualizarFechaDispersion(idSolicitudLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarEstadoDerechoLiquidacion(java.lang.String)
     */
    @Override
    public void actualizarEstadoDerechoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.actualizarEstadoDerechoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.actualizarEstadoDerechoLiquidacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public void guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.guardarCondicionesEspecialesReconocimiento(condiciones, idSolicitudLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarSPLiquidacionReconocimiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    @Override
    public void ejecutarSPLiquidacionReconocimiento(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPLiquidacionEspecifica: " + numeroRadicado;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarSPLiquidacionReconocimiento(numeroRadicado);
        //Ejecucion automatica de los archivos sin y con derecho de manera asincrona
        // Persistencia temprana de archivo liquidacion subsidio
        try {

            GenerarRegistroArchivoLiquidacion servicio1 = new GenerarRegistroArchivoLiquidacion(numeroRadicado);
            servicio1.execute();
            Long idArchivoLiquidacion = servicio1.getResult();
            //Archivo sin derecho
            GenerarArchivoResultadoPersonasSinDerechoAsync servicio2 = new GenerarArchivoResultadoPersonasSinDerechoAsync(idArchivoLiquidacion, numeroRadicado);
            servicio2.execute();
            //Archivo con derecho
            GenerarArchivoLiquidacion servicio3 = new GenerarArchivoLiquidacion(idArchivoLiquidacion, numeroRadicado);
            servicio3.execute();
        } catch (Exception e) {
            logger.error("Fallo en la ejecucion de los procesos de generacion arhcivos de liquidacion", e);
        }
        Long idSolicitudLiquidacion = consultarIdSolicitud(numeroRadicado);        
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);  

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarSPLiquidacionReconocimiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionReconocimientoSincrono(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarSPLiquidacionReconocimiento(numeroRadicado);
        Long idSolicitudLiquidacion = consultarIdSolicitud(numeroRadicado);        
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);  

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)    
    @Override    
    public List<String> ejecutarSPGestionarColaEjecucionLiquidacion() {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPGestionarColaEjecucionLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<String> liquidacionesPorEjecutar = consultasSubsidio.ejecutarSPGestionarColaEjecucionLiquidacion();   
        
        if (liquidacionesPorEjecutar == null)
            logger.debug("no hay liquidaciones pendientes");

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return liquidacionesPorEjecutar;
    }
    

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarResultadoLiquidacionFallecimiento(java.lang.String)
     */
 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarResultadoLiquidacionFallecimiento(String) numeroRadicacion: "+numeroRadicacion;
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO result = consultasSubsidio.consultarResultadoLiquidacionFallecimiento(numeroRadicacion);
        //Verifica si es por APORTE_MINIMO
        logger.info("Resultado de consultarResultadoLiquidacionFallecimiento: " + result);
        logger.info("Resultado de consultarResultadoLiquidacionFallecimiento: " + consultasSubsidio.consultarValidacionAporteMinimoFallecimiento(numeroRadicacion));
        result.setIsAporteMinimo(consultasSubsidio.consultarValidacionAporteMinimoFallecimiento(numeroRadicacion));
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarResultadoLiquidacionFallecimiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimientoConfirmados(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarResultadoLiquidacionFallecimientoConfirmados(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO result = consultasSubsidio.consultarResultadoLiquidacionFallecimientoConfirmados(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#confirmarBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public void confirmarBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionBeneficiario) {
        String firmaMetodo = "SubsidioMonetarioBusiness.confirmarBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.confirmarBeneficiarioLiquidacionFallecimiento(numeroRadicacion, idCondicionBeneficiario);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#confirmarAfiliadoLiquidacionFallecimienot(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public void confirmarAfiliadoLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionAfiliado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.confirmarAfiliadoLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.confirmarAfiliadoLiquidacionFallecimiento(numeroRadicacion, idCondicionAfiliado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarDetalleBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleLiquidacionBeneficiarioFallecimientoDTO consultarDetalleBeneficiarioLiquidacionFallecimiento(String numeroRadicacion,
            Long idCondicionBeneficiario) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarDetalleBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DetalleLiquidacionBeneficiarioFallecimientoDTO detalleLiquidacionDTO = consultasSubsidio
                .consultarDetalleBeneficiarioLiquidacionFallecimiento(numeroRadicacion, idCondicionBeneficiario);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleLiquidacionDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajador(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaSubsidioFallecimientoTrabajador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        //consultasSubsidio.ejecutarOrquestadorStaginFallecimiento((new Date()).getTime(), tipoIdentificacion, numeroIdentificacion);

        PersonaFallecidaTrabajadorDTO persona = consultasCore.seleccionarPersonaSubsidioFallecimientoTrabajador(tipoIdentificacion, numeroIdentificacion, tipoLiquidacion);
        logger.info("despues de seleccionarPersonaSubsidioFallecimientoTrabajador idPersona: "+persona.getIdPersona());
        //se retorna si esta activo en la caja
        if (persona.getActivoEnCaja() != null && persona.getActivoEnCaja()
                && TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(tipoLiquidacion))
            return persona;
        
        //Hacer la verificacion Caso 5 de HU-317-503
        if(tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE)){
            persona = consultasSubsidio.consultarBeneficiarioActivoAlFallecerAfiliado(persona);
        }
        
        // Verificar las condiciones para el beneficiario
        if(tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO) && !persona.getListaBeneficiarios().isEmpty()){
            // Condiciones beneficiario en Core
            persona = consultasCore.consultarCondicionesBeneficiarioFallecido(persona);
            // Condiciones beneficiario en subsidio (staging)
            persona = consultasSubsidio.consultarCondicionesBeneficiarioFallecido(persona);
        } 

        // Verificar si los beneficiarios han tenido derecho asignado en la ultima liquidacion
        if (!persona.getListaBeneficiarios().isEmpty()) {
            persona = consultasSubsidio.consultarEstadoDerechoBeneficiarios(persona);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return persona;

    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarCondicionesPersonas(java.lang.String, java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaSubsidioFallecimientoTrabajador(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, CondicionPersonaLiquidacionDTO> resultado = consultasSubsidio.consultarCondicionesPersonas(numeroRadicacion,
                identificadoresCondiciones);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarCondicionesEntidadesDescuento(java.lang.String,
     *      java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<Long, CondicionEntidadDescuentoLiquidacionDTO> consultarCondicionesEntidadesDescuento(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarCondicionesEntidadesDescuento(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, CondicionEntidadDescuentoLiquidacionDTO> resultado = consultasSubsidio
                .consultarCondicionesEntidadesDescuento(numeroRadicacion, identificadoresCondiciones);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarSPLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long, java.lang.Boolean)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionFallecimiento(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //consultasSubsidio.ejecutarSPLiquidacionFallecimiento(numeroRadicado, periodo, beneficiarioFallecido);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarDesembolsoSubsidioLiquidacionFallecimiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoLiquidacionFallecimientoDTO consultarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarDesembolsoSubsidioLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultado = null;
        Date periodoFallecimiento = null;
        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultasCore.consultarSolicitudLiquidacion(numeroRadicacion);

        if (solicitudLiquidacionDTO.getTipoLiquidacionEspecifica().equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE)) {
            periodoFallecimiento = consultasSubsidio.consultarPeriodoFallecimientoAfiliado(numeroRadicacion);
        }
        else {
            periodoFallecimiento = consultasSubsidio.consultarPeriodoFallecimientoBeneficiario(numeroRadicacion);
            
             //si el afiliado no cumple las validaciones
            if (periodoFallecimiento == null) {
                periodoFallecimiento = consultasSubsidio.consultarPeriodoFallecimientoBenAfiliadoNoCumpleValidaciones(numeroRadicacion);
            }
        }
        resultado = consultasSubsidio.consultarDesembolsoSubsidioLiquidacionFallecimiento(numeroRadicacion, periodoFallecimiento);
        resultado.setPeriodoFallecimiento(periodoFallecimiento);
        //Consulta si la causal es por aporte mínimo.
        resultado.setIsAporteMinimo(consultasSubsidio.consultarValidacionAporteMinimoFallecimiento(numeroRadicacion));
        
        if (solicitudLiquidacionDTO.getConsideracionAporteDesembolso() != null) {
            resultado.setConsideracionAportes(solicitudLiquidacionDTO.getConsideracionAporteDesembolso());
        }
        if (solicitudLiquidacionDTO.getModoDesembolso() != null) {
            resultado.setTipoDesembolso(solicitudLiquidacionDTO.getModoDesembolso());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarDesembolsoSubsidioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Byte, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum)
     */
    @Override
    public void actualizarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso) {
        String firmaMetodo = "SubsidioMonetarioBusiness.actualizarDesembolsoSubsidioLiquidacionFallecimiento(String,Boolean,ModoDesembolsoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        consultasCore.actualizarDesembolsoSubsidioLiquidacionFallecimiento(numeroRadicacion, consideracionAportes, tipoDesembolso);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarHistoricoLiquidacionFallecimiento(java.lang.Long,
     *      java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ResultadoHistoricoLiquidacionFallecimientoDTO> consultarHistoricoLiquidacionFallecimiento(Long periodoRegular,
            Long fechaInicio, Long fechaFin, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarHistoricoLiquidacionFallecimiento(Long,Long,Long,TipoIdentificacionEnum,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ResultadoHistoricoLiquidacionFallecimientoDTO> result = consultasCore.consultarHistoricoLiquidacionFallecimiento(
                periodoRegular, fechaInicio, fechaFin, tipoIdentificacion, numeroIdentificacion, numeroRadicacion, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#enviarResultadoLiquidacionAPagosFallecimiento(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void enviarResultadoLiquidacionAPagosFallecimiento(String nombreUsuario, String numeroRadicado,
            ModoDesembolsoEnum modoDesembolso) {
        String firmaMetodo = "SubsidioMonetarioBusiness.enviarResultadoLiquidacionAPagosFallecimiento(String,String,ModoDesembolsoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.enviarResultadoLiquidacionAPagosFallecimiento(nombreUsuario, numeroRadicado, modoDesembolso);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarParametrizacionLiqEspecifica(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public LiquidacionEspecificaDTO consultarParametrizacionLiqEspecifica(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarHistoricoLiquidacionFallecimiento(Long,Long,Long,TipoIdentificacionEnum,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        LiquidacionEspecificaDTO liquidacion = consultasCore.consultarParametrizacionLiqEspecifica(numeroRadicado);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        return liquidacion;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#verificarLiquidacionEnProcesoInfoCompleta(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO verificarLiquidacionEnProcesoInfoCompleta(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.verificarLiquidacionEnProcesoInfoCompleta()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = inicializarPantallaSolicitudLiquidacionConHoraCorteAportes(userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValidacionFallidaPersonaFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConjuntoValidacionSubsidioEnum consultarValidacionFallidaPersonaFallecimiento(String numeroRadicacion, Long condicionPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValidacionFallidaPersonaFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConjuntoValidacionSubsidioEnum result = consultasSubsidio.consultarValidacionFallidaPersonaFallecimiento(numeroRadicacion,
                condicionPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarIdentificadorPersonaCore(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarIdentificadorPersonaCore(String numeroRadicacion, Long condicionPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarIdentificadorPersonaCore(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasSubsidio.consultarIdentificadorPersonaCore(numeroRadicacion, condicionPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(numeroRadicacion, idPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(numeroRadicacion,
                idPersona);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValidacionesTipoProceso(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConjuntoValidacionSubsidioEnum> consultarValidacionesTipoProceso(TipoValidacionLiquidacionEspecificaEnum tipoProceso) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValidacionesTipoProceso(TipoValidacionLiquidacionEspecificaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConjuntoValidacionSubsidioEnum> result = consultasCore.consultarValidacionesTipoProceso(tipoProceso);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarIdentificadorConjuntoValidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarIdentificadorConjuntoValidacion(ConjuntoValidacionSubsidioEnum validacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarIdentificadorConjuntoValidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.consultarIdentificadorConjuntoValidacion(validacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#registrarAplicacionValidacionSubsidio(com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO)
     */
    @Override
    public Long registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO) {
        String firmaMetodo = "SubsidioMonetarioBusiness.registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.registrarAplicacionValidacionSubsidio(aplicacionValidacionDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#registrarAplicacionValidacionSubsidioPersona(com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO)
     */
    @Override
    public Long registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO) {
        String firmaMetodo = "SubsidioMonetarioBusiness.registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasCore.registrarAplicacionValidacionSubsidioPersona(aplicacionValidacionPersonaDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarSPLiquidacionFallecimientoGestionPersona(java.lang.String,
     *      java.lang.Long, java.lang.Boolean, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void ejecutarSPLiquidacionFallecimientoGestionPersona(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ejecutarSPLiquidacionFallecimientoGestionPersona()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.ejecutarSPLiquidacionFallecimientoGestionPersona(numeroRadicado, periodo, beneficiarioFallecido,
                tipoIdentificacion, numeroIdentificacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarParametrizacionLiquidacionMasiva(java.lang.String)
     */
    @Override
    public IniciarSolicitudLiquidacionMasivaDTO consultarParametrizacionLiquidacionMasiva(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.consultarParametrizacionLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = consultasCore.consultarParametrizacionLiquidacionMasiva(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarInformacionComunicadosLiquidacionMasiva(java.lang.String)
     */
    public List<DatosComunicadoDTO> consultarInformacionComunicadosLiquidacionMasiva(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.consultarInformacionComunicadosLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();
        SolicitudLiquidacionSubsidioModeloDTO sol = consultarSolicitudLiquidacion(numeroRadicacion);
        switch (sol.getTipoLiquidacion()) {
        case MASIVA: 
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_TRA, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_ADM_SUB, numeroRadicacion, false));
            break;
        case RECONOCIMIENTO_DE_SUBSIDIOS:
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_TRA, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_ADM_SUB, numeroRadicacion, false));
            break;
        case AJUSTES_DE_CUOTA:
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_TRA, numeroRadicacion, false));
            comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_ADM_SUB, numeroRadicacion, false));
            break;
        case SUBSUDIO_DE_DEFUNCION:
            if(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(sol.getTipoLiquidacionEspecifica())) {
                comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA, numeroRadicacion, true));
                comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_ADM_SUB, numeroRadicacion, true));
            } else {
                comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA, numeroRadicacion, true));
                comunicados.addAll(llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_ADM_SUB, numeroRadicacion, true));
            }
            break;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return comunicados;

    }
    
    /**
     * Se encarga de consultar los destinatarios y la información adicional del comunicado de la liquidación masiva
     * segun el tipo de etiqueta
     * 
     * @param etiquetaPlantillaComunicado
     * @param numeroRadicacion
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DatosComunicadoDTO> llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado,
            String numeroRadicacion, Boolean fallecimiento) {
        String firmaMetodo = "SubsidioBusiness.llenarDatosComunciado(EtiquetaPlantillaComunicadoEnum,String,Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            final int idPersona = 0;
            final int numeroTrabBen = 1;
            final int valorCuota = 2;
            final int periodo = 3;

            List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();
            Set<Long> idDestinatariosComunicados = new HashSet<Long>();
            List<Object[]> destinatariosUnicos = new ArrayList<Object[]>();
            List<Object[]> variables = consultasSubsidio
                .consultarInformacionComunicadosLiquidacionMasiva(numeroRadicacion, etiquetaPlantillaComunicado);
            if(variables == null) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }
            
            Long id;
            Map<Long, String> periodos = new HashMap<Long, String>();
            StringBuilder periodosTmp = new StringBuilder();
            for (Object[] fila : variables) {
                periodosTmp.setLength(0);
                id = new Long(fila[idPersona].toString());
                idDestinatariosComunicados.add(id);
                if (periodos.containsKey(id)) {
                    periodosTmp.append(periodos.get(id));
                    periodosTmp.append(", ");
                } else {
                    destinatariosUnicos.add(fila);
                }
                periodosTmp.append(fila[periodo]);
                periodos.put(id, periodosTmp.toString());
            }
            
            StringBuilder nombreFallecido = new StringBuilder();
            if(Boolean.TRUE.equals(fallecimiento)) {
                List<Object[]> nombres = consultasSubsidio.consultarNombreFallecido(numeroRadicacion);
                for (Object[] fila : nombres) {
                    nombreFallecido.setLength(0);
                    if (nombreFallecido.length() > 0) {
                        nombreFallecido.append(", ");
                    }
                    nombreFallecido.append(fila[1]);
                }
            }

            if(idDestinatariosComunicados.isEmpty()) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }
            
            Map<Long, AutorizacionEnvioComunicadoDTO> destinatarios = consultasCore
                    .consultarDestinatariosComunicadosLiquidacionMasiva(idDestinatariosComunicados, etiquetaPlantillaComunicado);
            if(destinatarios == null) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }

            DatosComunicadoDTO comunicado;
            for (Object[] fila : destinatariosUnicos) {
                id = new Long(fila[idPersona].toString());
                if (destinatarios.containsKey(id)) {
                    comunicado = new DatosComunicadoDTO();
                    comunicado.setIdPersona(id);
                    comunicado.setDestinatario(destinatarios.get(id).getDestinatario());
                    comunicado.setAutorizaEnvio(destinatarios.get(id).getAutorizaEnvio());
                    comunicado.setVariables(new HashMap<String, String>());
                    comunicado.getVariables().put(
                            EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP.equals(etiquetaPlantillaComunicado)? 
                            DatosComunicadoDTO.NUMERO_TRABAJADORES : DatosComunicadoDTO.NUMERO_BENEFICIARIOS,
                            fila[numeroTrabBen].toString());
                    comunicado.getVariables().put(DatosComunicadoDTO.MONTO_LIQUIDADO, fila[valorCuota].toString());
                    if (periodos.containsKey(id)) {
                        comunicado.getVariables().put(DatosComunicadoDTO.PERIODOS_LIQUIDADOS, periodos.get(id));
                    }
                    
                    if(Boolean.TRUE.equals(fallecimiento)) {
                        String nombreFallecidoCapitalizado = capitalizar(nombreFallecido != null ? nombreFallecido.toString() : "");
                        comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_FALLECIDO, nombreFallecidoCapitalizado);
                    }
                    comunicado.setEtiqueta(etiquetaPlantillaComunicado);
                    comunicados.add(comunicado);
                    destinatarios.remove(id);
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return comunicados;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return new ArrayList<DatosComunicadoDTO>();
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarPorcentajeAvanceProcesoLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Integer consultarPorcentajeAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.consultarPorcentajeAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Integer result = 0;
        try{
            Thread.sleep(2500); //dos segundos y medio
            result = consultasSubsidio.consultarPorcentajeAvanceProcesoLiquidacion(numeroRadicacion);
        } catch (Exception e) {
            System.out.println(e);
        }


        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#cancelarProcesoLiquidacion(java.lang.String)
     */
    @Override
    public void cancelarProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.cancelarProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.cancelarProcesoLiquidacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#agregarDetallesProgramadosToDetallesSubsidiosAsignados()
     */
    @Override
    public void agregarDetallesProgramadosToDetallesSubsidiosAsignados() {
        String firmaMetodo = "SubsidioBusiness.agregarDetallesProgramadosToDetallesSubsidiosAsignados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Boolean isDiaDeDispersion = consultasCore.consultarDiaDelPeriodoDispersionDetallesProgramadosToDetallesAsignados();
        //si se ingresa a la condición es porque en el periodo, es el día acordado para dispersar los detalles programados
        if(isDiaDeDispersion){
            //se ejecuta el SP que realiza el cambio de los registros de los detalles programados a la tabla de detalles subsidios asignados
            consultasCore.agregarRegistroDeDetallesProgramadosToDetallesAsignados();
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#iniciarPorcentajeAvanceProcesoLiquidacion(java.lang.String)
     */
    @Override
    public void iniciarPorcentajeAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.iniciarPorcentajeAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.iniciarAvanceProcesoLiquidacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#verificarLiquidacionFallecimientoEnProceso()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean verificarLiquidacionFallecimientoEnProceso() {
        String firmaMetodo = "SubsidioMonetarioBusiness.verificarLiquidacionFallecimientoEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean hayLiquidacionEnProceso = consultasCore.verificarLiquidacionFallecimientoEnProceso();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return hayLiquidacionEnProceso;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarBeneficiariosAfiliadoCondicionesLiquidacion(java.lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliadoCondicionesLiquidacion(Long idSolicitud, Long idPersona) {
        String firmaMetodo = "SubsidioBusiness.consultarBeneficiariosAfiliadoCondicionesLiquidacion(Long,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> lstBeneficiarios = consultasCore.consultarBeneficiariosAfiliadoCondicionesLiquidacion(idSolicitud,idPersona);
        
        PersonaFallecidaTrabajadorDTO persona = new PersonaFallecidaTrabajadorDTO();
        persona.setListaBeneficiarios(lstBeneficiarios);
        
        //se obtiene si los beneficiarios tienen derechos asignados o no
        List<BeneficiariosAfiliadoDTO> result = consultasSubsidio.consultarEstadoDerechoBeneficiarios(persona).getListaBeneficiarios();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#eliminarProcesoGestionarTrabajadorBeneficiario(java.lang.String)
     */
    @Override
    public void eliminarProcesoGestionarTrabajadorBeneficiario(String numeroRadicacion, Long idCondicionPersona,
            TipoLiquidacionEspecificaEnum tipoLiquidacion, Boolean cumple, Boolean esTrabajadorFallecido) {
        String firmaMetodo = "SubsidioBusiness.eliminarProcesoGestionarTrabajadorBeneficiario(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionSubsidioModeloDTO = consultarSolicitudLiquidacion(numeroRadicacion);
        //se obtiene la primera validación que se tenía del registro de la persona
        GrupoAplicacionValidacionSubsidioEnum validacion = consultasSubsidio.obtenerPrimeraValidacionSubsidio(numeroRadicacion,
                idCondicionPersona, tipoLiquidacion, cumple, esTrabajadorFallecido);
        if (validacion != null) {
            //se obtiene el identificador del ultimo registro almacenado en la tabla AplciacionValidacionSubsidio asociado a la Liquidación
            List<Long> lstIdAplicacionValidacionDTO = consultasCore.obtenerAplicacionValidacionSubsidioPorIDSolicitudLiquidacionSubsidio(
                    solicitudLiquidacionSubsidioModeloDTO.getIdProcesoLiquidacionSubsidio(), validacion);

            if (lstIdAplicacionValidacionDTO != null) {
                //se elimina el registro AplicaionValidacionSubsidio persona asociado a registro AplicacionValidacionSubsidio
                consultasCore.eliminarRegistroAplicacionValidacionSubsidioPersona(lstIdAplicacionValidacionDTO);
                //se elimina el registro AplicacionValidacionSubsidio
                consultasCore.eliminarRegistroAplicacionValidacionSubsidioPorId(lstIdAplicacionValidacionDTO);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    } 

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#cancelarMasivaActualizarObservacionesProceso(java.lang.String, java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String,String> cancelarMasivaActualizarObservacionesProceso(String numeroSolicitud, String observacion, UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.actualizarObservacionesProceso(Long, String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        HashMap<String, String> resultado = new HashMap<>();      
        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = consultasCore.consultarSolicitudLiquidacion(numeroSolicitud);
        if (solicitudLiquidacion.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.MASIVA)) {
            if(consultasSubsidio.eliminarLiquidacionSP(numeroSolicitud, Boolean.FALSE)){
            	resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación en este momento, por favor intentar en un momento");
                return resultado;
            }            
        }
        else {
            consultasSubsidio.activarEnColaProcesoLiquidacion(numeroSolicitud);
        }
        
        consultasCore.cancelarMasivaActualizarObservacionesProceso(numeroSolicitud, observacion);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        return null;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarLiquidacionesPorEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorEmpleador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long periodo, Long fechaInicio,
            Long fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorEmpleador(TipoIdentificacionEnum, String, Long, Long, Long, String, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date dPeriodo = null;
        Date dFechaInicio = null;
        Date dFechaFin = null;
        
        if (periodo != null) {
            dPeriodo = new Date(periodo);
        }

        if (fechaInicio != null) {
            dFechaInicio = new Date(fechaInicio);
        }

        if (fechaFin != null) {
            dFechaFin = new Date(fechaFin);
        }
        
        List<ConsultaLiquidacionSubsidioMonetarioDTO> resultado = consultasSubsidio.consultarLiquidacionesPorEmpleador(
                tipoIdentificacion, numeroIdentificacion, dPeriodo, dFechaInicio,
                dFechaFin, numeroRadicacion, uri, response);
        
        if(!resultado.isEmpty())
            consultasCore.obtenerLiquidacionesporTrabajadorVista360(resultado);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<Long> periodo,
            Long fechaInicio, Long fechaFin, String numeroRadicacion, TipoProcesoLiquidacionEnum tipoLiquidacion,
            UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, Long, Long, Long, String, TipoProcesoLiquidacionEnum, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Date> dPeriodo = null;
        Date dFechaInicio = null;
        Date dFechaFin = null;

        if (periodo != null) {
            for (Long p : periodo) {
                if (dPeriodo == null) {
                    dPeriodo = new ArrayList<>();
                }
                dPeriodo.add(new Date(p));
            }
        }

        if (fechaInicio != null) {
            dFechaInicio = new Date(fechaInicio);
        }

        if (fechaFin != null) {
            dFechaFin = new Date(fechaFin);
        }

        List<ConsultaLiquidacionSubsidioMonetarioDTO> resultado = consultasSubsidio.consultarLiquidacionesPorTrabajador(
                tipoIdentificacion, numeroIdentificacion, dPeriodo, dFechaInicio, dFechaFin, numeroRadicacion,
                tipoLiquidacion, uri, response);
        
        if(!resultado.isEmpty())
            consultasCore.obtenerLiquidacionesporTrabajadorVista360(resultado);
        
        List<String> numerosRadicados = new ArrayList<>();
        
        for (ConsultaLiquidacionSubsidioMonetarioDTO r : resultado) {
            numerosRadicados.add(r.getNumeroRadicado());
        }

        Map<String, Object[]> pagosXRadicadoYtipoIndentificacionNumeroDeIdentificacionEmpleador = new HashMap<>();
        if (!numerosRadicados.isEmpty()) {
            List<Object[]> pagos = consultasCore.consultarPagosLiquidacionesPorTrabajador(tipoIdentificacion,
                    numeroIdentificacion, numerosRadicados);
            for(Object[] p : pagos) {
                pagosXRadicadoYtipoIndentificacionNumeroDeIdentificacionEmpleador.put(p[0].toString()+p[7].toString()+p[8].toString(), p);
            }
        }

        Object[] pago;
        for (int i = 0; i < resultado.size(); i++) {

            if (pagosXRadicadoYtipoIndentificacionNumeroDeIdentificacionEmpleador.containsKey(resultado.get(i).getNumeroRadicado()+ resultado.get(i).getNumeroIdentificacionEmpl()+resultado.get(i).getTipoIdentificacionEmpl().name())) {
                pago = pagosXRadicadoYtipoIndentificacionNumeroDeIdentificacionEmpleador.get(resultado.get(i).getNumeroRadicado()+ resultado.get(i).getNumeroIdentificacionEmpl()+resultado.get(i).getTipoIdentificacionEmpl().name());
                // Datos traidos desde DB subsidio (Liquidación)
                // MontoLiquidado
                // MontoDescontado
                // MontoParaPago
                
                // Datos traidos desde DB Core (Disperción)
                // MontoDispersado
                // MontoRetirado
                // MontoProgramado
                // TotalPagar
                
                // valor liquidado/dispersado debe coincidir con MontoLiquidado
                resultado.get(i).setMontoDispersado(pago[1] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(pago[1].toString())));

                // valor pendiente programado
                resultado.get(i).setMontoProgramado(pago[2] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(pago[2].toString())));

                // valor pagado
                resultado.get(i).setMontoRetirado(pago[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.parseDouble(pago[3].toString())));

                // valor saldo
                resultado.get(i).setMontoParaPago(pago[4] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(pago[4].toString())));
                if (resultado.get(i).getCumple() != null && resultado.get(i).getCumple().equals(Boolean.FALSE)) {
                    resultado.get(i).setMontoParaPago(BigDecimal.ZERO);
                }
                resultado.get(i).setTotalPagar(pago[4] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(pago[4].toString())));
                
            }
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarValidacionesLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */ 
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ConsultaValidacionesLiquidacionSubsidioMonetarioDTO consultarValidacionesLiquidacionesPorTrabajador(
        TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion, 
        TipoIdentificacionEnum tipoIdentificacionEmpl, String numeroIdentificacionEmpl) {
    String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, Long, Long, Long, String, TipoProcesoLiquidacionEnum, UriInfo, HttpServletResponse)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            int procesadoresDisponibles = Runtime.getRuntime().availableProcessors();
            logger.info("SUBSIDIO -<>>>liberarPlanillasBloque9Process procesadoresDisponibles: "+procesadoresDisponibles+" Hilos: "+java.lang.Thread.activeCount());
            ConsultaValidacionesLiquidacionSubsidioMonetarioDTO validaciones = new ConsultaValidacionesLiquidacionSubsidioMonetarioDTO();
            logger.debug("Tiempo Consulta validaciones Trabajador= " + Calendar.getInstance().getTime());
            List<ConsultaLiquidacionSubsidioMonetarioDTO> validacionesTrabajador = consultasSubsidio
                    .consultarValidacionesLiquidacionesPorTrabajador(tipoIdentificacion, numeroIdentificacion,
                            numeroRadicacion, Boolean.TRUE, tipoIdentificacionEmpl, numeroIdentificacionEmpl);
            logger.debug("Tiempo Consulta validaciones Trabajador FIN= " + Calendar.getInstance().getTime());
            List<String> numerosRadicados = new ArrayList<>();
            numerosRadicados.add(numeroRadicacion); 
            logger.debug("Tiempo Consulta Pagos Liquidaciones= " + Calendar.getInstance().getTime());
            List<Object[]> pagos = consultasCore.consultarPagosLiquidacionesPorTrabajadorPeriodo(tipoIdentificacion,
                    numeroIdentificacion, numerosRadicados,tipoIdentificacionEmpl, numeroIdentificacionEmpl);
            logger.debug("Tiempo Consulta Pagos Liquidaciones FIN= " + Calendar.getInstance().getTime());
            Map<Date, Object[]> pagosXRadicadoPeriodo = new HashMap<>();
            Date periodo;
            for(Object[] p : pagos) {
                periodo = p[5] == null ? null : CalendarUtils.darFormatoYYYYMMDDGuionDate(p[5].toString());
                logger.info(" FOR periodo: "+periodo+" p:"+p);
                pagosXRadicadoPeriodo.put(periodo, p);
            } 
            
            for (ConsultaLiquidacionSubsidioMonetarioDTO valTra : validacionesTrabajador) {
                logger.info(" FOR valTra.GetMontoParaPago: "+valTra.getMontoParaPago());
                if (Boolean.TRUE.equals(valTra.getCumple()) && !BigDecimal.ZERO.equals(valTra.getMontoLiquidado())) {
                    if (pagosXRadicadoPeriodo.containsKey(valTra.getPeriodo())) {
                        Object[] obj = pagosXRadicadoPeriodo.get(valTra.getPeriodo());
                        logger.info(" consultarValidacionesLiquidacionesPorTrabajador obj[4]"+obj[4]);
                        valTra.setMontoRetirado(BigDecimal.valueOf(Double.parseDouble(obj[3] == null ? "0": obj[3].toString())));
                        valTra.setMontoParaPago(valTra.getMontoParaPago());
                    }
                }
            }
            logger.debug("Tiempo Consulta Liquidaciones Empleador= " + Calendar.getInstance().getTime());
            validaciones.setValidacionesTrabajador(validacionesTrabajador);
            validaciones.setValidacionesEmpleador(consultasSubsidio
                    .consultarValidacionesLiquidacionesPorTrabajador(tipoIdentificacion, numeroIdentificacion,
                            numeroRadicacion, Boolean.FALSE, tipoIdentificacionEmpl, numeroIdentificacionEmpl));
            logger.debug("Tiempo Consulta Liquidaciones Empleador FIN= " + Calendar.getInstance().getTime());
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    return validaciones;
}
 
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarGrupoFamiliarLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.util.Date)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleResultadoPorAdministradorDTO> consultarGrupoFamiliarLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, 
            String numeroIdentificacion, 
            TipoIdentificacionEnum tipoIdentificacionEmp, 
            String numeroIdentificacionEmp,
            String numeroRadicacion, 
            Long periodo) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarGrupoFamiliarLiquidacionesPorTrabajador(TipoIdentificacionEnum, String, String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Map<Long, String> sitiosPago = consultasCore.consultarSitiosPago();
        
        Date dPeriodo = null;
        if (periodo != null) {
            dPeriodo = new Date(periodo);
        }
        List<DetalleResultadoPorAdministradorDTO> resultado = consultasSubsidio
                .consultarGrupoFamiliarLiquidacionesPorTrabajador(tipoIdentificacion, numeroIdentificacion,
                        tipoIdentificacionEmp, numeroIdentificacionEmp,
                        numeroRadicacion, dPeriodo, sitiosPago);
        
        List<Long> listaIdsRvl = new ArrayList<>();
        Map<Long, ItemBeneficiarioPorAdministradorDTO> detallesPorIdRvl = new HashMap<>();
        Map<Long, DetalleResultadoPorAdministradorDTO> detallesGrupoPorIdRvl = new HashMap<>();
        for (DetalleResultadoPorAdministradorDTO registro : resultado) {
            for (ItemBeneficiarioPorAdministradorDTO beneficiario : registro.getBeneficiariosPorAdministrador()) {
                detallesPorIdRvl.put(beneficiario.getIdResultadoValidacionLiquidacion(), beneficiario);
                detallesGrupoPorIdRvl.put(beneficiario.getIdResultadoValidacionLiquidacion(), registro);
                listaIdsRvl.add(beneficiario.getIdResultadoValidacionLiquidacion());
                beneficiario.setDispersado(Boolean.FALSE); // indica que no se ha consultado en pagos
            }
        }
         
        if (listaIdsRvl.size() > 0) {
            
            List<Object[]> infoPagos = consultasCore.consultarPagosPorResultadoValidacionLiquidacion(listaIdsRvl);
            
            final int idRvl = 0;
            final int valorDispersado = 1;
            final int pendienteProgramado = 2;
            final int pagado = 3;
            final int saldo = 4;
            final int estadoSubsidio = 5;
            final int valorAnulado = 6;
            
            DetalleResultadoPorAdministradorDTO grupo;
            ItemBeneficiarioPorAdministradorDTO beneficiario;
            
            for (Long idRvlPagos : listaIdsRvl) {
                
                for (Object[] obj : infoPagos) {
                    if (Long.valueOf(obj[idRvl].toString()).equals(idRvlPagos) && detallesPorIdRvl.containsKey(idRvlPagos)) {
                        beneficiario = detallesPorIdRvl.get(idRvlPagos);
                        
                        beneficiario.setTotalDispersado(BigDecimal.valueOf(Double.parseDouble(obj[valorDispersado] == null ? "0": obj[valorDispersado].toString())));
                        beneficiario.setPendienteProgramado(BigDecimal.valueOf(Double.parseDouble(obj[pendienteProgramado] == null ? "0": obj[pendienteProgramado].toString())));
                        beneficiario.setPagado(BigDecimal.valueOf(Double.parseDouble(obj[pagado] == null ? "0": obj[pagado].toString())));
                        beneficiario.setSaldo(BigDecimal.valueOf(Double.parseDouble(obj[saldo] == null ? "0": obj[saldo].toString())));
                        beneficiario.setDispersado(Boolean.TRUE); // indica que se consultó en pagos
                        //Indica el estado cuando se ha dispersado el subsidio
                        beneficiario.setEstadoSubsidio(obj[estadoSubsidio] != null ? EstadoSubsidioAsignadoEnum.valueOf(obj[estadoSubsidio].toString()) : null);
                        
                        if (Long.valueOf(obj[idRvl].toString()).equals(idRvlPagos) && detallesGrupoPorIdRvl.containsKey(idRvlPagos)) {
                            grupo = detallesGrupoPorIdRvl.get(idRvlPagos);
                            
                            if(beneficiario.getEstadoSubsidio() != null && beneficiario.getEstadoSubsidio().name().equals(EstadoSubsidioAsignadoEnum.ANULADO.name())){
                                grupo.setTotalPago(grupo.getTotalPago().subtract(BigDecimal.valueOf(Double.parseDouble(obj[valorAnulado] == null ? "0": obj[valorAnulado].toString()))));
                            }
                            else{
                                grupo.setTotalRetirado(grupo.getTotalRetirado().add(beneficiario.getPagado()));
                                grupo.setTotalPago(grupo.getTotalPago().subtract(beneficiario.getPagado()));
                            }
                            //grupo.setTotalDispersado(grupo.getTotalDispersado() != null ? grupo.getTotalDispersado().add(beneficiario.getTotalDispersado()) : beneficiario.getTotalDispersado());
                        }
                    }
                }
            }
        }
            
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#exportarLiquidacionesPorEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long periodo, Long fechaInicio,
            Long fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorEmpleador(TipoIdentificacionEnum, String, Long, Long, Long, String, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date dPeriodo = null;
        Date dFechaInicio = null;
        Date dFechaFin = null;

        if (periodo != null) {
            dPeriodo = new Date(periodo);
        }

        if (fechaInicio != null) {
            dFechaInicio = new Date(fechaInicio);
        }

        if (fechaFin != null) {
            dFechaFin = new Date(fechaFin);
        }
        Date fechaDispersion = null;
        List<RegistroLiquidacionSubsidioDTO> resultado = null;
        if(numeroRadicacion != null){
            SolicitudLiquidacionSubsidioModeloDTO solicitud = consultarSolicitudLiquidacion(numeroRadicacion);
            fechaDispersion = solicitud != null?solicitud.getFechaDispersion():null;
        }else{
        resultado = consultasCore.exportarLiquidacionesPorEmpleador(tipoIdentificacion,
                numeroIdentificacion, dPeriodo, dFechaInicio, dFechaFin, numeroRadicacion, uri, response);
        }
        
        List<RegistroLiquidacionSubsidioDTO> resultadoSubsidio = consultasSubsidio.exportarLiquidacionesPorEmpleadorNoDispersadas(
                tipoIdentificacion, numeroIdentificacion, dPeriodo, dFechaInicio, dFechaFin, numeroRadicacion);
        

        //se guardan los registros que no estan dispersados los cuales no se encuentran
        for (int i = 0; i < resultadoSubsidio.size(); i++) {
            final int j = i;
            if (resultado != null && resultado.stream().anyMatch(obj -> obj.getNumeroRadicado().equals(resultadoSubsidio.get(j).getNumeroRadicado()))) {
                Date fecha = resultado.stream().filter(obj -> obj.getNumeroRadicado().equals(resultadoSubsidio.get(j).getNumeroRadicado()))
                        .findFirst().get().getFechaDispersion();
                resultadoSubsidio.get(i).setFechaDispersion(fecha);
            }else{
                resultadoSubsidio.get(j).setFechaDispersion(fechaDispersion);
            }
        }
        
        
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadoSubsidio;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarInfoLiquidacionFallecimientoVista360(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleLiquidacionSubsidioEspecificoFallecimientoDTO consultarInfoLiquidacionFallecimientoVista360(String numeroRadicado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarInfoLiquidacionFallecimientoVista360(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        DetalleLiquidacionSubsidioEspecificoFallecimientoDTO detalle = new DetalleLiquidacionSubsidioEspecificoFallecimientoDTO();

        Object[] detalleCore = consultasCore.consultarInfoLiquidacionFallecimientoVista360(numeroRadicado);
        if (detalleCore != null) { 
            final int numeroRadicacion = 0;
            final int slsFechaInicio = 1;
            final int slsTipoLiquidacion = 2;
            final int slsTipoLiquidacionEspecifica = 3;
            final int slsEstadoLiquidacion = 4;
            final int resultadoProceso = 5;
            final int slsConsideracionAporteDesembolso = 6;
            final int slsTipoDesembolso = 7;
            final int valorDispersado = 8;
            final int pendienteProgramado = 9;
            final int pagado = 10;
            final int saldo = 11;
            detalle.setNumeroRadicacion(detalleCore[numeroRadicacion].toString());
            detalle.setFechaLiquidacion((Date) detalleCore[slsFechaInicio]);
            if (detalleCore[slsTipoLiquidacion] != null) {
                detalle.setTipoLiquidacion(TipoProcesoLiquidacionEnum.valueOf(detalleCore[slsTipoLiquidacion].toString()));
            }
            if (detalleCore[slsTipoLiquidacionEspecifica] != null) {
                detalle.setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum.valueOf(detalleCore[slsTipoLiquidacionEspecifica].toString()));
            }
            if (detalleCore[slsEstadoLiquidacion] != null) {
                detalle.setEstadoLiquidacion(EstadoProcesoLiquidacionEnum.valueOf(detalleCore[slsEstadoLiquidacion].toString()));
            }
            detalle.setResultadoProceso(detalleCore[resultadoProceso].toString());
            detalle.setConsideracionAporteDesembolso((Boolean) detalleCore[slsConsideracionAporteDesembolso]);
            if (detalleCore[slsTipoDesembolso] != null) {
                detalle.setModoDesembolso(ModoDesembolsoEnum.valueOf(detalleCore[slsTipoDesembolso].toString()));
            }
            detalle.setValorDispersado(BigDecimal.valueOf(Double.parseDouble(detalleCore[valorDispersado] == null ? "0": detalleCore[valorDispersado].toString())));
            detalle.setPendienteProgramado(BigDecimal.valueOf(Double.parseDouble(detalleCore[pendienteProgramado] == null ? "0": detalleCore[pendienteProgramado].toString())));
            detalle.setPagado(BigDecimal.valueOf(Double.parseDouble(detalleCore[pagado] == null ? "0": detalleCore[pagado].toString())));
            detalle.setSaldo(BigDecimal.valueOf(Double.parseDouble(detalleCore[saldo] == null ? "0": detalleCore[saldo].toString())));
        }

        Object[] detalleSubsidio = consultasSubsidio.consultarInfoLiquidacionFallecimientoVista360(numeroRadicado);
        if (detalleSubsidio != null) {
            final int valorCuota = 0;
            final int valorDescuento = 1;
            final int valorCuotaAjustada = 2;
            final int periodoFallecimiento = 3;
            final int tieneAportes = 4;
            final int estadoAportes = 5;        
            detalle.setValorCuota(BigDecimal.valueOf(Double.parseDouble(detalleSubsidio[valorCuota] == null ? "0": detalleSubsidio[valorCuota].toString())));
            detalle.setValorDescuento(BigDecimal.valueOf(Double.parseDouble(detalleSubsidio[valorDescuento] == null ? "0": detalleSubsidio[valorDescuento].toString())));
            detalle.setValorCuotaAjustada(BigDecimal.valueOf(Double.parseDouble(detalleSubsidio[valorCuotaAjustada] == null ? "0": detalleSubsidio[valorCuotaAjustada].toString())));
            detalle.setFechaFallecimiento((Date) detalleSubsidio[periodoFallecimiento]);
            detalle.setTieneAportes((Boolean) detalleSubsidio[tieneAportes]);
            detalle.setEstadoAportes(detalleSubsidio[estadoAportes].toString());
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalle;
    }
    
    /**
     * Método que se encarga de consultar la información de una liquidación para determinar los datos en pantalla
     * @param userDTO
     * @return
     */
    private IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionConHoraCorteAportes(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.verificarLiquidacionEnProcesoInfoCompleta()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = consultasCore.inicializarPantallaSolicitudLiquidacion(userDTO);
        if (Boolean.FALSE.equals(result.getLiquidacionEnProceso())) {
            result.setFechaHoraCorteAportes(consultasSubsidio.consultarUltimaFechaCorteAportes());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Método que se encarga de consultar la información de una liquidación para determinar los datos en pantalla
     * @param userDTO
     * @return
     */
    /*private IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerradaConHoraCorteAportes(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.verificarLiquidacionEnProcesoInfoCompleta()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = consultasCore.inicializarPantallaSolicitudLiquidacionCerrada(userDTO);
        if (Boolean.FALSE.equals(result.getLiquidacionEnProceso())) {
            result.setFechaHoraCorteAportes(consultasSubsidio.consultarUltimaFechaCorteAportes());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }*/
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarBeneficiarioPadre(java.lang.Long)
     */
    @Override
    public Boolean consultarBeneficiarioPadre(Long idCondicionBeneficiario,String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.consultarBeneficiarioPadre()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return consultasSubsidio.consultarBeneficiarioPadre(idCondicionBeneficiario,numeroRadicado);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarInformacionComunicadosFallecimiento137_138(java.lang.String)
     */
    @Override
    public List<DatosComunicadoDTO> consultarInformacionComunicadosFallecimiento137138(String numeroRadicacion,Long causal) {
        String firmaMetodo = "SubsidioBusiness.consultarInformacionComunicadosFallecimiento137138(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();
        //137 Comunicado Administrador de subsidio
        comunicados.addAll(obtenerDatosComunicado137138Fallecimiento(numeroRadicacion, EtiquetaPlantillaComunicadoEnum.NTF_RCZ_LIQ_ESP_FALL_ADM_SUB,causal));
        //138 Comunicado Trabajador
        comunicados.addAll(obtenerDatosComunicado137138Fallecimiento(numeroRadicacion, EtiquetaPlantillaComunicadoEnum.NTF_RCZ_LIQ_ESP_FALL_TRA,causal));
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return comunicados;
    }

    /**
     * Metodo que permite obtener los datos de cada comunicado
     * @param numeroRadicacion
     *          número de radicado
     * @param etiquetaPlantilla
     *          etiqueta al que pertenece el comunicado
     * @return Lista con los datos del comunicado respectivo
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DatosComunicadoDTO> obtenerDatosComunicado137138Fallecimiento(String numeroRadicacion,
            EtiquetaPlantillaComunicadoEnum etiquetaPlantilla, Long causal) {
        String firmaMetodo = "SubsidioBusiness.obtenerDatosComunicado137_138Fallecimiento(String,EtiquetaPlantillaComunicadoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();

        List<Object[]> informacionComunicado = null;
        Map<Long, List<Object[]>> infoPorPersonaAdminOtrabajador = new HashMap<>();

        //COMUNICADO 137 (138) - RECHAZO LIQ ESPECIFICA FALLECIMIENTO - ADMIN SUBSIDIO
        if (etiquetaPlantilla.equals(EtiquetaPlantillaComunicadoEnum.NTF_RCZ_LIQ_ESP_FALL_TRA)) {

            informacionComunicado = consultasSubsidio.consultarInformacionComunicado137(numeroRadicacion, causal);

        }
        else { //COMUNICADO 138 (139) - RECHAZO LIQ ESPECIFICA FALLECIMIENTO - TRABAJADOR

            informacionComunicado = consultasSubsidio.consultarInformacionComunicado138(numeroRadicacion, causal);

        }

        for (Object[] item : informacionComunicado) {
            Long numeroIdAdmin = Long.valueOf(item[2].toString());
            if (infoPorPersonaAdminOtrabajador.containsKey(numeroIdAdmin)) {
                infoPorPersonaAdminOtrabajador.get(numeroIdAdmin).add(item);
            }
            else {
                infoPorPersonaAdminOtrabajador.put(numeroIdAdmin, new ArrayList<>());
                infoPorPersonaAdminOtrabajador.get(numeroIdAdmin).add(item);
            }
        }

        //se obtiene los ids de las personas relacionadas a los administradores de subsidio
        List<Long> lstIdPersonasAdmin = informacionComunicado.stream()
                .filter(distinctByValue(detalle -> Long.valueOf(detalle[5].toString()))).map(e -> Long.valueOf(e[5].toString()))
                .collect(Collectors.toList());

        //se obtiene el numero de cedula y correo electronico de cada administrador
        Map<Long, AutorizacionEnvioComunicadoDTO> destinatarios = consultasCore.consultarDestinatariosComunicadosFallecimiento137_138(lstIdPersonasAdmin);
        if (destinatarios == null) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return new ArrayList<DatosComunicadoDTO>();
        }

        Iterator<Map.Entry<Long, AutorizacionEnvioComunicadoDTO>> registros = destinatarios.entrySet().iterator();
        while (registros.hasNext()) {
            DatosComunicadoDTO comunicado;
            Map.Entry<Long, AutorizacionEnvioComunicadoDTO> destinatarioAdmin = registros.next();
            if (infoPorPersonaAdminOtrabajador.containsKey(destinatarioAdmin.getKey())) { //se compara si esta la CC del admin
                Boolean entroUnavezAdmin = Boolean.TRUE;
                StringBuilder beneficiarios = new StringBuilder();
                Set<String> nombresBeneficiarios = new HashSet<>();
                for (Object[] item : infoPorPersonaAdminOtrabajador.get(destinatarioAdmin.getKey())) {

                    if (!nombresBeneficiarios.isEmpty() && nombresBeneficiarios.contains(item[3].toString())) {
                        //si se encuentra el nombre del beneficiario repetido por el mismo administrador, se pasa al siguiente registro
                        continue;
                    }
                    //solo se guardan los datos del administrador o trabajador, una vez.
                    if (entroUnavezAdmin) {
                        comunicado = new DatosComunicadoDTO();
                        comunicado.setIdPersona(Long.valueOf(item[5].toString()));
                        comunicado.setDestinatario(destinatarioAdmin.getValue().getDestinatario());
                        comunicado.setAutorizaEnvio(destinatarioAdmin.getValue().getAutorizaEnvio());
                        comunicado.setVariables(new HashMap<String, String>());
                        
                        if(etiquetaPlantilla.equals(EtiquetaPlantillaComunicadoEnum.NTF_RCZ_LIQ_ESP_FALL_TRA)){
                            comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_TRABAJADOR_O_CONYUGE, item[0].toString());
                            comunicado.getVariables().put(DatosComunicadoDTO.TIPO_ID_TRABAJADOR_O_PAREJA, TipoIdentificacionEnum.valueOf(item[1].toString()).getDescripcion());
                            comunicado.getVariables().put(DatosComunicadoDTO.NUM_ID_TRABAJADOR_O_PAREJA, item[2].toString());
                        }else{
                            comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_ADMIN_SUBSIDIO, item[0].toString());
                            comunicado.getVariables().put(DatosComunicadoDTO.TIPO_ID_ADMIN_SUBSIDIO, TipoIdentificacionEnum.valueOf(item[1].toString()).getDescripcion());
                            comunicado.getVariables().put(DatosComunicadoDTO.NUM_ID_ADMIN_SUBSIDIO, item[2].toString());
                        }
                        
                        comunicado.getVariables().put(DatosComunicadoDTO.CAUSA, item[4].toString());
                        comunicado.setEtiqueta(etiquetaPlantilla);
                        beneficiarios.append(item[3].toString());
                        nombresBeneficiarios.add(item[3].toString());
                        comunicados.add(comunicado);
                        entroUnavezAdmin = Boolean.FALSE;
                    }
                    else { //si hay mas beneficiarios por dicho trabajdor o administrador, se almacenan sus nombres
                        beneficiarios.append(", " + item[3].toString());
                        nombresBeneficiarios.add(item[3].toString());
                    }

                }
                Object[] item = infoPorPersonaAdminOtrabajador.get(destinatarioAdmin.getKey()).get(0);
                //se guardan los nombres de los beneficios para ese administrador o trabajador
                comunicados.stream().filter(com -> com.getIdPersona().longValue() == Long.valueOf(item[5].toString())).findFirst().get()
                        .getVariables().put(DatosComunicadoDTO.NOMBRE_BENEFICIARIO_O_TRABAJADOR, beneficiarios.toString());
                infoPorPersonaAdminOtrabajador.remove(destinatarioAdmin.getKey());
            }
        }

        return comunicados;
    }
    
    /**
     * Metódo utilitario para obtener una lista de de datos sin repetición de los mismos.
     * @param keyExtractor
     *        dato que se requiere sea unico.
     * @return lista sin datos repetidos
     */
    private <T> Predicate<T> distinctByValue(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consolidarSubsidiosFallecimiento(java.lang.String)
     */
    @Override
    public void consolidarSubsidiosFallecimiento(String numeroRadicado,ModoDesembolsoEnum modoDesembolso) {
        String firmaMetodo = "SubsidioBusiness.consolidarSubsidiosFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.consolidarSubsidiosFallecimiento(numeroRadicado,modoDesembolso);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#obtenerDatosComunicadosDispersionFallecimiento(com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum, java.lang.String)
     */
    @Override
    public List<DatosComunicadoDTO> obtenerDatosComunicadosDispersionFallecimiento(String numeroRadicacion,  ModoDesembolsoEnum modoDesembolso) {
        String firmaMetodo = "SubsidioBusiness.consultarInformacionComunicadosLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();
        boolean programado = false;
        
        if(ModoDesembolsoEnum.MES_POR_MES.equals(modoDesembolso)){
          //se obtienen los comunicados que son programados
          //074 Notificación de dispersión programada de pagos fallecimiento al trabajador
          //075 Notificación de dispersión programada de pagos fallecimiento al admin
          comunicados.addAll(obtenerDatosDispersionFallecimiento(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA, numeroRadicacion));
          comunicados.addAll(obtenerDatosDispersionFallecimiento(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_ADM_SUB, numeroRadicacion));
          programado = true;
        }else{
           //se obtiene la información de los comunicados cuando es dispersada
           //077 Notificación de dispersión de pagos fallecimiento al trabajador
           //078 Notificación de dispersión de pagos fallecimiento al trabajador
           comunicados.addAll(obtenerDatosDispersionFallecimiento(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA, numeroRadicacion));
           comunicados.addAll(obtenerDatosDispersionFallecimiento(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_ADM_SUB, numeroRadicacion));
        }
        if (comunicados.isEmpty()) {
            DatosComunicadoDTO comunicadoTra =  new DatosComunicadoDTO();
            DatosComunicadoDTO comunicadoAdm =  new DatosComunicadoDTO();
            if (programado){
                comunicadoTra.setEtiqueta(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA);
                comunicadoAdm.setEtiqueta(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_ADM_SUB);
            }else{
                comunicadoTra.setEtiqueta(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA);
                comunicadoAdm.setEtiqueta(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_ADM_SUB);
            }
            comunicados.add(comunicadoTra);
            comunicados.add(comunicadoAdm);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return comunicados;
    }
    
    /**
     * 
     * @param etiquetaPlantillaComunicado
     * @param numeroRadicacion
     * @param adminSubsidio
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<DatosComunicadoDTO> obtenerDatosDispersionFallecimiento(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado,
            String numeroRadicacion){
        String firmaMetodo = "SubsidioBusiness.obtenerDatosDispersionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        try {
            final int idPersona = 0;
            final int numeroTrabBen = 1;
            final int valorCuota = 2;
            final int periodo = 3;

            List<DatosComunicadoDTO> comunicados = new ArrayList<DatosComunicadoDTO>();
            Set<Long> idDestinatariosComunicados = new HashSet<Long>();
            List<Object[]> destinatariosUnicos = new ArrayList<Object[]>();
            List<Object[]> variables = consultasSubsidio
                .consultarInformacionComunicadosLiquidacionMasiva(numeroRadicacion, etiquetaPlantillaComunicado);
            if(variables == null) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }
            
            Long id;
            Map<Long, String> periodos = new HashMap<Long, String>();
            StringBuilder periodosTmp = new StringBuilder();
            for (Object[] fila : variables) {
                periodosTmp.setLength(0);
                id = new Long(fila[idPersona].toString());
                idDestinatariosComunicados.add(id);
                if (periodos.containsKey(id)) {
                    periodosTmp.append(periodos.get(id));
                    periodosTmp.append(", ");
                } else {
                    destinatariosUnicos.add(fila);
                }
                periodosTmp.append(fila[periodo]);
                periodos.put(id, periodosTmp.toString());
            }
            
            StringBuilder nombreFallecido = new StringBuilder();
            List<Object[]> nombres = consultasSubsidio.consultarNombreFallecido(numeroRadicacion);
            for (Object[] fila : nombres) {
                nombreFallecido.setLength(0);
                if (nombreFallecido.length() > 0) {
                    nombreFallecido.append(", ");
                }
                nombreFallecido.append(fila[1]);
            }

            if(idDestinatariosComunicados.isEmpty()) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }
            
            Map<Long, AutorizacionEnvioComunicadoDTO> destinatarios = consultasCore
                    .consultarDestinatariosComunicadosLiquidacionMasiva(idDestinatariosComunicados, etiquetaPlantillaComunicado);
            if(destinatarios == null) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return new ArrayList<DatosComunicadoDTO>();
            }
            
            String periodosSaldos="";
            periodosSaldos = consultasCore.consultarPeriodosLiquidadosDispersionFallecimiento(numeroRadicacion,NamedQueriesConstants.OBTENER_PERIODOS_PROGRAMADOS_DISPERSADOS);
            //se obtienen los periodos del pago programado
            /*if (EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_ADM_SUB.equals(etiquetaPlantillaComunicado)
                    || EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA.equals(etiquetaPlantillaComunicado)) {
                periodosSaldos = consultasCore.consultarPeriodosLiquidadosDispersionFallecimiento(numeroRadicacion,NamedQueriesConstants.OBTENER_PERIODOS_PROGRAMADOS);
            }else if(!EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA.equals(etiquetaPlantillaComunicado)){
                periodosSaldos = consultasCore.consultarPeriodosLiquidadosDispersionFallecimiento(numeroRadicacion,NamedQueriesConstants.OBTENER_PERIODOS_DISPERSADOS);
            }*/
            
            // SE CONSULTA NOMBRE, CEDULA E ID PERSONA DEL TRABAJADOR
            List<Object[]> informacionComunicado = null;
            if(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA.equals(etiquetaPlantillaComunicado) ||
                    EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA.equals(etiquetaPlantillaComunicado))
                informacionComunicado = consultasSubsidio.consultarInformacionComunicado138(numeroRadicacion, 1L);
            else//DATOS ADMIN
                informacionComunicado = consultasSubsidio.consultarInformacionComunicado137(numeroRadicacion, 1L);

            DatosComunicadoDTO comunicado;
            for (Object[] fila : destinatariosUnicos) {
                id = new Long(fila[idPersona].toString()); 
                
                Object [] infoUbicacionAdminTrabajador = null;
                final Long idPer = id;
                Object[] trabajador = informacionComunicado.stream().filter( tra -> Long.parseLong(tra[5].toString()) == idPer.longValue()).findFirst().get();
                boolean esTrabajador = false;
                if(EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PRO_TRA.equals(etiquetaPlantillaComunicado) ||
                    EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA.equals(etiquetaPlantillaComunicado))
                    esTrabajador = true;
                  
                if (destinatarios.containsKey(id)) {
                    
                    infoUbicacionAdminTrabajador = consultasCore.consultarInforUbicacionAdminTrabajadorDispersion508(id);
                    
                    comunicado = new DatosComunicadoDTO();
                    comunicado.setIdPersona(id);
                    comunicado.setDestinatario(destinatarios.get(id).getDestinatario());
                    comunicado.setAutorizaEnvio(destinatarios.get(id).getAutorizaEnvio());
                    comunicado.setVariables(new HashMap<String, String>());
                    comunicado.getVariables().put(
                            EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_PAG_EMP.equals(etiquetaPlantillaComunicado)? 
                            DatosComunicadoDTO.NUMERO_TRABAJADORES : DatosComunicadoDTO.NUMERO_BENEFICIARIOS,
                            fila[numeroTrabBen].toString());
                    comunicado.getVariables().put(DatosComunicadoDTO.MONTO_LIQUIDADO, fila[valorCuota].toString());
                    if (periodos.containsKey(id)) {
                        comunicado.getVariables().put(DatosComunicadoDTO.PERIODOS_LIQUIDADOS, periodos.get(id));
                    }
                    String nombreFallecidoCapitalizado = capitalizar(nombreFallecido != null ? nombreFallecido.toString() : "");
                    comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_FALLECIDO, nombreFallecidoCapitalizado);
                    
                    if(!EtiquetaPlantillaComunicadoEnum.COM_SUB_DIS_FAL_PAG_TRA.equals(etiquetaPlantillaComunicado))
                        comunicado.getVariables().put(DatosComunicadoDTO.REPORTE_BENEFICIARIOS_PERIODOS,periodosSaldos);
                    
                    if(esTrabajador){
                        comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_TRABAJADOR_O_CONYUGE,trabajador[0].toString());
                        comunicado.getVariables().put(DatosComunicadoDTO.TIPO_ID_TRABAJADOR_O_PAREJA, TipoIdentificacionEnum.valueOf(trabajador[1].toString()).getDescripcion());
                        comunicado.getVariables().put(DatosComunicadoDTO.NUM_ID_TRABAJADOR_O_PAREJA,trabajador[2].toString());
                    }else{
                        comunicado.getVariables().put(DatosComunicadoDTO.NOMBRE_ADMIN_SUBSIDIO, trabajador[0].toString());
                        comunicado.getVariables().put(DatosComunicadoDTO.TIPO_ID_ADMIN_SUBSIDIO,TipoIdentificacionEnum.valueOf(trabajador[1].toString()).getDescripcion());
                        comunicado.getVariables().put(DatosComunicadoDTO.NUM_ID_ADMIN_SUBSIDIO, trabajador[2].toString());
                    }
                    //se agrega la información de ubicación
                    if(infoUbicacionAdminTrabajador != null){
                        if(infoUbicacionAdminTrabajador[0]!=null)
                            comunicado.getVariables().put(DatosComunicadoDTO.DIRECCION,infoUbicacionAdminTrabajador[0].toString());
                        if(infoUbicacionAdminTrabajador[1]!=null)
                            comunicado.getVariables().put(DatosComunicadoDTO.MUNICIPIO,infoUbicacionAdminTrabajador[1].toString());
                        if(infoUbicacionAdminTrabajador[2]!=null)
                            comunicado.getVariables().put(DatosComunicadoDTO.DEPARTAMENTO,infoUbicacionAdminTrabajador[2].toString());
                        if(infoUbicacionAdminTrabajador[3]!=null)
                            comunicado.getVariables().put(DatosComunicadoDTO.TELEFONO,infoUbicacionAdminTrabajador[3].toString());
                    }
                    
                    comunicado.setEtiqueta(etiquetaPlantillaComunicado);
                    comunicados.add(comunicado);
                    destinatarios.remove(id);
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return comunicados;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return new ArrayList<DatosComunicadoDTO>();
        }
    
    }

     /**
     * Capitaliza la primera letra de una cadena, haciendo que el resto de las letras estén en minúscula.
     *
     * @param texto El texto a formatear.
     * @return El texto con la primera letra en mayúscula y el resto en minúscula.
     */
    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        String[] palabras = texto.toLowerCase().split(" ");
        StringBuilder textoCapitalizado = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                textoCapitalizado.append(palabra.substring(0, 1).toUpperCase())
                                .append(palabra.substring(1)).append(" ");
            }
        }
        String resultado = textoCapitalizado.toString().trim();
        return resultado;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarParametrosPeriodos(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ValorPeriodoDTO> consultarParametrosPeriodos(List<Long> periodos) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarParametrosPeriodos(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();
        Boolean resultado;
        for (Long periodo : periodos) {
            try {
                consultasCore.consultarCondicionesPeriodo(new Date(periodo));
                consultasCore.consultarParametrosLiquidacionPeriodo(new Date (periodo));
                resultado = Boolean.TRUE;
            } catch (Exception e) {
                resultado = Boolean.FALSE;
                
            }
            valoresPeriodos.add(new ValorPeriodoDTO(periodo, resultado));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valoresPeriodos;
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarPeriodosLiquidacion(java.util.List, java.lang.Long)
     */
    @Override
    public List<TrazabilidadSubsidioEspecificoDTO> obtenerTrazabilidadSubsidioEspecifico(Long identificadorLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerTrazabilidadSubsidioEspecifico(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);  

        List<TrazabilidadSubsidioEspecificoDTO> lstTrazabilidadSubsidio = consultasCore.obtenerTrazabilidadSubsidioEspecifico(identificadorLiquidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstTrazabilidadSubsidio;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#obtenerCuentaCCF()
     */
    @Override
    public CuentaCCFModeloDTO obtenerCuentaCCF(){
         String firmaMetodo = "SubsidioMonetarioBusiness.obtenerCuentaCCF()";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);  
        CuentaCCFModeloDTO cuentaDTO = consultasCore.obtenerCuentaCCF();
        cuentaDTO.setBancos(consultasCore.obtenerBancos());
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cuentaDTO;
    };
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#obtenerCuentaCCF()
     */
    @Override
    public Long registrarCuentaCCF(@NotNull CuentaCCFModeloDTO cuentaDTO){
         String firmaMetodo = "SubsidioMonetarioBusiness.registrarCuentaCCF(CuentaCCFModeloDTO)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);  
        Long idCuenta = consultasCore.registrarCuentaCCF(cuentaDTO);
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idCuenta;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.fovis.service.FovisCargueService#verificarEstructuraArchivoCruce(com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoBloqueoCMDTO verificarEstructuraArchivoBloquedoCM(CargueArchivoBloqueoCMDTO cargue,UserDTO userDTO) {
        logger.debug("Inicio servicio verificarEstructuraArchivoBloquedoCM(InformacionArchivoDTO)");
        
        ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion = new ResultadoValidacionArchivoBloqueoCMDTO();      
        resultadoValidacion.setNumeroRegistros(0);
        resultadoValidacion.setFechaCargueArchivo(new Date());
        resultadoValidacion.setResultadoAllazgos(new ArrayList<>());
        resultadoValidacion.setLineasError(new ArrayList<>());
        InformacionArchivoDTO archivo = cargue.getArchivo();
        
        if(cargue.getNivelBloqueo() == TipoNivelBloqueoEnum.BLOQUEO_BENEFICIARIO_SUBSIDIO){
            if (!archivo.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_BLOQUEO_CM)) {
                ResultadoHallazgosValidacionArchivoDTO resultadoHallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                resultadoHallazgo.setError(ArchivoMultipleCampoConstants.NOMBRE_ARCHIVO_NO_VALIDO);
                resultadoValidacion.getResultadoAllazgos().add(resultadoHallazgo);
                return resultadoValidacion;
            }
        }else if(cargue.getNivelBloqueo() == TipoNivelBloqueoEnum.BLOQUEO_TRABAJADOR_SUBSIDIO){
            if(!archivo.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_BLOQUEO_TRABAJADOR_CM)){
                ResultadoHallazgosValidacionArchivoDTO resultadoHallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                resultadoHallazgo.setError(ArchivoMultipleCampoConstants.NOMBRE_ARCHIVO_NO_VALIDO);
                resultadoValidacion.getResultadoAllazgos().add(resultadoHallazgo);
                return resultadoValidacion;
            }
        }else{
            ResultadoHallazgosValidacionArchivoDTO resultadoHallazgo = new ResultadoHallazgosValidacionArchivoDTO();
            resultadoHallazgo.setError(ArchivoMultipleCampoConstants.NOMBRE_ARCHIVO_NO_VALIDO);
            resultadoValidacion.getResultadoAllazgos().add(resultadoHallazgo);
            return resultadoValidacion;
        }
        
        ArrayList<String[]> lineas;

        try {
            lineas = TextReaderUtil.fileToListString(archivo.getDataFile(), ",");
            for (String[] linea : lineas) {
                if (linea.length < 4) {
                    ResultadoHallazgosValidacionArchivoDTO resultadoHallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                    resultadoHallazgo.setError("La cantidad de columnas en una o varias filas no es la correcta.");
                    resultadoValidacion.getResultadoAllazgos().add(resultadoHallazgo);
                    return resultadoValidacion;
                }
            }

            // Metodo para beneficiario
            if (archivo.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_BLOQUEO_CM)) {
                resultadoValidacion.setResultadoAllazgos(validarRegistrosArchivoBloqueo(lineas));
                if (resultadoValidacion.getResultadoAllazgos() == null || resultadoValidacion.getResultadoAllazgos().isEmpty()) {
                    resultadoValidacion.setIdCargueBloqueoCuotaMonetaria(consultasCore.persistirBloqueoBeneficiario(lineas, resultadoValidacion, cargue));
                }
            }

            // Metodo para trabajador - beneficiario
            if (archivo.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_BLOQUEO_TRABAJADOR_CM)) {
                validarRegistrosTrabajadorBeneficiarioArchivoBloqueo(lineas,resultadoValidacion);
                if (resultadoValidacion.getResultadoAllazgos() == null || resultadoValidacion.getResultadoAllazgos().isEmpty()) {
                    resultadoValidacion.setIdCargueBloqueoCuotaMonetaria(consultasCore.persistirBloqueoTrabajadorBeneficiario(lineas, resultadoValidacion, cargue));
                }
            }
        
            ResultadoValidacionArchivoBloqueoCMDTO resultadoActualizacion = consultasCore.validarExistenciaBeneficiarios(resultadoValidacion.getIdCargueBloqueoCuotaMonetaria(), userDTO);

            if (resultadoActualizacion != null) {
                logger.info("ENTRA AQUI");
                logger.info(resultadoActualizacion.getNumeroRegistrosExitosos());
                logger.info(lineas.size());
                resultadoValidacion.setNumeroRegistrosExitosos(resultadoActualizacion.getNumeroRegistrosExitosos());
                resultadoValidacion.setNumeroRegistros(lineas.size());
                if (resultadoActualizacion.getLineasError() != null) {
                    resultadoValidacion.setNumeroRegistrosError(resultadoActualizacion.getLineasError().size());
                    resultadoValidacion.setLineasError(resultadoActualizacion.getLineasError());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoValidacion;
    }
    
    private List<ResultadoHallazgosValidacionArchivoDTO> validarRegistrosArchivoBloqueo(ArrayList<String[]> lineas){
        List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones = new ArrayList<>();
        
        try {
            
            long lineaArchivo = 1L;
            for (String[] linea : lineas) {
                validarTipoIdentificacion(linea[0], lineaArchivo, resultadoValidaciones);
                validarNumeroIdentificacion(linea[1], lineaArchivo, resultadoValidaciones);
                validarNombreBeneficiario(linea[2], lineaArchivo, resultadoValidaciones);
                validarCausalBloqueo(linea[3], lineaArchivo, resultadoValidaciones); 
    
                lineaArchivo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoValidaciones;
    }

    private void validarRegistrosTrabajadorBeneficiarioArchivoBloqueo(ArrayList<String[]> lineas,ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion){
        List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones = new ArrayList<>();
        
        try {
            
            long lineaArchivo = 1L;
            for (String[] linea : lineas) {
                validarTipoIdentificacion(linea[0], lineaArchivo, resultadoValidacion,linea);
                validarNumeroIdentificacion(linea[1], lineaArchivo, resultadoValidacion,linea);
                validarNombreBeneficiario(linea[2], lineaArchivo, resultadoValidacion,linea);
                validarTipoIdentificacion(linea[3], lineaArchivo, resultadoValidacion,linea);
                validarNumeroIdentificacion(linea[4], lineaArchivo, resultadoValidacion,linea);
                validarNombreBeneficiario(linea[5], lineaArchivo, resultadoValidacion,linea);
                validarCausalBloqueo(linea[6], lineaArchivo, resultadoValidacion,linea); 
                validarExistenciaPersona(linea[0], linea[1], resultadoValidacion, lineaArchivo,linea);
                validarExistenciaPersona(linea[3], linea[4], resultadoValidacion, lineaArchivo,linea);
                validarExistenciaRelacionAfiliadoBeneficiario(linea[0], linea[1], linea[3], linea[4], resultadoValidacion, lineaArchivo,linea);
                lineaArchivo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void validarTipoIdentificacion(String valor, long lineaArchivo, List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones) {
        if(valor.length() != 2 || !valor.matches(ExpresionesRegularesConstants.REGEX_TIPO_DOC_BENEFICIARIO)) {
            resultadoValidaciones.add(new ResultadoHallazgosValidacionArchivoDTO(TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_CUMPLEN.name(),CampoArchivoBloquedoCMEnum.TIPO_IDENTIFICACION_BENEFICIARIO.name(), lineaArchivo));
        }
    }
    
    private void validarNumeroIdentificacion(String valor, long lineaArchivo, List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones) {
        if(valor.length() == 0 || valor.length() >= 18 || !valor.matches(ExpresionesRegularesConstants.REGEX_NUMERO_DOC_BENEFICIARIO)) {
            resultadoValidaciones.add(new ResultadoHallazgosValidacionArchivoDTO(TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_CUMPLEN.name(),CampoArchivoBloquedoCMEnum.NUMERO_IDENTIFICACION_BENEFICIARIO.name(), lineaArchivo));
        }
    }
    
    private void validarNombreBeneficiario(String valor, long lineaArchivo, List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones) {
        if(valor.length() < 1 || valor.length() > 50) {
            resultadoValidaciones.add(new ResultadoHallazgosValidacionArchivoDTO(TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_CUMPLEN.name(),CampoArchivoBloquedoCMEnum.NOMBRE_BENEFICIARIO.name(), lineaArchivo));
        }
    }
    
    private void validarCausalBloqueo(String valor, long lineaArchivo, List<ResultadoHallazgosValidacionArchivoDTO> resultadoValidaciones) {
        if(!valor.matches(ExpresionesRegularesConstants.REGEX_CAUSAL_BLOQUEO)) {
            resultadoValidaciones.add(new ResultadoHallazgosValidacionArchivoDTO(TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_CUMPLEN.name(),CampoArchivoBloquedoCMEnum.CAUSAL_BLOQUEO.name(), lineaArchivo));
        }

    }
    
    private void validarTipoIdentificacion(String valor, long lineaArchivo, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, String[] lineas) {
        if(valor.length() != 2 || !valor.matches(ExpresionesRegularesConstants.REGEX_TIPO_DOC_BENEFICIARIO)) {
            String[] lineaError = new String[8];
            lineaError[0] = (String) lineas[0];
            lineaError[1] = (String) lineas[1];
            lineaError[2] = (String) lineas[2];
            lineaError[3] = (String) lineas[3];
            lineaError[4] = (String) lineas[4];
            lineaError[5] = (String) lineas[5];
            lineaError[6] = (String) lineas[6];
            lineaError[7] = CampoArchivoBloquedoCMEnum.TIPO_IDENTIFICACION_BENEFICIARIO.name();
            resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
            resultadoValidacion.getLineasError().add(lineaError);
        }
    }
    
    private void validarNumeroIdentificacion(String valor, long lineaArchivo, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, String[] lineas) {
        if(valor.length() == 0 || valor.length() >= 18 || !valor.matches(ExpresionesRegularesConstants.REGEX_NUMERO_DOC_BENEFICIARIO)) {
            String[] lineaError = new String[8];
            lineaError[0] = (String) lineas[0];
            lineaError[1] = (String) lineas[1];
            lineaError[2] = (String) lineas[2];
            lineaError[3] = (String) lineas[3];
            lineaError[4] = (String) lineas[4];
            lineaError[5] = (String) lineas[5];
            lineaError[6] = (String) lineas[6];
            lineaError[7] = CampoArchivoBloquedoCMEnum.NUMERO_IDENTIFICACION_BENEFICIARIO.name();
            resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
            resultadoValidacion.getLineasError().add(lineaError);
        }
    }
    
    private void validarNombreBeneficiario(String valor, long lineaArchivo, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, String[] lineas) {
        if(valor.length() < 1 || valor.length() > 50) {
            String[] lineaError = new String[8];
            lineaError[0] = (String) lineas[0];
            lineaError[1] = (String) lineas[1];
            lineaError[2] = (String) lineas[2];
            lineaError[3] = (String) lineas[3];
            lineaError[4] = (String) lineas[4];
            lineaError[5] = (String) lineas[5];
            lineaError[6] = (String) lineas[6];
            lineaError[7] = CampoArchivoBloquedoCMEnum.NOMBRE_BENEFICIARIO.name();
            resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
            resultadoValidacion.getLineasError().add(lineaError);
        }
    }
    
    private void validarCausalBloqueo(String valor, long lineaArchivo, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, String[] lineas) {
        if(!valor.matches(ExpresionesRegularesConstants.REGEX_CAUSAL_BLOQUEO)) {
            String[] lineaError = new String[8];
            lineaError[0] = (String) lineas[0];
            lineaError[1] = (String) lineas[1];
            lineaError[2] = (String) lineas[2];
            lineaError[3] = (String) lineas[3];
            lineaError[4] = (String) lineas[4];
            lineaError[5] = (String) lineas[5];
            lineaError[6] = (String) lineas[6];
            lineaError[7] = CampoArchivoBloquedoCMEnum.CAUSAL_BLOQUEO.name();
            resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
            resultadoValidacion.getLineasError().add(lineaError);
        }

    }
    
    private void validarExistenciaPersona(String tipoIdentificacion, String numeroIdentificacion, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, long lineaArchivo, String[] lineas) {
        try{
            BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null, null, null, numeroIdentificacion,
            TipoIdentificacionEnum.valueOf(TipoDocumentoBloqueoBeneficiarioEnum.valueOf(tipoIdentificacion).getValorCampo()), null);
            buscarPersonas.execute();
            if (buscarPersonas.getResult() == null) {
                String[] lineaError = new String[8];
                lineaError[0] = (String) lineas[0];
                lineaError[1] = (String) lineas[1];
                lineaError[2] = (String) lineas[2];
                lineaError[3] = (String) lineas[3];
                lineaError[4] = (String) lineas[4];
                lineaError[5] = (String) lineas[5];
                lineaError[6] = (String) lineas[6];
                lineaError[7] = TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_EXISTE_GENESYS.getDescripcion();
                resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
                resultadoValidacion.getLineasError().add(lineaError);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
  

	  }

      private void validarExistenciaRelacionAfiliadoBeneficiario(String tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion , long lineaArchivo, String[] lineas) {
        
        if(!consultasCore.validarExistenciaRelacionAfiliadoBeneficiario(tipoIdentificacionAfiliado, numeroIdentificacionAfiliado, tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario)){
            String[] lineaError = new String[8];
            lineaError[0] = (String) lineas[0];
            lineaError[1] = (String) lineas[1];
            lineaError[2] = (String) lineas[2];
            lineaError[3] = (String) lineas[3];
            lineaError[4] = (String) lineas[4];
            lineaError[5] = (String) lineas[5];
            lineaError[6] = (String) lineas[6];
            lineaError[7] = TipoInconsistenciaArchivoBloqueoCMEnum.ERROR_REGISTROS_NO_EXISTE_RELACION_AFILIADO_BENEFICIARIO.getDescripcion();
            resultadoValidacion.setNumeroRegistrosError(resultadoValidacion.getNumeroRegistrosError()+1);
            resultadoValidacion.getLineasError().add(lineaError);
        }     
      }   
    /**
     * 
     * @param lineas
     * @return
     */
    public ResultadoValidacionArchivoBloqueoCMDTO cantidadRegistrosExitosos(ArrayList<String[]> lineas){
        
        List<Boolean> listBeneficiario = new ArrayList<>();
        
        ResultadoValidacionArchivoBloqueoCMDTO validacionBeneficiario = new ResultadoValidacionArchivoBloqueoCMDTO();
        List<String[]> benInconsistentes = new ArrayList<>();
        String causaInconsistencia = "No se encuentra como Beneficiario en el sistema";
        int numeroRegistros = 0;
        int tamañoLinea = 0;
        
        for (String[] linea:lineas) {
            //se cuenta el numero de registros
            numeroRegistros++;
          //Se consulta el beneficiario
          Boolean existeBeneficiario;
          existeBeneficiario = consultasCore.consultarBeneficiariosExistentes(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(linea[0]), linea[1]);
          if (existeBeneficiario.equals(Boolean.TRUE)) {
              //Se agrega a la lista de exitosos
              listBeneficiario.add(existeBeneficiario);
        
          } else {
              //Se agrega la validacion a la linea de inconsistente
              tamañoLinea = 5;
              String[] lineaError = new String[tamañoLinea + 1];
              lineaError[0] = linea[0];
              lineaError[1] = linea[1];
              lineaError[2] = linea[2];
              lineaError[3] = linea[3];
              lineaError[4] = linea[4];
              lineaError[5] = causaInconsistencia;
              benInconsistentes.add(lineaError);
          }
      }
        
        
//        for (String[] linea:lineas) {
//            //Se consulta el beneficiario
//            List<Object[]> beneficiario = new ArrayList<>();
//            beneficiario = null;
//            beneficiario = consultasCore.consultarBeneficiariosExistentes(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(linea[0]), linea[1]);
//            if (beneficiario != null) {
//                //Se agrega a la lista de exitosos
//                listBeneficiario.add(beneficiario.get(0));
//
//            } else {
//                //Se agrega la validacion a la linea de inconsistente
//                tamañoLinea = linea.length;
//                linea[tamañoLinea] = causaInconsistencia;
//                benInconsistentes.add(linea);
//            }
//        }
        
        //Constrir arreglo de bytes de archivo
//        String caracterSeparador = ",";
//        List<String[]> encabezado = new ArrayList<>();
//        
//        if (!benInconsistentes.isEmpty() || benInconsistentes != null) {
//            byte[] data = ArchivosSubsidioUtils.generarArchivoPlano(encabezado, benInconsistentes, caracterSeparador);
//            validacionBeneficiario.setLineasError(data);
//        }
        
//          String caracterSeparador = ",";
//          List<String[]> encabezado = new ArrayList<>();
          
          if (!benInconsistentes.isEmpty() || benInconsistentes != null) {
              
              validacionBeneficiario.setLineasError(benInconsistentes);
          }
        
        validacionBeneficiario.setNumeroRegistros(numeroRegistros);
        validacionBeneficiario.setNumeroRegistrosExitosos(listBeneficiario.size());
        
        return validacionBeneficiario;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#eliminarProcesoGestionarTrabajadorBeneficiario(java.lang.String)
     */
    @Override
    public int radicarBloqueoCM(Long idCargueBloqueoCuotaMonetaria){
         String firmaMetodo = "SubsidioBusiness.radicarBloqueoCM(Long)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);         
         int registrosAfectados=consultasCore.radicarBloqueoCM(idCargueBloqueoCuotaMonetaria);
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         return registrosAfectados;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#eliminarProcesoGestionarTrabajadorBeneficiario(java.lang.String)
     */
    @Override
    public int cancelarBloqueoCM(Long idCargueBloqueoCuotaMonetaria){
         String firmaMetodo = "SubsidioBusiness.radicarBloqueoCM(Long)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);         
         int registrosAfectados=consultasCore.cancelarBloqueoCM(idCargueBloqueoCuotaMonetaria);
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         return registrosAfectados;
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BeneficiarioModeloDTO> consultarBeneficiarioSub(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, Long fechaNacimiento) {

        String firmaServicio = "SubsidioBusiness.consultarBeneficiarioPrincipal(TipoIdentificacionEnum,String,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Date fecha = null;
        if (fechaNacimiento != null){
            fecha = new Date(fechaNacimiento);
        }

        List<BeneficiarioModeloDTO> listaBeneficiarios = consultasCore.consultarBeneficiario(tipoIdentificacion, numeroIdentificacion, primerNombre, segundoNombre, primerApellido, segundoApellido, fecha);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio); 
        return listaBeneficiarios;
        
    }
    
    
     /**
         * (non-Javadoc)
         * 
         * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.String)
         */
        @SuppressWarnings("unchecked")
        @Override
        public Long radicarCargueManualBloqueoCM(CargueBloqueoCMDTO cargue,UserDTO userDTO) {

            String firmaServicio = "SubsidioBusiness.radicarCargueManualBloqueoCM(CargueBloqueoCMDTO)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            
            Long idCargueBloqueoCuotaMonetaria = consultasCore.persistirBloqueoBeneficiario(cargue);
            
            idCargueBloqueoCuotaMonetaria = consultasCore.persistirBloqueoBeneficiarioAuditoria(idCargueBloqueoCuotaMonetaria,userDTO);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio); 
            return idCargueBloqueoCuotaMonetaria; 
        }

         /**
         * (non-Javadoc)
         * 
         * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.String)
         */
        @Override
        public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueados(ConsultaBeneficiarioBloqueadosDTO consulta){

            String firmaServicio = "SubsidioBusiness.radicarCargueManualBloqueoCM(CargueBloqueoCMDTO)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            
            List<BloqueoBeneficiarioCuotaMonetariaDTO> listaConsolidada = new ArrayList<>();
            
            List<BloqueoBeneficiarioCuotaMonetariaDTO> lstBeneficiariosBloqueadosCore = consultasCore.consultarBeneficiariosBloqueadosCoreFiltros(consulta);
            List<BloqueoBeneficiarioCuotaMonetariaDTO> lstBeneficiariosBloqueadosSubsidio = consultasSubsidio.consultarBeneficiariosBloqueadosSubsidioFiltros(consulta);
            
            if (lstBeneficiariosBloqueadosCore != null) {
                for (BloqueoBeneficiarioCuotaMonetariaDTO beneficiario : lstBeneficiariosBloqueadosCore) {
                    listaConsolidada.add(beneficiario);
                }
            }
            
            /* 
            if (lstBeneficiariosBloqueadosSubsidio != null) {
                for (BloqueoBeneficiarioCuotaMonetariaDTO beneficiario : lstBeneficiariosBloqueadosSubsidio) {
                    listaConsolidada.add(beneficiario);
                }
            }
            */
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio); 
            return listaConsolidada; 
        }
        
        /**
         * (non-Javadoc)
         * 
         * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.String)
         */
        @SuppressWarnings("unchecked")
        @Override
        public int desbloquearBeneficiariosCM(List<BloqueoBeneficiarioCuotaMonetariaDTO> beneficiariosBloqueados,UserDTO userDTO){

            String firmaServicio = "SubsidioBusiness.desbloquearBeneficiariosCM(List<Long>)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            
            List<Long> idsBloqueoBeneficiarioCuotaMonetaria = new ArrayList<Long>();
            List<Long> idsBloqueoAfiliadoBeneficiarioCM = new ArrayList<Long>();
            int registrosAfectados = 0;
            int registrosDesbloqueadosCore = 0;
            int registrosDesbloqueadosSubsidio = 0;
            
            for (BloqueoBeneficiarioCuotaMonetariaDTO beneficiario : beneficiariosBloqueados) {
                if (beneficiario.getIdBloqueoBeneficiarioCuotaMonetaria() != null) {
                    if(beneficiario.getNumeroIdentificacionAfiliado() != null){
                        // Trabajador - Beneficiario
                        logger.info("ENTRA AQUI EN 1");
                        int idAfiliadoBeneficiarioCM = consultasSubsidio.consultarAfiliadoBeneficiarioCM(beneficiario.getIdBloqueoBeneficiarioCuotaMonetaria());
                        idsBloqueoBeneficiarioCuotaMonetaria.add(beneficiario.getIdBloqueoBeneficiarioCuotaMonetaria());
                        idsBloqueoAfiliadoBeneficiarioCM.add(Long.valueOf(idAfiliadoBeneficiarioCM));
                        logger.info(idAfiliadoBeneficiarioCM);
                    }else{
                        // Beneficiario 
                        logger.info("ENTRA AQUI EN 2");
                        idsBloqueoBeneficiarioCuotaMonetaria.add(beneficiario.getIdBloqueoBeneficiarioCuotaMonetaria());
                    }              
                } 
            } 
            
            // Desbloqueo solo beneficiario
            if (!idsBloqueoBeneficiarioCuotaMonetaria.isEmpty() && idsBloqueoBeneficiarioCuotaMonetaria != null) {
                registrosDesbloqueadosCore = consultasCore.desbloquearBeneficiariosCMCore(idsBloqueoBeneficiarioCuotaMonetaria,userDTO);
            }
            // Desbloqueo Trabajador - Beneficiario
            if (!idsBloqueoAfiliadoBeneficiarioCM.isEmpty() && idsBloqueoAfiliadoBeneficiarioCM != null) {
                registrosDesbloqueadosSubsidio = consultasSubsidio.desbloquearBeneficiariosCMSubsidio(idsBloqueoAfiliadoBeneficiarioCM);
            }
            
            registrosAfectados = registrosDesbloqueadosCore + registrosDesbloqueadosSubsidio;
            logger.info(registrosAfectados);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio); 
            return registrosAfectados; 
        };
        
        /**
         * (non-Javadoc)
         * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarHistoricoLiquidacionFallecimiento(java.lang.Long,
         *      java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String,
         *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
         */
        @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
        @Override
        public Response exportarHistoricoLiquidacionFallecimiento(Long periodoRegular,
                Long fechaInicio, Long fechaFin, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
            String firmaMetodo = "SubsidioMonetarioBusiness.exportarHistoricoLiquidacionFallecimiento(Long,Long,Long,TipoIdentificacionEnum,String,String)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

            List<ResultadoHistoricoLiquidacionFallecimientoDTO> result = consultasCore.consultarHistoricoLiquidacionFallecimiento(
                    periodoRegular, fechaInicio, fechaFin, tipoIdentificacion, numeroIdentificacion, numeroRadicacion, uri, response);
            
            List<String[]> dataList = new ArrayList<String[]>();
            
            String[] encabezado = {"No","Tipo de identificación","Número de identificación","Nombre del afiliado principal","Tipo de identificación del fallecido","Número de identificación del fallecido", "Nombre del fallecido","Periodo","Monto dispersado","Fecha de radicado","Fecha de registro de liquidación","Estado"};
            List<String[]> encabezadoList = new ArrayList<String[]>();
            encabezadoList.add(encabezado);
       
            
            DateFormat dateFormatPeriodo = new SimpleDateFormat("yyyy-MM");
            DateFormat dateFormatFecha = new SimpleDateFormat("dd/MM/yyyy");
            
            Integer consecutivo = 1;
            
            
            for (ResultadoHistoricoLiquidacionFallecimientoDTO resultado : result) {
                
                String[] data = {consecutivo.toString(),
                        resultado.getTipoIdentificacionAfiliado().getDescripcion(),
                        resultado.getNumeroIdentificacionAfiliado(),
                        resultado.getNombreAfiliado(),
                        resultado.getTiposIdentificacionFallecidos().get(0).getDescripcion(),
                        resultado.getNumerosIdentificacionFallecidos().get(0),
                        resultado.getNombresFallecidos().get(0),
                        dateFormatPeriodo.format(resultado.getPeriodoFallecimiento()),
                        resultado.getMontoDispersado() != null ? resultado.getMontoDispersado().toString() : "0",
                        dateFormatFecha.format(resultado.getFechaRadicacion()),
                        resultado.getFechaDispersion() != null ? dateFormatFecha.format(resultado.getFechaDispersion()) : "",
                        ResultadoProcesoEnum.CANCELADA.equals(resultado.getEstado()) ?  "Rechazada segundo nivel" : resultado.getEstado().getDescripcion()};
                dataList.add(data);
                consecutivo ++;
                
            }
            
            byte[] dataReporte = ArchivosSubsidioUtils.generarNuevoArchivoExcel(encabezadoList, dataList);
            
            Response.ResponseBuilder responseBld = null;
            responseBld = Response.ok(new ByteArrayInputStream(dataReporte));
            responseBld.header("Content-Type", "application/vnd.ms-excel" + ";charset=utf-8");
            responseBld.header("Content-Disposition", "attachment; filename=" + "HistoricoLiquidacionFallecimiento");

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return responseBld.build();
        }
        
        /**
         * (non-Javadoc)
         * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarPeriodo()
         */
        @Override
        public Boolean validarEnProcesoStaging(){ 
            String firmaMetodo = "SubsidioMonetarioBusiness.validarEnProcesoStaging()";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return consultasSubsidio.validarEnProcesoStaging();         
        }
        
        @Override
        public RespuestaVerificarPersonasSinCondicionesDTO verificarPersonasSinCondiciones(VerificarPersonasSinCondicionesDTO verificacion) {
            String firmaMetodo = "SubsidioMonetarioBusiness.verificarPersonasSinCondiciones(Long perido, List<Integer> idPersonas, List<Integer> idEmpleadores)";
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            
            RespuestaVerificarPersonasSinCondicionesDTO respuestaVerificacion;
            
            if(verificacion.getIdEmpleadores().size() != 0){
                respuestaVerificacion = consultasSubsidio.consultarPersonasEmpleadoresSinCondiciones(verificacion.getPeriodos(), verificacion.getIdEmpleadores());
            } else {
                respuestaVerificacion = consultasSubsidio.consultarPersonasAfiliadosSinCondiciones(verificacion.getPeriodos(), verificacion.getIdPersonas());
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return respuestaVerificacion;
        }

        @Override
        public VerificarPersonasSinCondicionesDTO verificarPersonasSinCondicionesAprobarResultados(String numeroRadicado) {
            
            VerificarPersonasSinCondicionesDTO listas= new VerificarPersonasSinCondicionesDTO();
   
            listas = consultasCore.consultarPersonaLiquidacionEspecifica(numeroRadicado);
            
            return listas;
        }

//        @Override
//        public void parametrizarLiquidacionSinCondiciones() {
//            // TODO Auto-generated method stub
//            
//        }     
        
        /**
         * 
         * 
         * @param numeroRadicado
         * @return
         */
    @Override
    public Boolean validarMarcaAprobacionSegNivel(String numeroRadicado) { 
        String firmaMetodo = "SubsidioMonetarioBusiness.validarMarcaAprobacionSegNivel(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo); 
        Boolean resultado = new Boolean(false);
   
        resultado = consultasSubsidio.validarMarcaAprobacionSegNivel(numeroRadicado);
        logger.debug("resultado service:" + resultado); 
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    /**
     * 
     * 
     * @param numeroRadicado
     * @return
     */
    @Override
    public void eliminarMarcaAprobacionSegNivel() { 
        String firmaMetodo = "SubsidioMonetarioBusiness.validarMarcaAprobacionSegNivel(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo); 
        Boolean resultado = new Boolean(false);
    
        consultasSubsidio.eliminarMarcaAprobacionSegNivel();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    //    @Override
    //    public Response exportarReporteNormativo(byte[] data) {
    //        // TODO Auto-generated method stub
    //        return null;
    //    }
    
    @Override
    public Response exportarRegistrosInconsistentesBloqueo(ExportarInconsistenciasDTO datosExportar) {
        logger.debug("Ingreso al metodo exportarRegistrosInconsistentesBloqueo de la clase SubsidioMonetarioBussiness");
        List<String[]> encabezado = new ArrayList<>();
        String caracterSeparador = "|";
      
        String nombreArchivo = ArchivosSubsidioUtils.generarNombreArchivoInconsistentes(datosExportar.getNombreArchivo());
        byte[] data = ArchivosSubsidioUtils.generarArchivoPlano(encabezado, datosExportar.getData(), caracterSeparador);
        
        Response.ResponseBuilder response = null;
        
        response = Response.ok(new ByteArrayInputStream(data));
        response.header("Content-Type", FormatoReporteEnum.TXT.getMimeType() + ";charset=utf-8");
        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo);
        
        return response.build();
    }
    
    @Override
    public Response exportarBeneficiariosBloqueados() {
        logger.debug("Ingreso al metodo exportarBeneficiariosBloqueados de la clase SubsidioMonetarioBussiness");
        List<Object[]> dataBenBloqueadosCore = new ArrayList<>();
        List<Object[]> dataBenBloqueadosSubsidio = new ArrayList<>();
        List<Object[]> dataBeneficiarioInformacion = new ArrayList<>();
        List<String[]> dataConsolidada = new ArrayList<>();
        List<String[]> encabezado = new ArrayList<>();
        String[] encabezadoArchivo = {"INCREMENTAL","TIPO IDENTIFICACION","NUMERO IDENTIFICACION","NOMBRE BENEFICIARIO",
                "TIPO IDENTIFICAION AFILIADO PRINCIPAL","NUMERO IDENTIFICACION AFILIADO PRINCIPAL","RELACION","ESTADO","MOTIVO DE BLOQUEO",
                "PERIODO INICIO","PERIODO FIN","FECHA BLOQUEO"};
        String caracterSeparador = "|";
        String nombreArchivo;
        byte[] data;
        //numeracion de lineas del archivo
        Integer incremental = 1;
        
        dataBenBloqueadosCore = consultasCore.consultarBeneficiarioBloqueadosCore();
        dataBenBloqueadosSubsidio = consultasSubsidio.consultarBeneficiarioBloqueadosSubsidio();        

        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        //Se agregan los registros de core al archivo
        for (Object[] benCore : dataBenBloqueadosCore) {
            //array con los 11 campos del archivo
            String[] ben = new String[12];
            //numeracion
            ben[0] = incremental.toString();
            //tipoId beneficiario
            ben[1] = benCore[0].toString();
            //numeroId beneficiario
            ben[2] = benCore[1].toString();
            //nombre beneficiario
            ben[3] = benCore[2].toString();

            // Datos unicamente cuando hay un bloqueo de nivel trabajador-beneficiario
            //if(benCore[10] != null && !benCore[10].toString().isEmpty()){                
                //tipoId afiliado
                ben[4] = "";
                //numeroId afiilado
                ben[5] = "";
                //relacion
                ben[6] = "";
            //}

            //estado
            ben[7] = benCore[3].toString();
            //Motivo
            //if (benCore[4].equals(Boolean.TRUE)) {
            //    ben[8] = "AsignacionCuotaPorOtraCCF";
            //} 
            if (benCore[6] != null && !benCore[6].toString().isEmpty()) {
                String causalBloqueo = benCore[6].toString();
                switch (causalBloqueo) {
                    case "BENEFICIARIO_AFILIADO_OTRA_CCF":
                        ben[8] = "Beneficiario como afiliado por otra CCF";
                        break;
                    case "BENEFICIARIO_DUPLICIDAD_TIPO_IDENTIFICACION":
                        ben[8] = "Beneficiario con duplicidad en tipo de identificación";
                        break;
                    case "BLOQUEO_FISCALIZACION":
                        ben[8] = "Bloqueo por fiscalización";
                        break;
                    case "INVESTIGACION_TRABAJO_SOCIAL":
                        ben[8] = "Caso Investigación Trabajo Social";
                        break;
                    case "CRUCE_GIASS":
                        ben[8] = "Cruce GIASS";
                        break;
                    case "FALLECIDO_REPORTADO_ADRES":
                        ben[8] = "Fallecido reportado en ADRES";
                        break;
                    case "SUBSIDIO_ASIGNADO_OTRA_CCF":
                        ben[8] = "Subsidio asignado por otra CCF";
                        break;
                    case "TRABAJADOR_LABORA_MENOS_96_HORAS":
                        ben[8] = "Trabajador labora menos de 96 horas";
                        break;
                    case "AFILIADO_DEPENDIENTE_VETERANO_FUERZA_PUBLICA":
                        ben[8] = "Afiliado como veterano de la fuerza publica";
                        break;
                    default:
                        ben[8] = "No disponible";
                        break;
                }
            } else {
                ben[8] = "";
            }
            
            //periodo inicio
            ben[9] = benCore[7].toString();
            ben[9] = (benCore[7] != null) ? LocalDate.parse(benCore[7].toString(), originalFormatter).format(targetFormatter) : "";

            //periodo fin
            ben[10] = benCore[8].toString();
            ben[10] = (benCore[8] != null) ? LocalDate.parse(benCore[8].toString(), originalFormatter).format(targetFormatter) : "";
            //fecha carga
            ben[11] = benCore[9].toString();
            incremental++;
            dataConsolidada.add(ben);
        }
        
        //Se agregan los registros de par afiliado beneficiario al archivo
        for (Object[] benSubsidio : dataBenBloqueadosSubsidio) {
            //array con los 11 campos del archivo
            String[] ben = new String[12];
            //numeracion
            ben[0] = incremental.toString();
            //tipoId beneficiario
            ben[1] = benSubsidio[0].toString();
            //numeroId beneficiario
            ben[2] = benSubsidio[1].toString();
            //nombre beneficiario
            ben[3] = benSubsidio[2].toString();
            //tipoId afiliado
            ben[4] = benSubsidio[3].toString();
            //numeroId afiilado
            ben[5] = benSubsidio[4].toString();
            if (benSubsidio[11] != null) {
            	//relacion
                ben[6] = benSubsidio[11].toString();
            } else {
            	ben[6] = "";
            }
            //estado
            ben[7] = benSubsidio[5].toString();
            //Motivo
            // Verificamos si tiene fraude
            if (benSubsidio[6] == null) {
                benSubsidio[6] = Boolean.FALSE;
            }
            // Asignamos el motivo priorizando el motivo de fraude
            if (benSubsidio[8] != null && !benSubsidio[8].toString().isEmpty()) {
                String causalBloqueo = benSubsidio[8].toString();
                switch (causalBloqueo) {
                    case "BENEFICIARIO_AFILIADO_OTRA_CCF":
                        ben[8] = "Beneficiario como afiliado por otra CCF";
                        break;
                    case "BENEFICIARIO_DUPLICIDAD_TIPO_IDENTIFICACION":
                        ben[8] = "Beneficiario con duplicidad en tipo de identificación";
                        break;
                    case "BLOQUEO_FISCALIZACION":
                        ben[8] = "Bloqueo por fiscalización";
                        break;
                    case "INVESTIGACION_TRABAJO_SOCIAL":
                        ben[8] = "Caso Investigación Trabajo Social";
                        break;
                    case "CRUCE_GIASS":
                        ben[8] = "Cruce GIASS";
                        break;
                    case "FALLECIDO_REPORTADO_ADRES":
                        ben[8] = "Fallecido reportado en ADRES";
                        break;
                    case "SUBSIDIO_ASIGNADO_OTRA_CCF":
                        ben[8] = "Subsidio asignado por otra CCF";
                        break;
                    case "TRABAJADOR_LABORA_MENOS_96_HORAS":
                        ben[8] = "Trabajador labora menos de 96 horas";
                        break;
                    case "AFILIADO_DEPENDIENTE_VETERANO_FUERZA_PUBLICA":
                        ben[8] = "Afiliado como veterano de la fuerza publica";
                        break;
                    default:
                        ben[8] = "No disponible";
                        break;
                }
            } else {
                // Causal de salida, por fallo de data
                ben[8] = "Sin causal";
            }

            //periodo inicio
            ben[9] = benSubsidio[9].toString();
            ben[9] = (benSubsidio[9] != null) ? LocalDate.parse(benSubsidio[9].toString(), originalFormatter).format(targetFormatter) : "";
            //periodo fin
            ben[10] = benSubsidio[10].toString();
            ben[10] = (benSubsidio[10] != null) ? LocalDate.parse(benSubsidio[10].toString(), originalFormatter).format(targetFormatter) : "";
            //fecha carga
            ben[11] = benSubsidio[12].toString();
            incremental++;
            dataConsolidada.add(ben);
        }
        
        encabezado.add(encabezadoArchivo);
        data = ArchivosSubsidioUtils.generarArchivoCSV(encabezado, dataConsolidada, caracterSeparador);

        nombreArchivo = ArchivosSubsidioUtils.generarNombreArchivoConsultaDesbloquear();
        
        Response.ResponseBuilder response = null;
        
        response = Response.ok(new ByteArrayInputStream(data));
        //response.header("Content-Type", FormatoReporteEnum.TXT + ";charset=utf-8");
        response.header("Content-Type", FormatoReporteEnum.CSV.getMimeType() + ";charset=utf-8");
        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo);
        
        return response.build();
    }

    @Override
    public Boolean consultarExistenciaBeneficiariosBloqueados(){
        logger.debug("Ingreso al metodo consultarExistenciaBeneficiariosBloqueados de la clase SubsidioMonetarioBussiness");
        String firmaServicio = "consultarExistenciaBeneficiariosBloqueados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Boolean existenBeneficiarios = Boolean.FALSE;
        
        Boolean existenCore = consultasCore.consultarExistenciaBeneficiariosBloqueadosCore();
        Boolean existenSubsidio = consultasSubsidio.consultarExistenciaBeneficiariosBloqueadosSubsidio();
        
        if (existenSubsidio) {
            existenBeneficiarios = Boolean.TRUE;
            return existenBeneficiarios;
        }
        
        if (existenCore) {
            existenBeneficiarios = Boolean.TRUE;
            return existenBeneficiarios;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio); 
        return existenBeneficiarios; 
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacion,String numeroLiquidacion) {
        logger.debug("Ingreso al metodo seleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaSubsidioFallecimientoTrabajador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        

        PersonaFallecidaTrabajadorDTO persona = consultasCore.seleccionarPersonaSubsidioFallecimientoTrabajador(tipoIdentificacion, numeroIdentificacion, tipoLiquidacion);
        
        //se retorna si esta activo en la caja
        if (persona.getActivoEnCaja() != null && persona.getActivoEnCaja()
                && TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(tipoLiquidacion))
            return persona;
        
        //Hacer la verificacion Caso 5 de HU-317-503
        if(tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE)){
            persona = consultasSubsidio.consultarBeneficiarioActivoAlFallecerAfiliado(persona);
        }
        
        // Verificar las condiciones para el beneficiario
        if(tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO) && !persona.getListaBeneficiarios().isEmpty()){
            // Condiciones beneficiario en Core
            persona = consultasCore.consultarCondicionesBeneficiarioFallecido(persona);
            // Condiciones beneficiario en subsidio (staging)
            persona = consultasSubsidio.consultarCondicionesBeneficiarioFallecido(persona);
            
            //aqui se debe setiar la fecha de fallecimiento del beneficiario
            Date fechaFallecimiento = consultasCore.consultarBeneficiarioFallecidoPorNumeroRadicado(numeroLiquidacion);
            persona.setFechaFallecido(fechaFallecimiento);
        } 

        // Verificar si los beneficiarios han tenido derecho asignado en la ultima liquidacion
        if (!persona.getListaBeneficiarios().isEmpty()) {
            persona = consultasSubsidio.consultarEstadoDerechoBeneficiarios(persona);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return persona;

    }

    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(
            String numeroRadicacion, Long identificadorCondicion) {
        logger.debug("Ingreso al metodo consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO dispersionEfectivoDTO = consultasSubsidio
                .consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(numeroRadicacion, identificadorCondicion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionEfectivoDTO;
    }
    
    @Override
    public Boolean consultarValidacionAporteMinimoFallecimiento(String numeroRadicacion) {
        logger.debug("Ingreso al metodo consultarValidacionAporteMinimoFallecimiento de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValidacionAporteMinimoFallecimiento(String)";
          logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

          Boolean resultado = consultasSubsidio
                  .consultarValidacionAporteMinimoFallecimiento(numeroRadicacion);

          logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
          return resultado;
    }
    
    @Override
    public Long consultarCondicionPersonaRadicacion(String numeroRadicacion) {
        logger.debug("Ingreso al metodo consultarCondicionPersonaRadicacion de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarValidacionAporteMinimoFallecimiento(String)";
          logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

          Long resultado = consultasSubsidio
                  .consultarCondicionPersonaRadicacion(numeroRadicacion);

          logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
          return resultado;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#confirmarBeneficiarioLiquidacionFallecimientoAporteMinimo(java.lang.String)
     */
    @Override
    public void confirmarLiquidacionFallecimientoAporteMinimo(String numeroRadicacion) {
        logger.debug("Ingreso al metodo confirmarLiquidacionFallecimientoAporteMinimo de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.confirmarBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasSubsidio.confirmarLiquidacionFallecimientoAporteMinimo(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#confirmarBeneficiarioLiquidacionFallecimientoAporteMinimo(java.lang.String)
     */
    @Override
    public ResultadoLiquidacionFallecimientoDTO consultarResultadosLiquidacionGestionAporteMinimo(String numeroRadicacion) {
        logger.debug("Ingreso al metodo consultarResultadosLiquidacionGestionAporteMinimo de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarResultadosLiquidacionGestionAporteMinimo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoLiquidacionFallecimientoDTO resultado = consultasSubsidio.consultarResultadoLiquidacionGestionAporteMinimo(numeroRadicacion);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    @Override
    public Boolean validarProcesoEnEjecucion(String numeroRadicado) {
        logger.debug("Ingreso al metodo validarProcesoEnEjecucion de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.validarProcesoEnEjecucion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean resultado = consultasSubsidio.consultarEjecucionProcesoEliminacion(numeroRadicado);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#gestionarProcesoEliminacion(java.lang.String)
     */
    @Override
    public void gestionarProcesoEliminacion(String numeroRadicado) {
        logger.debug("Ingreso al metodo gestionarProcesoEliminacion de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioBusiness.gestionarProcesoEliminacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //Crea el registro del proceso eliminación.
        consultasSubsidio.gestionProcesoEliminacion(numeroRadicado);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarDescuentosSubsidioTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> consultarDescuentosSubsidioTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion) {
        logger.debug("Ingreso al metodo consultarDescuentosSubsidioTrabajador de la clase SubsidioMonetarioBussiness");
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarDescuentosSubsidioTrabajador(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> descuentosTrabajador = consultasSubsidio
                .consultarDescuentosSubsidioTrabajador(tipoIdentificacion, numeroIdentificacion,
                        numeroRadicacion);
       
        if (descuentosTrabajador != null && !descuentosTrabajador.isEmpty()) {
        	// Se asocia el nombre de cada entidad de descuento.
        	 Map<Long, String> entidadesDescuentoMap = consultasCore.consultarEntidadesDescuento();
        	 for (ConsultaDescuentosSubsidioTrabajadorGrupoDTO descuento : descuentosTrabajador) {
				descuento.setNombreEntidadDescuento(entidadesDescuentoMap != null ? entidadesDescuentoMap.get(descuento.getIdEntidadDescuento()) : "");
			}
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return descuentosTrabajador;
    }
   //Metodo creado 15/05/2022 GLPI 57870
    @Override
    public List<EspecieLiquidacionManualDTO> consultarSubsidioEspecieLiquidacionManual(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String Periodo) {
        logger.debug("Ingreso al metodo consultarSubsidioEspecieLiquidacionManual de la clase SubsidioMonetarioBussiness");
        String firma = "consultarCategoriaBeneficiario(TipoIdentificacionEnum, String, TipoBeneficiarioEnum, Long)";
        logger.info("**__**Antes de consultas core consultarCategoriasPropiasAfiliadoBeneficiario" );
        logger.info("Finaliza servicio" + firma);
        return consultasCore.consultarSubsidioEspecieLiquidacionManualCore(tipoIdentificacionAfiliado,numeroIdentificacionAfiliado,Periodo);
    }
            //Metodo creado 02/06/2022 GLPI 57020
    @Override
    public List<CuotaMonetariaIVRDTO> consultarCuotaMonetariaCanalIVR(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado) {
        logger.debug("Ingreso al metodo consultarCuotaMonetariaCanalIVR de la clase SubsidioMonetarioBussiness");
        String firma = "consultarCuotaMonetariaCanalIVRCore(TipoIdentificacionEnum, String, TipoBeneficiarioEnum, Long)";
        logger.info("**__**Antes de consultas core consultarCuotaMonetariaCanalIVR" );
        logger.info("Finaliza servicio" + firma);
        return consultasCore.consultarCuotaMonetariaCanalIVRCore(tipoIdentificacionAfiliado,numeroIdentificacionAfiliado);
    }

    @Override
    public SolicitudLiquidacionSubsidioModeloDTO consultarLiquidacionMasivaEnProceso() {
        String firma = "consultarLiquidacionMasivaEnProceso()";
        logger.info("Finaliza servicio" + firma);
        return consultasCore.consultarLiquidacionEnProceso();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long ConsultarCantidadResgistrosSinDerecho(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.ConsultarCantidadResgistrosSinDerecho(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasSubsidio.consultarNumeroRegistrosSinDerecho(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

}
