package com.asopagos.afiliaciones.personas.web.composite.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.asopagos.afiliaciones.clients.BuscarSolicitud;
import com.asopagos.afiliaciones.clients.ConsultarProductosNoConforme;
import com.asopagos.afiliaciones.clients.CrearProductosNoConforme;
import com.asopagos.afiliaciones.clients.GuardarInstanciaProceso;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersonaAfiliada;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.afiliaciones.personas.web.clients.RegistrarCargueMultiple;
import com.asopagos.afiliaciones.personas.web.clients.ValidarEstructuraContenidoArchivo;
import com.asopagos.afiliaciones.personas.web.composite.dto.CancelacionSolicitudPersonasDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.ConsultaConceptoEscalamientoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.GuardarTemporalAfiliacionPersona;
import com.asopagos.afiliaciones.personas.web.composite.dto.IntentoAfiliacionesComunicadoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.ReintegraAfiliadoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.VerificarResultadoSolicitudAfiliacionPersonaDTO;
import com.asopagos.afiliaciones.personas.web.composite.ejb.comun.AfiliacionPersonasWebCompositeBusinessComun;
import com.asopagos.afiliaciones.personas.web.composite.enums.AccionFormularioEnum;
import com.asopagos.afiliaciones.personas.web.composite.enums.AccionResultadoConceptoEscalamientoEnum;
import com.asopagos.afiliaciones.personas.web.composite.enums.AccionResultadoEnum;
import com.asopagos.afiliaciones.personas.web.composite.enums.ResultadoRegistroInformacionContactoEnum;
import com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService;
import com.asopagos.afiliaciones.personas.web.composite.service.ValidacionPersonasWebCompositeService;
import com.asopagos.afiliados.clients.ActualizarAfiliado;
import com.asopagos.afiliados.clients.ActualizarEstadoBeneficiario;
import com.asopagos.afiliados.clients.ActualizarEstadoRolAfiliado;
import com.asopagos.afiliados.clients.ActualizarFechaAfiliacionRolAfiliado;
import com.asopagos.afiliados.clients.CalcularCategoriasAfiliado;
import com.asopagos.afiliados.clients.ConsultarAfiliado;
import com.asopagos.afiliados.clients.ConsultarAfiliadosUnGrupoFamiliar;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarGrupoFamiliar;
import com.asopagos.afiliados.clients.GuardarDatosIdentificacionYUbicacion;
import com.asopagos.afiliados.clients.GuardarInformacionLaboral;
import com.asopagos.afiliados.clients.RegistrarBeneficiario;
import com.asopagos.aportes.clients.ConsultarAporteDetalladoPorIdsGeneralPersona;
import com.asopagos.aportes.clients.ConsultarAporteGeneralPersona;
import com.asopagos.aportes.clients.CrearActualizarAporteDetallado;
import com.asopagos.aportes.clients.CrearActualizarAporteGeneral;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constantes.parametros.clients.ConsultarConstantesCaja;
import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliadoSolicitudDTO;
import com.asopagos.dto.AnalizarSolicitudAfiliacionDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.CategoriaDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.ListadoSolicitudesAfiliacionDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ProductoNoConformeDTO;
import com.asopagos.dto.ResultadoGeneralProductoNoConformeBeneficiarioDTO;
import com.asopagos.dto.ResultadoGeneralValidacionBeneficiarioDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.empleadores.clients.ConsultarDatosTemporalesEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmailEmpleadores;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empleadores.clients.GuardarDatosTemporalesEmpleador;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.composite.clients.EjecutarRetiroTrabajadores;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.personas.clients.ConsultarUbicacion;
import com.asopagos.personas.clients.ConsultarUbicacionPersona;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.exception.ValidacionFallidaException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ActualizarSolicitudEscalada;
import com.asopagos.solicitudes.clients.ConsultarSolicitudEscalada;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import com.asopagos.solicitudes.clients.EscalarSolicitud;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.EliminarTokenAcceso;
import com.asopagos.usuarios.clients.EstaUsuarioActivo;
import com.asopagos.usuarios.clients.GenerarTokenAcceso;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.ReenviarCorreoEnrolamiento;
import com.asopagos.usuarios.dto.InformacionReenvioDTO;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.util.ExcelUtils;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.com.heinsohn.lion.common.util.Interpolator;
import com.asopagos.dto.AfiliacionPersonaWebMasivaDTO;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAfiliacionPersonaSolo;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudCargueMultiple;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.asopagos.afiliaciones.personas.web.clients.ProcesarAfiliacionPersonasWebMasiva;
import com.asopagos.dto.AfiliacionArchivoPlanoDTO;
import com.google.gson.reflect.TypeToken;
import com.asopagos.afiliaciones.personas.web.composite.ejb.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
/**
 * <b>Descripcion:</b> EJB que implementa los servicios de composición del
 * proceso de afiliación de personas WEB
 *
 * @author Julian Andres Sanchez
 *         <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez@heinsohn.com.co
 *         </a>
 */
@Stateless
public class AfiliacionPersonasWebCompositeBusiness implements AfiliacionPersonasWebCompositeService {

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(AfiliacionPersonasWebCompositeService.class);
	/**
	 * Constante que indica el nombre de la senial formularioDiligenciado a
	 * enviar
	 */
	private static final String FORMULARIO_DILIGENCIADO = "formularioDiligenciado";
	/**
	 * Constante que indica el nombre de la senial informacionCorregida a enviar
	 */
	private static final String INFORMACION_CORREGIDA = "informacionCorregida";

	/**
	 * Constante que indica el nombre de la senial informacionCorregida a enviar
	 */
	private static final String RADICADA = "radicada";
	/**
	 * 
	 */
	private static final String CORREGIDO = "corregidos";
	/**
	 * Se injecta la clase ValidacionPersonasWebCompositeBusiness
	 */
	
	private Integer contador = 1;
	
	@PersistenceContext(unitName = "afiliaciones_web_PU")
	private EntityManager entityManager;

	@Inject
	ValidacionPersonasWebCompositeService validacionPersonasWeb;
	
