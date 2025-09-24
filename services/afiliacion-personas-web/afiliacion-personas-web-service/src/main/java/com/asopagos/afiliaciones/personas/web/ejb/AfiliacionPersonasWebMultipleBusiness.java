package com.asopagos.afiliaciones.personas.web.ejb;

import java.util.ArrayList;
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
import com.asopagos.afiliacion.personas.web.constants.CamposArchivoConstantes;
import com.asopagos.afiliacion.personas.web.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.personas.web.service.AfiliacionPersonasWebMultipleService;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.entidades.ccf.afiliaciones.CargueMultiple;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.PersonasUtils;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;
import com.asopagos.afiliaciones.personas.web.clients.ProcesarAfiliacionPersonasWebMasiva;
import com.asopagos.afiliaciones.personas.web.composite.clients.AfiliarTrabajadorCandidato;
import com.asopagos.afiliaciones.personas.web.composite.clients.AfiliarTrabajadorCandidatoCopyMasivo;
import com.asopagos.dto.AfiliacionArchivoPlanoDTO;
import com.asopagos.dto.AfiliacionPersonaWebMasivaDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.concurrent.ManagedExecutorService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.afiliaciones.clients.ConsultarDepartamentoPorIdMunicipio;





/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la afiliación de personas <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Juan Diego Ocampo Q <jocampo@heinsohn.com.co>
 */
