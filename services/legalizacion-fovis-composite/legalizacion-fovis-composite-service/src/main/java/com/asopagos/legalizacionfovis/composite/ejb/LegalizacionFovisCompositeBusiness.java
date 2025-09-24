package com.asopagos.legalizacionfovis.composite.ejb;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.asignaciones.clients.ConsultarSedesCajaCompensacion;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.fovis.AnticipoLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.DocumentoSoporteOferenteDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProveedorDTO;
import com.asopagos.dto.fovis.DocumentoSoporteProyectoViviendaDTO;
import com.asopagos.dto.fovis.RegistroExistenciaHabitabilidadDTO;
import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.VisitaDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.empleadores.clients.CrearActualizarRepresentanteLegal;
import com.asopagos.empresas.clients.ConsultarEmpresa;
import com.asopagos.empresas.clients.ConsultarOferente;
import com.asopagos.empresas.clients.ConsultarOferentePorRazonSocial;
import com.asopagos.empresas.clients.ConsultarProveedor;
import com.asopagos.empresas.clients.ConsultarProveedorPorRazonSocial;
import com.asopagos.empresas.clients.CrearActualizarOferente;
import com.asopagos.empresas.clients.CrearActualizarProveedor;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAnalistaEstalamientoFOVISEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.ClasificacionLicenciaEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;
import com.asopagos.enumeraciones.fovis.TipoLicenciaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ActualizarEstadoHogar;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarLicencia;
import com.asopagos.fovis.clients.CrearActualizarLicenciaDetalle;
import com.asopagos.fovis.clients.CrearActualizarProyectoSolucionVivienda;
import com.asopagos.fovis.clients.ExistenEscalamientosSinResultado;
import com.asopagos.fovis.clients.GuardarSolicitudGlobal;
import com.asopagos.legalizacionfovis.clients.ActualizarEstadoSolicitudLegalizacionDesembolso;
import com.asopagos.legalizacionfovis.clients.ConsultarAnticiposDesembolsados;
import com.asopagos.legalizacionfovis.clients.ConsultarCondicionesVisita;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteOferentePorIdOferente;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteProveedorPorIdProveedor;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteProyectoPorIdProyecto;
import com.asopagos.legalizacionfovis.clients.ConsultarPostulacionesParaLegalizacionYDesembolso;
import com.asopagos.legalizacionfovis.clients.ConsultarSolicitudLegalizacionDesembolso;
import com.asopagos.legalizacionfovis.clients.ConsultarVisita;
import com.asopagos.legalizacionfovis.clients.CrearActualizarLegalizacionDesembolso;
import com.asopagos.legalizacionfovis.clients.CrearActualizarSolicitudLegalizacionDesembolso;
import com.asopagos.legalizacionfovis.clients.CrearCondicionesVisita;
import com.asopagos.legalizacionfovis.clients.CrearVisita;
import com.asopagos.legalizacionfovis.clients.RegistrarDocumentoSoporte;
import com.asopagos.legalizacionfovis.clients.RegistrarIntentoLegalizacionDesembolsoFOVIS;
import com.asopagos.legalizacionfovis.clients.RegistrarIntentoLegalizacionDesembolsoRequisito;
import com.asopagos.legalizacionfovis.clients.RegistrarRequisitosDocumentalesOferente;
import com.asopagos.legalizacionfovis.clients.RegistrarRequisitosDocumentalesProveedor;
import com.asopagos.legalizacionfovis.clients.RegistrarRequisitosDocumentalesProyectoVivienda;
import com.asopagos.legalizacionfovis.composite.dto.AnalisisSolicitudLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.composite.dto.AsignarSolicitudLegalizacionDTO;
import com.asopagos.legalizacionfovis.composite.dto.ResultadoAnalisisLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.composite.dto.VerificacionGestionPNCLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.composite.enums.TipoAsignacionLegalizacionFovisEnum;
import com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService;
import com.asopagos.legalizacionfovis.dto.SolicitudPostulacionLegalizacionDTO;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.BuscarPersonasSinDetalle;
import com.asopagos.personas.clients.CrearActualizarUbicacion;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.solicitudes.clients.RegistrarEscalamientoSolicitud;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
/**GLPI  26482*/
import com.asopagos.fovis.clients.ConsultarDatosGeneralesFovis;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.PlazoVencimientoEnum;
import com.asopagos.fovis.clients.CrearActualizarPostulacionProveedor;
/**fin GLPI  26482*/

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con Legalización FOVIS. <b>Historia de Usuario:</b> proceso 3.2.4
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class LegalizacionFovisCompositeBusiness implements LegalizacionFovisCompositeService {

	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(LegalizacionFovisCompositeBusiness.class);
	/** Constantes BPM */
	/** Constante para el parámetro id de solicitud. */
	private static final String ID_SOLICITUD = "idSolicitud";
	/**
	 * Constante que define el número de radicación de la legalización y
	 * desembolso.
	 */
	private static final String NUMERO_RADICACION = "numeroRadicado";
	/** Constante cuando la solicitud es escalada al analista técnico */
	private static final String ANALISTA_TECNICO = "analistaTecnico";
	/** Constante cuando la solicitud es escalada al analista Jurídico */
	private static final String ANALISTA_JURIDICO = "analistaJuridico";
	/** Constante cuando la solicitud es escalada al analista Hogar */
	private static final String ANALISTA_HOGAR = "analistaHogar";
	/** Constante cuando la solicitud es escalada al usuarioBack */
	private static final String USUARIO_BACK = "usuarioBack";
	/** Constante cuando la solicitud es escalada al usuarioBack */
	private static final String USUARIO_FRONT = "usuarioFront";
	/** Constante cuando la solicitud es escalada al inspectorFOVIS */
	private static final String INSPECTOR_FOVIS = "inspectorFOVIS";
	/** Constante cuando la solicitud es escalada al coordinadorFovis */
	private static final String COORDINADOR_FOVIS = "coordinadorFOVIS";
	/** Constante que define señal para documentos físicos */
	private static final String DOCUMENTOS_FISICOS = "documentosFisicos";
	/**
	 * Constante para identificar si se agrego el certificado de existencia y
	 * habitabilidad.
	 */
	private static final String CERTI_EXISTENCIA_HABITABILIDAD = "certiExistenciaHabitabilidad";
	/** Constante para identificar si es legalizacion exitosa. */
	private static final String LEGALIZACION_EXITOSA = "legalizacionExitosa";
	/** Constante para identificar si es legalizacion exitosa. */
	private static final String SENAL_EXISTENCIA_HABITABILIDAD = "registroExistenciaHabitabilidad";
	/** Constante para identificar si se autorizó el desembolso */
	private static final String AUTORIZAR_DESEMBOLSO = "autorizarDesembolso";
	/** Constante para identificar si se rechazo el desembolso */
	private static final String RECHAZO_REINTENTO = "rechazoReintento";

	/**
	 * Constante para identificar la tarea de registrar resultado de ejecucion
	 * desembolso FOVIS
	 */
	private static final String DESEMBOLSO_CONFIRMADO = "desembolsoConfirmado";
	/**
	 *  Constante cuando la solicitud es escalada al usuario pagador FOVIS
	 */
	private static final String USUARIO_PAGADOR_FOVIS = "usuarioPagador";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#registrarEditarOferente(com.asopagos.
	 * dto.modelo. OferenteModeloDTO)
	 */
	@Override
	public OferenteModeloDTO registrarEditarOferente(OferenteModeloDTO oferenteModeloDTO) {
		logger.debug("Inicia el servicio registrarEditarOferente");
		OferenteModeloDTO oferenteDTO = null;
		// Se valida que el oferente no este creado
		if (oferenteModeloDTO.getPersona() != null
				&& oferenteModeloDTO.getPersona().getNumeroIdentificacion() != null) {
			oferenteDTO = consultarOferente(oferenteModeloDTO.getPersona().getTipoIdentificacion(),
					oferenteModeloDTO.getPersona().getNumeroIdentificacion());
		} else {
			oferenteDTO = consultarOferente(oferenteModeloDTO.getEmpresa().getTipoIdentificacion(),
					oferenteModeloDTO.getEmpresa().getNumeroIdentificacion());
			if (oferenteDTO != null && oferenteDTO.getPersona() != null) {
				oferenteModeloDTO.setPersona(oferenteDTO.getPersona());
			}
		}
                
                
		// Se valida si existe la empresa asociada al oferente si no existe
		// se crea
		if (oferenteModeloDTO.getEmpresa() != null
				&& oferenteModeloDTO.getEmpresa().getNumeroIdentificacion() != null) {
			// Consulta si existe la empresa.
			ConsultarEmpresa consultarEmpresa = new ConsultarEmpresa(
					oferenteModeloDTO.getEmpresa().getNumeroIdentificacion(),
					oferenteModeloDTO.getEmpresa().getTipoIdentificacion(), null);
			consultarEmpresa.execute();
			List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa.getResult();

			// Si no existe empresa la crea
			if (listaEmpresa == null || listaEmpresa.isEmpty()) {
				// Crea la empresa
				Long idEmpresa = crearEmpresa(oferenteModeloDTO.getEmpresa());
				if (idEmpresa != null) {
					oferenteModeloDTO.getEmpresa().setIdEmpresa(idEmpresa);
				}
				/* Consultar Empresa Registrada. */
				consultarEmpresa = new ConsultarEmpresa(oferenteModeloDTO.getEmpresa().getNumeroIdentificacion(),
						oferenteModeloDTO.getEmpresa().getTipoIdentificacion(), null);
				consultarEmpresa.execute();
				listaEmpresa = consultarEmpresa.getResult();
				PersonaModeloDTO personaDTO = new PersonaModeloDTO();
				personaDTO.setIdPersona(listaEmpresa.get(0).getIdPersona());
				oferenteModeloDTO.setPersona(personaDTO);
			} else if (!listaEmpresa.isEmpty()) {
				EmpresaModeloDTO empresaDTO = listaEmpresa.get(0);
				/* Se actualiza la ubicacion de la empresa */
				UbicacionModeloDTO ubicacionEmpresa = oferenteModeloDTO.getEmpresa().getUbicacionModeloDTO();
				ubicacionEmpresa = crearActualizarUbicacion(ubicacionEmpresa);
				empresaDTO.setUbicacionModeloDTO(ubicacionEmpresa);
				oferenteModeloDTO.setEmpresa(empresaDTO);
				PersonaModeloDTO personaDTO = new PersonaModeloDTO();
				personaDTO.setIdPersona(empresaDTO.getIdPersona());
				oferenteModeloDTO.setPersona(personaDTO);
			}

			// Se crea actualiza la información del representante Legal.
			CrearActualizarRepresentanteLegal crearActualizarRepresentante = new CrearActualizarRepresentanteLegal(
					oferenteModeloDTO.getEmpresa().getIdEmpresa(), Boolean.TRUE,
					oferenteModeloDTO.getRepresentanteLegal());
			crearActualizarRepresentante.execute();
			Long idRepresentante = crearActualizarRepresentante.getResult();
			oferenteModeloDTO.getRepresentanteLegal().setIdPersona(idRepresentante);
			// Se valida si existe la persona asociada al oferente si no
			// existe se crea
		} else if (oferenteModeloDTO.getPersona() != null
				&& oferenteModeloDTO.getPersona().getNumeroIdentificacion() != null) {
			List<PersonaDTO> personas = buscarPersonasSinDetalle(
					oferenteModeloDTO.getPersona().getTipoIdentificacion(),
					oferenteModeloDTO.getPersona().getNumeroIdentificacion(), null, null, null, null, null);
			if (personas == null || personas.isEmpty()) {
				Long idPersona = crearPersona(oferenteModeloDTO.getPersona());
				oferenteModeloDTO.getPersona().setIdPersona(idPersona);
			} else if (!personas.isEmpty()) {
				PersonaModeloDTO personaAsociar = new PersonaModeloDTO();
				personaAsociar.convertFromPersonaDTO(personas.get(0));
				/* Se actualiza la ubicacion de la persona */
				UbicacionModeloDTO ubicacionPersona = oferenteModeloDTO.getPersona().getUbicacionModeloDTO();
				ubicacionPersona = crearActualizarUbicacion(ubicacionPersona);
				personaAsociar.setUbicacionModeloDTO(ubicacionPersona);
				oferenteModeloDTO.setPersona(personaAsociar);
			}
		}
		// Se registra el actualiza o se crea el oferente
		oferenteModeloDTO.setEstado(EstadoOferenteEnum.ACTIVO);
		oferenteModeloDTO = crearActualizarOferente(oferenteModeloDTO);
		// Se registran los documentos soporte que se asociaran al oferente
		DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO = new DocumentoSoporteOferenteDTO();
		documentoSoporteOferenteDTO.setOferenteDTO(oferenteModeloDTO);

		// Se consultan los documentos asociaddos al oferente
		List<DocumentoSoporteModeloDTO> listaDocumentosOferente = consultarDocumentosSoportePorIdOferente(
				oferenteModeloDTO.getIdOferente());
		if (!oferenteModeloDTO.getListaDocumentosSoporte().isEmpty()) {
			for (DocumentoSoporteModeloDTO documentoSoporte : oferenteModeloDTO.getListaDocumentosSoporte()) {
				documentoSoporte = crearActualizarDocumentoSoporte(documentoSoporte);
				if (listaDocumentosOferente.isEmpty() || (!listaDocumentosOferente.isEmpty()
						&& !listaDocumentosOferente.contains(documentoSoporte))) {
					// se crea el registro documento soporte oferente
					documentoSoporteOferenteDTO.setDocumentoSoporteDTO(documentoSoporte);
					crearActualizarDocumentoSoporteOferente(documentoSoporteOferenteDTO);
				}
			}
		}
		logger.debug("Finaliza el servicio registrarEditarOferente");
		return oferenteModeloDTO;
	}
        
        
        /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#registrarEditarProveedor(com.asopagos.
	 * dto.modelo. OferenteModeloDTO)
	 */
	@Override
	public ProveedorModeloDTO registrarEditarProveedor(ProveedorModeloDTO proveedorModeloDTO) {
		logger.debug("Inicia el servicio registrarEditarProveedor");

		// Se valida si existe la empresa asociada al proveedor si no existe
		// se crea
		if (proveedorModeloDTO.getEmpresa() != null
				&& proveedorModeloDTO.getEmpresa().getNumeroIdentificacion() != null) {
			// Consulta si existe la empresa.
			ConsultarEmpresa consultarEmpresa = new ConsultarEmpresa(
					proveedorModeloDTO.getEmpresa().getNumeroIdentificacion(),
					proveedorModeloDTO.getEmpresa().getTipoIdentificacion(), null);
			consultarEmpresa.execute();
			List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa.getResult();

			// Si no existe empresa la crea
			if (listaEmpresa == null || listaEmpresa.isEmpty()) {
				// Crea la empresa
				Long idEmpresa = crearEmpresa(proveedorModeloDTO.getEmpresa());
				if (idEmpresa != null) {
					proveedorModeloDTO.getEmpresa().setIdEmpresa(idEmpresa);
				}
				/* Consultar Empresa Registrada. */
				consultarEmpresa = new ConsultarEmpresa(proveedorModeloDTO.getEmpresa().getNumeroIdentificacion(),
						proveedorModeloDTO.getEmpresa().getTipoIdentificacion(), null);
				consultarEmpresa.execute();
				listaEmpresa = consultarEmpresa.getResult();
				PersonaModeloDTO personaDTO = new PersonaModeloDTO();
				personaDTO.setIdPersona(listaEmpresa.get(0).getIdPersona());
				proveedorModeloDTO.setPersona(personaDTO);
			} else if (!listaEmpresa.isEmpty()) {
				EmpresaModeloDTO empresaDTO = listaEmpresa.get(0);
				/* Se actualiza la ubicacion de la empresa */
				UbicacionModeloDTO ubicacionEmpresa = proveedorModeloDTO.getEmpresa().getUbicacionModeloDTO();
				ubicacionEmpresa = crearActualizarUbicacion(ubicacionEmpresa);
				empresaDTO.setUbicacionModeloDTO(ubicacionEmpresa);
				proveedorModeloDTO.setEmpresa(empresaDTO);
				PersonaModeloDTO personaDTO = new PersonaModeloDTO();
				personaDTO.setIdPersona(empresaDTO.getIdPersona());
				proveedorModeloDTO.setPersona(personaDTO);
			}

			// Se crea actualiza la información del representante Legal.
			CrearActualizarRepresentanteLegal crearActualizarRepresentante = new CrearActualizarRepresentanteLegal(
					proveedorModeloDTO.getEmpresa().getIdEmpresa(), Boolean.TRUE,
					proveedorModeloDTO.getRepresentanteLegal());
			crearActualizarRepresentante.execute();
			Long idRepresentante = crearActualizarRepresentante.getResult();
			proveedorModeloDTO.getRepresentanteLegal().setIdPersona(idRepresentante);
			// Se valida si existe la persona asociada al proveedor si no
			// existe se crea
		} else if (proveedorModeloDTO.getPersona() != null
				&& proveedorModeloDTO.getPersona().getNumeroIdentificacion() != null) {
			List<PersonaDTO> personas = buscarPersonasSinDetalle(
					proveedorModeloDTO.getPersona().getTipoIdentificacion(),
					proveedorModeloDTO.getPersona().getNumeroIdentificacion(), null, null, null, null, null);
			if (personas == null || personas.isEmpty()) {
				Long idPersona = crearPersona(proveedorModeloDTO.getPersona());
				proveedorModeloDTO.getPersona().setIdPersona(idPersona);
			} else if (!personas.isEmpty()) {
				PersonaModeloDTO personaAsociar = new PersonaModeloDTO();
				personaAsociar.convertFromPersonaDTO(personas.get(0));
				/* Se actualiza la ubicacion de la persona */
				UbicacionModeloDTO ubicacionPersona = proveedorModeloDTO.getPersona().getUbicacionModeloDTO();
				ubicacionPersona = crearActualizarUbicacion(ubicacionPersona);
				personaAsociar.setUbicacionModeloDTO(ubicacionPersona);
				proveedorModeloDTO.setPersona(personaAsociar);
			}
		}
		// Se registra el actualiza o se crea el proveedor
		proveedorModeloDTO.setEstado(EstadoOferenteEnum.ACTIVO);
		proveedorModeloDTO = crearActualizarProveedor(proveedorModeloDTO);
                
		// Se registran los documentos soporte que se asociaran al proveedor
		DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO = new DocumentoSoporteProveedorDTO();
		documentoSoporteProveedorDTO.setProveedorDTO(proveedorModeloDTO);

		// Se consultan los documentos asociaddos al oferente
		List<DocumentoSoporteModeloDTO> listaDocumentosProveedor = consultarDocumentosSoporteProveedorPorIdProveedor(
				proveedorModeloDTO.getIdOferente());
		if (!proveedorModeloDTO.getListaDocumentosSoporte().isEmpty()) {
			for (DocumentoSoporteModeloDTO documentoSoporte : proveedorModeloDTO.getListaDocumentosSoporte()) {
				documentoSoporte = crearActualizarDocumentoSoporte(documentoSoporte);
				if (listaDocumentosProveedor.isEmpty() || (!listaDocumentosProveedor.isEmpty()
						&& !listaDocumentosProveedor.contains(documentoSoporte))) {
					// se crea el registro documento soporte oferente
					documentoSoporteProveedorDTO.setDocumentoSoporteDTO(documentoSoporte);
                                        
                                        //realmente no esta insertanto . la tabla presenta 0 registros (revisar)
                                        crearActualizarDocumentoSoporteProveedor(documentoSoporteProveedorDTO);
				}
			}
		}
		logger.debug("Finaliza el servicio registrarEditarProveedor");
		return proveedorModeloDTO;
	}

	@Override
	public List<SolicitudPostulacionLegalizacionDTO> consultarPostulacionesParaLegalizacionDesembolso(String numeroRadicadoSolicitud,
																									  TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.info("Inicia servicio consultarPostulacionesParaLegalizacionDesembolso(String, TipoIdentificacionEnum, String)");

		//try {
			ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
			consultarListafestivos.execute();
			List<ElementoListaDTO> festivos = consultarListafestivos.getResult();
			ConsultarPostulacionesParaLegalizacionYDesembolso consultarPostulacionesParaLegalizacionYDesembolso = new ConsultarPostulacionesParaLegalizacionYDesembolso(
					numeroRadicadoSolicitud, numeroIdentificacion, tipoIdentificacion);
			consultarPostulacionesParaLegalizacionYDesembolso.execute();
			List<SolicitudPostulacionLegalizacionDTO> solicitudes = consultarPostulacionesParaLegalizacionYDesembolso.getResult();

			BigDecimal parametroValorAdicional = null;
			PlazoVencimientoEnum plazoAdicional = PlazoVencimientoEnum.DIAS_HABILES;
			BigDecimal valorSegundaProrroga = null;
			BigDecimal valorPrimeraProrroga = null;
			BigDecimal valorSinProrroga = null;

			PlazoVencimientoEnum plazoSegundaProrroga = null;
			PlazoVencimientoEnum plazoPrimeraProrroga  = null;
			PlazoVencimientoEnum plazoSinProrroga = null;

			Date fechaPrimerVigencia = null;
			Date fechaSegundaVigencia = null;
			Date fechaFinPlazoLegalizacion = null;
			Date fechaFinPlazoUltimoLegalizacion = null;

			for (SolicitudPostulacionLegalizacionDTO solicitudPostulacionLegalizacionDTO : solicitudes) {

				if (solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia() != null) {
					ConsultarDatosGeneralesFovis consultarDatosGeneralesFovis = new ConsultarDatosGeneralesFovis();
					consultarDatosGeneralesFovis.execute();
					List<ParametrizacionFOVISModeloDTO> listParametros = consultarDatosGeneralesFovis.getResult();
					// Se obtiene el parámetro de LIMITE_CUANTIA_SUBSIDIO
					for (ParametrizacionFOVISModeloDTO parametrizacionFOVISModeloDTO : listParametros) {
						if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SEGUNDA_PRORROGA.equals(parametrizacionFOVISModeloDTO.getParametro())) {
							Calendar calendar = Calendar.getInstance();
							parametroValorAdicional = parametrizacionFOVISModeloDTO.getValorAdicional();
							plazoAdicional = parametrizacionFOVISModeloDTO.getPlazoAdicional();
							valorSegundaProrroga = parametrizacionFOVISModeloDTO.getValorNumerico();
							plazoSegundaProrroga = parametrizacionFOVISModeloDTO.getPlazoVencimiento();
							if (plazoSegundaProrroga.equals(PlazoVencimientoEnum.ANIOS)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.YEAR, valorSegundaProrroga.intValue());
								fechaFinPlazoLegalizacion = calendar.getTime();
							} else if (plazoSegundaProrroga.equals(PlazoVencimientoEnum.MESES)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.MONTH, valorSegundaProrroga.intValue());
								fechaFinPlazoLegalizacion = calendar.getTime();
							} else {
								fechaFinPlazoLegalizacion = CalendarUtils.calcularFecha(
										new Date(solicitudPostulacionLegalizacionDTO.getFechaFinVigencia()), valorSegundaProrroga.intValue(),
										plazoSegundaProrroga != PlazoVencimientoEnum.DIAS_HABILES ? CalendarUtils.TipoDia.CALENDARIO : CalendarUtils.TipoDia.HABIL, festivos);
							}

						} else if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_PRIMERA_PRORROGA.equals(parametrizacionFOVISModeloDTO.getParametro())) {
							Calendar calendar = Calendar.getInstance();
							valorPrimeraProrroga = parametrizacionFOVISModeloDTO.getValorNumerico();
							plazoPrimeraProrroga = parametrizacionFOVISModeloDTO.getPlazoVencimiento();
							if (plazoPrimeraProrroga.equals(PlazoVencimientoEnum.ANIOS)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.YEAR, valorPrimeraProrroga.intValue());
								fechaSegundaVigencia = calendar.getTime();
							} else if (plazoPrimeraProrroga.equals(PlazoVencimientoEnum.MESES)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.MONTH, valorPrimeraProrroga.intValue());
								fechaSegundaVigencia = calendar.getTime();
							} else {
								fechaSegundaVigencia = CalendarUtils.calcularFecha(
										new Date(solicitudPostulacionLegalizacionDTO.getFechaFinVigencia()), valorPrimeraProrroga.intValue(),
										plazoPrimeraProrroga != PlazoVencimientoEnum.DIAS_HABILES ? CalendarUtils.TipoDia.CALENDARIO : CalendarUtils.TipoDia.HABIL, festivos);

							}

						} else if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA.equals(parametrizacionFOVISModeloDTO.getParametro())) {
							valorSinProrroga = parametrizacionFOVISModeloDTO.getValorNumerico();
							plazoSinProrroga = parametrizacionFOVISModeloDTO.getPlazoVencimiento();
							Calendar calendar = Calendar.getInstance();
							if (plazoSinProrroga.equals(PlazoVencimientoEnum.ANIOS)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.YEAR, valorSinProrroga.intValue());
								fechaPrimerVigencia = calendar.getTime();
							} else if (plazoSinProrroga.equals(PlazoVencimientoEnum.MESES)) {
								calendar.setTimeInMillis(solicitudPostulacionLegalizacionDTO.getFechaInicioVigencia());
								calendar.add(Calendar.MONTH, valorSinProrroga.intValue());
								fechaPrimerVigencia = calendar.getTime();
							} else {
								fechaPrimerVigencia = CalendarUtils.calcularFecha(
										new Date(solicitudPostulacionLegalizacionDTO.getFechaFinVigencia()), valorSinProrroga.intValue(),
										plazoSinProrroga != PlazoVencimientoEnum.DIAS_HABILES ? CalendarUtils.TipoDia.CALENDARIO : CalendarUtils.TipoDia.HABIL, festivos);

							}
						}
					}

					//se obtiene la fecha ultima legalizacion sumando el plazo adicional
					Calendar calendar = Calendar.getInstance();
					if (plazoAdicional.equals(PlazoVencimientoEnum.ANIOS)) {
						calendar.setTimeInMillis(fechaFinPlazoLegalizacion.getTime());
						calendar.add(Calendar.YEAR, parametroValorAdicional.intValue());
						fechaFinPlazoUltimoLegalizacion = calendar.getTime();
					} else if (plazoAdicional.equals(PlazoVencimientoEnum.MESES)) {
						calendar.setTimeInMillis(fechaFinPlazoLegalizacion.getTime());
						calendar.add(Calendar.MONTH, parametroValorAdicional.intValue());
						fechaFinPlazoUltimoLegalizacion = calendar.getTime();
					} else {
						fechaFinPlazoUltimoLegalizacion = CalendarUtils.calcularFecha(
								new Date(fechaFinPlazoLegalizacion.getTime()), parametroValorAdicional.intValue(),
								plazoAdicional != PlazoVencimientoEnum.DIAS_HABILES ? CalendarUtils.TipoDia.CALENDARIO : CalendarUtils.TipoDia.HABIL, festivos);
					}
				}
				logger.info("fechaFinPlazoUltimoLegalizacion " +fechaFinPlazoUltimoLegalizacion);
				logger.info("fechaSegundaVigencia " +fechaSegundaVigencia);
				logger.info("fechaPrimerVigencia " +fechaPrimerVigencia);



				solicitudPostulacionLegalizacionDTO.setFechaFinPlazoLegalizacion(fechaFinPlazoUltimoLegalizacion != null
						? fechaFinPlazoUltimoLegalizacion.getTime() : null);
				solicitudPostulacionLegalizacionDTO.setFechaFinSegundaVigencia(fechaSegundaVigencia != null
						? fechaSegundaVigencia.getTime() : null);
				solicitudPostulacionLegalizacionDTO.setFechaFinPrimeraVigencia(fechaPrimerVigencia != null
						? fechaPrimerVigencia.getTime() : null);

			}
			logger.info("Finaliza servicio consultarPostulacionesParaLegalizacionDesembolso(String, TipoIdentificacionEnum, String)");
			return solicitudes;
		/*} catch (Exception e) {
			logger.error("Error inesperado en consultarPostulacionesParaLegalizacionDesembolso(String, TipoIdentificacionEnum, String)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}*/
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#guardarLegalizacionDesembolsoTemporal(
	 *      com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO guardarLegalizacionDesembolsoTemporal(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.info(
					"Inicio de servicio guardarLegalizacionDesembolsoTemporal(SolicitudLegalizacionDesembolsoFOVISDTO, UserDTO)");

			registrarSolicitudEIniciarProcesoLegalizacion(solicitudLegalizacionDesembolso, userDTO);
			guardarDatosTemporal(solicitudLegalizacionDesembolso);

			logger.info(
					"Fin de servicio guardarLegalizacionDesembolsoTemporal(SolicitudLegalizacionDesembolsoFOVISDTO, UserDTO)");
			return solicitudLegalizacionDesembolso;
		} catch (JsonProcessingException e) {
			logger.error("Ocurrio un error en el servicio guardarLegalizacionDesembolsoTemporal("
					+ "SolicitudLegalizacionDesembolsoFOVISDTO, UserDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#
	 *      consultarLegalizacionDesembolsoTemporal(java.lang.Long)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO consultarLegalizacionDesembolsoTemporal(Long idSolicitudGlobal) {
		try {
			logger.info("Inicio de servicio consultarLegalizacionDesembolsoTemporal");
			String jsonPayload = consultarDatosTemporales(idSolicitudGlobal);
			ObjectMapper mapper = new ObjectMapper();
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoFOVISDTO = mapper.readValue(jsonPayload,
					SolicitudLegalizacionDesembolsoDTO.class);
			logger.info("Fin de servicio consultarLegalizacionDesembolsoTemporal");
			return solicitudLegalizacionDesembolsoFOVISDTO;
		} catch (Exception e) {
			logger.error("Ocurrio un error en el servicio consultarLegalizacionDesembolsoTemporal", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#registrarIntentoLegalizacionDesembolso(
	 *      com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Long registrarIntentoLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {
		logger.info(
				"Inicio de servicio registrarIntentoLegalizacionDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
		try {
			Map<String, Object> parametros = new HashMap<>();
			if (solicitudLegalizacionDesembolsoDTO.getIntentoLegalizacion() != null
					&& solicitudLegalizacionDesembolsoDTO.getIntentoLegalizacion()) {
				parametros.put("intentoLegalizacion", solicitudLegalizacionDesembolsoDTO.getIntentoLegalizacion());
			}

			ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
				solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
			consultarSolicitudGlobal.execute();
			SolicitudModeloDTO resultadoSolicitud = consultarSolicitudGlobal.getResult();

			if (resultadoSolicitud != null && resultadoSolicitud.getNumeroRadicacion() != null) {
				parametros.put("numeroRadicado", resultadoSolicitud.getNumeroRadicacion());
			}

			registrarSolicitudEIniciarProcesoLegalizacion(solicitudLegalizacionDesembolsoDTO, userDTO);

			SolicitudModeloDTO solicitudDTO = transformarSolicitudLegalizacionDesembolso(
					solicitudLegalizacionDesembolsoDTO, userDTO);
			guardarSolicitudGlobal(solicitudDTO);

			// Se rechaza solicitud de postulación
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);
			// Se cierra la solicitud de postulación
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudDTO.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);

			solicitudLegalizacionDesembolsoDTO
					.setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
			// Crea el intento de postulación
			IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO = crearIntentoLegalizacionDesembolso(
					solicitudLegalizacionDesembolsoDTO, userDTO);

			if (CausaIntentoFallidoLegalizacionDesembolsoEnum.VALIDACION_REQUISITOS_DOCUMENTALES
					.equals(intentoLegalizacionDesembolsoModeloDTO.getCausaIntentoFallido())) {

				List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> listaRequisitosSinCumplir = new ArrayList<>();
				if (solicitudLegalizacionDesembolsoDTO.getListaChequeo() != null
						&& solicitudLegalizacionDesembolsoDTO.getListaChequeo().getListaChequeo() != null) {
					for (ItemChequeoDTO itemChequeoDTO : solicitudLegalizacionDesembolsoDTO.getListaChequeo()
							.getListaChequeo()) {
						if ((itemChequeoDTO.getCumpleRequisito() != null && !itemChequeoDTO.getCumpleRequisito())
								|| (itemChequeoDTO.getCumpleRequisitoBack() != null
										&& !itemChequeoDTO.getCumpleRequisitoBack())) {
							listaRequisitosSinCumplir.add(new IntentoLegalizacionDesembolsoRequisitoModeloDTO(
									intentoLegalizacionDesembolsoModeloDTO.getIdIntentoLegalizacionDesembolso(),
									itemChequeoDTO.getIdRequisito()));
						}
					}
				}
				RegistrarIntentoLegalizacionDesembolsoRequisito registrarIntentoLegalizacionDesembolsoRequisito = new RegistrarIntentoLegalizacionDesembolsoRequisito(
						listaRequisitosSinCumplir);
				registrarIntentoLegalizacionDesembolsoRequisito.execute();
			}
			guardarDatosTemporal(solicitudLegalizacionDesembolsoDTO);
			terminarTarea(solicitudLegalizacionDesembolsoDTO.getIdTarea(), parametros,
					solicitudLegalizacionDesembolsoDTO.getIdInstanciaProceso());
			logger.info(
					"Fin de servicio registrarIntentoLegalizacionDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
			return intentoLegalizacionDesembolsoModeloDTO.getIdIntentoLegalizacionDesembolso();
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio registrarIntentoLegalizacionDesembolso"
					+ "(SolicitudLegalizacionDesembolsoDTO, UserDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Método que realiza el proceso de racicación de una solicitud de
	 * legalización y desembolso
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            Datos de la solicitud para registrar en el intento.
	 * @param userDTO
	 *            Usuario del contexto de seguridad.
	 * @return los datos de la solicitud actualizados.
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO radicarLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, Boolean terminarTarea,
			UserDTO userDTO) {
		logger.info(
				"Inicia el servicio radicarLegalizacionDesembolso(SolicitudLegalizacionDesembolsoDTO, Boolean, UserDTO)");
		try {
			registrarSolicitudEIniciarProcesoLegalizacion(solicitudLegalizacionDesembolsoDTO, userDTO);

			/* Almacena los datos de la legalización y desembolso */
			solicitudLegalizacionDesembolsoDTO = this
					.guardarDatosInicialesLegalizacionDesembolso(solicitudLegalizacionDesembolsoDTO, userDTO);

			ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
					solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
			consultarSolicitudGlobal.execute();
			SolicitudModeloDTO solicitud = consultarSolicitudGlobal.getResult();

			/* Se cambia el estado de la documentación. */
			solicitud.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);

			guardarSolicitudGlobal(solicitud);

			/* Se cambia el estado a desembolso RADICADO. */
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitud.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RADICADO);
			solicitudLegalizacionDesembolsoDTO
					.setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RADICADO);

			guardarDatosTemporal(solicitudLegalizacionDesembolsoDTO);

			if (terminarTarea != null && terminarTarea) {
				Map<String, Object> parametrosBPM = new HashMap<>();
				if (solicitudLegalizacionDesembolsoDTO.getLegalizacionExitosa() != null
						&& solicitudLegalizacionDesembolsoDTO.getLegalizacionExitosa()) {
					parametrosBPM.put(LEGALIZACION_EXITOSA,
							solicitudLegalizacionDesembolsoDTO.getLegalizacionExitosa());
				}
				parametrosBPM.put(CERTI_EXISTENCIA_HABITABILIDAD,
						solicitudLegalizacionDesembolsoDTO.getIncluyeCertificadoExistenciaHabitabilidad());
				parametrosBPM.put(NUMERO_RADICACION, solicitudLegalizacionDesembolsoDTO.getNumeroRadicacion());
				terminarTarea(solicitudLegalizacionDesembolsoDTO.getIdTarea(), parametrosBPM,
						solicitudLegalizacionDesembolsoDTO.getIdInstanciaProceso());
			}

			logger.info("Finaliza el servicio radicarLegalizacionDesembolso");
			return solicitudLegalizacionDesembolsoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio radicarLegalizacionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#registrarEditarProyectoSolucionVivienda
	 * (com. asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO)
	 */
	@Override
	public ProyectoSolucionViviendaModeloDTO registrarEditarProyectoSolucionVivienda(
			ProyectoSolucionViviendaModeloDTO proyectoDTO) {
		try {
			logger.info("Inicia el servicio registrarEditarProyectoSolucionVivienda");
			// Se registra el actualiza o se crea el proyecto de vivienda
			proyectoDTO = crearActualizarProyectoSolucionVivienda(proyectoDTO);

			// Se crea la licencia o actualiza
			List<LicenciaModeloDTO> licenciasProyecto = new ArrayList<LicenciaModeloDTO>();
			if (proyectoDTO.getLicencias() != null && !proyectoDTO.getLicencias().isEmpty()) {
				for (LicenciaModeloDTO licenciaDTO : proyectoDTO.getLicencias()) {
					licenciaDTO.setIdProyecto(proyectoDTO.getIdProyectoVivienda());
					licenciaDTO = crearActualizarLicencia(licenciaDTO);
					// Se crean o actualizan los detalles de la licencia
					crearDetalleLicencia(licenciaDTO);
					licenciasProyecto.add(licenciaDTO);
				}
				proyectoDTO.setLicencias(licenciasProyecto);
			}

			// Se registran los documentos soporte que se asociaran al proyecto
			// de vivienda
			DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO = new DocumentoSoporteProyectoViviendaDTO();
			documentoSoporteProyectoViviendaDTO.setProyectoViviendaDTO(proyectoDTO);

			// Se consultan los documentos asociados al proyecto de vivienda
			List<DocumentoSoporteModeloDTO> listaDocumentosProyecto = consultarDocumentosSoportePorIdProyecto(
					proyectoDTO.getIdProyectoVivienda());
			if (!proyectoDTO.getListaDocumentosSoporte().isEmpty()) {
				for (DocumentoSoporteModeloDTO documentoSoporte : proyectoDTO.getListaDocumentosSoporte()) {
					// Se actualiza o crea el documento soporte
					documentoSoporte = crearActualizarDocumentoSoporte(documentoSoporte);
					if (listaDocumentosProyecto.isEmpty() || (!listaDocumentosProyecto.isEmpty()
							&& !listaDocumentosProyecto.contains(documentoSoporte))) {
						// se crea el registro documento soporte del proyecto
						documentoSoporteProyectoViviendaDTO.setDocumentoSoporteDTO(documentoSoporte);
						registrarRequisitosDocumentalesProyectoVivienda(documentoSoporteProyectoViviendaDTO);
						listaDocumentosProyecto.add(documentoSoporte);
					}
				}
			}
			
			//Se setea la lista actualizada de los documentos de soporte en caso de existir
			if (listaDocumentosProyecto != null && !listaDocumentosProyecto.isEmpty()) {
				proyectoDTO.setListaDocumentosSoporte(listaDocumentosProyecto);
			}

			logger.info("Finaliza el servicio registrarEditarProyectoSolucionVivienda");
			return proyectoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio registrarEditarProyectoSolucionVivienda", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Metodo encargado de crear o actualizar los detalles asociados a la
	 * licencia
	 * 
	 * @param licenciaDTO
	 *            licencia a que se le procesaran los detalles
	 */
	private void crearDetalleLicencia(LicenciaModeloDTO licenciaDTO) {
		try {
			logger.info("Inicia el metodo crearDetalleLicencia");
			Map<ClasificacionLicenciaEnum, LicenciaDetalleModeloDTO> detalleUrbanismoContruccion = new HashMap<ClasificacionLicenciaEnum, LicenciaDetalleModeloDTO>();

			// Se idenficia si ya existe un detalle de licencia
			if (!licenciaDTO.getLicenciaDetalle().isEmpty()) {
				for (LicenciaDetalleModeloDTO detalle : licenciaDTO.getLicenciaDetalle()) {
					if (ClasificacionLicenciaEnum.CONSTRUCCION.equals(detalle.getClasificacionLicencia())) {
						detalleUrbanismoContruccion.put(ClasificacionLicenciaEnum.CONSTRUCCION, detalle);
					} else if (ClasificacionLicenciaEnum.URBANISMO.equals(detalle.getClasificacionLicencia())) {
						detalleUrbanismoContruccion.put(ClasificacionLicenciaEnum.URBANISMO, detalle);
					}
				}
			}

			// Se crean los detalles de la licencia construccion y urbanismo en
			// caso de ser la primera vez que se crea el registro
			LicenciaDetalleModeloDTO licencia = new LicenciaDetalleModeloDTO();
			licencia.setIdLicencia(licenciaDTO.getIdLicencia());
			licencia.setEstadoLicencia(Boolean.TRUE);
			licencia.setFechaInicio(licenciaDTO.getFechaInicioVigenciaLicencia());
			licencia.setFechaFin(licenciaDTO.getFechaFinVigenciaLicencia());
			licencia.setNumeroResolucion(licenciaDTO.getNumeroResolucion());
			if (!detalleUrbanismoContruccion.containsKey(ClasificacionLicenciaEnum.CONSTRUCCION)
					&& TipoLicenciaEnum.LICENCIA_CONSTRUCCION.equals(licenciaDTO.getTipoLincencia())) {

				licencia.setClasificacionLicencia(ClasificacionLicenciaEnum.CONSTRUCCION);
				licenciaDTO.getLicenciaDetalle().add(licencia);
			} else if (!detalleUrbanismoContruccion.containsKey(ClasificacionLicenciaEnum.URBANISMO)
					&& TipoLicenciaEnum.LICENCIA_URBANISMO.equals(licenciaDTO.getTipoLincencia())) {

				licencia.setClasificacionLicencia(ClasificacionLicenciaEnum.URBANISMO);
				licenciaDTO.getLicenciaDetalle().add(licencia);
			}
			
			List <LicenciaDetalleModeloDTO> listDetalleMerged = new ArrayList<>();
			if (!licenciaDTO.getLicenciaDetalle().isEmpty()) {
				for (LicenciaDetalleModeloDTO detalle : licenciaDTO.getLicenciaDetalle()) {
					detalle.setIdLicencia(licenciaDTO.getIdLicencia());
					// Se crea o actualizan los detalles de la licencia
					detalle = crearActualizarLicenciaDetalle(detalle);
					listDetalleMerged.add(detalle);
				}
				licenciaDTO.setLicenciaDetalle(listDetalleMerged);
			}
			logger.info("Finaliza el metodo crearDetalleLicencia");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el metodo crearDetalleLicencia", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#escalarSolicitudLegalizacionYDesembolso(
	 *      com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO escalarSolicitudLegalizacionYDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {
		logger.info(
				"Inicia servicio escalarSolicitudLegalizacionYDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
		try {
			Map<String, Object> parametros = new HashMap<>();
			if (solicitudLegalizacionDesembolsoDTO.getEscalada() != null
					&& solicitudLegalizacionDesembolsoDTO.getEscalada()) {
				parametros.put("escalada", solicitudLegalizacionDesembolsoDTO.getEscalada());
			}

			radicarLegalizacionDesembolso(solicitudLegalizacionDesembolsoDTO, false, userDTO);

			parametros.put("certiExistenciaHabitabilidad",
					solicitudLegalizacionDesembolsoDTO.getIncluyeCertificadoExistenciaHabitabilidad());
			parametros.put("numeroRadicado", solicitudLegalizacionDesembolsoDTO.getNumeroRadicacion());

			Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = registrarEscalamientoAnalistas(
					parametros, solicitudLegalizacionDesembolsoDTO.getEscalamientoMiembrosHogar(),
					solicitudLegalizacionDesembolsoDTO.getEscalamientoTecnicoConstruccion(),
					solicitudLegalizacionDesembolsoDTO.getEscalamientoJuridico(),
					solicitudLegalizacionDesembolsoDTO.getIdSolicitud(), userDTO);
			solicitudLegalizacionDesembolsoDTO
					.setEscalamientoJuridico(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
			solicitudLegalizacionDesembolsoDTO
					.setEscalamientoMiembrosHogar(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
			solicitudLegalizacionDesembolsoDTO.setEscalamientoTecnicoConstruccion(
					escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));

			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolsoDTO.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_ESCALADO);
			solicitudLegalizacionDesembolsoDTO
					.setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_ESCALADO);

			guardarDatosTemporal(solicitudLegalizacionDesembolsoDTO);
			terminarTarea(solicitudLegalizacionDesembolsoDTO.getIdTarea(), parametros,
					solicitudLegalizacionDesembolsoDTO.getIdInstanciaProceso());

			logger.info(
					"Finaliza servicio escalarSolicitudLegalizacionYDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
			return solicitudLegalizacionDesembolsoDTO;
		} catch (Exception e) {
			logger.error(
					"Error inesperado en escalarSolicitudLegalizacionYDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#analizarSolicitudLegalizacionDesembolso(
	 *      com.asopagos.legalizacionfovis.composite.dto.AnalisisSolicitudLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void analizarSolicitudLegalizacionDesembolso(AnalisisSolicitudLegalizacionDesembolsoDTO analisisSolicitud,
			UserDTO userDTO) {
		logger.info(
				"Inicia servicio analizarSolicitudLegalizacionDesembolso(AnalisisSolicitudLegalizacionDesembolsoDTO, UserDTO)");
		try {
			SolicitudLegalizacionDesembolsoDTO solicitudTemporal = consultarLegalizacionDesembolsoTemporal(
					analisisSolicitud.getIdSolicitud());

			RegistrarEscalamientoSolicitud registrarEscalamientoSolicitud = new RegistrarEscalamientoSolicitud(
					analisisSolicitud.getIdSolicitud(), analisisSolicitud.getEscalamientoSolicitud());
			registrarEscalamientoSolicitud.execute();
			EscalamientoSolicitudDTO escalamiento = registrarEscalamientoSolicitud.getResult();
			if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR.equals(escalamiento.getTipoAnalistaFOVIS())) {
				solicitudTemporal.setEscalamientoMiembrosHogar(escalamiento);
			} else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO
					.equals(escalamiento.getTipoAnalistaFOVIS())) {
				solicitudTemporal.setEscalamientoJuridico(escalamiento);
			} else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO.equals(escalamiento.getTipoAnalistaFOVIS())) {
				solicitudTemporal.setEscalamientoTecnicoConstruccion(escalamiento);
			}

			if (!existenEscalamientosSinResultado(analisisSolicitud.getIdSolicitud())) {

				actualizarEstadoSolicitudLegalizacionDesembolso(analisisSolicitud.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_EN_ANALISIS_ESPECIALIZADO);
				actualizarEstadoSolicitudLegalizacionDesembolso(analisisSolicitud.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_GESTIONADO_POR_ESPECIALISTA);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_GESTIONADO_POR_ESPECIALISTA);
			}
			guardarLegalizacionDesembolsoTemporal(solicitudTemporal, userDTO);
			terminarTarea(analisisSolicitud.getIdTarea(), null, solicitudTemporal.getIdInstanciaProceso());
			logger.info(
					"Finaliza servicio analizarSolicitudLegalizacionDesembolso(AnalisisSolicitudLegalizacionDesembolsoDTO, UserDTO)");
		} catch (Exception e) {
			logger.error(
					"Error en analizarSolicitudLegalizacionDesembolso(AnalisisSolicitudLegalizacionDesembolsoDTO, UserDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#finalizarAnalisisLegalizacionDesembolso(
	 *      com.asopagos.legalizacionfovis.composite.dto.ResultadoAnalisisLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void finalizarAnalisisLegalizacionDesembolso(ResultadoAnalisisLegalizacionDesembolsoDTO resultadoAnalisis,
			UserDTO userDTO) {
		logger.info(
				"Se inicia el servicio de finalizarAnalisisLegalizacionDesembolso(ResultadoAnalisisLegalizacionDesembolsoDTO, UserDTO)");
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("resultadoAnalisisEsp", resultadoAnalisis.getResultadoAnalisisEsp());

			SolicitudLegalizacionDesembolsoDTO solicitudTemporal = consultarLegalizacionDesembolsoTemporal(
					resultadoAnalisis.getIdSolicitud());

			// caso 1: solicitud procedente, caso 2: solicitud no procedente
			switch (resultadoAnalisis.getResultadoAnalisisEsp()) {
			case 1:

				actualizarEstadoSolicitudLegalizacionDesembolso(resultadoAnalisis.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_PENDIENTE_ENVIO_AL_BACK);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_PENDIENTE_ENVIO_AL_BACK);
				break;
			case 2:
				// Se rechaza solicitud de postulación
				actualizarEstadoSolicitudLegalizacionDesembolso(resultadoAnalisis.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);
				// Se cierra la solicitud de postulación
				actualizarEstadoSolicitudLegalizacionDesembolso(resultadoAnalisis.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);

				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
				break;
			}
			guardarLegalizacionDesembolsoTemporal(solicitudTemporal, userDTO);
			terminarTarea(resultadoAnalisis.getIdTarea(), params, solicitudTemporal.getIdInstanciaProceso());
			logger.info(
					"Finaliza el servicio de finalizarAnalisisLegalizacionDesembolso(ResultadoAnalisisLegalizacionDesembolsoDTO, UserDTO)");

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio finalizarAnalisisLegalizacionDesembolso("
					+ "ResultadoAnalisisLegalizacionDesembolsoDTO, UserDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.service.LegalizacionFovisService#
	 * autorizarDesembolso(com.asopagos.dto.ProductoNoConformeDTO)
	 */
	@Override
	// @Asynchronous
	public SolicitudLegalizacionDesembolsoDTO autorizarDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.info("Inicia el servicio autorizarDesembolso");

			// se guarda la información de la legalización.
			guardarDatosInicialesLegalizacionDesembolso(solicitudLegalizacionDesembolso, userDTO);

			// El sistema cambia el estado a la solicitud Legalización y
			// desembolso autorizado, registrando fecha y hora.
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_AUTORIZADO);

			if (!FormaPagoEnum.PAGO_CONTRA_ESCRITURA.equals(solicitudLegalizacionDesembolso.getLegalizacionDesembolso().getFormaPago())) {

				logger.info("SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO 0 ");
				// Se cambia el estado del hogar a Subsidio con anticipo
				cambiarEstadoHogar(solicitudLegalizacionDesembolso.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(), EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO);
			}else {
				// Se cambia el estado del hogar a Subsidio legalizado
				logger.info("SUBSIDIO_LEGALIZADO 0.0 ");
				cambiarEstadoHogar(solicitudLegalizacionDesembolso.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(), EstadoHogarEnum.SUBSIDIO_LEGALIZADO);
			}

			// Registro de ejecución asíncrona
			//
			// EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new
			// EjecucionProcesoAsincronoDTO();
			// ejecucionProcesoAsincronoDTO.setFechaInicio(Calendar.getInstance().getTime());
			// ejecucionProcesoAsincronoDTO.setProcesoRevisado(Boolean.FALSE);
			// ejecucionProcesoAsincronoDTO.setTipoProceso(TipoProcesoAsincronoEnum.DESEMBOLSO_FOVIS);
			// ejecucionProcesoAsincronoDTO =
			// registrarEjecucionProcesoAsincrono(ejecucionProcesoAsincronoDTO);

			// El sistema cambia el estado de la solicitud Desembolso FOVIS
			// solicitado a área financiera
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA);

			solicitudLegalizacionDesembolso.setEstadoSolicitud(
					EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA);

			/* Consulta la solicitud actual. */
			SolicitudLegalizacionDesembolsoModeloDTO solicitud = this
					.consultarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud());
			solicitud.setEsReintentoTransaccionDesembolso(Boolean.FALSE);

			// this.procesarTransaccionDesembolso(solicitudLegalizacionDesembolso);

			if(!solicitudLegalizacionDesembolso.getLegalizacionProveedor().isEmpty()) {
				for (LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorModeloDTO : solicitudLegalizacionDesembolso.getLegalizacionProveedor()) {
					PostulacionProvOfeDTO postulacionProvOfeDTO = new PostulacionProvOfeDTO();
					postulacionProvOfeDTO.setProveedor(legalizacionDesembolosoProveedorModeloDTO.getIdProveedor().toString());
					postulacionProvOfeDTO.setSolicitudLegalizacionFovis(solicitud);
					postulacionProvOfeDTO.setValorDesembolsoProveedor(legalizacionDesembolosoProveedorModeloDTO.getValorADesembolsar().intValue());
					postulacionProvOfeDTO.setFechaRegistro(new Date());
					CrearActualizarPostulacionProveedor service = new CrearActualizarPostulacionProveedor(postulacionProvOfeDTO);
					service.execute();
				}
			}
			if(solicitudLegalizacionDesembolso.getOferenteLegalizacion() != null && solicitudLegalizacionDesembolso.getOferenteLegalizacion().getOferente() != null && solicitudLegalizacionDesembolso.getOferenteLegalizacion().getOferente().getIdOferente() != null){
				PostulacionProvOfeDTO postulacionProvOfeDTO = new PostulacionProvOfeDTO();
				postulacionProvOfeDTO.setOferente(solicitudLegalizacionDesembolso.getOferenteLegalizacion().getOferente().getIdOferente());
				postulacionProvOfeDTO.setProyectoSolucionVivienda(solicitudLegalizacionDesembolso.getProyectoSolucionViviendaLegalizacion() != null ? solicitudLegalizacionDesembolso.getProyectoSolucionViviendaLegalizacion().getIdProyectoVivienda() : null );
				postulacionProvOfeDTO.setSolicitudLegalizacionFovis(solicitud);
				postulacionProvOfeDTO.setFechaRegistro(new Date());
				postulacionProvOfeDTO.setValorDesembolsoProveedor(solicitudLegalizacionDesembolso.getLegalizacionDesembolso().getValorADesembolsar().intValue());
				CrearActualizarPostulacionProveedor service = new CrearActualizarPostulacionProveedor(postulacionProvOfeDTO);
				service.execute();
			}

			this.guardarDatosTemporal(solicitudLegalizacionDesembolso);

			/* Se finaliza la tarea. */
			// parametrosProceso.put(AUTORIZAR_DESEMBOLSO, Boolean.TRUE);
			// this.terminarTarea(null, parametrosProceso,
			// Long.valueOf(solicitudLegalizacionDesembolso.getIdInstanciaProceso()));

			// Adicionalmente se crea una tarea en la bandeja de trabajo del
			// usuario con rol Pagador FOVIS,
			// al abrir la tarea se ejecuta la HU-324-286 Registrar resultado
			// ejecución desembolso FOVIS
			// Se asigna la tarea al usuario con rol Pagador FOVIS
			Map<String, Object> params = new HashMap<>();
			params.put(AUTORIZAR_DESEMBOLSO, Boolean.TRUE);
			params.put(USUARIO_PAGADOR_FOVIS, solicitudLegalizacionDesembolso.getUsuario());
			terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
					solicitudLegalizacionDesembolso.getIdInstanciaProceso());

			// Actualiza finalización ejecución proceso asíncrono
			// ejecucionProcesoAsincronoDTO.setFechaFin(Calendar.getInstance().getTime());
			// ActualizarEjecucionProcesoAsincrono
			// actualizarEjecucionProcesoAsincrono = new
			// ActualizarEjecucionProcesoAsincrono(
			// ejecucionProcesoAsincronoDTO);
			// actualizarEjecucionProcesoAsincrono.execute();

			logger.info("Finaliza el servicio autorizarDesembolso");
			return solicitudLegalizacionDesembolso;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio autorizarDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#verificarSolicitudLegalizacionDesembolso(
	 *      com.asopagos.legalizacionfovis.composite.dto.VerificacionGestionPNCLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void verificarSolicitudLegalizacionDesembolso(
			VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion, UserDTO userDTO) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("resultadoVerificacionBack", datosVerificacion.getResultadoVerificacionBack());
			SolicitudLegalizacionDesembolsoDTO solicitudTemporal = consultarLegalizacionDesembolsoTemporal(
					datosVerificacion.getIdSolicitud());

			/* Consulta los datos de la solicitud. */
			SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
					.consultarSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud());
			solicitudTemporal.getLegalizacionDesembolso()
					.setIdLegalizacionDesembolso(solicitudDTO.getIdLegalizacionDesembolso());

			guardarDatosInicialesLegalizacionDesembolso(solicitudTemporal, userDTO);

			// Caso1: Subsanable, Caso2: Enviar a coordinador, Caso3: Rechazar
			switch (datosVerificacion.getResultadoVerificacionBack()) {
			case 1:
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_SUBSANABLE);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_SUBSANABLE);
				break;
			case 2:
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_VERIFICADO);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_VERIFICADO);
				break;
			case 3:
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);
				break;
			}
			guardarDatosTemporal(solicitudTemporal);
			/*
			 * Si se verifica que no se incluyó el certificado de existencia y
			 * habitabilidad se envía la señal.
			 */
			if (solicitudTemporal.getIncluyeCertificadoExistenciaHabitabilidad() != null
					&& !solicitudTemporal.getIncluyeCertificadoExistenciaHabitabilidad()) {
				try {
					EnviarSenal enviarSenal = new EnviarSenal(solicitudDTO.getTipoTransaccion().getProceso(),
							SENAL_EXISTENCIA_HABITABILIDAD, Long.valueOf(solicitudDTO.getIdInstanciaProceso()), null);
					enviarSenal.execute();
				} catch (Exception e) {
					// La tarea del inspector FOVIS ya fue finalizada.
					logger.info(
							"(verificarSolicitudLegalizacionDesembolso)La tarea del inspector FOVIS Fue finalizada, no fue posible enviar la señal.");
				}

			}
			/* Se termina la tarea actual. */
			terminarTarea(datosVerificacion.getIdTarea(), params, solicitudTemporal.getIdInstanciaProceso());
		} catch (TechnicalException e) {
			throw e;
		} catch (Exception e) {
			logger.error(
					"Ocurrio un error inesperado en verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#reintentarTransaccionDesembolso(com.
	 * asopagos.dto. modelo.SolicitudLegalizacionDesembolsoModeloDTO)
	 */
	@Override
	// @Asynchronous
	public SolicitudLegalizacionDesembolsoModeloDTO reintentarTransaccionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.info("Inicia el servicio reintentarTransaccionDesembolso");
			// El sistema registra la información de la solicitud en la base de
			// datos. Se guarda la fecha y hora de la operación
			SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
					.consultarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud());

			solicitudDTO.setEsReintentoTransaccionDesembolso(Boolean.TRUE);

			if (solicitudDTO.getCantidadReintentos() != null) {
				solicitudDTO.setCantidadReintentos((short) (solicitudDTO.getCantidadReintentos() + 1));
			} else {
				solicitudDTO.setCantidadReintentos((short) 1);
			}

			// se invoca el servicio que actualiza la solicitud de legalizacion
			// desembolso
			solicitudDTO = this.crearActualizarSolicitudLegalizacionDesembolso(solicitudDTO);
			solicitudLegalizacionDesembolso.setCantidadReintentos(solicitudDTO.getCantidadReintentos());

			// Se puede realizar la actualización de datos para reintentar la
			// transacción de desembolso debe permitir
			// máximo 10 intentos de modificación de actualización de la
			// información del oferente y proyecto o solución de vivienda
			if (solicitudDTO.getCantidadReintentos() != null && solicitudDTO.getCantidadReintentos() <= 10) {
				solicitudDTO.setFechaOperacion(new Date().getTime());

				// El sistema cambia el estado de la solicitud Desembolso FOVIS
				// solicitado a área financiera
				actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA);

				solicitudLegalizacionDesembolso.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA);

				this.guardarDatosTemporal(solicitudLegalizacionDesembolso);

				// Adicionalmente se crea una tarea en la bandeja de trabajo del
				// usuario con rol Pagador FOVIS,
				// al abrir la tarea se ejecuta la HU-324-286 Registrar
				// resultado ejecución desembolso FOVIS
				// Se asigna la tarea al usuario con rol Pagador FOVIS
				Map<String, Object> params = new HashMap<>();
				params.put(RECHAZO_REINTENTO, Boolean.FALSE);
				// Se envia el usuario pagador seleccionado por pantalla
				params.put(USUARIO_PAGADOR_FOVIS, solicitudLegalizacionDesembolso.getUsuario());
				terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
						solicitudLegalizacionDesembolso.getIdInstanciaProceso());
			}

			logger.info("Finaliza el servicio reintentarTransaccionDesembolso");
			return solicitudDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio reintentarTransaccionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#
	 *      gestionarPNCLegalizacionDesembolso(java.lang.Long,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void gestionarPNCLegalizacionDesembolso(Long idSolicitud, UserDTO userDTO) {
		logger.info("Inicia servicio gestionarPNCLegalizacionDesembolso(long, UserDTO)");
		try {
			SolicitudLegalizacionDesembolsoDTO solicitudTemporal = consultarLegalizacionDesembolsoTemporal(idSolicitud);
			actualizarEstadoSolicitudLegalizacionDesembolso(idSolicitud,
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_EN_GESTION);
			actualizarEstadoSolicitudLegalizacionDesembolso(idSolicitud,
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_GESTIONADO);
			solicitudTemporal.setEstadoSolicitud(
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_GESTIONADO);
			guardarDatosTemporal(solicitudTemporal);
			terminarTarea(solicitudTemporal.getIdTarea(), null, solicitudTemporal.getIdInstanciaProceso());
			logger.info("Finaliza servicio gestionarPNCLegalizacionDesembolso(Long, UserDTO)");
		} catch (Exception e) {
			logger.error("Error inesperado en gestionarPNCLegalizacionDesembolso(Long, UserDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.LegalizacionFovisCompositeService#verificarGestionPNCLegalizacionDesembolso(
	 *      com.asopagos.legalizacionfovis.composite.dto.VerificacionGestionPNCLegalizacionDesembolsoDTO,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void verificarGestionPNCLegalizacionDesembolso(
			VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion, UserDTO userDTO) {
		logger.info(
				"Se inicia el servicio de verificarGestionPNCLegalizacionDesembolso(VerificacionGestionPNCLegalizacionDesembolsoDTO, UserDTO)");
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("resultadoVerifPNC", datosVerificacion.getResultadoVerifPNC());

			SolicitudLegalizacionDesembolsoDTO solicitudTemporal = consultarLegalizacionDesembolsoTemporal(
					datosVerificacion.getIdSolicitud());

			// se guarda la información de la postulación
			guardarDatosInicialesLegalizacionDesembolso(solicitudTemporal, userDTO);

			// Caso1: Enviar a coordinador, Caso2: Rechazar
			switch (datosVerificacion.getResultadoVerifPNC()) {
			case 1:
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_SUBSANADO);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_CONFORME_SUBSANADO);
				break;
			case 2:
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);
				actualizarEstadoSolicitudLegalizacionDesembolso(datosVerificacion.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
				solicitudTemporal.setEstadoSolicitud(
						EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
				break;
			}
			guardarDatosTemporal(solicitudTemporal);
			terminarTarea(datosVerificacion.getIdTarea(), params, solicitudTemporal.getIdInstanciaProceso());
			logger.info(
					"Finaliza el servicio de verificarGestionPNCLegalizacionDesembolso(VerificacionGestionPNCLegalizacionDesembolsoDTO, UserDTO)");

		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio "
							+ "verificarGestionPNCLegalizacionDesembolso(VerificacionGestionPNCLegalizacionDesembolsoDTO, UserDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#asignarSolicitudLegalizacion(com.
	 * asopagos.
	 * legalizacionfovis.composite.dto.AsignarSolicitudLegalizacionDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
 @Override
	public void asignarSolicitudLegalizacion(AsignarSolicitudLegalizacionDTO entrada, UserDTO userDTO) {
		logger.info("Se inicia el servicio de asignarSolicitudLegalizacion(AsignarSolicitudLegalizacionDTO)");
		try {
			String destinatario = null;
			String sedeDestinatario = null;
			String observacion = null;
			UsuarioDTO usuarioDTO = new UsuarioCCF();
			Map<String, Object> params = new HashMap<>();

			if (MetodoAsignacionBackEnum.AUTOMATICO.equals(entrada.getMetodoAsignacion())) {
				destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(
						new Long(userDTO.getSedeCajaCompensacion()), entrada.getTipoTransaccion().getProceso());
				usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
				sedeDestinatario = usuarioDTO.getCodigoSede();
				observacion = null;
			} else if (MetodoAsignacionBackEnum.MANUAL.equals(entrada.getMetodoAsignacion())) {
				// se busca el usuario a quien se le asigna la tarea, por su
				// nombe
				// de usuario
				usuarioDTO = consultarUsuarioCajaCompensacion(entrada.getUsuario());
				sedeDestinatario = usuarioDTO.getCodigoSede();
				destinatario = usuarioDTO.getNombreUsuario();
				observacion = entrada.getObservacion();
			}
			/* se actualiza la solicitud de legalización */
			SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacion = consultarSolicitudLegalizacionDesembolso(
					entrada.getIdSolicitud());
			if (TipoAsignacionLegalizacionFovisEnum.BACK.equals(entrada.getTipoAsignacion())) {
				solicitudLegalizacion.setDestinatario(destinatario);
				solicitudLegalizacion
						.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
				solicitudLegalizacion.setObservacion(observacion);

				/*
				 * se cambia el estado de la soliciutd de acuerdo a los
				 * documentos.
				 */
				if (entrada.getDocumentosFisicos()) {
					solicitudLegalizacion.setEstadoSolicitud(
							EstadoSolicitudLegalizacionDesembolsoEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS);
				} else {
					solicitudLegalizacion.setEstadoDocumentacion(EstadoDocumentacionEnum.ENVIADA_AL_BACK);
					solicitudLegalizacion.setEstadoSolicitud(
							EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_ASIGNADO_AL_BACK);
				}
				solicitudLegalizacion.setFechaOperacion((new Date()).getTime());
				this.crearActualizarSolicitudLegalizacionDesembolso(solicitudLegalizacion);

				params.put(USUARIO_BACK, destinatario);
				params.put(DOCUMENTOS_FISICOS, entrada.getDocumentosFisicos());
			} else if (TipoAsignacionLegalizacionFovisEnum.INSPECTOR.equals(entrada.getTipoAsignacion())) {
				params.put(INSPECTOR_FOVIS, destinatario);
			} else if (TipoAsignacionLegalizacionFovisEnum.COORDINADOR.equals(entrada.getTipoAsignacion())) {
				params.put(COORDINADOR_FOVIS, destinatario);
			}
			terminarTarea(entrada.getIdTarea(), params, solicitudLegalizacion.getIdInstanciaProceso() != null
					? new Long(solicitudLegalizacion.getIdInstanciaProceso()) : null);
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio asignarSolicitudLegalizacion(AsignarSolicitudLegalizacionDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#
	 * registrarConceptoExistenciaHabitabilidad(com.
	 * asopagos.legalizacionfovis.composite.dto.
	 * RegistroExistenciaHabitabilidadDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public RegistroExistenciaHabitabilidadDTO registrarConceptoExistenciaHabitabilidad(
			RegistroExistenciaHabitabilidadDTO existenciaHabitabilidadDTO, UserDTO userDTO) {
		logger.info(
				"Se inicia el servicio de registrarConceptoExistenciaHabitabilidad(RegistroExistenciaHabitabilidadDTO)");
		try {
			/* Se registran los datos de la visita */
			CrearVisita crearVisita = new CrearVisita(existenciaHabitabilidadDTO.getVisita());
			crearVisita.execute();
			VisitaModeloDTO visitaOutDTO = crearVisita.getResult();
			existenciaHabitabilidadDTO.setVisita(visitaOutDTO);

			/*
			 * Se asigna el identificador de la Visita, a las condiciones de
			 * visita.
			 */
			for (CondicionVisitaModeloDTO condicionVisita : existenciaHabitabilidadDTO.getCondicionesVisitaDTO()) {
				condicionVisita.setIdVisita(visitaOutDTO.getIdVisita());
			}
			/* Se crean las condiciones de la visita. */
			CrearCondicionesVisita crearCondicionesVisita = new CrearCondicionesVisita(
					existenciaHabitabilidadDTO.getCondicionesVisitaDTO());
			crearCondicionesVisita.execute();
			existenciaHabitabilidadDTO.setCondicionesVisitaDTO(crearCondicionesVisita.getResult());

			/*
			 * Si se debe terminar tarea de HU058-Registro existencia y
			 * habitabilidad.
			 */
			if (existenciaHabitabilidadDTO.getIdSolicitudGlobal() != null
					&& existenciaHabitabilidadDTO.getTerminarTarea() != null
					&& existenciaHabitabilidadDTO.getTerminarTarea()) {
				/* Consulta los datos de la solicitud. */
				SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
						.consultarSolicitudLegalizacionDesembolso(existenciaHabitabilidadDTO.getIdSolicitudGlobal());
				if (solicitudDTO.getLegalizacionDesembolso() != null) {
					solicitudDTO.getLegalizacionDesembolso().setIdVisita(visitaOutDTO.getIdVisita());
					this.crearActualizarLegalizacionDesembolso(solicitudDTO.getLegalizacionDesembolso());
				}
				/* Se termina la tarea. */
				terminarTarea(existenciaHabitabilidadDTO.getIdTarea(), null,
						Long.valueOf(solicitudDTO.getIdInstanciaProceso()));
			}

			logger.info(
					"Finaliza servicio de registrarConceptoExistenciaHabitabilidad(RegistroExistenciaHabitabilidadDTO)");

			return existenciaHabitabilidadDTO;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio registrarConceptoExistenciaHabitabilidad(RegistroExistenciaHabitabilidadDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/*********************************************
	 * METODOS PRIVADOS
	 * 
	 *********************************************/

	/**
	 * Método que invoca el servicio de creación de persona
	 * 
	 * @param personaDTO
	 *            La información de la persona a crear
	 * @return El identificador del registro generado
	 */
	private Long crearPersona(PersonaModeloDTO personaDTO) {
		logger.info("Inicio de método crearPersona");
		CrearPersona crearPersona = new CrearPersona(personaDTO);
		crearPersona.execute();
		logger.info("Fin de método crearPersona");
		return crearPersona.getResult();
	}

	/**
	 * Método que invoca el servicio de creación de empresa
	 * 
	 * @param empresaDTO
	 *            La información de la empresa
	 * @return El identificador del registro generado
	 */
	private Long crearEmpresa(EmpresaModeloDTO empresaDTO) {
		logger.info("Inicio de método crearPersona");
		CrearEmpresa crearEmpresa = new CrearEmpresa(empresaDTO);
		crearEmpresa.execute();
		logger.info("Fin de método crearPersona");
		return crearEmpresa.getResult();
	}

	/**
	 * Busca una lista de personas pero sin el detalle de la misma
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación de la persona
	 * @param numeroIdentificacion
	 *            Número de identificación de la persona
	 * @param primerNombre
	 *            Primer nombre asociado a la persona
	 * @param primerApellido
	 *            Primer apellido asociado a la persona
	 * @param segundoNombre
	 *            Segundo nombre asociado a la persona
	 * @param segundoApellido
	 *            Segundo apellido asociado a la persona
	 * @param razonSocial
	 *            Razon social de la persona
	 * @return Lista de personas DTO
	 */
	private List<PersonaDTO> buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String primerApellido, String segundoNombre,
			String segundoApellido, String razonSocial) {
		try {
			logger.info("Inicio de método buscarPersonasSinDetalle");
			BuscarPersonasSinDetalle buscarPersonaSinDetalleServices = new BuscarPersonasSinDetalle(primerApellido,
					primerNombre, segundoApellido, numeroIdentificacion, tipoIdentificacion, segundoNombre,
					razonSocial);
			buscarPersonaSinDetalleServices.execute();
			logger.info("Fin de método buscarPersonasSinDetalle");
			return buscarPersonaSinDetalleServices.getResult();
		} catch (Exception e) {
			logger.error("Ocurrió un error en buscarPersonasSinDetalle", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Método que invoca el servicio de consulta de un oferente
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del oferente
	 * @param numeroIdentificacion
	 *            Número de identificación del oferente
	 * @return Objeo <code>OferenteModeloDTO</code> con la información del
	 *         oferente
	 */
	private OferenteModeloDTO consultarOferente(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.info("Inicia el método consultarOferente");
		ConsultarOferente service = new ConsultarOferente(numeroIdentificacion, tipoIdentificacion);
		service.execute();
		logger.info("Finaliza el método consultarOferente");
		return service.getResult();
	}
        
        /**
	 * Método que invoca el servicio de consulta de un proveedor
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del proveedor
	 * @param numeroIdentificacion
	 *            Número de identificación del proveedor
	 * @return Objeo <code>ProveedorModeloDTO</code> con la información del
	 *         oferente
	 */
	private ProveedorModeloDTO consultarProveedor(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.info("Inicia el método consultarProveedor");
		ConsultarProveedor service = new ConsultarProveedor(numeroIdentificacion, tipoIdentificacion);
		service.execute();
		logger.info("Finaliza el método consultarProveedor");
		return service.getResult();
	}
        

	/**
	 * Método que hace la peticion REST al servicio de obtener tarea activa en
	 * el BPM
	 * 
	 * @param idInstanciaProceso
	 *            <code>String</code> El identificador de la Instancia Proceso
	 *            Afiliacion de la Persona
	 * 
	 * @return <code>Long</code> El identificador de la tarea Activa
	 */
	private Long consultarTareaActiva(Long idInstanciaProceso) {
		logger.info("Inicia consultarTareaAfiliacionPersonas( idInstanciaProceso )");
		Long idTarea = null;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(idInstanciaProceso);
		obtenerTareaActivaService.execute();
		mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
		logger.info("Finaliza consultarTareaActiva( idInstanciaProceso )");
		idTarea = ((Integer) mapResult.get("idTarea")).longValue();
		return idTarea;
	}

	/**
	 * Método que termina una tarea del BPM
	 * 
	 * @param idTarea
	 *            es el identificador de la tarea
	 * @param params
	 *            Son los parámetros de la tarea
	 */
	private void terminarTarea(Long idTarea, Map<String, Object> params, Long idInstanciaProceso) {
		logger.info("Inicia terminarTarea(idTarea, paramas, idInstanciaProceso)");
		if (params == null) {
			params = new HashMap<>();
		}
		Long idTareaActiva = idTarea;
		if (idTarea == null) {
			idTareaActiva = consultarTareaActiva(idInstanciaProceso);
		}
		TerminarTarea terminarTarea = new TerminarTarea(idTareaActiva, params);
		terminarTarea.execute();
		logger.info("Fin terminarTarea(idTarea, paramas, idInstanciaProceso)");
	}

	/**
	 * Método encargado invocar el servicio que guarda temporalmente los datos
	 * de la solicitud.
	 * 
	 * @param solicitudLegalizacionDesembolsoFOVISModelDTO
	 *            dto con los datos a guardar.
	 * @throws JsonProcessingException
	 *             error convirtiendo
	 */
	private void guardarDatosTemporal(SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoFOVISModelDTO)
			throws JsonProcessingException {
		logger.info("Inicio de método guardarDatosTemporal(SolicitudLegalizacionDesembolsoDTO)");
		ObjectMapper mapper = new ObjectMapper();
		String jsonPayload;
		jsonPayload = mapper.writeValueAsString(solicitudLegalizacionDesembolsoFOVISModelDTO);
		GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(
				solicitudLegalizacionDesembolsoFOVISModelDTO.getIdSolicitud(), jsonPayload);
		datosTemporalService.execute();
		logger.info("Fin de método guardarDatosTemporal(SolicitudLegalizacionDesembolsoDTO)");
	}

	/**
	 * Método que invoca el servicio que consulta los datos temporales
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global.
	 * @return <code>jsonPayload</code> con los datos temporales.
	 */
	private String consultarDatosTemporales(Long idSolicitud) {
		logger.info("Inicio de método consultarDatosTemporales");
		String jsonPayload = new String();
		ConsultarDatosTemporales service = new ConsultarDatosTemporales(idSolicitud);
		service.execute();
		jsonPayload = (String) service.getResult();
		logger.info("Fin de método consultarDatosTemporales");
		return jsonPayload;
	}

	/**
	 * Método que verifica si la solicitud ya tiene un id relacionado, sino se
	 * crea y se inicia el proceso de legalizacion y desembolso.
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Datos de la solicitud a actualizar
	 * @param userDTO
	 *            Usuario del contexto de seguridad
	 */
	private void registrarSolicitudEIniciarProcesoLegalizacion(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {

		/*
		 * Si no existe la solicitud se crea y se inicia el proceso de
		 * legalizacion y desembolso.
		 */
		if (solicitudLegalizacionDesembolso.getIdSolicitud() == null) {
			SolicitudLegalizacionDesembolsoModeloDTO solicitud = this
					.crearSolicitudLegalizacionDesembolsoInicial(solicitudLegalizacionDesembolso, userDTO);
			/* Se inicia el Proceso BPM Legalización y Desembolso */
			solicitudLegalizacionDesembolso
					.setIdInstanciaProceso(this.iniciarProcesoLegalizacionDesembolso(solicitud.getIdSolicitud(),
							solicitudLegalizacionDesembolso.getTipoTransaccionEnum().getProceso(), userDTO));
		}
	}

	/**
	 * Método que transforma un
	 * <code>SolicitudLegalizacionDesembolsoFOVISDTO</code> en
	 * <code>SolicitudModeloDTO</code>
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            DTO a transformar
	 * @param userDTO
	 *            Información del usuario, tomado del contexto del servicio
	 * @return Obejto <code>SolicitudModeloDTO</code> equivalente
	 */
	private SolicitudModeloDTO transformarSolicitudLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {
		logger.info("Inicia el método transformarSolicitudLegalizacionDesembolso");
		SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
		solicitudDTO.setIdInstanciaProceso(solicitudLegalizacionDesembolsoDTO.getIdInstanciaProceso() != null
				? solicitudLegalizacionDesembolsoDTO.getIdInstanciaProceso().toString() : null);
		solicitudDTO.setCanalRecepcion(solicitudLegalizacionDesembolsoDTO.getCanalRecepcion());
		solicitudDTO.setEstadoDocumentacion(null);
		solicitudDTO.setMetodoEnvio(solicitudLegalizacionDesembolsoDTO.getMetodoEnvio());
		solicitudDTO.setIdCajaCorrespondencia(userDTO.getSedeCajaCompensacion() != null
				? consultarIdSedeCajaCompensacion(userDTO.getSedeCajaCompensacion()) : null);
		solicitudDTO.setTipoTransaccion(solicitudLegalizacionDesembolsoDTO.getTipoTransaccionEnum());
		solicitudDTO.setClasificacion(ClasificacionEnum.HOGAR);

		// Se radica la solicitud y se consulta la información de la radicación
		solicitudLegalizacionDesembolsoDTO.setNumeroRadicacion(radicarSolicitud(
				solicitudLegalizacionDesembolsoDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion()));
		SolicitudLegalizacionDesembolsoModeloDTO solicitud = consultarSolicitudLegalizacionDesembolso(
				solicitudLegalizacionDesembolsoDTO.getIdSolicitud());

		solicitudDTO.setNumeroRadicacion(solicitud.getNumeroRadicacion());
		solicitudDTO.setTipoRadicacion(solicitud.getTipoRadicacion());
		solicitudDTO.setFechaRadicacion(solicitud.getFechaRadicacion());
		solicitudDTO.setUsuarioRadicacion(solicitud.getUsuarioRadicacion());
		solicitudDTO.setCiudadSedeRadicacion(solicitud.getCiudadSedeRadicacion());
		solicitudDTO.setDestinatario(solicitud.getDestinatario());
		solicitudDTO.setSedeDestinatario(solicitud.getSedeDestinatario());
		solicitudDTO.setFechaCreacion(new Date().getTime());
		solicitudDTO.setObservacion(null);
		solicitudDTO.setIdSolicitud(solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
		logger.info("Finaliza el método transformarSolicitudLegalizacionDesembolso");
		return solicitudDTO;
	}

	/**
	 * Método que obtiene el identificador de una caja de compensación, por
	 * nombre
	 * 
	 * @param nombre
	 *            Nombre de la sede
	 * @return El identificador
	 */
	private Long consultarIdSedeCajaCompensacion(String nombre) {
		logger.info("Inicia método consultarIdSedeCajaCompensacion");
		ConsultarSedesCajaCompensacion consultarSedesCajaCompensacion = new ConsultarSedesCajaCompensacion();
		consultarSedesCajaCompensacion.execute();
		List<SedeCajaCompensacion> listaSedes = consultarSedesCajaCompensacion.getResult();

		for (SedeCajaCompensacion sede : listaSedes) {
			if (sede.getNombre().equals(nombre)) {
				logger.info("Finaliza método consultarIdSedeCajaCompensacion");
				return sede.getIdSedeCajaCompensacion();
			}
		}

		logger.info("Finaliza método consultarIdSedeCajaCompensacion. Sede no encontrada.");
		return null;
	}

	/**
	 * Método que consulta una solicitud de postulación.
	 * 
	 * @param idSolicitud
	 *            Es el identificador de la solicitud global.
	 * @return solicitudNovedadDTO Solicitud de postulación consultada.
	 */
	private SolicitudLegalizacionDesembolsoModeloDTO consultarSolicitudLegalizacionDesembolso(Long idSolicitud) {
		SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO = new SolicitudLegalizacionDesembolsoModeloDTO();
		ConsultarSolicitudLegalizacionDesembolso consultarSolicitudLegalizacionDesembolso = new ConsultarSolicitudLegalizacionDesembolso(
				idSolicitud);
		consultarSolicitudLegalizacionDesembolso.execute();
		solicitudLegalizacionDesembolsoDTO = consultarSolicitudLegalizacionDesembolso.getResult();
		return solicitudLegalizacionDesembolsoDTO;
	}

	/**
	 * Método que invoca el servicio de radicación de una solicitud
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador único de la solicitud global
	 * @param sedeCajaCompensacion
	 *            Sede de la CCF del usuario autenticado
	 */
	private String radicarSolicitud(Long idSolicitudGlobal, String sedeCajaCompensacion) {
		logger.info("Inicia radicarSolicitud(idSolicitud, sedeCajaCompensacion)");
		RadicarSolicitud service = new RadicarSolicitud(idSolicitudGlobal, sedeCajaCompensacion);
		service.execute();
		logger.info("Finaliza radicarSolicitud(idSolicitud, sedeCajaCompensacion)");

		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de crear o actualizar una licencia
	 * 
	 * @param licenciaDTO
	 *            DTO con la informacion a registrar o actualizar de la licencia
	 * @return Objeo <code>LicenciaModeloDTO</code> con la información del
	 *         oferente
	 */
	private LicenciaModeloDTO crearActualizarLicencia(LicenciaModeloDTO licenciaDTO) {

		logger.info("Inicia el método crearActualizarLicencia");
		CrearActualizarLicencia service = new CrearActualizarLicencia(licenciaDTO);
		service.execute();
		logger.info("Finaliza el método crearActualizarLicencia");
		return service.getResult();
	}

	/**
	 * Se encarga de guardar los datos de la solicitud de legalización y
	 * desembolso
	 * 
	 * @param tipoLegalizacionDesembolso
	 */
	private SolicitudLegalizacionDesembolsoModeloDTO crearSolicitudLegalizacionDesembolsoInicial(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {

		SolicitudLegalizacionDesembolsoModeloDTO solicitud = new SolicitudLegalizacionDesembolsoModeloDTO();
		solicitud.setCanalRecepcion(solicitudLegalizacionDesembolsoDTO.getCanalRecepcion());
		solicitud.setClasificacion(ClasificacionEnum.HOGAR);
		solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
		solicitud.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
		solicitud.setTipoTransaccion(solicitudLegalizacionDesembolsoDTO.getTipoTransaccionEnum());
		solicitud.setMetodoEnvio(solicitudLegalizacionDesembolsoDTO.getMetodoEnvio());
		solicitud.setFechaCreacion((new Date()).getTime());
        if (solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis() != null
                && solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion() != null
                && solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion().getIdPostulacion() != null) {
            solicitud.setIdPostulacionFOVIS(
                    solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion().getIdPostulacion());
            String jsonPostulacion = solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion()
                    .getInformacionAsignacion();
            if (jsonPostulacion == null) {
                jsonPostulacion = consultarPostulacionFOVIS(
                        solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion().getIdPostulacion())
                                .getInformacionAsignacion();
            }
            solicitud.setJsonPostulacion(jsonPostulacion);
        }
        
		solicitud = this.crearActualizarSolicitudLegalizacionDesembolso(solicitud);

		solicitudLegalizacionDesembolsoDTO.setIdSolicitud(solicitud.getIdSolicitud());
		solicitudLegalizacionDesembolsoDTO
				.setIdSolicitudLegalizacionDesembolso(solicitud.getIdSolicitudLegalizacionDesembolso());
		if (solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso() == null) {
			solicitudLegalizacionDesembolsoDTO.setLegalizacionDesembolso(new LegalizacionDesembolsoModeloDTO());
		}
		solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso()
				.setIdLegalizacionDesembolso(solicitud.getIdLegalizacionDesembolso());
		// solicitudLegalizacionDesembolsoDTO.setNumeroRadicacion(solicitud.getNumeroRadicacion());
		return solicitud;
	}

    /**
     * Llamado al servicio de consulta de información de postulacion fovis
     * @param idPostulacion
     *        identificador de la postulación
     * @return Información de postulación fovis
     */
    private PostulacionFOVISModeloDTO consultarPostulacionFOVIS(Long idPostulacion) {
        ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(idPostulacion);
        consultarPostulacionFOVIS.execute();
        return consultarPostulacionFOVIS.getResult();
    }

	/**
	 * Inicia Proceso BPM de LegalizacionDesembolso
	 * 
	 * @param idSolicitud
	 * @param procesoEnum
	 * @param usuario
	 * @return
	 */
	private Long iniciarProcesoLegalizacionDesembolso(Long idSolicitud, ProcesoEnum procesoEnum, UserDTO usuario) {
		logger.info("Inicia iniciarProcesoLegalizacionDesembolso( idSolicitudGlobal, procesoEnum )");
		Map<String, Object> parametrosProceso = new HashMap<String, Object>();
		SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolso = consultarSolicitudLegalizacionDesembolso(
				idSolicitud);

		ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
				idSolicitud);
		consultarSolicitudGlobal.execute();
		SolicitudModeloDTO resultadoSolicitud = consultarSolicitudGlobal.getResult();

		parametrosProceso.put(ID_SOLICITUD, idSolicitud);
		parametrosProceso.put(USUARIO_FRONT, usuario.getNombreUsuario());
		parametrosProceso.put("numeroRadicado", resultadoSolicitud.getNumeroRadicacion());
		IniciarProceso iniciarProcesLegalizacionDesembolsoService = new IniciarProceso(procesoEnum, parametrosProceso);
		iniciarProcesLegalizacionDesembolsoService.execute();
		Long idInstanciaProcesoLegalizacionDesembolso = iniciarProcesLegalizacionDesembolsoService.getResult();

		/* se actualiza el id instancia proceso */
		solicitudLegalizacionDesembolso.setIdInstanciaProceso(idInstanciaProcesoLegalizacionDesembolso.toString());
		/* se invoca el servicio que actualiza la solicitud de postulacion */
		this.crearActualizarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso);
		logger.info("Finaliza iniciarProcesoNovedad( idSolicitudGlobal )");
		return idInstanciaProcesoLegalizacionDesembolso;
	}

	/**
	 * Método que invoca el servicio de actualización de una solicitud de
	 * postulación
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private SolicitudLegalizacionDesembolsoModeloDTO crearActualizarSolicitudLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO) {
		logger.info("Inicia el método crearActualizarSolicitudLegalizacionDesembolso");
		CrearActualizarSolicitudLegalizacionDesembolso service = new CrearActualizarSolicitudLegalizacionDesembolso(
				solicitudLegalizacionDesembolsoDTO);
		service.execute();
		logger.info("Finaliza el método crearActualizarSolicitudLegalizacionDesembolso");

		return service.getResult();
	}

	/**
	 * 
	 * Método que invoca el servicio de crear o actualizar una licencia
	 * 
	 * @param licenciaDTO
	 *            DTO con la informacion a registrar o actualizar de la licencia
	 * @return Objeo <code>LicenciaModeloDTO</code> con la información del
	 *         oferente
	 */
	private LicenciaDetalleModeloDTO crearActualizarLicenciaDetalle(LicenciaDetalleModeloDTO licenciaDetalleDTO) {
		logger.info("Inicia el método crearActualizarLicenciaDetalle");
		CrearActualizarLicenciaDetalle service = new CrearActualizarLicenciaDetalle(licenciaDetalleDTO);
		service.execute();
		logger.info("Finaliza el método crearActualizarLicenciaDetalle");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de creación o actualización de un registro
	 * en la tabla <code>Solicitud</code>
	 * 
	 * @param solicitudDTO
	 *            La información del registro a actualizar
	 * @return El identificador del registro actualizado
	 */
	private Long guardarSolicitudGlobal(SolicitudModeloDTO solicitudDTO) {
		logger.info("Inicio de método guardarSolicitudGlobal");
		GuardarSolicitudGlobal service = new GuardarSolicitudGlobal(solicitudDTO);
		service.execute();
		logger.info("Fin de método guardarSolicitudGlobal");
		return service.getResult();
	}

	/**
	 * 324-053 Almacena los datos iniciales de la legalizacion y desembolso.
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 * @param estadoSolicitud
	 * @return
	 */
	private SolicitudLegalizacionDesembolsoDTO guardarDatosInicialesLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {

		SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
				.consultarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolsoDTO.getIdSolicitud());

		PostulacionFOVISModeloDTO postulacionFOVISModeloDTO  = consultarPostulacionFOVIS(solicitudDTO.getIdPostulacionFOVIS());

		/* Si llegan los datos de existencia y habitabilidad se actualizan. */
		RegistroExistenciaHabitabilidadDTO existenciaHabitabilidad = solicitudLegalizacionDesembolsoDTO
				.getExistenciaHabitabilidad();
		if (existenciaHabitabilidad != null && existenciaHabitabilidad.getVisita() != null
				&& existenciaHabitabilidad.getVisita().getCodigoIdentificadorVisita() != null) {
			/* Si ya esta asociada la Visita a la legalización se actualiza. */
			if (solicitudDTO.getLegalizacionDesembolso() != null
					&& solicitudDTO.getLegalizacionDesembolso().getIdVisita() != null) {
				existenciaHabitabilidad.getVisita().setIdVisita(solicitudDTO.getLegalizacionDesembolso().getIdVisita());
			}
			existenciaHabitabilidad.setTerminarTarea(Boolean.FALSE);
			existenciaHabitabilidad.setIdSolicitudGlobal(null);
			existenciaHabitabilidad = this.registrarConceptoExistenciaHabitabilidad(existenciaHabitabilidad, userDTO);
			if (existenciaHabitabilidad.getVisita() != null
					&& existenciaHabitabilidad.getVisita().getIdVisita() != null) {
				solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso()
						.setIdVisita(existenciaHabitabilidad.getVisita().getIdVisita());
			}
		}
		/* Si no tiene se radica la solicitud. */
		if (solicitudDTO.getNumeroRadicacion() == null) {
			solicitudLegalizacionDesembolsoDTO.setNumeroRadicacion(this.radicarSolicitud(
					solicitudLegalizacionDesembolsoDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion()));

			// cuando se radica se asigna la fecha limite de pago a la
			// legalización con 15 días hábiles
			ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
			consultarListafestivos.execute();
			Date fechaLimitePago = CalendarUtils.calcularFecha(new Date(), 15, CalendarUtils.TipoDia.HABIL,
					consultarListafestivos.getResult());
			solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso()
					.setFechaLimitePago(fechaLimitePago.getTime());
		} else {
		    solicitudLegalizacionDesembolsoDTO.setNumeroRadicacion(solicitudDTO.getNumeroRadicacion());
		}

		// Se registran los datos de la legalización
		if (FormaPagoEnum.PAGO_CONTRA_ESCRITURA.equals(solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso().getFormaPago())
				&& BigDecimal.ZERO.compareTo(solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso().getValorADesembolsar()) == 0){
			if (postulacionFOVISModeloDTO != null && postulacionFOVISModeloDTO.getValorAsignadoSFV() != null) {
				solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso().setValorADesembolsar(postulacionFOVISModeloDTO.getValorAsignadoSFV());
			}
		}
		solicitudLegalizacionDesembolsoDTO.setLegalizacionDesembolso(
				crearActualizarLegalizacionDesembolso(solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso()));
		// Se actualiza la solicitud con el Id de la Legalización.
		solicitudDTO.setIdLegalizacionDesembolso(
				solicitudLegalizacionDesembolsoDTO.getLegalizacionDesembolso().getIdLegalizacionDesembolso());
		// Se consulta la solicitud global para que se actualize correctamente
		// al registrar la solicitud de legalizacion desembolso
		ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
				solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
		consultarSolicitudGlobal.execute();
		solicitudDTO.convertToDTO(consultarSolicitudGlobal.getResult().convertToSolicitudEntity());

		// Almacena lista de chequeo documental de legalizacion y desembolso.
		ListaChequeoDTO listaChequeoLegalizacionDesembolso = solicitudLegalizacionDesembolsoDTO.getListaChequeo();
		listaChequeoLegalizacionDesembolso.setNumeroIdentificacion(solicitudLegalizacionDesembolsoDTO
				.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getNumeroIdentificacion());
		listaChequeoLegalizacionDesembolso.setTipoIdentificacion(solicitudLegalizacionDesembolsoDTO
				.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getTipoIdentificacion());
		listaChequeoLegalizacionDesembolso.setIdSolicitudGlobal(solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
		// TODO parametrizar las lista de chequeo para la clasificaicon hogar
		// legalización
		listaChequeoLegalizacionDesembolso.setListaFOVIS(Boolean.TRUE);
		guardarListaChequeo(listaChequeoLegalizacionDesembolso);

		// Se actualiza la solicitud de legalizacion
		this.crearActualizarSolicitudLegalizacionDesembolso(solicitudDTO);

		return solicitudLegalizacionDesembolsoDTO;
	}

	/**
	 * Método que registra el escalamiento al analista registrado en las
	 * propiedades del objeto <code>datosEscalamientoAnalistas</code> y coloca
	 * el destinatario en el map según aplique.
	 */
	private Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> registrarEscalamientoAnalistas(
			Map<String, Object> parametros, EscalamientoSolicitudDTO hogar, EscalamientoSolicitudDTO tecnico,
			EscalamientoSolicitudDTO juridico, Long idSolicitud, UserDTO userDTO) {
		logger.info("Inicia método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
		Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = new HashMap<>();
		if (hogar != null && hogar.getDestinatario() != null) {
			hogar = escalarSolicitud(idSolicitud, hogar, userDTO);
			escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR, hogar);
			parametros.put(ANALISTA_HOGAR, hogar.getDestinatario());
		}
		if (juridico != null && juridico.getDestinatario() != null) {
			juridico = escalarSolicitud(idSolicitud, juridico, userDTO);
			parametros.put(ANALISTA_JURIDICO, juridico.getDestinatario());
			escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO, juridico);
		}
		if (tecnico != null && tecnico.getDestinatario() != null) {
			tecnico = escalarSolicitud(idSolicitud, tecnico, userDTO);
			parametros.put(ANALISTA_TECNICO, tecnico.getDestinatario());
			escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO, tecnico);
		}
		logger.info("Finaliza método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
		return escalamientos;
	}

	/**
	 * Método que usa el servicio para escalar una solicitud.
	 * 
	 * @param idSolicitud
	 *            identificador global de la solicitud de postulación.
	 * @param escalamientoDTO
	 *            Datos del escalammiento.
	 */
	private EscalamientoSolicitudDTO escalarSolicitud(Long idSolicitud, EscalamientoSolicitudDTO escalamientoDTO,
			UserDTO usuario) {
		logger.debug("Se inicia el método de radicarSolicitud(Long, EscalamientoSolicitudDTO)");
		escalamientoDTO.setFechaCreacion(new Date());
		escalamientoDTO.setUsuarioCreacion(usuario != null ? usuario.getNombreUsuario() : null);
		RegistrarEscalamientoSolicitud escalarSolicitud = new RegistrarEscalamientoSolicitud(idSolicitud,
				escalamientoDTO);
		escalarSolicitud.execute();
		logger.debug("Finaliza el método de radicarSolicitud(Long, EscalamientoSolicitudDTO)");
		return escalarSolicitud.getResult();
	}

	/**
	 * Método que crea el intento de legalizacion y desembolso con los datos de
	 * la solicitud.
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            Datos de la solicitud.
	 * @param userDTO
	 *            Usuario a registrar.
	 * @return Objeto persistido del intento.
	 */
	private IntentoLegalizacionDesembolsoModeloDTO crearIntentoLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, UserDTO userDTO) {
		logger.debug("Inicia crearIntentoLegalizacionDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
		IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO = new IntentoLegalizacionDesembolsoModeloDTO();
		intentoLegalizacionDesembolsoModeloDTO
				.setCausaIntentoFallido(solicitudLegalizacionDesembolsoDTO.getCausaIntentoFallido());
		intentoLegalizacionDesembolsoModeloDTO.setFechaInicioProceso(Calendar.getInstance().getTime());
		intentoLegalizacionDesembolsoModeloDTO.setFechaCreacion(Calendar.getInstance().getTime());
		intentoLegalizacionDesembolsoModeloDTO.setIdSolicitud(solicitudLegalizacionDesembolsoDTO.getIdSolicitud());
		intentoLegalizacionDesembolsoModeloDTO.setSedeCajaCompensacion(userDTO.getSedeCajaCompensacion());
		intentoLegalizacionDesembolsoModeloDTO
				.setTipoTransaccion(solicitudLegalizacionDesembolsoDTO.getTipoTransaccionEnum());
		intentoLegalizacionDesembolsoModeloDTO.setUsuarioCreacion(userDTO.getNombreUsuario());
		intentoLegalizacionDesembolsoModeloDTO.setProceso(ProcesoEnum.LEGALIZACION_DESEMBOLSO_FOVIS);
		intentoLegalizacionDesembolsoModeloDTO.setModalidad(
				solicitudLegalizacionDesembolsoDTO.getDatosPostulacionFovis().getPostulacion().getIdModalidad());
		intentoLegalizacionDesembolsoModeloDTO.setTipoSolicitante(ClasificacionEnum.HOGAR.name());

		RegistrarIntentoLegalizacionDesembolsoFOVIS registrarIntentoLegalizacionDesembolso = new RegistrarIntentoLegalizacionDesembolsoFOVIS(
				intentoLegalizacionDesembolsoModeloDTO);
		registrarIntentoLegalizacionDesembolso.execute();
		intentoLegalizacionDesembolsoModeloDTO
				.setIdIntentoLegalizacionDesembolso(registrarIntentoLegalizacionDesembolso.getResult());
		logger.debug("Fin crearIntentoLegalizacionDesembolso(SolicitudLegalizacionDesembolsoDTO, UserDTO)");
		return intentoLegalizacionDesembolsoModeloDTO;
	}

	/**
	 * Método que hace la peticion REST al servicio que actualiza el estado de
	 * una solicitud de postulación
	 */
	private void actualizarEstadoSolicitudLegalizacionDesembolso(Long idSolicitud,
			EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
		logger.debug(
				"Inicia actualizarEstadoSolicitudLegalizacionDesembolso(Long, EstadoSolicitudLegalizacionDesembolsoEnum)");
		ActualizarEstadoSolicitudLegalizacionDesembolso actualizarEstadoSolPostulService = new ActualizarEstadoSolicitudLegalizacionDesembolso(
				idSolicitud, estadoSolicitud);
		actualizarEstadoSolPostulService.execute();
		logger.debug(
				"Finaliza actualizarEstadoSolicitudLegalizacionDesembolso(Long, EstadoSolicitudLegalizacionDesembolsoEnum)");
	}

	/**
	 * Método que usa un servicio para verificar si existen escalamientos sin
	 * resultado para una solicitud de postulación.
	 * 
	 * @param idSolicitud
	 *            Identificador de la solicitud global.
	 * @return Resultado de la existencia o no de escalamientos sin resultado.
	 */
	private boolean existenEscalamientosSinResultado(Long idSolicitud) {
		ExistenEscalamientosSinResultado servicio = new ExistenEscalamientosSinResultado(idSolicitud);
		servicio.execute();
		return servicio.getResult();
	}

	/**
	 * Método que invoca el servicio de actualización de un oferente
	 * 
	 * @param oferenteDTO
	 *            Información del oferente
	 * @return El registro actualizado
	 */
	private OferenteModeloDTO crearActualizarOferente(OferenteModeloDTO oferenteDTO) {
		logger.debug("Inicia el método crearActualizarOferente");
		CrearActualizarOferente service = new CrearActualizarOferente(oferenteDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarOferente");
		return service.getResult();
	}
        
        
        /**
	 * Método que invoca el servicio de actualización de un proveedor
	 * 
	 * @param proveedorDTO
	 *            Información del proveedor
	 * @return El registro actualizado
	 */
	private ProveedorModeloDTO crearActualizarProveedor(ProveedorModeloDTO proveedorDTO) {
		logger.debug("Inicia el método crearActualizarProveedor");
		CrearActualizarProveedor service = new CrearActualizarProveedor(proveedorDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarProveedor");
		return service.getResult();
	}
        

	/**
	 * Método que invoca el servicio de actualización de un proyecto de vivienda
	 * 
	 * @param proyectoSolucionViviendaDTO
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private ProyectoSolucionViviendaModeloDTO crearActualizarProyectoSolucionVivienda(
			ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO) {
		logger.debug("Inicia el método crearActualizarProyectoSolucionVivienda");
		CrearActualizarProyectoSolucionVivienda service = new CrearActualizarProyectoSolucionVivienda(
				proyectoSolucionViviendaDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarProyectoSolucionVivienda");
		return service.getResult();
	}

    /**
     * Método que actualiza el estado del hogar de una postulación FOVIS.
     * @param idPostulacionFOVIS
     *        identificador de la postulación.
     * @param estadoHogar
     *        Nuevo estado a a	signar.
     */
    private void cambiarEstadoHogar(Long idPostulacionFOVIS, EstadoHogarEnum estadoHogar) {
        ActualizarEstadoHogar actualizarEstadoHogar = new ActualizarEstadoHogar(idPostulacionFOVIS, estadoHogar);
        actualizarEstadoHogar.execute();
    }

	/**
	 * Método que invoca el servicio de crear actualizar el documento soporte
	 * 
	 * @param documentoSoporte
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private DocumentoSoporteModeloDTO crearActualizarDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporte) {
		logger.debug("Inicia el método crearActualizarDocumentoSoporte");
		RegistrarDocumentoSoporte service = new RegistrarDocumentoSoporte(documentoSoporte);
		service.execute();
		logger.debug("Finaliza el método crearActualizarDocumentoSoporte");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de almacenamiento de una lista de chequeo
	 * de requisitos documentales
	 * 
	 * @param listaChequeo
	 *            La información de la lista de chequeo a almacenar
	 */
	private void guardarListaChequeo(ListaChequeoDTO listaChequeo) {
		logger.debug("Inicio de método guardarListaChequeo");
		GuardarListaChequeo service = new GuardarListaChequeo(listaChequeo);
		service.execute();
		logger.debug("Fin de método guardarListaChequeo");
	}

	/**
	 * Método que invoca el servicio de actualización de la Legalizacion y
	 * Desembolso FOVIS
	 * 
	 * @param LegalizacionDTO
	 *            La información del registro a actualizar.
	 * @return Datos del registro actualizado.
	 */
	private LegalizacionDesembolsoModeloDTO crearActualizarLegalizacionDesembolso(
			LegalizacionDesembolsoModeloDTO legalizaciondesembolsoDTO) {
		logger.debug("Inicia el método crearActualizarLegalizacionDesembolso");
		CrearActualizarLegalizacionDesembolso service = new CrearActualizarLegalizacionDesembolso(
				legalizaciondesembolsoDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarLegalizacionDesembolso");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de asociar el documento soporte y oferente
	 * 
	 * @param documentoSoporteOferenteDTO
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private DocumentoSoporteOferenteDTO crearActualizarDocumentoSoporteOferente(
			DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO) {
		logger.debug("Inicia el método crearActualizarDocumentoSoporte");
		RegistrarRequisitosDocumentalesOferente service = new RegistrarRequisitosDocumentalesOferente(
				documentoSoporteOferenteDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarDocumentoSoporte");
		return service.getResult();
	}
        
        
        /**
	 * Método que invoca el servicio de asociar el documento soporte y proveedor
	 * 
	 * @param DocumentoSoporteProveedorDTO
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private DocumentoSoporteProveedorDTO crearActualizarDocumentoSoporteProveedor(
			DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO) {
		logger.debug("Inicia el método crearActualizarDocumentoSoporteProveedor");
		RegistrarRequisitosDocumentalesProveedor service = new RegistrarRequisitosDocumentalesProveedor(
				documentoSoporteProveedorDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarDocumentoSoporteProveedor");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de asociar el documento soporte y el
	 * proyecto de vivienda
	 * 
	 * @param documentoSoporteProyectoViviendaDTO
	 *            La información del registro a actualizar
	 * @return Datos del registro actualizado
	 */
	private DocumentoSoporteProyectoViviendaDTO registrarRequisitosDocumentalesProyectoVivienda(
			DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO) {
		logger.debug("Inicia el método crearActualizarDocumentoSoporte");
		RegistrarRequisitosDocumentalesProyectoVivienda service = new RegistrarRequisitosDocumentalesProyectoVivienda(
				documentoSoporteProyectoViviendaDTO);
		service.execute();
		logger.debug("Finaliza el método crearActualizarDocumentoSoporte");
		return service.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#consultarInformacionVisita(java.lang.
	 * Long)
	 */
	@Override
	public VisitaDTO consultarInformacionVisita(Long idVisita) {

		VisitaDTO visitaDTO = new VisitaDTO();
		List<CondicionVisitaModeloDTO> listaCondiciones = new ArrayList<>();

		try {
			logger.debug("Inicia el servicio consultarInformacionVisita(Long idVisita)");
			// Se consulta los datos de la visita
			ConsultarVisita consultarVisita = new ConsultarVisita(idVisita);
			consultarVisita.execute();
			visitaDTO = consultarVisita.getResult();

			// Se consultan las condiciones asocciadas a la visita
			ConsultarCondicionesVisita consultarCondiciones = new ConsultarCondicionesVisita(idVisita);
			consultarCondiciones.execute();
			listaCondiciones = consultarCondiciones.getResult();

			visitaDTO.setListaCondiciones(listaCondiciones);

			return visitaDTO;

		} catch (Exception e) {
			logger.error("Error - Finaliza servicio consultarInformacionVisita(Long idVisita)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#procesarTransaccionDesembolso(com.
	 * asopagos.dto. modelo. SolicitudLegalizacionDesembolsoModeloDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO procesarTransaccionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.debug("Inicia el servicio procesarTransaccionDesembolso");
			// Se asigna una tarea a la bandeja de trabajo del usuario con rol
			// Pagador FOVIS, mediante la HU-TRA-140.
			String destinatario = null;
			UsuarioDTO usuarioDTO = new UsuarioCCF();
			Map<String, Object> params = new HashMap<>();

			if (MetodoAsignacionBackEnum.AUTOMATICO.equals(solicitudLegalizacionDesembolso.getMetodoAsignacion())) {
				destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(
						new Long(userDTO.getSedeCajaCompensacion()),
						solicitudLegalizacionDesembolso.getTipoTransaccionEnum().getProceso());
				usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);

			} else if (MetodoAsignacionBackEnum.MANUAL.equals(solicitudLegalizacionDesembolso.getMetodoAsignacion())) {
				// se busca el usuario a quien se le asigna la tarea, por su
				// nombre
				// de usuario
				usuarioDTO = consultarUsuarioCajaCompensacion(solicitudLegalizacionDesembolso.getUsuario());
				destinatario = usuarioDTO.getNombreUsuario();
			}
			// El sistema cambia el estado de la solicitud Desembolso FOVIS
			// solicitado a área financiera
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA);
			// Se envia el usuario pagador seleccionado por pantalla
			params.put(USUARIO_PAGADOR_FOVIS, solicitudLegalizacionDesembolso.getUsuario());

			terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
					solicitudLegalizacionDesembolso.getIdInstanciaProceso() != null
							? new Long(solicitudLegalizacionDesembolso.getIdInstanciaProceso()) : null);

			logger.debug("Finaliza el servicio procesarTransaccionDesembolso");
			return solicitudLegalizacionDesembolso;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio procesarTransaccionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#noAutorizarDesembolso(com.asopagos.dto.
	 * modelo. SolicitudLegalizacionDesembolsoModeloDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoModeloDTO noAutorizarDesembolso(
			SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolso) {
		try {

			logger.debug("Inicia el servicio noAutorizarDesembolso");
			// El sistema cambia el estado de la solicitud a Legalización y
			// desembolso No autorizado
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_NO_AUTORIZADO);
			Map<String, Object> parametrosProceso = new HashMap<>();
			parametrosProceso.put(AUTORIZAR_DESEMBOLSO, Boolean.FALSE);
			if (solicitudLegalizacionDesembolso.getIdInstanciaProceso() == null) {
				ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
						solicitudLegalizacionDesembolso.getIdSolicitud());
				consultarSolicitudGlobal.execute();
				SolicitudModeloDTO solicitud = consultarSolicitudGlobal.getResult();
				/* Se finaliza la tarea. */
				this.terminarTarea(null, parametrosProceso, Long.valueOf(solicitud.getIdInstanciaProceso()));
			} else {
				/* Se finaliza la tarea. */
				this.terminarTarea(null, parametrosProceso,
						Long.valueOf(solicitudLegalizacionDesembolso.getIdInstanciaProceso()));
			}

			logger.debug("Finaliza el servicio noAutorizarDesembolso");
			return solicitudLegalizacionDesembolso;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio noAutorizarDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#
	 * consultarOferentePorTipoNumeroIdORazonSocial(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OferenteModeloDTO> consultarOferentePorTipoNumeroIdORazonSocial(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String razonSocialNombre) {
		try {
			logger.debug("Inicia el servicio consultarOferentePorTipoNumeroIdORazonSocial");
			// Se valida que el oferente no este creado
			List<OferenteModeloDTO> listOferentes = null;
			if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
				OferenteModeloDTO oferenteDTO = consultarOferente(tipoIdentificacion, numeroIdentificacion);
				if (oferenteDTO != null && (oferenteDTO.getPersona() != null || oferenteDTO.getEmpresa() != null)) {
				    listOferentes = new ArrayList<>();
				    listOferentes.add(oferenteDTO);
				}
			} else if (razonSocialNombre != null && !razonSocialNombre.isEmpty()) {
			    listOferentes = consultarOferentePorRazonSocial(razonSocialNombre);
			} 
                        
			logger.debug("Finaliza el servicio consultarOferentePorTipoNumeroIdORazonSocial");
			return listOferentes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarOferentePorTipoNumeroIdORazonSocial", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

        /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#
	 * consultarProveedorPorTipoNumeroIdORazonSocial(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<ProveedorModeloDTO> consultarProveedorPorTipoNumeroIdORazonSocial(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String razonSocialNombre) {
		try {
			logger.debug("Inicia el servicio consultarProveedorPorTipoNumeroIdORazonSocial");
			// Se valida que el oferente no este creado
			List<ProveedorModeloDTO> listProveedor = null;
			if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
				ProveedorModeloDTO proveedorDTO = consultarProveedor(tipoIdentificacion, numeroIdentificacion);
				if (proveedorDTO != null && (proveedorDTO.getPersona() != null || proveedorDTO.getEmpresa() != null)) {
				    listProveedor = new ArrayList<>();
				    listProveedor.add(proveedorDTO);
				}
			} else if (razonSocialNombre != null && !razonSocialNombre.isEmpty()) {
			    listProveedor = consultarProveedorPorRazonSocial(razonSocialNombre);
			} 
                        
			logger.debug("Finaliza el servicio consultarOferentePorTipoNumeroIdORazonSocial");
			return listProveedor;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarOferentePorTipoNumeroIdORazonSocial", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}
        
        
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#registrarResultadoEjecucionDesembolso(
	 * com.asopagos .dto.fovis.SolicitudLegalizacionDesembolsoDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO registrarResultadoEjecucionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.debug("Inicia el servicio registrarResultadoEjecucionDesembolso");

			// Se consulta la postulacion
			PostulacionFOVISModeloDTO postulacionFOVISDTO = consultarPostulacionFOVIS(
			        solicitudLegalizacionDesembolso.getDatosPostulacionFovis().getPostulacion().getIdPostulacion());

			// Si el desembolso fue exitoso, el estado de la solicitud es
			// Desembolso exitoso reportado por área financiera
			if (solicitudLegalizacionDesembolso.getDesembolsoExitoso()) {
				// Se consultan los anticipos desembolsados
				List<AnticipoLegalizacionDesembolsoDTO> listaAnticipos = consultarAnticiposDesembolsados(
						postulacionFOVISDTO.getIdPostulacion());
				// Se suman los valores desembolsados
				BigDecimal valorDesembolsado = BigDecimal.ZERO;
				if (listaAnticipos != null && !listaAnticipos.isEmpty()) {
					for (AnticipoLegalizacionDesembolsoDTO anticipoDTO : listaAnticipos) {
						valorDesembolsado = valorDesembolsado.add(anticipoDTO.getValorDesembolsado());
					}
				}
				// El sistema le asigna el estado a la solicitud Desembolso
				// exitoso reportado por área financiera
				actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
						EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA);

				LegalizacionDesembolsoModeloDTO legalizacion = solicitudLegalizacionDesembolso
						.getLegalizacionDesembolso();
				// Se actualiza la informacion de la legalizacion
				legalizacion = this.crearActualizarLegalizacionDesembolso(legalizacion);
				
				solicitudLegalizacionDesembolso.setLegalizacionDesembolso(legalizacion);

				// Se suma el valor del desembolso actual
				if (solicitudLegalizacionDesembolso.getLegalizacionDesembolso() != null
						&& solicitudLegalizacionDesembolso.getLegalizacionDesembolso().getValorADesembolsar() != null) {
					valorDesembolsado = valorDesembolsado
							.add(solicitudLegalizacionDesembolso.getLegalizacionDesembolso().getValorADesembolsar());				
					
				}

				BigDecimal valorPorDesembolsar = postulacionFOVISDTO.getValorAsignadoSFV();
				BigDecimal validacionCero = BigDecimal.ZERO;
				if (postulacionFOVISDTO.getValorSFVAjustado() != null && postulacionFOVISDTO.getValorSFVAjustado() == validacionCero ) {
				    valorPorDesembolsar = postulacionFOVISDTO.getValorSFVAjustado();			    
				    
				} 
				else if(postulacionFOVISDTO.getValorAjusteIPCSFV() != null && postulacionFOVISDTO.getValorAjusteIPCSFV() == validacionCero) {
				    valorPorDesembolsar = postulacionFOVISDTO.getValorAjusteIPCSFV();
				    
				}
				
				if (!FormaPagoEnum.PAGO_CONTRA_ESCRITURA.equals(legalizacion.getFormaPago())
						&& valorDesembolsado.compareTo(valorPorDesembolsar) < 0) {
					// El sistema le asigna el estado a la solicitud
					// Legalización y desembolso confirmado
					actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
							EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CONFIRMADO);
					// El sistema cambia el estado del hogar a Subsidio con
					// anticipo desembolsado.
					cambiarEstadoHogar(postulacionFOVISDTO.getIdPostulacion(), EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO);	
					

				} else if (valorPorDesembolsar.compareTo(valorDesembolsado) == 0 || valorDesembolsado == postulacionFOVISDTO.getValorAsignadoSFV()) {
					// El sistema le asigna el estado a la solicitud
					// Legalización y desembolso confirmado					
					
					actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
							EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CONFIRMADO);
					// El sistema cambia el estado del hogar a Subsidio con
					// anticipo desembolsado.
					cambiarEstadoHogar(postulacionFOVISDTO.getIdPostulacion(), EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO);
				}


				this.guardarDatosTemporal(solicitudLegalizacionDesembolso);

				// Adicionalmente se crea una tarea en la bandeja de trabajo del
				// usuario con rol back FOVIS,
				// al abrir esta tarea se ejecuta la HU-324-ZZZ Finalizar
				// tramite desembolso FOVIS y notificar a oferente.
				Map<String, Object> params = new HashMap<>();
				params.put(DESEMBOLSO_CONFIRMADO, Boolean.TRUE);
				terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
						solicitudLegalizacionDesembolso.getIdInstanciaProceso());
			} else {
				// Si la solicitud se encuentra entre los nueve intentos
				// permitidos
				SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
						.consultarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud());
				if ((solicitudDTO.getCantidadReintentos() == null ) || (solicitudDTO.getCantidadReintentos() != null && solicitudDTO.getCantidadReintentos() <= 10)) {
					// Si el desembolso no fue exitoso, el estado de la
					// solicitud es Desembolso no exitoso reportado por área
					// financiera.
					actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
							EstadoSolicitudLegalizacionDesembolsoEnum.DESEMBOLSO_NO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA);
					// Se crea una tarea en la bandeja de trabajo al usuario con
					// rol coordinador FOVIS, al abrir esta tarea se ejecuta la
					// HU-324-074 Actualizar
					// datos de pago para reintentar transacción de desembolso
					// FOVIS.
					Map<String, Object> params = new HashMap<>();
					params.put(DESEMBOLSO_CONFIRMADO, Boolean.FALSE);
					terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
							solicitudLegalizacionDesembolso.getIdInstanciaProceso());
				}
			}

			logger.debug("Finaliza el servicio registrarResultadoEjecucionDesembolso");
			return solicitudLegalizacionDesembolso;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio registrarResultadoEjecucionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#finalizarTramiteDesembolso(com.asopagos
	 * .dto.fovis. SolicitudLegalizacionDesembolsoDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public SolicitudLegalizacionDesembolsoDTO finalizarTramiteDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, UserDTO userDTO) {
		try {
			logger.debug("Inicia el servicio finalizarTramiteDesembolso");
			LegalizacionDesembolsoModeloDTO legalizacion = solicitudLegalizacionDesembolso.getLegalizacionDesembolso();
			this.crearActualizarLegalizacionDesembolso(legalizacion);
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
			
			solicitudLegalizacionDesembolso.setLegalizacionDesembolso(legalizacion);
			this.guardarDatosTemporal(solicitudLegalizacionDesembolso);
			Map<String, Object> params = new HashMap<>();
			terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
					solicitudLegalizacionDesembolso.getIdInstanciaProceso() != null
							? new Long(solicitudLegalizacionDesembolso.getIdInstanciaProceso()) : null);

			logger.debug("Finaliza el servicio finalizarTramiteDesembolso");
			return solicitudLegalizacionDesembolso;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio finalizarTramiteDesembolsoz", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#rechazarSolicitudLegalizacionDesembolso
	 * (com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO)
	 */
	@Override
	public void rechazarSolicitudLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso) {
		try {
			// Si el desembolso no fue exitoso en el décimo intento
			logger.debug("Inicia el servicio rechazarSolicitudLegalizacionDesembolso");
			// El sistema cambia el estado de la solicitud a Legalización y
			// desembolso a Rechazado
			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_RECHAZADO);

			actualizarEstadoSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud(),
					EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
			// Se crea una tarea en la bandeja de trabajo al usuario con
			// rol coordinador FOVIS, al abrir esta tarea se ejecuta la
			// HU-324-074 Actualizar datos de pago para reintentar transacción
			// de desembolso FOVIS.
			Map<String, Object> params = new HashMap<>();
			params.put(COORDINADOR_FOVIS, solicitudLegalizacionDesembolso.getUsuario());
			params.put(RECHAZO_REINTENTO, Boolean.TRUE);
			if (solicitudLegalizacionDesembolso.getIdInstanciaProceso() == null) {
				ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
						solicitudLegalizacionDesembolso.getIdSolicitud());
				consultarSolicitudGlobal.execute();
				SolicitudModeloDTO solicitud = consultarSolicitudGlobal.getResult();
				Long idTareaActiva = consultarTareaActiva(Long.getLong(solicitud.getIdInstanciaProceso()));
				terminarTarea(idTareaActiva, params, solicitudLegalizacionDesembolso.getIdInstanciaProceso());
			} else {
				terminarTarea(solicitudLegalizacionDesembolso.getIdTarea(), params,
						solicitudLegalizacionDesembolso.getIdInstanciaProceso());
			}
			logger.debug("Finaliza el servicio rechazarSolicitudLegalizacionDesembolso");

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio rechazarSolicitudLegalizacionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.legalizacionfovis.composite.service.
	 * LegalizacionFovisCompositeService#cerrarSolicitudLegalizacionDesembolso(
	 * com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO)
	 */
	@Override
	public void cerrarSolicitudLegalizacionDesembolso(
			SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso) {
		try {
			// Si el desembolso no fue exitoso despues décimo intento
			logger.debug("Inicia el servicio cerrarSolicitudLegalizacionDesembolso");
			// El sistema registra la información de la solicitud en la base de
			// datos. Se guarda la fecha y hora de la operación
			SolicitudLegalizacionDesembolsoModeloDTO solicitudDTO = this
					.consultarSolicitudLegalizacionDesembolso(solicitudLegalizacionDesembolso.getIdSolicitud());
			solicitudDTO.setFechaOperacion(new Date().getTime());
			// El sistema cambia el estado de la solicitud a Legalización y
			// desembolso a Cerrada
			solicitudDTO
					.setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
			crearActualizarSolicitudLegalizacionDesembolso(solicitudDTO);
			Map<String, Object> parametrosProceso = new HashMap<>();
			parametrosProceso.put(AUTORIZAR_DESEMBOLSO, Boolean.FALSE);
			if (solicitudLegalizacionDesembolso.getIdInstanciaProceso() == null) {
				ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(
						solicitudLegalizacionDesembolso.getIdSolicitud());
				consultarSolicitudGlobal.execute();
				SolicitudModeloDTO solicitud = consultarSolicitudGlobal.getResult();
				/* Se finaliza la tarea. */
				this.terminarTarea(null, parametrosProceso, Long.valueOf(solicitud.getIdInstanciaProceso()));
			} else {
				/* Se finaliza la tarea. */
				this.terminarTarea(null, parametrosProceso,
						Long.valueOf(solicitudLegalizacionDesembolso.getIdInstanciaProceso()));
			}
			logger.debug("Finaliza el servicio cerrarSolicitudLegalizacionDesembolso");

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio cerrarSolicitudLegalizacionDesembolso", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	}

	/**
	 * Método que invoca el servicio de creación o actualización de un registro
	 * en la tabla <code>Ubicacion</code>
	 * 
	 * @param ubicacionDTO
	 *            La información del registro a actualizar
	 * @return El registro actualizado
	 */
	private UbicacionModeloDTO crearActualizarUbicacion(UbicacionModeloDTO ubicacionDTO) {
		logger.debug("Inicio de método crearActualizarUbicacion");
		CrearActualizarUbicacion service = new CrearActualizarUbicacion(ubicacionDTO);
		service.execute();
		logger.debug("Fin de método crearActualizarUbicacion");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de consulta los documentos de soporte de un
	 * oferente
	 * 
	 * @param idOferente
	 *            identificador del oferente
	 * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la
	 *         información de los documentos del oferente
	 */
	private List<DocumentoSoporteModeloDTO> consultarDocumentosSoportePorIdOferente(Long idOferente) {
		logger.debug("Inicia el método consultarDocumentosSoportePorIdOferente");
		ConsultarDocumentosSoporteOferentePorIdOferente service = new ConsultarDocumentosSoporteOferentePorIdOferente(
				idOferente);
		service.execute();
		logger.debug("Finaliza el método consultarDocumentosSoportePorIdOferente");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de consulta los documentos de soporte de un
	 * oferente
	 * 
	 * @param idOferente
	 *            identificador del oferente
	 * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la
	 *         información de los documentos del oferente
	 */
	private List<DocumentoSoporteModeloDTO> consultarDocumentosSoporteProveedorPorIdProveedor(Long idOferente) {
		logger.debug("Inicia el método consultarDocumentosSoporteProveedorPorIdProveedor");
		ConsultarDocumentosSoporteProveedorPorIdProveedor service = new ConsultarDocumentosSoporteProveedorPorIdProveedor(
				idOferente);
		service.execute();
		logger.debug("Finaliza el método consultarDocumentosSoporteProveedorPorIdProveedor");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de consulta los documentos de soporte de un
	 * proyecto de vivienda
	 * 
	 * @param idOferente
	 *            identificador del oferente
	 * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la
	 *         información de los documentos del proyecto de vivienda
	 */
	private List<DocumentoSoporteModeloDTO> consultarDocumentosSoportePorIdProyecto(Long idProyectoVivienda) {
		logger.debug("Inicia el método consultarDocumentosSoportePorIdProyecto");
		ConsultarDocumentosSoporteProyectoPorIdProyecto service = new ConsultarDocumentosSoporteProyectoPorIdProyecto(
				idProyectoVivienda);
		service.execute();
		logger.debug("Finaliza el método consultarDocumentosSoportePorIdProyecto");
		return service.getResult();
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
	private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
		logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
		EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
		ejecutarAsignacion.execute();
		logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
		String nombreUsuarioCaja = (String) ejecutarAsignacion.getResult();
		return nombreUsuarioCaja;
	}

	/**
	 * Método que hace la peticion REST al servicio de consultar un usuario de
	 * caja de compensacion
	 * 
	 * @param nombreUsuarioCaja
	 *            <code>String</code> El nombre de usuario del funcionario de la
	 *            caja que realiza la consulta
	 * 
	 * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
	 *         usuario
	 */
	private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
		logger.debug("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
				nombreUsuarioCaja, null, null, false);
		obtenerDatosUsuariosCajaCompensacionService.execute();
		usuarioDTO = (UsuarioDTO) obtenerDatosUsuariosCajaCompensacionService.getResult();
		logger.debug("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
		return usuarioDTO;
	}

	/**
	 * Método que invoca el servicio que consulta los anticipos desembolsados
	 * 
	 * @param idJefeHogar
	 *            identificador del jefe de hogar
	 * @return Objeo <code>PersonaModeloDTO</code> DTO con la información de la
	 *         persona
	 */
	private List<AnticipoLegalizacionDesembolsoDTO> consultarAnticiposDesembolsados(Long idPostulacionFovis) {
		logger.debug("Inicia el método consultarAnticiposDesembolsados");
		ConsultarAnticiposDesembolsados service = new ConsultarAnticiposDesembolsados(idPostulacionFovis);
		service.execute();
		logger.debug("Finaliza el método consultarAnticiposDesembolsados");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio de consulta de un oferente
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del oferente
	 * @param numeroIdentificacion
	 *            Número de identificación del oferente
	 * @return Objeo <code>OferenteModeloDTO</code> con la información del
	 *         oferente
	 */
	private List<OferenteModeloDTO> consultarOferentePorRazonSocial(String razonSocialNombre) {
		logger.debug("Inicia el método consultarOferentePorRazonSocial");
		ConsultarOferentePorRazonSocial service = new ConsultarOferentePorRazonSocial(razonSocialNombre);
		service.execute();
		logger.debug("Finaliza el método consultarOferentePorRazonSocial");
		return service.getResult();
	}
        
        /**
	 * Método que invoca el servicio de consulta de un Proveedor
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del Proveedor
	 * @param numeroIdentificacion
	 *            Número de identificación del Proveedor
	 * @return Objeo <code>ProveedorModeloDTO</code> con la información del
	 *         Proveedor
	 */
	private List<ProveedorModeloDTO> consultarProveedorPorRazonSocial(String razonSocialNombre) {
		logger.debug("Inicia el método consultarOferentePorRazonSocial");
		ConsultarProveedorPorRazonSocial service = new ConsultarProveedorPorRazonSocial(razonSocialNombre);
		service.execute();
		logger.debug("Finaliza el método consultarOferentePorRazonSocial");
		return service.getResult();
	}

}
