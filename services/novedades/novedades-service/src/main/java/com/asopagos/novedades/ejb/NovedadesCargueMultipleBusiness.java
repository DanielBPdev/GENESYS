package com.asopagos.novedades.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.novedades.constants.CamposGestionArchivosUsuariosConstants;
import com.asopagos.novedades.constants.ArchivoTrasladoEmpresasCCFConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.afiliaciones.AfiliadosMasivosDTO;
import com.asopagos.dto.afiliaciones.AfiliadosACargoMasivosDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.dto.DiferenciasCargueActualizacionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.dto.empleadorDatosDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoTrasladoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import com.asopagos.entidades.ccf.afiliaciones.CargueMultiple;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.novedades.CargueArchivoActualizacion;
import com.asopagos.entidades.ccf.novedades.CargueMultipleSupervivencia;
import com.asopagos.entidades.ccf.novedades.DiferenciasCargueActualizacion;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.novedades.service.NovedadesCargueMultipleService;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import org.apache.commons.collections.CollectionUtils;
///imports 64732
import com.asopagos.afiliaciones.clients.ConsultarPersonaPensionado25Anios;
import com.asopagos.personas.clients.ConsultarEstadoCajaPersona;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.Long;

import java.util.concurrent.TimeUnit;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import java.util.Arrays;
import java.time.Duration;
import java.time.Instant;
//import com.asopagos.usuarios.dto.UsuarioGestionDTO;
import com.asopagos.dto.cargaMultiple.UsuarioGestionDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.dto.ResultadoValidacionArchivoGestionUsuariosDTO;
import com.asopagos.entidades.transversal.core.CargueCertificadosMasivos;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.asopagos.enumeraciones.aportes.EstadoCargaArchivoCrucesAportesEnum;
import com.asopagos.entidades.transversal.core.CargueTrasladoMedioPagoTranferencia;

