package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.pila.parametrizacion.NormatividadFechaVencimiento;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContexto;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase encargada de la implementación de la persistencia de datos relacionados a la preparación del contexto para
 * validación<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class PersistenciaPreparacionContexto implements Serializable, IPersistenciaPreparacionContexto {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "archivosPila_PU")
    private EntityManager entityManager;

    /**
     * Referencia a la unidad de persistencia de core
     */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PersistenciaPreparacionContexto.class);
    
    /** Constante que contiene el mensaje para días festivos */
    private static final String DIAS_FESTIVOS = "días festivos";
    /** Constante que contiene el mensaje para normatividad aplicable para determinar la fecha de vencimiento */
    private static final String NORMATIVIDAD_VENCIMIENTO = "normatividad aplicable para determinar la fecha de vencimiento";
    /** Constante que contiene el mensaje para normatividad de tasa de interés por período */
    private static final String TASA_INTERES = "normatividad de tasa de interés por período";

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContextoInterface#consultarVariablesBloquePlanilla(java.lang.Long,
     * com.asopagos.enumeraciones.pila.BloqueValidacionEnum)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PasoValores> consultarVariablesBloquePlanilla(Long numeroPlanilla, String codOperador, BloqueValidacionEnum bloque) {
        logger.debug("Inicia consultarVariablesBloquePlanilla(Long, String, BloqueValidacionEnum)");

        //se consulta de las variables de paso de un bloque específico de la BD
        List<PasoValores> result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLES_DE_PASO)
                .setParameter("idPlanilla", numeroPlanilla).setParameter("codOperador", codOperador).setParameter("bloque", bloque)
                .getResultList();

        logger.debug("Finaliza consultarVariablesBloquePlanilla(Long, String, BloqueValidacionEnum)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContextoInterface#consultarFestivos()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DiasFestivos> consultarFestivos() {
        logger.debug("Inicia consultarFestivos()");
        List<DiasFestivos> result = new ArrayList<>();

        try {
            //se listan los dias festivos
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIAS_FESTIVOS).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(DIAS_FESTIVOS);

            logger.debug("Finaliza consultarFestivos() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());

            logger.error("Finaliza consultarFestivos() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarFestivos()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContextoInterface#consultarNormatividadVencimiento()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<NormatividadFechaVencimiento> consultarNormatividadVencimiento() {
        logger.debug("Inicia consultarNormatividadVencimiento()");
        List<NormatividadFechaVencimiento> result = null;

        try {
            //se lista la normatividad de fecha vencimiento
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NORMATIVIDAD_FECHA_VENCIMIENTO).getResultList();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(NORMATIVIDAD_VENCIMIENTO);

            logger.debug("Finaliza consultarNormatividadVencimiento() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());

            logger.error("Finaliza consultarNormatividadVencimiento() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarNormatividadVencimiento()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContextoInterface#consultarOportunidadVencimiento()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<NormatividadFechaVencimiento> consultarOportunidadVencimiento() {
        logger.debug("Inicia consultarOportunidadVencimiento()");
        List<NormatividadFechaVencimiento> result = null;

        try {
            //se lista las oportunidades de presentacion de planillas pila
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPORTUNIDAD_PRESENTACION).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(NORMATIVIDAD_VENCIMIENTO);

            logger.debug("Finaliza consultarOportunidadVencimiento() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());

            logger.error("Finaliza consultarOportunidadVencimiento() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarOportunidadVencimiento()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContextoInterface#consultarTasasInteres()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TasasInteresMora> consultarTasasInteres() {
        logger.debug("Inicia consultarTasasInteres()");
        List<TasasInteresMora> result = null;

        try {
            //se lista el periodo de intereses
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_INTERES).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(TASA_INTERES);

            logger.debug("Finaliza consultarTasasInteres() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());

            logger.error("Finaliza consultarTasasInteres() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarTasasInteres()");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContexto#consultarTasasInteresPorRango(java.util.Date,
     *      java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TasasInteresMora> consultarTasasInteresPorRango(Date fechaInicial, Date fechaFinal) {
        String firmaMetodo = "PersistenciaPreparacionContexto.consultarTasasInteresPorRango(Date, Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TasasInteresMora> result = null;

        try {
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_INTERES_RANGO)
                    .setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fechaFinal).getResultList();
            
        } catch (Exception e) {
            String mensaje = MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + " :: "
                    + MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(TASA_INTERES);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
}
