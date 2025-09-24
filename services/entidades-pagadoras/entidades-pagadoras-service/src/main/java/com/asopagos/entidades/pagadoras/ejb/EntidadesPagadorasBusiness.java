
package com.asopagos.entidades.pagadoras.ejb;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.modelo.SolicitudAsociacionPersonaEntidadPagadoraModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.DocumentoEntidadPagadora;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.pagadoras.constants.NamedQueriesConstants;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import com.asopagos.entidades.pagadoras.dto.DatosFiltroConsultaDTO;
import com.asopagos.entidades.pagadoras.dto.DocumentoEntidadPagadoraDTO;
import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.entidadesPagadoras.ResultadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.EstadosUtils;
import com.asopagos.util.PersonasUtils;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con entidades pagadoras<br/>
 * <b>Módulo:</b> Asopagos - HU 133, 109
 *
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@Stateless
public class EntidadesPagadorasBusiness implements EntidadesPagadorasService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "entidades_pagadoras_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private static final ILogger LOGGER = LogManager.getLogger(EntidadesPagadorasBusiness.class);

    @Inject
    private FileGenerator fileGenerator;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#buscarEntidadPagadora(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultarEntidadPagadoraOutDTO> buscarEntidadPagadora(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String razonSocial) {
        try {
            LOGGER.debug("Inicia servicio buscarEntidadPagadora(TipoIdentificacionEnum, String, String)");
            // Objeto que contendrá el resultado de la consulta
            List<EntidadPagadora> listaEntidadesPagadoras = null;
            if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
                // Búsqueda por tipo y nro de documento
                listaEntidadesPagadoras = buscarEntidadPagadoraPorDocumento(tipoIdentificacion,
                        numeroIdentificacion);
            } 
            else if (razonSocial != null) {
                // Búsqueda por razón social
                listaEntidadesPagadoras = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_ENTIDAD_PAGADORA_POR_RAZON_SOCIAL, EntidadPagadora.class)
                        .setParameter("razonSocial", "%" + razonSocial + "%").getResultList();
            }
            LOGGER.debug("Finaliza servicio buscarEntidadPagadora(TipoIdentificacionEnum, String, String)");
            return convertirDTO(listaEntidadesPagadoras);
        } catch (Exception e) {
            LOGGER.debug("Finaliza buscarEntidadPagadora(TipoIdentificacionEnum, String, String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Crea la respuesta en DTO para las entidades pagadoras
     * @param listaEntidadesPagadoras
     * @return Lista entidades en formtato DTO
     */
    private List<ConsultarEntidadPagadoraOutDTO> convertirDTO(List<EntidadPagadora> listaEntidadesPagadoras) {
        List<ConsultarEntidadPagadoraOutDTO> result = new ArrayList<>();

        if (listaEntidadesPagadoras == null || listaEntidadesPagadoras.isEmpty()) {
            return null;
        }
        List<ConsultarEstadoDTO> listConsulta= new ArrayList<ConsultarEstadoDTO>();
        for (EntidadPagadora entidadPagadora : listaEntidadesPagadoras) {
            ConsultarEntidadPagadoraOutDTO entidadPagadoraDTO = new ConsultarEntidadPagadoraOutDTO(entidadPagadora);
            result.add(entidadPagadoraDTO);
            
            // Se consulta el estado como empleador
            ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();
            consultEstado.setEntityManager(entityManager);
            consultEstado.setNumeroIdentificacion(entidadPagadoraDTO.getNumeroIdentificacion());
            consultEstado.setTipoIdentificacion(entidadPagadoraDTO.getTipoIdentificacion());
            consultEstado.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listConsulta.add(consultEstado);
        }
        List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
        for (EstadoDTO estadoDTO : estados) {
            for (ConsultarEntidadPagadoraOutDTO dto : result) {
                if (estadoDTO.getNumeroIdentificacion().equals(dto.getNumeroIdentificacion())
                        && estadoDTO.getTipoIdentificacion().equals(dto.getTipoIdentificacion())) {
                    dto.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().name()));
                    break;
                }
            }
        }
        
        
        return result;
    }

    /**
     * <b>Descripción</b>Método que se encarga de consultar un entidad pagadora
     * por tipo y nro de documento
     *
     * @param tipoIdentificacion
     *        tipo identificación
     * @param nroDocumento
     *        nro identificación
     * @return List<EntidadPagadora> lista con las entidades encontradas
     */
    private List<EntidadPagadora> buscarEntidadPagadoraPorDocumento(TipoIdentificacionEnum tipoIdentificacion, String nroDocumento) {
        return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ENTIDAD_PAGADORA_POR_DOCUMENTO, EntidadPagadora.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", nroDocumento).getResultList();
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#crearEntidadPagadora(com.asopagos.entidades.pagadoras.dto.
     * EntidadPagadoraDTO)
     */
    @Override
    public Long crearEntidadPagadora(EntidadPagadoraDTO entidadPagadoraDTO) {
        try {
            LOGGER.debug("Inicia servicio crearEntidadPagadora(EntidadPagadoraDTO)");
            // Se crea el registro de la entidad pagadora
            EntidadPagadora entidadPagadora = new EntidadPagadora();
            if (entidadPagadoraDTO.getIdEmpresa() != null){
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(entidadPagadoraDTO.getIdEmpresa());
                entidadPagadora.setEmpresa(empresa);
            }
            entidadPagadora.setSucursalPagadora(entidadPagadoraDTO.getIdSucursalEmpresa());
            entidadPagadora.setTipoAfiliacion(entidadPagadoraDTO.getTipoAfiliacion());
            entidadPagadora.setAportante(entidadPagadoraDTO.getAportante());
            entidadPagadora.setEstadoEntidadPagadora(HabilitadoInhabilitadoEnum.HABILITADO);
            if (entidadPagadoraDTO.getCanalComunicacion() != null
                    && !entidadPagadoraDTO.getCanalComunicacion().toString().trim().equals("")) {
                entidadPagadora.setCanalComunicacion(entidadPagadoraDTO.getCanalComunicacion());
            }
            if (entidadPagadoraDTO.getMedioComunicacion() != null
                    && !entidadPagadoraDTO.getMedioComunicacion().toString().trim().equals("")) {
                entidadPagadora.setMedioComunicacion(entidadPagadoraDTO.getMedioComunicacion());
            }
            if (entidadPagadoraDTO.getEmailContacto() != null && !entidadPagadoraDTO.getEmailContacto().trim().equals("")) {
                entidadPagadora.setEmailComunicacion(entidadPagadoraDTO.getEmailContacto());
            }
            entidadPagadora.setNombreContacto(entidadPagadoraDTO.getNombreContacto());
            entidadPagadora.setCargoContacto(entidadPagadoraDTO.getCargoContacto());
            entidadPagadora.setFechaCreacion(Calendar.getInstance().getTime());
            entityManager.persist(entidadPagadora);
            
            // Se guardan los documentos adjuntos
            if (entidadPagadoraDTO.getDocumentos() != null && !entidadPagadoraDTO.getDocumentos().isEmpty()) {
                for (DocumentoEntidadPagadoraDTO documentosEntidad : entidadPagadoraDTO.getDocumentos()) {
                    documentosEntidad.setIdEntidadPagadora(entidadPagadora.getIdEntidadPagadora());
                }
                guardarDocumentosAdjuntos(entidadPagadoraDTO.getDocumentos());
            }
            LOGGER.debug("Finaliza servicio buscarEntidadPagadora(TipoIdentificacionEnum, String, String)");
            return entidadPagadora.getIdEntidadPagadora();
        } catch (Exception e) {
            LOGGER.debug("Error - Finaliza servicio buscarEntidadPagadora(TipoIdentificacionEnum, String, String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que se encarga de consultar la ubicacion de empresa de tipo CORRESPONDENCIA
     * @param idEmpresa
     *        Identificador empresa
     * @return Informacion ubicacion empresa
     */
    private UbicacionEmpresa consultarUbicacionCorrespondenciaEmpresa(Long idEmpresa) {
        // Se consulta las ubicaciones de la empresa
        List<UbicacionEmpresa> listUbicaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA, UbicacionEmpresa.class)
                .setParameter("idEmpresa", idEmpresa).getResultList();

        if (listUbicaciones != null && !listUbicaciones.isEmpty()) {
            for (UbicacionEmpresa ubicacionEmpresa : listUbicaciones) {
                if (ubicacionEmpresa.getTipoUbicacion().equals(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA)) {
                    return ubicacionEmpresa;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#actualizarEntidadPagadora(com.asopagos.entidades.pagadoras.dto.
     * EntidadPagadoraDTO)
     */
    @Override
    public void actualizarEntidadPagadora(EntidadPagadoraDTO entidadPagadoraDTO) {
        try {
            LOGGER.debug("Inicia servicio actualizarEntidadPagadora(EntidadPagadoraDTO)");
            List<EntidadPagadora> entidadPagadoraResult = buscarEntidadPagadoraPorDocumento(entidadPagadoraDTO.getTipoIdentificacion(),
                    entidadPagadoraDTO.getNumeroIdentificacion());
            if (entidadPagadoraResult != null && !entidadPagadoraResult.isEmpty()) {
                EntidadPagadora entidadPagadora = entidadPagadoraResult.iterator().next();
                Empresa empresa = entidadPagadora.getEmpresa();
                Persona persona = empresa.getPersona();
                Ubicacion ubicacionPrincipal = persona.getUbicacionPrincipal();
                
                // Actualiza informacion empresa
                entityManager.merge(empresa);
                empresa.setPaginaWeb(entidadPagadoraDTO.getPaginaWeb());
                // Actualiza informacion ubicacion principal
                entityManager.merge(ubicacionPrincipal);
                mapearUbicacionActualizar(ubicacionPrincipal, entidadPagadoraDTO.getUbicacion());
                // Verifica si actualiza o crea la ubicacion de correspondencia
                UbicacionEmpresa ubicacionEmpresa = consultarUbicacionCorrespondenciaEmpresa(empresa.getIdEmpresa());
                if (ubicacionEmpresa != null) {
                    Ubicacion ubicacionCorrespondencia = ubicacionEmpresa.getUbicacion();
                    entityManager.merge(ubicacionCorrespondencia);
                    mapearUbicacionActualizar(ubicacionCorrespondencia, entidadPagadoraDTO.getUbicacionCorrespondencia());
                } else {
                    UbicacionEmpresa ubicacionEmpresaCorrespondencia = new UbicacionEmpresa();
                    ubicacionEmpresaCorrespondencia.setIdEmpresa(entidadPagadoraDTO.getIdEmpresa());
                    ubicacionEmpresaCorrespondencia.setTipoUbicacion(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA);
                    ubicacionEmpresaCorrespondencia.setUbicacion(UbicacionDTO.obtenerUbicacion(entidadPagadoraDTO.getUbicacionCorrespondencia()));
                    entityManager.persist(ubicacionEmpresaCorrespondencia);
                }
                // Actualiza la informacion de la entidad pagadora
                entityManager.merge(entidadPagadora);
                entidadPagadora.setSucursalPagadora(entidadPagadoraDTO.getIdSucursalEmpresa());
                entidadPagadora.setTipoAfiliacion(entidadPagadoraDTO.getTipoAfiliacion());
                entidadPagadora.setAportante(entidadPagadoraDTO.getAportante());
                entidadPagadora.setEstadoEntidadPagadora(entidadPagadoraDTO.getEstadoEntidadPagadora());
                if (entidadPagadoraDTO.getCanalComunicacion() != null
                        && !entidadPagadoraDTO.getCanalComunicacion().toString().trim().equals("")) {
                    entidadPagadora.setCanalComunicacion(entidadPagadoraDTO.getCanalComunicacion());
                }
                if (entidadPagadoraDTO.getMedioComunicacion() != null
                        && !entidadPagadoraDTO.getMedioComunicacion().toString().trim().equals("")) {
                    entidadPagadora.setMedioComunicacion(entidadPagadoraDTO.getMedioComunicacion());
                }
                if (entidadPagadoraDTO.getEmailContacto() != null && !entidadPagadoraDTO.getEmailContacto().trim().equals("")) {
                    entidadPagadora.setEmailComunicacion(entidadPagadoraDTO.getEmailContacto());
                }
                entidadPagadora.setNombreContacto(entidadPagadoraDTO.getNombreContacto());
                entidadPagadora.setCargoContacto(entidadPagadoraDTO.getCargoContacto());
                
                // Se guardan los documentos adjuntos
                if (entidadPagadoraDTO.getDocumentos() != null && !entidadPagadoraDTO.getDocumentos().isEmpty()) {
                    for (DocumentoEntidadPagadoraDTO documentosEntidad : entidadPagadoraDTO.getDocumentos()) {
                        documentosEntidad.setIdEntidadPagadora(entidadPagadora.getIdEntidadPagadora());
                    }
                    guardarDocumentosAdjuntos(entidadPagadoraDTO.getDocumentos());
                }
            }
            LOGGER.debug("Finaliza servicio actualizarEntidadPagadora(EntidadPagadoraDTO)");
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio actualizarEntidadPagadora(EntidadPagadoraDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * Mapea los datos de ubicacion para actualizar
     * @param ubicacionExistente
     *        Ubicacion existente a actualizar
     * @param ubicacionDTO
     *        Datos ubicacion que se deben actualizar
     */
    private void mapearUbicacionActualizar(Ubicacion ubicacionExistente, UbicacionDTO ubicacionDTO) {
        // Convierte a entity la informacion de ubicacion actualizada
        Ubicacion ubicacionModificada = UbicacionDTO.obtenerUbicacion(ubicacionDTO);
        // Setea la informacion de la ubicacion actualizada en la existente
        ubicacionExistente.setAutorizacionEnvioEmail(ubicacionModificada.getAutorizacionEnvioEmail());
        ubicacionExistente.setCodigoPostal(ubicacionModificada.getCodigoPostal());
        ubicacionExistente.setDescripcionIndicacion(ubicacionModificada.getDescripcionIndicacion());
        ubicacionExistente.setDireccionFisica(ubicacionModificada.getDireccionFisica());
        ubicacionExistente.setEmail(ubicacionModificada.getEmail());
        ubicacionExistente.setIndicativoTelFijo(ubicacionModificada.getIndicativoTelFijo());
        ubicacionExistente.setMunicipio(ubicacionModificada.getMunicipio());
        ubicacionExistente.setTelefonoCelular(ubicacionModificada.getTelefonoCelular());
        ubicacionExistente.setTelefonoFijo(ubicacionModificada.getTelefonoFijo());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#guardarDocumentosAdjuntos(java.util.List)
     */
    @Override
    public void guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO> documentos) {
        try {
            LOGGER.debug("Inicia servicio guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO>)");
            for (DocumentoEntidadPagadoraDTO documentoEntidadPagadoraDTO : documentos) {
                // Se crea el documento actualizar o registrar
                DocumentoEntidadPagadora documento = new DocumentoEntidadPagadora();
                // Se configura el tipo de documentos de la entidad pagadora
                documento.setTipoDocumento(documentoEntidadPagadoraDTO.getTipoDocumento());
                if (documentoEntidadPagadoraDTO.getIdEntidadPagadora() != null
                        && documentoEntidadPagadoraDTO.getIdEntidadPagadora() > NumerosEnterosConstants.CERO) {
                    documento.setIdEntidadPagadora(documentoEntidadPagadoraDTO.getIdEntidadPagadora());
                }
                else {
                    LOGGER.debug("Finaliza guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO>)");
                    LOGGER.debug("No se ingresó el id de la entidad pagadora");
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                }
                if (documentoEntidadPagadoraDTO.getIdentificadorDocumento() != null
                        && !documentoEntidadPagadoraDTO.getIdentificadorDocumento().trim().equals("")) {
                    documento.setIdentificadorDocumento(documentoEntidadPagadoraDTO.getIdentificadorDocumento());
                }
                else {
                    LOGGER.debug("Finaliza guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO>)");
                    LOGGER.debug("No se ingresó el identicador del documento");
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                }
                if (documentoEntidadPagadoraDTO.getDocName() != null && !documentoEntidadPagadoraDTO.getDocName().trim().equals("")) {
                    documento.setNombreDocumento(documentoEntidadPagadoraDTO.getDocName());
                }
                if (documentoEntidadPagadoraDTO.getVersion() != null
                        && documentoEntidadPagadoraDTO.getVersion() > NumerosEnterosConstants.CERO) {
                    documento.setVersionDocumento(documentoEntidadPagadoraDTO.getVersion());
                }
                if (documentoEntidadPagadoraDTO.getIdDocumentoEntidadPagadora() != null) {
                    documento.setIdDocumentoEntidadPagadora(documentoEntidadPagadoraDTO.getIdDocumentoEntidadPagadora());
                    entityManager.merge(documento);
                }
                else {
                    entityManager.persist(documento);
                }
            }
            LOGGER.debug("Finaliza servicio guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO>)");
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio guardarDocumentosAdjuntos(List<DocumentoEntidadPagadoraDTO>)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#consultarEntidadPagadora(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EntidadPagadoraDTO consultarEntidadPagadora(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            LOGGER.debug("Inicia servicio consultarEntidadPagadora(TipoIdentificacionEnum, String)");
            // Objeto que contendrá el resultado de la consulta
            EntidadPagadoraDTO entidadPagadoraDTO = null;
            // Búsqueda por tipo y nro de documento
            List<EntidadPagadora> listEntidades = buscarEntidadPagadoraPorDocumento(tipoIdentificacion, numeroIdentificacion);
            // Se verifica que hayan resultados
            if (listEntidades != null && !listEntidades.isEmpty()) {
                EntidadPagadora entidadPagadora = listEntidades.iterator().next();
                Empresa empresa = entidadPagadora.getEmpresa();
                Persona persona = empresa.getPersona();
                Ubicacion ubicacionPrincipal = persona.getUbicacionPrincipal();
                
                entidadPagadoraDTO = new EntidadPagadoraDTO(entidadPagadora, empresa, persona, ubicacionPrincipal);
                // Informacion ubicacion correspondencia
                UbicacionEmpresa ubicacionEmpresa = consultarUbicacionCorrespondenciaEmpresa(empresa.getIdEmpresa());
                UbicacionDTO ubicacionCorrespondeciaDTO = new UbicacionDTO();
                if (ubicacionEmpresa != null && ubicacionEmpresa.getUbicacion() != null) {
                    ubicacionCorrespondeciaDTO =  UbicacionDTO.obtenerUbicacionDTO(ubicacionEmpresa.getUbicacion());
                }
                entidadPagadoraDTO.setUbicacionCorrespondencia(ubicacionCorrespondeciaDTO);
                // Informacion documentos
                List<DocumentoEntidadPagadora> documentosEntidadPagadora = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTOS_ENTIDAD_PAGADORA, DocumentoEntidadPagadora.class)
                        .setParameter("idEntidadPagadora", entidadPagadora.getIdEntidadPagadora()).getResultList();
                // Se verifica que hayan documentos de la entidad pagadora
                if (documentosEntidadPagadora != null && !documentosEntidadPagadora.isEmpty()) {
                    List<DocumentoEntidadPagadoraDTO> listaDocumentosEntidadPagadora = new ArrayList<>();
                    DocumentoEntidadPagadoraDTO documentoEntidadDTO = null;
                    for (DocumentoEntidadPagadora documentoEntidadPagadora : documentosEntidadPagadora) {
                        documentoEntidadDTO = new DocumentoEntidadPagadoraDTO();
                        documentoEntidadDTO.setIdDocumentoEntidadPagadora(documentoEntidadPagadora.getIdDocumentoEntidadPagadora());
                        documentoEntidadDTO.setDocName(documentoEntidadPagadora.getNombreDocumento());
                        documentoEntidadDTO.setIdentificadorDocumento(documentoEntidadPagadora.getIdentificadorDocumento());
                        documentoEntidadDTO.setTipoDocumento(documentoEntidadPagadora.getTipoDocumento());
                        documentoEntidadDTO.setVersion(documentoEntidadPagadora.getVersionDocumento());
                        listaDocumentosEntidadPagadora.add(documentoEntidadDTO);
                    }
                    entidadPagadoraDTO.setDocumentos(listaDocumentosEntidadPagadora);
                }
            }
            LOGGER.debug("Finaliza servicio consultarEntidadPagadora(TipoIdentificacionEnum, String)");
            return entidadPagadoraDTO;
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio consultarEntidadPagadora(TipoIdentificacionEnum, String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#consultarTodasEntidadesPagadoras()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultarEntidadPagadoraOutDTO> consultarTodasEntidadesPagadoras(UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "consultarTodasEntidadesPagadoras(UriInfo, HttpServletResponse)";
        try {
            LOGGER.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            List<ConsultarEntidadPagadoraOutDTO> result = null; 
            Query query = null;

            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
            queryBuilder.addOrderByDefaultParam("razonSocial");
            query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_PAGADORAS, null);
            List<EntidadPagadora> entidadesPagadoras = (List<EntidadPagadora>) query.getResultList();
            List<ConsultarEstadoDTO> listConsulta = new ArrayList<>();
            // Consulta las entidades pagadoras existentes
            if (entidadesPagadoras != null && !entidadesPagadoras.isEmpty()) {
                result = new ArrayList<>();
                for (EntidadPagadora entidadPagadora : entidadesPagadoras) {
                    result.add(convertEntityInOutDTO(entidadPagadora,listConsulta));
                }
                List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
                for (EstadoDTO estadoDTO : listEstado) {
                    for (ConsultarEntidadPagadoraOutDTO dto : result) {
                        if (estadoDTO.getNumeroIdentificacion().equals(dto.getNumeroIdentificacion())
                                && estadoDTO.getTipoIdentificacion().equals(dto.getTipoIdentificacion())
                                && estadoDTO.getEstado() != null) {
                            dto.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().name()));
                            break;
                        }
                    }
                }
            }

            
            LOGGER.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        } catch (Exception e) {
            LOGGER.error(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * <b>Descripción</b>Método que se encarga de consultar las solicitudes
     * asociadas a la persona de la entidad pagadora
     *
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     * @param tipoGestion
     *        tipo de gestión de la solicitud
     * @param consecutivoGestion
     *        consecutivo de la gestión
     * @param numeroRadicado
     *        número de radicado
     * @return List<SolicitudAsociacionPersonaEntidadPagadoraDTO> lista con las
     *         solicitudes consultadas
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> consultarSolicitudesAsociacionPersonas(Long idEntidadPagadora,
            TipoGestionSolicitudAsociacionEnum tipoGestion, String consecutivoGestion, String numeroRadicado) {

        SolicitudAsociacionPersonaEntidadPagadoraDTO solicitud = null;
        List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes = new ArrayList<>();

        List<Object[]> listaEntidadesPagadoras = null;

        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder consulta = new StringBuilder();
        consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_BASE);
        if (consecutivoGestion != null && !consecutivoGestion.trim().equals("")) {
            consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_CONSECUTIVO_GESTION);
            params.put("consecutivoGestion", consecutivoGestion);
        }
        if (numeroRadicado != null && !numeroRadicado.trim().equals("")) {
            consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_NUMERO_RADICADO);
            params.put("numeroRadicado", numeroRadicado);
        }
        Query query = null;

        // Si tipoGestion es null se consulta por idEntidadPagadora
        if (tipoGestion == null) {
            consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_ID_ENTIDAD_PAGADORA);
            params.put("idEntidadPagadora", idEntidadPagadora);
        }
        else {
            // Si tipoGestion no es null e igual a tipo de gestión
            // SOLICITAR_ALTA
            if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_ALTA)) {
                consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_SOLICITAR_ALTA);
            }
            // Si tipoGestion no es null e igual a tipo de gestión
            // SOLICITAR_RETIRO
            else if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_RETIRO)) {
                consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_SOLICITAR_RETIRO);
            }
            // Si tipoGestion no es null e igual a tipo de gestión
            // REGISTRAR_RESULTADO_SOLICITUD
            else if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.REGISTRAR_RESULTADO_SOLICITUD)) {
                consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_REGISTRAR_RESULTADO_SOLICITUD);
            }
        }
        consulta.append(NamedQueriesConstants.ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_ORDER_BY);
        query = entityManager.createQuery(consulta.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        listaEntidadesPagadoras = query.getResultList();
        List<ConsultarEstadoDTO> listConsulta= new ArrayList<ConsultarEstadoDTO>();

        // Se verifica que hayan resultados
        if (listaEntidadesPagadoras != null && !listaEntidadesPagadoras.isEmpty()) {
            for (Object[] registro : listaEntidadesPagadoras) {
                Persona persona = (Persona) registro[0];
                RolAfiliado rolAfiliado = (RolAfiliado) registro[1];
                SolicitudAsociacionPersonaEntidadPagadora solicitudAsociacionPersonaEntidadPagadora = (SolicitudAsociacionPersonaEntidadPagadora) registro[2];
                Solicitud sol = (Solicitud) registro[3];
                // Se instancia y configura el objeto que retornará las
                // solicitudes
                solicitud = new SolicitudAsociacionPersonaEntidadPagadoraDTO(solicitudAsociacionPersonaEntidadPagadora, sol, rolAfiliado,
                        persona);
                solicitudes.add(solicitud);
                
              //se consulta estado del rol calculado
                ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();
                consultEstado.setEntityManager(entityManager);
                consultEstado.setNumeroIdentificacion(persona.getNumeroIdentificacion());
                consultEstado.setTipoIdentificacion(persona.getTipoIdentificacion());
                consultEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                listConsulta.add(consultEstado);
            }
            
            List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
            for (EstadoDTO estadoDTO : estados) {
                for (SolicitudAsociacionPersonaEntidadPagadoraDTO soli : solicitudes) {
                    if (estadoDTO.getNumeroIdentificacion().equals(soli.getNumeroIdentificacion())
                            && estadoDTO.getTipoIdentificacion().equals(soli.getTipoIdentificacion())) {
                        soli.setEstadoPersona(estadoDTO.getEstado());
                        break;
                    }
                }
            }
        }
        else {
            return null;
        }
        return solicitudes;
    }

    /**
     * <b>Descripción</b> Método que se encarga de ejecutar las validaciones de
     * la gestión de solicitudes de asociación
     * 
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     *
     * @param tipoGestion
     *        Tipo de gestión para la solicitud
     * 
     * @return Boolean true si las validaciones son exitosas false si falla
     *         alguna validación
     */
    @SuppressWarnings("unchecked")
    public Boolean ejecutarValidacionesSolicitudesAsociacion(Long idEntidadPagadora,
            List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes, TipoGestionSolicitudAsociacionEnum tipoGestion) {
        for (SolicitudAsociacionPersonaEntidadPagadoraDTO solicitud : solicitudes) {

            List<Object[]> listaEntidadesPagadoras = null;
            if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_ALTA)) {
                listaEntidadesPagadoras = entityManager
                        .createNamedQuery(NamedQueriesConstants.VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_SOLICITAR_ALTA)
                        .setParameter("numeroRadicacion", solicitud.getNumeroRadicado()).getResultList();
            }
            else {
                if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_RETIRO)) {
                    listaEntidadesPagadoras = entityManager
                            .createNamedQuery(NamedQueriesConstants.VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_SOLICITAR_RETIRO)
                            .setParameter("numeroRadicacion", solicitud.getNumeroRadicado()).getResultList();
                }
                else {
                    if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.REGISTRAR_RESULTADO_SOLICITUD)) {
                        listaEntidadesPagadoras = entityManager
                                .createNamedQuery(NamedQueriesConstants.VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_REGISTRAR_RESULTADO_SOLICITUD)
                                .setParameter("numeroRadicacion", solicitud.getNumeroRadicado()).getResultList();
                    }
                }
            }
            if (listaEntidadesPagadoras == null || listaEntidadesPagadoras.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * <b>Descripción</b> Método que se encarga de registrar la gestión de las
     * solicitudes de asociación
     * 
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     * 
     * @param solicitudes
     *        lista con las solicitudes a actualizar
     * 
     * @param user
     *        usuario que realiza la actulización
     * 
     * @return Long consecutivoGestion consecutivo de la gestión
     */
    @SuppressWarnings("unchecked")
    @Override
    public String actualizarGestionSolicitudesAsociacion(Long idEntidadPagadora,
            List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes, UserDTO user) {
        String firmaMetodo = " actualizarGestionSolicitudesAsociacion(Long, List<SolicitudAsociacionPersonaEntidadPagadoraDTO>, UserDTO)";
        try {
            LOGGER.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            // Lista de solicitudes actualizar estado
            List<Long> idsSolitudes = new ArrayList<>();
            List<Long> idsSolitudesRetirar = new ArrayList<>();
            List<Long> idsSolitudesAprobar = new ArrayList<>();
            List<Long> idsSolitudesRechazar = new ArrayList<>();
            
            // Lista de roles Afiliado actualizar estado
            List<Long> idsRolAfiliadoResultadoRetirar = new ArrayList<>();
            List<Long> idsRolAfiliadoResultadoAprobar = new ArrayList<>();
            List<Long> idsRolAfiliadoResultadoRechazar = new ArrayList<>();
            
            // Identificador primera solicitud
            String nroConsecutivoSolicitud = new String(); 
            
            // Se obtiene la primera solicitud de la lista
            SolicitudAsociacionPersonaEntidadPagadoraDTO solicitudDTO = solicitudes.iterator().next();
            EstadoSolicitudPersonaEntidadPagadoraEnum nuevoEstadoSolicitud = null;
            // Se identifica cual es el proceso a realizar
            boolean solictudAltaORetiro = false;
            if (solicitudDTO.getTipoGestion().equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_ALTA)) {
                nuevoEstadoSolicitud = EstadoSolicitudPersonaEntidadPagadoraEnum.SOLICITADA_ALTA;
                solictudAltaORetiro = true;
            } else if(solicitudDTO.getTipoGestion().equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_RETIRO)){
                nuevoEstadoSolicitud = EstadoSolicitudPersonaEntidadPagadoraEnum.RETIRO_SOLICITADO;
                solictudAltaORetiro = true;
            }
            // Se itera la lista de solicitudes para determinar su informacion
            for (SolicitudAsociacionPersonaEntidadPagadoraDTO solicitud : solicitudes) {
                
                // Se obtiene la informacion de la solicitud
                List<Object[]> listaSolicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLITUD_POR_NRO_RADICADO)
                        .setParameter("numeroRadicacion", solicitud.getNumeroRadicado()).getResultList();
                if (listaSolicitudes != null && !listaSolicitudes.isEmpty()) {
                    // Conjunto de resultados 
                    Object[] registros = listaSolicitudes.get(NumerosEnterosConstants.CERO);
                    // Info Solicitud global
                    Solicitud solicitudGlobal = (Solicitud) registros[0];
                    // Info RolAfiliado
                    RolAfiliado rolAfiliado = (RolAfiliado) registros[1];
                    
                    // Se agregan a las listas los valores
                    Long idSolicitud = solicitudGlobal.getIdSolicitud();
                    idsSolitudes.add(idSolicitud);
                    
                    // Se verifica si el proceso debe crear solicitud
                    nroConsecutivoSolicitud = consultarNroSolicitud(idSolicitud);
                    if(TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_ALTA.equals(solicitud.getTipoGestion())
                            || TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_RETIRO.equals(solicitud.getTipoGestion())){
                            asociarPagadorAportes(idEntidadPagadora, rolAfiliado);
                    }
                    // Se verifica el resultado a registrar
                    if (solicitud.getTipoGestion().equals(TipoGestionSolicitudAsociacionEnum.REGISTRAR_RESULTADO_SOLICITUD)) {
                      if (solicitud.getResultadoSolicitud().equals(ResultadoSolicitudPersonaEntidadPagadoraEnum.RETIRAR)) {
                          idsRolAfiliadoResultadoRetirar.add(rolAfiliado.getIdRolAfiliado());
                          idsSolitudesRetirar.add(idSolicitud);
                      }
                      if (solicitud.getResultadoSolicitud().equals(ResultadoSolicitudPersonaEntidadPagadoraEnum.APROBAR)) {
                          idsRolAfiliadoResultadoAprobar.add(rolAfiliado.getIdRolAfiliado());
                          idsSolitudesAprobar.add(idSolicitud);
                      }
                      if (solicitud.getResultadoSolicitud().equals(ResultadoSolicitudPersonaEntidadPagadoraEnum.RECHAZAR)) {
                          idsRolAfiliadoResultadoRechazar.add(rolAfiliado.getIdRolAfiliado());
                          idsSolitudesRechazar.add(idSolicitud);
                      }
                    }
                    
                }
            }
            if (solictudAltaORetiro) {
                // Se ejecuta la actualizacion del estado de la solicitd 
                actualizarGestionSolicitudAsociacion(solicitudDTO.getTipoGestion(), nuevoEstadoSolicitud, user.getNombreUsuario(),
                        idsSolitudes);
                // Se verifica si se debe generar el consecutivo para las solicitudes
                if (nroConsecutivoSolicitud == null || nroConsecutivoSolicitud.isEmpty()) {
                    // Se asocia el consecutivo para todas las solicitudes
                    Long consecutivo = consultarConsecutivo();
                    nroConsecutivoSolicitud = generarConsecutivoSolicitudAsociacion(idsSolitudes, consecutivo,
                            solicitudDTO.getTipoGestion());
                }
            }
            else if(TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_ALTA.equals(solicitudDTO.getTipoGestion())
                    || TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_RETIRO.equals(solicitudDTO.getTipoGestion())){
                    actualizarGestionSolicitudAsociacion(solicitudDTO.getTipoGestion(), solicitudDTO.getEstadoSolicitud(), user.getNombreUsuario(), idsSolitudes);
            }
            // Se crea la respuesta del servicio
            nroConsecutivoSolicitud = "\"" + nroConsecutivoSolicitud + "\"";
            if (!idsSolitudesRetirar.isEmpty()) {
                // Se actualiza estado en la entidad en RolAfiliado a INACTIVO 
                actualizarRolAfiliado(EstadoActivoInactivoEnum.INACTIVO, idsRolAfiliadoResultadoRetirar);

                // Se actualiza el estado de la solicitud de asociación entidad a RETIRADO
                actualizarGestionSolicitudAsociacion(solicitudDTO.getTipoGestion(), EstadoSolicitudPersonaEntidadPagadoraEnum.RETIRADO,
                        user.getNombreUsuario(), idsSolitudesRetirar);
            }
            if (!idsSolitudesAprobar.isEmpty()) {
                // Se actualiza estado en la entidad en RolAfiliado a ACTIVO
                actualizarRolAfiliado(EstadoActivoInactivoEnum.ACTIVO, idsRolAfiliadoResultadoAprobar);

                // Se actualiza el estado de la solicitud de asociación entidad a APROBADA
                actualizarGestionSolicitudAsociacion(solicitudDTO.getTipoGestion(), EstadoSolicitudPersonaEntidadPagadoraEnum.APROBADA,
                        user.getNombreUsuario(), idsSolitudesAprobar);
            }
            if (!idsSolitudesRechazar.isEmpty()) {
                // Se actualiza estado en la entidad en RolAfiliado a INACTIVO 
                actualizarRolAfiliado(EstadoActivoInactivoEnum.INACTIVO, idsRolAfiliadoResultadoRechazar);

                // Se actualiza el estado de la solicitud de asociación entidad a RECHAZADA
                actualizarGestionSolicitudAsociacion(solicitudDTO.getTipoGestion(), EstadoSolicitudPersonaEntidadPagadoraEnum.RECHAZADA,
                        user.getNombreUsuario(), idsSolitudesRechazar);
            }
            LOGGER.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return nroConsecutivoSolicitud;
        } catch (Exception e) {
            LOGGER.error(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * <b>Descripción</b> Método que se encarga de asociar el pagador aportante
     *
     * @param idEntidadPagadora
     *        id entidad pagadora a asociar
     * @param rolAfiliado
     *        objeto a actulizar
     */
    private void asociarPagadorAportes(Long idEntidadPagadora, RolAfiliado rolAfiliado) {
        // Asocia el pagador aportes    	
        entityManager.createNamedQuery(NamedQueriesConstants.ASOCIAR_PAGADOR_APORTES).setParameter("idEntidadPagadora", idEntidadPagadora)
                .setParameter("idRolAfiliado", rolAfiliado.getIdRolAfiliado()).executeUpdate();
    }

    /**
     * <b>Descripción</b>Método que se encarga de consultar el número de nro de
     * solicitud de la Gestión de Solicitud Asociación
     *
     * @param idSolicitud
     *        identificadores de las solicitudes a los que se les generará
     *        el consecutivo
     */
    private String consultarNroSolicitud(Long idSolicitud) {

        return (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSECTUTIVO_GESTION_SOLICITUD_ASOCIACION)
                .setParameter("idSolicitud", idSolicitud).getSingleResult();
    }

    /**
     * <b>Descripción</b>Método que se encarga de generar el número de
     * consecutivo de la Gestión de Solicitud Asociación
     *
     * @param idsSolicitud
     *        identificadores de las solicitudes a los que se les generará
     *        el consecutivo
     */
    private String generarConsecutivoSolicitudAsociacion(List<Long> idsSolicitud, Long consecutivo,
            TipoGestionSolicitudAsociacionEnum tipoGestion) {

        // Se construye el (consecutivo = Número de solicitud), con el
        // formato CCFYYXXXXXX.
        String formatoCadena = String.format("%%0%dd", NumerosEnterosConstants.SEIS);
        String cadenaFormateada = String.format(formatoCadena, consecutivo);
        CacheManager.sincronizarParametrosYConstantes();
        String codigoCCF = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID);

        if (codigoCCF.length() == NumerosEnterosConstants.UNO) {
            codigoCCF = NumerosEnterosConstants.CERO + codigoCCF;
        }

        String YY = null;
        final String SA = "SA";
        final String SR = "SR";
        if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_ALTA)) {
            YY = SA;
        }
        else {
            if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_RETIRO)) {
                YY = SR;
            }
        }
        StringBuilder nroSolicitud = new StringBuilder();
        nroSolicitud.append(codigoCCF);
        nroSolicitud.append(YY);
        nroSolicitud.append(cadenaFormateada);

        // Actualiza sobre SolicitudAsociacionPersonaEntidadPagadora
        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_CONSECUTIVO).setParameter("consecutivo", nroSolicitud.toString())
                .setParameter("idsSolicitudes", idsSolicitud).executeUpdate();
        return nroSolicitud.toString();
    }

    /**
     * <b>Descripción</b>Método que se encarga de actualizar la Gestión de
     * Solicitud Asociación
     *
     * @param tipoGestion
     *        tipo de gestión de la solicitud
     * @param estadoSolicitud
     *        estado al que se actualizará la solicitud
     *
     * @param usuario
     *        Usuario que gestiona la solicitud
     *
     * @param idsSolicitud
     *        identificadores de las solicitudes que serán actualizadas
     */
    private void actualizarGestionSolicitudAsociacion(TipoGestionSolicitudAsociacionEnum tipoGestion,
            EstadoSolicitudPersonaEntidadPagadoraEnum estadoSolicitud, String usuario, List<Long> idsSolicitud) {

        // Actualiza sobre SolicitudAsociacionPersonaEntidadPagadora
        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GESTION_SOLICITUD_ASOCIACION)
                .setParameter("tipoGestion", tipoGestion.toString()).setParameter("estadoSolicitud", estadoSolicitud.toString())
                .setParameter("fechaGestion", Calendar.getInstance().getTime()).setParameter("usuario", usuario)
                .setParameter("idsSolicitudes", idsSolicitud).executeUpdate();
    }

    /**
     * <b>Descripción</b>Método que se encarga de actualizar la Gestión de
     * Solicitud Asociación
     *
     * @param estadoEnEntidadPagadora
     *        estado al que se actualizará el registro
     * 
     * @param idsRolAfiliado
     *        identificadores de los registros que serán actualizados
     */
    private void actualizarRolAfiliado(EstadoActivoInactivoEnum estadoEnEntidadPagadora, List<Long> idsRolAfiliado) {

        // Se actualiza sobre RolAfiliado cuando el resultado de la
        // solicitud es RECHAZAR
        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ROL_AFILIADO)
                .setParameter("estadoEnEntidadPagadora", estadoEnEntidadPagadora.toString()).setParameter("idsRolAfiliado", idsRolAfiliado)
                .executeUpdate();
    }

    /**
     * <b>Descripción</b>Método que se encarga de consultar el número de
     * consecutivo que se colocará a la Gestión de Solicitud Asociación
     *
     * @param idsSolicitud
     *        identificadores de las solicitudes a los que se les generará
     *        el consecutivo
     */
    private Long consultarConsecutivo() {
        // Actualiza sobre SolicitudAsociacionPersonaEntidadPagadora
        return ((BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSECUTIVO).getSingleResult()).longValue();
    }

    /**
     * <b>Descripción</b>Método que se encarga de generar los archivos de
     * gestión de solicitudes de asociación
     *
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     * @param consecutivoGestion
     *        número consecutivo de la gestión
     *
     * @return Response resultado del archivo generado
     */
    @Override
    public Response generarArchivoGestionSolicitudesAsociacion(Long idEntidadPagadora, String consecutivoGestion) {
        DatosFiltroConsultaDTO datoFiltro = new DatosFiltroConsultaDTO();
        datoFiltro.setIdEntidadPagadora(idEntidadPagadora);
        datoFiltro.setConsecutivoGestion(consecutivoGestion);
        FileFormat[] format = { FileFormat.FIXED_TEXT_PLAIN };
        String nombreArchivoConsolidado = "attachment; filename=";
        
        try {
            FileGeneratorOutDTO outDTO = fileGenerator.generate(1L, datoFiltro, format);
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(outDTO.getFixedLengthTxt());

            // Fecha del sistema
            Calendar fechaGeneracionArchivo = Calendar.getInstance();
            Date date = new Date();
            date = fechaGeneracionArchivo.getTime();
            DateFormat df = DateFormat.getDateTimeInstance(NumerosEnterosConstants.TRES, NumerosEnterosConstants.DOS);
            String fechaActualGeneracionArchivo = df.format(date);
            fechaActualGeneracionArchivo = fechaActualGeneracionArchivo.replace("/", "_").replaceFirst(":", "h");
            fechaActualGeneracionArchivo = fechaActualGeneracionArchivo.replace(":", "m");
            fechaActualGeneracionArchivo = fechaActualGeneracionArchivo + "s";

            // Se consulta la entidad pagadora
            EntidadPagadora entidadPagadora = entityManager.find(EntidadPagadora.class, idEntidadPagadora);

            // Se obtiene el nombre de la entidad (
            String nombreEntidad = PersonasUtils.obtenerNombreORazonSocial(entidadPagadora.getEmpresa().getPersona());

            // Se llama al cliente AlmacenarArchivo para consumir el servicio
            // almacenarArchivo y obtener el identificadorArchivo
            InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
            String identificadorArchivo = new String();
            infoFile.setDataFile(outDTO.getFixedLengthTxt());
            infoFile.setFileName(nombreEntidad + "_" + fechaActualGeneracionArchivo + ".txt");
            infoFile.setDocName(nombreEntidad + "_" + fechaActualGeneracionArchivo);
            infoFile.setFileType("text/plain");
            infoFile.setProcessName(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL.name());
            infoFile.setDescription("Archivo txt generado de acuerdo a la entidad y su convenio con cada caja de compensación");

            AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
            almacenarArchivo.execute();
            //identificadorArchivo = (String) almacenarArchivo.getResult();
            identificadorArchivo = (String) almacenarArchivo.getResult().getIdentificadorDocumento();

            // Se guarda el identificadorArchivo
            guardarIdentificadorArchivo(idEntidadPagadora, consecutivoGestion, identificadorArchivo);

            // Se procesa el contenido del archivo a retornar
            ResponseBuilder response = Response.ok(byteArrayIS);
            response.header("Content-Type", "text/plain");
            response.header("Content-Disposition", nombreArchivoConsolidado + infoFile.getFileName());
            return response.build();
        } catch (FileGeneratorException e) {
            LOGGER.debug("Finaliza generarArchivoGestionSolicitudesAsociacion(Long, String");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * <b>Descripción</b> Método que se encarga de guardar el identificador del
     * archivo carta
     * 
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     * 
     * @param tipoGestion
     *        tipo de gestión
     *
     * @param numerosRadicado
     *        lista con números de radicado
     */
    @Override
    public void guardarIdentificadorArchivoCarta(Long idEntidadPagadora, TipoGestionSolicitudAsociacionEnum tipoGestion,
            String identificadorArchivoCarta, List<String> numerosRadicado) {
        try {
            /* Se validan que los datos de entrada no se encuentren vacios */
            if (tipoGestion != null && identificadorArchivoCarta != null && !identificadorArchivoCarta.equals("") && numerosRadicado != null
                    && !numerosRadicado.isEmpty()) {
                /*
                 * Se verifica que el tipo de gestion sea SOLICITAR_ALTA o
                 * SOLICITAR_RETIRO
                 */
                if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_ALTA)
                        || tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.SOLICITAR_RETIRO)) {
                    /*
                     * Actualiza sobre SolicitudAsociacionPersonaEntidadPagadora
                     * el campo identificadorArchivoCarta
                     */
                    entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_IDENTIFICADOR_ARCHIVO_CARTA)
                            .setParameter("identificadorArchivoCarta", identificadorArchivoCarta)
                            .setParameter("numerosRadicado", numerosRadicado).executeUpdate();
                }
                /*
                 * Se verifica el tipo gestion que sea
                 * REGISTRAR_RESULTADO_SOLICITUD
                 */
                if (tipoGestion.equals(TipoGestionSolicitudAsociacionEnum.REGISTRAR_RESULTADO_SOLICITUD)) {
                    /*
                     * Se actualiza el campo identificadorCartaResultadoGestion
                     * en la tabla SolicitudAsociacionPersonaEntidadPagadora
                     */
                    entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_IDENTIFICADOR_CARTA_RESULTADO_GESTION)
                            .setParameter("identificadorCartaResultadoGestion", identificadorArchivoCarta)
                            .setParameter("numerosRadicado", numerosRadicado).executeUpdate();
                }
                LOGGER.debug("Finaliza guardarIdentificadorArchivoCarta(Long, TipoGestionSolicitudAsociacionEnum, String,List<String>)");
            }
            else {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            }
        } catch (Exception e) {
            LOGGER.error("Error guardarIdentificadorArchivoCarta(Long, TipoGestionSolicitudAsociacionEnum, String,List<String>)",e);
            throw new RuntimeException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * <b>Descripción</b> Método que se encarga de guardar el identificador del
     * archivo
     * 
     * @param idEntidadPagadora
     *        Identificador de la entidad pagadora
     *
     * @param consecutivoGestion
     *        número consecutivo de la gestión
     * 
     * @param identificadorArchivo
     *        Identificador archivo
     */
    @Override
    public void guardarIdentificadorArchivo(Long idEntidadPagadora, String consecutivoGestion, String identificadorArchivo) {
        // Actualiza sobre SolicitudAsociacionPersonaEntidadPagadora
        entityManager.createNamedQuery(NamedQueriesConstants.GUARDAR_IDENTIFICADOR_ARCHIVO)
                .setParameter("identificadorArchivo", identificadorArchivo).setParameter("consecutivoGestion", consecutivoGestion)
                .executeUpdate();
    }

    /* (non-Javadoc)
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#consultarEntidadesByTipoAfiliacion(com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum)
     */
    @Override
    public List<ConsultarEntidadPagadoraOutDTO> consultarEntidadesByTipoAfiliacion(TipoAfiliacionEntidadPagadoraEnum tipoAfiliacion) {
        String firmaMetodo = "consultarEntidadesByTipoAfiliacion(TipoAfiliacionEntidadPagadoraEnum) ENTRO";
        LOGGER.info("**_**Entidades pagadoras inicio"+tipoAfiliacion);
        try {
            LOGGER.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            List<ConsultarEntidadPagadoraOutDTO> result = null;
            List<EntidadPagadora> listEntidades;
            // Se consultan las entidades 
            if(tipoAfiliacion.equals(TipoAfiliacionEntidadPagadoraEnum.ENTIDADES_INDEPENDIENTES) || tipoAfiliacion.equals(TipoAfiliacionEntidadPagadoraEnum.ENTIDADES_PENSIONALES)){
                LOGGER.info("**_**Entidades pagadoras if 1 " +  tipoAfiliacion);
                listEntidades = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_PAGADORAS_BY_TIPO_AFILIACION_COMPLETA, EntidadPagadora.class)
                    .setParameter("estadoEntidadPagadora", HabilitadoInhabilitadoEnum.HABILITADO)
                    .setParameter("tipoAfiliacion", tipoAfiliacion)
                    .getResultList();
            }else{
                LOGGER.info("**_**Entidades pagadoras if 2 " +  tipoAfiliacion);
                listEntidades = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_PAGADORAS_BY_TIPO_AFILIACION, EntidadPagadora.class)
                    .setParameter("estadoEntidadPagadora", HabilitadoInhabilitadoEnum.HABILITADO)
                    .setParameter("tipoAfiliacion", tipoAfiliacion)
                    .getResultList();
            }
            if (listEntidades != null && !listEntidades.isEmpty()) {
                result = new ArrayList<>();
                List<ConsultarEstadoDTO> listConsulta = new ArrayList<>();
                for (EntidadPagadora entidadPagadora : listEntidades) {
                    LOGGER.info("**_**Entidades pagadoras entidadPagadora- id: "+entidadPagadora.getIdEntidadPagadora());
                    result.add(convertEntityInOutDTO(entidadPagadora,listConsulta));
                }
                List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
                for (EstadoDTO estadoDTO : listEstado) {
                    for (ConsultarEntidadPagadoraOutDTO dto : result) {
                        if (estadoDTO.getNumeroIdentificacion().equals(dto.getNumeroIdentificacion())
                                && estadoDTO.getTipoIdentificacion().equals(dto.getTipoIdentificacion())
                                && estadoDTO.getEstado() != null) {
                            dto.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().name()));
                            break;
                        }
                    }
                }
            }
            LOGGER.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return result;
        } catch(Exception e){
            LOGGER.error(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * Convierte la entidad en el DTO de salida de las consultas
     * @param entidadPagadora
     * @return
     */
    private ConsultarEntidadPagadoraOutDTO convertEntityInOutDTO(EntidadPagadora entidadPagadora, List<ConsultarEstadoDTO> listConsulta) {
        ConsultarEntidadPagadoraOutDTO entidad;
        if (entidadPagadora.getEmpresa() != null){
            LOGGER.info("**_**convertEntityInOutDTO getEmpresa - id: "+entidadPagadora.getIdEntidadPagadora());
            // Obtiene la informacion de persona
            Persona persona = entidadPagadora.getEmpresa().getPersona();
            // Crea el DTO respuesta del servicio
            entidad = new ConsultarEntidadPagadoraOutDTO(entidadPagadora);

            // Se consulta el estado empleador
            ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
            paramsConsulta.setEntityManager(entityManager);
            paramsConsulta.setNumeroIdentificacion(persona.getNumeroIdentificacion());
            paramsConsulta.setTipoIdentificacion(persona.getTipoIdentificacion());
            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listConsulta.add(paramsConsulta);
        }else{
            LOGGER.info("**_**convertEntityInOutDTO else - id: "+entidadPagadora.getIdEntidadPagadora());
            entidad = new ConsultarEntidadPagadoraOutDTO();
            entidad.setAportante(entidadPagadora.getAportante());
            entidad.setEstadoEmpleador(EstadoEmpleadorEnum.SIN_ESTADO);
            entidad.setEstadoEntidadPagadora(HabilitadoInhabilitadoEnum.HABILITADO);
            entidad.setIdEntidadPagadora(entidadPagadora.getIdEntidadPagadora());
            entidad.setNumeroIdentificacion(null);
            entidad.setRazonSocial(entidadPagadora.getNombreContacto());
            entidad.setEstadoEntidadPagadora(entidadPagadora.getEstadoEntidadPagadora());
            entidad.setTipoAfiliacion(entidadPagadora.getTipoAfiliacion());
        }
        return entidad;
    }
    
    @Override
    public SolicitudAsociacionPersonaEntidadPagadoraModeloDTO consultarSolicitudEntidadPagadora(Long idSolicitudGlobal){
        SolicitudAsociacionPersonaEntidadPagadoraModeloDTO solicitudEntidadPagadoraModelo = new SolicitudAsociacionPersonaEntidadPagadoraModeloDTO();
        SolicitudAsociacionPersonaEntidadPagadora solicitudEntidadPagadora = 
                    consultarSolicitudEntidadPagadoraPorIdGlobal(idSolicitudGlobal);
        solicitudEntidadPagadoraModelo.convertToDTO(solicitudEntidadPagadora);
        return solicitudEntidadPagadoraModelo;
    }
    
    @Override
    public void actualizarEstadoSolicitudEntidadPagadora(Long idSolicitudGlobal, EstadoSolicitudPersonaEntidadPagadoraEnum estado){
        SolicitudAsociacionPersonaEntidadPagadora solicitudEntidadPagadora = 
                consultarSolicitudEntidadPagadoraPorIdGlobal(idSolicitudGlobal);
        
        if(EstadoSolicitudPersonaEntidadPagadoraEnum.CERRADA.equals(estado)){ 
            solicitudEntidadPagadora.getSolicitudGlobal()
                .setResultadoProceso(ResultadoProcesoEnum.valueOf(solicitudEntidadPagadora.getEstado().name()));
        }

        solicitudEntidadPagadora.setEstado(estado);
        entityManager.merge(solicitudEntidadPagadora);
    }
    
    private SolicitudAsociacionPersonaEntidadPagadora consultarSolicitudEntidadPagadoraPorIdGlobal(Long idSolicitudGlobal){
        try{
            return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD_ENTIDAD_PAGADORA_POR_ID_SOLICITUD, 
                            SolicitudAsociacionPersonaEntidadPagadora.class)
                    .setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();
        } catch(NoResultException e){
            LOGGER.error("No se encontró solicitud de entidad pagadora");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.entidades.pagadoras.service.EntidadesPagadorasService#validarConvenioEntidad(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public Boolean validarConvenioEntidad(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion){
        String firma = "validarConvenioEntidad(TipoIdentificacionEnum, String)";
        try {
            LOGGER.debug(ConstantesComunes.INICIO_LOGGER + firma);
            Boolean result = false;
            // Se obtienen los datos de las entidades con convenio 
            String nitConfa = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_CONFA);
            String nitColpensiones = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_COLPENSIONES);
            
            // Se obtiene el nit de la CCF logueada
            String nitCCF = (String) CacheManager.getParametro(ParametrosSistemaConstants.NUMERO_ID_CCF);
            
            // Se verifica si la identificacion de la entidad pagadora es igual a la entidad del convenio
            if (nitConfa != null && nitColpensiones != null
                    && (nitCCF.equals(nitConfa) || nitCCF.contains(nitConfa)) 
                    && (nitColpensiones.equals(numeroIdentificacion) || nitColpensiones.contains(numeroIdentificacion))) {
                result = true;
            }
            LOGGER.debug(ConstantesComunes.FIN_LOGGER + firma);
            return result;
        } catch (Exception e) {
            LOGGER.error(ConstantesComunes.FIN_LOGGER + firma);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> consultarPersonasAsociadasEntidad(UriInfo uriInfo,
            HttpServletResponse response, Long idEntidadPagadora) {
        String firma = "consultarSolicitudesAsociacionPersona(Long)";
        try {
            LOGGER.debug(ConstantesComunes.INICIO_LOGGER + firma);
            QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
            queryBuilder.addParam("idEntidadPagadora", idEntidadPagadora);
            queryBuilder.addOrderByDefaultParam("-tipoIdentificacion");

            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTA_SOLICITUD_ASOCIACION_ENTIDAD_PAGADORA_BY_ID, null);
            List<Object[]> listPersonasAsociadas = query.getResultList();
            List<SolicitudAsociacionPersonaEntidadPagadoraDTO> listSolicitudes = new ArrayList<>();

            for (Object[] objSol : listPersonasAsociadas) {
                SolicitudAsociacionPersonaEntidadPagadoraDTO personaEntidadPagadoraDTO = new SolicitudAsociacionPersonaEntidadPagadoraDTO();
                personaEntidadPagadoraDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) objSol[0]));
                personaEntidadPagadoraDTO.setNumeroIdentificacion((String) objSol[1]);
                personaEntidadPagadoraDTO.setTipoAfiliacion(TipoAfiliadoEnum.valueOf((String) objSol[2]).getDescripcion());
                personaEntidadPagadoraDTO.setNombre((String) objSol[3]);
                personaEntidadPagadoraDTO.setUltimaFechaNovedad(objSol[4] != null ? (((Date) objSol[4])) : null);
                listSolicitudes.add(personaEntidadPagadoraDTO);
            }
            LOGGER.debug(ConstantesComunes.FIN_LOGGER + firma);
            return listSolicitudes;
        } catch (Exception e) {
            LOGGER.error(ConstantesComunes.FIN_LOGGER + firma);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
}
