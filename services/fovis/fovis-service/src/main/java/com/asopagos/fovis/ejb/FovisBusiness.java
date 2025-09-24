package com.asopagos.fovis.ejb;

import static com.asopagos.util.Interpolator.interpolate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.dto.CalificacionPostulacionDTO;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.PostulacionAsignacionDTO;
import com.asopagos.dto.fovis.HistoricoAsignacionFOVISDTO;
import com.asopagos.dto.fovis.HistoricoCrucesFOVISDTO;
import com.asopagos.dto.fovis.InformacionAporteSubsidioFOVISDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.fovis.InformacionIntegranteHogarSubsidioFOVISDTO;
import com.asopagos.dto.fovis.InformacionOferenteSubsidioFOVISDTO;
import com.asopagos.dto.fovis.InformacionSubsidioFOVISDTO;
import com.asopagos.dto.fovis.IntentoPostulacionDTO;
import com.asopagos.dto.fovis.MiembroHogarDTO;
import com.asopagos.dto.fovis.OferenteDTO;
import com.asopagos.dto.fovis.ParametrizacionMedioPagoDTO;
import com.asopagos.dto.fovis.PostulacionDTO;
import com.asopagos.dto.fovis.ProveedorDTO;
import com.asopagos.dto.fovis.ResultadoHistoricoSolicitudesFovisDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.fovis.*;
import com.asopagos.entidades.ccf.general.DocumentoAdministracionEstadoSolicitud;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.CondicionEspecialPersona;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.entidades.ccf.personas.MediosPagoCCF;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.entidades.fovis.parametricas.CicloModalidad;
import com.asopagos.entidades.fovis.parametricas.FormaPagoModalidad;
import com.asopagos.entidades.fovis.parametricas.ParametrizacionFOVIS;
import com.asopagos.entidades.fovis.parametricas.ParametrizacionModalidad;
import com.asopagos.entidades.fovis.parametricas.RangoTopeValorSFV;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.ClasificacionLicenciaEnum;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.PrioridadAsignacionEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoLicenciaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.fovis.constants.MensajesConstants;
import com.asopagos.fovis.constants.NamedQueriesConstants;
import com.asopagos.fovis.constants.QueryConstants;
import com.asopagos.fovis.dto.CartasAsignacionDTO;
import com.asopagos.fovis.dto.PostulanteDTO;
import com.asopagos.fovis.dto.RedireccionarTareaDTO;
import com.asopagos.fovis.dto.TareasHeredadasDTO;
import com.asopagos.fovis.service.FovisService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.Interpolator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.LocalDate;
import javax.persistence.StoredProcedureQuery;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de FOVIS<br/>
 * <b>Módulo:</b> Asopagos - FOVIS 3.2.1 - 3.2.5
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class FovisBusiness implements FovisService {

    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName = "fovis_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(FovisBusiness.class);

    private final Integer TOP_1 = 1;

    /**
     * tope maximo permitido por el Decreto 1467 del 2019 para la adquisicion de vivienda urbana
     *
     * @param identificadorMunicipio Identificador de el municipio
     * @return Tope Maximo para dicha ubicación
     */

    @Override
    public Long consultarTopeDec14672019(Long munId) {
        logger.debug("Inicia el servicio consultarTopeDec14672019");
        List<Long> topelist = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TOPE_POR_MUNICIPIO)
                .setParameter("munId", munId)
                .getResultList();
        logger.debug("Lista vacia " + topelist.isEmpty());
        return !topelist.isEmpty() && topelist != null ? topelist.get(0) : Long.valueOf(0);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * crearActualizarProyectoSolucionVivienda(com.asopagos.dto.modelo.
     * ProyectoSolucionViviendaModeloDTO)
     */
    @Override
    public ProyectoSolucionViviendaModeloDTO crearActualizarProyectoSolucionVivienda(ProyectoSolucionViviendaModeloDTO proyectoDTO) {
        logger.debug("Inicia el servicio crearActualizarProyectoSolucionVivienda");
        /* Se crea la Ubicación del Proyecto */
        if (proyectoDTO.getUbicacionProyecto() != null) {
            Ubicacion ubicacionProyecto = proyectoDTO.getUbicacionProyecto().convertToEntity();
            Ubicacion ubicacionProyectoManaged = entityManager.merge(ubicacionProyecto);
            proyectoDTO.getUbicacionProyecto().setIdUbicacion(ubicacionProyectoManaged.getIdUbicacion());
        }
        /* Se crea el proyecto de vivienda */
        ProyectoSolucionVivienda proyecto = proyectoDTO.convertToEntity();
        ProyectoSolucionVivienda managed = entityManager.merge(proyecto);
        proyectoDTO.setIdProyectoVivienda(managed.getIdProyectoVivienda());
        /* Se crea/actualiza el medio de pago asociado al Proyecto */
        if (proyectoDTO.getMedioPago() != null && proyectoDTO.getMedioPago().getTipoMedioDePago() != null) {
            actualizarMedioPagoProyectoVivienda(proyectoDTO.getIdProyectoVivienda(), proyectoDTO.getMedioPago());
        }

        logger.debug("Finaliza el servicio crearActualizarProyectoSolucionVivienda");
        return proyectoDTO;
    }

    /**
     * Actualiza el medio de pago asociado a un proyecto de vivienda.
     *
     * @param idProyectoVivienda
     * @param medioPagoDTO
     */
    @SuppressWarnings("unchecked")
    private void actualizarMedioPagoProyectoVivienda(Long idProyectoVivienda, MedioDePagoModeloDTO medioPagoDTO) {
        /* Identifica los medios de pago asociados a la persona */
        Map<TipoMedioDePagoEnum, MedioDePago> mediosPagoAsociados = new HashMap<>();
        /* Identifica los medios de pago */
        Map<TipoMedioDePagoEnum, MedioPagoProyectoVivienda> mediosPagoProyecto = new HashMap<>();

        /* Se consulta si existen Medios de Pago asociados a la persona. */
        List<Object[]> medioDePagoProyectoList = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PROYECTO)
                .setParameter("idProyectoVivienda", idProyectoVivienda).getResultList();
        if (medioDePagoProyectoList != null && !medioDePagoProyectoList.isEmpty()) {
            for (Object[] mediosPago : medioDePagoProyectoList) {
                MedioDePago medioDePago = (MedioDePago) mediosPago[0];
                MedioPagoProyectoVivienda medioPagoProyecto = (MedioPagoProyectoVivienda) mediosPago[1];
                mediosPagoAsociados.put(medioDePago.getTipoMediopago(), medioDePago);
                mediosPagoProyecto.put(medioDePago.getTipoMediopago(), medioPagoProyecto);
            }
        }
        Boolean crearMedioPago = Boolean.TRUE;
        /* Si tiene Medios de Pago asociados */
        if (!mediosPagoAsociados.isEmpty()) {
            /* Se verifica si existe el tipo de Medio de pago a actualizar */
            if (mediosPagoAsociados.containsKey(medioPagoDTO.getTipoMedioDePago())) {
                /* Actualiza el Medio de Pago */
                //MedioDePago medioDePago = mediosPagoAsociados.get(medioPagoDTO.getTipoMedioDePago());
                MedioDePago medioDePago = medioPagoDTO.convertToMedioDePagoEntity(null);
                //MedioDePago medioDePago = mediosPagoAsociados.get(medioPagoDTO.getTipoMedioDePago());
                medioDePago.setIdMedioPago(mediosPagoAsociados.get(medioPagoDTO.getTipoMedioDePago()).getIdMedioPago());
                medioDePago.setTipoMediopago(medioPagoDTO.getTipoMedioDePago());
                medioDePago = entityManager.merge(medioDePago);
                //medioPagoDTO.convertToMedioDePagoEntity(medioDePago);
                /* Se actualiza a activo el medio de Pago proyecto */
                MedioPagoProyectoVivienda medioPagoProyecto = mediosPagoProyecto.get(medioPagoDTO.getTipoMedioDePago());
                medioPagoProyecto = entityManager.merge(medioPagoProyecto);
                medioPagoProyecto.setActivo(Boolean.TRUE);
                mediosPagoProyecto.remove(medioDePago.getTipoMediopago());
                crearMedioPago = Boolean.FALSE;
            }
        }
        /* Si se debe crear un nuevo Medio de Pago */
        if (crearMedioPago) {
            /* Si no tiene medio de Pago asociado se crea. */
            MedioDePago medioDePago = medioPagoDTO.convertToMedioDePagoEntity(null);
            medioDePago.setTipoMediopago(medioPagoDTO.getTipoMedioDePago());
            entityManager.persist(medioDePago);
            /* Se asocia el medio de pago a la persona */
            MedioPagoProyectoVivienda medioPagoProyecto = new MedioPagoProyectoVivienda();
            medioPagoProyecto.setIdProyectoVivienda(idProyectoVivienda);
            medioPagoProyecto.setIdMedioPago(medioDePago.getIdMedioPago());
            medioPagoProyecto.setActivo(Boolean.TRUE);
            entityManager.persist(medioPagoProyecto);
        }
        /* Si tiene otros medios de Pago asociados se inactivan */
        if (!mediosPagoProyecto.isEmpty()) {
            Collection<MedioPagoProyectoVivienda> mediosInactivar = mediosPagoProyecto.values();
            for (MedioPagoProyectoVivienda medioPagoProyecto : mediosInactivar) {
                medioPagoProyecto = entityManager.merge(medioPagoProyecto);
                medioPagoProyecto.setActivo(Boolean.FALSE);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarProyectosOferente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ProyectoSolucionViviendaModeloDTO> consultarProyectosOferente(TipoIdentificacionEnum tipoIdentificacion,
                                                                              String numeroIdentificacion, Boolean proyectoRegistrado, Boolean proyectosConLicencia) {
        logger.debug(interpolate("Inicia consultarProyectosOferente({0}, {1}, {2}, {3})", tipoIdentificacion, numeroIdentificacion, proyectoRegistrado, proyectosConLicencia));
        List<ProyectoSolucionVivienda> listaProyectoSolucionVivienda = new ArrayList<>();
        if (proyectoRegistrado != null && proyectoRegistrado) {
            listaProyectoSolucionVivienda = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PROYECTO_POR_OFERENTE_REG_NOREG, ProyectoSolucionVivienda.class)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("registrado", proyectoRegistrado).getResultList();
        } else {
            listaProyectoSolucionVivienda = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PROYECTO_POR_OFERENTE, ProyectoSolucionVivienda.class)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getResultList();
        }

        List<ProyectoSolucionViviendaModeloDTO> listaProyectoSolucionViviendaDTO = new ArrayList<ProyectoSolucionViviendaModeloDTO>();
        List<Long> idsProyectoVivienda = new ArrayList<Long>();

        for (ProyectoSolucionVivienda proyectoSolucionVivienda : listaProyectoSolucionVivienda) {
            ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO = new ProyectoSolucionViviendaModeloDTO();
            proyectoSolucionViviendaDTO.convertToDTO(proyectoSolucionVivienda, null, null);
            if (proyectoSolucionVivienda.getIdUbicacionProyecto() != null) {
                UbicacionModeloDTO ubicacionProyectoDTO = consultarUbicacionProyectoVivienda(
                        proyectoSolucionVivienda.getIdUbicacionProyecto());
                proyectoSolucionViviendaDTO.setUbicacionProyecto(ubicacionProyectoDTO);
            }
            List<DocumentoSoporteModeloDTO> documentos = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_SOPORTE_POR_PROYECTO, DocumentoSoporteModeloDTO.class)
                    .setParameter("idProyecto", proyectoSolucionVivienda.getIdProyectoVivienda()).getResultList();
            proyectoSolucionViviendaDTO.setListaDocumentosSoporte(documentos);

            MedioDePagoModeloDTO medioPagoDTO = this
                    .consultarMedioPagoProyectoVivienda(proyectoSolucionVivienda.getIdProyectoVivienda());
            logger.info("medioPagoDTO ----> " + medioPagoDTO.toString());
            proyectoSolucionViviendaDTO.setMedioPago(medioPagoDTO);
            listaProyectoSolucionViviendaDTO.add(proyectoSolucionViviendaDTO);
            idsProyectoVivienda.add(proyectoSolucionVivienda.getIdProyectoVivienda());
        }

        //Se consultan las licencias asociadas a los proyectos de vivienda encontrados
        listaProyectoSolucionViviendaDTO = consultarLicenciaProyectoVivienda(listaProyectoSolucionViviendaDTO, idsProyectoVivienda);

        if (proyectosConLicencia != null && proyectosConLicencia) {
            //Ajuste GLPI 51982 - 54802 - Verificar informacion licencias vigentes
            Calendar fechaActual = Calendar.getInstance();
            Calendar fechaFin = Calendar.getInstance();
            Calendar fechaInicio = Calendar.getInstance();
            List<ProyectoSolucionViviendaModeloDTO> listaProyectoConLicencia = new ArrayList<ProyectoSolucionViviendaModeloDTO>();
            // se verifica si tiene la licencia de construcción y urbanismo vigentes
            for (ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaModeloDTO : listaProyectoSolucionViviendaDTO) {
                boolean construccionVigente = false;
                boolean urbanismoVigente = false;
                if (proyectoSolucionViviendaModeloDTO.getLicencias() == null
                        || proyectoSolucionViviendaModeloDTO.getLicencias().isEmpty()) {
                    continue;
                }
                for (LicenciaModeloDTO licencia : proyectoSolucionViviendaModeloDTO.getLicencias()) {
                    if (licencia.getFechaFinVigenciaLicencia() == null) {
                        fechaFin.setTimeInMillis(fechaActual.getTimeInMillis());
                    } else {
                        fechaFin.setTimeInMillis(licencia.getFechaFinVigenciaLicencia());
                    }

                    if (licencia.getFechaInicioVigenciaLicencia() == null) {
                        fechaInicio.setTimeInMillis(fechaActual.getTimeInMillis());
                    } else {
                        fechaInicio.setTimeInMillis(licencia.getFechaFinVigenciaLicencia());
                    }
                    fechaFin.add(Calendar.DAY_OF_MONTH, 1);

                    if (!(fechaInicio.getTimeInMillis() <= fechaActual.getTimeInMillis()
                            && fechaActual.getTimeInMillis() < fechaFin.getTimeInMillis())
                            && !licenciaVigentePorDetalle(licencia)) {
                        continue;
                    }
                    if (TipoLicenciaEnum.LICENCIA_CONSTRUCCION.equals(licencia.getTipoLincencia())) {
                        construccionVigente = true;
                    } else if (TipoLicenciaEnum.LICENCIA_URBANISMO.equals(licencia.getTipoLincencia())) {
                        urbanismoVigente = true;
                    }
                }
                if (construccionVigente && urbanismoVigente) {
                    listaProyectoConLicencia.add(proyectoSolucionViviendaModeloDTO);
                }
            }
            return listaProyectoConLicencia;
        }

        logger.debug(interpolate("Finaliza consultarProyectosOferente({0}, {1}, {2}, {3})", tipoIdentificacion, numeroIdentificacion, proyectoRegistrado, proyectosConLicencia));
        return listaProyectoSolucionViviendaDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarHistoricoPostulaciones(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PostulacionDTO> consultarHistoricoPostulaciones(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug(interpolate("Inicia consultarHistoricoPostulaciones({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        List<PostulacionDTO> listaPostulaciones = new ArrayList<PostulacionDTO>();
        List<PostulacionDTO> listaPostulacionesJefe = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_POSTULACION_JEFE, PostulacionDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();
        List<PostulacionDTO> listaPostulacionesIntegrantes = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_POSTULACION_INTEGRANTES, PostulacionDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();
        listaPostulaciones.addAll(listaPostulacionesJefe);
        listaPostulaciones.addAll(listaPostulacionesIntegrantes);
        logger.debug(interpolate("Finaliza consultarHistoricoPostulaciones({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        return listaPostulaciones;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * crearActualizarSolicitudPostulacion(com.asopagos.dto.modelo.
     * SolicitudPostulacionModeloDTO)
     */
    @Override
    public SolicitudPostulacionModeloDTO crearActualizarSolicitudPostulacion(SolicitudPostulacionModeloDTO solicitudPostulacionDTO) {
        logger.debug("Inicia el servicio crearActualizarSolicitudPostulacion");
        SolicitudPostulacion solicitudPostulacion = solicitudPostulacionDTO.convertToEntity();
        SolicitudPostulacion managed = entityManager.merge(solicitudPostulacion);
        solicitudPostulacionDTO.convertToDTO(managed);
        logger.debug("Finaliza el servicio crearActualizarSolicitudPostulacion");
        return solicitudPostulacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarPostulacionFOVIS(com.asopagos.dto.modelo.PostulacionFOVISModeloDTO)
     */
    @Override
    public PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Linea 429 Inicia crearActualizarPostulacionFOVIS(" + postulacionFOVISDTO.getIdPostulacion() + ")");
        PostulacionFOVIS postulacionFOVIS = postulacionFOVISDTO.convertToEntity();
        PostulacionFOVIS managed = entityManager.merge(postulacionFOVIS);
        postulacionFOVISDTO.setIdPostulacion(managed.getIdPostulacion());
        logger.debug("Linea 433 Finaliza crearActualizarPostulacionFOVIS(" + postulacionFOVISDTO.getIdPostulacion() + ")");
        return postulacionFOVISDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#crearActualizarAhorroPrevio(com.
     * asopagos.dto.modelo.AhorroPrevioModeloDTO)
     */
    @Override
    public List<AhorroPrevioModeloDTO> crearActualizarAhorroPrevio(List<AhorroPrevioModeloDTO> ahorrosPreviosDTO, Long idPostulacion) {
        logger.debug("Inicia el servicio crearActualizarAhorroPrevio");
        for (AhorroPrevioModeloDTO ahorroPrevioDTO : ahorrosPreviosDTO) {
            Boolean crear = Boolean.FALSE;
            if (ahorroPrevioDTO != null) {
                AhorroPrevio ahorroPrevio = ahorroPrevioDTO.convertToEntity();
                ahorroPrevio.setIdPostulacion(idPostulacion);
                try {
                    BigInteger idAhorroPrevio = (BigInteger) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_AHORRO_PREVIO_NOMBRE_IDPOSTULACION)
                            .setParameter("nombre", ahorroPrevioDTO.getNombreAhorro().name())
                            .setParameter("idPostulacion", idPostulacion).getSingleResult();
                    ahorroPrevio.setIdAhorroPrevio(idAhorroPrevio.longValue());
                    ahorroPrevioDTO.setIdAhorroPrevio(idAhorroPrevio.longValue());
                    ahorroPrevio.setAhorroMovilizado(ahorroPrevioDTO.getAhorroMovilizado());
                    entityManager.merge(ahorroPrevio);
                    logger.debug("Finaliza el servicio crearActualizarAhorroPrevio");
                } catch (Exception e) {
                    logger.debug("Se creará ahorro previo" + ahorroPrevioDTO.getNombreAhorro().name() + "para la postulacion:"
                            + ahorroPrevioDTO.getIdPostulacion());
                    crear = Boolean.TRUE;
                }
                if (crear) {
                    entityManager.persist(ahorroPrevio);
                    ahorroPrevioDTO.setIdAhorroPrevio(ahorroPrevio.getIdAhorroPrevio());
                }
            }
        }
        logger.debug("Finaliza el servicio crearActualizarAhorroPrevio");
        return ahorrosPreviosDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * crearActualizarRecursoComplementario(com.asopagos.dto.modelo.
     * RecursoComplementarioModeloDTO)
     */
    @Override
    public List<RecursoComplementarioModeloDTO> crearActualizarRecursoComplementario(
            List<RecursoComplementarioModeloDTO> recursosComplementariosDTO, Long idPostulacion) {
        logger.debug("Inicia el servicio crearActualizarRecursoComplementario");
        for (RecursoComplementarioModeloDTO recursoComplementarioDTO : recursosComplementariosDTO) {
            Boolean crear = Boolean.FALSE;
            if (recursoComplementarioDTO != null) {
                RecursoComplementario recursoComplementario = recursoComplementarioDTO.convertToEntity();
                recursoComplementario.setPostulacion(idPostulacion);
                try {
                    BigInteger idRecurso = (BigInteger) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECURSO_COMP_NOMBRE_IDPOSTULACION)
                            .setParameter("nombre", recursoComplementarioDTO.getNombre().name())
                            .setParameter("idPostulacion", idPostulacion).getSingleResult();
                    recursoComplementario.setIdRecurso(idRecurso.longValue());
                    recursoComplementarioDTO.setIdRecurso(idRecurso.longValue());
                    entityManager.merge(recursoComplementario);
                    logger.debug("Finaliza el servicio crearActualizarRecursoComplementario");
                } catch (Exception e) {
                    logger.debug("Se creará recurso complementario" + recursoComplementarioDTO.getNombre().name()
                            + "para la postulacion:" + recursoComplementarioDTO.getPostulacion());
                    crear = Boolean.TRUE;
                }
                if (crear) {
                    entityManager.persist(recursoComplementario);
                    recursoComplementarioDTO.setIdRecurso(recursoComplementario.getIdRecurso());
                }
            }
        }
        logger.debug("Finaliza el servicio crearActualizarRecursoComplementario");
        return recursosComplementariosDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarLicencia(com.
     * asopagos.dto.modelo.LicenciaModeloDTO)
     */
    @Override
    public LicenciaModeloDTO crearActualizarLicencia(LicenciaModeloDTO licenciaDTO) {
        logger.debug("Inicia el servicio crearActualizarLicencia");
        Licencia licencia = licenciaDTO.convertToEntity();
        Licencia managed = entityManager.merge(licencia);
        licenciaDTO.setIdLicencia(managed.getIdLicencia());
        logger.debug("Finaliza el servicio crearActualizarLicencia");
        return licenciaDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#crearActualizarLicenciaDetalle(
     * com.asopagos.dto.modelo.LicenciaDetalleModeloDTO)
     */
    @Override
    public LicenciaDetalleModeloDTO crearActualizarLicenciaDetalle(LicenciaDetalleModeloDTO licenciaDetalleDTO) {
        logger.debug("Inicia el servicio crearActualizarLicenciaDetalle");
        LicenciaDetalle licenciaDetalle = licenciaDetalleDTO.convertToEntity();
        LicenciaDetalle managed = entityManager.merge(licenciaDetalle);
        licenciaDetalleDTO.setIdLicenciaDetalle(managed.getIdLicenciaDetalle());
        logger.debug("Finaliza el servicio crearActualizarLicenciaDetalle");
        return licenciaDetalleDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarDatosGeneralesFovis()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionFOVISModeloDTO> consultarDatosGeneralesFovis() {
        logger.debug("Se inicia el servicio de consultarDatosGeneralesFovis()");
        List<ParametrizacionFOVISModeloDTO> parametrizacion = new ArrayList<>();
        List<ParametrizacionFOVIS> parametrizacionesConsultadas = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_GENERAL_FOVIS).getResultList();
        if (parametrizacionesConsultadas != null && !parametrizacionesConsultadas.isEmpty()) {
            for (ParametrizacionFOVIS parametrizacionFOVIS : parametrizacionesConsultadas) {
                ParametrizacionFOVISModeloDTO parametrizacionFOVISModeloDTO = new ParametrizacionFOVISModeloDTO(parametrizacionFOVIS);
                parametrizacion.add(parametrizacionFOVISModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarDatosGeneralesFovis()");
        return parametrizacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarParametrizacionModalidades()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionModalidadModeloDTO> consultarParametrizacionModalidades() {
        logger.debug("Se inicia el servicio de consultarParametrizacionModalidades()");
        List<ParametrizacionModalidadModeloDTO> parametrizacionModalidades = new ArrayList<>();
        List<ParametrizacionModalidad> parametrizacionModalidadesConsultadas = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_MODALIDADES).getResultList();
        if (parametrizacionModalidadesConsultadas != null && !parametrizacionModalidadesConsultadas.isEmpty()) {
            for (ParametrizacionModalidad parametrizacionModalidad : parametrizacionModalidadesConsultadas) {
                ParametrizacionModalidadModeloDTO parametrizacionModalidadModeloDTO = new ParametrizacionModalidadModeloDTO(
                        parametrizacionModalidad);

                // se consultan los rangos para la modalidad
                parametrizacionModalidadModeloDTO
                        .setRangosSVFPorModalidad(consultarRangosSVFPorModalidad(parametrizacionModalidadModeloDTO.getNombre()));

                // se consultan las formas de pago para la modalidad
                parametrizacionModalidadModeloDTO
                        .setFormasDePagoModalidad(consultarFormasDePagoPorModalidad(parametrizacionModalidadModeloDTO.getNombre()));

                parametrizacionModalidades.add(parametrizacionModalidadModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarParametrizacionModalidades()");
        return parametrizacionModalidades;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarRangosSVFPorModalidad(com.asopagos.enumeraciones.fovis.ModalidadEnum)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<RangoTopeValorSFVModeloDTO> consultarRangosSVFPorModalidad(ModalidadEnum modalidad) {
        logger.debug("Se inicia el servicio de consultarRangosSVFPorModalidad(ModalidadEnum)");
        List<RangoTopeValorSFVModeloDTO> rangosTopeValorSFVModeloDTO = new ArrayList<>();
        List<RangoTopeValorSFV> rangosTopeValorSFV = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_RANGOS_SVF_POR_MODALIDAD).setParameter("modalidad", modalidad)
                .getResultList();
        if (rangosTopeValorSFV != null && !rangosTopeValorSFV.isEmpty()) {
            for (RangoTopeValorSFV cicloAsignacion : rangosTopeValorSFV) {
                RangoTopeValorSFVModeloDTO rangoTopeValorSFVModeloDTO = new RangoTopeValorSFVModeloDTO(cicloAsignacion);
                rangosTopeValorSFVModeloDTO.add(rangoTopeValorSFVModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarRangosSVFPorModalidad(ModalidadEnum)");
        return rangosTopeValorSFVModeloDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarFormasDePagoPorModalidad(com.asopagos.enumeraciones.fovis.ModalidadEnum)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<FormaPagoModalidadModeloDTO> consultarFormasDePagoPorModalidad(ModalidadEnum modalidad) {
        logger.debug("Se inicia el servicio de consultarFormasDePagoPorModalidad(ModalidadEnum)");
        List<FormaPagoModalidadModeloDTO> formasPagoModalidadDTO = new ArrayList<>();
        List<FormaPagoModalidad> formasPagoModalidad = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_FORMAS_DE_PAGO_POR_MODALIDAD).setParameter("modalidad", modalidad)
                .getResultList();
        if (formasPagoModalidad != null && !formasPagoModalidad.isEmpty()) {
            for (FormaPagoModalidad formaPagoModalidad : formasPagoModalidad) {
                FormaPagoModalidadModeloDTO formaPagoModalidadModelDTO = new FormaPagoModalidadModeloDTO(formaPagoModalidad);
                formasPagoModalidadDTO.add(formaPagoModalidadModelDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarFormasDePagoPorModalidad(ModalidadEnum)");
        return formasPagoModalidadDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarMediosDePago()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionMedioPagoDTO> consultarMediosDePago() {
        logger.debug("Se inicia el servicio de consultarMediosDePago()");
        List<ParametrizacionMedioPagoDTO> parametrizacionMediosDePago = new ArrayList<>();
        List<MediosPagoCCF> mediosDePagoFovis = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOS_DE_PAGO_FOVIS)
                .getResultList();
        if (mediosDePagoFovis != null && !mediosDePagoFovis.isEmpty()) {
            for (MediosPagoCCF medioDePago : mediosDePagoFovis) {
                ParametrizacionMedioPagoDTO parametrizacionMedioPagoDTO = new ParametrizacionMedioPagoDTO(medioDePago);
                parametrizacionMediosDePago.add(parametrizacionMedioPagoDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarMediosDePago()");
        return parametrizacionMediosDePago;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCiclosAsignacionPorAnio()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorAnio(Short anio) {
        logger.debug("Se inicia el servicio de consultarCiclosAsignacionPorAnio(Short)");
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<>();
        List<CicloAsignacion> ciclosAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_POR_ANIO).setParameter("anio", new Integer(anio))
                .getResultList();
        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            for (CicloAsignacion cicloAsignacion : ciclosAsignacion) {
                CicloAsignacionModeloDTO cicloAsignacionModelDTO = new CicloAsignacionModeloDTO(cicloAsignacion);

                // se consultan las modalidades habilitadas para el ciclo
                cicloAsignacionModelDTO
                        .setModalidadesCiclo(consultarModalidadesPorCiclo(cicloAsignacionModelDTO.getIdCicloAsignacion(), null));

                // Se verifica si el ciclo tiene postulaciones para permitir
                // o no eliminarlo en pantalla
                cicloAsignacionModelDTO
                        .setPermiteEliminar(!existenPostulacionesPorCiclo(cicloAsignacionModelDTO.getIdCicloAsignacion()));

                // se asocia el ciclo sucesor
                CicloAsignacionModeloDTO cicloSucesor = consultarCicloSucesor(cicloAsignacion.getIdCicloAsignacion(),
                        cicloAsignacion.getFechaFin().getTime(), cicloAsignacion.getCicloPredecesor());
                if (cicloSucesor != null) {
                    cicloAsignacionModelDTO.setIdCicloSucesor(cicloSucesor != null ? cicloSucesor.getIdCicloAsignacion() : null);
                    cicloAsignacionModelDTO.setCicloSucesor(cicloSucesor != null ? cicloSucesor.getNombre() : null);
                }

                if (!EstadoCicloAsignacionEnum.CERRADO.equals(cicloAsignacion.getEstadoCicloAsignacion())) {

                    //TODO Se calcula temporalmente el estado (debe ser una ejecución programada)
                    Calendar fechaActual = CalendarUtils.formatearFechaSinHora(Calendar.getInstance());
                    Calendar fechaInicio = Calendar.getInstance();
                    fechaInicio.setTime(cicloAsignacion.getFechaInicio());
                    Calendar fechaFin = Calendar.getInstance();
                    fechaFin.setTime(cicloAsignacion.getFechaFin());
                    if (fechaActual.before(fechaInicio)) {
                        cicloAsignacionModelDTO.setEstadoCicloAsignacion(EstadoCicloAsignacionEnum.CICLO_NO_INICIADO);
                    } else if (fechaActual.after(fechaFin)) {
                        if (!EstadoCicloAsignacionEnum.CERRADO.equals(cicloAsignacion.getEstadoCicloAsignacion())) {
                            cicloAsignacionModelDTO.setEstadoCicloAsignacion(EstadoCicloAsignacionEnum.PENDIENTE_FINALIZAR_ASIGNACION);
                        }
                    } else {
                        cicloAsignacionModelDTO.setEstadoCicloAsignacion(EstadoCicloAsignacionEnum.VIGENTE);
                    }
                }

                ciclosAsignacionDTO.add(cicloAsignacionModelDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarCiclosAsignacionPorAnio(Short)");
        return ciclosAsignacionDTO;
    }

    /**
     * Método que verifica si existen postulaciones registradas para el ciclo de
     * asignación.
     *
     * @param idCicloAsignacion Identificador del ciclo de asignación.
     * @return La variable que indica si existen o no postulaciones.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Boolean existenPostulacionesPorCiclo(Long idCicloAsignacion) {
        logger.debug("Se inicia el método de existenPostulacionesPorCiclo(Long idCicloAsignacion)");
        Boolean existenPstulaciones = Boolean.FALSE;
        Integer rolesActivos = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CANTIDAD_POSTULACIONES_POR_CICLO)
                .setParameter("idCicloAsignacion", idCicloAsignacion).getSingleResult();
        if (rolesActivos > 0) {
            existenPstulaciones = Boolean.TRUE;
        }
        logger.debug("Finaliza el método de existenPostulacionesPorCiclo(Long idCicloAsignacion)");
        return existenPstulaciones;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarModalidadesPorCiclo(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CicloModalidadModeloDTO> consultarModalidadesPorCiclo(Long idCicloAsignacion, Boolean habilitadas) {
        logger.debug("Se inicia el servicio de consultarModalidadesPorCiclo(Long idCicloAsignacion)");
        List<CicloModalidadModeloDTO> ciclosModalidad;
        if (habilitadas != null) {
            ciclosModalidad = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_MODALIDADES_POR_CICLO_HABILITADAS)
                    .setParameter("idCicloAsignacion", idCicloAsignacion).setParameter("habilitada", habilitadas).getResultList();
        } else {
            ciclosModalidad = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_MODALIDADES_POR_CICLO)
                    .setParameter("idCicloAsignacion", idCicloAsignacion).getResultList();
        }
        logger.debug("Finaliza el servicio de consultarModalidadesPorCiclo(Long idCicloAsignacion)");
        return ciclosModalidad;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCiclosAsignacionPorModalidad(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorModalidad(ModalidadEnum modalidad) {
        String servicio = "consultarCiclosAsignacionPorModalidad(ModalidadEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + servicio);
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<>();
        List<CicloAsignacion> ciclosAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_MODALIDAD, CicloAsignacion.class)
                .setParameter("modalidad", modalidad.name())
                .getResultList();

        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            for (CicloAsignacion ciclo : ciclosAsignacion) {
                CicloAsignacionModeloDTO cicloDTO = new CicloAsignacionModeloDTO();
                cicloDTO.copyEntityToDTO(ciclo);
                ciclosAsignacionDTO.add(cicloDTO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + servicio);
        return ciclosAsignacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarDatosGeneralesFovis(com.asopagos.dto.modelo.ParametrizacionFOVISModeloDTO)
     */
    @SuppressWarnings("unchecked")
    public void actualizarDatosGeneralesFovis(List<ParametrizacionFOVISModeloDTO> parametrizacionFOVIS) {
        logger.debug("Se inicia el servicio de actualizarDatosGeneralesFovis(List<ParametrizacionFOVISModeloDTO>)");
        for (ParametrizacionFOVISModeloDTO parametrizacionFOVISModeloDTO : parametrizacionFOVIS) {

            List<ParametrizacionFOVIS> parametrizacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_FOVIS_POR_NOMBRE)
                    .setParameter("nombre", parametrizacionFOVISModeloDTO.getParametro()).getResultList();

            ParametrizacionFOVIS parametrizacionFOVISEntity = parametrizacionFOVISModeloDTO.convertToEntity();
            if (parametrizacion != null && !parametrizacion.isEmpty()) {
                parametrizacionFOVISEntity.setIdParametrizacion(parametrizacion.get(0).getIdParametrizacion());
                entityManager.merge(parametrizacionFOVISEntity);
            } else {
                entityManager.persist(parametrizacionFOVISEntity);
            }
        }
        logger.debug("Finaliza el servicio de actualizarDatosGeneralesFovis(List<ParametrizacionFOVISModeloDTO>)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarModalidades(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void actualizarModalidades(List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS) {
        logger.debug("Se inicia el servicio de actualizarModalidades(List<ModalidadFOVISModeloDTO>)");
        for (ParametrizacionModalidadModeloDTO modalidadFOVISDTO : modalidadesFOVIS) {

            // registrar la modalidad
            List<ParametrizacionModalidad> parametrizacionResult = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_POR_MODALIDAD)
                    .setParameter("modalidad", modalidadFOVISDTO.getNombre()).getResultList();

            ParametrizacionModalidad modalidadFOVIS = modalidadFOVISDTO.convertToEntity();
            if (parametrizacionResult != null && !parametrizacionResult.isEmpty()) {
                modalidadFOVIS.setIdParametrizacionModalidad(parametrizacionResult.get(0).getIdParametrizacionModalidad());
                entityManager.merge(modalidadFOVIS);
            } else {
                entityManager.persist(modalidadFOVIS);
            }

            // se registran los rangos asociados a la modalidad
            registrarRangosSVFPorModalidad(modalidadFOVISDTO);

            // se registran las formas de pago de la modalidad
            registrarFormasDePagoPorModalidad(modalidadFOVISDTO);

        }
        logger.debug("Finaliza el servicio de actualizarModalidades(List<ModalidadFOVISModeloDTO>)");
    }

    /**
     * Método que registra los muevos rangos SVF establecidos para la modalidad.
     *
     * @param modalidadFOVISDTO DTO que contiene la lista de los rangos a registrar
     */
    private void registrarRangosSVFPorModalidad(ParametrizacionModalidadModeloDTO modalidadFOVISDTO) {
        logger.debug("Se inicia el método de registrarRangosSVFPorModalidad(ParametrizacionModalidadModeloDTO)");
        // Se eliminan los rangos establecidos anteriormente para la
        // modalidad
        entityManager.createNamedQuery(NamedQueriesConstants.REMOVER_RANGOS_SVF_POR_MODALIDAD)
                .setParameter("modalidad", modalidadFOVISDTO.getNombre()).executeUpdate();

        // Se registran los nuevos rangos
        if (modalidadFOVISDTO.getRangosSVFPorModalidad() != null && !modalidadFOVISDTO.getRangosSVFPorModalidad().isEmpty()) {
            for (RangoTopeValorSFVModeloDTO rangoSFVModalidadDTO : modalidadFOVISDTO.getRangosSVFPorModalidad()) {

                rangoSFVModalidadDTO.setIdParametrizacionModalidad(modalidadFOVISDTO.getNombre());
                RangoTopeValorSFV rangoTopeValorSFV = rangoSFVModalidadDTO.convertToEntity();
                if (rangoTopeValorSFV.getIdRangoTopeValorSFV() != null) {
                    entityManager.merge(rangoTopeValorSFV);
                } else {
                    entityManager.persist(rangoTopeValorSFV);
                }
            }
        }
        logger.debug("Finaliza el método de registrarRangosSVFPorModalidad(ParametrizacionModalidadModeloDTO)");
    }

    /**
     * Método que registra las formas de pago habilitadas para una determinada
     * modalidad.
     *
     * @param modalidadFOVISDTO DTO que contirne la lista de las formas de pago a registrar.
     */
    private void registrarFormasDePagoPorModalidad(ParametrizacionModalidadModeloDTO modalidadFOVISDTO) {
        logger.debug("Se inicia el método de registrarFormasDePagoPorModalidad(ParametrizacionModalidadModeloDTO)");
        // Se eliminan las formas de pago registradas para la modalidad
        entityManager.createNamedQuery(NamedQueriesConstants.REMOVER_FORMAS_DE_PAGO_POR_MODALIDAD)
                .setParameter("modalidad", modalidadFOVISDTO.getNombre()).executeUpdate();

        // Se registran las nuevas formas de pago asociadas
        if (modalidadFOVISDTO.getFormasDePagoModalidad() != null && !modalidadFOVISDTO.getFormasDePagoModalidad().isEmpty()) {
            for (FormaPagoModalidadModeloDTO formaPagoModalidadModelDTO : modalidadFOVISDTO.getFormasDePagoModalidad()) {

                formaPagoModalidadModelDTO.setIdParametrizacionModalidad(modalidadFOVISDTO.getNombre());
                FormaPagoModalidad formaPagoModalidad = formaPagoModalidadModelDTO.convertToEntity();
                if (formaPagoModalidad.getIdFormaPagoModalidad() != null) {
                    entityManager.merge(formaPagoModalidad);
                } else {
                    entityManager.persist(formaPagoModalidad);
                }
            }
        }
        logger.debug("Finaliza el método de registrarFormasDePagoPorModalidad(ParametrizacionModalidadModeloDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarMediosDePago(java.util.List)
     */
    public void actualizarMediosDePago(List<ParametrizacionMedioPagoDTO> parametrizacionesMedioPagoDTO) {
        logger.debug("Se inicia el servicio de actualizarMediosDePago(List<ParametrizacionMedioPagoDTO>)");
        for (ParametrizacionMedioPagoDTO parametrizacionMedioPagoDTO : parametrizacionesMedioPagoDTO) {

            MediosPagoCCF medioDePago = (MediosPagoCCF) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_MEDIO_PAGO_CCF_POR_ID)
                    .setParameter("medioPago", parametrizacionMedioPagoDTO.getMedioPago()).getSingleResult();
            // se actualizan el medios de pago
            medioDePago.setAplicaFOVIS(parametrizacionMedioPagoDTO.getHabilitado());
            entityManager.merge(medioDePago);
        }
        logger.debug("Finaliza el servicio de actualizarMediosDePago(List<ParametrizacionMedioPagoDTO>)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#registrarCicloAsignacion(com.asopagos.dto.modelo.CicloAsignacionModeloDTO)
     */
    @SuppressWarnings("unchecked")
    public CicloAsignacionModeloDTO registrarCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModelDTO) {
        logger.debug("Se inicia el servicio de registrarCiclosAsignacion(CicloAsignacionModeloDTO)");
        // Se verifica que no haya un ciclo activo con el mismo ciclo
        // predecesor
        if (cicloAsignacionModelDTO.getCicloPredecesor() != null && cicloAsignacionModelDTO.getCicloActivo() != null
                && cicloAsignacionModelDTO.getCicloActivo()) {
            List<CicloAsignacion> ciclosAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_ASIGNACION_POR_CICLO_PREDECESOR)
                    .setParameter("idCicloPredecesor", cicloAsignacionModelDTO.getCicloPredecesor()).getResultList();
            if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()
                    && !cicloAsignacionModelDTO.getIdCicloAsignacion().equals(ciclosAsignacion.get(0).getIdCicloAsignacion())) {
                throw new FunctionalConstraintException(MensajesConstants.ERROR_CICLO_PRECECESOR_ASIGNADO);
            }
        }
        // se registra el ciclo de asignación
        CicloAsignacion cicloAsignacion = cicloAsignacionModelDTO.convertToEntity();
        if (cicloAsignacion.getIdCicloAsignacion() != null) {
            cicloAsignacion = entityManager.merge(cicloAsignacion);
        } else {
            entityManager.persist(cicloAsignacion);
        }
        cicloAsignacionModelDTO.copyEntityToDTO(cicloAsignacion);
        logger.debug("Finaliza el servicio de registrarCiclosAsignacion(CicloAsignacionModeloDTO)");
        return cicloAsignacionModelDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#registrarModalidadesCicloAsignacion(com.asopagos.dto.modelo.CicloAsignacionModeloDTO)
     */
    public void registrarModalidadesCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModelDTO) {
        logger.debug("Se inicia el servicio de registrarModalidadesHabilitadasParaElCicloAsignacion(CicloAsignacionModelDTO)");
        // se eliminan los registros de modalidades habilitadas para el
        // ciclo
        entityManager.createNamedQuery(NamedQueriesConstants.REMOVER_MODALIDADES_POR_CICLO_ASIGNACION)
                .setParameter("idCicloAsignacion", cicloAsignacionModelDTO.getIdCicloAsignacion()).executeUpdate();

        // se agregan los registros que llegan
        if (cicloAsignacionModelDTO.getModalidadesCiclo() != null && !cicloAsignacionModelDTO.getModalidadesCiclo().isEmpty()) {
            for (CicloModalidadModeloDTO cicloModalidadModelDTO : cicloAsignacionModelDTO.getModalidadesCiclo()) {

                cicloModalidadModelDTO.setIdCicloModalidad(null);
                cicloModalidadModelDTO.setCicloAsignacion(cicloAsignacionModelDTO);
                CicloModalidad cicloModalidad = cicloModalidadModelDTO.convertToEntity();
                entityManager.persist(cicloModalidad);
            }
        }
        logger.debug("Finaliza el servicio de registrarModalidadesHabilitadasParaElCicloAsignacion(CicloAsignacionModelDTO)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * crearListaCondicionEspecialPersona(java.lang.Long, java.util.List, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void crearListaCondicionEspecialPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                   List<NombreCondicionEspecialEnum> listaCondicionEspecial, Long idPostulacion) {
        logger.debug("Inicia el servicio crearListaCondicionEspecialPersona");

        if (!listaCondicionEspecial.isEmpty()) {
            Long idPersona = null;
            try {
                BigInteger datoIdPersona = (BigInteger) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_IDPERSONA_TIPO_NUMERO)
                        .setParameter("numeroIdentificacion", numeroIdentificacion)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name()).getSingleResult();
                idPersona = datoIdPersona.longValue();
            } catch (NoResultException e) {
                return;
            }

            List<CondicionEspecialPersona> condicionesActuales = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_PERSONA).setParameter("idPersona", idPersona)
                    .getResultList();

            if (condicionesActuales != null && !condicionesActuales.isEmpty()) {
                List<NombreCondicionEspecialEnum> condicionesExistentes = new ArrayList<>();
                for (CondicionEspecialPersona condicionEspecialPersona : condicionesActuales) {
                    condicionesExistentes.add(condicionEspecialPersona.getNombreCondicion());
                    entityManager.merge(condicionEspecialPersona);
                    /* Se INACTIVAN las condiciones asociadas a la persona */
                    if (!listaCondicionEspecial.contains(condicionEspecialPersona.getNombreCondicion())) {
                        condicionEspecialPersona.setActiva(Boolean.FALSE);
                        /* Se ACTIVAN las condiciones asociadas a la persona que ya existen */
                    } else if (listaCondicionEspecial.contains(condicionEspecialPersona.getNombreCondicion())) {
                        condicionEspecialPersona.setActiva(Boolean.TRUE);
                    }
                }

                /* Se crean las condiciones que no existen */
                for (NombreCondicionEspecialEnum condicionCrear : listaCondicionEspecial) {
                    if (!condicionesExistentes.contains(condicionCrear)) {
                        CondicionEspecialPersona condicionNueva = new CondicionEspecialPersona();
                        condicionNueva.setIdPersona(idPersona);
                        condicionNueva.setNombreCondicion(condicionCrear);
                        condicionNueva.setActiva(Boolean.TRUE);
                        condicionNueva.setIdPostulacionFOVIS(idPostulacion);
                        entityManager.persist(condicionNueva);
                    }
                }
            } else {
                for (NombreCondicionEspecialEnum condicionCrear : listaCondicionEspecial) {
                    CondicionEspecialPersona condicionNueva = new CondicionEspecialPersona();
                    condicionNueva.setIdPersona(idPersona);
                    condicionNueva.setNombreCondicion(condicionCrear);
                    condicionNueva.setActiva(Boolean.TRUE);
                    condicionNueva.setIdPostulacionFOVIS(idPostulacion);
                    entityManager.persist(condicionNueva);
                }
            }
        }
        logger.debug("Finaliza el servicio crearListaCondicionEspecialPersona");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudPostulacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacion(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarSolicitudPostulacion(Long)");
        try {
            SolicitudPostulacion sol = (SolicitudPostulacion) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
            SolicitudPostulacionModeloDTO solicitudDTO = new SolicitudPostulacionModeloDTO();
            solicitudDTO.convertToDTO(sol);
            logger.debug("Finaliza consultarSolicitudPostulacion(Long)");
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudPostulacion(Long)"
                    + interpolate("No se encontraron resultados con el id de solicitud {0} ingresada.", idSolicitudGlobal));
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarEstadoSolicitudPostulacion(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum)
     */
    public void actualizarEstadoSolicitudPostulacion(Long idSolicitudGlobal, EstadoSolicitudPostulacionEnum estadoSolicitud) {
        logger.debug("Se inicia el servicio de actualizarEstadoSolicitudPostulacion(Long, EstadoSolicitudPostulacionEnum)");
        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(idSolicitudGlobal);
        SolicitudPostulacion solicitudPostulacion = solicitudPostulacionDTO.convertToEntity();

        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA.equals(estadoSolicitud)) {
            if (EstadoSolicitudPostulacionEnum.HALLAZGOS_SUBSANADOS.equals(solicitudPostulacion.getEstadoSolicitud())
                    || EstadoSolicitudPostulacionEnum.POSTULACION_HABIL.equals(solicitudPostulacion.getEstadoSolicitud())
                    || EstadoSolicitudPostulacionEnum.SIN_HALLAZGOS.equals(solicitudPostulacion.getEstadoSolicitud())) {
                solicitudPostulacion.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
            } else if (EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA.equals(solicitudPostulacion.getEstadoSolicitud())) {
                solicitudPostulacion.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
            } else if (EstadoSolicitudPostulacionEnum.DESISTIDA.equals(solicitudPostulacion.getEstadoSolicitud())
                    || EstadoSolicitudPostulacionEnum.CANCELADA.equals(solicitudPostulacion.getEstadoSolicitud())) {
                solicitudPostulacion.getSolicitudGlobal()
                        .setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudPostulacion.getEstadoSolicitud().name()));
            }
        }
        solicitudPostulacion.setEstadoSolicitud(estadoSolicitud);
        entityManager.merge(solicitudPostulacion);
        logger.debug("Finaliza actualizarEstadoSolicitudPostulacion(Long, EstadoSolicitudPostulacionEnum)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#existenEscalamientosSinResultado(java.lang.Long)
     */
    public Boolean existenEscalamientosSinResultado(Long idSolicitudGlobal) {
        logger.debug("Se inicia el método de existenEscalamientosSinResultado(Long idSolicitud)");
        Boolean existenEscalamientos = Boolean.FALSE;
        Integer cantidadEscalamientosSinResultado = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CANTIDAD_ESCALAMIENTOS_SIN_RESULTADO_POR_SOLICITUD)
                .setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
        if (cantidadEscalamientosSinResultado > 0) {
            existenEscalamientos = Boolean.TRUE;
        }
        logger.debug("Finaliza el método de existenEscalamientosSinResultado(Long idSolicitud)");
        return existenEscalamientos;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCiclosNoPredecesores(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CicloAsignacionModeloDTO> consultarCiclosNoPredecesores(Long idCicloAsignacion, Long idCicloPredecesor) {
        logger.debug("Se inicia el servicio de consultarCiclosNoPredecesores(Long,Long)");
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<>();
        List<Long> ciclosAOmitir = new ArrayList<>();
        if (idCicloAsignacion != null) {
            ciclosAOmitir.add(idCicloAsignacion);
        }
        List<CicloAsignacion> ciclosAsignacion;
        if (ciclosAOmitir.isEmpty()) {
            ciclosAsignacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES)
                    .getResultList();
        } else if (idCicloPredecesor != null) {
            ciclosAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES_CON_PREDECESOR)
                    .setParameter("idCiclosAsignacion", ciclosAOmitir).setParameter("idCicloPredecesor", idCicloPredecesor)
                    .getResultList();
        } else {
            ciclosAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES_CON_EXCEPCION)
                    .setParameter("idCiclosAsignacion", ciclosAOmitir).getResultList();
        }
        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            for (CicloAsignacion cicloAsignacion : ciclosAsignacion) {
                CicloAsignacionModeloDTO cicloAsignacionModelDTO = new CicloAsignacionModeloDTO(cicloAsignacion);
                ciclosAsignacionDTO.add(cicloAsignacionModelDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarCiclosNoPredecesores(Long,Long)");
        return ciclosAsignacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulacionFOVIS(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PostulacionFOVISModeloDTO consultarPostulacionFOVIS(Long idPostulacion) {
        logger.debug("Se inicia el servicio de consultarPostulacionFOVIS(Long idPostulacion)");
        PostulacionFOVIS postulacionFovis = (PostulacionFOVIS) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACION_FOVIS_POR_ID).setParameter("idPostulacion", idPostulacion)
                .getSingleResult();
        PostulacionFOVISModeloDTO postulacionDTO = new PostulacionFOVISModeloDTO();
        postulacionDTO.convertToDTO(postulacionFovis);
        logger.debug("Finaliza el servicio de consultarPostulacionFOVIS(Long idPostulacion)");
        return postulacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPersonasDetallePorPostulacion(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<PersonaDetalleModeloDTO> consultarPersonasDetallePorPostulacion(Long idPostulacion) {
        logger.debug("Se inicia el servicio de consultarPersonasDetallePorPostulacion(Long idPostulacion)");
        List<PersonaDetalleModeloDTO> personasDetalleDTO = new ArrayList<>();
        List<PersonaDetalle> personasDetalle = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE_POR_POSTULACION)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        if (personasDetalle != null && !personasDetalle.isEmpty()) {
            for (PersonaDetalle personaDetalle : personasDetalle) {
                PersonaDetalleModeloDTO personaDetalleModeloDTO = new PersonaDetalleModeloDTO(personaDetalle);
                personasDetalleDTO.add(personaDetalleModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarPersonasDetallePorPostulacion(Long idPostulacion)");
        return personasDetalleDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#registrarIntentoPostulacionFOVIS(
     * com.asopagos.fovis.dto.IntentoPostulacionDTO)
     */
    @Override
    public Long registrarIntentoPostulacionFOVIS(IntentoPostulacionDTO intentoPostulacionDTO) {
        logger.debug("Se inicia el servicio de registrarIntentoPostulacion(intentoPostulacionDTO)");
        IntentoPostulacion intentoPostulacion = intentoPostulacionDTO.convertToIntentoPostulacionEntity();
        entityManager.persist(intentoPostulacion);
        logger.debug("Finaliza el servicio de registrarIntentoPostulacion(intentoPostulacionDTO)");
        return intentoPostulacion.getIdIntentoPostulacion();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#consultarSolicitudPorRadicado(
     * java.lang.String)
     */
    @Override
    public SolicitudModeloDTO consultarSolicitudPorRadicado(String numeroRadicado) {
        logger.debug("Se inicia el servicio de consultarSolicitudPorRadicado");
        Solicitud solicitud = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD, Solicitud.class)
                .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
        SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
        solicitudDTO.convertToDTO(solicitud);
        logger.debug("Finaliza el servicio de consultarSolicitudPorRadicado");
        return solicitudDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#consultarSolicitud(java.lang.
     * String)
     */
    @Override
    public DepartamentoModeloDTO consultarDepartamentoExcepcionFOVIS() {
        logger.debug("Se inicia el servicio de consultarDepartamentoExcepcionFOVIS");
        Departamento departamento = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTO_EXCEPCION_FOVIS, Departamento.class).getSingleResult();
        DepartamentoModeloDTO departamentoDTO = new DepartamentoModeloDTO();
        departamentoDTO.convertToDTO(departamento);
        logger.debug("Finaliza el servicio de consultarDepartamentoExcepcionFOVIS");
        return departamentoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCiclosAsignacionPorEstado(
     *com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum)
     */
    @SuppressWarnings("unchecked")
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum estadoCicloAsignacion) {
        logger.debug("Se inicia el servicio de consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum)");
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<>();
        List<CicloAsignacion> ciclosAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_POR_ESTADO)
                .setParameter("estadoCicloAsignacion", estadoCicloAsignacion).getResultList();
        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            for (CicloAsignacion cicloAsignacion : ciclosAsignacion) {
                CicloAsignacionModeloDTO cicloAsignacionModelDTO = new CicloAsignacionModeloDTO(cicloAsignacion);
                ciclosAsignacionDTO.add(cicloAsignacionModelDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum)");
        return ciclosAsignacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#obtenerPostulacionesParaControlInterno(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<SolicitudPostulacionModeloDTO> obtenerPostulacionesParaControlInterno(Long idCicloAsignacion) {
        logger.debug("Se inicia el servicio de obtenerPostulacionesParaControlInterno(Long idCicloAsignacion)");
        List<String> estados = new ArrayList<>();
        estados.add(EstadoHogarEnum.HABIL.name());
        estados.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        List<SolicitudPostulacionModeloDTO> postulacionesDTO = new ArrayList<>();
        List<Object[]> postulaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACIONES_PARA_CONTROL_INTERNO)
                .setParameter("idCicloAsignacion", idCicloAsignacion)
                .setParameter("estadoHogar", EstadoHogarEnum.POSTULADO.name())
                .setParameter("estadoPostulacion", EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO.name())
                .setParameter("estadosHogar", estados)
                .setParameter("estadoSolicitudVerificacion", EstadoSolicitudVerificacionFovisEnum.ASIGNADA_A_CONTROL_INTERNO.name())
                .getResultList();
        if (postulaciones != null && !postulaciones.isEmpty()) {
            for (Object[] datosPostulacion : postulaciones) {
                SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = new SolicitudPostulacionModeloDTO();
                PostulacionFOVISModeloDTO postulacionFovisModeloDTO = new PostulacionFOVISModeloDTO();
                postulacionFovisModeloDTO.convertToDTO((PostulacionFOVIS) datosPostulacion[0]);
                solicitudPostulacionModeloDTO.convertToDTO((SolicitudPostulacion) datosPostulacion[1]);
                /* Se asignan los datos del Jefe del Hogar */
                Afiliado afiliado = (Afiliado) datosPostulacion[2];
                JefeHogarModeloDTO jefeHogarModeloDTO = new JefeHogarModeloDTO();
                jefeHogarModeloDTO.convertToJefeHogarDTO((JefeHogar) datosPostulacion[4], afiliado.getPersona(),
                        (PersonaDetalle) datosPostulacion[3]);
                postulacionFovisModeloDTO.setJefeHogar(jefeHogarModeloDTO);
                solicitudPostulacionModeloDTO.setNumeroRadicacion((String) datosPostulacion[5]);
                solicitudPostulacionModeloDTO
                        .setFechaRadicacion(datosPostulacion[6] != null ? ((Date) datosPostulacion[6]).getTime() : null);
                solicitudPostulacionModeloDTO.setPostulacionFOVISModeloDTO(postulacionFovisModeloDTO);
                postulacionesDTO.add(solicitudPostulacionModeloDTO);

            }
        }
        logger.debug("Finaliza el servicio de obtenerPostulacionesParaControlInterno(Long idCicloAsignacion)");
        return postulacionesDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#registrarDocumentoSolicitud(
     *com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO)
     */
    public void registrarDocumentoSolicitud(DocumentoAdministracionEstadoSolicitudDTO docAdminEstadoSolicitudDTO) {
        logger.debug("Se inicia el servicio de guardarDocumentoSolicitud(DocumentoAdministracionEstadoSolicitudDTO)");
        if (docAdminEstadoSolicitudDTO.getIdentificadorDocumentoSoporteCambioEstado() != null
                && docAdminEstadoSolicitudDTO.getIdSolicitudGlobal() != null) {
            entityManager.persist(docAdminEstadoSolicitudDTO.convertToEntity(docAdminEstadoSolicitudDTO));
        }
        logger.debug("Finaliza el servicio de guardarDocumentoSolicitud(DocumentoAdministracionEstadoSolicitudDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarIntegrantesHogar(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public List<IntegranteHogarModeloDTO> consultarIntegrantesHogar(Long idPostulacion) {
        logger.debug("Se inicia el servicio de consultarIntegrantesHogar(Long idPostulacion)");
        List<IntegranteHogarModeloDTO> integrantesHogarDTO = new ArrayList<>();
        List<IntegranteHogar> integrantesHogar = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTES_HOGAR_POR_POSTULACION)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        if (integrantesHogar != null && !integrantesHogar.isEmpty()) {
            for (IntegranteHogar integranteHogar : integrantesHogar) {
                IntegranteHogarModeloDTO personaDetalleModeloDTO = new IntegranteHogarModeloDTO();
                personaDetalleModeloDTO.convertToDTO(integranteHogar);
                integrantesHogarDTO.add(personaDetalleModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarIntegrantesHogar(Long idPostulacion)");
        return integrantesHogarDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulantes()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<PostulanteDTO> consultarPostulantes(String idCicloAsignacion) {
        logger.debug("DATA" + idCicloAsignacion);
        logger.debug("Se inicia el servicio de consultarPostulantes()");
        List<String> estadosHogarSinRestriccion = new ArrayList<>();
        List<String> estadosHogarRestringidos = new ArrayList<>();
        List<String> tiposDocumento = new ArrayList<>();

        estadosHogarRestringidos.add(EstadoHogarEnum.POSTULADO.name());
        estadosHogarSinRestriccion.add(EstadoHogarEnum.HABIL.name());
        estadosHogarSinRestriccion.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        List<String> estadosSolicitudPostulacion = new ArrayList<String>();
        EstadoFOVISHogarEnum estadoFOVIShogar = EstadoFOVISHogarEnum.ACTIVO;

        tiposDocumento.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD.name());
        tiposDocumento.add(TipoIdentificacionEnum.REGISTRO_CIVIL.name());

        //String idCicloAsignacion = "10489";

        /*TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
         */
        /*
         * Se retiran de la lista los enumerados de las enums que, por regla
         * de negocio, no deben ser incluidos en la consulta.
         */
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.ASIGNADA_A_CONTROL_INTERNO.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.NO_CONFORME_SUBSANABLE.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.NO_CONFORME_EN_GESTION.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.NO_CONFORME_GESTIONADA.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.SIN_HALLAZGOS.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.HALLAZGOS_POR_GESTIONAR.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.HALLAZGOS_GESTIONADOS.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.HALLAZGOS_SUBSANADOS.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_HABIL.name());

        List<Object[]> postulantes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULANTES)
                .setParameter("estadosHogarSinRestriccion", estadosHogarSinRestriccion)
                .setParameter("estadosSolicitudPostulacion", estadosSolicitudPostulacion)
                .setParameter("estadosHogarRestringidos", estadosHogarRestringidos)
                .setParameter("estadoFOVIShogar", estadoFOVIShogar.name())
                /*               .setParameter("tipoIdentificacion", tipoIdentificacion.name())*/
                .setParameter("tipoIdentificacion", tiposDocumento)
                .setParameter("idCicloAsignacion", idCicloAsignacion)
                .getResultList();

        logger.debug("Postulantes" + postulantes);

        List<PostulanteDTO> listaPostulantes = new ArrayList<PostulanteDTO>();

        for (Object[] registro : postulantes) {
            PostulanteDTO postulante = new PostulanteDTO();
            postulante.setNumeroIdentificacionJefeHogar(registro[0] != null ? registro[0].toString() : null);
            postulante.setNumeroIdentificacionIntegranteHogar(registro[1] != null ? registro[1].toString() : null);
            listaPostulantes.add(postulante);
        }
        logger.debug("Finaliza el servicio de consultarPostulantes()");
        return listaPostulantes;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCicloSucesor(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public CicloAsignacionModeloDTO consultarCicloSucesor(Long idCicloAsignacion, Long fecha, Long idCicloPredecesor) {
        logger.debug("Inicia servicio consultarCicloSucesor(Long)");
        CicloAsignacionModeloDTO cicloAsignacionDTO = null;
        List<Long> ciclosAExcluir = new ArrayList<>();
        if (idCicloAsignacion != null) {
            ciclosAExcluir.add(idCicloAsignacion);
        }
        if (idCicloPredecesor != null) {
            ciclosAExcluir.add(idCicloPredecesor);
        }
        List<CicloAsignacion> ciclosAsignacion = new ArrayList<>();
        if (!ciclosAExcluir.isEmpty()) {
            ciclosAsignacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_SUCESOR_CON_EXCEPCION)
                    .setParameter("idCiclosAsignacion", ciclosAExcluir).setParameter("fecha", new Date(fecha), TemporalType.DATE).getResultList();
        }
        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            cicloAsignacionDTO = new CicloAsignacionModeloDTO();
            cicloAsignacionDTO.copyEntityToDTO(ciclosAsignacion.get(0));
        }
        logger.debug("Finaliza servicio consultarCicloSucesor(Long)");
        return cicloAsignacionDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#guardarSolicitudGlobal(com.
     * asopagos.dto.modelo.SolicitudModeloDTO)
     */
    @Override
    public Long guardarSolicitudGlobal(SolicitudModeloDTO solicitudDTO) {
        logger.debug("Inicia servicio guardarSolicitudGlobal");
        Solicitud solicitud = solicitudDTO.convertToSolicitudEntity();
        Solicitud managed = entityManager.merge(solicitud);
        logger.debug("Finaliza servicio guardarSolicitudGlobal");
        return managed.getIdSolicitud();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.fovis.service.FovisService#consultarSolicitudUsuario(com.
     * asopagos.rest.security.dto.UserDTO)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudModeloDTO consultarSolicitudUsuario(UserDTO usuario) {
        logger.debug("Inicia servicio consultarSolicitudUsuario");
        SolicitudModeloDTO solicitud = new SolicitudModeloDTO();

        List<Solicitud> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_USUARIOWEB)
                .setParameter("nombreUsuario", usuario.getNombreUsuario()).setParameter("clasificacion", ClasificacionEnum.HOGAR)
                .setParameter("estadoSolicitud", EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA).getResultList();

        if (solicitudes != null && !solicitudes.isEmpty()) {
            solicitud.convertToDTO(solicitudes.get(0));
        }
        logger.debug("Finaliza servicio consultarSolicitudUsuario");
        return solicitud;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudesPostulacionEnProceso(
     *java.lang.String,
     * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum)
     */
    @SuppressWarnings("unchecked")
    public List<SolicitudPostulacionModeloDTO> consultarSolicitudesPostulacionEnProceso(String numeroIdentificacion,
                                                                                        TipoIdentificacionEnum tipoIdentificacion) {
        logger.info("GLPI 79807--> Inicia servicio consultarSolicitudesPostulacionEnProceso(String,TipoIdentificacionEnum):"+numeroIdentificacion+" tipoIdentificacion:"+tipoIdentificacion);

        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POSTULACION_POR_ESTADOS_Y_JEFE_HOGAR)
                .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                .getResultList();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * consultarPostulacionesEnCicloAsignacion(java.lang.Long)
     */
    @SuppressWarnings("unchecked")

    public List<SolicitudPostulacionModeloDTO> consultarPostulacionesEnCicloAsignacion(List<EstadoHogarEnum> estadosHogar,
                                                                                       Long idCicloAsignacion) {

        logger.debug("Inicia el servicio consultarPostulacionesEnCicloAsignacion");

        List<SolicitudPostulacionModeloDTO> postulacionesDTO = new ArrayList<>();
        List<Object[]> postulaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_CICLO_ASIGNACION)
                .setParameter("idCicloAsignacion", idCicloAsignacion).setParameter("estadosHogar", estadosHogar).getResultList();
        if (postulaciones != null && !postulaciones.isEmpty()) {
            for (Object[] datosPostulacion : postulaciones) {
                SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = new SolicitudPostulacionModeloDTO();
                PostulacionFOVISModeloDTO postulacionFovisModeloDTO = new PostulacionFOVISModeloDTO();
                postulacionFovisModeloDTO.convertToDTO((PostulacionFOVIS) datosPostulacion[0]);
                solicitudPostulacionModeloDTO.convertToDTO((SolicitudPostulacion) datosPostulacion[1]);
                /* Se asignan los datos del Jefe del Hogar */
                Afiliado afiliado = (Afiliado) datosPostulacion[2];
                JefeHogarModeloDTO jefeHogarModeloDTO = new JefeHogarModeloDTO();
                jefeHogarModeloDTO.convertToJefeHogarDTO((JefeHogar) datosPostulacion[6], afiliado.getPersona(),
                        (PersonaDetalle) datosPostulacion[5]);
                postulacionFovisModeloDTO.setJefeHogar(jefeHogarModeloDTO);
                /* Se asignan los datos de la postulacion */
                solicitudPostulacionModeloDTO.setNumeroRadicacion((String) datosPostulacion[3]);
                solicitudPostulacionModeloDTO.setPostulacionFOVISModeloDTO(postulacionFovisModeloDTO);
                postulacionesDTO.add(solicitudPostulacionModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio consultarPostulacionesEnCicloAsignacion");
        return postulacionesDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * consultarPostulacionesCrucesInternos(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<SolicitudPostulacionModeloDTO> consultarPostulacionesCrucesInternos(List<EstadoHogarEnum> estadosHogar,
                                                                                    Long idCicloAsignacion) {

        List<String> estados = new ArrayList<>();
        estados.add(EstadoHogarEnum.HABIL.name());
        estados.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        logger.debug("Inicia el servicio consultarPostulacionesCrucesInternos");

        List<SolicitudPostulacionModeloDTO> postulacionesDTO = new ArrayList<>();
        String str = String.valueOf(idCicloAsignacion);
        List<Object[]> postulaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_CRUCES_INTERNOS)
                .setParameter("idCicloAsignacion", idCicloAsignacion)
                .setParameter("estadosHogar", estados)
                .getResultList();
        if (postulaciones != null && !postulaciones.isEmpty()) {
            for (Object[] datosPostulacion : postulaciones) {
                SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = new SolicitudPostulacionModeloDTO();
                PostulacionFOVISModeloDTO postulacionFovisModeloDTO = new PostulacionFOVISModeloDTO();
                postulacionFovisModeloDTO.convertToDTO((PostulacionFOVIS) datosPostulacion[0]);
                solicitudPostulacionModeloDTO.convertToDTO((SolicitudPostulacion) datosPostulacion[1]);
                /* Se asignan los datos del Jefe del Hogar */
                Afiliado afiliado = (Afiliado) datosPostulacion[2];
                JefeHogarModeloDTO jefeHogarModeloDTO = new JefeHogarModeloDTO();
                jefeHogarModeloDTO.convertToJefeHogarDTO((JefeHogar) datosPostulacion[4], afiliado.getPersona(),
                        (PersonaDetalle) datosPostulacion[3]);
                postulacionFovisModeloDTO.setJefeHogar(jefeHogarModeloDTO);
                postulacionFovisModeloDTO.setFechaValidacionCruce((Date) datosPostulacion[6]);
                /* Se asignan los datos de la postulacion */
                solicitudPostulacionModeloDTO.setNumeroRadicacion((String) datosPostulacion[5]);
                solicitudPostulacionModeloDTO.setPostulacionFOVISModeloDTO(postulacionFovisModeloDTO);
                postulacionesDTO.add(solicitudPostulacionModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio consultarPostulacionesCrucesInternos");
        return postulacionesDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCicloAsignacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CicloAsignacionModeloDTO consultarCicloAsignacion(Long idCicloAsignacion) {
        if (idCicloAsignacion == null) {
            return null;
        }

        try {
            CicloAsignacion cicloAsignacion = (CicloAsignacion) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_ASIGNACION_POR_ID)
                    .setParameter("idCicloAsignacion", idCicloAsignacion).getSingleResult();
            CicloAsignacionModeloDTO cicloDTO = new CicloAsignacionModeloDTO(cicloAsignacion);
            logger.debug("Finaliza consultarCicloAsignacion(Long)");
            return cicloDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarCicloAsignacion(Long) "
                    + interpolate("No se encontraron resultados con el id del ciclo {0} ingresado.", idCicloAsignacion));
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisService#consultarHogaresAplicanCalificacionPostulacion(String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionPostulacion(Long idCicloAsignacion, Boolean calificados) {
        logger.debug(interpolate("Inicia consultarHogaresAplicanCalificacionPostulacion({0})", idCicloAsignacion));

        List<String> listaEstadoHogar = new ArrayList<>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL.name());
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());

        List<String> listaEstadoCruceHogar = new ArrayList<>();
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.name());
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCES_RATIFICADOS.name());
        List<PostulacionFOVIS> listaPostulacionFOVIS = new ArrayList<>();

        if (calificados) {
            listaPostulacionFOVIS = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION_EJECUCION, PostulacionFOVIS.class)
                    .setParameter("listaEstadoHogar", listaEstadoHogar)
                    .setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                    .setParameter("idCicloAsignacion", idCicloAsignacion)
                    .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getResultList();
        } else {
            listaPostulacionFOVIS = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION, PostulacionFOVIS.class)
                    .setParameter("listaEstadoHogar", listaEstadoHogar)
                    .setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                    .setParameter("idCicloAsignacion", idCicloAsignacion)
                    .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getResultList();
        }

        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<>();

        for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
            listaPostulacionFOVISDTO.add(new PostulacionFOVISModeloDTO(postulacionFOVIS));
        }

        logger.debug(interpolate("Finaliza consultarHogaresAplicanCalificacionPostulacion({0})", idCicloAsignacion));
        return listaPostulacionFOVISDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCondicionEspecialPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CondicionEspecialPersonaModeloDTO> consultarCondicionEspecialPersona(TipoIdentificacionEnum tipoIdentificacion,
                                                                                     String numeroIdentificacion, Long idPostulacion) {
        logger.debug("Inicia consultarCondicionEspecialPersona(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");

        List<CondicionEspecialPersona> listaCondicionEspecialPersona = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_ESPECIAL_PERSONA, CondicionEspecialPersona.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("idPostulacion", idPostulacion)
                .setParameter("activa", Boolean.TRUE).getResultList();

        List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialDTO = new ArrayList<CondicionEspecialPersonaModeloDTO>();

        for (CondicionEspecialPersona condicionEspecialPersona : listaCondicionEspecialPersona) {
            CondicionEspecialPersonaModeloDTO condicionEspecialPersonaDTO = new CondicionEspecialPersonaModeloDTO();
            condicionEspecialPersonaDTO.convertToDTO(condicionEspecialPersona);
            listaCondicionEspecialDTO.add(condicionEspecialPersonaDTO);
        }

        logger.debug("Finaliza consultarCondicionEspecialPersona(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        return listaCondicionEspecialDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#calcularTotalRecursosHogar(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal calcularTotalRecursosHogar(Long idPostulacion) {
        logger.debug("Inicia calcularTotalRecursosHogar(" + idPostulacion + ")");
        // Se consulta el total de ahorro previo de la postulacion
        Object ahorroPrevio = entityManager.createNamedQuery(NamedQueriesConstants.CALCULAR_TOTAL_AHORRO_PREVIO_HOGAR)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        BigDecimal totalAhorroPrevio = (ahorroPrevio != null ? new BigDecimal(ahorroPrevio.toString()) : BigDecimal.ZERO);
        // Se consulta el total de recurso complementario de la postulacion
        Object recursoComplem = entityManager.createNamedQuery(NamedQueriesConstants.CALCULAR_TOTAL_RECURSO_COMPLMENTARIO_HOGAR)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        BigDecimal totalRecursosComplementarios = (recursoComplem != null ? new BigDecimal(recursoComplem.toString()) : BigDecimal.ZERO);
        logger.debug("Finaliza calcularTotalRecursosHogar(" + idPostulacion + ")");
        return totalAhorroPrevio.add(totalRecursosComplementarios);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#calcularAhorroPrevio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal calcularAhorroPrevio(Long idPostulacion) {
        logger.debug("Inicia calcularAhorroPrevio(" + idPostulacion + ")");
        // Se consulta el total de ahorro previo de la postulacion
        Object ahorroPrevio = entityManager.createNamedQuery(NamedQueriesConstants.CALCULAR_TOTAL_AHORRO_PREVIO_HOGAR)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        return (ahorroPrevio != null ? new BigDecimal(ahorroPrevio.toString()) : BigDecimal.ZERO);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * consultarHogaresCalificacionPostulacion(java.lang.String)
     */
    @Override
    public List<PostulacionFOVISModeloDTO> consultarHogaresCalificacionPostulacion(String nombreCicloAsignacion) {
        logger.debug("Inicia servicio consultarHogaresCalificacionPostulacion");

        List<EstadoHogarEnum> listaEstadoHogar = new ArrayList<EstadoHogarEnum>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL);
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO);

        //            List<EstadoSolicitudPostulacionEnum> listaEstadoPostulacion = new ArrayList<EstadoSolicitudPostulacionEnum>();
        //            listaEstadoPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
        //            listaEstadoPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION, PostulacionFOVIS.class)
                .setParameter("listaEstadoHogar", listaEstadoHogar)
                //.setParameter("estadoHogarPostulado", EstadoHogarEnum.POSTULADO)
                //.setParameter("listaEstadoPostulacion", listaEstadoPostulacion)
                .setParameter("nombreCicloAsignacion", nombreCicloAsignacion).setParameter("tipoCruce", TipoCruceEnum.INTERNO)
                .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA).getResultList();

        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<PostulacionFOVISModeloDTO>();

        for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
            PostulacionFOVISModeloDTO postulacionFOVISDTO = new PostulacionFOVISModeloDTO();
            postulacionFOVISDTO.convertToDTO(postulacionFOVIS);
            listaPostulacionFOVISDTO.add(postulacionFOVISDTO);
        }

        logger.debug("Finaliza servicio consultarHogaresCalificacionPostulacion");
        return listaPostulacionFOVISDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#registrarIntentoPostulacionRequisito(java.util.List)
     */
    @Override
    public void registrarIntentoPostulacionRequisito(List<IntentoPostulacionRequisitoModeloDTO> intentoPostulacionRequisitosDTO) {
        logger.debug("Inicia servicio registrarIntentoPostulacionRequisito(List<IntentoPostulacionRequisitoModeloDTO>)");
        for (IntentoPostulacionRequisitoModeloDTO intentoPostulacionRequisitoModeloDTO : intentoPostulacionRequisitosDTO) {
            IntentoPostulacionRequisito intentoPostulacionRequisito = intentoPostulacionRequisitoModeloDTO.convertToEntity();
            if (intentoPostulacionRequisito.getIdIntentoPostulacionRequisito() == null) {
                entityManager.persist(intentoPostulacionRequisito);
            } else {
                entityManager.merge(intentoPostulacionRequisito);
            }
        }
        logger.debug("Finaliza servicio registrarIntentoPostulacionRequisito(List<IntentoPostulacionRequisitoModeloDTO>)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#postulacionWebHabilitada()
     */
    @Override
    public Boolean postulacionWebHabilitada() {
        logger.debug("Inicia servicio postulacionWebHabilitada()");
        ParametrizacionFOVIS parametrizacionPostulacionWebHabilitada = (ParametrizacionFOVIS) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_FOVIS_POR_NOMBRE)
                .setParameter("nombre", ParametroFOVISEnum.HABILITAR_POSTULACION_FOVIS_WEB).getSingleResult();
        logger.debug("Finaliza servicio postulacionWebHabilitada()");
        return parametrizacionPostulacionWebHabilitada.getValor();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#calcularTotalMesesAhorroProgramado(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer calcularTotalMesesAhorroProgramado(Long idPostulacionFOVIS) {
        try {
            logger.debug("Inicia calcularTotalMesesAhorroProgramado(" + idPostulacionFOVIS + ")");
            Integer numeroMeses = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHAS_AHORRO_PROGRAMADO)
                    .setParameter("idPostulacionFOVIS", idPostulacionFOVIS).getSingleResult();

            if (numeroMeses == null) {
                numeroMeses = NumerosEnterosConstants.CERO;
            }
            logger.debug("Finaliza calcularTotalMesesAhorroProgramado(" + idPostulacionFOVIS + ")");
            return numeroMeses;
        } catch (NonUniqueResultException | NoResultException e) {
            logger.debug("Finaliza con error calcularTotalMesesAhorroProgramado(" + idPostulacionFOVIS + ")", e);
            return NumerosEnterosConstants.CERO;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarIntegrantesHogarPostulacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IntegranteHogarModeloDTO> consultarIntegrantesHogarPostulacion(Long idPostulacion) {
        logger.debug(Interpolator.interpolate("consultarIntegrantesHogarPostulacion(Long) - {0}", idPostulacion));
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MIEMBROS_HOGAR, IntegranteHogarModeloDTO.class)
                .setParameter("estadoHogar", EstadoFOVISHogarEnum.ACTIVO)
                .setParameter("idPostulacion", idPostulacion).getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#guardarResultadoAsignacion(com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO)
     */
    @Override
    public SolicitudAsignacionFOVISModeloDTO guardarResultadoAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion,
                                                                        UserDTO usuario) {
        logger.debug("Inicia el servicio guardarResultadoAsignacion");
        /* Se cambia el estado de la tarea de Asignación FOVIS a Pendiente generación acta asignación FOVIS */
        solicitudAsignacion.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.PENDIENTE_GENERACION_ACTA_ASIGNACION_FOVIS);
        solicitudAsignacion.setFechaAceptacion(new Date().getTime());
        solicitudAsignacion.setUsuario(usuario.getNombreUsuario());
        SolicitudAsignacion solicitud = solicitudAsignacion.convertToEntity();
        SolicitudAsignacion managed = entityManager.merge(solicitud);

        /*
         * Se actualizan los hogares que fueron asignados (que en el campo Resultado asignación tienen el valor Asignado), se
         * cambia el estado del hogar a Asignado sin prórroga.
         */
        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = consultarPostulacionesAsignacion(
                solicitudAsignacion.getIdSolicitud());

        for (PostulacionFOVISModeloDTO postulacion : listaPostulacionFOVISDTO) {
            if (postulacion.getResultadoAsignacion() != null
                    && ResultadoAsignacionEnum.ESTADO_ASIGNADO.equals(postulacion.getResultadoAsignacion())) {
                postulacion.setEstadoHogar(EstadoHogarEnum.ASIGNADO_SIN_PRORROGA);
                crearActualizarPostulacionFOVIS(postulacion);
            }
        }

        solicitudAsignacion.setIdSolicitudAsignacion(managed.getIdSolicitudAsignacion());
        logger.debug("Finaliza el servicio guardarResultadoAsignacion");
        return solicitudAsignacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#guardarActaAsignacion(com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO)
     */
    @Override
    public ActaAsignacionFOVISModeloDTO guardarActaAsignacion(ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO) {
        logger.debug("Inicia el servicio guardarActaAsignacion");
        //Se almacena o actualiza el registro de acta de asignacion
        ActaAsignacionFOVIS actaAsignacionFOVIS = actaAsignacionFOVISModeloDTO.convertToEntity();
        ActaAsignacionFOVIS managed = entityManager.merge(actaAsignacionFOVIS);
        actaAsignacionFOVISModeloDTO.setIdActaAsignacion(managed.getIdActaAsignacion());
        logger.debug("Finaliza el servicio guardarActaAsignacion");
        return actaAsignacionFOVISModeloDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulacionesAsignacion()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesAsignacion(Long idSolicitudGlobal) {
        logger.debug("Inicia el servicio consultarPostulacionesAsignacion");

        List<EstadoHogarEnum> listaEstadoHogar = new ArrayList<EstadoHogarEnum>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL);
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO);

        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_ASIGNACION, PostulacionFOVIS.class)
                .setParameter("listaEstadoHogar", listaEstadoHogar).setParameter("idSolicitud", idSolicitudGlobal).getResultList();

        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<PostulacionFOVISModeloDTO>();

        for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
            PostulacionFOVISModeloDTO postulacionFOVISDTO = new PostulacionFOVISModeloDTO();
            postulacionFOVISDTO.convertToDTO(postulacionFOVIS);
            listaPostulacionFOVISDTO.add(postulacionFOVISDTO);
        }
        logger.debug("Finaliza servicio consultarPostulacionesAsignacion");
        return listaPostulacionFOVISDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#rechazarResultadoAsignacion(com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO)
     */
    @Override
    public SolicitudAsignacionFOVISModeloDTO rechazarResultadoAsignacion(@NotNull SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        logger.debug("Inicia el servicio rechazarResultadoAsignacion");
        SolicitudAsignacion solicitud = solicitudAsignacion.convertToEntity();
        /*
         * Se rechazan los resultados de asignación asociados al ciclo
         */
        for (PostulacionFOVISModeloDTO postulacion : solicitudAsignacion.getListadoPostulacionesHabiles()) {
            postulacion.setEstadoHogar(EstadoHogarEnum.RECHAZADO);
            crearActualizarPostulacionFOVIS(postulacion);
        }

        /* Cambia el estado de la tarea de asignación a Asignación rechazada por control interno */
        solicitud.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.ASIGNACION_RECHAZADA_CONTROL_INTERNO);
        SolicitudAsignacion managed = entityManager.merge(solicitud);

        /* Cambia el estado de la tarea de asignación a Cerrada */
        solicitud.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.CERRADA);
        managed = entityManager.merge(solicitud);

        solicitudAsignacion.setIdSolicitudAsignacion(managed.getIdSolicitudAsignacion());
        logger.debug("Finaliza el servicio rechazarResultadoAsignacion");
        return solicitudAsignacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudAsignacion(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public SolicitudAsignacionFOVISModeloDTO consultarSolicitudAsignacion(Long idSolicitud) {
        try {
            logger.debug("Inicia servicio consultarSolicitudAsignacion");
            SolicitudAsignacion solicitud = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ASIGNACION, SolicitudAsignacion.class)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            SolicitudAsignacionFOVISModeloDTO solicitudDTO = new SolicitudAsignacionFOVISModeloDTO();
            solicitudDTO.convertToDTO(solicitud);
            logger.debug("Finaliza servicio consultarSolicitudGlobal");
            return solicitudDTO;
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Integer consultarCantidadAsignacionesPreviasHogar(Long identificadorPostulacion) {
        try {
            logger.debug("Inicia servicio consultarAsignacionesPreviasHogar(Long)");

            Integer cantidadAsignaciones = (Integer) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_CANTIDAD_PROCESOS_ASIGNACION_BY_ID_POST)
                    .setParameter("idPostulacion", identificadorPostulacion).getSingleResult();

            logger.debug("Finaliza servicio consultarAsignacionesPreviasHogar(Long)");
            return cantidadAsignaciones;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ParametrizacionModalidadModeloDTO consultarParametrizacionModalidad(ModalidadEnum nombreParametrizacionModalidad) {
        logger.debug("Inicia servicio consultarParametrizacionModalidad");
        ParametrizacionModalidad parametrizacionModalidad = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_MODALIDAD, ParametrizacionModalidad.class)
                .setParameter("nombreParametrizacionModalidad", nombreParametrizacionModalidad).getSingleResult();

        ParametrizacionModalidadModeloDTO parametrizacionModalidadModeloDTO = new ParametrizacionModalidadModeloDTO(
                parametrizacionModalidad);
        // se consultan los rangos para la modalidad
        parametrizacionModalidadModeloDTO
                .setRangosSVFPorModalidad(consultarRangosSVFPorModalidad(nombreParametrizacionModalidad));
        // se consultan las formas de pago para la modalidad
        parametrizacionModalidadModeloDTO
                .setFormasDePagoModalidad(consultarFormasDePagoPorModalidad(nombreParametrizacionModalidad));

        logger.debug("Finaliza servicio consultarParametrizacionModalidad");
        return parametrizacionModalidadModeloDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarAhorroPrevio(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum)
     */
    @Override
    public AhorroPrevioModeloDTO consultarAhorroPrevio(Long idPostulacion, TipoAhorroPrevioEnum tipoAhorro) {
        try {
            logger.debug("Inicia servicio consultarAhorroPrevio");
            AhorroPrevio ahorroPrevio = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AHORRO_PREVIO, AhorroPrevio.class)
                    .setParameter("idPostulacion", idPostulacion).setParameter("tipoAhorro", tipoAhorro).getSingleResult();
            AhorroPrevioModeloDTO ahorroPrevioDTO = new AhorroPrevioModeloDTO();
            ahorroPrevioDTO.convertToDTO(ahorroPrevio);
            logger.debug("Finaliza servicio consultarAhorroPrevio");
            return ahorroPrevioDTO;
        } catch (NoResultException e) {
            logger.error("No existe ahorro previo para [" + idPostulacion + "," + tipoAhorro + "]", e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#guardarSolicitudAsignacion(com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO)
     */
    @Override
    public SolicitudAsignacionFOVISModeloDTO guardarSolicitudAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        // Consultar solicitud asigncion por id sol global para actualizar
        if (solicitudAsignacion.getIdSolicitud() != null) {
            SolicitudAsignacionFOVISModeloDTO solAsig = consultarSolicitudAsignacionPorSolicitudGlobal(solicitudAsignacion.getIdSolicitud());
            if (solAsig != null && solAsig.getIdSolicitudAsignacion() != null) {
                solicitudAsignacion.setIdSolicitudAsignacion(solAsig.getIdSolicitudAsignacion());
            }
        }
        // Se registra la informacion de solicitud
        logger.debug("Inicia el servicio guardarSolicitudAsignacion");
        SolicitudAsignacion solicitud = solicitudAsignacion.convertToEntity();
        SolicitudAsignacion managed = entityManager.merge(solicitud);
        solicitudAsignacion.setIdSolicitudAsignacion(managed.getIdSolicitudAsignacion());
        logger.debug("Finaliza el servicio guardarSolicitudAsignacion");
        return solicitudAsignacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulacionesSolicitudPorResultado(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesSolicitudPorResultado(Long idSolicitudAsignacion,
                                                                                       ResultadoAsignacionEnum resultadoAsignacion) {
        logger.debug("Inicia servicio consultarPostulacionesSolicitudPorResultado");
        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ASIGNACIONES_POR_SOLICITUD_y_RESULTADO_ASIGNACION,
                        PostulacionFOVIS.class)
                .setParameter("idSolicitudAsignacion", idSolicitudAsignacion).setParameter("resultadoAsignacion", resultadoAsignacion)
                .getResultList();
        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<PostulacionFOVISModeloDTO>();

        for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
            PostulacionFOVISModeloDTO postulacionFOVISDTO = new PostulacionFOVISModeloDTO();
            postulacionFOVISDTO.convertToDTO(postulacionFOVIS);
            listaPostulacionFOVISDTO.add(postulacionFOVISDTO);
        }

        logger.debug("Finaliza servicio consultarPostulacionesSolicitudPorResultado");
        return listaPostulacionFOVISDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulantes()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<CartasAsignacionDTO> consultarCartasAsignacionPorCicloAnoAsignacion(Integer anoAsignacion, Long cicloAsignacion) {
        logger.debug("Se inicia el servicio de consultarCartasAsignacion()");
        List<Object[]> cartasAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARTAS_ASIGNACION_FOVIS_POR_CICLO_ANO_ASIGNACION)
                .setParameter("idCicloAsignacion", cicloAsignacion).getResultList();

        List<CartasAsignacionDTO> listaCartasAsignacion = new ArrayList<CartasAsignacionDTO>();
        Map<String, CartasAsignacionDTO> map = new HashMap<>();
        for (Object[] registro : cartasAsignacion) {
            CartasAsignacionDTO carta = new CartasAsignacionDTO(registro[0] != null ? registro[0].toString() : "",
                    registro[1] != null ? TipoIdentificacionEnum.valueOf(registro[1].toString()) : null,
                    registro[2] != null ? registro[2].toString() : "", registro[3] != null ? registro[3].toString() : "",
                    registro[4] != null ? registro[4].toString() : "",
                    registro[5] != null ? ModalidadEnum.valueOf(registro[5].toString()) : null,
                    registro[6] != null ? registro[6].toString() : "", registro[7] != null ? registro[7].toString() : "");
            if (!map.containsKey(carta.getNumeroIdentificacionJefeHogar())) {
                map.put(carta.getNumeroIdentificacionJefeHogar(), carta);
                listaCartasAsignacion.add(carta);
            }
        }
        logger.debug("Finaliza el servicio de consultarCartasAsignacion()");
        return listaCartasAsignacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudGlobalPostulacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudModeloDTO consultarSolicitudGlobalPostulacion(Long idPostulacion) {
        logger.debug("Se inicia el servicio de consultarSolicitudGlobalPostulacion");
        Solicitud solicitud = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GLOBAL_POSTULACION, Solicitud.class)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
        solicitudDTO.convertToDTO(solicitud);
        logger.debug("Finaliza el servicio de consultarSolicitudGlobalPostulacion");
        return solicitudDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarPostulantes()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<Integer> consultarAnosCicloAsignacionEstadoCerrado() {
        logger.debug("Se inicia el servicio de consultarAnosCicloAsignacionEstadoCerrado()");
        List<Integer> anosCicloAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ANOS_CICLO_ASIGNACION_ESTADO_CERRADO).getResultList();

        logger.debug("Finaliza el servicio de consultarAnosCicloAsignacionEstadoCerrado()");
        return anosCicloAsignacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarCiclosAsignacionEstadoCerradoPorAnio(java.lang.Integer)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacionEstadoCerradoPorAnio(Integer anoAsignacion) {
        logger.debug("Se inicia el servicio de consultarCiclosAsignacionEstadoCerradoPorAnio()");
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<CicloAsignacionModeloDTO>();

        List<CicloAsignacion> ciclosAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_CERRADOS_POR_ANIO)
                .setParameter("anio", anoAsignacion).getResultList();

        for (CicloAsignacion cicloAsignacion : ciclosAsignacion) {
            CicloAsignacionModeloDTO cicloAsignacionModelDTO = new CicloAsignacionModeloDTO(cicloAsignacion);
            ciclosAsignacionDTO.add(cicloAsignacionModelDTO);
        }

        logger.debug("Finaliza el servicio de consultarCiclosAsignacionEstadoCerradoPorAnio()");
        return ciclosAsignacionDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudPostulacionById(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacionById(Long idSolicitudPostulacion) {
        logger.debug("Se inicia el servicio de consultarSolicitudPostulacionById(Long)");
        try {
            SolicitudPostulacion sol = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_SOLICITUD_POSTULACION_BY_ID, SolicitudPostulacion.class)
                    .setParameter("idSolicitud", idSolicitudPostulacion).getSingleResult();
            SolicitudPostulacionModeloDTO solicitudDTO = new SolicitudPostulacionModeloDTO();
            solicitudDTO.convertToDTO(sol);
            logger.debug("Finaliza consultarSolicitudPostulacionById(Long)");
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudPostulacionById(Long)"
                    + interpolate("No se encontraron resultados con el id solicitud postulacion {0} ingresada.", idSolicitudPostulacion));
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#personaBeneficiariaPostulacionModalidad(
     *com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.fovis.ModalidadEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean personaBeneficiariaPostulacionModalidad(TipoIdentificacionEnum tipoIdentificacionPersona,
                                                           String numeroIdentificacionPersona, ModalidadEnum modalidad) {
        logger.debug("Inicia servicio personaBeneficiariaPostulacionModalidad(TipoIdentificacionEnum, String, ModalidadEnum)");
        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ASIGNACIONES_PREVIAS_PERSONA_MODALIDAD, PostulacionFOVIS.class)
                .setParameter("tipoIdentificacionPersona", tipoIdentificacionPersona)
                .setParameter("numeroIdentificacionPersona", numeroIdentificacionPersona).setParameter("modalidad", modalidad)
                .setParameter("estadoHogar", EstadoHogarEnum.ASIGNADO_SIN_PRORROGA).getResultList();

        Boolean beneficiario = listaPostulacionFOVIS != null && !listaPostulacionFOVIS.isEmpty();

        logger.debug("Finaliza servicio personaBeneficiariaPostulacionModalidad(TipoIdentificacionEnum, String, ModalidadEnum)");
        return beneficiario;
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionDocumentoActaAsignacionDTO consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal()");
        // Objeto de retorno con la información para el acta de asignación
        InformacionDocumentoActaAsignacionDTO informacionDocumento = new InformacionDocumentoActaAsignacionDTO();

        // Contiene la información del acta de asignacion a devolver
        List<ActaAsignacionFOVIS> listActaAsignacion = null;
        // Indica si no existe un acta para la solicitud
        Boolean actaAnterior = false;

        // Se verifica si ya existe un acta de asignacion para la solicitud global
        listActaAsignacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTA_ASIGNACION_SOLICITUD_GLOBAL, ActaAsignacionFOVIS.class)
                .setParameter("idSolicitud", idSolicitudGlobal).getResultList();

        // Si no se encuentra acta de asignación previa asociada a la solicitud global
        // Se consultan los responsables de aprobar el ultima acta de asignacion
        if (listActaAsignacion == null || listActaAsignacion.isEmpty()) {
            listActaAsignacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESPONSABLES_ULTIMA_ACTA_ASGINACION,
                    ActaAsignacionFOVIS.class).getResultList();
            actaAnterior = true;
        }

        // Se obtiene la información 
        if (listActaAsignacion != null && !listActaAsignacion.isEmpty()) {
            informacionDocumento.convertEntityToDTO(listActaAsignacion.get(0), actaAnterior);
        }

        // Consulta la informacion para gestionar el acta de asignacion
        List<Object[]> datosSoporteAsignacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_GENERAR_SOPORTE_ACTA_ASGINACION)
                .setParameter("idSolicitudGlobal", idSolicitudGlobal).getResultList();

        if (datosSoporteAsignacion != null && !datosSoporteAsignacion.isEmpty()) {
            for (Object[] registro : datosSoporteAsignacion) {
                // Se obtiene la fecha inicio y fin de la postulación
                informacionDocumento.setFechaInicialPostulacion(registro[0] != null ? ((Date) registro[0]).getTime() : null);
                informacionDocumento.setFechaFinalPostulacion(registro[1] != null ? ((Date) registro[1]).getTime() : null);
                informacionDocumento.setCicloAsignacion(registro[2] != null ? registro[2].toString() : "");
                informacionDocumento.setValorDisponible(registro[3] != null ? (BigDecimal) registro[3] : BigDecimal.ZERO);
            }
        }
        informacionDocumento.setFechaActaAsignacion(new Date().getTime());
        logger.debug("Finaliza el servicio de consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal()");
        return informacionDocumento;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudAsignacionPorSolicitudGlobal(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public SolicitudAsignacionFOVISModeloDTO consultarSolicitudAsignacionPorSolicitudGlobal(Long idSolicitudGlobal) {
        try {
            logger.debug("Inicia servicio consultarSolicitudAsignacionPorSolicitudGlobal");
            Solicitud solicitudGlobal = new Solicitud();
            solicitudGlobal.setIdSolicitud(idSolicitudGlobal);
            SolicitudAsignacion solicitud = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ASIGNACION_POR_ID_SOLICITUD_GLOBAL,
                            SolicitudAsignacion.class)
                    .setParameter("idSolicitud", solicitudGlobal).getSingleResult();
            SolicitudAsignacionFOVISModeloDTO solicitudDTO = new SolicitudAsignacionFOVISModeloDTO();
            solicitudDTO.convertToDTO(solicitud);
            logger.debug("Finaliza servicio consultarSolicitudAsignacionPorSolicitudGlobal");
            return solicitudDTO;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#inactivarIntegrantesHogarNoRelacionados(java.lang.Long, java.lang.Long, java.util.List)
     */
    @Override
    public void inactivarIntegrantesHogarNoRelacionados(Long idJefeHogar, Long idPostulacion, List<Long> integrantesPermanecientes) {
        logger.debug(Interpolator.interpolate("Inicia inactivarIntegrantesHogarNoRelacionados(Long, Long, List<Long>) {0}, {1}, {2}", idJefeHogar, idPostulacion, integrantesPermanecientes));
        List<IntegranteHogar> listaIntegrantesAInactivar;
        if (integrantesPermanecientes == null || integrantesPermanecientes.isEmpty()) {
            listaIntegrantesAInactivar = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTES_HOGAR_JEFE_HOGAR_ESTADO, IntegranteHogar.class)
                    .setParameter("idJefeHogar", idJefeHogar).setParameter("idPostulacion", idPostulacion)
                    .setParameter("estadoIntegrante", EstadoFOVISHogarEnum.ACTIVO).getResultList();
        } else {
            listaIntegrantesAInactivar = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTES_HOGAR_NO_RELACIONADOS_ACTIVOS, IntegranteHogar.class)
                    .setParameter("integrantesExcluir", integrantesPermanecientes).setParameter("idJefeHogar", idJefeHogar)
                    .setParameter("idPostulacion", idPostulacion).setParameter("estadoIntegrante", EstadoFOVISHogarEnum.ACTIVO)
                    .getResultList();
        }
        if (listaIntegrantesAInactivar != null && !listaIntegrantesAInactivar.isEmpty()) {
            for (IntegranteHogar integranteHogar : listaIntegrantesAInactivar) {
                integranteHogar.setEstadoHogar(EstadoFOVISHogarEnum.INACTIVO);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarDocumentoSolicitud(java.lang.Long)
     */
    public DocumentoAdministracionEstadoSolicitudDTO consultarDocumentoSolicitud(Long idSolicitudGlobal) {
        logger.debug("Inicia servicio consultarDocumentoSolicitud(Long)");
        DocumentoAdministracionEstadoSolicitudDTO documentoEstadoDTO = new DocumentoAdministracionEstadoSolicitudDTO();
        try {
            DocumentoAdministracionEstadoSolicitud documentoEstado = (DocumentoAdministracionEstadoSolicitud) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_ESTADO_SOLICITUD,
                            DocumentoAdministracionEstadoSolicitud.class)
                    .setParameter("tipoDocumentoAdjunto", TipoDocumentoAdjuntoEnum.CONTROL_INTERNO_FOVIS)
                    .setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();

            documentoEstadoDTO.convertToDTO(documentoEstado);
        } catch (NoResultException e) {
            return documentoEstadoDTO;
        }
        logger.debug("Finaliza servicio consultarDocumentoSolicitud(Long)");
        return documentoEstadoDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#guardarActualizarCicloAsignacion(com.asopagos.dto.modelo.CicloAsignacionModeloDTO)
     */
    @Override
    public CicloAsignacionModeloDTO guardarActualizarCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModelDTO) {
        logger.debug("Se inicia el servicio de guardarActualizarCicloAsignacion(CicloAsignacionModeloDTO)");
        // Se actualiza el ciclo de asignacion
        CicloAsignacion cicloAsignacion = cicloAsignacionModelDTO.convertToEntity();
        cicloAsignacion = entityManager.merge(cicloAsignacion);
        cicloAsignacionModelDTO.copyEntityToDTO(cicloAsignacion);
        logger.debug("Finaliza el servicio de guardarActualizarCicloAsignacion(CicloAsignacionModeloDTO)");
        return cicloAsignacionModelDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarActaAsignacionPorIdSolicitudAsignacion(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ActaAsignacionFOVISModeloDTO consultarActaAsignacionPorIdSolicitudAsignacion(Long idSolicitudAsignacion) {
        try {
            logger.debug("Inicia servicio consultarActaAsignacionPorIdSolicitudAsignacion");
            ActaAsignacionFOVIS actaAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTA_ASGINACION_POR_ID_SOLICITUD_ASIGNACION,
                            ActaAsignacionFOVIS.class)
                    .setParameter("idSolicitud", idSolicitudAsignacion)
                    .setMaxResults(TOP_1)
                    .getSingleResult();
            ActaAsignacionFOVISModeloDTO actaAsignacionDTO = new ActaAsignacionFOVISModeloDTO();
            actaAsignacionDTO.convertToDTO(actaAsignacion);
            logger.debug("Finaliza servicio consultarActaAsignacionPorIdSolicitudAsignacion");
            return actaAsignacionDTO;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Metodo encargado de consultar la ubicancion por id
     *
     * @param idUbicacion identificador de la ubicacion a consultar
     * @return UbicacionModeloDTO DTO con la informacion de la Ubicacion
     */
    private UbicacionModeloDTO consultarUbicacionProyectoVivienda(Long idUbicacion) {
        logger.debug("Se inicia el servicio de consultarUbicacionProyectoVivienda(idUbicacion)");
        UbicacionModeloDTO ubicacionProyectoDTO = new UbicacionModeloDTO();
        try {
            Ubicacion ubicacionProyecto = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_ID, Ubicacion.class)
                    .setParameter("idUbicacion", idUbicacion).getSingleResult();
            ubicacionProyectoDTO.convertToDTO(ubicacionProyecto);
        } catch (NoResultException e) {
            ubicacionProyectoDTO = null;
            logger.debug("No existe Ubicación asociada");
        }
        logger.debug("Finaliza el servicio de consultarUbicacionProyectoVivienda()");
        return ubicacionProyectoDTO;
    }

    /**
     * Metodo encargado de consultar las licencias y detalle licencias de los proyectos de vivienda asociados al oferente
     *
     * @param listaProyectoSolucionViviendaDTO, lista de DTOs con la lista de los proyectos de vivienda
     * @param idsProyectoVivienda               lista con los indentificadores de los proyectos de vivienda
     * @return lista con los proyectos de vivienda y sus licencias asociadas
     */
    private List<ProyectoSolucionViviendaModeloDTO> consultarLicenciaProyectoVivienda(
            List<ProyectoSolucionViviendaModeloDTO> listaProyectoSolucionViviendaDTO, List<Long> idsProyectoVivienda) {
        logger.debug("Se inicia el servicio de consultarLicenciaProyectoVivienda()");

        List<Licencia> listaLicencias = null;
        List<LicenciaModeloDTO> listaLicencia = new ArrayList<LicenciaModeloDTO>();
        Map<Long, List<LicenciaModeloDTO>> proyectoLicencia = new HashMap<Long, List<LicenciaModeloDTO>>();

        if (idsProyectoVivienda != null && !idsProyectoVivienda.isEmpty()) {
            listaLicencias = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LICENCIA_IDS_PROYECTO_VIVIENDA, Licencia.class)
                    .setParameter("idProyectos", idsProyectoVivienda).getResultList();
        }

        if (listaLicencias != null && !listaLicencias.isEmpty()) {
            Map<Long, LicenciaModeloDTO> listaLicenciasDTO = new HashMap<Long, LicenciaModeloDTO>();
            List<LicenciaModeloDTO> listaLicenciasProyectoDTO = new ArrayList<LicenciaModeloDTO>();
            List<Long> idsLicencias = new ArrayList<Long>();
            for (Licencia licencia : listaLicencias) {
                LicenciaModeloDTO licenciaDTO = new LicenciaModeloDTO();
                licenciaDTO.convertToDTO(licencia);
                listaLicenciasProyectoDTO.add(licenciaDTO);
                listaLicenciasDTO.put(licencia.getIdLicencia(), licenciaDTO);
                idsLicencias.add(licencia.getIdLicencia());
            }

            //Se consultan los detalles de las licencias asociadas
            List<LicenciaDetalle> listaDetalleLicencias = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LICENCIA_DETALLE_POR_ID_LICENCIAS, LicenciaDetalle.class)
                    .setParameter("idLicencias", idsLicencias).getResultList();

            Map<Long, List<LicenciaDetalleModeloDTO>> listaDetalleLicenciasDTO = new HashMap<Long, List<LicenciaDetalleModeloDTO>>();
            for (LicenciaDetalle detalleLicencia : listaDetalleLicencias) {
                LicenciaDetalleModeloDTO detalleLicenciaDTO = new LicenciaDetalleModeloDTO();
                detalleLicenciaDTO.convertToDTO(detalleLicencia);
                if (listaDetalleLicenciasDTO.containsKey(detalleLicencia.getIdLicencia())) {
                    listaDetalleLicenciasDTO.get(detalleLicencia.getIdLicencia()).add(detalleLicenciaDTO);
                } else {
                    listaDetalleLicenciasDTO.put(detalleLicencia.getIdLicencia(), new ArrayList<>());
                    listaDetalleLicenciasDTO.get(detalleLicencia.getIdLicencia()).add(detalleLicenciaDTO);
                }
            }

            //Se agregan los detalles de cada licencia
            for (LicenciaModeloDTO licenciaDTO : listaLicenciasProyectoDTO) {
                if (listaDetalleLicenciasDTO.containsKey(licenciaDTO.getIdLicencia())) {
                    licenciaDTO.setLicenciaDetalle(listaDetalleLicenciasDTO.get(licenciaDTO.getIdLicencia()));
                } else
                    licenciaDTO.setLicenciaDetalle(new ArrayList<>());
            }

            //Cuando es la clasificacion de la licencia es Urbanismo o Construccion se obtienen los
            //valores de fecha inicio y fecha fin y se asocian a la licencia, para que sean mostrados correctamente en pantalla
            for (LicenciaModeloDTO licenciaDTO : listaLicenciasProyectoDTO) {
                for (LicenciaDetalleModeloDTO detalle : licenciaDTO.getLicenciaDetalle()) {
                    if ((ClasificacionLicenciaEnum.URBANISMO.equals(detalle.getClasificacionLicencia()))
                            || (ClasificacionLicenciaEnum.CONSTRUCCION.equals(detalle.getClasificacionLicencia()))) {
                        licenciaDTO.setFechaInicioVigenciaLicencia(detalle.getFechaInicio() != null ? detalle.getFechaInicio() : null);
                        licenciaDTO.setFechaFinVigenciaLicencia(detalle.getFechaFin() != null ? detalle.getFechaFin() : null);
                        licenciaDTO.setNumeroResolucion(detalle.getNumeroResolucion() != null ? detalle.getNumeroResolucion() : null);
                    }
                }
                listaLicencia.add(licenciaDTO);
                proyectoLicencia.put(licenciaDTO.getIdProyecto(), listaLicencia);
            }

            //Se actualizan las licencias a los proyectos de vivienda
            for (ProyectoSolucionViviendaModeloDTO proyectoVivienda : listaProyectoSolucionViviendaDTO) {
                proyectoVivienda.setLicencias(proyectoVivienda.getLicencias() == null ? new ArrayList<>() : proyectoVivienda.getLicencias());
                for (LicenciaModeloDTO licenciaDTO : listaLicenciasProyectoDTO) {
                    if (licenciaDTO.getIdProyecto().equals(proyectoVivienda.getIdProyectoVivienda())) {
                        proyectoVivienda.getLicencias().add(licenciaDTO);
                    }
                }
            }
        }
        logger.debug("Finaliza el servicio de consultarLicenciaProyectoVivienda()");
        return listaProyectoSolucionViviendaDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarMedioPagoProyectoVivienda(java.lang.Long)
     */
    @Override
    public MedioDePagoModeloDTO consultarMedioPagoProyectoVivienda(Long idProyectoVivienda) {
        logger.debug("Se inicia el servicio de consultarMedioPagoProyectoVivienda(idProyectoVivienda)");
        MedioDePagoModeloDTO medioReturn = new MedioDePagoModeloDTO();
        try {
            /* Consulta el medio de pago activo asociado al proyecto. */
            logger.info("idProyectoVivienda--> " + idProyectoVivienda);
            MedioDePago medioDePago = (MedioDePago) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_ACTIVO_PROYECTO)
                    .setParameter("idProyectoVivienda", idProyectoVivienda).setParameter("medioActivo", Boolean.TRUE).getSingleResult();
            logger.info("medioDePago--> " + medioDePago.toString());
            medioReturn.convertToDTO(medioDePago);
        } catch (Exception e) {
            logger.error(e);
            medioReturn = new MedioDePagoModeloDTO();
        }
        logger.debug("Finaliza el servicio de consultarMedioPagoProyectoVivienda(idProyectoVivienda)");
        return medioReturn;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarListaPostulacionFOVIS(java.util.List)
     */
    @Override
    public void crearActualizarListaPostulacionFOVIS(List<PostulacionFOVISModeloDTO> listaPostulaciones) {
        logger.debug("Inicia el servicio crearActualizarPostulacionFOVIS");
        for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPostulaciones) {
            postulacionFOVISDTO = crearActualizarPostulacionFOVIS(postulacionFOVISDTO);
        }
        logger.debug("Finaliza el servicio crearActualizarPostulacionFOVIS");

    }

    @Override
    public void actualizarEstadoHogar(Long idPostulacion, EstadoHogarEnum estadoHogar) {
        logger.debug("Inicia actualizarEstadoHogar(" + idPostulacion + "," + estadoHogar + ")");
        // Se consulta la postulacion por el identificador
        PostulacionFOVISModeloDTO postulacionDTO = consultarPostulacionFOVIS(idPostulacion);
        // Se obtiene el entity y se actualiza el estado
        PostulacionFOVIS postulacion = postulacionDTO.convertToEntity();
        postulacion.setEstadoHogar(estadoHogar);
        entityManager.merge(postulacion);
        logger.debug("Finaliza actualizarEstadoHogar(" + idPostulacion + "," + estadoHogar + ")");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarIntegranteHogarPorIdentificacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IntegranteHogarModeloDTO consultarIntegranteHogarPorIdentificacion(TipoIdentificacionEnum tipoIdentificacion,
                                                                              String numeroIdentificacion, Long idPostulacion) {
        logger.debug(Interpolator.interpolate(
                "Inicia consultarIntegrantesHogarPorTipoNumeroIdJefeHogar(TipoIdentificacionEnum, String, Long) - {0}, {1}, {2}",
                tipoIdentificacion, numeroIdentificacion, idPostulacion));
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTE_HOGAR_POR_TIPO_NUMERO_ID_POSTULACION,
                        IntegranteHogarModeloDTO.class)
                .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("estadoHogar", EstadoFOVISHogarEnum.ACTIVO).setParameter("idPostulacion", idPostulacion)
                .getSingleResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarHogaresAplicanCalificacionConReclamacionProcedente(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionConReclamacionProcedente(Long idCicloAsignacion) {
        logger.debug(interpolate("Inicia consultarHogaresAplicanCalificacionConReclamacionProcedente({0})", idCicloAsignacion));

        List<String> listaEstadoHogar = new ArrayList<>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL.name());
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());

        List<String> listaEstadoCruceHogar = new ArrayList<>();
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.name());
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCES_RATIFICADOS.name());

        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION_CON_RECLAMACION_PROCEDENTE,
                        PostulacionFOVIS.class)
                .setParameter("listaEstadoHogar", listaEstadoHogar).setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                .setParameter("idCicloAsignacion", idCicloAsignacion).setParameter("tipoCruce", TipoCruceEnum.EXTERNO.name())
                .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getResultList();

        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<>();

        for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
            listaPostulacionFOVISDTO.add(new PostulacionFOVISModeloDTO(postulacionFOVIS));
        }

        logger.debug("Finaliza servicio consultarHogaresAplicanCalificacionConReclamacionProcedente");
        return listaPostulacionFOVISDTO;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarIntegranteHogarPorIdentificacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Integer> ConsultarPersonaPerdioViviendaImposibilidadPago(TipoIdentificacionEnum tipoIdentificacion,
                                                                         String numeroIdentificacion) {
        logger.debug(Interpolator.interpolate(
                "Inicia consultarIntegrantesHogarPorTipoNumeroIdJefeHogar(TipoIdentificacionEnum, String) - {0}, {1}",
                tipoIdentificacion, numeroIdentificacion));

        List<Integer> res = new ArrayList<Integer>();
        List<Object> idPostulacion = entityManager
                .createNamedQuery(NamedQueriesConstants.VALIDAR_POSTULACION_PERDIO_VIVIENDA_IMPOSIBILIDAD_PAGO)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                //.setParameter("resultadoAsignacion", resultadoAsignacion)
                .getResultList();

        for (Object po : idPostulacion) {
            res.add(Integer.parseInt(po.toString()));
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoCrucesFOVISDTO> consultarEstadoCrucesHogar(String numeroRadicacion) {
        logger.debug("Inicia servicio consultarEstadoCrucesHogar");
        List<HistoricoCrucesFOVISDTO> listResult = new ArrayList<>();
        List<Object[]> listCruces = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CRUCE_HOGAR)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        for (Object[] registro : listCruces) {
            Cruce cruce = (Cruce) registro[0];
            CruceDetalle cruceDetalle = (CruceDetalle) registro[1];
            Persona persona = (Persona) registro[2];
            HistoricoCrucesFOVISDTO historico = new HistoricoCrucesFOVISDTO(persona, cruce, cruceDetalle);
            historico.setTipoCruce(registro[3] != null ? TipoCruceEnum.valueOf((String) registro[3]) : null);
            historico.setClasificacion(registro[4] != null ? ClasificacionEnum.valueOf((String) registro[4]) : null);
            historico.setEstadoCruceHogar(registro[5] != null ? EstadoCruceHogarEnum.valueOf((String) registro[5]) : null);
            listResult.add(historico);
        }
        logger.debug("Finaliza servicio consultarEstadoCrucesHogar");
        return listResult;
    }

    /* (non-Javadoc)
     * @see com.asopagos.fovis.service.FovisService#consultarHistoricoSolicitudesPostulacion(java.lang.String, com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum, com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SolicitudPostulacionFOVISDTO> consultarHistoricoSolicitudesPostulacion(String numeroSolicitud,
                                                                                       TipoSolicitudEnum tipoSolicitud, EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,
                                                                                       Long fechaExactaRadicacion, Long fechaInicio, Long fechaFin) {
        logger.debug("Se inicia el servicio de consultarHistoricoSolicitudesPostulacion()");
        List<Object[]> solicitudesPostulacion = null;
        if (fechaInicio != null && fechaFin != null) {
            solicitudesPostulacion = consultarHistoricoSolicitudesPostulacionPorRangoFechas(numeroSolicitud,
                    tipoSolicitud, estadoSolicitudPostulacion, fechaExactaRadicacion, fechaInicio, fechaFin);
        } else {
            solicitudesPostulacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_SOLICITUD_POSTULACION)
                    .setParameter("numeroSolicitud", numeroSolicitud)
                    .setParameter("estadoSolicitudPostulacion",
                            estadoSolicitudPostulacion != null ? estadoSolicitudPostulacion.name() : null)
                    .setParameter("fechaExactaRadicacion", fechaExactaRadicacion).getResultList();
        }

        List<SolicitudPostulacionFOVISDTO> listaSolicitudPostulacion = new ArrayList<SolicitudPostulacionFOVISDTO>();

        for (Object[] registro : solicitudesPostulacion) {

            Date fechaRadicacion = convertirObjetoAFecha(registro[2]);

            SolicitudPostulacionFOVISDTO solicitudPostulacion = new SolicitudPostulacionFOVISDTO(
                    registro[0] != null ? registro[0].toString() : "",
                    registro[1] != null ? registro[1].toString() : "", fechaRadicacion,
                    registro[3] != null ? registro[3].toString() : "",
                    registro[4] != null ? registro[4].toString() : "",
                    registro[5] != null ? EstadoSolicitudPostulacionEnum.valueOf(registro[5].toString()) : null,
                    registro[6] != null ? registro[6].toString() : "");

            listaSolicitudPostulacion.add(solicitudPostulacion);

        }
        logger.debug("Finaliza el servicio de consultarHistoricoSolicitudesPostulacion()");
        return listaSolicitudPostulacion;
    }

    /**
     * Consulta las solicitudes de legalizacion por rango de fechas
     *
     * @param fechaInicio fecha de inicio
     * @param fechaFin    fecha fin
     * @return Lista de solicitudes de historico de legalizacion donde la fecha
     * de radicacion se encuentra en el rango de fechas ingresado
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> consultarHistoricoSolicitudesPostulacionPorRangoFechas(String numeroSolicitud,
                                                                                  TipoSolicitudEnum tipoSolicitud, EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,
                                                                                  Long fechaExactaRadicacion, Long fechaInicio, Long fechaFin) {
        logger.debug("Se inicia el servicio de consultarHistoricoSolicitudesPostulacionPorRangoFechas()");

        List<Object[]> solicitudesPostulacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_SOLICITUD_POSTULACION_RANGO_FECHAS)
                .setParameter("numeroSolicitud", numeroSolicitud)
                .setParameter("estadoSolicitudPostulacion",
                        estadoSolicitudPostulacion != null ? estadoSolicitudPostulacion.name() : null)
                .setParameter("fechaExactaRadicacion", fechaExactaRadicacion)
                .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin).getResultList();

        logger.debug("Finaliza el servicio de consultarHistoricoSolicitudesPostulacionPorRangoFechas()");
        return solicitudesPostulacion;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoAsignacionFOVISDTO> consultarHistoricoAsignacion(String numeroRadicacion) {
        logger.debug("Se inicia el servicio de consultarHistoricoAsignacion()");
        List<Object[]> historicoAsignacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ASIGNACION_FOVIS)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        List<HistoricoAsignacionFOVISDTO> listaHistoricoAsignacion = new ArrayList<HistoricoAsignacionFOVISDTO>();
        for (Object[] registro : historicoAsignacion) {
            HistoricoAsignacionFOVISDTO historico = new HistoricoAsignacionFOVISDTO(registro[0] != null ? registro[0].toString() : "",
                    registro[1] != null ? registro[1].toString() : "", registro[2] != null ? registro[2].toString() : "",
                    registro[3] != null ? TipoIdentificacionEnum.valueOf(registro[3].toString()) : null,
                    registro[4] != null ? registro[4].toString() : "", registro[5] != null ? registro[5].toString() : "",
                    registro[6] != null ? ModalidadEnum.valueOf(registro[6].toString()) : null,
                    registro[7] != null ? registro[7].toString() : "", registro[8] != null ? registro[8].toString() : "",
                    registro[9] != null ? registro[9].toString() : "", registro[10] != null ? registro[10].toString() : "",
                    registro[11] != null ? ResultadoAsignacionEnum.valueOf(registro[11].toString()) : null,
                    convertirObjetoAFecha(registro[12]), convertirObjetoAFecha(registro[13]),
                    registro[14] != null ? PrioridadAsignacionEnum.valueOf(registro[14].toString()) : null,
                    registro[15] != null ? registro[15].toString() : null,
                    registro[16] != null ? registro[16].toString() : "");
            listaHistoricoAsignacion.add(historico);
        }
        logger.debug("Finaliza el servicio de consultarHistoricoAsignacion()");
        return listaHistoricoAsignacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSolicitudVerificacionFovis(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudVerificacionFovisModeloDTO consultarSolicitudVerificacionFovis(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarSolicitudVerificacionFovis(Long)");
        try {
            SolicitudVerificacionFovis sol = (SolicitudVerificacionFovis) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_VERIFICACION_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
            SolicitudVerificacionFovisModeloDTO solicitudDTO = new SolicitudVerificacionFovisModeloDTO();
            solicitudDTO.convertToDTO(sol);
            logger.debug("Finaliza consultarSolicitudVerificacionFovis(Long)");
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudVerificacionFovis(Long)"
                    + interpolate("No se encontraron resultados con el id de solicitud {0} ingresada.", idSolicitudGlobal));
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarEstadoSolicitudVerificacionFovis(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum)
     */
    public void actualizarEstadoSolicitudVerificacionFovis(Long idSolicitudGlobal, EstadoSolicitudVerificacionFovisEnum estadoSolicitud) {
        logger.debug("Se inicia el servicio de actualizarEstadoSolicitudVerificacionFovis(Long, EstadoSolicitudVerificacionFovisEnum)");
        SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO = consultarSolicitudVerificacionFovis(idSolicitudGlobal);
        SolicitudVerificacionFovis solicitudVerificacionFovis = solicitudVerificacionFovisDTO.convertToEntity();

        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudVerificacionFovisEnum.CERRADA.equals(estadoSolicitud)) {
            if (EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_SUBSANADOS.equals(solicitudVerificacionFovis.getEstadoSolicitud())
                    || EstadoSolicitudVerificacionFovisEnum.SIN_HALLAZGOS.equals(solicitudVerificacionFovis.getEstadoSolicitud())) {
                solicitudVerificacionFovis.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
            } else if (EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_NO_SUBSANADOS
                    .equals(solicitudVerificacionFovis.getEstadoSolicitud())) {
                solicitudVerificacionFovis.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
            } else if (EstadoSolicitudVerificacionFovisEnum.DESISTIDA.equals(solicitudVerificacionFovis.getEstadoSolicitud())
                    || EstadoSolicitudVerificacionFovisEnum.CANCELADA.equals(solicitudVerificacionFovis.getEstadoSolicitud())) {
                solicitudVerificacionFovis.getSolicitudGlobal()
                        .setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudVerificacionFovis.getEstadoSolicitud().name()));
            }
        }
        solicitudVerificacionFovis.setEstadoSolicitud(estadoSolicitud);
        solicitudVerificacionFovis.setResultado(EstadoSolicitudVerificacionFovisEnum.CON_HALLAZGOS.getDescripcion());
        entityManager.merge(solicitudVerificacionFovis);
        logger.debug("Finaliza actualizarEstadoSolicitudVerificacionFovis(Long, EstadoSolicitudVerificacionFovisEnum)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * crearActualizarSolicitudVerificacionFovis(com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO)
     */
    @Override
    public SolicitudVerificacionFovisModeloDTO crearActualizarSolicitudVerificacionFovis(
            SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO) {
        logger.debug("Inicia el servicio crearActualizarSolicitudVerificacionFovis");
        SolicitudVerificacionFovis solicitudVerificacionFovis = solicitudVerificacionFovisDTO.convertToEntity();
        SolicitudVerificacionFovis managed = entityManager.merge(solicitudVerificacionFovis);
        solicitudVerificacionFovisDTO.convertToDTO(managed);
        logger.debug("Finaliza el servicio crearActualizarSolicitudVerificacionFovis");
        return solicitudVerificacionFovisDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#
     * validarHogaresConGestionCruceInternoEnProceso(java.lang.String)
     */
    @Override
    public Boolean validarHogaresConGestionCruceInternoEnProceso(String nombreCicloAsignacion) {
        logger.debug("Inicia servicio validarHogaresConGestionCruceInternoEnProceso");

        List<EstadoHogarEnum> listaEstadoHogar = new ArrayList<EstadoHogarEnum>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL);
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO);

        List<PostulacionFOVIS> listaPostulacionFOVIS = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CRUCE_INTERNO_EN_PROCESO,
                        PostulacionFOVIS.class)
                .setParameter("listaEstadoHogar", listaEstadoHogar)
                .setParameter("nombreCicloAsignacion", nombreCicloAsignacion)
                .setParameter("tipoCruce", TipoCruceEnum.INTERNO)
                .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA)
                .getResultList();

        Boolean existenCrucesInternos = Boolean.FALSE;
        if (listaPostulacionFOVIS != null && !listaPostulacionFOVIS.isEmpty()) {
            existenCrucesInternos = Boolean.TRUE;
        }
        logger.debug("Finaliza servicio validarHogaresConGestionCruceInternoEnProceso");
        return existenCrucesInternos;
    }

    /**
     * Obtiene la fecha formateada
     *
     * @param fecha objeto fecha
     * @return fecha formateada
     */
    private Date convertirObjetoAFecha(Object fecha) {
        logger.debug("Se inicia el servicio de convertirObjetoAFecha()");
        Date fechaRadicacion = null;
        try {

            if (fecha != null && !fecha.toString().isEmpty()) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fechaRadicacion = df.parse(fecha.toString());
            }
            logger.debug("Finaliza el servicio de convertirObjetoAFecha()");
            return fechaRadicacion;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio convertirObjetoAFecha()", e);
        }
        return fechaRadicacion;
    }

    @Override
    public Map<String, Object> consultarAvanceCalificacion(Long idCicloAsignacion) {
        logger.debug("Inicia servicio consultarAvanceCalificacion(Long)");
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("idCicloAsignacion", idCicloAsignacion);
        try {
            List<String> listaEstadoHogar = new ArrayList<String>();
            listaEstadoHogar.add(EstadoHogarEnum.HABIL.name());
            listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
            //
            List<String> listaEstadoCruceHogar = new ArrayList<>();
            listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.name());
            listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCES_RATIFICADOS.name());
            // 
            List<String> listaTipoCruces = new ArrayList<>();
            listaTipoCruces.add(TipoCruceEnum.EXTERNO.name());
            listaTipoCruces.add(TipoCruceEnum.INTERNO.name());

            // 
            Object[] cantidadPostulaciones = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_AVANCE_CALIFICACION)
                    .setParameter("listaEstadoHogar", listaEstadoHogar)
                    .setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                    .setParameter("idCicloAsignacion", idCicloAsignacion)
                    .setParameter("tipoCruce", listaTipoCruces)
                    .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getSingleResult();
            if (cantidadPostulaciones != null) {
                mapResult.put("total", cantidadPostulaciones[1]);
                mapResult.put("avance", cantidadPostulaciones[2]);
            }
            logger.debug("Finaliza servicio consultarHogaresAplicanCalificacionPostulacion");
            return mapResult;
        } catch (NoResultException e) {
            return mapResult;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ResultadoHistoricoSolicitudesFovisDTO> consultarHistoricoSolicitudesFOVIS(UriInfo uriInfo, HttpServletResponse response,
                                                                                          String numeroRadicado, TipoSolicitudEnum tipoSolicitud, EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad,
                                                                                          EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion,
                                                                                          EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion, Long fechaExactaRadicacion, Long fechaInicio, Long fechaFin, Long idPostulacion) {
        String ruta = "consultarHistoricoSolicitudesFOVIS(UriInfo, HttpServletResponse, String, TipoSolicitudEnum, EstadoSolicitudNovedadFovisEnum, EstadoSolicitudLegalizacionDesembolsoEnum, EstadoSolicitudPostulacionEnum, Long, Long, Long)::List<ResultadoHistoricoSolicitudesFovisDTO>";
        logger.debug(ConstantesComunes.INICIO_LOGGER + ruta);
        // Lista Respuesta
        List<ResultadoHistoricoSolicitudesFovisDTO> historicoSolicitudes = new ArrayList<>();
        // Consulta 
        StringBuilder querySolicitud = new StringBuilder();
        querySolicitud.append(QueryConstants.CONSULTA_SOLICITUDES_FOVIS_BASE);
        // Builder con paginacion
        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        queryBuilder.addOrderByDefaultParam("-numeroSolicitud");
        // Condicionales consulta
        StringBuilder whereClause = new StringBuilder();
        if (numeroRadicado != null && !numeroRadicado.isEmpty()) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_NRO_RADICADO);
            queryBuilder.addParam(QueriesConstants.NUMERO_RADICADO, numeroRadicado);
        }
        if (tipoSolicitud != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_TIPO_SOLICITUD);
            queryBuilder.addParam(QueriesConstants.TIPO_SOLICITUD, tipoSolicitud.name());
        }
        if (estadoSolicitudNovedad != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_ESTADO_NOVEDAD);
            queryBuilder.addParam(QueriesConstants.ESTADO_SOLICITUD, estadoSolicitudNovedad.name());
        }
        if (estadoSolicitudlegalizacion != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_ESTADO_LEGALIZACION);
            queryBuilder.addParam(QueriesConstants.ESTADO_SOLICITUD, estadoSolicitudlegalizacion.name());
        }
        if (estadoSolicitudPostulacion != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_ESTADO_POSTULACION);
            queryBuilder.addParam(QueriesConstants.ESTADO_SOLICITUD, estadoSolicitudPostulacion.name());
        }
        if (fechaExactaRadicacion != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_FECHA_RADICADO_EXACTA);
            queryBuilder.addParam(QueriesConstants.FECHA_EXACTA_RADICACION, new Date(fechaExactaRadicacion));
        }
        if (fechaInicio != null && fechaFin != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_FECHA_RADICADO_ENTRE);
            queryBuilder.addParam(QueriesConstants.FECHA_INICIO, new Date(fechaInicio));
            queryBuilder.addParam(QueriesConstants.FECHA_FIN, new Date(fechaFin));
        }
        if (idPostulacion != null) {
            verifyAddWhereOrAnd(whereClause);
            whereClause.append(QueryConstants.CONDICION_ID_POSTULACION);
            queryBuilder.addParam(QueriesConstants.ID_POSTULACION, idPostulacion);
        }
        querySolicitud.append(whereClause);
        Map<String, String> hints = new HashMap<>();
        hints.put("idSolicitudGlobal", "solId");
        hints.put("numeroSolicitud", "solNumeroRadicacion");
        hints.put("fechaRadicacion", "solFechaRadicacion");
        hints.put("tipoSolicitud", "tipo");
        hints.put("comunicado", "comIdentificaArchivoComunicado");
        hints.put("estadoSolicitud", "estadoSolicitud");
        queryBuilder.setHints(hints);
        Query query = queryBuilder.createNativeQuery(querySolicitud.toString());
        List<Object[]> solicitudesFovis = query.getResultList();
        for (Object[] obj : solicitudesFovis) {
            ResultadoHistoricoSolicitudesFovisDTO dto = new ResultadoHistoricoSolicitudesFovisDTO();
            BigInteger idSol = (BigInteger) obj[0];
            dto.setIdSolicitudGlobal(idSol.longValue());
            dto.setNumeroSolicitud((String) obj[1]);
            dto.setFechaRadicacion(obj[2] != null ? (Date) obj[2] : null);
            dto.setTipoSolicitud(TipoSolicitudEnum.valueOf((String) obj[3]));
            if (obj[5] != null) {
                dto.setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum.valueOf((String) obj[5]));
                dto.setEstadoSolicitud((String) obj[9]);
            }
            if (obj[6] != null) {
                dto.setEstadoSolicitudLegalizacion(EstadoSolicitudLegalizacionDesembolsoEnum.valueOf((String) obj[6]));
                dto.setEstadoSolicitud((String) obj[9]);
            }
            if (obj[7] != null) {
                dto.setEstadoSolicitudPostulacion(EstadoSolicitudPostulacionEnum.valueOf((String) obj[7]));
                dto.setEstadoSolicitud((String) obj[9]);
            }
            dto.setComunicado((String) obj[8]);
            historicoSolicitudes.add(dto);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + ruta);
        return historicoSolicitudes;
    }

    /**
     * Verifica si hay condicion para agregar AND y en caso contrario agregar WHERE
     *
     * @param queryCondition Condicion de consulta
     */
    private void verifyAddWhereOrAnd(StringBuilder queryCondition) {
        if (queryCondition.length() > 0) {
            queryCondition.append(QueriesConstants.AND_CLAUSE);
        } else {
            queryCondition.append(QueriesConstants.WHERE_CLAUSE);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarInformacionHogar(java.lang.String, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPostulacionFOVISDTO consultarInformacionHogar(String numeroRadicado, Boolean aplicaVista360) {

        SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = new SolicitudPostulacionFOVISDTO();
        logger.debug("Inicia consultarInformacionHogar(" + numeroRadicado + ", " + aplicaVista360 + ")");
        // Información básica de la postulación
        try {
            solicitudPostulacionFOVISDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_POSTULACION_BY_NUMERO_RADICACION_CRUCE, SolicitudPostulacionFOVISDTO.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
        } catch (NoResultException e) {
            logger.debug("Finaliza el servicio consultarRecursosPrioridadPostulacionAsignacion()");
            logger.error("No se encontro registros");
            solicitudPostulacionFOVISDTO = null;
        }

        if (solicitudPostulacionFOVISDTO == null) {
            solicitudPostulacionFOVISDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_POSTULACION_BY_NUMERO_RADICACION, SolicitudPostulacionFOVISDTO.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
        }

        if (aplicaVista360 != null && aplicaVista360 && solicitudPostulacionFOVISDTO.getPostulacion().getInformacionPostulacion() != null) {
            solicitudPostulacionFOVISDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_POSTULACION_BY_NUMERO_RADICACION, SolicitudPostulacionFOVISDTO.class)
                    .setParameter("numeroRadicado", numeroRadicado).getSingleResult();
            return solicitudPostulacionFOVISDTO;
        }

        consultarInformacionRelacionadaPostulacion(solicitudPostulacionFOVISDTO,
                solicitudPostulacionFOVISDTO.getPostulacion().getIdPostulacion());
        BigDecimal valor = solicitudPostulacionFOVISDTO.getPostulacion().getValorCalculadoSFV().setScale(0, RoundingMode.HALF_UP);
        solicitudPostulacionFOVISDTO.getPostulacion().setValorCalculadoSFV(valor);
        logger.debug("Finaliza consultarInformacionHogar(" + numeroRadicado + ", " + aplicaVista360 + ")");
        return solicitudPostulacionFOVISDTO;
    }

    @Override
    public void actualizarEstadoSolicitudAsignacion(Long idSolicitudGlobal, EstadoSolicitudAsignacionEnum estadoSolicitud) {
        logger.debug("Se inicia el servicio de actualizarEstadoSolicitudAsignacion(Long, EstadoSolicitudAsignacionEnum)");
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacionFOVISModeloDTO = consultarSolicitudAsignacionPorSolicitudGlobal(
                idSolicitudGlobal);
        SolicitudAsignacion solicitudAsignacion = solicitudAsignacionFOVISModeloDTO.convertToEntity();

        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudAsignacionEnum.CERRADA.equals(estadoSolicitud)) {
            if (EstadoSolicitudAsignacionEnum.ACTA_ASIGNACION_FOVIS_CON_FIRMAS_CARGADA
                    .equals(solicitudAsignacion.getEstadoSolicitudAsignacion())) {
                solicitudAsignacion.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
            } else if (EstadoSolicitudAsignacionEnum.ASIGNACION_RECHAZADA_CONTROL_INTERNO
                    .equals(solicitudAsignacion.getEstadoSolicitudAsignacion())) {
                solicitudAsignacion.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
            } else if (EstadoSolicitudAsignacionEnum.DESISTIDA.equals(solicitudAsignacion.getEstadoSolicitudAsignacion())
                    || EstadoSolicitudAsignacionEnum.CANCELADA.equals(solicitudAsignacion.getEstadoSolicitudAsignacion())) {
                solicitudAsignacion.getSolicitudGlobal()
                        .setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudAsignacion.getEstadoSolicitudAsignacion().name()));
            }
        }
        solicitudAsignacion.setEstadoSolicitudAsignacion(estadoSolicitud);
        entityManager.merge(solicitudAsignacion);
        logger.debug("Finaliza actualizarEstadoSolicitudAsignacion(Long, EstadoSolicitudAsignacionEnum)");
    }

    @Override
    public InformacionSubsidioFOVISDTO consultarInformacionSubsidioFovis(String numeroRadicado, TipoIdentificacionEnum tipoIdentificacion,
                                                                         String numeroIdentificacion) {
        try {
            InformacionSubsidioFOVISDTO informacionSubsidioFOVISDTO = new InformacionSubsidioFOVISDTO();
            // Se verifica que los campos de texto tenga al menos un caracter diferente a espacio 
            if (numeroIdentificacion != null && numeroIdentificacion.trim().equals("")) {
                numeroIdentificacion = null;
            }
            if (numeroRadicado != null && numeroRadicado.trim().equals("")) {
                numeroRadicado = null;
            }
            if (numeroRadicado == null && numeroIdentificacion == null) {
                // Error faltan los filtros de consulta
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            } else if (tipoIdentificacion == null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
                // Error falta el filtro de identificacion
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            // Se busca siempre por el radicado si se envio como parámetro
            if (numeroRadicado != null) {
                numeroIdentificacion = null;
                tipoIdentificacion = null;
            }

            PostulacionFOVIS postulacionFOVIS = null;
            SolicitudPostulacion solicitudPostulacion = null;
            Persona personaJefe = null;
            CicloAsignacion cicloAsignacion = null;
            BigDecimal codigoVisita = null;
            BigDecimal montoDesembolsado = null;

            // Se realiza la consulta de la información básica de la postulación
            Object[] listInfo = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_BASICA_POSTULACION_FOVIS)
                    .setParameter("tipoIdentificacion", tipoIdentificacion != null ? tipoIdentificacion.name() : null)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("numeroRadicado", numeroRadicado)
                    .setMaxResults(1).getSingleResult();

            if (listInfo != null) {
                postulacionFOVIS = (PostulacionFOVIS) listInfo[0];
                solicitudPostulacion = (SolicitudPostulacion) listInfo[1];
                personaJefe = (Persona) listInfo[2];
                cicloAsignacion = (CicloAsignacion) listInfo[3];
                codigoVisita = (BigDecimal) listInfo[4];
                montoDesembolsado = (BigDecimal) listInfo[5];
                informacionSubsidioFOVISDTO = new InformacionSubsidioFOVISDTO(postulacionFOVIS, solicitudPostulacion, personaJefe,
                        cicloAsignacion, codigoVisita, montoDesembolsado);
                // Se consultan las condiciones especiales del jefe hogar
                // Se consultan las condiciones especiales del jefe hogar
                List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialPersona = consultarCondicionEspecialPersona(
                        personaJefe.getTipoIdentificacion(), personaJefe.getNumeroIdentificacion(), postulacionFOVIS.getIdPostulacion());
                informacionSubsidioFOVISDTO.setCondicionJefeHogar(convertirListaCondicionesTexto(listaCondicionEspecialPersona));
            }
            if (postulacionFOVIS != null && postulacionFOVIS.getIdProyectoVivienda() != null) {
                ProyectoSolucionVivienda proyectoSolucionVivienda = null;
                Persona personaOferente = null;
                String direccionProyecto = null;
                String direccionVivienda = null;
                Object[] infoProyecto = (Object[]) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_BASICA_PROYECTO_POSTULACION_FOVIS)
                        .setParameter("idProyecto", postulacionFOVIS.getIdProyectoVivienda()).getSingleResult();
                // Se consulta la ubicación de vivienda
                if (postulacionFOVIS.getIdUbicacionVivienda() != null) {
                    UbicacionModeloDTO ubicacionVivienda = consultarUbicacionProyectoVivienda(postulacionFOVIS.getIdUbicacionVivienda());
                    direccionVivienda = ubicacionVivienda != null ? ubicacionVivienda.getDireccionFisica() : null;
                }
                if (infoProyecto != null) {
                    proyectoSolucionVivienda = (ProyectoSolucionVivienda) infoProyecto[0];
                    personaOferente = (Persona) infoProyecto[1];
                    direccionProyecto = (String) infoProyecto[2];
                    informacionSubsidioFOVISDTO.setOferente(new InformacionOferenteSubsidioFOVISDTO(proyectoSolucionVivienda,
                            personaOferente, direccionProyecto, direccionVivienda));
                }
            }
            List<AhorroPrevio> listAhorro = null;
            List<RecursoComplementario> listRecursos = null;
            List<IntegranteHogarModeloDTO> listIntegrantes = null;
            if (postulacionFOVIS != null && postulacionFOVIS.getIdPostulacion() != null) {
                listAhorro = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_BASICA_AHORROS_POSTULACION_FOVIS, AhorroPrevio.class)
                        .setParameter("idPostulacion", postulacionFOVIS.getIdPostulacion()).getResultList();
                listRecursos = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_BASICA_RECURSOS_COMPLEMENTARIOS_POSTULACION_FOVIS,
                                RecursoComplementario.class)
                        .setParameter("idPostulacion", postulacionFOVIS.getIdPostulacion()).getResultList();
                listIntegrantes = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_MIEMBROS_HOGAR, IntegranteHogarModeloDTO.class)
                        .setParameter("estadoHogar", EstadoFOVISHogarEnum.ACTIVO)
                        .setParameter("idPostulacion", postulacionFOVIS.getIdPostulacion()).getResultList();
            }
            if (listAhorro != null && !listAhorro.isEmpty()) {
                List<InformacionAporteSubsidioFOVISDTO> listInfoAhorro = new ArrayList<>();
                for (AhorroPrevio ahorroPrevio : listAhorro) {
                    listInfoAhorro.add(new InformacionAporteSubsidioFOVISDTO(ahorroPrevio));
                }
                informacionSubsidioFOVISDTO.setAhorrosPrevios(listInfoAhorro);
            }
            if (listRecursos != null && !listAhorro.isEmpty()) {
                List<InformacionAporteSubsidioFOVISDTO> listInfoRecurso = new ArrayList<>();
                for (RecursoComplementario recursoComplementario : listRecursos) {
                    listInfoRecurso.add(new InformacionAporteSubsidioFOVISDTO(recursoComplementario));
                }
                informacionSubsidioFOVISDTO.setRecursosComplementarios(listInfoRecurso);
            }
            if (listIntegrantes != null && !listIntegrantes.isEmpty()) {
                List<InformacionIntegranteHogarSubsidioFOVISDTO> listIntegranteHogar = new ArrayList<>();
                for (IntegranteHogarModeloDTO integranteHogarModeloDTO : listIntegrantes) {
                    InformacionIntegranteHogarSubsidioFOVISDTO infoIntegrante = new InformacionIntegranteHogarSubsidioFOVISDTO(
                            integranteHogarModeloDTO);
                    // Se consultan las condiciones especiales del integrante hogar
                    List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialPersona = consultarCondicionEspecialPersona(
                            integranteHogarModeloDTO.getTipoIdentificacion(), integranteHogarModeloDTO.getNumeroIdentificacion(), postulacionFOVIS.getIdPostulacion());
                    infoIntegrante.setCondicionIntegrante(convertirListaCondicionesTexto(listaCondicionEspecialPersona));
                    listIntegranteHogar.add(infoIntegrante);
                }
                informacionSubsidioFOVISDTO.setIntegrantesHogar(listIntegranteHogar);
            }
            return informacionSubsidioFOVISDTO;
        } catch (NonUniqueResultException | NoResultException e) {
            return new InformacionSubsidioFOVISDTO();
        }
    }

    /**
     * Organiza la lista enviada por parametro en un texto
     *
     * @param listaCondicionEspecialPersona
     * @return texto de condiciones de persona
     */
    private String convertirListaCondicionesTexto(List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialPersona) {
        StringBuffer condiciones = new StringBuffer();
        if (listaCondicionEspecialPersona != null && !listaCondicionEspecialPersona.isEmpty()) {
            for (CondicionEspecialPersonaModeloDTO condicionEspecialPersona : listaCondicionEspecialPersona) {
                condiciones.append(condicionEspecialPersona.getNombreCondicion().name());
                condiciones.append(",");
            }
        }
        String result = null;
        if (condiciones.length() > 0) {
            result = condiciones.toString().trim().substring(0, condiciones.toString().trim().length() - 1);
        }
        return result;
    }

    @Override
    public void actualizarPostulacionesCalificadasSinCambioCiclo() {
        logger.debug("Inicia servicio actualizarPostulacionesCalificadasSinCambioCiclo");
        EstadoCicloAsignacionEnum estadoCiclo = EstadoCicloAsignacionEnum.CERRADO;

        // Se consultan ciclos de asignacion con predecesor que no se encuentren cerrados 
        List<CicloAsignacion> listCiclos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_CON_PREDECESOR, CicloAsignacion.class)
                .setParameter("estadoCicloAsignacion", estadoCiclo).getResultList();

        if (listCiclos == null || listCiclos.isEmpty()) {
            logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
            return;
        }
        List<Long> listIdCiclos = new ArrayList<>();
        for (CicloAsignacion cicloAsignacion : listCiclos) {
            listIdCiclos.add(cicloAsignacion.getCicloPredecesor());
        }

        // Se consultan las postulaciones asociadas a los ciclos predecesores existentes
        // que se encuentren con resultado de asignacion Calificadas no asignadas 
        // y que el ciclo actual este cerrado
        List<PostulacionFOVIS> listPostulaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACIONES_BY_CICLO_Y_RESULTADO_ASIGNACION, PostulacionFOVIS.class)
                .setParameter("idCicloAsignacion", listIdCiclos)
                .setParameter("resultadoAsignacion", ResultadoAsignacionEnum.ESTADO_CALIFICADO_NO_ASIGNADO)
                .setParameter("estadoCicloAsignacion", estadoCiclo).getResultList();

        if (listPostulaciones == null || listPostulaciones.isEmpty()) {
            logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
            return;
        }
        for (CicloAsignacion cicloAsignacion : listCiclos) {
            verificarPostulacionActualizar(cicloAsignacion.getCicloPredecesor(), cicloAsignacion.getIdCicloAsignacion(),
                    listPostulaciones);
        }
        logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
    }

    @Override
    public void actualizarPostulacionesNovedadesAsociadasCicloPredecesor() {
        logger.debug("Inicia servicio actualizarPostulacionesNovedadesAsociadasCicloPredecesor");
        EstadoCicloAsignacionEnum estadoCiclo = EstadoCicloAsignacionEnum.CERRADO;

        // Se consultan ciclos de asignacion con predecesor que no se encuentren cerrados 
        List<CicloAsignacion> listCiclos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_ASIGNACION_CON_PREDECESOR, CicloAsignacion.class)
                .setParameter("estadoCicloAsignacion", estadoCiclo).getResultList();

        if (listCiclos == null || listCiclos.isEmpty()) {
            logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
            return;
        }
        List<Long> listIdCiclos = new ArrayList<>();
        for (CicloAsignacion cicloAsignacion : listCiclos) {
            listIdCiclos.add(cicloAsignacion.getCicloPredecesor());
        }

        // Se consultan las postulaciones asociadas a los ciclos predecesores existentes
        // que se encuentren con novedades de "Habilitación hogar suspendido por cambio de año" o "Habilitación postulación rechazada" 
        // y que el ciclo actual este cerrado
        List<PostulacionFOVIS> listPostulaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACIONES_BY_CICLO_Y_TIPO_TRANSACCION, PostulacionFOVIS.class)
                .setParameter("idCicloAsignacion", listIdCiclos)
                .setParameter("estadoCicloAsignacion", estadoCiclo).getResultList();

        if (listPostulaciones == null || listPostulaciones.isEmpty()) {
            logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
            return;
        }
        for (CicloAsignacion cicloAsignacion : listCiclos) {
            verificarPostulacionActualizar(cicloAsignacion.getCicloPredecesor(), cicloAsignacion.getIdCicloAsignacion(),
                    listPostulaciones);
        }
        logger.debug("Finaliza servicio actualizarPostulacionesCalificadasSinCambioCiclo");
    }

    /**
     * Verifica si existe en la lista alguna postulacion asociada al ciclo anterior y la actualiza al ciclo nuevo
     *
     * @param idCicloAnterior   Identificador del ciclo anterior
     * @param idCicloNuevo      Identificador ciclo nuevo
     * @param listPostulaciones Lista de Postulaciones
     */
    private void verificarPostulacionActualizar(Long idCicloAnterior, Long idCicloNuevo, List<PostulacionFOVIS> listPostulaciones) {
        for (PostulacionFOVIS postulacionFOVIS : listPostulaciones) {
            if (postulacionFOVIS.getIdCicloAsignacion().equals(idCicloAnterior)) {
                postulacionFOVIS.setIdCicloAsignacion(idCicloNuevo);
                // Mantis:0244896
                // Se ajusta los datos de calificacion y asignación por el cambio de ciclo
                postulacionFOVIS.setPuntaje(null);
                postulacionFOVIS.setFechaCalificacion(null);
                postulacionFOVIS.setPrioridadAsignacion(null);
                postulacionFOVIS.setValorAsignadoSFV(null);
                postulacionFOVIS.setIdDocumento(null);
                postulacionFOVIS.setIdSolicitudAsignacion(null);
                entityManager.merge(postulacionFOVIS);
            }
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPostulacionFOVISDTO consultarInfoPostulacion(Long idPostulacion) {
        logger.debug("Inicia consultarInfoPostulacion(" + idPostulacion + ")");
        SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = new SolicitudPostulacionFOVISDTO();
        // Información básica de la postulación
        solicitudPostulacionFOVISDTO = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_POSTULACION_BY_ID, SolicitudPostulacionFOVISDTO.class)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        consultarInformacionRelacionadaPostulacion(solicitudPostulacionFOVISDTO, idPostulacion);
        // Formatear lista chequeo hogar
        formatearListaChequeo(solicitudPostulacionFOVISDTO.getListaChequeo());
        // Formatear lista chequeo jefe
        formatearListaChequeo(solicitudPostulacionFOVISDTO.getPostulacion().getListaChequeoJefeHogar());
        // Fomratear lista chequeo integrante
        if (!solicitudPostulacionFOVISDTO.getIntegrantesHogar().isEmpty()) {
            for (IntegranteHogarModeloDTO integrante : solicitudPostulacionFOVISDTO.getIntegrantesHogar()) {
                formatearListaChequeo(integrante.getListaChequeo());
            }
        }
        logger.debug("Finaliza consultarInfoPostulacion(" + idPostulacion + ")");
        return solicitudPostulacionFOVISDTO;
    }

    /**
     * Le da formato a la lista de chequeo elimnando el campo de ayuda y los item no diligenciados
     *
     * @param listaConsultada Lista de chequeo consultada
     * @return Lista de chequeo formateada
     */
    private void formatearListaChequeo(ListaChequeoDTO listaConsultada) {
        if (listaConsultada != null && listaConsultada.getListaChequeo() != null && !listaConsultada.getListaChequeo().isEmpty()) {
            List<ItemChequeoDTO> itemsDiligenciados = new ArrayList<>();
            for (ItemChequeoDTO item : listaConsultada.getListaChequeo()) {
                if (item.getIdentificadorDocumento() != null) {
                    item.setTextoAyuda(null);
                    itemsDiligenciados.add(item);
                }
            }
            // Solo se devuelven los item diligenciados
            listaConsultada.setListaChequeo(itemsDiligenciados);
        }
    }

    /**
     * Se consulta la información relacionada con la postulación
     *
     * @param solicitudPostulacionFOVISDTO Info solicitud postulación
     * @param idPostulacion                Identificador postulación
     */
    private void consultarInformacionRelacionadaPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO, Long idPostulacion) {
        // Información para la consulta de lista de chequeo
        Long idSolicitud = solicitudPostulacionFOVISDTO.getIdSolicitud();
        if (solicitudPostulacionFOVISDTO != null && solicitudPostulacionFOVISDTO.getPostulacion() != null
                && solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar() != null) {
            // Condiciones especiales del jefe hogar
            List<NombreCondicionEspecialEnum> listCondiciones = consultarCondionEspecialPersona(
                    solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar().getNumeroIdentificacion(),
                    solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar().getTipoIdentificacion(), idPostulacion);
            solicitudPostulacionFOVISDTO.setCondicionesEspeciales(listCondiciones);

            if (solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar().getIngresosMensuales() == null) {
                Long jefeHogar = solicitudPostulacionFOVISDTO.getPostulacion().getIdJefeHogar();
                JefeHogar jefe = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_JEFE_HOGAR_BY_ID, JefeHogar.class)
                        .setParameter("idJefeHogar", jefeHogar).getSingleResult();
                solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar().setIngresosMensuales(jefe.getIngresosMensuales());
            }

            // Lista chequeo Jefe
            solicitudPostulacionFOVISDTO.getPostulacion().setListaChequeoJefeHogar(consultarListaChequeo(idSolicitud,
                    solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar(), ClasificacionEnum.JEFE_HOGAR));
            // Lista chequeo hogar
            solicitudPostulacionFOVISDTO.setListaChequeo(consultarListaChequeo(idSolicitud,
                    solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar(), ClasificacionEnum.HOGAR));
        }
        // Información oferente
        OferenteModeloDTO oferenteModeloDTO = consultarInfoOferentePostulacion(idPostulacion);
        if (oferenteModeloDTO != null) {
            solicitudPostulacionFOVISDTO.setOferente(new OferenteDTO(oferenteModeloDTO));
        }
        /*// Información proveedor
    comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza
        List<LegalizacionDesembolosoProveedorModeloDTO> proveedorModeloDTOList = consultarInfoProveedorPostulacion(solicitudPostulacionFOVISDTO.getNumeroRadicacion());
        if (proveedorModeloDTOList != null) {
             solicitudPostulacionFOVISDTO.setLegalizacionProveedor(proveedorModeloDTOList);
        }*/
        // Información Proyecto
        ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaModeloDTO = consultarInfoProyectoPostulacion(idPostulacion);
        if (proyectoSolucionViviendaModeloDTO != null) {
            proyectoSolucionViviendaModeloDTO
                    .setMedioPago(consultarMedioPagoProyectoVivienda(proyectoSolucionViviendaModeloDTO.getIdProyectoVivienda()));
            solicitudPostulacionFOVISDTO.getPostulacion().setProyectoSolucionVivienda(proyectoSolucionViviendaModeloDTO);
        }
        // Información ubcación vivienda vivienda
        if (solicitudPostulacionFOVISDTO.getPostulacion().getUbicacionVivienda() != null
                && solicitudPostulacionFOVISDTO.getPostulacion().getUbicacionVivienda().getIdUbicacion() != null) {
            UbicacionModeloDTO ubicacionProyectoDTO = consultarUbicacionProyectoVivienda(
                    solicitudPostulacionFOVISDTO.getPostulacion().getUbicacionVivienda().getIdUbicacion());
            solicitudPostulacionFOVISDTO.getPostulacion().setUbicacionVivienda(ubicacionProyectoDTO);
        }
        // Información de ahorros previos
        List<AhorroPrevioModeloDTO> listAhorro = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_AHORRO_PREVIO_BY_ID_POSTULACION, AhorroPrevioModeloDTO.class)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        convertirAhorrosPrevios(listAhorro, solicitudPostulacionFOVISDTO.getPostulacion());
        // Información de recursos complementarios
        List<RecursoComplementarioModeloDTO> listRecursos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_RECURSOS_COMPLEMENT_BY_ID_POSTULACION,
                        RecursoComplementarioModeloDTO.class)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        convertirRecursosComplementarios(listRecursos, solicitudPostulacionFOVISDTO.getPostulacion());
        // Lista de inhabilidades del hogar
        List<InhabilidadSubsidioFovis> listInhabilidades = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INHABILIDADES_PERSONA_POSTULACION_BY_ID, InhabilidadSubsidioFovis.class)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        // Lista de integrantes hogar
        List<IntegranteHogarModeloDTO> listIntegrantes = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MIEMBROS_HOGAR, IntegranteHogarModeloDTO.class)
                .setParameter("estadoHogar", EstadoFOVISHogarEnum.ACTIVO).setParameter("idPostulacion", idPostulacion).getResultList();
        if (listIntegrantes != null && !listIntegrantes.isEmpty()) {
            for (IntegranteHogarModeloDTO integranteHogarModeloDTO : listIntegrantes) {
                // Condiciones especiales del integrante hogar
                List<NombreCondicionEspecialEnum> listCondiciones = consultarCondionEspecialPersona(
                        integranteHogarModeloDTO.getNumeroIdentificacion(), integranteHogarModeloDTO.getTipoIdentificacion(), idPostulacion);
                integranteHogarModeloDTO.setCondicionesEspeciales(listCondiciones);
                integranteHogarModeloDTO.setListaChequeo(consultarListaChequeo(idSolicitud, integranteHogarModeloDTO, integranteHogarModeloDTO.getTipoIntegranteHogar()));
                asociarInhabilidad(listInhabilidades, integranteHogarModeloDTO, null);
            }
        }
        solicitudPostulacionFOVISDTO.setIntegrantesHogar(listIntegrantes);
        // Asociar inhabilidad Jefe
        asociarInhabilidad(listInhabilidades, null, solicitudPostulacionFOVISDTO.getPostulacion().getJefeHogar());
    }

    /**
     * Consulta la información de la lista de chequeo por solicitud, persona y clasificación
     *
     * @param idSolicitud   Identificador de la solicitud global
     * @param idPersona     Identificador de la persona asociada en la lista de chequeo
     * @param clasificacion Clasificación de la persona
     * @return Información de la lista de chequeo diligenciada
     */
    private ListaChequeoDTO consultarListaChequeo(Long idSolicitud, PersonaModeloDTO personaModeloDTO, ClasificacionEnum clasificacion) {
        ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();

        List<ItemChequeoDTO> listItemChequeo = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_LISTA_CHEQUEO_BY_SOLICITUD_PERSONA_CLASIFICACION, ItemChequeoDTO.class)
                .setParameter("idSolicitud", idSolicitud).setParameter("idPersona", personaModeloDTO.getIdPersona())
                .setParameter("clasificacion", clasificacion).getResultList();

        for (ItemChequeoDTO itemChequeoDTO : listItemChequeo) {
            itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
        }

        listaChequeoDTO.setIdSolicitudGlobal(idSolicitud);
        listaChequeoDTO.setNumeroIdentificacion(personaModeloDTO.getNumeroIdentificacion());
        listaChequeoDTO.setTipoIdentificacion(personaModeloDTO.getTipoIdentificacion());
        listaChequeoDTO.setListaChequeo(listItemChequeo);
        return listaChequeoDTO;
    }

    /**
     * Realiza la asociación de inhabilidades al integrante o jefe de hogar si existe la inhabilidad
     *
     * @param listInhabilidades        Lista de inhabilidades de los integrantes de hogar
     * @param integranteHogarModeloDTO Información de integrante de hogar (es nulo cuando se realiza asociación al jefe)
     * @param jefeHogarModeloDTO       Información de jefe de hogar (es nulo cuando se realiza asociación al integrante)
     */
    private void asociarInhabilidad(List<InhabilidadSubsidioFovis> listInhabilidades, IntegranteHogarModeloDTO integranteHogarModeloDTO,
                                    JefeHogarModeloDTO jefeHogarModeloDTO) {
        if (listInhabilidades.isEmpty()) {
            return;
        }
        for (InhabilidadSubsidioFovis inhabilidadSubsidioFovis : listInhabilidades) {
            // Jefe Hogar
            if (jefeHogarModeloDTO != null && jefeHogarModeloDTO.getIdJefeHogar().equals(inhabilidadSubsidioFovis.getIdJefeHogar())) {
                jefeHogarModeloDTO.setInhabilitadoParaSubsidio(inhabilidadSubsidioFovis.getInhabilitadoParaSubsidio());
                break;
            }
            // Integrante Hogar
            else if (integranteHogarModeloDTO != null
                    && integranteHogarModeloDTO.getIdIntegranteHogar().equals(inhabilidadSubsidioFovis.getIdIntegranteHogar())) {
                integranteHogarModeloDTO.setInhabilitadoParaSubsidio(inhabilidadSubsidioFovis.getInhabilitadoParaSubsidio());
                break;
            }
        }
    }

    /**
     * Consulta la información registrada del oferente asociado al proyecto de la postulación
     *
     * @param idPostulacion Identificador postulación consultada
     * @return Información Oferente
     */
    private OferenteModeloDTO consultarInfoOferentePostulacion(Long idPostulacion) {
        try {
            OferenteModeloDTO oferenteModeloDTO = new OferenteModeloDTO();
            Object[] infoOferente = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_OFERENTE_BY_ID_POSTULACION)
                    .setParameter("idPostulacion", idPostulacion).getSingleResult();
            if (infoOferente == null) {
                return null;
            }
            oferenteModeloDTO.convertToDTO((Persona) infoOferente[1], (Empresa) infoOferente[2], (Oferente) infoOferente[0]);
            return oferenteModeloDTO;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * Consulta la información registrada del proveedor asociado al proyecto de la postulación - glpi 49270
     *
     * @param idPostulacion Identificador postulación consultada
     * @return Información proveedor
     */// 49270BORRADOKT comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza
 /*   private List<LegalizacionDesembolosoProveedorModeloDTO> consultarInfoProveedorPostulacion(String numeroRadicacion) {
        try {
            List<LegalizacionDesembolosoProveedorModeloDTO> proveedorModeloDTO = new ArrayList<>();
            List<Object[]> infoProveedor = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_PROVEEDOR_BY_ID_POSTULACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();
            if (infoProveedor == null) {
                return null;
            }
            proveedorModeloDTO = mapProveedorObject(infoProveedor);
            return proveedorModeloDTO;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
*/
    public List<LegalizacionDesembolosoProveedorModeloDTO> mapProveedorObject(List<Object[]> resultado) {
        logger.debug(interpolate("Inicia mapProveedorObject()"));
        List<LegalizacionDesembolosoProveedorModeloDTO> listLegalizacionDesembolosoProveedorModeloDTO = new ArrayList<>();
        boolean existe = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Object[] obj : resultado) {
            LegalizacionDesembolosoProveedorModeloDTO objLocal = new LegalizacionDesembolosoProveedorModeloDTO();
            PersonaModeloDTO persona = new PersonaModeloDTO();
            ProveedorModeloDTO proveedor = new ProveedorModeloDTO();
            BancoModeloDTO banco = new BancoModeloDTO();

            BigDecimal ValorADesembolsar = new BigDecimal(String.valueOf(obj[5] == null ? "0" : obj[5].toString()));
            Date fecha = null;

            objLocal.setIdlegalizacionDesembolosoProveedor(Long.valueOf(String.valueOf(obj[0] == null ? "" : obj[0].toString())));
            objLocal.setIdProveedor(Long.valueOf(String.valueOf(obj[1] == null ? "0" : obj[1].toString())));
            objLocal.setIdPersona(Long.valueOf(String.valueOf(obj[2] == null ? "0" : obj[2].toString())));
            objLocal.setSldId(Long.valueOf(String.valueOf(obj[3] == null ? "0" : obj[3].toString())));
            objLocal.setNumeroRadicacion(String.valueOf(String.valueOf(obj[4] == null ? null : obj[4].toString())));
            objLocal.setValorADesembolsar(ValorADesembolsar);
            objLocal.setPorcentajeASembolsar(Double.valueOf(String.valueOf(obj[6] == null ? "0" : obj[6].toString())));
            String fechaString = obj[7] != null ? obj[7].toString() : null;

            if (fechaString != null) {
                try {
                    objLocal.setFecha(format.parse(fechaString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            persona.setNumeroIdentificacion(String.valueOf(obj[8] == null ? "0" : obj[8].toString()));
            persona.setRazonSocial(String.valueOf(obj[9] == null ? "" : obj[9].toString()));
            if (obj[10] != null) {
                persona.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(obj[10].toString()));
            }
            persona.setPrimerNombre(String.valueOf(obj[11] == null ? "" : obj[11].toString()));
            persona.setSegundoNombre(String.valueOf(obj[12] == null ? "" : obj[12].toString()));
            persona.setPrimerApellido(String.valueOf(obj[13] == null ? "" : obj[13].toString()));
            persona.setSegundoApellido(String.valueOf(obj[14] == null ? "" : obj[14].toString()));

            String estadoOferente = String.valueOf(obj[15] == null ? "" : obj[15].toString());

            if (estadoOferente != null) {
                if (estadoOferente.equalsIgnoreCase(EstadoOferenteEnum.ACTIVO.toString())) {
                    proveedor.setEstado(EstadoOferenteEnum.ACTIVO);
                } else {
                    proveedor.setEstado(EstadoOferenteEnum.INACTIVO);
                }
            }

            proveedor.setCuentaBancaria(obj[16] == null ? null : (Boolean) obj[16]);

            banco.setCodigoPILA(String.valueOf(obj[17] == null ? "" : obj[17].toString()));
            banco.setNombre(String.valueOf(obj[18] == null ? "" : obj[18].toString()));
            banco.setMedioPago(obj[19] == null ? null : (Boolean) obj[19]);
            banco.setCodigo(String.valueOf(obj[20] == null ? "" : obj[20].toString()));

            if (obj[21] != null) {
                proveedor.setTipoCuenta(TipoCuentaEnum.obtenerTipoCuentaEnum(obj[21].toString()));
            }

            proveedor.setNumeroCuenta(String.valueOf(obj[22] == null ? "" : obj[22].toString()));
            if (obj[23] != null) {
                proveedor.setTipoIdentificacionTitular(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(obj[23].toString()));
            }
            proveedor.setNumeroIdentificacionTitular(String.valueOf(obj[24] == null ? "" : obj[24].toString()));
            proveedor.setDigitoVerificacionTitular(obj[25] == null ? null : (Short) obj[25]);
            proveedor.setNombreTitularCuenta(String.valueOf(obj[26] == null ? "" : obj[26].toString()));
            proveedor.setConcepto(obj[27] == null ? null : (Integer) obj[27]);

            proveedor.setBanco(banco);
            proveedor.setPersona(persona);
            objLocal.setProveedor(proveedor);

            listLegalizacionDesembolosoProveedorModeloDTO.add(objLocal);
        }

        logger.debug(interpolate("Finaliza mapProveedorObject()"));
        return listLegalizacionDesembolosoProveedorModeloDTO;
    }

    /**
     * Consulta la información registrada del proyecto asociado a la postulación
     *
     * @param idPostulacion Identificador postulación consultada
     * @return Información Proyecto solución Vivienda
     */
    private ProyectoSolucionViviendaModeloDTO consultarInfoProyectoPostulacion(Long idPostulacion) {
        try {
            ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO = new ProyectoSolucionViviendaModeloDTO();
            // Se consulta la información
            Object[] infoProyecto = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_INFORMACION_PROYECTO_BY_ID_POSTULACION)
                    .setParameter("idPostulacion", idPostulacion).getSingleResult();
            if (infoProyecto == null) {
                return null;
            }
            ProyectoSolucionVivienda proyectoSolucionVivienda = (ProyectoSolucionVivienda) infoProyecto[0];
            Ubicacion ubicacionProyecto = (Ubicacion) infoProyecto[1];
            proyectoSolucionViviendaDTO.convertToDTO(proyectoSolucionVivienda, ubicacionProyecto, null);
            return proyectoSolucionViviendaDTO;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * Consulta las condiciones especiales registradas a cada persona
     *
     * @param numeroIdentificacion Numero identificación
     * @param tipoIdentificacion   Tipo Identificación
     * @return Lista de condiciones especiales
     */
    private List<NombreCondicionEspecialEnum> consultarCondionEspecialPersona(String numeroIdentificacion,
                                                                              TipoIdentificacionEnum tipoIdentificacion, Long idPostulacion) {
        // Se consulta las condiciones especiales asociadas a un jefe de hogar
        List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialPersona = consultarCondicionEspecialPersona(tipoIdentificacion,
                numeroIdentificacion, idPostulacion);

        List<NombreCondicionEspecialEnum> nombreCondicionesPersona = new ArrayList<>();
        if (listaCondicionEspecialPersona != null && !listaCondicionEspecialPersona.isEmpty()) {
            for (CondicionEspecialPersonaModeloDTO condicionEspecialPersonaModeloDTO : listaCondicionEspecialPersona) {
                nombreCondicionesPersona.add(condicionEspecialPersonaModeloDTO.getNombreCondicion());
            }
        } else {
            nombreCondicionesPersona = null;
        }
        return nombreCondicionesPersona;
    }

    /**
     * Convierte la información de los ahorros previos al formato de la postulación
     *
     * @param listAhorro       Lista de ahorros
     * @param postulacionFovis Información de la postulación
     */
    private void convertirAhorrosPrevios(List<AhorroPrevioModeloDTO> listAhorro, PostulacionFOVISModeloDTO postulacionFovis) {
        if (listAhorro == null || listAhorro.isEmpty()) {
            return;
        }
        // Se ajusta cada tipo de ahorro en la información de la postulación
        for (AhorroPrevioModeloDTO ahorroPrevioModeloDTO : listAhorro) {
            switch (ahorroPrevioModeloDTO.getNombreAhorro()) {
                case AHORRO_PROGRAMADO:
                    postulacionFovis.setAhorroProgramado(ahorroPrevioModeloDTO);
                    break;
                case AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA:
                    postulacionFovis.setAhorroProgramadoContractual(ahorroPrevioModeloDTO);
                    break;
                case APORTES_PERIODICOS:
                    postulacionFovis.setAportesPeriodicos(ahorroPrevioModeloDTO);
                    break;
                case CESANTIAS_INMOVILIZADAS:
                    postulacionFovis.setCesantiasInmovilizadas(ahorroPrevioModeloDTO);
                    break;
                case CUOTA_INICIAL:
                    postulacionFovis.setCuotaInicial(ahorroPrevioModeloDTO);
                    break;
                case CUOTAS_PAGADAS:
                    postulacionFovis.setCuotasPagadas(ahorroPrevioModeloDTO);
                    break;
                case VALOR_LOTE_O_TERRENO_PROPIO:
                    postulacionFovis.setValorLoteTerreno(ahorroPrevioModeloDTO);
                    break;
                case VALOR_LOTE_OPV:
                    postulacionFovis.setValorLoteOPV(ahorroPrevioModeloDTO);
                    break;
                case VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL:
                    postulacionFovis.setValorLoteSubsidioMunicipal(ahorroPrevioModeloDTO);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Convierte la información de los recursos complementarios al formato de la postulación
     *
     * @param listRecursos     Lista de recursos
     * @param postulacionFovis Información de la postulación
     */
    private void convertirRecursosComplementarios(List<RecursoComplementarioModeloDTO> listRecursos,
                                                  PostulacionFOVISModeloDTO postulacionFovis) {
        if (listRecursos == null || listRecursos.isEmpty()) {
            return;
        }
        // Se organiza la informacion de los recursos complementarios en la postulación
        for (RecursoComplementarioModeloDTO recursoComplementarioModeloDTO : listRecursos) {
            switch (recursoComplementarioModeloDTO.getNombre()) {
                case AHORRO_OTRAS_MODALIDADES:
                    postulacionFovis.setAhorroOtrasModalidades(recursoComplementarioModeloDTO);
                    break;
                case APORTES_ENTE_TERRITORIAL:
                    postulacionFovis.setAportesEnteTerritorial(recursoComplementarioModeloDTO);
                    break;
                case APORTES_SOLIDARIOS:
                    postulacionFovis.setAportesSolidarios(recursoComplementarioModeloDTO);
                    break;
                case CESANTIAS_NO_INMOVILIZADAS:
                    postulacionFovis.setCesantiasNoInmovilizadas(recursoComplementarioModeloDTO);
                    break;
                case CREDITO_APROBADO:
                    postulacionFovis.setCreditoAprobado(recursoComplementarioModeloDTO);
                    break;
                case DONACION_OTRAS_ENTIDADES:
                    postulacionFovis.setDonacionOtrasEntidades(recursoComplementarioModeloDTO);
                    break;
                case EVALUACION_CREDITICIA:
                    postulacionFovis.setEvaluacionCrediticia(recursoComplementarioModeloDTO);
                    break;
                case OTROS_RECURSOS:
                    postulacionFovis.setOtrosRecursos(recursoComplementarioModeloDTO);
                    break;
                case VALOR_AVANCE_OBRA:
                    postulacionFovis.setValorAvanceObra(recursoComplementarioModeloDTO);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarCalificacion(com.asopagos.dto.CalificacionPostulacionDTO)
     */
    @Override
    public CalificacionPostulacionDTO crearActualizarCalificacion(CalificacionPostulacionDTO calificacionPostulacionDTO) {
        logger.debug("Inicia el servicio crearActualizarCalificacion");
        CalificacionPostulacion calificacionPostulacion = calificacionPostulacionDTO.convertToEntity();
        CalificacionPostulacion managed = entityManager.merge(calificacionPostulacion);
        logger.debug("Finaliza el servicio crearActualizarCalificacion");
        return new CalificacionPostulacionDTO(managed);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarPostulacionAsignacion(com.asopagos.dto.PostulacionAsignacionDTO)
     */
    @Override
    public PostulacionAsignacionDTO crearActualizarPostulacionAsignacion(PostulacionAsignacionDTO postulacionAsignacionDTO) {
        logger.debug("Inicia el servicio crearActualizarPostulacionAsignacion(PostulacionAsignacionDTO)");
        PostulacionAsignacion postulacionAsignacion = postulacionAsignacionDTO.convertToEntity();
        PostulacionAsignacion managed = entityManager.merge(postulacionAsignacion);
        logger.debug("Finaliza el servicio crearActualizarPostulacionAsignacion(PostulacionAsignacionDTO)");
        return new PostulacionAsignacionDTO(managed);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#crearActualizarListaPostulacionAsignacion(java.util.List)
     */
    @Override
    public void crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO> listPostulaciones) {
        logger.debug("Inicia el servicio crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO>)");
        for (PostulacionAsignacionDTO postulacionAsignacionDTO : listPostulaciones) {
            crearActualizarPostulacionAsignacion(postulacionAsignacionDTO);
        }
        logger.debug("Finaliza el servicio crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO>)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarListaPostulacionAsignacion(java.lang.Long)
     */
    @Override
    public List<PostulacionAsignacionDTO> consultarListaPostulacionAsignacion(Long idSolicitudAsignacion) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACIONES_RELACIONADAS_ASIGNACION_BY_ID_SOL_ASIGNACION, PostulacionAsignacionDTO.class)
                .setParameter("idSolicitudAsignacion", idSolicitudAsignacion).getResultList();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarTopeParametrizadoFovis()
     */
    @Override
    public Map<String, BigDecimal> consultarTopeParametrizadoFovis() {
        logger.debug("Inicia el servicio consultarTopeParametrizadoFovis()");
        Map<String, BigDecimal> params = new HashMap<>();

        //Se consulta el valor parametrizado del Salario minimo
        String salarioMinimoSt = (String) CacheManager.getParametro(ParametrosSistemaConstants.SMMLV);
        BigDecimal salarioMinimo = new BigDecimal(salarioMinimoSt);
        params.put("SMMLV", salarioMinimo);

        //Se consulta el valor de salarios tope parametrizados para FOVIS
        ParametrizacionFOVIS parametrizacion;
        try {
            parametrizacion = (ParametrizacionFOVIS) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_FOVIS_POR_NOMBRE)
                    .setParameter("nombre", ParametroFOVISEnum.TOPE_VALOR_INGRESOS_HOGAR).getSingleResult();
        } catch (NoResultException e) {
            logger.debug("Finaliza el servicio consultarTopeParametrizadoFovis()");
            logger.error("No se ha establecido un valor para el parametro TOPE_VALOR_INGRESOS_HOGAR");
            parametrizacion = new ParametrizacionFOVIS();
        }

        BigDecimal numeroSalariostope = parametrizacion.getValorNumerico();
        params.put("NumeroSalariosTope", numeroSalariostope);

        //Se realiza la multiplicacion de los valores obtenidos
        BigDecimal valorDineroTope = salarioMinimo.multiply(numeroSalariostope);
        params.put("ValorDineroTope", valorDineroTope);

        logger.debug("Finaliza el servicio consultarTopeParametrizadoFovis()");
        return params;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarMiembrosHogarPostulacion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<MiembroHogarDTO> consultarMiembrosHogarPostulacion(Long idPostulacion) throws IOException {
        logger.debug(Interpolator.interpolate("Inicia consultarMiembrosHogarPostulacion({0})", idPostulacion));
        List<IntegranteHogarModeloDTO> integrantesHogarDTO = new ArrayList<>();
        // Consulta la lista de miembros de hogar (incluye el jefe) actuales
        List<MiembroHogarDTO> listaMiembrosActual = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_MIEMBROS_HOGAR_BY_ID_POSTULACION, MiembroHogarDTO.class)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        // Se consulta la información basica de la postulación
        PostulacionFOVIS postulacionFovis = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACION_FOVIS_POR_ID, PostulacionFOVIS.class)
                .setParameter("idPostulacion", idPostulacion).getSingleResult();
        ObjectMapper mapper = new ObjectMapper();
        SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = mapper.readValue(postulacionFovis.getInformacionAsignacion(),
                SolicitudPostulacionFOVISDTO.class);
        if (solicitudPostulacionFOVISDTO.getIntegrantesHogar() == null || solicitudPostulacionFOVISDTO.getIntegrantesHogar().isEmpty()) {
            List<IntegranteHogar> integrantesHogar = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTES_HOGAR_POR_POSTULACION)
                    .setParameter("idPostulacion", idPostulacion).getResultList();
            if (integrantesHogar != null && !integrantesHogar.isEmpty()) {
                for (IntegranteHogar integranteHogar : integrantesHogar) {
                    IntegranteHogarModeloDTO personaDetalleModeloDTO = new IntegranteHogarModeloDTO();
                    personaDetalleModeloDTO.convertToDTO(integranteHogar);
                    integrantesHogarDTO.add(personaDetalleModeloDTO);
                }
            }
        } else {
            integrantesHogarDTO = solicitudPostulacionFOVISDTO.getIntegrantesHogar();
        }

        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = solicitudPostulacionFOVISDTO.getPostulacion();
        logger.debug(Interpolator.interpolate("Finaliza consultarMiembrosHogarPostulacion({0})", idPostulacion));
        return construirListaMiembros(listaMiembrosActual, integrantesHogarDTO,
                postulacionFOVISModeloDTO.getJefeHogar());
    }

    /**
     * Construye la lista de miembros de hogar con la información de la asignación
     *
     * @param listaMiembrosActual Lista de miembros de hogar actuales
     * @param integrantesHogar    Lista de integrantes de hogar en la asignacion
     * @param jefeHogar           Información del jefe de hogar en la asignación
     * @return Lista con información de miembros o lista vacia
     */
    private List<MiembroHogarDTO> construirListaMiembros(List<MiembroHogarDTO> listaMiembrosActual,
                                                         List<IntegranteHogarModeloDTO> integrantesHogar, JefeHogarModeloDTO jefeHogar) {
        List<MiembroHogarDTO> listaMiembros = new ArrayList<>();
        for (MiembroHogarDTO miembroHogarDTO : listaMiembrosActual) {
            if (ClasificacionEnum.JEFE_HOGAR.equals(miembroHogarDTO.getParentesco())) {
                miembroHogarDTO.setEstadoHogarAsignacion(jefeHogar.getEstadoHogar());
                miembroHogarDTO.setIngresosMensualesAsignacion(jefeHogar.getIngresosMensuales());
                listaMiembros.add(miembroHogarDTO);
            } else {
                construirIntegranteHogar(integrantesHogar, listaMiembros, miembroHogarDTO);
            }
        }
        return listaMiembros;
    }

    /**
     * Coloca la información del integrante al momento de la asignación
     *
     * @param integrantesHogar Integrantes hogar en la asignación
     * @param listaMiembros    Lista de miembros respuesta
     * @param miembroHogarDTO  Miembro hogar a agregar
     */
    private void construirIntegranteHogar(List<IntegranteHogarModeloDTO> integrantesHogar, List<MiembroHogarDTO> listaMiembros,
                                          MiembroHogarDTO miembroHogarDTO) {
        for (IntegranteHogarModeloDTO integranteHogar : integrantesHogar) {
            if (miembroHogarDTO.getIdIntegranteHogar().equals(integranteHogar.getIdIntegranteHogar())) {
                miembroHogarDTO.setEstadoHogarAsignacion(integranteHogar.getEstadoHogar());
                miembroHogarDTO.setIngresosMensualesAsignacion(integranteHogar.getIngresosMensuales());
                listaMiembros.add(miembroHogarDTO);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarIngresosMiembrosHogar(java.util.List)
     */
    @Override
    public void actualizarIngresosMiembrosHogar(List<MiembroHogarDTO> listaMiembrosHogar) {
        logger.debug("Inicia actualizarIngresosMiembrosHogar(List<MiembroHogarDTO>)");
        Long idJefeHogar = null;
        List<Long> listIdIntegranteHogar = new ArrayList<>();
        for (MiembroHogarDTO miembroHogarDTO : listaMiembrosHogar) {
            if (ClasificacionEnum.JEFE_HOGAR.equals(miembroHogarDTO.getParentesco())) {
                idJefeHogar = miembroHogarDTO.getIdJefeHogar();
            } else {
                listIdIntegranteHogar.add(miembroHogarDTO.getIdIntegranteHogar());
            }
        }

        // Se consulta el jefe de hogar
        JefeHogar jefe = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_JEFE_HOGAR_BY_ID, JefeHogar.class)
                .setParameter("idJefeHogar", idJefeHogar).getSingleResult();


        // Se actualizan los ingresos
        for (MiembroHogarDTO miembroHogarDTO : listaMiembrosHogar) {
            if (ClasificacionEnum.JEFE_HOGAR.equals(miembroHogarDTO.getParentesco())) {
                jefe.setIngresosMensuales(miembroHogarDTO.getIngresosMensuales());
            } else {
                // Se consulta los integrantes de hogar
                List<IntegranteHogar> listaIntegrantes = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_LISTA_INTEGRANTE_HOGAR_BY_LIST_ID, IntegranteHogar.class)
                        .setParameter("listIdIntegrante", listIdIntegranteHogar).getResultList();
                actualizarIngresosIntegrante(listaIntegrantes, miembroHogarDTO);
            }
        }

        logger.debug("Finaliza actualizarIngresosMiembrosHogar(List<MiembroHogarDTO>)");
    }

    /**
     * Realiza la actulización de los ingresos del integrante de hogar
     *
     * @param listaIntegrantes Lista de integrantes hogar a actualizar
     * @param miembroHogarDTO  Información del integrante actualizada
     */
    private void actualizarIngresosIntegrante(List<IntegranteHogar> listaIntegrantes, MiembroHogarDTO miembroHogarDTO) {
        for (IntegranteHogar integranteHogar : listaIntegrantes) {
            if (miembroHogarDTO.getIdIntegranteHogar().equals(integranteHogar.getIdIntegranteHogar())) {
                integranteHogar.setSalarioMensual(miembroHogarDTO.getIngresosMensuales());
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarJsonPostulacion(java.lang.Long,
     * com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO)
     */
    @Override
    public void actualizarJsonPostulacion(Long idPostulacion, SolicitudPostulacionFOVISDTO solicitudPostulacion) throws IOException {
        logger.debug("Inicia actualizarJsonPostulacion(" + idPostulacion + ", SolicitudPostulacionFOVISDTO)");
        PostulacionFOVISModeloDTO postulacionDTO = consultarPostulacionFOVIS(idPostulacion);
        // Garantizar que no se envie el valor del campo información de postulación y evitar duplicidad
        solicitudPostulacion.getPostulacion().setInformacionPostulacion(null);
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(solicitudPostulacion);

        PostulacionFOVIS postulacion = postulacionDTO.convertToEntity();
        postulacion.setInformacionPostulacion(jsonPayload);
        entityManager.merge(postulacion);

        logger.debug("Finaliza actualizarJsonPostulacion(" + idPostulacion + ", SolicitudPostulacionFOVISDTO)");
    }

    /**
     * Evalua si la fecha en que la licencia ha sido prórrogada o revalidada (o prorrogada despues de la revalidación)
     * se encuentra vigente.
     *
     * @param licencia :  Licencia asociada al Proyecto Solucion de Vivienda (urbanismo y/o contrucción)
     * @return TRUE si por las condiciones descritas la licencia se encuentra vigente.
     */
    private Boolean licenciaVigentePorDetalle(LicenciaModeloDTO licencia) {
        logger.debug("Incia licenciaVigentePorDetalle(LicenciaModeloDTO)");

        Boolean vigente = false;
        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();

        for (LicenciaDetalleModeloDTO licenciaDetalle : licencia.getLicenciaDetalle()) {
            fechaFin.setTimeInMillis(licenciaDetalle.getFechaFin());
            fechaFin.add(Calendar.DAY_OF_MONTH, 1);
            if (licenciaDetalle.getFechaInicio() <= fechaActual.getTimeInMillis()
                    && fechaActual.getTimeInMillis() < fechaFin.getTimeInMillis()) {
                vigente = true;
                break;
            }
        }
        logger.debug("Finaliza licenciaVigentePorDetalle(LicenciaModeloDTO)");
        return vigente;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarSMMLVPorAnio(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public String consultarSMMLVPorAnio(String anio) {
        logger.debug("Inicia ConsultarSMMLVPorAnio" + anio);

        Date fecha = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        try {
            fecha = dateFormat.parse(anio);
        } catch (ParseException e) {
            logger.info("Casteo fecha de anio actual");
        }

        // Coge el año de la fecha usando localdate
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer valor = localDate.getYear();

        Object SMMLV = (Object) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SMMLV_ANIO)
                .setParameter("anio", valor).getSingleResult();

        logger.debug("Finaliza ConsultarSMMLVPorAnio" + anio);
        return SMMLV.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarRecursosPrioridad(java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<String> consultarRecursosPrioridad(Boolean estado) {
        logger.debug("Inicia consultarRecursosPrioridad" + estado);

        List<String> recursoPrioridad = (List<String>) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECURSOS_PRIORIDAD)
                .setParameter("estado", estado).getResultList();


        logger.debug("Finaliza consultarRecursosPrioridad" + recursoPrioridad);
        return recursoPrioridad;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarRecursosPrioridadPostulacion(java.lang.long, java.lang.long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public String consultarRecursosPrioridadPostulacionAsignacion(Long idPostulacion, Long idSolicitudAsignacion) {
        logger.debug("Inicia consultarRecursosPrioridadPostulacion" + idPostulacion + idSolicitudAsignacion);
        Object recursoPrioridad = new Object();
        try {
            recursoPrioridad = (Object) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECURSOS_PRIORIDAD_POSTULACION_ASIGNACION)
                    .setParameter("idPostulacion", idPostulacion)
                    .setParameter("idSolicitudAsignacion", idSolicitudAsignacion).getSingleResult();

            logger.info("recursoPrioridad " + recursoPrioridad);
            if(recursoPrioridad == null){
                recursoPrioridad = "";
            }
        } catch (Exception e) {
            logger.debug("Finaliza el servicio consultarRecursosPrioridadPostulacionAsignacion()");
            logger.error("No se encontro registros");
            recursoPrioridad = "";
        }
        logger.debug("Finaliza consultarRecursosPrioridadPostulacion" + recursoPrioridad);
        return recursoPrioridad.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#consultarTareasHeredadas(java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<TareasHeredadasDTO> consultarTareasHeredadas(String numeroRadicacion, String usuario, String tipoTransaccion) {
        logger.debug("Inicia consultarSolicitudesHeredadas" + numeroRadicacion + "--" + usuario);
        List<Solicitud> tareas = new ArrayList<Solicitud>();
        List<TareasHeredadasDTO> sol = new ArrayList<>();
        try {
            String radicado = numeroRadicacion;
            if (numeroRadicacion.contains("_")) {
                radicado = numeroRadicacion.substring(0, numeroRadicacion.indexOf('_'));
            }
            radicado = radicado + "_";
            if (tipoTransaccion.equals(TipoTransaccionEnum.VERIFICACION_CONTROL_INTERNO_POSTULACION_FOVIS.name())) {
                sol = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_TAREAS_HEREDADAS, TareasHeredadasDTO.class)
                        .setParameter("numeroRadicacion", radicado).getResultList();
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS.name()) || tipoTransaccion.equals(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS.name())) {
                sol = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_TAREAS_HEREDADAS_CRUCE, TareasHeredadasDTO.class)
                        .setParameter("numeroRadicacion", radicado).getResultList();
            }
        } catch (Exception e) {
            logger.error("No se encontro registros");
            sol = new ArrayList<>();
        }
        logger.debug("Finaliza consultarRecursosPrioridadPostulacion" + tareas.size());
        return sol;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#redireccionarTarea(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public RedireccionarTareaDTO redireccionarTarea(String numeroRadicacion) {
        logger.debug("Inicia consultarSolicitudesHeredadas" + numeroRadicacion);
        RedireccionarTareaDTO redireccionarTareaDTO = new RedireccionarTareaDTO();
        try {
            redireccionarTareaDTO = (RedireccionarTareaDTO) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REDIRECCIONAMIENTO_TAREA, RedireccionarTareaDTO.class)
                    .setParameter("numeroRadicacion", numeroRadicacion)
                    .getSingleResult();
            if (redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.VERIFICACION_CONTROL_INTERNO_POSTULACION_FOVIS.name())
                    && (redireccionarTareaDTO.getIdInstanciaProceso() == null || redireccionarTareaDTO.getNumeroRadicado().contains("_"))) {
                redireccionarTareaDTO.setRuleNav("321-36");
            } else if ((redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.VERIFICACION_CONTROL_INTERNO_POSTULACION_FOVIS.name()) ||
                    redireccionarTareaDTO.equals(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS))
                    && redireccionarTareaDTO.getIdInstanciaProceso() != null) {
                redireccionarTareaDTO.setRuleNav("bandeja-postulacion-interna");
            } else if (redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS.name())
                    && redireccionarTareaDTO.getIdInstanciaProceso() == null) {
                redireccionarTareaDTO.setRuleNav("321-34");
            } else if (redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS.name())
                    && redireccionarTareaDTO.getIdInstanciaProceso() == null) {
                redireccionarTareaDTO.setRuleNav("323-046");
            } else if (redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_PRESENCIAL.name())
                    || redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_WEB.name())
                    || redireccionarTareaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_DEPWEB.name())) {
                redireccionarTareaDTO.setRuleNav("122-verifiSoliRegis");
            } else {
                redireccionarTareaDTO.setRuleNav("blanco");
            }

        } catch (Exception e) {
            logger.error("No se encontro registros");
            redireccionarTareaDTO = new RedireccionarTareaDTO();
        }
        logger.debug("Finaliza consultarRecursosPrioridadPostulacion" + redireccionarTareaDTO);
        return redireccionarTareaDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#actualizarSolicitud(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public Solicitud consultarSolicitudFovis(Long instanciaProceso) {
        logger.debug("ingresa a actualizar la tarea " + instanciaProceso);
        Solicitud solicitud = new Solicitud();
        solicitud = (Solicitud) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLCITUD, Solicitud.class)
                .setParameter("instanciaProceso", instanciaProceso)
                .getSingleResult();
        logger.debug("solicitud " + solicitud.toString());
        return solicitud;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#obtenerPostulacionesPorCiclo(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<SolicitudPostulacionModeloDTO> obtenerPostulacionesPorCiclo(Long idCicloAsignacion) {
        logger.debug("Se inicia el servicio de obtenerPostulacionesPorCiclo(Long idCicloAsignacion)");
        List<SolicitudPostulacionModeloDTO> postulacionesDTO = new ArrayList<>();
        List<Object[]> postulaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACIONES_POR_CICLO)
                .setParameter("idCicloAsignacion", idCicloAsignacion)
                .getResultList();
        for (Object[] datosPostulacion : postulaciones) {
            SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = new SolicitudPostulacionModeloDTO();
            PostulacionFOVISModeloDTO postulacionFovisModeloDTO = new PostulacionFOVISModeloDTO();
            postulacionFovisModeloDTO.convertToDTO((PostulacionFOVIS) datosPostulacion[0]);
            solicitudPostulacionModeloDTO.convertToDTO((SolicitudPostulacion) datosPostulacion[1]);
            /* Se asignan los datos del Jefe del Hogar */
            Afiliado afiliado = (Afiliado) datosPostulacion[2];
            JefeHogarModeloDTO jefeHogarModeloDTO = new JefeHogarModeloDTO();
            jefeHogarModeloDTO.convertToJefeHogarDTO((JefeHogar) datosPostulacion[4], afiliado.getPersona(),
                    (PersonaDetalle) datosPostulacion[3]);
            postulacionFovisModeloDTO.setJefeHogar(jefeHogarModeloDTO);
            solicitudPostulacionModeloDTO.setNumeroRadicacion((String) datosPostulacion[5]);
            solicitudPostulacionModeloDTO
                    .setFechaRadicacion(datosPostulacion[6] != null ? ((Date) datosPostulacion[6]).getTime() : null);
            solicitudPostulacionModeloDTO.setPostulacionFOVISModeloDTO(postulacionFovisModeloDTO);
            postulacionesDTO.add(solicitudPostulacionModeloDTO);
        }

        logger.debug("Finaliza el servicio de obtenerPostulacionesPorCiclo(Long idCicloAsignacion)");
        return postulacionesDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisService#consultarCiclosAsignacion(EstadoCicloAsignacionEnum, Date)
     */
    @SuppressWarnings("unchecked")
    public List<CicloAsignacionModeloDTO> consultarCiclosAsignacion(EstadoCicloAsignacionEnum estadoCicloAsignacion, String fecha) {
        logger.debug("Se inicia el servicio de consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum)");
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        List<CicloAsignacionModeloDTO> ciclosAsignacionDTO = new ArrayList<>();
        List<CicloAsignacion> ciclosAsignacion = new ArrayList<>();
        if (fecha != null && !fecha.isEmpty()) {
            Date fechaDate = null;
            try {
                // Convertir el String a Date
                fechaDate = ff.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ciclosAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_FECHA_ASIGNACION)
                    .setParameter("estadoCicloAsignacion", estadoCicloAsignacion)
                    .setParameter("fecha", fechaDate).getResultList();

        } else {
            ciclosAsignacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_CICLOS_FECHA_ASIGNACION_EJECUCION)
                    .setParameter("estadoCicloAsignacion", estadoCicloAsignacion).getResultList();

        }

        if (ciclosAsignacion != null && !ciclosAsignacion.isEmpty()) {
            for (CicloAsignacion cicloAsignacion : ciclosAsignacion) {
                CicloAsignacionModeloDTO cicloAsignacionModelDTO = new CicloAsignacionModeloDTO(cicloAsignacion);
                ciclosAsignacionDTO.add(cicloAsignacionModelDTO);
            }
        }
        logger.debug("Finaliza el servicio de consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum)");
        return ciclosAsignacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisService#consultarCiclosAsignacionPorEstado(
     *EstadoCicloAsignacionEnum)
     */
    @Override
    public void consultarCalificacionPostulacion(Long idPostulacion, Long idCiclo) {
        logger.debug("Se inicia el servicio de consultarCiclosAsignacionPorEstado(idPostulacion, idCiclo)");
        List<CalificacionPostulacion> calificacionPostulacions = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CALIFICACIONES_POSTULACION, CalificacionPostulacion.class)
                .setParameter("idPostulacion", idPostulacion)
                .setParameter("idCiclo", idCiclo).getResultList();
        if (calificacionPostulacions != null && !calificacionPostulacions.isEmpty()) {
            for (CalificacionPostulacion calificacionPostulacion : calificacionPostulacions) {
                calificacionPostulacion.setEjecutado(Boolean.FALSE);
                entityManager.merge(calificacionPostulacion);

            }
        }
        logger.debug("Finaliza el servicio de consultarCiclosAsignacionPorEstado(EstadoCicloAsignacionEnum)");
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisService#consultarNovedadesPostulacionRangoFecha(String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PostulacionFOVISModeloDTO> consultarNovedadesPostulacionRangoFecha(Long idCicloAsignacion, String fechaInico, String fechaFin) {
        logger.debug("Inicia consultarNovedadesPostulacionRangoFecha()" + idCicloAsignacion + fechaInico + fechaFin);

        List<String> listaEstadoHogar = new ArrayList<>();
        listaEstadoHogar.add(EstadoHogarEnum.HABIL.name());
        listaEstadoHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());

        List<String> listaEstadoCruceHogar = new ArrayList<>();
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.name());
        listaEstadoCruceHogar.add(EstadoCruceHogarEnum.CRUCES_RATIFICADOS.name());

        List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = new ArrayList<>();

        List<PostulacionFOVIS> listaPostulacionFOVIS = new ArrayList<>();

        //try {

            if (fechaInico != null && !fechaInico.isEmpty()) {
                // Definir el formato correcto
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fechaIni = null;
                LocalDateTime fechaFinal = null;

                // Analiza la cadena de fecha
                fechaIni = LocalDateTime.parse(fechaInico, formatter);
                fechaFinal = LocalDateTime.parse(fechaFin, formatter2);

                List<PostulacionFOVIS>  listaPostulacionNovedad = new ArrayList<>();
                StoredProcedureQuery query = entityManager
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_FOVIS_CONSULTAR_NOVEDADES_PENDIENTES);
                query.setParameter("idCicloAsignacion", BigInteger.valueOf(idCicloAsignacion));
                listaPostulacionNovedad = query.getResultList();

                logger.info("listaPostulacionNovedad----> " +listaPostulacionNovedad);

                listaPostulacionFOVIS.addAll(listaPostulacionNovedad);

                List<PostulacionFOVIS> listaPostulacionNoCalificados = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION_RANGO, PostulacionFOVIS.class)
                        .setParameter("idCicloAsignacion", idCicloAsignacion)
                        .setParameter("fechaInicio", fechaIni)
                        .setParameter("fechaFin", fechaFinal)
                        .setParameter("listaEstadoHogar", listaEstadoHogar)
                        .setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                        .setParameter("idCicloAsignacion", idCicloAsignacion)
                        .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getResultList();

                listaPostulacionFOVIS.addAll(listaPostulacionNoCalificados);

                for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
                    listaPostulacionFOVISDTO.add(new PostulacionFOVISModeloDTO(postulacionFOVIS));
                }
            } else {
                LocalDateTime now = LocalDateTime.now();
                // Definir el formato deseado
                DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // Formatear la fecha
                String fechaFinal = now.format(formatterF);
                // Restar 48 horas
                LocalDateTime fechaMenos48Horas = now.minusHours(48);

                // Formatear la fecha
                String fechaInicial = fechaMenos48Horas.format(formatterF);

                Object valor = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CICLO_ASIGNACION_48HORAS)
                        .setParameter("idCicloAsignacion", idCicloAsignacion.intValue())
                        .setParameter("fechaInicio", fechaInicial)
                        .setParameter("fechaFin", fechaFinal).getSingleResult();

                Long longValue = (Long) valor;
                Integer intValue = longValue.intValue();  // Conversión a Integer


                if (intValue == 0) {
                    listaPostulacionFOVISDTO = consultarHogaresAplicanCalificacionPostulacion(idCicloAsignacion, Boolean.FALSE);
                } else {
                    List<PostulacionFOVIS>  listaPostulacionesPendientesNov = new ArrayList<>();
                    StoredProcedureQuery query = entityManager
                            .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_FOVIS_CONSULTAR_NOVEDADES_PENDIENTES);
                    query.setParameter("idCicloAsignacion", BigInteger.valueOf(idCicloAsignacion));
                    listaPostulacionesPendientesNov = query.getResultList();

                    logger.info("listaPostulacionNovedad----> " +listaPostulacionesPendientesNov);

                    listaPostulacionFOVIS.addAll(listaPostulacionesPendientesNov);

                    List<PostulacionFOVIS> listaPostulacionNoCalificados = entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGARES_CALIFICACION_POSTULACION_RANGO, PostulacionFOVIS.class)
                            .setParameter("idCicloAsignacion", idCicloAsignacion)
                            .setParameter("fechaInicio", fechaInicial)
                            .setParameter("fechaFin", fechaFinal)
                            .setParameter("listaEstadoHogar", listaEstadoHogar)
                            .setParameter("estadoCruceHogar", listaEstadoCruceHogar)
                            .setParameter("idCicloAsignacion", idCicloAsignacion)
                            .setParameter("estadoSolicitudGestionCruce", EstadoSolicitudGestionCruceEnum.CERRADA.name()).getResultList();

                    listaPostulacionFOVIS.addAll(listaPostulacionNoCalificados);

                    for (PostulacionFOVIS postulacionFOVIS : listaPostulacionFOVIS) {
                        listaPostulacionFOVISDTO.add(new PostulacionFOVISModeloDTO(postulacionFOVIS));
                    }

                }


            }
            logger.info(interpolate("Finaliza consultarNovedadesPostulacionRangoFecha({0})", idCicloAsignacion));
            return listaPostulacionFOVISDTO;
        /*} catch (Exception e) {
            logger.error(interpolate("FALLO consultarNovedadesPostulacionRangoFecha({0})", idCicloAsignacion));
            return listaPostulacionFOVISDTO;
        }*/
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisService#consultarFechaResultadoEjecucionProgramada(String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object consultarFechaResultadoEjecucionProgramada(Long id) {
        logger.debug(interpolate("Inicia consultarFechaResultadoEjecucionProgramada({0})", id));
        Object date = new Object();
        try {
            date = (Object) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_RESULTADO_EJECUCION)
                    .setParameter("id", id).getSingleResult();

            logger.info(interpolate("Finaliza consultarFechaResultadoEjecucionProgramada({0})", id));
            return date;
        } catch (Exception e) {
            logger.error(interpolate("no se encontro resultados - consultarFechaResultadoEjecucionProgramada({0})", id));
            return null;
        }
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CicloAsignacionModeloDTO> consultaModalidadEnCicloVigente(Long idCicloAsignacion, ModalidadEnum modalidad) {
        logger.debug("Se inicia el método de consultaModalidadEnCicloVigente(Long idCicloAsignacion, ModalidadEnum modalidad)");
        List<CicloAsignacionModeloDTO> ciclos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_MODALIDAD_EN_CICLO_VIGENTE)
                .setParameter("estadoCicloAsignacion", EstadoCicloAsignacionEnum.CERRADO)
                .setParameter("modalidad", modalidad)
                .getResultList();

        logger.debug("Finaliza el método de consultaModalidadEncicloVigente(Long idCicloAsignacion, ModalidadEnum modalidad)");
        return ciclos;
    }

    @Override
    public Object consultarIngresosHogarFovis(Long idPostulacion, String tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia servicio consultarIngresosHogarFovis(Long,String, String)");

        Object ingresosHogar = null;
        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_FOVIS_CONSULTAR_INGRESOS_HOGAR);
            query.setParameter("idPostulacion", BigInteger.valueOf(idPostulacion));
            query.setParameter("tipoIdentificacion", tipoIdentificacion);
            query.setParameter("numeroIdentificacion", numeroIdentificacion);
            ingresosHogar = query.getSingleResult();

        } catch (Exception e) {
            logger.info(" :: Hubo un error en el SP: " + e);
        }
        logger.debug("Finaliza servicio consultarIngresosHogarFovis(Long, String, String)");
        return ingresosHogar;
    }

    @Override
    public void crearActualizarPostulacionFovisDev(PostulacionFovisDevDTO postulacionFovisDev) {
        logger.debug("Inicia actualizarPostulacionFovisDev(" + postulacionFovisDev + ")");
        // Se obtiene el entity y se actualiza el estado
        PostulacionFovisDev postulacionDev = postulacionFovisDev.convertToEntity();
        entityManager.persist(postulacionDev);
        logger.debug("Finaliza actualizarPostulacionFovisDev(" + postulacionDev + ")");
    }

    @Override
    public void crearActualizarPostulacionProveedor(PostulacionProvOfeDTO postulacionProvOfeDTO) {
        logger.info("Inicia crearActualizarPostulacionProveedor(" + postulacionProvOfeDTO + ")");
        // Se obtiene el entity y se actualiza el estado
        logger.info("postulacionProveedorDTO id solicitud lega" + postulacionProvOfeDTO.getSolicitudLegalizacionFovis().getIdSolicitudLegalizacionDesembolso());
        PostulacionProvOfe postulacionProvOfe = postulacionProvOfeDTO.convertToEntity();
        logger.info("crearActualizarPostulacionProveedor(" + postulacionProvOfe + ")");
        logger.info("PostulacionProveedor id solicitud lega" + postulacionProvOfe.getSolicitudLegalizacionFovis().getIdSolicitudLegalizacionDesembolso());
        entityManager.merge(postulacionProvOfe);
        logger.info("Finaliza crearActualizarPostulacionProveedor(" + postulacionProvOfe + ")");
    }


}
