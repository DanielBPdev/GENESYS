package com.asopagos.afiliaciones.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.attribute.AclFileAttributeView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.afiliaciones.business.interfaces.IconsultasModeloAud;
import com.asopagos.afiliaciones.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.dto.BeneficiarioSTDTO;
import com.asopagos.afiliaciones.dto.CategoriaAfiliacionSTDTO;
import com.asopagos.afiliaciones.dto.CategoriaAfiliadoSTDTO;
import com.asopagos.afiliaciones.dto.CategoriaBeneficiarioSTDTO;
import com.asopagos.afiliaciones.dto.CategoriaPersonaOutDTO;
import com.asopagos.afiliaciones.dto.CategoriaSTDTO;
import com.asopagos.afiliaciones.dto.CertificadoEscolaridadOutDTO;
import com.asopagos.afiliaciones.dto.ContactosEmpleadorOutDTO;
import com.asopagos.afiliaciones.dto.DatoHistoricoAfiliadoOutDTO;
import com.asopagos.afiliaciones.dto.DatosContactoEmpleadorOutDTO;
import com.asopagos.afiliaciones.dto.GrupoFamiliarOutDTO;
import com.asopagos.afiliaciones.dto.GrupoFamiliarSTDTO;
import com.asopagos.afiliaciones.dto.InfoAfiliadoOutDTO;
import com.asopagos.afiliaciones.dto.InfoBasicaEmpleadorOutDTO;
import com.asopagos.afiliaciones.dto.InfoBasicaPersonaOutDTO;
import com.asopagos.afiliaciones.dto.InfoCiudadOutDTO;
import com.asopagos.afiliaciones.dto.InfoEmpleadorInDTO;
import com.asopagos.afiliaciones.dto.InfoPadresBiologicosOutDTO;
import com.asopagos.afiliaciones.dto.InfoSalarioMinimoOutDTO;
import com.asopagos.afiliaciones.dto.InfoTotalAfiliadoOutDTO;
import com.asopagos.afiliaciones.dto.InfoTotalBeneficiarioOutDTO;
import com.asopagos.afiliaciones.dto.InfoTotalEmpleadorOutDTO;
import com.asopagos.afiliaciones.dto.RepresentanteLegalOutDTO;
import com.asopagos.afiliaciones.dto.InfoAfiliadosPrincipalesOutDTO;
import com.asopagos.afiliaciones.dto.SucursalEmpresaOutDTO;
import com.asopagos.afiliaciones.dto.UltimoSalarioAfiliadoOutDTO;
import com.asopagos.afiliaciones.dto.grupoCategoriasSTDTO;
import com.asopagos.afiliaciones.service.IntegracionAfiliacionService;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.google.gson.Gson;

import javafx.beans.NamedArg;

import java.io.IOException;
import java.util.Arrays;
import javax.persistence.TypedQuery;
import com.asopagos.afiliaciones.dto.ConsultaAfiliadoRecaudosPagosDTO;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import javax.servlet.http.HttpServletRequest;
// TODO 21: Importar javax.ws.rs.core.Response; java.time.Duration; java.time.Instant; com.asopagos.util.AuditoriaIntegracionInterceptor
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.Instant;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import java.util.Date;
import com.asopagos.rest.security.dto.UserDTO;

//import com.asopagos.afiliaciones.wsCajasan.service.AfiliadosCajaSanWebService;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultaAfiliadoCajaSanOutDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultaAfiliadoInDTO;
import com.asopagos.dto.webservices.ResponseDTO;
import com.asopagos.enumeraciones.CodigosErrorWebservicesEnum;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarIdentificacionDinamicoOutDTO;
import com.asopagos.dto.webservices.AfiliacionEmpleadorDTO;

import com.asopagos.afiliaciones.wsCajasan.dto.BuscarTarjetaOutDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.BuscarTarjetaInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.GetMunicipiosCalendarioInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.GetMunicipiosCalendarioOutDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarPagosPCInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarPagosPCOutDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ValidaEmpresaInDTO;
import com.asopagos.dto.webservices.AfiliaPensionadoDTO;
import com.asopagos.dto.*;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.afiliaciones.empleadores.web.composite.clients.DigitarYRadicarSolicitudAfiliacionWS; 
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarAportesInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarAportesOutDTO;
import java.util.Calendar;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarInformacionDinamicoInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarAportesOutDTO;
import org.springframework.validation.*;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.webservices.AfiliarPersonaACargoDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedad;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.ccf.personas.*;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.validaciones.clients.VerificarSolicitudesEnCurso;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ActualizarDatosDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.CertificadoFosfecDTO;
import com.asopagos.dto.webservices.UsuarioDTO;
import com.asopagos.enumeraciones.TiposUsuarioWebServiceEnum;
import com.asopagos.afiliaciones.wsCajasan.dto.ActualizarDatosUsuarioDTO;
import com.asopagos.usuarios.clients.*;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.afiliaciones.wsCajasan.dto.AuthenticationResultDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.RedireccionarInDTO;
import com.asopagos.usuarios.clients.GenerarTokenAcceso;
import com.asopagos.dto.cargaMultiple.UsuarioGestionDTO;
import com.asopagos.dto.webservices.AfiliaTrabajadorDepDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.asopagos.enumeraciones.TipoConsultanteAportesWS;
import com.asopagos.afiliaciones.personas.composite.clients.RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados;
import com.asopagos.afiliaciones.personas.composite.clients.AsignarSolicitudAfiliacionPersona;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliaciones.personas.composite.clients.DigitarDatosPersona;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.afiliaciones.personas.web.composite.clients.AfiliarTrabajador;
import com.asopagos.afiliaciones.personas.web.composite.clients.IniciarVerificarInformacionSolicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.afiliaciones.personas.web.composite.clients.DigitarInformacionContactoWS;
import com.asopagos.dto.webservices.AfiliarTrabajadorIndDTO;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.entidades.transversal.personas.OcupacionProfesion;
import com.asopagos.validaciones.clients.ExisteRegistraduriaNacional;
import com.asopagos.afiliaciones.wsCajasan.dto.ActualizarDatosIdentificacionDTO;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.dto.webservices.ConsultaCargueDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ActualizarDatosEmpleadorInDTO;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;

@Stateless
public class IntegracionAfiliacionBusiness  implements IntegracionAfiliacionService{

    /** 
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "afiliaciones_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(IntegracionAfiliacionBusiness.class);
    
    /**
     * Inject del EJB para consultas en modelo Core_aud entityManager
     */
    @Inject
    private IconsultasModeloAud consultasAud;

