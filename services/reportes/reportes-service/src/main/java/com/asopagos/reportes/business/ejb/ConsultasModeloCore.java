package com.asopagos.reportes.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.DashBoardConsultaDTO;
import com.asopagos.entidades.ccf.core.DatosFichaControl;
import com.asopagos.entidades.ccf.core.GeneracionReporteNormativo;
import com.asopagos.entidades.ccf.core.ReporteKPI;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.reportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.CategoriaAfiliadoDTO;
import com.asopagos.reportes.dto.CategoriaBeneficiarioDTO;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.reportes.dto.DatosIdentificadorGrupoReporteDTO;
import com.asopagos.reportes.dto.DetalleBeneficiarioDTO;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.rest.exception.TechnicalException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-KPI<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    @SuppressWarnings("unchecked")
    @Override
    public List<DatosIdentificadorGrupoReporteDTO> consultarIdentificadorReporte(ReporteKPIEnum nombreReporte,
            FrecuenciaMetaEnum frecuenciaReporte) {
        try {
            logger.debug(
                    "Inicio de método consultarIdentificadorReporte(ReporteKPIEnum, FrecuenciaMetaEnum)" + nombreReporte.getDescripcion());
            List<DatosIdentificadorGrupoReporteDTO> identificadorReportes = new ArrayList<>();
            List<ReporteKPI> reportes = (List<ReporteKPI>) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_REPORTE_TIPO_REPORTE_FRECUENCIA)
                    .setParameter("nombreReporte", nombreReporte).setParameter("frecuenciaReporte", frecuenciaReporte).getResultList();

            if (reportes != null && !reportes.isEmpty()) {
                for (ReporteKPI reporteKPI : reportes) {
                    DatosIdentificadorGrupoReporteDTO reporteDTO = new DatosIdentificadorGrupoReporteDTO(reporteKPI);
                    identificadorReportes.add(reporteDTO);
                }
            }
            logger.debug(
                    "Fin de método consultarIdentificadorReporte(ReporteKPIEnum, FrecuenciaMetaEnum) " + nombreReporte.getDescripcion());

            return identificadorReportes;

        } catch (Exception e) {
            logger.error("consultarIdentificadorReporte(ReporteKPIEnum, FrecuenciaMetaEnum)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#guardarReporteNormativo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public void guardarReporteNormativo(GeneracionReporteNormativoDTO reporteNormativoDTO) {
        String firmaServicio = "ConsultasModeloCore.guardarReporteNormativo(GeneracionReporteNormativoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        GeneracionReporteNormativo reporte = reporteNormativoDTO.convertToEntity();
        try {
            entityManagerCore.persist(reporte);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + " error al guardar el reporte normativo",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#consultarHistoricosReportesOficiales(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<GeneracionReporteNormativoDTO> consultarHistoricosReportesOficiales(GeneracionReporteNormativoDTO generacionReporteDTO,
            UriInfo uri, HttpServletResponse response, Boolean sinFechas) {
        String firmaServicio = "ConsultasModeloCore.consultarHistoricosReportesOficiales(GeneracionReporteNormativoDTO,UriInfo,HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Query query = null;
        QueryBuilder queryBuilder = new QueryBuilder(entityManagerCore, uri, response);
        queryBuilder.addParam("reporteNormativo", generacionReporteDTO.getReporteNormativo());
        queryBuilder.addParam("reporteOficial", Boolean.TRUE);
        queryBuilder.addParam("fechaInicio", generacionReporteDTO.getFechaInicio());
        queryBuilder.addParam("fechaFin", generacionReporteDTO.getFechaFin());
        queryBuilder.addOrderByDefaultParam("fechaGeneracion");
        
        List<GeneracionReporteNormativoDTO> resultado = null; 
        
        String consulta = sinFechas ? NamedQueriesConstants.CONSULTAR_HISTORICO_REPORTES_NORMATIVOS_OFICIALES
                : NamedQueriesConstants.VERIFICAR_REPORTES_NORMATIVOS_OFICIALES;
        
        try {
            query = queryBuilder.createQuery(consulta, null);
            resultado = query.getResultList();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#verificarReporteNormativo(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.reportes.ReporteNormativoEnum)
     */
    @Override
    public Boolean verificarReporteNormativo(Long fechaInicio, Long fechaFin, ReporteNormativoEnum reporteNormativo) {
        String firmaServicio = "ConsultasModeloCore.verificarReporteNormativo(Long,Long,ReporteNormativoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Boolean resultado = null;
        try {
            List<GeneracionReporteNormativoDTO> reporte = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.VERIFICAR_REPORTES_NORMATIVOS_OFICIALES,
                            GeneracionReporteNormativoDTO.class)
                    .setParameter("fechaInicio", new Date(fechaInicio))
                    .setParameter("fechaFin", fechaFin != null ? new Date(fechaFin) : fechaFin)
                    .setParameter("reporteNormativo", reporteNormativo).setParameter("reporteOficial", Boolean.TRUE).getResultList();
            
            if(reporte != null && !reporte.isEmpty()){
                resultado = Boolean.TRUE;
            }else{
                resultado = Boolean.FALSE;
            }
            

        } catch (NoResultException e) {
            resultado = Boolean.FALSE;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#obtenerCategoriaActual(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum, java.lang.Boolean)
     */
    @Override
    public CategoriaDTO obtenerCategoriaActual(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            TipoAfiliadoEnum tipoAfiliado, Boolean isAfiliadoPrincipal) {
        String firmaServicio = "ConsultasModeloCore.obtenerCategoriaActual(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        CategoriaDTO categoria = new CategoriaDTO();
        try {
            
//            List<String> idsBenDetalle = (List<String>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_BENEFICIARIO_DETALLE)
//                    .setParameter("tipoIdBeneficiario", tipoIdAfiliado)
//                    .setParameter("numeroIdBeneficiario", numeroIdAfiliado)
//                    .getResultList();
            
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return categoria;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#obtenerCategoriaPropiaActual(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    /*
    @Override
    public List<CategoriaAfiliadoDTO> obtenerCategoriaPropiaActual(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            TipoAfiliadoEnum tipoAfiliado) {
        String firmaServicio = "ConsultasModeloCore.obtenerCategoriaPropiaActual(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        try {
            
            List<CategoriaAfiliadoDTO> salidaCategorias;
            
            List<Object[]> categorias = (List<Object[]>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CATEGORIA_ACTUAL_AFILIADO_CORE)
                    .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                    .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                    .getResultList();

            if(categorias != null && !categorias.isEmpty()){
                salidaCategorias = new ArrayList<>();
                for (Object[] obj : categorias) {
                    
                    CategoriaAfiliadoDTO categoria = new CategoriaAfiliadoDTO();
                    
                    categoria.setAfiliado(obj[0] != null ? Long.valueOf(obj[0].toString()) : null);
                    categoria.setPersona(obj[1] != null ? Long.valueOf(obj[1].toString()) : null);
                    categoria.setTipoIdentificacion(obj[2] != null ? TipoIdentificacionEnum.valueOf(obj[2].toString()) : null);
                    categoria.setNumeroIdentificacion(obj[3] != null ? obj[3].toString() : null);
                    categoria.setTipoCotizante(obj[4] != null ? TipoAfiliadoEnum.valueOf(obj[4].toString()) : null);
                    categoria.setClasificacion(obj[5] != null ? ClasificacionEnum.valueOf(obj[5].toString()) : null);
                    categoria.setSalario(obj[6] != null ? new BigDecimal(obj[6].toString()) : null);
                    categoria.setEstadoAfiliacion(obj[7] != null ? EstadoAfiliadoEnum.valueOf(obj[7].toString()) : null);
                    categoria.setFechaFinServicioSinAfiliacion(obj[8] != null ? new Date(obj[8].toString()) : null);
                    categoria.setAporteEmpleadorNoAfiliado(obj[9] != null ? Boolean.valueOf(obj[9].toString()) : null);
                    categoria.setCategoria(obj[10] != null ? obj[10].toString() : null);
                    
                    salidaCategorias.add(categoria);
                }
            }
            else{
                salidaCategorias = null;
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return salidaCategorias;
        }
        catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    */

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#obtenerCategoriaActualConyugeAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    /*
    @Override
    public List<CategoriaBeneficiarioDTO> obtenerCategoriaActualConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            TipoAfiliadoEnum tipoAfiliado, List<Object[]> datosIdConyuge) {
        String firmaServicio = "ConsultasModeloCore.obtenerCategoriaActualConyugeAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<CategoriaBeneficiarioDTO> categorias = new ArrayList<>();
        try {
            if(datosIdConyuge != null && !datosIdConyuge.isEmpty()){
                List<Object[]> objetos = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_CONYUGE_EN_CORE)
                        .setParameter("tipoIdBeneficiario", datosIdConyuge.get(0)[0].toString())
                        .setParameter("numeroIdBeneficiario", datosIdConyuge.get(0)[1].toString())
                        .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                        .getResultList();
                
                
                if(objetos != null && !objetos.isEmpty()){
                    for (Object[] obj : objetos) {
                        CategoriaBeneficiarioDTO categoria = new CategoriaBeneficiarioDTO();
                        
                        categoria.setBeneficiarioDetalle(obj[0] != null ? obj[0].toString() : null);
                        categoria.setPersona(obj[1] != null ? obj[1].toString() : null);
                        categoria.setTipoIdentificacion(obj[2] != null ? obj[2].toString() : null);
                        categoria.setNumeroIdentificacion(obj[3] != null ? obj[3].toString() : null);
                        categoria.setTipoBeneficiario(obj[4] != null ? obj[4].toString() : null);
                        categoria.setEstadoBeneficiarioAfiliado(obj[5] != null ? obj[5].toString() : null);
                        categoria.setAfiliado(obj[6] != null ? obj[6].toString() : null);
                        categoria.setTipoIdentificacionAfiliado(obj[7] != null ? obj[7].toString() : null);
                        categoria.setNumeroIdentificacionAfiliado(obj[8] != null ? obj[8].toString() : null);
                        categoria.setTipoCotizante(obj[9] != null ? obj[9].toString() : null);
                        categoria.setClasificacion(obj[10] != null ? obj[10].toString() : null);
                        categoria.setSalario(obj[11] != null ? obj[11].toString() : null);
                        categoria.setEstadoAfiliacionAfiliado(obj[12] != null ? obj[12].toString() : null);
                        categoria.setFechaFinServicioSinAfiliacion(obj[13] != null ? obj[13].toString() : null);
                        categoria.setCategoria(obj[14] != null ? obj[14].toString() : null); 
                        
                        categorias.add(categoria);
                    }
                    
                    
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return categorias;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    */

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#consultarDatosIdConyuge(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<Object[]> consultarDatosIdConyuge(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        List<Object[]> datosIdConyuge = (List<Object[]>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_ID_CONYUGE_ACTIVO_AFILIADO_EN_CORE)
                .setParameter("tipoIdentificacion", tipoIdAfiliado.name())
                .setParameter("numeroIdentificacion", numeroIdAfiliado)
                .getResultList();
        return datosIdConyuge;
    }

        /*
    @Override
    public List<Object[]> consultarCategoriaActualAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        List<Object[]> categoriasPropias = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_PROPIA_EN_CORE)
                .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                .getResultList();
        
        return categoriasPropias;
    }
    */

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DashBoardConsultaDTO consultarPermisos(String rolUsuario) {
		String firmaServicio = "ConsultasModeloCore.consultarPermisos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        DashBoardConsultaDTO dashBoardConsultaDTO = null; 
		try {
			dashBoardConsultaDTO = (DashBoardConsultaDTO) entityManagerCore.createNamedQuery(
				NamedQueriesConstants.BUSCAR_PERMISOS_DASHBOARD_POR_ROL_USARIO)
				.setParameter("rolUsuario", rolUsuario).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return dashBoardConsultaDTO; 
		
	}

    @Override
    public List<DetalleBeneficiarioDTO> consultarIdBeneficiarios(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        List<DetalleBeneficiarioDTO> salida = new ArrayList<>();
        
        List<Object[]> idBeneficiarios = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_BENEFICIARIOS_AFILIADO)
                .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                .getResultList();

        if(idBeneficiarios != null && !idBeneficiarios.isEmpty()){
            for (Object[] obj : idBeneficiarios) {
                DetalleBeneficiarioDTO detalle = new DetalleBeneficiarioDTO();
                detalle.setIdDetalleBeneficiario(obj[0] != null ? Long.valueOf(obj[0].toString()): null);
                detalle.setTipoBeneficiario(obj[1] != null ? ClasificacionEnum.valueOf(obj[1].toString()) : null);
                
                salida.add(detalle);
            }
        }
        return salida;
    }

    @Override
    public Long consultarIdPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        List<Object[]> idPersona = (List<Object[]>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PEROSONA)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();
        
        return  (idPersona != null && !idPersona.isEmpty() && idPersona.get(0)[0] != null) ? Long.valueOf(idPersona.get(0)[0].toString()) : null;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<String> obtenerIdsBeneficarioDetalle(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        String firmaServicio = "ConsultasModeloCore.obtenerCategoriaActual(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        try {
            
            List<String> idsBenDetalle = (List<String>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_BENEFICIARIO_DETALLE)
                    .setParameter("tipoIdBeneficiario", tipoIdAfiliado.name())
                    .setParameter("numeroIdBeneficiario", numeroIdAfiliado)
                    .getResultList();
            
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return idsBenDetalle;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * @param tipoIdBeneficiario
     * @param numeroIdBeneficiario
     * @param numeroIdAfiliado
     * @param tipoIdAfiliado
     * @return a
     */
    public EstadoAfiliadoEnum obtenerEstadoBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario, String numeroIdAfiliado, TipoIdentificacionEnum tipoIdAfiliado) {
        
        //busca la info de los afiliados principales del afiliado como beneficiario
        Object resultado = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_BENEFICIARIO)
                  .setParameter("tipoIdBeneficiario", tipoIdBeneficiario.name())
                  .setParameter("numeroIdBeneficiario", numeroIdBeneficiario)
                  .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                  .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                  .getSingleResult();
        
        if (resultado == null) {
            return null;
        }
        else {
            return EstadoAfiliadoEnum.valueOf(resultado.toString());
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerInfoDetalladaAfiliadosPpales(List<Object[]> infoAfiPpales) {
        List<Object[]> salida = new ArrayList<>();
        for (Object[] obj : infoAfiPpales) {
            List<Object[]> datosRestantes = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_ID_AFILIADO_PPAL)
                    .setParameter("idAfiliado", obj[0].toString())
                    .getResultList();
            
            Object[] lista = new Object[9];
            lista[0] = obj[0];
            lista[3] = obj[1];
            lista[4] = obj[2];
            lista[5] = obj[3];
            lista[6] = obj[4];
            if(datosRestantes != null && !datosRestantes.isEmpty()){
                lista[1] = datosRestantes.get(0)[0];
                lista[2] = datosRestantes.get(0)[1];
                lista[7] = datosRestantes.get(0)[2];
                lista[8] = datosRestantes.get(0)[3];
            }
            salida.add(lista);
        }
        
        return salida;
    }

    @Override
    public String obtenerIdAfiliadoConyuge(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario) {
        
        List<BigInteger> idAfiliadoConyuge = (List<BigInteger>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_AFILIADO_BENEFICIARIO_CONYUGE)
                .setParameter("tipoIdAfiliado", tipoIdBeneficiario.name())
                .setParameter("numeroIdAfiliado", numeroIdBeneficiario)
                .getResultList();
        
        if(idAfiliadoConyuge != null && !idAfiliadoConyuge.isEmpty()){
            return idAfiliadoConyuge.get(0).toString();
        }
        else {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#obtenerIdAfiliadoSecundario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<BigInteger> obtenerIdAfiliadoSecundario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario,
            Long idAfiliado) {
        List<BigInteger> idAfiliadoSecundario = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_AFILIADO_SECUNDARIO_CORE, BigInteger.class)
                .setParameter("tipoIdBeneficiario", tipoIdBeneficiario.name()).setParameter("numeroIdBeneficiario", numeroIdBeneficiario)
                .setParameter("idAfiliado", idAfiliado).getResultList();
        return idAfiliadoSecundario;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#obtenerCategoriasPropiasAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
        String firma = "obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum, List<CategoriaAfiliadoDTO>)";
        logger.debug("Inicia servicio " + firma);

        CategoriasComoAfiliadoPrincipalDTO categoriasAfiliado = new CategoriasComoAfiliadoPrincipalDTO();
        List<CategoriaDTO> historicoCategorias = new ArrayList<>();
        int contador = 0;
        
        // se consulta el parámetro que indica los días de gracia para mantener la categoría luego del retiro
        Integer tiempoServicios = Integer
                .parseInt((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA));

        Boolean tieneReporteFallemiciento = Boolean.FALSE;

        List<Object> consultaReporteFallecimiento = (List<Object>) entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIENE_FALLECIMIENTO_REPORTADO)
                .setParameter("tipoIdentificacion", tipoIdAfiliado.name())
                .setParameter("numeroIdentificacion", numeroIdAfiliado)
                .getResultList();
        
        if (consultaReporteFallecimiento != null && !consultaReporteFallecimiento.isEmpty()) {
            tieneReporteFallemiciento = Boolean.TRUE;
        }
 
        List<Object[]> objetos = (List<Object[]>) entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_CATEGORIAS_PROPIAS_AFILIADO)
                .setParameter("tipoIdentificacion", tipoIdAfiliado.name()).setParameter("numeroIdentificacion", numeroIdAfiliado)
                .getResultList();

        if (objetos != null && !objetos.isEmpty()) {
            String fechaCambio = null;
            CategoriaPersonaEnum categoria = null;
            CategoriaPersonaEnum tarifaUVT = null;
            TipoAfiliadoEnum tipoAfiliadoEnum = null;
            MotivoCambioCategoriaEnum motivo = null;
            
            LocalDate fechaLimiteServicios = null;
            LocalDate fechaActual = null;
            String[] fechaCambioArray = null;

            String fechaFinServiciosAfi = null;
            String[] fechaFinServiciosAfiCambioArray = null;
            
            Boolean agregar = null;
            
            for (Object[] obj : objetos) {
                agregar = Boolean.TRUE;

                tipoAfiliadoEnum = TipoAfiliadoEnum.valueOf(obj[0].toString());
                categoria = obj[1] != null ? CategoriaPersonaEnum.valueOf(obj[1].toString()) : null;
                tarifaUVT = obj[2] != null ? CategoriaPersonaEnum.valueOf(obj[2].toString()) : null;
                fechaCambio = obj[3] != null ? obj[3].toString() : null;
                motivo = obj[4] != null ? MotivoCambioCategoriaEnum.valueOf(obj[4].toString()) : null;
                fechaFinServiciosAfi = obj[5] != null ? obj[5].toString() : null;
                fechaCambioArray = fechaCambio != null ? fechaCambio.split(" ")[0].split("-") : null;
                fechaFinServiciosAfiCambioArray = fechaFinServiciosAfi != null ? fechaFinServiciosAfi.split(" ")[0].split("-") : null;
                CategoriaDTO cat = new CategoriaDTO();
                if (contador != 0) {
                    cat.setCategoriaDependiente(historicoCategorias.get(contador - 1).getCategoriaDependiente());
                    cat.setCategoriaIndependiente(historicoCategorias.get(contador - 1).getCategoriaIndependiente());
                    cat.setCategoriaPensionado(historicoCategorias.get(contador - 1).getCategoriaPensionado());
                    cat.setFechaCambioCategoria(historicoCategorias.get(contador - 1).getFechaCambioCategoria());
                    cat.setTarifaUVTDependiente(historicoCategorias.get(contador- 1).getTarifaUVTDependiente());
                    cat.setTarifaUVTIndependiente(historicoCategorias.get(contador- 1).getTarifaUVTIndependiente());
                    cat.setTarifaUVTPensionado(historicoCategorias.get(contador- 1).getTarifaUVTPensionado());
                    cat.setMotivoCambioCategoria(historicoCategorias.get(contador - 1).getMotivoCambioCategoria());
                }

                if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliadoEnum)) {
                    cat.setCategoriaDependiente(categoria.name());
                    cat.setCategoriaIndependiente(null);
                    cat.setCategoriaPensionado(null);
                    cat.setTarifaUVTDependiente(tarifaUVT != null ? tarifaUVT.name() : null);
                    cat.setTarifaUVTIndependiente(null);
                    cat.setTarifaUVTPensionado(null);
                    /*
                     * se calcula la fecha límite de servicios por parámetro, para los casos en los que se obtiene la
                     * categoría "Sin Categoría" por motivo de retiro
                     */
                    if (MotivoCambioCategoriaEnum.RETIRO.equals(motivo) && CategoriaPersonaEnum.SIN_CATEGORIA.equals(categoria)
                            && fechaCambioArray != null) {
                        if(fechaFinServiciosAfiCambioArray != null){
                            fechaLimiteServicios = LocalDate.of(Integer.parseInt(fechaFinServiciosAfiCambioArray[0]), 
                            Integer.parseInt(fechaFinServiciosAfiCambioArray[1]),Integer.parseInt(fechaFinServiciosAfiCambioArray[2]));  
                        }else{
                        fechaLimiteServicios = LocalDate.of(Integer.parseInt(fechaCambioArray[0]), Integer.parseInt(fechaCambioArray[1]),
                            Integer.parseInt(fechaCambioArray[2]));
                            fechaLimiteServicios = fechaLimiteServicios.plusDays(tiempoServicios);
                        }
                        fechaActual = LocalDate.now();
                        if (!tieneReporteFallemiciento && fechaActual.compareTo(fechaLimiteServicios) < 0) {
                            agregar = Boolean.FALSE;
                        }
                    }
                    if(agregar){
                         categoriasAfiliado.setCategoriaDependiente(categoria.name());
                    }
                }
                else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliadoEnum)) {
                    cat.setCategoriaIndependiente(categoria.name());
                    cat.setCategoriaDependiente(null);
                    cat.setCategoriaPensionado(null);
                    cat.setTarifaUVTIndependiente(tarifaUVT != null ? tarifaUVT.name() : null);
                    cat.setTarifaUVTDependiente(null);
                    cat.setTarifaUVTPensionado(null);
                    categoriasAfiliado.setCategoriaIndependiente(categoria.name());
                    categoriasAfiliado.setTarifaUVTIndependiente(tarifaUVT != null ? tarifaUVT.name() : null);
                }
                else if (TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliadoEnum)) {
                    cat.setCategoriaPensionado(categoria.name());
                    cat.setCategoriaDependiente(null);
                    cat.setCategoriaIndependiente(null);
                    cat.setTarifaUVTPensionado(tarifaUVT != null ? tarifaUVT.name() : null);
                    cat.setTarifaUVTDependiente(null);
                    cat.setTarifaUVTIndependiente(null);
                    categoriasAfiliado.setCategoriaPensionado(categoria.name());
                    categoriasAfiliado.setTarifaUVTPensionado(tarifaUVT != null ? tarifaUVT.name() : null);
                }
                cat.setFechaCambioCategoria(fechaCambio);
                cat.setMotivoCambioCategoria(motivo);

                if (agregar) {
                    historicoCategorias.add(cat);
                    contador++;
                }
            }
        }

        if (!historicoCategorias.isEmpty()) {
            Collections.reverse(historicoCategorias);
        }
        categoriasAfiliado.setCategorias(historicoCategorias);
       // categoriasAfiliado.setCategoriaDependiente((!historicoCategorias.isEmpty() && historicoCategorias.get(0) != null)
       //         ? historicoCategorias.get(0).getCategoriaDependiente() : null);
       // categoriasAfiliado.setCategoriaIndependiente((!historicoCategorias.isEmpty() && historicoCategorias.get(0) != null)
       //         ? historicoCategorias.get(0).getCategoriaIndependiente() : null);
       // categoriasAfiliado.setCategoriaPensionado((!historicoCategorias.isEmpty() && historicoCategorias.get(0) != null)
       //         ? historicoCategorias.get(0).getCategoriaPensionado() : null);
        
        logger.debug("Finaliza servicio " + firma);
        return categoriasAfiliado;
    
    }

    
    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#obtenerCategoriasConyugeAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
        String firma = "obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
        logger.debug("Inicia servicio " + firma);
        try {

            CategoriasComoAfiliadoPrincipalDTO categoriaPropia = new CategoriasComoAfiliadoPrincipalDTO();
            List<CategoriaDTO> historicoCatHeredadas = new ArrayList<>();
            
            List<Object[]> datosIdConyuge = consultarDatosIdConyuge(tipoIdAfiliado, numeroIdAfiliado);
            
            
            
            if(datosIdConyuge != null && !datosIdConyuge.isEmpty() && datosIdConyuge.get(0)[0] != null
            		&& datosIdConyuge.get(0)[1] != null){
            	TipoIdentificacionEnum tipoId = TipoIdentificacionEnum.valueOf(datosIdConyuge.get(0)[0].toString());
            	String numeroId = datosIdConyuge.get(0)[1].toString();
            	categoriaPropia = obtenerCategoriasPropiasAfiliado(tipoId, numeroId, null);
            }
            logger.debug("Finaliza servicio " + firma);
            return categoriaPropia;
            
            
//            List<Object[]> categoriasPropias = consultarCategoriaActualAfiliado(tipoIdAfiliado, numeroIdAfiliado);
//            
//            
//            if(datosIdConyuge != null && !datosIdConyuge.isEmpty()){
//                if(categoriasPropias != null && !categoriasPropias.isEmpty()){
//                    for (Object[] obj : categoriasPropias) {
//                        if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            categoriaPropia.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
//                        }
//                        else if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            categoriaPropia.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
//                        }
//                        else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            categoriaPropia.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
//                        }
//                    }
//                }
//                
//                int contador = 0;
//                List<Object[]> objetos = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_CATEGORIAS_HEREDADAS_AFI_PRINCIPAL)
//                        .setParameter("tipoIdBeneficiario", datosIdConyuge.get(0)[0].toString())
//                        .setParameter("numeroIdBeneficiario", datosIdConyuge.get(0)[1].toString())
//                        .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
//                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
//                        .getResultList();
//
//                if(objetos != null && !objetos.isEmpty()){
//                    for (Object[] obj : objetos) {
//                        CategoriaDTO cat = new CategoriaDTO();
//                        if(contador != 0){
//                            cat.setCategoriaDependiente(historicoCatHeredadas.get(contador-1).getCategoriaDependiente());
//                            cat.setCategoriaIndependiente(historicoCatHeredadas.get(contador-1).getCategoriaIndependiente());
//                            cat.setCategoriaPensionado(historicoCatHeredadas.get(contador-1).getCategoriaPensionado());
//                            cat.setFechaCambioCategoria(historicoCatHeredadas.get(contador-1).getFechaCambioCategoria());
//                            cat.setMotivoCambioCategoria(historicoCatHeredadas.get(contador-1).getMotivoCambioCategoria());
//                        }
//                        
//                        if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            cat.setCategoriaDependiente(obj[1].toString());
//                        }
//                        else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            cat.setCategoriaIndependiente(obj[1].toString());
//                        }
//                        else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
//                            cat.setCategoriaPensionado(obj[1].toString());
//                        }
//                        cat.setFechaCambioCategoria(obj[2].toString());
//                        cat.setMotivoCambioCategoria(obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()): null);
//                        
//                        historicoCatHeredadas.add(cat);
//                        contador++;
//                    }
//                }
//               
//
//                if(!historicoCatHeredadas.isEmpty()){
//                    Collections.reverse(historicoCatHeredadas);
//                }
//            }
//            categoriaPropia.setCategorias(historicoCatHeredadas);
//            logger.debug("Finaliza servicio " + firma);
//            return categoriaPropia;
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#obtenerCategoriasHeredadas(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            Boolean isAfiliadoPrincipal) {
        String firma = "obtenerCategoriasHeredadas(TipoIdentificacionEnum, String, Boolean, List<String>)";
        logger.info("Inicia servicio " + firma);
        try {
        	List<String> idsBenDetalle = obtenerIdsBeneficarioDetalle(tipoIdAfiliado, numeroIdAfiliado);
        	List<Object[]> infoAfiPrincipal = obtenerInfoDetalladaAfiliadosPpales(obtenerInfoAfiliadosPrincipales(idsBenDetalle,isAfiliadoPrincipal));
        	
        	
            CategoriasComoBeneficiarioDTO categoriasHeredadas = new CategoriasComoBeneficiarioDTO();
            List<CategoriaDTO> historicoCatHeredadas = new ArrayList<>();
            
            
            //verifica si se obtuvieron resultados (si el afiliado es beneficiario activo de alguien más) 
            if(infoAfiPrincipal != null && !infoAfiPrincipal.isEmpty()){

                List<Long> listaIdsAfiliadosPpales = new ArrayList<>();
                List<List<String>> afiliadosPpales = new ArrayList<>();
                
                //se determina de cuantos afiliadosPricipales es beneficiario el afiliado
                for (Object[] infoAfiPpal : infoAfiPrincipal) {
                    if(!listaIdsAfiliadosPpales.contains(Long.valueOf(infoAfiPpal[0].toString()))){
                        List <String> afiliado = new ArrayList<>();
                        afiliado.add(infoAfiPpal[0].toString());
                        listaIdsAfiliadosPpales.add(Long.valueOf(infoAfiPpal[0].toString()));
                        afiliado.add(infoAfiPpal[8].toString());
                        afiliadosPpales.add(afiliado);
                    }
                }
                
                //si solo es beneficiario activo de un afiliado, se toma este como el afiliado principal
                if(afiliadosPpales.size() == 1){
                    logger.info("**__**Afiliado priencipal 1  " +isAfiliadoPrincipal);
                    //si solo es beneficiario de un afiliado y el servicio se invocó
                    //buscando con respecto al afiliado principal se toma esté como tal,
                    //de lo contrario se devuelve un DTO vacío que se interpreta como que no
                    //tiene afiliado secundario (isAfiliadoPrincipal == false)
                    if(isAfiliadoPrincipal){
                        logger.info("**__**Afiliado  isAfiliadoPrincipal verdadero  ");
                        obtenerCategoriaAfiliadoPrincipal(categoriasHeredadas, infoAfiPrincipal, afiliadosPpales, isAfiliadoPrincipal);
                        logger.info("**__**Afiliado  isAfiliadoPrincipal verdadero 2  ");
                        obtenerHistoricoCategoriasRespectoAfiliado(tipoIdAfiliado, numeroIdAfiliado, categoriasHeredadas,
                                historicoCatHeredadas, idsBenDetalle, afiliadosPpales.get(0).get(0));
                                 categoriasHeredadas.setCategorias(historicoCatHeredadas);
                                 //inicio para categoria pocicion 1
                                int ciclos =1;
                                int ciclosInd =1;
                                int ciclosPen =1;
                                for (CategoriaDTO categoriaIter : historicoCatHeredadas){
                                    
                                   //  logger.info("**__**getTipoAfiliado   " + categoriaIter.getTipoAfiliado());
                                   // logger.info("**__**categoriasHeredadasgetTipoAfiliado   " + categoriasHeredadas.getTipoAfiliado());
                               
                                        if(categoriaIter.getCategoriaDependiente() != null && ciclos==1){
                                        categoriasHeredadas.setCategoriaDependiente(categoriaIter.getCategoriaDependiente());
                                            if(categoriaIter.getTarifaUVTDependiente() != null){
                                            categoriasHeredadas.setTarifaUVTDependiente(categoriaIter.getTarifaUVTDependiente());    
                                            }
                                               ciclos++;
                                        }
                                         if(categoriaIter.getCategoriaIndependiente() != null && ciclosInd==1){
                                            categoriasHeredadas.setCategoriaIndependiente(categoriaIter.getCategoriaIndependiente());
                                            if(categoriaIter.getTarifaUVTIndependiente() != null){
                                            categoriasHeredadas.setTarifaUVTIndependiente(categoriaIter.getTarifaUVTIndependiente());
                                            }
                                             ciclosInd++;
                                            logger.info("**__**categoriasHeredadas.setCategoriaIndependiente prime if  " + categoriasHeredadas.getCategoriaIndependiente());
                                        }
                                         if(categoriaIter.getCategoriaPensionado() != null  && ciclosPen==1){
                                            categoriasHeredadas.setCategoriaPensionado(categoriaIter.getCategoriaPensionado());
                                            if(categoriaIter.getTarifaUVTPensionado() != null){
                                            categoriasHeredadas.setTarifaUVTPensionado(categoriaIter.getTarifaUVTPensionado());
                                            } 
                                              ciclosPen++;
                                        }
                                           logger.info("**__**categoriaPocUno   " + categoriaIter.getCategoriaDependiente());
                                    
                          
                                }
                      
                            //fin para categoria pocicion 1
                       
                        return categoriasHeredadas;
                    }else{
                         logger.info("**__**Afiliado    FALSE isAfiliadoPrincipal verdadero  ");
                        obtenerCategoriaAfiliadoPrincipal(categoriasHeredadas, infoAfiPrincipal, afiliadosPpales, isAfiliadoPrincipal);
                        logger.info("**__**Afiliado  FALSE isAfiliadoPrincipal verdadero 2  ");
                        obtenerHistoricoCategoriasRespectoAfiliado(tipoIdAfiliado, numeroIdAfiliado, categoriasHeredadas,
                                historicoCatHeredadas, idsBenDetalle, afiliadosPpales.get(0).get(0));
                         categoriasHeredadas.setCategorias(historicoCatHeredadas);
                                 //inicio para categoria pocicion 1
                                int ciclos =1;
                                int ciclosInd =1;
                                int ciclosPen =1;
                                for (CategoriaDTO categoriaIter : historicoCatHeredadas){
                                        if(categoriaIter.getCategoriaDependiente() != null && ciclos==1){
                                        categoriasHeredadas.setCategoriaDependiente(categoriaIter.getCategoriaDependiente());
                                            if(categoriaIter.getTarifaUVTDependiente() != null){
                                            categoriasHeredadas.setTarifaUVTDependiente(categoriaIter.getTarifaUVTDependiente());
                                            }
                                        logger.info("**__**categoriaPocUno setCategoriaDependiente  " + categoriasHeredadas.getCategoriaDependiente());
                                             ciclos++;
                                        }
                                         if(categoriaIter.getCategoriaIndependiente() != null && ciclosInd==1){
                                            categoriasHeredadas.setCategoriaIndependiente(categoriaIter.getCategoriaIndependiente());
                                            if(categoriaIter.getTarifaUVTIndependiente() != null){
                                            categoriasHeredadas.setTarifaUVTIndependiente(categoriaIter.getTarifaUVTIndependiente());
                                            }
                                            ciclosInd++;
                                                 logger.info("**__**setCategoriaIndependiente no es afiliado principal  " + categoriasHeredadas.getCategoriaDependiente());
                                        }
                                         if(categoriaIter.getCategoriaPensionado() != null && ciclosPen==1){
                                            categoriasHeredadas.setCategoriaPensionado(categoriaIter.getCategoriaPensionado());
                                            if(categoriaIter.getTarifaUVTPensionado() != null){
                                            categoriasHeredadas.setTarifaUVTPensionado(categoriaIter.getTarifaUVTPensionado());
                                            }
                                            ciclosPen++;
                                        }
                                               logger.info("**__**categoriaPocUno 1  " + categoriaIter.getCategoriaDependiente());
                                    }
                               
                                    
                                
                      
                            //fin para categoria pocicion 1
                        return categoriasHeredadas;
                    }
                //si es beneficiario activo de más de un afiliado
                }else if(afiliadosPpales.size()>1){
                    //
                        logger.info("**__**categoriasHeredadas.getNumeroIdentificacion()  " + categoriasHeredadas.getNumeroIdentificacion());
                       obtenerCategoriaAfiliadoPrincipal(categoriasHeredadas, infoAfiPrincipal, afiliadosPpales, isAfiliadoPrincipal);
                       //Mantis 264542 Estado del beneficiario en vez del afiliado
                       categoriasHeredadas.setEstadoActualAfiliado(obtenerEstadoBeneficiario(tipoIdAfiliado, numeroIdAfiliado,categoriasHeredadas.getNumeroIdentificacion(),categoriasHeredadas.getTipoIdentificacion()));
                       obtenerHistoricoCategoriasRespectoAfiliado(tipoIdAfiliado, numeroIdAfiliado, categoriasHeredadas,
                            historicoCatHeredadas, idsBenDetalle, afiliadosPpales.get(1) != null ? afiliadosPpales.get(1).get(0) : afiliadosPpales.get(0).get(0));
                               categoriasHeredadas.setCategorias(historicoCatHeredadas);
                                 //inicio para categoria pocicion 1
                                   int ciclos =1;
                                int ciclosInd =1;
                                int ciclosPen =1;
                                for (CategoriaDTO categoriaIter : historicoCatHeredadas){
                                 
                                        if(categoriaIter.getCategoriaDependiente() != null){
                                        categoriasHeredadas.setCategoriaDependiente(categoriaIter.getCategoriaDependiente());
                                           logger.info("**__**categoriaPocUno c2  " + categoriaIter.getCategoriaDependiente());
                                            ciclos++;
                                        }
                                         if(categoriaIter.getCategoriaIndependiente() != null){
                                            categoriasHeredadas.setCategoriaIndependiente(categoriaIter.getCategoriaIndependiente());
                                        logger.info("**__**categoriaPocUno getCategoriaIndependientec 2  " + categoriasHeredadas.getCategoriaIndependiente());
                                           ciclosInd++;
                                        }
                                         if(categoriaIter.getCategoriaPensionado() != null){
                                            categoriasHeredadas.setCategoriaPensionado(categoriaIter.getCategoriaPensionado());
                                             ciclosPen++;
                                        }
                                    
                                }
                      
                            //fin para categoria pocicion 1
                    
                       return categoriasHeredadas;
                }
            }
            logger.debug("Finaliza servicio " + firma);
            categoriasHeredadas.setCategorias(historicoCatHeredadas);
            return categoriasHeredadas;
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * @param tipoIdAfiliado
     * @param numeroIdAfiliado
     * @param categoriasHeredadas
     * @param historicoCatHeredadas
     */
    private void obtenerHistoricoCategoriasRespectoAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
    CategoriasComoBeneficiarioDTO categoriasHeredadas, List<CategoriaDTO> historicoCatHeredadas, List<String> idsBenDetalle, String idAfiliado) {
        List<Object[]> novedadesRegistradas = null;
        List<Object[]> objetos = consultarCategoriasBeneficiarioProcedure(null,null,null,tipoIdAfiliado.toString(),numeroIdAfiliado,
        categoriasHeredadas.getTipoIdentificacion().name(),categoriasHeredadas.getNumeroIdentificacion(),null);
        int contador = 0;
                    Integer tiempoServicios = Integer
            .parseInt((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA));
                    logger.info("**__**Inicia named query  CONSULTAR_HISTORICO_CATEGORIAS_HEREDADAS_AFI_PRINCIPAL");
                    logger.info("**__**tipoIdBeneficiario: " + tipoIdAfiliado.toString());
                    logger.info("**__**numeroIdBeneficiario: " + numeroIdAfiliado);
                    logger.info("**__**parametercategoriasHeredadas TipoIdentificacion(). afiliado: " + categoriasHeredadas.getTipoIdentificacion().name());
                    logger.info("**__**parametercategoriasHeredadas.getNumeroIdentificacion() afiliado: " + categoriasHeredadas.getNumeroIdentificacion());


        if(objetos != null && !objetos.isEmpty()){
                    
            String fechaCambio = null;
            CategoriaPersonaEnum categoria = null;
            TipoAfiliadoEnum tipoAfiliadoEnum = null;
            MotivoCambioCategoriaEnum motivo = null;

            CategoriaPersonaEnum categoriaUVT = null;
            
            LocalDate fechaLimiteServicios = null;
            LocalDate fechaActual = null;
            String[] fechaCambioArray = null;
            String fechaFinServiciosAfi = null;
            String[] fechaFinServiciosAfiCambioArray = null;
            
            Boolean agregar = null;
            logger.info("**__**antes del for objetos");
            for (Object[] obj : objetos) {
                agregar = Boolean.TRUE;
                tipoAfiliadoEnum = TipoAfiliadoEnum.valueOf(obj[1].toString());
                categoria = obj[2] != null ? CategoriaPersonaEnum.valueOf(obj[2].toString()) : null;
                fechaCambio = obj[0] != null ? obj[0].toString() : null;
                motivo = obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null;
                categoriaUVT = obj[4] != null ? CategoriaPersonaEnum.valueOf(obj[4].toString()) : null;
                 fechaFinServiciosAfi = obj[5] != null ? obj[5].toString() : null;
                fechaCambioArray = fechaCambio != null ? fechaCambio.split(" ")[0].split("-") : null;
                fechaFinServiciosAfiCambioArray = fechaFinServiciosAfi != null ? fechaFinServiciosAfi.split(" ")[0].split("-") : null;
                CategoriaDTO cat = new CategoriaDTO();
                if(contador != 0){
                    logger.info("**__**historicoCatHeredadas.get(contador-1).getCategoriaDependiente()  " + historicoCatHeredadas.get(contador-1).getCategoriaDependiente());
                    cat.setCategoriaDependiente(historicoCatHeredadas.get(contador-1).getCategoriaDependiente());
                    cat.setCategoriaIndependiente(historicoCatHeredadas.get(contador-1).getCategoriaIndependiente());
                    cat.setCategoriaPensionado(historicoCatHeredadas.get(contador-1).getCategoriaPensionado());

                    cat.setFechaCambioCategoria(historicoCatHeredadas.get(contador-1).getFechaCambioCategoria());
                    cat.setMotivoCambioCategoria(historicoCatHeredadas.get(contador-1).getMotivoCambioCategoria());
                }
                
                if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))){
                        if (MotivoCambioCategoriaEnum.RETIRO.equals(motivo) && CategoriaPersonaEnum.SIN_CATEGORIA.equals(categoria)
                            && fechaCambioArray != null) {
                          if(fechaFinServiciosAfiCambioArray != null){
                                fechaLimiteServicios = LocalDate.of(Integer.parseInt(fechaFinServiciosAfiCambioArray[0]), 
                                Integer.parseInt(fechaFinServiciosAfiCambioArray[1]),Integer.parseInt(fechaFinServiciosAfiCambioArray[2]));  
                            }else{
                            fechaLimiteServicios = LocalDate.of(Integer.parseInt(fechaCambioArray[0]), Integer.parseInt(fechaCambioArray[1]),
                                Integer.parseInt(fechaCambioArray[2]));
                                fechaLimiteServicios = fechaLimiteServicios.plusDays(tiempoServicios);
                            }
                        fechaActual = LocalDate.now();
                        if (/*!tieneReporteFallemiciento && */fechaActual.compareTo(fechaLimiteServicios) < 0) {
                            agregar = Boolean.FALSE;
                        }
                        if (fechaActual.compareTo(fechaLimiteServicios) < 0) {
                            //agregar = Boolean.FALSE; 
                        // para beneficiario no aplica el paraemtro de ejecucion 24/02/2022
                            agregar = Boolean.TRUE;
                        }

                    }
                    cat.setCategoriaDependiente(obj[2].toString());
                    cat.setTarifaUVTDependiente(obj != null && obj.length > 4 && obj[4] != null ? obj[4].toString() : null);
                        logger.info("**__**setCategoriaDependiente obj[1].toString()  " + obj[2].toString());
                }
                else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))){
                    cat.setCategoriaIndependiente(obj[2].toString());
                    cat.setTarifaUVTIndependiente(obj != null && obj.length > 4 && obj[4] != null ? obj[4].toString() : null);
                        logger.info("**__**cat.setCategoriaIndependiente ,,  " + cat.getCategoriaIndependiente());
                }
                else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))){
                    cat.setCategoriaPensionado(obj[2].toString());
                    cat.setTarifaUVTPensionado(obj != null && obj.length > 4 && obj[4] != null ? obj[4].toString() : null);
                }
                cat.setFechaCambioCategoria(obj[0].toString());
                cat.setMotivoCambioCategoria(obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null);
                    if(MotivoCambioCategoriaEnum.RETIRO_NO_APLICA_SERVICIOS_CAJA.equals(motivo)){
                        cat.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.RETIRO);
                    }
                if (agregar) {
                    historicoCatHeredadas.add(cat);
                    contador++;
                }
                
            }
        }else{
            logger.info("**__**Atencion Objetos viene null " );  
        }

