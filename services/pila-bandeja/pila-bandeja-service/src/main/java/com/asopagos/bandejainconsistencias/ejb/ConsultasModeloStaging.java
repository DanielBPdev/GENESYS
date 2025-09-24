package com.asopagos.bandejainconsistencias.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.bandejainconsistencias.constants.NamedQueriesConstants;
import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaDTO;
import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaRespuestaDTO;
import com.asopagos.bandejainconsistencias.dto.CriteriosDTO;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging;
import com.asopagos.bandejainconsistencias.service.ejb.PilaBandejaBusiness;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos de Staging <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class ConsultasModeloStaging implements IConsultasModeloStaging, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloStaging.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "pilaStaging_PU")
    private EntityManager entityManager;

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#actualizarRegistroDetallado(com.asopagos.entidades.pila.staging.RegistroDetallado)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RegistroDetallado actualizarRegistroDetallado(RegistroDetallado registroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.actualizarRegistroDetallado(Long)";
        logger.debug("Inicia " + firmaMetodo);
        try {
            registroDetallado = entityManager.merge(registroDetallado);
            logger.info("Mantis 265511 consultasModeloStaging ln 77");
        } catch (Exception e) {
        	logger.info("Mantis 265511 catch consultasModeloStaging ln 78");
            logger.debug(
                    firmaMetodo + " No se logro actualizar el registro detallado de procesamiento de planilla PILA :: " + e.getMessage());
            registroDetallado = null;
        }
        logger.debug("Finaliza " + firmaMetodo);
        return registroDetallado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#buscarPorPersonaCriterios(com.asopagos.bandejainconsistencias.dto.CriteriosDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorPersonaCriterios(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response) {
        String firmaMetodo = "ConsultasModeloStaging.buscarPorPersonaCriterios(CriteriosDTO, UriInfo, HttpServletResponse)";
        logger.debug("Inicia " + firmaMetodo);

        List<BusquedaPorPersonaDTO> result = null;
        List<BusquedaPorPersonaDTO> resultIP = null;

        List<BusquedaPorPersonaRespuestaDTO> respuesta = new ArrayList<>();

        Query query = null;

        try {
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam("tipoIdentificacion", criterios.getTipoIdentificacion());
            queryBuilder.addParam("numeroIdentificacion", criterios.getNumeroIdentificacion());
            queryBuilder.addParam("periodoAporte", criterios.getPeriodo());
            queryBuilder.addParam("numeroPlanilla", criterios.getNumeroPlanilla());

            query = queryBuilder.createQuery(NamedQueriesConstants.BUSCAR_POR_PERSONA_CRITERIOS, null);
            result = (List<BusquedaPorPersonaDTO>) query.getResultList();

            query = queryBuilder.createQuery(NamedQueriesConstants.BUSCAR_POR_PERSONA_PENSIONADO_CRITERIOS, null);
            resultIP = (List<BusquedaPorPersonaDTO>) query.getResultList();

            if (result != null && !result.isEmpty()) {
                respuesta = prepararRespuestaDTO(result, Boolean.TRUE);
            }
            if (resultIP != null && !resultIP.isEmpty()) {
                respuesta.addAll(prepararRespuestaDTO(resultIP, Boolean.TRUE));
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + e.getMessage());
        }

        logger.debug("Finaliza " + firmaMetodo);
        return respuesta;
    }

    /**
     * Metodo que separa una consulta en sus correspondientes maestro-detalle
     * @param result
     * @return una lista con la cabecera armada de la busqueda de personas
     */
    public List<BusquedaPorPersonaRespuestaDTO> prepararRespuestaDTO(List<BusquedaPorPersonaDTO> result, Boolean esPersona) {
        String firmaMetodo = "ConsultasModeloStaging.prepararRespuestaDTO(List<BusquedaPorPersonaDTO>)";
        logger.debug("Inicia " + firmaMetodo);

        // Lista de retorno 
        List<BusquedaPorPersonaRespuestaDTO> resultList = new ArrayList<>();

        // Variable temporal para llevar el registro de control de la cabera
        Long regCtrlTmp = 0L;

        // Recorrer los registros
        for (BusquedaPorPersonaDTO busq : result) {
            Long registroControl = busq.getGenRegistroControl();

            BusquedaPorPersonaRespuestaDTO resp = new BusquedaPorPersonaRespuestaDTO();
            List<RegistroDetalladoModeloDTO> detalleList = new ArrayList<>();

            // Se arma la cabecera
            if (!registroControl.equals(regCtrlTmp)) {
                RegistroGeneralModeloDTO cabecera = new RegistroGeneralModeloDTO();
                cabecera.setNumPlanilla(busq.getGenNumPlanilla());
                cabecera.setTipoPlanilla(busq.getGenTipoPlanilla());
                cabecera.setTipoIdentificacionAportante(busq.getGenTipoIdentificacionAportante());
                cabecera.setNumeroIdentificacionAportante(busq.getGenNumeroIdentificacionAportante());
                cabecera.setPeriodoAporte(busq.getGenPeriodoAporte());
                cabecera.setRegistroControl(busq.getGenRegistroControl());
                cabecera.setValTotalApoObligatorio(busq.getGenValTotalApoObligatorio());
                cabecera.setDigVerAportante(busq.getGenDigVerAportante());
                cabecera.setNombreAportante(busq.getGenNombreAportante());
                cabecera.setValorIntMora(busq.getGenValorInteresMora());

                resp.setCabecera(cabecera);
                regCtrlTmp = busq.getGenRegistroControl();

                BigDecimal sumatoriaAportes = new BigDecimal(0);
                // Ahora se arma el detalle                
                for (BusquedaPorPersonaDTO registro : result) {
                    if (registroControl.equals(registro.getGenRegistroControl())) {
                        RegistroDetalladoModeloDTO detalle = new RegistroDetalladoModeloDTO();
                        detalle.setId(registro.getDetId());
                        detalle.setNumeroIdentificacionCotizante(registro.getDetNumeroIdentificacionCotizante());
                        detalle.setRegistroControl(registro.getDetRegistroControl());
                        detalle.setOutFechaProcesamientoValidRegAporte(registro.getDetOutFechaProcesamientoValidRegAporte());
                        detalle.setAporteObligatorio(registro.getDetAporteObligatorio());
                        detalle.setTipoCotizante(registro.getDetTipoCotizante());
                        detalle.setOutEstadoValidacionV0(registro.getDetOutEstadoValidacionV0());
                        detalle.setOutEstadoValidacionV1(registro.getDetOutEstadoValidacionV1());
                        detalle.setOutEstadoValidacionV2(registro.getDetOutEstadoValidacionV2());
                        detalle.setOutEstadoValidacionV3(registro.getDetOutEstadoValidacionV3());
                        detalle.setTipoIdentificacionCotizante(registro.getDetTipoIdentificacionCotizante());
                        detalle.setOutEstadoRegistroAporte(registro.getDetOutEstadoRegistroAporte());
                        detalle.setOutEstadoSolicitante(registro.getDetOutEstadoSolicitante());
                        detalleList.add(detalle);

                        sumatoriaAportes = sumatoriaAportes.add(registro.getDetAporteObligatorio());
                        if (esPersona) {
                            resp.setDetalle(detalleList);
                        }

                    }
                }
                resp.setTotalCotizantes((long) detalleList.size());
                // Tomar la sumatoria de los aportes y sumar el valor de la mora
                if (cabecera.getValorIntMora() != null) {
                    sumatoriaAportes = sumatoriaAportes.add(cabecera.getValorIntMora());
                }
                resp.setSumatoriaAportes(sumatoriaAportes);

                resultList.add(resp);

            }
            else {
                continue;
            }
        }

        return resultList;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#buscarPorAportanteCriterios(com.asopagos.bandejainconsistencias.dto.CriteriosDTO,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorAportanteCriterios(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response) {

        String firmaMetodo = "ConsultasModeloStaging.buscarPorAportanteCriterios(CriteriosDTO, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BusquedaPorPersonaDTO> result = new ArrayList<>();
        List<EstadoProcesoArchivoEnum> estadosProceso = obtenerEstadosProcesoArchivo();
        List<BusquedaPorPersonaRespuestaDTO> respuesta = new ArrayList<>();

        Query query = null;

        try {
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam("tipoIdentificacion", criterios.getTipoIdentificacion());
            queryBuilder.addParam("numeroIdentificacion", criterios.getNumeroIdentificacion());
            queryBuilder.addParam("periodoAporte", criterios.getPeriodo());
            queryBuilder.addParam("numeroPlanilla", criterios.getNumeroPlanilla());
            queryBuilder.addParam("registroControl", criterios.getRegistroControl());
            queryBuilder.addParam("estadosProceso", estadosProceso);

            query = queryBuilder.createQuery(NamedQueriesConstants.BUSCAR_POR_APORTANTE_CRITERIOS,
                    NamedQueriesConstants.BUSCAR_POR_APORTANTE_CRITERIOS_TOTAL);
            result = (List<BusquedaPorPersonaDTO>) query.getResultList();

            if (result != null && !result.isEmpty()) {
                respuesta = prepararRespuestaDTO(result, Boolean.FALSE);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
    }

    /**
     * @return Listado de procesos archivo
     */
    private List<EstadoProcesoArchivoEnum> obtenerEstadosProcesoArchivo() {
        String firmaMetodo = "obtenerEstadosProcesoArchivo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<EstadoProcesoArchivoEnum> estadosProceso = new ArrayList<>();

        estadosProceso.add(EstadoProcesoArchivoEnum.PROCESADO_VS_BD);
        estadosProceso.add(EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES);
        estadosProceso.add(EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES);
        estadosProceso.add(EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES);
        estadosProceso.add(EstadoProcesoArchivoEnum.PROCESADO_SIN_NOVEDADES);
        estadosProceso.add(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO);
        estadosProceso.add(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO_MANUAL);
        estadosProceso.add(EstadoProcesoArchivoEnum.NOTIFICACION_RECAUDO_FALLIDO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return estadosProceso;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#contarPlanillasConInconsistenciasPorGestionar()
     */
    @Override
    public Integer contarPlanillasConInconsistenciasPorGestionar() {
        logger.debug("Inicia PilaBandejaBusiness.contarPlanillasConInconsistenciasPorGestionar()");
        Integer numPlanillas = null;
        try {
            numPlanillas = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONTAR_PLANILLAS_CON_INCONSISTENCIAS)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.debug("PilaBandejaBusiness.contarPlanillasConInconsistenciasPorGestionar() :: "
                    + "No se encuentra una planilla con inconsistencias");
        }
        logger.debug("Finaliza PilaBandejaBusiness.contarPlanillasConInconsistenciasPorGestionar()");
        return numPlanillas;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarPlanillasPorGestionarConInconsistenciasValidacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorGestionarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin) {
        logger.debug(
                "Inicia PilaBandejaBusiness.consultarPlanillasPorGestionarConInconsistenciasValidacion( TipoIdentificacionEnum, String, Short, Long, Long)");
        List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO = null;
        Date fechaInicioAporte = null;
        Date fechaFinalAporte = null;

        if (TipoIdentificacionEnum.NIT.equals(tipoIdentificacionAportante) && digitoVerificacionAportante == null) {
            logger.debug(
                    "PilaBandejaBusiness.consultarPlanillasPorGestionarConInconsistenciasValidacion :: No llego parametro dígito de verificacion, para el TipoId NIT");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        if (fechaInicio != null && fechaFin == null) {
            fechaInicioAporte = new Date(fechaInicio);
            // Si no viene fecha fin del procesamiento, esta se asume como la actual
            fechaFinalAporte = Calendar.getInstance().getTime();
        }
        else if (fechaInicio == null && fechaFin != null) {
            // Se hace fecha inicio la fecha limite incial definida en la HU; para realizar una sola consulta.
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                fechaInicioAporte = format.parse("1900/01/01");
            } catch (ParseException e) {
            }
            // Se realiza la consulta solo con fecha fin del procesamiento
            fechaFinalAporte = new Date(fechaFin);
        }
        else if (fechaInicio == null && fechaFin == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                fechaInicioAporte = format.parse("1900/01/01");
            } catch (ParseException e) {
            }
            // Se realiza la consulta solo con fecha fin del procesamiento
            fechaFinalAporte = new Date();
        }
        else {
            fechaInicioAporte = new Date(fechaInicio);
            fechaFinalAporte = new Date(fechaFin);
        }
        // se realiza la consulta de los registrosde aporte de planillas que
        // tienen inconsistencias por gestionar
        lstInconsistenciaRegistroAporteDTO = new ArrayList<InconsistenciaRegistroAporteDTO>();
        if (tipoIdentificacionAportante == null && numeroIdentificacionAportante == null && fechaInicio == null && fechaFin == null) {
            lstInconsistenciaRegistroAporteDTO = entityManager.createNamedQuery(
                    NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR_SIN_FILTRO,
                    InconsistenciaRegistroAporteDTO.class).getResultList();
        }
        else if (TipoIdentificacionEnum.NIT.equals(tipoIdentificacionAportante)) {
            // busqueda con todo el filtro
            lstInconsistenciaRegistroAporteDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR,
                            InconsistenciaRegistroAporteDTO.class)
                    .setParameter("tipoIdentificacionAportante", tipoIdentificacionAportante)
                    .setParameter("numeroIdentificacionAportante", numeroIdentificacionAportante)
                    .setParameter("digitoVerificacionAportante", digitoVerificacionAportante)
                    .setParameter("fechaInicioAporte", CalendarUtils.truncarHora(fechaInicioAporte))
                    .setParameter("fechaFinalAporte", CalendarUtils.truncarHoraMaxima(fechaFinalAporte)).getResultList();
        }
        else if (digitoVerificacionAportante == null) {
            //busqueda sin digito de verificacion en el filtro
            lstInconsistenciaRegistroAporteDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR_SIN_DV,
                            InconsistenciaRegistroAporteDTO.class)
                    .setParameter("tipoIdentificacionAportante", tipoIdentificacionAportante)
                    .setParameter("numeroIdentificacionAportante", numeroIdentificacionAportante)
                    .setParameter("fechaInicioAporte", CalendarUtils.truncarHora(fechaInicioAporte))
                    .setParameter("fechaFinalAporte", CalendarUtils.truncarHoraMaxima(fechaFinalAporte)).getResultList();
        }
        logger.debug(
                "Finaliza PilaBandejaBusiness.consultarPlanillasPorGestionarConInconsistenciasValidacion( TipoIdentificacionEnum, String, Short, Long, Long)");
        return lstInconsistenciaRegistroAporteDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarPlanillasPorAprobarConInconsistenciasValidacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorAprobarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin) {
        logger.debug(
                "Inicia PilaBandejaBusiness.consultarPlanillasPorAprobarGestionarConInconsistenciasValidacion( TipoIdentificacionEnum, String, Short, Long, Long)");
        List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO = null;
        Date fechaInicioAporte = null;
        Date fechaFinalAporte = null;

        if (TipoIdentificacionEnum.NIT.equals(tipoIdentificacionAportante) && digitoVerificacionAportante == null) {
            logger.debug(
                    "PilaBandejaBusiness.consultarPlanillasPorAprobarGestionarConInconsistenciasValidacion :: No llego parametro dígito de verificacion, para el TipoId NIT");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        if (fechaInicio != null && fechaFin == null) {
            fechaInicioAporte = new Date(fechaInicio);
            // Si no viene fecha fin del procesamiento, esta se asume como la actual
            fechaFinalAporte = Calendar.getInstance().getTime();
        }
        else if (fechaInicio == null && fechaFin != null) {
            // Se hace fecha inicio la fecha limite incial definida en la HU, para realizar una sola consulta.
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                fechaInicioAporte = format.parse("1900/01/01");
            } catch (ParseException e) {
            }
            // Se realiza la consulta solo con fecha fin del procesamiento
            fechaFinalAporte = new Date(fechaFin);
        }
        else if (fechaInicio == null && fechaFin == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {
                fechaInicioAporte = format.parse("1900/01/01");
            } catch (ParseException e) {
            }
            // Se realiza la consulta solo con fecha fin del procesamiento
            fechaFinalAporte = new Date();
        }
        else {
            fechaInicioAporte = new Date(fechaInicio);
            fechaFinalAporte = new Date(fechaFin);
        }
        // se realiza la consulta de los registrosde aporte de planillas que
        // tienen inconsistencias por gestionar
        lstInconsistenciaRegistroAporteDTO = new ArrayList<>();
        if (tipoIdentificacionAportante == null && numeroIdentificacionAportante == null && fechaInicio == null && fechaFin == null) {
            lstInconsistenciaRegistroAporteDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_SIN_FILTRO,
                            InconsistenciaRegistroAporteDTO.class)
                    .getResultList();
        }
        else if (TipoIdentificacionEnum.NIT.equals(tipoIdentificacionAportante)) {
            // busqueda con todo el filtro
            lstInconsistenciaRegistroAporteDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS,
                            InconsistenciaRegistroAporteDTO.class)
                    .setParameter("tipoIdentificacionAportante", tipoIdentificacionAportante)
                    .setParameter("numeroIdentificacionAportante", numeroIdentificacionAportante)
                    .setParameter("digitoVerificacionAportante", digitoVerificacionAportante)
                    .setParameter("fechaInicioAporte", CalendarUtils.truncarHora(fechaInicioAporte))
                    .setParameter("fechaFinalAporte", CalendarUtils.truncarHoraMaxima(fechaFinalAporte)).getResultList();
        }
        else if (digitoVerificacionAportante == null) {
            lstInconsistenciaRegistroAporteDTO = new ArrayList<InconsistenciaRegistroAporteDTO>();
            lstInconsistenciaRegistroAporteDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_SIN_DV,
                            InconsistenciaRegistroAporteDTO.class)
                    .setParameter("tipoIdentificacionAportante", tipoIdentificacionAportante)
                    .setParameter("numeroIdentificacionAportante", numeroIdentificacionAportante)
                    .setParameter("fechaInicioAporte", CalendarUtils.truncarHora(fechaInicioAporte))
                    .setParameter("fechaFinalAporte", CalendarUtils.truncarHoraMaxima(fechaFinalAporte)).getResultList();
        }
        logger.debug(
                "Finaliza PilaBandejaBusiness.consultarPlanillasPorAprobarGestionarConInconsistenciasValidacion( TipoIdentificacionEnum, String, Short, Long, Long)");
        return lstInconsistenciaRegistroAporteDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarRegistroAporteConInconsistencia(java.lang.Long)
     */
    @Override
    public RegistroDetallado consultarRegistroAporteConInconsistencia(Long idRegistroAporteDetallado) {
        RegistroDetallado registroAporte = null;
        try {
            registroAporte = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_APORTE_CON_INCONSISTENCIA, RegistroDetallado.class)
                    .setParameter("idRegistroAporteDetallado", idRegistroAporteDetallado).getSingleResult();
            return registroAporte;
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#buscarControlResultadosPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosPersona(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO) {

        String firmaMetodo = "Inicia buscarControlResultadosPersona(TipoIdentificacionEnum, String, Long, Long, UserDTO)";
        logger.debug(firmaMetodo);

        logger.debug(
                "Inicia buscarControlResultadosEmpleador(Empleador empleador, Long numeroPlanilla, Integer añoPeriodo,Integer mesPeriodo, UserDTO userDTO)");
        List<Object[]> consulta = new ArrayList<Object[]>();
        List<Object[]> ConsultaIP = new ArrayList<Object[]>();
        List<RespuestaConsultaEmpleadorDTO> result = new ArrayList<RespuestaConsultaEmpleadorDTO>();
        String periodoAporte = "";
        // Si se establece periodo en la solicitud se realiza casting para
        // obtener el año y el mes

        // Si se establece periodo en la solicitud se realiza casting para obtener el año y el mes
        if (periodo != null) {

            Date fechaPeriodo = new Date(periodo);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fechaPeriodo);
            Integer anio = calendar.get(Calendar.YEAR);
            Integer mes = calendar.get(Calendar.MONTH) + 1;

            periodoAporte = "" + anio + "-" + mes;
        }

        if (tipoDocumento != null && idAportante != null) {

            if (numeroPlanilla != null) {

                if (periodo != null) {
                    // consulta con todos los atributos de busqueda

                    logger.debug("Inicia Consulta con Persona,numero de planilla y periodo");

                    logger.debug("Finaliza Consulta con empleador,numero de planilla y periodo");

                    try {
                        logger.debug("Inicia Consulta con empleador,numero de planilla y periodo");
                        consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_I_TODOS_ARGUMENTOS)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla)
                                .getResultList();
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                        // Mapeo Datos
                        ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_IP_TODOS_ARGUMENTOS)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla)
                                .getResultList();
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                        // Mapeo Datos
                        logger.debug("Finaliza Consulta con empleador,numero de planilla y periodo");
                        return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                    } catch (Exception e) {
                        logger.error("Error al realizar la consulta,verifique los datos", e);
                        logger.debug("Finaliza Consulta con empleador,numero de planilla y periodo");
                    }

                }
                else {
                    // consulta sin periodo
                    try {
                        logger.debug("Inicia Consulta con empleador,numero de planilla");
                        consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_I_PLANILLA)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                        // Mapeo Datos
                        ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_IP_PLANILLA)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                        // Mapeo Datos
                        logger.debug("Fin Consulta con empleador,numero de planilla");
                        return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                    } catch (Exception e) {
                        logger.error("Error al realizar la consulta,verifique los datos", e);
                        logger.debug("Fin Consulta con empleador,numero de planilla");
                    }

                }
            }
            if (periodo != null) {
                // consulta empleador y periodo
                try {

                    logger.debug("Consulta con empleador y periodo");

                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_I_PERIODO)
                            .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                            .setParameter("periodoAporte", periodoAporte).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                    // Mapeo Datos
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_PERSONA_IP_PERIODO)
                            .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                            .setParameter("periodoAporte", periodoAporte).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                    // Mapeo Datos
                    logger.debug("Finaliza Consulta con empleador y periodo");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza Consulta con empleador y periodo");
                }
            }

        }
        // No existe Persona
        if (numeroPlanilla != null) {
            if (periodo != null) {
                // consulta con numero de planilla y periodo
                try {
                    logger.debug("consulta con numero de planilla y periodo");
                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_I_PERIODO_PLANILLA)
                            .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                    // Mapeo Datos
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_IP_PERIODO_PLANILLA)
                            .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                    // Mapeo Datos
                    logger.debug("Finaliza consulta con numero de planilla y periodo");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza consulta con numero de planilla y periodo");
                }

            }
            else {
                // consulta con numero de planilla
                try {
                    logger.debug("consulta con numero de planilla ");
                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_I_PLANILLA)
                            .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                    // Mapeo Datos
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_IP_PLANILLA)
                            .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                    logger.debug("Finaliza consulta con numero de planilla ");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza consulta con numero de planilla ");
                }

            }
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#buscarControlResultadosEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosEmpleador(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO) {

        logger.debug(
                "Inicia buscarControlResultadosEmpleador(Empleador empleador, Long numeroPlanilla, Integer añoPeriodo,Integer mesPeriodo, UserDTO userDTO)");
        List<Object[]> consulta = new ArrayList<Object[]>();
        List<Object[]> ConsultaIP = new ArrayList<Object[]>();
        List<RespuestaConsultaEmpleadorDTO> result = new ArrayList<RespuestaConsultaEmpleadorDTO>();
        String periodoAporte = "";
        // Si se establece periodo en la solicitud se realiza casting para
        // obtener el año y el mes

        if (periodo != null) {

            Date fechaPeriodo = new Date(periodo);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fechaPeriodo);
            Integer anio = calendar.get(Calendar.YEAR);
            Integer mes = calendar.get(Calendar.MONTH) + 1;

            periodoAporte = "" + anio + "-" + mes;

        }
        if (tipoDocumento != null && idAportante != null) {

            if (numeroPlanilla != null) {

                if (periodo != null) {
                    // consulta con todos los atributos de busqueda
                    try {
                        logger.debug("Inicia Consulta con empleador,numero de planilla y periodo");
                        consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_I_TODOS_ARGUMENTOS)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla)
                                .getResultList();

                        // Mapeo Datos
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                        ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_IP_TODOS_ARGUMENTOS)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla)
                                .getResultList();
                        // Mapeo Datos
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                        logger.debug("Finaliza Consulta con empleador,numero de planilla y periodo");
                        return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                    } catch (Exception e) {
                        logger.error("Error al realizar la consulta,verifique los datos", e);
                        logger.debug("Finaliza Consulta con empleador,numero de planilla y periodo");
                    }

                }
                else {
                    // consulta sin periodo
                    try {
                        logger.debug("Inicia Consulta con empleador,numero de planilla");
                        consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_I_PLANILLA)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("numeroPlanilla", numeroPlanilla).getResultList();

                        // Mapeo Datos
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                        ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_IP_PLANILLA)
                                .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                                .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                        // Mapeo Datos
                        result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                        logger.debug("Fin Consulta con empleador,numero de planilla");
                        return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                    } catch (Exception e) {
                        logger.error("Error al realizar la consulta,verifique los datos", e);
                        logger.debug("Fin Consulta con empleador,numero de planilla");
                    }

                }
            }
            if (periodo != null) {
                // consulta empleador y periodo
                try {

                    logger.debug("Consulta con empleador y periodo");

                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_I_PERIODO)
                            .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                            .setParameter("periodoAporte", periodoAporte).getResultList();

                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_EMPLEADOR_IP_PERIODO)
                            .setParameter("tipoDocumento", tipoDocumento.getValorEnPILA()).setParameter("idAportante", idAportante)
                            .setParameter("periodoAporte", periodoAporte).getResultList();
                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                    logger.debug("Finaliza Consulta con empleador y periodo");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza Consulta con empleador y periodo");
                }
            }

        }
        // No existe empleador
        if (numeroPlanilla != null) {
            if (periodo != null) {
                // consulta con numero de planilla y periodo
                try {
                    logger.debug("consulta con numero de planilla y periodo");
                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_I_PERIODO_PLANILLA)
                            .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla).getResultList();

                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_IP_PERIODO_PLANILLA)
                            .setParameter("periodoAporte", periodoAporte).setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));
                    logger.debug("Finaliza consulta con numero de planilla y periodo");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza consulta con numero de planilla y periodo");
                }

            }
            else {
                // consulta con numero de planilla
                try {
                    logger.debug("consulta con numero de planilla ");
                    consulta = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_I_PLANILLA)
                            .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresa(consulta));

                    // Mapeo Datos
                    ConsultaIP = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PLANILLAS_IP_PLANILLA)
                            .setParameter("numeroPlanilla", numeroPlanilla).getResultList();
                    // Mapeo Datos
                    result.addAll(PilaBandejaBusiness.mapeoAportesEmpresaPensionados(ConsultaIP));

                    logger.debug("Finaliza consulta con numero de planilla ");
                    return PilaBandejaBusiness.calcularCantidadTotalAportes(result);
                } catch (Exception e) {
                    logger.error("Error al realizar la consulta,verifique los datos", e);
                    logger.debug("Finaliza consulta con numero de planilla ");
                }

            }
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarRegistroDetalladoxRegistroGeneral(java.lang.Long)
     */
    @Override
    public List<RegistroDetallado> consultarRegistroDetalladoxRegistroGeneral(Long idRegistroGeneral) {
        List<RegistroDetallado> lstRegistroDetallado = new ArrayList<RegistroDetallado>();
        try {
            lstRegistroDetallado = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_DETALLADO_APORTE_POR_REGISTRO_GENERAL,
                            RegistroDetallado.class)
                    .setParameter("idRegistroAporteGeneral", idRegistroGeneral).getResultList();
            return lstRegistroDetallado;
        } catch (NoResultException nre) {
            logger.error(
                    "evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO) :: No se pudo obtener los registros detallados asociados al registro general de planilla::"
                            + nre.getMessage());
            return lstRegistroDetallado;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarRegistroGeneralxIdPlanila(java.lang.Long)
     */
    @Override
    public RegistroGeneral consultarRegistroGeneralxId(Long idRegistroGeneral) {
        RegistroGeneral registroGeneral = null;
        try {
            registroGeneral = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID, RegistroGeneral.class)
                    .setParameter("idRegistroAporteGeneral", idRegistroGeneral).getSingleResult();
            return registroGeneral;
        } catch (NoResultException nre) {
            logger.error(
                    "evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO) :: No se pudo obtener el registro general de planilla :: "
                            + nre.getMessage());
            return registroGeneral;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#actualizarRegistroGeneral(com.asopagos.entidades.pila.staging.RegistroGeneral)
     */
    @Override
    public void actualizarRegistroGeneral(RegistroGeneral registroGeneral) {
        try {
            entityManager.merge(registroGeneral);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#detalleAportanteCriterios(com.asopagos.bandejainconsistencias.dto.CriteriosDTO,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroDetalladoModeloDTO> detalleAportanteCriterios(CriteriosDTO criterios, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "ConsultasModeloStaging.detalleAportanteCriterios(CriteriosDTO, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroDetalladoModeloDTO> detalle = new ArrayList<>();

        List<BusquedaPorPersonaDTO> result = new ArrayList<>();
        List<EstadoProcesoArchivoEnum> estadosProceso = obtenerEstadosProcesoArchivo();

        Query query = null;

        try {
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam("tipoIdentificacion", criterios.getTipoIdentificacion());
            queryBuilder.addParam("numeroIdentificacion", criterios.getNumeroIdentificacion());
            queryBuilder.addParam("periodoAporte", criterios.getPeriodo());
            queryBuilder.addParam("numeroPlanilla", criterios.getNumeroPlanilla());
            queryBuilder.addParam("registroControl", criterios.getRegistroControl());
            queryBuilder.addParam("estadosProceso", estadosProceso);

            query = queryBuilder.createQuery(NamedQueriesConstants.BUSCAR_POR_APORTANTE_CRITERIOS,
                    NamedQueriesConstants.BUSCAR_POR_APORTANTE_CRITERIOS_TOTAL);
            result = (List<BusquedaPorPersonaDTO>) query.getResultList();

            if (result != null && !result.isEmpty()) {
                detalle = prepararDetalle(result);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalle;
    }

    /**
     * @param result
     * @return
     */
    private List<RegistroDetalladoModeloDTO> prepararDetalle(List<BusquedaPorPersonaDTO> result) {
        String firmaMetodo = "ConsultasModeloStaging.prepararDetalle(List<BusquedaPorPersonaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroDetalladoModeloDTO> detalleList = new ArrayList<>();

        // Extraer detalle de los registros                
        for (BusquedaPorPersonaDTO registro : result) {
            RegistroDetalladoModeloDTO detalle = new RegistroDetalladoModeloDTO();

            detalle.setId(registro.getDetId());
            detalle.setNumeroIdentificacionCotizante(registro.getDetNumeroIdentificacionCotizante());
            detalle.setRegistroControl(registro.getDetRegistroControl());
            detalle.setOutFechaProcesamientoValidRegAporte(registro.getDetOutFechaProcesamientoValidRegAporte());
            detalle.setAporteObligatorio(registro.getDetAporteObligatorio());
            detalle.setTipoCotizante(registro.getDetTipoCotizante());
            detalle.setOutEstadoValidacionV0(registro.getDetOutEstadoValidacionV0());
            detalle.setOutEstadoValidacionV1(registro.getDetOutEstadoValidacionV1());
            detalle.setOutEstadoValidacionV2(registro.getDetOutEstadoValidacionV2());
            detalle.setOutEstadoValidacionV3(registro.getDetOutEstadoValidacionV3());
            detalle.setTipoIdentificacionCotizante(registro.getDetTipoIdentificacionCotizante());
            detalle.setOutEstadoRegistroAporte(registro.getDetOutEstadoRegistroAporte());
            detalle.setOutEstadoSolicitante(registro.getDetOutEstadoSolicitante());

            detalleList.add(detalle);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleList;
    }

    @Override
    public void aprobarRegistrosDetalladosPorId(List<Long> idsRegistrosDetallados, String usuarioAprobador) {
        String firmaMetodo = "ConsultasModeloStaging.prepararDetalle(List<Long>, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManager.createNamedQuery(NamedQueriesConstants.APROBAR_REGISTROS_POR_ID)
                    .setParameter("idsRegistrosDetallados", idsRegistrosDetallados).setParameter("usuarioAprobador", usuarioAprobador)
                    .executeUpdate();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(e);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#asignarIdTransaccionYEstadoBase(java.util.List,
     *      java.lang.Long, com.asopagos.enumeraciones.pila.BloqueValidacionEnum)
     */
    @Override
    public void asignarIdTransaccionYEstadoBase(List<Long> idsRegistrosGenerales, Long idTransaccion, BloqueValidacionEnum bloque,
            Boolean esSimulado) {
        String firmaMetodo = "ConsultasModeloStaging.asignarIdTransaccion(List<Long>, Long, BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            if (!esSimulado) {
                entityManager.createNamedQuery(NamedQueriesConstants.ASIGNAR_ID_TRANSACCION_POR_ID)
                        .setParameter("idsRegistrosGenerales", idsRegistrosGenerales).setParameter("idTransaccion", idTransaccion)
                        .setParameter("bloque", bloque.name()).executeUpdate();
            }
            else {
                entityManager.createNamedQuery(NamedQueriesConstants.ASIGNAR_ID_TRANSACCION_POR_ID_SIMLUACION)
                        .setParameter("idsRegistrosGenerales", idsRegistrosGenerales).executeUpdate();
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#consultarRegistrosGeneralesPorListaId(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistroGeneral, Boolean esReproceso,
            Boolean esSimulado) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosGeneralesPorListaId(List<Long>, Boolean, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroGeneralModeloDTO> result = new ArrayList<>();

        try {
            // los registros generales se consultan en grupos de 1000 por seguridad
            int inicio = 0;
            int fin = idsRegistroGeneral.size() > 1000 ? 1000 : idsRegistroGeneral.size();
            while (fin <= idsRegistroGeneral.size()) {
                List<Long> idsRegistrosDetalladosTemp = idsRegistroGeneral.subList(inicio, fin);

                List<RegistroGeneral> resultQuery = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_GENERALES_LISTA_ID)
                        .setParameter("idsRegistroGeneral", idsRegistrosDetalladosTemp).setParameter("esReproceso", esReproceso ? 1 : 0)
                        .setParameter("esSimulado", esSimulado ? 1 : 0).getResultList();

                for (RegistroGeneral registroGeneral : resultQuery) {
                    RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO(registroGeneral);
                    result.add(registroGeneralDTO);
                }

                inicio = fin;
                fin++;
                if (fin <= idsRegistroGeneral.size()) {
                    fin = fin + 1000 <= idsRegistroGeneral.size() ? inicio + 1000 : idsRegistroGeneral.size();
                }
            }
        } catch (NoResultException e) {
            logger.debug(firmaMetodo + " :: Sin resultados que agregar");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	/** (non-Javadoc)
	 * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#recalcularEstadoRegistroGeneral(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void recalcularEstadoRegistroGeneral(Long idTransaccion) {
        String firmaMetodo = "ConsultasModeloStaging.recalcularEstadoRegistroGeneral(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        entityManager.createNamedQuery(NamedQueriesConstants.RECALCULAR_ESTADO_ARCHIVO_RG).setParameter("idTransaccion", idTransaccion).executeUpdate();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroGeneral consultarRegistroGeneralxRegistroControl(Long idRegistroControl) {
        RegistroGeneral registroGeneral = null;
        try {
            registroGeneral = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_REGISTRO_CONTROL, RegistroGeneral.class)
                    .setParameter("idRegistroControl", idRegistroControl).getSingleResult();
            return registroGeneral;
        } catch (NoResultException nre) {
            logger.error(
                    "evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO) :: No se pudo obtener el registro general de planilla :: "
                            + nre.getMessage());
            return registroGeneral;
        }
    }
}
