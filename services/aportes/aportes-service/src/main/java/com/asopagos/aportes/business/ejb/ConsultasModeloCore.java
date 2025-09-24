package com.asopagos.aportes.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.asopagos.aportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.constants.NamedQueriesConstants;
import com.asopagos.aportes.dto.ConsultaAporteRelacionadoDTO;
import com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosAfiliadoServiciosDTO;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO;
import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.aportes.dto.DetalleCorreccionAportanteVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteNuevoVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteVista360DTO;
import com.asopagos.aportes.dto.DetalleDatosAportanteDTO;
import com.asopagos.aportes.dto.DetalleDatosCotizanteDTO;
import com.asopagos.aportes.dto.DetalleDevolucionCotizanteDTO;
import com.asopagos.aportes.dto.DetalleDevolucionVista360DTO;
import com.asopagos.aportes.dto.DetalleRegistroAportanteDTO;
import com.asopagos.aportes.dto.DetalleRegistroCotizanteDTO;
import com.asopagos.aportes.dto.DevolucionVistasDTO;
import com.asopagos.aportes.dto.HistoricoNovedadesDTO;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDetalladoDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.aportes.dto.ResultadoDetalleRegistroDTO;
import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesDatosAportesPila;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.*;
import com.asopagos.dto.aportes.AportePilaDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.Correccion;
import com.asopagos.entidades.ccf.aportes.DevolucionAporte;
import com.asopagos.entidades.ccf.aportes.DevolucionAporteDetalle;
import com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.aportes.SolicitudAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudCierreRecaudo;
import com.asopagos.entidades.ccf.aportes.SolicitudCorreccionAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudDevolucionAporte;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.MedioConsignacion;
import com.asopagos.entidades.ccf.personas.MedioTransferencia;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoInteresEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;

