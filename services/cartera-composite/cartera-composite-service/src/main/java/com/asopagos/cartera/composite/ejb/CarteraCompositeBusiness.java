package com.asopagos.cartera.composite.ejb;

import co.com.heinsohn.lion.common.util.CalendarUtil;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.*;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.aportes.composite.clients.RegistrarNovedadesCartera;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.clients.*;
import com.asopagos.cartera.composite.clients.AsignarUsuarioCiclo;
import com.asopagos.cartera.composite.clients.RetomarAccionesCobro;
import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.cartera.composite.constants.NamedQueriesConstants;
import com.asopagos.cartera.composite.dto.ResultadoGestionPreventivaDTO;
import com.asopagos.cartera.composite.service.CarteraAsincronoCompositeService;
import com.asopagos.cartera.composite.service.CarteraCompositeService;
import com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobroFactory;
import com.asopagos.cartera.dto.*;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.*;
import com.asopagos.dto.cartera.*;
import com.asopagos.dto.modelo.*;
import com.asopagos.empleadores.clients.*;
import com.asopagos.empresas.clients.ConsultarSucursalesEmpresa;
import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.*;
import com.asopagos.enumeraciones.cartera.*;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.*;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.fovis.clients.ConsultarSolicitudPorRadicado;
import com.asopagos.historicos.clients.BuscarDireccion;
import com.asopagos.historicos.clients.BuscarDireccionPorTipoUbicacion;
import com.asopagos.historicos.clients.BuscarHistorialUbicacionRolContactoEmpledor;
import com.asopagos.historicos.clients.ObtenerEstadoTrabajador;
import com.asopagos.listas.clients.ConsultarListasValores;
import com.asopagos.novedades.clients.EjecutarDesafiliacionTrabajadoresEmpledorMasivo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicadoAsincrono;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.*;
import com.asopagos.pila.clients.CalcularFechaVencimiento;
import com.asopagos.processschedule.clients.CancelarProgramacionPorProceso;
import com.asopagos.processschedule.clients.RegistrarActualizarProgramacionAutomatico;
import com.asopagos.reportes.clients.ConsultarEstadoAfiliadoFecha;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.tareashumanas.clients.*;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.concurrent.CompletableFuture;


import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la cartera de la caja de compensacion<b>
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@SuppressWarnings("unchecked")
@Stateless
public class CarteraCompositeBusiness implements CarteraCompositeService {

    /**
     * Constante con el nombre del parametro del bpm comunicadoEnviado
     */
    private static final String COMUNICADO_ENVIADO = "comunicadoEnviado";

    /**
     * Inyección del entity manager.
     */
    @PersistenceContext(unitName = "carteracomposite_PU")
    private EntityManager entityManager;
    /**
     * Constante con la llave del parametro idTarea.
     */
    private static final String ID_TAREA = "idTarea";
    /**
     * Constante con el valor SEGUNDO, para el segundo intento de notificación.
     */
    private static final String SEGUNDO = "SEGUNDO";
    /**
     * Constante con el valor PRIMERO, para el primer intento de notificación.
     */
    private static final String PRIMERO = "PRIMERO";
    /**
     * Constante para enviar parametro de intento.
     */
    private static final String INTENTO = "INTENTO";
    /**
     * Constante con el espacio.
     */
    private static final String ESPACIO = " ";
    /**
     * Estado sin información
     */
    private static final String SIN_INFORMACION = "SIN_INFORMACION";
    /**
     * Perfil back de actualización personas.
     */
    private static final String BAC_ACT_PER = "BacActPer";
    /**
     * Perfil back de actualización empleador.
     */
    private static final String BAC_ACT_EMP = "BacActEmp";
    /**
     * Perfil Back de gestión preventiva de mora.
     */
    private static final String BAC_GES_PRE_MOR = "BacGesPreMor";
    /**
     * Constante con el nonmbre del parametro del bpm backActualizacion
     */
    private static final String BACK_ACTUALIZACION = "backActualizacion";
    /**
     * Constante con el dato del usuario analista de cartera aportes
     */
    private static final String ANA_CAR_APO = "AnaCarApo";
    /**
     * Constante con el nombre del parametro del bpm analistaCartera.
     */
    private static final String ANALISTA_CARTERA = "analistaCartera";
    /**
     * Constante con el nombre del parametro del bpm numeroRadicado.
     */
    private static final String NUMERO_RADICADO = "numeroRadicado";
    /**
     * Constante con el nombre del parametro del bpm idSolicitud.
     */
    private static final String ID_SOLICITUD = "idSolicitud";
    /**
     * Constante con el nombre del parametro del bpm contactoEfectivo
     */
    private static final String CONTACTO_EFECTIVO = "contactoEfectivo";
    /**
     * Constante con el nombre del parámetro periodoAEvaluar
     */
    private static final String PERIODO_EVALUAR = "periodoAEvaluar";
    /**
     * Constante con el nombre del parámetro fecha vecimiento
     */
    private static final String FECHA_VENCIMIENTO = "fechaVencimiento";
    /**
     * Constante con el nombre del parametro del BPM "actualizacionEfectiva"
     */
    private static final String ACTUALIZACION_EFECTIVA = "actualizacionEfectiva";
    /**
     * Perfil back de actualización empleador.
     */
    private static final String SUP_CAR = "coordinador";
    /**
     * SimpleDateFormat para un periodo de pago.
     */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    /**
     * Set que contiene los usuarios asigandos para la HU 160 usuarios asignados
     * para acciones
     * preventivas
     */
    private Set<String> usuariosAsignadosPreventivaAgrupada = new HashSet<String>();
    /**
     * Tipo de autenticación (de acuerdo al header de la petición)(Controla la
     * cantidad de caracteres previos al token)
     */
    private static final String TIPO_AUTORIZACION = "Bearer ";

    private ArrayList<SimulacionDTO> simulacionesManual;

    /**
     * Instancia del Excecutor Manager
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/cartera")
    private ManagedExecutorService mes;

    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(CarteraCompositeBusiness.class);

    @Inject
    private CarteraAsincronoCompositeService carteraAsincronoCompositeService;

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * registrarGestionPreventiva(com.asopagos.cartera.composite.dto.
     * ResultadoGestionPreventivaDTO)
     */
    @Override
    public void registrarGestionPreventiva(ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO,
                                           UserDTO userDTO) {
        logger.debug("Inicia registrarGestionPreventiva(ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO)");
        try {
            SolicitudPreventivaModeloDTO solicitudPreventivaDTO = consultarSolicitudPreventiva(
                resultadoGestionPreventivaDTO.getNumeroRadicacion());
            if (solicitudPreventivaDTO != null) {
                // Se realiza la gestión preventiva por casos
                registrarGestionPorCasos(resultadoGestionPreventivaDTO,
                    resultadoGestionPreventivaDTO.getNumeroRadicacion(), solicitudPreventivaDTO, userDTO);
            }
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en registrarGestionPreventiva: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(
            "Finaliza registrarGestionPreventiva(ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#actualizarGestionPreventiva(java.lang.String,
     * java.lang.Long,
     * java.lang.Boolean)
     */
    @Override
    public void actualizarGestionPreventiva(String numeroRadicacion, Long idTarea, Boolean actualizacionEfectiva,
                                            UserDTO userDTO) {
        logger.debug("Inicio de método actualizarGestionPreventiva(String, Long, Boolean)");
        logger.info("CarteraCompositeBusiness.actualizarGestionPreventiva->" + numeroRadicacion + ":" + idTarea + ":"
            + actualizacionEfectiva + ":" + userDTO);
        try {
            /* mantis 237068 */
            boolean aprobacionEnvioComunicadoEmpleador = false;
            boolean aprobacionEnvioComunicadoPersona = false;
            List<RolAfiliadoEmpleadorDTO> rolAfiliadoEmpleadorDTOs = new ArrayList<>();
            /* se consulta y guarda la solicitud preventiva. */
            SolicitudPreventivaModeloDTO solicitudPreventivaModeloDTO = consultarSolicitudPreventiva(numeroRadicacion);
            /* mantis 237068 */
            PersonaModeloDTO personaModeloDTO = consultarPersona(solicitudPreventivaModeloDTO.getIdPersona());
            solicitudPreventivaModeloDTO.setActualizacionEfectiva(actualizacionEfectiva);
            actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaModeloDTO);

            // Si es automatico validar la fecha limite de pago
            if (Boolean.TRUE.equals(actualizacionEfectiva)) {
                if (TipoGestionCarteraEnum.AUTOMATICA.equals(solicitudPreventivaModeloDTO.getTipoGestionCartera())) {
                    NotificacionParametrizadaDTO notificacion = construirNotificacionParametrizada(
                        solicitudPreventivaModeloDTO, SEGUNDO, idTarea);
                    /* mantis 237068 */
                    EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(
                        personaModeloDTO.getTipoIdentificacion() != null ? personaModeloDTO.getTipoIdentificacion()
                            : null,
                        personaModeloDTO.getNumeroIdentificacion() != null
                            ? personaModeloDTO.getNumeroIdentificacion()
                            : null);
                    if (empleadorModeloDTO != null) {
                        /* Se setea el Id del empleador para referencia del comunicado */
                        notificacion.setIdEmpleador(
                            empleadorModeloDTO.getIdEmpleador() != null ? empleadorModeloDTO.getIdEmpleador()
                                : null);
                        aprobacionEnvioComunicadoEmpleador = validarPeriodoAnalizadoRecaudado(
                            empleadorModeloDTO.getDiaHabilVencimientoAporte());
                    }
                    if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                        .equals(solicitudPreventivaModeloDTO.getTipoSolicitante())
                        || TipoSolicitanteMovimientoAporteEnum.PENSIONADO
                        .equals(solicitudPreventivaModeloDTO.getTipoSolicitante())) {
                        PersonaModeloDTO persona = consultarPersona(solicitudPreventivaModeloDTO.getIdPersona());
                        List<String> correos = new ArrayList<>();
                        correos.add(persona.getUbicacionModeloDTO().getEmail());
                        notificacion.setDestinatarioTO(correos);
                        notificacion.setReplantearDestinatarioTO(true);

                        /* mantis 237068 */
                        /* Se consultan los roles */
                        rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(
                            personaModeloDTO.getTipoIdentificacion() != null
                                ? personaModeloDTO.getTipoIdentificacion()
                                : null,
                            personaModeloDTO.getNumeroIdentificacion() != null
                                ? personaModeloDTO.getNumeroIdentificacion()
                                : null,
                            TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                                .equals(solicitudPreventivaModeloDTO.getTipoSolicitante())
                                ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
                                : TipoAfiliadoEnum.PENSIONADO);

                        /* Se valida que hay datos */
                        if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                            /* Se consulta el afiliado */
                            RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                                rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                            if (rolAfiliadoDTO != null) {
                                aprobacionEnvioComunicadoPersona = validarPeriodoAnalizadoRecaudado(
                                    rolAfiliadoDTO.getDiaHabilVencimientoAporte());
                            }
                        }
                    }

                    if (aprobacionEnvioComunicadoEmpleador || aprobacionEnvioComunicadoPersona) {
                        enviarCorreoParametrizado(notificacion);

                        // Persiste en BitacoraCartera
                        guardarBitacoraPreventiva(solicitudPreventivaModeloDTO,
                            TipoActividadBitacoraEnum.COMUNICADO_GENERADO, MedioCarteraEnum.ELECTRONICO);

                    }

                    // Persiste en BitacoraCartera
                    guardarBitacoraPreventiva(solicitudPreventivaModeloDTO,
                        TipoActividadBitacoraEnum.ACTUALIZACION_EXITOSA, MedioCarteraEnum.ELECTRONICO);

                    /* mantis 236060 y 234370 */
                    actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.EXITOSA);
                    actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.CERRADA);
                    terminarTarea(idTarea, null);
                } else {
                    // Persiste en BitacoraCartera
                    guardarBitacoraPreventiva(solicitudPreventivaModeloDTO,
                        TipoActividadBitacoraEnum.ACTUALIZACION_EXITOSA, MedioCarteraEnum.PERSONAL);
                    actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.EXITOSA);
                    actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.CERRADA);
                    terminarTarea(idTarea, null);
                }
            } else {
                // Persiste en BitacoraCartera
                guardarBitacoraPreventiva(solicitudPreventivaModeloDTO,
                    TipoActividadBitacoraEnum.ACTUALIZACION_NO_EXITOSA,
                    TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaModeloDTO.getTipoGestionCartera())
                        ? MedioCarteraEnum.PERSONAL
                        : MedioCarteraEnum.ELECTRONICO);

                actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.NO_EXITOSA);
                actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.CERRADA);
                terminarTarea(idTarea, null);
            }

            // Persiste en BitacoraCartera
            guardarBitacoraPreventiva(solicitudPreventivaModeloDTO, TipoActividadBitacoraEnum.CIERRE_PREVENTIVA,
                TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaModeloDTO.getTipoGestionCartera())
                    ? MedioCarteraEnum.PERSONAL
                    : MedioCarteraEnum.ELECTRONICO);

            if (TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaModeloDTO.getTipoGestionCartera())) {
                List<Long> idSolicitudAgrupada = new ArrayList<>();
                idSolicitudAgrupada.add(solicitudPreventivaModeloDTO.getIdSolicitudPreventivaAgrupadora());
                List<SolicitudPreventivaAgrupadoraModeloDTO> solicituAgrupadora = validarCierreSolicitudAgrupadora(
                    idSolicitudAgrupada);

                if (!solicituAgrupadora.isEmpty()) {
                    for (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO : solicituAgrupadora) {
                        TareaDTO tareaDTO = obtenerTareaActivaInstancia(
                            Long.parseLong(solicitudPreventivaAgrupadoraModeloDTO.getIdInstanciaProceso()), false);

                        if (tareaDTO != null) {
                            /* Se reasigna la tarea al usuario back para que la cierre */
                            reasignarTarea(tareaDTO.getId(), userDTO.getNombreUsuario());
                            terminarTarea(tareaDTO.getId(), null);
                        }
                    }
                }
            }
            logger.debug("Fin de método actualizarGestionPreventiva(String, Long, Boolean)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en actualizarGestionPreventiva: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Valida el Período analizado recaudado tomando como punto de partida la fecha
     * actual del sistema y sumando
     * el valor seleccionado en el campo “Cantidad de días hábiles previos a la
     * fecha de vencimiento del plazo
     * de pago de aportes para ejecutar este proceso”, verificar que se encuentra
     * dentro de las fechas límite de pago.
     * Esto según la HU -221-160
     *
     * @param diaHabilVencimientoAporte Día hábil vencimiento del aporte para
     *                                  empleador, independiente o pensionado
     * @return
     */
    private Boolean validarPeriodoAnalizadoRecaudado(Short diaHabilVencimientoAporte) {
        List<ElementoListaDTO> elementoListaDTOs = new ArrayList<>();
        List<Integer> idsListaValores = new ArrayList<>();
        List<Date> diasFestivos = new ArrayList<>();

        Boolean periodoAnalizadoRecaudado = false;

        /* se agrega el id a buscar */
        idsListaValores.add(239);
        /* Se consultan los dias habiles */
        elementoListaDTOs = consultarListasValores(idsListaValores);
        /* Se recorre los elementos de ElementoListaDTO */
        for (ElementoListaDTO elementoListaDTO : elementoListaDTOs) {
            /* Se recorre el map */
            for (Entry<String, Object> map : elementoListaDTO.getAtributos().entrySet()) {
                if (map.getKey().equals("fecha")) {
                    Long diaFestivo = (Long) map.getValue();
                    diasFestivos.add(new Date(diaFestivo));
                }
            }
        }
        short contadorDiaHabil = 0;
        Calendar fechaActual = Calendar.getInstance();
        fechaActual = CalendarUtil.fomatearFechaSinHora(fechaActual);
        Calendar fechaPibote = Calendar.getInstance();
        fechaPibote = CalendarUtil.getPrimerDiaDelMes(fechaActual.getTime());
        fechaPibote.set(Calendar.DAY_OF_MONTH, 1);
        for (Date diaFestivo : diasFestivos) {
            Calendar fechaFestivo = Calendar.getInstance();
            fechaFestivo.setTime(diaFestivo);
            fechaFestivo = CalendarUtil.fomatearFechaSinHora(fechaFestivo);
            diaFestivo = fechaFestivo.getTime();
        }
        while (contadorDiaHabil < diaHabilVencimientoAporte) {
            /*
             * Si la fecha pibote no corresponde a un dia festivo o fin de semana se empieza
             * a obtener el dia habil
             */
            if (!(diasFestivos.contains(fechaPibote.getTime())
                || (fechaPibote.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || fechaPibote.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))) {
                contadorDiaHabil++;
            }
            fechaPibote.add(Calendar.DAY_OF_MONTH, 1);
        }

        /*
         * Se valida si la fecha actual es menor a la fecha limite de pago para poder
         * enviar el comunciado
         */
        if (fechaActual.before(fechaPibote)) {
            periodoAnalizadoRecaudado = true;
        }

        return periodoAnalizadoRecaudado;
    }

    /**
     * Método para construir y almacenar un DTO de persistencia en bitácora de
     * cartera, para gestión preventiva
     *
     * @param solicitudDTO Información de la solicitud preventiva
     * @param actividad    Actividad de bitácora
     * @param medio        Medio o canal
     * @return El DTO a persistir
     */
    private void guardarBitacoraPreventiva(SolicitudPreventivaModeloDTO solicitudDTO,
                                           TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio) {
        logger.debug(
            "Inicio de método guardarBitacoraPreventiva(SolicitudPreventivaModeloDTO solicitudDTO, TipoActividadBitacoraEnum actividad,\r\n"
                + "            MedioCarteraEnum medio)");
        ConsultarNumeroOperacionCartera consultarNumeroOperacion = 
        new ConsultarNumeroOperacionCartera(Long.parseLong(solicitudDTO.getNumeroRadicacion()));
        consultarNumeroOperacion.execute();
        logger.info("**___** Este es el resultado: " + consultarNumeroOperacion.getResult());
        BitacoraCarteraDTO bitacoraDTO = new BitacoraCarteraDTO();
        bitacoraDTO.setActividad(actividad);
        bitacoraDTO.setResultado(null);
        bitacoraDTO.setIdPersona(solicitudDTO.getIdPersona());
        bitacoraDTO.setTipoSolicitante(solicitudDTO.getTipoSolicitante());
        bitacoraDTO.setMedio(medio);
        bitacoraDTO.setNumeroOperacion(String.valueOf(consultarNumeroOperacion.getResult()));
        logger.info("**___** bitacoraDTO: "+ bitacoraDTO.toString());
        guardarBitacoraCartera(bitacoraDTO);
        logger.debug(
            "Fin de método guardarBitacoraPreventiva(SolicitudPreventivaModeloDTO solicitudDTO, TipoActividadBitacoraEnum actividad,\r\n"
                + "            MedioCarteraEnum medio)");
    }

    /**
     * Método para construir un DTO de persistencia en bitácora de cartera, para
     * gestión de fiscalización
     *
     * @param solicitudDTO  Información de la solicitud de fiscalización
     * @param actividad     Actividad de bitácora
     * @param idPersona     Identificador único de la persona
     * @param tipoAportante Tipo de aportante
     * @return El DTO a persistir
     */
    private BitacoraCarteraDTO construirBitacoraFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudDTO,
                                                              Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoAportante, TipoActividadBitacoraEnum actividad) {
        logger.debug("Inicio de método construirBitacoraFiscalizacion");
        BitacoraCarteraDTO bitacoraDTO = new BitacoraCarteraDTO();
        bitacoraDTO.setActividad(actividad);
        bitacoraDTO.setResultado(null);
        bitacoraDTO.setIdPersona(idPersona);
        bitacoraDTO.setTipoSolicitante(tipoAportante);
        bitacoraDTO.setMedio(MedioCarteraEnum.PERSONAL);
        bitacoraDTO.setNumeroOperacion(solicitudDTO.getNumeroRadicacion());
        logger.debug("Inicio de método construirBitacoraFiscalizacion");
        return bitacoraDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarAportantesGestionPreventiva(java.lang.Boolean,
     * com.asopagos.dto.modelo.ParametrizacionPreventivaModeloDTO,
     * com.asopagos.rest.security.dto.UserDTO,
     * com.asopagos.cartera.composite.service.UriInfo,
     * com.asopagos.cartera.composite.service.HttpServletResponse)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarAportantesGestionPreventiva(Boolean automatico,
                                                                    ParametrizacionPreventivaModeloDTO parametrizacionDTO, UserDTO userDTO, UriInfo uri,
                                                                    HttpServletResponse response) {

        logger.info("CarteraCompositeBusiness.consultarAportantesGestionPreventiva->" + automatico + ":"
            + parametrizacionDTO + ":" + userDTO + ":" + uri + ":" + response);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.debug("Inicio de método consultarAportantesGestionPreventiva");
            if (parametrizacionDTO == null || parametrizacionDTO.getAplicar() == null) {
                parametrizacionDTO = consultarParametrizacionPreventiva();
            }

            FiltrosParametrizacionDTO filtrosParametrizacion = construirFiltrosParametrizacion(parametrizacionDTO);

            if (automatico == null || !automatico) {
                filtrosParametrizacion.setAutomatico(Boolean.FALSE);
            } else {
                filtrosParametrizacion.setAutomatico(Boolean.TRUE);
                /* Se setea la cantidad de días hábiles ingresado en la paramtrizacion */
                filtrosParametrizacion.setDiasHabilesPrevios(
                    parametrizacionDTO.getDiasHabilesPrevios() != null ? parametrizacionDTO.getDiasHabilesPrevios()
                        : null);
                short contadorDiaHabil = 0;
                List<Date> diasFestivos = new ArrayList<>();
                List<Integer> idsListaValores = new ArrayList<>();
                /* se agrega el id a buscar */
                idsListaValores.add(239);
                /* Se consultan los dias habiles */
                List<ElementoListaDTO> elementoListaDTOs = consultarListasValores(idsListaValores);
                /* Se recorre los elementos de ElementoListaDTO */
                for (ElementoListaDTO elementoListaDTO : elementoListaDTOs) {
                    /* Se recorre el map */
                    for (Entry<String, Object> map : elementoListaDTO.getAtributos().entrySet()) {
                        if (map.getKey().equals("fecha")) {
                            Long diaFestivo = (Long) map.getValue();
                            diasFestivos.add(new Date(diaFestivo));
                        }
                    }
                }

                Calendar fechaActual = Calendar.getInstance();
                fechaActual.add(Calendar.DATE, filtrosParametrizacion.getDiasHabilesPrevios());

                for (Date diaFestivo : diasFestivos) {
                    Calendar fechaFestivo = Calendar.getInstance();
                    fechaFestivo.setTime(diaFestivo);
                    fechaFestivo = CalendarUtil.fomatearFechaSinHora(fechaFestivo);
                    diaFestivo = fechaFestivo.getTime();
                }

                fechaActual = CalendarUtil.fomatearFechaSinHora(fechaActual);
                Calendar fechaPibote = Calendar.getInstance();
                fechaPibote = CalendarUtil.fomatearFechaSinHora(fechaActual);
                fechaPibote.set(Calendar.DAY_OF_MONTH, 1);

                /*
                 * Si la fecha actual correspone a un dia festivo o fin de semana no se
                 * consultan los aportantes
                 */
                if (diasFestivos.contains(fechaActual.getTime())
                    || fechaActual.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || fechaActual.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    return null;
                }

                /*
                 * Se valida si la fecha pibote es menor a la actual para obtener el dia habil
                 */
                while (CalendarUtil.esFechaMenor(fechaPibote, fechaActual)) {
                    /*
                     * Si la fecha pibote no corresponde a un dia festivo o fin de semana se empieza
                     * a obtener el dia habil
                     */
                    if (!(diasFestivos.contains(fechaPibote.getTime())
                        || (fechaPibote.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                        || fechaPibote.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))) {
                        contadorDiaHabil++;
                    }
                    fechaPibote.add(Calendar.DAY_OF_MONTH, 1);
                }

                SimpleDateFormat formatoPeriodo = new SimpleDateFormat("yyyy-MM");
                fechaActual.add(Calendar.MONTH, -1);
                String periodo = formatoPeriodo.format(fechaActual.getTime());
                filtrosParametrizacion.setDiaActualHabil(contadorDiaHabil);
                filtrosParametrizacion.setPeriodoInicialAportes(periodo);
                filtrosParametrizacion.setPeriodoFinalAportes(periodo);
            }

            if (uri != null && uri.getQueryParameters() != null) {
                for (Entry<String, List<String>> e : uri.getQueryParameters().entrySet()) {
                    filtrosParametrizacion.getParams().put(e.getKey(), e.getValue());
                }
            }

            SimulacionPaginadaDTO simulacionPaginada = consultarAportantesParametrizacion(filtrosParametrizacion);
            logger.info("simulacionPaginada:::" + objectMapper.writeValueAsString(simulacionPaginada));

            List<SimulacionDTO> simulaciones = simulacionPaginada.getSimulaciones();
            if (simulacionPaginada.getTotalRecords() != null && response != null) {
                response.addHeader("totalRecords", simulacionPaginada.getTotalRecords().toString());
            }

            Integer usuariosOffset = 0;

            if (filtrosParametrizacion.getParams() != null && filtrosParametrizacion.getParams().containsKey("offset")
                && !filtrosParametrizacion.getParams().get("offset").isEmpty()) {
                usuariosOffset = Integer.parseInt(filtrosParametrizacion.getParams().get("offset").get(0));
            }

            simulaciones = asignarUsuarioGestionPreventiva(simulaciones, userDTO, usuariosOffset);
            logger.debug("Inicio de método consultarAportantesGestionPreventiva");
            return simulaciones;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarAportantesGestionPreventiva: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * asignarAccionesPreventivas(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void asignarAccionesPreventivas(UserDTO userDTO, Boolean automatico) {
        logger.debug("Inicio de método asignarAccionesPreventivas(UserDTO userDTO, Boolean automatico)");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("userDTO:::" + objectMapper.writeValueAsString(userDTO));
            logger.info("Boolean automatico:::" + objectMapper.writeValueAsString(automatico));
            Calendar fechaActual = Calendar.getInstance();
            fechaActual = CalendarUtil.fomatearFechaSinHora(fechaActual);
            fechaActual.set(Calendar.DAY_OF_MONTH, 1);
            fechaActual.set(Calendar.MONTH, fechaActual.get(Calendar.MONTH) - 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String periodoEvaluacion = dateFormat.format(fechaActual.getTime());
            ArrayList<SimulacionDTO> simulaciones = new ArrayList<>(
                consultarAportantesGestionPreventiva(automatico, null, userDTO, null, null));
            logger.info("simulaciones:::" + objectMapper.writeValueAsString(simulaciones));
            ArrayList<SimulacionDTO> simulacionesManual = ((ArrayList<SimulacionDTO>) simulaciones.clone());
            ArrayList<SimulacionDTO> simulacionesAutomatica = (ArrayList<SimulacionDTO>) simulaciones.clone();
            simulacionesManual.removeIf(s -> !s.getTipoGestionPreventiva().equals(TipoGestionCarteraEnum.MANUAL));
            simulacionesAutomatica.removeIf(s -> s.getTipoGestionPreventiva().equals(TipoGestionCarteraEnum.MANUAL));
            logger.info("simulacionesAutomatica :::" + objectMapper.writeValueAsString(simulacionesAutomatica));
            logger.info("simulacionesManual :::" + objectMapper.writeValueAsString(simulacionesManual));
            simulaciones = null;
            logger.info("CarteraCompositeBusiness.asignarAccionesPreventivas->periodoEvaluacion:" + ":"
                + periodoEvaluacion);
            procesarSimulacionManual(simulacionesManual, userDTO, periodoEvaluacion);
            List<Callable<Void>> tareasParalelas = new LinkedList<>();
            for (SimulacionDTO simulacionDTO : simulacionesAutomatica) {
                Callable<Void> parallelTask = () -> {
                    procesarSimulacionAutomatica(simulacionDTO, userDTO, periodoEvaluacion);
                    return null;
                };
                tareasParalelas.add(parallelTask);
            }
            if (!tareasParalelas.isEmpty()) {
                mes.invokeAll(tareasParalelas);
            }
            logger.info("CarteraCompositeBusiness.asignarAccionesPreventivas->:fin" + ":" + automatico);
            logger.info("Fin de método asignarAccionesPreventivas(UserDTO userDTO, Boolean automatico)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en asignarAccionesPreventivas: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * guardarParametrizacionPreventiva(com.asopagos.dto.modelo.
     * ParametrizacionPreventivaModeloDTO)
     */
    @Override
    public void guardarParametrizacionPreventiva(
        ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO) {
        logger.debug(
            "Inicio de método guardarParametrizacionPreventiva(ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO)");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.debug("Inicio::" + objectMapper.writeValueAsString(parametrizacionPreventivaModeloDTO));

            guardarParametrizacionPreventivaCartera(parametrizacionPreventivaModeloDTO);

            ParametrizacionEjecucionProgramadaModeloDTO programacionDTO = new ParametrizacionEjecucionProgramadaModeloDTO();
            programacionDTO.setProceso(ProcesoAutomaticoEnum.GESTION_PREVENTIVA_CARTERA);
            if (parametrizacionPreventivaModeloDTO.getEjecucionAutomatica() != null
                && parametrizacionPreventivaModeloDTO.getEjecucionAutomatica()) {
                logger.debug("!= null::");
                Calendar hora = Calendar.getInstance();
                hora.setTime(new Date(parametrizacionPreventivaModeloDTO.getHoraEjecucion()));
                programacionDTO.setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.DIARIO);
                programacionDTO.setHoras(Integer.toString(hora.get(Calendar.HOUR_OF_DAY)));
                programacionDTO.setMinutos(Integer.toString(hora.get(Calendar.MINUTE)));
                programacionDTO.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones = new ArrayList<>();
                programaciones.add(programacionDTO);
                logger.debug("!= null::" + objectMapper.writeValueAsString(programaciones));
                registrarActualizarProgramacion(programaciones,null,null);
            } else {
                cancelarProgramacionPorProceso(ProcesoAutomaticoEnum.GESTION_PREVENTIVA_CARTERA);
            }
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarParametrizacionPreventiva", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(
            "Fin de método guardarParametrizacionPreventiva(ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarAportantesFiscalizacion()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarAportantesFiscalizacion(
        ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO, UserDTO userDTO, UriInfo uri,
        HttpServletResponse response) {

        try {
            FiltrosParametrizacionDTO filtrosParametrizacion = construirFiltrosParametrizacion(
                parametrizacionFiscalizacionModeloDTO);

            logger.debug("Inicia el método construirFiltrosParametrizacion *****");

            List<String> validacionesPila = new ArrayList<>();
            if (parametrizacionFiscalizacionModeloDTO.getAlertaValidacionPila()) {
                validacionesPila.add(MotivoFiscalizacionAportanteEnum.VALIDACIONES_PILA.name());
            }
            if (parametrizacionFiscalizacionModeloDTO.getSalarioMenorUltimo()) {
                validacionesPila.add(MotivoFiscalizacionAportanteEnum.TRABAJADORES_SALARIO_MENOR.name());
            }
            if (parametrizacionFiscalizacionModeloDTO.getIbcMenorUltimo()) {
                validacionesPila.add(MotivoFiscalizacionAportanteEnum.TRABAJADORES_IBC_MENOR.name());
            }
            if (parametrizacionFiscalizacionModeloDTO.getNovedadRetiro()) {
                validacionesPila.add(MotivoFiscalizacionAportanteEnum.NOVEDAD_RETIRO.name());
            }
            if (parametrizacionFiscalizacionModeloDTO.getEstadoNoOk()) {
                validacionesPila.add(MotivoFiscalizacionAportanteEnum.APORTES_NO_OK.name());
            }
            filtrosParametrizacion.setValidacionesPila(validacionesPila);
            if (!validacionesPila.isEmpty() || parametrizacionFiscalizacionModeloDTO.getGestionPreventiva()) {
                LocalDate fecha = LocalDate.now();
                fecha = fecha.with(lastDayOfMonth());
                Date periodoFinal = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                filtrosParametrizacion.setPeriodoFinal(periodoFinal);
                filtrosParametrizacion.setPeriodoFinalEmpleador(periodoFinal);
                fecha = fecha.minusMonths(parametrizacionFiscalizacionModeloDTO.getPeriodosRetroactivos());
                fecha = fecha.with(firstDayOfMonth());
                Date periodoInicial = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                filtrosParametrizacion.setPeriodoInicial(periodoInicial);
                filtrosParametrizacion.setPeriodoInicialEmpleador(periodoInicial);
            }
            filtrosParametrizacion.setGestionPreventiva(parametrizacionFiscalizacionModeloDTO.getGestionPreventiva());
            filtrosParametrizacion.setCorteEntidades(parametrizacionFiscalizacionModeloDTO.getCorteEntidades());
            filtrosParametrizacion.setIncluirLC2(parametrizacionFiscalizacionModeloDTO.getIncluirLC2());
            filtrosParametrizacion.setIncluirLC3(parametrizacionFiscalizacionModeloDTO.getIncluirLC3());
            logger.debug("Finaliza el método construirFiltrosParametrizacion *****");
            return consultarAportantesParametrizacionFiscalizacion(filtrosParametrizacion);
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesFiscalizacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * crearNuevoCicloFiscalizacion(java.lang.Long, java.lang.Long,
     * java.util.List)
     */
    @Override
    public CicloCarteraModeloDTO crearNuevoCicloFiscalizacion(Long fechaInicio, Long fechaFin,
                                                              List<SimulacionDTO> simulacionDTOs, UserDTO user) {
        logger.debug("Inicia crearNuevoCicloFiscalizacion(Long fechaInicio, Long fechaFin, List<SimulacionDTO>)");
        try {
            if (verificarCicloFiscalizacionNoActivo()) {
                // Armar ciclo fiscalizacion y todos sus aportantes
                CicloCarteraModeloDTO cicloFiscalizacionModeloDTO = new CicloCarteraModeloDTO();
                cicloFiscalizacionModeloDTO.setFechaInicio(fechaInicio);
                cicloFiscalizacionModeloDTO.setFechaFin(fechaFin);
                cicloFiscalizacionModeloDTO.setFechaCreacion(new Date().getTime());
                cicloFiscalizacionModeloDTO.setEstadoCicloFiscalizacion(EstadoCicloCarteraEnum.ACTIVO);
                cicloFiscalizacionModeloDTO.setTipoCiclo(TipoCicloEnum.FISCALIZACION);
                List<CicloAportanteModeloDTO> cicloAportantes = new ArrayList<>();
                /* Se agregan los aportantes */
                for (SimulacionDTO simulation : simulacionDTOs) {
                    CicloAportanteModeloDTO cicloAportanteModeloDTO = new CicloAportanteModeloDTO();
                    PersonaModeloDTO persona = consultarDatosPersona(simulation.getTipoIdentificacion(),
                        simulation.getNumeroIdentificacion());
                    if (simulation.getUsuarioAnalista() == null || simulation.getUsuarioAnalista().isEmpty()) {
                        logger.error(
                            "Error en metodo crearNuevoCicloFiscalizacion(Long fechaInicio, Long fechaFin, List<SimulacionDTO> simulacionDTOs,UserDTO user)");
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

                    } else {
                        cicloAportanteModeloDTO.setIdPersona(persona.getIdPersona());
                        cicloAportanteModeloDTO.setTipoSolicitanteMovimientoAporteEnum(simulation.getTipoAportante());
                        cicloAportanteModeloDTO.setAnalista(simulation.getUsuarioAsignacion());
                        if (!cicloAportantes.contains(cicloAportanteModeloDTO.getIdPersona())) {
                            cicloAportantes.add(cicloAportanteModeloDTO);
                        }
                    }
                }
                cicloFiscalizacionModeloDTO.setAportantes(cicloAportantes);
                // Se debe invocar el servicio de guardar el ciclo de fiscalizacion
                cicloFiscalizacionModeloDTO = guardarCicloFiscalizacion(cicloFiscalizacionModeloDTO);

                for (CicloAportanteModeloDTO aportanteModeloDTO : cicloFiscalizacionModeloDTO.getAportantes()) {
                    /* Se construye objeto SolicitudFiscalizacionModeloDTO */
                    SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = construirSolicitudFiscalizacionModeloDTO(
                        aportanteModeloDTO.getIdCicloAportante(), user);
                    /* Se guarda la solicitud de fiscalizacion */
                    Long idSolicitudFiscalizacion = guardarSolicitudFiscalizacion(solicitudFiscalizacionModeloDTO);

                    String numeroRadicacion = generarNumeroRadicado(idSolicitudFiscalizacion,
                        user.getSedeCajaCompensacion());
                    Map<String, Object> params = new HashMap<>();
                    params.put(ID_SOLICITUD, idSolicitudFiscalizacion);
                    params.put(NUMERO_RADICADO, numeroRadicacion);
                    params.put(ANALISTA_CARTERA, aportanteModeloDTO.getAnalista());
                    /* Se inicia el proceso */
                    Long idProceso = iniciarProceso(ProcesoEnum.FISCALIZACION_CARTERA, params, Boolean.FALSE);
                    solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion(numeroRadicacion);
                    solicitudFiscalizacionModeloDTO.setIdInstanciaProceso(idProceso.toString());
                    solicitudFiscalizacionModeloDTO.setDestinatario(aportanteModeloDTO.getAnalista());
                    solicitudFiscalizacionModeloDTO.setSedeDestinatario(user.getSedeCajaCompensacion());
                    guardarSolicitudFiscalizacion(solicitudFiscalizacionModeloDTO);

                    // Persiste en BitacoraCartera
                    BitacoraCarteraDTO bitacoraDTO = construirBitacoraFiscalizacion(solicitudFiscalizacionModeloDTO,
                        aportanteModeloDTO.getIdPersona(),
                        aportanteModeloDTO.getTipoSolicitanteMovimientoAporteEnum(),
                        TipoActividadBitacoraEnum.INGRESO_FISCALIZACION);
                    guardarBitacoraCartera(bitacoraDTO);
                }
                logger.debug(
                    "Finaliza crearNuevoCicloFiscalizacion(Long fechaInicio, Long fechaFin, List<SimulacionDTO>)");
                return cicloFiscalizacionModeloDTO;
            } else {
                /* Cuando un ciclo activo genera la excepcion */
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
            }
        } catch (AsopagosException as) {
            throw as;

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en crearNuevoCicloFiscalizacion: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * excluirAportanteFiscalizacion(java.lang.Long, java.lang.Long)
     */
    @Override
    public void terminarProcesoFiscalizacion(String numeroRadicacion, Long idTarea,
                                             EstadoFiscalizacionEnum estadoFiscalizacion) {
        logger.debug(
            "Inicio de método terminarProcesoFiscalizacion(String numeroRadicacion, Long idTarea, EstadoFiscalizacionEnum estadoFiscalizacion, Long idSolicitud)");
        try {
            /*
             * Se realiza llamado al metodo para actualizar el estado de la solicitud de
             * fiscalizacion
             */
            actualizarEstadoSolicitudFiscalizacion(numeroRadicacion, estadoFiscalizacion);
            /* Se invoca la tarea */
            terminarTarea(idTarea, null);
            verificarCierreCiclos(TipoCicloEnum.FISCALIZACION, null);
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error inesperado en terminarProcesoFiscalizacion(String numeroRadicacion, Long idTarea, EstadoFiscalizacionEnum estadoFiscalizacion, Long idSolicitud) ",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(
            "Fin de método terminarProcesoFiscalizacion(String numeroRadicacion, Long idTarea, EstadoFiscalizacionEnum estadoFiscalizacion, Long idSolicitud)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarFiscalizacionAportes(java.lang.Long,
     * com.asopagos.dto.modelo.ProgramacionFiscalizacionDTO)
     */
    @Override
    public void guardarFiscalizacionAportes(String numeroRadicacion,
                                            ProgramacionFiscalizacionDTO programacionFiscalizacionDTO) {
        logger.debug(
            "Inicio de método guardarFiscalizacionAportes(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO)");
        try {
            /* Se consulta la solicitud de fiscalización */
            SolicitudFiscalizacionModeloDTO fiscalizacionModeloDTO = consultarSolicitudFiscalizacion(numeroRadicacion);

            /* Se settea el id de ciclo aportante a todas las agendas */
            for (AgendaCarteraModeloDTO agendaFiscalizacionModeloDTO : programacionFiscalizacionDTO
                .getAgendaFiscalizacionModeloDTOs()) {
                agendaFiscalizacionModeloDTO.setIdCicloAportante(fiscalizacionModeloDTO.getIdCicloAportante());
            }
            /* Se settea el id de ciclo aportante a todas las actividades */
            for (ActividadCarteraModeloDTO actividadFiscalizacionModeloDTO : programacionFiscalizacionDTO
                .getActividadFiscalizacionModeloDTOs()) {
                actividadFiscalizacionModeloDTO.setIdCicloAportante(fiscalizacionModeloDTO.getIdCicloAportante());
            }

            /* Se realiza llamado al metodo para terminar el proceso de fiscalizacion */
            terminarFiscalizacion(programacionFiscalizacionDTO);

            /* Se valida si el estado de la solicitud no está en proceso */
            if (!EstadoFiscalizacionEnum.EN_PROCESO.equals(fiscalizacionModeloDTO.getEstadoFiscalizacion())) {
                /* Se actualiza el estado de la solicitud */
                actualizarEstadoSolicitudFiscalizacion(numeroRadicacion, EstadoFiscalizacionEnum.EN_PROCESO);
            }

        } catch (Exception e) {
            logger.error(
                "Finaliza el método guardarFiscalizacionAportes(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(
            "Finaliza el método guardarFiscalizacionAportes(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarFiscalizacionAportesTemporal(java.lang.Long)
     */
    @Override
    public ProgramacionFiscalizacionDTO consultarFiscalizacionAportesTemporal(Long idSolicitud) {
        logger.debug("Inicio de método consultarFiscalizacionAportesTemporal(Long idSolicitud)");
        ProgramacionFiscalizacionDTO programacionFiscalizacionDTO = null;
        try {
            /*
             * Permite recuperar la información que se encuentra en la tabla temporal y lo
             * retorna en un objeto ProgramacionFiscalizacionDTO
             */
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            if (jsonPayload != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                programacionFiscalizacionDTO = objectMapper.readValue(jsonPayload, ProgramacionFiscalizacionDTO.class);
            } else {
                return null;
            }
            logger.debug("Finaliza el método consultarFiscalizacionAportesTemporal(Long idSolicitud)");
        } catch (Exception e) {
            logger.error("Finaliza el método consultarFiscalizacionAportesTemporal(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return programacionFiscalizacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarAportantesCicloActual(com.asopagos.dto.cartera.FiscalizacionAportanteDTO,
     * com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    public List<SimulacionDTO> consultarAportantesCicloActual(TipoIdentificacionEnum tipoIdentificacion,
                                                              String numeroIdentificacion, String analista, EstadoFiscalizacionEnum estado, UserDTO user) {
        logger.debug(
            "Inicio de método consultarAportantesCicloActual(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String analista, EstadoFiscalizacionEnum estado, UserDTO user)");

        /* Se consulta la persona */

        List<SimulacionDTO> simulacionDTOs = new ArrayList<>();
        PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
        List<ConsultarEstadoDTO> lstConsultaEstadoPersona = new ArrayList<>();
        try {

            if (tipoIdentificacion != null && !numeroIdentificacion.isEmpty()) {
                personaModeloDTO = consultarDatosPersona(tipoIdentificacion, numeroIdentificacion);

                /* Si la persona esta vacia */
                if (personaModeloDTO == null || (personaModeloDTO.getNumeroIdentificacion() == null
                    && personaModeloDTO.getTipoIdentificacion() == null)) {
                    return null;
                }
            }

            List<EstadoFiscalizacionEnum> estados = new ArrayList<>(3);
            List<String> listAnalistas = new ArrayList<>();

            /* Se pregunta si el estado es diferente de null */
            if (estado != null) {
                estados.add(estado);
            } else {
                /*
                 * Se llena la lista de enum de estado de ciclo fiscalizacion con todos sus
                 * valores
                 */
                estados.add(EstadoFiscalizacionEnum.ASIGNADO);
                estados.add(EstadoFiscalizacionEnum.EN_PROCESO);
                estados.add(EstadoFiscalizacionEnum.EXCLUIDA);
                estados.add(EstadoFiscalizacionEnum.FINALIZADA);
            }
            /*
             * Se pregunta si el analista no tiene informacion
             * para poder llenar la lista de analistas
             */
            if (analista == null || analista.isEmpty()) {

                List<UsuarioDTO> usuarioDTOs = obtenerMiembrosGrupo(ANA_CAR_APO, user.getSedeCajaCompensacion(),
                    EstadoUsuarioEnum.ACTIVO);

                for (UsuarioDTO usuario : usuarioDTOs) {
                    listAnalistas.add(usuario.getNombreUsuario());
                }
            } else {
                /* Se adiciona el analista */
                listAnalistas.add(analista);
            }
            /* Se invoca servicio de consultarAportantesCiclo */
            simulacionDTOs = consultarAportantesCiclo(listAnalistas, estados, personaModeloDTO.getIdPersona());
            /*
             * Se recorre la simulacion para verificar de que los aportantes que sean
             * diferentes a empleador
             * tengan estado afiliado
             */
            for (SimulacionDTO simulacionDTO : simulacionDTOs) {

                if (!TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(simulacionDTO.getTipoAportante())) {

                    ConsultarEstadoDTO consultaEstado = new ConsultarEstadoDTO();
                    consultaEstado.setTipoIdentificacion(simulacionDTO.getTipoIdentificacion());
                    consultaEstado.setNumeroIdentificacion(simulacionDTO.getNumeroIdentificacion());
                    /* Se especifica el tipo de persona */
                    consultaEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                    consultaEstado.setEntityManager(entityManager);
                    /*
                     * Se envia el tipo de indetificacion y numero de identificacion para ser
                     * consultado
                     * en el metodo darEstadoAfiliado
                     */
                    lstConsultaEstadoPersona.add(consultaEstado);
                }
                /* Se setea estado de la cartera */
                simulacionDTO.setEstadoActualCartera(EstadoCarteraEnum.MOROSO);
            }

            if (!lstConsultaEstadoPersona.isEmpty()) {
                /*
                 * Se realiza el proceso de setear el estado afiliado cuando el aportante no es
                 * empleador
                 */
                simulacionDTOs = obtenerEstadoAfiliado(lstConsultaEstadoPersona, simulacionDTOs);
            }

        } catch (Exception e) {
            logger.error(
                "método consultarAportantesCicloActual(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String analista, EstadoFiscalizacionEnum estado, UserDTO user)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(
            "Finaliza de método consultarAportantesCicloActual(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String analista, EstadoFiscalizacionEnum estado, UserDTO user)");
        return simulacionDTOs;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#cancelarCicloFiscalizacion(long,
     * java.lang.Long)
     */
    @Override
    public void cancelarCicloFiscalizacion() {
        logger.debug("Inicio de método cancelarCicloFiscalizacion()");
        try {
            CicloCarteraModeloDTO cicloFiscalizacionModeloDTO = consultarCicloFiscalizacionActual();
            cicloFiscalizacionModeloDTO.setFechaFin(new Date().getTime());
            terminarCicloFiscalizacion(EstadoCicloCarteraEnum.CANCELADO, cicloFiscalizacionModeloDTO);
        } catch (AsopagosException as) {
            logger.error("Error en método cancelarCicloFiscalizacion()", as);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, as);
        } catch (Exception e) {
            logger.error("Finaliza de método cancelarCicloFiscalizacion()");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza de método cancelarCicloFiscalizacion()");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * finalizarCicloFiscalizacion()
     */
    @Override
    public void finalizarCiclosVencidos() {
        logger.debug("Inicio de método finalizarCicloFiscalizacion()");
        try {

            List<CicloCarteraModeloDTO> cicloFiscalizacionModeloDTOs = consultarCiclosVencidos();

            if (cicloFiscalizacionModeloDTOs != null && !cicloFiscalizacionModeloDTOs.isEmpty()) {
                for (CicloCarteraModeloDTO cicloCarteraModeloDTO : cicloFiscalizacionModeloDTOs) {
                    /* si la fecha fin es mayor a la fecha actual */
                    terminarCicloFiscalizacion(EstadoCicloCarteraEnum.FINALIZADO, cicloCarteraModeloDTO);
                }
            }

        } catch (AsopagosException as) {
            logger.error("Error en método finalizarCicloFiscalizacion()", as);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, as);
        } catch (Exception e) {
            logger.error("Finaliza de método finalizarCicloFiscalizacion()");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza de método finalizarCicloFiscalizacion()");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * asignarUsuarioCiclo(java.util.List,
     * com.asopagos.enumeraciones.core.ProcesoEnum,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<SimulacionDTO> asignarUsuarioCiclo(List<SimulacionDTO> simulacionDTOs, ProcesoEnum proceso,
                                                   UserDTO user) {
        logger.debug("Inicia asignarUsuarioCiclo(List<SimulacionDTO> simulacionDTOs,UserDTO user) ");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        try {
            for (SimulacionDTO simulacionDTO : simulacionDTOs) {

                /* Se obtiene el analista */
                String analista = asignarAutomaticamenteUsuarioCajaCompensacion(
                    new Long(user.getSedeCajaCompensacion()), proceso);

                usuarioDTO = consultarUsuarioCajaCompensacion(analista);

                StringBuilder nombreAnalista = new StringBuilder();
                nombreAnalista.append(usuarioDTO.getPrimerNombre());
                nombreAnalista.append(ESPACIO);
                nombreAnalista.append(usuarioDTO.getSegundoNombre() != null ? usuarioDTO.getSegundoNombre() : "");
                nombreAnalista.append(ESPACIO);
                nombreAnalista.append(usuarioDTO.getPrimerApellido());
                nombreAnalista.append(ESPACIO);
                nombreAnalista.append(usuarioDTO.getSegundoApellido() != null ? usuarioDTO.getSegundoApellido() : "");

                /* Se setea el analista a la simulacion */
                simulacionDTO.setUsuarioAnalista(nombreAnalista.toString());
                simulacionDTO.setUsuarioAsignacion(usuarioDTO.getNombreUsuario());
            }
        } catch (AsopagosException ae) {
            logger.error("Error en método asignarUsuarioCiclo(List<SimulacionDTO> simulacionDTOs,UserDTO user)", ae);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug("Finaliza asignarUsuarioCiclo(List<SimulacionDTO> simulacionDTOs,UserDTO user) ");
        return simulacionDTOs;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#buscarDetallesCicloFiscalizacion(java.lang.Long,
     * java.lang .
     * boolean)
     */
    @Override
    public List<SimulacionDTO> buscarDetallesCicloFiscalizacion(Long idCiclo, boolean esSupervisor,
                                                                boolean gestionManual, UserDTO user) {
        try {
            logger.debug("Inica  buscarDetallesCicloFiscalizacion(Long idCiclo)");
            List<ConsultarEstadoDTO> lstConsultaEstadoPersona = new ArrayList<>();
            ConsultarEstadoDTO consultaEstadoCaja = null;
            List<SimulacionDTO> lstDetallesCiclo = consultarDetalleCiclo(idCiclo, esSupervisor, gestionManual);
            if (lstDetallesCiclo != null) {
                for (SimulacionDTO simulacionDTO : lstDetallesCiclo) {
                    if (!TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(simulacionDTO.getTipoAportante())) {
                        consultaEstadoCaja = new ConsultarEstadoDTO();
                        consultaEstadoCaja.setTipoIdentificacion(simulacionDTO.getTipoIdentificacion());
                        consultaEstadoCaja.setNumeroIdentificacion(simulacionDTO.getNumeroIdentificacion());
                        /* Se especifica el tipo de persona */
                        consultaEstadoCaja.setTipoPersona(ConstantesComunes.PERSONAS);
                        consultaEstadoCaja.setEntityManager(entityManager);
                        lstConsultaEstadoPersona.add(consultaEstadoCaja);
                    }
                    /* Se setea estado de la cartera */
                    simulacionDTO.setEstadoActualCartera(EstadoCarteraEnum.MOROSO);
                }
                if (!lstConsultaEstadoPersona.isEmpty()) {
                    lstDetallesCiclo = obtenerEstadoAfiliado(lstConsultaEstadoPersona, lstDetallesCiclo);
                }
                logger.debug("Finaliza  buscarDetallesCicloFiscalizacion(Long idCiclo)");
            }
            return lstDetallesCiclo;
        } catch (AsopagosException ae) {
            logger.error("Error en método buscarDetallesCicloFiscalizacion(Long idCiclo)", ae);
            throw ae;
        } catch (Exception e) {
            logger.error("Error en método buscarDetallesCicloFiscalizacion(Long idCiclo)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#buscarDetalleCicloAportante(java.lang.String)
     */
    @Override
    public SimulacionDTO buscarDetalleCicloAportante(String numeroRadicado) {
        try {
            logger.debug("Inicia servicio buscarDetalleCicloAportante(String numeroRadicado)");
            SimulacionDTO detalleCiclo = consultarDetalleCicloAportante(numeroRadicado);
            if (detalleCiclo != null) {

                List<ConsultarEstadoDTO> consultaEstadoDTOs = construirEstadoCaja(detalleCiclo.getTipoIdentificacion(),
                    detalleCiclo.getNumeroIdentificacion(), detalleCiclo.getTipoAportante());
                /* Se consulta estado */
                List<EstadoDTO> estadoDTOs = consultarEstadoCaja(consultaEstadoDTOs);
                detalleCiclo.setEstadoAfiliado(estadoDTOs.get(0).getEstado());
                /* Se setea estado de la cartera */
                detalleCiclo.setEstadoActualCartera(EstadoCarteraEnum.MOROSO);
            }
            logger.debug("Finaliza servicio buscarDetalleCicloAportante(String numeroRadicado)");
            return detalleCiclo;
        } catch (AsopagosException ae) {
            logger.error("Error en servicio buscarDetalleCicloAportante(String numeroRadicado)", ae);
            throw ae;
        } catch (Exception e) {
            logger.error("Error en servicio buscarDetalleCicloAportante(String numeroRadicado)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#obtenerParametrosComunicadoPreventiva(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * java.lang.String)
     */
    @Override
    public Map<String, String> obtenerParametrosComunicadoPreventiva(
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante, String numeroIdentificacion) {
        Map<String, String> params = new HashMap<>();
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime(new Date());
        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
            fechaActual.add(Calendar.MONTH, -1);
        }
        String fechaPeriodo = dateFormat.format(fechaActual.getTime());
        params.put(PERIODO_EVALUAR, fechaPeriodo);
        Long fechaVencimiento = obtenerFechaVencimiento(fechaPeriodo, tipoSolicitante, numeroIdentificacion);
        String fechaAVencer = new SimpleDateFormat("dd/MM/yyyy").format(new Date(fechaVencimiento));
        params.put(FECHA_VENCIMIENTO, fechaAVencer);
        return params;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#validacionesCrearConvenioPago(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public Boolean validarCrearConvenioPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        EstadoAfiliadoEnum estadoCCF = null;
        try {
            Boolean validado = false;
            List<EstadoDTO> responseListEstadoEmpleador = null;
            /* Se obtiene la construccion de los estados */
            List<ConsultarEstadoDTO> consolidadoCaja = construirEstadoCaja(tipoIdentificacion, numeroIdentificacion,
                tipoSolicitante);
            /* Se consulta el estado */
            responseListEstadoEmpleador = consultarEstadoCaja(consolidadoCaja);

            if (responseListEstadoEmpleador != null && !responseListEstadoEmpleador.isEmpty()) {
                estadoCCF = responseListEstadoEmpleador.get(0).getEstado();
            }

            EstadoCarteraEnum estadoCartera = consultarEstadoCartera(tipoIdentificacion, numeroIdentificacion,
                tipoSolicitante);
            List<ConvenioPagoModeloDTO> conveniosPago = consultarConveniosPago(tipoIdentificacion, numeroIdentificacion,
                tipoSolicitante);
            ParametrizacionConveniosPagoModeloDTO parametrizacionConveniosPagoModeloDTO = consultarParametrizacionConveniosPago();

            if (EstadoCarteraEnum.MOROSO.equals(estadoCartera)
                && parametrizacionConveniosPagoModeloDTO.getCantidadPeriodos() >= conveniosPago.size()) {
                Boolean noApto = false;
                for (ConvenioPagoModeloDTO convenioPagoModeloDTO : conveniosPago) {
                    if (EstadoConvenioPagoEnum.ACTIVO.equals(convenioPagoModeloDTO.getEstadoConvenioPago())
                        || EstadoConvenioPagoEnum.ANULADO.equals(convenioPagoModeloDTO.getEstadoConvenioPago())) {
                        noApto = true;
                        break;
                    }
                }
                if (!noApto) {
                    validado = true;
                }
            }
            return validado;
        } catch (Exception e) {
            logger.error(
                "Error en servicio validacionesCrearConvenioPago(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarFechasLimitePago(java.lang.Short,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * java.lang.String)
     */
    @Override
    public List<Long> consultarFechasLimitePago(Short numeroCuotas, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                String numeroIdentificacion) {
        String firmaMetodo = "CarteraCompositeBusiness.consultarFechasLimitePago(Short, TipoSolicitanteMovimientoAporteEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> lstFechaVencimiento = new ArrayList<>();
        try {
            LocalDate fechaActual = LocalDate.now();
            if (!TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                fechaActual = fechaActual.plusMonths(1L);
            }
            int cuentaCuotas = 1;

            while (cuentaCuotas <= numeroCuotas) {
                String periodo = fechaActual.getYear() + "-" + fechaActual.getMonth().getValue();
                lstFechaVencimiento.add(obtenerFechaVencimiento(periodo, tipoSolicitante, numeroIdentificacion));
                cuentaCuotas++;
                fechaActual = fechaActual.plusMonths(1L);
            }

            if (lstFechaVencimiento.isEmpty()) {
                lstFechaVencimiento = null;
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstFechaVencimiento;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarExclusionCarteraActiva(com.asopagos.dto.cartera.ExclusionCarteraDTO)
     */
    @Override
    public List<ExclusionCarteraDTO> consultarExclusionCarteraActiva(ExclusionCarteraDTO exclusionCarteraDTO) {
        try {
            logger.debug("Inicia consultarExclusionCarteraActiva(ExclusionCarteraDTO exclusionCarteraDTO)");
            Set<Long> lstIdPersonas = new HashSet<>();
            List<EstadoDTO> estadoConsolidadoCaja = new ArrayList<>();
            List<ConsultarEstadoDTO> consolidadoCaja = new ArrayList<>();
            List<ExclusionCarteraDTO> lstExclusionesCartera = new ArrayList<>();
            List<PersonaDTO> lstPersonasDTO = consultarAportantesCaja(exclusionCarteraDTO.getTipoSolicitante(),
                exclusionCarteraDTO.getTipoIdentificacion(), exclusionCarteraDTO.getNumeroIdentificacion(),
                exclusionCarteraDTO.getRazonSocial(), exclusionCarteraDTO.getPrimerNombre(),
                exclusionCarteraDTO.getSegundoNombre(), exclusionCarteraDTO.getPrimerApellido(),
                exclusionCarteraDTO.getSegundoApellido());

            if (!lstPersonasDTO.isEmpty()) {
                for (PersonaDTO personaDTO : lstPersonasDTO) {
                    // Se agrega el aportante a una lista para consulta en bloque de estados
                    // respecto a la caja
                    consolidadoCaja.addAll(construirEstadoCaja(personaDTO.getTipoIdentificacion(),
                        personaDTO.getNumeroIdentificacion(), exclusionCarteraDTO.getTipoSolicitante()));
                    lstIdPersonas.add(personaDTO.getIdPersona());
                }

                /* Se consultan los estados de caja */
                estadoConsolidadoCaja = consultarEstadoCaja(consolidadoCaja);

                // Se consultan las exclusiones cartera
                String sTamanoPaginador = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
                Integer tamanoPaginador = new Integer(sTamanoPaginador);
                Integer count = 0;
                List<Long> lstIdPersonasTmp = new ArrayList<>();
                List<List<Long>> lstPaginaIdPersonasTmp = new ArrayList<>();
                for (Long id : lstIdPersonas) {
                    count++;
                    lstIdPersonasTmp.add(id);
                    if (tamanoPaginador.equals(count)) {
                        lstPaginaIdPersonasTmp.add(lstIdPersonasTmp);
                        lstIdPersonasTmp = new ArrayList<>();
                        count = 0;
                    }
                }
                if (count.intValue() > 0) {
                    lstPaginaIdPersonasTmp.add(lstIdPersonasTmp);
                }

                List<Callable<List<ExclusionCarteraDTO>>> lstExclusionesCarteraCallable = new ArrayList<>();
                for (List<Long> peticion : lstPaginaIdPersonasTmp) {
                    Callable<List<ExclusionCarteraDTO>> parallelTask = () -> {
                        return buscarListaExclusionCarteraActiva(exclusionCarteraDTO.getTipoSolicitante(), peticion);
                    };
                    lstExclusionesCarteraCallable.add(parallelTask);
                }

                if (!lstExclusionesCarteraCallable.isEmpty()) {
                    List<Future<List<ExclusionCarteraDTO>>> futures = mes.invokeAll(lstExclusionesCarteraCallable);
                    for (Future<List<ExclusionCarteraDTO>> future : futures) {
                        lstExclusionesCartera.addAll(future.get());
                    }
                }

                // Se obtiene el estado de estado del afiliado para las exclusiones de la caja
                if (lstExclusionesCartera != null && !lstExclusionesCartera.isEmpty()) {
                    for (ExclusionCarteraDTO exclusionDTO : lstExclusionesCartera) {
                        for (EstadoDTO estado : estadoConsolidadoCaja) {
                            if (exclusionDTO.getTipoIdentificacion().equals(estado.getTipoIdentificacion())
                                && exclusionDTO.getNumeroIdentificacion()
                                .equals(estado.getNumeroIdentificacion())) {
                                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
                                    .equals(exclusionDTO.getTipoSolicitante())) {
                                    exclusionDTO.setEstadoEmpleadorCaja(
                                        EstadoEmpleadorEnum.valueOf(estado.getEstado().name()));
                                } else {
                                    exclusionDTO.setEstadoAfiliadoRespectoCaja(estado.getEstado());
                                }
                            }
                        }
                    }
                }
            }

            logger.debug("Finaliza consultarExclusionCarteraActiva(ExclusionCarteraDTO exclusionCarteraDTO)");
            return lstExclusionesCartera;
        } catch (Exception e) {
            logger.error("Error en servicio consultarExclusionCarteraActiva(ExclusionCarteraDTO exclusionCarteraDTO)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que invoca el servicio que consulta la información de una persona
     * registrada en la CCF como aportante
     *
     * @param tipoAportante        Tipo de aportante
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param razonSocial          Razón social
     * @param primerNombre         Primer nombre
     * @param segundoNombre        Segundo nombre
     * @param primerApellido       Primer apellido
     * @param segundoApellido      Segundo apellido
     * @return La lista de aportantes que cumplen con los criterios de búsqueda
     */
    private List<PersonaDTO> consultarAportantesCaja(TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                                     TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
                                                     String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
        logger.debug("Inicio de método consultarAportantesCaja");
        ConsultarAportantesCaja service = new ConsultarAportantesCaja(primerApellido, tipoAportante, primerNombre,
            segundoApellido, numeroIdentificacion, tipoIdentificacion, segundoNombre, razonSocial);
        service.execute();
        logger.debug("Fin de método consultarAportantesCaja");
        return service.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#ejecutarProcesoAutomaticoConvenioPago()
     */
    @Override
    public void ejecutarProcesoAutomaticoConvenioPago() {
        logger.debug("Inicio método ejecutarProcesoAutomaticoConvenioPago");
        List<Date> diasFestivos = new ArrayList<>();
        List<Integer> idsListaValores = new ArrayList<>();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        /* se agrega el id a buscar */
        idsListaValores.add(239);
        /* Se consultan los dias habiles */
        List<ElementoListaDTO> elementoListaDTOs = consultarListasValores(idsListaValores);
        for (ElementoListaDTO elementoListaDTO : elementoListaDTOs) {
            for (Entry<String, Object> map : elementoListaDTO.getAtributos().entrySet()) {
                if (map.getKey().equals("fecha")) {
                    LocalDateTime diaFestivo = Instant.ofEpochMilli(((Long) map.getValue()))
                        .atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
                    diasFestivos.add(Date.from(diaFestivo.atZone(ZoneId.systemDefault()).toInstant()));
                }
            }
        }
        /* se revisan los convenios que se vencieron y no tuvieron pago */
        List<ConvenioPagoModeloDTO> conveniosPago = consultarPagoConvenio(diasFestivos);
        /* si hay resultados, es porque no hubo el pago de algún convenio */
        if (conveniosPago != null && !conveniosPago.isEmpty()) {
            Long fechaAnulacion = new Date().getTime();
            for (ConvenioPagoModeloDTO convenioPagoModeloDTO : conveniosPago) {
                convenioPagoModeloDTO.setEstadoConvenioPago(EstadoConvenioPagoEnum.ANULADO);
                convenioPagoModeloDTO.setMotivoAnulacion(MotivoAnulacionEnum.INCUMPLIMIENTO);
                convenioPagoModeloDTO.setFechaAnulacion(fechaAnulacion);
            }
            actualizarConveniosPago(conveniosPago);
            for (ConvenioPagoModeloDTO convenioPagoModeloDTO : conveniosPago) {
                RetomarAccionesCobro retomarService = new RetomarAccionesCobro(
                    convenioPagoModeloDTO.getTipoSolicitante(), convenioPagoModeloDTO.getIdPersona());
                retomarService.execute();
            }
        }

        /* se revisan los convenios que ya cumplieron con los pagos para cerrarlos */
        List<ConvenioPagoModeloDTO> conveniosPagoCierre = consultarConveniosCierre(diasFestivos);
        if (conveniosPagoCierre != null && !conveniosPagoCierre.isEmpty()) {
            for (ConvenioPagoModeloDTO convenioPagoModeloDTO : conveniosPagoCierre) {
                convenioPagoModeloDTO.setEstadoConvenioPago(EstadoConvenioPagoEnum.CERRADO);
            }
            actualizarConveniosPago(conveniosPagoCierre);
        }
        /*
         * se revisan los convenios a lo que se les debe enviar comunicado de cierre
         * satisfactorio
         */
        List<ConvenioPagoModeloDTO> conveniosComunicado = consultarConveniosComunicado(diasFestivos);
        /* Se recorre la lista */
        for (ConvenioPagoModeloDTO convenioPagoModeloDTO : conveniosComunicado) {
            DatosComunicadoCierreConvenioDTO cierreConvenioDTO = consultarInformacionCierreConvenio(
                convenioPagoModeloDTO.getIdPersona(), convenioPagoModeloDTO.getTipoSolicitante());
            cierreConvenioDTO.setNumeroConvenio(convenioPagoModeloDTO.getIdConvenioPago());
            /* Se arma la notificación */
            NotificacionParametrizadaDTO notificacionParametrizadaDTO = construirComunicado(cierreConvenioDTO);
            notificacionParametrizadaDTO.setIdPersona(convenioPagoModeloDTO.getIdPersona());
            /* Se envia correo parametrizado */
            notificacionParametrizadaDTO.getParams().put("fechaFirmaConvenio",
                convenioPagoModeloDTO.getFechaRegistro() != null
                    ? simpleDate.format(new Date(convenioPagoModeloDTO.getFechaRegistro()))
                    : simpleDate.format(new Date()));
            enviarCorreoParametrizadoAsincrono(notificacionParametrizadaDTO);
        }
        logger.debug("Fin método ejecutarProcesoAutomaticoConvenioPago");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#buscarExclusionPorAportante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public List<ExclusionCarteraDTO> buscarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion,
                                                                 String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        try {
            logger.debug(
                "Inicio método buscarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
            List<ExclusionCarteraDTO> lstExclusionesCartera = new ArrayList<>();
            PersonaDTO personaDTO = buscarTipoAfiliacionPersona(tipoIdentificacion, numeroIdentificacion,
                tipoSolicitante);
            if (personaDTO != null) {
                lstExclusionesCartera = consultarExclusionPorAportante(personaDTO.getTipoIdentificacion(),
                    personaDTO.getNumeroIdentificacion(), personaDTO.getTipoSolicitante());

                if (lstExclusionesCartera != null) {
                    for (ExclusionCarteraDTO exclusionCarteraDTO : lstExclusionesCartera) {
                        exclusionCarteraDTO.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
                        exclusionCarteraDTO.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
                        exclusionCarteraDTO.setPrimerNombre(personaDTO.getPrimerNombre());
                        exclusionCarteraDTO.setSegundoNombre(personaDTO.getSegundoNombre());
                        exclusionCarteraDTO.setPrimerApellido(personaDTO.getPrimerApellido());
                        exclusionCarteraDTO.setSegundoApellido(personaDTO.getSegundoApellido());
                        exclusionCarteraDTO.setRazonSocial(personaDTO.getRazonSocial());
                    }
                }
            }

            if (lstExclusionesCartera == null || lstExclusionesCartera.isEmpty()) {
                lstExclusionesCartera = null;
            }

            logger.debug(
                "Fin método buscarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
            return lstExclusionesCartera;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en buscarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * ejecutarProcesoAutomaticoCartera(com.asopagos.rest.security.dto.
     * UserDTO)
     */
    @Override
    public void ejecutarProcesoAutomaticoCartera(UserDTO userDTO) {
        try {
            logger.debug("Inicia ejecutarProcesoAutomaticoCartera");
            crearRegistroCartera();
            logger.debug("Finaliza ejecutarProcesoAutomaticoCartera");
        } catch (Exception e) {
            logger.error("Error en servicio ejecutarProcesoAutomaticoCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * ejecutarProcesoAutomaticoGestionCobro(com.asopagos.enumeraciones.
     * cartera.TipoAccionCobroEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum accionCobro, UserDTO userDTO) {
        try {
            logger.debug("Inicia ejecutarProcesoAutomaticoGestionCobro :: " + accionCobro);
            asignarAccionesCobro(accionCobro, userDTO);
            logger.debug("Finaliza ejecutarProcesoAutomaticoGestionCobro :: " + accionCobro);
        } catch (Exception e) {
            logger.error("Error en servicio ejecutarProcesoAutomaticoGestionCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* Inicio proceso 22 */
    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * crearNuevoCicloGestionManual(java.lang.Long, java.lang.Long,
     * com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum, java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public CicloCarteraModeloDTO crearNuevoCicloGestionManual(Long fechaInicio, Long fechaFin,
                                                              TipoLineaCobroEnum lineaCobro, List<SimulacionDTO> aportantes, UserDTO userDTO) {
        try {
            logger.debug("Inicio de método crearNuevoCicloGestionManual");

            // Armar ciclo cartera y todos sus aportantes
            CicloCarteraModeloDTO cicloCartera = new CicloCarteraModeloDTO();
            cicloCartera.setFechaInicio(fechaInicio);
            cicloCartera.setFechaFin(fechaFin);
            cicloCartera.setFechaCreacion(new Date().getTime());
            cicloCartera.setEstadoCicloFiscalizacion(EstadoCicloCarteraEnum.ACTIVO);
            cicloCartera.setTipoCiclo(TipoCicloEnum.GESTION_MANUAL);
            List<CicloAportanteModeloDTO> cicloAportantes = new ArrayList<>();

            /* Se agregan los aportantes */
            for (SimulacionDTO simulation : aportantes) {
                CicloAportanteModeloDTO cicloAportanteModeloDTO = new CicloAportanteModeloDTO();
                PersonaModeloDTO persona = consultarDatosPersona(simulation.getTipoIdentificacion(),
                    simulation.getNumeroIdentificacion());

                if (simulation.getUsuarioAnalista() == null || simulation.getUsuarioAnalista().isEmpty()) {
                    logger.error(
                        "Error en metodo crearNuevoCicloFiscalizacion(Long fechaInicio, Long fechaFin, List<SimulacionDTO> simulacionDTOs,UserDTO user)");
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                } else {
                    cicloAportanteModeloDTO.setIdPersona(persona.getIdPersona());
                    cicloAportanteModeloDTO.setTipoSolicitanteMovimientoAporteEnum(simulation.getTipoAportante());
                    cicloAportanteModeloDTO.setAnalista(simulation.getUsuarioAsignacion());
                    cicloAportantes.add(cicloAportanteModeloDTO);
                }
            }

            cicloCartera.setAportantes(cicloAportantes);
            cicloCartera = guardarCicloFiscalizacion(cicloCartera);

            for (CicloAportanteModeloDTO aportanteModeloDTO : cicloCartera.getAportantes()) {
                SolicitudGestionCobroManualModeloDTO solicitudGestionManualDTO = construirSolicitudGestionCobroManualModeloDTO(
                    aportanteModeloDTO.getIdCicloAportante(), lineaCobro, userDTO);
                Long idSolicitudGestionManual = guardarSolicitudGestionCobroManual(solicitudGestionManualDTO);

                String numeroRadicacion = generarNumeroRadicado(idSolicitudGestionManual,
                    userDTO.getSedeCajaCompensacion());
                Map<String, Object> params = new HashMap<>();
                params.put(ID_SOLICITUD, idSolicitudGestionManual);
                params.put(NUMERO_RADICADO, numeroRadicacion);
                params.put(ANALISTA_CARTERA, aportanteModeloDTO.getAnalista());

                /* Se inicia el proceso */
                Long idProceso = iniciarProceso(ProcesoEnum.GESTION_COBRO_MANUAL, params, Boolean.FALSE);
                solicitudGestionManualDTO = consultarSolicitudGestionCobroManual(numeroRadicacion, null);
                solicitudGestionManualDTO.setIdInstanciaProceso(idProceso.toString());
                solicitudGestionManualDTO.setDestinatario(aportanteModeloDTO.getAnalista());
                solicitudGestionManualDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
                guardarSolicitudGestionCobroManual(solicitudGestionManualDTO);
            }

            logger.debug("Finaliza crearNuevoCicloGestionManual(Long fechaInicio, Long fechaFin, List<SimulacionDTO>)");
            return cicloCartera;
        } catch (Exception e) {
            logger.error("Ocurrió un error en crearNuevoCicloGestionManual", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarParametrizacionCriterioTemporal(com.asopagos.enumeraciones.
     * cartera.TipoLineaCobroEnum, java.lang.Boolean)
     */
    @Override
    public ParametrizacionCriteriosGestionCobroModeloDTO consultarParametrizacionCriterioTemporal(
        TipoLineaCobroEnum lineaCobro, TipoGestionCarteraEnum accion) {
        try {
            logger.debug(
                "Inicio de método consultarParametrizacionCriterioTemporal(TipoLineaCobroEnum lineaCobro,Boolean automatica)");
            ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionCriterio = null;
            CriteriosParametrizacionTemporalDTO parametrizacion = consultarDatoTemporalParametrizacion(
                ParametrizacionEnum.GESTION_COBRO);
            if (parametrizacion == null || parametrizacion.getParametrizaciones() == null) {
                return null;
            }
            for (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionCriterios : parametrizacion
                .getParametrizaciones()) {
                /* si la linea de cobro coincide con la que se recorre */
                if ((parametrizacionCriterios.getLineaCobro().equals(lineaCobro)
                    && parametrizacionCriterios.getAccion().equals(accion))) {
                    parametrizacionCriterio = parametrizacionCriterios;
                }
            }
            logger.debug(
                "Fin de método consultarParametrizacionCriterioTemporal(TipoLineaCobroEnum lineaCobro,Boolean automatica)");
            return parametrizacionCriterio;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en consultarParametrizacionCriterioTemporal(TipoLineaCobroEnum lineaCobro,Boolean automatica)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * guardarParametrizacionCriterioTemporal(com.asopagos.dto.modelo.
     * ParametrizacionCriteriosGestionCobroModeloDTO)
     */
    @Override
    public void guardarParametrizacionCriterioTemporal(
        ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO) {
        try {
            logger.debug(
                "Inicio de método guardarParametrizacionCriterioTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)");
            List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizaciones = new ArrayList<>();
            boolean existePametrizacionCritero = false;
            CriteriosParametrizacionTemporalDTO criterioParametrizacion = consultarDatoTemporalParametrizacion(
                ParametrizacionEnum.GESTION_COBRO);
            if (criterioParametrizacion != null) {
                if (criterioParametrizacion.getParametrizaciones() != null) {
                    for (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionExistente : criterioParametrizacion
                        .getParametrizaciones()) {
                        /* si la linea de cobro coincide con la que se recorre */
                        if (parametrizacionExistente.getLineaCobro().equals(parametrizacionDTO.getLineaCobro())
                            && parametrizacionExistente.getAccion().equals(parametrizacionDTO.getAccion())) {
                            parametrizaciones.add(parametrizacionDTO);
                            existePametrizacionCritero = true;
                        } else {
                            parametrizaciones.add(parametrizacionExistente);
                        }
                    }
                }
                if (!existePametrizacionCritero) {
                    parametrizaciones.add(parametrizacionDTO);
                }
                criterioParametrizacion.setParametrizaciones(parametrizaciones);
            }
            guardarDatoTemporalParametrizacion(criterioParametrizacion, ParametrizacionEnum.GESTION_COBRO);
            logger.debug(
                "Fin de método guardarParametrizacionCriterioTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en guardarParametrizacionCriterioTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarCriteriosParametrizacion()
     */
    @Override
    public CriteriosParametrizacionTemporalDTO consultarCriteriosParametrizacion() {
        try {
            logger.debug("Inicio de método consultarCriteriosParametrizacion");
            /* se consulta la información temporal */
            CriteriosParametrizacionTemporalDTO criterioParametrizacion = consultarDatoTemporalParametrizacion(
                ParametrizacionEnum.GESTION_COBRO);
            /* se elimina las parametrizaciones para que solo se envíe lo del encabezado. */
            if (criterioParametrizacion != null && criterioParametrizacion.getParametrizaciones() != null) {
                criterioParametrizacion.getParametrizaciones().clear();
            }
            logger.debug("Fin de método consultarCriteriosParametrizacion");
            return criterioParametrizacion;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarCriteriosParametrizacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#finalizarParametrizacionCriteriosGestionCobro()
     */
    @Override
    public Integer finalizarParametrizacionCriteriosGestionCobro() {
        try {
            logger.debug("Inicio de método finalizarParametrizacionCriteriosGestionCobro");

            /*
             * Valores que puede tomar la respuesta
             * Cero: 0 - Cumple las validaciones
             * Uno: 1 - Tiene al menos una línea de cobro sin parametrizar
             * Dos: 2 - Tiene aportantes asignados a la LC1
             */
            MetodoAccionCobroEnum metodoActivo = consultarMetodoCriterioGestionCobro();
            Integer respuesta = 0;
            CriteriosParametrizacionTemporalDTO criterioParametrizacion = consultarDatoTemporalParametrizacion(
                ParametrizacionEnum.GESTION_COBRO);
            if (criterioParametrizacion != null) {
                // Se validan las condiciones para las líneas de cobro
                respuesta = validarCondicionParametrizacionLC(criterioParametrizacion);
                if (respuesta != 0) {
                    return respuesta;
                }
                // Mantis 0236016: Se verifica el método de parametrización anterior es igual al
                // nuevo a guardar
                if (metodoActivo != null && !criterioParametrizacion.getMetodo().equals(metodoActivo)) {
                    // Se podrá cambiar de método solo si la CCF no tiene aportantes asignados a la
                    // LC1.
                    if (consultarCondicionAportanteCarteraLCUNO(TipoLineaCobroEnum.LC1,
                        EstadoOperacionCarteraEnum.VIGENTE)) {
                        respuesta = 0;
                        // Solucion a mantis 238238
                        actualizarActivacionMetodoGestionCobro(metodoActivo);
                    } else {
                        respuesta = 2;
                    }
                }
                // Si la respuesta es 0 (correcta) se puede proceder a guardar los criterios de
                // gestión de cobro
                if (respuesta == 0) {
                    guardarCriteriosGestionCobro(criterioParametrizacion.getParametrizaciones(),
                        criterioParametrizacion.getMetodo());
                }
                return respuesta;
            }
            respuesta = 1;
            logger.debug("Fin de método finalizarParametrizacionCriteriosGestionCobro");
            return respuesta;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en finalizarParametrizacionCriteriosGestionCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * traspasarCartera(com.asopagos.enumeraciones.aportes.
     * TipoSolicitanteMovimientoAporteEnum, java.lang.String, java.lang.String)
     */
    @Override
    public void traspasarCartera(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                 TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, UserDTO userDTO) {
        try {
            logger.debug("Inicio de método traspasarCartera");
            List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs = new ArrayList<>();

            PersonaModeloDTO personaModeloDTO = consultarDatosPersona(tipoIdentificacion, numeroIdentificacion);

            if (personaModeloDTO != null && personaModeloDTO.getIdPersona() != null) {
                // Registro de bitácora
                PersonaModeloDTO personaDTO = consultarDatosPersona(tipoIdentificacion, numeroIdentificacion);
                almacenarBitacoraCartera(null, null, TipoActividadBitacoraEnum.TRASPASO_DEUDA_ANTIGUA,
                    MedioCarteraEnum.PERSONAL, ResultadoBitacoraCarteraEnum.TRASPASO, null,
                    personaDTO.getIdPersona(), tipoSolicitante, null, null, null);
            }

            /* Se crea instancia del objeto */
            DesafiliacionAportanteDTO aportanteTraspaso = new DesafiliacionAportanteDTO();
            aportanteTraspaso.setTipoIdentificacion(tipoIdentificacion);
            aportanteTraspaso.setNumeroIdentificacion(numeroIdentificacion);
            aportanteTraspaso.setTipoSolicitante(tipoSolicitante);

            switch (tipoSolicitante) {
                case EMPLEADOR:
                    aportanteTraspaso.setTipoLineaCobro(TipoLineaCobroEnum.LC1);
                    break;
                case INDEPENDIENTE:
                    aportanteTraspaso.setTipoLineaCobro(TipoLineaCobroEnum.LC4);
                    break;
                case PENSIONADO:
                    aportanteTraspaso.setTipoLineaCobro(TipoLineaCobroEnum.LC5);
                    break;
                default:
                    break;
            }

            /* Se agrega al aportante que sera traspasodo a deuda antigua */
            desafiliacionAportanteDTOs.add(aportanteTraspaso);
            /* Se llama al servicio para actualizar las lineas de cobro del aportante */
            actualizarLineaCobroDesafiliacion(desafiliacionAportanteDTOs, userDTO.getNombreUsuario());
            logger.debug("Fin de método traspasarCartera");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en traspasarCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarAportantesDeudaAntigua(com.asopagos.dto.cartera.
     * FiltrosGestionCobroManualDTO, javax.ws.rs.core.UriInfo,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<AportanteGestionManualDTO> consultarAportantesDeudaAntigua(FiltrosGestionCobroManualDTO filtros,
                                                                           UriInfo uri, HttpServletResponse response) {
        try {
            logger.debug("Inicio de método consultarAportantesDeudaAntigua");

            FiltrosTrasladoDeudaAntiguaPersonaDTO filtrosTraslado = new FiltrosTrasladoDeudaAntiguaPersonaDTO();

            if (uri != null && uri.getQueryParameters() != null) {
                for (Entry<String, List<String>> e : uri.getQueryParameters().entrySet()) {
                    filtrosTraslado.getParams().put(e.getKey(), e.getValue());
                }
            }

            if (filtros.getPrimerNombre() != null || filtros.getSegundoNombre() != null
                || filtros.getPrimerApellido() != null || filtros.getSegundoApellido() != null
                || filtros.getRazonSocial() != null || filtros.getTipoIdentificacion() != null
                || filtros.getNumeroIdentificacion() != null) {
                List<PersonaDTO> personas = buscarPersonasSinDetalle(filtros.getTipoIdentificacion(),
                    filtros.getNumeroIdentificacion(), filtros.getPrimerNombre(), filtros.getPrimerApellido(),
                    filtros.getSegundoNombre(), filtros.getSegundoApellido(), filtros.getRazonSocial());

                List<Long> idPersonas = new ArrayList<>();

                if (personas != null) {
                    for (PersonaDTO personaDTO : personas) {
                        idPersonas.add(personaDTO.getIdPersona());
                    }
                } else {
                    return null;
                }

                filtrosTraslado.setIdPersonas(idPersonas);
                filtrosTraslado.setTipoSolicitante(filtros.getTipoSolicitante());
            } else {
                filtrosTraslado.setTipoSolicitante(filtros.getTipoSolicitante());
            }

            AportantesTraspasoDeudaDTO aportantesTraspasoDeuda = consultarAportantesTraspasoDeudaAntigua(
                filtrosTraslado);

            if (aportantesTraspasoDeuda.getTotalRecords() != null && response != null) {
                response.addHeader("totalRecords", aportantesTraspasoDeuda.getTotalRecords().toString());
            }

            logger.debug("Fin de método consultarAportantesDeudaAntigua");
            return aportantesTraspasoDeuda.getAportantes();
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesDeudaAntigua", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /* Fin proceso 22 */
    /* Inicio proceso 223 */

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#confirmarPrimeraRemision(com.asopagos.cartera.composite.dto
     * .
     * RegistroRemisionAportantesDTO)
     */
    @Override
    public void confirmarPrimeraRemision(RegistroRemisionAportantesDTO registroRemision, HttpHeaders header,
                                         UserDTO userDTO) {
        logger.debug("Inicio de método confirmarPrimeraRemision(RegistroRemisionAportantesDTO)");

        String token = header.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Se obtine solo la parte asociada al token (del header proviene un string de
        // tipo [tipoAtorizacion][token])
        registroRemision.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));
        // Se setea el contexto del usuario quien realiza la tarea
        registroRemision.setUserDTO(userDTO);

        carteraAsincronoCompositeService.confirmarPrimeraRemisionAsync(registroRemision);
        logger.debug("Fin de método confirmarPrimeraRemision");
    }

    /**
     * Método que obtiene el número de operación de cartera a partir de un
     * identificador del periodo en mora
     *
     * @param idCartera Identificador de cartera / periodo en mora
     * @return El número de operación
     */
    private Long consultarNumeroOperacionCartera(Long idCartera) {
        logger.debug("Inicia método consultarNumeroOperacionCartera");
        ConsultarNumeroOperacionCartera service = new ConsultarNumeroOperacionCartera(idCartera);
        service.execute();
        logger.debug("Finaliza método consultarNumeroOperacionCartera");
        return service.getResult();
    }

    /**
     * Método que consulta el registro de cartera asociado a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param tipoSolicitante      Tipo de aportante
     * @param tipoLineaCobro       Línea de cobro
     * @param periodoMillis        Periodo de deuda a consulta
     * @return La información del registro en cartera
     */
    private CarteraModeloDTO obtenerInformacionCartera(TipoIdentificacionEnum tipoIdentificacion,
                                                       String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                       TipoLineaCobroEnum tipoLineaCobro, Long periodoMillis) {
        try {
            logger.debug("Inicio de método obtenerInformacionCartera");
            List<CarteraModeloDTO> listaCartera = consultarPeriodosAportanteLineaCobro(tipoIdentificacion,
                numeroIdentificacion, tipoSolicitante, tipoLineaCobro);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

            if (periodoMillis != null) {
                String periodo = dateFormat.format(new Date(periodoMillis));

                for (CarteraModeloDTO registro : listaCartera) {
                    if (periodo.equals(dateFormat.format(new Date(registro.getPeriodoDeuda())))) {
                        logger.debug("Fin de método obtenerInformacionCartera");
                        return registro;
                    }
                }
            }

            if (!listaCartera.isEmpty()) {
                return listaCartera.get(0);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerInformacionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return null;
    }

    /**
     * Método que prepara el registro de bitácora a ser almacenado
     *
     * @param idBitacoraCartera Identificador del registro en bitácora
     * @param fecha             Fecha de registro
     * @param actividad         Actividad
     * @param medio             Medio o canal
     * @param resultado         Resultado
     * @param usuario           Usuario que registró
     * @param idPersona         Identificador de la persona aportante
     * @param tipoSolicitante   Tipo de aportante
     * @param documentosSoporte Lista de documentos a agregar en la traza
     * @param numeroOperacion   Número de operación
     */
    private void almacenarBitacoraCartera(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad,
                                          MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona,
                                          TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<DocumentoSoporteModeloDTO> documentosSoporte,
                                          String numeroOperacion, String comentarios) {
        try {
            logger.debug("Inicio de método almacenarBitacoraCartera");
            logger.info("**__** idBitacoraCartera idBitacoraCartera: " + idBitacoraCartera + " numeroOperacion: "
                + numeroOperacion + "idPersona: " + idPersona + " actividad: " + actividad + " resultado : "
                + resultado + " Comentarios: " + comentarios);
            BitacoraCarteraDTO bitacora = crearBitacoraCarteraDTO(idBitacoraCartera, fecha, actividad, medio, resultado,
                usuario, idPersona, tipoSolicitante, documentosSoporte, numeroOperacion, null, comentarios);

            guardarBitacoraCartera(bitacora);
            logger.debug("Fin de método almacenarBitacoraCartera");
        } catch (Exception e) {
            logger.error("Ocurrió un error en almacenarBitacoraCartera", e);
        }
    }

    /**
     * Método que prepara el registro de bitácora a ser almacenado
     *
     * @param idBitacoraCartera Identificador del registro en bitácora
     * @param fecha             Fecha de registro
     * @param actividad         Actividad
     * @param medio             Medio o canal
     * @param resultado         Resultado
     * @param usuario           Usuario que registró
     * @param idPersona         Identificador de la persona aportante
     * @param tipoSolicitante   Tipo de aportante
     * @param documentosSoporte Lista de documentos a agregar en la traza
     * @param numeroOperacion   Número de operación
     * @param idCartera         Número identificador de la cartera
     */
    private BitacoraCarteraDTO crearBitacoraCarteraDTO(Long idBitacoraCartera, Long fecha,
                                                       TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado,
                                                       String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                       List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, Long idCartera,
                                                       String comentarios) {
        logger.info("se inicia el crear bitacora cartea ");
        BitacoraCarteraDTO bitacora = new BitacoraCarteraDTO();
        bitacora.setIdBitacoraCartera(idBitacoraCartera);
        bitacora.setFecha(fecha);
        bitacora.setActividad(actividad);
        bitacora.setMedio(medio);
        bitacora.setResultado(resultado);
        bitacora.setUsuario(usuario);
        bitacora.setIdPersona(idPersona);
        bitacora.setTipoSolicitante(tipoSolicitante);
        bitacora.setDocumentosSoporte(documentosSoporte);
        bitacora.setNumeroOperacion(numeroOperacion);
        bitacora.setIdCartera(idCartera);
        logger.info("fin de el crear bitacora cartea ");
        return bitacora;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * actualizarGestionCobro(java.lang.String, java.lang.Long,
     * java.lang.Boolean)
     */
    @Override
    public void actualizarGestionCobro(String numeroRadicacion, Long idTarea, Boolean actualizacionEfectiva) {
        try {
            logger.debug(
                "Inicio de método actualizarGestionCobro(String numeroRadicacion, Long idTarea, Boolean actualizacionEfectiva)");
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionFisicaDTO = consultarSolicitudGestionCobro(
                numeroRadicacion);

            if (solicitudGestionFisicaDTO == null) {
                SolicitudGestionCobroElectronicoModeloDTO solicitudGestionElectronicoDTO = consultarSolicitudGestionCobroElectronico(
                    numeroRadicacion);

                if (solicitudGestionElectronicoDTO != null) {
                    if (actualizacionEfectiva) {
                        actualizarEstadoSolicitudGestionCobroElectronico(numeroRadicacion,
                            EstadoSolicitudGestionCobroEnum.ACTUALIZACION_EXITOSA);

                        if (verificarEnvioComunicadoElectronico(numeroRadicacion,
                            solicitudGestionElectronicoDTO.getTipoAccionCobro(),
                            solicitudGestionElectronicoDTO.getIdCartera())) {
                            // Actualizacion del estado de la solicitud de gestion de cobro
                            actualizarEstadoSolicitudGestionCobroElectronico(numeroRadicacion,
                                EstadoSolicitudGestionCobroEnum.PENDIENTE_REGISTRAR_SEGUNDA_REMISION);
                        } else {
                            // Actualizacion del estado de la solicitud de gestion de cobro
                            actualizarEstadoSolicitudGestionCobroElectronico(numeroRadicacion,
                                EstadoSolicitudGestionCobroEnum.CERRADA);
                        }
                    }
                } else {
                    // Actualizacion del estado de la solicitud de gestion de cobro
                    actualizarEstadoSolicitudGestionCobroElectronico(numeroRadicacion,
                        EstadoSolicitudGestionCobroEnum.ACTUALIZACION_NO_EXITOSA);
                    actualizarEstadoSolicitudGestionCobroElectronico(numeroRadicacion,
                        EstadoSolicitudGestionCobroEnum.CERRADA);
                }
                /* Se termina tarea */
                terminarTarea(idTarea, null);
            } else {
                DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroDTO = consultarDetallePorSolicitud(
                    solicitudGestionFisicaDTO.getIdSolicitudGestionCobroFisico());

                /* la solicitud es fisica */
                if (actualizacionEfectiva) {
                    /* si fue efectivo */
                    // Actualizacion del estado de la solicitud de gestion de cobro
                    actualizarEstadoSolicitudGestionCobro(numeroRadicacion,
                        EstadoSolicitudGestionCobroEnum.ACTUALIZACION_EXITOSA);
                    // Actualizacion del estado del detalle de gestion de cobro
                    detalleSolicitudGestionCobroDTO
                        .setEstadoSolicitud(EstadoTareaGestionCobroEnum.ACTUALIZACION_EXITOSA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroDTO,
                        solicitudGestionFisicaDTO.getIdSolicitud());
                    Boolean registrarSegundaRemision = verificarEnvioComunicadoFisico(numeroRadicacion,
                        solicitudGestionFisicaDTO.getTipoAccionCobro(),
                        detalleSolicitudGestionCobroDTO.getIdCartera());

                    if (registrarSegundaRemision) {

                        // Actualizacion del estado de la solicitud de gestion de cobro
                        actualizarEstadoSolicitudGestionCobro(numeroRadicacion,
                            EstadoSolicitudGestionCobroEnum.PENDIENTE_REGISTRAR_SEGUNDA_REMISION);

                        // Actualizacion del estado del detalle de gestion de cobro
                        detalleSolicitudGestionCobroDTO
                            .setEstadoSolicitud(EstadoTareaGestionCobroEnum.PENDIENTE_REGISTRAR_SEGUNDA_REMISION);
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroDTO,
                            solicitudGestionFisicaDTO.getIdSolicitud());

                        /* Se crean los parametros */
                        Map<String, Object> params = new HashMap<>();

                        /* Se inicial proceso para obtener el usuario que gestiono desde la HU 170 */
                        SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroFisicoModeloDTO = consultarSolicitudGestionCobroPorId(
                            detalleSolicitudGestionCobroDTO.getIdPrimeraSolicitudRemision());
                        params.put(ANALISTA_CARTERA, solicitudGestionCobroFisicoModeloDTO.getDestinatario());
                        params.put(ACTUALIZACION_EFECTIVA, actualizacionEfectiva);
                        terminarTarea(idTarea, params);
                    } else {
                        // Actualizacion del estado de la solicitud de gestion de cobro
                        actualizarEstadoSolicitudGestionCobro(numeroRadicacion,
                            EstadoSolicitudGestionCobroEnum.CERRADA);

                        /* Se crean los parametros */
                        Map<String, Object> params = new HashMap<>();

                        // Actualizacion del estado del detalle de gestion de cobro
                        detalleSolicitudGestionCobroDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroDTO,
                            solicitudGestionFisicaDTO.getIdSolicitud());
                        params.put(ACTUALIZACION_EFECTIVA, Boolean.FALSE);
                        terminarTarea(idTarea, params);
                    }
                } else {
                    // Actualizacion del estado de la solicitud de gestion de cobro
                    actualizarEstadoSolicitudGestionCobro(numeroRadicacion,
                        EstadoSolicitudGestionCobroEnum.ACTUALIZACION_NO_EXITOSA);
                    actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.CERRADA);

                    // Actualizacion del estado del detalle de gestion de cobro
                    detalleSolicitudGestionCobroDTO
                        .setEstadoSolicitud(EstadoTareaGestionCobroEnum.ACTUALIZACION_NO_EXITOSA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroDTO,
                        solicitudGestionFisicaDTO.getIdSolicitud());

                    // Actualizacion del estado del detalle de gestion de cobro
                    detalleSolicitudGestionCobroDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroDTO,
                        solicitudGestionFisicaDTO.getIdSolicitud());
                    /* Se crean los parametros */
                    Map<String, Object> params = new HashMap<>();
                    params.put(ACTUALIZACION_EFECTIVA, actualizacionEfectiva);
                    terminarTarea(idTarea, params);
                }
            }

            logger.debug(
                "Finalizacion de método actualizarGestionCobro(String numeroRadicacion, Long idTarea, Boolean actualizacionEfectiva)");
        } catch (Exception e) {
            logger.error("Ocurrió un error en actualizarGestionCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * gestionarSegundoIntento(java.lang.String,
     * com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum,
     * com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public EstadoSolicitudGestionCobroEnum gestionarSegundoIntento(String numeroRadicacion,
                                                                   TipoAccionCobroEnum accionCobro, AportanteRemisionComunicadoDTO aportanteRemisionDTO, UserDTO userDTO) {
        try {
            UsuarioDTO userEquitativo = null;
            logger.debug(
                "Inicio de método gestionarSegundoIntento(AportanteRemisionComunicadoDTO aportanteRemisionDTO)");

            /* Se ejecutan validaciones de HU 175 seccion 7.2 */
            /* Se consulta los usuarios dependiendo del tipo de aportante */
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportanteRemisionDTO.getTipoAportante())) {
                List<UsuarioDTO> usuariosBackEmpleador = obtenerMiembrosGrupo(BAC_ACT_EMP,
                    userDTO.getSedeCajaCompensacion(), EstadoUsuarioEnum.ACTIVO);
                userEquitativo = obtenerUsuarioConsecutivo(usuariosBackEmpleador,
                    consultarUltimoDestinatarioSolicitud());
            } else {
                List<UsuarioDTO> usuariosBackPersona = obtenerMiembrosGrupo(BAC_ACT_PER,
                    userDTO.getSedeCajaCompensacion(), EstadoUsuarioEnum.ACTIVO);
                userEquitativo = obtenerUsuarioConsecutivo(usuariosBackPersona, consultarUltimoDestinatarioSolicitud());
            }

            SolicitudGestionCobroFisicoModeloDTO solicitudAnterior = consultarSolicitudGestionCobro(numeroRadicacion);

            /* se crea una nueva solicitud de gestión de cobro */
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO = transformarGestionCobroSegundoIntento(
                aportanteRemisionDTO, userDTO, userEquitativo.getNombreUsuario(),
                solicitudAnterior.getTipoTransaccion(), solicitudAnterior.getTipoAccionCobro());

            /* Se crea filtro del detalle */
            FiltroDetalleSolicitudGestionCobroDTO filtroDetalle = new FiltroDetalleSolicitudGestionCobroDTO();
            filtroDetalle.setNumeroIdentificacion(aportanteRemisionDTO.getNumeroIdentificacion());
            filtroDetalle.setTipoIdentificacion(aportanteRemisionDTO.getTipoIdentificacion());
            filtroDetalle.setNumeroRadicacion(numeroRadicacion);

            /* se agrega el filtro a la lista */
            List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestionCobroDTO = new ArrayList<>();
            filtroDetalleSolicitudGestionCobroDTO.add(filtroDetalle);

            /* Se consulta el detalle de la solicitud de gestion de cobro */
            List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroDto = consultarDetalleSolicitudGestionCobro(
                filtroDetalleSolicitudGestionCobroDTO);
            DetalleSolicitudGestionCobroModeloDTO detalleIndividual = detalleSolicitudGestionCobroDto.get(0);
            detalleIndividual.setFechaPrimeraEntrega(aportanteRemisionDTO.getFechaPrimeraEntrega());
            detalleIndividual.setObservacionPrimeraEntrega(aportanteRemisionDTO.getObservacionPrimeraEntrega());
            /* Se arma el documento */

            String nombreArchivo = "DOCUMENTO_REGISTRO_PRIMERA_REMISION_COMUNICADO";
            String descipcionArchivo = "Se realiza registro de primera remisión del comunicado";
            DocumentoSoporteModeloDTO documentoDTO = construirDocumentoSoporte(
                detalleIndividual.getDocumentoPrimeraRemision(),
                aportanteRemisionDTO.getIdDocumentoPrimeraEntrega(), nombreArchivo, descipcionArchivo);

            /* Se settea el documento al detalle */
            detalleIndividual.setDocumentoPrimeraRemision(documentoDTO);

            /* Se settea el estado del resultado de primera remision */
            detalleIndividual.setResultadoPrimeraEntrega(aportanteRemisionDTO.getResultadoPrimeraEntrega());
            detalleIndividual.setEstadoSolicitud(EstadoTareaGestionCobroEnum.REMISION_COMUNICADO_NO_EXITOSA);

            /* Se guarda el DetalleSolicitudGestionCobro */
            detalleIndividual = guardarDetalleSolicitudGestionCobro(detalleIndividual,
                solicitudAnterior.getIdSolicitud());

            /* Se obtiene el id de Solicitud */
            solicitudGestionCobroDTO = guardarSolicitudGestionCobro(solicitudGestionCobroDTO);
            Long idSolicitud = solicitudGestionCobroDTO.getIdSolicitud();
            String numeroRad = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());

            /* Se actualiza el detalle nuevamente */
            /* Se consulta la solicitud nuevamente para enviarlo al detalle */
            solicitudGestionCobroDTO = consultarSolicitudGestionCobro(numeroRad);
            detalleIndividual.setEstadoSolicitud(EstadoTareaGestionCobroEnum.PENDIENTE_ACTUALIZACION_DATOS);
            detalleIndividual
                .setIdSegundaSolicitudRemision(solicitudGestionCobroDTO.getIdSolicitudGestionCobroFisico());
            detalleIndividual = guardarDetalleSolicitudGestionCobro(detalleIndividual,
                solicitudAnterior.getIdSolicitud());

            /* Se inicia proceso */
            Map<String, Object> params = new HashMap<>();

            params.put(ID_SOLICITUD, idSolicitud);
            params.put(NUMERO_RADICADO, numeroRad);
            params.put(BACK_ACTUALIZACION, userEquitativo.getNombreUsuario());
            String[] split = solicitudAnterior.getTipoAccionCobro().getDescripcion().split(" ");
            params.put(ConstanteCartera.ACCION_COBRO, split[3]);
            Long idProceso = iniciarProceso(ProcesoEnum.GESTION_CARTERA_FISICA_DETALLADA, params, Boolean.FALSE);
            solicitudGestionCobroDTO.setIdInstanciaProceso(idProceso.toString());
            solicitudGestionCobroDTO = guardarSolicitudGestionCobro(solicitudGestionCobroDTO);

            logger.debug("Fin de método gestionarSegundoIntento(AportanteRemisionComunicadoDTO aportanteRemisionDTO)");
            return EstadoSolicitudGestionCobroEnum.PENDIENTE_ACTUALIZACION_DATOS;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error gestionarSegundoIntento(AportanteRemisionComunicadoDTO aportanteRemisionDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * registrarResultadosPrimeraRemision(java.lang.String,
     * java.lang.Long, java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void registrarResultadosPrimeraRemision(String numeroRadicacion, Long idTarea,
                                                   List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO, TipoAccionCobroEnum accionCobro, UserDTO userDTO,
                                                   HttpHeaders header) {
        logger.debug(
            "Inicio de método registrarResultadosPrimeraRemision(String, List<AportanteRemisionComunicadoDTO>, UserDTO, HttpHeaders)");

        String token = header.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Se obtine solo la parte asociada al token (del header proviene un string de
        // tipo [tipoAtorizacion][token])
        userDTO.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));
        carteraAsincronoCompositeService.registrarResultadosPrimeraRemisionAsync(numeroRadicacion, idTarea,
            aportanteRemisionDTO, accionCobro, userDTO);
        logger.debug(
            "Fin de método registrarResultadosPrimeraRemision(String, List<AportanteRemisionComunicadoDTO>, UserDTO, HttpHeaders)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#guardarParametrizacionGestionCobroTemporal(com.asopagos.dto.cartera.CriteriosParametrizacionTemporalDTO)
     */
    @Override
    public void guardarParametrizacionGestionCobroTemporal(
        CriteriosParametrizacionTemporalDTO criterioParametrizacionDTO) {
        try {
            logger.debug(
                "Inicio de método guardarParametrizacionGestionCobroTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)");
            CriteriosParametrizacionTemporalDTO criterioParametrizacionExistente = consultarDatoTemporalParametrizacion(
                ParametrizacionEnum.GESTION_COBRO);
            if (criterioParametrizacionExistente != null) {

                criterioParametrizacionExistente.setMetodoAnterior(consultarMetodoActivoLC1());
                criterioParametrizacionExistente = criterioParametrizacionExistente
                    .copyDTOToDTO(criterioParametrizacionExistente, criterioParametrizacionDTO);
                guardarDatoTemporalParametrizacion(criterioParametrizacionExistente, ParametrizacionEnum.GESTION_COBRO);
            } else {
                guardarDatoTemporalParametrizacion(criterioParametrizacionDTO, ParametrizacionEnum.GESTION_COBRO);
            }
            logger.debug(
                "Fin de método guardarParametrizacionGestionCobroTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en guardarParametrizacionGestionCobroTemporal(ParametrizacionCriteriosGestionCobroModeloDTO)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * guardarSegundaRemision(com.asopagos.dto.cartera.
     * RegistroRemisionAportantesDTO)
     */
    @Override
    public void guardarSegundaRemision(RegistroRemisionAportantesDTO registroRemision) {
        try {
            logger.debug("Inicio de método guardarSegundaRemision(RegistroRemisionAportantesDTO)");

            /* Se consulta la solicitud */
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroFisicoModeloDTO = consultarSolicitudGestionCobro(
                registroRemision.getNumeroRadicacion());
            String nombreArchivo = "DOCUMENTO_SEGUNDA_REMISION_COMUNICADO";
            String descipcionArchivo = "Se realiza segunda remision del comunicado";
            DocumentoSoporteModeloDTO documentoSoporte = construirDocumentoSoporte(
                solicitudGestionCobroFisicoModeloDTO.getDocumentoSoporte(), registroRemision.getIdDocumento(),
                nombreArchivo, descipcionArchivo);
            solicitudGestionCobroFisicoModeloDTO.setDocumentoSoporte(documentoSoporte);

            /* se consulta el servicio de silvio para obtener el detalle */
            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO = consultarDetallePorSolicitud(
                solicitudGestionCobroFisicoModeloDTO.getIdSolicitudGestionCobroFisico());

            /* se empieza a setear el detalle */
            detalleSolicitudGestionCobroModeloDTO
                .setEnviarSegundaRemision(registroRemision.getAportantes().get(0).getEnviar());
            detalleSolicitudGestionCobroModeloDTO
                .setObservacionSegundaRemision(registroRemision.getAportantes().get(0).getObservacion());
            guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());

            /* se emipieza a setear la solicitud */
            if (registroRemision.getFechaRemision() != null && registroRemision.getHoraRemision() != null) {
                Date fechaHora = CalendarUtils.concatenarFechaHora(registroRemision.getFechaRemision(),
                    registroRemision.getHoraRemision());
                solicitudGestionCobroFisicoModeloDTO.setFechaRemision(fechaHora.getTime());
            }

            solicitudGestionCobroFisicoModeloDTO.setObservacionRemision(registroRemision.getObservaciones());
            solicitudGestionCobroFisicoModeloDTO = guardarSolicitudGestionCobro(solicitudGestionCobroFisicoModeloDTO);

            if (!registroRemision.getGuardar()) {
                /* Se inicia proceso */
                Map<String, Object> params = new HashMap<>();
                params.put(COMUNICADO_ENVIADO, detalleSolicitudGestionCobroModeloDTO.getEnviarSegundaRemision());
                ResultadoBitacoraCarteraEnum resultado = ResultadoBitacoraCarteraEnum.ENVIADO;

                /* Se ejecutan validaciones de Hu 175 seccion 9.2 */
                if (!detalleSolicitudGestionCobroModeloDTO.getEnviarSegundaRemision()) {
                    /*
                     * Se realiza el cambio de estados respectivos cuando la opcion enviar es false
                     * para el detalle
                     */
                    detalleSolicitudGestionCobroModeloDTO
                        .setEstadoSolicitud(EstadoTareaGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_CANCELADA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                        solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                    detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.NO_EXITOSA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                        solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                    detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                        solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());

                    /*
                     * Se realiza el cambio de estados respectivos cuando la opcion enviar es false
                     * para la solictitud
                     */
                    actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                        EstadoSolicitudGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_CANCELADA);
                    actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                        EstadoSolicitudGestionCobroEnum.NO_EXITOSA);
                    actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                        EstadoSolicitudGestionCobroEnum.CERRADA);

                    resultado = ResultadoBitacoraCarteraEnum.NO_ENVIADO;
                } else {
                    /*
                     * Se realiza el cambio de estados respectivos cuando la opcion enviar es true
                     * para el detalle
                     */
                    detalleSolicitudGestionCobroModeloDTO
                        .setEstadoSolicitud(EstadoTareaGestionCobroEnum.COMUNICADO_REMITIDO_POR_SEGUNDA_VEZ);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                        solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                    detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(
                        EstadoTareaGestionCobroEnum.PENDIENTE_POR_REGISTRAR_SEGUNDO_RESULTADO_DE_ENTREGA);
                    guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                        solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());

                    /*
                     * Se realiza el cambio de estados respectivos cuando la opcion enviar es true
                     * para la solictitud
                     */
                    actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                        EstadoSolicitudGestionCobroEnum.COMUNICADO_REMITIDO_POR_SEGUNDA_VEZ);
                    actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                        EstadoSolicitudGestionCobroEnum.PENDIENTE_POR_REGISTRAR_SEGUNDO_RESULTADO_DE_ENTREGA);
                }

                // Se termina la tarea
                terminarTarea(registroRemision.getIdTarea(), params);

                // Se guarda la bitácora para cada aportante
                for (AportanteRemisionComunicadoDTO aportanteRemision : registroRemision.getAportantes()) {
                    PersonaModeloDTO personaDTO = consultarDatosPersona(aportanteRemision.getTipoIdentificacion(),
                        aportanteRemision.getNumeroIdentificacion());
                    CarteraModeloDTO carteraDTO = obtenerInformacionCartera(aportanteRemision.getTipoIdentificacion(),
                        aportanteRemision.getNumeroIdentificacion(), aportanteRemision.getTipoAportante(),
                        registroRemision.getAccionCobro().getLineaCobro(), aportanteRemision.getPeriodo());
                    Long numeroOperacion = consultarNumeroOperacionCartera(carteraDTO.getIdCartera());

                    ParametrosComunicadoDTO parametrosDTO = new ParametrosComunicadoDTO();
                    Map<String, Object> parametros = new HashMap<>();
                    if (EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR.equals(registroRemision.getPlantilla())) {
                        parametrosDTO.setIdCartera(carteraDTO.getIdCartera());
                        params.put(ConstanteCartera.ID_CARTERA, carteraDTO.getIdCartera().toString());
                    } else {
                        parametrosDTO.setNumeroIdentificacion(aportanteRemision.getNumeroIdentificacion());
                        parametrosDTO.setTipoIdentificacion(aportanteRemision.getTipoIdentificacion());
                        params.put(ConstanteCartera.TIPO_IDENTIFICACION,
                            aportanteRemision.getTipoIdentificacion().name());
                        params.put(ConstanteCartera.NUMERO_IDENTIFICACION, aportanteRemision.getNumeroIdentificacion());
                    }
                    parametrosDTO.setParams(parametros);
                    TipoTransaccionEnum tipoTransaccion = obtenerTipoTransaccion(registroRemision.getPlantilla(),
                        carteraDTO.getTipoAccionCobro());
                    InformacionArchivoDTO archivo = guardarObtenerInfoArchivoComunicado(tipoTransaccion,
                        registroRemision.getPlantilla(), parametrosDTO);

                    DocumentoSoporteModeloDTO documento = new DocumentoSoporteModeloDTO();

                    documento.setDescripcionComentarios(archivo.getDescription());
                    documento.setFechaHoraCargue(new Date().getTime());
                    documento.setIdentificacionDocumento(archivo.getIdentificadorDocumento());
                    documento.setNombre(registroRemision.getPlantilla().name());
                    documento.setVersionDocumento(archivo.getVersionDocumento());
                    documento.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);

                    List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();
                    listaDocumentos.add(documento);
                    // List<ResultadoBitacoraCarteraEnum> resultados = new ArrayList<>();
                    // resultados.add(ResultadoBitacoraCarteraEnum.NO_ENVIADO);
                    // resultados.add(ResultadoBitacoraCarteraEnum.ENVIADO);
                    // List<BitacoraCarteraDTO> bitacoras =
                    // consultarBitacoraSinResultado(numeroOperacion,
                    // TipoActividadBitacoraEnum.valueOf(registroRemision.getAccionCobro().name()),
                    // resultados);
                    // if (bitacoras != null && !bitacoras.isEmpty()){
                    // listaDocumentos.addAll(bitacoras.get(0).getDocumentosSoporte());
                    // }
                    // for (DocumentoSoporteModeloDTO documento : listaDocumentos) {
                    // documento.setIdDocumentoSoporte(null);
                    // }
                    almacenarBitacoraCartera(null, null,
                        TipoActividadBitacoraEnum
                            .valueOf(solicitudGestionCobroFisicoModeloDTO.getTipoAccionCobro().name()),
                        MedioCarteraEnum.DOCUMENTO_FISICO, resultado, null, personaDTO.getIdPersona(),
                        aportanteRemision.getTipoAportante(), listaDocumentos, numeroOperacion.toString(), null);
                }
            }

            logger.debug("Fin de método guardarSegundaRemision(RegistroRemisionAportantesDTO)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarSegundaRemision(RegistroRemisionAportantesDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    private InformacionArchivoDTO guardarObtenerInfoArchivoComunicado(TipoTransaccionEnum tipoTransaccion,
                                                                      EtiquetaPlantillaComunicadoEnum etiquetaPlantilla, ParametrosComunicadoDTO parametros) {
        GuardarObtenerInfoArchivoComunicado service = new GuardarObtenerInfoArchivoComunicado(tipoTransaccion,
            etiquetaPlantilla, parametros);
        service.execute();
        logger.debug("Fin de método guardarObtenerComunicadoECM");
        return service.getResult();
    }

    private TipoTransaccionEnum obtenerTipoTransaccion(EtiquetaPlantillaComunicadoEnum plantilla,
                                                       TipoAccionCobroEnum accion) {
        MetodoAccionCobroEnum metodo = accion.getMetodo();
        switch (plantilla) {
            case NTF_NO_REC_APO:
            case SUS_NTF_NO_PAG:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1A_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2A_FISICO;
            case AVI_INC:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1B_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2B_FISICO;
            case LIQ_APO_MOR:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1C_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2C_FISICO;
            case CIT_NTF_PER:
                return TipoTransaccionEnum.ACCION_COBRO_2C_FISICO;
            case PRI_AVI_COB_PRS:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1D_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2F_FISICO;
            case NTF_AVI:
                return TipoTransaccionEnum.ACCION_COBRO_2D_FISICO;
            case SEG_AVI_COB_PRS:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1E_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2G_FISICO;
            case CAR_EMP_EXP:
                return MetodoAccionCobroEnum.METODO_1.equals(metodo) ? TipoTransaccionEnum.ACCION_COBRO_1F_FISICO
                    : TipoTransaccionEnum.ACCION_COBRO_2H_FISICO;
            default:
                return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * registrarResultadoSegundaRemision(com.asopagos.dto.cartera.
     * RegistroRemisionAportantesDTO)
     */
    @Override
    public void registrarResultadoSegundaRemision(RegistroRemisionAportantesDTO registroRemision) {
        try {
            logger.debug("Inicio de método registrarResultadoSegundaRemision(RegistroRemisionAportantesDTO)");

            /* Se consulta la solicitud */
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroFisicoModeloDTO = consultarSolicitudGestionCobro(
                registroRemision.getNumeroRadicacion());

            /* Se consulta el detalle de gestion de cobro */
            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO = consultarDetallePorSolicitud(
                solicitudGestionCobroFisicoModeloDTO.getIdSolicitudGestionCobroFisico());

            /* Se arma el documento */
            String nombreArchivo = "DOCUMENTO_SEGUNDA_REMISION_COMUNICADO";
            String descipcionArchivo = "Se realiza segunda remision del comunicado";
            DocumentoSoporteModeloDTO documentoSoporte = construirDocumentoSoporte(
                detalleSolicitudGestionCobroModeloDTO.getDocumentoSegundaRemision(),
                registroRemision.getAportantes().get(0).getIdDocumentoSegundaEntrega(), nombreArchivo,
                descipcionArchivo);
            detalleSolicitudGestionCobroModeloDTO.setDocumentoSegundaRemision(documentoSoporte);

            /* Se settean los demás valores del detalle */
            detalleSolicitudGestionCobroModeloDTO
                .setFechaSegundaEntrega(registroRemision.getAportantes().get(0).getFechaSegundaEntrega());
            detalleSolicitudGestionCobroModeloDTO.setObservacionSegundaEntrega(
                registroRemision.getAportantes().get(0).getObservacionSegundaEntrega());
            detalleSolicitudGestionCobroModeloDTO
                .setResultadoSegundaEntrega(registroRemision.getAportantes().get(0).getResultadoSegundaEntrega());

            /* Se guardan los datos requeridos */
            if (ResultadoEntregaEnum.PENDIENTE
                .equals(registroRemision.getAportantes().get(0).getResultadoSegundaEntrega())) {
                guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                    solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
            }

            if (!ResultadoEntregaEnum.PENDIENTE
                .equals(registroRemision.getAportantes().get(0).getResultadoSegundaEntrega())) {
                // Se guarda el registro en la bitacora de cartera
                ResultadoBitacoraCarteraEnum resultado = ResultadoBitacoraCarteraEnum.EXITOSO;

                /* Se ejecuta validaciones HU175 seccion 10 */
                switch (registroRemision.getAportantes().get(0).getResultadoSegundaEntrega()) {
                    case ENTREGA_EXITOSA:

                        /* Se actualiza el estado de la solicitud */
                        actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                            EstadoSolicitudGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_EXITOSA);
                        /* Se actualiza el estado de la solicitud */
                        actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                            EstadoSolicitudGestionCobroEnum.CERRADA);

                        /* Se cambia el estado para el detalle */
                        detalleSolicitudGestionCobroModeloDTO
                            .setEstadoSolicitud(EstadoTareaGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_EXITOSA);
                        /* Se guarda el detalle */
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                        /* se consulta el detalle */
                        detalleSolicitudGestionCobroModeloDTO = consultarDetallePorSolicitud(
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitudGestionCobroFisico());
                        /* Se cambia el estado para el detalle */
                        detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
                        /* Se guarda el detalle */
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                        break;

                    case ENTREGA_NO_EXITOSA:

                        /* Se actualiza el estado de la solicitud */
                        actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                            EstadoSolicitudGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_NO_EXITOSA);
                        /* Se actualiza el estado de la solicitud */
                        actualizarEstadoSolicitudGestionCobro(registroRemision.getNumeroRadicacion(),
                            EstadoSolicitudGestionCobroEnum.CERRADA);

                        /* Se cambia el estado para el detalle */
                        detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(
                            EstadoTareaGestionCobroEnum.SEGUNDA_REMISION_DE_COMUNICADO_NO_EXITOSA);
                        /* Se guarda el detalle */
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                        /* se consulta el detalle */
                        detalleSolicitudGestionCobroModeloDTO = consultarDetallePorSolicitud(
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitudGestionCobroFisico());
                        /* Se cambia el estado para el detalle */
                        detalleSolicitudGestionCobroModeloDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
                        /* Se guarda el detalle */
                        guardarDetalleSolicitudGestionCobro(detalleSolicitudGestionCobroModeloDTO,
                            solicitudGestionCobroFisicoModeloDTO.getIdSolicitud());
                        resultado = ResultadoBitacoraCarteraEnum.NO_ENVIADO;
                        break;
                    default:
                        break;
                }

                /* Se termina la tarea */
                terminarTarea(registroRemision.getIdTarea(), null);

                // Se guarda la bitácora para cada aportante
                for (AportanteRemisionComunicadoDTO aportanteRemision : registroRemision.getAportantes()) {
                    List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();

                    if (documentoSoporte != null) {
                        listaDocumentos.add(documentoSoporte);
                    }

                    PersonaModeloDTO personaDTO = consultarDatosPersona(aportanteRemision.getTipoIdentificacion(),
                        aportanteRemision.getNumeroIdentificacion());
                    CarteraModeloDTO carteraDTO = obtenerInformacionCartera(aportanteRemision.getTipoIdentificacion(),
                        aportanteRemision.getNumeroIdentificacion(), aportanteRemision.getTipoAportante(),
                        registroRemision.getAccionCobro().getLineaCobro(), aportanteRemision.getPeriodo());
                    Long numeroOperacion = consultarNumeroOperacionCartera(carteraDTO.getIdCartera());
                    almacenarBitacoraCartera(null, null,
                        TipoActividadBitacoraEnum
                            .valueOf(solicitudGestionCobroFisicoModeloDTO.getTipoAccionCobro().name()),
                        MedioCarteraEnum.DOCUMENTO_FISICO, resultado, null, personaDTO.getIdPersona(),
                        aportanteRemision.getTipoAportante(), listaDocumentos, numeroOperacion.toString(), null);
                }
            }

            logger.debug("Fin de método registrarResultadoSegundaRemision(RegistroRemisionAportantesDTO)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en registrarResultadoSegundaRemision(RegistroRemisionAportantesDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#guardarEntrega(java.lang.String,
     * com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO)
     */
    @Override
    public EstadoSolicitudGestionCobroEnum guardarEntrega(String numeroRadicacion,
                                                          AportanteRemisionComunicadoDTO aportanteRemision) {
        try {
            logger.debug(
                "Inicio de servicio guardarEntrega(String numeroRadicacion, AportanteRemisionComunicadoDTO aportanteRemision)");
            SolicitudModeloDTO solicitudDTO = consultarSolicitudPorRadicado(numeroRadicacion);
            // Se crea el FiltroDetalleDTO para poder consultar el detalle
            FiltroDetalleSolicitudGestionCobroDTO filtroDetalleSolicitud = new FiltroDetalleSolicitudGestionCobroDTO();
            filtroDetalleSolicitud.setTipoIdentificacion(aportanteRemision.getTipoIdentificacion());
            filtroDetalleSolicitud.setNumeroIdentificacion(aportanteRemision.getNumeroIdentificacion());
            filtroDetalleSolicitud.setNumeroRadicacion(numeroRadicacion);
            filtroDetalleSolicitud.setEnviarPrimeraRemision(aportanteRemision.getEnviar());
            List<FiltroDetalleSolicitudGestionCobroDTO> lstFiltroDetalleSolicitud = new ArrayList<>();
            lstFiltroDetalleSolicitud.add(filtroDetalleSolicitud);
            List<DetalleSolicitudGestionCobroModeloDTO> lstDetallesSolicitudes = consultarDetalleSolicitudGestionCobro(lstFiltroDetalleSolicitud);
            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudIndividual = lstDetallesSolicitudes.get(0);
            detalleSolicitudIndividual.setFechaPrimeraEntrega(aportanteRemision.getFechaPrimeraEntrega());
            detalleSolicitudIndividual.setObservacionPrimeraEntrega(aportanteRemision.getObservacionPrimeraEntrega());

            /* Se arma el documento */
            String nombreArchivo = "DOCUMENTO_REGISTRO_PRIMERA_REMISION_COMUNICADO";
            String descipcionArchivo = "Se realiza registro de primera remisión del comunicado";
            DocumentoSoporteModeloDTO documentoDTO = construirDocumentoSoporte(
                detalleSolicitudIndividual.getDocumentoPrimeraRemision(),
                aportanteRemision.getIdDocumentoPrimeraEntrega(), nombreArchivo, descipcionArchivo);

            /* Se settea el documento al detalle */
            detalleSolicitudIndividual.setDocumentoPrimeraRemision(documentoDTO);

            /* Se settea el estado del resultado de primera remision */
            detalleSolicitudIndividual.setResultadoPrimeraEntrega(aportanteRemision.getResultadoPrimeraEntrega());

            if (ResultadoEntregaEnum.ENTREGA_EXITOSA.equals(aportanteRemision.getResultadoPrimeraEntrega())) {
                detalleSolicitudIndividual.setEstadoSolicitud(EstadoTareaGestionCobroEnum.REMISION_COMUNICADO_EXITOSA);
            } else {
                detalleSolicitudIndividual
                    .setEstadoSolicitud(EstadoTareaGestionCobroEnum.REMISION_COMUNICADO_NO_EXITOSA);
            }


            //Start Glpi 74199
            /* Se actualiza y guarda el detalle de la solicitud */
            logger.info("Guardando detalle de solicitud con estado de primera remisión: " + detalleSolicitudIndividual.getEnviarPrimeraRemision());
            detalleSolicitudIndividual.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
            guardarDetalleSolicitudGestionCobro(detalleSolicitudIndividual, solicitudDTO.getIdSolicitud());


            // Actualizar la bitácora con el resultado de la primera entrega
            //BitacoraCarteraDTO bitacora = actualizarBitacoraGestionCobro(aportanteRemision.getTipoIdentificacion(), aportanteRemision.getNumeroIdentificacion(), numeroRadicacion, aportanteRemision.getResultadoPrimeraEntrega());
            BitacoraCarteraDTO bitacora = consultarBitacoraAActualizarPersonaRadicado(aportanteRemision.getTipoIdentificacion(), aportanteRemision.getNumeroIdentificacion(), numeroRadicacion);
            if (bitacora != null) {
                logger.info("Detalle de solicitud actualizado y guardado. Bitácora actualizada con ID: " + bitacora.getIdBitacoraCartera() + " y resultado de primera entrega: " + aportanteRemision.getResultadoPrimeraEntrega());
                asignarResultadoABitacora(bitacora, aportanteRemision.getResultadoPrimeraEntrega());

                if (documentoDTO != null) {
                    List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();
                    listaDocumentos.add(documentoDTO);
                    bitacora.setDocumentosSoporte(listaDocumentos);
                }
                bitacora.setIdBitacoraCartera(null);
                guardarBitacoraCartera(bitacora);
            } else {
                logger.info("Bitácora es null");
            }
            //Finish Glpi 74199
            logger.info("LIsto, actualizo");

            logger.debug(
                "Fin de servicio guardarEntrega(String numeroRadicacion, AportanteRemisionComunicadoDTO aportanteRemision)");
            return EstadoSolicitudGestionCobroEnum.CERRADA;
        } catch (AsopagosException aso) {
            throw aso;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en guardarEntrega(String numeroRadicacion, AportanteRemisionComunicadoDTO aportanteRemision)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }



    private DetalleSolicitudGestionCobroModeloDTO obtenerDetalleSolicitud(AportanteRemisionComunicadoDTO aportanteRemision, String numeroRadicacion) {
        FiltroDetalleSolicitudGestionCobroDTO filtro = new FiltroDetalleSolicitudGestionCobroDTO();
        filtro.setTipoIdentificacion(aportanteRemision.getTipoIdentificacion());
        filtro.setNumeroIdentificacion(aportanteRemision.getNumeroIdentificacion());
        filtro.setNumeroRadicacion(numeroRadicacion);
        filtro.setEnviarPrimeraRemision(aportanteRemision.getEnviar());
        List<DetalleSolicitudGestionCobroModeloDTO> detalles = consultarDetalleSolicitudGestionCobro(Collections.singletonList(filtro));
        return detalles.isEmpty() ? null : detalles.get(0);
    }

    private void actualizarYGuardarDetalleSolicitud(DetalleSolicitudGestionCobroModeloDTO detalle, Long idSolicitud, AportanteRemisionComunicadoDTO aportanteRemision) {
        if (detalle == null) {
            logger.warn("Detalle de solicitud no encontrado.");
            return;
        }

        detalle.setFechaPrimeraEntrega(aportanteRemision.getFechaPrimeraEntrega());
        detalle.setObservacionPrimeraEntrega(aportanteRemision.getObservacionPrimeraEntrega());
        detalle.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
        guardarDetalleSolicitudGestionCobro(detalle, idSolicitud);
    }


    private void asignarResultadoABitacora(BitacoraCarteraDTO bitacora, ResultadoEntregaEnum resultadoEntrega) {
        if (bitacora == null) {
            logger.warn("Bitácora no disponible para asignar resultado.");
            return;
        }

        switch (resultadoEntrega) {
            case ENTREGA_EXITOSA:
                bitacora.setResultado(ResultadoBitacoraCarteraEnum.EXITOSO);
                break;
            case ENTREGA_NO_EXITOSA:
                bitacora.setResultado(ResultadoBitacoraCarteraEnum.NO_EXITOSO);
                break;
            case PENDIENTE:
                bitacora.setResultado(ResultadoBitacoraCarteraEnum.EN_PROCESO);
                break;
            default:
                logger.warn("Resultado de entrega no reconocido: " + resultadoEntrega);
                break;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#tramitarDesafiliacion(java.lang.String,
     * java.lang.String,
     * java.lang.Long, boolean)
     */
    @Override
    public void tramitarDesafiliacion(String numeroRadicacion, Long idTarea, String observacionCoordinador,
                                      boolean aprobado) {
        String firmaMetodo = "tramitarDesafiliacion(String numeroRadicacion, String observacionCoordinador, Long idTarea, boolean aprobado)";
        logger.debug("Inicia " + firmaMetodo);
        try {
            SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO = consultarSolicitudDesafiliacion(
                numeroRadicacion);
            solicitudDesafiliacionModeloDTO.setComentarioCoordinador(observacionCoordinador);
            Map<String, Object> params = new HashMap<>();
            params.put(ANALISTA_CARTERA, solicitudDesafiliacionModeloDTO.getUsuarioRadicacion());

            if (aprobado) {
                solicitudDesafiliacionModeloDTO.setEstadoSolicitud(EstadoSolicitudDesafiliacionEnum.APROBADA);
                guardarSolicitudDesafiliacion(solicitudDesafiliacionModeloDTO);
            } else {
                solicitudDesafiliacionModeloDTO.setEstadoSolicitud(EstadoSolicitudDesafiliacionEnum.RECHAZADA);
                guardarSolicitudDesafiliacion(solicitudDesafiliacionModeloDTO);
            }

            /* Se termina tarea */
            terminarTarea(idTarea, params);
        } catch (AsopagosException aso) {
            throw aso;
        } catch (Exception e) {
            logger.error("Ocurrió un error en " + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza " + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#finalizarSolicitudDesafiliacion(com.asopagos.dto.modelo.DesafiliacionDTO)
     */
    @Override
    public void finalizarSolicitudDesafiliacion(DesafiliacionDTO desafiliacionDTO, @Context UserDTO userDTO) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        logger.debug("Inicia finalizarSolicitudDesafiliacion");

        SuspenderTarea suspenderTareaService = new SuspenderTarea(desafiliacionDTO.getIdTarea(), new HashMap<>());
        suspenderTareaService.setToken(userDTO.getToken());
        suspenderTareaService.execute();
        try{
            if(desafiliacionDTO.getDesafiliacionAportanteDTOs().size() >=10 ){
                CompletableFuture.runAsync(() -> {
                    procesarSolicitudDesafiliacion(desafiliacionDTO, userDTO);
                }, executor);
            }else{
                procesarSolicitudDesafiliacion(desafiliacionDTO, userDTO);
            }
        }catch(Exception e){
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
            executor.shutdown(); // Asegurar que el servicio se apaga correctamente
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void procesarSolicitudDesafiliacion(DesafiliacionDTO desafiliacionDTO, UserDTO userDTO){
        List<Long> idEmpleadores = new ArrayList<>();
        try {
            // Recuperar los datos de los empleadores de forma sincrónica antes de lanzar procesos asincrónicos
            for (DesafiliacionAportanteDTO desafiliarAportante : desafiliacionDTO.getDesafiliacionAportanteDTOs()) {
                EmpleadorModeloDTO idEmpleador = (EmpleadorModeloDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_DATOS_EMPLEADOR_MOVER_CARTERA)
                                        .setParameter("perId", desafiliarAportante.getIdPersona())
                                        .getSingleResult();
                idEmpleadores.add(idEmpleador.getIdEmpleador());
            }

            // Tarea asincrónica para ejecutar desafiliación de empleadores primero
                ejecutarNovedadDesafiliacionEmpleadorPersona(idEmpleadores, TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
                actualizarLineaCobroDesafiliacion(desafiliacionDTO.getDesafiliacionAportanteDTOs(), userDTO.getNombreUsuario());
                RetomarTarea retomarTareaService = new RetomarTarea(desafiliacionDTO.getIdTarea(), new HashMap<>());
                retomarTareaService.setToken(userDTO.getToken());
                retomarTareaService.execute();

                TerminarTarea terminarTarea = new TerminarTarea(desafiliacionDTO.getIdTarea(), new HashMap<>());
                terminarTarea.setToken(userDTO.getToken());
                terminarTarea.execute();
            } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private void procesarAportanteDesafiliacion(DesafiliacionAportanteDTO desafiliacionAportanteDTO,
                                                List<PersonaRetiroNovedadAutomaticaDTO> personasRetiro,
                                                boolean estadoSolicitud,
                                                List<Long> idAportantes) {

        //ObjectMapper objectMapper = new ObjectMapper();
        List<RolAfiliadoEmpleadorDTO> rolAfiliadoEmpleadorDTOs;

        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
            .equals(desafiliacionAportanteDTO.getTipoSolicitante())) {
            EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(
                desafiliacionAportanteDTO.getTipoIdentificacion(),
                desafiliacionAportanteDTO.getNumeroIdentificacion());
            //logger.info("empleadorModeloDTO::: " + objectMapper.writeValueAsString(empleadorModeloDTO));
            //logger.info("empleadorModeloDTO::: " + empleadorModeloDTO.toString());

            idAportantes.add(empleadorModeloDTO.getIdEmpleador());
            if (empleadorModeloDTO != null) {
                if (estadoSolicitud) {
                    empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.DESAFILIACION_APROBADA);
                    guardarDatosEmpleador(empleadorModeloDTO);
                    empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.EXITOSA);
                    guardarDatosEmpleador(empleadorModeloDTO);
                } else {
                    empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                    guardarDatosEmpleador(empleadorModeloDTO);
                }
            }
        } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
            .equals(desafiliacionAportanteDTO.getTipoSolicitante())
            || TipoSolicitanteMovimientoAporteEnum.PENSIONADO
            .equals(desafiliacionAportanteDTO.getTipoSolicitante())) {
            rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(
                desafiliacionAportanteDTO.getTipoIdentificacion(),
                desafiliacionAportanteDTO.getNumeroIdentificacion(),
                TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                    .equals(desafiliacionAportanteDTO.getTipoSolicitante())
                    ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
                    : TipoAfiliadoEnum.PENSIONADO);
            idAportantes.add(rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getAfiliado().getIdPersona());
            if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                    rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                PersonaRetiroNovedadAutomaticaDTO personaRetiro = new PersonaRetiroNovedadAutomaticaDTO();
                List<RolAfiliadoModeloDTO> rolesAfiliado = new ArrayList<>();
                rolesAfiliado.add(rolAfiliadoDTO);
                personaRetiro.setListRolAfiliado(rolesAfiliado);
                personasRetiro.add(personaRetiro);
                if (rolAfiliadoDTO != null) {
                    if (estadoSolicitud) {
                        rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.DESAFILIACION_APROBADA);
                        actualizarRolAfiliado(rolAfiliadoDTO);
                        rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.EXITOSA);
                        actualizarRolAfiliado(rolAfiliadoDTO);
                    } else {
                        rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                        actualizarRolAfiliado(rolAfiliadoDTO);
                    }
                }
            }
        }
    }
    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#gestionarDesafiliacion(java.util.List)
     */
    @Override
    public String gestionarDesafiliacion(UserDTO userDTO, List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs) {
        String firmaMetodo = "gestionarDesafiliacion(List<DesafiliacionAportanteDTO> aportanteDesafiliacionDTOs)";
        logger.debug("Inicia " + firmaMetodo);
        try {

            List<RolAfiliadoEmpleadorDTO> rolAfiliadoEmpleadorDTOs = new ArrayList<>();
            String numeroRad = "";
            if (desafiliacionAportanteDTOs != null && !desafiliacionAportanteDTOs.isEmpty()) {

                /* Se construye la solicitud de desafiliacion */
                SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO = construirSolicitudDesafiliacion(
                    userDTO, desafiliacionAportanteDTOs.get(0).getTipoSolicitante());

                solicitudDesafiliacionModeloDTO.setAportanteDesafiliacionDTOs(desafiliacionAportanteDTOs);
                /* Se obtiene el id de solicitud */
                Long idSolicitud = guardarSolicitudDesafiliacion(solicitudDesafiliacionModeloDTO).getIdSolicitud();
                /* Se genera numero de radicado para la solicitud */
                numeroRad = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());

                /* Se inicia proceso */
                Map<String, Object> params = new HashMap<>();

                /* Se obtiene el supervisor (coordinador) */
                String supervisor = asignarAutomaticamenteUsuarioCajaCompensacion(
                    new Long(userDTO.getSedeCajaCompensacion()), ProcesoEnum.DESAFILIACION_APORTANTES);

                params.put(ID_SOLICITUD, idSolicitud);
                params.put(NUMERO_RADICADO, numeroRad);
                params.put(SUP_CAR, supervisor);

                Long idProceso = iniciarProceso(ProcesoEnum.DESAFILIACION_APORTANTES, params, Boolean.FALSE);
                /* Se consulta la solicitud de desafiliacion */
                solicitudDesafiliacionModeloDTO = consultarSolicitudDesafiliacion(numeroRad);
                solicitudDesafiliacionModeloDTO.setIdInstanciaProceso(idProceso.toString());
                solicitudDesafiliacionModeloDTO.setDestinatario(supervisor);
                solicitudDesafiliacionModeloDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
                guardarSolicitudDesafiliacion(solicitudDesafiliacionModeloDTO);

                /*
                 * Se recorre los aportantes que son canditatos a expulsar con posibles acciones
                 * de cobro 1F, 2H, LC4C y LC5C
                 */
                for (DesafiliacionAportanteDTO desafiliacionAportanteDTO : desafiliacionAportanteDTOs) {
                    /* Se valida el tipo de solicitante */
                    if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
                        .equals(desafiliacionAportanteDTO.getTipoSolicitante())) {

                        /* Se consulta el empleador para asignar la marca */
                        EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(
                            desafiliacionAportanteDTO.getTipoIdentificacion(),
                            desafiliacionAportanteDTO.getNumeroIdentificacion());
                        if (empleadorModeloDTO != null) {
                            empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.ESPERA_DESAFILIACION);
                            /* Se actualiza el empleador */
                            guardarDatosEmpleador(empleadorModeloDTO);
                        }

                    } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                        .equals(desafiliacionAportanteDTO.getTipoSolicitante())
                        || TipoSolicitanteMovimientoAporteEnum.PENSIONADO
                        .equals(desafiliacionAportanteDTO.getTipoSolicitante())) {
                        /* Se consultan los roles */
                        rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(
                            desafiliacionAportanteDTO.getTipoIdentificacion(),
                            desafiliacionAportanteDTO.getNumeroIdentificacion(),
                            TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                                .equals(desafiliacionAportanteDTO.getTipoSolicitante())
                                ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
                                : TipoAfiliadoEnum.PENSIONADO);

                        /* Se valida que hay datos */
                        if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                            /* Se consulta el afiliado */
                            RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                                rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                            if (rolAfiliadoDTO != null) {
                                rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.ESPERA_DESAFILIACION);
                                /* Se actualiza el afiliado */
                                actualizarRolAfiliado(rolAfiliadoDTO);
                            }
                        }
                    }
                }
            }

            logger.debug("Finaliza " + firmaMetodo);
            return "\"" + numeroRad + "\"";
        } catch (Exception e) {
            logger.error("Ocurrió un error en " + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#cierreMasivoSolicitudPreventivaAgrupadora()
     */
    @Override
    public void cierreMasivoSolicitudPreventivaAgrupadora(UserDTO userDTO) {
        try {
            logger.debug("Inicia servicio cierreMasivoSolicitudPreventivaAgrupadora");

            // Consulta las solicitudes preventivas individuales manuales, candidatas a
            // cierre por extemporaneidad
            List<SolicitudPreventivaModeloDTO> listaSolicitudesIndividuales = consultarSolicitudesIndividualesCierrePreventiva();
            logger.error("1Cierre Masivo info individuales:" + listaSolicitudesIndividuales);
            for (SolicitudPreventivaModeloDTO solicitudDTO : listaSolicitudesIndividuales) {
                solicitudDTO.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.NO_EXITOSA);
                guardarSolicitudPreventiva(solicitudDTO);
                solicitudDTO.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.CERRADA);
                guardarSolicitudPreventiva(solicitudDTO);

                // Persiste en BitacoraCartera
                guardarBitacoraPreventiva(solicitudDTO, TipoActividadBitacoraEnum.CIERRE_PREVENTIVA,
                    MedioCarteraEnum.PERSONAL);
            }

            // Consulta la lista de solicitudes agrupadoras para realizar el cierre a las
            // que apliquen
            List<SolicitudPreventivaAgrupadoraModeloDTO> listaPreventivaAgrupadorasDTO = consultarSolicitudesAgrupadorasCierrePreventiva();
            List<SolicitudPreventivaAgrupadoraModeloDTO> listaAgrupadorasCierreDTO = new ArrayList<>();
            logger.error("2Cierre Masivo info agrupadoras:" + listaPreventivaAgrupadorasDTO);

            // Evalúa una a una las solciitudes agrupadoras para verificar si ya se puede
            // realizar el cierre
            for (SolicitudPreventivaAgrupadoraModeloDTO solicitudAgrupadoraDTO : listaPreventivaAgrupadorasDTO) {
                List<Long> idsSolicitudAgrupadora = new ArrayList<>();
                idsSolicitudAgrupadora.add(solicitudAgrupadoraDTO.getIdSolicitudPreventivaAgrupadora());
                List<SolicitudPreventivaAgrupadoraModeloDTO> solicitudAgrupadoras = validarCierreSolicitudAgrupadora(
                    idsSolicitudAgrupadora);
                logger.error("4Cierre Masivo info solicitudespreventivas:" + solicitudAgrupadoras);
                listaAgrupadorasCierreDTO.addAll(solicitudAgrupadoras);
            }
            List<Long> idInstnacias = new ArrayList<>();

            // Realiza el cierre de las solicitudes agrupadoras
            for (SolicitudPreventivaAgrupadoraModeloDTO solicitudDTO : listaAgrupadorasCierreDTO) {
                if (solicitudDTO.getIdInstanciaProceso() != null) {
                    idInstnacias.add(Long.parseLong(solicitudDTO.getIdInstanciaProceso()));
                }
            }
            if (!idInstnacias.isEmpty()) {
                abortarProcesos(ProcesoEnum.GESTION_PREVENTIVA_CARTERA, idInstnacias, Boolean.FALSE);
            }

            logger.debug("Finaliza servicio cierreMasivoSolicitudPreventivaAgrupadora");
        } catch (Exception e) {
            logger.error("Ocurrió un error en cierreMasivoSolicitudPreventivaAgrupadora", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que invoca el servicio que consulta la lista de solicitudes
     * preventivas agrupadoras, candidatas a cierre por extemporaneidad
     *
     * @return La lista de solicitudes agrupadoras
     */
    private List<SolicitudPreventivaAgrupadoraModeloDTO> consultarSolicitudesAgrupadorasCierrePreventiva() {
        logger.debug("Inicia método consultarSolicitudesAgrupadorasCierrePreventiva");
        ConsultarSolicitudesAgrupadorasCierrePreventiva service = new ConsultarSolicitudesAgrupadorasCierrePreventiva();
        service.execute();
        logger.debug("Finaliza método consultarSolicitudesAgrupadorasCierrePreventiva");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de solicitudes preventivas
     * individuales pre-cierre
     *
     * @return La lista de solciitudes preventivas
     */
    private List<SolicitudPreventivaModeloDTO> consultarSolicitudesIndividualesCierrePreventiva() {
        logger.debug("Inicia método consultarSolicitudesIndividualesCierrePreventiva");
        ConsultarSolicitudesIndividualesCierrePreventiva service = new ConsultarSolicitudesIndividualesCierrePreventiva();
        service.execute();
        logger.debug("Finaliza método consultarSolicitudesIndividualesCierrePreventiva");
        return service.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#guardarGestionCicloManualTemporal(com.asopagos.dto.cartera.GestionCicloManualDTO)
     */
    @Override
    public void guardarGestionCicloManualTemporal(Long numeroOperacion, Boolean tieneSolicitudManual,
                                                  GestionCicloManualDTO gestionCicloDTO) {
        String firmaMetodo = "guardarGestionCicloManualTemporal(Long,Boolean,GestionCicloManualDTO)";
        logger.debug("Inicia " + firmaMetodo);

        if (tieneSolicitudManual) {
            SolicitudGestionCobroManualModeloDTO solicitudGestionManual = consultarSolicitudGestionCobroManual(null,
                numeroOperacion);
            if (solicitudGestionManual != null) {
                actualizarEstadoSolicitudGestionCobroManual(solicitudGestionManual.getNumeroRadicacion(),
                    EstadoFiscalizacionEnum.EN_PROCESO);
            }
        }

        CarteraModeloDTO carteraDTO = consultarInformacionCarteraPorNumeroOperacion(numeroOperacion);
        SolicitudGestionCobroManualModeloDTO solicitudGestionCobroManual = consultarSolicitudGestionCobroManual(null,
            numeroOperacion);

        // Se asocia el id del ciclo aportante de la solicitud con el listado de agendas
        for (AgendaCarteraModeloDTO agendaCartera : gestionCicloDTO.getLstAgendasCartera()) {
            if (solicitudGestionCobroManual != null) {
                agendaCartera.setIdCicloAportante(solicitudGestionCobroManual.getIdCicloAportante());
            }
            agendaCartera.setIdCartera(carteraDTO.getIdCartera());
        }

        // Se guarda el listado de agendas pertenecientes a cartera
        guardarAgendaCartera(gestionCicloDTO.getLstAgendasCartera());

        for (ActividadCarteraModeloDTO actividadCartera : gestionCicloDTO.getLstActividadesCartera()) {
            if (tieneSolicitudManual && solicitudGestionCobroManual != null) {
                actividadCartera.setIdCicloAportante(solicitudGestionCobroManual.getIdCicloAportante());
            }
            actividadCartera.setIdCartera(carteraDTO.getIdCartera());

            if (actividadCartera.getActividadDocumentoModeloDTOs() != null
                && !actividadCartera.getActividadDocumentoModeloDTOs().isEmpty()) {
                for (ActividadDocumentoModeloDTO documento : actividadCartera.getActividadDocumentoModeloDTOs()) {
                    InformacionArchivoDTO informacionArchivoDTO = obtenerArchivo(
                        documento.getIdentificacionDocumento());
                    documento.setDescripcionComentarios("Documento actividad cartera");
                    documento.setFechaHoraCargue(new Date().getTime());
                    documento.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
                    documento.setNombre(informacionArchivoDTO.getDocName() == null ? "Documento actividad cartera"
                        : informacionArchivoDTO.getDocName());
                    documento.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
                }
            }
        }
        guardarActividadCartera(gestionCicloDTO.getLstActividadesCartera());

        if (gestionCicloDTO.getLstActividadesCartera() != null) {
            for (ActividadCarteraModeloDTO actividadDTO : gestionCicloDTO.getLstActividadesCartera()) {
                if (actividadDTO.getEsNuevaActividad()) {
                    List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();
                    if (actividadDTO.getActividadDocumentoModeloDTOs() != null) {
                        for (ActividadDocumentoModeloDTO actividadDocumento : actividadDTO
                            .getActividadDocumentoModeloDTOs()) {
                            DocumentoSoporteModeloDTO documento = actividadDocumento.obtenerDocumentoSoporteDTO();
                            listaDocumentos.add(documento);
                        }
                    }
                    String actividad = consultarActividadBitacoraNumeroOperacion(String.valueOf(numeroOperacion), actividadDTO.getActividadFiscalizacion().name());
                    if (Objects.isNull(actividad)) {
                        almacenarBitacoraCartera(null, null,
                            TipoActividadBitacoraEnum.valueOf(actividadDTO.getActividadFiscalizacion().name()),
                            MedioCarteraEnum.PERSONAL,
                            ResultadoBitacoraCarteraEnum.valueOf(actividadDTO.getResutadoFiscalizacion().name()), null,
                            carteraDTO.getIdPersona(), carteraDTO.getTipoSolicitante(), listaDocumentos,
                            String.valueOf(numeroOperacion), null);
                        if (TipoActividadBitacoraEnum.APORTANTE_NO_TIENE_VOLUNTAD_PAGO
                            .equals(TipoActividadBitacoraEnum.valueOf(actividadDTO.getActividadFiscalizacion().name()))
                            || TipoActividadBitacoraEnum.APORTANTE_PROCESO_CONCURSAL.equals(
                            TipoActividadBitacoraEnum.valueOf(actividadDTO.getActividadFiscalizacion().name()))
                            || TipoActividadBitacoraEnum.LIQUIDACION_PROCESO_SUCESION.equals(TipoActividadBitacoraEnum
                            .valueOf(actividadDTO.getActividadFiscalizacion().name()))) {
                            actualizarExclusionCartera(mapperActualizarBitacora(carteraDTO), null);
                        } else if (TipoActividadBitacoraEnum.INACTIVACION_PROCESO_CONCURSAL
                            .equals(TipoActividadBitacoraEnum.valueOf(actividadDTO.getActividadFiscalizacion().name()))
                            || TipoActividadBitacoraEnum.INACTIVACION_LIQUIDACION_O_SUCESION.equals(
                            TipoActividadBitacoraEnum.valueOf(actividadDTO.getActividadFiscalizacion().name()))
                            || TipoActividadBitacoraEnum.SALE_RIEGO_INCOBRABILIDAD.equals(TipoActividadBitacoraEnum
                            .valueOf(actividadDTO.getActividadFiscalizacion().name()))) {
                            actualizarExclusionCarteraInactivar(carteraDTO.getIdPersona());
                        }
                    }
                }
            }
        }
        logger.info("Finaliza guardarGestionCicloManualTemporal");
    }

    public String consultarActividadBitacoraNumeroOperacion(String numeroOperacion, String actividad) {
        ConsultarActividadBitacoraNumeroOperacion service = new ConsultarActividadBitacoraNumeroOperacion(numeroOperacion, actividad);
        service.execute();
        return service.getResult();
    }

    public ExclusionCarteraDTO mapperActualizarBitacora(CarteraModeloDTO carteraModeloDTO) {
        ExclusionCarteraDTO exclusionCarteraDTO = new ExclusionCarteraDTO();
        exclusionCarteraDTO.setEstadoAntesExclusion(EstadoAportanteEnum.ACTIVO);
        exclusionCarteraDTO.setEstadoExclusionCartera(EstadoExclusionCarteraEnum.ACTIVA);
        exclusionCarteraDTO.setFechaFinalizacion(sumarAñoFechaInicial(new Date()));
        exclusionCarteraDTO.setFechaInicio(new Date().getTime());
        exclusionCarteraDTO.setFechaRegistro(new Date().getTime());
        exclusionCarteraDTO.setIdPersona(carteraModeloDTO.getIdPersona());
        exclusionCarteraDTO.setObservacion(null);
        exclusionCarteraDTO.setTipoExclusionCartera(TipoExclusionCarteraEnum.RIESGO_INCOBRABILIDAD);
        exclusionCarteraDTO.setTipoSolicitante(carteraModeloDTO.getTipoSolicitante());
        return exclusionCarteraDTO;
    }

    public Long sumarAñoFechaInicial(Date fecha) {
        Calendar año = Calendar.getInstance();
        año.setTime(fecha);
        año.add(Calendar.DATE, 365);
        fecha = año.getTime();
        return fecha.getTime();
    }

    public void actualizarExclusionCarteraInactivar(Long idPersona) {
        logger.info("Inicia método actualizarExclusionCarteraInactivar");
        ActualizarExclusionCarteraInactivar service = new ActualizarExclusionCarteraInactivar(idPersona);
        service.execute();
        logger.info("Inicia método actualizarExclusionCarteraInactivar");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarDatosTemporalesCartera(java.lang.Long)
     */
    @Override
    public GestionCicloManualDTO consultarDatosTemporalesCartera(Long numeroOperacion) {
        GestionCicloManualDTO gestionCicloDTO = null;
        String firmaMetodo = "consultarDatosTemporalesCartera(String)";
        logger.debug("Inicia " + firmaMetodo);

        try {
            DatoTemporalCarteraModeloDTO datoTemporalDTO = consultarDatoTemporalCartera(numeroOperacion);

            if (datoTemporalDTO != null && datoTemporalDTO.getJsonPayload() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                gestionCicloDTO = objectMapper.readValue(datoTemporalDTO.getJsonPayload(), GestionCicloManualDTO.class);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error en " + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return gestionCicloDTO;
    }

    /**
     * Método que invoca el servicio de consulta de datos temporales de cartera
     */
    public DatoTemporalCarteraModeloDTO consultarDatoTemporalCartera(Long numeroOperacion) {
        logger.debug("Inicia método consultarDatoTemporalCartera");
        ConsultarDatoTemporalCartera service = new ConsultarDatoTemporalCartera(numeroOperacion);
        service.execute();
        logger.debug("Finaliza método consultarDatoTemporalCartera");
        return service.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#finalizarSolicitudGestionManual(java.lang.Long,
     * java.lang.Boolean, java.lang.Boolean,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void finalizarSolicitudGestionManual(Long numeroOperacion, Boolean tieneSolicitudManual, Boolean finalizar,
                                                UserDTO userDTO) {
        String firmaMetodo = "finalizarSolicitudGestionManual(String,Boolean,Boolean)";
        logger.debug("Inicia " + firmaMetodo);
        SolicitudGestionCobroManualModeloDTO solicitudGestionCobroManual = consultarSolicitudGestionCobroManual(null,
            numeroOperacion);
        if (tieneSolicitudManual && solicitudGestionCobroManual != null) {
            if (!finalizar) {
                actualizarEstadoSolicitudGestionCobroManual(solicitudGestionCobroManual.getNumeroRadicacion(),
                    EstadoFiscalizacionEnum.EXCLUIDA);
            } else {
                actualizarEstadoSolicitudGestionCobroManual(solicitudGestionCobroManual.getNumeroRadicacion(),
                    EstadoFiscalizacionEnum.FINALIZADA);
            }
        }
        if (tieneSolicitudManual && solicitudGestionCobroManual != null) {
            TareaDTO tarea = obtenerTareaActivaInstancia(
                Long.parseLong(solicitudGestionCobroManual.getIdInstanciaProceso()), false);
            reasignarTarea(tarea.getId(), userDTO.getNombreUsuario());
            terminarTarea(tarea.getId(), null);
            verificarCierreCiclos(TipoCicloEnum.GESTION_MANUAL, solicitudGestionCobroManual.getIdCicloAportante());
        }
        actualizarAgendasActividadesVisibles(numeroOperacion);
        logger.debug("Finaliza " + firmaMetodo);
    }

    private void actualizarAgendasActividadesVisibles(Long numeroOperacion) {
        ActualizarAgendasActividadesVisibles actualizar = new ActualizarAgendasActividadesVisibles(numeroOperacion);
        actualizar.execute();
    }

    /**
     * Método que invoca el servicio de consulta de un registro de cartera, por
     * número de operación
     *
     * @param numeroOperacion Número de operación
     * @return La información de cartera
     */
    private CarteraModeloDTO consultarInformacionCarteraPorNumeroOperacion(Long numeroOperacion) {
        logger.debug("Inicia método consultarCarteraNumeroOperacion");
        ConsultarInformacionCarteraPorNumeroOperacion service = new ConsultarInformacionCarteraPorNumeroOperacion(
            numeroOperacion);
        service.execute();
        logger.debug("Finaliza método consultarCarteraNumeroOperacion");
        return service.getResult();
    }

    /**
     * Metodo que invoca el cancelarProgramacionPorProceso de
     * process-shedule-service
     *
     * @param proceso
     */

    public void cancelarProgramacionPorProceso(ProcesoAutomaticoEnum proceso) {
        logger.debug("Inicio de método cancelarProgramacionPorProceso(ProcesoAutomaticoEnum proceso)");
        CancelarProgramacionPorProceso cancelar = new CancelarProgramacionPorProceso(proceso);
        logger.debug("Fin de método cancelarProgramacionPorProceso(ProcesoAutomaticoEnum proceso)");
        cancelar.execute();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * asignarAccionesCobro(com.asopagos.enumeraciones.cartera.
     * TipoAccionCobroEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void asignarAccionesCobro(TipoAccionCobroEnum accionCobro, UserDTO userDTO) {
        try {
            logger.debug("Inicia servicio asignarAccionesCobro");
            logger.info("Inicia servicio asignarAccionesCobro " + accionCobro + " userDTO " + userDTO);
            AsignacionSolicitudGestionCobroFactory.getInstance().crearAsignacion(accionCobro, userDTO).asignar();
            logger.debug("Finaliza servicio asignarAccionesCobro");
        } catch (Exception e) {
            logger.error("Error en servicio asignarAccionesCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public void asignarAccionesCobroNew(TipoAccionCobroEnum accionCobro, UserDTO userDTO, List<Long> idPersonasAProcesar) {
        try {
            logger.debug("Inicia servicio asignarAccionesCobroNew");
            logger.info("Inicia servicio asignarAccionesCobroNew " + accionCobro + " userDTO " + userDTO);
            AsignacionSolicitudGestionCobroFactory.getInstance().crearAsignacion(accionCobro, userDTO, idPersonasAProcesar).asignar();
            logger.debug("Finaliza servicio asignarAccionesCobroNew");
        } catch (Exception e) {
            logger.error("Error en servicio asignarAccionesCobroNew", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que consulta un archivo de acuerdo con el id del ECM
     *
     * @param archivoId Identificador del ECM
     * @return La información del archivo consultado
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia método obtenerArchivo");
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        InformacionArchivoDTO archivoMultiple = consultarArchivo.getResult();
        logger.debug("Finaliza método obtenerArchivo");
        return archivoMultiple;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * finalizarResultadosEdictos(com.asopagos.dto.cartera.
     * RegistroRemisionAportantesDTO)
     */
    @Override
    public void finalizarResultadosEdictos(RegistroRemisionAportantesDTO registroDTO, HttpHeaders header) {
        logger.debug("Inicio de método finalizarResultadosEdictos");

        String token = header.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Se obtine solo la parte asociada al token (del header proviene un string de
        // tipo [tipoAtorizacion][token])
        registroDTO.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));
        carteraAsincronoCompositeService.finalizarResultadosEdictosAsync(registroDTO);

        logger.debug("Fin de método finalizarResultadosEdictos");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#guardarNotificacionPersonal(com.asopagos.dto.modelo.NotificacionPersonalModeloDTO)
     */

    @Override
    public void guardarNotificacionPersonal(NotificacionPersonalModeloDTO notificacionPersonalDTO) {
        String firmaMetodo = "guardarNotificacionPersonal(NotificacionPersonalModeloDTO)";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonaModeloDTO persona = consultarDatosPersona(notificacionPersonalDTO.getTipoIdentificacion(), notificacionPersonalDTO.getNumeroIdentificacion());

            notificacionPersonalDTO.setIdPersona(persona.getIdPersona());
            logger.info("notificacionPersonalDTO:: " + objectMapper.writeValueAsString(notificacionPersonalDTO));
            List<DocumentoSoporteModeloDTO> listaDocumentoDTO = crearNotificacionPersonal(notificacionPersonalDTO);
            Long numeroOperacion = consultarNumeroOperacionCartera(notificacionPersonalDTO.getIdCartera());
            String bitacoraEnum = consultarFirmezaTituloBitacora(persona.getIdPersona());

            logger.info("bitacoraEnum:: " + bitacoraEnum);
            if (!bitacoraEnum.equals("FIRMEZA_TITULO_EJECUTIVO")) {
                logger.info("entra !FIRMEZA_TITULO_EJECUTIVO" + bitacoraEnum);
                TipoActividadBitacoraEnum tipo;
                ResultadoBitacoraCarteraEnum resultado;
                if (ActividadNotificacionCicloCarteraEnum.REGISTRAR_NOTIFICACION_PERSONAL_APORTANTE.equals(notificacionPersonalDTO.getActividad())) {
                    tipo = TipoActividadBitacoraEnum.REGISTRO_NOTIFICACION_PERSONAL;
                    resultado = ResultadoBitacoraCarteraEnum.NOTIFICADO_PERSONALMENTE;
                } else if (ActividadNotificacionCicloCarteraEnum.FIRMEZA_TITULO_EJECUTIVO
                    .equals(notificacionPersonalDTO.getActividad())) {// 36320
                    tipo = TipoActividadBitacoraEnum.FIRMEZA_TITULO_EJECUTIVO;
                    resultado = ResultadoBitacoraCarteraEnum.OTRO;
                    logger.info("notificacionPersonalDTO:"
                        + new ObjectMapper().writeValueAsString(notificacionPersonalDTO));
                    logger.info("persona:" + new ObjectMapper().writeValueAsString(persona));
                    actualizarDeudaRealPorIdCarteraService(persona.getNumeroIdentificacion());
                } else {
                    tipo = TipoActividadBitacoraEnum.OTRO;
                    resultado = ResultadoBitacoraCarteraEnum.OTRO;
                }
                almacenarBitacoraCartera(null, null, tipo, MedioCarteraEnum.PERSONAL, resultado, null,
                    persona.getIdPersona(), notificacionPersonalDTO.getTipoSolicitante(), listaDocumentoDTO,
                    numeroOperacion.toString(), notificacionPersonalDTO.getComentario());
            }
            logger.info("if FIRMEZA_TITULO_EJECUTIVO");
        } catch (Exception e) {
            logger.error("Ocurrió un error en " + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    public void crearFirmezaTituloBitacora(FirmezaDeTituloDTO firmeza) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("crearFirmezaTituloBitacora(NotificacionPersonalModeloDTO) :: " + objectMapper.writeValueAsString(firmeza));
            TipoActividadBitacoraEnum tipo;
            ResultadoBitacoraCarteraEnum resultado;
            tipo = TipoActividadBitacoraEnum.FIRMEZA_TITULO_EJECUTIVO;
            resultado = ResultadoBitacoraCarteraEnum.OTRO;
            logger.info("firmeza :: " + new ObjectMapper().writeValueAsString(firmeza));
            actualizarDeudaRealPorIdCarteraService(firmeza.getNumeroIdentificacion());
            almacenarBitacoraCartera(null, new Date().getTime(), tipo, MedioCarteraEnum.PERSONAL, resultado, null,
                firmeza.getIdPersona(), firmeza.getTipoSolicitante(), new ArrayList<>(),
                firmeza.getNumeroOperacion(), new String());
            logger.info("FIN crearFirmezaTituloBitacora(NotificacionPersonalModeloDTO) :: " + objectMapper.writeValueAsString(firmeza));
        } catch (Exception e) {
            logger.error("Ocurrió un error en " + "crearFirmezaTituloBitacora(NotificacionPersonalModeloDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    public void actualizarDeudaRealPorIdCarteraService(String numeroIdentificacion) {
        logger.info("Inicio de método actualizarDeudaRealPorIdCarteraService(idCartera)" + numeroIdentificacion);
        ActualizarDeudaRealPorIdCarteraServices actualizarDeudaRealPorIdCarteraServices = new ActualizarDeudaRealPorIdCarteraServices(
            numeroIdentificacion);
        actualizarDeudaRealPorIdCarteraServices.execute();
        logger.info("Finaliza de método actualizarDeudaRealPorIdCarteraService(idCartera)"
            + actualizarDeudaRealPorIdCarteraServices);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesGestionCobroManual(com.asopagos.cartera.dto
     * .
     * FiltrosGestionCobroManualDTO)
     */
    @Override
    public List<AportanteGestionManualDTO> consultarAportantesGestionCobroManual(FiltrosGestionCobroManualDTO filtros) {
        try {
            logger.debug(
                "Inicio de método consultarAportantesGestionCobroManual(FiltrosGestionCobroManualDTO filtros)");
            List<EstadoFiscalizacionEnum> estados = new ArrayList<>();
            List<TipoSolicitanteMovimientoAporteEnum> tipoSolicitantes = new ArrayList<>();
            List<Long> idsPersonas = new ArrayList<>();

            if (filtros.getEstado() != null) {
                estados.add(filtros.getEstado());
            } else {
                estados.add(EstadoFiscalizacionEnum.ASIGNADO);
                estados.add(EstadoFiscalizacionEnum.EN_PROCESO);
                estados.add(EstadoFiscalizacionEnum.EXCLUIDA);
                estados.add(EstadoFiscalizacionEnum.FINALIZADA);
            }

            if (filtros.getTipoSolicitante() != null) {
                tipoSolicitantes.add(filtros.getTipoSolicitante());
            } else {
                tipoSolicitantes.add(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
                tipoSolicitantes.add(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE);
                tipoSolicitantes.add(TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
            }

            if (filtros.getNumeroIdentificacion() != null || filtros.getTipoIdentificacion() != null
                || filtros.getPrimerNombre() != null || filtros.getPrimerApellido() != null
                || filtros.getSegundoNombre() != null || filtros.getSegundoApellido() != null
                || filtros.getRazonSocial() != null) {
                List<PersonaDTO> personas = buscarPersonasSinDetalle(filtros.getTipoIdentificacion(),
                    filtros.getNumeroIdentificacion(), filtros.getPrimerNombre(), filtros.getPrimerApellido(),
                    filtros.getSegundoNombre(), filtros.getSegundoApellido(), filtros.getRazonSocial());

                if (personas != null && !personas.isEmpty()) {
                    for (PersonaDTO personaDTO : personas) {
                        idsPersonas.add(personaDTO.getIdPersona());
                    }
                }

                if (idsPersonas.isEmpty()) {
                    idsPersonas.add(0L);
                }
            }

            ParametrosGestionCobroManualDTO parametros = new ParametrosGestionCobroManualDTO();
            parametros.setEstadosFiscalizacion(estados);
            parametros.setTipoSolicitantes(tipoSolicitantes);
            parametros.setIdsPersonas(idsPersonas);
            parametros.setEsSupervisor(filtros.getEsSupervisor());
            List<AportanteGestionManualDTO> aportantes = consultarAportantesGestionManual(parametros);

            for (AportanteGestionManualDTO aportanteGestionManualDTO : aportantes) {
                aportanteGestionManualDTO.setEstadoAfiliacion(EstadoAfiliadoEnum.ACTIVO);
            }
            logger.debug("Fin de método consultarAportantesGestionCobroManual(FiltrosGestionCobroManualDTO filtros)");
            return aportantes;
        } catch (Exception e) {
            logger.error(
                "Ocurrió un error en consultarAportantesGestionCobroManual(FiltrosGestionCobroManualDTO filtros)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /* Fin proceso 223 */

    /**
     * Método privado que crear la notificacion personal y la notificacion personal
     * documento
     *
     * @param notificacionPersonalDTO
     */
    private List<DocumentoSoporteModeloDTO> crearNotificacionPersonal(
        NotificacionPersonalModeloDTO notificacionPersonalDTO) {
        logger.debug("Inicia crearNotificacionPersonal");
        CrearNotificacionPersonal crearNotificacionPersonalService = new CrearNotificacionPersonal(
            notificacionPersonalDTO);
        crearNotificacionPersonalService.execute();
        logger.debug("Finaliza crearNotificacionPersonal");
        return crearNotificacionPersonalService.getResult();
    }

    /**
     * Método encargado de dar el estado del afiliado para cada uno de los detalles
     *
     * @param lstConsultaEstadoPersona, lista de estados de la persona
     * @param lstDetallesCiclo,         listado de detalles del afiliado
     * @return retorna la lista de detalles con el estado dado
     */
    private List<SimulacionDTO> obtenerEstadoAfiliado(List<ConsultarEstadoDTO> lstConsultaEstadoPersona,
                                                      List<SimulacionDTO> lstDetallesCiclo) {
        List<EstadoDTO> consultaEstadoCajaMasivo = consultarEstadoCaja(lstConsultaEstadoPersona);
        for (SimulacionDTO simulacionDTO : lstDetallesCiclo) {
            for (EstadoDTO consultaEstadoCajaPersonaDTO : consultaEstadoCajaMasivo) {
                if (simulacionDTO.getTipoIdentificacion().equals(consultaEstadoCajaPersonaDTO.getTipoIdentificacion())
                    && simulacionDTO.getNumeroIdentificacion()
                    .equals(consultaEstadoCajaPersonaDTO.getNumeroIdentificacion())) {
                    simulacionDTO.setEstadoAfiliado(consultaEstadoCajaPersonaDTO.getEstado());
                }
            }
        }
        return lstDetallesCiclo;
    }

    /**
     * Método encargado de invocar el cliente que consulta una solicitud
     * preventiva por el número de radicado
     *
     * @param numeroRadicacion, número de radicado por el cual se va a consultar
     * @return retorna la solicitud preventiva
     */
    private SolicitudPreventivaModeloDTO consultarSolicitudPreventiva(String numeroRadicacion) {
        logger.debug("Inicia consultarSolicitudPreventiva(String numeroRadicacion)");
        ConsultarSolicitudPreventiva buscarSolicitudPreventiva = new ConsultarSolicitudPreventiva(numeroRadicacion);
        buscarSolicitudPreventiva.execute();
        logger.debug("Finaliza consultarSolicitudPreventiva(String numeroRadicacion)");
        return buscarSolicitudPreventiva.getResult();
    }

    private AportantesTraspasoDeudaDTO consultarAportantesTraspasoDeudaAntigua(
        FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso) {
        logger.debug(
            "Inicia consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso)");
        ConsultarAportantesTraspasoDeudaAntigua consultarAportantesDeudaAntigua = new ConsultarAportantesTraspasoDeudaAntigua(
            aportantesTraspaso);
        consultarAportantesDeudaAntigua.execute();
        logger.debug(
            "Finaliza consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso)");
        return consultarAportantesDeudaAntigua.getResult();
    }

    /**
     * Método encargado de invocar el clietne que actualiza el estado de una
     * solicitud preventiva
     *
     * @param numeroRadicacion,          número de radicacion por de la solicitud a
     *                                   actualizar
     * @param estadoSolicitudPreventiva, estado de la solicitud preventiva a
     *                                   actualizar
     */
    private void actualizarEstadoSolicitudPreventiva(String numeroRadicacion,
                                                     EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva) {
        ActualizarEstadoSolicitudPreventiva actualizarEstadoSolicitud = new ActualizarEstadoSolicitudPreventiva(
            numeroRadicacion, estadoSolicitudPreventiva);
        actualizarEstadoSolicitud.execute();
    }

    /**
     * Método encargado de llamar el servicio de BPM que inicia un proceso
     *
     * @param proceso, proceso el cual se va a iniciar
     * @param params,  listado de parametros correspondientes al proceso
     * @return se devuelve el id de la instancia proceso
     */
    private Long iniciarProceso(ProcesoEnum proceso, Map<String, Object> params, Boolean automatico) {

        IniciarProceso iniciarProceso = new IniciarProceso(proceso, params);
        if (automatico) {
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            iniciarProceso.setToken(token.getToken());
        }
        iniciarProceso.execute();
        return iniciarProceso.getResult();
    }

    /**
     * Método que termina una tarea del BPM
     *
     * @param idTarea, es el identificador de la tarea
     * @param params,  son los parámetros de la tarea
     */
    private void terminarTarea(Long idTarea, Map<String, Object> params) {
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params == null ? new HashMap<>() : params);
        terminarTarea.execute();
    }

    /**
     * Método que asigna el usuario a cada una de las gestiones preventivas.
     *
     * @param aportantes     a asignarle el usuario de gestion preventiva.
     * @param user           Usuario tomado del contexto
     * @param usuariosOffset cantidad de usuarios que son omitidos en la consulta,
     *                       debido a la paginación de la funcionalidad
     * @return lista de los aportantes.
     */
    private List<SimulacionDTO> asignarUsuarioGestionPreventiva(List<SimulacionDTO> aportantes, UserDTO user,
                                                                Integer usuariosOffset) {
        logger.info("CarteraCompositeBusiness.asignarUsuarioGestionPreventiva--> " + aportantes + ":" + user + ":"
            + usuariosOffset);
        String ultimo = null;
        List<UsuarioDTO> usuarios = obtenerMiembrosGrupo(BAC_GES_PRE_MOR, user.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);

        // Procesamiento de offset de usuarios
        for (int i = 0; i < usuariosOffset; i++) {
            UsuarioDTO usuario = obtenerUsuarioConsecutivo(usuarios, ultimo);
            ultimo = usuario.getNombreUsuario();
        }

        for (SimulacionDTO simulacion : aportantes) {
            if (TipoGestionCarteraEnum.MANUAL.equals(simulacion.getTipoGestionPreventiva())) {
                UsuarioDTO usuario = obtenerUsuarioConsecutivo(usuarios, ultimo);
                if (usuario == null) {
                    logger.error("Ocurrió un error, no se encontró un usuario para el perfil " + BAC_GES_PRE_MOR);
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
                }
                ultimo = usuario.getNombreUsuario();
                StringBuilder usuarioAsignacion = new StringBuilder();
                usuarioAsignacion.append(usuario.getPrimerNombre());
                usuarioAsignacion.append(ESPACIO);
                usuarioAsignacion
                    .append(usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() + ESPACIO : "");
                usuarioAsignacion.append(usuario.getPrimerApellido());
                usuarioAsignacion.append(ESPACIO);
                usuarioAsignacion.append(usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : "");
                simulacion.setUsuarioAsignacion(usuarioAsignacion.toString());
                simulacion.setUsuarioAnalista(usuario.getNombreUsuario());

                logger.info("CarteraCompositeBusiness.asignarUsuarioGestionPreventiva-->:"
                    + Calendar.getInstance().getTime().getTime());
                // simulacion.setFechaLimitePago(Calendar.getInstance().getTime()); //i
                /*
                 * Se almacenan los usuarios a los cuales fueron asignados para acciones de
                 * gestion preventiva
                 */
                usuariosAsignadosPreventivaAgrupada.add(usuario.getNombreUsuario());

            }
        }
        return aportantes;
    }

    /**
     * Método encargado de registrar la gestión preventiva por casos
     *
     * @param resultadoGestionPreventivaDTO, resultado de la gestión Preventiva
     * @param numeroRadicacion,              número de la radiación de la gestión
     *                                       preventiva
     * @param solicitudPreventivaDTO,        solitudPreventivaDTO que contiene la
     *                                       solicitud
     */
    private void registrarGestionPorCasos(ResultadoGestionPreventivaDTO resultadoGestionPreventivaDTO,
                                          String numeroRadicacion, SolicitudPreventivaModeloDTO solicitudPreventivaDTO, UserDTO userDTO) {
        // Mapa de parametros perteneciente al proceso
        Map<String, Object> paramsProceso = new HashMap<>();
        List<UsuarioDTO> usuariosBackEmpleador = obtenerMiembrosGrupo(BAC_ACT_EMP, userDTO.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);
        List<UsuarioDTO> usuariosBackPersona = obtenerMiembrosGrupo(BAC_ACT_PER, userDTO.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);
        solicitudPreventivaDTO.setContactoEfectivo(resultadoGestionPreventivaDTO.getContactoEfectivo());
        solicitudPreventivaDTO.setRequiereFiscalizacion(resultadoGestionPreventivaDTO.getRequiereFiscalizacion());
        solicitudPreventivaDTO.setObservacion(resultadoGestionPreventivaDTO.getDescripcion());

        if (solicitudPreventivaDTO.getRequiereFiscalizacion() != null
            && solicitudPreventivaDTO.getRequiereFiscalizacion()) {
            solicitudPreventivaDTO.setFechaFiscalizacion(new Date().getTime());
        }

        if (resultadoGestionPreventivaDTO.getContactoEfectivo()) {
            /* si hubo contacto con el aportante la solicitud se cierra y es exitosa */
            solicitudPreventivaDTO.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.EXITOSA);

            // Persiste en BitacoraCartera
            guardarBitacoraPreventiva(solicitudPreventivaDTO, TipoActividadBitacoraEnum.CONTACTO_EFECTIVO,
                TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaDTO.getTipoGestionCartera())
                    ? MedioCarteraEnum.PERSONAL
                    : MedioCarteraEnum.ELECTRONICO);

            actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaDTO);
            actualizarEstadoSolicitudPreventiva(numeroRadicacion, EstadoSolicitudPreventivaEnum.CERRADA);

            // Persiste en BitacoraCartera
            guardarBitacoraPreventiva(solicitudPreventivaDTO, TipoActividadBitacoraEnum.CIERRE_PREVENTIVA,
                TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaDTO.getTipoGestionCartera())
                    ? MedioCarteraEnum.PERSONAL
                    : MedioCarteraEnum.ELECTRONICO);

            if (TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaDTO.getTipoGestionCartera())) {
                List<Long> idSolicitudAgrupada = new ArrayList<>();
                idSolicitudAgrupada.add(solicitudPreventivaDTO.getIdSolicitudPreventivaAgrupadora());
                List<SolicitudPreventivaAgrupadoraModeloDTO> idInstanciasProceso = validarCierreSolicitudAgrupadora(
                    idSolicitudAgrupada);
                if (!idInstanciasProceso.isEmpty()) {
                    for (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO : idInstanciasProceso) {
                        TareaDTO tareaDTO = obtenerTareaActivaInstancia(
                            Long.parseLong(solicitudPreventivaAgrupadoraModeloDTO.getIdInstanciaProceso()), false);

                        if (tareaDTO != null) {
                            terminarTarea(tareaDTO.getId(), null);
                        }
                    }
                }
            }
        } else {
            /*
             * se obtiene el ultimo back de actualización asignado para realizar por turnos
             * la asignación
             */
            String ultimoUsuario = obtenerUltimoBackAsignado(solicitudPreventivaDTO.getTipoSolicitante(),
                TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA);
            UsuarioDTO usuarioDTO = null;
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(solicitudPreventivaDTO.getTipoSolicitante())) {
                usuarioDTO = obtenerUsuarioConsecutivo(usuariosBackEmpleador, ultimoUsuario);
            } else {
                usuarioDTO = obtenerUsuarioConsecutivo(usuariosBackPersona, ultimoUsuario);
            }
            if (usuarioDTO != null) {
                paramsProceso.put(BACK_ACTUALIZACION, usuarioDTO.getNombreUsuario());
                /* inicio mantis 229529 */
                solicitudPreventivaDTO.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.NO_EXITOSA);
                actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaDTO);
                /* fin mantis 229529 */
                /* si no hubo contacto con el aportante se debe actualizar la información */
                solicitudPreventivaDTO
                    .setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.PENDIENTE_ACTUALIZACION);
                solicitudPreventivaDTO.setBackActualizacion(usuarioDTO.getNombreUsuario());

                // Persiste en BitacoraCartera
                guardarBitacoraPreventiva(solicitudPreventivaDTO, TipoActividadBitacoraEnum.CONTACTO_NO_EFECTIVO,
                    TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaDTO.getTipoGestionCartera())
                        ? MedioCarteraEnum.PERSONAL
                        : MedioCarteraEnum.ELECTRONICO);
            }
            actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaDTO);

            if (TipoGestionCarteraEnum.MANUAL.equals(solicitudPreventivaDTO.getTipoGestionCartera())) {

                /* se crea tarea invidual para la solcitud preventiva */
                paramsProceso.put(ID_SOLICITUD, solicitudPreventivaDTO.getIdSolicitud());
                paramsProceso.put(NUMERO_RADICADO, numeroRadicacion);
                Long idInstanciaProceso = iniciarProceso(ProcesoEnum.GESTION_PREVENTIVA_CARTERA_ACTUALIZACION,
                    paramsProceso, Boolean.FALSE);
                solicitudPreventivaDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
                actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaDTO);
            }

        }
        paramsProceso.put(CONTACTO_EFECTIVO, resultadoGestionPreventivaDTO.getContactoEfectivo());

        if (TipoGestionCarteraEnum.AUTOMATICA.equals(solicitudPreventivaDTO.getTipoGestionCartera())
            && !resultadoGestionPreventivaDTO.getContactoEfectivo()) {
            /* si la gestion es automática y no hubo contacto se inicia el proceso */
            paramsProceso.put(ID_SOLICITUD, solicitudPreventivaDTO.getIdSolicitud());
            paramsProceso.put(NUMERO_RADICADO, numeroRadicacion);
            Long idInstanciaProceso = iniciarProceso(ProcesoEnum.GESTION_PREVENTIVA_CARTERA_ACTUALIZACION,
                paramsProceso, Boolean.TRUE);
            solicitudPreventivaDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
            actualizarSolicitudPreventiva(numeroRadicacion, solicitudPreventivaDTO);
        }

    }

    /**
     * Método encargado de construir una solicitud preventiva a partir de los datos
     * simulados.
     *
     * @param simulacionDTO datos de una simulación.
     * @param userDTO       usuario que esta realizando la solicitud.
     * @return solicitud preventiva construida.
     */
    private SolicitudPreventivaModeloDTO construirSolicitudPreventiva(SimulacionDTO simulacionDTO, UserDTO userDTO) {
        logger.debug("Inicio de método construirSolicitudPreventiva(SimulacionDTO simulacionDTO, UserDTO userDTO)");
        SolicitudPreventivaModeloDTO solicitudPreventiva = new SolicitudPreventivaModeloDTO();
        solicitudPreventiva.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudPreventiva.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudPreventiva.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.ASIGNADA);
        solicitudPreventiva.setFechaCreacion(new Date().getTime());
        solicitudPreventiva.setFechaRadicacion(new Date().getTime());
        PersonaModeloDTO personaDTO = consultarDatosPersona(simulacionDTO.getTipoIdentificacion(),
            simulacionDTO.getNumeroIdentificacion());
        solicitudPreventiva.setIdPersona(personaDTO.getIdPersona());
        solicitudPreventiva.setTipoTransaccion(TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA);
        solicitudPreventiva.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudPreventiva.setTipoSolicitante(simulacionDTO.getTipoAportante());
        solicitudPreventiva.setTipoGestionCartera(simulacionDTO.getTipoGestionPreventiva());
        /* nuevos campo del control de cambio para hu 160 161 162 */
        solicitudPreventiva.setEstadoActualCartera(simulacionDTO.getEstadoActualCartera());
        solicitudPreventiva.setValorPromedioAportes(simulacionDTO.getValorPromedioAportes());
        solicitudPreventiva.setTrabajadoresActivos(
            simulacionDTO.getTrabajadoresActivos() != null ? simulacionDTO.getTrabajadoresActivos().shortValue()
                : null);
        solicitudPreventiva.setCantidadVecesMoroso(simulacionDTO.getCantidadVecesMoroso());
        logger.debug("Inicio de método construirSolicitudPreventiva(SimulacionDTO simulacionDTO, UserDTO userDTO)");
        return solicitudPreventiva;

    }

    /**
     * Servicio encargado de consultar a una persona por tipo y número de
     * identificación.
     *
     * @param tipoIdentificacion   tipo de identificación.
     * @param numeroIdentificacion número de identificación.
     * @return persona encontrada.
     */
    private PersonaModeloDTO consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion,
                                                   String numeroIdentificacion) {
        logger.debug(
            "Inicio de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarDatosPersona consultarDatosService = new ConsultarDatosPersona(numeroIdentificacion,
            tipoIdentificacion);
        consultarDatosService.execute();
        PersonaModeloDTO personaModeloDTO = consultarDatosService.getResult();
        logger.debug(
            "Fin de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return personaModeloDTO;

    }

    /**
     * Método encargado de invocar el servicio que guarda o acutaliza una solicitud
     * preventiva.
     *
     * @param solicitudPreventivaDTO solicitud preventiva a guardar.
     * @return id de la solicitud.
     */
    private Long guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO) {
        logger.debug(
            "Inicio de método guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO)");
        GuardarSolicitudPreventiva guardarSolicitudPreventivaService = new GuardarSolicitudPreventiva(
            solicitudPreventivaDTO);
        guardarSolicitudPreventivaService.execute();
        Long idSolicitud = guardarSolicitudPreventivaService.getResult();
        logger.debug(
            "Inicio de método guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO)");
        return idSolicitud;
    }

    /**
     * Método que hace la peticion REST al servicio de generar número de
     * radicado
     *
     * @param idSolicitud          <code>Long</code> El identificador de la
     *                             solicitud
     * @param sedeCajaCompensacion <code>String</code> El usuario del sistema
     * @return número de radicación.
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        String numeroRadicacion = radicarSolicitudService.getResult();
        logger.debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        return numeroRadicacion;
    }

    /**
     * Método encargado de llamar el cliente
     *
     * @param numeroRadicacion,       número de radicación perteneciente a la
     *                                solicitud
     * @param solicitudPreventivaDTO, solicitud preventiva dto
     */
    private void actualizarSolicitudPreventiva(String numeroRadicacion,
                                               SolicitudPreventivaModeloDTO solicitudPreventivaDTO) {
        ActualizarSolicitudPreventiva actualizacion = new ActualizarSolicitudPreventiva(numeroRadicacion,
            solicitudPreventivaDTO);
        actualizacion.execute();
    }

    /**
     * Método encargado de llamar el cliente del servicio envio de correo
     * parametrizado
     *
     * @param notificacion, notificación dto que contiene la información del correo
     */
    private void enviarCorreoParametrizadoAsincrono(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia enviarCorreoParametrizadoAsincrono(NotificacionParametrizadaDTO)");
        EnviarNotificacionComunicadoAsincrono enviarComunicado = new EnviarNotificacionComunicadoAsincrono(
            notificacion);
        enviarComunicado.execute();
        logger.debug("Finaliza enviarCorreoParametrizadoAsincrono(NotificacionParametrizadaDTO)");
    }

    /**
     * Método encargado de llamar el cliente del servicio envio de correo
     * parametrizado
     *
     * @param notificacion, notificación dto que contiene la información del correo
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
        logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
    }

    /**
     * Método que consulta y obtiene los analistas
     *
     * @param user Usuario de la caja de compensación
     * @return List<UsuarioDTO> Lista de usuarios analistas
     */
    private List<UsuarioDTO> obtenerMiembrosGrupo(String idGrupo, String sede, EstadoUsuarioEnum estado) {
        logger.debug("Inicia obtenerMiembrosGrupo(String idGrupo, String sede, EstadoUsuarioEnum estado)");
        ObtenerMiembrosGrupo obtenerMiembrosGrupoService = new ObtenerMiembrosGrupo(idGrupo, sede, estado);
        obtenerMiembrosGrupoService.execute();
        List<UsuarioDTO> usuarios = obtenerMiembrosGrupoService.getResult();
        logger.debug("Finaliza distribuirAnalistas(UserDTO user)");
        return usuarios;
    }

    /* privados bryan */

    /**
     * Método que consulta consulta la informacion de la persona o empleador que se
     * le va a
     * aplicar el cierre de convenio.
     *
     * @return lista de los convenios cerrados.
     */
    private DatosComunicadoCierreConvenioDTO consultarInformacionCierreConvenio(Long idPersona,
                                                                                TipoSolicitanteMovimientoAporteEnum tipo) {
        logger.debug("Inicio de método consultarInformacionCierreConvenio");
        ConsultarInformacionCierreConvenio convenio = new ConsultarInformacionCierreConvenio(idPersona, tipo);
        convenio.execute();
        logger.debug("Fin de método consultarInformacionCierreConvenio");
        return convenio.getResult();
    }

    /**
     * Método encargado de construir una notificación parametrizada.
     *
     * @param solicitudPreventiva solicitud preventiva.
     * @param intento,            si es el primero o segundo intento de
     *                            notificación.
     * @param idTarea,            id de la tarea a terminar.
     * @return notificación armada.
     */
    private NotificacionParametrizadaDTO construirComunicado(DatosComunicadoCierreConvenioDTO cierreConvenioDTO) {
        logger.debug(
            "Inicio de método construirNotificacionParametrizada(SolicitudPreventivaModeloDTO solicitudPreventiva)");
        NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
        Map<String, String> params = new HashMap<>();

        /* Se llena con información Empleador */
        params.put("fechaDelSistema", new Date().toString());
        params.put("nombreYApellidosRepresentanteLegal", cierreConvenioDTO.getNombreCompleto());
        params.put("razonSocial/Nombre", cierreConvenioDTO.getRazonSocial());
        params.put("direccionRepresentanteLegal", cierreConvenioDTO.getDireccionRepresentanteLegal());
        params.put("telefonoRepresentanteLegal", cierreConvenioDTO.getTelefonoRepresentanteLegal());
        params.put("ciudadRepresentanteLegal", cierreConvenioDTO.getCiudadRepresentanteLegal());
        params.put("numeroIdentificacionRepresentanteLegal", cierreConvenioDTO.getNumeroIdentificacion());
        params.put("tipoIdentificacionRepresentanteLegal", cierreConvenioDTO.getTipoIdentificacion().name());
        params.put("numeroConvenio", cierreConvenioDTO.getNumeroConvenio().toString());
        notificacion.setParams(params);
        notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.CRR_STC_CNV_PAG);
        notificacion.setProcesoEvento("CONVENIO_PAGO");
        notificacion.setTipoTx(TipoTransaccionEnum.CONVENIO_PAGO);
        // Se setea el id del empleador
        notificacion
            .setIdEmpleador(cierreConvenioDTO.getIdEmpleador() != null ? cierreConvenioDTO.getIdEmpleador() : null);
        ParametrosComunicadoDTO parametros = new ParametrosComunicadoDTO();
        parametros.setTipoIdentificacion(cierreConvenioDTO.getTipoIdentificacion());
        parametros.setNumeroIdentificacion(cierreConvenioDTO.getNumeroIdentificacion());
        notificacion.setParametros(parametros);
        logger.debug(
            "Fin de método construirNotificacionParametrizada(SolicitudPreventivaModeloDTO solicitudPreventiva)");
        return notificacion;
    }

    /**
     * Método que consulta los convenios que ya pueden ser cerrados.
     *
     * @return lista de los convenios cerrados.
     */
    private List<ConvenioPagoModeloDTO> consultarConveniosCierre(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarConveniosCierre");
        ConsultarConveniosCierre consultarConveniosCierre = new ConsultarConveniosCierre(diasFestivos);
        consultarConveniosCierre.execute();
        logger.debug("Fin de método consultarConveniosCierre");
        return consultarConveniosCierre.getResult();
    }

    /**
     * Método que se encarga de consultar los convenios que ya deben ser notificados
     * de cierre.
     *
     * @return lista de los convenios notificados de cierre.
     */
    private List<ConvenioPagoModeloDTO> consultarConveniosComunicado(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarConveniosComunicado");
        ConsultarConveniosComunicado consultarConveniosComunicado = new ConsultarConveniosComunicado(diasFestivos);
        consultarConveniosComunicado.execute();
        logger.debug("Fin de método consultarConveniosComunicado");
        return consultarConveniosComunicado.getResult();
    }

    /**
     * Metodo que invoca el clinte ConsultarListasValores de la clase ListasBusiness
     *
     * @param idsListaValores el id de la lista de valores por el que se va a buscar
     * @return una lista de ElementoListaDTO
     */
    private List<ElementoListaDTO> consultarListasValores(List<Integer> idsListaValores) {
        logger.debug("Inicia consultarListasValores (List<Integer> idsListaValores)");
        /* Se instancia el cliente */
        ConsultarListasValores listasValores = new ConsultarListasValores(idsListaValores);
        listasValores.execute();
        /* Se almacena la informacion de consultarListasValores */
        logger.debug("Finaliza consultarListasValores (List<Integer> idsListaValores)");
        return listasValores.getResult();
    }

    /**
     * Metodo que invoca al servicio de cartera business para traer los aportantes
     * del ciclo actual
     *
     * @param listAnalistas lista de analistas
     * @param enums         lista de los estados de solicitud de fiscalizacion
     * @param idPersona     identificador de la persona
     * @return
     */

    private List<SimulacionDTO> consultarAportantesCiclo(List<String> listAnalistas,
                                                         List<EstadoFiscalizacionEnum> enums, Long idPersona) {
        logger.debug("Inicia distribuirAnalistas(UserDTO user)");
        ConsultarAportantesCiclo consultarAportantesCiclo = new ConsultarAportantesCiclo(idPersona, enums,
            listAnalistas);
        consultarAportantesCiclo.execute();
        logger.debug("Inicia distribuirAnalistas(UserDTO user)");
        return consultarAportantesCiclo.getResult();

    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     *
     * @param nombreUsuarioCaja <code>String</code> El nombre de usuario del
     *                          funcionario de la
     *                          caja que realiza la consulta
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     * usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.debug("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
            nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        logger.debug("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return obtenerDatosUsuariosCajaCompensacionService.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     *
     * @param sedeCaja    <code>Long</code> el identificador del afiliado
     * @param procesoEnum <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     * caja
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        return ejecutarAsignacion.getResult();
    }

    /**
     * Método que llama al micro servicio de cartera consultarSolicitudFiscalizacion
     *
     * @param numeroRadicacion recibe como parametro el numero de radicacion de la
     *                         solicitud
     * @return un objeto tipo
     */
    private SolicitudFiscalizacionModeloDTO consultarSolicitudFiscalizacion(String numeroRadicacion) {
        logger.debug("Inicio de método consultarSolicitudFiscalizacion(String numeroRadicacion)");
        ConsultarSolicitudFiscalizacion consultarSolicitudFiscalizacion = new ConsultarSolicitudFiscalizacion(
            numeroRadicacion);
        consultarSolicitudFiscalizacion.execute();
        SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion.getResult();
        logger.debug("Fin de método consultarSolicitudFiscalizacion(String numeroRadicacion)");
        return solicitudFiscalizacionModeloDTO;
    }

    /**
     * Método encargado de invocar el servicio de guardar el ciclo de
     * fiscalización.
     *
     * @param cicloFiscalizacionDTO ciclo de fiscalización a guardar.
     * @return ciclo de fiscalización guardado.
     */
    private CicloCarteraModeloDTO guardarCicloFiscalizacion(CicloCarteraModeloDTO cicloFiscalizacionDTO) {
        logger.debug("Inicio de método guardarCicloFiscalizacion(CicloFiscalizacionModeloDTO cicloFiscalizacionDTO)");
        GuardarCicloCartera guardarCicloFiscalizacionService = new GuardarCicloCartera(cicloFiscalizacionDTO);
        guardarCicloFiscalizacionService.execute();
        logger.debug("Fin de método guardarCicloFiscalizacion(CicloFiscalizacionModeloDTO cicloFiscalizacionDTO)");
        return guardarCicloFiscalizacionService.getResult();
    }

    /**
     * Método que llama al micro servicio de cartera
     * actualizarEstadoSolicitudFiscalizacion
     *
     * @param numeroRadicacion    recibe como parametro el numero de radicacion de
     *                            la solicitud
     * @param estadoFiscalizacion recibe como parametro el estado de la solicitud
     *                            fiscalizacion
     */
    private void actualizarEstadoSolicitudFiscalizacion(String numeroRadicacion,
                                                        EstadoFiscalizacionEnum estadoFiscalizacion) {
        ActualizarEstadoSolicitudFiscalizacion actualizarEstadoSolicitudFiscalizacion = new ActualizarEstadoSolicitudFiscalizacion(
            numeroRadicacion, estadoFiscalizacion);
        /* Se actualiza el estado de la solicitud de fiscalización */
        actualizarEstadoSolicitudFiscalizacion.execute();
    }

    /**
     * Método que invoca el servicio que consulta los datos temporales.
     *
     * @param idSolicitud id de la solicitud global.
     * @return jsonPayload con los datos temporales.
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        logger.debug("Inicio de método consultarDatosTemporales(Long idSolicitud)");
        ConsultarDatosTemporales consultarDatosNovedad = new ConsultarDatosTemporales(idSolicitud);
        consultarDatosNovedad.execute();
        logger.debug("Fin de método consultarDatosTemporales(Long idSolicitud)");
        return consultarDatosNovedad.getResult();
    }

    /**
     * Metodo que permite consutar el ciclo de fiscalizaicon actual
     *
     * @return objeto tipo CicloFiscalizacionModeloDTO
     */
    private CicloCarteraModeloDTO consultarCicloFiscalizacionActual() {
        logger.debug("Inicia consultarCicloFiscalizacionActual()");
        ConsultarCicloFiscalizacionActual cicloFiscalizacionActual = new ConsultarCicloFiscalizacionActual();
        cicloFiscalizacionActual.execute();
        logger.debug("Finaliza consultarCicloFiscalizacionActual()");
        return cicloFiscalizacionActual.getResult();
    }

    /**
     * Metodo que sirve para cancelar el ciclo de fiscalizacion
     *
     * @param cicloFiscalizacionModeloDTO recibe el objeto
     *                                    CicloFiscalizacionModeloDTO
     */
    private void actualizarCiclo(CicloCarteraModeloDTO cicloFiscalizacionModeloDTO) {
        logger.debug("Inicia cancelarCiclo(CicloFiscalizacionModeloDTO cicloFiscalizacionModeloDTO)");
        CancelarCiclo cancelar = new CancelarCiclo(cicloFiscalizacionModeloDTO);
        cancelar.execute();
        logger.debug("Finaliza cancelarCiclo(CicloFiscalizacionModeloDTO cicloFiscalizacionModeloDTO)");
    }

    /**
     * Metodo que sirve para guardar la solicitud de fiscalizacion
     *
     * @param solicitudFiscalizacionDTO objeto de tipo solicitudFiscalizacionDTO
     * @return el id de la solicitud de fiscalizacion
     */
    private Long guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO) {
        logger.debug("Inicia guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)");
        GuardarSolicitudFiscalizacion guardar = new GuardarSolicitudFiscalizacion(solicitudFiscalizacionDTO);
        guardar.execute();
        logger.debug(
            "Finaliza guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)");
        return guardar.getResult();

    }

    /**
     * Metodo que se encarga de invocar el servicio que guarda la solicitud de
     * gestión manual.
     *
     * @param solicitudGestionManual objeto de tipo solicitudGestionManual.
     * @return el id de la solicitud de gestión manual.
     */
    private Long guardarSolicitudGestionCobroManual(SolicitudGestionCobroManualModeloDTO solicitudGestionManual) {
        logger.debug(
            "Inicia guardarSolicitudGestionCobroManual(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)");
        GuardarSolicitudGestionCobroManual guardarService = new GuardarSolicitudGestionCobroManual(
            solicitudGestionManual);
        guardarService.execute();
        logger.debug(
            "Finaliza guardarSolicitudGestionCobroManual(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)");
        return guardarService.getResult();

    }

    /**
     * Metodo que construye un objeto SolicitudFiscalizacionModeloDTO seteando sus
     * valores
     *
     * @param idCicloAportante se envia por parametro el id del ciclo aportante
     * @param userDTO          se envia por parametro el usuario
     * @return un objeto tipo SolicitudFiscalizacionModeloDTO
     */
    private SolicitudFiscalizacionModeloDTO construirSolicitudFiscalizacionModeloDTO(Long idCicloAportante,
                                                                                     UserDTO userDTO) {
        logger.debug("Inicia construirSolicitudFiscalizacionModeloDTO(Long idCicloAportante, UserDTO userDTO)");
        SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = new SolicitudFiscalizacionModeloDTO();
        solicitudFiscalizacionModeloDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudFiscalizacionModeloDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudFiscalizacionModeloDTO.setEstadoFiscalizacion(EstadoFiscalizacionEnum.ASIGNADO);
        solicitudFiscalizacionModeloDTO.setFechaCreacion(new Date().getTime());
        solicitudFiscalizacionModeloDTO.setFechaRadicacion(new Date().getTime());
        solicitudFiscalizacionModeloDTO.setTipoTransaccion(TipoTransaccionEnum.FISCALIZACION_CARTERA);
        solicitudFiscalizacionModeloDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudFiscalizacionModeloDTO.setIdCicloAportante(idCicloAportante);
        logger.debug("Finaliza construirSolicitudFiscalizacionModeloDTO(Long idCicloAportante, UserDTO userDTO)");
        return solicitudFiscalizacionModeloDTO;
    }

    /**
     * Metodo que construye un objeto SolicitudFiscalizacionModeloDTO seteando sus
     * valores
     *
     * @param idCicloAportante se envia por parametro el id del ciclo aportante
     * @param userDTO          se envia por parametro el usuario
     * @return un objeto tipo SolicitudFiscalizacionModeloDTO
     */
    private SolicitudGestionCobroManualModeloDTO construirSolicitudGestionCobroManualModeloDTO(Long idCicloAportante,
                                                                                               TipoLineaCobroEnum lineaCobro, UserDTO userDTO) {
        logger.debug("Inicia construirSolicitudGestionCobroManualModeloDTO(Long idCicloAportante, UserDTO userDTO)");
        SolicitudGestionCobroManualModeloDTO solicitudGestionManualDTO = new SolicitudGestionCobroManualModeloDTO();
        solicitudGestionManualDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudGestionManualDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudGestionManualDTO.setEstadoSolicitud(EstadoFiscalizacionEnum.ASIGNADO);
        solicitudGestionManualDTO.setFechaCreacion(new Date().getTime());
        solicitudGestionManualDTO.setFechaRadicacion(new Date().getTime());
        solicitudGestionManualDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_COBRO_MANUAL);
        solicitudGestionManualDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudGestionManualDTO.setIdCicloAportante(idCicloAportante);
        solicitudGestionManualDTO.setLineaCobro(lineaCobro);
        logger.debug("Finaliza construirSolicitudGestionCobroManualModeloDTO(Long idCicloAportante, UserDTO userDTO)");
        return solicitudGestionManualDTO;
    }

    /**
     * Metodo que sirve para verificar si hay un ciclo activo
     *
     * @return
     */
    private Boolean verificarCicloFiscalizacionNoActivo() {
        logger.debug("Inicia verificarCicloFiscalizacionNoActivo()");
        VerificarCicloFiscalizacionNoActivo noActivo = new VerificarCicloFiscalizacionNoActivo();
        noActivo.execute();
        logger.debug("Finaliza verificarCicloFiscalizacionNoActivo()");
        return noActivo.getResult();
    }

    /**
     * método que se encarga de abortar un proceso relacionado con el id de
     * instancia
     */
    private void abortarProcesos(ProcesoEnum proceso, List<Long> idInstanciaProceso, Boolean automatico) {
        logger.debug("Inicia abortarProceso(ProcesoEnum proceso, Long idInstanciaProceso)");
        AbortarProcesos abortarProceso = new AbortarProcesos(proceso, idInstanciaProceso);
        if (automatico) {
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            abortarProceso.setToken(token.getToken());
        }
        abortarProceso.execute();

        logger.debug("Finaliza abortarProceso(ProcesoEnum proceso, Long idInstanciaProceso)");
    }

    /**
     * Metodo que invoca servicio de guardar el DetalleSolicitudGestionCobro
     *
     * @param detalleSolicitudGestionCobroModeloDTO DTO con la informacion
     *                                              relacionado al detalle
     */
    private DetalleSolicitudGestionCobroModeloDTO guardarDetalleSolicitudGestionCobro(
        DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO, Long idSolicitudGlobal) {
        String fimaMetodo = "guardarDetalleSolicitudGestionCobro(DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO)";
        logger.debug("Inicia " + fimaMetodo);
        GuardarDetalleSolicitudGestionCobroFisico guardar = new GuardarDetalleSolicitudGestionCobroFisico(
            idSolicitudGlobal, detalleSolicitudGestionCobroModeloDTO);
        guardar.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return guardar.getResult();
    }

    /**
     * Metodo que invoca servicio de guardar el DetalleSolicitudGestionCobro
     *
     * @param detalleSolicitudGestionCobroModeloDTO DTO con la informacion
     *                                              relacionado al detalle
     */
    private void guardarListaDetalleSolicitudGestionCobro(
        List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO,
        Long idSolicitudGlobal) {
        String fimaMetodo = "guardarListaDetalleSolicitudGestionCobro(Lizt<DetalleSolicitudGestionCobroModeloDTO>)";
        logger.debug("Inicia " + fimaMetodo);
        GuardarDetallesSolicitudGestionCobroFisico guardar = new GuardarDetallesSolicitudGestionCobroFisico(
            idSolicitudGlobal, detallesSolicitudGestionCobroModeloDTO);
        guardar.execute();
        logger.debug("Finaliza " + fimaMetodo);
    }

    /**
     * Metodo que invoca al servicio consultarUltimoDestinatarioSolicitud
     *
     * @return el ultimo usuario destinatario de la tabla solicitud
     */
    private String consultarUltimoDestinatarioSolicitud() {
        String fimaMetodo = "consultarUltimoDestinatarioSolicitud()";
        logger.debug("Inicia " + fimaMetodo);
        ConsultarUltimoDestinatarioSolicitud destinatarioSolicitud = new ConsultarUltimoDestinatarioSolicitud();
        destinatarioSolicitud.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return destinatarioSolicitud.getResult();
    }

    /**
     * Metodo que invoca al servicio consultarDetalleSolicitudGestionCobro
     *
     * @param filtroDetalleSolicitudGestion recibe de parametro el dto
     * @return una lista de DetalleSolicitudGestionCobroModeloDTO
     */
    private List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleSolicitudGestionCobro(
        List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion) {
        String fimaMetodo = "consultarDetalleSolicitudGestionCobro(List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion)";
        logger.debug("Inicia " + fimaMetodo);
        ConsultarDetalleSolicitudGestionCobro consultarDetalleSolicitudGestionCobro = new ConsultarDetalleSolicitudGestionCobro(
            filtroDetalleSolicitudGestion);
        consultarDetalleSolicitudGestionCobro.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return consultarDetalleSolicitudGestionCobro.getResult();
    }

    /**
     * Metodo que invoca al servicio consultarSolicitudGestionCobroPorId
     *
     * @param idSolicitudPrimeraRemision es relacionado con el
     *                                   DetalleSolicitudGestionCobro
     * @return un objeto SolicitudGestionCobroFisicoModeloDTO
     */
    private SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision) {
        String fimaMetodo = "consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision)";
        logger.debug("Inicia " + fimaMetodo);
        ConsultarSolicitudGestionCobroPorId consultarSolicitudGestionCobroPorId = new ConsultarSolicitudGestionCobroPorId(
            idSolicitudPrimeraRemision);
        consultarSolicitudGestionCobroPorId.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return consultarSolicitudGestionCobroPorId.getResult();

    }

    /**
     * Método generico que se encarga de consultar el estado de caja ya sea para
     * persona o empleador
     *
     * @return lista de EstadoDTO
     */
    private List<ConsultarEstadoDTO> construirEstadoCaja(TipoIdentificacionEnum tipoIdentificacion,
                                                         String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {

        ConsultarEstadoDTO parametros = new ConsultarEstadoDTO();
        List<ConsultarEstadoDTO> consultarEstadoDTOs = new ArrayList<>();
        parametros.setEntityManager(entityManager);
        parametros.setNumeroIdentificacion(numeroIdentificacion);
        parametros.setTipoIdentificacion(tipoIdentificacion);
        /* valida si el tipo de solicitante es empleador */
        if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)) {
            parametros.setTipoPersona(ConstantesComunes.EMPLEADORES);
            consultarEstadoDTOs.add(parametros);
        } else {
            parametros.setTipoPersona(ConstantesComunes.PERSONAS);
            consultarEstadoDTOs.add(parametros);
        }
        return consultarEstadoDTOs;
    }

    /**
     * Metodo que invoca al servicio consultarSolicitudDesafiliacion
     *
     * @param numeroRadicacion relacionado a la solicitud de desafiliacion
     * @return un objeto SolicitudDesafiliacionModeloDTO con la informacion asociada
     */
    private SolicitudDesafiliacionModeloDTO consultarSolicitudDesafiliacion(String numeroRadicacion) {
        String fimaMetodo = "consultarSolicitudDesafiliacion(String numeroRadicacion)";
        logger.debug("Inicia " + fimaMetodo);
        ConsultarSolicitudDesafiliacion consultarSolicitudDesafiliacion = new ConsultarSolicitudDesafiliacion(
            numeroRadicacion);
        consultarSolicitudDesafiliacion.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return consultarSolicitudDesafiliacion.getResult();
    }

    /**
     * Metodo que guarda la informacion de la solcitud de desafiliacion
     *
     * @param solicitudDesafiliacionModeloDTO objeto con la informacion de la
     *                                        solicitud de desafiliacion
     */
    private SolicitudDesafiliacionModeloDTO guardarSolicitudDesafiliacion(
        SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO) {
        String fimaMetodo = "guardarSolicitudDesafiliacion(SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO)";
        logger.debug("Inicia " + fimaMetodo);
        GuardarSolicitudDesafiliacion guardarSolicitudDesafiliacion = new GuardarSolicitudDesafiliacion(
            solicitudDesafiliacionModeloDTO);
        guardarSolicitudDesafiliacion.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return guardarSolicitudDesafiliacion.getResult();
    }

    /**
     * Método que construye una solicitud de desafiliacion para aportantes
     *
     * @param userDTO         Información del usuario
     * @param tipoSolicitante de los aportantes
     * @return Objeto <code>SolicitudDesafiliacionModeloDTO</code> con la
     * información de la solicitud
     */
    private SolicitudDesafiliacionModeloDTO construirSolicitudDesafiliacion(UserDTO userDTO,
                                                                            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug(
            "Inicio de método construirSolicitudDesafiliacion(UserDTO userDTO,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        SolicitudDesafiliacionModeloDTO solicitudDesafiliacionDTO = new SolicitudDesafiliacionModeloDTO();
        solicitudDesafiliacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudDesafiliacionDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudDesafiliacionDTO.setFechaCreacion(new Date().getTime());
        solicitudDesafiliacionDTO.setFechaRadicacion(new Date().getTime());
        /*
         * Se valida tipo de solicitante para saber que tipo de transaccion va tener la
         * solicitud
         * en cuestion
         */
        switch (tipoSolicitante) {
            case EMPLEADOR:
                solicitudDesafiliacionDTO.setTipoTransaccion(TipoTransaccionEnum.DESAFILIACION_EMPLEADORES);
                break;
            case INDEPENDIENTE:
                solicitudDesafiliacionDTO.setTipoTransaccion(TipoTransaccionEnum.DESAFILIACION_INDEPENDIENTES);
                break;
            case PENSIONADO:
                solicitudDesafiliacionDTO.setTipoTransaccion(TipoTransaccionEnum.DESAFILIACION_PENSIONADOS);
                break;
            default:
                break;
        }
        solicitudDesafiliacionDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudDesafiliacionDTO.setEstadoSolicitud(EstadoSolicitudDesafiliacionEnum.RADICADO);
        logger.debug(
            "Fin de método construirSolicitudDesafiliacion(UserDTO userDTO,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        return solicitudDesafiliacionDTO;
    }

    /**
     * Metodo que invocal el servicio de consultarAportantesSolicitudDesafiliacion
     *
     * @param numeroRadicacion de la solicitud de la solcitud global
     * @return una lista de DesafiliacionAportanteDTO
     */
    private void actualizarLineaCobroDesafiliacion(List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs,
                                                   String usuarioTraspaso) {
        String firmaMetodo = "consultarAportantesSolicitudDesafiliacion(String numeroRadicacion)";
        logger.debug("Inicia " + firmaMetodo);
        ActualizarLineaCobroDesafiliacion actualizarLineaCobroDesafiliacion = new ActualizarLineaCobroDesafiliacion(
            usuarioTraspaso, desafiliacionAportanteDTOs);
        logger.debug("Finaliza " + firmaMetodo);
        actualizarLineaCobroDesafiliacion.execute();
    }

    /**
     * Metodo que sirve para consultar la informacion del empleador por tipo y
     * numero identificacion
     *
     * @param tipoIdentificacion   recibe como parametro el tipo de identificacion
     *                             del empleador
     * @param numeroIdentificacion recibe como parametro el numero de identificacion
     *                             del empleador
     * @return
     */
    private EmpleadorModeloDTO consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion,
                                                            String numeroIdentificacion) {
        String firmaMetodo = "consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
        logger.debug("Inicia " + firmaMetodo);
        ConsultarEmpleadorTipoNumero consultarEmpleadorTipoNumero = new ConsultarEmpleadorTipoNumero(
            numeroIdentificacion, tipoIdentificacion);
        consultarEmpleadorTipoNumero.execute();
        logger.debug("Finaliza " + firmaMetodo);
        return consultarEmpleadorTipoNumero.getResult();
    }

    /**
     * Metodo que sirve para consultar la informacion del afiliado
     *
     * @param tipoIdentificacion   recibe como parametro el tipo de identificacion
     *                             del afiliado
     * @param numeroIdentificacion recibe como parametro el numero de identificacion
     *                             del afiliado
     * @param tipoAfiliado         recibe como parametro el tipo de afilado
     * @return
     */
    private List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion,
                                                                 String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {
        String firmaMetodo = "consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoAfiliadoEnum tipoAfiliado)";
        logger.debug("Inicia " + firmaMetodo);
        ConsultarRolesAfiliado consultarRolesAfiliado = new ConsultarRolesAfiliado(tipoAfiliado, numeroIdentificacion,
            tipoIdentificacion);
        consultarRolesAfiliado.execute();
        logger.debug("Finaliza " + firmaMetodo);
        return consultarRolesAfiliado.getResult();
    }

    /**
     * Metodo que se encarga de armar y ejecutar la novedad de desafiliacion para
     * los aportantes de la HU 196
     *
     * @param desafiliacionAportanteDTOs recibe los aportantes para ser desafiliados
     */
    private void ejecutarNovedadDesafiliacionEmpleadorPersona(List<Long> idsAportantes,
                                                              TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        try {
            String firmaMetodo = "ejecutarNovedadDesafiliacionEmpleadorPersona(List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs)";
            logger.debug("Inicia " + firmaMetodo);
            SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
            DatosNovedadAutomaticaDTO datosNovedad = new DatosNovedadAutomaticaDTO();

            solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.DESAFILIACION_AUTOMATICA_POR_MORA);
            datosNovedad.setIdEmpleadores(idsAportantes);
            ObjectMapper objectMapper = new ObjectMapper();
            solNovedadDTO.setDatosNovedadMasiva(datosNovedad);
            logger.info("radicarSolicitudNovedadMasivaService antes::::: "
                    + objectMapper.writeValueAsString(solNovedadDTO));
            RadicarSolicitudNovedadMasiva radicarSolicitudNovedadMasivaService = new RadicarSolicitudNovedadMasiva(
                    solNovedadDTO);
            radicarSolicitudNovedadMasivaService.execute();
            logger.debug("Finaliza " + firmaMetodo);
        } catch (Exception ae) {
            logger.error(
                    "Error en método incluirAportanteCicloFiscalizacion (SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user)",
                    ae);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo que invoca al servicio ConsultarRolAfiliado
     *
     * @param idRolAfiliado insumo por el cual va hacer consultado el afiliado
     * @return un objeto con la informacion del afiliado
     */
    private RolAfiliadoModeloDTO consultarRolAfiliado(Long idRolAfiliado) {
        logger.debug("Inicio de método ConsultarRolAfiliado (Long idRolAfiliado)");
        ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(idRolAfiliado);
        consultarRolAfiliado.execute();
        logger.debug("Finaliza de método ConsultarRolAfiliado (Long idRolAfiliado)");
        return consultarRolAfiliado.getResult();
    }

    /**
     * Metodo que invoca al servicio de guardar datos del empleador
     *
     * @param empleadorModeloDTO recibe la informacion a guardar del empleador
     */
    private void guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO) {
        String firmaMetodo = "guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        GuardarDatosEmpleador guardarDatosEmpleador = new GuardarDatosEmpleador(empleadorModeloDTO);
        logger.debug("Finaliza metodo" + firmaMetodo);
        guardarDatosEmpleador.execute();
    }

    /**
     * Metodo que invoca el servicio de actualizarRolAfiliado
     *
     * @param rolAfiliadoModeloDTO recibe la informacion del afiliado
     */
    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        String firmaMetodo = "actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
        actualizarRolAfiliado.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
    }

    /**
     * Metodo que invoca el servicio validarCarteraVigenteEnvioFisicoElectronico
     *
     * @param numeroRadicacion atributo por el cual se busca la solicitud
     * @param proceso          atributo que puede ser fisico o electronico
     * @return true o false si encuentra registro
     */

    private Boolean validarCarteraVigenteEnvioFisicoElectronico(String numeroRadicacion, ProcesoEnum proceso) {
        String firmaMetodo = "validarCarteraVigenteEnvioFisicoElectronico(String numeroRadicacion,ProcesoEnum proceso)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ValidarCarteraVigenteEnvioFisicoElectronico validarCarteraVigenteEnvioFisicoElectronico = new ValidarCarteraVigenteEnvioFisicoElectronico(
            numeroRadicacion, proceso);
        logger.debug("Finaliza metodo" + firmaMetodo);
        validarCarteraVigenteEnvioFisicoElectronico.execute();
        return validarCarteraVigenteEnvioFisicoElectronico.getResult();
    }

    /**
     * Metodo que invoca al servicio consultarCiclosVencidos
     *
     * @return una lista de los ciclos vencidos
     */
    private List<CicloCarteraModeloDTO> consultarCiclosVencidos() {
        String firmaMetodo = "consultarCiclosVencidos";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarCiclosVencidos ciclosVencidos = new ConsultarCiclosVencidos();
        ciclosVencidos.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return ciclosVencidos.getResult();
    }

    /**
     * Metodo que construye la informacion SolicitudPreventivaAgrupadora
     *
     * @return un objeto de tipo SolicitudPreventivaAgrupadora
     */
    private SolicitudPreventivaAgrupadoraModeloDTO construirSolicitudPreventivaAgrupadora(UserDTO userDTO) {
        SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO = new SolicitudPreventivaAgrupadoraModeloDTO();
        solicitudPreventivaAgrupadoraModeloDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudPreventivaAgrupadoraModeloDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudPreventivaAgrupadoraModeloDTO
            .setEstadoSolicitudPreventivaAgrupadora(EstadoSolicitudPreventivaEnum.EN_PROCESO);
        solicitudPreventivaAgrupadoraModeloDTO.setFechaCreacion(new Date().getTime());
        solicitudPreventivaAgrupadoraModeloDTO.setFechaRadicacion(new Date().getTime());
        solicitudPreventivaAgrupadoraModeloDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA);
        solicitudPreventivaAgrupadoraModeloDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        return solicitudPreventivaAgrupadoraModeloDTO;
    }

    /**
     * Metodo que ejecuta el servicio guardarSolicitudPreventivaAgrupadora
     *
     * @param solicitudPreventivaAgrupadora contiene la informacion de la
     *                                      solicitudPreventivaAgrupadora
     * @return un objeto tipo solicitudPreventivaAgrupadora
     */
    private SolicitudPreventivaAgrupadoraModeloDTO guardarSolicitudPreventivaAgrupadora(
        SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO) {
        String firmaMetodo = "guardarSolicitudPreventivaAgrupadora(SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO)(String)";
        logger.debug("Inicia metodo" + firmaMetodo);
        GuardarSolicitudPreventivaAgrupadora guardarSolicitudPreventivaAgrupadora = new GuardarSolicitudPreventivaAgrupadora(
            solicitudPreventivaAgrupadoraModeloDTO);
        guardarSolicitudPreventivaAgrupadora.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return guardarSolicitudPreventivaAgrupadora.getResult();

    }

    /**
     * Metodo que ejecuta el servicio consultarSolicitudPreventivaAgrupadora
     *
     * @param numeroRadicacion parametro con el cual se consulta la
     *                         solicitudPreventivaAgrupadora
     * @return un objeto tipo solicitudPreventivaAgrupadora
     */
    private SolicitudPreventivaAgrupadoraModeloDTO consultarSolicitudPreventivaAgrupadora(String numeroRadicacion) {
        String firmaMetodo = "consultarSolicitudPreventivaAgrupadora(String numeroRadicacion)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarSolicitudPreventivaAgrupadora consultarSolicitudPreventivaAgrupadora = new ConsultarSolicitudPreventivaAgrupadora(
            numeroRadicacion);
        consultarSolicitudPreventivaAgrupadora.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return consultarSolicitudPreventivaAgrupadora.getResult();
    }

    /**
     * Metodo que ejecuta el servicio consultarSolicitudPreventivaAgrupadora
     *
     * @param idSolicitudAgrupadora recibe una lista de idSolicitudAgrupadora
     * @return retorna lista de SolicitudPreventivaAgrupadoraModeloDTO
     */
    private List<SolicitudPreventivaAgrupadoraModeloDTO> consultarCierreSolicitudesPreventivas(
        List<Long> idSolicitudAgrupadora) {
        String firmaMetodo = "consultarCierreSolicitudesPreventivas(List<Long> idSolicitudAgrupadora)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarCierreSolicitudesPreventivas cierreSolicitudesPreventivas = new ConsultarCierreSolicitudesPreventivas(
            idSolicitudAgrupadora);
        cierreSolicitudesPreventivas.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return cierreSolicitudesPreventivas.getResult();
    }

    /**
     * Metodo que valdia el CierreSolicitudAgrupadora
     *
     * @return retorna lista con los id de instancia proceso
     */
    private List<SolicitudPreventivaAgrupadoraModeloDTO> validarCierreSolicitudAgrupadora(
        List<Long> idSolicitudAgrupadora) {
        List<SolicitudPreventivaAgrupadoraModeloDTO> solicitudPreventivaAgrupadoraModeloDTOs = consultarCierreSolicitudesPreventivas(
            idSolicitudAgrupadora);
        logger.error("3Cierre Masivo info solicitudespreventivas:" + solicitudPreventivaAgrupadoraModeloDTOs);
        if (!solicitudPreventivaAgrupadoraModeloDTOs.isEmpty()) {
            for (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO : solicitudPreventivaAgrupadoraModeloDTOs) {
                solicitudPreventivaAgrupadoraModeloDTO
                    .setEstadoSolicitudPreventivaAgrupadora(EstadoSolicitudPreventivaEnum.CERRADA);
                guardarSolicitudPreventivaAgrupadora(solicitudPreventivaAgrupadoraModeloDTO);
            }
        }
        return solicitudPreventivaAgrupadoraModeloDTOs;
    }

    /**
     * Metodo que invoca el servicio de obtenerTareaActivaInstancia
     *
     * @return una objeto TareaDTO
     */
    public TareaDTO obtenerTareaActivaInstancia(Long idProceso, Boolean generarToken) {
        String firmaMetodo = "obtenerTareaActivaInstancia(Long idProceso, UserDTO userDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ObtenerTareaActivaInstancia obtenerTareaActivaInstancia = new ObtenerTareaActivaInstancia(idProceso);

        if (generarToken) {
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            obtenerTareaActivaInstancia.setToken(token.getToken());
        }

        obtenerTareaActivaInstancia.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return obtenerTareaActivaInstancia.getResult();
    }

    /**
     * Metodo que invoca el servicio de reasignarTarea
     *
     * @param idTarea de la tarea
     * @param usuario al que se va reasignar la tarea
     */
    private void reasignarTarea(Long idTarea, String usuario) {
        String firmaMetodo = "reasignarTarea(Long idTarea, String usuario)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ReasignarTarea reasignarTarea = new ReasignarTarea(idTarea, usuario);
        reasignarTarea.execute();
    }

    /**
     * Meotdo que llama al servicio actualizarActivacionMetodoGestionCobro
     *
     * @param metodoAnterior parametro que hace referencial al metodo anterior para
     *                       ser actualizado la activacion
     */
    public void actualizarActivacionMetodoGestionCobro(MetodoAccionCobroEnum metodoAnterior) {
        String firmaMetodo = "actualizarActivacionMetodoGestionCobro(MetodoAccionCobroEnum metodoAnterior)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ActualizarActivacionMetodoGestionCobro activacionMetodoGestionCobro = new ActualizarActivacionMetodoGestionCobro(
            metodoAnterior);
        activacionMetodoGestionCobro.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
    }

    /* privados julian */

    /**
     * Método encargado de llamar el cliente del servicio que consulta la solicitud
     * de gestión de cobro electronico
     *
     * @param numeroRadicacion, número de radicación
     * @return solicitud gestión de cobro dto
     */
    private SolicitudGestionCobroElectronicoModeloDTO consultarSolicitudGestionCobroElectronico(
        String numeroRadicacion) {
        String firmaMetodo = "consultarSolicitudGestionCobroElectronico(String)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarSolicitudGestionCobroElectronico solicitudGestionCobroElectronico = new ConsultarSolicitudGestionCobroElectronico(
            numeroRadicacion);
        solicitudGestionCobroElectronico.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return solicitudGestionCobroElectronico.getResult();
    }

    /**
     * Método encargado de buscar el historial de ubicaciones de rol contacto
     * empleador
     *
     * @param idPersona,                id la persona a consultar
     * @param fechaRevision,            fecha de revisión del
     * @param tipoRolContactoEmpleador, tipo de rol contacto empleador
     * @return retorna la lista de ubicaciones del rol contacto empleador
     */
    private List<UbicacionDTO> buscarHistorialUbicacionRolContactoEmpledor(Long idPersona, Long fechaRevision,
                                                                           TipoRolContactoEnum tipoRolContactoEmpleador) {
        String firmaMetodo = "buscarHistorialUbicacionRolContactoEmpledor(Long,Long,TipoParametrizacionGestionCobroEnum)";
        logger.debug("Inicia metodo" + firmaMetodo);
        BuscarHistorialUbicacionRolContactoEmpledor ubicacionesHistorial = new BuscarHistorialUbicacionRolContactoEmpledor(
            idPersona, fechaRevision, tipoRolContactoEmpleador);
        ubicacionesHistorial.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return ubicacionesHistorial.getResult();
    }

    /**
     * Método encargado de consultar la ubicación del rolContacto empleador
     *
     * @param idPersona,       id de la persona que es empleador
     * @param tipoRolContacto, tipo de rol contacto empleador
     * @return retorna la ubicación del rolContacto empleador
     */
    private UbicacionDTO consultarUbicacionRolContactoEmpleador(Long idPersona, TipoRolContactoEnum tipoRolContacto) {
        String firmaMetodo = "consultarUbicacionRolContactoEmpleador(Long,TipoParametrizacionGestionCobroEnum)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarUbicacionRolContactoEmpleador ubicacionRolContacto = new ConsultarUbicacionRolContactoEmpleador(
            idPersona, tipoRolContacto);
        ubicacionRolContacto.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return ubicacionRolContacto.getResult();

    }

    /**
     * Método encargado de consultar el buzón de correspondencia activo para el
     * método electronico
     *
     * @param actualizacionDatos,                 actualizacion de datos
     * @param parametrizacionGestionCobro,Listado de parametrizaciones
     * @param tipoParametrizacion,                tipo de parametrización
     * @param tipoCobro,                          tipo de acción de cobro
     * @param metodoEnvio,método                  de envio
     * @return retorna la respuesta dada
     */
    public Boolean consultarBuzonActivoElectronico(ActualizacionDatosDTO actualizacionDatos,
                                                   List<Object> parametrizacionGestionCobro, TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        MetodoAccionCobroEnum metodoParametrizacion = null;
        Boolean respuesta = false;
        Map<String, Object> parametrizacion = null;
        List<UbicacionDTO> lstUbicacionesEmpresa = new ArrayList<>();
        List<UbicacionDTO> lstUbicacionesHistorial = new ArrayList<>();
        List<TipoUbicacionEnum> tiposUbicacion = new ArrayList<>();
        switch (tipoParametrizacion) {
            case ACCION_COBRO_A:
            case ACCION_COBRO_B:
                metodoParametrizacion = consultarMetodoActivoLC1();
                for (Object objectParametrizacion : parametrizacionGestionCobro) {
                    parametrizacion = (Map<String, Object>) objectParametrizacion;
                    if (metodoParametrizacion.name().equals(parametrizacion.get("metodo"))) {
                        break;
                    }
                }
                break;
            case LINEA_COBRO:
            case ACCION_COBRO_1C:
            case ACCION_COBRO_2C:
            case ACCION_COBRO_2D:
            case ACCION_COBRO_1F:
            case ACCION_COBRO_2H:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            case ACCION_COBRO_2F:
            case ACCION_COBRO_2G:
            case ACCION_COBRO_1D:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            case ACCION_COBRO_1E:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            default:
                break;

        }
        if (parametrizacion != null) {
            Boolean oficinaPrincipal = false;
            // Se consulta la ubicación para la oficina principal
            if (parametrizacion.get("oficinaPrincipalElectronico") != null) {
                oficinaPrincipal = (Boolean) parametrizacion.get("oficinaPrincipalElectronico");
            }
            Boolean correspondenciaFisico = false;
            // Se consulta la ubicación para el representante legal
            if (parametrizacion.get("representanteLegalElectronico") != null) {
                correspondenciaFisico = (Boolean) parametrizacion.get("representanteLegalElectronico");
            }
            Boolean notificacionJudicial = false;
            // Se consulta la ubicación para el responsable de aportes
            if (parametrizacion.get("responsableAportesElectronico") != null) {
                notificacionJudicial = (Boolean) parametrizacion.get("responsableAportesElectronico");
            }
            if (oficinaPrincipal) {
                tiposUbicacion.add(TipoUbicacionEnum.UBICACION_PRINCIPAL);
            }
            if (correspondenciaFisico) {
                tiposUbicacion.add(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA);
            }
            if (notificacionJudicial) {
                UbicacionDTO ubicacionEmpresa = consultarUbicacionRolContactoEmpleador(
                    actualizacionDatos.getIdPersona(), TipoRolContactoEnum.ROL_RESPONSABLE_APORTES);
                if (ubicacionEmpresa != null) {
                    lstUbicacionesEmpresa.add(ubicacionEmpresa);
                }
                List<UbicacionDTO> lstUbicacionesHistorico = buscarHistorialUbicacionRolContactoEmpledor(
                    actualizacionDatos.getIdPersona(), actualizacionDatos.getFechaRadicacion(),
                    TipoRolContactoEnum.ROL_RESPONSABLE_APORTES);
                if (lstUbicacionesHistorico != null) {
                    lstUbicacionesHistorial.addAll(lstUbicacionesHistorico);
                }
            }
            if (!tiposUbicacion.isEmpty()) {
                List<UbicacionDTO> lstUbicacionEmpres = consultarUbicacionEmpresaPorTipo(
                    actualizacionDatos.getIdPersona(), tiposUbicacion);
                if (lstUbicacionEmpres != null) {
                    lstUbicacionesEmpresa.addAll(lstUbicacionEmpres);
                }
                List<UbicacionDTO> lstUbicacionesHistorico = buscarDireccionPorTipoUbicacion(
                    actualizacionDatos.getIdPersona(), actualizacionDatos.getTipoAportante(), tiposUbicacion,
                    actualizacionDatos.getFechaRadicacion());
                if (lstUbicacionesHistorico != null) {
                    lstUbicacionesHistorial.addAll(lstUbicacionesHistorico);
                }
            }
            respuesta = compararUbicacionEmpresaHistorial(lstUbicacionesEmpresa, lstUbicacionesHistorial,
                MetodoEnvioComunicadoEnum.ELECTRONICO);
        }
        return respuesta;
    }

    /**
     * Método encargado de verificar el tipo de parametrizacion y consultar los
     * buzones activos para los diferentes métodos de envio
     *
     * @param actualizacionDatos, dto que contiene los datos a actualziar
     * @param tipoAccionCobro,    tipo de accion de cobro
     * @param metodoEnvio,        método de envio
     * @return retorna la respuesta dada en el buzon de correspondendica
     */
    private Boolean buscarParametrizacionBuzonGestionCobro(ActualizacionDatosDTO actualizacionDatos,
                                                           TipoAccionCobroEnum tipoAccionCobro, MetodoEnvioComunicadoEnum metodoEnvio) {
        List<Object> lstParametrizaciones = null;
        TipoParametrizacionGestionCobroEnum tipoParametrizacion = null;
        Boolean respuesta = false;
        switch (tipoAccionCobro) {
            case A1:
            case A2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A;
                break;
            case B1:
            case B2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_B;
                break;
            case C1:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1C;
                break;
            case C2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2C;
                break;
            case D1:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1D;
                break;
            case E1:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1E;
                break;
            case D2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2D;
                break;
            case F1:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F;
                break;
            case F2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2F;
                break;
            case G2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2G;
                break;
            case H2:
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2H;
                break;
            default:
                break;
        }
        if (tipoParametrizacion != null) {
            lstParametrizaciones = consultarParametrizacionGestionCobro(tipoParametrizacion);
            if (MetodoEnvioComunicadoEnum.FISICO.equals(metodoEnvio)) {
                respuesta = consultarBuzonActivoFisico(actualizacionDatos, lstParametrizaciones, tipoParametrizacion);
            } else {
                respuesta = consultarBuzonActivoElectronico(actualizacionDatos, lstParametrizaciones,
                    tipoParametrizacion);
            }

        }
        return respuesta;
    }

    /**
     * Método encargado de comparar las ubicaciones de la empresa y el historial
     *
     * @param actualizacionDatos, dto que contiene los datos de la actualización
     * @param tiposUbicacion,     tipos de ubicación de la empresa
     */
    private Boolean compararUbicacionEmpresaHistorial(List<UbicacionDTO> lstUbicacionesEmpresa,
                                                      List<UbicacionDTO> lstUbicacionesHistorial, MetodoEnvioComunicadoEnum metodoEnvio) {
        Boolean respuesta = false;

        // Si no existe histórico de ubicaciones, es válida la actualizaicón de datos
        if (lstUbicacionesHistorial == null) {
            return Boolean.TRUE;
        }

        if (lstUbicacionesEmpresa != null && !lstUbicacionesEmpresa.isEmpty() && !lstUbicacionesHistorial.isEmpty()) {
            for (UbicacionDTO ubicacionEmpresa : lstUbicacionesEmpresa) {
                for (UbicacionDTO ubicacionHistorial : lstUbicacionesHistorial) {
                    if (ubicacionEmpresa.getTipoUbicacion() != null) {
                        /* Se validan que el tipo de ubicacion sea igual */
                        if (ubicacionEmpresa.getTipoUbicacion().equals(ubicacionHistorial.getTipoUbicacion())) {
                            if (MetodoEnvioComunicadoEnum.FISICO.equals(metodoEnvio)
                                && (!ubicacionEmpresa.getDireccion().equals(ubicacionHistorial.getDireccion()))) {
                                respuesta = true;
                                return respuesta;
                            }
                            if (MetodoEnvioComunicadoEnum.ELECTRONICO.equals(metodoEnvio) && (!ubicacionEmpresa
                                .getCorreoElectronico().equals(ubicacionHistorial.getCorreoElectronico()))) {
                                respuesta = true;
                                return respuesta;
                            }
                        }
                    }
                    // Debe compararse con la ubicacion responsable de aportes
                }
            }
        }
        return respuesta;
    }

    /**
     * Se consulta el buzon para los diferentes tipos de ubicacion de
     * correspondencia activo
     *
     * @param actualizacionDatos,          actualización de datos dto que contiene
     *                                     la información
     * @param parametrizacionGestionCobro, parametrización perteneciente a la
     *                                     gestión de cobro
     * @return retorna true si la dirección de la empresa es diferente a la
     * historica
     */
    public Boolean consultarBuzonActivoFisico(ActualizacionDatosDTO actualizacionDatos,
                                              List<Object> parametrizacionGestionCobro, TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        Boolean respuesta = false;
        MetodoAccionCobroEnum metodoParametrizacion = null;
        Map<String, Object> parametrizacion = null;
        switch (tipoParametrizacion) {
            case ACCION_COBRO_A:
            case ACCION_COBRO_B:
                metodoParametrizacion = consultarMetodoActivoLC1();
                for (Object objectParametrizacion : parametrizacionGestionCobro) {
                    parametrizacion = (Map<String, Object>) objectParametrizacion;
                    if (metodoParametrizacion.name().equals(parametrizacion.get("metodo"))) {
                        break;
                    }
                }
                break;
            case LINEA_COBRO:
            case ACCION_COBRO_1C:
            case ACCION_COBRO_2C:
            case ACCION_COBRO_2D:
            case ACCION_COBRO_1F:
            case ACCION_COBRO_2H:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            case ACCION_COBRO_2F:
            case ACCION_COBRO_2G:
            case ACCION_COBRO_1D:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            case ACCION_COBRO_1E:
                parametrizacion = (Map<String, Object>) parametrizacionGestionCobro.get(0);
                break;
            default:
                break;

        }
        if (parametrizacion != null) {
            List<TipoUbicacionEnum> tiposUbicacion = construirTipoUbicacion(parametrizacion);
            if (tiposUbicacion.isEmpty()) {
                return respuesta;
            }
            List<UbicacionDTO> lstUbicacionesEmpresa = consultarUbicacionEmpresaPorTipo(
                actualizacionDatos.getIdPersona(), tiposUbicacion);
            List<UbicacionDTO> lstUbicacionesHistorial = buscarDireccionPorTipoUbicacion(
                actualizacionDatos.getIdPersona(), actualizacionDatos.getTipoAportante(), tiposUbicacion,
                actualizacionDatos.getFechaRadicacion());
            respuesta = compararUbicacionEmpresaHistorial(lstUbicacionesEmpresa, lstUbicacionesHistorial,
                MetodoEnvioComunicadoEnum.FISICO);
        }
        return respuesta;
    }

    /**
     * Método encargado de construir los tipos de ubicación
     *
     * @param parametrizacion, parametrizaciones existentes
     * @return retorna la lista de tipos de ubicación
     */
    private List<TipoUbicacionEnum> construirTipoUbicacion(Map<String, Object> parametrizacion) {
        List<TipoUbicacionEnum> tiposUbicacion = new ArrayList<>();
        Boolean oficinaPrincipal = false;
        Boolean correspondenciaFisico = false;
        Boolean notificacionJudicial = false;
        // Se consulta la ubicación paraa la oficina principal
        if (parametrizacion.get("oficinaPrincipalFisico") != null) {
            oficinaPrincipal = (Boolean) parametrizacion.get("oficinaPrincipalFisico");
        }
        if (parametrizacion.get("correspondenciaFisico") != null) {
            // Se consulta la ubicación para el envio de correspondenecia
            correspondenciaFisico = (Boolean) parametrizacion.get("correspondenciaFisico");
        }
        // Se consulta la ubicación para la notificación judicial
        if (parametrizacion.get("notificacionJudicialFisico") != null) {
            notificacionJudicial = (Boolean) parametrizacion.get("notificacionJudicialFisico");
        }
        if (oficinaPrincipal) {
            tiposUbicacion.add(TipoUbicacionEnum.UBICACION_PRINCIPAL);
        }
        if (correspondenciaFisico) {
            tiposUbicacion.add(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA);
        }
        if (notificacionJudicial) {
            tiposUbicacion.add(TipoUbicacionEnum.NOTIFICACION_JUDICIAL);
        }
        return tiposUbicacion;
    }

    /**
     * Método encargado de consultar las ubicaciones de empresa por tipo
     *
     * @param idPersona,     id de la persona asociada a la empresa
     * @param tipoUbicacion, lista de tipos de ubicacion
     * @return retorna la lista de ubicaciones
     */
    private List<UbicacionDTO> consultarUbicacionEmpresaPorTipo(Long idPersona,
                                                                List<TipoUbicacionEnum> tiposUbicacion) {
        logger.debug("Inicia ConsultarUbicacionEmpresaPorTipo(Boolean,List<DetalleSolicitudGestionCobroModeloDTO>)");
        ConsultarUbicacionEmpresaPorTipo ubicacionEmpresa = new ConsultarUbicacionEmpresaPorTipo(idPersona,
            tiposUbicacion);
        ubicacionEmpresa.execute();
        logger.debug("Finaliza ConsultarUbicacionEmpresaPorTipo(Boolean,List<DetalleSolicitudGestionCobroModeloDTO>)");
        return ubicacionEmpresa.getResult();
    }

    /**
     * Método encargado de llamar el cliente del servicio que busca la dirreción por
     * tipo y ubicación
     *
     * @param idPersona,       id de la persona
     * @param tipoSolicitante, tipo de solicitante
     * @param tiposUbicacion,  tipos de ubicación a consultar
     * @param fechaRevision,   fecha de revisión
     * @return retorna el listado de ubicaciones
     */
    private List<UbicacionDTO> buscarDireccionPorTipoUbicacion(Long idPersona,
                                                               TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<TipoUbicacionEnum> tiposUbicacion,
                                                               Long fechaRevision) {
        logger.debug(
            "Inicia buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)");
        BuscarDireccionPorTipoUbicacion ubicacionEmpresa = new BuscarDireccionPorTipoUbicacion(idPersona,
            tipoSolicitante, tiposUbicacion, fechaRevision);
        ubicacionEmpresa.execute();
        logger.debug(
            "Finaliza buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)");
        return ubicacionEmpresa.getResult();
    }

    /**
     * Método encargado de verificar si se aprueban las condiciones dadas para una
     * solicitud
     *
     * @param numeroRadicado,  número de radicado de la
     *                         SolicitudGestionCobroElectronico
     * @param tipoAccionCobro, tipo de acción de cobro
     * @return retorna true si se aprueban las validaciones
     */
    private Boolean verificarEnvioComunicadoElectronico(String numeroRadicado, TipoAccionCobroEnum tipoAccionCobro,
                                                        Long idCartera) {
        Boolean respuesta = false;
        UbicacionDTO ubicacionActual = null;
        /* Se valida si la cartera está vigente */
        if (validarCarteraVigenteEnvioFisicoElectronico(numeroRadicado, ProcesoEnum.GESTION_COBRO_ELECTRONICO)
            && validarEnvioComunicadoElectronico(numeroRadicado, tipoAccionCobro, idCartera)) {
            ActualizacionDatosDTO actualizacionDatos = consultarActualizacionDatos(numeroRadicado);
            Long fechaRadicacion = actualizacionDatos.getFechaRadicacion();
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(actualizacionDatos.getTipoAportante())) {
                respuesta = buscarParametrizacionBuzonGestionCobro(actualizacionDatos, tipoAccionCobro,
                    MetodoEnvioComunicadoEnum.ELECTRONICO);
            } else {
                ubicacionActual = consultarUbicacionPersona(actualizacionDatos.getIdPersona());
                UbicacionDTO ubicacionRepresentanteLegal = buscarDireccion(actualizacionDatos.getIdPersona(),
                    actualizacionDatos.getTipoAportante(), TipoUbicacionEnum.UBICACION_PRINCIPAL, fechaRadicacion);
                if (ubicacionRepresentanteLegal != null && ubicacionActual != null && !(ubicacionRepresentanteLegal
                    .getCorreoElectronico().equals(ubicacionActual.getCorreoElectronico()))) {
                    respuesta = true;
                }
            }
        }
        return respuesta;
    }

    /**
     * Método encargado de verificar el envio del comunicado fisico
     *
     * @param numeroRadicado,  número de radicado
     * @param tipoAccionCobro, tipo de acción de cobro de la solicitud de envio de
     *                         comunicado fisico
     * @return retorna true si cumple las validaciones
     */
    private Boolean verificarEnvioComunicadoFisico(String numeroRadicado, TipoAccionCobroEnum tipoAccionCobro,
                                                   Long idCartera) {
        Boolean respuesta = false;

        if (validarCarteraVigenteEnvioFisicoElectronico(numeroRadicado, ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL)) {
            UbicacionDTO ubicacionActual = null;
            List<Date> diasFestivos = new ArrayList<>();
            List<Integer> idsListaValores = new ArrayList<>();
            /* se agrega el id a buscar */
            idsListaValores.add(239);
            /* Se consultan los dias habiles */
            List<ElementoListaDTO> elementoListaDTOs = consultarListasValores(idsListaValores);
            /* Se recorre los elementos de ElementoListaDTO */
            for (ElementoListaDTO elementoListaDTO : elementoListaDTOs) {
                /* Se recorre el map */
                for (Entry<String, Object> map : elementoListaDTO.getAtributos().entrySet()) {
                    if (map.getKey().equals("fecha")) {
                        Long diaFestivo = (Long) map.getValue();
                        diasFestivos.add(new Date(diaFestivo));
                    }
                }
            }
            Calendar calendarFechaActual = Calendar.getInstance();
            Date fechaActual = new Date();
            calendarFechaActual.setTime(fechaActual);
            calendarFechaActual = CalendarUtil.fomatearFechaSinHora(calendarFechaActual);
            Date ultimoDiaMesHabil = CalendarUtils.obtenerUltimoDiaMesHabil(fechaActual, elementoListaDTOs);
            // Se verifica si la fecha actual es menor igual al último día hábil del mes y
            // que no presente exclusiones ni convenios
            if (CalendarUtils.esFechaMenorIgual(calendarFechaActual.getTime(), ultimoDiaMesHabil)
                && validarEnvioComunicadoFisico(numeroRadicado, tipoAccionCobro, idCartera)) {
                ActualizacionDatosDTO actualizacionDatos = consultarActualizacionDatos(numeroRadicado);
                Long fechaRadicacion = actualizacionDatos.getFechaRadicacion();
                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(actualizacionDatos.getTipoAportante())) {
                    respuesta = buscarParametrizacionBuzonGestionCobro(actualizacionDatos, tipoAccionCobro,
                        MetodoEnvioComunicadoEnum.FISICO);
                } else {
                    ubicacionActual = consultarUbicacionPersona(actualizacionDatos.getIdPersona());
                    UbicacionDTO ubicacionRepresentanteLegal = buscarDireccion(actualizacionDatos.getIdPersona(),
                        actualizacionDatos.getTipoAportante(), TipoUbicacionEnum.UBICACION_PRINCIPAL,
                        fechaRadicacion);
                    if (ubicacionRepresentanteLegal != null && ubicacionActual != null
                        && !(ubicacionRepresentanteLegal.getDireccion().equals(ubicacionActual.getDireccion()))) {
                        respuesta = true;
                    }
                }
            }
        }
        return respuesta;
    }

    /**
     * Método encargado de llamar el cliente del servicio que consulta la ubicación
     * de la persona
     *
     * @param idPersona, id de la persona
     * @return retorna la ubicación DTO
     */
    private UbicacionDTO consultarUbicacionPersona(Long idPersona) {
        logger.debug("Inicia método consultarUbicacionPersona(Long)");
        ConsultarUbicacionPersona ubicacionRepresentante = new ConsultarUbicacionPersona(idPersona);
        ubicacionRepresentante.execute();
        logger.debug("Inicia método consultarUbicacionPersona(Long)");

        return ubicacionRepresentante.getResult();

    }

    /**
     * Método encargado de consultar la ubicación del representante legal
     *
     * @param idPersona,       id de la persona
     * @param tipoSolicitante, tipo de solicitante
     * @param tipoUbicacion,   tipoUbicación de la empresa
     * @param fechaRevision,   fecha de revisión
     * @return retorna la ubicación
     */
    private UbicacionDTO buscarDireccion(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                         TipoUbicacionEnum tipoUbicacion, Long fechaRevision) {
        logger.debug(
            "Inicia método buscarDireccionRepresentanteLegal(String,TipoSolicitanteMovimientoAporteEnum,Long)");
        BuscarDireccion buscarDireccion = new BuscarDireccion(idPersona, tipoSolicitante, tipoUbicacion, fechaRevision);
        buscarDireccion.execute();
        logger.debug(
            "Finaliza método buscarDireccionRepresentanteLegal(Long,TipoSolicitanteMovimientoAporteEnum,Long)");
        return buscarDireccion.getResult();
    }

    /**
     * Método encargado de consultar la actualización de datos
     *
     * @param numeroRadicacion, número de radicado de la solicitud
     * @return retorna los datos de actualización
     */
    private ActualizacionDatosDTO consultarActualizacionDatos(String numeroRadicacion) {
        logger.debug("Inicia método consultarActualizacionDatos(String)");
        ConsultarActualizacionDatos consultaActualizacionDatos = new ConsultarActualizacionDatos(numeroRadicacion);
        consultaActualizacionDatos.execute();
        logger.debug("Finaliza método consultarActualizacionDatos(String)");
        return consultaActualizacionDatos.getResult();
    }

    /**
     * Método encargado de llamar el cliente que consulta las exclusiones por
     * aportante
     *
     * @param exclusionesCarteraDTO, exclusion Cartera DTO
     * @return retorna la lista de exclusiones cartera DTO
     */
    private List<ExclusionCarteraDTO> buscarListaExclusionCarteraActiva(
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<Long> idPersonas) {
        logger.debug("Inicia método buscarListaExclusionCarteraActiva(List<Long> idPersonas)");
        BuscarListaExclusionCarteraActiva exclusionesCartera = new BuscarListaExclusionCarteraActiva(tipoSolicitante,
            idPersonas);
        exclusionesCartera.execute();
        logger.debug("Finaliza método buscarListaExclusionCarteraActiva(List<Long> idPersonas)");
        return exclusionesCartera.getResult();
    }

    /**
     * Método encargado de consultar la afiliacion con la que cuenta una persona
     *
     * @return retorna la persona dto encontrada
     */
    private PersonaDTO buscarTipoAfiliacionPersona(TipoIdentificacionEnum tipoIdentificacion,
                                                   String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug(
            "Inicia método buscarTipoAfiliacionPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        BuscarTipoAfiliacionPersona busquedaAfiliacion = new BuscarTipoAfiliacionPersona(tipoSolicitante,
            numeroIdentificacion, tipoIdentificacion);
        busquedaAfiliacion.execute();
        logger.debug(
            "Finaliza método buscarTipoAfiliacionPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        return busquedaAfiliacion.getResult();
    }

    /**
     * Método encargado de consultar las exclusiones por aportante
     *
     * @param tipoIdentificacion,   tipo de identificación de la persona
     * @param numeroIdentificacion, número de identificación de la persona
     * @param tipoSolicitante,      Tipo de solicitante
     * @return retorna el listado de exclusiones de la persona
     */
    private List<ExclusionCarteraDTO> consultarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion,
                                                                     String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug(
            "Inicia método consultarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarExclusionPorAportante consultaExclusion = new ConsultarExclusionPorAportante(tipoSolicitante,
            numeroIdentificacion, tipoIdentificacion);
        consultaExclusion.execute();
        logger.debug(
            "Finaliza método consultarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return consultaExclusion.getResult();
    }

    /**
     * Método que invoca el servicio de creación de registros en cartera
     */
    private void crearRegistroCartera() {
        logger.debug("Inicia crearRegistroCartera()");
        CrearRegistroCartera service = new CrearRegistroCartera();
        service.execute();
        logger.debug("Finaliza crearRegistroCartera()");
    }

    /**
     * Método encargado de llamar el cliente del servicio que consulta la condición
     * de cartera para la línea de cobro uno
     *
     * @param estadoOperacion, estado de operación de cartera
     * @param tipoLineaCobro,  tipo de línea de cobro
     * @return retorna true si no hay aportantes en cartera
     */
    private Boolean consultarCondicionAportanteCarteraLCUNO(TipoLineaCobroEnum tipoLineaCobro,
                                                            EstadoOperacionCarteraEnum estadoOperacion) {
        logger.debug("Inicia consultarCondicionAportanteCarteraLCUNO(EstadoOperacionCarteraEnum,TipoLineaCobroEnum)");
        ConsultarCondicionAportantesCarteraLCUNO consultaCondicionAportante = new ConsultarCondicionAportantesCarteraLCUNO(
            tipoLineaCobro, estadoOperacion);
        consultaCondicionAportante.execute();
        logger.debug("Finaliza consultarCondicionAportanteCarteraLCUNO(EstadoOperacionCarteraEnum,TipoLineaCobroEnum)");
        return consultaCondicionAportante.getResult();
    }

    /**
     * Método encargado de validar la condición de parametrización para las líneas
     * de cobro
     *
     * @param criterioParametrizacion, criterio de parametrización a realizar las
     *                                 validaciones
     * @return retorna 0 si se cumplen las validaciones y 1 si no se cumplen
     */
    private Integer validarCondicionParametrizacionLC(CriteriosParametrizacionTemporalDTO criterioParametrizacion) {
        logger.debug("Inicio de método validarCondicionParametrizacionLC");
        Integer respuesta = 1;
        TipoLineaCobroEnum lineaCobro = null;
        if (criterioParametrizacion.getMetodo() != null) {
            lineaCobro = TipoLineaCobroEnum.LC1;
            Boolean automaticaAgregada = Boolean.FALSE;
            for (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion : criterioParametrizacion
                .getParametrizaciones()) {
                if (lineaCobro.equals(parametrizacion.getLineaCobro())) {
                    if (TipoGestionCarteraEnum.MANUAL.equals(parametrizacion.getAccion())) {
                        /* si esta activo la manual responde exitoso */
                        respuesta = 0;
                        break;
                    } else if (TipoGestionCarteraEnum.AUTOMATICA.equals(parametrizacion.getAccion())) {
                        automaticaAgregada = Boolean.TRUE;
                    }
                }
            }
            /* si no hay parametrizacion y no esta activa manualmente */
            if (respuesta == 1 && !criterioParametrizacion.getManual()) {
                respuesta = 0;
            }
            if (!automaticaAgregada) {
                ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion = new ParametrizacionCriteriosGestionCobroModeloDTO();
                parametrizacion.setAccion(TipoGestionCarteraEnum.AUTOMATICA);
                parametrizacion.setActiva(Boolean.TRUE);
                parametrizacion.setAplicar(Boolean.TRUE);
                parametrizacion.setLineaCobro(TipoLineaCobroEnum.LC1);
                parametrizacion.setMetodo(criterioParametrizacion.getMetodo());
                criterioParametrizacion.getParametrizaciones().add(parametrizacion);
            }
        }
        // Validación línea de cobro 2
        lineaCobro = TipoLineaCobroEnum.LC2;
        respuesta = validarLineaCobro(lineaCobro, criterioParametrizacion.getParametrizaciones(),
            criterioParametrizacion.getAccionesLc2(), criterioParametrizacion.getLc2());
        if (respuesta == 1) {
            return respuesta;
        }
        // Validación línea de cobro 3
        lineaCobro = TipoLineaCobroEnum.LC3;
        respuesta = validarLineaCobro(lineaCobro, criterioParametrizacion.getParametrizaciones(),
            criterioParametrizacion.getAccionesLc3(), criterioParametrizacion.getLc3());
        if (respuesta == 1) {
            return respuesta;
        }
        // Validación línea de cobro 4

        lineaCobro = TipoLineaCobroEnum.LC4;
        respuesta = validarLineaCobro(lineaCobro, criterioParametrizacion.getParametrizaciones(),
            criterioParametrizacion.getAccionesLc4(), criterioParametrizacion.getLc4());
        if (respuesta == 1) {
            return respuesta;
        }
        // Validación línea de cobro 5
        lineaCobro = TipoLineaCobroEnum.LC5;
        respuesta = validarLineaCobro(lineaCobro, criterioParametrizacion.getParametrizaciones(),
            criterioParametrizacion.getAccionesLc5(), criterioParametrizacion.getLc5());
        if (respuesta == 1) {
            return respuesta;
        }
        return respuesta;
    }

    /**
     * Método encargado de validar si una linea de cobro tiene parametrizaciones
     *
     * @param lineaCobro,        linea de cobro a verificar
     * @param parametrizaciones, listado de parametrizaciones
     * @return retorna true si cumple la validación
     */
    private Integer validarLineaCobro(TipoLineaCobroEnum lineaCobro,
                                      List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizaciones,
                                      List<TipoGestionCarteraEnum> acciones, Boolean activa) {
        Integer respuesta = 1;
        int contador = 0;
        for (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion : parametrizaciones) {
            if (lineaCobro.equals(parametrizacion.getLineaCobro())) {
                /* si la acción seleccionada esta en la parametrizacion se suma */
                if (acciones.contains(parametrizacion.getAccion())) {
                    contador++;
                } else {
                    parametrizacion.setActiva(Boolean.FALSE);
                }
            }
        }
        if (contador == acciones.size()) {
            respuesta = 0;
        }
        return respuesta;
    }

    /**
     * Método encargado de construir un archivo de documento de soporte
     *
     * @param documento,          documento ya existente
     * @param identificadorECM,   identificador del ECM perteneciente al archivo
     *                            nuevo
     * @param fecha,              fecha de carga del archivo
     * @param hora,               hora de carga del archivo
     * @param nombreArchivo,      Nombre del archivo
     * @param descripcionArchivo, Descripción del archivo
     * @return retorna el archivo de documento soporte dto ya construido
     */
    private DocumentoSoporteModeloDTO construirDocumentoSoporte(DocumentoSoporteModeloDTO documento,
                                                                String identificadorECM, String nombreArchivo, String descripcionArchivo) {
        String separador = "_";
        DocumentoSoporteModeloDTO documentoSoporte = null;
        if (identificadorECM != null && !identificadorECM.equals("") && documento == null) {
            documentoSoporte = new DocumentoSoporteModeloDTO();
            documentoSoporte.setNombre(nombreArchivo);
            documentoSoporte.setDescripcionComentarios(descripcionArchivo);
            documentoSoporte.setFechaHoraCargue((new Date()).getTime());
            String[] split = identificadorECM.split(separador);
            documentoSoporte.setIdentificacionDocumento(split[0]);
            documentoSoporte.setVersionDocumento(split[1]);
        } else if (documento != null && (identificadorECM != null && !identificadorECM.equals(""))) {
            documentoSoporte = documento;
            String[] split = identificadorECM.split(separador);
            documentoSoporte.setIdentificacionDocumento(split[0]);
            documentoSoporte.setVersionDocumento(split[1]);
        }
        return documentoSoporte;
    }

    /* privados Clau */

    /**
     * Servicio que consulta el estado de cartera para un aportante
     *
     * @param tipoIdentificacion   Tipo de Identificación del aportante
     * @param numeroIdentificacion Número de Identificación del aportante
     * @param tipoSolicitante      Tipo de solicitante empleador, independiente o
     *                             pensionado
     * @return Retorna el estado de cartera para el aportante
     */
    private EstadoCarteraEnum consultarEstadoCartera(TipoIdentificacionEnum tipoIdentificacion,
                                                     String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug(
            "Inicio de consultarEstadoCartera(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
        ConsultarEstadoCartera consultarEstadoCarteraService = new ConsultarEstadoCartera(tipoSolicitante,
            numeroIdentificacion, tipoIdentificacion);
        consultarEstadoCarteraService.execute();
        logger.debug(
            "Fin de consultarEstadoCartera(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
        return consultarEstadoCarteraService.getResult();
    }

    /**
     * Servicio que consulta los convenios de pagos asociados a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de solicitante que puede ser empleador,
     *                             independiente o pensionado
     * @return Retorna la lista de convenios de pago asociadas al aportante
     */
    private List<ConvenioPagoModeloDTO> consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion,
                                                               String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug(
            "Inicio de método consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        ConsultarConveniosPago consultarConveniosPagoService = new ConsultarConveniosPago(tipoSolicitante,
            numeroIdentificacion, tipoIdentificacion);
        consultarConveniosPagoService.execute();
        logger.debug(
            "Fin de método consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        return consultarConveniosPagoService.getResult();
    }

    /**
     * Servicio que consulta la parametrización de convenios de pago
     *
     * @return parametrizacionConvenio Retorna la parametrizacion de convenio modelo
     * DTO
     */
    private ParametrizacionConveniosPagoModeloDTO consultarParametrizacionConveniosPago() {
        logger.debug("Inicio de método consultarParametrizacionConveniosPago()");
        ConsultarParametrizacionConveniosPago consultarParametrizacionConveniosPagoService = new ConsultarParametrizacionConveniosPago();
        consultarParametrizacionConveniosPagoService.execute();
        ParametrizacionConveniosPagoModeloDTO parametrizacionConvenio = consultarParametrizacionConveniosPagoService
            .getResult();
        logger.debug("Fin de método consultarParametrizacionConveniosPago()");
        return parametrizacionConvenio;
    }

    /* privados Angelica */

    /**
     * Método encargado de consultar el método activo.
     *
     * @return metodo activo.
     */
    private MetodoAccionCobroEnum consultarMetodoActivoLC1() {
        logger.debug("Inicio de método consultarMetodoActivoLC1");
        ConsultarMetodoActivoLC1 consultarMetodoService = new ConsultarMetodoActivoLC1();
        consultarMetodoService.execute();
        logger.debug("Fin de método consultarMetodoActivoLC1");
        return consultarMetodoService.getResult();
    }

    /**
     * Método encargado de terminar el ciclo de fiscalización según el estado.
     *
     * @param estado
     */
    private void terminarCicloFiscalizacion(EstadoCicloCarteraEnum estado,
                                            CicloCarteraModeloDTO cicloFiscalizacionModeloDTO) {
        cicloFiscalizacionModeloDTO.setEstadoCicloFiscalizacion(estado);
        /* se llama al servicio de cancelar ciclo de fiscalizacion */
        actualizarCiclo(cicloFiscalizacionModeloDTO);

        /* Se instancia lista de estados de la solicitud de fiscalizacion */
        List<EstadoFiscalizacionEnum> estadosFiscalizacion = new ArrayList<>();
        estadosFiscalizacion.add(EstadoFiscalizacionEnum.ASIGNADO);
        estadosFiscalizacion.add(EstadoFiscalizacionEnum.EN_PROCESO);

        /* Se consulta el listado de solicitudes pertenecientes al ciclo */
        List<SolicitudModeloDTO> lstSolicitudesFiscalizacion = consultarSolicitudesCiclo(
            cicloFiscalizacionModeloDTO.getIdCicloFiscalizacion(), estadosFiscalizacion,
            cicloFiscalizacionModeloDTO.getTipoCiclo());

        List<Long> idInstnacias = new ArrayList<>();
        for (SolicitudModeloDTO solicitudUTActivaDTO : lstSolicitudesFiscalizacion) {
            idInstnacias.add(new Long(solicitudUTActivaDTO.getIdInstanciaProceso()));
        }

        if (!idInstnacias.isEmpty()) {
            if (EstadoCicloCarteraEnum.CANCELADO.equals(estado)) {
                /* Se invoca abortar tarea */
                abortarProcesos(ProcesoEnum.FISCALIZACION_CARTERA, idInstnacias, Boolean.FALSE);
            } else {
                abortarProcesos(TipoCicloEnum.FISCALIZACION.equals(cicloFiscalizacionModeloDTO.getTipoCiclo())
                    ? ProcesoEnum.FISCALIZACION_CARTERA
                    : ProcesoEnum.GESTION_COBRO_MANUAL, idInstnacias, Boolean.TRUE);
            }
        }
    }

    /**
     * Método que se encarga de invocar el servicio que consulta el dato temporal de
     * la solicitud de parametrización.
     *
     * @return criteriosParametrizacion criterios de parametrización.
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    private CriteriosParametrizacionTemporalDTO consultarDatoTemporalParametrizacion(
        ParametrizacionEnum parametrizacion) throws JsonParseException, JsonMappingException, IOException {
        logger.debug("Inicio de método consultarDatoTemporalParametrizacion");
        ConsultarDatoTemporalParametrizacion datoTemporal = new ConsultarDatoTemporalParametrizacion(parametrizacion);
        datoTemporal.execute();
        String jsonPayload = datoTemporal.getResult();
        if (jsonPayload != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonPayload, CriteriosParametrizacionTemporalDTO.class);
        }
        logger.debug("Fin de método consultarDatoTemporalParametrizacion");
        return null;
    }

    /**
     * Método que se encarga de guardar el dato temporal de la solicitud.
     *
     * @param criterioParametrizacion parametrización a guardar.
     */
    private void guardarDatoTemporalParametrizacion(CriteriosParametrizacionTemporalDTO criterioParametrizacion,
                                                    ParametrizacionEnum parametrizacion) {
        logger.debug(
            "Inicio de método guardarDatoTemporalParametrizacion(ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion)");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(criterioParametrizacion);
            GuardarDatoTemporalParametrizacion datoTemporal = new GuardarDatoTemporalParametrizacion(parametrizacion,
                jsonPayload);
            datoTemporal.execute();
            logger.debug(
                "Fin de método guardarDatoTemporalParametrizacion(ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion)");
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que se encarga de invocar el servicio que guarda los criterios de la
     * gestión de cobro.
     *
     * @param parametrizacionCriterios parametrizaciones a guardar.
     */
    private void guardarCriteriosGestionCobro(
        List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios,
        MetodoAccionCobroEnum metodo) {
        logger.debug(
            "Inicio de método guardarCriteriosGestionCobro(List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios)");
        GuardarCriteriosGestionCobro guardarCriteriosService = new GuardarCriteriosGestionCobro(metodo,
            parametrizacionCriterios);
        guardarCriteriosService.execute();
        logger.debug(
            "Fin de método guardarCriteriosGestionCobro(List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios)");
    }

    /**
     * Método que se encarga de transformar un aportante en una solicitud de gestión
     * de cobro de segunda remisión.
     *
     * @param aportante aportante a crearle la solicitud.
     * @param userDTO   usuario autenticado.
     * @return solicitud de gestion de cobro a guardar.
     */
    private SolicitudGestionCobroFisicoModeloDTO transformarGestionCobroSegundoIntento(
        AportanteRemisionComunicadoDTO aportante, UserDTO userDTO, String usuarioEquitativo,
        TipoTransaccionEnum tipoTransaccion, TipoAccionCobroEnum tipoAccion) {
        logger.debug(
            "Inicio de método encargado transformarGestionCobroSegundoIntento(AportanteRemisionComunicadoDTO aportante)");
        SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobro = new SolicitudGestionCobroFisicoModeloDTO();
        solicitudGestionCobro.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudGestionCobro.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudGestionCobro.setFechaCreacion(new Date().getTime());
        solicitudGestionCobro.setEstado(EstadoSolicitudGestionCobroEnum.PENDIENTE_ACTUALIZACION_DATOS);
        solicitudGestionCobro.setTipoTransaccion(tipoTransaccion);
        solicitudGestionCobro.setDestinatario(usuarioEquitativo);
        solicitudGestionCobro.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
        solicitudGestionCobro.setTipoAccionCobro(tipoAccion);
        logger.debug(
            "Fin de método encargado transformarGestionCobroSegundoIntento(AportanteRemisionComunicadoDTO aportante)");
        return solicitudGestionCobro;

    }

    /**
     * Método que se encarga de validar si se debe o no realizar el envío del
     * comunicado de acuerdo a
     * las validaciones descritas en el numeral 8 de la HU175.
     *
     * @param numeroRadicacion número de radicación de la solicitud fisica.
     * @param fisica           true si es física, false si es electrónica.
     * @return true si se puede enviar el comunicado, false si no se puede.
     */
    private Boolean validarEnvioComunicadoFisico(String numeroRadicacion, TipoAccionCobroEnum tipoAccionCobro,
                                                 Long idCartera) {
        logger.debug("Inicio de método validarEnvioComunicado(SolicitudGestionCobroModeloDTO solicitudGestion)");
        ValidarEnvioComunicadoFisico validarEnvioService = new ValidarEnvioComunicadoFisico(numeroRadicacion,
            tipoAccionCobro, idCartera);
        validarEnvioService.execute();
        logger.debug("Fin de método validarEnvioComunicado(SolicitudGestionCobroModeloDTO solicitudGestion)");
        return validarEnvioService.getResult();
    }

    /**
     * Método que se encarga de validar si se debe o no realizar el envío del
     * comunicado de acuerdo a
     * las validaciones descritas en el numeral 4 de la HU175.
     *
     * @param numeroRadicacion número de radicación de la solicitud electronica.
     * @return true si se puede enviar el comunicado, false si no se puede.
     */
    private Boolean validarEnvioComunicadoElectronico(String numeroRadicacion, TipoAccionCobroEnum tipoAccionCobro,
                                                      Long idCartera) {
        logger.debug("Inicio de método validarEnvioComunicado(SolicitudGestionCobroModeloDTO solicitudGestion)");
        ValidarEnvioComunicadoElectronico validarEnvioService = new ValidarEnvioComunicadoElectronico(numeroRadicacion,
            tipoAccionCobro, idCartera);
        validarEnvioService.execute();
        logger.debug("Fin de método validarEnvioComunicado(SolicitudGestionCobroModeloDTO solicitudGestion)");
        return validarEnvioService.getResult();
    }

    /**
     * Método que se encarga de consultar la solicitud de gestión de cobro por el
     * número de radicación.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     * @return solicitud de gestión de cobro encontrada.
     */
    private SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobro(String numeroRadicacion) {
        logger.debug("Inicio de método consultarSolicitudGestionCobro(String numeroRadicacion)");
        ConsultarSolicitudGestionCobro consultarSolicitudService = new ConsultarSolicitudGestionCobro(numeroRadicacion);
        consultarSolicitudService.execute();
        logger.debug("Fin de método consultarSolicitudGestionCobro(String numeroRadicacion)");
        return consultarSolicitudService.getResult();
    }

    /**
     * Método encargado de invocar el servicio que guarda o actualiza una solicitud
     * de gestión de cobro.
     *
     * @param solicitudGestionDTO gestión de cobro a guardar.
     * @return Solicitud guardada o actualizada.
     */
    private SolicitudGestionCobroFisicoModeloDTO guardarSolicitudGestionCobro(
        SolicitudGestionCobroFisicoModeloDTO solicitudGestionDTO) {
        logger.debug(
            "Inicio de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        GuardarSolicitudGestionCobro guardarSolicitudService = new GuardarSolicitudGestionCobro(solicitudGestionDTO);
        guardarSolicitudService.execute();
        logger.debug("Fin de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        return guardarSolicitudService.getResult();
    }

    /**
     * Método encargado de invocar el servicio que actualiza el estado de una
     * solicitud de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud de gestión de
     *                         cobro.
     * @param estadoSolicitud  estado de solicitud a actualizar.
     */
    private void actualizarEstadoSolicitudGestionCobro(String numeroRadicacion,
                                                       EstadoSolicitudGestionCobroEnum estadoSolicitud) {
        logger.debug("Inicio de método actualizarEstadoSolicitudGestionCobro");
        ActualizarEstadoSolicitudGestionCobro actualizarEstadoService = new ActualizarEstadoSolicitudGestionCobro(
            numeroRadicacion, estadoSolicitud);
        actualizarEstadoService.execute();
        logger.debug("Fin de método actualizarEstadoSolicitudGestionCobro");
    }

    private DetalleSolicitudGestionCobroModeloDTO consultarDetallePorSolicitud(Long idSolicitud) {
        logger.debug("Inicio de método consultarDetallePorSolicitud");
        ConsultarDetallePorSolicitud consultarDetallePorSolicitudService = new ConsultarDetallePorSolicitud(
            idSolicitud);
        consultarDetallePorSolicitudService.execute();
        logger.debug("Fin de método consultarDetallePorSolicitud");
        return consultarDetallePorSolicitudService.getResult();
    }

    /**
     * Método encargado de invocar el servicio que actualiza el estado de una
     * solicitud de gestión de cobro electronico
     *
     * @param numeroRadicacion
     * @param estadoSolicitud
     */
    private void actualizarEstadoSolicitudGestionCobroElectronico(String numeroRadicacion,
                                                                  EstadoSolicitudGestionCobroEnum estadoSolicitud) {
        logger.debug("Inicio de método actualizarEstadoSolicitudGestionCobroElectronico");
        ActualizarEstadoSolicitudGestionCobroElectronico actualizarEstadoService = new ActualizarEstadoSolicitudGestionCobroElectronico(
            numeroRadicacion, estadoSolicitud);
        actualizarEstadoService.execute();
        logger.debug("Fin de método actualizarEstadoSolicitudGestionCobroElectronico");
    }

    /**
     * Método encargado de invocar al servicio que consulta la parametrización
     * preventiva.
     *
     * @return parametrización de cartera encontrada.
     */
    private ParametrizacionPreventivaModeloDTO consultarParametrizacionPreventiva() {
        logger.debug("Inicio de método consultarParametrizacionPreventiva");
        ConsultarParametrizacionPreventiva consultarParametrizacionService = new ConsultarParametrizacionPreventiva();
        consultarParametrizacionService.execute();
        logger.debug("Fin de método consultarParametrizacionPreventiva");
        return consultarParametrizacionService.getResult();
    }

    /**
     * Método que se encarga de construir los filtros necesarios para buscar los
     * aportantes.
     *
     * @param parametrizacionCartera parametrización de cartera.
     * @return filtros de parametrización.
     */
    private FiltrosParametrizacionDTO construirFiltrosParametrizacion(
        ParametrizacionCarteraModeloDTO parametrizacionCartera) {
        logger.debug(
            "Inicio de método construirFiltrosParametrizacion(ParametrizacionCarteraModeloDTO parametrizacionCartera)");
        FiltrosParametrizacionDTO filtrosParametrizacion = new FiltrosParametrizacionDTO();
        if (parametrizacionCartera.getEstadoCartera() != null) {
            List<String> estadosCartera = new ArrayList<>();
            // Se agrega a la consulta.
            if (parametrizacionCartera.getEstadoCartera().equals(EstadoCarteraEnum.AL_DIA_Y_MOROSO)) {
                estadosCartera.add(EstadoCarteraEnum.AL_DIA.name());
                estadosCartera.add(EstadoCarteraEnum.MOROSO.name());
            } else {
                estadosCartera.add(parametrizacionCartera.getEstadoCartera().name());
            }
            filtrosParametrizacion.setEstadosCartera(estadosCartera);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate fechaActual = LocalDate.now();
        fechaActual = fechaActual.minusMonths(1L);
        String periodoFinalEmpleador = formatter.format(fechaActual);
        filtrosParametrizacion.setPeriodoFinalAportesEmpleador(periodoFinalEmpleador);
        fechaActual = fechaActual.minusMonths(parametrizacionCartera.getCantidadPeriodos());
        String periodoInicialEmpleador = formatter.format(fechaActual);
        filtrosParametrizacion.setPeriodoInicialAportesEmpleador(periodoInicialEmpleador);

        BigDecimal smlv = new BigDecimal((String) CacheManager.getParametro(ParametrosSistemaConstants.SMMLV));
        filtrosParametrizacion.setValorMinimoAportes(
            smlv.multiply(new BigDecimal(parametrizacionCartera.getValorPromedioAportes().getCantidadSalarios())));
        filtrosParametrizacion.setCantidadTrabajadoresActivos(
            Long.valueOf(parametrizacionCartera.getTrabajadoresActivos().getCantidad()));

        if (!PeriodoRegularEnum.NO_APLICAR_CRITERIO.equals(parametrizacionCartera.getPeriodosMorosos())) {
            LocalDate fechaMoraEmp = LocalDate.now();

            fechaMoraEmp = fechaMoraEmp.minusMonths(1L);
            fechaMoraEmp = fechaMoraEmp.with(lastDayOfMonth());
            Date periodoFinalMorosoEmpleador = Date
                .from(fechaMoraEmp.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
            filtrosParametrizacion.setPeriodoFinalMorosoEmpleador(periodoFinalMorosoEmpleador);

            fechaMoraEmp = fechaMoraEmp.minusMonths(parametrizacionCartera.getCantidadPeriodos());
            fechaMoraEmp = fechaMoraEmp.with(firstDayOfMonth());
            Date periodoInicialMorosoEmpleador = Date
                .from(fechaMoraEmp.atStartOfDay(ZoneId.systemDefault()).toInstant());
            filtrosParametrizacion.setPeriodoInicialMorosoEmpleador(periodoInicialMorosoEmpleador);

            filtrosParametrizacion.setSinFiltroMora(Boolean.FALSE);
        } else {
            filtrosParametrizacion.setSinFiltroMora(Boolean.TRUE);
        }

        if (parametrizacionCartera.getIncluirIndependientes() || parametrizacionCartera.getIncluirPensionados()) {
            LocalDate fechaActualInd = LocalDate.now();
            String periodoFinal = formatter.format(fechaActualInd);
            filtrosParametrizacion.setPeriodoFinalAportes(periodoFinal);
            fechaActualInd = fechaActualInd.minusMonths(parametrizacionCartera.getCantidadPeriodos());
            String periodoInicial = formatter.format(fechaActualInd);
            filtrosParametrizacion.setPeriodoInicialAportes(periodoInicial);

            if (!PeriodoRegularEnum.NO_APLICAR_CRITERIO.equals(parametrizacionCartera.getPeriodosMorosos())) {
                LocalDate fechaMora = LocalDate.now();
                fechaMora = fechaMora.with(lastDayOfMonth());
                Date periodoFinalMoroso = Date
                    .from(fechaMora.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
                filtrosParametrizacion.setPeriodoFinalMoroso(periodoFinalMoroso);

                fechaMora = fechaMora.minusMonths(parametrizacionCartera.getCantidadPeriodos());
                fechaMora = fechaMora.with(firstDayOfMonth());
                Date periodoInicialMoroso = Date.from(fechaMora.atStartOfDay(ZoneId.systemDefault()).toInstant());
                filtrosParametrizacion.setPeriodoInicialMoroso(periodoInicialMoroso);
            }
        }
        filtrosParametrizacion.setIncluirIndependientes(parametrizacionCartera.getIncluirIndependientes());
        filtrosParametrizacion.setEstadoCarteraPantalla(parametrizacionCartera.getEstadoCarteraPantalla());
        filtrosParametrizacion.setIncluirPensionados(parametrizacionCartera.getIncluirPensionados());
        filtrosParametrizacion.setMayorTrabajadoresActivos(parametrizacionCartera.getMayorTrabajadoresActivos());
        filtrosParametrizacion.setMayorValorPromedio(parametrizacionCartera.getMayorValorPromedio());
        filtrosParametrizacion.setMayorVecesMoroso(parametrizacionCartera.getMayorVecesMoroso());
        logger.debug(
            "Fin de método construirFiltrosParametrizacion(ParametrizacionCarteraModeloDTO parametrizacionCartera)");
        return filtrosParametrizacion;
    }

    /**
     * Servicio que se encarga de consultar los aportantes de acuerdo a una
     * parametrización dada.
     *
     * @param filtrosParametrizacion filtro de parametrización.
     * @return lista de los aportantes consultados.
     */
    private SimulacionPaginadaDTO consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion) {
        logger.debug("Inicio de método consultarAportantesParametrizacion");
        ConsultarAportantesParametrizacion consultarAportantesParametrizacionService = new ConsultarAportantesParametrizacion(
            filtrosParametrizacion);
        consultarAportantesParametrizacionService.execute();
        logger.debug("Fin de método consultarAportantesParametrizacion");
        return consultarAportantesParametrizacionService.getResult();
    }

    /**
     * Servicio que se encarga de consultar los aportantes de acuerdo a una
     * parametrización dada.
     *
     * @param filtrosParametrizacion filtro de parametrización.
     * @return lista de los aportantes consultados.
     */
    private List<SimulacionDTO> consultarAportantesParametrizacionFiscalizacion(
        FiltrosParametrizacionDTO filtrosParametrizacion) {
        logger.debug("Inicio de método consultarAportantesParametrizacionFiscalizacion");
        ConsultarAportantesParametrizacionFiscalizacion consultarAportantesParametrizacionService = new ConsultarAportantesParametrizacionFiscalizacion(
            filtrosParametrizacion);
        consultarAportantesParametrizacionService.execute();
        logger.debug("Fin de método consultarAportantesParametrizacionFiscalizacion");
        return consultarAportantesParametrizacionService.getResult();
    }

    /**
     * Método que invoca el servicio que consulta una persona por id de persona.
     *
     * @param idPersona id de la persona a consultar.
     * @return persona consultada.
     */
    private PersonaModeloDTO consultarPersona(Long idPersona) {
        logger.debug("Inicio de método consultarPersona");
        ConsultarPersona consultarPersonaService = new ConsultarPersona(idPersona);
        consultarPersonaService.execute();
        logger.debug("Fin de método consultarPersona");
        return consultarPersonaService.getResult();

    }

    /**
     * Método que invoca el servicio que actualiza una lista de convenios.
     *
     * @param convenios a actualizar.
     */
    private void actualizarConveniosPago(List<ConvenioPagoModeloDTO> convenios) {
        logger.debug("Inicio de método actualizarConveniosPago");
        ActualizarConveniosPago actualizarConveniosService = new ActualizarConveniosPago(convenios);
        actualizarConveniosService.execute();
        logger.debug("Fin de método actualizarConveniosPago");
    }

    /**
     * Servicio que consulta el ultimo back asignado.
     *
     * @param tipoSolicitante tipo de solicitante.
     * @param tipoTransaccion Tipo de transacción
     * @return ultimo usuario asignado.
     */
    private String obtenerUltimoBackAsignado(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                             TipoTransaccionEnum tipoTransaccion) {
        logger.debug("Inicio de método obtenerUltimoBackAsignado");
        ObtenerUltimoBackAsignado obtenerUltimoBackService = new ObtenerUltimoBackAsignado(tipoSolicitante,
            tipoTransaccion);
        obtenerUltimoBackService.execute();
        logger.debug("Fin de método obtenerUltimoBackAsignado");
        return obtenerUltimoBackService.getResult();
    }

    /**
     * Método que se encarga de obtener un usuario utilizando el método consecutivo
     * por turnos.
     *
     * @param perfil        que se desea obtener el usuario.
     * @param sede          sede del usuario.
     * @param ultimoUsuario ultimo usuario al que se le asignó una tarea.
     * @return usuario DTO.
     */
    private UsuarioDTO obtenerUsuarioConsecutivo(List<UsuarioDTO> usuarios, String ultimoUsuario) {
        logger.debug("Inicio método obtenerUsuarioConsecutivo(String perfil, String sede, String ultimoUsuario)");

        Collections.sort(usuarios, new Comparator<UsuarioDTO>() {
            @Override
            public int compare(UsuarioDTO u1, UsuarioDTO u2) {
                return u1.getNombreUsuario().toUpperCase().compareTo(u2.getNombreUsuario().toUpperCase());
            }
        });

        if (ultimoUsuario == null) {
            UsuarioDTO usuario = usuarios.iterator().next();
            return usuario;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombreUsuario().equals(ultimoUsuario)) {
                if (i != (usuarios.size() - 1)) {
                    return usuarios.get(i + 1);
                } else {
                    return usuarios.get(0);
                }
            }
        }

        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }

        return null;
    }

    /**
     * Método que se encarga de consultar los convenios que no han sido pagasod en
     * la fecha pactada.
     *
     * @return lista de los convenios no pagados.
     */
    private List<ConvenioPagoModeloDTO> consultarPagoConvenio(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarPagoConvenio");
        ConsultarPagoConvenio consultarConvenioPagoService = new ConsultarPagoConvenio(diasFestivos);
        consultarConvenioPagoService.execute();
        logger.debug("Fin de método consultarPagoConvenio");
        return consultarConvenioPagoService.getResult();
    }

    /**
     * Método encargado de construir una notificación parametrizada.
     *
     * @param solicitudPreventiva solicitud preventiva.
     * @param intento,            si es el primero o segundo intento de
     *                            notificación.
     * @param idTarea,            id de la tarea a terminar.
     * @return notificación armada.
     */
    private NotificacionParametrizadaDTO construirNotificacionParametrizada(
        SolicitudPreventivaModeloDTO solicitudPreventiva, String intento, Long idTarea) {
        logger.debug(
            "Inicio de método construirNotificacionParametrizada(SolicitudPreventivaModeloDTO solicitudPreventiva)");
        NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
        Map<String, String> params = new HashMap<>();
        params.put(NUMERO_RADICADO, solicitudPreventiva.getNumeroRadicacion());
        params.put(INTENTO, intento);
        if (idTarea != null) {
            params.put(ID_TAREA, idTarea.toString());
        }
        notificacion.setParams(params);
        notificacion.setIdSolicitud(solicitudPreventiva.getIdSolicitud());
        notificacion.setProcesoEvento(ProcesoEnum.GESTION_PREVENTIVA_CARTERA.toString());
        notificacion.setTipoTx(TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA);
        if (solicitudPreventiva.getTipoSolicitante().equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)
            || solicitudPreventiva.getTipoSolicitante().equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
            notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.REC_PLZ_LMT_PAG_PER);
        } else {
            notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.REC_PLZ_LMT_PAG);
        }
        logger.debug(
            "Fin de método construirNotificacionParametrizada(SolicitudPreventivaModeloDTO solicitudPreventiva)");
        return notificacion;
    }

    /**
     * Método encargado de realizar el llamado al servicio que consulta los detalles
     * un ciclo de fiscalización
     *
     * @param idCiclo,              identificador del ciclo
     * @param esSupervisor,         booleano que identifica si es un supervisor en
     *                              encargado de realizar la consulta
     * @param gestionManual,gestión manual
     * @return retorna la lista de detalles del ciclo de fiscalizacion
     */
    private List<SimulacionDTO> consultarDetalleCiclo(Long idCiclo, boolean esSupervisor, boolean gestionManual) {
        logger.debug("Inicio de método consultarDetalleFiscalizacion(Long idCiclo)");
        ConsultarDetalleCiclo detalleCiclo = new ConsultarDetalleCiclo(idCiclo, gestionManual, esSupervisor);
        detalleCiclo.execute();
        logger.debug("Fin de método consultarDetalleFiscalizacion(Long idCiclo)");
        return detalleCiclo.getResult();
    }

    /**
     * Método encargado de realizar el llamdo al servicio que consulta el estado de
     * la caja para un listado de personas
     *
     * @param lstPersonas, Listado de las personas
     * @return retorna el listado de las personas con el estado del afiliado
     */
    private List<EstadoDTO> consultarEstadoCaja(List<ConsultarEstadoDTO> lstPersonas) {
        logger.debug("Inicio de método buscarEstadoCajaPersonasMasivo(List<ConsultaEstadoCajaPersonaDTO> lstPersonas)");
        /* Se consulta el o los estados conrrespondienes */
        List<EstadoDTO> estadoDTOs = EstadosUtils.consultarEstadoCaja(lstPersonas);
        logger.debug("Finaliza método buscarEstadoCajaPersonasMasivo(List<ConsultaEstadoCajaPersonaDTO> lstPersonas)");
        return estadoDTOs;
    }

    /**
     * Método encargado de realizar el llamado al servicio que consulta el ciclo
     * detallado perteneciente al aportante
     *
     * @param numeroRadicado, número de radicado
     * @return retorna el detalle del ciclo del aportante
     */
    private SimulacionDTO consultarDetalleCicloAportante(String numeroRadicado) {
        logger.debug("Inicio de método consultarDetalleCicloAportante(String numeroRadicado)");
        ConsultarDetalleCicloAportante consultaDetalleCiclo = new ConsultarDetalleCicloAportante(numeroRadicado);
        consultaDetalleCiclo.execute();
        logger.debug("Finalizo de método consultarDetalleCicloAportante(String numeroRadicado)");
        return consultaDetalleCiclo.getResult();
    }

    /**
     * Método encargado de realizar el llamado al cliente de servicio que realizar
     * la validación del cierre de un ciclo.
     */
    private void verificarCierreCiclos(TipoCicloEnum tipoCiclo, Long idCicloAportante) {
        logger.debug("Inicio de método modificarEstadoUltimoCicloSolicitud");
        VerificarCierreCiclos verificarCierreService = new VerificarCierreCiclos(idCicloAportante, tipoCiclo);
        verificarCierreService.execute();
        logger.debug("Finaliza método verificarCierreCicloFiscalizacion");
    }

    /**
     * Método encargado de realizar el llamado al cliente de servicio que guarda una
     * parametrización preventiva.
     */
    private void guardarParametrizacionPreventivaCartera(
        ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO) {
        logger.debug(
            "Inicio de método guardarParametrizacionPreventiva(ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO)");
        GuardarParametrizacionPreventivaCartera guardarPreventivaService = new GuardarParametrizacionPreventivaCartera(
            parametrizacionPreventivaModeloDTO);
        guardarPreventivaService.execute();
        logger.debug(
            "Fin de método guardarParametrizacionPreventiva(ParametrizacionPreventivaModeloDTO parametrizacionPreventivaModeloDTO)");
    }

    /**
     * Método que se encarga de invocar el servicio que registra una programación de
     * un proceso automático.
     *
     * @param programaciones
     */
    private void registrarActualizarProgramacion(List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones,String user, String pass) {
        logger.debug(
            "Inicio de método registrarActualizarProgramacion(List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones)");
        RegistrarActualizarProgramacionAutomatico registrarActualizacionService = new RegistrarActualizarProgramacionAutomatico(
            programaciones);
        registrarActualizacionService.execute();
        logger.debug(
            "Fin de método registrarActualizarProgramacion(List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones)");
    }

    /**
     * Método que se encarga de realizar el llamado al servicio que consulta las
     * solicitudes pertenecientes a un ciclo aportante
     *
     * @param idCicloAportante, identificador del ciclo aportante
     * @return retorna la lista de solicitudes pertenecientes a un ciclo
     */
    private List<SolicitudModeloDTO> consultarSolicitudesCiclo(Long idCicloAportante,
                                                               List<EstadoFiscalizacionEnum> estadosFiscalizacion, TipoCicloEnum tipoCiclo) {
        logger.debug("Inicio de método consultarSolicitudesCiclo(Long idCicloAportante)");
        ConsultarSolicitudesCiclo consultaSolicitud = new ConsultarSolicitudesCiclo(idCicloAportante,
            estadosFiscalizacion, tipoCiclo);
        consultaSolicitud.execute();
        logger.debug("Inicio de método consultarSolicitudesCiclo(Long idCicloAportante)");
        return consultaSolicitud.getResult();
    }

    /**
     * Método donde se construye los parametros para llamar el servicio de calcular
     * fecha de vencimiento
     *
     * @param periodo,              periodo para el cual se va a calcular la fecha
     *                              de vencimiento
     * @param tipoAportante,        tipo de aportante (EMPLEADOR, INDEPENDIENTE,
     *                              PENSIONADO)
     * @param numeroIdentificacion, numero de identificación del aportante
     * @return retorna la fecha de vencimiento
     */
    private Long obtenerFechaVencimiento(String periodo, TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                         String numeroIdentificacion) {
        logger.debug("Inicio de método obtenerFechaVencimiento()");
        logger.debug("CarteraCompositeBusiness.obtenerFechaVencimiento->" + periodo + ":" + tipoAportante + ":"
            + numeroIdentificacion);
        Long result = null;
        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoAportante)) {
            PeriodoPagoPlanillaEnum oportunidad = PeriodoPagoPlanillaEnum.MES_VENCIDO;
            Integer cantidadPersonas = 200;
            TipoArchivoPilaEnum tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_I;
            ClaseAportanteEnum claseAportante;
            List<EmpleadorModeloDTO> empleadores = consultarEmpleadorNumero(numeroIdentificacion);

            if (cantidadPersonas >= 200) {
                claseAportante = ClaseAportanteEnum.CLASE_A;
            } else {
                claseAportante = ClaseAportanteEnum.CLASE_B;
            }
            result = calcularFechaVencimiento(empleadores.get(0).getNaturalezaJuridica(), periodo, oportunidad,
                numeroIdentificacion, tipoArchivo, cantidadPersonas, claseAportante);
        } else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoAportante)) {
            PeriodoPagoPlanillaEnum oportunidad = PeriodoPagoPlanillaEnum.MES_ACTUAL;
            Integer cantidadPersonas = 0;
            ClaseAportanteEnum claseAportante = ClaseAportanteEnum.CLASE_I;
            TipoArchivoPilaEnum tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_I;
            result = calcularFechaVencimiento(NaturalezaJuridicaEnum.PRIVADA, periodo, oportunidad,
                numeroIdentificacion, tipoArchivo, cantidadPersonas, claseAportante);
        } else {
            PeriodoPagoPlanillaEnum oportunidad = PeriodoPagoPlanillaEnum.MES_ACTUAL;
            Integer cantidadPersonas = 0;
            ClaseAportanteEnum claseAportante = ClaseAportanteEnum.CLASE_I;
            TipoArchivoPilaEnum tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_IP;
            result = calcularFechaVencimiento(NaturalezaJuridicaEnum.PRIVADA, periodo, oportunidad,
                numeroIdentificacion, tipoArchivo, cantidadPersonas, claseAportante);
        }
        logger.debug("Finaliza método obtenerFechaVencimiento()");
        // return result;

        logger.info("CarteraCompositeBusiness.obtenerFechaVencimiento()-> fin"
            + java.util.Calendar.getInstance().getTime().getTime());
        return java.util.Calendar.getInstance().getTime().getTime() + (1000 * 60 * 60 * 24); // i
    }

    /**
     * Método que se encarga de realizar el llamado al servicio calcular fecha
     * vencimiento
     *
     * @param periodo,                  periodo actual del empleador, independiente
     *                                  o pensionado
     * @param oportunidad,              Define si la fecha de vencimiento es para el
     *                                  periodo actual o periodo vencido
     * @param numeroDocumentoAportante, Identificación del aportante
     * @param tipoArchivo,              Tipo de archivo del aportante (ARCHIVO_OI_I
     *                                  o ARCHIVO_OI_IP)
     * @param cantidadPersonas,         parametro para elegirNormatividad
     * @param claseAportante,           Clase del aportante definida por el Decreto
     *                                  2388 de 2016
     * @return retorna la fecha de vencimiento
     */
    private Long calcularFechaVencimiento(NaturalezaJuridicaEnum naturalezaJuridica, String periodo,
                                          PeriodoPagoPlanillaEnum oportunidad, String numeroDocumentoAportante, TipoArchivoPilaEnum tipoArchivo,
                                          Integer cantidadPersonas, ClaseAportanteEnum claseAportante) {
        logger.debug(
            "Inicio de método calcularFechaVencimiento(String, PeriodoPagoPlanillaEnum, String numeroDocumentoAportante, TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, ClaseAportanteEnum claseAportante)");
        CalcularFechaVencimiento calcularFechaVencimiento = new CalcularFechaVencimiento(naturalezaJuridica,
            cantidadPersonas, tipoArchivo, oportunidad, claseAportante, periodo, numeroDocumentoAportante);
        calcularFechaVencimiento.execute();
        logger.debug(
            "Finaliza método calcularFechaVencimiento(String, PeriodoPagoPlanillaEnum, String numeroDocumentoAportante, TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, ClaseAportanteEnum claseAportante)");
        return calcularFechaVencimiento.getResult();
    }

    /**
     * Servicio que consulta una lista de personas sin su detalle
     *
     * @param tipoIdentificacion   Tipo de identificacion de la persona
     * @param numeroIdentificacion Numero de identificacion de la persona
     * @param primerNombre         Primer nombre de la persona
     * @param primerApellido       Primer apellido de la persona
     * @param segundoNombre        Segundo nombre de la persona
     * @param segundoApellido      Segundo apellido de la persona
     * @param razonSocial          Nombre o razon social asociado a la persona
     * @return Lista de personas
     */
    private List<PersonaDTO> buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion,
                                                      String numeroIdentificacion, String primerNombre, String primerApellido, String segundoNombre,
                                                      String segundoApellido, String razonSocial) {
        logger.debug(
            "Inicio buscarPersonasSinDetalle(TipoIdentificacionEnum, String, String, String, String, String, String)");
        BuscarPersonasSinDetalle buscarPersonasSinDetalle = new BuscarPersonasSinDetalle(primerApellido, primerNombre,
            segundoApellido, numeroIdentificacion, tipoIdentificacion, segundoNombre, razonSocial);
        buscarPersonasSinDetalle.execute();
        logger.debug(
            "Finaliza buscarPersonasSinDetalle(TipoIdentificacionEnum, String, String, String, String, String, String)");
        return buscarPersonasSinDetalle.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de acciones de
     * cobro
     *
     * @param tipoParametrizacion Tipo de parametrización
     * @return Lista de registros de parametrización de la acción de cobro
     */
    private List<Object> consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        logger.debug("Inicio de método consultarParametrizacionGestionCobro");
        ConsultarParametrizacionGestionCobro service = new ConsultarParametrizacionGestionCobro(tipoParametrizacion);
        service.execute();
        logger.debug("Fin de método consultarParametrizacionGestionCobro");
        return service.getResult();
    }

    /**
     * Método que ser encarga de obtener la solicitud de gestión de cobro manual, ya
     * sea por número de radicado o por idCartera(número de
     * operación)
     *
     * @param numeroRadicado  número de radicación perteneciente a la solicitud
     * @param numeroOperacion Número de operación
     * @return retorna la solicitud de gestión de cobro Manual Modelo DTO
     */
    private SolicitudGestionCobroManualModeloDTO consultarSolicitudGestionCobroManual(String numeroRadicado,
                                                                                      Long numeroOperacion) {
        logger.debug("Inicio de método consultarSolicitudGestionCobroManual(String)");
        ConsultarSolicitudGestionCobroManual solicitudGestionCobroManual = new ConsultarSolicitudGestionCobroManual(
            numeroOperacion, numeroRadicado);
        solicitudGestionCobroManual.execute();
        logger.debug("Finaliza método consultarSolicitudGestionCobroManual(String)");
        return solicitudGestionCobroManual.getResult();
    }

    /**
     * Método encargado de actualizar el estado de la solicitud de gestión de cobro
     * manual
     *
     * @param numeroRadicacion, número de radicación de la solicitud
     * @param estadoSolicitud,  estado de la solicitud
     */
    private void actualizarEstadoSolicitudGestionCobroManual(String numeroRadicacion,
                                                             EstadoFiscalizacionEnum estadoSolicitud) {
        logger.debug("Inicio de método actualizarEstadoSolicitudGestionCobroManual(String,EstadoCicloCarteraEnum)");
        ActualizarEstadoSolicitudGestionCobroManual solicitud = new ActualizarEstadoSolicitudGestionCobroManual(
            numeroRadicacion, estadoSolicitud);
        solicitud.execute();
        logger.debug("Finaliza método actualizarEstadoSolicitudGestionCobroManual(String,EstadoCicloCarteraEnum)");
    }

    /**
     * Servicio encargado de llamar el cliente del servicio guardar el listado de
     * agendas pertenecientes a cartera
     *
     * @param agendasCarteraModeloDTO, listado de agendas cartera
     */
    private void guardarAgendaCartera(List<AgendaCarteraModeloDTO> agendasCarteraModeloDTO) {
        logger.debug("Inicio de método guardarAgendaCartera(List<AgendaCarteraModeloDTO>)");
        GuardarAgendaCartera agendaCartera = new GuardarAgendaCartera(agendasCarteraModeloDTO);
        agendaCartera.execute();
        logger.debug("Finaliza método guardarAgendaCartera(List<AgendaCarteraModeloDTO>)");
    }

    /**
     * Método encargado de llamar el cliente del servicio que guarda un listado de
     * actividades de cartera
     *
     * @param actividadesCarteraDTO, lista de actividades de cartera
     */
    private void guardarActividadCartera(List<ActividadCarteraModeloDTO> actividadesCarteraDTO) {
        logger.debug("Inicio de método guardarActividadCartera(List<ActividadCarteraModeloDTO>)");
        GuardarActividadCartera actividadCartera = new GuardarActividadCartera(actividadesCarteraDTO);
        actividadCartera.execute();
        logger.debug("Finaliza método guardarActividadCartera(List<ActividadCarteraModeloDTO>)");
    }

    /**
     * Método encargado de llamar el cliente del servicio que guarda la bitacora de
     * cartera
     *
     * @param bitacoraCartera, bitacora de cartera a guardar
     */
    private void guardarBitacoraCartera(BitacoraCarteraDTO bitacoraCartera) {
        logger.debug("Inicio de método guardarBitacoraCartera(BitacoraCarteraDTO)");
        GuardarBitacoraCartera bitacora = new GuardarBitacoraCartera(bitacoraCartera);
        bitacora.execute();
        logger.debug("Finaliza método guardarBitacoraCartera(BitacoraCarteraDTO)");
    }

    /**
     * @param parametros
     * @return
     */
    private List<AportanteGestionManualDTO> consultarAportantesGestionManual(
        ParametrosGestionCobroManualDTO parametros) {
        try {
            logger.info("CarteraCompositeBusiness.consultarAportantesGestionManual->" + parametros);
            logger.debug("Inicia el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO)");
            ConsultarAportantesGestionManual consultarAportantesService = new ConsultarAportantesGestionManual(
                parametros);
            consultarAportantesService.execute();
            logger.debug("Finaliza el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO)");
            return consultarAportantesService.getResult();
        } catch (Exception e) {
            logger.error(
                "Finaliza el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO): Error técnico inesperado ",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * obtenerCarteraAportante(com.asopagos.dto.cartera.AportanteCarteraDTO)
     */
    @Override
    public List<CarteraModeloDTO> obtenerCarteraAportante(AportanteCarteraDTO aportanteDTO) {
        try {
            logger.debug("Inicia el servicio obtenerCarteraAportante");
            List<CarteraModeloDTO> listaCarteraDTO = consultarCarteraAportante(aportanteDTO);

            for (CarteraModeloDTO carteraDTO : listaCarteraDTO) {
                EstadoSolicitudGestionCobroEnum estado = consultarEstadoSolicitudGestionCobro(carteraDTO.getIdCartera(),
                    carteraDTO.getTipoAccionCobro());
                carteraDTO.setEstadoSolicitudActiva(estado == null ? EstadoSolicitudGestionCobroEnum.GENERADA : estado);
            }

            logger.debug("Finaliza el servicio obtenerCarteraAportante");
            return listaCarteraDTO;
        } catch (Exception e) {
            logger.error("Error en el servicio obtenerCarteraAportante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Servicio que consulta el estado de una solicitud de gestión de cobro
     *
     * @param idCartera       Identificador único del registro en cartera
     * @param tipoAccionCobro Tipo de acción de cobro
     * @return El estado de la solicitude
     */
    private EstadoSolicitudGestionCobroEnum consultarEstadoSolicitudGestionCobro(Long idCartera,
                                                                                 TipoAccionCobroEnum tipoAccionCobro) {
        logger.debug("Inicia consultarEstadoSolicitudGestionCobro");
        ConsultarEstadoSolicitudGestionCobro service = new ConsultarEstadoSolicitudGestionCobro(tipoAccionCobro,
            idCartera);
        service.execute();
        logger.debug("Finaliza consultarEstadoSolicitudGestionCobro");
        return service.getResult() != null ? EstadoSolicitudGestionCobroEnum.valueOf(service.getResult()) : null;
    }

    /**
     * Método que invoca el servicio que consulta el total de deuda de un aportante
     * registrado en cartera, agrupada por línea de cobro
     *
     * @param aportanteCarteraDTO Información del aportante
     * @return La lista de deuda, agrupada por línea de cobro
     */
    private List<CarteraModeloDTO> consultarCarteraAportante(AportanteCarteraDTO aportanteCarteraDTO) {
        logger.debug("Inicia consultarCarteraAportante");
        ConsultarCarteraAportante service = new ConsultarCarteraAportante(aportanteCarteraDTO);
        service.execute();
        logger.debug("Finaliza consultarCarteraAportante");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de empleador por identificador de
     * cartera
     *
     * @param idCartera Identificador único de cartera
     * @return La información del empleador
     */
    private EmpleadorModeloDTO consultarEmpleadorCartera(Long idCartera) {
        logger.debug("Inicia método consultarEmpleadorCartera");
        ConsultarEmpleadorCartera service = new ConsultarEmpleadorCartera(idCartera);
        service.execute();
        logger.debug("Finaliza método consultarEmpleadorCartera");
        return service.getResult();
    }
    /**
     * Método encargado de sacar de cartera a un empleador basado en sus cotizantes,
     * puede ser por cuestiones de retiro
     * @param listaCotizantesDTO Lista de cotizantes (incluye novedades a aplicar)
     * @param periodo            Periodo de evaluación
     * @param userDTO
     */
    @Override
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarDeudaPersonaCartera(List<GestionDeudaDTO> listaCotizantesDTO, String periodo, UserDTO userDTO) {
        //try {
            Instant inicio = Instant.now();
            logger.debug("Inicia el servicio registrarDeudaPersonaCartera");
            procesarDeudas(listaCotizantesDTO, periodo, userDTO);
            Instant fin = Instant.now();

            Duration duracion = Duration.between(inicio, fin);
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes();
            long segundos = duracion.getSeconds();
            long milisegundos = duracion.toMillis();
            String tiempoTranscurrido = String.format("%02d:%02d:%02d.%03d", horas, minutos, segundos, milisegundos);
            logger.debug("Finaliza el servicio registrarDeudaPersonaCartera. Duración: " + tiempoTranscurrido);
        /*} catch (Exception e) {
            logger.error("Error en el servicio registrarDeudaPersonaCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }*/
    }

    private void procesarDeudas(List<GestionDeudaDTO> listaCotizantesDTO, String periodo, UserDTO userDTO) {
        EmpleadorModeloDTO empleadorDTO = consultarEmpleadorCartera(listaCotizantesDTO.get(0).getIdCartera());
        logger.info("EmpleadorDTO: " + empleadorDTO);


        List<NovedadCarteraDTO> listaNovedadesAProcesar = new ArrayList<>();

        if (empleadorDTO == null) {
            registrarDeudaIndependientePensionado(listaCotizantesDTO, periodo);
            return;
        }

        PersonaModeloDTO personaEmpleador = consultarPersona(empleadorDTO.getIdPersona());
        List<SucursalEmpresa> listaSucursales = consultarSucursalesEmpresa(empleadorDTO.getIdEmpresa());
        Long sucursalEmpleadorId = null;

        if (!listaSucursales.isEmpty()) {
            sucursalEmpleadorId = listaSucursales.get(0).getIdSucursalEmpresa();
        }

        procesarCotizantesEnParalelo(listaCotizantesDTO, empleadorDTO, sucursalEmpleadorId, personaEmpleador, periodo, userDTO, listaNovedadesAProcesar);
    }

    private void registrarNovedadesCartera1(List<NovedadCarteraDTO> listaNovedadesAProcesar) {
        logger.info("Inicia registrarNovedadesCartera, listaNovedadesAProcesar: perdoname diosito" + listaNovedadesAProcesar.size());
        try {
            List<Callable<Void>> tareasParalelas = new LinkedList<>();
            for (NovedadCarteraDTO novCartera : listaNovedadesAProcesar) {
                Callable<Void> parallelTask = () -> {
                    RegistrarNovedadesCartera service = new RegistrarNovedadesCartera(novCartera);
                    service.execute();
                    return null;
                };
                tareasParalelas.add(parallelTask);
            }
            if (!tareasParalelas.isEmpty()) {
                mes.invokeAll(tareasParalelas);
            }
            logger.info("Finaliza registrarNovedadesCartera");
        } catch (InterruptedException e) {
            logger.error("Ocurrió un error inesperado en registrarNovedadesCartera" + e);
        }
    }

    private CompletableFuture<Void> registrarNovedadesCartera(List<NovedadCarteraDTO> listaNovedadesAProcesar, EmpleadorModeloDTO empleadorDTO, String periodo) {
        int procesadoresDisponibles = Runtime.getRuntime().availableProcessors();
        final int TAMANO_LOTE = (int) Math.max(1, Math.floor(procesadoresDisponibles * 0.8));  // 80% de los procesadores disponibles

        logger.info("Inicia RegistrarNovedadesCartera...");
        logger.info("TAMANO_LOTE: " + TAMANO_LOTE);
        ExecutorService executor = Executors.newFixedThreadPool(TAMANO_LOTE);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                List<CompletableFuture<Void>> futures = IntStream.range(0, listaNovedadesAProcesar.size())
                        .boxed()
                        .collect(Collectors.groupingBy(i -> i / TAMANO_LOTE))
                        .values()
                        .stream()
                        .map(indices -> indices.stream().map(listaNovedadesAProcesar::get).collect(Collectors.toList()))
                        .map(lote -> CompletableFuture.runAsync(() -> {
                            lote.forEach(novedadCarteraDTO -> {
                                RegistrarNovedadesCartera service = new RegistrarNovedadesCartera(novedadCarteraDTO);
                                service.execute();
                            });
                        }, executor).exceptionally(ex -> {
                            logger.warn("Error procesando Novedad Cartera: " + ex.getMessage());
                            return null;
                        }))
                        .collect(Collectors.toList());

                // Esperar a que todas las tareas se completen
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } finally {
                shutdownExecutor(executor);
            }
        }, executor);

        CompletableFuture<Void> future2 = future.thenRun(() -> 
            actualizarDeudaPresuntaCartera(empleadorDTO.getTipoIdentificacion(), empleadorDTO.getNumeroIdentificacion(), periodo, TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)
        );

        return future2;
    }
    

    /**
     * se encarga de procesar una lista de cotizantes en paralelo, utilizando un número de hilos determinado por los procesadores disponibles o un máximo preestablecido.
     * Si la cantidad de cotizantes procesados alcanza un límite específico, se inicia un proceso asincrónico para manejar las novedades.
     * @param listaCotizantesDTO
     * @param empleadorDTO
     * @param sucursalEmpleadorId
     * @param personaEmpleador
     * @param periodo
     * @param userDTO
     * @param listaNovedadesAProcesar
     */
    
     public void procesarCotizantesEnParalelo(List<GestionDeudaDTO> listaCotizantesDTO, EmpleadorModeloDTO empleadorDTO, Long sucursalEmpleadorId, PersonaModeloDTO personaEmpleador, String periodo, UserDTO userDTO, List<NovedadCarteraDTO> listaNovedadesAProcesar) {
        int procesadoresDisponibles = Runtime.getRuntime().availableProcessors();
        //final int TAMANO_LOTE = Math.min(procesadoresDisponibles, 10);
        final int TAMANO_LOTE = Math.max(procesadoresDisponibles - 5, 1);
        logger.info("TAMANO_LOTE: " + TAMANO_LOTE);
        final int LIMITE_ASINCRONO = 300;
        boolean isExecuteASincrono = false;
        CompletableFuture<Void> futuroAsincrono = null;

        ExecutorService executor = Executors.newFixedThreadPool(TAMANO_LOTE);

        try {
            List<List<GestionDeudaDTO>> lotes = new ArrayList<>();
            for (int i = 0; i < listaCotizantesDTO.size(); i += TAMANO_LOTE) {
                lotes.add(listaCotizantesDTO.subList(i, Math.min(i + TAMANO_LOTE, listaCotizantesDTO.size())));
            }

            int cotizantesProcesados = 0;

            for (List<GestionDeudaDTO> lote : lotes) {
                CompletableFuture.allOf(lote.stream()
                        .map(cotizanteDTO -> CompletableFuture.runAsync(() -> {
                            procesarCotizante(cotizanteDTO, empleadorDTO, sucursalEmpleadorId, personaEmpleador, periodo, userDTO, listaNovedadesAProcesar);
                        }, executor))
                        .toArray(CompletableFuture[]::new))
                    .join();
                cotizantesProcesados += lote.size();
                logger.info("Lote procesado. Cotizantes procesados: " + cotizantesProcesados);
                //if (cotizantesProcesados >= LIMITE_ASINCRONO && !isExecuteASincrono) {
                //    logger.info("Iniciando proceso asincrónico para manejo de novedades.");
                //    futuroAsincrono = CompletableFuture.runAsync(() -> registrarNovedadesCartera(new ArrayList<>(listaNovedadesAProcesar)));
                //    listaNovedadesAProcesar.clear();
                //    isExecuteASincrono = true;
                //}
            }


            // Esperar a que el futuro asincrónico se complete si existe
            if (futuroAsincrono != null) {
                futuroAsincrono.join();
                logger.info("Proceso asincrónico completado.");
            }

            // Registrar las novedades que no se enviaron asincrónicamente
            if( listaNovedadesAProcesar.size() >= 100){
                registrarNovedadesCartera(listaNovedadesAProcesar,empleadorDTO,periodo);
            }else{
                for(NovedadCarteraDTO novedad : listaNovedadesAProcesar){
                    RegistrarNovedadesCartera novedadesCartera = new RegistrarNovedadesCartera(novedad);
                    novedadesCartera.execute();
                }
                actualizarDeudaPresuntaCartera(empleadorDTO.getTipoIdentificacion(), empleadorDTO.getNumeroIdentificacion(), periodo, TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
            }

        } catch (Exception e) {
            logger.error("Error durante el procesamiento paralelo de cotizantes: " + e.getMessage(), e);
        } finally {
            shutdownExecutor(executor);
        }
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

    private void procesarCotizante(GestionDeudaDTO cotizanteDTO, EmpleadorModeloDTO empleadorDTO, Long sucursalEmpleadorId, PersonaModeloDTO personaEmpleador, String periodo, UserDTO userDTO, List<NovedadCarteraDTO> listaNovedadesAProcesar) {
        // Implementa la lógica para procesar un único cotizante, incluyendo la creación de registros y el manejo de novedades.
        if (cotizanteDTO.getIdPersona() == null) {
            // Almacena la nueva persona
            PersonaDTO personaDTO = new PersonaDTO();
            personaDTO.setTipoIdentificacion(cotizanteDTO.getTipoIdentificacion());
            personaDTO.setNumeroIdentificacion(cotizanteDTO.getNumeroIdentificacion());
            personaDTO.setPrimerNombre(cotizanteDTO.getPrimerNombre());
            personaDTO.setPrimerApellido(cotizanteDTO.getPrimerApellido());
            personaDTO.setSegundoNombre(cotizanteDTO.getSegundoNombre());
            personaDTO.setSegundoApellido(cotizanteDTO.getSegundoApellido());


            // Crea el afiliado
            AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
            afiliadoDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            afiliadoDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
            afiliadoDTO.setPersona(personaDTO);
            afiliadoDTO.setIdEmpleador(empleadorDTO.getIdEmpleador());
            afiliadoDTO.setSucursalEmpleadorId(sucursalEmpleadorId);
            afiliadoDTO = crearAfiliado(afiliadoDTO);
            cotizanteDTO.setIdPersona(afiliadoDTO.getPersona().getIdPersona());


        } else {
            List<RolAfiliadoEmpleadorDTO> listaRolDTO = consultarRolesAfiliado(cotizanteDTO.getTipoIdentificacion(),
                cotizanteDTO.getNumeroIdentificacion(), TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);

            /*
            - listaRolDTO.stream(): Convierte la lista listaRolDTO en un stream, que es una secuencia de elementos que soporta operaciones de agregación secuenciales y paralelas.
            - .anyMatch(rol -> ...): Esta operación intermedia recorre los elementos del stream y evalúa la expresión lambda proporcionada en cada elemento.
              Si al menos un elemento cumple con la condición especificada en la expresión lambda, anyMatch devuelve true; de lo contrario, devuelve false.
            - rol -> rol.getEmpleador() != null && rol.getEmpleador().getTipoIdentificacion().equals(empleadorDTO.getTipoIdentificacion()) && rol.getEmpleador().getNumeroIdentificacion().equals(empleadorDTO.getNumeroIdentificacion()):
              Es una expresión lambda que define la condición para cada RolAfiliadoEmpleadorDTO en el stream. La condición verifica que el campo empleador no sea nulo
              y que el tipo y número de identificación del empleador en el rol coincidan con los del empleadorDTO proporcionado.
            */
            boolean existeRol = listaRolDTO.stream()
                .anyMatch(rol -> rol.getEmpleador() != null
                    && rol.getEmpleador().getTipoIdentificacion().equals(empleadorDTO.getTipoIdentificacion())
                    && rol.getEmpleador().getNumeroIdentificacion().equals(empleadorDTO.getNumeroIdentificacion()));

            if (!existeRol && (cotizanteDTO.getEstadoCotizante().equals("ACTIVO") || cotizanteDTO.getEstadoCotizante().equals("INACTIVO"))) {
                // Solo buscar y crear un nuevo afiliado si es necesario
                PersonaDTO personaDTO = buscarPersonasSinDetalle(cotizanteDTO.getTipoIdentificacion(),
                    cotizanteDTO.getNumeroIdentificacion(), null, null, null, null, null).get(0);

                AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
                afiliadoDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                afiliadoDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                afiliadoDTO.setPersona(personaDTO);
                afiliadoDTO.setIdEmpleador(empleadorDTO.getIdEmpleador());
                afiliadoDTO.setSucursalEmpleadorId(sucursalEmpleadorId);
                afiliadoDTO = crearAfiliado(afiliadoDTO);

                cotizanteDTO.setIdPersona(afiliadoDTO.getPersona().getIdPersona());
            }
        }

        // Registra la información en CarteraDependiente
        CarteraDependienteModeloDTO carteraDependienteDTO = cotizanteDTO.obtenerCarteraDependiente();
        guardarCarteraDependiente(carteraDependienteDTO);


        // Registra las novedades PILA
        if (cotizanteDTO.getListaNovedades() != null) {
            PersonaModeloDTO personaCotizante = consultarPersona(cotizanteDTO.getIdPersona());
            NovedadCarteraDTO novedadCarteraDTO = new NovedadCarteraDTO();
            novedadCarteraDTO.setCanal(CanalRecepcionEnum.CARTERA);
            novedadCarteraDTO.setEsTrabajadorReintegrable(Boolean.TRUE);
            novedadCarteraDTO.setPersonaAportante(personaEmpleador);
            novedadCarteraDTO.setPersonaCotizante(personaCotizante);
            List<NovedadPilaDTO> listaNovedadesPila = new ArrayList<>();

            //procesarNovedadesEnParalelo(procesosDisponibles, cotizanteDTO.getListaNovedades(), cotizanteDTO, periodo, personaCotizante, listaNovedadesPila, userDTO);
            for(CarteraNovedadModeloDTO carteraNovedad : cotizanteDTO.getListaNovedades()){
                procesarNovedad(carteraNovedad, cotizanteDTO, periodo, personaCotizante, listaNovedadesPila, userDTO);
            }


            if (!listaNovedadesPila.isEmpty()) {
                novedadCarteraDTO.setNovedades(listaNovedadesPila);
                listaNovedadesAProcesar.add(novedadCarteraDTO);
                //RegistrarNovedadesCartera service = new RegistrarNovedadesCartera(listaNovedades);
                //service.execute();
            }
        }
    }

    private void procesarNovedad(CarteraNovedadModeloDTO carteraNovedadDTO, GestionDeudaDTO cotizanteDTO, String periodo, PersonaModeloDTO personaCotizante, List<NovedadPilaDTO> listaNovedadesPila, UserDTO userDTO) {
        // Almacena en la tabla de novedades de cartera
        if (carteraNovedadDTO.getFechaInicio() != null || carteraNovedadDTO.getFechaFin() != null) {
            carteraNovedadDTO.setIdPersona(cotizanteDTO.getIdPersona());
            /*if (TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.equals(carteraNovedadDTO.getTipoNovedad())) {
                carteraNovedadDTO.setFechaInicio(formatDate(periodo));
            }*/
            guardarCarteraNovedad(carteraNovedadDTO);
        }

        // Registra las novedades a aplicar
        if (carteraNovedadDTO.getAplicar() && !TipoTransaccionEnum.PROCESO_INTERNO_DE_LA_CCF.equals(carteraNovedadDTO.getTipoNovedad())) {
            NovedadPilaDTO novedadDTO = new NovedadPilaDTO();
            novedadDTO.setAccionNovedad(carteraNovedadDTO.getAplicar() ? MarcaAccionNovedadEnum.APLICAR_NOVEDAD.name()
                : MarcaAccionNovedadEnum.NO_APLICADA.name());
            novedadDTO.setEsTrabajadorReintegrable(Boolean.TRUE);
            novedadDTO.setTipoIdentificacionCotizante(personaCotizante.getTipoIdentificacion());
            novedadDTO.setNumeroIdentificacionCotizante(personaCotizante.getNumeroIdentificacion());
            novedadDTO.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            novedadDTO.setFechaInicioNovedad(
                carteraNovedadDTO.getFechaInicio() != null ? new Date(carteraNovedadDTO.getFechaInicio()) : null);
            novedadDTO.setFechaFinNovedad(
                carteraNovedadDTO.getFechaFin() != null ? new Date(carteraNovedadDTO.getFechaFin()) : null);
            novedadDTO.setTipoTransaccion(carteraNovedadDTO.getTipoNovedad());
            novedadDTO.setEsIngreso(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO
                .equals(carteraNovedadDTO.getTipoNovedad()));

            if (novedadDTO.getEsIngreso()) {
                novedadDTO.setTipoTransaccion(null);
            }

            novedadDTO.setEsRetiro(
                TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.equals(carteraNovedadDTO.getTipoNovedad()));
            novedadDTO.setMensajeNovedad("La novedad fue registrada");
            listaNovedadesPila.add(novedadDTO);
        }
        if (TipoTransaccionEnum.PROCESO_INTERNO_DE_LA_CCF.equals(carteraNovedadDTO.getTipoNovedad())) {
            DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO = new DocumentosSeguimientoGestionDTO(null, cotizanteDTO.getIdOperacion(),
                new Date(), "PROCESO INTERNO CCF ", "Personal", "OTRO", userDTO.getNombreUsuario(), cotizanteDTO.getIdDocumento(), cotizanteDTO.getObservaciones());
            //logger.info("documentosSeguimientoGestionDTO" + new ObjectMapper().writeValueAsString(documentosSeguimientoGestionDTO));
            //logger.info(documentosSeguimientoGestionDTO.toString());
            createDocumentosSeguimientoGestion(documentosSeguimientoGestionDTO);
        }
    }


    //@Override
    public void registrarDeudaPersonaCartera1(List<GestionDeudaDTO> listaCotizantesDTO, String periodo, UserDTO userDTO) {
        try {
            logger.debug("Inicia el servicio registrarDeudaPersonaCartera");
            EmpleadorModeloDTO empleadorDTO = consultarEmpleadorCartera(listaCotizantesDTO.get(0).getIdCartera());
            if (empleadorDTO != null) {
                PersonaModeloDTO personaEmpleador = consultarPersona(empleadorDTO.getIdPersona());
                List<SucursalEmpresa> listaSucursales = consultarSucursalesEmpresa(empleadorDTO.getIdEmpresa());
                Long sucursalEmpleadorId = null;

                if (!listaSucursales.isEmpty()) {
                    sucursalEmpleadorId = listaSucursales.get(0).getIdSucursalEmpresa();
                }

                for (GestionDeudaDTO cotizanteDTO : listaCotizantesDTO) {
                    if (cotizanteDTO.getIdPersona() == null) {
                        // Almacena la nueva persona
                        PersonaDTO personaDTO = new PersonaDTO();
                        personaDTO.setTipoIdentificacion(cotizanteDTO.getTipoIdentificacion());
                        personaDTO.setNumeroIdentificacion(cotizanteDTO.getNumeroIdentificacion());
                        personaDTO.setPrimerNombre(cotizanteDTO.getPrimerNombre());
                        personaDTO.setPrimerApellido(cotizanteDTO.getPrimerApellido());
                        personaDTO.setSegundoNombre(cotizanteDTO.getSegundoNombre());
                        personaDTO.setSegundoApellido(cotizanteDTO.getSegundoApellido());


                        // Crea el afiliado
                        AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
                        afiliadoDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                        afiliadoDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                        afiliadoDTO.setPersona(personaDTO);
                        afiliadoDTO.setIdEmpleador(empleadorDTO.getIdEmpleador());
                        afiliadoDTO.setSucursalEmpleadorId(sucursalEmpleadorId);
                        afiliadoDTO = crearAfiliado(afiliadoDTO);
                        cotizanteDTO.setIdPersona(afiliadoDTO.getPersona().getIdPersona());


                    } else {
                        List<RolAfiliadoEmpleadorDTO> listaRolDTO = consultarRolesAfiliado(cotizanteDTO.getTipoIdentificacion(),
                            cotizanteDTO.getNumeroIdentificacion(), TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                        Boolean existeRol = Boolean.FALSE;

                        for (RolAfiliadoEmpleadorDTO rol : listaRolDTO) {
                            if (rol.getEmpleador() != null
                                && rol.getEmpleador().getTipoIdentificacion().equals(empleadorDTO.getTipoIdentificacion())
                                && rol.getEmpleador().getNumeroIdentificacion().equals(empleadorDTO.getNumeroIdentificacion())) {
                                existeRol = Boolean.TRUE;
                                break;
                            }
                        }

                        if (!existeRol) {
                            if (cotizanteDTO.getEstadoCotizante().equals("ACTIVO") || cotizanteDTO.getEstadoCotizante().equals("INACTIVO")) {
                                // Crea el rol afiliado del cotizante asociado al empleador
                                PersonaDTO personaDTO = buscarPersonasSinDetalle(cotizanteDTO.getTipoIdentificacion(),
                                    cotizanteDTO.getNumeroIdentificacion(), null, null, null, null, null).get(0);
                                AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
                                afiliadoDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                                afiliadoDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                                afiliadoDTO.setPersona(personaDTO);
                                afiliadoDTO.setIdEmpleador(empleadorDTO.getIdEmpleador());
                                afiliadoDTO.setSucursalEmpleadorId(sucursalEmpleadorId);
                                afiliadoDTO = crearAfiliado(afiliadoDTO);
                                cotizanteDTO.setIdPersona(afiliadoDTO.getPersona().getIdPersona());
                            }
                        }
                    }

                    // Registra la información en CarteraDependiente
                    CarteraDependienteModeloDTO carteraDependienteDTO = cotizanteDTO.obtenerCarteraDependiente();
                    guardarCarteraDependiente(carteraDependienteDTO);


                    // Registra las novedades PILA
                    if (cotizanteDTO.getListaNovedades() != null) {
                        PersonaModeloDTO personaCotizante = consultarPersona(cotizanteDTO.getIdPersona());
                        NovedadCarteraDTO listaNovedades = new NovedadCarteraDTO();
                        listaNovedades.setCanal(CanalRecepcionEnum.CARTERA);
                        listaNovedades.setEsTrabajadorReintegrable(Boolean.TRUE);
                        listaNovedades.setPersonaAportante(personaEmpleador);
                        listaNovedades.setPersonaCotizante(personaCotizante);
                        List<NovedadPilaDTO> listaNovedadesPila = new ArrayList<>();

                        for (CarteraNovedadModeloDTO carteraNovedadDTO : cotizanteDTO.getListaNovedades()) {
                            // Almacena en la tabla de novedades de cartera
                            if (carteraNovedadDTO.getFechaInicio() != null || carteraNovedadDTO.getFechaFin() != null) {
                                carteraNovedadDTO.setIdPersona(cotizanteDTO.getIdPersona());
                                if (TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.equals(carteraNovedadDTO.getTipoNovedad())) {
                                    carteraNovedadDTO.setFechaInicio(formatDate(periodo));
                                }
                                guardarCarteraNovedad(carteraNovedadDTO);
                            }

                            // Registra las novedades a aplicar
                            if (carteraNovedadDTO.getAplicar() && !TipoTransaccionEnum.PROCESO_INTERNO_DE_LA_CCF.equals(carteraNovedadDTO.getTipoNovedad())) {
                                NovedadPilaDTO novedadDTO = new NovedadPilaDTO();
                                novedadDTO.setAccionNovedad(carteraNovedadDTO.getAplicar() ? MarcaAccionNovedadEnum.APLICAR_NOVEDAD.name()
                                    : MarcaAccionNovedadEnum.NO_APLICADA.name());
                                novedadDTO.setEsTrabajadorReintegrable(Boolean.TRUE);
                                novedadDTO.setTipoIdentificacionCotizante(personaCotizante.getTipoIdentificacion());
                                novedadDTO.setNumeroIdentificacionCotizante(personaCotizante.getNumeroIdentificacion());
                                novedadDTO.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                                novedadDTO.setFechaInicioNovedad(
                                    carteraNovedadDTO.getFechaInicio() != null ? new Date(carteraNovedadDTO.getFechaInicio()) : null);
                                novedadDTO.setFechaFinNovedad(
                                    carteraNovedadDTO.getFechaFin() != null ? new Date(carteraNovedadDTO.getFechaFin()) : null);
                                novedadDTO.setTipoTransaccion(carteraNovedadDTO.getTipoNovedad());
                                novedadDTO.setEsIngreso(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO
                                    .equals(carteraNovedadDTO.getTipoNovedad()));

                                if (novedadDTO.getEsIngreso()) {
                                    novedadDTO.setTipoTransaccion(null);
                                }

                                novedadDTO.setEsRetiro(
                                    TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.equals(carteraNovedadDTO.getTipoNovedad()));
                                novedadDTO.setMensajeNovedad("La novedad fue registrada");
                                listaNovedadesPila.add(novedadDTO);
                            }
                            if (TipoTransaccionEnum.PROCESO_INTERNO_DE_LA_CCF.equals(carteraNovedadDTO.getTipoNovedad())) {
                                DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO = new DocumentosSeguimientoGestionDTO(null, cotizanteDTO.getIdOperacion(),
                                    new Date(), "PROCESO INTERNO CCF ", "Personal", "OTRO", userDTO.getNombreUsuario(), cotizanteDTO.getIdDocumento(), cotizanteDTO.getObservaciones());
                                logger.info("documentosSeguimientoGestionDTO" + new ObjectMapper().writeValueAsString(documentosSeguimientoGestionDTO));
                                createDocumentosSeguimientoGestion(documentosSeguimientoGestionDTO);
                            }
                        }

                        if (!listaNovedadesPila.isEmpty()) {
                            listaNovedades.setNovedades(listaNovedadesPila);
                            RegistrarNovedadesCartera service = new RegistrarNovedadesCartera(listaNovedades);
                            service.execute();
                        }
                    }
                }

                // Actualiza la cartera del aportante
                actualizarDeudaPresuntaCartera(empleadorDTO.getTipoIdentificacion(), empleadorDTO.getNumeroIdentificacion(), periodo,
                    TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
            } else {
                registrarDeudaIndependientePensionado(listaCotizantesDTO, periodo);
            }
            logger.debug("Finaliza el servicio registrarDeudaPersonaCartera");
        } catch (Exception e) {
            logger.error("Error en el servicio registrarDeudaPersonaCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private Long formatDate(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            date = dateFormat.parse(fecha.concat("-01"));
            return date.getTime();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private DocumentosSeguimientoGestionDTO createDocumentosSeguimientoGestion(DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO) {
        logger.info("Inicia documentosSeguimientoGestionDTO");
        CreateDocumentosSeguimientoGestion service = new CreateDocumentosSeguimientoGestion(documentosSeguimientoGestionDTO);
        service.execute();
        logger.info("Finaliza documentosSeguimientoGestionDTO");
        return service.getResult();

    }

    void actualizarDeudaRealDeudaPresunta(BigDecimal deudaReal, BigDecimal deudaPresunta, Long idCarteraDependiente,
                                          Long idCartera) {
        logger.info("entro deudaReal:::" + deudaReal);
        ActualizarDeudaRealDeudaPresunta actualizarDeudaRealDeudaPresunta = new ActualizarDeudaRealDeudaPresunta(
            deudaReal, deudaPresunta, idCarteraDependiente, idCartera);
        actualizarDeudaRealDeudaPresunta.execute();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#generarLiquidacionAportes(com.asopagos.enumeraciones.personas
     * .
     * TipoIdentificacionEnum, java.lang.String,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum)
     **/
    @Override
    public LiquidacionAporteCarteraDTO generarLiquidacionAportes(TipoIdentificacionEnum tipoIdentificacion,
                                                                 String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                                                 TipoLineaCobroEnum tipoLineaCobro) {
        CarteraModeloDTO carteraDTO = null;
        DocumentoCarteraModeloDTO documentoCarteraDTO = null;

        try {
            logger.info("Inicia el servicio generarLiquidacionAportes");
            ObjectMapper mapper = new ObjectMapper();
            String idECM = null;
            logger.info("NUMERO DE IDENTIFICACION :: " + numeroIdentificacion);
            List<CarteraModeloDTO> listaCarteraDTO = consultarPeriodosAportanteLineaCobro(tipoIdentificacion,
                numeroIdentificacion, tipoAportante, tipoLineaCobro);
            LiquidacionAporteCarteraDTO liquidacionDTO = null;

            if (!listaCarteraDTO.isEmpty()) {
                logger.info("INGRESA AL PRIMER IF DE GENERARLIQUIDACION");
                carteraDTO = listaCarteraDTO.get(listaCarteraDTO.size() - 1);
                Long idCartera = carteraDTO.getIdCartera();
                ParametrosComunicadoDTO parametrosDTO = new ParametrosComunicadoDTO();
                parametrosDTO.setIdCartera(idCartera);
                Map<String, Object> parametros = new HashMap<>();
                parametros.put(ConstanteCartera.ID_CARTERA, idCartera);
                parametros.put(ConstanteCartera.IDENTIFICADOR, idCartera);
                parametrosDTO.setParams(parametros);

                switch (carteraDTO.getTipoAccionCobro()) {
                    case A01:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.A1);
                        break;
                    case AB1:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.A1);
                        break;
                    case BC1:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.B1);
                        break;
                    case CD1:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.C1);
                        break;
                    case DE1:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.D1);
                        break;
                    case EF1:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.E1);
                        break;
                    case A02:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.A2);
                        break;
                    case AB2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.A2);
                        break;
                    case BC2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.B2);
                        break;
                    case CD2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.C2);
                        break;
                    case DE2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.D2);
                        break;
                    case EF2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.E2);
                        break;
                    case FG2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.F2);
                        break;
                    case GH2:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.G2);
                        break;
                    case LC40:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.LC4A);
                        break;
                    case L4AC:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.LC4A);
                        break;
                    case LC50:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.LC5A);
                        break;
                    case L5AC:
                        carteraDTO.setTipoAccionCobro(TipoAccionCobroEnum.LC5A);
                        break;
                    default:
                        break;
                }

                logger.info("Inicia guardar el comunicado en el ECM y en DocumentoCartera");
                TipoTransaccionEnum tipoTransaccion = obtenerTipoTransaccion(carteraDTO.getTipoAccionCobro());
                logger.info("Inicia guardar ECM");
                logger.info(tipoTransaccion);
                logger.info(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MAN);
                logger.info(ConstanteCartera.IDENTIFICADOR);
                logger.info(ConstanteCartera.ID_CARTERA);
                idECM = guardarComunicadoECM(tipoTransaccion, EtiquetaPlantillaComunicadoEnum.LIQ_APO_MAN,
                    parametrosDTO);
                InformacionArchivoDTO informacionArchivoDTO = obtenerArchivo(idECM);
                documentoCarteraDTO = transformarArchivoDocumentoCartera(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MAN,
                    informacionArchivoDTO, idCartera, carteraDTO.getTipoAccionCobro());
                logger.info("Inicia guardar DOCUMENTO CARTERA");
                documentoCarteraDTO = guardarDocumentoCartera(documentoCarteraDTO);
                logger.info("Fin guardar DOCUMENTO CARTERA");
                liquidacionDTO = new LiquidacionAporteCarteraDTO(carteraDTO);
                liquidacionDTO.setConsecutivoLiquidacion(documentoCarteraDTO.getConsecutivoLiquidacion());
                liquidacionDTO.setIdECM(idECM);
                liquidacionDTO.setNombreCcf(obtenerParametro(ParametrosSistemaConstants.NOMBRE_CCF));
                liquidacionDTO.setDepartamentoCcf(obtenerParametro(ParametrosSistemaConstants.DEPARTAMENTO_CCF));
                liquidacionDTO.setCiudadCcf(obtenerParametro(ParametrosSistemaConstants.CIUDAD_CCF));
                liquidacionDTO.setDireccionCcf(obtenerParametro(ParametrosSistemaConstants.DIRECCION_CCF));
                liquidacionDTO.setTelefonoCcf(obtenerParametro(ParametrosSistemaConstants.TELEFONO_CCF));
                liquidacionDTO.setWebCcf(obtenerParametro(ParametrosSistemaConstants.WEB_CCF));
                liquidacionDTO.setResponsableCcf(obtenerParametro(ParametrosSistemaConstants.RESPONSABLE_CCF));
                liquidacionDTO
                    .setCargoResponsableCcf(obtenerParametro(ParametrosSistemaConstants.CARGO_RESPONSABLE_CCF));
                liquidacionDTO.setLogoDeLaCcf(obtenerParametro(ParametrosSistemaConstants.LOGO_DE_LA_CCF));
                liquidacionDTO.setLogoSuperservicios(obtenerParametro(ParametrosSistemaConstants.LOGO_SUPERSUBSIDIO));
                if (Objects.isNull(idECM)) {
                    logger.info("NO GENERO DOCUMENTO :: IDECM :: null");
                } else {
                    logger.info("GENERO DOCUMENTO :: IDECM :: " + idECM);
                }
            } else {
                logger.info("listaCarteraDTO :: esta vacio");
            }

            return liquidacionDTO;
        } catch (Exception e) {
            logger.error("Error en el servicio generarLiquidacionAportes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que invoca el servicio que actualiza la deuda presunta de un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param periodoEvaluacion    Periodo de evaluación. Formato YYYY-MM
     * @param tipoAportante        Tipo de aportante
     */
    private void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        logger.info("Inicia actualizarDeudaPresuntaCartera composite");
        ActualizarDeudaPresuntaCartera service = new ActualizarDeudaPresuntaCartera(tipoAportante, numeroIdentificacion,
            periodoEvaluacion, tipoIdentificacion);
        service.execute();
        logger.debug("Finaliza actualizarDeudaPresuntaCartera composite");
    }

    /**
     * Método que invoca el servicio de consulta de las sucursales de una empresa
     *
     * @param idEmpresa Identificador único de la empresa
     * @return La lista de sucursales
     */
    private List<SucursalEmpresa> consultarSucursalesEmpresa(Long idEmpresa) {
        logger.debug("Inicia consultarSucursalesEmpresa");
        ConsultarSucursalesEmpresa service = new ConsultarSucursalesEmpresa(idEmpresa);
        service.execute();
        logger.debug("Finaliza consultarSucursalesEmpresa");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de registro de un afiliado
     *
     * @param afiliadoDTO Información del afiliado
     * @return La información actualizada del afiliado
     */
    private AfiliadoInDTO crearAfiliado(AfiliadoInDTO afiliadoDTO) {
        logger.debug("Inicia método crearAfiliado");
        CrearAfiliado service = new CrearAfiliado(afiliadoDTO);
        service.execute();
        logger.debug("Finaliza método crearAfiliado");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento en
     * <code>CarteraDepediente</code>
     *
     * @param carteraDependienteDTO Información del registro a almacenar
     */
    private void guardarCarteraDependiente(CarteraDependienteModeloDTO carteraDependienteDTO) {
        logger.debug("Inicia método guardarCarteraDependiente");
        GuardarCarteraDependiente service = new GuardarCarteraDependiente(carteraDependienteDTO);
        service.execute();
        logger.debug("Finaliza método guardarCarteraDependiente");
    }

    /**
     * Método que invoca el servicio de almacenamiento de novedades registradas en
     * cartera
     *
     * @param carteraNovedadDTO Información de la novedad a guardar
     */
    private void guardarCarteraNovedad(CarteraNovedadModeloDTO carteraNovedadDTO) {
        logger.debug("Inicia método guardarCarteraNovedad");
        GuardarCarteraNovedad service = new GuardarCarteraNovedad(carteraNovedadDTO);
        service.execute();
        logger.debug("Finaliza método guardarCarteraNovedad");
    }

    /**
     * Método que invoca el servicio de consultar el empleador solo por numero de
     * identificación
     *
     * @param numeroIdentificacion Número de identificación del empleador
     * @return Lista de empleadores
     */
    private List<EmpleadorModeloDTO> consultarEmpleadorNumero(String numeroIdentificacion) {
        logger.debug("Inicia método consultarEmpleadorNumero");
        ConsultarEmpleadorNumero empleadoresService = new ConsultarEmpleadorNumero(numeroIdentificacion);
        empleadoresService.execute();
        logger.debug("Finaliza método consultarEmpleadorNumero");
        return empleadoresService.getResult();
    }

    /**
     * Método que obtiene un parámetro almacenado en caché
     *
     * @param llave Nombre del parámetro
     * @return El valor del poarámetro, como <code>String</code>
     */
    private String obtenerParametro(String llave) {
        return CacheManager.getParametro(llave) != null ? CacheManager.getParametro(llave).toString() : null;
    }

    /**
     * Método que obtiene el tipo de transacción asociada a una acción de cobro
     *
     * @param accionCobro Tipo de acción de cobro
     * @return El tipo de transacción asociada
     */
    private TipoTransaccionEnum obtenerTipoTransaccion(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicia método obtenerTipoTransaccion");
        TipoTransaccionEnum tipoTransaccion = null;

        switch (accionCobro) {
            case A1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1A_ELECTRONICO;
                break;
            case B1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1B_ELECTRONICO;
                break;
            case C1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1C_ELECTRONICO;
                break;
            case D1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1D_ELECTRONICO;
                break;
            case E1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1E_ELECTRONICO;
                break;
            case F1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1F_ELECTRONICO;
                break;
            case G1:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_1G_ELECTRONICO;
                break;
            case A2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2A_ELECTRONICO;
                break;
            case B2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2B_ELECTRONICO;
                break;
            case C2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2C_ELECTRONICO;
                break;
            case D2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2D_ELECTRONICO;
                break;
            case E2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2E;
                break;
            case F2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2F_ELECTRONICO;
                break;
            case G2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2G_ELECTRONICO;
                break;
            case H2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2H_ELECTRONICO;
                break;
            case I2:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_2I_ELECTRONICO;
                break;
            case LC2A:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC2A_ELECTRONICO;
                break;
            case LC3A:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC3A_ELECTRONICO;
                break;
            case LC4A:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC4A_ELECTRONICO;
                break;
            case LC4C:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC4C_ELECTRONICO;
                break;
            case LC5A:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC5A_ELECTRONICO;
                break;
            case LC5C:
                tipoTransaccion = TipoTransaccionEnum.ACCION_COBRO_LC5C_ELECTRONICO;
                break;
            default:
                break;
        }

        logger.debug("Finaliza método obtenerTipoTransaccion");
        return tipoTransaccion;
    }

    /**
     * Método que invoca el servicio de consulta de registros en cartera vigente,
     * por aportante y línea de cobro
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param tipoLineaCobro       Línea de cobro
     * @return La lista de registros en cartera, para el aportante
     */
    private List<CarteraModeloDTO> consultarPeriodosAportanteLineaCobro(TipoIdentificacionEnum tipoIdentificacion,
                                                                        String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                                                        TipoLineaCobroEnum tipoLineaCobro) {
        logger.debug("Inicia método consultarEmpleadorNumero");
        ConsultarPeriodosAportanteLineaCobro service = new ConsultarPeriodosAportanteLineaCobro(tipoAportante,
            tipoLineaCobro, numeroIdentificacion, tipoIdentificacion);
        service.execute();
        logger.debug("Finaliza método consultarEmpleadorNumero");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de un comunicado en el
     * ECM
     *
     * @param tipoTransaccion        Tipo de transacción
     * @param plantilla              Plantilla del comunicado
     * @param parametroComunicadoDTO Parámetros del comunicado
     * @return Identificador del archivo almacenado en el ECM
     */
    private String guardarComunicadoECM(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,
                                        ParametrosComunicadoDTO parametroComunicadoDTO) {
        logger.info("Inicio de método guardarComunicadoSinNotificacionEmail");
        GuardarComunicadoECM service = new GuardarComunicadoECM(tipoTransaccion, plantilla, parametroComunicadoDTO);
        service.execute();
        logger.debug("Fin de método guardarComunicadoSinNotificacionEmail");
        return service.getResult();
    }

    /**
     * Método que obtiene un objeto <code>DocumentoCarteraModeloDTO</code> a
     * partir de un objeto <code>InformacionArchivoDTO</code> y un identificador
     * de registro en cartera
     *
     * @param plantilla             Plantilla del comunicado a ser enviado
     * @param informacionArchivoDTO Información del archivo en el ECM
     * @param idCartera             Identificador del registro en cartera
     * @param accionCobro           Tipo de acción de cobro
     * @return EL objeto <code>DocumentoCarteraModeloDTO</code> equivalente
     */
    private DocumentoCarteraModeloDTO transformarArchivoDocumentoCartera(EtiquetaPlantillaComunicadoEnum plantilla,
                                                                         InformacionArchivoDTO informacionArchivoDTO, Long idCartera, TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método transformarArchivoDocumentoCartera");
        DocumentoCarteraModeloDTO documentoCarteraDTO = new DocumentoCarteraModeloDTO();
        documentoCarteraDTO.setDescripcionComentarios(plantilla.getDescripcion());
        documentoCarteraDTO.setFechaHoraCargue(new Date().getTime());
        documentoCarteraDTO.setIdCartera(idCartera);
        documentoCarteraDTO.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
        documentoCarteraDTO.setNombre(
            informacionArchivoDTO.getDocName() == null ? plantilla.name() : informacionArchivoDTO.getDocName());
        documentoCarteraDTO.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
        documentoCarteraDTO.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
        documentoCarteraDTO.setAccionCobro(accionCobro);
        String consecutivo = obtenerConsecutivoLiquidacion();
        documentoCarteraDTO.setConsecutivoLiquidacion(consecutivo);
        logger.debug("Fin de método transformarArchivoDocumentoCartera");
        return documentoCarteraDTO;
    }

    /**
     * Método que invoca el servicio de consulta del consecutivo de liquidación
     * a ser generado
     *
     * @return El consecutivo de liquidación
     */
    private String obtenerConsecutivoLiquidacion() {
        logger.debug("Inicio de método obtenerConsecutivoLiquidacion");
        ObtenerConsecutivoLiquidacion service = new ObtenerConsecutivoLiquidacion();
        service.execute();
        logger.debug("Fin de método obtenerConsecutivoLiquidacion");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de documentos cartera
     *
     * @param documentoCarteraDTO Información del documento
     * @return Información actualizada del documento almacenado
     */
    private DocumentoCarteraModeloDTO guardarDocumentoCartera(DocumentoCarteraModeloDTO documentoCarteraDTO) {
        logger.debug("Inicio de método guardarDocumentoCartera");
        GuardarDocumentoCartera service = new GuardarDocumentoCartera(documentoCarteraDTO);
        service.execute();
        logger.debug("Fin de método guardarDocumentoCartera");
        return service.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarDetalleDeuda(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * java.lang.Long)
     */
    @Override
    public DetalleDeudaDTO consultarDetalleDeuda(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                 TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo) {
        String firmaServicio = "CarteraCompositeService.consultarDetalleDeuda(TipoIdentificacionEnum, String, "
            + "TipoSolicitanteMovimientoAporteEnum, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        logger.info("=========================================4");
        logger.info(tipoIdentificacion);

        DetalleDeudaDTO result = consultarDetalleDeudaLineaCobro(tipoIdentificacion, numeroIdentificacion,
            tipoSolicitante, periodo, null);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    @Override
    public DetalleDeudaDTO consultarDetalleDeudaLC(TipoIdentificacionEnum tipoIdentificacion,
                                                   String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo,
                                                   TipoLineaCobroEnum lineaCobro) {
        String firmaServicio = "CarteraCompositeService.consultarDetalleDeuda(TipoIdentificacionEnum, String, "
            + "TipoSolicitanteMovimientoAporteEnum, Long, TipoLineaCobroEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        logger.info("=========================================3");
        logger.info(tipoIdentificacion);
        DetalleDeudaDTO result = consultarDetalleDeudaLineaCobro(tipoIdentificacion, numeroIdentificacion,
            tipoSolicitante, periodo, lineaCobro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * Método encargado de consultar el detalle de una cartera dependiente por llave
     * natural de aportante, tipo de solicitante, período y/o
     * línea de cobro
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoSolicitante
     * @param periodo
     * @param lineaCobro
     * @return La lista de trabajadores asociados a la deuda
     */
    private DetalleDeudaDTO consultarDetalleDeudaLineaCobro(TipoIdentificacionEnum tipoIdentificacion,
                                                            String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo,
                                                            TipoLineaCobroEnum lineaCobro) {
        String firmaServicio = "CarteraCompositeService.consultarDetalleDeudaLineaCobro(TipoIdentificacionEnum, String, "
            + "TipoSolicitanteMovimientoAporteEnum, Long, TipoLineaCobroEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DetalleDeudaDTO detalleDeudaDTO = new DetalleDeudaDTO();
        List<CarteraDependienteModeloDTO> lstTrabajadoresActivos = new ArrayList<>();
        List<CarteraDependienteModeloDTO> lstTrabajadoresNoActivos = new ArrayList<>();
        List<CarteraDependienteModeloDTO> lstIngresoManual = new ArrayList<>();

        Long periodoLocal = periodo;

        logger.info("1============================================");
        logger.info(tipoIdentificacion);
        logger.info(numeroIdentificacion);

        try {
            List<CarteraDependienteModeloDTO> listaTrabajadores = consultarCarteraCotizantesAportante(
                tipoIdentificacion, numeroIdentificacion, tipoSolicitante, periodoLocal, lineaCobro);
            Date fecha = new Date(periodoLocal);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            periodoLocal = calendar.getTime().getTime();

            for (CarteraDependienteModeloDTO registro : listaTrabajadores) {
                String estado = consultarEstadoAfiliadoFecha(registro.getTipoIdentificacion(),
                    registro.getNumeroIdentificacion(), TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, tipoIdentificacion,
                    numeroIdentificacion, periodoLocal);

                if (SIN_INFORMACION.equals(estado)) {
                    registro.setEstadoCotizante(EstadoAfiliadoEnum.valueOf(estado));
                }

                if (registro.getAgregadoManualmente() != null && registro.getAgregadoManualmente()) {
                    lstIngresoManual.add(registro);
                } else if (EstadoAfiliadoEnum.ACTIVO.equals(registro.getEstadoCotizante())) {
                    lstTrabajadoresActivos.add(registro);
                } else {
                    lstTrabajadoresNoActivos.add(registro);
                }
            }

            detalleDeudaDTO.setLstIngresoManual(lstIngresoManual);
            detalleDeudaDTO.setLstTrabajadoresActivos(lstTrabajadoresActivos);
            detalleDeudaDTO.setLstTrabajadoresNoActivos(lstTrabajadoresNoActivos);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return detalleDeudaDTO;
    }

    /**
     * Método que invoca el servicio que consulta la cartera de dependientes
     * asociados a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param periodo              Periodo de deuda
     * @param lineaCobro
     * @return La lista de cotizantes asociados a un aportante registrado en cartera
     */
    private List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportante(
        TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodo, TipoLineaCobroEnum lineaCobro) {
        logger.debug("Inicia método consultarCarteraCotizantesAportante");

        List<CarteraDependienteModeloDTO> result = null;

        if (lineaCobro == null) {
            ConsultarCarteraCotizantesAportante service = new ConsultarCarteraCotizantesAportante(tipoSolicitante,
                periodo, numeroIdentificacion, tipoIdentificacion, null);
            service.execute();

            result = service.getResult();
        } else {
            ConsultarCarteraCotizantesAportanteLC service = new ConsultarCarteraCotizantesAportanteLC(tipoSolicitante,
                lineaCobro, periodo, numeroIdentificacion, tipoIdentificacion, null);
            service.execute();

            result = service.getResult();
        }

        logger.debug("Finaliza método consultarCarteraCotizantesAportante");
        return result;
    }

    /**
     * Método que invoca el servicio que consulta el estado que tenía un afiliado
     * respecto a su aportante, en una fecha dada
     *
     * @param tipoIdentificacion            Tipo de identificación del afiliado
     * @param numeroIdentificacion          Número de identificación del afiliado
     * @param tipoAfiliado                  Tipo de afiliado
     * @param tipoIdentificacionEmpleador   Tipo de identificación del empleador, si
     *                                      tipoAfiliado=TRABAJADOR_DEPENDIENTE
     * @param numeroIdentificacionEmpleador Número de identificación del empleador,
     *                                      si tipoAfiliado=TRABAJADOR_DEPENDIENTE
     * @param fecha                         Fecha a evaluar
     * @return El estado del afiliado. Se generaliza tipo de retorno
     * <code>String</code>, dado que la consulta puede retornar el valor
     * SIN_INFORMACION, que corresponde al estado del afiliado que presenta
     * estado <code>null</code>
     */
    private String consultarEstadoAfiliadoFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacionEmpleador,
                                                String numeroIdentificacionEmpleador, Long fecha) {
        logger.debug("Inicia método consultarEstadoAfiliadoFecha");
        ConsultarEstadoAfiliadoFecha service = new ConsultarEstadoAfiliadoFecha(tipoIdentificacionEmpleador,
            numeroIdentificacionEmpleador, tipoAfiliado, numeroIdentificacion, tipoIdentificacion, fecha);
        service.execute();
        logger.debug("Finaliza método consultarEstadoAfiliadoFecha");
        return service.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * consultarEstadoGestionManualCartera(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public EstadoFiscalizacionEnum consultarEstadoGestionManualCartera(String numeroRadicacion, Long numeroOperacion) {
        try {
            logger.info("Inicia el servicio consultarEstadoGestionManualCartera");
            SolicitudGestionCobroManualModeloDTO solicitudDTO = consultarSolicitudGestionCobroManual(numeroRadicacion,
                numeroOperacion);

            if (solicitudDTO != null) {
                return solicitudDTO.getEstadoSolicitud();
            }

            GestionCicloManualDTO gestionDTO = consultarDatosTemporalesCartera(numeroOperacion);
            logger.info("Finaliza el servicio consultarEstadoGestionManualCartera");
            return gestionDTO != null ? gestionDTO.getEstadoGestionManual() : null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarEstadoGestionManualCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#
     * guardarBitacoraLiquidacion(java.lang.Long,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum,
     * com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum,
     * java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void guardarBitacoraLiquidacion(Long numeroOperacion, TipoIdentificacionEnum tipoIdentificacion,
                                           String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                           TipoActividadBitacoraEnum tipoActividad, ResultadoBitacoraCarteraEnum resultado, String idECM,
                                           UserDTO userDTO) {
        try {
            logger.info("Inicia el servicio guardarBitacoraLiquidacion");
            List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();

            if (idECM != null && !StringUtils.isEmpty(idECM)) {
                DocumentoSoporteModeloDTO documento = new DocumentoSoporteModeloDTO();
                InformacionArchivoDTO informacionArchivoDTO = obtenerArchivo(idECM);
                documento.setDescripcionComentarios("Documento liquidación de aportes");
                documento.setFechaHoraCargue(new Date().getTime());
                documento.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
                documento.setNombre(informacionArchivoDTO.getDocName() == null ? "Documento liquidación de aportes"
                    : informacionArchivoDTO.getDocName());
                documento.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
                documento.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
                listaDocumentos.add(documento);
            }

            PersonaModeloDTO personaDTO = consultarDatosPersona(tipoIdentificacion, numeroIdentificacion);
            almacenarBitacoraCartera(null, null, tipoActividad,
                TipoActividadBitacoraEnum.GENERAR_LIQUIDACION.equals(tipoActividad) ? MedioCarteraEnum.PERSONAL
                    : MedioCarteraEnum.ELECTRONICO,
                resultado, userDTO.getNombreUsuario(), personaDTO.getIdPersona(), tipoAportante, listaDocumentos,
                numeroOperacion.toString(), null);
            logger.info("Finaliza el servicio guardarBitacoraLiquidacion");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio guardarBitacoraLiquidacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que almacena agendas y actividades de fiscalización
     *
     * @param programacionFiscalizacionDTO Información a persistir
     */
    private void terminarFiscalizacion(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO) {
        try {
            logger.debug(
                "Inicia metodo terminarProcesoFiscalizacion(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO, EstadoFiscalizacionEnum estadoFiscalizacion)");

            // Almacena agendas
            guardarAgendaCartera(programacionFiscalizacionDTO.getAgendaFiscalizacionModeloDTOs());

            // Almacena actividades
            for (ActividadCarteraModeloDTO actividadCartera : programacionFiscalizacionDTO
                .getActividadFiscalizacionModeloDTOs()) {
                if (actividadCartera.getActividadDocumentoModeloDTOs() != null
                    && !actividadCartera.getActividadDocumentoModeloDTOs().isEmpty()) {
                    for (ActividadDocumentoModeloDTO documento : actividadCartera.getActividadDocumentoModeloDTOs()) {
                        InformacionArchivoDTO informacionArchivoDTO = obtenerArchivo(
                            documento.getIdentificacionDocumento());
                        documento.setDescripcionComentarios("Documento actividad cartera");
                        documento.setFechaHoraCargue(new Date().getTime());
                        documento.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
                        documento.setNombre(informacionArchivoDTO.getDocName() == null ? "Documento actividad cartera"
                            : informacionArchivoDTO.getDocName());
                        documento.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
                    }
                }
            }

            guardarActividadCartera(programacionFiscalizacionDTO.getActividadFiscalizacionModeloDTOs());
        } catch (Exception e) {
            logger.error(
                "Finaliza metodo terminarProcesoFiscalizacion(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO,EstadoFiscalizacionEnum estadoFiscalizacion)",
                e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(
            "Finaliza metodo terminarProcesoFiscalizacion(ProgramacionFiscalizacionDTO programacionFiscalizacionDTO,EstadoFiscalizacionEnum estadoFiscalizacion)");
    }

    /**
     * @param numeroRadicacion
     * @return
     */
    private SolicitudModeloDTO consultarSolicitudPorRadicado(String numeroRadicacion) {
        logger.debug("Inicio de método consultarSolicitudPorRadicado");
        ConsultarSolicitudPorRadicado solicitud = new ConsultarSolicitudPorRadicado(numeroRadicacion);
        solicitud.execute();
        logger.debug("Fin de método consultarSolicitudPorRadicado");
        return solicitud.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarExistenciaGestionCobroManual(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public Boolean consultarExistenciaGestionCobroManual(String numeroRadicado, Long numeroOperacion) {
        SolicitudGestionCobroManualModeloDTO solicitud = consultarSolicitudGestionCobroManual(numeroRadicado,
            numeroOperacion);
        return solicitud != null && (EstadoFiscalizacionEnum.EN_PROCESO.equals(solicitud.getEstadoSolicitud())
            || EstadoFiscalizacionEnum.ASIGNADO.equals(solicitud.getEstadoSolicitud())) ? true : false;
    }

    /**
     * Servicio que consulta la bitacora sin resultado dado un número de operación,
     * es para el caso que es físico el metodo de envió en la
     * acción de cobro y no se ha realizado su respectiva gestión
     *
     * @param numeroOperacion Numero de operacion relacionado a la cartera y
     *                        bitacora
     * @return Lista de bitacoras
     */
    private MetodoAccionCobroEnum consultarMetodoCriterioGestionCobro() {
        logger.debug("Inicio de método consultarMetodoCriterioGestionCobro");
        ConsultarMetodoCriterioGestionCobro metodo = new ConsultarMetodoCriterioGestionCobro();
        metodo.execute();
        logger.debug("Fin de método consultarMetodoCriterioGestionCobro");
        return metodo.getResult();
    }

    /**
     * Método privado que consulta los cotizantes que se encuentran morosos para un
     * empleador
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificacion del aportante
     * @param periodo              Periodo de la cartera sobre la cual se tiene una
     *                             deuda
     * @return Listado de dependientes mororsos relacionados con el empleador
     */
    private List<CarteraDependienteModeloDTO> consultarPeriodosMorososCotizantes(
        TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long periodo) {
        logger.debug("Inicio de método consultarPeriodosMorososCotizantes");
        ConsultarPeriodosMorososCotizantes cotizantes = new ConsultarPeriodosMorososCotizantes(periodo,
            numeroIdentificacion, tipoIdentificacion);
        cotizantes.execute();
        logger.debug("Fin de método consultarPeriodosMorososCotizantes");
        return cotizantes.getResult();
    }

    /**
     * Método que invoca el servicio obtenerEstadoTrabajador.
     *
     * @param startDate   inicio del periodo.
     * @param endDate     fin del periodo
     * @param idEmpleador id del empleador
     * @param idAfiliado  id del afiliado.
     * @return estado del afiliado.
     */
    private EstadoAfiliadoEnum obtenerEstadoTrabajador(Long startDate, Long endDate, Long idEmpleador, Long idAfiliado,
                                                       TipoAfiliadoEnum tipoAfiliado) {
        logger.debug("Inicio de método obtenerEstadoTrabajador");
        ObtenerEstadoTrabajador obtenerEstadoService = new ObtenerEstadoTrabajador(startDate, idEmpleador, idAfiliado,
            tipoAfiliado, endDate);
        obtenerEstadoService.execute();
        logger.debug("Fin de método obtenerEstadoTrabajador");
        return obtenerEstadoService.getResult() != null ? obtenerEstadoService.getResult()
            : EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#consultarCotizantesMorosos(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.Long)
     */
    @Override
    public List<CarteraDependienteModeloDTO> consultarCotizantesMorosos(TipoIdentificacionEnum tipoIdentificacion,
                                                                        String numeroIdentificacion, Long periodo) {
        logger.debug("Inicio de método consultarCotizantesMorosos");
        List<CarteraDependienteModeloDTO> cotizantes = consultarPeriodosMorososCotizantes(tipoIdentificacion,
            numeroIdentificacion, periodo);
        LocalDate localDateBase = Instant.ofEpochMilli(periodo).atZone(ZoneId.systemDefault()).toLocalDate();
        localDateBase = localDateBase.withDayOfMonth(1);
        Long startDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        localDateBase = localDateBase.plusMonths(1L);
        Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

        if (!cotizantes.isEmpty()) {
            for (CarteraDependienteModeloDTO cotizante : cotizantes) {
                cotizante.setEstadoCotizante(obtenerEstadoTrabajador(startDate, endDate, cotizante.getIdEmpleador(),
                    cotizante.getIdAfiliado(), TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE));
            }
        }
        logger.debug("Fin de método consultarCotizantesMorosos");
        return cotizantes;
    }

    @Override
    public SolicitudGestionCobroFisicoModeloDTO guardarResultadosEdictos(RegistroRemisionAportantesDTO registroDTO) {
        logger.debug("Inicio de método guardarResultadosEdictos");

        SolicitudGestionCobroFisicoModeloDTO solicitudDTO = consultarSolicitudGestionCobro(
            registroDTO.getNumeroRadicacion());
        List<DetalleSolicitudGestionCobroModeloDTO> listaDetallesDTO = new ArrayList<>();

        if (registroDTO.getIdDocumento() != null && solicitudDTO.getDocumentoSoporte() == null) {
            InformacionArchivoDTO informacionArchivoDTO = obtenerArchivo(registroDTO.getIdDocumento());
            DocumentoSoporteModeloDTO documentoDTO = new DocumentoSoporteModeloDTO();
            documentoDTO.setDescripcionComentarios(registroDTO.getObservaciones());
            documentoDTO.setFechaHoraCargue(new Date().getTime());
            documentoDTO.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
            documentoDTO.setNombre("DOCUMENTO_SOPORTE_PUBLICACION_EDICTOS");
            documentoDTO.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
            documentoDTO.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
            solicitudDTO.setDocumentoSoporte(documentoDTO);
        }

        solicitudDTO.setFechaRemision(registroDTO.getFechaRemision());
        solicitudDTO.setObservacionRemision(registroDTO.getObservaciones());
        solicitudDTO = guardarSolicitudGestionCobro(solicitudDTO);

        DetalleSolicitudGestionCobroModeloDTO detalleDTO = null;

        for (AportanteRemisionComunicadoDTO aportanteDTO : registroDTO.getAportantes()) {
            detalleDTO = new DetalleSolicitudGestionCobroModeloDTO();
            detalleDTO.setIdDetalleSolicitudGestionCobro(aportanteDTO.getIdDetalleSolicitudGestionCobro());
            detalleDTO.setIdCartera(aportanteDTO.getIdCartera());
            detalleDTO.setIdPrimeraSolicitudRemision(aportanteDTO.getIdPrimeraSolicitudRemision());
            detalleDTO.setEnviarPrimeraRemision(aportanteDTO.getEnviar());
            detalleDTO.setObservacionPrimeraRemision(aportanteDTO.getObservacionPrimeraEntrega());

            if (!registroDTO.getGuardar()) {
                detalleDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.ACTUALIZACION_EXITOSA);
            }

            listaDetallesDTO.add(detalleDTO);
        }
        guardarListaDetalleSolicitudGestionCobro(listaDetallesDTO, solicitudDTO.getIdSolicitud());

        logger.debug("Fin de método guardarResultadosEdictos");
        return solicitudDTO;
    }

    /**
     * Método encargado de registrar la deuda de un independiente o pensionado
     *
     * @param listaCotizantesDTO Lista de cotizantes (incluye novedades a aplicar)
     * @param periodo            Periodo de evaluación
     * @author Francisco Alejandro Hoyos Rojas
     */
    private void registrarDeudaIndependientePensionado(List<GestionDeudaDTO> listaCotizantesDTO, String periodo) {
        try {
            logger.debug("Inicia el método registrarDeudaIndependientePensionado");
            // Almacena la nueva persona
            List<CarteraNovedadModeloDTO> tipoNovedades = new ArrayList<>();
            for (GestionDeudaDTO cotizanteDTO : listaCotizantesDTO) {
                RolAfiliadoModeloDTO rolAfiliadoModeloDTO = null;
                if (cotizanteDTO.getListaNovedades() != null) {
                    PersonaModeloDTO personaCotizante = consultarPersona(cotizanteDTO.getIdPersona());
                    NovedadCarteraDTO listaNovedades = new NovedadCarteraDTO();
                    listaNovedades.setCanal(CanalRecepcionEnum.CARTERA);
                    listaNovedades.setEsTrabajadorReintegrable(Boolean.TRUE);
                    listaNovedades.setPersonaAportante(personaCotizante);
                    listaNovedades.setPersonaCotizante(personaCotizante);
                    List<NovedadPilaDTO> listaNovedadesPila = new ArrayList<>();

                    for (CarteraNovedadModeloDTO carteraNovedadDTO : cotizanteDTO.getListaNovedades()) {
                        // Almacena en la tabla de novedades de cartera
                        if (carteraNovedadDTO.getFechaInicio() != null || carteraNovedadDTO.getFechaFin() != null) {
                            carteraNovedadDTO.setIdPersona(cotizanteDTO.getIdPersona());
                           /* if (TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.equals(carteraNovedadDTO.getTipoNovedad())) {
                                carteraNovedadDTO.setFechaInicio(formatDate(periodo));
                            }*/
                            guardarCarteraNovedad(carteraNovedadDTO);
                        }
                        // Registra las novedades a aplicar
                        if (carteraNovedadDTO.getAplicar()) {
                            NovedadPilaDTO novedadDTO = new NovedadPilaDTO();
                            novedadDTO.setAccionNovedad(
                                carteraNovedadDTO.getAplicar() ? MarcaAccionNovedadEnum.APLICAR_NOVEDAD.name()
                                    : MarcaAccionNovedadEnum.NO_APLICADA.name());
                            novedadDTO.setEsTrabajadorReintegrable(Boolean.TRUE);
                            novedadDTO.setTipoIdentificacionCotizante(personaCotizante.getTipoIdentificacion());
                            novedadDTO.setNumeroIdentificacionCotizante(personaCotizante.getNumeroIdentificacion());
                            novedadDTO.setFechaInicioNovedad(carteraNovedadDTO.getFechaInicio() != null
                                ? new Date(carteraNovedadDTO.getFechaInicio())
                                : null);
                            novedadDTO.setFechaFinNovedad(
                                carteraNovedadDTO.getFechaFin() != null ? new Date(carteraNovedadDTO.getFechaFin())
                                    : null);
                            novedadDTO.setTipoTransaccion(carteraNovedadDTO.getTipoNovedad());
                            novedadDTO.setEsIngreso(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO
                                .equals(carteraNovedadDTO.getTipoNovedad()));

                            if (novedadDTO.getEsIngreso()) {
                                novedadDTO.setTipoTransaccion(null);
                            }
                            rolAfiliadoModeloDTO = consultarRolAfiliadoCartera(cotizanteDTO.getIdCartera());
                            if (rolAfiliadoModeloDTO.getTipoAfiliado() == TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE) {
                                novedadDTO.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
                                novedadDTO.setEsRetiro(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE
                                    .equals(carteraNovedadDTO.getTipoNovedad()));
                                novedadDTO.setTipoTransaccion(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE);
                            } else {
                                novedadDTO.setTipoCotizante(TipoAfiliadoEnum.PENSIONADO);
                                novedadDTO.setEsRetiro(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2
                                    .equals(carteraNovedadDTO.getTipoNovedad())
                                    || TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR
                                    .equals(carteraNovedadDTO.getTipoNovedad()));
                            }
                            novedadDTO.setMensajeNovedad("La novedad fue registrada");
                            listaNovedadesPila.add(novedadDTO);
                        }
                    }

                    if (!listaNovedadesPila.isEmpty()) {
                        listaNovedades.setNovedades(listaNovedadesPila);
                        RegistrarNovedadesCartera service = new RegistrarNovedadesCartera(listaNovedades);
                        service.execute();
                    }
                    // Actualiza la cartera del aportante
                    logger.info("periodo--> " +periodo);
                    logger.info("afiliado--> " + rolAfiliadoModeloDTO.getTipoAfiliado());
                    actualizarDeudaPresuntaCartera(cotizanteDTO.getTipoIdentificacion(),
                        cotizanteDTO.getNumeroIdentificacion(), periodo,
                        TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(rolAfiliadoModeloDTO.getTipoAfiliado())
                            ? TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
                            : TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
                }
            }
            logger.info("Fin método registrarDeudaIndependientePensionado");
        } catch (Exception e) {
            logger.error("Error en el método registrarDeudaIndependientePensionado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que invoca el servicio que consulta la información de un rol por
     * persona, de acuerdo a un identificador de cartera
     *
     * @param idCartera identificador de cartera
     * @return La información de rol afiliado
     * @author Francisco Alejandro Hoyos Rojas
     */
    private RolAfiliadoModeloDTO consultarRolAfiliadoCartera(Long idCartera) {
        logger.debug("Inicia método consultarRolAfiliadoCartera");
        ConsultarRolAfiliadoCartera service = new ConsultarRolAfiliadoCartera(idCartera);
        service.execute();
        logger.debug("Finaliza método consultarRolAfiliadoCartera");
        return service.getResult();
    }

    /**
     * Servicio que se encarga de consultar la deuda presunta para el aportante
     *
     * @param tipoIdentificacion   Tipo de identificacion del aportante
     * @param numeroIdentificacion Número de identificacion del aportante
     * @param tipoAportante        Tipo de solicitante
     * @param periodoEvaluacion    Periodo sobre el cual se consultará la cartera
     * @return El monto de la deuda
     */
    private BigDecimal consultarDeudaPresunta(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                              TipoSolicitanteMovimientoAporteEnum tipoAportante, String periodoEvaluacion) {
        logger.debug("Inicio de método consultarDeudaPresunta");
        ObtenerDeudaPresuntaCartera deudaService = new ObtenerDeudaPresuntaCartera(tipoAportante, numeroIdentificacion,
            periodoEvaluacion, tipoIdentificacion);
        deudaService.execute();
        logger.debug("Fin de método consultarDeudaPresunta");
        return deudaService.getResult();
    }

    /**
     * Método encargado de agregar las simulaciones que tengan tipo de gestion
     * cartera manual
     *
     * @param simulacionDTO     simulacion
     * @param periodoEvaluacion periodo evaluado
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private void agregarSimulacionManual(SimulacionDTO simulacionDTO, String periodoEvaluacion) {
        logger.info("CarteraCompositeBusiness.agregarSimulacionManual" + simulacionDTO + ":" + periodoEvaluacion);
        logger.debug("Inicio de método agregarSimulacionManual(SimulacionDTO simulacionDTO, String periodoEvaluacion)");
        Object[] resultados = obtenerDiaHabilVencimientoAporteYEmpleador(simulacionDTO);
        Short diaHabilVencimientoAporte = resultados[0] != null ? (Short) resultados[0] : null;
        if (validarDiaHabilVencimientoYPeriodoAnalizadoRecaudado(diaHabilVencimientoAporte, simulacionDTO,
            periodoEvaluacion, "(Manual): ")) {
            /* Se agrega las simulaciones que tengan tipo de gestion cartera es manual */
            simulacionesManual.add(simulacionDTO);
            logger.debug("Se agrega la simulación manual del aportante (Manual): "
                + simulacionDTO.getTipoIdentificacion() + " - " + simulacionDTO.getNumeroIdentificacion());
        }
        logger.debug("Fin de método agregarSimulacionManual(SimulacionDTO simulacionDTO, String periodoEvaluacion)");
    }

    /**
     * Método encargado de validar que el día hábil de vencimiento del aporte sea
     * diferente de null, que la deuda presunta del
     * aportante sea mayor a cero y de validar el periodo analizado recaudado
     *
     * @param diaHabilVencimientoAporte día hábil de vencimiento de aportes de un
     *                                  aportante
     * @param simulacionDTO             simulación generada al ejecutar la
     *                                  asignación de acciones preventivas
     * @param periodoEvaluacion         periodo evaluado
     * @param tipo                      tipo de gestión de cartera de la simulación
     * @return true si cumple todas las validaciones de lo contrario false
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private Boolean validarDiaHabilVencimientoYPeriodoAnalizadoRecaudado(Short diaHabilVencimientoAporte,
                                                                         SimulacionDTO simulacionDTO, String periodoEvaluacion, String tipo) {
        logger.debug(
            "Inicio de método validarDiaHabilVencimientoYPeriodoAnalizadoRecaudado(Short diaHabilVencimientoAporte, SimulacionDTO simulacionDTO, String periodoEvaluacion, String tipo)");
        BigDecimal deuda = BigDecimal.ZERO;
        if (diaHabilVencimientoAporte != null) {
            if (!validarPeriodoAnalizadoRecaudado(diaHabilVencimientoAporte)) {
                deuda = consultarDeudaPresunta(simulacionDTO.getTipoIdentificacion(),
                    simulacionDTO.getNumeroIdentificacion(), simulacionDTO.getTipoAportante(), periodoEvaluacion);
                if (deuda != null && deuda.compareTo(BigDecimal.ZERO) > 0) {
                    return Boolean.TRUE;
                } else {
                    logger.debug("La deuda presunta del aportante es null o igual a cero " + tipo
                        + simulacionDTO.getTipoIdentificacion() + " - " + simulacionDTO.getNumeroIdentificacion());
                }
            } else {
                logger.debug("El aportante no cumple la validación del periodo analizado recaudado " + tipo
                    + simulacionDTO.getTipoIdentificacion() + " - " + simulacionDTO.getNumeroIdentificacion()
                    + " día habil vencimiento aporte " + String.valueOf(diaHabilVencimientoAporte));
            }
        } else {
            logger.debug("El día habil de vencimiento del aporte es null para el aportante " + tipo
                + simulacionDTO.getTipoIdentificacion() + " - " + simulacionDTO.getNumeroIdentificacion());
        }
        logger.debug(
            "Fin de método validarDiaHabilVencimientoYPeriodoAnalizadoRecaudado(Short diaHabilVencimientoAporte, SimulacionDTO simulacionDTO, String periodoEvaluacion, String tipo)");
        return Boolean.FALSE;
    }

    /**
     * Método encargado de procesar las simulaciones en las cuales el tipo de
     * gestión de cartera es Automatica
     *
     * @param simulaciones      lista de simulaciones en las cules su tipo de
     *                          gestión de cartera es Automatica
     * @param userDTO           usuario autenticado
     * @param periodoEvaluacion periodo evaluado
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private void procesarSimulacionAutomatica(SimulacionDTO simulacionDTO, UserDTO userDTO, String periodoEvaluacion) {
        logger.debug(
            "Inicio de método procesarSimulacionAutomatica(SimulacionDTO simulacionDTO, UserDTO userDTO, String periodoEvaluacion)");
        Object[] resultados = obtenerDiaHabilVencimientoAporteYEmpleador(simulacionDTO);
        Short diaHabilVencimientoAporte = resultados[0] != null ? (Short) resultados[0] : null;
        EmpleadorModeloDTO empleador = resultados[1] != null ? (EmpleadorModeloDTO) resultados[1] : null;
        SolicitudPreventivaModeloDTO solicitudPreventivaDTO = construirSolicitudPreventiva(simulacionDTO, userDTO);
        Long idSolicitud = guardarSolicitudPreventiva(solicitudPreventivaDTO);
        solicitudPreventivaDTO.setIdSolicitud(idSolicitud);
        String numeroRadicacion = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());
        // Persiste en BitacoraCartera
        guardarBitacoraPreventiva(solicitudPreventivaDTO, TipoActividadBitacoraEnum.INGRESO_PREVENTIVA,
            MedioCarteraEnum.ELECTRONICO);
        /* si se trata de una solicitud automática se hace el envío del comunicado */
        NotificacionParametrizadaDTO notificacion = construirNotificacionParametrizada(solicitudPreventivaDTO, PRIMERO,
            null);
        if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(simulacionDTO.getTipoAportante())
            || TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(simulacionDTO.getTipoAportante())) {
            List<String> correos = new ArrayList<>();
            correos.add(simulacionDTO.getCorreoElectronico());
            notificacion.setDestinatarioTO(correos);
            notificacion.setReplantearDestinatarioTO(true);
        } else {
            notificacion.setIdEmpleador(empleador != null ? empleador.getIdEmpleador() : null);
        }
        /*
         * Se obtienen los parametros pertenecientes al comunicado de gestión preventiva
         */
        Map<String, String> params = obtenerParametrosComunicadoPreventiva(simulacionDTO.getTipoAportante(),
            simulacionDTO.getNumeroIdentificacion());
        params.put(NUMERO_RADICADO, numeroRadicacion);
        notificacion.getParams().putAll(params);

        if (validarDiaHabilVencimientoYPeriodoAnalizadoRecaudado(diaHabilVencimientoAporte, simulacionDTO,
            periodoEvaluacion, "(Automático): ")) {
            enviarCorreoParametrizadoAsincrono(notificacion);
            // Persiste en BitacoraCartera
            guardarBitacoraPreventiva(solicitudPreventivaDTO, TipoActividadBitacoraEnum.COMUNICADO_GENERADO,
                MedioCarteraEnum.ELECTRONICO);
            logger.debug("Se envia correo y se guarda bitacora para el aportante (Automático): "
                + simulacionDTO.getTipoIdentificacion() + " - " + simulacionDTO.getNumeroIdentificacion());
        }

        logger.debug(
            "Fin de método procesarSimulacionAutomatica(SimulacionDTO simulacionDTO, UserDTO userDTO, String periodoEvaluacion)");
    }

    /**
     * Método encargado de procesar las simulaciones en las cuales el tipo de
     * gestión de cartera es Manual
     *
     * @param simulaciones      lista de simulaciones en las cules su tipo de
     *                          gestión de cartera es manual
     * @param userDTO           usuario autenticado
     * @param periodoEvaluacion periodoEvaluado
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private void procesarSimulacionManual(ArrayList<SimulacionDTO> simulaciones, UserDTO userDTO,
                                          String periodoEvaluacion) {
        logger.debug(
            "Inicio de método procesarSimulacionManual(ArrayList<SimulacionDTO> simulaciones, UserDTO userDTO, String periodoEvaluacion)");

        simulacionesManual = new ArrayList<>();
        List<Callable<Void>> tareasParalelas = new LinkedList<>();
        for (SimulacionDTO simulacionDTO : simulaciones) {
            Callable<Void> parallelTask = () -> {
                agregarSimulacionManual(simulacionDTO, periodoEvaluacion);
                return null;
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            try {
                mes.invokeAll(tareasParalelas);
                if (!usuariosAsignadosPreventivaAgrupada.isEmpty()) {
                    List<SimulacionDTO> tareasAsignadas = new ArrayList<SimulacionDTO>();
                    /* Se recorre los usuarios asignados */
                    for (String usurioAsignado : usuariosAsignadosPreventivaAgrupada) {
                        int contador = 0;
                        SolicitudPreventivaAgrupadoraModeloDTO agrupadoraModeloDTO = new SolicitudPreventivaAgrupadoraModeloDTO();
                        simulacionesManual
                            .removeIf(s -> s.getTipoGestionPreventiva().equals(TipoGestionCarteraEnum.AUTOMATICA));
                        for (SimulacionDTO simulacionDTO : simulacionesManual) {
                            if (tareasAsignadas.indexOf(simulacionDTO) == -1) {
                                if (simulacionDTO.getTipoGestionPreventiva() == TipoGestionCarteraEnum.MANUAL) {
                                    if (contador == 0) {
                                        /* Se construye la solicitud agrupadora */
                                        logger.debug("Se crea solicitud preventiva agrupadora)");
                                        agrupadoraModeloDTO = construirSolicitudPreventivaAgrupadora(userDTO);
                                        agrupadoraModeloDTO = guardarSolicitudPreventivaAgrupadora(agrupadoraModeloDTO);
                                        String numeroRadicacion = generarNumeroRadicado(
                                            agrupadoraModeloDTO.getIdSolicitud(),
                                            userDTO.getSedeCajaCompensacion());
                                        Map<String, Object> params = new HashMap<>();
                                        params.put(ID_SOLICITUD, agrupadoraModeloDTO.getIdSolicitud());
                                        params.put(NUMERO_RADICADO, numeroRadicacion);
                                        params.put(ANALISTA_CARTERA, simulacionDTO.getUsuarioAnalista());
                                        agrupadoraModeloDTO = consultarSolicitudPreventivaAgrupadora(numeroRadicacion);
                                        Long idInstancia = iniciarProceso(ProcesoEnum.GESTION_PREVENTIVA_CARTERA,
                                            params, Boolean.TRUE);
                                        agrupadoraModeloDTO.setDestinatario(simulacionDTO.getUsuarioAnalista());
                                        agrupadoraModeloDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
                                        agrupadoraModeloDTO.setIdInstanciaProceso(idInstancia.toString());
                                        guardarSolicitudPreventivaAgrupadora(agrupadoraModeloDTO);
                                    }
                                    /* Se crea la solicitud preventiva para cada aportante */
                                    SolicitudPreventivaModeloDTO solicitudPreventivaModeloDTO = construirSolicitudPreventiva(
                                        simulacionDTO, userDTO);
                                    Long idSolicitud = guardarSolicitudPreventiva(solicitudPreventivaModeloDTO);

                                    String numeroRadicacionIndividual = generarNumeroRadicado(idSolicitud,
                                        userDTO.getSedeCajaCompensacion());

                                    /* Se consulta de nuevo la solicitud para actualizar los demas datos */
                                    solicitudPreventivaModeloDTO = consultarSolicitudPreventiva(
                                        numeroRadicacionIndividual);
                                    solicitudPreventivaModeloDTO.setDestinatario(simulacionDTO.getUsuarioAnalista());
                                    solicitudPreventivaModeloDTO.setIdSolicitudPreventivaAgrupadora(
                                        agrupadoraModeloDTO.getIdSolicitudPreventivaAgrupadora());
                                    Long fechaLimitePago = null;
                                    // Persiste en BitacoraCartera
                                    guardarBitacoraPreventiva(solicitudPreventivaModeloDTO,
                                        TipoActividadBitacoraEnum.INGRESO_PREVENTIVA, MedioCarteraEnum.PERSONAL);

                                    if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
                                        .equals(simulacionDTO.getTipoAportante())) {
                                        Calendar cal = Calendar.getInstance();
                                        /* Se resta un mes del periodo actual caso empleador */
                                        cal.add(Calendar.MONTH, -1);
                                        Date date = cal.getTime();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                                        String periodo = format.format(date);
                                        fechaLimitePago = obtenerFechaVencimiento(periodo,
                                            simulacionDTO.getTipoAportante(),
                                            simulacionDTO.getNumeroIdentificacion());
                                    } else {
                                        Date date = new Date();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                                        String periodo = format.format(date);
                                        fechaLimitePago = obtenerFechaVencimiento(periodo,
                                            simulacionDTO.getTipoAportante(),
                                            simulacionDTO.getNumeroIdentificacion());
                                    }
                                    solicitudPreventivaModeloDTO.setFechaLimitePago(fechaLimitePago);
                                    guardarSolicitudPreventiva(solicitudPreventivaModeloDTO);
                                    contador++;
                                    if (contador == 20) {
                                        /* Se reincia contador para crear otra tarea */
                                        contador = 0;
                                    }

                                }
                                tareasAsignadas.add(simulacionDTO);
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                logger.error(
                    "Ocurrió un error inesperado en procesarSimulacionManual(ArrayList<SimulacionDTO> simulaciones, UserDTO userDTO, String periodoEvaluacion)"
                        + e);
            }
        }
        logger.debug(
            "Fin de método procesarSimulacionManual(ArrayList<SimulacionDTO> simulaciones, UserDTO userDTO, String periodoEvaluacion)");
    }

    /**
     * Método encargado de obtener un arreglo con el día habil de vencimiento de
     * aportes y el empleador de una simulación
     *
     * @param simulacionDTO simulación
     * @return arreglo con el día hábil de vencimiento de aportes y el empleador
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private Object[] obtenerDiaHabilVencimientoAporteYEmpleador(SimulacionDTO simulacionDTO) {
        logger.debug("Inicio de método obtenerDiaHabilVencimientoAporteYEmpleador(SimulacionDTO simulacionDTO)");
        Object[] resultados = new Object[2];
        Short diaHabilVencimientoAporte = null;
        EmpleadorModeloDTO empleador = new EmpleadorModeloDTO();
        List<RolAfiliadoEmpleadorDTO> rolAfiliadoEmpleadorDTOs = new ArrayList<>();
        if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(simulacionDTO.getTipoAportante())
            || TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(simulacionDTO.getTipoAportante())) {
            rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(simulacionDTO.getTipoIdentificacion(),
                simulacionDTO.getNumeroIdentificacion(),
                TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(simulacionDTO.getTipoAportante())
                    ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
                    : TipoAfiliadoEnum.PENSIONADO);
            /* Se valida que hay datos */
            if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                /* Se consulta el afiliado */
                RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                    rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                if (rolAfiliadoDTO != null) {
                    diaHabilVencimientoAporte = rolAfiliadoDTO.getDiaHabilVencimientoAporte();
                }
            }
        } else {
            // Se setea el Id del empleador para referencia del comunicado*/
            ConsultarEmpleadorTipoNumero empleadorService = new ConsultarEmpleadorTipoNumero(
                simulacionDTO.getNumeroIdentificacion(), simulacionDTO.getTipoIdentificacion());
            empleadorService.execute();
            empleador = empleadorService.getResult();
            diaHabilVencimientoAporte = empleador.getDiaHabilVencimientoAporte();
        }
        resultados[0] = diaHabilVencimientoAporte;
        resultados[1] = empleador;
        logger.debug("Fin de método obtenerDiaHabilVencimientoAporteYEmpleador(SimulacionDTO simulacionDTO)");
        return resultados;
    }

    /**
     *
     */
    public List<PersonaDTO> buscarPersonasCartera(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                  TipoIdentificacionEnum valorTI, String valorNI, String primerNombre, String primerApellido,
                                                  Long fechaNacimiento, Long idEmpleador, String segundoNombre, String segundoApellido,
                                                  Boolean esVista360Web) {
        logger.debug(
            "Inicio de método buscarPersonas(valorTI, valorNI, primerNombre, primerApellido, fechaNacimiento, idEmpleador, segundoNombre, segundoApellido, esVista360Web)");

        BuscarPersonas personasService = new BuscarPersonas(fechaNacimiento, primerApellido, idEmpleador, esVista360Web,
            primerNombre, segundoApellido, valorNI, valorTI, segundoNombre);
        personasService.execute();
        List<PersonaDTO> personas = personasService.getResult();

        for (PersonaDTO personaDTO : personas) {
            EstadoCarteraEnum estadoCartera = consultarEstadoCartera(personaDTO.getTipoIdentificacion(),
                personaDTO.getNumeroIdentificacion(), tipoSolicitante);
            personaDTO.setEstadoCartera(estadoCartera);
            GestionCarteraDTO gestionCartera = new GestionCarteraDTO();
            gestionCartera.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
            gestionCartera.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
            gestionCartera.setTipoSolicitante(tipoSolicitante);
            ConsultarGestionCartera360 gestionCarteraService = new ConsultarGestionCartera360(gestionCartera);
            gestionCarteraService.execute();
            personaDTO.setGestionCartera(gestionCarteraService.getResult());
        }
        return personas;
    }

    public List<EmpleadorDTO> buscarEmpleadorCartera(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                     TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
                                                     Boolean aplicaPre) {
        logger.debug(
            "Inicio de método buscarEmpleador(tipoIdentificacion, numeroIdentificacion, razonSocial, aplicaPre)");

        BuscarEmpleador empleadorService = new BuscarEmpleador(aplicaPre, numeroIdentificacion, tipoIdentificacion,
            razonSocial);
        empleadorService.execute();
        List<Empleador> empleadores = empleadorService.getResult();
        List<EmpleadorDTO> empleadoresDTO = new ArrayList<EmpleadorDTO>();
        if (empleadores != null && !empleadores.isEmpty()) {
            for (Empleador empleador : empleadores) {
                EmpleadorDTO empleadorDTO = new EmpleadorDTO();
                empleadorDTO = empleadorDTO.convertEmpleadorToDTO(empleador);
                EstadoCarteraEnum estadoCartera = consultarEstadoCartera(
                    empleadorDTO.getPersona().getTipoIdentificacion(),
                    empleadorDTO.getPersona().getNumeroIdentificacion(), tipoSolicitante);
                empleadorDTO.setEstadoCartera(estadoCartera);
                GestionCarteraDTO gestionCartera = new GestionCarteraDTO();
                gestionCartera.setTipoIdentificacion(empleadorDTO.getPersona().getTipoIdentificacion());
                gestionCartera.setNumeroIdentificacion(empleadorDTO.getPersona().getNumeroIdentificacion());
                gestionCartera.setTipoSolicitante(tipoSolicitante);
                ConsultarGestionCartera360 gestionCarteraService = new ConsultarGestionCartera360(gestionCartera);
                gestionCarteraService.execute();
                empleadorDTO.setGestionCartera(gestionCarteraService.getResult());

                empleadoresDTO.add(empleadorDTO);
            }
        }
        return empleadoresDTO;
    }

    @Override
    public void incluirAportanteCicloFiscalizacion(SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user) {
        logger.debug(
            "Inicia incluirAportanteCicloFiscalizacion (SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user) ");
        List<SimulacionDTO> simulacionDTOs = new ArrayList<SimulacionDTO>();
        try {
            simulacionDTOs.add(simulacionDTO);
            AsignarUsuarioCiclo asignacionService = new AsignarUsuarioCiclo(proceso, simulacionDTOs);
            asignacionService.execute();
            simulacionDTOs = asignacionService.getResult();
            simulacionDTO = simulacionDTOs.get(0);

            CicloCarteraModeloDTO cicloFiscalizacionModeloDTO = new CicloCarteraModeloDTO();
            cicloFiscalizacionModeloDTO = consultarCicloFiscalizacionActual();

            CicloAportanteModeloDTO cicloAportanteModeloDTO = new CicloAportanteModeloDTO();
            PersonaModeloDTO persona = consultarDatosPersona(simulacionDTO.getTipoIdentificacion(),
                simulacionDTO.getNumeroIdentificacion());
            if (simulacionDTO.getUsuarioAnalista() == null || simulacionDTO.getUsuarioAnalista().isEmpty()) {
                logger.error(
                    "Error en metodo incluirAportanteCicloFiscalizacion(SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user)");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            } else {
                cicloAportanteModeloDTO.setIdPersona(persona.getIdPersona());
                cicloAportanteModeloDTO.setTipoSolicitanteMovimientoAporteEnum(simulacionDTO.getTipoAportante());
                cicloAportanteModeloDTO.setAnalista(simulacionDTO.getUsuarioAsignacion());
                cicloAportanteModeloDTO.setIdCicloFiscalizacion(cicloFiscalizacionModeloDTO.getIdCicloFiscalizacion());
            }

            GuardarCicloAportante cicloAportanteService = new GuardarCicloAportante(cicloAportanteModeloDTO);
            cicloAportanteService.execute();
            cicloAportanteModeloDTO = cicloAportanteService.getResult();

            SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = construirSolicitudFiscalizacionModeloDTO(
                cicloAportanteModeloDTO.getIdCicloAportante(), user);
            /* Se guarda la solicitud de fiscalizacion */
            Long idSolicitudFiscalizacion = guardarSolicitudFiscalizacion(solicitudFiscalizacionModeloDTO);

            String numeroRadicacion = generarNumeroRadicado(idSolicitudFiscalizacion, user.getSedeCajaCompensacion());
            Map<String, Object> params = new HashMap<>();
            params.put(ID_SOLICITUD, idSolicitudFiscalizacion);
            params.put(NUMERO_RADICADO, numeroRadicacion);
            params.put(ANALISTA_CARTERA, cicloAportanteModeloDTO.getAnalista());
            /* Se inicia el proceso */
            Long idProceso = iniciarProceso(ProcesoEnum.FISCALIZACION_CARTERA, params, Boolean.FALSE);
            solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion(numeroRadicacion);
            solicitudFiscalizacionModeloDTO.setIdInstanciaProceso(idProceso.toString());
            solicitudFiscalizacionModeloDTO.setDestinatario(cicloAportanteModeloDTO.getAnalista());
            solicitudFiscalizacionModeloDTO.setSedeDestinatario(user.getSedeCajaCompensacion());
            guardarSolicitudFiscalizacion(solicitudFiscalizacionModeloDTO);

            // Persiste en BitacoraCartera
            BitacoraCarteraDTO bitacoraDTO = construirBitacoraFiscalizacion(solicitudFiscalizacionModeloDTO,
                cicloAportanteModeloDTO.getIdPersona(),
                cicloAportanteModeloDTO.getTipoSolicitanteMovimientoAporteEnum(),
                TipoActividadBitacoraEnum.INGRESO_FISCALIZACION);
            guardarBitacoraCartera(bitacoraDTO);

            logger.debug(
                "Finaliza incluirAportanteCicloFiscalizacion (SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user) ");
        } catch (AsopagosException ae) {
            logger.error(
                "Error en método incluirAportanteCicloFiscalizacion (SimulacionDTO simulacionDTO, ProcesoEnum proceso, UserDTO user)",
                ae);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public void retomarAccionesCobro(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                     UserDTO user) {
        logger.debug("Inicio de método retomarAccionesCobro(Long, TipoSolicitanteMovimientoAporteEnum)");
        try {
            ConsultarCarteraPersona listaCarteraService = new ConsultarCarteraPersona(tipoSolicitante, idPersona);
            listaCarteraService.execute();
            List<CarteraModeloDTO> listaCartera = listaCarteraService.getResult();
            if (listaCartera != null && !listaCartera.isEmpty()) {
                for (CarteraModeloDTO carteraModeloDTO : listaCartera) {
                    TipoAccionCobroEnum accionSiguiente = obtenerAccionCobroSiguiente(carteraModeloDTO);
                    if (accionSiguiente != null && accionSiguiente != carteraModeloDTO.getTipoAccionCobro()) {
                        try {
                            AsignacionSolicitudGestionCobroFactory.getInstance().crearAsignacion(accionSiguiente, user)
                                .asignar();
                        } catch (Exception e) {
                            logger.error("Error en servicio retomarAccionesCobro: asignacion accion de cobro", e);
                            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en retomarAccionesCobro: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Obtiene la accion de cobro siguiente a ejecutarse
     *
     * @return TipoAccionCobroEnum
     */
    private TipoAccionCobroEnum obtenerAccionCobroSiguiente(CarteraModeloDTO carteraModeloDTO) {
        logger.debug("Inicio de método obtenerAccionCobroSiguiente(carteraModeloDTO)");
        ObtenerAccionCobroSiguiente accionService = new ObtenerAccionCobroSiguiente(carteraModeloDTO);
        accionService.execute();
        logger.debug("Finaliza método obtenerAccionCobroSiguiente(carteraModeloDTO)");
        return accionService.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarGuardarExclusionCartera(com.asopagos.dto.cartera.ExclusionCarteraDTO)
     */
    @Override
    public void actualizarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO, UserDTO user) {
        logger.debug("Inicia actualizarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO)");
        try {
            ActualizarGuardarExclusionCartera actualizarExclusionService = new ActualizarGuardarExclusionCartera(
                exclusionCarteraDTO);
            actualizarExclusionService.execute();
            ExclusionCarteraDTO exclusionCartera = actualizarExclusionService.getResult();

            if (exclusionCartera != null
                && EstadoExclusionCarteraEnum.NO_ACTIVA.equals(exclusionCartera.getEstadoExclusionCartera())) {
                RetomarAccionesCobro retomarService = new RetomarAccionesCobro(exclusionCartera.getTipoSolicitante(),
                    exclusionCartera.getIdPersona());
                retomarService.execute();
            }
            logger.debug("Finaliza actualizarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO)");
        } catch (Exception e) {
            logger.error("Ocurrió un error en actualizarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void actualizarExclusionCarteraMora(List<ExclusionCarteraDTO> exclusionCarteraDTOS, UserDTO user) {
        logger.info("Inicia actualizarExclusionCarteraMora");
        try {
            exclusionCarteraDTOS.forEach(exclusionCarteraDTO -> {
                exclusionCarteraDTO.setUsuarioRegistro(user.getEmail());
                ActualizarGuardarExclusionCartera actualizarExclusionService = new ActualizarGuardarExclusionCartera(exclusionCarteraDTO);
                actualizarExclusionService.execute();
                ExclusionCarteraDTO exclusionCartera = actualizarExclusionService.getResult();

                if (exclusionCartera != null && EstadoExclusionCarteraEnum.NO_ACTIVA.equals(exclusionCartera.getEstadoExclusionCartera())) {
                    RetomarAccionesCobro retomarService = new RetomarAccionesCobro(exclusionCartera.getTipoSolicitante(), exclusionCartera.getIdPersona());
                    retomarService.execute();
                }
            });
            logger.info("Finaliza actualizarExclusionCarteraMora");
        } catch (Exception e) {
            logger.error("Ocurrió un error en actualizarExclusionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Servicio que se encarga de realizar la ejecución del proceso automático de
     * exclusion de cartera.
     */
    @Override
    public void ejecutarProcesoAutomaticoExclusion() {
        logger.debug("Inicio de método ejecutarProcesoAutomaticoExclusion");
        List<ExclusionCarteraDTO> exclusiones = consultarExclusionCarteraPorInactivar();
        actualizarExclusionCarteraInactivacion();
        if (exclusiones != null && !exclusiones.isEmpty()) {
            for (ExclusionCarteraDTO exclusionCarteraDTO : exclusiones) {
                RetomarAccionesCobro retomarService = new RetomarAccionesCobro(exclusionCarteraDTO.getTipoSolicitante(),
                    exclusionCarteraDTO.getIdPersona());
                retomarService.execute();
            }
        }
        logger.debug("Fin de método ejecutarProcesoAutomaticoExclusion");
    }

    @Override
    public RespuestaCargueMasivoAportanteDTO insertarCargueMasivo(String idArchivo) {
        try {
            logger.info("Inicio de método insertarCargueMasivo ::" + idArchivo);
            RespuestaCargueMasivoAportanteDTO respuestaCargueMasivoAportante = new RespuestaCargueMasivoAportanteDTO();
            List<CargueManualCotizanteAportante> manualCotizanteAportanteList = new ArrayList<>();
            InformacionArchivoDTO file = obtenerArchivo(idArchivo);
            InputStream inputStream = new ByteArrayInputStream(file.getDataFile());
            ArrayList<CarteraModeloDTO> modeloDTOArrayList = lecturaArchivoExcelCartera(inputStream);
            if (!modeloDTOArrayList.isEmpty()) {
                for (CarteraModeloDTO carteraModeloDTO : modeloDTOArrayList) {
                    RepuestaCargue errores = new RepuestaCargue();
                    if (Objects.nonNull(carteraModeloDTO.getTipoLineaCobro())) {
                        Long numeroOperacion = consultarNumeroOperacionAgrupacion();
                        logger.info("entra tipoLinea: " + new ObjectMapper().writeValueAsString(carteraModeloDTO));
                        if (carteraModeloDTO.getTipoLineaCobro().equals(TipoLineaCobroEnum.LC1)) {
                            errores = validacionAccionCobroLc1(carteraModeloDTO, numeroOperacion);
                        } else if (carteraModeloDTO.getTipoLineaCobro().equals(TipoLineaCobroEnum.LC2)) {
                            errores = validacionAccionCobroLc2(carteraModeloDTO);
                        } else if (carteraModeloDTO.getTipoLineaCobro().equals(TipoLineaCobroEnum.C6)) {
                            errores = validacionAccionCobroC6(carteraModeloDTO, numeroOperacion);
                        }
                        manualCotizanteAportanteList
                            .add(carteraModelDtoToCargueManualCotizanteApotante(carteraModeloDTO, errores));
                        respuestaCargueMasivoAportante.setCotizanteAportanteList(manualCotizanteAportanteList);
                    }
                }
                respuestaCargueMasivoAportante.setResultado("Cargue Existoso");
            }
            return respuestaCargueMasivoAportante;
        } catch (Exception e) {
            logger.error("Ocurrió un error en InsertarCargueMasivo", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public CargueManualCotizanteAportante carteraModelDtoToCargueManualCotizanteApotante(
        CarteraModeloDTO carteraModeloDTO, RepuestaCargue repuestaCargue) {
        CargueManualCotizanteAportante cotizanteApotante = new CargueManualCotizanteAportante();
        cotizanteApotante.setEstadoCartera(carteraModeloDTO.getEstadoCartera());
        cotizanteApotante.setDeudaPresunta(carteraModeloDTO.getDeudaPresunta());
        cotizanteApotante.setLineaCobro(carteraModeloDTO.getTipoLineaCobro());
        cotizanteApotante.setTipoAccionCobro(carteraModeloDTO.getTipoAccionCobro());
        cotizanteApotante.setNumeroIdentificacion(carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion());
        cotizanteApotante.setTipoIdentificacion(carteraModeloDTO.getPersonaDTO().getTipoIdentificacion());
        cotizanteApotante.setPeriodo(carteraModeloDTO.getPeriodoDeuda());
        cotizanteApotante.setRazonSocial(carteraModeloDTO.getPersonaDTO().getRazonSocial());
        cotizanteApotante.setTipoDeuda(carteraModeloDTO.getTipoDeuda());
        cotizanteApotante.setTipoSolicitanteMovimientoAporte(carteraModeloDTO.getTipoSolicitante());
        cotizanteApotante.setIdCartera(repuestaCargue.getIdCartera());
        cotizanteApotante.setErrores(repuestaCargue.getErrores());
        return cotizanteApotante;
    }

    private RepuestaCargue validacionAccionCobroLc1(CarteraModeloDTO carteraModeloDTO, Long numeroOperacion)
        throws JsonProcessingException {
        List<String> errores = new ArrayList<>();
        RepuestaCargue repuestaCargue = new RepuestaCargue();
        List<CarteraAportantePersonaDTO> aportantePersonaDTOList = consultarGestionCarteraAportante(
            carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion(), TipoLineaCobroEnum.LC1.name());
        if (!aportantePersonaDTOList.isEmpty()) {
            logger.info("aportantePersonaDTOList: " + new ObjectMapper().writeValueAsString(aportantePersonaDTOList));
            for (CarteraAportantePersonaDTO aportantePersonaDTO : aportantePersonaDTOList) {
                logger.info("entra por else LC1: ");
                String estadoActivoInactivoEnum = consultarEstadoAportante(aportantePersonaDTO.getIdPersona());
                logger.info(
                    "estadoActivoInactivoEnum: " + new ObjectMapper().writeValueAsString(estadoActivoInactivoEnum));
                if (carteraModeloDTO.getEstadoCartera().equals(EstadoCarteraEnum.MOROSO) &&
                    estadoActivoInactivoEnum.equals(EstadoActivoInactivoEnum.ACTIVO.name())
                    /*
                     * &&carteraModeloDTO.getEstadoOperacion().equals(EstadoOperacionCarteraEnum.
                     * VIGENTE)
                     */) {
                    Boolean validarPeriodoFecha = validarPeriodoFecha(new Date(aportantePersonaDTO.getPeriodo()),
                        new Date(carteraModeloDTO.getPeriodoDeuda()));
                    logger.info("validarPeriodoFecha: " + validarPeriodoFecha);
                    if (validarPeriodoFecha == false) {
                        repuestaCargue.setIdCartera(insertarCarteraAportante(
                            mapperCarteraModeloDTO(aportantePersonaDTO, aportantePersonaDTO.getIdPersona(),
                                carteraModeloDTO, EstadoOperacionCarteraEnum.VIGENTE)));
                        insertarCarteraAgrupadora(numeroOperacion += 1, repuestaCargue.getIdCartera());
                    } else {
                        errores.add("Ya existe un periodo de deuda en el mismo mes");
                    }
                } else {
                    errores.add("Estados incorrectos");
                }
                repuestaCargue.setErrores(errores);
            }
        } else {
            logger.info("entra por else LC1: ");
            PersonaModeloDTO idPersona = consultarDatosPersona(carteraModeloDTO.getPersonaDTO().getTipoIdentificacion(),
                carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion());
            repuestaCargue.setIdCartera(insertarCarteraAportante(mapperCarteraModeloDTO(null, idPersona.getIdPersona(),
                carteraModeloDTO, EstadoOperacionCarteraEnum.VIGENTE)));
            insertarCarteraAgrupadora(numeroOperacion += 1, repuestaCargue.getIdCartera());
        }
        logger.info("repuestaCargue: " + new ObjectMapper().writeValueAsString(repuestaCargue));
        return repuestaCargue;
    }

    private RepuestaCargue validacionAccionCobroLc2(CarteraModeloDTO carteraModeloDTO) throws JsonProcessingException {
        List<String> errores = new ArrayList<>();
        RepuestaCargue repuestaCargue = new RepuestaCargue();
        List<CarteraAportantePersonaDTO> aportantePersonaDTOList = consultarGestionCarteraAportante(
            carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion(), TipoLineaCobroEnum.LC2.name());
        if (!aportantePersonaDTOList.isEmpty()) {
            logger.info("aportantePersonaDTOList: " + new ObjectMapper().writeValueAsString(aportantePersonaDTOList));
            for (CarteraAportantePersonaDTO aportantePersonaDTO : aportantePersonaDTOList) {
                logger.info("entro al for: ");
                String estadoActivoInactivoEnum = consultarEstadoAportante(aportantePersonaDTO.getIdPersona());
                logger.info(
                    "estadoActivoInactivoEnum: " + new ObjectMapper().writeValueAsString(estadoActivoInactivoEnum));
                if (carteraModeloDTO.getEstadoCartera().equals(EstadoCarteraEnum.AL_DIA)
                    && estadoActivoInactivoEnum.equals(EstadoActivoInactivoEnum.ACTIVO.name())
                    /*
                     * &&carteraModeloDTO.getEstadoOperacion().equals(EstadoOperacionCarteraEnum.
                     * NO_VIGENTE)
                     */) {
                    Boolean validarPeriodoFecha = validarPeriodoFecha(new Date(aportantePersonaDTO.getPeriodo()),
                        new Date(carteraModeloDTO.getPeriodoDeuda()));
                    logger.info("validarPeriodoFecha: " + validarPeriodoFecha);
                    if (validarPeriodoFecha == false) {
                        logger.info("validarPeriodoFecha::");
                        repuestaCargue.setIdCartera(insertarCarteraAportante(
                            mapperCarteraModeloDTO(aportantePersonaDTO, aportantePersonaDTO.getIdPersona(),
                                carteraModeloDTO, EstadoOperacionCarteraEnum.NO_VIGENTE)));
                    } else {
                        errores.add("Ya existe un periodo de deuda en el mismo mes");
                    }
                } else {
                    errores.add("Estados incorrecto");
                }
                repuestaCargue.setErrores(errores);
            }
        } else {
            logger.info("entra por else LC2: ");
            PersonaModeloDTO idPersona = consultarDatosPersona(carteraModeloDTO.getPersonaDTO().getTipoIdentificacion(),
                carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion());
            repuestaCargue.setIdCartera(insertarCarteraAportante(mapperCarteraModeloDTO(null, idPersona.getIdPersona(),
                carteraModeloDTO, EstadoOperacionCarteraEnum.NO_VIGENTE)));
        }
        logger.info("repuestaCargue: " + new ObjectMapper().writeValueAsString(repuestaCargue));
        return repuestaCargue;
    }

    private RepuestaCargue validacionAccionCobroC6(CarteraModeloDTO carteraModeloDTO, Long numeroOperacion)
        throws JsonProcessingException {
        List<String> errores = new ArrayList<>();
        RepuestaCargue repuestaCargue = new RepuestaCargue();
        List<CarteraAportantePersonaDTO> aportantePersonaDTOList = consultarGestionCarteraAportante(
            carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion(), TipoLineaCobroEnum.C6.name());
        if (!aportantePersonaDTOList.isEmpty()) {
            logger.info("aportantePersonaDTOList: " + new ObjectMapper().writeValueAsString(aportantePersonaDTOList));
            for (CarteraAportantePersonaDTO aportantePersonaDTO : aportantePersonaDTOList) {
                String estadoActivoInactivoEnum = consultarEstadoAportante(aportantePersonaDTO.getIdPersona());
                if (carteraModeloDTO.getEstadoCartera().equals(EstadoCarteraEnum.MOROSO)
                    && estadoActivoInactivoEnum.equals(EstadoActivoInactivoEnum.ACTIVO.name())) {
                    logger.info("validar antes de fecha::");
                    Boolean validarPeriodoFecha = validarPeriodoFecha(new Date(aportantePersonaDTO.getPeriodo()),
                        new Date(carteraModeloDTO.getPeriodoDeuda()));
                    logger.info("validarPeriodoFecha: " + validarPeriodoFecha);
                    if (validarPeriodoFecha == false) {
                        repuestaCargue.setIdCartera(insertarCarteraAportante(
                            mapperCarteraModeloDTO(aportantePersonaDTO, aportantePersonaDTO.getIdPersona(),
                                carteraModeloDTO, EstadoOperacionCarteraEnum.NO_VIGENTE)));
                        insertarCarteraAgrupadora(numeroOperacion += 1, repuestaCargue.getIdCartera());
                    } else {
                        errores.add("Ya existe un periodo de deuda en el mismo mes");
                    }
                } else {
                    errores.add("Estados incorrecto");
                }
                repuestaCargue.setErrores(errores);
            }
        } else {
            logger.info("entra por else LC6: ");
            PersonaModeloDTO idPersona = consultarDatosPersona(carteraModeloDTO.getPersonaDTO().getTipoIdentificacion(),
                carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion());
            repuestaCargue.setIdCartera(insertarCarteraAportante(mapperCarteraModeloDTO(null, idPersona.getIdPersona(),
                carteraModeloDTO, EstadoOperacionCarteraEnum.NO_VIGENTE)));
            insertarCarteraAgrupadora(numeroOperacion += 1, repuestaCargue.getIdCartera());
        }
        logger.info("repuestaCargue: " + new ObjectMapper().writeValueAsString(repuestaCargue));
        return repuestaCargue;
    }

    public CarteraModeloDTO mapperCarteraModeloDTO(CarteraAportantePersonaDTO carteraAportantePersonaDTO,
                                                   Long idPersona, CarteraModeloDTO carteraModeloDTO, EstadoOperacionCarteraEnum estadoOperacionCarteraEnum) {
        carteraModeloDTO.setIdPersona(
            carteraAportantePersonaDTO != null ? carteraAportantePersonaDTO.getIdPersona() : idPersona);
        carteraModeloDTO.setFechaCreacion(new Date().getTime());
        carteraModeloDTO.setFechaAsignacionAccion(null);
        carteraModeloDTO.setEstadoOperacion(estadoOperacionCarteraEnum);
        return carteraModeloDTO;
    }

    private ArrayList<CarteraModeloDTO> lecturaArchivoExcelCartera(InputStream file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Row row;
            int columna = 0, fila = 0;
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            XSSFSheet sheet = worbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            ArrayList<CarteraModeloDTO> listadoDocumento = new ArrayList<>();
            while (rowIterator.hasNext()) {
                CarteraModeloDTO modeloDTO = new CarteraModeloDTO();
                PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                row = rowIterator.next();
                if (fila != 0) {
                    // Obtiene las celdas por fila
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell;
                    while (cellIterator.hasNext() && columna < 12) {
                        // Obtiene la celda en específico y se la imprime
                        cell = cellIterator.next();
                        if (columna == 1) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 1::" + cell.getStringCellValue());
                                personaModeloDTO.setTipoIdentificacion(
                                    TipoIdentificacionEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        if (columna == 2) {
                            if (cell.getNumericCellValue() != 0.0) {
                                logger.info("columna 2::" + cell.getNumericCellValue());
                                personaModeloDTO.setNumeroIdentificacion(
                                    converterNumber(String.valueOf(cell.getNumericCellValue())));
                            }
                        }
                        if (columna == 3) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 3::" + cell.getStringCellValue());
                                personaModeloDTO.setRazonSocial(cell.getStringCellValue());
                            }
                        }
                        if (columna == 4) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 4::" + cell.getStringCellValue());
                                modeloDTO.setTipoSolicitante(
                                    TipoSolicitanteMovimientoAporteEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        if (columna == 5) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 5::" + cell.getStringCellValue());
                                modeloDTO.setEstadoCartera(EstadoCarteraEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        if (columna == 6) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 6::" + cell.getStringCellValue());
                                modeloDTO.setTipoDeuda(TipoDeudaEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        if (columna == 7) {
                            if (cell.getNumericCellValue() != 0.0) {
                                logger.info("columna 7::" + cell.getNumericCellValue());
                                modeloDTO.setDeudaPresunta(BigDecimal.valueOf((long) cell.getNumericCellValue()));
                            }
                        }
                        if (columna == 9) {
                            if (cell.getDateCellValue() != null) {
                                logger.info("columna 9::" + cell.getDateCellValue());
                                modeloDTO.setPeriodoDeuda(cell.getDateCellValue().getTime());
                            }
                        }
                        if (columna == 10) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 10::" + cell.getStringCellValue());
                                modeloDTO.setTipoLineaCobro(TipoLineaCobroEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        if (columna == 11) {
                            if (!StringUtils.isEmpty(cell.getStringCellValue())) {
                                logger.info("columna 11::" + cell.getStringCellValue());
                                modeloDTO.setTipoAccionCobro(TipoAccionCobroEnum.valueOf(cell.getStringCellValue()));
                            }
                        }
                        columna++;
                    }
                    modeloDTO.setPersonaDTO(personaModeloDTO);
                    listadoDocumento.add(modeloDTO);
                }
                columna = 0;
                fila++;
            }
            logger.info("listadoDocumento ::" + objectMapper.writeValueAsString(listadoDocumento));
            return listadoDocumento;
        } catch (Exception e) {
            logger.error("Error - Finaliza servicio lecturaArchivoExcelCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    private String converterNumber(String number) {
        Double numberDouble = Double.parseDouble(number);
        Long numberLong = (numberDouble).longValue();
        return String.valueOf(numberLong);
    }

    private Boolean validarPeriodoFecha(Date fecha, Date periodoFecha) {
        Boolean valiarFechaCargue;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fecha);
        cal2.setTime(periodoFecha);
        valiarFechaCargue = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.WEEK_OF_MONTH) == cal2.get(Calendar.WEEK_OF_MONTH);
        return valiarFechaCargue;
    }

    private List<CarteraAportantePersonaDTO> consultarGestionCarteraAportante(String numeroIdentificacion,
                                                                              String tipoLineaCobroEnum) {
        logger.info("Inicio de método consultarGestionCarteraAportante::::" + "numeroIdentificacion::"
            + numeroIdentificacion);
        ConsultarGestionCarteraAportante service = new ConsultarGestionCarteraAportante(numeroIdentificacion,
            tipoLineaCobroEnum);
        service.execute();
        return service.getResult();
    }

    private void insertarCarteraAgrupadora(Long numeroOperacion, Long idCartera) {
        logger.info("Inicio de método insertarCarteraAgrupadora :: numeroOperacion::" + numeroOperacion
            + " ::idCartera:: " + idCartera);
        InsertarCarteraAgrupadora service = new InsertarCarteraAgrupadora(numeroOperacion, idCartera);
        service.execute();
    }

    private Long insertarCarteraAportante(CarteraModeloDTO carteraModeloDTO) throws JsonProcessingException {
        logger.info("Inicio de método insertarCarteraAportante ::: "
            + new ObjectMapper().writeValueAsString(carteraModeloDTO));
        InsertarCarteraAportante service = new InsertarCarteraAportante(carteraModeloDTO);
        service.execute();
        return service.getResult();
    }

    private String consultarEstadoAportante(Long idPersona) {
        logger.info("Inicio de método consultarEstadoAportante ::: " + idPersona);
        ConsultarEstadoAportante service = new ConsultarEstadoAportante(idPersona);
        service.execute();
        return service.getResult();
    }

    private Long consultarNumeroOperacionAgrupacion() {
        logger.info("Inicio de método consultarNumeroOperacionAgrupacion ::: ");
        ConsultarNumeroOperacionAgrupacion service = new ConsultarNumeroOperacionAgrupacion();
        service.execute();
        return service.getResult();
    }

    /**
     * Método encargado de consultar las exclusiones de cartera que estan pendientes
     * por inactivar
     */
    private List<ExclusionCarteraDTO> consultarExclusionCarteraPorInactivar() {
        logger.debug("Inicio de método consultarExclusionCarteraPorInactivar");
        ConsultarExclusionCarteraPorInactivar service = new ConsultarExclusionCarteraPorInactivar();
        service.execute();
        logger.debug("Fin de método consultarExclusionCarteraPorInactivar");
        return service.getResult();
    }

    /**
     * Método encargado invocar el servicio de actualización de exclusiones de
     * cartera
     */
    private void actualizarExclusionCarteraInactivacion() {
        logger.debug("Inicio de método actualizarExclusionCartera");
        ActualizarExclusionCarteraInactivacion service = new ActualizarExclusionCarteraInactivacion();
        service.execute();
        logger.debug("Fin de método actualizarExclusionCartera");
    }

    @Override
    public void ejecutarProcesoAutomaticoFirmezaTitulo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("Inicia ejecutarProcesoAutomaticoFirmezaTitulo");
            List<FirmezaDeTituloDTO> firmezaList = consultarCarteraLineaCobro();
            logger.info("firmezaList::" + objectMapper.writeValueAsString(firmezaList));
            if (!firmezaList.isEmpty() || firmezaList != null || firmezaList.size() > 0) {
                firmezaList.forEach(firmeza -> {
                    if (!firmeza.getActividad().equals(TipoActividadBitacoraEnum.GENERAR_LIQUIDACION) &&
                        firmeza.getMetodo().equals(MetodoAccionCobroEnum.METODO_2)) {
                        crearFirmezaTituloBitacora(firmeza);
                    } else if (!firmeza.getMetodo().equals(MetodoAccionCobroEnum.METODO_2)) {
                        crearFirmezaTituloBitacora(firmeza);
                    }

                });
            }
            logger.info("Finaliza ejecutarProcesoAutomaticoFirmezaTitulo");
        } catch (Exception e) {
            logger.error("Error en servicio ejecutarProcesoAutomaticoFirmezaTitulo ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public BitacoraCarteraDTO consultarBitacoraPersona(Long id) {
        logger.info("Inicia consultarBitacoraPersona" + id);
        ConsultarBitacoraPersona service = new ConsultarBitacoraPersona(id);
        service.execute();
        logger.debug("Fin de método consultarBitacoraPersona");
        return service.getResult();
    }

    @Override
    public void EjecutarProcesoAutomaticoActaLiquidacion() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Inicia EjecutarProcesoAutomaticoActaLiquidacion");
            List<Cartera> carteraList = new ArrayList<>();
            List<Persona> personaList = new ArrayList<>();
            List<Object[]> result = consultarCarteraLineaCobro2c();
            logger.info("consulto la linea de cobro 2 c :: " + mapper.writeValueAsString(result));
            if (!result.isEmpty() && result.size() > 0) {
                logger.info("ingresa al primer if");
                for (Object[] consulta : result) {
                    Cartera cartera = new Cartera();
                    Persona persona = new Persona();
                    cartera.setTipoLineaCobro(
                        consulta[0] != null ? TipoLineaCobroEnum.valueOf(String.valueOf(consulta[0])) : null);
                    cartera.setTipoAccionCobro(
                        consulta[1] != null ? TipoAccionCobroEnum.valueOf(String.valueOf(consulta[1])) : null);
                    persona.setNumeroIdentificacion(consulta[2] != null ? String.valueOf(consulta[2]) : null);
                    persona.setTipoIdentificacion(
                        consulta[3] != null ? TipoIdentificacionEnum.valueOf(String.valueOf(consulta[3])) : null);
                    cartera.setTipoSolicitante(consulta[4] != null
                        ? TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(consulta[4]))
                        : null);
                    carteraList.add(cartera);
                    personaList.add(persona);
                }
                logger.info("finaliza el for con la data 1 :: carteraList :: personaList ::");
            }
            if (Objects.nonNull(carteraList)) {
                logger.info("ingresa al segundo if :: ");
                Long diasActaLiquidacion = consultarDiasActaGeneracion();
                logger.info("finaliza la consulta de los dias de acta de liquidacion :: "
                    + mapper.writeValueAsString(diasActaLiquidacion));
                Cartera car = new Cartera();
                Persona per = new Persona();
                for (int i = 0; i < carteraList.size(); i++) {
                    car = carteraList.get(i);
                    per = personaList.get(i);
                    Date fechaNotificacion = ConsultaFechaNotificacion(per.getNumeroIdentificacion());
                    try {
                        logger.info("finaliza la consulta de la fecha de notificacion personal ::::::"
                            + mapper.writeValueAsString(fechaNotificacion));
                        Integer diasTranscurridos = diferenciaDias(fechaNotificacion);
                        logger.info("calculo la diferencia de dias en la fecha :: DIAS TRANSCURRIDOS :: "
                            + mapper.writeValueAsString(diasTranscurridos));
                        logger.info(" ::: DIAS_ACTA_LIQUIDACION ::: " + mapper.writeValueAsString(diasActaLiquidacion));
                        if (diasTranscurridos >= (diasActaLiquidacion - 1)) {
                            logger.info("si cumple con los dias del parametro :: "
                                + mapper.writeValueAsString(diasTranscurridos));
                            if (car.getIdPersona() != null || !String.valueOf(car.getIdPersona()).isEmpty()) {

                                logger.info(
                                    "aqui ya paso consulta fecha notificacion, calcuo dif dias, dias trasncurridos ");
                                logger.info("aqui va a entrar a:  generarLiquidacionAportes");

                                LiquidacionAporteCarteraDTO liquidacion = generarLiquidacionAportes(
                                    per.getTipoIdentificacion(), per.getNumeroIdentificacion(),
                                    car.getTipoSolicitante(), car.getTipoLineaCobro());

                                logger.info("aqui ya paso generarLiquidacionAportes");
                                logger.info("va a entrar al metodo consultarNumeroDeOperacion ");
                                logger.info(
                                    "va a entrar al metodo consultarNumeroDeOperacion  donde posiblemente esta la falla");

                                Long numeroOperacion = consultarNumeroDeOperacion(per.getNumeroIdentificacion());
                                logger.info("pasa llamado metodo consultarNumeroDeOperacion ");

                                UserDTO user = new UserDTO();
                                user.setNombreUsuario("service-account-clientes_web");
                                if(liquidacion != null){
                                    guardarBitacoraLiquidacion(numeroOperacion, per.getTipoIdentificacion(),
                                        per.getNumeroIdentificacion(), car.getTipoSolicitante(),
                                        TipoActividadBitacoraEnum.GENERAR_LIQUIDACION,
                                        ResultadoBitacoraCarteraEnum.GENERADO_PERSONALMENTE,
                                        liquidacion.getIdECM() != null ? liquidacion.getIdECM() : null,
                                        user);
                                }
                            } else {
                                logger.error("Error en servicio EjecutarProcesoAutomaticoActaLiquidacion "
                                    + mapper.writeValueAsString(car));
                            }
                        } else {
                            logger.info("no se han cumplido los dias parametrizados");
                        }
                    } catch (JsonProcessingException e) {
                        logger.error("Error en servicio EjecutarProcesoAutomaticoActaLiquidacion " + e);
                    }
                }
            } else {
                logger.info("los objetos de validacion estan vacios :: carteraList :: "
                    + mapper.writeValueAsString(carteraList) + " :: personaList ::"
                    + mapper.writeValueAsString(personaList));
            }
            logger.info("Finaliza EjecutarProcesoAutomaticoActaLiquidacion ");
        } catch (Exception e) {
            logger.error("Error en servicio EjecutarProcesoAutomaticoActaLiquidacion ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<Object[]> DocumentoFiscalizacion(TipoIdentificacionEnum tipoIdentificacion,
                                                 String numeroIdentificacion) {
        logger.info("Inicia DocumentoFiscalizacion (TipoIdentificacionEnum " + tipoIdentificacion
            + " ,numeroIdentificacion " + numeroIdentificacion + ") ");
        try {
            DocumentoFiscalizacionData service = new DocumentoFiscalizacionData(tipoIdentificacion,
                numeroIdentificacion);
            service.execute();
            logger.info("Finaliza DocumentoFiscalizacion (TipoIdentificacionEnum " + tipoIdentificacion
                + " ,numeroIdentificacion " + numeroIdentificacion + ") ");
            return service.getResult();
        } catch (Exception e) {
            logger.error("Error en el servicio DocumentoFiscalizacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void ejecutarProcesoAutomaticoPrescribirCartera() {
        try {
            logger.info("Inicia ejecutarProcesoAutomaticoPrescribirCartera");
            procesoValidacionCarteraPrescrita();
            logger.info("Finaliza ejecutarProcesoAutomaticoPrescribirCartera");
        } catch (Exception e) {
            logger.error("Error en el servicio ejecutarProcesoAutomaticoPrescribirCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public void procesoValidacionCarteraPrescrita() {
        logger.info("Inicio de método procesoValidacionCarteraPrescrita");
        ProcesoValidacionCarteraPrescrita service = new ProcesoValidacionCarteraPrescrita();
        service.execute();
        logger.info("Fin de método procesoValidacionCarteraPrescrita");
    }

    public Date ConsultaFechaNotificacion(String numeroIdentificacion) {
        try {
            logger.info("Inicio de método ConsultaFechaNotificacion ");
            ConsultarFechaNotificacionBitacora service = new ConsultarFechaNotificacionBitacora(numeroIdentificacion);
            service.execute();
            logger.info("Fin de método ConsultaFechaNotificacion ");
            return service.getResult();
        } catch (Exception e) {
            logger.error("Error en el servicio ConsultaFechaNotificacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public Long consultarNumeroDeOperacion(String numeroIdentificacion) {
        try {
            logger.info("Inicio de método consultarNumeroDeOperacion ");
            // logger.debug("Inicio de método ConsultarNumeroDeOperacion ");
            ConsultarNumeroDeOperacion service = new ConsultarNumeroDeOperacion(numeroIdentificacion);
            service.execute();
            logger.info("Fin de método consultarNumeroDeOperacion ");
            return service.getResult();
        } catch (Exception e) {
            logger.error("Error en el servicio consultarNumeroDeOperacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public Long consultarDiasActaGeneracion() {
        logger.info("Inicio de método consultarDiasActaGeneracion ");
        ConsultarDiasActaGeneracion service = new ConsultarDiasActaGeneracion();
        service.execute();
        logger.info("Finaliza de método consultarDiasActaGeneracion ");
        return service.getResult();
    }

    public List<FirmezaDeTituloDTO> consultarCarteraLineaCobro() {
        logger.info("Inicio de método ConsultarCarteraLineaCobro");
        ConsultarCarteraLineaCobro service = new ConsultarCarteraLineaCobro();
        service.execute();
        logger.info("Fin de método ConsultarCarteraLineaCobro");
        return service.getResult();
    }

    public List<Object[]> consultarCarteraLineaCobro2c() {
        logger.info("Inicio de método consultarCarteraLineaCobro2c");
        ConsultarCarteraLineaDeCobro2C service = new ConsultarCarteraLineaDeCobro2C();
        service.execute();
        logger.info("Fin de método consultarCarteraLineaCobro2c");
        return service.getResult();
    }

    public FiltroIdPersonaDTO consultarNumeroTipoIdPersona(Long idPersona) {
        logger.info("Inicio de método consultarNumeroTipoIdPersona " + idPersona);
        ConsultarNumeroTipoIdPersona service = new ConsultarNumeroTipoIdPersona(idPersona);
        service.execute();
        logger.info("Finaliza de método consultarNumeroTipoIdPersona ");
        return service.getResult();
    }

    public NotificacionPersonalModeloDTO mapperPersonalModeloDTO(FiltroCarteraLineaCobroDTO filtroCarteraLineaCobroDTO,
                                                                 FiltroIdPersonaDTO personaModeloDTO) {
        logger.info("Inicio de método mapperPersonalModeloDTO");
        NotificacionPersonalModeloDTO modeloDTO = new NotificacionPersonalModeloDTO();
        modeloDTO.setIdCartera(filtroCarteraLineaCobroDTO.getIdCartera());
        modeloDTO.setTipoIdentificacion(personaModeloDTO.getTipoIdentificacion());
        modeloDTO.setNumeroIdentificacion(personaModeloDTO.getNumeroIdentificacion());
        modeloDTO.setTipoSolicitante(filtroCarteraLineaCobroDTO.getTipoSolicitante());
        modeloDTO.setActividad(ActividadNotificacionCicloCarteraEnum.FIRMEZA_TITULO_EJECUTIVO);
        logger.info("finaliza de método mapperPersonalModeloDTO");
        return modeloDTO;
    }

    public Integer diferenciaDias(Date periodoDeuda) {
        return ((int) TimeUnit.DAYS.convert(Math.abs(new Date().getTime() - periodoDeuda.getTime()),
            TimeUnit.MILLISECONDS));
    }

    public String consultarFirmezaTituloBitacora(Long idPersona) {
        logger.info("Inicio de método consultarFirmezaTituloBitacora " + idPersona);
        ConsultarFirmezaTituloBitacora service = new ConsultarFirmezaTituloBitacora(idPersona);
        service.execute();
        logger.info("Finaliza de método consultarFirmezaTituloBitacora " + service.getResult().toString());
        return service.getResult();
    }

    public BitacoraCarteraDTO actualizarBitacoraGestionCobro(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificaion,String numeroRadicacion,ResultadoEntregaEnum resultadoPrimeraEntrega){
        logger.info("Inicio de método actualizarBitacoraGestionCobro " + tipoIdentificacion + numeroIdentificaion + numeroRadicacion);
        ConsultarBitacoraPersonaRadicado consulta = new ConsultarBitacoraPersonaRadicado(tipoIdentificacion, numeroIdentificaion, numeroRadicacion);
        consulta.execute();
        return consulta.getResult();

        // bitacora.setResultado(resultadoPrimeraEntrega);

        // guardarBitacoraCartera(bitacora);
    }

    /**
     * Devuelve la última bitácora para actualizar ordenada por fecha DESC
     * Si se requiere más funcionalidad aplicar filtros cuando sea mayor a 1 el tamaño de resultado
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroRadicacion
     * @return
     */
    public BitacoraCarteraDTO consultarBitacoraAActualizarPersonaRadicado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion) {
        logger.info("Iniciando consulta de Bitácora para Tipo Identificación: " + tipoIdentificacion.name() + ", Número Identificación: " + numeroIdentificacion + ", Número Radicación: " + numeroRadicacion);
        try {
            ConsultarBitacoraAActualizarPersonaRadicado consulta = new ConsultarBitacoraAActualizarPersonaRadicado(tipoIdentificacion, numeroIdentificacion, numeroRadicacion);
            consulta.execute();
            List<BitacoraCarteraDTO> resultado = consulta.getResult();

            if (resultado == null || resultado.isEmpty()) {
                logger.info("No se encontraron registros en la consulta para Número Radicación: " + numeroRadicacion);
                return null;
            }

            logger.info("Consulta de Bitácora completada. Número de registros encontrados: " + resultado.size());

            // Devuelve el primer registro si existe más de uno, considerar manejar esta situación según las reglas del negocio
            if (resultado.size() > 1) {
                logger.warn("Más de un registro encontrado, se devolverá el primero. Número Radicación: " + numeroRadicacion);
                //List<BitacoraCarteraDTO> registrosEnProceso = resultado.stream().filter(b -> ResultadoBitacoraCarteraEnum.EN_PROCESO.equals(b.getResultado())).collect(Collectors.toList());
            }
            return resultado.get(0);
        } catch (Exception e) {
            logger.error("Error al consultar Bitácora para Número Radicación: " + numeroRadicacion, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
}