//if(!historicoCatHeredadas.isEmpty()){
//    Collections.reverse(historicoCatHeredadas);
//}
}
  
    /**
     * @param idsBenDetalle
     * @return a
     */
    public List<Object[]> obtenerInfoAfiliadosPrincipales(List<String> idsBenDetalle,Boolean isAfiliadoPrincipal) {
        List<Object[]> infoAfiPrincipal = new ArrayList<>();
        List<Object[]> infoAfiPrincipalTemp = new ArrayList<>();
        if(!isAfiliadoPrincipal && idsBenDetalle != null && !idsBenDetalle.isEmpty()){
       
         //   //busca la info de los afiliados principales del afiliado como beneficiario
         //        logger.info("**__**Inicia consulta para afiliados secundarios ");
         //     infoAfiPrincipal = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO)
         //             .setParameter("idsBenDetalle", idsBenDetalle)
         //             .getResultList(); 
         //   if(infoAfiPrincipal == null || infoAfiPrincipal.isEmpty()){
         //     
         //       logger.info("**__**No vienen datos para afiliados inactivos se consultaran los activos y se tomara el segundo afiliado principal "+idsBenDetalle);
         //           //ESTADO ativos y trae pociion 1 porque es el segundario
         //       infoAfiPrincipalTemp = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO_ACTIVOS)
         //       .setParameter("idsBenDetalle", idsBenDetalle)
         //       .getResultList(); 
//
         //        System.out.println("**********************************************************"); 
         //       if(infoAfiPrincipalTemp.size() > 1){
         //           int ciclos = 1;
         //               for (Object[] obj : infoAfiPrincipalTemp) {
         //                   if(ciclos>1){
         //                        logger.info("**__**infoAfiPrincipal Ciclo-"+ciclos);
         //                       if(obj != null){
         //                        logger.info("**__**infoAfiPrincipal obj 1-"+obj[0].toString());
         //                       logger.info("**__**infoAfiPrincipal obj 11-"+obj[1].toString());
         //                         logger.info("**__**infoAfiPrincipal obj 12-"+obj[2].toString());
         //                         infoAfiPrincipal = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO_ACTIVOS_ID_AFILIADO_SEC)
         //                           .setParameter("idsBenDetalle", idsBenDetalle)
         //                           .setParameter("idAfiliadoSecundario",Long.valueOf(obj[0].toString()))
         //                           .getResultList(); 
         //                     logger.info("**__**infoAfiPrincipal obj 1-"+obj[1].toString());
         //                       }
         //                   }
         //                ciclos++;
         //               }
         //           }else{
         //               //aca no se pone porque si llega aqui es porque solo hay un afiliado principal
         //          // infoAfiPrincipal = infoAfiPrincipalTemp;
         //           }
         //   } 
      //   List<String> listIdsBenDetalle = new ArrayList<>();
      //   listIdsBenDetalle = idsBenDetalle;
      //    logger.info("**__**Lista idsBenDetalle pocision 0: " );
      //Long grandChildCount = Long.valueOf(listIdsBenDetalle.get(0));
      //       System.out.println(grandChildCount);
      String idBeneficiarioDetalleU =  String.valueOf(idsBenDetalle.get(0));
             System.out.println(idBeneficiarioDetalleU);
            try {
                 logger.info(" :: -->Ingresa a sp 1: " );
                StoredProcedureQuery query = entityManagerCore
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_CATEGORIAS_HEREDADAS);

                query.setParameter("idsBenDetalle", Long.valueOf(idBeneficiarioDetalleU));
               query.setParameter("isAfiliadoPrincipal", isAfiliadoPrincipal);
                //	query.execute();

                //result = (Date) query.getOutputParameterValue("dFechaHabil");
                infoAfiPrincipal = query.getResultList();
            } catch (Exception e) {
                logger.info(" :: -->Hubo un error en el SP: " + e);
            }
        }else{
        if(idsBenDetalle != null && !idsBenDetalle.isEmpty()){
            //busca la info de los afiliados principales del afiliado como beneficiario
             //infoAfiPrincipal = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET)
             //        .setParameter("idsBenDetalle", idsBenDetalle)
             //        .getResultList();
                 logger.info(" :: -->Ingresa a sp 2: " );
               String idBeneficiarioDetalleU =  String.valueOf(idsBenDetalle.get(0));
                  StoredProcedureQuery query = entityManagerCore
                        .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_CATEGORIAS_HEREDADAS);
                query.setParameter("idsBenDetalle", Long.valueOf(idBeneficiarioDetalleU));
                query.setParameter("isAfiliadoPrincipal", isAfiliadoPrincipal);
                //query.setParameter("numeroIdentificacion", numeroIdentificacion);
                //	query.execute();

                //result = (Date) query.getOutputParameterValue("dFechaHabil");
                infoAfiPrincipal = query.getResultList();
          }
    }
        return infoAfiPrincipal;
    }
    
    /**
     * @param categoriasHeredadas
     * @param infoAfiPrincipal
     * @param afiliadosPpales
     */
    private void obtenerCategoriaAfiliadoPrincipal(CategoriasComoBeneficiarioDTO categoriasHeredadas, List<Object[]> infoAfiPrincipal,
            List<List<String>> afiliadosPpales, Boolean isAfiliadoPrincipal) {
        int posicionAfiliado = 0;
        if(!isAfiliadoPrincipal){
            Collections.reverse(afiliadosPpales);
        }
        
        for (int i = 0; i < afiliadosPpales.size(); i++) {
            if(isAfiliadoPrincipal && GeneroEnum.FEMENINO.name().equals(afiliadosPpales.get(i).get(1))){
                posicionAfiliado = i;
                break;
            }
            else if(!isAfiliadoPrincipal && GeneroEnum.MASCULINO.name().equals(afiliadosPpales.get(i).get(1))){
                posicionAfiliado = i;
                break;
            }
        }
logger.info("**__**Afiliado  obtenerCategoriaAfiliadoPrincipal ");
        for (Object[] infoAfiPpal : infoAfiPrincipal) {
            logger.info("**__**Afiliado  obtenerCategoriaAfiliadoPrincipal 1 ");
            if(Long.valueOf(afiliadosPpales.get(posicionAfiliado).get(0)).equals(Long.valueOf(infoAfiPpal[0].toString()))){
                logger.info("**__**Afiliado  obtenerCategoriaAfiliadoPrincipal 2 ");
   
                   categoriasHeredadas.setTipoIdentificacion((categoriasHeredadas.getTipoIdentificacion() == null && infoAfiPpal[1] != null) ? TipoIdentificacionEnum.valueOf(infoAfiPpal[1].toString()) : categoriasHeredadas.getTipoIdentificacion());
                   categoriasHeredadas.setNumeroIdentificacion((categoriasHeredadas.getNumeroIdentificacion() == null && infoAfiPpal[2] != null) ? infoAfiPpal[2].toString() : categoriasHeredadas.getNumeroIdentificacion());
                   categoriasHeredadas.setEstadoActualAfiliado((categoriasHeredadas.getEstadoActualAfiliado() == null && infoAfiPpal[3] != null) ? EstadoAfiliadoEnum.valueOf(infoAfiPpal[3].toString()) : categoriasHeredadas.getEstadoActualAfiliado());
                   categoriasHeredadas.setClasificacion((categoriasHeredadas.getClasificacion() == null && infoAfiPpal[4] != null) ? ClasificacionEnum.valueOf(infoAfiPpal[4].toString()) : categoriasHeredadas.getClasificacion());
                   categoriasHeredadas.setParentezco((categoriasHeredadas.getParentezco() == null && infoAfiPpal[4] != null) ? TipoBeneficiarioEnum.valueOf(ClasificacionEnum.valueOf(infoAfiPpal[4].toString()).getSujetoTramite().getName()) : categoriasHeredadas.getParentezco());
                   categoriasHeredadas.setNombres((categoriasHeredadas.getNombres() == null && infoAfiPpal[7] != null) ? infoAfiPpal[7].toString() : categoriasHeredadas.getNombres());
                   if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(infoAfiPpal[5].toString()))){
                       categoriasHeredadas.setCategoriaPensionado(infoAfiPpal[6] != null ? infoAfiPpal[6].toString() : null);
                   }
                   else if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(infoAfiPpal[5].toString()))){
                       categoriasHeredadas.setCategoriaDependiente(infoAfiPpal[6] != null ? infoAfiPpal[6].toString() : null);
                   }
                   else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(infoAfiPpal[5].toString()))){
                       categoriasHeredadas.setCategoriaIndependiente(infoAfiPpal[6] != null ? infoAfiPpal[6].toString() : null);
                   }
               }
           }
    }
    
