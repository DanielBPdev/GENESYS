package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.DiasFestivosModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.pila.business.interfaces.IConsultasModeloCore;
import com.asopagos.pila.constants.ConstantesParaMensajes;
import com.asopagos.pila.constants.MensajesErrorPersistenciaEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.CriterioConsultaDTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;
import com.asopagos.pila.dto.ResumenNovedadVigenteDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
/**
 * @author jocampo
 *
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 7048887106626912665L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    /** Variable de mensaje de error */
    private String mensaje;

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#totalAportesRelacionados(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal totalAportesRelacionados(Long idRegistroGeneral) {
        logger.debug("Inicia totalAportesRelacionados(Long idRegistroGeneral)");

        try {
            BigDecimal result = entityManagerCore.createNamedQuery(NamedQueriesConstants.TOTAL_APORTES_RELACIONADOS, BigDecimal.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();
            logger.debug("Finaliza totalAportesRelacionados(Long idRegistroGeneral)");

            return result;
        } catch (Exception e) {
            logger.debug("Finaliza totalAportesRelacionados(Long idRegistroGeneral)");
            logger.error("Hubo un error en la consulta");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#totalAportesRegistrados(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal totalAportesRegistrados(Long idRegistroGeneral) {
        logger.debug("Inicia totalAportesRegistrados(Long idRegistroGeneral)");

        try {
            BigDecimal result = entityManagerCore.createNamedQuery(NamedQueriesConstants.TOTAL_APORTES_REGISTRADOS, BigDecimal.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getSingleResult();
            logger.debug("Finaliza totalAportesRegistrados(Long idRegistroGeneral)");

            return result;
        } catch (Exception e) {
            logger.debug("Finaliza totalAportesRegistrados(Long idRegistroGeneral)");
            logger.error("Hubo un error en la consulta");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#detalleAportesPlanilla(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AporteDetalladoPlanillaDTO> detalleAportesPlanilla(Long idRegistroGeneral) {
        String firmaMetodo = "ConsultasModeloCore.detalleAportesPlanilla(Long)";
        logger.debug("Inicia " + firmaMetodo);

        List<AporteDetalladoPlanillaDTO> result = null;
        try {
            result = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.DETALLE_APORTES_ASOCIADOS_A_PLANILLA, AporteDetalladoPlanillaDTO.class)
                    .setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

        } catch (NoResultException e) {
            result = Collections.emptyList();
        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.APORTES, e.getMessage());
            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AporteDetalladoPlanillaDTO> detalleAportesPlanillaPaginada(Long idRegistroGeneral, UriInfo uri,
            HttpServletResponse response) {
        String firmaMetodo = "ConsultasModeloCore.detalleAportesPlanillaPaginada(Long, UriInfo, HttpServletResponse)";
        logger.debug("Inicia " + firmaMetodo);

        List<AporteDetalladoPlanillaDTO> result = null;
        try {
            QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
            
            queryBuilder.addParam("idRegistroGeneral", idRegistroGeneral);

            queryBuilder.addOrderByDefaultParam("-idAporte");
            
            Query query = queryBuilder.createQuery(NamedQueriesConstants.DETALLE_APORTES_ASOCIADOS_A_PLANILLA, null);
            
            result = (List<AporteDetalladoPlanillaDTO>) query.getResultList();

        } catch (NoResultException e) {
            result = Collections.emptyList();
        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.APORTES, e.getMessage());
            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#obtenerNovedades(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, List<DetallePestanaNovedadesDTO>> obtenerNovedades(List<Long> idsRegDet) {
        String firmaMetodo = "ConsultasModeloCore.obtenerNovedades(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, List<DetallePestanaNovedadesDTO>> result = new HashMap<>();

        // se consultan los registros en paquetes de 1000 registros
        Integer inicio = 0;
        Integer fin = idsRegDet.size() > 1000 ? 1000 : idsRegDet.size();
        while (fin <= idsRegDet.size()) {
            List<Long> idsRegDetTemp = idsRegDet.subList(inicio, fin);
        
            List<DetallePestanaNovedadesDTO> resultQuery = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES, DetallePestanaNovedadesDTO.class)
                    .setParameter("idRegistroDetallado", idsRegDetTemp).getResultList();
            
            for (DetallePestanaNovedadesDTO novedad : resultQuery) {
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

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarBancos()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Banco> consultarBancos() {
        logger.debug("Inicia ConsultasModeloCore.consultarBancos");
        List<Banco> lstBancos = null;
        try {
            lstBancos = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANCOS_PARAMETRIZADOS, Banco.class)
                    .getResultList();
        } catch (NoResultException e) {
            logger.error("ConsultasModeloCore.consultarBancos :: Hubo un error en la consulta");
        }
        logger.debug("Finaliza ConsultasModeloCore.consultarBancos()");
        return lstBancos;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarFestivos()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DiasFestivosModeloDTO> consultarFestivos() {
        String firmaMetodo = "ConsultasModeloCore.consultarFestivos()";
        logger.debug("Inicia " + firmaMetodo);

        List<DiasFestivosModeloDTO> result = new ArrayList<DiasFestivosModeloDTO>();

        try {
            List<DiasFestivos> resultEnEntity = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIAS_FESTIVOS)
                    .getResultList();

            for (DiasFestivos diasFestivos : resultEnEntity) {
                DiasFestivosModeloDTO diaFestivoDTO = new DiasFestivosModeloDTO();
                diaFestivoDTO.converToDTO(diasFestivos);
                result.add(diaFestivoDTO);
            }
        } catch (NoResultException e) {
            // no se encuentran resultados, se retona un listado vacío
        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.DIAS_FESTIVOS, e.getMessage());
            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarNovedadesIngRetCotizante(com.asopagos.pila.dto.CriterioConsultaDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarNovedadesIngRetCotizante(CriterioConsultaDTO criteriosConsulta) {
        String firmaMetodo = "ConsultasModeloCore.consultarNovedadesIngRetCotizante(CriterioConsultaDTO)";
        logger.debug("Inicia " + firmaMetodo);

        Long result = null;

        try {
            TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
            if (criteriosConsulta.getEsDependiente()) {
                tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
            }

            result = (Long) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ING_RET_AFILIADO_POSTERIOR)
                    .setParameter("tipoIdCotizante", criteriosConsulta.getTipoIdCotizante())
                    .setParameter("numeroIdCotizante", criteriosConsulta.getNumeroIdCotizante()).setParameter("tipoAfiliado", tipoAfiliado)
                    .setParameter("tipoIdAportante", criteriosConsulta.getTipoIdAportante())
                    .setParameter("numeroIdAportante", criteriosConsulta.getNumeroIdAportante())
                    .setParameter("fechaIngreso", criteriosConsulta.getFechaIngresoReferencia())
                    .setParameter("fechaRetiro", criteriosConsulta.getFechaRetiroReferencia()).getSingleResult();
        } catch (NoResultException e) {
            // no se encuentran resultados, se retona 0

            logger.debug("Finaliza " + firmaMetodo + " - Sin resultados");
            return 0L;
        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.ROL_AFILIADO, e.getMessage());
            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarAporteDetalladoPorTransaccion(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AporteDetalladoModeloDTO consultarAporteDetalladoPorTransaccion(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloCore.consultarAporteDetalladoPorTransaccion(Long)";
        logger.debug("Inicia " + firmaMetodo);

        AporteDetalladoModeloDTO result = null;

        try {
            AporteDetallado resultEntity = (AporteDetallado) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_CORE_POR_REGISTRO).setMaxResults(1)
                    .setParameter("idRegistroDetallado", idRegistroDetallado).getSingleResult();

            if (resultEntity != null) {
                result = new AporteDetalladoModeloDTO();
                result.convertToDTO(resultEntity);
            }
        } catch (NoResultException e) {
            // no se encuentran resultados, se retona null

            logger.debug("Finaliza " + firmaMetodo + " - Sin resultados");
            return null;
        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.APORTE_DETALLADO,
                    e.getMessage());
            logger.error("Finaliza " + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#actualizarAporteDetallado(com.asopagos.dto.modelo.AporteDetalladoModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarAporteDetallado(AporteDetallado aporteDetallado) {
        String firmaMetodo = "ConsultasModeloCore.actualizarAporteDetallado(AporteDetallado)";
        logger.debug("Inicia " + firmaMetodo);
        if (aporteDetallado.getId() != null) {
            entityManagerCore.merge(aporteDetallado);
        }
        else {
            entityManagerCore.persist(aporteDetallado);
        }
        logger.debug("Finaliza " + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#actualizarAporteDetallado(com.asopagos.dto.modelo.AporteDetalladoModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarAporteGeneral(AporteGeneral aporteGeneral) {
        String firmaMetodo = "ConsultasModeloCore.actualizarAporteGeneral(AporteGeneral)";
        logger.debug("Inicia " + firmaMetodo);
        if (aporteGeneral.getId() != null) {
            entityManagerCore.merge(aporteGeneral);
        }
        else {
            entityManagerCore.persist(aporteGeneral);
        }
        logger.debug("Finaliza " + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarNovedadesVigentesCotizante(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ResumenNovedadVigenteDTO> consultarNovedadesVigentesCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante,
            String numeroIdentificacionCotizante) {
        String firmaMetodo = "ConsultasModeloCore.consultarNovedadesVigentesCotizante(TipoIdentificacionEnum, String)";
        logger.debug("Inicia " + firmaMetodo);

        List<ResumenNovedadVigenteDTO> result = null;

        result = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_VIGENTES_COTIZANTE, ResumenNovedadVigenteDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacionCotizante)
                .setParameter("numeroIdentificacion", numeroIdentificacionCotizante).getResultList();

        logger.debug("Finaliza " + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#cotizanteConSubsidioPeriodo(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
            String periodoAporte) {
        String firmaMetodo = "ConsultasModeloCore.cotizanteConSubsidioPeriodo(TipoIdentificacionEnum, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = Boolean.FALSE;

        try {
            StoredProcedureQuery storedProcedure = entityManagerCore
                    .createStoredProcedureQuery(NamedQueriesConstants.HAY_SUBSIDIO_PARA_COTIZANTE);
            storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.TIPO_ID_COTIZANTE, String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.NUM_ID_COTIZANTE, String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.PERIODO_APORTE_TEXTO, String.class, ParameterMode.IN);
            
            storedProcedure.setParameter(ConstantesParametrosSp.TIPO_ID_COTIZANTE, tipoIdentificacionCotizante.name());
            storedProcedure.setParameter(ConstantesParametrosSp.NUM_ID_COTIZANTE, numeroIdentificacionCotizante);
            storedProcedure.setParameter(ConstantesParametrosSp.PERIODO_APORTE_TEXTO, periodoAporte);

            storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.TIENE_SUBSIDIO, Boolean.class, ParameterMode.OUT);
            storedProcedure.execute();
            
            result = (Boolean) storedProcedure.getOutputParameterValue(ConstantesParametrosSp.TIENE_SUBSIDIO);

        } catch (Exception e) {
            // se presentan errores en la consulta, se lanza excepción técnica
            mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA.getReadableMessage(ConstantesParaMensajes.COTIZANTE_CON_SUBSIDIO,
                    e.getMessage());
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarAporteGeneralPorId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AporteGeneralModeloDTO consultarAporteGeneralPorId(Long idAporteGeneral) {
        String firmaMetodo = "ConsultasModeloCore.consultarAporteGeneralPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AporteGeneralModeloDTO result = null;

        AporteGeneral aporteGeneralEntity = entityManagerCore.find(AporteGeneral.class, idAporteGeneral);
        if (aporteGeneralEntity != null) {
            result = new AporteGeneralModeloDTO();
            result.convertToDTO(aporteGeneralEntity);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarAnulacionAportesOriginales(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, Boolean> consultarAnulacionAportesOriginales(List<Long> idsRegistroDetallado){
        String firmaMetodo = "ConsultasModeloCore.consultarAnulacionAportesOriginales(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Map<Long, Boolean> result = new HashMap<>();
        
        if(!idsRegistroDetallado.isEmpty()){
            List<Object[]> resultQuery = new ArrayList<>();
            
            List<Long> listaTemp = new ArrayList<>();
            for(Long idRegistro : idsRegistroDetallado){
                if(listaTemp.size() == 2000){
                    resultQuery.addAll(entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICADOR_APORTE_ANULADO)
                            .setParameter("idsRegistroDetallado", listaTemp).getResultList());
                    
                    listaTemp.clear();
                }
                listaTemp.add(idRegistro);
            }
            resultQuery.addAll(entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICADOR_APORTE_ANULADO)
                    .setParameter("idsRegistroDetallado", listaTemp).getResultList());
            
            for (Object[] registro : resultQuery) {
                result.put(((BigInteger) registro[0]).longValue(), (Integer) registro[1] == 1 ? Boolean.TRUE : Boolean.FALSE);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	@Override
	public List<PersonaModeloDTO> consultarPersonas(List<PersonaModeloDTO> personasABuscar) {
		
		logger.debug("Inicia consultarPersonas(List<PersonaDTO> personasABuscar)");
		
		List<PersonaModeloDTO> listaPersonasFinal = new ArrayList<>();
		
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            String filtro = mapper.writeValueAsString(personasABuscar);

            List<Object[]> listaPersonasEncontradas = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_EXISTENTES)
                    .setParameter("filtros", filtro).getResultList();
            
            if(!listaPersonasEncontradas.isEmpty()) {
            	for(Object[] persona : listaPersonasEncontradas) {
            		PersonaModeloDTO personaModelo = new PersonaModeloDTO();
            		if(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(persona[0].toString()) != null ) {
            			personaModelo.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(persona[0].toString()));
                    	personaModelo.setNumeroIdentificacion(persona[1].toString());

                		listaPersonasFinal.add(personaModelo);
            		}
            		
            	}
            }

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarPersonas(List<PersonaDTO> personasABuscar)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
		
		
		return listaPersonasFinal;
	}
	
	/** 
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarListasBlancasAportantes(java.util.List)
     */
	public List<ListasBlancasAportantes> consultarListasBlancasAportantes(List<String> numeroIds){
		List<ListasBlancasAportantes> listaBlanca = new ArrayList<ListasBlancasAportantes>(); 
		try {
			if(!numeroIds.isEmpty()) {
				listaBlanca = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_BLANCA_PILA_POR_LISTA_NUMERO_IDENTIFICACION)
					.setParameter("numeroIdentificacionPlanilla", numeroIds).getResultList();
			}
		} catch (NoResultException e) {
			logger.info("aplicarListaBlanca: no existe la datos");
		}
		return listaBlanca;
	}

    @Override
    public List<ListasBlancasAportantes> consultarlistasBlancas() {

        List<ListasBlancasAportantes> listasBlancas = new ArrayList<>();
        listasBlancas =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTAS_BLANCAS,ListasBlancasAportantes.class).getResultList();
        return listasBlancas;

    }

    @Override
    public Boolean agregarEditarlistasBlancas(ListasBlancasAportantes listaBlancaAportante) {
        if(!existeListasBlancasAportantes(listaBlancaAportante)){
            if(listaBlancaAportante.getIdListaBlanca() != null){
                entityManagerCore.merge(listaBlancaAportante);
            }else{
                entityManagerCore.persist(listaBlancaAportante);
            }
            
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }

    }
    @Override
    public void editarEstadolistasBlancas(ListasBlancasAportantes listaBlancaAportante) {


            if(listaBlancaAportante.getActivo().equals(Boolean.TRUE)){
                listaBlancaAportante.setActivo(Boolean.FALSE);
            }else{
                listaBlancaAportante.setActivo(Boolean.TRUE);
            }
            entityManagerCore.merge(listaBlancaAportante);

    }

	public Boolean existeListasBlancasAportantes(ListasBlancasAportantes listaBlancaAportante){
		ListasBlancasAportantes listaBlanca = new ListasBlancasAportantes(); 
		try {
				listaBlanca = entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_BLANCA_NUMERO_IDENTIFICACION, ListasBlancasAportantes.class)
					.setParameter("numeroIdentificacionEmpleador", listaBlancaAportante.getNumeroIdentificacionEmpleador())
                    .setParameter("id", listaBlancaAportante.getIdListaBlanca()).getSingleResult();
        logger.info("listaBlanca "+listaBlanca);
		if(listaBlanca != null){
            return Boolean.TRUE;
        }
		} catch (NoResultException e) {
			logger.info("aplicarListaBlanca: no existe la datos");
		}
		return Boolean.FALSE;
	}
}