import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.listas.clients.ConsultarListaValores;
/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {

	/**
	 * Constantes para nombramiento de parámetros de consulta
	 */
	private static final String NOVEDADES_RETIRO = "novedadesRetiro";
	private static final String ESTADO_SOLICITUD = "estadoSolicitud";
	private static final String ID_PERSONAS = "idPersonas";
	private static final String ID_APORTE = "idAporte";
	private static final String MOVIMIENTOS = "movimientos";
	private static final String ID_APORTE_DETALLADO = "idAporteDetallado";
	private static final String TIPO_SOLICITANTE = "tipoSolicitante";
	private static final String ID_APORTE_GENERAL = "idAporteGeneral";
	private static final String IDS_APORTE_GENERAL = "idsAporteGeneral";
	private static final String ESTADO_REGISTRO = "estadoRegistro";
	private static final String ESTADO_APORTE = "estadoAporte";
	private static final String FORMA_RECONOCIMIENTO = "formaReconocimiento";
	private static final String TIPO_ENTIDAD = "tipoEntidad";
	private static final String ID_PERSONA = "idPersona";
	private static final String ID_EMPRESA = "idEmpresa";
	private static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
	private static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
	private static final String FECHA_FIN = "fechaFin";
	private static final String FECHA_INICIO = "fechaInicio";
	private static final String ID_SOLICITUD = "idSolicitud";
	private static final String ID_REGISTRO_GENERAL = "idRegistroGeneral";
	private static final String ID_REGISTRO_DETALLADO = "idRegistroDetallado";
	private static final String CODIGO_MUNICIPIO = "codigoMunicipio";
	private static final String ID_AFILIADOS = "idAfiliados";
	private static final String CODIGO_OI = "codigoOI";
	private static final String LLAVES_SUCURSALES = "llavesSucursales";
	private static final String ID_EMPRESAS = "idEmpresas";
	private static final String MODALIDAD_RECAUDO = "modalidadRecaudo";
	private static final String TIENE_PERSONAS = "tienePersonas";
	private static final String CONSULTAR_LEGALIZACIONES = "legalizados";
	private static final String CONSULTAR_ORIGINAL = "original";
	private static final String FECHA_APORTE = "fechaAporte";
	private static final String PERIODO_APORTE = "periodoAporte";
	private static final String IDS_APORTE_DETALLADO = "idsAporteDetallado";

	private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "core_PU")
	private EntityManager entityManagerCore;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearSolicitudAporte(com.asopagos.dto.modelo.SolicitudAporteModeloDTO)
	 */
	@Override
	public void crearSolicitudAporte(SolicitudAporte solicitudAporte) {
		logger.info("Inicia crearSolicitudAporte(SolicitudAporte): ++++");
		try {
			entityManagerCore.persist(solicitudAporte);
		} catch (Exception e) {
			logger.error("Ocurrió un error en crearSolicitudAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarEstadoSolicitud(com.asopagos.entidades.ccf.aportes.SolicitudAporte)
	 */
	@Override
	public void actualizarSolicitud(SolicitudAporte solicitudAporte) {
		logger.debug("Inicia actualizarSolicitud(SolicitudAporte) ");
		try {
			entityManagerCore.merge(solicitudAporte);
		} catch (Exception e) {
			logger.error("Ocurrio un error en actualizarSolicitud:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudAporte(java.lang.Long)
	 */
	@Override
	public SolicitudAporte consultarSolicitudAporte(Long idSolicitud) {
		logger.debug("Inicia consultarSolicitudAporte(Long) ");
		try {
			SolicitudAporte solicitudAporte = (SolicitudAporte) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_ID)
					.setParameter(ID_SOLICITUD, idSolicitud).getSingleResult();

			logger.debug("Fin de método consultarSolicitudAporte(Long)");
			return solicitudAporte;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarOperadorInformacionCodigo(java.lang.Long)
	 */
	@Override
	public Long consultarOperadorInformacionCodigo(String codigo) {
		logger.debug("Inicia consultarOperadorInformacionCodigo(String) ");
		try {
			Long idOperadorInformacion = (Long) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADOR_INFORMACION_POR_CODIGO)
					.setParameter(CODIGO_OI, codigo).getSingleResult();
			logger.debug("Finaliza consultarOperadorInformacionCodigo(String) ");
			return idOperadorInformacion;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarOperadorInformacionCodigo: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearAporteGeneral(com.asopagos.entidades.ccf.aportes.AporteGeneral)
	 */
	@Override
	public void crearAporteGeneral(AporteGeneral aporteGeneral) {
		logger.debug("Inicia ConsultasModeloCore.crearAporteGeneral(AporteGeneral) ");
		try {
			entityManagerCore.persist(aporteGeneral);
		} catch (Exception e) {
			logger.error("Finaliza ConsultasModeloCore.crearAporteGeneral(AporteGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug("Finaliza ConsultasModeloCore.crearAporteGeneral(AporteGeneral)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarAporteDetallado(com.asopagos.entidades.ccf.aportes.AporteDetallado)
	 */
	@Override
	public void actualizarAporteDetallado(AporteDetallado aporteDetallado) {
		logger.debug("Inicia ConsultasModeloCore.actualizarAporteDetallado(AporteDetallado) ");
		try {
			logger.info("Actualizando el aporte detallado relacionado al registro detallado <<"
					+ aporteDetallado.getIdRegistroDetallado() + ">>, " + "asociado al aporte general <<"
					+ aporteDetallado.getIdAporteGeneral() + ">>");
			entityManagerCore.merge(aporteDetallado);
			logger.info("Aporte detallado actualizado correctamente");
		} catch (Exception e) {
			logger.debug("Finaliza ConsultasModeloCore.actualizarAporteDetallado(AporteDetallado)");
			logger.error("Ocurrió un error en actualizarAporteDetallado :", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza ConsultasModeloCore.actualizarAporteDetallado(AporteDetallado)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearAporteDetallado(com.asopagos.entidades.ccf.aportes.AporteDetallado)
	 */
	@Override
	public void crearAporteDetallado(AporteDetallado aporteDetallado) {
		logger.debug("Inicia ConsultasModeloCore.crearAporteDetallado(AporteDetallado) ");
		try {
			entityManagerCore.persist(aporteDetallado);
		} catch (Exception e) {
			logger.error("Finaliza ConsultasModeloCore.crearAporteDetallado(AporteDetallado)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug("Finaliza ConsultasModeloCore.crearAporteDetallado(AporteDetallado)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearInfoFaltante(com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante)
	 */
	@Override
	public void crearInfoFaltante(InformacionFaltanteAportante infoFaltante) {
		logger.debug("Inicia crearInfoFaltante(InformacionFaltanteAportante) ");
		try {
			entityManagerCore.persist(infoFaltante);
		} catch (Exception e) {
			logger.debug("Finaliza crearInfoFaltante(InformacionFaltanteAportante)");
			logger.error("Ocurrió un error en crearInfoFaltante", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarInfoFaltante(com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante)
	 */
	@Override
	public void actualizarInfoFaltante(InformacionFaltanteAportante infoFaltante) {
		logger.debug("Inicia crearInfoFaltante(InformacionFaltanteAportante) ");
		try {
			entityManagerCore.merge(infoFaltante);
		} catch (Exception e) {
			logger.debug("Finaliza crearInfoFaltante(InformacionFaltanteAportante)");
			logger.error("Ocurrió un error en crearInfoFaltante", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public String consultarTarifa(String registroGeneral) {
		try {
			Object tarifa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_REGISTRO_GENERAL_TARIFA)
					.setParameter("idRegistroGeneral", registroGeneral).getResultList().get(0);
			return tarifa.toString();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;

		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarTarifaAportante(String registroGeneral, String numeroIdentificacion, String tipoIdentificacion) {
		List<Object> tarifas = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_REGISTRO_GENERAL_TARIFA_APORTANTE)
				.setParameter("idRegistroGeneral", registroGeneral)
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.getResultList();
		if (tarifas == null || tarifas.isEmpty()) return null;
		return tarifas.get(0).toString();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarInformacionFaltanteId(java.lang.Long)
	 */
	@Override
	public List<InformacionFaltanteAportante> consultarInformacionFaltanteId(Long idSolicitud) {
		logger.debug("Inicia consultarInformacionFaltanteId(Long) ");
		try {
			List<InformacionFaltanteAportante> infosFaltantesAportante = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_FALTANTE_ID,
							InformacionFaltanteAportante.class)
					.setParameter(ID_SOLICITUD, idSolicitud).getResultList();
			logger.debug("Finaliza consultarInformacionFaltanteId(Long) ");
			return infosFaltantesAportante;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarInformacionFaltanteId: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#obtenerCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder obtenerCriteriaBuilder() {
		logger.debug("Inicia obtenerCriteriaBuilder() ");
		try {
			return entityManagerCore.getCriteriaBuilder();
		} catch (Exception e) {
			logger.error("Ocurrio un error al obtenerCriteriaBuilder:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#obtenerListaSolicitudes(javax.persistence.criteria.CriteriaQuery)
	 */
	@Override
	public List<SolicitudAporte> obtenerListaSolicitudes(CriteriaQuery<SolicitudAporte> c) {
		logger.debug("Inicia obtenerListaSolicitudes(CriteriaQuery<SolicitudAporte>) ");
		try {
			return entityManagerCore.createQuery(c).getResultList();
		} catch (Exception e) {
			logger.error("Ocurrió un error obtenerListaSolicitudes", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDepartamentos()
	 */
	@Override
	public List<Departamento> consultarDepartamentos() {
		logger.debug("Inicia consultarDepartamentos() ");
		try {
			List<Departamento> departamentos = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTOS, Departamento.class)
					.getResultList();
			logger.debug("Finaliza consultarDepartamentos() ");
			return departamentos;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarDepartamentos: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarMunicipios()
	 */
	@Override
	public List<Municipio> consultarMunicipios() {
		logger.debug("Inicia consultarMunicipios() ");
		try {
			List<Municipio> municipios = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIOS, Municipio.class).getResultList();
			logger.debug("Finaliza consultarMunicipios() ");
			return municipios;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarMunicipios: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitanteIndependientePensionado(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public SolicitanteDTO consultarSolicitanteIndependientePensionado(
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicia consultarSolicitanteIndependientePensionado(TipoSolicitanteMovimientoAporteEnum,TipoIdentificacionEnum,String)");
		try {
			SolicitanteDTO solicitante = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITANTE_INDEPENDIENTE_PENSIONADO,
							SolicitanteDTO.class)
					.setParameter(TIPO_SOLICITANTE, tipoSolicitante)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).setMaxResults(1).getSingleResult();
			List<ConsultarEstadoDTO> requestConsultaEstado = new ArrayList<>();
			ConsultarEstadoDTO paramEstados = new ConsultarEstadoDTO();
			List<EstadoDTO> responseListEstadoEmpleador = null;
			paramEstados.setEntityManager(entityManagerCore);
			paramEstados.setNumeroIdentificacion(numeroIdentificacion);
			paramEstados.setTipoIdentificacion(tipoIdentificacion);
			paramEstados.setTipoPersona(ConstantesComunes.PERSONAS);
			requestConsultaEstado.add(paramEstados);
			responseListEstadoEmpleador = EstadosUtils.consultarEstadoCaja(requestConsultaEstado);
			if (responseListEstadoEmpleador != null && !responseListEstadoEmpleador.isEmpty()) {
				solicitante.setEstado(responseListEstadoEmpleador.get(0).getEstado());
			}

			logger.debug(
					"Finaliza consultarSolicitanteIndependientePensionado(TipoSolicitanteMovimientoAporteEnum,TipoIdentificacionEnum,String)");
			return solicitante;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitanteIndependientePensionado: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitanteEmpleador(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public SolicitanteDTO consultarSolicitanteEmpleador(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug(
				"Inicia consultarSolicitanteEmpleador(TipoSolicitanteMovimientoAporteEnum,TipoIdentificacionEnum,String)");
		try {
			SolicitanteDTO solicitante = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITANTE_EMPLEADOR, SolicitanteDTO.class)
					.setParameter(TIPO_SOLICITANTE, tipoSolicitante)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).setMaxResults(1).getSingleResult();
			List<ConsultarEstadoDTO> requestConsultaEstado = new ArrayList<>();
			ConsultarEstadoDTO paramEstados = new ConsultarEstadoDTO();
			List<EstadoDTO> responseListEstadoEmpleador = null;
			paramEstados.setEntityManager(entityManagerCore);
			paramEstados.setNumeroIdentificacion(numeroIdentificacion);
			paramEstados.setTipoIdentificacion(tipoIdentificacion);
			paramEstados.setTipoPersona(ConstantesComunes.EMPLEADORES);
			requestConsultaEstado.add(paramEstados);
			responseListEstadoEmpleador = EstadosUtils.consultarEstadoCaja(requestConsultaEstado);
			if (responseListEstadoEmpleador != null && !responseListEstadoEmpleador.isEmpty()) {
				solicitante.setEstado(responseListEstadoEmpleador.get(0).getEstado());
			}
			logger.debug(
					"Finaliza consultarSolicitanteEmpleador(TipoSolicitanteMovimientoAporteEnum,TipoIdentificacionEnum,String)");
			return solicitante;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitanteEmpleador: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonaSolicitanteAporteGeneral(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<SolicitanteDTO> consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum,String)");
		try {
			List<SolicitanteDTO> solicitantes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_SOLICITANTE_APORTE_GENERAL,
							SolicitanteDTO.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getResultList();
			logger.debug("Finaliza consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum,String)");
			return solicitantes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonaSolicitanteAporteGeneral: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonaEmpresaSolicitanteAporteGeneral(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<SolicitanteDTO> consultarPersonaEmpresaSolicitanteAporteGeneral(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia consultarPersonaEmpresaSolicitanteAporteGeneral(TipoIdentificacionEnum,String)");
		try {
			List<SolicitanteDTO> solicitantes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EMPRESA_SOLICITANTE_APORTE_GENERAL,
							SolicitanteDTO.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getResultList();
			logger.debug("Finaliza consultarPersonaEmpresaSolicitanteAporteGeneral(TipoIdentificacionEnum,String)");
			return solicitantes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonaEmpresaSolicitanteAporteGeneral: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarHistoricoNovedades(java.util.Calendar)
	 */
	@Override
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedades(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia consultarHistoricoNovedades");
		try {
			List<HistoricoNovedadesDTO> novedades = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES, HistoricoNovedadesDTO.class)
					.setParameter(FECHA_INICIO, fechaInicio).setParameter(FECHA_FIN, fechaFin)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getResultList();
			novedades.addAll(
					consultarHistoricoNovedadesRetiro(fechaInicio, fechaFin, tipoIdentificacion, numeroIdentificacion));
			logger.debug("Finaliza consultarHistoricoNovedades)");
			return novedades;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoNovedades: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#buscarCamposArchivo(java.lang.Long)
	 */
	@Override
	public List<DefinicionCamposCargaDTO> buscarCamposArchivo(Long fileLoadedId) {
		logger.debug("Inicia buscarCamposArchivo(Long)");
		try {
			List<DefinicionCamposCargaDTO> campos = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CAMPOS_ARCHIVO, DefinicionCamposCargaDTO.class)
					.setParameter("idFileDefinition", fileLoadedId).getResultList();
			logger.debug("Finaliza buscarCamposArchivo(Long)");
			return campos;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en buscarCamposArchivo: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPlanilla(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ConsultaPlanillaResultDTO consultarPlanilla(Long idPlanilla) {
		String firmaMetodo = "consultarPlanilla(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		ConsultaPlanillaResultDTO resultado = new ConsultaPlanillaResultDTO();
		List<Object[]> planillasObject = new ArrayList<>();
		List<AportePilaDTO> planillas = new ArrayList<>();
		Integer cantidadPlanillas = null;

		try {
			// se consulta la cantidad de aportes del registro general
			cantidadPlanillas = (Integer) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_COMPLETA_CUENTA)
					.setParameter("idPlanilla", idPlanilla).getSingleResult();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		try {
			// se agrega la entrada de muestra para los registros de dependientes
			planillasObject.add((Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_COMPLETA)
					.setParameter("idPlanilla", idPlanilla)
					.setParameter("tipoCotizante", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name()).setMaxResults(1)
					.getSingleResult());
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: sin registros para dependientes");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		try {
			// se agregan las entradas de independientes
			List<Object[]> planillasIndependiente = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_COMPLETA)
					.setParameter("idPlanilla", idPlanilla)
					.setParameter("tipoCotizante", TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name()).getResultList();
			if (!planillasIndependiente.isEmpty()) {
				planillasObject.addAll(planillasIndependiente);
			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: sin registros para independientes");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		try {
			// se agregan las entradas de pensionados
			List<Object[]> planillasPensionado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_COMPLETA)
					.setParameter("idPlanilla", idPlanilla)
					.setParameter("tipoCotizante", TipoAfiliadoEnum.PENSIONADO.name())
					.getResultList();
			if (!planillasPensionado.isEmpty()) {
				planillasObject.addAll(planillasPensionado);
			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: sin registros para pensionados");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		planillas.addAll(convertToListAportePilaDTO(planillasObject));
		resultado.setCantidadAportes(cantidadPlanillas);
		resultado.setListadoAportes(planillas);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultado;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<SolicitudAporte> consultarSolicitudAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarSolicitudAporte(TipoIdentificacionEnum,String)");
		try {
			List<SolicitudAporte> solicitudesAportes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI,
							SolicitudAporte.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
					.setParameter(ESTADO_SOLICITUD, EstadoSolicitudAporteEnum.CERRADA).getResultList();
			logger.debug("Finaliza consultarSolicitudAporte(TipoIdentificacionEnum,String)");
			return solicitudesAportes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCotizantesPorIdAporte(com.asopagos.entidades.ccf.personas.Persona)
	 */
	@Override
	public List<Persona> consultarPersonas(Map<String, String> campos, Map<String, Object> valores) {
		try {
			logger.debug("Inicia consultarPersonas(Map<String, String> campos,Map<String, Object> valores)");
			List<Persona> personas = JPAUtils.consultaEntidad(entityManagerCore, Persona.class, campos, valores);
			logger.debug("Finaliza consultarPersonas(Map<String, String> campos,Map<String, Object> valores)");
			return personas;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonas: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCotizantesSinParametros(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<CotizanteDTO> consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas) {
		try {
			logger.debug("Inicia consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas)");

			List<TipoAjusteMovimientoAporteEnum> movimientos = new ArrayList<>();
			movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA);

			List<CotizanteDTO> cotizantesDTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_POR_ID_APORTE, CotizanteDTO.class)
					.setParameter(ID_APORTE, idAporte).setParameter(ID_PERSONAS, idPersonas)
					.setParameter(MOVIMIENTOS, movimientos).getResultList();
			List<CotizanteDTO> cotizantesEmpresaDTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_POR_ID_APORTE_EMPRESA,
							CotizanteDTO.class)
					.setParameter(ID_APORTE, idAporte).setParameter(ID_PERSONAS, idPersonas)
					.setParameter(MOVIMIENTOS, movimientos).getResultList();
			cotizantesDTO.addAll(cotizantesEmpresaDTO);
			logger.debug("Finaliza consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas)");
			return cotizantesDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarCotizantesPorIdAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCotizantesSinParametros(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CotizanteDTO> consultarCotizantesSinParametros(Long idAporte) {
		String firmaMetodo = "ConsultasModeloCore.consultarCotizantesSinParametros(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<CotizanteDTO> result = new ArrayList<>();

		List<String> movimientos = new ArrayList<>();
		movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION.name());
		movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA.name());

		List<Object[]> cotizantesDTO = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_SIN_PARAMETROS)
				.setParameter(ID_APORTE, idAporte).setParameter(MOVIMIENTOS, movimientos).getResultList();

		List<Object[]> cotizantesEmpresaDTO = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_SIN_PARAMETROS_EMPRESA)
				.setParameter(ID_APORTE, idAporte).setParameter(MOVIMIENTOS, movimientos).getResultList();

		for (Object[] objeto : cotizantesDTO) {
			CotizanteDTO cotizante = new CotizanteDTO();

			cotizante.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) objeto[0]));
			cotizante.setNumeroIdentificacion((String) objeto[1]);
			cotizante.setNombreCompletoCotizante((String) objeto[2]);
			cotizante.setTipoAfiliado(TipoAfiliadoEnum.valueOf((String) objeto[3]));

			cotizante.setAportoPorSiMismo(((Integer) objeto[4]) == 1 ? Boolean.TRUE : Boolean.FALSE);

			cotizante.setIdCotizante(((BigInteger) objeto[5]).longValue());
			cotizante.setTieneModificaciones(((Integer) objeto[6]) == 1 ? Boolean.TRUE : Boolean.FALSE);
			cotizante.setAporteObligatorio((BigDecimal) objeto[7]);
			cotizante.setValorMora((BigDecimal) objeto[8]);
			cotizante.setPrimerNombre((String) objeto[9]);
			cotizante.setSegundoNombre((String) objeto[10]);
			cotizante.setPrimerApellido((String) objeto[11]);
			cotizante.setSegundoApellido((String) objeto[12]);
			cotizante.setIdRegistro(((BigInteger) objeto[13]).longValue());
			cotizante.setEstadoAporteCotizante(EstadoAporteEnum.valueOf((String) objeto[14]));

			result.add(cotizante);
		}

		for (Object[] objeto : cotizantesEmpresaDTO) {
			CotizanteDTO cotizante = new CotizanteDTO();

			cotizante.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) objeto[0]));
			cotizante.setNumeroIdentificacion((String) objeto[1]);
			cotizante.setNombreCompletoCotizante((String) objeto[2]);
			cotizante.setTipoAfiliado(TipoAfiliadoEnum.valueOf((String) objeto[3]));

			cotizante.setAportoPorSiMismo(Boolean.FALSE);
			cotizante.setTipoIdentificacionAportante(TipoIdentificacionEnum.valueOf((String) objeto[4]));
			cotizante.setNumeroIdentificacionAportante((String) objeto[5]);
			cotizante.setNombreAportante((String) objeto[6]);

			cotizante.setIdCotizante(((BigInteger) objeto[7]).longValue());
			cotizante.setTieneModificaciones(((Integer) objeto[8]) == 1 ? Boolean.TRUE : Boolean.FALSE);
			cotizante.setAporteObligatorio((BigDecimal) objeto[9]);
			cotizante.setValorMora((BigDecimal) objeto[10]);
			cotizante.setPrimerNombre((String) objeto[11]);
			cotizante.setSegundoNombre((String) objeto[12]);
			cotizante.setPrimerApellido((String) objeto[13]);
			cotizante.setSegundoApellido((String) objeto[14]);
			cotizante.setIdRegistro(((BigInteger) objeto[15]).longValue());
			cotizante.setEstadoAporteCotizante(EstadoAporteEnum.valueOf((String) objeto[16]));

			result.add(cotizante);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#obtenerListaAportes(javax.persistence.criteria.CriteriaQuery)
	 */
	@Override
	public List<AporteGeneral> obtenerListaAportes(CriteriaQuery<AporteGeneral> c) {
		logger.debug("Inicia obtenerListaAportes(CriteriaQuery<AporteGeneral>) ");
		try {
			return entityManagerCore.createQuery(c).getResultList();
		} catch (Exception e) {
			logger.error("Ocurrio un error obtenerListaAportes", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteDetallado(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AporteDetallado consultarAporteDetallado(Long idAporteDetallado) {
		try {
			logger.debug("Inicia método consultarAporteDetallado(Long idAporteDetallado)");
			AporteDetallado aporteDetallado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO, AporteDetallado.class)
					.setParameter(ID_APORTE_DETALLADO, idAporteDetallado).getSingleResult();
			logger.debug("Finaliza método consultarAporteDetallado(Long idAporteDetallado)");
			return aporteDetallado;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetallado(Long idAporteDetallado)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarSolicitudAportePorIdAporte(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudAporte consultarSolicitudAportePorIdAporte(Long idAporteGeneral) {
		try {
			logger.debug("Inicio de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
			AporteGeneral aporteGeneral = consultarAporteGeneral(idAporteGeneral);
 
			SolicitudAporte solicitudAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_ID_APORTE, SolicitudAporte.class)
					.setParameter(ID_REGISTRO_GENERAL, aporteGeneral.getIdRegistroGeneral()).getSingleResult();
			logger.debug("Fin de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
			return solicitudAporte;
		} catch (NoResultException nre) { 
			return null;
		} catch (NonUniqueResultException nrex) {
			try {
			logger.debug("Inicio de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
			AporteGeneral aporteGeneral = consultarAporteGeneral(idAporteGeneral);
 
			List<SolicitudAporte> solicitudAportes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_ID_APORTE, SolicitudAporte.class)
					.setParameter(ID_REGISTRO_GENERAL, aporteGeneral.getIdRegistroGeneral()).getResultList();
			if (solicitudAportes != null && !solicitudAportes.isEmpty() && solicitudAportes.size() > 0) {
				int maxPos = 0;
				Long maxSolicitud = 0L;
				for (int i = 0; i < solicitudAportes.size(); i++) {
					if (solicitudAportes.get(i).getIdSolicitudAporte() > maxSolicitud) {
						maxSolicitud = solicitudAportes.get(i).getIdSolicitudAporte();
						maxPos = i;
					}
				}
				return solicitudAportes.get(maxPos);
			}
			logger.debug("Fin de método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)");
			return null;
			} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitudAportePorIdAporte(Long idAporteGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarNovedadesCotizanteAporte(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idAporteDetallado) {
		try {
			logger.debug("Inicia método consultarNovedadesCotizanteAporte(Long idAporteDetallado)");
			List<Object[]> listaNovedades = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_COTIZANTE_APORTE)
					.setParameter(ID_APORTE_DETALLADO, idAporteDetallado).getResultList();
			List<NovedadCotizanteDTO> listaNovedadesDTO = new ArrayList<>();

			for (Object[] registro : listaNovedades) {
				NovedadCotizanteDTO novedadCotizanteDTO = new NovedadCotizanteDTO();
				novedadCotizanteDTO.convertToDTO(registro);
				listaNovedadesDTO.add(novedadCotizanteDTO);
			}

			logger.debug("Finaliza método consultarNovedadesCotizanteAporte(Long idAporteDetallado)");
			return listaNovedadesDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarNovedadesCotizanteAporte(Long idAporteDetallado)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarSolicitudDevolucionAporte(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudDevolucionAporte consultarSolicitudDevolucionAporte(Long idSolicitudGlobal) {
		try {
			logger.debug("Inicia método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
			SolicitudDevolucionAporte solicitudDevolucionAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_DEVOLUCION_APORTE_ID,
							SolicitudDevolucionAporte.class)
					.setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();
			logger.debug("Finaliza método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
			return solicitudDevolucionAporte;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarSolicitudGlobal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Solicitud consultarSolicitudGlobal(Long idSolicitud) {
		try {
			logger.debug("Inicia método consultarSolicitud(Long idSolicitud)");
			Solicitud solicitud = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD, Solicitud.class)
					.setParameter(ID_SOLICITUD, idSolicitud).getSingleResult();
			logger.debug("Finaliza método consultarSolicitud(Long idSolicitud)");
			return solicitud;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitud(Long idSolicitud)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarSolicitudDevolucionAporte(com.asopagos.entidades.ccf.
	 * aportes.SolicitudDevolucionAporte)
	 */
	@Override
	public Long crearActualizarSolicitudDevolucionAporte(SolicitudDevolucionAporte solicitudDevolucionAporte) {
		try {
			logger.debug(
					"Inicia método crearActualizarSolicitudDevolucionAporte(SolicitudDevolucionAporte solicitudDevolucionAporte)");
			SolicitudDevolucionAporte managed = entityManagerCore.merge(solicitudDevolucionAporte);
			logger.debug(
					"Finaliza método crearActualizarSolicitudDevolucionAporte(SolicitudDevolucionAporte solicitudDevolucionAporte)");
			return managed.getSolicitudGlobal().getIdSolicitud();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarSolicitudDevolucionAporte(SolicitudDevolucionAporte solicitudDevolucionAporte)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarSolicitudGlobal(com.asopagos.entidades.ccf.general.
	 * Solicitud)
	 */
	@Override
	public Long crearActualizarSolicitudGlobal(Solicitud solicitud) {
		try {
			logger.debug("Inicia método actualizarSolicitudGlobal(Solicitud solicitud)");
			Solicitud managed = entityManagerCore.merge(solicitud);
			logger.debug("Finaliza método actualizarSolicitudGlobal(Solicitud solicitud)");
			return managed.getIdSolicitud();
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método actualizarSolicitudGlobal(Solicitud solicitud)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarMovimientoAporte(java.lang.Long)
	 */
	@Override
	public MovimientoAporte consultarMovimientoAporte(Long idMovimientoAporte) {
		logger.debug("Inicia consultarMovimientoAporte(Long)");
		try {
			MovimientoAporte movimientoAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVIMIENTO_APORTE_ID, MovimientoAporte.class)
					.setParameter("idMovimientoAporte", idMovimientoAporte).getSingleResult();
			logger.debug("Finaliza consultarMovimientoAporte(Long)");
			return movimientoAporte;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarMovimientoAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarMovimientoAporte(com.asopagos.entidades.ccf.aportes.MovimientoAporte)
	 */
	@Override
	public void actualizarMovimientoAporte(MovimientoAporte movimientoAporte) {
		logger.debug("Inicia actualizarMovimientoAporte(MovimientoAporte) ");
		try {
			entityManagerCore.merge(movimientoAporte);
		} catch (Exception e) {
			logger.error("Ocurrio en un error en actualizarMovimientoAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearMovimientoAporte(com.asopagos.entidades.ccf.aportes.MovimientoAporte)
	 */
	@Override
	public void crearMovimientoAporte(MovimientoAporte movimientoAporte) {
		logger.debug("Inicia crearMovimientoAporte(MovimientoAporte) ");
		try {
			logger.info("Creando movimiento aporte: " + movimientoAporte.toString());
			entityManagerCore.persist(movimientoAporte);
		} catch (Exception e) {
			logger.error("Ocurrió un error en crearMovimientoAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarAporteDetallado(java.util.List)
	 */
	@Override
	public void actualizarAporteDetallado(List<AporteDetallado> listaAporteDetallado) {
		try {
			logger.debug("Inicia método actualizarAporteDetallado(List<AporteDetallado> listaAporteDetallado)");
			entityManagerCore.merge(listaAporteDetallado);
			logger.debug("Finaliza método actualizarAporteDetallado(List<AporteDetallado> listaAporteDetallado)");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método actualizarAporteDetallado(List<AporteDetallado> listaAporteDetallado)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * actualizarMovimientoAporte(java.util.List)
	 */
	@Override
	public void actualizarMovimientoAporte(List<MovimientoAporte> listaMovimientoAporte) {
		try {
			logger.debug("Inicia método actualizarMovimientoAporte(List<MovimientoAporte> listaMovimientoAporte)");
			entityManagerCore.merge(listaMovimientoAporte);
			logger.debug("Finaliza método actualizarMovimientoAporte(List<MovimientoAporte> listaMovimientoAporte)");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método actualizarMovimientoAporte(List<MovimientoAporte> listaMovimientoAporte)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarDevolucionAporte(com.asopagos.entidades.ccf.aportes.
	 * DevolucionAporte)
	 */
	@Override
	public Long crearActualizarDevolucionAporte(DevolucionAporte devolucionAporte) {
		try {
			logger.debug("Inicia método crearActualizarDevolucionAporte(DevolucionAporte devolucionAporte)");
			DevolucionAporte managed = entityManagerCore.merge(devolucionAporte);
			logger.debug("Finaliza método crearActualizarDevolucionAporte(DevolucionAporte devolucionAporte)");
			return managed.getIdDevolucionAporte();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarDevolucionAporte(DevolucionAporte devolucionAporte)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCuentasAporte(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarCuentasAporte(List<Long> idsAporteGeneral,TipoMovimientoRecaudoAporteEnum tipoRecaudo) {
		logger.info("Inicia consultarCuentasAporte(List<Long>) tipoRecaudo: "+tipoRecaudo);
			List<TipoMovimientoRecaudoAporteEnum> tipos = new ArrayList<TipoMovimientoRecaudoAporteEnum>();
			if (tipoRecaudo == null){
				tipos.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO);
				tipos.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES );
				tipos.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL);
				tipos.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES);
				tipos.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES);
			}else{
				tipos.add(tipoRecaudo);
			}
		return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_APORTE, CuentaAporteDTO.class)
				.setParameter("idsAporteGeneral", idsAporteGeneral)
				.setParameter("tiposMovimiento", tipos).getResultList();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesPorEmpleador(com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum,
	 *      java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AporteDetallado> consultarAportesPorEmpleador(EstadoAfiliadoEnum estadoAfiliado, Long idEmpleador) {
		logger.debug("Inicia método consultarAportesPorEmpleador(EstadoAfiliadoEnum estadoAfiliado, Long idEmpleador)");
		List<AporteDetallado> aportes = null;
		try {
			aportes = (List<AporteDetallado>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_POR_PERSONA_ROLAFILIADO_ESTADO,
							Empleador.class)
					.setParameter("estadoAfiliado", estadoAfiliado).setParameter("idEmpleador", idEmpleador)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarAportesPorEmpleador(EstadoAfiliadoEnum estadoAfiliado, Long idEmpleador)");
			return aportes;
		}
		return aportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesDetalladosPorPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AporteDetallado> consultarAportesDetalladosPorPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicia método consultarAportesDetalladosPorPersona(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
		List<AporteDetallado> aportes = null;
		try {
			aportes = (List<AporteDetallado>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_PERSONA_TIPO_NUMERO_IDENTIFICACION,
							AporteDetallado.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarAportesDetalladosPorPersona(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
			return aportes;
		}
		return aportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesPorAportanteEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetallado> consultarAportesPorAportanteEmpleador(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicia método consultarAportesPorAportanteEmpleador(TipoIdentificacionEnum tipoIdentificacion,	String numeroIdentificacion, Long idEmpleador)");
		List<AporteDetallado> aportes = null;
		try {
			aportes = (List<AporteDetallado>) entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_APORTES_PERSONA_TIPO_NUMERO_IDENTIFICACION_Y_EMPLEADOR,
							AporteDetallado.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarAportesPorAportanteEmpleador(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion, Long idEmpleador)");
			return aportes;
		}
		return aportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Empleador consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug(
				"Inicia método consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		Empleador empleador = null;
		try {
			empleador = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_NUMERO_IDENTIFICACION,
							Empleador.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			return empleador;
		}
		return empleador;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteGeneralPorRegistro(java.lang.Long)
	 */
	@Override
	@Deprecated
	public AporteGeneral consultarAporteGeneralPorRegistro(Long idRegistroGeneral) {
		logger.debug("Inicio de método consultarAporteGeneralPorRegistro(Long idRegistroGeneral)");
		try {
			AporteGeneral aporteGeneral = (AporteGeneral) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_POR_REGISTRO)
					.setParameter(ID_REGISTRO_GENERAL, idRegistroGeneral).getSingleResult();
			logger.debug("Fin de método consultarAporteGeneralPorRegistro(Long idRegistroGeneral)");
			return aporteGeneral;

		} catch (Exception e) {
			logger.error("Ocurrió un error en consultarAporteGeneralPorRegistro", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarMovimientoHistoricos(com.asopagos.aportes.dto.
	 * ConsultaMovimientoIngresosDTO)
	 */
	@Override
	public List<MovimientoIngresosDTO> consultarMovimientoHistoricos(
			ConsultaMovimientoIngresosDTO consultaMovimientosIngresos) {
		try {
			logger.debug(
					"Inicia método consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consultaMovimientosIngresos)");
			Date fechaInicio = new Date(consultaMovimientosIngresos.getFechaInicio());
			Date fechaFin = new Date(consultaMovimientosIngresos.getFechaFin());
			List<String> listaFormaReconocimiento = new ArrayList<String>();
			listaFormaReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
			listaFormaReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
			listaFormaReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());

			List<String> listaEstados = new ArrayList<String>();

			List<String> lstTipoEntidad = new ArrayList();

			for (TipoSolicitanteMovimientoAporteEnum tipoEntidad: consultaMovimientosIngresos.getTipoEntidad()) {
				lstTipoEntidad.add(tipoEntidad.name());
			}

			for (TipoReconocimientoAporteEnum tipoReconocimiento : consultaMovimientosIngresos
					.getTipoReconocimiento()) {
				if (tipoReconocimiento.equals(TipoReconocimientoAporteEnum.RECONOCIMIENTO_INGRESOS)) {
					listaEstados.add(EstadoRegistroAporteEnum.REGISTRADO.name());
				}

				if (tipoReconocimiento.equals(TipoReconocimientoAporteEnum.OTROS_INGRESOS)) {
					listaEstados.add(EstadoRegistroAporteEnum.OTROS_INGRESOS.name());
				}
			}

			List<MovimientoIngresosDTO> movimientosIngresos = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVIMIENTO_HISTORICO, MovimientoIngresosDTO.class)
					.setParameter(TIPO_ENTIDAD, lstTipoEntidad)
					.setParameter(FECHA_INICIO, fechaInicio)
					.setParameter(FECHA_FIN, fechaFin)
					.setParameter(FORMA_RECONOCIMIENTO, listaFormaReconocimiento)
					.setParameter(ESTADO_REGISTRO, listaEstados)
					.setParameter(ESTADO_APORTE, "VIGENTE")
					.getResultList();
			//movimientosIngresos = asignarDatosAportante(movimientosIngresos);

			List<MovimientoIngresosDTO> movimientosIngresosEmpresa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVIMIENTO_HISTORICO_EMPRESA,
							MovimientoIngresosDTO.class)
					.setParameter(TIPO_ENTIDAD, lstTipoEntidad)
					.setParameter(FECHA_INICIO, fechaInicio)
					.setParameter(FECHA_FIN, fechaFin)
					.setParameter(FORMA_RECONOCIMIENTO, listaFormaReconocimiento)
					.setParameter(ESTADO_REGISTRO, listaEstados)
					.setParameter(ESTADO_APORTE, "VIGENTE")
					.getResultList();
			movimientosIngresos.addAll(movimientosIngresosEmpresa);

			//if (movimientosIngresos.size() > 1) {
			//	Collections.sort(movimientosIngresos,
			//			(o1, o2) -> o2.getFechaReconocimiento().compareTo(o1.getFechaReconocimiento()));
			//}

			logger.debug(
					"Finaliza método consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consultaMovimientosIngresos)");
			return movimientosIngresos;
		 } catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consultaMovimientosIngresos)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarMovimientoDetallado(java.lang.Long)
	 */
	@Override
	public List<MovimientoIngresosDetalladoDTO> consultarMovimientoDetallado(Long idAporteGeneral) {
		logger.debug("Inicia consultarMovimientoDetallado(List<Long>) ");
		try {
			List<MovimientoIngresosDetalladoDTO> movimientosDetallados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVIMIENTO_APORTE_DETALLADO,
							MovimientoIngresosDetalladoDTO.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getResultList();
			logger.debug("Finaliza consultarMovimientoDetallado(List<Long>)");
			return movimientosDetallados;
		} catch (Exception e) {
			logger.debug("Finaliza consultarMovimientoDetallado(List<Long>):Error técnico inesperado");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAportesRelacionados(com.asopagos.aportes.dto.
	 * ConsultaAporteRelacionadoDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MovimientoIngresosDTO> consultarAportesRelacionados(
			ConsultaAporteRelacionadoDTO consultaAportesRelacionados) {
		logger.debug("Inicia método consultarAportesRelacionados");
		Date fechaInicio = new Date(consultaAportesRelacionados.getFechaInicio());
		Date fechaFin = new Date(consultaAportesRelacionados.getFechaFin());

		if (consultaAportesRelacionados.getAntiguedadRecaudo() != null
				&& consultaAportesRelacionados.getAntiguedadRecaudo() > consultaAportesRelacionados.getFechaFin()) {
			fechaFin = new Date(consultaAportesRelacionados.getAntiguedadRecaudo());
		}

		List<MovimientoIngresosDTO> movimientosIngresos = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_RELACIONADOS, MovimientoIngresosDTO.class)
				.setParameter(TIPO_ENTIDAD, consultaAportesRelacionados.getTipoEntidad())
				.setParameter(FECHA_INICIO, fechaInicio).setParameter(FECHA_FIN, fechaFin)
				.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.RELACIONADO)
				.setParameter(ESTADO_APORTE, EstadoAporteEnum.VIGENTE).getResultList();
		movimientosIngresos = asignarDatosAportante(movimientosIngresos);

		List<MovimientoIngresosDTO> movimientosIngresosEmpresa = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_RELACIONADOS_EMPRESA,
						MovimientoIngresosDTO.class)
				.setParameter(TIPO_ENTIDAD, consultaAportesRelacionados.getTipoEntidad())
				.setParameter(FECHA_INICIO, fechaInicio).setParameter(FECHA_FIN, fechaFin)
				.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.RELACIONADO)
				.setParameter(ESTADO_APORTE, EstadoAporteEnum.VIGENTE).getResultList();
		movimientosIngresos.addAll(movimientosIngresosEmpresa);

		if (movimientosIngresos.size() > 1) {
			Collections.sort(movimientosIngresos,
					(o1, o2) -> o2.getFechaRegistroAporte().compareTo(o1.getFechaRegistroAporte()));
		}

		logger.debug("Finaliza método consultarAportesRelacionados");
		return movimientosIngresos;
	}

	/**
	 * Método que asigna los datos de los aportantes a una lista de movimientos
	 * de ingresos
	 * 
	 * @param movimientosIngresos
	 *                            Lista de movimientos de ingresos
	 * @return La lista actualizada
	 */
	private List<MovimientoIngresosDTO> asignarDatosAportante(List<MovimientoIngresosDTO> movimientosIngresos) {
		logger.debug("Inicia método asignarDatosAportante");

		for (MovimientoIngresosDTO movimiento : movimientosIngresos) {
			if (movimiento.getPagoPorSiMismo() != null && movimiento.getPagoPorSiMismo()) {
				movimiento.setTipoIdentificacionAportante(movimiento.getTipoIdentificacionEntidad());
				movimiento.setNumeroIdentificacionAportante(movimiento.getNumeroIdentificacionEntidad());
				movimiento.setRazonSocialAportante(movimiento.getNombres());
			} else {
				if (movimiento.getIdEmpresaTramitadora() != null) {
					Persona personaEmpresaTramitadora = consultarPersonaEmpresa(movimiento.getIdEmpresaTramitadora());

					if (personaEmpresaTramitadora != null) {
						movimiento.setTipoIdentificacionAportante(personaEmpresaTramitadora.getTipoIdentificacion());
						movimiento
								.setNumeroIdentificacionAportante(personaEmpresaTramitadora.getNumeroIdentificacion());

						if (personaEmpresaTramitadora.getRazonSocial() != null) {
							movimiento.setRazonSocialAportante(personaEmpresaTramitadora.getRazonSocial());
						} else {
							movimiento.setRazonSocialAportante(personaEmpresaTramitadora.getPrimerNombre() + " "
									+ personaEmpresaTramitadora.getSegundoNombre() + " "
									+ personaEmpresaTramitadora.getPrimerApellido() + " "
									+ personaEmpresaTramitadora.getSegundoApellido());
						}
					}
				}
			}
			// Si no es empleador se consulta la tarifa en aporte
			if (!TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(movimiento.getTipoEntidad())) {
				logger.info("getIdRegistroGeneral: " + movimiento.getIdRegistroGeneral().toString());
				String tarifaString = consultarTarifa(movimiento.getIdRegistroGeneral().toString());
				logger.info("Res consultar tarifa: " + tarifaString);
				if (tarifaString != null ){
					BigDecimal tarifa = new BigDecimal(tarifaString);
					movimiento.setPorcentajeAporte(tarifa);
				}
			}

		}

		logger.debug("Inicia método asignarDatosAportante");
		return movimientosIngresos;
	}

	/**
	 * Método que consulta los datos de una persona asociada a una empresa, por
	 * identificador de la empresa
	 * 
	 * @param idEmpresa
	 *                  Identificador único de la empresa
	 * @return Los datos de la <code>Persona</code> asociada
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private Persona consultarPersonaEmpresa(Long idEmpresa) {
		try {
			logger.debug("Inicia método consultarPersonaEmpresa");
			List<Persona> listaPersonaEmpresa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EMPRESA, Persona.class)
					.setParameter(ID_EMPRESA, idEmpresa).getResultList();
			logger.debug("Finaliza método consultarPersonaEmpresa");

			if (!listaPersonaEmpresa.isEmpty()) {
				return listaPersonaEmpresa.get(0);
			}

			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarPersonaEmpresa");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 *      consultarAporteGeneral(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AporteGeneral consultarAporteGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicia método consultarAporteGeneral(idAporteGeneral)");
			logger.info("idAporteGeneral: " + idAporteGeneral);
			AporteGeneral aporteGeneral = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL, AporteGeneral.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getSingleResult();
			logger.debug("Finaliza método consultarAporteGeneral(idAporteGeneral)");
			return aporteGeneral;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteGeneral(idAporteGeneral)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarSolicitudCorreccionAporte(com.asopagos.entidades.ccf.
	 * aportes.SolicitudCorreccionAporte)
	 */
	@Override
	public Long crearActualizarSolicitudCorreccionAporte(SolicitudCorreccionAporte solicitudCorreccionAporte) {
		try {
			logger.debug(
					"Inicia método crearActualizarSolicitudCorrecionAporte(SolicitudCorreccionAporte solicitudCorreccionAporte)");
			SolicitudCorreccionAporte managed = entityManagerCore.merge(solicitudCorreccionAporte);
			logger.debug(
					"Finaliza método crearActualizarSolicitudCorrecionAporte(SolicitudCorreccionAporte solicitudCorreccionAporte)");
			return managed.getSolicitudGlobal().getIdSolicitud();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarSolicitudCorrecionAporte(SolicitudCorreccionAporte solicitudCorreccionAporte)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarSolicitudDevolucionAporte(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudCorreccionAporte consultarSolicitudCorreccionAporte(Long idSolicitudGlobal) {
		try {
			logger.debug("Inicia método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			SolicitudCorreccionAporte solicitudCorreccionAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_CORRECCION,
							SolicitudCorreccionAporte.class)
					.setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();
			logger.debug("Finaliza método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			return solicitudCorreccionAporte;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarCorreccion(com.asopagos.entidades.ccf.aportes.Correccion)
	 */
	@Override
	public Long crearActualizarCorreccion(Correccion correccion) {
		try {
			logger.debug("Inicia método crearActualizarCorreccion(Correccion correccion)");
			Correccion managed = entityManagerCore.merge(correccion);
			logger.debug("Finaliza método crearActualizarCorreccion(Correccion correccion)");
			return managed.getIdCorreccion();
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método crearActualizarCorreccion(Correccion correccion)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 *      consultarCotizantesPorPametro(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CotizanteDTO> consultarCotizantesPorPametro(List<Long> idPersonas) {
		try {
			logger.debug("Inicia consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas)");
			List<CotizanteDTO> cotizantesDTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_POR_PERSONA, CotizanteDTO.class)
					.setParameter(ID_PERSONAS, idPersonas).getResultList();
			logger.debug("Finaliza consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas)");
			return cotizantesDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarCotizantesPorIdAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarMovimientoAporte(com.asopagos.entidades.ccf.aportes.
	 * MovimientoAporte)
	 */
	@Override
	public Long crearActualizarMovimientoAporte(MovimientoAporte movimientoAporte) {
		try {
			logger.debug("Inicia método crearActualizarMovimientoAporte(MovimientoAporte movimientoAporte)");
			MovimientoAporte managed = entityManagerCore.merge(movimientoAporte);
			logger.debug("Finaliza método crearActualizarMovimientoAporte(MovimientoAporte movimientoAporte)");
			return managed.getIdMovimientoAporte();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarMovimientoAporte(MovimientoAporte movimientoAporte)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarAporteDetallado(com.asopagos.entidades.ccf.aportes.
	 * AporteDetallado)
	 */
	@Override
	public Long crearActualizarAporteDetallado(AporteDetallado aporteDetallado) {
		try {
			logger.debug("Inicia método crearActualizarAporteDetallado(AporteDetallado aporteDetallado)");
			AporteDetallado managed = entityManagerCore.merge(aporteDetallado);
			logger.debug("Finaliza método crearActualizarAporteDetallado(AporteDetallado aporteDetallado)");
			return managed.getId();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarAporteDetallado(AporteDetallado aporteDetallado)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarAporteGeneral(com.asopagos.entidades.ccf.aportes.
	 * AporteGeneral)
	 */
	@Override
	public Long crearActualizarAporteGeneral(AporteGeneral aporteGeneral) {
		try {
			logger.debug("Inicia método crearActualizarAporteGeneral(AporteGeneral aporteGeneral)");
			logger.info("aporteGeneral antes del merge " + aporteGeneral.toString());
			AporteGeneral managed = entityManagerCore.merge(aporteGeneral);
			logger.debug("Finaliza método crearActualizarAporteGeneral(AporteGeneral aporteGeneral)");
			return managed.getId();
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método crearActualizarAporteGeneral(AporteGeneral aporteGeneral)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * crearActualizarDevolucionAporteDetalle(com.asopagos.entidades.ccf.
	 * aportes.DevolucionAporteDetalle)
	 */
	@Override
	public Long crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalle devolucionAporteDetalle) {
		try {
			logger.debug(
					"Inicia método crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalle devolucionAporteDetalle)");
			DevolucionAporteDetalle managed = entityManagerCore.merge(devolucionAporteDetalle);
			logger.debug(
					"Finaliza método crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalle devolucionAporteDetalle)");
			return managed.getIdAporteDevolucionDetalle();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalle devolucionAporteDetalle)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteGeneralIdPersona(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAporteGeneralIdPersonaPeriodo(Long idPersona, String periodoAporte) {
		try {
			logger.debug("Inicia consultarAporteGeneralIdPersonaPeriodo");

			List<TipoAjusteMovimientoAporteEnum> movimientos = new ArrayList<>();
			movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA);

			List<AporteGeneralModeloDTO> aportesGenerales = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_ID_PERSONA_DETALLE,
							AporteGeneralModeloDTO.class)
					.setParameter(ID_PERSONA, idPersona).setParameter("periodoAporte", periodoAporte)
					.setParameter(MOVIMIENTOS, movimientos).getResultList();

			logger.debug("Finaliza consultarAporteGeneralIdPersonaPeriodo");
			return aportesGenerales;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarAporteGeneralIdPersonaPeriodo: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportante(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona consultarAportante(Long idPersona) {
		try {
			logger.debug("Inicia consultarAporteGeneralIdPersona(Long)");
			Persona persona = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_PERSONA, Persona.class)
					.setParameter(ID_PERSONA, idPersona).getSingleResult();

			logger.debug("Finaliza consultarAporteGeneralIdPersona(Long)");
			return persona;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarAporteGeneralIdPersona: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportanteEmpresa(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona consultarAportanteEmpresa(Long idEmpresa) {
		try {
			logger.debug("Inicia consultarAporteGeneralIdPersona(Long)");
			Persona persona = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_EMPRESA_PERSONA, Persona.class)
					.setParameter(ID_EMPRESA, idEmpresa).getSingleResult();

			logger.debug("Finaliza consultarAporteGeneralIdPersona(Long)");
			return persona;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarAporteGeneralIdPersona: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 *      com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteYMovimiento(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAporteYMovimiento(List<Long> idsAporte) {
		logger.info("entraaa consultarAporteYMovimiento");
		try {
			List<String> movimientos = new ArrayList<>();
			movimientos.add("DEVOLUCION");
			movimientos.add("CORRECCION_A_LA_BAJA");
			movimientos.add("CORRECCION_A_LA_ALTA");
			int registrosPag = 1500;
			int contRegistros = 0;
			int cantAportes  = idsAporte.size();

			ArrayList<Long> paginaIdApor = new ArrayList<>();

			List<AporteGeneralModeloDTO> aportesGenerales = new ArrayList<>();

			do{
				for (int i = 0; i < registrosPag && contRegistros<cantAportes; i++) {
					paginaIdApor.add(idsAporte.get(contRegistros));
					contRegistros++;
				}


				List<AporteGeneralModeloDTO> aportesGeneral = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_Y_MOVIMIENTO_NATIVA,
								AporteGeneralModeloDTO.class)
						.setParameter("idsAporte", paginaIdApor)
						.setParameter(MOVIMIENTOS, movimientos)
						.getResultList();
				if(!aportesGeneral.isEmpty() && aportesGeneral != null){
					for(AporteGeneralModeloDTO aporte :aportesGeneral){
						aportesGenerales.add(aporte);
						
					}
					
				}
				paginaIdApor.clear();
			}
			while(contRegistros<cantAportes);
			
			
			
			// if (aportesGenerales != null && !aportesGenerales.isEmpty()) {
			// 	aportesGenerales.stream().forEach((iteAportes) -> {
			// 		if (iteAportes.getCuentaBancariaRecaudo() != null) {
			// 			logger.info("2g id cuentas bancarias "+iteAportes.getCuentaBancariaRecaudo());
			// 			Query qCuentaBancaria = entityManagerCore.createNativeQuery(
			// 					"select CONCAT(BANCO,'-',TIPO,'-',NUMERO_CUENTA,'-',TIPO_RECAUDO) AS CUENTA "
			// 							+ "FROM cuentas_bancarias WHERE id = " + iteAportes.getCuentaBancariaRecaudo());
			// 			List<Object> cuentaBancariaTexto = qCuentaBancaria.getResultList();
			// 			logger.info("2g tamaño "+cuentaBancariaTexto.size());
			// 			logger.info("2g toString "+cuentaBancariaTexto.get(0).toString());

			// 			if (cuentaBancariaTexto != null && !cuentaBancariaTexto.isEmpty()) {
			// 				logger.info("entra if cuentabancaria texto");
			// 				logger.info(cuentaBancariaTexto.get(0).toString());
			// 				iteAportes.setCuentaBancariaRecaudoTexto(cuentaBancariaTexto.get(0).toString());
			// 			}
			// 		}
			// 	});
			// }

			return aportesGenerales;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarAporteYMovimiento(List<Long>): ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEstadoAporte(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Boolean> consultarEstadoAporte(Long idAporteGeneral, Long idAporteDetallado) {
		try {
			logger.debug("Inicia método consultarEstadoAporte(Long idAporteGeneral, Long idAporteDetallado)");
			Map<String, Boolean> validaciones = new HashMap<>();

			Boolean validacionAporteGeneral = (Boolean) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_APORTE_GENERAL)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getSingleResult();

			Boolean validacionAporteDetallado = (Boolean) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_APORTE_DETALLADO)
					.setParameter(ID_APORTE_DETALLADO, idAporteDetallado).getSingleResult();

			validaciones.put(ConstantesDatosAportesPila.APORTE_GENERAL_PROCESADO, validacionAporteGeneral);
			validaciones.put(ConstantesDatosAportesPila.APORTE_DETALLADO_PROCESADO, validacionAporteDetallado);

			logger.debug("Finaliza método consultarEstadoAporte(Long idAporteGeneral, Long idAporteDetallado)");
			return validaciones;
		} catch (Exception e) {
			logger.error("Finaliza método consultarEstadoAporte(Long idAporteGeneral, Long idAporteDetallado)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudCorrecionAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudCorreccionAporte> consultarSolicitudCorrecionAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarSolicitudCorrecionAporte(TipoIdentificacionEnum,String)");
		try {
			List<SolicitudCorreccionAporte> solicitudesCorreccionAportes = entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_CORRECCION_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI,
							SolicitudCorreccionAporte.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
					.setParameter(ESTADO_SOLICITUD, EstadoSolicitudAporteEnum.CERRADA).getResultList();
			logger.debug("Finaliza consultarSolicitudCorrecionAporte(TipoIdentificacionEnum,String)");
			return solicitudesCorreccionAportes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitudCorrecionAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudDevolucionAporte> consultarSolicitudDevolucionAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarSolicitudDevolucionAporte(TipoIdentificacionEnum,String)");
		try {
			List<SolicitudDevolucionAporte> solicitudesDevolucionAportes = entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_DEVOLUCION_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI,
							SolicitudDevolucionAporte.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
					.setParameter(ESTADO_SOLICITUD, EstadoSolicitudAporteEnum.CERRADA).getResultList();
			logger.debug("Finaliza consultarSolicitudDevolucionAporte(TipoIdentificacionEnum,String)");
			return solicitudesDevolucionAportes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitudDevolucionAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#buscarMunicipio(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Short buscarMunicipio(String codigoMunicipio) {
		try {
			logger.debug("Inicia buscarMunicipio(Long codigoMunicipio)");
			Short idMunicipio = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIO_CODIGO, Short.class)
					.setParameter(CODIGO_MUNICIPIO, codigoMunicipio).getSingleResult();

			logger.debug("Finaliza buscarMunicipio(Long codigoMunicipio)");
			return idMunicipio;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en buscarMunicipio: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonaDetallado(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona consultarPersonaDetallado(Long idPersona) {
		try {
			logger.debug("Inicia consultarPersonaDetallado(Long idPersona)");
			Persona persona = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTE, Persona.class)
					.setParameter(ID_PERSONA, idPersona).getSingleResult();

			logger.debug("Finaliza consultarPersonaDetallado(Long idPersona)");
			return persona;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonaDetallado: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarHistoricoNovedadesRetiro(java.util.Date, java.util.Date,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedadesRetiro(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia consultarHistoricoNovedadesRetiro");
		try {
			List<TipoTransaccionEnum> novedadesRetiro = new ArrayList<>();
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2);
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR);

			List<HistoricoNovedadesDTO> novedades = new ArrayList<>();

			if (fechaInicio != null) {
				novedades = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES_RETIRO,
								HistoricoNovedadesDTO.class)
						.setParameter(FECHA_INICIO, fechaInicio).setParameter(FECHA_FIN, fechaFin)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter(NOVEDADES_RETIRO, novedadesRetiro).getResultList();
			} else {
				novedades = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES_RETIRO_RECIENTES,
								HistoricoNovedadesDTO.class)
						.setParameter(FECHA_FIN, fechaFin).setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter(NOVEDADES_RETIRO, novedadesRetiro).setMaxResults(10).getResultList();
			}
			logger.debug("Finaliza consultarHistoricoNovedadesRetiro");
			return novedades;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoNovedadesRetiro: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarTipoTransaccionNovedadRechazadaCotizante(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> consultarTipoTransaccionNovedadRechazadaCotizante(Long idAporteDetallado) {
		try {
			logger.debug("Inicia consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado)");
			List<String> lista = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPOTRANSACCION_NOVEDADESRECHAZADAS_COTIZANTE)
					.setParameter(ID_APORTE_DETALLADO, idAporteDetallado).getResultList();
			logger.debug("Finalizada consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado)");
			return lista;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarNovedadesRetiro(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NovedadCotizanteDTO> consultarNovedadesRetiro(Long idRegistroDetallado, Long idPersona) {
		try {
			logger.debug("Inicio de método consultarNovedadesRetiro(Long idRegistroDetallado)");
			List<String> novedadesRetiro = new ArrayList<>();
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2.name());
			novedadesRetiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR.name());

			List<Object[]> novedadesRetiroCotizante = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_RETIRO_COTIZANTE)
					.setParameter(ID_REGISTRO_DETALLADO, idRegistroDetallado)
					.setParameter("novedadesRetiro", novedadesRetiro).setParameter(ID_PERSONA, idPersona)
					.getResultList();
			List<NovedadCotizanteDTO> novedadesCotizanteRetiro = new ArrayList<>();
			for (Object[] novedad : novedadesRetiroCotizante) {
				NovedadCotizanteDTO novedadCotizanteRetiro = new NovedadCotizanteDTO();
				novedadCotizanteRetiro.setTipoNovedad(TipoTransaccionEnum.valueOf(novedad[0].toString()));
				novedadCotizanteRetiro.setFechaInicio(novedad[1] != null ? ((Date) novedad[1]).getTime() : null);
				novedadCotizanteRetiro.setEstadoNovedad(Boolean.TRUE);
				novedadCotizanteRetiro.setCondicion(Boolean.TRUE);
				novedadesCotizanteRetiro.add(novedadCotizanteRetiro);
			}

			logger.debug("Fin de método consultarNovedadesRetiro(Long idRegistroDetallado)");
			return novedadesCotizanteRetiro;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarCotizantesPorRol(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CotizanteDTO> consultarCotizantesPorRol(List<Long> idRoles) {
		try {
			logger.debug("Inicio de método consultarCotizantesPorRol(List<Long> idRoles)");

			List<CotizanteDTO> cotizantes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_ROL_AFILIADO)
					.setParameter("idRoles", idRoles).getResultList();
			logger.debug("Inicio de método consultarCotizantesPorRol(List<Long> idRoles)");
			return cotizantes;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportantesSinDiaVencimiento()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AportanteDiaVencimientoDTO> consultarAportantesSinDiaVencimiento() {
		String firmaMetodo = "AportesBusiness.consultarAportantesSinVencimiento()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AportanteDiaVencimientoDTO> result = null;

		try {
			// se consultan los empleadores sin día de vencimiento
			result = (List<AportanteDiaVencimientoDTO>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_EMPRESA_SIN_DIA_VENCIMIENTO)
					.setParameter("estado", EstadoEmpleadorEnum.ACTIVO).getResultList();

		} catch (NoResultException e) {
			// inicializa la lista vacía
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaMetodo);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		try {
			// se consultan los empleadores sin día de vencimiento
			List<TipoAfiliadoEnum> tiposAfiliados = new ArrayList<>();
			tiposAfiliados.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
			tiposAfiliados.add(TipoAfiliadoEnum.PENSIONADO);

			// se añaden los independientes y pensionados (RolAfiliado) sin día
			// de vencimiento
			result.addAll((List<AportanteDiaVencimientoDTO>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_PERSONA_SIN_DIA_VENCIMIENTO)
					.setParameter("tiposAfiliados", tiposAfiliados).getResultList());
		} catch (NoResultException e) {
			// retorna la lista existente hasta el momento
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return result;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaMetodo);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarDiaVencimientoAportesEmpleadores(java.lang.Short,
	 *      java.util.List)
	 */
	@Override
	public void actualizarDiaVencimientoAportesEmpleadores(Short diaVencimiento, List<Long> empleadores) {
		String firmaMetodo = "AportesBusiness.actualizarDiaVencimientoAportesEmpleadores(Short, List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_DIA_VENCIMIENTO_EMPLEADOR)
					.setParameter("diaVencimiento", diaVencimiento).setParameter("idsEmpleadores", empleadores)
					.executeUpdate();
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaMetodo);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarDiaVencimientoAportesIndPen(java.lang.Short,
	 *      java.util.List)
	 */
	@Override
	public void actualizarDiaVencimientoAportesIndPen(Short diaVencimiento, List<Long> indPen) {
		String firmaMetodo = "AportesBusiness.actualizarDiaVencimientoAportesIndPen(Short, List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			entityManagerCore
					.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_DIA_VENCIMIENTO_INDEPENDIENTES_PENSIONADOS)
					.setParameter("diaVencimiento", diaVencimiento).setParameter("idsIndPen", indPen).executeUpdate();
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaMetodo);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteGeneralEmpleador(java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneral> consultarAporteGeneralEmpleador(Long idEmpleador,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteGeneralEmpleador");
			List<AporteGeneral> listaAporteGeneral = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_EMPLEADOR, AporteGeneral.class)
					.setParameter("idEmpleador", idEmpleador).setParameter("estadoRegistroAporte", estadoRegistroAporte)
					.setParameter("estadoAporteAportante", estadoAporteAportante).getResultList();
			logger.debug("Finaliza método consultarAporteGeneralEmpleador");
			return listaAporteGeneral;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteGeneralEmpleador");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteDetalladoPorIdsGeneral(java.util.List,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetallado> consultarAporteDetalladoPorIdsGeneral(List<Long> listaIdAporteGeneral,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
			List<AporteDetallado> listaAporteDetallado = new ArrayList<AporteDetallado>();

			if (listaIdAporteGeneral != null && !listaIdAporteGeneral.isEmpty()) {
				listaAporteDetallado = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_IDS_GENERAL,
								AporteDetallado.class)
						.setParameter("listaIdAporteGeneral", listaIdAporteGeneral)
						.setParameter("estadoRegistroAporte", estadoRegistroAporte)
						.setParameter("estadoAporteAportante", estadoAporteAportante).getResultList();
			} else {
				logger.debug("No existen aportes de nivel 2 pendientes por registrar para el aportante");
			}

			logger.debug("Finaliza método consultarAporteDetalladoPorIdsGeneral");
			return listaAporteDetallado;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdsGeneral");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteGeneralPersona(java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneral> consultarAporteGeneralPersona(Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, EstadoAporteEnum estadoAporte,
			EstadoRegistroAporteEnum estadoRegistroAporte) {
		try {
			logger.debug("Inicia método consultarAporteGeneralPersona");
			List<AporteGeneral> listaAporteGeneral = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_PERSONA_IDENPENDIENTE_PENSIONADO,
							AporteGeneral.class)
					.setParameter(ID_PERSONA, idPersona).setParameter(TIPO_SOLICITANTE, tipoSolicitante)
					.setParameter("estadoRegistroAporte", estadoRegistroAporte)
					.setParameter("estadoAporte", estadoAporte).getResultList();
			logger.debug("Finaliza método consultarAporteGeneralPersona");
			return listaAporteGeneral;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteGeneralPersona");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteDetalladoPorIdsGeneralPersona(java.util.List,
	 * java.lang.Long, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetallado> consultarAporteDetalladoPorIdsGeneralPersona(List<Long> listaIdAporteGeneral,
			Long idPersona, TipoAfiliadoEnum tipoAfiliado, EstadoRegistroAporteEnum estadoRegistroAporte,
			EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
			List<AporteDetallado> listaAporteDetallado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_IDS_GENERAL_PERSONA,
							AporteDetallado.class)
					.setParameter("listaIdAporteGeneral", listaIdAporteGeneral)
					.setParameter("estadoRegistroAporte", estadoRegistroAporte)
					.setParameter("estadoAporteAportante", estadoAporteAportante)
					.setParameter("tipoAfiliado", tipoAfiliado).setParameter(ID_PERSONA, idPersona).getResultList();
			logger.debug("Finaliza método consultarAporteDetalladoPorIdsGeneral");
			return listaAporteDetallado;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdsGeneral");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarAporteDetalladoPorIdGeneral(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetallado> consultarAporteDetalladoPorIdGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdGeneral");
			List<AporteDetallado> listaAporteDetallado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_ID_GENERAL,
							AporteDetallado.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getResultList();
			logger.debug("Finaliza método consultarAporteDetalladoPorIdGeneral");
			return listaAporteDetallado;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdGeneral");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCuentasAporteSinDetalle(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarCuentasAporteSinDetalle(List<Long> idsAporteGeneral,UriInfo uri, HttpServletResponse response) {
		logger.debug("Inicia consultarCuentasAporteSinDetalle(List<Long>) ");

		List<CuentaAporteDTO> result = new  ArrayList<>();

		if(uri ==null || response == null){
			return entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_APORTE_SIN_DETALLE, CuentaAporteDTO.class)
				.setParameter("idsAporteGeneral", idsAporteGeneral).getResultList();
		}else{
				/*builder */
			QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
			Query query = null;
			String consulta = NamedQueriesConstants.CONSULTAR_CUENTA_APORTE_SIN_DETALLE_BUILDER;
			queryBuilder.addParam("idsAporteGeneral", idsAporteGeneral);
			query = queryBuilder.createQuery(consulta, null);
			 result = query.getResultList();
			return result;
		}
	
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCuentasAporteCotizante(java.util.List,
	 *      java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarCuentasAporteCotizante(List<Long> idsAporteGeneral, Long idPersonaCotizante,UriInfo uri, HttpServletResponse response, TipoMovimientoRecaudoAporteEnum tipo) {
		String firmaMetodo = "ConsultasModeloCore.consultarCuentasAporteCotizante(List<Long>, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<CuentaAporteDTO> result = null;

		Integer tieneIdsAportes = 1;
		if (idsAporteGeneral == null || idsAporteGeneral.isEmpty()) {
			tieneIdsAportes = 0;
			idsAporteGeneral.add(0L);
		}
		if(uri ==null || response == null){
			result = entityManagerCore
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_APORTE_COTIZANTE, CuentaAporteDTO.class)
			.setParameter(IDS_APORTE_GENERAL, idsAporteGeneral)
			.setParameter(ID_PERSONA, idPersonaCotizante)
			.setParameter("tieneIdsAportes", tieneIdsAportes).getResultList();
		}else{
				/*builder */
				List<TipoMovimientoRecaudoAporteEnum> tipoMovimiento = new ArrayList<>();
				if(tipo.equals(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL)){
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL);
				}else if(tipo != null){
					tipoMovimiento.add(tipo);

				}else{
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES);
					tipoMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES);
				}
				QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
				Query query = null;
				String consulta = NamedQueriesConstants.CONSULTAR_CUENTA_APORTE_COTIZANTE_BUILDER;
				queryBuilder.addParam(IDS_APORTE_GENERAL, idsAporteGeneral);
				queryBuilder.addParam(ID_PERSONA, idPersonaCotizante);
				queryBuilder.addParam("tieneIdsAportes", tieneIdsAportes);
				queryBuilder.addParam("tipoMovimiento", tipoMovimiento);
				query = queryBuilder.createQuery(consulta, null);
				result = query.getResultList();
				/*fin buider */
		}


		if (result != null && !result.isEmpty()) {
			result.stream().forEach((iteAportes) -> {
				Query qCuentaBancaria = entityManagerCore
						.createNativeQuery("select case when p.id is not null then CONCAT(p.BANCO,'-',p.TIPO,'-',p.NUMERO_CUENTA,'-',p.TIPO_RECAUDO) "
								+ "when pn.id is not null then  CONCAT(pn.BANCO,'-',pn.TIPO,'-',pn.NUMERO_CUENTA,'-',pn.TIPO_RECAUDO)  end AS CUENTA from AporteGeneral ap  "
								+ "left join aporteDetalladoRegistroControlN a on ap.apgid = a.apdAporteGeneral "
								+ "left join CUENTAS_BANCARIAS p on p.id = ap.apgCuentaBancariaRecaudo "
								+ "left join CUENTAS_BANCARIAS pn on pn.id = a.regCuentaBancariaRecaudo where ap.apgId ="
								+ iteAportes.getIdAporteGeneral());
				String cuentaBancariaTexto = "";
				try {
					cuentaBancariaTexto =  qCuentaBancaria.getSingleResult() != null ? (String) qCuentaBancaria.getSingleResult():"No aplica.";
				} catch (NoResultException nre) {
					// Code for handling NoResultException
					logger.error("entra catch");
					cuentaBancariaTexto = "No aplica.";
				}
				iteAportes.setCuentaBancariaRecaudoTexto(cuentaBancariaTexto);
			});
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitanteAporteGeneral(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitanteDTO> consultarSolicitanteAporteGeneral(List<Long> idsPersona) {
		String firmaMetodo = "ConsultasModeloCore.consultarSolicitanteAporteGeneral(List<Long> idsPersona)";
		logger.debug(ConstantesComunes.INICIO_LOGGER);
		List<SolicitanteDTO> solicitantes = null;
		Map<String, SolicitanteDTO> mapaSolicitantes = new HashMap<>();

		try {

			// se actualiza el ID de transacción en los registros generales en
			// paquetes de 1000 registros
			Integer inicio = 0;
			Integer fin = idsPersona.size() > 1000 ? 1000 : idsPersona.size();
			while (fin <= idsPersona.size()) {
				List<Long> idsPersonasTemp = idsPersona.subList(inicio, fin);

				List<SolicitanteDTO> solicitantesAportePropio = null;
				List<SolicitanteDTO> solicitantesDependienteTercero = null;

				solicitantesAportePropio = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITANTE_APORTE_GENERAL,
								SolicitanteDTO.class)
						.setParameter("idsPersona", idsPersonasTemp).getResultList();

				solicitantesDependienteTercero = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITANTE_APORTE_GENERAL_EMPRESA,
								SolicitanteDTO.class)
						.setParameter("idsPersona", idsPersonasTemp).getResultList();

				String llave = null;
				// se agregan los solicitantes por aporte propio
				for (SolicitanteDTO solicitantePropio : solicitantesAportePropio) {
					llave = solicitantePropio.getIdPersona() + "-" + solicitantePropio.getTipoSolicitante();

					if (!mapaSolicitantes.containsKey(llave)) {
						mapaSolicitantes.put(llave, solicitantePropio);
					}
				}

				// se agregan los solicitantes como empleador o tercero pagador
				// (se controla para no repetir solicitantes)
				for (SolicitanteDTO solicitanteTercero : solicitantesDependienteTercero) {
					llave = solicitanteTercero.getIdPersona() + "-" + solicitanteTercero.getTipoSolicitante();

					if (!mapaSolicitantes.containsKey(llave)) {
						mapaSolicitantes.put(llave, solicitanteTercero);
					}
				}

				inicio = fin;
				fin++;
				if (fin <= idsPersona.size()) {
					fin = fin + 1000 <= idsPersona.size() ? inicio + 1000 : idsPersona.size();
				}
			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: Sin datos en la iteración");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		solicitantes = new ArrayList<>(mapaSolicitantes.values());

		logger.debug(ConstantesComunes.FIN_LOGGER);
		return solicitantes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonaTipoNumeroIdentificacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PersonaDTO consultarPersonaTipoNumeroIdentificacion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarPersonaTipoNumeroIdentificacion(TipoIdentificacionEnum, String)");
		try {
			PersonaDTO persona = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
							PersonaDTO.class)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getSingleResult();

			logger.debug("Finaliza consultarPersonaTipoNumeroIdentificacion(TipoIdentificacionEnum, String)");
			return persona;
		} catch (NoResultException e) {
			logger.debug("No se encontraron coincidencias para consultarPersonaTipoNumeroIdentificacion");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonaTipoNumeroIdentificacion: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpresa(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EmpresaDTO consultarEmpresa(Long idPersona) {
		logger.debug("Inicia consultarEmpresa(Long idPersona)");
		try {
			EmpresaDTO empresa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID_PERSONA, EmpresaDTO.class)
					.setParameter(ID_PERSONA, idPersona).getSingleResult();

			logger.debug("Finaliza consultarEmpresa(Long idPersona)");
			return empresa;
		} catch (NoResultException e) {
			logger.debug("No se encontraron coincidencias para consultarEmpresa(Long idPersona)");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarEmpresa(Long idPersona): ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudCorreccionAporteAporteGeneral(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudCorreccionAporte consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicia método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			SolicitudCorreccionAporte solicitudCorreccionAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_CORRECCION_APORTE_GENERAL,
							SolicitudCorreccionAporte.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getSingleResult();
			logger.debug("Finaliza método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			return solicitudCorreccionAporte;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarHistoricoCierre(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudCierreRecaudoModeloDTO> consultarHistoricoCierre(Long fechaInicio, Long fechaFin,
			String numeroRadicacion, TipoCierreEnum tipoCierre) {
		try {
			logger.debug("Inicia método consultarHistoricoCierre");
			List<SolicitudCierreRecaudoModeloDTO> solicitudCierreRecaudoDTO = new ArrayList<>();
			List<SolicitudCierreRecaudo> solicitudCierreRecaudo = new ArrayList<>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(fechaFin));
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			fechaFin = calendar.getTimeInMillis();

			if (fechaInicio != null && fechaFin != null && numeroRadicacion != null) {
				// Esta es cuando viene de historico ejecutar la siguiente
				// consulta
				List<EstadoSolicitudCierreRecaudoEnum> estados = new ArrayList<>();
				estados.add(EstadoSolicitudCierreRecaudoEnum.CERRADA);
				solicitudCierreRecaudo = entityManagerCore
						.createNamedQuery(
								NamedQueriesConstants.CONSULTAR_FECHA_INICIO_FIN_NUMERO_RADICACION_CIERRE_RECAUDO,
								SolicitudCierreRecaudo.class)
						.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
						.setParameter("estados", estados).setParameter("numeroRadicacion", numeroRadicacion)
						.getResultList();
			} else if (fechaInicio != null && fechaFin != null && numeroRadicacion == null) {

				if (tipoCierre != null && TipoCierreEnum.HISTORICO.equals(tipoCierre)) {
					// Esta es cuando viene de historico ejecutar la siguiente
					// consulta pero sin el numero de la radicación
					List<EstadoSolicitudCierreRecaudoEnum> estados = new ArrayList<>();
					estados.add(EstadoSolicitudCierreRecaudoEnum.CERRADA);
					solicitudCierreRecaudo = entityManagerCore
							.createNamedQuery(
									NamedQueriesConstants.CONSULTAR_FECHA_INICIO_FIN_SIN_NUMERO_RADICACION_CIERRE_RECAUDO,
									SolicitudCierreRecaudo.class)
							.setParameter(FECHA_INICIO, new Date(fechaInicio))
							.setParameter(FECHA_FIN, new Date(fechaFin)).setParameter("estados", estados)
							.getResultList();
				} else {
					// Esta es cuando viene de cierre o precierre ejecutar la
					// siguiente consulta
					solicitudCierreRecaudo = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_INICIO_FIN_CIERRE_RECAUDO,
									SolicitudCierreRecaudo.class)
							.setParameter(FECHA_INICIO, new Date(fechaInicio))
							.setParameter(FECHA_FIN, new Date(fechaFin)).getResultList();
				}
			} else {
				// CONSULTAR_SOLICITUD_CIERRE_RECAUDO_NUMERO_RADICACION
				solicitudCierreRecaudo = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_RADICACION_CIERRE_RECAUDO,
								SolicitudCierreRecaudo.class)
						.setParameter("numeroRadicacion", numeroRadicacion).getResultList();
			}

			for (SolicitudCierreRecaudo solicitudCierre : solicitudCierreRecaudo) {
				SolicitudCierreRecaudoModeloDTO cierreDTO = new SolicitudCierreRecaudoModeloDTO();
				cierreDTO.convertToDTO(solicitudCierre);
				solicitudCierreRecaudoDTO.add(cierreDTO);
			}
			logger.debug("Fin método consultarHistoricoCierre");
			return solicitudCierreRecaudoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarHistoricoCierre(Long ,Long,String)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * validarGeneracionCierre(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean validarGeneracionCierre(Long fechaInicio, Long fechaFin) {
		try {

			logger.debug("Inicia método validarGeneracionCierre");
			logger.info("Inicia método validarGeneracionCierre");

			List<ResultadoProcesoEnum> estadosRechazados = new ArrayList<>();
			estadosRechazados.add(ResultadoProcesoEnum.RECHAZADA_ANALISTA);
			estadosRechazados.add(ResultadoProcesoEnum.RECHAZADA_SUPERVISOR);
			estadosRechazados.add(ResultadoProcesoEnum.RECHAZADA_CONTABILIDAD);

			Date fechaInicial = new Date(fechaInicio);
			Date fechaFinal = new Date(fechaFin);
			fechaInicial = CalendarUtils.truncarHora(fechaInicial);
			fechaFinal = CalendarUtils.truncarHoraMaxima(fechaFinal);

			logger.info(" SQL -> NamedQueriesConstants.CONSULTAR_SOLICITUD_CIERRE_RECAUDO_FECHAS_ESTADO_RESULTADO");
			SolicitudCierreRecaudo solicitud = new SolicitudCierreRecaudo();
			try{
			solicitud = entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_CIERRE_RECAUDO_FECHAS_ESTADO_RESULTADO,
							SolicitudCierreRecaudo.class)
					.setParameter(FECHA_INICIO, fechaInicial).setParameter(FECHA_FIN, fechaFinal)
					.setParameter("estado", EstadoSolicitudCierreRecaudoEnum.CERRADA)
					.setParameter("tipoCierre", TipoCierreEnum.CIERRE)
					.setParameter("resultadoProceso", estadosRechazados).getSingleResult();
			}catch(NoResultException nre){
				logger.debug("No se encontraron coincidencias para validarGeneracionCierre");
				solicitud = null;
			}
			if (solicitud != null) {
				logger.info(" Return true - solicitud");
				return true;
			}

			logger.debug("Fin método validarGeneracionCierre");
			logger.info("Fin método validarGeneracionCierre");
			return false;

		} catch (NoResultException nre) {
			logger.error("Ocurrió un error NoResultException", nre);
			return false;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método validarGeneracionCierre(Long ,Long)", e);
			logger.info("Ocurrió un error en el método validarGeneracionCierre(Long ,Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * guardarSolicitudCierreRecaudo(com.asopagos.dto.modelo.
	 * SolicitudCierreRecaudoModeloDTO)
	 */
	@Override
	public Long guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO solicitudCierreDTO) {
		try {

			logger.debug("Inicia método guardarSolicitudCierreRecaudo");
			logger.info("Inicia método guardarSolicitudCierreRecaudo");
			SolicitudCierreRecaudo solicitudCierre = solicitudCierreDTO.convertToEntity();
			if (solicitudCierreDTO.getNumeroRadicacion() != null) {
				SolicitudCierreRecaudoModeloDTO solicitudDTO = consultarSolicitudCierreRecaudo(
						solicitudCierreDTO.getNumeroRadicacion());
				if (solicitudDTO != null) {
					entityManagerCore.merge(solicitudCierre);
					return solicitudCierre.getSolicitudGlobal().getIdSolicitud();
				}

			}
			logger.debug("Fin método guardarSolicitudCierreRecaudo");
			logger.info("Fin método guardarSolicitudCierreRecaudo");

			entityManagerCore.persist(solicitudCierre);
			return solicitudCierre.getSolicitudGlobal().getIdSolicitud();

		} catch (Exception e) {
			logger.error("Ocurrió un error en el método guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO)",
					e);
			logger.info("Ocurrió un error en el método guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO)",
					e);

			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * consultarSolicitudCierreRecaudo(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudCierreRecaudoModeloDTO consultarSolicitudCierreRecaudo(String numeroRadicacion) {
		try {
			logger.debug("Inicia método consultarSolicitudCierreRecaudo");
			logger.info("Inicia método consultarSolicitudCierreRecaudo");

			SolicitudCierreRecaudo solicitudCierre = (SolicitudCierreRecaudo) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_CIERRE_RECAUDO_NUMERO_RADICACION)
					.setParameter("numeroRadicacion", numeroRadicacion).getSingleResult();

			SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = new SolicitudCierreRecaudoModeloDTO();
			solicitudCierreDTO.convertToDTO(solicitudCierre);
			logger.debug("Fin método consultarSolicitudCierreRecaudo");
			logger.info("Fin método consultarSolicitudCierreRecaudo");

			return solicitudCierreDTO;
		} catch (NoResultException nre) {
			logger.error("Ocurrió un error - NoResultException - consultarSolicitudCierreRecaudo", nre);
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarSolicitudCierreRecaudo(String)", e);
			logger.info("Ocurrió un error en el método consultarSolicitudCierreRecaudo(String)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#
	 * actualizarEstadoSolicitudCierre(com.asopagos.enumeraciones.aportes.
	 * EstadoSolicitudCierreRecaudoEnum, java.lang.String)
	 */
	@Override
	public void actualizarEstadoSolicitudCierre(EstadoSolicitudCierreRecaudoEnum estado, String numeroRadicacion) {
		try {
			logger.debug("Inicia método actualizarEstadoSolicitudCierre");
			SolicitudCierreRecaudoModeloDTO solicitudDTO = consultarSolicitudCierreRecaudo(numeroRadicacion);
			if (EstadoSolicitudCierreRecaudoEnum.CERRADA.equals(estado)) {
				solicitudDTO.setResultadoProceso(ResultadoProcesoEnum.APROBADO_REGISTROS_CONCILIADOS);
				solicitudDTO.setEstadoSolicitud(estado);
			} else if (EstadoSolicitudCierreRecaudoEnum.RECHAZADA_ANALISTA.equals(estado)
					|| EstadoSolicitudCierreRecaudoEnum.RECHAZADA_CONTABILIDAD.equals(estado)
					|| EstadoSolicitudCierreRecaudoEnum.RECHAZADA_SUPERVISOR.equals(estado)) {
				solicitudDTO.setResultadoProceso(ResultadoProcesoEnum.valueOf(estado.name()));
				solicitudDTO.setEstadoSolicitud(EstadoSolicitudCierreRecaudoEnum.CERRADA);
			}
			guardarSolicitudCierreRecaudo(solicitudDTO);
			logger.debug("Fin método actualizarEstadoSolicitudCierre");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método actualizarEstadoSolicitudCierre(EstadoSolicitudCierreRecaudoEnum ,String)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosAportes(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosAportes(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosAportes(Long fechaInicio, Long fechaFin)");

			List<String> tiposMovimiento = new ArrayList<>();
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());

			@SuppressWarnings("unchecked")
			List<Object[]> aportesRegistrados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_REGISTRADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.REGISTRADO.name())
					.setParameter("tipoMovimiento", tiposMovimiento)
					.setParameter("origenAporte", OrigenAporteEnum.CORRECCION_APORTE.name()).getResultList();

			logger.debug("Fin método consultarRegistrosAportes(Long fechaInicio, Long fechaFin)");
			return aportesRegistrados;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarRegistrosAportes(Long fechaInicio, Long fechaFin)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosAportesRelacionados(java.lang.Long,
	 *      java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosAportesRelacionados(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosAportesRelacionados(Long fechaInicio, Long fechaFin)");

			List<String> tiposMovimiento = new ArrayList<>();
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());

			List<Object[]> aportesRelacionados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_RELACIONADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.RELACIONADO.name())
					.setParameter("tipoMovimiento", tiposMovimiento)
					.setParameter("origenAporte", OrigenAporteEnum.CORRECCION_APORTE.name()).getResultList();

			logger.debug("Fin método consultarRegistrosAportesRelacionados(Long fechaInicio, Long fechaFin)");
			return aportesRelacionados;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarRegistrosAportesRelacionados(Long fechaInicio, Long fechaFin)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosDevoluciones(java.lang.Long,
	 *      java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosDevoluciones(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosDevoluciones(Long fechaInicio, Long fechaFin)");

			List<String> tiposMovimiento = new ArrayList<>();
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());

			List<Object[]> devolucionesRegistradas = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_DEVOLUCIONES_REGISTRADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.REGISTRADO.name())
					.setParameter("tipoMovimiento", tiposMovimiento).getResultList();

			logger.debug("Fin método consultarRegistrosDevoluciones(Long fechaInicio, Long fechaFin)");
			return devolucionesRegistradas;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarRegistrosDevoluciones(Long fechaInicio, Long fechaFin)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosDevolucionesRelacionados(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosDevolucionesRelacionados(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosDevolucionesRelacionados(Long fechaInicio, Long fechaFin)");

			List<String> tiposMovimiento = new ArrayList<>();
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());

			List<Object[]> devolucionesRelacionadas = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_DEVOLUCIONES_RELACIONADAS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.RELACIONADO.name())
					.setParameter("tipoMovimiento", tiposMovimiento).getResultList();

			logger.debug("Fin método consultarRegistrosDevolucionesRelacionados(Long fechaInicio, Long fechaFin)");
			return devolucionesRelacionadas;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarRegistrosDevolucionesRelacionados(Long fechaInicio, Long fechaFin)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosCorrecciones(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosCorrecciones(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosCorrecciones(Long fechaInicio, Long fechaFin)");

			List<Object[]> correccionesRegistrados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_CORRECCIONES_REGISTRADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.REGISTRADO.name())
					.setParameter("origenAporte", OrigenAporteEnum.CORRECCION_APORTE.name()).getResultList();

			logger.debug("Fin método consultarRegistrosCorrecciones(Long fechaInicio, Long fechaFin)");
			return correccionesRegistrados;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarRegistrosCorrecciones(Long fechaInicio, Long fechaFin)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosCorreccionesRelacionados(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosCorreccionesRelacionados(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosCorreccionesRelacionados(Long fechaInicio, Long fechaFin)");
			List<Object[]> correccionesRelacionados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_CORRECCIONES_RELACIONADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.RELACIONADO.name())
					.setParameter("origenAporte", OrigenAporteEnum.CORRECCION_APORTE.name()).getResultList();

			logger.debug("Fin método consultarRegistrosCorreccionesRelacionados(Long fechaInicio, Long fechaFin)");
			return correccionesRelacionados;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarRegistrosCorreccionesRelacionados(Long fechaInicio, Long fechaFin)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosLegalizados(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosLegalizados(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio método consultarRegistrosLegalizados(Long fechaInicio, Long fechaFin)");

			List<String> formasReconocimiento = new ArrayList<>();
			formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
			formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());

			List<String> tiposMovimiento = new ArrayList<>();
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
			tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());

			List<Object[]> registrosLegalizados = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_REGISTRADOS_LEGALIZADOS)
					.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
					.setParameter(ESTADO_REGISTRO, EstadoRegistroAporteEnum.REGISTRADO.name())
					.setParameter(FORMA_RECONOCIMIENTO, formasReconocimiento)
					.setParameter("tipoMovimiento", tiposMovimiento).getResultList();

			logger.debug("Fin método consultarRegistrosLegalizados(Long fechaInicio, Long fechaFin)");
			return registrosLegalizados;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarRegistrosLegalizados(Long fechaInicio, Long fechaFin)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDevolucionesVista360(java.util.List,
	 *      java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionVista360(Long idsAporteGeneral,
			Long numeroOperacion) {
		String firmaMetodo = "consultarDevolucionesVista360(List<Long> idsAporteGeneral, List<Long> idsAporteDetallado)";
		SolicitudDevolucionAporteModeloDTO devolucionAporteModeloDTO = new SolicitudDevolucionAporteModeloDTO();
		try {
			logger.debug("Inicia método " + firmaMetodo);
			SolicitudDevolucionAporte solicitudDevolucionAporte = (SolicitudDevolucionAporte) entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE_ID_MOVIMIENTO)
					.setParameter("idsAporteGeneral", idsAporteGeneral).setParameter("idMovimiento", numeroOperacion)
					.setParameter("tipoAjuste", TipoAjusteMovimientoAporteEnum.DEVOLUCION).getSingleResult();
			logger.debug("Finaliza método " + firmaMetodo);
			if (solicitudDevolucionAporte != null) {
				devolucionAporteModeloDTO.convertToDTO(solicitudDevolucionAporte);
			}
			return devolucionAporteModeloDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleDevolucionVista360(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DetalleDevolucionVista360DTO> consultarDetalleDevolucionVista360(Long idSolicitudDevolucion) {
		String firmaMetodo = "consultarDetalleDevolucionVista360(Long idsAporteGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<DetalleDevolucionVista360DTO> devoluciones = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE,
							DetalleDevolucionVista360DTO.class)
					.setParameter("idSolicitudDevolucion", idSolicitudDevolucion).getResultList();

			if (!devoluciones.isEmpty()) {
				for (DetalleDevolucionVista360DTO detalleDevolucionVista360DTO : devoluciones) {
					if (TipoMedioDePagoEnum.CONSIGNACION.equals(detalleDevolucionVista360DTO.getTipoMediopago())) {
						MedioConsignacion medioConsignacion = entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIO_CONSIGNACION,
										MedioConsignacion.class)
								.setParameter("idMedioPago", detalleDevolucionVista360DTO.getIdMedioPago())
								.getSingleResult();
						detalleDevolucionVista360DTO
								.setNombreTitularCuenta(medioConsignacion.getNombreTitularCuenta() != null
										? medioConsignacion.getNombreTitularCuenta()
										: null);
						detalleDevolucionVista360DTO.setNumeroIdentificacionTitular(
								medioConsignacion.getNumeroIdentificacionTitular() != null
										? medioConsignacion.getNumeroIdentificacionTitular()
										: null);
						detalleDevolucionVista360DTO.setNumeroCuenta(medioConsignacion.getNumeroCuenta() != null
								? medioConsignacion.getNumeroCuenta()
								: null);
						detalleDevolucionVista360DTO
								.setTipoIdentificacionTitular(medioConsignacion.getTipoIdentificacionTitular() != null
										? medioConsignacion.getTipoIdentificacionTitular()
										: null);
						detalleDevolucionVista360DTO.setTipoCuenta(
								medioConsignacion.getTipoCuenta() != null ? medioConsignacion.getTipoCuenta() : null);
					} else if (TipoMedioDePagoEnum.TRANSFERENCIA
							.equals(detalleDevolucionVista360DTO.getTipoMediopago())) {
						MedioTransferencia medioTransferencia = entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIO_TRANSFERENCIA,
										MedioTransferencia.class)
								.setParameter("idMedioPago", detalleDevolucionVista360DTO.getIdMedioPago())
								.getSingleResult();
						detalleDevolucionVista360DTO
								.setNombreTitularCuenta(medioTransferencia.getNombreTitularCuenta() != null
										? medioTransferencia.getNombreTitularCuenta()
										: null);
						detalleDevolucionVista360DTO.setNumeroIdentificacionTitular(
								medioTransferencia.getNumeroIdentificacionTitular() != null
										? medioTransferencia.getNumeroIdentificacionTitular()
										: null);
						detalleDevolucionVista360DTO.setNumeroCuenta(medioTransferencia.getNumeroCuenta() != null
								? medioTransferencia.getNumeroCuenta()
								: null);
						detalleDevolucionVista360DTO
								.setTipoIdentificacionTitular(medioTransferencia.getTipoIdentificacionTitular() != null
										? medioTransferencia.getTipoIdentificacionTitular()
										: null);
						detalleDevolucionVista360DTO.setTipoCuenta(
								medioTransferencia.getTipoCuenta() != null ? medioTransferencia.getTipoCuenta() : null);
					}
				}
			}
			logger.debug("Finaliza método " + firmaMetodo);
			return devoluciones;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteGeneralPorCotizante360Persona(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarAporteGeneralPorCotizante360Persona(Long idPersona) {
		String firmaMetodo = "consultarAporteGeneralPorCotizante360Persona(Long idPersona)";
		List<Long> idAporteGeneral = new ArrayList<>();
		try {
			logger.debug("Inicia método " + firmaMetodo);
			idAporteGeneral = entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_IDENTIFICADOR_APORTE_GENERAL_POR_ID_COTIZANTE_VISTA_360_PERSONA)
					.setParameter(ID_PERSONA, idPersona).getResultList();
			logger.debug("Finaliza método " + firmaMetodo);
			return idAporteGeneral;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudCorrecionVista360(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, List<SolicitudCorreccionAporteModeloDTO>> consultarSolicitudCorrecionVista360(
			List<Long> idsAporteGeneral) {
		String firmaMetodo = "ConsultasModeloCore.consultarSolicitudCorrecionVista360(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccion = null;

		try {
			List<SolicitudCorreccionAporte> solicitudesCorreccionAporte = entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_CORRECION_VISTA_360_APORTE_POR_ID_APORTE,
							SolicitudCorreccionAporte.class)
					.setParameter("idsAporteGeneral", idsAporteGeneral).getResultList();

			solicitudesCorreccion = new HashMap<>();

			if (solicitudesCorreccionAporte != null && !solicitudesCorreccionAporte.isEmpty()) {
				for (SolicitudCorreccionAporte solicitudCorreccion : solicitudesCorreccionAporte) {
					SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteModeloDTO = new SolicitudCorreccionAporteModeloDTO();
					solicitudCorreccionAporteModeloDTO.convertToDTO(solicitudCorreccion);

					if (solicitudesCorreccion.containsKey(solicitudCorreccion.getIdAporteGeneralNuevo())) {
						solicitudesCorreccion.get(solicitudCorreccion.getIdAporteGeneralNuevo())
								.add(solicitudCorreccionAporteModeloDTO);
					} else {
						List<SolicitudCorreccionAporteModeloDTO> listado = new ArrayList<>();
						listado.add(solicitudCorreccionAporteModeloDTO);
						solicitudesCorreccion.put(solicitudCorreccion.getIdAporteGeneralNuevo(), listado);
					}
				}

			}
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return solicitudesCorreccion;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleDevolucionVista360(java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DetalleCorreccionCotizanteVista360DTO> consultarDetalleCorrecionCotizanteVista360(
			Long idSolicitudCorreccion) {
		String firmaMetodo = "consultarDetalleDevolucionVista360(Long idSolicitudCorreccion)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<DetalleCorreccionCotizanteVista360DTO> detalleCorreccionCotizanteVista360DTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCION_COTIZANTE_VISTA_360,
							DetalleCorreccionCotizanteVista360DTO.class)
					.setParameter("idSolicitudCorreccion", idSolicitudCorreccion)
					.setParameter("tipoAjuste", TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA).getResultList();
			logger.debug("Finaliza método " + firmaMetodo);
			return detalleCorreccionCotizanteVista360DTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleCorrecionAportanteVista360(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleCorreccionAportanteVista360DTO consultarDetalleCorrecionAportanteVista360(
			Long idSolicitudCorreccion) {
		String firmaMetodo = "consultarDetalleCorrecionAportanteVista360(Long idSolicitudCorreccion)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			DetalleCorreccionAportanteVista360DTO correccionAportanteVista360DTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCION_APORTANTE_VISTA_360,
							DetalleCorreccionAportanteVista360DTO.class)
					.setParameter("tipoAjuste", TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA)
					.setParameter("idSolicitudCorreccion", idSolicitudCorreccion).getSingleResult();
			logger.debug("Finaliza método " + firmaMetodo);
			return correccionAportanteVista360DTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleCorrecionCotizanteNuevoVista360(java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DetalleCorreccionCotizanteNuevoVista360DTO> consultarDetalleCorrecionCotizanteNuevoVista360(
			Long idSolicitudCorreccion, Long idAporteDetallado) {
		String firmaMetodo = "consultarDetalleCorrecionAportanteVista360(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<DetalleCorreccionCotizanteNuevoVista360DTO> detalleCorreccionCotizanteNuevoVista360DTO = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCION_COTIZANTE_NUEVO_VISTA_360_NATIVA,
							DetalleCorreccionCotizanteNuevoVista360DTO.class)
					.setParameter("idSolicitudCorreccion", idSolicitudCorreccion)
					.setParameter("idAporteDetallado", idAporteDetallado)
					.setParameter("origenAporte", OrigenAporteEnum.CORRECCION_APORTE.name()).getResultList();
			logger.debug("Finaliza método " + firmaMetodo);
			return detalleCorreccionCotizanteNuevoVista360DTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudAporteVista360Empleador(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudAporteModeloDTO consultarSolicitudAporteVista360Empleador(Long idAporteGeneral) {
		String firmaMetodo = "consultarSolicitudCorrecionVista360(Long idsAporteGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			AporteGeneral aporte = consultarAporteGeneral(idAporteGeneral);

			SolicitudAporte solicitudAporte = (SolicitudAporte) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_VISTA_360_APORTE_POR_ID_APORTE)
					.setParameter(ID_REGISTRO_GENERAL, aporte.getIdRegistroGeneral())
					.setParameter("estado", EstadoSolicitudAporteEnum.CERRADA).getSingleResult();
			SolicitudAporteModeloDTO solicitudAporteModeloDTO = new SolicitudAporteModeloDTO();
			if (solicitudAporte != null) {
				solicitudAporteModeloDTO.convertToDTO(solicitudAporte);
			}
			logger.debug("Finaliza método " + firmaMetodo);
			return solicitudAporteModeloDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPeriodoPagoAfiliacion(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PeriodoPagoPlanillaEnum consultarPeriodoPagoAfiliacion(Long idPersona, TipoAfiliadoEnum tipoAfiliado) {
		String firmaMetodo = "consultarPeriodoPagoAfiliacion(Long idPersona)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			RolAfiliado afiliado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_PAGO_AFILIACION, RolAfiliado.class)
					.setParameter(ID_PERSONA, idPersona).setParameter("tipoAfiliado", tipoAfiliado).getSingleResult();
			logger.debug("Finaliza método " + firmaMetodo);
			return afiliado.getOportunidadPago();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCotizanteAporteDetallado(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DetalleDevolucionCotizanteDTO> consultarCotizanteAporteDetallado(List<Long> idsAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCore.consultarCotizanteAporteDetallado(List<Long>)";

		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<DetalleDevolucionCotizanteDTO> detallesCotizantes = new ArrayList<>();

		List<Long> sublistaIds = new ArrayList<>();
		for (Long idAporte : idsAporteDetallado) {
			if (sublistaIds.size() == 2000) {
				detallesCotizantes.addAll(entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_APORTE_DETALLADO,
								DetalleDevolucionCotizanteDTO.class)
						.setParameter(ID_APORTE_DETALLADO, sublistaIds).getResultList());

				sublistaIds.clear();
			}

			sublistaIds.add(idAporte);
		}

		if (!sublistaIds.isEmpty()) {
			detallesCotizantes.addAll(entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_APORTE_DETALLADO,
							DetalleDevolucionCotizanteDTO.class)
					.setParameter(ID_APORTE_DETALLADO, sublistaIds).getResultList());
		}

		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		return detallesCotizantes;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistrosPendientesAfiliar(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RecaudoCotizanteDTO> consultarRegistrosPendientesAfiliar(Long idAporteGeneral) {
		String firmaMetodo = "consultarRegistrosNovedadesGuardadas(Long idRegistroGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<RecaudoCotizanteDTO> recaudos = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_RECAUDO_COTIZANTE_PENDIENTE_AFILIAR,
							RecaudoCotizanteDTO.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).getResultList();
			logger.debug("Finaliza método " + firmaMetodo);
			return recaudos;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String periodoAporte) {
		String firmaMetodo = "ConsultasModeloCore.cotizanteConSubsidioPeriodo(TipoIdentificacionEnum, String, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Boolean result = Boolean.FALSE;

		try {
			StoredProcedureQuery storedProcedure = entityManagerCore
					.createStoredProcedureQuery(NamedQueriesConstants.HAY_SUBSIDIO_PARA_COTIZANTE);
			storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.TIPO_ID_COTIZANTE, String.class,
					ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.NUM_ID_COTIZANTE, String.class,
					ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.PERIODO_APORTE_TEXTO, String.class,
					ParameterMode.IN);

			storedProcedure.setParameter(ConstantesParametrosSp.TIPO_ID_COTIZANTE, tipoIdentificacionCotizante.name());
			storedProcedure.setParameter(ConstantesParametrosSp.NUM_ID_COTIZANTE, numeroIdentificacionCotizante);
			storedProcedure.setParameter(ConstantesParametrosSp.PERIODO_APORTE_TEXTO, periodoAporte);

			storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.TIENE_SUBSIDIO, Boolean.class,
					ParameterMode.OUT);
			storedProcedure.execute();

			result = (Boolean) storedProcedure.getOutputParameterValue(ConstantesParametrosSp.TIENE_SUBSIDIO);

		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " " + e.getMessage());
			throw new TechnicalException("Error durante la consulta de Cotizante con Subsidio" + e.getMessage());
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleRegistroAportesLegalizacion(java.lang.Long,
	 *      java.lang.Long, java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroAportesLegalizacion(Long fechaInicio,
			Long fechaFin, Boolean legalizacion, Boolean otrosIngresos) {
		String firmaMetodo = "ConsultasModeloCore.consultarDetalleRegistroAportesLegalizacion(Long, Long, Boolean)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ResultadoDetalleRegistroDTO> resultadosAportes = null;

		List<DetalleRegistroAportanteDTO> resultadosAportantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTES_REGISTRADOS,
						DetalleRegistroAportanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_LEGALIZACIONES, legalizacion ? 1 : 0)
				.setParameter("otrosIngresos", otrosIngresos ? 1 : 0).getResultList();

		List<DetalleRegistroCotizanteDTO> resultadosCotizantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTES_COTIZANTES_REGISTRADOS,
						DetalleRegistroCotizanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_LEGALIZACIONES, legalizacion ? 1 : 0)
				.setParameter("otrosIngresos", otrosIngresos ? 1 : 0).getResultList();

		logger.info("salio consulta");

		resultadosAportes = organizarSalidaDetalleCierre(resultadosCotizantes, resultadosAportantes);

		logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadosAportes;
	}
	@Override
	public String consultarRadicadoCierre(String idAporteGeneral){
		try {
			Object radicado = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_RADICADO_POR_REGISTRO_GENERAL)
					.setParameter("idAporteGeneral", idAporteGeneral).getSingleResult();
			return radicado.toString();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;

		}
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleRegistroDevoluciones(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroDevoluciones(Long fechaInicio, Long fechaFin) {
		String firmaMetodo = "ConsultasModeloCore.consultarDetalleRegistroDevoluciones(Long, Long)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ResultadoDetalleRegistroDTO> resultadosAportes = null;

		List<DetalleRegistroAportanteDTO> resultadosAportantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DEVOLUCIONES,
						DetalleRegistroAportanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.getResultList();

		List<DetalleRegistroCotizanteDTO> resultadosCotizantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DEVOLUCIONES_COTIZANTES,
						DetalleRegistroCotizanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.getResultList();

		resultadosAportes = organizarSalidaDetalleCierreDevolucionCorrecion(resultadosCotizantes, resultadosAportantes);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadosAportes;
	}

	public Long consultarAporteGeneralCorregido(Long idAporteGeneral){
		try {
			Long aporteGeneral = (Long)entityManagerCore
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_CORRECION)
			.setParameter("idAporteGeneral", idAporteGeneral).getResultList().get(0);

			return aporteGeneral;
		} catch (Exception e) {
			return null;
		}

		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleRegistroCorrecciones(java.lang.Long,
	 *      java.lang.Long, java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroCorrecciones(Long fechaInicio, Long fechaFin,
			Boolean original) {
		String firmaMetodo = "ConsultasModeloCore.consultarDetalleRegistroCorreccionesOrigen(Long, Long, Boolean)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ResultadoDetalleRegistroDTO> resultadosAportes = new ArrayList<>();

		List<DetalleRegistroAportanteDTO> resultadosAportantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCIONES_ORIGEN,
						DetalleRegistroAportanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_ORIGINAL, original ? 1 : 0).getResultList();

		List<DetalleRegistroCotizanteDTO> resultadosCotizantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCIONES_ORIGEN_COTIZANTES,
						DetalleRegistroCotizanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_ORIGINAL, original ? 1 : 0).getResultList();

		resultadosAportes = organizarSalidaDetalleCierreDevolucionCorrecion(resultadosCotizantes, resultadosAportantes);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadosAportes;
	}


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDetalleRegistroCorrecciones(java.lang.Long,
	 *      java.lang.Long, java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroCorreccionesALaAlta(Long fechaInicio, Long fechaFin,
			Boolean original) {
		String firmaMetodo = "ConsultasModeloCore.consultarDetalleRegistroCorreccionesOrigen(Long, Long, Boolean)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ResultadoDetalleRegistroDTO> resultadosAportes = new ArrayList<>();

		List<DetalleRegistroAportanteDTO> resultadosAportantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCIONES_A_LA_ALTA_ORIGEN,
						DetalleRegistroAportanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_ORIGINAL, original ? 1 : 0).getResultList();

		List<DetalleRegistroCotizanteDTO> resultadosCotizantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CORRECCIONES_A_LA_ALTA_ORIGEN_COTIZANTES,
						DetalleRegistroCotizanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_ORIGINAL, original ? 1 : 0).getResultList();

		resultadosAportes = organizarSalidaDetalleCierreCorreccionALaAlta(resultadosCotizantes, resultadosAportantes);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadosAportes;
	}

	/**
	 * @param resultadosCotizantes
	 * @param resultadosAportantes
	 * @return
	 */
	private List<ResultadoDetalleRegistroDTO> organizarSalidaDetalleCierre(
			List<DetalleRegistroCotizanteDTO> resultadosCotizantes,
			List<DetalleRegistroAportanteDTO> resultadosAportantes) {

		String firmaMetodo = "ConsultasModeloCore.organizarSalidaDetalleCierre(List<DetalleRegistroCotizanteDTO>, "
				+ "List<DetalleRegistroAportanteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		Map<String, DetalleRegistroAportanteDTO> nuevosAportantes = new HashMap<String, DetalleRegistroAportanteDTO>();
		Map<String, DetalleRegistroCotizanteDTO> nuevosCotizantes = new HashMap<String, DetalleRegistroCotizanteDTO>();

		for (DetalleRegistroAportanteDTO aportante : resultadosAportantes) {
			if (!nuevosAportantes.containsKey(Long.toString(aportante.getNumeroOperacion()))) {
				nuevosAportantes.put(Long.toString(aportante.getNumeroOperacion()), aportante);
			}
		}

		for (DetalleRegistroCotizanteDTO cotizante : resultadosCotizantes) {
			if (!nuevosCotizantes.containsKey(Long.toString(cotizante.getNumeroOperacion()))) {
				nuevosCotizantes.put(Long.toString(cotizante.getNumeroOperacion()), cotizante);
			}
		}

		List<ResultadoDetalleRegistroDTO> result = new ArrayList<>();

		Map<Long, List<DetalleRegistroCotizanteDTO>> detallesPorAporte = new HashMap<>();
		// se organizan los resultados de cotizantes en un mapa por ID de
		// aporte general
		List<DetalleRegistroCotizanteDTO> listaCotizantes = null;
		for (DetalleRegistroCotizanteDTO cotizante : new ArrayList<DetalleRegistroCotizanteDTO>(
				nuevosCotizantes.values())) {
			listaCotizantes = detallesPorAporte.get(cotizante.getIdAporteGeneral());
			if (listaCotizantes == null) {
				listaCotizantes = new ArrayList<>();
				detallesPorAporte.put(cotizante.getIdAporteGeneral(), listaCotizantes);
			}

			listaCotizantes.add(cotizante);
		}

		// se preparan los DTO de salida
		ResultadoDetalleRegistroDTO conjuntoAporte = null;
		for (DetalleRegistroAportanteDTO aporte : new ArrayList<DetalleRegistroAportanteDTO>(
				nuevosAportantes.values())) {

			conjuntoAporte = new ResultadoDetalleRegistroDTO();
			conjuntoAporte.setAportante(aporte);
			conjuntoAporte.setCotizantes(detallesPorAporte.get(aporte.getNumeroOperacion()));
			result.add(conjuntoAporte);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * @param resultadosCotizantes
	 * @param resultadosAportantes
	 * @return
	 */
	private List<ResultadoDetalleRegistroDTO> organizarSalidaDetalleCierreDevolucionCorrecion(
			List<DetalleRegistroCotizanteDTO> resultadosCotizantes,
			List<DetalleRegistroAportanteDTO> resultadosAportantes) {

		String firmaMetodo = "ConsultasModeloCore.organizarSalidaDetalleCierre(List<DetalleRegistroCotizanteDTO>, "
				+ "List<DetalleRegistroAportanteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<String, DetalleRegistroAportanteDTO> nuevosAportantes = new HashMap<String, DetalleRegistroAportanteDTO>();
		Map<String, DetalleRegistroCotizanteDTO> nuevosCotizantes = new HashMap<String, DetalleRegistroCotizanteDTO>();

		for (DetalleRegistroAportanteDTO aportante : resultadosAportantes) {
			if (!nuevosAportantes.containsKey(Long.toString(aportante.getNumeroOperacion()))) {
				nuevosAportantes.put(Long.toString(aportante.getNumeroOperacion()), aportante);
			} else {
				if (nuevosAportantes.get(Long.toString(aportante.getNumeroOperacion())) != null) {
					if (nuevosAportantes.get(Long.toString(aportante.getNumeroOperacion())).getNumeroOperacion()
							.toString().equals(Long.toString(aportante.getNumeroOperacion())) &&
							nuevosAportantes.containsKey(Long.toString(aportante.getNumeroOperacion()))) {
						DetalleRegistroAportanteDTO auxAporte = nuevosAportantes
								.get(Long.toString(aportante.getNumeroOperacion()));
						auxAporte.setMonto(auxAporte.getMonto().add(aportante.getMonto()));
						auxAporte.setInteres(auxAporte.getInteres().add(aportante.getInteres()));
						auxAporte.setTotalAporte(auxAporte.getTotalAporte().add(aportante.getTotalAporte()));
						nuevosAportantes.put(Long.toString(aportante.getNumeroOperacion()), auxAporte);
					}
				}
			}
		}

		for (DetalleRegistroCotizanteDTO cotizante : resultadosCotizantes) {
			logger.info("cotizante: " + cotizante.getNumeroOperacion());
			logger.info("monto: " + cotizante.getMonto());
			logger.info("condicional: " + nuevosCotizantes.containsKey(Long.toString(cotizante.getNumeroOperacion())));
			if (!nuevosCotizantes.containsKey(Long.toString(cotizante.getNumeroOperacion()))) {
				nuevosCotizantes.put(Long.toString(cotizante.getNumeroOperacion()), cotizante);
			} else {
				if (nuevosCotizantes.get(Long.toString(cotizante.getNumeroOperacion())) != null) {
					if (nuevosCotizantes.get(Long.toString(cotizante.getNumeroOperacion())).getNumeroOperacion()
							.toString().equals(Long.toString(cotizante.getNumeroOperacion())) &&
							nuevosCotizantes.containsKey(Long.toString(cotizante.getNumeroOperacion()))) {
						DetalleRegistroCotizanteDTO auxCot = nuevosCotizantes
								.get(Long.toString(cotizante.getNumeroOperacion()));
						auxCot.setMonto(auxCot.getMonto().add(cotizante.getMonto()));
						auxCot.setInteres(auxCot.getInteres().add(cotizante.getInteres()));
						auxCot.setTotalAporte(auxCot.getTotalAporte().add(cotizante.getTotalAporte()));
						nuevosCotizantes.put(Long.toString(auxCot.getNumeroOperacion()), auxCot);
					}
				}
			}
		}

		List<ResultadoDetalleRegistroDTO> result = new ArrayList<>();

		Map<Long, List<DetalleRegistroCotizanteDTO>> detallesPorAporte = new HashMap<>();
		// se organizan los resultados de cotizantes en un mapa por ID de
		// aporte general
		List<DetalleRegistroCotizanteDTO> listaCotizantes = null;
		for (DetalleRegistroCotizanteDTO cotizante : new ArrayList<DetalleRegistroCotizanteDTO>(
				nuevosCotizantes.values())) {
			listaCotizantes = detallesPorAporte.get(cotizante.getIdAporteGeneral());
			if (listaCotizantes == null) {
				listaCotizantes = new ArrayList<>();
				detallesPorAporte.put(cotizante.getIdAporteGeneral(), listaCotizantes);
			}

			listaCotizantes.add(cotizante);
		}

		// se preparan los DTO de salida
		ResultadoDetalleRegistroDTO conjuntoAporte = null;
		for (DetalleRegistroAportanteDTO aporte : new ArrayList<DetalleRegistroAportanteDTO>(
				nuevosAportantes.values())) {

			conjuntoAporte = new ResultadoDetalleRegistroDTO();
			conjuntoAporte.setAportante(aporte);
			conjuntoAporte.setCotizantes(detallesPorAporte.get(aporte.getNumeroOperacion()));
			result.add(conjuntoAporte);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	private List<ResultadoDetalleRegistroDTO> organizarSalidaDetalleCierreCorreccionALaAlta(
			List<DetalleRegistroCotizanteDTO> resultadosCotizantes,
			List<DetalleRegistroAportanteDTO> resultadosAportantes) {

		String firmaMetodo = "ConsultasModeloCore.organizarSalidaDetalleCierre(List<DetalleRegistroCotizanteDTO>, "
				+ "List<DetalleRegistroAportanteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);	

		List<ResultadoDetalleRegistroDTO> result = new ArrayList<>();

		// se organizan los resultados de cotizantes en un mapa por ID de
		// aporte general


		// se preparan los DTO de salida
		ResultadoDetalleRegistroDTO conjuntoAporte = null;
		for (DetalleRegistroAportanteDTO aporte : resultadosAportantes) {
			conjuntoAporte = new ResultadoDetalleRegistroDTO();
			conjuntoAporte.setAportante(aporte);
			conjuntoAporte.setCotizantes(resultadosCotizantes.stream()
            .filter(cotizante -> cotizante.getNumPlanilla().equals(aporte.getRecaudoOriginal()))
            .collect(Collectors.toList()));
			result.add(conjuntoAporte);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarConciliadoAporteGeneral(java.lang.String)
	 */
	@Override
	public void actualizarConciliadoAporteGeneral(String idsAporteGeneral) {
		String firmaMetodo = "actualizarConciliadoAporteGeneral(String idsAporteGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			if (idsAporteGeneral != null && !idsAporteGeneral.isEmpty()) {
				List<Long> listaIdsAportes = new ArrayList<>();
				if (idsAporteGeneral.contains("|")) {
					List<String> listaAportes = Arrays.asList(idsAporteGeneral.split("|"));

					for (String id : listaAportes) {
						if(!id.equals("|")){
							listaIdsAportes.add(Long.parseLong(id));

						}
					}
				} else {
					listaIdsAportes.add(Long.parseLong(idsAporteGeneral));
				}
				if(listaIdsAportes.size() > 2000){
					while(listaIdsAportes.size() > 2000){
					entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_MARCA_CONCILIADO_APORTE_GENERAL)
						.setParameter("idsAporteGeneral", listaIdsAportes.subList(0,1999)).executeUpdate();
					listaIdsAportes.subList(0,1999).clear();
					}
					if(listaIdsAportes.size() > 0 && listaIdsAportes.size() <=2000)
					entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_MARCA_CONCILIADO_APORTE_GENERAL)
						.setParameter("idsAporteGeneral", listaIdsAportes).executeUpdate();
				}else{
					entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_MARCA_CONCILIADO_APORTE_GENERAL)
						.setParameter("idsAporteGeneral", listaIdsAportes).executeUpdate();
				}
				
			}
			logger.debug("Finaliza método " + firmaMetodo);
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarHistoricoNovedadesRecientes(java.util.Date,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedadesRecientes(Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia consultarHistoricoNovedades");
		try {
			List<HistoricoNovedadesDTO> novedades = new ArrayList<>();
			novedades = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES_RECIENTES,
							HistoricoNovedadesDTO.class)
					.setParameter(FECHA_FIN, fechaFin).setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).setMaxResults(10).getResultList();
			novedades.addAll(
					consultarHistoricoNovedadesRetiro(null, fechaFin, tipoIdentificacion, numeroIdentificacion));
			if (!novedades.isEmpty() && novedades.size() > 10) {
				Collections.sort(novedades, (o1, o2) -> o2.getFechaRegistro().compareTo(o1.getFechaRegistro()));
			}
			logger.debug("Finaliza consultarHistoricoNovedades");
			return novedades.size() > 10 ? novedades.subList(0, 9) : novedades;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoNovedades: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarNovedadesSinRolAfiliado(java.util.Date,
	 *      java.util.Date,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<HistoricoNovedadesDTO> consultarNovedadesSinRolAfiliado(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia consultarHistoricoNovedadesSinRolAfiliado");
		try {
			List<HistoricoNovedadesDTO> novedades = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES_SIN_ROL_AFILIADO,
							HistoricoNovedadesDTO.class)
					.setParameter(FECHA_INICIO, fechaInicio).setParameter(FECHA_FIN, fechaFin)
					.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
					.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getResultList();
			// novedades.addAll(consultarHistoricoNovedadesRetiro(fechaInicio,
			// fechaFin, tipoIdentificacion, numeroIdentificacion));
			logger.debug("Finaliza consultarHistoricoNovedadesSinRolAfiliado)");
			return novedades;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoNovedadesSinRolAfiliado: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCotizantesCorrecciones(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, String> consultarCotizantesCorrecciones(List<Long> idsAporteGeneral) {
		String firmaMetodo = "ConsultasModeloCore.consultarCotizantesCorrecciones(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, String> result = new HashMap<>();

		try {
			// se consultan los registros en paquetes de 1000 registros
			Integer inicio = 0;
			Integer fin = idsAporteGeneral.size() > 1000 ? 1000 : idsAporteGeneral.size();
			while (fin <= idsAporteGeneral.size()) {
				List<Long> idsTemp = idsAporteGeneral.subList(inicio, fin);

				List<Object[]> idsCotizantes = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_COTIZANTE_CORRECCIONES)
						.setParameter(IDS_APORTE_GENERAL, idsTemp).getResultList();

				for (Object[] resultado : idsCotizantes) {
					Long idApg = ((BigInteger) resultado[0]).longValue();
					Long idApd = ((BigInteger) resultado[1]).longValue();

					StringBuilder valor = null;

					if (!result.containsKey(idApg)) {
						result.put(idApg, idApd.toString());
					} else {
						valor = new StringBuilder(result.get(idApg));
						valor.append(",");
						valor.append(idApd.toString());
					}

				}

				inicio = fin;
				fin++;
				if (fin <= idsAporteGeneral.size()) {
					fin = fin + 1000 <= idsAporteGeneral.size() ? inicio + 1000 : idsAporteGeneral.size();
				}
			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDatosAportante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DatosAportanteDTO consultarDatosAportante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionCotizante) {
		try {
			logger.debug("Inicia consultarDatosAportante");
			DatosAportanteDTO aportante = new DatosAportanteDTO();

			List<DatosAportanteDTO> aportantePersona = new ArrayList<>();
			List<DatosAportanteDTO> aportanteEmpresa = new ArrayList<>();

			if (numeroIdentificacion != null && numeroIdentificacionCotizante != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_APO_COT)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante).setMaxResults(1)
						.getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA_APO_COT)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante).setMaxResults(1)
						.getResultList();
			} else if (numeroIdentificacion != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).setMaxResults(1).getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion.name())
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion).getResultList();
			} else if (numeroIdentificacionCotizante != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_COTIZANTE)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante).setMaxResults(1)
						.getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA_COTIZANTE)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante).setMaxResults(1)
						.getResultList();
			}

			if (!aportanteEmpresa.isEmpty()) {
				aportantePersona.addAll(aportanteEmpresa);
			}
			aportantePersona.sort(Comparator.comparing(DatosAportanteDTO::getFechaProcesamiento)
					.thenComparing(DatosAportanteDTO::getPeriodoAporte));
			if (!aportantePersona.isEmpty()) {
				aportante = aportantePersona.get(0);

				List<Long> ids = new ArrayList<>();
				ids.add(aportante.getIdAporteGeneral());
				List<DetalleDatosAportanteDTO> detalleAportante = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_APORTANTE,
								DetalleDatosAportanteDTO.class)
						.setParameter(ID_APORTE_GENERAL, ids).getResultList();
				aportante.setDatosAportes(detalleAportante);
			}
			logger.debug("Finaliza consultarDatosAportante");
			return aportante;
		} catch (NoResultException nre) {
			logger.debug("Finaliza consultarDatosAportante");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarDatosAportante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarHistoricoDatosAportante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public DatosAportanteDTO consultarHistoricoDatosAportante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionCotizante, Long periodoInicio, Long periodoFin) {
		try {
			logger.debug("Inicia consultarHistoricoDatosAportante");
			List<DatosAportanteDTO> aportantePersona = new ArrayList<>();
			List<DatosAportanteDTO> aportanteEmpresa = new ArrayList<>();
			DatosAportanteDTO aportante = new DatosAportanteDTO();

			String periodoInicial;
			String periodoFinal;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

			if (periodoInicio != null) {
				periodoInicial = dateFormat.format(new Date(periodoInicio));
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, -1);
				periodoInicial = dateFormat.format(calendar.getTime());
			}

			if (periodoFin != null) {
				periodoFinal = dateFormat.format(new Date(periodoFin));
			} else {
				periodoFinal = dateFormat.format(new Date());
			}

			if (numeroIdentificacion != null && numeroIdentificacionCotizante != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_APO_COT,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS_APO_COT,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
			} else if (numeroIdentificacion != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
			} else if (numeroIdentificacionCotizante != null) {
				aportantePersona = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_COTIZANTE,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
				aportanteEmpresa = (List<DatosAportanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS_COTIZANTE,
								DatosAportanteDTO.class)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter("numeroIdentificacionCot", numeroIdentificacionCotizante)
						.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
						.setMaxResults(12).getResultList();
			}

			List<Long> ids = new ArrayList<>();
			if (!aportanteEmpresa.isEmpty()) {
				aportantePersona.addAll(aportanteEmpresa);
			}
			aportantePersona.sort(Comparator.comparing(DatosAportanteDTO::getFechaProcesamiento)
					.thenComparing(DatosAportanteDTO::getPeriodoAporte));

			if (!aportantePersona.isEmpty()) {
				int indiceDatoAportante = 0;
				int tamañoLista = 0;
				if (aportantePersona.size() >= 12) {
					tamañoLista = 12;
				} else {
					tamañoLista = aportantePersona.size();
				}

				Long idPersonaAportante = 0L;
				for (int i = 0; i < tamañoLista; i++) {
					if (indiceDatoAportante == 0) {
						aportante.setTipoIdentificacionAportante(
								aportantePersona.get(i).getTipoIdentificacionAportante());
						aportante.setNumeroIdentificacionAportante(
								aportantePersona.get(i).getNumeroIdentificacionAportante());
						aportante.setRazonSocial(aportantePersona.get(i).getRazonSocial());
						idPersonaAportante = aportantePersona.get(i).getIdPersonaAportante();
						indiceDatoAportante++;
					}
					ids.add(aportantePersona.get(i).getIdAporteGeneral());
				}

				List<DetalleDatosAportanteDTO> detalleAportante = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_APORTANTE,
								DetalleDatosAportanteDTO.class)
						.setParameter(ID_APORTE_GENERAL, ids).getResultList();

				for (DetalleDatosAportanteDTO detalleDatosAportanteDTO : detalleAportante) {
					try {
						if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
								.equals(detalleDatosAportanteDTO.getTipoAportante())) {
							Short diaVencimientoAporte = entityManagerCore
									.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIA_VENCIMIENTO_APORTE_AFILIADO,
											Short.class)
									.setParameter(ID_PERSONA, idPersonaAportante).getSingleResult();
							detalleDatosAportanteDTO.setDiaHabilVencimientoAporte(diaVencimientoAporte);
						} else {
							Short diaVencimientoAporte = entityManagerCore
									.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIA_VENCIMIENTO_APORTE_EMPLEADOR,
											Short.class)
									.setParameter(ID_PERSONA, idPersonaAportante).getSingleResult();
							detalleDatosAportanteDTO.setDiaHabilVencimientoAporte(diaVencimientoAporte);
						}
					} catch (NoResultException e) {

					}
				}

				aportante.setDatosAportes(detalleAportante);
			}
			logger.debug("Finaliza consultarHistoricoDatosAportante");
			return aportante;
		} catch (NoResultException nre) {
			logger.debug("Finaliza consultarHistoricoDatosAportante");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoDatosAportante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDatosCotizante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DatosCotizanteIntegracionDTO consultarDatosCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionAportante) {
		try {
			logger.debug("Inicia consultarDatosCotizante");
			DatosCotizanteIntegracionDTO cotizante;
			List<DetalleDatosCotizanteDTO> detalleCotizanteUnico = new ArrayList<>();

			try {
				cotizante = (DatosCotizanteIntegracionDTO) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_COTIZANTE_PERSONA)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionApo", numeroIdentificacionAportante)
						.setMaxResults(1)
						.getSingleResult();

			} catch (NoResultException e) {
				cotizante = (DatosCotizanteIntegracionDTO) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_COTIZANTE_EMPRESA)
						.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
						.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
						.setParameter("numeroIdentificacionApo", numeroIdentificacionAportante)
						.setMaxResults(1)
						.getSingleResult();
			}

			if (cotizante != null) {
				List<DetalleDatosCotizanteDTO> detalleCotizante = (List<DetalleDatosCotizanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_COTIZANTE,
								DetalleDatosCotizanteDTO.class)
						.setParameter(ID_PERSONA, cotizante.getIdPersona()).setMaxResults(1).getResultList();

				List<DetalleDatosCotizanteDTO> detalleCotizanteEmpresa = (List<DetalleDatosCotizanteDTO>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_COTIZANTE_EMPRESA)
						.setParameter(ID_PERSONA, cotizante.getIdPersona()).setMaxResults(1).getResultList();

				if (detalleCotizanteEmpresa != null && !detalleCotizanteEmpresa.isEmpty()) {
					detalleCotizante.addAll(detalleCotizanteEmpresa);
				}

				detalleCotizante.sort(Comparator.comparing(DetalleDatosCotizanteDTO::getFechaProcesamiento)
						.thenComparing(DetalleDatosCotizanteDTO::getPeriodoAporte));
				detalleCotizanteUnico.add(detalleCotizante.get(0));
				cotizante.setDatosAportesCotizante(detalleCotizanteUnico);
			}

			logger.debug("Finaliza consultarDatosCotizante");
			return cotizante;
		} catch (NoResultException nre) {
			logger.debug("Finaliza consultarDatosCotizante");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarDatosCotizante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarHistoricoDatosCotizante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public DatosCotizanteIntegracionDTO consultarHistoricoDatosCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionAportante, Long periodoInicio, Long periodoFin) {
				try {
					logger.debug("Inicia consultarHistoricoDatosCotizante con parámetros");
					DatosCotizanteIntegracionDTO cotizante;
		
					try {
						cotizante = entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_COTIZANTE_PERSONA,
										DatosCotizanteIntegracionDTO.class)
								.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
								.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
								.setParameter("numeroIdentificacionApo", numeroIdentificacionAportante).setMaxResults(1)
								.getSingleResult();
					} catch (NoResultException e) {
						cotizante = (DatosCotizanteIntegracionDTO) entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_COTIZANTE_EMPRESA)
								.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion)
								.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
								.setParameter("numeroIdentificacionApo", numeroIdentificacionAportante).setMaxResults(1)
								.getSingleResult();
					}

					if (cotizante != null) {
						
						String periodoInicial;
						String periodoFinal;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		
						if (periodoInicio != null) {
							periodoInicial = dateFormat.format(new Date(periodoInicio));
						} else {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.add(Calendar.YEAR, -6);
							periodoInicial = dateFormat.format(calendar.getTime());
						}
		
						if (periodoFin != null) {
							periodoFinal = dateFormat.format(new Date(periodoFin));
						} else {
							periodoFinal = dateFormat.format(new Date());
						}
						List<DetalleDatosCotizanteDTO> detalleCotizantePersona = (List<DetalleDatosCotizanteDTO>) entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_COTIZANTE_PERIODOS,
										DetalleDatosCotizanteDTO.class)
								.setParameter(ID_PERSONA, cotizante.getIdPersona())
								.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
								.setMaxResults(10).getResultList();
						List<DetalleDatosCotizanteDTO> detalleCotizanteEmpresa = (List<DetalleDatosCotizanteDTO>) entityManagerCore
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_DATOS_COTIZANTE_PERIODOS_EMPRESA,
										DetalleDatosCotizanteDTO.class)
								.setParameter(ID_PERSONA, cotizante.getIdPersona())
								.setParameter("periodoInicial", periodoInicial).setParameter("periodoFinal", periodoFinal)
								.setMaxResults(10).getResultList();
		
						if (detalleCotizanteEmpresa != null && !detalleCotizanteEmpresa.isEmpty()) {
							detalleCotizantePersona.addAll(detalleCotizanteEmpresa);
						}
						detalleCotizantePersona.sort(Comparator.comparing(DetalleDatosCotizanteDTO::getFechaProcesamiento)
								.thenComparing(DetalleDatosCotizanteDTO::getPeriodoAporte));
						List<DetalleDatosCotizanteDTO> detalleCotizante = new ArrayList<>();
		
						int tamañoLista = 0;
						if (detalleCotizantePersona.size() >= 10) {
							tamañoLista = 10;
						} else {
							tamañoLista = detalleCotizantePersona.size();
						}
						for (int i = 0; i < tamañoLista; i++) {
							logger.info(detalleCotizantePersona.get(i).getTipoCotizante()+"getTipoCotizante");
							detalleCotizante.add(detalleCotizantePersona.get(i));
						}
						cotizante.setDatosAportesCotizante(detalleCotizante);
					}
					logger.debug("Finaliza consultarHistoricoDatosCotizante");
					return cotizante;
				} catch (NoResultException nre) {
			logger.debug("Finaliza consultarHistoricoDatosCotizante");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarHistoricoDatosCotizante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteDetalladoPorRegistroDetallado(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorRegistroDetallado(Long idRegistroDetallado) {
		String firmaMetodo = "ConsultasModeloCore.consultarAporteDetalladoPorRegistroDetallado(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AporteDetalladoModeloDTO> result = null;
		List<AporteDetallado> resultEntity = null;

		try {
			resultEntity = (List<AporteDetallado>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_REGISTRO_DETALLADO)
					.setParameter(ID_REGISTRO_DETALLADO, idRegistroDetallado).getResultList();

			result = new ArrayList<>();
		} catch (NoResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		for (AporteDetallado aporteDetallado : resultEntity) {
			AporteDetalladoModeloDTO aporteDetalladoModeloDTO = new AporteDetalladoModeloDTO();
			aporteDetalladoModeloDTO.convertToDTO(aporteDetallado);
			result.add(aporteDetalladoModeloDTO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarAportesReprocesados(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarAportesReprocesados(List<AporteDetalladoModeloDTO> aportes) {
		String firmaMetodo = "ConsultasModeloCore.actualizarAportesReprocesados(List<AporteDetalladoModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		for (AporteDetalladoModeloDTO aporteDetalladoModeloDTO : aportes) {
			AporteDetallado aporte = aporteDetalladoModeloDTO.convertToEntity();

			try {
				entityManagerCore.merge(aporte);
			} catch (Exception e) {
				logger.error(
						ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la actualización: ",
						e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesConCorrecciones(java.lang.Long,
	 *      java.lang.Long,
	 *      com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum,
	 *      java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarAportesConCorrecciones(Long idEmpresa, Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodoRecaudo, Long fechaInicio, Long fechaFin,
			TipoMovimientoRecaudoAporteEnum tipoMovimiento) {

		logger.debug("Inicia consultarAportesConCorrecciones");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String periodo = null;
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		if (periodoRecaudo != null) {
			periodo = dateFormat.format(new Date(periodoRecaudo));
		}
		if (fechaInicio != null) {
			fechaInicioDate = new Date(fechaInicio);
		}
		if (fechaFin != null) {
			LocalDate localDateBase = Instant.ofEpochMilli(fechaFin).atZone(ZoneId.systemDefault()).toLocalDate();
			localDateBase = localDateBase.plusDays(1L);
			Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;
			fechaFinDate = new Date(endDate);
		}
		logger.info("idPersona "+idPersona);
		logger.info("idEmpresa "+idEmpresa);
		logger.info("tipoSolicitante "+tipoSolicitante);
		logger.info("tipoMovimiento "+tipoMovimiento);
		logger.info("periodo "+periodo);
		logger.info("fechaInicioDate "+fechaInicioDate);
		logger.info("fechaFinDate "+fechaFinDate);
		List<Long> idsAportes =  entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_CON_CORRECCIONES_DEVOLUCIONES)
				.setParameter(ID_PERSONA, idPersona != null ?Long.toString(idPersona):null).setParameter(ID_EMPRESA, idEmpresa != null ? Long.toString(idEmpresa) : null)
				.setParameter(TIPO_SOLICITANTE, tipoSolicitante!= null ? tipoSolicitante.name() : null).setParameter("tipoMovimiento", tipoMovimiento != null ? tipoMovimiento.name() : null)
				.setParameter("periodo", periodo).setParameter(FECHA_INICIO, fechaInicioDate)
				.setParameter(FECHA_FIN, fechaFinDate).getResultList();

		logger.debug("Finaliza consultarAportesConCorrecciones");
		return idsAportes;

	}
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarAportesConCorreccionesEmpleador(Long idEmpresa, Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodoRecaudo, Long fechaInicio, Long fechaFin,
			TipoMovimientoRecaudoAporteEnum tipoMovimiento) {

		logger.debug("Inicia consultarAportesConCorrecciones");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String periodo = null;
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		if (periodoRecaudo != null) {
			periodo = dateFormat.format(new Date(periodoRecaudo));
		}
		if (fechaInicio != null) {
			fechaInicioDate = new Date(fechaInicio);
		}
		if (fechaFin != null) {
			LocalDate localDateBase = Instant.ofEpochMilli(fechaFin).atZone(ZoneId.systemDefault()).toLocalDate();
			localDateBase = localDateBase.plusDays(1L);
			Long endDate = localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;
			fechaFinDate = new Date(endDate);
		}
		logger.info("idPersona "+idPersona);
		logger.info("idEmpresa "+idEmpresa);
		logger.info("tipoSolicitante "+tipoSolicitante);
		logger.info("tipoMovimiento "+tipoMovimiento);
		logger.info("periodo "+periodo);
		logger.info("fechaInicioDate "+fechaInicioDate);
		logger.info("fechaFinDate "+fechaFinDate);
		List<Long> idsAportes =  entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_CON_CORRECCIONES_DEVOLUCIONES_EMPLEADOR)
				.setParameter(ID_EMPRESA, idEmpresa != null ? Long.toString(idEmpresa) : null)
				.setParameter("tipoMovimiento", tipoMovimiento != null ? tipoMovimiento.name() : null)
				.setParameter("periodo", periodo).setParameter(FECHA_INICIO, fechaInicioDate)
				.setParameter(FECHA_FIN, fechaFinDate).getResultList();

		logger.debug("Finaliza consultarAportesConCorrecciones");
		return idsAportes;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDatosAfiliadoServicios(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 *      java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DatosAfiliadoServiciosDTO consultarDatosAfiliadoServicios(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliacion, Date periodoEnFecha) {
		String firmaMetodo = "ConsultasModeloCore.consultarDatosAfiliadoServicios(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosAfiliadoServiciosDTO result = null;

		try {
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIADO_SERVICIOS,
							DatosAfiliadoServiciosDTO.class)
					.setParameter("sTipoID", tipoIdentificacion.name()).setParameter("sNumID", numeroIdentificacion)
					.setParameter("sTipoCotiz", tipoAfiliacion.name()).setParameter("dFechaPeriodo", periodoEnFecha)
					.getSingleResult();

		} catch (NoResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return null;
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#ejecutarCalculoFechaHabil(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Short)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Date ejecutarCalculoFechaHabil(Integer iMes, Integer iAnio, Short iDiaHabil) {
		String firmaMetodo = "ConsultasModeloCore.ejecutarCalculoFechaHabil(Integer, Integer, Short)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Date result = null;

		try {
			StoredProcedureQuery query = entityManagerCore
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_CALCULO_FECHA_DIA_HABIL);

			query.setParameter("iMes", iMes);
			query.setParameter("iAnio", iAnio);
			query.setParameter("iDiaHabil", iDiaHabil);

			query.execute();

			result = (Date) query.getOutputParameterValue("dFechaHabil");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en el SP: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEstadoAporteMasRecientePeriodo(com.asopagos.aportes.dto.DatosAfiliadoServiciosDTO,
	 *      java.lang.String, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DatosAfiliadoServiciosDTO consultarEstadoAporteMasRecientePeriodo(DatosAfiliadoServiciosDTO datosAfiliado,
			String periodoCalculo, Date fechaCalendarioVencimiento) {
		String firmaMetodo = "ConsultasModeloCore.consultarEstadoAporteMasRecientePeriodo(DatosAfiliadoServiciosDTO, String, Date)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosAfiliadoServiciosDTO datosActualizados = datosAfiliado;

		try {
			Object[] resultadoConsulta = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_APORTE_CALCULO_SERVICIOS)
					.setParameter("iIdPersona", datosAfiliado.getIdPersona())
					.setParameter("sTipoCotiz", datosAfiliado.getTipoAfiliado().name())
					.setParameter("sPeriodo", periodoCalculo)
					// .setParameter("dFechaHabil", fechaCalendarioVencimiento)
					.getSingleResult();

			datosActualizados.setEstadoAporte(EstadoRegistroAportesArchivoEnum.valueOf((String) resultadoConsulta[0]));
			datosActualizados.setIdRegistroGeneral(((BigInteger) resultadoConsulta[1]).longValue());
		} catch (NoResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return datosActualizados;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return datosActualizados;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEstadoAfiliacionPorTipoAfiliacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoAfiliadoEnum consultarEstadoAfiliacionPorTipoAfiliacion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {
		String firmaMetodo = "ConsultasModeloCore.consultarEstadoAfiliacionPorTipoAfiliacion(TipoIdentificacionEnum, String, "
				+ "TipoAfiliadoEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		EstadoAfiliadoEnum result = null;

		try {
			Query query = null;
			switch (tipoAfiliado) {
				case PENSIONADO:
					query = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_PENSIONADO);
					break;
				case TRABAJADOR_INDEPENDIENTE:
					query = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_INDEPENDIENTE);
					break;
				default:
					logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
					return null;
			}

			String estado = (String) query.setParameter("sTipoID", tipoIdentificacion.name())
					.setParameter("sNumID", numeroIdentificacion).getSingleResult();

			result = estado != null ? EstadoAfiliadoEnum.valueOf(estado) : null;

		} catch (NoResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return null;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEstadoRegistros(java.lang.Long)
	 */
	@Override
	public List<RecaudoCotizanteDTO> consultarEstadoRegistros(Long idAporteGeneral,
			List<EstadoRegistroAportesArchivoEnum> estadoRegistro) {
		String firmaMetodo = "consultarRegistrosOK(Long idRegistroGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<RecaudoCotizanteDTO> recaudos = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_RECAUDO_COTIZANTE_APORTES,
							RecaudoCotizanteDTO.class)
					.setParameter(ID_APORTE_GENERAL, idAporteGeneral).setParameter(ESTADO_REGISTRO, estadoRegistro)
					.getResultList();
			logger.debug("Finaliza método " + firmaMetodo);
			return recaudos;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDevolucionesVista360(java.util.List,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DevolucionVistasDTO> consultarSolicitudDevolucion(List<Long> idsAporteGeneral) {
		String firmaMetodo = "consultarSolicitudDevolucion(List<Long> idsAporteGeneral)";
		try {
			logger.debug("Inicia método " + firmaMetodo);
			List<Object[]> solicitudDevolucionAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_DEVOLUCION_IDS_APORTES)
					.setParameter("idsAporteGeneral", idsAporteGeneral)
					.setParameter("tipoAjuste", TipoAjusteMovimientoAporteEnum.DEVOLUCION.name()).getResultList();

			List<DevolucionVistasDTO> solicitudesDevoluciones = new ArrayList<>();
			Long idSolicitudTemporal = 0L;
			DevolucionVistasDTO devolucion = new DevolucionVistasDTO();
			BigDecimal monto = BigDecimal.ZERO;
			BigDecimal interes = BigDecimal.ZERO;
			List<Long> idsAporte = new ArrayList<>();
			for (int i = 0; i < solicitudDevolucionAporte.size(); i++) {
				Object[] solicitudDevolucion = solicitudDevolucionAporte.get(i);
				if (idSolicitudTemporal != Long.parseLong(solicitudDevolucion[1].toString())) {
					devolucion = new DevolucionVistasDTO();
					monto = BigDecimal.ZERO;
					interes = BigDecimal.ZERO;
					idsAporte = new ArrayList<>();
					idsAporte.add(Long.parseLong(solicitudDevolucion[0].toString()));
					devolucion.setIdAporteGeneral(idsAporte);
					devolucion.setIdSolicitudDevolucionAporte(Long.parseLong(solicitudDevolucion[1].toString()));
					devolucion.setIdSolicitud(Long.parseLong(solicitudDevolucion[2].toString()));
					monto = solicitudDevolucion[3] != null ? (BigDecimal) solicitudDevolucion[3] : BigDecimal.ZERO;
					interes = solicitudDevolucion[4] != null ? (BigDecimal) solicitudDevolucion[4] : BigDecimal.ZERO;
					devolucion.setMontoDevolucion(monto);
					devolucion.setInteresesDevolucion(interes);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date fecha = df.parse(solicitudDevolucion[5].toString());
					devolucion.setFechaRadicacion(fecha);
					devolucion.setTotalDevolucion(monto.add(interes));
					idSolicitudTemporal = Long.parseLong(solicitudDevolucion[1].toString());
				} else {
					idsAporte.add(Long.parseLong(solicitudDevolucion[0].toString()));
					devolucion.setIdAporteGeneral(idsAporte);
					monto = solicitudDevolucion[3] != null ? monto.add((BigDecimal) solicitudDevolucion[3])
							: monto.add(BigDecimal.ZERO);
					interes = solicitudDevolucion[4] != null ? interes.add((BigDecimal) solicitudDevolucion[4])
							: interes.add(BigDecimal.ZERO);
					devolucion.setMontoDevolucion(monto);
					devolucion.setInteresesDevolucion(interes);
					devolucion.setTotalDevolucion(monto.add(interes));
				}
				if (i + 1 == solicitudDevolucionAporte.size()) {
					solicitudesDevoluciones.add(devolucion);
				} else {
					Object[] solicitudDevolucionTemp = solicitudDevolucionAporte.get(i + 1);
					if (idSolicitudTemporal != Long.parseLong(solicitudDevolucionTemp[1].toString())) {
						solicitudesDevoluciones.add(devolucion);
					}
				}
			}
			logger.debug("Finaliza método " + firmaMetodo);
			return solicitudesDevoluciones;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDatosDevolucion(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public AnalisisDevolucionDTO consultarDatosDevolucion(Long idAporte, Long idSolicitudDevolucion) {
		try {
			Object[] devolucion = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_DEVOLUCION)
					.setParameter(ID_APORTE, idAporte).setParameter("idSolicitudDevolucion", idSolicitudDevolucion)
					.getSingleResult();

			AnalisisDevolucionDTO analisis = new AnalisisDevolucionDTO();
			analisis.setMonto(devolucion[0] != null ? (BigDecimal) devolucion[0] : BigDecimal.ZERO);
			analisis.setInteres(devolucion[1] != null ? (BigDecimal) devolucion[1] : BigDecimal.ZERO);
			return analisis;
		} catch (Exception e) {
			logger.error("Ocurrió un error consultarDatosDevolucion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarDatosSolicitudCorreccion(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CorreccionVistasDTO> consultarDatosSolicitudCorreccion(List<Long> idsAporteGeneral) {
		try {
			List<Object[]> solicitudes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_CORRECCION)
					.setParameter(ID_APORTE, idsAporteGeneral).getResultList();

			List<CorreccionVistasDTO> correcciones = new ArrayList<>();
			if (!solicitudes.isEmpty()) {
				for (Object[] solicitud : solicitudes) {
					CorreccionVistasDTO correccion = new CorreccionVistasDTO();
					correccion.setIdSolicitudCorreccionAporte(Long.parseLong(solicitud[0].toString()));
					correccion.setIdSolicitud(Long.parseLong(solicitud[1].toString()));
					correccion.setIdAporteGeneral(Long.parseLong(solicitud[2].toString()));
					BigDecimal monto = solicitud[3] != null ? (BigDecimal) solicitud[3] : BigDecimal.ZERO;
					BigDecimal interes = solicitud[4] != null ? (BigDecimal) solicitud[4] : BigDecimal.ZERO;
					correccion.setMontoCorreccion(monto);
					correccion.setInteresesCorreccion(interes);
					correccion.setTotalCorreccion(monto.add(interes));
					correcciones.add(correccion);
					logger.info("Solicitud Correccion: " + correccion.toString());
				}
			}
			return correcciones;
		} catch (Exception e) {
			logger.error("Ocurrió un error consultarDatosDevolucion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesGenerales(java.util.List,
	 *      java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAportesGenerales(List<Long> ids, List<Long> idsPersona) {
		String firmaMetodo = "ConsultasModeloCore.consultarAportesGenerales(List<Long>, List<Long>)";
		List<AporteGeneralModeloDTO> result = null;
		// se limpia el listado de valores nulos
		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i) == null) {
				ids.remove(i);
				i--;
			}
		}

		if (!ids.isEmpty()) {
			List<TipoAjusteMovimientoAporteEnum> movimientos = new ArrayList<>();
			movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA);

			Integer tienePersonas = 1;
			if (idsPersona == null || idsPersona.isEmpty()) {
				idsPersona = new ArrayList<>();
				idsPersona.add(0L);
				tienePersonas = 0;
			}
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_POR_REGISTRO_MASIVO,
							AporteGeneralModeloDTO.class)
					.setParameter(ID_APORTE_GENERAL, ids).setParameter(MOVIMIENTOS, movimientos)
					.setParameter(ID_PERSONAS, idsPersona).setParameter(TIENE_PERSONAS, tienePersonas)
					.getResultList();
		} else {
			result = Collections.emptyList();
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesDetallados(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetalladoModeloDTO> consultarAportesDetallados(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarAportesDetallados(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AporteDetalladoModeloDTO> result = null;

		try {
			List<AporteDetallado> resultQuery = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_REGISTRO_DETALLADO_MASIVO,
							AporteDetallado.class)
					.setParameter(ID_REGISTRO_DETALLADO, ids).getResultList();

			result = new ArrayList<>();
			for (AporteDetallado aporteGeneral : resultQuery) {
				AporteDetalladoModeloDTO dto = new AporteDetalladoModeloDTO();
				dto.convertToDTO(aporteGeneral);
				result.add(dto);
			}
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarMunicipiosPorCodigos(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Municipio> consultarMunicipiosPorCodigos(List<String> codigos) {
		String firmaMetodo = "ConsultasModeloCore.consultarMunicipiosPorCodigos(List<String>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Municipio> result = null;

		try {
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIO_CODIGO_MASIVO, Municipio.class)
					.setParameter(CODIGO_MUNICIPIO, codigos).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonasPorListado(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Deprecated
	public List<PersonaModeloDTO> consultarPersonasPorListado(List<String> nums) {
		String firmaMetodo = "ConsultasModeloCore.consultarPersonasPorListado(TipoIdentificacionEnum, List<String>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<PersonaModeloDTO> result = null;
		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_TIPO_ID_LISTA_NUM_ID,
					PersonaModeloDTO.class).setParameter(NUMERO_IDENTIFICACION, nums).getResultList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpresasPorIdsPersonas(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EmpresaModeloDTO> consultarEmpresasPorIdsPersonas(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarEmpresasPorIdsPersonas(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EmpresaModeloDTO> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_ID_PERSONA_MASIVO,
					EmpresaModeloDTO.class).setParameter(ID_PERSONAS, ids).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpleadoresPorIdsEmpresas(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EmpleadorModeloDTO> consultarEmpleadoresPorIdsEmpresas(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarEmpleadoresPorIdsEmpresas(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EmpleadorModeloDTO> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_ID_EMPRESA_MASIVO,
					EmpleadorModeloDTO.class).setParameter(ID_EMPRESAS, ids).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpresasPorIds(java.util.List)
	 */
	@Override
	public List<EmpresaModeloDTO> consultarEmpresasPorIds(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarEmpresasPorIds(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EmpresaModeloDTO> result = null;

		try {
			List<Empresa> resultQuery = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_ID_MASIVO, Empresa.class)
					.setParameter(ID_EMPRESAS, ids).getResultList();

			result = new ArrayList<>();
			for (Empresa empresa : resultQuery) {
				result.add(new EmpresaModeloDTO(empresa));
			}

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAfiliadosPorPersona(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AfiliadoModeloDTO> consultarAfiliadosPorPersona(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarAfiliadosPorPersona(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AfiliadoModeloDTO> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_ID_PERSONA_MASIVO,
					AfiliadoModeloDTO.class).setParameter(ID_PERSONAS, ids).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRolesAfiliadosPorPersona(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosPorPersona(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarAfiliadosPorPersona(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<RolAfiliadoModeloDTO> result = null;

		try {
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLES_AFILIADOS_ID_AFILIADO_MASIVO,
							RolAfiliadoModeloDTO.class)
					.setParameter(ID_AFILIADOS, ids).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarOperadoresInformacionPorCodigos(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<OperadorInformacion> consultarOperadoresInformacionPorCodigos(List<Integer> codigos) {
		String firmaMetodo = "ConsultasModeloCore.consultarOperadoresInformacionPorCodigos(List<Integer>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<OperadorInformacion> result = null;

		try {
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADOR_INFORMACION_POR_CODIGO_MASIVO,
							OperadorInformacion.class)
					.setParameter(CODIGO_OI, codigos).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSucursalesPorLlaves(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, List<SucursalEmpresaModeloDTO>> consultarSucursalesPorLlaves(List<String> sucursales) {
		String firmaMetodo = "ConsultasModeloCore.consultarSucursalesPorLlaves(List<String>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, List<SucursalEmpresaModeloDTO>> result = new HashMap<>();

		List<SucursalEmpresaModeloDTO> resultQuery = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSAL_EMPRESA_LLAVE_MASIVO,
						SucursalEmpresaModeloDTO.class)
				.setParameter(LLAVES_SUCURSALES, sucursales).getResultList();

		List<SucursalEmpresaModeloDTO> sucursalesEmpresa = null;
		for (SucursalEmpresaModeloDTO sucursal : resultQuery) {
			sucursalesEmpresa = result.get(sucursal.getIdEmpresa());
			if (sucursalesEmpresa == null) {
				sucursalesEmpresa = new ArrayList<>();
				result.put(sucursal.getIdEmpresa(), sucursalesEmpresa);
			}

			sucursalesEmpresa.add(sucursal);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonasInconsistentes(java.util.List)
	 */
	@Override
	public List<Long> consultarPersonasInconsistentes(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarPersonasInconsistentes(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Long> result = null;

		try {
			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_INCONSISTENTES_LISTA, Long.class)
					.setParameter(ID_PERSONAS, ids).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarNovedadesPorListaIdRegDet(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> consultarNovedadesPorListaIdRegDet(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarNovedadesPorListaIdRegDet(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);



		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}

	/*	try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_LISTA)
					.setParameter(ID_REGISTRO_DETALLADO, ids).getResultList();

		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;*/



		List<Object[]> result = new ArrayList<>();
		try {
			int registrosPag = 2000;
			int contRegistros = 0;
			int cantIdRegistroDetallado  = ids.size();
			
			ArrayList<Long> paginaIdBen = new ArrayList<>();
			
			do{
				for (int i = 0; i < registrosPag && contRegistros<cantIdRegistroDetallado; i++) {
					paginaIdBen.add(ids.get(contRegistros));
					//System.out.println("regitrosDetallados " + ids.get(contRegistros));
					contRegistros++;
				}
				
				List<Object[]> resultConsulta = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_LISTA)
				.setParameter(ID_REGISTRO_DETALLADO, paginaIdBen).getResultList();

				if (resultConsulta != null ) {
					result.addAll(resultConsulta);
				}
				paginaIdBen.clear();
			}while(contRegistros<cantIdRegistroDetallado);
		
			logger.debug("Finaliza operacin retirarBeneficiarioMayorEdadConTI(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarNovedadesPorListaIdRegDet (List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
  
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteDetalladoOriginal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaAporteDTO consultarAporteDetalladoOriginal(Long idAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCore.consultarAporteOriginal(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		CuentaAporteDTO result = null;

		try {
			Object[] resultQuery = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_ORIGINAL)
					.setParameter(ID_APORTE_DETALLADO, idAporteDetallado).getSingleResult();

			if (resultQuery != null) {
				result = new CuentaAporteDTO();

				result.setIdAporteDetallado(((BigInteger) resultQuery[0]).longValue());
				result.setFechaRegistro(((Date) resultQuery[1]).getTime());
				result.setTipoMovimientoRecaudo(TipoMovimientoRecaudoAporteEnum.valueOf(resultQuery[2].toString()));
				result.setConDetalle(Boolean.TRUE);
				result.setPeriodoPago(((Date) resultQuery[3]).getTime());
				result.setTieneModificaciones((Boolean) resultQuery[4]);
				result.setAporteDeRegistro((BigDecimal) resultQuery[5]);
				result.setInteresesAporte((BigDecimal) resultQuery[6]);
				result.setTotalAporte((BigDecimal) resultQuery[7]);
				result.setIdRegistroDetallado(((BigInteger) resultQuery[8]).longValue());
			}

		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistroDetalladoAnterior(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long consultarRegistroDetalladoAnterior(Long idAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistroDetalladoAnterior(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Long result = null;

		try {
			Object resultQuery = (Object) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_APORTE_DETALLADO)
					.setParameter("idAporteDetallado", idAporteDetallado).getSingleResult();
			if (resultQuery != null) {

				result = Long.valueOf(resultQuery.toString());

			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteObligatorioInteres(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BigDecimal> consultarAporteObligatorioInteres(Long idAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCore.consultarAporteObligatorioInteres(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<BigDecimal> result = new ArrayList<>();
		Object[] resultQuery;
		try {
			resultQuery = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_OBLIGATORIO_INTERES_POR_APORTE_DETALLADO)
					.setParameter("idAporteDetallado", idAporteDetallado).getSingleResult();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		if (resultQuery != null) {
			result.add((BigDecimal) resultQuery[0]);
			result.add((BigDecimal) resultQuery[1]);

		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosResumenCierre(com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO criterios) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Object[]> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_CIERRE_RESUMEN)
					.setParameter("estadosRegistro", criterios.getEstadoRegistro())
					.setParameter("tiposMovimiento", criterios.getTipoMovimiento())
					.setParameter("fechaInicio", criterios.getFechaInicio())
					.setParameter("fechaFin", criterios.getFechaFin())
					.setParameter("formasReconocimiento", criterios.getReconocimiento())
					.setParameter("evaluarReconocimiento", criterios.getEvaluarReconocimiento() ? 1 : 0)
					.setParameter("esCorreccion", criterios.getEsCorreccion() ? 1 : 0)
					.setParameter("signo", criterios.getSigno())
					.setParameter("evaluarEstado", criterios.getEvaluarEstado() ? 1 : 0)
					.setParameter("filtrarReconocimiento", criterios.getFiltrarReconocimiento() ? 1 : 0)
					.getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosResumenCierre(com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosResumenCierreRegistrados(DatosConsultaAportesCierreDTO criterios) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Object[]> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_CIERRE_RESUMEN_REGISTRADOS)
					.setParameter("estadosRegistro", criterios.getEstadoRegistro())
					.setParameter("tiposMovimiento", criterios.getTipoMovimiento())
					.setParameter("fechaInicio", criterios.getFechaInicio())
					.setParameter("fechaFin", criterios.getFechaFin())
					.setParameter("formasReconocimiento", criterios.getReconocimiento())
					.setParameter("evaluarReconocimiento", criterios.getEvaluarReconocimiento() ? 1 : 0)
					.setParameter("esCorreccion", criterios.getEsCorreccion() ? 1 : 0)
					.setParameter("signo", criterios.getSigno())
					.setParameter("evaluarEstado", criterios.getEvaluarEstado() ? 1 : 0)
					.setParameter("filtrarReconocimiento", criterios.getFiltrarReconocimiento() ? 1 : 0)
					.getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosResumenCierreN(com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosResumenCierreN(DatosConsultaAportesCierreDTO criterios) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Object[]> result = null;

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_CIERRE_RESUMEN_N)
					.setParameter("estadosRegistro", criterios.getEstadoRegistro())
					//.setParameter("tiposMovimiento", criterios.getTipoMovimiento())
					.setParameter("fechaInicio", criterios.getFechaInicio())
					.setParameter("fechaFin", criterios.getFechaFin())
					//.setParameter("formasReconocimiento", criterios.getReconocimiento())
					//.setParameter("evaluarReconocimiento", criterios.getEvaluarReconocimiento() ? 1 : 0)
					//.setParameter("esCorreccion", criterios.getEsCorreccion() ? 1 : 0)
					//.setParameter("signo", criterios.getSigno())
					//.setParameter("evaluarEstado", criterios.getEvaluarEstado() ? 1 : 0)
					//.setParameter("filtrarReconocimiento", criterios.getFiltrarReconocimiento() ? 1 : 0)
					.getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	public boolean esPrimerDiaDelMes(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		logger.info("fecha primero dia ?? " +(calendar.get(Calendar.DAY_OF_MONTH) == 1));
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}
	

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosResumenCierre(com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosResumenCierreAportes(DatosConsultaAportesCierreDTO criterios) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<Object[]> result = null;
		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_CIERRE_RESUMEN_APORTES)
					.setParameter("estadosRegistro", criterios.getEstadoRegistro())
					.setParameter("tiposMovimiento", criterios.getTipoMovimiento())
					.setParameter("fechaInicio", criterios.getFechaInicio())
					.setParameter("fechaFin", criterios.getFechaFin())
					.setParameter("formasReconocimiento", criterios.getReconocimiento())
					.setParameter("evaluarReconocimiento", criterios.getEvaluarReconocimiento() ? 1 : 0)
					.setParameter("esCorreccion", criterios.getEsCorreccion() ? 1 : 0)
					.setParameter("signo", criterios.getSigno())
					.setParameter("evaluarEstado", criterios.getEvaluarEstado() ? 1 : 0)
					.setParameter("filtrarReconocimiento", criterios.getFiltrarReconocimiento() ? 1 : 0)
					.getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudesDevolucionListaIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, SolicitudAporteModeloDTO> consultarSolicitudesDevolucionListaIds(List<Long> idsRegistroGeneral) {
		String firmaMetodo = "ConsultasModeloCore.consultarSolicitudesDevolucionListaIds(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, SolicitudAporteModeloDTO> result = null;

		try {
			int registrosPag = 1500;
			int contRegistros = 0;
			int cantRegistros  = idsRegistroGeneral.size();

			ArrayList<Long> paginaIdReg = new ArrayList<>();
			List<SolicitudAporteModeloDTO> resultQuery =  new ArrayList<>();

			do{
				for (int i = 0; i < registrosPag && contRegistros<cantRegistros; i++) {
					paginaIdReg.add(idsRegistroGeneral.get(contRegistros));
					contRegistros++;
				}

				List<SolicitudAporteModeloDTO> resultQ = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_LISTADO_ID_REGGEN,
							SolicitudAporteModeloDTO.class)
					.setParameter("idsRegistroGeneral", paginaIdReg).getResultList();
				if(!resultQ.isEmpty() && resultQ != null){
					for(SolicitudAporteModeloDTO sol :resultQ){
						resultQuery.add(sol);
						
					}
				}
				paginaIdReg.clear();
			}
			while(contRegistros<cantRegistros);

			result = new HashMap<>();
			for (SolicitudAporteModeloDTO solicituAporte : resultQuery) {
				result.put(solicituAporte.getIdRegistroGeneral(), solicituAporte);
			}
		} catch (NoResultException e) {
			result = Collections.emptyMap();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPagoSubsidioCotizantes(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosConsultaSubsidioPagadoDTO> consultarPagoSubsidioCotizantes(
			List<DatosConsultaSubsidioPagadoDTO> datosCotizantes) {
		String firmaMetodo = "ConsultasModeloCore.consultarPagoSubsidioCotizantes(List<DatosConsultaSubsidioPagadoDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			String jsonPayload;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			jsonPayload = mapper.writeValueAsString(datosCotizantes);

			List<Object[]> resultQuery = (List<Object[]>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTA_PAGO_SUBSIDIO_COTIZANTES)
					.setParameter("parametros", jsonPayload).getResultList();

			for (Object[] registro : resultQuery) {
				TipoIdentificacionEnum tipoId = TipoIdentificacionEnum.valueOf((String) registro[0]);
				String numId = (String) registro[1];
				String periodo = (String) registro[2];

				for (DatosConsultaSubsidioPagadoDTO cotizante : datosCotizantes) {
					if (cotizante.getTipoIdentificacion().equals(tipoId)
							&& cotizante.getNumeroIdentificacion().equalsIgnoreCase(numId)
							&& cotizante.getPeriodo().equals(periodo)) {
						cotizante.setTieneSubsidio(Boolean.TRUE);
					}
				}
			}

		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return datosCotizantes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarPersonasPorListadoIds(java.util.List,
	 *      java.util.Boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, PersonaModeloDTO> consultarPersonasPorListadoIds(List<Long> ids, Boolean desdeEmpresa) {
		String firmaMetodo = "ConsultasModeloCore.consultarPersonasPorListadoIds(List<Long>, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, PersonaModeloDTO> result = new HashMap<>();

		try {
			// se consultan los registros en paquetes de 1000 registros
			Integer inicio = 0;
			Integer fin = ids.size() > 1000 ? 1000 : ids.size();
			while (fin <= ids.size()) {
				List<Long> idsTemp = ids.subList(inicio, fin);

				if (!desdeEmpresa) {
					List<PersonaModeloDTO> resultQuery = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_ID_LISTA_ID,
									PersonaModeloDTO.class)
							.setParameter(ID_PERSONAS, idsTemp).getResultList();

					for (PersonaModeloDTO persona : resultQuery) {
						result.put(persona.getIdPersona(), persona);
					}
				} else {
					List<Object[]> resultQuery = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_ID_LISTA_ID_EMPRESA)
							.setParameter(ID_EMPRESAS, idsTemp).getResultList();

					for (Object[] objetos : resultQuery) {
						Persona per = (Persona) objetos[0];
						Empresa emp = (Empresa) objetos[1];
						Ubicacion ubi = (Ubicacion) objetos[2];
						per.setUbicacionPrincipal(ubi);

						result.put(emp.getIdEmpresa(), new PersonaModeloDTO(per));
					}
				}

				inicio = fin;
				fin++;
				if (fin <= ids.size()) {
					fin = fin + 1000 <= ids.size() ? inicio + 1000 : ids.size();
				}
			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Map<Long, SolicitudAporteModeloDTO> consultarSolicitudAportePorListaIdAporte(List<Long> idAporteGeneral) {
		String firmaMetodo = "ConsultasModeloCore.consultarSolicitudAportePorListaIdAporte(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, SolicitudAporteModeloDTO> result = new HashMap<>();
		try {
			List<Object[]> solicitudesAporte = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_APORTE_LISTADO_ID)
					.setParameter(ID_REGISTRO_GENERAL, idAporteGeneral).getResultList();

			for (Object[] solicitudAporte : solicitudesAporte) {
				SolicitudAporte soa = (SolicitudAporte) solicitudAporte[0];
				AporteGeneral apg = (AporteGeneral) solicitudAporte[1];
				Solicitud sol = (Solicitud) solicitudAporte[2];
				soa.setSolicitudGlobal(sol);

				result.put(apg.getId(), new SolicitudAporteModeloDTO(soa));
			}

		} catch (NoResultException nre) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarSolicitudDevolucionVista360ListaIds(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> consultarSolicitudDevolucionVista360ListaIds(
			List<Long> idsAporteGeneral) {
		String firmaMetodo = "ConsultasModeloCore.consultarDevolucionesVista360(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> result = new HashMap<>();

		try {
			List<Object[]> resultQuery = (List<Object[]>) entityManagerCore
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SOLICITUD_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE)
					.setParameter("idsAporteGeneral", idsAporteGeneral)
					.setParameter("tipoAjuste", TipoAjusteMovimientoAporteEnum.DEVOLUCION.name()).getResultList();

			for (Object[] dato : resultQuery) {
				SolicitudDevolucionAporte sda = (SolicitudDevolucionAporte) dato[0];
				MovimientoAporte moa = (MovimientoAporte) dato[1];
				Solicitud sol = (Solicitud) dato[2];

				sda.setSolicitudGlobal(sol);

				Map<Long, SolicitudDevolucionAporteModeloDTO> mapaAporte = result.get(moa.getIdAporteGeneral());
				if (mapaAporte == null) {
					mapaAporte = new HashMap<>();
					result.put(moa.getIdAporteGeneral(), mapaAporte);
				}

				if (!mapaAporte.containsKey(moa.getIdMovimientoAporte())) {
					mapaAporte.put(moa.getIdMovimientoAporte(), new SolicitudDevolucionAporteModeloDTO(sda));
				}
			}

		} catch (NoResultException nre) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarHistoricoAportesPorTipoAfiliacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<HistoricoAportes360DTO> consultarHistoricoAportesPorTipoAfiliacion(
			TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado,
			Long idEmpresa) {
		String firmaServicio = "ConsultasModeloCore.consultarHistoricoAportesDependiente(TipoIdentificacionEnum, String, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<HistoricoAportes360DTO> historicoAportes = null;

		List<String> movimientos = new ArrayList<>();
		movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION.name());
		movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA.name());
		movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA.name());

		TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;

		switch (tipoAfiliado) {
			case PENSIONADO:
				tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
				break;
			case TRABAJADOR_DEPENDIENTE:
				tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.EMPLEADOR;
				break;
			case TRABAJADOR_INDEPENDIENTE:
				tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
				break;
		}

		historicoAportes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_APORTES_TIPO_AFILIACION,
						HistoricoAportes360DTO.class)
				.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
				.setParameter("numeroIdAfiliado", numeroIdAfiliado).setParameter("idEmpresa", idEmpresa)
				.setParameter(MOVIMIENTOS, movimientos).setParameter("tipoSolicitante", tipoSolicitante.name())
				.getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return historicoAportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesGeneralesPorIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIds(List<Long> idsAportes) {
		String firmaServicio = "ConsultasModeloCore.consultarAportesGeneralesPorIds(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AporteGeneralModeloDTO> result = null;
		try {
			for (int i = 0; i < idsAportes.size(); i++) {
				if (idsAportes.get(i) == null) {
					idsAportes.remove(i);
					i--;
				}
			}

			if (idsAportes != null && !idsAportes.isEmpty()) {
				result = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_POR_ID_MASIVO,
								AporteGeneralModeloDTO.class)
						.setParameter(IDS_APORTE_GENERAL, idsAportes).getResultList();
			} else {
				result = Collections.emptyList();
			}
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesDetalladosPorIdsGeneral(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDetalladoModeloDTO> consultarAportesDetalladosPorIdsGeneral(List<Long> idsAportes) {
		String firmaServicio = "ConsultasModeloCore.consultarAportesDetalladosPorIdsGeneral(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<AporteDetalladoModeloDTO> result = null;
		try {
			for (int i = 0; i < idsAportes.size(); i++) {
				if (idsAportes.get(i) == null) {
					idsAportes.remove(i);
					i--;
				}
			}

			if (idsAportes != null && !idsAportes.isEmpty()) {
				List<AporteDetallado> resultQuery = entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_ID_APORTE_GENERAL_MASIVO,
								AporteDetallado.class)
						.setParameter(IDS_APORTE_GENERAL, idsAportes).getResultList();

				result = new ArrayList<>();
				for (AporteDetallado aporteDetallado : resultQuery) {
					AporteDetalladoModeloDTO aporteDet = new AporteDetalladoModeloDTO(aporteDetallado);
					result.add(aporteDet);
				}
			} else {
				result = Collections.emptyList();
			}
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#actualizarPaqueteAportes(java.util.List,
	 *      java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarPaqueteAportes(List<AporteGeneralModeloDTO> aportesGenerales,
			List<AporteDetalladoModeloDTO> aportesDetallados) {
		String firmaMetodo = "ConsultasModeloCore.actualizarPaqueteAportes(List<AporteGeneralModeloDTO>, List<AporteDetalladoModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			if (aportesGenerales != null) {
				for (AporteGeneralModeloDTO aporteGeneral : aportesGenerales) {
					if (aporteGeneral.getId() != null) {
						entityManagerCore.merge(aporteGeneral.convertToEntity());
					}
				}
			}

			if (aportesDetallados != null) {
				List<AporteDetallado> aportesDetalladosEntity = new ArrayList<>();
				for (AporteDetalladoModeloDTO aporteDetallado : aportesDetallados) {
					aportesDetalladosEntity.add(aporteDetallado.convertToEntity());
				}

				int cuenta = 0;
				for (AporteDetallado aporteDetallado : aportesDetalladosEntity) {
					cuenta++;
					if (aporteDetallado.getId() != null) {
						logger.warn(firmaMetodo + "Actualizando aporte detallado " + cuenta + " de "
								+ aportesDetalladosEntity.size());
						entityManagerCore.merge(aporteDetallado);
					}
				}
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesGeneralesPorMetodo(com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum,
	 *      java.util.List)
	 */
	@Override
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorMetodo(ModalidadRecaudoAporteEnum metodoRecaudo,
			List<Long> idsPersona) {
		String firmaServicio = "ConsultasModeloCore.consultarAportesGeneralesPorMetodo(ModalidadRecaudoAporteEnum, , List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AporteGeneralModeloDTO> result = null;
		try {
			List<TipoAjusteMovimientoAporteEnum> movimientos = new ArrayList<>();
			movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA);

			Integer tienePersonas = 1;
			if (idsPersona == null || idsPersona.isEmpty()) {
				idsPersona = new ArrayList<>();
				idsPersona.add(0L);
				tienePersonas = 0;
			}

			result = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_POR_MODALIDAD_RECAUDO,
							AporteGeneralModeloDTO.class)
					.setParameter(MODALIDAD_RECAUDO, metodoRecaudo).setParameter(MOVIMIENTOS, movimientos)
					.setParameter(ID_PERSONAS, idsPersona).setParameter(TIENE_PERSONAS, tienePersonas).getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportesGeneralesPorIdRegGen(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIdRegGen(Long idRegistroGeneral) {
		String firmaServicio = "ConsultasModeloCore.consultarAportesGeneralesPorMetodo(ModalidadRecaudoAporteEnum, , List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AporteGeneralModeloDTO> result = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_GENERALES_POR_REGISTRO,
						AporteGeneralModeloDTO.class)
				.setParameter(ID_REGISTRO_GENERAL, idRegistroGeneral).getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#registrarAportesGenerales(java.util.Map)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Deprecated
	public Map<String, Long> registrarAportesGenerales(Map<String, AporteGeneralModeloDTO> aportesGenerales) {
		String firmaMetodo = "ConsultasModeloCore.registrarAportesGenerales(Map<String, AporteGeneralModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<String, Long> result = new HashMap<>();

		AporteGeneral aporteGeneral = null;
		for (String llave : aportesGenerales.keySet()) {

			aporteGeneral = aportesGenerales.get(llave).convertToEntity();

			entityManagerCore.persist(aporteGeneral);
			result.put(llave, aporteGeneral.getId());
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#registrarAportesDetallados(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Long> registrarAportesDetallados(List<JuegoAporteMovimientoDTO> aportesDetallados) {
		String firmaMetodo = "ConsultasModeloCore.registrarAportesDetallados(List<AporteDetalladoModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		logger.debug(ConstantesComunes.INICIO_LOGGER + "aportesDetallados: " + aportesDetallados.size());

		List<Long> result = new ArrayList<>();

		AporteDetallado aporteDetallado = null;
		MovimientoAporte movimientoAporte = null;
		for (JuegoAporteMovimientoDTO juegoAporteMovimiento : aportesDetallados) {
			aporteDetallado = juegoAporteMovimiento.getAporteDetallado().convertToEntity();
			aporteDetallado.setFechaCreacion(new Date());
			aporteDetallado.setMarcaCalculoCategoria(true);
			movimientoAporte = juegoAporteMovimiento.getMovimientoAporte().convertToEntity();
			entityManagerCore.persist(aporteDetallado);

			movimientoAporte.setIdAporteGeneral(aporteDetallado.getIdAporteGeneral());
			movimientoAporte.setIdAporteDetallado(aporteDetallado.getId());
			movimientoAporte.setFechaCreacion(new Date());
			entityManagerCore.persist(movimientoAporte);

			result.add(aporteDetallado.getIdRegistroDetallado());
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	@Override
	public void consultarPersonasAfiliadasConAporteFuturo(String fecha, String periodo) {

		List<Object> idsAfiliados = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_AFILIADAS_CON_APORTE_FUTURO_EN_PERIODO)
				.setParameter(FECHA_APORTE, fecha)
				.setParameter(PERIODO_APORTE, periodo)
				.getResultList();

		if (idsAfiliados != null && !idsAfiliados.isEmpty()) {
			for (Object obj : idsAfiliados) {
				StoredProcedureQuery procedimiento = entityManagerCore
						.createNamedStoredProcedureQuery(
								NamedQueriesConstants.PROCEDURE_USP_REP_CAMBIO_CATEGORIA_AFILIADO)
						.setParameter("iAfiId", BigInteger.valueOf(Long.parseLong(obj.toString())))
						.setParameter("bNuevoAporte", Byte.parseByte("1"));

				procedimiento.execute();
			}
		}
	}

	@Override
	public List<Object> ejecutarCalculoCategoriaAportesFuturos() {
		String firmaMetodo = "ConsultasModeloCore.ejecutarCalculoCategoriaAportesFuturos()";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		// se consultaran los futuros
		logger.info("**__**Inicia CONSULTAR_ID_REGISTROS_DETALLADOS_FUTUROS tarea Automatica");
		List<Object> listaIdRegistrosDetallados = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_REGISTROS_DETALLADOS_FUTUROS).getResultList();
		
		logger.info("**__**Finaliza CONSULTAR_ID_REGISTROS_DETALLADOS_FUTUROS tarea Automatica");
		return listaIdRegistrosDetallados;
		 
	}
	@Override
	public void ejecutarCalculoCategoriaAportesFuturosSP(BigInteger registroDetallado) {
		StoredProcedureQuery procedimiento = entityManagerCore
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_REP_CALCULO_CATEGORIAS_APORTES)
					.setParameter("apdRegistroDetallado", registroDetallado);
		procedimiento.execute();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAportanteCorreccion(java.lang.Long)
	 */
	@Override
	public PersonaModeloDTO consultarAportanteCorreccion(Long idAporteGeneralCorregido,
			Long idAporteDetalladoCorregido) {
		try {
			return entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_NUEVO_CORRECCION,
							PersonaModeloDTO.class)
					.setParameter("idAporteGeneralAnt", idAporteGeneralCorregido)
					.setParameter("idAporteDetalladoAnt", idAporteDetalladoCorregido).setMaxResults(1)
					.getSingleResult();
			// modificado el 16/02/2023 setMaxResults
		} catch (NoResultException e) {
			return new PersonaModeloDTO();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#ejecutarCalculoFechaHabil(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Short)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarCalculoCategoriasMasiva() {
		String firmaMetodo = "ConsultasModeloCore.ejecutarCalculoCategoriasMasiva()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		StoredProcedureQuery query = entityManagerCore
				.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_CalculoMasivoCategorias);

		query.execute();
	}

	/**
	 * Método encargado de convertir una lista de tipo object[] a
	 * una lista de tipo AportePilaDTO
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @param planillasObject lista tipo object[]
	 * @return lista tipo AportePilaDTO
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<AportePilaDTO> convertToListAportePilaDTO(List<Object[]> planillasObject) {
		List<AportePilaDTO> aportesPilaDTO = new ArrayList<>();
		AportePilaDTO aptpDTO = new AportePilaDTO();
		for (Object[] planillaObject : planillasObject) {
			aptpDTO.setAporteGeneral((AporteGeneral) planillaObject[0]);
			aptpDTO.setAporteDetallado((AporteDetallado) planillaObject[1]);
			aptpDTO.setTipoIdCotizante(TipoIdentificacionEnum.valueOf((String) planillaObject[2]));
			aptpDTO.setNumeroIdCotizante((String) planillaObject[3]);
			aptpDTO.setEmailCotizante((String) planillaObject[4]);
			aptpDTO.setEmailAportante((String) planillaObject[5]);
			aportesPilaDTO.add(aptpDTO);
		}
		return aportesPilaDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#copiarDatosAportes(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void copiarDatosAportes(Long idRegistroGeneral) {
		String firmaServicio = "copiarDatosAportes(Long:" + idRegistroGeneral + ")";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.info("PROCEDE A EJECUTAR EL SP USP_COPIAR_DATOS_TEMPORALES_APORTES" + idRegistroGeneral);
		StoredProcedureQuery sQuery = entityManagerCore
				.createNamedStoredProcedureQuery(NamedQueriesConstants.USP_COPIAR_DATOS_TEMPORALES_APORTES)
				.setParameter("idRegistroGeneral", idRegistroGeneral);
		sQuery.execute();
		logger.info("FIN EJECUTAR EL SP USP_COPIAR_DATOS_TEMPORALES_APORTES" + idRegistroGeneral);
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#copiarDatosAportes(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean validarNovedadesPila(Long idRegistroGeneral) {
		String firmaServicio = "validarNovedadesPila(Long:" + idRegistroGeneral + ")";
		Boolean result = false;
		StoredProcedureQuery sQuery = entityManagerCore
				.createNamedStoredProcedureQuery(NamedQueriesConstants.ASP_EXECUTEVALIDATENOVPILA)
				.setParameter("regId", idRegistroGeneral);
		sQuery.execute();	
		Object respuesta = sQuery.getOutputParameterValue("validar");
		if(respuesta.toString().equals("true") || respuesta.toString().equals("1")){
			result = true;
		}
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	@Override
	public List<TasasInteresMoraModeloDTO> consultarTasasInteresMora() {
		String firmaMetodo = "ConsultasModeloCore.consultarTasasInteresMora()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TasasInteresMoraModeloDTO> tasasInteresMora = new ArrayList<>();

		tasasInteresMora = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TASA_INTERES_MORA_APORTES,
						TasasInteresMoraModeloDTO.class)
				.getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return tasasInteresMora;
	}

	@Override
	public ResultadoModificarTasaInteresDTO actualizarTasaInteresMora(ModificarTasaInteresMoraDTO tasaModificada) {
		String firmaMetodo = "ConsultasModeloCore.actualizarTasaInteresMora(Long fechaInicioTasa, BigDecimal porcentajeNuevo, String normativaNueva)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		ResultadoModificarTasaInteresDTO respuesta = new ResultadoModificarTasaInteresDTO();

		TasasInteresMoraModeloDTO tasa = new TasasInteresMoraModeloDTO();
		TasasInteresMora tasaActualizada;
		// Date periodo = new Date(tasaModificada.getPeriodo() * 1000);

		try {
			tasa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_TASA_INTERES_MORA_APORTES_POR_PERIODO,
							TasasInteresMoraModeloDTO.class)
					.setParameter("idTasa", tasaModificada.getIdTasa())
					.getSingleResult();

		} catch (NoResultException nre) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			respuesta.setRespuestaServicio(Boolean.FALSE);
			respuesta.setModificaTasa(Boolean.FALSE);
			return respuesta;
		}

		if (tasa.getId() == null) {

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			respuesta.setRespuestaServicio(Boolean.FALSE);
			respuesta.setModificaTasa(Boolean.FALSE);
			return respuesta;

		} else {
			// BigDecimal difTasas= tasa.getPorcentajeTasa() -
			// tasaModificada.getTasaInteres();

			if (tasa.getPorcentajeTasa().compareTo(tasaModificada.getTasaInteres()) == 0) {
				respuesta.setModificaTasa(Boolean.FALSE);
			} else {
				respuesta.setModificaTasa(Boolean.TRUE);
			}

			tasa.setPorcentajeTasa(tasaModificada.getTasaInteres());
			tasa.setNormativa(tasaModificada.getNormativa());
			tasaActualizada = tasa.convertToEntity();
			entityManagerCore.merge(tasaActualizada);

			respuesta.setRespuestaServicio(Boolean.TRUE);

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return respuesta;
		}

	}

	@Override
	public Boolean crearTasaInteresMora(ModificarTasaInteresMoraDTO nuevaTasa) {
		String firmaMetodo = "ConsultasModeloCore.crearTasaInteresMora(ModificarTasaInteresMoraDTO nuevaTasa)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TasasInteresMoraModeloDTO tasa = new TasasInteresMoraModeloDTO();
		List<TasasInteresMoraModeloDTO> tasasInteresMora = new ArrayList<>();
		// Short maxNumPeriodo = 0;
		Long maxId = 0L;

		Date fechaInicio = new Date(nuevaTasa.getPeriodo() * 1000);

		Calendar calInicio = Calendar.getInstance();
		calInicio.setTime(fechaInicio);

		Calendar calendar = Calendar.getInstance();
		calendar.set(calInicio.get(Calendar.YEAR), calInicio.get(Calendar.MONTH), calInicio.get(Calendar.DATE));
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);

		Date fechaFin = calendar.getTime();

		tasasInteresMora = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TASA_INTERES_MORA_APORTES,
						TasasInteresMoraModeloDTO.class)
				.getResultList();

		Long nuevaTasaLong = nuevaTasa.getPeriodo() * 1000;
		for (TasasInteresMoraModeloDTO tasasInteresMoraModeloDTO : tasasInteresMora) {

			// Date fechaInicioTasa = new
			// Date(tasasInteresMoraModeloDTO.getFechaInicioTasa().getTime());
			Long fechaInicioTasa = tasasInteresMoraModeloDTO.getFechaInicioTasa().getTime();
			Long difFechas = nuevaTasaLong - fechaInicioTasa;
			if (difFechas == 0) {
				logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
				return false;
			} else {
				// if (tasasInteresMoraModeloDTO.getNumeroPeriodoTasa() > maxNumPeriodo) {
				// maxNumPeriodo = tasasInteresMoraModeloDTO.getNumeroPeriodoTasa();
				// }
				if (tasasInteresMoraModeloDTO.getId() > maxId) {
					maxId = tasasInteresMoraModeloDTO.getId();
				}
			}
		}

		Short numPeriodoShort;
		Integer numPeriodoInt = maxId.intValue() + 1;
		numPeriodoShort = numPeriodoInt.shortValue();

		tasa.setFechaInicioTasa(fechaInicio);
		tasa.setFechaFinTasa(fechaFin);
		tasa.setPorcentajeTasa(nuevaTasa.getTasaInteres());
		tasa.setNormativa(nuevaTasa.getNormativa());
		// tasa.setNumeroPeriodoTasa(maxNumPeriodo);
		tasa.setNumeroPeriodoTasa(numPeriodoShort);
		tasa.setTipoInteres(TipoInteresEnum.SIMPLE);

		TasasInteresMora crearTasa = tasa.convertToEntity();

		try {
			entityManagerCore.persist(crearTasa);
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en ConsultasModeloCore.crearTasaInteresMora(ModificarTasaInteresMoraDTO nuevaTasa)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return true;
	}

	@Override
	public Map<Long, String> consultarNuevosNumerosCorreccion(List<Long> listaIdApDetallados) {
		String firmaMetodo = "ConsultasModeloCore.consultarNuevosNumerosCorreccion(List<Long>)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		Map<Long, String> result = new HashMap<Long, String>();
		logger.info(firmaMetodo + " :: IDs de Aporte Detallado a consultar1111: " + listaIdApDetallados);


		try {
			List<Object[]> idsConsulta = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUEVOS_NUMERO_OPERACION_CORRECCION)
					.setParameter(IDS_APORTE_DETALLADO, listaIdApDetallados).getResultList();


			for (Object[] resultado : idsConsulta) {
				Long idApg = ((BigInteger) resultado[0]).longValue();
				Long idApd = ((BigInteger) resultado[1]).longValue();

				result.put(idApg, idApd.toString());

			}
		} catch (NoResultException e) {
			logger.debug(firmaMetodo + " :: Sin datos");
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PilaEstadoTransitorio> consultarEstadoProcesamientoPlanilla(Long idAporteGeneral) {
		List<PilaEstadoTransitorio> datosPlanilla = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_FORMALIZACION_DATOS_PLANILLA)
				.setParameter("idAporteGeneral", idAporteGeneral).getResultList();
		return datosPlanilla;
	}

	

	@Override
	public String consultarClasificacionIndependiente(Long idSolicitud) {
		Object query = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CLASIFICACION_INDEPENDIENTE)
				.setParameter(ID_SOLICITUD, idSolicitud)
				.getSingleResult();
		String clasificacion = query.toString();
		return clasificacion;
	}

	public Double consultarPorcentajeIndependiente(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		Object query = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PORCENTAJE_INDEPENDIENTE)
				.setParameter(TIPO_IDENTIFICACION, tipoIdentificacion.name())
				.setParameter(NUMERO_IDENTIFICACION, numeroIdentificacion)
				.getSingleResult();
		Double porcentaje = Double.parseDouble(query.toString());
		return porcentaje;
	}

	/**
    * (non-Javadoc)
    * 
    * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarNumeroDePlanillaN(java.lang.Long)
    */
    @Override
    public String consultarNumeroDePlanillaN(Long aporteDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.consultarNumeroDePlanillaN(Long,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		String result = new String();
		try{
		Object query = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUM_PLANILLA_POR_PLANILLA_ANT_REGISTRO)
				.setParameter("movimientoAporte", aporteDetallado)
				.getSingleResult();                                     
		result =((query).toString());
			    
		}catch(Exception e){
			result = "";
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
    }

	/**
    * (non-Javadoc)
    * 
    * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarNumeroDePlanillaN(java.lang.Long)
    */
    @Override
    public String consultarNumeroDePlanillaCorregida(Long aporteDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.consultarNumeroDePlanillaN(Long,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		String result = new String();
		try{
		Object query = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUM_PLANILLA_POR_PLANILLA_COR_MOV)
				.setParameter("movimientoAporte", aporteDetallado)
				.getSingleResult();                                     
		result =((query).toString());
			    
		}catch(Exception e){
			result = "";
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
    }

	public Map<Long, Boolean> consultaModificaciones(List<CuentaAporteDTO> cuentaAportes) {
		Map<Long, Boolean> tieneModificaciones = new HashMap<Long, Boolean>();
		List<Long> registrosDetallados = cuentaAportes.stream()
			.map(CuentaAporteDTO::getIdAporteDetallado).collect(Collectors.toList());

		List<Object[]> consulta = entityManagerCore.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MODIFICACIONES_POR_APORTE_DETALLADO
		).setParameter("idAporteDetallados", registrosDetallados).getResultList();

		if (consulta != null && !consulta.isEmpty()) {
			for (Object[] res : consulta) {
				tieneModificaciones.put(
					Long.valueOf(res[0].toString()),
					res[1].toString() != null && res[1].toString().equals("1") ? Boolean.TRUE : Boolean.FALSE
				);
			}
		}
		return tieneModificaciones;
	}

	public List<AporteGeneralModeloDTO> consultarCuentaBancariaRecaudo(List<AporteGeneralModeloDTO> aportesGenerales){
		if (aportesGenerales != null && !aportesGenerales.isEmpty()) {
			aportesGenerales.stream().forEach((iteAportes) -> {
				Query qCuentaBancaria = entityManagerCore
						.createNativeQuery("select case when p.id is not null then CONCAT(p.BANCO,'-',p.TIPO,'-',p.NUMERO_CUENTA,'-',p.TIPO_RECAUDO) "
								+ "when pn.id is not null then  CONCAT(pn.BANCO,'-',pn.TIPO,'-',pn.NUMERO_CUENTA,'-',pn.TIPO_RECAUDO)  end AS CUENTA from AporteGeneral ap  "
								+ "left join aporteDetalladoRegistroControlN a on ap.apgid = a.apdAporteGeneral "
								+ "left join CUENTAS_BANCARIAS p on p.id = ap.apgCuentaBancariaRecaudo "
								+ "left join CUENTAS_BANCARIAS pn on pn.id = a.regCuentaBancariaRecaudo where ap.apgId ="
								+ iteAportes.getId());
				String cuentaBancariaTexto = "";
				try {
					cuentaBancariaTexto = (String) qCuentaBancaria.getSingleResult();
				} catch (NoResultException nre) {
					// Code for handling NoResultException
					cuentaBancariaTexto = "No aplica.";
				}
				iteAportes.setCuentaBancariaRecaudoTexto(cuentaBancariaTexto);
			});
		}
		return aportesGenerales;

	}
		/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarCuentasAporte(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarCuentasAporteLimit(List<Long> idsAporteGeneral, UriInfo uri, HttpServletResponse response) {
		logger.debug("Inicia consultarCuentasAporte(List<Long>) ");
		List<CuentaAporteDTO> result = new  ArrayList<>();
				/*builder */
				QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
				Query query = null;
				String consulta = NamedQueriesConstants.CONSULTAR_CUENTA_APORTE_BULDER;
				queryBuilder.addParam("idsAporteGeneral", idsAporteGeneral);
				query = queryBuilder.createQuery(consulta, null);
			 result = query.getResultList();
			return result;
			//comentado por limir bulder
		/*return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CUENTA_APORTE, CuentaAporteDTO.class)
				.setParameter("idsAporteGeneral", idsAporteGeneral).getResultList();*/
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object[] consultarCuentaBancariaAporteGeneral(Long idAporteGeneral) {
		logger.info("inicia consultarCuentaBancariaAporteGeneral");
		try {
			Object result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_Y_CODIGO_CUENTA_BANCARIA)
											 .setParameter("idAporteGeneral", idAporteGeneral)
											 .getSingleResult();
			if (result instanceof Object[]) {
				return (Object[]) result;
			} else {
				logger.error("El resultado no es del tipo esperado Object[]");
				return new Object[0];
			}
		} catch (NoResultException e) {
			logger.error("No se obtuvo un resultado en consultarCuentaBancariaAporteGeneral con el parámetro " + idAporteGeneral);
			e.printStackTrace();
			return new Object[0];
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object[] consultarCuentaBancariaAportePlanillaN(Long numPlanillaN) {
		logger.info("inicia consultarCuentaBancariaAportePlanillaN "+numPlanillaN);
		try {
			Object result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_Y_CODIGO_CUENTA_BANCARIA_PLANILLA_N)
											 .setParameter("numPlanillaN", numPlanillaN)
											 .getSingleResult();
				return (Object[]) result;

			
		} catch (NoResultException e) {
			logger.error("No se obtuvo un resultado en consultarCuentaBancariaAportePlanillaN con el parámetro " + numPlanillaN);
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consutlarNumeroOperacionCorreccion(Long idAporteDetallado){
		logger.info("incia consutlarNumeroOperacionCorreccion(Long idAporteDetallado)"+idAporteDetallado);
		try{
			Object result = (Object) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_OPERACION_CORRECCION)
											.setParameter("idAporteDetallado",String.valueOf(idAporteDetallado))
											.getSingleResult();
			return  result.toString();
		}catch(NoResultException e){
			logger.error("no se encontro moaId para el aporte detallado" + idAporteDetallado);
			e.printStackTrace();
			return "new Object[0]";
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistrosResumenCierreAportesExtemporaneos(com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> consultarRegistrosResumenCierreAportesExtemporaneos(DatosConsultaAportesCierreDTO criterios) {
		String firmaMetodo = "ConsultasModeloCore.consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Object[]> result = null;
		Date fechaInicioHabil = criterios.getFechaInicio();
		if(esPrimerDiaDelMes(criterios.getFechaInicio())){
			ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
			consultarListafestivos.execute();
			List<ElementoListaDTO> festivos = consultarListafestivos.getResult();
			fechaInicioHabil = CalendarUtils.calcularFecha(
					criterios.getFechaInicio(), 2, CalendarUtils.TipoDia.HABIL, festivos);
		}

		try {
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTA_APORTES_CIERRE_RESUMEN_APORTES_EXTEMPORANEOS)
					.setParameter("estadosRegistro", criterios.getEstadoRegistro())
					.setParameter("tiposMovimiento", criterios.getTipoMovimiento())
					.setParameter("fechaInicio", fechaInicioHabil)
					.setParameter("fechaFin", criterios.getFechaFin())
					.setParameter("formasReconocimiento", criterios.getReconocimiento())
					.setParameter("evaluarReconocimiento", criterios.getEvaluarReconocimiento() ? 1 : 0)
					.setParameter("esCorreccion", criterios.getEsCorreccion() ? 1 : 0)
					.setParameter("signo", criterios.getSigno())
					.setParameter("evaluarEstado", criterios.getEvaluarEstado() ? 1 : 0)
					.setParameter("filtrarReconocimiento", criterios.getFiltrarReconocimiento() ? 1 : 0)
					.getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroAportesExtemporaneos(Long fechaInicio,
																						  Long fechaFin, Boolean legalizacion, Boolean otrosIngresos) {
		String firmaMetodo = "ConsultasModeloCore.consultarDetalleRegistroAportesLegalizacion(Long, Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ResultadoDetalleRegistroDTO> resultadosAportes = null;

		List<DetalleRegistroAportanteDTO> resultadosAportantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTES_EXTEMPORANEOS_REGISTRADOS,
						DetalleRegistroAportanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_LEGALIZACIONES, legalizacion ? 1 : 0)
				.setParameter("otrosIngresos", otrosIngresos ? 1 : 0).getResultList();

		List<DetalleRegistroCotizanteDTO> resultadosCotizantes = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTES_EXTEMPORANEOS_COTIZANTES_REGISTRADOS,
						DetalleRegistroCotizanteDTO.class)
				.setParameter(FECHA_INICIO, new Date(fechaInicio)).setParameter(FECHA_FIN, new Date(fechaFin))
				.setParameter(CONSULTAR_LEGALIZACIONES, legalizacion ? 1 : 0)
				.setParameter("otrosIngresos", otrosIngresos ? 1 : 0).getResultList();

		resultadosAportes = organizarSalidaDetalleCierre(resultadosCotizantes, resultadosAportantes);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadosAportes;
	}
}