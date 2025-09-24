package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.AportePeriodoCertificadoDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoNovedadModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.pila.staging.PlanillaCandidataReproceso;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroDetalladoNovedad;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IConsultasModeloStaging;
import com.asopagos.pila.constants.ConstantesParaMensajes;
import com.asopagos.pila.constants.MensajesErrorPersistenciaEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.ConsultaNovedadesPorRegistroDTO;
import com.asopagos.pila.dto.DetalleAporteVista360DTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;
import com.asopagos.pila.dto.HistoricoNovedades360DTO;
import com.asopagos.pila.dto.InformacionAporte360DTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.ExcelUtils;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos de Staging <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class ConsultasModeloStaging implements IConsultasModeloStaging, Serializable {
    private static final long serialVersionUID = 5996518508973585711L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloStaging.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "pilaStaging_PU")
    private EntityManager entityManager;

    /** Variable de mensaje de error */
    private String mensaje;

    /** ----------------------------------------- INICIO METODOS HU-211-401 --------------------------------------------- */

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#obtenerIdRegistroGeneral(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long obtenerIdRegistroGeneral(Long idPlanilla) {
        Long result = null;
        try {
            result = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_ID_REGISTRO_GENERAL, Long.class)
                    .setParameter("idPlanilla", idPlanilla).getSingleResult();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#obtenerRegistroDetallado(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetallado> obtenerRegistroDetallado(Long idRegistroGeneral) {
        logger.debug("inicia obtenerRegistroDetallado");
        List<RegistroDetallado> result = new ArrayList<>();
        try {
            result = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_REGISTRO_DETALLADO, RegistroDetallado.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
            return result;
        } catch (NoResultException nre) {
            logger.debug("no se obtuvieron resultados");
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#obtenerParametrosIndependientes()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String[] obtenerParametrosIndependientes() {
        logger.debug("Inicia obtenerParametrosIndependientes");
        try {
            String respuesta = (String) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PARAMETROS_INDEPENDIENTE)
                    .getSingleResult();
            return respuesta.split(",");
        } catch (Exception e) {
            logger.error("No fue posible obtener los datos");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarEstadoGeneralPlanilla(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RespuestaConsultaEmpleadorDTO consultarEstadoGeneralPlanilla(Long idIndicePlanilla) {
        String firmaMetodo = "ConsultasModeloStaging.consultarEstadoGeneralPlanilla(Long)";
        logger.debug("Inicia " + firmaMetodo);

        RespuestaConsultaEmpleadorDTO result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_GENERAL_IIP, RespuestaConsultaEmpleadorDTO.class)
                .setParameter("idIndicePlanilla", idIndicePlanilla).getSingleResult();

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarEstadoDetalladoPlanilla(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetalleTablaAportanteDTO> consultarEstadoDetalladoPlanilla(Long idIndicePlanilla) {
        String firmaMetodo = "ConsultasModeloStaging.consultarEstadoDetalladoPlanilla(Long)";
        logger.debug("Inicia " + firmaMetodo);

        List<DetalleTablaAportanteDTO> result = new ArrayList<DetalleTablaAportanteDTO>();

        try {
            result = (List<DetalleTablaAportanteDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_DETALLADO)
                    .setParameter("idIndicePlanilla", idIndicePlanilla).getResultList();
        } catch (NoResultException e) {
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistrosDetalladosPorIdPlanilla(java.lang.Long,
     *      java.lang.Boolean, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetalladoModeloDTO> consultarRegistrosDetalladosPorIdPlanilla(Long idPlanilla, Boolean ignorarA, 
            Boolean usarInicial, Boolean discriminar) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosDetalladosPorNumeroPlanilla(Long, Boolean, Boolean, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<RegistroDetalladoModeloDTO> result = null;
        
        // se consultan los registros de acuerdo a su situación actual
        List<RegistroDetalladoModeloDTO> resultActual = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_DETALLADO_COMPLETO, RegistroDetalladoModeloDTO.class)
                .setParameter("idPlanilla", idPlanilla).setParameter("ignorarA", ignorarA).setParameter("usarInicial", Boolean.FALSE)
                .setParameter("discriminar", discriminar).getResultList();
        
        // sí no se requiere de la información original, se retornan los datos tal cual
        if(!usarInicial){
            result = resultActual;
        }
        else {
            /*
             * en caso de requerir información histórica con ID actual, se lista en mapa una relación de id
             * de los registros actuales vs el original
             */
            Map<Long, Long> idsOriginales = new HashMap<>();
            for (RegistroDetalladoModeloDTO registro : resultActual){
                if(registro.getOutRegInicial() != null && !idsOriginales.containsKey(registro.getOutRegInicial())){
                    idsOriginales.put(registro.getOutRegInicial(), registro.getId());
                }
            }
            
            // se limpia el arreglo de registros actuales para economía de recursos
            resultActual.clear();
            
            // se consultan los registros históricos
            result = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_DETALLADO_COMPLETO, RegistroDetalladoModeloDTO.class)
                    .setParameter("idPlanilla", idPlanilla).setParameter("ignorarA", ignorarA).setParameter("usarInicial", Boolean.TRUE)
                    .setParameter("discriminar", discriminar).getResultList();
            
            // se recorren los registros históricos para actualizar los IDs que lo requieran
            for (RegistroDetalladoModeloDTO registro : result) {
                if(idsOriginales.containsKey(registro.getId())){
                    registro.setId(idsOriginales.get(registro.getId()));
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#actualizarRegistroDetallado(com.asopagos.entidades.pila.staging.RegistroDetallado)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarRegistroDetallado(RegistroDetallado registroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.actualizarRegistroDetallado(Long)";
        logger.debug("Inicia " + firmaMetodo);

        entityManager.merge(registroDetallado);

        logger.debug("Finaliza " + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneral(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroGeneral consultarRegistroGeneral(Long idRegistroGeneral) {
        logger.debug("Inicia ConsultasModeloStaging.consultarRegistroGeneral(Long) con idRegistroGeneral = " + idRegistroGeneral);
        RegistroGeneral registroGeneral = null;
        try {
            registroGeneral = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID, RegistroGeneral.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();
        } catch (NoResultException nre) {
            logger.error(
                    "ConsultasModeloStaging.consultarRegistroGeneral(InconsistenciaRegistroAporteDTO) :: No se pudo obtener el registro general de procesamiento de planilla :: "
                            + nre.getMessage());
        } catch (Exception e) {
            logger.error(
                    "ConsultasModeloStaging.consultarRegistroGeneral(InconsistenciaRegistroAporteDTO) :: No se pudo obtener el registro general de procesamiento de planilla :: "
                            + e.getMessage());
        }
        logger.debug("Finaliza ConsultasModeloStaging.consultarRegistroGeneral(Long)");
        return registroGeneral;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#actualizaRegistroGeneral(com.asopagos.entidades.pila.staging.RegistroGeneral)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RegistroGeneral actualizaRegistroGeneral(RegistroGeneral registroGeneral) {
        logger.debug("Inicia ConsultasModeloStaging.actualizaRegistroGeneral( RegistroGeneral: "+registroGeneral.getId()+" )");
        registroGeneral = entityManager.merge(registroGeneral);
        registroGeneral.setFechaActualizacion(Calendar.getInstance().getTime());
        logger.debug("Finalizar ConsultasModeloStaging.actualizaRegistroGeneral( RegistroGeneral: "+registroGeneral.getId()+" )");
        return registroGeneral;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistroDetallado(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroDetallado consultarRegistroDetallado(Long idRegistroDetallado) {
        logger.debug("Inicia ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum)");
        try {
            RegistroDetallado rd = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_ID, RegistroDetallado.class)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();
            logger.debug("Finaliza ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum)");
            return rd;
        } catch (NoResultException nre) {
            logger.debug("Finaliza ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum)");
            return null;
        } catch (NonUniqueResultException nure) {
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    nure.getMessage());
            logger.error("Finaliza ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum) :: " + mensaje);
            throw new TechnicalException(mensaje);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#actualizaRegistroDetallado(com.asopagos.entidades.pila.staging.RegistroDetallado)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizaRegistroDetallado(RegistroDetallado registroDetallado) {
        logger.debug("Inicia ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum)");
        registroDetallado = entityManager.merge(registroDetallado);
        logger.debug("Finaliza ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#obtenerRegistroDetalladoEspecifico(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroDetalladoModeloDTO obtenerRegistroDetalladoEspecifico(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.obtenerRegistroDetalladoEspecifico(Long)";
        logger.debug("Inicia " + firmaMetodo);

        RegistroDetalladoModeloDTO result = null;

        try {
            RegistroDetallado resultEntity = (RegistroDetallado) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_ID)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();

            result = new RegistroDetalladoModeloDTO();
            result.convertToDTO(resultEntity);
        } catch (NoResultException e) {
            // se retorna el DTO nulo
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * @param idRegistroDetallado
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroDetallado obtenerEntidadRegistroDetalladoEspecifico(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.obtenerEntidadRegistroDetalladoEspecifico(Long)";
        logger.debug("Inicia " + firmaMetodo);

        RegistroDetallado result = null;

        try {
            RegistroDetallado resultEntity = (RegistroDetallado) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_ID)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();

            result = resultEntity;
        } catch (NoResultException e) {
            // se retorna el DTO nulo
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralPorNumeroPlanilla(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroGeneral consultarRegistroGeneralPorNumeroPlanilla(String numeroPlanilla) {
        logger.debug("Inicia ConsultasModeloStaging.consultarRegistroGeneralPorNumeroPlanilla(String)");
        RegistroGeneral registroGeneral;
        try {
            registroGeneral = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_NUMERO_PLANILLA, RegistroGeneral.class)
                    .setParameter("numeroPlanilla", numeroPlanilla).getSingleResult();
            logger.debug("Finaliza ConsultasModeloStaging.consultarRegistroGeneralPorNumeroPlanilla(String)");
            return registroGeneral;
        } catch (NoResultException nre) {
            logger.debug("Finaliza ConsultasModeloStaging.consultarRegistroGeneralPorNumeroPlanilla(String): No existe el registro");
            return null;
        } catch (NonUniqueResultException e) {
            logger.error(
                    "ConsultasModeloStaging.consultarRegistroGeneralPorNumeroPlanilla(String) :: Error al consultar el registro general se encuentra más de un registro :: "
                            + e.getMessage());
            throw new TechnicalException("Error al consultar el registro general", e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistrosDetalladosPorRegistroGeneral(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetalladoModeloDTO> consultarRegistrosDetalladosPorRegistroGeneral(Long idRegistroGeneral) {
    	String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosDetalladosPorRegistroGeneral(Long)";
        logger.debug("Inicia " + firmaMetodo);
        try {
            List<RegistroDetallado> resultEntity = (List<RegistroDetallado>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

            List<RegistroDetalladoModeloDTO> result = new ArrayList<RegistroDetalladoModeloDTO>();
            
            resultEntity.parallelStream().forEachOrdered(registroDetallado -> {
            	RegistroDetalladoModeloDTO resultado = new RegistroDetalladoModeloDTO();
                resultado.convertToDTO(registroDetallado);
                result.add(resultado);
            });
            
            logger.debug("Finaliza " + firmaMetodo);
            return result;
            
        } catch (NoResultException e) {
            // se retorna el listado nulo
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

//        String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosDetalladosPorRegistroGeneral(Long)";
//        logger.debug("Inicia " + firmaMetodo);
//
//        List<RegistroDetalladoModeloDTO> result = null;
//
//        try {
//            List<RegistroDetallado> resultEntity = (List<RegistroDetallado>) entityManager
//                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL)
//                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
//
//            result = new ArrayList<RegistroDetalladoModeloDTO>();
//            for (RegistroDetallado registroDetallado : resultEntity) {
//                RegistroDetalladoModeloDTO resultado = new RegistroDetalladoModeloDTO();
//                resultado.convertToDTO(registroDetallado);
//                result.add(resultado);
//            }
//        } catch (NoResultException e) {
//            // se retorna el listado nulo
//            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
//            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
//            return null;
//        } catch (Exception e) {
//            // se lanza la excepción técnica
//            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
//                    e.getMessage());
//
//            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
//            throw new TechnicalException(mensaje);
//        }
//
//        logger.debug("Finaliza " + firmaMetodo);
//        return result;
    }

    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistrosDetalladosPorRegistroGeneral(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetallado> consultarInfoBasicaRegistrosDetalladosPorRegistroGeneral(Long idRegistroGeneral) {
    	String firmaMetodo = "ConsultasModeloStaging.consultarInfoBasicaRegistrosDetalladosPorRegistroGeneral(Long)";
        long a = System.nanoTime();
    	logger.debug("Inicia " + firmaMetodo);
        try {
        	
        	List<RegistroDetallado> registros = (List<RegistroDetallado>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

            logger.debug("Finaliza " + firmaMetodo + " :  TARDÓ " + CalendarUtils.calcularTiempoEjecucion(a, System.nanoTime()) + " SEGUNDOS : obtuvo " + registros.size() + " registros");
            return registros;
            
        } catch (NoResultException e) {
            // se retorna el listado nulo
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }
    }
    
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarNovedadesRegistroDetallado(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RegistroDetalladoNovedadModeloDTO> consultarNovedadesRegistroDetallado(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.consultarNovedadesRegistroDetallado(Long)";
        logger.debug("Inicia " + firmaMetodo);

        List<RegistroDetalladoNovedadModeloDTO> result = new ArrayList<RegistroDetalladoNovedadModeloDTO>();

        try {
            List<RegistroDetalladoNovedad> resultEntity = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_REGISTRO_DETALLADO)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getResultList();

            if (resultEntity != null && !resultEntity.isEmpty()) {
                for (RegistroDetalladoNovedad registroDetalladoNovedad : resultEntity) {
                    RegistroDetalladoNovedadModeloDTO dto = new RegistroDetalladoNovedadModeloDTO();
                    dto.converToDTO(registroDetalladoNovedad);

                    result.add(dto);
                }
            }
        } catch (NoResultException e) {
            // se retorna el listado nulo
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO_NOVEDAD);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return null;
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO_NOVEDAD,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#actualizarNovedadesDetalladas(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Deprecated
    public void actualizarNovedadesDetalladas(List<RegistroDetalladoNovedadModeloDTO> novedadesDetalladas) {
        String firmaMetodo = "ConsultasModeloStaging.actualizarNovedadesDetalladas(List<RegistroDetalladoNovedadModeloDTO>)";
        logger.debug("Inicia " + firmaMetodo);

        if (novedadesDetalladas != null && !novedadesDetalladas.isEmpty()) {
            for (RegistroDetalladoNovedadModeloDTO registroDetalladoNovedadModeloDTO : novedadesDetalladas) {
                RegistroDetalladoNovedad novedadDetallada = registroDetalladoNovedadModeloDTO.convertToEntity();
                novedadDetallada = entityManager.merge(novedadDetallada);
            }
        }
        
        logger.debug("Finaliza " + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarCantidadNovedadesPorRegistroDetallado(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultaNovedadesPorRegistroDTO> consultarCantidadNovedadesPorRegistroDetallado(Long idRegistroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.consultarCantidadNovedadesPorRegistroDetallado(Long)";
        logger.debug("Inicia " + firmaMetodo);

        List<ConsultaNovedadesPorRegistroDTO> respuesta = null;

        try {
            respuesta = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_NOVEDADES)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();
        } catch (NoResultException e) {
            // se retorna el listado vacío
            mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO_NOVEDAD);
            logger.debug("Finaliza " + firmaMetodo + " :: " + mensaje);
            return new ArrayList<>();
        } catch (Exception e) {
            // se lanza la excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.RESULTADO_DETALLADO_NOVEDAD,
                    e.getMessage());

            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#marcarRegistrosSinNovedades(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void marcarRegistrosSinNovedades(List<Long> registrosSinNovedad) {
        String firmaMetodo = "ConsultasModeloStaging.marcarRegistrosSinNovedades(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        entityManager.createNamedQuery(NamedQueriesConstants.MARCAR_REGISTRADOS_SIN_NOVEDAD)
                .setParameter("registrosSinNovedad", registrosSinNovedad).executeUpdate();

        logger.debug("Finaliza " + firmaMetodo);
    }

    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralDTO(java.lang.Long)
     */
    @Override
    public RegistroGeneralModeloDTO consultarRegistroGeneralDTO(Long idRegistroGeneral) {
        String firmaMetodo = "consultarRegistroGeneralDTO(Long)";
        logger.debug("Inicia " + firmaMetodo);
        
        RegistroGeneralModeloDTO result = null;
        
        RegistroGeneral resultEntity = consultarRegistroGeneral(idRegistroGeneral);
        
        if(resultEntity != null){
            result = new RegistroGeneralModeloDTO();
            result.convertToDTO(resultEntity);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarNovedadesRegistroDetallado(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, List<DetallePestanaNovedadesDTO>> consultarNovedadReintegroAplicadaRegistroDetallado(List<Long> idsRegDet) {
        String firmaMetodo = "ConsultasModeloStaging.consultarNovedadesRegistroDetallado(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, List<DetallePestanaNovedadesDTO>> result = new HashMap<>();
        try {
            // se consultan los registros en paquetes de 1000 registros
            Integer inicio = 0;
            Integer fin = idsRegDet.size() > 1000 ? 1000 : idsRegDet.size();
            while (fin <= idsRegDet.size()) {
                List<Long> idsRegDetTemp = idsRegDet.subList(inicio, fin);

                List<DetallePestanaNovedadesDTO> resultEntity = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_REINTEGRO_PILA)
                        .setParameter("tipoNovedad", TipoNovedadPilaEnum.NOVEDAD_ING.name())
                        .setParameter("idRegistroDetallado", idsRegDetTemp).getResultList();

                //Este es el ajuste de la mantis 264434, pero se comenta debido a la mantis 265948
                //resultEntity.addAll(entityManager
                //        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_REINTEGRO_PILA)
                //        .setParameter("tipoNovedad", TipoNovedadPilaEnum.NOVEDAD_VAC_LR.name())
                //        .setParameter("idRegistroDetallado", idsRegDetTemp).getResultList());
                //
                for (DetallePestanaNovedadesDTO novedad : resultEntity) {
                    List<DetallePestanaNovedadesDTO> listado = result.get(novedad.getIdRegDet());
                    
                    if(listado == null){
                        listado = new ArrayList<>();
                        result.put(novedad.getIdRegDet(), listado);
                    }
                    
                    listado.add(novedad);
                }
                
                inicio = fin;
                fin++;
                if (fin <= idsRegDet.size()) {
                    fin = fin + 1000 <= idsRegDet.size() ? inicio + 1000 : idsRegDet.size();
                }
            }
        } catch (NoResultException e) {
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**-----Inicio métodos para vistas 360---------------------*/
    /* (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarDetalleAporte(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DetalleAporteVista360DTO consultarDetalleAporte(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloStaging.consultarDetalleAporte(Long)";
        logger.debug("Inicia " + firmaMetodo);

        try {
            DetalleAporteVista360DTO detalleAporte = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_APORTE_360, DetalleAporteVista360DTO.class)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();

//            List<HistoricoMovimientoAporte360DTO> historicoMovimientosAporte = new ArrayList<>();

            List<HistoricoNovedades360DTO> historicoNovedades = (List<HistoricoNovedades360DTO>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_APORTE, HistoricoNovedades360DTO.class)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getResultList();

            InformacionAporte360DTO infoAporte = (InformacionAporte360DTO) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_APORTE)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();

//            detalleAporte.setHistoricoMovimientosAporte(historicoMovimientosAporte);
            detalleAporte.setHistoricoNovedades(historicoNovedades);
            detalleAporte.setInformacionAporte(infoAporte);
            logger.debug("Finaliza " + firmaMetodo);

            return detalleAporte;
        } catch (Exception e) {
            logger.error("Finaliza con error " + firmaMetodo);
            throw new TechnicalException(e);
        }
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public List<AportePeriodoCertificadoDTO> consultarAportePeriodo(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short anio) {
		String firmaMetodo = "ConsultasModeloStaging.consultarDetalleAporte(Long)";
		logger.debug("Inicia " + firmaMetodo);
		List<AportePeriodoCertificadoDTO> aporteList = new ArrayList<>();
		AportePeriodoCertificadoDTO aporte=null;
		try {
			List<Object[]> consultaAportes = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFORMACION_APORTE_POR_ANIO)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", tipoIdentificacion.name())
					.setParameter("anio", anio).getResultList();

			logger.debug("Finaliza " + firmaMetodo);
			for (Object[] objects : consultaAportes) {
				aporte = new AportePeriodoCertificadoDTO();
				aporte.setFechaRecaudo(objects[0].toString());
				aporte.setNumeroPlanilla(objects[1]!=null ? objects[1].toString():"");
				aporte.setValorTotalAporte(objects[2]!=null ? objects[2].toString():"");
				aporte.setPeriodoAporte(objects[3]!=null ? objects[3].toString():"");
                aporte.setValorInteres(objects[4]!=null ? objects[4].toString():"");//agregado a DTO AportePeriodoCertificadoDTO
				aporte.setTipoAfiliacion(objects[5]!=null ? objects[5].toString():"");
                logger.info("Finaliza aportesssssss " + aporte);
				aporteList.add(aporte);
			}
			return aporteList;
		} catch (Exception e) {
			logger.error("Finaliza con error " + firmaMetodo);
			throw new TechnicalException(e);
		}
	}
    /**-----Fin métodos para vistas 360------------------------*/

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#
	 * consultarRegistrosPlanillasParaAgrupar(java.lang.Long,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RegistroDetalladoModeloDTO> consultarRegistrosPlanillasParaAgrupar(Long idIndicePlanilla,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
    	String firmaMetodo = "ConsultasModeloStaging.consultarPlanillasParaAgrupar(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		
		List<RegistroDetalladoModeloDTO> result;
		if (tipoIdentificacion != null && numeroIdentificacion != null) {
			result = (List<RegistroDetalladoModeloDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_APORTES_PLANILLA_N,
							RegistroDetalladoModeloDTO.class)
					.setParameter("idIndicePlanilla", idIndicePlanilla)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		} else {
			result = (List<RegistroDetalladoModeloDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_A_APORTES_PLANILLA_N,
							RegistroDetalladoModeloDTO.class)
					.setParameter("idIndicePlanilla", idIndicePlanilla).getResultList();
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
    }
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging#
	 * consultarRegistrosPlanillasParaAgrupar(java.lang.Long,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public byte[] consultarAgrupaciones(Long idIndicePlanilla) {
    	String firmaMetodo = "ConsultasModeloStaging.consultarPlanillasParaAgrupar(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		
		List<RegistroDetalladoModeloDTO> result;
		
			result = (List<RegistroDetalladoModeloDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_AGRUPACIONES,
							RegistroDetalladoModeloDTO.class)
					.setParameter("idIndicePlanilla", idIndicePlanilla)
					.getResultList();
		
		ArrayList<String[]> listaResult = new ArrayList<String[]>();
		for(RegistroDetalladoModeloDTO r:result){
			String[] linea = new String[23];
			linea[0] = r.getId().toString();
			linea[1] = r.getGrupoAC()==null?null:r.getGrupoAC().toString();
			linea[2] = r.getTipoIdentificacionCotizante()==null?null:r.getTipoIdentificacionCotizante().toString();
			linea[3] = r.getNumeroIdentificacionCotizante();
			linea[4] = r.getPrimerNombre();
			linea[5] = r.getSegundoNombre();
			linea[6] = r.getPrimerApellido();
			linea[7] = r.getSegundoApellido();
			linea[8] = r.getTipoCotizante()==null?null:r.getTipoCotizante().toString();
			linea[9] = r.getAporteObligatorio()==null?null:r.getAporteObligatorio().toString();
			linea[10] = r.getDiasCotizados()==null?null:r.getDiasCotizados().toString();
			linea[11] = r.getTarifa()==null?null:r.getTarifa().toString();
			linea[12] = r.getNovIngreso();
			linea[13] = r.getNovRetiro();
			linea[14] = r.getNovVSP();
			linea[15] = r.getNovVST();
			linea[16] = r.getNovSLN();
			linea[17] = r.getNovIGE();
			linea[18] = r.getNovLMA();
			linea[19] = r.getNovVACLR();
			linea[20] = r.getDiasIRL();
			linea[21] = r.getCorrecciones();
			linea[22] = r.getRegistroControl()==null?null:r.getRegistroControl().toString();
			listaResult.add(linea);
		}
		
		ExcelUtils.generarArchivoCSV(null,listaResult,",");
			
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return ExcelUtils.generarArchivoCSV(null,listaResult,",");
    }
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#gestionarRegistrosPlanillasParaAgrupar(java.util.List, java.lang.Boolean)
	 */
	@Override
	public void gestionarRegistrosPlanillasParaAgrupar(List<Long> idsRegistrosDetallados, Boolean agrupar, Long idIndicePlanilla) {
		String firmaMetodo = "ConsultasModeloStaging.gestionarRegistrosPlanillasParaAgrupar(List<Long>, Integer)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		if (Boolean.TRUE.equals(agrupar)) {
			entityManager.createNamedQuery(NamedQueriesConstants.AGRUPAR_REGISTROS_APORTES_PLANILLA_N)
					.setParameter("idsRegistrosDetallados", idsRegistrosDetallados)
					.setParameter("idIndicePlanilla", idIndicePlanilla).executeUpdate();
		} else {
			entityManager.createNamedQuery(NamedQueriesConstants.DESAGRUPAR_REGISTROS_APORTES_PLANILLA_N)
					.setParameter("idsRegistrosDetallados", idsRegistrosDetallados).executeUpdate();
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
	
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#agruparAutomaticamentePlanillaN(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void agruparAutomaticamentePlanillaN(Long idIndicePlanilla) {
        String firmaMetodo = "ConsultasModeloStaging.agruparAutomaticamentePlanillaN(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
		StoredProcedureQuery storedProcedure = entityManager
	            .createStoredProcedureQuery(NamedQueriesConstants.USP_AGRUPAR_AUTOMATICAMENTE_REGISTROS_PLANILLA_N);
	    storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
	    storedProcedure.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
	    storedProcedure.execute();
	    
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarRegistroGeneralPorIdPlanilla(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorIdPlanilla(Long idIndicePlanilla){
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistroGeneralPorIdPlanilla(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        RegistroGeneralModeloDTO result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTRO_GENERAL_POR_ID_PLANILLA, 
                RegistroGeneralModeloDTO.class).setParameter("idIndicePlanilla", idIndicePlanilla).getSingleResult();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarIdsRegistrosDetalladosPorIdPlanilla(java.util.List,
     *      java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, List<Long>> consultarIdsRegistrosDetalladosPorIdPlanilla(List<Long> idsPlanilla, Boolean ignorarA) {
        String firmaMetodo = "ConsultasModeloStaging.consultarIdsRegistrosDetalladosPorIdPlanilla(List<Long>, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, List<Long>> result = new HashMap<>();
        
        if (idsPlanilla != null && !idsPlanilla.isEmpty()){
            List<Object[]> resultQuery = null;
            
            List<Long> idsPlanillaTemp = new ArrayList<>();
            
            // se consulta la información en paquetes de 2000 IDs de planilla
            for(Long idPlanilla : idsPlanilla){
                idsPlanillaTemp.add(idPlanilla);
                if(idsPlanillaTemp.size() == 2000){
                    resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_DETALLADO_IDS)
                            .setParameter("idsPlanilla", idsPlanillaTemp).setParameter("ignorarA", ignorarA).getResultList();
                    
                    idsPlanillaTemp.clear();
                }
            }
            
            if(!idsPlanillaTemp.isEmpty()){
                resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_DETALLADO_IDS)
                        .setParameter("idsPlanilla", idsPlanillaTemp).setParameter("ignorarA", ignorarA).getResultList();
            }
            
            for(Object[] registro : resultQuery){
                Long idPlanilla = (Long) registro[0];
                Long idRegDet = (Long) registro[1];
                
                List<Long> idsRegistros = result.get(idPlanilla);
                
                if(idsRegistros == null){
                    idsRegistros = new ArrayList<>();
                    result.put(idPlanilla, idsRegistros);
                }
                
                idsRegistros.add(idRegDet);
            }
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#marcarRegistrosDetalladosAprobados(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void marcarRegistrosDetalladosAprobados(List<Long> idsRegistrosPorAprobar) {
        String firmaMetodo = "ConsultasModeloStaging.consultarIdsRegistrosDetalladosPorIdPlanilla(List<Long>, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<Long> subgrupo = new ArrayList<>();
        for (Long idRegistro : idsRegistrosPorAprobar){
            subgrupo.add(idRegistro);
            if(subgrupo.size() == 2000){
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_APROBACION_REGISTROS_DETALLADOS)
                        .setParameter("idsRegistro", subgrupo).executeUpdate();
                
                subgrupo.clear();
            }
        }
        
        if(!subgrupo.isEmpty()){
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_APROBACION_REGISTROS_DETALLADOS)
                    .setParameter("idsRegistro", subgrupo).executeUpdate();
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#limpiarAgrupacionesPlanillaCorreccion(java.lang.Long)
	 */
	@Override
	public void limpiarAgrupacionesPlanillaCorreccion(Long idPlanilla) {
        String firmaMetodo = "ConsultasModeloStaging.limpiarAgrupacionesPlanillaCorreccion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
		entityManager.createNamedQuery(NamedQueriesConstants.LIMPIAR_AGRUPACION_ARCHIVO)
				.setParameter("idPlanilla", idPlanilla).executeUpdate();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarIdRegistroGeneralPorPlanilla(java.lang.String)
	 */
	@Override
	public Long consultarIdRegistroGeneralPorPlanilla(String numeroPlanilla) {
		Long salida = null;
		try {
			BigInteger idRegistroGeneral = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_ID_REGISTRO_GENERAL_POR_NUMERO_PLANILLA)
					.setParameter("numeroPlanilla", numeroPlanilla)
					.getSingleResult();
			
		salida = idRegistroGeneral.longValue();
			
		} catch (NoResultException nre) {
            logger.debug("Finaliza consultarIdRegistroGeneralPorPlanilla(Long) :: No se encontraró un registro general asociado a la planilla");
            return null;
        } catch (NonUniqueResultException nure) {
            logger.error("Finaliza ConsultasModeloStaging.actualizaRegistroDetallado(Long, EstadoAporteEnum) :: " + nure.getMessage());
            throw new TechnicalException(nure.getMessage());
        }
		logger.debug("Finaliza consultarIdRegistroGeneralPorPlanilla(Long) :: el id de registro general encontrado es: " + salida);
		return salida;
	}

	@Override
	public boolean verificarExistenciaAportesPendientes(Long idRegGeneralAdicionCorreccion) {
		try {
			Integer aportes = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VERIFICAR_EXISTENCIA_APORTES_PENDIENTES)
					.setParameter("idRegGeneral", idRegGeneralAdicionCorreccion.longValue())
					.getSingleResult();
			
			if(aportes > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean verificarExistenciaNovedadesPendientes(Long idRegGeneralAdicionCorreccion) {
		try {
			Integer aportes = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VERIFICAR_EXISTENCIA_NOVEDADES_PENDIENTES)
					.setParameter("idRegGeneral", idRegGeneralAdicionCorreccion.longValue())
					.getSingleResult();
			
			if(aportes > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void registrarPlanillaCandidataReproceso(Long idRegistroGeneral, String motivoBloqueo) {
		
		String firma = "registrarPlanillaCandidataReproceso(Long, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma + "con idRegistroGeneral = "+idRegistroGeneral);
		try {
			PlanillaCandidataReproceso planillaCandidataReproceso = new PlanillaCandidataReproceso(idRegistroGeneral, motivoBloqueo, new Date());
			entityManager.persist(planillaCandidataReproceso);
			
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: " + e);
			e.printStackTrace();
		}
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		
	}
}