@Stateless
public class AfiliacionPersonasWebMultipleBusiness implements AfiliacionPersonasWebMultipleService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "afiliacionpersonasweb_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(AfiliacionPersonasWebMultipleBusiness.class);

	private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";

	/**
	 * 
	 */
	@Inject
	private FileLoaderInterface fileLoader;
        
        @Resource//(lookup="java:jboss/ee/concurrency/executor/novedades")
        private ManagedExecutorService managedExecutorService;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.service.AfiliacionPersonasWebMultipleService#validarEstructuraContenidoArchivo(java.lang.Long,
	 *      com.asopagos.dto.InformacionGeneralArchivoDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResultadoValidacionArchivoDTO validarEstructuraContenidoArchivo(Long idEmpleador, Long idCargueMultiple,
			InformacionArchivoDTO archivoMultiple, UserDTO userDTO) {
		logger.debug("Inicia validarEstructuraContenidoArchivo(Long, Long,InformacionArchivoDTO, UserDTO)");
		Empleador empleador = consultarEmpleador(idEmpleador);
		if (empleador != null) {
			Long fileDefinitionId;
			try {
				fileDefinitionId = new Long(CacheManager
						.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_MULTIPLES).toString());
			} catch (Exception e) {
				throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
			}
			FileFormat fileFormat;
			if (archivoMultiple.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLS)) {
				fileFormat = FileFormat.EXCEL_XLS;
			} else if (archivoMultiple.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLSX)) {
				fileFormat = FileFormat.EXCEL_XLSX;
			} else {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
			}
			Map<String, Object> context = new HashMap<String, Object>();
			List<AfiliarTrabajadorCandidatoDTO> listaCandidatos = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
			List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
			List<Departamento> listaDepartamentos = consultarDepartamentos();
			List<Municipio> listaMunicipios = consultarMunicipios();
			List<Long> totalRegistro = new ArrayList<Long>();
			List<Long> totalRegistroError = new ArrayList<Long>();
			List<Long> totalRegistroValidos = new ArrayList<Long>();

			context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listaCandidatos);
			context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
			context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
			context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
			context.put(ArchivoMultipleCampoConstants.ID_EMPLEADOR, idEmpleador);
			context.put(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE, idCargueMultiple);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_VALIDO, totalRegistroValidos);

			FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
			String nombreYApellidosRepresentanteLegal = "";
			try {
				outDTO = fileLoader.validateAndLoad(context, fileFormat, archivoMultiple.getDataFile(),
						fileDefinitionId);
				listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
				ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
				resultaDTO.setNombreArchivo(archivoMultiple.getFileName());
				listaCandidatos = (List<AfiliarTrabajadorCandidatoDTO>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
				Empresa empresa = null;
				Persona persona = null;
				Ubicacion ubicacion = null;
				Gson gsonEntrada = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
				List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
				for (AfiliarTrabajadorCandidatoDTO candidato : listaCandidatos) {
					PersonaDTO personaConsulta = new PersonaDTO();
					personaConsulta.setTipoIdentificacion(candidato.getAfiliadoInDTO().getPersona().getTipoIdentificacion());
					personaConsulta.setNumeroIdentificacion(candidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
					personas.add(personaConsulta);
				}

        		String parametrosJson = gsonEntrada.toJson(personas);
				StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.ASP_JsonPersona);
				query.setParameter("personas", parametrosJson);
				query.execute();
				Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<PersonaDTO>>(){}.getType();
                personas = gson.fromJson((String)query.getSingleResult(), listType);
				nombreYApellidosRepresentanteLegal = PersonasUtils.obtenerNombrePersona(consultarPersonaPorId(empleador.getEmpresa().getIdPersonaRepresentanteLegal()));
				//	query.execute();
				for (AfiliarTrabajadorCandidatoDTO candidato : listaCandidatos) {
                    empresa = empleador.getEmpresa();
					if (empresa != null) {
						persona = empresa.getPersona();
						if (persona != null) {
							ubicacion = persona.getUbicacionPrincipal();
							if (ubicacion != null) {
								candidato.setDireccionEmpleador(ubicacion.getDireccionFisica());
								candidato.setTelefonoEmpleador(ubicacion.getTelefonoFijo());
								candidato.setCorreoEmpleador(ubicacion.getEmail());
							}
							candidato.setNumeroIdentificacionEmpleador(persona.getNumeroIdentificacion());
							candidato.setTipoIdentificacionEmpleador(persona.getTipoIdentificacion());
							candidato.setRazonSocialNombre(persona.getRazonSocial());
							candidato.setNombreYApellidosRepresentanteLegal(nombreYApellidosRepresentanteLegal);
						}
					}
                    if (candidato.getAfiliadoInDTO() != null && candidato.getAfiliadoInDTO().getPersona() != null
                            && candidato.getAfiliadoInDTO().getPersona().getTipoIdentificacion() != null
                            && candidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion() != null) {
						PersonaDTO personaDTO = null;
						PersonaDTO personaSeleccionada = null;
					if(personas != null){
						for(int i = 0; i < personas.size(); i++){
							personaSeleccionada = personas.get(i);
							if(personaSeleccionada.getTipoIdentificacion().equals(candidato.getAfiliadoInDTO().getPersona().getTipoIdentificacion())
							&& personaSeleccionada.getNumeroIdentificacion().equals(candidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion())){
								personaDTO = personaSeleccionada;
								personas.remove(i);	
								break;
							}
						}
					}
                        if (personaDTO != null) {
							logger.info("entra if");
							
							/**
							 * CAMBIO GLPI 68964
							 */

                            //if (personaDTO.getFechaNacimiento() == null) {
                            //    personaDTO.setFechaNacimiento(candidato.getAfiliadoInDTO().getPersona().getFechaNacimiento());
                            //}
                            
                            //Se asigna los valores de los nuevos campos 
                            if(candidato.getAfiliadoInDTO().getPersona().getOrientacionSexual() != null){
                                personaDTO.setOrientacionSexual(candidato.getAfiliadoInDTO().getPersona().getOrientacionSexual());
                            }
                            if(candidato.getAfiliadoInDTO().getPersona().getFactorVulnerabilidad() != null){
                                personaDTO.setFactorVulnerabilidad(candidato.getAfiliadoInDTO().getPersona().getFactorVulnerabilidad());
                            }
                            if(candidato.getAfiliadoInDTO().getPersona().getPertenenciaEtnica() != null){
                                personaDTO.setPertenenciaEtnica(candidato.getAfiliadoInDTO().getPersona().getPertenenciaEtnica());
                            }
                            if(candidato.getAfiliadoInDTO().getPersona().getIdPaisResidencia() != null){
                                personaDTO.setIdPaisResidencia(candidato.getAfiliadoInDTO().getPersona().getIdPaisResidencia());
                            }
                            if(candidato.getAfiliadoInDTO().getPersona().getIdOcupacionProfesion() != null){
                                personaDTO.setIdOcupacionProfesion(candidato.getAfiliadoInDTO().getPersona().getIdOcupacionProfesion());
                            }
                            if(candidato.getAfiliadoInDTO().getPersona().getNivelEducativo() != null){
                                personaDTO.setNivelEducativo(candidato.getAfiliadoInDTO().getPersona().getNivelEducativo());
                            }
                            
                            candidato.getAfiliadoInDTO().setPersona(personaDTO);
                            candidato.getIdentificadorUbicacionPersona().setPersona(personaDTO);
							if(personaDTO.getUbicacionDTO().getIdMunicipio() != null && personaDTO.getUbicacionDTO().getIdDepartamento() == null){
								ConsultarDepartamentoPorIdMunicipio consultarDepartamentoPorIdMunicipio = new ConsultarDepartamentoPorIdMunicipio(personaDTO.getUbicacionDTO().getIdMunicipio());
								consultarDepartamentoPorIdMunicipio.execute();
								Departamento departamento = consultarDepartamentoPorIdMunicipio.getResult();

								personaDTO.getUbicacionDTO().setIdDepartamento(departamento.getIdDepartamento());

							}
                            candidato.getIdentificadorUbicacionPersona().setUbicacion(personaDTO.getUbicacionDTO());
                            
                        }
                    }
                }
				if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
					resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
				} else {
					resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
				}
				if (resultaDTO.getEstadoCargue().equals(EstadoCargaMultipleEnum.EN_COLA) && listaHallazgos.size() > 0) {
					resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
				}
				if (listaHallazgos.size() > 0) {
					resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
				}

				listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));

				List<Long> numLinea = new ArrayList<Long>();
				for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
					if (!numLinea.contains(hallazgo.getNumeroLinea())) {
						numLinea.add(hallazgo.getNumeroLinea());
					}
				}
				resultaDTO.setAfiliarTrabajadorCandidatoDTO(listaCandidatos);
				resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
				totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
				totalRegistroError = (List<Long>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
				totalRegistroValidos = (List<Long>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_VALIDO);
				resultaDTO.setTotalRegistro((long) totalRegistro.size());
				resultaDTO.setRegistrosValidos((long) totalRegistroValidos.size());
				resultaDTO.setRegistrosConErrores((long) totalRegistroError.size());
				resultaDTO.setFechaCargue(new Date().getTime());
				resultaDTO.setFileDefinitionId(fileDefinitionId);
				if (resultaDTO.getAfiliarTrabajadorCandidatoDTO().isEmpty()
						&& resultaDTO.getResultadoHallazgosValidacionArchivoDTO().isEmpty()) {
					resultaDTO.getResultadoHallazgosValidacionArchivoDTO()
							.add(crearHallazgo(0L, "", CamposArchivoConstantes.ARCHIVO_SIN_REGISTROS_MSG));
					resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
				}
				logger.debug("Finaliza validarEstructuraContenidoArchivo(Long, Long, InformacionArchivoDTO, UserDTO)");
				return resultaDTO;
			} catch (FileProcessingException e) {
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/**
	 * Consulta los departamentos
	 * 
	 * @return
	 */
	private List<Departamento> consultarDepartamentos() {
		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTOS).getResultList();
	}

	/**
	 * Consulta los departamentos
	 * 
	 * @return
	 */
	private List<Municipio> consultarMunicipios() {
		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIOS).getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.service.
	 * AfiliacionPersonasWebService#estadoIdentificadorCargueMultiple(java.lang.
	 * Long)
	 */
	@Override
	public EstadoCargaMultipleEnum estadoIdentificadorCargueMultiple(Long idEmpleador) {
		logger.debug("Inicia estadoIdentificadorCargueMultiple(Long)");
		/* Se valida si el idEmpleador no esta vacio */
		if (idEmpleador != null) {
			try {
				Query qEstadoCargaMultiple = entityManager
						.createNamedQuery(NamedQueriesConstants.ESTADO_IDENTIFICADOR_CARGUE_MULTIPLE_EMPLEADOR);
				qEstadoCargaMultiple.setParameter("idEmpleador", idEmpleador);
				List<CargueMultiple> lstCargueMultiple = qEstadoCargaMultiple.getResultList();
				/* Se verifica si se encuentra el estado Cargue Multiple */
				if (!lstCargueMultiple.isEmpty()) {
					CargueMultiple cargueMultipleActualizar = lstCargueMultiple.get(0);
					logger.debug("Finaliza estadoIdentificadorCargueMultiple(Long)");
					return cargueMultipleActualizar.getEstado();
				} else {
					return null;
				}
			} catch (NoResultException e) {
				return null;
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.service.
	 * AfiliacionPersonasWebService#actualizarEstadoCargueMultiple(java.lang.
	 * Long,
	 * com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultiplePersonaEnum)
	 */
	@Override
	public Long actualizarEstadoCargueMultiple(Long identificador, Boolean empleadorCargue,
			EstadoCargaMultipleEnum estadoCargueMultiple) {
		logger.debug("Inicia actualizarEstadoCargueMultiple(Long)");
		if (empleadorCargue == null) {
			empleadorCargue = false;
		}
		try {
			// Se valida si identificador pertenece a un empleador al cargue
			// multiple
			if (empleadorCargue) {
				if (identificador != null && estadoCargueMultiple != null) {
					Query qEstadoCargaMultiple = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARGUE_MULTIPLE_POR_ID_EMPLEADOR);
					qEstadoCargaMultiple.setParameter("idEmpleador", identificador);
					List<CargueMultiple> lstCargueMultiple = qEstadoCargaMultiple.getResultList();
					/* Se verifica si se encuentra el estado Cargue Multiple */
					for(CargueMultiple cargue : lstCargueMultiple){
						if(cargue.getEstado() == EstadoCargaMultipleEnum.EVALUADO){
							cargue.setEstado(estadoCargueMultiple);
							entityManager.merge(cargue);
						}
					}
					if (!lstCargueMultiple.isEmpty()) {
						CargueMultiple cargueMultiple = lstCargueMultiple.get(0);
						return cargueMultiple.getIdSolicitudAfiliacionMultiple();
					}
				}
			} else {
				/* Se verifica el id de cargue */
				if (identificador != null && estadoCargueMultiple != null) {
					Query qEstadoCarga = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_CARGUE_MULTIPLE_POR_ID);
					qEstadoCarga.setParameter("idSolicitudAfiliacionMultiple", identificador);
					CargueMultiple cargueMultiple = (CargueMultiple) qEstadoCarga.getSingleResult();
					/* Se verifica si el cargueMultiple se encuentra */
					if (cargueMultiple != null) {
						cargueMultiple.setEstado(estadoCargueMultiple);
						entityManager.merge(cargueMultiple);
						logger.debug("Finaliza actualizarEstadoCargueMultiple(Long)");
						return cargueMultiple.getIdSolicitudAfiliacionMultiple();

					} else {
						return null;
					}
				} else {
					throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
				}
			}
		} catch (NullPointerException e) {
			return null;
		} catch (NoResultException e) {
			return null;
		}
		logger.debug("Finaliza actualizarEstadoCargueMultiple(Long)");
		return null;
	}
	////////////////////////////////////mio //////////////////////////////////////////////////////
	@Override
	public CargueMultipleDTO consultarSucursalECM(Long idEmpleador) {
		logger.info("Inicia consultarSucursalECM(Long)");

		if (idEmpleador != null) {
			Query qEstadoCargaMultiple = entityManager
					.createNamedQuery(NamedQueriesConstants.AFILIACION_SUCURSAL_ECM);
			qEstadoCargaMultiple.setParameter("idEmpleador", idEmpleador);
			qEstadoCargaMultiple.setParameter("estadoSolicitud", "EVALUADO");
			List<Object[]> lstCargueMultiple = qEstadoCargaMultiple.getResultList();

			if (!lstCargueMultiple.isEmpty()) {
				Object[] cargueMultiple = lstCargueMultiple.get(0);
				CargueMultipleDTO objReturn = new CargueMultipleDTO();
				
				objReturn.setIdEmpleador(idEmpleador);
				objReturn.setIdSucursalEmpleador(Long.valueOf(cargueMultiple[0].toString()));
				objReturn.setCodigoIdentificacionECM(cargueMultiple[1].toString());
				logger.info("Finaliza consultarSucursalECM(Long)");
				return objReturn;
			} else {
				return null;
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}
	////////////////////////mio1///////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.service.AfiliacionPersonasWebMultipleService#registrarCargueMultiple(java.lang.Long,
	 *      com.asopagos.dto.CargueMultipleDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Long registrarCargueMultiple(Long idEmpleador, CargueMultipleDTO cargueMultipleDTO, UserDTO userDTO) {
		logger.debug("Inicia actualizarEstadoCargueMultiple(Long)");
		if (!idEmpleador.equals(cargueMultipleDTO.getIdEmpleador())) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
					"El empleador no existe");
		}

		CargueMultiple cargue = new CargueMultiple();

		Empleador empleador = consultarEmpleador(idEmpleador);
		if (empleador == null) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
					"El empleador no existe");
		}
		cargue.setEmpleador(empleador);

		if (cargueMultipleDTO.getIdSucursalEmpleador() != null) {
			SucursalEmpresa sucursalEmpresa = consultarSucursalEmpresa(cargueMultipleDTO.getIdSucursalEmpleador());
			if (sucursalEmpresa == null) {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
						"La sucursal del empleador no existe");
			}
			cargue.setSucursalEmpleador(sucursalEmpresa);
		}
		cargue.setClasificacion(cargueMultipleDTO.getClasificacion());
		cargue.setCodigoIdentificacionECM(cargueMultipleDTO.getCodigoIdentificacionECM());
		cargue.setEstado(cargueMultipleDTO.getEstado());
		if (cargueMultipleDTO.getFechaCarga() != null) {
			cargue.setFechaCarga(cargueMultipleDTO.getFechaCarga());
		} else {
			cargue.setFechaCarga(new Date());
		}
		cargue.setProceso(cargueMultipleDTO.getProceso());
		cargue.setTipoSolicitante(cargueMultipleDTO.getTipoSolicitante());
		cargue.setTipoTransaccion(cargueMultipleDTO.getTipoTransaccion());
		try {
			List<CargueMultiple> carguePrevio = entityManager
					.createNamedQuery(NamedQueriesConstants.ESTADO_IDENTIFICADOR_CARGUE_MULTIPLE_EMPLEADOR)
					.setParameter("idEmpleador", idEmpleador).getResultList();
			if (carguePrevio != null && !carguePrevio.isEmpty()) {
				if (carguePrevio.get(0).getCodigoCargueMultiple() != null) {
					cargue.setCodigoCargueMultiple(carguePrevio.get(0).getCodigoCargueMultiple() + 1);
				} else {
					cargue.setCodigoCargueMultiple(1L);
				}
			} else {
				cargue.setCodigoCargueMultiple(1L);
			}
		} catch (NullPointerException e) {
			cargue.setCodigoCargueMultiple(1L);
		} catch (NoResultException e) {
			cargue.setCodigoCargueMultiple(1L);
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
					"Error al calcular el identificador de la carga múltiple");
		}

		entityManager.persist(cargue);
		logger.debug("Inicia actualizarEstadoCargueMultiple(Long)");
		return cargue.getIdSolicitudAfiliacionMultiple();
	}

	/**
	 * Método encargado de consultar un empleador
	 * 
	 * @param idEmpleador,
	 *            id del empleador a consultar
	 * @return retorna el empleador encontrado
	 */
	private Empleador consultarEmpleador(Long idEmpleador) {
		List<Empleador> empleador = (List<Empleador>) entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_ID)
				.setParameter("idEmpleador", idEmpleador).getResultList();
		if (!empleador.isEmpty()) {
			return empleador.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Metodo encargado de consultar los campos del archivo
	 * 
	 * @param fileLoadedId
	 *            identificador del fileLoadedId
	 * @return lista de definiciones de campos
	 */
	private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
		try {
			List<DefinicionCamposCargaDTO> campos = (List<DefinicionCamposCargaDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CAMPOS_ARCHIVO)
					.setParameter("idFileDefinition", fileLoadedId).getResultList();
			return campos;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Método encargado de crear un hallazgo dto
	 * 
	 * @param lineNumber,
	 *            número de linea
	 * @param campo,
	 *            campo al que pertenece el hallazgos
	 * @param errorMessage,
	 *            mensaje de error
	 * @return retorna el dto ResultadoHallazgosValidacionArchivoDTO
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(errorMessage);
		return hallazgo;
	}

	/**
	 * Metodo encargado de consulta una persona por id
	 * 
	 * @param idPersona,
	 *            id de la persona a consultar
	 * @return retorna la persona encontrada
	 */
	private Persona consultarPersonaPorId(Long idPersona) {
		List<Persona> personas = (List<Persona>) entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_ID).setParameter("idPersona", idPersona)
				.getResultList();
		if (!personas.isEmpty()) {
			return personas.get(0);
		} else {
			return null;
		}
	}

	/**
     * Metodo encargado de consulta una persona por tipo y numero de identificación 
     * 
     * @param idPersona,
     *            id de la persona a consultar
     * @return retorna la persona encontrada
     */
    private Persona consultarPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            Persona persona = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA, Persona.class)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            return persona;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Metodo encargado de consulta una persona por tipo y numero de identificación 
     * 
     * @param idPersona,
     *            id de la persona a consultar
     * @return retorna la persona encontrada
     */
    private PersonaDetalle consultarPersonaDetalle(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            PersonaDetalle persona = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION, PersonaDetalle.class)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            return persona;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
	/**
	 * Método encargado de consultar la lista hallazgos
	 * 
	 * @param fileDefinitionId
	 * @param outDTO
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId,
			FileLoaderOutDTO outDTO) {
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
		String mensaje;
		String campoMensaje = "";
		String[] arregloMensaje;
		if (outDTO.getFileLoadedId() != null && outDTO.getDetailedErrors() != null) {// tabla
																						// FileLoadedLog
																						// (getDetailedErrors)
			for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
				mensaje = detalleError.getMessage();
				arregloMensaje = mensaje.split(";");
				for (DefinicionCamposCargaDTO campo : campos) {
					for (int i = 0; i < arregloMensaje.length; i++) {
						if (arregloMensaje[i].contains(campo.getName())) {
							mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
							campoMensaje = campo.getLabel();
							ResultadoHallazgosValidacionArchivoDTO hallazgo = crearHallazgo(
									detalleError.getLineNumber(), campoMensaje, mensaje);
							listaHallazgos.add(hallazgo);
							mensaje = "";
							break;
						}
					}
				}
			}
		}
		return listaHallazgos;
	}

	/**
	 * Método encargado de consultar un sucursalEmpresa por id de la sucursal
	 * 
	 * @param idSucursal
	 * @return
	 */
	private SucursalEmpresa consultarSucursalEmpresa(Long idSucursal) {
		SucursalEmpresa sucursalEmpesa = (SucursalEmpresa) entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL_EMPRESA_POR_ID)
				.setParameter("idSucursalEmpresa", idSucursal).getSingleResult();
		if (sucursalEmpesa != null) {
			return sucursalEmpesa;
		} else {
			return null;
		}
	}
        

    @Asynchronous
    @Override
    public void solicitarAfiliacionMasiva(Long idEmpleador,AfiliacionArchivoPlanoDTO candidatosAfiliacion,UserDTO userDTO) {
        List<AfiliadoInDTO> afiliado = afiliacionesPersonasWeb(idEmpleador,candidatosAfiliacion.getListaCandidatos(),userDTO);
    }

	@Override
    public void cancelarSolicitudes(List<Long> solicitudesCandidatosACancelar) {
        logger.info("ENTRO A: cancelarSolicitudes");
        if(solicitudesCandidatosACancelar != null && !solicitudesCandidatosACancelar.isEmpty()){
			try {
				entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_SOLICITUDES_A_CANCELADAS)
						.setParameter("listado", solicitudesCandidatosACancelar) 
						.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    
        /**
     * Metodo que llama al ws que realiza la afiliacion web 
     */
    private List<AfiliadoInDTO> afiliacionesPersonasWeb(Long idEmpleador,List<AfiliarTrabajadorCandidatoDTO>  lstCandidatos,UserDTO userDTO) {
        logger.info(" PASO 2: Inicia svrAfiliacionesPersonasWeb");
         
        
        
        List<Callable<AfiliadoInDTO>> tareasParalelas = new LinkedList<>();
        ConsultarDatosTemporales consultarDatosTemporales = new ConsultarDatosTemporales(lstCandidatos.get(0).getAfiliadoInDTO().getIdSolicitudGlobal());
		consultarDatosTemporales.execute();
		String jsonPayload = consultarDatosTemporales.getResult();
		Gson gson = new GsonBuilder().create();
		TypeToken<Object[]> listType = new TypeToken<Object[]>() {};
		Object[] candidatos = gson.fromJson(jsonPayload, listType.getType());
        
        // Convertir el JSON a un JsonArray
        JsonArray jsonArray = gson.fromJson(jsonPayload, JsonArray.class);
        
        // Iterar sobre el JsonArray

        for (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidato : lstCandidatos) {
			String datoTemporal = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				
				// Extraer la ciudad
				JsonObject afiliadoInDTO = jsonObject.getAsJsonObject("afiliadoInDTO");
            	JsonObject persona = afiliadoInDTO.getAsJsonObject("persona");

				String tipoIdentificacion = persona.getAsJsonObject().get("tipoIdentificacion").getAsString();
				String numeroIdentificacion = persona.getAsJsonObject().get("numeroIdentificacion").getAsString();

				if(tipoIdentificacion.equals(afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getTipoIdentificacion().name())
				&& numeroIdentificacion.equals(afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion())){
					datoTemporal = jsonObject.toString();
					jsonArray.remove(i);
					break;
				}
			}
              logger.info("revision 19/04/2023 for inicial: " + afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());

            
              AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb = new AfiliacionPersonaWebMasivaDTO();
             afiliacionPersonaWeb.setUserDTO(userDTO);
             afiliacionPersonaWeb.setCandidatoAfiliacion(afiliarTrabajadorCandidato);
             
              printJsonMessage(afiliacionPersonaWeb, " objeto a enviar en callable ");
			  
             final String datoTemporalFinal = datoTemporal;
             Callable<AfiliadoInDTO> parallelTask = () -> {                
                ProcesarAfiliacionPersonasWebMasiva procesarArchivoSrv = new ProcesarAfiliacionPersonasWebMasiva(idEmpleador,afiliacionPersonaWeb);
                procesarArchivoSrv.execute();
				GuardarDatosTemporales guardar = new GuardarDatosTemporales(afiliarTrabajadorCandidato.getIdSolicitudGlobal(), datoTemporalFinal);
                guardar.execute();
                logger.info("revision 19/04/2023 callable: resultado del llamado al ws: " + procesarArchivoSrv.getResult());
                return procesarArchivoSrv.getResult();
            };
             

            tareasParalelas.add(parallelTask);
            
        }
        

        List<AfiliadoInDTO> listResultadoProcesamiento = new ArrayList<>();


        try {
            List<Future<AfiliadoInDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<AfiliadoInDTO> future : listInfoArchivoFuture) {
                listResultadoProcesamiento.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona afiliacionesPersonasWeb", e);
            throw new TechnicalException(e);
        }

        logger.debug("Finaliza procesarNovedadConfirmacionAbonosBancarios");
        return listResultadoProcesamiento;
        

    }
        
    @Override
        public AfiliadoInDTO procesarAfiliacionPersonasWebMasiva(Long idEmpleador, AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb) {
            logger.debug("Inicia procesarAfiliacionPersonasWebMasiva(Long,AfiliarTrabajadorCandidatoDTO)");
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + idEmpleador);
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + afiliacionPersonaWeb.getCandidatoAfiliacion().getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + afiliacionPersonaWeb.getUserDTO().getNombreUsuario());  
                    
        AfiliarTrabajadorCandidatoCopyMasivo afiliarCandidato = new AfiliarTrabajadorCandidatoCopyMasivo(idEmpleador,afiliacionPersonaWeb);
        afiliarCandidato.execute();
        AfiliadoInDTO afiliadoDTO = afiliarCandidato.getResult();
        
        logger.info("Resultado buscarCodigoPais " + afiliadoDTO.getNumeroRadicado());

        
        return afiliadoDTO;
    }
    
    @Override
    public RespuestaValidacionArchivoDTO validacionesArchivoAfiliaciones(String idValidacion, String valorCampo) {
        RespuestaValidacionArchivoDTO respuestaValidacion = new RespuestaValidacionArchivoDTO();
        logger.debug("Inicia validacionesArchivoAfiliaciones" + idValidacion + " " + valorCampo);

        if(idValidacion.equals("1")){
        //VALIDACION PAIS DE RESIDENCIA 
        Integer validacion = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PAIS)
                    .setParameter("nombrePaisP", valorCampo)
                    .getSingleResult();
        logger.debug("Resultado validacionesArchivoAfiliaciones " + validacion);

            if (validacion != null) {
                if (validacion == 0) {
                    respuestaValidacion.setMensaje("No cumple con la validacion número 1");
                    respuestaValidacion.setStatus("KO");
                } else {
                    respuestaValidacion.setMensaje("cumple con la val 1");
                    respuestaValidacion.setStatus("OK");
                }
            }
        }else if(idValidacion.equals("2")){
           //VALIDACION OCUPACION 
            Integer validacion = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_OCUPACION_PROFESIONAL_BY_NAME)
                    .setParameter("ocupacion", valorCampo)
                    .getSingleResult();
            logger.debug("Resultado validacionesArchivoAfiliaciones " + validacion);

            if (validacion != null) {
                if (validacion == 0) {
                    respuestaValidacion.setMensaje("No cumple con la validacion número 2");
                    respuestaValidacion.setStatus("KO");
                } else {
                    respuestaValidacion.setMensaje("cumple con la val 2");
                    respuestaValidacion.setStatus("OK");
                }
            } 
        }else if(idValidacion.equals("3")){
           //VALIDACION MUNICIPIO 
            Integer validacion = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_MUNICIPIO)
                    .setParameter("codigoMunicipio", valorCampo)
                    .getSingleResult();
            logger.debug("Resultado validacionesArchivoAfiliaciones " + validacion);

            if (validacion != null) {
                if (validacion == 0) {
                    respuestaValidacion.setMensaje("No cumple con la validacion número 3");
                    respuestaValidacion.setStatus("KO");
                } else {
                    respuestaValidacion.setMensaje("cumple con la val 3");
                    respuestaValidacion.setStatus("OK");
                }
            }  
        }
        
        logger.debug("Finalizo validacionesArchivoAfiliaciones " + respuestaValidacion.getStatus());

       
        return respuestaValidacion;
    }
    
    
    @Override
    public RespuestaValidacionArchivoDTO buscarCodigoPais(String nombrePais) {
        RespuestaValidacionArchivoDTO respuestaValidacion = new RespuestaValidacionArchivoDTO();
        logger.debug("Inicia buscarCodigoPais" + nombrePais);

        try {
                   //VALIDACION PAIS DE RESIDENCIA 
                BigInteger codigo = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CODIGO_PAIS)
                            .setParameter("nombrePaisP", nombrePais)
                            .getSingleResult();

                logger.debug("Resultado buscarCodigoPais " + codigo);



                if (codigo != null) {
                        respuestaValidacion.setMensaje(codigo.toString());
                        respuestaValidacion.setStatus("OK");
                }   
        } catch (Exception e) {
            respuestaValidacion.setMensaje("0");
            respuestaValidacion.setStatus("KO");
        }

        
        return respuestaValidacion;
    }
    
    @Override
    public RespuestaValidacionArchivoDTO buscarIdOcupacion(String nombreOcupacion) {
        RespuestaValidacionArchivoDTO respuestaValidacion = new RespuestaValidacionArchivoDTO();
        logger.info("Inicia buscarIdOcupacion" + nombreOcupacion);

        try {
                   //VALIDACION PAIS DE RESIDENCIA 
                Integer codigo = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_OCUPACION)
                            .setParameter("nombreOcupacionP", nombreOcupacion)
                            .getSingleResult();

                logger.info("Resultado buscarCodigoPais " + codigo);



                if (codigo != null) {
                        respuestaValidacion.setMensaje(codigo.toString());
                        respuestaValidacion.setStatus("OK");
                }   
        } catch (Exception e) {
            respuestaValidacion.setMensaje("0");
            respuestaValidacion.setStatus("KO");
        }

        
        return respuestaValidacion;
    }
    
     private void printJsonMessage(Object object,String message){
        try{
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(object);
            logger.info(message + jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
  
}