import java.util.concurrent.Callable;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.annotation.Resource;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con las novedades de empleadores o personas. <b>Historia de Usuario:</b>
 * proceso 1.3 Novedades
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Stateless
public class NovedadesCargueMultipleBusiness implements NovedadesCargueMultipleService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "novedades_PU")
	private EntityManager entityManager;
	/**
	 * 
	 */
	@Inject
	private FileLoaderInterface fileLoader;


    @Resource // (lookup="java:jboss/ee/concurrency/executor/novedades")
    private ManagedExecutorService managedExecutorService;

	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(NovedadesCargueMultipleBusiness.class);

	public NovedadesCargueMultipleBusiness() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.service.NovedadesService#registrarCargue(java.lang
	 * .Long, com.asopagos.dto.CargueAfiliacionMultipleDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Long registrarCargue(Long idEmpleador, CargueMultipleDTO cargueMultipleDTO, UserDTO userDTO) {
		logger.debug("Inicia registrarCargue(Long,CargueMultipleDTO)");
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
			if (!carguePrevio.isEmpty()) {
				if (carguePrevio.get(0).getCodigoCargueMultiple() != null) {
					cargue.setCodigoCargueMultiple(carguePrevio.get(0).getCodigoCargueMultiple() + 1);
				} else {
					cargue.setCodigoCargueMultiple(1L);
				}
			} else {
				cargue.setCodigoCargueMultiple(1L);
			}
		} catch (NullPointerException ex) {
			cargue.setCodigoCargueMultiple(1L);
		} catch (NoResultException e) {
			cargue.setCodigoCargueMultiple(1L);
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
					"Error al calcular el identificador de la carga múltiple");
		}
		entityManager.persist(cargue);
		logger.debug("Finaliza registrarCargue(Long,CargueMultipleDTO)");
		return cargue.getIdSolicitudAfiliacionMultiple();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.novedades.service.NovedadesService#
	 * modificarEstadoCargueMultiple(java.lang.Long, java.lang.Boolean,
	 * com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultiplePersonaEnum)
	 */
	@Override
	public void modificarEstadoCargueMultiple(Long identificador, Boolean empleadorCargue,
			EstadoCargaMultipleEnum estadoCargueMultiple) {
		logger.debug("Inicia actualizarEstadoCargueMultiple(Long)");
		if (empleadorCargue == null) {
			empleadorCargue = false;
		}
		if (identificador != null && estadoCargueMultiple != null) {
			// Se valida si identificador pertenece a un empleador al cargue
			// multiple
			if (empleadorCargue) {
				Query qEstadoCargaMultiple = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARGUE_MULTIPLE_POR_ID_EMPLEADOR);
				qEstadoCargaMultiple.setParameter("idEmpleador", identificador);
				List<CargueMultiple> lstCargue = qEstadoCargaMultiple.getResultList();
				/* Se verifica si se encuentra el estado Cargue Multiple */
				if (!lstCargue.isEmpty()) {
					CargueMultiple cargue = lstCargue.get(0);
					cargue.setEstado(estadoCargueMultiple);
					entityManager.merge(cargue);
				}
			} else {
				/* Se verifica el id de cargue */
				Query qEstadoCarga = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_CARGUE_MULTIPLE_POR_ID);
				qEstadoCarga.setParameter("idSolicitudAfiliacionMultiple", identificador);
				List<CargueMultiple> lstCargueMultiple = qEstadoCarga.getResultList();
				/* Se verifica si el cargueMultiple se encuentra */
				if (!lstCargueMultiple.isEmpty()) {
					CargueMultiple cargue = lstCargueMultiple.get(0);
					cargue.setEstado(estadoCargueMultiple);
					entityManager.merge(cargue);
				}
				logger.debug("Inicia actualizarEstadoCargueMultiple(Long)");
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.novedades.service.NovedadesService#
	 * consultarIdentificadorCargueMultiple(java.lang.Long)
	 */
	@Override
	public EstadoCargaMultipleEnum consultarIdentificadorCargueMultiple(Long idEmpleador) {
		logger.debug("Inicia estadoIdentificadorCargueMultiple(Long)");
		/* Se valida si el idEmpleador no esta vacio */
		if (idEmpleador != null) {
			Query qEstadoCargaMultiple = entityManager
					.createNamedQuery(NamedQueriesConstants.ESTADO_IDENTIFICADOR_CARGUE_MULTIPLE_EMPLEADOR);
			qEstadoCargaMultiple.setParameter("idEmpleador", idEmpleador);
			List<CargueMultiple> lstCargueMultiple = qEstadoCargaMultiple.getResultList();
			/* Se verifica si se encuentra el estado Cargue Multiple */
			if (!lstCargueMultiple.isEmpty()) {
				CargueMultiple cargueMultiple = lstCargueMultiple.get(0);
				logger.debug("Finaliza estadoIdentificadorCargueMultiple(Long)");
				return cargueMultiple.getEstado();
			} else {
				return null;
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#
	 * verificarEstructuraArchivo(java.lang.Long, java.lang.Long,
	 * java.lang.Long, com.asopagos.dto.InformacionArchivoDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivo(Long idEmpleador, Long idCargueMultiple,
			Long idSucursalEmpleador, InformacionArchivoDTO archivoMultiple, UserDTO userDTO) {
		logger.debug("Inicia verificarEstructuraArchivo(Long, Long, InformacionArchivoDTO, UserDTO)");
		Empleador empleador = consultarEmpleador(idEmpleador);
		if (empleador != null) {
			Long fileDefinitionId = darFileDefinitionId(
					ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDADES_MULTIPLES.toString());
			FileFormat fileFormat = darFileFormat(archivoMultiple);
			Map<String, Object> context = new HashMap<String, Object>();
			List<TrabajadorCandidatoNovedadDTO> lstSolicitudNovedad = new ArrayList<TrabajadorCandidatoNovedadDTO>();
			List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
			List<Departamento> listaDepartamentos = consultarDepartamentos();
			List<Municipio> listaMunicipios = consultarMunicipios();
			List<Long> totalRegistro = new ArrayList<Long>();
			List<Long> totalRegistroError = new ArrayList<Long>();
			context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstSolicitudNovedad);
			context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
			context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
			context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
			context.put(ArchivoMultipleCampoConstants.ID_EMPLEADOR, idEmpleador);
			context.put(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE, idCargueMultiple);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
			FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
			try {
				outDTO = fileLoader.validateAndLoad(context, fileFormat, archivoMultiple.getDataFile(),
						fileDefinitionId);
				listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
				ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
				resultaDTO.setNombreArchivo(archivoMultiple.getFileName());
				resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
				lstSolicitudNovedad = (List<TrabajadorCandidatoNovedadDTO>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
                               
				listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
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
				List<Long> numLinea = new ArrayList<Long>();
				for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
					if (!numLinea.contains(hallazgo.getNumeroLinea())) {
						numLinea.add(hallazgo.getNumeroLinea());
					}
				}
				resultaDTO.setTrabajadorCandidatoNovedadDTO(lstSolicitudNovedad);
				resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
				totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
				totalRegistroError = (List<Long>) outDTO.getContext()
						.get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
				resultaDTO.setIdCargue(idCargueMultiple);
				Long cantidadLinea = 0L;
				if (totalRegistro.isEmpty()) {
					cantidadLinea = (long) numLinea.size();
				}
				Long sumTotalRegistro = (long) totalRegistro.size() + cantidadLinea;
				Long registrosError = (long) totalRegistroError.size();
				resultaDTO.setTotalRegistro(sumTotalRegistro);
				resultaDTO.setFechaCargue(new Date().getTime());
				resultaDTO.setRegistrosConErrores(registrosError);
				resultaDTO.setFileDefinitionId(fileDefinitionId);
				resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
				logger.debug("Finaliza verificarEstructuraArchivo(Long, Long, InformacionArchivoDTO, UserDTO)");
				return resultaDTO;
			} catch (FileProcessingException e) {
			    logger.error("Error verificarEstructuraArchivo()", e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/**
	 * Consulta los departamentos
	 * 
	 * @return retorna la lista de departamentos
	 */
	private List<Departamento> consultarDepartamentos() {
		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTOS).getResultList();
	}

	/**
	 * Consulta los departamentos
	 * 
	 * @return retorna la lista de municipios
	 */
	private List<Municipio> consultarMunicipios() {
		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIOS).getResultList();
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
	 * Metodo encargado retornar un DTO que se construye con los datos que
	 * llegan por parametro
	 * 
	 * @param lineNumber
	 * @param campo
	 * @param errorMessage
	 * @return retorna el resultado hallazgo validacion
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(errorMessage);
		return hallazgo;
	}

	/**
	 * Método encargado de consultar un empleador
	 * 
	 * @param idEmpleador,
	 *            id del empleador a consultar
	 * @return retorna el empleador encontrado
	 */
	private Empleador consultarEmpleador(Long idEmpleador) {
		try {
			Empleador empleador = (Empleador) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_ID)
					.setParameter("idEmpleador", idEmpleador).getSingleResult();
			if (empleador != null) {
				return empleador;
			} else {
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Método encargado de consultar un sucursalEmpresa por id de la sucursal
	 * 
	 * @param idSucursal
	 * @return Retorna la sucursal perteneciente a la empresa
	 */
	private SucursalEmpresa consultarSucursalEmpresa(Long idSucursal) {
		try {
			SucursalEmpresa sucursalEmpesa = (SucursalEmpresa) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL_EMPRESA_POR_ID)
					.setParameter("idSucursalEmpresa", idSucursal).getSingleResult();
			if (sucursalEmpesa != null) {
				return sucursalEmpesa;
			} else {
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Método encargado de consultar un Departamento por el id
	 * 
	 * @param idDepartamento
	 * @return retorna el departamento encontrado
	 */
	private Departamento consultarDepartamentoPorId(Short idDepartamento) {
		Departamento departamento = (Departamento) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTO_POR_ID)
				.setParameter("idDepartamento", idDepartamento).getSingleResult();
		try {
			if (departamento != null) {
				return departamento;
			} else {
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Método encargado de consultar un Municipio por el id
	 * 
	 * @param idMunicipio
	 * @return retorna el municipio encontrado
	 */
	private Municipio consultarMunicipioPorId(Short idMunicipio) {
		Municipio municipio = (Municipio) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIO_POR_ID)
				.setParameter("idMunicipio", idMunicipio).getSingleResult();
		try {
			if (municipio != null) {
				return municipio;
			} else {
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}
	}

    /**
     * Método encargado de consultar la lista hallazgos
     * 
     * @param fileDefinitionId
     *        Identificador del archivo
     * @param outDTO
     *        Objeto con el procesamiento del archivo
     * @return Lista de hallazgos del procesamiento del archivo
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId, FileLoaderOutDTO outDTO) {
        // Lista de errores
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        // Campos asociados al archivo
        List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
        // Se verifica si se registraron errores en la tabla FileLoadedLog
        if (outDTO.getFileLoadedId() != null && outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty()) {
            // Se recorren los errores y se crean los respectivos hallazgos
            for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
                listaHallazgos.add(obtenerHallazgo(campos, detalleError.getMessage(), detalleError.getLineNumber()));
            }
        }
        return listaHallazgos;
    }

    /**
     * Obtiene el hallazgo a partir de la informacion del mensaje y los campos
     * @param campos
     *        Lista de campos del archivo
     * @param mensaje
     *        Mensaje de error obtenido
     * @param lineNumber
     *        Linea donde se encontro el error
     * @return Hallazgo creado en respectivo formato
     */
    private ResultadoHallazgosValidacionArchivoDTO obtenerHallazgo(List<DefinicionCamposCargaDTO> campos, String mensaje, Long lineNumber) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
        // Indica si el mensaje contiene el nombre del campo
        Boolean encontroCampo = Boolean.FALSE;
        // Se separa el mensaje por caracter ;
        String[] arregloMensaje = mensaje.split(";");
        // Se verifica si el mensaje contiene algún campo
        for (DefinicionCamposCargaDTO campo : campos) {
            for (int i = 0; i < arregloMensaje.length; i++) {
                if (arregloMensaje[i].contains(campo.getName())) {
                    mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
                    hallazgo = crearHallazgo(lineNumber, campo.getLabel(), mensaje);
                    encontroCampo = Boolean.TRUE;
                    break;
                }
            }
        }
        // Si no se encontro campo se crea el hallazgo sin campo
        if (!encontroCampo) {
            hallazgo = crearHallazgo(lineNumber, "", mensaje);
        }
        return hallazgo;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarArchivoSupervivencia(com.asopagos.novedades.dto.ArchivoSupervivenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoDTO verificarArchivoSupervivencia(ArchivoSupervivenciaDTO archivoSuperVivenciaDTO, UserDTO userDTO) {
        logger.debug("Inicia verificarArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
        // File Format del archivo con registros encontrados
        FileFormat fileFormat = darFileFormat(archivoSuperVivenciaDTO.getInfoArchivoCargado());
        // Se valida la esctructura del archivo 
        ResultadoValidacionArchivoDTO respRegistroEncontrado = validarEstructuraArchivoSupervivencia(fileFormat,
                archivoSuperVivenciaDTO.getFileDefinitionId(), archivoSuperVivenciaDTO.getIdCargue(),
                archivoSuperVivenciaDTO.getInfoArchivoCargado());
        respRegistroEncontrado.setUsuarioRegistro(userDTO.getNombreUsuario());
        logger.debug("Finaliza verificarArchivoSupervivencia(Long, ArchivoSupervivenciaDTO, UserDTO)");
        return respRegistroEncontrado;
    }

	/**
	 * Método encargado de dar FileFormat de un archivo
	 * 
	 * @param informacionArchivoDTO,
	 *            archivo al cual se le dara el fileFormat
	 * @return retorna el fileFormat encontrado
	 */
	private FileFormat darFileFormat(InformacionArchivoDTO informacionArchivoDTO) {
		FileFormat fileFormat;
		if (informacionArchivoDTO.getFileName().toUpperCase()
				.endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)) {
			fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
			return fileFormat;
		}
		if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLSX)) {
			fileFormat = FileFormat.EXCEL_XLSX;
			return fileFormat;
		}
		if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLS)) {
			fileFormat = FileFormat.EXCEL_XLS;
			return fileFormat;
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
		}

	}

	/**
	 * Método encargado de dar el fileDefinitionId que se consulta por medio de
	 * la constante
	 * 
	 * @param parametroConstante,
	 *            parametro a consultar el fileDefinitionId
	 * @return retorna el id perteneciente a la constante
	 */
	private Long darFileDefinitionId(String parametroConstante) {
		return new Long(CacheManager.getConstante(parametroConstante).toString());
	}

	/**
	 * Método encargado de verificar la esctructura de un archivo de
	 * supervivencia
	 * 
	 * @param fileFormat,
	 *            formato que se debe de tener en cuenta
	 * @param fileDefinitionId,
	 *            id de la definicion del archivo
	 * @param idCargueMultiple,
	 *            id del cargue múltiple registrado
	 * @param archivoMultiple,
	 *            dto que contiene el contenido del archivo
	 * @return retorna el Resultado de la validacion DTO
	 */
	@SuppressWarnings("unchecked")
	private ResultadoValidacionArchivoDTO validarEstructuraArchivoSupervivencia(FileFormat fileFormat,
			Long fileDefinitionId, Long idCargueMultiple, InformacionArchivoDTO archivoMultiple) {
        logger.debug("Inicia validarEstructuraArchivoSupervivencia(" + fileFormat + ", " + fileDefinitionId + ", " + idCargueMultiple + ", "
                + archivoMultiple.getFileName() + ")");
		try {
			List<ResultadoSupervivenciaDTO> listaResultadoSupervivencia = new ArrayList<ResultadoSupervivenciaDTO>();
			List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
			List<Long> totalRegistro = new ArrayList<Long>();
			List<Long> totalRegistroError = new ArrayList<Long>();

			Map<String, Object> context = new HashMap<String, Object>();
			context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listaResultadoSupervivencia);
			context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
			context.put(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE, idCargueMultiple);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

			FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
			
			//Seguimiento al archivo
//    	    String dataFile = new String(archivoMultiple.getDataFile());
//    	    System.out.println("validarEstructuraArchivoSupervivencia - Archivo: " + archivoMultiple.getFileName());
//    	    System.out.println("dataFile: " + dataFile);
//    	    System.out.println("context: " + context);
//    	    System.out.println("fileFormat: " + fileFormat);
//    	    System.out.println("fileDefinitionId: " + fileDefinitionId);
    	    logger.info("*****Context*******"+context);
			logger.info("*****fileFormat*****"+fileFormat);
			logger.info("*****Archivo Multiple******"+archivoMultiple.getDataFile().toString());
			logger.info("*****fileDefinitionId******"+fileDefinitionId);
    	    outDTO = fileLoader.validateAndLoad(context, fileFormat, archivoMultiple.getDataFile(), fileDefinitionId);
			

			ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
			resultaDTO.setIdCargue(idCargueMultiple);
			resultaDTO.setNombreArchivo(archivoMultiple.getFileName());
			logger.info("******LINEA 625*********");
			resultaDTO.setFileDefinitionId(fileDefinitionId);
			logger.info("*****fileLoadedId******"+fileDefinitionId);
			resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
			// Lista de hallazgos
			logger.info("******LINEA 630**************");
			listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
			logger.info("******____********");
			//listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
			resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
			// Lista de datos validos
			listaResultadoSupervivencia = (List<ResultadoSupervivenciaDTO>) outDTO.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
			resultaDTO.setLstResultadoSupervivenciaDTO(listaResultadoSupervivencia);
			// Verificar estado
			if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
				resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_PROCESO);
			} else {
				resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
			}
			if (resultaDTO.getEstadoCargue().equals(EstadoCargaMultipleEnum.EN_COLA) && !listaHallazgos.isEmpty()) {
				resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
			}
			if (!listaHallazgos.isEmpty()) {
				resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
			}
			// Se obtiene la cantidad de lineas con error
			List<Long> numLinea = new ArrayList<Long>();
			for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
				if (!numLinea.contains(hallazgo.getNumeroLinea())) {
					numLinea.add(hallazgo.getNumeroLinea());
				}
			}

			// Calcular cantidad total
			totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
			totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
			Integer totalRegistrosE = totalRegistroError.size();
			Integer totalHallazgo = numLinea.size();
			Integer lineasError = 0;
			if (totalRegistrosE != totalHallazgo) {
				lineasError = totalHallazgo - totalRegistrosE;
			}

			Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
			Long registrosError = (long) totalHallazgo;
			resultaDTO.setTotalRegistro(sumTotalRegistro);
			resultaDTO.setFechaCargue(new Date().getTime());
			resultaDTO.setRegistrosConErrores(registrosError);
			resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza validarEstructuraArchivoSupervivencia(" + fileFormat + ", " + fileDefinitionId + ", " + idCargueMultiple
                    + ", " + archivoMultiple.getFileName() + ")");
			return resultaDTO;
		} catch (FileProcessingException e) {
            logger.error("Error validarEstructuraArchivoSupervivencia(" + fileFormat + ", " + fileDefinitionId + ", " + idCargueMultiple
                    + ", " + archivoMultiple.getFileName() + ")", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoRetiroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @Override
    public ResultadoValidacionArchivoTrasladoDTO VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF(TipoArchivoRespuestaEnum tipoArchivo, String idEmpleador, InformacionArchivoDTO archivo,
	UserDTO userDTO) {
		
        logger.info("Inicia VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF**********5("+ tipoArchivo +", InformacionArchivoDTO)");
        Long fileDefinitionId = null;
        
        // Se verifica el tipo de archivo cargado
		
        if (tipoArchivo.equals(TipoArchivoRespuestaEnum.TrasladoMasivosEmpresasCCF)) {
            fileDefinitionId = darFileDefinitionId(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF.toString());
        }else if (tipoArchivo.equals(TipoArchivoRespuestaEnum.TrasladoMasivosEmpresasCCFCargo)) {
            fileDefinitionId = darFileDefinitionId(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF_BENEFICIARIO.toString());
        }
        
        // Se obtiene el formato de archivo
		FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        // List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<AfiliadosMasivosDTO> afliadosCandidatos = new ArrayList<>();
        List<AfiliadosACargoMasivosDTO> afliadosCargoCandidatos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        // List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        logger.info("2G datos empleador sin transformar"+idEmpleador);
		String[] datos = separarDatos(idEmpleador);
		String docEmpleador = "", tipoDocumneto = "";
		if (datos != null) {
            docEmpleador = datos[0];
            tipoDocumneto = datos[1];
        } else {
            System.out.println("El formato no es válido.");
        }
		logger.info("2G datos empleador idEmpleaador"+docEmpleador);
		logger.info("2G datos empleador tipodocumento"+tipoDocumneto);
        context.put(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS, listaHallazgos);
        // context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        // context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        // context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoTrasladoEmpresasCCFConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA, afliadosCandidatos);
        context.put(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA_CARGO, afliadosCargoCandidatos);
        context.put(ArchivoTrasladoEmpresasCCFConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put("docEmpleador", docEmpleador);
        context.put("tipoDocumneto", tipoDocumneto);


        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        logger.info(context);
        logger.info(fileFormat);
        logger.info(archivo);
        logger.info(archivo.getDataFile());
        logger.info(fileDefinitionId);

        try {
			logger.info("Entra persist validateAndLoad");
			logger.info("Entra persist validateAndLoad" + listaHallazgos.size());
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS);
			logger.info("Entra persist validateAndLoad" + listaHallazgos.size());

            ResultadoValidacionArchivoTrasladoDTO resultaDTO = new ResultadoValidacionArchivoTrasladoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            afliadosCandidatos = (List<AfiliadosMasivosDTO>) outDTO.getContext()
                    .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA);
            afliadosCargoCandidatos = (List<AfiliadosACargoMasivosDTO>) outDTO.getContext()
                    .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA_CARGO);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
			if(listaHallazgos.size()>0){
                if(listaHallazgos.get(0) != null){
                
                    logger.info("Hay errores");
                    logger.info(listaHallazgos.get(0).getNombreCampo());
                    logger.info(listaHallazgos.get(0).getError());
                }else{
                    logger.info("No hay errores");
    
    
                }
            }
            
            
            
            
            logger.info("afliadosCandidatos: "+ afliadosCandidatos.size());
            logger.info("afliadosCargoCandidatos: "+ afliadosCargoCandidatos.size());
                        
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }

            if(afliadosCandidatos.size()>0)
            resultaDTO.setListaCandidatosAfiliar(afliadosCandidatos);
            
            if(afliadosCargoCandidatos.size()>0)
            resultaDTO.setListaCandidatosCargoAfiliar(afliadosCargoCandidatos);

            if(listaHallazgos.size()>0)
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);//Y

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoRetiroTrabajadores ccc("+ tipoArchivo +", InformacionArchivoDTO)+resultaDTO.getListaCandidatosAfiliar()");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoRetiroTrabajadores: "+ e.getMessage());
            logger.error("Error verificarEstructuraArchivoRetiroTrabajadores("+ tipoArchivo +", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#
	 * modificarCargueSupervivencia(com.asopagos.novedades.dto.
	 * ArchivoSupervivenciaDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Long modificarCrearCargueSupervivencia(ArchivoSupervivenciaDTO archivoSupervivenciaDTO, UserDTO userDTO) {
		logger.debug("Inicia modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
		try {
			boolean crear = false;
			CargueMultipleSupervivencia cargueSupervivencia = null;
			if (archivoSupervivenciaDTO.getIdentificadorCargue() != null) {
				try {
					cargueSupervivencia = (CargueMultipleSupervivencia) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_MULTIPLE_SUPERVIVENCIA_POR_ID)
							.setParameter("idCargueSupervivencia", archivoSupervivenciaDTO.getIdentificadorCargue())
							.getSingleResult();
					if (cargueSupervivencia != null) {
						if (archivoSupervivenciaDTO.getIdentificadorECMRegistro() != null) {
							cargueSupervivencia
									.setIdentificacionECM(archivoSupervivenciaDTO.getIdentificadorECMRegistro());
						}
						if (archivoSupervivenciaDTO.getFechaIngreso() != null) {
							cargueSupervivencia.setFechaIngreso(new Date(archivoSupervivenciaDTO.getFechaIngreso()));
						}
						if (archivoSupervivenciaDTO.getUsuario() != null) {
							cargueSupervivencia.setUsuario(archivoSupervivenciaDTO.getUsuario());
						}
						if (archivoSupervivenciaDTO.getEstadoCargue() != null) {
							cargueSupervivencia.setEstadoCargueSupervivencia(archivoSupervivenciaDTO.getEstadoCargue());
						}
						if (archivoSupervivenciaDTO.getPeriodo() != null) {
							cargueSupervivencia.setPeriodo(new Date(archivoSupervivenciaDTO.getPeriodo()));
						}
						if (archivoSupervivenciaDTO.getNombreArhivo() != null) {
							cargueSupervivencia.setNombreArchivo(archivoSupervivenciaDTO.getNombreArhivo());
						}
						entityManager.merge(cargueSupervivencia);
					} else {
						crear = true;
					}
				} catch (NoResultException e) {
					crear = true;
				}
			} else {
				crear = true;
			}
			if (crear) {
				if (archivoSupervivenciaDTO.getIdentificadorECMRegistro() != null
						&& archivoSupervivenciaDTO.getFechaIngreso() != null
						&& archivoSupervivenciaDTO.getUsuario() != null
						&& archivoSupervivenciaDTO.getEstadoCargue() != null
						&& archivoSupervivenciaDTO.getNombreArhivo() != null) {
					cargueSupervivencia = new CargueMultipleSupervivencia();
					cargueSupervivencia.setFechaIngreso(new Date(archivoSupervivenciaDTO.getFechaIngreso()));
					cargueSupervivencia.setIdentificacionECM(archivoSupervivenciaDTO.getIdentificadorECMRegistro());
					cargueSupervivencia.setEstadoCargueSupervivencia(archivoSupervivenciaDTO.getEstadoCargue());
					cargueSupervivencia.setUsuario(archivoSupervivenciaDTO.getUsuario());
					cargueSupervivencia.setNombreArchivo(archivoSupervivenciaDTO.getNombreArhivo());
					if (archivoSupervivenciaDTO.getPeriodo() != null) {
						cargueSupervivencia.setPeriodo(new Date(archivoSupervivenciaDTO.getPeriodo()));
					}
					entityManager.persist(cargueSupervivencia);
				} else {
					logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
					throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
				}
			}
			logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
			return cargueSupervivencia.getIdCargueMultipleSupervivencia();
		} catch (Exception e) {
			logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
					"Error al calcular el identificador de la carga múltiple");
		}
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoRespuesta(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoRespuesta(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
            InformacionArchivoDTO archivo) {
        logger.debug("Inicia verificarEstructuraArchivoRespuesta("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
        Long fileDefinitionId = null;
        // Se verifica el tipo de archivo cargado
        if (tipoArchivoRespuesta.equals(TipoArchivoRespuestaEnum.EMPLEADOR)) {
            fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_EMPLEADOR.toString());
        }
        else if (tipoArchivoRespuesta.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {
            fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_AFILIADO.toString());
        }
        else if (tipoArchivoRespuesta.equals(TipoArchivoRespuestaEnum.BENEFICIARIO)) {
            fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_BENEFICIARIO.toString());
        }
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoRespuesta("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoRespuesta("+ tipoArchivoRespuesta +", InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public ResultadoArchivo25AniosDTO VerificarEstructuraArchivoPensionado25Anios(TipoArchivoRespuestaEnum tipoArchivo, InformacionArchivoDTO archivo,
                                                                                  UserDTO userDTO) {

        logger.info("Inicia VerificarEstructuraArchivoPensionado25Anios**********5(" + tipoArchivo + ", InformacionArchivoDTO)");
        Long fileDefinitionId = null;

        // Se verifica el tipo de archivo cargado

        if (tipoArchivo.equals(TipoArchivoRespuestaEnum.Pensionados25Anios)) {
            fileDefinitionId = darFileDefinitionId(ConstantesSistemaConstants.FILE_DEFINITION_ID_LEY_2225.toString());
        }

       // Se obtiene el formato de archivo
       FileFormat fileFormat = darFileFormat(archivo);
       // Se llena el contexto de lectura de archivo
       // List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
       List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
       List<Afiliado25AniosDTO> pensionadosTodos = new ArrayList<>();
       List<Afiliado25AniosDTO> pensionadosNuevos = new ArrayList<>();
       List<Afiliado25AniosExistenteDTO> pensionadosExistentes = new ArrayList<>();
       List<Departamento> listaDepartamentos = consultarDepartamentos(); ////<------unir a la consulta 
       List<Municipio> listaMunicipios = consultarMunicipios(); ///<-----unir a la consulta
       // List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
       List<Long> totalRegistro = new ArrayList<Long>();
       List<Long> totalRegistroError = new ArrayList<Long>();

       Map<String, Object> context = new HashMap<String, Object>();
       // logger.info("2G datos empleador sin transformar"+idEmpleador);
       // String[] datos = separarDatos(idEmpleador);
       // String docEmpleador = "", tipoDocumneto = "";
       // if (datos != null) {
       //     docEmpleador = datos[0];
       //     tipoDocumneto = datos[1];
       // } else {
       //     System.out.println("El formato no es válido.");
       // }
       // logger.info("2G datos empleador idEmpleaador"+docEmpleador);
       // logger.info("2G datos empleador tipodocumento"+tipoDocumneto);
       context.put(ArchivoPensionados25AniosConstants.LISTA_HALLAZGOS, listaHallazgos);
       context.put(ArchivoPensionados25AniosConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
       context.put(ArchivoPensionados25AniosConstants.LISTA_MUNICIPIO, listaMunicipios);
       // context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
       context.put(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO, totalRegistro);
       context.put(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_NUEVOS, pensionadosTodos);
       // context.put(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_EXISTENTES, pensionadosExistentes);
       context.put(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        // context.put("docEmpleador", docEmpleador);
        // context.put("tipoDocumneto", tipoDocumneto);


        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        logger.info(context);
        logger.info(fileFormat);
        logger.info(archivo);
        logger.info(archivo.getDataFile());
        logger.info(fileDefinitionId);

        try {
            logger.info("Entra persist validateAndLoad");
            logger.info("Entra persist validateAndLoad" + listaHallazgos.size());
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoPensionados25AniosConstants.LISTA_HALLAZGOS);
            logger.info("Entra persist validateAndLoad" + listaHallazgos.size());

            ResultadoArchivo25AniosDTO resultaDTO = new ResultadoArchivo25AniosDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            separarPensionadosNuevosYExistentes(
                (List<Afiliado25AniosDTO>) outDTO.getContext()
                    .get(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_NUEVOS),
                pensionadosNuevos,
                pensionadosExistentes
            );
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (listaHallazgos.size() > 0) {
                if (listaHallazgos.get(0) != null) {

                    logger.info("Hay errores");
                    logger.info(listaHallazgos.get(0).getNombreCampo());
                    logger.info(listaHallazgos.get(0).getError());
                } else {
                    logger.info("No hay errores");


                }
            }


            logger.info("pensionadosNuevos: " + pensionadosNuevos.size());
            logger.info("pensionadosExistentes: " + pensionadosExistentes.size());

            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }

            if (pensionadosNuevos.size() > 0)
                resultaDTO.setListaCandidatosAfiliar25Anios(pensionadosNuevos);

            if (pensionadosExistentes.size() > 0)
                resultaDTO.setListaCandidatosExistentes(pensionadosExistentes);

            if (listaHallazgos.size() > 0)
                resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);//Y

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoRetiroTrabajadores ccc(" + tipoArchivo + ", InformacionArchivoDTO)+resultaDTO.getListaCandidatosAfiliar()");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoRetiroTrabajadores: " + e.getMessage());
            logger.error("Error verificarEstructuraArchivoRetiroTrabajadores(" + tipoArchivo + ", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private String[] separarDatos(String input) {
        String[] partes = input.split("-");

        if (partes.length == 2) {
            return partes;
        } else {
            return null;
        }
    }
    @Override
    public Afiliado25AniosExistenteDTO consultarPersonaPensionado25Anios(TipoIdentificacionEnum tipoDocumento, String numeroDocumento,
                                                                         UserDTO userDTO) {
        logger.info("Inicia consultarPersonaPensionado25Anios" + tipoDocumento + numeroDocumento);
        // Afiliado25AniosExistenteDTO 
        Afiliado25AniosExistenteDTO afiliadoExistente = new  Afiliado25AniosExistenteDTO();
        int persona = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EXISTE)
                .setParameter("tipoDocumento", tipoDocumento.name())
                .setParameter("numeroDocumento", numeroDocumento).getSingleResult();

        logger.info("Inicia consultarPersonaPensionado25Anios" + persona);
        if(persona==1){
        logger.info("entra persona existe" );
            List<Object[]> afiliado = entityManager
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PENSIONADO_25_ANIOS)
            .setParameter("tipoDocu", tipoDocumento.name())
            .setParameter("numeroDocumento", numeroDocumento).getResultList();

            logger.info(afiliado.toString());

            //estado
            ConsultarEstadoCajaPersona consultarEstadoCajaPersonaService = new ConsultarEstadoCajaPersona(null,
            numeroDocumento, tipoDocumento);
            consultarEstadoCajaPersonaService.execute();
            logger.info("Finaliza consultarEstadoCajaPersona(valorTI, valorNI, idEmpleador)");
            EstadoAfiliadoEnum estado = consultarEstadoCajaPersonaService.getResult();
                
            System.out.println("estado " + estado.name());

            for (Object[] obj : afiliado) {
                
                
                afiliadoExistente.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(String.valueOf(obj[0] == null ? "N/A": obj[0].toString())));
                afiliadoExistente.setNumeroIdentificación(String.valueOf(String.valueOf(obj[1] == null ? "N/A": obj[1].toString())));
                afiliadoExistente.setNombreCompleto(String.valueOf(String.valueOf(obj[2] == null ? "N/A": obj[2].toString())));
                afiliadoExistente.setFechaNacimiento(String.valueOf(String.valueOf(obj[3] == null ? "N/A": obj[3].toString())));
                afiliadoExistente.setEdad(Integer.parseInt(String.valueOf(obj[4] == null ? "N/A": obj[4].toString())));
                afiliadoExistente.setEstadoDependiente(EstadoAfiliadoEnum.valueOf(String.valueOf(obj[5] == null ? "SIN_ESTADO": obj[5].toString())));
                afiliadoExistente.setEstadoIndependiente(EstadoAfiliadoEnum.valueOf(String.valueOf(obj[6] == null ? "SIN_ESTADO": obj[6].toString())));
                afiliadoExistente.setEstadoPensionado(EstadoAfiliadoEnum.valueOf(String.valueOf(obj[7] == null ? "SIN_ESTADO": obj[7].toString())));
                afiliadoExistente.setEstadoBeneficiario(EstadoAfiliadoEnum.valueOf(String.valueOf(obj[8] == null ? "SIN_ESTADO": obj[8].toString())));
                afiliadoExistente.setTiempoAfiliacion(Long.parseLong(String.valueOf(obj[9] == null ? "N/A": obj[9].toString())));
                afiliadoExistente.setHogarFovis(Integer.parseInt(String.valueOf(obj[10] == null ? "N/A": obj[10].toString())));
                afiliadoExistente.setEstadoHogar(obj[11] == null ? null : EstadoHogarEnum.valueOf(String.valueOf(obj[11].toString())));
                afiliadoExistente.setUltimoPeriodoAfiliadoPrincipal(String.valueOf(String.valueOf(obj[12] == null ? "N/A": obj[12].toString()))); 
                afiliadoExistente.setUltimoPeriodoBeneficiario(String.valueOf(String.valueOf(obj[13] == null ? "N/A": obj[13].toString()))); 
                afiliadoExistente.setAportes(Integer.parseInt(String.valueOf(obj[14] == null ? "N/A": obj[14].toString())));
                
                
            }
            logger.info("2g toString " +afiliadoExistente.toString());
            
            //CONSULTAR_TIEMPOS_MULTIAFILIACION
            logger.info("*_* NovedadesCargueMultipleBusiness.consultarPersonaPensionado25Anios ... numeroDocumento: " + numeroDocumento + ", estadoAfiliado: " + estado.name());
            Long tiempoAfiliacion = 0L;
            int tiempoAux = 0;
            StoredProcedureQuery query =
            entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_TIEMPOS_MULTIAFILIACION)
            .setParameter("estadoAfiliado", estado.name())
            .setParameter("numeroIdentificacion", numeroDocumento);
            if(query.getSingleResult()!=null){
                tiempoAux = (Integer) query.getSingleResult();
                tiempoAfiliacion = Long.valueOf(tiempoAux);
    
                logger.info("2g tiempoAfiliacion " +tiempoAfiliacion);
            }else{
                tiempoAfiliacion = 0L;
            }
            

            if(tiempoAfiliacion!=null){
                // BigInteger tiempoafi = new BigInteger(tiempoAfiliacion);

                afiliadoExistente.setTiempoAfiliacion(tiempoAfiliacion);
            }
            
            logger.info("2g toString final" +afiliadoExistente.toString());
            logger.info("2g toString " +afiliadoExistente);

            return afiliadoExistente;
        }else{
            // Instant fin = Instant.now(); // Marca el final
            // Duration duracion = Duration.between(inicio, fin); // Calcula la duración
            // logger.info("Tiempo de ejecución: " + duracion.toMillis() + " ms");
            return null;
        }

    }

    /**
     * Consulta las actividades economicas existentes
     * 
     * @return retorna la lista de actividades economicas
     */
    private List<CodigoCIIU> consultarActiviadesEconomicas() {
        return (List<CodigoCIIU>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGOS_CIIU, CodigoCIIU.class)
                .getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#crearCargueArchivoActualizacion(com.asopagos.dto.
     * CargueArchivoActualizacionDTO)
     */
    @Override
    public Long crearCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        logger.debug("Inicia crearCargueArchivoActualizacion(CargueArchivoActualizacionDTO)");
        try {
            CargueArchivoActualizacion cargueArchivoActualizacion = cargueArchivoActualizacionDTO.convertToEntity();
            if (cargueArchivoActualizacion.getIdCargueArchivoActualizacion() != null) {
                entityManager.merge(cargueArchivoActualizacion);
            }
            else {
                entityManager.persist(cargueArchivoActualizacion);
            }
            logger.debug("Finaliza crearCargueArchivoActualizacion(CargueArchivoActualizacionDTO)");
            return cargueArchivoActualizacion.getIdCargueArchivoActualizacion();
        } catch (Exception e) {
            logger.debug("Finaliza crearCargueArchivoActualizacion(CargueArchivoActualizacionDTO) con error");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#crearDiferenciaArchivoActualizacion(com.asopagos.dto.
     * DiferenciasCargueActualizacionDTO)
     */
    @Override
    public Long crearDiferenciaArchivoActualizacion(DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO) {
        logger.debug("Inicia crearDiferenciaArchivoActualizacion(DiferenciasCargueActualizacionDTO)");
        try {
            DiferenciasCargueActualizacion diferenciasCargueActualizacion = diferenciasCargueActualizacionDTO.convertToEntity();
            if (diferenciasCargueActualizacion.getIdDiferenciasCargueActualizacion() != null) {
                entityManager.merge(diferenciasCargueActualizacion);
            }
            else {
                entityManager.persist(diferenciasCargueActualizacion);
            }
            logger.debug("Finaliza crearDiferenciaArchivoActualizacion(DiferenciasCargueActualizacionDTO)");
            return diferenciasCargueActualizacion.getIdDiferenciasCargueActualizacion();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoCertificadoEscolar(com.asopagos.dto.
     * InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCertificadoEscolar(InformacionArchivoDTO archivo) {
        logger.info("Inicia_ verificarEstructuraArchivoCertificadoEscolar(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CERTIFICADO_ESCOLAR.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);


	try {
			List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<InformacionActualizacionNovedadDTO>();
			List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
			List<GradoAcademico> listGradoAcademico = consultarGradosAcademicos();
			List<Long> totalRegistro = new ArrayList<Long>();
			List<Long> totalRegistroError = new ArrayList<Long>();

			Map<String, Object> context = new HashMap<String, Object>();
		context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_GRADOS_ACADEMICOS, listGradoAcademico);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
			context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

			FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
			
			//Seguimiento al archivo
//    	    String dataFile = new String(archivoMultiple.getDataFile());
//    	    System.out.println("validarEstructuraArchivoSupervivencia - Archivo: " + archivoMultiple.getFileName());
//    	    System.out.println("dataFile: " + dataFile);
//    	    System.out.println("context: " + context);
//    	    System.out.println("fileFormat: " + fileFormat);
//    	    System.out.println("fileDefinitionId: " + fileDefinitionId);
    	    		//linea de error
				System.out.println("**__**verificarEstructuraArchivoCertificadoEscolar");
					System.out.println("**__**fileDefinitionId: "+fileDefinitionId);
					System.out.println("**__**fileFormat: "+fileFormat);
				
					
    	    outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);
  
	System.out.println("**__**verificarEstructuraArchivoCertificadoEscolar outDTO asignado");
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoCertificadoEscolar(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
			System.out.println("**__**catchfileDefinitionId: "+fileDefinitionId);
            logger.info("¨¨Error verificarEstructuraArchivoCertificadoEscolar(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Consulta los grados academicos existentes
     * 
     * @return retorna la lista de grados academicos
     */
    private List<GradoAcademico> consultarGradosAcademicos() {
        return (List<GradoAcademico>) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_GRADOS_ACADEMICOS, GradoAcademico.class).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoPensionado(com.asopagos.dto.
     * InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoPensionado(InformacionArchivoDTO archivo) {
        logger.debug("Inicia verificarEstructuraArchivoPensionado(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PENSIONADO.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<AFP> listAFP = consultarAFPs();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_AFP, listAFP);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoPensionado(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoPensionado(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Consulta las AFP existentes
     * 
     * @return retorna la lista de AFP
     */
    private List<AFP> consultarAFPs() {
        return (List<AFP>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFP, AFP.class).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#consultarCargueArchivoActualizacion(java.lang.Long)
     */
    @Override
    public CargueArchivoActualizacionDTO consultarCargueArchivoActualizacion(Long idCargue) {
        try {
            CargueArchivoActualizacion cargue = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_ACTUALIZACION_BY_ID, CargueArchivoActualizacion.class)
                    .setParameter("idCargue", idCargue).getSingleResult();
            return CargueArchivoActualizacionDTO.convertCargueArchivoActualizacionToDTO(cargue);
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#consultarDiferenciaCargueArchivoActualizacion(java.lang.Long)
     */
    @Override
    public DiferenciasCargueActualizacionDTO consultarDiferenciaCargueArchivoActualizacion(Long idDiferencia) {
        try {
            DiferenciasCargueActualizacion diferencia = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DIFERENCIA_CARGUE_ACTUALIZACION_BY_ID, DiferenciasCargueActualizacion.class)
                    .setParameter("idDiferenciaCargue", idDiferencia).getSingleResult();
            return DiferenciasCargueActualizacionDTO.convertDiferenciasCargueActualizacionToDTO(diferencia);
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoRetiroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoRetiroTrabajadores(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
            InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoRetiroTrabajadores**********5("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
        Long fileDefinitionId = null;
        // Se verifica el tipo de archivo cargado
        if (tipoArchivoRespuesta.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {
            fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_AFILIADO.toString());
        }
        else if (tipoArchivoRespuesta.equals(TipoArchivoRespuestaEnum.BENEFICIARIO)) {
            fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_BENEFICIARIO.toString());
        }
        
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);


        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        logger.info(context);
        logger.info(fileFormat);
        logger.info(archivo);
        logger.info(archivo.getDataFile());
        logger.info(fileDefinitionId);

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            
            logger.info("listInfoActualizar: "+ listInfoActualizar.size());
            
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoRetiroTrabajadores ccc("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoRetiroTrabajadores: "+ e.getMessage());
            logger.error("Error verificarEstructuraArchivoRetiroTrabajadores("+ tipoArchivoRespuesta +", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoReintegroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoReintegroTrabajadores(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
            InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoReintegroTrabajadores**********5("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
        Long fileDefinitionId = null;
        // Se verifica el tipo de archivo cargado
        fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_REINTEGRO_AFILIADO.toString());
        
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            
            logger.info("listInfoActualizar verificarEstructuraArchivoReintegroTrabajadores: "+ listInfoActualizar.size());
                        
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoReintegroTrabajadores ("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoReintegroTrabajadores: "+ e.getMessage());
            logger.error("Error verificarEstructuraArchivoReintegroTrabajadores("+ tipoArchivoRespuesta +", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoActualizacionSucursal(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoActualizacionSucursal(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
            InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoActualizacionSucursal("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_SUCURSAL);
        
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            
            logger.info("listInfoActualizar: "+ listInfoActualizar.size());
                        
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoRetiroTrabajadores ccc("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoRetiroTrabajadores: "+ e.getMessage());
            logger.error("Error verificarEstructuraArchivoRetiroTrabajadores("+ tipoArchivoRespuesta +", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /*
    * (non-Javadoc)
    *
    * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoConfirmacionAB(com.asopagos.enumeraciones.
    * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
    */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoConfirmacionAB(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
                                                                                InformacionArchivoDTO archivo) {
        logger.info("Iniciaa verificarEstructuraArchivoConfirmacionAB(" + tipoArchivoRespuesta + ", InformacionArchivoDTO)");
        logger.info(tipoArchivoRespuesta.toString( ));
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CONFIRMACION_ABONO_BANCARIO);
        long tiempoInicial, tiempoFinal;
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put("nombreArchivo", archivo.getIdentificadorDocumento());

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            tiempoInicial = System.nanoTime();
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);
            tiempoFinal = System.nanoTime();
            logger.info("Calculo tiempo (verificarEstructuraArchivoConfirmacionAB, metodo validateAndLoad): " + String.valueOf(TimeUnit.MILLISECONDS.convert(tiempoFinal - tiempoInicial, TimeUnit.NANOSECONDS)));


            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = consultarResultadosValidacionCargueAbonos(archivo.getIdentificadorDocumento());
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            logger.info("============");
            logger.info(listaHallazgos.size());
            // 3. CONFIRMACION DE ERRORES LISTA HALLAZGOS BASE DE DATOS TRIGGER 
            listaHallazgos.addAll(consultarListaHallazgosArchivoAbono(archivo.getIdentificadorDocumento()));
            logger.info(listaHallazgos.size());
            logger.info("============");


            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();

            Long sumTotalRegistro = (long) totalRegistro.size();
            Long registrosError = (long) totalHallazgo;

            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            
            logger.info("Finaliza verificarEstructuraArchivoConfirmacionAB(" + tipoArchivoRespuesta + ", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoConfirmacionAB: " + e.getMessage());
            logger.error("Error verificarEstructuraArchivoConfirmacionAB(" + tipoArchivoRespuesta + ", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.service.NovedadesCargueMultipleService#verificarEstructuraArchivoSustitucionPatronal(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoSustitucionPatronal(TipoArchivoRespuestaEnum tipoArchivoRespuesta,
            InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoSustitucionPatronal("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_NOV_SUSTITUCION_PATRONAL);
        
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Departamento> listaDepartamentos = consultarDepartamentos();
        List<Municipio> listaMunicipios = consultarMunicipios();
        List<CodigoCIIU> listaActividadEconomica = consultarActiviadesEconomicas();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
        context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
        context.put(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU, listaActividadEconomica);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            
            logger.info("listInfoActualizar: "+ listInfoActualizar.size());
                        
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.info("Finaliza verificarEstructuraArchivoSustitucionPatronal("+ tipoArchivoRespuesta +", InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoSustitucionPatronal: "+ e.getMessage());
            logger.error("Error verificarEstructuraArchivoSustitucionPatronal("+ tipoArchivoRespuesta +", InformacionArchivoDTO)", e);
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public List<InformacionActualizacionNovedadDTO> consultarResultadosValidacionCargueAbonos(String nombreArchivo) {
        List<InformacionActualizacionNovedadDTO> res = new ArrayList<>();
    
        List<ConfirmacionAbonoBancarioCargueDTO> abonosConfirmados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_RESPUESTAS_CONFIRMACION_ABONOS, ConfirmacionAbonoBancarioCargueDTO.class)
            .setParameter("nombreArchivo", nombreArchivo)
            .getResultList();
    
        for (ConfirmacionAbonoBancarioCargueDTO it : abonosConfirmados) {
            InformacionActualizacionNovedadDTO nov = new InformacionActualizacionNovedadDTO();
            nov.setConfirmacionAbonoAdminSubsidio(it);
            res.add(nov);
        }
        return res;
    }
    
    private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgosArchivoAbono(String nombreArchivo) {
        // Lista de errores
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        
        listaHallazgos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_HALLAZGOS_CONFIRMACION_ABONOS, ResultadoHallazgosValidacionArchivoDTO.class)
            .setParameter("nombreArchivo", nombreArchivo)
            .getResultList();
    
        return listaHallazgos;
    }

    /**
     * GLPI 82800 Gestion Crear Usuario Empleador Masivo
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoEmpleador(InformacionArchivoDTO archivo) {
        logger.debug("Inicia verificarEstructuraArchivoEmpleador(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_EMPLEADOR.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<UsuarioGestionDTO> listaUsuarios = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS, listaUsuarios);
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoGestionUsuariosDTO resultaDTO = new ResultadoValidacionArchivoGestionUsuariosDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listaUsuarios = (List<UsuarioGestionDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            logger.info("Lista de usuarios a crear: " + listaUsuarios);
            resultaDTO.setListCrearUsuarioGestion(listaUsuarios);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoEmpleador(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoEmpleador(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }


    /**
     * GLPI 82800 Gestion Crear Usuario Persona Masivo
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoPersona(InformacionArchivoDTO archivo) {
        logger.debug("Inicia verificarEstructuraArchivoPersona(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PERSONA.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<UsuarioGestionDTO> listaUsuarios = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS, listaUsuarios);
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoGestionUsuariosDTO resultaDTO = new ResultadoValidacionArchivoGestionUsuariosDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listaUsuarios = (List<UsuarioGestionDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            logger.info("Lista de usuarios persona a crear: " + listaUsuarios);
            resultaDTO.setListCrearUsuarioGestion(listaUsuarios);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoPersona(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoPersona(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * GLPI 82800 Gestion Crear Usuario CCF Masivo
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoCcf(InformacionArchivoDTO archivo) {
        logger.debug("Inicia verificarEstructuraArchivoCcf(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CCF.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<UsuarioGestionDTO> listaUsuarios = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS, listaUsuarios);
        context.put(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS);

            ResultadoValidacionArchivoGestionUsuariosDTO resultaDTO = new ResultadoValidacionArchivoGestionUsuariosDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            listaUsuarios = (List<UsuarioGestionDTO>) outDTO.getContext()
                    .get(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS);
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            }
            else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (listaHallazgos.size() > 0) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            logger.info("Lista de usuarios ccf a crear: " + listaUsuarios);
            resultaDTO.setListCrearUsuarioGestion(listaUsuarios);
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // Calcular cantidad total
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES);
            Integer totalRegistrosE = totalRegistroError.size();
            Integer totalHallazgo = numLinea.size();
            Integer lineasError = 0;
            if (totalRegistrosE != totalHallazgo) {
                lineasError = totalHallazgo - totalRegistrosE;
            }

            Long sumTotalRegistro = (long) (totalRegistro.size() + lineasError);
            Long registrosError = (long) totalHallazgo;
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosError);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(sumTotalRegistro - registrosError);
            logger.debug("Finaliza verificarEstructuraArchivoCcf(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoCcf(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    // ============================ Masiva Transferencia

    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia(TipoArchivoRespuestaEnum tipoArchivo,InformacionArchivoDTO archivo){
        logger.info("inicia VerificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia");
        Long idFileDefinition = new Long(CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_MASIVA_CAMBIO_MEDIODEPAGO_TRANSFERENCIA).toString());
        FileFormat fileFormat;
        ResultadoValidacionArchivoDTO resultadoDTO = null;
        if(archivo.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)){
            fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
        }else{
            logger.error("Formato del archivo incorrecto");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();
        List<CargueTrasladoMedioPagoTranferencia> cargueTraslado = new ArrayList<CargueTrasladoMedioPagoTranferencia>();
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(ArchivoMultipleCampoConstants.LISTA_CARGUE_NOVEDAD_MASIVA_TRANSFERENCIA,cargueTraslado);
        context.put(ArchivoMultipleCampoConstants.CODIGO_IECM_ARCHIVO,archivo.getIdentificadorDocumento());
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        
        try{ 
            logger.warn("llega aca */*/*/*/*/*/*/*/*/*/*/* CAMELOOOOOOOOOOOOOOOOOOOOOOOOOO <3");
            outDTO = fileLoader.validateAndLoad(context,fileFormat,archivo.getDataFile(),idFileDefinition);
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
                    
            cargueTraslado = (List<CargueTrasladoMedioPagoTranferencia>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CARGUE_NOVEDAD_MASIVA_TRANSFERENCIA);
            resultadoDTO = new ResultadoValidacionArchivoDTO();

            listaHallazgos.addAll(consultarListaHallazgos(idFileDefinition,outDTO));

            if(FileLoadedState.SUCCESFUL.equals(outDTO.getState())){
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.CARGADO);
            }else{
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }

            List<Long> numLinea = listaHallazgos.stream().map(ResultadoHallazgosValidacionArchivoDTO::getNumeroLinea).distinct().collect(Collectors.toList());

            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);

            Long cantidadLinea = 0L;
            if(totalRegistro.isEmpty()){
                cantidadLinea = (long) numLinea.size();
            }

            Long sumTotalRegistro = (long) totalRegistro.size() + cantidadLinea;
            Long registrosError = (long) totalRegistroError.size();
            
            if (registrosError == sumTotalRegistro || registrosError > 0L) {
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.ANULADO);
            logger.info("**__**- resultadoDTO.registrosError: "+registrosError +" sumTotalRegistro"+sumTotalRegistro);
            }
            // if(!listaAportantes.isEmpty()){
                // generarResultadosArchivoCruce(listaAportantes);
            // }
            logger.info("**__**- cargueTraslado registrosError: "+registrosError);
            logger.info("**__**- cargueTraslado size: "+cargueTraslado.size());
            logger.info("**__**- resultadoDTO.getEstadoCargue: "+resultadoDTO.getEstadoCargue());
            resultadoDTO.setCargueTrasladoMedioPagoTranferencia(cargueTraslado);
            resultadoDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
            resultadoDTO.setTotalRegistro(sumTotalRegistro);
            resultadoDTO.setRegistrosConErrores(((long) totalRegistroError.size())+cantidadLinea);
            resultadoDTO.setRegistrosValidos(sumTotalRegistro - resultadoDTO.getRegistrosConErrores());
            resultadoDTO.setFechaCargue(new Date().getTime());
            resultadoDTO.setFileDefinitionId(idFileDefinition);
            logger.info("Finaliza verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia sin errores");
        }catch(Exception e){
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
        logger.info("Finaliza verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia-");
        // logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoDTO;
    }

    // ============ certificados masivos

    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCertificadosMasivos(Long idEmpleador, TipoArchivoRespuestaEnum tipoArchivo,InformacionArchivoDTO archivo){
        logger.info("inicia VerificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia");
        Long idFileDefinition = new Long(CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_CERTIFICADO_MASIVO_AFILIACION).toString());
        FileFormat fileFormat;
        ResultadoValidacionArchivoDTO resultadoDTO = null;
        if(archivo.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)){
            fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
        }else{
            logger.error("Formato del archivo incorrecto");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();
        List<CargueCertificadosMasivos> cargueCertificados = new ArrayList<CargueCertificadosMasivos>();
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(ArchivoMultipleCampoConstants.LISTA_CARGUE_CERTIFICADOS_MASIVOS,cargueCertificados);
        context.put(ArchivoMultipleCampoConstants.ID_EMPLEADOR_CERTIFICADO_MASIVO, idEmpleador);
        context.put(ArchivoMultipleCampoConstants.CODIGO_IECM_ARCHIVO,archivo.getIdentificadorDocumento());
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        Long totalRegistros = contarRegistrosArchivoMasivo(archivo.getDataFile());
        if(totalRegistros > 200L){
            resultadoDTO = crearHallazgo(ArchivoMultipleCampoConstants.LIMITE_DE_REGISTROS_PERMITIDOS_MSG + 200L,archivo.getFileName(),0L,totalRegistros);
            return resultadoDTO;
        };
        try{ 
            logger.warn("llega aca */*/*/*/*/*/*/*/*/*/*/* CAMELOOOOOOOOOOOOOOOOOOOOOOOOOO <3");
            outDTO = fileLoader.validateAndLoad(context,fileFormat,archivo.getDataFile(),idFileDefinition);
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
                    
            cargueCertificados = (List<CargueCertificadosMasivos>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CARGUE_CERTIFICADOS_MASIVOS);
            resultadoDTO = new ResultadoValidacionArchivoDTO();

            listaHallazgos.addAll(consultarListaHallazgos(idFileDefinition,outDTO));

            if(FileLoadedState.SUCCESFUL.equals(outDTO.getState())){
                logger.warn("cargue exitoso");
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.CARGADO);
            }else{
                logger.warn("pusimos estado cancelado quien sabe pq :c");
                logger.warn(outDTO.getState());
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }

            List<Long> numLinea = listaHallazgos.stream().map(ResultadoHallazgosValidacionArchivoDTO::getNumeroLinea).distinct().collect(Collectors.toList());

            totalRegistro = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES);

            Long cantidadLinea = 0L;
            if(totalRegistro.isEmpty()){
                cantidadLinea = (long) numLinea.size();
            }

            Long sumTotalRegistro = (long) totalRegistro.size() + cantidadLinea;
            Long registrosError = (long) totalRegistroError.size();
            
            if (registrosError == sumTotalRegistro || registrosError > 0L) {
                resultadoDTO.setEstadoCargue(EstadoCargaMultipleEnum.ANULADO);
            logger.info("**__**- resultadoDTO.registrosError: "+registrosError +" sumTotalRegistro"+sumTotalRegistro);
            }
            logger.info("**__**- cargueCertificados registrosError: "+registrosError);
            logger.info("**__**- cargueCertificados size: "+cargueCertificados.size());
            logger.info("**__**- resultadoDTO.getEstadoCargue: "+resultadoDTO.getEstadoCargue());
            resultadoDTO.setCargueCertificadosMasivos(cargueCertificados);
            resultadoDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
            resultadoDTO.setTotalRegistro(sumTotalRegistro);
            resultadoDTO.setRegistrosConErrores(((long) totalRegistroError.size())+cantidadLinea);
            resultadoDTO.setRegistrosValidos(sumTotalRegistro - resultadoDTO.getRegistrosConErrores());
            resultadoDTO.setFechaCargue(new Date().getTime());
            resultadoDTO.setFileDefinitionId(idFileDefinition);
            logger.info("Finaliza verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia sin errores");
        }catch(Exception e){
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
        logger.info("Finaliza verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia");
        logger.warn("resultado de validacion: "+ resultadoDTO.toString());
        return resultadoDTO;
    }

    public void separarPensionadosNuevosYExistentes(
        List<Afiliado25AniosDTO> todos,
        List<Afiliado25AniosDTO> nuevos,
        List<Afiliado25AniosExistenteDTO> existentes
    ) {
        List<Callable<Void>> tareasParalelas = new LinkedList<>();
        try {

            for (Afiliado25AniosDTO registro: todos){
                Callable<Void> parallelTask = () -> {
                    ConsultarPersonaPensionado25Anios consultarPersona = new ConsultarPersonaPensionado25Anios(
                        registro.getTipoIdentificacion(),
                        registro.getNumeroIdentificacion()
                    );
                    consultarPersona.execute();
                    Afiliado25AniosExistenteDTO resultado = consultarPersona.getResult();
        
                    if (resultado != null) {
                        resultado.setPagadorPension(registro.getPagadorPension());
                        resultado.setFechaRecepcionDocumentos(registro.getFechaRecepcionDocumentos());
                        resultado.setValorMesadaPensional(registro.getValorMesadaPensional());  
                        existentes.add(resultado);
                    } else {
                        nuevos.add(registro);
        
                    }
                    return null;
                };
                tareasParalelas.add(parallelTask);
            }

            if (!tareasParalelas.isEmpty()) {
                managedExecutorService.invokeAll(tareasParalelas);
            }
        } catch (Exception e) {
            logger.error("Error",e);
        }
        
    }

    public Long contarRegistrosArchivoMasivo(byte[] archivo){
        try(BufferedReader lector = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(archivo),StandardCharsets.UTF_8))){
            return lector.lines().count();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private ResultadoValidacionArchivoDTO crearHallazgo(String mensaje, String nombreArchivo, Long numeroLinea,Long totalRegistros) {
        ResultadoValidacionArchivoDTO resultDTO = new ResultadoValidacionArchivoDTO();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(numeroLinea);
        hallazgo.setError(mensaje);
        listaHallazgos.add(hallazgo);
        resultDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
        resultDTO.setNombreArchivo(nombreArchivo);
        resultDTO.setRegistrosConErrores(0L);
        resultDTO.setTotalRegistro(totalRegistros);
        return resultDTO;
    }

    //Inicio 96686
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoAfiliado(InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoAfiliado(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO_AFILIADO.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Inicializa las listas que se pasarán al contexto
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<>();
        List<Long> totalRegistroError = new ArrayList<>();

        Map<String, Object> context = new HashMap<>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            // Recupera las listas actualizadas del contexto después de que FileLoader ejecutó IPersistLine
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            
            // También consulta los hallazgos que el propio FileLoader haya podido generar
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar); 
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // --- Nueva lógica para determinar el estado del cargue ---
            // Calcula el número de líneas únicas con errores
            List<Long> numLineasConErrorUnicas = new ArrayList<>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLineasConErrorUnicas.contains(hallazgo.getNumeroLinea())) {
                    numLineasConErrorUnicas.add(hallazgo.getNumeroLinea());
                }
            }
            Long registrosConErrores = (long) numLineasConErrorUnicas.size();
            Long registrosValidos = (long) listInfoActualizar.size();

            if (registrosConErrores == 0 && FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                // El archivo se procesó sin errores y FileLoader lo marcó como exitoso
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else if (registrosConErrores > 0 && registrosValidos > 0) {
                // Hay errores en algunas líneas, PERO también hay registros válidos para procesar
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                // Todos los registros tienen errores (registrosValidos es 0) o el FileLoader falló catastróficamente
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }

            // Calcula los totales
            // sumTotalRegistro representa el número total de líneas en el archivo (válidas + con error)
            Long sumTotalRegistro = (long) (registrosValidos + registrosConErrores);
            
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosConErrores);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(registrosValidos);
            logger.info("Cantidad de registros validos - Guillermo: " + registrosValidos);
            logger.info("Cantidad de registros con errores - Guillermo:" + registrosConErrores);
            logger.info("Finaliza verificarEstructuraArchivoAfiliado(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoAfiliado(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoBeneficiario(InformacionArchivoDTO archivo) {
        logger.info("Inicia verificarEstructuraArchivoBeneficiario(InformacionArchivoDTO)");
        Long fileDefinitionId = darFileDefinitionId(
                ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO_BENEFICIARIO.toString());
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Inicializa las listas que se pasarán al contexto
        List<InformacionActualizacionNovedadDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<>();
        List<Long> totalRegistroError = new ArrayList<>();

        Map<String, Object> context = new HashMap<>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            // Recupera las listas actualizadas del contexto después de que FileLoader ejecutó IPersistLine
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            listInfoActualizar = (List<InformacionActualizacionNovedadDTO>) outDTO.getContext()
                .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            
            // También consulta los hallazgos que el propio FileLoader haya podido generar
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));

            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            resultaDTO.setNombreArchivo(archivo.getFileName());
            resultaDTO.setFileLoadedId(outDTO.getFileLoadedId());
            resultaDTO.setListActualizacionInfoNovedad(listInfoActualizar); 
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            // --- Nueva lógica para determinar el estado del cargue ---
            // Calcula el número de líneas únicas con errores
            List<Long> numLineasConErrorUnicas = new ArrayList<>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLineasConErrorUnicas.contains(hallazgo.getNumeroLinea())) {
                    numLineasConErrorUnicas.add(hallazgo.getNumeroLinea());
                }
            }
            Long registrosConErrores = (long) numLineasConErrorUnicas.size();
            Long registrosValidos = (long) listInfoActualizar.size();

            if (registrosConErrores == 0 && FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                // El archivo se procesó sin errores y FileLoader lo marcó como exitoso
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else if (registrosConErrores > 0 && registrosValidos > 0) {
                // Hay errores en algunas líneas, PERO también hay registros válidos para procesar
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                // Todos los registros tienen errores (registrosValidos es 0) o el FileLoader falló catastróficamente
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }

            // Calcula los totales
            // sumTotalRegistro representa el número total de líneas en el archivo (válidas + con error)
            Long sumTotalRegistro = (long) (registrosValidos + registrosConErrores);
            
            resultaDTO.setTotalRegistro(sumTotalRegistro);
            resultaDTO.setFechaCargue(new Date().getTime());
            resultaDTO.setRegistrosConErrores(registrosConErrores);
            resultaDTO.setFileDefinitionId(fileDefinitionId);
            resultaDTO.setRegistrosValidos(registrosValidos);
            logger.info("Finaliza verificarEstructuraArchivoBeneficiario(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (FileProcessingException e) {
            logger.error("Error verificarEstructuraArchivoBeneficiario(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    //Fin 96686
}