/*Metodo creado - sera dinamico para las tablas de categorias beneficiario . dentro del sp esta contemplada la logia de categorias */
@Override
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public List<CategoriaDTO> consultarCategoriaBeneficiarioStoredProcedure(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario,
        TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle, List<BigInteger> idAfiliadoSecundario,
        String idAfiliadoConyuge) {
            logger.info("Inicia consultarCategoriaBeneficiarioStoredProcedure" );
            try {
                List<CategoriaDTO> categorias = new ArrayList<>();
                List<Object[]> objetos = consultarCategoriasBeneficiarioProcedure(null,idAfiliado,null,tipoIdBeneficiario.name(),numeroIdBeneficiario,
                null,null,tipoBeneficiario.name());

                if (objetos != null && !objetos.isEmpty()) {
                   for (Object[] obj : objetos) {
                        CategoriaDTO categoriaSP = new CategoriaDTO();
                        categoriaSP.setRespectoAfiliadoPrincipal(true);
                        categoriaSP.setFechaCambioCategoria(obj[0] != null ? obj[0].toString() : null);
                       if (TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))) {
                        categoriaSP.setCategoriaPensionado(obj[2] != null ? obj[2].toString() : null);
                        categoriaSP.setTarifaUVTPensionado(obj[4] != null ? obj[4].toString() : null);
                       }
                       else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))) {
                        categoriaSP.setCategoriaDependiente(obj[2] != null ? obj[2].toString() : null);
                        categoriaSP.setTarifaUVTDependiente(obj[4] != null ? obj[4].toString() : null);
                       }
                       else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[1].toString()))) {
                        categoriaSP.setCategoriaIndependiente(obj[2] != null ? obj[2].toString() : null);
                        categoriaSP.setTarifaUVTIndependiente(obj[4] != null ? obj[4].toString() : null);
                       }
                       
                       categoriaSP.setMotivoCambioCategoria(obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null);
                  categorias.add(categoriaSP);
                   }
                    
               }
               return categorias;
            } catch (Exception e) {
                logger.error("Error en el servicio consultarCategoriaBeneficiarioStoredProcedure", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
}


    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#consultarCategoriaBeneficiario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum, java.lang.Long, java.lang.Long, java.util.List, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CategoriaDTO> consultarCategoriaBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario,
            TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle, List<BigInteger> idAfiliadoSecundario,
            String idAfiliadoConyuge) {
        String firma = "consultarCategoriaBeneficiario(TipoIdentificacionEnum, String, TipoBeneficiarioEnum, Long)";
        logger.debug("Inicia servicio-------------------------------------------------------- " + firma);
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            //si el beneficiario es tipo PADRES solo se devuelven las categorias heredadas del afiliado principal
             // if (TipoBeneficiarioEnum.PADRES.equals(tipoBeneficiario)) {
             // return categorias;
             // } 

                       /**para categorias beneficiario propias de retiro */
           logger.info("**__**CATEGORIA INICIA PARA BENEFICIARIO " );
            logger.info("**__**CATEGORIA tipoIdBeneficiario: "+tipoIdBeneficiario );
            logger.info("**__**CATEGORIA InumeroIdBeneficiario "+numeroIdBeneficiario );
             logger.info("**__**CATEGORIA idAfiliadoP "+idAfiliado );
                    List<Object[]> categoriaPropiasBeneficiarioSQL = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_PROPIA_BENEFICIARIO_INACTIVO)
                            .setParameter("tipoBeneficiario", tipoIdBeneficiario.name()) 
                            .setParameter("identificacionBenefciciario", numeroIdBeneficiario.toString())
                            .setParameter("idAfiliadoP", idAfiliado)
                            .getResultList();
