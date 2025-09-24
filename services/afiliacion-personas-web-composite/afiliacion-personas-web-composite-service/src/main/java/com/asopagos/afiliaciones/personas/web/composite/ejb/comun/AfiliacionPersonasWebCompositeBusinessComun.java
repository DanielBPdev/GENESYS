/**
 * 
 */
package com.asopagos.afiliaciones.personas.web.composite.ejb.comun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.web.clients.ActualizarEstadoCargueMultiple;
import com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService;
import com.asopagos.afiliados.clients.CrearAfiliado;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ListaDatoValidacionDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.validaciones.clients.ValidarCargaMultipleAfiliaciones;
import com.asopagos.validaciones.clients.ValidarCargaMultipleAfiliacionesStoredProcedure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import javax.ejb.Asynchronous;

/**
 * @author jusanchez
 *
 */
@Stateless
public class AfiliacionPersonasWebCompositeBusinessComun implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Referencia al logger
	 */
	private static ILogger logger = LogManager.getLogger(AfiliacionPersonasWebCompositeService.class);
	/**
	 * Constante que contendra las horas del día
	 */
	private static final Long HORA_DIA = (long) 24;
	/**
	 * Constante que contendra los minutos con los que cuenta una hora
	 */
	private static final Long MINUTO_HORA = (long) 60;
	/**
	 * Constante que conendra los milisegundos de un minuto
	 */
	private static final Long MILISEGUNDO_MINUTO = (long) 1000;

	private static final String ID_CARGUE_MULTIPLE = "idCargueMultiple";
	private static final String LST_SOLICITUDES = "lstIdSolicitud";
	private static final String CIUDAD_SOLICITUD = "ciudadSolicitud";
	private static final String RAZON_SOCIAL = "razonSocial";
	private static final String TIPO_IDENTIFICACION_EMPLEADOR = "tipoIdentificacionEmpleador";
	private static final String NUMERO_IDENTIFICACION_EMPLEADOR = "numeroIdentificacionEmpleador";
	private static final String CORREO_EMPLEADOR = "correoEmpleador";
	private static final String USER_DTO = "userDTO";
	private static final String TOKEN = "token";

	@Resource
	private TimerService timerService;

	@PostConstruct
	private void init() {

	}

	/**
	 * Metodo encargado de llamar al cliente de actualizarEstadoCargueMultiple
	 * 
	 * @param idCargue,
	 *            id del cargue a realizar la actualizacion
	 * @param estadoCargueMultiple,
	 *            Nuevo estado con el que quedara el cargue
	 */
	public static void actualizarEstadoCargueMultiple(Long idCargue, EstadoCargaMultipleEnum estadoCargueMultiple) {
		logger.debug("Inicia actualizarEstadoCargueMultiple (Long,EstadoCargaMultiplePersonaEnum)");
		Boolean empleadorCargue = false;
		ActualizarEstadoCargueMultiple actualizarEstado = new ActualizarEstadoCargueMultiple(idCargue, empleadorCargue,
				estadoCargueMultiple);
		actualizarEstado.execute();
		logger.debug("Finaliza actualizarEstadoCargueMultiple (Long,EstadoCargaMultiplePersonaEnum)");
	}

	/*
	 * Ejecución del timer
	 */
	@Timeout
	public static void execute(Timer timer) {
		// Se obtiene los elementos a utilizar en el temporizador
		Map<String, Object> elemento = (Map<String, Object>) timer.getInfo();
		List<Long> lstIdSolicitud = new ArrayList<Long>();
		Long idCargue = (long) elemento.get(ID_CARGUE_MULTIPLE);
		lstIdSolicitud = (List<Long>) elemento.get(LST_SOLICITUDES);
		String ciudadSolicitud = (String) elemento.get(CIUDAD_SOLICITUD);
		String razonSocial = (String) elemento.get(RAZON_SOCIAL);
		TipoIdentificacionEnum tipoIdentificacionEmpleador = (TipoIdentificacionEnum) elemento
				.get(TIPO_IDENTIFICACION_EMPLEADOR);
		String numeroIdentificacionEmpleador = (String) elemento.get(NUMERO_IDENTIFICACION_EMPLEADOR);
		String correoEmpleador = (String) elemento.get(CORREO_EMPLEADOR);
		UserDTO userDTO = (UserDTO) elemento.get(USER_DTO);
		String token = (String) elemento.get(TOKEN);
		/*
		 * Se genera el comunicado 03 NOTIFICACION_INTENTO_AFILIACION
		 */
		Map<String, Object> paramsComunicado;
		for (Long idSolicitud : lstIdSolicitud) {
			SolicitudAfiliacionPersonaDTO solicitudAfiliacion = consultarSolicitudAfiliacionPersona(idSolicitud, token);
			if (solicitudAfiliacion.getEstadoSolicitud().equals(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA)) {
				// Se registra el intento de afiliacion
				IntentoAfiliacionInDTO intentoAfiliacion = new IntentoAfiliacionInDTO();
				intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES); 	
				intentoAfiliacion.setIdSolicitud(idSolicitud);
				intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION);
				intentoAfiliacion.setFechaInicioProceso(new Date());
				intentoAfiliacion.getSolicitud().setResultadoProceso(ResultadoProcesoEnum.DESISTIDA);
				registrarIntentoAfiliacion(intentoAfiliacion);
				//actualizarEstadoSolicitudPersona(idSolicitud, EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA);
				actualizarEstadoSolicitudPersona(idSolicitud, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			}
		}
		paramsComunicado = new HashMap<>();
		paramsComunicado.put("ciudadSolicitud", ciudadSolicitud);
		paramsComunicado.put("fechaDelSistema", new Date().toString());
		paramsComunicado.put("razonSocial/Nombre", razonSocial);
		paramsComunicado.put("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador.toString());
		paramsComunicado.put("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador);
		
		if (correoEmpleador != null && lstIdSolicitud!=null && !lstIdSolicitud.isEmpty()) {
			Map<String, String> paramComunicado = transformarMapaStringString(paramsComunicado);
			paramComunicado.put("correos", correoEmpleador);
			//se debe descomentar para enviar el comunicado adecuado 
			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
					EtiquetaPlantillaComunicadoEnum.NTF_INT_AFL_DEP, paramComunicado,
					TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, null, lstIdSolicitud.get(0), null);
			
		}
		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoCargueMultiple(idCargue,
				EstadoCargaMultipleEnum.CERRADO);
	}

	/**
	 * Método encargado de generar temporizador
	 * 
	 * @param numeroDiaTemporizador
	 * @param idCargueMultiple
	 */
	public void generarTemporizador(Long numeroDiaTemporizador, Long idCargueMultiple, List<Long> lstIdSolicitud,
			String ciudadSolicitud, String razonSocial, TipoIdentificacionEnum tipoIdentificacionEmpleador,
			String numeroIdentificacionEmpleador, String correoEmpleador, UserDTO userDTO) {
		logger.debug("Entre generarTemporizador(Long,Long)");

		Map<String, Object> elemento = new HashMap<String, Object>();
		elemento.put(ID_CARGUE_MULTIPLE, idCargueMultiple);
		elemento.put(LST_SOLICITUDES, lstIdSolicitud);
		elemento.put(CIUDAD_SOLICITUD, ciudadSolicitud);
		elemento.put(RAZON_SOCIAL, razonSocial);
		elemento.put(TIPO_IDENTIFICACION_EMPLEADOR, tipoIdentificacionEmpleador);
		elemento.put(NUMERO_IDENTIFICACION_EMPLEADOR, numeroIdentificacionEmpleador);
		elemento.put(CORREO_EMPLEADOR, correoEmpleador);
		elemento.put(USER_DTO, userDTO);
		elemento.put(TOKEN, null);
		if (!lstIdSolicitud.isEmpty()) {
			Long horaDia = numeroDiaTemporizador * HORA_DIA;
			Long minutoDia = horaDia * MINUTO_HORA;
			Long milisegundo = minutoDia * MILISEGUNDO_MINUTO;
			TimerConfig timerConfig = new TimerConfig();
			timerConfig.setInfo((Serializable) elemento);
			timerService.createSingleActionTimer(milisegundo, timerConfig);
			logger.debug("Finalizo generarTemporizador(Long,Long)");
		}
	}

	/**
	 * 
	 * @param proceso
	 * @param objetoValidacion
	 * @param bloque
	 * @param listaDatosValidacion
	 * @return
	 */
	public static ListaDatoValidacionDTO validarPersonasCargaMultiple(String bloque, ProcesoEnum proceso,
			String objetoValidacion, List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO) {
		logger.debug(
				"Inicia validarPersonasCargaMultiple(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
		ValidarCargaMultipleAfiliaciones validarPersona = new ValidarCargaMultipleAfiliaciones(bloque, proceso,
				objetoValidacion, lstTrabajadorCandidatoDTO);
		validarPersona.execute();
		ListaDatoValidacionDTO solicitudAfiliacionPerona = validarPersona.getResult();
		logger.debug(
				"Finaliza validarPersonasCargaMultiple(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
		return solicitudAfiliacionPerona;
	}
	public static ListaDatoValidacionDTO validarPersonasCargaMultipleStoredProcedure(String bloque, ProcesoEnum proceso,
			String objetoValidacion, List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO) {
		logger.info(
				"Inicia validarPersonasCargaMultipleStoredProcedure(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
		ValidarCargaMultipleAfiliacionesStoredProcedure validarPersona = new ValidarCargaMultipleAfiliacionesStoredProcedure(bloque, proceso,
				objetoValidacion, lstTrabajadorCandidatoDTO);
		validarPersona.execute();
		ListaDatoValidacionDTO solicitudAfiliacionPerona = validarPersona.getResult();
		logger.debug(
				"Finaliza validarPersonasCargaMultipleStoredProcedure(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
		return solicitudAfiliacionPerona;
	}
	/**
	 * Método encargado de crear registros temporales para una solicitud de
	 * afiliacion de persona
	 * 
	 * @param afiliarTrabajadorCandidatoDTO,
	 *            trabajador candidato dto
	 * @return retorna el id de la solicitud global
	 */
	public static Long crearRegistroTemporalSolicitudTrabajadorCandidato(
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO) {
		AfiliadoInDTO afiInDTOCreado = crearAfiliado(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO());
		afiInDTOCreado.setCodigoCargueMultiple(afiliarTrabajadorCandidatoDTO.getCodigoCargueMultiple());
		Long idSolGlobal = null;
		if (afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdSolicitudGlobal() != null) {
			idSolGlobal = afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdSolicitudGlobal();
		} else {
			afiInDTOCreado.setCanalRecepcion(CanalRecepcionEnum.WEB);
			afiInDTOCreado.getPersona().setTipoTransaccion(afiliarTrabajadorCandidatoDTO.getTipoTransaccion());
			afiInDTOCreado.getPersona().setClasificacion(afiliarTrabajadorCandidatoDTO.getClasificacion());
			idSolGlobal = crearSolicitudAfiliacionPersona(afiInDTOCreado);
		}
		afiliarTrabajadorCandidatoDTO.setAfiliadoInDTO(afiInDTOCreado);
		afiliarTrabajadorCandidatoDTO.setIdSolicitudGlobal(idSolGlobal);
		guardarInformacionTemporal(afiliarTrabajadorCandidatoDTO);
		return idSolGlobal;
	}

	// TODO 01: Crear metodo static void registrarSolicitudAfiliacionPersonaCargueMultiple(AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO)
	public static AfiliarTrabajadorCandidatoDTO registrarSolicitudAfiliacionPersonaCargueMultiple(AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
		AfiliadoInDTO afiInDTOCreado = crearAfiliado(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO());
		afiInDTOCreado.setCodigoCargueMultiple(afiliarTrabajadorCandidatoDTO.getCodigoCargueMultiple());
		Long idSolGlobal = null;
		if (afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdSolicitudGlobal() != null) {
			idSolGlobal = afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdSolicitudGlobal();
		} else {
			afiInDTOCreado.setCanalRecepcion(CanalRecepcionEnum.WEB);
			afiInDTOCreado.getPersona().setTipoTransaccion(afiliarTrabajadorCandidatoDTO.getTipoTransaccion());
			afiInDTOCreado.getPersona().setClasificacion(afiliarTrabajadorCandidatoDTO.getClasificacion());
			idSolGlobal = crearSolicitudAfiliacionPersona(afiInDTOCreado);
		}
		afiliarTrabajadorCandidatoDTO.setAfiliadoInDTO(afiInDTOCreado);
		return afiliarTrabajadorCandidatoDTO;
	}
	// TODO 02: Copiar lineas de 242 a  252 al nuevo metodo
	// TODO 03: Pasar al todo 04

	public static void guardarInformacionTemporalCargueMultiple (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
		guardarInformacionTemporal(afiliarTrabajadorCandidatoDTO);
	}

	public static void guardarInformacionTemporalCargueMultipleTotal (List<AfiliarTrabajadorCandidatoDTO> totalAfiliarTrabajadorCandidatoDTO, Long idSolicitud){
		 try {
            ObjectMapper mapper = new ObjectMapper();
			String jsonPayload = mapper.writeValueAsString(totalAfiliarTrabajadorCandidatoDTO);
            Long idDatoTemporalSolicitud = new Long(0);
            
            // logger.info("jsonPayload 1:" + jsonPayload);
			if(jsonPayload.startsWith("\"{\\")){
                logger.info("in");
                jsonPayload = jsonPayload.replaceFirst("\"{\\", "{");
                jsonPayload = jsonPayload.replace("\\", "");
            }
            
             // logger.info("jsonPayload 2:" + jsonPayload);
            GuardarDatosTemporales respuestaGuardarDatoTemporal = new GuardarDatosTemporales(idSolicitud, jsonPayload);
            respuestaGuardarDatoTemporal.execute();
            idDatoTemporalSolicitud = (long) respuestaGuardarDatoTemporal.getResult();
        } catch (JsonProcessingException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
	}
	/**
	 * Metodo encargado de llamar al cliente que realiza la creacion del
	 * afiliado
	 * 
	 * @param afiliadoInDTO,
	 *            afiliadoInDTO a realizar la creacion
	 * @return retorna el afiliadoInDTO que fue creado
	 */
	public static AfiliadoInDTO crearAfiliado(AfiliadoInDTO afiliadoInDTO, String... tokens) {
		logger.debug("Inicia crearAfiliado( AfiliadoInDTO )");
		AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
		CrearAfiliado crearAfiliadoService;
		crearAfiliadoService = new CrearAfiliado(afiliadoInDTO);
		if (tokens != null && tokens.length > 0) {
			crearAfiliadoService.setToken(tokens[0]);
		}
		crearAfiliadoService.execute();
		afiliadoDTO = (AfiliadoInDTO) crearAfiliadoService.getResult();
		logger.debug("Finaliza crearAfiliado( AfiliadoInDTO )");
		return afiliadoDTO;
	}

	/**
	 * Metodo encargado de llamar el cliente Rest de de la creacion de una
	 * solicitud afiliacion persona
	 * 
	 * @param afiliadoInDTO,
	 *            dto que necesita el afiliado
	 * @return retorna el id de solicitud que se encarga de devolver el cliente
	 *         rest
	 */
	public static Long crearSolicitudAfiliacionPersona(AfiliadoInDTO afiliadoInDTO) {
		logger.debug("Inicia crearSolicitudAfiliacionPersona(AfiliadoInDTO)");
		Long idSolicitud = new Long(0);
		CrearSolicitudAfiliacionPersona solicitudRespuesta = new CrearSolicitudAfiliacionPersona(afiliadoInDTO);
		solicitudRespuesta.execute();
		idSolicitud = (long) solicitudRespuesta.getResult();
		logger.debug("Finaliza crearSolicitudAfiliacionPersona(AfiliadoInDTO)");
		return idSolicitud;
	}

	/**
	 * Metodo encargado de llamar el cliente del servicio GuardarDatosTemporales
	 * 
	 * @param afiliarTrabajadorCandidatoDTO,
	 *            dto que contiene la informacion a afiliar del
	 *            trabajadorCandidato
	 */
	@Asynchronous
	 public static void guardarInformacionTemporal(AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO) {
        try {
            ObjectMapper mapper = new ObjectMapper();
			String jsonPayload = mapper.writeValueAsString(afiliarTrabajadorCandidatoDTO);
            Long idDatoTemporalSolicitud = new Long(0);
            
            // logger.info("jsonPayload 1:" + jsonPayload);
			if(jsonPayload.startsWith("\"{\\")){
                logger.info("in");
                jsonPayload = jsonPayload.replaceFirst("\"{\\", "{");
                jsonPayload = jsonPayload.replace("\\", "");
            }
            
             // logger.info("jsonPayload 2:" + jsonPayload);
            GuardarDatosTemporales respuestaGuardarDatoTemporal = new GuardarDatosTemporales(
                    afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), jsonPayload);
            respuestaGuardarDatoTemporal.execute();
            idDatoTemporalSolicitud = (long) respuestaGuardarDatoTemporal.getResult();
        } catch (JsonProcessingException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	/**
	 * Metodo encargado de registrar el intento de afiliacion
	 * 
	 * @param intentoAfiliacionInDTO,
	 *            dto que llega por parametro
	 */
	public static void registrarIntentoAfiliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO) {
		logger.debug("Inicia registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
		RegistrarIntentoAfliliacion registrarIntentoAfliliacion = new RegistrarIntentoAfliliacion(
				intentoAfiliacionInDTO);
		registrarIntentoAfliliacion.execute();
		logger.debug("Finaliza registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
	}

	/**
	 * Método encargado de llamar al servicio que se encarga de almacenar los
	 * archivos
	 * 
	 * @param infoFile
	 * @return
	 */
	public static String almacenarArchivo(InformacionArchivoDTO infoFile) {
		AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
		String idECM = "";
		almacenarArchivo.execute();
		// idECM = (String) almacenarArchivo.getResult();
		idECM = (String) almacenarArchivo.getResult().getIdentificadorDocumento();
		return idECM;
	}

	public static String generarTokenAccesoCore() {
		GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
		accesoCore.execute();
		TokenDTO token = accesoCore.getResult();
		return token.getToken();
	}

	/**
	 * Método que hace la peticion REST al servicio de actualizar el estado de
	 * una Solicitud de Afiliacion de Persona indicado en
	 * <code>EstadoSolicitudAfiliacionPersonaEnum</code>
	 * 
	 * @param idSolicitudGlobal
	 *            <code>Long</code> El identificador de la solicitud global de
	 *            afiliacion de persona
	 * @param estadoSolcitudAfiliacionPersona
	 *            <code>EstadoSolicitudAfiliacionPersonaEnum</code> Enumeración
	 *            que representa los estados de la solicitud de afiliación de
	 *            una persona
	 */
	public static void actualizarEstadoSolicitudPersona(Long idSolicitudGlobal,
			EstadoSolicitudAfiliacionPersonaEnum estadoSolcitudAfiliacionPersona, String... tokens) {
		logger.debug("Inicia actualizarEstadoSolicitudPersona( Long, EstadoSolicitudAfiliacionPersonaEnum )");
		ActualizarEstadoSolicitudAfiliacionPersona actualizarSoliticutdAfilPersonaService = new ActualizarEstadoSolicitudAfiliacionPersona(
				idSolicitudGlobal, estadoSolcitudAfiliacionPersona);
		if (tokens != null && tokens.length > 0) {
			actualizarSoliticutdAfilPersonaService.setToken(tokens[0]);
		}
		actualizarSoliticutdAfilPersonaService.execute();
		logger.debug(
				"Finaliza actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
	}

	/**
	 * Metodo encargado de consultar una soliciutd afiliacion persona
	 * 
	 * @param idSolicitud,
	 *            id por el cual se consulta la solicitud
	 * @return retorna SolicitudAfiliacionPersonaDTO
	 */
	public static SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersona(Long idSolicitud,
			String... tokens) {
		logger.debug("Inicia consultarSolicitudAfiliacionPersona(Long)");
		ConsultarSolicitudAfiliacionPersona consultar = new ConsultarSolicitudAfiliacionPersona(idSolicitud);
		if (tokens != null && tokens.length > 0) {
			consultar.setToken(tokens[0]);
		}
		consultar.execute();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPerona = consultar.getResult();

		logger.debug("Finaliza consultarSolicitudAfiliacionPersona(Long)");
		return solicitudAfiliacionPerona;
	}

	/**
	 * Método encargado de realizar el llamado al cliente que actualiza el
	 * estado del cargue
	 * 
	 * @param idCargue,
	 *            id del cargue a actualizar
	 * @param consolaEstadoCargueProcesoDTO,
	 *            datos que seran actualizados
	 */
	public static void actualizarCargueConsolaEstado(Long idCargue,
			ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
		ActualizarCargueConsolaEstado actualizacion = new ActualizarCargueConsolaEstado(idCargue,
				consolaEstadoCargueProcesoDTO);
		actualizacion.execute();
	}

	/**
	 * Método encargado de construir el envio de un comunicado
	 * 
	 * @param etiquetaPlantillaComunicado,
	 *            Etiqueta que sera enviada en el comunicado
	 * @param paramsComunicado,
	 *            lista de parametros del comunicado
	 * @param procesoEvento,
	 *            Tipo de transaccion que se realiza
	 * @param idInstanciaProceso,
	 *            id Instancia del proceso
	 * @param idSolicitud,
	 *            id de la solicitud
	 */
	public static void enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado,
			Map<String, String> paramsComunicado, TipoTransaccionEnum tipoTransaccion, String idInstanciaProceso,
			Long idSolicitud, Long idPersona) {
		logger.debug(
				"Inicia enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum,Map<String, String>,TipoTransaccionEnum,String,Long)");
		try {
			NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
			notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(etiquetaPlantillaComunicado);
			notificacionParametrizadaDTO.setParams(paramsComunicado);
			notificacionParametrizadaDTO.setIdInstanciaProceso(idInstanciaProceso);
			notificacionParametrizadaDTO.setIdSolicitud(idSolicitud);
			notificacionParametrizadaDTO.setProcesoEvento(tipoTransaccion.getProceso().name());
			notificacionParametrizadaDTO.setTipoTx(tipoTransaccion);
			if(idPersona!=null)
				notificacionParametrizadaDTO.setIdPersona(idPersona);
			if(paramsComunicado!=null&&!paramsComunicado.isEmpty()&&paramsComunicado.containsKey("correos")){
			    notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
			    List<String> destinatarios = new ArrayList<>();
			    for (Map.Entry<String, String> param : paramsComunicado.entrySet()) {
			        if (param.getKey()=="correos"){
			            destinatarios.add(param.getValue());
			        }
			    }
			    notificacionParametrizadaDTO.setDestinatarioTO(destinatarios);
			}
			
			enviarCorreoParametrizado(notificacionParametrizadaDTO);
		} catch (Exception e) {
			// este es el caso en que el envío del correo del comunicado no debe
			// abortar el proceso de afiliación
			// TODO Mostrar solo el log o persistir el error la bd ?
			logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
		}
		logger.debug(
				"Finaliza enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum,Map<String, String>,TipoTransaccionEnum,String,Long)");
	}

	/**
	 * Método encargado de enviar un comunicado construido
	 * 
	 * @param notificacion,
	 *            notificacion a enviar
	 */
	public static void enviarComunicadoConstruido(NotificacionParametrizadaDTO notificacion) {
		try {
			enviarCorreoParametrizado(notificacion);
		} catch (Exception e) {
			// este es el caso en que el envío del correo del comunicado no debe
			// abortar el proceso de afiliación
			// TODO Mostrar solo el log o persistir el error la bd ?
			logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
		}
	}

	/**
	 * Método encargado de llamar el cliente del servicio envio de correo
	 * parametrizado
	 * 
	 * @param notificacion,
	 *            notificación dto que contiene la información del correo
	 */
	public static void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
		logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO)");		
		EnviarNotificacionComunicado enviarComunicado=new EnviarNotificacionComunicado(notificacion);
		enviarComunicado.execute();
		logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
	}
	
	/**
	 * Método encargado de convertir un mapa de <String, Object> a un mapa
	 * <String,String
	 * 
	 * @param paramStringObject,mapa
	 *            a realizar la conversion
	 * @return retorna el mapa Map<String, String>
	 */
	public static Map<String, String> transformarMapaStringString(Map<String, Object> paramStringObject) {
		Map<String, String> paramStringString = new HashMap<String, String>();
		for (Map.Entry<String, Object> registroMap : paramStringObject.entrySet()) {
			if (registroMap.getValue() instanceof String) {
				paramStringString.put(registroMap.getKey(), (String) registroMap.getValue());
			}
		}
		return paramStringString;
	}

	/**
	 * Método encargado de transformar un mapa <String,String> a un mapa
	 * Map<String, Object>
	 * 
	 * @param paramStringString,
	 *            mapa a realizar la conversion
	 * @return retorna el mapa Map<String, Object>
	 */
	public static Map<String, Object> transformarMapaStringObject(Map<String, String> paramStringString) {
		Map<String, Object> paramStringObject = new HashMap<String, Object>();
		for (Map.Entry<String, String> registroMap : paramStringString.entrySet()) {
			if (registroMap.getValue() instanceof String) {
				paramStringObject.put(registroMap.getKey(), (Object) registroMap.getValue());
			}
		}
		return paramStringObject;
	}
}