package com.asopagos.novedades.ejb;

import static com.asopagos.util.Interpolator.interpolate;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.SolicitudNovedadGeneralDTO;
import com.asopagos.dto.FiltroConsultaSolicitudesEnProcesoDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.core.ParametrizacionNovedad;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.CargueMultipleSupervivencia;
import com.asopagos.entidades.ccf.novedades.IntentoNovedad;
import com.asopagos.entidades.ccf.novedades.IntentoNovedadRequisito;
import com.asopagos.entidades.ccf.novedades.RegistroNovedadFutura;
import com.asopagos.entidades.ccf.novedades.RegistroPersonaInconsistente;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPila;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.novedades.EmpleadoresDesafiliacionMasiva;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.EstadoGestionEnum;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.EstadoDesafiliacionMasivaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.CrearSolicitudNovedadEmpleador;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.novedades.dto.DatosAfiliadoRetiroDTO;
import com.asopagos.novedades.dto.DatosExcepcionNovedadDTO;
import com.asopagos.novedades.dto.DatosFiltroConsultaDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.DatosNovedadRegistradaPersonaDTO;
import com.asopagos.novedades.dto.DatosNovedadVista360DTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;
import com.asopagos.novedades.dto.GenerarReporteSupervivenciaDTO;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import com.asopagos.novedades.service.NovedadesService;
import com.asopagos.pagination.PaginationQueryParamsEnum;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.novedadesrutines.actualizarestadosolicitudnovedad.ActualizarEstadoSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.actualizarsolicitudnovedadpersona.ActualizarSolicitudNovedadPersonaRutine;
import com.asopagos.rutine.novedadesrutines.consultarnovedadpornombre.ConsultarNovedadPorNombreRutine;
import com.asopagos.rutine.novedadesrutines.crearintentonovedad.CrearIntentoNovedadRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedad.CrearSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedadEmpleador.CrearSolicitudNovedadEmpleadorRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedadPersona.CrearSolicitudNovedadPersonaRutine;
import com.asopagos.rutine.novedadesrutines.guardarexcepcionnovedad.GuardarExcepcionNovedadRutine;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.enums.FileGeneratedState;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
//import com.asopagos.rutines;
/**nuevo */
import com.asopagos.novedades.dto.DatosReporteAfiliadosSupervivenciaDTO;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.util.GetValueUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.persistence.NonUniqueResultException;
// import com.asopagos.aportes.composite.clients.InsercionAnulacionPlanillas;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con las novedades de empleadores o personas. <b>Historia de Usuario:</b>
 * proceso 1.3 Novedades
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Stateless
public class NovedadesBusiness implements NovedadesService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;

    /**
     * Referencia a la unidad de persistencia staging PILA
     */
    @PersistenceContext(unitName = "pilaStaging_PU")
    private EntityManager entityManagerStaging;

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesBusiness.class);

    @Inject
    private FileGenerator fileGenerator;

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * consultarNovedadPorTipoTransaccion (java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionNovedadModeloDTO consultarNovedadPorNombre(TipoTransaccionEnum tipoTransaccion) {
        ConsultarNovedadPorNombreRutine c = new ConsultarNovedadPorNombreRutine();
        System.out.println("**__** novedades bussines consultarNovedadPorNombre: " + tipoTransaccion);
        return c.consultarNovedadPorNombre(tipoTransaccion, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.novedades.service.NovedadesService#crearSolicitudNovedad(com
     * .asopagos.novedades.dto.NovedadDTO)
     */
    @Override
    public SolicitudNovedadModeloDTO crearSolicitudNovedad(SolicitudNovedadModeloDTO solicitudNovedad) {
        /*
         * try {
         * logger.
         * debug("Inicio de método crearSolicitudNovedad(SolicitudNovedad solicitudNovedad)"
         * );
         * Solicitud solicitud = solicitudNovedad.convertToSolicitudEntity();
         * if (solicitud.getIdSolicitud() == null) {
         * entityManager.persist(solicitud);
         * logger.info("solId NULL (P)");
         * } else {
         * entityManager.merge(solicitud);
         * logger.info("solId (M) : " + solicitud.getIdSolicitud());
         * }
         * SolicitudNovedad solicitudNovedadE = solicitudNovedad.convertToEntity();
         * solicitudNovedadE.setSolicitudGlobal(solicitud);
         * if (solicitudNovedadE.getIdSolicitudNovedad() == null) {
         * entityManager.persist(solicitudNovedadE);
         * logger.info("snpId NULL (P)");
         * }
         * else {
         * entityManager.merge(solicitudNovedadE);
         * logger.info("snpId NULL (M) : " + solicitudNovedadE.getIdSolicitudNovedad());
         * }
         * solicitudNovedad.convertToDTO(solicitudNovedadE);
         * logger.
         * debug("Fin de método crearSolicitudNovedad(SolicitudNovedad solicitudNovedad)"
         * );
         * return solicitudNovedad;
         * } catch (Exception e) {
         * logger.
         * error("Ocurrió un error inesperado en crearSolicitudNovedad(NovedadDTO novedadDTO, UserDTO userDTO)"
         * );
         * throw new
         * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
         * }
         */
        CrearSolicitudNovedadRutine a = new CrearSolicitudNovedadRutine();
        return a.crearSolicitudNovedad(solicitudNovedad, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * actualizarSolicitudNovedad(com.asopagos.entidades.ccf.novedades.
     * SolicitudNovedad)
     */
    @Override
    public void actualizarSolicitudNovedad(Long idSolicitudGlobal, SolicitudNovedadModeloDTO solicitudNovedad) {
        try {
            logger.debug("Inicia actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
            SolicitudNovedad solicitudNovedadE = solicitudNovedad.convertToEntity();
            SolicitudNovedadModeloDTO s = consultarSolicitudNovedad(idSolicitudGlobal);
            if (s.getIdSolicitudNovedad() != null) {
                solicitudNovedadE.setIdSolicitudNovedad(s.getIdSolicitudNovedad());
                entityManager.merge(solicitudNovedadE);
            }
        } catch (Exception e) {
            logger.debug("Ocurrió un error inesperado actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * actualizarEstadoSolicitudNovedad(java.lang.Long,
     * com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum)
     */
    @Override
    public void actualizarEstadoSolicitudNovedad(Long idSolicitudGlobal, EstadoSolicitudNovedadEnum estadoSolicitud) {
        /*
         * try {
         * logger.
         * debug("Inicia actualizarEstadoSolicitudNovedad(Long idSolicitudNovedad, EstadoSolicitudNovedadEnum estado)"
         * );
         * SolicitudNovedadModeloDTO solicitudNovedadDTO =
         * consultarSolicitudNovedad(idSolicitudGlobal);
         * SolicitudNovedad solicitudNovedad = solicitudNovedadDTO.convertToEntity();
         *
         * //
         * // se verifica si el nuevo estado es CERRADA para actualizar el
         * // resultado del proceso
         * //
         * if (EstadoSolicitudNovedadEnum.CERRADA.equals(estadoSolicitud)
         * && (EstadoSolicitudNovedadEnum.APROBADA.equals(solicitudNovedad.
         * getEstadoSolicitud())
         * || EstadoSolicitudNovedadEnum.RECHAZADA.equals(solicitudNovedad.
         * getEstadoSolicitud())
         * || EstadoSolicitudNovedadEnum.CANCELADA.equals(solicitudNovedad.
         * getEstadoSolicitud())
         * || EstadoSolicitudNovedadEnum.DESISTIDA.equals(solicitudNovedad.
         * getEstadoSolicitud()))) {
         * solicitudNovedad.getSolicitudGlobal()
         * .setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudNovedad.
         * getEstadoSolicitud().name()));
         * }
         * solicitudNovedad.setEstadoSolicitud(estadoSolicitud);
         * entityManager.merge(solicitudNovedad);
         * logger.info("SolicitudNovedad: "+ solicitudNovedad.getIdSolicitudNovedad() +
         * " estado: " + solicitudNovedad.getEstadoSolicitud() + " global: " +
         * solicitudNovedad.getSolicitudGlobal().getIdSolicitud() + " estado : " +
         * solicitudNovedad.getSolicitudGlobal().getResultadoProceso());
         * } catch (Exception e) {
         * logger.
         * debug("Ocurrio un error inesperado actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudNovedadEnum)"
         * );
         * throw new
         * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
         * }
         */
        logger.info(
                "Inicia actualizarEstadoSolicitudNovedad en novedades bussines a continuacion ActualizarEstadoSolicitudNovedadRutine estadoSolicitud: "
                        + estadoSolicitud);
        ActualizarEstadoSolicitudNovedadRutine a = new ActualizarEstadoSolicitudNovedadRutine();
        a.actualizarEstadoSolicitudNovedad(idSolicitudGlobal, estadoSolicitud, entityManager);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.novedades.service.NovedadesService#consultarSolicitudNovedad
     * (java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudNovedadModeloDTO consultarSolicitudNovedad(Long idSolicitud) {
        logger.debug("Inicia consultarSolicitudNovedad(Long idSolicitud) " + idSolicitud);
        try {
            SolicitudNovedad sol = (SolicitudNovedad) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            SolicitudNovedadModeloDTO solicitudDTO = new SolicitudNovedadModeloDTO();
            solicitudDTO.convertToDTO(sol);
            logger.debug("Fin consultarSolicitudNovedad(Long idSolicitud) " + idSolicitud);
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudNovedadPorNumeroRadicado(String)"
                    + interpolate("No se encontraron resultados con el nro radicación {0} ingresada.", idSolicitud));
            return null;
        }
        // catch (NonUniqueResultException nur) {
        // logger.error("Finaliza consultarSolicitudNovedadPorNumeroRadicado(String)");
        // throw new
        // TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
        // } catch (Exception e) {
        // logger.error("Ocurrió un error inesperado en consultarSolicitudNovedad(Long
        // idSolicitudNovedad)");
        // throw new
        // TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        // }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.novedades.service.NovedadesService#crearIntentoNovedad(com.
     * asopagos.novedades.dto.IntentoNovedadDTO)
     */
    @Override
    public Long crearIntentoNovedad(IntentoNovedadDTO intentoNovedadDTO) {
        /*
         * try {
         * logger.
         * debug("Ingresa al mètodo crearIntentoNovedad(IntentoNovedadDTO intentoNovedadDTO)"
         * );
         * IntentoNovedad intentoNovedad = new IntentoNovedad();
         * intentoNovedad.setCausaIntentoFallido(intentoNovedadDTO.
         * getCausaIntentoFallido());
         * intentoNovedad.setFechaCreacion(new Date());
         * intentoNovedad.setFechaCreacion(intentoNovedadDTO.getFechaCreacion());
         * intentoNovedad.setIdSolicitud(intentoNovedadDTO.getIdSolicitud());
         * intentoNovedad.setTipoTransaccion(intentoNovedadDTO.getTipoTransaccion());
         * entityManager.persist(intentoNovedad);
         * if (CausaIntentoFallidoNovedadEnum.VALIDACION_REQUISITOS_DOCUMENTALES.equals(
         * intentoNovedadDTO.getCausaIntentoFallido())) {
         * for (Long idRequisito : intentoNovedadDTO.getRequisitos()) {
         * IntentoNovedadRequisito intentoNovedadRequisito = new
         * IntentoNovedadRequisito();
         * intentoNovedadRequisito.setIntentoNovedad(intentoNovedad);
         * intentoNovedadRequisito.setIdRequisito(idRequisito);
         * entityManager.persist(intentoNovedadRequisito);
         * }
         * }
         * logger.
         * debug("Ingresa al método crearIntentoNovedad(IntentoNovedadDTO intentoNovedadDTO)"
         * );
         * return intentoNovedad.getIdIntentoNovedad();
         * } catch (Exception e) {
         * logger.
         * error("Ocurrió un error inesperado en crearSolicitudNovedad(NovedadDTO novedadDTO, UserDTO userDTO)"
         * );
         * throw new
         * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
         * }
         */

        logger.info("Inicia llamado rutina generarReporteAfiliadosSupervivencia(GenerarReporteSupervivenciaDTO)");
        CrearIntentoNovedadRutine c = new CrearIntentoNovedadRutine();
        return c.crearIntentoNovedad(intentoNovedadDTO, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * crearSolicitudNovedadEmpleador(com.asopagos.entidades.ccf.novedades.
     * SolicitudNovedadEmpleador)
     */
    @Override
    public SolicitudNovedadEmpleador crearSolicitudNovedadEmpleador(
            SolicitudNovedadEmpleador solicitudNovedadEmpleador) {
        CrearSolicitudNovedadEmpleadorRutine c = new CrearSolicitudNovedadEmpleadorRutine();
        return c.crearSolicitudNovedadEmpleador(solicitudNovedadEmpleador, entityManager);
    }

    /**
     * nuevo
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DatosReporteAfiliadosSupervivenciaDTO> generarReporteAfiliadosSupervivenciaJson(
            GenerarReporteSupervivenciaDTO gerarReporte) {
        // comentado asignar directamente en dto si variables en json pero no se podria
        // hacer ciclo
        // logger.debug(
        // "Inicio del método generarReporteAfiliadosSupervivenciaJson(String estado)");
        // logger.info("**__** estado: "+estado);
        // // DatosReporteAfiliadosSupervivenciaDTO trazabilidad = null;
        // gerarReporte.getCriteriosBusquedad().
        // try {
        // List<DatosReporteAfiliadosSupervivenciaDTO> registrostxt = entityManager
        // .createNamedQuery(NamedQueriesConstants.CONSULTAR_GENERAR_REPORTE_AFILIACION_SUPERVIVENCIA)
        // .setParameter("estado", estado.toString()).getResultList();
        //
        // if (!registrostxt.isEmpty()) {
        // // trazabilidad.convertToDTO(registrostxt.get(0));
        // logger.info("**__** get(0): "+registrostxt.get(0));
        // logger.info("**__** registrostxt: "+registrostxt);
        // }
        // logger.debug("Fin de m�todo consultarTrazabilidadPorId(Long
        // idTrazabilidad)");
        // return registrostxt;
        //
        // } catch (Exception e) {
        // logger.info("**__** error1: "+e);
        // logger.error(
        // "Ocurri� un error inesperado en el servicio ",e);
        // throw new
        // TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        // }
        DatosFiltroConsultaDTO datosFiltroConsultaDTO = new DatosFiltroConsultaDTO();
        datosFiltroConsultaDTO.setCriteriosBusquedad(gerarReporte.getCriteriosBusquedad());
        datosFiltroConsultaDTO.setAfiliadosDto(gerarReporte.getAfiliadosDto());
        datosFiltroConsultaDTO.setCodigoEntidad(
                CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString());

        List<Object[]> datosPersonaList = null;
        try {
            List<DatosReporteAfiliadosSupervivenciaDTO> ListadoTxt = null;
            ListadoTxt = new ArrayList<>();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            DatosReporteAfiliadosSupervivenciaDTO datosReporteAfiliadosSupervivenciaDTOHeader = new DatosReporteAfiliadosSupervivenciaDTO();
            datosReporteAfiliadosSupervivenciaDTOHeader.setCodigoEntidad(datosFiltroConsultaDTO.getCodigoEntidad());
            datosReporteAfiliadosSupervivenciaDTOHeader.setFechaDescarga(dtf.format(now).toString());
            try {
                String numeroNIT = (String) entityManager
                        .createNamedQuery(NamedQueriesConstants.NUMERO_IDENTIFICACION_NIT_CAJA).getSingleResult();
                datosReporteAfiliadosSupervivenciaDTOHeader.setNumeroEntidad(numeroNIT);
            } catch (Exception e) {
                datosReporteAfiliadosSupervivenciaDTOHeader.setNumeroEntidad("000000000");
            }

            ListadoTxt.add(datosReporteAfiliadosSupervivenciaDTOHeader);
            if (gerarReporte.getCriteriosBusquedad().size() > 0) {
                for (int x = 0; x < gerarReporte.getCriteriosBusquedad().size(); x++) {
                    datosPersonaList = entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_GENERAR_REPORTE_AFILIACION_SUPERVIVENCIA)
                            .setParameter("estado", gerarReporte.getCriteriosBusquedad().get(x).toString())
                            .getResultList();
                    if (datosPersonaList != null && !datosPersonaList.isEmpty()) {
                        for (Object[] datosPersona : datosPersonaList) {
                            DatosReporteAfiliadosSupervivenciaDTO datosReporteAfiliadosSupervivenciaDTO = new DatosReporteAfiliadosSupervivenciaDTO();
                            datosReporteAfiliadosSupervivenciaDTO.setCodigo((String) datosPersona[0]);
                            datosReporteAfiliadosSupervivenciaDTO.setTipoIdentificacion((String) datosPersona[1]);
                            datosReporteAfiliadosSupervivenciaDTO.setNumeroIdentificacion((String) datosPersona[2]);
                            ListadoTxt.add(datosReporteAfiliadosSupervivenciaDTO);
                        }
                    }
                    logger.debug(
                            "fin de método generarReporteAfiliadosSupervivenciaJson(String estado)");
                }
            }
            return ListadoTxt;
        } catch (Exception e) {
            logger.debug(
                    "catch generarReporteAfiliadosSupervivenciaJson(String estado)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public Response generarReporteAfiliadosSupervivencia(GenerarReporteSupervivenciaDTO gerarReporte) {
        logger.debug("Inicia generarReporteAfiliadosSupervivencia(GenerarReporteSupervivenciaDTO)");
        // List<Afiliado> afiliados = new ArrayList<Afiliado>();
        FileFormat[] format = {FileFormat.DELIMITED_TEXT_PLAIN};
        DatosFiltroConsultaDTO datosFiltroConsultaDTO = new DatosFiltroConsultaDTO();
        datosFiltroConsultaDTO.setCriteriosBusquedad(gerarReporte.getCriteriosBusquedad());
        datosFiltroConsultaDTO.setAfiliadosDto(gerarReporte.getAfiliadosDto());
        datosFiltroConsultaDTO.setCodigoEntidad(
                CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString());
        try {
            FileGeneratorOutDTO outDTO = fileGenerator.generate(2L, datosFiltroConsultaDTO, format);
            ResponseBuilder response = null;
            if (outDTO != null && (outDTO.getState().equals(FileGeneratedState.FAILED)
                    || outDTO.getDelimitedTxt() == null)) {
                response = Response.noContent();
                return response.build();
            }

            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(outDTO.getDelimitedTxt());
            String nombre = "SMC";
            nombre = nombre
                    .concat(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString());
            nombre = nombre.concat(CalendarUtils.darFormatoYYYYMMDDSinGuion(new Date()));
            nombre = nombre.concat(".txt");
            response = Response.ok(byteArrayIS);
            response.header("Content-Type", "text/plain");
            response.header("Content-Disposition", "attachment; filename=" + nombre);

            logger.debug("Finaliza generarReporteAfiliadosSupervivencia(GenerarReporteSupervivenciaDTO)");
            return response.build();

        } catch (FileGeneratorException e) {
            logger.debug("Error generarReporteAfiliadosSupervivencia(GenerarReporteSupervivenciaDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

   @Override
    public List<SolicitudNovedadEnProcesoDTO> consultarSolicitudesNovedadEnProceso(FiltroConsultaSolicitudesEnProcesoDTO filtro) {
        logger.info("Inicia consultarSolicitudesNovedadEnProceso");

        try {
          List<SolicitudNovedadEnProcesoDTO> resultados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDADES_POR_EMPLEADOR, SolicitudNovedadEnProcesoDTO.class)
                .setParameter("tipoIdentificacionEmpleador", filtro.getTipoIdentificacionEmpleador().name())
                .setParameter("numeroIdentificacionEmpleador", filtro.getNumeroIdentificacionEmpleador())
                .setParameter("tipoIdentificacionSolicitante", filtro.getTipoIdentificacionSolicitante() != null ? filtro.getTipoIdentificacionSolicitante().name() : null)
                .setParameter("numeroIdentificacionSolicitante", filtro.getNumeroIdentificacionSolicitante()!= null ? filtro.getNumeroIdentificacionSolicitante() : null)
                .setParameter("numeroSolicitud", filtro.getNumeroSolicitud() != null ? filtro.getNumeroSolicitud() : null)
                .setParameter("fechaInicio", filtro.getFechaInicio() != null ?  new Date(filtro.getFechaInicio()) : null, TemporalType.TIMESTAMP)
                .setParameter("fechaFin", filtro.getFechaFin() != null ? limpiarHora(new Date(filtro.getFechaFin())) : null, TemporalType.TIMESTAMP)
                .getResultList();

            return resultados;

        } catch (Exception e) {
            logger.error("Error inesperado en consultarSolicitudesNovedadEnProceso", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public List<SolicitudNovedadEnProcesoDTO> consultarSolicitudesYNovedadCerradasPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO filtro) {
        logger.info("Inicia consultarSolicitudesYNovedadCerradasPorEmpleador");
        try {
          List<SolicitudNovedadEnProcesoDTO> resultados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDADES_POR_EMPLEADOR_CERRADAS, SolicitudNovedadEnProcesoDTO.class)
                .setParameter("tipoIdentificacionEmpleador", filtro.getTipoIdentificacionEmpleador().name())
                .setParameter("numeroIdentificacionEmpleador", filtro.getNumeroIdentificacionEmpleador())
                .setParameter("tipoIdentificacionSolicitante", filtro.getTipoIdentificacionSolicitante() != null ? filtro.getTipoIdentificacionSolicitante().name() : null)
                .setParameter("numeroIdentificacionSolicitante", filtro.getNumeroIdentificacionSolicitante()!= null ? filtro.getNumeroIdentificacionSolicitante() : null)
                .setParameter("numeroSolicitud", filtro.getNumeroSolicitud() != null ? filtro.getNumeroSolicitud() : null)
                .setParameter("fechaInicio", filtro.getFechaInicio() != null ? new Date(filtro.getFechaInicio()) : null, TemporalType.TIMESTAMP)
                .setParameter("fechaFin", filtro.getFechaFin() != null ? limpiarHora(new Date(filtro.getFechaFin())) : null, TemporalType.TIMESTAMP)
                .getResultList();

            return resultados;

        } catch (Exception e) {
            logger.error("Error inesperado en consultarSolicitudesYNovedadCerradasPorEmpleador", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

     private Date limpiarHora(Date date) {
        if (date == null) return null;
        Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * consultarSolicitudesNovedad(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.Long,
     * com.asopagos.enumeraciones.core.CanalRecepcionEnum)
     */
    @Override
    public List<SolicitudNovedadModeloDTO> consultarSolicitudesNovedad(TipoIdentificacionEnum tipoIdentificacion,
                                                                       Long numeroIdentificacion,
                                                                       CanalRecepcionEnum canalRecepcion, TipoTipoSolicitanteEnum tipoSolicitante) {

        try {
            logger.debug("Inicio de método consultarSolicitudesNovedad(TipoIdentificacionEnum tipoIdentificacion, "
                    + "Long numeroIdentificacion, CanalRecepcionEnum canalRecepcion)");

            List<SolicitudNovedad> solicitudesNovedad = new ArrayList<SolicitudNovedad>();

            if (canalRecepcion != null) {
                if (tipoSolicitante != null && tipoSolicitante.equals(TipoTipoSolicitanteEnum.PERSONA)) {

                    solicitudesNovedad = (List<SolicitudNovedad>) entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDAD_PERSONA_CON_CANAL_RECEPCION)
                            .setParameter("tipoIdentificacion", tipoIdentificacion)
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString())
                            .setParameter("canalRecepcion", canalRecepcion).getResultList();
                } else {
                    solicitudesNovedad = (List<SolicitudNovedad>) entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDAD_EMPLEADOR_CON_CANAL_RECEPCION)
                            .setParameter("tipoIdentificacion", tipoIdentificacion)
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString())
                            .setParameter("canalRecepcion", canalRecepcion).getResultList();
                }
            } else {

                if (tipoSolicitante != null && tipoSolicitante.equals(TipoTipoSolicitanteEnum.PERSONA)) {
                    solicitudesNovedad = (List<SolicitudNovedad>) entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDAD_PERSONA_SIN_CANAL_RECEPCION)
                            .setParameter("tipoIdentificacion", tipoIdentificacion)
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString()).getResultList();
                } else {

                    solicitudesNovedad = (List<SolicitudNovedad>) entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_SOLICITUDES_NOVEDAD_EMPLEADOR_SIN_CANAL_RECEPCION_NATIVE)
                            .setParameter("tipoIdentificacion", tipoIdentificacion)
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString()).getResultList();
                }

            }

            logger.debug("Fin de método consultarSolicitudesNovedad(TipoIdentificacionEnum tipoIdentificacion, "
                    + "Long numeroIdentificacion, CanalRecepcionEnum canalRecepcion)");

            List<SolicitudNovedadModeloDTO> solicitudesNovedadDTO = new ArrayList<SolicitudNovedadModeloDTO>();

            for (SolicitudNovedad solicitudNovedad : solicitudesNovedad) {

                SolicitudNovedadModeloDTO solicitudNovedadModeloDTO = new SolicitudNovedadModeloDTO();
                solicitudNovedadModeloDTO.convertToDTO(solicitudNovedad);
                solicitudesNovedadDTO.add(solicitudNovedadModeloDTO);
            }

            return solicitudesNovedadDTO;

        } catch (Exception e) {
            logger.error(
                    "Ocurrió un error inesperado en consultarSolicitudesNovedad(TipoIdentificacionEnum tipoIdentificacion, "
                            + "Long numeroIdentificacion, CanalRecepcionEnum canalRecepcion)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * crearSolicitudNovedadPersona(com.asopagos.entidades.ccf.novedades.
     * SolicitudNovedadPersona)
     */
    @Override
    public SolicitudNovedadPersona crearSolicitudNovedadPersona(SolicitudNovedadPersona solicitudNovedadPersona) {
        /*
         * try {
         * logger.
         * debug("Inicio de método crearSolicitudNovedadPersona(crearSolicitudNovedadPersona solicitudNovedadPersona)"
         * );
         * if (solicitudNovedadPersona.getIdSolicitudNovedadPersona() == null) {
         * entityManager.persist(solicitudNovedadPersona);
         * logger.info("SolicitudNovedadPersona id NULL (P)");
         * }
         * else {
         * entityManager.merge(solicitudNovedadPersona);
         * logger.info("SolicitudNovedadPersona (M) " +
         * solicitudNovedadPersona.getIdSolicitudNovedadPersona());
         * }
         * logger.
         * debug("fin de método crearSolicitudNovedadPersona(crearSolicitudNovedadPersona solicitudNovedadPersona)"
         * );
         * return solicitudNovedadPersona;
         * } catch (Exception e) {
         * logger.error(
         * "Ocurrió un error inesperado en crearSolicitudNovedadPersona(crearSolicitudNovedadPersona solicitudNovedadPersona)"
         * );
         * throw new
         * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
         * }
         */

        CrearSolicitudNovedadPersonaRutine c = new CrearSolicitudNovedadPersonaRutine();
        return c.crearSolicitudNovedadPersona(solicitudNovedadPersona, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * actualizarSolicitudNovedadPersona(com.asopagos.entidades.ccf.novedades.
     * SolicitudNovedadPersona)
     */
    @Override
    public void actualizarSolicitudNovedadPersona(SolicitudNovedadPersona solicitudNovedadPersona) {
        ActualizarSolicitudNovedadPersonaRutine a = new ActualizarSolicitudNovedadPersonaRutine();
        a.actualizarSolicitudNovedadPersona(solicitudNovedadPersona, entityManager);

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ParametrizacionNovedadModeloDTO> consultarNovedades(ProcesoEnum procesoEnum) {
        try {
            logger.debug("Inicio del método consultarNovedades(Long procesoEnum)");
            List<ParametrizacionNovedadModeloDTO> novedadesModeloDTO = new ArrayList<ParametrizacionNovedadModeloDTO>();

            List<ParametrizacionNovedad> novedades = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES)
                    .setParameter("procesoEnum", procesoEnum).getResultList();

            for (ParametrizacionNovedad novedad : novedades) {
                ParametrizacionNovedadModeloDTO novedadModeloDTO = new ParametrizacionNovedadModeloDTO();
                novedadModeloDTO.convertToDTO(novedad);
                novedadesModeloDTO.add(novedadModeloDTO);
            }

            logger.debug("fin de método consultarNovedades(Long procesoEnum)");
            return novedadesModeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarNovedades(Long procesoEnum)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    @Override
    public List<RegistroPersonaInconsistenteDTO> buscarPersonasPendientesActulizar(
            TipoIdentificacionEnum tipoIdentificacion,
            Long numeroIdentificacion, Long fechaIngresoBandeja) {

        logger.debug(
                "Inicio del método buscarPersonasPendientesActulizar(TipoIdentificacionEnum tipoIdentificacion, Long numeroIdentificacion, Long fechaIngresoBandeja)");
        List<Object[]> datosPersonaInconsistente = null;
        try {

            if (tipoIdentificacion != null && numeroIdentificacion != null) {

                if (fechaIngresoBandeja != null) {
                    datosPersonaInconsistente = entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_TIPO_Y_NUMERO_IDENTIFICACION_FECHAINGRESO)
                            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString())
                            .setParameter("fechaIngreso", new Date(fechaIngresoBandeja))
                            .setParameter("estado", EstadoGestionEnum.PENDIENTE_GESITONAR.name()).getResultList();
                } else {
                    datosPersonaInconsistente = entityManager
                            .createNamedQuery(
                                    NamedQueriesConstants.CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_TIPO_Y_NUMERO_IDENTIFICACION)
                            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                            .setParameter("numeroIdentificacion", numeroIdentificacion.toString())
                            .setParameter("estado", EstadoGestionEnum.PENDIENTE_GESITONAR.name()).getResultList();
                }
            } else if (fechaIngresoBandeja != null) {
                datosPersonaInconsistente = entityManager
                        .createNamedQuery(
                                NamedQueriesConstants.CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_FECHAINGRESO)
                        .setParameter("fechaIngreso", new Date(fechaIngresoBandeja))
                        .setParameter("estado", EstadoGestionEnum.PENDIENTE_GESITONAR.name()).getResultList();
            } else {
                datosPersonaInconsistente = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE)
                        .setParameter("estado", EstadoGestionEnum.PENDIENTE_GESITONAR.name()).getResultList();
            }
            List<RegistroPersonaInconsistenteDTO> personaInconsistentes = null;
            if (datosPersonaInconsistente != null && !datosPersonaInconsistente.isEmpty()) {
                personaInconsistentes = new ArrayList<>();
                for (Object[] datosPersona : datosPersonaInconsistente) {
                    RegistroPersonaInconsistenteDTO registroPersonaInconsistenteDTO = new RegistroPersonaInconsistenteDTO();
                    registroPersonaInconsistenteDTO
                            .setIdRegistroPersonaInconsitente(((BigInteger) datosPersona[0]).longValue());
                    registroPersonaInconsistenteDTO
                            .setCanalContacto(CanalRecepcionEnum.valueOf((String) datosPersona[1]));
                    registroPersonaInconsistenteDTO
                            .setEstadoGestion(EstadoGestionEnum.valueOf((String) datosPersona[2]));
                    registroPersonaInconsistenteDTO.setFechaIngreso(((Date) datosPersona[3]).getTime());
                    registroPersonaInconsistenteDTO.setObservaciones((String) datosPersona[4]);
                    registroPersonaInconsistenteDTO
                            .setTipoInconsistencia(TipoInconsistenciaANIEnum.valueOf((String) datosPersona[5]));
                    if (datosPersona[6] != null) {
                        registroPersonaInconsistenteDTO.setIdCargueMultiple(((BigInteger) datosPersona[6]).longValue());
                        registroPersonaInconsistenteDTO.setNombreCargue((String) datosPersona[7]);
                    } else {
                        registroPersonaInconsistenteDTO.setNombreCargue("N/A");
                    }
                    PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                    personaModeloDTO.setIdPersona(((BigInteger) datosPersona[8]).longValue());
                    personaModeloDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) datosPersona[9]));
                    personaModeloDTO.setNumeroIdentificacion((String) datosPersona[10]);
                    registroPersonaInconsistenteDTO.setPersona(personaModeloDTO);
                    personaInconsistentes.add(registroPersonaInconsistenteDTO);
                }
            }
            logger.debug(
                    "fin de método buscarPersonasPendientesActulizar(TipoIdentificacionEnum tipoIdentificacion, Long numeroIdentificacion, Long fechaIngresoBandeja)");
            return personaInconsistentes;
        } catch (NoResultException nr) {
            logger.debug(
                    "fin de método buscarPersonasPendientesActulizar(TipoIdentificacionEnum tipoIdentificacion, Long numeroIdentificacion, Long fechaIngresoBandeja)");
            return null;
        } catch (Exception e) {
            logger.debug(
                    "buscarPersonasPendientesActulizar(TipoIdentificacionEnum tipoIdentificacion, Long numeroIdentificacion, Long fechaIngresoBandeja)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public void guardarRegistroPersonaInconsistencia(List<RegistroPersonaInconsistenteDTO> Listadto) {
        logger.debug("Inicio guardarRegistroPersonaInconsistencia(List<RegistroPersonaInconsistenteDTO>)");
        for (RegistroPersonaInconsistenteDTO dto : Listadto) {
            crearActualizarRegistroPersonaInconsistente(dto);
        }
        logger.debug("Fin guardarRegistroPersonaInconsistencia(List<RegistroPersonaInconsistenteDTO>)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * almacenarSolicitudNovedadPersonaMasivo(java.lang.Long,
     * com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO)
     */
    @Override
    public void almacenarSolicitudNovedadPersonaMasivo(Long idSolicitudNovedad,
                                                       DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO) {
        try {
            logger.info("Ingresa al mètodo almacenarSolicitudNovedadPersonaMasivo");
            /*
             * Se persiste en Batch las solicitudesNovedadAfiliado Configuración
             * actual: hibernate.jdbc.batch_size value = 500
             */
            if (datosNovedadAutomaticaDTO.getIdPersonaAfiliados() != null
                    && !datosNovedadAutomaticaDTO.getIdPersonaAfiliados().isEmpty()) {
                for (Long idPersona : datosNovedadAutomaticaDTO.getIdPersonaAfiliados()) {
                    SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
                    solicitudNovedadPersona.setIdPersona(idPersona);
                    solicitudNovedadPersona.setIdSolicitudNovedad(idSolicitudNovedad);
                    entityManager.persist(solicitudNovedadPersona);
                    logger.debug("IF 1 persona : " + idPersona + " solicitud : " + idSolicitudNovedad);
                }
            } else if (datosNovedadAutomaticaDTO.getListaBeneficiarios() != null
                    && !datosNovedadAutomaticaDTO.getListaBeneficiarios().isEmpty()) {
                // datosNovedadAutomaticaDTO.setListaBeneficiarios(datosNovedadAutomaticaDTO.getListaBeneficiarios().subList(0,
                // 15));
                for (BeneficiarioNovedadAutomaticaDTO beneficiarioNovedadAutomaticaDTO : datosNovedadAutomaticaDTO
                        .getListaBeneficiarios()) {
                    SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
                    solicitudNovedadPersona.setIdPersona(beneficiarioNovedadAutomaticaDTO.getIdPersonaAfiliado());
                    solicitudNovedadPersona.setIdBeneficiario(beneficiarioNovedadAutomaticaDTO.getIdBeneficiario());
                    solicitudNovedadPersona.setIdSolicitudNovedad(idSolicitudNovedad);
                    // idsBeneficiarioparte1=idsBeneficiario.subList(0, idsBeneficiario.size()/2);
                    // solicitudNovedadPersona=solicitudNovedadPersona.subList(0, 15);
                    entityManager.persist(solicitudNovedadPersona);
                    // logger.info("IF 2 persona : " +
                    // beneficiarioNovedadAutomaticaDTO.getIdPersonaAfiliado() + " solicitud : " +
                    // idSolicitudNovedad + " beneficiario : " +
                    // beneficiarioNovedadAutomaticaDTO.getIdBeneficiario());
                }
            }

            logger.debug(
                    "Finaliza el mètodo almacenarSolicitudNovedadPersonaMasivo( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
        } catch (Exception e) {
            logger.error("**Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * actualizarSolicitudesNovedadDesistimiento()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void actualizarSolicitudesNovedadDesistimiento() {
        try {
            logger.debug("Ingresa al mètodo actualizarSolicitudesNovedadDesistimiento()");

            Long diasReintegro = new Long(
                    (String) CacheManager
                            .getParametro(ParametrosSistemaConstants.TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES));

            // Long diasReintegro = new Long(
            // (String)
            // CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES))
            // / (3600 * 24 * 1000);
            Calendar fechaActual = Calendar.getInstance();
            /* Se restan los dias de reintegro a la fecha actual */
            fechaActual.add(Calendar.DATE, -diasReintegro.intValue());

            /*
             * Consulta las solicitudes de Novedad Diferentes a CERRADA que sean
             * Carga Multiple y se haya cumplido el plazo para Desistir.
             */
            List<SolicitudNovedad> solicitudesDesistir = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_DESISTIR)
                    .setParameter("cargaMultiple", Boolean.TRUE)
                    .setParameter("estadoSolicitud", EstadoSolicitudNovedadEnum.CERRADA)
                    .setParameter("fechaDesistirCargaMultiple", fechaActual.getTime()).getResultList();
            if (solicitudesDesistir != null && !solicitudesDesistir.isEmpty()) {
                /*
                 * Se persiste en Batch las solicitudesNovedadAfiliado
                 * Configuración actual: hibernate.jdbc.batch_size value = 500
                 */
                for (SolicitudNovedad solicitudDesistir : solicitudesDesistir) {
                    solicitudDesistir = entityManager.merge(solicitudDesistir);
                    /* Se cambia el estado de la solicitud Novedad a CERRADA */
                    solicitudDesistir.setEstadoSolicitud(EstadoSolicitudNovedadEnum.CERRADA);

                    /* Se cambia la Solicitud Global a DESISTIDA */
                    Solicitud solicitudGlobal = solicitudDesistir.getSolicitudGlobal();
                    solicitudGlobal = entityManager.merge(solicitudGlobal);
                    solicitudGlobal.setResultadoProceso(ResultadoProcesoEnum.DESISTIDA);

                    /* Se registra Intento de Registro de Novedad */
                    IntentoNovedad intentoNovedad = new IntentoNovedad();
                    intentoNovedad.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.DESISTIMIENTO_AUTOMATICO);
                    intentoNovedad.setFechaCreacion(new Date());
                    intentoNovedad.setIdSolicitud(solicitudGlobal.getIdSolicitud());
                    intentoNovedad.setTipoTransaccion(solicitudGlobal.getTipoTransaccion());
                    entityManager.persist(intentoNovedad);
                }
            }

            logger.debug("Finaliza el mètodo actualizarSolicitudesNovedadDesistimiento()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#crearNovedadFutura(com.
     * asopagos.dto.modelo.RegistroNovedadFuturaModeloDTO)
     */
    @Override
    public void crearNovedadFutura(RegistroNovedadFuturaModeloDTO novedadFuturaDTO) {
        try {
            logger.debug("Inicio de método crearNovedadFutura()");
            RegistroNovedadFutura novedadFutura = novedadFuturaDTO.convertToEntity();
            if (novedadFutura.getId() != null) {
                entityManager.merge(novedadFutura);
            } else {
                entityManager.persist(novedadFutura);
            }
            logger.debug("Fin de método crearNovedadFutura()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.novedades.service.NovedadesService#crearSolicitudNovedadPila(com
     * .asopagos.dto.modelo.SolicitudNovedadPilaModeloDTO)
     */
    @Override
    public void crearSolicitudNovedadPila(SolicitudNovedadPilaModeloDTO solicitudNovedadPila) {
        try {
            logger.debug("Inicio de método crearSolicitudNovedadPila()");
            SolicitudNovedadPila novedadPila = solicitudNovedadPila.convertToEntity();
            if (novedadPila.getIdSolicitudNovedadPila() != null) {
                entityManager.merge(novedadPila);
            } else {
                entityManager.persist(novedadPila);
            }
            logger.debug("Fin de método crearSolicitudNovedadPila()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.service.NovedadesService#
     * consultarRegistroPersonaInconsistente(com.asopagos.dto.modelo.
     * PersonaModeloDTO)
     */
    @Override
    public boolean consultarRegistroPersonaInconsistente(PersonaModeloDTO persona) {
        try {
            logger.debug("Inicio de método consultarRegistroPersonaInconsistente(PersonaModeloDTO)");
            Boolean hayRegistro = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HAY_REGISTRO_PERSONA_INCONSISTENTE, Boolean.class)
                    .setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
                    .setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();

            logger.debug("Fin de método consultarRegistroPersonaInconsistente(PersonaModeloDTO)");
            return hayRegistro;

        } catch (NoResultException nre) {
            logger.debug("Fin de método consultarRegistroPersonaInconsistente(PersonaModeloDTO)");
            return false;
        }
    }

    @Override
    public List<RegistroNovedadFuturaModeloDTO> consultarNovedadesFuturas() {
        String path = "consultarNovedadesFuturas():List<RegistroNovedadFuturaModeloDTO>";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            List<RegistroNovedadFuturaModeloDTO> result = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_FUTURA_SIN_PROCESAR,
                            RegistroNovedadFuturaModeloDTO.class)
                    .setParameter("fechaActual", new Date(), TemporalType.DATE)
                    .setParameter("registroProcesado", Boolean.FALSE)
                    .getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return result;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + path);
            return null;
        }
    }

    @Override
    public void actualizarRegistroNovedadFuturaMasivo(List<RegistroNovedadFuturaModeloDTO> listRegistros) {
        String path = "actualizarRegistroNovedadFuturaMasivo(List<RegistroNovedadFuturaModeloDTO>)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            for (RegistroNovedadFuturaModeloDTO registroNovedadFuturaModeloDTO : listRegistros) {
                crearNovedadFutura(registroNovedadFuturaModeloDTO);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + path);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpleador(FiltrosDatosNovedadDTO filtrosDatosNovedad,
                                                                        UriInfo uri,
                                                                        HttpServletResponse response) {
        logger.debug("Inicia servicio consultarNovedadesEmpleador(TipoIdentificacionEnum,String)");
        try {
            MultivaluedMap<String, String> parametros = new MultivaluedHashMap<>();
            if (filtrosDatosNovedad.getParams() != null) {
                for (Entry<String, List<String>> e : filtrosDatosNovedad.getParams().entrySet()) {
                    parametros.put(e.getKey(), e.getValue());
                }
            }

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, parametros, response);
            queryBuilder.addParam("tipoIdentificacion", filtrosDatosNovedad.getTipoIdentificacion().name());
            queryBuilder.addParam("numeroIdentificacion", filtrosDatosNovedad.getNumeroIdentificacion());
            queryBuilder.addOrderByDefaultParam("-fechaRadicacion");
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_EMPLEADOR, null);

            List<DatosNovedadEmpleadorDTO> listaNovedadesEmpleador = new ArrayList<>();
            List<Object[]> datos = query.getResultList();
            if (datos != null && !datos.isEmpty()) {
                for (Object[] datosNovedad : datos) {
                    DatosNovedadEmpleadorDTO datosNovedadEmpleadorDTO = new DatosNovedadEmpleadorDTO(
                            (String) datosNovedad[0],
                            (Date) datosNovedad[1], (String) datosNovedad[2],
                            datosNovedad[3] != null ? (String) datosNovedad[3] : null,
                            datosNovedad[4] != null ? (String) datosNovedad[4] : null,
                            datosNovedad[5] != null ? (Date) datosNovedad[5] : null,
                            datosNovedad[6] != null ? (Date) datosNovedad[6] : null,
                            (String) datosNovedad[7], (String) datosNovedad[8],
                            (BigInteger) datosNovedad[9]);
                    listaNovedadesEmpleador.add(datosNovedadEmpleadorDTO);
                }
            }

            DatosNovedadEmpleadorPaginadoDTO datosNovedadEmpleadorPaginadoDTO = new DatosNovedadEmpleadorPaginadoDTO();
            datosNovedadEmpleadorPaginadoDTO.setDatosNovedadEmpleador(listaNovedadesEmpleador);
            if (response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor()) != null) {
                datosNovedadEmpleadorPaginadoDTO
                        .setTotalRecords(
                                new Integer(response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor())));
            }
            logger.debug("Finaliza servicio consultarNovedadesEmpleador(TipoIdentificacionEnum,String)");
            return datosNovedadEmpleadorPaginadoDTO;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarNovedadesEmpleador(TipoIdentificacionEnum,String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DatosNovedadVista360DTO consultarNovedadesPersonaVista360(TipoIdentificacionEnum tipoIdentificacion,
                                                                     String numeroIdentificacion,
                                                                     Boolean esBeneficiario) {
        String path = "consultarNovedadesPersonaVista360(TipoIdentificacionEnum, String, Boolean):DatosNovedadVista360DTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            DatosNovedadVista360DTO datosNovedadVista360DTO = new DatosNovedadVista360DTO();
            datosNovedadVista360DTO.setTipoIdentificacion(tipoIdentificacion);
            datosNovedadVista360DTO.setNumeroIdentificacion(numeroIdentificacion);
            // Se consultan las novedades vigentes asociadas a Licencia o Incapacidad.
            List<Object[]> novedadesVigentes = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_VIGENTE_PERSONA)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("fechaActual", Calendar.getInstance(), TemporalType.DATE)
                    .getResultList();

            for (Object[] novedadVigente : novedadesVigentes) {
                String tipoNovedadStr = (String) novedadVigente[0];
                Date fechaInicio = (Date) novedadVigente[1];
                Date fechaFin = (Date) novedadVigente[2];
                TipoTransaccionEnum tipoNovedad = TipoTransaccionEnum.valueOf(tipoNovedadStr);
                if (TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB.equals(tipoNovedad)
                        || TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL
                        .equals(tipoNovedad)
                        || TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB
                        .equals(tipoNovedad)
                        || TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB
                        .equals(tipoNovedad)
                        || TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL
                        .equals(tipoNovedad)
                        || TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB
                        .equals(tipoNovedad)) {
                    datosNovedadVista360DTO.setIncapacidad(Boolean.TRUE);
                    datosNovedadVista360DTO.setTipoIncapacidad(tipoNovedad);
                    datosNovedadVista360DTO.setFechaInicioIncapacidad(fechaInicio.getTime());
                    datosNovedadVista360DTO.setFechaFinIncapacidad(fechaFin.getTime());
                } else if (TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB.equals(tipoNovedad)
                        || TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL.equals(tipoNovedad)
                        || TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB.equals(tipoNovedad)) {
                    datosNovedadVista360DTO.setLicenciaMaternidad(Boolean.TRUE);
                    datosNovedadVista360DTO.setFechaInicioLicencia(fechaInicio.getTime());
                    datosNovedadVista360DTO.setFechaFinLicencia(fechaFin.getTime());
                }
            }
            // Si es Beneficiario, se identifica si tiene condicion de Invalidez.
            if (esBeneficiario) {
                List<Object> condicionInvalidez = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_INVALIDEZ_PERSONA)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                        .setParameter("numeroIdentificacion", numeroIdentificacion)
                        .setParameter("condicionInvalidez", NumerosEnterosConstants.UNO).getResultList();
                if (condicionInvalidez != null && !condicionInvalidez.isEmpty()) {
                    Date fechaInicio = (Date) condicionInvalidez.get(0);
                    datosNovedadVista360DTO.setCondicionInvalidez(Boolean.TRUE);
                    datosNovedadVista360DTO.setFechaInicioInvalidez(fechaInicio.getTime());
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return datosNovedadVista360DTO;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersona(UriInfo uriInfo,
                                                                                       HttpServletResponse response,
                                                                                       TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String path = "consultarNovedadesRegistradasPersona(UriInfo, HttpServletResponse, TipoIdentificacionEnum, String):List<DatosNovedadRegistradaPersonaDTO>";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);

        // Se consultan las novedades futuras sin registrar
        List<Object[]> novedadesFutural = entityManagerStaging
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_FUTURAS_PERSONA_INT_NG)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();

        List<DatosNovedadRegistradaPersonaDTO> datosNovedadesFuturas = new ArrayList<>();

        for (Object[] novedadObj : novedadesFutural) {
            DatosNovedadRegistradaPersonaDTO datosNovedadFut = new DatosNovedadRegistradaPersonaDTO();
            datosNovedadFut.setNombreNovedad(TipoTransaccionEnum.valueOf((String) novedadObj[0]));
            datosNovedadFut.setFechaRegistroNovedad(null);
            datosNovedadFut.setEstadoNovedad((String) novedadObj[1]);
            datosNovedadFut.setFechaInicioVigencia(novedadObj[2] != null ? (((Date) novedadObj[2]).getTime()) : null);
            datosNovedadFut.setFechaFinVigencia(novedadObj[3] != null ? (((Date) novedadObj[3]).getTime()) : null);
            datosNovedadFut.setNumeroOperacion(null);
            datosNovedadFut.setCanal((String) novedadObj[4]);
            datosNovedadFut.setEmpleador((String) novedadObj[5]);
            datosNovedadFut.setEstadoPersonaAntes(null);
            datosNovedadFut.setEstadoPersonaDespues(null);
            datosNovedadFut.setIdSolicitudGlobal(null);
            datosNovedadFut.setNivelNovedad((String) novedadObj[6]);
            datosNovedadFut.setIdEmpleador(null);
            datosNovedadFut.setIdPersona(null);
            datosNovedadFut.setIdEmpresa(null);

            datosNovedadFut.setTipoIdentificacion(
                    novedadObj[5] == null ? null : TipoIdentificacionEnum.valueOf((String) novedadObj[7]));
            datosNovedadFut.setNumeroIdentificacion(novedadObj[5] == null ? null : ((String) novedadObj[8]));

            datosNovedadesFuturas.add(datosNovedadFut);
        }

        List<Object[]> novedadesRegistradas = null;
        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(
                            NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA_INT_NG);

            query.setParameter("tipoIdentificacion", tipoIdentificacion.name());
            query.setParameter("numeroIdentificacion", numeroIdentificacion);
            // query.execute();

            // result = (Date) query.getOutputParameterValue("dFechaHabil");
            novedadesRegistradas = query.getResultList();
        } catch (Exception e) {
            logger.info(" :: Hubo un error en el SP: " + e);
        }
        /*
         * QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo,
         * response);
         * //Se consultan las novedades registradas a la persona.
         * queryBuilder.addParam("tipoIdentificacion", tipoIdentificacion.name());
         * queryBuilder.addParam("numeroIdentificacion", numeroIdentificacion);
         * queryBuilder.addOrderByDefaultParam("-fechaRegistroNovedad");
         * Query query = queryBuilder.createQuery(NamedQueriesConstants.
         * CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA_INT_NG, null);
         * List<Object[]> novedadesRegistradas = query.getResultList();
         */
        List<DatosNovedadRegistradaPersonaDTO> datosNovedadRegistrada = new ArrayList<>();

        if (datosNovedadesFuturas.size() > 0) {
            datosNovedadRegistrada = datosNovedadesFuturas;
        }

        for (Object[] novedadObj : novedadesRegistradas) {
            DatosNovedadRegistradaPersonaDTO datosNovedadReg = new DatosNovedadRegistradaPersonaDTO();
            datosNovedadReg.setNombreNovedad(TipoTransaccionEnum.valueOf((String) novedadObj[0]));
            datosNovedadReg.setFechaRegistroNovedad(novedadObj[1] != null ? (((Date) novedadObj[1]).getTime()) : null);
            datosNovedadReg.setEstadoNovedad((String) novedadObj[2]);
            datosNovedadReg.setFechaInicioVigencia(novedadObj[3] != null ? (((Date) novedadObj[3]).getTime()) : null);
            datosNovedadReg.setFechaFinVigencia(novedadObj[4] != null ? (((Date) novedadObj[4]).getTime()) : null);
            datosNovedadReg.setNumeroOperacion((String) novedadObj[5]);
            datosNovedadReg.setCanal((String) novedadObj[6]);
            datosNovedadReg.setEmpleador((String) novedadObj[7]);
            datosNovedadReg.setEstadoPersonaAntes(
                    novedadObj[8] == null ? null : EstadoAfiliadoEnum.valueOf((String) novedadObj[8]));
            datosNovedadReg.setEstadoPersonaDespues(
                    novedadObj[9] == null ? null : EstadoAfiliadoEnum.valueOf((String) novedadObj[9]));
            datosNovedadReg.setIdSolicitudGlobal(((BigInteger) novedadObj[10]).longValue());
            datosNovedadReg.setNivelNovedad((String) novedadObj[11]);
            datosNovedadReg.setIdEmpleador(novedadObj[12] == null ? null : ((BigInteger) novedadObj[12]).longValue());
            datosNovedadReg.setIdPersona(novedadObj[13] == null ? null : ((BigInteger) novedadObj[13]).longValue());
            datosNovedadReg.setIdEmpresa(novedadObj[14] == null ? null : ((BigInteger) novedadObj[14]).longValue());
            datosNovedadRegistrada.add(datosNovedadReg);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return datosNovedadRegistrada;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersonaBeneficiario(UriInfo uriInfo,
                                                                                                   HttpServletResponse response, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String path = "consultarNovedadesRegistradasPersona(UriInfo, HttpServletResponse, TipoIdentificacionEnum, String):List<DatosNovedadRegistradaPersonaDTO>";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // Se ocultan de manera temporal las solicitudes que no llegaron a aplicarse
            // Y que se desprenden de novedades de cascada o procesos automaticos
            // ya que en escenarios ideales siempre deberían tener estado "Aplicada"
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
            // Se consultan las novedades registradas a la persona.
            queryBuilder.addParam("tipoIdentificacion", tipoIdentificacion.name());
            queryBuilder.addParam("numeroIdentificacion", numeroIdentificacion);
            queryBuilder.addOrderByDefaultParam("-fechaRegistroNovedad");
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_REGISTRADAS_BENEFICIARIO,
                    null);
            List<Object[]> novedadesRegistradas = query.getResultList();
            List<DatosNovedadRegistradaPersonaDTO> datosNovedadesBeneficiario = new ArrayList<>();

            for (Object[] novedadObj : novedadesRegistradas) {
                DatosNovedadRegistradaPersonaDTO datosNovedadReg = new DatosNovedadRegistradaPersonaDTO();
                datosNovedadReg.setRelacionBeneficiario((String) novedadObj[0]);
                datosNovedadReg.setClasificacionBeneficiario((String) novedadObj[1]);
                datosNovedadReg.setNombreNovedad(TipoTransaccionEnum.valueOf((String) novedadObj[2]));
                datosNovedadReg
                        .setFechaRegistroNovedad(novedadObj[3] != null ? (((Date) novedadObj[3]).getTime()) : null);
                datosNovedadReg.setAfiliado((String) novedadObj[4]);
                datosNovedadReg
                        .setFechaInicioVigencia(novedadObj[5] != null ? (((Date) novedadObj[5]).getTime()) : null);
                datosNovedadReg.setFechaFinVigencia(novedadObj[6] != null ? (((Date) novedadObj[6]).getTime()) : null);
                datosNovedadReg.setNumeroOperacion((String) novedadObj[7]);
                datosNovedadReg.setEstadoNovedad((String) novedadObj[8]);
                datosNovedadReg.setCanal((String) novedadObj[9]);
                datosNovedadReg.setIdSolicitudGlobal(((BigInteger) novedadObj[10]).longValue());
                datosNovedadReg.setNumeroIdentificacion((String) novedadObj[11]);
                datosNovedadReg.setTipoIdentificacion(
                        novedadObj[12] != null ? TipoIdentificacionEnum.valueOf((String) novedadObj[12]) : null);
                datosNovedadReg
                        .setNombresApellidosBeneficiario(novedadObj[13] != null ? (String) novedadObj[13] : null);
                datosNovedadReg.setTipoIdentificacionBeneficiario(
                        novedadObj[14] != null ? TipoIdentificacionEnum.valueOf((String) novedadObj[14]) : null);
                datosNovedadReg
                        .setNumeroIdentificacionBeneficiario(novedadObj[15] != null ? (String) novedadObj[15] : null);
                datosNovedadesBeneficiario.add(datosNovedadReg);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return datosNovedadesBeneficiario;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public Integer obtenerConsecutivoNovedad(String numeroRadicado) {
        String path = "obtenerConsecutivoNovedad(String) " + numeroRadicado;
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);

        Integer consecutivo = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSECUTIVO_CASCADA_RADICADO)
                .setParameter("numeroRadicado", numeroRadicado)
                .getSingleResult();

        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return consecutivo;
    }

    /**
     * Realiza el registro de la incosistencia de la persona
     *
     * @param dto Información de persona inconsistente
     */
    private void crearActualizarRegistroPersonaInconsistente(RegistroPersonaInconsistenteDTO dto) {
        String path = "crearActualizarRegistroPersonaInconsistente(RegistroPersonaInconsistenteDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            RegistroPersonaInconsistente personaInconsistente = null;
            // Se verifica si se actualiza el registro
            if (dto.getIdRegistroPersonaInconsitente() != null) {
                personaInconsistente = (RegistroPersonaInconsistente) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_ID)
                        .setParameter("idRegistroPersonaInconsitente", dto.getIdRegistroPersonaInconsitente())
                        .getSingleResult();

                if (dto.getObservaciones() != null) {
                    personaInconsistente.setObservaciones(dto.getObservaciones());
                }
                if (dto.getEstadoGestion() != null) {
                    personaInconsistente.setEstadoGestion(dto.getEstadoGestion());
                }
                if (dto.getCanalContacto() != null) {
                    personaInconsistente.setCanalContacto(dto.getCanalContacto());
                }
                if (dto.getFechaIngreso() != null) {
                    personaInconsistente.setFechaIngreso(new Date(dto.getFechaIngreso()));
                }
                if (dto.getTipoInconsistencia() != null) {
                    personaInconsistente.setTipoInconsistencia(dto.getTipoInconsistencia());
                }
                entityManager.merge(personaInconsistente);
            } else {
                Long idPersona = null;
                // Si no se envio el identificador de persona, consultar información persona con
                // los datos de identificacion
                if (dto.getPersona().getIdPersona() != null) {
                    idPersona = dto.getPersona().getIdPersona();
                } else if (dto.getPersona().getNumeroIdentificacion() != null
                        && dto.getPersona().getNumeroIdentificacion() != null) {
                    Persona per = (Persona) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_POR_TIPO_NUMEROIDENTIFICACION)
                            .setParameter("numeroIdentificacion", dto.getPersona().getNumeroIdentificacion())
                            .setParameter("tipoIdentificacion", dto.getPersona().getTipoIdentificacion())
                            .getSingleResult();

                    idPersona = per.getIdPersona();
                }

                if (idPersona == null) {
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
                }
                personaInconsistente = new RegistroPersonaInconsistente();
                personaInconsistente.setIdPersona(idPersona);
                if (dto.getIdCargueMultiple() != null) {
                    CargueMultipleSupervivencia cargueMultiple = (CargueMultipleSupervivencia) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_MULTIPLE_SUPERVIVENCIA_POR_ID)
                            .setParameter("idCargueSupervivencia", dto.getIdCargueMultiple()).getSingleResult();
                    personaInconsistente
                            .setidCargueMultipleSupervivencia(cargueMultiple.getIdCargueMultipleSupervivencia());
                }
                if (dto.getObservaciones() != null) {
                    personaInconsistente.setObservaciones(dto.getObservaciones());
                }
                if (dto.getEstadoGestion() != null) {
                    personaInconsistente.setEstadoGestion(dto.getEstadoGestion());
                }
                if (dto.getCanalContacto() != null) {
                    personaInconsistente.setCanalContacto(dto.getCanalContacto());
                }
                if (dto.getFechaIngreso() != null) {
                    personaInconsistente.setFechaIngreso(new Date(dto.getFechaIngreso()));
                }
                if (dto.getTipoInconsistencia() != null) {
                    personaInconsistente.setTipoInconsistencia(dto.getTipoInconsistencia());
                }
                entityManager.persist(personaInconsistente);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void guardarExcepcionNovedad(DatosExcepcionNovedadDTO datosExcepcionNovedadDTO,
                                        String excepcion, UserDTO userDTO) throws Exception {

        String path = "guardarExcepcionNovedad(DatosExcepcionNovedadDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        Date fechaRegistro = new Date();
        ObjectMapper mapper = new ObjectMapper();
        String jsonSolicitudNovedadDTO = "";
        String jsonSolicitudNovedad = "";
        String jsonUserDTO = "";

        try {

            jsonSolicitudNovedadDTO = mapper
                    .writeValueAsString(datosExcepcionNovedadDTO.getSolicitudNovedadModeloDTO());
            jsonSolicitudNovedad = mapper.writeValueAsString(datosExcepcionNovedadDTO.getSolicitudNovedadDTO());
            jsonUserDTO = mapper.writeValueAsString(userDTO);

            entityManager.createNamedQuery(NamedQueriesConstants.GUARDAR_EXCEPCION_NOVEDAD)
                    .setParameter("fechaRegistro", fechaRegistro)
                    .setParameter("jsonSolicitudNovedadDTO", jsonSolicitudNovedadDTO)
                    .setParameter("jsonSolicitudNovedad", jsonSolicitudNovedad)
                    .setParameter("jsonUserDTO", jsonUserDTO)
                    .setParameter("excepcion", excepcion)
                    .executeUpdate();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(ConstantesComunes.FIN_LOGGER + path, e);
            throw new Exception(e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        /*
         * GuardarExcepcionNovedadRutine g = new GuardarExcepcionNovedadRutine();
         * g.guardarExcepcionNovedad(datosExcepcionNovedadDTO, excepcion, userDTO,
         * entityManager);
         */
    }

    @Override
    public List<DatosAfiliadoRetiroDTO> consultarAfiliadosInactivosBeneficiariosActivos() {
        String path = "consultarListaAfiliadosInactivosBenefActivos(DatosExcepcionNovedadDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);

        List<DatosAfiliadoRetiroDTO> dataAfiliados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATA_AFILIADOS_INACTIVOS_BENEFICIARIOS_ACTIVOS,
                        DatosAfiliadoRetiroDTO.class)
                .getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return dataAfiliados;

    }

    @Override
    @SuppressWarnings("unchecked")
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpVista360(FiltrosDatosNovedadDTO filtrosDatosNovedad,
                                                                          UriInfo uri,
                                                                          HttpServletResponse response) {
        logger.debug("Inicia servicio consultarNovedadesEmpVista360(TipoIdentificacionEnum,String)");
        try {
            MultivaluedMap<String, String> parametros = new MultivaluedHashMap<>();
            if (filtrosDatosNovedad.getParams() != null) {
                for (Entry<String, List<String>> e : filtrosDatosNovedad.getParams().entrySet()) {
                    parametros.put(e.getKey(), e.getValue());
                }
            }

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, parametros, response);
            queryBuilder.addParam("tipoIdentificacion", filtrosDatosNovedad.getTipoIdentificacion().name());
            queryBuilder.addParam("numeroIdentificacion", filtrosDatosNovedad.getNumeroIdentificacion());
            queryBuilder.addOrderByDefaultParam("-fechaRadicacion");
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_EMPLEADOR_VISTA_360, null);

            List<DatosNovedadEmpleadorDTO> listaNovedadesEmpleador = new ArrayList<>();
            List<Object[]> datos = query.getResultList();
            if (datos != null && !datos.isEmpty()) {
                for (Object[] datosNovedad : datos) {
                    DatosNovedadEmpleadorDTO datosNovedadEmpleadorDTO = new DatosNovedadEmpleadorDTO(
                            (String) datosNovedad[0],
                            (Date) datosNovedad[1], (String) datosNovedad[2],
                            datosNovedad[3] != null ? (String) datosNovedad[3] : null,
                            datosNovedad[4] != null ? (String) datosNovedad[4] : null,
                            datosNovedad[5] != null ? (Date) datosNovedad[5] : null,
                            datosNovedad[6] != null ? (Date) datosNovedad[6] : null,
                            (String) datosNovedad[7], (String) datosNovedad[8],
                            (BigInteger) datosNovedad[9]);
                    listaNovedadesEmpleador.add(datosNovedadEmpleadorDTO);
                }
            }

            DatosNovedadEmpleadorPaginadoDTO datosNovedadEmpleadorPaginadoDTO = new DatosNovedadEmpleadorPaginadoDTO();
            datosNovedadEmpleadorPaginadoDTO.setDatosNovedadEmpleador(listaNovedadesEmpleador);
            if (response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor()) != null) {
                datosNovedadEmpleadorPaginadoDTO
                        .setTotalRecords(
                                new Integer(response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor())));
            }
            logger.debug("Finaliza servicio consultarNovedadesEmpVista360(TipoIdentificacionEnum,String)");
            return datosNovedadEmpleadorPaginadoDTO;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarNovedadesEmpVista360(TipoIdentificacionEnum,String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public List<SolicitudNovedadGeneralDTO> obtenerNovedadesSinInstanciaProceso() {
        logger.debug("Inicia obtenerNovedadesSinInstanciaProceso()");

        List<EstadoSolicitudNovedadEnum> estados = new ArrayList<EstadoSolicitudNovedadEnum>();
        List<CanalRecepcionEnum> canales = new ArrayList<CanalRecepcionEnum>();
        String fechaIncialStr = "01/01/2020";
        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaIncialStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        estados.add(EstadoSolicitudNovedadEnum.RADICADA);
        estados.add(EstadoSolicitudNovedadEnum.PENDIENTE_ENVIO_AL_BACK);

        canales.add(CanalRecepcionEnum.PRESENCIAL);

        List<SolicitudNovedadGeneralDTO> solicitudes = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_NOVEDADES_SIN_INSTANCIA_PROCESO)
                .setParameter("estados", estados)
                .setParameter("canales", canales)
                .setParameter("fechaInicial", fecha)
                .getResultList();

        logger.debug("Finaliza obtenerNovedadesSinInstanciaProceso()");
        return solicitudes;
    }

    @Override
    public Long TransaccionNovedadPilaCompleta(Long tempId) {
        BigInteger tnpId;
        try {
            tnpId = (BigInteger) entityManager
                    .createNamedQuery(NamedQueriesConstants.TRANACCION_NOVEDAD_PILA_COMPLETA)
                    .setParameter("tempId", tempId).getSingleResult();

            return tnpId.longValue();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void GuardarExcepcionNovedadPila(Long tempNovedadId, String excepcion) {
        entityManager.createNamedQuery(NamedQueriesConstants.GUARDAR_EXCEPCION_NOVEDAD_PILA)
                .setParameter("tempNovedadId", tempNovedadId)
                .setParameter("excepcion", excepcion)
                .executeUpdate();

    }

    @Override
    public void InsertarRegistroPersonaInconsistente(RegistroPersonaInconsistenteDTO inconsistenteDTO) {
        logger.info("inicia inconsistenteDTO " + inconsistenteDTO.toString());

        entityManager.createNamedQuery(NamedQueriesConstants.INSERTAR_REGISTRO_PERSONA_INCOSISTENTE)
                .setParameter("idPersona", inconsistenteDTO.getPersona().getIdPersona())
                .setParameter("canalContacto", inconsistenteDTO.getCanalContacto().name())
                .setParameter("fechaIngreso", new Date(inconsistenteDTO.getFechaIngreso()))
                .setParameter("estadoGestion", inconsistenteDTO.getEstadoGestion().name())
                .setParameter("observaciones", inconsistenteDTO.getObservaciones())
                .setParameter("tipoInconsistencia", inconsistenteDTO.getTipoInconsistencia().name())
                .executeUpdate();
        logger.info("Finaliza InsertarRegistroPersonaInconsistente()");

    }

    @Override
    public Long ConsultarTipoNumeroIdentificacion(String numeroIdentificacion) {
        logger.info("Inicia ConsultarTipoNumeroIdentificacion " + numeroIdentificacion);

        Long respuesta;
        Persona persona = null;
        try {
            persona = (Persona) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_POR_NUMEROIDENTIFICACION)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
        } catch (NoResultException e) {
            respuesta = Long.valueOf(0);
            logger.info("Resultado Nulo" + e);
        } catch (NonUniqueResultException e) {
            respuesta = Long.valueOf(1);
            logger.info("Se encontro mas de 1 resultado con este numero de identificacion");
        }
        if (persona == null) {
            logger.info("valida Persona no encontrada" + numeroIdentificacion);
            respuesta = Long.valueOf(0);
        } else {
            respuesta = persona.getIdPersona();
        }
        logger.info("Respuesta" + respuesta);
        return respuesta;
    }

    @Override
    public Boolean consultarTipoNumeroIdentificacionYTipo(String numeroIdentificacion,
                                                          TipoIdentificacionEnum tipoIdentificacion) {
        logger.info("Inicia ConsultarTipoNumeroIdentificacion " + numeroIdentificacion);
        Boolean respuesta;
        Persona persona = null;
        try {
            persona = (Persona) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_POR_TIPO_NUMEROIDENTIFICACION)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("Resultado Nulo" + e);
            respuesta = Boolean.FALSE;
        }
        if (persona == null) {
            logger.info("valida Persona no encontrada" + numeroIdentificacion);
            respuesta = Boolean.FALSE;
        } else {
            respuesta = Boolean.TRUE;
        }
        logger.info("Respuesta" + respuesta);
        return respuesta;
    }

    @Override
    public Boolean consultarRetroactividadNovedad(Long idRegistroDetallado) {
        logger.info("Inicia ConsultaRetroactividadNovedad " + idRegistroDetallado);
        Boolean result = false;
        Object resultado = new Object();
        try {
            resultado = (Object) entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RETROACTIVIDAD_NOVEDAD)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();
        } catch (NoResultException e) {
            return Boolean.FALSE;
        }
        if (idRegistroDetallado == null) {
            logger.info("RegistroDetallado no encontrada" + idRegistroDetallado);
        }
        if (resultado.toString().equals("1")) {
            result = true;
        }
        logger.info("resultado" + resultado);
        return result;

    }

    @Override
    public RespuestaValidacionArchivoDTO validacionesRegistroArchConfirmAbonosBancario(String tipoIdenAdminSubsidio,
                                                                                       String numeroIdenAdminSubsidio, String casId, String tipoCuenta, String numeroCuenta,
                                                                                       String valorTransferencia, String resultadoAbono, String motivoRechazoAbono, String fechaConfirmacionAbono,
                                                                                       String idValidacion) {

        logger.info("Inicia validacionesRegistroArchConfirmAbonosBancario");

        /*
         * logger.info(" parametro tipoIdenAdminSubsidio : " + tipoIdenAdminSubsidio);
         * logger.info(" : parametro numeroIdenAdminSubsidio : " +
         * numeroIdenAdminSubsidio + " :  " );
         * logger.info(" : parametro casId : " + casId + " :  " );
         * logger.info(" : parametro tipoCuenta : " + tipoCuenta + " :  " );
         * logger.info(" : parametro numeroCuenta : " + numeroCuenta + " :  " );
         * logger.info(" : parametro valorTransferencia : " + valorTransferencia +
         * " :  " );
         * logger.info(" : parametro resultadoAbono : " + resultadoAbono + " :  " );
         * logger.info(" : parametro motivoRechazoAbono : " + motivoRechazoAbono +
         * " :  " );
         * logger.info(" : parametro fechaConfirmacionAbono : " + fechaConfirmacionAbono
         * + " :  " );
         * logger.info(" : parametro idValidacion : " + idValidacion + " :  " );
         *
         */
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();
        String tipoIdentificacionFinal = "";
        TipoIdentificacionEnum tipoIdentificacionAdminEnum = null;

        if (tipoIdentificacionFinal.equals("") && tipoIdenAdminSubsidio.equals("")) {
            tipoIdentificacionFinal = obtenerTitularCuentaAdministradorSubsidioByCasId(casId)[0].toString();
            // Campo no necesario:
            // tipoIdenAdminSubsidio = "CC";
        } else {
            tipoIdentificacionAdminEnum = GetValueUtil.getTipoIdentificacionByPila(tipoIdenAdminSubsidio);
            if (tipoIdentificacionAdminEnum == null) {
                tipoIdentificacionFinal = tipoIdenAdminSubsidio;
            } else {
                tipoIdentificacionFinal = tipoIdentificacionAdminEnum.name();
            }
        }

        // TipoIdentificacionEnum tipoIdentificacionAdmin = TipoIdentificacionEnum
        // .obtenerTiposIdentificacionPILAEnum(tipoIdenAdminSubsidio);

        // logger.info("1574: tipoIdentificacionAdmin " + tipoIdentificacionFinal);

        if (tipoCuenta != null && !(tipoCuenta.equals(""))) {
            switch (tipoCuenta) {
                case "1":
                    tipoCuenta = "AHORROS";
                    break;
                case "2":
                    tipoCuenta = "CORRIENTE";
                    break;
                case "3":
                    tipoCuenta = "DAVIPLATA";
                    break;
                default:
                    break;
            }
        }

        // logger.info("Inicia tipoCuenta " +tipoCuenta);
        // Val 3: Se valida el campo No 1 Tipo Iden - No 2 Num iden del admin sub
        // corresponda a una persona que es o fue administrador de subsidio en Genesys
        if (idValidacion.equals("3")) {
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_3_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .getSingleResult();

            logger.info(" RESUL VAL: " + validacion);

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 3" + tipoIdentificacionFinal + " " + numeroIdenAdminSubsidio);
                    solNovedadDTO.setMensaje("no cumple con la val 3");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 3");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("4")) {
            // Val 4: Se valida que el campo No 3 - casId corresponda a una persona que es o
            // fue administrador de subsidio en Genesys.
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_4_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            logger.info(" RESUL VAL: 4" + validacion);

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 4 " + casId);
                    solNovedadDTO.setMensaje("no cumple con la val 4");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 4");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("5")) {
            // Val 5: Se valida que el campo No 3 - casId este relacionado a un “Medio de
            // pago” igual a “TRANSFERENCIA” con estado de la transacción igual a “ENVIADO”
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_5_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 5 " + casId);
                    solNovedadDTO.setMensaje("no cumple con la val 5");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 5");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("6")) {
            // Val 6: Se valida que el campo No 1 tipo Iden - No 2 Num iden este asociado al
            // administrador de subsidio
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_6_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 6 " + tipoIdentificacionFinal + " " + numeroIdenAdminSubsidio);
                    solNovedadDTO.setMensaje("no cumple con la val 6");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 6");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("7")) {
            // Val 7 : Se valida que el campo No 1 Tipo iden - No 2 Num iden sea el mismo
            // que el del administrador de subsidio
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_7_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 7 " + numeroIdenAdminSubsidio);
                    solNovedadDTO.setMensaje("no cumple con la val 7");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 7");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("8")) {
            // Val 8.1: Se valida que el campo No tipo cuenta este asociado al titular de la
            // cuenta
            if (tipoCuenta != null && !(tipoCuenta.equals(""))) {
                Integer validacion = (Integer) entityManager
                        .createNamedQuery(NamedQueriesConstants.VALIDACION_8_REGISTRO_CONF_ABONO)
                        .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                        .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                        .setParameter("casId", casId)
                        .setParameter("tipoCuenta", tipoCuenta)
                        .getSingleResult();

                if (validacion != null) {
                    if (validacion == 0) {
                        logger.info("No cumple con la val 8 " + tipoCuenta);
                        solNovedadDTO.setMensaje("no cumple con la val 8");
                        solNovedadDTO.setStatus("KO");
                    } else {
                        solNovedadDTO.setMensaje("cumple con la val 8");
                        solNovedadDTO.setStatus("OK");
                    }
                }
            } else {
                logger.info("TIPO DE CUENTA NO VALIDADA.");
                solNovedadDTO.setMensaje("cumple con la val 8");
                solNovedadDTO.setStatus("OK");
            }
        } else if (idValidacion.equals("9")) {
            // Val 9: Se valida que el campo No Numero cuenta exista en Genesys y este
            // asociado al titular de la cuenta
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_9_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .setParameter("numeroCuenta", numeroCuenta)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 9 " + numeroCuenta);
                    solNovedadDTO.setMensaje("no cumple con la val 9");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 9");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else if (idValidacion.equals("10")) {
            // Val 10: Se valida que el campo No Valor transferencia sea igual al “valor
            // real de la transacción” del abono que existe en Genesys
            Integer validacion = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDACION_10_REGISTRO_CONF_ABONO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionFinal)
                    .setParameter("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                    .setParameter("casId", casId)
                    .setParameter("valorTransferencia", valorTransferencia)
                    .getSingleResult();

            if (validacion != null) {
                if (validacion == 0) {
                    logger.info("No cumple con la val 10 " + valorTransferencia);
                    solNovedadDTO.setMensaje("no cumple con la val 10");
                    solNovedadDTO.setStatus("KO");
                } else {
                    solNovedadDTO.setMensaje("cumple con la val 10");
                    solNovedadDTO.setStatus("OK");
                }
            }
        } else {
            solNovedadDTO.setMensaje("Envie la validacion que se quiere realizar (del 3 al 10)");
            solNovedadDTO.setStatus("KO");
        }
        logger.info("finaliza validacionesRegistroArchConfirmAbonosBancario");
        return solNovedadDTO;
    }

    @Override
    public void ejecutarDesafiliacionTrabajadoresEmpledorMasivo(String numerRadicacionEmpresa, Long idEmpledor) {
        logger.info(
                "**__**INGRESA A EJECUTAR AL SP PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS CON numerRadicacionEmpresa: "
                        + numerRadicacionEmpresa + " idEmpledor: " + idEmpledor);
        try {

            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(
                            NamedQueriesConstants.PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS);
            query.setParameter("numerRadicacionEmpresa", numerRadicacionEmpresa);
            query.setParameter("idEmpledor", BigInteger.valueOf(idEmpledor));
            query.execute();
        } catch (Exception e) {
            logger.error("ERROR PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public Object[] obtenerTitularCuentaAdministradorSubsidioByCasId(String casId) {
        logger.info("**__**INGRESA A obtenerTitularCuentaAdministradorSubsidioByCasIdByCasId: " + casId);
        try {

            Object[] cuentaAdministrador = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TITULAR_CUENTA_ADMINISTTRADOR_SUBSIDIO_CASID)
                    .setParameter("casId", casId)
                    .getSingleResult();
            return cuentaAdministrador;

        } catch (Exception e) {
            logger.error("ERROR obtenerTipoIdentificacionByCasId: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.novedades.service.NovedadesService#consultarJuzgados
     *
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<OficinaJuzgadoDTO> consultarJuzgados() {
        String firmaServicio = "NovedadesBusiness.consultarJuzgados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<OficinaJuzgadoDTO> res = new ArrayList<>();
        List<Object[]> outDtos = (List<Object[]>) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TODOS_LOS_JUZGADOS)
                .getResultList();
        for (Object[] v : outDtos) {
            OficinaJuzgadoDTO juzgado = new OficinaJuzgadoDTO();
            juzgado.setCtaId(v[0] != null && !v[0].toString().equals("") ? Long.parseLong(v[0].toString()) : null);
            juzgado.setNroCtaJudicial(v[1] != null && !v[1].toString().equals("") ? v[1].toString() : null);
            juzgado.setCodJuzgado(v[2] != null && !v[2].toString().equals("") ? v[2].toString() : null);
            juzgado.setNombreJuzgado(v[3] != null && !v[3].toString().equals("") ? v[3].toString() : null);
            res.add(juzgado);
        }
        res = res.isEmpty() ? null : res;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return res;
    }

    @Transactional
    @Override
    public void actualizarPersonaDetalleFallecido(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion, PersonaDetalleModeloDTO personaDetalleModeloDTO) {
        logger.info("Inicia actulalizarPersonaDetalle");
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.UPDATE_PERSONA_DETALLE_FALLECIDO)
                    .setParameter("fallecido",personaDetalleModeloDTO.getFallecido())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .executeUpdate();

            logger.info("Finaliza actulalizarPersonaDetalle");
        } catch (Exception e) {
            logger.error("ERROR actulalizarPersonaDetalle ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public void empleadoresProcesar(List<Long> idEmpleadores, String numeroRadicado,EstadoDesafiliacionMasivaEnum estado, int intentosDiarios){
        
        for (Long idEmpleador : idEmpleadores){
            try{
                EmpleadoresDesafiliacionMasiva empleador = new EmpleadoresDesafiliacionMasiva(idEmpleador.toString(),numeroRadicado, estado,intentosDiarios);
                entityManager.persist(empleador);
            }catch(Exception e ){
                // InsercionAnulacionPlanillas insercionFallo = new InsercionAnulacionPlanillas("insercion de empleadores a desafiliar", "id empleador: "+ idEmpleador + " numero de radicado: "+ numeroRadicacion, e);
                // insercionFallo.execute();
            }
        }

    }
    
    @Override
    public List<Object[]> obtenerEmpleadoresProcesar(){
        try{
            List<Object[]> empleadores = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_EMPLEADORES_POR_PROCESAR)
                .getResultList();
            return empleadores;
        }catch(Exception e){
            // InsercionAnulacionPlanillas insercionFallo = new InsercionAnulacionPlanillas("obtenerEmpleadoresProcesar", "", e);
            // insercionFallo.execute();
            List<Object[]> empleadores = new ArrayList<>();
            return empleadores;
        }
    }

}
