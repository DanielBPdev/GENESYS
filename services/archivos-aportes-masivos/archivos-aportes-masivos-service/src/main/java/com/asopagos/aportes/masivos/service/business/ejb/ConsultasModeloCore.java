package com.asopagos.aportes.masivos.service.business.ejb;

import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.aportes.masivos.dto.ArchivoAporteMasivoDTO;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.masivos.service.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.EmpresaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.pila.masivos.MasivoArchivo;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import javax.persistence.NoResultException;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;

@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    
    
    private static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
    private static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    private static final String ID_PERSONA = "idPersona";
    private static final String ID_PERSONAS = "idPersonas";
    private static final String ID_EMPRESAS = "idEmpresas";
    private static final String MOVIMIENTOS = "movimientos";

    private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "aportes_masivos_PU")
	private EntityManager entityManagerCore;

	@PersistenceContext(unitName = "aportes_masivos_pila_PU")
	private EntityManager entityManagerPila;

    public List<SolicitanteDTO> consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion, 
        String numeroIdentificacion, String periodo){
        logger.info("Inicia consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum,String, String)");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            		String fechaPagoString = null;

			if (periodo != null && !periodo.isEmpty()) {
				fechaPagoString = dateFormat.format(new Date(Long.parseLong(periodo)));
			}
			List<SolicitanteDTO> solicitantes = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_SOLICITANTE_APORTE_GENERAL_MASIVO,
							SolicitanteDTO.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("periodo", fechaPagoString).getResultList();
			
			if (solicitantes != null && !solicitantes.isEmpty()) {
				for (SolicitanteDTO solicitante : solicitantes ) {
					List<Object> estadoRegistroAporteAportante = (List<Object>) entityManagerCore
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFIACION_PERSONA)
						.setParameter("tipoIdentificacion", tipoIdentificacion.name())
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
					
					if (estadoRegistroAporteAportante != null && !estadoRegistroAporteAportante.isEmpty()) {
						solicitante.setEstado(EstadoAfiliadoEnum.valueOf(estadoRegistroAporteAportante.get(0).toString()));
					}

				}
			}
            logger.info(solicitantes.size());
			logger.debug("Finaliza consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum,String)");
			return solicitantes;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarPersonaSolicitanteAporteGeneral: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
    }

    public List<SolicitanteDTO> consultarEmpresaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion, 
    String numeroIdentificacion, String periodo){

    logger.info("Inicia consultarEmpresaSolicitanteAporteGeneral(TipoIdentificacionEnum,String, String)");
    try {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    String fechaPagoString = null;
    if (periodo != null && !periodo.isEmpty()) {
        fechaPagoString = dateFormat.format(new Date(Long.parseLong(periodo)));
    }
        List<SolicitanteDTO> solicitantes = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_SOLICITANTE_APORTE_GENERAL_MASIVO,
                        SolicitanteDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .setParameter("periodo", fechaPagoString).getResultList();
		
		if (solicitantes != null && !solicitantes.isEmpty()) {
			for (SolicitanteDTO solicitante : solicitantes ) {
				List<Object> estadoRegistroAporteAportante = (List<Object>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFIACION_EMPLEADOR)
					.setParameter("tipoIdentificacion", tipoIdentificacion.name())
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
				
				if (estadoRegistroAporteAportante != null && !estadoRegistroAporteAportante.isEmpty()) {
					solicitante.setEstado(EstadoAfiliadoEnum.valueOf(estadoRegistroAporteAportante.get(0).toString()));
				}

			}
		}

        logger.info(solicitantes.size());
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
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_MASIVO_TIPO_NUMERO_IDENTIFICACION,
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
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpresa(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EmpresaDTO consultarEmpresa(Long idPersona) {
		logger.debug("Inicia consultarEmpresa(Long idPersona)");
		try {
			EmpresaDTO empresa = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID_PERSONA_MASIVO, EmpresaDTO.class)
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
        
        
        /**
	 * (non-Javadoc)
	 *
	 * @see
	 *      com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarAporteYMovimiento(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteGeneralModeloDTO> consultarAporteYMovimiento(List<Long> idsAporte) {
		try {

			List<TipoAjusteMovimientoAporteEnum> movimientos = new ArrayList<>();
			movimientos.add(TipoAjusteMovimientoAporteEnum.DEVOLUCION);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA);
			movimientos.add(TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA);

			List<AporteGeneralModeloDTO> aportesGenerales = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_MASIVOS_GENERAL_Y_MOVIMIENTO,
							AporteGeneralModeloDTO.class)
					.setParameter("idsAporte", idsAporte).setParameter(MOVIMIENTOS, movimientos).getResultList();

			if (aportesGenerales != null && !aportesGenerales.isEmpty()) {
				aportesGenerales.stream().forEach((iteAportes) -> {
					if (iteAportes.getCuentaBancariaRecaudo() != null) {

						Query qCuentaBancaria = entityManagerCore.createNativeQuery(
								"select CONCAT(BANCO,'-',TIPO,'-',NUMERO_CUENTA,'-',TIPO_RECAUDO) AS CUENTA "
										+ "FROM cuentas_bancarias WHERE id = " + iteAportes.getCuentaBancariaRecaudo());
						List<Object> cuentaBancariaTexto = qCuentaBancaria.getResultList();
						if (cuentaBancariaTexto != null && !cuentaBancariaTexto.isEmpty()) {

							iteAportes.setCuentaBancariaRecaudoTexto(cuentaBancariaTexto.get(0).toString());
						}
					}
				});
			}

			return aportesGenerales;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarAporteYMovimiento(List<Long>): ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
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
			result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_EMPRESAS_ID_PERSONA_MASIVO,
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
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarEmpresasPorIds(java.util.List)
	 */
	@Override
	public List<EmpresaModeloDTO> consultarEmpresasPorIds(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloCore.consultarEmpresasPorIds(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EmpresaModeloDTO> result = null;

		try {
			List<Empresa> resultQuery = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_EMPRESAS_ID_MASIVO, Empresa.class)
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
        
    @Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PilaEstadoTransitorio> consultarEstadoProcesamientoPlanilla(Long idAporteGeneral) {
		List<PilaEstadoTransitorio> datosPlanilla = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_ESTADO_FORMALIZACION_DATOS_PLANILLA)
				.setParameter("idAporteGeneral", idAporteGeneral).getResultList();
		return datosPlanilla;
	}


    @Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarsolicitudesGlobales(Long idSolicitud) {
		List<Long> datosPlanilla = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_DEVOLUCION_MASIVA)
				.setParameter("idSolicitud", idSolicitud).getResultList();
		return datosPlanilla;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Solicitud consultarSolicitudGlobal(Long idSolicitud) {
		Solicitud sol = new Solicitud();
		sol = entityManagerCore.find(Solicitud.class, idSolicitud);
		return sol;

	}


	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Solicitud consultarSolicitudGlobalPorRadicado(String numeroRadicacion) {
		List<Solicitud> resultQuery = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POR_RADICADO,
					Solicitud.class)
					.setParameter("radicado", numeroRadicacion).getResultList();

		if (resultQuery != null && !resultQuery.isEmpty()) {
			return resultQuery.get(0);
		}
		return null;

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
			List<SolicitudAporteModeloDTO> resultQuery = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_SOLICITUD_APORTE_LISTADO_ID_REGGEN,
							SolicitudAporteModeloDTO.class)
					.setParameter("idsRegistroGeneral", idsRegistroGeneral).getResultList();

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
					.createNamedQuery(NamedQueriesConstants.CONSULTA_MASIVO_PAGO_SUBSIDIO_COTIZANTES)
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
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_PERSONAS_ID_LISTA_ID,
									PersonaModeloDTO.class)
							.setParameter(ID_PERSONAS, idsTemp).getResultList();

					for (PersonaModeloDTO persona : resultQuery) {
						result.put(persona.getIdPersona(), persona);
					}
				} else {
					List<Object[]> resultQuery = entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_PERSONAS_ID_LISTA_ID_EMPRESA)
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
        
        
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String periodoAporte) {
		String firmaMetodo = "ConsultasModeloCore.cotizanteConSubsidioPeriodo(TipoIdentificacionEnum, String, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Boolean result = Boolean.FALSE;

		try {
			StoredProcedureQuery storedProcedure = entityManagerCore
					.createStoredProcedureQuery(NamedQueriesConstants.HAY_MASIVO_SUBSIDIO_PARA_COTIZANTE);
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

	@Override
	public List<ArchivoAporteMasivoDTO> popularDatosMasivoArchivoAportes(List<MasivoArchivo> archivosMasivos) {
		List<ArchivoAporteMasivoDTO> dtoArchivos = new ArrayList<>();

			
		for (int i = 0; i < archivosMasivos.size(); i++) {
			//Obj[0] -> numeroTotalRegistros
			//Obj[1] -> numeroTotalErrores
			List<Object[]> objCargue =
				entityManagerCore.createNamedQuery(NamedQueriesConstants.POPULAR_DATOS_MASIVO_ARCHIVO_APORTES)
				.setParameter("cargueId", archivosMasivos.get(i).getIdCargue())
				.getResultList();
			if (objCargue == null) {
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
			Long totalRegistros = 0L;
			Long totalErrores = 0L;
			if (objCargue.size() > 0) {
				totalRegistros = Long.valueOf(
					objCargue.get(0)[0] != null ?
					objCargue.get(0)[0].toString()
					: "0");
				totalErrores = Long.valueOf(
					objCargue.get(0)[1] != null ?
					objCargue.get(0)[1].toString()
					: "0");
			}
			dtoArchivos.add(new ArchivoAporteMasivoDTO(
				archivosMasivos.get(i),
				totalRegistros,
				totalErrores
			));
		}
		return dtoArchivos;
	}

	@Override
	public List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
		try {
			List<DefinicionCamposCargaDTO> campos = (List<DefinicionCamposCargaDTO>) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CAMPOS_ARCHIVO)
					.setParameter("idFileDefinition", fileLoadedId).getResultList();
			return campos;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}


	@Override
	public Long consultarIdConsolaAporte(Long idCargue){
		List<Object> query = entityManagerCore
		.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_BUSCAR_CONSOLA)
			.setParameter("idCargue", idCargue).getResultList();
		if (query != null && !query.isEmpty()) {
			return Long.valueOf(query.get(0).toString());
		}
		return null;
		
	}

	@Override
	public CorreccionAportanteDTO popularCorreccionAportante(CorreccionAportanteDTO correccionAportanteDTO) {
		logger.info("inicia core.popularCorreccionAportante");
		List<Object[]> resultado = 
			entityManagerCore.createNamedQuery(NamedQueriesConstants.POPULAR_CORRECCION_APORTANTE_CORE)
			.setParameter("idAporte", correccionAportanteDTO.getIdAporte())
			.getResultList();
		logger.info("termina POPULAR_CORRECCION_APORTANTE_CORE");
		Object tipoCotizante = null;
		if (resultado != null && !resultado.isEmpty()) {

			Object[] datosCorreccion = resultado.get(0);

			correccionAportanteDTO.setRazonSocial(datosCorreccion[0] != null ? datosCorreccion[0].toString() : null);
			correccionAportanteDTO.setValorIntMora(datosCorreccion[1] != null ? new BigDecimal(datosCorreccion[1].toString()) : null);
			correccionAportanteDTO.setPrimerNombre(datosCorreccion[2] != null ? datosCorreccion[2].toString() : null);
			correccionAportanteDTO.setSegundoNombre(datosCorreccion[3] != null ? datosCorreccion[3].toString() : null);
			correccionAportanteDTO.setPrimerApellido(datosCorreccion[4] != null ? datosCorreccion[4].toString() : null);
			correccionAportanteDTO.setSegundoApellido(datosCorreccion[5] != null ? datosCorreccion[5].toString() : null);
			correccionAportanteDTO.setValTotalApoObligatorio(datosCorreccion[6] != null ? new BigDecimal(datosCorreccion[6].toString()): null);
		}

		for (CotizanteDTO cotizante : correccionAportanteDTO.getCotizantesNuevos()) {
			List<Object[]> resultadoCot = 
				entityManagerCore.createNamedQuery(NamedQueriesConstants.POPULAR_CORRECCION_APORTANTE_CORE_COTIZANTES)
				.setParameter("apdId", cotizante.getIdRegistro())
				.getResultList();
			try {
				tipoCotizante =  entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_COTIZANTE)
				.setParameter("apdId", cotizante.getIdRegistro())
				.getSingleResult();
			} catch (NoResultException e) {
				// TODO: handle exception
			}
			
			logger.info("termina POPULAR_CORRECCION_APORTANTE_CORE_COTIZANTES");
			Object[] datosCotizante;
			if (resultadoCot != null && !resultadoCot.isEmpty()) {
				datosCotizante  = resultadoCot.get(0);
				cotizante.setTipoCotizante(tipoCotizante != null ? TipoCotizanteEnum.obtenerTipoCotizante(Integer.valueOf(tipoCotizante.toString())) : null);
				cotizante.setTarifa(datosCotizante[0] != null ? new BigDecimal(datosCotizante[0].toString()) : null);
				cotizante.setDiasCotizados(datosCotizante[1] != null ? Short.parseShort(datosCotizante[1].toString()) : null);
			}
		}
		logger.info("fin core.popularCorreccionAportante");
		return correccionAportanteDTO;

	}
	@Override
	public void actualizarSolicitudDevolucionMasiva(Solicitud solicitud){
		solicitud.setResultadoProceso(ResultadoProcesoEnum.APROBADA);
		entityManagerCore.merge(solicitud);
        
    }
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void simularDevolucionMasivoCore(String numeroRadicado,Long idMedioPago) {
		try {
			StoredProcedureQuery procedimiento = entityManagerCore
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_SIMULARAPORTESDEVOLUCIONMASIVOSCORE)
					.setParameter("radicado", numeroRadicado)
					.setParameter("idMedioPago", idMedioPago);

			procedimiento.execute();
			// Actualizar estado del archivo masivo a cargado

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
    
}
