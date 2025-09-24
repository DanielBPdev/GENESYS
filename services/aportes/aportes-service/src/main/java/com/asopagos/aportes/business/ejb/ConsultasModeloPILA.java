package com.asopagos.aportes.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.aportes.business.interfaces.IConsultasModeloPILA;
import com.asopagos.aportes.constants.NamedQueriesConstants;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemAporteActualizadoModeloDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.temporal.TemAportante;
import com.asopagos.entidades.pila.temporal.TemAporte;
import com.asopagos.entidades.pila.temporal.TemCotizante;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.util.stream.Collectors;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloPILA implements IConsultasModeloPILA, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ConsultasModeloPILA.class);

	/**
	 * Entity Manager
	 */
@PersistenceContext(unitName = "pila_PU")
	private EntityManager entityManagerPila;	

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#ejecutarBloqueStaging(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarBloqueStaging(Long idTransaccion) {
		logger.debug("Inicia ejecutarBloqueStaging(Long)");
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_BLOQUE_STAGING)
					.setParameter("IdTransaccion", idTransaccion);
			logger.debug("Finaliza ejecutarBloqueStaging(Long)");
			procedimiento.execute();
		} catch (Exception e) {
			logger.debug("Finaliza ejecutarBloqueStaging");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#simularFasePilaDos(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void simularFaseUnoPilaDos(Long idTransaccion) {
		logger.info("Inicia simularFasePilaDos(Long)"+idTransaccion);
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.PROCEDURE_USP_EXECUTE_PILA_2_FASE_1_VALIDACION)
					.setParameter("idTransaccion", idTransaccion)
					.setParameter("sFase", FasePila2Enum.PILA2_FASE_1.toString());
			logger.info("Finaliza simularFasePilaDos(Long)");
			procedimiento.execute();
		} catch (Exception e) {
			logger.debug("Finaliza simularFasePilaDos");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#ejecutarDeleteBloqueStaging(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarDeleteBloqueStaging(Long idTransaccion) {
		logger.debug("Inicia ejecutarDeleteBloqueStaging(Long)");
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_DELETE_BLOQUE_STAGING)
					.setParameter("IdTransaccion", idTransaccion);
			logger.debug("Finaliza ejecutarDeleteBloqueStaging(Long)");
			procedimiento.execute();
		} catch (Exception e) {
			logger.debug("Finaliza ejecutarDeleteBloqueStaging");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#obtenerNotificacionRegistro(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object[] obtenerNotificacionRegistro(Long idRegistroGeneral) {
		logger.info("Inicia obtenerNotificacionRegistro(Long:"+idRegistroGeneral+")");
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.NOTIFICACION_APORTES_DEP)
					.setParameter("IdRegistroGeneral", idRegistroGeneral);
			logger.info("Finaliza obtenerNotificacionRegistro(Long"+idRegistroGeneral+")");
			return (Object[]) procedimiento.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#obtenerNotificacionRegistroEspecial(java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object[] obtenerNotificacionRegistroEspecial(Long idRegistroGeneral, String tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia obtenerNotificacionRegistroEspecial(Long, String, String)");
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.NOTIFICACION_APORTES_INDEP_PENS)
					.setParameter("IdRegistroGeneral", idRegistroGeneral)
					.setParameter("TipoIdentificacionCotizante", tipoIdentificacion)
					.setParameter("NumeroIdentificacionCotizante", numeroIdentificacion);
			logger.debug("Finaliza obtenerNotificacionRegistroEspecial(Long, String, String)");
			return (Object[]) procedimiento.getSingleResult();
		} catch (Exception e) {
			logger.debug("Finaliza obtenerNotificacionRegistroEspecial");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#simularFaseDosPilaDos(java.lang.Long,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void simularFaseDosPilaDos(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado) {
		logger.debug("Inicia simularFaseDosPilaDos(Long)");
		try {
			StoredProcedureQuery procedimientoAportes = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_PILA_2_FASE_2)
					.setParameter("IdTransaccion", idTransaccion).setParameter("EsProcesoManual", esProcesoManual)
					.setParameter("EsSimulado", esSimulado);
			logger.debug("Finaliza simularFaseDosPilaDos(Long)");
			procedimientoAportes.execute();
		} catch (Exception e) {
			logger.debug("Finaliza simularFaseDosPilaDos");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#simularFaseTresPilaDos(java.lang.Long,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void simularFaseTresPilaDos(Long idTransaccion, Boolean esRegistroManual, Boolean esSimulado) {
		logger.debug("Inicia simularFaseTresPilaDos(Long)");
		logger.info("idTransaccion "+ idTransaccion);
		logger.info("EsSimulado "+ esSimulado);
		logger.info("esRegistroManual "+ esRegistroManual);
		try {
			StoredProcedureQuery procedimientoNovedades = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_EXECUTE_PILA_2_FASE_3)
					.setParameter("IdTransaccion", idTransaccion)
					.setParameter("EsSimulado", esSimulado)
					.setParameter("EsRegistroManual", esRegistroManual);
			logger.debug("Finaliza simularFaseTresPilaDos(Long)");
			procedimientoNovedades.execute();
		} catch (Exception e) {
			logger.debug("Finaliza simularFaseTresPilaDos");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarIndicePlanilla(java.lang.String)
	 */
	@Override
	public IndicePlanilla consultarIndicePlanilla(String numPlanilla) {
		logger.debug("Inicia consultarIndicePlanilla(String)");
		try {
			List<TipoArchivoPilaEnum> lista = new ArrayList<TipoArchivoPilaEnum>();
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_I);
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IP);
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IR);
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IPR);

			IndicePlanilla indicePlanilla = entityManagerPila
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_PLANILLA, IndicePlanilla.class)
					.setParameter("numPlanilla", Long.parseLong(numPlanilla)).setParameter("lista", lista)
					.getSingleResult();
			logger.debug("Finaliza consultarIndicePlanilla(String)");
			return indicePlanilla;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.debug("Finaliza consultarIndicePlanilla");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	@Override
	public IndicePlanilla consultarIndicePlanillaNumeroAportante(String numPlanilla,Long registroDetallado) {
		logger.debug("Inicia consultarIndicePlanilla(String)");
		try {
			List<String> lista = new ArrayList<String>();
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_I.name());
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IP.name());
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IR.name());
			lista.add(TipoArchivoPilaEnum.ARCHIVO_OI_IPR.name());

			IndicePlanilla indicePlanilla = entityManagerPila
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_PLANILLA_NUMERO_APORTANTE, IndicePlanilla.class)
					.setParameter("numPlanilla", numPlanilla).setParameter("lista", lista)
					.setParameter("registroDetallado", registroDetallado.toString())
					.getSingleResult();
			logger.debug("Finaliza consultarIndicePlanilla(String)");
			return indicePlanilla;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.debug("Finaliza consultarIndicePlanilla");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#organizarNovedadesSucursal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void organizarNovedadesSucursal(Long idTransaccion) {
		logger.info("Inicia organizarNovedadesSucursal(Long)"+ idTransaccion);
		try {
			StoredProcedureQuery procedimientoNovedades = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_ValidarNovedadesEmpleadorActivoSUCURSALES)
					.setParameter("IdTransaccion", idTransaccion);
			logger.info("Finaliza organizarNovedadesSucursal(Long)");
			procedimientoNovedades.execute();
		} catch (Exception e) {
			logger.debug("Finaliza organizarNovedadesSucursal");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarAporteTemporal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDTO> consultarAporteTemporal(Long idRegistroGeneral) {
		logger.debug("Inicio de método consultarAporteTemporal(Long idTransaccion)");
		List<AporteDTO> aportes = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_TEMPORAL, AporteDTO.class)
				.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
		logger.debug("Fin de método consultarAporteTemporal(Long idTransaccion)");
		return aportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarNovedad(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemNovedad> consultarNovedad(Long idRegistroGeneral) {
		logger.info("**__**Inicio de método consultarNovedad(Long)");
		List<TemNovedad> novedades = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_TEMPORAL, TemNovedad.class)
				.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

		      for (TemNovedad temNovedad : novedades) {
             logger.info("**__**¨consultarNovedad))))). : getAccionNovedad: "+ temNovedad.getAccionNovedad());
			logger.info("**__**¨ consultarNovedad))))) : getRegistroDetallado"+ temNovedad.getRegistroDetallado());
            logger.info("**__**¨ consultarNovedad))))) getTipoTransaccion: "+ temNovedad.getTipoTransaccion());
			logger.info("**__**¨ consultarNovedad))))) getFechaInicioNovedad: "+ temNovedad.getFechaInicioNovedad());
               logger.info("**__**temNovedad.getNumeroIdCotizante()"+temNovedad.getNumeroIdCotizante());
			  }
		logger.info("**__**Fin de método consultarNovedad(Long)");
		return novedades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#
	 * ejecutarDeleteTemporalesPILA(java.lang.Long)
	 */
	@Override
	public void ejecutarDeleteTemporalesPILA(Long idRegistroGeneral) {
		try {
			logger.debug("Inicio de método ejecutarDeleteTemporalesPILA(Long idRegistroGeneral)");
			/*
			 * mantis 232043 se realiza ajuste de lista TemAporte para que pueda
			 * borrar los registros de las tablas temporales correspondiente a
			 * su idRegistroGeneral
			 */
			List<TemAporte> temAportes = entityManagerPila
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_TEMAPORTE, TemAporte.class)
					.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

			List<Long> idTransaccion = new ArrayList<>();
			/*
			 * Se recorre los temaporte para ir agregando a una lista de long
			 * con los id de trasaccion
			 */
			for (TemAporte temAporte : temAportes) {
				idTransaccion.add(temAporte.getIdTransaccion());
			}

			if (!idTransaccion.isEmpty() && idTransaccion != null) {
				entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMNOVEDAD)
						.setParameter("idTransaccion", idTransaccion).executeUpdate();
				entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTE)
						.setParameter("idTransaccion", idTransaccion).executeUpdate();
				entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTANTE)
						.setParameter("idTransaccion", idTransaccion).executeUpdate();
				entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMCOTIZANTE)
						.setParameter("idTransaccion", idTransaccion).executeUpdate();
				entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTEPROCESADO)
						.setParameter("idRegistroGeneral", idRegistroGeneral).executeUpdate();
			}
			logger.debug("Fin de método ejecutarDeleteTemporalesPILA(Long idRegistroGeneral)");
		} catch (NoResultException nre) {
			logger.debug(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		} catch (Exception e) {
			logger.error("Ocurrió un error en ejecutarDeleteTemporalesPILA(Long idRegistroGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#ejecutarVerificacionNovedadesFuturas(java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarVerificacionNovedadesFuturas(Date fechaValidacion) {
		String firmaMetodo = "ConsultasModeloPila.ejecutarVerificacionNovedadesFuturas(Date)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			StoredProcedureQuery storedProcedureQuery = entityManagerPila.createNamedStoredProcedureQuery(
					NamedQueriesConstants.PROCEDURE_USP_SOLICITAR_EVALUACION_NOVEDAD_FUTURA);

			storedProcedureQuery.setParameter("dFechaActual", fechaValidacion);

			storedProcedureQuery.execute();
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + e.getMessage());
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#validarSucursal(java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean validarSucursal(Long idRegistroGeneral, String sucursalPILA, String sucursalPrincipal) {
		logger.debug("Inicia validarSucursal(Long, String, String)");
		try {
			Boolean result = false;

			StoredProcedureQuery procedimiento = entityManagerPila
					.createStoredProcedureQuery(NamedQueriesConstants.VALIDAR_SUCURSAL);

			procedimiento.registerStoredProcedureParameter("IdRegistroGeneral", Long.class, ParameterMode.IN);
			procedimiento.setParameter("IdRegistroGeneral", idRegistroGeneral);
			procedimiento.registerStoredProcedureParameter("codigoSucursalPILA", String.class, ParameterMode.IN);
			procedimiento.setParameter("codigoSucursalPILA", sucursalPILA);
			procedimiento.registerStoredProcedureParameter("codigoSucursalPrincipal", String.class, ParameterMode.IN);
			procedimiento.setParameter("codigoSucursalPrincipal", sucursalPrincipal);
			// salida
			procedimiento.registerStoredProcedureParameter("cumpleSucursal", Boolean.class, ParameterMode.OUT);
			procedimiento.execute();
			result = (Boolean) procedimiento.getOutputParameterValue("cumpleSucursal");
			logger.debug("Finaliza validarSucursal(Long, String, String)");
			return result;
		} catch (Exception e) {
			logger.debug("Finaliza validarSucursal");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarActualizacionesAporte(java.lang.Boolean, java.util.List)
     */
    @Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemAporteActualizadoModeloDTO> consultarActualizacionesAporte(Boolean procesoManual,
            List<Long> idsRegistrosOrigen) {
		String firmaMetodo = "ConsultasModeloPILA.consultarActualizacionesAporte(Boolean, List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TemAporteActualizadoModeloDTO> result = null;
        Boolean conIds = idsRegistrosOrigen != null && !idsRegistrosOrigen.isEmpty();

        List<Long> sublist = new ArrayList<>();
        if (conIds) {
            result = new ArrayList<>();
            for (Long id : idsRegistrosOrigen) {
                sublist.add(id);
                if (sublist.size() == 1000) {
                    result.addAll(
                            entityManagerPila
                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_PARA_ACTUALIZAR,
                                            TemAporteActualizadoModeloDTO.class)
                                    .setParameter("procesoManual", procesoManual)
                                    .setParameter("conIds", conIds).setParameter("idsRegistrosOrigen", sublist).getResultList());

                    sublist.clear();
                }
            }

            if (!sublist.isEmpty()) {
                result.addAll(
                        entityManagerPila
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_PARA_ACTUALIZAR,
                                        TemAporteActualizadoModeloDTO.class)
                                .setParameter("procesoManual", procesoManual)
                                .setParameter("conIds", conIds).setParameter("idsRegistrosOrigen", sublist).getResultList());

            }

        }
        else {
            sublist.add(0L);
            result = entityManagerPila
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_PARA_ACTUALIZAR, TemAporteActualizadoModeloDTO.class)
                    .setParameter("procesoManual", procesoManual)
                    .setParameter("conIds", conIds).setParameter("idsRegistrosOrigen", sublist).getResultList();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#eliminarActualizacionesAporte(java.util.Set)
	 */
	@Override
	public void eliminarActualizacionesAporte(Set<Long> listaIdPeticiones) {
		String firmaMetodo = "ConsultasModeloPILA.eliminarActualizacionesAporte(Set<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			List<Long> ids = new ArrayList<>();
			ids.addAll(listaIdPeticiones);
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTEACTUALIZADO)
					.setParameter("idPeticion", ids).executeUpdate();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#ejecutarRevalidarPila2Fase1(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarRevalidarPila2Fase1(Long idRegistroGeneral) {
		String firmaMetodo = "ConsultasModeloPILA.ejecutarRevalidarPila2Fase1(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			StoredProcedureQuery query = entityManagerPila
					.createStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_REVALIDAR_PILA2_FASE1);

			query.registerStoredProcedureParameter("iIdRegistroGeneral", Long.class, ParameterMode.IN);
			query.setParameter("iIdRegistroGeneral", idRegistroGeneral);

			query.execute();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en el SP: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPila#consultarIdsTemAporteProcesado(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarIdsTemAporteProcesado(List<Long> ids) {
		String firmaMetodo = "ConsultasModeloPila.consultarIdsTemAporteProcesado(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Long> result = null;

		try {
			List<BigInteger> resultQuery = entityManagerPila
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_PROCESADO_POR_REGISTRO_MASIVO)
					.setParameter("idRegistroGeneral", ids).getResultList();

			result = new ArrayList<>();
			for (BigInteger registro : resultQuery) {
				result.add(registro.longValue());
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
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#crearTemAporteProcesado(java.util.List)
	 */
	@Override
	public void crearTemAporteProcesado(List<ConsultaPresenciaNovedadesDTO> mapaNovedades) {
		String firmaMetodo = "ConsultasModeloPila.crearTemAporteProcesado(Map<Long, ConsultaPresenciaNovedadesDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			String query = null;

			for (ConsultaPresenciaNovedadesDTO datos : mapaNovedades) {
				String tieneNovedad = datos.getTieneNovedades() ? "1" : "0";
				query = "INSERT INTO dbo.TemAporteProcesado (tprAporteGeneral, tprAporte, tprNovedadesProcesadas, tprPresentaNovedades) "
						+ "VALUES (" + datos.getIdRegGen() + ", " + datos.getTamanioPlanilla() + ", 0, " + tieneNovedad
						+ ")";

				entityManagerPila.createNativeQuery(query).executeUpdate();
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
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarIndicesPlanillaPorIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IndicePlanillaModeloDTO> consultarIndicesPlanillaPorIds(List<Long> idsPlanilla) {
		String firmaMetodo = "ConsultasModeloPila.crearTemAporteProcesado(Map<Long, ConsultaPresenciaNovedadesDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<IndicePlanillaModeloDTO> result = new ArrayList<>();

		try {
			int registrosPag = 1500;
			int contIds = 0; 
			int cantRegistros  = idsPlanilla.size();

			ArrayList<Long> paginaIdP = new ArrayList<>();
			

			do{
				for (int i = 0; i < registrosPag && contIds<cantRegistros; i++) {
					paginaIdP.add(idsPlanilla.get(contIds));
					contIds++;
				}

				List<IndicePlanillaModeloDTO> resultQ = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_PLANILLA_LISTA_ID,
				IndicePlanillaModeloDTO.class).setParameter("idsPlanilla", paginaIdP).getResultList();
				if(!resultQ.isEmpty() && resultQ != null){
					for(IndicePlanillaModeloDTO sol :resultQ){
						result.add(sol);
						
					}
				}
				paginaIdP.clear();
			}
			while(contIds<cantRegistros);

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
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarInformacionAportesProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionAportesProcesar() {
		String firmaMetodo = "ConsultasModeloPila.consultarInformacionAportesProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InformacionPlanillasRegistrarProcesarDTO> infoAportes;
		infoAportes = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NUM_APORTES,
				InformacionPlanillasRegistrarProcesarDTO.class).getResultList();
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infoAportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarInformacionNovedadesProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesar() {
		String firmaMetodo = "ConsultasModeloPila.consultarInformacionNovedadesProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades;
		infoNovedades = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NUM_NOVEDADES,
				InformacionPlanillasRegistrarProcesarDTO.class).getResultList();
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infoNovedades;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarAportesPlanillasRegistrarProcesar(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	// TODO: usar paginador real
	public List<AporteDTO> consultarAportesPlanillasRegistrarProcesar(Long idRegistroGeneral, Integer pagina, Integer tamanoPaginador) {
		String firmaMetodo = "ConsultasModeloPila.consultarAportesPlanillasRegistrarProcesar(idRegistroGeneral="
				+ idRegistroGeneral + ", pagina=" + pagina + ")";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AporteDTO> aportesPlanillas = new ArrayList<>();
		List<Object[]> resultQuery = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_APORTES_A_PROCESAR)
				.setParameter("idRegistroGeneral", idRegistroGeneral).setFirstResult(tamanoPaginador * pagina)
				.setMaxResults(tamanoPaginador).getResultList();

		AporteDTO aporteDTO = null;
		for (Object[] resultado : resultQuery) {
			TemAporte aporte = (TemAporte) resultado[0];
			TemAportante aportante = resultado[1] != null ? (TemAportante) resultado[1] : null;
			TemCotizante cotizante = resultado[2] != null ? (TemCotizante) resultado[2] : null;

			// sólo se crean los aporte DTO de temporales que tengan la informción completa de la persona
			if(aportante != null && aportante.getTipoDocAportante() != null && aportante.getIdAportante() != null
					&& cotizante != null && cotizante.getTipoCotizante() != null && cotizante.getIdCotizante() != null){
				aporteDTO = new AporteDTO(aportante, cotizante, aporte);
				aportesPlanillas.add(aporteDTO);
			}else{
				logger.warn("Datos incompletos para el registro general " + aporte.getRegistroGeneral()
						+ " y registro detallado " + aporte.getRegistroDetallado() + ". El aporte no será procesado");
			}
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return aportesPlanillas;
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long buscarNotificacionPlanillasNCore(Long idRegistroGeneral) {
		Object cantidadNObject = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_NOTIFICACION_PLANILLAS_N)
				.setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();
		logger.info("**__**Cantidad buscarNotificacionPlanillasNCore cantidadNObject: " + cantidadNObject
				+ " idRegistroGeneral:" + idRegistroGeneral);
		Long cantidadN = Long.valueOf(cantidadNObject.toString());
		return cantidadN;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarNovedadesPlanillasRegistrarProcesar(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object construirNovedadesPlanillasRegistrarProcesar(Long planillaAProcesar) {
				Object respuesta = null;
				StoredProcedureQuery procedimiento = entityManagerPila
							.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_ASP_PROCESADO_NOVEDADES)
							.setParameter("idRegistroGeneral", planillaAProcesar);
					logger.debug("Finaliza ejecutarBloqueStaging(Long)");
					procedimiento.execute();
					logger.info("Finaliza PROCEDURE_ASP_PROCESADO_NOVEDADES"+procedimiento.getOutputParameterValue("resul"));
				respuesta = procedimiento.getOutputParameterValue("resul");
			return respuesta;
			 }


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarNovedadesPlanillasRegistrarProcesar(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NovedadesProcesoAportesDTO> consultarNovedadesPlanillasRegistrarProcesar(Long planillaAProcesar,
			Integer pagina) { 
		String firmaMetodo = "aaaConsultasModeloPila.consultarNovedadesPlanillasRegistrarProcesar(planillaAProcesar="
				+ planillaAProcesar + ", pagina=" + pagina + ")";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<NovedadesProcesoAportesDTO> result = null;
		


		
		String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
		sTamanoPaginador = sTamanoPaginador == null?"100":sTamanoPaginador;
		Integer tamanoPaginador = new Integer(sTamanoPaginador);

	/* 	List<NovedadesProcesoAportesDTO> novedadesPlanillas = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NOVEDADES_A_PROCESAR,
						NovedadesProcesoAportesDTO.class)
				.setParameter("idRegistroGeneral", planillaAProcesar).setFirstResult(tamanoPaginador * pagina)
				.setMaxResults(tamanoPaginador).getResultList();*/
///////////////////////////////////////////////////
			List<NovedadesProcesoAportesDTO> novedadesPlanillasSP = new ArrayList<NovedadesProcesoAportesDTO>();
			try {
			StoredProcedureQuery query = entityManagerPila.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_PLANILLA_NOVEDADES_A_PROCESAR);
			query.setParameter("idRegistroGeneral", planillaAProcesar);
			query.setFirstResult(tamanoPaginador * pagina);
			query.setMaxResults(tamanoPaginador);
			//	query.execute();
			novedadesPlanillasSP =  query.getResultList();
			} catch (Exception e) {
				logger.info(" :: Hubo un error en el SP: " + e);
			} 	

/////////////////////////////////////
		// se organizan los DTO obtenidos
		Map<Long, NovedadesProcesoAportesDTO> novedadesAgrupadas = new HashMap<>(); 
		NovedadesProcesoAportesDTO novTemp = null;
		int cont =1;
	//	for (NovedadesProcesoAportesDTO novedad : novedadesPlanillas) {
	//		logger.info(cont+"**__**getNumeroIdentificacion "+ novedad.getNumeroIdentificacion());
	//		
	//		for(NovedadPilaDTO novpila: novedad.getNovedades()){
	//					logger.info(cont+"**__**consultarNovedadesPlanillasRegistrarProcesar : getAccionNovedad: "+ novpila.getAccionNovedad());
	//		logger.info(cont+"**__**consultarNovedadesPlanillasRegistrarProcesar : getNumeroIdentificacionCotizante"+ novpila.getNumeroIdentificacionCotizante());
    //        logger.info(cont+"**__**consultarNovedadesPlanillasRegistrarProcesar getTipoTransaccion: "+ novpila.getTipoTransaccion());
	//		logger.info(cont+"**__**consultarNovedadesPlanillasRegistrarProcesar getFechaInicioNovedad: "+ novpila.getFechaInicioNovedad());
	//			cont ++;
	//		}
	//		
	//		novTemp = novedadesAgrupadas.get(novedad.getIdRegistroDetallado());
	//		if (novTemp == null) {
	//			novedadesAgrupadas.put(novedad.getIdRegistroDetallado(), novedad);
	//		} else {
	//			novTemp.getNovedades().addAll(novedad.getNovedades());
	//		}
	//	}
//
	//	result = new ArrayList<>(novedadesAgrupadas.values());
		result = novedadesPlanillasSP;
		logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		cont=1;
			for (NovedadesProcesoAportesDTO novedad1 : result) {
			logger.info(cont+"**__**novedad.getIdRegistroDetallado(): "+ novedad1.getIdRegistroDetallado());
			
			for(NovedadPilaDTO novpila1: novedad1.getNovedades()){
			logger.info(cont+"**__**>>>consultarNovedadesPlanillasRegistrarProcesar : getAccionNovedad: "+ novpila1.getAccionNovedad());
			logger.info(cont+"**__**>>>consultarNovedadesPlanillasRegistrarProcesar : getNumeroIdentificacionCotizante"+ novpila1.getNumeroIdentificacionCotizante());
            logger.info(cont+"**__**>>>consultarNovedadesPlanillasRegistrarProcesar getTipoTransaccion: "+ novpila1.getTipoTransaccion());
			logger.info(cont+"**__**>>>consultarNovedadesPlanillasRegistrarProcesar getFechaInicioNovedad: "+ novpila1.getFechaInicioNovedad());
				cont ++;
			}
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#validarProcesadoNovedades(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object validarProcesadoNovedades(Long idPlanilla) {
		logger.debug("Inicia ejecutarBloqueStaging(Long)");
		logger.info("idPlanilla: " + idPlanilla);
		Object respuesta = null;
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_ASP_VALIDAR_PROCESADO_NOVEDADES)
					.setParameter("registroGeneral", idPlanilla);
			logger.debug("Finaliza ejecutarBloqueStaging(Long)");
			procedimiento.execute();
			logger.info(procedimiento.getOutputParameterValue("validar"));
			respuesta = procedimiento.getOutputParameterValue("validar");
			return respuesta;
		} catch (Exception e) {
			logger.debug("Finaliza ejecutarBloqueStaging");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#eliminarTemporales(java.lang.Boolean,
	 *      java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminarTemporales(Boolean sonAportes, List<Long> idsDetalle) {
		String firmaMetodo = "ConsultasModeloPila.consultarAportesPlanillasRegistrarProcesar(Boolean, List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (!idsDetalle.isEmpty() && sonAportes) {
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTE)
					.setParameter("idTransaccion", idsDetalle).executeUpdate();
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTANTE)
					.setParameter("idTransaccion", idsDetalle).executeUpdate();
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMCOTIZANTE)
					.setParameter("idTransaccion", idsDetalle).executeUpdate();
		} else if (!idsDetalle.isEmpty()){
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMNOVEDAD_DETALLE)
					.setParameter("idTransaccion", idsDetalle).executeUpdate();
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#actualizarTemAporteProcesado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarTemAporteProcesado() {
		String firmaMetodo = "ConsultasModeloPila.actualizarTemAporteProcesado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		entityManagerPila.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_TEM_APORTE_PROCESADO).executeUpdate();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
        
        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#actualizarTemAporteProcesado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarTemAporteProcesadoByIdPlanilla(Long idPlanilla) {
		String firmaMetodo = "ConsultasModeloPila.actualizarTemAporteProcesadoByIdPlanilla()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		entityManagerPila.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_TEM_APORTE_PROCESADO_IDPLANILLA)
                        .setParameter("idPlanilla", idPlanilla).executeUpdate();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarDatosComunicado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicado() {
		String firmaMetodo = "ConsultasModeloPila.consultarDatosComunicado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<DatosComunicadoPlanillaDTO> result = entityManagerPila.createNamedQuery(
				NamedQueriesConstants.CONSULTAR_TEM_APORTE_PROCESADO, DatosComunicadoPlanillaDTO.class).getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
        
        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarDatosComunicado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicadoByIdPlanilla(Long idPlanilla) {
		String firmaMetodo = "ConsultasModeloPila.consultarDatosComunicadoByIdPlanilla()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<DatosComunicadoPlanillaDTO> result = entityManagerPila.createNamedQuery(
				NamedQueriesConstants.CONSULTAR_TEM_APORTE_PROCESADO_IDPLANILLA, DatosComunicadoPlanillaDTO.class)
                        .setParameter("idPlanilla", idPlanilla).getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result; 
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#eliminarTemAporteProcesado(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminarTemAporteProcesado(List<Long> idsDetalle) {
		String firmaMetodo = "ConsultasModeloPila.eliminarTemAporteProcesado(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (!idsDetalle.isEmpty()) {
			entityManagerPila.createNamedQuery(NamedQueriesConstants.DELETE_TEMAPORTEPROCESADO_MASIVO)
					.setParameter("idRegistroGeneral", idsDetalle).executeUpdate();
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#marcarTemporalEnProceso(java.util.List,
	 *      java.lang.Boolean)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void marcarTemporalEnProceso(List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas,
			Boolean esAporte, Boolean enProceso) {
		String firmaMetodo = "ConsultasModeloPila.marcarTemporalEnProceso(List<InformacionPlanillasRegistrarProcesarDTO>, "
				+ "Boolean, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		
		if (!infoPlanillas.isEmpty()) {
			String query = null;
			if (esAporte) {
				query = NamedQueriesConstants.MARCA_PROCESO_APORTES;
			} else {
				query = NamedQueriesConstants.MARCA_PROCESO_NOVEDADES;
			}

			List<Long> subList = new ArrayList<>();
			for (InformacionPlanillasRegistrarProcesarDTO planilla : infoPlanillas) {
				subList.add(planilla.getRegistroGeneral());
				if (subList.size() == 1000) {
					entityManagerPila.createNamedQuery(query).setParameter("idsRegistroGeneral", subList)
							.setParameter("enProceso", enProceso ? 1 : 0).executeUpdate();
					subList.clear();
				}
			}

			if (!subList.isEmpty()) {
				entityManagerPila.createNamedQuery(query).setParameter("idsRegistroGeneral", subList)
						.setParameter("enProceso", enProceso ? 1 : 0).executeUpdate();
			}
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
        
        
        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarInformacionAportesProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionAportesProcesarByIdPlanilla(Long idPlanilla) {
		String firmaMetodo = "***ConsultasModeloPila.consultarInformacionAportesProcesar()";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InformacionPlanillasRegistrarProcesarDTO> infoAportes;
		infoAportes = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NUM_APORTES_IDPLANILLA,
				InformacionPlanillasRegistrarProcesarDTO.class).setParameter("idPlanilla", idPlanilla).getResultList();
		logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infoAportes;
	}
        
        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarInformacionNovedadesProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesarByIdPlanilla(Long idPlanilla) {
		String firmaMetodo = "***ConsultasModeloPila.consultarInformacionNovedadesProcesar()";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades;
		infoNovedades = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NUM_NOVEDADES_IDPLANILLA,
				InformacionPlanillasRegistrarProcesarDTO.class).setParameter("idPlanilla", idPlanilla).getResultList();
		logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infoNovedades;
	}

	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarPlanillasCorreccionN()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarPlanillasCorreccionN(List<String> listaPlanillaConCorrecion) {

		String firmaMetodo = "ConsultasModeloPila.consultarPlanillasCorreccionN()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		
		List<Object[]> resultQuery = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_CORRECCION)
				.setParameter("numeroPlanilla", listaPlanillaConCorrecion).getResultList();
		
		List<CuentaAporteDTO> resultadoDTO = new ArrayList<CuentaAporteDTO>();
		
		if(resultQuery.size()>0) {
			
			for (Object[] resultado : resultQuery) {
				CuentaAporteDTO cuentaAporte = new CuentaAporteDTO();
				cuentaAporte.setNumeroPlanilla(resultado[0] != null ? (String) resultado[0] : null);
				cuentaAporte.setNumeroPlanillaCorregida(resultado[1] != null ? (String) resultado[1] : null);
				
				resultadoDTO.add(cuentaAporte);
			}
			
		}
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadoDTO;
		
		
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarInformacionNovedadesProcesarFuturas()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesarFuturas() {
		String firmaMetodo = "ConsultasModeloPila.consultarInformacionNovedadesProcesarFuturas()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades;
		infoNovedades = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_NUM_NOVEDADES_FUTURAS,
				InformacionPlanillasRegistrarProcesarDTO.class).getResultList();
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infoNovedades;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDTO> consultarDatosAportantesTemporales(Long idRegistroGeneral) {
		String firmaServicio = "PilaBusiness.consultarDatosAportantesTemporales(Long:" + idRegistroGeneral + ")";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<Object[]> listaDatosAportante = (List<Object[]>)
				entityManagerPila.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_INFO_PROCESAR)
				.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
		
		TemAportante tap;
		AporteDTO aportante;
		List<AporteDTO> result = new ArrayList<AporteDTO>();
		for(Object[] datosAportante : listaDatosAportante) {
			tap = (TemAportante) datosAportante[0];
			Long idPersona = (Long) datosAportante[1];
			Long idEmpresa = (Long) datosAportante[2];
			Long idEmpleador = (Long) datosAportante[3];
			String estadoEmpleador = (String) datosAportante[4];
			Boolean tieneCotizanteDependienteReintegrable = (Boolean) datosAportante[5];
			String periodoAporte = (String) datosAportante[6];
			String modalidadRecaudoAporte = (String) datosAportante[7];
			
			aportante = new AporteDTO(tap, idPersona, idEmpresa,
					idEmpleador, estadoEmpleador,
					tieneCotizanteDependienteReintegrable, periodoAporte,
					modalidadRecaudoAporte);
			result.add(aportante);
		}
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDTO> consultarDatosCotizantesTemporalesPorCrear(Long idRegistroGeneral) {
		String firmaServicio = "PilaBusiness.consultarDatosCotizantesTemporalesPorCrear(Long:" + idRegistroGeneral + ")";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<TemCotizante> listaCotizantesPorCrear = entityManagerPila
				.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_COTIZANTES_POR_CREAR)
				.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
		
		List<AporteDTO> cotizantesPorCrear = new ArrayList<AporteDTO>();
		AporteDTO aporte;
		for (TemCotizante cto : listaCotizantesPorCrear) {
			aporte = new AporteDTO(cto);
			cotizantesPorCrear.add(aporte);
		}
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cotizantesPorCrear;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long contarTemAportesPendientes(Long idPlanilla) {
		Integer count = (Integer) entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_APORTES_TEMPORALES)
				.setParameter("idPlanilla", idPlanilla).getSingleResult();
		return count.longValue();
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> PilaIndicePlanilla() {
		String consulta = "SELECT  p.pipId FROM  PilaIndicePlanilla p WHERE "+
				"p.pipEstadoArchivo in ('PROCESADO_SIN_NOVEDADES', 'PROCESADO_NOVEDADES')"+
				"AND p.pipTipoArchivo like 'ARCHIVO_OI_I%' ";
		return entityManagerPila.createNativeQuery(consulta).getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MovimientoIngresosDTO> consultarMovimientoHistoricosPila(List<MovimientoIngresosDTO> movimientos) {

		List<MovimientoIngresosDTO> movimientosAjustados= new ArrayList();
		// Se fragmenta la lista en porciones de a 1000
		// Esto reduce la cantidad de conexiones a la base de datos
		// Y reduce el  tiempo de las respuestas al cargar un batch de resultados
		int batch = 1000;
		int cont = 0;
		int inicio=0,fin=0;
		Map<Long, String[]> mapaDatosRestantes;
		//Mapa con resultados de consulta por aporteGeneral
		while (cont * batch < movimientos.size()) {
			mapaDatosRestantes = new HashMap<Long, String[]>();
			inicio = cont * batch;
			cont ++;
			fin = cont * batch;
			if (cont * batch > movimientos.size()) {
				fin = movimientos.size();
			}
			List<MovimientoIngresosDTO> subLista = movimientos.subList(inicio, fin);
			List<Long> regGen = subLista.stream().map(MovimientoIngresosDTO::getIdRegistroGeneral).collect(Collectors.toList());

			// Se realiza la consulta
			List<Object[]> datosRestantesAportante = (List<Object[]>)
					entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVIMIENTO_HISTORICO_PILA)
					.setParameter("idRegistroGenerales", regGen)
					.getResultList();

			// Se llena el mapa para asignar valores a cada registroGeneral
			if (datosRestantesAportante != null && !datosRestantesAportante.isEmpty()) {
				for (Object[] data: datosRestantesAportante) {
					String[] datoRes = new String[]{
						data[1] != null ? data[1].toString(): "",
						data[2] != null ? data[2].toString(): "",
						data[3] != null ? data[3].toString() : "0"
					};
					mapaDatosRestantes.put(Long.valueOf(data[0].toString()),datoRes);
				}
			}

			for (MovimientoIngresosDTO movimiento: subLista) {
				String[] datosPila = mapaDatosRestantes.get(movimiento.getIdRegistroGeneral());
				if (datosPila != null && movimiento != null) {
					movimiento.setNumeroPlanilla(datosPila[0] != null ? datosPila[0].toString(): "");
					movimiento.setPeriodoPagoAporte(datosPila[1] != null ? datosPila[1].toString(): "");
					if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(movimiento.getTipoEntidad())
						&&  datosPila[2] != null) {
						movimiento.setPorcentajeAporte(new BigDecimal(datosPila[2].toString()));
					}
				}
			}


			movimientosAjustados.addAll(subLista);
			
		}

		return movimientosAjustados;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTotalAporteObligatorio(String idPlanilla){
		logger.info("Inicia metodo Consulta Total aporte obligatorio");
		List<Object> total = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO)
				.setParameter("idPlanilla", idPlanilla)
				.getResultList();
		if (total == null || total.isEmpty()) return null;
		return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorMora(String idPlanilla){
		List<Object> total = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_VALOR_MORA)
				.setParameter("idPlanilla", idPlanilla)
				.getResultList();
		if (total == null || total.isEmpty()) return null;
		return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTotalReacudo(String idPlanilla){
		List<Object> total = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_TOTAL_RECAUDO)
				.setParameter("idPlanilla", idPlanilla)
				.getResultList();
		if (total == null || total.isEmpty()) return null;
		return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTarifaCotizante(String idPlanilla, String idRegistro){
		List<Object> total = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_TARIFA_COTIZANTE)
				.setParameter("idPlanilla", idPlanilla)
				.setParameter("idRegistro", idRegistro)
				.getResultList();
		if (total == null || total.isEmpty()) return null;
		return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTarifaAportante(String idRegistroGeneral, String numeroIdentificacionAportante, String tipoIdentificacionAportante){
		List<Object> total = entityManagerPila
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_TARIFA_APORTANTE)
				.setParameter("idNumeroIdentificacionAportante", numeroIdentificacionAportante)
				.setParameter("idTipoIdentificacionAportante", tipoIdentificacionAportante)
				.setParameter("idRegistroGeneral", Long.parseLong(idRegistroGeneral))
				.getResultList();
		if (total == null || total.isEmpty()) return null;
		return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTarifaAportanteCotizante(String idPlanilla, String numeroIdentificacion){
		List<Object> total = entityManagerPila
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE)
			.setParameter("idPlanilla", idPlanilla)
			.setParameter("idIdentificacion", numeroIdentificacion)
			.getResultList();

		if (total == null || total.isEmpty()) return null;
			return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTarifaAportanteCotizanteMora(String idPlanilla, String numeroIdentificacion){
		List<Object> total = entityManagerPila
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE_MORA)
			.setParameter("idPlanilla", idPlanilla)
			.setParameter("idIdentificacion", numeroIdentificacion)
			.getResultList();

		if (total == null || total.isEmpty()) return null;
			return total.get(0).toString();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarValorTarifaAportanteCotizanteTotal(String idPlanilla, String numeroIdentificacion){
		List<Object> total = entityManagerPila
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE_TOTAL)
			.setParameter("idPlanilla", idPlanilla)
			.setParameter("idIdentificacion", numeroIdentificacion)
			.getResultList();

		if (total == null || total.isEmpty()) return null;
			return total.get(0).toString();
	}
}
