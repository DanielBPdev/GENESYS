package com.asopagos.cartera.ejb;

import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.business.interfaces.IConsultasModeloCore;
import com.asopagos.cartera.constants.NamedQueriesConstants;
import com.asopagos.cartera.dto.*;
import com.asopagos.cartera.service.CarteraService;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.cartera.*;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.cartera.*;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.*;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.asopagos.dto.modelo.DatosArchivoCargueExclusionMoralParcialDTO;
import javax.ws.rs.core.Response;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.asopagos.archivos.util.ExcelUtil;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import javax.ws.rs.core.MediaType;
import com.asopagos.entidades.transversal.core.ComunicadoNiyarakyRecepcion;
import com.asopagos.entidades.transversal.core.ComunicadoNiyaraky;
import static com.asopagos.util.Interpolator.interpolate;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import com.asopagos.dto.modelo.GuardarRelacionComunicadoBitacoraCarteraDTO;
import com.asopagos.entidades.transversal.core.ComunicadoTransversalBitacora;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la cartera de la caja de compensacion<b>
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Stateless
@SuppressWarnings("unchecked")
public class CarteraBusiness implements CarteraService {

    /**
     * Mensaje para el log de los servicios si ocurre un error: ocurrió un error
     */
    private static final String LOG_OCURRIO_UN_ERROR = "Ocurrió un error en ";

    /**
     * Mensaje para el log de los servicios cuando finaliza: Finaliza
     */
    private static final String LOG_FINALIZA = "Finaliza ";

    /**
     * Mensaje para el log de los servicios cuando inicia el servicio: Inicia
     */
    private static final String LOG_INICIA = "Inicia ";

    /**
     * Constante con el valor del nombre del parametro tipo ciclo
     */
    private static final String TIPO_CICLO = "tipoCiclo";

    /**
     * Constante con el valor del parámetro tipo líne de cobro
     */
    private static final String TIPO_LINEA_COBRO = "tipoLineaCobro";

    /**
     * Constante con el valor del parámetro fecha fin.
     */
    private static final String FECHA_FIN = "fechaFin";

    /**
     * Constante con el valor del parámetro fecha inicio.
     */
    private static final String FECHA_INICIO = "fechaInicio";

    /**
     * Constante con el valor del parámetro estado fiscalización.
     */
    private static final String ESTADO_CICLO_CARTERA = "estadoCicloCartera";

    /**
     * Constante con el valor del parámetro id ciclo fiscalización.
     */
    private static final String ID_CICLO_CARTERA = "idCicloCartera";

    /**
     * Constante que contiene el nombre del parámetro para el número de radicación.
     */
    private static final String NUMERO_RADICACION = "numeroRadicacion";
    /**
     * Constante que contiene el nombre del parámetro para el usuario destinatario
     */
    private static final String USUARIO_DESTINATARIO = "usuarioDestinatario";

    /**
     * Constante con el formato de la fecha del consecutivo de liquidación
     */
    private static final String FORMATO_FECHA_LIQUIDACION = "yyyyMM";

    /**
     * Constante con el formato del consecutivo de liquidación
     */
    private static final String FORMATO_CONSECUTIVO_LIQUIDACION = "%04d";

    /**
     * Constante con el nombre de la secuencia de cartera dependiente
     */
    private static final String SECUENCIA_CARTERA_DEPENDIENTE = "Sec_CarteraDependiente";

    /**
     * Constante con el nombre de la secuencia de cartera dependiente
     */
    private static final String SECUENCIA_DETALLE_SOLICITUD_GESTION_COBRO = "Sec_DetalleSolicitudGestionCobro";

    /**
     * Referencia a la unidad de persistencia del servicio.
     */
    @PersistenceContext(unitName = "cartera_PU")
    private EntityManager entityManager;

    /**
     * Injección de EJB para consultas en Core
     */
    @Inject
    private IConsultasModeloCore consultasModeloCore;

    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(CarteraBusiness.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.cartera.service.CarteraService#guardarParametrizacionCartera(com.asopagos.dto.modelo.ParametrizacionCarteraModeloDTO)
     */
    @Override
    public void guardarParametrizacionPreventivaCartera(ParametrizacionPreventivaModeloDTO preventivaModeloDTO) {
        logger.debug("Inicia guardarParametrizacionPreventivaCartera(ParametrizacionPreventivaModeloDTO preventivaModeloDTO) ");
        try {
            logger.debug("Inicia  ");
            /* Se crea instancia de objeto ParametrizacionCarteraModeloDTO */
            ParametrizacionCarteraModeloDTO parametrizacionCarteraModeloDTO = consultarParametrizacionCartera(TipoParametrizacionCarteraEnum.GESTION_PREVENTIVA);
            logger.debug("consultarParametrizacionCartera::  " + parametrizacionCarteraModeloDTO);
            /* Proceso que convierte DTO a entity */
            ParametrizacionPreventiva parametrizacionPreventiva = preventivaModeloDTO.convertToParametrizacionPreventivaEntity();
            /* Se valida de que el objeto exista */
            if (parametrizacionCarteraModeloDTO != null) {
                logger.debug("parametrizacionCarteraModeloDTO!=null::  " + parametrizacionCarteraModeloDTO);
                parametrizacionPreventiva.setIdParametrizacionCartera(parametrizacionCarteraModeloDTO.getIdParametrizacionCartera());
            }
            /* Se guarda la información de la parametrización */
            guardarParametrizacionCartera(parametrizacionPreventiva);
        } catch (Exception e) {
            logger.debug("Finaliza guardarParametrizacionPreventivaCartera(ParametrizacionPreventivaModeloDTO preventivaModeloDTO)");
            logger.error(LOG_OCURRIO_UN_ERROR, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionCartera(com.asopagos.enumeraciones.cartera.
     * TipoParametrizacionCarteraEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionCarteraModeloDTO consultarParametrizacionCartera(TipoParametrizacionCarteraEnum tipoParametrizacion) {
        logger.debug("Inicia metodo consultarParametrizacionCartera(TipoParametrizacionCarteraEnum tipoParametrizacion)");
        try {
            /* Se almacena el resultado de la consulta sobre el objeto ParametrizacionCarteraModeloDTO */
            ParametrizacionCartera parametrizacionCartera = (ParametrizacionCartera) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_CARTERA_TIPO_PARAMETRIZACION).setParameter("tipoParametrizacion", tipoParametrizacion).getSingleResult();
            logger.debug("Finaliza metodo consultarParametrizacion(TipoParametrizacionCarteraEnum tipoParametrizacion)");

            switch (tipoParametrizacion) {
                case GESTION_PREVENTIVA:
                    /* Se consulta la parametrizacion de la cartera */
                    ParametrizacionPreventiva parametrizacionPreventiva = (ParametrizacionPreventiva) parametrizacionCartera;
                    /* Se instancia objeto ParametrizacionPreventivaModeloDTO */
                    ParametrizacionPreventivaModeloDTO preventivaModeloDTO = new ParametrizacionPreventivaModeloDTO();
                    /* Se convierte a objeto DTO */
                    preventivaModeloDTO.convertToDTO(parametrizacionPreventiva);
                    return preventivaModeloDTO;
                case FISCALIZACION_APORTANTES:
                    /* Se consulta la parametrizacion de la cartera */
                    ParametrizacionFiscalizacion parametrizacionFiscalizacion = (ParametrizacionFiscalizacion) parametrizacionCartera;
                    /* Se instancia objeto ParametrizacionPreventivaModeloDTO */
                    ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO = new ParametrizacionFiscalizacionModeloDTO();
                    /* Se convierte a objeto DTO */
                    parametrizacionFiscalizacionModeloDTO.convertToDTO(parametrizacionFiscalizacion);
                    return parametrizacionFiscalizacionModeloDTO;
                default:
                    break;
            }
            return null;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarParametrizacionCartera: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudPreventiva(com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO)
     */
    @Override
    public Long guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO) {
        logger.debug("Inicio de método guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO)");
        try {
            SolicitudPreventiva solicitudPreventiva = solicitudPreventivaDTO.convertToEntity();
            if (solicitudPreventiva.getIdSolicitudPreventiva() != null) {
                entityManager.merge(solicitudPreventiva);
            } else {
                entityManager.persist(solicitudPreventiva);
            }
            logger.debug("Fin de método guardarSolicitudPreventiva(SolicitudPreventivaModeloDTO solicitudPreventivaDTO)");
            return solicitudPreventiva.getSolicitudGlobal().getIdSolicitud();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarSolicitudPreventiva: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public Long guardarRelacionComunicadoBitacoraCartera(GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO) {
        logger.debug("Inicio de método guardarRelacionComunicadoBitacoraCartera(GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO)");
        try {
            ComunicadoTransversalBitacora comunicadoTransversalBitacora = relacionComunicadoBitacoraCarteraDTO.convertToEntity();
            if (comunicadoTransversalBitacora.getComunicado() != null) {
                entityManager.merge(comunicadoTransversalBitacora);
            } else {
                entityManager.persist(comunicadoTransversalBitacora);
            }
            logger.debug("Fin de método guardarRelacionComunicadoBitacoraCartera(GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO)");
            return comunicadoTransversalBitacora.getIdComunicadoTransversalBitacora();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarSolicitudPreventiva: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudPreventiva(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPreventivaModeloDTO consultarSolicitudPreventiva(String numeroRadicacion) {
        logger.debug("Inicia el método consultarSolicitudPreventiva(String)");
        try {
            SolicitudPreventiva solicitudPreventiva = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_PREVENTIVA_POR_NUMERO_RADICADO, SolicitudPreventiva.class).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
            SolicitudPreventivaModeloDTO solicitudPreventivaDTO = new SolicitudPreventivaModeloDTO();
            solicitudPreventivaDTO.convertToDTO(solicitudPreventiva);
            return solicitudPreventivaDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza el método consultarSolicitudPreventiva,no se encontraron resultados con el número de radicación" + numeroRadicacion);
            return null;
        } catch (NonUniqueResultException nur) {
            logger.error("Finaliza consultarSolicitudPreventiva(String)");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarSolicitudPreventiva(String numeroRadicacion)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCicloFiscalizacionActual()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CicloCarteraModeloDTO consultarCicloFiscalizacionActual() {
        try {
            logger.debug("Inicio de método consultarCicloFiscalizacionActual()");
            CicloCartera ciclo = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION, CicloCartera.class).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, TipoCicloEnum.FISCALIZACION).getSingleResult();
            CicloCarteraModeloDTO cicloDTO = new CicloCarteraModeloDTO();
            List<Long> idCiclo = new ArrayList<>();
            idCiclo.add(ciclo.getIdCicloCartera());
            /* Se obtiene la cantidad de entidades del ciclo de fiscalización */
            List<Object[]> objects = consultarCantidadEntidadesCiclosFiscalizacion(idCiclo);
            if (objects != null && !objects.isEmpty()) {
                Object[] object = objects.get(0);
                long cantidadEntidad = (long) object[0];
                cicloDTO.setCantidadEntidades(cantidadEntidad);
            }
            Long entidadesFiscalizadas = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_FISCALIZACION_CONSULTAR_APORTANTES_FISCALIZADOS_POR_CICLO).setParameter("estadoFiscalizacion", EstadoFiscalizacionEnum.FINALIZADA).setParameter("idCiclo", idCiclo).getSingleResult();
            cicloDTO.setCantidadFiscalizadas(entidadesFiscalizadas);
            cicloDTO.convertToDTO(ciclo);
            logger.debug("Fin de método consultarCicloFiscalizacionActual()");
            return cicloDTO;
        } catch (NonUniqueResultException nure) {
            logger.error("Ocurrió un error consultando el ciclo, mas de un ciclo en curso", nure);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nure);
        } catch (NoResultException nre) {
            logger.debug("No hay ciclos en curso");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarCicloFiscalizacionActual", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesCicloActual(java.lang.String, java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarAportantesCiclo(List<EstadoFiscalizacionEnum> estado, Long idPersona, List<String> analista) {
        logger.debug("Inicia consultarAportantesCiclo(List<EstadoFiscalizacionEnum> estadoFiscalizacion, Long idPersona, List<String> destinatario)");
        List<SimulacionDTO> simulacionDTOs = new ArrayList<>();

        //se debe hacer una subconsutla que me traiga el id del ciclo haciendo una subconsutla que el cicloaporten que tiene
        //el id del ciclo busque en ciclofiscalizaion donde su estado sea activo
        try {
            if (idPersona == null) {

                simulacionDTOs = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CICLOFISCALIZACION_CONSULTAR_APORTANTES_CICLO_ACTUAL_SIN_PERSONA).setParameter("estadoFiscalizacion", estado).setParameter("destinatario", analista).setParameter(ESTADO_CICLO_CARTERA, EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, TipoCicloEnum.FISCALIZACION).getResultList();

            } else {
                //consulta con persona
                simulacionDTOs = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CICLOFISCALIZACION_CONSULTAR_APORTANTES_CICLO_ACTUAL_CON_PERSONA).setParameter("estadoFiscalizacion", estado).setParameter("destinatario", analista).setParameter(ESTADO_CICLO_CARTERA, EstadoCicloCarteraEnum.ACTIVO).setParameter("idPersona", idPersona).setParameter(TIPO_CICLO, TipoCicloEnum.FISCALIZACION).getResultList();
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarAportantesCiclo(List<EstadoFiscalizacionEnum> estadoFiscalizacion, Long idPersona, List<String> destinatario)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza consultarAportantesCiclo(List<EstadoFiscalizacionEnum> estadoFiscalizacion, Long idPersona, List<String> destinatario)");
        return simulacionDTOs;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarCicloFiscalizacion(com.asopagos.dto.modelo.CicloFiscalizacionModeloDTO)
     */
    @Override
    public CicloCarteraModeloDTO guardarCicloCartera(CicloCarteraModeloDTO ciclo) {
        logger.debug("Inicio de método guardarCicloCartera(CicloCarteraModeloDTO)");
        try {
            CicloCartera cicloCartera = ciclo.convertToEntity();
            if (cicloCartera.getIdCicloCartera() != null) {
                entityManager.merge(cicloCartera);
            } else {
                entityManager.persist(cicloCartera);
            }
            ciclo.convertToDTO(cicloCartera);
            List<CicloAportanteModeloDTO> cicloAportantesModeloDTO = new ArrayList<>();
            for (CicloAportanteModeloDTO cicloAportanteModeloDTOIter : ciclo.getAportantes()) {
                CicloAportante cicloAportante = cicloAportanteModeloDTOIter.convertToEntity();
                /* Se settea ciclo de cartera */
                cicloAportante.setIdCicloCartera(cicloCartera.getIdCicloCartera());
                if (cicloAportante.getIdCicloAportante() != null) {
                    entityManager.merge(cicloAportante);
                    cicloAportanteModeloDTOIter.convertToDTO(cicloAportante);
                    cicloAportantesModeloDTO.add(cicloAportanteModeloDTOIter);
                } else {
                    entityManager.persist(cicloAportante);
                    cicloAportanteModeloDTOIter.convertToDTO(cicloAportante);
                    cicloAportantesModeloDTO.add(cicloAportanteModeloDTOIter);
                }
            }
            ciclo.setAportantes(cicloAportantesModeloDTO);
            logger.debug("Fin de método guardarCicloCartera(CicloCarteraModeloDTO )");
            return ciclo;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarCicloCartera: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#
     * actualizarEstadoSolicitudPreventiva(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum)
     */
    @Override
    public void actualizarEstadoSolicitudPreventiva(String numeroRadicacion, EstadoSolicitudPreventivaEnum estadoSolicitud) {
        try {
            logger.debug("Inicia actualizarEstadoSolicitudPreventiva(String numeroRadicacion, EstadoSolicitudPreventivaEnum estadoSolicitud)");
            SolicitudPreventivaModeloDTO solicitudPreventivaDTO = consultarSolicitudPreventiva(numeroRadicacion);
            SolicitudPreventiva solicitudPreventiva = solicitudPreventivaDTO.convertToEntity();

            /*
             * se verifica si el nuevo estado es CERRADA para actualizar el
             * resultado del proceso
             */
            if (EstadoSolicitudPreventivaEnum.CERRADA.equals(estadoSolicitud) && (EstadoSolicitudPreventivaEnum.EXITOSA.equals(solicitudPreventiva.getEstadoSolicitudPreventiva()) || EstadoSolicitudPreventivaEnum.NO_EXITOSA.equals(solicitudPreventiva.getEstadoSolicitudPreventiva()) || EstadoSolicitudPreventivaEnum.DESISTIDA.equals(solicitudPreventiva.getEstadoSolicitudPreventiva()) || EstadoSolicitudPreventivaEnum.CANCELADA.equals(solicitudPreventiva.getEstadoSolicitudPreventiva()))) {
                solicitudPreventiva.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudPreventiva.getEstadoSolicitudPreventiva().name()));
            }
            solicitudPreventiva.setEstadoSolicitudPreventiva(estadoSolicitud);
            entityManager.merge(solicitudPreventiva);
            logger.debug("Finaliza actualizarEstadoSolicitudPreventiva(String numeroRadicacion, EstadoSolicitudPreventivaEnum estadoSolicitud)");
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado actualizarEstadoSolicitudPreventiva", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesGestionPreventivaMock()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SimulacionPaginadaDTO consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion, UriInfo uri, HttpServletResponse response) {
        logger.debug("Inicia consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion)");

        try {

            MultivaluedMap<String, String> parametros = new MultivaluedHashMap<>();
            if (filtrosParametrizacion.getParams() != null) {
                for (Entry<String, List<String>> e : filtrosParametrizacion.getParams().entrySet()) {
                    parametros.put(e.getKey(), e.getValue());
                }
            }

            StringBuilder tiposAfiliado = new StringBuilder();
            StringBuilder tiposSolicitante = new StringBuilder();
            if (filtrosParametrizacion.getIncluirIndependientes() && filtrosParametrizacion.getIncluirPensionados()) {
                tiposAfiliado.append(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name());
                tiposAfiliado.append(",");
                tiposAfiliado.append(TipoAfiliadoEnum.PENSIONADO.name());
                tiposSolicitante.append(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name());
                tiposSolicitante.append(",");
                tiposSolicitante.append(TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name());
            } else if (filtrosParametrizacion.getIncluirIndependientes() || filtrosParametrizacion.getIncluirPensionados()) {
                tiposAfiliado.append(filtrosParametrizacion.getIncluirIndependientes() ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name() : TipoAfiliadoEnum.PENSIONADO.name());
                tiposSolicitante.append(filtrosParametrizacion.getIncluirIndependientes() ? TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name() : TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name());
            }

            Boolean estadoMoroso = Boolean.FALSE;
            Boolean estadoAlDia = Boolean.FALSE;
            if (filtrosParametrizacion.getEstadosCartera() != null && !filtrosParametrizacion.getEstadosCartera().isEmpty()) {
                if (filtrosParametrizacion.getEstadosCartera().get(0).equals(EstadoCarteraEnum.MOROSO.name())) {
                    estadoMoroso = Boolean.TRUE;
                }
                if (filtrosParametrizacion.getEstadosCartera().get(0).equals(EstadoCarteraEnum.AL_DIA.name())) {
                    estadoAlDia = Boolean.TRUE;
                }
            } else {
                estadoMoroso = Boolean.TRUE;
                estadoAlDia = Boolean.TRUE;
            }

            StoredProcedureQuery procedimiento = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_PREVENTIVA).setParameter("periodoInicialEmpleador", filtrosParametrizacion.getPeriodoInicialAportesEmpleador()).setParameter("periodoFinalEmpleador", filtrosParametrizacion.getPeriodoFinalAportesEmpleador()).setParameter("periodoInicial", filtrosParametrizacion.getPeriodoInicialAportes() != null ? filtrosParametrizacion.getPeriodoInicialAportes() : "").setParameter("periodoFinal", filtrosParametrizacion.getPeriodoFinalAportes() != null ? filtrosParametrizacion.getPeriodoFinalAportes() : "").setParameter("periodoInicialMoraEmpleador", filtrosParametrizacion.getPeriodoInicialMorosoEmpleador() != null ? filtrosParametrizacion.getPeriodoInicialMorosoEmpleador() : new Date()).setParameter("periodoFinalMoraEmpleador", filtrosParametrizacion.getPeriodoFinalMorosoEmpleador() != null ? filtrosParametrizacion.getPeriodoFinalMorosoEmpleador() : new Date()).setParameter("periodoInicialMora", filtrosParametrizacion.getPeriodoInicialMoroso() != null ? filtrosParametrizacion.getPeriodoInicialMoroso() : new Date()).setParameter("periodoFinalMora", filtrosParametrizacion.getPeriodoFinalMoroso() != null ? filtrosParametrizacion.getPeriodoFinalMoroso() : new Date()).setParameter("tipoAfiliadoEnum", tiposAfiliado.toString()).setParameter("tipoSolicitanteEnum", tiposSolicitante.toString()).setParameter("noAplicarCriterioCantVecesMoroso", filtrosParametrizacion.getSinFiltroMora()).setParameter("esAutomatico", filtrosParametrizacion.getAutomatico()).setParameter("estadoMoroso", estadoMoroso).setParameter("estadoAlDia", estadoAlDia).setParameter("personas", filtrosParametrizacion.getIncluirIndependientes() || filtrosParametrizacion.getIncluirPensionados() ? Boolean.TRUE : Boolean.FALSE).setParameter("valorAportes", filtrosParametrizacion.getValorMinimoAportes()).setParameter("cantidadTrabajadoresActivos", filtrosParametrizacion.getCantidadTrabajadoresActivos()).setParameter("cantidadDiaHabilEjecucioAutomatica", filtrosParametrizacion.getDiasHabilesPrevios() != null ? filtrosParametrizacion.getDiasHabilesPrevios() : 0).setParameter("diaHabilActual", filtrosParametrizacion.getDiaActualHabil() != null ? filtrosParametrizacion.getDiaActualHabil() : 0);
            logEjecucionGestionPreventiva(procedimiento);
            procedimiento.execute();
            List<Object[]> aportantes = procedimiento.getResultList();
            List<SimulacionDTO> simulaciones = new ArrayList<>();
            for (Object[] aportante : aportantes) {
                SimulacionDTO simulacion = new SimulacionDTO();
                simulacion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[0]));
                simulacion.setNumeroIdentificacion((String) aportante[1]);
                simulacion.setNombreRazonSocial((String) aportante[2]);
                simulacion.setCorreoElectronico(aportante[3] != null ? (String) aportante[3] : null);
                simulacion.setEstadoActualCartera(aportante[4] == null ? EstadoCarteraEnum.AL_DIA : EstadoCarteraEnum.valueOf((String) aportante[4]));
                BigDecimal valorPromedioAportes = aportante[5] != null ? (BigDecimal) aportante[5] : new BigDecimal(0);
                simulacion.setValorPromedioAportes(valorPromedioAportes);
                simulacion.setCantidadVecesMoroso(aportante[6] != null ? new Short(((Integer) aportante[6]).toString()) : new Short("0"));
                simulacion.setTrabajadoresActivos(aportante[7] != null ? (Integer) aportante[7] : 0);
                simulacion.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportante[9]));

                Boolean autorizacion = aportante[8] != null ? (Boolean) aportante[8] : false;
                if (!autorizacion) {
                    simulacion.setTipoGestionPreventiva(TipoGestionCarteraEnum.MANUAL);
                } else {
                    simulacion.setTipoGestionPreventiva(TipoGestionCarteraEnum.AUTOMATICA);
                }
                simulacion.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                simulacion.setEstadoEmpleador(EstadoEmpleadorEnum.ACTIVO);
                simulaciones.add(simulacion);
            }

            if (filtrosParametrizacion.getMayorTrabajadoresActivos() > 0) {
                Integer limiteAportantes = Math.round(simulaciones.size() * filtrosParametrizacion.getMayorTrabajadoresActivos() / 100);
                Collections.sort(simulaciones, new Comparator<SimulacionDTO>() {
                    @Override
                    public int compare(SimulacionDTO s1, SimulacionDTO s2) {
                        return s2.getTrabajadoresActivos().compareTo(s1.getTrabajadoresActivos());
                    }
                });
                for (int i = 0; i < limiteAportantes; i++) {
                    simulaciones.get(i).setTipoGestionPreventiva(TipoGestionCarteraEnum.MANUAL);
                }
            }
            if (filtrosParametrizacion.getMayorVecesMoroso() > 0) {
                Integer limiteAportantes = Math.round(simulaciones.size() * filtrosParametrizacion.getMayorVecesMoroso() / 100);
                Collections.sort(simulaciones, new Comparator<SimulacionDTO>() {
                    @Override
                    public int compare(SimulacionDTO s1, SimulacionDTO s2) {
                        return s2.getCantidadVecesMoroso().compareTo(s1.getCantidadVecesMoroso());
                    }
                });
                for (int i = 0; i < limiteAportantes; i++) {
                    simulaciones.get(i).setTipoGestionPreventiva(TipoGestionCarteraEnum.MANUAL);
                }
            }
            if (filtrosParametrizacion.getMayorValorPromedio() > 0) {
                Integer limiteAportantes = Math.round(simulaciones.size() * filtrosParametrizacion.getMayorValorPromedio() / 100);
                Collections.sort(simulaciones, new Comparator<SimulacionDTO>() {
                    @Override
                    public int compare(SimulacionDTO s1, SimulacionDTO s2) {
                        return s2.getValorPromedioAportes().compareTo(s1.getValorPromedioAportes());
                    }
                });
                for (int i = 0; i < limiteAportantes; i++) {
                    simulaciones.get(i).setTipoGestionPreventiva(TipoGestionCarteraEnum.MANUAL);
                }
            } else {
                Collections.sort(simulaciones, new Comparator<SimulacionDTO>() {
                    @Override
                    public int compare(SimulacionDTO s1, SimulacionDTO s2) {
                        return s2.getValorPromedioAportes().compareTo(s1.getValorPromedioAportes());
                    }
                });
            }

            Collections.sort(simulaciones, new Comparator<SimulacionDTO>() {
                @Override
                public int compare(SimulacionDTO s1, SimulacionDTO s2) {
                    return s1.getTipoGestionPreventiva().compareTo(s2.getTipoGestionPreventiva());
                }
            });

            SimulacionPaginadaDTO simulacionPaginada = new SimulacionPaginadaDTO();
            simulacionPaginada.setSimulaciones(simulaciones);
            if (response.getHeader("totalRecords") != null) {
                simulacionPaginada.setTotalRecords(new Integer(response.getHeader("totalRecords")));
            }
            logger.debug("Finaliza consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion)");
            return simulacionPaginada;

        } catch (Exception e) {
            logger.error("Se presento un error durante la consulta ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesGestionPreventivaMock()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarAportantesParametrizacionFiscalizacion(FiltrosParametrizacionDTO filtrosParametrizacion) {
        logger.debug("Inicia consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion)");

        StringBuilder tiposAfiliado = new StringBuilder();
        StringBuilder tiposSolicitante = new StringBuilder();
        if (filtrosParametrizacion.getIncluirIndependientes() && filtrosParametrizacion.getIncluirPensionados()) {
            tiposAfiliado.append(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name());
            tiposAfiliado.append(",");
            tiposAfiliado.append(TipoAfiliadoEnum.PENSIONADO.name());
            tiposSolicitante.append(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name());
            tiposSolicitante.append(",");
            tiposSolicitante.append(TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name());
        } else if (filtrosParametrizacion.getIncluirIndependientes() || filtrosParametrizacion.getIncluirPensionados()) {
            tiposAfiliado.append(filtrosParametrizacion.getIncluirIndependientes() ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name() : TipoAfiliadoEnum.PENSIONADO.name());
            tiposSolicitante.append(filtrosParametrizacion.getIncluirIndependientes() ? TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name() : TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name());
        }

        Boolean estadoMoroso = Boolean.FALSE;
        Boolean estadoAlDia = Boolean.FALSE;
        if (filtrosParametrizacion.getEstadosCartera() != null && !filtrosParametrizacion.getEstadosCartera().isEmpty()) {
            if (filtrosParametrizacion.getEstadosCartera().size() > 1) {
                estadoMoroso = Boolean.TRUE;
                estadoAlDia = Boolean.TRUE;
            } else if (filtrosParametrizacion.getEstadosCartera().get(0).equals(EstadoCarteraEnum.MOROSO.name())) {
                estadoMoroso = Boolean.TRUE;
            } else if (filtrosParametrizacion.getEstadosCartera().get(0).equals(EstadoCarteraEnum.AL_DIA.name())) {
                estadoAlDia = Boolean.TRUE;
            }
        }

        StringBuilder validaciones = new StringBuilder();
        logger.debug("27. iniciando validaciones Pila  ");
        if (!filtrosParametrizacion.getValidacionesPila().isEmpty()) {
            for (int i = 0; i < filtrosParametrizacion.getValidacionesPila().size(); i++) {
                if (i != 0) {
                    validaciones.append(",");
                }
                validaciones.append(filtrosParametrizacion.getValidacionesPila().get(i));
            }
        }

        logger.debug("26. iniciando consulta  CONSULTAR_PARAMETRIZACION_FISCALIZACION");
        StoredProcedureQuery consulta = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_FISCALIZACION)
        .setParameter("periodoInicialEmpleador", filtrosParametrizacion.getPeriodoInicialAportesEmpleador())
        .setParameter("periodoFinalEmpleador", filtrosParametrizacion.getPeriodoFinalAportesEmpleador())
        .setParameter("periodoInicial", filtrosParametrizacion.getPeriodoInicialAportes() != null ? filtrosParametrizacion.getPeriodoInicialAportes() : "")
        .setParameter("periodoFinal", filtrosParametrizacion.getPeriodoFinalAportes() != null ? filtrosParametrizacion.getPeriodoFinalAportes() : "")
        .setParameter("periodoInicialMoraEmpleador", filtrosParametrizacion.getPeriodoInicialMorosoEmpleador() != null ? filtrosParametrizacion.getPeriodoInicialMorosoEmpleador() : new Date())
        .setParameter("periodoFinalMoraEmpleador", filtrosParametrizacion.getPeriodoFinalMorosoEmpleador() != null ? filtrosParametrizacion.getPeriodoFinalMorosoEmpleador() : new Date())
        .setParameter("periodoInicialMora", filtrosParametrizacion.getPeriodoInicialMoroso() != null ? filtrosParametrizacion.getPeriodoInicialMoroso() : new Date())
        .setParameter("periodoFinalMora", filtrosParametrizacion.getPeriodoFinalMoroso() != null ? filtrosParametrizacion.getPeriodoFinalMoroso() : new Date())
        .setParameter("tipoAfiliadoEnum", tiposAfiliado.toString()).setParameter("tipoSolicitanteEnum", tiposSolicitante.toString())
        .setParameter("noAplicarCriterioCantVecesMoroso", filtrosParametrizacion.getSinFiltroMora())
        .setParameter("estadoMoroso", estadoMoroso).setParameter("estadoAlDia", estadoAlDia)
        .setParameter("personas", filtrosParametrizacion.getIncluirIndependientes() || filtrosParametrizacion.getIncluirPensionados() ? Boolean.TRUE : Boolean.FALSE)
        .setParameter("valorAportes", filtrosParametrizacion.getValorMinimoAportes()).setParameter("cantidadTrabajadoresActivos", filtrosParametrizacion.getCantidadTrabajadoresActivos())
        .setParameter("corteEntidades", filtrosParametrizacion.getCorteEntidades()).setParameter("preventiva", filtrosParametrizacion.getGestionPreventiva())
        .setParameter("validacionesPILA", filtrosParametrizacion.getValidacionesPila().isEmpty() ? Boolean.FALSE : Boolean.TRUE)
        .setParameter("fechaInicialEmpleadorP", filtrosParametrizacion.getPeriodoInicialEmpleador() != null ? filtrosParametrizacion.getPeriodoInicialEmpleador() : new Date())
        .setParameter("fechaFinalEmpleadorP", filtrosParametrizacion.getPeriodoFinalEmpleador() != null ? filtrosParametrizacion.getPeriodoFinalEmpleador() : new Date())
        .setParameter("fechaInicialP", filtrosParametrizacion.getPeriodoInicial() != null ? filtrosParametrizacion.getPeriodoInicial() : new Date())
        .setParameter("fechaFinalP", filtrosParametrizacion.getPeriodoFinal() != null ? filtrosParametrizacion.getPeriodoFinal() : new Date())
        .setParameter("motivoFiscalizacionEnum", validaciones.toString()).setParameter("estadoAfiliacionCartera", filtrosParametrizacion.getEstadoCarteraPantalla())
        .setParameter("incluirLC2",filtrosParametrizacion.getIncluirLC2()? Boolean.TRUE : Boolean.FALSE)
        .setParameter("incluirLC3",filtrosParametrizacion.getIncluirLC3()? Boolean.TRUE : Boolean.FALSE);
        logger.debug("finalizando consulta CONSULTAR_PARAMETRIZACION_FISCALIZACION ");

        consulta.execute();
        logger.debug("Consulta ejecutada");
        List<Object[]> aportantes = consulta.getResultList();
        List<SimulacionDTO> simulaciones = new ArrayList<>();
        for (Object[] aportante : aportantes) {
            SimulacionDTO simulacion = new SimulacionDTO();
            simulacion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[0]));
            simulacion.setNumeroIdentificacion((String) aportante[1]);
            simulacion.setNombreRazonSocial((String) aportante[2]);
            simulacion.setEstadoAfiliado(EstadoAfiliadoEnum.valueOf((String) aportante[3]));
            simulacion.setEstadoActualCartera(aportante[4] == null ? EstadoCarteraEnum.AL_DIA : EstadoCarteraEnum.valueOf((String) aportante[4]));
            BigDecimal valorPromedioAportes = aportante[5] != null ? (BigDecimal) aportante[5] : new BigDecimal(0);
            simulacion.setValorPromedioAportes(valorPromedioAportes);
            Short cantidadVecesMoroso = aportante[6] != null ? new Short(((Integer) aportante[6]).toString()) : new Short("0");
            simulacion.setCantidadVecesMoroso(cantidadVecesMoroso);
            Integer trabajadoresActivos = aportante[7] != null ? (Integer) aportante[7] : 0;
            simulacion.setTrabajadoresActivos(trabajadoresActivos);
            simulacion.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportante[8]));
            if(aportante.length == 10){
                simulacion.setTipoLineaCobro(TipoLineaCobroEnum.valueOf((String) aportante[9]));
                }
            simulacion.setEstadoEmpleador(EstadoEmpleadorEnum.ACTIVO);
            simulaciones.add(simulacion);
        }
        logger.debug("Finaliza consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion)");
        return simulaciones;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudFiscalizacion(com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO)
     */
    @Override
    public Long guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO) {
        logger.debug("Inicia guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO) ");
        try {
            /* Se consulta si hay solicitudes de fiscalizacion */
            SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion(solicitudFiscalizacionDTO.getNumeroRadicacion());
            SolicitudFiscalizacion solicitudFiscalizacion = null;
            /* Se averigua de que el objeto tenga información */
            if (solicitudFiscalizacionModeloDTO != null) {
                solicitudFiscalizacionDTO.setIdSolicitud(solicitudFiscalizacionModeloDTO.getIdSolicitud());
                /* Se instancia objeto SolicitudFiscalizacion y se carga con informacion de solicitudFiscalizacionDTO */
                solicitudFiscalizacion = solicitudFiscalizacionDTO.convertToSolicitudFiscalizacionEntity();
                solicitudFiscalizacion.setIdSolicitudFiscalizacion(solicitudFiscalizacionModeloDTO.getIdSolicitudFiscalizacion());
            } else {
                /* Se instancia objeto SolicitudFiscalizacion y se carga con informacion de solicitudFiscalizacionDTO */
                solicitudFiscalizacion = solicitudFiscalizacionDTO.convertToSolicitudFiscalizacionEntity();
            }

            if (solicitudFiscalizacion.getIdSolicitudFiscalizacion() != null) {
                entityManager.merge(solicitudFiscalizacion);
            } else {
                entityManager.persist(solicitudFiscalizacion);
            }
            logger.debug("Finaliza guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)) ");
            return solicitudFiscalizacion.getSolicitudGlobal().getIdSolicitud();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarSolicitudFiscalizacion: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudGestionCobroManual(com.asopagos.dto.modelo.
     * SolicitudGestionCobroManualModeloDTO)
     */
    @Override
    public Long guardarSolicitudGestionCobroManual(SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO) {
        /* Se consulta si hay solicitudes de fiscalizacion */
        SolicitudGestionCobroManualModeloDTO solicitudGestionManualDTO = null;
        if (solicitudGestionCobroDTO.getNumeroRadicacion() != null) {
            solicitudGestionManualDTO = consultarSolicitudGestionCobroManual(solicitudGestionCobroDTO.getNumeroRadicacion(), null);
        }
        SolicitudGestionCobroManual solicitudGestionManual = null;
        /* Se averigua de que el objeto tenga información */
        if (solicitudGestionManualDTO != null) {
            solicitudGestionCobroDTO.setIdSolicitud(solicitudGestionManualDTO.getIdSolicitud());
            /* Se instancia objeto SolicitudFiscalizacion y se carga con informacion de solicitudFiscalizacionDTO */
            solicitudGestionManual = solicitudGestionCobroDTO.convertToEntity();
            solicitudGestionManual.setIdSolicitudGestionCobroManual(solicitudGestionManualDTO.getIdSolicitudGestionCobroManual());
        } else {
            /* Se instancia objeto SolicitudFiscalizacion y se carga con informacion de solicitudFiscalizacionDTO */
            solicitudGestionManual = solicitudGestionCobroDTO.convertToEntity();
        }

        if (solicitudGestionManual.getIdSolicitudGestionCobroManual() != null) {
            entityManager.merge(solicitudGestionManual);
        } else {
            entityManager.persist(solicitudGestionManual);
        }
        logger.debug("Finaliza guardarSolicitudFiscalizacion(SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO)) ");
        return solicitudGestionManual.getSolicitudGlobal().getIdSolicitud();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudFiscalizacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudFiscalizacionModeloDTO consultarSolicitudFiscalizacion(String numeroRadicacion) {
        /* Se consulta la Solicitud de Fiscalizacion por medio del numero radicacion */
        logger.debug("Inicia consultarSolicitudFiscalizacion(String numeroRadicacion) ");
        try {
            SolicitudFiscalizacion solicitudFiscalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_FISCALIZACION_POR_NUMERO_RADICACION, SolicitudFiscalizacion.class).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
            /* Se instancia objeto SolicitudFiscalizacionModeloDTO */
            SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = new SolicitudFiscalizacionModeloDTO();
            /* Se valida si solicitudFiscalizacion contiene información */
            solicitudFiscalizacionModeloDTO.convertToDTO(solicitudFiscalizacion);
            return solicitudFiscalizacionModeloDTO;
        } catch (NoResultException ne) {
            logger.debug("Finaliza el método consultarSolicitudFiscalizacion(String)" + interpolate("No se encontraron resultados con el número de radicación {0} ingresado.", numeroRadicacion));
            return null;
        } catch (NonUniqueResultException nur) {
            logger.error("Finaliza consultarSolicitudFiscalizacion(String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarSolicitudFiscalizacion: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarEstadoSolicitudFiscalizacion(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum)
     */
    @Override
    public void actualizarEstadoSolicitudFiscalizacion(String numeroRadicacion, EstadoFiscalizacionEnum estadoSolicitud) {
        logger.debug("Inicia actualizarEstadoSolicitudFiscalizacion(String numeroRadicacion, EstadoFiscalizacionEnum estadoSolicitud) ");
        try {
            /* Se carga la información de solicitudFiscalizacionModeloDTO */
            SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion(numeroRadicacion);
            /* Se valida si el objeto existe para luego ser actualizado */
            if (solicitudFiscalizacionModeloDTO != null) {
                SolicitudFiscalizacion fiscalizacion = solicitudFiscalizacionModeloDTO.convertToSolicitudFiscalizacionEntity();
                fiscalizacion.setEstadoFiscalizacion(estadoSolicitud);
                entityManager.merge(fiscalizacion);
            } else {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }
            logger.debug("Finaliza actualizarEstadoSolicitudFiscalizacion(String numeroRadicacion, EstadoFiscalizacionEnum estadoSolicitud) ");
        } catch (Exception e) {
            logger.error("Finaliza el método actualizarEstadoSolicitudFiscalizacion,no se encontraron resultados con el número de radicación ingresado " + numeroRadicacion);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarParametrizacionFiscalizacionCartera(com.asopagos.dto.modelo.ParametrizacionFiscalizacionModeloDTO)
     */
    @Override
    public void guardarParametrizacionFiscalizacionCartera(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO) {
        logger.debug("Inicia guardarParametrizacionFiscalizacionCartera(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO) ");
        try {
            /* Se crea instancia de objeto ParametrizacionCarteraModeloDTO */
            ParametrizacionCarteraModeloDTO parametrizacionCarteraModeloDTO = consultarParametrizacionCartera(TipoParametrizacionCarteraEnum.FISCALIZACION_APORTANTES);
            /* Proceso que convierte DTO a entity */
            ParametrizacionFiscalizacion parametrizacionFiscalizacion = parametrizacionFiscalizacionModeloDTO.convertToParametrizacionFiscalizacionEntity();
            /* Se valida de que el objeto exista */
            if (parametrizacionCarteraModeloDTO != null) {
                parametrizacionFiscalizacion.setIdParametrizacionCartera(parametrizacionCarteraModeloDTO.getIdParametrizacionCartera());
            }
            /* Se guarda la información de la parametrización */
            guardarParametrizacionCartera(parametrizacionFiscalizacion);
        } catch (Exception e) {
            logger.debug("Finaliza guardarParametrizacionFiscalizacionCartera(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarSolicitudPreventiva(java.lang.String,
     * com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO)
     */
    @Override
    public void actualizarSolicitudPreventiva(String numeroRadicacion, SolicitudPreventivaModeloDTO solicitudPreventivaDTO) {
        logger.debug("Inicia actualizarSolicitudPreventiva(String, SolicitudPreventivaModeloDTO)");
        SolicitudPreventiva solicitudPeventiva = null;
        try {
            solicitudPeventiva = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_PREVENTIVA_POR_NUMERO_RADICADO, SolicitudPreventiva.class).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
        } catch (NoResultException e) {
            solicitudPeventiva = null;
        }
        if (solicitudPeventiva != null) {
            entityManager.merge(solicitudPreventivaDTO.actualizarSolicitudPreventiva(solicitudPeventiva, solicitudPreventivaDTO));
        }
        logger.debug("Finaliza actualizarSolicitudPreventiva(String, SolicitudPreventivaModeloDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarProcesosFiscalizacionHistoricos(java.lang.Long,
     * com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum, java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CicloCarteraModeloDTO> consultarProcesosFiscalizacionHistoricos(Long idCicloFiscalizacion, EstadoCicloCarteraEnum estadoCicloFiscalizacion, Long fechaInicio, Long fechaFin) {
        logger.debug("Inicia metodo consultarProcesosFiscalizacionHistoricos");
        /* Se instancia la lista cicloFiscalizacionModeloDTOs */
        List<CicloCarteraModeloDTO> cicloFiscalizacionModeloDTOs = new ArrayList<>();
        try {
            /* Se crean los map */
            Map<String, String> fields = new HashMap<>();
            Map<String, Object> values = new HashMap<>();

            /* Se validan cada uno de los campos para poder ir seteando en los maps */
            if (idCicloFiscalizacion == null && estadoCicloFiscalizacion == null && fechaInicio == null && fechaFin == null) {
                logger.error(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }

            if (idCicloFiscalizacion != null) {
                fields.put(ID_CICLO_CARTERA, ConsultasDinamicasConstants.IGUAL);
                values.put(ID_CICLO_CARTERA, idCicloFiscalizacion);
            }

            if (estadoCicloFiscalizacion != null) {
                fields.put(ESTADO_CICLO_CARTERA, ConsultasDinamicasConstants.IGUAL);
                values.put(ESTADO_CICLO_CARTERA, estadoCicloFiscalizacion);
            }

            if (fechaInicio != null) {
                fields.put(FECHA_INICIO, ConsultasDinamicasConstants.MAYOR_IGUAL);
                values.put(FECHA_INICIO, new Date(fechaInicio));
            }

            if (fechaFin != null) {
                fields.put(FECHA_FIN, ConsultasDinamicasConstants.MENOR_IGUAL);
                values.put(FECHA_FIN, new Date(fechaFin));
            }
            /* se añade busqueda por tipo de ciclo de fiscalización pues la tabla es compartida para los ciclos manuales tambien */
            fields.put(TIPO_CICLO, ConsultasDinamicasConstants.IGUAL);
            values.put(TIPO_CICLO, TipoCicloEnum.FISCALIZACION);

            List<CicloCartera> cicloFiscalizacions = JPAUtils.consultaEntidad(entityManager, CicloCartera.class, fields, values);
            /* Se recorren los ciclos, se realiza el convert de entity a DTO y se van agregando a la lista */
            for (CicloCartera cicloFiscalizacion : cicloFiscalizacions) {
                CicloCarteraModeloDTO cicloFiscalizacionModeloDTO = new CicloCarteraModeloDTO();
                cicloFiscalizacionModeloDTO.convertToDTO(cicloFiscalizacion);
                cicloFiscalizacionModeloDTOs.add(cicloFiscalizacionModeloDTO);
            }
            List<Long> idCiclos = new ArrayList<>();
            /* Se recorren los ciclos de fiscalización y se almacenan sus id directamente en la lista de long */
            for (int i = 0; i < cicloFiscalizacionModeloDTOs.size(); i++) {
                idCiclos.add(cicloFiscalizacionModeloDTOs.get(i).getIdCicloFiscalizacion());
            }
            /* Se obtiene la cantidad de entidades del ciclo de fiscalización */
            List<Object[]> objects = consultarCantidadEntidadesCiclosFiscalizacion(idCiclos);

            /*
             * Se recorre la lista cicloFiscalizacionModeloDTOs para ir preguntado por el id del ciclo
             * si es igual al de la lista de objeto para así poder setear la cantidad de entidades
             */
            for (int i = 0; i < cicloFiscalizacionModeloDTOs.size(); i++) {
                for (Object[] object : objects) {
                    long idCiclo = (Long) object[1];
                    if (cicloFiscalizacionModeloDTOs.get(i).getIdCicloFiscalizacion() == idCiclo) {
                        long cantidadEntidad = (long) object[0];
                        cicloFiscalizacionModeloDTOs.get(i).setCantidadEntidades(cantidadEntidad);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Finaliza metodo consultarProcesosFiscalizacionHistoricos(Long idCicloFiscalizacion, EstadoCicloFiscalizacionEnum" + " estadoCicloFiscalizacion, Long fechaInicio, Long fechaFin)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug("Finaliza metodo consultarProcesosFiscalizacionHistoricos(Long idCicloFiscalizacion, EstadoCicloFiscalizacionEnum" + " estadoCicloFiscalizacion, Long fechaInicio, Long fechaFin)");
        return cicloFiscalizacionModeloDTOs;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#verificarCicloFiscalizacionAportante(com.asopagos.dto.cartera.SimulacionDTO)
     */
    @Override
    public Boolean verificarCicloFiscalizacionAportante(SimulacionDTO simulacionDTO) {
        logger.debug("Inicia metodo verificarCicloFiscalizacionAportante(SimulacionDTO simulacionDTO)");
        try {
            // Se consulta si hay ciclos de fiscalizacion activos para el aportante true= Si encuentra el aportante relacionado con el ciclo de fiscalización actual
            // false = Si el aportante no se encuentra relacionado para el ciclo de fiscalización actual
            Integer verificar = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION_APORTANTE).setParameter("tipoIdentificacion", simulacionDTO.getTipoIdentificacion()).setParameter("numeroIdentificacion", simulacionDTO.getNumeroIdentificacion()).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, TipoCicloEnum.FISCALIZACION).getSingleResult();
            logger.debug("Finaliza metodo verificarCicloFiscalizacionAportante(SimulacionDTO simulacionDTO)");
            return verificar == 1;
        } catch (Exception e) {
            logger.error("Finaliza por error verificarCicloFiscalizacionAportante(SimulacionDTO simulacionDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#verificarCicloFiscalizacionNoActivo(com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum)
     */
    @Override
    public Boolean verificarCicloFiscalizacionNoActivo() {
        logger.debug("Inicia metodo verificarCicloFiscalizacionNoActivo(EstadoCicloFiscalizacionEnum cicloFiscalizacionEnum)");
        boolean verificar = Boolean.FALSE;
        try {
            /* Se consulta si hay ciclos de fiscalizacion activos */
            CicloCartera ciclo = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION, CicloCartera.class).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, TipoCicloEnum.FISCALIZACION).getSingleResult();
            /* Si hay un ciclo activo se retorna false */
            if (ciclo != null) {
                return verificar;
            }

        } catch (NonUniqueResultException nure) {
            logger.error("Ocurrió un error consultando el ciclo, mas de un ciclo en curso", nure);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nure);
        } catch (NoResultException nre) {
            logger.debug("No hay ciclos en curso");
            verificar = Boolean.TRUE;
            return verificar;
        } catch (Exception e) {
            logger.error("Finaliza por error verificarCicloFiscalizacionNoActivo(EstadoCicloFiscalizacionEnum cicloFiscalizacionEnum)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug("Finaliza metodo verificarCicloFiscalizacionNoActivo(EstadoCicloFiscalizacionEnum cicloFiscalizacionEnum)");
        return verificar;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#cancelarCiclo(long, java.lang.Long)
     */
    @Override
    public void cancelarCiclo(CicloCarteraModeloDTO cicloFiscalizacionModeloDTO) {
        logger.debug("Inicia metodo cancelarCiclo(CicloFiscalizacionModeloDTO cicloFiscalizacionModeloDTO)");
        try {
            /*
             * Se cancela el ciclo de fiscalizacion, actualizando su estado y la fecha fin por la
             * fecha de cancelacion
             */
            CicloCartera cicloFiscalizacion = cicloFiscalizacionModeloDTO.convertToEntity();
            entityManager.merge(cicloFiscalizacion);

        } catch (Exception e) {
            logger.error("Finaliza por error cancelarCiclo(CicloFiscalizacionModeloDTO cicloFiscalizacionModeloDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug("Finaliza metodo cancelarCiclo(CicloFiscalizacionModeloDTO cicloFiscalizacionModeloDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetalleCiclo(java.lang.Long, java.lang .boolean,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarDetalleCiclo(Long idCiclo, boolean esSupervisor, boolean gestionManual, UserDTO userDTO) {
        try {
            logger.debug("Inicia consultarDetalleCiclo(Long idCiclo,boolean esSupervisor)");
            List<SimulacionDTO> solicitudFiscalizacion = null;
            if (gestionManual) {
                solicitudFiscalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_GESTION_MANUAL_CON_DETALLE).setParameter(ID_CICLO_CARTERA, idCiclo).setParameter(USUARIO_DESTINATARIO, !esSupervisor ? userDTO.getNombreUsuario() : null).getResultList();
            } else {
                if (esSupervisor) {
                    solicitudFiscalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION_CON_DETALLE).setParameter(ID_CICLO_CARTERA, idCiclo).getResultList();
                } else {
                    if (userDTO.getNombreUsuario() != null && !userDTO.getNombreUsuario().equals("")) {
                        solicitudFiscalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION_CON_DETALLE_USUARIO).setParameter(ID_CICLO_CARTERA, idCiclo).setParameter(USUARIO_DESTINATARIO, userDTO.getNombreUsuario()).getResultList();
                    } else {
                        logger.debug("Finaliza con error consultarDetalleCiclo(Long idCiclo,boolean esSupervisor): sin usuario a consultar");
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                    }
                }
            }
            if (solicitudFiscalizacion == null || solicitudFiscalizacion.isEmpty()) {
                solicitudFiscalizacion = null;
            }
            logger.debug("Finaliza consultarDetalleCiclo(Long idCiclo,boolean esSupervisor)");

            return solicitudFiscalizacion;
        } catch (Exception e) {
            logger.debug("Ocurrió un error inesperado en consultarDetalleCiclo(Long idCiclo,boolean esSupervisor)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#obtenerUltimoBackAsignado(com.asopagos.enumeraciones.aportes.
     * TipoSolicitanteMovimientoAporteEnum, com.asopagos.enumeraciones.core.TipoTransaccionEnum)
     */
    @Override
    public String obtenerUltimoBackAsignado(TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoTransaccionEnum tipoTransaccion) {
        try {
            logger.debug("Inicio de método obtenerUltimoBackAsignado");
            String ultimoUsuario = null;

            if (TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA.equals(tipoTransaccion)) {
                ultimoUsuario = (String) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_PREVENTIVA_CONSULTAR_BACK_ACTUALIZACION_POR_TIPO_SOLICITANTE).setParameter("tipoSolicitante", tipoSolicitante.name()).getSingleResult();
            }else if(ProcesoEnum.GESTION_COBRO_ELECTRONICO.equals(tipoTransaccion.getProceso())) {
                ultimoUsuario = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_USUARIO_ELECTRONICO).getSingleResult();
            }
            
            else {
                ultimoUsuario = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMO_BACK_ACTUALIZACION_ELECTRONICO).setParameter("tipoSolicitante", tipoSolicitante.name()).getSingleResult();

            }

            logger.debug("Fin de método obtenerUltimoBackAsignado");
            return ultimoUsuario;
        } catch (NoResultException nre) {
            logger.debug("Fin de método obtenerUltimoBackAsignado, no se encontró ningún usuario");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en obtenerUltimoBackAsignado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultar(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SimulacionDTO consultarDetalleCicloAportante(String numeroRadicado) {
        try {
            logger.debug("Inicia consultarDetalleCicloAportante(String numeroRadicado)");
            SimulacionDTO simulacionDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CICLO_FISCALIZACION_APORTANTE, SimulacionDTO.class).setParameter(NUMERO_RADICACION, numeroRadicado).getSingleResult();
            if (simulacionDTO != null) {
                return simulacionDTO;
            }
            logger.debug("Finaliza consultarDetalleCicloAportante(String numeroRadicado)");
            return null;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarDetalleCicloAportante(String numeroRadicado)");
            return null;
        } catch (Exception e) {
            logger.debug("Ocurrió un error inesperado en consultarDetalleCicloAportante(String numeroRadicado)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#modificarEstadoUltimoCicloSolicitud()
     */
    @Override
    public void verificarCierreCiclos(TipoCicloEnum tipoCiclo, Long idCicloAportante) {
        try {
            logger.debug("Inicio de servicio modificarEstadoUltimoCicloSolicitud()");
            List<EstadoFiscalizacionEnum> estadosFiscalizacion = new ArrayList<>();
            estadosFiscalizacion.add(EstadoFiscalizacionEnum.EXCLUIDA);
            estadosFiscalizacion.add(EstadoFiscalizacionEnum.FINALIZADA);
            List<CicloCartera> ciclos = new ArrayList<>();
            switch (tipoCiclo) {
                case FISCALIZACION:
                    ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_FISCALIZACION_POR_ESTADOS, CicloCartera.class).setParameter("estadosFiscalizacion", estadosFiscalizacion).setParameter("estadoCicloFiscaliacion", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, tipoCiclo).setMaxResults(1).getResultList();
                    break;
                case GESTION_MANUAL:
                    ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_MANUAL_POR_ESTADOS, CicloCartera.class).setParameter("estadosFiscalizacion", estadosFiscalizacion).setParameter("estadoCicloFiscaliacion", EstadoCicloCarteraEnum.ACTIVO).setParameter("idCicloAportante", idCicloAportante).setParameter(TIPO_CICLO, tipoCiclo).setMaxResults(1).getResultList();
                    break;
                default:
                    break;
            }
            if (ciclos.isEmpty()) {
                switch (tipoCiclo) {
                    case FISCALIZACION:
                        /* Se consulta el ciclo de fiscalizacion actual para poder cancelarlo */
                        ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_FISCALIZACION, CicloCartera.class).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, tipoCiclo).getResultList();
                        break;
                    case GESTION_MANUAL:
                        ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_GESTION_COBRO_MANUAL, CicloCartera.class).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter(TIPO_CICLO, tipoCiclo).setParameter("idCicloAportante", idCicloAportante).getResultList();
                        break;
                    default:
                        break;
                }

                for (CicloCartera cicloCartera : ciclos) {
                    cicloCartera.setEstadoCicloCartera(EstadoCicloCarteraEnum.FINALIZADO);
                    entityManager.merge(cicloCartera);
                }
            }
            logger.debug("Finaliza de servicio modificarEstadoUltimoCicloSolicitud()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en modificarEstadoUltimoCicloSolicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudesCiclo(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudModeloDTO> consultarSolicitudesCiclo(Long idCicloVencido, List<EstadoFiscalizacionEnum> estado, TipoCicloEnum tipoCiclo) {
        logger.debug("Inicio de servicio consultarSolicitudesCiclo(Long idCicloAportante)");
        List<SolicitudModeloDTO> lstSolicitudesModelo = new ArrayList<>();
        List<Solicitud> lstSolicitudes = new ArrayList<>();
        try {
            switch (tipoCiclo) {
                case FISCALIZACION:
                    /* Se obtienen las solicitudes con la informacion de id instancia y el usuario destino */
                    lstSolicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_CONSULTAR_IDISNTANCIA_USUARIODESTINO_POR_IDCICLOAPORTANTE, Solicitud.class).setParameter(ID_CICLO_CARTERA, idCicloVencido).setParameter("estado", estado).getResultList();
                    break;

                case GESTION_MANUAL:
                    lstSolicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_CONSULTAR_IDISNTANCIA_USUARIODESTINO_POR_IDCICLOAPORTANTEMANUAL, Solicitud.class).setParameter(ID_CICLO_CARTERA, idCicloVencido).setParameter("estado", estado).getResultList();
                    break;

                default:
                    break;
            }

            /* Se convierte de entidad a DTO */
            for (Solicitud solicitud : lstSolicitudes) {
                SolicitudModeloDTO solicitudModeloDTO = new SolicitudModeloDTO();
                solicitudModeloDTO.convertToDTO(solicitud);
                lstSolicitudesModelo.add(solicitudModeloDTO);
            }
            logger.debug("Finaliza de servicio consultarSolicitudesCiclo(Long idCicloAportante)");
            return lstSolicitudesModelo;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarSolicitudesCiclo(Long idCicloAportante)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarParametrizacionConveniosPago(com.asopagos.dto.modelo.ParametrizacionConveniosPagoModeloDTO)
     */
    @Override
    public void guardarParametrizacionConveniosPago(ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO) {
        logger.debug("Inicia guardarParametrizacionConveniosPago(ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO)");
        try {
            ParametrizacionConveniosPagoModeloDTO parametrizacionConveniosPagoModeloDTOBD = consultarParametrizacionConveniosPago();

            ParametrizacionConveniosPago parametrizacionConveniosPago = parametrizacionConvenioPagoDTO.convertToEntity();
            if (parametrizacionConveniosPagoModeloDTOBD == null) {
                entityManager.persist(parametrizacionConveniosPago);
            } else {
                parametrizacionConveniosPago.setIdParametrizacionConveniosPago(parametrizacionConveniosPagoModeloDTOBD.getIdParametrizacionConveniosPago());
                entityManager.merge(parametrizacionConveniosPago);
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarParametrizacionFiscalizacionCartera(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionConveniosPago()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionConveniosPagoModeloDTO consultarParametrizacionConveniosPago() {
        logger.debug("Inicia método consultarParametrizacionConveniosPago()");
        try {
            ParametrizacionConveniosPago parametrizacionConvenioPago = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_CONVENIO_PAGO, ParametrizacionConveniosPago.class).getSingleResult();
            logger.debug("Finaliza método consultarParametrizacionConveniosPago()");
            ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO = new ParametrizacionConveniosPagoModeloDTO();
            parametrizacionConvenioPagoDTO.convertToDTO(parametrizacionConvenioPago);
            return parametrizacionConvenioPagoDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la Parametrizacion de Convenios de Pago: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarParametrizacionConveniosPago: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarParametrizacionExclusiones(com.asopagos.dto.modelo.ParametrizacionExclusionesModeloDTO)
     */
    @Override
    public void guardarParametrizacionExclusiones(ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO) {
        logger.debug("Inicia guardarParametrizacionExclusiones(ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO)");
        try {
            ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTOBD = consultarParametrizacionExclusiones();

            ParametrizacionExclusiones parametrizacionExclusiones = parametrizacionExclusionesModeloDTO.convertToEntity();
            if (parametrizacionExclusionesModeloDTOBD == null) {
                entityManager.persist(parametrizacionExclusiones);
            } else {
                parametrizacionExclusiones.setIdParametrizacionExclusiones(parametrizacionExclusionesModeloDTOBD.getIdParametrizacionExclusiones());
                entityManager.merge(parametrizacionExclusiones);
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarParametrizacionExclusiones(ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionConveniosPago()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionExclusionesModeloDTO consultarParametrizacionExclusiones() {
        logger.debug("Inicia método consultarParametrizacionExclusiones()");
        try {
            ParametrizacionExclusiones parametrizacionExclusiones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_EXCLUSIONES, ParametrizacionExclusiones.class).getSingleResult();
            logger.debug("Finaliza método consultarParametrizacionExclusiones()");
            ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO = new ParametrizacionExclusionesModeloDTO();
            parametrizacionExclusionesModeloDTO.convertToDTO(parametrizacionExclusiones);
            return parametrizacionExclusionesModeloDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la Parametrizacion de exclusiones: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarParametrizacionExclusiones: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarParametrizacionDesafiliacion(com.asopagos.dto.modelo.ParametrizacionDesafiliacionModeloDTO)
     */
    @Override
    public void guardarParametrizacionDesafiliacion(ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO) {
        logger.debug("Inicia guardarParametrizacionDesafiliacion(ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO)");
        try {
            ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTOBD = consultarParametrizacionDesafiliacion(parametrizacionDesafiliacionModeloDTO.getLineaCobro());
            if (parametrizacionDesafiliacionModeloDTOBD == null) {
                logger.debug("Entra crear::::");
                entityManager.persist(parametrizacionDesafiliacionModeloDTO.convertToEntity());
            } else {
                logger.debug("Entra actualizar::::");
                entityManager.merge(mapperParametrizacionDesafiliacion(parametrizacionDesafiliacionModeloDTO, parametrizacionDesafiliacionModeloDTOBD.getIdParametrizacionDesafiliacion()));
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarParametrizacionDesafiliacion(ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public ParametrizacionDesafiliacion mapperParametrizacionDesafiliacion(ParametrizacionDesafiliacionModeloDTO desafiliacionModeloDTO, Long id) {
        ParametrizacionDesafiliacion parametrizacionDesafiliacion = new ParametrizacionDesafiliacion();
        parametrizacionDesafiliacion.setIdParametrizacionDesafiliacion(id);
        parametrizacionDesafiliacion.setProgramacionEjecucion(desafiliacionModeloDTO.getProgramacionEjecucion() != null ? desafiliacionModeloDTO.getProgramacionEjecucion() : null);
        parametrizacionDesafiliacion.setPeriodosMora(desafiliacionModeloDTO.getPeriodosMora() != null ? desafiliacionModeloDTO.getPeriodosMora() : null);
        parametrizacionDesafiliacion.setMetodoEnvioComunicado(desafiliacionModeloDTO.getMetodoEnvioComunicado() != null ? desafiliacionModeloDTO.getMetodoEnvioComunicado() : null);
        parametrizacionDesafiliacion.setCorrespondenciaFisico(desafiliacionModeloDTO.getCorrespondenciaFisico() != null ? desafiliacionModeloDTO.getCorrespondenciaFisico() : null);
        parametrizacionDesafiliacion.setNotificacionJudicialFisico(desafiliacionModeloDTO.getNotificacionJudicialFisico() != null ? desafiliacionModeloDTO.getNotificacionJudicialFisico() : null);
        parametrizacionDesafiliacion.setOficinaPrincipalFisico(desafiliacionModeloDTO.getOficinaPrincipalFisico() != null ? desafiliacionModeloDTO.getOficinaPrincipalFisico() : null);
        parametrizacionDesafiliacion.setOficinaPrincipalElectronico(desafiliacionModeloDTO.getOficinaPrincipalElectronico() != null ? desafiliacionModeloDTO.getOficinaPrincipalElectronico() : null);
        parametrizacionDesafiliacion.setRepresentanteLegalElectronico(desafiliacionModeloDTO.getRepresentanteLegalElectronico() != null ? desafiliacionModeloDTO.getRepresentanteLegalElectronico() : null);
        parametrizacionDesafiliacion.setResponsableAportesElectronico(desafiliacionModeloDTO.getResponsableAportesElectronico() != null ? desafiliacionModeloDTO.getResponsableAportesElectronico() : null);
        parametrizacionDesafiliacion.setHabilitado(Boolean.TRUE);
        parametrizacionDesafiliacion.setLineaCobro(desafiliacionModeloDTO.getLineaCobro());
        parametrizacionDesafiliacion.setDesafiAutomatica(desafiliacionModeloDTO.getDesafiAutomatica());
        parametrizacionDesafiliacion.setNumDiasDesafiliacion(desafiliacionModeloDTO.getNumDiasDesafiliacion());
        //parametrizacionDesafiliacion.setHabilitado(desafiliacionModeloDTO.getHabilitado() != null ? desafiliacionModeloDTO.getHabilitado() : true);
        parametrizacionDesafiliacion.setSiguienteAccion(desafiliacionModeloDTO.getSiguienteAccion() != null ? desafiliacionModeloDTO.getSiguienteAccion() : null);
        return parametrizacionDesafiliacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionDesafiliacion()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionDesafiliacionModeloDTO consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro) {
        logger.debug("Inicia método consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro)");
        try {
            ParametrizacionDesafiliacion parametrizacionDesafiliacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_DESAFILIACION, ParametrizacionDesafiliacion.class).setParameter("lineaCobro", lineaCobro).getSingleResult();
            logger.debug("Finaliza método consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro)");
            ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO = new ParametrizacionDesafiliacionModeloDTO();
            parametrizacionDesafiliacionModeloDTO.convertToDTO(parametrizacionDesafiliacion);
            return parametrizacionDesafiliacionModeloDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la Parametrizacion de desafiliacion: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarParametrizacionDesafiliacion: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#crearConvenioPago(com.asopagos.dto.modelo.ConvenioPagoModeloDTO)
     */
    @Override
    public void crearConvenioPago(ConvenioPagoModeloDTO convenioPagoDTO, UserDTO userDTO) {
        logger.debug("Inicio de método crearConvenioPago(ConvenioPagoModeloDTO)");
        try {

            convenioPagoDTO.setEstadoConvenioPago(EstadoConvenioPagoEnum.ACTIVO);
            convenioPagoDTO.setUsuarioCreacion(userDTO.getNombreUsuario());
            ConvenioPago convenioPago = convenioPagoDTO.convertToEntity();
            if (convenioPago.getIdConvenioPago() != null) {
                entityManager.merge(convenioPago);
            } else {
                entityManager.persist(convenioPago);
            }

            for (PagoPeriodoConvenioModeloDTO pagoPeriodoConvenioModeloDTO : convenioPagoDTO.getPagoPeriodos()) {
                PagoPeriodoConvenio pagoPeriodoConvenio = pagoPeriodoConvenioModeloDTO.convertToEntity();
                pagoPeriodoConvenio.setIdConvenioPago(convenioPago.getIdConvenioPago());
                if (pagoPeriodoConvenio.getIdPagoConvenio() != null) {
                    entityManager.merge(pagoPeriodoConvenio);
                } else {
                    entityManager.persist(pagoPeriodoConvenio);
                }
                /* Solo aplica para empleador */
                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(convenioPagoDTO.getTipoSolicitante())) {
                    /* Se consultan los cotizantes del aportante */
                    List<CarteraDependienteModeloDTO> carteraDependienteModeloDTOs = consultarPeriodosMorososCotizantes(convenioPagoDTO.getTipoIdentificacion(), convenioPagoDTO.getNumeroIdentificacion(), pagoPeriodoConvenioModeloDTO.getPeriodo());

                    for (CarteraDependienteModeloDTO carteraDependienteModeloDTO : carteraDependienteModeloDTOs) {
                        ConvenioPagoDependienteModeloDTO convenioPagoDependientesModeloDTO = new ConvenioPagoDependienteModeloDTO();
                        /* Se setean los datos al DTO */
                        convenioPagoDependientesModeloDTO.setIdPagoPeriodoConvenio(pagoPeriodoConvenio.getIdPagoConvenio());
                        convenioPagoDependientesModeloDTO.setIdPersona(carteraDependienteModeloDTO.getIdPersona());
                        /* Se guarda ConvenioPagoDependientes */
                        guardarConvenioPagoDependientes(convenioPagoDependientesModeloDTO);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarCicloFiscalizacion: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarConveniosPago(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioPagoModeloDTO> consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug("Inicio de servicio consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) ");
        try {
            List<ConvenioPago> conveniosPago = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_PAGO_DTO, ConvenioPago.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).getResultList();

            List<Long> idsConveniosPago = new ArrayList<>();
            for (ConvenioPago convenioPago : conveniosPago) {
                idsConveniosPago.add(convenioPago.getIdConvenioPago());
            }
            List<ConvenioPagoModeloDTO> conveniosPagoModeloDTO = new ArrayList<>();
            if (!idsConveniosPago.isEmpty()) {
                List<PagoPeriodoConvenio> pagosPeriodoConvenio = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGO_PERIODO_CONVENIO_DTO, PagoPeriodoConvenio.class)
                        .setParameter("idsConveniosPago", idsConveniosPago).getResultList();

                for (ConvenioPago convenioPago : conveniosPago) {
                    List<PagoPeriodoConvenioModeloDTO> pagosPeriodoConvenioModeloDTO = new ArrayList<>();
                    for (PagoPeriodoConvenio pagoPeriodoConvenio : pagosPeriodoConvenio) {
                        if (convenioPago.getIdConvenioPago().equals(pagoPeriodoConvenio.getIdConvenioPago())) {
                            PagoPeriodoConvenioModeloDTO pagoPeriodoConvenioModeloDTO = new PagoPeriodoConvenioModeloDTO();
                            pagoPeriodoConvenioModeloDTO.convertToDTO(pagoPeriodoConvenio);
                            pagosPeriodoConvenioModeloDTO.add(pagoPeriodoConvenioModeloDTO);
                        }
                    }
                    ConvenioPagoModeloDTO convenioPagoModeloDTOTem = new ConvenioPagoModeloDTO();
                    convenioPagoModeloDTOTem.convertToDTO(convenioPago);
                    convenioPagoModeloDTOTem.setPagoPeriodos(pagosPeriodoConvenioModeloDTO);
                    conveniosPagoModeloDTO.add(convenioPagoModeloDTOTem);
                }
            }
            logger.debug("Finaliza de servicio consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) ");
            return conveniosPagoModeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarConveniosPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarGuardarExclusionCartera(com.asopagos.dto.cartera.ExclusionCarteraDTO)
     */
    @Override
    public ExclusionCarteraDTO actualizarGuardarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO) {
        logger.debug("Inicia actualizarGuardarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO)");
        ExclusionCartera exclusionCartera = null;
        PeriodoExclusionMora periodoExclusionMora = null;
        Long idExclusionCartera = null;
        if (exclusionCarteraDTO.getIdExclusionCartera() != null) {
            try {
                exclusionCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSION_CARTERA_POR_ID, ExclusionCartera.class).setParameter("idExclusionCartera", exclusionCarteraDTO.getIdExclusionCartera()).getSingleResult();
                idExclusionCartera = exclusionCartera.getIdExclusionCartera();

            } catch (NoResultException ex) {
                exclusionCartera = null;
            }
        }
        try {
            if (exclusionCartera != null && !(EstadoExclusionCarteraEnum.NO_ACTIVA.equals(exclusionCartera.getEstadoExclusionCartera()) && EstadoExclusionCarteraEnum.ACTIVA.equals(exclusionCarteraDTO.getEstadoExclusionCartera()))) {
                exclusionCartera = exclusionCarteraDTO.convertToExclusionEntityToDto(exclusionCartera, exclusionCarteraDTO);
                entityManager.merge(exclusionCartera);
            } else {
                exclusionCartera = exclusionCarteraDTO.convertToExclusionCarteraEntity();
                exclusionCartera.setFechaRegistro(new java.util.Date());
                entityManager.persist(exclusionCartera);
                idExclusionCartera = exclusionCartera.getIdExclusionCartera();
            }
            if (TipoExclusionCarteraEnum.ACLARACION_MORA.equals(exclusionCarteraDTO.getTipoExclusionCartera())) {
                for (PeriodoExclusionMoraDTO periodoExclusionMoraDTO : exclusionCarteraDTO.getPeriodosExclusionMora()) {
                    periodoExclusionMora = new PeriodoExclusionMora();
                    periodoExclusionMora.setFechaPeriodo(new Date(periodoExclusionMoraDTO.getFechaPeriodo()));
                    periodoExclusionMora.setIdExclusionCartera(idExclusionCartera);
                    periodoExclusionMora.setEstadoPeriodo(periodoExclusionMoraDTO.getEstadoPeriodo());
                    if (periodoExclusionMoraDTO.getIdPeriodoExclusionMora() != null) {
                        periodoExclusionMora.setIdPeriodoExclusionMora(periodoExclusionMoraDTO.getIdPeriodoExclusionMora());
                        entityManager.merge(periodoExclusionMora);
                    } else {
                        entityManager.persist(periodoExclusionMora);
                    }
                }
            }
            exclusionCarteraDTO = exclusionCarteraDTO.convertEntityToDTO(exclusionCartera);
            logger.debug("Finaliza actualizarGuardarExclusionCartera(ExclusionCarteraDTO exclusionCarteraDTO)");
            return exclusionCarteraDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en actualizarGuardarExclusionCartera(Long idCicloAportante)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarExclusionPorAportante(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ExclusionCarteraDTO> consultarExclusionPorAportante(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug("Inicio de método consultarExclusionPorAportante(Long idPersona)");
        try {

            List<ExclusionCarteraDTO> lstExclusionesDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSION_CARTERA_APORTANTE).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).getResultList();
            //Se consulta el convenio de pago
            ExclusionCarteraDTO exclusionConvenioPago = buscarConvenioPago(tipoIdentificacion, numeroIdentificacion, tipoSolicitante);
            if (exclusionConvenioPago != null) {
                lstExclusionesDTO.add(exclusionConvenioPago);
            }
            if (lstExclusionesDTO.isEmpty()) {
                lstExclusionesDTO = null;
            } else {
                for (ExclusionCarteraDTO exclusionCarteraDTO : lstExclusionesDTO) {
                    if (TipoExclusionCarteraEnum.ACLARACION_MORA.equals(exclusionCarteraDTO.getTipoExclusionCartera())) {
                        List<PeriodoExclusionMoraDTO> periodosExclusionMora = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODOS_EXCLUSION_CARTERA_MORA).setParameter("idPersona", exclusionCarteraDTO.getIdPersona()).setParameter("tipoExclusionCartera", TipoExclusionCarteraEnum.ACLARACION_MORA).getResultList();
                        exclusionCarteraDTO.setPeriodosExclusionMora(periodosExclusionMora);
                    }
                }
            }
            logger.debug("Finaliza de método consultarExclusionPorAportante(Long idPersona)");
            return lstExclusionesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarExclusionPorAportante(Long idPersona)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#buscarTrazabilidadExclusionCartera(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<ExclusionCarteraDTO> buscarTrazabilidadExclusionCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        try {
            logger.debug("Inicia servicio buscarTrazabilidadExclusionCartera(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
            List<ExclusionCarteraDTO> lstExclusionesDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRAZABILIDAD_EXCLUSION_CARTERA).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).getResultList();
            ExclusionCarteraDTO exclusionConvenioPago = buscarConvenioPago(tipoIdentificacion, numeroIdentificacion, tipoSolicitante);
            if (exclusionConvenioPago != null) {
                lstExclusionesDTO.add(exclusionConvenioPago);
            }
            logger.debug("Finaliza de servicio buscarTrazabilidadExclusionCartera(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
            return lstExclusionesDTO.isEmpty() ? null : lstExclusionesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio buscarTrazabilidadExclusionCartera(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarGestionLineaCobro(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> consultarGestionLineaCobro(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "consultarGestionLineaCobro(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<Long> lstGestionLineaCobro = new ArrayList<>();
        try {
            List<Object> lstObject = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_CARTERA_POR_CADA_LINEA_DE_COBRO_POR_APORTANTE).setParameter("tipoIdentificacion", tipoIdentificacion.toString()).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante.toString()).getResultList();
            for (Object object : lstObject) {
                BigInteger idCartera = (BigInteger) object;
                lstGestionLineaCobro.add(idCartera.longValue());
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return lstGestionLineaCobro;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#anularConvenioPago(java.lang.Long)
     */
    @Override
    public void anularConvenioPago(Long numeroConvenio, UserDTO userDTO) {
        logger.debug("Inicia servicio anularConvenioPago(Long numeroConvenio");
        try {
            ConvenioPago convenioPago = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_PAGO_POR_ID, ConvenioPago.class).setParameter("numeroConvenio", numeroConvenio).getSingleResult();
            convenioPago.setEstadoConvenioPago(EstadoConvenioPagoEnum.ANULADO);
            convenioPago.setMotivoAnulacion(MotivoAnulacionEnum.ANULADO_MANUALMENTE);
            convenioPago.setFechaAnulacion(new Date());
            convenioPago.setUsuarioAnulacion(userDTO.getNombreUsuario());
            entityManager.merge(convenioPago);
            logger.debug("Finaliza de servicio anularConvenioPago(Long numeroConvenio");
        } catch (Exception e) {
            logger.error("Ocurrió un error anulando un convenio de pago", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#calcularDeudaPresunta(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public List<CarteraModeloDTO> consultarPeriodosMorosos(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug("Inicia consultarPeriodosMorosos(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
        try {
            List<CarteraModeloDTO> lstCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA, CarteraModeloDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).getResultList();

            for (CarteraModeloDTO carteraModeloDTO : lstCartera) {
                Object deudaReal = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_DEUDA_REAL).setParameter("idCartera", carteraModeloDTO.getIdCartera()).getSingleResult();

                carteraModeloDTO.setDeudaReal(BigDecimal.valueOf(Double.valueOf(String.valueOf(deudaReal))));

                Object numeroOperacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_OPERACION_CARTERA).setParameter("idCartera", carteraModeloDTO.getIdCartera()).getSingleResult();


                List<ActividadCarteraModeloDTO> actividades = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDADES_CARTERA, ActividadCarteraModeloDTO.class).setParameter("numeroOperacion", numeroOperacion != null ? Long.parseLong(numeroOperacion.toString()) : 0).getResultList();
                if (actividades.size() > 0) {
                    carteraModeloDTO.setClasificacion(asignacionClasifiacionSet(actividades));
                }
            }
            logger.debug("Finaliza consultarPeriodosMorosos(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
            return lstCartera;
        } catch (Exception e) {
            logger.debug("Finaliza consultarPeriodosMorosos(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
            logger.error("Se presento un error durante la consulta ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public String asignacionClasifiacionSet(List<ActividadCarteraModeloDTO> actividades) {

        String actValidacion = "";
        for (ActividadCarteraModeloDTO act : actividades) {

            logger.info("** -- act.getActividadFiscalizacion().name() :: " + act.getActividadFiscalizacion().name());
            logger.info(actividades.size());
            if (actividades.size() == 1) {
                switch (act.getActividadFiscalizacion().name()) {
                    case "ACTIVACION_REESTRUCTURACION":
                        return "Reestructuración";

                    case "ACIVACION_LIQUIDACION":
                        return "Liquidación";

                    case "ENVIO_JURIDICO":
                        return "Jurídico";

                    default:
                        return "";

                }
            } else {
                switch (act.getActividadFiscalizacion().name()) {
                    case "ACTIVACION_REESTRUCTURACION":
                        actValidacion += "AR";

                    case "ACIVACION_LIQUIDACION":
                        actValidacion += "AL";


                    case "ENVIO_JURIDICO":
                        actValidacion += "AJ";

                    default:
                        return "";

                }

            }

        }
        if (actValidacion.contains("AR") && actValidacion.contains("AJ")) {
            return "Reestructuración jurídico";
        } else if (actValidacion.contains("AL") && actValidacion.contains("AJ")) {
            return "Liquidación jurídico";
        }
        return "";
    }

    @Override
    public boolean consultaParametrizacionAnexoLiquidacion(TipoAccionCobroEnum accionCobro) {
        Boolean parametrizacionAnexoBandera = false;
        logger.debug("Inicia consulta anexo liquidacion metodo");
        logger.info("Inicia consulta anexo liquidacion metodo");
        if (accionCobro.name().equals("D2")) {
            parametrizacionAnexoBandera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2D, Boolean.class).getSingleResult();
            logger.debug("query: " + parametrizacionAnexoBandera);
            logger.info("query: " + parametrizacionAnexoBandera);
            logger.debug("Finaliza consulta anexo liquidacion");
            logger.info("Finaliza consulta anexo liquidacion");
        } else if (accionCobro.name().equals("2E")) {
            parametrizacionAnexoBandera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2E, Boolean.class).getSingleResult();
        } else if (accionCobro.name().equals("C2")) {
            Object anexo = (Object) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2C).getSingleResult();
            logger.info("anexo: " + anexo);
            if (anexo != null && anexo.toString().equals("true")) {
                logger.info("entra if");
                parametrizacionAnexoBandera = true;
            } else if (anexo != null && anexo.toString().equals("false")) {
                parametrizacionAnexoBandera = false;
            }
        }
        logger.debug("Finaliza consulta anexo liquidacion metodo");
        logger.info("Finaliza consulta anexo liquidacion metodo");
        return parametrizacionAnexoBandera;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionGestionCobro(com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object> consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        logger.info("Inicia metodo consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion)");
        /* Se almacena el resultado de la consulta sobre el objeto ParametrizacionGestionCobroModeloDTO */
        List<ParametrizacionGestionCobro> parametrizacionesGestionCobro = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_GESTION_COBRO_TIPO_PARAMETRIZACION, ParametrizacionGestionCobro.class).setParameter("tipoParametrizacion", tipoParametrizacion).getResultList();
        logger.info("Finaliza metodo consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion)");

        switch (tipoParametrizacion) {
            case LINEA_COBRO:
                logger.info("LINEA_COBRO");
                List<Object> lineasCobroModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    LineaCobro lineaCobro = (LineaCobro) parametrizacionGestionCobro;
                    LineaCobroModeloDTO lineaCobroModeloDTO = new LineaCobroModeloDTO();
                    lineaCobroModeloDTO.convertToDTO(lineaCobro);
                    lineasCobroModeloDTO.add(lineaCobroModeloDTO);
                }
                return lineasCobroModeloDTO;
            case ACCION_COBRO_A:
                logger.info("ACCION_COBRO_A");
                List<Object> accionesCobroAModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobroA accionCobroA = (AccionCobroA) parametrizacionGestionCobro;
                    AccionCobroAModeloDTO accionCobroAModeloDTO = new AccionCobroAModeloDTO();
                    accionCobroAModeloDTO.convertToDTO(accionCobroA);
                    accionesCobroAModeloDTO.add(accionCobroAModeloDTO);
                }
                return accionesCobroAModeloDTO;
            case ACCION_COBRO_B:
                logger.info("ACCION_COBRO_B");
                List<Object> accionesCobroBModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobroB accionCobroB = (AccionCobroB) parametrizacionGestionCobro;
                    AccionCobroBModeloDTO accionCobroBModeloDTO = new AccionCobroBModeloDTO();
                    accionCobroBModeloDTO.convertToDTO(accionCobroB);
                    accionesCobroBModeloDTO.add(accionCobroBModeloDTO);
                }
                return accionesCobroBModeloDTO;
            case ACCION_COBRO_1C:
                logger.info("ACCION_COBRO_1C");
                List<Object> accionesCobro1CModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro1C accionCobro1C = (AccionCobro1C) parametrizacionGestionCobro;
                    AccionCobro1CModeloDTO accionCobro1CModeloDTO = new AccionCobro1CModeloDTO();
                    accionCobro1CModeloDTO.convertToDTO(accionCobro1C);
                    accionesCobro1CModeloDTO.add(accionCobro1CModeloDTO);
                }
                return accionesCobro1CModeloDTO;
            case ACCION_COBRO_1D:
                logger.info("ACCION_COBRO_1D");
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Object accionCobro1D = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACCION_COBRO_1D)
                            .getSingleResult();
                    logger.info("Object 1D :: " + mapper.writeValueAsString(accionCobro1D));
                    AccionCobro1D entity = mapper.convertValue(accionCobro1D, AccionCobro1D.class);
                    if (Objects.nonNull(accionCobro1D)) {
                        List<Object> accionesCobro1DModeloDTO = new ArrayList<>();
                        AccionCobro1DModeloDTO dto = new AccionCobro1DModeloDTO();
                        accionesCobro1DModeloDTO.add(dto.convertToDTO(entity));
                        return accionesCobro1DModeloDTO;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }

            case ACCION_COBRO_1E:
                logger.info("ACCION_COBRO_1E");
                List<Object> accionesCobro1EModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro1E accionCobro1E = (AccionCobro1E) parametrizacionGestionCobro;
                    AccionCobro1EModeloDTO accionCobro1EModeloDTO = new AccionCobro1EModeloDTO();
                    accionCobro1EModeloDTO.convertToDTO(accionCobro1E);
                    accionesCobro1EModeloDTO.add(accionCobro1EModeloDTO);
                }
                return accionesCobro1EModeloDTO;
            case ACCION_COBRO_1F:
                logger.info("ACCION_COBRO_1F");
                List<Object> accionesCobro1FModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro1F accionCobro1F = (AccionCobro1F) parametrizacionGestionCobro;
                    AccionCobro1FModeloDTO accionCobro1FModeloDTO = new AccionCobro1FModeloDTO();
                    accionCobro1FModeloDTO.convertToDTO(accionCobro1F);
                    accionesCobro1FModeloDTO.add(accionCobro1FModeloDTO);
                }
                return accionesCobro1FModeloDTO;
            case ACCION_COBRO_2C:
                logger.info("ACCION_COBRO_2C");
                List<Object> accionesCobro2CModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro2C accionCobro2C = (AccionCobro2C) parametrizacionGestionCobro;
                    AccionCobro2CModeloDTO accionCobro2CModeloDTO = new AccionCobro2CModeloDTO();
                    accionCobro2CModeloDTO.convertToDTO(accionCobro2C);
                    accionesCobro2CModeloDTO.add(accionCobro2CModeloDTO);
                }
                return accionesCobro2CModeloDTO;
            case ACCION_COBRO_2D:
                logger.info("ACCION_COBRO_2D");
                List<Object> accionesCobro2DModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro2D accionCobro2D = (AccionCobro2D) parametrizacionGestionCobro;
                    AccionCobro2DModeloDTO accionCobro2DModeloDTO = new AccionCobro2DModeloDTO();
                    accionCobro2DModeloDTO.convertToDTO(accionCobro2D);
                    accionesCobro2DModeloDTO.add(accionCobro2DModeloDTO);
                }
                return accionesCobro2DModeloDTO;
            case ACCION_COBRO_2F:
                logger.info("ACCION_COBRO_2F");
                List<Object> accionesCobro2FModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro2F accionCobro2F = (AccionCobro2F) parametrizacionGestionCobro;
                    AccionCobro2FModeloDTO accionCobro2FModeloDTO = new AccionCobro2FModeloDTO();
                    accionCobro2FModeloDTO.convertToDTO(accionCobro2F);
                    accionesCobro2FModeloDTO.add(accionCobro2FModeloDTO);
                }
                return accionesCobro2FModeloDTO;
            case ACCION_COBRO_2G:
                logger.info("ACCION_COBRO_2G");
                List<Object> accionesCobro2GModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro2G accionCobro2G = (AccionCobro2G) parametrizacionGestionCobro;
                    AccionCobro2GModeloDTO accionCobro2GModeloDTO = new AccionCobro2GModeloDTO();
                    accionCobro2GModeloDTO.convertToDTO(accionCobro2G);
                    accionesCobro2GModeloDTO.add(accionCobro2GModeloDTO);
                }
                return accionesCobro2GModeloDTO;
            case ACCION_COBRO_2H:
                logger.info("ACCION_COBRO_2H");
                List<Object> accionesCobro2HModeloDTO = new ArrayList<>();
                for (ParametrizacionGestionCobro parametrizacionGestionCobro : parametrizacionesGestionCobro) {
                    AccionCobro2H accionCobro2H = (AccionCobro2H) parametrizacionGestionCobro;
                    AccionCobro2HModeloDTO accionCobro2HModeloDTO = new AccionCobro2HModeloDTO();
                    accionCobro2HModeloDTO.convertToDTO(accionCobro2H);
                    accionesCobro2HModeloDTO.add(accionCobro2HModeloDTO);
                }
                return accionesCobro2HModeloDTO;
            default:
                break;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarLineaCobro(com.asopagos.dto.modelo.LineaCobroModeloDTO)
     */
    @Override
    public void guardarLineaCobro(LineaCobroModeloDTO lineaCobroDTO) {
        logger.debug("Inicia guardarLineaCobro(LineaCobroModeloDTO lineaCobroDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);

            LineaCobro lineaCobro = lineaCobroDTO.convertToLineaCobroEntity();
            Boolean guardoAccionCobro = false;
            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(lineaCobro);
            } else {
                for (Object parametrizacionGestionCobroModeloDTO : gestionCobroModeloDTO) {
                    LineaCobroModeloDTO lineaCobroDTOBD = (LineaCobroModeloDTO) parametrizacionGestionCobroModeloDTO;
                    if (lineaCobroDTO.getTipoLineaCobro().equals(lineaCobroDTOBD.getTipoLineaCobro())) {
                        lineaCobro.setIdParametrizacionGestionCobro(lineaCobroDTOBD.getIdParametrizacionGestionCobro());
                        guardarParametrizacionGestionCobro(lineaCobro);
                        guardoAccionCobro = true;
                    }
                }
                if (!guardoAccionCobro) {
                    guardarParametrizacionGestionCobro(lineaCobro);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarLineaCobro(LineaCobroModeloDTO lineaCobroDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionConveniosPago()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public LineaCobroPersonaModeloDTO consultarLineaCobroPersona(TipoLineaCobroEnum tipoLinea) {
        logger.debug("Inicia método consultarLineaCobroPersona(TipoLineaCobroEnum tipoLinea)");
        try {
            LineaCobroPersona lineaCobroPersona = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LINEA_COBRO, LineaCobroPersona.class).setParameter("tipoLinea", tipoLinea).getSingleResult();
            LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTO = new LineaCobroPersonaModeloDTO();
            lineaCobroPersonaModeloDTO.convertToDTO(lineaCobroPersona);
            logger.debug("Finaliza método consultarAccionCobro2E()");
            return lineaCobroPersonaModeloDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la linea de cobro personas: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarLineaCobroPersona: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarLineaCobroPersona(com.asopagos.dto.modelo.LineaCobroPersonaModeloDTO)
     */
    @Override
    public void guardarLineaCobroPersona(LineaCobroPersonaModeloDTO lineaCobroPersonaDTO) {
        logger.debug("Inicia guardarLineaCobroPersona(LineaCobroPersonaModeloDTO lineaCobroPersonaDTO)");
        try {
            LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTOBD = consultarLineaCobroPersona(lineaCobroPersonaDTO.getTipoLineaCobro());

            LineaCobroPersona lineaCobroPersona = lineaCobroPersonaDTO.convertToLineaCobroPersonaEntity();
            if (lineaCobroPersonaModeloDTOBD == null) {
                entityManager.persist(lineaCobroPersona);
            } else {
                lineaCobroPersona.setIdLineaCobroPersona(lineaCobroPersonaModeloDTOBD.getIdLineaCobroPersona());
                entityManager.merge(lineaCobroPersona);
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarLineaCobroPersona(LineaCobroPersonaModeloDTO lineaCobroPersonaDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobroA(com.asopagos.dto.modelo.AccionCobroAModeloDTO)
     */
    @Override
    public void guardarAccionCobroA(AccionCobroAModeloDTO accionCobroADTO) {
        logger.debug("Inicia guardarAccionCobroA(AccionCobroAModeloDTO accionCobroADTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
            AccionCobroA accionCobroA = accionCobroADTO.convertToAccionCobroAEntity();
            logger.info("el numero de dias limite de pago es :: " + accionCobroA.getDiasLimitePago());
            Boolean guardoAccionCobro = false;
            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobroA);
            } else {
                for (Object parametrizacionGestionCobroModeloDTO : gestionCobroModeloDTO) {
                    AccionCobroAModeloDTO accionCobroADTOBD = (AccionCobroAModeloDTO) parametrizacionGestionCobroModeloDTO;
                    if (accionCobroA.getMetodo().equals(accionCobroADTOBD.getMetodo())) {
                        accionCobroA.setIdParametrizacionGestionCobro(accionCobroADTOBD.getIdParametrizacionGestionCobro());
                        guardarParametrizacionGestionCobro(accionCobroA);
                        guardoAccionCobro = true;
                    }
                }
                if (!guardoAccionCobro) {
                    guardarParametrizacionGestionCobro(accionCobroA);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobroA(AccionCobroAModeloDTO accionCobroADTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobroB(com.asopagos.dto.modelo.AccionCobroBModeloDTO)
     */
    @Override
    public void guardarAccionCobroB(AccionCobroBModeloDTO accionCobroBDTO) {
        logger.debug("Inicia guardarAccionCobroB(AccionCobroBModeloDTO accionCobroBDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_B);
            AccionCobroB accionCobroB = accionCobroBDTO.convertToAccionCobroBEntity();
            Boolean guardoAccionCobro = false;
            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobroB);
            } else {
                for (Object parametrizacionGestionCobroModeloDTO : gestionCobroModeloDTO) {
                    AccionCobroBModeloDTO accionCobroBDTOBD = (AccionCobroBModeloDTO) parametrizacionGestionCobroModeloDTO;
                    if (accionCobroB.getMetodo().equals(accionCobroBDTOBD.getMetodo())) {
                        accionCobroB.setIdParametrizacionGestionCobro(accionCobroBDTOBD.getIdParametrizacionGestionCobro());
                        guardarParametrizacionGestionCobro(accionCobroB);
                        guardoAccionCobro = true;
                    }
                }
                if (!guardoAccionCobro) {
                    guardarParametrizacionGestionCobro(accionCobroB);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobroB(AccionCobroBModeloDTO accionCobroBDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro1C(com.asopagos.dto.modelo.AccionCobro1CModeloDTO)
     */
    @Override
    public void guardarAccionCobro1C(AccionCobro1CModeloDTO accionCobro1CModeloDTO) {
        logger.debug("Inicia guardarAccionCobro1C(AccionCobro1CModeloDTO accionCobro1CDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1C);
            AccionCobro1C accionCobro1C = accionCobro1CModeloDTO.convertToAccionCobro1CEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro1C);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro1CModeloDTO accionCobro1CModelo = (AccionCobro1CModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro1C accionCobro1CTem = accionCobro1CModelo.convertToAccionCobro1CEntity();
                    accionCobro1C.setIdParametrizacionGestionCobro(accionCobro1CTem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro1C);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 1C");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro1C(AccionCobro1CModeloDTO accionCobro1CDTO)");
            logger.error("Hubo un error al guardar acción de cobro 1C:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro1D(com.asopagos.dto.modelo.AccionCobro1DModeloDTO)
     */
    @Override
    public void guardarAccionCobro1D(AccionCobro1DModeloDTO accionCobro1DModeloDTO) {
        logger.debug("Inicia guardarAccionCobro1D(AccionCobro1DModeloDTO accionCobro1DModeloDTO)");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_ACCION_COBRO_1D)
                    .getSingleResult();
            logger.info("la consulta funciona :: " + result.toString());
            AccionCobro1D accionCobro1D = accionCobro1DModeloDTO.convertToAccionCobro1DEntity();
            logger.info("si convierte bien a entity :: " + mapper.writeValueAsString(accionCobro1D));
            if (Objects.isNull(result)) {
                guardarParametrizacionGestionCobro1D(accionCobro1D, Boolean.TRUE, null);
            } else {
                logger.info("else accionCobro1d");
                guardarParametrizacionGestionCobro1D(accionCobro1D, Boolean.FALSE, Long.parseLong(result.toString()));
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro1D(AccionCobro1DModeloDTO accionCobro1DModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro1E(com.asopagos.dto.modelo.AccionCobro1EModeloDTO)
     */
    @Override
    public void guardarAccionCobro1E(AccionCobro1EModeloDTO accionCobro1EModeloDTO) {
        logger.debug("Inicia guardarAccionCobro1E(AccionCobro1EModeloDTO accionCobro1EModeloDTO)");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1E);
            AccionCobro1E accionCobro1E = accionCobro1EModeloDTO.convertToAccionCobro1EEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro1E);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro1EModeloDTO accionCobro1EModelo = (AccionCobro1EModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro1E accionCobro1ETem = accionCobro1EModelo.convertToAccionCobro1EEntity();
                    accionCobro1E.setIdParametrizacionGestionCobro(accionCobro1ETem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro1E);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 1E");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro1E(AccionCobro1EModeloDTO accionCobro1EModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro1F(com.asopagos.dto.modelo.AccionCobro1FModeloDTO)
     */
    @Override
    public void guardarAccionCobro1F(AccionCobro1FModeloDTO accionCobro1FModeloDTO) {
        logger.debug("Inicia guardarAccionCobro1F(AccionCobro1FModeloDTO accionCobro1FDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F);
            AccionCobro1F accionCobro1F = accionCobro1FModeloDTO.convertToAccionCobro1FEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro1F);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro1FModeloDTO accionCobro1FModelo = (AccionCobro1FModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro1F accionCobro1FTem = accionCobro1FModelo.convertToAccionCobro1FEntity();
                    accionCobro1F.setIdParametrizacionGestionCobro(accionCobro1FTem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro1F);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 1F");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }

            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro1F(AccionCobro1FModeloDTO accionCobro1FDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2C(com.asopagos.dto.modelo.AccionCobro2CModeloDTO)
     */
    @Override
    public void guardarAccionCobro2C(AccionCobro2CModeloDTO accionCobro2CModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2C(AccionCobro2CModeloDTO accionCobro2CDTO) ");
        try {
            Object result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACCION_COBRO_2C)
                    .getSingleResult();
            logger.info("la consulta funciona :: " + result.toString());
            AccionCobro2C accionCobro2C = accionCobro2CModeloDTO.convertToAccionCobro2CEntity();
            logger.info("si convierte bien a entity :: " + accionCobro2C.toString());
            if (Objects.isNull(result)) {
                guardarParametrizacionGestionCobroC2(accionCobro2C, Boolean.TRUE, null);

            } else {
                BigInteger id = new BigInteger(result.toString());
                guardarParametrizacionGestionCobroC2(accionCobro2C, Boolean.FALSE, id.longValue());
            }

            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2C);
            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro2C);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro2CModeloDTO accionCobro2CModelo = (AccionCobro2CModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro2C accionCobro2CTem = accionCobro2CModelo.convertToAccionCobro2CEntity();
                    accionCobro2C.setIdParametrizacionGestionCobro(accionCobro2CTem.getIdParametrizacionGestionCobro());

                    guardarParametrizacionGestionCobro(accionCobro2C);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 2F");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }

            }


        } catch (
                Exception e) {
            logger.debug("Finaliza guardarAccionCobro2C(AccionCobro2CModeloDTO accionCobro2CDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2D(com.asopagos.dto.modelo.AccionCobro2DModeloDTO)
     */
    @Override
    public void guardarAccionCobro2D(AccionCobro2DModeloDTO accionCobro2DModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2D(AccionCobro2DModeloDTO accionCobro2DDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2D);
            AccionCobro2D accionCobro2D = accionCobro2DModeloDTO.convertToAccionCobro2DEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro2D);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro2DModeloDTO accionCobro2DModelo = (AccionCobro2DModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro2D accionCobro2DTem = accionCobro2DModelo.convertToAccionCobro2DEntity();
                    accionCobro2D.setIdParametrizacionGestionCobro(accionCobro2DTem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro2D);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 2D");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro2D(AccionCobro2DModeloDTO accionCobro2DDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    public void guardarParametrizacionGestionCobro2FConvert(AccionCobro2FModeloDTO accionCobro2FModeloDTO) {
        logger.debug("Inicia accionCobro2F(AccionCobroBModeloDTO accionCobroBDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2F);
            AccionCobro2F accionCobro2F = accionCobro2FModeloDTO.convertToAccionCobro2FEntity();
            Boolean guardoAccionCobro = false;
            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                ParametrizacionGestionCobro parametrizacionGestionCobro = accionCobro2FModeloDTO.convertToEntity(accionCobro2F);
                guardarParametrizacionGestionCobro(parametrizacionGestionCobro);
            } else {
                for (Object parametrizacionGestionCobroModeloDTO : gestionCobroModeloDTO) {
                    AccionCobro2FModeloDTO accionCobro2FModeloDTOBD = (AccionCobro2FModeloDTO) parametrizacionGestionCobroModeloDTO;
                    accionCobro2F.setIdParametrizacionGestionCobro(accionCobro2FModeloDTOBD.getIdParametrizacionGestionCobro());
                    accionCobro2F.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2F);
                    ParametrizacionGestionCobro parametrizacionGestionCobro = accionCobro2FModeloDTO.convertToEntity(accionCobro2F);
                    guardarParametrizacionGestionCobro(parametrizacionGestionCobro);
                    guardoAccionCobro = true;

                }
                if (!guardoAccionCobro) {
                    ParametrizacionGestionCobro parametrizacionGestionCobro = accionCobro2FModeloDTO.convertToEntity(accionCobro2F);
                    guardarParametrizacionGestionCobro(parametrizacionGestionCobro);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza accionCobro2F(AccionCobroBModeloDTO accionCobroBDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2F(com.asopagos.dto.modelo.AccionCobro2FModeloDTO)
     */
    @Override
    public void guardarAccionCobro2F(AccionCobro2FModeloDTO accionCobro2FModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2F(AccionCobro2FModeloDTO accionCobro2FModeloDTO)");
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("INICIA METODO guardarAccionCobro2F");


            Object result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_ACCION_COBRO_2F)
                    .getSingleResult();
            logger.info("la consulta funciona :: " + result.toString());
            AccionCobro2F accionCobro2F = accionCobro2FModeloDTO.convertToAccionCobro2FEntity();
            logger.info("si convierte bien a entity :: " + mapper.writeValueAsString(accionCobro2F));
            if (Objects.isNull(result)) {
                guardarParametrizacionGestionCobro2F(accionCobro2F, Boolean.TRUE, null);
            } else {
                BigInteger id = new BigInteger(result.toString());
                guardarParametrizacionGestionCobro2F(accionCobro2F, Boolean.FALSE, Long.parseLong(result.toString()));
            }
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2F);


            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro2F);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro2FModeloDTO accionCobro2FModelo = (AccionCobro2FModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro2F accionCobro2FTem = accionCobro2FModelo.convertToAccionCobro2FEntity();
                    accionCobro2F.setIdParametrizacionGestionCobro(accionCobro2FTem.getIdParametrizacionGestionCobro());

                    guardarParametrizacionGestionCobro(accionCobro2F);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 2F");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }

            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro2F(AccionCobro2FModeloDTO accionCobro2FModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2G(com.asopagos.dto.modelo.AccionCobro2GModeloDTO)
     */
    @Override
    public void guardarAccionCobro2G(AccionCobro2GModeloDTO accionCobro2GModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2G(AccionCobro2GModeloDTO accionCobro2GModeloDTO)");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2G);
            AccionCobro2G accionCobro2G = accionCobro2GModeloDTO.convertToAccionCobro2GEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro2G);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro2GModeloDTO accionCobro2GModelo = (AccionCobro2GModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro2G accionCobro2GTem = accionCobro2GModelo.convertToAccionCobro2GEntity();
                    accionCobro2G.setIdParametrizacionGestionCobro(accionCobro2GTem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro2G);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 2G");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro2G(AccionCobro2GModeloDTO accionCobro2GModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2H(com.asopagos.dto.modelo.AccionCobro2HModeloDTO)
     */
    @Override
    public void guardarAccionCobro2H(AccionCobro2HModeloDTO accionCobro2HModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2H(AccionCobro2HModeloDTO accionCobro2HDTO) ");
        try {
            List<Object> gestionCobroModeloDTO = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2H);
            AccionCobro2H accionCobro2H = accionCobro2HModeloDTO.convertToAccionCobro2HEntity();

            if (gestionCobroModeloDTO == null || gestionCobroModeloDTO.isEmpty()) {
                guardarParametrizacionGestionCobro(accionCobro2H);
            } else {
                if (gestionCobroModeloDTO.size() == 1) {
                    AccionCobro2HModeloDTO accionCobro2HModelo = (AccionCobro2HModeloDTO) gestionCobroModeloDTO.get(0);
                    AccionCobro2H accionCobro2HTem = accionCobro2HModelo.convertToAccionCobro2HEntity();
                    accionCobro2H.setIdParametrizacionGestionCobro(accionCobro2HTem.getIdParametrizacionGestionCobro());
                    guardarParametrizacionGestionCobro(accionCobro2H);
                } else {
                    logger.error("Existe mas de un registro para la accion de cobro 2H");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
                }
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro2H(AccionCobro2HModeloDTO accionCobro2HDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionConveniosPago()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AccionCobro2EModeloDTO consultarAccionCobro2E() {
        logger.debug("Inicia método consultarAccionCobro2E()");
        try {
            AccionCobro2E accionCobro2E = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACCION_COBRO_2E, AccionCobro2E.class).getSingleResult();
            AccionCobro2EModeloDTO accionCobro2EModeloDTO = new AccionCobro2EModeloDTO();
            accionCobro2EModeloDTO.convertToDTO(accionCobro2E);
            logger.debug("Finaliza método consultarAccionCobro2E()");
            return accionCobro2EModeloDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la accion de cobro 2E: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarAccionCobro2E: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAccionCobro2E(com.asopagos.dto.modelo.AccionCobro2EModeloDTO)
     */
    @Override
    public void guardarAccionCobro2E(AccionCobro2EModeloDTO accionCobro2EModeloDTO) {
        logger.debug("Inicia guardarAccionCobro2E(AccionCobro2EModeloDTO accionCobro2EModeloDTO)");
        try {
            AccionCobro2EModeloDTO accionCobro2EModeloDTOBD = consultarAccionCobro2E();

            AccionCobro2E accionCobro2E = accionCobro2EModeloDTO.convertToEntity();
            if (accionCobro2EModeloDTOBD == null) {
                entityManager.persist(accionCobro2E);
            } else {
                accionCobro2E.setIdAccionCobro2E(accionCobro2EModeloDTOBD.getIdAccionCobro2E());
                entityManager.merge(accionCobro2E);
            }
        } catch (Exception e) {
            logger.debug("Finaliza guardarAccionCobro2E(AccionCobro2EModeloDTO accionCobro2EModeloDTO)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarHistoricoConvenioPago(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioPagoModeloDTO> consultarHistoricoConvenioPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.info("**__**Inicia consultarHistoricoConvenioPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        try {
            List<ConvenioPagoModeloDTO> conveniosPagoModeloDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_PAGO_FECHA_LIMITE, ConvenioPagoModeloDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).getResultList();

            return conveniosPagoModeloDTO;
        } catch (Exception e) {
            logger.debug("Finaliza consultarHistoricoConvenioPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#accionConsultar(java.util.List, com.asopagos.dto.cartera.AportanteConvenioDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteConvenioDTO> consultarConveniosFiltro(AportanteConvenioDTO aportanteConvenioDTO) {
        logger.debug("Inicia consultarConveniosFiltro(AportanteConvenioDTO aportanteConvenioDTO)");
        String razonSocial = aportanteConvenioDTO.getNombreRazonSocial() != null ? '%' + aportanteConvenioDTO.getNombreRazonSocial() + '%' : null;
        String primerNombre = aportanteConvenioDTO.getPrimerNombre() != null ? '%' + aportanteConvenioDTO.getPrimerNombre() + '%' : null;
        String segundoNombre = aportanteConvenioDTO.getSegundoNombre() != null ? '%' + aportanteConvenioDTO.getSegundoNombre() + '%' : null;
        String primerApellido = aportanteConvenioDTO.getPrimerApellido() != null ? '%' + aportanteConvenioDTO.getPrimerApellido() + '%' : null;
        String segundoApellido = aportanteConvenioDTO.getSegundoApellido() != null ? '%' + aportanteConvenioDTO.getSegundoApellido() + '%' : null;

        Date fechaRegistroInicio = null;
        Date fechaRegistroFin = null;

        if (aportanteConvenioDTO.getFechaRegistro() != null) {
            fechaRegistroInicio = Date.from(Instant.ofEpochMilli(aportanteConvenioDTO.getFechaRegistro()).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            fechaRegistroFin = Date.from(Instant.ofEpochMilli(aportanteConvenioDTO.getFechaRegistro()).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
        }

        List<Object[]> aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_PAGO_FILTRO).setParameter("tipoIdentificacion", aportanteConvenioDTO.getTipoIdentificacion() != null ? aportanteConvenioDTO.getTipoIdentificacion().name() : null).setParameter("numeroIdentificacion", aportanteConvenioDTO.getNumeroIdentificacion()).setParameter("razonSocial", razonSocial).setParameter("primerNombre", primerNombre).setParameter("segundoNombre", segundoNombre).setParameter("primerApellido", primerApellido).setParameter("segundoApellido", segundoApellido).setParameter("tipoSolicitante", aportanteConvenioDTO.getTipoSolicitante().name()).setParameter("idConvenioPago", aportanteConvenioDTO.getNumeroRegistro()).setParameter("fechaRegistroInicio", fechaRegistroInicio).setParameter("fechaRegistroFin", fechaRegistroFin).setParameter("estadoConvenioPago", aportanteConvenioDTO.getEstadoConvenio() != null ? aportanteConvenioDTO.getEstadoConvenio().name() : null).getResultList();

        List<AportanteConvenioDTO> aportantesConvenioDTO = new ArrayList<>();
        if (aportantes != null && !aportantes.isEmpty()) {
            for (Object[] aportante : aportantes) {
                AportanteConvenioDTO aportanteConvenioDTOTem = new AportanteConvenioDTO();
                aportanteConvenioDTOTem.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(aportante[0].toString()));
                aportanteConvenioDTOTem.setNumeroIdentificacion(aportante[1].toString());
                aportanteConvenioDTOTem.setNombreRazonSocial(aportante[2].toString());
                aportanteConvenioDTOTem.setNumeroRegistro(Long.parseLong(aportante[3].toString()));
                aportanteConvenioDTOTem.setEstadoConvenio(EstadoConvenioPagoEnum.valueOf(aportante[4].toString()));
                try {
                    aportanteConvenioDTOTem.setFechaRegistro(new SimpleDateFormat("yyyy-MM-dd").parse(aportante[5].toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                aportantesConvenioDTO.add(aportanteConvenioDTOTem);
            }
        }
        logger.debug("Finaliza consultarConveniosPagoFiltro(AportanteConvenioDTO aportanteConvenioDTO)");
        return aportantesConvenioDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#buscarListaExclusionCarteraActiva(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ExclusionCarteraDTO> buscarListaExclusionCarteraActiva(TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<Long> listIdePersonas) {
        try {
            logger.debug("Inicia buscarListaExclusionCarteraActiva");
            List<ExclusionCarteraDTO> listExclusiones = null;

            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                listExclusiones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSION_CARTERA_APORTANTE_ACTIVA_EMPLEADOR).setParameter("idPersonas", listIdePersonas).setParameter("tipoSolicitante", tipoSolicitante).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).setParameter("estadoConvenioPago", EstadoConvenioPagoEnum.ACTIVO).getResultList();
            } else {
                TipoAfiliadoEnum tipoAfiliacion = null;

                if (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {
                    tipoAfiliacion = TipoAfiliadoEnum.PENSIONADO;
                }

                if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)) {
                    tipoAfiliacion = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
                }

                listExclusiones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSION_CARTERA_APORTANTE_ACTIVA_INDEPENDIENTE_PENSIONADO).setParameter("idPersonas", listIdePersonas).setParameter("tipoSolicitante", tipoSolicitante).setParameter("tipoAfiliacion", tipoAfiliacion).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).setParameter("estadoConvenioPago", EstadoConvenioPagoEnum.ACTIVO).getResultList();
            }

            logger.debug("Finaliza buscarListaExclusionCarteraActiva");
            return listExclusiones;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en buscarListaExclusionCarteraActiva", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método encargado de consultar una exclusion de convenio pago
     *
     * @param tipoIdentificacion,   tipo de identificación de la persona
     * @param numeroIdentificacion, número de identificación de la persona
     * @param tipoSolicitante       tipo de solicitante
     * @return retorna la exclusion encontrada
     */
    private ExclusionCarteraDTO buscarConvenioPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        ExclusionCarteraDTO convenioPago = null;
        try {
            convenioPago = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSIONES_CONVENIO_PAGO, ExclusionCarteraDTO.class).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).setParameter("estadoConvenioPago", EstadoConvenioPagoEnum.ACTIVO).getSingleResult();
        } catch (NoResultException e) {
            convenioPago = null;
        }
        if (convenioPago != null) {
            List<PagoPeriodoConvenioModeloDTO> pagoPeriodoConvenio = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_PAGO_EXCLUSION_POR_PERSONA).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).setParameter("estadoConvenioPago", EstadoConvenioPagoEnum.ACTIVO).getResultList();
            convenioPago.setPeriodoConvenioPago(pagoPeriodoConvenio);
            convenioPago.setFechaFinalizacion(pagoPeriodoConvenio.get(0).getFechaPago());
        }

        return convenioPago;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarPagoConvenio(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioPagoModeloDTO> consultarPagoConvenio(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarPagoConvenio");
        LocalDateTime fecha = LocalDate.now().atStartOfDay();
        Date fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        if (diasFestivos.contains(fechaActual)) {
            /* el día actual es un dia no habil no se consulta */
            return null;
        }
        /* si al restarle un día a la fecha actual fue un dia no habil, se sigue buscando un día anterior */
        while (diasFestivos.contains(fechaActual) || fecha.getDayOfWeek() == DayOfWeek.SUNDAY || fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fecha.minusDays(1);
            fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        }
        /* se consultan los convenios que no tuvieron pago, por el valor pactado en la cuota que se vence. */
        List<ConvenioPago> convenios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_CONVENIO, ConvenioPago.class).setParameter("fechaPago", fechaActual).setParameter("tipoSolicitante", TipoSolicitanteMovimientoAporteEnum.EMPLEADOR).getResultList();
        convenios.addAll(entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_CONVENIO_PERSONAS, ConvenioPago.class).setParameter("fechaPago", fechaActual).setParameter("tipoSolicitante", TipoSolicitanteMovimientoAporteEnum.EMPLEADOR).getResultList());

        List<ConvenioPagoModeloDTO> conveniosDTO = new ArrayList<>();
        for (ConvenioPago convenioPago : convenios) {
            ConvenioPagoModeloDTO convenioDTO = new ConvenioPagoModeloDTO();
            convenioDTO.convertToDTO(convenioPago);
            conveniosDTO.add(convenioDTO);
        }
        logger.debug("Fin de método consultarPagoConvenio");
        return conveniosDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarConveniosCierre(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioPagoModeloDTO> consultarConveniosCierre(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarConveniosCierre");
        LocalDateTime fecha = LocalDate.now().atStartOfDay();
        Date fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());

        if (diasFestivos.contains(fechaActual)) {
            /* el día actual es un dia no habil no se consulta */
            return null;
        }
        /* si al restarle un día a la fecha actual fue un dia no habil, se sigue buscando un día anterior */
        while (diasFestivos.contains(fechaActual) || fecha.getDayOfWeek() == DayOfWeek.SUNDAY || fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fecha.minusDays(1);
            fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        }

        List<ConvenioPagoModeloDTO> convenios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_CIERRE).setParameter("fechaPago", fechaActual).setParameter("estado", EstadoConvenioPagoEnum.ACTIVO).getResultList();
        logger.debug("Fin de método consultarConveniosCierre");
        return convenios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarConveniosComunicado(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioPagoModeloDTO> consultarConveniosComunicado(List<Date> diasFestivos) {
        logger.debug("Inicio de método consultarConveniosComunicado");
        LocalDateTime fecha = LocalDate.now().atStartOfDay();
        Date fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        if (diasFestivos.contains(fechaActual)) {
            /* el día actual es un dia no habil no se consulta */
            return null;
        }

        /* se recorren tres días habiles hacía atras para conocer cuales son los convenios que ya pueden cerrarse */
        Integer diasHabilesCierre = 1;
        while (diasFestivos.contains(fechaActual) || diasHabilesCierre <= 3 || fecha.getDayOfWeek() == DayOfWeek.SUNDAY || fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            if (!diasFestivos.contains(fechaActual)) {
                diasHabilesCierre++;
            }
            fecha = fecha.minusDays(1);
            fechaActual = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        }

        List<ConvenioPagoModeloDTO> convenios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIOS_COMUNICADO).setParameter("fechaPago", fechaActual).setParameter("estadoConvenio", EstadoConvenioPagoEnum.CERRADO).getResultList();
        logger.debug("Fin de método consultarConveniosComunicado");
        return convenios;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarConveniosPago(java.util.List)
     */
    @Override
    public void actualizarConveniosPago(List<ConvenioPagoModeloDTO> conveniosPago) {
        logger.debug("Inicio de método actualizarConveniosPago");
        for (ConvenioPagoModeloDTO convenioPago : conveniosPago) {
            entityManager.merge(convenioPago.convertToEntity());
        }
        logger.debug("Fin de método actualizarConveniosPago");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarEstadoCartera(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoCarteraEnum consultarEstadoCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        logger.debug("Inicia método consultarEstadoCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        EstadoCarteraEnum estadoCarteraEnum = null;
        try {
            Object estado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARTERA_APORTANTE).setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante.name()).getSingleResult();
            estadoCarteraEnum = EstadoCarteraEnum.valueOf(estado != null ? estado.toString() : null);
        } catch (NoResultException ex) {
            estadoCarteraEnum = null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarEstadoCartera: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método consultarEstadoCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoSolicitanteMovimientoAporteEnum tipoSolicitante)");
        return estadoCarteraEnum;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesConvenio(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteConvenioDTO> consultarAportantesConvenio(AportanteCarteraDTO aportante, UriInfo uri, HttpServletResponse response) {
        logger.debug("Inicia método consultarAportantesConvenio(TipoSolicitanteMovimientoAporteEnum,List<Long>)");
        try {
            String razonSocial = aportante.getNombreRazonSocial() != null ? '%' + aportante.getNombreRazonSocial() + '%' : null;
            String primerNombre = aportante.getPrimerNombre() != null ? '%' + aportante.getPrimerNombre() + '%' : null;
            String segundoNombre = aportante.getSegundoNombre() != null ? '%' + aportante.getSegundoNombre() + '%' : null;
            String primerApellido = aportante.getPrimerApellido() != null ? '%' + aportante.getPrimerApellido() + '%' : null;
            String segundoApellido = aportante.getSegundoApellido() != null ? '%' + aportante.getSegundoApellido() + '%' : null;

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            Query query = null;
            String consulta = NamedQueriesConstants.CONSULTAR_APORTANTE_CONVENIO;
            queryBuilder.addParam("tipoIdentificacion", aportante.getTipoIdentificacion());
            queryBuilder.addParam("numeroIdentificacion", aportante.getNumeroIdentificacion());
            queryBuilder.addParam("digitoVerificacion", aportante.getDigitoVerificacion());
            queryBuilder.addParam("razonSocial", razonSocial);
            queryBuilder.addParam("primerNombre", primerNombre);
            queryBuilder.addParam("segundoNombre", segundoNombre);
            queryBuilder.addParam("primerApellido", primerApellido);
            queryBuilder.addParam("segundoApellido", segundoApellido);
            queryBuilder.addParam("tipoSolicitante", aportante.getTipoSolicitante());
            query = queryBuilder.createQuery(consulta, null);
            List<AportanteConvenioDTO> aportantesConvenio = query.getResultList();

            logger.debug("Finaliza método consultarAportantesConvenio(TipoSolicitanteMovimientoAporteEnum,List<Long>)");
            return aportantesConvenio;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarAportantesConvenio: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarInformacionCierreConvenio(java.lang.Long,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DatosComunicadoCierreConvenioDTO consultarInformacionCierreConvenio(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipo) {
        logger.debug("Inicia método consultarInformacionCierreConvenio(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipo)");
        try {
            DatosComunicadoCierreConvenioDTO convenioDTO = new DatosComunicadoCierreConvenioDTO();
            /* Verifica si es empleador para consultar su información */
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipo)) {
                convenioDTO = (DatosComunicadoCierreConvenioDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_CONVENIOCIERRE).setParameter("idPersona", idPersona).getSingleResult();

            } else {
                convenioDTO = (DatosComunicadoCierreConvenioDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_CONVENIOCIERRE).setParameter("idPersona", idPersona).getSingleResult();
            }
            logger.debug("Finaliza método consultarInformacionCierreConvenio(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipo)");
            return convenioDTO;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException nue) {
            logger.error("Existe mas de un registro para la Parametrizacion de exclusiones: ", nue);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarInformacionCierreConvenio: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarPeriodosMorososCotizantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraDependienteModeloDTO> consultarPeriodosMorososCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long periodo) {
        logger.debug("Inicia método consultarPeriodosMorososCotizantes(TipoIdentificacionEnum, String,Long)");
        Date periodoFecha = new Date(periodo);
        List<CarteraDependienteModeloDTO> carteraDependienteDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_DEPENDIENTE, CarteraDependienteModeloDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("periodo", periodoFecha).getResultList();

        logger.debug("Finaliza método consultarPeriodosMorososCotizantes(TipoIdentificacionEnum, String,Long)");
        return carteraDependienteDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#crearRegistroCartera()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void crearRegistroCartera() {
        logger.debug("Inicia crearRegistroCartera()");
        consultasModeloCore.crearRegistroCartera();
        //procesarTemporales();
        //List<CarteraAgrupadora> listaCarteraAgrupadora = consultasModeloCore.prepararCarteraAgrupadora();
        //consultasModeloCore.crearCarteraAgrupadora(listaCarteraAgrupadora);
        logger.debug("Finaliza crearRegistroCartera()");

    }

    /**
     * Metodo que guara la paramtrización de la cartera
     *
     * @param cartera objeto que contiene la información para ser almacenada en la bd
     */
    private void guardarParametrizacionCartera(ParametrizacionCartera cartera) {
        logger.debug("Inicia metodo guardarParametrizacionCartera(ParametrizacionCartera cartera)");
        logger.info("CarteraBusiness.guardarParametrizacionCartera->" + cartera);
        /* Se actualiza el objeto si el IdParametrizacionCartera existe */
        cartera.setFechaActualizacion(new Date());

        logger.info("**__**cartera.getIdParametrizacionCartera();:" + cartera.getIdParametrizacionCartera());
        logger.info("**__**cartera.getAplicar(    );:" + cartera.getAplicar());
        logger.info("**__**cartera.getCantidadPeriodos();:" + cartera.getCantidadPeriodos());
        logger.info("**__**cartera.getEstadoCartera(   );:" + cartera.getEstadoCartera());

        logger.info("**__**cartera.getIncluirIndependientes();:" + cartera.getIncluirIndependientes());
        logger.info("**__**cartera.getIncluirPensionados();:" + cartera.getIncluirPensionados());
        logger.info("**__**cartera.getMayorTrabajadoresActivos();:" + cartera.getMayorTrabajadoresActivos());
        logger.info("**__**cartera.getMayorValorPromedio();:" + cartera.getMayorValorPromedio());
        logger.info("**__**cartera.getMayorVecesMoroso();:" + cartera.getMayorVecesMoroso());
        logger.info("**__**cartera.getPeriodosMorosos();:" + cartera.getPeriodosMorosos());
        logger.info("**__**cartera.getTipoParametrizacion();:" + cartera.getTipoParametrizacion());
        logger.info("**__**cartera.getTrabajadoresActivos();:" + cartera.getTrabajadoresActivos());
        logger.info("**__**cartera.getValorPromedioAportes();:" + cartera.getValorPromedioAportes());

        if (cartera.getIdParametrizacionCartera() != null) {
            logger.info("11Se realiza merge del objeto");
            entityManager.merge(cartera);
            logger.info("Se realiza merge del objeto");
        } else {
            logger.info("22Se realiza persist del objeto");
            /* Se persiste la información de la parametrización de la carteta modelo */
            entityManager.persist(cartera);
            logger.info("Se realiza persist del objeto");
        }
        logger.debug("Finaliza metodo guardarParametrizacionCartera(ParametrizacionCartera cartera)");
    }

    /**
     * Metodo que permite consultar la cantidad de entidades del ciclo de fiscalización
     *
     * @param idCiclos recibe como parametro una lista de tipo long con los id de los ciclos
     * @return una lista que contiene las entidades del ciclo de fiscalización
     */
    private List<Object[]> consultarCantidadEntidadesCiclosFiscalizacion(List<Long> idCiclos) {
        logger.debug("Inicia metodo consultarCantidadEntidadesCiclosFiscalizacion(List<Long>idCiclos) ");
        /* Se carga la consulta con la cantidad de entidades del ciclo de fiscalización */
        List<Object[]> objects = new ArrayList<>();
        try {
            /* Si los id de ciclos viene vacio se retorna null para tratamiento desde la pantalla */
            if (idCiclos.isEmpty()) {
                return null;
            } else {
                objects = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CICLOFISCALIZACION_CONSULTAR_ENTITADES_CICLOFISCALIZACION_POR_NUMEROCICLO).setParameter(ID_CICLO_CARTERA, idCiclos).getResultList();

                logger.debug("Finaliza metodo consultarCantidadEntidadesCiclosFiscalizacion(List<Long> idCiclos)");
            }
        } catch (Exception e) {
            logger.error("Finaliza metodo consultarCantidadEntidadesCiclosFiscalizacion(List<Long> idCiclos)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return objects;
    }

    private void guardarParametrizacionGestionCobroC2(AccionCobro2C accionCobro2C, Boolean banderaInsert, Long id) {
        logger.info("Inicia metodo guardarParametrizacionGestionCobroC2");
        try {
            if (banderaInsert) {
                entityManager.createNamedQuery(NamedQueriesConstants.INSERTAR_ACCION_COBRO_2C)
                        .setParameter("anexoLiquidacion", accionCobro2C.getAnexoLiquidacion())
                        .setParameter("diasEjecucion", accionCobro2C.getDiasEjecucion())
                        .setParameter("horaEjecucion", accionCobro2C.getHoraEjecucion())
                        .setParameter("diasGeneracionActa", accionCobro2C.getDiasGeneracionActa())
                        .executeUpdate();
            } else {
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ACCION_COBRO_2C)
                        .setParameter("anexoLiquidacion", accionCobro2C.getAnexoLiquidacion())
                        .setParameter("diasEjecucion", accionCobro2C.getDiasEjecucion())
                        .setParameter("horaEjecucion", accionCobro2C.getHoraEjecucion())
                        .setParameter("diasGeneracionActa", accionCobro2C.getDiasGeneracionActa())
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardarParametrizacionGestionCobro1D ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info("finaliza metodo guardarParametrizacionGestionCobroC2");
    }

    private void guardarParametrizacionGestionCobro1D(AccionCobro1D accionCobro1D, Boolean banderaInsert, Long id) {
        logger.info("Inicia metodo guardarParametrizacionGestionCobro1D");
        try {
            if (banderaInsert) {
                entityManager.createNamedQuery(NamedQueriesConstants.INSERTAR_ACCION_COBRO_1D)
                        .setParameter("InicioDiasConteo", accionCobro1D.getInicioDiasConteo().name())
                        .setParameter("DiasTranscurridos", accionCobro1D.getDiasTranscurridos())
                        .setParameter("HoraEjecucion", accionCobro1D.getHoraEjecucion())
                        .setParameter("LimiteEnvio", accionCobro1D.getLimiteEnvio())
                        .setParameter("Comunicado", accionCobro1D.getComunicado())
                        .executeUpdate();
            } else {
                logger.info(accionCobro1D.toString());
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ACCION_COBRO_1D)
                        .setParameter("InicioDiasConteo", accionCobro1D.getInicioDiasConteo().name())
                        .setParameter("DiasTranscurridos", accionCobro1D.getDiasTranscurridos())
                        .setParameter("HoraEjecucion", accionCobro1D.getHoraEjecucion())
                        .setParameter("LimiteEnvio", accionCobro1D.getLimiteEnvio())
                        .setParameter("Comunicado", accionCobro1D.getComunicado())
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardarParametrizacionGestionCobro1D ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info("finaliza metodo guardarParametrizacionGestionCobro1D");
    }

    /**
     * metodo para guardar la parametrizacion accion de cobro 2f
     */
    private void guardarParametrizacionGestionCobro2F(AccionCobro2F accionCobro2F, Boolean banderaInsert, Long id) {
        logger.info("Inicia metodo guardarParametrizacionGestionCobro2F");
        try {
            if (banderaInsert) {
                entityManager.createNamedQuery(NamedQueriesConstants.INSERTAR_ACCION_COBRO_2F)
                        .setParameter("InicioDiasConteo", accionCobro2F.getInicioDiasConteo().name())
                        .setParameter("DiasTranscurridos", accionCobro2F.getDiasTranscurridos())
                        .setParameter("HoraEjecucion", accionCobro2F.getHoraEjecucion())
                        .setParameter("LimiteEnvio", accionCobro2F.getLimiteEnvio())
                        .setParameter("DiasRegistro", accionCobro2F.getDiasRegistro())
                        .setParameter("DiasParametrizados", accionCobro2F.getDiasParametrizados())
                        .setParameter("Comunicado", accionCobro2F.getComunicado())
                        .executeUpdate();
            } else {
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ACCION_COBRO_2F)
                        .setParameter("InicioDiasConteo", accionCobro2F.getInicioDiasConteo().name())
                        .setParameter("DiasTranscurridos", accionCobro2F.getDiasTranscurridos())
                        .setParameter("HoraEjecucion", accionCobro2F.getHoraEjecucion())
                        .setParameter("LimiteEnvio", accionCobro2F.getLimiteEnvio())
                        .setParameter("DiasRegistro", accionCobro2F.getDiasRegistro())
                        .setParameter("DiasParametrizados", accionCobro2F.getDiasParametrizados())
                        .setParameter("Comunicado", accionCobro2F.getComunicado())
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardarParametrizacionGestionCobro1D ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info("finaliza metodo guardarParametrizacionGestionCobro1D");
    }

    /**
     * Metodo que guara la paramtrización de gestión de cobro
     *
     * @param gestionCobro objeto que contiene la información para ser almacenada en la bd
     */
    private void guardarParametrizacionGestionCobro(ParametrizacionGestionCobro gestionCobro) throws
            JsonProcessingException {
        logger.debug("Inicia metodo guardarParametrizacionGestionCobro(ParametrizacionGestionCobro gestionCobro)");
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("objeto de entrada para guardar gestion de cobro :: " + mapper.writeValueAsString(gestionCobro));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        /* Se actualiza el objeto si el IdParametrizacionGestionCobro existe */
        if (gestionCobro.getIdParametrizacionGestionCobro() != null) {
            logger.info("gestionCobrox:: " + mapper.writeValueAsString(gestionCobro));
            logger.info("String de entidad : " + gestionCobro.toString());

            entityManager.merge(gestionCobro);
            logger.info("...Se realiza merge del objeto");
        } else {
            /* Se persiste la información de la parametrización de gestion de cobro modelo */
            entityManager.persist(gestionCobro);
            logger.debug("Se realiza persist del objeto");
        }
        logger.debug("Finaliza metodo guardarParametrizacionGestionCobro(ParametrizacionGestionCobro gestionCobro)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCartera()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraModeloDTO> consultarCartera(FiltrosCarteraDTO filtrosCartera, UriInfo
            uri, HttpServletResponse response) {
        logger.debug("Inicio de servicio que se encarga de consultar la cartera");
        try {

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            StringBuilder consultaCartera = new StringBuilder("SELECT  new com.asopagos.dto.modelo.CarteraModeloDTO(car,per) FROM Cartera car, Persona per WHERE car.idPersona = per.idPersona");
            if (filtrosCartera != null && filtrosCartera.getEstadoCartera() != null) {
                consultaCartera.append(" and car.estadoCartera = :estadoCartera");
                queryBuilder.addParam("estadoCartera", filtrosCartera.getEstadoCartera());
            }
            if (filtrosCartera != null && filtrosCartera.getNumeroIdentificacion() != null && filtrosCartera.getTipoIdentificacion() != null) {
                consultaCartera.append(" and per.numeroIdentificacion=:numeroIdentificacion and per.tipoIdentificacion = :tipoIdentificacion");
                queryBuilder.addParam("numeroIdentificacion", filtrosCartera.getNumeroIdentificacion());
                queryBuilder.addParam("tipoIdentificacion", filtrosCartera.getTipoIdentificacion());
            }
            if (filtrosCartera != null && filtrosCartera.getTipoAportante() != null) {
                consultaCartera.append(" and car.tipoSolicitante=:tipoAportante");
                queryBuilder.addParam("tipoAportante", filtrosCartera.getTipoAportante());
            }
            if (filtrosCartera != null && filtrosCartera.getTipoDeuda() != null) {
                consultaCartera.append(" and car.tipoDeuda=:tipoDeuda");
                queryBuilder.addParam("tipoDeuda", filtrosCartera.getTipoDeuda());
            }

            Query query = queryBuilder.createQuery(consultaCartera.toString(), null);
            List<CarteraModeloDTO> carteras = query.getResultList();
            logger.debug("Inicio de servicio que se encarga de consultar la cartera");
            return carteras;
        } catch (Exception e) {
            logger.error("Ccurrió un error en consultarCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarParametrizacionPreventiva()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionPreventivaModeloDTO consultarParametrizacionPreventiva() {
        logger.debug("Inicio de servicio que se encarga de consultar la parametrización preventiva");
        ParametrizacionPreventivaModeloDTO parametrizacionPreventiva = (ParametrizacionPreventivaModeloDTO) consultarParametrizacionCartera(TipoParametrizacionCarteraEnum.GESTION_PREVENTIVA);
        logger.debug("Inicio de servicio que se encarga de consultar la parametrización preventiva");
        return parametrizacionPreventiva;
    }

    /* Inicio proceso 22 */

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarHistoricoCicloManual(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<CicloCarteraModeloDTO> consultarHistoricoCicloManual(Long fechaInicio, Long fechaFin) {
        try {
            logger.debug("Inicio de método consultarHistoricoCicloManual(Long,Long)");
            Date inicioFecha = CalendarUtils.truncarHora(new Date(fechaInicio));
            Date finFecha = CalendarUtils.truncarHoraMaxima(new Date(fechaFin));
            List<CicloCarteraModeloDTO> ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_MANUAL_POR_FECHA).setParameter("fechaInicio", inicioFecha).setParameter("fechaFin", finFecha).setParameter(TIPO_CICLO, TipoCicloEnum.GESTION_MANUAL).getResultList();
            logger.debug("Fin de método consultarHistoricoCicloManual(Long,Long)");
            return ciclos;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarHistoricoCicloManual", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesTraspasoDeudaAntigua(com.asopagos.cartera.dto.
     * FiltrosGestionCobroManualDTO)
     */
    @Override
    public AportantesTraspasoDeudaDTO consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO
                                                                                      aportantesTraspaso, UriInfo uri, HttpServletResponse response) {
        try {
            logger.debug("Inicio de método consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso)");

            MultivaluedMap<String, String> parametros = new MultivaluedHashMap<>();
            if (aportantesTraspaso.getParams() != null) {
                for (Entry<String, List<String>> e : aportantesTraspaso.getParams().entrySet()) {
                    parametros.put(e.getKey(), e.getValue());
                }
            }

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, parametros, response);
            Query query = null;

            List<AportanteGestionManualDTO> aportantes = new ArrayList<>();
            List<TipoLineaCobroEnum> lineasCobro = new ArrayList<TipoLineaCobroEnum>();
            List<TipoAccionCobroEnum> accionesCobro = new ArrayList<TipoAccionCobroEnum>();

            AportantesTraspasoDeudaDTO aportantesTraspasoDeuda = new AportantesTraspasoDeudaDTO();

            String consulta = null;

            if (aportantesTraspaso.getIdPersonas() != null && !aportantesTraspaso.getIdPersonas().isEmpty()) {
                consulta = NamedQueriesConstants.CONSULTAR_APORTANTES_TRASLADO_DEUDA_ANTIGUA_PERSONAS;
                queryBuilder.addParam("idPersonas", aportantesTraspaso.getIdPersonas());
            } else {
                consulta = NamedQueriesConstants.CONSULTAR_APORTANTES_TRASLADO_DEUDA_ANTIGUA;
            }

            if (aportantesTraspaso.getTipoSolicitante() == TipoSolicitanteMovimientoAporteEnum.EMPLEADOR) {
                lineasCobro.add(TipoLineaCobroEnum.LC1);
                accionesCobro.add(TipoAccionCobroEnum.F1);
                accionesCobro.add(TipoAccionCobroEnum.G1);
                accionesCobro.add(TipoAccionCobroEnum.G2);
                accionesCobro.add(TipoAccionCobroEnum.I2);
            } else if (aportantesTraspaso.getTipoSolicitante() == TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE) {
                lineasCobro.add(TipoLineaCobroEnum.LC4);
                accionesCobro.add(TipoAccionCobroEnum.LC4B);
                accionesCobro.add(TipoAccionCobroEnum.LC4C);
            } else {
                lineasCobro.add(TipoLineaCobroEnum.LC5);
                accionesCobro.add(TipoAccionCobroEnum.LC5B);
                accionesCobro.add(TipoAccionCobroEnum.LC5C);
            }

            queryBuilder.addParam("lineasCobro", lineasCobro);
            queryBuilder.addParam("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE);
            queryBuilder.addParam("accionesCobro", accionesCobro);
            query = queryBuilder.createQuery(consulta, null);

            aportantes = (List<AportanteGestionManualDTO>) query.getResultList();

            // Incluye invocación a lógica de validación para traspaso a deuda antigua
            List<AportanteGestionManualDTO> listaFinalAportantes = new ArrayList<AportanteGestionManualDTO>();

            for (AportanteGestionManualDTO aportante : aportantes) {
                List<String> respuesta = validarTraspasoCartera(aportante.getTipoSolicitante(), aportante.getTipoIdentificacion(), aportante.getNumeroIdentificacion(), aportante.getAccionCobro());

                if (respuesta.size() == 0) {
                    listaFinalAportantes.add(aportante);
                }
            }

            aportantesTraspasoDeuda.setAportantes(listaFinalAportantes);
            aportantesTraspasoDeuda.setTotalRecords(listaFinalAportantes.size());
            logger.debug("Fin de método consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso)");
            return aportantesTraspasoDeuda;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO aportantesTraspaso)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetalleAportantes(com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum,
     * com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PanelCarteraDTO> consultarDetalleAportantes(TipoLineaCobroEnum
                                                                              lineaCobro, TipoAccionCobroEnum accionCobro, String usuarioAnalista, String filtroNumIdentificacion, UriInfo uri, HttpServletResponse response) {
        try {
            logger.info("Inicio de método consultarDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum accionCobro)");

            logger.info("UriInfo query " + uri.getQueryParameters());
            MultivaluedMap<String, String> queryParamsCompleto = uri.getQueryParameters();
            int offset = 0;
            int rows = 0;
            String filtros = "df";
            for (String key : queryParamsCompleto.keySet()) {
                StringBuilder resultado = new StringBuilder();
                List<String> values = queryParamsCompleto.get(key);
                resultado.append("Parámetro: ").append(key).append(", Valores: ").append(values).append("\n");
                logger.info("UriInfo query values: " + resultado.toString());
                if ("offset".equals(key) && !values.isEmpty()) {
                    // Intenta convertir el primer valor a int, maneja la excepción si no es posible
                    try {
                        offset = Integer.parseInt(values.get(0));
                    } catch (NumberFormatException e) {
                        logger.warn("El valor proporcionado para 'offset' no es un número válido: " + values.get(0));
                    }
                }
                if ("limit".equals(key) && !values.isEmpty()) {
                    // Intenta convertir el primer valor a int, maneja la excepción si no es posible
                    try {
                        rows = Integer.parseInt(values.get(0));
                    } catch (NumberFormatException e) {
                        logger.warn("El valor proporcionado para 'limit' no es un número válido: " + values.get(0));
                    }
                }

                if ("orderby".equals(key) && !values.isEmpty()) {
                    filtros = values.get(0);
                }
            }

            // Verifica si accionCobro o lineaCobro son nulos antes de acceder a name()
            String accionCobroParam = (accionCobro != null) ? accionCobro.name() : "null";
            String lineaCobroParam = (lineaCobro != null) ? lineaCobro.name() : "null";

            List<PanelCarteraDTO> aportantes = null;
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CARTERA_PANEL)
                .setParameter("accioncobro", accionCobroParam)
                .setParameter("lineaCobro", lineaCobroParam)    
                .setParameter("usuarioAnalista", usuarioAnalista == null || usuarioAnalista.isEmpty() ? "null" : usuarioAnalista)
                .setParameter("identificacion", filtroNumIdentificacion == null || filtroNumIdentificacion.isEmpty() ? "null" : filtroNumIdentificacion)
                .setParameter("offset", offset).setParameter("rows", rows).setParameter("filtros", filtros);
            query.execute();
            aportantes = query.getResultList();

            int conteo = 0;
            StoredProcedureQuery queryC = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CARTERA_PANEL_CONTEO)
                .setParameter("accioncobro", accionCobroParam)
                .setParameter("lineaCobro", lineaCobroParam)            
                .setParameter("usuarioAnalista", usuarioAnalista == null || usuarioAnalista.isEmpty() ? "null" : usuarioAnalista)
                .setParameter("identificacion", filtroNumIdentificacion == null || filtroNumIdentificacion.isEmpty() ? "null" : filtroNumIdentificacion);
            queryC.execute();
            // Asumiendo que el conteo es el único resultado, puedes obtenerlo directamente
            conteo = (Integer) queryC.getSingleResult();
            response.addHeader("totalrecords", String.valueOf(conteo));
            response.addHeader("recordsFiltered", String.valueOf(rows));

            return aportantes;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum accionCobro)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public byte[] exportExcelConsultarDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum
            accionCobro, String usuarioAnalista, UriInfo uri, HttpServletResponse response) throws IOException {
        try {
            logger.info("exportExcelConsultarDetalleAportantes");
            List<PanelCarteraDTO> aportantes = consultarDetalleAportantes(lineaCobro, accionCobro, usuarioAnalista, null, uri, response);
            List<AportanteGestionManualDTO> listAportanteGestionManualDTO = aportantes.stream().map(aportante -> {
                AportanteGestionManualDTO dto = new AportanteGestionManualDTO();
                    dto.setIdCartera(aportante.getNumeroRadicado());
                    dto.setAccionCobro(TipoAccionCobroEnum.valueOf(aportante.getAccionCobro()));
                    dto.setLineaCobro(TipoLineaCobroEnum.valueOf(aportante.getLineaCobro()));
                    dto.setTipoDeuda(TipoDeudaEnum.valueOf(aportante.getTipoDeuda()));
                    dto.setMontoDeuda(aportante.getMontoDeuda());
                    dto.setEstadoCartera(EstadoCarteraEnum.valueOf(aportante.getEstadoCartera()));
                    dto.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf(aportante.getTipoSolicitante()));
                    dto.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(aportante.getTipoIdentificacion()));
                    dto.setNumeroIdentificacion(aportante.getNumeroIdentificacion());
                    return dto;
                }).collect(Collectors.toList());
            logger.info("List : aportantes" + listAportanteGestionManualDTO.size());
            List<String[]> dataList = new ArrayList<String[]>();
            String[] encabezado = {"No.", "Tipo Identificación", "Número de identificación", "Nombre / Razón social", "Tipo de solicitante", "Estado de cartera", "Deuda", "Monto", "Tipo de deuda", "Línea de cobro", "Acción de cobro"};
            List<String[]> encabezadoList = new ArrayList<String[]>();
            encabezadoList.add(encabezado);
            int cont = 0;
            for (AportanteGestionManualDTO dto : listAportanteGestionManualDTO) {
                cont += 1;
                String[] data = {String.valueOf(cont), dto.getTipoIdentificacion().getDescripcion(), dto.getNumeroIdentificacion(), dto.getNombreCompleto(), dto.getTipoSolicitante().getDescripcion(), dto.getEstadoCartera().getDescripcion(), dto.getTipoDeuda().getDescripcion(), String.valueOf(dto.getMontoDeuda()), dto.getDeuda().getDescripcion(), dto.getLineaCobro().getDescripcion(), dto.getAccionCobro().getDescripcion()};
                dataList.add(data);
            }
            byte[] dataReporte = generarNuevoArchivoExcel(encabezadoList, dataList);
            logger.info("dataReporte :: " + dataReporte);

            return dataReporte;
        } catch (Exception e) {
            logger.info("error genarting excel :: " + e.getMessage());
            throw e;
        }
    }

    /**
     * metodo que genera un excel apartir del encabezado y la data
     *
     * @param encabezado
     * @param data
     * @return retorna el archivo excel en arreglo de bytes
     **/
    public static byte[] generarNuevoArchivoExcel(List<String[]> encabezado, List<String[]> data) throws
            IOException {
        //TO-DO Usar Apache POI paara crar este archivo en formato XSLX
        try {
            //InputStream ist = new FileInputStream();
            XSSFWorkbook libro = new XSSFWorkbook();
            XSSFSheet pagina = libro.createSheet("reporte");

            int indiceRow = 0;
            int indiceColumn = 0;

            if (encabezado != null && encabezado.size() > 1) {
                //0,first row (0-based); 0,last row  (0-based) ; 0, first column (0-based);  last column  (0-based)
                CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn, encabezado.get(1).length);
                pagina.addMergedRegion(mergedCell);
                indiceRow++;
            }

            if (encabezado != null) {
                //Generación del encabezado del reporte
                XSSFRow filaIterEncabezadoAportante = pagina.createRow(indiceRow);
                for (int i = 0; i < encabezado.size(); i++) {
                    for (String encAportante : encabezado.get(i)) {
                        XSSFCell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
                        celdaIterEncabezadoAportante.setCellValue(encAportante);
                        indiceColumn++;
                    }
                    indiceRow++;
                }
            }
            //Generación de cada registro por fila
            for (Object[] items : data) {
                indiceColumn = 0;
                XSSFRow filaIterAportante = pagina.createRow(indiceRow);
                for (Object dato : items) {
                    XSSFCell celdaIterAportante = filaIterAportante.createCell(indiceColumn);

                    Integer numero = null;
                    if (dato != null && dato instanceof BigDecimal) {
                        numero = BigDecimal.valueOf(Double.valueOf(dato.toString())).toBigInteger().intValue();
                    } else if (dato != null && dato instanceof Integer) {
                        numero = Integer.valueOf(dato.toString());
                    }

                    if (numero != null) {
                        celdaIterAportante.setCellType(CellType.NUMERIC);
                        celdaIterAportante.setCellValue(numero.longValue());

                    } else {
                        celdaIterAportante.setCellValue(dato != null ? dato.toString() : "");
                    }

                    indiceColumn++;
                }
                //se aumenta el número de la fila para almacenar el otro registro
                indiceRow++;
            }

            ByteArrayOutputStream archivo = new ByteArrayOutputStream();
            try {
                // Almacenamos el libro de Excel via ese flujo de datos
                libro.write(archivo);
                libro.close();
            } catch (IOException e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e + " En el cierre del archivo para la generación del Excel");
            }
            return archivo.toByteArray();
        } catch (Exception e) {
            throw e;
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.cartera.service.CarteraService#consultarTotalDetalleAportantes(com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum,
     * com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object[] consultarTotalDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum
            accionCobro, String usuarioAnalista) {
        Object[] detalle = null;

        try {
            logger.debug("Inicio de método consultarTotalDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum accionCobro,String usuarioAnalista)");

            if (accionCobro != null) {
                List<TipoAccionCobroEnum> tipoAccionCobro = new ArrayList<>();
                tipoAccionCobro.add(TipoAccionCobroEnum.G1);
                tipoAccionCobro.add(TipoAccionCobroEnum.I2);
                tipoAccionCobro.add(TipoAccionCobroEnum.LC2B);
                tipoAccionCobro.add(TipoAccionCobroEnum.LC3B);
                tipoAccionCobro.add(TipoAccionCobroEnum.LC4B);
                tipoAccionCobro.add(TipoAccionCobroEnum.LC5B);

                if (tipoAccionCobro.contains(accionCobro)) {
                    List<String> estados = new ArrayList<>();
                    estados.add(EstadoFiscalizacionEnum.ASIGNADO.name());
                    estados.add(EstadoFiscalizacionEnum.EN_PROCESO.name());

                    if (usuarioAnalista != null) {
                        detalle = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_CICLO_USUARIO_ANALISTA).setParameter("lineaCobro", lineaCobro.name()).setParameter("usuarioAnalista", usuarioAnalista).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO.name()).setParameter("estados", estados).getSingleResult();
                    } else {
                        detalle = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_CICLO).setParameter("lineaCobro", lineaCobro.name()).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO.name()).setParameter("estados", estados).getSingleResult();
                    }
                } else {
                    if(lineaCobro.equals(TipoLineaCobroEnum.LC2)){
                        List<String> acciones = new ArrayList<String>();
                        acciones.add(accionCobro.name());
                        acciones.add(obtenerAccionIntermedia(accionCobro).name());
                        detalle = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_ACCION_COBRO_LC2)
                        .setParameter("linea", lineaCobro.name())
                        .setParameter("accionCobro", acciones)
                        .setParameter("incluirNull", 0)
                        .setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name()).getSingleResult();
                        

                    }else{
                        // logger.info("2g entra accionCobro "+accionCobro);

                        // logger.info("2g entra 4");
    
                        List<String> acciones = new ArrayList<String>();
                        acciones.add(accionCobro.name());
                        acciones.add(obtenerAccionIntermedia(accionCobro).name());
                        detalle = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_ACCION_COBRO).setParameter("lineaCobro", lineaCobro.name()).setParameter("accionCobro", acciones).setParameter("incluirNull", 0).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name()).getSingleResult();

                    }
                }
            } else {
                List<String> acciones = new ArrayList<String>();

                switch (lineaCobro) {
                    case C6:
                        acciones.add(TipoAccionCobroEnum.A1.name());
                        acciones.add(TipoAccionCobroEnum.B1.name());
                        acciones.add(TipoAccionCobroEnum.C1.name());
                        acciones.add(TipoAccionCobroEnum.D1.name());
                        acciones.add(TipoAccionCobroEnum.E1.name());
                        acciones.add(TipoAccionCobroEnum.F1.name());
                        acciones.add(TipoAccionCobroEnum.A2.name());
                        acciones.add(TipoAccionCobroEnum.B2.name());
                        acciones.add(TipoAccionCobroEnum.C2.name());
                        acciones.add(TipoAccionCobroEnum.D2.name());
                        acciones.add(TipoAccionCobroEnum.E2.name());
                        acciones.add(TipoAccionCobroEnum.F2.name());
                        acciones.add(TipoAccionCobroEnum.G2.name());
                        acciones.add(TipoAccionCobroEnum.H2.name());

                        acciones.add(TipoAccionCobroEnum.A01.name());
                        acciones.add(TipoAccionCobroEnum.AB1.name());
                        acciones.add(TipoAccionCobroEnum.BC1.name());
                        acciones.add(TipoAccionCobroEnum.CD1.name());
                        acciones.add(TipoAccionCobroEnum.DE1.name());
                        acciones.add(TipoAccionCobroEnum.EF1.name());
                        acciones.add(TipoAccionCobroEnum.A02.name());
                        acciones.add(TipoAccionCobroEnum.AB2.name());
                        acciones.add(TipoAccionCobroEnum.BC2.name());
                        acciones.add(TipoAccionCobroEnum.CD2.name());
                        acciones.add(TipoAccionCobroEnum.DE2.name());
                        acciones.add(TipoAccionCobroEnum.EF2.name());
                        acciones.add(TipoAccionCobroEnum.FG2.name());
                        acciones.add(TipoAccionCobroEnum.GH2.name());
                        break;
                    case C7:
                        acciones.add(TipoAccionCobroEnum.A1.name());
                        acciones.add(TipoAccionCobroEnum.B1.name());
                        acciones.add(TipoAccionCobroEnum.C1.name());
                        acciones.add(TipoAccionCobroEnum.D1.name());
                        acciones.add(TipoAccionCobroEnum.E1.name());
                        acciones.add(TipoAccionCobroEnum.F1.name());
                        acciones.add(TipoAccionCobroEnum.A2.name());
                        acciones.add(TipoAccionCobroEnum.B2.name());
                        acciones.add(TipoAccionCobroEnum.C2.name());
                        acciones.add(TipoAccionCobroEnum.D2.name());
                        acciones.add(TipoAccionCobroEnum.E2.name());
                        acciones.add(TipoAccionCobroEnum.F2.name());
                        acciones.add(TipoAccionCobroEnum.G2.name());
                        acciones.add(TipoAccionCobroEnum.H2.name());

                        acciones.add(TipoAccionCobroEnum.A01.name());
                        acciones.add(TipoAccionCobroEnum.AB1.name());
                        acciones.add(TipoAccionCobroEnum.BC1.name());
                        acciones.add(TipoAccionCobroEnum.CD1.name());
                        acciones.add(TipoAccionCobroEnum.DE1.name());
                        acciones.add(TipoAccionCobroEnum.EF1.name());
                        acciones.add(TipoAccionCobroEnum.A02.name());
                        acciones.add(TipoAccionCobroEnum.AB2.name());
                        acciones.add(TipoAccionCobroEnum.BC2.name());
                        acciones.add(TipoAccionCobroEnum.CD2.name());
                        acciones.add(TipoAccionCobroEnum.DE2.name());
                        acciones.add(TipoAccionCobroEnum.EF2.name());
                        acciones.add(TipoAccionCobroEnum.FG2.name());
                        acciones.add(TipoAccionCobroEnum.GH2.name());
                        // acciones.add(TipoAccionCobroEnum.LC4A.name());
                        // acciones.add(TipoAccionCobroEnum.LC4B.name());
                        // acciones.add(TipoAccionCobroEnum.LC4C.name());

                        // acciones.add(TipoAccionCobroEnum.LC40.name());
                        break;
                    case C8:
                        acciones.add(TipoAccionCobroEnum.A1.name());
                        acciones.add(TipoAccionCobroEnum.B1.name());
                        acciones.add(TipoAccionCobroEnum.C1.name());
                        acciones.add(TipoAccionCobroEnum.D1.name());
                        acciones.add(TipoAccionCobroEnum.E1.name());
                        acciones.add(TipoAccionCobroEnum.F1.name());
                        acciones.add(TipoAccionCobroEnum.A2.name());
                        acciones.add(TipoAccionCobroEnum.B2.name());
                        acciones.add(TipoAccionCobroEnum.C2.name());
                        acciones.add(TipoAccionCobroEnum.D2.name());
                        acciones.add(TipoAccionCobroEnum.E2.name());
                        acciones.add(TipoAccionCobroEnum.F2.name());
                        acciones.add(TipoAccionCobroEnum.G2.name());
                        acciones.add(TipoAccionCobroEnum.H2.name());

                        acciones.add(TipoAccionCobroEnum.A01.name());
                        acciones.add(TipoAccionCobroEnum.AB1.name());
                        acciones.add(TipoAccionCobroEnum.BC1.name());
                        acciones.add(TipoAccionCobroEnum.CD1.name());
                        acciones.add(TipoAccionCobroEnum.DE1.name());
                        acciones.add(TipoAccionCobroEnum.EF1.name());
                        acciones.add(TipoAccionCobroEnum.A02.name());
                        acciones.add(TipoAccionCobroEnum.AB2.name());
                        acciones.add(TipoAccionCobroEnum.BC2.name());
                        acciones.add(TipoAccionCobroEnum.CD2.name());
                        acciones.add(TipoAccionCobroEnum.DE2.name());
                        acciones.add(TipoAccionCobroEnum.EF2.name());
                        acciones.add(TipoAccionCobroEnum.FG2.name());
                        acciones.add(TipoAccionCobroEnum.GH2.name());
                        // acciones.add(TipoAccionCobroEnum.LC5A.name());
                        // acciones.add(TipoAccionCobroEnum.LC5B.name());
                        // acciones.add(TipoAccionCobroEnum.LC5C.name());

                        // acciones.add(TipoAccionCobroEnum.LC50.name());
                        break;
                    default:
                        break;
                }

                if (!acciones.isEmpty()) {
                    detalle = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_ACCION_COBRO).setParameter("lineaCobro", lineaCobro.name()).setParameter("accionCobro", acciones).setParameter("incluirNull", 1).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name()).getSingleResult();
                }
            }

            logger.debug("Fin de método consultarTotalDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum accionCobro,String usuarioAnalista)");
            return detalle;
        } catch (NoResultException nre) {
            logger.debug("No hay valor para realizar el total de aportantes en la linea de cobro" + lineaCobro.name());
            return detalle;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarTotalDetalleAportantes(TipoLineaCobroEnum lineaCobro, TipoAccionCobroEnum accionCobro,String usuarioAnalista)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarCriteriosGestionCobro(java.util.List)
     */
    @Override
    public void guardarCriteriosGestionCobro(MetodoAccionCobroEnum
                                                     metodo, List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios) {
        logger.debug("Inicio de servicio guardarCriteriosGestionCobro");
        ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion = null;
        ParametrizacionCriterioGestionCobro parametrizacionCriterioGestion = null;
        TipoParametrizacionCarteraEnum tipoParametrizacion = TipoParametrizacionCarteraEnum.GESTION_COBRO;
        for (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacionDTO : parametrizacionCriterios) {
            parametrizacionDTO.setTipoParametrizacion(tipoParametrizacion);
            if (TipoLineaCobroEnum.LC1.equals(parametrizacionDTO.getLineaCobro())) {
                parametrizacionDTO.setMetodo(metodo);
            }
            parametrizacion = consultarCriteriosGestionCobro(parametrizacionDTO.getLineaCobro(), parametrizacionDTO.getAccion(), parametrizacionDTO.getMetodo());
            parametrizacionCriterioGestion = parametrizacionDTO.convertToEntity();
            if (parametrizacionCriterioGestion != null && parametrizacion != null) {
                parametrizacionCriterioGestion.setIdParametrizacionCartera(parametrizacion.getIdParametrizacionCartera());
            }
            guardarParametrizacionCriterioGestionCobro(parametrizacionCriterioGestion);
        }
        logger.debug("Fin de servicio guardarCriteriosGestionCobro");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCriteriosGestionCobro(com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum,
     * com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum, com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ParametrizacionCriteriosGestionCobroModeloDTO consultarCriteriosGestionCobro(TipoLineaCobroEnum
                                                                                                lineaCobro, TipoGestionCarteraEnum accion, MetodoAccionCobroEnum metodo) {
        try {
            logger.debug("Inicio de servicio consultarCriteriosGestionCobro(TipoLineaCobroEnum lineaCobro, Boolean automatica,MetodoAccionCobroEnum metodo)");
            ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion = null;
            if (TipoLineaCobroEnum.LC1.equals(lineaCobro)) {
                /* si es línea de cobro 1 se consulta por linea y método */
                Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA_Y_METODO).setParameter(TIPO_LINEA_COBRO, lineaCobro).setParameter("metodo", metodo).setParameter("accionLineaCobro", accion);
                parametrizacion = (ParametrizacionCriteriosGestionCobroModeloDTO) q.getSingleResult();
            } else {
                /* si es línea de cobro diferente a LC1 se consulta por linea y accion */
                Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA_Y_ACCION).setParameter(TIPO_LINEA_COBRO, lineaCobro).setParameter("accionLineaCobro", accion);
                parametrizacion = (ParametrizacionCriteriosGestionCobroModeloDTO) q.getSingleResult();
            }
            logger.debug("Fin de servicio consultarCriteriosGestionCobro(TipoLineaCobroEnum lineaCobro, Boolean automatica,MetodoAccionCobroEnum metodo)");
            return parametrizacion;
        } catch (NoResultException nre) {
            logger.debug("No hay parametrizado un criterio para la linea de cobro" + lineaCobro.name());
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarCriteriosGestionCobro(TipoLineaCobroEnum lineaCobro, Boolean automatica,MetodoAccionCobroEnum metodo)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarTotalDetalleAportantesPanel(java.util.String)
     */
    @Override
    public GestionCarteraMoraDTO consultarTotalDetalleAportantesPanel(String usuarioAnalista) {
        try {
            logger.debug("Inicio de servicio consultarTotalDetalleAportantesPanel");
            GestionCarteraMoraDTO gestionCarteraMora = new GestionCarteraMoraDTO();

            //LÍNEA DE COBRO 1
            GestionLineaCobroDTO lineaCobro1 = new GestionLineaCobroDTO();
            MetodoAccionCobroEnum metodoActivoLC1 = consultarMetodoActivoLC1();
            List<TipoAccionCobroEnum> accionesLC1 = new ArrayList<>();

            if (MetodoAccionCobroEnum.METODO_1.equals(metodoActivoLC1)) {
                accionesLC1.add(TipoAccionCobroEnum.A1);
                accionesLC1.add(TipoAccionCobroEnum.B1);
                accionesLC1.add(TipoAccionCobroEnum.C1);
                accionesLC1.add(TipoAccionCobroEnum.D1);
                accionesLC1.add(TipoAccionCobroEnum.E1);
                accionesLC1.add(TipoAccionCobroEnum.F1);
                accionesLC1.add(TipoAccionCobroEnum.G1);
            } else if (MetodoAccionCobroEnum.METODO_2.equals(metodoActivoLC1)) {
                accionesLC1.add(TipoAccionCobroEnum.A2);
                accionesLC1.add(TipoAccionCobroEnum.B2);
                accionesLC1.add(TipoAccionCobroEnum.C2);
                accionesLC1.add(TipoAccionCobroEnum.D2);
                accionesLC1.add(TipoAccionCobroEnum.E2);
                accionesLC1.add(TipoAccionCobroEnum.F2);
                accionesLC1.add(TipoAccionCobroEnum.G2);
                accionesLC1.add(TipoAccionCobroEnum.H2);
                accionesLC1.add(TipoAccionCobroEnum.I2);
            }

            if (!accionesLC1.isEmpty()) {
                List<GestionDetalleCarteraMoraDTO> listaDetalleCarteraMoraL1 = new ArrayList<GestionDetalleCarteraMoraDTO>();

                for (TipoAccionCobroEnum accionLC1 : accionesLC1) {
                    Object[] aportantesLC1 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.LC1, accionLC1, usuarioAnalista);
                    GestionDetalleCarteraMoraDTO gestionL1 = new GestionDetalleCarteraMoraDTO();
                    gestionL1.setAccion(accionLC1);
                    gestionL1.setCantidad((Integer) aportantesLC1[1]);
                    listaDetalleCarteraMoraL1.add(gestionL1);
                }

                lineaCobro1.setMetodo(metodoActivoLC1);
                lineaCobro1.setDetallesCarteraMora(listaDetalleCarteraMoraL1);
                gestionCarteraMora.setLineaCobro1(lineaCobro1);
            }

            //LÍNEA DE COBRO 2
            GestionLineaCobroDTO lineaCobro2 = new GestionLineaCobroDTO();
            List<GestionDetalleCarteraMoraDTO> listaDetalleCarteraMoraL2 = new ArrayList<GestionDetalleCarteraMoraDTO>();
            List<TipoAccionCobroEnum> accionesLC2 = new ArrayList<>();
            accionesLC2.add(TipoAccionCobroEnum.LC2A);
            accionesLC2.add(TipoAccionCobroEnum.LC2B);

            for (TipoAccionCobroEnum accionLC2 : accionesLC2) {
                Object[] aportantesLC2 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.LC2, accionLC2, usuarioAnalista);
                GestionDetalleCarteraMoraDTO gestionL2 = new GestionDetalleCarteraMoraDTO();
                gestionL2.setAccion(accionLC2);
                gestionL2.setCantidad((Integer) aportantesLC2[1]);
                listaDetalleCarteraMoraL2.add(gestionL2);
            }

            lineaCobro2.setDetallesCarteraMora(listaDetalleCarteraMoraL2);
            gestionCarteraMora.setLineaCobro2(lineaCobro2);

            //LÍNEA DE COBRO 3
            GestionLineaCobroDTO lineaCobro3 = new GestionLineaCobroDTO();
            List<GestionDetalleCarteraMoraDTO> listaDetalleCarteraMoraL3 = new ArrayList<GestionDetalleCarteraMoraDTO>();
            List<TipoAccionCobroEnum> accionesLC3 = new ArrayList<>();
            accionesLC3.add(TipoAccionCobroEnum.LC3A);
            accionesLC3.add(TipoAccionCobroEnum.LC3B);

            for (TipoAccionCobroEnum accionLC3 : accionesLC3) {
                Object[] aportantesLC3 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.LC3, accionLC3, usuarioAnalista);
                GestionDetalleCarteraMoraDTO gestionL3 = new GestionDetalleCarteraMoraDTO();
                gestionL3.setAccion(accionLC3);
                gestionL3.setCantidad((Integer) aportantesLC3[1]);
                listaDetalleCarteraMoraL3.add(gestionL3);
            }

            lineaCobro3.setDetallesCarteraMora(listaDetalleCarteraMoraL3);
            gestionCarteraMora.setLineaCobro3(lineaCobro3);

            //LÍNEA DE COBRO 4
            GestionLineaCobroDTO lineaCobro4 = new GestionLineaCobroDTO();
            List<GestionDetalleCarteraMoraDTO> listaDetalleCarteraMoraL4 = new ArrayList<GestionDetalleCarteraMoraDTO>();
            List<TipoAccionCobroEnum> accionesLC4 = new ArrayList<>();
            // accionesLC4.add(TipoAccionCobroEnum.LC4A);
            // accionesLC4.add(TipoAccionCobroEnum.LC4B);
            // accionesLC4.add(TipoAccionCobroEnum.LC4C);
            if (MetodoAccionCobroEnum.METODO_1.equals(metodoActivoLC1)) {
                accionesLC4.add(TipoAccionCobroEnum.A1);
                accionesLC4.add(TipoAccionCobroEnum.B1);
                accionesLC4.add(TipoAccionCobroEnum.C1);
                accionesLC4.add(TipoAccionCobroEnum.D1);
                accionesLC4.add(TipoAccionCobroEnum.E1);
                accionesLC4.add(TipoAccionCobroEnum.F1);
                accionesLC4.add(TipoAccionCobroEnum.G1);
            } else if (MetodoAccionCobroEnum.METODO_2.equals(metodoActivoLC1)) {
                accionesLC4.add(TipoAccionCobroEnum.A2);
                accionesLC4.add(TipoAccionCobroEnum.B2);
                accionesLC4.add(TipoAccionCobroEnum.C2);
                accionesLC4.add(TipoAccionCobroEnum.D2);
                accionesLC4.add(TipoAccionCobroEnum.E2);
                accionesLC4.add(TipoAccionCobroEnum.F2);
                accionesLC4.add(TipoAccionCobroEnum.G2);
                accionesLC4.add(TipoAccionCobroEnum.H2);
                accionesLC4.add(TipoAccionCobroEnum.I2);
            }

            for (TipoAccionCobroEnum accionLC4 : accionesLC4) {
                Object[] aportantesLC4 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.LC4, accionLC4, usuarioAnalista);
                GestionDetalleCarteraMoraDTO gestionL4 = new GestionDetalleCarteraMoraDTO();
                gestionL4.setAccion(accionLC4);
                gestionL4.setCantidad((Integer) aportantesLC4[1]);
                listaDetalleCarteraMoraL4.add(gestionL4);
            }
            lineaCobro1.setMetodo(metodoActivoLC1);
            lineaCobro4.setDetallesCarteraMora(listaDetalleCarteraMoraL4);
            gestionCarteraMora.setLineaCobro4(lineaCobro4);

            //LÍNEA DE COBRO 5
            GestionLineaCobroDTO lineaCobro5 = new GestionLineaCobroDTO();
            List<GestionDetalleCarteraMoraDTO> listaDetalleCarteraMoraL5 = new ArrayList<GestionDetalleCarteraMoraDTO>();
            List<TipoAccionCobroEnum> accionesLC5 = new ArrayList<>();
            // accionesLC5.add(TipoAccionCobroEnum.LC5A);
            // accionesLC5.add(TipoAccionCobroEnum.LC5B);
            // accionesLC5.add(TipoAccionCobroEnum.LC5C);
            if (MetodoAccionCobroEnum.METODO_1.equals(metodoActivoLC1)) {
                accionesLC5.add(TipoAccionCobroEnum.A1);
                accionesLC5.add(TipoAccionCobroEnum.B1);
                accionesLC5.add(TipoAccionCobroEnum.C1);
                accionesLC5.add(TipoAccionCobroEnum.D1);
                accionesLC5.add(TipoAccionCobroEnum.E1);
                accionesLC5.add(TipoAccionCobroEnum.F1);
                accionesLC5.add(TipoAccionCobroEnum.G1);
            } else if (MetodoAccionCobroEnum.METODO_2.equals(metodoActivoLC1)) {
                accionesLC5.add(TipoAccionCobroEnum.A2);
                accionesLC5.add(TipoAccionCobroEnum.B2);
                accionesLC5.add(TipoAccionCobroEnum.C2);
                accionesLC5.add(TipoAccionCobroEnum.D2);
                accionesLC5.add(TipoAccionCobroEnum.E2);
                accionesLC5.add(TipoAccionCobroEnum.F2);
                accionesLC5.add(TipoAccionCobroEnum.G2);
                accionesLC5.add(TipoAccionCobroEnum.H2);
                accionesLC5.add(TipoAccionCobroEnum.I2);
            }

            for (TipoAccionCobroEnum accionLC5 : accionesLC5) {
                Object[] aportantesLC5 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.LC5, accionLC5, usuarioAnalista);
                GestionDetalleCarteraMoraDTO gestionL5 = new GestionDetalleCarteraMoraDTO();
                gestionL5.setAccion(accionLC5);
                gestionL5.setCantidad((Integer) aportantesLC5[1]);
                listaDetalleCarteraMoraL5.add(gestionL5);
            }
            lineaCobro1.setMetodo(metodoActivoLC1);
            lineaCobro5.setDetallesCarteraMora(listaDetalleCarteraMoraL5);
            gestionCarteraMora.setLineaCobro5(lineaCobro5);

            //DEUDA ANTIGUA C6
            GestionDetalleCarteraMoraDTO deudaAntiguaC6 = new GestionDetalleCarteraMoraDTO();
            Object[] aportantesC6 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.C6, null, usuarioAnalista);
            deudaAntiguaC6.setDeuda(aportantesC6[0] != null ? new BigDecimal(aportantesC6[0].toString()) : null);
            deudaAntiguaC6.setCantidad((Integer) aportantesC6[1]);
            gestionCarteraMora.setDeudaAntiguaC6(deudaAntiguaC6);

            //DEUDA ANTIGUA C7
            GestionDetalleCarteraMoraDTO deudaAntiguaC7 = new GestionDetalleCarteraMoraDTO();
            Object[] aportantesC7 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.C7, null, usuarioAnalista);
            deudaAntiguaC7.setDeuda(aportantesC7[0] != null ? new BigDecimal(aportantesC7[0].toString()) : null);
            deudaAntiguaC7.setCantidad((Integer) aportantesC7[1]);
            gestionCarteraMora.setDeudaAntiguaC7(deudaAntiguaC7);

            //DEUDA ANTIGUA C8
            GestionDetalleCarteraMoraDTO deudaAntiguaC8 = new GestionDetalleCarteraMoraDTO();
            Object[] aportantesC8 = consultarTotalDetalleAportantes(TipoLineaCobroEnum.C8, null, usuarioAnalista);
            deudaAntiguaC8.setDeuda(aportantesC8[0] != null ? new BigDecimal(aportantesC8[0].toString()) : null);
            deudaAntiguaC8.setCantidad((Integer) aportantesC8[1]);
            gestionCarteraMora.setDeudaAntiguaC8(deudaAntiguaC8);

            logger.debug("Finalización de servicio consultarTotalDetalleAportantesPanel");
            return gestionCarteraMora;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarTotalDetalleAportantesPanel", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que obtiene la acción intermedia de una acción de cobro
     *
     * @param accion Acción de cobro en la que está el aportante en cartera
     * @return La acción intermedia correspondiente
     */
    private TipoAccionCobroEnum obtenerAccionIntermedia(TipoAccionCobroEnum accion) {
        logger.debug("Inicia método obtenerAccionIntermedia");
        TipoAccionCobroEnum resultado = accion;

        switch (accion) {
            case A1:
                resultado = TipoAccionCobroEnum.AB1;
                break;
            case B1:
                resultado = TipoAccionCobroEnum.BC1;
                break;
            case C1:
                resultado = TipoAccionCobroEnum.CD1;
                break;
            case D1:
                resultado = TipoAccionCobroEnum.DE1;
                break;
            case E1:
                resultado = TipoAccionCobroEnum.EF1;
                break;
            case A2:
                resultado = TipoAccionCobroEnum.AB2;
                break;
            case B2:
                resultado = TipoAccionCobroEnum.BC2;
                break;
            case C2:
                resultado = TipoAccionCobroEnum.CD2;
                break;
            case D2:
                resultado = TipoAccionCobroEnum.DE2;
                break;
            case E2:
                resultado = TipoAccionCobroEnum.EF2;
                break;
            case F2:
                resultado = TipoAccionCobroEnum.FG2;
                break;
            case G2:
                resultado = TipoAccionCobroEnum.GH2;
                break;
            case LC4A:
                resultado = TipoAccionCobroEnum.L4AC;
                break;
            case LC5A:
                resultado = TipoAccionCobroEnum.L5AC;
                break;
            default:
        }

        logger.debug("Finaliza método obtenerAccionIntermedia");
        return resultado;
    }

    /* Fin proceso 22 */
    /* Inicio proceso 223 */

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarEnvioComunicadoElectronico(java.lang.String)
     */
    @Override
    public Boolean validarEnvioComunicadoElectronico(String numeroRadicacion, TipoAccionCobroEnum
            tipoAccionCobro, Long idCartera) {
        try {
            logger.debug("Inicio de método que validarEnvioComunicadoElectronico(String)");
            Boolean respuesta = false;
            List<String> lstTipoExclusiones = new ArrayList<>();
            lstTipoExclusiones.add(TipoExclusionCarteraEnum.IMPOSICION_RECURSO.toString());
            lstTipoExclusiones.add(TipoExclusionCarteraEnum.ACLARACION_MORA.toString());
            if (TipoAccionCobroEnum.A1.equals(tipoAccionCobro) || TipoAccionCobroEnum.A2.equals(tipoAccionCobro)) {
                List<SolicitudGestionCobroElectronico> se = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTIONCOBRO_ELECTRONICO_CONSULTAR_ACCION_COBRO_POSTERIOR).setParameter("tipoAccioCobro", tipoAccionCobro).setParameter("estadoCartera", EstadoCarteraEnum.MOROSO).getResultList();
                /* Si la solicitud de gestion electronica es null no se envia el comunicado */
                if (se == null || se.isEmpty()) {
                    return respuesta;
                }
            }
            /* Se valida si hay notificacion personal el ultimo periodo en mora */
            if (TipoAccionCobroEnum.D1.equals(tipoAccionCobro) || TipoAccionCobroEnum.D2.equals(tipoAccionCobro) || TipoAccionCobroEnum.F2.equals(tipoAccionCobro) || TipoAccionCobroEnum.G2.equals(tipoAccionCobro)) {

                int cantidadNotificacionPersonal = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_NOTIFICACION_PERSONAL_CONSULTAR_NOTIFICACION_PERSONAL_ULTIMO_PERIODO_MORA).setParameter("idCartera", idCartera).getSingleResult()).intValue();
                /* Si hay un registro no se envia el comunicado */
                if (cantidadNotificacionPersonal > 0) {
                    return respuesta;
                }
            }

            lstTipoExclusiones = verificarSolicitudElectronicoIncobrabilidad(lstTipoExclusiones, tipoAccionCobro);
            List<Object[]> solicitudGestionCobroElectronico = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_ELECTRONICO_POR_EXCLUSIONES).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("exclusiones", lstTipoExclusiones).setParameter("estadoConvenio", EstadoConvenioPagoEnum.ACTIVO.toString()).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.toString()).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA.toString()).setParameter("idCartera", idCartera).getResultList();
            if (solicitudGestionCobroElectronico.isEmpty()) {
                respuesta = true;
            }
            logger.debug("Fin de método que validarEnvioComunicadoElectronico(String)");
            return respuesta;
        } catch (Exception e) {
            logger.error("Ocurrió un error en validarEnvioComunicadoElectronico(String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarEnvioComunicadoFisico(java.lang.String)
     */
    @Override
    public Boolean validarEnvioComunicadoFisico(String numeroRadicacion, TipoAccionCobroEnum tipoAccionCobro, Long
            idCartera) {
        try {
            logger.debug("Inicio de método que validarEnvioComunicadoFisico(String)");
            Boolean respuesta = false;
            List<String> lstTipoExclusiones = new ArrayList<>();
            lstTipoExclusiones.add(TipoExclusionCarteraEnum.IMPOSICION_RECURSO.toString());
            lstTipoExclusiones.add(TipoExclusionCarteraEnum.ACLARACION_MORA.toString());

            /* Se valida si hay notificacion personal el ultimo periodo en mora */
            if (TipoAccionCobroEnum.D2.equals(tipoAccionCobro) || TipoAccionCobroEnum.F2.equals(tipoAccionCobro) || TipoAccionCobroEnum.G2.equals(tipoAccionCobro)) {

                int cantidadNotificacionPersonal = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_NOTIFICACION_PERSONAL_CONSULTAR_NOTIFICACION_PERSONAL_ULTIMO_PERIODO_MORA).setParameter("idCartera", idCartera).getSingleResult()).intValue();
                /* Si hay un registro no se envia el comunicado */
                if (cantidadNotificacionPersonal > 0) {
                    return respuesta;
                }
            }

            //verificar riesgo de incobrabilidad en la solicitud
            lstTipoExclusiones = verificarSolicitudFisicaIncobrabilidad(numeroRadicacion, lstTipoExclusiones);
            List<Object[]> solicitudGestionCobroFisico = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_FISICO_POR_EXCLUSIONES).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("exclusiones", lstTipoExclusiones).setParameter("estadoConvenio", EstadoConvenioPagoEnum.ACTIVO.toString()).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA.toString()).setParameter("idCartera", idCartera).getResultList();
            if (solicitudGestionCobroFisico.isEmpty()) {
                respuesta = true;
            }
            logger.debug("Fin de método que validarEnvioComunicadoFisico(String)");
            return respuesta;
        } catch (Exception e) {
            logger.error("Ocurrió un error en validarEnvioComunicadoFisico(String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarComunicadoGenerado(java.lang.String,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public String consultarComunicadoGenerado(String numeroRadicacion, TipoIdentificacionEnum
            tipoIdentificacion, String numeroIdentificacion) {
        try {
            logger.debug("Inicio de servicio consultarComunicadoGenerado(String numeroRadicacion, TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
            String identificador = (String) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_IDENTIFICADOR_DOCUMENTO_CONSULTAR_POR_SOLICITUD_APORTANTE).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
            logger.debug("Fin de servicio consultarComunicadoGenerado(String numeroRadicacion, TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
            return "\"" + identificador + "\"";
        } catch (NoResultException nre) {
            logger.debug("No hay documento");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesPrimeraRemision(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesPrimeraRemision(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroRemisionAportantesDTO consultarAportantesPrimeraRemision(String
                                                                                    numeroRadicacion, List<Long> municipios) {
        try {
            logger.debug("Inicio de servicio consultarAportantesPrimeraRemision(String numeroRadicacion)");
            StringBuilder consulta = new StringBuilder();
            consulta.append(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES);

            if (municipios != null && !municipios.isEmpty()) {
                consulta.append(" order by CASE ubimunicipio");
                for (int i = 1; i <= municipios.size(); i++) {
                    consulta.append(" WHEN " + municipios.get(i - 1) + " THEN " + i);
                }
                consulta.append(" else " + municipios.size() + 1 + " END");
            } else {
                consulta.append(" order by ubimunicipio");
            }
            List<Object[]> aportantes = entityManager.createNativeQuery(consulta.toString()).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("tipoUbicacion", TipoUbicacionEnum.NOTIFICACION_JUDICIAL.name()).getResultList();
            List<AportanteRemisionComunicadoDTO> aportantesDTO = new ArrayList<>();
            if (!aportantes.isEmpty()) {
                for (Object[] aportante : aportantes) {
                    AportanteRemisionComunicadoDTO aportanteRemision = new AportanteRemisionComunicadoDTO();
                    aportanteRemision.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportante[0]));
                    aportanteRemision.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[1]));
                    aportanteRemision.setNumeroIdentificacion((String) aportante[2]);
                    aportanteRemision.setNombreCompleto(aportante[3] != null ? (String) aportante[3] : null);
                    aportanteRemision.setPeriodo(((Date) aportante[4]).getTime());
                    aportanteRemision.setDestinatario(aportante[5] != null ? (String) aportante[5] : null);
                    aportanteRemision.setDepartamento(aportante[6] != null ? (Short) aportante[6] : null);
                    aportanteRemision.setMunicipio(aportante[7] != null ? (Short) aportante[7] : null);
                    aportanteRemision.setDireccion(aportante[8] != null ? (String) aportante[8] : null);
                    aportanteRemision.setCodigoPostal(aportante[9] != null ? (String) aportante[9] : null);
                    aportanteRemision.setTelefono(aportante[10] != null ? (String) aportante[10] : null);
                    aportanteRemision.setEnviar(aportante[11] != null ? (Boolean) aportante[11] : null);
                    aportanteRemision.setObservacion(aportante[12] != null ? (String) aportante[12] : null);
                    aportantesDTO.add(aportanteRemision);
                }

            }

            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO = consultarSolicitudGestionCobro(numeroRadicacion);
            RegistroRemisionAportantesDTO registroRemisionAportantesDTO = new RegistroRemisionAportantesDTO();
            registroRemisionAportantesDTO.setAportantes(aportantesDTO);
            if (solicitudGestionCobroDTO != null) {

                registroRemisionAportantesDTO.setFechaRemision(solicitudGestionCobroDTO.getFechaRemision());
                registroRemisionAportantesDTO.setHoraRemision(solicitudGestionCobroDTO.getFechaRemision());
                if (solicitudGestionCobroDTO.getDocumentoSoporte() != null && solicitudGestionCobroDTO.getDocumentoSoporte().getIdentificacionDocumento() != null && solicitudGestionCobroDTO.getDocumentoSoporte().getVersionDocumento() != null) {
                    registroRemisionAportantesDTO.setIdDocumento(solicitudGestionCobroDTO.getDocumentoSoporte().getIdentificacionDocumento() + "_" + solicitudGestionCobroDTO.getDocumentoSoporte().getVersionDocumento());
                }
                registroRemisionAportantesDTO.setObservaciones(solicitudGestionCobroDTO.getObservacionRemision());
                registroRemisionAportantesDTO.setNumeroRadicacion(numeroRadicacion);
            }

            logger.debug("Fin de servicio consultarAportantesPrimeraRemision(String numeroRadicacion)");
            return registroRemisionAportantesDTO;

        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesPrimeraRemision(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDocumentoSoporte(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DocumentoSoporteModeloDTO consultarDocumentoSoporte(String idECM) {
        try {
            logger.debug("Inicio de servicio consultarDocumentoSoporte");
            DocumentoSoporte documento = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_SOPORTE, DocumentoSoporte.class).setParameter("idECM", idECM).getSingleResult();
            DocumentoSoporteModeloDTO documentoDTO = new DocumentoSoporteModeloDTO();
            documentoDTO.convertToDTO(documento);
            logger.debug("Fin de servicio consultarDocumentoSoporte");
            return documentoDTO;
        } catch (NoResultException e) {
            logger.debug("Fin de servicio consultarDocumentoSoporte: No se encontro el documento con el identificador: " + idECM);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetalleGestionCobroSolicitud(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleGestionCobroSolicitud(Long idSolicitudFisico) {
        try {
            logger.debug("Inicio de servicio consultarDetalleGestionCobroSolicitud");
            List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_GESTION, DetalleSolicitudGestionCobroModeloDTO.class).setParameter("idSolicitudFisico", idSolicitudFisico).getResultList();
            logger.debug("Fin de servicio consultarDetalleGestionCobroSolicitud");
            return listaDetalleDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio consultarDetalleGestionCobroSolicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarEstadoSolicitudGestionCobro(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum)
     */
    @Override
    public void actualizarEstadoSolicitudGestionCobro(String numeroRadicacion, EstadoSolicitudGestionCobroEnum
            estadoSolicitud) {
        try {
            logger.debug("Inicio de servicio actualizarEstadoSolicitudGestionCobro(String numeroRadicacion, EstadoSolicitudGestionCobroEnum estadoSolicitud)");

            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO = consultarSolicitudGestionCobro(numeroRadicacion);
            solicitudGestionCobroDTO.setEstado(estadoSolicitud);
            SolicitudGestionCobroFisico solicitudGestion = solicitudGestionCobroDTO.convertToEntity();
            entityManager.merge(solicitudGestion);
            logger.debug("Inicio de servicio actualizarEstadoSolicitudGestionCobro(String numeroRadicacion, EstadoSolicitudGestionCobroEnum estadoSolicitud)");
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesSolicitudCobro(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudGestionCobro(com.asopagos.dto.modelo.
     * SolicitudGestionCobroFisicoModeloDTO)
     */
    @Override
    public SolicitudGestionCobroFisicoModeloDTO guardarSolicitudGestionCobro(SolicitudGestionCobroFisicoModeloDTO
                                                                                     solicitudGestionCobroDTO) {
        try {
            logger.debug("Inicio de servicio guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionCobroDTO)");
            SolicitudGestionCobroFisico solicitudGestionCobroFisico = solicitudGestionCobroDTO.convertToEntity();
            SolicitudGestionCobroFisico managed = entityManager.merge(solicitudGestionCobroFisico);
            solicitudGestionCobroDTO.convertToDTO(managed);
            return solicitudGestionCobroDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesSolicitudCobro(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudGestionCobro(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobro(String numeroRadicacion) {
        try {
            logger.debug("Inicio de servicio consultarSolicitudGestionCobro(String numeroRadicacion)");

            SolicitudGestionCobroFisico solicitudGestion = (SolicitudGestionCobroFisico) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_NUMERO_RADICACION).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO = new SolicitudGestionCobroFisicoModeloDTO();
            solicitudGestionCobroDTO.convertToDTO(solicitudGestion);
            logger.debug("Inicio de servicio consultarSolicitudGestionCobro(String numeroRadicacion)");
            return solicitudGestionCobroDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarSolicitudGestionCobro(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesEntregaPrimeraRemision(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteRemisionComunicadoDTO> consultarAportantesEntregaPrimeraRemision(String numeroRadicacion) {
        try {
            logger.debug("Inicio de servicio consultarAportantesEntregaPrimeraRemision(String numeroRadicacion)");
            List<Object[]> aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES_PRIMERA_ENTREGA).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("tipoUbicacion", TipoUbicacionEnum.NOTIFICACION_JUDICIAL.name()).setParameter("enviar", Boolean.TRUE).getResultList();
            List<AportanteRemisionComunicadoDTO> aportantesDTO = new ArrayList<>();
            if (!aportantes.isEmpty()) {
                for (Object[] aportante : aportantes) {
                    AportanteRemisionComunicadoDTO aportanteRemision = new AportanteRemisionComunicadoDTO();
                    aportanteRemision.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportante[0]));
                    aportanteRemision.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[1]));
                    aportanteRemision.setNumeroIdentificacion((String) aportante[2]);
                    aportanteRemision.setNombreCompleto(aportante[3] != null ? (String) aportante[3] : null);
                    aportanteRemision.setPeriodo(((Date) aportante[4]).getTime());
                    aportanteRemision.setDestinatario(aportante[5] != null ? (String) aportante[5] : null);
                    aportanteRemision.setDepartamento(aportante[6] != null ? (Short) aportante[6] : null);
                    aportanteRemision.setMunicipio(aportante[7] != null ? (Short) aportante[7] : null);
                    aportanteRemision.setDireccion(aportante[8] != null ? (String) aportante[8] : null);
                    aportanteRemision.setCodigoPostal(aportante[9] != null ? (String) aportante[9] : null);
                    aportanteRemision.setTelefono(aportante[10] != null ? (String) aportante[10] : null);
                    aportanteRemision.setFechaPrimeraEntrega(aportante[11] != null ? ((Date) aportante[11]).getTime() : null);
                    aportanteRemision.setObservacionPrimeraEntrega(aportante[12] != null ? (String) aportante[12] : null);
                    aportanteRemision.setEstadoTarea(aportante[13] != null ? EstadoTareaGestionCobroEnum.valueOf((String) aportante[13]) : null);
                    aportanteRemision.setResultadoPrimeraEntrega(aportante[14] != null ? ResultadoEntregaEnum.valueOf((String) aportante[14]) : null);
                    aportanteRemision.setIdDocumentoPrimeraEntrega(aportante[15] != null ? (String) aportante[15] : null);
                    aportantesDTO.add(aportanteRemision);
                }
            }
            logger.debug("Inicio de servicio consultarAportantesEntregaPrimeraRemision(String numeroRadicacion)");
            return aportantesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesEntregaPrimeraRemision(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAporanteSegundaRemision(java.lang.String)
     */
    @Override
    public RegistroRemisionAportantesDTO consultarAportanteSegundaRemision(String numeroRadicacion) {
        try {
            logger.debug("Inicio de servicio consultarAporanteSegundaRemision(String numeroRadicacion)");
            SolicitudGestionCobroFisicoModeloDTO solicitudGestion = consultarSolicitudGestionCobro(numeroRadicacion);
            RegistroRemisionAportantesDTO registroRemisionDTO = new RegistroRemisionAportantesDTO();
            registroRemisionDTO.setFechaRemision(solicitudGestion.getFechaRemision());
            registroRemisionDTO.setObservaciones(solicitudGestion.getObservacionRemision());
            if (solicitudGestion.getDocumentoSoporte() != null && solicitudGestion.getDocumentoSoporte().getIdentificacionDocumento() != null && solicitudGestion.getDocumentoSoporte().getVersionDocumento() != null) {
                registroRemisionDTO.setIdDocumento(solicitudGestion.getDocumentoSoporte() != null ? solicitudGestion.getDocumentoSoporte().getIdentificacionDocumento() + "_" + solicitudGestion.getDocumentoSoporte().getVersionDocumento() : null);
            }
            List<Object[]> listaAportante = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES_SEGUNDA_REMISION_ENTREGA).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("tipoUbicacion", TipoUbicacionEnum.NOTIFICACION_JUDICIAL.name()).getResultList();
            List<AportanteRemisionComunicadoDTO> aportantes = new ArrayList<>();

            for (Object[] aportante : listaAportante) {
                AportanteRemisionComunicadoDTO aportanteRemision = new AportanteRemisionComunicadoDTO();
                aportanteRemision.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportante[0]));
                aportanteRemision.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[1]));
                aportanteRemision.setNumeroIdentificacion((String) aportante[2]);
                aportanteRemision.setNombreCompleto(aportante[3] != null ? (String) aportante[3] : null);
                aportanteRemision.setPeriodo(((Date) aportante[4]).getTime());
                aportanteRemision.setDestinatario(aportante[5] != null ? (String) aportante[5] : null);
                aportanteRemision.setDepartamento(aportante[6] != null ? (Short) aportante[6] : null);
                aportanteRemision.setMunicipio(aportante[7] != null ? (Short) aportante[7] : null);
                aportanteRemision.setDireccion(aportante[8] != null ? (String) aportante[8] : null);
                aportanteRemision.setCodigoPostal(aportante[9] != null ? (String) aportante[9] : null);
                aportanteRemision.setTelefono(aportante[10] != null ? (String) aportante[10] : null);
                aportanteRemision.setEnviar(aportante[11] != null ? (Boolean) aportante[11] : null);
                aportanteRemision.setObservacion(aportante[12] != null ? (String) aportante[12] : null);
                aportanteRemision.setFechaPrimeraEntrega(aportante[13] != null ? ((Date) aportante[13]).getTime() : null);
                aportanteRemision.setObservacionPrimeraEntrega(aportante[14] != null ? (String) aportante[14] : null);
                aportanteRemision.setEstadoTarea(EstadoTareaGestionCobroEnum.valueOf((String) aportante[15]));
                aportanteRemision.setFechaSegundaEntrega(aportante[16] != null ? ((Date) aportante[16]).getTime() : null);
                aportanteRemision.setObservacionSegundaEntrega(aportante[17] != null ? (String) aportante[17] : null);
                aportanteRemision.setResultadoPrimeraEntrega(aportante[18] != null ? ResultadoEntregaEnum.valueOf((String) aportante[18]) : null);
                aportanteRemision.setResultadoSegundaEntrega(aportante[19] != null ? ResultadoEntregaEnum.valueOf((String) aportante[19]) : null);
                aportanteRemision.setIdDocumentoPrimeraEntrega(aportante[20] != null ? (String) aportante[20] : null);
                aportanteRemision.setIdDocumentoSegundaEntrega(aportante[21] != null ? (String) aportante[21] : null);
                aportantes.add(aportanteRemision);
            }

            registroRemisionDTO.setAportantes(aportantes);
            logger.debug("Fin de servicio consultarAporanteSegundaRemision(String numeroRadicacion)");
            return registroRemisionDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAporanteSegundaRemision(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetallePorSolicitud(java.lang.Long)
     */
    @Override
    public DetalleSolicitudGestionCobroModeloDTO consultarDetallePorSolicitud(Long idSolicitud) {
        try {
            logger.debug("Inicio de servicio consultarDetallePorSolicitud(Long idSolicitud)");
            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroDTO = (DetalleSolicitudGestionCobroModeloDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_POR_SOLICITUD).setParameter("idSolicitud", idSolicitud).getSingleResult();
            logger.debug("Fin de servicio consultarDetallePorSolicitud(Long idSolicitud)");
            return detalleSolicitudGestionCobroDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarDetallePorSolicitud(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudGestionCobroElectronico(java.lang.String)
     */
    @Override
    public SolicitudGestionCobroElectronicoModeloDTO consultarSolicitudGestionCobroElectronico(String
                                                                                                       numeroRadicacion) {
        try {
            logger.debug("Inicio de servicio consultarSolicitudGestionCobroElectronico(String numeroRadicacion)");
            SolicitudGestionCobroElectronico solicitudGestionCobroElectronico = (SolicitudGestionCobroElectronico) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_ELECTRONICO).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();

            SolicitudGestionCobroElectronicoModeloDTO solicitudGestionCobroElectronicoDTO = new SolicitudGestionCobroElectronicoModeloDTO();
            solicitudGestionCobroElectronicoDTO.convertToDTO(solicitudGestionCobroElectronico);
            logger.debug("Fin de servicio consultarSolicitudGestionCobroElectronico(String numeroRadicacion)");
            return solicitudGestionCobroElectronicoDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarSolicitudGestionCobroElectronico(String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarEstadoSolicitudGestionCobroElectronico(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum)
     */
    @Override
    public void actualizarEstadoSolicitudGestionCobroElectronico(String
                                                                         numeroRadicacion, EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro) {
        try {
            logger.debug("Inicio de servicio actualizarEstadoSolicitudGestionCobroElectronico(String numeroRadicado,EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro)");
            SolicitudGestionCobroElectronicoModeloDTO solicitudGestionCobroElectronicoDTO = consultarSolicitudGestionCobroElectronico(numeroRadicacion);
            SolicitudGestionCobroElectronico solicitudGestionCobroElectronico = solicitudGestionCobroElectronicoDTO.convertToEntity();
            solicitudGestionCobroElectronico.setEstado(estadoSolicitudGestionCobro);
            entityManager.merge(solicitudGestionCobroElectronico);
            logger.debug("Fin de servicio actualizarEstadoSolicitudGestionCobroElectronico(String numeroRadicado,EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro)");
        } catch (Exception e) {
            logger.error("Ocurrió un error en actualizarEstadoSolicitudGestionCobroElectronico(String numeroRadicado,EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /* Fin proceso 223 */
    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraService#consultarAportantesGestionCobro(com.asopagos.dto.modelo.
     * ParametrizacionCriteriosGestionCobroModeloDTO, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimulacionDTO> consultarAportantesGestionCobro(Boolean
                                                                       parametro, ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, UriInfo uri, HttpServletResponse
                                                                       response) {
        try {
            logger.debug("Inicio de método consultarAportantesGestionCobro");
            FiltrosParametrizacionDTO filtrosParametrizacion = construirFiltrosParametrizacionGestionCobro(parametrizacion);
            /* si el uri llega con query params es porque la consulta debe ser paginada */
            List<String> tiposAfiliado = new ArrayList<>();
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;
            if (filtrosParametrizacion.getIncluirIndependientes() != null && filtrosParametrizacion.getIncluirIndependientes()) {
                tiposAfiliado.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name());
                tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
            }
            if (filtrosParametrizacion.getIncluirPensionados() != null && filtrosParametrizacion.getIncluirPensionados()) {
                tiposAfiliado.add(TipoAfiliadoEnum.PENSIONADO.name());
                tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
            }

            List<Object[]> aportantes = null;
            StoredProcedureQuery procedimiento = null;
            if (tiposAfiliado.contains(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name()) || tiposAfiliado.contains(TipoAfiliadoEnum.PENSIONADO.name())) {
                procedimiento = consultasModeloCore.consultarAportantesGestionCobroPersona(tipoSolicitante, parametrizacion, filtrosParametrizacion);
            } else {
                procedimiento = consultasModeloCore.consultarAportantesGestionCobroEmpleador(parametrizacion, filtrosParametrizacion);
            }

            List<SimulacionDTO> simulaciones = new ArrayList<>();
            SimulacionDTO simulacion;
            Object objEstado;
            if (procedimiento != null) {
                aportantes = procedimiento.getResultList() != null ? (List<Object[]>) procedimiento.getResultList() : Collections.emptyList();
                for (Object[] aportante : aportantes) {
                    simulacion = new SimulacionDTO();
                    simulacion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[0]));
                    simulacion.setNumeroIdentificacion((String) aportante[1]);
                    simulacion.setNombreRazonSocial((String) aportante[2]);
                    simulacion.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf(aportante[3].toString()));
                    EstadoCarteraEnum estadoCartera = aportante[4] == null ? EstadoCarteraEnum.AL_DIA : EstadoCarteraEnum.valueOf((String) aportante[4]);
                    simulacion.setEstadoActualCartera(estadoCartera);
                    BigDecimal valorPromedioAportes = aportante[5] != null ? (BigDecimal) aportante[5] : new BigDecimal(0);
                    simulacion.setValorPromedioAportes(valorPromedioAportes);

                    if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante) || TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {
                        Short cantidadVecesMoroso = aportante[6] != null ? new Short(((Integer) aportante[6]).toString()) : new Short("0");
                        simulacion.setCantidadVecesMoroso(cantidadVecesMoroso);
                        objEstado = aportante[7];
                        simulacion.setNumeroOperacion(aportante[8].toString());
                    } else {
                        Integer trabajadoresActivos = aportante[6] != null ? (Integer) aportante[6] : 0;
                        simulacion.setTrabajadoresActivos(trabajadoresActivos);
                        Short cantidadVecesMoroso = aportante[7] != null ? new Short(((Integer) aportante[7]).toString()) : new Short("0");
                        simulacion.setCantidadVecesMoroso(cantidadVecesMoroso);
                        objEstado = aportante[8];
                        simulacion.setNumeroOperacion(aportante[9].toString());
                    }

                    simulacion.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                    if (objEstado != null && !objEstado.toString().isEmpty()) {
                        simulacion.setEstadoAfiliado(EstadoAfiliadoEnum.valueOf(objEstado.toString()));
                    }

                    simulaciones.add(simulacion);
                }
            }
            return simulaciones;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarAportantesGestionCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraService#consultarCondicionAportanteCarteraLCUNO()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarCondicionAportantesCarteraLCUNO(EstadoOperacionCarteraEnum
                                                                    estadoOperacion, TipoLineaCobroEnum tipoLineaCobro) {
        logger.debug("Inicia consultarCondicionCarteraLCUNO()");
        try {
            Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_EN_CARTERA_LINEA_DE_COBRO_UNO_VIGENTE).setParameter("estadoOperacion", estadoOperacion).setParameter(TIPO_LINEA_COBRO, tipoLineaCobro);
            List<Cartera> lstAportantesCartera = q.getResultList();
            if (lstAportantesCartera.isEmpty()) {
                logger.debug("Finaliza consultarCondicionCarteraLCUNO()");
                return true;
            } else {
                logger.debug("Finaliza consultarCondicionCarteraLCUNO()");
                return false;
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarCondicionCarteraLCUNO", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#asignarAccionCobro()
     */
    public List<AportanteAccionCobroDTO> asignarAccionCobro(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicia método asignarAccionCobro");

        List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = consultasModeloCore.asignarAccionCobro(accionCobro);

        //procesarTemporales();

        logger.debug("Finaliza método asignarAccionCobro");
        return listaAportanteAccionCobroDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDetalleSolicitudGestionCobroFisico(com.asopagos.dto.modelo .
     * DetalleSolicitudGestionCobroModeloDTO)
     */
    @Override
    public DetalleSolicitudGestionCobroModeloDTO guardarDetalleSolicitudGestionCobroFisico
    (DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO, Long idSolicitudGlobal) {
        String fimaMetodo = "guardarDetalleSolicitudGestionCobroModeloDTO(DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO)";
        logger.debug(LOG_INICIA + fimaMetodo);

        List<Long> listaIdsCartera = new ArrayList<>();
        listaIdsCartera.add(detalleSolicitudGestionCobroModeloDTO.getIdCartera());
        List<DetalleSolicitudGestionCobro> listaDetalleDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_GESTION_POR_CARTERA).setParameter("listaIdsCartera", listaIdsCartera).setParameter("idSolicitudGlobal", idSolicitudGlobal).getResultList();
        if (listaDetalleDTO.isEmpty()) {
            List<Long> ids = consultasModeloCore.obtenerValoresSecuencia(SECUENCIA_DETALLE_SOLICITUD_GESTION_COBRO, 1);
            /* Se convierte a entity */
            DetalleSolicitudGestionCobro detalleSolicitudGestionCobro = detalleSolicitudGestionCobroModeloDTO.convertToEntity();
            detalleSolicitudGestionCobro.setId(ids.get(0));
            DetalleSolicitudGestionCobro managed = entityManager.merge(detalleSolicitudGestionCobro);
            /* Se devuelve el DTO */
            detalleSolicitudGestionCobroModeloDTO.convertToDTO(managed);
        } else {
            DetalleSolicitudGestionCobro detalle = listaDetalleDTO.get(0);
            detalleSolicitudGestionCobroModeloDTO.setNoNullValuesToEntity(detalle);
            DetalleSolicitudGestionCobro managed = entityManager.merge(detalle);
            /* Se devuelve el DTO */
            detalleSolicitudGestionCobroModeloDTO.convertToDTO(managed);
        }

        logger.debug(LOG_FINALIZA + fimaMetodo);
        return detalleSolicitudGestionCobroModeloDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDetalleSolicitudGestionCobroFisico(com.asopagos.dto.modelo .
     * DetalleSolicitudGestionCobroModeloDTO)
     */
    @Override
    public void guardarDetallesSolicitudGestionCobroFisico
    (List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO, Long idSolicitudGlobal) {
        String fimaMetodo = "guardarDetallesSolicitudGestionCobroFisico(List<DetalleSolicitudGestionCobroModeloDTO>)";
        logger.debug(LOG_INICIA + fimaMetodo);
        for (DetalleSolicitudGestionCobroModeloDTO detalleDTO : detallesSolicitudGestionCobroModeloDTO) {
            guardarDetalleSolicitudGestionCobroFisico(detalleDTO, idSolicitudGlobal);
        }
        logger.debug(LOG_FINALIZA + fimaMetodo);
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarListaDetalleSolicitudGestionCobroFisico(java.util.List)
     */
    @Override
    public void guardarListaDetalleSolicitudGestionCobroFisico
    (List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO, Long
            idSolicitudGlobal) {
        String fimaMetodo = "guardarDetalleSolicitudGestionCobroModeloDTO(DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO)";
        logger.debug(LOG_INICIA + fimaMetodo);
        try {

            Map<Long, DetalleSolicitudGestionCobroModeloDTO> detallesProcesados = new HashMap<Long, DetalleSolicitudGestionCobroModeloDTO>();
            List<Long> listaIdsCartera = new ArrayList<>();
            int cont = 0;
            for (DetalleSolicitudGestionCobroModeloDTO detalleDTO : listaDetalleSolicitudGestionCobroModeloDTO) {
                cont++;
                listaIdsCartera.add(detalleDTO.getIdCartera());
                detallesProcesados.put(detalleDTO.getIdCartera(), detalleDTO);
                if (cont == 1000) {
                    guardarActualizarDetallesSolicitudGestionCobro(listaIdsCartera, detallesProcesados, idSolicitudGlobal);
                    cont = 0;
                    listaIdsCartera = new ArrayList<>();
                    detallesProcesados = new HashMap<Long, DetalleSolicitudGestionCobroModeloDTO>();
                }
            }

            if (cont > 0) {
                guardarActualizarDetallesSolicitudGestionCobro(listaIdsCartera, detallesProcesados, idSolicitudGlobal);
            }

            logger.debug(LOG_FINALIZA + fimaMetodo);
        } catch (Exception e) {
            logger.error("Error en " + fimaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que determina si el detalle existia para sber si guardarlo o debe crearlo nuevo
     *
     * @param listaIdsCartera
     * @param detallesProcesados
     */
    private void guardarActualizarDetallesSolicitudGestionCobro
    (List<Long> listaIdsCartera, Map<Long, DetalleSolicitudGestionCobroModeloDTO> detallesProcesados, Long
            idSolicitudGlobal) {

        DetalleSolicitudGestionCobroModeloDTO detalleTmp;

        List<DetalleSolicitudGestionCobro> listaDetalleDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_GESTION_POR_CARTERA).setParameter("listaIdsCartera", listaIdsCartera).setParameter("idSolicitudGlobal", idSolicitudGlobal).getResultList();

        for (DetalleSolicitudGestionCobro detalle : listaDetalleDTO) {
            if (detallesProcesados.containsKey(detalle.getIdCartera())) {
                detalleTmp = detallesProcesados.get(detalle.getIdCartera());
                detalleTmp.setNoNullValuesToEntity(detalle);
                entityManager.merge(detalle);
                detallesProcesados.remove(detalle.getIdCartera());
            }
        }
        if (detallesProcesados.size() > 0) {
            List<Long> ids = consultasModeloCore.obtenerValoresSecuencia(SECUENCIA_DETALLE_SOLICITUD_GESTION_COBRO, detallesProcesados.size());
            int countId = 0;
            DetalleSolicitudGestionCobro detalle;
            Map<Long, DetalleSolicitudGestionCobroModeloDTO> detallesProcesadosTmp = new HashMap<Long, DetalleSolicitudGestionCobroModeloDTO>(detallesProcesados);
            for (Entry<Long, DetalleSolicitudGestionCobroModeloDTO> detalleMap : detallesProcesadosTmp.entrySet()) {
                detalle = detalleMap.getValue().convertToEntity();
                if (detallesProcesados.containsKey(detalle.getIdCartera())) {
                    detalle.setId(ids.get(countId));
                    entityManager.persist(detalle);
                    detallesProcesados.remove(detalle.getIdCartera());
                    countId++;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDetallesSolicitudGestionCobro(java.util.List)
     */
    @Override
    public void guardarDetallesSolicitudGestionCobro
    (List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroModeloDTO, Long idSolicitudGlobal) {
        String fimaMetodo = "guardarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO>  detalleSolicitudGestionCobroModeloDTO)";
        logger.debug(LOG_INICIA + fimaMetodo);
        try {
            /* Se recorre cada DetalleSolicitudGestionCobroModelo para ser guardado */
            //            for (DetalleSolicitudGestionCobroModeloDTO dto : detalleSolicitudGestionCobroModeloDTO) {
            guardarListaDetalleSolicitudGestionCobroFisico(detalleSolicitudGestionCobroModeloDTO, idSolicitudGlobal);
            //            }

        } catch (Exception e) {
            logger.error("Error en " + fimaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(LOG_FINALIZA + fimaMetodo);
    }

    @Override
    public void actualizarDetallesSolicitudGestionCobro
            (List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO) {
        String fimaMetodo = "actualizarDetallesSolicitudGestionCobro(DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO)";
        logger.debug(LOG_INICIA + fimaMetodo);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String filtro = mapper.writeValueAsString(listaDetalleSolicitudGestionCobroModeloDTO);

            StoredProcedureQuery procedimiento = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_DETALLE_SOLICITUD_GESTION_COBRO_OK).setParameter("filtros", filtro);
            procedimiento.execute();

            logger.debug(LOG_FINALIZA + fimaMetodo);
        } catch (Exception e) {
            logger.error("Error en " + fimaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }


    /**
     * Bryan
     */

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarUltimoDestinatarioSolicitud()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarUltimoDestinatarioSolicitud() {
        String fimaMetodo = "consultarUltimoDestinatarioSolicitud()";
        String destinatario = null;
        logger.debug(LOG_INICIA + fimaMetodo);
        try {
            destinatario = (String) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_GESTION_COBRO_1A_CONSULTAR_DESTINATARIO).getSingleResult();

        } catch (NoResultException er) {
            logger.error("Error en " + fimaMetodo, er);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, er);
        } catch (Exception e) {
            logger.error("Error en " + fimaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(LOG_FINALIZA + fimaMetodo);
        return destinatario;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#obtenerBackGestionCobroAporte(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision) {
        try {
            logger.debug("Inicio de método consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision)");
            SolicitudGestionCobroFisico solicitudGestionCobroFisico = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_GESTION_COBRO_CONSULTAR_SOLICITUD_GESTION_COBRO_POR_ID_PRIMERA_REMISION, SolicitudGestionCobroFisico.class).setParameter("idSolicitudPrimeraRemision", idSolicitudPrimeraRemision).getSingleResult();
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroFisicoModeloDTO = new SolicitudGestionCobroFisicoModeloDTO();
            solicitudGestionCobroFisicoModeloDTO.convertToDTO(solicitudGestionCobroFisico);
            logger.debug("Fin de método consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision)");
            return solicitudGestionCobroFisicoModeloDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarSolicitudGestionCobroPorId(Long idSolicitudPrimeraRemision)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetallePorSolicitudPrimeraRemision(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleSolicitudGestionCobroModeloDTO consultarDetallePorSolicitudPrimeraRemision(Long
                                                                                                     idSolicitudPrimeraRemision) {
        try {
            logger.debug("Inicio de servicio consultarDetallePorSolicitudPrimeraRemision(Long idSolicitudPrimeraRemision)");
            DetalleSolicitudGestionCobro detalleSolicitudGestionCobro = (DetalleSolicitudGestionCobro) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_POR_SOLICITUD_PRIMERA_REMISION).setParameter("idSolicitudPrimeraRemision", idSolicitudPrimeraRemision).getSingleResult();

            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroDTO = new DetalleSolicitudGestionCobroModeloDTO();
            detalleSolicitudGestionCobroDTO.convertToDTO(detalleSolicitudGestionCobro);
            logger.debug("Fin de servicio consultarDetallePorSolicitudPrimeraRemision(Long idSolicitudPrimeraRemision)");
            return detalleSolicitudGestionCobroDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarDetallePorSolicitudPrimeraRemision(Long idSolicitudPrimeraRemision)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesDesafiliacion(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DesafiliacionAportanteDTO> consultarAportantesDesafiliacion(TipoSolicitanteMovimientoAporteEnum
                                                                                    tipoSolicitante) {
        String firmaMetodo = "consultarAportantesDesafiliacion(" + tipoSolicitante + ")";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs = new ArrayList<>();

        StoredProcedureQuery procedimiento = consultasModeloCore.consultarAportantesDesafiliacion(tipoSolicitante);
        List<Object[]> query = procedimiento.getResultList();

        if (query == null) {
            query = Collections.emptyList();
        }

        for (Object[] objects : query) {
            DesafiliacionAportanteDTO desafiliacionAportanteDTO = new DesafiliacionAportanteDTO();

            desafiliacionAportanteDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(objects[0].toString()));
            desafiliacionAportanteDTO.setNumeroIdentificacion(objects[1].toString());
            desafiliacionAportanteDTO.setNombreRazonSocial(objects[2].toString());
            desafiliacionAportanteDTO.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf(objects[3].toString()));
            desafiliacionAportanteDTO.setEstadoCartera(EstadoCarteraEnum.valueOf(objects[4].toString()));
            desafiliacionAportanteDTO.setTipoDeuda(objects[5].toString());
            if (objects[6] != null) {
                desafiliacionAportanteDTO.setMonto(new BigDecimal(objects[6].toString()));
            }
            desafiliacionAportanteDTO.setDeuda(TipoDeudaEnum.valueOf(objects[7].toString()));
            desafiliacionAportanteDTO.setTipoLineaCobro(TipoLineaCobroEnum.valueOf(objects[8].toString()));
            desafiliacionAportanteDTO.setIdPersona(Long.parseLong(objects[9].toString()));
            desafiliacionAportanteDTO.setNumeroOperacion(Long.parseLong(objects[10].toString()));
            desafiliacionAportanteDTOs.add(desafiliacionAportanteDTO);
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return desafiliacionAportanteDTOs;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesSolicitudDesafiliacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DesafiliacionAportanteDTO> consultarAportantesSolicitudDesafiliacion(String numeroRadicacion) {
        String firmaMetodo = "consultarAportantesSolicitudDesafiliacion(String numeroRadicacion)";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs = new ArrayList<>();
        try {
            /* Se valida el tipo de solicitante para verficar que consulta ejecutar */
            List<Object[]> query = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_DESAFILIACION_CONSULTAR_APORTANTES_SOLICITUD_DESAFILIACION).setParameter(NUMERO_RADICACION, numeroRadicacion).getResultList();

            for (Object[] objects : query) {
                DesafiliacionAportanteDTO desafiliacionAportanteDTO = new DesafiliacionAportanteDTO();

                desafiliacionAportanteDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(objects[0].toString()));
                desafiliacionAportanteDTO.setNumeroIdentificacion(objects[1].toString());
                desafiliacionAportanteDTO.setNombreRazonSocial(objects[2].toString());
                desafiliacionAportanteDTO.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf(objects[3].toString()));
                desafiliacionAportanteDTO.setEstadoCartera(EstadoCarteraEnum.MOROSO);
                desafiliacionAportanteDTO.setDeuda(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(desafiliacionAportanteDTO.getTipoSolicitante()) ? TipoDeudaEnum.POR_MORA : TipoDeudaEnum.INEXACTITUD);
                if (objects[4] != null) {
                    desafiliacionAportanteDTO.setMonto(new BigDecimal(objects[4].toString()));
                }
                desafiliacionAportanteDTO.setTipoDeuda(objects[5].toString() != null ? objects[5].toString() : null);
                desafiliacionAportanteDTO.setTipoLineaCobro(TipoLineaCobroEnum.valueOf(objects[6].toString()));
                desafiliacionAportanteDTO.setIdPersona(Long.parseLong(objects[7].toString()));
                desafiliacionAportanteDTO.setNumeroOperacion(Long.parseLong(objects[8].toString()));
                desafiliacionAportanteDTOs.add(desafiliacionAportanteDTO);
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return desafiliacionAportanteDTOs;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarPromedioPeriodoImpago(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal consultarPromedioPeriodoImpago
    (List<PersonaModeloDTO> personaModeloDTOs, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "consultarPromedioPeriodoImpago(List<PersonaModeloDTO> personaModeloDTOs)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            TipoDeudaEnum tipoDeuda = null;
            /* Se valida tipo solicitante para asignar el tipo de deuda */
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                tipoDeuda = TipoDeudaEnum.POR_MORA;
            } else {
                tipoDeuda = TipoDeudaEnum.INEXACTITUD;
            }

            StringBuilder filtro = new StringBuilder();
            String separador = ",";
            for (PersonaModeloDTO personaModeloDTO : personaModeloDTOs) {
                filtro.append((personaModeloDTO.getTipoIdentificacionNuevo() + separador + personaModeloDTO.getNumeroIdentificacionNuevo() + "|"));
            }

            BigDecimal promedio = (BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CONSULTAR_PROMEDIO_DESAFILIACION).setParameter("filtro", filtro.toString()).setParameter("tipoDeuda", tipoDeuda.name()).setParameter("tipoSolicitante", tipoSolicitante.name()).getSingleResult();
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return promedio;
        } catch (NoResultException ex) {
            logger.error("Fin de servicio " + firmaMetodo + " - Recurso no encontrado");
            return null;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudDesafiliacion(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudDesafiliacionModeloDTO consultarSolicitudDesafiliacion(String numeroRadicacion) {

        String firmaMetodo = "consultarSolicitudDesafiliacion(String numeroRadicacion)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO = new SolicitudDesafiliacionModeloDTO();
            /* Se consulta la Solicitud de Desafiliacion */
            SolicitudDesafiliacion solicitudDesafiliacion = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_DESAFILIACION_CONSULTAR_SOLICITUD_DESAFILIACION_POR_NUMERO_RADICACION, SolicitudDesafiliacion.class).setParameter("numRadicacion", numeroRadicacion).getSingleResult();
            /* Se convierte a DTO */
            solicitudDesafiliacionModeloDTO.convertToDTO(solicitudDesafiliacion);
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return solicitudDesafiliacionModeloDTO;
        } catch (NoResultException n) {
            return null;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudDesafiliacion(com.asopagos.dto.modelo.SolicitudDesafiliacionModeloDTO)
     */
    @Override
    public SolicitudDesafiliacionModeloDTO guardarSolicitudDesafiliacion(SolicitudDesafiliacionModeloDTO
                                                                                 solicitudDesafiliacionModeloDTO, UserDTO userDTO) {
        String firmaMetodo = "guardarSolicitudDesafiliacion(SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            /* Se valida si ya existe la Solicitud de Desafiliacion */
            SolicitudDesafiliacion solicitudDesafiliacion = solicitudDesafiliacionModeloDTO.convertToEntity();
            solicitudDesafiliacion = entityManager.merge(solicitudDesafiliacion);
            solicitudDesafiliacionModeloDTO.setIdSolicitudDesafiliacion(solicitudDesafiliacion.getIdSolicitudDesafiliacion());
            solicitudDesafiliacionModeloDTO.setIdSolicitud(solicitudDesafiliacion.getSolicitudGlobal().getIdSolicitud());

            /* Se persiste en la tabla desafiliacion aportante */
            if (EstadoSolicitudDesafiliacionEnum.RADICADO.equals(solicitudDesafiliacionModeloDTO.getEstadoSolicitud()) && solicitudDesafiliacionModeloDTO.getAportanteDesafiliacionDTOs() != null && !solicitudDesafiliacionModeloDTO.getAportanteDesafiliacionDTOs().isEmpty())
                for (DesafiliacionAportanteDTO desafiliacionAportanteDTO : solicitudDesafiliacionModeloDTO.getAportanteDesafiliacionDTOs()) {
                    DesafiliacionAportanteModeloDTO desafiliacionAportanteModeloDTO = new DesafiliacionAportanteModeloDTO();
                    desafiliacionAportanteModeloDTO.setIdPersona(desafiliacionAportanteDTO.getIdPersona());
                    desafiliacionAportanteModeloDTO.setIdSolicitudDesafiliacion(solicitudDesafiliacion.getIdSolicitudDesafiliacion());
                    desafiliacionAportanteModeloDTO.setTipoSolicitante(desafiliacionAportanteDTO.getTipoSolicitante());
                    desafiliacionAportanteModeloDTO.setTipoLineaCobro(desafiliacionAportanteDTO.getTipoLineaCobro());
                    /* Se convierte a entidad */
                    DesafiliacionAportante desafiliacionAportante = desafiliacionAportanteModeloDTO.convertToEnity();
                    entityManager.persist(desafiliacionAportante);
                }

            /* Se periste en la tabla de DocumentoDesafiliacion */
            List<DocumentoDesafiliacionModeloDTO> listaDocumentos = new ArrayList<DocumentoDesafiliacionModeloDTO>();

            if (solicitudDesafiliacionModeloDTO.getDocumentoDesafiliacionModeloDTOs() != null && !solicitudDesafiliacionModeloDTO.getDocumentoDesafiliacionModeloDTOs().isEmpty()) {
                for (DocumentoDesafiliacionModeloDTO documentoDesafiliacionModeloDTO : solicitudDesafiliacionModeloDTO.getDocumentoDesafiliacionModeloDTOs()) {
                    documentoDesafiliacionModeloDTO.setIdSolicitudDesafiliacion(solicitudDesafiliacion.getIdSolicitudDesafiliacion());
                    DocumentoDesafiliacion documentoDesafiliacion = documentoDesafiliacionModeloDTO.convertToEntity();
                    documentoDesafiliacion = entityManager.merge(documentoDesafiliacion);
                    listaDocumentos.add(new DocumentoDesafiliacionModeloDTO().convertToDTO(documentoDesafiliacion));
                }

                solicitudDesafiliacionModeloDTO.setDocumentoDesafiliacionModeloDTOs(listaDocumentos);
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return solicitudDesafiliacionModeloDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarEstadoSolicitudDesafiliacion(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoSolicitudDesafiliacionEnum)
     */
    @Override
    public void actualizarEstadoSolicitudDesafiliacion(String numeroRadicacion, EstadoSolicitudDesafiliacionEnum
            estadoDesafiliacion) {
        String firmaMetodo = "actualizarEstadoSolicitudDesafiliacion(String numeroRadicacion, EstadoSolicitudDesafiliacionEnum estadoDesafiliacion)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            /* Se valida si ya existe la Solicitud de Desafiliacion */
            SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO = consultarSolicitudDesafiliacion(numeroRadicacion);
            if (solicitudDesafiliacionModeloDTO != null) {
                /* Se convierte a entidad */
                SolicitudDesafiliacion solicitudDesafiliacion = solicitudDesafiliacionModeloDTO.convertToEntity();
                /* Se setea el estado */
                solicitudDesafiliacion.setEstadoSolicitud(estadoDesafiliacion);
                /* Se actualiza el estado */
                entityManager.merge(solicitudDesafiliacion);
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarLineaCobroDesafiliacion(java.util.List)
     */
    @Override
    public void actualizarLineaCobroDesafiliacion
    (List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs, String usuarioTraspaso) {
        String firmaMetodo = "actualizarLineaCobroDesafiliacion(List<DesafiliacionAportanteDTO> aportanteDTOs)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            /* Se obtienen realiza los filtros */
            StringBuilder filtro = new StringBuilder();
            String separador = ",";
            List<Long> numerosOperacion = new ArrayList<>();
            for (DesafiliacionAportanteDTO desafiliacionAportanteDTO : desafiliacionAportanteDTOs) {
                filtro.append((desafiliacionAportanteDTO.getTipoIdentificacion() + separador + desafiliacionAportanteDTO.getNumeroIdentificacion() + "|"));
                numerosOperacion.add(desafiliacionAportanteDTO.getNumeroOperacion());
            }

            /* Se consulta la cartera del aportante al cual se le realizará traspaso a deuda antigua */
            List<Object[]> objects = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CONSULTAR_CARTERA_APORTANTES_DESAFILIACION).setParameter("filtros", filtro.toString()).setParameter("tipoSolicitante", desafiliacionAportanteDTOs.get(0).getTipoSolicitante().name()).setParameter("lineaCobro", desafiliacionAportanteDTOs.get(0).getTipoLineaCobro().name())
            .setParameter("numeroOperacion", numerosOperacion).getResultList();
            /* Se recorren y setean valores */
            for (Object[] obj : objects) {

                CarteraModeloDTO carteraModeloDTO = new CarteraModeloDTO();

                if (obj[0] != null) {
                    carteraModeloDTO.setDeudaPresunta(new BigDecimal(obj[0].toString()));
                }
                carteraModeloDTO.setEstadoCartera(obj[1] != null ? EstadoCarteraEnum.valueOf(obj[1].toString()) : null);
                carteraModeloDTO.setEstadoOperacion(obj[2] != null ? EstadoOperacionCarteraEnum.valueOf(obj[2].toString()) : null);
                if (obj[3] != null) {
                    carteraModeloDTO.setFechaCreacion(((Date) obj[3]).getTime());
                }
                carteraModeloDTO.setIdPersona(Long.parseLong(obj[4].toString()));
                if (obj[5].toString() != null) {
                    BigDecimal idCartera = new BigDecimal(obj[5].toString());
                    carteraModeloDTO.setIdCartera(idCartera.longValue());
                }
                carteraModeloDTO.setMetodo(obj[6] != null ? MetodoAccionCobroEnum.valueOf(obj[6].toString()) : null);
                if (obj[7] != null) {
                    carteraModeloDTO.setPeriodoDeuda(((Date) obj[7]).getTime());
                }
                carteraModeloDTO.setRiesgoIncobrabilidad(obj[8] != null ? RiesgoIncobrabilidadEnum.valueOf(obj[8].toString()) : null);
                carteraModeloDTO.setTipoAccionCobro(obj[9] != null ? TipoAccionCobroEnum.valueOf(obj[9].toString()) : null);
                carteraModeloDTO.setTipoDeuda(obj[10] != null ? TipoDeudaEnum.valueOf(obj[10].toString()) : null);
                carteraModeloDTO.setTipoLineaCobro(obj[11] != null ? TipoLineaCobroEnum.valueOf(obj[11].toString()) : null);
                carteraModeloDTO.setTipoSolicitante(obj[12] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(obj[12].toString()) : null);
                carteraModeloDTO.setUsuarioTraspaso(usuarioTraspaso);
                carteraModeloDTO.setFechaAsignacionAccion(new Date().getTime());

                switch (carteraModeloDTO.getTipoSolicitante()) {
                    case EMPLEADOR:
                        carteraModeloDTO.setTipoLineaCobro(TipoLineaCobroEnum.C6);
                        break;
                    case INDEPENDIENTE:
                        carteraModeloDTO.setTipoLineaCobro(TipoLineaCobroEnum.C7);
                        break;
                    case PENSIONADO:
                        carteraModeloDTO.setTipoLineaCobro(TipoLineaCobroEnum.C8);
                        break;
                    default:
                        break;
                }
                /* Se convierte de DTO a entidad */
                Cartera cartera = carteraModeloDTO.convertToEntity();
                /* Se actualiza la entidad cartera */
                entityManager.merge(cartera);
            }
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarTraspasoCartera(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<String> validarTraspasoCartera(TipoSolicitanteMovimientoAporteEnum
                                                       tipoSolicitante, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAccionCobroEnum
                                                       tipoAccionCobro) {
        try {
            logger.debug("Inicio de método validarTraspasoCartera");
            /* Lista que contiene los fallos de las validaciones */
            List<String> validadorErrores = new ArrayList<>();
            List<TipoAccionCobroEnum> tipoAcciones = new ArrayList<>();
            //Listado de acciones correspondientes al método 1
            int validar = 0;
            switch (tipoSolicitante) {
                case EMPLEADOR:
                    tipoAcciones.add(TipoAccionCobroEnum.F1);
                    tipoAcciones.add(TipoAccionCobroEnum.H2);
                    validar = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_TRASPASO_CARTERA_ANTIGUA_EMPLEADOR).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("estadoEmpleador", EstadoEmpleadorEnum.INACTIVO).setParameter("tipoDeuda", TipoDeudaEnum.POR_MORA).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("estadoCartera", EstadoCarteraEnum.MOROSO).setParameter("tipoSolicitante", tipoSolicitante).setParameter("tipoAcciones", tipoAcciones).getSingleResult()).intValue();

                    break;
                case INDEPENDIENTE:
                    tipoAcciones.add(TipoAccionCobroEnum.LC4C);
                    validar = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_TRASPASO_CARTERA_ANTIGUA_INDEPENDIENTE_PENSIONADO).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE).setParameter("estadoAfiliado", EstadoAfiliadoEnum.INACTIVO).setParameter("tipoDeuda", TipoDeudaEnum.INEXACTITUD).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("estadoCartera", EstadoCarteraEnum.MOROSO).setParameter("tipoSolicitante", tipoSolicitante).setParameter("tipoAcciones", tipoAcciones).getSingleResult()).intValue();
                    break;

                case PENSIONADO:
                    tipoAcciones.add(TipoAccionCobroEnum.LC5C);
                    validar = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_TRASPASO_CARTERA_ANTIGUA_INDEPENDIENTE_PENSIONADO).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoAfiliado", TipoAfiliadoEnum.PENSIONADO).setParameter("estadoAfiliado", EstadoAfiliadoEnum.INACTIVO).setParameter("tipoDeuda", TipoDeudaEnum.INEXACTITUD).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("estadoCartera", EstadoCarteraEnum.MOROSO).setParameter("tipoSolicitante", tipoSolicitante).setParameter("tipoAcciones", tipoAcciones).getSingleResult()).intValue();
                    break;

                default:
                    break;
            }
            /* Si el aportante no supero las validaciones */
            if (validar <= 0) {

                if (!tipoAcciones.contains(tipoAccionCobro)) {
                    validadorErrores.add("No se le han efectuado todas las acciones de cobro al aportante");
                }

                List<ConsultarEstadoDTO> listConsultaEstado = new ArrayList<>();
                ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();

                /* Se arma el objeto para la consulta de estados */
                consultEstado.setNumeroIdentificacion(numeroIdentificacion);
                consultEstado.setTipoIdentificacion(tipoIdentificacion);
                if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                    consultEstado.setTipoPersona(ConstantesComunes.EMPLEADORES);
                } else {
                    consultEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                }
                consultEstado.setEntityManager(entityManager);
                /* Se agrega uno por uno el objeto a consultar su estado */
                listConsultaEstado.add(consultEstado);
                /* Se calcula el estado de la persona con respecto a la ccf */
                List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsultaEstado);

                for (EstadoDTO estadoDTO : estados) {
                    if (estadoDTO.getNumeroIdentificacion().equals(numeroIdentificacion) && EstadoAfiliadoEnum.ACTIVO.equals(estadoDTO.getEstado())) {
                        validadorErrores.add("El aportante se encuentra con estado de afiliación Activo");
                        break;
                    }
                }
            }

            logger.debug("Fin de método validarTraspasoCartera");
            return validadorErrores;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en validarTraspasoCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarCarteraVigenteEnvioFisicoElectronico(java.lang.String)
     */
    @Override
    public Boolean validarCarteraVigenteEnvioFisicoElectronico(String numeroRadicacion, ProcesoEnum proceso) {
        String firmaMetodo = "validarCarteraVigenteEnvioFisicoElectronico(String numeroRadicacion,ProcesoEnum proceso)";
        logger.debug("Inicio " + firmaMetodo);
        int validar = 0;
        try {
            /* Se valida el proceso si es fisico o electronico */
            switch (proceso) {
                case GESTION_COBRO_ELECTRONICO:
                    validar = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_ELECTRONICO_VALIDAR_CARTERA_VIGENTE_ENVIO_ELECTRONICO).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).getSingleResult()).intValue();
                    break;

                case GESTION_CARTERA_FISICA_GENERAL:
                    validar = ((Number) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_GESTION_COBRO_FISICO_VALIDAR_CARTERA_VIGENTE_ENVIO_FISICO).setParameter(NUMERO_RADICACION, numeroRadicacion).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).getSingleResult()).intValue();
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return validar > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCiclosVencidos()
     */
    @Override
    public List<CicloCarteraModeloDTO> consultarCiclosVencidos() {
        String firmaMetodo = "consultarCiclosVencidos()";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<CicloCarteraModeloDTO> cicloCarteraModeloDTOs = new ArrayList<>();
        try {

            cicloCarteraModeloDTOs = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_CICLOCARTERA_CONSULTAR_CICLOS_VENCIDOS).setParameter("estadoCiclo", EstadoCicloCarteraEnum.ACTIVO).setParameter("fechaActual", CalendarUtils.truncarHora(new Date())).getResultList();
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return cicloCarteraModeloDTOs;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarHistoricosDesafiliacion()
     */
    @Override
    public List<SolicitudModeloDTO> consultarHistoricosDesafiliacion(UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "consultarHistoricosDesafiliacion()";
        List<SolicitudModeloDTO> solicitudesDTO = new ArrayList<>();
        try {
            logger.debug(LOG_INICIA + firmaMetodo);
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CARTERA_DESAFILIACION_CONSULTAR_HISTORICOS, null);
            List<Solicitud> solicitudes = query.getResultList();

            for (Solicitud solicitud : solicitudes) {
                SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
                solicitudDTO.setFechaCreacion(solicitud.getFechaCreacion() != null ? solicitud.getFechaCreacion().getTime() : null);
                solicitudDTO.setNumeroRadicacion(solicitud.getNumeroRadicacion() != null ? solicitud.getNumeroRadicacion() : null);
                solicitudDTO.setIdSolicitud(solicitud.getIdSolicitud());
                solicitudesDTO.add(solicitudDTO);
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return solicitudesDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudPreventivaAgrupadora(com.asopagos.dto.modelo.SolicitudPreventivaAgrupadoraModeloDTO)
     */
    @Override
    public SolicitudPreventivaAgrupadoraModeloDTO guardarSolicitudPreventivaAgrupadora
    (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO) {
        String firmaMetodo = "guardarSolicitudPreventivaAgrupadora(SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO)";
        logger.debug("Inicio " + firmaMetodo);
        try {
            SolicitudPreventivaAgrupadora solicitudPreventivaAgrupadora = solicitudPreventivaAgrupadoraModeloDTO.convertToEntity();
            if (solicitudPreventivaAgrupadoraModeloDTO.getIdSolicitudPreventivaAgrupadora() == null) {
                entityManager.persist(solicitudPreventivaAgrupadora);
            } else {
                entityManager.merge(solicitudPreventivaAgrupadora);
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            solicitudPreventivaAgrupadoraModeloDTO.convertToDTO(solicitudPreventivaAgrupadora);
            return solicitudPreventivaAgrupadoraModeloDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudPreventivaAgrupadora(java.lang.String)
     */
    @Override
    public SolicitudPreventivaAgrupadoraModeloDTO consultarSolicitudPreventivaAgrupadora(String numeroRadicacion) {
        String firmaMetodo = "consultarSolicitudPreventivaAgrupadora(String numeroRadicacion)";
        try {
            logger.debug("Inicio " + firmaMetodo);
            SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO = new SolicitudPreventivaAgrupadoraModeloDTO();
            SolicitudPreventivaAgrupadora solicitudPreventivaAgrupadora = (SolicitudPreventivaAgrupadora) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_PREVENTIVA_AGRUPADORA_CONSULTAR_SOLICITUD_PREVENTIVA_AGRUPADORA).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();

            if (solicitudPreventivaAgrupadora != null) {
                solicitudPreventivaAgrupadoraModeloDTO.convertToDTO(solicitudPreventivaAgrupadora);
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return solicitudPreventivaAgrupadoraModeloDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCierreSolicitudesPreventivas(java.lang.Long)
     */
    @Override
    public List<SolicitudPreventivaAgrupadoraModeloDTO> consultarCierreSolicitudesPreventivas
    (List<Long> idSolicitudAgrupadora) {
        String firmaMetodo = "consultarCierreSolicitudesPreventivas(Long idSolicitudAgrupadora)";
        try {
            logger.debug("Inicio " + firmaMetodo);

            List<EstadoSolicitudPreventivaEnum> estadoSolicitudPreventiva = new ArrayList<>();
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.ASIGNADA);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.CANCELADA);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.DESISTIDA);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.EN_PROCESO);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.EXITOSA);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.NO_EXITOSA);
            estadoSolicitudPreventiva.add(EstadoSolicitudPreventivaEnum.PENDIENTE_ACTUALIZACION);
            List<SolicitudPreventivaAgrupadoraModeloDTO> solicitudPreventivaAgrupadoraModeloDTO = new ArrayList<>();
            List<SolicitudPreventivaAgrupadora> listSolicitudPreventivaAgrupadoras = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_PREVENTIVA_AGRUPADORA_CONSULTAR_CIERRE_AGRUPADORA).setParameter("estadoSolicitudPreventiva", estadoSolicitudPreventiva).setParameter("idSolicitudAgrupadora", idSolicitudAgrupadora).getResultList();

            if (!listSolicitudPreventivaAgrupadoras.isEmpty()) {
                for (SolicitudPreventivaAgrupadora solicitudPreventivaAgrupadora : listSolicitudPreventivaAgrupadoras) {
                    SolicitudPreventivaAgrupadoraModeloDTO agrupadoraModeloDTO = new SolicitudPreventivaAgrupadoraModeloDTO();
                    agrupadoraModeloDTO.convertToDTO(solicitudPreventivaAgrupadora);
                    solicitudPreventivaAgrupadoraModeloDTO.add(agrupadoraModeloDTO);
                }
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return solicitudPreventivaAgrupadoraModeloDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudesIndividualesCierrePreventiva()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudPreventivaModeloDTO> consultarSolicitudesIndividualesCierrePreventiva() {
        String firmaMetodo = "consultarSolicitudesIndividualesCierrePreventiva";
        try {
            logger.debug("Inicio " + firmaMetodo);

            List<SolicitudPreventiva> listaPreventiva = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_INDIVIDUAL_CIERRE_PREVENTIVA, SolicitudPreventiva.class).setParameter("estadoSolicitud", EstadoSolicitudPreventivaEnum.CERRADA).setParameter("fechaActual", new Date()).getResultList();
            List<SolicitudPreventivaModeloDTO> listaPreventivaDTO = new ArrayList<SolicitudPreventivaModeloDTO>();

            for (SolicitudPreventiva solicitudPreventiva : listaPreventiva) {
                SolicitudPreventivaModeloDTO solicitudDTO = new SolicitudPreventivaModeloDTO();
                solicitudDTO.convertToDTO(solicitudPreventiva);
                listaPreventivaDTO.add(solicitudDTO);
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return listaPreventivaDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudesAgrupadorasCierrePreventiva()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudPreventivaAgrupadoraModeloDTO> consultarSolicitudesAgrupadorasCierrePreventiva() {
        String firmaMetodo = "consultarSolicitudesAgrupadorasCierrePreventiva";
        try {
            logger.debug("Inicio " + firmaMetodo);

            List<SolicitudPreventivaAgrupadora> listaPreventiva = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_AGRUPADORA_CIERRE_PREVENTIVA, SolicitudPreventivaAgrupadora.class).setParameter("estadoSolicitud", EstadoSolicitudPreventivaEnum.CERRADA).getResultList();
            List<SolicitudPreventivaAgrupadoraModeloDTO> listaPreventivaDTO = new ArrayList<SolicitudPreventivaAgrupadoraModeloDTO>();

            for (SolicitudPreventivaAgrupadora solicitudPreventiva : listaPreventiva) {
                SolicitudPreventivaAgrupadoraModeloDTO solicitudDTO = new SolicitudPreventivaAgrupadoraModeloDTO();
                solicitudDTO.convertToDTO(solicitudPreventiva);
                listaPreventivaDTO.add(solicitudDTO);
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return listaPreventivaDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetalleSolicitudesIndividuales(java.lang.String)
     */
    @Override
    public List<SimulacionDTO> consultarDetalleSolicitudesIndividuales(String numeroRadicacion) {
        String firmaMetodo = "consultarDetalleSolicitudesIndividuales(String numeroRadicacion)";
        try {

            List<String> listTipoSolicitante = new ArrayList<>();
            listTipoSolicitante.add(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name());
            listTipoSolicitante.add(TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name());

            logger.debug("Inicio " + firmaMetodo);
            List<SimulacionDTO> lisolicitudPreventivaModeloDTOs = new ArrayList<>();
            List<Object[]> objects = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_SOLICITUD_PREVENTIVA_CONSULTAR_DETALLE_SOLICITUDES_INDIVIDUALES).setParameter("listTipoSolicitante", listTipoSolicitante).setParameter("rolContacto", TipoRolContactoEnum.ROL_RESPONSABLE_APORTES.name()).setParameter(NUMERO_RADICACION, numeroRadicacion).getResultList();

            if (!objects.isEmpty()) {
                for (Object[] object : objects) {
                    SimulacionDTO detallePreventiva = new SimulacionDTO();
                    detallePreventiva.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(object[0].toString()));
                    detallePreventiva.setNumeroIdentificacion(object[1].toString());
                    detallePreventiva.setNombreRazonSocial(object[2].toString());
                    if (object[3] != null) {
                        detallePreventiva.setCorreoElectronico(object[3].toString());
                    }
                    detallePreventiva.setEstadoActualCartera(EstadoCarteraEnum.valueOf(object[4].toString()));
                    if (object[5] != null) {
                        detallePreventiva.setValorPromedioAportes(new BigDecimal(object[5].toString()));
                    }
                    if (object[6] != null) {
                        detallePreventiva.setTrabajadoresActivos(Integer.valueOf(object[6].toString()));
                    }
                    if (object[7] != null) {
                        detallePreventiva.setCantidadVecesMoroso(new Short(object[7].toString()));
                    }
                    if (object[8] != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateInString = object[8].toString();
                        Date fecha = formatter.parse(dateInString);
                        detallePreventiva.setFechaLimitePago(fecha.getTime());
                    }
                    detallePreventiva.setEstadoSolicitudPreventiva(EstadoSolicitudPreventivaEnum.valueOf(object[9].toString()));
                    detallePreventiva.setNumeroOperacion(object[10].toString());
                    lisolicitudPreventivaModeloDTOs.add(detallePreventiva);
                }

            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return lisolicitudPreventivaModeloDTOs;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDocumentosDesafiliacion(java.lang.String)
     */
    @Override
    public List<DocumentoDesafiliacionModeloDTO> consultarDocumentosDesafiliacion(String numeroRadicacion) {
        String firmaMetodo = "consultarDocumentosDesafiliacion(String numeroRadicacion)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            List<DocumentoDesafiliacionModeloDTO> documentoDesafiliacionModeloDTOs = new ArrayList<>();
            List<DocumentoDesafiliacion> documentoDesafiliacions = entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_DESAFILIACION_CONSULTAR_DOCUMENTOS_DESAFILIACION).setParameter(NUMERO_RADICACION, numeroRadicacion).getResultList();

            if (documentoDesafiliacions != null) {
                for (DocumentoDesafiliacion documentoDesafiliacion : documentoDesafiliacions) {
                    DocumentoDesafiliacionModeloDTO desafiliacionModeloDTO = new DocumentoDesafiliacionModeloDTO();
                    desafiliacionModeloDTO.convertToDTO(documentoDesafiliacion);
                    documentoDesafiliacionModeloDTOs.add(desafiliacionModeloDTO);
                }
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return documentoDesafiliacionModeloDTOs;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarActivacionLineaDeCobroAnterior(com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum)
     */
    @Override
    public void actualizarActivacionMetodoGestionCobro(MetodoAccionCobroEnum metodoAnterior) {
        String firmaMetodo = "actualizarActivacionLineaDeCobroAnterior(MetodoAccionCobroEnum metodoAnterior)";
        try {
            logger.debug("Inicio " + firmaMetodo);
            entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_PARAMETRIZACION_GESTION_COBRO_ACTUALIZAR_ACTIVACION_METODO_ANTERIOR).setParameter("metodoAnterior", metodoAnterior).setParameter("lineaCobro", TipoLineaCobroEnum.LC1).executeUpdate();
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Angelica
     */

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarActualizacionDatos(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActualizacionDatosDTO consultarActualizacionDatos(String numeroRadicacion) {
        try {
            logger.debug("Inicio de método consultarActualizacionDatos (String numeroRadicacion)");
            Object[] datosActualizacion = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_DATOS_ACTUALIZACION).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
            ActualizacionDatosDTO actualizacionDatos = new ActualizacionDatosDTO();
            actualizacionDatos.setIdPersona(new Long(((BigInteger) datosActualizacion[0]).toString()));
            actualizacionDatos.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) datosActualizacion[1]));
            actualizacionDatos.setFechaRadicacion(datosActualizacion[2] != null ? ((Date) (datosActualizacion[2])).getTime() : null);
            logger.debug("Fin de método consultarActualizacionDatos (String numeroRadicacion)");
            return actualizacionDatos;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarActualizacionDatos (String numeroRadicacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Silvio
     */

    /**
     * Claudia
     */

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.cartera.service.CarteraService#obtenerRolesDestinatarios(com.asopagos.dto.modelo.ParametrizacionGestionCobroModeloDTO,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatarios(ParametrizacionGestionCobroModeloDTO
                                                                                  parametrizacion, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoLineaCobroEnum
                                                                                  lineaCobro) {
        try {
            List<AutorizacionEnvioComunicadoDTO> destinatarios = new ArrayList<>();

            logger.info("Ingresa?? "+ lineaCobro);
            if ((!lineaCobro.equals(TipoLineaCobroEnum.LC1) && !lineaCobro.equals(TipoLineaCobroEnum.LC2) && !lineaCobro.equals(TipoLineaCobroEnum.LC3) && TipoParametrizacionGestionCobroEnum.LINEA_COBRO.equals(parametrizacion.getTipoParametrizacion()))||(lineaCobro.equals(TipoLineaCobroEnum.LC4) || (lineaCobro.equals(TipoLineaCobroEnum.LC5)))) {
                logger.info("LineaCobro"+ lineaCobro);
                List<AutorizacionEnvioComunicadoDTO> correos = new ArrayList<>();
                correos = entityManager.createNamedQuery(NamedQueriesConstants.CORREO_RESPONSABLE_AFILIADO, AutorizacionEnvioComunicadoDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
                destinatarios.addAll(correos);
            } else {
                if (parametrizacion.getOficinaPrincipalElectronico()) {
                    List<AutorizacionEnvioComunicadoDTO> correos = new ArrayList<>();
                    correos = entityManager.createNamedQuery(NamedQueriesConstants.CORREO_OFICINA_PRINCIPAL, AutorizacionEnvioComunicadoDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
                    destinatarios.addAll(correos);
                }

                if (parametrizacion.getRepresentanteLegalElectronico()) {
                    List<AutorizacionEnvioComunicadoDTO> correos = new ArrayList<>();
                    correos = entityManager.createNamedQuery(NamedQueriesConstants.CORREO_REPRESENTANTE_LEGAL, AutorizacionEnvioComunicadoDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
                    destinatarios.addAll(correos);
                }

                if (parametrizacion.getResponsableAportesElectronico()) {
                    List<AutorizacionEnvioComunicadoDTO> correos = new ArrayList<>();
                    correos = entityManager.createNamedQuery(NamedQueriesConstants.CORREO_RESPONSABLE_APORTES, AutorizacionEnvioComunicadoDTO.class).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
                    destinatarios.addAll(correos);
                }
            }

            return destinatarios;
        } catch (Exception e) {
            logger.error("Finaliza el método obtenerRolesDestinatarios(ParametrizacionGestionCobroModeloDTO, TipoIdentificacionEnum, String): Error técnico Inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDetalleSolicitudGestionCobro(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleSolicitudGestionCobro
    (List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion) {
        logger.debug("Inicia consultarDetalleSolicitudGestionCobro(FiltroDetalleSolicitudGestionCobroDTO)");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String filtro = mapper.writeValueAsString(filtroDetalleSolicitudGestion);

            List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudesGestion = new ArrayList<>();
            List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_INTEGRADO).setParameter("filtros", filtro).getResultList();

            for (Object[] detalleSolicitudCobro : resultado) {

                DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroDTO = new DetalleSolicitudGestionCobroModeloDTO();

                detalleSolicitudGestionCobroDTO.setEnviarPrimeraRemision(detalleSolicitudCobro[0] != null ? (Boolean) detalleSolicitudCobro[0] : false);
                detalleSolicitudGestionCobroDTO.setEnviarSegundaRemision(detalleSolicitudCobro[1] != null ? (Boolean) detalleSolicitudCobro[1] : false);
                detalleSolicitudGestionCobroDTO.setEstadoSolicitud(detalleSolicitudCobro[2] != null ? (EstadoTareaGestionCobroEnum.valueOf((String) detalleSolicitudCobro[2])) : null);
                detalleSolicitudGestionCobroDTO.setFechaPrimeraEntrega(detalleSolicitudCobro[3] != null ? ((Date) detalleSolicitudCobro[3]).getTime() : null);
                detalleSolicitudGestionCobroDTO.setFechaSegundaEntrega(detalleSolicitudCobro[4] != null ? ((Date) detalleSolicitudCobro[4]).getTime() : null);

                detalleSolicitudGestionCobroDTO.setFechaPrimeraEntregaString(detalleSolicitudCobro[3] != null ? ((Date) detalleSolicitudCobro[3]).toString() : null);
                detalleSolicitudGestionCobroDTO.setFechaSegundaEntregaString(detalleSolicitudCobro[4] != null ? ((Date) detalleSolicitudCobro[4]).toString() : null);

                detalleSolicitudGestionCobroDTO.setIdCartera(((BigInteger) detalleSolicitudCobro[5]).longValue());
                detalleSolicitudGestionCobroDTO.setObservacionPrimeraEntrega(detalleSolicitudCobro[6] != null ? ((String) detalleSolicitudCobro[6]) : null);
                detalleSolicitudGestionCobroDTO.setObservacionPrimeraRemision(detalleSolicitudCobro[7] != null ? ((String) detalleSolicitudCobro[7]) : null);
                detalleSolicitudGestionCobroDTO.setObservacionSegundaEntrega(detalleSolicitudCobro[8] != null ? ((String) detalleSolicitudCobro[8]) : null);
                detalleSolicitudGestionCobroDTO.setObservacionSegundaRemision(detalleSolicitudCobro[9] != null ? ((String) detalleSolicitudCobro[9]) : null);
                detalleSolicitudGestionCobroDTO.setIdPrimeraSolicitudRemision(detalleSolicitudCobro[10] != null ? ((BigInteger) detalleSolicitudCobro[10]).longValue() : null);
                detalleSolicitudGestionCobroDTO.setIdSegundaSolicitudRemision(detalleSolicitudCobro[11] != null ? ((BigInteger) detalleSolicitudCobro[11]).longValue() : null);
                detalleSolicitudGestionCobroDTO.setResultadoPrimeraEntrega(detalleSolicitudCobro[12] != null ? (ResultadoEntregaEnum.valueOf((String) detalleSolicitudCobro[12])) : null);
                detalleSolicitudGestionCobroDTO.setResultadoSegundaEntrega(detalleSolicitudCobro[13] != null ? (ResultadoEntregaEnum.valueOf((String) detalleSolicitudCobro[13])) : null);
                detalleSolicitudGestionCobroDTO.setIdDetalleSolicitudGestionCobro(((BigInteger) detalleSolicitudCobro[14]).longValue());
                detalleSolicitudGestionCobroDTO.setIdPersona(detalleSolicitudCobro[15] != null ? (((BigInteger) detalleSolicitudCobro[15])).longValue() : null);
                detalleSolicitudGestionCobroDTO.setTipoIdentificacion(detalleSolicitudCobro[16] != null ? (TipoIdentificacionEnum.valueOf((String) detalleSolicitudCobro[16])) : null);
                detalleSolicitudGestionCobroDTO.setNumeroIdentificacion(detalleSolicitudCobro[17] != null ? ((String) detalleSolicitudCobro[17]) : null);
                detalleSolicitudGestionCobroDTO.setTipoSolicitante(detalleSolicitudCobro[18] != null ? (TipoSolicitanteMovimientoAporteEnum.valueOf((String) detalleSolicitudCobro[18])) : null);
                detalleSolicitudGestionCobroDTO.setNumeroOperacion(detalleSolicitudCobro[19] != null ? ((BigInteger) detalleSolicitudCobro[19]).longValue() : null);

                //Existe documento de primera remisión
                if (detalleSolicitudCobro[20] != null) {
                    DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
                    documentoSoporteModeloDTO.setIdDocumentoSoporte(((BigInteger) detalleSolicitudCobro[20]).longValue());
                    documentoSoporteModeloDTO.setNombre(detalleSolicitudCobro[21] != null ? ((String) detalleSolicitudCobro[21]) : null);
                    documentoSoporteModeloDTO.setDescripcionComentarios(detalleSolicitudCobro[22] != null ? ((String) detalleSolicitudCobro[22]) : null);
                    documentoSoporteModeloDTO.setIdentificacionDocumento(detalleSolicitudCobro[23] != null ? ((String) detalleSolicitudCobro[23]) : null);
                    documentoSoporteModeloDTO.setVersionDocumento(detalleSolicitudCobro[24] != null ? ((String) detalleSolicitudCobro[24]) : null);
                    documentoSoporteModeloDTO.setFechaHoraCargue(detalleSolicitudCobro[25] != null ? ((Date) detalleSolicitudCobro[25]).getTime() : null);
                    documentoSoporteModeloDTO.setTipoDocumento(detalleSolicitudCobro[26] != null ? (TipoDocumentoAdjuntoEnum.valueOf((String) detalleSolicitudCobro[26])) : null);
                    documentoSoporteModeloDTO.setIdBitacoraCartera(detalleSolicitudCobro[27] != null ? ((BigInteger) detalleSolicitudCobro[27]).longValue() : null);
                    documentoSoporteModeloDTO.setIdTipoDocumentoSoporteFovis(detalleSolicitudCobro[28] != null ? ((BigInteger) detalleSolicitudCobro[28]).longValue() : null);
                    detalleSolicitudGestionCobroDTO.setDocumentoPrimeraRemision(documentoSoporteModeloDTO);
                }

                //Existe documento de segunda remisión
                if (detalleSolicitudCobro[29] != null) {
                    DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
                    documentoSoporteModeloDTO.setIdDocumentoSoporte(((BigInteger) detalleSolicitudCobro[29]).longValue());
                    documentoSoporteModeloDTO.setNombre(detalleSolicitudCobro[30] != null ? ((String) detalleSolicitudCobro[30]) : null);
                    documentoSoporteModeloDTO.setDescripcionComentarios(detalleSolicitudCobro[31] != null ? ((String) detalleSolicitudCobro[31]) : null);
                    documentoSoporteModeloDTO.setIdentificacionDocumento(detalleSolicitudCobro[32] != null ? ((String) detalleSolicitudCobro[32]) : null);
                    documentoSoporteModeloDTO.setVersionDocumento(detalleSolicitudCobro[33] != null ? ((String) detalleSolicitudCobro[33]) : null);
                    documentoSoporteModeloDTO.setFechaHoraCargue(detalleSolicitudCobro[34] != null ? ((Date) detalleSolicitudCobro[34]).getTime() : null);
                    documentoSoporteModeloDTO.setTipoDocumento(detalleSolicitudCobro[35] != null ? (TipoDocumentoAdjuntoEnum.valueOf((String) detalleSolicitudCobro[35])) : null);
                    documentoSoporteModeloDTO.setIdBitacoraCartera(detalleSolicitudCobro[36] != null ? ((BigInteger) detalleSolicitudCobro[36]).longValue() : null);
                    documentoSoporteModeloDTO.setIdTipoDocumentoSoporteFovis(detalleSolicitudCobro[37] != null ? ((BigInteger) detalleSolicitudCobro[37]).longValue() : null);
                    detalleSolicitudGestionCobroDTO.setDocumentoSegundaRemision(documentoSoporteModeloDTO);
                }

                detallesSolicitudesGestion.add(detalleSolicitudGestionCobroDTO);
            }
            resultado = null;
            logger.debug("Finalizar consultarDetalleSolicitudGestionCobro(FiltroDetalleSolicitudGestionCobroDTO)");
            return detallesSolicitudesGestion;

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarDetalleSolicitudGestionCobro(FiltroDetalleSolicitudGestionCobroDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Ferney
     */

    /**
     * Método que se encarga de construir los filtros necesarios para buscar los aportantes.
     *
     * @param parametrizacionCartera parametrización de cartera.
     * @return filtros de parametrización.
     */
    private FiltrosParametrizacionDTO construirFiltrosParametrizacionGestionCobro(ParametrizacionCarteraModeloDTO
                                                                                          parametrizacionCartera) {
        logger.debug("Inicio de método construirFiltrosParametrizacionGestionCobro(ParametrizacionCarteraModeloDTO)");

        FiltrosParametrizacionDTO filtroParametrizacion = new FiltrosParametrizacionDTO();
        // Filtro estadoCartera
        List<String> estadosCartera = new ArrayList<>();
        if (parametrizacionCartera.getEstadoCartera() != null) {
            estadosCartera.add(parametrizacionCartera.getEstadoCartera().name());
        } else {
            StringBuilder estados = new StringBuilder();
            estados.append(EstadoCarteraEnum.AL_DIA.name());
            estados.append(",");
            estados.append(EstadoCarteraEnum.MOROSO.name());
            estadosCartera.add(estados.toString());
        }
        filtroParametrizacion.setEstadosCartera(estadosCartera);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate fechaActual = LocalDate.now();
        fechaActual = fechaActual.minusMonths(1L);
        String periodoFinalEmp = formatter.format(fechaActual);
        filtroParametrizacion.setPeriodoFinalAportesEmpleador(periodoFinalEmp);
        fechaActual = fechaActual.minusMonths(parametrizacionCartera.getCantidadPeriodos());
        String periodoInicialEmp = formatter.format(fechaActual);
        filtroParametrizacion.setPeriodoInicialAportesEmpleador(periodoInicialEmp);

        // Filtro trabajadoresActivos
        if (parametrizacionCartera.getTrabajadoresActivos() != null) {
            filtroParametrizacion.setCantidadTrabajadoresActivos(parametrizacionCartera.getTrabajadoresActivos().getCantidad().longValue());
        }

        /* valida si tiene periodos morosos para settear la fecha de morosidad caso empleador */
        if (!PeriodoRegularEnum.NO_APLICAR_CRITERIO.equals(parametrizacionCartera.getPeriodosMorosos())) {
            LocalDate fechaMoraEmp = LocalDate.now();

            fechaMoraEmp = fechaMoraEmp.minusMonths(1L);
            fechaMoraEmp = fechaMoraEmp.with(lastDayOfMonth());
            Date periodoFinalMorosoEmpleador = Date.from(fechaMoraEmp.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
            filtroParametrizacion.setPeriodoFinalMorosoEmpleador(periodoFinalMorosoEmpleador);

            fechaMoraEmp = fechaMoraEmp.minusMonths(parametrizacionCartera.getCantidadPeriodos());
            fechaMoraEmp = fechaMoraEmp.with(firstDayOfMonth());
            Date periodoInicialMorosoEmpleador = Date.from(fechaMoraEmp.atStartOfDay(ZoneId.systemDefault()).toInstant());
            filtroParametrizacion.setPeriodoInicialMorosoEmpleador(periodoInicialMorosoEmpleador);
        }

        /* Valida si tiene periodos morosos para settear la fecha de morosidad caso independientes y pensionados */
        if ((parametrizacionCartera.getIncluirIndependientes() != null && parametrizacionCartera.getIncluirIndependientes()) || (parametrizacionCartera.getIncluirPensionados() != null && parametrizacionCartera.getIncluirPensionados())) {
            LocalDate fechaActualInd = LocalDate.now();
            //fechaActualInd = fechaActualInd.minusMonths(1L);
            String periodoFinal = formatter.format(fechaActualInd);
            filtroParametrizacion.setPeriodoFinalAportes(periodoFinal);
            fechaActualInd = fechaActualInd.minusMonths(parametrizacionCartera.getCantidadPeriodos());
            String periodoInicial = formatter.format(fechaActualInd);
            filtroParametrizacion.setPeriodoInicialAportes(periodoInicial);

            if (!PeriodoRegularEnum.NO_APLICAR_CRITERIO.equals(parametrizacionCartera.getPeriodosMorosos())) {
                LocalDate fechaMora = LocalDate.now();

                //fechaMora = fechaMora.minusMonths(1L);
                fechaMora = fechaMora.with(lastDayOfMonth());
                Date periodoFinalMoroso = Date.from(fechaMora.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
                filtroParametrizacion.setPeriodoFinalMoroso(periodoFinalMoroso);

                fechaMora = fechaMora.minusMonths(parametrizacionCartera.getCantidadPeriodos());
                fechaMora = fechaMora.with(firstDayOfMonth());
                Date periodoInicialMoroso = Date.from(fechaMora.atStartOfDay(ZoneId.systemDefault()).toInstant());
                filtroParametrizacion.setPeriodoInicialMoroso(periodoInicialMoroso);
            }
        }

        /* Valida si aplica criterio o no */
        if (!PeriodoRegularEnum.NO_APLICAR_CRITERIO.equals(parametrizacionCartera.getPeriodosMorosos())) {
            filtroParametrizacion.setAplicarCriterio(Boolean.TRUE);
        } else {
            filtroParametrizacion.setAplicarCriterio(Boolean.FALSE);
        }

        filtroParametrizacion.setIncluirIndependientes(parametrizacionCartera.getIncluirIndependientes());
        filtroParametrizacion.setIncluirPensionados(parametrizacionCartera.getIncluirPensionados());
        filtroParametrizacion.setMayorTrabajadoresActivos(parametrizacionCartera.getMayorTrabajadoresActivos());
        filtroParametrizacion.setMayorValorPromedio(parametrizacionCartera.getMayorValorPromedio());
        filtroParametrizacion.setMayorVecesMoroso(parametrizacionCartera.getMayorVecesMoroso());
        logger.debug("Fin de método de método construirFiltrosParametrizacionGestionCobro(ParametrizacionCarteraModeloDTO)");

        return filtroParametrizacion;
    }

    /**
     * Metodo que guarda la parametrización de criterio gestión de cobro
     *
     * @param gestionCobro objeto que contiene la información para ser almacenada en la bd
     */
    private void guardarParametrizacionCriterioGestionCobro(ParametrizacionCriterioGestionCobro gestionCobro) {
        logger.debug("Inicia metodo guardarParametrizacionGestionCobro(ParametrizacionGestionCobro gestionCobro)");
        /* Se actualiza el objeto si el IdParametrizacionCartera existe */
        if (gestionCobro.getIdParametrizacionCartera() != null) {
            entityManager.merge(gestionCobro);
            logger.debug("Se realiza merge del objeto");
        } else {
            /* Se persiste la información de la parametrización de gestion de cobro modelo */
            entityManager.persist(gestionCobro);
            logger.debug("Se realiza persist del objeto");
        }
        logger.debug("Finaliza metodo guardarParametrizacionGestionCobro(ParametrizacionGestionCobro gestionCobro)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudGestionCobroElectronico(com.asopagos.dto.modelo.
     * SolicitudGestionCobroElectronicoModeloDTO)
     */
    @Override
    public SolicitudGestionCobroElectronicoModeloDTO guardarSolicitudGestionCobroElectronico
    (SolicitudGestionCobroElectronicoModeloDTO solicitudDTO) {
        try {
            logger.debug("Inicio de servicio guardarSolicitudGestionCobroElectronico");
            SolicitudGestionCobroElectronico solicitud = solicitudDTO.convertToEntity();
            SolicitudGestionCobroElectronico managed = entityManager.merge(solicitud);
            logger.debug("Inicio de servicio guardarSolicitudGestionCobroElectronico");
            solicitudDTO.convertToDTO(managed);
            return solicitudDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarSolicitudGestionCobroElectronico", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#obtenerUltimoAnalistaAsignado
     */
    @Override
    public String obtenerUltimoAnalistaAsignado() {
        try {
            logger.debug("Inicio de método obtenerUltimoAnalistaAsignado");
            String ultimoUsuario = null;

            Object resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMO_ANALISTA_FISICO).getSingleResult();
            ultimoUsuario = resultado != null ? resultado.toString() : null;

            logger.debug("Fin de método obtenerUltimoAnalistaAsignado");
            return ultimoUsuario;
        } catch (NoResultException nre) {
            logger.debug("Fin de método obtenerUltimoAnalistaAsignado, no se encontró ningún usuario");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en obtenerUltimoAnalistaAsignado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDocumentoCartera(com.asopagos.dto.modelo.DocumentoCarteraModeloDTO)
     */
    @Override
    public DocumentoCarteraModeloDTO guardarDocumentoCartera(DocumentoCarteraModeloDTO documentoCarteraDTO) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            logger.debug("Inicio de servicio guardarDocumentoCartera :: " + mapper.writeValueAsString(documentoCarteraDTO));
            DocumentoCartera documentoCartera = documentoCarteraDTO.convertToDocumentoCarteraEntity();
            DocumentoCartera managed = entityManager.merge(documentoCartera);
            logger.debug("Fin de servicio guardarDocumentoCartera");
            documentoCarteraDTO.convertToDTO(managed);
            return documentoCarteraDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarDocumentoCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public DocumentoCartera guardarDocumentoCarteraEnt(DocumentoCarteraModeloDTO documentoCarteraDTO) {
        try {
            logger.debug("Inicio de servicio guardarDocumentoCarteraEnt");
            DocumentoCartera documentoCartera = documentoCarteraDTO.convertToDocumentoCarteraEntity();
            DocumentoCartera managed = entityManager.merge(documentoCartera);
            logger.debug("Fin de servicio guardarDocumentoCartera");
            return managed;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarDocumentoCarteraEnt", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDocumentoCartera(java.lang.Long,
     * com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DocumentoCarteraModeloDTO consultarDocumentoCartera(Long numeroOperacion, TipoAccionCobroEnum
            accionCobro) {
        try {
            logger.debug("Inicio de servicio guardarDocumentoCartera");
            List<DocumentoCartera> listaDocumento = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_CARTERA, DocumentoCartera.class).setParameter("accionCobro", accionCobro).setParameter("numeroOperacion", numeroOperacion).getResultList();
            DocumentoCarteraModeloDTO documentoDTO = null;

            if (listaDocumento != null) {
                documentoDTO = new DocumentoCarteraModeloDTO();
                documentoDTO.convertToDTO(listaDocumento.get(0));
            }

            logger.debug("Fin de servicio guardarDocumentoCartera");
            return documentoDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en guardarDocumentoCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarMetodoActivoLC1()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public MetodoAccionCobroEnum consultarMetodoActivoLC1() {
        try {
            logger.debug("Inicio de servicio consultarMetodoActivoLC1");
            MetodoAccionCobroEnum metodo = (MetodoAccionCobroEnum) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_METODO_ACTIVO_LC1).setParameter(TIPO_LINEA_COBRO, TipoLineaCobroEnum.LC1).setParameter("estado", Boolean.TRUE).setParameter("accion", TipoGestionCarteraEnum.AUTOMATICA).getSingleResult();
            logger.debug("Fin de servicio consultarMetodoActivoLC1");
            return metodo;
        } catch (NoResultException ex) {
            logger.error("Fin de servicio consultarMetodoActivoLC1 - Recurso no encontrado");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarMetodoActivoLC1", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDeuda(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,java.lang.
     * String,com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DeudaDTO> consultarDeuda(TipoIdentificacionEnum tipoIdentificacion, String
            numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoLineaCobroEnum lineaCobro) {
        String firmaMetodo = "consultarDeuda(TipoIdentificacionEnum,String,TipoSolicitanteMovimientoAporteEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<DeudaDTO> lstDeudaDTO = new ArrayList<>();
        try {
            String consultaDeuda = NamedQueriesConstants.CONSULTAR_DEUDA_APORTANTE_TIPO_PERSONA;
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                consultaDeuda = NamedQueriesConstants.CONSULTAR_DEUDA_APORTANTE_TIPO_EMPLEADOR;
            }
            lstDeudaDTO = (List<DeudaDTO>) entityManager.createNamedQuery(consultaDeuda).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoSolicitante", tipoSolicitante).setParameter("lineaCobro", lineaCobro).getResultList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return !lstDeudaDTO.isEmpty() ? lstDeudaDTO : null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarBitacora(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<BitacoraCarteraDTO> consultarBitacora(Long numeroOperacion) {
        String firmaMetodo = "consultarBitacora";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<BitacoraCarteraDTO> bitacorasCartera = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            bitacorasCartera = (List<BitacoraCarteraDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_APORTANTE_CARTERA).setParameter("numeroOperacion", numeroOperacion.toString()).getResultList();
            List<Long> lstIdBitacora = new ArrayList<>();
            logger.info("la consulta de Bitacora devuelve :: " + mapper.writeValueAsString(bitacorasCartera));
            if (!bitacorasCartera.isEmpty()) {
                for (BitacoraCarteraDTO bitacoraCarteraDTO : bitacorasCartera) {
                    lstIdBitacora.add(bitacoraCarteraDTO.getIdBitacoraCartera());
                }

                bitacorasCartera = consultarDocumentoSoporteBitacora(bitacorasCartera, lstIdBitacora);
            }
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return !bitacorasCartera.isEmpty() ? bitacorasCartera : null;
    }

    /**
     * Método encargado de consultar los documentos pertenecientes a la bitacora de cartera
     *
     * @param bitacorasCartera
     * @param lstIdBitacora,   lista de id de bitacoras de cartea
     * @return Listado de bitacoras de cartera dto con los documentos
     */
    private List<BitacoraCarteraDTO> consultarDocumentoSoporteBitacora
    (List<BitacoraCarteraDTO> bitacorasCartera, List<Long> lstIdBitacora) {
        List<DocumentoSoporteModeloDTO> documentosSoporte = (List<DocumentoSoporteModeloDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_SOPORTE_BITACORA).setParameter("lstIdBitacoraCartera", lstIdBitacora).getResultList();
        if (!documentosSoporte.isEmpty()) {
            for (BitacoraCarteraDTO bitacoraCarteraDTO : bitacorasCartera) {
                List<DocumentoSoporteModeloDTO> listaDocumentosSoporte = new ArrayList<>();
                for (DocumentoSoporteModeloDTO documentoSoporte : documentosSoporte) {
                    if (bitacoraCarteraDTO.getIdBitacoraCartera().equals(documentoSoporte.getIdBitacoraCartera())) {
                        listaDocumentosSoporte.add(documentoSoporte);
                    }
                }
                bitacoraCarteraDTO.setDocumentosSoporte(listaDocumentosSoporte);
            }
        }
        return bitacorasCartera;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarBitacoraCartera(com.asopagos.cartera.dto.BitacoraCarteraDTO)
     */
    @Override
    public Long guardarBitacoraCartera(BitacoraCarteraDTO bitacoraCartera, UserDTO userDTO) {
        String firmaMetodo = "guardarBitacoraCartera(BitacoraCarteraDTO)";
        try {
            logger.debug(LOG_INICIA + firmaMetodo);
            bitacoraCartera.setFecha(new Date().getTime());
            if (bitacoraCartera.getUsuario() == null || bitacoraCartera.getUsuario().equals("")) {
                bitacoraCartera.setUsuario(userDTO.getNombreUsuario() != null ? userDTO.getNombreUsuario() : "SISTEMA");
            }
            BitacoraCartera bitacora = bitacoraCartera.convertToEntity();
            logger.info("llega hasta el merge de bitacora");
            bitacora = entityManager.merge(bitacora);
            logger.info("Paso del merge de bitacora");
            logger.info(bitacora.toString());
            List<DocumentoSoporteModeloDTO> documentosSoporte = bitacoraCartera.getDocumentosSoporte();

            if (documentosSoporte != null && !documentosSoporte.isEmpty()) {
                for (DocumentoSoporteModeloDTO documento : documentosSoporte) {
                    DocumentoSoporte docSoporte = null;
                    if (documento != null) {
                        if (ResultadoBitacoraCarteraEnum.ENVIADO.equals(bitacora.getResultado()) || ResultadoBitacoraCarteraEnum.NO_ENVIADO.equals(bitacora.getResultado())) {
                            documento.setIdBitacoraCartera(bitacora.getIdBitacoraCartera());
                        } else {
                            documento.setIdBitacoraCartera(bitacora.getIdBitacoraCartera());
                            documento.setIdDocumentoSoporte(null);
                        }
                        docSoporte = documento.convertToEntity();
                        entityManager.merge(docSoporte);
                    }
                }
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return bitacora.getIdBitacoraCartera();
        } catch (Exception e) {
            logger.info("error al almacenar en bitacora cartera " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarBitacoraCartera(com.asopagos.cartera.dto.BitacoraCarteraDTO)
     */
    @Override
    public void guardarListaBitacorasCarteraPorIdCartera(List<BitacoraCarteraDTO> listaBitacorasCartera, UserDTO
            userDTO) {
        String firmaMetodo = "guardarListaBitacorasCarteraPorIdCartera(List<BitacoraCarteraDTO>)";
        logger.debug(LOG_INICIA + firmaMetodo);
        logger.info("guardarListaBitacorasCarteraPorIdCartera" + listaBitacorasCartera.toString() + "UserDTO" + userDTO.getNombreUsuario());
        String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
        Integer tamanoPaginador = new Integer(sTamanoPaginador);
        Integer count = 0;
        List<BitacoraCarteraDTO> listaBitacorasCarterapagina = new ArrayList<>();
        List<Long> listaIdCartera = new ArrayList<>();
        for (BitacoraCarteraDTO bitacoraCartera : listaBitacorasCartera) {
            count++;
            listaIdCartera.add(bitacoraCartera.getIdCartera());
            listaBitacorasCarterapagina.add(bitacoraCartera);
            if (count.intValue() == tamanoPaginador.intValue()) {
                guardarListaBitacorasCarteraPorIdCartera(listaBitacorasCarterapagina, listaIdCartera, userDTO);
                count = 0;
                listaBitacorasCarterapagina = new ArrayList<>();
                listaIdCartera = new ArrayList<>();
            }
        }
        if (count.intValue() > 0) {
            guardarListaBitacorasCarteraPorIdCartera(listaBitacorasCarterapagina, listaIdCartera, userDTO);
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
    }

    /**
     * @param listaBitacorasCartera
     * @param listaIdCartera
     */
    private void guardarListaBitacorasCarteraPorIdCartera
    (List<BitacoraCarteraDTO> listaBitacorasCartera, List<Long> listaIdCartera, UserDTO userDTO) {
        String firmaMetodo = "guardarListaBitacorasCarteraPorIdCartera(List<BitacoraCarteraDTO>)";
        logger.debug(LOG_INICIA + firmaMetodo);

        List<Object[]> listaData = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_CARTERA_NUMERO_OPERACION).setParameter("listaIdCartera", listaIdCartera).getResultList();

        int carId = 0;
        int carPersona = 1;
        int carTipoSolicitante = 2;
        int cagNumeroOperacion = 3;
        Map<Long, Object[]> datosOrdenados = new HashMap<>();
        for (Object[] data : listaData) {
            datosOrdenados.put(Long.valueOf(data[carId].toString()), data);
        }

        for (BitacoraCarteraDTO bitacoraCartera : listaBitacorasCartera) {
            if (datosOrdenados.containsKey(bitacoraCartera.getIdCartera())) {

                Object[] data = datosOrdenados.get(bitacoraCartera.getIdCartera());
                bitacoraCartera.setIdPersona(Long.valueOf(data[carPersona].toString()));
                bitacoraCartera.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf(data[carTipoSolicitante].toString()));
                bitacoraCartera.setNumeroOperacion(data[cagNumeroOperacion].toString());
                bitacoraCartera.setFecha(new Date().getTime());
                bitacoraCartera.setUsuario(userDTO.getNombreUsuario() != null ? userDTO.getNombreUsuario() : "SISTEMA");
                BitacoraCartera bitacora = bitacoraCartera.convertToEntity();
                bitacora = entityManager.merge(bitacora);
                List<DocumentoSoporteModeloDTO> documentosSoporte = bitacoraCartera.getDocumentosSoporte();

                if (documentosSoporte != null && !documentosSoporte.isEmpty()) {
                    for (DocumentoSoporteModeloDTO documento : documentosSoporte) {
                        DocumentoSoporte docSoporte = null;
                        if (documento != null) {
                            documento.setIdBitacoraCartera(documento.getIdBitacoraCartera() != null ? documento.getIdBitacoraCartera() : bitacora.getIdBitacoraCartera());
                            docSoporte = documento.convertToEntity();
                            entityManager.merge(docSoporte);
                        }
                    }
                }
            }
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudGestionCobroManual(java.lang.String, java.lang.Long)
     */
    @Override
    public SolicitudGestionCobroManualModeloDTO consultarSolicitudGestionCobroManual(String numeroRadicacion, Long
            numeroOperacion) {
        String firmaMetodo = "consultarSolicitudGestionCobroManual(String)";
        List<SolicitudGestionCobroManualModeloDTO> listaManual = new ArrayList<SolicitudGestionCobroManualModeloDTO>();

        try {
            if (numeroRadicacion != null) {
                listaManual = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL, SolicitudGestionCobroManualModeloDTO.class).setParameter(NUMERO_RADICACION, numeroRadicacion).getResultList();
            }

            if (listaManual.isEmpty() && numeroOperacion != null) {
                listaManual = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL_CARTERA, SolicitudGestionCobroManualModeloDTO.class).setParameter("numeroOperacion", numeroOperacion).getResultList();
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        return !listaManual.isEmpty() ? listaManual.get(0) : null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarSolicitudGestionCobroManualEstado(java.lang.String)
     */
    @Override
    public SolicitudGestionCobroManualModeloDTO consultarSolicitudGestionCobroManualEstado(String numeroRadicacion) {
        String firmaMetodo = "consultarSolicitudGestionCobroManual(String)";
        List<SolicitudGestionCobroManualModeloDTO> listaManual = new ArrayList<SolicitudGestionCobroManualModeloDTO>();
        try {
            if (numeroRadicacion != null) {
                listaManual = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL_ESTADO, SolicitudGestionCobroManualModeloDTO.class).setParameter(NUMERO_RADICACION, numeroRadicacion).getResultList();
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return !listaManual.isEmpty() ? listaManual.get(0) : null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarEstadoSolicitudGestionCobroManual(java.lang.String,
     * com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum)
     */
    @Override
    public void actualizarEstadoSolicitudGestionCobroManual(String numeroRadicacion, EstadoFiscalizacionEnum
            estadoSolicitud) {
        String firmaMetodo = "actualizarEstadoSolicitudGestionCobroManual(String,EstadoCicloCarteraEnum)";
        try {
            SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO = (SolicitudGestionCobroManualModeloDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL).setParameter(NUMERO_RADICACION, numeroRadicacion).getSingleResult();
            SolicitudGestionCobroManual solicitudGestionCobroManual = solicitudGestionCobroDTO.convertToEntity();
            if (solicitudGestionCobroManual != null) {
                solicitudGestionCobroManual.setEstadoSolicitud(estadoSolicitud);
                entityManager.merge(solicitudGestionCobroManual);
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarAgendaCartera(java.util.List)
     */
    @Override
    public void guardarAgendaCartera(List<AgendaCarteraModeloDTO> agendasCarteraDTO) {
        String firmaMetodo = "guardarAgendaCartera(AgendaCarteraModeloDTO)";
        try {
            for (AgendaCarteraModeloDTO agendaCarteraModeloDTO : agendasCarteraDTO) {
                if (agendaCarteraModeloDTO.getIdAgendaFiscalizacion() != null) {
                    entityManager.merge(agendaCarteraModeloDTO.convertToEntity());
                } else {
                    entityManager.persist(agendaCarteraModeloDTO.convertToEntity());
                }
            }
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarActividadCartera(java.util.List)
     */
    @Override
    public void guardarActividadCartera(List<ActividadCarteraModeloDTO> actividadesCarteraDTO) {
        String firmaMetodo = "guardarActividadCartera(List<ActividadCarteraModeloDTO>)";
        logger.debug("**__** INICIA guardarActividadCartera");
        try {
            for (ActividadCarteraModeloDTO actividadCartera : actividadesCarteraDTO) {
                List<ActividadDocumentoModeloDTO> actividadDocumentoModeloDTOs = new ArrayList<>();

                if (actividadCartera.getActividadDocumentoModeloDTOs() != null && !actividadCartera.getActividadDocumentoModeloDTOs().isEmpty()) {
                    actividadDocumentoModeloDTOs.addAll(actividadCartera.getActividadDocumentoModeloDTOs());
                }

                ActividadCartera actividadCaEntity = actividadCartera.convertToEntity();
                actividadCaEntity = entityManager.merge(actividadCaEntity);

                for (ActividadDocumentoModeloDTO actividadDocumentoModeloDTO : actividadDocumentoModeloDTOs) {
                    actividadDocumentoModeloDTO.setIdActividadCartera(actividadCaEntity.getIdActividadFiscalizacion());
                    ActividadDocumento actividadDocumento = actividadDocumentoModeloDTO.convertToActividadDocumentoEntity();
                    entityManager.merge(actividadDocumento);
                }
            }
            logger.debug("**__** FINALIZA guardarActividadCartera");
            logger.debug(LOG_FINALIZA + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesEdictos(java.lang.String)
     */
    @Override
    public RegistroRemisionAportantesDTO consultarAportantesEdictos(String numeroRadicacion) {
        try {
            RegistroRemisionAportantesDTO registroRemisionAportante = new RegistroRemisionAportantesDTO();
            SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroFisico = consultarSolicitudGestionCobro(numeroRadicacion);
            registroRemisionAportante.setObservaciones(solicitudGestionCobroFisico.getObservacionRemision());
            registroRemisionAportante.setFechaRemision(solicitudGestionCobroFisico.getFechaRemision());

            List<Object[]> aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_SOLICITUD_EDICTOS).setParameter("etiqueta", EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR.name()).setParameter("numeroRadicado", numeroRadicacion).getResultList();
            List<AportanteRemisionComunicadoDTO> aportantesDTO = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            if (!aportantes.isEmpty()) {
                for (Object[] aportante : aportantes) {
                    AportanteRemisionComunicadoDTO aportanteRemision = new AportanteRemisionComunicadoDTO();
                    aportanteRemision.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportante[0]));
                    aportanteRemision.setNumeroIdentificacion((String) aportante[1]);
                    aportanteRemision.setNombreCompleto(aportante[2] != null ? (String) aportante[2] : null);
                    aportanteRemision.setEnviar(aportante[3] != null ? (Boolean) aportante[3] : null);
                    aportanteRemision.setConsecutivoLiquidacion(aportante[4] != null ? (String) aportante[4] : null);
                    aportanteRemision.setFechaLiquidacion(aportante[5] != null ? dateFormat.parse(aportante[5].toString()).getTime() : null);
                    aportanteRemision.setIdCartera(aportante[6] != null ? Long.valueOf(aportante[6].toString()) : null);
                    aportanteRemision.setObservacionPrimeraEntrega(aportante[7] != null ? aportante[7].toString() : null);
                    aportanteRemision.setIdPrimeraSolicitudRemision(aportante[8] != null ? Long.valueOf(aportante[8].toString()) : null);
                    aportanteRemision.setIdDetalleSolicitudGestionCobro(aportante[9] != null ? Long.valueOf(aportante[9].toString()) : null);
                    aportantesDTO.add(aportanteRemision);
                }
            }

            registroRemisionAportante.setAportantes(aportantesDTO);
            return registroRemisionAportante;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarAportantesEdictos: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de verificar si una solicitud de gestión de cobro fisico tiene riesgo de incobrabilidad
     *
     * @param numeroRadicacion,   número de radicación de la solicitud
     * @param lstTipoExclusiones, listado de exclusiones
     * @return retona el listado de exclusiones con la exclusion de incobrabilidad
     */
    private List<String> verificarSolicitudFisicaIncobrabilidad(String
                                                                        numeroRadicacion, List<String> lstTipoExclusiones) {
        SolicitudGestionCobroFisicoModeloDTO solGestionCobroFisico = consultarSolicitudGestionCobro(numeroRadicacion);
        //Tipo de Acciones de Incobrabilidad
        List<TipoAccionCobroEnum> tipoAccionesIncobrabilidad = new ArrayList<>();
        //Listado de acciones correspondientes al método 1
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.B1);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.C1);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.D1);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.E1);
        //Listado de acciones correspondientes al método 2
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.B2);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.D2);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.F2);
        tipoAccionesIncobrabilidad.add(TipoAccionCobroEnum.G2);
        //Tipo de acciones de exclusiones de negocio
        List<TipoAccionCobroEnum> tipoAccionesExclusiones = new ArrayList<>();
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.A1);
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.C2);
        if (solGestionCobroFisico != null) {
            //Se verifica las acciones que son propias de la exclusion de negocio
            for (TipoAccionCobroEnum tipoAccionExclusion : tipoAccionesExclusiones) {
                if (tipoAccionExclusion.equals(solGestionCobroFisico.getTipoAccionCobro())) {
                    lstTipoExclusiones.add(TipoExclusionCarteraEnum.EXCLUSION_NEGOCIO.toString());
                    break;
                }
            }
            //Se verifica las acciones que son propias del riesgo de incobrabilidad
            for (TipoAccionCobroEnum tipoAccionIncobrabilidad : tipoAccionesIncobrabilidad) {
                if (tipoAccionIncobrabilidad.equals(solGestionCobroFisico.getTipoAccionCobro())) {
                    lstTipoExclusiones.add(TipoExclusionCarteraEnum.RIESGO_INCOBRABILIDAD.toString());
                    break;
                }
            }
        }
        return lstTipoExclusiones;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCanditatosExpulsion(java.util.Boolean,com.asopagos.enumeraciones.cartera.
     * TipoAccionCobroEnum,java.util.List)
     */
    @Override
    public List<DetalleSolicitudGestionCobroModeloDTO> consultarCanditatosExpulsion(Boolean
                                                                                            validacionExclusion, TipoAccionCobroEnum
                                                                                            tipoAccionCobro, List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles) {
        String firmaMetodo = "consultarCanditatosExpulsion(EstadoCarteraEnum,EstadoOperacionCarteraEnum,List<TipoExclusionCarteraEnum> )";
        List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudes = new ArrayList<>();
        List<Long> lstIdCartera = new ArrayList<>();
        try {
            List<TipoExclusionCarteraEnum> lstExclusiones = new ArrayList<>();
            lstExclusiones.add(TipoExclusionCarteraEnum.ACLARACION_MORA);
            for (DetalleSolicitudGestionCobroModeloDTO detalleSolicitud : lstDetalles) {
                lstIdCartera.add(detalleSolicitud.getIdCartera());
            }
            String consulta = "";
            if (TipoAccionCobroEnum.F1.equals(tipoAccionCobro) || TipoAccionCobroEnum.H2.equals(tipoAccionCobro)) {
                if (validacionExclusion) {
                    consulta = NamedQueriesConstants.CONSULTAR_EMPLEADORES_CANDITADOS_EXCLUSION_CARTERA;
                } else {
                    consulta = NamedQueriesConstants.CONSULTAR_EMPLEADORES_CANDITADOS_EXCLUSION_CARTERA_SIN_EXCLUSION;
                }
            }
            if (TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro) || TipoAccionCobroEnum.LC5C.equals(tipoAccionCobro)) {
                if (validacionExclusion) {
                    consulta = NamedQueriesConstants.CONSULTAR_AFILIADOS_CANDITADOS_EXCLUSION_CARTERA;
                } else {
                    consulta = NamedQueriesConstants.CONSULTAR_AFILIADOS_CANDITADOS_EXCLUSION_CARTERA_SIN_EXCLUSION;
                }
            }
            detallesSolicitudes = entityManager.createNamedQuery(consulta).setParameter("estadoCartera", EstadoCarteraEnum.MOROSO).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("lstIdCartera", lstIdCartera).setParameter("lstExclusiones", lstExclusiones).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).setParameter("estadoConvenio", EstadoConvenioPagoEnum.ACTIVO).setParameter("tipoAccionCobro", tipoAccionCobro).getResultList();
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return detallesSolicitudes.isEmpty() ? null : detallesSolicitudes;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarEmpleadorExpulsion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean validarEmpleadorExpulsion(Long idCartera) {
        try {
            logger.debug("Inicia servicio validarEmpleadorExpulsion");
            List<TipoExclusionCarteraEnum> lstExclusiones = new ArrayList<>();
            lstExclusiones.add(TipoExclusionCarteraEnum.ACLARACION_MORA);
            Integer resultado = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_VALIDACION_EMPLEADOR_EXPULSION).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("idCartera", idCartera).setParameter("lstExclusiones", lstExclusiones).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).setParameter("estadoConvenio", EstadoConvenioPagoEnum.ACTIVO).getSingleResult();
            logger.debug("Finaliza servicio validarEmpleadorExpulsion");
            return resultado == 0;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio validarEmpleadorExpulsion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#validarPersonaExpulsion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean validarPersonaExpulsion(Long idCartera) {
        try {
            logger.debug("Inicia servicio validarPersonaExpulsion");
            List<TipoExclusionCarteraEnum> lstExclusiones = new ArrayList<>();
            lstExclusiones.add(TipoExclusionCarteraEnum.ACLARACION_MORA);
            Integer resultado = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_VALIDACION_PERSONA_EXPULSION).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).setParameter("idCartera", idCartera).setParameter("lstExclusiones", lstExclusiones).setParameter("estadoExclusion", EstadoExclusionCarteraEnum.ACTIVA).setParameter("estadoConvenio", EstadoConvenioPagoEnum.ACTIVO).getSingleResult();
            logger.debug("Finaliza servicio validarPersonaExpulsion");
            return resultado == 0;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio validarPersonaExpulsion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de verificar si una solicitud de gestión de cobro electronico tiene riesgo de incobrabilidad
     *
     * @param lstTipoExclusiones,       listado de exclusiones
     * @param tipoAccionCobroSolicitud, tipo de acción de cobro de la solicitud de gestión de incobrabilidad
     * @return retona el listado de exclusiones con la exclusion de incobrabilidad
     */
    private List<String> verificarSolicitudElectronicoIncobrabilidad
    (List<String> lstTipoExclusiones, TipoAccionCobroEnum tipoAccionCobroSolicitud) {
        //Tipo de acciones para las validaciones de exclusiones de negocio
        List<TipoAccionCobroEnum> tipoAccionesCobro = new ArrayList<>();
        tipoAccionesCobro.add(TipoAccionCobroEnum.C1);
        tipoAccionesCobro.add(TipoAccionCobroEnum.D1);
        tipoAccionesCobro.add(TipoAccionCobroEnum.E1);
        tipoAccionesCobro.add(TipoAccionCobroEnum.F2);
        tipoAccionesCobro.add(TipoAccionCobroEnum.G2);
        //Tipo de acciones para las validaciones de exclusiones de negocio
        List<TipoAccionCobroEnum> tipoAccionesExclusiones = new ArrayList<>();
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.A2);
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.B2);
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.C2);
        tipoAccionesExclusiones.add(TipoAccionCobroEnum.D2);
        if (tipoAccionCobroSolicitud != null) {
            //Se verifica las acciones que son propias de la exclusion de negocio
            for (TipoAccionCobroEnum tipoAccionExclusion : tipoAccionesExclusiones) {
                if (tipoAccionExclusion.equals(tipoAccionCobroSolicitud)) {
                    lstTipoExclusiones.add(TipoExclusionCarteraEnum.EXCLUSION_NEGOCIO.toString());
                    break;
                }
            }
            //Se verifica las acciones que son propias del riesgo de incobrabilidad
            for (TipoAccionCobroEnum tipoAccionCobroEnum : tipoAccionesCobro) {
                if (tipoAccionCobroEnum.equals(tipoAccionCobroSolicitud)) {
                    lstTipoExclusiones.add(TipoExclusionCarteraEnum.RIESGO_INCOBRABILIDAD.toString());
                    break;
                }
            }
        }
        return lstTipoExclusiones;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarEtiquetaPorAccion(com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @Override
    public EtiquetaPlantillaComunicadoEnum consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro) {
        try {
            logger.debug("Inicio de método consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro)");
            switch (accionCobro) {
                case A1:
                    List<Object> accionCobroA1 = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);

                    for (Object object : accionCobroA1) {
                        AccionCobroAModeloDTO accion = (AccionCobroAModeloDTO) object;
                        if (MetodoAccionCobroEnum.METODO_1.equals(accion.getMetodo())) {
                            if (accion.getSuspensionAutomatica()) {
                                return EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG;
                            } else {
                                return EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO;
                            }
                        }
                    }
                    return null;
                case A2:
                    List<Object> accionCobroA2 = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);

                    for (Object object : accionCobroA2) {
                        AccionCobroAModeloDTO accion = (AccionCobroAModeloDTO) object;
                        if (MetodoAccionCobroEnum.METODO_2.equals(accion.getMetodo())) {
                            if (accion.getSuspensionAutomatica()) {
                                return EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG;
                            } else {
                                return EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO;
                            }
                        }
                    }
                    return null;
                case B1:
                    return EtiquetaPlantillaComunicadoEnum.AVI_INC;
                case B2:
                    return EtiquetaPlantillaComunicadoEnum.AVI_INC;
                case C1:
                    return EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR;
                case C2:
                    return EtiquetaPlantillaComunicadoEnum.CIT_NTF_PER;
                case D1:
                    return EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS;
                case D2:
                    return EtiquetaPlantillaComunicadoEnum.NTF_AVI;
                case E1:
                    return EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS;
                case F1:
                    return EtiquetaPlantillaComunicadoEnum.CAR_EMP_EXP;
                case F2:
                    return EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS;
                case G2:
                    return EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS;
                case H2:
                    return EtiquetaPlantillaComunicadoEnum.CAR_EMP_EXP;
                case LC2A:
                    return EtiquetaPlantillaComunicadoEnum.NOTI_IN_RE_APORTE;
                case LC3A:
                    return EtiquetaPlantillaComunicadoEnum.NOTI_IN_RE_APORTE;
                case LC4A:
                    return EtiquetaPlantillaComunicadoEnum.AVI_INC_PER;
                case LC5A:
                    return EtiquetaPlantillaComunicadoEnum.AVI_INC_PER;
                case LC4C:
                    return EtiquetaPlantillaComunicadoEnum.CAR_PER_EXP;
                case LC5C:
                    return EtiquetaPlantillaComunicadoEnum.CAR_PER_EXP;
                default:
                    return null;
            }
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error en consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCriteriosGestionCobroLinea(com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ParametrizacionCriteriosGestionCobroModeloDTO> consultarCriteriosGestionCobroLinea
    (List<TipoLineaCobroEnum> lineasCobro) {
        return consultasModeloCore.consultarCriteriosGestionCobroLinea(lineasCobro);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.CarteraCompositeService#guardarNotificacionPersonal(com.asopagos.dto.modelo.NotificacionPersonalModeloDTO)
     */
    @Override
    public List<DocumentoSoporteModeloDTO> crearNotificacionPersonal(NotificacionPersonalModeloDTO
                                                                             notificacionPersonalDTO) {
        String firmaMetodo = "guardarNotificacionPersonal(NotificacionPersonalModeloDTO)";
        logger.debug(LOG_INICIA + firmaMetodo);
        try {
            List<DocumentoSoporteModeloDTO> listaDocumentoDTO = new ArrayList<DocumentoSoporteModeloDTO>();
            NotificacionPersonal notificacionPersonal = new NotificacionPersonal();
            notificacionPersonal.setActividad(notificacionPersonalDTO.getActividad());
            notificacionPersonal.setComentario(notificacionPersonalDTO.getComentario());
            notificacionPersonal.setIdPersona(notificacionPersonalDTO.getIdPersona());
            notificacionPersonal.setTipoSolicitante(notificacionPersonalDTO.getTipoSolicitante());
            /* Se consulta la cartera del ultimo periodo */
            Long idCartera = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CARTERA_NOTIFICACION_PERSONAL_CONSULTAR_CARTERA_ULTIMO_PERIODO).setParameter("idPersona", notificacionPersonal.getIdPersona()).setParameter("tipoSolicitante", notificacionPersonal.getTipoSolicitante()).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).getSingleResult();
            notificacionPersonal.setIdCartera(idCartera);
            notificacionPersonal = entityManager.merge(notificacionPersonal);

            StringBuilder desc = new StringBuilder("Documento ");
            if (notificacionPersonalDTO.getLstDocumentosSoporteModelo() != null && !notificacionPersonalDTO.getLstDocumentosSoporteModelo().isEmpty()) {
                for (DocumentoSoporteModeloDTO documento : notificacionPersonalDTO.getLstDocumentosSoporteModelo()) {
                    documento.setFechaHoraCargue((new Date()).getTime());
                    switch (notificacionPersonalDTO.getActividad()) {
                        case REGISTRAR_NOTIFICACION_PERSONAL_APORTANTE:
                            desc.append(ResultadoBitacoraCarteraEnum.NOTIFICADO_PERSONALMENTE.getDescripcion());
                            break;
                        case OTRO:
                            desc.append(ResultadoBitacoraCarteraEnum.OTRO.getDescripcion());
                            break;
                        default:
                            desc.append("Notificación Personal");
                    }
                    documento.setDescripcionComentarios(desc.toString());
                    String[] parts = documento.getIdentificacionDocumento().split("\\_");
                    documento.setIdentificacionDocumento(parts[0]);
                    documento.setVersionDocumento(parts[1]);
                    NotificacionPersonalDocumento notificacionPersonalDocumento = new NotificacionPersonalDocumento();
                    notificacionPersonalDocumento.setDocumentosSoporte(documento.convertToEntity());
                    notificacionPersonalDocumento.setIdNotificacionPersonal(notificacionPersonal.getIdNotificacionPersonal());
                    notificacionPersonalDocumento = entityManager.merge(notificacionPersonalDocumento);

                    documento.convertToDTO(notificacionPersonalDocumento.getDocumentosSoporte());
                    listaDocumentoDTO.add(documento);
                }
            }

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return listaDocumentoDTO;
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public NotificacionNiyarakyActualizacionEstadoOutDTO crearActualizacionEstadoNiyaraky(
            NotificacionNiyarakyActualizacionEstadoInDTO notificacionNiyarakyActualizacionEstadoInDTO) {

        String firmaMetodo = "crearActualizacionEstadoNiyaraky(NotificacionNiyarakyActualizacionEstadoInDTO)";
        logger.info("LOG_INICIA" + firmaMetodo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (notificacionNiyarakyActualizacionEstadoInDTO == null) {
                NotificacionNiyarakyActualizacionEstadoOutDTO respuesta = new NotificacionNiyarakyActualizacionEstadoOutDTO();
                respuesta.setOk(false);
                respuesta.setMsg("[400] La notificación recibida es nula.");
                respuesta.setCodigo(400);
                logger.info("LOG_FINALIZA" + firmaMetodo + " - Solicitud incorrecta");
                return respuesta;
            }

            // Extraer los valores del DTO de entrada
            String idMensaje = notificacionNiyarakyActualizacionEstadoInDTO.getIdMensaje();
            Integer estado = notificacionNiyarakyActualizacionEstadoInDTO.getEstado();
            EstadoCorreoCertificadoEnum estadoEnum = EstadoCorreoCertificadoEnum.fromCodigo(estado);
            logger.info(estado);
            String descripcion = notificacionNiyarakyActualizacionEstadoInDTO.getDescripcion();
            String fechaEvento = notificacionNiyarakyActualizacionEstadoInDTO.getFecha_evento() != null ? notificacionNiyarakyActualizacionEstadoInDTO.getFecha_evento() : null;

            Integer idComunicadoNiyaraky;
            ComunicadoNiyaraky comunicadoNiyaraky;
            try {
                // Busca el id de la tabla ComunicadoNiyaray para ver si existe dentro de los comunicados que se han enviado
                idComunicadoNiyaraky = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDMENSAJE_NIYARAKY)
                    .setParameter("idMensaje", idMensaje).getSingleResult();
                comunicadoNiyaraky = entityManager.find(ComunicadoNiyaraky.class, Long.valueOf(idComunicadoNiyaraky));
            } catch (NoResultException e) {
                // Comunicado no encontrado
                NotificacionNiyarakyActualizacionEstadoOutDTO respuesta = new NotificacionNiyarakyActualizacionEstadoOutDTO();
                respuesta.setOk(false);
                respuesta.setMsg("[-9] Mensaje con identificador ID: " + idMensaje + " no encontrado en el sistema");
                respuesta.setCodigo(404);
    
                NotificacionNiyarakyActualizacionEstadoOutDTO.History history = new NotificacionNiyarakyActualizacionEstadoOutDTO.History();
                history.setIdMensaje(idMensaje);
                history.setEstado(-9);
                history.setDescripcion("El mensaje con el identificador suministrado(" + idMensaje + ") no fue encontrado. Probablemente no se envió.");
                history.setFechaEvento(fechaEvento);

                respuesta.setHistory(history);

    
                ComunicadoNiyarakyRecepcion comunicadoNiyarakyRecepcion = new ComunicadoNiyarakyRecepcion();
                comunicadoNiyarakyRecepcion.setEstado(Long.valueOf(history.getEstado()));
                comunicadoNiyarakyRecepcion.setDescripcion(history.getDescripcion());
                comunicadoNiyarakyRecepcion.setFechaEventoRecibido(new Date());
                comunicadoNiyarakyRecepcion.setFechaEventoRecibidoPorNiyaraky(sdf.parse(fechaEvento));
                comunicadoNiyarakyRecepcion.setRequest(notificacionNiyarakyActualizacionEstadoInDTO.toString());
                comunicadoNiyarakyRecepcion.setResponse(respuesta.toString());
    
                entityManager.persist(comunicadoNiyarakyRecepcion);
    
                logger.info("LOG_FINALIZA" + firmaMetodo + " - Comunicado no encontrado");
                return respuesta;
            }
            // Guardar registro en base de datos
            ComunicadoNiyarakyRecepcion comunicadoNiyarakyRecepcion = new ComunicadoNiyarakyRecepcion();
            comunicadoNiyarakyRecepcion.setEstado(Long.valueOf(estado));
            comunicadoNiyarakyRecepcion.setDescripcion(descripcion);
            comunicadoNiyarakyRecepcion.setFechaEventoRecibido(new Date());
            comunicadoNiyarakyRecepcion.setFechaEventoRecibidoPorNiyaraky(sdf.parse(fechaEvento));
            comunicadoNiyarakyRecepcion.setRequest(notificacionNiyarakyActualizacionEstadoInDTO.toString());
            comunicadoNiyarakyRecepcion.setResponse(null);
            comunicadoNiyarakyRecepcion.setComunicadoNiyaraky(comunicadoNiyaraky);
            // Crear el DTO de salida
            NotificacionNiyarakyActualizacionEstadoOutDTO respuesta = new NotificacionNiyarakyActualizacionEstadoOutDTO();
            respuesta.setOk(true);
            respuesta.setMsg("[200] Actualización procesada exitosamente.");
            respuesta.setCodigo(200);
    
            // Crear y poblar el objeto History
            NotificacionNiyarakyActualizacionEstadoOutDTO.History history = new NotificacionNiyarakyActualizacionEstadoOutDTO.History();
            history.setIdMensaje(idMensaje);
            history.setEstado(estado);
            history.setDescripcion("Estado: " + estadoEnum.getDescripcion());
            history.setFechaEvento(fechaEvento);
            respuesta.setHistory(history);
    
            // Guarda la respuesta final
            comunicadoNiyarakyRecepcion.setResponse(respuesta.toString());
            entityManager.persist(comunicadoNiyarakyRecepcion);
    
            logger.info("LOG_FINALIZA" + firmaMetodo);
            return respuesta;
    
        } catch (Exception e) {
            logger.error("LOG_OCURRIO_UN_ERROR" + firmaMetodo, e);
            NotificacionNiyarakyActualizacionEstadoOutDTO respuestaError = new NotificacionNiyarakyActualizacionEstadoOutDTO();
            respuestaError.setOk(false);
            respuestaError.setMsg("[500] Error técnico en el proceso.");
            respuestaError.setCodigo(500);
            logger.error("Error técnico en " + firmaMetodo, e);
            return respuestaError;
        }
    }


    /**
     * Metodo que se encarga de guardar en la tabla ConvenioPagoDependientes
     *
     * @param convenioPagoDependientesModeloDTO revibe un DTO
     */
    private void guardarConvenioPagoDependientes(ConvenioPagoDependienteModeloDTO convenioPagoDependientesModeloDTO) {
        String firmaMetodo = "guardarConvenioPagoDependientes (ConvenioPagoDependientesModeloDTO convenioPagoDependientesModeloDTO)";
        try {
            logger.debug("Inicio " + firmaMetodo);
            ConvenioPagoDependiente convenioPagoDependientes = convenioPagoDependientesModeloDTO.convertEntity();
            if (convenioPagoDependientes.getIdConvenioPagoDependientes() != null) {
                entityManager.merge(convenioPagoDependientes);
            } else {
                entityManager.persist(convenioPagoDependientes);
            }
            logger.debug("Fin " + firmaMetodo);
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarSolicitudGlobalCartera(com.asopagos.dto.modelo.SolicitudModeloDTO)
     */
    @Override
    public SolicitudModeloDTO guardarSolicitudGlobalCartera(SolicitudModeloDTO solicitudDTO) {
        try {
            logger.debug("Inicia servicio guardarSolicitudGlobal");
            Solicitud solicitud = solicitudDTO.convertToSolicitudEntity();
            Solicitud managed = entityManager.merge(solicitud);
            solicitudDTO.setIdSolicitud(managed.getIdSolicitud());
            logger.debug("Finaliza servicio guardarSolicitudGlobal");
            return solicitudDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio guardarSolicitudGlobal", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#obtenerConsecutivoLiquidacion()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String obtenerConsecutivoLiquidacion() {
        try {
            logger.debug("Inicia servicio obtenerConsecutivoLiquidacion");
            String consecutivo = null;
            Object resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMO_CONSECUTIVO_PERIODO_ACTUAL).getSingleResult();

            if (resultado == null) {
                Format formatter = new SimpleDateFormat(FORMATO_FECHA_LIQUIDACION);
                consecutivo = formatter.format(new Date()) + String.format(FORMATO_CONSECUTIVO_LIQUIDACION, 1);
            } else {
                String ultimo = resultado.toString();
                int siguiente = Integer.parseInt(ultimo.substring(6)) + 1;
                consecutivo = ultimo.substring(0, 6) + String.format(FORMATO_CONSECUTIVO_LIQUIDACION, siguiente);
            }

            logger.debug("Finaliza servicio obtenerConsecutivoLiquidacion");
            return consecutivo;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio obtenerConsecutivoLiquidacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#obtenerConsecutivoLiquidacionExistenteCartera()
     */
    @Override
    public String obtenerConsecutivoLiquidacionExistenteCartera(Long idCartera) {
        try {
            logger.debug("Inicia servicio obtenerConsecutivoLiquidacionExistenteCartera - idCartera: " + idCartera);
            String consecutivo = null;
            Object resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENTE_CONSECUTIVO_LIQUIDACION).setParameter("idCartera", idCartera).getSingleResult();

            if (resultado == null) {
                consecutivo = "";
            } else {
                consecutivo = resultado.toString();
            }
            logger.info("Consecutivo:" + consecutivo);
            logger.debug("Finaliza servicio obtenerConsecutivoLiquidacionExistenteCartera");
            return consecutivo;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio obtenerConsecutivoLiquidacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAportantesGestionManual(com.asopagos.dto.cartera.
     * ParametrosGestionCobroManualDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<AportanteGestionManualDTO> consultarAportantesGestionManual(ParametrosGestionCobroManualDTO
                                                                                    parametros, UserDTO userDTO) {
        try {
            logger.debug("Inicia el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO)");
            List<AportanteGestionManualDTO> aportantes;
            List<TipoAccionCobroEnum> listaAcciones = new ArrayList<>();
            List<EstadoCicloCarteraEnum> estadosCiclo = new ArrayList<>();
            estadosCiclo.add(EstadoCicloCarteraEnum.ACTIVO);

            for (TipoSolicitanteMovimientoAporteEnum tipoSolicitante : parametros.getTipoSolicitantes()) {
                if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)) {
                    listaAcciones.add(TipoAccionCobroEnum.G1);
                    listaAcciones.add(TipoAccionCobroEnum.I2);
                    listaAcciones.add(TipoAccionCobroEnum.LC2B);
                    listaAcciones.add(TipoAccionCobroEnum.LC3B);
                } else if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
                    listaAcciones.add(TipoAccionCobroEnum.LC4B);
                } else {
                    listaAcciones.add(TipoAccionCobroEnum.LC5B);
                }
            }

            String destinatario = null;

            if (parametros.getEsSupervisor() != null && !parametros.getEsSupervisor()) {
                destinatario = userDTO.getNombreUsuario();
            }

            if (parametros.getIdsPersonas() != null && !parametros.getIdsPersonas().isEmpty()) {
                aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_GESTION_MANUAL_DETALLE).setParameter("estados", parametros.getEstadosFiscalizacion()).setParameter("tipoSolicitantes", parametros.getTipoSolicitantes()).setParameter("idsPersonas", parametros.getIdsPersonas())//.setParameter("estadosCiclo", estadosCiclo)
                        .setParameter("destinatario", destinatario).getResultList();
            } else {
                aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_GESTION_MANUAL_SIN_DETALLE).setParameter("estados", parametros.getEstadosFiscalizacion()).setParameter("tipoSolicitantes", parametros.getTipoSolicitantes())//.setParameter("estadosCiclo", estadosCiclo)
                        .setParameter("destinatario", destinatario).getResultList();
            }

            logger.debug("Finaliza el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO)");
            return aportantes;
        } catch (Exception e) {
            logger.error("Finaliza el método consultarAportantesGestionManual(ParametrosGestionCobroManualDTO): Error técnico inesperado ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarDeudaPresuntaCartera(com.asopagos.dto.cartera.DeudaPresuntaDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String
            numeroIdentificacion, String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        logger.info("Inicia el servicio actualizarDeudaPresuntaCartera" + numeroIdentificacion);
        logger.info("Inicia el servicio actualizarDeudaPresuntaCartera con periodo" + periodoEvaluacion);
        logger.info("Inicia el servicio actualizarDeudaPresuntaCartera con tipo de aportante" + tipoAportante);
        logger.info("Inicia el servicio actualizarDeudaPresuntaCartera con periodo: " + periodoEvaluacion);
        consultasModeloCore.actualizarDeudaPresuntaCartera(tipoIdentificacion, numeroIdentificacion, periodoEvaluacion, tipoAportante);
        logger.debug("Finaliza el servicio actualizarDeudaPresuntaCartera");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    public void actualizarDeudaPresuntaCarteraAsincrono(TipoIdentificacionEnum tipoIdentificacion, String
            numeroIdentificacion, String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        logger.debug("Inicia el servicio actualizarDeudaPresuntaCarteraAsincrono");
        consultasModeloCore.actualizarDeudaPresuntaCartera(tipoIdentificacion, numeroIdentificacion, periodoEvaluacion, tipoAportante);
        logger.debug("Finaliza el servicio actualizarDeudaPresuntaCarteraAsincrono");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCarteraAportante(com.asopagos.dto.cartera.AportanteCarteraDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraModeloDTO> consultarCarteraAportante(AportanteCarteraDTO aportanteCarteraDTO) {
        try {
            logger.debug("Inicia el servicio consultarCarteraAportante");
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_CARTERA_APORTANTE).setParameter("tipoIdentificacion", aportanteCarteraDTO.getTipoIdentificacion().name()).setParameter("numeroIdentificacion", aportanteCarteraDTO.getNumeroIdentificacion()).setParameter("tipoAportante", aportanteCarteraDTO.getTipoSolicitante().name());
            query.execute();
            List<Object[]> listaCartera = query.getResultList();
            List<CarteraModeloDTO> listaCarteraDTO = new ArrayList<CarteraModeloDTO>();

            for (Object[] registro : listaCartera) {
                CarteraModeloDTO carteraDTO = new CarteraModeloDTO();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date fecha = (Date) formatter.parse(registro[0].toString());
                carteraDTO.setFechaCreacion(fecha.getTime());
                carteraDTO.setIdCartera(Long.parseLong(registro[1].toString()));
                carteraDTO.setTipoAccionCobro(registro[2] != null ? TipoAccionCobroEnum.valueOf(registro[2].toString()) : null);
                carteraDTO.setTipoLineaCobro(TipoLineaCobroEnum.valueOf(registro[3].toString()));
                carteraDTO.setDeudaPresunta(registro[4] != null ? new BigDecimal(registro[4].toString()) : BigDecimal.ZERO);
                carteraDTO.setDeudaReal(registro[5] != null ? new BigDecimal(registro[5].toString()) : BigDecimal.ZERO);
                carteraDTO.setDeudaTotal(registro[6] != null ? new BigDecimal(registro[6].toString()) : BigDecimal.ZERO);
                listaCarteraDTO.add(carteraDTO);
            }

            logger.debug("Finaliza el servicio consultarCarteraAportante");
            return listaCarteraDTO;
        } catch (Exception e) {
            logger.error("Error en el servicio consultarCarteraAportante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarEstadoSolicitudGestionCobro(java.lang.Long,
     * com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarEstadoSolicitudGestionCobro(Long idCartera, TipoAccionCobroEnum tipoAccionCobro) {
        try {
            logger.debug("Inicia el servicio consultarEstadoSolicitudGestionCobro");
            List<String> lista = new ArrayList<String>();

            if (tipoAccionCobro != null) {
                lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_GESTION_COBRO).setParameter("idCartera", idCartera).setParameter("tipoAccionCobro", tipoAccionCobro.name()).getResultList();
            }

            logger.debug("Finaliza el servicio consultarEstadoSolicitudGestionCobro");
            return !lista.isEmpty() ? lista.get(0) : null;
        } catch (Exception e) {
            logger.error("Error en el servicio consultarEstadoSolicitudGestionCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarPeriodosAportanteLineaCobro(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
     * com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraModeloDTO> consultarPeriodosAportanteLineaCobro(TipoIdentificacionEnum
                                                                               tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum
                                                                               tipoSolicitante, TipoLineaCobroEnum tipoLineaCobro) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CarteraModeloDTO> carteraModeloDTOList = new ArrayList<>();
            logger.info("Inicia consultarPeriodosAportanteLineaCobro");
            logger.info("Inicia tipoIdentificacion::::: " + tipoIdentificacion + " numeroIdentificacion " + numeroIdentificacion + " tipoSolicitante " + tipoSolicitante + " tipoLineaCobro " + tipoLineaCobro);
            List<Object[]> listaCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODOS_APORTANTE_LINEACOBRO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoSolicitante", tipoSolicitante.name())
                    .setParameter("tipoLineaCobro", tipoLineaCobro.name())
                    .getResultList();
            logger.info("listaCartera" + objectMapper.writeValueAsString(listaCartera));
            for (Object[] registro : listaCartera) {
                carteraModeloDTOList.add(mapperCarteraModeloDTO(registro, numeroIdentificacion));
            }
            logger.debug("Finaliza consultarPeriodosAportanteLineaCobro" + objectMapper.writeValueAsString(listaCartera));
            return carteraModeloDTOList;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarPeriodosAportanteLineaCobro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public CarteraModeloDTO mapperCarteraModeloDTO(Object[] registro, String numeroIdentificacion) throws
            ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //BigDecimal deudaReal = (BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEUDA_REAL).setParameter("idCartera", Long.valueOf(String.valueOf(registro[14]))).getSingleResult();
        //logger.info("deudaReal:::::" + objectMapper.writeValueAsString(deudaReal));
        CarteraModeloDTO carteraModeloDTO = new CarteraModeloDTO();
        carteraModeloDTO.setDeudaPresunta(registro[0] != null ? (BigDecimal) registro[0] : null);
        carteraModeloDTO.setEstadoCartera(registro[1] != null ? EstadoCarteraEnum.valueOf(String.valueOf(registro[1])) : null);
        carteraModeloDTO.setEstadoOperacion(registro[2] != null ? EstadoOperacionCarteraEnum.valueOf(String.valueOf(registro[2])) : null);
        carteraModeloDTO.setFechaCreacion(registro[3] != null ? converterStringToDate(String.valueOf(registro[3])) : null);
        carteraModeloDTO.setIdPersona(registro[4] != null ? Long.valueOf(String.valueOf(registro[4])) : null);
        carteraModeloDTO.setMetodo(registro[5] != null ? MetodoAccionCobroEnum.valueOf(String.valueOf(registro[5])) : null);
        carteraModeloDTO.setPeriodoDeuda(registro[6] != null ? converterStringToDate(String.valueOf(registro[6])) : null);
        carteraModeloDTO.setRiesgoIncobrabilidad(registro[7] != null ? RiesgoIncobrabilidadEnum.valueOf(String.valueOf(registro[7])) : null);
        carteraModeloDTO.setTipoAccionCobro(registro[8] != null ? TipoAccionCobroEnum.valueOf(String.valueOf(registro[8])) : null);
        carteraModeloDTO.setTipoDeuda(registro[9] != null ? TipoDeudaEnum.valueOf(String.valueOf(registro[9])) : null);
        carteraModeloDTO.setTipoLineaCobro(registro[10] != null ? TipoLineaCobroEnum.valueOf(String.valueOf(registro[10])) : null);
        carteraModeloDTO.setTipoSolicitante(registro[11] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(registro[11])) : null);
        carteraModeloDTO.setFechaAsignacionAccion(registro[12] != null ? converterStringToDate(String.valueOf(registro[12])) : null);
        carteraModeloDTO.setUsuarioTraspaso(registro[13] != null ? String.valueOf(registro[13]) : null);
        carteraModeloDTO.setIdCartera(registro[14] != null ? Long.valueOf(String.valueOf(registro[14])) : null);
        carteraModeloDTO.setDeudaReal((BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEUDA_REAL).setParameter("idCartera", Long.valueOf(String.valueOf(registro[14]))).getSingleResult());
        carteraModeloDTO.setActividades(entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDADES_IDENTIFICACION).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList());
        logger.info("mapperCarteraModeloDTO:::::" + objectMapper.writeValueAsString(carteraModeloDTO));
        return carteraModeloDTO;
    }

    public Long converterStringToDate(String fecha) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formato.parse(fecha);
        Long resul = date.getTime();
        return resul;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarCarteraCotizantesAportante(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportante(TipoIdentificacionEnum
                                                                                         tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long
                                                                                         periodo, Long idCartera) {
        String firmaServicio = "CarteraBusiness.consultarCarteraCotizantesAportante(TipoIdentificacionEnum, String, " + "TipoSolicitanteMovimientoAporteEnum, Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        logger.info("------------------------------------");
        logger.info(tipoIdentificacion);
        logger.info(numeroIdentificacion);
        List<CarteraDependienteModeloDTO> listaCarteraDependienteDTO = consultasModeloCore.consultarCarteraCotizantesAportanteLC(tipoIdentificacion, numeroIdentificacion, tipoSolicitante, periodo, idCartera, null);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCarteraDependienteDTO;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportanteLC(TipoIdentificacionEnum
                                                                                           tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long
                                                                                           periodo, Long idCartera, TipoLineaCobroEnum lineaCobro) {
        String firmaServicio = "CarteraBusiness.consultarCarteraCotizantesAportante(TipoIdentificacionEnum, String, " + "TipoSolicitanteMovimientoAporteEnum, Long, Long, TipoLineaCobroEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CarteraDependienteModeloDTO> listaCarteraDependienteDTO = consultasModeloCore.consultarCarteraCotizantesAportanteLC(tipoIdentificacion, numeroIdentificacion, tipoSolicitante, periodo, idCartera, lineaCobro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCarteraDependienteDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarCarteraDependiente(com.asopagos.dto.modelo.CarteraDependienteModeloDTO)
     */
    @Override
    public void guardarCarteraDependiente(CarteraDependienteModeloDTO carteraDependienteDTO) {
        String firmaServicio = "CarteraBusiness.guardarCarteraDependiente(CarteraDependienteModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        if (carteraDependienteDTO.getIdCarteraDependiente() == null) {
            List<Long> idsCarteraDependiente = consultasModeloCore.obtenerValoresSecuencia(SECUENCIA_CARTERA_DEPENDIENTE, 1);
            carteraDependienteDTO.setIdCarteraDependiente(idsCarteraDependiente.get(0));
        }
        consultasModeloCore.guardarCarteraDependiente(carteraDependienteDTO);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarCarteraNovedad(com.asopagos.dto.modelo.CarteraNovedadModeloDTO)
     */
    @Override
    public void guardarCarteraNovedad(CarteraNovedadModeloDTO carteraNovedadDTO) {
        try {
            logger.debug("Inicia guardarCarteraNovedad");
            entityManager.merge(carteraNovedadDTO.convertToEntity());
            logger.debug("Finaliza guardarCarteraNovedad");
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio guardarCarteraNovedad", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarEmpleadorNumero(java.lang.String)
     */
    public List<EmpleadorModeloDTO> consultarEmpleadorNumero(String numeroIdentificacion) {
        logger.debug("Inicio de método consultarEmpleadorNumero(String numeroIdentificacion)");
        try {
            List<Empleador> empleadores = (List<Empleador>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_NUMERO_INDENTIFICACION).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

            List<EmpleadorModeloDTO> empleadoresDTO = new ArrayList<>();
            for (Empleador empleador : empleadores) {
                EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();
                empleadorDTO.convertToDTO(empleador);
                empleadoresDTO.add(empleadorDTO);
            }
            logger.debug("Fin de método consultarEmpleadorNumero(String numeroIdentificacion)");
            return empleadoresDTO;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("No se encontró un empleador con ese número de identificación", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarGestionCartera360(com.asopagos.dto.cartera.GestionCarteraDTO)
     */
    @Override
    public GestionCarteraDTO consultarGestionCartera360(GestionCarteraDTO gestionCarteraDTO) {
        try {
            logger.debug("Inicia consultarGestionCartera360");
            List<CarteraModeloDTO> listaCarteraDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_CARTERA_360, CarteraModeloDTO.class).setParameter("tipoIdentificacion", gestionCarteraDTO.getTipoIdentificacion()).setParameter("numeroIdentificacion", gestionCarteraDTO.getNumeroIdentificacion()).setParameter("tipoAportante", gestionCarteraDTO.getTipoSolicitante()).getResultList();

            BigDecimal totalDeuda = BigDecimal.ZERO;
            StringJoiner joiner = new StringJoiner(" / ");
            List<Long> listaNumeroOperacion = new ArrayList<Long>();
            List<String> listaPeriodos = new ArrayList<String>();
            List<CarteraModeloDTO> listaResultado = new ArrayList<CarteraModeloDTO>();
            EstadoCarteraEnum estadoCartera = EstadoCarteraEnum.AL_DIA;

            for (CarteraModeloDTO registro : listaCarteraDTO) {
                totalDeuda = totalDeuda.add(registro.getDeudaPresunta() != null ? registro.getDeudaPresunta() : BigDecimal.ZERO);
                logger.info("**__** registro: " + registro.toString());
                logger.info("**__** registro id: " + registro.getIdCartera());
                Date date = new Date(registro.getPeriodoDeuda());
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
                String periodo;
                Long numeroOperacion = consultarNumeroOperacionCartera(registro.getIdCartera());
                if (!listaNumeroOperacion.contains(numeroOperacion)) {
                    listaNumeroOperacion.add(numeroOperacion);
                    //Identificador de cartera
                    registro.setIdentificadorCartera(registro.getIdCartera());
                    //Número de operación
                    registro.setIdCartera(numeroOperacion);
                    logger.info("numeroOperacion"+numeroOperacion);
                    if(consultarVigenciaOperacionCartera(numeroOperacion)){
                        registro.setEstadoOperacion(EstadoOperacionCarteraEnum.VIGENTE);
                    }
                    listaResultado.add(registro);

                    if (TipoLineaCobroEnum.LC1.equals(registro.getTipoLineaCobro()) && EstadoOperacionCarteraEnum.VIGENTE.equals(registro.getEstadoOperacion())) {
                        estadoCartera = EstadoCarteraEnum.MOROSO;
                    } else if (EstadoOperacionCarteraEnum.VIGENTE.equals(registro.getEstadoOperacion())) {
                        periodo = StringUtils.capitalize(dateFormat.format(date));
                        if (!listaPeriodos.contains(periodo)) {
                            joiner.add(periodo);
                        }
                    }

                
            }
        }
            gestionCarteraDTO.setEstadoCartera(estadoCartera);
            gestionCarteraDTO.setTotalDeuda(totalDeuda);
            gestionCarteraDTO.setListaCarteraDTO(listaResultado);
            gestionCarteraDTO.setPeriodosAdeudados(joiner.toString());
            logger.debug("Finaliza consultarGestionCartera360");
            return gestionCarteraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarGestionCartera360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarBitacoraCartera360(com.asopagos.dto.modelo.CarteraModeloDTO)
     */
    @Override
    public List<BitacoraCarteraDTO> consultarBitacoraCartera360(CarteraModeloDTO carteraDTO) {
        try {
            logger.info("Inicia consultarBitacoraCartera360" + carteraDTO.toString());
            List<TipoActividadBitacoraEnum> listaAcciones = new ArrayList<>();
            listaAcciones.add(TipoActividadBitacoraEnum.AGENDAR_VISITA);
            listaAcciones.add(TipoActividadBitacoraEnum.SOLICITAR_DOCUMENTACION);
            listaAcciones.add(TipoActividadBitacoraEnum.ANALISIS_DOCUMENTACION);
            listaAcciones.add(TipoActividadBitacoraEnum.PREPARAR_DOCUMENTACION_PARA_CONTACTO);
            listaAcciones.add(TipoActividadBitacoraEnum.GESTION_TELEFONICA);
            listaAcciones.add(TipoActividadBitacoraEnum.REGISTRO_NOTIFICACION_PERSONAL);
            listaAcciones.add(TipoActividadBitacoraEnum.GENERAR_LIQUIDACION);
            listaAcciones.add(TipoActividadBitacoraEnum.ENVIAR_LIQUIDACION);
            listaAcciones.add(TipoActividadBitacoraEnum.OTRO);
            listaAcciones.add(TipoActividadBitacoraEnum.DESAFILIACION);
            listaAcciones.add(TipoActividadBitacoraEnum.TRASPASO_DEUDA_ANTIGUA);
            listaAcciones.add(TipoActividadBitacoraEnum.APORTANTE_PROCESO_CONCURSAL);
            listaAcciones.add(TipoActividadBitacoraEnum.LIQUIDACION_PROCESO_SUCESION);
            listaAcciones.add(TipoActividadBitacoraEnum.INACTIVACION_PROCESO_CONCURSAL);
            listaAcciones.add(TipoActividadBitacoraEnum.APORTANTE_NO_TIENE_VOLUNTAD_PAGO);
            listaAcciones.add(TipoActividadBitacoraEnum.SALE_RIEGO_INCOBRABILIDAD);
            listaAcciones.add(TipoActividadBitacoraEnum.NO_DETERMINADO_ADMINISTRADORA);
            listaAcciones.add(TipoActividadBitacoraEnum.INACTIVACION_NO_DETERMINADA_ADMINISTRADORA);

            switch (carteraDTO.getTipoLineaCobro()) {
                case LC1:
                    if (MetodoAccionCobroEnum.METODO_1.equals(carteraDTO.getMetodo())) {
                        listaAcciones.add(TipoActividadBitacoraEnum.A1);
                        listaAcciones.add(TipoActividadBitacoraEnum.B1);
                        listaAcciones.add(TipoActividadBitacoraEnum.C1);
                        listaAcciones.add(TipoActividadBitacoraEnum.D1);
                        listaAcciones.add(TipoActividadBitacoraEnum.E1);
                        listaAcciones.add(TipoActividadBitacoraEnum.F1);
                        listaAcciones.add(TipoActividadBitacoraEnum.FIRMEZA_TITULO_EJECUTIVO);
                    } else {
                        listaAcciones.add(TipoActividadBitacoraEnum.A2);
                        listaAcciones.add(TipoActividadBitacoraEnum.B2);
                        listaAcciones.add(TipoActividadBitacoraEnum.C2);
                        listaAcciones.add(TipoActividadBitacoraEnum.D2);
                        listaAcciones.add(TipoActividadBitacoraEnum.E2);
                        listaAcciones.add(TipoActividadBitacoraEnum.F2);
                        listaAcciones.add(TipoActividadBitacoraEnum.G2);
                        listaAcciones.add(TipoActividadBitacoraEnum.H2);
                        listaAcciones.add(TipoActividadBitacoraEnum.FIRMEZA_TITULO_EJECUTIVO);

                    }
                    break;
                case LC2:
                    listaAcciones.add(TipoActividadBitacoraEnum.LC2A);
                    break;
                case LC3:
                    listaAcciones.add(TipoActividadBitacoraEnum.LC3A);
                    break;
                case LC4:
                case C7:
                    listaAcciones.add(TipoActividadBitacoraEnum.LC4A);
                    listaAcciones.add(TipoActividadBitacoraEnum.LC4C);
                    break;
                case LC5:
                case C8:
                    listaAcciones.add(TipoActividadBitacoraEnum.LC5A);
                    listaAcciones.add(TipoActividadBitacoraEnum.LC5C);
                    break;
                case C6:
                    if (MetodoAccionCobroEnum.METODO_1.equals(carteraDTO.getMetodo())) {
                        listaAcciones.add(TipoActividadBitacoraEnum.A1);
                        listaAcciones.add(TipoActividadBitacoraEnum.B1);
                        listaAcciones.add(TipoActividadBitacoraEnum.C1);
                        listaAcciones.add(TipoActividadBitacoraEnum.D1);
                        listaAcciones.add(TipoActividadBitacoraEnum.E1);
                        listaAcciones.add(TipoActividadBitacoraEnum.F1);
                        listaAcciones.add(TipoActividadBitacoraEnum.NOTIFICACION_MORA_DESAFILIACION);
                        listaAcciones.add(TipoActividadBitacoraEnum.CARTA_APOR_EXPUL);
                    } else {
                        listaAcciones.add(TipoActividadBitacoraEnum.A2);
                        listaAcciones.add(TipoActividadBitacoraEnum.B2);
                        listaAcciones.add(TipoActividadBitacoraEnum.C2);
                        listaAcciones.add(TipoActividadBitacoraEnum.D2);
                        listaAcciones.add(TipoActividadBitacoraEnum.E2);
                        listaAcciones.add(TipoActividadBitacoraEnum.F2);
                        listaAcciones.add(TipoActividadBitacoraEnum.G2);
                        listaAcciones.add(TipoActividadBitacoraEnum.H2);
                        listaAcciones.add(TipoActividadBitacoraEnum.NOTIFICACION_MORA_DESAFILIACION);
                        listaAcciones.add(TipoActividadBitacoraEnum.CARTA_APOR_EXPUL);
                    }
                    listaAcciones.add(TipoActividadBitacoraEnum.LC2A);
                    listaAcciones.add(TipoActividadBitacoraEnum.LC3A);
                    break;
                default:
                    break;
            }
            List<BitacoraCarteraDTO> listaBitacoraDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_CARTERA_360, BitacoraCarteraDTO.class).setParameter("idPersona", carteraDTO.getIdPersona()).setParameter("tipoAportante", carteraDTO.getTipoSolicitante()).setParameter("actividad", listaAcciones).setParameter("numeroOperacion", carteraDTO.getIdCartera().toString()).getResultList();
            List<Long> listaIdBitacora = new ArrayList<>();

            if (!listaBitacoraDTO.isEmpty()) {
                for (BitacoraCarteraDTO bitacoraCarteraDTO : listaBitacoraDTO) {
                    listaIdBitacora.add(bitacoraCarteraDTO.getIdBitacoraCartera());
                }
                listaBitacoraDTO = consultarDocumentoSoporteBitacora(listaBitacoraDTO, listaIdBitacora);
            }
            logger.debug("Finaliza consultarBitacoraCartera360");
            return listaBitacoraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarBitacoraCartera360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarGestionFiscalizacion360(com.asopagos.dto.cartera.GestionCarteraDTO)
     */
    @Override
    public GestionCarteraDTO consultarGestionFiscalizacion360(GestionCarteraDTO gestionCarteraDTO) {
        try {
            logger.debug("Inicia consultarGestionFiscalizacion360");
            List<SolicitudFiscalizacion> listaFiscalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_FISCALIZACION_360, SolicitudFiscalizacion.class).setParameter("tipoIdentificacion", gestionCarteraDTO.getTipoIdentificacion()).setParameter("numeroIdentificacion", gestionCarteraDTO.getNumeroIdentificacion()).setParameter("tipoAportante", gestionCarteraDTO.getTipoSolicitante()).getResultList();
            List<SolicitudFiscalizacionModeloDTO> listaFiscalizacionDTO = new ArrayList<SolicitudFiscalizacionModeloDTO>();

            for (SolicitudFiscalizacion solicitud : listaFiscalizacion) {
                SolicitudFiscalizacionModeloDTO solicitudDTO = new SolicitudFiscalizacionModeloDTO();
                solicitudDTO.convertToDTO(solicitud);
                listaFiscalizacionDTO.add(solicitudDTO);
            }

            gestionCarteraDTO.setListaFiscalizacionDTO(listaFiscalizacionDTO);
            logger.debug("Finaliza consultarGestionFiscalizacion360");
            return gestionCarteraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarGestionFiscalizacion360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarBitacoraFiscalizacion360(com.asopagos.dto.modelo.
     * SolicitudFiscalizacionModeloDTO)
     */
    @Override
    public List<BitacoraCarteraDTO> consultarBitacoraFiscalizacion360(SolicitudFiscalizacionModeloDTO
                                                                              fiscalizacionDTO) {
        try {
            logger.debug("Inicia consultarBitacoraFiscalizacion360");
            CicloAportante ciclo = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_APORTANTE_ID, CicloAportante.class).setParameter("idCiclo", fiscalizacionDTO.getIdCicloAportante()).getSingleResult();
            CicloAportanteModeloDTO cicloDTO = new CicloAportanteModeloDTO();
            cicloDTO.convertToDTO(ciclo);
            List<TipoActividadBitacoraEnum> listaAcciones = new ArrayList<TipoActividadBitacoraEnum>();
            listaAcciones.add(TipoActividadBitacoraEnum.INGRESO_FISCALIZACION);
            List<Object[]> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_FISCALIZACION_360).setParameter("idPersona", cicloDTO.getIdPersona()).setParameter("tipoAportante", cicloDTO.getTipoSolicitanteMovimientoAporteEnum().name()).setParameter("numeroRadicado", fiscalizacionDTO.getNumeroRadicacion()).getResultList();
            List<BitacoraCarteraDTO> listaBitacoraDTO = new ArrayList<BitacoraCarteraDTO>();

            for (Object[] registro : lista) {
                BitacoraCarteraDTO bitacoraDTO = new BitacoraCarteraDTO();
                bitacoraDTO.setFecha(registro[0] != null ? ((Date) registro[0]).getTime() : null);
                bitacoraDTO.setActividad(registro[1] != null ? TipoActividadBitacoraEnum.valueOf(registro[1].toString()) : null);
                bitacoraDTO.setMedio(registro[2] != null ? MedioCarteraEnum.valueOf(registro[2].toString()) : null);
                bitacoraDTO.setResultado(registro[3] != null ? ResultadoBitacoraCarteraEnum.valueOf(registro[3].toString()) : null);
                bitacoraDTO.setUsuario(registro[4].toString());
                bitacoraDTO.setIdPersona(registro[5] != null ? Long.parseLong(registro[5].toString()) : null);
                bitacoraDTO.setTipoSolicitante(registro[6] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(registro[6].toString()) : null);
                bitacoraDTO.setNumeroOperacion(registro[7].toString());
                Long idActividad = registro[8] != null ? Long.parseLong(registro[8].toString()) : null;
                List<String> listaDocumentos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_BITACORA_FISCALIZACION).setParameter("idActividad", idActividad).getResultList();

                List<DocumentoSoporteModeloDTO> listaDocumentoSoporte = new ArrayList<>();
                for (String idECM : listaDocumentos) {
                    DocumentoSoporteModeloDTO documentoSoporte = consultarDocumentoSoporte(idECM);
                    listaDocumentoSoporte.add(documentoSoporte);
                }
                bitacoraDTO.setDocumentosSoporte(listaDocumentoSoporte);
                listaBitacoraDTO.add(bitacoraDTO);
            }

            logger.debug("Finaliza consultarBitacoraFiscalizacion360");
            return listaBitacoraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarBitacoraFiscalizacion360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarGestionPreventiva360(com.asopagos.dto.cartera.GestionCarteraDTO)
     */
    @Override
    public GestionCarteraDTO consultarGestionPreventiva360(GestionCarteraDTO gestionCarteraDTO) {
        try {
            logger.debug("Inicia consultarGestionPreventiva360");
            List<SolicitudPreventiva> listaPreventiva = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_PREVENTIVA_360, SolicitudPreventiva.class)
                    .setParameter("tipoIdentificacion", gestionCarteraDTO.getTipoIdentificacion()).setParameter("numeroIdentificacion", gestionCarteraDTO.getNumeroIdentificacion())
                    .setParameter("tipoAportante", gestionCarteraDTO.getTipoSolicitante())
                    .getResultList();
            List<SolicitudPreventivaModeloDTO> listaPreventivaDTO = new ArrayList<SolicitudPreventivaModeloDTO>();

            for (SolicitudPreventiva solicitud : listaPreventiva) {
                SolicitudPreventivaModeloDTO solicitudDTO = new SolicitudPreventivaModeloDTO();
                solicitudDTO.convertToDTO(solicitud);
                listaPreventivaDTO.add(solicitudDTO);
            }
            gestionCarteraDTO.setListaPreventivaDTO(listaPreventivaDTO);
            logger.debug("Finaliza consultarGestionPreventiva360");
            return gestionCarteraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarGestionPreventiva360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarBitacoraPreventiva360(com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO)
     */
    @Override
    public List<BitacoraCarteraDTO> consultarBitacoraPreventiva360(SolicitudPreventivaModeloDTO preventivaDTO) {
        try {
            logger.debug("Inicia consultarBitacoraPreventiva360");
            List<TipoActividadBitacoraEnum> listaAcciones = new ArrayList<TipoActividadBitacoraEnum>();
            listaAcciones.add(TipoActividadBitacoraEnum.INGRESO_PREVENTIVA);
            listaAcciones.add(TipoActividadBitacoraEnum.CONTACTO_EFECTIVO);
            listaAcciones.add(TipoActividadBitacoraEnum.CONTACTO_NO_EFECTIVO);
            listaAcciones.add(TipoActividadBitacoraEnum.ACTUALIZACION_EXITOSA);
            listaAcciones.add(TipoActividadBitacoraEnum.ACTUALIZACION_NO_EXITOSA);
            listaAcciones.add(TipoActividadBitacoraEnum.CIERRE_PREVENTIVA);

            List<BitacoraCarteraDTO> listaBitacoraDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_CARTERA_360, BitacoraCarteraDTO.class).setParameter("idPersona", preventivaDTO.getIdPersona()).setParameter("tipoAportante", preventivaDTO.getTipoSolicitante()).setParameter("actividad", listaAcciones).setParameter("numeroOperacion", preventivaDTO.getNumeroRadicacion()).getResultList();

            List<Long> listaIdBitacora = new ArrayList<>();

            if (!listaBitacoraDTO.isEmpty()) {
                for (BitacoraCarteraDTO bitacoraCarteraDTO : listaBitacoraDTO) {
                    listaIdBitacora.add(bitacoraCarteraDTO.getIdBitacoraCartera());
                }

                listaBitacoraDTO = consultarDocumentoSoporteBitacora(listaBitacoraDTO, listaIdBitacora);
            }

            logger.debug("Finaliza consultarBitacoraPreventiva360");
            return listaBitacoraDTO;
        } catch (Exception e) {
            logger.error("Se presentó un error en el servicio consultarBitacoraPreventiva360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDocumentoSoporte(com.asopagos.dto.modelo.DocumentoSoporteModeloDTO)
     */
    @Override
    public DocumentoSoporteModeloDTO guardarDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporteDTO) {
        try {
            logger.info("Inicia el servicio registrarDocumentoSoporte(documentoSoporteDTO)");
            DocumentoSoporte documentoSoporte = documentoSoporteDTO.convertToEntity();
            documentoSoporte = entityManager.merge(documentoSoporte);
            documentoSoporteDTO.setIdDocumentoSoporte(documentoSoporte.getIdDocumentoSoporte());
            logger.info("Finaliza el servicio registrarDocumentoSoporte(documentoSoporteDTO)");
            return documentoSoporteDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio registrarDocumentoSoporte(documentoSoporteDTO)", e);
        }
        return documentoSoporteDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarEmpleadorCartera(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EmpleadorModeloDTO consultarEmpleadorCartera(Long idCartera) {
        try {
            logger.info("Inicia el servicio consultarEmpleadorCartera");
            Empleador empleador = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_CARTERA, Empleador.class).setParameter("idCartera", idCartera).getSingleResult();
            logger.info("empleador: " + empleador.toString());
            EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();
            empleadorDTO.convertToDTO(empleador);
            logger.info("empleadorDTO: " + empleadorDTO.toString());
            logger.info("Finaliza el servicio consultarEmpleadorCartera");
            return empleadorDTO;
        } catch (NoResultException e) {
            logger.info("Finaliza el servicio consultarEmpleadorCartera sin encontrar un empleador para la cartera con id " + idCartera);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarExclusionCartera()
     */
    @Override
    public void actualizarExclusionCarteraInactivacion() {
        try {
            logger.info("Inicia el servicio actualziarExclusionCartera");
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_EXCLUSION_CARTERA).executeUpdate();
            logger.info("Finaliza el servicio actualziarExclusionCartera");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio actualziarExclusionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarExclusionCarteraPorInactivar()
     */
    @Override
    public List<ExclusionCarteraDTO> consultarExclusionCarteraPorInactivar() {
        try {
            logger.info("Inicia el servicio consultarExclusionCarteraPorInactivar");
            List<ExclusionCarteraDTO> exclusiones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCLUSION_CARTERA_INACTIVAR, ExclusionCarteraDTO.class).getResultList();
            logger.info("Finaliza el servicio consultarExclusionCarteraPorInactivar");
            return exclusiones;
        } catch (NoResultException e) {
            logger.info("Finaliza el servicio consultarExclusionCarteraPorInactivar: No se encontraron exclusiones por inactivar");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarExclusionCarteraPorInactivar", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarRolAfiliadoCartera(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RolAfiliadoModeloDTO consultarRolAfiliadoCartera(Long idCartera) {
        try {
            logger.info("Inicia el servicio consultarRolAfiliadoCartera");
            RolAfiliado rolAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLAFILIADO_CARTERA, RolAfiliado.class).setParameter("idCartera", idCartera).getSingleResult();
            RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
            rolAfiliadoDTO.convertToDTO(rolAfiliado, null);
            logger.info("Finaliza el servicio consultarRolAfiliadoCartera");
            return rolAfiliadoDTO;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarRolAfiliadoCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#guardarDatosTemporalesCartera(java.lang.Long, java.lang.String)
     */
    @Override
    public void guardarDatosTemporalesCartera(Long numeroOperacion, String jsonPayload) {
        try {
            logger.info("Inicia el servicio guardarDatosTemporalesCartera");

            if (numeroOperacion != null && jsonPayload != null && !jsonPayload.equals("")) {
                DatoTemporalCarteraModeloDTO datoTemporalDTO = consultarDatoTemporalCartera(numeroOperacion);

                if (datoTemporalDTO == null) {
                    datoTemporalDTO = new DatoTemporalCarteraModeloDTO();
                }

                datoTemporalDTO.setJsonPayload(jsonPayload);
                datoTemporalDTO.setNumeroOperacion(numeroOperacion);
                entityManager.merge(datoTemporalDTO.convertToEntity());
            }

            logger.info("Finaliza el servicio guardarDatosTemporalesCartera");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio guardarDatosTemporalesCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarDatoTemporalCartera(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DatoTemporalCarteraModeloDTO consultarDatoTemporalCartera(Long numeroOperacion) {
        try {
            logger.info("Inicia el servicio consultarDatoTemporalCartera");
            DatoTemporalCartera datoTemporal = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_CARTERA, DatoTemporalCartera.class).setParameter("numeroOperacion", numeroOperacion).getSingleResult();
            DatoTemporalCarteraModeloDTO datoTemporalDTO = new DatoTemporalCarteraModeloDTO();
            datoTemporalDTO.convertToDTO(datoTemporal);
            logger.info("Finaliza el servicio consultarDatoTemporalCartera");
            return datoTemporalDTO;
        } catch (NoResultException nre) {
            logger.info("Finaliza el servicio consultarDatoTemporalCartera sin encontrar dato temporal para el número de opreación " + numeroOperacion);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#eliminarDatoTemporalCartera(java.lang.Long)
     */
    @Override
    public void eliminarDatoTemporalCartera(Long numeroOperacion) {
        logger.info("Inicia el servicio eliminarDatoTemporalCartera");
        entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_DATO_TEMPORAL_CARTERA).setParameter("numeroOperacion", numeroOperacion).executeUpdate();
        logger.info("Finaliza el servicio eliminarDatoTemporalCartera");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarNumeroOperacionCartera(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarNumeroOperacionCartera(Long idCartera) {
        try {
            logger.info("Inicia el servicio consultarNumeroOperacionCartera");
            Object numeroOperacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_OPERACION_CARTERA).setParameter("idCartera", idCartera).getSingleResult();
            logger.info("Finaliza el servicio consultarNumeroOperacionCartera");
            return numeroOperacion != null ? Long.parseLong(numeroOperacion.toString()) : null;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarNumeroOperacionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarVigenciaOperacionCartera(Long idOperacion) {
        try {
            logger.info("Inicia el servicio consultarVigenciaOperacionCartera");
            logger.info("idOperacion"+idOperacion);
            Object Vigencia = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_VIGENCIA_OPERACION_CARTERA).setParameter("numOperacion", idOperacion).getSingleResult();
            logger.info("Finaliza el servicio consultarVigenciaOperacionCartera");
            logger.info("Vigencia.toString()"+Vigencia.toString());
            if(Vigencia.toString().equals("true") || Vigencia.toString().equals("1")){     
                return true;
            } 
            
            return false;
            
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarVigenciaOperacionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarInformacionCarteraPorNumeroOperacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CarteraModeloDTO consultarInformacionCarteraPorNumeroOperacion(Long numeroOperacion) {
        try {
            logger.info("Inicia el servicio consultarInformacionCarteraPorNumeroOperacion");
            List<CarteraModeloDTO> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_NUMERO_OPERACION, CarteraModeloDTO.class).setParameter("numeroOperacion", numeroOperacion).getResultList();
            logger.info("Finaliza el servicio consultarInformacionCarteraPorNumeroOperacion");
            return lista != null && !lista.isEmpty() ? lista.get(0) : null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarInformacionCarteraPorNumeroOperacion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarBitacoraSinResultado(java.lang.Long)
     */
    @Override
    public List<BitacoraCarteraDTO> consultarBitacoraSinResultado(Long numeroOperacion, TipoActividadBitacoraEnum
            actividad, List<ResultadoBitacoraCarteraEnum> resultados) {
        String firmaMetodo = "consultarBitacoraSinResultado";
        logger.debug(LOG_INICIA + firmaMetodo);
        List<BitacoraCarteraDTO> bitacorasCartera = new ArrayList<>();

        try {
            bitacorasCartera = (List<BitacoraCarteraDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_APORTANTE_CARTERA_SIN_RESULTADO).setParameter("numeroOperacion", numeroOperacion.toString()).setParameter("actividad", actividad).setParameter("resultado", resultados).getResultList();
            List<Long> lstIdBitacora = new ArrayList<>();

            if (!bitacorasCartera.isEmpty()) {
                for (BitacoraCarteraDTO bitacoraCarteraDTO : bitacorasCartera) {
                    lstIdBitacora.add(bitacoraCarteraDTO.getIdBitacoraCartera());
                }
                bitacorasCartera = consultarDocumentoSoporteBitacora(bitacorasCartera, lstIdBitacora);
            }
        } catch (Exception e) {
            logger.error(LOG_OCURRIO_UN_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(LOG_FINALIZA + firmaMetodo);
        return !bitacorasCartera.isEmpty() ? bitacorasCartera : null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarMetodoCriterioGestionCobro()
     */
    @Override
    public MetodoAccionCobroEnum consultarMetodoCriterioGestionCobro() {
        String firmaMetodo = "consultarMetodoCriterioGestionCobro";
        try {
            logger.debug(LOG_INICIA + firmaMetodo);
            MetodoAccionCobroEnum metodoActivo = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_METODO_CRITERIO_GESTION_COBRO_ACTIVO, MetodoAccionCobroEnum.class).setParameter("lineaCobro", TipoLineaCobroEnum.LC1).setParameter("activa", Boolean.TRUE).getSingleResult();

            logger.debug(LOG_FINALIZA + firmaMetodo);
            return metodoActivo;
        } catch (NoResultException e) {
            logger.debug(LOG_FINALIZA + firmaMetodo);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarIdEntidadComunicado(TipoIdentificacionEnum, String,
     * TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    public DatosIdEntidadComunicadoDTO consultarIdEntidadComunicado(TipoIdentificacionEnum
                                                                            tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "consultarIdEntidadComunicado(TipoIdentificacionEnum,String,TipoSolicitanteMovimientoAporteEnum)";
        logger.debug(LOG_INICIA + firmaMetodo);
        DatosIdEntidadComunicadoDTO idsAsociados = new DatosIdEntidadComunicadoDTO();
        try {
            idsAsociados = (DatosIdEntidadComunicadoDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_ENTIDAD_COMUNICADO).setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();

        } catch (NoResultException nre) {
            String warnMsg = interpolate("Finaliza ConsultarIdEntidadComunicado(TipoIdentificacionEnum,String,TipoSolicitanteMovimientoAporteEnum) : No se encontraron entidades asociadas a {0} {1}, el comunicado no se asociará a la entidad (V360)", tipoIdentificacion, numeroIdentificacion);
            logger.warn(warnMsg, nre);
            return null;
        } catch (NonUniqueResultException nure) {
            String warnMsg = interpolate("Finaliza ConsultarIdEntidadComunicado(TipoIdentificacionEnum,String,TipoSolicitanteMovimientoAporteEnum) : Se encontró mas de una entidad del mismo tipo (Persona o Empleador) asociadas a {0} {1}, el comunicado no se asociará a la entidad (V360)", tipoIdentificacion, numeroIdentificacion);
            logger.warn(warnMsg, nure);
            return null;
        } catch (Exception e) {
            String warnMsg = interpolate("Finaliza ConsultarIdEntidadComunicado(TipoIdentificacionEnum,String,TipoSolicitanteMovimientoAporteEnum) : Ocurrió un error consultando las entidades asociadas a {0} {1} , el comunicado no se asociará a la entidad (V360)", tipoIdentificacion, numeroIdentificacion);
            logger.warn(warnMsg, e);
            return null;
        }
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return idsAsociados;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAgendasActividadesVisibles(java.lang.Long)
     */
    @Override
    public GestionCicloManualDTO consultarAgendasActividadesVisibles(Long numeroOperacion) {
        String firmaMetodo = "consultarAgendasActividadesVisibles(Long)";
        logger.debug(LOG_INICIA + firmaMetodo);

        GestionCicloManualDTO gestionCiclo = new GestionCicloManualDTO();
        List<ActividadCarteraModeloDTO> actividades = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDADES_CARTERA, ActividadCarteraModeloDTO.class).setParameter("numeroOperacion", numeroOperacion).getResultList();
        List<AgendaCarteraModeloDTO> agendas = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AGENDAS_CARTERA, AgendaCarteraModeloDTO.class).setParameter("numeroOperacion", numeroOperacion).getResultList();
        gestionCiclo.setLstActividadesCartera(actividades);
        gestionCiclo.setLstAgendasCartera(agendas);
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return gestionCiclo;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#consultarAgendasActividadesFiscalizacion(java.lang.Long)
     */
    @Override
    public ProgramacionFiscalizacionDTO consultarAgendasActividadesFiscalizacion(Long idSolicitud) {
        String firmaMetodo = "consultarAgendasActividadesFiscalizacion(Long)";
        logger.debug(LOG_INICIA + firmaMetodo);

        ProgramacionFiscalizacionDTO fiscalizacion = new ProgramacionFiscalizacionDTO();
        List<ActividadCarteraModeloDTO> actividades = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDADES_CARTERA_FISCALIZACION, ActividadCarteraModeloDTO.class).setParameter("idSolicitud", idSolicitud).getResultList();

        for (ActividadCarteraModeloDTO actividadCarteraModeloDTO : actividades) {
            List<ActividadDocumentoModeloDTO> documentos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_ACTIVIDAD_CARTERA, ActividadDocumentoModeloDTO.class).setParameter("idActividadCartera", actividadCarteraModeloDTO.getIdActividadFiscalizacion()).getResultList();
            actividadCarteraModeloDTO.setActividadDocumentoModeloDTOs(documentos);
        }

        List<AgendaCarteraModeloDTO> agendas = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AGENDAS_CARTERA_FISCALIZACION, AgendaCarteraModeloDTO.class).setParameter("idSolicitud", idSolicitud).getResultList();
        fiscalizacion.setActividadFiscalizacionModeloDTOs(actividades);
        fiscalizacion.setAgendaFiscalizacionModeloDTOs(agendas);
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return fiscalizacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarAgendasActividadesVisibles(java.lang.Long)
     */
    @Override
    public void actualizarAgendasActividadesVisibles(Long numeroOperacion) {
        String firmaMetodo = "actualizarAgendasActividadesVisibles(Long)";
        logger.debug(LOG_INICIA + firmaMetodo);
        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ACTIVIDADES_CARTERA).setParameter("numeroOperacion", numeroOperacion).executeUpdate();
        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_AGENDAS_CARTERA).setParameter("numeroOperacion", numeroOperacion).executeUpdate();
        logger.debug(LOG_FINALIZA + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.service.CarteraService#actualizarCarteraEstadoOK()
     */
    @Override
    public void actualizarCarteraEstadoOK() {
        String firmaMetodo = "actualizarCarteraEstadoOK()";
        logger.debug(LOG_INICIA + firmaMetodo);
        consultasModeloCore.actualizarRegistrosCarteraOK();
        logger.debug(LOG_FINALIZA + firmaMetodo);
    }

    @Override
    public BigDecimal obtenerDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String
            numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante, String periodoEvaluacion) {
        String firmaMetodo = "obtenerDeudaPresuntaCartera(String,String,String,String)";
        logger.debug(LOG_INICIA + firmaMetodo);
        BigDecimal deuda = BigDecimal.ZERO;
        deuda = consultasModeloCore.obtenerDeudaPresunta(tipoIdentificacion, numeroIdentificacion, tipoAportante, periodoEvaluacion);
        logger.debug(LOG_FINALIZA + firmaMetodo);
        return deuda;
    }

    @Override
    public void guardarTiempoProcesoCartera(TiempoProcesoCartera procesoCartera) {
        String firmaMetodo = "guardarTiempoProcesoCartera(TiempoProcesoCartera procesoCartera)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            entityManager.merge(procesoCartera);
            logger.debug("Finaliza guardarTiempoProcesoCartera");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AportanteGestionManualDTO> exportarDetalleAportesDeudaAntigua() {
        try {
            logger.debug("Inicio de método exportarDetalleAportesDeudaAntigua()");

            List<String> acciones = new ArrayList<String>();
            acciones.add(TipoAccionCobroEnum.A1.name());
            acciones.add(TipoAccionCobroEnum.B1.name());
            acciones.add(TipoAccionCobroEnum.C1.name());
            acciones.add(TipoAccionCobroEnum.D1.name());
            acciones.add(TipoAccionCobroEnum.E1.name());
            acciones.add(TipoAccionCobroEnum.F1.name());
            acciones.add(TipoAccionCobroEnum.A2.name());
            acciones.add(TipoAccionCobroEnum.B2.name());
            acciones.add(TipoAccionCobroEnum.C2.name());
            acciones.add(TipoAccionCobroEnum.D2.name());
            acciones.add(TipoAccionCobroEnum.E2.name());
            acciones.add(TipoAccionCobroEnum.F2.name());
            acciones.add(TipoAccionCobroEnum.G2.name());
            acciones.add(TipoAccionCobroEnum.H2.name());

            acciones.add(TipoAccionCobroEnum.A01.name());
            acciones.add(TipoAccionCobroEnum.AB1.name());
            acciones.add(TipoAccionCobroEnum.BC1.name());
            acciones.add(TipoAccionCobroEnum.CD1.name());
            acciones.add(TipoAccionCobroEnum.DE1.name());
            acciones.add(TipoAccionCobroEnum.EF1.name());
            acciones.add(TipoAccionCobroEnum.A02.name());
            acciones.add(TipoAccionCobroEnum.AB2.name());
            acciones.add(TipoAccionCobroEnum.BC2.name());
            acciones.add(TipoAccionCobroEnum.CD2.name());
            acciones.add(TipoAccionCobroEnum.DE2.name());
            acciones.add(TipoAccionCobroEnum.EF2.name());
            acciones.add(TipoAccionCobroEnum.FG2.name());
            acciones.add(TipoAccionCobroEnum.GH2.name());

            List<Object[]> aportantesObject = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTES_LINEA_COBRO).setParameter("lineaCobro", TipoLineaCobroEnum.C6.name()).setParameter("accionCobro", acciones).setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE.name()).setParameter("filtroNumIdentificacion", null).getResultList();


            List<AportanteGestionManualDTO> aportantes = new ArrayList<>();

            for (Object[] aportanteObject : aportantesObject) {
                AportanteGestionManualDTO aportante = new AportanteGestionManualDTO();
                aportante.setAccionCobro(TipoAccionCobroEnum.valueOf((String) aportanteObject[0]));
                aportante.setLineaCobro(TipoLineaCobroEnum.valueOf((String) aportanteObject[1]));
                aportante.setTipoDeuda(TipoDeudaEnum.valueOf((String) aportanteObject[2]));
                aportante.setMontoDeuda(aportanteObject[3] != null ? new BigDecimal(aportanteObject[3].toString()) : new BigDecimal("0"));
                aportante.setEstadoCartera(EstadoCarteraEnum.valueOf((String) aportanteObject[4]));
                aportante.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf((String) aportanteObject[5]));
                aportante.setNumeroIdentificacion((String) aportanteObject[6]);
                aportante.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) aportanteObject[7]));
                aportante.setNombreCompleto((String) aportanteObject[8]);
                aportante.setFechaIngreso(aportanteObject[9] != null ? ((Date) aportanteObject[9]).getTime() : null);
                aportante.setDeuda(SubTipoDeudaEnum.DEUDA_PRESUNTA);
                aportante.setIdCartera(Long.parseLong(aportanteObject[10].toString()));
                aportantes.add(aportante);
            }

            logger.debug("Fin de método exportarDetalleAportesDeudaAntigua()");
            return aportantes;
        } catch (Exception e) {
            logger.error("Ocurrió un error en exportarDetalleAportesDeudaAntigua()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de generar el log con los parámetros enviados al ejecutar el SP USP_ExecuteCARTERAConsultarGestionPreventiva
     *
     * @param procedimiento query con los parámetros
     * @author Francisco Alejandro Hoyos Rojas <fhoyos@heinsohn.com.co>
     */
    private void logEjecucionGestionPreventiva(StoredProcedureQuery procedimiento) {
        logger.debug("periodoInicialEmpleador " + procedimiento.getParameterValue("periodoInicialEmpleador"));
        logger.debug("periodoFinalEmpleador " + procedimiento.getParameterValue("periodoFinalEmpleador"));
        logger.debug("periodoInicial " + procedimiento.getParameterValue("periodoInicial"));
        logger.debug("periodoFinal " + procedimiento.getParameterValue("periodoFinal"));
        logger.debug("periodoInicialMoraEmpleador " + procedimiento.getParameterValue("periodoInicialMoraEmpleador"));
        logger.debug("periodoFinalMoraEmpleador " + procedimiento.getParameterValue("periodoFinalMoraEmpleador"));
        logger.debug("periodoInicialMora " + procedimiento.getParameterValue("periodoInicialMora"));
        logger.debug("periodoFinalMora " + procedimiento.getParameterValue("periodoFinalMora"));
        logger.debug("tipoAfiliadoEnum " + procedimiento.getParameterValue("tipoAfiliadoEnum"));
        logger.debug("tipoSolicitanteEnum " + procedimiento.getParameterValue("tipoSolicitanteEnum"));
        logger.debug("noAplicarCriterioCantVecesMoroso " + procedimiento.getParameterValue("noAplicarCriterioCantVecesMoroso"));
        logger.debug("esAutomatico " + procedimiento.getParameterValue("esAutomatico"));
        logger.debug("estadoMoroso " + procedimiento.getParameterValue("estadoMoroso"));
        logger.debug("estadoAlDia " + procedimiento.getParameterValue("estadoAlDia"));
        logger.debug("persona s" + procedimiento.getParameterValue("personas"));
        logger.debug("valorAportes " + procedimiento.getParameterValue("valorAportes"));
        logger.debug("cantidadTrabajadoresActivos " + procedimiento.getParameterValue("cantidadTrabajadoresActivos"));
        logger.debug("cantidadDiaHabilEjecucioAutomatica " + procedimiento.getParameterValue("cantidadDiaHabilEjecucioAutomatica"));
        logger.debug("diaHabilActual " + procedimiento.getParameterValue("diaHabilActual"));
    }

    @Override
    public CicloAportanteModeloDTO guardarCicloAportante(CicloAportanteModeloDTO ciclo) {
        logger.debug("Inicio de método guardarCicloAportante(CicloAportanteModeloDTO )");
        try {
            CicloAportante cicloAportante = ciclo.convertToEntity();
            if (cicloAportante.getIdCicloAportante() != null) {
                entityManager.merge(cicloAportante);
            } else {
                entityManager.persist(cicloAportante);
            }
            ciclo.convertToDTO(cicloAportante);
            logger.debug("Fin de método guardarCicloAportante(CicloAportanteModeloDTO )");
            return ciclo;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en guardarCicloAportante: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que obtiene la acción siguiente a ejecutarse despues de un convenio de pago anulado o una exclusión que se inactivo
     * si cambia se encuentra que cartera debe cerrar una acción transitoria este metodo persiste la información relacionada
     *
     * @param accion Acción de cobro en la que está el aportante en cartera
     * @return La acción intermedia correspondiente
     */

    @Override
    public TipoAccionCobroEnum obtenerAccionCobroSiguiente(CarteraModeloDTO carteraModeloDTO) {
        logger.debug("Inicia método obtenerAccionCobroSiguiente");
        TipoAccionCobroEnum accion = carteraModeloDTO.getTipoAccionCobro();
        TipoAccionCobroEnum accionSiguiente = accion;
        TipoAccionCobroEnum accionTransitoria = null;

        if (TipoLineaCobroEnum.LC1 == carteraModeloDTO.getTipoLineaCobro()) {
            if (MetodoAccionCobroEnum.METODO_1 == carteraModeloDTO.getMetodo()) {
                accion = accion != null ? accion : TipoAccionCobroEnum.A01;
            }
            if (MetodoAccionCobroEnum.METODO_2 == carteraModeloDTO.getMetodo()) {
                accion = accion != null ? accion : TipoAccionCobroEnum.A02;
            }

            switch (accion) {
                case A01:
                    accionSiguiente = TipoAccionCobroEnum.A1;
                    break;
                case A1:
                    accionTransitoria = TipoAccionCobroEnum.AB1;
                    accionSiguiente = TipoAccionCobroEnum.B1;
                    break;
                case AB1:
                    accionSiguiente = TipoAccionCobroEnum.B1;
                    break;
                case B1:
                    accionTransitoria = TipoAccionCobroEnum.BC1;
                    accionSiguiente = TipoAccionCobroEnum.C1;
                    break;
                case BC1:
                    accionSiguiente = TipoAccionCobroEnum.C1;
                    break;
                case C1:
                    accionTransitoria = TipoAccionCobroEnum.CD1;
                    accionSiguiente = TipoAccionCobroEnum.D1;
                    break;
                case CD1:
                    accionSiguiente = TipoAccionCobroEnum.D1;
                    break;
                case D1:
                    accionTransitoria = TipoAccionCobroEnum.DE1;
                    accionSiguiente = TipoAccionCobroEnum.E1;
                    break;
                case DE1:
                    accionSiguiente = TipoAccionCobroEnum.E1;
                    break;
                case E1:
                    accionTransitoria = TipoAccionCobroEnum.EF1;
                    accionSiguiente = TipoAccionCobroEnum.F1;
                    break;
                case EF1:
                    accionSiguiente = TipoAccionCobroEnum.F1;
                    break;
                case A02:
                    accionSiguiente = TipoAccionCobroEnum.A2;
                    break;
                case A2:
                    accionTransitoria = TipoAccionCobroEnum.AB2;
                    accionSiguiente = TipoAccionCobroEnum.B2;
                    break;
                case AB2:
                    accionSiguiente = TipoAccionCobroEnum.B2;
                    break;
                case B2:
                    accionTransitoria = TipoAccionCobroEnum.BC2;
                    accionSiguiente = TipoAccionCobroEnum.C2;
                    break;
                case BC2:
                    accionSiguiente = TipoAccionCobroEnum.C2;
                    break;
                case C2:
                    accionTransitoria = TipoAccionCobroEnum.CD2;
                    accionSiguiente = TipoAccionCobroEnum.D2;
                    break;
                case CD2:
                    accionSiguiente = TipoAccionCobroEnum.D2;
                    break;
                case D2:
                    accionTransitoria = TipoAccionCobroEnum.DE2;
                    accionSiguiente = TipoAccionCobroEnum.E2;
                    break;
                case DE2:
                    accionSiguiente = TipoAccionCobroEnum.E2;
                    break;
                case E2:
                    accionTransitoria = TipoAccionCobroEnum.EF2;
                    accionSiguiente = TipoAccionCobroEnum.F2;
                    break;
                case EF2:
                    accionSiguiente = TipoAccionCobroEnum.F2;
                    break;
                case F2:
                    accionTransitoria = TipoAccionCobroEnum.FG2;
                    accionSiguiente = TipoAccionCobroEnum.G2;
                    break;
                case FG2:
                    accionSiguiente = TipoAccionCobroEnum.G2;
                    break;
                case G2:
                    accionTransitoria = TipoAccionCobroEnum.GH2;
                    accionSiguiente = TipoAccionCobroEnum.H2;
                    break;
                case GH2:
                    accionSiguiente = TipoAccionCobroEnum.H2;
                    break;
                default:
            }
        } else if (TipoLineaCobroEnum.LC2 == carteraModeloDTO.getTipoLineaCobro()) {
            accion = accion != null ? accion : TipoAccionCobroEnum.LC20;
            if (TipoAccionCobroEnum.LC20.equals(accion)) {
                accionSiguiente = TipoAccionCobroEnum.LC2A;
            }
        } else if (TipoLineaCobroEnum.LC3 == carteraModeloDTO.getTipoLineaCobro()) {
            accion = accion != null ? accion : TipoAccionCobroEnum.LC30;
            if (TipoAccionCobroEnum.LC30.equals(accion)) {
                accionSiguiente = TipoAccionCobroEnum.LC3A;
            }
        } else if (TipoLineaCobroEnum.LC4 == carteraModeloDTO.getTipoLineaCobro()) {
            accion = accion != null ? accion : TipoAccionCobroEnum.LC40;
            if (TipoAccionCobroEnum.LC40.equals(accion)) {
                accionSiguiente = TipoAccionCobroEnum.LC4A;
            }
        } else if (TipoLineaCobroEnum.LC5 == carteraModeloDTO.getTipoLineaCobro()) {
            accion = accion != null ? accion : TipoAccionCobroEnum.LC50;
            if (TipoAccionCobroEnum.LC50.equals(accion)) {
                accionSiguiente = TipoAccionCobroEnum.LC5A;
            }
        }

        if (accionTransitoria != null) {
            try {
                carteraModeloDTO.setTipoAccionCobro(accionTransitoria);
                carteraModeloDTO.setFechaAsignacionAccion(new Date().getTime());
                entityManager.merge(carteraModeloDTO.convertToEntity());
                HistoricoAsignacionCartera historico = new HistoricoAsignacionCartera();
                historico.setIdCartera(carteraModeloDTO.getIdCartera());
                historico.setTipoAccionCobro(carteraModeloDTO.getTipoAccionCobro());
                historico.setFechaAsignacionAccion(new Date());
                entityManager.persist(historico);
            } catch (Exception e) {
                logger.error("Se presentó un error en el servicio obtenerAccionCobroSiguiente, retomarAccionesCobro: actualizar cartera", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        logger.debug("Finaliza método obtenerAccionCobroSiguiente");
        return accionSiguiente;
    }


    /**
     * Servicio que consulta todos los registros de cartera.
     *
     * @param idPersona
     * @param tipoSolicitante
     * @return lista con todas las carteras.
     */
    @Override
    public List<CarteraModeloDTO> consultarCarteraPersona(Long idPersona, TipoSolicitanteMovimientoAporteEnum
            tipoSolicitante) {
        logger.debug("Inicio de método consultarCarteraPersona(Long, TipoSolicitanteMovimientoAporteEnum)");
        try {
            List<CarteraModeloDTO> listaCartera = new ArrayList<CarteraModeloDTO>();
            listaCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_PERSONA).setParameter("idPersona", idPersona).setParameter("tipoSolicitante", tipoSolicitante).getResultList();
            return listaCartera;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarCarteraPersona: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void actualizarDeudaRealPorIdCarteraServices(String numeroIdentificacion) {
        try {
            logger.info("Inicia el servicio actualizarDeudaRealPorIdCartera:" + numeroIdentificacion);
            List<Object> consultarCarteraDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_FIRMEZA_TITULO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getResultList();
            logger.info("consultarCarteraDTO::" + new ObjectMapper().writeValueAsString(consultarCarteraDTO));
            if (!consultarCarteraDTO.isEmpty()) {
                for (Object id : consultarCarteraDTO) {
                    entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_CARTERA_DEPENDIENTE_DEUDA_REAL_IDPERSONA)
                            .setParameter("idCartera", id)
                            .executeUpdate();
                }
            }
            logger.info("Finaliza el servicio actualizarDeudaRealPorIdCartera");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio actualizarDeudaRealPorIdCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<CarteraAportantePersonaDTO> ConsultarGestionCarteraAportante(String numeroIdentificacion, String tipoLineaCobro) {
        logger.info("Inicia consultarGestionCarteraAportante:::" + numeroIdentificacion);
        try {
            List<CarteraAportantePersonaDTO> result = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            List<Object[]> personaDTO = entityManager.
                    createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_CARTERA_CARGUE)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoLineaCobro", tipoLineaCobro)
                    .getResultList();
            logger.info("personaDTO:::" + objectMapper.writeValueAsString(personaDTO));
            if (!personaDTO.isEmpty())
                result = converterObjectToAportantePersonaDTO(personaDTO);
            return result;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio ConsultarGestionCarteraAportante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    public List<CarteraAportantePersonaDTO> converterObjectToAportantePersonaDTO(List<Object[]> object) {
        try {
            logger.info("Inicia converterObjectToAportantePersonaDTO:::" + new ObjectMapper().writeValueAsString(object));
            List<CarteraAportantePersonaDTO> aportantePersonaDTOList = new ArrayList<>();
            for (Object[] carteraAportante : object) {
                CarteraAportantePersonaDTO aportantePersonaDTO = new CarteraAportantePersonaDTO();
                aportantePersonaDTO.setLineaCobroEnum(carteraAportante[0] != null ? TipoLineaCobroEnum.valueOf(String.valueOf(carteraAportante[0])) : null);
                aportantePersonaDTO.setEstadoCarteraEnum(carteraAportante[1] != null ? EstadoCarteraEnum.valueOf(String.valueOf(carteraAportante[1])) : null);
                aportantePersonaDTO.setEstadoOperacionCarteraEnum(carteraAportante[2] != null ? EstadoOperacionCarteraEnum.valueOf(String.valueOf(carteraAportante[2])) : null);
                aportantePersonaDTO.setIdPersona(carteraAportante[3] != null ? Long.parseLong(String.valueOf(carteraAportante[3])) : null);
                aportantePersonaDTO.setPeriodo(carteraAportante[4] != null ? converterStringToDate(String.valueOf(carteraAportante[4])) : null);
                aportantePersonaDTOList.add(aportantePersonaDTO);
            }
            logger.info("finaliza converterObjectToAportantePersonaDTO:::" + new ObjectMapper().writeValueAsString(aportantePersonaDTOList));
            return aportantePersonaDTOList;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio ConsultarGestionCarteraAportante", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public Long InsertarCarteraAportante(CarteraModeloDTO carteraModeloDTO) {
        try {
            logger.info("inicia InsertarCarteraAportante " + new ObjectMapper().writeValueAsString(carteraModeloDTO));
            Object generateIdCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_MAX_CARTERA)
                    .getSingleResult();
            Long id = Long.parseLong(String.valueOf(generateIdCartera));
            carteraModeloDTO.setIdCartera(id += 1);
            Date periodo = new Date(carteraModeloDTO.getPeriodoDeuda());
            logger.info("inicia periodo " + new ObjectMapper().writeValueAsString(periodo));
            logger.info("set id " + new ObjectMapper().writeValueAsString(carteraModeloDTO));
            entityManager.persist(carteraModeloDTO.convertToEntity());
            List<Object> idCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_IDENTIFICACION_PERIODO)
                    .setParameter("numeroIdentificacion", carteraModeloDTO.getPersonaDTO().getNumeroIdentificacion())
                    .setParameter("periodo", periodo)
                    .getResultList();
            logger.info("idCartera: " + new ObjectMapper().writeValueAsString(idCartera));
            return Long.parseLong(String.valueOf(idCartera.get(0)));
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el InsertarCarteraAportante ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public void InsertarCarteraAgrupadora(Long numeroOperacion, Long idCartera) {
        logger.info("inicia InsertarCarteraAgrupadora :: numeroOperacion " + numeroOperacion + " idCartera ::: " + idCartera);
        Object idCarteraAgrupadora = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_MAX_CARTERA_AGRUPADORA)
                .getSingleResult();
        Long generateId = Long.parseLong(String.valueOf(idCarteraAgrupadora));
        CarteraAgrupadoraModeloDTO carteraAgrupadoraModeloDTO = new CarteraAgrupadoraModeloDTO();
        carteraAgrupadoraModeloDTO.setIdCartera(idCartera);
        carteraAgrupadoraModeloDTO.setNumeroOperacion(numeroOperacion);
        carteraAgrupadoraModeloDTO.setIdCarteraAgrupadora(generateId += 1);
        entityManager.persist(carteraAgrupadoraModeloDTO.convertToEntity());
    }

    @Override
    public String ConsultarEstadoAportante(Long idPersona) {
        logger.info("inicia ConsultarEstadoAportante" + idPersona);
        Object estadoAportnate = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_APORTANTE_EMPLEADOR)
                .setParameter("idPersona", idPersona)
                .getSingleResult();
        logger.info("finaliza ConsultarEstadoAportante" + estadoAportnate);
        return String.valueOf(estadoAportnate);
    }

    @Override
    public Long ConsultarNumeroOperacionAgrupacion() {
        Object numeroOperacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_OPERACION)
                .getSingleResult();
        logger.info("inicia ConsultarNumeroOperacionAgrupacion :::" + numeroOperacion);
        return Long.parseLong(String.valueOf(numeroOperacion));
    }

    @Override
    public void InsertarPersonaCartera(PersonaModeloDTO personaModeloDTO) {
        logger.info("inicia InsertarPersonaCartera " + personaModeloDTO.toString());
        entityManager.createNamedQuery(NamedQueriesConstants.INSERTAR_PERSONA_CARTERA).setParameter("tipoIdentificacion", personaModeloDTO.getTipoIdentificacion()).setParameter("numeroIdentificacion", personaModeloDTO.getNumeroIdentificacion()).setParameter("primerNombre", personaModeloDTO.getPrimerNombre()).setParameter("primerApellido", personaModeloDTO.getPrimerApellido()).executeUpdate();
    }

    @Override
    public List<FirmezaDeTituloDTO> ConsultarCarteraLineaCobro() {
        logger.info("Inicia proceso de validacion de firmeza de titulo");
        try {
            List<Object[]> bitacoraCarteraSP = new ArrayList<>();
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_PERSONAS_FIRMEZA_TITULO);
            bitacoraCarteraSP = query.getResultList() != null ? query.getResultList() : null;
            return !bitacoraCarteraSP.isEmpty() ? mapObjectToNotificationDTO(bitacoraCarteraSP) : null;
        } catch (Exception e) {
            logger.error(" :: Hubo un error en el SP ConsultarCarteraLineaCobro: : " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    public List<FirmezaDeTituloDTO> mapObjectToNotificationDTO(List<Object[]> bitacoraCarteraSP) {
        List<FirmezaDeTituloDTO> response = new ArrayList<>();
        try {
            for (Object[] obj : bitacoraCarteraSP) {
                FirmezaDeTituloDTO firmeza = new FirmezaDeTituloDTO();
                firmeza.setIdCartera(obj[0] != null ? Long.parseLong(obj[0].toString()) : null);
                firmeza.setTipoIdentificacion(obj[1] != null ? TipoIdentificacionEnum.valueOf(obj[1].toString()) : null);
                firmeza.setNumeroIdentificacion(obj[2] != null ? (String) obj[2] : null);
                firmeza.setTipoSolicitante(obj[3] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(obj[3].toString()) : null);
                firmeza.setActividad(obj[4] != null ? TipoActividadBitacoraEnum.valueOf(obj[4].toString()) : null);
                firmeza.setNumeroOperacion(obj[5] != null ? (String) obj[5] : null);
                firmeza.setMetodo(obj[6] != null ? MetodoAccionCobroEnum.valueOf(obj[6].toString()) : null);
                firmeza.setFecha(obj[7] != null ? converterStringToDate(obj[7].toString()) : null);
                firmeza.setIdPersona(obj[8] != null ? Long.parseLong(obj[8].toString()) : null);
                firmeza.setResultado(obj[9] != null ? ResultadoBitacoraCarteraEnum.valueOf(obj[9].toString()) : null);
                response.add(firmeza);
            }
            return response;
        }catch (Exception e){
            logger.error(" :: Hubo un error en el metodo mapObjectToNotificationDTO: : " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public List<FiltroCarteraLineaCobroDTO> mapperFiltroCarteraLineaCobroDTOS(List<Object[]> objects) {
        try {
            List<FiltroCarteraLineaCobroDTO> filtroCarteraLineaCobroDTOS = new ArrayList<>();
            for (Object[] object : objects) {
                FiltroCarteraLineaCobroDTO filtroCarteraLineaCobroDTO = new FiltroCarteraLineaCobroDTO();
                filtroCarteraLineaCobroDTO.setIdCartera(Long.parseLong(String.valueOf(object[14])));
                filtroCarteraLineaCobroDTO.setIdPersona(Long.parseLong(String.valueOf(object[4])));
                filtroCarteraLineaCobroDTO.setPeriodoDeuda(converterStringToDate(String.valueOf(object[6])));
                filtroCarteraLineaCobroDTO.setMetodo(MetodoAccionCobroEnum.valueOf(String.valueOf(object[5])));
                filtroCarteraLineaCobroDTO.setTipoAccionCobro(TipoAccionCobroEnum.valueOf(String.valueOf(object[8])));
                filtroCarteraLineaCobroDTO.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(object[11])));
                filtroCarteraLineaCobroDTOS.add(filtroCarteraLineaCobroDTO);
            }
            return filtroCarteraLineaCobroDTOS;
        } catch (Exception e) {
            logger.error("Ocurrió un error mapperFiltroCarteraLineaCobroDTOS ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<Object[]> ConsultarCarteraLineaDeCobro2C() {
        try {
            logger.info("Inicia proceso de ConsultarCarteraLineaCobro2C");
            List<Object[]> aportantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_POR_ACCION_DE_COBRO).getResultList();
            logger.info("Finaliza proceso de ConsultarCarteraLineaCobro2C");
            return aportantes;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el ConsultarCarteraLineaCobro2C ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public FiltroIdPersonaDTO ConsultarNumeroTipoIdPersona(Long idPersona) {
        try {
            logger.info("Inicia proceso ConsultarNumeroTipoIdPersona " + idPersona);
            FiltroIdPersonaDTO modeloDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_TIPO_ID_PERSONA, FiltroIdPersonaDTO.class).setParameter("idPersona", idPersona).getSingleResult();
            logger.info("Finaliza proceso ConsultarNumeroTipoIdPersona ");
            return modeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el ConsultarNumeroTipoIdPersona ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public String consultarFirmezaTituloBitacora(Long idPersona) {
        try {
            String validadorBitacora = null;
            List<Object> resultList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FIRMEZA_TTTULO_ID_PERSONA)
                    .setParameter("idPersona", idPersona)
                    .getResultList();
            logger.info("value lista::::  " + resultList);
            if (Objects.isNull(resultList) || resultList.isEmpty()) {
                validadorBitacora = "NO_EXISTE";
            } else {
                for (Object actividad : resultList) {
                    if (actividad.equals("FIRMEZA_TITULO_EJECUTIVO")) {
                        validadorBitacora = "FIRMEZA_TITULO_EJECUTIVO";
                    } else {
                        validadorBitacora = "NO_EXISTE";
                    }
                    logger.info("value lista::::  " + actividad);
                }
            }
            logger.info("validadorBitacora " + validadorBitacora);
            return validadorBitacora;
        } catch (
                Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarFirmezaTituloBitacora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public Long consultarCarteraBitacoraId(Long idPersona) {
        logger.info("Inicia metodo consultarCarteraBitacoraId  " + idPersona);
        Object result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_BITACORA_IDCARTERA).setParameter("idPersona", idPersona).getSingleResult();
        logger.debug("return consultarCarteraDependiente  " + result);
        return Long.parseLong(String.valueOf(result));
    }

    @Override
    public void actualizarExclusionCarteraInactivar(Long idPersona) {
        try {
            logger.info("Inicia el servicio actualizarExclusionCarteraInactivar  {} {}" + idPersona);
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_EXCLUSION_CARTERA_INACTIVAR).setParameter("idPersona", idPersona).executeUpdate();
            logger.info("Finaliza el servicio actualizarExclusionCarteraInactivar");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio actualizarExclusionCarteraInactivar", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public List<AportanteAccionCobroDTO> enviarComunicadoH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        try {
            logger.debug("Inicia método asignarAccionCobro");
            List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = consultasModeloCore.enviarComunicadoH2C2ToF1C6(accionCobro);
            logger.debug("Finaliza método asignarAccionCobro");
            return listaAportanteAccionCobroDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio enviarComunicadoH2C2ToF1C6", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public List<AportanteAccionCobroDTO> enviarComunicadoExpulsionH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        try {
            logger.debug("Inicia método asignarAccionCobro");
            List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = consultasModeloCore.enviarComunicadoExpulsionH2C2ToF1C6(accionCobro);
            logger.debug("Finaliza método asignarAccionCobro");
            return listaAportanteAccionCobroDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio enviarComunicadoH2C2ToF1C6", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public List<AportanteAccionCobroDTO> obtenerAportantesParaExpulsionPorIds(TipoAccionCobroEnum accionCobro, List<Long> idPersonasAProcesar) {
        try {
            logger.debug("Inicia método asignarAccionCobro");
            List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO = consultasModeloCore.obtenerAportantesParaExpulsionPorIds(accionCobro, idPersonasAProcesar);
            logger.debug("Finaliza método asignarAccionCobro");
            return listaAportanteAccionCobroDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio enviarComunicadoH2C2ToF1C6", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public String consultarActividadCarIdNumeroIdentificacion(Long carId, String perNumeroIdentificacion) {
        try {
            logger.info("inicia consulta de actividad carId: " + carId + " perNumeroIdentificacion: " + perNumeroIdentificacion);
            String actividad = consultasModeloCore.consultarActividadCarIdNumeroIdentificacion(carId, perNumeroIdentificacion);
            logger.info("finaliza consulta de actividad ");
            return actividad;
        } catch (Exception e) {
            logger.error("ocurrio un error en la consulta de la actividad");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public Long ConsultarDiasActaGeneracion() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            logger.info("inicia consulta de consultarDiasActaGeneracion");
            Object response = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIAS_GENERACION_ACTA_LIQUIDACION)
                    .getSingleResult();
            logger.info("Finaliza consulta de consultarDiasActaGeneracion :: " + mapper.writeValueAsString(response));
            return Long.parseLong(mapper.writeValueAsString(response));
        } catch (Exception e) {
            logger.error("ocurrio un error en la consulta de consultarDiasActaGeneracion");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public Long ConsultarFechaNotificacionBitacora(String numeroIdentificacion) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            logger.info("inicia consulta de consultarfechaNotificacion");
            Object response = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIAS_NOTIFICACION_BITACORA)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            Date fecha = (Date) response;
            logger.info("Finaliza consulta de consultarfechaNotificacion :: " + mapper.writeValueAsString(fecha));
            return fecha.getTime();
        } catch (Exception e) {
            logger.error("ocurrio un error en la consulta de consultarfechaNotificacion");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public List<CarteraNovedadModeloDTO> consultarTipoNovedadesId(ConsultarNovedadesIdDTO consultarNovedadesIdDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            logger.info("consultarNovedadesIdDTO" + objectMapper.writeValueAsString(consultarNovedadesIdDTO));
            List<Long> listIdPersons = consultarNovedadesIdDTO.getListIdPersons();
            List<CarteraNovedadModeloDTO> novedadModeloDTOList = new ArrayList<>();
            int cantidadPorConsulta = 2000;
            int inicio = 0;
            int fin = 0;
            List<Object[]> result = null;
            for (int i = 0; i < listIdPersons.size(); i += cantidadPorConsulta) {
                fin += cantidadPorConsulta;
                if (fin > listIdPersons.size()) {
                    fin = listIdPersons.size();
                }

                result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_ID)
                        .setParameter("perId", listIdPersons.subList(inicio, fin))
                        .getResultList();

                for (Object[] objects : result) {
                    CarteraNovedadModeloDTO carteraNovedadModeloDTO = new CarteraNovedadModeloDTO();
                    carteraNovedadModeloDTO.setIdPersona(Long.parseLong(String.valueOf(objects[0])));
                    carteraNovedadModeloDTO.setTipoNovedad(TipoTransaccionEnum.valueOf(String.valueOf(objects[1])));
                    novedadModeloDTOList.add(carteraNovedadModeloDTO);
                }

                inicio = fin;
            }
            //logger.info("result::" + objectMapper.writeValueAsString(result));
            return novedadModeloDTOList;
        } catch (Exception e) {
            logger.error("ocurrio un error en la consulta de consultarNovedades");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public Long ConsultarNumeroDeOperacion(String numeroIdentificacion) {
        Long result = 0L;

        //try {
        logger.info("inicia consulta de ConsultarNumeroDeOperacion" + numeroIdentificacion);
        Object response = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUM_OPERACION_BITACORA)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getSingleResult();
        logger.info("Finaliza consulta de ConsultarNumeroDeOperacion :: " + response);
        result = Long.parseLong(String.valueOf(response));
        //} catch (NoResultException e) {
        logger.info("NO HAY RESULTADOS PARA LA IDENTIFICACION: " + numeroIdentificacion);
        // } catch (Exception e) {
        //     logger.error("ocurrio un error en la consulta de NumeroDeOperacion");
        //     throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        // }
        return result;
    }

    @Override
    public void actualizarDeudaRealDeudaPresunta(BigDecimal deudaReal, BigDecimal deudaPresunta, Long idCarteraDependiente, Long idCartera) {
        try {
            logger.info("Inicia actualizarDeudaRealDeudaPresunta");
            String consultarActividadBitacora = consultarActividadBitacora(idCartera);
            Object[] result = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_DEPENDIENTE_ID)
                    .setParameter("cadId", idCarteraDependiente)
                    .getSingleResult();
            CarteraDependienteModeloDTO modeloDTO = mapCarteraDependienteModeloDTO(result);
            if (consultarActividadBitacora.equals("NO_EXISTE")) {
                modeloDTO.setDeudaPresunta(deudaPresunta);
                entityManager.merge(modeloDTO.convertToEntity());
            } else if (consultarActividadBitacora.equals("FIRMEZA_TITULO_EJECUTIVO")) {
                modeloDTO.setDeudaPresunta(deudaPresunta);
                modeloDTO.setDeudaReal(deudaReal);
                entityManager.merge(modeloDTO.convertToEntity());
            }
            consultarDeudaPresuntaDependiente(idCartera);
            logger.info("Finaliza consulta de actualizarDeudaRealDeudaPresunta ::");
        } catch (Exception e) {
            logger.error("ocurrio un error en actualizarDeudaRealDeudaPresunta");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public CarteraDependienteModeloDTO mapCarteraDependienteModeloDTO(Object[] object) {
        CarteraDependienteModeloDTO modeloDTO = new CarteraDependienteModeloDTO();
        modeloDTO.setDeudaPresunta(new BigDecimal(object[0].toString()));
        modeloDTO.setEstadoOperacion(EstadoOperacionCarteraEnum.valueOf(object[1].toString()));
        modeloDTO.setIdCartera(Long.parseLong(object[2].toString()));
        modeloDTO.setIdPersona(Long.parseLong(object[3].toString()));
        modeloDTO.setDeudaReal(new BigDecimal(object[4].toString()));
        modeloDTO.setIdCarteraDependiente(Long.parseLong(object[6].toString()));
        return modeloDTO;
    }

    public String consultarActividadBitacora(Long carId) {
        try {
            logger.info("consultarActividadBitacora::" + carId);
            String validadorBitacora = null;
            Object numeroIdentificacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDENTIFICACION_CARTERA_ID)
                    .setParameter("carId", carId)
                    .getSingleResult();
            List<Object> resultList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDAD_BITACORA_CARTERTA_NUMERO_IDENTENDIFICACION)
                    .setParameter("numeroIdentificacion", numeroIdentificacion.toString())
                    .getResultList();
            for (Object actividad : resultList) {
                if (actividad.equals("FIRMEZA_TITULO_EJECUTIVO")) {
                    validadorBitacora = actividad.toString();
                } else {
                    validadorBitacora = "NO_EXISTE";
                }
            }
            return validadorBitacora;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarFirmezaTituloBitacora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public void consultarDeudaPresuntaDependiente(Long idCard) throws JsonProcessingException, ParseException {
        logger.info("Inicia consultarDeudaPresuntaDependiente" + idCard);
        BigDecimal sumaDeudaPresunta = new BigDecimal(0);
        List<Object> resultList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_CARTERA_DEPENDIENTE_ID)
                .setParameter("carId", idCard)
                .getResultList();
        for (Object deudaPresunta : resultList) {
            sumaDeudaPresunta = sumaDeudaPresunta.add((BigDecimal) deudaPresunta);
        }
        actualizarDeudaPresuntaCartera(idCard, sumaDeudaPresunta);
        logger.info("Finaliza consultarDeudaPresuntaDependiente");
    }

    public void actualizarDeudaPresuntaCartera(Long id, BigDecimal deudaPresunta) throws JsonProcessingException, ParseException {
        logger.info("Inicia actualizarDeudaPresuntaCartera " + id + "deudaPresunta " + deudaPresunta);
        Object[] result = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_ID)
                .setParameter("carId", id)
                .getSingleResult();
        CarteraModeloDTO modeloDTO = mapObjectToCarteraModeloDTO(result, deudaPresunta);
        entityManager.merge(modeloDTO.convertToEntity());
        logger.info("Finaliza actualizarDeudaPresuntaCartera ");
    }

    public CarteraModeloDTO mapObjectToCarteraModeloDTO(Object[] object, BigDecimal deudaPresunta) throws ParseException {
        logger.info("entra a mapper ");
        CarteraModeloDTO carteraModeloDTO = new CarteraModeloDTO();
        carteraModeloDTO.setDeudaPresunta(deudaPresunta);
        carteraModeloDTO.setEstadoCartera(EstadoCarteraEnum.valueOf(object[1].toString()));
        carteraModeloDTO.setEstadoOperacion(EstadoOperacionCarteraEnum.valueOf((object[2].toString())));
        carteraModeloDTO.setFechaCreacion(converterStringToDate(String.valueOf(object[3])));
        carteraModeloDTO.setIdPersona(Long.valueOf((object[4].toString())));
        carteraModeloDTO.setMetodo(MetodoAccionCobroEnum.valueOf((object[5].toString())));
        carteraModeloDTO.setPeriodoDeuda(converterStringToDate(String.valueOf(object[6])));
        carteraModeloDTO.setTipoAccionCobro(TipoAccionCobroEnum.valueOf((object[8].toString())));
        carteraModeloDTO.setTipoDeuda(TipoDeudaEnum.valueOf((object[9].toString())));
        carteraModeloDTO.setTipoLineaCobro(TipoLineaCobroEnum.valueOf((object[10].toString())));
        carteraModeloDTO.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.valueOf((object[11].toString())));
        carteraModeloDTO.setFechaAsignacionAccion(converterStringToDate(String.valueOf(object[12])));
        carteraModeloDTO.setIdCartera(Long.parseLong(object[14].toString()));
        carteraModeloDTO.setDeudaTotal((BigDecimal) object[15]);
        logger.info("termina a mapper ");
        return carteraModeloDTO;
    }

    public void guardarCotizateDependiente(List<CarteraDependienteModeloDTO> carteraDependienteModeloDTOList) {
        try {
            for (CarteraDependienteModeloDTO dependienteModeloDTO : carteraDependienteModeloDTOList) {
                CarteraDependienteModeloDTO modeloDTO = new CarteraDependienteModeloDTO();
                Long idPersona = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_IDENTIFICACION)
                        .setParameter("numeroIdentificacion", dependienteModeloDTO.getNumeroIdentificacion())
                        .setParameter("tipoIdentificacion", dependienteModeloDTO.getTipoIdentificacion())
                        .getSingleResult();
                logger.info("idPersona " + new ObjectMapper().writeValueAsString(idPersona));
                Object idCateraDependiente = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_MAX_CARTERA_DEPENDIENTE)
                        .getSingleResult();
                logger.info("idCateraDependiente " + new ObjectMapper().writeValueAsString(idCateraDependiente));
                Long generateId = Long.parseLong(String.valueOf(idCateraDependiente));
                if (Objects.nonNull(idPersona)) {
                    modeloDTO.setIdCarteraDependiente(generateId += 1);
                    modeloDTO.setIdPersona(idPersona);
                    modeloDTO.setIdCartera(dependienteModeloDTO.getIdCartera());
                    modeloDTO.setEstadoOperacion(EstadoOperacionCarteraEnum.VIGENTE);
                    modeloDTO.setDeudaPresunta(dependienteModeloDTO.getDeudaPresunta());
                    modeloDTO.setDeudaReal(new BigDecimal(0));
                    modeloDTO.setAgregadoManualmente(null);
                    logger.info("modeloDTO " + new ObjectMapper().writeValueAsString(modeloDTO));
                    entityManager.persist(modeloDTO.convertToEntity());
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarFirmezaTituloBitacora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    public List<Object[]> documentoFiscalizacionData(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.info("Inicia documentoFiscalizacionData (TipoIdentificacionEnum " + tipoIdentificacion + " ,numeroIdentificacion " + numeroIdentificacion + ") ");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Object[]> aportantes = entityManager.
                    createNamedQuery(NamedQueriesConstants.CONSULTAR_DATA_DOCUMENTO_FISCALIZACION)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .getResultList();
            logger.info("Finaliza documentoFiscalizacionData " + mapper.writeValueAsString(aportantes));
            return aportantes;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el documentoFiscalizacionData ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BitacoraCarteraDTO consultarBitacoraPersona(Long perId) {
        try {
            logger.info("Inicia consultarBitacoraPersona " + perId);
            List<Object[]> bitacoraCartera = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_CATERA)
                    .setParameter("perId", perId)
                    .getResultList();
            logger.info("bitacoraCartera query " + new ObjectMapper().writeValueAsString(bitacoraCartera));
            return !bitacoraCartera.isEmpty() ? mapBitacoraCarteraDTOToObject(bitacoraCartera) : null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarBitacora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    public BitacoraCarteraDTO mapBitacoraCarteraDTOToObject(List<Object[]> objects) throws ParseException {
        BitacoraCarteraDTO bitacoraCarteraDTO = new BitacoraCarteraDTO();
        for (Object[] bitacora : objects) {
            bitacoraCarteraDTO.setFecha(converterStringToDate(String.valueOf(bitacora[0])));
            bitacoraCarteraDTO.setActividad(TipoActividadBitacoraEnum.valueOf(String.valueOf(bitacora[1])));
        }
        return bitacoraCarteraDTO;
    }

    public DocumentosSeguimientoGestionDTO createDocumentosSeguimientoGestion(DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Inicia createDocumentosSeguimientoGestion " + mapper.writeValueAsString(documentosSeguimientoGestionDTO));
            entityManager.persist(mapper.convertValue(documentosSeguimientoGestionDTO, DocumentosSeguimientoGestion.class));
            return documentosSeguimientoGestionDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el createDocumentosSeguimientoGestion ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<DocumentosSeguimientoGestionDTO> findDocumentosSeguimientoGestion(Long idOperacion) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Inicia findDocumentosSeguimientoGestion " + mapper.writeValueAsString(idOperacion));
            List<Object[]> query = entityManager.createNamedQuery(NamedQueriesConstants.FIND_DOCUMENTOS_SEGUIMIENTO_GESTION)
                    .setParameter("idOperacion", idOperacion)
                    .getResultList();
            logger.info("Fin de la query findDocumentosSeguimientoGestion :: " + mapper.writeValueAsString(query));
            List<DocumentosSeguimientoGestionDTO> response = new ArrayList<>();
            for (Object[] gestion : query) {
                DocumentosSeguimientoGestionDTO gestionDto = new DocumentosSeguimientoGestionDTO();
                gestionDto.setDsgId(gestion[0] != null ? Long.parseLong(String.valueOf(gestion[0])) : null);
                gestionDto.setDsgNrOperacion(gestion[1] != null ? Long.parseLong(String.valueOf(gestion[1])) : null);
                gestionDto.setDgsFecha(gestion[2] != null ? (Date) gestion[2] : null);
                gestionDto.setDsgActividad(gestion[3] != null ? (String) gestion[3] : null);
                gestionDto.setDsgMedio(gestion[4] != null ? (String) gestion[4] : null);
                gestionDto.setDsgResultado(gestion[5] != null ? (String) gestion[5] : null);
                gestionDto.setDsgUsuario(gestion[6] != null ? (String) gestion[6] : null);
                gestionDto.setDsgDocumento(gestion[7] != null ? (String) gestion[7] : null);
                response.add(gestionDto);
            }

            logger.info("Inicia MAPP " + mapper.writeValueAsString(response));
            return response;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el findDocumentosSeguimientoGestion ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public DocumentosSeguimientoNovedadesListDTO createDocumentosSeguimientoNovedades(DocumentosSeguimientoNovedadesListDTO documentosSeguimientoNovedadesListDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Inicia createDocumentosSeguimientoNovedades " + mapper.writeValueAsString(documentosSeguimientoNovedadesListDTO));
            documentosSeguimientoNovedadesListDTO.getDsnNovedad().forEach(novedad -> {
                DocumentosSeguimientoNovedadesDTO documentosSeguimientoNovedadesDTO = new DocumentosSeguimientoNovedadesDTO();
                documentosSeguimientoNovedadesDTO.setDsnNovedad(novedad);
                documentosSeguimientoNovedadesDTO.setDsnEstado(documentosSeguimientoNovedadesListDTO.getDsnEstado());
                documentosSeguimientoNovedadesDTO.setDsnFechaRegistroNovedad(documentosSeguimientoNovedadesListDTO.getDsnFechaRegistroNovedad());
                documentosSeguimientoNovedadesDTO.setDsnFecha(documentosSeguimientoNovedadesListDTO.getDsnFecha());
                documentosSeguimientoNovedadesDTO.setDsnNrOperacion(documentosSeguimientoNovedadesListDTO.getDsnNrOperacion());
                documentosSeguimientoNovedadesDTO.setDsnId(null);
                entityManager.persist(mapper.convertValue(documentosSeguimientoNovedadesDTO, DocumentosSeguimientoNovedades.class));
            });
            return documentosSeguimientoNovedadesListDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el createDocumentosSeguimientoNovedades ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<DocumentosSeguimientoNovedadesDTO> findDocumentosSeguimientoNovedades(Long idOperacion) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<DocumentosSeguimientoNovedadesDTO> response = new ArrayList<>();
            logger.info("Inicia findDocumentosSeguimientoNovedades " + mapper.writeValueAsString(idOperacion));
            List<Object[]> query = entityManager.createNamedQuery(NamedQueriesConstants.FIND_DOCUMENTOS_SEGUIMIENTO_NOVEDADES)
                    .setParameter("idOperacion", idOperacion)
                    .getResultList();

            for (Object[] novedades : query) {
                DocumentosSeguimientoNovedadesDTO novedad = new DocumentosSeguimientoNovedadesDTO();
                novedad.setDsnId(novedades[0] != null ? Long.parseLong(String.valueOf(novedades[0])) : null);
                novedad.setDsnNrOperacion(novedades[1] != null ? Long.parseLong(String.valueOf(novedades[1])) : null);
                novedad.setDsnFecha(novedades[2] != null ? (Date) novedades[2] : null);
                novedad.setDsnNovedad(novedades[3] != null ? (String) novedades[3] : null);
                novedad.setDsnFechaRegistroNovedad(novedades[4] != null ? (Date) novedades[4] : null);
                novedad.setDsnEstado(novedades[5] != null ? (String) novedades[5] : null);
                response.add(novedad);
            }
            return response;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el findDocumentosSeguimientoNovedades ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public DocumentosSeguimientoConveniosPagoDTO createDocumentosSeguimientoConveniosPago(DocumentosSeguimientoConveniosPagoDTO documentosSeguimientoConveniosPagoDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Inicia createDocumentosSeguimientoConveniosPago " + mapper.writeValueAsString(documentosSeguimientoConveniosPagoDTO));
            entityManager.persist(mapper.convertValue(documentosSeguimientoConveniosPagoDTO, DocumentosSeguimientoConveniosPago.class));
            return documentosSeguimientoConveniosPagoDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el createDocumentosSeguimientoConveniosPago ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public List<DocumentosSeguimientoConveniosPagoDTO> findDocumentosSeguimientoConveniosPago(Long idOperacion) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<DocumentosSeguimientoConveniosPagoDTO> response = new ArrayList<>();
            logger.info("Inicia findDocumentosSeguimientoConveniosPago " + mapper.writeValueAsString(idOperacion));
            List<Object[]> query = entityManager.createNamedQuery(NamedQueriesConstants.FIND_DOCUMENTOS_SEGUIMIENTO_CONVENIO_PAGO)
                    .setParameter("idOperacion", idOperacion)
                    .getResultList();
            for (Object[] ConvenioDePago : query) {
                logger.info("Inicia findDocumentosSeguimientoConveniosPago " + mapper.writeValueAsString(ConvenioDePago));
                DocumentosSeguimientoConveniosPagoDTO ConveniosDePago = new DocumentosSeguimientoConveniosPagoDTO();
                ConveniosDePago.setDscpId(ConvenioDePago[0] != null ? Long.parseLong(String.valueOf(ConvenioDePago[0])) : null);
                ConveniosDePago.setDscpNrOperacion(ConvenioDePago[1] != null ? Long.parseLong(String.valueOf(ConvenioDePago[1])) : null);
                ConveniosDePago.setDscpFecha(ConvenioDePago[2] != null ? (Date) ConvenioDePago[2] : null);
                ConveniosDePago.setDscpValorDeuda(ConvenioDePago[3] != null ? (String) ConvenioDePago[3] : null);
                ConveniosDePago.setDscpPeriodo(ConvenioDePago[4] != null ? (Date) ConvenioDePago[4] : null);
                ConveniosDePago.setDscpNrCuotas(ConvenioDePago[5] != null ? Long.parseLong(String.valueOf(ConvenioDePago[5])) : null);
                ConveniosDePago.setDscpFechaInicial(ConvenioDePago[6] != null ? (Date) ConvenioDePago[6] : null);
                ConveniosDePago.setDscpFechaFinal(ConvenioDePago[7] != null ? (Date) ConvenioDePago[7] : null);
                ConveniosDePago.setDscpResultado(ConvenioDePago[8] != null ? (String) ConvenioDePago[8] : null);
                ConveniosDePago.setDscpSoporteDocumental(ConvenioDePago[9] != null ? (String) ConvenioDePago[9] : null);
                ConveniosDePago.setDscpNorConvenio(ConvenioDePago[10] != null ? Long.parseLong(String.valueOf(ConvenioDePago[10])) : null);
                ConveniosDePago.setDscpUsuario(ConvenioDePago[11] != null ? (String) ConvenioDePago[11] : null);
                response.add(ConveniosDePago);
            }
            return response;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el findDocumentosSeguimientoConveniosPago ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    @Override
    public List<CarteraModeloDTO> consultarLineaCobroMoraParcial(UriInfo uri, HttpServletResponse response, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            List<CarteraModeloDTO> carteraModeloDTOList = new ArrayList<>();
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam("tipoIdentificacion", Objects.nonNull(tipoIdentificacion) ? tipoIdentificacion.name() : null);
            queryBuilder.addParam("numeroIdentificacion", Objects.nonNull(numeroIdentificacion) ? numeroIdentificacion : null);
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LINEA_COBRO_UNO_MORA_PARCIAL, null);
            List<Object[]> listCartera = query.getResultList();
            if (!listCartera.isEmpty() || Objects.nonNull(listCartera))
                for (Object[] cartera : listCartera) {
                    carteraModeloDTOList.add(mapObjectToCarteraDTO(cartera));
                }
            return carteraModeloDTOList;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarLineaCobroMoraParcial ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }
    
    // consultarExclusionCarteraActivaMora
    public Response exportarArchivoExclusionMoraParcialExclusion(DatosArchivoCargueExclusionMoralParcialDTO DatosArchivoCargueExclusionMoralParcial, UriInfo uriInfo, HttpServletResponse response) throws IOException {

        List<CarteraModeloMoraParcialDTO> result = new ArrayList<>();

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);

        queryBuilder.addParam("tipoIdentificacion", Objects.nonNull(DatosArchivoCargueExclusionMoralParcial.getTipoIdentificacion()) ? DatosArchivoCargueExclusionMoralParcial.getTipoIdentificacion().name() : null);
        queryBuilder.addParam("numeroIdentificacion", Objects.nonNull(DatosArchivoCargueExclusionMoralParcial.getNumeroIdentificacion()) ? DatosArchivoCargueExclusionMoralParcial.getNumeroIdentificacion() : null);

        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LINEA_COBRO_UNO_EXCLUSION_MORA_PARCIAL, null);

        List<Object[]> listCartera = query.getResultList();
        try{
            if (listCartera != null && !listCartera.isEmpty()) {
                for (Object[] cartera : listCartera) {
                    result.add(mapObjectToCarteraModeloMoraParcial(cartera));
                }
            }
        } catch(Exception e) {
            logger.info(e);
        }

        String[] cabeceras = new String[0];
        if (DatosArchivoCargueExclusionMoralParcial.getCabeceras() != null) {
            cabeceras = new String[DatosArchivoCargueExclusionMoralParcial.getCabeceras().size()];
            DatosArchivoCargueExclusionMoralParcial.getCabeceras().toArray(cabeceras);
        }

        List<String[]> datosCuerpoExcel = result.stream().map(CarteraModeloMoraParcialDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("archivos", datosCuerpoExcel, DatosArchivoCargueExclusionMoralParcial.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
        inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
        res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
    }

    public Response exportarArchivoExclusionMoraParcial(DatosArchivoCargueExclusionMoralParcialDTO DatosArchivoCargueExclusionMoralParcial, UriInfo uriInfo, HttpServletResponse response) throws IOException {

        List<CarteraModeloDTO> result = new ArrayList<>();

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);

        queryBuilder.addParam("tipoIdentificacion", Objects.nonNull(DatosArchivoCargueExclusionMoralParcial.getTipoIdentificacion()) ? DatosArchivoCargueExclusionMoralParcial.getTipoIdentificacion().name() : null);
        queryBuilder.addParam("numeroIdentificacion", Objects.nonNull(DatosArchivoCargueExclusionMoralParcial.getNumeroIdentificacion()) ? DatosArchivoCargueExclusionMoralParcial.getNumeroIdentificacion() : null);

        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LINEA_COBRO_UNO_MORA_PARCIAL, null);

        List<Object[]> listCartera = query.getResultList();
        try{
            if (listCartera != null && !listCartera.isEmpty()) {
                for (Object[] cartera : listCartera) {
                    result.add(mapObjectToCarteraDTO(cartera));
                }
            }
        } catch(Exception e) {
            logger.info(e);
        }

        String[] cabeceras = new String[0];
        if (DatosArchivoCargueExclusionMoralParcial.getCabeceras() != null) {
            cabeceras = new String[DatosArchivoCargueExclusionMoralParcial.getCabeceras().size()];
            DatosArchivoCargueExclusionMoralParcial.getCabeceras().toArray(cabeceras);
        }

        List<String[]> datosCuerpoExcel = result.stream().map(CarteraModeloDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("archivos", datosCuerpoExcel, DatosArchivoCargueExclusionMoralParcial.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
        inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
        res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
    }

    private CarteraModeloDTO mapObjectToCarteraDTO(Object[] cartera) throws ParseException {
        CarteraModeloDTO carteraModeloDTO = new CarteraModeloDTO();
        carteraModeloDTO.setDeudaPresunta(cartera[0] != null ? (BigDecimal) cartera[0] : null);
        carteraModeloDTO.setEstadoCartera(cartera[1] != null ? EstadoCarteraEnum.valueOf(String.valueOf(cartera[1])) : null);
        carteraModeloDTO.setEstadoOperacion(cartera[2] != null ? EstadoOperacionCarteraEnum.valueOf(String.valueOf(cartera[2])) : null);
        carteraModeloDTO.setFechaCreacion(cartera[3] != null ? converterStringToDate(String.valueOf(cartera[3])) : null);
        carteraModeloDTO.setIdPersona(cartera[4] != null ? Long.valueOf(String.valueOf(cartera[4])) : null);
        carteraModeloDTO.setMetodo(cartera[5] != null ? MetodoAccionCobroEnum.valueOf(String.valueOf(cartera[5])) : null);
        carteraModeloDTO.setPeriodoDeuda(cartera[6] != null ? converterStringToDate(String.valueOf(cartera[6])) : null);
        carteraModeloDTO.setRiesgoIncobrabilidad(cartera[7] != null ? RiesgoIncobrabilidadEnum.valueOf(String.valueOf(cartera[7])) : null);
        carteraModeloDTO.setTipoAccionCobro(cartera[8] != null ? TipoAccionCobroEnum.valueOf(String.valueOf(cartera[8])) : null);
        carteraModeloDTO.setTipoDeuda(cartera[9] != null ? TipoDeudaEnum.valueOf(String.valueOf(cartera[9])) : null);
        carteraModeloDTO.setTipoLineaCobro(cartera[10] != null ? TipoLineaCobroEnum.valueOf(String.valueOf(cartera[10])) : null);
        carteraModeloDTO.setTipoSolicitante(cartera[11] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(cartera[11])) : null);
        carteraModeloDTO.setFechaAsignacionAccion(cartera[12] != null ? converterStringToDate(String.valueOf(cartera[12])) : null);
        carteraModeloDTO.setUsuarioTraspaso(cartera[13] != null ? String.valueOf(cartera[13]) : null);
        carteraModeloDTO.setIdCartera(cartera[14] != null ? Long.valueOf(String.valueOf(cartera[14])) : null);
        carteraModeloDTO.setDeudaParcial(cartera[16] != null ? DeudaParcialEnum.valueOf(String.valueOf(cartera[16])) : null);
        carteraModeloDTO.setPersonaDTO(new PersonaModeloDTO());
        carteraModeloDTO.getPersonaDTO().setNumeroIdentificacion(cartera[20] != null ? String.valueOf(cartera[20]) : null);
        carteraModeloDTO.getPersonaDTO().setRazonSocial(cartera[21] != null ? String.valueOf(cartera[21]) : null);
        carteraModeloDTO.getPersonaDTO().setTipoIdentificacion(cartera[22] != null ? TipoIdentificacionEnum.valueOf(String.valueOf(cartera[22])) : null);
        return carteraModeloDTO;
    }

    @Override
    public List<CarteraModeloMoraParcialDTO> consultarExclusionCarteraActivaMora(UriInfo uri, HttpServletResponse response, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            List<CarteraModeloMoraParcialDTO> carteraModeloMoraParcialDTOS = new ArrayList<>();
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addParam("tipoIdentificacion", Objects.nonNull(tipoIdentificacion) ? tipoIdentificacion.name() : null);
            queryBuilder.addParam("numeroIdentificacion", Objects.nonNull(numeroIdentificacion) ? numeroIdentificacion : null);
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LINEA_COBRO_UNO_EXCLUSION_MORA_PARCIAL, null);
            List<Object[]> listCartera = query.getResultList();
            if (!listCartera.isEmpty() || Objects.nonNull(listCartera))
                for (Object[] cartera : listCartera) {
                    carteraModeloMoraParcialDTOS.add(mapObjectToCarteraModeloMoraParcial(cartera));
                }
            return carteraModeloMoraParcialDTOS;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarExclusionCarteraActivaMora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    private CarteraModeloMoraParcialDTO mapObjectToCarteraModeloMoraParcial(Object[] cartera) throws ParseException {
        CarteraModeloMoraParcialDTO carteraModeloMoraParcialDTO = new CarteraModeloMoraParcialDTO();
        carteraModeloMoraParcialDTO.setIdExclusionCartera(cartera[0] != null ? Long.valueOf(String.valueOf(cartera[0])) : null);
        carteraModeloMoraParcialDTO.setFechaInicio(cartera[1] != null ? converterStringToDate(String.valueOf(cartera[1])) : null);
        carteraModeloMoraParcialDTO.setFechaFinalizacion(cartera[2] != null ? converterStringToDate(String.valueOf(cartera[2])) : null);
        carteraModeloMoraParcialDTO.setPeriodoDeuda(cartera[3] != null ? converterStringToDate(String.valueOf(cartera[3])) : null);
        carteraModeloMoraParcialDTO.setDeudaPresunta(cartera[4] != null ? (BigDecimal) cartera[4] : null);
        carteraModeloMoraParcialDTO.setTipoDeuda(cartera[5] != null ? TipoDeudaEnum.valueOf(String.valueOf(cartera[5])) : null);
        carteraModeloMoraParcialDTO.setTipoAccionCobro(cartera[6] != null ? TipoAccionCobroEnum.valueOf(String.valueOf(cartera[6])) : null);
        carteraModeloMoraParcialDTO.setTipoSolicitante(cartera[7] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(cartera[7])) : null);
        carteraModeloMoraParcialDTO.setDeudaParcial(cartera[8] != null ? DeudaParcialEnum.valueOf(String.valueOf(cartera[8])) : null);
        carteraModeloMoraParcialDTO.setPersonaDTO(new PersonaModeloDTO());
        carteraModeloMoraParcialDTO.getPersonaDTO().setRazonSocial(cartera[9] != null ? String.valueOf(cartera[9]) : null);
        carteraModeloMoraParcialDTO.getPersonaDTO().setTipoIdentificacion(cartera[10] != null ? TipoIdentificacionEnum.valueOf(String.valueOf(cartera[10])) : null);
        carteraModeloMoraParcialDTO.getPersonaDTO().setNumeroIdentificacion(cartera[11] != null ? String.valueOf(cartera[11]) : null);
        carteraModeloMoraParcialDTO.setIdPersona(cartera[12] != null ? Long.valueOf(String.valueOf(cartera[12])) : null);
        carteraModeloMoraParcialDTO.setIdCartera(cartera[13] != null ? Long.valueOf(String.valueOf(cartera[13])) : null);
        carteraModeloMoraParcialDTO.setUsuarioRegistro(cartera[14] != null ? String.valueOf(cartera[14]) : null);
        carteraModeloMoraParcialDTO.setEstadoCartera(cartera[16] != null ? EstadoCarteraEnum.valueOf(String.valueOf(cartera[16])) : null);
        return carteraModeloMoraParcialDTO;
    }

    public BitacoraCarteraDTO ConsultarBitacoraPersonaRadicado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificaion, String numeroRadicacion){
        try {
            logger.info("Inicia consultarBitacoraPersona " + numeroRadicacion);
            Object[] bitacoraCartera = (Object[])entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_CATERA_PERSONA_RADICADO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificaion", numeroIdentificaion)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .getSingleResult();
            // logger.info("bitacoraCartera query "+ bitacoraCartera.length());
            return  mapBitacoraCarteraToObject(bitacoraCartera);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el consultarBitacora ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Consulta todas las bitácoras asociadas al numero de radicado
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroRadicacion
     * @return List<BitacoraCarteraDTO>
     */
    @Override
    public List<BitacoraCarteraDTO> consultarBitacoraAActualizarPersonaRadicado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion) {
        String methodName = "ConsultarBitacoraAActualizarPersonaRadicado";
        logger.info("Iniciando método " + methodName + " para tipoIdentificacion: " + tipoIdentificacion.name() + ", numeroIdentificacion: " + numeroIdentificacion + ", radicación: " + numeroRadicacion);

        try {
            List<Object[]> resultList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BITACORA_A_ACTUALIZAR_CATERA_PERSONA_RADICADO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .getResultList();

            List<BitacoraCarteraDTO> res = resultList.stream()
                    .map(this::mapBitacoraCarteraToObject)
                    .collect(Collectors.toList());

            logger.info("Consulta completada en método " + methodName + ". Número de registros encontrados: " + res.size());
            return res;
        } catch (NoResultException nre) {
            logger.warn("No se encontraron resultados en método " + methodName + " para radicación: " + numeroRadicacion);
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error inesperado en método " + methodName + " para radicación: " + numeroRadicacion, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }




            @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesoValidacionCarteraPrescrita() {
        logger.info("Inicia proceso de validacion cartera prescrita");
        try {
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CARTERA_PRESCRITA);
            query.execute();
            logger.info("Finaliza procesoValidacionCarteraPrescrita");
        } catch (Exception e) {
            logger.error("Error en el SP consultarCarteraPrescrita::: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public Boolean consultarBitacoraPrescritaGenerarLiquidacion(Long idPersona) {
        logger.info("Inicia consultarCarteraPrescriotaGenerarLiquidacion");
        try {
            Boolean resul = null;
            Object resultPrescrita = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_PRESCRITA)
                    .setParameter("idPersona", idPersona)
                    .getSingleResult();
            Object result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTERA_TOTAL)
                    .setParameter("idPersona", idPersona)
                    .getSingleResult();
            if (Integer.valueOf(String.valueOf(resultPrescrita)) != 0) {
                if (Integer.valueOf(String.valueOf(result)) - Integer.valueOf(String.valueOf(resultPrescrita)) == 0) {
                    resul = Boolean.TRUE;
                } else {
                    resul = Boolean.FALSE;
                }
            }
            logger.info("Finaliza consultarCarteraPrescriotaGenerarLiquidacion");
            return resul;
        } catch (Exception e) {
            logger.error("Error consultarCarteraPrescriotaGenerarLiquidacion::: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    public String consultarActividadBitacoraNumeroOperacion(String numeroOperacion, String actividad) {
        logger.info("Inicia consultarActividadBitacoraNumeroPeracion");
        try {
            List<Object> resultList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDAD_BITACORA_NUMERO_OPERACION)
                    .setParameter("numeroOperacion", numeroOperacion)
                    .setParameter("actividad", actividad)
                    .getResultList();
            logger.info("Finaliza ConsultarActividadBitacoraNumeroOperacion");
            return resultList.isEmpty() ? null : resultList.get(0).toString();
        } catch (Exception e) {
            logger.error("Error consultarActividadBitacoraNumeroPeracion::: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    private BitacoraCarteraDTO mapBitacoraCarteraToObject(Object[] bitacora) {
        logger.info("ACAAAAA2G" + bitacora[0]);
        BitacoraCarteraDTO bitacoraCarteraDTO = new BitacoraCarteraDTO();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Long fechalong = 0L;
        try {
            Date fecha = formatoFecha.parse(String.valueOf(bitacora[1]));
            fechalong = fecha.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
            bitacoraCarteraDTO.setIdBitacoraCartera(bitacora[0] != null ? Long.parseLong(String.valueOf(bitacora[0])) : null);
            bitacoraCarteraDTO.setFecha(fechalong != null ? fechalong : null);
            bitacoraCarteraDTO.setActividad(bitacora[2] != null ? TipoActividadBitacoraEnum.valueOf(String.valueOf(bitacora[2])) : null);
            bitacoraCarteraDTO.setMedio(bitacora[3] != null ? MedioCarteraEnum.valueOf(String.valueOf(bitacora[3])) : null);
            bitacoraCarteraDTO.setResultado(bitacora[4] != null ? ResultadoBitacoraCarteraEnum.valueOf(String.valueOf(bitacora[4])) : null);
            bitacoraCarteraDTO.setUsuario(bitacora[5] != null ? (String) bitacora[5] : null);
            bitacoraCarteraDTO.setIdPersona(bitacora[6] != null ? Long.parseLong(String.valueOf(bitacora[6])) : null);
            bitacoraCarteraDTO.setTipoSolicitante(bitacora[7] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(String.valueOf(bitacora[7])) : null);
            bitacoraCarteraDTO.setNumeroOperacion(bitacora[8] != null ? (String) bitacora[8]: null);
            bitacoraCarteraDTO.setComentarios(bitacora[9] != null ? (String) bitacora[9] : null);
        
        return bitacoraCarteraDTO;

    }


}