    @Inject
    private javax.validation.Validator validator;

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoBasicaPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response obtenerInfoBasicaPersona(TipoIdentificacionEnum tipoID,
            String identificacion, HttpServletRequest requestContext, UserDTO userDTO) {
    	//comment validación push al nuevo repo de master
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoBasicaPersona(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacion", identificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        List<InfoBasicaPersonaOutDTO> salida = new ArrayList<>();
        try {
            List<Object[]> outDTOs = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_BASICA_PERSONA)
                    .setParameter("tipoIdAfiliado", tipoID.name())
                    .setParameter("numeroIdAfiliado", identificacion)
                    .getResultList();
            InfoBasicaPersonaOutDTO dto;
            for(Object[] obj : outDTOs) { 
            	dto = new InfoBasicaPersonaOutDTO();
            	if(obj[0] != null) {
            		dto.setTipoAfiliado(TipoAfiliadoEnum.valueOf(obj[0].toString()));
            	}
            	if(obj[1] != null) {
            		dto.setTipoID(TipoIdentificacionEnum.valueOf(obj[1].toString()));
            	}
            	dto.setIdentificacion(obj[2] != null ? obj[2].toString() : null);
            	dto.setPrimerNombre(obj[3] != null ? obj[3].toString() : null);
            	dto.setSegundoNombre(obj[4] != null ? obj[4].toString() : null);
            	dto.setPrimerApellido(obj[5] != null ? obj[5].toString() : null);
            	dto.setSegundoApellido(obj[6] != null ? obj[6].toString() : null);
            	dto.obtenerNombreCompleto();
            	
            	if(obj[7] != null) {
            		dto.setFechaNacimiento(obj[7].toString());
            	}
            	if(obj[8] != null) {
            		dto.setGenero(GeneroEnum.valueOf(obj[8].toString()));
            	}
            	dto.setDepartamentoCodigo(obj[9] != null ? obj[9].toString() : null);
            	dto.setDepartamentoNombre(obj[10] != null ? obj[10].toString() : null);
            	dto.setMunicipioCodigo(obj[11] != null ? obj[11].toString() : null);
            	dto.setMunicipioNombre(obj[12] != null ? obj[12].toString() : null);
            	dto.setDireccionResidencia(obj[13] != null ? obj[13].toString() : null);
            	dto.setTelefonoFijo(obj[14] != null ? obj[14].toString() : null);
            	dto.setCelular(obj[15] != null ? obj[15].toString() : null);
            	dto.setCorreoElectronico(obj[16] != null ? obj[16].toString() : null);
            	if(obj[17] != null) {
            		dto.setEstado(EstadoAfiliadoEnum.valueOf(obj[17].toString()));
            	}
            	salida.add(dto);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            

        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,salida,entityManager,auditoriaIntegracionServicios);
        }
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,salida,entityManager,auditoriaIntegracionServicios);
    }  
    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoTotalAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerInfoTotalAfiliado(TipoIdentificacionEnum tipoID, String identificacionAfiliado, String identificacionBeneficiario, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoTotalAfiliado(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        parametrosMetodo.put("identificacionBeneficiario",identificacionBeneficiario);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

        List<InfoTotalAfiliadoOutDTO> salida = new ArrayList<>();
        try {
        	
        	Boolean isAfiliadoPrincipal = false;
            if(identificacionAfiliado != null && identificacionBeneficiario == null){
                obtenerInfoTotalAfiliadoEspecifico(tipoID, identificacionAfiliado, salida);
                isAfiliadoPrincipal = true;
            }
            else if(identificacionBeneficiario != null && identificacionAfiliado == null){
                
                List<Object[]> afiliadosPrincipalesBeneficiario = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_PRINCIPALES_BENEFICIARIO_SRV_TRA)
                        .setParameter("tipoIdentificacion", tipoID.name())
                        .setParameter("numeroIdentificacion", identificacionBeneficiario)
                        .getResultList();

                if(afiliadosPrincipalesBeneficiario != null && !afiliadosPrincipalesBeneficiario.isEmpty()){
                    for (Object[] datoAfiliado : afiliadosPrincipalesBeneficiario) {
                        obtenerInfoTotalAfiliadoEspecifico(TipoIdentificacionEnum.valueOf(datoAfiliado[0].toString()), datoAfiliado[1].toString(), salida);
                    }
                }
            }
            
            if(salida != null && !salida.isEmpty()){
            	if(isAfiliadoPrincipal){
            		for (InfoTotalAfiliadoOutDTO infoTotalAfiliadoOutDTO : salida) {
            			infoTotalAfiliadoOutDTO.setAfiliadoPrincipal((infoTotalAfiliadoOutDTO.getTipoAfiliado() != null) ? isAfiliadoPrincipal : !isAfiliadoPrincipal);
    				}
            	}
            	else{
            		for (InfoTotalAfiliadoOutDTO infoTotalAfiliadoOutDTO : salida) {
            			infoTotalAfiliadoOutDTO.setAfiliadoPrincipal(isAfiliadoPrincipal);
    				}
            	}
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        }  catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,salida,entityManager,auditoriaIntegracionServicios);
        }
         return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,salida,entityManager,auditoriaIntegracionServicios);
    }

    public List<InfoAfiliadosPrincipalesOutDTO> consultarAfiliadosPrincipalesPorBeneficiario(TipoIdentificacionEnum tipoID, String identificacion) {
        String firmaServicio = "IntegracionAfiliacionBusiness.consultarAfiliadosPrincipalesPorBeneficiario(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<InfoAfiliadosPrincipalesOutDTO> outDTO = new ArrayList<>();
        try {

            List<Object[]> afiliadosPrincipalesBeneficiario = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_PRINCIPALES_POR_BENEFICIARIO)
                        .setParameter("tipoID", tipoID.name())
                        .setParameter("identificacion", identificacion)
                        .getResultList();
        	
            InfoAfiliadosPrincipalesOutDTO dto;
            if(afiliadosPrincipalesBeneficiario != null && !afiliadosPrincipalesBeneficiario.isEmpty()){
                for(Object[] obj : afiliadosPrincipalesBeneficiario) { 
                    dto = new InfoAfiliadosPrincipalesOutDTO();
                    if(obj[0] != null) {
                        dto.setTipoID(TipoIdentificacionEnum.valueOf(obj[0].toString()));
                    }
                    dto.setIdentificacion(obj[1] != null ? obj[1].toString() : null);
                    dto.setRazonSocial(obj[2] != null ? obj[2].toString() : null);
                    dto.setPerId(obj[3] != null ? obj[3].toString() : null);
                    outDTO.add(dto);
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return outDTO;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
        }
    }


    /**
     * @param inDTO
     * @param identificacion
     * @param outDTO
     */
    private void obtenerInfoTotalAfiliadoEspecifico(TipoIdentificacionEnum tipoIdentificacion, String identificacion,
            List<InfoTotalAfiliadoOutDTO> outDTO) {
        List<Object[]> outDTOs = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_TOTAL_PERSONA)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", identificacion)
                .getResultList();
        
        if(outDTOs != null && !outDTOs.isEmpty())
        {
            for (Object[] obj : outDTOs) {
                
                InfoTotalAfiliadoOutDTO salida = new InfoTotalAfiliadoOutDTO();
                
                salida.setTipoAfiliado(obj[0] != null ? TipoAfiliadoEnum.valueOf(obj[0].toString()) : null);
                salida.setClaseIndependiente(obj[1] != null ? ClaseIndependienteEnum.valueOf(obj[1].toString()) : null);
                salida.setClaseTrabajador(obj[2] != null ? ClaseTrabajadorEnum.valueOf(obj[2].toString()) : null);
                salida.setTipoID(obj[3] != null ? TipoIdentificacionEnum.valueOf(obj[3].toString()) : null);
                salida.setIdentificacion((obj[4] != null && !obj[4].toString().equals("")) ? obj[4].toString() : "");
                salida.setPrimerNombre((obj[5] != null && !obj[5].toString().equals("")) ? obj[5].toString() : "");
                salida.setSegundoNombre((obj[6] != null && !obj[6].toString().equals("")) ? obj[6].toString() : "");
                salida.setPrimerApellido((obj[7] != null && !obj[7].toString().equals("")) ? obj[7].toString() : "");
                salida.setSegundoApellido((obj[8] != null && !obj[8].toString().equals("")) ? obj[8].toString() : "");
                salida.obtenerNombreCompleto();
                salida.setFechaNacimiento((obj[9] != null && !obj[9].toString().equals("")) ? obj[9].toString() : "");
                
                if(salida.getFechaNacimiento()!=null){
                	DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaNacimiento;
					try {
						fechaNacimiento = formatoFecha.parse(salida.getFechaNacimiento());
						salida.setEdad(CalendarUtils.calcularEdadAnos(fechaNacimiento));
					} catch (ParseException e) {
			            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
					}
                }
                
                salida.setFechaFallecido((obj[10] != null && !obj[10].toString().equals("")) ? obj[10].toString() : null);
                salida.setEstadoCivil(obj[11] != null ? EstadoCivilEnum.valueOf(obj[11].toString()) : null);
                salida.setGenero(obj[12] != null ? GeneroEnum.valueOf(obj[12].toString()) : null);
                salida.setDireccionResidencia((obj[13] != null && !obj[13].toString().equals("")) ? obj[13].toString() : "");
                salida.setHabitaCasaPropia(obj[14] != null ? Boolean.valueOf(obj[14].toString()) : null);
                salida.setMunicipioCodigo((obj[15] != null && !obj[15].toString().equals("")) ? obj[15].toString() : "");
                salida.setMunicipioNombre((obj[16] != null && !obj[16].toString().equals("")) ? obj[16].toString() : "");
                salida.setDepartamentoCodigo((obj[17] != null && !obj[17].toString().equals("")) ? obj[17].toString() : "");
                salida.setDepartamentoNombre((obj[18] != null && !obj[18].toString().equals("")) ? obj[18].toString() : "");
                salida.setCodigoPostal((obj[19] != null && !obj[19].toString().equals("")) ? obj[19].toString() : null);
                salida.setTelefonoFijo((obj[20] != null && !obj[20].toString().equals("")) ? obj[20].toString() : "");
                salida.setCelular((obj[21] != null && !obj[21].toString().equals("")) ? obj[21].toString() : "");
                salida.setCorreoElectronico((obj[22] != null && !obj[22].toString().equals("")) ? obj[22].toString() : "");
                salida.setAutorizacionEnvioEmail(obj[23] != null ? Boolean.valueOf(obj[23].toString()) : null);
                salida.setAutorizacionDatosPersonales(obj[24] != null ? Boolean.valueOf(obj[24].toString()) : null);
                salida.setSalario(obj[25] != null ? new BigDecimal(obj[25].toString()).intValue() : 0);
                salida.setCategoria(obj[26] != null ? CategoriaPersonaEnum.valueOf(obj[26].toString()) : null);
                salida.setPorcentajeAporte(obj[27] != null ? BigDecimal.valueOf(Double.valueOf(obj[27].toString())) : null);
                salida.setCargo((obj[28] != null && !obj[28].toString().equals("")) ? obj[28].toString() : null);
                salida.setFechaIngresoEmpresa((obj[29] != null && !obj[29].toString().equals("")) ? obj[29].toString() : null);
                salida.setFechaAfiliacionCCF((obj[30] != null && !obj[30].toString().equals("")) ? obj[30].toString() : null);
                salida.setFechaRetiro((obj[31] != null && !obj[31].toString().equals("")) ? obj[31].toString() : "");
                salida.setMotivoDesafiliacion(obj[32] != null ? MotivoDesafiliacionAfiliadoEnum.valueOf(obj[32].toString()) : null);
                salida.setHoraslaboradasMes(obj[33] != null ? Short.valueOf(obj[33].toString()) : null);
                salida.setEstadoAfiliacion(obj[34] != null ? EstadoAfiliadoEnum.valueOf(obj[34].toString()) : null);
                salida.setTipoIdentificacionEmpleador(obj[35] != null ? TipoIdentificacionEnum.valueOf(obj[35].toString()) : null);
                salida.setNumeroIdentificacionEmpleador((obj[36] != null && !obj[38].toString().equals("")) ? obj[36].toString() : "");
                salida.setDigitoVerificacion((obj[37] != null && !obj[37].toString().equals("")) ? obj[37].toString() : null);
                salida.setNombreEmpleador((obj[38] != null && !obj[38].toString().equals("")) ? obj[38].toString() : null);
                salida.setSucursalEmpleador((obj[39] != null && !obj[39].toString().equals("")) ? obj[39].toString() : "");
                salida.setNombreSucursalEmpleador((obj[40] != null && !obj[40].toString().equals("")) ? obj[40].toString() : null);
                salida.setMarcaCondicionInvalidez(obj[41] != null ? Boolean.valueOf(obj[41].toString()) : null);
                salida.setUsuarioGestionRegistro((obj[42] != null && !obj[42].toString().equals("")) ? obj[42].toString() : null);
                salida.setFechaCreacionRegistro((obj[43] != null && !obj[43].toString().equals("")) ? obj[43].toString() : null);
                salida.setClasificacion(obj[44] != null ? ClasificacionEnum.valueOf(obj[44].toString()) : null);
                salida.setCodigoCCF((String)CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
                salida.setUltimoPagoCuotaMonetaria((obj[45] != null && !obj[45].toString().equals("")) ? obj[45].toString() : null);
                salida.setUltimoPeriodoPagoAportes((obj[46] != null && !obj[46].toString().equals("")) ? obj[46].toString() : "");
                salida.setNumeroTarjeta((obj[47] != null && !obj[47].toString().equals("")) ? obj[47].toString() : null);
                salida.setNivelEducativo((obj[48] != null && !obj[48].toString().equals("")) ? obj[48].toString() : null);
                salida.setGradoAcademico((obj[49] != null && !obj[49].toString().equals("")) ? obj[49].toString() : null);
                salida.setFechaExpedicionDocumento((obj[50] != null && !obj[50].toString().equals("")) ? obj[50].toString() : null);
                salida.setCabezahogar(obj[51] != null ? Boolean.valueOf(obj[51].toString()) : null);

//                List<Beneficiario> beneficiarios = (List<Beneficiario>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_TOTAL_RESTANTE_PERSONA)
//                        .setParameter("tipoIdAfiliado", tipoIdentificacion)
//                        .setParameter("numeroIdAfiliado", identificacion)
//                        .getResultList();
                
                List<Object[]> beneficiarios = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_TOTAL_RESTANTE_PERSONA)
                        .setParameter("tipoIdAfiliado", tipoIdentificacion.name())
                        .setParameter("numeroIdAfiliado", identificacion)
                        .getResultList();
                
                
                Boolean isAfiliadoPrincipal = false;
                
                Short numeroHijos = 0;
                if (beneficiarios != null && !beneficiarios.isEmpty()) {
                    isAfiliadoPrincipal = true;
                    
                    for (Object[] beneficiario : beneficiarios) {
                        if(ClasificacionEnum.HIJO_BIOLOGICO.name().equals(beneficiario[0].toString()) ||
                                ClasificacionEnum.HIJO_ADOPTIVO.name().equals(beneficiario[0].toString()) ||
                                ClasificacionEnum.HIJASTRO.name().equals(beneficiario[0].toString()) ||
                                ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES.name().equals(beneficiario[0].toString()) ||
                                ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name().equals(beneficiario[0].toString())){
                            numeroHijos++;
                        }
                    }
                }
                salida.setAfiliadoPrincipal(isAfiliadoPrincipal);
                salida.setNumeroHijos(numeroHijos);
                
//                List<Object[]> categoria = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_PERSONA_TRA)
//                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
//                        .setParameter("numeroIdentificacion", identificacion)
//                        .getResultList();
//                
//                for (Object[] cat : categoria) {
//                    salida.setClasificacion(cat[0] != null ? ClasificacionEnum.valueOf(cat[0].toString()) : null);
//                    salida.setCategoria(cat[1] != null ? CategoriaPersonaEnum.valueOf(cat[1].toString()) : null);
//                }
                
                outDTO.add(salida);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerEstadoCategoriaPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerEstadoCategoriaPersona(TipoIdentificacionEnum tipoID, String identificacion, String fechaInicio, String fechaFinal, HttpServletRequest requestContext, UserDTO userDTO) {
        
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerEstadoCategoriaPersona(TipoIdentificacionEnum, String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacion", identificacion);
        parametrosMetodo.put("fechaInicio",fechaInicio);
        parametrosMetodo.put("fechaFinal",fechaInicio);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        
        //se crear el dto que contendrá la respuesta
        CategoriaPersonaOutDTO categoriaPersona = new CategoriaPersonaOutDTO();
        LocalDateTime fechaInicial, fechaFin;
        
        //se validan las fechas ingresadas, el valor por defecto de las mismas es el inicio y fin del día actual.
        try{
        	fechaInicial = fechaInicio != null ? LocalDate.parse(fechaInicio).atStartOfDay() : LocalDate.now().atStartOfDay();
        	fechaFin = fechaFinal != null ? LocalDate.parse(fechaFinal).atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);
        	
       } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,categoriaPersona,entityManager,auditoriaIntegracionServicios);
       }
      

        Object[] personaConsulta = null;
        //se consulta si la persona está como afiliado y/o beneficiario, de no encontrar registro para la persona
        //el servicio devuelve un dto vacío.
        try {
        	personaConsulta = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.DEFINIR_PERSONA_CONSULTA_ESTADO_CATEGORIA_SRV_TRA)
                    .setParameter("tipoIdentificacion", tipoID.name())
                    .setParameter("numeroIdentificacion", identificacion)
                    .getSingleResult();
		} catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,categoriaPersona,entityManager,auditoriaIntegracionServicios);
        }
        //valida si la persona está afiliada como afiliado
        if(personaConsulta[0] != null){
        	CategoriaAfiliadoSTDTO categoriaAfiliado = new CategoriaAfiliadoSTDTO();

        	//se obtienen los datos del afiliado y del beneficiario conyuge activo (si es que lo tiene)
        	List<Object[]> datosAfiliado = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_AFILIADO_SRV_TRA)
        			.setParameter("tipoIdentificacion", tipoID.name())
        			.setParameter("numeroIdentificacion", identificacion)
        			.getResultList();
        	
        	List<Object[]> datosBeneficiarios = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_BENEFICIARIOS_AFILIADO_SRV)
        			.setParameter("idAfiliado",personaConsulta[0].toString())
        			.setParameter("fechaInicio", fechaInicial.atZone(ZoneId.systemDefault()).toEpochSecond()*1000L)
        			.setParameter("fechaFin", fechaFin.atZone(ZoneId.systemDefault()).toEpochSecond()*1000L)
        			.getResultList();
        	
        	//valida si la consulta de datosAfiliado obtuvo información
        	if(datosAfiliado != null && !datosAfiliado.isEmpty()){
        		
        		grupoCategoriasSTDTO arregloPrincipal = new grupoCategoriasSTDTO();
            	
        		//se mapean los datos del afiliado
        		arregloPrincipal.setTipoId(datosAfiliado.get(0)[0] != null ? TipoIdentificacionEnum.valueOf(datosAfiliado.get(0)[0].toString()) : null);
        		arregloPrincipal.setIdentificacion(datosAfiliado.get(0)[1] != null ? datosAfiliado.get(0)[1].toString() : null);
        		arregloPrincipal.setPrimerNombre(datosAfiliado.get(0)[2] != null ? datosAfiliado.get(0)[2].toString() : null);
        		arregloPrincipal.setSegundoNombre(datosAfiliado.get(0)[3] != null ? datosAfiliado.get(0)[3].toString() : null);
        		arregloPrincipal.setPrimerApellido(datosAfiliado.get(0)[4] != null ? datosAfiliado.get(0)[4].toString() : null);
        		arregloPrincipal.setSegundoApellido(datosAfiliado.get(0)[5] != null ? datosAfiliado.get(0)[5].toString() : null);
        		arregloPrincipal.setEstadoAfiliacion(datosAfiliado.get(0)[6] != null ? EstadoAfiliadoEnum.valueOf(datosAfiliado.get(0)[6].toString()) : null);
        		
        		//se consultan las categorias para todos los tipos de afiliación del afiliado
        		List<Object[]> categoriasAfiliado = consultarCategoriasAfiliado(tipoID, identificacion, fechaInicial, fechaFin);
        		
        		//se mapean las categorias si la consulta anterior obtuvo datos
        		if(categoriasAfiliado != null && !categoriasAfiliado.isEmpty()){
        			CategoriaAfiliacionSTDTO categoriasDependiente = new CategoriaAfiliacionSTDTO();
					CategoriaAfiliacionSTDTO categoriasIndependiente = new CategoriaAfiliacionSTDTO();
					CategoriaAfiliacionSTDTO categoriasPensionado = new CategoriaAfiliacionSTDTO();
					List<CategoriaSTDTO> arregloCategoriasDependiente = new ArrayList<>();
					List<CategoriaSTDTO> arregloCategoriasIndependiente = new ArrayList<>();
					List<CategoriaSTDTO> arregloCategoriasPensionado = new ArrayList<>();
					
        			for (Object[] ca : categoriasAfiliado) {
						CategoriaSTDTO cat = new CategoriaSTDTO();
						cat.setFecha(ca[1] != null ? ca[1].toString() : null);
						cat.setCategoria(ca[2] != null ? CategoriaPersonaEnum.valueOf(ca[2].toString()) : null);
						
						if(ca[3] != null){
							if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(ca[3].toString()))){
								arregloCategoriasDependiente.add(cat);
								categoriasDependiente.setClasificacion(ca[0] != null ? ca[0].toString() : null);
							}
							else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(ca[3].toString()))){
								arregloCategoriasIndependiente.add(cat);
								categoriasIndependiente.setClasificacion(ca[0] != null ? ca[0].toString() : null);
							}
							else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(ca[3].toString()))){
								arregloCategoriasPensionado.add(cat);
								categoriasPensionado.setClasificacion(ca[0] != null ? ca[0].toString() : null);
							}
						}	
					}
        			//se ordenan las listas de categorias (de la más reciente a la más antigua)
        			Collections.reverse(arregloCategoriasDependiente);
        			Collections.reverse(arregloCategoriasIndependiente);
        			Collections.reverse(arregloCategoriasPensionado);
        			
        			//se agregan los arreglos de categorias a cada tipo de afiliación
        			categoriasDependiente.setArregloCategorias(arregloCategoriasDependiente);
        			categoriasIndependiente.setArregloCategorias(arregloCategoriasIndependiente);
        			categoriasPensionado.setArregloCategorias(arregloCategoriasPensionado);
        			
        			//se agregan los datos de categorias de los diferentes tipos de afiliación de afiliado
        			arregloPrincipal.setArregloDependiente(categoriasDependiente);
        			arregloPrincipal.setArregloIndependiente(categoriasIndependiente);
        			arregloPrincipal.setArregloPensionado(categoriasPensionado);
        		}
        		
        		//se agrega a la lista
        		categoriaAfiliado.setArregloPrincipal(arregloPrincipal);
        		
        		if(datosAfiliado.get(0)[7] != null && datosAfiliado.get(0)[7].toString().equals("1")){
        			grupoCategoriasSTDTO arregloConyuge = new grupoCategoriasSTDTO();
        			arregloConyuge.setTipoId(datosAfiliado.get(0)[8] != null ? TipoIdentificacionEnum.valueOf(datosAfiliado.get(0)[8].toString()) : null);
        			arregloConyuge.setIdentificacion(datosAfiliado.get(0)[9] != null ? datosAfiliado.get(0)[9].toString() : null);
        			arregloConyuge.setPrimerNombre(datosAfiliado.get(0)[10] != null ? datosAfiliado.get(0)[10].toString() : null);
        			arregloConyuge.setSegundoNombre(datosAfiliado.get(0)[11] != null ? datosAfiliado.get(0)[11].toString() : null);
        			arregloConyuge.setPrimerApellido(datosAfiliado.get(0)[12] != null ? datosAfiliado.get(0)[12].toString() : null);
        			arregloConyuge.setSegundoApellido(datosAfiliado.get(0)[13] != null ? datosAfiliado.get(0)[13].toString() : null);
        			arregloConyuge.setEstadoAfiliacion(datosAfiliado.get(0)[14] != null ? EstadoAfiliadoEnum.valueOf(datosAfiliado.get(0)[14].toString()) : null);

        			//se consultan las categorias para todos los tipos de afiliación del conyuge como afiliado
            		List<Object[]> categoriasConyuge = consultarCategoriasAfiliado(arregloConyuge.getTipoId(), arregloConyuge.getIdentificacion(), fechaInicial, fechaFin); 
            		
            		//se mapean las categorias si la consulta anterior obtuvo datos
            		if(categoriasConyuge != null && !categoriasConyuge.isEmpty()){
            			CategoriaAfiliacionSTDTO categoriasDependienteConyuge = new CategoriaAfiliacionSTDTO();
						CategoriaAfiliacionSTDTO categoriasIndependienteConyuge = new CategoriaAfiliacionSTDTO();
						CategoriaAfiliacionSTDTO categoriasPensionadoConyuge = new CategoriaAfiliacionSTDTO();
						List<CategoriaSTDTO> arregloCategoriasDependienteConyuge = new ArrayList<>();
						List<CategoriaSTDTO> arregloCategoriasIndependienteConyuge = new ArrayList<>();
						List<CategoriaSTDTO> arregloCategoriasPensionadoConyuge = new ArrayList<>();
						
            			for (Object[] cc : categoriasConyuge) {
							CategoriaSTDTO cat = new CategoriaSTDTO();
							cat.setFecha(cc[1] != null ? cc[1].toString() : null);
							cat.setCategoria(cc[2] != null ? CategoriaPersonaEnum.valueOf(cc[2].toString()) : null);
							
							if(cc[3] != null){
								if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(cc[3].toString()))){
									arregloCategoriasDependienteConyuge.add(cat);
									categoriasDependienteConyuge.setClasificacion(cc[0] != null ? cc[0].toString() : null);
								}
								else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(cc[3].toString()))){
									arregloCategoriasIndependienteConyuge.add(cat);
									categoriasIndependienteConyuge.setClasificacion(cc[0] != null ? cc[0].toString() : null);
								}
								else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(cc[3].toString()))){
									arregloCategoriasPensionadoConyuge.add(cat);
									categoriasPensionadoConyuge.setClasificacion(cc[0] != null ? cc[0].toString() : null);
								}
							}	
						}
            			//se ordenan las listas de categorias (de la más reciente a la más antigua)
            			Collections.reverse(arregloCategoriasDependienteConyuge);
            			Collections.reverse(arregloCategoriasIndependienteConyuge);
            			Collections.reverse(arregloCategoriasPensionadoConyuge);
            			
            			//se agregan los arreglos de categorias a cada tipo de afiliación
            			categoriasDependienteConyuge.setArregloCategorias(arregloCategoriasDependienteConyuge);
            			categoriasIndependienteConyuge.setArregloCategorias(arregloCategoriasIndependienteConyuge);
            			categoriasPensionadoConyuge.setArregloCategorias(arregloCategoriasPensionadoConyuge);
            			
            			//se agregan los datos de categorias de los diferentes tipos de afiliación del conyuge
            			arregloConyuge.setArregloDependiente(categoriasDependienteConyuge);
            			arregloConyuge.setArregloIndependiente(categoriasIndependienteConyuge);
            			arregloConyuge.setArregloPensionado(categoriasPensionadoConyuge);
            		}
            		categoriaAfiliado.setArregloConyuge(arregloConyuge);
        		}
        		if(datosBeneficiarios != null && !datosBeneficiarios.isEmpty()){
        			
        			List<grupoCategoriasSTDTO> arregloBeneficiarios = new ArrayList<>();
        			
        			grupoCategoriasSTDTO infoBeneficiario;
        			for (Object[] obj : datosBeneficiarios) {
        				//desde la linea de abajo
        				infoBeneficiario = new grupoCategoriasSTDTO();
						infoBeneficiario.setTipoId(obj[0] != null ? TipoIdentificacionEnum.valueOf(obj[0].toString()) : null);
						infoBeneficiario.setIdentificacion(obj[1] != null ? obj[1].toString() : null);
						infoBeneficiario.setPrimerNombre(obj[2] != null ? obj[2].toString() : null);
						infoBeneficiario.setSegundoNombre(obj[3] != null ? obj[3].toString() : null);
						infoBeneficiario.setPrimerApellido(obj[4] != null ? obj[4].toString() : null);
						infoBeneficiario.setSegundoApellido(obj[5] != null ? obj[5].toString() : null);
						infoBeneficiario.setEstadoAfiliacion(obj[6] != null ? EstadoAfiliadoEnum.valueOf(obj[6].toString()) : null);
						
						CategoriaAfiliacionSTDTO categoriasDependiente;
						CategoriaAfiliacionSTDTO categoriasIndependiente;
						CategoriaAfiliacionSTDTO categoriasPensionado;
						List<CategoriaSTDTO> arregloCategoriasDependiente;
						List<CategoriaSTDTO> arregloCategoriasIndependiente;
						List<CategoriaSTDTO> arregloCategoriasPensionado;
						
						if(obj[7] != null && obj[8] != null){
							CategoriaSTDTO categoriaDependiente = new CategoriaSTDTO(obj[8].toString(), 
									CategoriaPersonaEnum.valueOf(obj[7].toString()));
							arregloCategoriasDependiente = new ArrayList<>();
							arregloCategoriasDependiente.add(categoriaDependiente);
							categoriasDependiente = new CategoriaAfiliacionSTDTO(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name(), arregloCategoriasDependiente);
							infoBeneficiario.setArregloDependiente(categoriasDependiente);
						}
						if(obj[9] != null && obj[10] != null){
							CategoriaSTDTO categoriaIndependiente = new CategoriaSTDTO(obj[10].toString(), 
									CategoriaPersonaEnum.valueOf(obj[9].toString()));
							arregloCategoriasIndependiente = new ArrayList<>();
							arregloCategoriasIndependiente.add(categoriaIndependiente);
							categoriasIndependiente = new CategoriaAfiliacionSTDTO(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name(), arregloCategoriasIndependiente);
							infoBeneficiario.setArregloIndependiente(categoriasIndependiente);
						}
						if(obj[11] != null && obj[12] != null){
							CategoriaSTDTO categoriaPensionado = new CategoriaSTDTO(obj[12].toString(), 
									CategoriaPersonaEnum.valueOf(obj[11].toString()));
							arregloCategoriasPensionado = new ArrayList<>();
							arregloCategoriasPensionado.add(categoriaPensionado);
							categoriasPensionado = new CategoriaAfiliacionSTDTO(TipoAfiliadoEnum.PENSIONADO.name(), arregloCategoriasPensionado);
							infoBeneficiario.setArregloPensionado(categoriasPensionado);
						}
						//hasta la linea anterior
						arregloBeneficiarios.add(infoBeneficiario);
					}
        			categoriaAfiliado.setArregloBeneficiarios(arregloBeneficiarios);
        		}
        	}
        	categoriaPersona.setCategoriasAfiliadoPrincipal(categoriaAfiliado);
        }
        // si la persona es beneficiario activo de alguien
        if(personaConsulta[1] != null){
        	consultarCategoriaBeneficiario(tipoID, identificacion, categoriaPersona, fechaInicial, fechaFin);
        }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,categoriaPersona,entityManager,auditoriaIntegracionServicios);
    }




	private List<Object[]> consultarCategoriasAfiliado(TipoIdentificacionEnum tipoID, String identificacion, LocalDateTime fechaInicial, LocalDateTime fechaFin) {

		return (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CATEGORIAS_AFI_PPAL_SRV_TRA)
				.setParameter("tipoIdentificacion", tipoID.name())
				.setParameter("numeroIdentificacion", identificacion)
				.setParameter("fechaInicio", fechaInicial.atZone(ZoneId.systemDefault()).toEpochSecond()*1000L)
				.setParameter("fechaFin", fechaFin.atZone(ZoneId.systemDefault()).toEpochSecond()*1000L)
				.getResultList();
	}


	private void consultarCategoriaBeneficiario(TipoIdentificacionEnum tipoID, String identificacion,
			CategoriaPersonaOutDTO categoriaPersona, LocalDateTime fechaInicial, LocalDateTime fechaFin) {
		CategoriaBeneficiarioSTDTO categoriaBeneficiario = new CategoriaBeneficiarioSTDTO();
		
		//se consultan los afiliados de los cuales la persona es beneficiario
		List<Object[]> listaDatosAfiliadosBeneficiario = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_AFILIADOS_PPALES_BENEFICIARIO_SRV_TRA)
				.setParameter("tipoIdentificacion", tipoID.name())
				.setParameter("NumeroIdentificacion", identificacion)
				.getResultList();

		if(listaDatosAfiliadosBeneficiario != null && !listaDatosAfiliadosBeneficiario.isEmpty()){
			for (Object[] ldab : listaDatosAfiliadosBeneficiario) {
				grupoCategoriasSTDTO infoCategoriaAfiliado = new grupoCategoriasSTDTO();
				
				infoCategoriaAfiliado.setTipoId(ldab[0] != null ? TipoIdentificacionEnum.valueOf(ldab[0].toString()) : null);
				infoCategoriaAfiliado.setIdentificacion(ldab[1] != null ? ldab[1].toString() : null);
				infoCategoriaAfiliado.setPrimerNombre(ldab[2] != null ? ldab[2].toString() : null);
				infoCategoriaAfiliado.setSegundoNombre(ldab[3] != null ? ldab[3].toString() : null);
				infoCategoriaAfiliado.setPrimerApellido(ldab[4] != null ? ldab[4].toString() : null);
				infoCategoriaAfiliado.setSegundoApellido(ldab[5] != null ? ldab[5].toString() : null);
				infoCategoriaAfiliado.setEstadoAfiliacion(ldab[6] != null ? EstadoAfiliadoEnum.valueOf(ldab[6].toString()) : null);
				
				//se consultan las categorias para todos los tipos de afiliación del conyuge como afiliado
				List<Object[]> categoriasAfiPpal = consultarCategoriasAfiliado(infoCategoriaAfiliado.getTipoId(), infoCategoriaAfiliado.getIdentificacion(), fechaInicial, fechaFin);
				
				//se mapean las categorias si la consulta anterior obtuvo datos
				if(categoriasAfiPpal != null && !categoriasAfiPpal.isEmpty()){
					CategoriaAfiliacionSTDTO categoriasDependienteAfiPpal = new CategoriaAfiliacionSTDTO();
					CategoriaAfiliacionSTDTO categoriasIndependienteAfiPpal = new CategoriaAfiliacionSTDTO();
					CategoriaAfiliacionSTDTO categoriasPensionadoAfiPpal = new CategoriaAfiliacionSTDTO();
					List<CategoriaSTDTO> arregloCategoriasDependienteAfiPpal = new ArrayList<>();
					List<CategoriaSTDTO> arregloCategoriasIndependienteAfiPpal = new ArrayList<>();
					List<CategoriaSTDTO> arregloCategoriasPensionadoAfiPpal = new ArrayList<>();
					
					for (Object[] cap : categoriasAfiPpal) {
						CategoriaSTDTO cat = new CategoriaSTDTO();
						cat.setFecha(cap[1] != null ? cap[1].toString() : null);
						cat.setCategoria(cap[2] != null ? CategoriaPersonaEnum.valueOf(cap[2].toString()) : null);
						
						if(cap[3] != null){
							if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(cap[3].toString()))){
								arregloCategoriasDependienteAfiPpal.add(cat);
								categoriasDependienteAfiPpal.setClasificacion(cap[0] != null ? cap[0].toString() : null);
							}
							else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(cap[3].toString()))){
								arregloCategoriasIndependienteAfiPpal.add(cat);
								categoriasIndependienteAfiPpal.setClasificacion(cap[0] != null ? cap[0].toString() : null);
							}
							else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(cap[3].toString()))){
								arregloCategoriasPensionadoAfiPpal.add(cat);
								categoriasPensionadoAfiPpal.setClasificacion(cap[0] != null ? cap[0].toString() : null);
							}
						}	
					}
					//se ordenan las listas de categorias (de la más reciente a la más antigua)
					Collections.reverse(arregloCategoriasDependienteAfiPpal);
					Collections.reverse(arregloCategoriasIndependienteAfiPpal);
					Collections.reverse(arregloCategoriasPensionadoAfiPpal);
					
					//se agregan los arreglos de categorias a cada tipo de afiliación
					categoriasDependienteAfiPpal.setArregloCategorias(arregloCategoriasDependienteAfiPpal);
					categoriasIndependienteAfiPpal.setArregloCategorias(arregloCategoriasIndependienteAfiPpal);
					categoriasPensionadoAfiPpal.setArregloCategorias(arregloCategoriasPensionadoAfiPpal);
					
					//se agregan los datos de categorias de los diferentes tipos de afiliación del conyuge
					infoCategoriaAfiliado.setArregloDependiente(categoriasDependienteAfiPpal);
					infoCategoriaAfiliado.setArregloIndependiente(categoriasIndependienteAfiPpal);
					infoCategoriaAfiliado.setArregloPensionado(categoriasPensionadoAfiPpal);
				}
				
				if(categoriaBeneficiario.getArregloPrincipal() == null){
					categoriaBeneficiario.setArregloPrincipal(infoCategoriaAfiliado);
				}
				else{
					categoriaBeneficiario.setArregloSecundario(infoCategoriaAfiliado);
				}
			}
		}
		categoriaPersona.setCategoriasBeneficiario(categoriaBeneficiario);
	}

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerGrupoFamiliar(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerGrupoFamiliar(TipoIdentificacionEnum tipoID, String identificacionAfiliado, String identificacionBeneficiario, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerGrupoFamiliar(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        parametrosMetodo.put("identificacionBeneficiario", identificacionBeneficiario);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

        List<GrupoFamiliarOutDTO> listadoGrupoFamiliar = new ArrayList<>();

        try {
            if(identificacionAfiliado != null && identificacionBeneficiario == null){
                listadoGrupoFamiliar.addAll(obtenerGrupoFamiliarEspecifico(tipoID, identificacionAfiliado, null));

            }
            else if(identificacionBeneficiario != null && identificacionAfiliado == null){
                List<Object[]> afiliadosPrincipalesBeneficiario = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_PRINCIPALES_GRUPO_BENEFICIARIO_SRV_TRA)
                        .setParameter("tipoIdentificacion", tipoID.name())
                        .setParameter("numeroIdentificacion", identificacionBeneficiario)
                        .getResultList();
                
                if(afiliadosPrincipalesBeneficiario != null && !afiliadosPrincipalesBeneficiario.isEmpty()){
                    for (Object[] datoAfiliado : afiliadosPrincipalesBeneficiario) {
                        listadoGrupoFamiliar.addAll(obtenerGrupoFamiliarEspecifico(TipoIdentificacionEnum.valueOf(String.valueOf(datoAfiliado[0])), datoAfiliado[1].toString(), Long.valueOf(datoAfiliado[2].toString())));
                    }
                }
                
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            
         } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,listadoGrupoFamiliar,entityManager,auditoriaIntegracionServicios);
        }
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,listadoGrupoFamiliar,entityManager,auditoriaIntegracionServicios);
   }

    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idGrupoFamiliar
     * @return
     */
    private List<GrupoFamiliarOutDTO> obtenerGrupoFamiliarEspecifico(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long idGrupoFamiliar) {
        List<GrupoFamiliarOutDTO> listadoGrupoFamiliar;
        listadoGrupoFamiliar = (List<GrupoFamiliarOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_AFILIADO_PRINCIPAL_SRV_TRA)
                .setParameter("tipoIdAfiliado", tipoIdentificacion.name())
                .setParameter("numeroIdAfiliado", numeroIdentificacion)
                .getResultList();
        
        if(listadoGrupoFamiliar != null && !listadoGrupoFamiliar.isEmpty()){
        
        for (GrupoFamiliarOutDTO grupoFamiliar : listadoGrupoFamiliar) {
            
	        	List<Object[]> infoAfiliacionAfiPrincipal = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIACION_AFI_PRINCIPAL_ST)
	                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
	                    .setParameter("numeroIdentificacion", numeroIdentificacion)
	                    .getResultList();
	        	
	        	if (infoAfiliacionAfiPrincipal != null && !infoAfiliacionAfiPrincipal.isEmpty()){
					grupoFamiliar.setAfiliadoDependiente(infoAfiliacionAfiPrincipal.get(0)[0].toString().equals("1") ? true : false);
					grupoFamiliar.setAfiliadoIndependiente(infoAfiliacionAfiPrincipal.get(0)[1].toString().equals("1") ? true : false);
					grupoFamiliar.setAfiliadoPensionado(infoAfiliacionAfiPrincipal.get(0)[2].toString().equals("1") ? true : false);
					
					List<CategoriaPersonaEnum> categorias = new ArrayList<>();
					
					// se recorren las ultimas tres pocisiones del arreglo, las cuales contienen las categorias actuales para cada tipo de afiliación.
					for (int i = 3; i <=5; i++) {
						if(infoAfiliacionAfiPrincipal.get(0)[i] != null){
							categorias.add(CategoriaPersonaEnum.valueOf(infoAfiliacionAfiPrincipal.get(0)[i].toString()));
						}
					}
					grupoFamiliar.setCategoria(categorias);
					
					
				}
	        	else{
					grupoFamiliar.setAfiliadoDependiente(false);
					grupoFamiliar.setAfiliadoIndependiente(false);
					grupoFamiliar.setAfiliadoPensionado(false);
				}

	        	
	        	
                List<GrupoFamiliarSTDTO> gruposFamiliares = new ArrayList<>();
                
                @SuppressWarnings("unchecked")
                List<Object[]> grupoFamiliarSTOut = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_GENERAL_GRUPOS_FAMILIARES_ST)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                        .setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();
                
                if(grupoFamiliarSTOut != null && !grupoFamiliarSTOut.isEmpty()){
                    if(idGrupoFamiliar != null){
                        for (Object[] dataGrupo : grupoFamiliarSTOut) {
                            if(dataGrupo[0] != null &&  Long.valueOf(dataGrupo[0].toString()).equals(idGrupoFamiliar)){
                                GrupoFamiliarSTDTO outGrfDTO = new GrupoFamiliarSTDTO();
                                
                                outGrfDTO.setIdGrupoFamiliar(dataGrupo[0] != null ? Long.valueOf(dataGrupo[0].toString()) : null);
                                outGrfDTO.setDepartamento(dataGrupo[1] != null ? dataGrupo[1].toString() : null);
                                outGrfDTO.setMunicipio(dataGrupo[2] != null ? dataGrupo[2].toString() : null);
                                outGrfDTO.setDireccionResidencia(dataGrupo[3] != null ? dataGrupo[3].toString() : null);
                                outGrfDTO.setTipoID(dataGrupo[4] != null ? TipoIdentificacionEnum.valueOf(dataGrupo[4].toString()) : null);
                                outGrfDTO.setIdentificacion(dataGrupo[5] != null ? dataGrupo[5].toString() : null);
                                outGrfDTO.setNombreCompleto(dataGrupo[6] != null ? dataGrupo[6].toString() : null);
                                outGrfDTO.setSitioDePago(dataGrupo[7] != null ? dataGrupo[7].toString() : null);
                                
                                if(outGrfDTO.getIdGrupoFamiliar() != null){
                                    List<BeneficiarioSTDTO> beneficiarios = (List<BeneficiarioSTDTO>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_GRF_ST)
                                            .setParameter("idGrupoFamiliar", outGrfDTO.getIdGrupoFamiliar())
                                            .getResultList();
                                    
                                    outGrfDTO.setBeneficiarios(beneficiarios);
                                }
                                gruposFamiliares.add(outGrfDTO);
                            }
                        }
                    }else{
                        for (Object[] dataGrupo : grupoFamiliarSTOut) {
                            
                            GrupoFamiliarSTDTO outGrfDTO = new GrupoFamiliarSTDTO();
                            
                            outGrfDTO.setIdGrupoFamiliar(dataGrupo[0] != null ? Long.valueOf(dataGrupo[0].toString()) : null);
                            outGrfDTO.setDepartamento(dataGrupo[1] != null ? dataGrupo[1].toString() : null);
                            outGrfDTO.setMunicipio(dataGrupo[2] != null ? dataGrupo[2].toString() : null);
                            outGrfDTO.setDireccionResidencia(dataGrupo[3] != null ? dataGrupo[3].toString() : null);
                            outGrfDTO.setTipoID(dataGrupo[4] != null ? TipoIdentificacionEnum.valueOf(dataGrupo[4].toString()) : null);
                            outGrfDTO.setIdentificacion(dataGrupo[5] != null ? dataGrupo[5].toString() : null);
                            outGrfDTO.setNombreCompleto(dataGrupo[6] != null ? dataGrupo[6].toString() : null);
                            outGrfDTO.setSitioDePago(dataGrupo[7] != null ? dataGrupo[7].toString() : null);
                            
                            if(outGrfDTO.getIdGrupoFamiliar() != null){
                                List<BeneficiarioSTDTO> beneficiarios = (List<BeneficiarioSTDTO>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_GRF_ST)
                                        .setParameter("idGrupoFamiliar", outGrfDTO.getIdGrupoFamiliar())
                                        .getResultList();
                                
                                outGrfDTO.setBeneficiarios(beneficiarios);
                            }
                            gruposFamiliares.add(outGrfDTO);
                        }
                    }
                }
                
                //TODO info relacionada a categorias
                
                grupoFamiliar.setArregloGrupoFamiliar(gruposFamiliares);
            }
        }
        return listadoGrupoFamiliar;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoBasicaEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public Response obtenerInfoBasicaEmpleador(TipoIdentificacionEnum tipoID, String identificacion, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoBasicaEmpleador(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacion", identificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

        List<InfoBasicaEmpleadorOutDTO> listadoGrupoFamiliar = new ArrayList<>();
        try {
            List<Object[]> infoEmpleadores = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_BASICA_EMPLEADOR_SRV_TRA)
                    .setParameter("tipoIdEmpleador", tipoID.name())
                    .setParameter("numeroIdEmpleador", identificacion)
                    .getResultList();
            
            if(infoEmpleadores != null && !infoEmpleadores.isEmpty()){
                for (Object[] obj : infoEmpleadores) {
                    InfoBasicaEmpleadorOutDTO outDTO = new InfoBasicaEmpleadorOutDTO();
                    outDTO.setTipoID(obj[0] != null ? TipoIdentificacionEnum.valueOf(obj[0].toString()) : null);
                    outDTO.setIdentificacion(obj[1] != null ? obj[1].toString() : null);
                    outDTO.setDigitoVerificacion(obj[2] != null ? Short.valueOf(obj[2].toString()) : null);
                    outDTO.setRazonSocial(obj[3] != null ? obj[3].toString() : null);
                    outDTO.setNombreComercial(obj[4] != null ? obj[4].toString() : null);
                    outDTO.setDepartamentoCodigo(obj[5] != null ? obj[5].toString() : null);
                    outDTO.setDepartamento(obj[6] != null ? obj[6].toString() : null);
                    outDTO.setMunicipioCodigo(obj[7] != null ? obj[7].toString() : null);
                    outDTO.setMunicipio(obj[8] != null ? obj[8].toString() : null);
                    outDTO.setDireccionPrincipal(obj[9] != null ? obj[9].toString() : null);
                    outDTO.setTelefonoFijo(obj[10] != null ? obj[10].toString() : null);
                    outDTO.setCelular(obj[11] != null ? obj[11].toString() : null);
                    outDTO.setEstadoAfiliacion(obj[12] != null ? obj[12].toString() : EstadoAfiliadoEnum.SIN_ESTADO.name());
                    outDTO.setEstadoCartera(obj[13] != null ? EstadoCarteraEnum.valueOf(obj[13].toString()) : EstadoCarteraEnum.AL_DIA);
                    outDTO.setNaturalezaJuridica(obj[14] != null ? NaturalezaJuridicaEnum.valueOf(obj[14].toString()) : null);
                    listadoGrupoFamiliar.add(outDTO);
                }
            }
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

         } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,listadoGrupoFamiliar,entityManager,auditoriaIntegracionServicios);
        }
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,listadoGrupoFamiliar,entityManager,auditoriaIntegracionServicios);
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoTotalEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerInfoTotalEmpleador(TipoIdentificacionEnum tipoID, String identificacionEmpleador, String identificacionAfiliado, String codigoSucursal, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoTotalEmpleador(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacionEmpleador", identificacionEmpleador);
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        parametrosMetodo.put("codigoSucursal", codigoSucursal);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

        List<InfoTotalEmpleadorOutDTO> salida = new ArrayList<>();
         try {
        InfoEmpleadorInDTO inDTO = new InfoEmpleadorInDTO(tipoID, identificacionEmpleador, identificacionAfiliado, codigoSucursal);
        if(inDTO.getIdentificacionEmpleador() != null && inDTO.getCodigoSucursal() == null && inDTO.getIdentificacionAfiliado() == null){
            obtenerInfoTotalEmpleadorEspecifico(inDTO, salida);
        }
        else if(inDTO.getIdentificacionEmpleador() != null && inDTO.getCodigoSucursal() != null && inDTO.getIdentificacionAfiliado() == null){
            obtenerInfoTotalEmpleadorEspecifico(inDTO, salida);
            for (int i = 0; i < salida.size(); i++) {
                if(salida.get(i).getArregloSucursales()==null || salida.get(i).getArregloSucursales().isEmpty()){
                    salida.remove(i);
                }
            }
        }
        else if(inDTO.getIdentificacionAfiliado() != null && inDTO.getIdentificacionEmpleador() == null){
            
            List<Object[]> empleadoresAfiliado = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_AFILIADO_SRV_TRA)
                    .setParameter("tipoIdentificacion", inDTO.getTipoID().name())
                    .setParameter("numeroIdentificacion", inDTO.getIdentificacionAfiliado())
                    .getResultList();
            if(empleadoresAfiliado != null && !empleadoresAfiliado.isEmpty()){
                for (Object[] empleadorAfi : empleadoresAfiliado) {
                    InfoEmpleadorInDTO entrada = new InfoEmpleadorInDTO(TipoIdentificacionEnum.valueOf(empleadorAfi[0].toString()), empleadorAfi[1].toString(), null, null);
                    
                    obtenerInfoTotalEmpleadorEspecifico(entrada, salida);
                }
            }
        }
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,salida,entityManager,auditoriaIntegracionServicios);
        }
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,salida,entityManager,auditoriaIntegracionServicios);
    }

    /**
     * @param inDTO
     * @param salida
     */
    private void obtenerInfoTotalEmpleadorEspecifico(InfoEmpleadorInDTO inDTO, List<InfoTotalEmpleadorOutDTO> salida) {
        List<InfoTotalEmpleadorOutDTO> outDTOs = new ArrayList<>();
        
        List<Object[]> infoTotalEmpleador = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_TOTAL_EMPLEADOR_SRV_TRA)
                .setParameter("tipoIdEmpleador", inDTO.getTipoID().name())
                .setParameter("numeroIdEmpleador", inDTO.getIdentificacionEmpleador())
                .getResultList();
        
        if(infoTotalEmpleador != null && !infoTotalEmpleador.isEmpty()){
            for (Object[] ite : infoTotalEmpleador) {
                InfoTotalEmpleadorOutDTO iteDTO = new InfoTotalEmpleadorOutDTO();
                iteDTO.setIdEmpleador(ite[0] != null ? Long.valueOf(ite[0].toString()) : null);
                iteDTO.setIdEmpresa(ite[1] != null ? Long.valueOf(ite[1].toString()) : null);
                iteDTO.setTipoID(ite[2] != null ? TipoIdentificacionEnum.valueOf(ite[2].toString()) : null);
                iteDTO.setIdentificacion(ite[3] != null ? ite[3].toString() : null);
                iteDTO.setRazonSocial(ite[4] != null ? ite[4].toString() : null);
                iteDTO.setNombreComercial(ite[5] != null ? ite[5].toString() : null);
                iteDTO.setNaturalezaJuridica(ite[6] != null ? NaturalezaJuridicaEnum.valueOf(ite[6].toString()) : null);
                iteDTO.setFechaConstitucion(ite[7] != null ? ite[7].toString() : null);
                iteDTO.setDepartamentoCodigo(ite[8] != null ? ite[8].toString() : null);
                iteDTO.setDepartamento(ite[9] != null ? ite[9].toString() : null);
                iteDTO.setMunicipioCodigo(ite[10] != null ? ite[10].toString() : null);
                iteDTO.setMunicipio(ite[11] != null ? ite[11].toString() : null);
                iteDTO.setDireccionPrincipal(ite[12] != null ? ite[12].toString() : null);
                iteDTO.setTelefonoFijo(ite[13] != null ? ite[13].toString() : null);
                iteDTO.setCelular(ite[14] != null ? ite[14].toString() : null);
                iteDTO.setCodigoPostal(ite[15] != null ? ite[15].toString() : null);
                iteDTO.setEstadoAfiliacion(ite[16] != null ? EstadoEmpleadorEnum.valueOf(ite[16].toString()) : null);
                iteDTO.setEstadoCartera(ite[17] != null ? EstadoCarteraEnum.valueOf(ite[17].toString()) : EstadoCarteraEnum.AL_DIA);
                iteDTO.setActividadEconomica(ite[18] != null ? ite[18].toString() : null);
                iteDTO.setDescripcionActividadEconomica(ite[19] != null ? ite[19].toString() : null);
                iteDTO.setCorreoElectronico(ite[20] != null ? ite[20].toString() : null);
                iteDTO.setFechaAfiliacion(ite[21] != null ? ite[21].toString() : null);
                iteDTO.setFechaDesafiliacion(ite[22] != null ? ite[22].toString() : null);
                iteDTO.setFechaEntregaExpulsion(ite[23] != null ? ite[23].toString() : null);
                iteDTO.setNumeroTotalTrabajadores(ite[24] != null ? Integer.valueOf(ite[24].toString()) : null);
                iteDTO.setUltimoPeriodoAportes(ite[25] != null ? ite[25].toString() : null);
                iteDTO.setDiaHabilVencimientoPagoAportes(ite[26] != null ? Short.valueOf(ite[26].toString()) : null);
                iteDTO.setDigitoVerificacion(ite[27] != null ? Short.valueOf(ite[27].toString()) : null);
                
                outDTOs.add(iteDTO);
            }
        }
        if(outDTOs != null && !outDTOs.isEmpty()){
            for (InfoTotalEmpleadorOutDTO infoTotalEmpleadorOutDTO : outDTOs) {
                
                List<Object[]> representantes = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_REPRESENTANTE_LEGAL_EMPLEADOR_SRV_TRA)
                        .setParameter("idEmpresa", infoTotalEmpleadorOutDTO.getIdEmpresa())
                        .getResultList();
                
                InfoTotalEmpleadorOutDTO outDTO = infoTotalEmpleadorOutDTO;

                if(representantes != null && !representantes.isEmpty()){
                    RepresentanteLegalOutDTO repLegal = new RepresentanteLegalOutDTO();
                    repLegal.setTipoID(representantes.get(0)[0] != null ? TipoIdentificacionEnum.valueOf(representantes.get(0)[0].toString()) : null);
                    repLegal.setIdentificacion(representantes.get(0)[1] != null ? representantes.get(0)[1].toString() : null);
                    repLegal.setDigitoVerificacion(representantes.get(0)[2] != null ? Short.valueOf(representantes.get(0)[2].toString()) : null);
                    repLegal.setNombreCompleto(representantes.get(0)[3] != null ? representantes.get(0)[3].toString() : null);
                    repLegal.setDepartamentoCodigo(representantes.get(0)[4] != null ? representantes.get(0)[4].toString() : null);
                    repLegal.setDepartamento(representantes.get(0)[5] != null ? representantes.get(0)[5].toString() : null);
                    repLegal.setMunicipioCodigo(representantes.get(0)[6] != null ? representantes.get(0)[6].toString() : null);
                    repLegal.setMunicipio(representantes.get(0)[7] != null ? representantes.get(0)[7].toString() : null);
                    repLegal.setDireccionPrincipal(representantes.get(0)[8] != null ? representantes.get(0)[8].toString() : null);
                    repLegal.setTelefonoFijo(representantes.get(0)[9] != null ? representantes.get(0)[9].toString() : null);
                    repLegal.setCelular(representantes.get(0)[10] != null ? representantes.get(0)[10].toString() : null);
                    repLegal.setCorreoElectronico(representantes.get(0)[11] != null ? representantes.get(0)[11].toString() : null);
                    outDTO.setRepresentanteLegal(repLegal);
                }
                List<SucursalEmpresaOutDTO> arregloSucursales = new ArrayList<>();
                
                List<Object[]> sucursales = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSALES_EMPRESA_SRV_TRA)
                        .setParameter("idEmpleador", infoTotalEmpleadorOutDTO.getIdEmpresa())
                        .getResultList();
                
                
                
                if (sucursales != null && !sucursales.isEmpty()) {
                    if(inDTO.getCodigoSucursal() != null){
                        for (Object[] obj : sucursales) {
                            if(obj[0] != null && inDTO.getCodigoSucursal().equals(obj[0].toString())){
                                SucursalEmpresaOutDTO sucursal = new SucursalEmpresaOutDTO();
                                sucursal.setCodigoSucursal(obj[0] != null ? obj[0].toString() : null);
                                sucursal.setEstadoSucursal(obj[1] != null ? EstadoActivoInactivoEnum.valueOf(obj[1].toString()) : null);
                                sucursal.setNombreSucursal(obj[2] != null ? obj[2].toString() : null);
                                sucursal.setDireccionPrincipalSucursal(obj[3] != null ? obj[3].toString() : null);
                                sucursal.setCodigoDepartamentoSucursal(obj[4] != null ? obj[4].toString() : null);
                                sucursal.setNombreDepartamentoSucursal(obj[5] != null ? obj[5].toString() : null);
                                sucursal.setCodigoMunicipioSucursal(obj[6] != null ? obj[6].toString() : null);
                                sucursal.setNombreMunicipioSucursal(obj[7] != null ? obj[7].toString() : null);
                                sucursal.setEmailSucursal(obj[8] != null ? obj[8].toString() : null);
                                sucursal.setCodigoActividadSucursal(obj[9] != null ? obj[9].toString() : null);
                                sucursal.setNombreActividadSucursal(obj[10] != null ? obj[10].toString() : null);
                                sucursal.setTelefonoPrincipalSucursal(obj[11] != null ? obj[11].toString() : null);
                                sucursal.setUltimoPeriodoAportesSucursal(obj[12] != null ? obj[12].toString() : null);
                                
                                arregloSucursales.add(sucursal);
                            }
                        }
                    }
                    else{
                        for (Object[] obj : sucursales) {
                            SucursalEmpresaOutDTO sucursal = new SucursalEmpresaOutDTO();
                            sucursal.setCodigoSucursal(obj[0] != null ? obj[0].toString() : null);
                            sucursal.setEstadoSucursal(obj[1] != null ? EstadoActivoInactivoEnum.valueOf(obj[1].toString()) : null);
                            sucursal.setNombreSucursal(obj[2] != null ? obj[2].toString() : null);
                            sucursal.setDireccionPrincipalSucursal(obj[3] != null ? obj[3].toString() : null);
                            sucursal.setCodigoDepartamentoSucursal(obj[4] != null ? obj[4].toString() : null);
                            sucursal.setNombreDepartamentoSucursal(obj[5] != null ? obj[5].toString() : null);
                            sucursal.setCodigoMunicipioSucursal(obj[6] != null ? obj[6].toString() : null);
                            sucursal.setNombreMunicipioSucursal(obj[7] != null ? obj[7].toString() : null);
                            sucursal.setEmailSucursal(obj[8] != null ? obj[8].toString() : null);
                            sucursal.setCodigoActividadSucursal(obj[9] != null ? obj[9].toString() : null);
                            sucursal.setNombreActividadSucursal(obj[10] != null ? obj[10].toString() : null);
                            sucursal.setTelefonoPrincipalSucursal(obj[11] != null ? obj[11].toString() : null);
                            sucursal.setUltimoPeriodoAportesSucursal(obj[12] != null ? obj[12].toString() : null);
                            		
                            arregloSucursales.add(sucursal);
                        }
                    }
                }
                outDTO.setArregloSucursales(arregloSucursales);
                //se setea en null porque no es un dato requerido por el contrato de servicio y solo se obtuvo
                //para hacer la consulta del representante legal y las sucursales
                outDTO.setIdEmpleador(null);
                outDTO.setIdEmpresa(null);
                salida.add(outDTO);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerContactosEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerContactosEmpleador(TipoIdentificacionEnum tipoID, String identificacion, String codigoSucursal, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerContactosEmpleador(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacion", identificacion);
        parametrosMetodo.put("codigoSucursal",codigoSucursal);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        List<ContactosEmpleadorOutDTO> outDTOs = null; 
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        try {
             outDTOs = (List<ContactosEmpleadorOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_EMPLEADOR_PARA_CONTACTOS_SRV_TRA)
                    .setParameter("tipoIdEmpleador", tipoID.name())
                    .setParameter("numeroIdEmpleador", identificacion)
                    .getResultList();

            if(outDTOs != null && !outDTOs.isEmpty()){
                
                for (ContactosEmpleadorOutDTO outDTO : outDTOs) {
                    
                    if(codigoSucursal != null){
                        List<Object[]> sue = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSALES_EMPLEADOR_POR_CODIGO_SRV_TRA)
                                .setParameter("idEmpleador", outDTO.getIdEmpleador())
                                .setParameter("codigoSucursal", codigoSucursal)
                                .getResultList();
                        
                        setDatosContactoEspecifico(outDTO, sue);
                        outDTO.setIdEmpleador(null);
                    }
                    else{
                    	List<Object[]> sue = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSALES_EMPLEADOR_SRV_TRA)
                                .setParameter("idEmpleador", outDTO.getIdEmpleador())
                                .getResultList();
                    	
                    	setDatosContactoEspecifico(outDTO, sue);
                        outDTO.setIdEmpleador(null);
                    }
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
              
        }  catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,outDTOs,entityManager,auditoriaIntegracionServicios);
         }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,outDTOs,entityManager,auditoriaIntegracionServicios);
    }


    /**
     * @param outDTO
     * @param sue
     */
    private void setDatosContactoEspecifico(ContactosEmpleadorOutDTO outDTO, List<Object[]> sue) {
        List<List<DatosContactoEmpleadorOutDTO>> salida = new ArrayList<>(); 
        for (Object[] sucursal : sue) {
            List<Object[]> datosCont = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_DATOS_CONTACTO_EMPLEADOR_SRV_TRA)
                    .setParameter("idSucursal", sucursal[0].toString())
                    .getResultList();
            salida.add(mapearDatosContacto(datosCont));
            
        }
        outDTO.setDatosDeContacto(salida);
    }
    
    /**
     * Método que inicaliza la lista de contactos
     */
    private List<DatosContactoEmpleadorOutDTO> mapearDatosContacto(List<Object[]> datosCont) {
    	List<DatosContactoEmpleadorOutDTO> datosContacto = new ArrayList<>();
        for (Object[] data : datosCont) {
            DatosContactoEmpleadorOutDTO contacto = new DatosContactoEmpleadorOutDTO();
            
            contacto.setTipoContacto(data[0] != null ? TipoRolContactoEnum.valueOf(data[0].toString()) : null);
            contacto.setTipoID(data[1] != null ? TipoIdentificacionEnum.valueOf(data[1].toString()) : null);
            contacto.setIdentificacion(data[2] != null ? data[2].toString() : null);
            contacto.setNombreCompleto(data[3] != null ? data[3].toString() : null);
            contacto.setDepartamento(data[4] != null ? data[4].toString() : null);
            contacto.setMunicipio(data[5] != null ? data[5].toString() : null);
            contacto.setDireccionPrincipal(data[6] != null ? data[6].toString() : null);
            contacto.setTelefonoFijo(data[7] != null ? data[7].toString() : null);
            contacto.setCelular(data[8] != null ? data[8].toString() : null);
            contacto.setCorreoElectronico(data[9] != null ? data[9].toString() : null);
            contacto.setCodigoSucursal(data[10] != null ? data[10].toString() : null);
            contacto.setNombreSucursal(data[11] != null ? data[11].toString() : null);
            datosContacto.add(contacto);
        }
        return datosContacto;
    }
    

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */


    @Override
    public Response obtenerInfoAfiliado(
            TipoIdentificacionEnum tipoID,
            String identificacion,
            String codigoCaja,
            String tipoAfiliado,
            HttpServletRequest requestContext,
            UserDTO userDTO) {

        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoAfiliado(TipoIdentificacionEnum, String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + 
            " con parámetros de entrada: " + tipoID + ", " + identificacion + ", " + codigoCaja + ", " + tipoAfiliado);

        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<>();
        parametrosMetodo.put("tipoID", tipoID != null ? tipoID.name() : null);
        parametrosMetodo.put("identificacion", identificacion);
        parametrosMetodo.put("codigoCaja", codigoCaja);
        parametrosMetodo.put("tipoAfiliado", tipoAfiliado);

        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = 
            AuditoriaIntegracionServicios.integracionServicios(
                requestContext.getRemoteAddr(), 
                firmaServicio, 
                parametrosIn, 
                userDTO.getEmail());

        List<InfoAfiliadoOutDTO> infoAfiliadoOutDTOs = new ArrayList<>();

        try {
            if (tipoID != null && tipoAfiliado != null && codigoCaja != null && identificacion != null) {
                
                TipoAfiliadoEnum tipoAfiliadoEnum;
                switch (tipoAfiliado.toUpperCase()) {
                    case "DEPENDIENTE":
                        tipoAfiliadoEnum = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
                        break;
                    case "INDEPENDIENTE":
                        tipoAfiliadoEnum = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
                        break;
                    case "PENSIONADO":
                        tipoAfiliadoEnum = TipoAfiliadoEnum.PENSIONADO;
                        break;
                    default:
                        logger.error("Valor de tipoAfiliado inválido: " + tipoAfiliado);
                        return Response.status(Response.Status.BAD_REQUEST)
                                    .entity("Tipo de afiliado inválido: " + tipoAfiliado)
                                    .build();
                }

                infoAfiliadoOutDTOs = (List<InfoAfiliadoOutDTO>) entityManager
                        .createNamedQuery(NamedQueriesConstants.OBTENER_INFO_AFILIADO_CSF_SRV_TRA)
                        .setParameter("tipoIdentificacion", tipoID.name())
                        .setParameter("numeroIdentificacion", identificacion)
                        .setParameter("tipoAfiliado", tipoAfiliadoEnum.name())
                        .getResultList();
            }
        } catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(
                    start, e, infoAfiliadoOutDTOs, entityManager, auditoriaIntegracionServicios);
        }

        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(
                start, null, infoAfiliadoOutDTOs, entityManager, auditoriaIntegracionServicios);
    }


    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoTotalBeneficiario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    public Response obtenerInfoTotalBeneficiario(TipoIdentificacionEnum tipoID, String identificacionBeneficiario, String identificacionAfiliado, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.obtenerInfoTotalBeneficiario(TipoIdentificacionEnum, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("obtenerInfoTotalBeneficiario 0 B:" + identificacionBeneficiario); 
        logger.info("obtenerInfoTotalBeneficiario 0 A:" + identificacionAfiliado); 
        logger.info("obtenerInfoTotalBeneficiario 0 TIPO:" +  tipoID.name());

        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacionBeneficiario", identificacionBeneficiario);
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        List<InfoTotalBeneficiarioOutDTO> salida = new ArrayList<>();
        Gson gson = new Gson();
        try {
            if (identificacionBeneficiario != null && identificacionAfiliado == null) {
                obtenerInfoTotalBeneficiarioEspecifico(tipoID, identificacionBeneficiario, salida, null, null);
            } else if (identificacionAfiliado != null && identificacionBeneficiario == null) {               
                TypedQuery<Object[]> typedQuery = (TypedQuery<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_AFILIADO_PRINCIPAL_SRV_TRA)
                        .setParameter("tipoIdentificacion", tipoID.name())
                        .setParameter("numeroIdentificacion", identificacionAfiliado)
                        .setParameter("fechaActual", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                
                List<Object[]> datosAfiliado = typedQuery.getResultList();
                if (datosAfiliado != null && !datosAfiliado.isEmpty()) {
                    for (Object[] beneficiario : datosAfiliado) {
                        InfoTotalBeneficiarioOutDTO[] infoTotalBeneficiarioOutDTO = gson.fromJson(beneficiario[2].toString(), InfoTotalBeneficiarioOutDTO[].class);
                        for (InfoTotalBeneficiarioOutDTO beneficiarioDTO : infoTotalBeneficiarioOutDTO) {
                            if (beneficiarioDTO.getEstadoCivil() == null) {
                                beneficiarioDTO.setEstadoCivil("");
                            }
                            if(beneficiarioDTO.getTipoBeneficiario() == null){
                                beneficiarioDTO.setTipoBeneficiario((TipoBeneficiarioEnum) beneficiarioDTO.getClasificacion().getSujetoTramite() );
                            }
                            logger.info(infoTotalBeneficiarioOutDTO.toString()); 
                            salida.add(beneficiarioDTO);
                        }
                    }
                }
            }
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        } catch (Exception e) {
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, salida, entityManager, auditoriaIntegracionServicios);
        }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, salida, entityManager, auditoriaIntegracionServicios);
    }
    /**
     * @param inDTO
     * @param salida
     */
    private void obtenerInfoTotalBeneficiarioEspecifico(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<InfoTotalBeneficiarioOutDTO> salida, TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) throws IOException {
        Gson gson = new Gson();
    	        
        TypedQuery<Object[]> typedQuery = (TypedQuery<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_TOTAL_BENEFICIARIO_SRV_TRA)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("fechaActual", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<Object[]> objetos = typedQuery.getResultList();
        
        if(objetos != null && !objetos.isEmpty()){
            for (Object[] obj : objetos) {
            	if(obj[30] != null && (numeroIdAfiliado == null || obj[30].toString().equals(numeroIdAfiliado))){
            	    InfoTotalBeneficiarioOutDTO outDTO = new InfoTotalBeneficiarioOutDTO();
                    outDTO.setTipoBeneficiario(obj[0] != null ? TipoBeneficiarioEnum.valueOf(ClasificacionEnum.valueOf(obj[0].toString()).getSujetoTramite().getName()) : null);
                    outDTO.setClasificacion(obj[0] != null ? ClasificacionEnum.valueOf(obj[0].toString()) : null);
                    outDTO.setTipoID(obj[1] != null ? TipoIdentificacionEnum.valueOf(obj[1].toString()) : null);
                    outDTO.setIdentificacion(obj[2] != null ? obj[2].toString() : null);
                    outDTO.setPrimerNombre(obj[3] != null ? obj[3].toString() : null);
                    outDTO.setSegundoNombre(obj[4] != null ? obj[4].toString() : null);
                    outDTO.setPrimerApellido(obj[5] != null ? obj[5].toString() : null);
                    outDTO.setSegundoApellido(obj[6] != null ? obj[6].toString() : null);
                    outDTO.obtenerNombreCompleto();
                    outDTO.setFechaNacimiento(obj[7] != null ? obj[7].toString() : null);
                    
                    if(outDTO.getFechaNacimiento()!=null){
                    	DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaNacimiento;
    					try {
    						fechaNacimiento = formatoFecha.parse(outDTO.getFechaNacimiento());
    						outDTO.setEdad(CalendarUtils.calcularEdadAnos(fechaNacimiento));
    					} catch (ParseException e) {
    			            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
    					}
                    }
                    
                    outDTO.setFechaFallecido(obj[8] != null ? obj[8].toString() : null);
                    outDTO.setEstadoCivil((obj[9] != null && !obj[9].toString().equals("")) ? obj[9].toString() : "");
                    outDTO.setGenero(obj[10] != null ? GeneroEnum.valueOf(obj[10].toString()) : null);
                    outDTO.setDireccionResidencia(obj[11] != null ? obj[11].toString() : null);
                    outDTO.setMunicipioCodigo(obj[12] != null ? obj[12].toString() : null);
                    outDTO.setMunicipioNombre(obj[13] != null ? obj[13].toString() : null);
                    outDTO.setDepartamentoCodigo(obj[14] != null ? obj[14].toString() : null);
                    outDTO.setDepartamentoNombre(obj[15] != null ? obj[15].toString() : null);
                    outDTO.setCodigoPostal(obj[16] != null ? obj[16].toString() : null);
                    outDTO.setTelefonoFijo(obj[17] != null ? obj[17].toString() : null);
                    outDTO.setCelular(obj[18] != null ? obj[18].toString() : null);
                    outDTO.setCorreoElectronico(obj[19] != null ? obj[19].toString() : null);
                    outDTO.setAutorizacionEnvioEmail(obj[20] != null ? Boolean.valueOf(obj[20].toString()) : null);
                    outDTO.setAutorizacionDatosPersonales(obj[21] != null ? Boolean.valueOf(obj[21].toString()) : null);
                    outDTO.setEstadoAfiliacion(obj[22] != null ? EstadoAfiliadoEnum.valueOf(obj[22].toString()) : null);
                    outDTO.setGrupoFamiliar(obj[23] != null ? obj[23].toString() : null);
                    outDTO.setFechaAfiliacionCCF(obj[24] != null ? obj[24].toString() : null);
                    outDTO.setFechaRetiro(obj[25] != null ? obj[25].toString() : null);
                    outDTO.setMotivoDesafiliacion(obj[26] != null ? MotivoDesafiliacionBeneficiarioEnum.valueOf(obj[26].toString()) : null);
                    outDTO.setEstudianteTrabajoDesarrolloHumano(obj[27] != null ? Boolean.valueOf(obj[27].toString()) : null);
                    outDTO.setTipoAfiliado(obj[28] != null ? TipoAfiliadoEnum.valueOf(obj[28].toString()) : null);
                    outDTO.setTipoIDAfiliado(obj[29] != null ? TipoIdentificacionEnum.valueOf(obj[29].toString()) : null);
                    outDTO.setIdentificacionAfiliado(obj[30] != null ? obj[30].toString() : null);
                    outDTO.setPrimerNombreAfiliado(obj[31] != null ? obj[31].toString() : null);
                    outDTO.setSegundoNombreAfiliado(obj[32] != null ? obj[32].toString() : null);
                    outDTO.setPrimerApellidoAfiliado(obj[33] != null ? obj[33].toString() : null);
                    outDTO.setSegundoApellidoAfiliado(obj[34] != null ? obj[34].toString() : null);
                    //outDTO.setFechaCreacionRegistro(obj[35] != null ? obj[35].toString().substring(0, 10) : null);
                    outDTO.setFechaCreacionRegistro(obj[35] != null ? obj[35].toString() : null);
                    outDTO.setUsuarioCreacionRegistro(obj[36] != null ? obj[36].toString() : null);
                    outDTO.setCategoria(obj[37] != null ? CategoriaPersonaEnum.valueOf(obj[37].toString()) : null);
                    outDTO.setUltimoPagoCuotaMonetaria(obj[38] != null ? obj[38].toString() : null);
                    outDTO.setInhabilitadoSubsidio(obj[39] != null && obj[39].toString().equals("1")  ? true : false);
                       if(obj[40].toString().equals("1")){
                        outDTO.setCondicionInvalidez(true);
                        }else{
                        outDTO.setCondicionInvalidez(false); 
                        }
                    outDTO.setCodigoCCF((String)CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
                    
                    CertificadoEscolaridadOutDTO[] certificadosEscolaridad = gson.fromJson(obj[41] != null ? obj[41].toString() : null,
                    CertificadoEscolaridadOutDTO[].class);
                    if(certificadosEscolaridad != null){
                    List<CertificadoEscolaridadOutDTO> listCertificado = Arrays.asList(certificadosEscolaridad);
                    outDTO.setDTOArregloCertificadoEscolaridad(listCertificado);
                    }
                    else {
                        outDTO.setDTOArregloCertificadoEscolaridad(null);
                    }
                    
                    salida.add(outDTO);
            	}
            }
        }
    }
	@Override
	public Response obtenerHistoricoAfiliado(TipoIdentificacionEnum tipoID, String identificacion, HttpServletRequest requestContext, UserDTO userDTO) {
		String firmaServicio = "obtenerHistoricoAfiliado(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacion", identificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        List<DatoHistoricoAfiliadoOutDTO> datosHistorico = null; 
       
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try {
			 datosHistorico = (List<DatoHistoricoAfiliadoOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_HISTORICOS_AFILIADO)
					.setParameter("tipoId", tipoID.name())
					.setParameter("numeroId", identificacion)
					.getResultList();
			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		} catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,datosHistorico,entityManager,auditoriaIntegracionServicios);
         }
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,datosHistorico,entityManager,auditoriaIntegracionServicios);
	}
	@Override
	public Response obtenerUltimoSalarioActivo(TipoIdentificacionEnum tipoID,
			String identificacionAfiliado, String periodo, HttpServletRequest requestContext,UserDTO userDTO) {
		String firmaServicio = "obtenerUltimoSalarioActivo(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID.name());
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        parametrosMetodo.put("periodo", periodo);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        List<UltimoSalarioAfiliadoOutDTO> infoSalario  = null; 
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

		try {
			infoSalario = (List<UltimoSalarioAfiliadoOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ULTIMO_SALARIO_AFILIADO_SRV_TRA)
					.setParameter("tipoId", tipoID.name())
					.setParameter("numeroId", identificacionAfiliado)
					.setParameter("periodo", periodo)
					.getResultList();
			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	        /*return !infoSalario.isEmpty() ? infoSalario.get(0) : null;*/
		} catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,infoSalario,entityManager,auditoriaIntegracionServicios);
         }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,infoSalario,entityManager,auditoriaIntegracionServicios);
	}



	/* (non-Javadoc)
	 * @see com.asopagos.afiliaciones.service.IntegracionAfiliacionService#obtenerInfoCiudad(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Response obtenerInfoCiudad(Integer ciudadID, Integer departamentoID, HttpServletRequest requestContext, UserDTO userDTO) {
		String firmaServicio = "obtenerInfoCiudad(Integer, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("ciudadID",  String.valueOf(ciudadID));
        parametrosMetodo.put("departamentoID", String.valueOf(departamentoID));
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        List<InfoCiudadOutDTO> infoCiudad = null; 
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try {			
			infoCiudad = new ArrayList<>();

			if(departamentoID != null){
				infoCiudad = (List<InfoCiudadOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_INFO_CIUDAD_CON_DEPARATMENTO_SRV_TRA)
						.setParameter("codigoMunicipio", ciudadID)
						.setParameter("codigoDepartamento", departamentoID)
						.getResultList();
			}
			else{
				infoCiudad = (List<InfoCiudadOutDTO>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_INFO_CIUDAD_SRV_TRA)
						.setParameter("codigoMunicipio", ciudadID)
						.getResultList();
			}
			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	        /*return !infoCiudad.isEmpty() ? infoCiudad.get(0) : null;*/
		} catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,infoCiudad,entityManager,auditoriaIntegracionServicios);
         }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,infoCiudad,entityManager,auditoriaIntegracionServicios);
	}

	
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response obtenerSalarioMinimoLegal(String periodo, HttpServletRequest requestContext, UserDTO userDTO) {
		String firmaServicio = "obtenerSalarioMinimoLegal(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("periodo",periodo);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

        InfoSalarioMinimoOutDTO info = new InfoSalarioMinimoOutDTO();
        try {
			info.setPeriodo(periodo);
			DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Long fecha = formato.parse(periodo+"-01").getTime();

			Date fechaPeriodo = new Date(fecha);
			
			BigDecimal smlmv;
			
			smlmv = (BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SMMLV_EN_PERIODO)
					.setParameter("fecha",fechaPeriodo)
					.getSingleResult();
			
			if(smlmv == null){
				
				String periodoAjustado = periodo.substring(0, 4) + "-01-01";
				fecha = formato.parse(periodoAjustado).getTime();
				fechaPeriodo = new Date(fecha);
				
				smlmv = (BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SMMLV_EN_PERIODO)
						.setParameter("fecha",fechaPeriodo)
						.getSingleResult();

			}
			
			info.setSalarioMinimo(smlmv.toPlainString());
			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	        //return info;
		} catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,info,entityManager,auditoriaIntegracionServicios);
         }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,info,entityManager,auditoriaIntegracionServicios);
	}


	@Override
	public Response obtenerPadresBiologicosBeneficiario(TipoIdentificacionEnum tipoID,
			String identificacion, HttpServletRequest requestContext, UserDTO userDTO) {
		String firmaServicio = "obtenerPadresBiologicosBeneficiario(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID",tipoID.name());
        parametrosMetodo.put("identificacion",identificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        InfoPadresBiologicosOutDTO infoPadresBiologicos = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());

		try {
			infoPadresBiologicos = new InfoPadresBiologicosOutDTO();
			
			List<Object[]> datosPadre = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_PADRE_BIOLOGICO)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();

            
			
			List<Object[]> datosMadre = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_MADRE_BIOLOGICA)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();
			
			if(datosPadre != null && !datosPadre.isEmpty()){
				infoPadresBiologicos.setTipoIdPadreBiologico(datosPadre.get(0)[0] != null ? datosPadre.get(0)[0].toString() : null);
				infoPadresBiologicos.setIdentificacionPadreBiologico(datosPadre.get(0)[1] != null ? datosPadre.get(0)[1].toString() : null);
				infoPadresBiologicos.setNombrePadreBiologico(datosPadre.get(0)[2] != null ? datosPadre.get(0)[2].toString() : null);
			} 
            
			if(datosMadre != null && !datosMadre.isEmpty()){
				infoPadresBiologicos.setTipoIdMadreBiologica(datosMadre.get(0)[0] != null ? datosMadre.get(0)[0].toString() : null);
				infoPadresBiologicos.setIdentificacionMadreBiologica(datosMadre.get(0)[1] != null ? datosMadre.get(0)[1].toString() : null);
				infoPadresBiologicos.setNombreMadreBiologica(datosMadre.get(0)[2] != null ? datosMadre.get(0)[2].toString() : null);
			}
			
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	        //return infoPadresBiologicos;
		} catch (Exception e) {
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,infoPadresBiologicos,entityManager,auditoriaIntegracionServicios);
         }
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,infoPadresBiologicos,entityManager,auditoriaIntegracionServicios);
	}
    
	@Override
	public ConsultaAfiliadoRecaudosPagosDTO consultaAfiliadoRecaudosPagos(String  idTransaccion,String tipoIdentificacion,String numeroIdentificacion,String additionalData) {
        String firmaServicio = "consultaAfiliadoRecaudosPagos(TipoIdentificacionEnum, String) tipoIdentificacion: "+tipoIdentificacion+" numeroIdentificacion: "+numeroIdentificacion;
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ConsultaAfiliadoRecaudosPagosDTO infoafiliado = new ConsultaAfiliadoRecaudosPagosDTO();
        Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringDate = dateFormat.format(date);
		try {

			List<Object[]> datosPadre = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_AFILIADO_EROLAMIENTO_RECAUDOS_PAGOS)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.getResultList();
            
			if(datosPadre != null && !datosPadre.isEmpty()){
         
				infoafiliado.setTransactionId(idTransaccion);
				infoafiliado.setTransmissionDateTime(stringDate);
				infoafiliado.setResponseCode(Boolean.TRUE);
				infoafiliado.setUserName(datosPadre.get(0)[0] != null ? datosPadre.get(0)[0].toString() : null);
				infoafiliado.setUserEmail(datosPadre.get(0)[1] != null ? datosPadre.get(0)[1].toString() : null);
				infoafiliado.setUserCellPhone(datosPadre.get(0)[2] != null ? datosPadre.get(0)[2].toString() : null);
				//infoafiliado.setErrorID();
				//infoafiliado.setAdditionalData();
			}else{
				infoafiliado.setTransactionId(idTransaccion);
				infoafiliado.setTransmissionDateTime(stringDate);
				infoafiliado.setResponseCode(Boolean.FALSE);
				infoafiliado.setErrorID(3L);
				infoafiliado.setAdditionalData("Tipo y número de identificación no existe.");
            }
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	        return infoafiliado;
		} catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
                infoafiliado.setTransactionId(idTransaccion);
                infoafiliado.setTransmissionDateTime(stringDate);
                infoafiliado.setResponseCode(Boolean.FALSE);
                infoafiliado.setErrorID(1L);
                infoafiliado.setAdditionalData("Servicio de Asopagos no disponible, por favor contactar al responsable.");
            return infoafiliado;
			
		}     
    }
    //WEB SERVICE CAJASAN
	@Override
    public Response consultarInfoAfiliado(ConsultaAfiliadoInDTO input, HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "AfiliadosCajaSanWebService.consultarInfoAfiliado(String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        TipoIdentificacionEnum tipoEnum = input.getTipoDto();
        String identificacionAfiliado = input.getNumeroIdentificacion();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        logger.info("Dentro del metodo de ConsultarInfoAfiliado");
        parametrosMetodo.put("tipoID", tipoEnum.name());
        parametrosMetodo.put("identificacionAfiliado", identificacionAfiliado);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        List<ConsultaAfiliadoCajaSanOutDTO> salida = new ArrayList<>();
        ResponseDTO result = new ResponseDTO();
        try {
        	
        	if (tipoEnum != null && identificacionAfiliado != null) {
                // Traer información del Afiliado
                obtenerInfoTotalAfiliadoEspecificoCajaSan(tipoEnum, identificacionAfiliado, salida,1);
                if(salida == null || salida.isEmpty() || salida.size()== 0){
                    result.setCodigoRespuesta(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.getCodigoError());
                    result.setMensajeError(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.name());
                    logger.error("Error: " + CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.name() + " - " + CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.getCodigoError());
                    return Response.ok(result).build();
                }
                //Buscar beneficiarios relacionados
                obtenerInfoTotalAfiliadoEspecificoCajaSan(tipoEnum, identificacionAfiliado, salida,2);
                logger.info("Finalizaron las consultas.");
                //setear la categoria del afiliado a los beneficiarios
                for (int i = 1; i < salida.size(); i++) {
                    salida.get(i).setCategoria(salida.get(0).getCategoria());
                }
                result.setMensajeError("");
                result.setData(salida);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        }catch (Exception e) {
            logger.info("ERROR: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,salida,entityManager,auditoriaIntegracionServicios);
            result.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            result.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            result.setData(null);
            logger.error("Error: " + CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name() + " - " + CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.getCodigoError());
            return Response.ok(result).build();
        }
         AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,salida,entityManager,auditoriaIntegracionServicios);
         return Response.ok(result).build();
    }

    private void obtenerInfoTotalAfiliadoEspecificoCajaSan(TipoIdentificacionEnum tipoIdentificacion, String identificacion,
            List<ConsultaAfiliadoCajaSanOutDTO> outDTO, int caso){
                
        List<Object[]> outDTOs = new ArrayList<>();
        try {
            //Caso 1 ejecuta la consulta para la info del afiliado
            if(caso == 1){
                logger.info("Ejecutando NamedQuery CONSULTAR_INFO_TOTAL_AFILIADO con tipoIdentificacion = " + tipoIdentificacion.name() + " y numeroIdentificacion = " + identificacion);
                outDTOs = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_TOTAL_AFILIADO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("identificacion", identificacion)
                    .getResultList();
                logger.info("Consulta ejecutada correctamente. Resultados encontrados: " + outDTOs.size());
            }
            //Caso 2 ejecuta la consulta para la info del Beneficiario
            else if(caso == 2){
                // Usa la identificacion del afiliado para traer la info de los beneficiarios
                logger.info("Ejecutando NamedQuery CONSULTAR_INFO_TOTAL_BENEFICIARIO con tipoIdentificacion = " + tipoIdentificacion.name() + " y numeroIdentificacion = " + identificacion);
                outDTOs = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_TOTAL_BENEFICIARIO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("identificacion", identificacion)
                    .getResultList();
                logger.info("Consulta ejecutada correctamente. Resultados encontrados: " + outDTOs.size());
            }
        } catch (Exception e) {
            logger.error("Error al ejecutar la consulta CONSULTAR_INFO_TOTAL_AFILIADO", e);
             if (e.getCause() != null) {
                logger.error("Causa raíz: " + e.getCause().getClass().getName() + " - " + e.getCause().getMessage(), e.getCause());
            }
        }        
        if(outDTOs != null && !outDTOs.isEmpty())
        {
            for (Object[] obj : outDTOs) {

                ConsultaAfiliadoCajaSanOutDTO salida = new ConsultaAfiliadoCajaSanOutDTO();

                salida.setTipoDto(obj[0] != null ? obj[0].toString() : null);
                salida.setNumeroDoc(obj[1] != null ? Long.valueOf(obj[1].toString()) : null);  // Long
                salida.setPrimerNombre(obj[2] != null ? obj[2].toString() : "");
                salida.setSegundoNombre(obj[3] != null ? obj[3].toString() : "");
                salida.setPrimerApellido(obj[4] != null ? obj[4].toString() : "");
                salida.setSegundoApellido(obj[5] != null ? obj[5].toString() : "");
                salida.setTelefono(obj[6] != null ? obj[6].toString() : "");
                salida.setCelular(obj[7] != null ? obj[7].toString() : "");
                salida.setDireccion(obj[8] != null ? obj[8].toString() : "");
                salida.setEdad(obj[9] != null ? Integer.parseInt(obj[9].toString()) : null);
                salida.setEmail(obj[10] != null ? obj[10].toString() : "");
                salida.setFechaAfiliacion(obj[11] != null ? obj[11].toString() : null);
                salida.setTipoAfiliado(obj[12] != null ? obj[12].toString() : "");
                salida.setFechaIngreso(obj[13] != null ? obj[13].toString() : null);
                salida.setFechaNto(obj[14] != null ? obj[14].toString() : null);
                salida.setGenero(obj[15] != null ? obj[15].toString() : "");
                salida.setCodPais(obj[16] != null ? obj[16].toString() : "");
                salida.setMunicipio(obj[17] != null ? obj[17].toString() : "");
                salida.setNitEmpresa(obj[18] != null ? obj[18].toString() : "");
                salida.setRazonSocial(obj[19] != null ? obj[19].toString() : "");
                salida.setCargo(obj[20] != null ? obj[20].toString() : "");
                salida.setProfesion(obj[21] != null ? obj[21].toString() : "");
                salida.setVinculacion(obj[22] != null ? obj[22].toString() : "");
                salida.setParentesco(obj[23] != null ? obj[23].toString() : "");
                salida.setCategoria(obj[24] != null ? obj[24].toString() : "");
                salida.setEstCivil(obj[25] != null ? obj[25].toString() : "");
                salida.setFactVulnera(obj[26] != null ? obj[26].toString() : "");
                salida.setNivelEscol(obj[27] != null ? obj[27].toString() : "");
                salida.setOrientSexual(obj[28] != null ? obj[28].toString() : "");
                salida.setPertEtnica(obj[29] != null ? obj[29].toString() : "");
                salida.setTienenVivi(obj[30] != null ? obj[30].toString() : "");
                salida.setTipoVivi(obj[31] != null ? obj[31].toString() : "");

                outDTO.add(salida);
            }
        }
    }
       /* (non-Javadoc)
     * @see com.asopagos.reportes.service.IntegracionAfiliacionService#obtenerInfoTotalEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response consultarIdentificacionDinamico(ConsultarInformacionDinamicoInDTO consultarInformacionDinamicoInDTO, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "IntegracionAfiliacionBusiness.consultarIdentificacionEmpleador(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        // Auditoria de Integracion de Servicios
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,consultarInformacionDinamicoInDTO.toString(),userDTO.getEmail());
        ResponseDTO result = new ResponseDTO();
        List<Object[]> infoTotal = null;
         try {                 
             List<ConsultarIdentificacionDinamicoOutDTO> salida = new ArrayList<>();
            if(consultarInformacionDinamicoInDTO.getTipoIdentificacion() != null && consultarInformacionDinamicoInDTO.getNumeroIdentificacion() != null ){
              logger.info("N**__**:ingreso a consultarIdentificacionDinamico");
                if(consultarInformacionDinamicoInDTO.getTipoPersona().equals("EMPRESA")){
                   // Traer información del Empleador
                   infoTotal= (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFORMACION_EMPLEADOR)
                    .setParameter("tipoIdEmpleador", consultarInformacionDinamicoInDTO.getTipoIdentificacion().name())
                    .setParameter("numeroIdEmpleador", consultarInformacionDinamicoInDTO.getNumeroIdentificacion())
                    .getResultList();
                }
                else if(consultarInformacionDinamicoInDTO.getTipoPersona().equals("TRABAJADOR")){
                    // Traer información del Empleador por Afiliado
                   infoTotal= (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFORMACION_AFILIADO_SERVICE)
                    .setParameter("tipoIdentificacion", consultarInformacionDinamicoInDTO.getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacion", consultarInformacionDinamicoInDTO.getNumeroIdentificacion())
                    .getResultList();
                }else{
                    result.setCodigoRespuesta(CodigosErrorWebservicesEnum.TIPO_PERSONA_NO_ENCONTRADA.getCodigoError());
                    result.setMensajeError(CodigosErrorWebservicesEnum.TIPO_PERSONA_NO_ENCONTRADA.name());
                    logger.error("Tipo de persona no encontrada: " + consultarInformacionDinamicoInDTO.getTipoPersona());
                    return Response.ok(result).build();
                }
            
                if(infoTotal != null && !infoTotal.isEmpty()){
                    for (Object[] ite : infoTotal) {
                        ConsultarIdentificacionDinamicoOutDTO iteDTO = new ConsultarIdentificacionDinamicoOutDTO();
                        iteDTO.setCargo(ite[0] != null ? ite[0].toString() : "");
                        iteDTO.setCategoria(ite[1] != null ? ite[1].toString() : "");
                        iteDTO.setCelular(ite[2] != null ? ite[2].toString() : "");
                        iteDTO.setCodPais(ite[3] != null ? ite[3].toString() : "");
                        iteDTO.setDireccion(ite[4] != null ? ite[4].toString() : "");
                        iteDTO.setEdad(ite[5] != null ? Long.valueOf(ite[5].toString()) : 0L);
                        iteDTO.setEmail(ite[6] != null ? ite[6].toString() : "");
                        iteDTO.setEstCivil(ite[7] != null ? ite[7].toString() : "");
                        iteDTO.setFactVulnera(ite[8] != null ? Long.valueOf(ite[8].toString()) : 0L);
                        iteDTO.setFecIngreso(ite[9] != null ? (Date) ite[9] : null);
                        iteDTO.setFechaAfiliacion(ite[10] != null ? (Date) ite[10] : null);
                        iteDTO.setFechaNto(ite[11] != null ? (Date) ite[11] : null);
                        iteDTO.setGenero(ite[12] != null ? ite[12].toString() : null);
                        iteDTO.setInternoEmpresa(ite[13] != null ? Long.valueOf(ite[13].toString()) : 0L);
                        iteDTO.setMunicipio(ite[14] != null ? ite[14].toString() : ""); 
                        iteDTO.setNitEmpresa(ite[15] != null ? ite[15].toString() : "");
                        iteDTO.setNivEscol(ite[16] != null ? ite[16].toString() : "");
                        iteDTO.setNumeroDcto(ite[17] != null ? ite[17].toString() : "");
                        iteDTO.setNumeroDctoPrincipal(ite[18] != null ? ite[18].toString(): "");
                        iteDTO.setOriSexual(ite[19] != null ? ite[19].toString() : "");
                        iteDTO.setParentesco(ite[20] != null ? ite[20].toString() : "");
                        iteDTO.setPertEtnica(ite[21] != null ? ite[21].toString() : "");
                        iteDTO.setPrimerApellido(ite[22] != null ? ite[22].toString() : "");
                        iteDTO.setPrimerNombre(ite[23] != null ? ite[23].toString() : "");
                        iteDTO.setProfesion(ite[24] != null ? ite[24].toString() : "");
                        iteDTO.setRazonSocial(ite[25] != null ? ite[25].toString() : "");
                        iteDTO.setSegundoApellido(ite[26] != null ? ite[26].toString() : "");
                        iteDTO.setSegundoNombre(ite[27] != null ? ite[27].toString() : "");
                        iteDTO.setTelefono(ite[28] != null ? ite[28].toString() : "");
                        iteDTO.setTenenVivi(ite[29] != null ? Long.valueOf(ite[29].toString()) : 0L);
                        iteDTO.setTipVivi(ite[30] != null ? ite[30].toString() : "");
                        iteDTO.setTipoAfiliado(ite[31] != null ? ite[31].toString() : "");
                        iteDTO.setTipoDto(ite[32] != null ? ite[32].toString() : "");
                        iteDTO.setViculacion(ite[33] != null ? ite[33].toString() : "");
                        
                        salida.add(iteDTO);
                    }
                    result.setData(salida);
                    result.setMensajeError("");
                    result.setCodigoRespuesta(null);
                }else{
                    if(consultarInformacionDinamicoInDTO.getTipoPersona().equals("EMPRESA")){
                        result.setCodigoRespuesta(CodigosErrorWebservicesEnum.NO_EXISTE_EMPLEADOR.getCodigoError());
                        result.setMensajeError(CodigosErrorWebservicesEnum.NO_EXISTE_EMPLEADOR.name());
                        logger.error("No se encontró información del empleador con tipo de identificación: " + consultarInformacionDinamicoInDTO.getTipoIdentificacion().name() + " y número de identificación: " + consultarInformacionDinamicoInDTO.getNumeroIdentificacion());
                    }else if(consultarInformacionDinamicoInDTO.getTipoPersona().equals("TRABAJADOR")){
                        result.setCodigoRespuesta(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.getCodigoError());
                        result.setMensajeError(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO.name());
                        logger.error("No se encontró información del afiliado con tipo de identificación: " + consultarInformacionDinamicoInDTO.getTipoIdentificacion().name() + " y número de identificación: " + consultarInformacionDinamicoInDTO.getNumeroIdentificacion());
                    }
                 }
            }else{
                result.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                result.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
                logger.error("Tipo de identificación o número de identificación del empleador no pueden ser nulos");
            }

        } catch (Exception e) {
            e.printStackTrace();
                logger.error("Error al consultar información del empleador: " + e.getMessage());
               // persistirDatosAuditoria(start, e,result, auditoriaIntegracionServicios);
                result.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
                result.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name()); 
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,result,entityManager,auditoriaIntegracionServicios);
        }
          //persistirDatosAuditoria(start, null,result, auditoriaIntegracionServicios);
      
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,result,entityManager,auditoriaIntegracionServicios);
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public  void persistirDatosAuditoria(Instant start,  Exception e,Object salida, AuditoriaIntegracionServicios auditoriaIntegracionServicios) {
        System.out.println("Persistir datos auditorias");
        Integer codigoEstado ;
        Instant finish = Instant.now();
        long duracion = Duration.between(start, finish).toMillis();
        String detallesErrores = "";
        Boolean resultado = true;
        String parametrosSalida = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(salida);
        codigoEstado = Response.Status.OK.getStatusCode();
        if (e != null) {
            resultado = false;
            detallesErrores = e.getMessage();
            codigoEstado = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
        if(e instanceof NullPointerException){
                detallesErrores = "NullPointerException";
        }
        
        auditoriaIntegracionServicios.setDuracion((int) duracion);
        auditoriaIntegracionServicios.setDatosRespuesta(parametrosSalida);
        auditoriaIntegracionServicios.setDetallesErrores(detallesErrores);
        auditoriaIntegracionServicios.setCodigoEstado(codigoEstado);
        auditoriaIntegracionServicios.setResultado(resultado);
        entityManager.persist(auditoriaIntegracionServicios);
        
        System.out.println("Fin Persistir datos auditorias");
  }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response buscarTarjeta(BuscarTarjetaInDTO input, HttpServletRequest requestContext, UserDTO userDTO) {
    
        String firmaServicio = "buscarTarjeta(BuscarTarjetaInDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        
        TipoIdentificacionEnum tipoID = input != null ? input.getTipoDto() : null;
        String identificacionPersona = input != null ? input.getNumeroIdentificacion() : null;
        
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoID", tipoID != null ? tipoID.name() : "null");
        parametrosMetodo.put("identificacionPersona", identificacionPersona);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        
        BuscarTarjetaOutDTO tarjeta = new BuscarTarjetaOutDTO();
        ResponseDTO respuesta = new ResponseDTO();
    
        try {
            if (tipoID == null || identificacionPersona == null || identificacionPersona.isEmpty()) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
                logger.error("Parámetros incorrectos: tipoID=" + tipoID + ", identificacionPersona=" + identificacionPersona);
            } else {
                logger.info("Ejecutando consulta BUSCAR_TARJETA_AFILIADO con parámetros: tipoID=" + tipoID.name() + ", numeroIdentificacion=" + identificacionPersona);
                
                List<String> resultados = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_TARJETA_AFILIADO)
                    .setParameter("numeroIdentificacion", identificacionPersona)
                    .setParameter("tipoIdentificacion", tipoID.name())
                    .getResultList();
                  
                if (resultados != null && !resultados.isEmpty()) {
                    logger.info("Primer elemento de resultados: " + resultados.get(0));
                    
                    if (resultados.get(0) != null) {
                        String numeroTarjeta = resultados.get(0);
                        tarjeta.setNumeroTarjeta(numeroTarjeta);
                        respuesta.setMensajeError("");
                        respuesta.setData(tarjeta.getNumeroTarjeta());
                        logger.info("Tarjeta encontrada exitosamente: " + numeroTarjeta + " para tipo ID: " + tipoID.name() + " y número: " + identificacionPersona);
                    } else {
                        respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.NO_EXISTE_TARJETA.getCodigoError());
                        respuesta.setMensajeError(CodigosErrorWebservicesEnum.NO_EXISTE_TARJETA.name());
                        logger.warn("La consulta devolvió resultados pero el primer elemento es null para tipo de ID: " + tipoID.name() + " y número: " + identificacionPersona);
                    }
                } else {
                    respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.NO_EXISTE_TARJETA.getCodigoError());
                    respuesta.setMensajeError(CodigosErrorWebservicesEnum.NO_EXISTE_TARJETA.name());
                    logger.warn("No se encontró tarjeta para el tipo de ID: " + tipoID.name() + " y número: " + identificacionPersona);
                }
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            
        } catch (Exception e) {
            logger.error("Error en buscarTarjeta: " + e.getMessage(), e);
            respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            respuesta.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            return Response.ok(respuesta).build();
        }
        
        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, respuesta, entityManager, auditoriaIntegracionServicios);
        return Response.ok(respuesta).build();
    }


    @Override
    public Response orquestarAfiliacionEmpresa(AfiliacionEmpleadorDTO empleador,HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "Response orquestarAfiliacionEmpresa(AfiliacionEmpleadorDTO empleador)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("empleadorDTO",empleador.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        Set<ConstraintViolation<AfiliacionEmpleadorDTO>> violations = validator.validate(empleador);
        ResponseDTO response = new ResponseDTO();
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try{

            DigitarYRadicarSolicitudAfiliacionWS primerPaso = new DigitarYRadicarSolicitudAfiliacionWS(empleador);
            primerPaso.execute();
            Map<String,Object>  resultado = primerPaso.getResult();

            if(resultado.get("numeroRadicado") != null){
                response.setMensajeError("");
                response.setData("Empresa afiliada bajo el numero de radicado: "+resultado.get("numeroRadicado"));
            }else{
                response = new ResponseDTO(CodigosErrorWebservicesEnum.EMPRESA_NO_AFILIABLE,resultado.get("validacion"));    
            }

            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }catch(Exception e){
            logger.error("Error en orquestarAfiliacionEmpresa: " + e.getMessage(), e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }
    }

    @Override
    public Response consultarAportes(ConsultarAportesInDTO consultante, HttpServletRequest requestContext, UserDTO userDTO){
        Long numeroIdentificacion = consultante.getNumeroIdentificacion();
        int anioConsulta = consultante.getAnio();
        Instant start = Instant.now();
        //Construcciion de los periodos
        Calendar calendar = Calendar.getInstance();
        int anioActual = calendar.get(Calendar.YEAR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        // Fecha inicial
        Calendar fechaInicialCal = Calendar.getInstance();
        fechaInicialCal.set(anioConsulta, Calendar.JANUARY, 1); 
        String periodoInicial = dateFormat.format(fechaInicialCal.getTime());
        // Fecha final
        Calendar fechaFinalCal = Calendar.getInstance();
        if (anioConsulta < anioActual) {
            // 31 de diciembre del año consultado
            fechaFinalCal.set(anioConsulta, Calendar.DECEMBER, 31);
        } 
        String periodoFinal = dateFormat.format(fechaFinalCal.getTime());
        //parametros para la auditoria
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("numeroIdentificacion", String.valueOf(numeroIdentificacion));
        parametrosMetodo.put("periodoInicio", periodoInicial);
        parametrosMetodo.put("periodoFin", periodoFinal);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        logger.info("Parametros de entada: " + parametrosIn);
        String firmaServicio = "IntegracionAfiliacionBusiness.consultarAporte(ConsultarAportesInDTO)";
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        ResponseDTO result = new ResponseDTO();
        try{
            logger.debug("Inicio de método consultarAportes(ConsultarAportesInDTO consultante)");
            if (numeroIdentificacion != null) {
                List<Object[]> aportes = new ArrayList<>();
                if(consultante.getTipoConsultante().name().equals("APORTANTE")){
                    aportes = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_WS)
						.setParameter("numeroIdentificacion", String.valueOf(numeroIdentificacion))
                        .setParameter("periodoInicial", periodoInicial)
                        .setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
                }
                else if(consultante.getTipoConsultante().name().equals("COTIZANTE")){
                    aportes = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_COTIZANTE_PERSONA_PERIODOS_WS)
						.setParameter("numeroIdentificacion", String.valueOf(numeroIdentificacion))
                        .setParameter("periodoInicial", periodoInicial)
                        .setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
                }
				
                if(aportes != null && !aportes.isEmpty()){
                    List<ConsultarAportesOutDTO> salida = new ArrayList<>();
                    for( Object[] obj : aportes){
                        ConsultarAportesOutDTO aporte = new ConsultarAportesOutDTO();
                        aporte.setAnioPago(anioConsulta);
                        aporte.setFechaConsignacion(obj[0] != null ? Integer.valueOf(obj[0].toString().replaceAll("-", "")) : null);
                        aporte.setMesPago(obj[1] != null ? Integer.valueOf(obj[1].toString().split("-")[1]) : 0);
                        aporte.setNumeroIdentificacion(obj[2] != null ? Long.valueOf(obj[2].toString()) : null);
                        aporte.setNroConsignacion(obj[3] != null ? Long.valueOf(obj[3].toString()) : 0);
                        aporte.setVrAporte(obj[4] != null ? Double.valueOf(obj[4].toString()) : 0);
                        aporte.setVrIntereses(obj[5] != null ? Double.valueOf(obj[5].toString()) : 0);
                        salida.add(aporte);
                    }
                    result.setMensajeError("");
                    result.setData(salida);
                    return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,result,entityManager,auditoriaIntegracionServicios);
                }
			}

            result.setCodigoRespuesta(CodigosErrorWebservicesEnum.SIN_APORTES.getCodigoError());
            result.setMensajeError(CodigosErrorWebservicesEnum.SIN_APORTES.name());
            result.setData(null);
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,result,entityManager,auditoriaIntegracionServicios);
        }catch(Exception e){
            result.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            result.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            result.setData(null);
            logger.error("ERROR EN CONSULTAR APORTES",e);
            return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
    }

    public Response getMunicipiosCalendario(GetMunicipiosCalendarioInDTO input, HttpServletRequest requestContext, UserDTO userDTO){
        
        String firmaServicio = "getMunicipiosCalendario(GetMunicipiosCalendarioInDTO codigoDepartamento)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        
        String codigoDepartamento = input.getCodigoDepartamento();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("codigoDepartamento", codigoDepartamento != null ? codigoDepartamento: "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        ResponseDTO respuesta = new ResponseDTO();
        List<Object[]> resultados = null;

        try { 
            List<GetMunicipiosCalendarioOutDTO> salida = new ArrayList<>();

            if (codigoDepartamento == null || codigoDepartamento.isEmpty()) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
                logger.error("Parámetros incorrectos: "+codigoDepartamento);
            } else {

                logger.info("Ejecutando consulta OBTENER_MUNICIPIO_DEPARTAMENTO con parámetros: "+ codigoDepartamento);
                
                resultados = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_MUNICIPIO_DEPARTAMENTO)
                    .setParameter("codigoDepartamento", codigoDepartamento)
                    .getResultList();
                
                if(resultados != null && !resultados.isEmpty()){
                    for(Object[] resultado : resultados) {

                        GetMunicipiosCalendarioOutDTO municipioDTO = new GetMunicipiosCalendarioOutDTO();
                        municipioDTO.setCodigoMunicipio(resultado[0] != null ? resultado[0].toString() : "");
                        municipioDTO.setNombreMunicipio(resultado[1] != null ? resultado[1].toString() : "");
        
                        salida.add(municipioDTO);
                        
                    }
                    respuesta.setMensajeError("");
                    respuesta.setData(salida);
                }else {
                    respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.MUNICIPIOS_NO_ENCONTRADOS.getCodigoError());
                    respuesta.setMensajeError(CodigosErrorWebservicesEnum.MUNICIPIOS_NO_ENCONTRADOS.name());
                    logger.info("Codigos de municipios no encontrados con el codigo de departamento: "+ codigoDepartamento);
                }
            }
            
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            respuesta.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            return Response.ok(respuesta).build();
        } 

        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, respuesta, entityManager, auditoriaIntegracionServicios);
        return Response.ok(respuesta).build();
    }

    @Override
    public Response afiliacionPC(AfiliarPersonaACargoDTO dataIn,HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "Response afiliacionPC(AfiliarPersonaACargoDTO dataIn)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("afiliarPersonaACargoDTO",dataIn.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<AfiliarPersonaACargoDTO>> violations = validator.validate(dataIn);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
            
        try{
            List<ValidacionDTO> resultadosFallidos = armarYVerificarSolicitudes(dataIn);
            // logger.warn(resultadosFallidos == null && resultadosFallidos.isEmpty());
            if(resultadosFallidos == null || resultadosFallidos.isEmpty()){
                // como no tuvo resultados fallidos, hay que radicar la novedad
                // prueba pa ver las validaciones
                RadicarSolicitudNovedad radicarSolicitud = new RadicarSolicitudNovedad(construirSolicitud(dataIn));
                radicarSolicitud.execute();
                SolicitudNovedadDTO resultadoSolictud = radicarSolicitud.getResult();
                if(resultadoSolictud.getResultadoValidacion().name() == "APROBADA"){
                    response.setMensajeError("");
                    response.setData("Persona afiliada.");
                }else{
                    logger.warn("entra else posterior a novedad");
                    logger.warn(resultadoSolictud.getResultadoValidacion().name());
                    response = new ResponseDTO(CodigosErrorWebservicesEnum.BENEFICIARIO_NO_AFILIABLE,resultadoSolictud.getListResultadoValidacion().stream().map(ValidacionDTO::getDetalle).collect(Collectors.toList()));
                }
            }else{
                response = new ResponseDTO(CodigosErrorWebservicesEnum.BENEFICIARIO_NO_AFILIABLE,resultadosFallidos.stream().map(v ->v.getDetalle()).collect(Collectors.toList()));
            }
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }catch(Exception e){
            logger.error("Error en orquestarAfiliacionEmpresa: " + e.getMessage(), e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }
    }

    private List<ValidacionDTO> armarYVerificarSolicitudes(AfiliarPersonaACargoDTO dataIn){
        TipoIdentificacionEnum tipoDtoAfi = dataIn.getTipoDtoTrabajador();
        String nroIdentificacionAfi = String.valueOf(dataIn.getNroTrabajador());
        String bloque;
        String proceso = "NOVEDADES_DEPENDIENTE_WEB";
        String objeto = dataIn.getParentesco().name();
        Map<String, String> datosValidacion = new HashMap<String,String>();
        //Convertir la fecha a formato epoch
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            Date fecha = sdf.parse(dataIn.getFechaNto());
            long epoch = fecha.getTime(); 
            datosValidacion.put("fechaNacimiento", String.valueOf(epoch));
        } catch (ParseException e) {
            throw new TechnicalException("Formato de fecha inválido: " + dataIn.getFechaNto(), e);
        }
        datosValidacion.put("tipoIdentificacion", dataIn.getTipoId().name());
        datosValidacion.put("numeroIdentificacion", String.valueOf(dataIn.getNroId()));
        datosValidacion.put("tipoIdentificacionAfiliado", tipoDtoAfi.name());
        datosValidacion.put("numeroIdentificacionAfiliado", nroIdentificacionAfi);
        datosValidacion.put("primerNombre", dataIn.getPrimerNombre());
        datosValidacion.put("primerApellido", dataIn.getPrimerApellido());
        datosValidacion.put("tipoBeneficiario", dataIn.getParentesco().name());
        datosValidacion.put("condicionInvalidez", String.valueOf(dataIn.getDiscapacidad()));
        datosValidacion.put("estudianteTrabajoDesarrolloHumano", "false");
        datosValidacion.put("genero", dataIn.getGenero().name());
        switch (dataIn.getParentesco()) {
            case CONYUGE:
                bloque = "ACTIVAR_BENEFICIARIO_CONYUGE_WEB";
                break;
            case HIJO_BIOLOGICO:
                bloque = "ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB";
                break;
            case HIJASTRO:
                bloque = "ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB";
                break;
            case HERMANO_HUERFANO_DE_PADRES:
                bloque = "ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB";
                break;
            case PADRE:
                bloque = "ACTIVAR_BENEFICIARIO_PADRE_DEPWEB";
                break;
            case MADRE:
                bloque = "ACTIVAR_BENEFICIARIO_MADRE_DEPWEB";
                break;
            case HIJO_ADOPTIVO:
                bloque = "ACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_DEPWEB";
                break;
            case BENEFICIARIO_EN_CUSTODIA:
                bloque = "ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB";
            default:
                throw new IllegalArgumentException("Parentesco no soportado: " + dataIn.getParentesco());
        }
        try {
            ValidarPersonas validador = new ValidarPersonas(bloque,ProcesoEnum.valueOf(proceso),objeto,datosValidacion);
            validador.execute();
            return validador.getResult().stream().filter(v -> v.getResultado().name() == "NO_APROBADA").collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    private SolicitudNovedadDTO construirSolicitud(AfiliarPersonaACargoDTO dataIn){
        SolicitudNovedadDTO solicitud = new SolicitudNovedadDTO();
        DatosPersonaNovedadDTO personaNovedad = construirPersonaNovedadWs(dataIn.getTipoDtoTrabajador(),String.valueOf(dataIn.getNroTrabajador()),false);
        solicitud.setCanalRecepcion(CanalRecepcionEnum.WEB);
        solicitud.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
        solicitud.setClasificacion(dataIn.getParentesco());
        solicitud.setObservaciones("N/A");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        // solicitud.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        switch (dataIn.getParentesco()) {
            case CONYUGE:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case HIJO_BIOLOGICO:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case HIJASTRO:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case HERMANO_HUERFANO_DE_PADRES:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case PADRE:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case MADRE:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
                break;
            case HIJO_ADOPTIVO:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
            case BENEFICIARIO_EN_CUSTODIA:
                solicitud.setTipoTransaccion(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
                personaNovedad.setParentescoBeneficiarios(dataIn.getParentesco());
                personaNovedad.setClasificacion(dataIn.getParentesco());
                personaNovedad.setGeneroBeneficiario(dataIn.getGenero());
                personaNovedad.setGeneroConyuge(dataIn.getGenero());
                try {
                    personaNovedad.setFechaNacimientoConyuge(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                    personaNovedad.setFechaNacimientoBeneficiario(((Date)formato.parse(dataIn.getFechaNto())).getTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                personaNovedad.setTipoIdentificacionBeneficiario(dataIn.getTipoId());
                personaNovedad.setNumeroIdentificacionBeneficiario(String.valueOf(dataIn.getNroId()));
                personaNovedad.setPrimerApellidoBeneficiario(dataIn.getPrimerApellido());
                personaNovedad.setPrimerNombreBeneficiario(dataIn.getPrimerNombre());
                personaNovedad.setAfiliacion(Boolean.TRUE);
            default:
                throw new IllegalArgumentException("Parentezco no soportado: " + dataIn.getParentesco());
        }
        solicitud.setDatosPersona(personaNovedad);
        return solicitud;
    }

    private DatosPersonaNovedadDTO construirPersonaNovedadWs(TipoIdentificacionEnum tipoDoc, String numeroDoc,Boolean esActualizar){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Object[] datosConsulta = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_NOVEDAD)
                            .setParameter("numeroIdentificacion",numeroDoc)
                            .setParameter("tipoIdentificacion",tipoDoc.name())
                            .getSingleResult();
            logger.info("Tamaño lista consulta: "+datosConsulta.length);
            DatosPersonaNovedadDTO personaNovedad = new DatosPersonaNovedadDTO();
            personaNovedad.setNumeroIdentificacion(numeroDoc);
            personaNovedad.setTipoIdentificacion(tipoDoc);
            personaNovedad.setTipoIdentificacionTrabajador(tipoDoc);
            personaNovedad.setNumeroIdentificacionTrabajador(numeroDoc);
            personaNovedad.setPrimerNombreTrabajador(datosConsulta[4].toString());
            personaNovedad.setSegundoNombreTrabajador((datosConsulta[5] == null || datosConsulta[5].toString() == null) ? "":datosConsulta[5].toString());
            personaNovedad.setPrimerApellidoTrabajador(datosConsulta[2].toString() == null ? "":datosConsulta[2].toString());
            personaNovedad.setSegundoApellidoTrabajador((datosConsulta[3] == null || datosConsulta[3].toString() == null) ? "":datosConsulta[3].toString());
            personaNovedad.setGeneroTrabajador(GeneroEnum.valueOf(datosConsulta[6].toString()));
            personaNovedad.setFechaNacimientoTrabajador(((Date)formato.parse(datosConsulta[7].toString())).getTime());
            personaNovedad.setNivelEducativoTrabajador(NivelEducativoEnum.valueOf(datosConsulta[8].toString()));
            personaNovedad.setMunicipioTrabajador(new Municipio().setIdMunicipio2(Short.parseShort(datosConsulta[9].toString())).setIdDepartamento2(Short.parseShort(datosConsulta[10].toString())));
            personaNovedad.setDireccionResidenciaTrabajador(datosConsulta[11].toString());
            personaNovedad.setTelefonoCelularTrabajador(datosConsulta[12].toString());
            personaNovedad.setCorreoElectronicoTrabajador(datosConsulta[13].toString());
            personaNovedad.setPaisResidencia(Long.valueOf(datosConsulta[14].toString()));
            personaNovedad.setIdResguardo((datosConsulta[15] == null || datosConsulta[15].toString() == null) ? 0L : Long.valueOf(datosConsulta[15].toString()));
            personaNovedad.setIdPuebloIndigena((datosConsulta[16] == null || datosConsulta[16].toString() == null) ? 0L : Long.valueOf(datosConsulta[16].toString()));
            if(!esActualizar){
                GrupoFamiliar grupoFamiliarNuevo = new GrupoFamiliar();
                Afiliado afiliado = new Afiliado();
                afiliado.setIdAfiliado(Long.valueOf(datosConsulta[25].toString()));
                grupoFamiliarNuevo.setAfiliado(afiliado);
                grupoFamiliarNuevo.setNumero( (datosConsulta[24] == null || datosConsulta[24].toString() == null) ? Byte.valueOf("1") : Byte.valueOf(String.valueOf(Long.valueOf(datosConsulta[24].toString())+1L)));
                entityManager.persist(grupoFamiliarNuevo);
                GrupoFamiliarModeloDTO grupoModelo = new GrupoFamiliarModeloDTO();
                grupoModelo.setIdGrupoFamiliar(grupoFamiliarNuevo.getIdGrupoFamiliar());
                grupoModelo.setNumero(grupoFamiliarNuevo.getNumero());
                UbicacionModeloDTO ubicacionGrupo = new UbicacionModeloDTO();
                ubicacionGrupo.setIdUbicacion(datosConsulta[26] == null ? null : Long.valueOf(datosConsulta[26].toString()));
                ubicacionGrupo.setDireccionFisica(datosConsulta[11].toString());
                ubicacionGrupo.setIdMunicipio(Short.valueOf(datosConsulta[9].toString()));
                grupoModelo.setUbicacionGrupoFamiliar(ubicacionGrupo);
                personaNovedad.setGrupoFamiliarBeneficiario(grupoModelo);
            }
            personaNovedad.setOrientacionSexual(datosConsulta[27] == null ? null : OrientacionSexualEnum.valueOf(datosConsulta[27].toString()));
            personaNovedad.setFactorVulnerabilidad(datosConsulta[28] == null ? null : FactorVulnerabilidadEnum.valueOf(datosConsulta[28].toString()));
            personaNovedad.setPertenenciaEtnica(datosConsulta[29] == null ? null : PertenenciaEtnicaEnum.valueOf(datosConsulta[29].toString()));
            personaNovedad.setNivelEducativoTrabajador(datosConsulta[30] == null ? null : NivelEducativoEnum.valueOf(datosConsulta[30].toString()));
            OcupacionProfesion ocupacion = new OcupacionProfesion();
            ocupacion.setIdOcupacionProfesion(datosConsulta[31] == null ? null : Integer.valueOf(datosConsulta[31].toString()));
            personaNovedad.setOcupacionProfesionTrabajador(ocupacion);
            personaNovedad.setEstadoCivilTrabajador(datosConsulta[32] == null ? null : EstadoCivilEnum.valueOf(datosConsulta[32].toString()));
            personaNovedad.setTelefonoFijoTrabajador(datosConsulta[33] == null ? null : datosConsulta[33].toString());
            return personaNovedad;

        } catch (Exception e) {
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response consultarPagosPC(ConsultarPagosPCInDTO input, HttpServletRequest requestContext, UserDTO userDTO) {

        String firmaServicio = "consultarPagosPC(ConsultarPagosPCInDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        String tipoDto =  input.getTipoDto().name();
        String numeroIdentificacion = input.getNumeroIdentificacion(); 

        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoDto", tipoDto != null ? tipoDto: "null");
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion != null ? numeroIdentificacion: "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        ResponseDTO respuesta = new ResponseDTO();
        List<Object[]> resultados = null;

        try { 
            List<ConsultarPagosPCOutDTO> salida = new ArrayList<>();

            if (tipoDto == null || numeroIdentificacion == null || numeroIdentificacion.isEmpty()) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
                logger.error("Parámetros incorrectos: tipoID=" + tipoDto + ", identificacionPersona=" + numeroIdentificacion);
            } else {

                logger.info("Ejecutando consulta OBTENER_PAGOS_PERSONA con parámetros: "+ tipoDto + ", " + numeroIdentificacion);

                resultados = (List<Object[]>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PAGOS_PERSONA)
                    .setParameter("tipoDto", tipoDto)
                    .setParameter("numeroIdentificacion",numeroIdentificacion)
                    .getResultList();
                
                if(resultados != null && !resultados.isEmpty()){
                    for(Object[] resultado : resultados) {

                        ConsultarPagosPCOutDTO buscarPagosPorPersona = new ConsultarPagosPCOutDTO();
                        buscarPagosPorPersona.setCuotas(resultado[0] != null ? Integer.valueOf(resultado[0].toString()) : 0);
                        buscarPagosPorPersona.setMedioPago(resultado[1] != null ? resultado[1].toString() : "");
                        buscarPagosPorPersona.setPersonaCargo(resultado[2] != null ? resultado[2].toString() : "");
                        buscarPagosPorPersona.setTercero(resultado[3] != null ? resultado[3].toString() : "");
                        buscarPagosPorPersona.setVigencia(resultado[4] != null ? ((Number) resultado[4]).intValue() : 0);
                        buscarPagosPorPersona.setVrCuota(resultado[5] != null ? Double.valueOf(resultado[5].toString()) : 0.0);

                        salida.add(buscarPagosPorPersona);

                    }
                    respuesta.setMensajeError("");  
                    respuesta.setData(salida);
                }else {
                    respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PAGOS_NO_ENCONTRADOS.getCodigoError());
                    respuesta.setMensajeError(CodigosErrorWebservicesEnum.PAGOS_NO_ENCONTRADOS.name());
                    logger.info("Codigos de pagos no encontrados con la persona : "+ numeroIdentificacion);
                }
            }
            
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            respuesta.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            return Response.ok(respuesta).build();
        } 

        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, respuesta, entityManager, auditoriaIntegracionServicios);
        return Response.ok(respuesta).build();
    }
    @Override
    public Response actualizarDatos(ActualizarDatosDTO dataIn,  HttpServletRequest requestContext, UserDTO userDTO){
        //Auditoria
        String firmaServicio = "actualizarDatos(ActualizarDatosDTO dataIn)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        TipoIdentificacionEnum tipoDto = dataIn.getTipoDcto();
        String documento = String.valueOf(dataIn.getDocumento());
        Map<String,String> novedades = new HashMap<>();
        List<String> fallidas = new ArrayList<>();
        Map<String,List<String>> validaciones = new HashMap<>();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoDcto", tipoDto != null ? tipoDto.name() : "null");
        parametrosMetodo.put("documento", documento != null ? documento : "null");
        parametrosMetodo.put("primerNombre", dataIn.getPrimerNombre() != null ? dataIn.getPrimerNombre() : "null");
        parametrosMetodo.put("segundoNombre", dataIn.getSegundoNombre() != null ? dataIn.getSegundoNombre() : "null");
        parametrosMetodo.put("primerApellido", dataIn.getPrimerApellido() != null ? dataIn.getPrimerApellido() : "null");
        parametrosMetodo.put("segundoApellido", dataIn.getSegundoApellido() != null ? dataIn.getSegundoApellido() : "null");
        parametrosMetodo.put("fechaNto", dataIn.getFechaNto() != null ? dataIn.getFechaNto() : "null");
        parametrosMetodo.put("genero", dataIn.getGenero() != null ? dataIn.getGenero() : "null");
        parametrosMetodo.put("email", dataIn.getEmail() != null ? dataIn.getEmail() : "null");
        parametrosMetodo.put("municipio", dataIn.getMunicipio() != null ? dataIn.getMunicipio() : "null");
        parametrosMetodo.put("departamento", dataIn.getDepartamento() != null ? dataIn.getDepartamento() : "null");
        parametrosMetodo.put("direccion", dataIn.getDireccion() != null ? dataIn.getDireccion() : "null");
        parametrosMetodo.put("telefono", dataIn.getTelefono() != null ? dataIn.getTelefono() : "null");
        parametrosMetodo.put("celular", dataIn.getCelular() != null ? dataIn.getCelular() : "null");
        parametrosMetodo.put("origen", dataIn.getOrigen() != null ? dataIn.getOrigen() : "null");
        parametrosMetodo.put("usuario", dataIn.getUsuario() != null ? dataIn.getUsuario() : "null");
        parametrosMetodo.put("accion", dataIn.getAccion() != null ? dataIn.getAccion() : "null");
        parametrosMetodo.put("pais", dataIn.getPais() != null ? dataIn.getPais() : "null");
        parametrosMetodo.put("orientacionSexual", dataIn.getOrientacionSexual() != null ? dataIn.getOrientacionSexual().name() : "null");
        parametrosMetodo.put("profesion", dataIn.getProfesion() != null ? dataIn.getProfesion() : "null");
        parametrosMetodo.put("discapacidad", dataIn.getDiscapacidad() != null ? dataIn.getDiscapacidad() : "null");
        parametrosMetodo.put("estadoCivil", dataIn.getEstadoCivil() != null ? dataIn.getEstadoCivil().name() : "null");
        parametrosMetodo.put("factorVulnerabilidad", dataIn.getFactorVulnerabilidad() != null ? dataIn.getFactorVulnerabilidad().name() : "null");
        parametrosMetodo.put("nivelEscolaridad", dataIn.getNivelEscolaridad() != null ? dataIn.getNivelEscolaridad().name() : "null");
        parametrosMetodo.put("pertenenciaEtnica", dataIn.getPertenenciaEtnica() != null ? dataIn.getPertenenciaEtnica().name() : "null");
        parametrosMetodo.put("puebloIndigena", dataIn.getPuebloIndigena() != null ? dataIn.getPuebloIndigena() : "null");
        parametrosMetodo.put("lugarExpedicion", dataIn.getLugarExpedicion() != null ? dataIn.getLugarExpedicion() : "null");
        parametrosMetodo.put("resguardo", dataIn.getResguardo() != null ? dataIn.getResguardo() : "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        DatosPersonaNovedadDTO personaConsulta = new DatosPersonaNovedadDTO(); 
        try{
            personaConsulta = construirPersonaNovedadWs(tipoDto,documento,true); 
        }catch(Exception e){
            ResponseDTO errorDTO = new ResponseDTO(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO, null);
            errorDTO.setMensajeError("Error procesando solicitud: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, errorDTO, entityManager, auditoriaIntegracionServicios);
            return Response.ok(errorDTO).build();
        }
        //Para novedad CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB
        if ((dataIn.getPrimerNombre() != null && !dataIn.getPrimerNombre().trim().isEmpty() 
                && !dataIn.getPrimerNombre().trim().equalsIgnoreCase(personaConsulta.getPrimerNombreTrabajador())) ||

            (dataIn.getSegundoNombre() != null && !dataIn.getSegundoNombre().trim().isEmpty() 
                && !dataIn.getSegundoNombre().trim().equalsIgnoreCase(personaConsulta.getSegundoNombreTrabajador())) ||

            (dataIn.getPrimerApellido() != null && !dataIn.getPrimerApellido().trim().isEmpty() 
                && !dataIn.getPrimerApellido().trim().equalsIgnoreCase(personaConsulta.getPrimerApellidoTrabajador())) ||

            (dataIn.getSegundoApellido() != null && !dataIn.getSegundoApellido().trim().isEmpty() 
                && !dataIn.getSegundoApellido().trim().equalsIgnoreCase(personaConsulta.getSegundoApellidoTrabajador()))
        ) {
            novedades.put("CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB", null);
        }
        //Para Novedad CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB
        if (dataIn.getFechaNto() != null && !dataIn.getFechaNto().trim().isEmpty()) {
            long epoch;
            try {    
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(dataIn.getFechaNto());
                epoch = fecha.getTime(); 
            } catch (ParseException e) {
                throw new TechnicalException("Formato de fecha inválido: " + dataIn.getFechaNto(), e);
            }
            if(epoch != personaConsulta.getFechaNacimientoTrabajador()){
                novedades.put("CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB", null);
            }   
        }
        //Para Novedad CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB
        if (
            // Email
            (dataIn.getEmail() != null && !dataIn.getEmail().trim().isEmpty() &&
            !dataIn.getEmail().trim().equalsIgnoreCase(personaConsulta.getCorreoElectronicoTrabajador())) ||
            // Municipio
            (dataIn.getMunicipio() != null && !dataIn.getMunicipio().trim().isEmpty() &&
            personaConsulta.getMunicipioTrabajador() != null &&
            (!Short.valueOf(dataIn.getMunicipio()).equals(personaConsulta.getMunicipioTrabajador().getIdMunicipio()) ||
            !Short.valueOf(dataIn.getDepartamento()).equals(personaConsulta.getMunicipioTrabajador().getIdDepartamento()))) ||
            // Dirección
            (dataIn.getDireccion() != null && !dataIn.getDireccion().trim().isEmpty() &&
            !dataIn.getDireccion().trim().equalsIgnoreCase(personaConsulta.getDireccionResidenciaTrabajador())) ||
            // Teléfono
            (dataIn.getTelefono() != null && !dataIn.getTelefono().trim().isEmpty() &&
            !dataIn.getTelefono().trim().equalsIgnoreCase(personaConsulta.getTelefonoFijoTrabajador())) ||
            (dataIn.getCelular() != null && !dataIn.getCelular().trim().isEmpty() &&
            !dataIn.getCelular().trim().equalsIgnoreCase(personaConsulta.getTelefonoCelularTrabajador()))
        ) {
            novedades.put("CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB", null);
        }
        // Novedad Cambio Pais
        if (dataIn.getPais() != null && !dataIn.getPais().trim().isEmpty()) {
            List<?> resultados = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_COD_PAIS)
                    .setParameter("nombrePais", dataIn.getPais())
                    .getResultList();

            if (!resultados.isEmpty()) {
                Long codPaisNuevo = ((Number) resultados.get(0)).longValue(); // casteo genérico
                if (personaConsulta.getPaisResidencia() == null || !codPaisNuevo.equals(personaConsulta.getPaisResidencia())) {
                    personaConsulta.setPaisResidencia(codPaisNuevo);
                    novedades.put("ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS_WEB", null);
                }
            }
        }
        // Novedad CAMBIAR_DATOS_CARACTERIZACION_POBLACION_WEB
        if ((dataIn.getOrientacionSexual() != null &&
                (personaConsulta.getOrientacionSexual() == null ||
                !dataIn.getOrientacionSexual().name().equals(personaConsulta.getOrientacionSexual().name())))
            ||
            (dataIn.getFactorVulnerabilidad() != null &&
                (personaConsulta.getFactorVulnerabilidad() == null ||
                !dataIn.getFactorVulnerabilidad().name().equals(personaConsulta.getFactorVulnerabilidad().name())))
            ||
            (dataIn.getPuebloIndigena() != null && !dataIn.getPuebloIndigena().trim().isEmpty())
            ||
            (dataIn.getPertenenciaEtnica() != null &&
                (personaConsulta.getPertenenciaEtnica() == null ||
                !dataIn.getPertenenciaEtnica().name().equals(personaConsulta.getPertenenciaEtnica().name())))) {

            // Resguardo
            if (dataIn.getResguardo() != null && !dataIn.getResguardo().trim().isEmpty()) {
                List<?> resultados = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_COD_RESGUARDO)
                        .setParameter("nombreResguardo", dataIn.getResguardo())
                        .getResultList();
                if (resultados != null && !resultados.isEmpty()) {
                    Long codResguardoNuevo = ((Number) resultados.get(0)).longValue();
                    if (personaConsulta.getIdResguardo() == null || !codResguardoNuevo.equals(personaConsulta.getIdResguardo())) {
                        personaConsulta.setIdResguardo(codResguardoNuevo);
                    }
                }
            }

            // Pueblo indígena
            if (dataIn.getPuebloIndigena() != null && !dataIn.getPuebloIndigena().trim().isEmpty()) {
                List<?> resultadosPueblos = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_COD_PUEBLO_INDIGENA)
                        .setParameter("nombrePuebloIndigena", dataIn.getPuebloIndigena())
                        .getResultList();
                if (resultadosPueblos != null && !resultadosPueblos.isEmpty()) {
                    Long codNuevo = ((Number) resultadosPueblos.get(0)).longValue();
                    if (personaConsulta.getIdPuebloIndigena() == null || !codNuevo.equals(personaConsulta.getIdPuebloIndigena())) {
                        personaConsulta.setIdPuebloIndigena(codNuevo);
                    }
                }
            }

            novedades.put("CAMBIAR_DATOS_CARACTERIZACION_POBLACION", null);
        }

        // Novedad CAMBIO_ESTADO_CIVIL_PERSONAS_WEB
        if (dataIn.getEstadoCivil() != null &&
            (personaConsulta.getEstadoCivilTrabajador() == null ||
            !dataIn.getEstadoCivil().equals(personaConsulta.getEstadoCivilTrabajador().name()))) {
            novedades.put("CAMBIO_ESTADO_CIVIL_PERSONAS_WEB", null);
            personaConsulta.setEstadoCivilTrabajador(dataIn.getEstadoCivil());
        }

        // Novedad CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB
        if ((dataIn.getNivelEscolaridad() != null && personaConsulta.getNivelEducativoTrabajador() != null &&
                !dataIn.getNivelEscolaridad().name().equals(personaConsulta.getNivelEducativoTrabajador().name()))
            || (dataIn.getProfesion() != null && !dataIn.getProfesion().trim().isEmpty())) {

            List<?> resultados = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_OCUPACION)
                    .setParameter("nombreOcupacion", dataIn.getProfesion())
                    .getResultList();

            if (resultados != null && !resultados.isEmpty()) {
                Integer codOcupacionNuevo = ((Number) resultados.get(0)).intValue();
                Integer codActual = (personaConsulta.getOcupacionProfesionTrabajador() != null)
                        ? personaConsulta.getOcupacionProfesionTrabajador().getIdOcupacionProfesion()
                        : null;

                if (codActual == null || !codOcupacionNuevo.equals(codActual)) {
                    OcupacionProfesion ocupacion = new OcupacionProfesion();
                    ocupacion.setIdOcupacionProfesion(codOcupacionNuevo);
                    personaConsulta.setOcupacionProfesionTrabajador(ocupacion);
                }
                novedades.put("CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB", null);
            }
        }

        //Novedad
        logger.info("[actualizarDatos] Inicio - Documento:"+documento+", TipoDoc: "+tipoDto+", Novedades potenciales: "+novedades.keySet());
        try{
            for (String novedad : novedades.keySet()){
                TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.valueOf(novedad);
                VerificarSolicitudesEnCurso validacion = new VerificarSolicitudesEnCurso(true,documento,null,tipoDto,tipoTransaccion,null,null);
                validacion.execute();
                String resultadoValidacion = validacion.getResult().getResultado().name();
                logger.info("IFNO VALIDACIONES: "+resultadoValidacion+ " Novedad: "+ novedad);
                novedades.put(novedad,resultadoValidacion);
                if ("APROBADA".equals(resultadoValidacion)||"NO_EVALUADA".equals(resultadoValidacion)) {
                    try {
                        SolicitudNovedadDTO solicitudDTO = armarSolicitudNovedadActualizarDatos(dataIn, personaConsulta,tipoTransaccion,userDTO);
                        RadicarSolicitudNovedad servicioRadicacion = new RadicarSolicitudNovedad(solicitudDTO);
                        servicioRadicacion.execute();
                        String estadoRadicacion = servicioRadicacion.getResult().getResultadoValidacion().name();
                        logger.info("Estado de la Radicacion: "+estadoRadicacion);
                        if(estadoRadicacion.equals("EXITOSA")){
                            logger.info("[actualizarDatos] Resultado radicación novedad "+novedad+": "+ estadoRadicacion);
                            logger.info(novedad +": "+ estadoRadicacion);
                        }else{
                            fallidas.add(novedad +": "+ estadoRadicacion);
                            validaciones.put(novedad,servicioRadicacion.getResult().getListResultadoValidacion().stream()
                                            .filter(v -> "NO_APROBADA".equals(v.getResultado().name()))
                                            .map(v -> v.getResultado().name()) 
                                            .collect(Collectors.toList())
                                    );

                        }           
                    } catch (Exception e) {
                        logger.warn("[actualizarDatos] Error radicando novedad"+novedad+" Error:"+e.getMessage()+ " Excepsion:"+e);
                        logger.error("[actualizarDatos] Error radicando novedad " + novedad, e);
                        fallidas.add(novedad + " Error en radicación");
                    }
                }else {
                    logger.info("Salta el if de la novedad aprovada.");
                    fallidas.add(novedad +": "+ resultadoValidacion );
                }
             }
        }
        catch (Exception e){
            logger.warn("Error procesando actualización de datos", e);
            ResponseDTO errorDTO = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_RADICACION, null);
            errorDTO.setMensajeError("Error procesando solicitud: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, errorDTO, entityManager, auditoriaIntegracionServicios);
            return Response.ok(errorDTO).build();
        }
        ResponseDTO response;
        if (fallidas.isEmpty()) {
            //Cuando todo esta OK
            response = new ResponseDTO();
            response.setMensajeError("");
            response.setData("OK");
        } else {
            //cuando todo resulto mal
            response = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_VALIDACION, null);
            response.setMensajeError("Una novedad se proceso con error. Errores: " + String.join(", ", fallidas));
            response.setData(validaciones);
        }
        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
        return Response.ok(response).build();

    }

    private SolicitudNovedadDTO armarSolicitudNovedadActualizarDatos(ActualizarDatosDTO dataIn,DatosPersonaNovedadDTO persona,TipoTransaccionEnum novedad,UserDTO userDTO) {
        SolicitudNovedadDTO dto = new SolicitudNovedadDTO();
        dto.setCanalRecepcion(CanalRecepcionEnum.WEB);
        dto.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
        dto.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE); 
        dto.setTipoTransaccion(novedad);
        dto.setObservaciones("N/A");
        // Datos de persona
        // Setear solo los campos que correspondan a la novedad
        if ("CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB".equals(novedad.name())) {
            if (dataIn.getPrimerNombre() != null && !dataIn.getPrimerNombre().trim().isEmpty()) persona.setPrimerNombreTrabajador(dataIn.getPrimerNombre());
            if (dataIn.getSegundoNombre() != null && !dataIn.getSegundoNombre().trim().isEmpty()) persona.setSegundoNombreTrabajador(dataIn.getSegundoNombre());
            if (dataIn.getPrimerApellido() != null && !dataIn.getPrimerApellido().trim().isEmpty()) persona.setPrimerApellidoTrabajador(dataIn.getPrimerApellido());
            if (dataIn.getSegundoApellido() != null && !dataIn.getSegundoApellido().trim().isEmpty()) persona.setSegundoApellidoTrabajador(dataIn.getSegundoApellido());
        }

        //  NOVEDAD: CAMBIO FECHA NACIMIENTO 
        else if ("CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB".equals(novedad.name())) {
            if (dataIn.getFechaNto() != null && !dataIn.getFechaNto().trim().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = sdf.parse(dataIn.getFechaNto());
                    persona.setFechaNacimientoTrabajador(fecha.getTime());
                } catch (ParseException e) {
                    throw new TechnicalException("Formato de fecha inválido: " + dataIn.getFechaNto(), e);
                }
            }
        }

        // NOVEDAD: CAMBIO DATOS CORRESPONDENCIA 
        else if ("CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB".equals(novedad.name())) {
            if (dataIn.getEmail() != null && !dataIn.getEmail().trim().isEmpty()) persona.setCorreoElectronicoTrabajador(dataIn.getEmail());
            if (dataIn.getMunicipio() != null && !dataIn.getMunicipio().trim().isEmpty()
                    && dataIn.getDepartamento() != null && !dataIn.getDepartamento().trim().isEmpty()) {
                Municipio nuevoMunicipio = new Municipio()
                        .setIdMunicipio2(Short.parseShort(dataIn.getMunicipio()))
                        .setIdDepartamento2(Short.parseShort(dataIn.getDepartamento()));
                persona.setMunicipioTrabajador(nuevoMunicipio);
            }
            if (dataIn.getDireccion() != null && !dataIn.getDireccion().trim().isEmpty()) persona.setDireccionResidenciaTrabajador(dataIn.getDireccion());
            if (dataIn.getTelefono() != null && !dataIn.getTelefono().trim().isEmpty()) persona.setTelefonoFijoTrabajador(dataIn.getTelefono());
            if (dataIn.getCelular() != null && !dataIn.getCelular().trim().isEmpty()) persona.setTelefonoCelularTrabajador(dataIn.getCelular());
        }

        //  NOVEDAD: CAMBIO DATOS CARACTERIZACIÓN POBLACION 
        else if ("CAMBIAR_DATOS_CARACTERIZACION_POBLACION".equals(novedad.name())) {
            if (dataIn.getOrientacionSexual() != null) persona.setOrientacionSexual(dataIn.getOrientacionSexual());
            if (dataIn.getFactorVulnerabilidad() != null) persona.setFactorVulnerabilidad(dataIn.getFactorVulnerabilidad());
            if (dataIn.getPertenenciaEtnica() != null) persona.setPertenenciaEtnica(dataIn.getPertenenciaEtnica());
        }

        //  NOVEDAD: CAMBIO ESTADO CIVIL 
        else if ("CAMBIO_ESTADO_CIVIL_PERSONAS_WEB".equals(novedad.name())) {
            if (dataIn.getEstadoCivil() != null) persona.setEstadoCivilTrabajador(dataIn.getEstadoCivil());
        }

        //  NOVEDAD: CAMBIO NIVEL EDUCATIVO / OCUPACION / PROFESION 
        else if ("CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB".equals(novedad.name())) {
            if (dataIn.getNivelEscolaridad() != null) persona.setNivelEducativoTrabajador(dataIn.getNivelEscolaridad());
        }

        // Usuario de radicacion 
        dto.setUsuarioRadicacion(dataIn.getUsuario() != null && !dataIn.getUsuario().trim().isEmpty()
                ? dataIn.getUsuario()
                : userDTO.getNombreUsuario());

        dto.setDatosPersona(persona);
        return dto;
    }

    @Override
    public Response validarEmpresa(ValidaEmpresaInDTO input,  HttpServletRequest requestContext, UserDTO userDTO){

        String firmaServicio = "validarEmpresa(validaEmpresaInDTO input)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        String tipoDto = input.getTipoIdentificacion().name();
        String numeroIdentificacion = input.getNumeroIdentificacion();
        Long idActividadEconomica = input.getCodigoActividad();

        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoDto", tipoDto != null ? tipoDto: "null");
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion != null ? numeroIdentificacion: "null");
        parametrosMetodo.put("idActividadEconomica", idActividadEconomica != null ? idActividadEconomica.toString(): "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        ResponseDTO respuesta = new ResponseDTO();
        try {
            if (tipoDto == null || tipoDto.isEmpty() || numeroIdentificacion == null || numeroIdentificacion.isEmpty() 
                || idActividadEconomica == null || idActividadEconomica <= 0) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
                logger.error("Parámetros incorrectos: "+numeroIdentificacion);
            } else {

                String resultado = (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDA_EMPRESA)
                    .setParameter("tipoDto", tipoDto)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("idActividadEconomica", idActividadEconomica)
                    .getSingleResult();
                
                respuesta.setMensajeError("");
                respuesta.setData(resultado);
            }
            
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            respuesta.setMensajeError(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.name());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            return Response.ok(respuesta).build();
        } 

        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, respuesta, entityManager, auditoriaIntegracionServicios);
        return Response.ok(respuesta).build();
    }
    
    @Override
    public Response reestablecerClaveUsuario(UsuarioDTO usuario,HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "Response reestablecerClaveUsuario(UsuarioDTO usuario)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("UsuarioDTO",usuario.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuario);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try{
            if(correoCorrespondePersona(usuario.getTipoID().name(),usuario.getIdentificacionUsuario(),usuario.getEmailUsuario()) && existePersonaEnSistema(usuario.getTipoID().name(),usuario.getIdentificacionUsuario())){
                RestablecerContrasenaPersona restablecerContrasena = new RestablecerContrasenaPersona(usuario.getEmailUsuario());
                restablecerContrasena.execute();
                response.setMensajeError("");
                response.setData("Se reestablecio la contraseña.");
            }else{
                response = new ResponseDTO(CodigosErrorWebservicesEnum.INCONSISTENCIA_GENERAL,null);
            }
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }catch(Exception e){
            logger.error("Error en orquestarAfiliacionEmpresa: " + e.getMessage(), e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok().entity(response).build();
        }
    }

    @Override
    public Response certificadoFosfec(ConsultaAfiliadoInDTO dataIn, HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "certificadoFosfec(ConsultaAfiliadoInDTO dataIn)";
        Instant start = Instant.now();
        TipoIdentificacionEnum tipoDto = dataIn.getTipoDto();
        String numeroIdentificacion = dataIn.getNumeroIdentificacion();
        CertificadoFosfecDTO certificadoDto = new CertificadoFosfecDTO();

        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoDto", tipoDto != null ? tipoDto.name(): "null");
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion != null ? numeroIdentificacion: "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                                    requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        ResponseDTO respuesta = new ResponseDTO();
        try{
            if (tipoDto == null || numeroIdentificacion == null ) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.name());
            }else{
                List<Object[]> resultados= (List<Object[]>)  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_CERTIFICADO_FOSFEC_WS)
                .setParameter("tipoDto", tipoDto.name())
                .setParameter("numeroIdentificacion",numeroIdentificacion)
                .getResultList();
                Object[] result = resultados.get(0);
                if(result != null){
                    logger.info("Inicio setteo: "+result);
                    certificadoDto.setTipoDto(tipoDto);
                    certificadoDto.setNumeroIdentificacion(numeroIdentificacion);
                    certificadoDto.setNombreTrabajador(result[2] != null ? result[2].toString() : "");
                    certificadoDto.setDiasCotizados(result[3] != null ? Integer.valueOf(result[3].toString()) : 0);
                    certificadoDto.setFechaUltimoRetiro(result[4] != null ? result[4].toString() : "");
                    certificadoDto.setUltimoPeriodoAporte(result[5] != null ? result[5].toString() : "");
                }
                respuesta.setMensajeError("");
                respuesta.setData(certificadoDto);
            }
            
        }catch(Exception e){
            logger.error("Error: ",e);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            respuesta = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            return Response.ok(respuesta).build();
        }
        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, respuesta, entityManager, auditoriaIntegracionServicios);
        logger.info("Fin del proceso certificadoFosfec(ConsultaAfiliadoInDTO dataIn)");
        return Response.ok(respuesta).build();

    }

    private Boolean existePersonaEnSistema(String tipoIdentificacion, String numeroIdentificacion){
        try{
            Object[] datosConsulta = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTE_PERSONA)
                            .setParameter("numeroIdentificacion",numeroIdentificacion)
                            .setParameter("tipoIdentificacion",tipoIdentificacion)
                            .getSingleResult();
            return datosConsulta[0].toString() == "1" ? Boolean.TRUE : Boolean.FALSE;
        }catch(Exception e){    
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private Boolean correoCorrespondePersona(String tipoIdentificacion, String numeroIdentificacion,String email){
        try{
            Object[] datosConsulta = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.COINSIDEN_DATOS_REGISTRADOS)
                            .setParameter("numeroIdentificacion",numeroIdentificacion)
                            .setParameter("tipoIdentificacion",tipoIdentificacion)
                            .setParameter("email",email)
                            .getSingleResult();
            return datosConsulta[0].toString() == "1" ? Boolean.TRUE : Boolean.FALSE;
        }catch(Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Response orquestadorCreacionUsuario(UsuarioDTO usuario, HttpServletRequest requestContext, UserDTO userDTO){
        String firmaServicio = "Response orquestadorCreacionUsuario(UsuarioDTO usuario, HttpServletRequest requestContext, UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("UsuarioDTO",usuario.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuario);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try {
            switch (usuario.getTipoAfiliado()) {
                case EMPLEADOR:
                    GestionCrearEmpleador crearEmpleador = new GestionCrearEmpleador(construirUsuarioCreacion(usuario));
                    crearEmpleador.execute();
                    response.setMensajeError("");
                    response.setData("Usuario registrado");
                    break;
                case PERSONA:
                    GestionCrearPersona crearPersona = new GestionCrearPersona(construirUsuarioCreacion(usuario));
                    crearPersona.execute();
                    response.setMensajeError("");
                    response.setData("Usuario registrado");
                    break;
                default:
                    break;
            }
            return Response.ok().entity(response).build();
        }catch (NoResultException e) {
            logger.error("Error: ",e);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, response, entityManager, auditoriaIntegracionServicios);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INCONSISTENCIA_GENERAL,null);
            return Response.ok(response).build();
        }
        catch (Exception e) {
            logger.error("Error: ",e);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            return Response.ok(response).build();
        }
    }

    private UsuarioCCF construirUsuarioCreacion(UsuarioDTO usuario){
        logger.info("Inicia UsuarioCCF construirUsuarioCreacion(UsuarioDTO usuario)");
        try {
            UsuarioCCF usuarioC = new UsuarioCCF();
            usuarioC.setEmail(usuario.getEmailUsuario());
            usuarioC.setTipoIdentificacion(usuario.getTipoID().name());
            usuarioC.setNumIdentificacion(usuario.getIdentificacionUsuario());

            Object[] datosPersona = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSUTLA_BASICOS_USUARIO).setParameter("tipoIdentificacion", usuario.getTipoID().name()).setParameter("numeroIdentificacion", usuario.getIdentificacionUsuario()).getSingleResult();

            usuarioC.setPrimerNombre((datosPersona[0] == null || datosPersona[0].toString() == null) ? "":datosPersona[0].toString());
            usuarioC.setSegundoNombre((datosPersona[1] == null || datosPersona[1].toString() == null) ? "":datosPersona[1].toString());
            usuarioC.setPrimerApellido((datosPersona[2] == null || datosPersona[2].toString() == null) ? "":datosPersona[2].toString());
            usuarioC.setSegundoApellido((datosPersona[3] == null || datosPersona[3].toString() == null) ? "":datosPersona[3].toString());
            logger.info("Finaliza UsuarioCCF construirUsuarioCreacion(UsuarioDTO usuario)");
            return usuarioC;
        } catch (Exception e) {
            logger.error("ocurrio una exepcion UsuarioCCF parametros:"+ usuario.toString());
            throw e;
        }
    }

    @Override
    public Response actualizarDatosUsuario(ActualizarDatosUsuarioDTO dataIn, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "actualizarDatosUsuario(ActualizarDatosUsuarioDTO dataIn)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        ResponseDTO response = new ResponseDTO();
       HashMap<String, String> parametrosMetodo = new HashMap<>();
        parametrosMetodo.put("tipoDcto", dataIn.getTipoDcto() != null ? dataIn.getTipoDcto().name() : "null");
        parametrosMetodo.put("documento", dataIn.getDocumento() != null ? String.valueOf(dataIn.getDocumento()) : "null");
        parametrosMetodo.put("usuario", dataIn.getUsuario() != null ? dataIn.getUsuario().name() : "null");
        parametrosMetodo.put("email", dataIn.getEmail() != null ? dataIn.getEmail() : "null");
        parametrosMetodo.put("telefono", dataIn.getTelefono() != null ? dataIn.getTelefono() : "null");

        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios =
                AuditoriaIntegracionServicios.integracionServicios(
                        requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        try {
            UsuarioCCF usuario = new UsuarioCCF();
            String emailRollback = "";
            TiposUsuarioWebServiceEnum tipoUsuario = dataIn.getUsuario();
            switch (tipoUsuario.name()) {
                case "USUARIO_PERSONA":
                    // Consultar persona
                    GestionConsultarPersona consultaPersona = new GestionConsultarPersona(
                            dataIn.getTipoDcto(), String.valueOf(dataIn.getDocumento()), null, null, null, null);
                    consultaPersona.execute();
                    List<UsuarioGestionDTO> usuariosPersona = consultaPersona.getResult();
                    if (usuariosPersona == null || usuariosPersona.isEmpty()) {
                        return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_NO_ENCONTRADO,
                                                "Usuario persona no encontrado.", start, auditoriaIntegracionServicios);
                    }
                    usuario = constuirUsuarioCCF(usuariosPersona.get(0));
                    emailRollback = usuariosPersona.get(0).getEmail();
                    // Actualizar en Keycloak
                    GestionActualizarPersona actualizarPersona = new GestionActualizarPersona(usuario);
                    actualizarPersona.execute();

                    if (actualizarPersona.getResult().getError()) {
                        return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_NO_ENCONTRADO,
                                                "Error al actualizar usuario en Keycloak.", start, auditoriaIntegracionServicios);
                    }
                    // Actualizar en Genesys
                    ActualizarDatosDTO actualizarDatosGenesys = construirActualizarDatosDTO(dataIn);
                    Response resultadoGenesys = actualizarDatos(actualizarDatosGenesys, requestContext, userDTO);
                    ResponseDTO resultadoDTO = new ResponseDTO();
                    try {
                        if (resultadoGenesys.getStatus() == 200) {
                            Object entity = resultadoGenesys.getEntity();
                            if (entity instanceof ResponseDTO) {
                                resultadoDTO = (ResponseDTO) entity; // casteo directo
                            }
                            logger.info("Resultado Genesys: " + resultadoDTO);
                        } 
                    } finally {
                        resultadoGenesys.close();
                    }
                    if (resultadoDTO != null && "OK".equals(resultadoDTO.getData())) {
                        response.setMensajeError("");
                        response.setData("Actualización procesada correctamente.");
                        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, resultadoDTO.getData(), entityManager, auditoriaIntegracionServicios);
                        return Response.ok(response).build();
                    }
                    // 4. Rollback en KC si falla Genesys
                    rollbackEmail(usuario, emailRollback);
                    return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_INACTIVO,
                                            "Falló la actualización en Genesys.", start, auditoriaIntegracionServicios);
                case "USUARIO_EMPRESA":
                    GestionConsultarEmpleador consultaEmpresa = new GestionConsultarEmpleador(
                            dataIn.getTipoDcto(), String.valueOf(dataIn.getDocumento()), null, null, null, null);
                    consultaEmpresa.execute();
                    List<UsuarioGestionDTO> usuariosEmpresa = consultaEmpresa.getResult();

                    if (usuariosEmpresa == null || usuariosEmpresa.isEmpty()) {
                        return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_NO_ENCONTRADO,
                                                "Usuario empresa no encontrado.", start, auditoriaIntegracionServicios);
                    }
                    usuario = constuirUsuarioCCF(usuariosEmpresa.get(0));
                    emailRollback = usuariosEmpresa.get(0).getEmail();
                    // Actualizar en Keycloak
                    GestionActualizarEmpleador actualizarEmpresa = new GestionActualizarEmpleador(usuario);
                    actualizarEmpresa.execute();
                    if (actualizarEmpresa.getResult().getError()) {
                        return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_NO_ENCONTRADO,
                                                "Error al actualizar usuario en Keycloak (Empresa).", start, auditoriaIntegracionServicios);
                    }else{
                        response.setMensajeError("");
                        response.setData("Actualización procesada correctamente (Empresa).");
                        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(
                            start, null, "OK", entityManager, auditoriaIntegracionServicios);
                        return Response.ok(response).build();
                    }
                    /* //Actualizar en Genesys
                    ActualizarDatosDTO actualizarDatosGenesysEmp = construirActualizarDatosDTO(dataIn);
                    Response resultadoGenesysEmp = actualizarDatos(actualizarDatosGenesysEmp, requestContext, userDTO);
                    ResponseDTO resultadoDTOEmp = new ResponseDTO();
                    try {
                        if (resultadoGenesysEmp.getStatus() == 200) {
                            Object entity = resultadoGenesysEmp.getEntity();
                            if (entity instanceof ResponseDTO) {
                                resultadoDTOEmp = (ResponseDTO) entity; // casteo directo
                            }
                            logger.info("Resultado Genesys Empresa: " + resultadoDTOEmp);
                        }
                    } finally {
                        resultadoGenesysEmp.close();
                    }

                    if (resultadoDTOEmp != null && "OK".equals(resultadoDTOEmp.getData())) {
                        response.setData("Actualización procesada correctamente (Empresa).");
                        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(
                            start, null, resultadoDTOEmp.getData(), entityManager, auditoriaIntegracionServicios);
                        return Response.ok(response).build();
                    }

                    // Rollback en KC si falla Genesys
                    rollbackEmail(usuario, emailRollback); 
                    return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_INACTIVO,
                                            "Falló la actualización en Genesys (Empresa).", start, auditoriaIntegracionServicios);*/

                default:
                    return buildErrorResponse(CodigosErrorWebservicesEnum.USUARIO_NO_ENCONTRADO,
                                            "Tipo de usuario no soportado.", start, auditoriaIntegracionServicios);
            }

        } catch (Exception e) {
            logger.error("Error en actualizarDatosUsuario", e);
            return buildErrorResponse(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,
                                    "Error técnico: " + e.getMessage(), start, auditoriaIntegracionServicios);
        }
    }

    // Método auxiliar para rollback
    private void rollbackEmail(UsuarioCCF usuario, String emailRollback) {
        usuario.setEmail(emailRollback);
        new GestionActualizarPersona(usuario).execute();
    }

    // Método auxiliar para centralizar respuestas de error
    private Response buildErrorResponse(CodigosErrorWebservicesEnum codigo, String mensaje,
                                        Instant start, AuditoriaIntegracionServicios auditoria) {
        ResponseDTO response = new ResponseDTO(codigo, null);
        response.setMensajeError(mensaje);
        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, mensaje, entityManager, auditoria);
        return Response.ok(response).build();
    }


    private UsuarioCCF constuirUsuarioCCF(UsuarioGestionDTO consulta){
        UsuarioCCF usuario = new UsuarioCCF();
        usuario.setIdUsuario(consulta.getIdUsuario());
        usuario.setNombreUsuario(consulta.getNombreUsuario());
        usuario.setPrimerNombre(consulta.getPrimerNombre());
        usuario.setSegundoNombre(consulta.getSegundoNombre());
        usuario.setPrimerApellido(consulta.getPrimerApellido());
        usuario.setSegundoApellido(consulta.getSegundoApellido());
        usuario.setEmail(consulta.getEmail());
        usuario.setTipoIdentificacion(consulta.getTipoIdentificacion());
        usuario.setNumIdentificacion(consulta.getNumIdentificacion());

        logger.info("Construyendo objeto UsuarioCCF para actualización:");
        logger.info("  ID de usuario: "+ usuario.getIdUsuario());
        logger.info("  Nombre de usuario: "+ usuario.getNombreUsuario());
        logger.info("  Primer nombre: "+ usuario.getPrimerNombre());
        logger.info("  Segundo nombre: "+ usuario.getSegundoNombre());
        logger.info("  Primer apellido: "+ usuario.getPrimerApellido());
        logger.info("  Segundo apellido: "+ usuario.getSegundoApellido());
        logger.info("  Email: "+ usuario.getEmail());
        logger.info("  Tipo de identificación: "+ usuario.getTipoIdentificacion());
        logger.info("  Número de identificación: "+ usuario.getNumIdentificacion());
        return usuario;
    } 

    // Método auxiliar para construir el DTO de Genesys
    private ActualizarDatosDTO construirActualizarDatosDTO(ActualizarDatosUsuarioDTO dataIn) {
        ActualizarDatosDTO dto = new ActualizarDatosDTO();
        dto.setTipoDcto(dataIn.getTipoDcto());
        dto.setDocumento(dataIn.getDocumento());
        dto.setEmail(dataIn.getEmail());
        dto.setTelefono(dataIn.getTelefono());
        return dto;
    }

    @Override
    public Response redireccionamientoAutenticado(RedireccionarInDTO dataIn, HttpServletRequest requestContext, UserDTO usuario) {

        String firmaServicio = "redireccionamientoAutenticado(tipoIdentificacion, numeroDoc)";

        String numeroDoc = dataIn.getNumeroIdentificacion();
        String tipoIdentificacion = dataIn.getTipoDto() != null ? dataIn.getTipoDto().name() : null;

        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("UsuarioDTO",usuario.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, usuario.getEmail());

        ResponseDTO respuesta = new ResponseDTO();
        AuthenticationResultDTO authResult = null;

        try {
            if (tipoIdentificacion == null || numeroDoc == null || numeroDoc.isEmpty()) {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS.getCodigoError());
                respuesta.setMensajeError("Parámetros incorrectos: tipoIdentificacion o numeroDoc vacíos");
                return Response.ok(respuesta).build();
            }

            Object[] empresa = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIGITO_VERIFICACION_EMPRESA)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroDoc)
                .getSingleResult();

            if(empresa != null && empresa.length > 0) {

                Short digitoVerificacion = empresa[0] != null ? Short.valueOf(empresa[0].toString()) : null;
                String estadoEmpresa = empresa[1] != null ? empresa[1].toString() : null;

                if(estadoEmpresa.equals("ACTIVO")) {

                    logger.info("Empresa activa encontrada");
                    String tokenAutenticado = generarTokenAutenticado(dataIn.getTipoDto(), numeroDoc, digitoVerificacion);

                    String urlAmbiente = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_URL_AMBIENTE)
                        .getSingleResult();

                    String url = urlAmbiente + "/web-application";

                    authResult = new AuthenticationResultDTO(tokenAutenticado, url);
                    respuesta.setData(authResult);
                    respuesta.setMensajeError("");
                    return Response.ok(respuesta).build();
                }else {
                    respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.EMPRESA_NO_SE_ENCUENTRA_ACTIVA.getCodigoError());
                    respuesta.setMensajeError("Empresa no se encuentra activa");
                    return Response.ok(respuesta).build();
                }

            }else {
                respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.EMPRESA_NO_ENCONTRADA.getCodigoError());
                respuesta.setMensajeError("Empresa no encontrada para el representante legal proporcionado");
                return Response.ok(respuesta).build();
            }

        } catch (Exception e) {
            respuesta.setCodigoRespuesta(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR.getCodigoError());
            respuesta.setMensajeError("Error interno: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, respuesta, entityManager, auditoriaIntegracionServicios);
            return Response.serverError().entity(respuesta).build();
        }
    }

    private String generarTokenAutenticado(TipoIdentificacionEnum tipoIdentificacion, String numeroDoc, Short digitoVerificacion){

        GenerarTokenAcceso tokenAutenticado = new GenerarTokenAcceso(digitoVerificacion, numeroDoc, tipoIdentificacion);
        tokenAutenticado.execute();
        return tokenAutenticado.getResult().getToken();

    }

    @Override
    public Response afiliaTrabajadorDependiente(AfiliaTrabajadorDepDTO afiliadoIn,HttpServletRequest requestContext,UserDTO userDTO){
        String firmaServicio = "afiliaTrabajadorDependiente(AfiliaTrabajadorDepDTO afiliadoIn,HttpServletRequest requestContext,UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("AfiliaTrabajadorDepDTO",afiliadoIn.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<AfiliaTrabajadorDepDTO>> violations = validator.validate(afiliadoIn);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try {
            VerificarSolicitudesEnCurso validacion = new VerificarSolicitudesEnCurso(false,afiliadoIn.getNumeroId(),null,afiliadoIn.getTipoId(),null,null,null);
                validacion.execute();
            if(validacion.getResult().getResultado().name() == "APROBADA" || validacion.getResult().getResultado().name() == "NO_EVALUADA"){

                AfiliadoInDTO afiliado = new AfiliadoInDTO();

                EmpleadorModeloDTO empleador = consultarInformacionEmpleadorDTO(afiliadoIn.getTipoIdentificacionEmpleador().name(),afiliadoIn.getNit(),afiliadoIn.getRazonSocialEmpleador());

                AfiliarTrabajadorCandidatoDTO candidato = construirTrabajadorCandidato(afiliadoIn,empleador);

                AfiliarTrabajador afiliarTrabajador = new AfiliarTrabajador(empleador.getIdEmpleador(),candidato);
                afiliarTrabajador.execute();

                afiliado = afiliarTrabajador.getResult();

                candidato = actualizarCandidato(candidato,afiliado);
                AfiliarTrabajador afiliarTrabajador2 = new AfiliarTrabajador(empleador.getIdEmpleador(),candidato);
                afiliarTrabajador2.execute();

                afiliado = afiliarTrabajador2.getResult();

                IniciarVerificarInformacionSolicitud iniciarVerificacion = new IniciarVerificarInformacionSolicitud(empleador.getIdEmpleador(),afiliado.getIdInstanciaProceso(),candidato);
                iniciarVerificacion.execute();

                return Response.ok(response).build();
            }else{
                response = new ResponseDTO(CodigosErrorWebservicesEnum.TRABAJADOR_NO_AFILIABLE,validacion.getResult().getDetalle());
            }
            return Response.ok(response).build();
        }catch (Exception e) {
            logger.error("Error: ",e);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            return Response.ok(response).build();
        }
        // return Response.ok(response).build();
    }

    private AfiliarTrabajadorCandidatoDTO construirTrabajadorCandidato(AfiliaTrabajadorDepDTO afiliadoIn,EmpleadorModeloDTO empleador){
        AfiliarTrabajadorCandidatoDTO candidato = new AfiliarTrabajadorCandidatoDTO();
        candidato.setAfiliadoInDTO(new AfiliadoInDTO());
        candidato.getAfiliadoInDTO().setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        candidato.getAfiliadoInDTO().setPersona(new PersonaDTO());
        candidato.getAfiliadoInDTO().getPersona().setNumeroIdentificacion(afiliadoIn.getNumeroId());
        candidato.getAfiliadoInDTO().getPersona().setTipoIdentificacion(afiliadoIn.getTipoId());
        candidato.getAfiliadoInDTO().getPersona().setPrimerNombre(afiliadoIn.getPrimerNombre());
        candidato.getAfiliadoInDTO().getPersona().setPrimerApellido(afiliadoIn.getPrimerApellido());
        candidato.getAfiliadoInDTO().getPersona().setSegundoApellido(afiliadoIn.getSegundoApellido() == null ? "" : afiliadoIn.getSegundoApellido());
        candidato.getAfiliadoInDTO().getPersona().setSegundoNombre(afiliadoIn.getSegundoNombre() == null ? "" : afiliadoIn.getSegundoNombre());
        candidato.getAfiliadoInDTO().getPersona().setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
        candidato.getAfiliadoInDTO().getPersona().setCabezaHogar(Boolean.FALSE);
        candidato.getAfiliadoInDTO().getPersona().setAutorizacionEnvioEmail(Boolean.TRUE);
        candidato.getAfiliadoInDTO().setCanalRecepcion(CanalRecepcionEnum.WEB);
        // empieza estructurar consulta
        candidato.getAfiliadoInDTO().setIdEmpleador(empleador.getIdEmpleador());
        candidato.getAfiliadoInDTO().setSucursalEmpleadorId(empleador.getIdSucursalEmpleador());
        candidato.getAfiliadoInDTO().setTipoEmpleador(empleador.getClasificacion());

        candidato.setIdentificadorUbicacionPersona(new IdentificacionUbicacionPersonaDTO());
        candidato.getIdentificadorUbicacionPersona().setPersona(candidato.getAfiliadoInDTO().getPersona());
        // candidato.setUbicacion(new UbicacionDTO());
        // candidato.getUbicacion().
        // candidato.setAutorizacionDatosPersonales(Boolean.TRUE);
        // candidato.setResideSectorRural(Boolean.FALSE);

        candidato.setInformacionLaboralTrabajador(new InformacionLaboralTrabajadorDTO());
        // candidato.getInformacionLaboralTrabajador().setClaseTrabajador(afiliadoIn.getClasificacion());
        candidato.getInformacionLaboralTrabajador().setFechaInicioContrato(afiliadoIn.getFechaIngreso());
        candidato.getInformacionLaboralTrabajador().setValorSalario(afiliadoIn.getSueldo());
        candidato.setIniciarProceso(Boolean.TRUE);

        candidato.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);

        return candidato;
    }

    private EmpleadorModeloDTO consultarInformacionEmpleadorDTO(String tipoDocumento, String numeroDocumento, String razonSocial){
        Object[] v = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_AFILIACION).setParameter("numeroIdentificacion",numeroDocumento).setParameter("tipoIdentificacion",tipoDocumento).setParameter("razonSocial",razonSocial).getSingleResult();
        EmpleadorModeloDTO empleador = new EmpleadorModeloDTO();
        empleador.setIdEmpleador(Long.valueOf(v[0].toString()));
        empleador.setIdSucursalEmpleador(Long.valueOf(v[1].toString()));
        empleador.setClasificacion(ClasificacionEnum.valueOf(v[2].toString()));
        return empleador;
    }

    private AfiliarTrabajadorCandidatoDTO actualizarCandidato(AfiliarTrabajadorCandidatoDTO candidato,AfiliadoInDTO afiliado){
        candidato.setIdSolicitudGlobal(afiliado.getIdSolicitudGlobal());
        candidato.getAfiliadoInDTO().setIdAfiliado(afiliado.getIdAfiliado());
        candidato.getAfiliadoInDTO().setIdRolAfiliado(afiliado.getIdRolAfiliado());
        candidato.getAfiliadoInDTO().setNumeroRadicado(afiliado.getNumeroRadicado());
        candidato.getAfiliadoInDTO().setIdInstanciaProceso(afiliado.getIdInstanciaProceso());
        return candidato;
    }

    @Override
    public Response consultarSolicitudAfiliacionEmpresa(String numeroRadicado,HttpServletRequest requestContext,UserDTO userDTO){
        String firmaServicio = "consultarSolicitudAfiliacionEmpresa(String numeroRadicado,HttpServletRequest requestContext,UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("numeroRadicado",numeroRadicado);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        if (numeroRadicado.isEmpty()) {
            String error = "El numero de radicado es obligatorio.";
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, error);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try {
            logger.warn(">>>>>>>>> NUMERO RADICACION SOLICITUD"+numeroRadicado);
            Object s = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_AFILIACION_EMPRESA)
                .setParameter("numeroRadicado",numeroRadicado).getSingleResult();

            response.setMensajeError("");
            response.setData(EstadoSolicitudAfiliacionEmpleadorEnum.valueOf(s.toString()).getDescripcion());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, s.toString(), entityManager, auditoriaIntegracionServicios);
            return Response.ok(response).build();
        }catch (NoResultException e) {
            e.printStackTrace();
            response = new ResponseDTO(CodigosErrorWebservicesEnum.NO_EXISTE_PROCESO,null);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok(response).build();
        }
    }

    @Override
    public Response consultarEstadoCargueMasivo(ConsultaCargueDTO datosCargue,HttpServletRequest requestContext,UserDTO userDTO){
        String firmaServicio = "consultarSolicitudAfiliacionEmpresa(String numeroRadicado,HttpServletRequest requestContext,UserDTO userDTO)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("ConsultaCargueDTO",datosCargue.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<ConsultaCargueDTO>> violations = validator.validate(datosCargue);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try{
            switch (datosCargue.getTipoConsulta()) {
                case 1:
                    if(datosCargue.getNumeroRadicado() == null || datosCargue.getNumeroRadicado() == ""){
                        response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, "El campo numeroRadicado es obligatorio.");
                        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
                    }
                    break;
                case 2:
                    if(datosCargue.getIdCargue() == null){
                        response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, "El campo idCargue es obligatorio.");
                        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
                    }
                    break;
                default:
                    response = new ResponseDTO(CodigosErrorWebservicesEnum.INCONSISTENCIA_GENERAL, "");
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
            }
            response.setMensajeError("");
            response.setData(consultarInformacionSolicitudes(datosCargue.getNumeroRadicado(),datosCargue.getIdCargue()));
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, "", entityManager, auditoriaIntegracionServicios);
            return Response.ok(response).build();
        }catch(Exception e){
            e.printStackTrace();
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.ok(response).build();
        }
    }

    @Override
    public Response actualizarDatosEmpleador(ActualizarDatosEmpleadorInDTO dataIn, HttpServletRequest requestContext, UserDTO userDTO) {
        String firmaServicio = "actualizarDatosEmpleador(ActualizarDatosEmpleadorInDTO dataIn)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        TipoIdentificacionEnum tipoDto = dataIn.getTipoDcto();
        String documento = String.valueOf(dataIn.getDocumento());

        Map<String, String> novedades = new HashMap<>();
        List<String> fallidas = new ArrayList<>();
        Map<String, List<String>> validaciones = new HashMap<>();
        HashMap<String, String> parametrosMetodo = new HashMap<>();

        parametrosMetodo.put("tipoDcto", tipoDto != null ? tipoDto.name() : "null");
        parametrosMetodo.put("documento", documento != null ? documento : "null");
        parametrosMetodo.put("tipoDctoRL", dataIn.getTipoDctoRL() != null ? dataIn.getTipoDctoRL().name() : "null");
        parametrosMetodo.put("documentoRL", dataIn.getDocumentoRL() != null ? String.valueOf(dataIn.getDocumentoRL()) : "null");
        parametrosMetodo.put("primerNombreRL", dataIn.getPrimerNombreRL() != null ? dataIn.getPrimerNombreRL() : "null");
        parametrosMetodo.put("segundoNombreRL", dataIn.getSegundoNombreRL() != null ? dataIn.getSegundoNombreRL() : "null");
        parametrosMetodo.put("primerApellidoRL", dataIn.getPrimerApellidoRL() != null ? dataIn.getPrimerApellidoRL() : "null");
        parametrosMetodo.put("segundoApellidoRL", dataIn.getSegundoApellidoRL() != null ? dataIn.getSegundoApellidoRL() : "null");
        parametrosMetodo.put("correoElectronicoRL", dataIn.getCorreoElectronicoRL() != null ? dataIn.getCorreoElectronicoRL() : "null");
        parametrosMetodo.put("numCelularRL", dataIn.getNumCelularRL() != null ? dataIn.getNumCelularRL() : "null");
        parametrosMetodo.put("numTelefonoRL", dataIn.getNumTelefonoRL() != null ? dataIn.getNumTelefonoRL() : "null");

        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);

        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());

        DatosEmpleadorNovedadDTO empleador;
        try {
            empleador = construirEmpleadorNovedad(tipoDto, documento);
        } catch (Exception e) {
            ResponseDTO errorDTO = new ResponseDTO(CodigosErrorWebservicesEnum.EMPLEADOR_NO_EXISTE, null);
            errorDTO.setMensajeError("Error procesando solicitud: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, errorDTO, entityManager, auditoriaIntegracionServicios);
            return Response.ok(errorDTO).build();
        }

       if ((dataIn.getPrimerNombreRL() != null && !dataIn.getPrimerNombreRL().trim().isEmpty() 
                && !dataIn.getPrimerNombreRL().equalsIgnoreCase(empleador.getPrimerNombreRepLegal())) 
            || (dataIn.getSegundoNombreRL() != null && !dataIn.getSegundoNombreRL().trim().isEmpty() 
                && !dataIn.getSegundoNombreRL().equalsIgnoreCase(empleador.getSegundoNombreRepLegal())) 
            || (dataIn.getPrimerApellidoRL() != null && !dataIn.getPrimerApellidoRL().trim().isEmpty() 
                && !dataIn.getPrimerApellidoRL().equalsIgnoreCase(empleador.getPrimerApellidoRepLegal())) 
            || (dataIn.getSegundoApellidoRL() != null && !dataIn.getSegundoApellidoRL().trim().isEmpty() 
                && !dataIn.getSegundoApellidoRL().equalsIgnoreCase(empleador.getSegundoApellidoRepLegal())) 
            || (dataIn.getCorreoElectronicoRL() != null && !dataIn.getCorreoElectronicoRL().trim().isEmpty() 
                && !dataIn.getCorreoElectronicoRL().equalsIgnoreCase(empleador.getEmailRepLegal())) 
            || (dataIn.getNumCelularRL() != null && !dataIn.getNumCelularRL().trim().isEmpty() 
                && !dataIn.getNumCelularRL().equalsIgnoreCase(empleador.getTelefonoCelularRepLegal())) 
            || (dataIn.getNumTelefonoRL() != null && !dataIn.getNumTelefonoRL().trim().isEmpty() 
                && !dataIn.getNumTelefonoRL().equalsIgnoreCase(empleador.getTelefonoFijoRepLegal()))) {
            
            novedades.put("CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB", null);
        }

        if (dataIn.getNombreRazonSocial() != null && !dataIn.getNombreRazonSocial().trim().isEmpty() 
                && !dataIn.getNombreRazonSocial().equalsIgnoreCase(empleador.getRazonSocial())) {
            novedades.put("CAMBIO_RAZON_SOCIAL_NOMBRE", null);
        }

        if (dataIn.getUbicacionACambiar() != null) {
            if ("DATOS_OFICINA_PRINCIPAL".equals(dataIn.getUbicacionACambiar().name())) {
                novedades.put("ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB", null);
            } else if ("DATOS_ENVIO_CORRESPONDENCIA".equals(dataIn.getUbicacionACambiar().name())) {
                novedades.put("ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB", null);
            } else if ("DATOS_NOTIFICACION_JUDICIAL".equals(dataIn.getUbicacionACambiar().name())) {
                novedades.put("ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB", null);
            }
        }


        // Procesar novedades
        try {
            for (String novedad : novedades.keySet()) {
                TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.valueOf(novedad);
                VerificarSolicitudesEnCurso validacion = new VerificarSolicitudesEnCurso(true, documento, null, tipoDto, tipoTransaccion, null, null);
                validacion.execute();
                String resultadoValidacion = validacion.getResult().getResultado().name();
                novedades.put(novedad, resultadoValidacion);

                if ("APROBADA".equals(resultadoValidacion) || "NO_EVALUADA".equals(resultadoValidacion)) {
                    try {
                        SolicitudNovedadDTO solicitudDTO = armarSolicitudNovedadEmpleador(dataIn, empleador, tipoTransaccion, userDTO);
                        RadicarSolicitudNovedad servicioRadicacion = new RadicarSolicitudNovedad(solicitudDTO);
                        servicioRadicacion.execute();

                        String estadoRadicacion = servicioRadicacion.getResult().getResultadoValidacion().name();
                        if ("EXITOSA".equals(estadoRadicacion)) {
                            logger.info("[actualizarDatosEmpleador] Resultado radicación novedad " + novedad + ": " + estadoRadicacion);
                        } else {
                            fallidas.add(novedad + ": " + estadoRadicacion);
                            validaciones.put(novedad, servicioRadicacion.getResult().getListResultadoValidacion().stream()
                                    .filter(v -> "NO_APROBADA".equals(v.getResultado().name()))
                                    .map(v -> v.getResultado().name())
                                    .collect(Collectors.toList()));
                        }
                    } catch (Exception e) {
                        logger.error("[actualizarDatosEmpleador] Error radicando novedad " + novedad, e);
                        fallidas.add(novedad + " Error en radicación");
                    }
                } else {
                    fallidas.add(novedad + ": " + resultadoValidacion);
                }
            }
        } catch (Exception e) {
            ResponseDTO errorDTO = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_RADICACION, null);
            errorDTO.setMensajeError("Error procesando solicitud: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, errorDTO, entityManager, auditoriaIntegracionServicios);
            return Response.ok(errorDTO).build();
        }

        ResponseDTO response;
        if (fallidas.isEmpty()) {
            response = new ResponseDTO();
            response.setMensajeError("");
            response.setData("OK");
        } else {
            response = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_VALIDACION, null);
            response.setMensajeError("Una o más novedades se procesaron con error. Errores: " + String.join(", ", fallidas));
            response.setData(validaciones);
        }

        AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
        return Response.ok(response).build();
    }


    private DatosEmpleadorNovedadDTO construirEmpleadorNovedad(TipoIdentificacionEnum tipoDocumento, String numeroIdentificacion) {
        try {
            Object[] datosConsulta = (Object[]) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_EMPLEADOR_NOVEDAD)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoDocumento.name()) 
                    .getSingleResult();

            logger.info("Consulta empleador ejecutada, columnas: " + datosConsulta.length);
            DatosEmpleadorNovedadDTO empNovedad = new DatosEmpleadorNovedadDTO();
            empNovedad.setIdEmpleador(getLong(datosConsulta[0]));
            empNovedad.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(getString(datosConsulta[1])));
            empNovedad.setNumeroIdentificacion(getString(datosConsulta[2]));
            empNovedad.setDigitoVerificacion(getShort(datosConsulta[3]));
            empNovedad.setTipoIdentificacionNuevo(TipoIdentificacionEnum.valueOf(getString(datosConsulta[4])));
            empNovedad.setNumeroIdentificacionNuevo(getString(datosConsulta[5]));
            empNovedad.setDigitoVerificacionNuevo(getShort(datosConsulta[6]));
            empNovedad.setRazonSocial(getString(datosConsulta[7]));
            empNovedad.setNombreComercial(getString(datosConsulta[8]));
            empNovedad.setPrimerNombre(getString(datosConsulta[9]));
            empNovedad.setSegundoNombre(getString(datosConsulta[10]));
            empNovedad.setPrimerApellido(getString(datosConsulta[11]));
            empNovedad.setSegundoApellido(getString(datosConsulta[12]));
            empNovedad.setNaturalezaJuridica(NaturalezaJuridicaEnum.valueOf(getString(datosConsulta[13])));
            empNovedad.setFechaConstitucion(getDateAsLong(datosConsulta[14]));
            CodigoCIIU ciiu = new CodigoCIIU();
            ciiu.setIdCodigoCIIU(getShort(datosConsulta[15]));
            ciiu.setDescripcion(getString(datosConsulta[16]));
            empNovedad.setCodigoCIIU(ciiu);
            ARL arl = new ARL();
            arl.setIdARL(getShort(datosConsulta[17]));
            empNovedad.setArl(arl);
            empNovedad.setPaginaWeb(getString(datosConsulta[18]));
            empNovedad.setNumeroTotalTrabajadores(getInteger(datosConsulta[19]));
            empNovedad.setValorTotalUltimaNomina(getBigDecimal(datosConsulta[20]));
            empNovedad.setPeriodoUltimaNomina(getDateAsLong(datosConsulta[21]));
            // representante legal
            empNovedad.setTipoIdentificacionRepLegal(TipoIdentificacionEnum.valueOf(getString(datosConsulta[23])));
            empNovedad.setEmailRepLegal(getString(datosConsulta[24]));
            empNovedad.setTelefonoFijoRepLegal(getString(datosConsulta[25]));
            empNovedad.setIndicativoTelFijoRepLegal(getString(datosConsulta[26]));
            empNovedad.setTelefonoCelularRepLegal(getString(datosConsulta[27]));
            empNovedad.setNumeroIdentificacionRepLegal(getString(datosConsulta[28]));
            empNovedad.setPrimerNombreRepLegal(getString(datosConsulta[29]));
            empNovedad.setSegundoNombreRepLegal(getString(datosConsulta[30]));
            empNovedad.setPrimerApellidoRepLegal(getString(datosConsulta[31]));
            empNovedad.setSegundoApellidoRepLegal(getString(datosConsulta[32]));
            // sucursal
            empNovedad.setIdSucursalEmpresa(getLong(datosConsulta[33]));
            empNovedad.setIdUbicacionSucursal(getLong(datosConsulta[34]));
            empNovedad.setCodigoSucursal(getString(datosConsulta[35]));
            empNovedad.setDireccionFisicaSucursal(getString(datosConsulta[36]));
            empNovedad.setIdUbicacionPrincipal(getLong(datosConsulta[37]));
            empNovedad.setIdUbicacion(getLong(datosConsulta[38]));
            empNovedad.setIdUbicacionJudicial(getLong(datosConsulta[39]));
            empNovedad.setSucursalesOrigenSustPatronal(new ArrayList<>());
            empNovedad.setSociosEmpleador(new ArrayList<>());
            empNovedad.setSucursalesRolAfiliacion(new ArrayList<>());
            empNovedad.setSucursalesRolAportes(new ArrayList<>());
            empNovedad.setSucursalesRolSubsidio(new ArrayList<>());
            return empNovedad;
        } catch (NoResultException e) {
            logger.warn("No se encontró información del empleador con tipo " + tipoDocumento + " y número " + numeroIdentificacion);
            return null;
        }
    }
    // Métodos helpers para evitar NullPointer
    private String getString(Object obj) {
        return obj != null ? obj.toString() : null;
    }
    private Long getLong(Object obj) {
        return obj != null ? ((Number) obj).longValue() : null;
    }
    private Integer getInteger(Object obj) {
        return obj != null ? ((Number) obj).intValue() : null;
    }
    private Short getShort(Object obj) {
        return obj != null ? ((Number) obj).shortValue() : null;
    }
    private Long getDateAsLong(Object obj) {
        Date d = getDate(obj);
        return d != null ? d.getTime() : null;
    }
    private BigDecimal getBigDecimal(Object obj) {
        return obj != null ? new BigDecimal(obj.toString()) : null;
    }
    private Date getDate(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Number) {
            return new Date(((Number) obj).longValue());
        }
        return null;
    }

    private SolicitudNovedadDTO armarSolicitudNovedadEmpleador(ActualizarDatosEmpleadorInDTO dataIn,DatosEmpleadorNovedadDTO empleador,TipoTransaccionEnum novedad,UserDTO userDTO) {

        SolicitudNovedadDTO dto = new SolicitudNovedadDTO();
        // Valores constantes o definidos por negocio
        dto.setCanalRecepcion(CanalRecepcionEnum.WEB);
        dto.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
        dto.setClasificacion(ClasificacionEnum.PERSONA_JURIDICA);
        dto.setTipoTransaccion(novedad);
        dto.setObservaciones("N/A");
        // Novedad cambios representante legal 
        if ("CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB".equals(novedad.name())) {
            if (dataIn.getPrimerNombreRL() != null && !dataIn.getPrimerNombreRL().trim().isEmpty()
                    && !dataIn.getPrimerNombreRL().equalsIgnoreCase(empleador.getPrimerNombreRepLegal())) {
                empleador.setPrimerNombreRepLegal(dataIn.getPrimerNombreRL());
            }
            if (dataIn.getSegundoNombreRL() != null && !dataIn.getSegundoNombreRL().trim().isEmpty()
                    && !dataIn.getSegundoNombreRL().equalsIgnoreCase(empleador.getSegundoNombreRepLegal())) {
                empleador.setSegundoNombreRepLegal(dataIn.getSegundoNombreRL());
            }
            if (dataIn.getPrimerApellidoRL() != null && !dataIn.getPrimerApellidoRL().trim().isEmpty()
                    && !dataIn.getPrimerApellidoRL().equalsIgnoreCase(empleador.getPrimerApellidoRepLegal())) {
                empleador.setPrimerApellidoRepLegal(dataIn.getPrimerApellidoRL());
            }
            if (dataIn.getSegundoApellidoRL() != null && !dataIn.getSegundoApellidoRL().trim().isEmpty()
                    && !dataIn.getSegundoApellidoRL().equalsIgnoreCase(empleador.getSegundoApellidoRepLegal())) {
                empleador.setSegundoApellidoRepLegal(dataIn.getSegundoApellidoRL());
            }
            if (dataIn.getCorreoElectronicoRL() != null && !dataIn.getCorreoElectronicoRL().trim().isEmpty()
                    && !dataIn.getCorreoElectronicoRL().equalsIgnoreCase(empleador.getEmailRepLegal())) {
                empleador.setEmailRepLegal(dataIn.getCorreoElectronicoRL());
            }
            if (dataIn.getNumCelularRL() != null && !dataIn.getNumCelularRL().trim().isEmpty()
                    && !dataIn.getNumCelularRL().equalsIgnoreCase(empleador.getTelefonoCelularRepLegal())) {
                empleador.setTelefonoCelularRepLegal(dataIn.getNumCelularRL());
            }
            if (dataIn.getNumTelefonoRL() != null && !dataIn.getNumTelefonoRL().trim().isEmpty()
                    && !dataIn.getNumTelefonoRL().equalsIgnoreCase(empleador.getTelefonoFijoRepLegal())) {
                empleador.setTelefonoFijoRepLegal(dataIn.getNumTelefonoRL());
            }
            List<ItemChequeoDTO> listaChequeo = Arrays.asList(crearItem(72L, "Copia documento identidad  representante legal / administrador / empleador",
                "e57b1480-a249-434a-82c6-263b14669511.pdf_1360040400000",
                EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, null, null, TipoRequisitoEnum.ESTANDAR,
                "Se revisa:<br />-Que sea legible la copia del documento<br />-Sin tachones ni enmendaduras<br />-Ampliada al 150%..."),
                crearItem(79L, "Documento acredita existencia/representación legal o Personería Jurídica",
                        null, EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, null, null, TipoRequisitoEnum.ESTANDAR,
                        "Se revisa:<br />-Documento emitido por la entidad competente<br />-Fecha de expedición no mayor a 90 días..."),

                crearItem(43L, "Acta de posesión del representante legal",
                        null, EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, null, null, TipoRequisitoEnum.ESTANDAR,
                        "Para el caso de entidades (públicas y privadas) cuyos representantes legales sean elegidos..."),

                crearItem(44L, "Carta del Representante Legal dirigida a CCF solicitando el trámite de la novedad",
                        null, EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, null, null, TipoRequisitoEnum.ESTANDAR, ""),

                crearItem(115L, "Otros documentos anexos",
                        null, EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, null, null, TipoRequisitoEnum.ESTANDAR, null)
            );

            empleador.setListaChequeoNovedad(listaChequeo);
        }
        // Novedad cambio razón social / nombre comercial
        if ("CAMBIO_RAZON_SOCIAL_NOMBRE".equals(novedad.name())) {
            if (dataIn.getNombreRazonSocial() != null && !dataIn.getNombreRazonSocial().trim().isEmpty()
                    && !dataIn.getNombreRazonSocial().equalsIgnoreCase(empleador.getRazonSocial())) {
                empleador.setRazonSocial(dataIn.getNombreRazonSocial());
            }
            if (dataIn.getNombreComercial() != null && !dataIn.getNombreComercial().trim().isEmpty()
                    && !dataIn.getNombreComercial().equalsIgnoreCase(empleador.getNombreComercial())) {
                empleador.setNombreComercial(dataIn.getNombreComercial());
            }
        }
        // Novedad actualización datos oficina principal
        if ("ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB".equals(novedad.name())) {
            UbicacionDTO ubicacion = dataIn.getUbicacion();
            if (ubicacion != null) {
                if (ubicacion.getDireccion() != null && !ubicacion.getDireccion().trim().isEmpty()
                        && !ubicacion.getDireccion().equalsIgnoreCase(empleador.getDireccionFisicaOficinaPrincipal())) {
                    empleador.setDireccionFisicaOficinaPrincipal(ubicacion.getDireccion());
                }
                if (ubicacion.getDescripcionIndicacion() != null && !ubicacion.getDescripcionIndicacion().trim().isEmpty()
                        && !ubicacion.getDescripcionIndicacion().equalsIgnoreCase(empleador.getDescripcionIndicacionOficinaPrincipal())) {
                    empleador.setDescripcionIndicacionOficinaPrincipal(ubicacion.getDescripcionIndicacion());
                }
                if (ubicacion.getCodigoPostal() != null && !ubicacion.getCodigoPostal().trim().isEmpty()
                        && !ubicacion.getCodigoPostal().equalsIgnoreCase(empleador.getCodigoPostalOficinaPrincipal())) {
                    empleador.setCodigoPostalOficinaPrincipal(ubicacion.getCodigoPostal());
                }
                if (ubicacion.getTelefonoFijo() != null && !ubicacion.getTelefonoFijo().trim().isEmpty()
                        && !ubicacion.getTelefonoFijo().equalsIgnoreCase(empleador.getTelefonoFijoOficinaPrincipal())) {
                    empleador.setTelefonoFijoOficinaPrincipal(ubicacion.getTelefonoFijo());
                }
                if (ubicacion.getIndicativoTelefonoFijo() != null && !ubicacion.getIndicativoTelefonoFijo().trim().isEmpty()
                        && !ubicacion.getIndicativoTelefonoFijo().equalsIgnoreCase(empleador.getIndicativoTelFijoOficinaPrincipal())) {
                    empleador.setIndicativoTelFijoOficinaPrincipal(ubicacion.getIndicativoTelefonoFijo());
                }
                if (ubicacion.getTelefonoCelular() != null && !ubicacion.getTelefonoCelular().trim().isEmpty()
                        && !ubicacion.getTelefonoCelular().equalsIgnoreCase(empleador.getTelefonoCelularOficinaPrincipal())) {
                    empleador.setTelefonoCelularOficinaPrincipal(ubicacion.getTelefonoCelular());
                }
            }
        }

        // Novedad actualización datos envío correspondencia
        if ("ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB".equals(novedad.name())) {
            UbicacionDTO ubicacion = dataIn.getUbicacion();
            if (ubicacion != null) {
                if (ubicacion.getDireccion() != null && !ubicacion.getDireccion().trim().isEmpty()
                        && !ubicacion.getDireccion().equalsIgnoreCase(empleador.getDireccionFisica())) {
                    empleador.setDireccionFisica(ubicacion.getDireccion());
                }
                if (ubicacion.getDescripcionIndicacion() != null && !ubicacion.getDescripcionIndicacion().trim().isEmpty()
                        && !ubicacion.getDescripcionIndicacion().equalsIgnoreCase(empleador.getDescripcionIndicacion())) {
                    empleador.setDescripcionIndicacion(ubicacion.getDescripcionIndicacion());
                }
                if (ubicacion.getCodigoPostal() != null && !ubicacion.getCodigoPostal().trim().isEmpty()
                        && !ubicacion.getCodigoPostal().equalsIgnoreCase(empleador.getCodigoPostal())) {
                    empleador.setCodigoPostal(ubicacion.getCodigoPostal());
                }
                if (ubicacion.getTelefonoFijo() != null && !ubicacion.getTelefonoFijo().trim().isEmpty()
                        && !ubicacion.getTelefonoFijo().equalsIgnoreCase(empleador.getTelefonoFijo())) {
                    empleador.setTelefonoFijo(ubicacion.getTelefonoFijo());
                }
                if (ubicacion.getIndicativoTelefonoFijo() != null && !ubicacion.getIndicativoTelefonoFijo().trim().isEmpty()
                        && !ubicacion.getIndicativoTelefonoFijo().equalsIgnoreCase(empleador.getIndicativoTelFijo())) {
                    empleador.setIndicativoTelFijo(ubicacion.getIndicativoTelefonoFijo());
                }
                if (ubicacion.getTelefonoCelular() != null && !ubicacion.getTelefonoCelular().trim().isEmpty()
                        && !ubicacion.getTelefonoCelular().equalsIgnoreCase(empleador.getTelefonoCelular())) {
                    empleador.setTelefonoCelular(ubicacion.getTelefonoCelular());
                }
                if (ubicacion.getCorreoElectronico() != null && !ubicacion.getCorreoElectronico().trim().isEmpty()
                        && !ubicacion.getCorreoElectronico().equalsIgnoreCase(empleador.getEmail())) {
                    empleador.setEmail(ubicacion.getCorreoElectronico());
                }
            }
        }

        // Novedad actualización dirección notificación judicial
        if ("ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB".equals(novedad.name())) {
            UbicacionDTO ubicacion = dataIn.getUbicacion();
            if (ubicacion != null) {
                if (ubicacion.getDireccion() != null && !ubicacion.getDireccion().trim().isEmpty()
                        && !ubicacion.getDireccion().equalsIgnoreCase(empleador.getDireccionFisicaJudicial())) {
                    empleador.setDireccionFisicaJudicial(ubicacion.getDireccion());
                }
                if (ubicacion.getDescripcionIndicacion() != null && !ubicacion.getDescripcionIndicacion().trim().isEmpty()
                        && !ubicacion.getDescripcionIndicacion().equalsIgnoreCase(empleador.getDescripcionIndicacionJudicial())) {
                    empleador.setDescripcionIndicacionJudicial(ubicacion.getDescripcionIndicacion());
                }
                if (ubicacion.getCodigoPostal() != null && !ubicacion.getCodigoPostal().trim().isEmpty()
                        && !ubicacion.getCodigoPostal().equalsIgnoreCase(empleador.getCodigoPostalJudicial())) {
                    empleador.setCodigoPostalJudicial(ubicacion.getCodigoPostal());
                }
                if (ubicacion.getTelefonoFijo() != null && !ubicacion.getTelefonoFijo().trim().isEmpty()
                        && !ubicacion.getTelefonoFijo().equalsIgnoreCase(empleador.getTelefonoFijoJudicial())) {
                    empleador.setTelefonoFijoJudicial(ubicacion.getTelefonoFijo());
                }
                if (ubicacion.getTelefonoCelular() != null && !ubicacion.getTelefonoCelular().trim().isEmpty()
                        && !ubicacion.getTelefonoCelular().equalsIgnoreCase(empleador.getTelefonoCelularJudicial())) {
                    empleador.setTelefonoCelularJudicial(ubicacion.getTelefonoCelular());
                }
            }
        }

        // Usuario radicación
        dto.setUsuarioRadicacion(userDTO.getNombreUsuario());
        dto.setDatosEmpleador(empleador);
        return dto;
    }

    private ItemChequeoDTO crearItem(Long id, String nombre, String identificadorPrevio,EstadoRequisitoTipoSolicitanteEnum estado, 
                                 Boolean cumple, FormatoEntregaDocumentoEnum formato, TipoRequisitoEnum tipo, String ayuda) {
        ItemChequeoDTO item = new ItemChequeoDTO();
        item.setIdRequisito(id);
        item.setNombreRequisito(nombre);
        item.setIdentificadorDocumento(null);
        item.setIdentificadorDocumentoPrevio(identificadorPrevio);
        item.setEstadoRequisito(estado);
        item.setCumpleRequisito(cumple);
        item.setFormatoEntregaDocumento(formato);
        item.setTipoRequisito(tipo);
        item.setTextoAyuda(ayuda);
        return item;
    }

    @Override
    public Response orquestarAfiliacionIndependiente(AfiliarTrabajadorIndDTO afiliadoIn ,HttpServletRequest requestContext,UserDTO userDTO){
        String firmaServicio = "orquestarAfiliacionIndependiente(AfiliarTrabajadorIndDTO afiliadoIn ,HttpServletRequest requestContext,UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("AfiliarTrabajadorIndDTO",afiliadoIn.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<AfiliarTrabajadorIndDTO>> violations = validator.validate(afiliadoIn);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        try {
            // 1 validar solicitud
            List<String> validacionesFallidas = validarPersonas("123-374-1",ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,"TRABAJADOR_INDEPENDIENTE",construirDatosValidacionIndependiente(afiliadoIn));
            if(!validacionesFallidas.isEmpty() && validacionesFallidas.size() > 0){
                response = new ResponseDTO(CodigosErrorWebservicesEnum.TRABAJADOR_NO_AFILIABLE,validacionesFallidas);
            }else{
                // 2 digitar datos
                DigitarInformacionContactoWS digitarInformacion = new DigitarInformacionContactoWS(construirInformacionContacto(afiliadoIn,userDTO));
                digitarInformacion.execute();
                if(digitarInformacion.getResult().get("numeroRadicado") != null){
                    response.setMensajeError("");
                    response.setData("Persona afiliada bajo el numero de radicado: "+digitarInformacion.getResult().get("numeroRadicado"));
                }else{
                    response = new ResponseDTO(CodigosErrorWebservicesEnum.INCONSISTENCIA_GENERAL,"");
                }
            }
            // 3 guardar temporales
            // 4 radicar
            return Response.ok(response).build();
        }catch (Exception e) {
            logger.error("Error: ",e);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            return Response.ok(response).build();
        }
    }

    private List<String> validarPersonas(String bloque,ProcesoEnum proceso,String objeto,Map<String,String> datosValidacion){
        ValidarPersonas validador = new ValidarPersonas(bloque,proceso,objeto,datosValidacion);
            validador.execute();
            return validador.getResult().stream().filter(v -> v.getResultado().name() == "NO_APROBADA").map(v -> v.getDetalle()).collect(Collectors.toList());
    }

    private Map<String,String> construirDatosValidacionIndependiente(AfiliarTrabajadorIndDTO afiliadoIn){
        Map<String,String> parametros = new HashMap<String,String>();
        try{
            parametros.put("tipoIdentificacion",afiliadoIn.getTipoId().name());
            parametros.put("numeroIdentificacion",afiliadoIn.getNumeroId());
            parametros.put("primerNombre",afiliadoIn.getPrimerNombre());
            parametros.put("primerApellido",afiliadoIn.getPrimerApellido());
            parametros.put("fechaNacimiento",String.valueOf(afiliadoIn.getFechaNto().getTime()));
            parametros.put("afiliado",afiliadoIn.getClasificacion().name());
            parametros.put("tipoAfiliado",afiliadoIn.getClasificacion().name());
            parametros.put("tipoBeneficiario",afiliadoIn.getClasificacion().name());
            return parametros;
        }catch(Exception e){
            return parametros;
        }
    }

    private AfiliadoInDTO construirInformacionContacto(AfiliarTrabajadorIndDTO afiliadoIn,UserDTO userDto){
        AfiliadoInDTO informacion = new AfiliadoInDTO();
        try{
            informacion.setPersona(new PersonaDTO());
            informacion.getPersona().setNumeroIdentificacion(afiliadoIn.getNumeroId());
            informacion.getPersona().setTipoIdentificacion(afiliadoIn.getTipoId());
            informacion.getPersona().setPrimerNombre(afiliadoIn.getPrimerNombre());
            informacion.getPersona().setPrimerApellido(afiliadoIn.getPrimerApellido());
            informacion.getPersona().setSegundoApellido(afiliadoIn.getSegundoApellido() == null ? "" : afiliadoIn.getSegundoApellido());
            informacion.getPersona().setSegundoNombre(afiliadoIn.getSegundoNombre() == null ? "" : afiliadoIn.getSegundoNombre());
            informacion.getPersona().setClasificacion(afiliadoIn.getClasificacion());
            informacion.getPersona().setFechaNacimiento(afiliadoIn.getFechaNto().getTime());
            informacion.getPersona().setAutorizaUsoDatosPersonales(Boolean.TRUE);
            informacion.getPersona().setAutorizacionEnvioEmail(Boolean.TRUE);
            informacion.getPersona().setIdResguardo(2L);
			informacion.getPersona().setIdPuebloIndigena(2L);
            informacion.getPersona().setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION);
            informacion.getPersona().setUbicacionDTO(new UbicacionDTO());
            informacion.getPersona().getUbicacionDTO().setCorreoElectronico(afiliadoIn.getCorreoPersonal());
            informacion.getPersona().getUbicacionDTO().setTelefonoCelular(afiliadoIn.getCelularUno());
			informacion.getPersona().getUbicacionDTO().setDireccion(afiliadoIn.getDireccion());
            informacion.getPersona().getUbicacionDTO().setAutorizacionEnvioEmail(Boolean.TRUE);
            informacion.setNombreUsuarioCaja(userDto.getNombreUsuario());
            informacion.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
            informacion.setCanalRecepcion(CanalRecepcionEnum.WEB);
            informacion.setTrabajadorIndependienteWS(afiliadoIn);
            return informacion;
        }catch(Exception e){
            return informacion;
        }
    }


    public Response afiliaPensionado(AfiliaPensionadoDTO afiliaPensionadoIn, HttpServletRequest requestContext, UserDTO userDTO){

        String firmaServicio = "afiliaPensionado(AfiliaPensionadoDTO afiliaPensionadoIN,HttpServletRequest requestContext,UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("AfiliaPensionadoDTO",afiliaPensionadoIn.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        Set<ConstraintViolation<AfiliaPensionadoDTO>> violations = validator.validate(afiliaPensionadoIn);
        if (!violations.isEmpty()) {
            List<String> errores = violations.stream()
                    .map(v -> v.getMessage())
                    .collect(Collectors.toList());
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, errores);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }

        try {
            VerificarSolicitudesEnCurso validacion = new VerificarSolicitudesEnCurso(true, afiliaPensionadoIn.getNumeroId(), null, afiliaPensionadoIn.getTipoId(), null, null, null);
            validacion.execute();
            if (validacion.getResult().getResultado().name() == "APROBADA" || validacion.getResult().getResultado().name() == "NO_EVALUADA") {
                
                AfiliadoInDTO afiliado = new AfiliadoInDTO();

                BigInteger result = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PAGADOR_PENSION)
                    .setParameter("razonSocial", afiliaPensionadoIn.getPagadorPension())
                    .getSingleResult();

                Short idPagadorPension = result.shortValue();

                List<String> validacionesFallidas = validarPersonaPensionado(afiliaPensionadoIn);

                // if(!validacionesFallidas.isEmpty() && validacionesFallidas.size() > 0){
                //     response = new ResponseDTO(CodigosErrorWebservicesEnum.TRABAJADOR_NO_AFILIABLE, validacionesFallidas);
                //     return Response.ok(response).build();
                // } else {

                        afiliado.setPersona(new PersonaDTO());
                        afiliado.getPersona().setNumeroIdentificacion(afiliaPensionadoIn.getNumeroId());
                        afiliado.getPersona().setTipoIdentificacion(afiliaPensionadoIn.getTipoId());
                        afiliado.getPersona().setPrimerNombre(afiliaPensionadoIn.getPrimerNombre());
                        afiliado.getPersona().setPrimerApellido(afiliaPensionadoIn.getPrimerApellido());
                        afiliado.getPersona().setSegundoApellido(afiliaPensionadoIn.getSegundoApellido() == null ? "" : afiliaPensionadoIn.getSegundoApellido());
                        afiliado.getPersona().setSegundoNombre(afiliaPensionadoIn.getSegundoNombre() == null ? "" : afiliaPensionadoIn.getSegundoNombre());
                        afiliado.getPersona().setClasificacion(afiliaPensionadoIn.getClasificacion());
                        afiliado.getPersona().setFechaNacimiento(afiliaPensionadoIn.getFechaNto().getTime());
                        afiliado.getPersona().setUbicacionDTO(new UbicacionDTO());
                        afiliado.getPersona().getUbicacionDTO().setCorreoElectronico(afiliaPensionadoIn.getCorreoPersonal());
                        afiliado.getPersona().getUbicacionDTO().setTelefonoCelular(afiliaPensionadoIn.getCelularUno());
                        afiliado.getPersona().setAutorizaUsoDatosPersonales(Boolean.TRUE);
                        afiliado.setTipoAfiliado(TipoAfiliadoEnum.PENSIONADO);
                        afiliado.setCanalRecepcion(CanalRecepcionEnum.WEB);
                        afiliado.setIdPagadorPension(idPagadorPension);
                        afiliado.setPensionadoWS(afiliaPensionadoIn);

                        DigitarInformacionContactoWS digitarInformacion = new DigitarInformacionContactoWS(afiliado);
                        digitarInformacion.execute();
                        if(digitarInformacion.getResult().get("numeroRadicado") != null){
                            response.setMensajeError("");
                            response.setData("Persona afiliada bajo el numero de radicado: "+digitarInformacion.getResult().get("numeroRadicado"));
                        }else{
                            response = new ResponseDTO(CodigosErrorWebservicesEnum.INCONSISTENCIA_GENERAL,"");
                     //   }
                }
            } else {
                response = new ResponseDTO(CodigosErrorWebservicesEnum.TRABAJADOR_NO_AFILIABLE, validacion.getResult().getDetalle());
            }

            return Response.ok(response).build();
        }
        catch (NoResultException e) {
            logger.error("Error: ", e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PAGADOR_PENSION_NO_ENCONTRADO, null);
            return Response.ok(response).build();
        } catch (Exception e) {
            logger.error("Error: ", e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR, null);
            return Response.ok(response).build();
        }
    }


    public List<String> validarPersonaPensionado(AfiliaPensionadoDTO afiliaPensionadoIn){
            
            Map<String,String> datosValidacion = new HashMap<String,String>();
            datosValidacion.put("tipoIdentificacion",afiliaPensionadoIn.getTipoId().name());
            datosValidacion.put("numeroIdentificacion",afiliaPensionadoIn.getNumeroId());
            datosValidacion.put("primerNombre",afiliaPensionadoIn.getPrimerNombre());
            datosValidacion.put("primerApellido",afiliaPensionadoIn.getPrimerApellido());
            datosValidacion.put("fechaNacimiento",String.valueOf(afiliaPensionadoIn.getFechaNto().getTime()));
            datosValidacion.put("afiliado",afiliaPensionadoIn.getClasificacion().name());
            datosValidacion.put("tipoAfiliado",afiliaPensionadoIn.getClasificacion().name());
            datosValidacion.put("tipoBeneficiario",afiliaPensionadoIn.getClasificacion().name());

        return validarPersonas("123-374-1",ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,"PENSIONADO",datosValidacion);
    }

    public Response actualizarDatosIdentificacion(ActualizarDatosIdentificacionDTO dataIn ,HttpServletRequest requestContext, UserDTO userDTO){
         
        String firmaServicio = "actualizarDatosIdentificacion(ActualizarDatosIdentificacionDTO dataIn, HttpServletRequest requestContext, UserDTO userDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();

        Map<String, String> parametrosMetodo = new HashMap<>();
        parametrosMetodo.put("tipoDto", dataIn.getTipoIdentificacionPropietario() != null ? dataIn.getTipoIdentificacionPropietario().name() : "null");
        parametrosMetodo.put("documento", dataIn.getNumeroIdentificacionPropietario() != null ? dataIn.getNumeroIdentificacionPropietario() : "null");
        parametrosMetodo.put("nuevoDocumento", dataIn.getNuevoDocumento() != null ? dataIn.getNuevoDocumento() : "null");
        parametrosMetodo.put("nuevoTipoDto", dataIn.getNuevoTipoDto() != null ? dataIn.getNuevoTipoDto().name() : "null");
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
                            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        // Validaciones
        DatosPersonaNovedadDTO personaConsulta = new DatosPersonaNovedadDTO(); 
        try{
            personaConsulta = construirPersonaNovedadWs(dataIn.getTipoIdentificacionPropietario() ,dataIn.getNumeroIdentificacionPropietario(),true); 
        }catch(Exception e){
            ResponseDTO errorDTO = new ResponseDTO(CodigosErrorWebservicesEnum.NO_EXISTE_AFILIADO, null);
            errorDTO.setMensajeError("Error procesando solicitud: " + e.getMessage());
            AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, e, errorDTO, entityManager, auditoriaIntegracionServicios);
            return Response.ok(errorDTO).build();
        }
        ResponseDTO response = new ResponseDTO();
        try {
            Long count = (Long) entityManager
                    .createNamedQuery(NamedQueriesConstants.OBTENER_NUMEROS_DOCUMENTOS_EXISTENTES)
                    .setParameter("tipoIdentificacion", dataIn.getNuevoTipoDto())
                    .setParameter("numeroIdentificacion", dataIn.getNuevoDocumento())
                    .getSingleResult();

            if (count != null && count > 0) {
                response = new ResponseDTO(CodigosErrorWebservicesEnum.IDENTIFICACION_EXISTENTE_GENESYS, null);
                return Response.ok(response).build();
            }

            ExisteRegistraduriaNacional validacionRegistraduria =
                    new ExisteRegistraduriaNacional(dataIn.getNuevoTipoDto(), dataIn.getNuevoDocumento());
            validacionRegistraduria.execute();

            if (validacionRegistraduria.getResult()) {
                response = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_VALIDACION, null);
                return Response.ok(response).build();
            }
            // cargue de archivo
            InformacionArchivoDTO infoArchivo = new InformacionArchivoDTO();
            infoArchivo.setProcessName(dataIn.getProcessName());
            infoArchivo.setDataFile(dataIn.getDataFile());
            infoArchivo.setFileName(dataIn.getFileName());
            infoArchivo.setDocName(dataIn.getDocName());
            infoArchivo.setFileType(dataIn.getFileType());
            infoArchivo.setIsFront(dataIn.isFront());

            AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoArchivo);
			almacenarArchivo.execute();
			InformacionArchivoDTO archivo = almacenarArchivo.getResult();

            // Construcción solicitud
            TipoTransaccionEnum novedad = TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS;
            SolicitudNovedadDTO solicitudDTO = armarSolicitudNovedadCambioIdentificacion(dataIn, personaConsulta, userDTO, archivo);

            RadicarSolicitudNovedad servicioRadicacion = new RadicarSolicitudNovedad(solicitudDTO);
            servicioRadicacion.execute();
            logger.info(servicioRadicacion.toString());
            String estadoRadicacion = servicioRadicacion.getResult().getResultadoValidacion().name();
            logger.info("Estado de la Radicacion: " + estadoRadicacion);

            if ("EXITOSA".equals(estadoRadicacion)) {
                response.setMensajeError("");
                response.setData("Novedad asignada al BACK correctamente.");
                return Response.ok(response).build();
            } else {
                response = new ResponseDTO(CodigosErrorWebservicesEnum.ERROR_RADICACION, null);
                return Response.ok(response).build();
            }

        } catch (Exception e) {
            logger.error("Error en el proceso de actualización de identificación: ", e);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR, null);
            response.setMensajeError("Se presentó un error procesando la solicitud: " + e.getMessage());
            return Response.ok(response).build();
        }

    }

    private SolicitudNovedadDTO armarSolicitudNovedadCambioIdentificacion(ActualizarDatosIdentificacionDTO dataIn ,DatosPersonaNovedadDTO persona,UserDTO userDTO,InformacionArchivoDTO infoArchivo) {
        SolicitudNovedadDTO dto = new SolicitudNovedadDTO();
        dto.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        dto.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
        dto.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE); 
        dto.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS);
        dto.setObservaciones("N/A"); 
        logger.info("Data nueva: " 
            + dataIn.getNuevoTipoDto().name()
            + " | Numero identificacion New" 
            + dataIn.getNuevoDocumento());
        if(dataIn.getNuevoTipoDto() != null && !dataIn.getNuevoTipoDto().name().equals(persona.getTipoIdentificacionTrabajador().name()) ||
           dataIn.getNuevoDocumento() != null && !dataIn.getNuevoDocumento().equals(persona.getNumeroIdentificacionTrabajador())){
            persona.setTipoIdentificacionTrabajador(dataIn.getNuevoTipoDto());
            persona.setNumeroIdentificacionTrabajador(dataIn.getNuevoDocumento());
        }
        logger.info("Seteando Nuevo Documetno -> Tipo Documento(new): " 
            + persona.getTipoIdentificacionTrabajador()
            + " | Numero identificacion New" 
            + persona.getNumeroIdentificacionTrabajador());
        // Crear ItemChequeoDTO
        String documentoPrevio = "";
        try {
            List<String> resultados = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_PREVIO)
                    .setParameter("idRequisito", 69L) 
                    .setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
                    .setParameter("numeroIdentificacion",persona.getNumeroIdentificacion())
                    .getResultList();

            if (resultados != null && !resultados.isEmpty()) {
                documentoPrevio = String.valueOf(resultados.get(0));
            }
        } catch (Exception e) {
            logger.warn("No se pudo consultar documento previo: " + e.getMessage());
            documentoPrevio = "";
        }
        ItemChequeoDTO item = new ItemChequeoDTO();
        item.setIdRequisito(69L);
        item.setNombreRequisito("Copia del documento de identidad");
        item.setIdentificadorDocumento(infoArchivo.getFileName()+"_"+infoArchivo.getVersionDocumento());
        item.setIdentificadorDocumentoPrevio(documentoPrevio);
        item.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO);
        item.setCumpleRequisito(true);
        item.setFormatoEntregaDocumento(FormatoEntregaDocumentoEnum.ELECTRONICO);
        item.setTipoRequisito(TipoRequisitoEnum.ESTANDAR);
        item.setTextoAyuda("Tarjeta de identidad para menores de 18 años de edad, cédula de ciudadanía o cédula de extranjería para mayores de 18 años de edad. "
                + "Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación"
                + "<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación");

        // Asignar lista de chequeo a persona
        List<ItemChequeoDTO> listaChequeo = new ArrayList<>();
        listaChequeo.add(item);
        persona.setListaChequeoNovedad(listaChequeo);
        logger.info("Seteando ItemChequeoDTO -> identificadorDocumento: " 
            + item.getIdentificadorDocumento() 
            + " | identificadorDocumentoPrevio: " 
            + item.getIdentificadorDocumentoPrevio());

        dto.setUsuarioRadicacion(userDTO.getNombreUsuario());
        dto.setDatosPersona(persona);
        return dto;
    }

    private List<Map<String,Object>> consultarInformacionSolicitudes(String numeroRadicado,Long idCargue){
        List<Map<String,Object>> resultados = new ArrayList<Map<String,Object>>();
        List<Object[]> v = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_SOLICITUDES).setParameter("numeroRadicado",numeroRadicado == null ? "": numeroRadicado).setParameter("idCargue",idCargue == null ? "" : idCargue ).getResultList();
        for(Object[] d : v){
            Map<String,Object> resultado = new HashMap<String,Object>();
            resultado.put("Numero radicacion",d[0] == null ? "" : d[0].toString());
            resultado.put("Resultado proceso", d[1].toString());
            resultado.put("Estado solicitud", d[2].toString());
            resultado.put("Resultado intento", d[3].toString());
            resultado.put("Numero identificacion", d[4].toString());
            resultado.put("Tipo documento", d[5].toString());
            resultados.add(resultado);
        }
        return resultados;
    }

    public Response anexarArchivosAfiliacion(InformacionArchivoDTO dataIn,HttpServletRequest requestContext,UserDTO userDTO){
        String firmaServicio = "anexarArchivosAfiliacion(InformacionArchivoDTO dataIn,HttpServletRequest requestContext,UserDTO userDTO)";
        logger.info(firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("AfiliarTrabajadorIndDTO",dataIn.toString());
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(
            requestContext.getRemoteAddr(), firmaServicio, parametrosIn, userDTO.getEmail());
        ResponseDTO response = new ResponseDTO();
        List<String> listaErroresLogicaNegocio = mallaLogicaNegocio1(dataIn);
        if(!listaErroresLogicaNegocio.isEmpty()){
            response = new ResponseDTO(CodigosErrorWebservicesEnum.PARAMETROS_INCORRECTOS, listaErroresLogicaNegocio);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();    
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 1. validar que corresponda a requisito
            List<String> requisitorParametrizados = (List<String>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_ITEMS)
                            .setParameter("numeroRadicado",dataIn.getNumeroRadicado())
                            .getResultList();

            if(!requisitorParametrizados.contains(dataIn.getDescription())){
                response = new ResponseDTO(CodigosErrorWebservicesEnum.REQUISITO_NO_PARAMETRIZADO,"");
            }
            // 2. validar que exista solicitud en curso
            Object[] solicitud = null;
            try{
                logger.warn(">>>>>>>>>>>> PARAMETROS");
                logger.warn(dataIn.getNumeroRadicado());
                logger.warn(dataIn.getDescription());
                solicitud = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_SOLICITUD)
                                    .setParameter("numeroRadicado",dataIn.getNumeroRadicado())
                                    .setParameter("requisito",dataIn.getDescription()).getSingleResult();
                AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(dataIn);
                almacenarArchivo.execute();
                dataIn = almacenarArchivo.getResult();

                ItemChequeo itemChequeo = new ItemChequeo();
                itemChequeo.setSolicitudGlobal(new Solicitud());
                itemChequeo.getSolicitudGlobal().setIdSolicitud(Long.valueOf(solicitud[0].toString()));
                itemChequeo.setRequisito(new Requisito());
                itemChequeo.getRequisito().setIdRequisito(Long.valueOf(solicitud[1].toString()));
                itemChequeo.setPersona(new Persona());
                itemChequeo.getPersona().setIdPersona(Long.valueOf(solicitud[2].toString()));
                itemChequeo.setIdentificadorDocumento(dataIn.getIdentificadorDocumento()+"_"+dataIn.getVersionDocumento());
                itemChequeo.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.valueOf(solicitud[3].toString()));
                itemChequeo.setCumpleRequisito(Boolean.TRUE);
                itemChequeo.setFormatoEntregaDocumento(FormatoEntregaDocumentoEnum.ELECTRONICO);
                itemChequeo.setFechaRecepcionDocumentos(new Date());
                entityManager.persist(itemChequeo);

                Solicitud s = entityManager.find(Solicitud.class, Long.valueOf(solicitud[0].toString()));

                logger.warn(">>>>>>>>>>solicitud con.find()"+s.toString());

                logger.warn(">>>>>>>>>> dato temporal \n"+solicitud[4].toString());

                DatoTemporalSolicitud dts = entityManager.createQuery("SELECT D FROM DatoTemporalSolicitud D WHERE D.Solicitud = :solid", DatoTemporalSolicitud.class).setParameter("solid",s.getIdSolicitud()).getSingleResult();

                logger.warn(">>>>>>>>>> dato temporal completo \n"+dts.toString());



                JsonNode root = mapper.readTree(solicitud[4].toString());
                JsonNode nodo = root.path("listaChequeo");

                logger.warn("informacion del primer nivel del nodo"+mapper.writeValueAsString(root));
                logger.warn("informacion del segundo nivel del nodo"+mapper.writeValueAsString(nodo));

                List<ItemChequeoDTO> items = mapper.convertValue(
                        nodo,
                        mapper.getTypeFactory().constructCollectionType(List.class, ItemChequeoDTO.class)
                );

                response.setMensajeError("");
                response.setData("Documento adjuntado a la solicitud de manera satisfactoria");

            }catch(NoResultException e){
                e.printStackTrace();
                response = new ResponseDTO(CodigosErrorWebservicesEnum.NO_EXISTE_PROCESO,"");
            }
            // 3. insertar y acc 
            return Response.ok(response).build();
        }catch (Exception e) {
            logger.error("Error: ",e);
            // AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos(start, null, response, entityManager, auditoriaIntegracionServicios);
            response = new ResponseDTO(CodigosErrorWebservicesEnum.INTERNAL_SERVER_ERROR,null);
            return Response.ok(response).build();
        }
    }

    private List<String> mallaLogicaNegocio1(InformacionArchivoDTO dataIn){
        List<String> errores = new ArrayList<String>();

        if(dataIn.getDocName() == null || dataIn.getDocName() == ""){
            errores.add("El campo docName es obligatorio.");
        }
        if(dataIn.getDescription() == null || dataIn.getDescription() == ""){
            errores.add("El campo description es obligatorio.");
        }
        if(dataIn.getDataFile() == null || dataIn.getDataFile().length == 0){
            errores.add("Es obligatorio el cargue de un archivo para adjuntar.");
        }
        if(dataIn.getNumeroRadicado() == null || dataIn.getNumeroRadicado() == ""){
            errores.add("El campo numeroRadicado es obligatorio.");
        }

        return errores;
    }

}