	@Resource
    private ManagedExecutorService managedExecutorService;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#afiliarTrabajador(java.lang.Long,
	 * com.asopagos.afiliaciones.personas.web.composite.dto.
	 * AfiliarTrabajadorCandidatoDTO)
	 */
	@Override
	public AfiliadoInDTO afiliarTrabajador(Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO, UserDTO userDTO) {
		// Se verifica el idEmpleador con AfiliadoInDTO - idEmpleador
		if (!idEmpleador.equals(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdEmpleador())) {
			return null;
		} else {
			/* Se realiza la creacion del afiliado */
			if (afiliarTrabajadorCandidatoDTO.getCausaIntentoFallido() != null) {
				ConsultarSolicitudAfiliacionPersona consulSoli = new ConsultarSolicitudAfiliacionPersona(
						afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal());
				consulSoli.execute();
				SolicitudAfiliacionPersonaDTO solicitud = consulSoli.getResult();

				if (solicitud != null) {
					AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
							solicitud.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);

					AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
							solicitud.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

					// se registra intento de afiliacion
					registrarIntentoAfiliacion(solicitud.getIdSolicitudGlobal(),
							CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA, solicitud.getTipoTransaccion());

				} else {
					return null;
				}
			}
			Long idSolicitud = crearRegistroTemporalSolicitudTrabajadorCandidato(afiliarTrabajadorCandidatoDTO);
			AfiliadoInDTO afiliadoInDTO = afiliarTrabajadorComun(afiliarTrabajadorCandidatoDTO, true, false, userDTO);
			afiliadoInDTO.setIdSolicitudGlobal(idSolicitud);
			return afiliadoInDTO;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#
	 * crearRegistroTemporalSolicitudTrabajadorCandidato(com.asopagos.
	 * afiliaciones.personas.web.composite.dto.AfiliarTrabajadorCandidatoDTO)
	 */
	@Override
	public Long crearRegistroTemporalSolicitudTrabajadorCandidato(
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO) {
		return AfiliacionPersonasWebCompositeBusinessComun
				.crearRegistroTemporalSolicitudTrabajadorCandidato(afiliarTrabajadorCandidatoDTO);
	}

	@Override
	public void finalizarAfiliacionTrabajadorCandidato(Long idEmpleador,
			List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO, UserDTO userDTO) {
		logger.debug(
				"Inicio finalizarAfiliacionTrabajadorCandidato(Long, List<AfiliarTrabajadorCandidatoDTO>, UserDTO)");
		Map<String, String> paramsComunicado;
		String razonSocial = "";
		String direccionEmpleador = "";
		String telefonoEmpleador = "";
		String nombreYApellidosRepresentanteLegal = "";
		String ciudadSolicitud = "";
		/*
		 * Se verifica el idEmpleador y la lista de
		 * AfiliarTrabajadorCandidatoDTO no lleguen vacios
		 */
		if (idEmpleador != null && !afiliarTrabajadorCandidatoDTO.isEmpty()) {
			List<AfiliarTrabajadorCandidatoDTO> lstAfiliarTrabajadores = new ArrayList<>();
			boolean envioComunicado = true;
			List<String> destinatariosCorreo;
			Long idInstanciaProceso = null;
			for (AfiliarTrabajadorCandidatoDTO afiliarTrabajador : afiliarTrabajadorCandidatoDTO) {
				// se define el destinatario
				destinatariosCorreo = new ArrayList<>();
				// se valida se se cuenta con el correo electronico para enviar
				// comunicado
				if (afiliarTrabajador.getIdentificadorUbicacionPersona().getUbicacion()
						.getCorreoElectronico() != null) {
					destinatariosCorreo.add(
							afiliarTrabajador.getIdentificadorUbicacionPersona().getUbicacion().getCorreoElectronico());
				} else {
					envioComunicado = false;
				}
				// Se verifica si el trabajador no es afiliable
				if (!afiliarTrabajador.getAfiliable()) {
					IntentoAfiliacionInDTO intentoAfiliacion = new IntentoAfiliacionInDTO();
					intentoAfiliacion
							.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES);
					intentoAfiliacion.setIdSolicitud(afiliarTrabajador.getIdSolicitudGlobal());
					intentoAfiliacion
							.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION);
					intentoAfiliacion.setFechaInicioProceso(new Date());
					AfiliacionPersonasWebCompositeBusinessComun.registrarIntentoAfiliacion(intentoAfiliacion);

					/*
					 * Se genera el comunicado 03
					 * NOTIFICACION_INTENTO_AFILIACION
					 */
					paramsComunicado = new HashMap<>();
					paramsComunicado.put("ciudadSolicitud", ciudadSolicitud);
					paramsComunicado.put("fechaDelSistema", new Date().toString());
					paramsComunicado.put("razonSocial/Nombre", afiliarTrabajador.getRazonSocialNombre());
					paramsComunicado.put("tipoIdentificacionEmpleador",
							afiliarTrabajador.getTipoIdentificacionEmpleador().toString());
					paramsComunicado.put("numeroIdentificacionEmpleador",
							afiliarTrabajador.getNumeroIdentificacionEmpleador());

					if (envioComunicado) {
						// Envio de comunicado NTF_INT_AFL--descomentar cuando
						// este los comunicados
						paramsComunicado.put("correos", afiliarTrabajador.getCorreoEmpleador());
						AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
								EtiquetaPlantillaComunicadoEnum.NTF_INT_AFL_DEP, paramsComunicado,
								TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
								null, afiliarTrabajador.getIdSolicitudGlobal(), idEmpleador);
					}
				} else {
					// ProcesoEnum proceso =
					// ProcesoEnum.AFILIACION_DEPENDIENTE_WEB;
					// String destinatario =
					// asignarAutomaticamenteUsuarioCajaCompensacion(null,
					// proceso,null);
					// UsuarioCCF usuarioCCF =
					// consultarUsuarioCajaCompensacion(destinatario);
					// String sedeDestinatario = usuarioCCF.getCodigoSede();
					// SolicitudDTO solicitudDTO = new SolicitudDTO();
					// solicitudDTO.setDestinatario(destinatario);
					// solicitudDTO.setSedeDestinatario(sedeDestinatario);
					// solicitudDTO.setObservacion(null);
					// Map<String, Object> params = new HashMap<>();
					// params.put("usuarioBack", destinatario);
					// String numeroSolicitudGlobal =
					// String.valueOf(afiliarTrabajador.getIdSolicitudGlobal());
					// params.put("numeroSolicitud", numeroSolicitudGlobal);
					// params.put("idSolicitud", numeroSolicitudGlobal);
					// String numeroRadicacion =
					// consultarNumeroRadicado(afiliarTrabajador.getIdSolicitudGlobal());
					// params.put("numeroRadicado", numeroRadicacion);
					// idInstanciaProceso = iniciarProceso(proceso, params);

					// se envia la señal al bpm
					// EnviarSenal enviarSenal = new EnviarSenal(proceso,
					// RADICADA,
					// afiliarTrabajador.getAfiliadoInDTO().getIdInstanciaProceso(),
					// destinatario);
					// enviarSenal.execute();
					//
					//
					//
					// //solicitudDTO.setIdInstanciaProceso(String.valueOf(idInstanciaProceso));
					// actualizarSolicitudAfiliacionPersona(afiliarTrabajador.getIdSolicitudGlobal(),
					// solicitudDTO);
					// modificarEstadoSolicitud(afiliarTrabajador.getIdSolicitudGlobal(),
					// EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK);
					// lstAfiliarTrabajadores.add(afiliarTrabajador);
					// envío de comunicado
					paramsComunicado = new HashMap<String, String>();
					if (envioComunicado) {
						// Envio de comunicado NTF_INVL_AFL_TRBW
						AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
								EtiquetaPlantillaComunicadoEnum.NTF_INVL_AFL_TRBW, paramsComunicado,
								TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, null,
								afiliarTrabajador.getIdSolicitudGlobal(), idEmpleador);
					}
				}
			}
			if (afiliarTrabajadorCandidatoDTO.get(0).getRazonSocialNombre() != null) {
				razonSocial = afiliarTrabajadorCandidatoDTO.get(0).getRazonSocialNombre();
			}
			if (afiliarTrabajadorCandidatoDTO.get(0).getDireccionEmpleador() != null) {
				direccionEmpleador = afiliarTrabajadorCandidatoDTO.get(0).getDireccionEmpleador();
			}
			if (afiliarTrabajadorCandidatoDTO.get(0).getTelefonoEmpleador() != null) {
				telefonoEmpleador = afiliarTrabajadorCandidatoDTO.get(0).getTelefonoEmpleador();
			}
			if (afiliarTrabajadorCandidatoDTO.get(0).getNombreYApellidosRepresentanteLegal() != null) {
				nombreYApellidosRepresentanteLegal = afiliarTrabajadorCandidatoDTO.get(0)
						.getNombreYApellidosRepresentanteLegal();
			}

			paramsComunicado = new HashMap<String, String>();
			paramsComunicado.put("ciudadSolicitud", ciudadSolicitud);
			paramsComunicado.put("fechaDelSistema", new Date().toString());
			paramsComunicado.put("nombreYApellidosRepresentanteLegal", nombreYApellidosRepresentanteLegal);
			paramsComunicado.put("razonSocial/Nombre", razonSocial);
			paramsComunicado.put("direccion", direccionEmpleador);
			paramsComunicado.put("telefono", telefonoEmpleador);
			if (afiliarTrabajadorCandidatoDTO.get(0).getCorreoEmpleador() != null
					&& afiliarTrabajadorCandidatoDTO.get(0).getIdSolicitudGlobal() != null) {
				paramsComunicado.put("correos", afiliarTrabajadorCandidatoDTO.get(0).getCorreoEmpleador());
				// Envio de comunicado NTF_EMP_AFL_MLT_TRBW
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.NTF_EMP_AFL_MLT_TRBW, paramsComunicado,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						afiliarTrabajadorCandidatoDTO.get(0).getIdSolicitudGlobal(), idEmpleador);
			}
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoCargueMultiple(
					afiliarTrabajadorCandidatoDTO.get(0).getCodigoCargueMultiple(), EstadoCargaMultipleEnum.CERRADO);
		} else {
			return;
		}
		logger.debug(
				"Finaliza finalizarAfiliacionTrabajadorCandidato(Long, List<AfiliarTrabajadorCandidatoDTO>, UserDTO)");
	}

	private String consultarNumeroRadicado(Long idSolicitudGlobal) {
		ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
		consultarSolicitudGlobal.execute();
		SolicitudModeloDTO solicitudGlobal = consultarSolicitudGlobal.getResult();
		return solicitudGlobal.getNumeroRadicacion();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#validarEstructuraContenidoArchivoMultiple(java.lang.Long,
	 *      com.asopagos.dto.CargueMultipleDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public ResultadoValidacionArchivoDTO validarEstructuraContenidoArchivoMultiple(Long idEmpleador,
			CargueMultipleDTO cargue, UserDTO userDTO) {
		logger.debug("Inicio validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO)");
		ResultadoValidacionArchivoDTO resultDTO = null;
		if (cargue.getCodigoIdentificacionECM() == null) {
			logger.debug(
					"Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO): no se especifica id del archivo ECM");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
					"El empleador no existe");
		}
		if (!idEmpleador.equals(cargue.getIdEmpleador())) {
			logger.debug(
					"Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO): empleador no existe");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
					"El empleador no existe");
		}
		Long numeroRegistro = 0L;
		try {
			numeroRegistro = new Long(CacheManager
					.getParametro(ParametrosSistemaConstants.NUMERO_REGISTROS_LECTURA_AFILIACION_MULTIPLE).toString());
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
		}

		InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
		Long totalRegistro = 1L;
		try {
			byte[] dataArchivoOrden = ExcelUtils.ajustarOrdenHojaExcelPorNombre(archivo.getDataFile(), "Plantilla");
			//byte[] dataArchivoCorregido = ExcelUtils.limpiarFilasExcel(dataArchivoOrden);
			archivo.setDataFile(dataArchivoOrden);
			totalRegistro = ExcelUtils.leerNumeroFilas(archivo.getDataFile(), numeroRegistro + 1L);
		} catch (IOException e1) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		if (totalRegistro == 1L) {
			resultDTO = crearHallazgo(ArchivoMultipleCampoConstants.MINIMO_DE_REGISTROS_PERMITIDOS_MSG,
					archivo.getFileName(), 0L, "");
			return resultDTO;
		}
		logger.info("totalRegistro: " + totalRegistro + " numeroRegistro : " + numeroRegistro);
		if (totalRegistro > 1L && totalRegistro <= numeroRegistro + 1L) {
			cargue.setIdEmpleador(idEmpleador);
			cargue.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
			cargue.setEstado(EstadoCargaMultipleEnum.CARGADO);
			cargue.setFechaCarga(Calendar.getInstance().getTime());
			cargue.setProceso(ProcesoEnum.AFILIACION_DEPENDIENTE_WEB);
			cargue.setTipoSolicitante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
			cargue.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
			Long idCargueMultiple = registrarCargueMultiple(idEmpleador, cargue);
			resultDTO = validarEstructuraContenidoArchivo(idEmpleador, idCargueMultiple, archivo);
			resultDTO.setIdCargue(idCargueMultiple);
			ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
			String codigoCaja;
			if (resultDTO.getAfiliarTrabajadorCandidatoDTO() != null && !resultDTO.getAfiliarTrabajadorCandidatoDTO().isEmpty()) {
				for(int i = 0; i < resultDTO.getAfiliarTrabajadorCandidatoDTO().size(); i++ ) {
					resultDTO.getAfiliarTrabajadorCandidatoDTO().get(i).getInformacionLaboralTrabajador().setIdSucursalEmpleador(cargue.getIdSucursalEmpleador());
				}
			}
			try {
				codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
			} catch (Exception e) {
				codigoCaja = null;
			}
			conCargueMasivo.setCcf(codigoCaja);
			conCargueMasivo.setCargue_id(idCargueMultiple);
			EstadoCargueMasivoEnum estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
			if (resultDTO.getEstadoCargue().equals(EstadoCargaMultipleEnum.CANCELADO)) {
				estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
			}
			conCargueMasivo.setEstado(estadoProcesoMasivo);
			conCargueMasivo.setFileLoaded_id(resultDTO.getFileDefinitionId());
			conCargueMasivo.setGradoAvance(resultDTO.getEstadoCargue().getGradoAvance());
			conCargueMasivo.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
			if(archivo.getFileName() != null) {
				conCargueMasivo.setNombreArchivo(archivo.getFileName());
			}
			
			conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
			conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
			conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
			conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
			conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_AFILIACION_MULTIPLE_122);
			conCargueMasivo.setUsuario(userDTO.getNombreUsuario());
			registrarConsolaEstado(conCargueMasivo);
			// Actualizo el estado del cargue multiple en la base de datos
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoCargueMultiple(idCargueMultiple,
					resultDTO.getEstadoCargue());
		} else {
			resultDTO = crearHallazgo(ArchivoMultipleCampoConstants.LIMITE_DE_REGISTROS_PERMITIDOS_MSG + numeroRegistro,
					archivo.getFileName(), numeroRegistro, "");
			return resultDTO;
		}
		logger.debug("Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO)");
		return resultDTO;
	}

	/**
	 * Método encargado de crear un hallazgo
	 * 
	 * @param mensaje,
	 *            mensaje que debe de ir en el hallazgo
	 * @param nombreArchivo,
	 *            nombre del archivo
	 * @param numeroLinea,
	 *            número de linea
	 * @param nombreCampo,
	 *            nombre del campo
	 * 
	 * @return retorna el hallazgo creado
	 */
	private ResultadoValidacionArchivoDTO crearHallazgo(String mensaje, String nombreArchivo, Long numeroLinea,
			String nombreCampo) {
		ResultadoValidacionArchivoDTO resultDTO = new ResultadoValidacionArchivoDTO();
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(numeroLinea);
		hallazgo.setNombreCampo(nombreCampo);
		hallazgo.setError(mensaje);
		listaHallazgos.add(hallazgo);
		resultDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
		resultDTO.setNombreArchivo(nombreArchivo);
		resultDTO.setRegistrosConErrores(0L);
		resultDTO.setTotalRegistro(0L);
		return resultDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#
	 * iniciarVerificarInformacionSolicitud(java.lang.Long,
	 * com.asopagos.afiliaciones.personas.web.composite.dto.
	 * AfiliarTrabajadorCandidatoDTO)
	 */
	@Override
	public Long iniciarVerificarInformacionSolicitud(Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO, Long idInstanciaProceso, UserDTO userDTO) {
		logger.debug("Inicio iniciarVerificarInformacionSolicitud");
		if (!idEmpleador.equals(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdEmpleador())) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
		} else {
			/*
			 * Se envia la solicitud funcionario del back asignado previamente
			 * para este tipo de gestión (por medio de la ejecución de la
			 * “HU-TRA-084 Administrar asignación de solicitudes”)
			 */
			ProcesoEnum proceso = ProcesoEnum.AFILIACION_DEPENDIENTE_WEB;
			// null porque procesos web no hay caja y es externo
			String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(null, proceso, null);
			UsuarioCCF usuarioCCF = consultarUsuarioCajaCompensacion(destinatario);
			String sedeDestinatario = usuarioCCF.getCodigoSede();
			SolicitudDTO solicitudDTO = new SolicitudDTO();
			solicitudDTO.setDestinatario(destinatario);
			solicitudDTO.setSedeDestinatario(sedeDestinatario);
			solicitudDTO.setObservacion(null);

			// se envia la señal al bpm
			EnviarSenal enviarSenal = new EnviarSenal(proceso, RADICADA, idInstanciaProceso, destinatario);
			enviarSenal.execute();
			/*
			 * El sistema debe cambiar el “Estado de la solicitud” a “Asignada
			 * al back
			 */
			modificarEstadoSolicitud(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK);
			actualizarSolicitudAfiliacionPersona(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), solicitudDTO);
			actualizarEstadoRolAfiliado(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdRolAfiliado(),
					EstadoAfiliadoEnum.ACTIVO);
			
			ConsultarUbicacionPersona ubicacionPersonaService = new ConsultarUbicacionPersona(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getIdPersona());
			ubicacionPersonaService.execute();
			UbicacionDTO ubicacionPersona = ubicacionPersonaService.getResult();
			//if para evitar nullpointer
            if (ubicacionPersona!=null ){
				if(ubicacionPersona.getCorreoElectronico()!=null){
					if(ubicacionPersona.getAutorizacionEnvioEmail()!=null){
						if(ubicacionPersona.getAutorizacionEnvioEmail()){
                Map<String, String> paramsComunicado = new HashMap<String, String>();
                paramsComunicado.put("correos", ubicacionPersona.getCorreoElectronico());
                // Envio de comunicado NTF_INVL_AFL_TRBW
                AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum.NTF_INVL_AFL_TRBW,
                        paramsComunicado, TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, idInstanciaProceso.toString(),
                        afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getIdPersona());
						}
					}
				}
            }
            
            List<Long> idsEmpleadores = new ArrayList<>();
            idsEmpleadores.add(idEmpleador);
            ConsultarEmailEmpleadores emailEmpleadorService = new ConsultarEmailEmpleadores(idsEmpleadores);
            emailEmpleadorService.execute();
            if (emailEmpleadorService.getResult()!=null && !emailEmpleadorService.getResult().isEmpty()){
                Map<String, String>paramsComunicado = new HashMap<String, String>();
                paramsComunicado.put("correos", emailEmpleadorService.getResult().get(0));
                // Envio de comunicado NTF_INVL_AFL_TRBW_EMP
                AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum.NTF_INVL_AFL_TRBW_EMP,
                        paramsComunicado, TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, idInstanciaProceso.toString(),
                        afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), idEmpleador);    
            }
			logger.debug("Finaliza iniciarVerificarInformacionSolicitud");
			return idInstanciaProceso;
		}
	}

	private void registrarIntentoAfiliacion(Long idSolicitud, CausaIntentoFallidoAfiliacionEnum causa,
			TipoTransaccionEnum tipoTransaccion, String... tokens) {
		IntentoAfiliacionInDTO intentoInput = new IntentoAfiliacionInDTO();
		intentoInput.setCausaIntentoFallido(causa);
		intentoInput.setTipoTransaccion(tipoTransaccion);
		intentoInput.setIdSolicitud(idSolicitud);
		RegistrarIntentoAfliliacion r = new RegistrarIntentoAfliliacion(intentoInput);
		if (tokens != null && tokens.length > 0) {
			r.setToken(tokens[0]);
		}
		r.execute();
	}

	/**
	 * Método encargado de validar la existencia de una persona con tipo y
	 * numero de identificacion
	 * 
	 * @param inDTO,
	 *            datos de ingreso
	 * @param tipoAfiliado,
	 *            tipo de afiliado
	 * @return true en caso de existir, false otro caso
	 */
	private boolean validarExistenciaPersonaTipoNumeroIdentificacion(DigitarInformacionContactoDTO inDTO,
			TipoAfiliadoEnum tipoAfiliado) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null,null, null, null,
				inDTO.getInformacionContacto().getNumeroIdentificacion(),
				inDTO.getInformacionContacto().getTipoIdentificacion(), null);
		buscarPersonas.execute();
		List<PersonaDTO> personas = buscarPersonas.getResult();
		return personas != null && !personas.isEmpty();
	}

	private boolean validarExistenciaPersonaNumeroIdentificacion(DigitarInformacionContactoDTO inDTO,
			TipoAfiliadoEnum tipoAfiliado) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null, null,null,
				inDTO.getInformacionContacto().getNumeroIdentificacion(), null, null);
		buscarPersonas.execute();
		List<PersonaDTO> personas = buscarPersonas.getResult();
		return personas != null && !personas.isEmpty();
	}

	/**
	 * Metodo encargado de llamar al cliente que se encarga de calcular la
	 * categoria
	 * 
	 * @param afiliadoInDTO
	 */
	private void calcularCategoria(AfiliadoInDTO afiliadoInDTO) {
		logger.debug("Inicia calcularCategoria(AfiliadoInDTO)");
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setTipoIdentificacion(afiliadoInDTO.getPersona().getTipoIdentificacion());
		categoriaDTO.setNumeroIdentificacion(afiliadoInDTO.getPersona().getNumeroIdentificacion());
		categoriaDTO.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NUEVA_AFILIACION);
		CalcularCategoriasAfiliado calcularCategoria = new CalcularCategoriasAfiliado(categoriaDTO);
		logger.debug("Finaliza calcularCategoria(AfiliadoInDTO)");
	}

	/**
	 * Método que hace la peticion REST al servicio de actualizar el
	 * Identificador Instancia del Proceso de Solicitud de afiliacion De Persona
	 * *
	 * 
	 * @param idSolicitudGlobal
	 *            <code>Long</code> El identificador de la solicitud global de
	 *            afiliacion de persona
	 * 
	 * @param idInstanciaProceso
	 *            <code>Long</code> El identificador de la Instancia Proceso
	 *            Afiliacion de la Persona
	 */
	private void actualizarIdInstanciaProcesoSolicitudDePersona(Long idSolicitudGlobal, Long idInstanciaProceso,
			String token) {
		logger.debug("Inicia actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
		GuardarInstanciaProceso guardarInstanciaProcesoService = new GuardarInstanciaProceso(idSolicitudGlobal,
				String.valueOf(idInstanciaProceso));
		guardarInstanciaProcesoService.setToken(token);
		guardarInstanciaProcesoService.execute();
		logger.debug("Finaliza actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
	}

	private ValidacionDTO getValidacion(ValidacionCoreEnum validacion, List<ValidacionDTO> lista) {
		for (ValidacionDTO validacionAfiliacionDTO : lista) {
			if (validacionAfiliacionDTO.getValidacion().equals(validacion)) {
				return validacionAfiliacionDTO;
			}
		}
		return null;
	}

	/**
	 * Servicio encargado de actualizar la solicitud afiliacion Persona
	 * 
	 * @param idSolicitudGlobal,
	 *            id de la solicitud a actualizar
	 * @param solicitudDTO,
	 *            informacion a actualizar de la solicitud
	 */
	private void actualizarSolicitudAfiliacionPersona(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
		logger.debug("Inicia actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO)");
		ActualizarSolicitudAfiliacionPersona actualizarSolicitudAfiliacionPersonaService = new ActualizarSolicitudAfiliacionPersona(
				idSolicitudGlobal, solicitudDTO);
		actualizarSolicitudAfiliacionPersonaService.execute();
		logger.debug("Finaliza actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO)");
	}

	/**
	 * Ejecutar la “HU-132 Emitir tarjeta”.
	 */
	private void emitirTarjeta() {
		logger.debug("Inicia emitirTarjeta()");
		/*
		 * TODO Ejecutar la “HU-132 Emitir tarjeta”.
		 */
		logger.debug("Finaliza emitirTarjeta()");
	}

	/**
	 * Metodo encargado de generar el numero de radicado de una solicitud,
	 * mediante el llamado del cliente RadicarSolicitud
	 * 
	 * @param idSolicitud,
	 *            id de la solicitud a la cual se generara el numero de radicado
	 * @param sedeCajaCompensacion,
	 *            sede a la cual pertenece la solicitud
	 */
	private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion, String... tokens) {
		logger.debug("Inicia generarNumeroRadicado( Long, String)");
		logger.info("PASO 2 L_REVISION ASIGANAR A BACK: ENTRO A generarNumeroRadicado " + idSolicitud);
                String numeroRadicado = "";
		RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
		if (tokens != null && tokens.length > 0) {
			radicarSolicitudService.setToken(tokens[0]);
		}
		radicarSolicitudService.execute();
		numeroRadicado = radicarSolicitudService.getResult();
                logger.info("L_REVISION ASIGANAR A BACK: FINALIZA A generarNumeroRadicado: numeroRadicado " + numeroRadicado);
		logger.debug("Finaliza generarNumeroRadicado( Long, String)");
		return numeroRadicado;
	}

	/**
	 * Método encargado de validar la existencia de una persona con tipo y
	 * numero de identificacion
	 * 
	 * @param inDTO,
	 *            datos de ingreso
	 * @param tipoAfiliado,
	 *            tipo de afiliado
	 * @return true en caso de existir, false otro caso
	 */
	private boolean validarExistenciaPersonaTipoNumeroIdentificacion(AfiliadoInDTO inDTO, TipoAfiliadoEnum tipoAfiliado,
			String token) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null, null,null,
				inDTO.getPersona().getNumeroIdentificacion(), inDTO.getPersona().getTipoIdentificacion(), null);

		buscarPersonas.setToken(token);
		buscarPersonas.execute();
		List<PersonaDTO> personas = buscarPersonas.getResult();
		return personas != null && !personas.isEmpty();
	}

	private boolean validarExistenciaPersonaNumeroIdentificacion(AfiliadoInDTO inDTO, TipoAfiliadoEnum tipoAfiliado,
			String token) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null, null,null,
				inDTO.getPersona().getNumeroIdentificacion(), null, null);
		buscarPersonas.setToken(token);
		buscarPersonas.execute();
		List<PersonaDTO> personas = buscarPersonas.getResult();
		return personas != null && !personas.isEmpty();
	}

	private boolean validarExistenciaNombreApellidFechaNacimiento(AfiliadoInDTO inDTO, TipoAfiliadoEnum tipoAfiliado,
			String token) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(inDTO.getPersona().getFechaNacimiento(),
				inDTO.getPersona().getPrimerApellido(), null,null, inDTO.getPersona().getPrimerNombre(), null, null, null,
				null);
		buscarPersonas.setToken(token);
		buscarPersonas.execute();
		List<PersonaDTO> personas = buscarPersonas.getResult();
		return personas != null && !personas.isEmpty();
	}

	/**
	 * Metodo encargado de modificar el estado de la solicitud
	 * 
	 * @param idSolicitud,
	 *            id de la solicitud
	 * @param estado,
	 *            nuevo estado de la solicitud
	 */
	private void modificarEstadoSolicitud(Long idSolicitud, EstadoSolicitudAfiliacionPersonaEnum estado) {
                logger.info("PASO 7: L_REVISION ASIGANAR A BACK: asignarBack " + estado.name());
                ActualizarEstadoSolicitudAfiliacionPersona actualizarSolicitud = new ActualizarEstadoSolicitudAfiliacionPersona(
				idSolicitud, estado);
		actualizarSolicitud.execute();
		logger.debug("Finaliza modificarEstadoSolicitud(Long,EstadoSolicitudAfiliacionPersonaEnum)");
	}

	/**
	 * Reconocer aportes y novedades del afiliado como se especifica en la
	 * “HU-119 Reconocer aportes y novedades de afiliado”.
	 */
	private void reconocerAportesYNovedadesAfiliado() {
		logger.debug("Inicia reconocerAportesYNovedadesAfiliado");
		/*
		 * TODO Reconocer aportes y novedades del afiliado como se especifica en
		 * la “HU-119 Reconocer aportes y novedades de afiliado”.
		 */
		logger.debug("Finaliza reconocerAportesYNovedadesAfiliado");
	}

	/**
	 * Método que hace la peticion REST al servicio que permite buscar una
	 * Solicitud
	 * 
	 * @param numeroRadicado
	 *            <code>String</code> El numero de radicado de la solicitud
	 * 
	 * @return List <code>SolicitudDTO</code> Lista de DTO's que transporta los
	 *         datos de una solcitud
	 */
	private List<SolicitudDTO> buscarSolicitud(String numeroRadicado) {
		logger.debug("Inicia buscarSolicitud( numeroRadicado )");
		BuscarSolicitud buscarSolicitudService = new BuscarSolicitud(numeroRadicado);
		buscarSolicitudService.execute();
		List<SolicitudDTO> lstSolicitudDTO = buscarSolicitudService.getResult();
		logger.debug("Finaliza buscarSolicitud( numeroRadicado )");
		return lstSolicitudDTO;
	}

	/**
	 * Método que hace la peticion REST al servicio de actualizar un Afiliado
	 * indicado en <code>AfiliadoInDTO</code>
	 * 
	 * @param afiliadoInDTO
	 *            <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
	 *            de un Afiliado
	 */
	private void actualizarAfiliado(AfiliadoInDTO afiliadoInDTO) {
		logger.debug("Inicia actualizarAfiliado( PersonaDTO )");
		ActualizarAfiliado actualizarAfiliadoService = new ActualizarAfiliado(
				afiliadoInDTO.getPersona().getIdAfiliado(), afiliadoInDTO);
		actualizarAfiliadoService.execute();
		logger.debug("Finaliza actualizarAfiliado( PersonaDTO )");
	}

	/**
	 * Método que hace la peticion REST al servicio de obtener la lista de
	 * beneficiarios asociados a un afiliado
	 * 
	 * @param idAfiliado
	 *            <code>Long</code> El identificador del afiliado
	 * 
	 * @return List<BeneficiarioDTO> La lista de DTO's que transporta los datos
	 *         básicos de beneficiarios.
	 */
	private List<BeneficiarioDTO> consultarBeneficiariosDeAfiliado(Long idAfiliado, String... tokens) {
		logger.debug("Inicia consultarBeneficiariosDeAfiliado( idAfiliado )");
		List<BeneficiarioDTO> lstBeneficiariosDTO;
		ConsultarBeneficiarios consultarBeneficiariosService = new ConsultarBeneficiarios(idAfiliado, false);
		if (tokens != null && tokens.length > 0) {
			consultarBeneficiariosService.setToken(tokens[0]);
		}
		consultarBeneficiariosService.execute();
		lstBeneficiariosDTO = consultarBeneficiariosService.getResult();
		if (lstBeneficiariosDTO == null) {
			lstBeneficiariosDTO = new ArrayList<>();
		}
		logger.debug("Finaliza consultarBeneficiariosDeAfiliado( idAfiliado )");
		return lstBeneficiariosDTO;
	}

	/**
	 * Método que hace la peticion REST al servicio de actualizar un
	 * beneficiario
	 * 
	 * @param idAfiliado
	 *            <code>Long</code> El identificador del afiliado
	 * @param beneficiarioDTO
	 *            <code>BeneficiarioDTO</code> DTO que transporta los datos
	 *            básicos de un beneficiario
	 * 
	 * @return <code>Long</code> El identificador del Beneficiario
	 */
	private Long actualizarBeneficiario(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
		logger.debug("Inicia actualizarBeneficiario( idAfiliado ,BeneficiarioDTO )");
		RegistrarBeneficiario registrarBeneficiarioService = new RegistrarBeneficiario(idAfiliado, beneficiarioDTO);
		registrarBeneficiarioService.execute();
		Long idBeneficiario = Long.valueOf(registrarBeneficiarioService.getResult());
		logger.debug("Finaliza actualizarBeneficiario( idAfiliado ,BeneficiarioDTO ) ");
		return idBeneficiario;
	}

	private void actualizarEstadoBeneficiario(Long idBeneficario, EstadoAfiliadoEnum estado, String... tokens) {
		logger.debug("Inicia actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
		ActualizarEstadoBeneficiario actualizarEstadoBeneficario = new ActualizarEstadoBeneficiario(idBeneficario, null,
				estado);
		if (tokens != null && tokens.length > 0) {
			actualizarEstadoBeneficario.setToken(tokens[0]);
		}
		actualizarEstadoBeneficario.execute();
		logger.debug("Finaliza actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#verificarResultadoProductoNoConforme(com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO)
	 */
	@Override
	public void verificarResultadoProductoNoConforme(Long idEmpleador, VerificarSolicitudAfiliacionPersonaDTO inDTO) {
		logger.debug("Inicia verificarResultadoProductoNoConforme( Long ,VerificarSolicitudAfiliacionPersonaDTO)");
		if (inDTO.getNumeroRadicado() == null || inDTO.getNumeroIdentificacionAfiliado() == null) {
			logger.debug(
					"Finaliza verificarResultadoProductoNoConforme( Long ,VerificarSolicitudAfiliacionPersonaDTO)");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}

		List<SolicitudDTO> listaSolicitudes = buscarSolicitud(inDTO.getNumeroRadicado());
		SolicitudDTO solicitudDTO = listaSolicitudes.iterator().next();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(solicitudDTO.getIdSolicitud());

		if (idEmpleador.equals(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdEmpleador())) {
			Integer estadoSolicitud = 1;
			boolean inactivaBeneficiarios = false;
			EstadoAfiliadoEnum estadoAfiliado = EstadoAfiliadoEnum.ACTIVO;
			EstadoSolicitudAfiliacionPersonaEnum estadoSolicitudEnum = EstadoSolicitudAfiliacionPersonaEnum.CERRADA;

			if (inDTO.getResultadoGeneralAfiliado() == null && inDTO.getResultadoGeneralBeneficiarios() == null) {
				AfiliacionPersonasWebCompositeBusinessComun
						.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(), estadoSolicitudEnum);
			} else {
				if (ResultadoGeneralProductoNoConformeEnum.APROBADA != inDTO.getResultadoGeneralAfiliado()) {
				    if (inDTO.getEmpleadorInactivo() == null || Boolean.FALSE.equals(inDTO.getEmpleadorInactivo())) {
				        estadoSolicitud = 2;
                    }
					inactivaBeneficiarios = true;
					estadoAfiliado = EstadoAfiliadoEnum.INACTIVO;
					estadoSolicitudEnum = EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA;				
					
					// se actualiza estado de beneficiarios
					actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(),
							estadoAfiliado);
					
					//Se deben inactivar solo los beneficiarios de esa solicitud
					List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado());
			        
					if(beneficiarios.size()>0) {
						List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios = new ArrayList<ResultadoGeneralProductoNoConformeBeneficiarioDTO>();
						for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
							if(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado().equals(beneficiarioDTO.getIdRolAfiliado()) 
									|| beneficiarioDTO.getIdRolAfiliado() == null) {
								if(beneficiarioDTO.getEstadoBeneficiarioAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
									ResultadoGeneralProductoNoConformeBeneficiarioDTO beneficiarioInactivar = new ResultadoGeneralProductoNoConformeBeneficiarioDTO();
									beneficiarioInactivar.setIdBeneficiario(beneficiarioDTO.getIdBeneficiario());
									resultadoGeneralBeneficiarios.add(beneficiarioInactivar);
								}
								
							}
				        }
						if(resultadoGeneralBeneficiarios.size()>0) {
							inDTO.setResultadoGeneralBeneficiarios(resultadoGeneralBeneficiarios);
						}
					}

					if(inDTO.getResultadoGeneralBeneficiarios()!= null &&
							  inDTO.getResultadoGeneralBeneficiarios().size()>0) {
						  actualizarEstadoBeneficiarios(inDTO.getResultadoGeneralBeneficiarios(), true); 
					}
					
					
					RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
					rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
					AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
					afiliadoModeloDTO.setIdAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado());
					rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
					rolAfiliadoModeloDTO.setEstadoAfiliado(estadoAfiliado);
					rolAfiliadoModeloDTO
							.setIdRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado());
					rolAfiliadoModeloDTO
							.setTipoAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getTipoAfiliado());
					rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
					ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
					AfiliacionPersonasWebCompositeBusinessComun
							.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(), estadoSolicitudEnum);
				} else {
					if (inDTO.getResultadoGeneralBeneficiarios() != null) {
						actualizarEstadoBeneficiarios(inDTO.getResultadoGeneralBeneficiarios(), inactivaBeneficiarios);
					}
					estadoSolicitudEnum = EstadoSolicitudAfiliacionPersonaEnum.APROBADA;
					AfiliacionPersonasWebCompositeBusinessComun
							.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(), estadoSolicitudEnum);
				}

				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						solicitudDTO.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			}

			Map<String, Object> params = new HashMap<>();
			params.put("estadoSolicitud", estadoSolicitud);

			if (inDTO.getIdTarea() == null) {
				Long idTarea = consultarTareaAfiliacionPersonas(solicitudDTO.getIdInstanciaProceso());
				inDTO.setIdTarea(idTarea);
			}
			TerminarTarea terminarTareaService = new TerminarTarea(inDTO.getIdTarea(), params);
			terminarTareaService.execute();
			logger.debug(
					"Finaliza verificarResultadoProductoNoConforme( Long ,VerificarSolicitudAfiliacionPersonaDTO)");
		}
	}

	/**
	 * Método encargado de actualizar el estado de todos los beneficiaros,
	 * enviados en la lista de resultados producto no conforme
	 * 
	 * @param resultadoGeneralBeneficiarios,
	 *            listo de resultados de producto no conforme
	 * @param inactivaTodos,
	 *            true en caso de inactivar todos los beneficiarios, false caso
	 *            contrario
	 */
	private void actualizarEstadoBeneficiarios(
			List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios,
			boolean inactivaTodos) {
		logger.debug("Inicia actualizarEstadoBeneficiarios(List<ResultadoGeneralProductoNoConformeBeneficiarioDTO>)");
		if (resultadoGeneralBeneficiarios != null && !resultadoGeneralBeneficiarios.isEmpty()) {
			for (ResultadoGeneralProductoNoConformeBeneficiarioDTO dto : resultadoGeneralBeneficiarios) {
				if (inactivaTodos || ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE
						.equals(dto.getResultadoGeneralBeneficiario())) {
					actualizarEstadoBeneficiario(dto.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO);
				} else {
					actualizarEstadoBeneficiario(dto.getIdBeneficiario(), EstadoAfiliadoEnum.ACTIVO);
				}
			}
		}
		logger.debug("Finaliza actualizarEstadoBeneficiarios(List<ResultadoGeneralProductoNoConformeBeneficiarioDTO>)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#gestionarResultadoProductoNoConforme(java.lang.Long,
	 *      com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void gestionarResultadoProductoNoConforme(Long idEmpleador, GestionarProductoNoConformeSubsanableDTO inDTO,
			UserDTO userDTO) {
		logger.debug(
				"Inicia gestionarResultadoProductoNoConforme( Long ,GestionarProductoNoConformeSubsanableDTO, UserDTO )");
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
		if (idEmpleador.equals(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdEmpleador())) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
			if (inDTO.getProductosNoConformeDTO() != null && !inDTO.getProductosNoConformeDTO().isEmpty()) {
				CrearProductosNoConforme crearProductosNoConforme = new CrearProductosNoConforme(
						solicitudAfiliacionPersonaDTO.getIdSolicitudAfiliacionPersona(),
						inDTO.getProductosNoConformeDTO());
				crearProductosNoConforme.execute();
			}
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_GESTIONADA);

			if (inDTO.getObservaciones() != null) {
				SolicitudDTO solicitudDTO = new SolicitudDTO();
				solicitudDTO.setObservacion(inDTO.getObservaciones());
				actualizarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal(), solicitudDTO);
			}

			// Se termina la tarea
			if (inDTO.getCorrigeBack()) {
				Long idTarea = consultarTareaAfiliacionPersonas(solicitudAfiliacionPersonaDTO.getIdInstanciaProceso());
				Map<String, Object> maps = new HashMap<>();
				TerminarTarea service2 = new TerminarTarea(idTarea, maps);
				service2.execute();
			} else {
				String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
				EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_DEPENDIENTE_WEB, CORREGIDO,
						Long.parseLong(solicitudAfiliacionPersonaDTO.getIdInstanciaProceso()), "");
				enviarSenal.setToken(token);
				enviarSenal.execute(); 
			}
		}
		logger.debug(
				"Finaliza gestionarResultadoProductoNoConforme( Long ,GestionarProductoNoConformeSubsanableDTO, UserDTO )");
	}

	/**
	 * 
	 * @param idRolAfiliado
	 * @param fecha
	 */
	public void actualizarFechaAfiliacionRolAfiliado(Long idRolAfiliado, Long fecha) {
		logger.debug("Inicia actualizarFechaAfiliacionRolAfiliado( Long, Long )");
		ActualizarFechaAfiliacionRolAfiliado actualizarFechaAfiliacion = new ActualizarFechaAfiliacionRolAfiliado(
				idRolAfiliado, fecha);
		actualizarFechaAfiliacion.execute();
		logger.debug("Finaliza actualizarFechaAfiliacionRolAfiliado( Long, Long )");
	}

	/**
	 * Método que hace la peticion REST al servicio de consultar un usuario de
	 * caja de compensacion
	 * 
	 * @param nombreUsuarioCaja
	 *            String El nombre de usuario del funcionario de la caja que
	 *            realiza la consulta
	 * 
	 * @return UsuarioDTO DTO para el servicio de autenticación usuario
	 */
	private UsuarioCCF consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
		logger.debug("Inicia consultarUsuarioCajaCompensacion( String )");
		ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
				nombreUsuarioCaja, null, null, false);
		obtenerDatosUsuariosCajaCompensacionService.execute();
		UsuarioCCF usuarioCCF = obtenerDatosUsuariosCajaCompensacionService.getResult();
		logger.debug("Finaliza consultarUsuarioCajaCompensacion( String )");
		return usuarioCCF;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#verificarInformacionSolicitud(java.lang.Long,
	 *      com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO)
	 */
	@Override
	public void verificarInformacionSolicitud(Long idEmpleador, VerificarSolicitudAfiliacionPersonaDTO inDTO,
			UserDTO userDTO) {
		logger.debug("Inicia verificarInformacionSolicitud(Long, VerificarSolicitudAfiliacionPersonaDTO)");
		if (inDTO.getNumeroRadicado() == null || inDTO.getNumeroIdentificacionAfiliado() == null) {
			logger.debug("Final verificarInformacionSolicitud(Long, VerificarSolicitudAfiliacionPersonaDTO)");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}

		List<SolicitudDTO> listSol = buscarSolicitud(inDTO.getNumeroRadicado());
		if (listSol == null || listSol.isEmpty()) {
			logger.debug("Final verificarInformacionSolicitud(Long, VerificarSolicitudAfiliacionPersonaDTO)");
			throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		SolicitudDTO solicitudDTO = listSol.iterator().next();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionDTO = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(solicitudDTO.getIdSolicitud());

		Integer resultadoVerificacion = null;
		PlantillaComunicado plantillaComunicado = null;
		RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
		AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
		switch (inDTO.getResultadoGeneralAfiliado()) {
		case RECHAZADA:
			resultadoVerificacion = 1;
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
			actualizarEstadoRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado(),
					EstadoAfiliadoEnum.INACTIVO);

			rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
			afiliadoModeloDTO.setIdAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdAfiliado());
			rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
			rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
			rolAfiliadoModeloDTO.setIdRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado());
			rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getTipoAfiliado());
			rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
			rolAfiliadoModeloDTO.setIdEmpleador(idEmpleador);
			ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
			
			this.inactivarBeneficiarios(inDTO);

			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			break;
		case NO_SUBSANABLE:
			resultadoVerificacion = 4;
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
			actualizarEstadoRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado(),
					EstadoAfiliadoEnum.INACTIVO);

			rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
			afiliadoModeloDTO.setIdAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdAfiliado());
			rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
			rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
			rolAfiliadoModeloDTO.setIdRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado());
			rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getTipoAfiliado());
			rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
			rolAfiliadoModeloDTO.setIdEmpleador(idEmpleador);
			ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);

			
			//Se deben inactivar solo los beneficiarios de esa solicitud
            this.inactivarBeneficiarios(inDTO);
			
			
			
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

			List<String> destinatarioTO = null;
			if (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona() != null
					&& solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO() != null
					&& solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO()
							.getCorreoElectronico() != null
					&& !solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO().getCorreoElectronico()
							.isEmpty()) {
				destinatarioTO = new ArrayList<>();
				destinatarioTO.add(solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO()
						.getCorreoElectronico());

				// Envio de comunicado RCHZ_AFL_DPT_PROD_NSUBLE_TRB
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.RCHZ_AFL_DPT_PROD_NSUBLE_TRB, new HashMap<>(),
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						solicitudDTO.getIdSolicitud(), idEmpleador);
			}
			ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(idEmpleador);
			consultarEmpleador.execute();
			Empleador empleador = consultarEmpleador.getResult();
			Long idRepresentaLegal = empleador.getEmpresa().getIdPersonaRepresentanteLegal();
			if (idRepresentaLegal != null && empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
				ConsultarUbicacion consultarUbicacion = new ConsultarUbicacion(
						empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
				consultarUbicacion.execute();
				UbicacionDTO ubicacion = consultarUbicacion.getResult();
				if (ubicacion.getCorreoElectronico() != null && !ubicacion.getCorreoElectronico().isEmpty()) {
					destinatarioTO = new ArrayList<>();
					destinatarioTO.add(ubicacion.getCorreoElectronico());

					// Envio de comunicado RCHZ_AFL_DPT_PROD_NSUBLE_EMP
					AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
							EtiquetaPlantillaComunicadoEnum.RCHZ_AFL_DPT_PROD_NSUBLE_EMP, new HashMap<>(),
							TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
							solicitudDTO.getIdSolicitud(), idEmpleador);
				}
			}
			break;
		case SUBSANABLE:
			resultadoVerificacion = 3;
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_SUBSANABLE);
			actualizarEstadoBeneficiarios(inDTO.getResultadoGeneralBeneficiarios(), false);
			break;
		case APROBADA:

			resultadoVerificacion = 2;
			ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
			listaChequeo.setTipoIdentificacion(solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getTipoIdentificacion());
			listaChequeo.setNumeroIdentificacion(solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
			listaChequeo.setIdSolicitudGlobal(solicitudDTO.getIdSolicitud());
			listaChequeo.setFechaRecepcionDocumentos(solicitudAfiliacionDTO.getFechaRadicacion().getTime());
			List<ItemChequeoDTO> itemChequeos = new ArrayList<>();
			ItemChequeoDTO item = new ItemChequeoDTO();
			item.setIdSolicitudGlobal(solicitudDTO.getIdSolicitud());
			item.setIdRequisito(90L);
			item.setNombreRequisito("");
			item.setIdentificadorDocumento("");
			item.setVersionDocumento((short)1);
			item.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
			item.setCumpleRequisito(Boolean.TRUE);
			item.setFormatoEntregaDocumento(FormatoEntregaDocumentoEnum.ELECTRONICO);
			itemChequeos.add(item);
			listaChequeo.setListaChequeo(itemChequeos);

			GuardarListaChequeo guardarListaChequeo = new GuardarListaChequeo(listaChequeo);
			guardarListaChequeo.execute();

			if (contieneBeneficiarioDiferenteAprobado(inDTO.getResultadoGeneralBeneficiarios())) {
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						solicitudDTO.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
			} else {
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						solicitudDTO.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
				actualizarEstadoRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado(),
						EstadoAfiliadoEnum.ACTIVO);
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						solicitudDTO.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			}
			actualizarEstadoBeneficiarios(inDTO.getResultadoGeneralBeneficiarios(), false);
			break;
		default:
			break;
		}

		String tiempoCorrecciones = (String) CacheManager
				.getParametro(ParametrosSistemaConstants._122_CORREGIR_INFORMACION_TIMER);

		Map<String, Object> params = new HashMap<>();
		params.put("resultadoVerificacion", resultadoVerificacion);
		params.put("tiempoCorrecciones", tiempoCorrecciones);
		TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
		service2.execute();

		logger.debug("Final verificarInformacionSolicitud(Long, VerificarSolicitudAfiliacionPersonaDTO)");
	}

    private void inactivarBeneficiarios(VerificarSolicitudAfiliacionPersonaDTO inDTO) {
        List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(inDTO.getIdAfiliado());
        
        if(beneficiarios.size()>0) {
        	List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios = new ArrayList<ResultadoGeneralProductoNoConformeBeneficiarioDTO>();
        	for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
        		if(inDTO.getIdAfiliado().equals(beneficiarioDTO.getIdRolAfiliado()) 
        				|| beneficiarioDTO.getIdRolAfiliado() == null) {
        			if(beneficiarioDTO.getEstadoBeneficiarioAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
        				ResultadoGeneralProductoNoConformeBeneficiarioDTO beneficiarioInactivar = new ResultadoGeneralProductoNoConformeBeneficiarioDTO();
        				beneficiarioInactivar.setIdBeneficiario(beneficiarioDTO.getIdBeneficiario());
        				resultadoGeneralBeneficiarios.add(beneficiarioInactivar);
        			}
        			
        		}
            }
        	
        	if(resultadoGeneralBeneficiarios.size()>0) {
        		inDTO.setResultadoGeneralBeneficiarios(resultadoGeneralBeneficiarios);
        	}
        }
        
        if(inDTO.getResultadoGeneralBeneficiarios()!= null &&
        		  inDTO.getResultadoGeneralBeneficiarios().size()>0) {
        	  actualizarEstadoBeneficiarios(inDTO.getResultadoGeneralBeneficiarios(), true); 
        }
    }

	private boolean contieneBeneficiarioDiferenteAprobado(
			List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios) {
		if (resultadoGeneralBeneficiarios != null && !resultadoGeneralBeneficiarios.isEmpty()) {
			for (ResultadoGeneralProductoNoConformeBeneficiarioDTO dto : resultadoGeneralBeneficiarios) {
				if (!ResultadoGeneralProductoNoConformeEnum.APROBADA.equals(dto.getResultadoGeneralBeneficiario())) {
					return true;
				}
			}
		}
		return false;
	}

	private void enrolar(AfiliadoInDTO inDTO, UserDTO userDTO, Map<String, Object> result, boolean notificar,
			String token) {

		inDTO.setCanalRecepcion(CanalRecepcionEnum.WEB);
		inDTO = AfiliacionPersonasWebCompositeBusinessComun.crearAfiliado(inDTO, token);

		Long idSolicitud = AfiliacionPersonasWebCompositeBusinessComun.crearSolicitudAfiliacionPersona(inDTO);
		result.put("idSolicitudGlobal", idSolicitud);
		String numeroRadicado = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());

		String tiempoCaducidadLink = (String) CacheManager
				.getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK_123);
		String tiempoProcesoSolicitud = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AIPW_TIEMPO_PROCESO_SOLICITUD);
        String tiempoAsignacionBack = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AIPW_TIEMPO_ASIGNACION_BACK);
		
		// Se inicia el proceso
		Map<String, Object> datosProceso = new HashMap<>();
		datosProceso.put("idSolicitud", idSolicitud);
		datosProceso.put("numeroRadicado", numeroRadicado);
		datosProceso.put("tiempoCaducidadLink", tiempoCaducidadLink);
        datosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        datosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);

		IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, datosProceso);
		iniciarProceso.setToken(token);
		iniciarProceso.execute();
		Long idInstanciaProceso = iniciarProceso.getResult();

		result.put("idInstanciaProceso", idInstanciaProceso);

		if (idInstanciaProceso != null && idInstanciaProceso > 0) {
			actualizarIdInstanciaProcesoSolicitudDePersona(idSolicitud, idInstanciaProceso, token);
			try {
				if (notificar) {
					notificarLinkAcceso(inDTO, token, idSolicitud, idInstanciaProceso, false, userDTO, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("enrolar: No se logro enviar notificacion por email");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		} else {
			logger.debug("enrolar: No se logro incial el proceso de afiliacion persona en el BPM");
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE);
		}
	}

	/**
	 * Método encargado de notificar el link de accesso
	 * 
	 * @param inDTO,
	 *            afiliado dto a notificar
	 * @param token,
	 *            token de seguridad
	 * @param idSolicitud,
	 *            identificador de la solicitud
	 * @param idInstanciaProceso,
	 *            id de la instancia de proceso
	 * @param reenvio
	 * @param userDTO
	 * @param replantearDestinatarioTO,
	 *            se replantea si se deben de capturar el correo que llega del
	 *            destinatario
	 * @throws Exception
	 */
	private void notificarLinkAcceso(AfiliadoInDTO inDTO, String token, Long idSolicitud, Long idInstanciaProceso,
			boolean reenvio, UserDTO userDTO, boolean replantearDestinatarioTO) throws Exception {
		Map<String, String> params = generarTokenAcceso(inDTO, idSolicitud, idInstanciaProceso, token, reenvio);
		NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
		if (replantearDestinatarioTO) {
			List<String> destinatarioTO = new ArrayList<String>();
			destinatarioTO.add(inDTO.getPersona().getUbicacionDTO().getCorreoElectronico());
			notificacionParametrizadaDTO.setDestinatarioTO(destinatarioTO);
			notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
		}
		
		EtiquetaPlantillaComunicadoEnum etiqueta = EtiquetaPlantillaComunicadoEnum.NTF_ENRL_AFL_PNS_WEB;
		if (inDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
			etiqueta = EtiquetaPlantillaComunicadoEnum.NTF_ENRL_AFL_IDPE_WEB;
		}
		
		//EtiquetaPlantillaComunicadoEnum etiqueta = EtiquetaPlantillaComunicadoEnum.NTF_ENRL_AFL_IDPE_WEB;
		notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(etiqueta);
		notificacionParametrizadaDTO.setParams(params);
		notificacionParametrizadaDTO.setIdSolicitud(idSolicitud);
		notificacionParametrizadaDTO
				.setProcesoEvento(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION
						.getProceso().name());
		notificacionParametrizadaDTO
				.setTipoTx(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION);

		AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(notificacionParametrizadaDTO);
	}

	private String convertirDatosUrlABase64(Long idSolicitud, Long idInstanciaProceso, String token) {
		String json = "{\"idSolicitud\":" + idSolicitud + ",\"idInstanciaProceso\":" + idInstanciaProceso
				+ ",\"token\":\"" + token + "\"}";
		return Base64.getEncoder().encodeToString(json.getBytes());
	}

	/**
	 * Método que hace la peticion REST al servicio de ejecutar asignacion
	 * 
	 * @param sedeCaja
	 *            <code>Long</code> el identificador del afiliado
	 * @param procesoEnum
	 *            <code>ProcesoEnum</code> el identificador del afiliado
	 * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
	 *         caja
	 */
	private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum, String token) {
		logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String,ProcesoEnum )");
		EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
		String nombreUsuarioCaja = "";
		if (token != null) {
			ejecutarAsignacion.setToken(token);
		}
		ejecutarAsignacion.execute();
		logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String,ProcesoEnum )");
		nombreUsuarioCaja = (String) ejecutarAsignacion.getResult();
		return nombreUsuarioCaja;
	}

	/**
	 * Metodo encargado de llamar al cliente guardarDatosIdentificacion
	 * 
	 * @param inDTO,
	 *            dto de IdentificacionUbicacionPersonaDTO
	 */
	public void guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO inDTO) {
		GuardarDatosIdentificacionYUbicacion guardarDatoIndentificador = new GuardarDatosIdentificacionYUbicacion(
				inDTO);
		guardarDatoIndentificador.execute();
	}

	/**
	 * Metodo encargado de llamar al cliente de GuardarInformacionLaboral
	 * 
	 * @param inDTO,
	 *            dto que pertenece a InformacionLaboralTrabajadorDTO
	 */
	public void guardarInformacionLaboral(InformacionLaboralTrabajadorDTO inDTO) {
		GuardarInformacionLaboral guardarInformacionLaboral = new GuardarInformacionLaboral(inDTO);
		guardarInformacionLaboral.execute();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#verificarSolicitudAfiliacionIndependienteWeb(com.asopagos.afiliaciones.personas.web.composite.dto.VerificarResultadoSolicitudAfiliacionPersonaDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO,
			UserDTO userDTO) {
		logger.debug(
				"Inicia verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO)");
		Map<String, Object> params = obtenerParametrosVerificacionSolicitud(inDTO, userDTO, false);
		TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
		service2.execute();
		logger.debug(
				"Finaliza verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO)");

	}

	private Map<String, Object> obtenerParametrosVerificacionSolicitud(
			VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO, boolean prodConforme) {
		if (inDTO.getIdSolicitudGlobal() == null || inDTO.getIdTarea() == null) {
			logger.debug(
					"verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) : No viene datos completos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}

		Map<String, Object> params = new HashMap<>();
		SolicitudAfiliacionPersonaDTO solicitud = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());

		if (solicitud == null) {
			logger.debug(
					"verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) : No se encontro la solcitud.");
			throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		ArrayList<String> destinatarios = new ArrayList<>();
		destinatarios.add(solicitud.getAfiliadoInDTO().getPersona().getUbicacionDTO().getCorreoElectronico());

		ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
		consultarConstantesCaja.execute();
		ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();

		if (ResultadoGeneralProductoNoConformeEnum.APROBADA == inDTO.getResultadoGeneralAfiliado()) {
			if (AccionResultadoEnum.AFILIAR == inDTO.getAccionResultado()) {
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
				actualizarEstadoRolAfiliado(solicitud.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.ACTIVO);
				boolean conyugue = actualizarBeneficiarios(solicitud.getAfiliadoInDTO().getIdAfiliado(),
						inDTO.getResultadoGeneralBeneficiarios(), solicitud.getAfiliadoInDTO().getIdRolAfiliado());

				AfiliadoInDTO afiliadoInDTO = solicitud.getAfiliadoInDTO();
				afiliadoInDTO.setFechaAfiliacion(Calendar.getInstance().getTime());
				afiliadoInDTO.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
				/*CCREPNORMATIVOS*/
//				if (conyugue) {
//					afiliadoInDTO.getPersona().setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
//				}
				actualizarAfiliado(afiliadoInDTO);
				// TODO Pendiente HU-121-341 invocar servicio para el calculo de
				// la categoría del afiliado
				// TODO Pendiente HU-119 Reconocer aportes y novedades de
				// afiliado

				calcularCategoria(afiliadoInDTO);
				// TODO Pendiente definir campo "Entidad pagadora de aportes de
				// pensionado"

				Map<String, String> parametros = new HashMap<>();
				parametros.put("nombreCompleto", obtenerNombreCompleto(solicitud.getAfiliadoInDTO().getPersona()));
				parametros.put("nombreCCF", constantes.getNombre());

				if (TipoAfiliadoEnum.PENSIONADO == afiliadoInDTO.getTipoAfiliado()) {
					Long idSolicitudEntidad = asociarEntidadPagadora(afiliadoInDTO);
					generarNumeroRadicado(idSolicitudEntidad, userDTO.getSedeCajaCompensacion());
					AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
							EtiquetaPlantillaComunicadoEnum.CRT_ACP_PNS, parametros,
							TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
							solicitud.getIdInstanciaProceso(), null, solicitud.getAfiliadoInDTO().getPersona().getIdPersona());

					AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
							EtiquetaPlantillaComunicadoEnum.CRT_BVD_PNS, parametros,
							TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
							solicitud.getIdInstanciaProceso(), null, solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
				} else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE == afiliadoInDTO.getTipoAfiliado()) {

					AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
							EtiquetaPlantillaComunicadoEnum.CRT_ACP_IDPE, parametros,
							TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
							solicitud.getIdInstanciaProceso(), null, solicitud.getAfiliadoInDTO().getPersona().getIdPersona());

					AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
							EtiquetaPlantillaComunicadoEnum.CRT_BVD_IDPE, parametros,
							TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
							solicitud.getIdInstanciaProceso(), null, solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
				}
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
				if (prodConforme) {
					params.put("resultadoVerificacion", 1);
				} else {
					params.put("resultadoBack", 1);
				}
				params.put("requiereAnalisisEsp", false);

				if (afiliadoInDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.PENSIONADO)
						|| afiliadoInDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
					// Se realiza el reconocimiento de aportes "Retroactivo
					// Automático" cuando se activa la persona independiente o
					// pensionada -> HU-262
					ejecutarRetroactivoAutomaticoPersona(afiliadoInDTO.getPersona().getIdPersona(),
							afiliadoInDTO.getTipoAfiliado());
				}
			} else if (AccionResultadoEnum.ESCALAR == inDTO.getAccionResultado()) {
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(
						inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.ESCALADA);
				inDTO.getEscalamientoSolicitudDTO().setIdSolicitud(inDTO.getIdSolicitudGlobal());
				escalarSolicitudAfiliacion(inDTO.getEscalamientoSolicitudDTO());
				if (prodConforme) {
					params.put("resultadoVerificacion", 1);
				} else {
					params.put("resultadoBack", 1);
				}
				params.put("requiereAnalisisEsp", true);
				params.put("analista", inDTO.getEscalamientoSolicitudDTO().getDestinatario());
			}
		} else if (ResultadoGeneralProductoNoConformeEnum.SUBSANABLE == inDTO.getResultadoGeneralAfiliado()) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_SUBSANABLE);
			//Se Comenta Porque Segun La Hu 123-380 Indica que estos comunicados se deben enviar mediante La HU-TRA-331 Editar comunicado
			/*Map<String, String> parametros = new HashMap<>();
			parametros.put("nombreCompleto", obtenerNombreCompleto(solicitud.getAfiliadoInDTO().getPersona()));
			parametros.put("nombreCCF", constantes.getNombre());
			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
					solicitud.getAfiliadoInDTO().getTipoAfiliado() == TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
							? EtiquetaPlantillaComunicadoEnum.NTF_SBC_AFL_IDPE
							: EtiquetaPlantillaComunicadoEnum.NTF_SBC_AFL_PNS,
					parametros, TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
					solicitud.getIdInstanciaProceso(), inDTO.getIdSolicitudGlobal());*/

			List<ResultadoGeneralValidacionBeneficiarioDTO> listado = inDTO.getResultadoGeneralBeneficiarios();
			if (listado != null && !listado.isEmpty()) {
				for (ResultadoGeneralValidacionBeneficiarioDTO resultadoDTO : listado) {
					if (!resultadoDTO.getAfiliable()) {
						actualizarEstadoBeneficiario(resultadoDTO.getBeneficiario().getIdBeneficiario(),
								EstadoAfiliadoEnum.INACTIVO);
					}
				}
			}
			String tiempoCorrecciones = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_FORMULARIO_123);

			if (prodConforme) {
				params.put("resultadoVerificacion", 1);
			} else {
				params.put("resultadoBack", 2);
			}
			params.put("tiempoCorrecciones", tiempoCorrecciones);
			params.put("requiereAnalisisEsp", false);
			params.put("usuarioBackNoConf", userDTO.getNombreUsuario());
		} else if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE == inDTO.getResultadoGeneralAfiliado()) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
			// actualizarEstadoRolAfiliado(solicitud.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.INACTIVO);
			List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(
					solicitud.getAfiliadoInDTO().getIdAfiliado());
			if (beneficiarios != null && !beneficiarios.isEmpty()) {
				for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
					if (EstadoAfiliadoEnum.ACTIVO == beneficiarioDTO.getEstadoBeneficiarioAfiliado()
							&& beneficiarioDTO.getIdRolAfiliado().equals(solicitud.getAfiliadoInDTO().getIdRolAfiliado())) {
						actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO);
					}
				}
			}
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			if (prodConforme) {
				params.put("resultadoVerificacion", 2);
			} else {
				params.put("resultadoBack", 3);
			}
		}
		return params;
	}

	/**
	 * Metodo encargado de obtener el nombre completo de una persona
	 * 
	 * @param persona,
	 *            persona
	 * @return nombre completo
	 */
	private String obtenerNombreCompleto(PersonaDTO persona) {
		return (persona.getPrimerNombre() != null && !persona.getPrimerNombre().isEmpty()
				? persona.getPrimerNombre() + " " : " ")
				+ (persona.getSegundoNombre() != null && !persona.getSegundoNombre().isEmpty()
						? persona.getSegundoNombre() + " " : " ")
				+ (persona.getPrimerApellido() != null && !persona.getPrimerApellido().isEmpty()
						? persona.getPrimerApellido() + " " : " ")
				+ (persona.getSegundoApellido() != null && !persona.getSegundoApellido().isEmpty()
						? persona.getSegundoApellido() + " " : " ");
	}

	private Long asociarEntidadPagadora(AfiliadoInDTO afiliadoInDTO) {
		logger.debug("Inicia asociarEntidadPagadora(AfiliadoInDTO afiliadoInDTO)");
		CrearSolicitudAsociacionPersonaEntidadPagadora crearSolicitudAsociacionPersonaEntidadPagadora = new CrearSolicitudAsociacionPersonaEntidadPagadora(
				afiliadoInDTO);
		logger.debug("Inicia asociarEntidadPagadora(AfiliadoInDTO afiliadoInDTO)");
		crearSolicitudAsociacionPersonaEntidadPagadora.execute();
		return crearSolicitudAsociacionPersonaEntidadPagadora.getResult();
	}

	private boolean actualizarBeneficiarios(Long idAfiliado,
			List<ResultadoGeneralValidacionBeneficiarioDTO> resultadoGeneralBeneficiarios, Long idRolAfiliado) {
		logger.debug("Inicia actualizarBeneficiarios(Long, List<ResultadoGeneralValidacionBeneficiarioDTO>)");
		boolean conyugue = false;
		if (resultadoGeneralBeneficiarios != null && !resultadoGeneralBeneficiarios.isEmpty()) {
			for (ResultadoGeneralValidacionBeneficiarioDTO resultadoGeneralValidacionBeneficiarioDTO : resultadoGeneralBeneficiarios) {
				BeneficiarioDTO beneficiarioDTO = resultadoGeneralValidacionBeneficiarioDTO.getBeneficiario();
				if (resultadoGeneralValidacionBeneficiarioDTO.getAfiliable()
						&& beneficiarioDTO.getIdBeneficiario() != null) {
					beneficiarioDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
					beneficiarioDTO.setEstadoBeneficiarioCaja(EstadoAfiliadoEnum.ACTIVO);
					beneficiarioDTO.setFechaAfiliacion(Calendar.getInstance().getTime());
					beneficiarioDTO.setIdRolAfiliado(idRolAfiliado);
					actualizarBeneficiario(idAfiliado, beneficiarioDTO);
					if (resultadoGeneralValidacionBeneficiarioDTO.getBeneficiario().getTipoBeneficiario() != null
							&& ClasificacionEnum.CONYUGE == resultadoGeneralValidacionBeneficiarioDTO.getBeneficiario()
									.getTipoBeneficiario()) {
						conyugue = true;
					}
				}
			}
		}
		logger.debug("Finaliza actualizarBeneficiarios(Long, List<ResultadoGeneralValidacionBeneficiarioDTO>)");
		return conyugue;
	}

	/**
	 * Método que hace la peticion REST al servicio de registrar intento de
	 * afiliación
	 */
	private void escalarSolicitudAfiliacion(EscalamientoSolicitudDTO escalamiento) {
		logger.debug("Inicia escalarSolicitudAfiliacion(EscalamientoSolicitudAfiliacionEmpleador)");
		EscalarSolicitud escalarSolicitudAfiliacion = new EscalarSolicitud(escalamiento.getIdSolicitud(), escalamiento);
		escalarSolicitudAfiliacion.execute();
		logger.debug("Finaliza escalarSolicitudAfiliacion(EscalamientoSolicitudAfiliacionEmpleador)");
	}

	/**
	 * Metodo encargado de llamar el cliente que se encarga de iniciar la
	 * instancia proceso
	 * 
	 * @param proceso,
	 *            proceso al cual pertenece la instancia
	 * @param params,
	 *            lista de parametros que recibe la instancia proceso
	 * @return retorna el id de la instancia proceso
	 */
	private Long iniciarProceso(ProcesoEnum proceso, Map<String, Object> params) {
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		logger.debug(proceso);
		params.forEach((k, v) -> logger.debug("Key: " + k + ": Value: " + v));
		logger.debug(proceso);

		logger.debug("Inicia iniciarProceso(ProcesoEnum,Map<String, Object>)");
		IniciarProceso iniciarProceso = new IniciarProceso(proceso, params);
		Long instanciaProceso = new Long(0);
		iniciarProceso.setToken(token);
		iniciarProceso.execute();
		instanciaProceso = iniciarProceso.getResult();
		logger.debug("Finalizar iniciarProceso(ProcesoEnum,Map<String, Object>)");
		return instanciaProceso;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#digitarInformacionContacto(com.asopagos.afiliaciones.empleadores.web.composite.dto.DigitarInformacionContactoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Map<String, Object> digitarInformacionContacto(AfiliadoInDTO inDTO, UserDTO userDTO) {
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		TipoAfiliadoEnum tipoAfiliado = inDTO.getTipoAfiliado();

		TipoTransaccionEnum tipoTransaccion = null;
		String nombreUsuario = null;

		Map<String, Object> result = new HashMap<>();

		if (!validarExistenciaPersonaTipoNumeroIdentificacion(inDTO, tipoAfiliado, token)
				&& validarExistenciaPersonaNumeroIdentificacion(inDTO, tipoAfiliado, token)) {
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.TIPO_IDENTIFICACION_DIFERENTE);
			return result;
		} else if (!validarExistenciaPersonaNumeroIdentificacion(inDTO, tipoAfiliado, token)
				&& validarExistenciaNombreApellidFechaNacimiento(inDTO, tipoAfiliado, token)) {
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.DATOS_IDENTIFICACION_NO_CORRESPONDE);
			return result;
		}
		ListadoSolicitudesAfiliacionDTO listadoSolicitudesAfiliacionDTO = consultarSolicitudAfiliacionPersonaAfiliada(
				EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA, CanalRecepcionEnum.WEB, tipoAfiliado,
				inDTO.getPersona().getNumeroIdentificacion(), inDTO.getPersona().getTipoIdentificacion(), token);
		List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacion = listadoSolicitudesAfiliacionDTO.getLstSolicitudes();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = null;

		if (solicitudesAfiliacion != null && !solicitudesAfiliacion.isEmpty()) {
			ConsultarAfiliado consultarAfiliado = new ConsultarAfiliado(inDTO.getPersona().getNumeroIdentificacion(),
					inDTO.getPersona().getTipoIdentificacion(), true);
			consultarAfiliado.setToken(token);
			consultarAfiliado.execute();
			ConsultarAfiliadoOutDTO outDTO = consultarAfiliado.getResult();
			Boolean existeRol = false;
			for (InformacionLaboralTrabajadorDTO infoAfiliado : outDTO.getInformacionLaboralTrabajador()) {
				if (infoAfiliado.getTipoAfiliado().equals(inDTO.getTipoAfiliado())
						&& EstadoAfiliadoEnum.INACTIVO.equals(infoAfiliado.getEstadoAfiliado())) {
					existeRol = true;
					break;
				}
			}
			if (existeRol) {
				tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO;
				nombreUsuario = outDTO.getIdentificacionUbicacionPersona().getUbicacion().getCorreoElectronico();
			} else {
				tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION;
			}
			for (SolicitudAfiliacionPersonaDTO solicitud : solicitudesAfiliacion) {
				solicitudAfiliacionPersonaDTO = solicitud;
				break;
			}
		} else {
			tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION;
		}

		// Se realiza la validacion de existencia de solicitud de afiliacion y
		// envio de email
		Map<String, String> datosValidacion = new HashMap<>();
		datosValidacion.put("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion().toString());
		datosValidacion.put("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion());
		datosValidacion.put("tipoAfiliado", inDTO.getTipoAfiliado().toString());

		ValidarPersonas validarPersonas = new ValidarPersonas("123-374-2", ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
				inDTO.getTipoAfiliado().toString(), datosValidacion);
		validarPersonas.setToken(token);
		validarPersonas.execute();
		List<ValidacionDTO> list = validarPersonas.getResult();
		ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_PERSONA,
				list);

		if (validacionExistenciaSolicitud != null
				&& validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

			// Indica que tienen una solicitud de afiliacion en proceso
			result.put("idSolicitudGlobal", solicitudAfiliacionPersonaDTO.getIdSolicitudGlobal());
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.AFILIACION_EN_PROCESO_CORREO);

			// se registra intento de afiliacion
			registrarIntentoAfiliacion(solicitudAfiliacionPersonaDTO.getIdSolicitudGlobal(),
					CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA, tipoTransaccion, token);
			return result;
		}

		validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, list);

		if (validacionExistenciaSolicitud != null
				&& validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

			// Indica que tienen una solicitud de afiliacion en proceso
			// result.put("idSolicitudGlobal",
			// solicitudAfiliacionPersonaDTO.getIdSolicitudGlobal());
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.AFILIACION_EN_PROCESO);

			// se registra intento de afiliacion
			registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA, tipoTransaccion,
					token);
			return result;
		}

		Boolean afiliable = true;
		// se realizan validaciones 121-339 condiciones de personas en DB
		// Core
		validarPersonas = new ValidarPersonas("123-374-1", ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
				tipoAfiliado.toString(), datosValidacion);
		validarPersonas.setToken(token);
		validarPersonas.execute();
		list = validarPersonas.getResult();
		for (ValidacionDTO validacionAfiliacionDTO : list) {
			if (ResultadoValidacionEnum.NO_APROBADA == validacionAfiliacionDTO.getResultado()) {
				afiliable = false;
				break;
			}
		}
		// TODO Pendiente Validaciones en Fuentes Externas
		if (afiliable) {
			if (tipoTransaccion == TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO) {
				// Persona con opción de reintegro y cuenta web activa
				if (usuarioActivo(nombreUsuario, token)) {
					registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
							tipoTransaccion, token);
					result.put("resultado", ResultadoRegistroInformacionContactoEnum.REINTEGRO_WEB_ACTIVA);
				} else {
					result.put("resultado", ResultadoRegistroInformacionContactoEnum.REINTEGRO_SIN_WEB_ACTIVA);
					inDTO.getPersona().setTipoTransaccion(tipoTransaccion);
					enrolar(inDTO, userDTO, result, true, token);
				}
			}
			if (tipoTransaccion == TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION) {
				result.put("resultado", ResultadoRegistroInformacionContactoEnum.NUEVA_AFILIACION);
				inDTO.getPersona().setTipoTransaccion(tipoTransaccion);
				enrolar(inDTO, userDTO, result, true, token);
			}
		} else {
			registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
					tipoTransaccion, token);
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.NO_EXITOSA);
			return result;
		}

		return result;
	}

	private void eliminarTokenAcceso(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,
			String token) {
		EliminarTokenAcceso eliminarTokenAcceso = new EliminarTokenAcceso(null, true, numeroIdentificacion,
				tipoIdentificacion);
		eliminarTokenAcceso.setToken(token);
		eliminarTokenAcceso.execute();
	}

	/**
	 * Metodo encargado de generar el token de acceso del afiliado independiente
	 * o pensionado
	 * 
	 * @param inDTO,
	 *            datos del afiliado
	 * @param idSolicitud,
	 *            identificador de la solicitud
	 * @param idInstancia,
	 *            ide de instancia del proceso
	 * @param token,
	 *            token de acceso
	 * @return parametros de envio de informacion al correo electronico
	 * @throws JsonProcessingException,
	 *             genera una excepcion al convertir lso datos del afiliado a
	 *             formato JSON
	 */
	private Map<String, String> generarTokenAcceso(AfiliadoInDTO inDTO, Long idSolicitud, Long idInstancia,
			String token, boolean reenvio) throws JsonProcessingException {
		if (reenvio) {
			eliminarTokenAcceso(inDTO.getPersona().getNumeroIdentificacion(),
					inDTO.getPersona().getTipoIdentificacion(), token);
		}
		GenerarTokenAcceso generarTokenAcceso = new GenerarTokenAcceso(null,
				inDTO.getPersona().getNumeroIdentificacion(), inDTO.getPersona().getTipoIdentificacion());
		generarTokenAcceso.setToken(token);
		generarTokenAcceso.execute();
		TokenDTO tokenG = generarTokenAcceso.getResult();
		String tokenCodificado = convertirDatosUrlABase64(idSolicitud, idInstancia, tokenG.getToken());
		String url = inDTO.getDominio().replace("{token}", tokenCodificado);
		inDTO.setDominio(url);
		Map<String, Object> map = new HashMap<>();
		map.put("dto", inDTO);
		map.put("token", tokenG.getToken());
		ObjectMapper mapper = new ObjectMapper();
		GuardarDatosTemporalesEmpleador datosTemporalesEmpleador = new GuardarDatosTemporalesEmpleador(idSolicitud,
				null, null, mapper.writeValueAsString(map));
		datosTemporalesEmpleador.setToken(token);
		datosTemporalesEmpleador.execute();
		ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
		consultarConstantesCaja.setToken(token);
		consultarConstantesCaja.execute();
		ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("linkRegistro", url);
		params.put("enlaceDeEnrolamiento", url);
		String tiempo = (String) CacheManager.getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK_123);
		params.put("tiempoexpiracionenlace", tiempo);
		String nombreCompleto = (inDTO.getPersona().getPrimerNombre() != null
				&& !inDTO.getPersona().getPrimerNombre().isEmpty() ? inDTO.getPersona().getPrimerNombre() + " " : " ")
				+ (inDTO.getPersona().getSegundoNombre() != null && !inDTO.getPersona().getSegundoNombre().isEmpty()
						? inDTO.getPersona().getSegundoNombre() + " " : " ")
				+ (inDTO.getPersona().getPrimerApellido() != null && !inDTO.getPersona().getPrimerApellido().isEmpty()
						? inDTO.getPersona().getPrimerApellido() + " " : " ")
				+ (inDTO.getPersona().getSegundoApellido() != null && !inDTO.getPersona().getSegundoApellido().isEmpty()
						? inDTO.getPersona().getSegundoApellido() + " " : " ");
		params.put("nombrePersona", nombreCompleto);
		params.put("nombreSedeCCF", constantes.getNombre());
		return params;
	}

	/**
	 * Metodo encargado de reenviar un correo de enrolamiento
	 * 
	 * @param numeroIdentificacion,
	 *            numero de identificacion de la persona
	 * @param tipoIdentificacion,
	 *            tipo de identificacion
	 * @param correoDestinatario,
	 *            correo de destinatario
	 * @param parametrosNotificacion,
	 *            parametro de notificacion
	 * @param notificacion,
	 *            notificacion a enviar
	 * @param token,
	 *            token de acceso
	 */
	private void reenviarCorreoEnrolamiento(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,
			String correoDestinatario, Map<String, String> parametrosNotificacion,
			EtiquetaPlantillaComunicadoEnum notificacion, String token) {
		InformacionReenvioDTO informacionReenvioDTO = new InformacionReenvioDTO();
		informacionReenvioDTO.setCorreoDestinatario(correoDestinatario);
		informacionReenvioDTO.setNotificacion(notificacion);
		informacionReenvioDTO.setNumeroIdentificacion(numeroIdentificacion);
		informacionReenvioDTO.setParametrosNotificacion(parametrosNotificacion);
		informacionReenvioDTO.setTipoIdentificacion(tipoIdentificacion);
		EliminarTokenAcceso eliminarTokenAcceso = new EliminarTokenAcceso(null, true, numeroIdentificacion,
				tipoIdentificacion);
		eliminarTokenAcceso.execute();
		ReenviarCorreoEnrolamiento correoEnrolamiento = new ReenviarCorreoEnrolamiento(informacionReenvioDTO);
		correoEnrolamiento.setToken(token);
		correoEnrolamiento.execute();
	}

	/**
	 * Metodo que indica si el usuario se encuentra activo en el sistema
	 * 
	 * @param nombreUsuario,
	 *            nombre del usuario a consultar
	 * @param token,
	 *            token de acceso
	 * @return true en caso que este activo, false caso contrario
	 */
	private boolean usuarioActivo(String nombreUsuario, String token) {
		EstaUsuarioActivo estaActivo = new EstaUsuarioActivo(nombreUsuario);
		estaActivo.setToken(token);
		estaActivo.execute();
		return estaActivo.getResult();
	}

	@Override
	public Map<String, Object> radicarSolicitudAfiliacionWeb(Long idSolicitud, Integer resultadoFormulario,
			UserDTO userDTO) {
		logger.debug("Inicia radicarSolicitudAfiliacionWeb(Long)");

		Map<String, Object> result = new HashMap<>();
		result.put("resultado", ResultadoRegistroInformacionContactoEnum.TIPO_IDENTIFICACION_DIFERENTE);
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		StringBuilder datos = new StringBuilder();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionDTO = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(idSolicitud, token);

		if (AccionFormularioEnum.CANCELAR.getValor() == resultadoFormulario.intValue()) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			datos.append(AccionFormularioEnum.CANCELAR.getValor());
		} else if (AccionFormularioEnum.CONTINUAR.getValor() == resultadoFormulario.intValue()) {

			Map<String, String> datosValidacion = new HashMap<>();
			datosValidacion.put("tipoIdentificacion",
					solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getTipoIdentificacion().toString());
			datosValidacion.put("numeroIdentificacion",
					solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
			datosValidacion.put("tipoAfiliado", solicitudAfiliacionDTO.getAfiliadoInDTO().getTipoAfiliado().toString());

			ValidarPersonas validarPersonas = new ValidarPersonas("123-378-1", ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
					solicitudAfiliacionDTO.getAfiliadoInDTO().getTipoAfiliado().toString(), datosValidacion);
			validarPersonas.setToken(token);
			validarPersonas.execute();

			List<ValidacionDTO> list = validarPersonas.getResult();
			ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA,
					list);

			if (validacionExistenciaSolicitud != null
					&& validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

				result.put("resultado", ResultadoRegistroInformacionContactoEnum.AFILIACION_EN_PROCESO);

				// Se cancela la solicitud y se aborta su proceso y se registra
				// el intento de afiliación
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
						EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);

				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
						EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

				AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
						new Long(solicitudAfiliacionDTO.getIdInstanciaProceso()));
				aborProceso.execute();

				// se registra intento de afiliacion
				registrarIntentoAfiliacion(idSolicitud, CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA,
						solicitudAfiliacionDTO.getTipoTransaccion(), token);
				return result;
			}

			result.put("resultado", ResultadoRegistroInformacionContactoEnum.NUEVA_AFILIACION);
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
					EstadoSolicitudAfiliacionPersonaEnum.RADICADA);

			String usuarioBack = asignarAutomaticamenteUsuarioCajaCompensacion(null,
					ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, token);
			datos.append(AccionFormularioEnum.CONTINUAR.getValor());
			datos.append(",");
			datos.append(usuarioBack);

			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
					EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK);

			SolicitudDTO solicitudDTO = new SolicitudDTO();
			solicitudDTO.setDestinatario(usuarioBack);
			actualizarSolicitudAfiliacionPersona(idSolicitud, solicitudDTO);

			Date fechaAfilicionActual = new Date();

			actualizarFechaAfiliacionRolAfiliado(solicitudAfiliacionDTO.getAfiliadoInDTO().getIdRolAfiliado(),
					fechaAfilicionActual.getTime());

			ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
			consultarConstantesCaja.execute();
			ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();

			Map<String, String> parametros = new HashMap<>();

			String nombreCompleto = solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getNombreCompleto();
			if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
				nombreCompleto = (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerNombre() != null
						&& !solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerNombre().isEmpty()
								? solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerNombre() + " " : "")
						+ (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getSegundoNombre() != null
								&& !solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getSegundoNombre().isEmpty()
										? solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getSegundoNombre()
												+ " "
										: "")
						+ (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerApellido() != null
								&& !solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerApellido().isEmpty()
										? solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getPrimerApellido()
												+ " "
										: "")
						+ (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getSegundoApellido() != null
								&& !solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getSegundoApellido()
										.isEmpty()
												? solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona()
														.getSegundoApellido() + " "
												: "");
			}
			parametros.put("nombreCompleto", nombreCompleto);
			parametros.put("numeroRadicado", solicitudAfiliacionDTO.getNumeroRadicacion());
			parametros.put("nombreSedeCCF", constantes.getNombre());

			List<String> destinatarios = new ArrayList<String>();
			// Se valida si se cuenta con el correo electronico
			if (solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO() != null && solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO()
					.getCorreoElectronico() != null) {
				destinatarios.add(solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO()
						.getCorreoElectronico());

				// Envio de comunicado NTF_RAD_AFL_PNS_WEB o
				// NTF_RAD_AFL_IDPE_WEB
				EtiquetaPlantillaComunicadoEnum comunicado = null;
				if (solicitudAfiliacionDTO.getAfiliadoInDTO().equals(TipoAfiliadoEnum.PENSIONADO)) {
					comunicado = EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PNS_WEB;
				} else {
					comunicado = EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_IDPE_WEB;
				}
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(comunicado, parametros,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						idSolicitud, solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().getIdPersona());
			}
			/*
			 * eliminarTokenAcceso(solicitudAfiliacionDTO.getAfiliadoInDTO().
			 * getPersona().getNumeroIdentificacion(),
			 * solicitudAfiliacionDTO.getAfiliadoInDTO().getPersona().
			 * getTipoIdentificacion(), token);
			 */
		}

		EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, FORMULARIO_DILIGENCIADO,
				Long.parseLong(solicitudAfiliacionDTO.getIdInstanciaProceso()), datos.toString());
		enviarSenal.setToken(token);
		enviarSenal.execute();

		logger.debug("Finaliza radicarSolicitudAfiliacionWeb(Long)");
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#analizarSolicitudAfiliacionIndependienteWeb(com.asopagos.dto.AnalizarSolicitudAfiliacionDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	public void analizarSolicitudAfiliacionIndependienteWeb(AnalizarSolicitudAfiliacionDTO inDTO, UserDTO userDTO) {
		EscalamientoSolicitudDTO dto = consultarSolicitudEscalada(inDTO.getRegistroResultado().getIdSolicitud());
		if (dto == null) {
			logger.debug("verificarInformacionSolicitud(Long, VerificarSolicitudAfiliacionPersonaDTO)");
			throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		inDTO.getRegistroResultado().setIdSolicitud(inDTO.getRegistroResultado().getIdSolicitud());

		actualizarSolicitudEscalada(inDTO.getRegistroResultado().getIdSolicitud(), inDTO.getRegistroResultado());
		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(dto.getIdSolicitud(),
				EstadoSolicitudAfiliacionPersonaEnum.EN_ANALISIS_ESPECIALIZADO);
		Map<String, Object> params = new HashMap<>();
		TerminarTarea service = new TerminarTarea(inDTO.getIdTarea(), params);
		service.execute();
	}

	/**
	 * Método encargado de consultar los datos de la solicitud escalada
	 * 
	 * @param idSolicitudGlobal,
	 *            identificador de la solicitud
	 * @return datos escalamiento de la solicitud
	 */
	private EscalamientoSolicitudDTO consultarSolicitudEscalada(Long idSolicitudGlobal) {
		logger.debug("Inicia consultarSolicitudEscalada(Long idSolicitudGlobal)");
		ConsultarSolicitudEscalada solicitudEscalada = new ConsultarSolicitudEscalada(idSolicitudGlobal);
		logger.debug("Finaliza consultarSolicitudEscalada(Long idSolicitudGlobal)");
		solicitudEscalada.execute();
		return solicitudEscalada.getResult();
	}

	/**
	 * Metodo encargado de actualizar la solicitud escalada
	 * 
	 * @param idSolicitud,
	 *            identificador de la solicitud
	 * @param escalamientoSolicitudDTO,
	 *            datos del escalamiento
	 */
	private void actualizarSolicitudEscalada(Long idSolicitud, EscalamientoSolicitudDTO escalamientoSolicitudDTO) {
		logger.debug("Inicia actualizarSolicitudEscalada (Long, EscalamientoSolicitudDTO)");
		ActualizarSolicitudEscalada actualizarSolicitudEscalada = new ActualizarSolicitudEscalada(idSolicitud,
				escalamientoSolicitudDTO);
		actualizarSolicitudEscalada.execute();
		logger.debug("Finaliza actualizarSolicitudEscalada (Long, EscalamientoSolicitudDTO)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#verificarResultadosAfiliacionIndependienteWeb(com.asopagos.afiliaciones.personas.web.composite.dto.VerificarResultadoSolicitudAfiliacionPersonaDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void verificarResultadosAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO,
			UserDTO userDTO) {
		logger.debug(
				"Inicia verificarResultadosAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO, UserDTO)");
		if (inDTO.getIdTarea() == null) {
			SolicitudAfiliacionPersonaDTO solicitud = AfiliacionPersonasWebCompositeBusinessComun
					.consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());

			// Si la solicitud ya esta cerrada no haga nada.
			if (solicitud != null
					&& solicitud.getEstadoSolicitud().equals(EstadoSolicitudAfiliacionPersonaEnum.CERRADA)) {
				return;
			}
			inDTO.setIdTarea(consultarTareaAfiliacionPersonas(solicitud.getIdInstanciaProceso()));
		}

		Map<String, Object> params = obtenerParametrosVerificacionSolicitud(inDTO, userDTO, true);
		TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
		service2.execute();
		logger.debug(
				"Finaliza verificarResultadosAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO, UserDTO)");
	}

	/**
	 * Metodo encargado de realizar la afiliacion de un trabajador
	 * 
	 * @param afiliarTrabajadorCandidatoDTO,
	 *            trabajador a realizar la afiliacion
	 * @param userDTO,
	 *            usuario dto que se llama del contexto
	 * @return retorna un AfiliadoInDTO
	 */
	private AfiliadoInDTO afiliarTrabajadorComun(AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO,
			Boolean pendiente, Boolean asignarBack, UserDTO userDTO) {
                        
                logger.info("PASO 1: L_REVISION ASIGANAR A BACK: asignarBack " + asignarBack);
                printJsonMessage(afiliarTrabajadorCandidatoDTO, " afiliarTrabajadorCandidatoDTO ");

		if(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getCodigoCargueMultiple() != null){
			ConsultarDatosTemporales consultarDatosTemporales = new ConsultarDatosTemporales(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdSolicitudGlobal());
			consultarDatosTemporales.execute();
			Gson gson = new GsonBuilder().create();
			String jsonPayload = consultarDatosTemporales.getResult();
			JsonArray jsonArray = gson.fromJson(jsonPayload, JsonArray.class);
			String datoTemporal = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				
				// Extraer la ciudad
				JsonObject afiliadoInDTO = jsonObject.getAsJsonObject("afiliadoInDTO");
            	JsonObject persona = afiliadoInDTO.getAsJsonObject("persona");

				String tipoIdentificacion = persona.getAsJsonObject().get("tipoIdentificacion").getAsString();
				String numeroIdentificacion = persona.getAsJsonObject().get("numeroIdentificacion").getAsString();

				if(tipoIdentificacion.equals(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getTipoIdentificacion().name())
				&& numeroIdentificacion.equals(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getNumeroIdentificacion())){
					datoTemporal = jsonObject.toString();
					break;
				}
			}
			GuardarDatosTemporales guardar = new GuardarDatosTemporales(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), datoTemporal);
            guardar.execute();
		}
		/* Generacion de numero de radicado */
		String numRadicado = null;
		if (afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getNumeroRadicado() != null
				&& !afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getNumeroRadicado().isEmpty()) {
			numRadicado = afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getNumeroRadicado();
		} else {
			numRadicado = generarNumeroRadicado(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(),
					userDTO.getSedeCajaCompensacion());
		}
		afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setNumeroRadicado(numRadicado);
		/* Fecha de afiliacion */
		Long fechaAfiliacion = new Date().getTime();
		Long idRolAfiliado = afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdRolAfiliado();
		actualizarFechaAfiliacionRolAfiliado(idRolAfiliado, fechaAfiliacion);
		/* Se modifica el estado de la solicitud a RADICADA */
		if (pendiente) {
			modificarEstadoSolicitud(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
		} else {
			modificarEstadoSolicitud(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.RADICADA);
			actualizarEstadoRolAfiliado(idRolAfiliado, EstadoAfiliadoEnum.ACTIVO);
		}
		/*
		 * Se asignan los datos adicionales al InformacionLaboralTrabajadorDTO
		 * si esta existe
		 */
		if (afiliarTrabajadorCandidatoDTO.getInformacionLaboralTrabajador() != null) {
			InformacionLaboralTrabajadorDTO identificadorInformacionLaboral = afiliarTrabajadorCandidatoDTO
					.getInformacionLaboralTrabajador();
			identificadorInformacionLaboral.setIdRolAfiliado(idRolAfiliado);
			identificadorInformacionLaboral
					.setIdEmpleador(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdEmpleador());
			identificadorInformacionLaboral.setTipoIdentificacion(
					afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getTipoIdentificacion());
			identificadorInformacionLaboral.setNumeroIdentificacion(
					afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
			/* Se procede a guardar informacion laboral */
			// guardarInformacionLaboral(identificadorInformacionLaboral);
		}
		/*
		 * Se actualiza la fecha de radicacion de la solicitud afiliacion
		 * Persona, el usuario de radicacion, canal de recepcion y metodo de
		 * envio
		 */
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
		solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.WEB);
		solicitudDTO.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
		solicitudDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
		if (afiliarTrabajadorCandidatoDTO.getTipoTransaccion() != null) {
			solicitudDTO.setTipoTransaccion(afiliarTrabajadorCandidatoDTO.getTipoTransaccion());
		}
		// solicitudDTO.getTipoTransaccion()
		solicitudDTO.setFechaRadicacion(new Date());
		calcularCategoria(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO());
		reconocerAportesYNovedadesAfiliado();
		/* Asignacion identificador ubicacion si esta existe */
		if (afiliarTrabajadorCandidatoDTO.getIdentificadorUbicacionPersona() != null) {
			IdentificacionUbicacionPersonaDTO identificadorUbicacion = afiliarTrabajadorCandidatoDTO
					.getIdentificadorUbicacionPersona();
			identificadorUbicacion.setIdRolAfiliado(idRolAfiliado);
			identificadorUbicacion.setPersona(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getPersona());
			if (pendiente) {
				identificadorUbicacion.getPersona().setFechaExpedicionDocumento(null);
				identificadorUbicacion.getPersona().setFechaNacimiento(null);
			}
			/* Se almacenan los datos de identificacion y ubicacion */
			guardarDatosIdentificacionYUbicacion(identificadorUbicacion);
		}
		/* Se realiza la emision de la tarjeta */
		emitirTarjeta();
		// actualizarEstadoRolAfiliado(idRolAfiliado,
		// EstadoAfiliadoEnum.ACTIVO);
		if (afiliarTrabajadorCandidatoDTO.getIniciarProceso() != null
				&& afiliarTrabajadorCandidatoDTO.getIniciarProceso()) {
			String tiempoCorrecciones = (String) CacheManager
					.getParametro(ParametrosSistemaConstants._122_CORREGIR_INFORMACION_TIMER);
			String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_ADW_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_ADW_TIEMPO_ASIGNACION_BACK);
			
			Map<String, Object> params = new HashMap<>();
			params.put("usuarioFront", userDTO.getNombreUsuario());
			params.put("numeroSolicitud", String.valueOf(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal()));
			String numeroRadicado = consultarNumeroRadicado(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal());
			params.put("numeroRadicado", numeroRadicado);
			params.put("tiempoCorrecciones", tiempoCorrecciones);
			params.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
			params.put("tiempoAsignacionBack", tiempoAsignacionBack);

			logger.warn("<<<<<<<<<<<<<<<<<<<<< ESTA LLEGANDO ANTES DE INICIAR PROCESO");

			Long idInstanciaProceso = iniciarProceso(ProcesoEnum.AFILIACION_DEPENDIENTE_WEB, params);

			logger.warn("<<<<<<<<<<<<<<<<<<<<< ESTA LLEGANDO INICIAR "+ idInstanciaProceso);
			solicitudDTO.setIdInstanciaProceso(String.valueOf(idInstanciaProceso));
			afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setIdInstanciaProceso(idInstanciaProceso);
			if (asignarBack == true) {
				String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(0),
						ProcesoEnum.AFILIACION_DEPENDIENTE_WEB, null);
				UsuarioCCF usuarioCCF = consultarUsuarioCajaCompensacion(destinatario);
				String sedeDestinatario = usuarioCCF.getCodigoSede();
				solicitudDTO.setDestinatario(destinatario);
				solicitudDTO.setSedeDestinatario(sedeDestinatario);
				solicitudDTO.setObservacion(null);
                                
				// se envia la señal al bpm

				String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
				EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_DEPENDIENTE_WEB, RADICADA,
						idInstanciaProceso, destinatario);
				enviarSenal.setToken(token);
				enviarSenal.execute();

				// solicitudDTO.setIdInstanciaProceso(String.valueOf(idInstanciaProceso));
				modificarEstadoSolicitud(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(),
						EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK);
			}
		}
                
		actualizarSolicitudAfiliacionPersona(afiliarTrabajadorCandidatoDTO.getIdSolicitudGlobal(), solicitudDTO);
		return afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#afiliarTrabajadorCandidato(java.
	 * lang.Long, com.asopagos.afiliaciones.personas.web.composite.dto.
	 * AfiliarTrabajadorCandidatoDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public AfiliadoInDTO afiliarTrabajadorCandidato(Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO, UserDTO userDTO) {
            
            if (!idEmpleador.equals(afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().getIdEmpleador())) {
			return null;
		} else {
			/*
			 * Cargue multiple Generacion del identificador
			 */
			AfiliadoInDTO afiliadoInDTO = afiliarTrabajadorComun(afiliarTrabajadorCandidatoDTO, false, true, userDTO);
			return afiliadoInDTO;
		}
        }

	@Override
	public Map<String, Object> reintegrarIndependienteWeb(ReintegraAfiliadoDTO reintegraAfiliadoDTO, UserDTO userDTO) {
		Map<String, Object> resultado = new HashMap<>();
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		if (DecisionSiNoEnum.NO == reintegraAfiliadoDTO.getDecision()) {
			ListadoSolicitudesAfiliacionDTO listadoSolicitudesAfiliacionDTO = consultarSolicitudAfiliacionPersonaAfiliada(
					null, null, null, reintegraAfiliadoDTO.getAfiliadoInDTO().getPersona().getNumeroIdentificacion(),
					reintegraAfiliadoDTO.getAfiliadoInDTO().getPersona().getTipoIdentificacion(), token);
			SolicitudAfiliacionPersonaDTO solicitudAnterior = listadoSolicitudesAfiliacionDTO.getLstSolicitudes()
					.iterator().next();
			reintegraAfiliadoDTO.getAfiliadoInDTO().setCanalRecepcion(CanalRecepcionEnum.WEB);
			reintegraAfiliadoDTO.getAfiliadoInDTO().setFechaAfiliacion(Calendar.getInstance().getTime());
			// Se cera la solicitud
			Long idSolicitud = AfiliacionPersonasWebCompositeBusinessComun
					.crearSolicitudAfiliacionPersona(reintegraAfiliadoDTO.getAfiliadoInDTO());
			resultado.put("idSolicitudGlobal", idSolicitud);

			// se cambia el estado del rolAfiliado a Activo
			actualizarEstadoRolAfiliado(reintegraAfiliadoDTO.getAfiliadoInDTO().getIdRolAfiliado(),
					EstadoAfiliadoEnum.ACTIVO);

			// se actualiza estado beneficiarios del afiliado
			List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(
					reintegraAfiliadoDTO.getAfiliadoInDTO().getIdAfiliado(), token);
			if (beneficiarios != null && !beneficiarios.isEmpty()) {
				for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
					actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.ACTIVO, token);
				}
			}

			// se genera el numero de radicado
			String numeroRadicado = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion(), token);
			resultado.put("numeroRadicado", numeroRadicado);
			PlantillaComunicado plantillaComunicado = null;
			// notificar
			ArrayList<String> destinatarios = new ArrayList<>();
			destinatarios
					.add(reintegraAfiliadoDTO.getAfiliadoInDTO().getPersona().getUbicacionDTO().getCorreoElectronico());
			// Envio de comunicado CRT_BVD_IDPE o CRT_BVD_PNS
			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
					TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE == reintegraAfiliadoDTO.getAfiliadoInDTO()
							.getTipoAfiliado() ? EtiquetaPlantillaComunicadoEnum.CRT_BVD_IDPE
									: EtiquetaPlantillaComunicadoEnum.CRT_BVD_PNS,
					new HashMap<>(),
					TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
					idSolicitud, reintegraAfiliadoDTO.getAfiliadoInDTO().getPersona().getIdPersona());
			// Envio de comunicado CRT_ACP_IDPE o CRT_ACP_PNS
			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
					TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE == reintegraAfiliadoDTO.getAfiliadoInDTO()
							.getTipoAfiliado() ? EtiquetaPlantillaComunicadoEnum.CRT_ACP_IDPE
									: EtiquetaPlantillaComunicadoEnum.CRT_ACP_PNS,
					new HashMap<>(),
					TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
					idSolicitud, reintegraAfiliadoDTO.getAfiliadoInDTO().getPersona().getIdPersona());

			// se cambia el estado de la solicitud
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
					EstadoSolicitudAfiliacionPersonaEnum.APROBADA, token);

			// TODO Pendiente Ejecutar la HU-119 Reconocer aportes y novedades
			// de afiliado (también verificará si el solicitante posee aportes
			// del período).

			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(idSolicitud,
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
		} else if (DecisionSiNoEnum.SI == reintegraAfiliadoDTO.getDecision()) {
			enrolar(reintegraAfiliadoDTO.getAfiliadoInDTO(), userDTO, resultado, false, token);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#
	 * guardarDatosAfiliacionTrabajadorCandidato(java.lang.Long, java.util.List,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void guardarDatosAfiliacionTrabajadorCandidato(Long idEmpleador, Long numeroDiaTemporizador,
			List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO, String nombreArchivo, UserDTO userDTO) {
            logger.info("Entro a: guardarDatosAfiliacionTrabajadorCandidato" );
            logger.debug(
				"Inicia guardarDatosAfiliacionTrabajadorCandidato(Long, Long,List<AfiliarTrabajadorCandidatoDTO>)");
		if (!lstTrabajadorCandidatoDTO.isEmpty()) {
			if (lstTrabajadorCandidatoDTO.get(0).getCodigoCargueMultiple() != null) {
				Long idCargueMultiple = lstTrabajadorCandidatoDTO.get(0).getCodigoCargueMultiple();
				try {
					if (idEmpleador != null && !lstTrabajadorCandidatoDTO.isEmpty()) {
						validacionPersonasWeb.validarDatosAfiliacionTrabajadorCandidato(idCargueMultiple,
								lstTrabajadorCandidatoDTO, numeroDiaTemporizador, nombreArchivo, userDTO);
					} else {
						AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoCargueMultiple(idCargueMultiple,
								EstadoCargaMultipleEnum.CANCELADO);
						throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
					}
					logger.debug(
							"Finaliza guardarDatosAfiliacionTrabajadorCandidato(Long, Long,List<AfiliarTrabajadorCandidatoDTO>)");
				} catch (Exception e) {
					AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoCargueMultiple(idCargueMultiple,
							EstadoCargaMultipleEnum.CANCELADO);
					logger.debug(
							"Finaliza guardarDatosAfiliacionTrabajadorCandidato(Long, Long,List<AfiliarTrabajadorCandidatoDTO>)");
				}
			} else {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			}
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.
	 * AfiliacionPersonasWebCompositeService#validarProductoNoConformeEmpleador(
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	public void validarProductoNoConformeEmpleador(Long idEmpleador, Long idIstanciaProceso) {
		if (idEmpleador != null && idIstanciaProceso != null) {
			ProcesoEnum proceso = ProcesoEnum.AFILIACION_DEPENDIENTE_WEB;
			String tipoSenal = CORREGIDO;
			Map<String, Object> params = new HashMap<>();
			enviarSenal(proceso, tipoSenal, idIstanciaProceso, params);
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/**
	 * Metodo encargado de llamar al cliente de enviarSenal
	 * 
	 * @param proceso,
	 *            proceso al cual pertenece el tipo de señal
	 * @param tipoSenal,
	 *            tipo de senal a la que pertenece
	 * @param idInstanciaProceso,
	 *            identificador de la instancia de proceso que petenece
	 * @param params,
	 *            lista de parametros
	 */
	private void enviarSenal(ProcesoEnum proceso, String tipoSenal, Long idInstanciaProceso,
			Map<String, Object> params) {
		logger.debug("Inicia enviarSenal (ProcesoEnum, String,Long,Map<String,Object>)");
		EnviarSenal envioSenal = new EnviarSenal(proceso, tipoSenal, idInstanciaProceso, null);
		envioSenal.execute();
		logger.debug("Finaliza enviarSenal (ProcesoEnum, String,Long,Map<String,Object>)");
	}

	/**
	 * Consulta un archivo deacuerdo con el id del ECM
	 * 
	 * @param archivoId
	 * @return
	 */
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
	 * @param idEmpleador
	 * @param archivoMultiple
	 * @return
	 */
	private ResultadoValidacionArchivoDTO validarEstructuraContenidoArchivo(Long idEmpleador, Long idCargueMultiple,
			InformacionArchivoDTO archivoMultiple) {
		logger.debug(
				"Inicia validarEstructuraContenidoArchivo(validarEstructuraContenidoArchivo(Long, Long, InformacionGeneralArchivoDTO)");
		ResultadoValidacionArchivoDTO resultDTO = new ResultadoValidacionArchivoDTO();
		ValidarEstructuraContenidoArchivo validarArchivo = new ValidarEstructuraContenidoArchivo(idEmpleador,
				idCargueMultiple, archivoMultiple);
		validarArchivo.execute();
		resultDTO = (ResultadoValidacionArchivoDTO) validarArchivo.getResult();
		logger.debug(
				"Finaliza validarEstructuraContenidoArchivo(validarEstructuraContenidoArchivo(Long, Long, InformacionGeneralArchivoDTO)");
		return resultDTO;
	}

	/**
	 * @param idEmpleador
	 * @param cargueMultipleDTO
	 * @return
	 */
	private Long registrarCargueMultiple(Long idEmpleador, CargueMultipleDTO cargueMultipleDTO) {
		logger.debug("Inicia registrarCargueMultiple(Long, CargueAfiliacionMultipleDTO)");
		RegistrarCargueMultiple registrar = new RegistrarCargueMultiple(idEmpleador, cargueMultipleDTO);
		Long result = new Long(0);
		registrar.execute();
		result = registrar.getResult();
		logger.debug("Finaliza registrarCargueMultiple(Long, CargueAfiliacionMultipleDTO)");
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#corregirInformacionAfiliacion(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public void corregirInformacionAfiliacion(Long idInstanciaProceso, String resultadoGestion) {
		logger.debug("Inicia corregirInformacionAfiliacion(Long, String)");
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, INFORMACION_CORREGIDA,
				idInstanciaProceso, resultadoGestion);
		enviarSenal.setToken(token);
		enviarSenal.execute();
		logger.debug("Final corregirInformacionAfiliacion(Long, String)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#gestionarResultadoProductoNoConformeIndependiente(com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO,
	 *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void gestionarResultadoProductoNoConformeIndependiente(GestionarProductoNoConformeSubsanableDTO inDTO,
			Integer resultadoGestion, UserDTO userDTO) {
		logger.debug(
				"Inicia gestionarResultadoProductoNoConforme( Long ,GestionarProductoNoConformeSubsanableDTO, UserDTO )");
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());

		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
				EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
		if (inDTO.getProductosNoConformeDTO() != null && !inDTO.getProductosNoConformeDTO().isEmpty()) {
			CrearProductosNoConforme crearProductosNoConforme = new CrearProductosNoConforme(
					solicitudAfiliacionPersonaDTO.getIdSolicitudAfiliacionPersona(), inDTO.getProductosNoConformeDTO());
			crearProductosNoConforme.execute();
		}
		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
				EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_GESTIONADA);
		// Se termina la tarea
		if (resultadoGestion.intValue() == 2) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
			if (solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado() != null) {
				actualizarEstadoBeneficiarios(inDTO.getBeneficiariosDTO(), true);
				// CC 220114
				actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(),
						EstadoAfiliadoEnum.INACTIVO);
			}
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
		}
		if (inDTO.getCorrigeBack()) {

			Map<String, Object> maps = new HashMap<>();
			maps.put("resultadoGestion", resultadoGestion == 1 ? 1 : 2);
			TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), maps);
			service2.execute();
		} else {
			String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();

			Long instanciaProceso = Long.parseLong(solicitudAfiliacionPersonaDTO.getIdInstanciaProceso());

			EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, INFORMACION_CORREGIDA,
					instanciaProceso, resultadoGestion.toString() + "i");
			enviarSenal.setToken(token);
			enviarSenal.execute();
		}
		logger.debug(
				"Finaliza gestionarResultadoProductoNoConforme( Long ,GestionarProductoNoConformeSubsanableDTO, UserDTO )");
	}

	/**
	 * Método que hace la peticion REST al servicio de obtener tarea activa para
	 * posteriomente finalizar el proceso de Afiliación personas presencial
	 * 
	 * @param idInstanciaProceso
	 *            <code>String</code> El identificador de la Instancia Proceso
	 *            Afiliacion de la Persona
	 * 
	 * @return <code>Long</code> El identificador de la tarea Activa
	 */
	private Long consultarTareaAfiliacionPersonas(String idInstanciaProceso) {
		logger.debug("Inicia consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
		Response response = null;
		String idTarea = null;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
		obtenerTareaActivaService.execute();
		mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
		logger.debug("Finaliza consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
		idTarea = ((Integer) mapResult.get("idTarea")).toString();
		return new Long(idTarea);
	}

	/**
	 * Método que hace la peticion REST al servicio de actualizar el estado de
	 * un rol afiliado
	 * 
	 * @param idAfiliado
	 *            <code>Long</code> El identificador del rol afiliado
	 * @param estadoAfiliadoEnum
	 * @param beneficiarioDTO
	 *            <code>EstadoAfiliadoEnum</code> enumeracion que indica el
	 *            nuevo estado del rol afiliado
	 */
	private void actualizarEstadoRolAfiliado(Long idRolAfiliado, EstadoAfiliadoEnum estadoAfiliadoEnum) {
		logger.debug("Inicia actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
		ActualizarEstadoRolAfiliado actualizarEstadoRolAfiliado = new ActualizarEstadoRolAfiliado(idRolAfiliado,
				estadoAfiliadoEnum);
		actualizarEstadoRolAfiliado.execute();
		logger.debug("Finaliza actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
	}

	@Override
	public void consultaConceptoEscalamiento(ConsultaConceptoEscalamientoDTO inDTO, UserDTO userDTO) {
		logger.debug("Inicia consultaConceptoEscalamiento(Integer, UserDTO)");
		PlantillaComunicado plantillaComunicado = null;
		ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
		consultarConstantesCaja.execute();
		ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();

		SolicitudAfiliacionPersonaDTO solicitud = AfiliacionPersonasWebCompositeBusinessComun
				.consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
		if (solicitud == null) {
			logger.debug(
					"verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) : No se encontro la solcitud.");
			throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		ArrayList<String> destinatarios = new ArrayList<>();
		destinatarios.add(solicitud.getAfiliadoInDTO().getPersona().getUbicacionDTO().getCorreoElectronico());

		if (AccionResultadoConceptoEscalamientoEnum.EXITOSO.getResultadoAnalisis().intValue() == inDTO
				.getResultadoAnalisis().intValue()) {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
			actualizarEstadoRolAfiliado(solicitud.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.ACTIVO);
			boolean conyugue = actualizarBeneficiarios(solicitud.getAfiliadoInDTO().getIdAfiliado(),
					inDTO.getResultadoGeneralBeneficiarios(), solicitud.getAfiliadoInDTO().getIdRolAfiliado());

			AfiliadoInDTO afiliadoInDTO = solicitud.getAfiliadoInDTO();
			afiliadoInDTO.setFechaAfiliacion(Calendar.getInstance().getTime());
			afiliadoInDTO.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
			/*CCREPNORMATIVOS*/
//			if (conyugue) {
//				afiliadoInDTO.getPersona().setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
//			}
			actualizarAfiliado(afiliadoInDTO);

			Map<String, String> parametros = new HashMap<>();
			parametros.put("nombreCompleto", obtenerNombreCompleto(solicitud.getAfiliadoInDTO().getPersona()));
			parametros.put("nombreCCF", constantes.getNombre());
			if (TipoAfiliadoEnum.PENSIONADO == afiliadoInDTO.getTipoAfiliado()) {
				Long idSolicitudEntidad = asociarEntidadPagadora(afiliadoInDTO);
				generarNumeroRadicado(idSolicitudEntidad, userDTO.getSedeCajaCompensacion());
				// Envio de comunicado CRT_ACP_PNS
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.CRT_ACP_PNS, parametros,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						inDTO.getIdSolicitudGlobal(), solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
				// Envio de comunicado CRT_BVD_PNS
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.CRT_BVD_PNS, parametros,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						inDTO.getIdSolicitudGlobal(), solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
			} else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE == afiliadoInDTO.getTipoAfiliado()) {
				// Envio de comunicado CRT_ACP_IDPE
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.CRT_ACP_IDPE, parametros,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						solicitud.getIdSolicitudGlobal(), solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
				// Envio de comunicado CRT_BVD_IDPE
				AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
						EtiquetaPlantillaComunicadoEnum.CRT_BVD_IDPE, parametros,
						TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION, null,
						solicitud.getIdSolicitudGlobal(), solicitud.getAfiliadoInDTO().getPersona().getIdPersona());
			}
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
		} else {
			AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
			//actualizarEstadoRolAfiliado(solicitud.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.INACTIVO);
			List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(
					solicitud.getAfiliadoInDTO().getIdAfiliado());
			if (beneficiarios != null && !beneficiarios.isEmpty()) {
				for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
					if (EstadoAfiliadoEnum.ACTIVO == beneficiarioDTO.getEstadoBeneficiarioAfiliado()) {
						actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO);
					}
				}
			}
		}

		Map<String, Object> maps = new HashMap<>();
		maps.put("resultadoAnalisis", inDTO.getResultadoAnalisis());
		TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), maps);
		service2.execute();
		logger.debug("Finaliza consultaConceptoEscalamiento(Integer, UserDTO)");
	}

	private List<ProductoNoConformeDTO> consultarProductosNoConforme(Long idSolicitudAfiliacion, Boolean resuelto) {
		logger.debug("Inicia consultarProductosNoConforme(Long, Boolean)");
		ConsultarProductosNoConforme consultarProductosNoConforme = new ConsultarProductosNoConforme(
				idSolicitudAfiliacion, resuelto);
		consultarProductosNoConforme.execute();
		List<ProductoNoConformeDTO> productos = consultarProductosNoConforme.getResult();
		logger.debug("Finaliza consultarProductosNoConforme(Long, Boolean)");
		return productos;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#registrarIntentosAfliliaciones(java.util.List,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	public List<IntentoAfiliacionInDTO> registrarIntentosAfliliaciones(
			IntentoAfiliacionesComunicadoDTO intentoAfiliacionesInDTO, UserDTO userDTO) {
		List<IntentoAfiliacionInDTO> intentoAfiliacionesFallidos = new ArrayList<IntentoAfiliacionInDTO>();
		NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
		List<String> destinatario;
		if (intentoAfiliacionesInDTO.getIntentoAfiliaciones() != null) {
			for (IntentoAfiliacionInDTO inDTO : intentoAfiliacionesInDTO.getIntentoAfiliaciones()) {
				try {
					notificacion.setProcesoEvento(inDTO.getTipoTransaccion().name());

					inDTO.setFechaInicioProceso(new Date());
					AfiliacionPersonasWebCompositeBusinessComun.registrarIntentoAfiliacion(inDTO);
				} catch (Exception e) {
					logger.error("Se produjo un error al registrar el intento de afiliación");
					logger.debug("Finaliza registrarIntentosAfliliaciones(List<IntentoAfiliacionInDTO>, UserDTO)");
					intentoAfiliacionesFallidos.add(inDTO);
				}
			}
		}
		String idInstanciaProceso = intentoAfiliacionesInDTO.getIdInstanciaProceso();
		notificacion.setParams(new HashMap<String, String>());
		notificacion.setIdInstanciaProceso(idInstanciaProceso);

		// notificación al empleador
		if (intentoAfiliacionesInDTO.getCorreoElectronicoEmpleador() != null
				&& intentoAfiliacionesInDTO.getComunicadoEmpleador() != null) {
			destinatario = new ArrayList<String>();
			destinatario.add(intentoAfiliacionesInDTO.getCorreoElectronicoEmpleador());
			EtiquetaPlantillaComunicadoEnum etiquetaPlantilla = intentoAfiliacionesInDTO.getComunicadoEmpleador();
			notificacion.setEtiquetaPlantillaComunicado(etiquetaPlantilla);

			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(notificacion);
		}

		// notificación al afiliado (empleado, pensionado, independiente)
		if (intentoAfiliacionesInDTO.getCorreoElectronicoAfiliado() != null
				&& intentoAfiliacionesInDTO.getComunicadoAfiliado() != null) {
			destinatario = new ArrayList<String>();
			destinatario.add(intentoAfiliacionesInDTO.getCorreoElectronicoAfiliado());
			notificacion.setEtiquetaPlantillaComunicado(intentoAfiliacionesInDTO.getComunicadoAfiliado());
			AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(notificacion);
		}

		return intentoAfiliacionesFallidos;
	}

	/**
	 * Método encargado de llamar al cliente que se encarga de
	 * consultarSolicitudes de afiliacion de las personas afiliadas
	 * 
	 * @param estadoSolicitud
	 * @param canalRecepcion
	 * @param tipoAfiliado
	 * @param numeroIdentificacion
	 * @param tipoIdentificacion
	 * @param token
	 * @return retorna el ListadoSolicitudesAfiliacionDTO que se encarga
	 *         contiene la solicitudes del afiliado
	 */
	private ListadoSolicitudesAfiliacionDTO consultarSolicitudAfiliacionPersonaAfiliada(
			EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud, CanalRecepcionEnum canalRecepcion,
			TipoAfiliadoEnum tipoAfiliado, String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,
			String token) {
		ListadoSolicitudesAfiliacionDTO listadoSolicitudesAfiliacionDTO = new ListadoSolicitudesAfiliacionDTO();
		ConsultarSolicitudAfiliacionPersonaAfiliada consultarSolicitudAfiliacionPersonaAfiliada = new ConsultarSolicitudAfiliacionPersonaAfiliada(
				estadoSolicitud, null, canalRecepcion, tipoAfiliado, numeroIdentificacion, tipoIdentificacion);

		if (token != null) {
			consultarSolicitudAfiliacionPersonaAfiliada.setToken(token);
		}
		consultarSolicitudAfiliacionPersonaAfiliada.execute();
		listadoSolicitudesAfiliacionDTO = consultarSolicitudAfiliacionPersonaAfiliada.getResult();
		return listadoSolicitudesAfiliacionDTO;
	}

	/**
	 * Método encargado de llamar el cliente que se encarga de registrar en
	 * consola de estado de cargue múltiple
	 * 
	 * @param consolaEstadoCargueProcesoDTO
	 */
	private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
		RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
		registroConsola.execute();
	}

	@Override
	public void reenviarCorreoEnrolamiento(AfiliadoInDTO inDTO, UserDTO usuarioDTO) {
		logger.debug("Inicio reenviarCorreoEnrolamiento(AfiliadoInDTO inDTO, UserDTO usuarioDTO)");
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();
		ConsultarSolicitudAfiliacionPersona consulSoli = new ConsultarSolicitudAfiliacionPersona(
				inDTO.getIdSolicitudGlobal());
		consulSoli.setToken(token);
		consulSoli.execute();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacion = consulSoli.getResult();

		try {
			notificarLinkAcceso(inDTO, token, solicitudAfiliacion.getIdSolicitudGlobal(),
					solicitudAfiliacion.getIdInstanciaProceso() != null
							? Long.parseLong(solicitudAfiliacion.getIdInstanciaProceso()) : null,
					true, usuarioDTO, true);
			logger.debug("Finaliza reenviarCorreoEnrolamiento(AfiliadoInDTO inDTO, UserDTO usuarioDTO)");
		} catch (NumberFormatException e) {
			logger.debug("enrolar: No se produjo un error al castear la instancia del proceso");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		} catch (Exception e) {
			logger.debug("enrolar: No se logro enviar notificacion por email");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	@Override
	public void cancelarSolicitudPersonasWebTimeout(CancelacionSolicitudPersonasDTO cancelacion) {
	    logger.debug("Inicio el método cancelacionPersonasWeb");
	    try {
		ActualizarEstadoSolicitudAfiliacionPersona estadoSolicitud = new ActualizarEstadoSolicitudAfiliacionPersona(
				cancelacion.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA);
		estadoSolicitud.execute();

		ActualizarEstadoSolicitudAfiliacionPersona estadoSolicitudCerrada = new ActualizarEstadoSolicitudAfiliacionPersona(
			cancelacion.getIdSolicitud(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			estadoSolicitudCerrada.execute();

		ConsultarSolicitudAfiliacionPersona consultarSolicitud = new ConsultarSolicitudAfiliacionPersona(
				cancelacion.getIdSolicitud());
		consultarSolicitud.execute();
		

		SolicitudAfiliacionPersonaDTO solicitudAfiliacion = consultarSolicitud.getResult();
		Long idSolicitudGlobal = solicitudAfiliacion.getIdSolicitudGlobal();

		ConsultarSolicitudGlobal consultarsolicitudglobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
		consultarsolicitudglobal.execute();
		SolicitudModeloDTO solicitudGlobalDTO = consultarsolicitudglobal.getResult();

		solicitudAfiliacion.getTipoTransaccion().getProceso();

		if (ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB.equals(solicitudAfiliacion.getTipoTransaccion().getProceso())) {
			registrarIntentoAfiliacion(idSolicitudGlobal, CausaIntentoFallidoAfiliacionEnum.ENLACE_NO_VIGENTE,
					consultarSolicitud.getResult().getTipoTransaccion());
		} else if (ProcesoEnum.AFILIACION_DEPENDIENTE_WEB
				.equals(solicitudAfiliacion.getTipoTransaccion().getProceso())) {
			registrarIntentoAfiliacion(idSolicitudGlobal,
					CausaIntentoFallidoAfiliacionEnum.DESISTIMIENTO_AUTOMATICO_EMPLEADOR,
					consultarSolicitud.getResult().getTipoTransaccion());
			Solicitud solicitud = solicitudGlobalDTO.convertToSolicitudEntity();
			logger.info(solicitud);
			solicitud.setObservacion("Desistimiento por exceder plazo límite para gestionar la tarea");
			entityManager.merge(solicitud);
		}
		
		// Enviar notificacion
		enviarComunicado(EtiquetaPlantillaComunicadoEnum.NTF_INT_AFL,
				TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, cancelacion.getIdSolicitud());
		logger.debug("Finalizo el método cancelacionPersonasWeb");
	    } catch (Exception e) {
            logger.debug("Rechazar solicitud: No se logro rechazar la solicitud por vencimiento del link.");
			e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
	}

	/**
	 * Metodo encargado de enviar un comunicado dentro de la afiliacion de
	 * personas web
	 * 
	 * @param etiquetaPlantillaComunicado
	 * @param paramsComunicado
	 * @param tipoTransaccion
	 * @param idInstanciaProceso
	 * @param idSolicitud
	 */
	private void enviarComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado,
			TipoTransaccionEnum tipoTransaccion, Long idSolicitud) {
		logger.debug(
				"Inicia enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum,Map<String, String>,TipoTransaccionEnum,String,Long)");
		try {
			NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
			notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(etiquetaPlantillaComunicado);
			Map<String, String> paramsComunicado = new HashMap<>();
			notificacionParametrizadaDTO.setParams(paramsComunicado);
			// notificacionParametrizadaDTO.setIdInstanciaProceso(idInstanciaProceso);
			notificacionParametrizadaDTO.setIdSolicitud(idSolicitud);
			notificacionParametrizadaDTO.setProcesoEvento(tipoTransaccion.getProceso().name());
			notificacionParametrizadaDTO.setTipoTx(tipoTransaccion);

			EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(
					notificacionParametrizadaDTO);
			enviarComunicado.execute();
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
	 * Método que realiza la ejecución del retroactivo automático para una
	 * persona independiente o pensionada
	 * 
	 * @param idEmpleador
	 *            Identificador del empleador
	 */
	private void ejecutarRetroactivoAutomaticoPersona(Long idPersona, TipoAfiliadoEnum tipoAfiliado) {
		logger.debug("Inicia método ejecutarRetroactivoAutomaticoPersona");
		TipoSolicitanteMovimientoAporteEnum tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;

		if (tipoAfiliado.equals(TipoAfiliadoEnum.PENSIONADO)) {
			tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
		}

		// Consulta y actualiza "AporteGeneral" de la persona, a la fecha actual
		List<Long> listaIdsAporteGeneral = new ArrayList<Long>();
		List<AporteGeneralModeloDTO> listaAporteGeneralDTO = consultarAporteGeneralPersona(idPersona, tipoSolicitante,
				EstadoAporteEnum.VIGENTE, EstadoRegistroAporteEnum.RELACIONADO);

		for (AporteGeneralModeloDTO aporteGeneralDTO : listaAporteGeneralDTO) {
			listaIdsAporteGeneral.add(aporteGeneralDTO.getId());
			aporteGeneralDTO.setFechaReconocimiento(new Date().getTime());
			//Ajuste pruebas Mauricio GLPI 59039
			if(aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO")){            
                aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.RELACIONADO);
            }else{
				aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.REGISTRADO);
			}
			aporteGeneralDTO
					.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
			crearActualizarAporteGeneral(aporteGeneralDTO);
		}

		// Consulta y actualiza "AporteDetallado" de la persona, a la fecha
		// actual
		List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = new ArrayList<AporteDetalladoModeloDTO>();
		if (!listaIdsAporteGeneral.isEmpty()) {
			listaAporteDetalladoDTO = consultarAporteDetalladoPorIdsGeneralPersona(listaIdsAporteGeneral, idPersona,
					tipoAfiliado, EstadoAporteEnum.VIGENTE, EstadoRegistroAporteEnum.RELACIONADO);
		}

		for (AporteDetalladoModeloDTO aporteDetalladoDTO : listaAporteDetalladoDTO) {
			//Ajuste pruebas Mauricio GLPI 59039
			if(aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO")){            
                aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.RELACIONADO);
            }else{
				aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.REGISTRADO);
			}
            aporteDetalladoDTO.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
            aporteDetalladoDTO.setFechaMovimiento(new Date().getTime());
			crearActualizarAporteDetallado(aporteDetalladoDTO);
		}

		logger.debug("Finaliza método ejecutarRetroactivoAutomaticoPersona");
	}

	/**
	 * Método que invoca el servicio de consulta de aportes generales para un
	 * independiente o pensionado
	 * 
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoSolicitante
	 *            Tipo de solicitante
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @return La lista de aportes generales asociados a la persona
	 */
	private List<AporteGeneralModeloDTO> consultarAporteGeneralPersona(Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, EstadoAporteEnum estadoAporte,
			EstadoRegistroAporteEnum estadoRegistroAporte) {
		logger.debug("Inicia método consultarAporteGeneralPersona");
		ConsultarAporteGeneralPersona service = new ConsultarAporteGeneralPersona(tipoSolicitante, idPersona,
				estadoAporte, estadoRegistroAporte);
		service.execute();
		logger.debug("Finaliza método consultarAporteGeneralPersona");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de consulta de aportes detallados para una
	 * persona independiente o pensionada, de acuerdo a una lista de aportes
	 * generales
	 * 
	 * @param listaIdsAporteGeneral
	 *            Lista de ids de <code>AporteGeneral</code>
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de cotizante
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @return La lista de aportes detallados asociados
	 */
	private List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneralPersona(
			List<Long> listaIdsAporteGeneral, Long idPersona, TipoAfiliadoEnum tipoAfiliado,
			EstadoAporteEnum estadoAporte, EstadoRegistroAporteEnum estadoRegistroAporte) {
		logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneralPersona");
		ConsultarAporteDetalladoPorIdsGeneralPersona service = new ConsultarAporteDetalladoPorIdsGeneralPersona(
				idPersona, estadoAporte, tipoAfiliado, estadoRegistroAporte, listaIdsAporteGeneral);
		service.execute();
		logger.debug("Finaliza método consultarAporteDetalladoPorIdsGeneralPersona");
		return service.getResult();
	}

	/**
	 * Método que crea o actualiza un registro en la tabla
	 * <code>AporteGeneral</code>
	 * 
	 * @param aporteGeneralDTO
	 *            Información del registro a actualizar
	 */
	private void crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
		logger.debug("Inicia método crearActualizarAporteGeneral");
		CrearActualizarAporteGeneral service = new CrearActualizarAporteGeneral(aporteGeneralDTO);
		service.execute();
		logger.debug("Finaliza método crearActualizarAporteGeneral");
	}

	/**
	 * Método de creación o actualización de un registro en
	 * <code>AporteDetallado</code>
	 * 
	 * @param aporteDetalladoDTO
	 *            Datos del registro a modificar
	 */
	private void crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
		logger.debug("Inicia método crearActualizarDevolucionAporte");
		CrearActualizarAporteDetallado service = new CrearActualizarAporteDetallado(aporteDetalladoDTO);
		service.execute();
		logger.debug("Finaliza método crearActualizarDevolucionAporte");
	}

	/**
	 * Método encargado de llamar el cliente que genera el retiro de
	 * trabajadores y sus beneficiarios mediante novedades
	 * 
	 * @param rolAfiliadoModeloDTO,
	 *            rol Afiliado Modelo DTO
	 */
	private void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
		logger.debug("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
		EjecutarRetiroTrabajadores ejecucionRetiro = new EjecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
		ejecucionRetiro.execute();
		logger.debug("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.AfiliacionPersonasWebCompositeService#registrarBeneficiarioEnvio(java.lang.Long, com.asopagos.dto.BeneficiarioDTO)
	 */
    @Override
    public Long registrarBeneficiarioEnvio(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
        logger.info("Inicia metodo registrarBeneficiarioEnvio");
        try {
            RegistrarBeneficiario beneficiario = new RegistrarBeneficiario(idAfiliado, beneficiarioDTO);
            beneficiario.execute();

            ConsultarUbicacionPersona ubicacionPersonaService = new ConsultarUbicacionPersona(beneficiarioDTO.getPersonaAfiliada().getIdPersona());
            ubicacionPersonaService.execute();
            UbicacionDTO ubicacionPersona = ubicacionPersonaService.getResult();

            if (ubicacionPersona != null && ubicacionPersona.getCorreoElectronico() != null
                    && ubicacionPersona.getAutorizacionEnvioEmail()) {
                Map<String, String> paramsComunicado = new HashMap<String, String>();
                paramsComunicado.put("correos", ubicacionPersona.getCorreoElectronico());
                // Envio de comunicado NTF_REG_BNF_WEB_TRB
                AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum.NTF_REG_BNF_WEB_TRB,
                        paramsComunicado, TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, null,
                        beneficiarioDTO.getIdSolicitud(), beneficiarioDTO.getPersonaAfiliada().getIdPersona());
            }

            List<Long> idsEmpleadores = new ArrayList<>();
            idsEmpleadores.add(beneficiarioDTO.getIdEmpleador());
            ConsultarEmailEmpleadores emailEmpleadorService = new ConsultarEmailEmpleadores(idsEmpleadores);
            emailEmpleadorService.execute();
            //logger.info("emailEmpleadorService::" + new ObjectMapper().writeValueAsString(emailEmpleadorService));
            if (emailEmpleadorService.getResult() != null && !emailEmpleadorService.getResult().isEmpty()) {
                logger.info("entra emailEmpleadorService::");
                Map<String, String> paramsComunicado = new HashMap<String, String>();
                paramsComunicado.put("correos", emailEmpleadorService.getResult().get(0));
                // Envio de comunicado NTF_REG_BNF_WEB_EMP
                logger.info("paramsComunicado::" + new ObjectMapper().writeValueAsString(paramsComunicado));
                AfiliacionPersonasWebCompositeBusinessComun.enviarComunicadoConstruido(
                        EtiquetaPlantillaComunicadoEnum.NTF_REG_BNF_WEB_EMP, paramsComunicado,
                        TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION, null, beneficiarioDTO.getIdSolicitud(),
                        beneficiarioDTO.getIdEmpleador());
            }
            logger.debug("Finaliza registrarBeneficiarioEnvio(Long, BeneficiarioDTO)");
            return beneficiario.getResult();
        } catch (NoResultException nre) {
            logger.debug("Finaliza registrarBeneficiarioEnvio(Long, BeneficiarioDTO):No se pudo registrar beneficiario, recursos insuficientes");
            return null;
        } catch (Exception e) {
            logger.error("No es posible registrar el grupo familiar", e);
            logger.debug("Finaliza registrarBeneficiario(Long, BeneficiarioDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    @Override
    //@Asynchronous
    public String actualizarDatos() {
        ConsultarAfiliadosUnGrupoFamiliar afiliadosSrv = new ConsultarAfiliadosUnGrupoFamiliar();
        afiliadosSrv.execute();
        List<Callable<String>> tareasParalelas = new LinkedList<>();

        List<AfiliadoSolicitudDTO> afiliados = afiliadosSrv.getResult();
        
        int count = 1;
        int total = afiliados.size();

        for (AfiliadoSolicitudDTO afiliado : afiliados) {
            Callable<String> parallelTask = () -> {
                final AfiliadoSolicitudDTO afiliadoFinal = afiliado;
                return actualizarJsonTemporal(afiliadoFinal);
            };
            tareasParalelas.add(parallelTask);
            count ++;
        }
        
        List<Future<String>> resultadosFuturos =  new ArrayList<>();
        try {
            resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
            
        } catch (InterruptedException e) {
            logger.error("Finaliza procesarNotificacionesParalelo(List<NotificacionParametrizadaDTO>): Error al procesar la notificación",
                    e);
        } 
        
        return "La actualización fue exitosa";
    }

    private String actualizarJsonTemporal(AfiliadoSolicitudDTO afiliadoFinal) {
        
        GuardarTemporalAfiliacionPersona guardarTemporal = new GuardarTemporalAfiliacionPersona();
        ConsultarGrupoFamiliar grupoFamiliarSrv = new ConsultarGrupoFamiliar(afiliadoFinal.getIdAfiliado());
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = "";
        GuardarDatosTemporales guardar = null;
        List<BeneficiarioDTO> beneficiarios = null;
        

        ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(afiliadoFinal.getIdSolicitud());
        consultTemporal.execute();
        String dataTemporal = consultTemporal.getResult();
        try {
            if (dataTemporal != null && !dataTemporal.isEmpty()) {
                guardarTemporal = mapper.readValue(dataTemporal, GuardarTemporalAfiliacionPersona.class);
                //if (guardarTemporal.getBeneficiarios() == null || guardarTemporal.getBeneficiarios().isEmpty()) {
                    
                    beneficiarios = consultarBeneficiariosDeAfiliado(afiliadoFinal.getIdAfiliado());
                    beneficiarios = subdividir(beneficiarios);
                    
                    grupoFamiliarSrv.execute();
                    List<GrupoFamiliarDTO> grupofamiliar = grupoFamiliarSrv.getResult();
                    
                    guardarTemporal.setBeneficiarios(beneficiarios);
                    guardarTemporal.setGruposFamiliares(grupofamiliar);
                    jsonPayload = mapper.writeValueAsString(guardarTemporal);
                    guardar = new GuardarDatosTemporales(afiliadoFinal.getIdSolicitud(), jsonPayload);
                    guardar.execute();
                //}
            }else{
                ConsultarAfiliadoOutDTO afiliado = new ConsultarAfiliadoOutDTO();
                ConsultarAfiliado afiliadoService = new ConsultarAfiliado(afiliadoFinal.getNumeroIdentificacion() , afiliadoFinal.getTipoIdentificacion(), null);
                afiliadoService.execute();
                
                afiliado = afiliadoService.getResult();
                
                beneficiarios = consultarBeneficiariosDeAfiliado(afiliadoFinal.getIdAfiliado());
                beneficiarios = subdividir(beneficiarios);
                
                grupoFamiliarSrv.execute();
                List<GrupoFamiliarDTO> grupofamiliar = grupoFamiliarSrv.getResult();
                
                guardarTemporal.setModeloInformacion(afiliado.getIdentificacionUbicacionPersona());
                guardarTemporal.setBeneficiarios(beneficiarios);
                guardarTemporal.setGruposFamiliares(grupofamiliar);
                
                for (InformacionLaboralTrabajadorDTO infoLaboral : afiliado.getInformacionLaboralTrabajador()) {
                    if (infoLaboral.getIdRolAfiliado().equals(afiliadoFinal.getIdRolAfiliado())) {
                        guardarTemporal.setInfoLaboral(infoLaboral);
                        break;
                    }
                }
                
                jsonPayload = mapper.writeValueAsString(guardarTemporal);
                
                guardar = new GuardarDatosTemporales(afiliadoFinal.getIdSolicitud(), jsonPayload);
                guardar.execute();
                
                
            }
            //            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        contador++;
        return jsonPayload;
    }
    
    
    /**
     * 
     * @param beneficiariosTmp
     * @return
     */
    private List<BeneficiarioDTO> subdividir(List<BeneficiarioDTO> beneficiariosTmp) {
    	List<BeneficiarioDTO> beneficiarios = new ArrayList<>();
    	BeneficiarioDTO ben;
    	for(BeneficiarioDTO b : beneficiariosTmp) {
    		ben = new BeneficiarioDTO();
    		if(ClasificacionEnum.CONYUGE.equals(b.getTipoBeneficiario())) {
    			ben.setPersona(b.getPersona());
    			ben.setIdGrupoFamiliar(b.getIdGrupoFamiliar());
    			ben.setResultadoValidacion(b.getResultadoValidacion());
    			ben.setLabora(b.getLabora());
    			ben.setSalarioMensualBeneficiario(b.getSalarioMensualBeneficiario());
    			ben.setListaChequeo(b.getListaChequeo());
    			ben.setFechaAfiliacion(b.getFechaAfiliacion());
    			ben.setIdBeneficiario(b.getIdBeneficiario());
    			ben.setIdRolAfiliado(b.getIdRolAfiliado());
    			ben.setMotivoDesafiliacion(b.getMotivoDesafiliacion());
    			ben.setFechaRetiro(b.getFechaRetiro());
    			/*
    			ben.setUbicacion(b.getUbicacion());
    			ben.setAutorizacionUsoDatosPersonales(b.getAutorizacionUsoDatosPersonales());
    			ben.setResideSectorRural(b.getResideSectorRural());
    			ben.setClaseIndependiente(b.getClaseIndependiente());
    			ben.setPorcentajeAportes(b.getPorcentajeAportes());
    			ben.setValorMesadaSalarioIngresos(b.getValorMesadaSalarioIngresos());
    			ben.setIdPagadorPension(b.getIdPagadorPension());
    			ben.setIdEntidadPagadora(b.getIdEntidadPagadora());
    			ben.setIdentificadorAnteEntidadPagadora(b.getIdentificadorAnteEntidadPagadora());
    			ben.setPrimerNombre(b.getPrimerNombre());
    			ben.setSegundoNombre(b.getSegundoNombre());
    			ben.setPrimerApellido(b.getPrimerApellido());
    			ben.setSegundoApellido(b.getSegundoApellido());
    			ben.setMedioDePago(b.getMedioDePago());
    			ben.setOportunidadPago(b.getOportunidadPago());
    			ben.setMunicipioDesempenioLabores(b.getMunicipioDesempenioLabores());
    			*/
    		} else {
    			ben.setTipoBeneficiario(b.getTipoBeneficiario());
    			ben.setCertificadoEscolaridad(b.getCertificadoEscolaridad());
    			ben.setFechaVencimientoCertificadoEscolar(b.getFechaVencimientoCertificadoEscolar());
    			ben.setFechaRecepcionCertificadoEscolar(b.getFechaRecepcionCertificadoEscolar());
    			ben.setEstudianteTrabajoDesarrolloHumano(b.getEstudianteTrabajoDesarrolloHumano());
    			ben.setInvalidez(b.getInvalidez());
    			ben.setFechaReporteInvalidez(b.getFechaReporteInvalidez());
    			ben.setComentariosInvalidez(b.getComentariosInvalidez());
    			ben.setResultadoValidacion(b.getResultadoValidacion());
    			ben.setIdGrupoFamiliar(b.getIdGrupoFamiliar());
    			ben.setListaChequeo(b.getListaChequeo());
    			ben.setPersona(b.getPersona());
    			ben.setFechaAfiliacion(b.getFechaAfiliacion());
    			ben.setIdBeneficiario(b.getIdBeneficiario());
    			ben.setIdGradoAcademico(b.getIdGradoAcademico());
    			ben.setIdRolAfiliado(b.getIdRolAfiliado());
    			ben.setMotivoDesafiliacion(b.getMotivoDesafiliacion());
    			ben.setFechaRetiro(b.getFechaRetiro());

    		}
    	}
    	return beneficiarios; 
    }

    
    @Override
    public AfiliadoInDTO afiliarTrabajadorCandidatoCopyMasivo(Long idEmpleador,AfiliacionPersonaWebMasivaDTO afiliarTrabajadorCandidatoDTO) {
        
        if (!idEmpleador.equals(afiliarTrabajadorCandidatoDTO.getCandidatoAfiliacion().getAfiliadoInDTO().getIdEmpleador())) {
			return null;
		} else {
			/*
			 * Cargue multiple Generacion del identificador
			 */
			AfiliadoInDTO afiliadoInDTO = afiliarTrabajadorComun(afiliarTrabajadorCandidatoDTO.getCandidatoAfiliacion(), false, true, afiliarTrabajadorCandidatoDTO.getUserDTO());
			return afiliadoInDTO;
		}
    } 
	 @Override
	 public AfiliarTrabajadorCandidatoDTO completarAfiliacionCargueMultiple(AfiliarTrabajadorCandidatoDTO candidato){
        candidato = AfiliacionPersonasWebCompositeBusinessComun.registrarSolicitudAfiliacionPersonaCargueMultiple(candidato);
        Long solicitudGlobal = candidato.getIdSolicitudGlobal();
		Long rolAfiliado = candidato.getAfiliadoInDTO().getIdRolAfiliado();
		CrearSolicitudCargueMultiple crearSolicitudCargueMultiple = new CrearSolicitudCargueMultiple(candidato.getCodigoCargueMultiple());
		crearSolicitudCargueMultiple.execute();
		//solicitud asociada al cargue y al dato temporal
		Long res = crearSolicitudCargueMultiple.getRes();
		candidato.setIdSolicitudGlobal(res);
		CrearSolicitudAfiliacionPersonaSolo crearSolicitudAfiliacionPersonaSolo = new CrearSolicitudAfiliacionPersonaSolo(rolAfiliado, res);
		crearSolicitudAfiliacionPersonaSolo.execute();
			//se cierran las solicitudes que no pasaron validaciones
			if(!candidato.getAfiliable()){
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(candidato.getIdSolicitudGlobal(),
						EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);
				
				AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(candidato.getIdSolicitudGlobal(),
						EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
				
				IntentoAfiliacionInDTO afiliacionInDTO = new IntentoAfiliacionInDTO();
				afiliacionInDTO.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES);
				afiliacionInDTO.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
				afiliacionInDTO.setIdSolicitud(candidato.getIdSolicitudGlobal());
				AfiliacionPersonasWebCompositeBusinessComun.registrarIntentoAfiliacion(afiliacionInDTO);
			}
			return candidato; 
	 }

	public List<AfiliarTrabajadorCandidatoDTO>  completarAfiliacionCargueMultipleMasivo(List<AfiliarTrabajadorCandidatoDTO> afiliados){
       List<AfiliarTrabajadorCandidatoDTO> resultado = new ArrayList<>();
        try {
            // se prepara la carga paralela en el servicio de archivos
            List<Callable<AfiliarTrabajadorCandidatoDTO>> tareasParalelas = new LinkedList<>();
            List<Future<AfiliarTrabajadorCandidatoDTO>> resultadosFuturos;
            for (AfiliarTrabajadorCandidatoDTO afiliado : afiliados) {
                Callable<AfiliarTrabajadorCandidatoDTO> parallelTaskArchivos = () -> {
                    return completarAfiliacionCargueMultiple(afiliado);
                };
                tareasParalelas.add(parallelTaskArchivos);
            }
            resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<AfiliarTrabajadorCandidatoDTO> future : resultadosFuturos) {
                resultado.add(future.get());
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
		return resultado;

		/*	for(AfiliarTrabajadorCandidatoDTO afiliado: afiliados){
        		afiliado = completarAfiliacionCargueMultiple(afiliado);
   			}
		return afiliados;*/
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

	@Asynchronous
    @Override
    public void solicitarAfiliacionMasiva(Long idEmpleador,AfiliacionArchivoPlanoDTO candidatosAfiliacion,UserDTO userDTO) {
        List<AfiliadoInDTO> afiliado = afiliacionesPersonasWeb(idEmpleador,candidatosAfiliacion.getListaCandidatos(),userDTO);
    }
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
        public AfiliadoInDTO procesarAfiliacionPersonasWebMasiva(Long idEmpleador, AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb) {
            logger.debug("Inicia procesarAfiliacionPersonasWebMasiva(Long,AfiliarTrabajadorCandidatoDTO)");
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + idEmpleador);
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + afiliacionPersonaWeb.getCandidatoAfiliacion().getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
            logger.info("ENTRO A procesarAfiliacionPersonasWebMasiva;" + afiliacionPersonaWeb.getUserDTO().getNombreUsuario());  
        AfiliadoInDTO afiliadoDTO = afiliarTrabajadorCandidatoCopyMasivo(idEmpleador, afiliacionPersonaWeb);
        
        logger.info("Resultado buscarCodigoPais " + afiliadoDTO.getNumeroRadicado());

        
        return afiliadoDTO;
    }

	@Override
	public Map<String, Object> digitarInformacionContactoWS(AfiliadoInDTO inDTO, UserDTO userDTO) {
		String token = AfiliacionPersonasWebCompositeBusinessComun.generarTokenAccesoCore();

		TipoAfiliadoEnum tipoAfiliado = inDTO.getTipoAfiliado();

		TipoTransaccionEnum tipoTransaccion = null;
		String nombreUsuario = null;

		Map<String, Object> result = new HashMap<>();

		if (!validarExistenciaPersonaTipoNumeroIdentificacion(inDTO, tipoAfiliado, token)
				&& validarExistenciaPersonaNumeroIdentificacion(inDTO, tipoAfiliado, token)) {
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.TIPO_IDENTIFICACION_DIFERENTE);
			return result;
		} else if (!validarExistenciaPersonaNumeroIdentificacion(inDTO, tipoAfiliado, token)
				&& validarExistenciaNombreApellidFechaNacimiento(inDTO, tipoAfiliado, token)) {
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.DATOS_IDENTIFICACION_NO_CORRESPONDE);
			return result;
		}
		ListadoSolicitudesAfiliacionDTO listadoSolicitudesAfiliacionDTO = consultarSolicitudAfiliacionPersonaAfiliada(
				EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA, CanalRecepcionEnum.WEB, tipoAfiliado,
				inDTO.getPersona().getNumeroIdentificacion(), inDTO.getPersona().getTipoIdentificacion(), token);
		List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacion = listadoSolicitudesAfiliacionDTO.getLstSolicitudes();
		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = null;

		if (solicitudesAfiliacion != null && !solicitudesAfiliacion.isEmpty()) {
			ConsultarAfiliado consultarAfiliado = new ConsultarAfiliado(inDTO.getPersona().getNumeroIdentificacion(),
					inDTO.getPersona().getTipoIdentificacion(), true);
			consultarAfiliado.setToken(token);
			consultarAfiliado.execute();
			ConsultarAfiliadoOutDTO outDTO = consultarAfiliado.getResult();
			Boolean existeRol = false;
			for (InformacionLaboralTrabajadorDTO infoAfiliado : outDTO.getInformacionLaboralTrabajador()) {
				if (infoAfiliado.getTipoAfiliado().equals(inDTO.getTipoAfiliado())
						&& EstadoAfiliadoEnum.INACTIVO.equals(infoAfiliado.getEstadoAfiliado())) {
					existeRol = true;
					break;
				}
			}
			if (existeRol) {
				tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO;
				nombreUsuario = outDTO.getIdentificacionUbicacionPersona().getUbicacion().getCorreoElectronico();
			} else {
				tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION;
			}
			for (SolicitudAfiliacionPersonaDTO solicitud : solicitudesAfiliacion) {
				solicitudAfiliacionPersonaDTO = solicitud;
				break;
			}
		} else {
			tipoTransaccion = TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION;
		}

		// Se realiza la validacion de existencia de solicitud de afiliacion y
		// envio de email
		Map<String, String> datosValidacion = new HashMap<>();
		datosValidacion.put("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion().toString());
		datosValidacion.put("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion());
		datosValidacion.put("tipoAfiliado", inDTO.getTipoAfiliado().toString());

		ValidarPersonas validarPersonas = new ValidarPersonas("123-374-2", ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
				inDTO.getTipoAfiliado().toString(), datosValidacion);
		validarPersonas.setToken(token);
		validarPersonas.execute();
		List<ValidacionDTO> list = validarPersonas.getResult();
		ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_PERSONA,
				list);

		if (validacionExistenciaSolicitud != null
				&& validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

			logger.warn(validacionExistenciaSolicitud.getResultado().name());
			// Indica que tienen una solicitud de afiliacion en proceso
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.AFILIACION_EN_PROCESO_CORREO);

			// se registra intento de afiliacion
			registrarIntentoAfiliacion(solicitudAfiliacionPersonaDTO.getIdSolicitudGlobal(),
					CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA, tipoTransaccion, token);
			return result;
		}

		validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, list);

		if (validacionExistenciaSolicitud != null
				&& validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

			// Indica que tienen una solicitud de afiliacion en proceso
			// result.put("idSolicitudGlobal",
			// solicitudAfiliacionPersonaDTO.getIdSolicitudGlobal());
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.AFILIACION_EN_PROCESO);

			// se registra intento de afiliacion
			registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA, tipoTransaccion,
					token);
			return result;
		}

		Boolean afiliable = true;
		// se realizan validaciones 121-339 condiciones de personas en DB
		// Core
		validarPersonas = new ValidarPersonas("123-374-1", ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB,
				tipoAfiliado.toString(), datosValidacion);
		validarPersonas.setToken(token);
		validarPersonas.execute();
		list = validarPersonas.getResult();
		for (ValidacionDTO validacionAfiliacionDTO : list) {
			if (ResultadoValidacionEnum.NO_APROBADA == validacionAfiliacionDTO.getResultado()) {
				afiliable = false;
				break;
			}
		}
		// TODO Pendiente Validaciones en Fuentes Externas
		if (afiliable) {

			if (tipoTransaccion == TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION) {
				result.put("resultado", ResultadoRegistroInformacionContactoEnum.NUEVA_AFILIACION);
				inDTO.getPersona().setTipoTransaccion(tipoTransaccion);
				// radicamos
				Map<String,Object> datosProceso = enrolarWS(inDTO, userDTO, result, false, token);
				// guardartemporal
				if(inDTO.getTipoAfiliado().name().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name())){
					GuardarDatosTemporales guardar = new GuardarDatosTemporales(Long.valueOf(datosProceso.get("idSolicitud").toString()),construirDatoTemporalIndependiente(inDTO,Long.valueOf(datosProceso.get("idSolicitud").toString()),userDTO));
					guardar.execute();
				} else {
					GuardarDatosTemporales guardar = new GuardarDatosTemporales(Long.valueOf(datosProceso.get("idSolicitud").toString()),construirDatoTemporalPensionado(inDTO,Long.valueOf(datosProceso.get("idSolicitud").toString()),userDTO));
					guardar.execute();
					//construirDatoTemporalPensionado(inDTO,idSolicitudGlobal,userDTO);
				}

				// radicar
				result.put("numeroRadicado", datosProceso.get("numeroRadicado").toString());
				radicarSolicitudAfiliacionWeb(Long.valueOf(datosProceso.get("idSolicitud").toString()),Integer.valueOf(2),userDTO);
			}
		} else {
			registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
					tipoTransaccion, token);
			result.put("resultado", ResultadoRegistroInformacionContactoEnum.NO_EXITOSA);
			return result;
		}

		return result;
	}

	private Map<String,Object> enrolarWS(AfiliadoInDTO inDTO, UserDTO userDTO, Map<String, Object> result, boolean notificar,
			String token) {

		inDTO.setCanalRecepcion(CanalRecepcionEnum.WEB);
		inDTO = AfiliacionPersonasWebCompositeBusinessComun.crearAfiliado(inDTO, token);

		Long idSolicitud = AfiliacionPersonasWebCompositeBusinessComun.crearSolicitudAfiliacionPersona(inDTO);
		result.put("idSolicitudGlobal", idSolicitud);
		String numeroRadicado = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());

		String tiempoCaducidadLink = (String) CacheManager
				.getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK_123);
		String tiempoProcesoSolicitud = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AIPW_TIEMPO_PROCESO_SOLICITUD);
        String tiempoAsignacionBack = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AIPW_TIEMPO_ASIGNACION_BACK);
		
		// Se inicia el proceso
		Map<String, Object> datosProceso = new HashMap<>();
		datosProceso.put("idSolicitud", idSolicitud);
		datosProceso.put("numeroRadicado", numeroRadicado);
		datosProceso.put("tiempoCaducidadLink", tiempoCaducidadLink);
        datosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        datosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);

		IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.AFILIACION_INDEPENDIENTE_WEB, datosProceso);
		iniciarProceso.setToken(token);
		iniciarProceso.execute();
		Long idInstanciaProceso = iniciarProceso.getResult();

		result.put("idInstanciaProceso", idInstanciaProceso);

		if (idInstanciaProceso != null && idInstanciaProceso > 0) {
			actualizarIdInstanciaProcesoSolicitudDePersona(idSolicitud, idInstanciaProceso, token);
			try {
				if (notificar) {
					notificarLinkAcceso(inDTO, token, idSolicitud, idInstanciaProceso, false, userDTO, true);
				}

				return datosProceso;
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("enrolar: No se logro enviar notificacion por email");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		} else {
			logger.debug("enrolar: No se logro incial el proceso de afiliacion persona en el BPM");
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE);
		}
	}

	private String construirDatoTemporalIndependiente(
			AfiliadoInDTO afiliadoIn, Long idSolicitudGlobal, UserDTO userDto) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			// 1. Construcción de información de contacto
			afiliadoIn = construirInformacionContacto(afiliadoIn, idSolicitudGlobal);

			// 2. Construcción de lista de chequeo
			List<ItemChequeoDTO> listaChequeo = Arrays.asList(
				buildItemChequeo(69L, "Copia del documento de identidad",
						EstadoRequisitoTipoSolicitanteEnum.OPCIONAL, TipoRequisitoEnum.ESTANDAR),
				buildItemChequeo(89L, "Formulario solicitud afiliación o reintegro independiente",
						EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO, TipoRequisitoEnum.COMPORTAMIENTO_TIPO_A)
			);

			// 3. Crear nodos base
			ObjectNode root = mapper.createObjectNode();
			ObjectNode afiliadoNode = (ObjectNode) mapper.valueToTree(afiliadoIn);
			afiliadoNode.remove("persona");

			// 4. Construcción de "afiliado"
			ObjectNode afiliado = mapper.createObjectNode();
			afiliado.put("tipoAfiliado", afiliadoIn.getTipoAfiliado().name());
			afiliado.put("canalRecepcion", afiliadoIn.getCanalRecepcion().name());
			afiliado.put("idAfiliado", afiliadoIn.getIdAfiliado());
			afiliado.put("idRolAfiliado", afiliadoIn.getIdRolAfiliado());
			afiliado.put("nombreUsuarioCaja", userDto.getNombreUsuario());
			afiliado.set("persona", mapper.valueToTree(afiliadoIn.getPersona()));
			afiliado.putAll(afiliadoNode);

			// 5. Construcción de "modeloInformacionPaso2"
			ObjectNode modeloInformacion = mapper.createObjectNode();
			modeloInformacion.set("persona", mapper.valueToTree(afiliadoIn.getPersona()));
			modeloInformacion.set("ubicacion", mapper.valueToTree(afiliadoIn.getPersona().getUbicacionDTO()));
			modeloInformacion.putAll(afiliadoNode);

			modeloInformacion.put("resideSectorRural", false);
			modeloInformacion.put("autorizacionUsoDatosPersonales", true);
			modeloInformacion.put("idRolAfiliado", afiliadoIn.getIdRolAfiliado());
			modeloInformacion.put("oportunidadPago", "MES_ACTUAL");
			modeloInformacion.put("claseIndependiente",
					afiliadoIn.getTrabajadorIndependienteWS().getClaseIndependiente().name());
			modeloInformacion.put("porcentajeAportes",
					afiliadoIn.getTrabajadorIndependienteWS().getPorcentajeAporte().getValor());
			modeloInformacion.put("valorMesadaSalarioIngresos",
					afiliadoIn.getTrabajadorIndependienteWS().getIngresosMenensuales());

			// 6. Armar objeto raíz
			root.set("dto", afiliado);
			root.set("afiliadoInDTO", afiliado);
			root.set("modeloInformacionPaso2", modeloInformacion);
			root.set("listaChequeo", mapper.valueToTree(listaChequeo));

			// 7. Logging y retorno
			String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			return jsonResult;

		} catch (Exception e) {
			logger.error("Error construyendo dato temporal independiente", e);
			return null;
		}
	}

	/**
	 * Método auxiliar para crear un ItemChequeoDTO más legible.
	 */
	private ItemChequeoDTO buildItemChequeo(Long idRequisito, String nombre,
											EstadoRequisitoTipoSolicitanteEnum estado,
											TipoRequisitoEnum tipo) {
		ItemChequeoDTO item = new ItemChequeoDTO();
		item.setIdRequisito(idRequisito);
		item.setIdentificadorDocumento(null);
		item.setNombreRequisito(nombre);
		item.setEstadoRequisito(estado);
		item.setTipoRequisito(tipo);
		return item;
	}	

	private AfiliadoInDTO construirInformacionContacto(AfiliadoInDTO afiliadoIn,Long idSolicitudGLobal){
        try{
			Object[] v = new Object[5];
			if (afiliadoIn.getTrabajadorIndependienteWS() != null) {
				v = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_AFILIACION_WS).setParameter("solId",idSolicitudGLobal).setParameter("municipio",afiliadoIn.getTrabajadorIndependienteWS().getMunicipioDIreccion()).getSingleResult();
			}else if(afiliadoIn.getPensionadoWS() != null){
				v = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_AFILIACION_WS).setParameter("solId",idSolicitudGLobal).setParameter("municipio",afiliadoIn.getPensionadoWS().getMunicipioDIreccion()).getSingleResult();
			}
			// CONSULTAR DATOS DE UBICACION
			afiliadoIn.getPersona().getUbicacionDTO().setIdMunicipio(Short.parseShort(v[3].toString()));
			afiliadoIn.getPersona().getUbicacionDTO().setIdDepartamento(Short.parseShort(v[4].toString()));
			// realizar consulta para conseguir el dato
			afiliadoIn.getPersona().setIdPersona(Long.valueOf(v[0].toString()));
			// afiliadoIn.
			// realizar consulta para conseguir el dato
			afiliadoIn.setIdAfiliado(Long.valueOf(v[1].toString()));
			afiliadoIn.setIdRolAfiliado(Long.valueOf(v[2].toString()));			
            return afiliadoIn;
        }catch(Exception e){
            return afiliadoIn;
        }
    }
	

	public String construirDatoTemporalPensionado(AfiliadoInDTO afiliadoIn, Long idSolicitudGlobal, UserDTO userDto){

		afiliadoIn = construirInformacionContacto(afiliadoIn, idSolicitudGlobal);

		try {

			List<ItemChequeoDTO> listaChequeo = Arrays.asList(
			buildItemChequeo(90L, "Formulario solicitud afiliación o reintegro pensionado",
					EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO, TipoRequisitoEnum.ESTANDAR),
			buildItemChequeo(60L, "Copia del documento de identidad",
					EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO, TipoRequisitoEnum.ESTANDAR),
			buildItemChequeo(77L, "Copia último desprendible pago de mesada pensional",
					EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO, TipoRequisitoEnum.ESTANDAR)
			);

			ObjectMapper mapper = new ObjectMapper();

			// 1. Nodo raíz
			ObjectNode root = mapper.createObjectNode();

			// 2. Convertir afiliado a nodo y limpiar "persona"
			ObjectNode afiliadoNode = (ObjectNode) mapper.valueToTree(afiliadoIn);
			afiliadoNode.remove("persona");

			// 3. Construcción de "dto" (afiliado base)
			ObjectNode dto = mapper.createObjectNode();
			dto.put("tipoAfiliado", afiliadoIn.getTipoAfiliado().name());
			dto.put("canalRecepcion", afiliadoIn.getCanalRecepcion().name());
			dto.put("idAfiliado", afiliadoIn.getIdAfiliado());
			dto.put("idRolAfiliado", afiliadoIn.getIdRolAfiliado());
			dto.put("nombreUsuarioCaja", afiliadoIn.getPersona().getUbicacionDTO().getCorreoElectronico());
			dto.set("persona", mapper.valueToTree(afiliadoIn.getPersona()));
			dto.putAll(afiliadoNode);

			// 4. Construcción de "afiliadoInDTO"
			ObjectNode afiliadoInDTO = mapper.createObjectNode();
			afiliadoInDTO.put("tipoAfiliado", afiliadoIn.getTipoAfiliado().name());
			afiliadoInDTO.put("canalRecepcion", afiliadoIn.getCanalRecepcion().name());
			afiliadoInDTO.put("idAfiliado", afiliadoIn.getIdAfiliado());
			afiliadoInDTO.put("idRolAfiliado", afiliadoIn.getIdRolAfiliado());
			afiliadoInDTO.put("nombreUsuarioCaja", afiliadoIn.getPersona().getUbicacionDTO().getCorreoElectronico());
			afiliadoInDTO.set("persona", mapper.valueToTree(afiliadoIn.getPersona()));
			afiliadoInDTO.putAll(afiliadoNode);

			// 5. Construcción de "modeloInformacionPaso2"
			ObjectNode modeloInformacion = mapper.createObjectNode();
			modeloInformacion.set("persona", mapper.valueToTree(afiliadoIn.getPersona()));
			modeloInformacion.set("ubicacion", mapper.valueToTree(afiliadoIn.getPersona().getUbicacionDTO()));

			// Campos extra
			modeloInformacion.putAll(afiliadoNode);
			modeloInformacion.put("resideSectorRural", false);
			modeloInformacion.put("autorizacionUsoDatosPersonales", true);
			modeloInformacion.put("idRolAfiliado", afiliadoIn.getIdRolAfiliado());
			modeloInformacion.put("idPagadorPension", afiliadoIn.getIdPagadorPension());
			modeloInformacion.put("identificadorAnteEntidadPagadora", "");
			modeloInformacion.put("valorMesadaSalarioIngresos", afiliadoIn.getPensionadoWS().getValorMesadaPensional());

			// 6. Armar objeto raíz
			root.set("dto", dto);
			root.set("afiliadoInDTO", afiliadoInDTO);
			root.set("modeloInformacionPaso2", modeloInformacion);
			root.set("listaChequeo", mapper.valueToTree(listaChequeo));

			// 7. Convertir a JSON
			String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			logger.warn("JSON Pensionado: " + jsonResult);
			return jsonResult;
		} catch (Exception e) {
			logger.error("Error construyendo dato temporal pensionado", e);
			return null;
		}
	}
}
