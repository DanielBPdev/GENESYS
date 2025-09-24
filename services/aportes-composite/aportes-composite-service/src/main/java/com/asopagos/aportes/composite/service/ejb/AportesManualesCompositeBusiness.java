package com.asopagos.aportes.composite.service.ejb;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.common.IdentityConstraint.IdRefState;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliado;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.afiliados.clients.ConsultarEstadoAfiliacionRespectoTipoAfiliacion;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.aportes.clients.ActualizacionAportesRecalculados;
import com.asopagos.aportes.clients.ActualizarDiaHabilVencimientoAporte;
import com.asopagos.aportes.clients.ActualizarEstadoSolicitud;
import com.asopagos.aportes.clients.ActualizarEstadoSolicitudCorreccion;
import com.asopagos.aportes.clients.ActualizarEstadoSolicitudDevolucion;
import com.asopagos.aportes.clients.ActualizarMovimientoAporte;
import com.asopagos.aportes.clients.ActualizarSolicitudAporte;
import com.asopagos.aportes.clients.BorrarTemporalesPILA;
import com.asopagos.aportes.clients.BuscarMunicipio;
import com.asopagos.aportes.clients.CambiarEstadoRegistroDetallado;
import com.asopagos.aportes.clients.CambiarEstadoRegistroGeneral;
import com.asopagos.aportes.clients.ConsultarAportantesSinVencimiento;
import com.asopagos.aportes.clients.ConsultarAporteDetallado;
import com.asopagos.aportes.clients.ConsultarAporteGeneral;
import com.asopagos.aportes.clients.ConsultarAporteGeneralPorRegistro;
import com.asopagos.aportes.clients.ConsultarAporteTemporal;
import com.asopagos.aportes.clients.ConsultarCotizantesPorRol;
import com.asopagos.aportes.clients.ConsultarCuentaAporte;
import com.asopagos.aportes.clients.ConsultarDatosSolicitudCorreccion;
import com.asopagos.aportes.clients.ConsultarHistoricoEvaluacionAporte;
import com.asopagos.aportes.clients.ConsultarIndicePlanilla;
import com.asopagos.aportes.clients.ConsultarIndicePlanillaNumeroAportante;
import com.asopagos.aportes.clients.ConsultarNovedad;
import com.asopagos.aportes.clients.ConsultarNovedadRetiro;
import com.asopagos.aportes.clients.ConsultarNovedadesCotizanteAporte;
import com.asopagos.aportes.clients.ConsultarRecaudo;
import com.asopagos.aportes.clients.ConsultarRegistroDetallado;
import com.asopagos.aportes.clients.ConsultarRegistroDetalladoPorId;
import com.asopagos.aportes.clients.ConsultarRegistroGeneral;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralId;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralLimitado;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralLimitadoIdRegGen;
import com.asopagos.aportes.clients.ConsultarSolicitanteCorreccionCuentaAportesIds;
import com.asopagos.aportes.clients.ConsultarSolicitudAporte;
import com.asopagos.aportes.clients.ConsultarSolicitudAportePorIdAporte;
import com.asopagos.aportes.clients.ConsultarSolicitudCorreccionAporte;
import com.asopagos.aportes.clients.ConsultarSolicitudCorreccionAporteAporteGeneral;
import com.asopagos.aportes.clients.ConsultarSolicitudDevolucionAporte;
import com.asopagos.aportes.clients.ConsultarSolicitudesCorreccion;
import com.asopagos.aportes.clients.ConsultarTipoTransaccionNovedadRechazadaCotizante;
import com.asopagos.aportes.clients.CrearActualizarAporteGeneral;
import com.asopagos.aportes.clients.CrearActualizarCorreccion;
import com.asopagos.aportes.clients.CrearActualizarDevolucionAporte;
import com.asopagos.aportes.clients.CrearActualizarDevolucionAporteDetalle;
import com.asopagos.aportes.clients.CrearActualizarSolicitudCorreccionAporte;
import com.asopagos.aportes.clients.CrearActualizarSolicitudDevolucionAporte;
import com.asopagos.aportes.clients.CrearActualizarSolicitudGlobal;
import com.asopagos.aportes.clients.CrearAporteDetallado;
import com.asopagos.aportes.clients.CrearInfoFaltante;
import com.asopagos.aportes.clients.CrearRegistroDetallado;
import com.asopagos.aportes.clients.CrearRegistroGeneral;
import com.asopagos.aportes.clients.CrearSolicitudAporte;
import com.asopagos.aportes.clients.CrearTransaccion;
import com.asopagos.aportes.clients.EjecutarArmadoStaging;
import com.asopagos.aportes.clients.EjecutarBorradoStaging;
import com.asopagos.aportes.clients.EliminarRegistroGeneralPorId;
import com.asopagos.aportes.clients.EliminarRegistrosDetalladosPorRegistroGeneral;
import com.asopagos.aportes.clients.RegistrarRelacionarAportesNovedades;
import com.asopagos.aportes.clients.RegistrarRelacionarNovedades;
import com.asopagos.aportes.clients.SimularFasePila2;
import com.asopagos.aportes.clients.ValidarArchivoPagoManualAportes;
import com.asopagos.aportes.clients.ValidarArchivoPagoManualAportesPensionados;
import com.asopagos.aportes.clients.ValidarCodigoNombreSucursal;
import com.asopagos.aportes.composite.clients.FinalizarCorreccion;
import com.asopagos.aportes.composite.clients.FinalizarCorreccionAsync;
import com.asopagos.aportes.composite.dto.AporteManualDTO;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import com.asopagos.aportes.composite.dto.DatosCotizanteDTO;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import com.asopagos.aportes.composite.dto.EvaluacionAnalistaDTO;
import com.asopagos.aportes.composite.dto.EvaluacionSupervisorDTO;
import com.asopagos.aportes.composite.dto.GestionAnalistaDTO;
import com.asopagos.aportes.composite.dto.GestionInformacionFaltanteDTO;
import com.asopagos.aportes.composite.dto.InformacionPagoDTO;
import com.asopagos.aportes.composite.dto.InformacionSolicitudDTO;
import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import com.asopagos.aportes.composite.dto.RadicacionAporteManualDTO;
import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import com.asopagos.aportes.composite.service.AportesCompositeService;
import com.asopagos.aportes.composite.service.AportesManualesCompositeService;
import com.asopagos.aportes.composite.service.business.interfaces.IConsultasModeloCoreComposite;
import com.asopagos.aportes.composite.util.FuncionesUtilitarias;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosConsultaSolicitudesAporDevCorDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.dto.SolicitudCorreccionDTO;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCartera;
import com.asopagos.comunicados.clients.ConsultarComunicado;
import com.asopagos.comunicados.clients.ConsultarComunicadoPorSolicitud;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.AportanteDiaVencimientoDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.aportes.AportesDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.EvaluacionDTO;
import com.asopagos.dto.aportes.HistoricoDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.ComunicadoModeloDTO;
import com.asopagos.dto.modelo.CorreccionModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteDetalleModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.InformacionFaltanteAportanteModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.empresas.clients.ConsultarEmpresaPorId;
import com.asopagos.empresas.clients.ConsultarSucursalesEmpresa;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.MarcaRegistroAporteArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.historicos.clients.ObtenerEstadoAportantePeriodo;
import com.asopagos.historicos.clients.ObtenerEstadoTrabajador;
import com.asopagos.historicos.clients.ObtenerTrabajadoresActivosPeriodo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAportes;
import com.asopagos.personas.clients.ActualizarDatosPersona;
import com.asopagos.personas.clients.BuscarEstadoCajaPersonasMasivo;
import com.asopagos.personas.clients.BuscarPersonasSinDetalle;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.ConsultarEstadoCajaPersona;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.personas.clients.GuardarMedioDePago;
import com.asopagos.personas.dto.ConsultaEstadoCajaPersonaDTO;
import com.asopagos.pila.clients.CalcularDiaHabilVencimientoAporte;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ActualizarTrazabilidad;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import com.asopagos.solicitudes.clients.ConsultarTrazabilidad;
import com.asopagos.solicitudes.clients.ConsultarTrazabilidadPorActividad;
import com.asopagos.solicitudes.clients.CrearTrazabilidad;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.solicitudes.clients.GuardarDocumentosAdminSolicitudes;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActivaInstancia;
import com.asopagos.tareashumanas.clients.RetomarTarea;
import com.asopagos.tareashumanas.clients.SuspenderTarea;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.ObtenerTokenAcceso;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.validaciones.clients.HabilitarNovedadesAportes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.aportes.composite.clients.RegistrarAporteConDetalleAsync;

import com.asopagos.aportes.clients.ConsultarCuentaAporteConTipoRecaudo;

import com.asopagos.novedades.composite.clients.InsercionMonitoreoLogs;

/**
 * <b>Descripción: EJB que contiene la lógica de negocio para el proceso
 * 2.1.2</b>
 * 
 * @author Angélica Toro Murillo<atoro@heinsohn.com.co>
 */
@Stateless
public class AportesManualesCompositeBusiness implements AportesManualesCompositeService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(AportesManualesCompositeBusiness.class);

    /**
     * Ejb con la logica de los aportes.
     */
    @Inject
    private AportesCompositeService aportesCompositeService;

    @Inject
    private IConsultasModeloCoreComposite consultasCore;

    /**
     * Indica si la tarea fue previamente suspendida
     */
    private Boolean tareaSuspendida = Boolean.FALSE;

    /**
     * Tipo de autenticación (de acuerdo al header de la petición)(Controla la
     * cantidad de caracteres previos al token)
     */
    private static final String TIPO_AUTORIZACION = "Bearer ";

    /**
     * Nombre del header que contine el refresh token enviado desde pantalla
     */
    private final String PROFILE = "Profile";

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.service.AportesManualesCompositeService#
     * radicarSolicitud(com.asopagos.aportes.dto.RadicacionAporteManualDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public String radicarSolicitudAporte(RadicacionAporteManualDTO radicacionAporteManualDTO, UserDTO userDTO) {
        try {
            logger.info(
                    "Inicio de método radicarSolicitud(RadicacionAporteManualDTO radicacionAporteManualDTO, UserDTO userDTO)");
            /* se asocia el aporte y se guarda la solicitud de aporte */
            logger.info("**__**radicarSolicitudAporte inicio");
            SolicitudAporteModeloDTO solicitudAporteDTO = transformarSolicitudAporte(radicacionAporteManualDTO,
                    userDTO);
            solicitudAporteDTO = crearSolicitudAporte(solicitudAporteDTO);
            AporteManualDTO aporteManualDTO = new AporteManualDTO();
            aporteManualDTO.setRadicacionDTO(radicacionAporteManualDTO);
            ajustarSolicitantePorSiMismo(aporteManualDTO);
            logger.info("**__**finaliza ajustarSolicitantePorSiMismo ");
            guardarAporteManualTemporal(solicitudAporteDTO.getIdSolicitud(), aporteManualDTO);
            logger.info("**__**finaliza guardarAporteManualTemporal ");
            /* se realiza el llamado para que radique la solicitud */
            generarNumeroRadicado(solicitudAporteDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
            logger.info("**__**finaliza generarNumeroRadicado ");
            /*
             * Se consulta de nuevo la solicitud para que quede con el valor del
             * numero de radicado
             */
            solicitudAporteDTO = consultarSolicitudAporte(solicitudAporteDTO.getIdSolicitud());
            actualizarSolicitudTrazabilidad(solicitudAporteDTO.getIdSolicitud(), ProcesoEnum.PAGO_APORTES_MANUAL,
                    EstadoSolicitudAporteEnum.RADICADA, null, userDTO);
            /* Se guardan los documentos adjuntos */
            guardarDocumentos(solicitudAporteDTO.getNumeroRadicacion(),
                    aporteManualDTO.getRadicacionDTO().getDocumentos(),
                    ActividadEnum.RADICAR_SOLICITUD);
            logger.info("**__**finaliza guardarDocumentos ");

            List<PersonaDTO> personas = buscarPersonasSinDetalle(radicacionAporteManualDTO.getTipoIdentificacion(),
                    radicacionAporteManualDTO.getNumeroIdentificacion(), null, null, null, null, null);
            if (personas == null || personas.isEmpty()) {
                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(radicacionAporteManualDTO.getTipo())
                        || (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                                .equals(radicacionAporteManualDTO.getTipo())
                                && DecisionSiNoEnum.SI.equals(radicacionAporteManualDTO.getPagadorTercero()))
                        || (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(radicacionAporteManualDTO.getTipo())
                                && DecisionSiNoEnum.SI.equals(radicacionAporteManualDTO.getPagadorTercero()))) {
                    EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
                    empresaDTO.setTipoIdentificacion(radicacionAporteManualDTO.getTipoIdentificacion());
                    empresaDTO.setNumeroIdentificacion(radicacionAporteManualDTO.getNumeroIdentificacion());
                    empresaDTO.setPrimerNombre(radicacionAporteManualDTO.getPrimerNombre());
                    empresaDTO.setSegundoNombre(radicacionAporteManualDTO.getSegundoNombre());
                    empresaDTO.setPrimerApellido(radicacionAporteManualDTO.getPrimerApellido());
                    empresaDTO.setSegundoApellido(radicacionAporteManualDTO.getSegundoApellido());
                    empresaDTO.setDigitoVerificacion(radicacionAporteManualDTO.getDv());
                    empresaDTO.setRazonSocial(radicacionAporteManualDTO.getRazonSocialAportante());
                    crearEmpresa(empresaDTO);
                    logger.info("**__**finaliza crearEmpresa ");
                } else if ((TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                        .equals(radicacionAporteManualDTO.getTipo())
                        && DecisionSiNoEnum.NO.equals(radicacionAporteManualDTO.getPagadorTercero()))
                        || (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(radicacionAporteManualDTO.getTipo())
                                && DecisionSiNoEnum.NO.equals(radicacionAporteManualDTO.getPagadorTercero()))) {
                    PersonaModeloDTO persona = new PersonaModeloDTO();
                    persona.setTipoIdentificacion(radicacionAporteManualDTO.getTipoIdentificacion());
                    persona.setNumeroIdentificacion(radicacionAporteManualDTO.getNumeroIdentificacion());
                    persona.setPrimerNombre(radicacionAporteManualDTO.getPrimerNombre());
                    persona.setSegundoNombre(radicacionAporteManualDTO.getSegundoNombre());
                    persona.setPrimerApellido(radicacionAporteManualDTO.getPrimerApellido());
                    persona.setSegundoApellido(radicacionAporteManualDTO.getSegundoApellido());
                    StringBuilder nombre = new StringBuilder();
                    nombre.append(radicacionAporteManualDTO.getPrimerNombre() + " ");
                    nombre.append(
                            radicacionAporteManualDTO.getSegundoNombre() != null
                                    ? radicacionAporteManualDTO.getSegundoNombre() + " "
                                    : "");
                    nombre.append(radicacionAporteManualDTO.getPrimerApellido() + " ");
                    nombre.append(
                            radicacionAporteManualDTO.getSegundoApellido() != null
                                    ? radicacionAporteManualDTO.getSegundoApellido()
                                    : "");
                    persona.setRazonSocial(nombre.toString());
                    crearPersona(persona);
                    logger.info("**__**finaliza crearPersona ");
                }
            }

            /* se asigna la solicitud al analista */
            asignarSolicitudAnalista(solicitudAporteDTO.getIdSolicitud(), ProcesoEnum.PAGO_APORTES_MANUAL, userDTO);
            logger.info("**__**finaliza asignarSolicitudAnalista ");
            logger.info(
                    "Fin de método radicarSolicitud(RadicacionAporteManualDTO radicacionAporteManualDTO, UserDTO userDTO)");

            return "\"" + solicitudAporteDTO.getNumeroRadicacion() + "\"";
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.info("**__**finaliza Exception aportes manu " + e);
            logger.error("Ocurrio un errror en radicarSolicitudAporte", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * guardarAporteManualTemporal(java.lang.Long,
     * com.asopagos.aportes.composite.dto.AporteManualDTO)
     */
    @Override
    public void guardarAporteManualTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO) {
        try {
            logger.info("Inicio de método guardarAporteManualTemporal(Long " + idSolicitud + ", AporteManualDTO "
                    + aporteManualDTO.toString() + ")");
            TipoAfiliadoEnum tipoAfiliado = transformarTipoSolicitante(aporteManualDTO.getRadicacionDTO().getTipo());
            if (aporteManualDTO.getCotizantes() != null) {
                for (CotizanteDTO cotizante : aporteManualDTO.getCotizantesTemporales()) {
                    cotizante.setTipoAfiliado(tipoAfiliado);
                }
            }
            if (aporteManualDTO.getCotizantes() != null) {
                for (CotizanteDTO cotizante : aporteManualDTO.getCotizantes()) {
                    cotizante.setTipoAfiliado(tipoAfiliado);
                    DatosCotizanteDTO datosCotizanteDTO = new DatosCotizanteDTO();
                    datosCotizanteDTO.setTipoAfiliado(cotizante.getTipoAfiliado());
                    datosCotizanteDTO.setTipoIdentificacion(cotizante.getTipoIdentificacion());
                    datosCotizanteDTO.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
                    if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(datosCotizanteDTO.getTipoAfiliado())) {
                        datosCotizanteDTO.setNumeroIdentificacionEmpleador(
                                aporteManualDTO.getRadicacionDTO().getNumeroIdentificacion());
                        datosCotizanteDTO.setTipoIdenficacionEmpleador(
                                aporteManualDTO.getRadicacionDTO().getTipoIdentificacion());
                    }
                    datosCotizanteDTO = consultarDatosCotizante(datosCotizanteDTO,
                            aporteManualDTO.getRadicacionDTO().getTipo());
                    cotizante.setEstado(datosCotizanteDTO.getEstadoAfiliado());
                    cotizante.setFechaIngreso(datosCotizanteDTO.getFechaIngreso());
                    cotizante.setFechaRetiro(datosCotizanteDTO.getFechaRetiro());
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(aporteManualDTO);
            guardarDatosTemporales(idSolicitud, jsonPayload);
            logger.info("Fin de método guardarAporteManualTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarAporteManualTemporal(java.lang.Long)
     */
    @Override
    public AporteManualDTO consultarAporteManualTemporal(Long idSolicitud) {
        try {
            logger.info("Inicio de método consultarAporteManualTemporal(Long idSolicitud)");
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            ObjectMapper mapper = new ObjectMapper();
            AporteManualDTO aporteManualDTO = mapper.readValue(jsonPayload, AporteManualDTO.class);
            logger.info("Fin de método consultarAporteManualTemporal(Long idSolicitud)");
            return aporteManualDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAporteManualTemporal (Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * guardarDevolucionTemporal(java.lang.Long,
     * com.asopagos.aportes.composite.dto.AporteManualDTO)
     */
    @Override
    public void guardarDevolucionTemporal(Long idSolicitud, DevolucionDTO devolucionDTO) {
        try {
            logger.info(
                    "Inicio de método guardarDevolucionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
            BigDecimal totalIntereses;
            BigDecimal totalMonto;
            if (devolucionDTO != null && devolucionDTO.getAnalisis() != null)
                for (AnalisisDevolucionDTO analisisDTO : devolucionDTO.getAnalisis()) {
                    totalIntereses = new BigDecimal(0);
                    totalMonto = new BigDecimal(0);
                    if (analisisDTO.getCotizanteDTO() != null) {
                        for (CotizanteDTO cotizanteDTO : analisisDTO.getCotizanteDTO()) {
                            if (cotizanteDTO.getAportes() != null) {
                                if (cotizanteDTO.getAportes().getMoraAporte() != null
                                        && cotizanteDTO.getAportes().getMoraAporteNuevo() != null) {
                                    totalIntereses = totalIntereses.add(cotizanteDTO.getAportes().getMoraAporte()
                                            .subtract(cotizanteDTO.getAportes().getMoraAporteNuevo()));
                                }
                                if (cotizanteDTO.getAportes().getAporteObligatorio() != null
                                        && cotizanteDTO.getAportes().getAporteObligatorioNuevo() != null) {
                                    totalMonto = totalMonto.add(cotizanteDTO.getAportes().getAporteObligatorio()
                                            .subtract(cotizanteDTO.getAportes().getAporteObligatorioNuevo()));
                                }
                            }
                        }
                    }
                    analisisDTO.setInteresRegistro(totalIntereses);
                    analisisDTO.setMontoRegistro(totalMonto);
                }
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(devolucionDTO);
            guardarDatosTemporales(idSolicitud, jsonPayload);
            logger.info("Fin de método guardarDevolucionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
        } catch (Exception e) {
            logger.error("Ocurrio un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarAporteManualTemporal(java.lang.Long)
     */
    @Override
    public DevolucionDTO consultarDevolucionTemporal(Long idSolicitud) {
        InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( 
            "IdSolicitud" + idSolicitud 
            ,"AportesManualesCompositeBusiness-consultarDevolucionTemporal"
            );
			insercionMonitoreoLogs.execute();
            String jsonPayloadEncoded = "";
        try {
            logger.info("Inicio de método consultarDevolucionTemporal(Long idSolicitud)");
            logger.info("UserDTO: " + idSolicitud);
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            jsonPayloadEncoded = URLEncoder.encode(jsonPayload, StandardCharsets.UTF_8.toString());
            ObjectMapper mapper = new ObjectMapper();
            DevolucionDTO devolucionDTO = mapper.readValue(jsonPayload, DevolucionDTO.class);
            logger.info("Fin de método consultarDevolucionTemporal(Long idSolicitud)");
            return devolucionDTO;
        } catch (Exception e) {
            insercionMonitoreoLogs = new InsercionMonitoreoLogs (
                "UserDTO" + idSolicitud 
                + "jsonPayload" + jsonPayloadEncoded 
                + "error" + e
                ,"AportesManualesCompositeBusiness-consultarDevolucionTemporal"
            );
            insercionMonitoreoLogs.execute(); 
            logger.error("Ocurrio un error en consultarDevolucionTemporal(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * guardarCorreccionTemporal(java.lang.Long,
     * com.asopagos.aportes.composite.dto.AporteManualDTO)
     */
    @Override
    public void guardarCorreccionTemporal(Long idSolicitud, CorreccionDTO correccionDTO) {
        try {
            logger.debug(
                    "Inicio de método guardarCorreccionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(correccionDTO);
            guardarDatosTemporales(idSolicitud, jsonPayload);
            logger.debug("Fin de método guardarCorreccionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
        } catch (Exception e) {
            logger.error("Ocurrio un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarAporteManualTemporal(java.lang.Long)
     */
    @Override
    public CorreccionDTO consultarCorreccionTemporal(Long idSolicitud) {
        try {
            logger.info("Inicio de método consultarDevolucionTemporal(Long idSolicitud)");
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            ObjectMapper mapper = new ObjectMapper();
            CorreccionDTO correccionDTO = mapper.readValue(jsonPayload, CorreccionDTO.class);
            logger.info("Fin de método consultarDevolucionTemporal(Long idSolicitud)");
            return correccionDTO;
        } catch (Exception e) {
            logger.error("Ocurrio un errro en consultarDevolucionTemporal(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * simularAporteManual(java.lang.Long)
     */
    @Override
    public AporteManualDTO simularAporteManual(Long idSolicitud) {
        try {
            logger.info("Inicio de método simularAporteManual(Long idSolicitud)");
            AporteManualDTO aporteManualDTO = consultarAporteManualTemporal(idSolicitud);
            RegistroGeneralModeloDTO registroGeneralDTO = consultarRegistroGeneral(idSolicitud, Boolean.FALSE);
            if (registroGeneralDTO != null) {
                aporteManualDTO.setIdRegistroGeneral(registroGeneralDTO.getId());
                aporteManualDTO.setIdTransaccion(registroGeneralDTO.getTransaccion());
            }
            registroGeneralDTO = aporteManualDTO.convertToDTO();
            if (aporteManualDTO.getIdRegistroGeneral() != null) {
                registroGeneralDTO.setId(aporteManualDTO.getIdRegistroGeneral());
            }
            for (CotizanteDTO cotizante : aporteManualDTO.getCotizantesTemporales()) {
                if (registroGeneralDTO.getValTotalApoObligatorio().compareTo(BigDecimal.ZERO) > 0) {
                    cotizante.setValorMora(BigDecimal.ZERO);
                }
            }
            aporteManualDTO = actualizarMoraCotizante(aporteManualDTO);

            Long idRegistroGeneral = simularComun(idSolicitud, registroGeneralDTO,
                    aporteManualDTO.getCotizantesTemporales(), true);
            /* se consultan los resultados posteriores a la simulacion. */
            List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetallado(idRegistroGeneral);

            aporteManualDTO.setCotizantesTemporales(
                    asignarEvaluacionCotizante(aporteManualDTO.getCotizantesTemporales(), registroDetalladoList, true));
            guardarAporteManualTemporal(idSolicitud, aporteManualDTO);
            logger.info("Finaliza método simularAporteManual(Long idSolicitud)");
            return aporteManualDTO;
        } catch (Exception e) {
            logger.error("Ocurrio un error al simular el aporte Manual", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de distribuir el valor de la mora general del aporte entre
     * los cotizantes,
     * dado el caso de que se haya especificado una distribución por parte del
     * usuario
     * 
     * @param aporteManualDTO
     *                        DTO que representa al aporte manual
     * @return <b>AporteManualDTO</b>
     *         DTO de aporte manual actualizado
     */
    private AporteManualDTO actualizarMoraCotizante(AporteManualDTO aporteManualDTO) {
        String firmaMetodo = "AportesManualesCompositeBusiness.actualizarMoraCotizante(AporteManualDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AporteManualDTO aporteManualTemp = aporteManualDTO;

        List<CotizanteDTO> cotizantes = aporteManualTemp.getCotizantesTemporales();

        // si se cuenta con un valor de mora general y un listado de cotizantes no vacío
        if (aporteManualTemp.getRadicacionDTO().getMoraAporte() != null
                && aporteManualTemp.getRadicacionDTO().getMoraAporte().compareTo(BigDecimal.ZERO) > 0
                && cotizantes != null
                && !cotizantes.isEmpty()) {
            // se comprueba sí el aporte tiene distribución de mora
            if (aporteManualTemp.getRadicacionDTO().getMontoAporte().compareTo(BigDecimal.ZERO) > 0) {
                for (CotizanteDTO cotizante : cotizantes) {
                    if (cotizante.getValorMora() != null && cotizante.getValorMora().compareTo(BigDecimal.ZERO) > 0) {
                        // sí se encuentra que algún cotizante tiene valor de mora, se termina la
                        // comprobación

                        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                        return aporteManualTemp;
                    }
                }

                // sí no se tiene distribución, se asigna con base en el valor del aporte
                // detallado respecto al aporte total
                BigDecimal participacionAporte = BigDecimal.ZERO;
                participacionAporte.setScale(16, BigDecimal.ROUND_HALF_UP);

                for (CotizanteDTO cotizante : cotizantes) {
                    BigDecimal mul = aporteManualTemp.getRadicacionDTO().getMoraAporte() != null
                            ? aporteManualTemp.getRadicacionDTO().getMoraAporte()
                                    .multiply(cotizante.getAporteObligatorio())
                            : BigDecimal.ZERO;

                    if (aporteManualTemp.getRadicacionDTO().getMontoAporte() != BigDecimal.ZERO) {
                        participacionAporte = mul.divide(aporteManualTemp.getRadicacionDTO().getMontoAporte(), 5,
                                RoundingMode.HALF_EVEN);
                    } else {
                        participacionAporte = mul;
                    }

                    // se actualiza el valor de mora del cotizante con base en su participación en
                    // el aporte
                    cotizante.setValorMora(participacionAporte);
                }
            }
        }

        aporteManualTemp.setCotizantesTemporales(cotizantes);
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aporteManualTemp;
    }

    /**
     * Método encargado de distribuir el valor de la mora general del aporte entre
     * los cotizantes,
     * dado el caso de que se haya especificado una distribución por parte del
     * usuario
     * 
     * @param aporteManualDTO
     *                        DTO que representa al aporte manual
     * @return <b>AporteManualDTO</b>
     *         DTO de aporte manual actualizado
     */
    private CorreccionAportanteDTO actualizarMoraCotizante(CorreccionAportanteDTO correcciones) {
        String firmaMetodo = "AportesManualesCompositeBusiness.actualizarMoraCotizante(CorreccionAportanteDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CorreccionAportanteDTO correcionesTemp = correcciones;

        List<CotizanteDTO> cotizantes = correcionesTemp.getCotizantesNuevos();

        // si se cuenta con un valor de mora general y un listado de cotizantes no vacío
        if (correcionesTemp.getValorIntMora() != null
                && correcionesTemp.getValorIntMora().compareTo(BigDecimal.ZERO) > 0
                && cotizantes != null && !cotizantes.isEmpty()) {
            // se comprueba sí el aporte tiene distribución de mora
            for (CotizanteDTO cotizante : cotizantes) {
                if (cotizante.getValorMora() != null && cotizante.getValorMora().compareTo(BigDecimal.ZERO) > 0) {
                    // sí se encuentra que algún cotizante tiene valor de mora, se termina la
                    // comprobación

                    logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                    return correcionesTemp;
                }
            }

            // sí no se tiene distribución, se asigna con base en el valor del aporte
            // detallado respecto al aporte total
            BigDecimal participacionAporte = BigDecimal.ZERO;
            participacionAporte.setScale(16, BigDecimal.ROUND_HALF_UP);

            for (CotizanteDTO cotizante : cotizantes) {
                participacionAporte = cotizante.getAporteObligatorio().divide(
                        correcionesTemp.getValTotalApoObligatorio(), 5,
                        RoundingMode.HALF_EVEN);

                // se actualiza el valor de mora del cotizante con base en su participación en
                // el aporte
                cotizante.setValorMora(correcionesTemp.getValorIntMora() != null
                        ? correcionesTemp.getValorIntMora().multiply(participacionAporte)
                        : BigDecimal.ZERO);
            }
        }

        correcionesTemp.setCotizantesNuevos(cotizantes);
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return correcionesTemp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * simularAporteCorreccion(java.lang.Long)
     */
    @Override
    public CorreccionAportanteDTO simularAporteCorreccion(Long idSolicitud, CorreccionAportanteDTO correccion) {
        String firmaMetodo = "simularAporteCorreccion(Long, CorreccionAportanteDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        CorreccionAportanteDTO correccionLocal = correccion;

        try {
            RegistroGeneralModeloDTO registroGeneralDTO = null;
            if (correccionLocal.getIdRegistroGeneral() != null) {
                 logger.info("Registro general de correccionLocal " + correccionLocal.toString());
                logger.info("Si engresó al if: " + correccionLocal.toString());
                registroGeneralDTO = consultarRegistroGeneral(idSolicitud, Boolean.TRUE,
                        correccionLocal.getIdRegistroGeneral());
                logger.info("Registro general de registroGeneralDTO " + registroGeneralDTO.toString());
            } else {
                registroGeneralDTO = correccionLocal.convertToDTO();
                logger.info("NO engresó al if registroGeneralDTO: " + registroGeneralDTO.toString());

            }

            // se controla que el registro general siga correspondiendo con el aportante de
            // la corrección
            if (!registroGeneralDTO.getNumeroIdentificacionAportante().equals(correccionLocal.getNumeroIdentificacion())
                    || !registroGeneralDTO.getTipoIdentificacionAportante()
                            .equals(correccionLocal.getTipoIdentificacion())) {
                logger.info("Si engresó al if: " );
                eliminarRegistroGeneral(registroGeneralDTO);

                correccionLocal.setIdRegistroGeneral(null);
                registroGeneralDTO = correccionLocal.convertToDTO();
            }

            correccionLocal = actualizarMoraCotizante(correccionLocal);

            Long idRegistroGeneral = simularComun(idSolicitud, registroGeneralDTO,
                    correccionLocal.getCotizantesNuevos(), Boolean.TRUE);
            correccionLocal.setIdRegistroGeneral(idRegistroGeneral);
            // Consulta resultados posteriores a la simulación
            List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetallado(idRegistroGeneral);
            correccionLocal
                    .setCotizantesNuevos(asignarEvaluacionCotizante(correccionLocal.getCotizantesNuevos(),
                            registroDetalladoList, true));

        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return correccionLocal;
    }

    /**
     * Método encargado de solicitar la eliminación de un registro general en PILA
     * 
     * @param registroGeneralDTO
     */
    private void eliminarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO) {
        String firmaMetodo = "eliminarRegistroGeneral(RegistroGeneralModeloDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se deben eliminar los registros detallados por cada simulación y el staging
        // si aplica
        EliminarRegistrosDetalladosPorRegistroGeneral eliminarDetalles = new EliminarRegistrosDetalladosPorRegistroGeneral(
                registroGeneralDTO.getId());
        eliminarDetalles.execute();

        if (registroGeneralDTO.getTransaccion() != null) {
            ejecutarBorradoStaging(registroGeneralDTO.getTransaccion());
        }

        // se elimina el registro general
        EliminarRegistroGeneralPorId eliminarRegistroGeneral = new EliminarRegistroGeneralPorId(
                registroGeneralDTO.getId());
        eliminarRegistroGeneral.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarInformacionfaltante(java.lang.Long,
     * com.asopagos.aportes.composite.dto.GestionInformacionFaltanteDTO)
     */
    @Override
    public void finalizarInformacionfaltante(Long idSolicitud, GestionInformacionFaltanteDTO informacionFaltante,
            UserDTO userDTO) {
        try {
            logger.info(
                    "Inicio de método finalizarInformacionfaltante(Long idSolicitud, GestionInformacionFaltanteDTO informacionFaltante)");
            for (InformacionFaltanteAportanteModeloDTO informacionFaltanteDTO : informacionFaltante
                    .getInformacionFaltante()) {
                informacionFaltanteDTO.setUsuario(userDTO.getNombreUsuario());
            }
            if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(informacionFaltante.getProceso())) {
                AporteManualDTO aportes = consultarAporteManualTemporal(idSolicitud);
                aportes.setInformacionFaltante(informacionFaltante);
                guardarAporteManualTemporal(idSolicitud, aportes);
            } else if (ProcesoEnum.DEVOLUCION_APORTES.equals(informacionFaltante.getProceso())) {
                DevolucionDTO devolucion = consultarDevolucionTemporal(idSolicitud);
                devolucion.setInformacionFaltanteDTO(informacionFaltante);
                guardarDevolucionTemporal(idSolicitud, devolucion);
            }
            actualizarSolicitudTrazabilidad(idSolicitud, informacionFaltante.getProceso(),
                    EstadoSolicitudAporteEnum.EN_ANALISIS,
                    informacionFaltante.getIdComunicado(), userDTO);

            actualizarComunicadoTrazabilidad(idSolicitud, ActividadEnum.COMPLETAR_INFO_FALTANTE);
            logger.info(
                    "Fin de método finalizarInformacionfaltante(Long idSolicitud, GestionInformacionFaltanteDTO informacionFaltante)");
        } catch (Exception e) {
            logger.error("Ocurrio un errror radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * actualizarSolicitudTrazabilidad(java.lang.Long,
     * com.asopagos.enumeraciones.core.ProcesoEnum,
     * com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum, java.lang.Long,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void actualizarSolicitudTrazabilidad(Long idSolicitudGlobal, ProcesoEnum proceso,
            EstadoSolicitudAporteEnum estado,
            Long idComunicado, @Context UserDTO userDTO) {
        try {
            logger.info("Inicio de método actualizarSolicitudTrazabilidad");

            if (proceso.equals(ProcesoEnum.PAGO_APORTES_MANUAL)) {
                SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitudGlobal);
                EstadoSolicitudAporteEnum estadoActual = solicitudAporteDTO.getEstadoSolicitud();
                EstadoSolicitudAporteEnum estadoFuturo = estado;
                ActividadEnum actividad = obtenerActividadEstados(estadoActual, estadoFuturo, proceso);
                RegistroEstadoAporteModeloDTO trazabilidadDTO = new RegistroEstadoAporteModeloDTO(idSolicitudGlobal,
                        actividad,
                        estadoFuturo, (new Date()).getTime(), idComunicado, userDTO.getNombreUsuario());

                actualizarEstadoSolicitud(idSolicitudGlobal, estado, proceso);

                /* si el cambio de estado genera una actividad, se guarda la trazabilidad. */
                if (actividad != null) {
                    guardarTrazabilidad(trazabilidadDTO);
                }
            } else if (proceso.equals(ProcesoEnum.DEVOLUCION_APORTES)) {
                SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte(
                        idSolicitudGlobal);
                EstadoSolicitudAporteEnum estadoActual = solicitudDevolucionAporteDTO.getEstadoSolicitud();
                EstadoSolicitudAporteEnum estadoFuturo = estado;
                ActividadEnum actividad = obtenerActividadEstados(estadoActual, estadoFuturo, proceso);
                RegistroEstadoAporteModeloDTO trazabilidadDTO = new RegistroEstadoAporteModeloDTO(idSolicitudGlobal,
                        actividad,
                        estadoFuturo, (new Date()).getTime(), idComunicado, userDTO.getNombreUsuario());

                actualizarEstadoSolicitud(idSolicitudGlobal, estado, proceso);

                /* si el cambio de estado genera una actividad, se guarda la trazabilidad. */
                if (actividad != null) {
                    guardarTrazabilidad(trazabilidadDTO);
                }
            } else if (proceso.equals(ProcesoEnum.CORRECCION_APORTES)) {
                logger.info("*** si entra por correccion aportes");
                SolicitudCorreccionAporteModeloDTO solicitudCorreccionDTO = consultarSolicitudCorreccionAporte(
                        idSolicitudGlobal);
                logger.info("*** solicitudCorreccionDTO " + solicitudCorreccionDTO.toString());
                EstadoSolicitudAporteEnum estadoActual = solicitudCorreccionDTO.getEstadoSolicitud();
                EstadoSolicitudAporteEnum estadoFuturo = estado;
                ActividadEnum actividad = obtenerActividadEstados(estadoActual, estadoFuturo, proceso);
                RegistroEstadoAporteModeloDTO trazabilidadDTO = new RegistroEstadoAporteModeloDTO(idSolicitudGlobal,
                        actividad,
                        estadoFuturo, (new Date()).getTime(), idComunicado, userDTO.getNombreUsuario());
                actualizarEstadoSolicitud(idSolicitudGlobal, estado, proceso);
                /* si el cambio de estado genera una actividad, se guarda la trazabilidad. */
                if (actividad != null) {
                    logger.info("*** si entra a guardar la trazabilidad");
                    guardarTrazabilidad(trazabilidadDTO);
                }
            }

            logger.info("Fin de método actualizarSolicitudTrazabilidad");
        } catch (Exception e) {
            logger.error("Ocurrio un errror en el método actualizarSolicitudTrazabilidad", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * simularCorreccionTemporal(java.lang.Long)
     */
    @Override
    public List<CotizanteDTO> simularCorreccionTemporal(Long idSolicitud) {
        String firmaServicio = "AportesManualesCompositeBusiness.simularCorreccionTemporal(Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CorreccionDTO correccionDTO = consultarCorreccionTemporal(idSolicitud);
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        AnalisisDevolucionDTO analisisDevolucionDTO = correccionDTO.getAnalisis();

        // Consulta RegistroGeneral asociado al aporte. Si ya existe un RegistroGeneral
        // en simulación, se modifica ese
        if (analisisDevolucionDTO.getIdRegistroGeneralNuevo() != null) {
            logger.info("Si entra por el if de idRegistroGeneralNuevo");
            registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
        } else {
            logger.info("Entra por el else de idRegistroGeneralNuevo");
            registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneral());
            List<RegistroDetalladoModeloDTO> registrosDetallados = consultarRegistroDetallado(
                    analisisDevolucionDTO.getIdRegistroGeneral());
            logger.info("Registros general antes de crear: " + registroGeneralDTO.toString());
            logger.info("Registros detallados antes de crear: " + registrosDetallados.toString());
            registroGeneralDTO = crearRegistroGeneralDevCorr(registroGeneralDTO, registrosDetallados,
                    correccionDTO.getSolicitudCorrecionDTO().getTipoSolicitante());
        }

        // /* si la temporal no tiene nuevo registro es porque es la primera vez que se
        // simula */
        // if (correccionDTO.getAnalisis().getIdRegistroGeneralNuevo() == null) {
        // registroGeneralDTO.setId(null);
        // }

        Long idRegistroGeneral = null;
        List<CotizanteDTO> cotizantes = null;
        idRegistroGeneral = simularComun(idSolicitud, registroGeneralDTO, correccionDTO.getLstCotizantes(), false);
        correccionDTO.getAnalisis().setIdRegistroGeneralNuevo(idRegistroGeneral);
        /* se consultan los resultados posteriores a la simulacion. */
        List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetallado(idRegistroGeneral);
        correccionDTO.setLstCotizantes(
                asignarEvaluacionCotizante(correccionDTO.getLstCotizantes(), registroDetalladoList, false));
        guardarCorreccionTemporal(idSolicitud, correccionDTO);
        cotizantes = correccionDTO.getLstCotizantes();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cotizantes;
    }

    /**
     * Método encargado de radicar las novedades de un cotizante.
     * 
     * @param idRegistroGeneral
     *                                      id del registro general
     * @param idTransaccion
     *                                      id de la transacción.
     * @param cotizante
     *                                      cotizante a revisarle las novedades.
     * @param tipoIdentificacionAportante
     *                                      tipo de identificación del aportante.
     * @param numeroIdentificacionAportante
     *                                      número de identificación del aportante.
     */
    private void radicarNovedades(Long idRegistroGeneral, Long idTransaccion, CotizanteDTO cotizante,
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante) {
        logger.info("Inicio de método radicarNovedades(List<NovedadCotizanteDTO>)");
        try {
            registrarRelacionarNovedades(idTransaccion);
            List<TemNovedad> novedades = consultarInformacionTemporalNovedad(idRegistroGeneral);
            NovedadAportesDTO novedadAportes = new NovedadAportesDTO();

            RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId(idRegistroGeneral);
            Boolean reintegroEmpleadorProcesado = Boolean.FALSE;

            for (TemNovedad temNovedad : novedades) {
                String accionNovedad = temNovedad.getAccionNovedad();
                if (temNovedad.getEsIngreso()) {
                    if (MarcaNovedadEnum.valueOf(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca().toString())
                            .equals(MarcaNovedadEnum.APLICADA)) {

                        TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.valueOf(temNovedad.getTipoCotizante());
                        TipoAfiliadoEnum tipoAfiliado = tipoCotizante != null ? tipoCotizante.getTipoAfiliado()
                                : TipoAfiliadoEnum.PENSIONADO;
                        TipoIdentificacionEnum tipoIdAportante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdAportante());
                        TipoIdentificacionEnum tipoIdCotizante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdCotizante());

                        ProcesoNovedadIngresoDTO datosProcesoIng = new ProcesoNovedadIngresoDTO(tipoAfiliado,
                                tipoIdAportante,
                                temNovedad.getNumeroIdAportante(), tipoIdCotizante, temNovedad.getNumeroIdCotizante(),
                                Boolean.TRUE,
                                temNovedad.getRegistroDetallado(), idRegistroGeneral,
                                registroGeneral.getOutEsEmpleadorReintegrable(),
                                CanalRecepcionEnum.CORRECCION_APORTE, temNovedad.getFechaInicioNovedad().getTime());

                        // se procesa el reintegro del afiliado
                        Boolean afiliadoReintegrado = aportesCompositeService
                                .procesarNovedadIngresoAporte(datosProcesoIng, null);

                        // se procesa el reintegro del empleador (sí aplica)
                        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)
                                && registroGeneral.getOutEsEmpleadorReintegrable()
                                && !reintegroEmpleadorProcesado && afiliadoReintegrado) {
                            reintegroEmpleadorProcesado = procesarReintegroEmpleador(tipoIdAportante,
                                    temNovedad.getNumeroIdAportante(),
                                    Boolean.TRUE, idRegistroGeneral, CanalRecepcionEnum.CORRECCION_APORTE,
                                    FuncionesUtilitarias
                                            .obtenerFechaMillis(registroGeneral.getPeriodoAporte() + "-01"));
                        }
                        continue;
                    } else {
                        novedadAportes.setTipoNovedad(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
                        novedadAportes.setAplicar(MarcaNovedadEnum.NO_APLICADA);
                        novedadAportes.setCanalRecepcion(CanalRecepcionEnum.APORTE_MANUAL);
                    }

                } else {

                    novedadAportes.setAplicar(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca());
                    novedadAportes.setCanalRecepcion(CanalRecepcionEnum.APORTE_MANUAL);
                    novedadAportes.setTipoNovedad(temNovedad.getTipoTransaccion());
                }
                // se consultan las clasificaciones del afiliado (si lo está)
                List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(
                        cotizante.getTipoIdentificacion(),
                        cotizante.getNumeroIdentificacion());
                for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
                    if (clasificacion != null) {
                        // && clasificacion.equals(cotizante.getTipoCotizante() != null
                        // ? cotizante.getTipoCotizante().getTipoAfiliado() :
                        // cotizante.getTipoAfiliado())) {
                        novedadAportes.setClasificacionAfiliado(clasificacion);
                        break;
                    }
                }
                novedadAportes.setFechaFin(
                        temNovedad.getFechaFinNovedad() != null ? temNovedad.getFechaFinNovedad().getTime() : null);
                novedadAportes
                        .setFechaInicio(temNovedad.getFechaInicioNovedad() != null
                                ? temNovedad.getFechaInicioNovedad().getTime()
                                : null);
                novedadAportes.setIdRegistroDetallado(cotizante.getIdCotizante());
                novedadAportes.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
                novedadAportes.setNumeroIdentificacionAportante(numeroIdentificacionAportante);
                novedadAportes.setTipoIdentificacion(cotizante.getTipoIdentificacion());
                novedadAportes.setTipoIdentificacionAportante(tipoIdentificacionAportante);
                novedadAportes.setComentarios(temNovedad.getMensajeNovedad());
                radicarSolicitudNovedadAportes(novedadAportes);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando las novedades de devolucion", e);
        }

        logger.info("Fin de método radicarNovedades(List<NovedadCotizanteDTO>)");
    }

    /**
     * Método encargado de radicar las novedades de un cotizante.
     * 
     * @param idRegistroGeneral
     *                          id del registro general
     */
    private void radicarNovedadesCorreccion(Long idRegistroGeneral) {
        logger.info("Inicio de método radicarNovedades(List<NovedadCotizanteDTO>)");
        try {
            RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId(idRegistroGeneral);
            if (EstadoProcesoArchivoEnum.PROCESADO_VS_BD.equals(registroGeneral.getOutEstadoArchivo())) {
                registroGeneral.setOutEstadoArchivo(EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES);
                guardarRegistroGeneral(registroGeneral);
            }
            registrarRelacionarNovedades(registroGeneral.getTransaccion());
            List<TemNovedad> novedades = consultarInformacionTemporalNovedad(registroGeneral.getId());
            NovedadAportesDTO novedadAportes = new NovedadAportesDTO();

            Boolean reintegroEmpleadorProcesado = Boolean.FALSE;

            for (TemNovedad temNovedad : novedades) {
                String accionNovedad = temNovedad.getAccionNovedad();
                if (temNovedad.getEsIngreso()) {
                    if (MarcaNovedadEnum.valueOf(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca().toString())
                            .equals(MarcaNovedadEnum.APLICADA)) {

                        TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.valueOf(temNovedad.getTipoCotizante());
                        TipoAfiliadoEnum tipoAfiliado = tipoCotizante != null ? tipoCotizante.getTipoAfiliado()
                                : TipoAfiliadoEnum.PENSIONADO;
                        TipoIdentificacionEnum tipoIdAportante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdAportante());
                        TipoIdentificacionEnum tipoIdCotizante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdCotizante());

                        ProcesoNovedadIngresoDTO datosProcesoIng = new ProcesoNovedadIngresoDTO(tipoAfiliado,
                                tipoIdAportante,
                                temNovedad.getNumeroIdAportante(), tipoIdCotizante, temNovedad.getNumeroIdCotizante(),
                                Boolean.TRUE,
                                temNovedad.getRegistroDetallado(), idRegistroGeneral,
                                registroGeneral.getOutEsEmpleadorReintegrable(),
                                CanalRecepcionEnum.CORRECCION_APORTE, temNovedad.getFechaInicioNovedad().getTime());

                        // se procesa el reintegro del afiliado
                        Boolean afiliadoReintegrado = aportesCompositeService
                                .procesarNovedadIngresoAporte(datosProcesoIng, null);

                        // se procesa el reintegro del empleador (sí aplica)
                        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)
                                && registroGeneral.getOutEsEmpleadorReintegrable()
                                && !reintegroEmpleadorProcesado && afiliadoReintegrado) {
                            reintegroEmpleadorProcesado = procesarReintegroEmpleador(tipoIdAportante,
                                    temNovedad.getNumeroIdAportante(),
                                    Boolean.TRUE, idRegistroGeneral, CanalRecepcionEnum.CORRECCION_APORTE,
                                    FuncionesUtilitarias
                                            .obtenerFechaMillis(registroGeneral.getPeriodoAporte() + "-01"));
                        }
                        continue;
                    } else {
                        novedadAportes.setTipoNovedad(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
                        novedadAportes.setAplicar(MarcaNovedadEnum.NO_APLICADA);
                        novedadAportes.setCanalRecepcion(CanalRecepcionEnum.CORRECCION_APORTE);
                    }

                } else {

                    novedadAportes.setAplicar(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca());
                    novedadAportes.setCanalRecepcion(CanalRecepcionEnum.CORRECCION_APORTE);
                    novedadAportes.setTipoNovedad(temNovedad.getTipoTransaccion());
                }
                // se consultan las clasificaciones del afiliado (si lo está)
                List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(
                        TipoIdentificacionEnum.valueOf(temNovedad.getTipoIdCotizante()),
                        temNovedad.getNumeroIdCotizante());
                for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
                    if (clasificacion != null) {
                        // &&
                        // clasificacion.getSujetoTramite().equals(TipoAfiliadoEnum.valueOf(temNovedad.getTipoCotizante())))
                        // {
                        novedadAportes.setClasificacionAfiliado(clasificacion);
                        break;
                    }
                }
                novedadAportes.setFechaFin(
                        temNovedad.getFechaFinNovedad() != null ? temNovedad.getFechaFinNovedad().getTime() : null);
                novedadAportes
                        .setFechaInicio(temNovedad.getFechaInicioNovedad() != null
                                ? temNovedad.getFechaInicioNovedad().getTime()
                                : null);
                novedadAportes.setIdRegistroDetallado(temNovedad.getRegistroDetallado());
                novedadAportes.setNumeroIdentificacion(temNovedad.getNumeroIdCotizante());
                novedadAportes.setNumeroIdentificacionAportante(temNovedad.getNumeroIdAportante());
                novedadAportes.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(temNovedad.getTipoIdCotizante()));
                novedadAportes.setTipoIdentificacionAportante(
                        TipoIdentificacionEnum.valueOf(temNovedad.getTipoIdAportante()));
                novedadAportes.setComentarios(temNovedad.getMensajeNovedad());
                radicarSolicitudNovedadAportes(novedadAportes);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando las novedades de devolucion", e);
        }

        logger.info("Fin de método radicarNovedades(List<NovedadCotizanteDTO>)");
    }

    private Boolean procesarReintegroEmpleador(TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            Boolean tieneCotizanteReintegrable, Long idRegistroGeneral, CanalRecepcionEnum canal, Long periodo) {
        String firmaMetodo = "AportesManualesCompositeBusiness.procesarReintegroEmpleador(TipoIdentificacionEnum, String, Boolean, "
                + "Long, CanalRecepcionEnum, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tieneCotizanteReintegrable) {
            EmpleadorModeloDTO empleador = consultarEmpleadorTipoNumero(tipoIdAportante, numeroIdAportante);

            // se prepara el DTO con los datos del reintegro del empleador
            ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
            datosReintegro.setIdAportante(empleador.getIdEmpresa());
            datosReintegro.setIdRegistroGeneral(idRegistroGeneral);
            datosReintegro.setCanalReintegro(canal);
            datosReintegro.setFechaReintegro(periodo);
            datosReintegro.setTipoIdEmpleador(tipoIdAportante);
            datosReintegro.setNumIdEmpleador(numeroIdAportante);

            aportesCompositeService.procesarActivacionEmpleador(datosReintegro);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }

    /**
     * Método encargado de hacer el llamado al microservicio que consulta las
     * clasificaciones de un afiliado
     * 
     * @param tipoId
     *                 el tipo de identificacion del afiliado
     * @param numeroId
     *                 el numero de identificacion del afiliado
     * 
     * @return List<ClasificacionEnum> con las clasificaciones encontradas
     */
    private List<ClasificacionEnum> consultarClasificacionesAfiliado(TipoIdentificacionEnum tipoId, String numeroId) {
        try {
            ConsultarClasificacionesAfiliado consultarClasificacionAfiliado = new ConsultarClasificacionesAfiliado(
                    numeroId, tipoId);
            consultarClasificacionAfiliado.execute();
            return consultarClasificacionAfiliado.getResult();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Método que se encarga de radicar una solicitud de novedad que de aportes.
     * 
     * @param novedadAportesDTO
     *                          novedad a tramitar.
     */
    private void radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO) {
        logger.info("inicio radicarSolicitudNovedadAportes aportes manuales");
        RadicarSolicitudNovedadAportes radicarService = new RadicarSolicitudNovedadAportes(novedadAportesDTO);
        radicarService.execute();
        logger.info("FIN radicarSolicitudNovedadAportes aportes manuales");
    }

    /**
     * Método encargado de realizar la una simulacion
     * 
     * @param idSolicitud,
     *                            id de solicitud sobre el cual se realizara la
     *                            simulacion
     * @param cotizantes,
     *                            listado de cotizantes a utilizar mediante la
     *                            simulacion
     * @param registroGeneralDTO,
     *                            registro general DTO
     * @param aporteManual,
     *                            booleano encargado de verificar si se simulan
     *                            aportes(true) o
     *                            correcciones(false)
     * @return retorna un objecto, el cual puede ser un dto de CorreccionDTO o
     *         de AporteManualDTO
     */
    private Long simularComun(Long idSolicitud, RegistroGeneralModeloDTO registroGeneralDTO,
            List<CotizanteDTO> cotizantes,
            boolean aporteManual) {
        try {
            Long idRegistroDetallado = null;

            // sí se trata de un aporte manual, se deben eliminar los registros detallados
            // por cada simulación y el staging si aplica
            if (aporteManual && registroGeneralDTO.getId() != null) {
                logger.info("Se trata de un aporte manual, se eliminan los registros detallados y el staging si aplica");
                EliminarRegistrosDetalladosPorRegistroGeneral eliminarDetalles = new EliminarRegistrosDetalladosPorRegistroGeneral(
                        registroGeneralDTO.getId());
                eliminarDetalles.execute();

                // como parte de la eliminación de registros detallados, se borran los ID de
                // cotizante
                for (CotizanteDTO cotizanteDTO : cotizantes) {
                    cotizanteDTO.setIdCotizante(null);
                }

                if (registroGeneralDTO.getTransaccion() != null) {
                    ejecutarBorradoStaging(registroGeneralDTO.getTransaccion());
                }
            }

            /* creacion de la transacción para la simulación. */
            Long idTransaccion = crearTransaccion();
            registroGeneralDTO.setTransaccion(idTransaccion);
            registroGeneralDTO.setRegistroControlManual(idSolicitud);
            registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
            /* se guarda de la temporal a la tabla RegistroGeneral staging */
            logger.info("Se guarda el registro general en staging registroGeneralDTO: "
                    + registroGeneralDTO.toString());
            Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);

            Instant start = Instant.now();
            actualizarTransaccionCotizantesParalelo(
                cotizantes,
                idRegistroGeneral,
                aporteManual
            );
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            logger.info("Simular Id transaccion: "+idTransaccion+" idRegistroGeneral: "+idRegistroGeneral+" comun duracion actualizarTransaccionCotizantesParalelo: " + Long.toString(timeElapsed));
            /* Aplicar ejecución de Armado de tablas de Staging */
            start = Instant.now();
            ejecutarArmadoStaging(idTransaccion);
            finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toMillis();
            logger.info("Simular comun duracion ejecutarArmadoStaging: " + Long.toString(timeElapsed));
            /* se invoca la simulación */
            start = Instant.now();
            simularFasePila2(idTransaccion);
            finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toMillis();
            logger.info("Simular comun duracion simularFasePila2: " + Long.toString(timeElapsed));

            return idRegistroGeneral;
        } catch (Exception e) {
            logger.error("Ocurrio un error al momento de simular", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private void actualizarTransaccionCotizantesParalelo(
		List<CotizanteDTO> cotizantes,
		Long idRegistroGeneral,
		boolean aporteManual
		){
        logger.info("Inicio método actualizarTransaccionCotizantesParalelo");
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try {
            for (CotizanteDTO cotizante : cotizantes) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    actualizarTransaccionCotizantes(cotizante, idRegistroGeneral, aporteManual);
                }, executor).exceptionally(ex -> {
                    // Cancela todas las tareas pendientes y ejecutando debido a una excepción
                    //throw new CustomRuntimeException("Error interno por datos", ex);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, ex);
                });
                futures.add(future);
            }

            // Espera la conclusión de todas las tareas
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }  catch (Exception e) {
            // Manejo de otras excepciones inesperadas
            //throw new RuntimeException("Error ejecutando la simulación", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
            // Intenta apagar el executor de manera ordenada
            shutdownExecutor(executor);
        }
    }

    /**
	 * Actualizacion en paralelo de cotizantes
	 */
	private void actualizarTransaccionCotizantes(
		CotizanteDTO cotizanteDTO,
		Long idRegistroGeneral,
		boolean aporteManual
		) {
        /* se guarda de la temporal a la tabla RegistroDetallado staging */
        RegistroDetalladoModeloDTO registroDetalladoDTO = null;
        Long idRegistroDetallado = null;
        /*
        * si se trata de un aporte manual se setea el id de cotizante que corresponde
        * al id ya utilizado para el registro detallado
        */
        if (aporteManual) {
            registroDetalladoDTO = cotizanteDTO.convertToDTO();
            registroDetalladoDTO.setId(cotizanteDTO.getIdCotizante());
        } else {
            /*
                * si se trata de una corrección se verifica con el id del registro si tiene
                * valor es porque ya se hizo una evaluacion
                */
            registroDetalladoDTO = consultarRegistroDetalladoPorId(cotizanteDTO.getIdRegistro());
            registroDetalladoDTO = cotizanteDTO.convertToDTOCorreccion(registroDetalladoDTO);
            if (cotizanteDTO.getIdRegistroDetalladoNuevo() == null) {
                registroDetalladoDTO.setId(null);
            }
        }
        registroDetalladoDTO.setRegistroGeneral(idRegistroGeneral);
        registroDetalladoDTO.setOutRegistroActual(Boolean.TRUE);
        idRegistroDetallado = guardarRegistroDetallado(registroDetalladoDTO);
        if (aporteManual) {
            cotizanteDTO.setIdCotizante(idRegistroDetallado);
        } else {
            cotizanteDTO.setIdRegistroDetalladoNuevo(idRegistroDetallado);
        }
	}

    /**
     * Método con estructuras de control condicionales para determinar, de
     * acuerdo a un estadoActual y estadoFuturo, cuál es la actividadRealizada
     * correspondiente
     * 
     * @param estadoActual
     *                     Estado actual de la solicitud de aportes
     * @param estadoFuturo
     *                     Estado futuro de la solicitud de aportes
     * @return La actividad a registrar en trazabilidad
     */
    private ActividadEnum obtenerActividadEstados(EstadoSolicitudAporteEnum estadoActual,
            EstadoSolicitudAporteEnum estadoFuturo,
            ProcesoEnum proceso) {
        if (estadoActual == null) {
            return ActividadEnum.RADICAR_SOLICITUD;
        }
        switch (estadoActual) {
            case RADICADA:
                if (EstadoSolicitudAporteEnum.EN_ANALISIS.equals(estadoFuturo)) {
                    return ActividadEnum.ANALIZAR_SOLICITUD;
                }
                break;
            case EN_ANALISIS:
                return validarEstadoAnalisis(estadoFuturo, proceso);
            case FALTA_INFORMACION:
                if (EstadoSolicitudAporteEnum.EN_ANALISIS.equals(estadoFuturo)) {
                    return ActividadEnum.COMPLETAR_INFO_FALTANTE;
                }
                break;
            case EN_EVALUACION_SUPERVISOR:
                if (EstadoSolicitudAporteEnum.EVALUADA_POR_SUPERVISOR.equals(estadoFuturo)) {
                    return ActividadEnum.EVALUAR_SOLICITUD;
                }
                break;
            case EVALUADA_POR_SUPERVISOR:
                if (EstadoSolicitudAporteEnum.RECHAZADA.equals(estadoFuturo)) {
                    return ActividadEnum.RECHAZAR_SOLICITUD_2N_CERRAR;
                }
                if (EstadoSolicitudAporteEnum.APROBADA.equals(estadoFuturo)) {
                    if (ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)) {
                        return ActividadEnum.APROBAR_PAGO;
                    } else {
                        return ActividadEnum.APROBAR_CERRAR_SOLICITUD;
                    }
                }
                break;
            case GESTIONAR_PAGO:
                if (EstadoSolicitudAporteEnum.PAGO_PROCESADO.equals(estadoFuturo)) {
                    return ActividadEnum.GESTIONAR_PAGO_CERRAR;
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * Método encargado de validar el estado de analisis
     * 
     * @param estadoFuturo,
     *                      estado a realizar la validacion futura
     */
    private ActividadEnum validarEstadoAnalisis(EstadoSolicitudAporteEnum estadoFuturo, ProcesoEnum proceso) {
        if (EstadoSolicitudAporteEnum.FALTA_INFORMACION.equals(estadoFuturo)) {
            return ActividadEnum.GESTIONAR_INFO_FALTANTE;
        } else if (EstadoSolicitudAporteEnum.CERRADA.equals(estadoFuturo)) {
            return ActividadEnum.RECHAZAR_SOLICITUD_1N_CERRAR;
        } else if (EstadoSolicitudAporteEnum.CANCELADA.equals(estadoFuturo)) {
            return ActividadEnum.GESTIONAR_SOLICITUD;
        } else if (EstadoSolicitudAporteEnum.EN_EVALUACION_SUPERVISOR.equals(estadoFuturo)) {
            if (ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)) {
                return ActividadEnum.ESCALAR_APROBACION;
            } else {
                return ActividadEnum.ESCALAR_SOLICITUD;
            }

        } else if (EstadoSolicitudAporteEnum.NOTIFICADA.equals(estadoFuturo)) {
            return ActividadEnum.GESTIONAR_SOLICITUD;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * verificarArchivoPagoManualAportes(java.lang.String)
     */
    @Override
    public ResultadoArchivoAporteDTO verificarArchivoPagoManualAportes(String identificadorDocumento) {
        logger.info("Inicia método verificarArchivoPagoManualAportes(String identificadorDocumento)");
        InformacionArchivoDTO archivo = obtenerArchivo(identificadorDocumento);
        ResultadoArchivoAporteDTO resultadoArchivoAporte = validarArchivoPagoManualAportes(archivo);
        logger.info("Finaliza método verificarArchivoPagoManualAportes(String identificadorDocumento)");
        return resultadoArchivoAporte;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * verificarArchivoPagoManualAportesPensionados(java.lang.String)
     */
    @Override
    public ResultadoArchivoAporteDTO verificarArchivoPagoManualAportesPensionados(String identificadorDocumento) {
        logger.info("Inicia método verificarArchivoPagoManualAportesPensionados(String identificadorDocumento)");
        InformacionArchivoDTO archivo = obtenerArchivo(identificadorDocumento);
        ResultadoArchivoAporteDTO resultadoArchivoAporte = null;

        ValidarArchivoPagoManualAportesPensionados validarPensionados = new ValidarArchivoPagoManualAportesPensionados(
                archivo);
        validarPensionados.execute();
        resultadoArchivoAporte = validarPensionados.getResult();

        logger.info("Finaliza método verificarArchivoPagoManualAportesPensionados(String identificadorDocumento)");
        return resultadoArchivoAporte;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * registrar(java.lang.Long)
     */
    @Override
    public Boolean registrarAporte(Long idSolicitud, Long idTarea, UserDTO userDTO) {
        logger.info("Inicio de método registrarAporte(Long idSoliciutd)");
        Boolean resultado = Boolean.TRUE;
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        AporteManualDTO aporteManualDTO = null;

        try {
            registroGeneralDTO = consultarRegistroGeneral(idSolicitud, Boolean.FALSE);

            /* se consultan los datos temporales para simularlos */
            aporteManualDTO = consultarAporteManualTemporal(idSolicitud);

            logger.info("aporteManualDTO: " + aporteManualDTO);

            List<CotizanteDTO> cotizantesTemporales = aporteManualDTO.getCotizantesTemporales();
            List<Long> idCotizante = new ArrayList<>();

            for (CotizanteDTO cotizanteDTO : cotizantesTemporales) {
                if (EstadoValidacionRegistroAporteEnum.NO_OK.equals(cotizanteDTO.getEvaluacionSimulacion())
                        || EstadoValidacionRegistroAporteEnum.NO_VALIDADO_BD
                                .equals(cotizanteDTO.getEvaluacionSimulacion())) {
                    idCotizante.add(cotizanteDTO.getIdCotizante());
                }
            }
            if (!idCotizante.isEmpty()) {
                actualizarEstadoRegistroDetallado(idCotizante);
            }

            if (aporteManualDTO.getCotizantesTemporales().isEmpty() || !validarTotalAportes(idSolicitud)) {
                logger.info("validarTotalAportes: FALSE");
                resultado = registrarAporteSinDetalle(idSolicitud, aporteManualDTO);
                if (!resultado) {
                    actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL,
                            EstadoSolicitudAporteEnum.BLOQUEADA, null,
                            userDTO);
                    resultado = Boolean.FALSE;
                } else {
                    cambiarEstadoAporte(idSolicitud, aporteManualDTO, userDTO, idTarea);
                }
            } else {
                if (registroGeneralDTO == null) {
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                } else {
                    Boolean cumpleSucursal = verificarCumplimientoSucursal(registroGeneralDTO.getId());
                    if (!cumpleSucursal) {
                        logger.info("Fin de método registrar(Long idSoliciutd) de manera no exitosa");
                        actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL,
                                EstadoSolicitudAporteEnum.BLOQUEADA,
                                null, userDTO);
                        resultado = Boolean.FALSE;
                    }

                    if (EstadoProcesoArchivoEnum.PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD
                            .equals(registroGeneralDTO.getOutEstadoArchivo())) {
                        registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.PROCESADO_VS_BD);
                        guardarRegistroGeneral(registroGeneralDTO);
                    }
                    if (registroGeneralDTO.getTransaccion() != null) {
                        registrarRelacionarAportesNovedades(registroGeneralDTO.getTransaccion());
                        registroGeneralDTO = consultarRegistroGeneral(idSolicitud, Boolean.FALSE);
                    }
                    logger.info("***__**Aportes manuales registroGeneralDTO.getTransaccion()"
                            + registroGeneralDTO.getTransaccion());
                    /*
                     * si despues de registrarse el estado sigue estando pendiente por registro de
                     * aportes se devuevle una marca de false
                     */
                    if (resultado) {
                        Boolean pagadorTercero = (aporteManualDTO.getRadicacionDTO().getPagadorTercero() != null
                                && DecisionSiNoEnum.SI.equals(aporteManualDTO.getRadicacionDTO().getPagadorTercero()))
                                        ? Boolean.TRUE
                                        : Boolean.FALSE;
                        RegistrarAporteConDetalleAsync servicio = new RegistrarAporteConDetalleAsync(aporteManualDTO.getRadicacionDTO().getOrigenAporte(),
                                aporteManualDTO.getRadicacionDTO().getCajaAporte(), idSolicitud,
                                aporteManualDTO.getRadicacionDTO().getTipo(), pagadorTercero,
                                registroGeneralDTO.getId(),
                                null, null);
                        servicio.execute();
                        /* se actualiza la solicitud con el id del aporte general */
                        SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitud);
                        solicitudAporteDTO.setIdRegistroGeneral(registroGeneralDTO.getId());
                        actualizarSolicitudAporte(solicitudAporteDTO);
                        cambiarEstadoAporte(idSolicitud, aporteManualDTO, userDTO, idTarea);
                        logger.info("Fin de método registrar(Long idSoliciutd) de manera exitosa");
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Ocurrio un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
            /*
             * Se valida que sea diferente de null para borrar los datos de las tablas
             * temporales
             */
            if (registroGeneralDTO != null) {
                borrarTemporalesPILA(registroGeneralDTO.getId());

                if (registroGeneralDTO.getTransaccion() != null) {
                    ejecutarBorradoStaging(registroGeneralDTO.getTransaccion());
                }
            }

            try { // Actualiza la cartera del aportante -> HU-169
                if (resultado && aporteManualDTO != null && aporteManualDTO.getRadicacionDTO() != null) {
                    RadicacionAporteManualDTO radicacion = aporteManualDTO.getRadicacionDTO();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                    String periodoPago = dateFormat.format(new Date(radicacion.getPeriodoPago()));
                    actualizarDeudaPresuntaCartera(radicacion.getTipoIdentificacion(),
                            radicacion.getNumeroIdentificacion(), periodoPago,
                            radicacion.getTipo());
                }
            } catch (Exception e) {
                logger.info("Ocurrió un error actualizando la deuda presunta", e);
            }
        }

        return resultado;
    }

    /**
     * Método que invoca el servicio que actualiza la deuda presunta de un aportante
     * 
     * @param tipoIdentificacion
     *                             Tipo de identificación del aportante
     * @param numeroIdentificacion
     *                             Número de identificación del aportante
     * @param periodoEvaluacion
     *                             Periodo de evaluación. Formato YYYY-MM
     * @param tipoAportante
     *                             Tipo de aportante
     */
    private void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        String firmaMetodo = "AportesManuales.CompositeBusiness.actualizarDeudaPresuntaCartera(TipoIdentificacionEnum, String, String, "
                + "TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " :: tipoIdentificacion = "
                + tipoIdentificacion.getValorEnPILA()
                + ", numeroIdentificacion = " + numeroIdentificacion + ", periodoEvaluacion = " + periodoEvaluacion
                + ", "
                + "tipoAportante = " + tipoAportante.name());

        ActualizarDeudaPresuntaCartera service = new ActualizarDeudaPresuntaCartera(tipoAportante, numeroIdentificacion,
                periodoEvaluacion, tipoIdentificacion);
        service.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * radicarSolicitudDevolucion(com.asopagos.aportes.composite.dto.
     * SolicitudDevolucionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucionDTO,
            UserDTO userDTO) {
        try {
            logger.info("Inicio de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");

            // Se crea la solicitud de devolución
            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = transformarSolicitudDevolucionAporte(
                    solicitudDevolucionDTO,
                    userDTO);
            Long idSolicitudGlobal = crearActualizarSolicitudDevolucionAporte(solicitudDevolucionAporteDTO);

            // Se almacena en la tabla temporal
            DevolucionDTO devolucionDTO = new DevolucionDTO();
            devolucionDTO.setSolicitud(solicitudDevolucionDTO);
            guardarDevolucionTemporal(idSolicitudGlobal, devolucionDTO);

            /* se realiza el llamado para que radique la solicitud */
            generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());
            /*
             * Se consulta de nuevo la solicitud para que quede con el valor del numero de
             * radicado
             */
            solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte(idSolicitudGlobal);
            actualizarSolicitudTrazabilidad(solicitudDevolucionAporteDTO.getIdSolicitud(),
                    ProcesoEnum.DEVOLUCION_APORTES,
                    EstadoSolicitudAporteEnum.RADICADA, null, userDTO);

            /* se asigna la solicitud al analista */
            asignarSolicitudAnalista(idSolicitudGlobal, ProcesoEnum.DEVOLUCION_APORTES, userDTO);

            logger.info("Fin de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");
            Map<String, String> mapa = new HashMap<>();
            mapa.put("numeroRadicacion", solicitudDevolucionAporteDTO.getNumeroRadicacion());
            mapa.put("idSolicitud", solicitudDevolucionAporteDTO.getIdSolicitud().toString().toString());
            return mapa;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando una solicitud de devolución", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarEvaluacionAnalista(com.asopagos.aportes.composite.dto
     * .EvaluacionAnalistaDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void finalizarEvaluacionAnalista(EvaluacionAnalistaDTO evaluacionAnalistaDTO, UserDTO userDTO) {
        try {
            logger.info("Inicio de método finalizarEvaluacionAnalista");
            Map<String, Object> params = new HashMap<>();
            if (ProcesoEnum.DEVOLUCION_APORTES.equals(evaluacionAnalistaDTO.getProceso())) {
                SolicitudDevolucionAporteModeloDTO solicitudDevolucionDTO = consultarSolicitudDevolucionAporte(
                        evaluacionAnalistaDTO.getIdSolicitud());
                solicitudDevolucionDTO.setResultadoAnalista(evaluacionAnalistaDTO.getEvaluacionSolicitud());
                solicitudDevolucionDTO.setObservacionAnalista(evaluacionAnalistaDTO.getObservacion());
                crearActualizarSolicitudDevolucionAporte(solicitudDevolucionDTO);
            }
            if (evaluacionAnalistaDTO.getEvaluacionSolicitud().equals(ResultadoProcesoEnum.APROBADA)) {
                actualizarSolicitudTrazabilidad(evaluacionAnalistaDTO.getIdSolicitud(),
                        evaluacionAnalistaDTO.getProceso(),
                        EstadoSolicitudAporteEnum.EN_EVALUACION_SUPERVISOR, null, userDTO);
                params.put("resultadoAnalisis", 1);
                params.put("usuarioSupervisor", evaluacionAnalistaDTO.getUsuarioSupervisor());
                this.terminarTarea(evaluacionAnalistaDTO.getIdTarea(), params);
            } else {
                if (ProcesoEnum.CORRECCION_APORTES.equals(evaluacionAnalistaDTO.getProceso())) {
                    actualizarSolicitudTrazabilidad(evaluacionAnalistaDTO.getIdSolicitud(),
                            evaluacionAnalistaDTO.getProceso(),
                            EstadoSolicitudAporteEnum.RECHAZADA, null, userDTO);
                } else {
                    actualizarSolicitudTrazabilidad(evaluacionAnalistaDTO.getIdSolicitud(),
                            evaluacionAnalistaDTO.getProceso(),
                            EstadoSolicitudAporteEnum.RECHAZADA, null, userDTO);
                }
                actualizarSolicitudTrazabilidad(evaluacionAnalistaDTO.getIdSolicitud(),
                        evaluacionAnalistaDTO.getProceso(),
                        EstadoSolicitudAporteEnum.CERRADA, null, userDTO);
                params.put("resultadoAnalisis", 2);
                terminarTarea(evaluacionAnalistaDTO.getIdTarea(), params);
            }
            logger.info("Finaliza método finalizarEvaluacionAnalista");
        } catch (Exception e) {
            logger.info("Finaliza con error el método finalizarEvaluacionAnalista");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarEvaluacionSolicitudCorrecion()
     */
    @Override
    public void finalizarEvaluacionSupervisor(EvaluacionSupervisorDTO evaluacionSupervisorDTO, UserDTO userDTO) {
        try {
            logger.info(
                    "Inicio de método finalizarEvaluacionSupervisor(EvaluacionSupervisorDTO evaluacionSupervisorDTO, UserDTO userDTO)");
            String destinatario = null;
            if (ProcesoEnum.DEVOLUCION_APORTES.equals(evaluacionSupervisorDTO.getProceso())) {
                SolicitudDevolucionAporteModeloDTO solicitudDevolucionModeloDTO = consultarSolicitudDevolucionAporte(
                        evaluacionSupervisorDTO.getIdSolicitud());
                solicitudDevolucionModeloDTO.setResultadoSupervisor(evaluacionSupervisorDTO.getEvaluacionSolicitud());
                solicitudDevolucionModeloDTO
                        .setObservacionSupervisor(evaluacionSupervisorDTO.getObservacionesSupervisor());
                crearActualizarSolicitudDevolucionAporte(solicitudDevolucionModeloDTO);
                destinatario = solicitudDevolucionModeloDTO.getDestinatario();
            } else if (ProcesoEnum.CORRECCION_APORTES.equals(evaluacionSupervisorDTO.getProceso())) {
                SolicitudCorreccionAporteModeloDTO solicitudCorreccionDTO = consultarSolicitudCorreccionAporte(
                        evaluacionSupervisorDTO.getIdSolicitud());
                solicitudCorreccionDTO.setResultadoSupervisor(evaluacionSupervisorDTO.getEvaluacionSolicitud());
                solicitudCorreccionDTO.setObservacionSupervisor(evaluacionSupervisorDTO.getObservacionesSupervisor());
                crearActualizarSolicitudCorreccionAporte(solicitudCorreccionDTO);
                destinatario = solicitudCorreccionDTO.getDestinatario();
            }

            // Se actualiza el estado de la solicitud realizando trazabilidad
            actualizarSolicitudTrazabilidad(evaluacionSupervisorDTO.getIdSolicitud(),
                    evaluacionSupervisorDTO.getProceso(),
                    EstadoSolicitudAporteEnum.EVALUADA_POR_SUPERVISOR, null, userDTO);
            Map<String, Object> params = new HashMap<>();
            params.put("usuarioAnalista", destinatario);
            this.terminarTarea(evaluacionSupervisorDTO.getIdTarea(), params);
            logger.info(
                    "Finaliza método finalizarEvaluacionSupervisor(EvaluacionSupervisorDTO evaluacionSupervisorDTO, UserDTO userDTO)");
        } catch (Exception e) {
            logger.error(
                    "Finaliza con Error método finalizarEvaluacionSupervisor(EvaluacionSupervisorDTO evaluacionSupervisorDTO, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarInformacionFaltante(java.lang.Long)
     */
    @Override
    public GestionInformacionFaltanteDTO consultarInformacionFaltante(Long idSolicitud, ProcesoEnum proceso) {
        try {
            logger.info("Inicio de método consultarInformacionFaltante(Long idSolicitud)");
            GestionInformacionFaltanteDTO gestionInfoFaltante = null;
            if (ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)) {
                DevolucionDTO devolucion = consultarDevolucionTemporal(idSolicitud);
                gestionInfoFaltante = devolucion.getInformacionFaltanteDTO();
            } else if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(proceso)) {
                AporteManualDTO aportes = consultarAporteManualTemporal(idSolicitud);
                gestionInfoFaltante = aportes.getInformacionFaltante();
            }
            logger.info("Fin de método consultarInformacionFaltante(Long idSolicitud)");
            return gestionInfoFaltante;
        } catch (Exception e) {
            logger.error("Finaliza con Error método consultarInformacionFaltante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * radicarSolicitudCorreccion(com.asopagos.aportes.composite.dto.
     * SolicitudCorreccionDTO)
     */
    @Override
    public Map<String, String> radicarSolicitudCorreccion(SolicitudCorreccionDTO solicitudCorreccionDTO,
            UserDTO userDTO) {
        try {
            logger.info("Inicio de método radicarSolicitudCorreccion(SolicitudCorreccionDTO solicitudDevolucion)");
            SolicitudCorreccionAporteModeloDTO solicitudCorreccion = transformarSolicitudCorreccion(
                    solicitudCorreccionDTO, userDTO);
            Long idSolicitud = crearActualizarSolicitudCorreccionAporte(solicitudCorreccion);
            solicitudCorreccion.setIdSolicitud(idSolicitud);
            /* se asocia la solicitud y se guarda la solicitud de devolución */
            CorreccionDTO correccionDTO = new CorreccionDTO();
            correccionDTO.setSolicitudCorrecionDTO(solicitudCorreccionDTO);
            guardarCorreccionTemporal(idSolicitud, correccionDTO);
            /* se realiza el llamado para que radique la solicitud */
            String numeroRadicado = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());
            /*
             * Se consulta de nuevo la solicitud para que quede con el valor del numero de
             * radicado
             */
            solicitudCorreccion = consultarSolicitudCorreccionAporte(idSolicitud);
            actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                    EstadoSolicitudAporteEnum.RADICADA, null, userDTO);
            /* se asigna la solicitud al analista */
            Long idInstancia = asignarSolicitudAnalista(idSolicitud, ProcesoEnum.CORRECCION_APORTES, userDTO);
            logger.info("Finaliza de método radicarSolicitudCorrecion(SolicitudCorreccionDTO solicitudDevolucion)");
            Map<String, String> mapa = new HashMap<>();
            mapa.put("numeroRadicacion", numeroRadicado);
            mapa.put("idSolicitud", idSolicitud.toString());
            mapa.put("idInstancia", idInstancia.toString());
            return mapa;
        } catch (Exception e) {
            logger.error("Ocurrio un error radicando una solicitud correccion ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarDatosCotizante(com.asopagos.aportes.composite.dto.
     * DatosCotizanteDTO,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public DatosCotizanteDTO consultarDatosCotizante(DatosCotizanteDTO datosCotizante,
            TipoSolicitanteMovimientoAporteEnum tipoSoliciante) {
        logger.info("Inicio de método consultarDatosCotizante(DatosCotizanteDTO datosCotizante)");
        try {

            TipoAfiliadoEnum tipoAfiliado = transformarTipoSolicitante(tipoSoliciante);
            datosCotizante.setTipoAfiliado(tipoAfiliado);
            List<RolAfiliadoEmpleadorDTO> rolAfiliadoLista = consultarRolesAfiliado(
                    datosCotizante.getTipoIdentificacion(),
                    datosCotizante.getNumeroIdentificacion(), datosCotizante.getTipoAfiliado());

            if (!rolAfiliadoLista.isEmpty()) {
                if (datosCotizante.getTipoAfiliado().equals(TipoAfiliadoEnum.PENSIONADO)
                        || datosCotizante.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
                    datosCotizante.setEstadoAfiliado(
                            consultarEstadoCajaPersona(null, datosCotizante.getNumeroIdentificacion(),
                                    datosCotizante.getTipoIdentificacion()));
                    datosCotizante.setFechaIngreso(rolAfiliadoLista.get(0).getRolAfiliado().getFechaAfiliacion());
                    datosCotizante.setFechaRetiro(rolAfiliadoLista.get(0).getRolAfiliado().getFechaRetiro());
                    // return datosCotizante;
                } else {
                    for (RolAfiliadoEmpleadorDTO rolAfiliado : rolAfiliadoLista) {
                        if (rolAfiliado.getEmpleador() != null) {
                            if (rolAfiliado.getEmpleador().getTipoIdentificacion()
                                    .equals(datosCotizante.getTipoIdenficacionEmpleador())
                                    && rolAfiliado.getEmpleador().getNumeroIdentificacion()
                                            .equals(datosCotizante.getNumeroIdentificacionEmpleador())) {
                                datosCotizante.setEstadoAfiliado(
                                        consultarEstadoCajaPersona(rolAfiliado.getEmpleador().getIdEmpleador(),
                                                datosCotizante.getNumeroIdentificacion(),
                                                datosCotizante.getTipoIdentificacion()));
                                datosCotizante.setFechaIngreso(rolAfiliado.getRolAfiliado().getFechaAfiliacion());
                                datosCotizante.setFechaRetiro(rolAfiliado.getRolAfiliado().getFechaRetiro());
                            }
                        } else {
                            datosCotizante.setEstadoAfiliado(
                                    consultarEstadoCajaPersona(null, datosCotizante.getNumeroIdentificacion(),
                                            datosCotizante.getTipoIdentificacion()));
                            datosCotizante.setFechaIngreso(rolAfiliadoLista.get(0).getRolAfiliado().getFechaIngreso());
                            datosCotizante.setFechaRetiro(rolAfiliadoLista.get(0).getRolAfiliado().getFechaRetiro());
                        }
                        if (datosCotizante.getEstadoAfiliado() == null) {
                            ConsultarEstadoAfiliacionRespectoTipoAfiliacion consultaEstado = new ConsultarEstadoAfiliacionRespectoTipoAfiliacion(
                                    datosCotizante.getTipoIdenficacionEmpleador(), null,
                                    datosCotizante.getTipoIdentificacion(), null,
                                    datosCotizante.getTipoAfiliado(), datosCotizante.getNumeroIdentificacion(),
                                    datosCotizante.getNumeroIdentificacionEmpleador());

                            consultaEstado.execute();
                            ConsultaEstadoAfiliacionDTO estado = consultaEstado.getResult();

                            datosCotizante.setEstadoAfiliado(
                                    estado.getEstadoAfiliacion() != null ? estado.getEstadoAfiliacion()
                                            : EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
                        }
                    }
                }
            } else {
                ConsultarEstadoAfiliacionRespectoTipoAfiliacion consultaEstado = new ConsultarEstadoAfiliacionRespectoTipoAfiliacion(
                        datosCotizante.getTipoIdenficacionEmpleador(), null, datosCotizante.getTipoIdentificacion(),
                        null,
                        datosCotizante.getTipoAfiliado(), datosCotizante.getNumeroIdentificacion(),
                        datosCotizante.getNumeroIdentificacionEmpleador());

                consultaEstado.execute();
                ConsultaEstadoAfiliacionDTO estado = consultaEstado.getResult();

                datosCotizante.setEstadoAfiliado(estado.getEstadoAfiliacion());
            }
        } catch (Exception e) {
            logger.error("Ocurrio un error consultando los datos del cotizante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return datosCotizante;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarGestionAnalistaContable(java.lang.Long, java.lang.Long,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void finalizarGestionAnalistaAporte(GestionAnalistaDTO gestionAnalista, UserDTO userDTO,
            HttpHeaders headers) {
        logger.info(
                "Inicio de método finalizarGestionAnalistaContable(Long idSolicitud, Long idTarea,UserDTO userDTO)");
        Long idTarea = gestionAnalista.getIdTarea();
        try {
            logger.info(
                    "Inicio de método finalizarGestionAnalistaContable(Long idSolicitud, Long idTarea,UserDTO userDTO)");
            SolicitudDevolucionAporteModeloDTO solicitudDevolucion = consultarSolicitudDevolucionAporte(
                    gestionAnalista.getIdSolicitud());
            if (solicitudDevolucion != null) {
                Map<String, Object> params = new HashMap<>();
                // Se suspende la tarea para evitar que se retome desde bandeja mientras está en
                // proceso
                this.suspenderTarea(idTarea);
                if (ResultadoProcesoEnum.APROBADA.equals(solicitudDevolucion.getResultadoSupervisor())) {
                    actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(),
                            ProcesoEnum.DEVOLUCION_APORTES,
                            EstadoSolicitudAporteEnum.APROBADA, null, userDTO);
                    actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(),
                            ProcesoEnum.DEVOLUCION_APORTES,
                            EstadoSolicitudAporteEnum.GESTIONAR_PAGO, null, userDTO);
                    /* se registra la información de la devolucion en las cuentas de aportes */
                    registrarDevolucion(gestionAnalista.getIdSolicitud(), userDTO);
                    /*
                     * Enviar la solicitud a la bandeja de trabajo del Analista Contable para que
                     * finalice el proceso
                     */
                    params.put("usuarioContable", gestionAnalista.getUsuarioAnalistaContable());
                    params.put("resultadoEvaluacion", 1);
                } else if (ResultadoProcesoEnum.RECHAZADA.equals(solicitudDevolucion.getResultadoSupervisor())) {
                    // Se cambia el estado de la solicitud a Rechazada
                    actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(),
                            ProcesoEnum.DEVOLUCION_APORTES,
                            EstadoSolicitudAporteEnum.RECHAZADA, null, userDTO);
                    // Se cambia el estado de la solicitud a Cerrada
                    actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(),
                            ProcesoEnum.DEVOLUCION_APORTES,
                            EstadoSolicitudAporteEnum.CERRADA, null, userDTO);
                    // Eliminar la solicitud de la bandeja del Analista de Aportes
                    params.put("resultadoEvaluacion", 2);

                }

                try {
                    this.retomarTarea(idTarea, null);
                    TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
                    terminarTarea.execute();

                } catch (RecursoNoAutorizadoException ne) {
                    String token = obtenerTokenAcceso(headers);
                    this.retomarTarea(idTarea, token);
                    TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
                    terminarTarea.setToken(token);
                    terminarTarea.execute();
                } catch (Exception e) {
                    throw new BPMSExecutionException(
                            MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE, e);
                }

                logger.info(
                        "Finaliza método finalizarGestionAnalistaContable(Long idSolicitud, Long idTarea,UserDTO userDTO)");
            }
        } catch (Exception e) {
            logger.error("Ocurrio un error en finalizarGestionAnalistaAporte", e);
            String token = obtenerTokenAcceso(headers);
            this.retomarTarea(idTarea, token);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Genera un token de acceso a partir de un refresh token (Incluido en la
     * petición de la pantalla en el header PROFILE)
     * 
     * @param headers
     * @return
     */
    private String obtenerTokenAcceso(HttpHeaders headers) {
        ObtenerTokenAcceso obtenerTokenSrv = new ObtenerTokenAcceso(headers.getHeaderString(PROFILE));
        obtenerTokenSrv.execute();
        String token = obtenerTokenSrv.getToken();
        return token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarCorreccion(java.lang.Long)
     */
    @Override
    public void finalizarCorreccion(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO,
            HttpHeaders header) {
        try {
            logger.info("Inicio de método finalizarCorreccion(Long idSolicitud)");

            String token = header.getHeaderString(HttpHeaders.AUTHORIZATION);
            // Se obtine solo la parte asociada al token (del header proviene un string de
            // tipo [tipoAtorizacion][token])
            userDTO.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));

            InformacionSolicitudDTO infoSolicitud = new InformacionSolicitudDTO();
            infoSolicitud.setIdSolicitud(idSolicitud);
            infoSolicitud.setIdTarea(idTarea);
            infoSolicitud.setInstaciaProceso(instaciaProceso);
            infoSolicitud.setUserDTO(userDTO);

            TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
            terminarTarea.setToken(userDTO.getToken());
            terminarTarea.execute();

            FinalizarCorreccionAsync finalizar = new FinalizarCorreccionAsync(infoSolicitud);
            finalizar.execute();
        } catch (Exception e) {
            logger.error(
                    "Ocurrio un error en finalizarCorreccion(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarCorreccion(java.lang.Long)
     */
    @Override
    @Asynchronous
    public void finalizarCorreccionAsync(InformacionSolicitudDTO infoSolicitud) {
        Long idSolicitud = infoSolicitud.getIdSolicitud();
        Long idTarea = infoSolicitud.getIdTarea();
        Long instaciaProceso = infoSolicitud.getInstaciaProceso();
        UserDTO userDTO = infoSolicitud.getUserDTO();
        try {
            // logger.info(
            //         "Inicio de método finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)");
            // Se suspende temporalmente la tarea a fin de que no sea retomada por el
            // usuario desde la bandeja de tareas
            // SuspenderTarea suspenderTareaService = new SuspenderTarea(idTarea, new HashMap<>());
            // suspenderTareaService.setToken(userDTO.getToken());
            // suspenderTareaService.execute();

            SolicitudCorreccionAporteModeloDTO solicitudCorreccion = consultarSolicitudCorreccionAporte(idSolicitud);
            if (ResultadoProcesoEnum.APROBADA.equals(solicitudCorreccion.getResultadoSupervisor())) {
                CorreccionDTO correccion = consultarCorreccionTemporal(idSolicitud);
                List<CotizanteDTO> cotizantesTemporales = correccion.getLstCotizantes();
                List<BigDecimal> valorAportes = new ArrayList<>();
                BigDecimal montoAporteGeneral = BigDecimal.ZERO;
                BigDecimal moraAporteGeneral = BigDecimal.ZERO;

                if (cotizantesTemporales == null || cotizantesTemporales.isEmpty()) {
                    // Representa si el aporte viene sin detalle desde el aporte manual
                    Boolean sinDetalleInicio = true;
                    logger.info("El aporte viene sin detalle");
                    valorAportes = registrarAporteCorreccionSinDetalle(idSolicitud, correccion.getCorreccion(),
                            solicitudCorreccion.getIdSolicitudCorreccionAporte(), sinDetalleInicio,
                            correccion.getAnalisis().getIdAporte(),
                            null);
                } else {
                    logger.info("El aporte viene con detalle");
                    valorAportes = registrarAporteCorreccionConDetalle(idSolicitud,
                            solicitudCorreccion.getIdSolicitudCorreccionAporte());
                    if (valorAportes.isEmpty()) {
                        logger.info("Fin de método registrar(Long idSoliciutd) de manera no exitosa");
                        actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                                EstadoSolicitudAporteEnum.BLOQUEADA,
                                null, userDTO);
                    }
                }

                AporteGeneralModeloDTO aporteGeneralACorregir = consultarAporteGeneral(
                        correccion.getAnalisis().getIdAporte());
                logger.info("Aporte a corregir: " + aporteGeneralACorregir.getId());
                montoAporteGeneral = aporteGeneralACorregir.getValorTotalAporteObligatorio().setScale(0,
                        RoundingMode.FLOOR);
                moraAporteGeneral = aporteGeneralACorregir.getValorInteresesMora().setScale(0, RoundingMode.FLOOR);
                logger.info("montoAporteGeneral: ");
                logger.info(montoAporteGeneral);
                logger.info("moraAporteGeneral: ");
                logger.info(moraAporteGeneral);
                if (montoAporteGeneral.equals(valorAportes.get(0)) && moraAporteGeneral.equals(valorAportes.get(1))) {
                    aporteGeneralACorregir.setEstadoAporteAportante(EstadoAporteEnum.ANULADO);
                }
                aporteGeneralACorregir.setValorTotalAporteObligatorio(
                        aporteGeneralACorregir.getValorTotalAporteObligatorio().subtract(valorAportes.get(0)));
                aporteGeneralACorregir.setValorInteresesMora(
                        aporteGeneralACorregir.getValorInteresesMora().subtract(valorAportes.get(1)));
                crearActualizarAporteGeneral(aporteGeneralACorregir);
                logger.info("Aporte corregido: " + aporteGeneralACorregir.getId());
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.APROBADA, null,
                        userDTO);
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.CERRADA, null,
                        userDTO);

                // Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
                // RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
                // retomarTareaService.setToken(userDTO.getToken());
                // retomarTareaService.execute();

                // TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
                // terminarTarea.setToken(userDTO.getToken());
                // terminarTarea.execute();

                try { // Actualiza la cartera del aportante -> HU-169
                    logger.error("Procesamiento corrección CO1");

                    if (aporteGeneralACorregir != null && aporteGeneralACorregir.getPeriodoAporte() != null
                            && aporteGeneralACorregir.getTipoSolicitante() != null) {
                        logger.error("Procesamiento corrección CO2");
                        PersonaModeloDTO personaDTO = consultarPersona(solicitudCorreccion.getIdPersona());
                        logger.error("Procesamiento corrección CO3");
                        logger.error("periodo " + aporteGeneralACorregir.getPeriodoAporte());
                        logger.error("periodoAnterior " + aporteGeneralACorregir.getPeriodoAporte());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                        String periodo = dateFormat
                                .format(new Date(cotizantesTemporales.get(0).getCorreccion().getPeriodoPago()));
                        logger.error("periodoAnterior " + aporteGeneralACorregir.getPeriodoAporte());
                        logger.error("tipoIdentificacion " + personaDTO.getTipoIdentificacion().name());
                        logger.error("numeroIdentificacion " + personaDTO.getNumeroIdentificacion());
                        logger.error("tipoAportante " + aporteGeneralACorregir.getTipoSolicitante().name());
                        logger.error("Procesamiento corrección CO4");
                        actualizarDeudaPresuntaCartera(personaDTO.getTipoIdentificacion(),
                                personaDTO.getNumeroIdentificacion(), periodo,
                                aporteGeneralACorregir.getTipoSolicitante());
                        logger.error("Procesamiento corrección CO5");
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error actualizando la deuda presunta", e);
                }
            } else if (ResultadoProcesoEnum.RECHAZADA.equals(solicitudCorreccion.getResultadoSupervisor())) {
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.RECHAZADA, null,
                        userDTO);
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.CERRADA, null,
                        userDTO);

                // Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
                // RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
                // retomarTareaService.setToken(userDTO.getToken());
                // retomarTareaService.execute();

                // TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
                // terminarTarea.setToken(userDTO.getToken());
                // terminarTarea.execute();
            }
            logger.info(
                    "Fin de método finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)");

        } catch (Exception e) {
            logger.error(
                    "Ocurrio un error en finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)",
                    e);
            // Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
            RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
            retomarTareaService.setToken(userDTO.getToken());
            retomarTareaService.execute();
            logger.error(
                    "Ocurrio un error en finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que invoca el servicio de consulta de información de persona por
     * identificador
     * 
     * @param idPersona
     *                  Identificador de la persona
     * @return Información de la persona
     */
    private PersonaModeloDTO consultarPersona(Long idPersona) {
        // logger.info("Inicio de método consultarPersona");
        ConsultarPersona service = new ConsultarPersona(idPersona);
        service.execute();
        // logger.info("Fin de método consultarPersona");
        return service.getResult();
    }

    /**
     * Metodo que invoca al servicio borrarTemporalesPILA
     * para borrar en las tablas temporales de pila
     * 
     * @param idRegistroGeneral
     *                          ingresa como parametro
     *                          al método
     */
    public void borrarTemporalesPILA(Long idRegistroGeneral) {
        logger.info("Inicio de método borrarTemporalesPILA(Long idRegistroGeneral)");
        BorrarTemporalesPILA borrarTemporal = new BorrarTemporalesPILA(idRegistroGeneral);
        borrarTemporal.execute();
        logger.info("Fin de método borrarTemporalesPILA(Long idRegistroGeneral)");
    }

    /**
     * Método encargado de guardar la información faltante.
     * 
     * @param idSolicitud
     *                            id de la solicitud.
     * @param informacionFaltante
     *                            información faltante.
     */
    private void guardarInformacionFaltante(Long idSolicitud, GestionInformacionFaltanteDTO informacionFaltante) {
        logger.info(
                "Inicio de método guardarInformacionFaltante(Long idSolicitud,GestionInformacionFaltanteDTO informacionFaltante)");
        SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitud);
        guardarInformacionFaltante(informacionFaltante.getInformacionFaltante());
        guardarDocumentos(solicitudAporteDTO.getNumeroRadicacion(), informacionFaltante.getDocumentos(),
                ActividadEnum.GESTIONAR_INFO_FALTANTE);
        logger.info(
                "Fin  de método guardarInformacionFaltante(Long idSolicitud,GestionInformacionFaltanteDTO informacionFaltante)");
    }

    /**
     * Método que se encarga de validar si el aporte es por si mismo, entonces llena
     * la información del cotizante.
     * 
     * @param aporteManualDTO
     *                        dto con la información temporal del aporte.
     */
    private void ajustarSolicitantePorSiMismo(AporteManualDTO aporteManualDTO) {
        logger.info("Inicio de método ajustarSolicitantePorSiMismo(AporteManualDTO aporteDTO)");
        /*
         * si el solicitante es por si mismo entonces se añade el solicitante como un
         * cotizante.
         */

        if (DecisionSiNoEnum.NO.equals(aporteManualDTO.getRadicacionDTO().getPagadorTercero())) {

            CotizanteDTO cotizante = new CotizanteDTO();
            DatosCotizanteDTO datosCotizanteDTO = new DatosCotizanteDTO();
            datosCotizanteDTO.setTipoIdentificacion(aporteManualDTO.getRadicacionDTO().getTipoIdentificacion());
            datosCotizanteDTO.setNumeroIdentificacion(aporteManualDTO.getRadicacionDTO().getNumeroIdentificacion());
            datosCotizanteDTO = consultarDatosCotizante(datosCotizanteDTO,
                    aporteManualDTO.getRadicacionDTO().getTipo());
            cotizante.setTipoIdentificacion(aporteManualDTO.getRadicacionDTO().getTipoIdentificacion());
            cotizante.setNumeroIdentificacion(aporteManualDTO.getRadicacionDTO().getNumeroIdentificacion());
            cotizante.setPrimerApellido(aporteManualDTO.getRadicacionDTO().getPrimerApellido());
            cotizante.setPrimerNombre(aporteManualDTO.getRadicacionDTO().getPrimerNombre());
            cotizante.setSegundoNombre(aporteManualDTO.getRadicacionDTO().getSegundoNombre());
            cotizante.setSegundoApellido(aporteManualDTO.getRadicacionDTO().getSegundoApellido());
            cotizante.setEstado(datosCotizanteDTO.getEstadoAfiliado());
            cotizante.setFechaIngreso(datosCotizanteDTO.getFechaIngreso());
            cotizante.setFechaRetiro(datosCotizanteDTO.getFechaRetiro());
            cotizante.setTipoAfiliado(datosCotizanteDTO.getTipoAfiliado());
            List<CotizanteDTO> cotizantes = new ArrayList<>();
            cotizantes.add(cotizante);
            aporteManualDTO.setCotizantesTemporales(cotizantes);
        }
        logger.info("Fin de método ajustarSolicitantePorSiMismo(AporteManualDTO aporteDTO)");
    }
    @Override
    @Asynchronous
    public void finalizarCorreccionAsyncMasiva(InformacionSolicitudDTO infoSolicitud) {
        Long idSolicitud = infoSolicitud.getIdSolicitud();
        UserDTO userDTO = infoSolicitud.getUserDTO();
        try {
            // logger.info(
            //         "Inicio de método finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)");

            SolicitudCorreccionAporteModeloDTO solicitudCorreccion = consultarSolicitudCorreccionAporte(idSolicitud);
            //logger.info("Res solicitudCorreccion: " + ToStringBuilder.reflectionToString(solicitudCorreccion));
            if (ResultadoProcesoEnum.APROBADA.equals(solicitudCorreccion.getResultadoSupervisor())) {
                CorreccionDTO correccion = consultarCorreccionTemporal(idSolicitud);
                List<CotizanteDTO> cotizantesTemporales = correccion.getLstCotizantes();
                //trae un listado de los nuevos cotizantes 
                List<BigDecimal> valorAportes = new ArrayList<>();
                BigDecimal montoAporteGeneral = BigDecimal.ZERO;
                BigDecimal moraAporteGeneral = BigDecimal.ZERO;

                if (cotizantesTemporales == null || cotizantesTemporales.isEmpty()) {
                    //si hay data
                    // Representa si el aporte viene sin detalle desde el aporte manual
                    Boolean sinDetalleInicio = true;
                    valorAportes = registrarAporteCorreccionSinDetalle(idSolicitud, correccion.getCorreccion(),
                            solicitudCorreccion.getIdSolicitudCorreccionAporte(), sinDetalleInicio,
                            correccion.getAnalisis().getIdAporte(),
                            null);
                    // logger.info("El aporte viene sin detalle y se registra correctamente");
                } else {
                    // logger.info("El aporte viene con detalle");
                    valorAportes = registrarAporteCorreccionConDetalle(idSolicitud,
                            solicitudCorreccion.getIdSolicitudCorreccionAporte());
                    // logger.info("El aporte viene con detalle y se registra correctamente");
                    if (valorAportes.isEmpty()) {
                        // logger.info("Fin de método registrar(Long idSoliciutd) de manera no exitosa");
                        actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                                EstadoSolicitudAporteEnum.BLOQUEADA,
                                null, userDTO);
                    }

                }

                AporteGeneralModeloDTO aporteGeneralACorregir = consultarAporteGeneral(
                        correccion.getAnalisis().getIdAporte());
                montoAporteGeneral = aporteGeneralACorregir.getValorTotalAporteObligatorio().setScale(0,
                        RoundingMode.FLOOR);
                // logger.info("montoAporteGeneral: " + montoAporteGeneral);
                moraAporteGeneral = aporteGeneralACorregir.getValorInteresesMora().setScale(0, RoundingMode.FLOOR);
                // logger.info("moraAporteGeneral: " + moraAporteGeneral);
                if (montoAporteGeneral.equals(valorAportes.get(0)) && moraAporteGeneral.equals(valorAportes.get(1))) {
                    // logger.info("****entra al primer if ****");
                    aporteGeneralACorregir.setEstadoAporteAportante(EstadoAporteEnum.ANULADO);
                }
                aporteGeneralACorregir.setValorTotalAporteObligatorio(
                        aporteGeneralACorregir.getValorTotalAporteObligatorio().subtract(valorAportes.get(0)));
                aporteGeneralACorregir.setValorInteresesMora(
                        aporteGeneralACorregir.getValorInteresesMora().subtract(valorAportes.get(1)));
                crearActualizarAporteGeneral(aporteGeneralACorregir);

                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.APROBADA, null,
                        userDTO);
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.CERRADA, null,
                        userDTO);

                try { // Actualiza la cartera del aportante -> HU-169
                    logger.error("Procesamiento corrección CO1");

                    if (aporteGeneralACorregir != null && aporteGeneralACorregir.getPeriodoAporte() != null
                            && aporteGeneralACorregir.getTipoSolicitante() != null) {
                        logger.error("Procesamiento corrección CO2");
                        PersonaModeloDTO personaDTO = consultarPersona(solicitudCorreccion.getIdPersona());
                        logger.error("Procesamiento corrección CO3");
                        logger.error("periodo " + aporteGeneralACorregir.getPeriodoAporte());
                        logger.error("periodoAnterior " + aporteGeneralACorregir.getPeriodoAporte());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                        String periodo = dateFormat
                                .format(new Date(cotizantesTemporales.get(0).getCorreccion().getPeriodoPago()));
                        logger.error("periodoAnterior " + aporteGeneralACorregir.getPeriodoAporte());
                        logger.error("tipoIdentificacion " + personaDTO.getTipoIdentificacion().name());
                        logger.error("numeroIdentificacion " + personaDTO.getNumeroIdentificacion());
                        logger.error("tipoAportante " + aporteGeneralACorregir.getTipoSolicitante().name());
                        logger.error("Procesamiento corrección CO4");
                        actualizarDeudaPresuntaCartera(personaDTO.getTipoIdentificacion(),
                                personaDTO.getNumeroIdentificacion(), periodo,
                                aporteGeneralACorregir.getTipoSolicitante());
                        logger.error("Procesamiento corrección CO5");
                        // actualizar la solicitud de la correccion y se asigna el aporte general creado
                        solicitudCorreccion.setIdAporteGeneralNuevo(correccion.getSolicitudCorrecionDTO().getIdAporte());
                        solicitudCorreccion.setEstadoSolicitud(EstadoSolicitudAporteEnum.CERRADA);

                        CrearActualizarSolicitudCorreccionAporte crearActualizarSolicitudCorreccionAporte =
                            new CrearActualizarSolicitudCorreccionAporte(solicitudCorreccion);
                        crearActualizarSolicitudCorreccionAporte.execute();

                        

                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error actualizando la deuda presunta", e);
                }
            } else if (ResultadoProcesoEnum.RECHAZADA.equals(solicitudCorreccion.getResultadoSupervisor())) {
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.RECHAZADA, null,
                        userDTO);
                actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.CORRECCION_APORTES,
                        EstadoSolicitudAporteEnum.CERRADA, null,
                        userDTO);

            }
            


            // logger.info(
            //         "Fin de método finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)");

        } catch (Exception e) {
            logger.error(
                    "Ocurrio un error en finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)",
                    e);
            logger.error(
                    "Ocurrio un error en finalizarCorreccionAsync(Long idSolicitud, Long idTarea, Long instaciaProceso, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que se encarga de realizar el registro de la devolución en base de
     * datos
     * 
     * @param idSolicitudGlobal
     *                          Identificador de la solicitud global relacionada
     * @param userDTO
     *                          Información del usuario, tomada del contexto
     */
    private void registrarDevolucion(Long idSolicitudGlobal, UserDTO userDTO) {
        try {
            logger.info("Inicio de método registrarDevolucion");

            // Consulta la devolución almacenada en la tabla temporal
            DevolucionDTO devolucionDTO = consultarDevolucionTemporal(idSolicitudGlobal);

            // Crea el registro en DevolucionAporte
            DevolucionAporteModeloDTO devolucionAporteDTO = transformarDevolucionAporte(devolucionDTO);
            Long idDevolucionAporte = crearActualizarDevolucionAporte(devolucionAporteDTO);

            // Actualiza la SolicitudDevolucionAporte
            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte(
                    idSolicitudGlobal);
            solicitudDevolucionAporteDTO.setIdDevolucionAporte(idDevolucionAporte);
            crearActualizarSolicitudDevolucionAporte(solicitudDevolucionAporteDTO);

            // Recorre cada aporte general agregado a la solicitud de devolución
            for (AnalisisDevolucionDTO analisisDevolucionDTO : devolucionDTO.getAnalisis()) {
                // Consulta el AporteGeneral
                AporteGeneralModeloDTO aporteGeneralDTO = consultarAporteGeneral(analisisDevolucionDTO.getIdAporte());
                BigDecimal nuevoAporteGeneral = aporteGeneralDTO.getValorTotalAporteObligatorio();
                BigDecimal nuevoInteresGeneral = aporteGeneralDTO.getValorInteresesMora();

                // Guarda el RegistroGeneral
                RegistroGeneralModeloDTO registroGeneralDTO = null;
                if (!analisisDevolucionDTO.getConDetalle()) {
                    registroGeneralDTO = consultarRegistroGeneralId(aporteGeneralDTO.getIdRegistroGeneral());

                    // se actualiza el registro general anterior para quitar al marca de registro
                    // actual
                    registroGeneralDTO.setOutRegistroActual(Boolean.FALSE);
                    guardarRegistroGeneral(registroGeneralDTO);

                    // se prepara el nuevo registro general 
                    // referencia al registro inicial
                    registroGeneralDTO.setOutRegInicial(registroGeneralDTO.getOutRegInicial() != null
                            ? registroGeneralDTO.getOutRegInicial()
                            : registroGeneralDTO.getId());
                    registroGeneralDTO.setOutRegistroActual(Boolean.TRUE);

                    registroGeneralDTO.setId(null);
                    registroGeneralDTO.setRegistroControlManual(idSolicitudGlobal);
                    registroGeneralDTO.setEsSimulado(Boolean.TRUE);
                    registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
                    registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
                    nuevoAporteGeneral = aporteGeneralDTO.getValorTotalAporteObligatorio()
                            .subtract(new BigDecimal(analisisDevolucionDTO.getHistorico().getAporteObligatorio()));
                    nuevoInteresGeneral = aporteGeneralDTO.getValorInteresesMora().subtract(new BigDecimal(
                            analisisDevolucionDTO.getHistorico().getMora() != null
                                    ? analisisDevolucionDTO.getHistorico().getMora()
                                    : "0"));
                    aporteGeneralDTO.setValorTotalAporteObligatorio(nuevoAporteGeneral);
                    aporteGeneralDTO.setValorInteresesMora(nuevoInteresGeneral);
                    registroGeneralDTO.setValTotalApoObligatorio(nuevoAporteGeneral);
                    registroGeneralDTO.setValorIntMora(nuevoInteresGeneral);
                } else {
                    registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
                    registroGeneralDTO.setRegistroControlManual(idSolicitudGlobal);
                    registroGeneralDTO.setEsSimulado(Boolean.TRUE);
                    registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
                    registroGeneralDTO
                            .setOutEstadoArchivo(EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES);
                }

                Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);

                Long idRegistroGeneralOriginal = aporteGeneralDTO.getIdRegistroGeneral();
                // Guarda el AporteGeneral
                aporteGeneralDTO.setIdRegistroGeneral(idRegistroGeneral);
                Long idAporteGeneral = crearActualizarAporteGeneral(aporteGeneralDTO);

                if (analisisDevolucionDTO.getConDetalle() && analisisDevolucionDTO.getCotizanteDTO() != null) {
                    for (CotizanteDTO cotizanteDTO : analisisDevolucionDTO.getCotizanteDTO()) {
                        // Consulta el AporteDetallado
                        AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado(
                                cotizanteDTO.getIdCotizante());
                        BigDecimal aporteDevolucion = cotizanteDTO.getAportes().getAporteObligatorioNuevo() != null
                                ? cotizanteDTO.getAportes().getAporteObligatorioNuevo()
                                : BigDecimal.ZERO;
                        BigDecimal moraDevolucion = cotizanteDTO.getAportes().getMoraAporteNuevo() != null
                                ? cotizanteDTO.getAportes().getMoraAporteNuevo()
                                : BigDecimal.ZERO;
                        BigDecimal valorDevolucion = aporteDetalladoDTO.getAporteObligatorio()
                                .subtract(aporteDevolucion);
                        BigDecimal valorDevolucionInteres = aporteDetalladoDTO.getValorMora().subtract(moraDevolucion);
                        BigDecimal nuevoAporteDetallado = aporteDetalladoDTO.getAporteObligatorio()
                                .subtract(valorDevolucion);
                        BigDecimal nuevoInteresDetallado = aporteDetalladoDTO.getValorMora()
                                .subtract(valorDevolucionInteres);
                        aporteDetalladoDTO.setAporteObligatorio(nuevoAporteDetallado);
                        aporteDetalladoDTO.setValorMora(nuevoInteresDetallado);
                        aporteDetalladoDTO.setDiasCotizados(cotizanteDTO.getAportes().getDiasCotizadoNuevo() != null
                                ? cotizanteDTO.getAportes().getDiasCotizadoNuevo().shortValue()
                                : null);
                        aporteDetalladoDTO
                                .setHorasLaboradas(cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo() != null
                                        ? cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo().shortValue()
                                        : null);
                        aporteDetalladoDTO.setSalarioBasico(cotizanteDTO.getAportes().getSalarioBasicoNuevo());
                        aporteDetalladoDTO.setValorIBC(cotizanteDTO.getAportes().getIbcNuevo());
                        aporteDetalladoDTO.setTarifa(cotizanteDTO.getAportes().getTarifaNuevo());
                        nuevoAporteGeneral = nuevoAporteGeneral.subtract(valorDevolucion);
                        nuevoInteresGeneral = nuevoInteresGeneral.subtract(valorDevolucionInteres);

                        // Guarda el RegistroDetallado
                        RegistroDetalladoModeloDTO registroDetalladoDTO = consultarRegistroDetalladoPorId(
                                aporteDetalladoDTO.getIdRegistroDetallado());

                        // se actualiza el registro detallado anterior para quitar al marca de registro
                        // actual
                        registroDetalladoDTO.setOutRegistroActual(Boolean.FALSE);
                        guardarRegistroDetallado(registroDetalladoDTO);

                        // se prepara el nuevo registro detallado
                        // referencia al registro inicial
                        registroDetalladoDTO.setOutRegInicial(registroDetalladoDTO.getOutRegInicial() != null
                                ? registroDetalladoDTO.getOutRegInicial()
                                : registroDetalladoDTO.getId());

                        registroDetalladoDTO.setId(null);
                        registroDetalladoDTO.setOutRegistroActual(Boolean.TRUE);

                        registroDetalladoDTO.setAporteObligatorio(nuevoAporteDetallado);
                        registroDetalladoDTO.setOutValorMoraCotizante(nuevoInteresDetallado);
                        registroDetalladoDTO.setDiasCotizados(cotizanteDTO.getAportes().getDiasCotizadoNuevo() != null
                                ? cotizanteDTO.getAportes().getDiasCotizadoNuevo().shortValue()
                                : null);
                        registroDetalladoDTO
                                .setHorasLaboradas(cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo() != null
                                        ? cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo().shortValue()
                                        : null);
                        registroDetalladoDTO.setSalarioBasico(cotizanteDTO.getAportes().getSalarioBasicoNuevo());
                        registroDetalladoDTO.setValorIBC(cotizanteDTO.getAportes().getIbcNuevo());
                        registroDetalladoDTO.setTarifa(cotizanteDTO.getAportes().getTarifaNuevo());

                        Long idRegistroDetallado = cotizanteDTO.getIdRegistroDetalladoNuevo();
                        // guardarRegistroDetallado(registroDetalladoDTO);
                        // logger.info("registrardev:"+idRegistroDetallado);
                        // Crea transacción
                        Long idTransaccion = crearTransaccion();
                        registroGeneralDTO.setTransaccion(idTransaccion);
                        guardarRegistroGeneral(registroGeneralDTO);

                        if (cotizanteDTO.getTipoCotizante() == null
                                && registroDetalladoDTO.getTipoCotizante() != null) {
                            String codigoTipoCotizante = registroDetalladoDTO.getTipoCotizante().toString();
                            cotizanteDTO.setTipoCotizante(
                                    TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(codigoTipoCotizante)));
                        }
                        // se radican novedades
                        radicarNovedades(aporteGeneralDTO.getIdRegistroGeneral(), registroGeneralDTO.getTransaccion(),
                                cotizanteDTO,
                                devolucionDTO.getSolicitud().getTipoIdentificacion(),
                                devolucionDTO.getSolicitud().getNumeroIdentificacion());

                        // Actualiza el AporteDetallado
                        aporteDetalladoDTO.setIdRegistroDetallado(idRegistroDetallado);
                        crearActualizarAporteDetallado(aporteDetalladoDTO);

                        // Crea el MovimientoAporte
                        MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                                TipoAjusteMovimientoAporteEnum.DEVOLUCION,
                                TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES,
                                EstadoAporteEnum.CORREGIDO, valorDevolucion, valorDevolucionInteres, new Date(),
                                new Date(),
                                aporteDetalladoDTO.getId(), idAporteGeneral);
                        Long idMovimientoAporte = crearActualizarMovimientoAporte(movimientoAporteDTO);

                        // Crea el DevolucionDetalleAporte
                        DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = transformarDevolucionAporteDetalle(
                                null, cotizanteDTO,
                                idMovimientoAporte, idDevolucionAporte, userDTO);
                        crearActualizarDevolucionAporteDetalle(devolucionAporteDetalleDTO);
                    }
                    aporteGeneralDTO.setValorTotalAporteObligatorio(nuevoAporteGeneral);
                    aporteGeneralDTO.setValorInteresesMora(nuevoInteresGeneral);
                    aporteGeneralDTO.setIdRegistroGeneral(idRegistroGeneralOriginal);
                    crearActualizarAporteGeneral(aporteGeneralDTO);
                    registroGeneralDTO.setValTotalApoObligatorio(nuevoAporteGeneral);
                    registroGeneralDTO.setValorIntMora(nuevoInteresGeneral);
                    registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
                    guardarRegistroGeneral(registroGeneralDTO);
                } else {
                    // Crea el MovimientoAporte
                    MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                            TipoAjusteMovimientoAporteEnum.DEVOLUCION,
                            TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES, EstadoAporteEnum.CORREGIDO,
                            new BigDecimal(analisisDevolucionDTO.getHistorico().getAporteObligatorio()),
                            new BigDecimal(analisisDevolucionDTO.getHistorico().getMora() != null
                                    ? analisisDevolucionDTO.getHistorico().getMora()
                                    : "0"),
                            new Date(), new Date(), null, idAporteGeneral);
                    Long idMovimientoAporte = crearActualizarMovimientoAporte(movimientoAporteDTO);

                    // Crea el DevolucionDetalleAporte
                    DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = transformarDevolucionAporteDetalle(
                            analisisDevolucionDTO,
                            null, idMovimientoAporte, idDevolucionAporte, userDTO);
                    crearActualizarDevolucionAporteDetalle(devolucionAporteDetalleDTO);
                }
            }

            logger.info("Fin de método registrarDevolucion");
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método registrarDevolucion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que transforma un <code>CotizanteDTO</code> en un
     * <code>DevolucionAporteDetalleModeloDTO</code>
     * 
     * @param cotizanteDTO
     *                           El <code>CotizanteDTO</code> a transformar
     * @param idMovimientoAporte
     *                           Identificador del movimiento monetario asociado
     * @param idDevolucionAporte
     *                           Identificador de la devolución asociada
     * @param userDTO
     *                           Información del usuario, tomada del contexto
     * @return El <code>DevolucionAporteDetalleModeloDTO</code> resultante
     */
    private DevolucionAporteDetalleModeloDTO transformarDevolucionAporteDetalle(AnalisisDevolucionDTO analisis,
            CotizanteDTO cotizanteDTO,
            Long idMovimientoAporte, Long idDevolucionAporte, UserDTO userDTO) {
        DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = new DevolucionAporteDetalleModeloDTO();
        if (analisis != null) {
            devolucionAporteDetalleDTO.setComentarioHistorico(analisis.getHistorico().getComentarios());
        } else if (cotizanteDTO != null) {
            devolucionAporteDetalleDTO.setComentarioAportes(cotizanteDTO.getAportes().getComentario());
            devolucionAporteDetalleDTO.setComentarioHistorico(cotizanteDTO.getHistorico().getComentarios());
            devolucionAporteDetalleDTO.setComentarioNovedades(cotizanteDTO.getComentarioNovedad());
            devolucionAporteDetalleDTO
                    .setIncluyeAporteObligatorio(cotizanteDTO.getAportes().getAplicarAporteObligatorio());
            devolucionAporteDetalleDTO.setIncluyeMoraCotizante(cotizanteDTO.getAportes().getAplicarMoraCotizante());
        }
        devolucionAporteDetalleDTO.setFechaGestion(new Date().getTime());
        devolucionAporteDetalleDTO.setIdMovimientoAporte(idMovimientoAporte);
        devolucionAporteDetalleDTO.setIdDevolucionAporte(idDevolucionAporte);
        devolucionAporteDetalleDTO.setUsuario(userDTO.getNombreUsuario());
        return devolucionAporteDetalleDTO;
    }

    /**
     * Método que crea o actualiza un registro en la tabla
     * <code>DvolucionAporteDetalle</code>
     * 
     * @param devolucionAporteDetalleDTO
     *                                   Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO) {
        logger.info("Inicia método crearACtualizaDevolucionAporteDetalle");
        CrearActualizarDevolucionAporteDetalle crearActualizarDevolucionAporteDetalle = new CrearActualizarDevolucionAporteDetalle(
                devolucionAporteDetalleDTO);
        crearActualizarDevolucionAporteDetalle.execute();
        Long id = crearActualizarDevolucionAporteDetalle.getResult();
        logger.info("Inicia método crearACtualizaDevolucionAporteDetalle");
        return id;
    }

    /**
     * Método que crea o actualiza un registro en la tabla
     * <code>AporteGeneral</code>
     * 
     * @param aporteGeneralDTO
     *                         Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
        logger.info("Inicia método crearActualizarAporteGeneral");
        CrearActualizarAporteGeneral crearActualizarAporteGeneral = new CrearActualizarAporteGeneral(aporteGeneralDTO);
        crearActualizarAporteGeneral.execute();
        Long id = crearActualizarAporteGeneral.getResult();
        logger.info("Inicia finaliza crearActualizarAporteGeneral");
        return id;
    }

    /**
     * Método que invoca el servicio de actualización de un registro en
     * <code>DevolucionAporte</code>
     * 
     * @param devolucionAporteDTO
     *                            Información de la devolución a almacenar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarDevolucionAporte(DevolucionAporteModeloDTO devolucionAporteDTO) {
        logger.info("Inicia método crearActualizarDevolucionAporte");
        CrearActualizarDevolucionAporte crearActualizarDevolucionAporte = new CrearActualizarDevolucionAporte(
                devolucionAporteDTO);
        crearActualizarDevolucionAporte.execute();
        logger.info("Fin método crearActualizarDevolucionAporte");
        return crearActualizarDevolucionAporte.getResult();
    }

    /**
     * Método que transforma un <code>DevolucionDTO</code> en un
     * <code>DevolucionAporteModeloDTO</code>
     * 
     * @param devolucionDTO
     *                      El <code>DevolucionDTO</code> a transformar
     * @return Objeto <code>DevolucionAporteModeloDTO</code> resultante
     */
    private DevolucionAporteModeloDTO transformarDevolucionAporte(DevolucionDTO devolucionDTO) {
        logger.info("Inicia método transformarDevolucionAporte");
        DevolucionAporteModeloDTO devolucionAporteDTO = new DevolucionAporteModeloDTO();
        devolucionAporteDTO.setCajaCompensacion(devolucionDTO.getSolicitud().getCaja());
        devolucionAporteDTO.setDescuentoGestionFinanciera(devolucionDTO.getDescuentoGestionFinanciera());
        devolucionAporteDTO.setDescuentoGestionPagoOI(devolucionDTO.getDescuentoGestionPagoOI());
        devolucionAporteDTO.setDescuentoOtro(devolucionDTO.getDescuentoOtro());
        devolucionAporteDTO.setDestinatarioDevolucion(devolucionDTO.getSolicitud().getDestinatario());
        devolucionAporteDTO.setFechaRecepcion(devolucionDTO.getSolicitud().getFechaRecepcion());

        if (devolucionDTO.getSolicitud().getPago() != null) {
            // Crea la forma de pago
            MedioDePagoModeloDTO medioDePagoDTO = devolucionDTO.getSolicitud().getPago()
                    .convertToMedioDePagoModeloDTO();
            medioDePagoDTO = guardarMedioDePago(medioDePagoDTO);
            devolucionAporteDTO.setFormaPago(medioDePagoDTO.getIdMedioDePago());
            devolucionAporteDTO.setSedeCCF(medioDePagoDTO.getSede());
        }

        if (devolucionDTO.getSolicitud().getMontoReclamado() != null) {
            devolucionAporteDTO.setMontoAportes(new BigDecimal(devolucionDTO.getSolicitud().getMontoReclamado()));
        }

        if (devolucionDTO.getSolicitud().getMontoIntereses() != null) {
            devolucionAporteDTO.setMontoIntereses(new BigDecimal(devolucionDTO.getSolicitud().getMontoIntereses()));
        }

        devolucionAporteDTO.setMotivoPeticion(devolucionDTO.getSolicitud().getMotivoPeticion());
        devolucionAporteDTO.setOtroDestinatario(devolucionDTO.getSolicitud().getOtro());
        devolucionAporteDTO
                .setPeriodoReclamado(StringUtils.join(devolucionDTO.getSolicitud().getPeriodosReclamados(), "|"));
        devolucionAporteDTO.setOtraCaja(devolucionDTO.getSolicitud().getOtraCaja());
        devolucionAporteDTO.setOtroMotivo(devolucionDTO.getSolicitud().getOtroMotivo());
        logger.info("Finaliza método transformarDevolucionAporte");
        return devolucionAporteDTO;
    }

    /**
     * Método que invoca el servicio de creación o actualización de un registro en
     * <code>AporteDetallado</code>
     * 
     * @param aporteDetalladoDTO
     *                           Datos del registro a modificar
     * @return El identificador del registro actualizado
     */
    private Long crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
        logger.info("Inicia método crearActualizarDevolucionAporte");
        CrearAporteDetallado crearAporteDetallado = new CrearAporteDetallado(aporteDetalladoDTO);
        crearAporteDetallado.execute();
        Long id = crearAporteDetallado.getResult();
        logger.info("finaliza método crearActualizarDevolucionAporte" + id);
        return id;
    }

    /**
     * Método que invoca el servicio de creación/actualización de un conjunto de
     * registros en <code>MovimientoAporte</code>
     * 
     * @param listaMovimientoAporteDTO
     *                                 Lista de registros a crear/actualizar
     */
    private Long crearActualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteDTO) {
        logger.info("Inicia método crearActualizarMovimientoAporte");
        ActualizarMovimientoAporte actualizarMovimientoAporte = new ActualizarMovimientoAporte(movimientoAporteDTO);
        actualizarMovimientoAporte.execute();
        logger.info("Finaliza método crearActualizarMovimientoAporte");
        return actualizarMovimientoAporte.getResult();
    }

    /**
     * Método que se encarga de asignar los estados de evaluación de cotizante.
     * 
     * @param cotizantes
     *                              a asignarle los estados de evaluación.
     * @param registroDetalladoList
     *                              registros detallado con la evaluacion.
     * @return lista de cotizante con los estados de evaluacion asignados.
     */
    private List<CotizanteDTO> asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes,
            List<RegistroDetalladoModeloDTO> registroDetalladoList, boolean aporteManual) {
        logger.info(
                "Inicio de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        for (CotizanteDTO cotizante : cotizantes) {
            for (RegistroDetalladoModeloDTO registroDetalladoModeloDTO : registroDetalladoList) {
                if ((aporteManual && cotizante.getIdCotizante() != null
                        && cotizante.getIdCotizante().equals(registroDetalladoModeloDTO.getId()))
                        || (!aporteManual && cotizante.getIdRegistroDetalladoNuevo() != null
                                && cotizante.getIdRegistroDetalladoNuevo()
                                        .equals(registroDetalladoModeloDTO.getId()))) {
                    EvaluacionDTO evaluacion = new EvaluacionDTO();
                    if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setDepV0(registroDetalladoModeloDTO.getOutEstadoValidacionV0());
                        evaluacion.setDepV1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV2(registroDetalladoModeloDTO.getOutEstadoValidacionV2());
                        evaluacion.setDepV3(registroDetalladoModeloDTO.getOutEstadoValidacionV3());
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setIdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.PENSIONADO.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setPdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    }
                    evaluacion.setPersona(cotizante.getTipoAfiliado());
                    evaluacion.setValorAporte(registroDetalladoModeloDTO.getAporteObligatorio());
                    if (aporteManual) {
                        cotizante.setEvaluacion(evaluacion);
                    } else {
                        cotizante.setEvaluacionSimulada(evaluacion);
                    }

                    cotizante.setEvaluacionSimulacion(calcularEstadoSimulacion(registroDetalladoModeloDTO));
                }
            }
        }

        logger.info(
                "Fin de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        return cotizantes;
    }

    /**
     * Método encargado de calcular el resultado del estado de la simulación, para
     * saber si fue o no superada.
     * 
     * @param evaluación
     *                   de la simulación.
     * @return resultado de la simulacion
     */
    private EstadoValidacionRegistroAporteEnum calcularEstadoSimulacion(RegistroDetalladoModeloDTO registroDetallado) {
        logger.info("Inicio de método calcularEstadoSimulacion(EvaluacionDTO evaluacion)");

        EstadoValidacionRegistroAporteEnum noOk = EstadoValidacionRegistroAporteEnum.NO_OK;
        if (MarcaRegistroAporteArchivoEnum.NO_VALIDADO_BD
                .equals(registroDetallado.getOutMarcaValidacionRegistroAporte())) {
            return EstadoValidacionRegistroAporteEnum.NO_VALIDADO_BD;
        }
        if (EstadoRegistroAportesArchivoEnum.NO_OK.equals(registroDetallado.getOutEstadoRegistroAporte())
                || registroDetallado.getOutEstadoRegistroAporte() == null
                || registroDetallado.getOutMarcaValidacionRegistroAporte() == null) {
            return noOk;
        }
        if (EstadoValidacionRegistroAporteEnum.NO_CUMPLE.equals(registroDetallado.getOutEstadoValidacionV0())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV2())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV3())) {
            return noOk;
        } else {
            return EstadoValidacionRegistroAporteEnum.OK;
        }
    }

    /**
     * Método encargado de asignar la solicitud a al analista de aportes y guardar
     * la información en la solicitud.
     * 
     * @param solicitudDTO
     *                      solicitud a asignar analista.
     * @param numeroProceso
     *                      número del proceso a atender.
     * @param userDTO
     *                      usuario autenticado.
     * @return id de instancia.
     */
    private Long asignarSolicitudAnalista(Long idSolicitudGlobal, ProcesoEnum proceso, UserDTO userDTO) {
        logger.info(
                "Inicio de método asignarSolicitudAnalista(SolicitudAporteModeloDTO solicitudAporteModeloDTO,UserDTO userDTO)");
        /* se inicia el proceso asignandose la tarea al analista de aportes */
        String destinatario = null;
        if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(proceso) || ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)) {
            destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                    proceso);
        } else {
            /*
             * si se trata de una solicitud de corrección se asinga a si mismo(el analista)
             */
            destinatario = userDTO.getNombreUsuario();
        }
        UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);

        /* Consulta de la solicitud global */
        ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
        consultarSolicitudGlobal.execute();
        SolicitudModeloDTO solicitudDTO = consultarSolicitudGlobal.getResult();

        /* se inicia el proceso de aportes manual asociando la tarea 482 al analista */
        Map<String, Object> parametrosProceso = new HashMap<>();
        parametrosProceso.put("usuarioAnalista", usuarioDTO.getNombreUsuario());
        parametrosProceso.put("idSolicitud", solicitudDTO.getIdSolicitud());
        parametrosProceso.put("numeroRadicado", solicitudDTO.getNumeroRadicacion());
        Long idInstanciaProceso = iniciarProceso(proceso, parametrosProceso);

        /* se actualizan los datos del analista y del proceso a la solciitud. */
        solicitudDTO.setDestinatario(usuarioDTO.getNombreUsuario());
        solicitudDTO.setSedeDestinatario(usuarioDTO.getCodigoSede());
        solicitudDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
        crearActualizarSolicitud(solicitudDTO);
        logger.info(
                "Fin de método asignarSolicitudAnalista(SolicitudAporteModeloDTO solicitudAporteModeloDTO,UserDTO userDTO)");
        return idInstanciaProceso;
    }

    /**
     * Método que se encarga de transformar un objeto RadicacionAporteManual a
     * una SolicitudAporte.
     * 
     * @param radicacionDTO
     *                      radicación a transformar.
     * @return solicitud de aporte.
     */
    private SolicitudAporteModeloDTO transformarSolicitudAporte(RadicacionAporteManualDTO radicacionDTO,
            UserDTO userDTO) {
        logger.info("Inicio de método transformarSolicitudAporte(RadicacionAporteManualDTO radicacionDTO)");
        SolicitudAporteModeloDTO solicitudAporteDTO = new SolicitudAporteModeloDTO();
        solicitudAporteDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudAporteDTO.setFechaCreacion(new Date().getTime());
        solicitudAporteDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudAporteDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudAporteDTO.setTipoIdentificacion(radicacionDTO.getTipoIdentificacion());
        solicitudAporteDTO.setNumeroIdentificacion(radicacionDTO.getNumeroIdentificacion());
        solicitudAporteDTO.setPeriodoPago(radicacionDTO.getPeriodoPago());
        solicitudAporteDTO.setCuentaBancariaRecaudo(radicacionDTO.getCuentaBancariaRecaudo());
        if (radicacionDTO.getRazonSocialAportante() != null) {
            solicitudAporteDTO.setNombreAportante(radicacionDTO.getRazonSocialAportante());
        } else {
            StringBuilder nombre = new StringBuilder();
            nombre.append(radicacionDTO.getPrimerNombre() + " ");
            nombre.append(radicacionDTO.getSegundoNombre() != null ? radicacionDTO.getSegundoNombre() + " " : "");
            nombre.append(radicacionDTO.getPrimerApellido() + " ");
            nombre.append(radicacionDTO.getSegundoApellido() != null ? radicacionDTO.getSegundoApellido() : "");
            solicitudAporteDTO.setNombreAportante(nombre.toString());
        }
        solicitudAporteDTO.setObservacion(radicacionDTO.getObservaciones());
        solicitudAporteDTO.setTipoTransaccion(TipoTransaccionEnum.APORTES_MANUALES);
        solicitudAporteDTO.setTipoSolicitante(radicacionDTO.getTipo());
        logger.info("Inicio de método transformarSolicitudAporte(RadicacionAporteManualDTO radicacionDTO)");
        return solicitudAporteDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de generar nuemro de
     * radicado
     * 
     * @param idSolicitud
     *                             <code>Long</code> El identificador de la
     *                             solicitud
     * @param sedeCajaCompensacion
     *                             <code>String</code> El usuario del sistema
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.info("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        logger.info("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        return radicarSolicitudService.getResult();
    }

    /**
     * Método encargado de invocar el servicio Iniciar proceso.
     * 
     * @param procesoEnum
     *                          proceso que se desea iniciar.
     * @param parametrosProceso
     *                          parametros del proceso.
     * @return id de la instancia del proceso.
     */
    private Long iniciarProceso(ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso) {
        logger.info("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");

        if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(procesoEnum)) {

            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_AM_TIEMPO_PROCESO_SOLICITUD);
            String tiempoPendienteInformacion = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_AM_TIEMPO_PENDIENTE_INFORMACION);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoPendienteInformacion", tiempoPendienteInformacion);

        } else if (ProcesoEnum.DEVOLUCION_APORTES.equals(procesoEnum)) {

            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_DA_TIEMPO_PROCESO_SOLICITUD);
            String tiempoPendienteInformacion = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_DA_TIEMPO_PENDIENTE_INFORMACION);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoPendienteInformacion", tiempoPendienteInformacion);
        } else if (ProcesoEnum.CORRECCION_APORTES.equals(procesoEnum)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_CA_TIEMPO_PROCESO_SOLICITUD);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);

        }

        IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesoNovedadService.execute();
        Long idInstanciaProcesoNovedad = iniciarProcesoNovedadService.getResult();
        logger.info("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
        return idInstanciaProcesoNovedad;

    }

    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     * 
     * @param sedeCaja
     *                    <code>Long</code> el identificador del afiliado
     * @param procesoEnum
     *                    <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     *         caja
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.info("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.info("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        return ejecutarAsignacion.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     * 
     * @param nombreUsuarioCaja
     *                          <code>String</code> El nombre de usuario del
     *                          funcionario de la
     *                          caja que realiza la consulta
     * 
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     *         usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.info("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        logger.info("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return obtenerDatosUsuariosCajaCompensacionService.getResult();
    }

    /**
     * Método que invoca el servicio que crea una solicitud de aporte.
     * 
     * @param solicitudAporteModeloDTO
     *                                 solicitud de aporte a crear.
     * @return solicitud de aporte creada.
     */
    private SolicitudAporteModeloDTO crearSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO) {
        logger.info("Inicio de método crearSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
        CrearSolicitudAporte crearSolicitudAporteService = new CrearSolicitudAporte(solicitudAporteModeloDTO);
        crearSolicitudAporteService.execute();
        solicitudAporteModeloDTO = crearSolicitudAporteService.getResult();
        logger.info("Fin de método crearSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
        return solicitudAporteModeloDTO;
    }

    /**
     * Método que invoca el servicio de almacenamiento/actualización de una
     * solicitud de devolución de aportes
     * 
     * @param solicitudDevolucionAporteDTO
     *                                     La solicitud de devolución a almacenar
     * @return El identificador de la solicitud global creada/actualizada
     */
    private Long crearActualizarSolicitudDevolucionAporte(
            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO) {
        logger.info("Inicio de método crearActualizarSolicitudDevolucionAporte");
        CrearActualizarSolicitudDevolucionAporte crearActualizarSolicitudDevolucionAporte = new CrearActualizarSolicitudDevolucionAporte(
                solicitudDevolucionAporteDTO);
        crearActualizarSolicitudDevolucionAporte.execute();
        Long id = crearActualizarSolicitudDevolucionAporte.getResult();
        logger.info("Fin de método crearActualizarSolicitudDevolucionAporte");
        return id;
    }

    /**
     * Método que invoca el servicio de almacenamiento/actualización de una
     * solicitud de devolución de aportes
     * 
     * @param solicitudCorreccionAporteDTO
     *                                     La solicitud de devolución a almacenar
     * @return El identificador de la solicitud global creada/actualizada
     */
    private Long crearActualizarSolicitudCorreccionAporte(
            SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO) {
        logger.info(
                "Inicio de método crearActualizarSolicitudDevolucionAporte(SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO)");
        CrearActualizarSolicitudCorreccionAporte crearActualizarSolicitudCorreccionAporte = new CrearActualizarSolicitudCorreccionAporte(
                solicitudCorreccionAporteDTO);
        crearActualizarSolicitudCorreccionAporte.execute();
        Long id = crearActualizarSolicitudCorreccionAporte.getResult();
        logger.info(
                "Fin de método crearActualizarSolicitudDevolucionAporte(SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO)");
        return id;
    }

    /**
     * Método que invoca el servicio que consulta una solicitud de aporte.
     * 
     * @param idSolicitudGlobal
     *                          id de la solicitud global.
     * @return solicitud de aporte consultada.
     */
    private SolicitudAporteModeloDTO consultarSolicitudAporte(Long idSolicitudGlobal) {
        logger.info("Inicio de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudAporte consultarSolicitudAporte = new ConsultarSolicitudAporte(idSolicitudGlobal);
        consultarSolicitudAporte.execute();
        SolicitudAporteModeloDTO solicitudAporteModeloDTO = consultarSolicitudAporte.getResult();
        logger.info("Fin de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        return solicitudAporteModeloDTO;
    }

    /**
     * Método que invoca el servicio que consulta una solicitud de corrección
     * aporte.
     * 
     * @param idSolicitudGlobal
     *                          id de la solicitud global.
     * @return solicitud de aporte consultada.
     */
    private SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporte(Long idSolicitudGlobal) {
        logger.info("Inicio de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudCorreccionAporte consultarSolicitudAporte = new ConsultarSolicitudCorreccionAporte(
                idSolicitudGlobal);
        consultarSolicitudAporte.execute();
        SolicitudCorreccionAporteModeloDTO solicitudAporteModeloDTO = consultarSolicitudAporte.getResult();
        logger.info("Fin de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        return solicitudAporteModeloDTO;
    }

    /**
     * Método que invoca el servicio que consulta una solicitud de devolución de
     * aportes
     * 
     * @param idSolicitudGlobal
     *                          Identificador de la solicitud global
     * @return Objeto <code>SolicitudDevolucionAporteModeloDTO</code> con la
     *         información de la solicitud consultada
     */
    private SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporte(Long idSolicitudGlobal) {
        logger.info("Inicio de método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudDevolucionAporte consultarSolicitudDevolucionAporte = new ConsultarSolicitudDevolucionAporte(
                idSolicitudGlobal);
        consultarSolicitudDevolucionAporte.execute();
        SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte
                .getResult();
        logger.info("Fin de método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
        return solicitudDevolucionAporteDTO;
    }

    /**
     * Método que invoca el servicio que crea o actualiza una solicitud global
     * 
     * @param solicitudDTO
     *                     Los datos de la solicitud
     * @return El identificador de la solicitud creada/actualizada
     */
    private Long crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO) {
        logger.info("Inicio de método crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO)");
        CrearActualizarSolicitudGlobal crearActualizarSolicitudGlobal = new CrearActualizarSolicitudGlobal(
                solicitudDTO);
        crearActualizarSolicitudGlobal.execute();
        Long id = crearActualizarSolicitudGlobal.getResult();
        logger.info("Fin de método crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO)");
        return id;
    }

    /**
     * Método que invoca el servicio que actualiza una solicitud de aporte.
     * 
     * @param solicitudAporteModeloDTO
     *                                 solicitud de aporte a actualizar.
     */
    private void actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO) {
        logger.info("Inicio de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
        ActualizarSolicitudAporte actualizarSolicitudAporteService = new ActualizarSolicitudAporte(
                solicitudAporteModeloDTO);
        actualizarSolicitudAporteService.execute();
        logger.info("Fin de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
    }

    /**
     * Método encargado de invocar el servicio para guardar documentos.
     * 
     * @param numeroRadicacion
     *                         número de radicación.
     * @param documentos
     *                         que se desean asociar.
     * @param actividad
     *                         que se esta realizando para guardar los documentos
     */
    private void guardarDocumentos(String numeroRadicado, List<DocumentoAdministracionEstadoSolicitudDTO> documentos,
            ActividadEnum actividad) {
        logger.info("Inicio de método guardarDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos)");
        for (DocumentoAdministracionEstadoSolicitudDTO documentoAdministracionEstadoSolicitudDTO : documentos) {
            documentoAdministracionEstadoSolicitudDTO.setActividad(actividad);
        }
        GuardarDocumentosAdminSolicitudes guardarDocumentoService = new GuardarDocumentosAdminSolicitudes(
                numeroRadicado, documentos);
        guardarDocumentoService.execute();
        logger.info("Fin de método guardarDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos)");
    }

    /**
     * Método que invoca el servicio que guarda los datos temporales.
     * 
     * @param idsolicitud
     *                    id de la solicitud global.
     * @param jsonPayload
     *                    cadena con los datos temporales.
     */
    private void guardarDatosTemporales(Long idsolicitud, String jsonPayload) {
        logger.info("Inicio de método guardarDatosTemporales(Long idsolicitud, String jsonPayload)");
        GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(idsolicitud, jsonPayload);
        datosTemporalService.execute();
        logger.info("Fin de método guardarDatosTemporales(Long idsolicitud, String jsonPayload)");
    }

    /**
     * Método que invoca el servicio que consulta los datos temporales.
     * 
     * @param idSolicitud
     *                    id de la solicitud global.
     * @return jsonPayload con los datos temporales.
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        logger.info("Inicio de método consultarDatosTemporales(Long idSolicitud)");
        ConsultarDatosTemporales consultarDatosNovedad = new ConsultarDatosTemporales(idSolicitud);
        consultarDatosNovedad.execute();
        logger.info("Fin de método consultarDatosTemporales(Long idSolicitud)");
        return consultarDatosNovedad.getResult();
    }

    /**
     * Método que invoca el servicio que guarda la información faltante.
     * 
     * @param infoFaltante
     *                     información faltante a guardar.
     */
    private void guardarInformacionFaltante(List<InformacionFaltanteAportanteModeloDTO> infoFaltante) {
        logger.info(
                "Inicio de método guardarInformacionFaltante(List<InformacionFaltanteAportanteModeloDTO> infoFaltante)");
        CrearInfoFaltante crearInfoFalanteService = new CrearInfoFaltante(infoFaltante);
        crearInfoFalanteService.execute();
        logger.info(
                "Fin de método guardarInformacionFaltante(List<InformacionFaltanteAportanteModeloDTO> infoFaltante)");
    }

    /**
     * Método que invoca el servicio que guardar trazabilidad.
     * 
     * @param trazabilidadDTO
     *                        trazabilidad a guardar.
     */
    private void guardarTrazabilidad(RegistroEstadoAporteModeloDTO trazabilidadDTO) {
        logger.info("Inicio de método guardarTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
        CrearTrazabilidad crearTrazabilidadService = new CrearTrazabilidad(trazabilidadDTO);
        crearTrazabilidadService.execute();
        logger.info("Fin de método guardarTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
    }

    /**
     * Método que invoca el servicio que actualiza el estado de una solicitud
     * 
     * @param idSolicitud
     *                    Identificadro de la solicitud global
     * @param estado
     *                    Estado de la solicitud
     * @param proceso
     *                    Proceso de negocio en aportes
     */
    private void actualizarEstadoSolicitud(Long idSolicitud, EstadoSolicitudAporteEnum estado, ProcesoEnum proceso) {
        logger.info("Inicio de método actualizarEstadoSoliciutd(Long idSolicitud, EstadoSolicitudAporteEnum estado)");

        if (proceso.equals(ProcesoEnum.PAGO_APORTES_MANUAL)) {
            ActualizarEstadoSolicitud service = new ActualizarEstadoSolicitud(idSolicitud, estado);
            service.execute();
        } else if (proceso.equals(ProcesoEnum.DEVOLUCION_APORTES)) {
            ActualizarEstadoSolicitudDevolucion service = new ActualizarEstadoSolicitudDevolucion(idSolicitud, estado);
            service.execute();
        } else if (proceso.equals(ProcesoEnum.CORRECCION_APORTES)) {
            ActualizarEstadoSolicitudCorreccion service = new ActualizarEstadoSolicitudCorreccion(idSolicitud, estado);
            service.execute();
        }

        logger.info("Fin de método actualizarEstadoSoliciutd(Long idSolicitud, EstadoSolicitudAporteEnum estado)");
    }

    /**
     * Método encargado de llamar el cliente de obtener un archivo
     * 
     * @param archivoId,
     *                   identificador del archivo
     * @return devuelve el dto del archivo
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.info("Inicia obtenerArchivo(String)");
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        logger.info("Finaliza obtenerArchivo(String)");
        return consultarArchivo.getResult();
    }

    /**
     * Método encargado de llamar el cliente de validar archivo pago manual de
     * aportes
     * 
     * @param archivo,
     *                 archivo a realizar las validaciones
     * @return retorna el dto de resultadoArchivoAporte
     */
    private ResultadoArchivoAporteDTO validarArchivoPagoManualAportes(InformacionArchivoDTO archivo) {
        logger.info("Inicia validarArchivoPagoManualAportes(InformacionArchivoDTO archivo)");
        ValidarArchivoPagoManualAportes validarArchivo = new ValidarArchivoPagoManualAportes(archivo);
        validarArchivo.execute();
        logger.info("Fin validarArchivoPagoManualAportes(InformacionArchivoDTO archivo)");
        return validarArchivo.getResult();
    }

    /**
     * Método encargado de invocar el servicio que crea o actualiza un registro
     * general.
     * 
     * @param registroGeneralDTO
     *                           registro general dto.
     * @return id del registro guardado o actualizado.
     */
    private Long guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO) {
        logger.info("Inicia guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
        CrearRegistroGeneral crearRegGeneralService = new CrearRegistroGeneral(registroGeneralDTO);
        crearRegGeneralService.execute();
        logger.info("Fin guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
        return crearRegGeneralService.getResult(); 
    }

    /**
     * Método encargado de invocar el servicio que crea o actualiza un registro
     * detallado.
     * 
     * @param registroDetalladoDTO
     *                             registro detallado dto.
     * @return id del resgistro detallado.
     */
    private Long guardarRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO) {
        logger.info("Inicia guardarRegistroDetallado(RegistroGeneralModeloDTO registroGeneralDTO)");
        CrearRegistroDetallado crearRegDetalladoService = new CrearRegistroDetallado(registroDetalladoDTO);
        crearRegDetalladoService.execute();
        logger.info("Fin guardarRegistroDetallado(RegistroGeneralModeloDTO registroGeneralDTO)");
        return crearRegDetalladoService.getResult();
    }

    /**
     * Método encargado de invocar el servicio que crea una transacción.
     * 
     * @return id de la transacción.
     */
    private Long crearTransaccion() {
        logger.info("Inicio de método crearTransaccion()");
        CrearTransaccion crearTransaccionService = new CrearTransaccion();
        crearTransaccionService.execute();
        logger.info("Fin de método crearTransaccion()");
        return crearTransaccionService.getResult();
    }

    /**
     * Método encargado de invocar el servicio del armado staging.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     */
    private void ejecutarArmadoStaging(Long idTransaccion) {
        logger.info("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
        EjecutarArmadoStaging ejecutarArmadoStagingService = new EjecutarArmadoStaging(idTransaccion);
        ejecutarArmadoStagingService.execute();
        logger.info("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
    }

    /**
     * Método encargado de invocar el servicio.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     */
    private void simularFasePila2(Long idTransaccion) {
        logger.info("Inicio de método simularFasePila2(Long idTransaccion)");
        SimularFasePila2 simularService = new SimularFasePila2(idTransaccion);
        simularService.execute();
        logger.info("Fin de método simularFasePila2(Long idTransaccion)");
    }

    /**
     * Método que se encarga de transformar un objeto
     * <code>SolicitudDevolucionDTO</code> a un
     * <code>SolicitudDevolucionAporteModeloDTO</code>
     * 
     * @param solicitudDevolucionDTO
     *                               El objeto a transformar
     * @param userDTO
     *                               Información del usuario, tomada del contexto
     * @return El objeto <code>SolicitudDevolucionAporteModeloDTO</code> equivalente
     */
    private SolicitudDevolucionAporteModeloDTO transformarSolicitudDevolucionAporte(
            SolicitudDevolucionDTO solicitudDevolucionDTO,
            UserDTO userDTO) {
        logger.info(
                "Inicio de método transformarSolicitudDevolucionAporte(SolicitudDevolucionDTO solicitudDevolucionDTO, UserDTO userDTO)");
        SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = new SolicitudDevolucionAporteModeloDTO();

        // Datos de la solicitud global
        solicitudDevolucionAporteDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudDevolucionAporteDTO.setFechaCreacion(new Date().getTime());
        solicitudDevolucionAporteDTO.setFechaRadicacion(new Date().getTime());
        solicitudDevolucionAporteDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudDevolucionAporteDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudDevolucionAporteDTO.setObservacion(solicitudDevolucionDTO.getComentarios());
        solicitudDevolucionAporteDTO.setTipoTransaccion(TipoTransaccionEnum.DEVOLUCION_APORTES);

        // Datos de la solicitud de devolución
        solicitudDevolucionAporteDTO.setTipoSolicitante(solicitudDevolucionDTO.getTipoSolicitante());
        PersonaModeloDTO personaDTO = consultarPersona(solicitudDevolucionDTO.getNumeroIdentificacion(),
                solicitudDevolucionDTO.getTipoIdentificacion());
        solicitudDevolucionAporteDTO.setIdPersona(personaDTO.getIdPersona());

        logger.info(
                "Fin de método transformarSolicitudDevolucionAporte(SolicitudDevolucionDTO solicitudDevolucionDTO, UserDTO userDTO)");
        return solicitudDevolucionAporteDTO;
    }

    /**
     * Método que consulta los datos de una persona, por tipo y número de documento
     * 
     * @param numeroIdentificacion
     *                             Número de identificación
     * @param tipoIdentificacion
     *                             Tipo de identificación
     * @return Objeto <code>PersonaModeloDTO</code> con los datos de persona
     */
    private PersonaModeloDTO consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        logger.info(
                "Inicio de método consultarPersona(String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion)");
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(numeroIdentificacion,
                tipoIdentificacion);
        consultarDatosPersona.execute();
        PersonaModeloDTO personaDTO = consultarDatosPersona.getResult();
        logger.info(
                "Fin de método consultarPersona(String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion)");
        return personaDTO;
    }

    /**
     * Método que se encarga de transformar un objeto SolicitudCorreccionDTO a una
     * SolicitudAporte.
     * 
     * @param solicitudCorreccion
     *                            radicación a transformar.
     * @return solicitud de aporte.
     */
    private SolicitudCorreccionAporteModeloDTO transformarSolicitudCorreccion(
            SolicitudCorreccionDTO solicitudCorreccion, UserDTO userDTO) {
        logger.info(
                "Inicio de método transformarSolicitudCorreccion(SolicitudCorreccionDTO solicitudCorreccion, UserDTO userDTO)");
        SolicitudCorreccionAporteModeloDTO solicitudCorreccionDTO = new SolicitudCorreccionAporteModeloDTO();
        solicitudCorreccionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudCorreccionDTO.setFechaCreacion(new Date().getTime());
        solicitudCorreccionDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudCorreccionDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudCorreccionDTO.setTipoSolicitante(solicitudCorreccion.getTipoSolicitante());
        solicitudCorreccionDTO.setTipoSolicitante(solicitudCorreccion.getTipoSolicitante());
        PersonaModeloDTO personaDTO = consultarPersona(solicitudCorreccion.getNumeroIdentificacion(),
                solicitudCorreccion.getTipoIdentificacion());
        solicitudCorreccionDTO.setIdPersona(personaDTO.getIdPersona());
        solicitudCorreccionDTO.setIdAporteGeneralNuevo(solicitudCorreccion.getIdAporte());
        solicitudCorreccionDTO.setTipoTransaccion(TipoTransaccionEnum.CORRECCION_APORTES);
        logger.info(
                "Inicio de método transformarSolicitudCorreccion(SolicitudCorreccionDTO solicitudCorreccion, UserDTO userDTO)");
        return solicitudCorreccionDTO;
    }

    /**
     * Método encargado de invocar el servicio del borrado staging.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     */
    private void ejecutarBorradoStaging(Long idTransaccion) {
        logger.info("Inicio de método ejecutarBorradoStaging(Long idTransaccion)");
        EjecutarBorradoStaging ejecutarBorradoStagingService = new EjecutarBorradoStaging(idTransaccion);
        ejecutarBorradoStagingService.execute();
        logger.info("Fin de método ejecutarBorradoStaging(Long idTransaccion)");
    }

    /**
     * Método encargado de invocar el servicio que consulta los registros detallados
     * por id de registro general.
     * 
     * @param idRegistroGeneral
     *                          id del registro general.
     */
    private List<RegistroDetalladoModeloDTO> consultarRegistroDetallado(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idRegistroGeneral)");
        ConsultarRegistroDetallado consultarRegistroDetalladoService = new ConsultarRegistroDetallado(
                idRegistroGeneral);
        consultarRegistroDetalladoService.execute();
        List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetalladoService.getResult();
        logger.info("Fin de método consultarRegistroDetallado(Long idRegistroGeneral)");
        return registroDetalladoList;
    }

    /**
     * Método encargado de invocar el servicio que consulta el registro general por
     * id de registro general.
     * 
     * @param idSolicitud
     *                    id de la solicitud.
     */
    private RegistroGeneralModeloDTO consultarRegistroGeneral(Long idSolicitud, Boolean limitar,
            Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        ConsultarRegistroGeneralLimitadoIdRegGen consultarRegistroGeneralService = new ConsultarRegistroGeneralLimitadoIdRegGen(
                idSolicitud, idRegistroGeneral);
        consultarRegistroGeneralService.execute();
        registroGeneralDTO = consultarRegistroGeneralService.getResult();
        logger.info("Fin de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        return registroGeneralDTO;
    }

    /**
     * Método encargado de invocar el servicio que consulta el registro general por
     * id de registro general.
     * 
     * @param idSolicitud
     *                    id de la solicitud.
     */
    private RegistroGeneralModeloDTO consultarRegistroGeneral(Long idSolicitud, Boolean limitar) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        if (!limitar) {
            ConsultarRegistroGeneral consultarRegistroGeneralService = new ConsultarRegistroGeneral(idSolicitud);
            consultarRegistroGeneralService.execute();
            registroGeneralDTO = consultarRegistroGeneralService.getResult();
        } else {
            ConsultarRegistroGeneralLimitado consultarRegistroGeneralService = new ConsultarRegistroGeneralLimitado(
                    idSolicitud);
            consultarRegistroGeneralService.execute();
            registroGeneralDTO = consultarRegistroGeneralService.getResult();
        }
        logger.info("Fin de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        return registroGeneralDTO;
    }

    /**
     * Método para enviar una notificación mediante un comunicado.
     * 
     * @param notificacion
     */
    private void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion) {
        EnviarNotificacionComunicado enviarNotificacion = new EnviarNotificacionComunicado(notificacion);
        enviarNotificacion.execute();
    }

    /**
     * Método que termina una tarea del BPM
     * 
     * @param idTarea,
     *                 es el identificador de la tarea
     * @param params,
     *                 son los parámetros de la tarea
     */
    private void terminarTarea(Long idTarea, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
        terminarTarea.execute();
    }

    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de
     * relacionar o registrar aportes y novedades.
     * 
     * @param idTransaccion
     *                      id de la transacción.
     */
    private void registrarRelacionarAportesNovedades(Long idTransaccion) {
        logger.info("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
        RegistrarRelacionarAportesNovedades registrarRelacionarService = new RegistrarRelacionarAportesNovedades(
                idTransaccion,
                Boolean.TRUE, Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.info("Fin de método registrarRelacionarAportesNovedades(Long idTransaccion)");
    }

    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de
     * relacionar o registrar novedades.
     * 
     * @param idTransaccion
     *                      id de la transacción.
     */
    private void registrarRelacionarNovedades(Long idTransaccion) {
        logger.info("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
        RegistrarRelacionarNovedades registrarRelacionarService = new RegistrarRelacionarNovedades(idTransaccion,
                Boolean.TRUE,
                Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.info("Fin de método registrarRelacionarAportesNovedades(Long idTransaccion)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * finalizarGestionAnalistaContable(java.lang.Long, java.lang.Long,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void finalizarGestionAnalistaContable(Long idSolicitud, Long idTarea, UserDTO userDTO) {
        try {
            logger.info("Inicio de método finalizarGestionAnalista(Long idSolicitud, UserDTO userDTO)");
            SolicitudDevolucionAporteModeloDTO solicitudDevolucion = consultarSolicitudDevolucionAporte(idSolicitud);

            /*
             * Se lleva a cabo el registro de la traza generada por el cambio en la
             * Solicitud, en este caso, un cambio en el estado de la
             * solicitud.
             */
            actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(), ProcesoEnum.DEVOLUCION_APORTES,
                    EstadoSolicitudAporteEnum.PAGO_PROCESADO, null, userDTO);

            /*
             * Se vuelve a llamar al método de actualizarSolicitudTrazabilidad con el fin de
             * generar un histórico debido al cierre de la
             * solicitud.
             */
            actualizarSolicitudTrazabilidad(solicitudDevolucion.getIdSolicitud(), ProcesoEnum.DEVOLUCION_APORTES,
                    EstadoSolicitudAporteEnum.CERRADA, null, userDTO);

            /*
             * Generar y enviar automáticamente al solicitante (sin editar) el comunicado
             * 111.
             */
            NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
            notificacion.setIdSolicitud(idSolicitud);
            notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_PAG_DVL_APT);
            notificacion.setTipoTx(TipoTransaccionEnum.DEVOLUCION_APORTES);
            notificacion.setProcesoEvento(TipoTransaccionEnum.DEVOLUCION_APORTES.getProceso().toString());
            // Enviar mapa para el comunicado
            DevolucionDTO devolucionDTO = consultarDevolucionTemporal(idSolicitud);
            Map<String, String> params = new HashMap<>();
            List<AnalisisDevolucionDTO> analisisDevolucionDTO = devolucionDTO.getAnalisis();
            if (analisisDevolucionDTO != null && !analisisDevolucionDTO.isEmpty()) {
                for (AnalisisDevolucionDTO analisis : analisisDevolucionDTO) {
                    if (analisis.getCotizanteDTO() != null && !analisis.getCotizanteDTO().isEmpty()) {
                        for (CotizanteDTO cotizante : analisis.getCotizanteDTO()) {
                            if (analisis.getNumPlanilla() != null && analisis.getTipoPlanilla() != null) {
                                params.put("planilla_" + cotizante.getIdCotizante(), analisis.getNumPlanilla());
                                params.put("tipoPlanilla_" + cotizante.getIdCotizante(),
                                        analisis.getTipoPlanilla().getCodigo());
                            }
                        }
                    }
                }
            }
            notificacion.setParams(params);
            enviarNotificacionComunicado(notificacion);

            /* Quitar la solicitud de la bandeja de trabajo del Analista Contable. */
            this.terminarTarea(idTarea, null);

        } catch (Exception e) {
            logger.error("Finaliza con Error método finalizarGestionAnalista(Long idSolicitud, UserDTO userDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#gestionarCotizante(com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum,
     *      java.lang.Long, com.asopagos.dto.aportes.CotizanteDTO)
     */
    @Override
    public CotizanteDTO gestionarCotizante(ModalidadRecaudoAporteEnum modalidadRecaudo, Long idPlanilla,
            CotizanteDTO cotizanteDTO) {
        String firmaServicio = "AportesManualesCompositeBusiness.gestionDeCotizante(ModalidadRecaudoAporteEnum, Long, CotizanteDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CotizanteDTO result = gestionDeCotizante(modalidadRecaudo, idPlanilla, cotizanteDTO, Boolean.FALSE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#gestionarCotizantes(com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum,
     *      java.lang.Long, com.asopagos.dto.aportes.CotizanteDTO)
     */
    @Override
    public List<CotizanteDTO> gestionarCotizantes(ModalidadRecaudoAporteEnum modalidadRecaudo, Long idPlanilla,
            List<CotizanteDTO> cotizanteDTO) {
        String firmaServicio = "AportesManualesCompositeBusiness.gestionDeCotizante(ModalidadRecaudoAporteEnum, Long, List<CotizanteDTO>, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CotizanteDTO> result = gestionDeCotizantes(modalidadRecaudo, idPlanilla, cotizanteDTO, Boolean.FALSE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#gestionarCotizanteV360(com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum,
     *      java.lang.Long, com.asopagos.dto.aportes.CotizanteDTO)
     */
    @Override
    public CotizanteDTO gestionarCotizanteV360(ModalidadRecaudoAporteEnum modalidadRecaudo, Long idPlanilla,
            CotizanteDTO cotizanteDTO) {
        String firmaServicio = "AportesManualesCompositeBusiness.gestionarCotizanteV360(ModalidadRecaudoAporteEnum, Long, CotizanteDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CotizanteDTO result = gestionDeCotizante(modalidadRecaudo, idPlanilla, cotizanteDTO, Boolean.TRUE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Método que consulta la información necesaria para la gestión de un
     * aporte a nivel de cotizante dependiendo si se llama desde vista 360 o no
     * 
     * @param modalidadRecaudo
     *                         Modalidad de recaudo (Pila manual, PILA automático o
     *                         Aporte
     *                         Manual)
     * @param idPlanilla
     *                         Número de planilla, si el aporte se hizo por PILA
     * @param cotizanteDTO
     *                         Información del cotizante
     * @param esVista360
     *                         Indica que se está solicitando la gesttión desde una
     *                         vista 360
     * @return Información del cotizante con la información actualizada para
     *         realizar la gestión del aporte
     */
    private CotizanteDTO gestionDeCotizante(ModalidadRecaudoAporteEnum modalidadRecaudo, Long idPlanilla,
            CotizanteDTO cotizanteDTO,
            Boolean esVista360) {
        String firmaMetodo = "AportesManualesCompositeBusiness.gestionDeCotizante(ModalidadRecaudoAporteEnum, Long, CotizanteDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Consulta el registro en AporteDetallado
        AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado(cotizanteDTO.getIdCotizante());

        // Consulta el registro en RegistroDetallado
        RegistroDetalladoModeloDTO registroDetalladoDTO = consultarRegistroDetalladoPorId(
                aporteDetalladoDTO.getIdRegistroDetallado());

        /*
         * sí el aporte es por dependiente, pero no se agrega el tipo y número de id del
         * empleador en la
         * solicitud, se consulta del aporte
         */
        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(aporteDetalladoDTO.getTipoCotizante())
                && (cotizanteDTO.getTipoIdentificacionAportante() == null
                        || cotizanteDTO.getNumeroIdentificacionAportante() == null)) {
            // se consulta el aporte general
            AporteGeneralModeloDTO aporteGeneral = consultarAporteGeneral(aporteDetalladoDTO.getIdAporteGeneral());
            // se consulta la persona por id empresa
            EmpresaModeloDTO empresa = consultarEmpresaPorId(aporteGeneral.getIdEmpresa());
            cotizanteDTO.setTipoIdentificacionAportante(empresa.getTipoIdentificacion());
            cotizanteDTO.setNumeroIdentificacionAportante(empresa.getNumeroIdentificacion());
        }

        // Si el aporte fue por PILA, consulta la información del archivo
        IndicePlanillaModeloDTO indicePlanillaDTO = null;

        if (!modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.MANUAL)) {
            indicePlanillaDTO = consultarIndicePlanillaNumeroAportante(idPlanilla,cotizanteDTO.getIdRegistro());
        }

        // Consulta la solicitud de aportes
        SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAportePorIdAporte(
                aporteDetalladoDTO.getIdAporteGeneral());
        EstadoSolicitudAporteEnum estadoSolucitud = null;

        if (solicitudAporteDTO == null) {
            SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = consultarSolicitudCorreccionAporteAporteGeneral(
                    aporteDetalladoDTO.getIdAporteGeneral());
            estadoSolucitud = solicitudCorreccionAporteDTO.getEstadoSolicitud();
        } else {
            estadoSolucitud = solicitudAporteDTO.getEstadoSolicitud();
        }

        // Consulta el registro en AporteGeneral
        AporteGeneralModeloDTO aporteGeneralDTO = consultarAporteGeneral(aporteDetalladoDTO.getIdAporteGeneral());

        // Boolean esReproceso = indicePlanillaDTO != null ?
        // indicePlanillaDTO.getTipoArchivo().isReproceso() : Boolean.FALSE;
        // el cliente indica que nunca se debería dar el caso de una aporte reemplazado
        Boolean esReproceso = Boolean.FALSE;

        // Consulta de información pestaña "Histórico", sección "Evaluación de
        // condiciones"
        HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporte(aporteDetalladoDTO.getEstadoAporteCotizante(),
                modalidadRecaudo,
                indicePlanillaDTO, esReproceso, estadoSolucitud, cotizanteDTO.getTipoIdentificacion(),
                cotizanteDTO.getNumeroIdentificacion(), aporteGeneralDTO.getPeriodoAporte());
        cotizanteDTO.setHistorico(historicoDTO);

        // Consulta de información pestaña "Novedades"
        List<NovedadCotizanteDTO> listaNovedadesCotizanteDTO = consultarNovedadesCotizanteAporte(aporteDetalladoDTO);
        cotizanteDTO.setNovedades(listaNovedadesCotizanteDTO);

        // Consulta de información pestaña "Aportes"
        AportesDTO aporteDTO = consultarAportesCotizante(aporteDetalladoDTO, registroDetalladoDTO, esVista360);
        cotizanteDTO.setAportes(aporteDTO);
        EvaluacionDTO evaluacionDTO = obtenerEvaluacionVigente(aporteDetalladoDTO, registroDetalladoDTO);
        cotizanteDTO.setEvaluacion(evaluacionDTO);

        // Consulta datos adicionales del cotizante
        DatosCotizanteDTO datosCotizanteDTO = new DatosCotizanteDTO();
        datosCotizanteDTO.setTipoIdentificacion(cotizanteDTO.getTipoIdentificacion());
        datosCotizanteDTO.setNumeroIdentificacion(cotizanteDTO.getNumeroIdentificacion());
        datosCotizanteDTO.setTipoIdenficacionEmpleador(cotizanteDTO.getTipoIdentificacionAportante());
        datosCotizanteDTO.setNumeroIdentificacionEmpleador(cotizanteDTO.getNumeroIdentificacionAportante());
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante = transformarTipoAfiliado(cotizanteDTO.getTipoAfiliado());
        datosCotizanteDTO = consultarDatosCotizante(datosCotizanteDTO, tipoSolicitante);
        cotizanteDTO.setFechaIngreso(datosCotizanteDTO.getFechaIngreso());
        cotizanteDTO.setFechaRetiro(datosCotizanteDTO.getFechaRetiro());
        cotizanteDTO.setTipoAfiliado(datosCotizanteDTO.getTipoAfiliado());
        cotizanteDTO.setEstado(datosCotizanteDTO.getEstadoAfiliado());
        List<BigDecimal> resultados = new ArrayList();
        logger.info("idporteDetallado" + aporteDetalladoDTO.getId());
        resultados.addAll(consultasCore.consultarSalarioeIbcNuevo(aporteDetalladoDTO.getId()));
        AportesDTO aporte = new AportesDTO();
        aporte = cotizanteDTO.getAportes();
        logger.info("resultados.get(0)" + resultados.get(0));
        logger.info("resultados.get(1)" + resultados.get(1));
        BigDecimal result = consultasCore.consultarAporteObligatorio(aporteDetalladoDTO.getId());
        BigDecimal result2 = consultasCore.consultarAporteObligatorioAnt(cotizanteDTO.getIdRegistro());
        logger.info("aporteObligatorio"+result);
        aporte.setAporteObligatorio(result2);
        aporte.setAporteObligatorioNuevo(result);        
        aporte.setSalarioBasicoNuevo(resultados.get(0));
        aporte.setIbcNuevo(resultados.get(1));
        cotizanteDTO.setAportes(aporte);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cotizanteDTO;
    }

    /**
     * Método que consulta la información necesaria para la gestión de un
     * aporte a nivel de cotizante dependiendo si se llama desde vista 360 o no
     * 
     * @param modalidadRecaudo
     *                         Modalidad de recaudo (Pila manual, PILA automático o
     *                         Aporte
     *                         Manual)
     * @param idPlanilla
     *                         Número de planilla, si el aporte se hizo por PILA
     * @param cotizanteDTO
     *                         Información del cotizante
     * @param esVista360
     *                         Indica que se está solicitando la gesttión desde una
     *                         vista 360
     * @return Información del cotizante con la información actualizada para
     *         realizar la gestión del aporte
     */
    private List<CotizanteDTO> gestionDeCotizantes(ModalidadRecaudoAporteEnum modalidadRecaudo, Long idPlanilla,
            List<CotizanteDTO> cotizantesDTO,
            Boolean esVista360) {
        String firmaMetodo = "AportesManualesCompositeBusiness.gestionDeCotizantes(ModalidadRecaudoAporteEnum, Long, CotizanteDTO, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        for (CotizanteDTO cotizanteDTO : cotizantesDTO) {

            // Consulta el registro en AporteDetallado
            AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado(cotizanteDTO.getIdCotizante());

            // Consulta el registro en RegistroDetallado
            RegistroDetalladoModeloDTO registroDetalladoDTO = consultarRegistroDetalladoPorId(
                    aporteDetalladoDTO.getIdRegistroDetallado());

            /*
             * sí el aporte es por dependiente, pero no se agrega el tipo y número de id del
             * empleador en la
             * solicitud, se consulta del aporte
             */
            if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(aporteDetalladoDTO.getTipoCotizante())
                    && (cotizanteDTO.getTipoIdentificacionAportante() == null
                            || cotizanteDTO.getNumeroIdentificacionAportante() == null)) {
                // se consulta el aporte general
                AporteGeneralModeloDTO aporteGeneral = consultarAporteGeneral(aporteDetalladoDTO.getIdAporteGeneral());
                // se consulta la persona por id empresa
                EmpresaModeloDTO empresa = consultarEmpresaPorId(aporteGeneral.getIdEmpresa());
                cotizanteDTO.setTipoIdentificacionAportante(empresa.getTipoIdentificacion());
                cotizanteDTO.setNumeroIdentificacionAportante(empresa.getNumeroIdentificacion());
            }

            // Si el aporte fue por PILA, consulta la información del archivo
            IndicePlanillaModeloDTO indicePlanillaDTO = null;

            if (!modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.MANUAL)) {
                indicePlanillaDTO = consultarIndicePlanilla(idPlanilla);
            }

            // Consulta la solicitud de aportes
            SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAportePorIdAporte(
                    aporteDetalladoDTO.getIdAporteGeneral());
            EstadoSolicitudAporteEnum estadoSolucitud = null;

            if (solicitudAporteDTO == null) {
                SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = consultarSolicitudCorreccionAporteAporteGeneral(
                        aporteDetalladoDTO.getIdAporteGeneral());
                estadoSolucitud = solicitudCorreccionAporteDTO.getEstadoSolicitud();
            } else {
                estadoSolucitud = solicitudAporteDTO.getEstadoSolicitud();
            }

            // Consulta el registro en AporteGeneral
            AporteGeneralModeloDTO aporteGeneralDTO = consultarAporteGeneral(aporteDetalladoDTO.getIdAporteGeneral());

            // Boolean esReproceso = indicePlanillaDTO != null ?
            // indicePlanillaDTO.getTipoArchivo().isReproceso() : Boolean.FALSE;
            // el cliente indica que nunca se debería dar el caso de una aporte reemplazado
            Boolean esReproceso = Boolean.FALSE;

            // Consulta de información pestaña "Histórico", sección "Evaluación de
            // condiciones"
            HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporte(
                    aporteDetalladoDTO.getEstadoAporteCotizante(), modalidadRecaudo,
                    indicePlanillaDTO, esReproceso, estadoSolucitud, cotizanteDTO.getTipoIdentificacion(),
                    cotizanteDTO.getNumeroIdentificacion(), aporteGeneralDTO.getPeriodoAporte());
            cotizanteDTO.setHistorico(historicoDTO);

            // Consulta de información pestaña "Novedades"
            List<NovedadCotizanteDTO> listaNovedadesCotizanteDTO = consultarNovedadesCotizanteAporte(
                    aporteDetalladoDTO);
            cotizanteDTO.setNovedades(listaNovedadesCotizanteDTO);

            // Consulta de información pestaña "Aportes"
            AportesDTO aporteDTO = consultarAportesCotizante(aporteDetalladoDTO, registroDetalladoDTO, esVista360);
            cotizanteDTO.setAportes(aporteDTO);
            EvaluacionDTO evaluacionDTO = obtenerEvaluacionVigente(aporteDetalladoDTO, registroDetalladoDTO);
            cotizanteDTO.setEvaluacion(evaluacionDTO);

            // Consulta datos adicionales del cotizante
            DatosCotizanteDTO datosCotizanteDTO = new DatosCotizanteDTO();
            datosCotizanteDTO.setTipoIdentificacion(cotizanteDTO.getTipoIdentificacion());
            datosCotizanteDTO.setNumeroIdentificacion(cotizanteDTO.getNumeroIdentificacion());
            datosCotizanteDTO.setTipoIdenficacionEmpleador(cotizanteDTO.getTipoIdentificacionAportante());
            datosCotizanteDTO.setNumeroIdentificacionEmpleador(cotizanteDTO.getNumeroIdentificacionAportante());
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante = transformarTipoAfiliado(
                    cotizanteDTO.getTipoAfiliado());
            datosCotizanteDTO = consultarDatosCotizante(datosCotizanteDTO, tipoSolicitante);
            cotizanteDTO.setFechaIngreso(datosCotizanteDTO.getFechaIngreso());
            cotizanteDTO.setFechaRetiro(datosCotizanteDTO.getFechaRetiro());
            cotizanteDTO.setTipoAfiliado(datosCotizanteDTO.getTipoAfiliado());
            cotizanteDTO.setEstado(datosCotizanteDTO.getEstadoAfiliado());
            List<BigDecimal> resultados = new ArrayList();
            logger.info("idporteDetallado" + aporteDetalladoDTO.getId());
            resultados.addAll(consultasCore.consultarSalarioeIbcNuevo(aporteDetalladoDTO.getId()));
            AportesDTO aporte = new AportesDTO();
            aporte = cotizanteDTO.getAportes();
            logger.info("resultados.get(0)" + resultados.get(0));
            logger.info("resultados.get(1)" + resultados.get(1));
            BigDecimal result = consultasCore.consultarAporteObligatorio(aporteDetalladoDTO.getId());
            logger.info("aporteObligatorio"+result);
            aporte.setSalarioBasicoNuevo(resultados.get(0));
            aporte.setAporteObligatorio(result);
            aporte.setIbcNuevo(resultados.get(1));
            cotizanteDTO.setAportes(aporte);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cotizantesDTO;
    }

    /**
     * @param idEmpresa
     * @return
     */
    private EmpresaModeloDTO consultarEmpresaPorId(Long idEmpresa) {
        String firmaMetodo = "AportesManualesCompositeBusiness.consultarEmpresaPorId(Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarEmpresaPorId consulta = new ConsultarEmpresaPorId(idEmpresa);
        consulta.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return consulta.getResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * gestionarAporteSinDetalle(com.asopagos.dto.
     * AnalisisDevolucionDTO)
     */
    @Override
    public AnalisisDevolucionDTO gestionarAporteSinDetalle(AnalisisDevolucionDTO analisisDevolucionDTO) {
        try {
            logger.info("Inicio de método gestionarAporteSinDetalle");

            // Consulta el registro en AporteGeneral
            AporteGeneralModeloDTO aporteGeneralDTO = consultarAporteGeneral(analisisDevolucionDTO.getIdAporte());

            // Si el aporte fue por PILA, consulta la información del archivo
            IndicePlanillaModeloDTO indicePlanillaDTO = null;

            if (analisisDevolucionDTO.getNumPlanilla() != null) {
                indicePlanillaDTO = consultarIndicePlanilla(Long.parseLong(analisisDevolucionDTO.getNumPlanilla()));
            }

            // Consulta la solicitud de aportes
            SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAportePorIdAporte(
                    analisisDevolucionDTO.getIdAporte());
            EstadoSolicitudAporteEnum estadoSolucitud = null;

            if (solicitudAporteDTO == null) {
                SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = consultarSolicitudCorreccionAporteAporteGeneral(
                        analisisDevolucionDTO.getIdAporte());
                estadoSolucitud = solicitudCorreccionAporteDTO.getEstadoSolicitud();
            } else {
                estadoSolucitud = solicitudAporteDTO.getEstadoSolicitud();
            }

            // Consulta de información pestaña "Histórico", sección "Evaluación de
            // condiciones"
            HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporte(aporteGeneralDTO.getEstadoAporteAportante(),
                    analisisDevolucionDTO.getMetodo(), indicePlanillaDTO,
                    analisisDevolucionDTO.getTieneModificaciones(), estadoSolucitud,
                    analisisDevolucionDTO.getTipoIdentificacion(), analisisDevolucionDTO.getNumeroIdentificacion(),
                    aporteGeneralDTO.getPeriodoAporte());
            analisisDevolucionDTO.setHistorico(historicoDTO);

            logger.info("Fin de método gestionarAporteSinDetalle");
            return analisisDevolucionDTO;
        } catch (Exception e) {
            logger.error("Finaliza con error el método gestionarAporteSinDetalle", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Simula el proceso de devolución de manera temporal para una solicitud global dada.
     * Este método encapsula varios pasos críticos en el proceso de simulación de devoluciones,
     * incluyendo la consulta de datos temporales, el procesamiento en paralelo de múltiples
     * devoluciones y la actualización de los resultados de la simulación.
     *
     * La simulación se realiza en un entorno temporal, permitiendo realizar ajustes y validar
     * resultados sin afectar los datos permanentes. Este enfoque facilita la evaluación de
     * diferentes escenarios de devolución antes de aplicar cualquier cambio en el sistema de
     * manera definitiva.
     *
     * Al finalizar la simulación, los resultados son almacenados temporalmente para su consulta
     * o ajuste posterior. Este método utiliza manejo de excepciones para asegurar la integridad
     * del proceso y proporcionar una respuesta adecuada ante cualquier eventualidad técnica.
     *
     * @param idSolicitudGlobal El identificador único de la solicitud global de devolución.
     * @return DevolucionDTO Objeto que contiene los resultados de la simulación de la devolución.
     * @throws TechnicalException Si ocurre un error técnico inesperado durante el proceso de simulación.
     */
    @Override
    public DevolucionDTO simularDevolucionTemporal(Long idSolicitudGlobal) {
        try {
            logger.info("Inicio de método simularDevolucionTemporal(Long idSolicitudGlobal)");

            // Consulta datos temporales para simulación
            DevolucionDTO devolucionDTO = consultarDevolucionTemporal(idSolicitudGlobal);

            // Crea lista de ids de RegistroGeneral
            List<Long> listaIdRegistroGeneral = new ArrayList<>();

            // Recorre lista de aportes incluidos en la solicitud de devolución
            // Procesamiento de cada análisis de devolución
            simularDevolucionesEnParalelo(devolucionDTO.getAnalisis(), idSolicitudGlobal, listaIdRegistroGeneral);

            // Consulta resultados posteriores a la simulación
            actualizarResultadosSimulacion(devolucionDTO.getAnalisis(), listaIdRegistroGeneral);

            // Almacena datos de simulación en tabla temporal
            guardarDevolucionTemporal(idSolicitudGlobal, devolucionDTO);

            logger.info("Fin de método simularDevolucionTemporal(Long idSolicitudGlobal)");
            return devolucionDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un errror en el método simularDevolucionTemporal(Long idSolicitudGlobal)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Simula en paralelo el proceso de devolución para una lista de análisis de devolución,
     * utilizando un pool de ejecución con threads fijos basado en los procesadores disponibles.
     * Cada simulación de devolución se ejecuta en su propio thread, permitiendo una gestión eficiente
     * y optimizada del tiempo al procesar múltiples solicitudes de devolución simultáneamente.
     *
     * Este método es especialmente útil para manejar grandes volúmenes de datos o solicitudes
     * que necesitan ser procesadas en un tiempo reducido, aprovechando la capacidad de cómputo
     * paralelo del sistema.
     *
     * Al finalizar la ejecución de todas las simulaciones, el método se asegura de cerrar el pool de ejecución,
     * liberando así los recursos del sistema y evitando posibles fugas de memoria o sobreconsumo de recursos.
     *
     * @param listaAnalisisDevolucionDTO Lista que contiene los objetos de análisis de devolución a simular.
     * @param idSolicitudGlobal Identificador único de la solicitud global asociada a las devoluciones.
     * @param listaIdRegistroGeneral Lista que se utiliza para agregar los IDs de los registros generales
     *        creados o actualizados durante la simulación de cada devolución.
     */
    /*
    Manejo de Excepciones: Utiliza exceptionally para manejar excepciones que ocurran durante la ejecución de cada tarea.
    Si una tarea falla, lanza una TechnicalException, lo que puede cancelar el proceso de simulación inmediatamente.
    Cancelación y Limpieza: En caso de una excepción, el bloque finally garantiza que el ExecutorService se apague correctamente, cancelando cualquier tarea pendiente o en ejecución.
     */
    public void simularDevolucionesEnParalelo(List<AnalisisDevolucionDTO> listaAnalisisDevolucionDTO, Long idSolicitudGlobal, List<Long> listaIdRegistroGeneral) {
        logger.info("Inicio método simularDevolucionesEnParalelo listaAnalisisDevolucionDTO.size(): " + listaAnalisisDevolucionDTO.size() + ", idSolicitudGlobal: " + idSolicitudGlobal + ", listaIdRegistroGeneral: " + listaIdRegistroGeneral);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try {
            for (AnalisisDevolucionDTO analisis : listaAnalisisDevolucionDTO) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    simularDevolucion(analisis, idSolicitudGlobal, listaIdRegistroGeneral);
                }, executor).exceptionally(ex -> {
                    // Cancela todas las tareas pendientes y ejecutando debido a una excepción
                    //throw new CustomRuntimeException("Error interno por datos", ex);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, ex);
                });
                futures.add(future);
            }

            // Espera la conclusión de todas las tareas
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }  catch (Exception e) {
            // Manejo de otras excepciones inesperadas
            //throw new RuntimeException("Error ejecutando la simulación", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
            // Intenta apagar el executor de manera ordenada
            shutdownExecutor(executor);
        }

        // Si llega aquí, la simulación fue exitosa
        // Realiza las operaciones de éxito y devuelve el resultado adecuado
        logger.info("Finaliza método simularDevolucionesEnParalelo correctamente");
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
        }
    }


    /**
     * Simula el proceso de devolución para un análisis de devolución específico.
     * Este método gestiona la creación o actualización de un registro general asociado a la solicitud de devolución,
     * ajusta montos de aportes según el análisis proporcionado, y realiza simulaciones de procesos internos
     * como el armado de tablas y la limpieza en el entorno de staging.
     * <p>
     * La simulación inicia con la creación de una transacción única y procede con la consulta o creación
     * de un registro general basado en el estado actual del análisis de devolución. Se ajustan los valores
     * de aportes obligatorios y los intereses de mora según el análisis, reflejando el resultado de la simulación
     * de devolución. Posteriormente, se actualiza el registro general en la base de datos y se procede con
     * el análisis de cotizantes relacionados en paralelo, si aplicase.
     * <p>
     * Finalmente, se realizan operaciones de simulación específicas como la ejecución de armado de tablas en staging
     * y la simulación del proceso de borrado de datos en staging, replicando el flujo de operaciones
     * de un proceso de devolución real en un entorno controlado.
     *
     * @param analisisDevolucionDTO  Objeto que contiene la información del análisis de devolución a simular.
     * @param idSolicitudGlobal      Identificador de la solicitud global asociada a la devolución.
     * @param listaIdRegistroGeneral Lista para agregar el ID del registro general creado o actualizado durante la simulación.
     */
    private void simularDevolucion(AnalisisDevolucionDTO analisisDevolucionDTO, Long idSolicitudGlobal, List<Long> listaIdRegistroGeneral) {
        // Inicia la simulación creando una transacción y gestionando registros generales
        // Crea transacción para la simulación
        Long idTransaccion = crearTransaccion();
        RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();

        // Consulta RegistroGeneral asociado al aporte. Si ya existe un RegistroGeneral
        // en simulación, se modifica ése
        if (analisisDevolucionDTO.getIdRegistroGeneralNuevo() != null) {
            registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
        } else {
            registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneral());
            List<RegistroDetalladoModeloDTO> registrosDetallados = consultarRegistroDetallado(analisisDevolucionDTO.getIdRegistroGeneral());
            registroGeneralDTO.setTransaccion(idTransaccion);
            registroGeneralDTO = crearRegistroGeneralDevCorr(registroGeneralDTO, registrosDetallados, analisisDevolucionDTO.getTipoSolicitante());
        }

        // Asigna valores al nuevo RegistroGeneral que se va a crear
        registroGeneralDTO.setRegistroControlManual(idSolicitudGlobal);
        registroGeneralDTO.setTransaccion(idTransaccion);
        registroGeneralDTO.setEsSimulado(Boolean.TRUE);
        registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
        registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);

        // Ajusta montos de aportes y intereses según el análisis de devolución
        BigDecimal nuevoAporte = new BigDecimal(0);
        if (registroGeneralDTO.getValTotalApoObligatorio() != null) {
            nuevoAporte = registroGeneralDTO.getValTotalApoObligatorio().subtract(analisisDevolucionDTO.getMonto());
        } else {
            nuevoAporte = analisisDevolucionDTO.getMonto().subtract(analisisDevolucionDTO.getMontoRegistro());
        }

        BigDecimal interes = registroGeneralDTO.getValorIntMora() != null ? registroGeneralDTO.getValorIntMora() : BigDecimal.ZERO;
        BigDecimal nuevoMora = interes.subtract(analisisDevolucionDTO.getInteres());
        registroGeneralDTO.setValTotalApoObligatorio(nuevoAporte);
        registroGeneralDTO.setValorIntMora(nuevoMora);

        // Guarda de la temporal a RegistroGeneral en staging
        Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);
        listaIdRegistroGeneral.add(idRegistroGeneral);
        analisisDevolucionDTO.setIdRegistroGeneralNuevo(idRegistroGeneral);

        // Actualiza el registro general y analiza cotizantes relacionados en paralelo
        if (analisisDevolucionDTO.getCotizanteDTO() != null) {
            // Recorre lista de cotizantes del aporte, incluidos en la solicitud de
            // devolución
            analizarCotizantesEnParalelo(analisisDevolucionDTO.getCotizanteDTO(), idRegistroGeneral);
        }

        // Ejecuta procesos de simulación de armado y borrado en staging
        // Aplica ejecución de armado de tablas en staging
        ejecutarArmadoStaging(idTransaccion);
        // Invoca simulación
        simularFasePila2(idTransaccion);
        // Invoca borrado staging
        ejecutarBorradoStaging(idTransaccion);
    }

    /**
     * El método divide un listado grande de CotizanteDTO en lotes más pequeños basados en un tamaño
     * de lote predefinido. Cada lote se procesa en paralelo utilizando un ExecutorService, lo que
     * permite la ejecución concurrente de tareas para analizar los cotizantes.
     * <p>
     * Al finalizar el procesamiento de todos los lotes, el método cierra el servicio de ejecución para liberar los recursos del sistema.
     * <p>
     * Uso de CompletableFuture.runAsync proporciona un mecanismo no bloqueante para ejecutar cada tarea,
     * lo cual es ideal para operaciones que pueden ser I/O-bound como consultas a base de datos o llamadas a servicios externos.
     *
     *
     * @param listaCotizanteDTOS Lista de {@link CotizanteDTO} que serán procesados en paralelo.
     * @param idRegistroGeneral  El identificador del registro general asociado a cada cotizante,
     *                           necesario para realizar la consulta y actualización de registros detallados.
     */
    /*
    Proceso
    1. Determinación del Tamaño de Lote: El método define un tamaño de lote óptimo (TAMANO_LOTE) para dividir la lista de cotizantes. Este tamaño puede ajustarse según la carga de trabajo y los recursos del sistema.
    2. División en Lotes: La lista de cotizantes se divide en sub-listas más pequeñas basadas en el tamaño de lote definido, preparando los datos para un procesamiento paralelo eficiente.
    3. Procesamiento Paralelo de Lotes: Cada lote se procesa en paralelo mediante la creación de tareas asincrónicas (CompletableFuture) que ejecutan el análisis de los cotizantes en un ExecutorService. Esto permite aprovechar múltiples núcleos del procesador para realizar el trabajo concurrentemente.
    4. Sincronización y Finalización: El método espera a que todas las tareas de un lote se completen antes de proceder con el siguiente, asegurando que todo el procesamiento se realice de manera controlada. Una vez procesados todos los lotes, se apaga el ExecutorService para liberar los recursos del sistema.
    */
    private void analizarCotizantesEnParalelo(List<CotizanteDTO> listaCotizanteDTOS, Long idRegistroGeneral) {
        final int TAMANO_LOTE = 5; // Define el tamaño óptimo del lote
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        /*
        se capturan las excepciones que pueden ocurrir durante el procesamiento de los lotes dentro del bloque try.
        El bloque finally asegura que, independientemente de si se produjo un error o no, el ExecutorService se apague correctamente, liberando así los recursos del sistema.
        */
        try {
            // Divide la lista total en sub-listas más pequeñas (lotes)
            List<List<CotizanteDTO>> lotes = new ArrayList<>();
            for (int i = 0; i < listaCotizanteDTOS.size(); i += TAMANO_LOTE) {
                lotes.add(listaCotizanteDTOS.subList(i, Math.min(i + TAMANO_LOTE, listaCotizanteDTOS.size())));
            }

            // Procesa cada lote en paralelo
            for (List<CotizanteDTO> lote : lotes) {
                List<CompletableFuture<Void>> futures = lote.stream()
                        .map(cotizanteDTO -> CompletableFuture.runAsync(() -> {
                            try {
                                analizarCotizante(cotizanteDTO, idRegistroGeneral);
                            } catch (Exception e) {
                                // Log the exception or handle it as necessary
                                System.err.println("Error processing cotizante: " + e.getMessage());
                                // Optionally, rethrow as an unchecked exception or handle as needed
                                throw new RuntimeException(e);
                            }
                        }, executor))
                        .collect(Collectors.toList());

                // Espera a que todas las tareas del lote actual completen
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            }
        } catch (Exception e) {
            // Manejo de excepciones generales de la ejecución de las tareas
            System.err.println("Error during parallel cotizante analysis: " + e.getMessage());
        } finally {
            // Asegura que el executor se apaga para liberar recursos
            executor.shutdown();
        }
    }




    /**
     * Analiza y actualiza la información de un cotizante específico, creando o actualizando su registro detallado.
     * Este método toma la información de un {@link CotizanteDTO}, la convierte a un {@link RegistroDetalladoModeloDTO},
     * y luego actualiza este registro detallado con información adicional obtenida a través de una consulta a la base de datos.
     * Finalmente, guarda el registro detallado actualizado en la base de datos y actualiza el ID de registro detallado
     * en el {@link CotizanteDTO} proporcionado.
     * <p>
     * Proceso:
     * 1. Convierte el {@link CotizanteDTO} a un {@link RegistroDetalladoModeloDTO} para su análisis.
     * 2. Establece el ID de registro general al registro detallado.
     * 3. Consulta información adicional del cotizante en la base de datos usando el ID de registro original.
     * 4. Actualiza el registro detallado con información adicional consultada.
     * 5. Guarda o actualiza el registro detallado en la base de datos, dependiendo de si ya existía.
     * 6. Actualiza el ID de registro detallado nuevo en el {@link CotizanteDTO}.
     *
     * @param cotizanteDTO      Objeto {@link CotizanteDTO} que contiene la información del cotizante a analizar.
     * @param idRegistroGeneral El ID del registro general asociado al cotizante, usado para enlazar registros detallados.
     */
    private void analizarCotizante(CotizanteDTO cotizanteDTO, Long idRegistroGeneral) {
        // Conversión y actualización de la información del cotizante para análisis
        RegistroDetalladoModeloDTO registroDetalladoDTO = cotizanteDTO.convertToDTODevolucion();
        registroDetalladoDTO.setRegistroGeneral(idRegistroGeneral);

        // Consulta de información adicional del cotizante y actualización del registro detallado
        RegistroDetalladoModeloDTO registroDetalladoBD = consultarRegistroDetalladoPorId(cotizanteDTO.getIdRegistro());
        registroDetalladoDTO.setTipoCotizante(registroDetalladoBD.getTipoCotizante());
        registroDetalladoDTO.setOutTipoAfiliado(registroDetalladoBD.getOutTipoAfiliado());
        registroDetalladoDTO.setTipoIdentificacionCotizante(registroDetalladoBD.getTipoIdentificacionCotizante());
        registroDetalladoDTO.setNumeroIdentificacionCotizante(registroDetalladoBD.getNumeroIdentificacionCotizante());
        registroDetalladoDTO.setCodDepartamento(registroDetalladoBD.getCodDepartamento());
        registroDetalladoDTO.setCodMunicipio(registroDetalladoBD.getCodMunicipio());
        registroDetalladoDTO.setPrimerApellido(registroDetalladoBD.getPrimerApellido());
        registroDetalladoDTO.setPrimerNombre(registroDetalladoBD.getPrimerNombre());
        registroDetalladoDTO.setSegundoApellido(registroDetalladoBD.getSegundoApellido());
        registroDetalladoDTO.setSegundoNombre(registroDetalladoBD.getSegundoNombre());

        // Si no existe simulación previa, se crea el registro en RegistroDetallado;
        // sino, se actualiza
        registroDetalladoDTO.setId(cotizanteDTO.getIdRegistroDetalladoNuevo());

        // Guardado o actualización del registro detallado en la base de datos y actualización del ID en cotizanteDTO
        Long idRegistroDetallado = guardarRegistroDetallado(registroDetalladoDTO);
        cotizanteDTO.setIdRegistroDetalladoNuevo(idRegistroDetallado);
    }

    /**
     * Actualiza los resultados de la simulación para cada análisis de devolución basándose en los registros detallados actualizados.
     * Este método se encarga de mapear y actualizar los datos de cotizantes asociados a cada análisis de devolución,
     * utilizando la información más reciente obtenida de registros detallados.
     * <p>
     * El proceso incluye:
     * 1. Crear un mapeo de análisis de devolución por el ID de registro general nuevo para acceso rápido.
     * 2. Iterar sobre la lista de IDs de registro general proporcionada, consultando los datos detallados asociados.
     * 3. Para cada ID de registro, actualizar la información de cotizantes en el análisis de devolución correspondiente,
     * si este existe y tiene cotizantes asociados.
     * 4. Registrar en el log el inicio y fin del proceso de actualización, así como la actualización de cotizantes
     * para cada análisis de devolución específico.
     *
     * @param listaAnalisisDevolucionDTO Lista de {@link AnalisisDevolucionDTO} que representa los análisis de devolución
     *                                   a ser actualizados basados en los resultados de la simulación.
     * @param listaIdRegistroGeneral     Lista de {@link Long} que contiene los IDs de registro general utilizados para
     *                                   consultar y actualizar los análisis de devolución correspondientes.
     */
    private void actualizarResultadosSimulacion(List<AnalisisDevolucionDTO> listaAnalisisDevolucionDTO, List<Long> listaIdRegistroGeneral) {
        logger.info("Iniciando la actualización de análisis de devolución post simulación.");

        // Mapeo para acceso rápido de análisis de devolución por ID de registro general nuevo
        Map<Long, AnalisisDevolucionDTO> mapAnalisisPorRegistroGeneral = listaAnalisisDevolucionDTO.stream()
                .filter(analisis -> analisis.getIdRegistroGeneralNuevo() != null)
                .collect(Collectors.toMap(AnalisisDevolucionDTO::getIdRegistroGeneralNuevo, Function.identity()));

        // Itera sobre los IDs de registro general para consultar y actualizar los análisis correspondientes
        for (Long idRegistroGeneral : listaIdRegistroGeneral) {
            List<RegistroDetalladoModeloDTO> listaRegistroDetalladoDTO = consultarRegistroDetallado(idRegistroGeneral);
            AnalisisDevolucionDTO analisisDevolucionDTO = mapAnalisisPorRegistroGeneral.get(idRegistroGeneral);
            if (analisisDevolucionDTO != null && analisisDevolucionDTO.getCotizanteDTO() != null) {
                logger.info("Actualizando cotizantes para el análisis de devolución con ID de registro general: " + idRegistroGeneral);
                // Actualiza los cotizantes del análisis de devolución basándose en los registros detallados actualizados
                List<CotizanteDTO> cotizantesActualizados = asignarEvaluacionCotizante(analisisDevolucionDTO.getCotizanteDTO(), listaRegistroDetalladoDTO, false);
                analisisDevolucionDTO.setCotizanteDTO(cotizantesActualizados);
            }
        }

        logger.info("Finalizada la actualización de análisis de devolución post simulación.");
    }


    ///Fin simularDevolucionTemporal ---------------------------------------------------------------

    /**
     * Método encargado de crear un DTO de registro general de PILA a partir de otro
     * existente
     * 
     * @param registroGeneralBase
     * @param tipoSolicitante
     * @return
     */
    private RegistroGeneralModeloDTO crearRegistroGeneralDevCorr(RegistroGeneralModeloDTO registroGeneralBase,
            List<RegistroDetalladoModeloDTO> registrosDetallados, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "AportesManualesComposite.crearRegistroGeneralDevolucion(RegistroGeneralModeloDTO, "
                + "List<RegistroDetalladoModeloDTO>, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroGeneralModeloDTO result = registroGeneralBase;
        result.setId(null);

        if (!TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {
            BigDecimal interes = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            Integer cantidad = 0;

            for (RegistroDetalladoModeloDTO regDet : registrosDetallados) {
                Boolean usarRegistro = Boolean.FALSE;
                if (regDet.getOutRegistroActual() && ((TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
                        .equals(tipoSolicitante)
                        && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(regDet.getOutTipoAfiliado()))
                        || (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)
                                && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(regDet.getOutTipoAfiliado())))) {
                    usarRegistro = Boolean.TRUE;
                }

                if (usarRegistro) {
                    interes = interes.add(regDet.getOutValorMoraCotizante());
                    total = total.add(regDet.getAporteObligatorio());
                    cantidad++;
                }
            }

            result.setValTotalApoObligatorio(total);
            result.setValorIntMora(interes);
            result.setCantidadReg2(cantidad);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarCotizantes(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, java.lang.Long)
     */
    @Override
    public List<CotizanteDTO> consultarCotizantes(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            Long periodoAporte) {
        logger.info("Inicio de consultarCotizantes");

        String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
        Integer tamanoPaginador = new Integer(sTamanoPaginador);

        EmpleadorModeloDTO empleador = consultarEmpleadorTipoNumero(tipoIdentificacion, numeroIdentificacion);
        if (empleador != null) {
            List<Long> idRoles = obtenerTrabajadoresActivosPeriodo(periodoAporte, empleador.getIdEmpleador());
            if (!idRoles.isEmpty()) {
                List<CotizanteDTO> cotizantes = consultarCotizantesPorRol(idRoles, tamanoPaginador);
                List<ConsultaEstadoCajaPersonaDTO> estadosCajaDTO = new ArrayList<>();
                for (CotizanteDTO cotizante : cotizantes) {
                    ConsultaEstadoCajaPersonaDTO estadoCajaDTO = new ConsultaEstadoCajaPersonaDTO();
                    estadoCajaDTO.setIdEmpleador(empleador.getIdEmpleador());
                    estadoCajaDTO.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
                    estadoCajaDTO.setTipoIdentificacion(cotizante.getTipoIdentificacion());
                    estadosCajaDTO.add(estadoCajaDTO);
                }
                estadosCajaDTO = buscarEstadoCajaPersonasMasivo(estadosCajaDTO, tamanoPaginador);
                for (ConsultaEstadoCajaPersonaDTO consultaEstadoCajaPersonaDTO : estadosCajaDTO) {
                    for (CotizanteDTO cotizante : cotizantes) {
                        if (cotizante.getTipoIdentificacion()
                                .equals(consultaEstadoCajaPersonaDTO.getTipoIdentificacion())
                                && cotizante.getNumeroIdentificacion()
                                        .equals(consultaEstadoCajaPersonaDTO.getNumeroIdentificacion())) {
                            cotizante.setEstado(consultaEstadoCajaPersonaDTO.getEstadoAfiliadoEnum());
                        }
                    }
                }
                logger.info("Fin de método consultarCotizantes,");
                return cotizantes;
            }
        }
        logger.info("Fin de consultarCotizantes");
        return null;

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarEstadoCotizantePorPeriodo(
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public EstadoAfiliadoEnum consultarEstadoCotizantePorPeriodo(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante,
            Long periodoAporte,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {

        String firmaServicio = "AportesManualesBusiness.consultarEstadoCotizantePorPeriodo(tipoIdentificacion, numeroIdentificacion, "
                + "tipoIdentificacionAportante, numeroIdentificacionAportante, periodoAporte, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " :: tipoIdentificacion = "
                + tipoIdentificacion.getValorEnPILA()
                + ", numeroIdentificacion = " + numeroIdentificacion + ", periodoAporte = " + periodoAporte
                + ", tipoSolicitante = "
                + tipoSolicitante.name());

        EstadoAfiliadoEnum result = EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;

        AfiliadoModeloDTO afiliado = consultarDatosAfiliado(tipoIdentificacion, numeroIdentificacion);
        if (afiliado != null) {
            EmpleadorModeloDTO empleador = null;
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                empleador = consultarEmpleadorTipoNumero(tipoIdentificacionAportante, numeroIdentificacionAportante);
                if (empleador == null) {
                    result = EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
                }
            }

            LocalDate localDateBase = Instant.ofEpochMilli(periodoAporte).atZone(ZoneId.systemDefault()).toLocalDate();
            localDateBase = localDateBase.withDayOfMonth(1);
            Long startDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            localDateBase = localDateBase.plusMonths(1L);
            Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

            result = obtenerEstadoTrabajador(startDate, endDate,
                    empleador == null ? null : empleador.getIdEmpleador(), afiliado.getIdAfiliado(),
                    transformarTipoSolicitante(tipoSolicitante));
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result != null ? result : EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarEstadoAportantePorPeriodo(
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public EstadoEmpleadorEnum consultarEstadoAportantePorPeriodo(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            Long periodoAporte, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaServicio = "AportesManualesBusiness.consultarEstadoAportantePorPeriodo(TipoIdentificacionEnum, String, "
                + "Long, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " :: tipoIdentificacion = "
                + tipoIdentificacion.getValorEnPILA()
                + ", numeroIdentificacion = " + numeroIdentificacion + ", periodoAporte = " + periodoAporte
                + ", tipoSolicitante = "
                + tipoSolicitante.name());

        EstadoEmpleadorEnum result = EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION;

        EmpleadorModeloDTO empleador = consultarEmpleadorTipoNumero(tipoIdentificacion, numeroIdentificacion);
        PersonaModeloDTO persona = consultarDatosPersona(tipoIdentificacion, numeroIdentificacion);

        LocalDate localDateBase = Instant.ofEpochMilli(periodoAporte).atZone(ZoneId.systemDefault()).toLocalDate();
        localDateBase = localDateBase.withDayOfMonth(1);
        Long startDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        localDateBase = localDateBase.plusMonths(1L);
        Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

        if (persona != null) {

            if (empleador != null && TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                result = obtenerEstadoAportantePeriodo(startDate, endDate,
                        empleador.getIdEmpleador(), tipoSolicitante);
            } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)
                    || TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {

                AfiliadoModeloDTO afiliado = consultarDatosAfiliado(tipoIdentificacion, numeroIdentificacion);
                if (afiliado != null) {
                    result = obtenerEstadoAportantePeriodo(startDate, endDate, afiliado.getIdAfiliado(),
                            tipoSolicitante);
                }
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result != null ? result : EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION;
    }

    /**
     * Método que consulta un registro en <code>AporteDetallado</code> por
     * identificador
     * 
     * @param idAporteDetallado
     *                          Identificador del registro
     * @return Objeto <code>AporteDetalladoModeloDTO</code> con la información del
     *         aporte
     */
    private AporteDetalladoModeloDTO consultarAporteDetallado(Long idAporteDetallado) {
        logger.info("Inicio de método consultarAporteDetallado(Long idAporteDetallado)");
        ConsultarAporteDetallado consultarAporteDetallado = new ConsultarAporteDetallado(idAporteDetallado);
        consultarAporteDetallado.execute();
        AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado.getResult();
        logger.info("Fin de método consultarAporteDetallado(Long idAporteDetallado)");
        return aporteDetalladoDTO;
    }

    /**
     * Método que consulta un registro en <code>RegistroDetallado</code> por
     * identificador
     * 
     * @param idRegistroDetallado
     *                            Identificador del registro
     * @return Objeto <code>RegistroDetalladoModeloDTO</code> con la información del
     *         aporte
     */
    private RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(Long idRegistroDetallado) {
        logger.info("Inicio de método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
        ConsultarRegistroDetalladoPorId consultarRegistroDetallado = new ConsultarRegistroDetalladoPorId(
                idRegistroDetallado);
        consultarRegistroDetallado.execute();
        logger.info("Fin de método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
        return consultarRegistroDetallado.getResult();
    }

    /**
     * Método que consulta un registro en <code>IndicePlanilla</code>, por
     * identificador
     * 
     * @param idPlanilla
     *                   Identificador del registro
     * @return Objeto <code>IndicePlanillaModeloDTO</code> con la información del
     *         registro
     */
    private IndicePlanillaModeloDTO consultarIndicePlanilla(Long idPlanilla) {
        logger.info("Inicio de método consultarIndicePlanilla(Long idPlanilla)");
        IndicePlanillaModeloDTO indicePlanillaDTO = null;

        if (idPlanilla != null) {
            ConsultarIndicePlanilla consultarIndicePlanilla = new ConsultarIndicePlanilla(idPlanilla);
            consultarIndicePlanilla.execute();
            indicePlanillaDTO = consultarIndicePlanilla.getResult();
        }

        logger.info("Fin de método consultarIndicePlanilla(Long idPlanilla)");
        return indicePlanillaDTO;
    }
    private IndicePlanillaModeloDTO consultarIndicePlanillaNumeroAportante(Long idPlanilla, Long registroDetallado) {
        logger.info("Inicio de método consultarIndicePlanilla(Long idPlanilla)");
        IndicePlanillaModeloDTO indicePlanillaDTO = null;

        if (idPlanilla != null) {
            ConsultarIndicePlanillaNumeroAportante consultarIndicePlanillaNumeroAportante = new ConsultarIndicePlanillaNumeroAportante(idPlanilla,registroDetallado);
            consultarIndicePlanillaNumeroAportante.execute();
            indicePlanillaDTO = consultarIndicePlanillaNumeroAportante.getResult();
        }
        

        logger.info("Fin de método consultarIndicePlanilla(Long idPlanilla)");
        return indicePlanillaDTO;
    }


    /**
     * Método que consulta un registro en <code>AporteGeneral</code>, por
     * identificador
     * 
     * @param idAporteGeneral
     *                        Identificador del registro
     * @return Objeto <code>AporteGeneralModeloDTO</code> con la información del
     *         registro
     */
    private AporteGeneralModeloDTO consultarAporteGeneral(Long idAporteGeneral) {
        logger.info("Inicio de método consultarAporteGeneral(Long idAporteGeneral)");
        ConsultarAporteGeneral consultarAporteGeneral = new ConsultarAporteGeneral(idAporteGeneral);
        consultarAporteGeneral.execute();
        logger.info("Fin de método consultarAporteGeneral(Long idAporteGeneral)");
        return consultarAporteGeneral.getResult();
    }

    /**
     * Método que consulta una solicitud de aportes, por el identificador del aporte
     * general asociado
     * 
     * @param idAporteGeneral
     *                        Identificador del aporte general
     * @return Objeto <code>SolicitudAporteModeloDTO</code> con la información de la
     *         solicitud
     */
    private SolicitudAporteModeloDTO consultarSolicitudAportePorIdAporte(Long idAporteGeneral) {
        logger.info("Inicio de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
        ConsultarSolicitudAportePorIdAporte consultarSolicitudAporte = new ConsultarSolicitudAportePorIdAporte(
                idAporteGeneral);
        consultarSolicitudAporte.execute();
        logger.info("Fin de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
        return consultarSolicitudAporte.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de la evaluación de condiciones
     * por las cuales un aporte puede ser tenido o no en cuenta en una solicitud
     * de devolución o corrección, de acuerdo a su registro histórico
     * 
     * @param estadoAporte
     *                            Estado del aporte
     * @param modalidadRecaudo
     *                            Modalidad de recaudo (PILA manual, PILA automático
     *                            o Aporte
     *                            Manual)
     * @param indicePlanillaDTO
     *                            Información de la planilla con la que se realizó
     *                            el aporte, si
     *                            éste se hizo por PILA
     * @param tieneModificaciones
     *                            Indica si el archivo ha sido modificado. Aplica
     *                            sólo para PILA
     * @param solicitudAporteDTO
     *                            Información de la solciitud de aportes
     * @return Información de la evaluación de condiciones de acuerdo al
     *         registro histórico del aporte
     */
    private HistoricoDTO consultarHistoricoEvaluacionAporte(EstadoAporteEnum estadoAporte,
            ModalidadRecaudoAporteEnum modalidadRecaudo,
            IndicePlanillaModeloDTO indicePlanillaDTO, Boolean tieneModificaciones,
            EstadoSolicitudAporteEnum estadoSolicitud,
            TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
            String periodoAporte) {
                
        logger.info("Inicio de método consultarHistoricoEvaluacionAporte");
        ConsultarHistoricoEvaluacionAporte service = new ConsultarHistoricoEvaluacionAporte(
                indicePlanillaDTO != null ? indicePlanillaDTO.getEstadoArchivo() : null, estadoAporte,
                tieneModificaciones,
                tipoIdentificacionCotizante, modalidadRecaudo, numeroIdentificacionCotizante, periodoAporte,
                estadoSolicitud);
        service.execute();
        logger.info("Fin de método consultarHistoricoEvaluacionAporte");
        return service.getResult();
    }

    /**
     * Método que consulta las novedades, aplicadas y no aplicadas, para un
     * cotizante en relación a un aporte realizado
     * 
     * @param aporteDetalladoDTO
     *                           Información del aporte realizado por el cotizante
     * @return La lista de novedades
     */
    private List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(AporteDetalladoModeloDTO aporteDetalladoDTO) {
        try {
            logger.info("Inicio de método consultarNovedadesCotizanteAporte");
            List<NovedadCotizanteDTO> listaFinalNovedadesDTO = new ArrayList<>();

            // Se consulta la lista de novedades aplicadas/no aplicadas al cotizante
            List<NovedadCotizanteDTO> listaNovedadesCotizanteAporte = consultarNovedadesCotizanteAporte(
                    aporteDetalladoDTO.getIdRegistroDetallado());
            logger.info("**__** aporteDetalladoDTO.getIdRegistroDetallado() : "
                    + aporteDetalladoDTO.getIdRegistroDetallado());
            // Se consulta la lista de novedades habilitadas por tipo de afiliado
            HabilitarNovedadesAportes habilitarNovedadesAportes = new HabilitarNovedadesAportes(
                    aporteDetalladoDTO.getTipoCotizante());
            habilitarNovedadesAportes.execute();
            List<TipoTransaccionEnum> listaNovedadesHabilitadas = habilitarNovedadesAportes.getResult();

            LocalDate fechaActual = LocalDate.now();
            fechaActual = fechaActual.with(firstDayOfMonth());
            fechaActual.atStartOfDay();
            Date fecha = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Long periodoActual = fecha.getTime();

            for (TipoTransaccionEnum tipoTransaccion : listaNovedadesHabilitadas) {
                NovedadCotizanteDTO novedadDTO = new NovedadCotizanteDTO();
                novedadDTO.setTipoNovedad(tipoTransaccion);
                novedadDTO.setCondicion(Boolean.FALSE);
                novedadDTO.setEstadoNovedad(Boolean.FALSE);

                for (NovedadCotizanteDTO novedadCotizanteAporte : listaNovedadesCotizanteAporte) {
                    Boolean marcar = Boolean.FALSE;
                    if (novedadCotizanteAporte.getTipoNovedad() == TipoTransaccionEnum.NOVEDAD_REINTEGRO) {
                        novedadCotizanteAporte
                                .setTipoNovedad(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO);
                        logger.info(
                                "**__** de método consultarNovedadesCotizanteAporte novedadCotizanteAporte.getTipoNovedad()"
                                        + novedadCotizanteAporte.getTipoNovedad());
                    }
                    // para las novedades de pensionado, se amplia la comparación
                    boolean estadoVerdaderoFechaNull = false;
                    switch (novedadCotizanteAporte.getTipoNovedad()) {

                        case RETIRO_PENSIONADO_25ANIOS:
                        case RETIRO_PENSIONADO_MAYOR_1_5SM_0_6:
                        case RETIRO_PENSIONADO_MAYOR_1_5SM_2:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_0:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_0_6:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_2:
                            if (TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6.equals(tipoTransaccion)) {
                                marcar = Boolean.TRUE;
                            }
                            break;
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL:
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL:
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL:
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL:
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL:
                        case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL:
                            if (TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL
                                    .equals(tipoTransaccion)) {
                                marcar = Boolean.TRUE;
                                estadoVerdaderoFechaNull = true;
                            }
                            break;
                        default:
                            if (novedadCotizanteAporte.getTipoNovedad().equals(tipoTransaccion)
                                    || (TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO
                                            .equals(tipoTransaccion)
                                            && TipoTransaccionEnum.NOVEDAD_ING
                                                    .equals(novedadCotizanteAporte.getTipoNovedad()))) {
                                marcar = Boolean.TRUE;
                            }
                            break;
                    }

                    if (marcar) {
                        novedadDTO.setCondicion(Boolean.TRUE);
                        novedadDTO.setFechaInicio(novedadCotizanteAporte.getFechaInicio());
                        novedadDTO.setFechaFin(novedadCotizanteAporte.getFechaFin());
                        novedadDTO.setEstadoNovedad(novedadCotizanteAporte.getEstadoNovedad());

                        logger.info("**__** novedadCotizanteAporte.getTipoNovedad() : "
                                + novedadCotizanteAporte.getTipoNovedad());
                        logger.info("**__** novedadDTO.periodoActual : " + periodoActual);
                        logger.info("**__** novedadCotizanteAporte.getEstadoNovedad() : "
                                + novedadCotizanteAporte.getEstadoNovedad());
                        logger.info("**__** novedadDTO.setEstadoNovedad( : " + novedadDTO.getEstadoNovedad());
                        logger.info("**__**novedadCotizanteAporte.getFechaInicio() : "
                                + novedadCotizanteAporte.getFechaInicio());
                    }
                }
                listaFinalNovedadesDTO.add(novedadDTO);
            }

            /* Inicia consulta de las novedades de retiro */
            List<NovedadCotizanteDTO> listaNovedadesRetiro = consultarNovedadesRetiro(
                    aporteDetalladoDTO.getIdRegistroDetallado(),
                    aporteDetalladoDTO.getIdPersona());

            for (NovedadCotizanteDTO novedadRetiroDTO : listaNovedadesRetiro) {
                for (NovedadCotizanteDTO novedadDTO : listaFinalNovedadesDTO) {
                    logger.info("Entra a doble for");
                    if (novedadDTO.getTipoNovedad().equals(novedadRetiroDTO.getTipoNovedad())) {
                        novedadDTO.setFechaInicio(novedadRetiroDTO.getFechaInicio());
                        novedadDTO.setCondicion(Boolean.TRUE);
                        logger.info("Entre primer IF");
                        if (novedadRetiroDTO.getFechaInicio() != null
                                && periodoActual > novedadRetiroDTO.getFechaInicio()) {
                            logger.info("Entre SEGUNDO IF");
                            novedadDTO.setEstadoNovedad(Boolean.TRUE);
                        } else if (novedadDTO.getFechaFin() != null && novedadDTO.getCondicion()) {
                            logger.info("Entre ELSE IF");
                            novedadDTO.setEstadoNovedad(Boolean.TRUE);
                        } else {
                            logger.info("Entre primer ELSE");
                            novedadDTO.setEstadoNovedad(Boolean.FALSE);
                        }
                        break;
                    }
                }
            }

            // Consulta tipo de transacción de la solicitud en estado RECHAZADA en Core
            List<String> listaTipoTransaccionNovedadRechazada = consultarTipoTransaccionNovedadRechazadaCotizante(
                    aporteDetalladoDTO.getId());

            // se evaluan las novedades encontradas, para actualizar su estado respecto a
            // PILA
            if (listaTipoTransaccionNovedadRechazada != null && !listaTipoTransaccionNovedadRechazada.isEmpty()) {
                for (NovedadCotizanteDTO novedad : listaFinalNovedadesDTO) {
                    if (listaTipoTransaccionNovedadRechazada.contains(novedad.getTipoNovedad().name())) {
                        novedad.setEstadoNovedad(Boolean.FALSE);
                    }
                }
            }

            /* Finaliza la consulta de las novedades de retiro */
            logger.info("Fin de método consultarNovedadesCotizanteAporte");
            return listaFinalNovedadesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarNovedadesCotizanteAporte", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que invoca el servicio de consulta de tipos de transacción de
     * solicitud de novedades rechazadas
     * 
     * @param idAporteDetallado
     *                          Identificador del aporte detallado
     * @return Lista de tipos de transacción
     */
    private List<String> consultarTipoTransaccionNovedadRechazadaCotizante(Long idAporteDetallado) {
        logger.info("Inicia método consultarTipoTransaccionNovedadRechazadaCotizante");
        ConsultarTipoTransaccionNovedadRechazadaCotizante service = new ConsultarTipoTransaccionNovedadRechazadaCotizante(
                idAporteDetallado);
        service.execute();
        logger.info("Finaliza método consultarTipoTransaccionNovedadRechazadaCotizante");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de novedades rechazadas, por
     * cotizante
     * 
     * @param idRegistroDetallado
     *                            Identificador del registro detallado, asociado al
     *                            aporte del cotizante
     * @param idPersona
     *                            id de la persona asociada.
     * @return Lista de novedades
     */
    private List<NovedadCotizanteDTO> consultarNovedadesRetiro(Long idRegistroDetallado, Long idPersona) {
        logger.info("Inicia método consultarNovedadesRechazadasCotizanteAporte");
        ConsultarNovedadRetiro service = new ConsultarNovedadRetiro(idPersona, idRegistroDetallado);
        service.execute();
        logger.info("Finaliza método consultarNovedadesRechazadasCotizanteAporte");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de novedades por cotizante
     * 
     * @param idAporteDetallado
     *                          Identificador del aporte detallado
     * @return Lista de novedades del cotizante, asociadas al aporte
     */
    private List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idRegistroDetallado) {
        ConsultarNovedadesCotizanteAporte consultarNovedadesCotizanteAporte = new ConsultarNovedadesCotizanteAporte(
                idRegistroDetallado);
        consultarNovedadesCotizanteAporte.execute();
        return consultarNovedadesCotizanteAporte.getResult();
    }

    /**
     * Método que consulta la información del aporte para evaluación de devolución o
     * corrección
     * 
     * @param aporteDetalladoDTO
     *                             Información del aporte detallado
     * @param registroDetalladoDTO
     *                             Información del registro detallado del aporte, en
     *                             el staging de PILA
     * @param esVista360
     *                             Indica que se está solicitando la gesttión desde
     *                             una vista 360
     * @return Objeto <code>AportesDTO</code> con la información del aporte y su
     *         evaluación
     */
    private AportesDTO consultarAportesCotizante(AporteDetalladoModeloDTO aporteDetalladoDTO,
            RegistroDetalladoModeloDTO registroDetalladoDTO, Boolean esVista360) {
        String firmaMetodo = "AportesManualesCompositeBusiness.consultarAportesCotizante(AporteDetalladoModeloDTO, RegistroDetalladoModeloDTO, "
                + "Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AportesDTO aporteDTO = new AportesDTO();
        aporteDTO.setEstadoAporte(EstadoAporteEnum.VIGENTE);

        // se consulta el registro detallado inicial para los datos originales
        RegistroDetalladoModeloDTO regInicial = registroDetalladoDTO;
        logger.info("aporteDetalladoDTO.getIdRegistroDetalladoUltimo() "+aporteDetalladoDTO.getIdRegistroDetalladoUltimo());
        if ((aporteDetalladoDTO.getIdRegistroDetalladoUltimo() != null 
        && !(Long.toString(aporteDetalladoDTO.getIdRegistroDetallado()).equals(Long.toString(aporteDetalladoDTO.getIdRegistroDetalladoUltimo())))) && esVista360) {
            regInicial = consultarRegistroDetalladoPorId(aporteDetalladoDTO.getIdRegistroDetalladoUltimo());
        }

        BigDecimal aporteFinalDelRegistro = aporteDetalladoDTO.getAporteObligatorio();
        aporteDTO.setAporteFinal(aporteFinalDelRegistro);

        BigDecimal interesesFinalesAjustados = aporteDetalladoDTO.getValorMora();
        aporteDTO.setInteresesFinales(interesesFinalesAjustados);

        Boolean salarioIntegral = Boolean.TRUE;
        Boolean salarioIntegralNuevo = Boolean.TRUE;

        if (aporteDetalladoDTO.getSalarioIntegral() == null
                || aporteDetalladoDTO.getSalarioIntegral().equals("0")) {
            salarioIntegralNuevo = Boolean.FALSE;
        }

        if (regInicial.getSalarioIntegral() == null || regInicial.getSalarioIntegral().equals("0")) {
            salarioIntegral = Boolean.FALSE;
        }

        // Tabla de valores -> Valor actual
        aporteDTO.setDiasCotizado(
                regInicial.getDiasCotizados() != null ? regInicial.getDiasCotizados().longValue() : null);
        aporteDTO.setNumeroHorasLaboral(
                regInicial.getHorasLaboradas() != null ? regInicial.getHorasLaboradas().longValue() : null);
        aporteDTO.setSalarioBasico(regInicial.getSalarioBasico());
        aporteDTO.setIbc(regInicial.getValorIBC());
        aporteDTO.setTarifa(regInicial.getTarifa());
        aporteDTO.setAporteObligatorio(regInicial.getAporteObligatorio());
        aporteDTO.setMoraAporte(regInicial.getOutValorMoraCotizante());
        aporteDTO.setSalarioIntegral(salarioIntegral);

        // Tabla de valores -> Valor nuevo
        aporteDTO.setDiasCotizadoNuevo(
            aporteDetalladoDTO.getDiasCotizados() != null ? aporteDetalladoDTO.getDiasCotizados().longValue()
                        : null);
        aporteDTO.setNumeroHorasLaboralNuevo(
            aporteDetalladoDTO.getHorasLaboradas() != null ? aporteDetalladoDTO.getHorasLaboradas().longValue()
                        : null);
        aporteDTO.setSalarioBasicoNuevo(aporteDetalladoDTO.getSalarioBasico());
        aporteDTO.setIbcNuevo(aporteDetalladoDTO.getValorIBC());
        aporteDTO.setTarifaNuevo(aporteDetalladoDTO.getTarifa());
        aporteDTO.setAporteObligatorioNuevo(aporteDetalladoDTO.getAporteObligatorio());
        aporteDTO.setMoraAporteNuevo(aporteDetalladoDTO.getValorMora());
        aporteDTO.setSalarioIntegralNuevo(salarioIntegralNuevo);

        // Tabla de valores -> Monto devolver
        aporteDTO.setAplicarAporteObligatorio(Boolean.FALSE);
        aporteDTO.setAplicarMoraCotizante(Boolean.FALSE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aporteDTO;

    }

    /**
     * Método que obtiene la evalaución vigente de un aporte, por cotizante
     * 
     * @param aporteDetalladoDTO
     *                             Información del registro detallado
     * @param registroDetalladoDTO
     *                             Información del registro detallado en el staging
     *                             de PILA
     * @return Objeto <code>EvaluacionDTO</code> con el resultado de la evaluación
     */
    private EvaluacionDTO obtenerEvaluacionVigente(AporteDetalladoModeloDTO aporteDetalladoDTO,
            RegistroDetalladoModeloDTO registroDetalladoDTO) {
        try {
            logger.info("Inicio de método obtenerEvaluacionVigente");
            TipoAfiliadoEnum tipoCotizante = aporteDetalladoDTO.getTipoCotizante();
            EvaluacionDTO evaluacion = new EvaluacionDTO();
            // Se cambia el valor del aporte de pila a core
            // Debido a la gestion de futuros cambios en valor del aporter por correcciones de planillas N
            evaluacion.setValorAporte(aporteDetalladoDTO.getAporteObligatorio());
            evaluacion.setPersona(tipoCotizante);

            if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoCotizante)) {
                evaluacion.setDepV0(registroDetalladoDTO.getOutEstadoValidacionV0());
                evaluacion.setDepV1(registroDetalladoDTO.getOutEstadoValidacionV1());
                evaluacion.setDepV2(registroDetalladoDTO.getOutEstadoValidacionV2());
                evaluacion.setDepV3(registroDetalladoDTO.getOutEstadoValidacionV3());
                evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoCotizante)) {
                evaluacion.setIdv1(registroDetalladoDTO.getOutEstadoValidacionV1());
                evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
            } else if (TipoAfiliadoEnum.PENSIONADO.equals(tipoCotizante)) {
                evaluacion.setPdv1(registroDetalladoDTO.getOutEstadoValidacionV1());
                evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
            }

            logger.info("Fin de método obtenerEvaluacionVigente");
            return evaluacion;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método obtenerEvaluacionVigente", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Servicio encargado de consultar los empleadores de un afiliado.
     * 
     * @param tipoIdentificacion
     *                             tipo de identificacion del afiliado.
     * @param numeroIdentificacion
     *                             numero de identificación del afiliado.
     * @param tipoAfiliado
     *                             tipo de afiliación.
     */
    private List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            TipoAfiliadoEnum tipoAfiliado) {
        logger.info(
                "Inicio de método consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado)");
        ConsultarRolesAfiliado consultarRolesAfiliadoService = new ConsultarRolesAfiliado(tipoAfiliado,
                numeroIdentificacion,
                tipoIdentificacion);
        consultarRolesAfiliadoService.execute();
        List<RolAfiliadoEmpleadorDTO> rolesList = consultarRolesAfiliadoService.getResult();
        logger.info(
                "Fin de método consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado)");
        return rolesList;
    }

    /**
     * Método encargado de invocar el servicio que consulta la información temporal.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     * @return lista de los aportes detallados
     */
    private List<AporteDTO> consultarInformacionTemporal(Long idTransaccion) {
        logger.info("Inicio de método consultarInformacionTemporal(Long idTransaccion)");
        ConsultarAporteTemporal consultarAporteService = new ConsultarAporteTemporal(idTransaccion);
        consultarAporteService.execute();
        List<AporteDTO> aportesDTO = consultarAporteService.getResult();
        logger.info("Fin de método consultarInformacionTemporal(Long idTransaccion)");
        return aportesDTO;
    }

    /**
     * Método encargado de invocar el servicio que consulta la información temporal.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     * @return lista de los aportes detallados
     */
    private List<TemNovedad> consultarInformacionTemporalNovedad(Long idTransaccion) {
        logger.info("Inicio de método consultarInformacionTemporalNovedad(Long idTransaccion)");
        ConsultarNovedad consultarNovedadTemporal = new ConsultarNovedad(idTransaccion);
        consultarNovedadTemporal.execute();
        List<TemNovedad> novedades = consultarNovedadTemporal.getResult();
        for (TemNovedad temNovedad : novedades) {
            logger.info("**__** ¡¡¡¡¡ : getAccionNovedad: " + temNovedad.getAccionNovedad());
            logger.info("**__**¨ ¡¡¡¡¡  : getRegistroDetallado" + temNovedad.getRegistroDetallado());
            logger.info("**__**¨ ¡¡¡¡¡getTipoTransaccion: " + temNovedad.getTipoTransaccion());
            logger.info("**__**¨ ¡¡¡¡¡getFechaInicioNovedad: " + temNovedad.getFechaInicioNovedad());
        }
        logger.info("Fin de método consultarInformacionTemporalNovedad(Long idTransaccion)");
        return novedades;
    }

    /**
     * Método que se encarga de transformar un tipo de solicitnate a un tipo
     * afiliado para aportes manuales.
     * 
     * @param tipoSolicitante
     *                        tipo de solicitante.
     * @return tipo de afiliado (cotizante).
     */
    private TipoAfiliadoEnum transformarTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.info("Inicio de método transformarTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        TipoAfiliadoEnum tipoAfiliado = null;
        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
            tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
        } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)) {
            tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
        } else if (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {
            tipoAfiliado = TipoAfiliadoEnum.PENSIONADO;
        }
        logger.info("Fin de método transformarTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        return tipoAfiliado;
    }

    /**
     * Método que se encarga de transformar un tipo afiliado a un tipo de
     * solicitnate, para aportes manuales
     * 
     * @param tipoAfiliado
     *                     Tipo de afiliado
     * @return El tipo de solicitante equivalente
     */
    private TipoSolicitanteMovimientoAporteEnum transformarTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        logger.info("Inicio de método transformarTipoAfiliado");
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;

        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
            tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.EMPLEADOR;
        } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)) {
            tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
        } else if (TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliado)) {
            tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
        }

        logger.info("Fin de método transformarTipoAfiliado");
        return tipoSolicitante;
    }

    /**
     * Método encargado de invocar el servicio que consulta el registro general por
     * id de registro general.
     * 
     * @param idRegistroGeneral
     *                          id del registro general.
     */
    private RegistroGeneralModeloDTO consultarRegistroGeneralId(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        ConsultarRegistroGeneralId consultarRegistroGeneralId = new ConsultarRegistroGeneralId(idRegistroGeneral);
        consultarRegistroGeneralId.execute();
        RegistroGeneralModeloDTO registroGeneralDTO = consultarRegistroGeneralId.getResult();
        logger.info("Fin de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        return registroGeneralDTO;
    }

    /**
     * Método encargado de invocar el servicio que consulta el aporte general por id
     * de registro general.
     * 
     * @param idRegistroGeneral
     *                          id del registro general.
     */
    private AporteGeneralModeloDTO consultarAporteGeneralPorIdRegistro(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        ConsultarAporteGeneralPorRegistro consultarAportePorRegistro = new ConsultarAporteGeneralPorRegistro(
                idRegistroGeneral);
        consultarAportePorRegistro.execute();
        AporteGeneralModeloDTO aporteGeneralDTO = consultarAportePorRegistro.getResult();
        logger.info("Fin de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        return aporteGeneralDTO;
    }

    /**
     * Método para registar un aporte sin detalle
     * 
     * @param idSolicitud
     *                        Identificador de la solicitud global
     * @param aporteManualDTO
     *                        DTO con la información del aporte
     */
    private Boolean registrarAporteSinDetalle(Long idSolicitud, AporteManualDTO aporteManualDTO) {
        logger.info("Inicio de método registrarAporteSinDetalle idSolicitud:" + idSolicitud);
        // Consulta el empleador
        AporteGeneralModeloDTO aporteGeneralDTO = transformarAporteGeneralSinDetalle(aporteManualDTO);

        RegistroGeneralModeloDTO registroGeneralDTO = consultarRegistroGeneral(idSolicitud, Boolean.FALSE);

        if (registroGeneralDTO == null) {
            registroGeneralDTO = aporteManualDTO.convertToDTO();
            registroGeneralDTO.setRegistroControlManual(idSolicitud);
            registroGeneralDTO.setOutEsEmpleador(Boolean.TRUE);
            registroGeneralDTO.setOutEstadoEmpleador(aporteGeneralDTO.getEstadoAportante());
        } else {
            aporteManualDTO.setIdRegistroGeneral(registroGeneralDTO.getId());
            registroGeneralDTO = aporteManualDTO.convertToDTO();
        }

        /* creacion de la transacción para la simulación. */
        Long idTransaccion = crearTransaccion();
        registroGeneralDTO.setTransaccion(idTransaccion);

        Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);
        /* Aplicar ejecución de Armado de tablas de Staging */
        ejecutarArmadoStaging(idTransaccion);

        Boolean cumpleSucursal = verificarCumplimientoSucursal(idRegistroGeneral);
        ejecutarBorradoStaging(idTransaccion);
        if (!cumpleSucursal) {
            return false;
        }
        aporteGeneralDTO.setIdRegistroGeneral(idRegistroGeneral);
        Long idAporte = crearActualizarAporteGeneral(aporteGeneralDTO);
        aporteGeneralDTO.setId(idAporte);

        /* datos del movimiento */
        MovimientoAporteModeloDTO movimiento = new MovimientoAporteModeloDTO();
        movimiento.setAporte(aporteGeneralDTO.getValorTotalAporteObligatorio());
        movimiento.setEstado(EstadoAporteEnum.VIGENTE);
        movimiento.setFechaActualizacionEstado(new Date());
        movimiento.setFechaCreacion(new Date());
        movimiento.setInteres(aporteGeneralDTO.getValorInteresesMora());
        movimiento.setTipoAjuste(null);
        movimiento.setTipoMovimiento(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL);
        movimiento.setIdAporteGeneral(aporteGeneralDTO.getId());
        actualizarMovimientoAporte(movimiento);

        /* se actualiza la solicitud con el id de aporte */
        SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitud);
        solicitudAporteDTO.setIdRegistroGeneral(idRegistroGeneral);
        actualizarSolicitudAporte(solicitudAporteDTO);
        logger.info("fIN de método registrarAporteSinDetalle idSolicitud:" + idSolicitud);

        return true;
    }

    /**
     * Método que transforma un <code>AporteManualDTO</code> en un
     * <code>AporteGeneralModeloDTO</code>
     * 
     * @param aporteManualDTO
     *                        El DTO a transformar
     * @return Objeto <code>AporteGeneralModeloDTO</code> equivalente
     */
    private AporteGeneralModeloDTO transformarAporteGeneralSinDetalle(AporteManualDTO aporteManualDTO) {
        AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
        aporteGeneralDTO.setCuentaBancariaRecaudo(aporteManualDTO.getRadicacionDTO().getCuentaBancariaRecaudo());
        aporteGeneralDTO.setAporteConDetalle(Boolean.FALSE);
        aporteGeneralDTO.setCodigoEntidadFinanciera(aporteManualDTO.getRadicacionDTO().getCodigoFinanciero() != null
                ? Short.parseShort(aporteManualDTO.getRadicacionDTO().getCodigoFinanciero())
                : null);
        aporteGeneralDTO.setEstadoAporteAportante(EstadoAporteEnum.VIGENTE);
        aporteGeneralDTO.setEstadoRegistroAporteAportante(null);
        aporteGeneralDTO.setFechaProcesamiento(new Date().getTime());
        aporteGeneralDTO.setFechaRecaudo(aporteManualDTO.getRadicacionDTO().getFechaRecepcionAporte());

        // Consulta el empleador
        BuscarEmpleador buscarEmpleador = new BuscarEmpleador(Boolean.TRUE,
                aporteManualDTO.getRadicacionDTO().getNumeroIdentificacion(),
                aporteManualDTO.getRadicacionDTO().getTipoIdentificacion(),
                aporteManualDTO.getRadicacionDTO().getRazonSocialAportante());
        buscarEmpleador.execute();
        List<Empleador> listaEmpleador = buscarEmpleador.getResult();
        EstadoEmpleadorEnum estadoAportante = null;
        // Si no existe empleador, lo crea
        if (listaEmpleador == null || listaEmpleador.isEmpty()) {
            // Crea la empresa
            EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
            empresaDTO.setTipoIdentificacion(aporteManualDTO.getRadicacionDTO().getTipoIdentificacion());
            empresaDTO.setNumeroIdentificacion(aporteManualDTO.getRadicacionDTO().getNumeroIdentificacion());
            empresaDTO.setDigitoVerificacion(aporteManualDTO.getRadicacionDTO().getDv());
            empresaDTO.setNombreComercial(aporteManualDTO.getRadicacionDTO().getRazonSocialAportante());
            empresaDTO.setFechaConstitucion(new Date().getTime());
            empresaDTO.setNaturalezaJuridica(aporteManualDTO.getInformacionPagoDTO().getNaturalezaJuridica());
            empresaDTO.setIdUltimaCajaCompensacion(aporteManualDTO.getRadicacionDTO().getCajaAporte());

            UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
            ubicacionDTO.setDireccionFisica(aporteManualDTO.getInformacionPagoDTO().getDireccion());
            ubicacionDTO.setDescripcionIndicacion(aporteManualDTO.getInformacionPagoDTO().getDescripcionIndicacion());
            if (aporteManualDTO.getInformacionPagoDTO() != null
                    && aporteManualDTO.getInformacionPagoDTO().getCodigoMunicipio() != null) {
                ubicacionDTO
                        .setIdMunicipio(buscarMunicipio(aporteManualDTO.getInformacionPagoDTO().getCodigoMunicipio()));
            }
            ubicacionDTO.setTelefonoFijo(aporteManualDTO.getInformacionPagoDTO().getTelefono());
            ubicacionDTO.setIndicativoTelFijo(aporteManualDTO.getInformacionPagoDTO().getTelefonoInd());
            ubicacionDTO.setEmail(aporteManualDTO.getInformacionPagoDTO().getCorreoElectronico());
            empresaDTO.setUbicacionModeloDTO(ubicacionDTO);
            Long idEmpresa = crearEmpresa(empresaDTO);
            aporteGeneralDTO.setIdEmpresa(idEmpresa);
            aporteGeneralDTO.setEstadoAportante(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
        } else {
            aporteGeneralDTO.setIdEmpresa(listaEmpleador.get(0).getEmpresa().getIdEmpresa());

            estadoAportante = listaEmpleador.get(0).getEstadoEmpleador();
            EstadoEmpleadorEnum estadoAportanteAporte = null;
            if (EstadoEmpleadorEnum.ACTIVO.equals(estadoAportante)) {
                estadoAportanteAporte = EstadoEmpleadorEnum.ACTIVO;
            } else if (EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES.equals(estadoAportante)
                    || EstadoEmpleadorEnum.INACTIVO.equals(estadoAportante)) {
                estadoAportanteAporte = EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES;
            } else if (EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION.equals(estadoAportante)
                    || EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES.equals(estadoAportante)) {
                estadoAportanteAporte = EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES;
            }
            aporteGeneralDTO.setEstadoAportante(estadoAportanteAporte);
        }
        if (estadoAportante == null
                || estadoAportante.equals(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES)
                || estadoAportante.equals(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION)) {
            aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.RELACIONADO);
        } else {
            aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.REGISTRADO);
            aporteGeneralDTO.setFechaReconocimiento(new Date().getTime());
            aporteGeneralDTO
                    .setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO);
        }

        aporteGeneralDTO.setIdOperadorInformacion(aporteManualDTO.getInformacionPagoDTO().getCodigoOperador());
        aporteGeneralDTO.setIdRegistroGeneral(aporteManualDTO.getIdRegistroGeneral());

        // Sucursal empresa
        SucursalEmpresa sucursalEmpresa = consultarSucursalEmpresa(aporteGeneralDTO.getIdEmpresa(),
                aporteManualDTO.getInformacionPagoDTO().getCodigoSucursal());

        if (sucursalEmpresa != null) {
            aporteGeneralDTO.setIdSucursalEmpresa(sucursalEmpresa.getIdSucursalEmpresa());
        }

        // Origen aporte
        aporteGeneralDTO.setOrigenAporte(aporteManualDTO.getRadicacionDTO().getOrigenAporte());

        // Caja compensación
        aporteGeneralDTO.setIdCajaCompensacion(aporteManualDTO.getRadicacionDTO().getCajaAporte());

        aporteGeneralDTO.setModalidadPlanilla(aporteManualDTO.getInformacionPagoDTO().getModalidadPlantilla());
        aporteGeneralDTO.setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum.MANUAL);
        aporteGeneralDTO.setPagadorPorTerceros(Boolean.FALSE);

        if (aporteManualDTO.getRadicacionDTO().getPeriodoPago() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            aporteGeneralDTO.setPeriodoAporte(dateFormat.format(aporteManualDTO.getRadicacionDTO().getPeriodoPago()));
        }

        aporteGeneralDTO.setTipoSolicitante(aporteManualDTO.getRadicacionDTO().getTipo());
        aporteGeneralDTO.setValorInteresesMora(aporteManualDTO.getRadicacionDTO().getMoraAporte());
        aporteGeneralDTO.setValorTotalAporteObligatorio(aporteManualDTO.getRadicacionDTO().getMontoAporte());
        return aporteGeneralDTO;
    }

    /**
     * Método que invoca el servicio de consulta de una sucursal de empresa
     * 
     * @param idEmpresa
     *                       Identificador único de la empresa
     * @param codigoSucursal
     *                       El código de la sucursal
     * @return Objeto <code>SucursalEmpresa</code> con la información de la sucursal
     *         encontrada
     */
    private SucursalEmpresa consultarSucursalEmpresa(Long idEmpresa, String codigoSucursal) {
        logger.info("Inicio de método consultarSucursalEmpresa");
        ConsultarSucursalesEmpresa service = new ConsultarSucursalesEmpresa(idEmpresa);
        service.execute();
        List<SucursalEmpresa> listaSucursalEmpresa = service.getResult();

        if (listaSucursalEmpresa != null && !listaSucursalEmpresa.isEmpty()) {
            for (SucursalEmpresa sucursalEmpresa : listaSucursalEmpresa) {
                if (sucursalEmpresa.getCodigo().equals(codigoSucursal)) {
                    return sucursalEmpresa;
                }
            }
        }

        logger.info("Fin de método consultarSucursalEmpresa");
        return null;
    }

    /**
     * Método que invoca el servicio de creación de persona
     * 
     * @param personaDTO
     *                   La información de la persona a crear
     * @return El identificador del registro generado
     */
    private Long crearPersona(PersonaModeloDTO personaDTO) {
        logger.info("Inicio de método crearPersona");
        CrearPersona crearPersona = new CrearPersona(personaDTO);
        crearPersona.execute();
        logger.info("Fin de método crearPersona");
        return crearPersona.getResult();
    }

    /**
     * Método que invoca el servicio de creación de empresa
     * 
     * @param empresaDTO
     *                   La información de la empresa
     * @return El identificador del registro generado
     */
    private Long crearEmpresa(EmpresaModeloDTO empresaDTO) {
        logger.info("Inicio de método crearPersona");
        CrearEmpresa crearEmpresa = new CrearEmpresa(empresaDTO);
        crearEmpresa.execute();
        logger.info("Fin de método crearPersona");
        return crearEmpresa.getResult();
    }

    @Override
    @Asynchronous
    public void registrarAporteConDetalleAsync(OrigenAporteEnum origenAporte, Integer cajaAporte, Long idSolicitud,
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Boolean pagadorTercero, Long idRegistroGeneral,
        Long fechaRecaudo, Integer cuentaBancariaRecaudo) {
            registrarAporteConDetalle(origenAporte, cajaAporte, idSolicitud, tipoSolicitante, pagadorTercero, idRegistroGeneral, fechaRecaudo, cuentaBancariaRecaudo);
    }

    /**
     * Método para registrar un aporte con detalle.
     */
    private void registrarAporteConDetalle(OrigenAporteEnum origenAporte, Integer cajaAporte, Long idSolicitud,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Boolean pagadorTercero, Long idRegistroGeneral,
            Long fechaRecaudo, Integer cuentaBancariaRecaudo) {
        logger.info("Inicio de método registrarAporteConDetalle");
        /*
         * se consultan las tablas temporales y se guarda la información del aporte
         * general y detallado
         */
        List<AporteDTO> aportes = consultarInformacionTemporal(idRegistroGeneral);
        List<TemNovedad> novedades = consultarInformacionTemporalNovedad(idRegistroGeneral);

        logger.info("**__**Manueles idRegistroGeneral a procesar:" + idRegistroGeneral);
        logger.info("**__**Manueles tem novedades a procesar:" + novedades.size());

        int ciclosAportes = 1;
        String cedulaNovedadTemp = "";
        for (AporteDTO aporteDTO : aportes) {
            List<NovedadPilaDTO> novedadesPila = new ArrayList<>();
            // Corregir consulta con el orden y verificarlo en este servicio
            Long idRegistroTemporal = 0L;
            String cedulaAporte = "";

            cedulaAporte = aporteDTO.getPersonaCotizante().getNumeroIdentificacion();
            logger.info("**__**cedulaAporte1111111111:" + cedulaAporte + "L ciclosAportes:" + ciclosAportes);
            if (!cedulaNovedadTemp.equals(cedulaAporte)) {
                cedulaNovedadTemp = cedulaAporte;
                String cedulaNovedad = "";
                for (TemNovedad temNovedad : novedades) {
                    logger.info("**__**¨[[[]]]. : getAccionNovedad: " + temNovedad.getAccionNovedad());
                    logger.info("**__**¨  [[[]]]  : getRegistroDetallado" + temNovedad.getRegistroDetallado());
                    logger.info("**__**¨ [[[]]] getTipoTransaccion: " + temNovedad.getTipoTransaccion());
                    logger.info("**__**¨ [[[]]] getFechaInicioNovedad: " + temNovedad.getFechaInicioNovedad());
                    logger.info("**__**temNovjnnnnvv:" + temNovedad.getNumeroIdCotizante() + "L");
                    logger.info(
                            "**__**temNovedajnnn:" + aporteDTO.getPersonaCotizante().getNumeroIdentificacion() + "L");

                    cedulaNovedad = temNovedad.getNumeroIdCotizante();
                    logger.info("**__**cedulaAporte:" + cedulaAporte + "L");
                    logger.info("**__**cedulaNovedad:" + cedulaNovedad + "L");
                    if (cedulaAporte.equals(cedulaNovedad)) {
                        logger.info(
                                "**__**¨-----------------------------------------------------------------------------------------------");
                        logger.info("**__**temNovedad.getRegistroDetallado()" + temNovedad.getRegistroDetallado());
                        logger.info("**__**temNovedad.getTipoTransaccion()" + temNovedad.getTipoTransaccion());
                        logger.info("**__**temNovedad.getFechaInicioNovedad()" + temNovedad.getFechaInicioNovedad());
                        logger.info("**__**temNovedad.getNumeroIdCotizante()" + temNovedad.getNumeroIdCotizante());
                        logger.info("**__**temNovedad.getNumeroIdentificacion()"
                                + aporteDTO.getPersonaCotizante().getNumeroIdentificacion());
                        logger.info(
                                "**__**¨******************************************************************************");
                        NovedadPilaDTO novedadPilaDTO = new NovedadPilaDTO(temNovedad.getMarcaNovedadSimulado(),
                                temNovedad.getMarcaNovedadManual(), temNovedad.getTipoTransaccion(),
                                temNovedad.getEsIngreso(),
                                temNovedad.getEsRetiro(),
                                temNovedad.getTipoIdCotizante() != null
                                        ? TipoIdentificacionEnum.valueOf(temNovedad.getTipoIdCotizante())
                                        : null,
                                temNovedad.getNumeroIdCotizante(), temNovedad.getFechaInicioNovedad(),
                                temNovedad.getFechaFinNovedad(),
                                temNovedad.getAccionNovedad(), temNovedad.getMensajeNovedad(),
                                temNovedad.getTipoCotizante() != null
                                        ? TipoAfiliadoEnum.valueOf(temNovedad.getTipoCotizante())
                                        : aporteDTO.getAporteDetallado().getTipoCotizante(),
                                temNovedad.getValor());
                        novedadPilaDTO.setIdRegistroDetallado(temNovedad.getRegistroDetallado());
                        novedadPilaDTO.setIdRegistroDetalladoNovedad(temNovedad.getRegistroDetalladoNovedad());
                        novedadPilaDTO.setIdRegistroGeneral(idRegistroGeneral);
                        novedadesPila.add(novedadPilaDTO);
                        idRegistroTemporal = temNovedad.getRegistroDetallado();

                    } else {
                        logger.info("**__**sddddddd");
                        // break;
                    }
                }
            }
            cedulaAporte = "";
            aporteDTO.setNovedades(novedadesPila);
            aporteDTO.getAporteGeneral().setTipoSolicitante(tipoSolicitante);
            aporteDTO.getAporteGeneral().setPagadorPorTerceros(pagadorTercero);
            aporteDTO.getAporteGeneral().setOrigenAporte(origenAporte);
            aporteDTO.getAporteGeneral().setIdCajaCompensacion(cajaAporte);
            aporteDTO.setEsManual(Boolean.TRUE);
            if (fechaRecaudo != null) {
                aporteDTO.getAporteGeneral().setFechaRecaudo(fechaRecaudo);
            }
            if (cuentaBancariaRecaudo != null) {
                aporteDTO.getAporteGeneral().setCuentaBancariaRecaudo(cuentaBancariaRecaudo);
            }
            ciclosAportes++;
        }
        for (AporteDTO aporteDTO1 : aportes) {
            for (NovedadPilaDTO novpila1 : aporteDTO1.getNovedades()) {
                logger.info("**__**¨ PilaAportesManuales>>..*.. : getAccionNovedad: " + novpila1.getAccionNovedad());
                logger.info("**__**¨ PilaAportesManuales>>..*.. : getNumeroIdentificacionCotizante"
                        + novpila1.getNumeroIdentificacionCotizante());
                logger.info("**__**¨ PilaAportesManuales>>..*.. getTipoTransaccion: " + novpila1.getTipoTransaccion());
                logger.info("**__**¨ PilaAportesManuales>>..*.. getFechaInicioNovedad: "
                        + novpila1.getFechaInicioNovedad());
            }
        }
        /* Se actualizan los aportes por HU-480 que apliquen */
        List<Long> ids = new ArrayList<>();
        ids.add(idRegistroGeneral);
        ActualizacionAportesRecalculados actualizacionAportes = new ActualizacionAportesRecalculados(Boolean.TRUE, ids);
        actualizacionAportes.execute();
        // entra aqui aportes manuales ruta donde hace persist(aportedetallado)
        /* se registra la información del aporte */
        aportesCompositeService.registrarRelacionarListadoAportes(aportes);
        logger.info("Fin de método registrarAporteConDetalle");
    }

    /**
     * Método para cambiar el estado del aporte y terminar la tarea.
     */
    private void cambiarEstadoAporte(Long idSolicitud, AporteManualDTO aporteManualDTO, UserDTO userDTO, Long idTarea) {
        try {
            if (aporteManualDTO.getInformacionFaltante() != null
                    && aporteManualDTO.getInformacionFaltante().getInformacionFaltante() != null) {
                guardarInformacionFaltante(idSolicitud, aporteManualDTO.getInformacionFaltante());
            }
            actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL,
                    EstadoSolicitudAporteEnum.NOTIFICADA, null, userDTO);
            actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL, EstadoSolicitudAporteEnum.CERRADA,
                    null, userDTO);

            // Evalua si previamente se ha suspendido la tarea, a fin de retomarla para
            // posterior cierre
            this.terminarTareaAporte(idTarea,null,0);
        } catch (Exception e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private void terminarTareaAporte(Long idTarea, Map<String, Object> params, int intentos){
       try{
            this.retomarTarea(idTarea, null);
            this.terminarTarea(idTarea, null);
       }catch (Exception e) {
            logger.info("Exception en terminarTareaAporte para idTarea: " + idTarea + ". Intento: " + intentos);
            if (intentos <= 2) {
                terminarTareaAporte(idTarea, null, intentos + 1);
            } else {
                // Si se alcanzó el número máximo de intentos, lanzar la excepción
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
        }
    }

    /**
     * Método encargado de invocar el servicio de creación o actualización de la
     * corrección.
     * 
     * @param correccionDTO
     *                      dto de la corrección.
     * @return id de la corrección.
     */
    private Long crearActualizarCorreccion(CorreccionModeloDTO correccionDTO) {
        logger.info("Inicio de método crearActualizarCorreccion(CorreccionModeloDTO correccionDTO)");
        CrearActualizarCorreccion crearActualizarCorreccionService = new CrearActualizarCorreccion(correccionDTO);
        crearActualizarCorreccionService.execute();
        Long idCorreccion = crearActualizarCorreccionService.getResult();
        logger.info("Inicio de método crearActualizarCorreccion(CorreccionModeloDTO correccionDTO)");
        return idCorreccion;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#actualizarTrazabilidadPorSolicitud(java.lang.Long)
     */
    @Override
    public void actualizarComunicadoTrazabilidad(Long idSolicitud, ActividadEnum actividad) {

        List<ComunicadoModeloDTO> lstComunicados = consultarComunicadosPorSolicitud(idSolicitud);

        if (!lstComunicados.isEmpty()) {
            // ordenar descendente
            lstComunicados.sort(Comparator.comparing(ComunicadoModeloDTO::getIdComunicado).reversed());
            /* se toma el primer comunicado porque es el mas reciente */
            RegistroEstadoAporteModeloDTO trazabilidad = consultarTrazabilidadPorActividad(idSolicitud, actividad);
            if (trazabilidad != null) {
                trazabilidad.setIdComunicado(lstComunicados.get(0).getIdComunicado());
                actualizarTrazabilidad(trazabilidad);
            }
        }
    }

    /**
     * Método encargado llamar el cliente del servicio que consulta los
     * comunicados por solicitud
     * 
     * @param idSolicitud,
     *                     id de la solicitud a consultar
     * @return retorna la lista de comunicados pertenecientes a la solicitud
     */
    private List<ComunicadoModeloDTO> consultarComunicadosPorSolicitud(Long idSolicitud) {
        ConsultarComunicadoPorSolicitud comunicado = new ConsultarComunicadoPorSolicitud(idSolicitud);
        comunicado.execute();
        return comunicado.getResult();
    }

    /**
     * Método encargado de llamar el cliente del servicio que actualiza la
     * trazabilidad
     * 
     * @param registroEstadoAporteModeloDTO,
     *                                       trazabilidad a actualizar
     */
    private void actualizarTrazabilidad(RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO) {
        ActualizarTrazabilidad trazabilidad = new ActualizarTrazabilidad(registroEstadoAporteModeloDTO);
        trazabilidad.execute();
    }

    /**
     * Método encargado de consultar la trazabilidad de la solicitud por id
     * 
     * @param idSolicitud,
     *                     id la trazabilidad a consultar
     * @return retorna la trazabilidad encontrada
     */
    private RegistroEstadoAporteModeloDTO consultarTrazabilidadPorActividad(Long idSolicitud, ActividadEnum actividad) {
        ConsultarTrazabilidadPorActividad trazabilidad = new ConsultarTrazabilidadPorActividad(idSolicitud, actividad);
        trazabilidad.execute();
        return trazabilidad.getResult();
    }

    /**
     * Método encargado de consultar el identificador de un Municipio por el codigo
     * 
     * @param registroEstadoAporteModeloDTO,
     *                                       trazabilidad a actualizar
     */
    private Short buscarMunicipio(String codigoMunicipio) {
        logger.info("Inicio de método buscarMunicipio(Long codigoMunicipio)");
        BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipio);
        buscarMunicipio.execute();
        logger.info("Finaliza método buscarMunicipio(Long codigoMunicipio)");
        return buscarMunicipio.getResult();
    }

    /**
     * Método que invoca el servicio de creación/actualización de un movimiento de
     * aporte.
     * 
     * @param movimientoAporteDTO
     *                            registro a crear/actualizar
     */
    private void actualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteDTO) {
        ActualizarMovimientoAporte actualizarMovimientoAporte = new ActualizarMovimientoAporte(movimientoAporteDTO);
        actualizarMovimientoAporte.execute();
    }

    /**
     * Método que consulta el estado de una persona respecto a la caja por su tipo y
     * número de identificación
     * 
     * @param valorTI
     *                    Tipo de identificación de la persona
     * @param valorNI
     *                    Número de identificación de la persona
     * @param idEmpleador
     *                    Id del empleador
     */
    private EstadoAfiliadoEnum consultarEstadoCajaPersona(Long idEmpleador, String valorNI,
            TipoIdentificacionEnum valorTI) {
        logger.info("Inicia consultarEstadoCajaPersona(valorTI, valorNI, idEmpleador)");
        ConsultarEstadoCajaPersona consultarEstadoCajaPersonaService = new ConsultarEstadoCajaPersona(idEmpleador,
                valorNI, valorTI);
        consultarEstadoCajaPersonaService.execute();
        logger.info("Finaliza consultarEstadoCajaPersona(valorTI, valorNI, idEmpleador)");
        if (consultarEstadoCajaPersonaService.getResult() != null) {
            return consultarEstadoCajaPersonaService.getResult();
        } else {
            return EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
        }
    }

    /**
     * Método que consulta el estado de una persona respecto a la caja por su tipo y
     * número de identificación
     * 
     * @param estadosCaja
     *                    lista con los cotizantes a saber el estado de la caja.
     */
    private List<ConsultaEstadoCajaPersonaDTO> buscarEstadoCajaPersonasMasivo(
            List<ConsultaEstadoCajaPersonaDTO> estadosCaja, Integer tamanoPaginador) {
        logger.info(
                "Inicia buscarEstadoCajaPersonasMasivo(Long idEmpleador, String valorNI, TipoIdentificacionEnum valorTI)");

        List<ConsultaEstadoCajaPersonaDTO> resultado = new ArrayList<>();
        List<ConsultaEstadoCajaPersonaDTO> listaProceso = new ArrayList<>();
        Integer count = 0;
        for (ConsultaEstadoCajaPersonaDTO cajaPersonaDTO : estadosCaja) {
            count++;
            listaProceso.add(cajaPersonaDTO);
            if (count.intValue() == tamanoPaginador.intValue()) {
                BuscarEstadoCajaPersonasMasivo buscarEstadoCajaPersonaService = new BuscarEstadoCajaPersonasMasivo(
                        listaProceso);
                buscarEstadoCajaPersonaService.execute();
                resultado.addAll(buscarEstadoCajaPersonaService.getResult());

                count = 0;
                listaProceso = new ArrayList<>();
            }
        }

        if (count > 0) {
            BuscarEstadoCajaPersonasMasivo buscarEstadoCajaPersonaService = new BuscarEstadoCajaPersonasMasivo(
                    listaProceso);
            buscarEstadoCajaPersonaService.execute();
            resultado.addAll(buscarEstadoCajaPersonaService.getResult());
        }

        logger.info(
                "Finaliza buscarEstadoCajaPersonasMasivo(Long idEmpleador, String valorNI, TipoIdentificacionEnum valorTI)");
        return resultado;
    }

    /**
     * Método encargado de actualizar los estados de
     * 
     * @param registroEstadoAporteModeloDTO,
     *                                       trazabilidad a actualizar
     */
    private void actualizarEstadoRegistroDetallado(List<Long> idsCotizante) {
        logger.info("Inicio de método actualizarEstadoRegistroDetallado(List<Long> idsCotizante)");
        CambiarEstadoRegistroDetallado cambiarEstadoRegistroDetallado = new CambiarEstadoRegistroDetallado(
                idsCotizante);
        cambiarEstadoRegistroDetallado.execute();
        logger.info("Finaliza método actualizarEstadoRegistroDetallado(List<Long> idsCotizante)");
    }

    /**
     * Método que se encarga de invocar el servicio de consultar empleadores por
     * tipo y número de identificación.
     * 
     * @param tipoIdentificacion
     *                             tipo de identificación.
     * @param numeroIdentificacion
     *                             número de identificación.
     * @return empleador consultado.
     */
    private EmpleadorModeloDTO consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.info(
                "Inicio de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarEmpleadorTipoNumero consultarEmpleadorService = new ConsultarEmpleadorTipoNumero(numeroIdentificacion,
                tipoIdentificacion);
        consultarEmpleadorService.execute();
        logger.info(
                "Fin de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return consultarEmpleadorService.getResult();
    }

    /**
     * Método que invoca el servicio que consulta los trabajadores activos para un
     * periodo determinado.
     * 
     * @param periodo
     *                    a consultar.
     * @param idEmpleador
     *                    id del empleador a consultarle los trabajadores.
     * @return lista de los id de los roles afiliados.
     */
    private List<Long> obtenerTrabajadoresActivosPeriodo(Long periodo, Long idEmpleador) {
        logger.info("Inicio de método obtenerTrabajadoresActivosPeriodo(Long periodo, Long idEmpleador)");
        Date fechaActual = new Date();
        if (periodo < fechaActual.getTime()) {
            LocalDate localDateBase = Instant.ofEpochMilli(periodo).atZone(ZoneId.systemDefault()).toLocalDate();
            localDateBase = localDateBase.withDayOfMonth(1);
            Long startDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            localDateBase = localDateBase.plusMonths(1L);
            Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

            ObtenerTrabajadoresActivosPeriodo obtenerTrabajadoresService = new ObtenerTrabajadoresActivosPeriodo(
                    startDate, idEmpleador,
                    endDate);
            obtenerTrabajadoresService.execute();
            logger.info("Fin de método obtenerTrabajadoresActivosPeriodo(Long periodo, Long idEmpleador)");
            return obtenerTrabajadoresService.getResult();
        }
        List<Long> listaVacia = new ArrayList<>();
        return listaVacia;
    }

    /**
     * Método encargado de consultar los cotizantes por rol.
     * 
     * @param idRoles
     *                id de los roles.
     * @return lista de los cotizantes.
     */
    private List<CotizanteDTO> consultarCotizantesPorRol(List<Long> idRoles, Integer tamanoPaginador) {
        logger.info("Inicio de método consultarCotizantesPorRol(List<Long> idRoles)");

        List<CotizanteDTO> resultado = new ArrayList<>();

        List<Long> listaProceso = new ArrayList<>();
        Integer count = 0;
        for (Long idRole : idRoles) {
            count++;
            listaProceso.add(idRole);
            if (count.intValue() == tamanoPaginador.intValue()) {
                ConsultarCotizantesPorRol consultarCotizanteService = new ConsultarCotizantesPorRol(listaProceso);
                consultarCotizanteService.execute();
                resultado.addAll(consultarCotizanteService.getResult());

                count = 0;
                listaProceso = new ArrayList<>();
            }
        }

        if (count > 0) {
            ConsultarCotizantesPorRol consultarCotizanteService = new ConsultarCotizantesPorRol(listaProceso);
            consultarCotizanteService.execute();
            resultado.addAll(consultarCotizanteService.getResult());
        }

        logger.info("Fin de método consultarCotizantesPorRol(List<Long> idRoles)");
        return resultado;
    }

    /**
     * Método que invoca el servicio obtenerEstadoTrabajador.
     * 
     * @param startDate
     *                    inicio del periodo.
     * @param endDate
     *                    fin del periodo
     * @param idEmpleador
     *                    id del empleador
     * @param idAfiliado
     *                    id del afiliado.
     * @return estado del afiliado.
     */
    private EstadoAfiliadoEnum obtenerEstadoTrabajador(Long startDate, Long endDate, Long idEmpleador, Long idAfiliado,
            TipoAfiliadoEnum tipoAfiliado) {
        logger.info("Inicio de método obtenerEstadoTrabajador");
        ObtenerEstadoTrabajador obtenerEstadoService = new ObtenerEstadoTrabajador(startDate, idEmpleador, idAfiliado,
                tipoAfiliado,
                endDate);
        obtenerEstadoService.execute();
        logger.info("Fin de método obtenerEstadoTrabajador");
        return obtenerEstadoService.getResult();
    }

    /**
     * Método que invoca el servicio que consulta el estado de un aportante en un
     * período dado
     * 
     * @param startDate
     *                        inicio del periodo.
     * @param endDate
     *                        fin del periodo.
     * @param idAportante
     *                        id del aportante.
     * @param tipoSolicitante
     *                        tipo de solicitante de aporte
     * @return estado del aportante.
     */
    private EstadoEmpleadorEnum obtenerEstadoAportantePeriodo(Long startDate, Long endDate, Long idAportante,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.info("Inicio de método obtenerEstadoEmpleadorPeriodo");
        ObtenerEstadoAportantePeriodo obtenerEstadoService = new ObtenerEstadoAportantePeriodo(startDate,
                tipoSolicitante, idAportante,
                endDate);
        obtenerEstadoService.execute();
        logger.info("Fin de método obtenerEstadoEmpleadorPeriodo");
        return obtenerEstadoService.getResult();
    }

    /**
     * Método encargado de invocar el servicio que consulta los datos del afiliado.
     * 
     * @param tipoIdentificacion
     *                             tipo de identificación del afiliado.
     * @param numeroIdentificacion
     *                             número de identificación del afiliado.
     * @return datos del afiliado.
     */
    private AfiliadoModeloDTO consultarDatosAfiliado(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.info("Inicio de método consultarDatosAfiliado");
        ConsultarDatosAfiliado consultarDatosAfiliadoService = new ConsultarDatosAfiliado(numeroIdentificacion,
                tipoIdentificacion);
        consultarDatosAfiliadoService.execute();
        logger.info("Fin de método consultarDatosAfiliado");
        return consultarDatosAfiliadoService.getResult();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#verificarDiaVencimientoAportantes()
     */
    @Override
    public void verificarDiaVencimientoAportantes() {
        String firmaServicio = "AportesManualesCompositeBusiness.verificarDiaVencimientoAportantes()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        // se consultan los aportantes sin día de vencimiento
        ConsultarAportantesSinVencimiento consultarAportantesSinVencimiento = new ConsultarAportantesSinVencimiento();
        consultarAportantesSinVencimiento.execute();

        List<AportanteDiaVencimientoDTO> aportantes = consultarAportantesSinVencimiento.getResult();

        // se calcula el día de vencimiento para cada aportante de acuerdo a su tipo
        for (AportanteDiaVencimientoDTO aportante : aportantes) {

            TipoArchivoPilaEnum tipoArchivo = null;
            ClaseAportanteEnum claseAportante = null;
            Integer cantidadPersonas = null; // TODO pendiente definición de origen del dato

            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportante.getTipoSolicitanteAporte())) {
                cantidadPersonas = 200;
                tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_I;

                if (aportante.getIdBeneficioLey() == null && cantidadPersonas >= 200) {
                    claseAportante = ClaseAportanteEnum.CLASE_A;
                } else if (aportante.getIdBeneficioLey() == null && cantidadPersonas < 200) {
                    claseAportante = ClaseAportanteEnum.CLASE_B;
                } else if (aportante.getIdBeneficioLey() == 1) {
                    claseAportante = ClaseAportanteEnum.CLASE_C;
                } else if (aportante.getIdBeneficioLey() == 2) {
                    claseAportante = ClaseAportanteEnum.CLASE_D;
                }
            } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(aportante.getTipoSolicitanteAporte())) {
                cantidadPersonas = 0;
                claseAportante = ClaseAportanteEnum.CLASE_I;
                tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_I;
            } else {
                cantidadPersonas = 0;
                claseAportante = ClaseAportanteEnum.CLASE_I;
                tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_IP;
            }

            Calendar fechaActual = Calendar.getInstance();
            fechaActual.setTime(new java.util.Date());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            if (!TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportante.getTipoSolicitanteAporte())) {
                fechaActual.add(Calendar.MONTH, 1);
            }
            String fechaPeriodo = dateFormat.format(fechaActual.getTime());

            String codigoNaturalezaJuridica;
            if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(aportante.getTipoSolicitanteAporte())
                    || TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(aportante.getTipoSolicitanteAporte())) {
                codigoNaturalezaJuridica = NaturalezaJuridicaEnum.PRIVADA.getCodigo().toString();
            } else {
                Empleador empleador = consultarEmpleador(aportante.getIdRegistro());
                if (empleador != null && empleador.getEmpresa() != null
                        && empleador.getEmpresa().getNaturalezaJuridica() != null) {
                    codigoNaturalezaJuridica = empleador.getEmpresa().getNaturalezaJuridica().getCodigo().toString();
                } else {
                    /*
                     * cuando no se cuenta con el dato del código de la naturaleza jurídica, se
                     * considera como privada para
                     * fines del cálculo del día hábil de vencimiento del aporte
                     */
                    codigoNaturalezaJuridica = "2";
                }
            }

            NaturalezaJuridicaEnum naturalezaJuridica = NaturalezaJuridicaEnum
                    .obtenerNaturalezaJuridica(Integer.parseInt(codigoNaturalezaJuridica));
            CalcularDiaHabilVencimientoAporte calcularDiaHabilVencimientoAporte = new CalcularDiaHabilVencimientoAporte(
                    naturalezaJuridica,
                    cantidadPersonas, tipoArchivo, claseAportante, fechaPeriodo, aportante.getNumeroIdentificacion());

            calcularDiaHabilVencimientoAporte.execute();

            aportante.setDiaHabilVencimiento(calcularDiaHabilVencimientoAporte.getResult());
        }

        if (aportantes != null && !aportantes.isEmpty()) {
            // se actualizan los empleadores
            ActualizarDiaHabilVencimientoAporte actualizarDiaHabilVencimientoAporte = new ActualizarDiaHabilVencimientoAporte(
                    aportantes);
            actualizarDiaHabilVencimientoAporte.execute();
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Servicio que consulta el empleador por el identificador
     * 
     * @param idEmpleador
     *                    Identificador del empleador
     * @return Empleador
     */
    private Empleador consultarEmpleador(Long idEmpleador) {
        try {
            logger.info("Inicia consultarEmpleador(Long idEmpleador)");
            ConsultarEmpleador empleadorService = new ConsultarEmpleador(idEmpleador);
            empleadorService.execute();
            logger.info("Finaliza consultarEmpleador(Long idEmpleador)");
            return empleadorService.getResult();
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultar el empleador en aportes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Busca una lista de personas pero sin el detalle de la misma
     * 
     * @param tipoIdentificacion
     *                             Tipo de identificación de la persona
     * @param numeroIdentificacion
     *                             Número de identificación de la persona
     * @param primerNombre
     *                             Primer nombre asociado a la persona
     * @param primerApellido
     *                             Primer apellido asociado a la persona
     * @param segundoNombre
     *                             Segundo nombre asociado a la persona
     * @param segundoApellido
     *                             Segundo apellido asociado a la persona
     * @param razonSocial
     *                             Razon social de la persona
     * @return Lista de personas DTO
     */
    private List<PersonaDTO> buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            String razonSocial) {
        try {
            logger.info("Inicio de método buscarPersonasSinDetalle");
            BuscarPersonasSinDetalle buscarPersonaSinDetalleServices = new BuscarPersonasSinDetalle(primerApellido,
                    primerNombre,
                    segundoApellido, numeroIdentificacion, tipoIdentificacion, segundoNombre, razonSocial);
            buscarPersonaSinDetalleServices.execute();
            logger.info("Fin de método buscarPersonasSinDetalle");
            return buscarPersonaSinDetalleServices.getResult();
        } catch (Exception e) {
            logger.error("Ocurrió un error en buscarPersonasSinDetalle", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#validarTotalAportes(java.lang.Long)
     */
    @Override
    public Boolean validarTotalAportes(Long idSolicitud) {
        try {
            logger.info("Inicio de método validarTotalAportes(Long idSoliciutd)");
            /* se consultan los datos temporales para simularlos */
            AporteManualDTO aporteManualDTO = consultarAporteManualTemporal(idSolicitud);
            List<CotizanteDTO> cotizantesTemporales = aporteManualDTO.getCotizantesTemporales();
            BigDecimal sumaAportesCotizante = BigDecimal.ZERO;
            BigDecimal AportesCotizante = BigDecimal.ZERO;

            if (cotizantesTemporales == null || cotizantesTemporales.isEmpty()) {
                logger.info("cotizantesTemporales == null");
                logger.info("Finaliza de método validarTotalAportes(Long idSoliciutd)");
                return false;
            } else {
                for (CotizanteDTO cotizanteDTO : cotizantesTemporales) {
                    BigDecimal aporte = cotizanteDTO.getAporteObligatorio() != null
                            ? cotizanteDTO.getAporteObligatorio()
                            : BigDecimal.ZERO;
                    BigDecimal mora = cotizanteDTO.getValorMora() != null ? cotizanteDTO.getValorMora()
                            : BigDecimal.ZERO;
                    sumaAportesCotizante = sumaAportesCotizante.add(aporte.add(mora));
                }
                logger.info("Finaliza de método validarTotalAportes(Long idSoliciutd)");
                AportesCotizante = sumaAportesCotizante.setScale(2, RoundingMode.HALF_EVEN);
                Boolean resultadoValidacionSumaAporte = AportesCotizante
                        .compareTo(aporteManualDTO.getRadicacionDTO().getTotalAporte()) != 0;

                logger.info("resultadoValidacionSumaAporte: " + resultadoValidacionSumaAporte);
                logger.info(sumaAportesCotizante);
                logger.info(AportesCotizante);
                logger.info(aporteManualDTO.getRadicacionDTO().getTotalAporte());
                return (AportesCotizante.compareTo(aporteManualDTO.getRadicacionDTO().getTotalAporte()) != 0) ? false
                        : true;
            }
        } catch (Exception e) {
            logger.error("Ocurrio un error validando el total de aportes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarSolicitanteCuentaAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String,
     *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public List<SolicitanteDTO> consultarSolicitanteCuentaAporte(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            String razonSocial,
            TipoSolicitanteMovimientoAporteEnum tipoAportante, Long numeroOperacionAportante) {
        String firmaServicio = "AportesManualesCompositeBusiness.consultarSolicitanteCuentaAporte(TipoIdentificacionEnum, String,"
                + " String, String, String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> idsPersonas = new ArrayList<>();
        List<SolicitanteDTO> solicitantes = new ArrayList<>();
        if (numeroOperacionAportante != null) {
            AporteGeneralModeloDTO aporteGeneral = consultarAporteGeneral(numeroOperacionAportante);
            if (aporteGeneral != null) {
                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aporteGeneral.getTipoSolicitante())) {
                    EmpresaModeloDTO empresa = consultarEmpresaPorId(aporteGeneral.getIdEmpresa());
                    idsPersonas.add(empresa.getIdPersona());
                } else {
                    idsPersonas.add(aporteGeneral.getIdPersona());
                }
            }
        }
        if (!idsPersonas.isEmpty()) {
            solicitantes.addAll(consultarSolicitantesAporteGeneral(idsPersonas));
        } else {
            List<PersonaDTO> personas = buscarPersonasSinDetalle(tipoIdentificacion, numeroIdentificacion, primerNombre,
                    primerApellido,
                    segundoNombre, segundoApellido, razonSocial);
            if (personas != null && !personas.isEmpty()) {
                for (PersonaDTO persona : personas) {
                    idsPersonas.add(persona.getIdPersona());
                }
                solicitantes.addAll(consultarSolicitantesAporteGeneral(idsPersonas));
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return solicitantes;
    }

    /**
     * Método que filtra un listado de solicitantes para obtener sólo los que
     * correspondan a un tipo determinado
     * 
     * @param solicitantes
     *                      Listado inicial de solicitantes
     * @param tipoAportante
     *                      Tipo especifico de solicitante consultado
     * @return <b>List<SolicitanteDTO></b>
     *         Listado de solicitantes actualizado
     */
    private List<SolicitanteDTO> filtrarSolicitantesPorTipo(List<SolicitanteDTO> solicitantes,
            TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        String firmaMetodo = "AportesManualesCompositeBusiness.filtrarSolicitantesPorTipo(List<SolicitanteDTO>, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<SolicitanteDTO> result = null;

        if (tipoAportante != null) {
            result = new ArrayList<>();
            for (SolicitanteDTO solicitante : solicitantes) {
                if (tipoAportante.equals(solicitante.getTipoSolicitante())) {
                    result.add(solicitante);
                }
            }
        } else {
            result = solicitantes;
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#validarTotalAportesCorreccion(java.lang.Long)
     */
    @Override
    public Boolean validarTotalAportesCorreccion(CorreccionAportanteDTO correccion) {
        try {
            logger.debug("Inicio de método validarTotalAportesCorreccion(CorreccionAportanteDTO correccion)");

            if (correccion == null) {
                logger.debug(
                        "Finaliza de método validarTotalAportesCorreccion(CorreccionAportanteDTO correccion):Parametros incompletos");
                return false;
            } else {
                BigDecimal valorAporteCorreccion = BigDecimal.ZERO;
                BigDecimal valorMoraCorreccion = BigDecimal.ZERO;

                for (CotizanteDTO cotizanteDTO : correccion.getCotizantesNuevos()) {
                    valorAporteCorreccion = valorAporteCorreccion
                            .add(cotizanteDTO.getAporteObligatorio() != null ? cotizanteDTO.getAporteObligatorio()
                                    : BigDecimal.ZERO);
                    valorMoraCorreccion = valorMoraCorreccion
                            .add(cotizanteDTO.getValorMora() != null ? cotizanteDTO.getValorMora() : BigDecimal.ZERO);
                }

                if (correccion.getValTotalApoObligatorio().equals(valorAporteCorreccion)
                        && correccion.getValorIntMora().equals(valorMoraCorreccion)) {
                    logger.info("Finaliza de método validarTotalAportesCorreccion(CorreccionAportanteDTO correccion)");
                    return true;
                } else {
                    logger.info("Finaliza de método validarTotalAportesCorreccion(CorreccionAportanteDTO correccion)");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrio un error validando el total de aportes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de actualizar los estados del registro general
     * 
     * @param idRegistroGeneralidentificador
     *                                       del registro general
     */
    private void actualizarEstadoRegistroGeneral(List<Long> idRegistroGeneral) {
        logger.info("Inicio de método actualizarEstadoRegistroGeneral(Long idRegistroGeneral)");
        CambiarEstadoRegistroGeneral cambiarEstadoRegistroGeneral = new CambiarEstadoRegistroGeneral(idRegistroGeneral);
        cambiarEstadoRegistroGeneral.execute();
        logger.info("Finaliza método actualizarEstadoRegistroGeneral(Long idRegistroGeneral)");
    }

    /**
     * Método encargado de consultar os solicitantes dada una lista de personas
     * 
     * @param idRegistroGeneralidentificador
     *                                       del registro general
     */
    private List<SolicitanteDTO> consultarSolicitantesAporteGeneral(List<Long> idsPersonas) {
        String firmaMetodo = "ApotesManualesCompositeBusiness.consultarSolicitantesAporteGeneral(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarSolicitanteCorreccionCuentaAportesIds solicitantesService = new ConsultarSolicitanteCorreccionCuentaAportesIds(
                idsPersonas);
        solicitantesService.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitantesService.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de medios de pago
     * 
     * @param medioDePagoModeloDTO
     *                             Información del medio de pago a almacenar
     * @return La información del medio de pago, actualizada
     */
    private MedioDePagoModeloDTO guardarMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO) {
        logger.debug("Inicio de método guardarMedioDePago");
        GuardarMedioDePago service = new GuardarMedioDePago(medioDePagoModeloDTO);
        service.execute();
        logger.debug("Finaliza método guardarMedioDePago");
        return service.getResult();
    }

    /**
     * Método para registar un aporte de una correccion sin detalle
     * 
     * @param idSolicitud
     *                               Identificador de la solicitud global
     * @param correccionAportanteDTO
     *                               DTO con la información de la corrección para el
     *                               aporte
     */
    private List<BigDecimal> registrarAporteCorreccionSinDetalle(Long idSolicitud,
            CorreccionAportanteDTO correccionAportanteDTO,
            Long idSolicitudCorreccion, Boolean sinDetalleInicio, Long idAporteGeneralAntiguo,
            Long idAporteDetalladoAntiguo) {
        RegistroGeneralModeloDTO registroGeneralDTO = correccionAportanteDTO.convertToDTO();
        // logger.info("registrarAporteCorreccionSinDetalle");
        registroGeneralDTO.setRegistroControlManual(idSolicitud);
        if (correccionAportanteDTO.getIdRegistroGeneral() != null) {
            // logger.info("Se actualiza el registro general");
            registroGeneralDTO.setId(correccionAportanteDTO.getIdRegistroGeneral());
        }
        // logger.info("correccionAportanteDTO: " + correccionAportanteDTO.getIdRegistroGeneral());
        List<BigDecimal> valorAportes = new ArrayList<>();

        // Consulta el empleador
        AporteManualDTO aporteManualDTO = new AporteManualDTO();
        RadicacionAporteManualDTO radicacionDTO = new RadicacionAporteManualDTO();
        radicacionDTO.setCodigoFinanciero(correccionAportanteDTO.getCodigoFinanciero());
        radicacionDTO.setTipoIdentificacion(correccionAportanteDTO.getTipoIdentificacion());
        radicacionDTO.setNumeroIdentificacion(correccionAportanteDTO.getNumeroIdentificacion());
        radicacionDTO.setRazonSocialAportante(correccionAportanteDTO.getRazonSocial());
        radicacionDTO.setOrigenAporte(correccionAportanteDTO.getOrigenAportante());
        radicacionDTO.setPeriodoPago(correccionAportanteDTO.getPeriodoPago());
        radicacionDTO.setTipo(correccionAportanteDTO.getTipoAportante());
        radicacionDTO.setMontoAporte(correccionAportanteDTO.getValTotalApoObligatorio());
        radicacionDTO.setMoraAporte(correccionAportanteDTO.getValorIntMora());
        InformacionPagoDTO informacionPagoDTO = new InformacionPagoDTO();
    
        // logger.info("informacionPagoDTO " + informacionPagoDTO.toString());

        aporteManualDTO.setIdRegistroGeneral(registroGeneralDTO.getId());
        aporteManualDTO.setRadicacionDTO(radicacionDTO);
        aporteManualDTO.setInformacionPagoDTO(informacionPagoDTO);
        AporteGeneralModeloDTO aporteGeneralDTO = transformarAporteGeneralSinDetalle(aporteManualDTO);

        // logger.info("aporteGeneralDTO " + aporteGeneralDTO.toString());

        registroGeneralDTO.setOutEsEmpleador(Boolean.TRUE);
        registroGeneralDTO.setOutEstadoEmpleador(aporteGeneralDTO.getEstadoAportante());
        registroGeneralDTO.setOutRegistroActual(Boolean.TRUE);
        guardarRegistroGeneral(registroGeneralDTO);
        // logger.info("se guarda registro general");

        Long idAporte = crearActualizarAporteGeneral(aporteGeneralDTO);
        aporteGeneralDTO.setId(idAporte);

        CorreccionModeloDTO correccionModeloDTO = new CorreccionModeloDTO();
        correccionModeloDTO.setIdAporteDetalladoCorregido(idAporteDetalladoAntiguo);
        correccionModeloDTO.setIdAporteGeneralNuevo(idAporte);
        correccionModeloDTO.setIdSolicitudCorreccionAporte(idSolicitudCorreccion);
        crearActualizarCorreccion(correccionModeloDTO);


        /* datos del movimiento nuevo */
        MovimientoAporteModeloDTO movimiento = new MovimientoAporteModeloDTO();
        movimiento.setAporte(aporteGeneralDTO.getValorTotalAporteObligatorio());
        movimiento.setEstado(EstadoAporteEnum.VIGENTE);
        movimiento.setFechaActualizacionEstado(new Date());
        movimiento.setFechaCreacion(new Date());
        movimiento.setInteres(aporteGeneralDTO.getValorInteresesMora());
        movimiento.setTipoAjuste(null);
        movimiento.setTipoMovimiento(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL);
        movimiento.setIdAporteGeneral(aporteGeneralDTO.getId());
        // logger.info("movimiento:  " + movimiento.toString());
        actualizarMovimientoAporte(movimiento);

        /* se crea un nuevo movimiento con los valores en cero para anular */
        MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA,
                TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES, EstadoAporteEnum.VIGENTE, BigDecimal.ZERO,
                BigDecimal.ZERO, new Date(),
                new Date(), idAporteDetalladoAntiguo, idAporteGeneralAntiguo);
        crearActualizarMovimientoAporte(movimientoAporteDTO);

        if (!sinDetalleInicio && idAporteDetalladoAntiguo != null) {
            // logger.info("***___*** se consulta aporte detallado");
            AporteDetalladoModeloDTO aporteDetalladoModeloDTO = consultarAporteDetallado(idAporteDetalladoAntiguo);
            /* se modifican los datos del aporte a cero y se anula */
            aporteDetalladoModeloDTO.setAporteObligatorio(BigDecimal.ZERO);
            aporteDetalladoModeloDTO.setValorMora(BigDecimal.ZERO);
            aporteDetalladoModeloDTO.setEstadoAporteAjuste(EstadoAporteEnum.ANULADO);
            crearActualizarAporteDetallado(aporteDetalladoModeloDTO);
        }

        valorAportes.add(aporteGeneralDTO.getValorTotalAporteObligatorio());
        valorAportes.add(aporteGeneralDTO.getValorInteresesMora());

        for (BigDecimal valor : valorAportes) {
            // logger.info("valorAportes: " + valor);
        }
        return valorAportes;
    }

    /**
     * Método para registar un aporte de una correccion con detalle
     * 
     * @param idSolicitud
     *                               Identificador de la solicitud global
     * @param correccionAportanteDTO
     *                               DTO con la información de la corrección para el
     *                               aporte
     */
    private List<BigDecimal> registrarAporteCorreccionConDetalle(Long idSolicitud, Long idSolicitudCorreccion) {
        CorreccionDTO correccion = consultarCorreccionTemporal(idSolicitud);
        List<CotizanteDTO> cotizantesTemporales = correccion.getLstCotizantes();
        BigDecimal valorAporteCorreccion = BigDecimal.ZERO;
        BigDecimal valorMoraCorreccion = BigDecimal.ZERO;
        Boolean guardarConDetalle = true;
        List<Long> idRegistroDetallado = new ArrayList<>();
        List<Long> idRegistroGeneral = new ArrayList<>();
        List<BigDecimal> valorAportes = new ArrayList<>();
        Long fechaInicialRecaudo = new Date().getTime();
        Integer cuentaBancaria = null;
        if (correccion.getAnalisis().getIdAporte() != null) {
            logger.info("**** idAporte original " + correccion.getAnalisis().getIdAporte());
            AporteGeneralModeloDTO apGeneral = consultarAporteGeneral(correccion.getAnalisis().getIdAporte());
            fechaInicialRecaudo = apGeneral.getFechaRecaudo();
            cuentaBancaria = apGeneral.getCuentaBancariaRecaudo();
        }
        for (CotizanteDTO cotizanteDTO : cotizantesTemporales) {
            logger.info("**** cotizanteDTO " + cotizanteDTO.toString());
            for (CotizanteDTO cotizanteDTONuevo : cotizanteDTO.getCorreccion().getCotizantesNuevos()) {
                    logger.info("**** cotizanteDTONuevo " + cotizanteDTONuevo.toString());
                if (EstadoValidacionRegistroAporteEnum.NO_OK.equals(cotizanteDTONuevo.getEvaluacionSimulacion())
                        || EstadoValidacionRegistroAporteEnum.NO_VALIDADO_BD
                                .equals(cotizanteDTONuevo.getEvaluacionSimulacion())) {
                    logger.info("**** cotizanteDTONuevo.getEvaluacionSimulacion() "
                             + cotizanteDTONuevo.getEvaluacionSimulacion());
                    idRegistroDetallado.add(cotizanteDTONuevo.getIdCotizante());
                }
            }
            idRegistroGeneral.add(cotizanteDTO.getCorreccion().getIdRegistroGeneral());
        }
        for (CotizanteDTO cotizante : correccion.getLstCotizantes()) {
            logger.info("**** cotizante " + cotizante.toString());
            if (cotizante != null &&
                    correccion.getSolicitudCorrecionDTO() != null &&
                    correccion.getSolicitudCorrecionDTO().getNumeroIdentificacion() != null &&
                    correccion.getSolicitudCorrecionDTO().getTipoIdentificacion() != null) {

                procesarAportesDevolucionCorreccion(
                        cotizante,
                        correccion.getSolicitudCorrecionDTO().getNumeroIdentificacion(),
                        correccion.getSolicitudCorrecionDTO().getTipoIdentificacion());
            }
        }
        // radicarNovedadesCorreccion(correccion.getAnalisis().getIdRegistroGeneralNuevo());
        if (!idRegistroDetallado.isEmpty()) {
            actualizarEstadoRegistroDetallado(idRegistroDetallado);
        }
        if (!idRegistroGeneral.isEmpty()) {
            actualizarEstadoRegistroGeneral(idRegistroGeneral);
        }
logger.info("**** correccion.getLstCotizantes().size(): " + correccion.getLstCotizantes().size());
logger.info("**** cotizantesTemporales: " + cotizantesTemporales.size());
        for (CotizanteDTO cotizanteDTO : correccion.getLstCotizantes()) {
            logger.info("**** cotizanteDTO 6645** " + cotizanteDTO.toString());
            BigDecimal valorAporteCorreccionTem = BigDecimal.ZERO;
            BigDecimal valorMoraCorreccionTem = BigDecimal.ZERO;
            if (cotizanteDTO.getCorreccion() != null && cotizanteDTO.getCorreccion().getCotizantesNuevos() != null
                    && !cotizanteDTO.getCorreccion().getCotizantesNuevos().isEmpty()) {
                for (CotizanteDTO cotizanteDTONuevo : cotizanteDTO.getCorreccion().getCotizantesNuevos()) {
                    logger.info("**** cotizanteDTONuevo 6651** " + cotizanteDTONuevo.toString());
                    valorAporteCorreccionTem = valorAporteCorreccionTem.add(
                            cotizanteDTONuevo.getAporteObligatorio() != null ? cotizanteDTONuevo.getAporteObligatorio()
                                    : BigDecimal.ZERO);
                    valorMoraCorreccionTem = valorMoraCorreccionTem
                            .add(cotizanteDTONuevo.getValorMora() != null ? cotizanteDTONuevo.getValorMora()
                                    : BigDecimal.ZERO);
                }
            }
            if (valorAporteCorreccionTem.equals(cotizanteDTO.getAporteObligatorio())
                    && valorMoraCorreccionTem.equals(cotizanteDTO.getValorMora())) {
                logger.info("**** valorAporteCorreccionTem " + valorAporteCorreccionTem.toString());
                logger.info("**** valorMoraCorreccionTem " + valorMoraCorreccionTem.toString());
                valorAporteCorreccion = valorAporteCorreccion.add(valorAporteCorreccionTem);
                valorMoraCorreccion = valorMoraCorreccion.add(valorMoraCorreccionTem);
                
            } else {
                guardarConDetalle = false;
            }

            if (!guardarConDetalle) {
                Boolean sinDetalleInicio = false;
                return registrarAporteCorreccionSinDetalle(idSolicitud, correccion.getCorreccion(),
                        idSolicitudCorreccion, sinDetalleInicio,
                        cotizanteDTO.getIdCotizante(), correccion.getAnalisis().getIdAporte());
            } else {
                /* Es con detalle */
                AporteDetalladoModeloDTO aporteDetalladoModeloDTO = null;
                AporteGeneralModeloDTO aporteGeneralModeloDTO = null;
                if (cotizanteDTO.getIdCotizante() != null) {
                    aporteDetalladoModeloDTO = consultarAporteDetallado(cotizanteDTO.getIdCotizante());
                    logger.info("**** aporteDetalladoModeloDTO 6680** "
                           + aporteDetalladoModeloDTO.toString());
                    aporteGeneralModeloDTO = consultarAporteGeneral(aporteDetalladoModeloDTO.getIdAporteGeneral());
                    logger.info("**** aporteGeneralModeloDTO 6684** "
                             + aporteDetalladoModeloDTO.toString());
                }

                RegistroGeneralModeloDTO registroGeneralDTO = consultarRegistroGeneralId(
                        cotizanteDTO.getCorreccion().getIdRegistroGeneral());
                logger.info("**** registroGeneralDTO 6690** " + registroGeneralDTO.toString());
                if (registroGeneralDTO == null) {
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                } else {
                    registrarRelacionarAportesNovedades(registroGeneralDTO.getTransaccion());
                    /*
                     * si despues de registrarse el estado sigue estando pendiente por reigstro de
                     * aportes se devuevle una marca de
                     * false
                     */
                    if (!EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES
                            .equals(registroGeneralDTO.getOutEstadoArchivo())) {

                        // Boolean pagadorTercero =
                        // ((cotizanteDTO.getCorreccion().get.getRadicacionDTO().getPagadorTercero() !=
                        // null
                        // &&
                        // DecisionSiNoEnum.SI.equals(aporteManualDTO.getRadicacionDTO().getPagadorTercero()))
                        // || aporteManualDTO.getRadicacionDTO().getPagadorTercero() == null) ?
                        // Boolean.TRUE : Boolean.FALSE
                        Long fechaRecaudo = aporteGeneralModeloDTO == null ? fechaInicialRecaudo
                                : aporteGeneralModeloDTO.getFechaRecaudo();
                        registrarAporteConDetalle(cotizanteDTO.getCorreccion().getOrigenAportante(), null, idSolicitud,
                                cotizanteDTO.getCorreccion().getTipoAportante(),
                                cotizanteDTO.getCorreccion().getPagadorPorTerceros(),
                                registroGeneralDTO.getId(), fechaRecaudo, cuentaBancaria);

                        /*
                         * si despues de registrarse el estado sigue estando pendiente por registro de
                         * aportes se devuevle una marca de
                         * false
                         */
                        if (EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES
                                .equals(registroGeneralDTO.getOutEstadoArchivo())) {
                            logger.info("Fin de método registrarAporteCorreccionConDetalle de manera no exitosa");
                            return valorAportes;
                        } else {
                            AporteGeneralModeloDTO aporteGeneral = new AporteGeneralModeloDTO();
                            try {
                                 logger.info("**__**registro general: "+registroGeneralDTO.getId());
                                aporteGeneral = consultarAporteGeneralPorIdRegistro(registroGeneralDTO.getId());
                                //logger.info("aporte general "+aporteGeneralModeloDTO.toString());
                            } catch (Exception e) {
                                throw new ParametroInvalidoExcepcion(
                                        MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                            }
                            // PersonaModeloDTO persona =
                            // consultarDatosPersona(cotizanteDTO.getCorreccion().getTipoIdentificacion(),
                            // cotizanteDTO.getCorreccion().getNumeroIdentificacion());
                            //
                            // if (persona != null) {
                            // persona.setPrimerNombre(cotizanteDTO.getCorreccion().getPrimerNombre());
                            // persona.setSegundoNombre(cotizanteDTO.getCorreccion().getSegundoNombre());
                            // persona.setPrimerApellido(cotizanteDTO.getCorreccion().getPrimerApellido());
                            // persona.setSegundoApellido(cotizanteDTO.getCorreccion().getSegundoApellido());
                            //
                            // actualizarDatosPersona(persona);
                            // }

                            CorreccionModeloDTO correccionModeloDTO = new CorreccionModeloDTO();
                            if (aporteDetalladoModeloDTO != null) {
                                correccionModeloDTO.setIdAporteDetalladoCorregido(aporteDetalladoModeloDTO.getId());
                            }
                            correccionModeloDTO.setIdAporteGeneralNuevo(aporteGeneral.getId());
                            correccionModeloDTO.setIdSolicitudCorreccionAporte(idSolicitudCorreccion);
                            logger.info("**** correccionModeloDTO 6757** "
                                    + correccionModeloDTO.toString());
                            crearActualizarCorreccion(correccionModeloDTO);

                            /* se crea un nuevo movimiento con los valores en cero */
                            if (aporteDetalladoModeloDTO != null) {
                                MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                                        TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA,
                                        TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES, EstadoAporteEnum.VIGENTE,
                                        aporteDetalladoModeloDTO.getAporteObligatorio(),
                                        aporteDetalladoModeloDTO.getValorMora(),
                                        new Date(), new Date(), cotizanteDTO.getIdCotizante(),
                                        correccion.getAnalisis().getIdAporte());
                                logger.info("**** movimientoAporteDTO 6766** "
                                        + movimientoAporteDTO.toString());
                                crearActualizarMovimientoAporte(movimientoAporteDTO);
                                /* se modifican los datos del aporte a cero y se anula */
                                aporteDetalladoModeloDTO.setAporteObligatorio(BigDecimal.ZERO);
                                aporteDetalladoModeloDTO.setValorMora(BigDecimal.ZERO);
                                aporteDetalladoModeloDTO.setEstadoAporteAjuste(EstadoAporteEnum.ANULADO);
                                crearActualizarAporteDetallado(aporteDetalladoModeloDTO);
                            } else {
                                MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                                        TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA,
                                        TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES, EstadoAporteEnum.VIGENTE,
                                        correccion.getAnalisis().getMonto(), correccion.getAnalisis().getInteres(),
                                        new Date(), new Date(),
                                        null, correccion.getAnalisis().getIdAporte());
                                    logger.info("**** movimientoAporteDTO 6780** "
                                        + movimientoAporteDTO.toString());
                                crearActualizarMovimientoAporte(movimientoAporteDTO);
                            }
                            logger.info("Fin de método registrar(Long idSoliciutd) de manera exitosa");
                        }
                    }
                }
            }
        }
        logger.info("**** valorAporteCorreccion " + valorAporteCorreccion.toString());
        logger.info("**** valorMoraCorreccion " + valorMoraCorreccion.toString());
        valorAportes.add(valorAporteCorreccion);
        valorAportes.add(valorMoraCorreccion);
        logger.debug("Fin de método finalizarCorreccion(Long idSolicitud)");
        return valorAportes;
    }

    /**
     * Consulta una salicitud de corrección de aporte por el identificador del
     * aporte general
     * 
     * @param idAporteGeneral
     * @return
     */

    private SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral) {
        logger.info("Inicio de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudCorreccionAporteAporteGeneral consultarSolicitudAporte = new ConsultarSolicitudCorreccionAporteAporteGeneral(
                idAporteGeneral);
        consultarSolicitudAporte.execute();
        SolicitudCorreccionAporteModeloDTO solicitudAporteModeloDTO = consultarSolicitudAporte.getResult();
        logger.info("Fin de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        return solicitudAporteModeloDTO;
    }

    private void procesarAportesDevolucionCorreccion(
            CotizanteDTO cotizante, String numeroIdentificacionAportante,
            TipoIdentificacionEnum tipoIdentificacionAportante) {
        logger.info("Inicio de método procesarAportesDevolucionCorreccion(List<NovedadCotizanteDTO>) ");
        // TODO: se debe validar el procesamiento de la proceso de devolucion en la
        // correccion
        if (cotizante.getNovedades() == null)
            return;
        try {

            NovedadAportesDTO novedadAportes = new NovedadAportesDTO();

            for (NovedadCotizanteDTO novedad : cotizante.getNovedades()) {
                if (novedad.getTipoNovedad().equals(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO)) {
                    continue;
                }
                if (novedad.getAplicar() == null || novedad.getAplicar() == false) {
                    continue;
                }
                MarcaAccionNovedadEnum accionNovedad = MarcaAccionNovedadEnum.APLICAR_NOVEDAD;
                novedadAportes.setCanalRecepcion(CanalRecepcionEnum.APORTE_MANUAL);

                novedadAportes.setAplicar(accionNovedad.getMarca());
                novedadAportes.setTipoNovedad(novedad.getTipoNovedad());

                // se consultan las clasificaciones del afiliado (si lo está)
                List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(
                        cotizante.getTipoIdentificacion(),
                        cotizante.getNumeroIdentificacion());
                for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
                    if (clasificacion != null) {
                        // &&
                        // clasificacion.getSujetoTramite().equals(TipoAfiliadoEnum.valueOf(temNovedad.getTipoCotizante())))
                        // {
                        novedadAportes.setClasificacionAfiliado(clasificacion);
                        break;
                    }
                }
                // Ajuste correccion
                novedadAportes.setFechaFin(novedad.getFechaFin());
                novedadAportes.setFechaInicio(
                        novedad.getFechaInicio() == null ? novedad.getFechaInicioNueva() : novedad.getFechaInicio());
                novedadAportes.setIdRegistroDetallado(cotizante.getIdRegistroDetalladoNuevo());
                novedadAportes.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
                novedadAportes.setNumeroIdentificacionAportante(numeroIdentificacionAportante);
                novedadAportes.setTipoIdentificacion(cotizante.getTipoIdentificacion());
                novedadAportes.setTipoIdentificacionAportante(tipoIdentificacionAportante);
                radicarSolicitudNovedadAportes(novedadAportes);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando las novedades de devolucion", e);
        }

        logger.info("Fin de método radicarNovedades(List<NovedadCotizanteDTO>)");
    }

    /**
     * Método privado que actualiza datos propios de la persona
     * 
     * @param personaDTO
     */
    private void actualizarDatosPersona(PersonaModeloDTO personaDTO) {
        logger.info("Inicio de método actualizarDatosPersona(PersonaModeloDTO personaDTO)");
        ActualizarDatosPersona personaService = new ActualizarDatosPersona(personaDTO);
        personaService.execute();
        logger.info("Finaliza de método actualizarDatosPersona(PersonaModeloDTO personaDTO)");
    }

    /**
     * Método privado que consulta los datos propios de la persona
     * 
     * @param personaDTO
     */
    private PersonaModeloDTO consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.info(
                "Inicio de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarDatosPersona personaService = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        personaService.execute();
        logger.info(
                "Finaliza de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return personaService.getResult();
    }

    @Override
    public List<DatosCotizanteDTO> consultarDatosCotizanteCargue(List<DatosCotizanteDTO> datosCotizante,
            TipoSolicitanteMovimientoAporteEnum tipoSoliciante) {
        logger.info("Inicio de método consultarDatosCotizanteCargue(List<DatosCotizanteDTO> datosCotizante)");
        List<DatosCotizanteDTO> datos = new ArrayList<>();
        for (DatosCotizanteDTO datosCotizanteDTO : datosCotizante) {
            datos.add(consultarDatosCotizante(datosCotizanteDTO, tipoSoliciante));
        }
        logger.info("Inicio de método consultarDatosCotizanteCargue(List<DatosCotizanteDTO> datosCotizante)");
        return datos;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#verificarCumplimientoSucursal(java.lang.Long)
     */
    @Override
    public Boolean verificarCumplimientoSucursal(Long idRegistroGeneral) {
        try {
            logger.info("Inicia el método verificarCumplimientoSucursal");
            RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId(idRegistroGeneral);
            Boolean cumpleSucursal = false;
            if (registroGeneral != null) {
                if (registroGeneral.getOutMarcaSucursalPILA() != null && registroGeneral.getOutMarcaSucursalPILA()) {
                    if ((registroGeneral.getCodSucursal() == null && registroGeneral.getNomSucursal() == null)
                            && (registroGeneral.getOutCodSucursalPrincipal() != null
                                    && registroGeneral.getOutNomSucursalPrincipal() != null)) {
                        cumpleSucursal = true;
                    } else if (registroGeneral.getCodSucursal() != null && registroGeneral.getNomSucursal() != null) {
                        ValidarCodigoNombreSucursal validarSucursal = new ValidarCodigoNombreSucursal(
                                registroGeneral.getCodSucursal(),
                                idRegistroGeneral, registroGeneral.getOutCodSucursalPrincipal());
                        validarSucursal.execute();
                        cumpleSucursal = validarSucursal.getResult();
                    } else {
                        cumpleSucursal = false;
                    }
                } else {
                    cumpleSucursal = true;
                }
            }
            logger.info("Finaliza el método verificarCumplimientoSucursal");
            return cumpleSucursal;
        } catch (Exception e) {
            logger.error("Error en el método verificarCumplimientoSucursal", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarCuentaAporteVista(java.lang.Long,
     *      java.util.List,
     *      com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum)
     */
    @Override
    public List<CuentaAporteDTO> consultarCuentaAporteVista(Long idPersonaCotizante,
            List<AnalisisDevolucionDTO> analisisDevolucionDTO,
            TipoMovimientoRecaudoAporteEnum tipo, Long idSolicitudDevolucion) {
        String firmaServicio = "AportesManualesCompositeBusiness.consultarCuentaAporteVista(Long, List<AnalisisDevolucionDTO>, "
                +
                "TipoMovimientoRecaudoAporteEnum, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAporteDTO> cuentaRecaudo = consultarCuentaAporteConTipoRecaudo(idPersonaCotizante, analisisDevolucionDTO,tipo);

        // se toman los IDs de aporte general para la consulta de solicitudes de aporte
        List<Long> idsAportes = new ArrayList<>();
        for (CuentaAporteDTO cuentaAporteDTO : cuentaRecaudo) {
            if (!idsAportes.contains(cuentaAporteDTO.getIdAporteGeneral())) {
                idsAportes.add(cuentaAporteDTO.getIdAporteGeneral());
            }
        }

        DatosConsultaSolicitudesAporDevCorDTO solicitudes = consultarSolicitudApoDevCor(idsAportes);

        List<CuentaAporteDTO> cuentaAporteGeneralDTOs = new ArrayList<>();
  
        if (tipo != null) {
            if (cuentaRecaudo != null && !cuentaRecaudo.isEmpty()) {
                if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)
                        || TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(tipo)
                        || TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.equals(tipo)) {
                    for (CuentaAporteDTO cuentaAporteDTO : cuentaRecaudo) {
                        if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL
                                .equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
                                || TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES
                                        .equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
                                || TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO
                                        .equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {

                            /*
                             * Se arma la lista de cuenta de aportes para solo recaudos
                             * Se consulta la solicitud de aporte
                             */
                            SolicitudAporteModeloDTO solicitudAporteModeloDTO = solicitudes.getSolicitudesAporte()
                                    .get(cuentaAporteDTO.getIdAporteGeneral());

                            if (solicitudAporteModeloDTO != null
                                    && EstadoSolicitudAporteEnum.CERRADA
                                            .equals(solicitudAporteModeloDTO.getEstadoSolicitud())) {
                                cuentaAporteDTO.setSolicitudAporteModeloDTO(solicitudAporteModeloDTO);
                            }
                         
                                cuentaAporteGeneralDTOs.add(cuentaAporteDTO);
                            
                    
                        }
                    }
                } else if (TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo)) {
                    for (CuentaAporteDTO cuentaAporteDTO : cuentaRecaudo) {
                        if (TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES
                                .equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
                            /* Se consulta la solicitud de devolucion */
                            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = null;

                            Map<Long, SolicitudDevolucionAporteModeloDTO> devAporte = solicitudes
                                    .getSolicitudesDevolucion()
                                    .get(cuentaAporteDTO.getIdAporteGeneral());
                            if (devAporte != null) {
                                solicitudDevolucionAporteModeloDTO = devAporte
                                        .get(cuentaAporteDTO.getIdMovimientoAporte());
                            }
                            if (solicitudDevolucionAporteModeloDTO != null
                                    && solicitudDevolucionAporteModeloDTO.getIdSolicitudDevolucionAporte() != null
                                    && solicitudDevolucionAporteModeloDTO.getIdSolicitudDevolucionAporte()
                                            .equals(idSolicitudDevolucion)) {
                                cuentaAporteDTO
                                        .setSolicitudDevolucionAporteModeloDTO(solicitudDevolucionAporteModeloDTO);
                                cuentaAporteGeneralDTOs.add(cuentaAporteDTO);
                            }
                        }
                    }
                } else if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)) {
                    for (CuentaAporteDTO cuentaAporteDTO : cuentaRecaudo) {
                        if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES
                                .equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
                            /* Se consulta la solicitud de correcion */
                            cuentaAporteDTO.setSolicitudCorreccionAporteModeloDTO(
                                    solicitudes.getSolicitudesCorreccion().get(cuentaAporteDTO.getIdAporteGeneral()));
                            cuentaAporteGeneralDTOs.add(cuentaAporteDTO);
                        }
                    }
                }
            }
        } else {
            cuentaAporteGeneralDTOs.addAll(cuentaRecaudo);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAporteGeneralDTOs;
    }

    /**
     * Método privado que actualiza datos propios de la persona
     * 
     * @param personaDTO
     */
    private List<CuentaAporteDTO> consultarCuentaAporte(Long idPersonaCotizante,
            List<AnalisisDevolucionDTO> analisisDevolucionDTO) {
        logger.info(
                "Inicio de método consultarCuentaAporte(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO) ");
        try {
            ConsultarCuentaAporte cuentaAporteService = new ConsultarCuentaAporte(idPersonaCotizante,
                    analisisDevolucionDTO);
            cuentaAporteService.execute();
            logger.info(
                    "Finaliza de método consultarCuentaAporte(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO) ");
            return cuentaAporteService.getResult();
        } catch (Exception e) {
            logger.error("**_Error en el método consultarCuentaAporte", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    /**
     * Método privado que actualiza datos propios de la persona
     * 
     * @param personaDTO
     */
    private List<CuentaAporteDTO> consultarCuentaAporteConTipoRecaudo(Long idPersonaCotizante,
            List<AnalisisDevolucionDTO> analisisDevolucionDTO,TipoMovimientoRecaudoAporteEnum tipoRecaudo) {
        logger.info(
                "Inicio de método consultarCuentaAporteConTipoRecaudo(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO) ");
        try {
            ConsultarCuentaAporteConTipoRecaudo cuentaAporteService = new ConsultarCuentaAporteConTipoRecaudo(idPersonaCotizante,
                    analisisDevolucionDTO,tipoRecaudo);
            cuentaAporteService.execute();
            logger.info(
                    "Finaliza de método consultarCuentaAporteConTipoRecaudo(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO) ");
            return cuentaAporteService.getResult();
        } catch (Exception e) {
            logger.error("**_Error en el método consultarCuentaAporteConTipoRecaudo", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    /**
     * Método para la consulta de solicitudes de corrección de aporte
     * 
     * @param personaDTO
     */
    private DatosConsultaSolicitudesAporDevCorDTO consultarSolicitudApoDevCor(List<Long> idsAporteGeneral) {
        String firmaMetodo = "AportesManualesCompositeBusiness.consultarSolicitudCorreccionAporteGeneral(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarSolicitudesCorreccion correciones = new ConsultarSolicitudesCorreccion(idsAporteGeneral);
        correciones.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return correciones.getResult();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarTrazabilidadAportes(java.lang.Long)
     */
    @Override
    public List<RegistroEstadoAporteModeloDTO> consultarTrazabilidadAportes(Long idSolicitud) {
        try {
            logger.info("Inicio de método consultarTrazabilidadAportes(Long idSolicitud) ");
            List<RegistroEstadoAporteModeloDTO> registros = consultarTrazabilidad(idSolicitud);
            if (!registros.isEmpty()) {
                for (RegistroEstadoAporteModeloDTO registro : registros) {
                    if (registro.getIdComunicado() != null) {
                        ComunicadoModeloDTO comunicado = consultarComunicados(registro.getIdComunicado());
                        registro.setDestinatario(comunicado.getDestinatario());
                        registro.setIdEcm(comunicado.getIdentificadorArchivoComunicado());
                        registro.setMedioComunicado(comunicado.getMedioComunicado());
                    }
                }
            }
            logger.info("Finaliza de método consultarTrazabilidadAportes(Long idSolicitud) ");
            return registros;
        } catch (Exception e) {
            logger.error("Ocurrio un error validando la existencia de la solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de consultar la trazabilidad de la solicitud por id
     * 
     * @param idSolicitud,
     *                     id la trazabilidad a consultar
     * @return retorna la trazabilidad encontrada
     */
    private List<RegistroEstadoAporteModeloDTO> consultarTrazabilidad(Long idSolicitud) {
        try {
            ConsultarTrazabilidad trazabilidad = new ConsultarTrazabilidad(idSolicitud);
            trazabilidad.execute();
            return trazabilidad.getResult();
        } catch (Exception e) {
            logger.error("Ocurrio un error validando la existencia de la solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado llamar el cliente del servicio que consulta los
     * comunicados por solicitud
     * 
     * @param idSolicitud,
     *                     id de la solicitud a consultar
     * @return retorna la lista de comunicados pertenecientes a la solicitud
     */
    private ComunicadoModeloDTO consultarComunicados(Long idComunicado) {
        try {
            ConsultarComunicado comunicado = new ConsultarComunicado(idComunicado);
            comunicado.execute();
            return comunicado.getResult();
        } catch (Exception e) {
            logger.error("Ocurrio un error validando la existencia de la solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#consultarRecaudoCorreccion(com.asopagos.aportes.dto.ConsultarRecaudoDTO,
     *      com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum,
     *      java.lang.Boolean, java.lang.Boolean)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AnalisisDevolucionDTO> consultarRecaudoCorreccion(ConsultarRecaudoDTO consultaRecaudo,
            TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte, Boolean hayParametros, Boolean vista360) {
        String firmaServicio = "AportesManualesCompositeBusiness.consultarRecaudoCorreccion(ConsultarRecaudoDTO, "
                + "TipoMovimientoRecaudoAporteEnum, Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Long tiempoInicial = Calendar.getInstance().getTimeInMillis();

        List<AnalisisDevolucionDTO> analisis = consultarRecaudo(consultaRecaudo, tipoMovimientoRecaudoAporte,
                hayParametros, vista360);
        Long tiempoConsultarRecaudo = Calendar.getInstance().getTimeInMillis();
        logger.info("tiempoConsultarRecaudo"+ (tiempoConsultarRecaudo - tiempoInicial));
        analisis.removeIf(n -> n.getEstadoAporte().equals(EstadoAporteEnum.CORREGIDO));
        Long tiempoRemove = Calendar.getInstance().getTimeInMillis();
        logger.info("tiempoConsultarRecaudo"+ (tiempoConsultarRecaudo - tiempoInicial));
        List<CorreccionVistasDTO> correcciones = new ArrayList<>();
        List<AnalisisDevolucionDTO> analisisConSolicitud = new ArrayList<>();

        if (!analisis.isEmpty()) {
            List<Long> idsAportes = new ArrayList<>();
            for (AnalisisDevolucionDTO analisisDevolucionDTO : analisis) {
                idsAportes.add(analisisDevolucionDTO.getIdAporte());
            }
            correcciones = consultarDatosSolicitudCorreccion(idsAportes);
        }
        Long tiempoConsultarDatosSolicitudCorreccion = Calendar.getInstance().getTimeInMillis();
        logger.info("tiempoConsultarDatosSolicitudCorreccion"+ (tiempoConsultarDatosSolicitudCorreccion - tiempoInicial));

        if (!correcciones.isEmpty()) {
            for (CorreccionVistasDTO correccion : correcciones) {
                logger.info("Correccion "+ correccion.getTotalCorreccion());
                for (AnalisisDevolucionDTO analisisDevolucionDTO : analisis) {
                    logger.info("analisisDevolucionDTO "+ analisisDevolucionDTO.toString());
                    if (analisisDevolucionDTO.getIdAporte().equals(correccion.getIdAporteGeneral())) {
                        AnalisisDevolucionDTO nuevoAnalisis = construirAnalisisDevolucion(analisisDevolucionDTO);
                        nuevoAnalisis.setIdSolicitudCorreccion(correccion.getIdSolicitudCorreccionAporte());
                        nuevoAnalisis.setIdSolicitudGlobal(correccion.getIdSolicitud());
                        nuevoAnalisis.setMontoCorreccion(correccion.getMontoCorreccion());
                        nuevoAnalisis.setInteresesCorreccion(correccion.getInteresesCorreccion());
                        nuevoAnalisis.setTotalCorreccion(correccion.getTotalCorreccion());
                        analisisConSolicitud.add(nuevoAnalisis);
                    }
                }
            }
        }
        Long tiempoFor = Calendar.getInstance().getTimeInMillis();
        logger.info("tiempoFor"+ (tiempoFor - tiempoInicial));
        if (analisisConSolicitud.isEmpty()) {
            analisisConSolicitud = analisis;
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return analisisConSolicitud;
    }

    /**
     * @param analisisDevolucionDTO
     * @return
     */
    private AnalisisDevolucionDTO construirAnalisisDevolucion(AnalisisDevolucionDTO analisisDevolucionDTO) {
        try {
            AnalisisDevolucionDTO analisis = new AnalisisDevolucionDTO();
            analisis.setIdAporte(analisisDevolucionDTO.getIdAporte());
            analisis.setNumOperacion(analisisDevolucionDTO.getNumOperacion());
            analisis.setFecha(analisisDevolucionDTO.getFecha());
            analisis.setFechaPago(analisisDevolucionDTO.getFechaPago());
            analisis.setMetodo(analisisDevolucionDTO.getMetodo());
            analisis.setConDetalle(analisisDevolucionDTO.getConDetalle());
            analisis.setNumPlanilla(analisisDevolucionDTO.getNumPlanilla());
            analisis.setEstadoArchivo(analisisDevolucionDTO.getEstadoArchivo());
            analisis.setTipoArchivo(analisisDevolucionDTO.getTipoArchivo());
            analisis.setTipoPlanilla(analisisDevolucionDTO.getTipoPlanilla());
            analisis.setPeriodo(analisisDevolucionDTO.getPeriodo());
            analisis.setMonto(analisisDevolucionDTO.getMonto());
            analisis.setInteres(analisisDevolucionDTO.getInteres());
            analisis.setIdEcmArchivo(analisisDevolucionDTO.getIdEcmArchivo());
            analisis.setGestionado(analisisDevolucionDTO.getGestionado());
            analisis.setResultado(analisisDevolucionDTO.getResultado());
            analisis.setTieneModificaciones(analisisDevolucionDTO.getTieneModificaciones());
            analisis.setCotizanteDTO(analisisDevolucionDTO.getCotizanteDTO());
            analisis.setComentariosResultado(analisisDevolucionDTO.getComentariosResultado());
            analisis.setHistorico(analisisDevolucionDTO.getHistorico());
            analisis.setTotal(analisisDevolucionDTO.getTotal());
            analisis.setIdRegistroGeneral(analisisDevolucionDTO.getIdRegistroGeneral());
            analisis.setIdRegistroGeneralNuevo(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
            analisis.setIdPersona(analisisDevolucionDTO.getIdPersona());
            analisis.setIdPersonaCotizante(analisisDevolucionDTO.getIdPersonaCotizante());
            analisis.setIdEmpresa(analisisDevolucionDTO.getIdEmpresa());
            analisis.setMontoRegistro(analisisDevolucionDTO.getMontoRegistro());
            analisis.setInteresRegistro(analisisDevolucionDTO.getInteresRegistro());
            analisis.setEstadoAporte(analisisDevolucionDTO.getEstadoAporte());
            analisis.setTipoSolicitante(analisisDevolucionDTO.getTipoSolicitante());
            analisis.setTipoIdentificacion(analisisDevolucionDTO.getTipoIdentificacion());
            analisis.setNumeroIdentificacion(analisisDevolucionDTO.getNumeroIdentificacion());
            analisis.setPagadorPorTerceros(analisisDevolucionDTO.getPagadorPorTerceros());
            analisis.setNombreCompleto(analisisDevolucionDTO.getNombreCompleto());
            analisis.setCodigoEntidadFinanciera(analisisDevolucionDTO.getCodigoEntidadFinanciera());
            analisis.setListIdAporteDetallados(analisisDevolucionDTO.getListIdAporteDetallados());
            return analisis;
        } catch (Exception e) {
            logger.error("Ocurrio un error validando la existencia de la solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado llamar el cliente del servicio que consulta los
     * comunicados por solicitud
     * 
     * @param idSolicitud,
     *                     id de la solicitud a consultar
     * @return retorna la lista de comunicados pertenecientes a la solicitud
     */
    private List<AnalisisDevolucionDTO> consultarRecaudo(ConsultarRecaudoDTO consultaRecaudo,
            TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte, Boolean hayParametros, Boolean vista360) {
        String firmaMetodo = "AportesManualesCompositeBusiness.consultarRecaudo(ConsultarRecaudoDTO, TipoMovimientoRecaudoAporteEnum, "
                + "Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarRecaudo recaudos = new ConsultarRecaudo(vista360, tipoMovimientoRecaudoAporte, hayParametros,
                consultaRecaudo);
        recaudos.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return recaudos.getResult();
    }

    /**
     * Método encargado llamar el cliente del servicio que consulta los
     * comunicados por solicitud
     * 
     * @param idSolicitud,
     *                     id de la solicitud a consultar
     * @return retorna la lista de comunicados pertenecientes a la solicitud
     */
    private List<CorreccionVistasDTO> consultarDatosSolicitudCorreccion(List<Long> idsAportes) {
        try {
            ConsultarDatosSolicitudCorreccion datosCorrecciones = new ConsultarDatosSolicitudCorreccion(idsAportes);
            datosCorrecciones.execute();
            return datosCorrecciones.getResult();
        } catch (Exception e) {
            logger.error("Ocurrio un error validando la existencia de la solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#actualizarComunicadoTrazabilidadAporte(java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.ActividadEnum, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarComunicadoTrazabilidadAporte(Long idSolicitud, ActividadEnum actividad, Long idComunicado) {
        String firmaMetodo = ".AportesManualesCompositeService#actualizarComunicadoTrazabilidadAporte(Long, ActividadEnum, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        RegistroEstadoAporteModeloDTO trazabilidad = consultarTrazabilidadPorActividad(idSolicitud, actividad);
        if (trazabilidad != null) {
            trazabilidad.setIdComunicado(idComunicado);
            actualizarTrazabilidad(trazabilidad);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarCotizantes(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, java.lang.Long)
     */
    @Override
    public Long consultarNumeroCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            Long periodoAporte) {
        logger.info("Inicio de consultarCotizantes");
        List<Long> idRoles = new ArrayList<>();

        EmpleadorModeloDTO empleador = consultarEmpleadorTipoNumero(tipoIdentificacion, numeroIdentificacion);
        if (empleador != null) {
            idRoles = obtenerTrabajadoresActivosPeriodo(periodoAporte, empleador.getIdEmpleador());
        }
        logger.info("Fin de consultarCotizantes");
        return idRoles.size() == 0 ? 0L : idRoles.size() + 1;
    }

     @Override
    public Map<String, Object> validarRegistrarAporteCorrecion(Long idSolicitud, Long idTarea, Long instaciaProceso,
            UserDTO userDTO) {
        logger.info("Inicio de validarRegistrarAporte(Long,Long,Long,UserDTO) " + idSolicitud);
        Boolean registroExitoso = false;
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            ObtenerTareaActivaInstancia tareaActivaSrv = new ObtenerTareaActivaInstancia(instaciaProceso);
            tareaActivaSrv.execute();

            TareaDTO tarea = tareaActivaSrv.getResult();

            if (tarea != null && idTarea.equals(tarea.getId()) && EstadoTareaEnum.RESERVADA.equals(tarea.getEstado())) {
                // Se suspende la tarea a fin de evitar que sea retomada desde la bandela
                // mientras se pprocesa
                suspenderTarea(idTarea);

                // Se consulta el tipo de transacción asociado a la solicitud
                ConsultarSolicitudGlobal solicitudSrv = new ConsultarSolicitudGlobal(idSolicitud);
                solicitudSrv.execute();

                SolicitudModeloDTO solicitud = solicitudSrv.getResult();

                switch (solicitud.getTipoTransaccion()) {
                    case APORTES_MANUALES:
                        registroExitoso = registrarAporte(idSolicitud, idTarea, userDTO);
                        break;
                    default:
                        break;
                }
            } else
                respuesta.put("mensaje", "La solicitud ya se encuentra en proceso");
        } catch (Exception e) {
            logger.info("exception: " + idSolicitud);
            this.retomarTarea(idTarea, null);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        respuesta.put("resultado", registroExitoso);
        logger.info("Fin de validarRegistrarAporte(Long,Long,Long,UserDTO) " + idSolicitud);
        return respuesta;
    }

    /**
     * Retoma una tarea en BPM previamente suspendida
     * 
     * @param idTarea
     */
    private void retomarTarea(Long idTarea, String token) {
        logger.info("Inicio de retomarTarea(Long)");
        if (tareaSuspendida) {
            RetomarTarea retomarTarea = new RetomarTarea(idTarea, new HashMap<>());
            if (token != null) {
                retomarTarea.setToken(token);
            }
            retomarTarea.execute();
            tareaSuspendida = Boolean.FALSE;
        }
        logger.info("Fin de retomarTarea(Long)");
    }

    /**
     * Asigna el estado SUSPENDED a una tarea en BPM
     * 
     * @param idTarea
     */
    private void suspenderTarea(Long idTarea) {
        logger.info("Inicio de suspenderTarea(Long)");
        SuspenderTarea suspenderTarea = new SuspenderTarea(idTarea, new HashMap<String, Object>());
        suspenderTarea.execute();
        tareaSuspendida = Boolean.TRUE;
        logger.info("Fin de suspenderTarea(Long)");
    }
}
