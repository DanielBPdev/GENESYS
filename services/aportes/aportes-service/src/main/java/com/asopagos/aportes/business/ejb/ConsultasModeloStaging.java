package com.asopagos.aportes.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.aportes.business.interfaces.IConsultasModeloStaging;
import com.asopagos.aportes.constants.NamedQueriesConstants;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloStaging implements IConsultasModeloStaging, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloStaging.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "pilaStaging_PU")
    private EntityManager entityManagerStaging;

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#crearRegistroGeneral(com.asopagos.entidades.pila.staging.RegistroGeneral)
     */
    @Override
    public void crearRegistroGeneral(RegistroGeneral registroGeneral) {
        logger.debug("Inicia crearRegistroGeneral(RegistroGeneral) ");
        try {
            entityManagerStaging.persist(registroGeneral);
            logger.info("si se creo el registro general: " + registroGeneral.getId());
        } catch (Exception e) {
            logger.debug("Finaliza crearRegistroGeneral(RegistroGeneral)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#actualizarRegistroGeneral(com.asopagos.entidades.pila.staging.RegistroGeneral)
     */
    @Override
    public void actualizarRegistroGeneral(RegistroGeneral registroGeneral) {
        logger.debug("Inicia actualizarRegistroGeneral(RegistroGeneral) ");
        try {
            entityManagerStaging.merge(registroGeneral);
        } catch (Exception e) {
            logger.debug("Finaliza actualizarRegistroGeneral(RegistroGeneral)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#crearRegistroDetallado(com.asopagos.entidades.pila.staging.RegistroDetallado)
     */
    @Override
    public void crearRegistroDetallado(RegistroDetallado registroDetallado) {
        logger.debug("Inicia crearRegistroDetallado(RegistroDetallado) ");
        try {
            entityManagerStaging.persist(registroDetallado);
        } catch (Exception e) {
            logger.debug("Finaliza crearRegistroDetallado(RegistroDetallado)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#actualizarRegistroDetallado(com.asopagos.entidades.pila.staging.RegistroDetallado)
     */
    @Override
    public void actualizarRegistroDetallado(RegistroDetallado registroDetallado) {
        logger.debug("Inicia actualizarRegistroDetallado(RegistroDetallado) ");
        try {
            entityManagerStaging.merge(registroDetallado);
        } catch (Exception e) {
            logger.debug("Finaliza actualizarRegistroDetallado(RegistroDetallado)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#crearTransaccion(com.asopagos.entidades.pila.staging.Transaccion)
     */
    @Override
    public void crearTransaccion(Transaccion transaccion) {
        logger.debug("Inicia crearTransaccion(Transaccion) ");
        try {
            entityManagerStaging.persist(transaccion);
        } catch (Exception e) {
            logger.debug("Finaliza crearTransaccion(Transaccion)");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneral(java.lang.Long, java.lang.Boolean)
     */
    @Override
    public RegistroGeneral consultarRegistroGeneral(Long idSolicitud, Boolean limitarFinalizados) {
        logger.debug("Inicia consultarRegistroGeneral(Long) ");
        try {
            RegistroGeneral registroGeneral = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID_SOLICITUD, RegistroGeneral.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .setParameter("limitarFinalizados", limitarFinalizados == null || !limitarFinalizados ? 0 : 1).getSingleResult();

            logger.debug("Fin de método consultarRegistroGeneral(Long)");
            return registroGeneral;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarRegistroGeneral: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneral(java.lang.Long, java.lang.Boolean)
     */
    @Override
    public RegistroGeneral consultarRegistroGeneralIdRegGen(Long idSolicitud, Boolean limitarFinalizados, Long idRegistroGeneral) {
        logger.debug("Inicia consultarRegistroGeneral(Long) ");
        try {
            RegistroGeneral registroGeneral = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID_SOLICITUD_ID_REG_GEN, RegistroGeneral.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .setParameter("idRegistroGeneral", idRegistroGeneral)
                    .setParameter("limitarFinalizados", limitarFinalizados == null || !limitarFinalizados ? 0 : 1).getSingleResult();

            logger.debug("Fin de método consultarRegistroGeneral(Long)");
            return registroGeneral;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarRegistroGeneral: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroDetallado(java.lang.Long)
     */
    @Override
    public List<RegistroDetallado> consultarRegistroDetallado(Long idRegistroGeneral) {
        logger.debug("Inicia consultarRegistroDetallado(Long) ");
        try {
            List<RegistroDetallado> registrosDetallados = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_ID_REGISTRO_GENERAL, RegistroDetallado.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

            logger.debug("Fin de método consultarRegistroDetallado(Long)");
            return registrosDetallados;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarRegistroDetallado: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroDetalladoPorTipoAfiliado(java.lang.Long,
     *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @Override
    public List<RegistroDetalladoModeloDTO> consultarRegistroDetalladoPorTipoAfiliado(Long idRegistroGeneral,
            TipoAfiliadoEnum idCotizante, TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistroDetalladoPorTipoAfiliado(Long, TipoAfiliadoEnum, "
                + "TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<RegistroDetalladoModeloDTO> registrosDetallados = null;

        try {
            registrosDetallados = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_ID_REGISTRO_GENERAL_TIPO_AFILIADO,
                            RegistroDetalladoModeloDTO.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).setParameter("tipoAfiliado", idCotizante)
                    .setParameter("tipoId", tipoId).setParameter("numId", numId).getResultList();
        } catch (NoResultException nre) {
            registrosDetallados = Collections.emptyList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return registrosDetallados;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralId(java.lang.Long)
     */
    @Override
    public RegistroGeneral consultarRegistroGeneralId(Long idRegistroGeneral) {
        logger.debug("Inicia consultarRegistroGeneral(Long) ");
        try {
            RegistroGeneral registroGeneral = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID, RegistroGeneral.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();

            logger.debug("Fin de método consultarRegistroGeneral(Long)");
            return registroGeneral;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarRegistroGeneral: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#
     * consultarRegistroDetalladoPorId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(Long idRegistroDetallado) {
        try {
            logger.debug("Inicia método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
            RegistroDetallado registroDetallado = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO, RegistroDetallado.class)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();
            RegistroDetalladoModeloDTO registroDetalladoDTO = new RegistroDetalladoModeloDTO();
            registroDetalladoDTO.convertToDTO(registroDetallado);
            logger.debug("Finaliza método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
            return registroDetalladoDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método consultarRegistroDetalladoPorId(Long idRegistroDetallado)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#
     * consultarNovedadesRechazadasCotizanteAporte(java.lang.Long,
     * java.util.List)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<NovedadCotizanteDTO> consultarNovedadesRechazadasCotizanteAporte(Long idRegistroDetallado,
            List<String> tiposTransaccionNovedadesRechazadas) {
        try {
            logger.debug("Inicia método consultarNovedadesRechazadasCotizanteAporte");
            List<Object[]> listaNovedades = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADESRECHAZADAS_COTIZANTE_APORTE)
                    .setParameter("idRegistroDetallado", idRegistroDetallado)
                    .setParameter("tiposTransaccionNovedadesRechazadas", tiposTransaccionNovedadesRechazadas).getResultList();
            List<NovedadCotizanteDTO> listaNovedadesDTO = new ArrayList<>();

            for (Object[] registro : listaNovedades) {
                NovedadCotizanteDTO novedadCotizanteDTO = new NovedadCotizanteDTO();
                novedadCotizanteDTO.convertToDTO(registro);
                listaNovedadesDTO.add(novedadCotizanteDTO);
            }

            logger.debug("Finaliza método consultarNovedadesRechazadasCotizanteAporte");
            return listaNovedadesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método consultarNovedadesRechazadasCotizanteAporte", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#actualizarEstadoAporteRegistroDetallado(java.util.List)
     */
    @Override
    public void actualizarEstadoAporteRegistroDetallado(List<Long> idCotizantes) {
        try {
            logger.debug("Inicia método actualizarEstadoAporteRegistroDetallado(List<Long> idCotizantes)");
            entityManagerStaging.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_REGISTRO_DETALLADO_NO_OK)
                    .setParameter("idCotizantes", idCotizantes).executeUpdate();
            entityManagerStaging.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_REGISTRO_DETALLADO_NO_VALIDADO)
                    .setParameter("idCotizantes", idCotizantes).executeUpdate();
            logger.debug("Finaliza método actualizarEstadoAporteRegistroDetallado");
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarNovedadesRechazadas(java.util.Date,
     *      java.util.Date,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> consultarNovedadesRechazadas(Date fechaInicio, Date fechaFin, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        try {
            logger.debug("Inicia método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)");
            List<Object[]> novedadesRechazadas = entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_RECHAZADAS)
                    .setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
            logger.debug("Finaliza método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)");
            return novedadesRechazadas;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#actualizarEstadoAporteRegistroGeneral(java.lang.Long)
     */
    @Override
    public void actualizarEstadoAporteRegistroGeneral(List<Long> idRegistroGeneral) {
        try {
            logger.debug("Inicia método actualizarEstadoAporteRegistroDetallado(List<Long> idCotizantes)");
            entityManagerStaging.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_REGISTRO_GENERAL_GESTIONAR_ERROR_VALIDACION_VS_BD)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).executeUpdate();
            logger.debug("Finaliza método actualizarEstadoAporteRegistroDetallado");
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralPorDatosAportante(java.lang.Long,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @Override
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorDatosAportante(Long idRegistroGeneral,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "consultarRegistroGeneralPorDatosAportante(Long idAporteGeneral, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
        try {
            logger.debug("Inicia método " + firmaMetodo);

            RegistroGeneral registroGeneral = (RegistroGeneral) entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_VISTA_360_POR_ID_REGISTRO_GENERAL_APORTE)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
            RegistroGeneralModeloDTO registroGeneralModeloDTO = new RegistroGeneralModeloDTO();
            if (registroGeneral != null) {
                registroGeneralModeloDTO.convertToDTO(registroGeneral);
            }
            logger.debug("Finaliza método " + firmaMetodo);
            return registroGeneralModeloDTO;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistrosNovedadesAplicadas(java.lang.Long)
     */
    @Override
    public List<RecaudoCotizanteDTO> consultarRegistrosNovedadesAplicadas(Long idRegistroGeneral) {
        String firmaMetodo = "consultarRegistrosNovedadesAplicadas(Long idRegistroGeneral)";
        try {
            logger.debug("Inicia método " + firmaMetodo);
            List<RecaudoCotizanteDTO> recaudos = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECAUDO_COTIZANTE_NOVEDADES_PROCESADAS, RecaudoCotizanteDTO.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
            logger.debug("Finaliza método " + firmaMetodo);
            return recaudos;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistrosNovedadesGuardadas(java.lang.Long)
     */
    @Override
    public List<RecaudoCotizanteDTO> consultarRegistrosNovedadesGuardadas(Long idRegistroGeneral) {
        String firmaMetodo = "consultarRegistrosNovedadesGuardadas(Long idRegistroGeneral)";
        try {
            logger.debug("Inicia método " + firmaMetodo);
            List<RecaudoCotizanteDTO> recaudos = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RECAUDO_COTIZANTE_NOVEDADES_NO_PROCESADAS, RecaudoCotizanteDTO.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
            logger.debug("Finaliza método " + firmaMetodo);
            return recaudos;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método actualizarEstadoAporteRegistroDetallado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarNovedadesRechazadasRecientes(java.util.Date,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> consultarNovedadesRechazadasRecientes(Date fechaFin, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        try {
            logger.debug("Inicia método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)");
            List<Object[]> novedadesRechazadas = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_RECHAZADAS_RECIENTES).setParameter("fechaFin", fechaFin)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setMaxResults(10).getResultList();
            logger.debug("Finaliza método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)");
            return novedadesRechazadas;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método consultarNovedadesRechazadas(Date, Date, TipoIdentificacionEnum, String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarNovedadesCotizanteAporte(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idRegistroDetallado) {
        try {
            logger.debug("Inicia método consultarNovedadesCotizanteAporte(Long idRegistroDetallado)");
            List<Object[]> listaNovedades = entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_COTIZANTE)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getResultList();
            List<NovedadCotizanteDTO> listaNovedadesDTO = new ArrayList<>();

            for (Object[] registro : listaNovedades) {
                NovedadCotizanteDTO novedadCotizanteDTO = new NovedadCotizanteDTO();
                novedadCotizanteDTO.convertToDTO(registro);
                listaNovedadesDTO.add(novedadCotizanteDTO);
            }

            logger.debug("Finaliza método consultarNovedadesCotizanteAporte(Long idRegistroDetallado)");
            return listaNovedadesDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método consultarNovedadesCotizanteAporte(Long idRegistroDetallado)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarTipoCotizanteMasReciente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public TipoCotizanteEnum consultarTipoCotizanteMasReciente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String periodoCalculo) {
        String firmaMetodo = "ConsultasModeloStaging.consultarTipoCotizanteMasReciente(TipoIdentificacionEnum, " + "String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoCotizanteEnum result = null;

        try {
            Short tipoCotizante = (Short) entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_COTIZANTE_APORTE_INDEPENDIENTE)
                    .setParameter("sTipoID", tipoIdentificacion.name()).setParameter("sNumID", numeroIdentificacion)
                    .setParameter("sPeriodo", periodoCalculo).getSingleResult();

            if (tipoCotizante != null) {
                result = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante.intValue());
            }
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
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#eliminarRegistrosDetalladosPorRG(java.lang.Long)
     */
    @Override
    public void eliminarRegistrosDetalladosPorRG(Long idRegistroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.eliminarRegistrosDetalladosPorRG(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("Eliminando registros detallados por idRegistroGeneral: " + idRegistroGeneral);
        entityManagerStaging.createNamedQuery(NamedQueriesConstants.ELIMINAR_REGISTRO_DETALLADO_NOVEDAD_POR_REGISTRO_GENERAL)
                .setParameter("idRegistroGeneral", idRegistroGeneral).executeUpdate();
        
        entityManagerStaging.createNamedQuery(NamedQueriesConstants.ELIMINAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL)
                .setParameter("idRegistroGeneral", idRegistroGeneral).executeUpdate();

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarEsPlanillaManual(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarEsPlanillaManual(Long idRegistroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.consultarEsPlanillaManual(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = null;

        try {
            result = entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTA_MARCA_PLANILLA_MANUAL, Boolean.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();
        } catch (NoResultException e) {
            result = Boolean.FALSE;
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
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarPresenciaNovedadesPorRegistroGeneral(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, ConsultaPresenciaNovedadesDTO> consultarPresenciaNovedadesPorRegistroGeneral(List<Long> ids) {
        String firmaMetodo = "ConsultasModeloStaging.consultarPresenciaNovedadesPorRegistroGeneral(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, ConsultaPresenciaNovedadesDTO> result = null;
        List<Object[]> resultQuery = null;

        try {
            resultQuery = entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTA_PRESENCIA_NOVEDADES_APORTE)
                    .setParameter("idRegistroGeneral", ids).getResultList();
        } catch (NoResultException e) {
            resultQuery = Collections.emptyList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        result = new HashMap<>();
        for (Long idRegistro : ids) {
            ConsultaPresenciaNovedadesDTO datoConsulta = new ConsultaPresenciaNovedadesDTO();
            datoConsulta.setIdRegGen(idRegistro);
            datoConsulta.setTieneNovedades(Boolean.FALSE);
            datoConsulta.setTamanioPlanilla(0);
            result.put(idRegistro, datoConsulta);
        }

        for (Object[] registro : resultQuery) {
            Long idRegGen = ((BigInteger) registro[0]).longValue();
            result.get(idRegGen).setTieneNovedades((Integer) registro[1] == 1);
            result.get(idRegGen).setTamanioPlanilla((Integer) registro[2]);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#completarDatosAporteOriginal(com.asopagos.aportes.dto.CuentaAporteDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CuentaAporteDTO completarDatosAporteOriginal(CuentaAporteDTO aporteOriginal) {
        String firmaMetodo = "ConsultasModeloStaging.completarDatosAporteOriginal(CuentaAporteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object[] resultQuery = null;

        try {
            resultQuery = (Object[]) entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTA_DATOS_PLANILLA_APORTE_ORIGINAL)
                    .setParameter("idRegistroDetallado", aporteOriginal.getIdRegistroDetallado()).getSingleResult();

            if (resultQuery != null) {
                aporteOriginal.setNumeroPlanilla(resultQuery[0].toString());
                aporteOriginal.setEstadoArchivo(EstadoProcesoArchivoEnum.valueOf(resultQuery[1].toString()));
                aporteOriginal.setTipoArhivo(TipoArchivoPilaEnum.valueOf(resultQuery[2].toString()));
                aporteOriginal.setTipoPlanilla(TipoPlanillaEnum.obtenerTipoPlanilla(resultQuery[3].toString()));
            }
        } catch (NoResultException e) {
            logger.debug(firmaMetodo + " :: sin datos");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return aporteOriginal;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#completarDatosAporteOriginal(com.asopagos.aportes.dto.CuentaAporteDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CuentaAporteDTO consultarDatosPlanillaAporte(Long registroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.completarDatosAporteOriginal(CuentaAporteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object[] resultQuery = null;
        
        CuentaAporteDTO r = new CuentaAporteDTO();

        try {
            resultQuery = (Object[]) entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTA_DATOS_PLANILLA_APORTE)
                    .setParameter("idRegistroGeneral", registroGeneral).getSingleResult();

            if (resultQuery != null) {               
                r.setRegistroControl(resultQuery[0]!=null?Long.parseLong(resultQuery[0].toString()):null);
                r.setEstadoArchivo(resultQuery[1]!=null?EstadoProcesoArchivoEnum.valueOf(resultQuery[1].toString()):null);               
            }
        } catch (NoResultException e) {
            logger.debug(firmaMetodo + " :: sin datos");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return r;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#eliminarRegistroGeneralId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarRegistroGeneralId(Long idRegistroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.eliminarRegistroGeneralId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerStaging.createNamedQuery(NamedQueriesConstants.ELIMINAR_REGISTRO_GENERAL_ID)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).executeUpdate();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la eliminación: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistrosGeneralesPorListaId(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistrosGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.eliminarRegistroGeneralId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, RegistroGeneralModeloDTO> result = new HashMap<>();

        int max = 1000;
        int count = 0;
        List<Long> idsRegistrosGeneralTMP = new ArrayList<Long>();
        for(Long id : idsRegistrosGeneral) {
        	count++;
        	idsRegistrosGeneralTMP.add(id);
        	
        	if(count == max) {
            	result.putAll(consultarRegistrosGeneralesPorIds(idsRegistrosGeneralTMP));
            	count = 0;
                idsRegistrosGeneralTMP = new ArrayList<Long>();
            }
        }
        
        if(count > 0 ) {
        	result.putAll(consultarRegistrosGeneralesPorIds(idsRegistrosGeneralTMP));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Realiza la consulta por la lista de identificadores de registros generales
     * @param idsRegistrosGeneral
     * @return
     */
    private Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorIds(List<Long> idsRegistrosGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosGeneralesPorIds(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, RegistroGeneralModeloDTO> result = new HashMap<>();

        List<RegistroGeneralModeloDTO> resultQuery = entityManagerStaging
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_LISTADO_ID_NATIVA, RegistroGeneralModeloDTO.class)
                .setParameter("idsRegistrosGeneral", idsRegistrosGeneral).getResultList();

        if (!resultQuery.isEmpty()) {
            for (RegistroGeneralModeloDTO registroGeneral : resultQuery) {
                result.put(registroGeneral.getId(), registroGeneral);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /** (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralPorPlanilla(java.lang.String)
     */
    @Override
    public List<RegistroGeneralModeloDTO> consultarRegistroGeneralPorPlanilla(String numeroPlanilla) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistroGeneralPorPlanilla(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroGeneralModeloDTO> result = null;

        try {
            result = entityManagerStaging
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_NUM_PLANILLA, RegistroGeneralModeloDTO.class)
                    .setParameter("numPlanilla", numeroPlanilla).getResultList();
        } catch (NoResultException e) {
            result = Collections.emptyList();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	/** (non-Javadoc)
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#eliminarControlPlanilla(java.util.List)
	 */
	@Override
	public void eliminarControlPlanilla(List<Long> idsDetalle) {
		String firmaMetodo = "ConsultasModeloStaging.eliminarControlPlanilla(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (!idsDetalle.isEmpty()) {
			List<Long> subList = new ArrayList<>();
			for (Long idRegistro : idsDetalle) {
				subList.add(idRegistro);
				if (subList.size() == 1000) {
					entityManagerStaging.createNamedQuery(NamedQueriesConstants.DELETE_CONTROL_EJECUCION_PILA)
							.setParameter("idsRegistroGeneral", subList).executeUpdate();
					subList.clear();
				}
			}

			if (!subList.isEmpty()) {
				entityManagerStaging.createNamedQuery(NamedQueriesConstants.DELETE_CONTROL_EJECUCION_PILA)
						.setParameter("idsRegistroGeneral", subList).executeUpdate();
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

    
}
