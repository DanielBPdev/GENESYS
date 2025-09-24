package com.asopagos.fovis.ejb;

import static com.asopagos.util.Interpolator.interpolate;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.fovis.AnticipoLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.CondicionesLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.ConsultarSubsidiosFOVISLegalizacionDTO;
import com.asopagos.dto.fovis.DocumentoSoporteOferenteDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProveedorDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProyectoViviendaDTO;
import com.asopagos.dto.fovis.HistoricoLegalizacionFOVISDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.fovis.VisitaDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoRequisitoModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.LicenciaDetalleModeloDTO;
import com.asopagos.dto.modelo.LicenciaModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.VisitaModeloDTO;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.fovis.CondicionVisita;
import com.asopagos.entidades.ccf.fovis.DocumentoSoporteOferente;
import com.asopagos.entidades.ccf.fovis.DocumentoSoporteProveedor;
import com.asopagos.entidades.ccf.fovis.DocumentoSoporteProyectoVivienda;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolsoRequisito;
import com.asopagos.entidades.ccf.fovis.JefeHogar;
import com.asopagos.entidades.ccf.fovis.LegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.Licencia;
import com.asopagos.entidades.ccf.fovis.LicenciaDetalle;
import com.asopagos.entidades.ccf.fovis.ProyectoSolucionVivienda;
import com.asopagos.entidades.ccf.fovis.RecursoComplementario;
import com.asopagos.entidades.ccf.fovis.SolicitudLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.Visita;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.legalizacionfovis.dto.SolicitudPostulacionLegalizacionDTO;
import com.asopagos.legalizacionfovis.service.LegalizacionFovisService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Query;

