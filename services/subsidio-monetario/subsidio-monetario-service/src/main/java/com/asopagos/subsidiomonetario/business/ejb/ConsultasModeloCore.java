package com.asopagos.subsidiomonetario.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.RolAfiliadoDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.NivelLiquidacionEspecificaDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoHistoricoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionAdminSubsidioDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionGrupoFamiliarDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.AplicacionValidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ArchivoLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoBeneficiarioCuotaMonetaria;
import com.asopagos.entidades.subsidiomonetario.liquidacion.CargueBloqueoCuotaMonetaria;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.CuentaCCF;
import com.asopagos.entidades.subsidiomonetario.liquidacion.EstadoCondicionValidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionCondicionesSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionSubsidioAjuste;
import com.asopagos.entidades.subsidiomonetario.liquidacion.Periodo;
import com.asopagos.entidades.subsidiomonetario.liquidacion.PeriodoLiquidacion;
import com.asopagos.entidades.subsidiomonetario.liquidacion.PersonaLiquidacionEspecifica;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.entidades.transversal.core.CajaCompensacion;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
//import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.GrupoAplicacionValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoDocumentoBloqueoBeneficiarioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoEjecucionProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoPeriodoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.*;
import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.util.SubsidioDateUtils;
import com.asopagos.subsidiomonetario.util.ValuePersonaFallecimiento;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import java.time.LocalDate;
import java.time.ZoneId;
import com.asopagos.enumeraciones.subsidiomonetario.bloqueos.CausalBloqueoCuotaMonetariaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoNivelBloqueoEnum;
import com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoAfiliadoBeneficiarioCM;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio;
import javax.inject.Inject;
import com.asopagos.dto.subsidiomonetario.pagos.ItemSubsidioBeneficiarioDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    @Inject
    private IConsultasModeloSubsidio consultasSubsidio;
    
    @Resource
    TimerService timerService;

    private EntityManager entityManager2;

    final String cadenaVacia = "";

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#ejecutarLiquidacionMasiva(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.ejecutarLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = new RespuestaGenericaDTO();
        result.setEjecucionEnProceso(verificarLiquidacionEnProceso());

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#guardarLiquidacionMasiva(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RespuestaGenericaDTO guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.guardarLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = new RespuestaGenericaDTO();
        Periodo periodoBD = null;

        try {
            // Cambiar el estado a cerrado de otras solicitudes programadas, antes de guardar la nueva  
            entityManagerCore.createNamedQuery(NamedQueriesConstants.CERRAR_SOLICITUDES)
                    .setParameter("estadoSolicitudLiquidacion", EstadoProcesoLiquidacionEnum.CERRADA)
                    .setParameter("tipoEjecucionProceso", TipoEjecucionProcesoLiquidacionEnum.PROGRAMADA).executeUpdate();

            // Consultar si el periodo que viene desde pantalla existe
            periodoBD = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODOS, Periodo.class)
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getSingleResult();
        } catch (NoResultException nre) {
            result.setOperacionExitosa(Boolean.FALSE);
            result.setCausaError("PERIODO_NO_EXISTE_EN_BD");
            return result;
        } catch (NonUniqueResultException nue) {
            result.setOperacionExitosa(Boolean.FALSE);
            result.setCausaError("VARIOS_PERIODOS_IGUALES");
            return result;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        try {
            //Guardar una nueva solicitud global
            Solicitud solGlobal = new Solicitud();
            solGlobal.setCanalRecepcion(CanalRecepcionEnum.WEB);
            solGlobal.setFechaCreacion(new Date());

            if (userDTO.getNombreUsuario() != null) {
                solGlobal.setUsuarioRadicacion(userDTO.getNombreUsuario());
            }

            entityManagerCore.persist(solGlobal);
            entityManagerCore.flush();

            // Agregar id solicitud a la respuesta
            result.setIdSolicitud(solGlobal.getIdSolicitud());

            //Guardar una solicitud de liquidacion (asociar solicitud global)
            liquidacion.setSolicitudGlobal(solGlobal);
            liquidacion.setFechaInicial(new Date().getTime());
            SolicitudLiquidacionSubsidio sls = liquidacion.convertToEntity();
            entityManagerCore.persist(sls);
            entityManagerCore.flush();

            result.setIdSolicitudLiquidacion(sls.getIdProcesoLiquidacionSubsidio());

            // Guardar el periodo de la liquidacion (asociar la solicitud liquidacion y periodo)            
            if (periodoBD != null) {
                PeriodoLiquidacion periodoLiquidacion = new PeriodoLiquidacion();
                periodoLiquidacion.setIdPeriodo(periodoBD.getIdPeriodo());
                periodoLiquidacion.setIdSolicitudLiquidacionSubsidio(sls.getIdProcesoLiquidacionSubsidio());
                periodoLiquidacion.setTipoPeriodo(TipoPeriodoEnum.REGULAR);
                entityManagerCore.persist(periodoLiquidacion);
                entityManagerCore.flush();

                //Asociar el periodo a la respuesta
                result.setPeriodo(periodoBD.getFechaPeriodo());
            }

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        result.setOperacionExitosa(Boolean.valueOf(true));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidios.business.interfaces.IConsultasModeloCore#cancelarLiquidacionMasiva()
     */
    @Override
    public Boolean cancelarLiquidacionMasiva() {
        String firmaMetodo = "ConsultasModeloCore.cancelarLiquidacionMasiva()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.valueOf(true);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarHistoricoLiquidacionMasiva(java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionMasiva(Long periodoRegular, Long fechaInicio,
            Long fechaFin, String numeroOperacion, UriInfo uri, HttpServletResponse response) {

        String firmaMetodo = "ConsultasModeloCore.consultarHistoricoLiquidacionMasiva(Long, Long, Long, String, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String porcentaje = "%";
        final String fechaInicioPorDefecto = "1950-01-01";

        Query query = null;
        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);

        Date fechaPeriodoRegular = (periodoRegular == null) ? null : new Date(periodoRegular);
        queryBuilder.addParam("periodo", fechaPeriodoRegular);
        String numeroRadicacion = (numeroOperacion == null) ? porcentaje + porcentaje : porcentaje + numeroOperacion + porcentaje;
        queryBuilder.addParam("numeroRadicacion", numeroRadicacion);
        Date fechaInicial = (fechaInicio == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioPorDefecto)
                : CalendarUtils.truncarHora(new Date(fechaInicio));
        queryBuilder.addParam("fechaInicio", fechaInicial);
        Date fechaFinal = (fechaFin == null) ? CalendarUtils.truncarHoraMaxima(new Date())
                : CalendarUtils.truncarHoraMaxima(new Date(fechaFin));
        queryBuilder.addParam("fechaFin", fechaFinal);

        queryBuilder.addOrderByDefaultParam("fechaInicio");

        query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_LIQUIDACION_MASIVA, null);
        logger.info("Respuesta del query en metodo ConsultasModeloCore.consultarHistoricoLiquidacionMasiva ees: " + query.getResultList());
        try {
            List<ResultadoHistoricoLiquidacionMasivaDTO> liquidaciones = query.getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
           
           return (liquidaciones.isEmpty()) ? null : liquidaciones;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarHistoricoLiquidacionEspecifica(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String,
     * javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionEspecifica(TipoProcesoLiquidacionEnum tipoLiquidacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, Long periodoRegular, Long fechaInicio, Long fechaFin,
            String numeroOperacion, UriInfo uri, HttpServletResponse response) {

        String firmaMetodo = "ConsultasModeloCore.consultarHistoricoLiquidacionEspecifica(TipoProcesoLiquidacionEnum, Long, Long, Long, String, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String porcentaje = "%";
        final String fechaInicioPorDefecto = "1950-01-01";

        Query query = null;
        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);

        Date fechaPeriodoRegular = null;
        if (periodoRegular != null) {
            fechaPeriodoRegular = new Date(periodoRegular);
        }
        queryBuilder.addParam("periodo", fechaPeriodoRegular);
        String numeroRadicacion = (numeroOperacion == null) ? porcentaje + porcentaje : porcentaje + numeroOperacion + porcentaje;
        queryBuilder.addParam("numeroRadicacion", numeroRadicacion);
        Date fechaInicial = (fechaInicio == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioPorDefecto)
                : CalendarUtils.truncarHora(new Date(fechaInicio));
        queryBuilder.addParam("fechaInicio", fechaInicial);
        Date fechaFinal = (fechaFin == null) ? CalendarUtils.truncarHoraMaxima(new Date())
                : CalendarUtils.truncarHoraMaxima(new Date(fechaFin));
        queryBuilder.addParam("fechaFin", fechaFinal);
        String tipoProcesoLiquidacion = tipoLiquidacion.toString();

        String tipoProcesoLiquidacionEspecifica = null;
        if (tipoLiquidacion.equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)) {
            tipoProcesoLiquidacionEspecifica = tipoLiquidacionEspecifica.toString();
        }
        queryBuilder.addParam("tipoLiquidacionEspecifica", tipoProcesoLiquidacionEspecifica);
        queryBuilder.addParam("tipoLiquidacion", tipoProcesoLiquidacion);

        queryBuilder.addOrderByDefaultParam("fecha");

        query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_LIQUIDACION_ESPECIFICA, null);

        try {
            List<Object[]> registrosLiquidaciones = query.getResultList();

            List<ResultadoHistoricoLiquidacionMasivaDTO> liquidaciones = new ArrayList<>();
            for (Object[] registroLiquidacion : registrosLiquidaciones) {
                liquidaciones.add(convertirRegistroHistoricoLiquidacion(registroLiquidacion));
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return (liquidaciones.isEmpty()) ? null : liquidaciones;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite realizar la conversión al DTO para el resultado de la
     * consulta de históricos de liquidaciones específicas
     *
     * @param registroLiquidacion arreglo con la información del registro
     * @return DTO con la información del registro
     * @author rlopez
     */
    private ResultadoHistoricoLiquidacionMasivaDTO convertirRegistroHistoricoLiquidacion(Object[] registroLiquidacion) {
        String firmaMetodo = "ConsultasModeloCore.convertirRegistroHistoricoLiquidacion(Object[])";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoHistoricoLiquidacionMasivaDTO registroDTO = new ResultadoHistoricoLiquidacionMasivaDTO();

        registroDTO.setNumeroOperacion(registroLiquidacion[0].toString());
        registroDTO.setPeriodo(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroLiquidacion[1].toString()));
        registroDTO.setFecha(
                (registroLiquidacion[2] == null) ? null : (Date) registroLiquidacion[2]);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return registroDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#inicializarPantallaSolicitudLiquidacion()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacion(UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.inicializarPantallaSolicitudLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = new IniciarSolicitudLiquidacionMasivaDTO();

        if (userDTO.getNombreUsuario() != null) {
            result.setUsuarioActual(userDTO.getNombreUsuario());
        }

        // Buscar si hay una liquidacion masiva y especificas con estado diferente a cerrada
        // y de fallecimiento con estado diferentes a cerrada y en pendiente de pago de aportes
        List<EstadoProcesoLiquidacionEnum> estadosLiquidacion = new ArrayList<>();
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.CERRADA);
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);
        List<SolicitudLiquidacionSubsidio> listaSolicitudes = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_LIQUIDACION_SIN_CERRAR, SolicitudLiquidacionSubsidio.class)
                .setParameter("estadoSolicitudLiquidacion", estadosLiquidacion)
                .setParameter("estadoSolicitudLiquidacionCerrada", estadosLiquidacion.get(0))
                .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION).getResultList();
        //seg
        System.out.println("estadosLiquidacion.get(0): " + estadosLiquidacion.get(0));
        //
        //Obtener el id del ultimo registro 
        if (!listaSolicitudes.isEmpty()) {

            result.setLiquidacionEnProceso(Boolean.valueOf(true));

            //traer masiva y cerrada
            List<SolicitudLiquidacionSubsidio> listaSolicitudesMasiva = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_LIQUIDACION_MASIVA_CERRADA, SolicitudLiquidacionSubsidio.class)
                    .setParameter("estadoSolicitudLiquidacion", EstadoProcesoLiquidacionEnum.CERRADA)
                    .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.MASIVA).getResultList();

            final Comparator<SolicitudLiquidacionSubsidio> comp = (s1, s2) -> Long.compare(s1.getIdProcesoLiquidacionSubsidio(),
                    s2.getIdProcesoLiquidacionSubsidio());
            SolicitudLiquidacionSubsidio ultimoRegistro = listaSolicitudes.stream().max(comp).get();

            final Comparator<SolicitudLiquidacionSubsidio> compMasiva = (s3, s4) -> Long.compare(s3.getIdProcesoLiquidacionSubsidio(),
                    s4.getIdProcesoLiquidacionSubsidio());

            SolicitudLiquidacionSubsidio ultimoRegistroMasiva = null;
            if (!listaSolicitudesMasiva.isEmpty()) {
                ultimoRegistroMasiva = listaSolicitudesMasiva.stream().max(compMasiva).get();
            }

            if (ultimoRegistro.getFechaHoraEjecucionProgramada() != null) {
                result.setFechaHoraEjecucionProgramada(ultimoRegistro.getFechaHoraEjecucionProgramada());
            }

            if (ultimoRegistro.getEstadoLiquidacion() != null) {
                result.setEstadoProcesoLiquidacion(ultimoRegistro.getEstadoLiquidacion());
            }

            if (ultimoRegistro.getTipoEjecucionProceso() != null) {
                result.setTipoEjecucionProcesoLiquidacion(ultimoRegistro.getTipoEjecucionProceso());
            }

            // se consulta la ultima fecha de corte de aportes de una liquidación masiva 
            result.setFechaUltimoCorteAportes(consultarFechaCorteAportesLiquidacionMasivaPrevia(new Date()));

            if (ultimoRegistro.getTipoLiquidacion() != null) {
                result.setTipoProcesoLiquidacion(ultimoRegistro.getTipoLiquidacion());
            }

            if (ultimoRegistro.getTipoLiquidacionEspecifica() != null) {
                result.setTipoLiquidacionEspecifica(ultimoRegistro.getTipoLiquidacionEspecifica());
            }

            // Id de la solicitud liquidación subsidio
            if (ultimoRegistro.getIdProcesoLiquidacionSubsidio() != null) {
                result.setIdSolicitudLiquidacionActual(ultimoRegistro.getIdProcesoLiquidacionSubsidio());
            }

            // Id de la solicitud global
            if (ultimoRegistro.getSolicitudGlobal().getIdSolicitud() != null) {
                result.setIdSolicitudGlobalActual(ultimoRegistro.getSolicitudGlobal().getIdSolicitud());
            }

            // Id de la instancia del proceso en el BPM
            if (ultimoRegistro.getSolicitudGlobal().getIdInstanciaProceso() != null) {
                result.setIdInstanciaProceso(ultimoRegistro.getSolicitudGlobal().getIdInstanciaProceso());
            }

            // Número de radicado
            if (ultimoRegistro.getSolicitudGlobal().getNumeroRadicacion() != null) {
                result.setNumeroRadicado(ultimoRegistro.getSolicitudGlobal().getNumeroRadicacion());
            }

            // Usuario radicador
            if (ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion() != null) {
                result.setUsuarioRadicador(ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion());
            }

            // Establecer si el usuario actual es el mismo usuario que hizo la radicación
            if ((ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion() != null) && (userDTO.getNombreUsuario() != null)) {
                result.setEsUsuarioQueRadico(
                        (ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion().equals(userDTO.getNombreUsuario())));
            }

            // Establecer el periodo asociado a la liquidacion masiva
            if (ultimoRegistroMasiva != null && ultimoRegistroMasiva.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.MASIVA)) {
                result.setPeriodoLiquidacionMasiva(consultarPeriodoRegular(ultimoRegistroMasiva.getIdProcesoLiquidacionSubsidio()));
            }

            result.setFinalizacionExitosa(Boolean.FALSE);
            result.setProcesoCanceladoPorOperador(Boolean.FALSE);
            result.setPorcentajeActual(25);
            result.setRechazadoPorSupervisor(Boolean.FALSE);
            if (ultimoRegistroMasiva != null) {
                result.setFechaUltimoCorteAportes(ultimoRegistroMasiva.getFechaCorteAportes());
            } else {
                result.setFechaUltimoCorteAportes(consultarFechaCorteAportesLiquidacionMasivaPrevia(new Date()));
            }
        } // No hay liquidaciones en proceso
        else {
            result.setLiquidacionEnProceso(Boolean.valueOf(false));
            result.setFinalizacionExitosa(Boolean.TRUE);
            result.setProcesoCanceladoPorOperador(Boolean.FALSE);
            result.setPorcentajeActual(0);
            result.setRechazadoPorSupervisor(Boolean.FALSE);
            result.setFechaUltimoCorteAportes(consultarFechaCorteAportesLiquidacionMasivaPrevia(new Date()));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerrada(UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.inicializarPantallaSolicitudLiquidacionCerrada()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = new IniciarSolicitudLiquidacionMasivaDTO();

        if (userDTO.getNombreUsuario() != null) {
            result.setUsuarioActual(userDTO.getNombreUsuario());
        }

        // Buscar si hay una liquidacion masiva y especificas con estado diferente a cerrada
        // y de fallecimiento con estado diferentes a cerrada y en pendiente de pago de aportes
        List<EstadoProcesoLiquidacionEnum> estadosLiquidacion = new ArrayList<>();
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.CERRADA);
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);
        List<SolicitudLiquidacionSubsidio> listaSolicitudes = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_LIQUIDACION_DIFERENTES_MASIVA, SolicitudLiquidacionSubsidio.class)
                .setParameter("estadoSolicitudLiquidacion", estadosLiquidacion)
                .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.MASIVA).getResultList();

        //Obtener el id del ultimo registro 
        if (!listaSolicitudes.isEmpty()) {
            result.setLiquidacionEnProceso(Boolean.valueOf(true));

            final Comparator<SolicitudLiquidacionSubsidio> comp = (s1, s2) -> Long.compare(s1.getIdProcesoLiquidacionSubsidio(),
                    s2.getIdProcesoLiquidacionSubsidio());
            SolicitudLiquidacionSubsidio ultimoRegistro = listaSolicitudes.stream().max(comp).get();

            if (ultimoRegistro.getFechaHoraEjecucionProgramada() != null) {
                result.setFechaHoraEjecucionProgramada(ultimoRegistro.getFechaHoraEjecucionProgramada());
            }
            
            if (ultimoRegistro.getEstadoLiquidacion() != null) {
                result.setEstadoProcesoLiquidacion(ultimoRegistro.getEstadoLiquidacion());
            }

            if (ultimoRegistro.getTipoEjecucionProceso() != null) {
                result.setTipoEjecucionProcesoLiquidacion(ultimoRegistro.getTipoEjecucionProceso());
            }

            // se consulta la ultima fecha de corte de aportes de una liquidación masiva 
            result.setFechaUltimoCorteAportes(consultarFechaCorteAportesLiquidacionMasivaPrevia(new Date()));

            if (ultimoRegistro.getTipoLiquidacion() != null) {
                result.setTipoProcesoLiquidacion(ultimoRegistro.getTipoLiquidacion());
            }

            if (ultimoRegistro.getTipoLiquidacionEspecifica() != null) {
                result.setTipoLiquidacionEspecifica(ultimoRegistro.getTipoLiquidacionEspecifica());
            }

            // Id de la solicitud liquidación subsidio
            if (ultimoRegistro.getIdProcesoLiquidacionSubsidio() != null) {
                result.setIdSolicitudLiquidacionActual(ultimoRegistro.getIdProcesoLiquidacionSubsidio());
            }

            // Id de la solicitud global
            if (ultimoRegistro.getSolicitudGlobal().getIdSolicitud() != null) {
                result.setIdSolicitudGlobalActual(ultimoRegistro.getSolicitudGlobal().getIdSolicitud());
            }

            // Id de la instancia del proceso en el BPM
            if (ultimoRegistro.getSolicitudGlobal().getIdInstanciaProceso() != null) {
                result.setIdInstanciaProceso(ultimoRegistro.getSolicitudGlobal().getIdInstanciaProceso());
            }

            // Número de radicado
            if (ultimoRegistro.getSolicitudGlobal().getNumeroRadicacion() != null) {
                result.setNumeroRadicado(ultimoRegistro.getSolicitudGlobal().getNumeroRadicacion());
            }

            // Usuario radicador
            if (ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion() != null) {
                result.setUsuarioRadicador(ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion());
            }

            // Establecer si el usuario actual es el mismo usuario que hizo la radicación
            if ((ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion() != null) && (userDTO.getNombreUsuario() != null)) {
                result.setEsUsuarioQueRadico(
                        (ultimoRegistro.getSolicitudGlobal().getUsuarioRadicacion().equals(userDTO.getNombreUsuario())));
            }

            // Establecer el periodo asociado a la liquidacion masiva
            if (ultimoRegistro.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.MASIVA)) {
                result.setPeriodoLiquidacionMasiva(consultarPeriodoRegular(ultimoRegistro.getIdProcesoLiquidacionSubsidio()));
            }

            result.setFinalizacionExitosa(Boolean.FALSE);
            result.setProcesoCanceladoPorOperador(Boolean.FALSE);
            result.setPorcentajeActual(25);
            result.setRechazadoPorSupervisor(Boolean.FALSE);
            result.setFechaUltimoCorteAportes(ultimoRegistro.getFechaCorteAportes());
        }
        // No hay liquidaciones en proceso
        else {
            result.setLiquidacionEnProceso(Boolean.valueOf(false));
            result.setFinalizacionExitosa(Boolean.TRUE);
            result.setProcesoCanceladoPorOperador(Boolean.FALSE);
            result.setPorcentajeActual(0);
            result.setRechazadoPorSupervisor(Boolean.FALSE);
            result.setFechaUltimoCorteAportes(consultarFechaCorteAportesLiquidacionMasivaPrevia(new Date()));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }*/
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarParametrizacion(com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long actualizarParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion) {
        String firmaMetodo = "ConsultasModeloCore.actualizarParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idParametrizacion = parametrizacion.getIdParametrizacionLiquidacionSubsidio();

        try {
            ParametrizacionLiquidacionSubsidio parametrizacionLiq = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_LIQUIDACION_ID,
                            ParametrizacionLiquidacionSubsidio.class)
                    .setParameter("idParametrizacionLiquidacionSubsidio", idParametrizacion).getSingleResult();

            //Validar que la nueva fecha para el periodo final sea superior a la fecha previamente definida
            if (parametrizacionLiq.getFechaPeriodoFinal().getTime() < parametrizacion.getFechaPeriodoFinal().getTime()) {
                parametrizacionLiq.setFechaPeriodoFinal(parametrizacion.getFechaPeriodoFinal());
                entityManagerCore.merge(parametrizacionLiq);
            } else {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }
        } catch (NoResultException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (AsopagosException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idParametrizacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#crearParametrizacion(com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO)
     */
    @Override
    public Long crearParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion) {
        String firmaMetodo = "ConsultasModeloCore.crearParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idParametrizacion = null;
        try {
            ParametrizacionLiquidacionSubsidio parametrizacionLiq = ParametrizacionLiquidacionSubsidioModeloDTO
                    .convertToEntity(parametrizacion);
            entityManagerCore.persist(parametrizacionLiq);
            idParametrizacion = parametrizacionLiq.getIdParametrizacionLiquidacionSubsidio();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idParametrizacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarCondicion(com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long actualizarCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO) {
        String firmaMetodo = "ConsultasModeloCore.actualizarCondicion(ParametrizacionCondicionesSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idCondicion = condicionDTO.getIdParametrizacionCondicionesSubsidio();

        try {
            ParametrizacionCondicionesSubsidio condicionLiq = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_LIQUIDACION_ID, ParametrizacionCondicionesSubsidio.class)
                    .setParameter("idParametrizacionCondicionesSubsidio", idCondicion).getSingleResult();

            //Validar que la nueva fecha para el periodo final sea superior a la fecha previamente definida
            if (condicionLiq.getFechaPeriodoFinal().getTime() < condicionDTO.getFechaPeriodoFinal().getTime()) {
                condicionLiq.setFechaPeriodoFinal(condicionDTO.getFechaPeriodoFinal());
                entityManagerCore.merge(condicionLiq);
            } else {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }
        } catch (NoResultException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (AsopagosException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idCondicion;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#crearCondicion(com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO)
     */
    @Override
    public Long crearCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO) {
        String firmaMetodo = "ConsultasModeloCore.crearCondicion(ParametrizacionCondicionesSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idCondicion = null;
        try {
            ParametrizacionCondicionesSubsidio condicionLiq = condicionDTO.convertToEntity();
            entityManagerCore.persist(condicionLiq);
            idCondicion = condicionLiq.getIdParametrizacionCondicionesSubsidio();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idCondicion;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarSolicitud(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarIdSolicitud(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarSolicitud(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = null;
        try {
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION_NUMERO_RADICADO, Long.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#aprobarLiquidacionMasivaPrimerNivel(java.lang.String,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long aprobarLiquidacionMasivaPrimerNivel(String numeroSolicitud, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.aprobarLiquidacionMasivaPrimerNivel(String, userDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = null;
        try {

            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroSolicitud).getSingleResult();

            solicitudLiquidacionSubsidio.setUsuarioEvaluacionPrimerNivel(userDTO.getNombreUsuario());
            solicitudLiquidacionSubsidio.setFechaEvaluacionPrimerNivel(new Date());
            entityManagerCore.merge(solicitudLiquidacionSubsidio);
            result = solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio();

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#rechazarLiquidacionMasivaPrimerNivel(java.lang.String,
     * com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long rechazarLiquidacionMasivaPrimerNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.rechazarLiquidacionMasivaPrimerNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = null;
        try {

            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroSolicitud).getSingleResult();

            solicitudLiquidacionSubsidio.setUsuarioEvaluacionPrimerNivel(userDTO.getNombreUsuario());
            solicitudLiquidacionSubsidio.setRazonRechazoLiquidacion(aprobacionRechazoSubsidioMonetarioDTO.getRazonRechazo());
            solicitudLiquidacionSubsidio.setObservacionesPrimerNivel(aprobacionRechazoSubsidioMonetarioDTO.getObservaciones());
            solicitudLiquidacionSubsidio.setFechaEvaluacionPrimerNivel(new Date());
            entityManagerCore.merge(solicitudLiquidacionSubsidio);
            result = solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio();

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#aprobarLiquidacionMasivaSegundoNivel(java.lang.String,
     * com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public SolicitudLiquidacionSubsidioModeloDTO aprobarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.aprobarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO result = null;
        try {

            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroSolicitud).getSingleResult();

            solicitudLiquidacionSubsidio.setUsuarioEvaluacionSegundoNivel(userDTO.getNombreUsuario());
            solicitudLiquidacionSubsidio.setObservacionesSegundoNivel(aprobacionRechazoSubsidioMonetarioDTO.getObservaciones());
            solicitudLiquidacionSubsidio.setFechaEvaluacionSegundoNivel(new Date());
            entityManagerCore.merge(solicitudLiquidacionSubsidio);
            result = new SolicitudLiquidacionSubsidioModeloDTO();
            result.convertToDTO(solicitudLiquidacionSubsidio);
            result.setSolicitudGlobal(null);

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#rechazarLiquidacionMasivaSegundoNivel(java.lang.String,
     * com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public SolicitudLiquidacionSubsidioModeloDTO rechazarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.rechazarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO result = null;
        try {

            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroSolicitud).getSingleResult();

            solicitudLiquidacionSubsidio.setUsuarioEvaluacionSegundoNivel(userDTO.getNombreUsuario());
            solicitudLiquidacionSubsidio.setRazonRechazoLiquidacion(aprobacionRechazoSubsidioMonetarioDTO.getRazonRechazo());
            solicitudLiquidacionSubsidio.setObservacionesSegundoNivel(aprobacionRechazoSubsidioMonetarioDTO.getObservaciones());
            solicitudLiquidacionSubsidio.setFechaEvaluacionSegundoNivel(new Date());
            entityManagerCore.merge(solicitudLiquidacionSubsidio);
            result = new SolicitudLiquidacionSubsidioModeloDTO();
            result.convertToDTO(solicitudLiquidacionSubsidio);
            result.setSolicitudGlobal(null);

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarInstanciaSolicitudGlobal(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public void actualizarInstanciaSolicitudGlobal(Long idInstancia, Long idSolicitudGlobal) {
        String firmaMetodo = "SubsidioBusiness.actualizarInstanciaSolicitudGlobal(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            // Obtener la entidad correspondiente al id 
            Solicitud solicitud = entityManagerCore.find(Solicitud.class, idSolicitudGlobal);
            solicitud = entityManagerCore.merge(solicitud);
            solicitud.setIdInstanciaProceso(idInstancia.toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarEstadoSolicitudLiquidacion(java.lang.Long,
     * com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoSolicitudLiquidacion(Long idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum estado) {
        String firmaMetodo = "SubsidioBusiness.actualizarEstadoSolicitudLiquidacion(Long, EstadoProcesoLiquidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidio solicitud = consultarSolicitudLiquidacion(idSolicitudLiquidacion);
        logger.info("solicitud " +solicitud.getSolicitudGlobal().getNumeroRadicacion());
        logger.info("solicitud " +solicitud.getSolicitudGlobal().getResultadoProceso());
        actualizarEstadoSolicitudLiquidacion(estado, solicitud);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método que permite consultar una solicitud de liquidación mediante su
     * correspondiente identificador
     *
     * @param idSolicitudLiquidacion identificador de la solicitud
     * @return información de la solicitud de liquidación
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private SolicitudLiquidacionSubsidio consultarSolicitudLiquidacion(Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(Long, EstadoProcesoLiquidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidio solicitud = null;
        try {
            solicitud = entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD, SolicitudLiquidacionSubsidio.class)
                    .setParameter("solicitudLiquidacion", idSolicitudLiquidacion).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitud;
    }

    /**
     * Método que permite realizar la actualización de la liquidación
     *
     * @param idSolicitudLiquidacion identificador de la solicitud de
     * liquidación
     * @param estado estado para actualización
     * @param solicitud solicitud registrada
     */
    private void actualizarEstadoSolicitudLiquidacion(EstadoProcesoLiquidacionEnum estado, SolicitudLiquidacionSubsidio solicitud) {
        String firmaMetodo = "SubsidioBusiness.actualizarEstadoSolicitudLiquidacion(Long, EstadoProcesoLiquidacionEnum, SolicitudLiquidacionSubsidio)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (solicitud != null) {
            /* Se verifica si desea cerrar el proceso y que el estado previo sea valido */
            if (EstadoProcesoLiquidacionEnum.CERRADA.equals(estado)
                    && (EstadoProcesoLiquidacionEnum.DISPERSADA.equals(solicitud.getEstadoLiquidacion())
                    || EstadoProcesoLiquidacionEnum.CANCELADA.equals(solicitud.getEstadoLiquidacion())
                    || EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL.equals(solicitud.getEstadoLiquidacion()))) {
                logger.info("Ingresa el if " + solicitud.getEstadoLiquidacion());
                solicitud.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitud.getEstadoLiquidacion().name()));
            }
            logger.info("solicitud estado " +solicitud.getEstadoLiquidacion());
            logger.info("solicitud estado xxx" +estado);
            solicitud.setEstadoLiquidacion(estado);
            entityManagerCore.merge(solicitud);
            logger.info("solicitud estado sale" +solicitud.getEstadoLiquidacion());
        } else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarSolicitudLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudLiquidacionSubsidioModeloDTO consultarSolicitudLiquidacion(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.consultarSolicitudLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " - numeroRadicado: " + numeroRadicado);

        SolicitudLiquidacionSubsidioModeloDTO result = null;
        try {
            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();

            result = new SolicitudLiquidacionSubsidioModeloDTO();
            result.convertToDTO(solicitudLiquidacionSubsidio);
            result.setSolicitudGlobal(null);
            result.setIdInstanciaProceso(solicitudLiquidacionSubsidio.getSolicitudGlobal().getIdInstanciaProceso());
            result.setPeriodoRegular(consultarPeriodoRegular(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));
            /*try {
                PersonaFallecidaTrabajadorDTO detallePersona = consultarPersonaFallecidaTrabajadorBeneficiarios(result.getIdProcesoLiquidacionSubsidio());
                result.setFechaFallecido(detallePersona!= null?detallePersona.getFechaFallecido():null);
                result.setSolicitudGlobal(solicitudLiquidacionSubsidio.getSolicitudGlobal());
            } catch (Exception e) { }*/

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método que se encarga de consultar el periodo regular asociado a una
     * liquidación
     *
     * @param idSolicitudLiquidacion identificador de la solicitud de
     * liquidación
     * @return fecha del periodo regular
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Date consultarPeriodoRegular(Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioBusiness.consultarPeriodoRegular(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoRegular = null;
        try {
            periodoRegular = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_SOLICITUD_LIQUIDACION, Date.class)
                    .setParameter("idSolicitudLiquidacion", idSolicitudLiquidacion).getSingleResult();

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoRegular;
    }

    /**
     * Metodo para consultar si hay una liquidacion en proceso (Estado !=
     * cerrado)
     *
     * @return SolicitudLiquidacion en proceso
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudLiquidacionSubsidioModeloDTO consultarLiquidacionEnProceso() {
        String firmaMetodo = "ConsultasModeloCore.consultarLiquidacionEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<SolicitudLiquidacionSubsidio> slsList = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_LIQUIDACION_MASIVA_NO_CERRADA)
                .setParameter("estadoProcesoLiquidacion", EstadoProcesoLiquidacionEnum.CERRADA)
                .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.MASIVA).setFirstResult(0).setMaxResults(1).getResultList();

        if (!slsList.isEmpty()) {
            SolicitudLiquidacionSubsidio sls = slsList.get(0);
            SolicitudLiquidacionSubsidioModeloDTO modelDTO = new SolicitudLiquidacionSubsidioModeloDTO();
            modelDTO.convertToDTO(sls);
            return modelDTO;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Metodo que permite saber si una persona esta siendo parte de una
     * liquidacion especifica
     *
     * @param persona con la cual se va a buscar si esta siendo parte de una
     * liquidacion especifica
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean consultarPersonaEnLiquidacionEspecifica(Persona persona) {
        String firmaMetodo = "SubsidioBusiness.consultarLiquidacionEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersona(com.asopagos.dto.PersonaDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaGenericaDTO seleccionarPersona(Long personaId) {
        String firmaMetodo = "ConsultasModeloCore.seleccionarPersona(PersonaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = new RespuestaGenericaDTO();
        // Consultar si hay liquidaciones en proceso
        SolicitudLiquidacionSubsidioModeloDTO slsModel = consultarLiquidacionEnProceso();

        // Hay liquidacion en proceso
        if (slsModel != null) {
            result.setLiquidacionMasivaEnProceso(Boolean.TRUE);
            result.setIdLiquidacionMasivaEnProceso(slsModel.getIdProcesoLiquidacionSubsidio());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarEmpleador(com.asopagos.entidades.ccf.personas.Empleador)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaGenericaDTO seleccionarEmpleador(Long empleadorId) {
        String firmaMetodo = "ConsultasModeloCore.seleccionarEmpleador(Empleador)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = new RespuestaGenericaDTO();
        // Consultar si hay liquidaciones en proceso
        SolicitudLiquidacionSubsidioModeloDTO slsModel = consultarLiquidacionEnProceso();

        // Hay liquidacion en proceso
        if (slsModel != null) {
            result.setLiquidacionMasivaEnProceso(Boolean.TRUE);
            result.setIdLiquidacionMasivaEnProceso(slsModel.getIdProcesoLiquidacionSubsidio());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarBeneficiariosAfiliado(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliado(Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.consultarBeneficiariosAfiliado(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> beneficiarios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_DISTINCT)
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE).setParameter("idPersona", idPersona).getResultList();

        if (!beneficiarios.isEmpty()) {
            return beneficiarios;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Collections.emptyList();
    }

    /**
     * @param listaPersonas
     * @return
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliadoIds(List<Long> listaPersonas) {
        String firmaMetodo = "ConsultasModeloCore.consultarBeneficiariosAfiliadoIds(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> beneficiarios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_IDS)
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE).setParameter("listaPersonas", listaPersonas)
                .getResultList();

        if (!beneficiarios.isEmpty()) {
            return beneficiarios;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Collections.emptyList();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#generarNuevoPeriodo()
     */
    @Override
    public void generarNuevoPeriodo() {
        String firmaMetodo = "ConsultasModeloCore.consultarBeneficiariosAfiliado(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean periodoExiste = verificarExistenciaEnPeriodo(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date()).getTime());

        if (!periodoExiste) {
            Periodo periodo = new Periodo();
            periodo.setFechaPeriodo(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date()));
            entityManagerCore.persist(periodo);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#generarNuevoPeriodo()
     */
    @Override
    public void generarNuevoPeriodo(Long periodoL) {
        String firmaMetodo = "ConsultasModeloCore.consultarBeneficiariosAfiliado(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean periodoExiste = verificarExistenciaEnPeriodo(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodoL)).getTime());

        if (!periodoExiste) {
            Periodo periodo = new Periodo();
            periodo.setFechaPeriodo(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date()));
            entityManagerCore.persist(periodo);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarLiquidacionesProgramadasAbiertas()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaGenericaDTO consultarLiquidacionesProgramadasAbiertas() {
        String firmaMetodo = "ConsultasModeloCore.consultarLiquidacionesProgramadasAbiertas()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaGenericaDTO> result = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_LIQUIDACIONES_PROGRAMADAS_ABIERTAS)
                .setParameter("estadoProcesoLiquidacion", EstadoProcesoLiquidacionEnum.CERRADA)
                .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.MASIVA)
                .setParameter("tipoEjecucion", TipoEjecucionProcesoLiquidacionEnum.PROGRAMADA).setFirstResult(0).setMaxResults(1)
                .getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result.get(0);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarEstadoSolicitudLiquidacionXNumRadicado(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoSolicitudLiquidacionXNumRadicado(String numeroRadicado, EstadoProcesoLiquidacionEnum estado) {
        String firmaMetodo = "ConsultasModeloCore.consultarLiquidacionesProgramadasAbiertas()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            // Cambiar el estado a cerrado de otras solicitudes programadas, antes de guardar la nueva  
            entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_SOLICITUDES_X_NUMERO_RADICADO)
                    .setParameter("nuevoEstado", estado).setParameter("numeroRadicado", numeroRadicado).executeUpdate();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#guardarLiquidacionEspecifica(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarValorCuotaPeriodo(java.util.Date)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal consultarValorCuotaPeriodo(Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarValorCuotaPeriodo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal valorCuota = BigDecimal.ZERO;

        try {
            valorCuota = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALOR_CUOTA_PERIODO, BigDecimal.class)
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getSingleResult();
        } catch (NoResultException nre) {
            return BigDecimal.valueOf(-1);
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorCuota;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarArchivosLiquidacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarArchivosLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();

        try {
            ArchivoLiquidacionSubsidio archivoLiquidacion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_LIQUIDACION, ArchivoLiquidacionSubsidio.class)
                    .setParameter("numeroRadicacion", numeroRadicacion).setFirstResult(0).setMaxResults(1).getSingleResult();
                    logger.debug("Acaba de consultar el query y trae la informacion");
            archivoLiquidacionDTO.convertToDTO(archivoLiquidacion);

        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info("Resultado despues de la conversion convertToDTO es : " + archivoLiquidacionDTO);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacionDTO;
    }
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionPorId(Long idArchivoLiquidacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarArchivosLiquidacionPorId(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();

        try {
            ArchivoLiquidacionSubsidio archivoLiquidacion = entityManagerCore.find(ArchivoLiquidacionSubsidio.class, idArchivoLiquidacion);
            logger.debug("Acaba de consultar el query y trae la informacion");
            archivoLiquidacionDTO.convertToDTO(archivoLiquidacion);

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info("Resultado despues de la conversion convertToDTO es : " + archivoLiquidacionDTO.toString());
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarArchivosLiquidacion(com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long actualizarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO) {
        String firmaMetodo = "ConsultasModeloCore.actualizarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidio archivoLiquidacion = new ArchivoLiquidacionSubsidio();
        try {
            ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTOConsulta = consultarArchivosLiquidacionId(
                    archivoLiquidacionDTO.getIdArchivoLiquidacionSubsidio());
            if (archivoLiquidacionDTOConsulta != null) {
                archivoLiquidacion = archivoLiquidacionDTO.convertToEntity(archivoLiquidacionDTOConsulta);
                entityManagerCore.merge(archivoLiquidacion);
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacion.getIdArchivoLiquidacionSubsidio();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#registrarArchivosLiquidacion(com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO)
     */
    @Override
    public Long registrarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidio archivoLiquidacion = archivoLiquidacionDTO.convertToEntity();
        try {
            entityManagerCore.persist(archivoLiquidacion);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacion.getIdArchivoLiquidacionSubsidio();
    }

    /**
     * Método que se encarga de consultar la información de los archivos de
     * liquidación dado su identificador
     *
     * @param idArchivoLiquidacionSubsidio identificador del archivo de
     * liquidación de subsidio
     * @return DTO con la información del archivo de liquidación
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionId(Long idArchivoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.registrarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();
        try {
            ArchivoLiquidacionSubsidio archivoLiquidacion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_LIQUIDACION_ID, ArchivoLiquidacionSubsidio.class)
                    .setParameter("idArchivoLiquidacionSubsidio", idArchivoLiquidacionSubsidio).getSingleResult();

            archivoLiquidacionDTO.convertToDTO(archivoLiquidacion);
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoLiquidacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarPeriodoRegularRadicacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarPeriodoRegularRadicacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarPeriodoRegularRadicacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date periodoRegular = null;

        periodoRegular = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_LIQUIDACION_POR_RADICACION, Date.class)
                .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();
        periodoRegular = CalendarUtils.obtenerPrimerDiaMesTruncarHora(periodoRegular);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoRegular;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#verificarLiquidacionEnProceso()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean verificarLiquidacionEnProceso() {
        String firmaMetodo = "ConsultasModeloCore.verificarLiquidacionEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean hayLiquidacionEnProceso = Boolean.FALSE;

        try {
            // Buscar si hay una liquidacion masiva y especificas con estado diferente a cerrada
            // y de fallecimiento con estado diferentes a cerrada y en pendiente de pago de aportes
            List<EstadoProcesoLiquidacionEnum> estadosLiquidacion = new ArrayList<>();
            estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.CERRADA);
            estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);
            List<SolicitudLiquidacionSubsidio> listaSolicitudes = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_LIQUIDACION_SIN_CERRAR,
                            SolicitudLiquidacionSubsidio.class)
                    .setParameter("estadoSolicitudLiquidacion", estadosLiquidacion)
                    .setParameter("estadoSolicitudLiquidacionCerrada", estadosLiquidacion.get(0))
                    .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION).getResultList();

            hayLiquidacionEnProceso = listaSolicitudes.isEmpty() ? Boolean.FALSE : Boolean.TRUE;

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return hayLiquidacionEnProceso;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#guardarLiquidacionEspecifica(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    public RespuestaGenericaDTO guardarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.guardarLiquidacionEspecifica(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO result = new RespuestaGenericaDTO();
        SolicitudLiquidacionSubsidioModeloDTO liquidacion = new SolicitudLiquidacionSubsidioModeloDTO();

        try {
            //Guardar una nueva solicitud global
            Solicitud solGlobal = crearSolicitudGlobal(userDTO);

            // Agregar id solicitud a la respuesta
            result.setIdSolicitud(solGlobal.getIdSolicitud());

            //Guardar una solicitud de liquidacion (asociar solicitud global)
            liquidacion.setSolicitudGlobal(solGlobal);
            liquidacion.setTipoLiquidacion(liquidacionEspecifica.getTipoLiquidacion());
            liquidacion.setTipoLiquidacionEspecifica(liquidacionEspecifica.getTipoAjuste());
            liquidacion.setFechaInicial(new Date().getTime());

            if (liquidacionEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION)) {
                liquidacion.setEstadoLiquidacion(EstadoProcesoLiquidacionEnum.RADICADO);
            } else {
                liquidacion.setEstadoLiquidacion(EstadoProcesoLiquidacionEnum.EN_PROCESO);
            }

            liquidacion.setTipoEjecucionProceso(TipoEjecucionProcesoLiquidacionEnum.MANUAL);
            liquidacion.setCodigoReclamo(liquidacionEspecifica.getCodigoReclamo());
            liquidacion.setComentarioReclamo(liquidacionEspecifica.getComentariosReclamo());

            SolicitudLiquidacionSubsidio sls = liquidacion.convertToEntity();
            entityManagerCore.persist(sls);

            result.setIdSolicitudLiquidacion(sls.getIdProcesoLiquidacionSubsidio());

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        result.setOperacionExitosa(Boolean.valueOf(true));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que verifica si un periodo existe en BD
     *
     * @param periodo Periodo en formato long
     * @return 1 o 0 con la cantidad de registros en tabla
     */
    @Override
    public Boolean verificarExistenciaPeriodo(List<ValorPeriodoDTO> periodos) {
        String firmaMetodo = "ConsultasModeloCore.crearSolicitudGlobal()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean periodoExiste = Boolean.TRUE;
        //Verificar que los periodos seleccionados existan en tabla periodo
        // Verificar casos para 143 y 144, puede ser necesaria otra condicion 
        if (!periodos.isEmpty()) {
            for (ValorPeriodoDTO periodo : periodos) {
                periodoExiste = verificarExistenciaEnPeriodo(periodo.getPeriodo());
                if (!periodoExiste) {
                    break;
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoExiste;
    }

    /**
     * @param periodo
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean verificarExistenciaEnPeriodo(Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.verificarExistenciaPeriodo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean periodoExiste = Boolean.FALSE;
        try {
            Long ocurrenciaPeriodo = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_EXISTE, Long.class)
                    .setParameter("fechaPeriodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getSingleResult();

            if (ocurrenciaPeriodo == 0L) {
                return Boolean.FALSE;
            } else {
                periodoExiste = Boolean.TRUE;
            }

        } catch (NoResultException nre) {
            return Boolean.FALSE;
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodoExiste;

    }

    /**
     * Metodo para crear una solicitud global y retornar el id
     *
     * @return
     */
    private Solicitud crearSolicitudGlobal(UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.crearSolicitudGlobal()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Solicitud solGlobal = new Solicitud();

        try {
            //Guardar una nueva solicitud global
            if (userDTO != null && userDTO.getNombreUsuario() != null) {
                solGlobal.setUsuarioRadicacion(userDTO.getNombreUsuario());
            }
            solGlobal.setCanalRecepcion(CanalRecepcionEnum.WEB);
            solGlobal.setFechaCreacion(new Date());
            entityManagerCore.persist(solGlobal);
            //entityManagerCore.flush();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solGlobal;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarValorCuotaAnualYAgrariaPeriodos(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ValorPeriodoDTO consultarValorCuotaAnualYAgrariaPeriodos(Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarValorCuotaAnualYAgrariaPeriodos(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ValorPeriodoDTO valorCuotaAnualYAgraria = null;
        List<ValorPeriodoDTO> valorCuotaAnualYAgrarias = new ArrayList<>();

        try {
            valorCuotaAnualYAgrarias = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALOR_CUOTA_ANUAL_Y_PERIODO, ValorPeriodoDTO.class)
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getResultList();

            if (!valorCuotaAnualYAgrarias.isEmpty()) {
                valorCuotaAnualYAgraria = valorCuotaAnualYAgrarias.get(valorCuotaAnualYAgrarias.size() - 1);
            }
            valorCuotaAnualYAgraria.setPeriodo(periodo);
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorCuotaAnualYAgraria;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#guardarPeriodosLiquidacion(java.util.List,
     * java.lang.Long,
     * com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum)
     */
    @Override
    public Boolean guardarPeriodosLiquidacion(List<ValorPeriodoDTO> periodos, Long idSolicitudLiquidacion,
            TipoLiquidacionEspecificaEnum tipoAjuste) {
        String firmaMetodo = "ConsultasModeloCore.guardarPeriodosLiquidacion(List<ValorPeriodoDTO>, Long, TipoLiquidacionEspecificaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (ValorPeriodoDTO periodo : periodos) {
            Long idPeriodo = consultarIdPeriodoFecha(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo.getPeriodo())));

            PeriodoLiquidacion periodoLiquidacion = new PeriodoLiquidacion();
            periodoLiquidacion.setIdPeriodo(idPeriodo);
            periodoLiquidacion.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);

            //Definir el tipo de periodo
            periodoLiquidacion.setTipoPeriodo(calcularPeriodoLiquidacionEspecifica(
                    CalendarUtils.truncarHora(SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo.getPeriodo())))));

            entityManagerCore.persist(periodoLiquidacion);
            entityManagerCore.flush();

            //Guardar la ParametrizacionSubsidioAjuste (HU-143)
            if ((tipoAjuste != null) && TipoLiquidacionEspecificaEnum.VALOR_CUOTA.equals(tipoAjuste)) {
                ParametrizacionSubsidioAjuste psa = new ParametrizacionSubsidioAjuste();
                psa.setIdPeriodoLiquidacion(periodoLiquidacion.getIdPeriodoLiquidacion());
                psa.setValorCuotaAjuste(periodo.getValorPeriodo());
                psa.setValorCuotaAgrariaAjuste(periodo.getValorCuotaAgraria());
                entityManagerCore.persist(psa);
                entityManagerCore.flush();
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.valueOf(true);
    }

    /**
     * @param periodo
     * @return
     */
    private TipoPeriodoEnum calcularPeriodoLiquidacionEspecifica(Date periodo) {
        String firmaMetodo = "ConsultasModeloCore.calcularPeriodoLiquidacionEspecifica(ValorPeriodoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        /*
         * TipoPeriodoEnum tipoPeriodo = null;
         * 
         * // Calcular el primer dia del mes anterior
         * Calendar cal = Calendar.getInstance();
         * cal.add(Calendar.MONTH, -1);
         * Date fechaMesAnterior = SubsidioDateUtils.ponerFechaEnPrimerDia(cal.getTime());
         * 
         * if (CalendarUtils.truncarHora(fechaMesAnterior).equals(periodo)) {
         * tipoPeriodo = TipoPeriodoEnum.REGULAR;
         * }
         * else if (periodo.before(CalendarUtils.truncarHora(fechaMesAnterior))) {
         * tipoPeriodo = TipoPeriodoEnum.RETROACTVO;
         * }
         */
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return TipoPeriodoEnum.REGULAR;

    }

    /**
     * Consulta el valor del periodo a partir de la fecha
     *
     * @param ponerFechaEnPrimerDia
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Long consultarIdPeriodoFecha(Date fechaPeriodo) {
        String firmaMetodo = "ConsultasModeloCore.guardarPeriodosLiquidacion(List<ValorPeriodoDTO>, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idPeriodo = null;

        try {
            idPeriodo = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PERIODO_FECHA, Long.class)
                    .setParameter("fechaPeriodo", fechaPeriodo).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idPeriodo;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarEstadoSolicitudLiquidacionXNumeroRadicado(java.lang.String)
     */
    /*
     * @Override
     * 
     * @TransactionAttribute (TransactionAttributeType.REQUIRES_NEW)
     * public void actualizarEstadoSolicitudLiquidacionXNumeroRadicado(String numeroRadicado) {
     * String firmaMetodo = "ConsultasModeloCore.guardarPeriodosLiquidacion(List<ValorPeriodoDTO>, Long)";
     * logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
     * 
     * Long idSolitudLiquidacion = null;
     * 
     * try {
     * idSolitudLiquidacion = entityManagerCore
     * .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION_NUMERO_RADICADO, Long.class)
     * .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
     * } catch (NoResultException nre) {
     * logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
     * throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
     * } catch (NonUniqueResultException nue) {
     * logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
     * throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
     * } catch (Exception e) {
     * logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
     * throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
     * }
     * 
     * SolicitudLiquidacionSubsidio solicitudLiquidacion = entityManagerCore.find(SolicitudLiquidacionSubsidio.class,
     * idSolitudLiquidacion);
     * solicitudLiquidacion = entityManagerCore.merge(solicitudLiquidacion);
     * solicitudLiquidacion.setEstadoLiquidacion(EstadoProcesoLiquidacionEnum.PROCESADO);
     * 
     * logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
     * }
     */
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarFactorCuotaDiscapacidadPeriodos(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal consultarFactorCuotaDiscapacidadPeriodos(Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarValorCuotaPeriodo(Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal factorDiscapacidad = BigDecimal.ZERO;
        List<BigDecimal> factorDiscapacidads = new ArrayList<>();

        try {
            factorDiscapacidads = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_FACTOR_DISCAPACIDAD_PERIODO, BigDecimal.class)
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getResultList();
            if (!factorDiscapacidads.isEmpty()) {
                factorDiscapacidad = (BigDecimal) factorDiscapacidads.get(0);
            }
        } catch (NoResultException nre) {
            return BigDecimal.valueOf(-1);
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return factorDiscapacidad;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarValorCuotaAgrariaPeriodos(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal consultarValorCuotaAgrariaPeriodo(Long periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarValorCuotaPeriodo(Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        BigDecimal valorCuota = BigDecimal.ZERO;
        List<BigDecimal> valorCuotas = new ArrayList<>();

        try {
            valorCuotas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALOR_CUOTA_AGRARIA_PERIODO, BigDecimal.class)
                    .setParameter("periodo", SubsidioDateUtils.ponerFechaEnPrimerDia(new Date(periodo))).getResultList();
            if (!valorCuotas.isEmpty()) {
                valorCuota = (BigDecimal) valorCuotas.get(0);
            }
        } catch (NoResultException nre) {
            return BigDecimal.valueOf(-1);
        } catch (NonUniqueResultException nue) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorCuota;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#guardarPersonasLiquidacionEspecifica(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO,
     * java.lang.Long)
     */
    @SuppressWarnings({"unchecked"})
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Boolean guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, Long idSolicitudLiquidacion) {
        String firmaMetodo = "ConsultasModeloCore.guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> listaPersonas = new ArrayList<>();
        List<Long> listaPersonasBeneficiarias = new ArrayList<>();
        List<BeneficiariosAfiliadoDTO> listaBeneficiarios = new ArrayList<>();

        if (liquidacionEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)
                || liquidacionEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.RECONOCIMIENTO_DE_SUBSIDIOS)) {
            // Nivel de liquidacion empleador
            if (liquidacionEspecifica.getNivelLiquidacion().equals(TipoTipoSolicitanteEnum.EMPLEADOR)) {
                for (Long idEmpleador : liquidacionEspecifica.getListaEmpleadores()) {
                    PersonaLiquidacionEspecifica ple = new PersonaLiquidacionEspecifica();

                    ple.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                    ple.setIdEmpleador(idEmpleador);
                    entityManagerCore.persist(ple);
                }

                // inicio mantis 0259559
                for (Long idEmpleador : liquidacionEspecifica.getListaEmpresas()) {
                    PersonaLiquidacionEspecifica ple = new PersonaLiquidacionEspecifica();

                    ple.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                    ple.setIdEmpresa(idEmpleador);
                    entityManagerCore.persist(ple);
                }
                // fin mantis 0259559
            } // Nivel de liquidacion personas
            else if (liquidacionEspecifica.getNivelLiquidacion().equals(TipoTipoSolicitanteEnum.PERSONA)) {

                //Seguimiento undefined
                String listaAfi = "[";
                String listaBen = "[";
                String listaRol = "[";

                for (PersonaDTO per : liquidacionEspecifica.getListaPersonas()) {
                    listaPersonas.add(per.getIdPersona());
                    listaAfi = listaAfi + Long.toString(per.getIdPersona()) + ", ";
                }
                listaAfi = listaAfi + "]";
                logger.debug("listaPersonas: " + listaAfi + " - seguimiento undefined");

                for (BeneficiariosAfiliadoDTO per : liquidacionEspecifica.getListaAfiliados()) {
                    listaPersonasBeneficiarias.add(per.getIdBeneficiario());
                    listaBen = listaBen + Long.toString(per.getIdBeneficiario()) + ", ";
                }
                listaBen = listaBen + "]";
                logger.debug("listaPersonasBeneficiarias: " + listaBen + " - seguimiento undefined");

                // cc Seleccionar empleador para persona
                List<Long> listaRoaId = new ArrayList<>();
                for (RolAfiliadoDTO roaDto : liquidacionEspecifica.getListaRolAfiliado()) {
                    listaRoaId.add(roaDto.getIdRolAfiliado());
                    listaRol = listaRol + Long.toString(roaDto.getIdRolAfiliado()) + ", ";
                }
                listaRol = listaRol + "]";
                logger.debug("listaRoaId: " + listaRol + " - seguimiento undefined");

                List<BeneficiariosAfiliadoDTO> empresas = new ArrayList<>();

                if (listaRoaId != null && !listaRoaId.isEmpty()) {

                    // Consultar las empresas donde trabaja esta persona
                    if (listaPersonasBeneficiarias != null && !listaPersonasBeneficiarias.isEmpty()) {
                        empresas = entityManagerCore
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSULTAR_EMPRESAS_AFILIADO)
                                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
                                .setParameter("listaBeneficiarios", listaPersonasBeneficiarias)
                                .setParameter("listaRoaId", listaRoaId)
                                .getResultList();
                    } else {
                        empresas = entityManagerCore
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSULTAR_EMPRESAS_ROA)
                                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
                                .setParameter("listaRoaId", listaRoaId)
                                .getResultList();
                    }
                } else {
                    if (listaPersonasBeneficiarias != null && !listaPersonasBeneficiarias.isEmpty()) {
                        // Consultar las empresas donde trabaja esta persona
                        empresas = entityManagerCore
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSULTAR_EMPRESAS_BEN)
                                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
                                //.setParameter("listaPersonas", listaPersonas)
                                .setParameter("listaBeneficiarios", listaPersonasBeneficiarias)
                                .getResultList();
                    }
                }

                for (BeneficiariosAfiliadoDTO empresa : empresas) {
                    PersonaLiquidacionEspecifica ple = new PersonaLiquidacionEspecifica();

                    ple.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                    ple.setIdEmpleador(empresa.getIdEmpleador());
                    ple.setIdAfiliadoPrincipal(empresa.getIdAfiliadoPrincipal());
                    ple.setIdBeneficiarioDetalle(empresa.getIdBeneficiarioDetalle());

                    entityManagerCore.persist(ple);
                }
            }
        }

        /* PERSONAS: LIQUIDACION POR DEFUNCION */
        if (liquidacionEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION)) {

            listaPersonas.add(liquidacionEspecifica.getPersonaFallecida().getIdPersona());
            listaBeneficiarios = consultarBeneficiariosAfiliadoIds(listaPersonas);

            Predicate<BeneficiariosAfiliadoDTO> lista = n -> liquidacionEspecifica.getPersonaFallecida().getListaBeneficiarios().contains(n);
            lista = lista.negate();
            listaBeneficiarios.removeIf(lista);

            // Persistir en persona liquidacion especifica
            if (!listaBeneficiarios.isEmpty()) {
                for (BeneficiariosAfiliadoDTO beneficiario : listaBeneficiarios) {
                    PersonaLiquidacionEspecifica ple = new PersonaLiquidacionEspecifica();

                    ple.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                    ple.setIdEmpleador(beneficiario.getIdEmpleador());
                    ple.setIdAfiliadoPrincipal(beneficiario.getIdAfiliadoPrincipal());
                    ple.setIdBeneficiarioDetalle(beneficiario.getIdBeneficiarioDetalle());
                    ple.setIdGrupoFamiliar(beneficiario.getIdGrupoFamiliar());

                    entityManagerCore.persist(ple);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.valueOf(true);
    }

    /**
     * Metodo para consultar todos los beneficiarios del sistema
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    @SuppressWarnings("unchecked")
    public List<RolAfiliadoDTO> consultareEmpleadorPorPersonaTrabajador(Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.consultareEmpleadorPorPersonaTrabajador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RolAfiliadoDTO> empleadores = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_POR_PERSONA_TRABAJADOR)
                .setParameter("idPersona", idPersona).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return empleadores;
    }

    /**
     * Obtiene el id del afiliado a partir del id de la persona
     *
     * @param idPersona
     * @return
     */
    private Long obtenerIdAfiliadoPersona(Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.obtenerIdAfiliadoPersona()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idAfiliado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_AFILIADO_PERSONA, Long.class)
                    .setParameter("idPersona", idPersona).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return idAfiliado;

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo para consultar todos los beneficiarios del sistema
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    private List<BeneficiariosAfiliadoDTO> consultarTodosLosBeneficiarios() {
        String firmaMetodo = "ConsultasModeloCore.consultarTodosLosBeneficiarios()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> beneficiarios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_IDS_TODOS)
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE).getResultList();

        if (!beneficiarios.isEmpty()) {
            return beneficiarios;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return beneficiarios;
    }

    /**
     * Obtiene el listado de beneficiarios para un listado de empleadores dado
     *
     * @param listaEmpleadores Lista con los id de los empleadores
     * @return Listado de beneficiarios
     * @author rarboleda
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    private List<BeneficiariosAfiliadoDTO> buscarBeneficiariosEmpleadores(List<Long> listaEmpleadores) {
        String firmaMetodo = "ConsultasModeloCore.buscarBeneficiariosEmpleadores(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> beneficiarios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_IDS_EMPLEADOR)
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE).setParameter("listaEmpleadores", listaEmpleadores)
                .getResultList();

        if (!beneficiarios.isEmpty()) {
            return beneficiarios;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return beneficiarios;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarFechaDispersion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarFechaDispersion(Long idSolicitudLiquidacion) {
        String firmaMetodo = "ConsultasModeloCore.buscarBeneficiariosEmpleadores(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            SolicitudLiquidacionSubsidio solicitudLiquidacion = consultarSolicitudLiquidacion(idSolicitudLiquidacion);
            solicitudLiquidacion.setFechaDispersion(new Date());
            entityManagerCore.merge(solicitudLiquidacion);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarCondicionesPeriodo(java.util.Date)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionCondicionesSubsidioModeloDTO consultarCondicionesPeriodo(Date periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesPeriodo(Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ParametrizacionCondicionesSubsidioModeloDTO condicionesDTO = new ParametrizacionCondicionesSubsidioModeloDTO();
        try {
            ParametrizacionCondicionesSubsidio condiciones = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_CONDICION_INTERVALO,
                            ParametrizacionCondicionesSubsidio.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .setParameter("periodo", periodo).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return condicionesDTO.convertToDTO(condiciones);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarCondicionesPeriodo(java.util.Date)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionLiquidacionSubsidioModeloDTO consultarParametrosLiquidacionPeriodo(Date periodo) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesPeriodo(Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ParametrizacionLiquidacionSubsidioModeloDTO paramLiqDTO = new ParametrizacionLiquidacionSubsidioModeloDTO();
        try {
            ParametrizacionLiquidacionSubsidio parametrizacion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_LIQUIDACION_INTERVALO,
                            ParametrizacionLiquidacionSubsidio.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .setParameter("periodo", periodo).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return ParametrizacionLiquidacionSubsidioModeloDTO.convertToDTO(parametrizacion);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#guardarCondicionesEspecialesReconocimiento(com.asopagos.subsidiomonetario.dto.CondicionesEspecialesLiquidacionEspecificaDTO,
     * java.lang.Long)
     */
    @Override
    public void guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConjuntoValidacionSubsidio> conjuntosValidacion = obtenerIdsConjuntoValidaciones(condiciones);

        // Persistir los elementos de la pestaña general, que no esten
        persistirDatosPestanaGenerales(conjuntosValidacion, idSolicitudLiquidacion);

        // Con los id's del conjunto de validacion persistir en AplicacionValidacionSubsidio
        for (ConjuntoValidacionSubsidio conjuntoValidacion : conjuntosValidacion) {
            AplicacionValidacionSubsidio aplicaValidacion = new AplicacionValidacionSubsidio();
            aplicaValidacion.setIdConjuntoValidacionSubsidio(conjuntoValidacion.getIdConjuntoValidacionSubsidio());
            aplicaValidacion.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);

            // Variable para determinar si se debe guardar el estado de la condicion
            Boolean isEstadoCondicion = false;
            ConjuntoValidacionSubsidioEnum conjuntoValidacionLista = null;
            List<Object> listaEstadosCondicion = null;

            switch (conjuntoValidacion.getConjuntoValidacion()) {
                case ESTADO_EMPLEADOR:
                case ESTADO_TRABAJADOR:
                case ESTADO_APORTE:
                case ESTADO_BENEFICIARIO_PADRE:
                case ESTADO_BENEFICIARIO_HIJO:
                    aplicaValidacion.setIsValidable(Boolean.valueOf(true));
                    logger.info("setIsValidable 1 " + aplicaValidacion.getIsValidable());
                    isEstadoCondicion = true;
                    conjuntoValidacionLista = conjuntoValidacion.getConjuntoValidacion();
                    break;
                default:
                    logger.info("setIsValidable " + conjuntoValidacion.getConjuntoValidacion());
                    aplicaValidacion.setIsValidable(obtenerEstadoValidable(condiciones, conjuntoValidacion.getConjuntoValidacion()));
                    logger.info("setIsValidable " + aplicaValidacion.getIsValidable());
                    break;
            }

            // Extraer la lista correspondiente a los estados
            switch (conjuntoValidacion.getConjuntoValidacion()) {
                case ESTADO_EMPLEADOR:
                    // la coleccion se pasa a Object
                    listaEstadosCondicion = new ArrayList<>(condiciones.getEstadosEmpleador());
                    break;
                case ESTADO_TRABAJADOR:
                    listaEstadosCondicion = new ArrayList<>(condiciones.getEstadosAfiliacionTrabajador());
                    break;
                case ESTADO_APORTE:
                    listaEstadosCondicion = new ArrayList<>(condiciones.getEstadosAporteValidosTrabajador());
                    break;
                case ESTADO_BENEFICIARIO_PADRE:
                    listaEstadosCondicion = new ArrayList<>(condiciones.getEstadosAfilPadreConAfiliadoPrincipal());
                    break;
                case ESTADO_BENEFICIARIO_HIJO:
                    listaEstadosCondicion = new ArrayList<>(condiciones.getEstadosAfilHijoConAfiliadoPrincipal());
                    break;
                default:
                    break;
            }

            entityManagerCore.persist(aplicaValidacion);
            entityManagerCore.flush();

            // Persistir el estado de la condicion
            if (isEstadoCondicion) {
                guardarEstadosCondicionSubsidio(conjuntoValidacionLista, listaEstadosCondicion,
                        aplicaValidacion.getIdAplicacionValidacionSubsidio());
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param condiciones
     * @param conjuntoValidacion
     * @return
     */
    private Boolean obtenerEstadoValidable(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            ConjuntoValidacionSubsidioEnum conjuntoValidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.persistirDatosPestanaGenerales(List<ConjuntoValidacionSubsidio>, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.CONDICION_AGRICOLA)) {
            return condiciones.getEvaluarCondicionAgricola();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.RETROACTIVO_NOVEDAD)) {
            return condiciones.getEvaluarRetroactivosPorNovedades();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.RETROACTIVO_APORTE)) {
            return condiciones.getEvaluarRetroactivosPorAportes();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.TIENE_BENEFICARIOS)) {
            return condiciones.getEvaluarBeneficiarioDiferenteAConyuge();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.CAUSACION_SALARIOS)) {
            return condiciones.getEvaluarTrabajadorConMultiAfiliacion();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.TRABAJADOR_ES_EMPLEADOR)) {
            return condiciones.getEvaluarTrabajadorDistintoAEmpleador();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.DIAS_COTIZADOS_NOVEDADES)) {
            return condiciones.getEvaluarDiasCotizadosYNovedadesTrabajador();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.SALARIO)) {
            return condiciones.getEvaluarSalarioTrabajador();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_AFILIADO_PRINCIPAL_PADRE)) {
            return condiciones.getEvaluarBeneficiarioPadreEsAfiliadoPrincipal();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_EMPLEADOR_PADRE)) {
            return condiciones.getEvaluarBeneficiarioPadreEsPersonaNatural();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTROS_APORTES_PADRE)) {
            return condiciones.getEvaluarBeneficiarioPadreTieneOtrosAportes();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_AFILIADO_PRINCIPAL_HIJO)) {
            return condiciones.getEvaluarBeneficiarioHijoEsAfiliadoPrincipal();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_EMPLEADOR_HIJO)) {
            return condiciones.getEvaluarBeneficiarioHijoEsPersonaNatural();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTROS_APORTES_HIJO)) {
            return condiciones.getEvaluarBeneficiarioHijoTieneOtrosAportes();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTRAS_PERSONAS_PADRE)) {
            return condiciones.getEvaluarBenefiarioTipoPadreDeOtrasPersonas();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTRAS_PERSONAS_HIJO)) {
            return condiciones.getEvaluarBenefiarioTipoHijoDeOtrasPersonas();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.CONDICION_INVALIDEZ_PADRE)) {
            return condiciones.getEvaluarBeneficiarioTipoPadreConCondicionInvalidez();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.CONDICION_INVALIDEZ_HIJO)) {
            return condiciones.getEvaluarBeneficiarioTipoHijoConCondicionInvalidez();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.EDAD_BENEFICIARIO_PADRE)) {
            return condiciones.getEvaluarEdadBeneficiarioTipoPadre();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.EDAD_BENEFICIARIO_HIJO)) {
            return condiciones.getEvaluarEdadBeneficiarioTipoHijo();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_EMPRESA)) {
            return condiciones.getValidarCondicionesAportanteRegular();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_TRABAJADOR)) {
            return condiciones.getValidarCondicionesTrabajador();
        }
        if (conjuntoValidacion.equals(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_BENEFICIARIOS)) {
            return condiciones.getValidarCondicionesBeneficiario();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * @param conjuntosValidacion
     * @param idSolicitudLiquidacion
     */
    private void persistirDatosPestanaGenerales(List<ConjuntoValidacionSubsidio> conjuntosValidacion, Long idSolicitudLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.persistirDatosPestanaGenerales(List<ConjuntoValidacionSubsidio>, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConjuntoValidacionSubsidio> cvList = conjuntosValidacion;
        ConjuntoValidacionSubsidio cvs = null;

        /* Si CONDICION_AGRICOLA no esta presente persisitir con 1 */
        if (!cvList.stream().filter(conjunto -> conjunto.getConjuntoValidacion().equals(ConjuntoValidacionSubsidioEnum.CONDICION_AGRICOLA))
                .findFirst().isPresent()) {
            // Consultar los datos de la persona        
            try {
                cvs = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONJUNTO_VALIDACION_X_NOMBRE, ConjuntoValidacionSubsidio.class)
                        .setParameter("tipoValidacion", ConjuntoValidacionSubsidioEnum.CONDICION_AGRICOLA).getSingleResult();
            } catch (NoResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            } catch (NonUniqueResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
            logger.info("Si CONDICION_AGRICOLA no esta presente persisitir con 1");
            AplicacionValidacionSubsidio aplicaValidacion = new AplicacionValidacionSubsidio();
            aplicaValidacion.setIdConjuntoValidacionSubsidio(cvs.getIdConjuntoValidacionSubsidio());
            aplicaValidacion.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
            aplicaValidacion.setIsValidable(Boolean.valueOf(true));
            entityManagerCore.persist(aplicaValidacion);
        }

        /* Si RETROACTIVO_NOVEDAD no esta presente persisitir con 1 */
        if (!cvList.stream().filter(conjunto -> conjunto.getConjuntoValidacion().equals(ConjuntoValidacionSubsidioEnum.RETROACTIVO_NOVEDAD))
                .findFirst().isPresent()) {
            // Consultar los datos de la persona        
            try {
                cvs = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONJUNTO_VALIDACION_X_NOMBRE, ConjuntoValidacionSubsidio.class)
                        .setParameter("tipoValidacion", ConjuntoValidacionSubsidioEnum.RETROACTIVO_NOVEDAD).getSingleResult();
            } catch (NoResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            } catch (NonUniqueResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
            logger.info("Si RETROACTIVO_NOVEDAD no esta presente persisitir con 1");
            AplicacionValidacionSubsidio aplicaValidacion = new AplicacionValidacionSubsidio();
            aplicaValidacion.setIdConjuntoValidacionSubsidio(cvs.getIdConjuntoValidacionSubsidio());
            aplicaValidacion.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
            aplicaValidacion.setIsValidable(Boolean.valueOf(true));
            entityManagerCore.persist(aplicaValidacion);
        }

        /* Si RETROACTIVO_APORTE no esta presente persisitir con 1 */
        if (!cvList.stream().filter(conjunto -> conjunto.getConjuntoValidacion().equals(ConjuntoValidacionSubsidioEnum.RETROACTIVO_APORTE))
                .findFirst().isPresent()) {
            // Consultar los datos de la persona        
            try {
                cvs = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONJUNTO_VALIDACION_X_NOMBRE, ConjuntoValidacionSubsidio.class)
                        .setParameter("tipoValidacion", ConjuntoValidacionSubsidioEnum.RETROACTIVO_APORTE).getSingleResult();
            } catch (NoResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            } catch (NonUniqueResultException e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
            } catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
            logger.info("Si RETROACTIVO_APORTE no esta presente persisitir con 1");
            AplicacionValidacionSubsidio aplicaValidacion = new AplicacionValidacionSubsidio();
            aplicaValidacion.setIdConjuntoValidacionSubsidio(cvs.getIdConjuntoValidacionSubsidio());
            aplicaValidacion.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
            aplicaValidacion.setIsValidable(Boolean.valueOf(true));
            entityManagerCore.persist(aplicaValidacion);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void guardarEstadosCondicionSubsidio(ConjuntoValidacionSubsidioEnum conjuntoValidacionLista, List<Object> listaEstadosCondicion,
            Long idAplicacionValidacionSubsidio) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        EstadoCondicionValidacionSubsidio ecvs = new EstadoCondicionValidacionSubsidio();

        ecvs.setIdAplicacionValidacionSubsidio(idAplicacionValidacionSubsidio);

        switch (conjuntoValidacionLista) {
            case ESTADO_EMPLEADOR:
                List<EstadoEmpleadorEnum> estadoEmpleador = listaEstadosCondicion.stream()
                        .map(estadoCondicion -> (EstadoEmpleadorEnum) estadoCondicion).collect(Collectors.toList());
                for(EstadoEmpleadorEnum estado : estadoEmpleador){
                    logger.info("ESTADO EMPLEADOR: " + estado.toString());
                }
                ecvs.setIsActivo(estadoEmpleador.contains(EstadoEmpleadorEnum.ACTIVO));
                ecvs.setIsInactivo(estadoEmpleador.contains(EstadoEmpleadorEnum.INACTIVO));
                ecvs.setIsNFConAporteSinAfiliacion(estadoEmpleador.contains(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES));
                ecvs.setIsNFConInformacion(estadoEmpleador.contains(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION));
                ecvs.setIsNFRetiradoConAportes(estadoEmpleador.contains(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES));
                ecvs.setIsSinEstado(estadoEmpleador.contains(EstadoEmpleadorEnum.SIN_ESTADO));
                break;
            case ESTADO_TRABAJADOR:
                List<EstadoAfiliadoEnum> estadosTrabajador = listaEstadosCondicion.stream()
                        .map(estadoCondicion -> (EstadoAfiliadoEnum) estadoCondicion).collect(Collectors.toList());

                ecvs.setIsActivo(estadosTrabajador.contains(EstadoAfiliadoEnum.ACTIVO));
                ecvs.setIsInactivo(estadosTrabajador.contains(EstadoAfiliadoEnum.INACTIVO));
                ecvs.setIsNFConAporteSinAfiliacion(
                        estadosTrabajador.contains(EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES));
                ecvs.setIsNFConInformacion(estadosTrabajador.contains(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION));
                ecvs.setIsNFRetiradoConAportes(estadosTrabajador.contains(EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES));
                ecvs.setIsSinEstado(estadosTrabajador.contains(EstadoAfiliadoEnum.SIN_ESTADO));
                break;
            case ESTADO_APORTE:
                List<EstadoRegistroAportesArchivoEnum> estadosAporte = listaEstadosCondicion.stream()
                        .map(estadoCondicion -> (EstadoRegistroAportesArchivoEnum) estadoCondicion).collect(Collectors.toList());

                ecvs.setIsOK(estadosAporte.contains(EstadoRegistroAportesArchivoEnum.OK));
                ecvs.setIsNoOk(estadosAporte.contains(EstadoRegistroAportesArchivoEnum.NO_OK));
                ecvs.setIsNoValidadoBD(estadosAporte.contains(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD));
                break;
            case ESTADO_BENEFICIARIO_PADRE:
                List<EstadoAfiliadoEnum> estadosBenePadre = listaEstadosCondicion.stream()
                        .map(estadoCondicion -> (EstadoAfiliadoEnum) estadoCondicion).collect(Collectors.toList());

                ecvs.setIsActivo(estadosBenePadre.contains(EstadoAfiliadoEnum.ACTIVO));
                ecvs.setIsInactivo(estadosBenePadre.contains(EstadoAfiliadoEnum.INACTIVO));
                ecvs.setIsSinEstado(estadosBenePadre.contains(EstadoAfiliadoEnum.SIN_ESTADO));
                break;
            case ESTADO_BENEFICIARIO_HIJO:
                List<EstadoAfiliadoEnum> estadosBeneHijo = listaEstadosCondicion.stream()
                        .map(estadoCondicion -> (EstadoAfiliadoEnum) estadoCondicion).collect(Collectors.toList());

                ecvs.setIsActivo(estadosBeneHijo.contains(EstadoAfiliadoEnum.ACTIVO));
                ecvs.setIsInactivo(estadosBeneHijo.contains(EstadoAfiliadoEnum.INACTIVO));
                ecvs.setIsSinEstado(estadosBeneHijo.contains(EstadoAfiliadoEnum.SIN_ESTADO));
                break;
            default:
                break;
        }

        entityManagerCore.persist(ecvs);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param condiciones
     * @return ids del conjunto de validacion asociados
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<ConjuntoValidacionSubsidio> obtenerIdsConjuntoValidaciones(CondicionesEspecialesLiquidacionEspecificaDTO condiciones) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Extraer las condiciones asociadas a la liquidacion por reconocimiento
        List<ConjuntoValidacionSubsidioEnum> conjuntoValidaciones = obtenerConjuntoValidaciones(condiciones);

        // Obtener los id's de las condiciones
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        if (!conjuntoValidaciones.isEmpty()) {
            return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_CONJUNTO_VALIDACIONES)
                    .setParameter("conjuntoValidaciones", conjuntoValidaciones).getResultList();
        }

        return Collections.emptyList();
    }

    /**
     * @param condiciones
     * @return
     */
    private List<ConjuntoValidacionSubsidioEnum> obtenerConjuntoValidaciones(CondicionesEspecialesLiquidacionEspecificaDTO condiciones) {
        String firmaMetodo = "SubsidioMonetarioBusiness.guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ConjuntoValidacionSubsidioEnum> conjuntoValidaciones = new ArrayList<>();

        /* Recorrer todos los campos del DTO de condiciones y si esta en 0 ir agregandolo a la lista */
        if (condiciones != null) {
            if (condiciones.getEvaluarCondicionAgricola() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.CONDICION_AGRICOLA);
            }
            if (condiciones.getEvaluarRetroactivosPorNovedades() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.RETROACTIVO_NOVEDAD);
            }
            if (condiciones.getEvaluarRetroactivosPorAportes() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.RETROACTIVO_APORTE);
            }
            if ((condiciones.getEstadosEmpleador() != null) && !condiciones.getEstadosEmpleador().isEmpty()) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.ESTADO_EMPLEADOR);
            }
            if ((condiciones.getEstadosAfiliacionTrabajador() != null) && !condiciones.getEstadosAfiliacionTrabajador().isEmpty()) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.ESTADO_TRABAJADOR);
            }
            if (condiciones.getEvaluarBeneficiarioDiferenteAConyuge() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.TIENE_BENEFICARIOS);
            }
            if (condiciones.getEvaluarTrabajadorConMultiAfiliacion() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.CAUSACION_SALARIOS);
            }
            if (condiciones.getEvaluarTrabajadorDistintoAEmpleador() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.TRABAJADOR_ES_EMPLEADOR);
            }
            if ((condiciones.getEstadosAporteValidosTrabajador() != null) && !condiciones.getEstadosAporteValidosTrabajador().isEmpty()) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.ESTADO_APORTE);
            }
            if (condiciones.getEvaluarDiasCotizadosYNovedadesTrabajador() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.DIAS_COTIZADOS_NOVEDADES);
            }
            if ((condiciones.getEvaluarSalarioTrabajador() != null)) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.SALARIO);
            }
            if ((condiciones.getEstadosAfilPadreConAfiliadoPrincipal() != null)
                    && !condiciones.getEstadosAfilPadreConAfiliadoPrincipal().isEmpty()) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.ESTADO_BENEFICIARIO_PADRE);
            }
            if ((condiciones.getEstadosAfilHijoConAfiliadoPrincipal() != null)
                    && !condiciones.getEstadosAfilHijoConAfiliadoPrincipal().isEmpty()) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.ESTADO_BENEFICIARIO_HIJO);
            }
            if (condiciones.getEvaluarBeneficiarioPadreEsAfiliadoPrincipal() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_AFILIADO_PRINCIPAL_PADRE);
            }
            if (condiciones.getEvaluarBeneficiarioPadreEsPersonaNatural() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_EMPLEADOR_PADRE);
            }
            if (condiciones.getEvaluarBeneficiarioPadreTieneOtrosAportes() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTROS_APORTES_PADRE);
            }
            if (condiciones.getEvaluarBeneficiarioHijoEsAfiliadoPrincipal() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_AFILIADO_PRINCIPAL_HIJO);
            }
            if (condiciones.getEvaluarBeneficiarioHijoEsPersonaNatural() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_EMPLEADOR_HIJO);
            }
            if (condiciones.getEvaluarBeneficiarioHijoTieneOtrosAportes() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTROS_APORTES_HIJO);
            }
            if (condiciones.getEvaluarBenefiarioTipoPadreDeOtrasPersonas() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTRAS_PERSONAS_PADRE);
            }
            if (condiciones.getEvaluarBenefiarioTipoHijoDeOtrasPersonas() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.BENEFICIARIO_OTRAS_PERSONAS_HIJO);
            }
            if (condiciones.getEvaluarBeneficiarioTipoPadreConCondicionInvalidez() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.CONDICION_INVALIDEZ_PADRE);
            }
            if (condiciones.getEvaluarBeneficiarioTipoHijoConCondicionInvalidez() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.CONDICION_INVALIDEZ_HIJO);
            }
            if (condiciones.getEvaluarEdadBeneficiarioTipoPadre() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.EDAD_BENEFICIARIO_PADRE);
            }
            if (condiciones.getEvaluarEdadBeneficiarioTipoHijo() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.EDAD_BENEFICIARIO_HIJO);
            }
            if (condiciones.getValidarCondicionesAportanteRegular() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_EMPRESA);
            }
            if (condiciones.getValidarCondicionesTrabajador() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_TRABAJADOR);
            }
            if (condiciones.getValidarCondicionesBeneficiario() != null) {
                conjuntoValidaciones.add(ConjuntoValidacionSubsidioEnum.PERMITIR_EVALUAR_LIQUIDACION_BENEFICIARIOS);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return conjuntoValidaciones;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajador(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion,
            TipoLiquidacionEspecificaEnum tipoLiquidacion) {
        String firmaMetodo = "SubsidioMonetarioBusiness.seleccionarPersonaSubsidioFallecimientoTrabajador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //Se consulta el Id de la persona por Tipo y Numero Documento.
        Long idPersona = this.consultarIdPersonaTipoNumeroDocumento(tipoIdentificacion, numeroIdentificacion);

        // Obtener el detalle de la persona
        PersonaFallecidaTrabajadorDTO persona = consultarDetalleTrabajador(idPersona);

        //CC703 - Elimina CASO 1: si en la consulta anterior no encuentra la persona relacionada a una novedad, esto indica que
        //sigue activo en la caja
//        if(persona == null && TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE.equals(tipoLiquidacion)){
//            persona = new PersonaFallecidaTrabajadorDTO();
//            persona.setActivoEnCaja(Boolean.TRUE);
//            return persona;
//        }else 
        if (persona == null) {
            persona = new PersonaFallecidaTrabajadorDTO();
        }

        // 2: Para defuncion beneficiario
        Byte tipo = tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE) ? Byte.valueOf((byte) 1)
                : Byte.valueOf((byte) 2);

        // Ejecutar el SP para determinar las condiciones que cumple
        logger.info("idPersona "+idPersona+" tipo "+tipo);
        StoredProcedureQuery storedProcedure = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_CONDICIONES_TRABAJADOR_FALLECIDO);
        storedProcedure.registerStoredProcedureParameter("iIdPersona", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("iTipo", Byte.class, ParameterMode.IN);
        storedProcedure.setParameter("iIdPersona", idPersona);
        storedProcedure.setParameter("iTipo", tipo);

        //Se reciben los parametros de salida del Stored procedure
        storedProcedure.registerStoredProcedureParameter("bLiquidacionEnProceso", Boolean.class, ParameterMode.OUT);
        //CC703 Se elimina Caso 1: storedProcedure.registerStoredProcedureParameter("bEstadoActivoEnCaja", Boolean.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("bDiaAnteriorFechaDefuncionActivo", Boolean.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("bRangoFallecimientoSolitudValido", Boolean.class, ParameterMode.OUT);
        //Caso 4:
        storedProcedure.registerStoredProcedureParameter("bMotivoDesafiliaCanalPresencial", Boolean.class, ParameterMode.OUT);
        //CC703 Se elimina Caso 1 506: storedProcedure.registerStoredProcedureParameter("bAfiliadoPrincipalActivo", Boolean.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("bTieneBeneficiarioDistintoConyuge", Boolean.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("bTrabajadorActivoAlFallecerBeneficiario", Boolean.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("bMasDeUnBeneficiarioFallecido", Boolean.class, ParameterMode.OUT);
        storedProcedure.execute();

        //persona.setLiquidacionEnProceso((Boolean) storedProcedure.getOutputParameterValue("bLiquidacionEnProceso"));
        persona.setLiquidacionEnProceso(verificarLiquidacionEnProceso());

        // Setear las validaciones realizadas desde el Stored Procedure para Fallecimiento de Trabajador
        if (tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE)) {
            //persona.setActivoEnCaja((Boolean) storedProcedure.getOutputParameterValue("bEstadoActivoEnCaja"));
            //persona.setActivoEnCaja(Boolean.FALSE); // TODO la novedad no Inactiva al afiliado, siempre va a estar activo
            persona.setDiaAnteriorFechaDefuncionActivo(
                    (Boolean) storedProcedure.getOutputParameterValue("bDiaAnteriorFechaDefuncionActivo"));
            //TODO Control de Cambios novedad de fallecimiento actualizacion de fecha de fallecimiento pendiente
            persona.setRangoFallecimientoSolitudValido(
                    (Boolean) storedProcedure.getOutputParameterValue("bRangoFallecimientoSolitudValido"));
            //persona.setRangoFallecimientoSolitudValido(Boolean.FALSE);

            persona.setMotivoFallecimientoCanalPresencial(
                    (Boolean) storedProcedure.getOutputParameterValue("bMotivoDesafiliaCanalPresencial"));

            //persona.setMotivoFallecimientoCanalPresencial(Boolean.FALSE);
            // Si ninguna condicion de NO procedencia de la liquidacion se cumple, buscar los beneficiarios asociados al trabajador
            if (!persona.getLiquidacionEnProceso() && persona.getDiaAnteriorFechaDefuncionActivo()
                    && !persona.getRangoFallecimientoSolitudValido()) {
                List<BeneficiariosAfiliadoDTO> listaBeneficiarios = consultarBeneficiariosAfiliado(idPersona);

                persona.setListaBeneficiarios(listaBeneficiarios);
            } else {
                persona.setListaBeneficiarios(Collections.emptyList());
            }
        } // Setear las validaciones realizadas desde el Stored Procedure para Fallecimiento de Beneficiario
        else if (tipoLiquidacion.equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO)) {
            //CC 703 - se elimina Caso 1 : persona.setAfiliadoPrincipalActivo((Boolean) storedProcedure.getOutputParameterValue("bAfiliadoPrincipalActivo"));
            persona.setTieneBeneficiarioDistintoConyuge(
                    (Boolean) storedProcedure.getOutputParameterValue("bTieneBeneficiarioDistintoConyuge"));
//            persona.setTrabajadorActivoAlFallecerBeneficiario(
//                    (Boolean) storedProcedure.getOutputParameterValue("bTrabajadorActivoAlFallecerBeneficiario"));
            persona.setMasDeUnBeneficiarioFallecido((Boolean) storedProcedure.getOutputParameterValue("bMasDeUnBeneficiarioFallecido"));

            // Si ninguna condicion de NO procedencia de la liquidacion se cumple, buscar los beneficiarios asociados al trabajador
            if (!persona.getLiquidacionEnProceso() //&& !persona.getAfiliadoPrincipalActivo()
                    && !persona.getTieneBeneficiarioDistintoConyuge() //&& !persona.getTrabajadorActivoAlFallecerBeneficiario()
                    && !persona.getMasDeUnBeneficiarioFallecido()) {

                List<BeneficiariosAfiliadoDTO> listaBeneficiarios = consultarBeneficiariosAfiliado(idPersona);
                persona.setListaBeneficiarios(listaBeneficiarios);
            } else {
                persona.setListaBeneficiarios(Collections.emptyList());
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return persona;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Long consultarIdPersonaTipoNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarPersonaTipoNumeroDocumento(tipoIdentificacion, numeroIdentificacion)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Long idPersona = null;
        // Consultar los datos de la persona        
        try {
            BigInteger perId = (BigInteger) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name()).getSingleResult();
            idPersona = perId.longValue();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idPersona;
    }

    /**
     * Consultar el detalle de la persona asociado
     *
     * @param idPersona
     * @return Detalle de la persona
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private PersonaFallecidaTrabajadorDTO consultarDetalleTrabajadorFallecido(Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetalleTrabajadorFallecido(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PersonaFallecidaTrabajadorDTO persona = new PersonaFallecidaTrabajadorDTO();

        // Consultar los datos de la persona        
        try {
            persona = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_TRABAJADOR_FALLECIDO, PersonaFallecidaTrabajadorDTO.class)
                    .setParameter("idPersona", idPersona).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return persona;
    }

    /**
     * Consultar el detalle de la persona asociado
     *
     * @param idPersona
     * @return Detalle de la persona
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private PersonaFallecidaTrabajadorDTO consultarDetalleTrabajador(Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.consultarDetalleTrabajadorFallecido(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PersonaFallecidaTrabajadorDTO persona = new PersonaFallecidaTrabajadorDTO();

        // Consultar los datos de la persona        
        try {
            persona = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_TRABAJADOR, PersonaFallecidaTrabajadorDTO.class)
                    .setParameter("idPersona", idPersona).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return persona;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#actualizarDesembolsoSubsidioLiquidacionFallecimiento(java.lang.String,
     * java.lang.Boolean,
     * com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso) {
        String firmaMetodo = "SubsidioMonetarioBusiness.actualizarDesembolsoSubsidioLiquidacionFallecimiento(String,Boolean,ModoDesembolsoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idSolicitudLiquidacion = consultarIdSolicitud(numeroRadicacion);
            SolicitudLiquidacionSubsidio solicitudLiquidacion = consultarSolicitudLiquidacion(idSolicitudLiquidacion);

            solicitudLiquidacion.setConsideracionAporteDesembolso(consideracionAportes);
            solicitudLiquidacion.setModoDesembolso(tipoDesembolso);
            entityManagerCore.merge(solicitudLiquidacion);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarCondicionesBeneficiarioFallecido(com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public PersonaFallecidaTrabajadorDTO consultarCondicionesBeneficiarioFallecido(PersonaFallecidaTrabajadorDTO persona) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesBeneficiarioFallecido(PersonaFallecidaTrabajadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PersonaFallecidaTrabajadorDTO per = persona;

        // Tomar los beneficiarios que se encontraron para esta persona
        List<BeneficiariosAfiliadoDTO> beneficiarios = persona.getListaBeneficiarios();

        for (BeneficiariosAfiliadoDTO ben : beneficiarios) {
            // Ejecutar el SP para determinar las condiciones que cumple
            StoredProcedureQuery storedProcedure = entityManagerCore
                    .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_CONDICIONES_BENEFICIARIO_FALLECIDO);
            storedProcedure.registerStoredProcedureParameter("iIdBeneficiario", Long.class, ParameterMode.IN);
            storedProcedure.setParameter("iIdBeneficiario", ben.getIdBeneficiario());

            // Registrar parametros de salida del Stored Procedure
            storedProcedure.registerStoredProcedureParameter("bEstadoDistintoDeActivo", Boolean.class, ParameterMode.OUT);
            storedProcedure.registerStoredProcedureParameter("bRangoValidoDiasParametrizacion", Boolean.class, ParameterMode.OUT);
            storedProcedure.registerStoredProcedureParameter("bMotivoFallecimientoCanalPresencial", Boolean.class, ParameterMode.OUT);
            storedProcedure.execute();

            // Setear las condiciones por beneficiario
            ben.setEstadoDistintoDeActivo((Boolean) storedProcedure.getOutputParameterValue("bEstadoDistintoDeActivo"));
            //ben.setRangoValidoDiasParametrizacion((Boolean) storedProcedure.getOutputParameterValue("bRangoValidoDiasParametrizacion"));
            //HU 317-506 : CASO 6
            ben.setRangoValidoDiasParametrizacion(validarRangoDeDiasCaso6Hu506(ben.getIdBeneficiario()));

            ben.setMotivoFallecimientoCanalPresencial(
                    (Boolean) storedProcedure.getOutputParameterValue("bMotivoFallecimientoCanalPresencial"));

            //ben.setMotivoFallecimientoCanalPresencial(Boolean.FALSE);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return per;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarNombresCajas()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, String> consultarNombresCajas() {
        String firmaMetodo = "ConsultasModeloCore.consultarNombresCajas()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<CajaCompensacion> cajasCompensacion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CAJAS_DE_COMPENSACION, CajaCompensacion.class).getResultList();

            Map<String, String> infoCajas = new HashMap<String, String>();
            for (CajaCompensacion cajaCompensacion : cajasCompensacion) {
                infoCajas.put(cajaCompensacion.getCodigo(), cajaCompensacion.getNombre());
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return infoCajas;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarHistoricoLiquidacionFallecimiento(java.lang.Long,
     * java.lang.Long, java.lang.Long,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, javax.ws.rs.core.UriInfo,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ResultadoHistoricoLiquidacionFallecimientoDTO> consultarHistoricoLiquidacionFallecimiento(Long periodoRegular,
            Long fechaInicio, Long fechaFin, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroRadicacion, UriInfo uri, HttpServletResponse response) {

        String firmaMetodo = "ConsultasModeloCore.consultarHistoricoLiquidacionFallecimiento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String porcentaje = "%";
        final String fechaInicioPorDefecto = "1950-01-01";

        Query query = null;
        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);

        Date fechaPeriodoRegular = new Date(periodoRegular);
        queryBuilder.addParam("periodo", fechaPeriodoRegular);
        String numeroRadicacionFiltro = (numeroRadicacion == null) ? porcentaje + porcentaje : porcentaje + numeroRadicacion + porcentaje;
        queryBuilder.addParam("numeroRadicacion", numeroRadicacionFiltro);
        Date fechaInicial = (fechaInicio == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioPorDefecto)
                : CalendarUtils.truncarHora(new Date(fechaInicio));
        queryBuilder.addParam("fechaInicio", fechaInicial);
        Date fechaFinal = (fechaFin == null) ? CalendarUtils.truncarHoraMaxima(new Date())
                : CalendarUtils.truncarHoraMaxima(new Date(fechaFin));
        queryBuilder.addParam("fechaFin", fechaFinal);
        queryBuilder.addParam("tipoIdentificacion", tipoIdentificacion == null ? null : tipoIdentificacion.name());
        queryBuilder.addParam("numeroIdentificacion", numeroIdentificacion);

        //Se comenta para que el parametro de ordenamiento se envíe por la url
//        List<String> camposOrden = new ArrayList<>();
//        camposOrden.add("fechaRadicacion");
//        queryBuilder.addOrderByParameters(camposOrden);
        query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_LIQUIDACION_ESPECIFICA_FALLECIMIENTO, null);

        try {
            List<Object[]> registrosLiquidaciones = query.getResultList();

            List<ResultadoHistoricoLiquidacionFallecimientoDTO> liquidaciones = new ArrayList<>();
            //Se obtienen los números de radicación de las solicitudes por defunción de beneficiario
            List<String> numerosRadicacion = obtenerNumerosRadicacion(registrosLiquidaciones);

            Map<String, List<ValuePersonaFallecimiento>> beneficiariosFallecidos = new HashMap<>();
            if (!numerosRadicacion.isEmpty()) {
                beneficiariosFallecidos = obtenerBeneficiariosFallecidos(numerosRadicacion);
            }

            for (Object[] registroLiquidacion : registrosLiquidaciones) {
                //Se evalua el caso en el que la solicitud no se ha procesado
                ResultadoHistoricoLiquidacionFallecimientoDTO resultadoDTO = new ResultadoHistoricoLiquidacionFallecimientoDTO();

                resultadoDTO.setNumeroRadicacion(registroLiquidacion[0].toString());
                resultadoDTO.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(registroLiquidacion[2].toString()));
                resultadoDTO.setNumeroIdentificacionAfiliado(registroLiquidacion[3].toString());
                resultadoDTO.setNombreAfiliado(registroLiquidacion[4].toString());
                resultadoDTO.setMontoDispersado(BigDecimal.valueOf(Double.parseDouble(registroLiquidacion[6].toString())));
                resultadoDTO.setFechaRadicacion((Date) registroLiquidacion[7]);
                resultadoDTO.setFechaDispersion(registroLiquidacion[8] == null ? null
                        : (Date) registroLiquidacion[8]);
                resultadoDTO.setEstado(ResultadoProcesoEnum.valueOf(registroLiquidacion[9].toString()));

                List<TipoIdentificacionEnum> tiposIdentificacionFallecidos = new ArrayList<>();
                List<String> numerosIdentificacionFallecidos = new ArrayList<>();
                List<String> nombresFallecidos = new ArrayList<>();

                if (TipoLiquidacionEspecificaEnum.valueOf(registroLiquidacion[1].toString())
                        .equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO)) {
                    //Se busca la información de los beneficiarios fallecidos y se añade al resultado
                    if (beneficiariosFallecidos.containsKey(registroLiquidacion[0].toString())) {
                        List<ValuePersonaFallecimiento> fallecidosSolicitud = beneficiariosFallecidos
                                .get(registroLiquidacion[0].toString());

                        resultadoDTO.setPeriodoFallecimiento(fallecidosSolicitud.get(0).getFecha() == null ? null
                                : SubsidioDateUtils.ponerFechaEnPrimerDia(fallecidosSolicitud.get(0).getFecha()));

                        for (ValuePersonaFallecimiento valuePersonaFallecimiento : fallecidosSolicitud) {
                            tiposIdentificacionFallecidos.add(valuePersonaFallecimiento.getTipoIdentificacion());
                            numerosIdentificacionFallecidos.add(valuePersonaFallecimiento.getNumeroIdentificacion());
                            nombresFallecidos.add(valuePersonaFallecimiento.getNombre());
                        }
                    }
                } else {
                    resultadoDTO.setPeriodoFallecimiento(registroLiquidacion[5] == null ? null
                            : SubsidioDateUtils.ponerFechaEnPrimerDia(
                                    CalendarUtils.darFormatoYYYYMMDDGuionDate(registroLiquidacion[5].toString())));

                    tiposIdentificacionFallecidos.add(TipoIdentificacionEnum.valueOf(registroLiquidacion[2].toString()));
                    numerosIdentificacionFallecidos.add(registroLiquidacion[3].toString());
                    nombresFallecidos.add(registroLiquidacion[4].toString());
                }
                resultadoDTO.setTiposIdentificacionFallecidos(tiposIdentificacionFallecidos);
                resultadoDTO.setNumerosIdentificacionFallecidos(numerosIdentificacionFallecidos);
                resultadoDTO.setNombresFallecidos(nombresFallecidos);

                liquidaciones.add(resultadoDTO);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return (liquidaciones.isEmpty()) ? null : liquidaciones;

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener los números de radicación para los registros
     * asociados a las liquidaciones
     *
     * @param registrosLiquidaciones registros históricos de liquidaciones
     * @return Lista de números de radicado
     * @author rlopez
     */
    private List<String> obtenerNumerosRadicacion(List<Object[]> registrosLiquidaciones) {
        List<String> numerosRadicacion = new ArrayList<>();
        for (Object[] registroLiquidacion : registrosLiquidaciones) {
            if (TipoLiquidacionEspecificaEnum.valueOf(registroLiquidacion[1].toString())
                    .equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO)) {
                numerosRadicacion.add(registroLiquidacion[0].toString());
            }
        }
        return numerosRadicacion;
    }

    /**
     * Método que permite obtener la información de los beneficiarios fallecidos
     * para las solicitudes de liquidación definidas
     *
     * @param numeroRadicacion Valor del número de radicación
     * @return Mapa con la información de los fallecidos
     * @author rlopez
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Map<String, List<ValuePersonaFallecimiento>> obtenerBeneficiariosFallecidos(List<String> numerosRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.obtenerBeneficiariosFallecidos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<Object[]> registrosBeneficiarios = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_LIQUIDACION_FALLECIMIENTOS_POR_RADICADOS)
                    .setParameter("numerosRadicacion", numerosRadicacion).getResultList();

            Map<String, List<ValuePersonaFallecimiento>> beneficiariosFallecimiento = agruparBeneficiariosFallecimiento(
                    registrosBeneficiarios);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return beneficiariosFallecimiento;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que se encarga de agrupar los beneficiarios fallecidos por la
     * solicitud de liquidacion
     *
     * @param registrosBeneficiarios Información de los beneficiarios
     * @return Mapa con la información de los beneficiarios
     * @author rlopez
     */
    private Map<String, List<ValuePersonaFallecimiento>> agruparBeneficiariosFallecimiento(List<Object[]> registrosBeneficiarios) {
        Map<String, List<ValuePersonaFallecimiento>> beneficiariosFallecimiento = new HashMap<>();

        for (Object[] registroBeneficiario : registrosBeneficiarios) {
            ValuePersonaFallecimiento infoBeneficiario = new ValuePersonaFallecimiento(
                    TipoIdentificacionEnum.valueOf(registroBeneficiario[1].toString()), registroBeneficiario[2].toString(),
                    registroBeneficiario[3].toString(),
                    registroBeneficiario[4] == null ? null
                            : SubsidioDateUtils
                                    .ponerFechaEnPrimerDia(CalendarUtils.darFormatoYYYYMMDDGuionDate(registroBeneficiario[4].toString())));

            if (!beneficiariosFallecimiento.containsKey(registroBeneficiario[0].toString())) {
                List<ValuePersonaFallecimiento> infoBeneficiarios = new ArrayList<>();

                infoBeneficiarios.add(infoBeneficiario);
                beneficiariosFallecimiento.put(registroBeneficiario[0].toString(), infoBeneficiarios);
            } else {
                beneficiariosFallecimiento.get(registroBeneficiario[0].toString()).add(infoBeneficiario);
            }
        }
        return beneficiariosFallecimiento;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarParametrizacionLiqEspecifica(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public LiquidacionEspecificaDTO consultarParametrizacionLiqEspecifica(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarHistoricoLiquidacionFallecimiento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        LiquidacionEspecificaDTO liqEspecifica = new LiquidacionEspecificaDTO();

        try {
            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();

            liqEspecifica.setTipoLiquidacion(solicitudLiquidacionSubsidio.getTipoLiquidacion());
            liqEspecifica.setTipoAjuste(solicitudLiquidacionSubsidio.getTipoLiquidacionEspecifica());
            liqEspecifica.setCodigoReclamo(solicitudLiquidacionSubsidio.getCodigoReclamo());
            liqEspecifica.setComentariosReclamo(solicitudLiquidacionSubsidio.getComentarioReclamo());

            liqEspecifica.setNivelLiquidacion(consultarNivelLiquidacion(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));

            // Buscar los periodos
            liqEspecifica.setListaPeriodosMostrar(
                    consultarPeriodosLiquidacionEspecifica(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));

            // Buscar las personas o empresas dependiendo del caso
            if (liqEspecifica.getNivelLiquidacion() != null) {
                if (liqEspecifica.getNivelLiquidacion().equals(TipoTipoSolicitanteEnum.PERSONA)) {

                    ConsultarEstadoDTO conEst;
                    List<ConsultarEstadoDTO> listConsulEstado = new ArrayList<>();
                    List<PersonaDTO> listPersonasDTO = consultarPersonasLiquidacionEspecifica(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio());
                    for (PersonaDTO per : listPersonasDTO) {
                        conEst = new ConsultarEstadoDTO();
                        conEst.setEntityManager(entityManagerCore);
                        conEst.setNumeroIdentificacion(per.getNumeroIdentificacion());
                        conEst.setTipoIdentificacion(per.getTipoIdentificacion());
                        conEst.setTipoPersona(ConstantesComunes.PERSONAS);
                        listConsulEstado.add(conEst);
                    }

                    List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsulEstado);
                    for (EstadoDTO estadoDTO : listEstados) {
                        for (PersonaDTO persona : listPersonasDTO) {
                            if (estadoDTO.getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())
                                    && estadoDTO.getTipoIdentificacion().equals(persona.getTipoIdentificacion())) {
                                persona.setEstadoAfiliadoCaja(estadoDTO.getEstado());
                                persona.setEstadoAfiliadoRol(persona.getEstadoAfiliadoRol() != null ? estadoDTO.getEstado() : null);
                            }
                        }
                    }

                    liqEspecifica.setListaPersonas(listPersonasDTO);
                } else {
                    liqEspecifica.setListaEmpleadoresMostrar(
                            consultarEmpresasLiquidacionEspecifica(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));
                }
            }

            // Buscar las condiciones de subsidio por reconocimiento
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.RECONOCIMIENTO_DE_SUBSIDIOS)) {
                liqEspecifica.setCondicionesEspecialesReconocimiento(
                        consultarCondicionesReconocimiento(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));
            }

            // Ajuste valor cuota
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)
                    && liqEspecifica.getTipoAjuste().equals(TipoLiquidacionEspecificaEnum.VALOR_CUOTA)) {
                liqEspecifica.setListaPeriodos(
                        consultarCondicionesEspecialesLiqAjuste(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));
            }

            // Reconocer cuota agraria
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)
                    && liqEspecifica.getTipoAjuste().equals(TipoLiquidacionEspecificaEnum.RECONOCER_CUOTA_AGRARIA)
                    && !liqEspecifica.getListaPeriodosMostrar().isEmpty()) {

                List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();
                for (Periodo periodo : liqEspecifica.getListaPeriodosMostrar()) {
//                    valoresPeriodos.add(new ValorPeriodoDTO(periodo.getFechaPeriodo().getTime(),
//                            consultarValorCuotaAgrariaPeriodo(periodo.getFechaPeriodo().getTime())));
                    ValorPeriodoDTO valorCuotaAgraria = new ValorPeriodoDTO();
                    valorCuotaAgraria.setPeriodo(periodo.getFechaPeriodo().getTime());
                    valorCuotaAgraria.setValorCuotaAgraria(consultarValorCuotaAgrariaPeriodo(periodo.getFechaPeriodo().getTime()));
                    valoresPeriodos.add(valorCuotaAgraria);
                }

                liqEspecifica.setListaPeriodos(valoresPeriodos);
            }

            // Reconocer factor de discapacidad
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)
                    && liqEspecifica.getTipoAjuste().equals(TipoLiquidacionEspecificaEnum.RECONOCER_DISCAPACIDAD)
                    && !liqEspecifica.getListaPeriodosMostrar().isEmpty()) {

                List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();
                for (Periodo periodo : liqEspecifica.getListaPeriodosMostrar()) {
//                    valoresPeriodos.add(new ValorPeriodoDTO(periodo.getFechaPeriodo().getTime(),
//                            consultarFactorCuotaDiscapacidadPeriodos(periodo.getFechaPeriodo().getTime())));
                    ValorPeriodoDTO valoresCuotas = consultarValorCuotaAnualYAgrariaPeriodos(periodo.getFechaPeriodo().getTime());
                    valoresCuotas.setValorDiscapacidad(consultarFactorCuotaDiscapacidadPeriodos(periodo.getFechaPeriodo().getTime()));
                    valoresPeriodos.add(valoresCuotas);
                }

                liqEspecifica.setListaPeriodos(valoresPeriodos);
            }

            // Reconocer factor de discapacidad
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA)
                    && liqEspecifica.getTipoAjuste().equals(TipoLiquidacionEspecificaEnum.COMPLEMENTAR_CUOTA)
                    && !liqEspecifica.getListaPeriodosMostrar().isEmpty()) {

                List<ValorPeriodoDTO> valoresPeriodos = new ArrayList<>();
                for (Periodo periodo : liqEspecifica.getListaPeriodosMostrar()) {
                    valoresPeriodos.add(consultarValorCuotaAnualYAgrariaPeriodos(periodo.getFechaPeriodo().getTime()));
                }

                liqEspecifica.setListaPeriodos(valoresPeriodos);
            }

            // Buscar trabajor o beneficiarios fallecidos
            if (liqEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION)) {
                liqEspecifica.setPersonaFallecida(
                        consultarPersonaFallecidaTrabajadorBeneficiarios(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio()));
            }

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return liqEspecifica;
    }

    /**
     * Metodo para determinar el nivel para una liquidacion especifica
     *
     * @param idProcesoLiquidacionSubsidio
     * @return
     */
    private TipoTipoSolicitanteEnum consultarNivelLiquidacion(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarNivelLiquidacion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoTipoSolicitanteEnum tipoSolicitante = null;

        List<NivelLiquidacionEspecificaDTO> niveles = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NIVEL_LIQUIDACION_ESPECIFICA, NivelLiquidacionEspecificaDTO.class)
                .setParameter("idSolicitudLiquidacion", idProcesoLiquidacionSubsidio).getResultList();

        if (niveles.isEmpty()) {
            return null;
        }

        if (niveles.get(0).getNivelLiquidacion().equals("EMPRESA")) {
            tipoSolicitante = TipoTipoSolicitanteEnum.EMPLEADOR;
        } else if (niveles.get(0).getNivelLiquidacion().equals("PERSONA")) {
            tipoSolicitante = TipoTipoSolicitanteEnum.PERSONA;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return tipoSolicitante;
    }

    /**
     * Metodo para obtener las personas asociadas a una liquidacion especifica
     *
     * @param idProcesoLiquidacionSubsidio
     * @return Lista de personas
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<PersonaDTO> consultarPersonasLiquidacionEspecifica(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarPersonasLiquidacionEspecifica(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<PersonaDTO> personas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_LIQUIDACION_ESPECIFICA)
                .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return personas;
    }

    /**
     * Metodo para obtener los periodos asociados a una liquidacion especifica
     *
     * @param idProcesoLiquidacionSubsidio
     * @return Lista de periodos
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<Periodo> consultarPeriodosLiquidacionEspecifica(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarPeriodosLiquidacionEspecifica(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Periodo> periodos = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODOS_LIQUIDACION_ESPECIFICA)
                .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return periodos;
    }

    /**
     * Metodo para obtener los periodos asociados a una liquidacion especifica
     *
     * @param idProcesoLiquidacionSubsidio
     * @return Lista de periodos
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<Empleador> consultarEmpresasLiquidacionEspecifica(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarEmpresasLiquidacionEspecifica(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Empleador> empresas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_LIQUIDACION_ESPECIFICA)
                .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return empresas;
    }

    /**
     * Consultar las condiciones de reconocimiento
     *
     * @param idProcesoLiquidacionSubsidio
     * @return
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private CondicionesEspecialesLiquidacionEspecificaDTO consultarCondicionesReconocimiento(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesReconocimiento(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CondicionesEspecialesLiquidacionEspecificaDTO condiciones = new CondicionesEspecialesLiquidacionEspecificaDTO();

        List<AplicacionValidacionSubsidio> validaciones = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_LIQUIDACION_RECONOCIMIENTO)
                .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getResultList();

        for (AplicacionValidacionSubsidio validacion : validaciones) {
            ConjuntoValidacionSubsidio conjunto = entityManagerCore.find(ConjuntoValidacionSubsidio.class,
                    validacion.getIdConjuntoValidacionSubsidio());

            // Establecer las condiciones dentro del DTO
            switch (conjunto.getConjuntoValidacion()) {
                case ESTADO_APORTE:
                case ESTADO_BENEFICIARIO_HIJO:
                case ESTADO_BENEFICIARIO_PADRE:
                case ESTADO_EMPLEADOR:
                case ESTADO_TRABAJADOR:
                    establecerEstadosEmpleador(condiciones, validacion, conjunto.getConjuntoValidacion());
                    break;
                default:
                    establecerCondicionesReconocimiento(condiciones, conjunto, validacion);
                    break;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return condiciones;
    }

    /**
     * Establecer las condiciones asociadas
     *
     * @param condiciones
     * @param conjunto
     * @param validacion
     */
    private void establecerCondicionesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            ConjuntoValidacionSubsidio conjunto, AplicacionValidacionSubsidio validacion) {
        String firmaMetodo = "ConsultasModeloCore.establecerCondicionesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO, ConjuntoValidacionSubsidio)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        switch (conjunto.getConjuntoValidacion()) {
            case CONDICION_AGRICOLA:
                condiciones.setEvaluarCondicionAgricola(validacion.getIsValidable());
                break;
            case RETROACTIVO_NOVEDAD:
                condiciones.setEvaluarRetroactivosPorNovedades(validacion.getIsValidable());
                break;
            case RETROACTIVO_APORTE:
                condiciones.setEvaluarRetroactivosPorAportes(validacion.getIsValidable());
                break;
            case TIENE_BENEFICARIOS:
                condiciones.setEvaluarBeneficiarioDiferenteAConyuge(validacion.getIsValidable());
                break;
            case CAUSACION_SALARIOS:
                condiciones.setEvaluarTrabajadorConMultiAfiliacion(validacion.getIsValidable());
                break;
            case TRABAJADOR_ES_EMPLEADOR:
                condiciones.setEvaluarTrabajadorDistintoAEmpleador(validacion.getIsValidable());
                break;
            case DIAS_COTIZADOS_NOVEDADES:
                condiciones.setEvaluarDiasCotizadosYNovedadesTrabajador(validacion.getIsValidable());
                break;
            case SALARIO:
                condiciones.setEvaluarSalarioTrabajador(validacion.getIsValidable());
                break;
            case BENEFICIARIO_AFILIADO_PRINCIPAL_PADRE:
                condiciones.setEvaluarBeneficiarioPadreEsAfiliadoPrincipal(validacion.getIsValidable());
                break;
            case BENEFICIARIO_EMPLEADOR_PADRE:
                condiciones.setEvaluarBeneficiarioPadreEsPersonaNatural(validacion.getIsValidable());
                break;
            case BENEFICIARIO_OTROS_APORTES_PADRE:
                condiciones.setEvaluarBeneficiarioPadreTieneOtrosAportes(validacion.getIsValidable());
                break;
            case BENEFICIARIO_AFILIADO_PRINCIPAL_HIJO:
                condiciones.setEvaluarBeneficiarioHijoEsAfiliadoPrincipal(validacion.getIsValidable());
                break;
            case BENEFICIARIO_EMPLEADOR_HIJO:
                condiciones.setEvaluarBeneficiarioHijoEsPersonaNatural(validacion.getIsValidable());
                break;
            case BENEFICIARIO_OTROS_APORTES_HIJO:
                condiciones.setEvaluarBeneficiarioHijoTieneOtrosAportes(validacion.getIsValidable());
                break;
            case BENEFICIARIO_OTRAS_PERSONAS_PADRE:
                condiciones.setEvaluarBenefiarioTipoPadreDeOtrasPersonas(validacion.getIsValidable());
                break;
            case BENEFICIARIO_OTRAS_PERSONAS_HIJO:
                condiciones.setEvaluarBenefiarioTipoHijoDeOtrasPersonas(validacion.getIsValidable());
                break;
            case CONDICION_INVALIDEZ_PADRE:
                condiciones.setEvaluarBeneficiarioTipoPadreConCondicionInvalidez(validacion.getIsValidable());
                break;
            case CONDICION_INVALIDEZ_HIJO:
                condiciones.setEvaluarBeneficiarioTipoHijoConCondicionInvalidez(validacion.getIsValidable());
                break;
            case EDAD_BENEFICIARIO_PADRE:
                condiciones.setEvaluarEdadBeneficiarioTipoPadre(validacion.getIsValidable());
                break;
            case EDAD_BENEFICIARIO_HIJO:
                condiciones.setEvaluarEdadBeneficiarioTipoHijo(validacion.getIsValidable());
                break;
            case PERMITIR_EVALUAR_LIQUIDACION_EMPRESA:
                condiciones.setValidarCondicionesAportanteRegular(validacion.getIsValidable());
                break;
            case PERMITIR_EVALUAR_LIQUIDACION_TRABAJADOR:
                condiciones.setValidarCondicionesTrabajador(validacion.getIsValidable());
                break;
            case PERMITIR_EVALUAR_LIQUIDACION_BENEFICIARIOS:
                condiciones.setValidarCondicionesBeneficiario(validacion.getIsValidable());
                break;
            default:
                break;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param condiciones
     * @param validacion
     * @param conjuntoValidacionSubsidio
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void establecerEstadosEmpleador(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            AplicacionValidacionSubsidio validacion, ConjuntoValidacionSubsidioEnum conjuntoValidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesReconocimiento(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<EstadoEmpleadorEnum> listaEstadosEmpleador = new ArrayList<>();
        List<EstadoAfiliadoEnum> listaEstadosTrabajador = new ArrayList<>();
        List<EstadoRegistroAportesArchivoEnum> listaEstadosAporte = new ArrayList<>();

        try {
            EstadoCondicionValidacionSubsidio estadosEmpleador = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADOS_EMPLEADOR_LIQ_RECONOCIMIENTO,
                            EstadoCondicionValidacionSubsidio.class)
                    .setParameter("idAplicacionValidacionSubsidio", validacion.getIdAplicacionValidacionSubsidio()).getSingleResult();

            //  Establecer condiciones del empleador
            if (conjuntoValidacionSubsidio.equals(ConjuntoValidacionSubsidioEnum.ESTADO_EMPLEADOR)) {
                if (estadosEmpleador.getIsActivo()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.ACTIVO);
                }
                if (estadosEmpleador.getIsInactivo()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.INACTIVO);
                }
                if (estadosEmpleador.getIsNFConAporteSinAfiliacion()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                }
                if (estadosEmpleador.getIsNFConInformacion()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
                }
                if (estadosEmpleador.getIsNFRetiradoConAportes()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
                }
                if (estadosEmpleador.getIsSinEstado()) {
                    listaEstadosEmpleador.add(EstadoEmpleadorEnum.SIN_ESTADO);
                }
                condiciones.setEstadosEmpleador(listaEstadosEmpleador);
            }

            //  Establecer condiciones del trabajador            
            if (conjuntoValidacionSubsidio.equals(ConjuntoValidacionSubsidioEnum.ESTADO_TRABAJADOR)) {
                if (estadosEmpleador.getIsActivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.ACTIVO);
                }
                if (estadosEmpleador.getIsInactivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.INACTIVO);
                }
                if (estadosEmpleador.getIsNFConAporteSinAfiliacion()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                }
                if (estadosEmpleador.getIsNFConInformacion()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
                }
                if (estadosEmpleador.getIsNFRetiradoConAportes()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
                }
                if (estadosEmpleador.getIsSinEstado()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.SIN_ESTADO);
                }
                condiciones.setEstadosAfiliacionTrabajador(listaEstadosTrabajador);
            }

            // Establecer estados del aporte
            if (conjuntoValidacionSubsidio.equals(ConjuntoValidacionSubsidioEnum.ESTADO_APORTE)) {
                if (estadosEmpleador.getIsOK()) {
                    listaEstadosAporte.add(EstadoRegistroAportesArchivoEnum.OK);
                }
                if (estadosEmpleador.getIsNoOk()) {
                    listaEstadosAporte.add(EstadoRegistroAportesArchivoEnum.NO_OK);
                }
                if (estadosEmpleador.getIsNoValidadoBD()) {
                    listaEstadosAporte.add(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD);
                }
                condiciones.setEstadosAporteValidosTrabajador(listaEstadosAporte);
            }

            //  Establecer condiciones de beneficiario padre
            if (conjuntoValidacionSubsidio.equals(ConjuntoValidacionSubsidioEnum.ESTADO_BENEFICIARIO_PADRE)) {
                if (estadosEmpleador.getIsActivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.ACTIVO);
                }
                if (estadosEmpleador.getIsInactivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.INACTIVO);
                }
                condiciones.setEstadosAfilPadreConAfiliadoPrincipal(listaEstadosTrabajador);
            }

            //  Establecer condiciones de beneficiario padre
            if (conjuntoValidacionSubsidio.equals(ConjuntoValidacionSubsidioEnum.ESTADO_BENEFICIARIO_HIJO)) {
                if (estadosEmpleador.getIsActivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.ACTIVO);
                }
                if (estadosEmpleador.getIsInactivo()) {
                    listaEstadosTrabajador.add(EstadoAfiliadoEnum.INACTIVO);
                }
                condiciones.setEstadosAfilHijoConAfiliadoPrincipal(listaEstadosTrabajador);
            }

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Consultar condiciones especiales subsidio por ajuste
     *
     * @param idProcesoLiquidacionSubsidio
     * @return
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<ValorPeriodoDTO> consultarCondicionesEspecialesLiqAjuste(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondicionesReconocimiento(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ValorPeriodoDTO> respuestaPeriodosAjustes = new ArrayList<>();

        Object idParametrizacionCondicionesSubsidio = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PARAMETRIZACION_CONDICION)
                .getSingleResult();


        List<ValorPeriodoDTO> periodosAjustes = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_ESPECIALES_LIQ_AJUSTE)
                .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio)
                .setParameter("idParametrizacionCondicionesSubsidio", Long.parseLong(idParametrizacionCondicionesSubsidio.toString())).getResultList();

        respuestaPeriodosAjustes = periodosAjustes;

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaPeriodosAjustes;
    }

    /**
     * @param idProcesoLiquidacionSubsidio
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private PersonaFallecidaTrabajadorDTO consultarPersonaFallecidaTrabajadorBeneficiarios(Long idProcesoLiquidacionSubsidio) {
        String firmaMetodo = "ConsultasModeloCore.consultarPersonaFallecidaTrabajador_Beneficiarios(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idPersona = null;
        try {
            BigInteger id = (BigInteger) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADOR_BENEFICIARIOS_FALLECIDOS_POR_SOLICITUD_LIQUIDACION)
                    .setParameter("idProcesoLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getSingleResult();

            if (id != null) {
                idPersona = id.longValue();
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        // se obtienen los detalles de la persona fallecida
        PersonaFallecidaTrabajadorDTO personaFallecidaTrabajadorDTO = idPersona != null ? consultarDetalleTrabajadorFallecido(idPersona)
                : null;

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return personaFallecidaTrabajadorDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarParametrizacionLiquidacionMasiva(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IniciarSolicitudLiquidacionMasivaDTO consultarParametrizacionLiquidacionMasiva(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarParametrizacionLiquidacionMasiva(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            IniciarSolicitudLiquidacionMasivaDTO parametrizacionSolicitudDTO = new IniciarSolicitudLiquidacionMasivaDTO();

            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);
            if (solicitudLiquidacionDTO != null) {

                if (TipoProcesoLiquidacionEnum.MASIVA.equals(solicitudLiquidacionDTO.getTipoLiquidacion())) {
                    Date fechaUltimaLiquidacion = consultarFechaCorteAportesLiquidacionMasivaPrevia(
                            new Date(solicitudLiquidacionDTO.getFechaInicial()));

                    parametrizacionSolicitudDTO.setEstadoProcesoLiquidacion(solicitudLiquidacionDTO.getEstadoLiquidacion());
                    parametrizacionSolicitudDTO.setTipoProcesoLiquidacion(solicitudLiquidacionDTO.getTipoLiquidacion());
                    parametrizacionSolicitudDTO.setTipoEjecucionProcesoLiquidacion(solicitudLiquidacionDTO.getTipoEjecucionProceso());
                    parametrizacionSolicitudDTO.setIdSolicitudLiquidacionActual(solicitudLiquidacionDTO.getIdProcesoLiquidacionSubsidio());
                    parametrizacionSolicitudDTO.setPeriodoLiquidacionMasiva(
                            consultarPeriodoRegular(solicitudLiquidacionDTO.getIdProcesoLiquidacionSubsidio()));
                    parametrizacionSolicitudDTO.setFechaHoraCorteAportes(new Date(solicitudLiquidacionDTO.getFechaCorteAportes()));

                    parametrizacionSolicitudDTO.setFechaUltimoCorteAportes(fechaUltimaLiquidacion);
//                    parametrizacionSolicitudDTO.setFechaUltimoCorteAportes(
//                            fechaUltimaLiquidacion == null ? SubsidioDateUtils.ponerFechaEnDiaAnteriorAntesMedianoche(
//                                    new Date(solicitudLiquidacionDTO.getFechaCorteAportes())) : fechaUltimaLiquidacion);

                    if (solicitudLiquidacionDTO.getFechaHoraEjecucionProgramada() != null) {
                        parametrizacionSolicitudDTO
                                .setFechaHoraEjecucionProgramada(new Date(solicitudLiquidacionDTO.getFechaHoraEjecucionProgramada()));
                    }
                } else {
                    SolicitudLiquidacionSubsidioModeloDTO ultimaMasivaDTO = consultarUltimaLiquidacionMasiva();
                    if (ultimaMasivaDTO != null) {
                        Date fechaUltimaLiquidacion = consultarFechaCorteAportesLiquidacionMasivaPrevia(
                                new Date(ultimaMasivaDTO.getFechaInicial()));

                        parametrizacionSolicitudDTO.setEstadoProcesoLiquidacion(ultimaMasivaDTO.getEstadoLiquidacion());
                        parametrizacionSolicitudDTO.setTipoProcesoLiquidacion(ultimaMasivaDTO.getTipoLiquidacion());
                        parametrizacionSolicitudDTO.setTipoEjecucionProcesoLiquidacion(ultimaMasivaDTO.getTipoEjecucionProceso());
                        parametrizacionSolicitudDTO.setIdSolicitudLiquidacionActual(ultimaMasivaDTO.getIdProcesoLiquidacionSubsidio());
                        parametrizacionSolicitudDTO
                                .setPeriodoLiquidacionMasiva(consultarPeriodoRegular(ultimaMasivaDTO.getIdProcesoLiquidacionSubsidio()));
                        parametrizacionSolicitudDTO.setFechaHoraCorteAportes(new Date(ultimaMasivaDTO.getFechaCorteAportes()));

                        parametrizacionSolicitudDTO.setFechaUltimoCorteAportes(fechaUltimaLiquidacion);
//                        parametrizacionSolicitudDTO.setFechaUltimoCorteAportes(
//                                fechaUltimaLiquidacion == null ? SubsidioDateUtils.ponerFechaEnDiaAnteriorAntesMedianoche(
//                                        new Date(solicitudLiquidacionDTO.getFechaCorteAportes())) : fechaUltimaLiquidacion);
                    }
                }
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return parametrizacionSolicitudDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener la fecha de corte de aportes para la última
     * liquidación masiva
     *
     * @param fechaInicialLiquidacion Fecha inicial de la liquidación masiva
     * @return Fecha de corte de aportes
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Date consultarFechaCorteAportesLiquidacionMasivaPrevia(Date fechaInicialLiquidacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarFechaCorteAportesLiquidacionMasivaPrevia(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Date fecha = (Date) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ULTIMO_CORTE_APORTES_LIQUIDACION_MASIVA2)
                    .setParameter("fechaInicialLiquidacion", fechaInicialLiquidacion).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return fecha;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener la última liquidación masiva ejecutada
     *
     * @return DTO con la información de la liquidación
     * @author rlopez
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private SolicitudLiquidacionSubsidioModeloDTO consultarUltimaLiquidacionMasiva() {
        String firmaMetodo = "ConsultasModeloCore.consultarUltimaLiquidacionMasiva()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            SolicitudLiquidacionSubsidio solicitudLiquidacion = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_LIQUIDACION_MASIVA, SolicitudLiquidacionSubsidio.class)
                    .getSingleResult();

            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = new SolicitudLiquidacionSubsidioModeloDTO();
            solicitudLiquidacionDTO.convertToDTO(solicitudLiquidacion);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return solicitudLiquidacionDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(java.lang.String,
     * java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idPersonaLiqEspecifica = ((Number) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.SELECCIONAR_PERSONA_LIQUIDACION_ESPECIFICA_TRABAJADOR_LIQUIDACION_FALLECIMIENTO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idPersona", idPersona).getSingleResult()).longValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return idPersonaLiqEspecifica;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(java.lang.String,
     * java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idPersonaLiqEspecifica = ((Number) entityManagerCore
                    .createNamedQuery(
                            NamedQueriesConstants.SELECCIONAR_PERSONA_LIQUIDACION_ESPECIFICA_BENEFICIARIO_LIQUIDACION_FALLECIMIENTO)
                    .setParameter("numeroRadicacion", numeroRadicacion).setParameter("idPersona", idPersona).getSingleResult()).longValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return idPersonaLiqEspecifica;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarValidacionesTipoProceso(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConjuntoValidacionSubsidioEnum> consultarValidacionesTipoProceso(TipoValidacionLiquidacionEspecificaEnum tipoProceso) {
        String firmaMetodo = "ConsultasModeloCore.consultarValidacionesTipoProceso(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<ConjuntoValidacionSubsidioEnum> validaciones = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALIDACIONES_TIPO_PROCESO, ConjuntoValidacionSubsidioEnum.class)
                    .setParameter("tipoProceso", tipoProceso).getResultList();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return validaciones;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarIdentificadorConjuntoValidacion(java.lang.String,
     * com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarIdentificadorConjuntoValidacion(ConjuntoValidacionSubsidioEnum validacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarIdentificadorConjuntoValidacion(String,ConjuntoValidacionSubsidioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Long idConjunto = ((Number) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDENTIFICADOR_CONJUNTO_VALIDACION)
                    .setParameter("validacion", validacion).getSingleResult()).longValue();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return idConjunto;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#registrarAplicacionValidacionSubsidio(com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO)
     */
    @Override
    public Long registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            AplicacionValidacionSubsidio aplicacionValidacionSubsidio = aplicacionValidacionDTO.converToEntity();
            entityManagerCore.persist(aplicacionValidacionSubsidio);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return aplicacionValidacionSubsidio.getIdAplicacionValidacionSubsidio();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#registrarAplicacionValidacionSubsidioPersona(com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO)
     */
    @Override
    public Long registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            AplicacionValidacionSubsidioPersona aplicacionValidacionPersona = aplicacionValidacionPersonaDTO.convertToEntity();
            entityManagerCore.persist(aplicacionValidacionPersona);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return aplicacionValidacionPersona.getIdAplicacionValidacionSubsidioPersona();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarPeriodoLiquidacionRadicacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarPeriodoLiquidacionRadicacion(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloCore.consultarPeriodoLiquidacionRadicacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);
            Date periodoLiquidacion = CalendarUtils.obtenerPrimerDiaMesTruncarHora(new Date(solicitudLiquidacionDTO.getFechaInicial()));
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return SubsidioDateUtils.ponerFechaEnPrimerDia(periodoLiquidacion);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarDestinatariosComunicadosLiquidacionMasiva(java.util.Set)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, AutorizacionEnvioComunicadoDTO> consultarDestinatariosComunicadosLiquidacionMasiva(Set<Long> idPersonasEmpresas, EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarDestinatariosComunicadosLiquidacionMasiva(Set<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String namedQuery;
        Map<Long, AutorizacionEnvioComunicadoDTO> destinatarios = new HashMap<Long, AutorizacionEnvioComunicadoDTO>();
        switch (etiquetaPlantillaComunicado) {
            case COM_SUB_DIS_PAG_EMP:
                namedQuery = NamedQueriesConstants.CONSULTAR_DESTINATARIOS_COMUNICADO_63;
                break;
            case COM_SUB_DIS_PAG_TRA:
                namedQuery = NamedQueriesConstants.CONSULTAR_DESTINATARIOS_COMUNICADO_64;
                break;
            case COM_SUB_DIS_PAG_ADM_SUB:
            case COM_SUB_DIS_FAL_PRO_ADM_SUB:
            case COM_SUB_DIS_FAL_PAG_ADM_SUB:
                namedQuery = NamedQueriesConstants.CONSULTAR_DESTINATARIOS_COMUNICADO_65;
                break;
            case COM_SUB_DIS_FAL_PRO_TRA:
            case COM_SUB_DIS_FAL_PAG_TRA:
                namedQuery = NamedQueriesConstants.CONSULTAR_DESTINATARIOS_COMUNICADO_74_77;
                break;
            default:
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return null;
        }

        Set<Long> idPersonasEmpresasTmp = new HashSet<Long>();
        List<Object[]> correos = new ArrayList<Object[]>();
        List<Object[]> correosTmp;

        int cont = 0;
        for (Long id : idPersonasEmpresas) {
            cont++;
            idPersonasEmpresasTmp.add(id);
            if (cont == 1000) {
                correosTmp = entityManagerCore
                        .createNamedQuery(namedQuery)
                        .setParameter("idPersonas", idPersonasEmpresasTmp).getResultList();
                correos.addAll(correosTmp);

                cont = 0;
                idPersonasEmpresasTmp = new HashSet<Long>();
            }
        }
        if (cont > 0) {
            correosTmp = entityManagerCore
                    .createNamedQuery(namedQuery)
                    .setParameter("idPersonas", idPersonasEmpresasTmp).getResultList();
            correos.addAll(correosTmp);
        }
        for (Object[] fila : correos) {
            if (fila[0] != null && fila[1] != null && fila[2] != null) {
                destinatarios.put(new Long(fila[0].toString()), new AutorizacionEnvioComunicadoDTO(fila[1].toString(), (Boolean) fila[2]));
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return destinatarios;
    }

    /**
     * Método que obtiene la validación del caso 6 de la HU 317 506 CASO 6 506
     * (Liquidación no procedente - Beneficiario): Validar que el rango de días
     * entre la “Fecha de defunción” y la “Fecha de registro” de la novedad, sea
     * menor o igual a los días parametrizados por la caja para poder registrar
     * una solicitud de subsidio por fallecimiento
     *
     * @param idBeneficiario <code>Long</code> Identificador principal del
     * beneficiario fallecido
     * @return TRUE si las validaciones no son existosas; FALSE las validaciones
     * son exitosas.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean validarRangoDeDiasCaso6Hu506(Long idBeneficiario) {
        String firmaMetodo = "SubsidioMonetarioBusiness.validarRangoDeDiasCaso6Hu506(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean esValidado = Boolean.FALSE;
        try {
            esValidado = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RANGO_DIAS_BENEFICIARIO_HU_506_CASO6, Boolean.class)
                    .setParameter("idBeneficiario", idBeneficiario).getSingleResult();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " Ocurrio un error al obtener la validación del Caso 6 HU 317-506");
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return esValidado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarDiaDelPeriodoDispersionDetallesProgramadosToDetallesAsignados()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean consultarDiaDelPeriodoDispersionDetallesProgramadosToDetallesAsignados() {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarDiaDelPeriodoDispersionDetallesProgramadosToDetallesAsignados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean resultado = null;
        try {

            String respuesta = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DIA_PERIODO_PARA_DISPERSION_DETALLES_PROGRAMADOS)
                    .getSingleResult().toString();

            resultado = (respuesta.equals("1")) ? Boolean.TRUE : Boolean.FALSE;

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " Ocurrio un error al obtener la validación del Caso 6 HU 317-506");
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#agregarRegistroDeDetallesProgramadosToDetallesAsignados()
     */
    @Override
    public void agregarRegistroDeDetallesProgramadosToDetallesAsignados() {
        String firmaMetodo = "ConsultasModeloCore.agregarRegistroDeDetallesProgramadosToDetallesAsignados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            StoredProcedureQuery storedProcedure = entityManagerCore
                    .createStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_REGISTRAR_DETALLESPROGRAMADOS_A_DETALLESASIGNADOS);
            storedProcedure.execute();

        } catch (QueryTimeoutException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (PersistenceException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#verificarLiquidacionFallecimientoEnProceso()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean verificarLiquidacionFallecimientoEnProceso() {
        String firmaMetodo = "ConsultasModeloCore.verificarLiquidacionFallecimientoEnProceso()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean hayLiquidacionEnProceso = Boolean.FALSE;

        List<EstadoProcesoLiquidacionEnum> estadosLiquidacion = new ArrayList<>();
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.CERRADA);
        estadosLiquidacion.add(EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);

        try {
            // Buscar si hay una liquidacion masiva con estado diferente a cerrada
            List<SolicitudLiquidacionSubsidio> listaSolicitudes = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_LIQUIDACION_FALLECIMIENTO_SIN_CERRAR,
                            SolicitudLiquidacionSubsidio.class)
                    .setParameter("estadoSolicitudLiquidacion", estadosLiquidacion)
                    .setParameter("tipoLiquidacion", TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION).getResultList();

            hayLiquidacionEnProceso = listaSolicitudes.isEmpty() ? Boolean.FALSE : Boolean.TRUE;

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return hayLiquidacionEnProceso;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarBeneficiariosAfiliadoCondicionesLiquidacion(java.lang.Long,
     * java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliadoCondicionesLiquidacion(Long idSolicitud, Long idPersona) {
        String firmaMetodo = "ConsultasModeloCore.consultarBeneficiariosAfiliado(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BeneficiariosAfiliadoDTO> beneficiarios = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_ASOCIADOS_CONDICION_LIQUIDACION_SUBSIDIO)
                .setParameter("idSolicituGlobal", idSolicitud)
                .setParameter("idPersona", idPersona)
                .getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return !beneficiarios.isEmpty() ? beneficiarios : Collections.emptyList();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAplicacionValidacionSubsidioPorIDSolicitudLiquidacionSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> obtenerAplicacionValidacionSubsidioPorIDSolicitudLiquidacionSubsidio(
            Long idProcesoLiquidacionSubsidio, GrupoAplicacionValidacionSubsidioEnum validacion) {
        String firmaMetodo = "ConsultasModeloCore.obtenerAplicacionValidacionSubsidioPorIDSolicitudLiquidacionSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> lstIdAplicacionValidacionSubsidio = new ArrayList<>();
        List<Object[]> lstValidaciones = null;
        try {
            lstValidaciones = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_APLICACION_VALIDACION_SUBSIDIO_POR_ID_SUBSIDIO)
                    .setParameter("idSolicitudLiquidacionSubsidio", idProcesoLiquidacionSubsidio).getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + " " + e);
        }

        String idApValidacion = lstValidaciones.stream().filter(v -> v[1].toString().equals(validacion.name())).findFirst().get()[0].toString();
        //se agregan los ids de cada validación
        long id = Long.parseLong(idApValidacion);
        if (lstValidaciones.size() == 2) {
            id++;
            lstIdAplicacionValidacionSubsidio.add(id);
        } else if (lstValidaciones.size() != 1) {
            for (Object[] item : lstValidaciones) {
                if (Long.parseLong(item[0].toString()) > id) {
                    lstIdAplicacionValidacionSubsidio.add(Long.parseLong(item[0].toString()));
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdAplicacionValidacionSubsidio.isEmpty() ? null : lstIdAplicacionValidacionSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#eliminarRegistroAplicacionValidacionSubsidioPersona(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarRegistroAplicacionValidacionSubsidioPersona(List<Long> lstIdAplicacionValidacionDTO) {
        String firmaMetodo = "ConsultasModeloCore.eliminarRegistroAplicacionValidacionSubsidioPersona(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            // se elimina el registro
            entityManagerCore
                    .createNamedQuery(
                            NamedQueriesConstants.ELIMINAR_REGISTRO_APLICACION_VALIDACION_SUBSIDIO_PERSONA_POR_ID_APLICACION_VALIDACION)
                    .setParameter("idAplicacionSubsidio", lstIdAplicacionValidacionDTO).executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO
                    + " al tratar de borrar el registroAplicacionValidacionSubsidioPersona " + e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#eliminarRegistroAplicacionValidacionSubsidioPorId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarRegistroAplicacionValidacionSubsidioPorId(List<Long> lstIdAplicacionValidacionDTO) {
        String firmaMetodo = "ConsultasModeloCore.eliminarRegistroAplicacionValidacionSubsidioPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            // se elimina el registro
            entityManagerCore
                    .createNamedQuery(
                            NamedQueriesConstants.ELIMINAR_REGISTRO_APLICACION_VALIDACION_SUBSIDIO_POR_ID)
                    .setParameter("idAplicacionSubsidio", lstIdAplicacionValidacionDTO).executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO
                    + " al tratar de borrar el registroAplicacionValidacionSubsidio " + e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#cancelarMasivaActualizarObservacionesProceso(java.lang.String,
     * java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cancelarMasivaActualizarObservacionesProceso(String numeroSolicitud, String observacion) {
        String firmaMetodo = "ConsultasModeloCore.actualizarObservacionesProceso(Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION, SolicitudLiquidacionSubsidio.class)
                    .setParameter("numeroRadicado", numeroSolicitud).getSingleResult();
            solicitudLiquidacionSubsidio.setObservacionesProceso(observacion);
            entityManagerCore.merge(solicitudLiquidacionSubsidio);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarSitiosPago()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, String> consultarSitiosPago() {
        String firmaMetodo = "ConsultasModeloCore.consultarSitiosPago()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Object[]> result = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SITIOS_PAGO).getResultList();
        Map<Long, String> sitiosDePago = new HashMap<>();
        for (Object[] obj : result) {
            sitiosDePago.put(Long.valueOf(obj[0].toString()), obj[1].toString());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return sitiosDePago;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#exportarLiquidacionesPorEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.util.Date, java.util.Date, java.util.Date,
     * java.lang.String, javax.ws.rs.core.UriInfo,
     * javax.servlet.http.HttpServletResponse)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Date periodo, Date fechaInicio,
            Date fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "ConsultasModeloCore.exportarLiquidacionesPorEmpleador(TipoIdentificacionEnum, String, Date, Date, Date, String, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<RegistroLiquidacionSubsidioDTO> resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_EMPLEADOR_EXPORTAR)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("periodo", periodo)
                .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarPagosPorResultadoValidacionLiquidacion(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarPagosPorResultadoValidacionLiquidacion(List<Long> listaIdsRvl) {
        String firmaMetodo = "ConsultasModeloCore.exportarLiquidacionesPorEmpleador(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final int idRvl = 0;
        Long key;
        List<Object[]> resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_PAGOS_POR_CUOTAS_NOMETARIA)
                .setParameter("listaIdsRvl", listaIdsRvl).getResultList();
//		Map<Long, Object[]> datosPagos = new HashMap<Long, Object[]>();
//		for(Object[] obj : resultado){ 
//			key = Long.parseLong(obj[idRvl].toString());
//			datosPagos.put(key, obj);
//		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarInfoLiquidacionFallecimientoVista360(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object[] consultarInfoLiquidacionFallecimientoVista360(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloSubsidio.consultarInfoLiquidacionFallecimientoVista360(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            Object[] result = (Object[]) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_LIQUIDACION_FALLECIMIENTO_CORE)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerInfoAdministradorSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public InformacionAdminSubsidioDTO obtenerInfoAdministradorSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio,
            String numeroIdAdminSubsidio) {
        String firmaMetodo = "ConsultasModeloSubsidio.obtenerInfoAdministradorSubsidio(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionAdminSubsidioDTO resultado = new InformacionAdminSubsidioDTO();

        List<Object[]> lstResultado = new ArrayList<>();
        try {

            lstResultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_ADMINSUBSIDIO)
                    .setParameter("tipoIdAdmin", tipoIdAdminSubsidio.name()).setParameter("numeroIdAdmin", numeroIdAdminSubsidio)
                    .getResultList();

            if (!lstResultado.isEmpty()) {

                resultado.setNombreAdminSubsidio(lstResultado.get(0)[0].toString());
                resultado.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(lstResultado.get(0)[1].toString()));
                resultado.setNumeroIdAdminSubsidio(lstResultado.get(0)[2].toString());

                List<InformacionGrupoFamiliarDTO> lstGrpFamiliar = new ArrayList<>();
                for (Object[] item : lstResultado) {
                    InformacionGrupoFamiliarDTO itemGrupoFamiliar = new InformacionGrupoFamiliarDTO();

                    itemGrupoFamiliar.setTipoIdAfiliado(TipoIdentificacionEnum.valueOf(item[3].toString()));
                    itemGrupoFamiliar.setNumeroIdAfiliado(item[4].toString());
                    itemGrupoFamiliar.setNombreAfiliado(item[5].toString());
                    itemGrupoFamiliar.setNumeroGrupoFamilarRelacionador(Long.valueOf(item[6].toString()));
                    itemGrupoFamiliar.setMedioDePago(TipoMedioDePagoEnum.valueOf(item[7].toString()));

                    lstGrpFamiliar.add(itemGrupoFamiliar);
                }
                resultado.setLstGruposFamiliares(lstGrpFamiliar);
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstResultado.isEmpty() ? null : resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#obtenerCuotaMonetaria(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public SubsidioAfiliadoDTO obtenerCuotaMonetaria(TipoIdentificacionEnum tipoIdPersona, String numeroIdPersona, Long periodo) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerCuotaMonetaria(TipoIdentificacionEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SubsidioAfiliadoDTO result = new SubsidioAfiliadoDTO();
        Date fechaPeriodo = new Date(periodo);
        List<Object[]> resultado = new ArrayList<>();

        resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUOTA_MONETARIA_POR_AFILIADO_BENEFICIARIO)
                .setParameter("tipoIdPersona", tipoIdPersona.name().toString()).setParameter("numeroIdPersona", numeroIdPersona)
                .setParameter("periodo", CalendarUtils.truncarHora(fechaPeriodo)).getResultList();

        if (!resultado.isEmpty()) {
            //se obtiene la información general del afiliado
            result.setTipoIdAfiliado(TipoIdentificacionEnum.valueOf(resultado.get(0)[0].toString()));
            result.setNumeroIdAfiliado(resultado.get(0)[1].toString());
            result.setNombreCompletoAfiliado(resultado.get(0)[2].toString());
            result.setPeriodo(resultado.get(0)[3] != null ? resultado.get(0)[3].toString() : null);

            List<InformacionGrupoFamiliarDTO> lstGrpFamiliar = new ArrayList<>();
            for (Object[] item : resultado) {
                InformacionGrupoFamiliarDTO infoGrupoFamiliar = new InformacionGrupoFamiliarDTO();

                infoGrupoFamiliar.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(item[4].toString()));
                infoGrupoFamiliar.setNumeroIdAdminSubsidio(item[5].toString());
                infoGrupoFamiliar.setNombreAdminSubsidio(item[6].toString());
                infoGrupoFamiliar.setMedioDePago(TipoMedioDePagoEnum.valueOf(item[7].toString()));
                infoGrupoFamiliar.setNumeroGrupoFamilarRelacionador(Long.valueOf(item[8].toString()));
                infoGrupoFamiliar.setValorSubsidioMonetario(BigDecimal.valueOf(Double.parseDouble(item[9].toString())));
                infoGrupoFamiliar.setUsuarioTransaccion(item[10].toString());
                infoGrupoFamiliar.setFechaHoraTransaccion(item[11] != null ? item[11].toString() : null);
                infoGrupoFamiliar.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(item[12].toString()));
                infoGrupoFamiliar.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(item[13].toString()));
                infoGrupoFamiliar.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(item[14].toString()));
                infoGrupoFamiliar.setValorRealTransaccion(BigDecimal.valueOf(Double.valueOf(item[15].toString())));
                if (item[16] != null) {
                    infoGrupoFamiliar.setNumeroTarjeta(item[16].toString());
                }
                if (item[17] != null) {
                    infoGrupoFamiliar.setNombreTerceroPagador(item[17].toString());
                    infoGrupoFamiliar.setTipoIdTerceroPagador(TipoIdentificacionEnum.valueOf(item[18].toString()));
                    infoGrupoFamiliar.setNumeroIdTerceroPagador(item[19].toString());
                }
                if (item[20] != null) {
                    infoGrupoFamiliar.setNombreSitioPago(item[20].toString());
                }
                if (item[21] != null) {
                    infoGrupoFamiliar.setNombreSitioCobro(item[21].toString());
                }
                if (item[22] != null) {
                    infoGrupoFamiliar.setNumeroCuenta(item[22].toString());
                    infoGrupoFamiliar.setTipoCuenta(TipoCuentaEnum.valueOf(item[23].toString()));
                    infoGrupoFamiliar.setNombreBanco(item[24].toString());
                }
                infoGrupoFamiliar.setMunicipioCodigo(item[25] != null ? item[25].toString() : null);
                infoGrupoFamiliar.setDepartamentoCodigo(item[26] != null ? item[26].toString() : null);
                infoGrupoFamiliar.setIdTransaccion(item[27] != null ? item[27].toString() : null);

                lstGrpFamiliar.add(infoGrupoFamiliar);
            }
            result.setLstGruposFamiliares(lstGrpFamiliar);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado.isEmpty() ? null : result;
    }

    @Override 
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal getMontoRetiroPorLiquidacionTrabajador(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
    TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, String numerosRadicados) {
        List<Object> resultado = new ArrayList<>(); 
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorTrabajador(TipoIdentificacionEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        resultado = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTA_RETIRO_LIQUIDACION_TRABAJADOR)
                .setParameter("tipoIdAfiliado", tipoIdAfiliado.toString())
                .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                .setParameter("tipoIdEmpleador", tipoIdEmpleador.toString())
                .setParameter("numeroIdEmpleador",numeroIdEmpleador)
                .setParameter("numerosRadicados",numerosRadicados).getResultList();       
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        if (resultado != null && resultado.get(0) != null ) return new BigDecimal(resultado.get(0).toString());
        return new BigDecimal("0");
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarPagosLiquidacionesPorTrabajador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarPagosLiquidacionesPorTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> numerosRadicados) {
        List<Object[]> resultado = new ArrayList<>();
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionesPorTrabajador(TipoIdentificacionEnum,String,List<String>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);


        try {
			int registrosPag = 100;
			int contIds = 0; 
			int cantRegistros  = numerosRadicados.size();

			ArrayList<String> paginaIdP = new ArrayList<>();
			
			do{
				for (int i = 0; i < registrosPag && contIds<cantRegistros; i++) {
					paginaIdP.add(numerosRadicados.get(contIds));
					contIds++;
				}

				List<Object[]> resultQ = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_PAGOS_POR_TRABAJADOR_LIQUIDACION)
                .setParameter("tipoIdentificacion", tipoIdentificacion.toString())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("numerosRadicados", paginaIdP).getResultList();
				if(!resultQ.isEmpty() && resultQ != null){
					for(Object[] sol :resultQ){
						resultado.add(sol);
						
					}
				}
				paginaIdP.clear();
			}
			while(contIds<cantRegistros);

		} catch (NoResultException e) {
			resultado = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarPagosLiquidacionesPorTrabajadorPeriodo(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> numerosRadicados,TipoIdentificacionEnum tipoIdentificacionEmpleador,String numeroIdentificacionEmpleador) {
        List<Object[]> resultado = new ArrayList<>();
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarPagosLiquidacionesPorTrabajadorPeriodo(TipoIdentificacionEnum,String,List<String>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        resultado = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VISTA_360_PAGOS_POR_TRABAJADOR_LIQUIDACION_PERIODO)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("numerosRadicados",numerosRadicados)
                .setParameter("tipoIdentificacionEmpleador",tipoIdentificacionEmpleador.name())
                .setParameter("numeroIdentificacionEmpleador",numeroIdentificacionEmpleador).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarDestinatariosComunicadosFallecimiento137_138(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<Long, AutorizacionEnvioComunicadoDTO> consultarDestinatariosComunicadosFallecimiento137_138(List<Long> lstIdPersona) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarInformacionComunicado137(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Map<Long, AutorizacionEnvioComunicadoDTO> listadoAdministradores = new HashMap<>();
        List<Object[]> resultados = null;
        try {
            resultados = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMAILS_PERSONAS_FALLECIMIENTO_COM_137_138)
                    .setParameter("listadoIdPersonas", lstIdPersona).getResultList();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "error en la busqueda de la Cuota Monetaria", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        for (Object[] item : resultados) {
            if (item[1] != null && item[2] != null) {
                listadoAdministradores.put(Long.valueOf(item[0].toString()), new AutorizacionEnvioComunicadoDTO(item[1].toString(), (Boolean) item[2]));
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoAdministradores.isEmpty() ? null : listadoAdministradores;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarPeriodosLiquidadosDispersionFallecimiento(java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String consultarPeriodosLiquidadosDispersionFallecimiento(String numeroRadicacion, String query) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarPeriodosLiquidadosDispersionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StringBuilder tablaPeriodos = new StringBuilder();
        List<Object[]> lstPeriodosLiquidados = null;

        try {
            lstPeriodosLiquidados = entityManagerCore.createNamedQuery(query)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            tablaPeriodos.append(
                    "<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>PERIODO</th><th>MONTO</th></tr>");

            for (Object[] item : lstPeriodosLiquidados) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                Date fechaPeriodo = dateFormat.parse(item[0].toString());
                String periodo = dateFormat.format(fechaPeriodo);
                tablaPeriodos.append("<tr><td>" + periodo + "</td>");
                tablaPeriodos.append("<td>" + BigDecimal.valueOf(Double.valueOf(item[1].toString())).intValue() + "</td> </tr>");
            }
            tablaPeriodos.append("</table>");

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return lstPeriodosLiquidados.isEmpty() ? null : tablaPeriodos.toString();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarInforUbicacionAdminTrabajadorDispersion508(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Object[] consultarInforUbicacionAdminTrabajadorDispersion508(Long id) {
        String firmaMetodo = "SubsidioMonetarioBusiness.consultarInforUbicacionAdminTrabajadorDispersion508(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Object[] infoPersona = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_UBICACION_TRABAJADOR_ADMIN_DISPERSION_FALLECIMIENTO)
                    .setParameter("idPersona", id).getSingleResult();

            return infoPersona;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerLiquidacionesporTrabajadorVista360(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void obtenerLiquidacionesporTrabajadorVista360(List<ConsultaLiquidacionSubsidioMonetarioDTO> resultado) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerLiquidacionesporTrabajadorVista360(List<ConsultaLiquidacionSubsidioMonetarioDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Object[]> lstRegistros = null;
        try {
            lstRegistros = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_LIQUIDACIONES_TRABAJADOR_VISTA360)
                    .setParameter("lstNumeroRadicados",
                            resultado.stream().map(registro -> registro.getNumeroRadicado()).collect(Collectors.toList()))
                    .getResultList();

        } catch (Exception e) {
            logger.debug("Ocurrio un error inesperado " + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        final List<Object[]> lstResultado = lstRegistros;
        resultado.removeIf(registro -> {
            String reg = null;
            for (Object[] item : lstResultado) {
                if (registro.getNumeroRadicado().equals(item[0].toString())) {
                    try {
                        if (item[2] != null) {
                            if (ResultadoProcesoEnum.DISPERSADA.name().equals(item[2].toString())) {
                                registro.setEstadoLiquidacion("APROBADA");
                            } else {
                                registro.setEstadoLiquidacion("RECHAZADA");
                            }

                            registro.setResultadoProceso(ResultadoProcesoEnum.valueOf(item[2].toString()));
                        }
                    } catch (Exception e) {
                        logger.error("Valor no esperado en el estado del resultado del proceso" + firmaMetodo);
                    }
                    reg = item[1] != null ? item[1].toString() : null;
                    break;
                }
            }
            if (reg != null) {
                if ((registro.getTipoLiquidacion().equalsIgnoreCase(TipoLiquidacionEnum.MASIVA.name()) && reg.equalsIgnoreCase(ResultadoProcesoEnum.DISPERSADA.name()))
                        || (!registro.getTipoLiquidacion().equalsIgnoreCase(TipoLiquidacionEnum.MASIVA.name())
                        && reg.equalsIgnoreCase(EstadoProcesoLiquidacionEnum.CERRADA.name()))) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        });

    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#consultarInforUbicacionAdminTrabajadorDispersion508(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TrazabilidadSubsidioEspecificoDTO> obtenerTrazabilidadSubsidioEspecifico(Long identificadorLiquidacion) {
        try {
            logger.debug("Inicio de método obtenerTrazabilidadSubsidioEspecifico");
            List<TrazabilidadSubsidioEspecificoDTO> trazabilidadSubsidioDTO = new ArrayList<TrazabilidadSubsidioEspecificoDTO>();

            List<Object[]> trazabilidadSubsidio = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_ESTADOS_SOLICITUD_LIQUIDACION_SUBSIDIO)
                    .setParameter("identificadorSolicitud", identificadorLiquidacion).getResultList();

            for (Object[] registroTrazabilidad : trazabilidadSubsidio) {
                TrazabilidadSubsidioEspecificoDTO trazabilidadDTO = new TrazabilidadSubsidioEspecificoDTO();
                trazabilidadDTO = trazabilidadDTO.convertirTrazabilidad(registroTrazabilidad);
                trazabilidadSubsidioDTO.add(trazabilidadDTO);
            }
            logger.debug("Fin de método obtenerTrazabilidadSubsidioEspecifico");
            return trazabilidadSubsidioDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerTrazabilidadSubsidioEspecifico", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaCCFModeloDTO obtenerCuentaCCF() {
        CuentaCCFModeloDTO cuentaDTO = new CuentaCCFModeloDTO();
        try {
            try {
                logger.debug("Inicio de método obtenerCuentaCCF");
                CuentaCCF cuenta = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_CCF, CuentaCCF.class)
                        .getSingleResult();
                cuentaDTO.convertToDTO(cuenta);
            } catch (NoResultException e) {
            }

        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerCuentaCCF", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug("Fin de método obtenerCuentaCCF");
        return cuentaDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<BancoModeloDTO> obtenerBancos() {
        logger.debug("Inicio de método obtenerCuentaCCF");
        List<BancoModeloDTO> bancosDTO = new ArrayList<>();
        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();
        List<Banco> bancos = JPAUtils.consultaEntidad(entityManagerCore, Banco.class,
                fields, values);
        for (Banco banco : bancos) {
            BancoModeloDTO banDTO = new BancoModeloDTO();
            banDTO.setId(banco.getId());
            banDTO.setNombre(banco.getNombre());
            bancosDTO.add(banDTO);
        }
        logger.debug("Fin de método obtenerBancos");
        return bancosDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Long registrarCuentaCCF(CuentaCCFModeloDTO cuentaDTO) {
        String firmaMetodo = "ConsultasModeloCore.registrarCuentaCCF(CuentaCCFModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_CUENTAS_CCF).executeUpdate();
            CuentaCCF cuenta = cuentaDTO.convertToEntity();
            entityManagerCore.persist(cuenta);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return cuenta.getIdCuentaCCF();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    public Long obtenerValorSecuencia(int cantidadValores, String nombreSecuencia) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerValorSecuencia(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Ejecutar el SP para determinar las condiciones que cumple
        StoredProcedureQuery storedProcedure = entityManagerCore
                .createStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_SP_GESTOR_VALOR_SECUENCIA);
        storedProcedure.registerStoredProcedureParameter("iCantidadValores", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("sNombreSecuencia", String.class, ParameterMode.IN);
        storedProcedure.setParameter("iCantidadValores", cantidadValores);
        storedProcedure.setParameter("sNombreSecuencia", nombreSecuencia);

        //Se reciben los parametros de salida del Stored procedure
        storedProcedure.registerStoredProcedureParameter("iPrimerValor", Long.class, ParameterMode.OUT);

        storedProcedure.execute();

        return (Long) storedProcedure.getOutputParameterValue("iPrimerValor");
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long persistirBloqueoBeneficiario(ArrayList<String[]> lineas, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, CargueArchivoBloqueoCMDTO cargue) {
        CargueBloqueoCuotaMonetaria cargueBloqueoCuotaMonetaria = new CargueBloqueoCuotaMonetaria();
            
        logger.info("ENTRA EN EL 1");
        cargueBloqueoCuotaMonetaria.setFechaCarga(new Date());
        cargueBloqueoCuotaMonetaria.setPeriodoInicio(cargue.getFechaInicioPeriodo());
        cargueBloqueoCuotaMonetaria.setPeriodoFin(cargue.getFechaFinPeriodo());
        cargueBloqueoCuotaMonetaria.setRadicado(Boolean.FALSE);
        entityManagerCore.persist(cargueBloqueoCuotaMonetaria);

        int cantidadRegistros = lineas.size();
        Long primerValor = obtenerValorSecuencia(cantidadRegistros, NamedQueriesConstants.SECUENCIA_BLOQUEO_BENEFICIARIO_CUOTAMONETARIA);
        int numLinea = 1;
        for (String[] linea : lineas) {

            BloqueoBeneficiarioCuotaMonetaria bfcm = new BloqueoBeneficiarioCuotaMonetaria();
            bfcm.setIdBloqueoBeneficiarioCuotaMonetaria(primerValor);
            bfcm.setTipoIdentificacionBeneciario(TipoIdentificacionEnum
                    .valueOf(TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[0]).getValorCampo()));
            bfcm.setNumeroIdentificacionBeneficiario(linea[1]);
            bfcm.setCausalBloqueoCuotaMonetaria(CausalBloqueoCuotaMonetariaEnum.valueOf(linea[3]));
            bfcm.setCargueBloqueoCuotaMonetaria(cargueBloqueoCuotaMonetaria);
            bfcm.setBloqueado(Boolean.TRUE);
            bfcm.setNombrePersona(linea[2]);
            bfcm.setNumLinea(numLinea);
            numLinea++;
            entityManagerCore.persist(bfcm);
            primerValor += 1;
        }
        entityManagerCore.flush();

        return cargueBloqueoCuotaMonetaria.getIdCargueBloqueoCuotaMonetaria();
    }
      public Boolean validarExistenciaRelacionAfiliadoBeneficiario(String tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {
        try{
            Object[] afiliadoRelacionado = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_POR_BENEFICIARIO)
            .setParameter("tipoIdentificacionAfi", TipoDocumentoBloqueoBeneficiarioEnum.valueOf(tipoIdentificacionAfiliado).getValorCampo())
            .setParameter("afiNumeroIdentificacion", numeroIdentificacionAfiliado)
            .setParameter("tipoIdentificacionBen", TipoDocumentoBloqueoBeneficiarioEnum.valueOf(tipoIdentificacionBeneficiario).getValorCampo())
            .setParameter("benNumeroIdentificacion", numeroIdentificacionBeneficiario)
            .getSingleResult();
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
      }
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long persistirBloqueoTrabajadorBeneficiario(ArrayList<String[]> lineas, ResultadoValidacionArchivoBloqueoCMDTO resultadoValidacion, CargueArchivoBloqueoCMDTO cargue) {
        logger.info("Bloqueo trabajador beneficiario");

        BloqueoAfiliadoBeneficiarioCM bloqueoAfiliadoBeneficiarioCM = new BloqueoAfiliadoBeneficiarioCM();
        CargueBloqueoCuotaMonetaria cargueBloqueoCuotaMonetaria = new CargueBloqueoCuotaMonetaria();
        
        // Persistencia en tabla CargueBloqueoCuotaMonetaria
        cargueBloqueoCuotaMonetaria.setFechaCarga(new Date());
        cargueBloqueoCuotaMonetaria.setPeriodoInicio(cargue.getFechaInicioPeriodo());
        cargueBloqueoCuotaMonetaria.setPeriodoFin(cargue.getFechaFinPeriodo());
        cargueBloqueoCuotaMonetaria.setRadicado(Boolean.FALSE);

        entityManagerCore.persist(cargueBloqueoCuotaMonetaria);

        int cantidadRegistros = lineas.size();
        Long primerValor = obtenerValorSecuencia(cantidadRegistros, NamedQueriesConstants.SECUENCIA_BLOQUEO_BENEFICIARIO_CUOTAMONETARIA);
        int numLinea = 1;

        for (String[] linea : lineas) {
            BloqueoBeneficiarioCuotaMonetaria bfcm = new BloqueoBeneficiarioCuotaMonetaria();
            Object[] afiliadoRelacionado = null;
            try{
            afiliadoRelacionado = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_POR_BENEFICIARIO)
            .setParameter("tipoIdentificacionAfi", TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[0]).getValorCampo())
            .setParameter("afiNumeroIdentificacion", linea[1].toString())
            .setParameter("tipoIdentificacionBen", TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[3]).getValorCampo())
            .setParameter("benNumeroIdentificacion", linea[4].toString())
            .getSingleResult();
            }catch (NoResultException e) {
                logger.warn("No se encontró afiliado para la línea " + numLinea + ": " + Arrays.toString(linea));
                continue; // saltar esta línea o manejarla como error
            }
            // Persistencia en tabla subsidio
            
            bloqueoAfiliadoBeneficiarioCM.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[0]).getValorCampo()));
            bloqueoAfiliadoBeneficiarioCM.setNumeroIdentificacionAfiliado(linea[1].toString());
            bloqueoAfiliadoBeneficiarioCM.setPersonaAfiliado(Long.parseLong(afiliadoRelacionado[0].toString()));
            bloqueoAfiliadoBeneficiarioCM.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[3]).getValorCampo()));
            bloqueoAfiliadoBeneficiarioCM.setNumeroIdentificacionBeneficiario(linea[4].toString());
            bloqueoAfiliadoBeneficiarioCM.setPersonaBeneficiario(Long.parseLong(afiliadoRelacionado[1].toString()));
            bloqueoAfiliadoBeneficiarioCM.setMotivoNoAcreditado(false);
            bloqueoAfiliadoBeneficiarioCM.setMotivoFraude(false);
            bloqueoAfiliadoBeneficiarioCM.setMotivoOtro(true);
            bloqueoAfiliadoBeneficiarioCM.setBloqueado(true);
            bloqueoAfiliadoBeneficiarioCM.setPeriodoInicio(cargue.getFechaInicioPeriodo());
            bloqueoAfiliadoBeneficiarioCM.setPeriodoFin(cargue.getFechaFinPeriodo());
            bloqueoAfiliadoBeneficiarioCM.setBbcId(primerValor);
            bloqueoAfiliadoBeneficiarioCM.setCausalBloqueo(CausalBloqueoCuotaMonetariaEnum.valueOf(linea[6].toString()));

            // Persistencia en tabla core 
            bfcm.setIdBloqueoBeneficiarioCuotaMonetaria(primerValor);
            bfcm.setRelacionBeneficiario(Long.parseLong(afiliadoRelacionado[2].toString()));
            bfcm.setTipoIdentificacionBeneciario(TipoIdentificacionEnum.valueOf(TipoDocumentoBloqueoBeneficiarioEnum.valueOf(linea[3]).getValorCampo()));
            bfcm.setNumeroIdentificacionBeneficiario(linea[4].toString());
            bfcm.setNombrePersona(linea[5].toString());
            bfcm.setCausalBloqueoCuotaMonetaria(CausalBloqueoCuotaMonetariaEnum.valueOf(linea[6].toString()));
            bfcm.setCargueBloqueoCuotaMonetaria(cargueBloqueoCuotaMonetaria);
            bfcm.setBloqueado(Boolean.TRUE);
            bfcm.setNumLinea(numLinea);

            // Envio a base de datos
            consultasSubsidio.insertarDatosSubsidioBloqueoAfiliadoBeneficiarioCM(bloqueoAfiliadoBeneficiarioCM);
            entityManagerCore.persist(bfcm);
            
            numLinea++;
            primerValor += 1;
        }
        entityManagerCore.flush();
        logger.info("Finaliza");
        return cargueBloqueoCuotaMonetaria.getIdCargueBloqueoCuotaMonetaria();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public ResultadoValidacionArchivoBloqueoCMDTO validarExistenciaBeneficiarios(Long idCargueBloqueoCuotaMonetaria, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.validarExistenciaBeneficiarios(Long)" + idCargueBloqueoCuotaMonetaria;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        int registrosActualizados = 0;

        ResultadoValidacionArchivoBloqueoCMDTO validacionBen = new ResultadoValidacionArchivoBloqueoCMDTO();

        registrosActualizados = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_BENEFICIARIOS_BLOQUEO)
                .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria).executeUpdate();

        validacionBen.setNumeroRegistrosExitosos(registrosActualizados);

        /*entityManagerCore.createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_BENEFICIARIOS_BLOQUEO_AUD)
				.setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria).executeUpdate();*/
        List<Object[]> beneficiariosNoExisten = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_NOEXISTEN)
                .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria).getResultList();

        if (beneficiariosNoExisten != null && !beneficiariosNoExisten.isEmpty()) {
            List<String[]> benInconsistentes = new ArrayList<>();
            for (Object[] linea : beneficiariosNoExisten) {

                // Se agrega la validacion a la linea de inconsistente
                String[] lineaError = new String[6];
                lineaError[0] = (String) linea[1];
                lineaError[1] = (String) linea[2];
                lineaError[2] = (String) linea[3];
                lineaError[3] = (String) linea[4];
                
                lineaError[4] = "No existe beneficiario";
                benInconsistentes.add(lineaError);
            }

            validacionBen.setLineasError(benInconsistentes);

            entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_BENEFICIARIO_NO_EXISTENTES_BLOQUEO)
                    .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria).executeUpdate();
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            userDTO.setEmail("");
        }

        /*Se crea las tablas de auditoria para las tablas cargueBloqueoMonetario y bloqueoMonetario*/
        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idCargueCuotaMonetaria", Long.class, ParameterMode.IN)
                .setParameter("sUsuario", userDTO.getEmail())
                .setParameter("idCargueCuotaMonetaria", idCargueBloqueoCuotaMonetaria).execute();

        /*entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_BENEFICIARIO_NO_EXISTENTES_BLOQUEO_AUD)
				.setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria).executeUpdate();*/
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return validacionBen;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int radicarBloqueoCM(Long idCargueBloqueoCuotaMonetaria) {
        String firmaMetodo = "ConsultasModeloCore.validarExistenciaBeneficiarios(Long)" + idCargueBloqueoCuotaMonetaria;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        int registrosActualizados = 0;
        try {
        CargueBloqueoCuotaMonetaria c = entityManagerCore.find(CargueBloqueoCuotaMonetaria.class, idCargueBloqueoCuotaMonetaria);
        c.setRadicado(Boolean.TRUE);
        entityManagerCore.merge(c);
        /*
              registrosActualizados = entityManagerCore.createNamedQuery(NamedQueriesConstants.RADICAR_BLOQUEO_CM)
              .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
              .executeUpdate();    
        */      
        entityManagerCore.createNamedQuery(NamedQueriesConstants.RADICAR_BLOQUEO_CM_AUD)
        .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
        .executeUpdate();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
              logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
              throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        return registrosActualizados;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int cancelarBloqueoCM(Long idCargueBloqueoCuotaMonetaria) {
        String firmaMetodo = "ConsultasModeloCore.validarExistenciaBeneficiarios(Long)" + idCargueBloqueoCuotaMonetaria;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        int registrosActualizados = 0;
        try {
            registrosActualizados = entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_BLOQUEO_BENEFICIARIO_CUOTACM_POR_ID_CARGUE)
                    .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
                    .executeUpdate();

            entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_BLOQUEO_BENEFICIARIO_CUOTACM_POR_ID_CARGUE_AUD)
                    .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
                    .executeUpdate();

            entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_CARGUE_BLOQUEO_DM_POR_ID)
                    .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
                    .executeUpdate();

            entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_CARGUE_BLOQUEO_DM_POR_ID_AUD)
                    .setParameter("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
                    .executeUpdate();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return registrosActualizados;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<BeneficiarioModeloDTO> consultarBeneficiario(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, Date fechaNacimiento) {

        String firmaServicio = "AfiliadosBusiness.consultarBeneficiarioPrincipal(TipoIdentificacionEnum,String,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<BeneficiarioModeloDTO> listaBeneficiarios = null;

        List<Object[]> lstBeneficiarios = null;

        final String PORCENTAJE = "%";

        String parametroPrimerNombre = null;
        String parametroSegundoNombre = null;
        String parametroPrimerApellido = null;
        String parametroSegundoApellido = null;

        if (primerNombre != null) {
            parametroPrimerNombre = PORCENTAJE + primerNombre + PORCENTAJE;
        }
        if (segundoNombre != null) {
            parametroSegundoNombre = PORCENTAJE + segundoNombre + PORCENTAJE;
        }
        if (primerApellido != null) {
            parametroPrimerApellido = PORCENTAJE + primerApellido + PORCENTAJE;
        }
        if (segundoApellido != null) {
            parametroSegundoApellido = PORCENTAJE + segundoApellido + PORCENTAJE;
        }

        Date fechaNa = (fechaNacimiento == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate("1800-01-01") : fechaNacimiento;

        lstBeneficiarios = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_POR_FILTROS)
                .setParameter("tipoIdentificacion",
                        tipoIdentificacion != null ? tipoIdentificacion.toString() : null)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("primerNombre", parametroPrimerNombre)
                .setParameter("segundoNombre", parametroSegundoNombre)
                .setParameter("primerApellido", parametroPrimerApellido)
                .setParameter("segundoApellido", parametroSegundoApellido)
                .setParameter("fechaNacimiento", fechaNa)
                .getResultList();
        /*} catch (Exception e) {
            logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/

        if (lstBeneficiarios.isEmpty()) {

            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return listaBeneficiarios;
        }

        listaBeneficiarios = new ArrayList<>();

        for (int i = 0; i < lstBeneficiarios.size(); i++) {

            Persona persona = new Persona();
            persona.setIdPersona(Long.valueOf((lstBeneficiarios.get(i)[0]).toString()));
            persona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(lstBeneficiarios.get(i)[1].toString()));
            persona.setNumeroIdentificacion(lstBeneficiarios.get(i)[2].toString());
            persona.setRazonSocial(lstBeneficiarios.get(i)[3] == null ? "" : lstBeneficiarios.get(i)[3].toString());

            Beneficiario ben = new Beneficiario();
            ben.setPersona(persona);
            ben.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.valueOf(lstBeneficiarios.get(i)[4].toString()));

            BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO(ben, null);
            listaBeneficiarios.add(beneficiarioModeloDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaBeneficiarios.isEmpty() ? null : listaBeneficiarios;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long persistirBloqueoBeneficiario(CargueBloqueoCMDTO cargue) {
        CargueBloqueoCuotaMonetaria cargueBloqueoCuotaMonetaria = new CargueBloqueoCuotaMonetaria();
        BloqueoAfiliadoBeneficiarioCM bloqueoAfiliadoBeneficiarioCM = new BloqueoAfiliadoBeneficiarioCM();
        try {
            int cantidadRegistros = cargue.getBeneficiariosBloqueados().size();
            Long primerValor = obtenerValorSecuencia(cantidadRegistros, NamedQueriesConstants.SECUENCIA_BLOQUEO_BENEFICIARIO_CUOTAMONETARIA);

            cargueBloqueoCuotaMonetaria.setFechaCarga(new Date());
            cargueBloqueoCuotaMonetaria.setPeriodoInicio(cargue.getFechaInicioPeriodo());
            cargueBloqueoCuotaMonetaria.setPeriodoFin(cargue.getFechaFinPeriodo());
            cargueBloqueoCuotaMonetaria.setRadicado(true);  
            entityManagerCore.persist(cargueBloqueoCuotaMonetaria);
            
            for (BloqueoBeneficiarioCuotaMonetariaDTO bloqueoBen : cargue.getBeneficiariosBloqueados()) {
                BloqueoBeneficiarioCuotaMonetaria bfcm = new BloqueoBeneficiarioCuotaMonetaria();
                bfcm.setIdBloqueoBeneficiarioCuotaMonetaria(primerValor);
                Persona persona = new Persona();
                persona.setIdPersona(bloqueoBen.getPersonaBeneficiario());
                bfcm.setPersona(persona);
                bfcm.setTipoIdentificacionBeneciario(bloqueoBen.getTipoIdentificacionBeneciario());
                bfcm.setNumeroIdentificacionBeneficiario(bloqueoBen.getNumeroIdentificacionBeneficiario());
                bfcm.setCargueBloqueoCuotaMonetaria(cargueBloqueoCuotaMonetaria);
                if (cargue.getNivelBloqueo() == TipoNivelBloqueoEnum.BLOQUEO_TRABAJADOR_SUBSIDIO) {
                    // Se guarda la relacion beneficiario en CORE
                    bfcm.setRelacionBeneficiario(Long.parseLong(bloqueoBen.getIdBen()));

                    if(!bloqueoBen.getCausalBloqueoCuotaMonetaria().equals(CausalBloqueoCuotaMonetariaEnum.AFILIADO_DEPENDIENTE_VETERANO_FUERZA_PUBLICA)){                        
                        // Se envian los valores del beneficiario bloqueado con relacion al afiliado
                        // hacia el schema subsidio, tabla bloqueoAfiliadoBeneficiarioCM
                        Object[] afiliadoRelacionado = (Object[]) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_RELACION_POR_BENEFICIARIO)
                            .setParameter("benId", bloqueoBen.getIdBen())
                            .getSingleResult();
    
                        if(afiliadoRelacionado != null) {
                            bloqueoAfiliadoBeneficiarioCM.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(afiliadoRelacionado[2].toString()));
                            bloqueoAfiliadoBeneficiarioCM.setNumeroIdentificacionAfiliado(afiliadoRelacionado[3].toString());
                            bloqueoAfiliadoBeneficiarioCM.setPersonaAfiliado(Long.parseLong(afiliadoRelacionado[1].toString()));
                            bloqueoAfiliadoBeneficiarioCM.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(bloqueoBen.getTipoIdentificacionBeneciario().toString()));
                            bloqueoAfiliadoBeneficiarioCM.setNumeroIdentificacionBeneficiario(bloqueoBen.getNumeroIdentificacionBeneficiario());
                            bloqueoAfiliadoBeneficiarioCM.setPersonaBeneficiario(Long.parseLong(afiliadoRelacionado[5].toString()));
                            bloqueoAfiliadoBeneficiarioCM.setMotivoNoAcreditado(false);
                            bloqueoAfiliadoBeneficiarioCM.setMotivoFraude(false);
                            bloqueoAfiliadoBeneficiarioCM.setMotivoOtro(true);
                            bloqueoAfiliadoBeneficiarioCM.setBloqueado(true);
                            bloqueoAfiliadoBeneficiarioCM.setPeriodoInicio(cargue.getFechaInicioPeriodo());
                            bloqueoAfiliadoBeneficiarioCM.setPeriodoFin(cargue.getFechaFinPeriodo());
                            bloqueoAfiliadoBeneficiarioCM.setBbcId(primerValor);
                            bloqueoAfiliadoBeneficiarioCM.setCausalBloqueo(bloqueoBen.getCausalBloqueoCuotaMonetaria());
                        }
                        consultasSubsidio.insertarDatosSubsidioBloqueoAfiliadoBeneficiarioCM(bloqueoAfiliadoBeneficiarioCM);
                    }
                }
                bfcm.setCausalBloqueoCuotaMonetaria(bloqueoBen.getCausalBloqueoCuotaMonetaria());
                entityManagerCore.persist(bfcm);
                primerValor += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cargueBloqueoCuotaMonetaria.getIdCargueBloqueoCuotaMonetaria();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    public Long persistirBloqueoBeneficiarioAuditoria(Long idcargue, UserDTO userDTO) {
        String firmaServicio = "ConstulasModeloCore.persistirBloqueoBeneficiarioAuditoria(idcargue,userDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idCargueCuotaMonetaria", Long.class, ParameterMode.IN)
                .setParameter("sUsuario", userDTO.getEmail())
                .setParameter("idCargueCuotaMonetaria", idcargue).execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idcargue;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
//    @Override
//    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueados(ConsultaBeneficiarioBloqueadosDTO consulta) { 
//
//        String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosBloqueados()";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//
//        List<BloqueoBeneficiarioCuotaMonetariaDTO> listaBeneficiarios = null;   
//        List<Object[]> lstBeneficiarios = null;
//        List<Object[]> lstParAfiBen = null;
//        
//        lstBeneficiarios = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_BLOQUEADOS)                     
//                    .getResultList();
//        /*} catch (Exception e) {
//            logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
//            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//        }*/
//        
//        
//
//        if (lstBeneficiarios.isEmpty()) {
//            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//            return listaBeneficiarios;
//        }
//
//        listaBeneficiarios = new ArrayList<>();
//
//        for (int i = 0; i < lstBeneficiarios.size(); i++) {
//            BloqueoBeneficiarioCuotaMonetariaDTO bloqueo = new BloqueoBeneficiarioCuotaMonetariaDTO();
//            bloqueo.setIdBloqueoBeneficiarioCuotaMonetaria(Long.valueOf((lstBeneficiarios.get(i)[0]).toString()));
//            bloqueo.setPersonaBeneficiario(Long.valueOf((lstBeneficiarios.get(i)[1]).toString()));
//            bloqueo.setTipoIdentificacionBeneciario(TipoIdentificacionEnum.valueOf(lstBeneficiarios.get(i)[2].toString()));             
//            bloqueo.setNumeroIdentificacionBeneficiario(lstBeneficiarios.get(i)[3].toString());             
//            bloqueo.setRazonSocialBeneficiario(lstBeneficiarios.get(i)[4]==null?"": lstBeneficiarios.get(i)[4].toString());  
//            bloqueo.setEstadoBeneficiario(lstBeneficiarios.get(i)[5].toString());
//            bloqueo.setAsignacionCuotaPorOtraCCF(lstBeneficiarios.get(i)[6]==null?null:((Boolean)lstBeneficiarios.get(i)[6]));
//            bloqueo.setBeneficiarioComoAfiliadoOtraCCF(lstBeneficiarios.get(i)[7]==null?null:((Boolean)lstBeneficiarios.get(i)[7]));
//            bloqueo.setPeriodoInicio((Date)lstBeneficiarios.get(i)[8]);
//            bloqueo.setPeriodoFin((Date)lstBeneficiarios.get(i)[9]);            
//            listaBeneficiarios.add(bloqueo);
//        }
//        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
//        return listaBeneficiarios.isEmpty() ? null : listaBeneficiarios;
//    }
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueadosCoreFiltros(ConsultaBeneficiarioBloqueadosDTO consulta) {

        String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosBloqueados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<BloqueoBeneficiarioCuotaMonetariaDTO> listaBeneficiarios = null;
        List<Object[]> lstBeneficiarios = null;

        final String PORCENTAJE = "%";

        String parametroPrimerNombre = null;
        String parametroSegundoNombre = null;
        String parametroPrimerApellido = null;
        String parametroSegundoApellido = null;

        if (consulta.getPrimerNombre() != null) {
            parametroPrimerNombre = PORCENTAJE + consulta.getPrimerNombre() + PORCENTAJE;
        }
        if (consulta.getSegundoNombre() != null) {
            parametroSegundoNombre = PORCENTAJE + consulta.getSegundoNombre() + PORCENTAJE;
        }
        if (consulta.getPrimerApellido() != null) {
            parametroPrimerApellido = PORCENTAJE + consulta.getPrimerApellido() + PORCENTAJE;
        }
        if (consulta.getSegundoApellido() != null) {
            parametroSegundoApellido = PORCENTAJE + consulta.getSegundoApellido() + PORCENTAJE;
        }

        Date fechaNa = (consulta.getFechaNacimiento() == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate("1800-01-01") : consulta.getFechaNacimiento();

        lstBeneficiarios = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_BLOQUEADOS_POR_FILTROS)
                .setParameter("tipoIdentificacion",
                        consulta.getTipoIdentificacion() != null ? consulta.getTipoIdentificacion().toString() : null)
                .setParameter("numeroIdentificacion", consulta.getNumeroIdentificacion())
                .setParameter("primerNombre", parametroPrimerNombre)
                .setParameter("segundoNombre", parametroSegundoNombre)
                .setParameter("primerApellido", parametroPrimerApellido)
                .setParameter("segundoApellido", parametroSegundoApellido)
                .setParameter("fechaNacimiento", fechaNa)
                .getResultList();
        /*} catch (Exception e) {
            logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/

        if (lstBeneficiarios.isEmpty()) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return listaBeneficiarios;
        }

        listaBeneficiarios = new ArrayList<>();

        for (int i = 0; i < lstBeneficiarios.size(); i++) {
            BloqueoBeneficiarioCuotaMonetariaDTO bloqueo = new BloqueoBeneficiarioCuotaMonetariaDTO();
            bloqueo.setIdBloqueoBeneficiarioCuotaMonetaria(Long.valueOf((lstBeneficiarios.get(i)[0]).toString()));
            bloqueo.setPersonaBeneficiario(Long.valueOf((lstBeneficiarios.get(i)[1]).toString()));
            bloqueo.setTipoIdentificacionBeneciario(TipoIdentificacionEnum.valueOf(lstBeneficiarios.get(i)[2].toString()));
            bloqueo.setNumeroIdentificacionBeneficiario(lstBeneficiarios.get(i)[3].toString());
            bloqueo.setRazonSocialBeneficiario(lstBeneficiarios.get(i)[4] == null ? "" : lstBeneficiarios.get(i)[4].toString());
            bloqueo.setEstadoBeneficiario(lstBeneficiarios.get(i)[5].toString());
            // bloqueo.setAsignacionCuotaPorOtraCCF(lstBeneficiarios.get(i)[6] == null ? null : ((Boolean) lstBeneficiarios.get(i)[6]));
            // bloqueo.setBeneficiarioComoAfiliadoOtraCCF(lstBeneficiarios.get(i)[7] == null ? null : ((Boolean) lstBeneficiarios.get(i)[7]));
            if (lstBeneficiarios.get(i)[8] != null) {
                bloqueo.setCausalBloqueoCuotaMonetaria(CausalBloqueoCuotaMonetariaEnum.valueOf(lstBeneficiarios.get(i)[8].toString()));
            } else {
                bloqueo.setCausalBloqueoCuotaMonetaria(null);
            }            
            
            if (lstBeneficiarios.get(i)[12] != null) {
                bloqueo.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(lstBeneficiarios.get(i)[12].toString()));
            } else {
                bloqueo.setTipoIdentificacionAfiliado(null);
            }   

            if (lstBeneficiarios.get(i)[13] != null) {
                bloqueo.setNumeroIdentificacionAfiliado(lstBeneficiarios.get(i)[13].toString());
            } else {
                bloqueo.setNumeroIdentificacionAfiliado(null);
            }   

            bloqueo.setPeriodoInicio((Date) lstBeneficiarios.get(i)[9]);
            bloqueo.setPeriodoFin((Date) lstBeneficiarios.get(i)[10]);
            bloqueo.setRelacionAfiBen(lstBeneficiarios.get(i)[11].toString());
            listaBeneficiarios.add(bloqueo);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaBeneficiarios.isEmpty() ? null : listaBeneficiarios;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#obtenerAniosCondicionesParametrizadosSubsidio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int desbloquearBeneficiariosCMCore(List<Long> idBeneficiarioBloqueados, UserDTO userDTO) {
        String firmaMetodo = "ConsultasModeloCore.validarExistenciaBeneficiarios(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<BloqueoBeneficiarioCuotaMonetaria> bens = new ArrayList<>();
        try {

            bens = entityManagerCore.createNamedQuery(NamedQueriesConstants.DESBLOQUEAR_CUOTA_MONETARIA_BENEFICIARIOS, BloqueoBeneficiarioCuotaMonetaria.class)
                    .setParameter("idsCargueBloqueoCuotaMonetaria", idBeneficiarioBloqueados).getResultList();

            for (BloqueoBeneficiarioCuotaMonetaria bloqueoBeneficiarioCuotaMonetaria : bens) {
                bloqueoBeneficiarioCuotaMonetaria.setBloqueado(Boolean.FALSE);
                entityManagerCore.merge(bloqueoBeneficiarioCuotaMonetaria);
                persistirDesbloqueoBeneficiarioAuditoria(bloqueoBeneficiarioCuotaMonetaria.getIdBloqueoBeneficiarioCuotaMonetaria(), userDTO);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return bens.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore#seleccionarPersonaSubsidioFallecimientoTrabajador(java.lang.Long)
     */
    @Override
    public Long persistirDesbloqueoBeneficiarioAuditoria(Long idBloqueo, UserDTO userDTO) {
        String firmaServicio = "ConstulasModeloCore.persistirBloqueoBeneficiarioAuditoria(idcargue,userDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria)
                .registerStoredProcedureParameter("sUsuario", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idBloqueo", Long.class, ParameterMode.IN)
                .setParameter("sUsuario", userDTO.getEmail())
                .setParameter("idBloqueo", idBloqueo).execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idBloqueo;
    }

    @Override
    public String consultarTipoLiquidacionParalela(String numeroRadicado) {
        String result = null;

        result = (String) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_LIQUIDACION_A_APROBAR)
                .setParameter("numeroRadicado", numeroRadicado).getSingleResult();

        return result;
    }

    @Override
    public VerificarPersonasSinCondicionesDTO consultarPersonaLiquidacionEspecifica(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarPersonaLiquidacionEspecifica()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Integer> idEmpleador = new ArrayList<Integer>();
        List<Integer> idAfiliadoPrincipal = new ArrayList<Integer>();
        List<Integer> idBeneficiarioDetalle = new ArrayList<Integer>();
        List<Integer> idGrupoFamiliar = new ArrayList<Integer>();
        List<Integer> idEmpresa = new ArrayList<Integer>();
        List<Long> periodos = new ArrayList<Long>();
        PersonaLiquidacionEspecificaDTO resultPersonas = new PersonaLiquidacionEspecificaDTO();
        VerificarPersonasSinCondicionesDTO listasPersonas = new VerificarPersonasSinCondicionesDTO();

        try {
            List<Object[]> personas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_SIN_CONDICIONES)
                    .setParameter("numeroRadicado", numeroRadicado).getResultList();

            for (Object[] persona : personas) {
                if (persona[0] != null) {
                    idEmpleador.add(Integer.parseInt(persona[0].toString()));
                }

                if (persona[1] != null) {
                    idAfiliadoPrincipal.add(Integer.parseInt(persona[1].toString()));
                }

                if (persona[2] != null) {
                    idBeneficiarioDetalle.add(Integer.parseInt(persona[2].toString()));
                }

                if (persona[3] != null) {
                    idGrupoFamiliar.add(Integer.parseInt(persona[3].toString()));
                }

                if (persona[4] != null) {
                    idEmpresa.add(Integer.parseInt(persona[4].toString()));
                }

                if (persona[5] != null) {
                    periodos.add((CalendarUtils.darFormatoYYYYMMDDGuionDate(persona[5].toString())).getTime());
                }

            }

            resultPersonas.setIdEmpleador(idEmpleador);
            resultPersonas.setIdAfiliadoPrincipal(idAfiliadoPrincipal);
            resultPersonas.setIdBeneficiarioDetalle(idBeneficiarioDetalle);
            resultPersonas.setIdGrupoFamiliar(idGrupoFamiliar);
            resultPersonas.setIdEmpresa(idEmpresa);
            resultPersonas.setPeriodos(periodos);

            if (!resultPersonas.getIdAfiliadoPrincipal().isEmpty()) {
                listasPersonas.setIdPersonas(resultPersonas.getIdAfiliadoPrincipal());
                listasPersonas.setIdEmpleadores(new ArrayList<Integer>());
            } else {
                listasPersonas.setIdEmpleadores(resultPersonas.getIdEmpleador());
                listasPersonas.setIdPersonas(new ArrayList<Integer>());
            }

            listasPersonas.setPeriodos(resultPersonas.getPeriodos());

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listasPersonas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean consultarBeneficiariosExistentes(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        String firmaMetodo = "consultarBeneficiariosExistentes(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)";
        List<Object[]> beneficiario = new ArrayList<>();
        Boolean existeBeneficiario;

        try {

            beneficiario = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_BLOQUEO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getResultList();

            if (beneficiario.isEmpty() || beneficiario == null) {
                existeBeneficiario = Boolean.FALSE;
            } else {
                existeBeneficiario = Boolean.TRUE;
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return existeBeneficiario;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> consultarBeneficiarioBloqueadosCore() {

        List<Object[]> benbloqCore = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_BLOQUEADOS)
                .getResultList();

        return benbloqCore;
    }

    @Override
    public Boolean consultarExistenciaBeneficiariosBloqueadosCore() {

        String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosBloqueados()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Object resultado;
        try {
            resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_BENEFICIARIOS_BLOQUEADOS_CORE)
                    .getSingleResult();

        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return Boolean.FALSE;
        } catch (NonUniqueResultException nur) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return Boolean.TRUE;
        }

        if (resultado != null) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            return Boolean.TRUE;
        } else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return Boolean.FALSE;
        }

    }

    /**
     * @param numeroRadicado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date consultarBeneficiarioFallecidoPorNumeroRadicado(String numeroRadicado) {
        String firmaMetodo = "ConsultasModeloCore.consultarPersonaFallecidaTrabajador_Beneficiarios(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            Date fechaFallecimiento = (Date) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_FALLECIDOS_POR_NUMERO_LIQUIDACION)
                    .setParameter("numeroRadicacion", numeroRadicado).getSingleResult();

            return fechaFallecimiento;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

    }

    @Override
    public void actualizarEcmArchivoLiquidacionSubsidio(String numeroRadicacion, String ecmArchivo) {
        String firmaMetodo = "ConsultasModeloCore.actualizarEcmArchivoLiquidacionSubsidio(String numeroRadicacion)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ECM_ARCHIVO_LIQUIDACION_SUBSIDIO)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .setParameter("ecmArchivo", ecmArchivo)
                    .executeUpdate();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        }
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, String> consultarEntidadesDescuento() {
        String firmaMetodo = "ConsultasModeloCore.consultarEntidadesDescuento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, String> entidadesDescuentoMap = new HashMap<>();
        try {
            List<Object[]> entidadesDescuento = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_DESCUENTO).getResultList();

            if (entidadesDescuento != null && !entidadesDescuento.isEmpty()) {
                for (Object[] entidad : entidadesDescuento) {
                    Long idEntidad = ((BigInteger) entidad[0]).longValue();
                    String nombreEntidad = entidad[1].toString();
                    entidadesDescuentoMap.put(idEntidad, nombreEntidad);
                }
            }
            return entidadesDescuentoMap;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EspecieLiquidacionManualDTO> consultarSubsidioEspecieLiquidacionManualCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String periodo) {
        String firmaMetodo = "consultarSubsidioEspecieLiquidacionManualCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String Periodo)";
        List<Object[]> reultStoreProcedure = null;
        logger.info("consultarSubsidioEspecieLiquidacionManualCore tipoIdentificacionAfiliado " + tipoIdentificacionAfiliado.name());
        logger.info("consultarSubsidioEspecieLiquidacionManualCore numeroIdentificacionAfiliado " + numeroIdentificacionAfiliado);
        logger.info("consultarSubsidioEspecieLiquidacionManualCore periodo " + periodo);
        try {
            StoredProcedureQuery query = entityManagerCore
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_WEB_SUBSIDIO_ESPECIE_LIQUIDACIÓN_MANUAL);

            query.setParameter("tipoDocumento", tipoIdentificacionAfiliado.name());
            query.setParameter("numeroDocumento", numeroIdentificacionAfiliado);
            query.setParameter("Periodo", periodo);
            //	query.execute();

            //result = (Date) query.getOutputParameterValue("dFechaHabil");
            reultStoreProcedure = query.getResultList();
        } catch (Exception e) {
            logger.info(" :: Hubo un error en el SP Consultas Modelo Core Subsidio Monetario: " + e);
        }

        List<EspecieLiquidacionManualDTO> resulDTO = new ArrayList<>();
        if (reultStoreProcedure != null) {
            for (Object[] reultObj : reultStoreProcedure) {
                EspecieLiquidacionManualDTO datosEspecieLiqManual = new EspecieLiquidacionManualDTO();
                datosEspecieLiqManual.setResultado_consulta(reultObj[0] != null ? reultObj[0].toString() : null);
                datosEspecieLiqManual.setTipo_doc_adminsub(reultObj[1] != null ? reultObj[1].toString() : null);
                datosEspecieLiqManual.setNum_doc_adminsub(reultObj[2] != null ? reultObj[2].toString() : null);
                datosEspecieLiqManual.setPri_nom_adminsub(reultObj[3] != null ? reultObj[3].toString() : null);
                datosEspecieLiqManual.setSeg_nom_admin_sub(reultObj[4] != null ? reultObj[4].toString() : null);
                datosEspecieLiqManual.setPri_ape_admin_sub(reultObj[5] != null ? reultObj[5].toString() : null);
                datosEspecieLiqManual.setSeg_ape_adminsub(reultObj[6] != null ? reultObj[6].toString() : null);
                datosEspecieLiqManual.setCorr_ben_pago(reultObj[7] != null ? reultObj[7].toString() : null);
                datosEspecieLiqManual.setCelular_ben_pago(reultObj[8] != null ? reultObj[8].toString() : null);
                datosEspecieLiqManual.setPri_nom_afiliado(reultObj[9] != null ? reultObj[9].toString() : null);
                datosEspecieLiqManual.setSeg_nom_afiliado(reultObj[10] != null ? reultObj[10].toString() : null);
                datosEspecieLiqManual.setPri_ape_afiliado(reultObj[11] != null ? reultObj[11].toString() : null);
                datosEspecieLiqManual.setSeg_ape_afiliado(reultObj[12] != null ? reultObj[12].toString() : null);
                datosEspecieLiqManual.setTipo_doc_afiliado(reultObj[13] != null ? reultObj[13].toString() : null);
                datosEspecieLiqManual.setNum_doc_afiliado(reultObj[14] != null ? reultObj[14].toString() : null);
                datosEspecieLiqManual.setEstado_afiliado(reultObj[15] != null ? reultObj[15].toString() : null);
                datosEspecieLiqManual.setCategoria_afiliado(reultObj[16] != null ? reultObj[16].toString() : null);
                datosEspecieLiqManual.setClasificacion(reultObj[17] != null ? reultObj[17].toString() : null);
                datosEspecieLiqManual.setClase_trabajador(reultObj[18] != null ? reultObj[18].toString() : null);
                datosEspecieLiqManual.setTipo_afiliacion(reultObj[19] != null ? reultObj[19].toString() : null);
                datosEspecieLiqManual.setFecha_fall_trab(reultObj[20] != null ? reultObj[20].toString() : null);
                datosEspecieLiqManual.setCod_depto_ubicacion(reultObj[21] != null ? reultObj[21].toString() : null);
                datosEspecieLiqManual.setCod_mun_ubicacion(reultObj[22] != null ? reultObj[22].toString() : null);
                datosEspecieLiqManual.setCorreo_afiliado(reultObj[23] != null ? reultObj[23].toString() : null);
                datosEspecieLiqManual.setAporte_pila(reultObj[24] != null ? reultObj[24].toString() : null);
                datosEspecieLiqManual.setTipo_doc_ben(reultObj[25] != null ? reultObj[25].toString() : null);
                datosEspecieLiqManual.setNum_doc_ben(reultObj[26] != null ? reultObj[26].toString() : null);
                datosEspecieLiqManual.setPri_nom_ben(reultObj[27] != null ? reultObj[27].toString() : null);
                datosEspecieLiqManual.setSeg_nom_ben(reultObj[28] != null ? reultObj[28].toString() : null);
                datosEspecieLiqManual.setPri_ape_ben(reultObj[29] != null ? reultObj[29].toString() : null);
                datosEspecieLiqManual.setSeg_ape_ben(reultObj[30] != null ? reultObj[30].toString() : null);
                datosEspecieLiqManual.setEstado_beneficiario(reultObj[31] != null ? reultObj[31].toString() : null);
                datosEspecieLiqManual.setCondicion_inv(reultObj[32] != null ? reultObj[32].toString() : null);
                datosEspecieLiqManual.setEdad(reultObj[33] != null ? reultObj[33].toString() : null);
                datosEspecieLiqManual.setFecha_nacimiento(reultObj[34] != null ? reultObj[34].toString() : null);
                datosEspecieLiqManual.setNum_grupo_fam(reultObj[35] != null ? reultObj[35].toString() : null);
                datosEspecieLiqManual.setGrado(reultObj[36] != null ? reultObj[36].toString() : null);
                datosEspecieLiqManual.setNivel_educativo(reultObj[37] != null ? reultObj[37].toString() : null);
                datosEspecieLiqManual.setSitio_pago(reultObj[38] != null ? reultObj[38].toString() : null);
                datosEspecieLiqManual.setEstado_grupo_fam(reultObj[39] != null ? reultObj[39].toString() : null);
                datosEspecieLiqManual.setParentesco(reultObj[40] != null ? reultObj[40].toString() : null);
                datosEspecieLiqManual.setTipo_doc_otro_padre(reultObj[41] != null ? reultObj[41].toString() : null);
                datosEspecieLiqManual.setNum_doc_otro_padre(reultObj[42] != null ? reultObj[42].toString() : null);
                datosEspecieLiqManual.setFecha_afiliacion_ben(reultObj[43] != null ? reultObj[43].toString() : null);
                datosEspecieLiqManual.setFecha_retiro_ben(reultObj[44] != null ? reultObj[44].toString() : null);
                datosEspecieLiqManual.setAnio_certificado(reultObj[45] != null ? reultObj[45].toString() : null);
                datosEspecieLiqManual.setTipo_doc_emp(reultObj[46] != null ? reultObj[46].toString() : null);
                datosEspecieLiqManual.setNum_doc_emp(reultObj[47] != null ? reultObj[47].toString() : null);
                datosEspecieLiqManual.setEstado_emp(reultObj[48] != null ? reultObj[48].toString() : null);
                datosEspecieLiqManual.setNombre_comercial(reultObj[49] != null ? reultObj[49].toString() : null);
                datosEspecieLiqManual.setEstado_mora(reultObj[50] != null ? reultObj[50].toString() : null);
                datosEspecieLiqManual.setEstado_subsidio(reultObj[51] != null ? reultObj[51].toString() : null);
                datosEspecieLiqManual.setPeriodo_liquidado(reultObj[52] != null ? reultObj[52].toString() : null);
                datosEspecieLiqManual.setCertificado_vigente(reultObj[53] != null ? reultObj[53].toString() : null);
                datosEspecieLiqManual.setFecha_ini_cert(reultObj[54] != null ? reultObj[54].toString() : null);
                datosEspecieLiqManual.setFecha_fin_cert(reultObj[55] != null ? reultObj[55].toString() : null);
                datosEspecieLiqManual.setFecha_liquidacion(reultObj[56] != null ? reultObj[56].toString() : null);
                resulDTO.add(datosEspecieLiqManual);
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER);
        return resulDTO;
    }
    //Consulta SERVICIO WEB SUBSIDIO  - ANTIOQUIA GLPI 57020

    @SuppressWarnings("unchecked")
    @Override
    public List<CuotaMonetariaIVRDTO> consultarCuotaMonetariaCanalIVRCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado) {
        String firmaMetodo = "consultarCuotaMonetariaCanalIVRCore(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado)";
        List<Object[]> reultStoreProcedure = null;
        logger.info("consultarCuotaMonetariaCanalIVRCore tipoIdentificacionAfiliado " + tipoIdentificacionAfiliado.name());
        logger.info("consultarCuotaMonetariaCanalIVRCore numeroIdentificacionAfiliado " + numeroIdentificacionAfiliado);
        List<CuotaMonetariaIVRDTO> resulDTO = new ArrayList<>();
        try {

            List<Object[]> cuotaMonetariaIVRResult = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDENTIFICACION_AFILIACION_CUOTA_MONETARIA_IVR)
                    .setParameter("tipoDocumento", tipoIdentificacionAfiliado.name())
                    .setParameter("numeroDocumento", numeroIdentificacionAfiliado).getResultList();
            if (cuotaMonetariaIVRResult != null && !cuotaMonetariaIVRResult.isEmpty()) {
                for (Object[] obj : cuotaMonetariaIVRResult) {
                    CuotaMonetariaIVRDTO cuotaMonetariaIVRDTOCiclo = new CuotaMonetariaIVRDTO();
                    // Se agrega la validacion a la linea de inconsistente
                    cuotaMonetariaIVRDTOCiclo.setResultado(obj[0] != null ? Long.parseLong(obj[0].toString()) : null);
                    cuotaMonetariaIVRDTOCiclo.setFechaCobro(obj[1] != null ? obj[1].toString() : null);
                    cuotaMonetariaIVRDTOCiclo.setValorAcumulado(obj[2] != null ? BigDecimal.valueOf(Double.valueOf(obj[2].toString())) : null);
                    List<Object[]> cuotaPeriodoIVRList = entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_PERIODOS_CUOTA_MONETARIA_IVR)
                            .setParameter("tipoDocumento", tipoIdentificacionAfiliado.name())
                            .setParameter("numeroDocumento", numeroIdentificacionAfiliado).getResultList();
                    if (cuotaPeriodoIVRList != null && !cuotaPeriodoIVRList.isEmpty()) {
                        List<CuotaPeriodoIVRDTO> resulPeriodosDTO = new ArrayList<>();
                        for (Object[] obj2 : cuotaPeriodoIVRList) {
                            CuotaPeriodoIVRDTO cuotaPeriodoIVRDTO = new CuotaPeriodoIVRDTO();
                            cuotaPeriodoIVRDTO.setValorPago(obj2[0] != null ? BigDecimal.valueOf(Double.valueOf(obj2[0].toString())) : null);
                            cuotaPeriodoIVRDTO.setPeriodoSubsidio(obj2[1] != null ? obj2[1].toString() : null);
                            cuotaPeriodoIVRDTO.setModalidadPago(obj2[2] != null ? obj2[2].toString() : null);
                            resulPeriodosDTO.add(cuotaPeriodoIVRDTO);
                        }
                        cuotaMonetariaIVRDTOCiclo.setListaPeriodos(resulPeriodosDTO);
                    }
                    resulDTO.add(cuotaMonetariaIVRDTOCiclo);
                }
            } else {
                CuotaMonetariaIVRDTO cuotaMonetariaIVRDTOCiclo2 = new CuotaMonetariaIVRDTO();
                cuotaMonetariaIVRDTOCiclo2.setResultado(Long.valueOf(460));
                resulDTO.add(cuotaMonetariaIVRDTOCiclo2);
            }
            logger.info(ConstantesComunes.FIN_LOGGER);
        } catch (Exception e) {
            logger.info(" :: Hubo un error en metodo consultarCuotaMonetariaCanalIVRCore error: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return resulDTO;
    }

    @Override
    public List<InformacionLiquidacionFallecimientoDTO> consultarLiquidacionFallecimiento(Long fechaInicio, Long fechaFin, String tipoIdentificacion, String identificacion, Long periodoRegular, String numeroOperacion, String medioPago, Long fechaProgramada) 
    {
    String firmaMetodo = "consultarLiquidacionFallecimiento(Long fechaInicio, Long fechaFin, String identificacion, Long periodoRegular, String numeroOperacion, String medioPago, String tipoIdentificacion, Long fechaProgramada)";
      
        logger.info("consultarLiquidacionFallecimiento fechaInicio " + fechaInicio);
        logger.info("consultarLiquidacionFallecimiento fechaProgramada " + fechaProgramada);

        List<InformacionLiquidacionFallecimientoDTO> result = new ArrayList<>();
        
         
            Date fechaInicioF = (fechaInicio == null) ? null : new Date(fechaInicio);
            String fechaInicioString = null ;
            if(fechaInicioF != null){
                SimpleDateFormat dfi = new SimpleDateFormat("yyyy-MM-dd");
                fechaInicioString = dfi.format(fechaInicioF);
            };

            Date fechaFinF = (fechaFin == null) ? null : new Date(fechaFin);
            String fechaFinString = null ;
            if(fechaFinF != null){
                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                fechaFinString = dff.format(fechaFinF);
            };

            Date periodoRegularF = (periodoRegular == null) ? null : new Date(periodoRegular );
            String periodoRegularString = null ;
            
            if(periodoRegularF != null){
                SimpleDateFormat prf = new SimpleDateFormat("yyyy-MM-dd");
                periodoRegularString = prf.format(periodoRegularF);
            };

            Date fechaProgramadaF = (fechaProgramada == null) ? null : new Date(fechaProgramada);
            String fechaProgramadaString = null ;
            if (fechaProgramadaF != null){
                SimpleDateFormat fpf = new SimpleDateFormat("yyyy-MM-dd");
                fechaProgramadaString = fpf.format(fechaProgramadaF);
            };
            logger.info("fechaFinString"+fechaFinString);
            logger.info("fechaInicioString"+fechaInicioString);
            logger.info("tipoIdentificacion"+tipoIdentificacion);
            logger.info("identificacion"+identificacion);
            logger.info("periodoRegularString"+periodoRegularString);
            logger.info("numeroOperacion"+numeroOperacion);
            logger.info("fechaProgramadaString"+fechaProgramadaString);
            logger.info("medioPago"+medioPago);
            

            List<Object[]> entidadesLiquidacionFallecimiento = (List<Object[]>)  entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTA_LIQUIDACION_F_PROGRAMADO)
                .setParameter("sNumeroRadicado", numeroOperacion == null ? "": numeroOperacion)
                .setParameter("tipoIdentificacion", tipoIdentificacion == null ? "" : tipoIdentificacion)
                .setParameter("numeroIdentificacion", identificacion == null ? "" : identificacion)
                .setParameter("medioPago", medioPago == null ? "" : medioPago)
                .setParameter("fechaInicio", fechaInicioString == null ? "": fechaInicioString)
                .setParameter("fechaFin", fechaFinString == null ? "" : fechaFinString)
                .setParameter("periodoRegular",periodoRegularString == null ? "" : periodoRegularString)
                .setParameter("fechaProgramadaPago", fechaProgramadaString == null ? "" : fechaProgramadaString)
                .getResultList();
            //query.registerStoredProcedureParameter("fechaInicio", String.class, ParameterMode.IN);    
            //query.registerStoredProcedureParameter("fechaFin", String.class, ParameterMode.IN);
            // query.registerStoredProcedureParameter("tipoIdentificacion", String.class, ParameterMode.IN);
            //query.registerStoredProcedureParameter("numeroIdentificacion", String.class, ParameterMode.IN);
            //query.registerStoredProcedureParameter("periodoRegular", String.class, ParameterMode.IN);
            // query.registerStoredProcedureParameter("sNumeroRadicado", String.class, ParameterMode.IN);
            //query.registerStoredProcedureParameter("fechaProgramadaPago", String.class, ParameterMode.IN);
            //query.registerStoredProcedureParameter("medioPago", String.class, ParameterMode.IN);
            // StoredProcedureQuery query = entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTA_LIQUIDACION_F_PROGRAMADO);
            //     query.setParameter("sNumeroRadicado", numeroOperacion);
            //     query.setParameter("tipoIdentificacion", tipoIdentificacion);
            //     query.setParameter("numeroIdentificacion", identificacion);
            //     query.setParameter("medioPago", medioPago);
            //     query.setParameter("fechaInicio", fechaInicioString);
            //     query.setParameter("fechaFin", fechaFinString);
            //     query.setParameter("periodoRegular",periodoRegularString);
            //     query.setParameter("fechaProgramadaPago", fechaProgramadaString);

            //     entidadesLiquidacionFallecimiento = (List<Object[]>) query.getResultList();
                

            if (entidadesLiquidacionFallecimiento.isEmpty()) {
                return result;
            }

            for (Object[] reultObj : entidadesLiquidacionFallecimiento) {

                InformacionLiquidacionFallecimientoDTO informacionLiquidacionFallecimientoDTO = new InformacionLiquidacionFallecimientoDTO();

                informacionLiquidacionFallecimientoDTO.setNumeroOperacion(reultObj[0] != null ? reultObj[0].toString() : null);
                informacionLiquidacionFallecimientoDTO.setFechaCreacion(reultObj[1] != null ? (Date) reultObj[1]: null);
                informacionLiquidacionFallecimientoDTO.setTipoIdAdministrador(reultObj[2] != null ? reultObj[2].toString() : null);
                informacionLiquidacionFallecimientoDTO.setNumeroIdAdministrador(reultObj[3] != null ? reultObj[3].toString() : null);
                informacionLiquidacionFallecimientoDTO.setNombreAdministrador(reultObj[4] != null ? reultObj[4].toString() : null);
                informacionLiquidacionFallecimientoDTO.setPeriodoLiquidado(reultObj[5] != null ? (Date) reultObj[5] : null);
                informacionLiquidacionFallecimientoDTO.setValorSubsidio(reultObj[6] != null ? BigDecimal.valueOf(Double.parseDouble(reultObj[6].toString())) : null);
                informacionLiquidacionFallecimientoDTO.setDescuento(reultObj[7] != null ? BigDecimal.valueOf(Double.parseDouble(reultObj[7].toString())): null);
                informacionLiquidacionFallecimientoDTO.setValorPagar(reultObj[8] != null ? BigDecimal.valueOf(Double.parseDouble(reultObj[8].toString())): null);
                informacionLiquidacionFallecimientoDTO.setFechaProgramada(reultObj[9] != null ? (Date) reultObj[9] : null);
                informacionLiquidacionFallecimientoDTO.setMedioPago(reultObj[10] != null ?  reultObj[10].toString() : null);

                result.add(informacionLiquidacionFallecimientoDTO);

            }

            return result;

        
    }

    @Override
    public List<RegistroSinDerechoSubsidioDTO> generarDataLiquidaciomSinDerecho(String numeroRadicacion) {
        String firmaMetodo = "consultasModeloCore.generarDataLiquidaciomSinDerecho(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug("Inicio el metodo generarDataLiquidaciomSinDerecho en la clase DataSouceLineSinDerecho");
        List<RegistroSinDerechoSubsidioDTO> personasSinDerechoDTO = new ArrayList<RegistroSinDerechoSubsidioDTO>();
        List<Object[]> resultadosSinDerecho = new ArrayList<>();
        try {
            if (numeroRadicacion != null) {
                logger.debug("Ingreso al if de generarDataLiquidaciomSinDerecho del metodo modelo core con el valpor de: "+ numeroRadicacion);
                resultadosSinDerecho = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_LIQUIDACION_MASIVA_SIN_DERECHO)
                        .setParameter("sNumeroRadicado", numeroRadicacion)
                        .getResultList();
            } else {

                logger.debug("Ingreso al else de generarDataLiquidaciomSinDerecho con el valpor de: " + numeroRadicacion);

            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            logger.debug("Ingreso al catch de generarDataLiquidaciomSinDerecho, error al consultar SP");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug("Ingresa a realizar la iteracion del objeto");
        for (Object[] registro : resultadosSinDerecho) {
            registro = reemplazarNulos(registro);
            RegistroSinDerechoSubsidioDTO sinDerechoDTO = new RegistroSinDerechoSubsidioDTO();
            //logger.debug("numEmp: " + registro[5].toString()+ " - numTrabaj: " + registro[11]!=null?registro[11].toString():"null");

            if (!registro[1].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registro[0].toString()));
            }

            sinDerechoDTO.setTipoLiquidacion(registro[1].toString());
            sinDerechoDTO.setSubtipoLiquidacion(registro[2].toString());
            sinDerechoDTO.setTipoIdentificacionEmpleador(registro[3].toString());
           /*  sinDerechoDTO.setTipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                    ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                            ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion()
                    : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString()) ? TipoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion() : "");
            sinDerechoDTO
                    .setSubtipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                            ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                                    ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion()
                                    : TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).getDescripcion()
                            : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString())? "" :TipoProcesoLiquidacionEnum.valueOf(registro[1].toString()).getDescripcion(): "");
            sinDerechoDTO.setTipoIdentificacionEmpleador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[4].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[4].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setNumeroIdentificacionEmpleador(registro[4].toString());
            sinDerechoDTO.setNombreEmpleador(registro[5].toString());

            sinDerechoDTO.setCiiu(registro[6].toString());

          /*   try {
                // Intentamos convertir el valor a un entero
                int ciiu = Integer.parseInt(registro[7].toString());

                // Si la conversión es exitosa, lo guardamos en sinDerechoDTO
                sinDerechoDTO.setCiiu(Integer.toString(ciiu));
            } catch (NumberFormatException e) {

                sinDerechoDTO.setCiiu(registro[8].toString());
            }*/
            sinDerechoDTO.setCondicionAgraria(registro[7].toString().equals(Boolean.TRUE.toString()) ? "A" : "N");
            sinDerechoDTO.setCodigoSucursal(registro[8].toString());
            if (!registro[10].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setAnioBeneficio1429(registro[9].toString());
            }
            sinDerechoDTO.setNumeroIdentificacionTrabajador(registro[10].toString());
           /*  sinDerechoDTO.setTipoIdentificacionTrabajador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[12].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[12].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoIdentificacionTrabajador(registro[11].toString());
            sinDerechoDTO.setNombreTrabajador(registro[12].toString());
            sinDerechoDTO.setNumeroIdentificacionBeneficiario(registro[13].toString());
            /*sinDerechoDTO.setTipoIdentificacionBeneficiario(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[15].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[15].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoIdentificacionBeneficiario(registro[14].toString());
            sinDerechoDTO.setNombreBeneficiario(registro[15].toString());
            /*sinDerechoDTO.setTipoSolicitante(validarExistenciaValorEnumeracion(TipoBeneficiarioEnum.class, registro[17].toString())
                    ? TipoBeneficiarioEnum.valueOf(registro[17].toString()).getDescripcion() : "");
            sinDerechoDTO.setClasificacion(validarExistenciaValorEnumeracion(ClasificacionEnum.class, registro[18].toString())
                    ? ClasificacionEnum.valueOf(registro[18].toString()).getDescripcion() : "");*/
            sinDerechoDTO.setTipoSolicitante(registro[16].toString());
            sinDerechoDTO.setClasificacion(registro[17].toString());
            sinDerechoDTO.setRazonesSinDerecho(registro[18].toString());
            sinDerechoDTO.setPeriodoLiquidado(registro[19].toString());
            sinDerechoDTO.setTipoPeriodo(registro[20].toString());

            personasSinDerechoDTO.add(sinDerechoDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.debug("Respuesta de parte de la DB de liquidacion de personas sin derecho DTO: " + personasSinDerechoDTO);
        return personasSinDerechoDTO;
    }
    private Object[] reemplazarNulos(Object[] registro) {
        for (int i = 0; i < registro.length; i++) {
            registro[i] = (registro[i] == null) ? cadenaVacia : registro[i];
        }
        return registro;
    }

        /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#obtenerInfoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
   public SubsidioAfiliadoDTO obtenerInfoSubsidio(TipoIdentificacionEnum tipoIdPersona, String numeroIdAfiliado,
            String numeroIdBeneficiario) {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerInfoSubsidio(TipoIdentificacionEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SubsidioAfiliadoDTO result = new SubsidioAfiliadoDTO();

        try {

            List<Object[]> resultado = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_SUBSIDIO_POR_AFILIADO_BENEFICIARIO)
                    .setParameter("tipoIdAfi", numeroIdAfiliado != null ? tipoIdPersona.name().toString() : null)
                    .setParameter("tipoIdBen", numeroIdBeneficiario != null ? tipoIdPersona.name().toString() : null)
                    .setParameter("numeroIdAfi", numeroIdAfiliado)
                    .setParameter("numeroIdBen", numeroIdBeneficiario)
                    .getResultList();

            if (!resultado.isEmpty()) {
                //se obtiene la información general del afiliado
                result.setTipoIdAfiliado(TipoIdentificacionEnum.valueOf(resultado.get(0)[0].toString()));
                result.setNumeroIdAfiliado(resultado.get(0)[1].toString());
                result.setNombreCompletoAfiliado(resultado.get(0)[2].toString());
                result.setTipoIdEmpleador(TipoIdentificacionEnum.valueOf(resultado.get(0)[3].toString()));
                result.setNumeroIdEmpleador(resultado.get(0)[4].toString());
                result.setRazonSocialEmpleador(resultado.get(0)[5].toString());

                List<InformacionGrupoFamiliarDTO> lstGrupoFamiliar = new ArrayList<>();
                
                result.setLstGruposFamiliares(lstGrupoFamiliar);

                Long numeroGrupoFamiliarAux = 0L;
                InformacionGrupoFamiliarDTO infoGrupoFamiliar = null;

                List<ItemSubsidioBeneficiarioDTO> lstBeneficiarios = null;
                ItemSubsidioBeneficiarioDTO itemBeneficiario = null;

                ItemSubsidioBeneficiarioDTO itemBeneficiarioanterior = null;

                for (Object[] item : resultado) {

                    Long numGrupoFamiliar = new Long(item[6].toString()); //217078

                    if (numeroGrupoFamiliarAux.compareTo(numGrupoFamiliar) != 0 && numeroGrupoFamiliarAux.longValue() != 0L) {
                         logger.info("ConsultasModeloSubsidio.obtenerInfoSubsidio if (numeroGrupoFamiliarAux.compareTo(numGrupoFamiliar) :" + lstBeneficiarios.size());  
                        // si es diferente el grupo familiar anterior
                        infoGrupoFamiliar = new InformacionGrupoFamiliarDTO();

                        lstBeneficiarios = new ArrayList<>();
                        infoGrupoFamiliar.setLstBeneficiarios(lstBeneficiarios);

                    infoGrupoFamiliar.setNumeroGrupoFamilarRelacionador(numGrupoFamiliar);
                    infoGrupoFamiliar.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(item[7].toString()));
                    infoGrupoFamiliar.setNumeroIdAdminSubsidio(item[8].toString());
                    infoGrupoFamiliar.setNombreAdminSubsidio(item[9].toString());                  
                    lstGrupoFamiliar.add(infoGrupoFamiliar);                      
                    }
                    if (infoGrupoFamiliar == null) {  //inicio
                     infoGrupoFamiliar = new InformacionGrupoFamiliarDTO();
                    infoGrupoFamiliar.setNumeroGrupoFamilarRelacionador(numGrupoFamiliar);
                    infoGrupoFamiliar.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(item[7].toString()));
                    infoGrupoFamiliar.setNumeroIdAdminSubsidio(item[8].toString());
                    infoGrupoFamiliar.setNombreAdminSubsidio(item[9].toString());
                   

                    infoGrupoFamiliar.setPeriodo("");
                       lstBeneficiarios = new ArrayList<>();
                       infoGrupoFamiliar.setLstBeneficiarios(lstBeneficiarios);
                       lstGrupoFamiliar.add(infoGrupoFamiliar);
                    }

                    itemBeneficiario = new ItemSubsidioBeneficiarioDTO();

                    itemBeneficiario.setPeriodo(item[10] != null ? item[10].toString(): null); //i

                    itemBeneficiario.setTipoIdentificacionBeneficiario(TipoIdentificacionEnum.valueOf(item[11].toString()));
                    itemBeneficiario.setNumeroIdentificacionBeneficiario(item[12].toString());
                    itemBeneficiario.setTipoBeneficiario(TipoBeneficiarioEnum.valueOf(item[13].toString()));
                  

                    itemBeneficiario.setFechaLiquidacion(item[14] != null ? item[14].toString() : null);
                    if (item[15] != null)
                        itemBeneficiario.setTipoCuotaSubsidio(TipoCuotaSubsidioEnum.valueOf(item[15].toString()));
                    itemBeneficiario.setTipoLiqSubsidioMonetario(TipoProcesoLiquidacionEnum.valueOf(item[16].toString()));
                    if (item[17] != null) {
                        itemBeneficiario.setEstadoDerechoSubsidio(EstadoDerechoSubsidioEnum.valueOf(item[17].toString()));
                    }
                    else {
                        itemBeneficiario.setEstadoDerechoSubsidio(EstadoDerechoSubsidioEnum.DERECHO_NO_APROBADO);
                    }
                    itemBeneficiario.setValorSubsidioMonetario(item[18] != null ? BigDecimal.valueOf(Double.valueOf(item[18].toString())) : BigDecimal.ZERO);
                    itemBeneficiario.setValorDescuento(item[19] != null ? BigDecimal.valueOf(Double.parseDouble(item[19].toString())) : BigDecimal.ZERO);

                    lstBeneficiarios.add(itemBeneficiario);

                    numeroGrupoFamiliarAux = numGrupoFamiliar;
                    itemBeneficiarioanterior = itemBeneficiario;
                } //for     

            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + "error en la busqueda de la Cuota Monetaria", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        return result;
    }
}
