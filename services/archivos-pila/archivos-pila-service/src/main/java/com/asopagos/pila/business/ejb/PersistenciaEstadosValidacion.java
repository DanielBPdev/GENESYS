package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.MensajesGestionEstadosEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo las operaciones con BD relacionadas a la gestión de
 * estados de validación<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class PersistenciaEstadosValidacion implements IPersistenciaEstadosValidacion, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "archivosPila_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PersistenciaEstadosValidacion.class);

    /** Constante para el mensaje de consulta de estados por bloque */
    private static final String ESTADO_BLOQUE = "estados por bloque de validación";

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#registrarEstadoOI(com.asopagos.entidades.pila.
     * EstadoArchivoPorBloque)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarEstadoOI(EstadoArchivoPorBloque nuevoEstado) {
        logger.debug("Inicia registrarEstadoOI(EstadoArchivoPorBloque)");

        try {
            entityManager.persist(nuevoEstado);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_ACTUALIZACION_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarEstadoOI(EstadoArchivoPorBloque) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza registrarEstadoOI(EstadoArchivoPorBloque)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#consultarEstadoTipoArchivoOI(com.asopagos.entidades.pila.
     * IndicePlanilla)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoArchivoPorBloque consultarEstadoEspecificoOI(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarEstadoEspecificoOI(IndicePlanilla)");
        EstadoArchivoPorBloque result = null;
        try {
            //se realiza la consulta del estado de la planilla
            result = (EstadoArchivoPorBloque) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_PLANILLA_TIPO)
                    .setParameter(ConstantesComunesProcesamientoPILA.INDICE_PLANILLA, indicePlanilla)
                    .setParameter(ConstantesComunesProcesamientoPILA.TIPO_ARCHIVO, indicePlanilla.getTipoArchivo())
                    .setParameter("codOperador", indicePlanilla.getCodigoOperadorInformacion()).getSingleResult();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(ESTADO_BLOQUE);

            logger.debug("Finaliza consultarEstadoEspecificoOI(IndicePlanilla) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_LECTURA_ESTADO_PLANILLA.getReadableMessage(e.getMessage());

            logger.error("Finaliza consultarEstadoEspecificoOI(IndicePlanilla) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarEstadoEspecificoOI(IndicePlanilla)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacionInterface#consultarEstadoGeneralOI(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EstadoArchivoPorBloque> consultarEstadoGeneralOI(Long numeroPlanilla, String codOperador) {
        logger.debug("Inicia consultarEstadoGeneralOI(Long, String)");
        List<EstadoArchivoPorBloque> result = null;

        result = (List<EstadoArchivoPorBloque>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_GENERAL)
                .setParameter(ConstantesComunesProcesamientoPILA.ID_PLANILLA, numeroPlanilla).setParameter("codOperador", codOperador)
                .getResultList();

        logger.debug("Finaliza consultarEstadoGeneralOI(Long, String)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#actualizarEstadoOI(com.asopagos.entidades.pila.
     * EstadoArchivoPorBloque)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoOI(EstadoArchivoPorBloque estadoActualizado) {
        logger.debug("Inicia actualizarEstadoOI(EstadoArchivoPorBloque)");

        entityManager.merge(estadoActualizado);

        logger.debug("Finaliza actualizarEstadoOI(EstadoArchivoPorBloque)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#registrarEstadoOF(com.asopagos.entidades.pila.
     * EstadoArchivoPorBloqueOF)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarEstadoOF(EstadoArchivoPorBloqueOF nuevoEstado) {
        logger.debug("Inicia registrarEstadoOF(EstadoArchivoPorBloqueOF)");

        try {
            entityManager.persist(nuevoEstado);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_ACTUALIZACION_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarEstadoOF(EstadoArchivoPorBloqueOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza registrarEstadoOF(EstadoArchivoPorBloqueOF)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#consultarEstadoArchivoOF(com.asopagos.entidades.pila.
     * IndicePlanillaOF)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoArchivoPorBloqueOF consultarEstadoOF(IndicePlanillaOF indicePlanilla) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarEstadoOF(IndicePlanillaOF)");

        EstadoArchivoPorBloqueOF result = null;

        try {
            result = (EstadoArchivoPorBloqueOF) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_ARCHIVO_OF)
                    .setParameter("indicePlanilla", indicePlanilla).getSingleResult();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(ESTADO_BLOQUE);

            logger.debug("Finaliza consultarEstadoOF(IndicePlanillaOF) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_LECTURA_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarEstadoOF(IndicePlanillaOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarEstadoOF(IndicePlanillaOF)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#actualizarEstadoOF(com.asopagos.entidades.pila.
     * EstadoArchivoPorBloqueOF)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoOF(EstadoArchivoPorBloqueOF estadoActualizado) {
        logger.debug("Inicia actualizarEstadoOF(EstadoArchivoPorBloqueOF)");

        try {
            entityManager.merge(estadoActualizado);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_ACTUALIZACION_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza actualizarEstadoOF(EstadoArchivoPorBloqueOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza actualizarEstadoOF(EstadoArchivoPorBloqueOF)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#actualizarEstadoArchivoFRegistro6(java.lang.Long,
     * com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoArchivoFRegistro6(Long registro6Fid, EstadoConciliacionArchivoFEnum estado) {
        logger.debug("Inicia actualizarEstadoArchivoFRegistro6(Long, EstadoConciliacionArchivoFEnum)");

        try {
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_ARCHIVO_F_REGISTRO_6).setParameter("id", registro6Fid)
                    .setParameter("estadoConciliacion", estado).executeUpdate();

        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_ACTUALIZACION_ESTADO_REGISTRO_6.getReadableMessage(e.getMessage());
            logger.error("Finaliza actualizarEstadoArchivoFRegistro6(Long, EstadoConciliacionArchivoFEnum) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza actualizarEstadoArchivoFRegistro6(Long, EstadoConciliacionArchivoFEnum)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#consultarEstadosRegistros6(com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<String> consultarEstadosRegistros6(IndicePlanillaOF indicePlanillaOF) {
        logger.debug("Inicia consultarEstadosRegistros6(IndicePlanillaOF)");

        List<String> result = null;

        try {
            result = (List<String>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADOS_REGISTRO_6_OF)
                    .setParameter("indicePlanillaOF", indicePlanillaOF).getResultList();

        } catch (NoResultException e) {
            // la consulta sin resultados no genera error en este caso
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_LECTURA_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarEstadosRegistros6(IndicePlanillaOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarEstadosRegistros6(IndicePlanillaOF)");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#actualizarEstadoConciliacionOF(com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF,
     *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum, com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarEstadoConciliacionOF(IndicePlanillaOF indicePlanillaOF, EstadoProcesoArchivoEnum estado,
            AccionProcesoArchivoEnum accion) {
        logger.debug("Inicia actualizarEstadoConciliacionOF(IndicePlanillaOF, EstadoProcesoArchivoEnum)");

        EstadoArchivoPorBloqueOF estadoPorBloqueOF = null;

        // se consulta el estado por bloque del archivo OF
        try {
            estadoPorBloqueOF = consultarEstadoOF(indicePlanillaOF);
        } catch (ErrorFuncionalValidacionException e) {
            // no se encuentra un estado por bloque para actualizar, se lanza error técnico
            String mensaje = MensajesGestionEstadosEnum.ERROR_ACTUALIZACION_ESTADO_PLANILLA.getReadableMessage(e.getMessage());
            logger.error("Finaliza actualizarEstadoConciliacionOF(IndicePlanillaOF, EstadoProcesoArchivoEnum) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        if (estadoPorBloqueOF != null) {
            // se prepara la entrada de historial de estado, en el caso de que el bloque haya estado ya procesado antes
            if (estadoPorBloqueOF.getEstadoBloque6() != null) {
                HistorialEstadoBloque historial = new HistorialEstadoBloque();
                historial.setEstado(estadoPorBloqueOF.getEstadoBloque6());
                historial.setAccion(estadoPorBloqueOF.getAccionBloque6());
                historial.setFechaEstado(estadoPorBloqueOF.getFechaBloque6());
                historial.setBloque(BloqueValidacionEnum.BLOQUE_6_OI);
                historial.setIdIndicePlanillaOF(estadoPorBloqueOF.getIndicePlanillaOF().getId());
                
                entityManager.persist(historial);
            }

            estadoPorBloqueOF = entityManager.merge(estadoPorBloqueOF);

            estadoPorBloqueOF.setEstadoBloque6(estado);
            estadoPorBloqueOF.setAccionBloque6(accion);
            estadoPorBloqueOF.setFechaBloque6(new Date());

            estadoPorBloqueOF.getIndicePlanillaOF().setEstado(estado);
        }

        logger.debug("Finaliza actualizarEstadoConciliacionOF(IndicePlanillaOF, EstadoProcesoArchivoEnum)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#registrarHistoricoEstado(com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public void registrarHistoricoEstado(HistorialEstadoBloque historicoEstado) {
        logger.debug("Inicia registrarHistoricoEstado(HistorialEstadoBloque)");

        try {
            entityManager.persist(historicoEstado);
        } catch (Exception e) {
            String mensaje = MensajesGestionEstadosEnum.ERROR_HISTORIAL_ESTADO.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarHistoricoEstado(HistorialEstadoBloque) - " + mensaje, e);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza registrarHistoricoEstado(HistorialEstadoBloque)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#consultarPlanilalsOIDetenidasPorConciliar()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public List<IndicePlanilla> consultarPlanilalsOIDetenidasPorConciliar() {
		logger.debug("Inicia consultarEstadosRegistros6(IndicePlanillaOF)");
		int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
		List<IndicePlanilla> result = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_PLANILLAS_I_DETENIDAS_PENDIENTES_CONCILIACION,
						IndicePlanilla.class).setMaxResults(pagina)
				.getResultList();
		logger.debug("Finaliza consultarEstadosRegistros6(IndicePlanillaOF)");
		return result;
	}

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanilla> consultarPlanilalsOISegunSpReiniciarPlanillas(List<Long> indicesPlanilla) {
        logger.debug("Inicia consultarEstadosRegistros6(IndicePlanillaOF)");
        int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
        List<IndicePlanilla> result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_PLANILLAS_I_SEGUN_SP_REINICIAR_PLANILLAS,
                        IndicePlanilla.class)
                .setParameter("indicesPlanilla", indicesPlanilla)
                .setMaxResults(pagina)
                .getResultList();
        logger.debug("Finaliza consultarEstadosRegistros6(IndicePlanillaOF)");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion#consultarPlanilalsOIDetenidasValorCero()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
	public List<IndicePlanilla> consultarPlanilalsOIDetenidasValorCero() {
		logger.debug("Inicia consultarPlanilalsOIDetenidasValorCero(IndicePlanillaOF)");
		int pagina = Integer.valueOf(CacheManager.getParametro(ConstantesSistemaConstants.PILA_CANTIDAD_ARCHIVO_MASIVO).toString());
		List<IndicePlanilla> result = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_PLANILLAS_I_DETENIDAS_PENDIENTES_VALOR_CERO,
						IndicePlanilla.class).setMaxResults(pagina)
				.getResultList();
		logger.debug("Finaliza consultarPlanilalsOIDetenidasValorCero(IndicePlanillaOF)");
		return result;
	}    
}