import fovis.constants.NamedQueriesConstants;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de Legalización FOVIS<br/>
 * <b>Módulo:</b> Asopagos - Legalización FOVIS 3.2.4
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class LegalizacionFovisBusiness implements LegalizacionFovisService {

    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName = "legalizacion_fovis_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(LegalizacionFovisBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.legalizacionfovis.service.LegalizacionFovisService#crearActualizarLicencia(com.asopagos.dto.modelo.LicenciaModeloDTO)
     */
    @Override
    public LicenciaModeloDTO crearActualizarLicencia(LicenciaModeloDTO licenciaDTO) {
        logger.debug("Inicia el servicio crearActualizarLicencia");
        Licencia licencia = licenciaDTO.convertToEntity();
        licencia = entityManager.merge(licencia);
        licenciaDTO.setIdLicencia(licencia.getIdLicencia());
        logger.debug("Finaliza el servicio crearActualizarLicencia");
        return licenciaDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarPostulacionesParaLegalizacionYDesembolso(
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<SolicitudPostulacionLegalizacionDTO> consultarPostulacionesParaLegalizacionYDesembolso(String numeroRadicadoSolicitud,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        //try {

            List<SolicitudPostulacionLegalizacionDTO> solicitudes = new ArrayList<>();
            if (numeroRadicadoSolicitud != null || (tipoIdentificacion != null && numeroIdentificacion != null)) {
                List<Object[]> datosPostulaciones = new ArrayList<>();

                StoredProcedureQuery query = entityManager
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_POSTULACION_PARA_LEGALIAZACION_DESEMBOLSO);
                query.setParameter("numeroRadicadoSolicitud", numeroRadicadoSolicitud != null ? numeroRadicadoSolicitud : "1");
                query.setParameter("tipoIdentificacion", tipoIdentificacion != null ? tipoIdentificacion.name() : "1");
                query.setParameter("numeroIdentificacion", numeroIdentificacion != null ? numeroIdentificacion : "1");
                datosPostulaciones =  query.getResultList();

                logger.info("datosPostulaciones " + datosPostulaciones);

                logger.info("**__**Finalizo la consulta de bloqueo ");
                if (datosPostulaciones != null && !datosPostulaciones.isEmpty()) {
                    for (Object[] registro : datosPostulaciones) {
                        SolicitudPostulacionLegalizacionDTO solicitudPostulacionLegalizacionDTO = new SolicitudPostulacionLegalizacionDTO(
                                registro[0].toString(), registro[1] != null ? TipoIdentificacionEnum.valueOf(registro[1].toString()) : null,
                                registro[2].toString(), registro[3].toString(),
                                registro[4] != null ? EstadoHogarEnum.valueOf(registro[4].toString()) : null,
                                registro[5] != null ? ((Date) registro[5]).getTime() : null, (BigInteger) registro[6],
                                (BigInteger) registro[7], (BigInteger) registro[8], registro[9] != null ? (BigInteger) registro[9] : null,
                                registro[10] != null ? (BigInteger) registro[10] : null, registro[11] != null ? ((Date) registro[11]).getTime() : null);

                        // se cambia el booleano para permitir o no seleccionar la postulacion para legalización y desembolso
                        if ((EstadoHogarEnum.ASIGNADO_SIN_PRORROGA.equals(solicitudPostulacionLegalizacionDTO.getEstadoHogar())
                                || EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA
                                        .equals(solicitudPostulacionLegalizacionDTO.getEstadoHogar())
                                || EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA
                                        .equals(solicitudPostulacionLegalizacionDTO.getEstadoHogar())
                                || EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO
                                        .equals(solicitudPostulacionLegalizacionDTO.getEstadoHogar()))
                                && solicitudPostulacionLegalizacionDTO.getIdSolicitudLegalizacionDesembolsoEnCurso() == null) {
                            solicitudPostulacionLegalizacionDTO.setLegalizacionYDesembolsoViable(Boolean.TRUE);
                        }
                        else {
                            solicitudPostulacionLegalizacionDTO.setLegalizacionYDesembolsoViable(Boolean.FALSE);
                        }
                        solicitudes.add(solicitudPostulacionLegalizacionDTO);
                    }
                }
            }
            logger.debug("Finaliza servicio consultarPostulacionesParaLegalizacionYDesembolso(String, TipoIdentificacionEnum, String)");
            return solicitudes;
        /*} catch (Exception e) {
            logger.error("Error inesperado en consultarPostulacionesParaLegalizacionYDesembolso(String, TipoIdentificacionEnum, String)",
                    e);

            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#crearActualizarDetalleLicencia(com.asopagos.dto.modelo.
     * LicenciaDetalleModeloDTO)
     */
    @Override
    public LicenciaDetalleModeloDTO crearActualizarDetalleLicencia(LicenciaDetalleModeloDTO licenciaDetalleDTO) {
        logger.debug("Inicia el servicio crearActualizarDetalleLicencia");
        LicenciaDetalle licenciaDetalle = licenciaDetalleDTO.convertToEntity();
        licenciaDetalle = entityManager.merge(licenciaDetalle);
        licenciaDetalleDTO.setIdLicenciaDetalle(licenciaDetalle.getIdLicencia());
        logger.debug("Finaliza el servicio crearActualizarDetalleLicencia");
        return licenciaDetalleDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#
     *      crearActualizarSolicitudLegalizacionDesembolso(com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public SolicitudLegalizacionDesembolsoModeloDTO crearActualizarSolicitudLegalizacionDesembolso(
            SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO) {
        logger.debug("Inicia el servicio crearActualizarSolicitudLegalizacionDesembolso(SolicitudLegalizacionDesembolsoModeloDTO)");
        /* Si se registró una solicitud de legalización para la postulación y se encuentra en proceso no se permite crear una nueva. */
        
        System.out.println("solicitudLegalizacionDesembolsoDTO " + solicitudLegalizacionDesembolsoDTO.getIdPostulacionFOVIS());
        if (solicitudLegalizacionDesembolsoDTO.getIdPostulacionFOVIS() != null
                && solicitudLegalizacionDesembolsoDTO.getIdSolicitudLegalizacionDesembolso() == null) {
            List<BigInteger> solicitudesEnProceso = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_PROCESO)
                    .setParameter("idPostulacion", solicitudLegalizacionDesembolsoDTO.getIdPostulacionFOVIS())
                    .setParameter("estadoSolicitud", EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO.name())
                    .getResultList();
            if (solicitudesEnProceso == null && solicitudesEnProceso.isEmpty()) {
                logger.error("Ya existe una solicitud en Proceso de Legalizacion y Desembolso para la Postulacion:"
                        + solicitudLegalizacionDesembolsoDTO.getIdPostulacionFOVIS());
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

        }
        SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso = solicitudLegalizacionDesembolsoDTO.convertToEntity();
        SolicitudLegalizacionDesembolso managed = entityManager.merge(solicitudLegalizacionDesembolso);
        solicitudLegalizacionDesembolsoDTO.convertToDTO(managed);
        logger.debug("Finaliza el servicio crearActualizarSolicitudLegalizacionDesembolso(SolicitudLegalizacionDesembolsoModeloDTO)");
        return solicitudLegalizacionDesembolsoDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#registrarRequisitosDocumentalesOferente(com.asopagos.dto.fovis.
     * DocumentoSoporteOferenteDTO)
     */
    @Override
    public DocumentoSoporteOferenteDTO registrarRequisitosDocumentalesOferente(DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO) {
        logger.debug("Inicia el servicio registrarRequisitosDocumentalesOferente(documentoSoporteOferenteDTO)");
        DocumentoSoporteOferente documentoSoporteOferente = new DocumentoSoporteOferente();
        documentoSoporteOferente.setIdDocumentoSoporte(documentoSoporteOferenteDTO.getDocumentoSoporteDTO().getIdDocumentoSoporte());
        documentoSoporteOferente.setIdOferente(documentoSoporteOferenteDTO.getOferenteDTO().getIdOferente());
        documentoSoporteOferente = entityManager.merge(documentoSoporteOferente);
        logger.debug("Finaliza el servicio registrarRequisitosDocumentalesOferente(documentoSoporteOferenteDTO)");
        return documentoSoporteOferenteDTO;
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#validarSolicitudesLegalizacionYDesembolsoCerrado(com.asopagos.dto.fovis.
     * DocumentoSoporteOferenteDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean validarSolicitudesLegalizacionYDesembolsoCerrado(Long idPostulacionFOVIS) {

        logger.info("Inicia el servicio validarSolicitudesLegalizacionYDesembolsoCerrado(idPostulacionFOVIS)");

        List<BigInteger> solicitudesEnProceso = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_PROCESO)
                    .setParameter("idPostulacion", idPostulacionFOVIS)
                    .setParameter("estadoSolicitud", EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO.name())
                    .getResultList();
        logger.info("Finaliza el servicio validarSolicitudesLegalizacionYDesembolsoCerrado(idPostulacionFOVIS)");
        return solicitudesEnProceso.isEmpty();

    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#registrarRequisitosDocumentalesProveedor(com.asopagos.dto.fovis.
     * DocumentoSoporteProveedorDTO)
    */
    @Override
    public DocumentoSoporteProveedorDTO registrarRequisitosDocumentalesProveedor(DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO) {
        logger.debug("Inicia el servicio registrarRequisitosDocumentalesProveedor(documentoSoporteProveedorDTO)");
        DocumentoSoporteProveedor documentoSoporteProveedor = new DocumentoSoporteProveedor();
        documentoSoporteProveedor.setIdDocumentoSoporte(documentoSoporteProveedorDTO.getDocumentoSoporteDTO().getIdDocumentoSoporte());
        documentoSoporteProveedor.setIdProveedor(documentoSoporteProveedorDTO.getProveedorDTO().getIdOferente());
        documentoSoporteProveedor = entityManager.merge(documentoSoporteProveedor);
        logger.debug("Finaliza el servicio registrarRequisitosDocumentalesProveedor(documentoSoporteProveedorDTO)");
        return documentoSoporteProveedorDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#
     *      registrarIntentoLegalizacionDesembolsoFOVIS(com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoModeloDTO)
     */
    @Override
    public Long registrarIntentoLegalizacionDesembolsoFOVIS(IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO) {
        logger.debug("Se inicia el servicio de registrarIntentoLegalizacionDesembolso(intentoLegalizacionDesembolsoDTO)");
        try {
            IntentoLegalizacionDesembolso intentoLegalizacionDesembolso = intentoLegalizacionDesembolsoModeloDTO
                    .convertToIntentoLegalizacionDesembolsoEntity();
            entityManager.persist(intentoLegalizacionDesembolso);
            logger.debug("Finaliza el servicio de registrarIntentoLegalizacionDesembolso(intentoLegalizacionDesembolsoDTO)");
            return intentoLegalizacionDesembolso.getIdIntentoLegalizacionDesembolso();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio de "
                    + "registrarIntentoLegalizacionDesembolso(intentoLegalizacionDesembolsoDTO))", e);

            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#registrarDocumentoSoporte(com.asopagos.dto.modelo.
     * DocumentoSoporteModeloDTO)
     */
    @Override
    public DocumentoSoporteModeloDTO registrarDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporteDTO) {
        logger.debug("Inicia el servicio registrarDocumentoSoporte(documentoSoporteDTO)");
        DocumentoSoporte documentoSoporte = documentoSoporteDTO.convertToEntity();
        documentoSoporte = entityManager.merge(documentoSoporte);
        documentoSoporteDTO.setIdDocumentoSoporte(documentoSoporte.getIdDocumentoSoporte());
        logger.debug("Finaliza el servicio registrarDocumentoSoporte(documentoSoporteDTO)");
        return documentoSoporteDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#registrarIntentoLegalizacionDesembolsoRequisito(java.util.List)
     */
    @Override
    public void registrarIntentoLegalizacionDesembolsoRequisito(
            List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> intentoLegalizacionDesembolsoRequisitosDTO) {
        logger.debug(
                "Inicia servicio registrarIntentoLegalizacionDesembolsoRequisito(List<IntentoLegalizacionDesembolsoRequisitoModeloDTO>)");
        try {
            for (IntentoLegalizacionDesembolsoRequisitoModeloDTO intentoLegalizacionDesembolsoRequisitoModeloDTO : intentoLegalizacionDesembolsoRequisitosDTO) {
                IntentoLegalizacionDesembolsoRequisito intentoLegalizacionDesembolsoRequisito = intentoLegalizacionDesembolsoRequisitoModeloDTO
                        .convertToEntity();
                if (intentoLegalizacionDesembolsoRequisito.getIdIntentoLegalizacionDesembolsoRequisito() == null) {
                    entityManager.persist(intentoLegalizacionDesembolsoRequisito);
                }
                else {
                    entityManager.merge(intentoLegalizacionDesembolsoRequisito);
                }
            }
            logger.debug("Finaliza servicio registrarIntentoLegalizacionDesembolsoRequisito("
                    + "List<IntentoLegalizacionDesembolsoRequisitoModeloDTO>)");
            return;
        } catch (Exception e) {
            logger.error("Error inesperado en registrarIntentoLegalizacionDesembolsoRequisito("
                    + "List<IntentoLegalizacionDesembolsoRequisitoModeloDTO>)", e);

            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.legalizacionfovis.service.LegalizacionFovisService#registrarRequisitosDocumentalesProyectoVivienda(com.asopagos.dto.
     * fovis.DocumentoSoporteProyectoViviendaDTO)
     */
    @Override
    public DocumentoSoporteProyectoViviendaDTO registrarRequisitosDocumentalesProyectoVivienda(
            DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO) {
        logger.debug("Inicia el servicio registrarRequisitosDocumentalesProyectoVivienda(documentoSoporteProyectoViviendaDTO)");
        DocumentoSoporteProyectoVivienda documentoSoporteProyecto = new DocumentoSoporteProyectoVivienda();
        documentoSoporteProyecto
                .setIdDocumentoSoporte(documentoSoporteProyectoViviendaDTO.getDocumentoSoporteDTO().getIdDocumentoSoporte());
        documentoSoporteProyecto
                .setIdProyectoVivienda(documentoSoporteProyectoViviendaDTO.getProyectoViviendaDTO().getIdProyectoVivienda());
        documentoSoporteProyecto = entityManager.merge(documentoSoporteProyecto);
        logger.debug("Finaliza el servicio registrarRequisitosDocumentalesProyectoVivienda(documentoSoporteProyectoViviendaDTO)");
        return documentoSoporteProyectoViviendaDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarSolicitudLegalizacionDesembolso(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudLegalizacionDesembolsoModeloDTO consultarSolicitudLegalizacionDesembolso(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarSolicitudLegalizacionDesembolso(Long)");
        try {
            SolicitudLegalizacionDesembolso sol = (SolicitudLegalizacionDesembolso) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
            SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = new SolicitudLegalizacionDesembolsoModeloDTO();
            solicitudDTO.convertToDTO(sol);
            /* Se consulta la legalización y desembolso. */
            if (sol.getIdLegalizacionDesembolso() != null) {
                LegalizacionDesembolso legalizacionDesembolso = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_LEGALIZACION_POR_ID, LegalizacionDesembolso.class)
                        .setParameter("idLegalizacion", sol.getIdLegalizacionDesembolso()).getSingleResult();
                LegalizacionDesembolsoModeloDTO legalizacionDTO = new LegalizacionDesembolsoModeloDTO(legalizacionDesembolso);
                solicitudDTO.setLegalizacionDesembolso(legalizacionDTO);
            }
            logger.debug("Finaliza consultarSolicitudLegalizacionDesembolso(Long)");
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudLegalizacionDesembolso(Long)"
                    + interpolate("No se encontraron resultados con el id de solicitud {0} ingresada.", idSolicitudGlobal));
            return new SolicitudLegalizacionDesembolsoModeloDTO();
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#actualizarEstadoSolicitudLegalizacionDesembolso(
     *      java.lang.Long, com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum)
     */
    @Override
    public void actualizarEstadoSolicitudLegalizacionDesembolso(Long idSolicitudGlobal,
            EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
        logger.debug("Inicia actualizarEstadoSolicitudLegalizacionDesembolso(" + idSolicitudGlobal + estadoSolicitud + ")");
        SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO = consultarSolicitudLegalizacionDesembolso(
                idSolicitudGlobal);
        SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso = solicitudLegalizacionDesembolsoDTO.convertToEntity();

        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO.equals(estadoSolicitud)) {
            ResultadoProcesoEnum estadoResultadoProceso = ResultadoProcesoEnum.APROBADA;
            if (EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO
                    .equals(solicitudLegalizacionDesembolso.getEstadoSolicitud())
                    || EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_AUTORIZADO
                            .equals(solicitudLegalizacionDesembolso.getEstadoSolicitud())) {
                estadoResultadoProceso = ResultadoProcesoEnum.RECHAZADA;
            }
            else if (EstadoSolicitudLegalizacionDesembolsoEnum.DESISTIDA.equals(solicitudLegalizacionDesembolso.getEstadoSolicitud())
                    || EstadoSolicitudLegalizacionDesembolsoEnum.CANCELADA.equals(solicitudLegalizacionDesembolso.getEstadoSolicitud())) {
                estadoResultadoProceso = ResultadoProcesoEnum.valueOf(solicitudLegalizacionDesembolso.getEstadoSolicitud().name());
            }
            solicitudLegalizacionDesembolso.getSolicitudGlobal().setResultadoProceso(estadoResultadoProceso);
        }

        solicitudLegalizacionDesembolso.setEstadoSolicitud(estadoSolicitud);
        solicitudLegalizacionDesembolso.setFechaOperacion(new Date());
        entityManager.merge(solicitudLegalizacionDesembolso);
        logger.debug("Finaliza actualizarEstadoSolicitudLegalizacionDesembolso(" + idSolicitudGlobal + estadoSolicitud + ")");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#
     *      crearActualizarLegalizacion(com.asopagos.dto.modelo.LegalizacionDesembolsoModeloDTO)
     */
    @Override
    public LegalizacionDesembolsoModeloDTO crearActualizarLegalizacionDesembolso(
            LegalizacionDesembolsoModeloDTO legalizacionDesembolsoDTO) {
        logger.debug("Inicia el servicio crearActualizarLegalizacionDesembolso");
        LegalizacionDesembolso legalizacionFOVIS = legalizacionDesembolsoDTO.convertToEntity();
        LegalizacionDesembolso managed = entityManager.merge(legalizacionFOVIS);
        legalizacionDesembolsoDTO.setIdLegalizacionDesembolso(managed.getIdLegalizacionDesembolso());
        logger.debug("Finaliza el servicio crearActualizarLegalizacionDesembolso");
        return legalizacionDesembolsoDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarAnticiposDesembolsados(java.lang.Long)
     */
    @Override
    public List<AnticipoLegalizacionDesembolsoDTO> consultarAnticiposDesembolsados(Long idPostulacionFovis) {
        logger.debug("Inicia servicio consultarAnticiposDesembolsados(Long idPostulacionFovis)");
        try {
            List<AnticipoLegalizacionDesembolsoDTO> anticiposDesembolsados = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LEGALIZACIONES_DESEMBOLSADAS_POR_POSTULACION,
                            AnticipoLegalizacionDesembolsoDTO.class)
                    .setParameter("idPostulacion", idPostulacionFovis).getResultList();
            logger.debug("Finaliza servicio consultarAnticiposDesembolsados(Long idPostulacionFovis)");
            return anticiposDesembolsados;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarAnticiposDesembolsados(Long idPostulacionFovis)", e);

            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.fovis.service.FovisService#consultarIdPostulacionFovisPorSolicitud(java.lang.Long)
     */
    @Override
    public Long consultarIdPostulacionFovisPorSolicitud(Long idSolicitud) {
        logger.debug("Se inicia el servicio de consultarIdPostulacionFovisPorSolicitud(Long idSolicitud)");

        try {
            Long idPostulacion = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_POSTULACION_FOVIS_SOLICITUD)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            return idPostulacion;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarIdPostulacionFovisPorSolicitud(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.fovis.service.FovisService#consultarDatosJefeHogarPostulacion(java.lang.Long)
     */
    @Override
    public JefeHogarModeloDTO consultarDatosJefeHogarPostulacion(Long idPostulacionFovis) {
        logger.debug("Se inicia el servicio consultarJefeHogarPostulacion(Long idPostulacionFovis)");
        JefeHogarModeloDTO jefeHogarModeloDTO = new JefeHogarModeloDTO();
        try {
            Object[] datosJefeHogar = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_JEFE_HOGAR_POSTULACION)
                    .setParameter("idPostulacionFovis", idPostulacionFovis).getSingleResult();

            //Se asignan los datos del jefe de hogar
            Afiliado afiliado = (Afiliado) datosJefeHogar[1];

            jefeHogarModeloDTO.convertToJefeHogarDTO((JefeHogar) datosJefeHogar[0], afiliado.getPersona(),
                    (PersonaDetalle) datosJefeHogar[2]);
        } catch (Exception e) {
            logger.debug("No existe el Jefe de Hogar para la postulacion.");
        }
        return jefeHogarModeloDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.fovis.service.FovisService#consultarOferentePostulacion(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PersonaModeloDTO consultarPersonaOferente(Long idPostulacionFovis) {

        logger.debug("Se incia el servicio consultarOferentePostulacion(Long idPostulacionFovis)");
        PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
        try {
            try {
                Persona persona = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_OFERENTE_POSTULACION, Persona.class)
                        .setParameter("idPostulacionFovis", idPostulacionFovis).getSingleResult();
                List<PersonaDetalle> perDetalles = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE)
                        .setParameter("idPersona", persona.getIdPersona()).getResultList();
                PersonaDetalle perDetalle = null;
                if (perDetalles != null && !perDetalles.isEmpty()) {
                    perDetalle = perDetalles.get(0);
                }
                personaModeloDTO.convertToDTO(persona, perDetalle);

            } catch (NoResultException e) {
                logger.debug("No existe el oferente para la postulacion.");
            }

            return personaModeloDTO;

        } catch (Exception e) {
            logger.error(
                    "Ocurrió un error inesperado en el servicio consultarOferentePostulacion(Long idPostulacionFovis - NO existe el oferente en base de datos)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarProyectoSolucionVivienda(java.lang.Long)
     */
    @Override
    public ProyectoSolucionViviendaModeloDTO consultarProyectoSolucionVivienda(Long idPostulacionFovis) {

        ProyectoSolucionViviendaModeloDTO proyectoSolucionVivienda = new ProyectoSolucionViviendaModeloDTO();
        logger.debug("Se incia el servicio consultarProyectoSolucionVivienda(Long idPostulacionFovis)");

        try {
            try {
                proyectoSolucionVivienda = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PROYECTO_SOLUCION_VIVIENDA,
                                ProyectoSolucionViviendaModeloDTO.class)
                        .setParameter("idPostulacionFovis", idPostulacionFovis).setParameter("registrado", Boolean.TRUE).getSingleResult();
            } catch (NoResultException e) {
                proyectoSolucionVivienda = new ProyectoSolucionViviendaModeloDTO();
            }
            return proyectoSolucionVivienda;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarProyectoSolucionVivienda(Long idPostulacionFovis)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarLicencia(java.lang.Long)
     */
    @Override
    public List<LicenciaModeloDTO> consultarLicencia(Long idProyectoVivienda) {

        logger.debug("Se incia el servicio consultarLicencia(Long idProyectoVivienda)");
        List<LicenciaModeloDTO> licencias = new ArrayList<LicenciaModeloDTO>();

        try {
            licencias = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LICENCIA, LicenciaModeloDTO.class)
                    .setParameter("idProyectoVivienda", idProyectoVivienda).getResultList();
            return licencias;

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarLicencia(Long idProyectoVivienda)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarDetalleVisita(java.lang.Long)
     */
    @Override
    public List<LicenciaDetalleModeloDTO> consultarDetalleLicencia(Long idLicencia) {

        logger.debug("Se inicia el servicio consultarDetalleVisita(Long idLicencia)");
        List<LicenciaDetalleModeloDTO> licenciaDetalleModeloDTO = new ArrayList<>();

        try {
            licenciaDetalleModeloDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_LICENCIA, LicenciaDetalleModeloDTO.class)
                    .setParameter("idLicencia", idLicencia).getResultList();
            return licenciaDetalleModeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarDetalleVisita(Long idLicencia)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#crearVisita(com.asopagos.dto.modelo.VisitaModeloDTO)
     */
    @Override
    public VisitaModeloDTO crearVisita(VisitaModeloDTO visitaDTO) {
        logger.debug("Inicia el servicio crearVisita(VisitaModeloDTO visitaDTO)");
        Visita visita = visitaDTO.convertToEntity();
        Visita managed = entityManager.merge(visita);
        visitaDTO.setIdVisita(managed.getIdVisita());
        logger.debug("Finaliza el servicio crearVisita(VisitaModeloDTO visitaDTO)");
        return visitaDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#crearCondicionesVisita(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CondicionVisitaModeloDTO> crearCondicionesVisita(List<CondicionVisitaModeloDTO> condicionesVisitaDTO) {

        List<CondicionVisitaModeloDTO> condicionesVisita = new ArrayList<>();
        try {
            logger.debug("Inicia el servicio crearCondicionesVisita(List<CondicionVisitaModeloDTO> condicionesVisitaDTO)");
            for (CondicionVisitaModeloDTO condicionVisitaModeloDTO : condicionesVisitaDTO) {
                List<CondicionVisita> condicionesVisitaResult = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_VISITA_ID)
                        .setParameter("idVisita", condicionVisitaModeloDTO.getIdVisita())
                        .setParameter("condicion", condicionVisitaModeloDTO.getCondicion()).getResultList();
                if (condicionesVisitaResult != null && !condicionesVisitaResult.isEmpty()) {
                    condicionVisitaModeloDTO.setIdCondicionVisita(condicionesVisitaResult.get(0).getIdCondicionVisita());
                }

                CondicionVisita condicionVisita = condicionVisitaModeloDTO.convertToEntity();
                CondicionVisita managed = entityManager.merge(condicionVisita);
                condicionVisitaModeloDTO.setIdCondicionVisita(managed.getIdCondicionVisita());

                condicionesVisita.add(condicionVisitaModeloDTO);
            }
            return condicionesVisita;
        } catch (Exception e) {
            logger.error(
                    "Ocurrió un error inesperado en el servicio crearCondicionesVisita(List<CondicionVisitaModeloDTO> condicionesVisitaDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarVisita(java.lang.Long)
     */
    @Override
    public VisitaDTO consultarVisita(Long idVisita) {

        VisitaDTO visitaDTO = new VisitaDTO();
        try {
            logger.debug("Inicia el servicio consultarVisita(Long idVisita)");

            visitaDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_VISITA, VisitaDTO.class)
                    .setParameter("idVisita", idVisita).getSingleResult();

            return visitaDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarVisita(Long idVisita)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarCondicionesVisita(java.lang.Long)
     */
    @Override
    public List<CondicionVisitaModeloDTO> consultarCondicionesVisita(Long idVisita) {

        List<CondicionVisitaModeloDTO> listaCondiciones = new ArrayList<>();

        try {
            logger.debug("Inicia el servicio consultarCondicionesVisita(Long idVisita)");

            listaCondiciones = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_VISITA, CondicionVisitaModeloDTO.class)
                    .setParameter("idVisita", idVisita).getResultList();
            return listaCondiciones;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarCondicionesVisita(Long idVisita)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarProyectoViviendaPorOferenteNombreProyecto(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public ProyectoSolucionViviendaModeloDTO consultarProyectoViviendaPorOferenteNombreProyecto(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String nombreProyecto) {
        logger.debug("Se inicia el servicio de consultarProyectoViviendaPorOferenteNombreProyecto()");
        try {
            ProyectoSolucionVivienda proyectoVivienda = (ProyectoSolucionVivienda) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PROYECTO_POR_OFERENTE_NOMBRE_PROYECTO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("nombreProyecto", nombreProyecto).getSingleResult();

            ProyectoSolucionViviendaModeloDTO proyectoViviendaDTO = new ProyectoSolucionViviendaModeloDTO();
            proyectoViviendaDTO.convertToDTO(proyectoVivienda, null, null);

            logger.debug("Finaliza consultarProyectoViviendaPorOferenteNombreProyecto()");
            return proyectoViviendaDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarProyectoViviendaPorOferenteNombreProyecto()");
            return null;
        } catch (NonUniqueResultException nur) {
            logger.error("Finaliza consultarProyectoViviendaPorOferenteNombreProyecto()");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarProyectoViviendaPorOferenteNombreProyecto()");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarDocumentosSoporteOferentePorIdOferente(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteOferentePorIdOferente(Long idOferente) {
        logger.debug(interpolate("Inicia consultarDocumentosSoporteOferentePorIdOferente({0})", idOferente));
        List<DocumentoSoporte> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_SOPORTE_POR_OFERENTE)
                .setParameter("idOferente", idOferente).getResultList();
        List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<DocumentoSoporteModeloDTO>();
        for (DocumentoSoporte documentoSoporte : lista) {
            DocumentoSoporteModeloDTO documentoOferente = new DocumentoSoporteModeloDTO();
            documentoOferente.convertToDTO(documentoSoporte);
            listaDocumentos.add(documentoOferente);
        }

        logger.debug(interpolate("Finaliza consultarDocumentosSoporteOferentePorIdOferente({0})", idOferente));
        return listaDocumentos;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarDocumentosSoporteProveedorPorIdProveedor(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteProveedorPorIdProveedor(Long idProveedor) {
        logger.debug(interpolate("Inicia consultarDocumentosSoporteProveedorPorIdProveedor({0})", idProveedor));
        List<DocumentoSoporte> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_SOPORTE_POR_PROVEEDOR)
                .setParameter("idProveedor", idProveedor).getResultList();
        List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<DocumentoSoporteModeloDTO>();
        for (DocumentoSoporte documentoSoporte : lista) {
            DocumentoSoporteModeloDTO documentoProveedor = new DocumentoSoporteModeloDTO();
            documentoProveedor.convertToDTO(documentoSoporte);
            listaDocumentos.add(documentoProveedor);
        }

        logger.debug(interpolate("Finaliza consultarDocumentosSoporteProveedorPorIdProveedor({0})", idProveedor));
        return listaDocumentos;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarDocumentosSoporteProyectoPorIdProyecto(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteProyectoPorIdProyecto(Long idProyectoVivienda) {
        logger.debug(interpolate("Inicia consultarDocumentosSoporteProyectoPorIdProyecto({0})", idProyectoVivienda));
        List<DocumentoSoporte> lista = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_SOPORTE_PROYECTO_VIVIENDA_POR_PROYECTO)
                .setParameter("idProyectoVivienda", idProyectoVivienda).getResultList();

        List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<DocumentoSoporteModeloDTO>();
        for (DocumentoSoporte documentoSoporte : lista) {
            DocumentoSoporteModeloDTO documentoProyecto = new DocumentoSoporteModeloDTO();
            documentoProyecto.convertToDTO(documentoSoporte);
            listaDocumentos.add(documentoProyecto);
        }

        logger.debug(interpolate("Finaliza consultarDocumentosSoporteProyectoPorIdProyecto({0})", idProyectoVivienda));
        return listaDocumentos;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarOferentePorId(java.lang.Long)
     */
    @Override
    public OferenteModeloDTO consultarOferentePorId(Long idOferente) {
        logger.debug(interpolate("Inicia consultarOferentePorId({0})", idOferente));

        OferenteModeloDTO oferenteDTO = new OferenteModeloDTO();
        /* Consulta si existe como Oferente */
        oferenteDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_OFERENTE_POR_ID, OferenteModeloDTO.class)
                .setParameter("idOferente", idOferente).getSingleResult();
        /* Si existe como empresa */
        if (oferenteDTO.getEmpresa() != null && oferenteDTO.getEmpresa().getIdEmpresa() != null) {
            Empresa empresaAsociada = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
                    .setParameter("idEmpresa", oferenteDTO.getEmpresa().getIdEmpresa()).getSingleResult();
            EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
            empresaDTO.convertToDTO(empresaAsociada);
            oferenteDTO.setEmpresa(empresaDTO);
        }

        logger.debug(interpolate("Finaliza consultarOferentePorId({0})", idOferente));
        return oferenteDTO;
    }

    /*
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarDatosHogarPreLegalizacionDesembolso(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudPostulacionFOVISDTO consultarDatosHogarPreLegalizacionDesembolso(String numeroRadicado) throws IOException {
        logger.debug("Inicia consultarDatosHogarPreLegalizacionDesembolso(" + numeroRadicado + ")");
        List<String> jsonDatosPostulacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_JSON_DATOS_INICIALES_POSTULACION_POR_RADICADO_SOLICITUD)
                .setParameter("numeroRadicado", numeroRadicado).getResultList();
        SolicitudPostulacionFOVISDTO datosPostulacion = new SolicitudPostulacionFOVISDTO();
        if (jsonDatosPostulacion != null && !jsonDatosPostulacion.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            datosPostulacion = mapper.readValue(jsonDatosPostulacion.get(0), SolicitudPostulacionFOVISDTO.class);
        }
        logger.debug("Finaliza consultarDatosHogarPreLegalizacionDesembolso(" + numeroRadicado + ")");
        return datosPostulacion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarTiposAhorroPostulacion(java.lang.Long)
     */
    @Override
    public List<TipoAhorroPrevioEnum> consultarTiposAhorroPostulacion(Long idPostulacionFovis) {

        logger.debug("Inicia servicio consultarTiposAhorroPostulacion(Long idPostulacionFovis)");
        List<TipoAhorroPrevioEnum> tiposAhorroPostulacion = new ArrayList<>();
        try {
            tiposAhorroPostulacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPOS_AHORRO_POSTULACION, TipoAhorroPrevioEnum.class)
                    .setParameter("idPostulacionFovis", idPostulacionFovis).getResultList();

            return tiposAhorroPostulacion;
        } catch (Exception e) {
            logger.error("Error inesperado en el servicio servicio consultarTiposAhorroPostulacion(Long idPostulacionFovis)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarTiposRecursosPostulacion(java.lang.Long)
     */
    @Override
    public List<TipoRecursoComplementarioEnum> consultarTiposRecursosPostulacion(Long idPostulacionFovis) {

        logger.debug("Inicia servicio consultarTiposRecursosPostulacion(Long idPostulacionFovis)");
        List<TipoRecursoComplementarioEnum> tiposRecursosPostulacion = new ArrayList<>();
        try {
            tiposRecursosPostulacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPOS_RECURSOS_POSTULACION, TipoRecursoComplementarioEnum.class)
                    .setParameter("idPostulacionFovis", idPostulacionFovis).getResultList();

            return tiposRecursosPostulacion;
        } catch (Exception e) {
            logger.error("Error inesperado en el servicio servicio consultarTiposRecursosPostulacion(Long idPostulacionFovis)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarRecursoComplementario(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum)
     */
    @Override
    public RecursoComplementarioModeloDTO consultarRecursoComplementario(Long idPostulacion,
            TipoRecursoComplementarioEnum tipoRecursoComplementario) {
        try {
            logger.debug("Inicia servicio consultarRecursoComplementario");
            RecursoComplementario recursoComplementario = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECURSO_COMPLEMENTARIO, RecursoComplementario.class)
                    .setParameter("idPostulacion", idPostulacion).setParameter("tipoRecursoComplementario", tipoRecursoComplementario)
                    .getSingleResult();
            RecursoComplementarioModeloDTO recursoComplementarioModeloDTO = new RecursoComplementarioModeloDTO();
            recursoComplementarioModeloDTO.convertToDTO(recursoComplementario);
            logger.debug("Finaliza servicio consultarRecursoComplementario");
            return recursoComplementarioModeloDTO;
        } catch (NoResultException e) {
            logger.error("No existrn recursos complementarios para la postulacion", e);
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio consultarRecursoComplementario", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarListaAhorrosPreviosPostulacion(java.lang.Long)
     */
    @Override
    public List<AhorroPrevioModeloDTO> consultarListaAhorrosPreviosPostulacion(Long idPostulacion) {

        logger.debug("Inicia servicio consultarListaAhorrosPreviosPostulacion(Long idPostulacion)");
        List<AhorroPrevioModeloDTO> ahorroPrevioModeloDTO = new ArrayList<>();
        try {
            ahorroPrevioModeloDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_AHORROS_PREVIOS, AhorroPrevioModeloDTO.class)
                    .setParameter("idPostulacion", idPostulacion).getResultList();

            return ahorroPrevioModeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio consultarListaAhorrosPreviosPostulacion(Long idPostulacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarListaRecursosComplementariosPostulacion(java.lang.Long)
     */
    @Override
    public List<RecursoComplementarioModeloDTO> consultarListaRecursosComplementariosPostulacion(Long idPostulacion) {

        logger.debug("Inicia servicio consultarListaRecursosComplementariosPostulacion(Long idPostulacion)");
        List<RecursoComplementarioModeloDTO> complementarioModeloDTO = new ArrayList<>();
        try {
            complementarioModeloDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_RECURSOS_COMPLEMENTARIOS, RecursoComplementarioModeloDTO.class)
                    .setParameter("idPostulacion", idPostulacion).getResultList();

            return complementarioModeloDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio consultarListaRecursosComplementariosPostulacion(Long idPostulacion)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarListaRecursosComplementariosPostulacion(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean existeLicenciaMatricula(String matriculaInmobiliaria) {
        logger.debug("Inicia servicio existeLicenciaMatricula(String matriculaInmobiliaria)");
        try {
            /*Si la licencia es nueva se valida que no exista una con la misma Matrícula Inmobiliaria.*/
            List<BigInteger> idLicencias = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LICENCIA_POR_MATRICULA)
                .setParameter("matriculaInmobiliaria", matriculaInmobiliaria)
                .getResultList();
            if (idLicencias != null && !idLicencias.isEmpty()) {
                return true;
            } 
            return false;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio existeLicenciaMatricula(String matriculaInmobiliaria)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	
	/* (non-Javadoc)
	 * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarResultadosExistenciaHabitabilidad(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public VisitaDTO consultarResultadosExistenciaHabitabilidad(String numeroRadicacion) {
		String path = "consultarResultadosExistenciaHabitabilidad(String):List<VisitaDTO>";
		try {
			logger.debug(ConstantesComunes.INICIO_LOGGER + path);
			List<VisitaDTO> condicionesVisita = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADOS_EXISTENCIA_HABITABILIDAD,
							VisitaDTO.class)
					.setParameter("numeroRadicacion", numeroRadicacion).getResultList();
			VisitaDTO resultadoExistenciaHabitabilidad = new VisitaDTO();
			List<CondicionVisitaModeloDTO> listaCondiciones = new ArrayList<CondicionVisitaModeloDTO>();
			if (condicionesVisita == null || condicionesVisita.isEmpty()) {
			    logger.debug(ConstantesComunes.FIN_LOGGER + path);
			    return resultadoExistenciaHabitabilidad;
			}

			resultadoExistenciaHabitabilidad.setFechaVisita(condicionesVisita.get(0).getFechaVisita());
			resultadoExistenciaHabitabilidad
			    .setCodigoIdentificadorVisita(condicionesVisita.get(0).getCodigoIdentificadorVisita());
			resultadoExistenciaHabitabilidad.setIdVisita(condicionesVisita.get(0).getIdVisita());
			resultadoExistenciaHabitabilidad
			    .setNombresEncargadoVisita(condicionesVisita.get(0).getNombresEncargadoVisita());

			for (VisitaDTO visita : condicionesVisita) {
				if (visita.getIdVisita().equals(resultadoExistenciaHabitabilidad.getIdVisita())) {
				    listaCondiciones.add(visita.getCondicionDTO());
				}
			}
			resultadoExistenciaHabitabilidad.setListaCondiciones(listaCondiciones);

			logger.debug(ConstantesComunes.FIN_LOGGER + path);
			return resultadoExistenciaHabitabilidad;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

    @Override
    public CondicionesLegalizacionDesembolsoDTO consultarHistoricoDesembolsoPorNumeroRadicado(String numeroRadicacion) {
        String path = "consultarAnticiposDesembolsadosPorTipoNumeroID(TipoIdentificacionEnum,String):List<AnticipoLegalizacionDesembolsoDTO>";
        CondicionesLegalizacionDesembolsoDTO condicionDesembolso = new CondicionesLegalizacionDesembolsoDTO();
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // Se consulta el historico desembolsado
            Object[] desembolsado = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_DESEMBOLSOS_POR_TIPO_NUMERO_ID)
                    .setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();
            if (desembolsado != null) {
                condicionDesembolso.setValorAsignadoSFV(desembolsado[1] != null ? (BigDecimal)desembolsado[1] : null);
                condicionDesembolso.setValorAjusteIPCSFV(desembolsado[2] != null ? (BigDecimal)desembolsado[2] : null);
                condicionDesembolso.setValorDesembolsado(desembolsado[3] != null ? (BigDecimal)desembolsado[3] : null);
                condicionDesembolso.setValorPendienteDesembolsar(desembolsado[4] != null ? (BigDecimal)desembolsado[4] : null);
                condicionDesembolso.setValorSFVAjustado(desembolsado[6] != null ? (BigDecimal)desembolsado[6] : null);
            }
            // Se consulta el historico de solicitudes de legalizacion
            List<HistoricoLegalizacionFOVISDTO> listHistorico = consultarHistoricoLegalizacion(numeroRadicacion);
            condicionDesembolso.setListaAnticipos(listHistorico);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return condicionDesembolso;
        } catch (NoResultException e) {
            return condicionDesembolso;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	/* (non-Javadoc)
	 * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#consultarSubsidiosFOVISLegalizadosDesembolsados(com.asopagos.dto.fovis.ConsultarSubsidiosFOVISLegalizacionDTO)
	 */
	@Override
	public List<ConsultarSubsidiosFOVISLegalizacionDTO> consultarSubsidiosFOVISLegalizadosDesembolsados(
			ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO) {
		String path = "consultarSubsidiosFOVISLegalizadosDesembolsados(ConsultarSubsidiosFOVISLegalizacionDTO):List<SolicitudLegalizacionDesembolsoDTO>";
		try {
			logger.debug(ConstantesComunes.INICIO_LOGGER + path);
			List<ConsultarSubsidiosFOVISLegalizacionDTO> listaSubsidios = new ArrayList<ConsultarSubsidiosFOVISLegalizacionDTO>();
			if (consultarSubsidiosFOVISLegalizacionDTO.getFechaInicio() != null
					&& consultarSubsidiosFOVISLegalizacionDTO.getFechaFin() != null) {
				listaSubsidios = obtenerSubsidiosFOVISLegalizadosDesembolsadosRangoFechas(
						consultarSubsidiosFOVISLegalizacionDTO);
			} else {
				listaSubsidios = obtenerSubsidiosFOVISLegalizadosDesembolsados(consultarSubsidiosFOVISLegalizacionDTO);
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + path);
			return listaSubsidios;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}
	
	/**
	 * Obtiene la lista de subsidios legalizados por los filtros ingresados
	 * 
	 * @param consultarSubsidiosFOVISLegalizacionDTO
	 *            DTO con los filtros para realizar la busqueda
	 * @return List<ConsultarSubsidiosFOVISLegalizacionDTO> con los subsidios
	 *         que corresponden a los filtros de busqueda ingresados
	 */
	@SuppressWarnings("unchecked")
	private List<ConsultarSubsidiosFOVISLegalizacionDTO> obtenerSubsidiosFOVISLegalizadosDesembolsados(
			ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO) {
		String path = "obtenerSubsidiosFOVISLegalizadosDesembolsados(ConsultarSubsidiosFOVISLegalizacionDTO):List<SolicitudLegalizacionDesembolsoDTO>";
		try {
			logger.debug(ConstantesComunes.INICIO_LOGGER + path);
			List<ConsultarSubsidiosFOVISLegalizacionDTO> solicitudesLegalizacion = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUBSIDIOS_FOVIS_LEGALIZADOS_DESEMBOLSADOS)
					.setParameter("idCiclo", consultarSubsidiosFOVISLegalizacionDTO.getIdCicloAsignacion())
					.setParameter("estadoHogar",
							consultarSubsidiosFOVISLegalizacionDTO.getEstadoHogar() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getEstadoHogar().name() : null)
					.setParameter("estadoSolicitudLegalizacion",
							consultarSubsidiosFOVISLegalizacionDTO.getEstadoSolicitud() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getEstadoSolicitud().name() : null)
					.setParameter("numeroRadicacionLegalizacion",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroSolicitudLegalizacion())
					.setParameter("numeroRadicacionPostulacion",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroSolicitudPostulacion())
					.setParameter("tipoIdentificacionJefe",
							consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionJefe() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionJefe().name() : null)
					.setParameter("numeroIdentificacionJefe",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroIdentificacionJefe())
					.setParameter("primerNombre", consultarSubsidiosFOVISLegalizacionDTO.getNombreJefeHogar())
					.setParameter("primerApellido", consultarSubsidiosFOVISLegalizacionDTO.getApellidoJefeHogar())
					.setParameter("idDepartamento", consultarSubsidiosFOVISLegalizacionDTO.getDepartamento())
					.setParameter("idMunicipio", consultarSubsidiosFOVISLegalizacionDTO.getMunicipio())
					.setParameter("tipoIdentificacionOf",
							consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionOferente() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionOferente().name()
									: null)
					.setParameter("numeroIdentificacionOf",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroIdentificacionOferente())
					.setParameter("nombreRazon", consultarSubsidiosFOVISLegalizacionDTO.getRazonSocialNombreOferente())
					.setParameter("idProyecto", consultarSubsidiosFOVISLegalizacionDTO.getProyectoVivienda())
					.setParameter("idDeptoProyecto", consultarSubsidiosFOVISLegalizacionDTO.getDepartamentoSolucion())
					.setParameter("idMunicipioProyecto", consultarSubsidiosFOVISLegalizacionDTO.getMunicipioSolucion())
					.getResultList();

			logger.debug(ConstantesComunes.FIN_LOGGER + path);
			return solicitudesLegalizacion;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Obtiene la lista de subsidios legalizados por los filtros ingresados y
	 * rango de fechas
	 * 
	 * @param consultarSubsidiosFOVISLegalizacionDTO
	 *            DTO con los filtros para realizar la busqueda
	 * @return List<ConsultarSubsidiosFOVISLegalizacionDTO> con los subsidios
	 *         que corresponden a los filtros de busqueda ingresados
	 */
	@SuppressWarnings("unchecked")
	private List<ConsultarSubsidiosFOVISLegalizacionDTO> obtenerSubsidiosFOVISLegalizadosDesembolsadosRangoFechas(
			ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO) {
		String path = "obtenerSubsidiosFOVISLegalizadosDesembolsadosRangoFechas(ConsultarSubsidiosFOVISLegalizacionDTO):List<SolicitudLegalizacionDesembolsoDTO>";
		try {
			logger.debug(ConstantesComunes.INICIO_LOGGER + path);
			List<ConsultarSubsidiosFOVISLegalizacionDTO> solicitudesLegalizacion = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUBSIDIOS_FOVIS_LEGALIZADOS_DESEMBOLSADOS_RANGO_FECHAS)
					.setParameter("idCiclo", consultarSubsidiosFOVISLegalizacionDTO.getIdCicloAsignacion())
					.setParameter("estadoHogar",
							consultarSubsidiosFOVISLegalizacionDTO.getEstadoHogar() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getEstadoHogar().name() : null)
					.setParameter("estadoSolicitudLegalizacion",
							consultarSubsidiosFOVISLegalizacionDTO.getEstadoSolicitud() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getEstadoSolicitud().name() : null)
					.setParameter("numeroRadicacionLegalizacion",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroSolicitudLegalizacion())
					.setParameter("numeroRadicacionPostulacion",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroSolicitudPostulacion())
					.setParameter("tipoIdentificacionJefe",
							consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionJefe() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionJefe().name() : null)
					.setParameter("numeroIdentificacionJefe",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroIdentificacionJefe())
					.setParameter("primerNombre", consultarSubsidiosFOVISLegalizacionDTO.getNombreJefeHogar())
					.setParameter("primerApellido", consultarSubsidiosFOVISLegalizacionDTO.getApellidoJefeHogar())
					.setParameter("idDepartamento", consultarSubsidiosFOVISLegalizacionDTO.getDepartamento())
					.setParameter("idMunicipio", consultarSubsidiosFOVISLegalizacionDTO.getMunicipio())
					.setParameter("tipoIdentificacionOf",
							consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionOferente() != null
									? consultarSubsidiosFOVISLegalizacionDTO.getTipoIdentificacionOferente().name()
									: null)
					.setParameter("numeroIdentificacionOf",
							consultarSubsidiosFOVISLegalizacionDTO.getNumeroIdentificacionOferente())
					.setParameter("nombreRazon", consultarSubsidiosFOVISLegalizacionDTO.getRazonSocialNombreOferente())
					.setParameter("idProyecto", consultarSubsidiosFOVISLegalizacionDTO.getProyectoVivienda())
					.setParameter("idDeptoProyecto", consultarSubsidiosFOVISLegalizacionDTO.getDepartamentoSolucion())
					.setParameter("idMunicipioProyecto", consultarSubsidiosFOVISLegalizacionDTO.getMunicipioSolucion())
					.setParameter("fechaInicio",
							consultarSubsidiosFOVISLegalizacionDTO.getFechaInicio() != null
									? new Date(consultarSubsidiosFOVISLegalizacionDTO.getFechaInicio()) : null)
					.setParameter("fechaFin",
							consultarSubsidiosFOVISLegalizacionDTO.getFechaFin() != null
									? new Date(consultarSubsidiosFOVISLegalizacionDTO.getFechaFin()) : null)
					.getResultList();

			logger.debug(ConstantesComunes.FIN_LOGGER + path);
			return solicitudesLegalizacion;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoricoLegalizacionFOVISDTO> consultarHistoricoLegalizacion(String numeroRadicacion) {
        String path = "consultarHistoricoLegalizacion(TipoIdentificacionEnum, String): List<HistoricoLegalizacionFOVISDTO>";
        List<HistoricoLegalizacionFOVISDTO> resultHistorico = new ArrayList<>();
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);

            List<Object[]> listResult = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_LEGALIZACIONES_POSTULACION_JEFE_HOGAR_TIPO_NRO_DOC)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            for (Object[] registro : listResult) {
                HistoricoLegalizacionFOVISDTO historico = new HistoricoLegalizacionFOVISDTO();
                // Info Tabla
                historico.setNumeroRadicacion(registro[0] != null ? registro[0].toString() : null);
                historico.setFechaRadicacion(registro[1] != null ? (Date) registro[1] : null);
                historico.setFormaPago(registro[2] != null ? FormaPagoEnum.valueOf(registro[2].toString()) : null);
                historico.setValorADesembolsar(registro[3] != null ? (BigDecimal) registro[3] : null);
                historico.setEstadoSolicitud(
                        registro[4] != null ? EstadoSolicitudLegalizacionDesembolsoEnum.valueOf(registro[4].toString()) : null);
                historico.setFechaOperacion(registro[5] != null ? (Date) registro[5] : null);
                // Info común formulario
                BigInteger idSolLega = (BigInteger) registro[6];
                historico.setIdSolicitudLegalizacionDesembolso(registro[6] != null ? idSolLega.longValue() : null);
                
                ObjectMapper mapper = new ObjectMapper();
                historico.setDatosPostulacionFovis(registro[7] != null ? mapper.readValue(registro[7].toString(),SolicitudPostulacionFOVISDTO.class) : null);
                
                BigInteger idPostulacionFOVIS = (BigInteger) registro[8];
                historico.setIdPostulacionFOVIS(registro[8] != null ? idPostulacionFOVIS.longValue() : null);
                
                BigInteger idLegalizacionDesembolso = (BigInteger) registro[9];
                historico.setIdLegalizacionDesembolso(registro[9] != null ? idLegalizacionDesembolso.longValue() : null);
                
                historico.setTipoMedioPago(registro[10] != null ? TipoMedioDePagoEnum.valueOf(registro[10].toString()) : null);
                historico.setFechaLimitePago(registro[11] != null ? (Date) registro[11] : null);
                historico.setSubsidioDesembolsado(registro[12] != null ? (Boolean) registro[12] : null);
                historico.setTipoTransaccion(registro[13] != null ? TipoTransaccionEnum.valueOf(registro[13].toString()) : null);
                
                BigInteger idVisita = (BigInteger) registro[14];
                historico.setIdVisita(registro[14] != null ? idVisita.longValue() : null);
                
                historico.setIncluyeCertificadoExistenciaHabitabilidad(getBooleanDato(registro[15]));
                historico.setNoCumplioCondicionesHabitablidad(getBooleanDato(registro[16]));
                historico.setRegistroPNCNoResuelto(getBooleanDato(registro[17]));
                historico.setResultadoProceso(registro[18] != null ? ResultadoProcesoEnum.valueOf(registro[18].toString()) : null);
                historico.setNumeroResolucionAsignacion(registro[19] != null ? registro[19].toString() : null);
                historico.setFechaResolucionAsignacion(registro[20] != null ? (Date) registro[20] : null);
                historico.setClasificacion(registro[21] != null ? ClasificacionEnum.valueOf(registro[21].toString()) : null);
                historico.setIdSolicitudGlobal(registro[22] != null ? ((BigInteger) registro[22]).longValue() : null);
                resultHistorico.add(historico);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return resultHistorico;
        } catch (NoResultException e) {
            return resultHistorico;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Obtiene el valor booleano del objeto enviado por parametro
     * @param objeto
     *        Informacion
     * @return Valor booleano
     */
    private Boolean getBooleanDato(Object objeto) {
        if (objeto != null && objeto instanceof Integer) {
            Integer numero = (Integer) objeto;
            return numero.equals(NumerosEnterosConstants.UNO);
        }
        return false;
    }

    @Override
    public CondicionesLegalizacionDesembolsoDTO calcularValorRestituir(String numeroRadicadoPostulacion) {
        String path = "calcularValorRestituir(String): Double";
        CondicionesLegalizacionDesembolsoDTO condicionDesembolso = new CondicionesLegalizacionDesembolsoDTO();
        condicionDesembolso.setValorDesembolsado(BigDecimal.ZERO);
        try {
            // Se consulta el historico desembolsado
            Object[] desembolsado = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_DESEMBOLSOS_POR_TIPO_NUMERO_ID)
                    .setParameter("numeroRadicacion", numeroRadicadoPostulacion).getSingleResult();
            if (desembolsado != null) {
                condicionDesembolso.setValorAsignadoSFV(desembolsado[1] != null ? (BigDecimal)desembolsado[1] : BigDecimal.ZERO);
                condicionDesembolso.setValorAjusteIPCSFV(desembolsado[2] != null ? (BigDecimal)desembolsado[2] : BigDecimal.ZERO);
                condicionDesembolso.setValorDesembolsado(desembolsado[3] != null ? (BigDecimal)desembolsado[3] : BigDecimal.ZERO);
                condicionDesembolso.setValorPendienteDesembolsar(desembolsado[4] != null ? (BigDecimal)desembolsado[4] : BigDecimal.ZERO);
                condicionDesembolso.setFechaTransferenciaDesembolso(desembolsado[5] != null ? (Date) desembolsado[5] : null);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return condicionDesembolso;
        } catch (NoResultException e){
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return condicionDesembolso;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

}
