package com.asopagos.aportes.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.aportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.business.interfaces.IConsultasModeloPILA;
//import com.asopagos.aportes.composite.service.ejb.DatosExcepcionNovedadDTO;
//import com.asopagos.aportes.composite.service.ejb.GuardarExcepcionNovedad;
//import com.asopagos.aportes.composite.service.ejb.PruebaRadicarNovedades;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.service.AportesPilaService;
import com.asopagos.aportes.util.FuncionesUtilitarias;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rutine.empleadores.ActualizarEstadoEmpleadorPorAportesRutine;
import com.asopagos.rutine.empleadores.CrearEmpresaRutine;
import com.asopagos.rutine.personasrutines.consultardatospersonarutine.CrearPersonaRutine;
import com.asopagos.usuarios.clients.ActualizarUsuarioCCF;
import com.asopagos.usuarios.clients.ConsultarUsuarios;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import com.asopagos.aportes.constants.NamedQueriesConstants;
/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con el registro o relación de aportes de una planilla pila
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397 <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Stateless
public class AportesPilaBusiness implements AportesPilaService {

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(AportesPilaBusiness.class);

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "core_PU")
	private EntityManager entityManagerCore;


@PersistenceContext(unitName = "pila_PU")
	private EntityManager entityManagerPila;
	/**
	 * Inject del EJB para consultas en modelo PILA entityManagerPila
	 */
	@Inject
	private IConsultasModeloPILA consultasPila;

	/**
	 * Inject del EJB para consultas en modelo Core entityManager
	 */
	@Inject
	private IConsultasModeloCore consultasCore;

	/**
	 * Llave valor de códigos de municipios y su id en base de datos
	 */
	private Map<String, Short> municipiosCodigoId = new HashMap<String, Short>();

	@Override
	public void copiarDatosAportesPlanilla(Long idPlanilla) {
		String firmaServicio = "AportesPilaBusiness.copiarDatosAportes(indicePlanilla: " + idPlanilla + ")";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		// consultasCore.copiarDatosAportes(idRegistroGeneral);

		// Llave valor de códigos de municipios y su id en base de datos
		municipiosCodigoId = new HashMap<String, Short>();
		List<Municipio> municipios = consultasCore.consultarMunicipios();
		for (Municipio m : municipios) {
			municipiosCodigoId.put(m.getCodigo(), m.getIdMunicipio());
		}

		List<InformacionPlanillasRegistrarProcesarDTO> infoPlanilla = consultarInformacionPlanillaByIdPlanilla(
				idPlanilla);

		for (InformacionPlanillasRegistrarProcesarDTO aporteGeneral : infoPlanilla) {

			logger.info("Planilla " + idPlanilla + ", AporteGeneral " + aporteGeneral.getRegistroGeneral()
					+ ", Cantidad detalles " + aporteGeneral.getCantidadTemAportes());

			// metodo con tx unificada con el uso de rutinas
			procesarAportanteCotizante(aporteGeneral.getRegistroGeneral());

			consultasCore.copiarDatosAportes(aporteGeneral.getRegistroGeneral());
		}

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * Consulta y marca los registros detallados y generales de la planilla para que
	 * no sean procesados nuevamente
	 * 
	 * @param idPlanilla
	 * @return
	 */
	private List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionPlanillaByIdPlanilla(Long idPlanilla) {
		String firmaServicio = "AportesPilaBusiness.consultarInformacionPlanillaIdPlanilla()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		// TODO: Mejorar consulta y marca en 1 solo paso
		List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas = consultasPila
				.consultarInformacionAportesProcesarByIdPlanilla(idPlanilla);

		logger.info("infoPlanillas.size()" + infoPlanillas.size());

		consultasPila.marcarTemporalEnProceso(infoPlanillas, Boolean.TRUE, Boolean.TRUE);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoPlanillas;

	}

	/**
	 * Crea y/o actualiza los aportantes y cotizantes
	 * 
	 * @param idRegistroGeneral
	 */
	private void procesarAportanteCotizante(Long idRegistroGeneral) {
		crearActualizarAportantes(idRegistroGeneral);
                entityManagerCore.flush();
		crearCotizante(idRegistroGeneral);
	}

	/**
	 * Método que determina si el aportante ya existe como empleador evalua si debe
	 * actualizar su estado (reingreso empleador sin novedad) o en caso de no
	 * existir ni como empleador o como empresa crea el aportante como empresa.
	 */
	private void crearActualizarAportantes(Long idRegistroGeneral) {
		String firmaServicio = "AportesPilaBusiness.crearActualizarAportante(ActivacionEmpleadorDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AporteDTO> aportantes = consultasPila.consultarDatosAportantesTemporales(idRegistroGeneral);

		for (AporteDTO aportante : aportantes) {
			if (aportante != null) {
				logger.info("aportante " +aportante.toString());
				if (aportante.getEmpresaAportante() != null) {
					logger.info("aportante.getEmpresaAportante() " +aportante.getEmpresaAportante());
					if (aportante.getIdEmpresa() == null) {
						logger.info("aportante.getIdEmpresa() " +aportante.getIdEmpresa());
						aportante.getEmpresaAportante().setCreadoPorPila(Boolean.TRUE);
						aportante.getEmpresaAportante().getUbicacionModeloDTO()
								.setIdMunicipio(ubicarMunicipio(aportante.getCodigoMunicioAportante()));
						aportante.setIdEmpresa(crearEmpresa(aportante.getEmpresaAportante()));
					} else if (esReintegrable(aportante)) {
						logger.info("ingresa al else esReintegrable------------->>>> " );
						activarEmpleador(aportante, idRegistroGeneral);
					}
				} else if (aportante.getPersonaAportante() != null) {
					if (aportante.getIdPersona() == null) {
						aportante.getPersonaAportante().setCreadoPorPila(Boolean.TRUE);
						aportante.getPersonaAportante().getUbicacionModeloDTO()
								.setIdMunicipio(ubicarMunicipio(aportante.getCodigoMunicioAportante()));
					logger.info("**__**crearActualizarAportantes aportante.getPersonaAportante(): "+aportante.getPersonaAportante());
						aportante.setIdPersona(crearPersona(aportante.getPersonaAportante()));
					}
				}

				if (aportante.getTipoDocTramitador() != null && aportante.getIdTramitador() != null) {
					logger.info("aportante.getTipoDocTramitador() " +aportante.getTipoDocTramitador());
					logger.info("aportante.getIdTramitador() " +aportante.getIdTramitador());
					crearEmpresa(prepararTramitador(aportante));
				}
			}
		}
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * Método encargado de hacer el llamado al microservicio que crea una empresa
	 * 
	 * @param empresaModeloDTO el DTO que contiene los datos de la empresa a crear
	 * @return Long con el id de la empresa creada
	 */
	private Long crearEmpresa(EmpresaModeloDTO empresaModeloDTO) {
		CrearEmpresaRutine nuevoEmpresa = new CrearEmpresaRutine();
		return nuevoEmpresa.crearEmpresa(empresaModeloDTO, entityManagerCore);
	}

	/**
	 * Método que retorna el identificador de un municipio según el código dado
	 * 
	 * @param codigoMunicipio
	 * @return identificador del municipio en la tabla de la base de datos
	 */
	private Short ubicarMunicipio(String codigoMunicipio) {
		if (municipiosCodigoId.containsKey(codigoMunicipio)) {
			return municipiosCodigoId.get(codigoMunicipio);
		}
		return 1;
	}

	/**
	 * Método que activa el empleador y envía email de activación de cuenta web
	 * 
	 * @param aportante
	 * @param idRegistroGeneral
	 */
	private void activarEmpleador(AporteDTO aportante, Long idRegistroGeneral) {
		String firmaServicio = "AportesPilaBusiness.procesarActivacionEmpleador(ActivacionEmpleadorDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		try {
			ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
			datosReintegro.setIdAportante(aportante.getIdEmpresa());
			datosReintegro
					.setFechaReintegro(FuncionesUtilitarias.obtenerFechaMillis(aportante.getPeriodoAporte() + "-01"));
			datosReintegro.setCanalReintegro(aportante.getCanal());
			datosReintegro.setIdRegistroGeneral(idRegistroGeneral);

			ActualizarEstadoEmpleadorPorAportesRutine actualizarEstadoEmpleador;
			actualizarEstadoEmpleador = new ActualizarEstadoEmpleadorPorAportesRutine();
			actualizarEstadoEmpleador.actualizarEstadoEmpleadorPorAportes(datosReintegro, entityManagerCore);

			activarCuentaPersona(aportante.getEmpresaAportante().getTipoIdentificacion(),
					aportante.getEmpresaAportante().getNumeroIdentificacion());
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * Método para reactivar la cuenta web de un empelador a partie de su ID
	 */
	private void activarCuentaPersona(TipoIdentificacionEnum tipoId, String numId) {
		String firmaServicio = "AportesPilaBusiness.activarCuentaPersona(TipoIdentificacionEnum, String)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		if (tipoId != null && numId != null) {
			UsuarioCCF usuario = buscarUsuario(tipoId, numId);
			if (usuario != null) {
				usuario.setUsuarioActivo(Boolean.TRUE);
				usuario.setReintegro(Boolean.TRUE);

				ActualizarUsuarioCCF actualizarUsuarioCCF = new ActualizarUsuarioCCF(usuario);
				actualizarUsuarioCCF.execute();
			}
		} else {
			TechnicalException e = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO
					+ " - No se cuenta con el tipo y número de identificación del usuario a activar");
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw e;
		}
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * Método para consultar un usuario a partir de su username
	 */
	private UsuarioCCF buscarUsuario(TipoIdentificacionEnum tipoId, String numId) {
		String firmaServicio = "AportesCompositeBusiness.buscarUsuario(TipoIdentificacionEnum, String)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		UsuarioCCF usuario = null;

		ConsultarUsuarios consultarUsuarios = new ConsultarUsuarios();
		consultarUsuarios.execute();
		List<UsuarioDTO> usuarios = consultarUsuarios.getResult();

		String userName = tipoId.name().toLowerCase() + "_" + numId;

		if (usuarios != null) {
			for (UsuarioDTO usuarioDTO : usuarios) {
				if (usuarioDTO.getNombreUsuario().equalsIgnoreCase(userName)
						|| (tipoId.equals(usuarioDTO.getTipoIdentificacion())
								&& numId.equalsIgnoreCase(usuarioDTO.getNumIdentificacion()))) {
					usuario = new UsuarioCCF(usuarioDTO);
				}
			}
		}

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return usuario;
	}

	/**
	 * Método que evalua las condiciones que determinan si el empleador se debe
	 * activar o no
	 */
	private Boolean esReintegrable(AporteDTO aportante) {
		logger.info("aportante " +aportante.toString());
		logger.info("aportante.getIdEmpleador() " +aportante.getIdEmpleador());
		logger.info("aportante.getEsEmpleadorReintegrable() " +aportante.getEsEmpleadorReintegrable());
		logger.info("aportante.getTieneCotizanteDependienteReintegrable() " +aportante.getTieneCotizanteDependienteReintegrable());
		logger.info("aportante.getEstadoEmpleador() " +aportante.getEstadoEmpleador());
		if (aportante.getIdEmpleador() != null && Boolean.TRUE.equals(aportante.getEsEmpleadorReintegrable())
				&& Boolean.TRUE.equals(aportante.getTieneCotizanteDependienteReintegrable())
				&& !EstadoEmpleadorEnum.ACTIVO.toString().equals(aportante.getEstadoEmpleador())) {
			logger.info("ingresa el true ----esReintegrable----- ");
			return Boolean.TRUE;
		}
		logger.info("ingresa el false ----esReintegrable----- ");
		return Boolean.FALSE;
	}

	/**
	 * Método encargado de hacer el llamado al microservicio que crea una persona
	 * 
	 * @param personaModeloDTO el DTO que contiene los datos de la persona a crear
	 * @return Long con el id de la persona creada
	 */
	private Long crearPersona(PersonaModeloDTO personaModeloDTO) {
		CrearPersonaRutine nuevaPersona = new CrearPersonaRutine();
		return nuevaPersona.crearPersona(personaModeloDTO, entityManagerCore);
	}

	@Override
	public void crearPilaEstadoTransitorio(PilaEstadoTransitorio pilaEstadoTransitorio) {

		String firmaServicio = "Process -> AportesPilaBusiness.crearPilaEstadoTransitorio(PilaEstadoTransitorio)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.info("**__** inicio crearPilaEstadoTransitorio" );
		try{
	    entityManagerCore.persist(pilaEstadoTransitorio);
		}catch(Exception e ){
		logger.info("**__** catch crearPilaEstadoTransitorio error "+e );
		}
	
		logger.info("**__** FIN crearPilaEstadoTransitorio" );
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

	}
	/**
	 * Método encargado de la consulta o creación de una empresa tramitadora de
	 * aporte
	 * 
	 * @param aporte
	 */
	private EmpresaModeloDTO prepararTramitador(AporteDTO aporte) {
		String firmaMetodo = "AportesPilaBusiness.prepararTramitador(AporteDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TipoIdentificacionEnum tipoIdTram = aporte.getTipoDocTramitador();
		String numIdTram = aporte.getIdTramitador();

		Persona tramitador = new Persona();
		tramitador.setTipoIdentificacion(tipoIdTram);
		tramitador.setNumeroIdentificacion(numIdTram);

		if (aporte.getDigVerTramitador() != null) {
			tramitador.setDigitoVerificacion(aporte.getDigVerTramitador());
		}
		if (aporte.getNombreTramitador() != null) {
			tramitador.setRazonSocial(aporte.getNombreTramitador());
		}
		Empresa empresaTramitadora = new Empresa();
		empresaTramitadora.setPersona(tramitador);
		EmpresaModeloDTO empresaTramitadoraModeloDTO = new EmpresaModeloDTO();
		empresaTramitadoraModeloDTO.convertToDTO(empresaTramitadora);
		empresaTramitadoraModeloDTO.setCreadoPorPila(true);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return empresaTramitadoraModeloDTO;
	}

	/**
	 * Método que crea cada cotizante como persona
	 * 
	 * @param cotizantesPorCrear
	 * @param idRegistroGeneral
	 */
	private void crearCotizante(Long idRegistroGeneral) {
		String firmaServicio = "AportesPilaBusiness.crearCotizante(ActivacionEmpleadorDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
logger.info("**__**AportespilabusinessCREAR COTIZANTE SP CONSULTAR_COTIZANTES_POR_CREAR Cpiado Aportes"+idRegistroGeneral);
			try {
			StoredProcedureQuery query = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_POR_CREAR);
			query.setParameter("idRegistroGeneral", idRegistroGeneral);
			query.execute();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR  + " :: Hubo un error en el SP: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

/*
		List<AporteDTO> cotizantesPorCrear = consultasPila
				.consultarDatosCotizantesTemporalesPorCrear(idRegistroGeneral);

		for (AporteDTO cotizantePorCrear : cotizantesPorCrear) {
			cotizantePorCrear.getPersonaCotizante().setCreadoPorPila(Boolean.TRUE);
			cotizantePorCrear.getPersonaCotizante().getUbicacionModeloDTO()
					.setIdMunicipio(ubicarMunicipio(cotizantePorCrear.getCodigoMunicioCotizante()));
			cotizantePorCrear.setIdPersona(crearPersona(cotizantePorCrear.getPersonaCotizante()));
			logger.info("**__**LOG COPIADOS APORTES  cotizantePorCrear getIdPersona: "+cotizantePorCrear.getIdPersona());
		}
		logger.info("**__**LOG COPIADOS APORTES idRegistroGeneral: "+idRegistroGeneral);

		consultasCore.copiarDatosAportes(idRegistroGeneral);
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		*/
	}


	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long contarTemAportesPendientes(Long idPlanilla) {
		return consultasPila.contarTemAportesPendientes(idPlanilla);
	}
	
	@Override
    public Boolean validarProcesadoNovedad(Long idPlanilla) {
        String firmaServicio = "AportesBusiness.validarProcesadoNovedad(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Boolean result =false;
		Object respuesta = consultasPila.validarProcesadoNovedades(idPlanilla);
		logger.info("respuesta : "+ respuesta.toString());
		if(respuesta.toString().equals("true") || respuesta.toString().equals("1")){
			result = true;
		}
		logger.info("result : "+ result);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
}