logger.info("**__**categoriaPropiasBeneficiarioSQL" +categoriaPropiasBeneficiarioSQL);
                   
                    if (categoriaPropiasBeneficiarioSQL != null && !categoriaPropiasBeneficiarioSQL.isEmpty()) {
                         logger.info("**__**CATEGORIA antes del for" );
                        for (Object[] obj : categoriaPropiasBeneficiarioSQL) {
                             CategoriaDTO categoriaPropiasBeneficiario = new CategoriaDTO();
                            categoriaPropiasBeneficiario.setRespectoAfiliadoPrincipal(true);
                             logger.info("**__**CATEGORIA FOR DE CATEGORIAS" );
                            if (TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaPropiasBeneficiario.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                                categoriaPropiasBeneficiario.setTarifaUVTPensionado(obj[4] != null ? obj[4].toString() : null);
                            }
                            else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaPropiasBeneficiario.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                                categoriaPropiasBeneficiario.setTarifaUVTDependiente(obj[4] != null ? obj[4].toString() : null);
                            }
                            else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaPropiasBeneficiario.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                                categoriaPropiasBeneficiario.setTarifaUVTIndependiente(obj[4] != null ? obj[4].toString() : null);
                            }
                             categoriaPropiasBeneficiario.setFechaCambioCategoria(obj[2] != null ? obj[2].toString() : null);
                            categoriaPropiasBeneficiario.setMotivoCambioCategoria(obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null);
                        logger.info("**__**CATEGORIA setCategoriaDependiente "+obj[1].toString() );
                        logger.info("**__**CATEGORIA setCategoriaDependiente motivo "+obj[3].toString() +" motivintan"+categoriaPropiasBeneficiario.getMotivoCambioCategoria());
                       categorias.add(categoriaPropiasBeneficiario);
                        }
                        // logger.info("**__**CATEGORIA LETRA: "+ categoriaPropiasBeneficiario.getCategoriaPensionado() );
                         
                    }
          
            /**fin categorias propias del beneficiario */
            //si el beneficiario es tipo CONYUGE adicionalmente a las categorias heredadas del afiliado principal 
            //se obtienen las categorias propias
            else if (TipoBeneficiarioEnum.CONYUGE.equals(tipoBeneficiario) && idAfiliadoConyuge != null) {

                List<Object[]> categoriasPropias = (List<Object[]>) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_PROPIA)
                        .setParameter("idAfiliado", idAfiliadoConyuge).getResultList();

                CategoriaDTO categoriaPropia = new CategoriaDTO();
                if (categoriasPropias != null && !categoriasPropias.isEmpty()) {
                     categoriaPropia.setRespectoAfiliadoPrincipal(false);
                    for (Object[] obj : categoriasPropias) {
                        if (TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                            categoriaPropia.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                        }
                        else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                            categoriaPropia.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                        }
                        else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                            categoriaPropia.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                        }
                    }

                    List<Object[]> fechaMotivoCambioCatPropia = (List<Object[]>) entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA_TIPO_NUM_ID)
                            .setParameter("idAfiliado", idAfiliadoConyuge).getResultList();

                    if (fechaMotivoCambioCatPropia != null && !fechaMotivoCambioCatPropia.isEmpty()) {
                        for (Object[] motivoYfecha : fechaMotivoCambioCatPropia) {
                            categoriaPropia.setFechaCambioCategoria(motivoYfecha[0] != null ? motivoYfecha[0].toString() : null);
                            categoriaPropia.setMotivoCambioCategoria(
                                    motivoYfecha[1] != null ? MotivoCambioCategoriaEnum.valueOf(motivoYfecha[1].toString()) : null);
                            break;
                        }
                    }
                     categorias.add(categoriaPropia);
                }

                return categorias;
            }
            //si el beneficiario es tipo HIJO adicionalmente a las categorias heredadas del afiliado principal 
            //se obtienen las categorias heredadas del afiliado secundario
            else if (TipoBeneficiarioEnum.HIJO.equals(tipoBeneficiario)) {

                //                List<BigInteger> idAfiliadoSecundario = (List<BigInteger>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_AFILIADO_SECUNDARIO)
                //                        .setParameter("tipoIdBeneficiario", tipoIdBeneficiario.name())
                //                        .setParameter("numeroIdBeneficiario", numeroIdBeneficiario)
                //                        .setParameter("idAfiliado", idAfiliado)
                //                        .getResultList();
//revision vista 30304868
                if (idAfiliadoSecundario != null && !idAfiliadoSecundario.isEmpty()) {
  logger.info("**__**idAfiliadoSecundario.get(0).toString() HIJOHIJO " + idAfiliadoSecundario.get(0).toString());
    logger.info("**__**idBenDet.get(0).toString() HIJOHIJO " + idBenDetalle);
                    List<Object[]> categoriasRespectoAfiSecundario = (List<Object[]>) entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_BENEFICIARIO_RESPECTO_AFI_SEC)
                            .setParameter("idBenDet", idBenDetalle)
                            .setParameter("idAfiliadoSecundario", idAfiliadoSecundario.get(0).toString()).getResultList();

                    CategoriaDTO categoriaHeredadasAfiSecundario = new CategoriaDTO();
                    if (categoriasRespectoAfiSecundario != null && !categoriasRespectoAfiSecundario.isEmpty()) {
                         categoriaHeredadasAfiSecundario.setRespectoAfiliadoPrincipal(false);
                        for (Object[] obj : categoriasRespectoAfiSecundario) {
                            if (TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaHeredadasAfiSecundario.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                            }
                            else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaHeredadasAfiSecundario.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                            }
                            else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))) {
                                categoriaHeredadasAfiSecundario.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                            }
                        }
  logger.info("**__**idAfiliadoSecundario.get(0).toString() " + idAfiliadoSecundario.get(0).toString());
                        List<Object[]> fechaMotivoCambioCatAfiSec = (List<Object[]>) entityManagerCore
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA)
                                .setParameter("idAfiliado", idAfiliadoSecundario.get(0).toString()).getResultList();

                        if (fechaMotivoCambioCatAfiSec != null && !fechaMotivoCambioCatAfiSec.isEmpty()) {
                            for (Object[] motivoYfecha : fechaMotivoCambioCatAfiSec) {
                                categoriaHeredadasAfiSecundario
                                        .setFechaCambioCategoria(motivoYfecha[0] != null ? motivoYfecha[0].toString() : null);
                                categoriaHeredadasAfiSecundario.setMotivoCambioCategoria(
                                        motivoYfecha[1] != null ? MotivoCambioCategoriaEnum.valueOf(motivoYfecha[1].toString()) : null);
                                break;
                            }
                        }
                    categorias.add(categoriaHeredadasAfiSecundario);
                
                    }
                     return categorias;
                }
            }
                for (CategoriaDTO cat :categorias){
             logger.info("**__*ffffCATEGORIA setCategoriaDependiente "+cat.getCategoriaDependiente() );
            logger.info("**__**ffffATEGORIA setCategoriaDependiente motivo "+cat.getMotivoCambioCategoria() );
          }
            logger.info("Finaliza servicio " + firma);
            return categorias;
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
	/* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#consultarEstadoAfiliadoFecha(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarEstadoAfiliadoFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacionEmpleador, String numeroIdentificacionEmpleador, Long fechaMillis){
        try {
        	logger.debug("Inicia servicio consultarEstadoAfiliadoFecha");
        	
        	if(fechaMillis==null || tipoAfiliado == null){
        		logger.debug("Finaliza servicio consultarEstadoAfiliadoFecha");
        		return null;
        	}
        	
        	Date fecha = new Date(fechaMillis);
        	SimpleDateFormat formatoFecha = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        	String fechaFormateada = formatoFecha.format(fecha);
        	
        	
        	List<Object[]> lista = (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFILIACION_PERSONA_CORE)
            .setParameter(ConstantesParametrosSp.TIPO_IDENTIFICACION, tipoIdentificacion.name())
            .setParameter(ConstantesParametrosSp.NUMERO_IDENTIFICACION, numeroIdentificacion)
            .setParameter(ConstantesParametrosSp.TIPO_AFILIADO, tipoAfiliado.name())
            .setParameter(ConstantesParametrosSp.TIPO_IDENTIFICACION_EMPLEADOR, tipoIdentificacionEmpleador!=null && !tipoIdentificacionEmpleador.name().trim().isEmpty() ? tipoIdentificacionEmpleador.name() : "")
            .setParameter(ConstantesParametrosSp.NUMERO_IDENTIFICACION_EMPLEADOR, numeroIdentificacionEmpleador!=null && !numeroIdentificacionEmpleador.trim().isEmpty() ? numeroIdentificacionEmpleador : "")
            .setParameter(ConstantesParametrosSp.FECHA_CONSULTA, fechaFormateada)
            .getResultList();
            
        	
        	if(lista != null && !lista.isEmpty() && lista.get(0)[0] != null){
        		logger.debug("Finaliza servicio consultarEstadoAfiliadoFecha");
        		return lista.get(0)[0].toString();
        	}
        	else {
        		logger.debug("Finaliza servicio consultarEstadoAfiliadoFecha");
                return "SIN_INFORMACION";
        	}
            
        } catch (Exception e) {
            logger.error("Error inesperado en consultarEstadoAfiliadoFecha", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#verificarReporteNormativo(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.reportes.ReporteNormativoEnum)
     */
    @Override
    public FichaControlDTO consultarValoresPrecargadosFichaControl(ReporteNormativoEnum reporteNormativo) {
        String firmaServicio = "ConsultasModeloCore.verificarReporteNormativo(Long,Long,ReporteNormativoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        FichaControlDTO fichaControlDTO = new FichaControlDTO(); 
        try {
        	fichaControlDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PRECARGADOS_FICHA_CONTROL,
                    		FichaControlDTO.class)
                    .setParameter("nombre", reporteNormativo.name())
                   .getSingleResult();      

        } catch (NoResultException e) {}       

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return fichaControlDTO;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#verificarReporteNormativo(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.reportes.ReporteNormativoEnum)
     */
    @Override
    public String consultarParametro(String nombreParametro) {
        String firmaServicio = "ConsultasModeloCore.consultarParametro(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
 
        String valor = null; 
       // try {
        	valor = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRO,
                    		String.class)
                    .setParameter("nombreParametro", nombreParametro)
                   .getSingleResult();      

        //} catch (Exception e) {}       

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return valor;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#verificarReporteNormativo(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.reportes.ReporteNormativoEnum)
     */
    @Override
    public DatosFichaControl consultarValoresPrecargadosFichaControlEntidad(ReporteNormativoEnum reporteNormativo) {
        String firmaServicio = "ConsultasModeloCore.verificarReporteNormativo(Long,Long,ReporteNormativoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DatosFichaControl datosFichaControl = new DatosFichaControl(); 
        try {
        	datosFichaControl = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PRECARGADOS_FICHA_CONTROL_ENTIDAD,
                    		DatosFichaControl.class)
                    .setParameter("nombre", reporteNormativo.name())
                   .getSingleResult();      

        } catch (NoResultException e) {}       

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return datosFichaControl;
    }
    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#guardarReporteNormativo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public void guardarDatosFichaControl(DatosFichaControl datosFichaControl) {
        String firmaServicio = "ConsultasModeloCore.guardarDatosFichaControl(DatosFichaControl)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);       
        try {
            entityManagerCore.persist(datosFichaControl);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + " error al guardar DatosFichaControl",e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloCore#consultarCategoriasPropiasAfiliadoBeneficiario(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<CategoriaDTO> consultarCategoriasPropiasAfiliadoBeneficiario(Long idAfiliado, Long idBenDetalle) {
        String firma = "consultarCategoriasPropiasAfiliadoBeneficiario(Long idAfiliado, Long idBenDetalle)";
        logger.debug("Inicia servicio " + firma);
   logger.info("**__**consultarCategoriasPropiasAfiliadoBeneficiario " +idAfiliado +" idBenDetalle: "+idBenDetalle);
        List<Object[]> objetos = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_CATEGORIAS_AFILIADO_BENEFICIARIO)
                .setParameter("idBenDetalle", idBenDetalle).setParameter("idAfiliado", idAfiliado).getResultList();
        
        List<CategoriaDTO> historicoCategorias = new ArrayList<>();
        int contador = 0;
        
        // se consulta el parámetro que indica los días de gracia para mantener la categoría luego del retiro
        Integer tiempoServicios = Integer
                .parseInt((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA));


        if (objetos != null && !objetos.isEmpty()) {
            String fechaCambio = null;
            CategoriaPersonaEnum categoria = null;
            TipoAfiliadoEnum tipoAfiliadoEnum = null;
            MotivoCambioCategoriaEnum motivo = null;
            CategoriaPersonaEnum tarifaUVT = null;
            
            LocalDate fechaLimiteServicios = null;
            LocalDate fechaActual = null;
            String[] fechaCambioArray = null;
            
         
            for (Object[] obj : objetos) {
                CategoriaDTO cat = new CategoriaDTO();
                tipoAfiliadoEnum = TipoAfiliadoEnum.valueOf(obj[0].toString());
                categoria = obj[1] != null ? CategoriaPersonaEnum.valueOf(obj[1].toString()) : null;
                fechaCambio = obj[2] != null ? obj[2].toString() : null;
                motivo = obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null;
                fechaCambioArray = fechaCambio != null ? fechaCambio.split(" ")[0].split("-") : null;
                tarifaUVT = obj[4] != null ? CategoriaPersonaEnum.valueOf(obj[4].toString()) : null;

                if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliadoEnum)) {
                    /*
                     * se calcula la fecha límite de servicios por parámetro, para los casos en los que se obtiene la
                     * categoría "Sin Categoría" por motivo de retiro
                     */
                        //comnetado de acuerdo a GLPI 50531 14/12/2021
                     
                    if (MotivoCambioCategoriaEnum.RETIRO.equals(motivo) && CategoriaPersonaEnum.SIN_CATEGORIA.equals(categoria)
                            && fechaCambioArray != null && CategoriaPersonaEnum.SIN_TARIFA.equals(tarifaUVT)) {
                        fechaLimiteServicios = LocalDate.of(Integer.parseInt(fechaCambioArray[0]), Integer.parseInt(fechaCambioArray[1]),
                                Integer.parseInt(fechaCambioArray[2]));

                        fechaLimiteServicios = fechaLimiteServicios.plusDays(tiempoServicios);
                        fechaActual = LocalDate.now();

                       if (fechaActual.compareTo(fechaLimiteServicios) < 0) {
                         //tambien aplica el parametro TIEMPO_ADICIONAL_SERVICIOS_CAJA para beneficiario
                            continue;
                        }
                    }
                    cat.setCategoriaDependiente(categoria.name());
                    cat.setTarifaUVTDependiente(tarifaUVT != null ? tarifaUVT.name() : null);
                }
                else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliadoEnum)) {
                    cat.setCategoriaIndependiente(categoria.name());
                    cat.setTarifaUVTIndependiente(tarifaUVT != null ? tarifaUVT.name() : null);
                }
                else if (TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliadoEnum)) {
                    cat.setCategoriaPensionado(categoria.name());
                    cat.setTarifaUVTPensionado(tarifaUVT != null ? tarifaUVT.name() : null);
                }
                cat.setFechaCambioCategoria(fechaCambio);
                cat.setMotivoCambioCategoria(motivo);
                cat.setRespectoAfiliadoPrincipal(true);
                System.out.println("**__**categoria ->: " + categoria.name());
                    historicoCategorias.add(cat);
            }
        }
        logger.debug("Finaliza servicio " + firma);
        return historicoCategorias;
    }
    public List<Object[]> consultarCategoriasBeneficiarioProcedure(Long idBeneficiario,Long idafiliado,Long idBeneficiarioDetalle,
    String tipoIdentificacionBeneficiario, String identificacionBeneficiario,String tipoIdentificacionAfiliado,
    String identificacionAfiliado,String tipoBeneficiario) {
        List<Object[]> objetos = null;
        try {
            StoredProcedureQuery query = entityManagerCore
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_CATEGORIAS_BENEFICIARIO);
            query.setParameter("idBeneficiario", idBeneficiario  !=null ? idBeneficiario : 0L);
            query.setParameter("idafiliado", idafiliado  !=null ? idafiliado : 0L);
            query.setParameter("idBeneficiarioDetalle", idBeneficiarioDetalle  !=null ? idBeneficiarioDetalle : 0L);
            query.setParameter("tipoIdentificacionBeneficiario", tipoIdentificacionBeneficiario  !=null ? tipoIdentificacionBeneficiario : "");
            query.setParameter("identificacionBeneficiario", identificacionBeneficiario  !=null ? identificacionBeneficiario : "");
            query.setParameter("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado  !=null ? tipoIdentificacionAfiliado : "");
            query.setParameter("identificacionAfiliado", identificacionAfiliado  !=null ? identificacionAfiliado : "");
            
            objetos = query.getResultList();
        } catch (Exception e) {
            logger.info(" :: Hubo un error en el SP obtenerHistoricoCategoriasRespectoAfiliado: : "+e +" idBeneficiarioDetalle:"+idBeneficiarioDetalle);
        }
        return objetos;
    }
}
