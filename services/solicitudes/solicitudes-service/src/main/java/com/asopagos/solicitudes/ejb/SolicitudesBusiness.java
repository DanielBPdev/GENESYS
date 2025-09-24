package com.asopagos.solicitudes.ejb;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.Solicitud360DTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.EscalamientoSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.aportes.RegistroEstadoAporte;
import com.asopagos.entidades.ccf.general.DocumentoAdministracionEstadoSolicitud;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.core.TipoSolicitud360Enum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.solicitudesrutines.guardardatostemporalespersona.GuardarDatosTemporalesPersonaRutine;
import com.asopagos.solicitudes.constants.NamedQueriesConstants;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import com.asopagos.solicitudes.service.SolicitudesService;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.util.ObtenerTipoSolicitud;
import com.asopagos.dto.TipoEspecificoSolicitudDTO;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.dto.InformacionArchivoDTO;

import com.asopagos.afiliaciones.personas.web.composite.clients.CancelarSolicitudPersonasWebTimeout;
import com.asopagos.afiliaciones.personas.web.composite.dto.CancelacionSolicitudPersonasDTO;

import org.apache.commons.lang3.StringUtils;
 //Importaciones para la optimizaciones del json
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonArray;
 import com.google.gson.JsonParser;
 import com.google.gson.JsonPrimitive;
 import com.google.gson.reflect.TypeToken;
 import java.lang.ProcessBuilder.Redirect.Type;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Map;
 //FIN Importaciones para la optimizaciones del json 
/**
 * <b>Descripci�n:</b> Interfaz de servicios Web REST para adminsitraci�n de
 * solicitudes <b>Historia de Usuario:</b> Transversal - Solicitudes
 * 
 * @author Sergio Bri�ez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class SolicitudesBusiness implements SolicitudesService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "solicitudes_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(SolicitudesBusiness.class);

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#escalarSolicitud(java.lang.Long,
     *      com.asopagos.dto.EscalamientoSolicitudDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void escalarSolicitud(Long idSolicitud, EscalamientoSolicitudDTO escalamientoSolicitud) {
        logger.info("Inicio escalarSolicitud(Long, EscalamientoSolicitudDTO)");
        try {
            // Verifica que no exista una solicitud de escalamiento ya asociada.
            Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ASIGNACION_ESCALADA);
            q.setParameter("idSolicitud", idSolicitud);

            List<EscalamientoSolicitud> list = q.getResultList();
            if (list.isEmpty()) {
                escalamientoSolicitud.setIdSolicitud(idSolicitud);
                entityManager.persist(
                        EscalamientoSolicitudDTO.convertEscalamientoSolicitudDTOToEntity(escalamientoSolicitud));
            } else {
                // en caso de existir una solicitud el sistema alerta con un
                // mensaje
                logger.error("Finaliza escalarSolicitud(Long, EscalamientoSolicitudDTO): error: "
                        + MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
            }

        } catch (Exception e) {
            logger.debug("Finaliza escalarSolicitud(Long, EscalamientoSolicitudDTO): error", e);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO, e);
        }
        logger.debug("Finaliza escalarSolicitud(Long, EscalamientoSolicitudDTO)");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#actualizarSolicitudEscalada(java.lang.Long,
     *      com.asopagos.dto.EscalamientoSolicitudDTO)
     */
    @Override
    public void actualizarSolicitudEscalada(Long idSolicitud, EscalamientoSolicitudDTO escalamientoSolAfilEmpleador) {
        logger.debug("Inicio actualizarSolicitudEscalada(Long, EscalamientoSolicitudDTO)");
        try {
            entityManager.merge(
                    EscalamientoSolicitudDTO.convertEscalamientoSolicitudDTOToEntity(escalamientoSolAfilEmpleador));
        } catch (Exception e) {
            logger.debug("Finaliza actualizarSolicitudEscalada(Long, EscalamientoSolicitudDTO): error: ", e);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }
        logger.debug("Finaliza actualizarSolicitudEscalada(Long, EscalamientoSolicitudDTO)");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#consultarSolicitudEscalada(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public EscalamientoSolicitudDTO consultarSolicitudEscalada(Long idSolicitud) {
        logger.debug("Inicia consultarSolicitudEscalada(Long)");
        List<EscalamientoSolicitud> list;
        EscalamientoSolicitud consulta;

        // Verifica que no exista una solicitud de escalamiento ya asociada.
        Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ASIGNACION_ESCALADA);
        q.setParameter("idSolicitud", idSolicitud);

        list = q.getResultList();
        if (!list.isEmpty()) {
            consulta = list.get(0);
            logger.debug("Finaliza consultarSolicitudEscalada(Long)");
            return EscalamientoSolicitudDTO.convertEscalamientoSolicitudToDTO(consulta);
        } else {
            logger.debug("Finaliza consultarSolicitudEscalada(Long)");
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#guardarDatosTemporales(java.lang.Long,
     *      java.lang.String)
     */
    @Override
public Long guardarDatosTemporales(Long idSolicitud, String jsonPayload) {
    logger.info("Inicio guardarDatosTemporales(Long, String)");

    if (idSolicitud != null && jsonPayload != null && !jsonPayload.equals("")) {
        DatoTemporalSolicitud datoTemporalSolicitud = buscarTemporales(idSolicitud);
        //logger.info("jsonPayload" + datoTemporalSolicitud.getJsonPayload() + datoTemporalSolicitud.getCreoArchivo());

        if (datoTemporalSolicitud != null) {
            if (jsonPayload.length() > 65535) {
                String idArchivo = crearArchivo(jsonPayload);
                datoTemporalSolicitud.setJsonPayload(idArchivo);
                datoTemporalSolicitud.setCreoArchivo((short) 1);
                //logger.info("jsonPayload" + datoTemporalSolicitud.getJsonPayload() + datoTemporalSolicitud.getCreoArchivo());
            } else {
                datoTemporalSolicitud.setJsonPayload(jsonPayload);
                datoTemporalSolicitud.setCreoArchivo((short) 0);
            }
            entityManager.merge(datoTemporalSolicitud);
        } else {
            datoTemporalSolicitud = new DatoTemporalSolicitud();
            datoTemporalSolicitud.setSolicitud(idSolicitud);
            if (jsonPayload.length() > 65535) {
                String idArchivo = crearArchivo(jsonPayload);
                datoTemporalSolicitud.setJsonPayload(idArchivo);
                datoTemporalSolicitud.setCreoArchivo((short) 1);
                //logger.info("jsonPayload" + datoTemporalSolicitud.getJsonPayload() + datoTemporalSolicitud.getCreoArchivo());
            } else {
                datoTemporalSolicitud.setJsonPayload(jsonPayload);
                datoTemporalSolicitud.setCreoArchivo((short) 0);
                //logger.info("jsonPayload" + datoTemporalSolicitud.getJsonPayload() + datoTemporalSolicitud.getCreoArchivo());
            }
            entityManager.persist(datoTemporalSolicitud);
        }
        logger.debug("Finaliza guardarDatosTemporales(Long, String)");
        return datoTemporalSolicitud.getIdDatoTemporalSolicitud();
    } else {
        logger.debug("Finaliza guardarDatosTemporales(Long, String): error: "
                + MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
    }
}

private String crearArchivo(String jsonPayload) {
    InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
    infoFile.setDataFile(jsonPayload.getBytes());
    infoFile.setFileType("text/plain");
    infoFile.setProcessName("guardarDatosTemporales");
    infoFile.setDescription("Archivo de datos temporales");
    infoFile.setDocName("datosTemporales.txt");
    infoFile.setFileName("datosTemporales.txt");
    String idArchivo = almacenarArchivo(infoFile);
    return idArchivo;
}

private String almacenarArchivo(InformacionArchivoDTO infoFile) {
    AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
    almacenarArchivo.execute();
    InformacionArchivoDTO archivo = almacenarArchivo.getResult();
    StringBuilder idECM = new StringBuilder();
    idECM.append(archivo.getIdentificadorDocumento());
    idECM.append("_");
    idECM.append(archivo.getVersionDocumento());
    return idECM.toString();
}

    // private static void buscarYProcesarFecha(JsonObject jsonObject) {

    //     String palabraClave = "fecha";
    //     // JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

    //     for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
    //         String clave = entry.getKey();
    //         JsonElement valor = entry.getValue();

    //         if (clave.contains(palabraClave)) {
    //             System.out.println("Clave que contiene 'fecha': " + clave);
    //             String fecha = valor.getAsString();
    //             try {
    //                 BigDecimal millis = new BigDecimal(fecha);
    //                 // Long millis = Long.parseLong(fecha);
    //                 DecimalFormat decimalFormat = new DecimalFormat("0.#####");
    //                 String numeroNotacionNormal = decimalFormat.format(millis);
    //                 System.out.println("Clave que contiene 'millis': " + millis);
    //                 JsonElement milise = JsonParser.parseString(numeroNotacionNormal);
    //                 entry.setValue(milise);
    //             } catch (NumberFormatException e) {
    //                 // Manejar error de conversión, puedes decidir qué hacer en este caso
    //             }
    //             System.out.println("Clave que contiene 'fecha' transformada: " + entry.getValue());

    //         }

    //         if (valor.isJsonObject()) {
    //             buscarYProcesarFecha(valor.getAsJsonObject());
    //         } else if (valor.isJsonArray()) {
    //             JsonArray jsonArray = valor.getAsJsonArray();
    //             for (JsonElement elemento : jsonArray) {
    //                 if (elemento.isJsonObject()) {
    //                     buscarYProcesarFecha(elemento.getAsJsonObject());
    //                 }
    //             }
    //         }
    //     }
    // }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#consultarDatosTemporales(java.lang.Long)
     */
    @Override
    public String consultarDatosTemporales(Long idSolicitud) {
        logger.info("Inicia consultarDatosTemporales(Long)" + idSolicitud);
        if (idSolicitud != null) {
            DatoTemporalSolicitud datoTemporalSolicitud = buscarTemporales(idSolicitud);
            if (datoTemporalSolicitud != null) {
                if( datoTemporalSolicitud.getCreoArchivo() != null && datoTemporalSolicitud.getCreoArchivo() == 1 ){
                    InformacionArchivoDTO archivo = obtenerArchivo(datoTemporalSolicitud.getJsonPayload());
                    if (archivo.getDataFile() != null &&  archivo.getDataFile().length > 0) {
                        try {
                            String jsonArchivo = new String(archivo.getDataFile(), "UTF-8");
                            //datoTemporalSolicitud.setJsonPayload(jsonArchivo);
                            return jsonArchivo;
                        } catch (UnsupportedEncodingException e) {
                        datoTemporalSolicitud.setJsonPayload(null); 
                    }
                    }else {
                        logger.warn("El archivo está vacío o es nulo");
                        datoTemporalSolicitud.setJsonPayload(null);
                    }
                }
                return datoTemporalSolicitud.getJsonPayload();
            } else {
                logger.debug("Finaliza consultarDatosTemporales(Long): error: "
                        + MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
                return null;
            }
        } else {
            logger.debug("Finaliza consultarDatosTemporales(Long): error: "
                    + MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
    }
    
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia obtenerArchivo(String)");
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }    

    /**
     * M�todo que busca los datos temporales de la solicitud
     * 
     * @return entidad que contiene los datos temporales
     */
    private DatoTemporalSolicitud buscarTemporales(Long idSolicitud) {
        logger.debug("Inicia buscarTemporales(Long)");
        logger.info("Inicia buscarTemporales(Long) V 385" +idSolicitud);
        Query q = entityManager.createNamedQuery(NamedQueriesConstants.SOLICITUDES_CONSULTAR_DATOS_TEMPORALES)
                .setParameter("idSolicitud", idSolicitud);
      //logger.info("Solcitudes --q 252-- " + q.getSingleResult());
        DatoTemporalSolicitud datoTemporalSolicitud;
        try {
            datoTemporalSolicitud = (DatoTemporalSolicitud) q.getSingleResult();
            logger.info("Solcitudes --datoTemporalSolicitud 252-- " + datoTemporalSolicitud);
        } catch (NoResultException e) {
            datoTemporalSolicitud = null;
        }
        logger.debug("Finaliza buscarTemporales(Long)");
        return datoTemporalSolicitud;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#
     * guardarDocumentosAdminSolicitudes(java.lang.String, java.util.List)
     */
    @Override
    public void guardarDocumentosAdminSolicitudes(String numeroRadicado,
            List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds) {

        logger.debug(
                "guardarDocumentosAdminSolicitudes(String numeroRadicado,List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds)");

        try {
            logger.info("numeroRadicado"+numeroRadicado);
            SolicitudDTO solicitud = (SolicitudDTO) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD_POR_NUMERO_RADICADO)
                    .setParameter("numeroRadicacion", numeroRadicado).getSingleResult();

            logger.info("Pasa consulta");

            for (DocumentoAdministracionEstadoSolicitudDTO docAdminiEstadoSolicitudDTO : administracionEstadoSolicituds) {
                if (docAdminiEstadoSolicitudDTO.getIdentificadorDocumentoSoporteCambioEstado() != null) {
                    logger.info("Genera error en documentos"+solicitud.getIdSolicitud());
                    docAdminiEstadoSolicitudDTO.setIdSolicitudGlobal(solicitud.getIdSolicitud());
                    DocumentoAdministracionEstadoSolicitud adminDoc = docAdminiEstadoSolicitudDTO
                            .convertToEntity(docAdminiEstadoSolicitudDTO);
                    logger.info("Antes de persistir");
                    entityManager.persist(adminDoc);
                    logger.info("Persiste");
                }
            }
            logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO)");

        } catch (NoResultException e) {
            logger.error("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO) con error", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO) con error", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO) con error", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#
     * actualizarDestinatarioSolicitud(java.lang.String, java.lang.String)
     */
    @Override
    public void actualizarDestinatarioSolicitud(String idInstanciaProceso, String usuario) {
        logger.debug("Inicia actualizarDestinatarioSolicitud(Long idSolicitud, String usuario)");
        List<Solicitud> solicitudes = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD_POR_INSTANCIA_PROCESO, Solicitud.class)
                .setParameter("idInstanciaProceso", idInstanciaProceso).getResultList();
        if (solicitudes != null && !solicitudes.isEmpty()) {
            Solicitud sol = solicitudes.iterator().next();
            sol.setDestinatario(usuario);
            entityManager.merge(sol);
        }
        logger.debug("Finaliza actualizarDestinatarioSolicitud(Long idSolicitud, String usuario)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.solicitudes.service.SolicitudesService#crearTrazabilidad(com
     * .asopagos.dto.modelo.TrazabilidadModeloDTO)
     */
    @Override
    public void crearTrazabilidad(RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO) {
        logger.debug("Inicio de m�todo crearTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
        try {
            RegistroEstadoAporte registroEstadoAporte = registroEstadoAporteModeloDTO.convertToEntity();
            entityManager.persist(registroEstadoAporte);
            logger.debug("Fin de m�todo crearTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
        } catch (Exception e) {
            logger.error("Ocurri� un error inesperado en crearTrazabilidad: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.solicitudes.service.SolicitudesService#consultarTrazabilidad
     * (java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RegistroEstadoAporteModeloDTO> consultarTrazabilidad(Long idSolicitud) {
        logger.debug("Inicio de m�todo consultarTrazabilidad(Long idSolicitud)");
        try {
            List<RegistroEstadoAporte> registroEstadoAportes = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRAZABILIDAD_ID)
                    .setParameter("idSolicitud", idSolicitud).getResultList();
            List<RegistroEstadoAporteModeloDTO> trazabilidadesdModeloDTO = new ArrayList<>();
            for (RegistroEstadoAporte registroEstadoAporte : registroEstadoAportes) {
                RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO = new RegistroEstadoAporteModeloDTO();
                registroEstadoAporteModeloDTO.convertToDTO(registroEstadoAporte);
                trazabilidadesdModeloDTO.add(registroEstadoAporteModeloDTO);
            }
            logger.debug("Fin de m�todo consultarTrazabilidad(Long idSolicitud)");
            return trazabilidadesdModeloDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurri� un error inesperado en consultarTrazabilidad: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SolicitudAfiliacionEmpleador> consultarDatosTempPorPersona(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.debug(
                "Inicia consultarDatosTempPorPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        List<EstadoSolicitudAfiliacionEmpleadorEnum> estados = new ArrayList<>();
        estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);

        Query consultarSolTemporal = entityManager
                .createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL_ESTADO_TEMPORAL)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("estadosSolicitud", estados);

        List<SolicitudAfiliacionEmpleador> sate = consultarSolTemporal.getResultList();
        if (sate != null && !sate.isEmpty()) {
            return sate;
        } else {
            logger.debug(
                    "Finaliza consultarDatosTempPorPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.solicitudes.service.SolicitudesService#consultarDocumentos(
     * java.lang.Long, com.asopagos.enumeraciones.aportes.ActividadEnum)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DocumentoAdministracionEstadoSolicitudDTO> consultarDocumentos(Long idSolicitud,
            ActividadEnum actividad) {
        try {
            logger.debug("Inicia consultarDocumentos(Long idSolicitud, ActividadEnum actividad)");
            List<DocumentoAdministracionEstadoSolicitud> documentos = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_DOCUMENTO_ADMIN_ESTADO)
                    .setParameter("idSolicitud", idSolicitud).setParameter("actividad", actividad).getResultList();
            List<DocumentoAdministracionEstadoSolicitudDTO> documentosDTO = new ArrayList<>();
            for (DocumentoAdministracionEstadoSolicitud documento : documentos) {
                DocumentoAdministracionEstadoSolicitudDTO documentoDTO = new DocumentoAdministracionEstadoSolicitudDTO();
                documentoDTO.convertToDTO(documento);
                documentosDTO.add(documentoDTO);
            }
            logger.debug("Inicia consultarDocumentos(Long idSolicitud, ActividadEnum actividad)");
            return documentosDTO;
        } catch (Exception e) {
            logger.error("Ocurri� un error inesperado en consultarTrazabilidad: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#actualizarTrazabilidad(com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO)
     */
    @Override
    public void actualizarTrazabilidad(RegistroEstadoAporteModeloDTO registroEstadoAporteModeloDTO) {
        logger.debug("Inicio de m�todo actualizarTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
        try {
            RegistroEstadoAporte registroEstadoAporte = registroEstadoAporteModeloDTO.convertToEntity();
            entityManager.merge(registroEstadoAporte);
            logger.debug("Fin de m�todo actualizarTrazabilidad(TrazabilidadModeloDTO trazabilidadDTO)");
        } catch (Exception e) {
            logger.error("Ocurri� un error inesperado en actualizarTrazabilidad: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#
     * consultarTrazabilidadPorId(java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
    public RegistroEstadoAporteModeloDTO consultarTrazabilidadPorActividad(Long idSolicitud, ActividadEnum actividad) {
        logger.debug("Inicio de m�todo consultarTrazabilidadPorId(Long idTrazabilidad)");
        RegistroEstadoAporteModeloDTO trazabilidad = null;
        try {
            List<RegistroEstadoAporte> registrosTrazabilidad = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRAZABILIDAD_POR_SOLICITUD_ACTIVIDAD)
                    .setParameter("idSolicitud", idSolicitud).setParameter("actividad", actividad).setMaxResults(1)
                    .getResultList();
            if (!registrosTrazabilidad.isEmpty()) {
                trazabilidad = new RegistroEstadoAporteModeloDTO();
                trazabilidad.convertToDTO(registrosTrazabilidad.get(0));

            }
            logger.debug("Fin de m�todo consultarTrazabilidadPorId(Long idTrazabilidad)");
            return trazabilidad;

        } catch (Exception e) {
            logger.error("Ocurri� un error inesperado en consultarTrazabilidadPorId(Long idTrazabilidad): ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#consultarEscalamientosSolicitud(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<EscalamientoSolicitudDTO> consultarEscalamientosSolicitud(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarEscalamientosSolicitud(Long idSolicitudGlobal)");
        try {
            List<EscalamientoSolicitudDTO> escalamientosDTO = new ArrayList<>();

            List<EscalamientoSolicitud> escalamientos = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_ASIGNACION_ESCALADA)
                    .setParameter("idSolicitud", idSolicitudGlobal).getResultList();

            if (escalamientos != null && !escalamientos.isEmpty()) {
                for (EscalamientoSolicitud escalamientoSolicitud : escalamientos) {
                    EscalamientoSolicitudDTO escalamientoSolicitudDTO = EscalamientoSolicitudDTO
                            .convertEscalamientoSolicitudToDTO(escalamientoSolicitud);
                    escalamientosDTO.add(escalamientoSolicitudDTO);
                }
            }
            logger.debug("Finaliza el servicio de consultarEscalamientosSolicitud(Long idSolicitudGlobal)");
            return escalamientosDTO;
        } catch (Exception e) {
            logger.error(
                    "Ocurri� un error inesperado en el servicio consultarEscalamientosSolicitud(Long idSolicitudGlobal)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.service.AportesService#consultarSolicitudGlobal(java.lang.Long)
     */
    @Override
    public SolicitudModeloDTO consultarSolicitudGlobal(Long idSolicitud) {
        try {
            logger.debug("Inicia servicio consultarSolicitudGlobal");
            Solicitud solicitud = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD, Solicitud.class)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
            solicitudDTO.convertToDTO(solicitud);
            logger.debug("Finaliza servicio consultarSolicitudGlobal");
            return solicitudDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurri� un error en el servicio consultarSolicitudGlobal", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.solicitudes.service.SolicitudesService#escalarSolicitudFOVIS
     * (java.lang.Long, com.asopagos.dto.EscalamientoSolicitudDTO)
     */
    @Override
    public EscalamientoSolicitudDTO registrarEscalamientoSolicitud(Long idSolicitud,
            EscalamientoSolicitudDTO escalamientoSolicitud) {
        logger.debug("Inicio escalarSolicitud(Long, EscalamientoSolicitudDTO)");
        try {
            escalamientoSolicitud.setIdSolicitud(idSolicitud);
            EscalamientoSolicitud managed = EscalamientoSolicitudDTO
                    .convertEscalamientoSolicitudDTOToEntity(escalamientoSolicitud);
            if (managed.getIdEscalamientoSolicitud() != null) {
                entityManager.merge(managed);
            } else {
                entityManager.persist(managed);
            }
            escalamientoSolicitud.setIdEscalamientoSolicitud(managed.getIdEscalamientoSolicitud());
            logger.debug("Finaliza escalarSolicitud(Long, EscalamientoSolicitudDTO)");
            return escalamientoSolicitud;
        } catch (Exception e) {
            logger.debug("Finaliza escalarSolicitud(Long, EscalamientoSolicitudDTO): error", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    @Override
    public Long guardarDatosTemporalesPersona(Long idSolicitud, String jsonPayload, String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        /*
        logger.debug("Inicio guardarDatosTemporalesPersona(Long, String, String, TipoIdentificacionEnum)");
        try {
            Long idTemporal = null;
            if (idSolicitud != null && jsonPayload != null && !jsonPayload.equals("")) {
                // Se consulta si existe dato temporal asociado a la solicitud
                DatoTemporalSolicitud datoTemporalSolicitud = buscarTemporales(idSolicitud);
                if (datoTemporalSolicitud != null) {
                    datoTemporalSolicitud.setJsonPayload(jsonPayload);
                    datoTemporalSolicitud.setNumeroIdentificacion(numeroIdentificacion);
                    datoTemporalSolicitud.setTipoIdentificacion(tipoIdentificacion);
                    entityManager.merge(datoTemporalSolicitud);
                } else {
                    datoTemporalSolicitud = new DatoTemporalSolicitud();
                    datoTemporalSolicitud.setSolicitud(idSolicitud);
                    datoTemporalSolicitud.setJsonPayload(jsonPayload);
                    datoTemporalSolicitud.setNumeroIdentificacion(numeroIdentificacion);
                    datoTemporalSolicitud.setTipoIdentificacion(tipoIdentificacion);
                    entityManager.persist(datoTemporalSolicitud);
                }
                idTemporal = datoTemporalSolicitud.getIdDatoTemporalSolicitud();
            }
            logger.debug("Finaliza guardarDatosTemporalesPersona(Long, String, String, TipoIdentificacionEnum)");
            return idTemporal;
        } catch (Exception e) {
            logger.debug(
                    "Finaliza guardarDatosTemporalesPersona(Long, String, String, TipoIdentificacionEnum) - Error: ",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        */
        
        GuardarDatosTemporalesPersonaRutine g = new GuardarDatosTemporalesPersonaRutine();
        return g.guardarDatosTemporalesPersona(idSolicitud, jsonPayload, numeroIdentificacion, tipoIdentificacion, entityManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.solicitudes.service.SolicitudesService#
     * consultarListaSolicitudes(com.asopagos.dto.Solicitud360DTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<Solicitud360DTO> consultarListaSolicitudes(Solicitud360DTO filtros) {
        try {
            logger.debug("Inicia servicio consultarListaSolicitudes");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String tipoIdentificacion = filtros.getTipoIdentificacion().name();
            String numeroIdentificacion = filtros.getNumeroIdentificacion();
            String tiposAportante = filtros.getTiposAportante().toString().replace("[","").replace("]","").replaceAll(" ", "");
            String numeroRadicado = filtros.getNumeroRadicado() == null ? "" : filtros.getNumeroRadicado();
            String tipoSolicitud = filtros.getTiposSolicitud().toString().replace("[","").replace("]","").replaceAll(" ", "");
            String estadoSolicitud = filtros.getEstadosSolicitud().toString().replace("[","").replace("]","").replaceAll(" ","");
            String fechaInicio = format.format(new Date(filtros.getFechaInicio() == null ? 1581522661 : filtros.getFechaInicio()));
            String fechaFin = format.format(filtros.getFechaFin() == null ? new Date() : new Date(filtros.getFechaFin()));
            String fechaRadicado = filtros.getFechaRadicado() == null ? "0" :  format.format(new Date(filtros.getFechaRadicado()));

            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_LISTA_SOLICITUDES)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tiposAportante", tiposAportante)
                    .setParameter("numeroRadicado", numeroRadicado)
                    .setParameter("tipoSolicitud", tipoSolicitud)
                    .setParameter("estadoSolicitud", estadoSolicitud)
                    .setParameter("fechaRadicado", fechaRadicado)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin);
            query.execute();
            logger.info("tipoIdentificacion :"+tipoIdentificacion);
            logger.info("numeroIdentificacion :"+numeroIdentificacion);
            logger.info("tiposAportante :"+tiposAportante);
            logger.info("numeroRadicado :"+numeroRadicado);
            logger.info("tipoSolicitud :"+tipoSolicitud);
            logger.info("estadoSolicitud :"+estadoSolicitud);
            logger.info("fechaRadicado :"+fechaRadicado);
            logger.info("fechaInicio :"+fechaInicio);
            logger.info("fechaFin :"+fechaFin);
            List<Object[]> lista = query.getResultList();
            List<Solicitud360DTO> listaDTO = new ArrayList<>();

            for (Object[] registro : lista) {
                Solicitud360DTO solicitudDTO = new Solicitud360DTO();
                solicitudDTO.setNumeroRadicado(registro[0] != null ? registro[0].toString() : null);
                solicitudDTO.setFechaRadicadoRespuesta(
                        registro[1] != null ? format.parse(registro[1].toString()).getTime() : null);
                solicitudDTO.setTipoSolicitudRespuesta(
                        registro[2] != null ? TipoSolicitud360Enum.valueOf(registro[2].toString()) : null);
                solicitudDTO.setEstadoSolicitudRespuesta(registro[3] != null ? registro[3].toString() : null);
                solicitudDTO.setIdSolicitud(registro[4] != null ? Long.parseLong(registro[4].toString()) : null);
                solicitudDTO
                        .setIdSolicitudAfiliacion(registro[5] != null ? Long.parseLong(registro[5].toString()) : null);
                solicitudDTO.setCanalSolicitud(registro[6] != null ? CanalRecepcionEnum.valueOf(registro[6].toString()) : null);
                listaDTO.add(solicitudDTO);
            }

            logger.debug("Finaliza servicio consultarListaSolicitudes");
            return listaDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el servicio consultarListaSolicitudes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void reasignarSolicitud(Long idProceso, String usuarioActual, String usuarioNuevo, UserDTO userDTO) {
        try {
            Solicitud solicitud = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POR_INSTANCIA_PROCESO, Solicitud.class)
                    .setParameter("idInstanciaProceso", String.valueOf(idProceso)).getSingleResult();
            if (StringUtils.equals(solicitud.getDestinatario(), usuarioActual)) {
                solicitud.setDestinatario(usuarioNuevo);
                entityManager.merge(solicitud);
            }
        } catch (NoResultException e) {
            logger.error(e);
            throw new ParametroInvalidoExcepcion(e);
        } catch (Exception e) {
            logger.error(e);
            throw new TechnicalException(e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ResultadoConsultaSolicitudDTO> consultarSolicitudesFiltroSolicitud(FiltroSolicitudDTO filtroSolicitud, UriInfo uri, HttpServletResponse response) {
        logger.info("Inicia servicio consultarSolicitudes");
        List<ResultadoConsultaSolicitudDTO> listaResultadoConsultaSolicitudDTO = new ArrayList<ResultadoConsultaSolicitudDTO>();

        List<TipoTransaccionEnum> listaTiposSolicitud = new ArrayList();
        ProcesoEnum proceso = filtroSolicitud.getProceso();
        TipoSolicitudEnum tipoEspecificoSolicitud = null;
        List<String> listaTiposSolicitudNativos = new ArrayList<>();
       TipoTransaccionEnum tipoTransaccionEnum1 = null; 
        if(ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO.equals(proceso)){
            tipoEspecificoSolicitud = TipoSolicitudEnum.SUBSIDIO_FALLECIMIENTO;
        }
        else if(ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO.equals(proceso)){
            tipoEspecificoSolicitud = TipoSolicitudEnum.SUBSIDIO_ESPECIFICO;
        }
        else if(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.equals(proceso)){
            tipoEspecificoSolicitud = TipoSolicitudEnum.SUBSIDIO_MASIVO;
        }
        else if(ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION.equals(proceso)){
            tipoEspecificoSolicitud = TipoSolicitudEnum.NOVEDAD_ARCHIVO;
        }
        else if(ProcesoEnum.GESTION_PREVENTIVA_CARTERA_ACTUALIZACION.equals(proceso)){
            tipoEspecificoSolicitud = TipoSolicitudEnum.GESTION_PREVENTIVA;
            listaTiposSolicitudNativos.add(TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA.toString());
        }
        else {
            TipoTransaccionEnum[] tiposTransaccionEnum = TipoTransaccionEnum.values();
            int ciclos = 1;
            for (TipoTransaccionEnum tipoTransaccionEnum : tiposTransaccionEnum) {
                if (tipoTransaccionEnum.getProceso() != null && tipoTransaccionEnum.getProceso().equals(proceso)) {
                    if(ciclos == 1){
                      tipoTransaccionEnum1 = tipoTransaccionEnum;  
                    } 
                    listaTiposSolicitud.add(tipoTransaccionEnum);
                     logger.info("**__**Log0 tipoTransaccionEnum: "+tipoTransaccionEnum);
                }
            }
        }
        
        
        /*
         * Como se busca por un proceso a la vez solo se necesita hacer la
         * busqueda de un tipo especifico de solicitud
         */
        if (listaTiposSolicitud.size() > 0) {
            tipoEspecificoSolicitud = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(listaTiposSolicitud.get(0));
        }

        /*
         * Se llama a las utilidades de solicitudes para obtener los datos de
         * construcci�n de la consulta din�mica
         */
        TipoEspecificoSolicitudDTO informacionTipoEspecificoSolicitud = ObtenerTipoSolicitud
                .obtenerInformacionTipoSolicitudEspecifico(tipoEspecificoSolicitud,
                        filtroSolicitud.getEstadoSolicitud());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = format
                .format(new Date(filtroSolicitud.getFechaInicio() == null ? 0 : filtroSolicitud.getFechaInicio()));
        String fechaFin = format
                .format(filtroSolicitud.getFechaFin() == null ? new Date() : new Date(filtroSolicitud.getFechaFin()));
        /*
         * Para construir la consulta se divide en secciones est�ticas y
         * din�micas
         */
        String salidaEstatica;
        if(ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.equals(proceso)){
            salidaEstatica = " SELECT sol.solId , sol.solUsuarioRadicacion , sol.solFechaRadicacion , "
                    + "per.perTipoIdentificacion , per.perNumeroIdentificacion, per.perRazonSocial , "
                    + "per.perPrimerNombre , per.perSegundoNombre , per.perPrimerApellido , per.perSegundoApellido , "
                    + "sol.solInstanciaProceso , per.perId , sol.solNumeroRadicacion , sol.solFechaCreacion , sol.solClasificacion , sol.solTipoTransaccion , sol.solCanalRecepcion, ";
         logger.info("**__**consultarSolicitudesFiltroSolicitud1");
        }  
        else{
             logger.info("**__**consultarSolicitudesFiltroSolicitud2");
            salidaEstatica = " SELECT sol.solId , sol.solUsuarioRadicacion , sol.solFechaRadicacion , "
                    + "per.perTipoIdentificacion , per.perNumeroIdentificacion, per.perRazonSocial , "
                    + "per.perPrimerNombre , per.perSegundoNombre , per.perPrimerApellido , per.perSegundoApellido , "
                    + "sol.solInstanciaProceso , per.perId , sol.solNumeroRadicacion , sol.solFechaCreacion , sol.solClasificacion , sol.solTipoTransaccion, sol.solCanalRecepcion, ";
        }
        String salidaDinamica = informacionTipoEspecificoSolicitud.getCampoEstadoSolicitud();
        String tablasDinamicas = " FROM " + informacionTipoEspecificoSolicitud.getConsultaDinamicaTablas();
        String restriccionesEstaticas;
        if(ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.equals(proceso)){
            restriccionesEstaticas = " WHERE ( :numeroSolicitud IS NULL OR sol.solNumeroRadicacion = :numeroSolicitud) "
                    + "AND sol.solTipoTransaccion IS NULL "
                    + "AND ( :canalRecepcion IS NULL OR sol.solCanalRecepcion = :canalRecepcion) "
                    + "AND ( :usuarioRadicacion IS NULL OR sol.solUsuarioRadicacion = :usuarioRadicacion) "
                    + "AND CONVERT(VARCHAR(10),sol.solFechaRadicacion,20) BETWEEN :fechaInicio AND :fechaFin ";
   logger.info("**__**consultarSolicitudesFiltroSolicitud3");
        }
        else if(ProcesoEnum.PAGO_APORTES_MANUAL.equals(proceso)){
            restriccionesEstaticas = " WHERE ( :numeroSolicitud IS NULL OR sol.solNumeroRadicacion = :numeroSolicitud) "
            + "AND sol.solTipoTransaccion IN (:tiposTransaccion) "
            + "AND ( :canalRecepcion IS NULL OR sol.solCanalRecepcion = :canalRecepcion) "
            + "AND ( :usuarioRadicacion IS NULL OR sol.solUsuarioRadicacion = :usuarioRadicacion) "
            + "AND CONVERT(VARCHAR(10),sol.solFechaRadicacion,20) BETWEEN :fechaInicio AND :fechaFin ";
        }
        else if(ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)){
            restriccionesEstaticas = " WHERE ( :numeroSolicitud IS NULL OR sol.solNumeroRadicacion = :numeroSolicitud) "
            + "AND sol.solTipoTransaccion IN (:tiposTransaccion) "
            + "AND ( :canalRecepcion IS NULL OR sol.solCanalRecepcion = :canalRecepcion) "
            + "AND ( :usuarioRadicacion IS NULL OR sol.solUsuarioRadicacion = :usuarioRadicacion) "
            + "AND CONVERT(VARCHAR(10),sol.solFechaRadicacion,20) BETWEEN :fechaInicio AND :fechaFin ";
        }
        else if(ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION.equals(proceso)){
            restriccionesEstaticas = " WHERE ( :numeroSolicitud IS NULL OR sol.solNumeroRadicacion = :numeroSolicitud) "
                     + "AND ( :canalRecepcion IS NULL OR sol.solCanalRecepcion = :canalRecepcion) "
                     + "AND ( :usuarioRadicacion IS NULL OR sol.solUsuarioRadicacion = :usuarioRadicacion) "
                    + "AND CONVERT(VARCHAR(10),sol.solFechaRadicacion,20) BETWEEN :fechaInicio AND :fechaFin ";
        }
        else{
             logger.info("**__**consultarSolicitudesFiltroSolicitud4");
            restriccionesEstaticas = " WHERE ( :numeroSolicitud IS NULL OR sol.solNumeroRadicacion = :numeroSolicitud) "
                    + "AND sol.solTipoTransaccion IN (:tiposTransaccion) "
                    + "AND ( :canalRecepcion IS NULL OR sol.solCanalRecepcion = :canalRecepcion) "
                    + "AND ( :usuarioRadicacion IS NULL OR sol.solUsuarioRadicacion = :usuarioRadicacion) "
                    + "AND CONVERT(VARCHAR(10),sol.solFechaRadicacion,20) BETWEEN :fechaInicio AND :fechaFin ";
        }
        String restriccionesDinamicas = " " + informacionTipoEspecificoSolicitud.getConsultaDinamicaRestricciones();
        if (!listaTiposSolicitud.isEmpty()){
            for (TipoTransaccionEnum tipoSolicitud : listaTiposSolicitud) {
                listaTiposSolicitudNativos.add(tipoSolicitud.toString());
            }    
        }
        
        String canalRecepcionNativo = filtroSolicitud.getCanalRecepcion() != null
                ? filtroSolicitud.getCanalRecepcion().toString() : null;

        String estadoSolicitudNativo = informacionTipoEspecificoSolicitud.getEstadoSolicitud() != null
                ? informacionTipoEspecificoSolicitud.getEstadoSolicitud().toString() : null;
            if(estadoSolicitudNativo == "CERRADA"  || estadoSolicitudNativo == "DESISTIDA" 
            || estadoSolicitudNativo == "APROBADA" || estadoSolicitudNativo == "RECHAZADA" ||
             estadoSolicitudNativo == "CANCELADA" ){
                salidaDinamica = "sol.solResultadoProceso as soaEstadoSolicitud ";
                        }
 String consultaDinamica = salidaEstatica + salidaDinamica + tablasDinamicas + restriccionesEstaticas
                + restriccionesDinamicas;
        List<Object[]> lista = new ArrayList<>();
            logger.info("**__**consultarSolicitudesFiltroSolicitud5 c:"+consultaDinamica);
            logger.info("**__**estadoSolicitudNativo c:"+estadoSolicitudNativo);
        if(ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO.equals(proceso) || ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.equals(proceso)){
            lista = entityManager.createNativeQuery(consultaDinamica)
                    .setParameter("numeroSolicitud", filtroSolicitud.getNumeroSolicitud())
                    .setParameter("canalRecepcion", canalRecepcionNativo)
                    .setParameter("usuarioRadicacion", filtroSolicitud.getUsuarioRadicacion())
                    .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                    .setParameter("estadoSolicitud", estadoSolicitudNativo).getResultList();
        }
        else if(ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION.equals(proceso)){
              logger.info("**__**consultarSolicitudesFiltroSolicitud6 c:"+canalRecepcionNativo);
            lista = entityManager.createNativeQuery(consultaDinamica)
                    .setParameter("numeroSolicitud", filtroSolicitud.getNumeroSolicitud())
                    .setParameter("canalRecepcion", canalRecepcionNativo)
                    .setParameter("usuarioRadicacion", filtroSolicitud.getUsuarioRadicacion())
                    .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                    .setParameter("estadoSolicitud", estadoSolicitudNativo).getResultList();
        }
        else if(ProcesoEnum.PAGO_APORTES_MANUAL.equals(proceso)){
            listaTiposSolicitudNativos.add(TipoTransaccionEnum.APORTES_MANUALES_MASIVA.name());
            lista = entityManager.createNativeQuery(consultaDinamica)
                .setParameter("tiposTransaccion", listaTiposSolicitudNativos)
                .setParameter("numeroSolicitud", filtroSolicitud.getNumeroSolicitud())
                .setParameter("canalRecepcion", canalRecepcionNativo)
                .setParameter("usuarioRadicacion", filtroSolicitud.getUsuarioRadicacion())
                .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                .setParameter("estadoSolicitud", estadoSolicitudNativo).getResultList();
        }
        else if( ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)){
            listaTiposSolicitudNativos.add(TipoTransaccionEnum.DEVOLUCION_APORTES_MASIVA.name());
            lista = entityManager.createNativeQuery(consultaDinamica)
                .setParameter("tiposTransaccion", listaTiposSolicitudNativos)
                .setParameter("numeroSolicitud", filtroSolicitud.getNumeroSolicitud())
                .setParameter("canalRecepcion", canalRecepcionNativo)
                .setParameter("usuarioRadicacion", filtroSolicitud.getUsuarioRadicacion())
                .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                .setParameter("estadoSolicitud", estadoSolicitudNativo).getResultList();
        }        
        else{
              logger.info("**__**consultarSolicitudesFiltroSolicitud7 c:"+estadoSolicitudNativo);
            lista = entityManager.createNativeQuery(consultaDinamica)
                    .setParameter("tiposTransaccion", listaTiposSolicitudNativos)
                    .setParameter("numeroSolicitud", filtroSolicitud.getNumeroSolicitud())
                    .setParameter("canalRecepcion", canalRecepcionNativo)
                    .setParameter("usuarioRadicacion", filtroSolicitud.getUsuarioRadicacion())
                    .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                    .setParameter("estadoSolicitud", estadoSolicitudNativo).getResultList();
        }
        logger.info("**__**estadoSolicitud c:"+estadoSolicitudNativo);
        logger.info("**__**canalRecepcion c:"+canalRecepcionNativo);
        logger.info("**__**numeroSolicitud :"+filtroSolicitud.getNumeroSolicitud());
        logger.info("**__**fechaInicio fechaini: "+fechaInicio +" fechaFin:"+fechaFin);
        logger.info("**__**usuarioRadicacion c:"+filtroSolicitud.getUsuarioRadicacion());
        if(lista != null){
            response.addHeader("totalRecords", Integer.toString(lista.size()));
        }
        /*
         * conversi�n de la lista de solicitudes de Object[] a
         * resultadoConsultaSolicitudDTO
         */
        Integer indice;
        Integer limite;
        if(filtroSolicitud.getLimit() == null || filtroSolicitud.getOffSet() == null ){
            
            Map<String,List<String>> params = new HashMap<String,List<String>>();
            
             if (uri != null && uri.getQueryParameters() != null) {
                 for (Entry<String, List<String>> e : uri.getQueryParameters().entrySet()) {
                     params.put(e.getKey(), e.getValue()); 
                 }
             }
             
             indice = params.get("offset") != null ? Integer.parseInt(params.get("offset").get(0)): null;
             limite = params.get("limit") != null  ? Integer.parseInt(params.get("limit").get(0)) : null ;
        }
        else{
             indice = filtroSolicitud.getOffSet();
             limite = filtroSolicitud.getLimit();
        }
        
        if(indice != null && limite != null){
            int contador = 0;
             while (contador < limite && indice < lista.size())
                {
                     Object[] resultadoConsultaSolicitudObject = lista.get(indice);
                        ResultadoConsultaSolicitudDTO resultadoConsultaSolicitudDTO = new ResultadoConsultaSolicitudDTO(
                                resultadoConsultaSolicitudObject);
                        resultadoConsultaSolicitudDTO.setProceso(filtroSolicitud.getProceso());
                        listaResultadoConsultaSolicitudDTO.add(resultadoConsultaSolicitudDTO);
                        indice ++;
                        contador ++;
                }
        }else{
            for ( Object[] resultadoConsultaSolicitudObject : lista) {
                    ResultadoConsultaSolicitudDTO resultadoConsultaSolicitudDTO = new ResultadoConsultaSolicitudDTO(
                            resultadoConsultaSolicitudObject);
                    resultadoConsultaSolicitudDTO.setProceso(filtroSolicitud.getProceso());
                    listaResultadoConsultaSolicitudDTO.add(resultadoConsultaSolicitudDTO);
            }
        }
        
        /*
         * Una vez que se tenga la lista de resultado de las solicitudes se
         * validan los filtros de empleador y persona.
         */
        Long idPersonaFiltro = null;
        if (filtroSolicitud.getIdEmpleador() != null) {
            BigInteger idPersonaFiltroNativo = (BigInteger) entityManager
                    .createNativeQuery(
                            "select per.perId from Empleador empl INNER JOIN Empresa empr on empl.empEmpresa = empr.empId INNER JOIN Persona per ON empr.empPersona = per.perId WHERE empl.empId = :idEmpleador")
                    .setParameter("idEmpleador", filtroSolicitud.getIdEmpleador()).getSingleResult();
            idPersonaFiltro = idPersonaFiltroNativo != null ? idPersonaFiltroNativo.longValue() : null;
        }
        if (filtroSolicitud.getIdPersona() != null) {
            idPersonaFiltro = filtroSolicitud.getIdPersona();
        }

        /*
         * En esta secci�n se aplica el filtro para Empleador o Persona de
         * acuerdo al paso de b�squeda que se haya realizado anterior
         */
        if (filtroSolicitud.getIdEmpleador() != null || filtroSolicitud.getIdPersona() != null) {
            Iterator<ResultadoConsultaSolicitudDTO> listaSolicitudesResultadoIterator = listaResultadoConsultaSolicitudDTO
                    .iterator();
            while (listaSolicitudesResultadoIterator.hasNext()) {
                ResultadoConsultaSolicitudDTO resultadoConsultaSolicitudDTO = listaSolicitudesResultadoIterator.next();
                if (!(resultadoConsultaSolicitudDTO.getIdPersona().longValue() == idPersonaFiltro)) {
                    listaSolicitudesResultadoIterator.remove();
                }
            }
        }

        return listaResultadoConsultaSolicitudDTO;
    }
    
    @Asynchronous
    @Override
    public void persistirResultadoUtiliarioBPM(String numeroradicado, String resultado, String usuario, UserDTO user) {
        logger.debug("Inicia servicio perisistirResultadoUtiliarioBPM");
        
        entityManager.createNamedQuery(NamedQueriesConstants.PERSISTIR_RESULTADO_EJECUCION_UTILITARIO_BPM)
        .setParameter("reuNumeroRadicado", numeroradicado)
        .setParameter("reuResultadaoProceso", resultado)
        .setParameter("reuUsuario", usuario)
        .executeUpdate();
        
        logger.debug("Finaliza servicio perisistirResultadoUtiliarioBPM");
    }
    
    @Asynchronous
    @Override
    public void calcularTiempoDesistirSolicitud(Long idSolicitud, String idTarea, String estadoSolicitud, String tipoTransaccion){
        logger.info("entra acá calcularTiempoDesistirSolicitud");
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.SP_CALCULAR_TIEMPO_DESISITIR_SOLICITUD)
        .setParameter("idSolicitud", idSolicitud)
        .setParameter("idTareaString", idTarea)
        .setParameter("estadoSolicitud", estadoSolicitud)
        .setParameter("tipoTransaccion", tipoTransaccion);
        query.execute();
    }

    @Override
    public void desistirSolicitudesAutomatico() {
    logger.info("Inicio del método desistirSolicitudesAutomatico");
    
       try {
        // Consultar las solicitudes con sus tipos de transacción
        List<Object[]> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_VIGENTES)
                                                  .getResultList();
        logger.warn("luego de consulta");
        logger.warn("tamano lista: " +solicitudes.size());
        for (Object[] solicitud : solicitudes) {
            logger.warn("entra for");
            Long idSolicitud = ((Number) solicitud[0]).longValue(); 
            String tipoTransaccion = (String) solicitud[1]; 
            if (TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION.name().equals(tipoTransaccion)) {
                CancelacionSolicitudPersonasDTO cancelar = new CancelacionSolicitudPersonasDTO();
                cancelar.setIdSolicitud(idSolicitud);

                // Cancelar la solicitud
                CancelarSolicitudPersonasWebTimeout cancelarSolicitud = new CancelarSolicitudPersonasWebTimeout(cancelar);
                cancelarSolicitud.execute();

                logger.info("Solicitud con ID {} procesada exitosamente.");
            } else {
                logger.info("Solicitud con ID {} no cumple con el tipo de transacción esperado.");
            }
        }
        logger.warn("luego de for");
        } catch (Exception e) {
            logger.error("Error al procesar solicitudes automáticas: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }       
        logger.warn("se salta el try");
    }
}
