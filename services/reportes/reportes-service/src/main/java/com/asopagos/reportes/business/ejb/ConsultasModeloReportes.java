package com.asopagos.reportes.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;	
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.business.interfaces.IConsultasModeloReportes;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.constants.NamedStoredProcedureConstants;
import com.asopagos.reportes.dto.CategoriaAfiliadoDTO;
import com.asopagos.reportes.dto.CategoriaBeneficiarioDTO;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.reportes.dto.DatosParametrizacionMetaDTO;
import com.asopagos.reportes.dto.DetalleBeneficiarioDTO;
import com.asopagos.reportes.dto.ParametrizacionMetaDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos de Reportes <br/>
 * <b>Módulo:</b> Asopagos - HU-KPI <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

@Stateless
public class ConsultasModeloReportes implements IConsultasModeloReportes, Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 6909763347730963359L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);
    
    /**
     * Estado sin información
     */
    private static final String SIN_INFORMACION = "SIN_INFORMACION"; 

    /** Entity Manager */
    @PersistenceContext(unitName = "reportes_PU")
    private EntityManager entityManagerReportes;

    @SuppressWarnings("unchecked")
    @Override
    public List<DatosParametrizacionMetaDTO> consultarParametrizacionMeta(MetaEnum meta, PeriodicidadMetaEnum periodicidad, String periodo,
            FrecuenciaMetaEnum frecuencia) {
        logger.debug("Inicia servicio consultarParametrizacionMeta(ParametrizacionMetaDTO)");
        //try {
            List<DatosParametrizacionMetaDTO> valoresParametrizacion = new ArrayList<>();

            StoredProcedureQuery sQuery = entityManagerReportes
                    .createStoredProcedureQuery(NamedStoredProcedureConstants.CONSULTAR_PARAMETRIZACION_META);
            sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.META, String.class, ParameterMode.IN);
            sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.PERIODICIDAD, String.class, ParameterMode.IN);
            sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.PERIODO, String.class, ParameterMode.IN);
            sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.FRECUENCIA, String.class, ParameterMode.IN);
            sQuery.setParameter(ConstantesParametrosSp.META, meta.name());
            sQuery.setParameter(ConstantesParametrosSp.PERIODICIDAD, periodicidad.name());
            sQuery.setParameter(ConstantesParametrosSp.PERIODO, periodo);
            sQuery.setParameter(ConstantesParametrosSp.FRECUENCIA, frecuencia.name());

            sQuery.execute();
            List<Object[]> datosPersonaInconsistente = sQuery.getResultList();

            if (datosPersonaInconsistente != null && !datosPersonaInconsistente.isEmpty()) {
                for (Object[] dato : datosPersonaInconsistente) {
                    DatosParametrizacionMetaDTO datosParametrizacionMetaDTO = new DatosParametrizacionMetaDTO(
                            dato[0] != null ? CanalRecepcionEnum.valueOf((String) dato[0]) : null, (String) dato[1], (Byte) dato[2],
                            (Integer) dato[3]);
                    valoresParametrizacion.add(datosParametrizacionMetaDTO);
                }
            }

            logger.debug("Finaliza servicio consultarParametrizacionMeta(ParametrizacionMetaDTO)");
            return valoresParametrizacion;
         /*   
        } catch (Exception e) {
            logger.error("Error inesperado en consultarParametrizacionMeta(ParametrizacionMetaDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
    }

    @Override
    public void actualizarParametrizacionMeta(ParametrizacionMetaDTO parametrizacionMetasDTO) {
        logger.debug("Inicia servicio actualizarParametrizacionMeta(ParametrizacionMetaDTO)");
        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(parametrizacionMetasDTO);

            StoredProcedureQuery sQuery = entityManagerReportes
                    .createStoredProcedureQuery(NamedStoredProcedureConstants.ACTUALIZAR_PARAMETRIZACION_META);
            sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.VALORES_PARAMETRIZACION_META, String.class, ParameterMode.IN);
            sQuery.setParameter(ConstantesParametrosSp.VALORES_PARAMETRIZACION_META, jsonPayload);
            sQuery.execute();

            logger.debug("Finaliza servicio actualizarParametrizacionMeta(ParametrizacionMetaDTO)");
        } catch (Exception e) {
            logger.error("Error inesperado en actualizarParametrizacionMeta(ParametrizacionMetaDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#consultarEstadoAportanteFecha(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String consultarEstadoAportanteFecha(Long idPersona, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante, Long fechaMillis){
        try {
        	logger.debug("Inicia servicio consultarEstadoAportanteFecha");
        	
        	if(fechaMillis==null){
        		return null;
        	}
        	
        	Date fecha = new Date(fechaMillis);
        	StoredProcedureQuery query = entityManagerReportes
                    .createNamedStoredProcedureQuery(NamedStoredProcedureConstants.CONSULTAR_HISTORICO_ESTADO_APORTANTE);
            query.setParameter(ConstantesParametrosSp.ID_PERSONA,idPersona);
            query.setParameter(ConstantesParametrosSp.TIPO_APORTANTE, tipoAportante.name());
            query.execute();
            List<Object[]> lista = (List<Object[]>) query.getResultList();
            Date fechaFinal = new Date();

            for(Object[] registro : lista){
            	Date fechaInicial = (Date) registro[1];
            	
            	if(fecha.after(fechaInicial) && fecha.before(fechaFinal)){
            		logger.debug("Finaliza servicio consultarEstadoAportanteFecha");
            		return registro[0].toString();
            	}
            	
            	fechaFinal = fechaInicial;
            }
            
            return SIN_INFORMACION;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarEstadoAportanteFecha", e);
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
        	
        	if(fechaMillis==null){
        		return null;
        	}
        	
        	Date fecha = new Date(fechaMillis);
        	StoredProcedureQuery query = entityManagerReportes
                    .createNamedStoredProcedureQuery(NamedStoredProcedureConstants.CONSULTAR_HISTORICO_AFILIACION_PERSONA);
            query.setParameter(ConstantesParametrosSp.TIPO_IDENTIFICACION, tipoIdentificacion.name());
            query.setParameter(ConstantesParametrosSp.NUMERO_IDENTIFICACION, numeroIdentificacion);
            query.setParameter(ConstantesParametrosSp.TIPO_AFILIADO, tipoAfiliado.name());
            query.setParameter(ConstantesParametrosSp.TIPO_IDENTIFICACION_EMPLEADOR, tipoIdentificacionEmpleador!=null && !tipoIdentificacionEmpleador.name().trim().isEmpty() ? tipoIdentificacionEmpleador.name() : "");
            query.setParameter(ConstantesParametrosSp.NUMERO_IDENTIFICACION_EMPLEADOR, numeroIdentificacionEmpleador!=null && !numeroIdentificacionEmpleador.trim().isEmpty() ? numeroIdentificacionEmpleador : "");
            query.execute();            
            List<Object[]> lista = (List<Object[]>) query.getResultList();
            Date fechaFinal = new Date();

            for(Object[] registro : lista){
            	Date fechaInicial = (Date) registro[1];
            	
            	if(fechaInicial!=null && fechaFinal!=null && fecha.after(fechaInicial) && fecha.before(fechaFinal)){
            		logger.debug("Finaliza servicio consultarEstadoAportanteFecha");
            		return registro[0].toString();
            	}
            	
            	fechaFinal = fechaInicial;
            }

            logger.debug("Finaliza servicio consultarEstadoAfiliadoFecha");
            return SIN_INFORMACION;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarEstadoAfiliadoFecha", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#consultarCategoriaBeneficiario(java.lang.Long, com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CategoriaDTO> consultarCategoriaBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario, TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle, List<BigInteger> idAfiliadoSecundario, String idAfiliadoConyuge) {
        String firma = "consultarCategoriaBeneficiario(TipoIdentificacionEnum, String, TipoBeneficiarioEnum, Long)";
        logger.debug("Inicia servicio " + firma);
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            
            List<Object[]> categoriasHeredadasAfiPpal = (List<Object[]>) entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_BENEFICIARIO_RESPECTO_AFI_PPAL)
                    .setParameter("idBenDet", idBenDetalle)
                    .setParameter("idAfiliado", idAfiliado)
                    .getResultList();
//                    .setParameter("tipoIdBeneficiario", tipoIdBeneficiario.name())
//                    .setParameter("numeroIdBeneficiario", numeroIdBeneficiario)
//                    .setParameter("idAfiliado", idAfiliado)
//                    .getResultList();
            
            CategoriaDTO categoriaRespectoAfiPpal = new CategoriaDTO();
            if(categoriasHeredadasAfiPpal != null && !categoriasHeredadasAfiPpal.isEmpty()){
                for (Object[] obj : categoriasHeredadasAfiPpal) {
                    if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                        categoriaRespectoAfiPpal.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                    }
                    else if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                        categoriaRespectoAfiPpal.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                    }
                    else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                        categoriaRespectoAfiPpal.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                    }
                }
                
                List<Object[]> fechaMotivoCambioCatAfiPpal = (List<Object[]>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA)
                        .setParameter("idAfiliado", idAfiliado)
                        .getResultList();
                
                if(fechaMotivoCambioCatAfiPpal != null && !fechaMotivoCambioCatAfiPpal.isEmpty()){
                    for (Object[] motivoYfecha : fechaMotivoCambioCatAfiPpal) {
                        categoriaRespectoAfiPpal.setFechaCambioCategoria(motivoYfecha[0] != null ? motivoYfecha[0].toString() : null);
                        categoriaRespectoAfiPpal.setMotivoCambioCategoria(motivoYfecha[1] != null ? MotivoCambioCategoriaEnum.valueOf(motivoYfecha[1].toString()) : null);
                        break;
                    }
                }
            }
            
            categorias.add(categoriaRespectoAfiPpal);
            
            //si el beneficiario es tipo PADRES solo se devuelven las categorias heredadas del afiliado principal
            if(TipoBeneficiarioEnum.PADRES.equals(tipoBeneficiario)){
                return categorias;
            }
            //si el beneficiario es tipo CONYUGE adicionalmente a las categorias heredadas del afiliado principal 
            //se obtienen las categorias propias
            else if(TipoBeneficiarioEnum.CONYUGE.equals(tipoBeneficiario) && idAfiliadoConyuge != null){
                
                List<Object[]> categoriasPropias = (List<Object[]>) entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_PROPIA)
                        .setParameter("idAfiliado", idAfiliadoConyuge)
                        .getResultList();
                
                CategoriaDTO categoriaPropia = new CategoriaDTO();
                if(categoriasPropias != null && !categoriasPropias.isEmpty()){
                    for (Object[] obj : categoriasPropias) {
                        if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                            categoriaPropia.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                        }
                        else if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                            categoriaPropia.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                        }
                        else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                            categoriaPropia.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                        }
                    }
                    
                    List<Object[]> fechaMotivoCambioCatPropia = (List<Object[]>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA_TIPO_NUM_ID)
                            .setParameter("idAfiliado", idAfiliadoConyuge)
                            .getResultList();
                    
                    if(fechaMotivoCambioCatPropia != null && !fechaMotivoCambioCatPropia.isEmpty()){
                        for (Object[] motivoYfecha : fechaMotivoCambioCatPropia) {
                            categoriaPropia.setFechaCambioCategoria(motivoYfecha[0] != null ? motivoYfecha[0].toString() : null);
                            categoriaPropia.setMotivoCambioCategoria(motivoYfecha[1] != null ? MotivoCambioCategoriaEnum.valueOf(motivoYfecha[1].toString()) : null);
                            break;
                        }
                    }
                }

                categorias.add(categoriaPropia);
                return categorias;
            }
            //si el beneficiario es tipo HIJO adicionalmente a las categorias heredadas del afiliado principal 
            //se obtienen las categorias heredadas del afiliado secundario
            else if(TipoBeneficiarioEnum.HIJO.equals(tipoBeneficiario)){
                
//                List<BigInteger> idAfiliadoSecundario = (List<BigInteger>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_AFILIADO_SECUNDARIO)
//                        .setParameter("tipoIdBeneficiario", tipoIdBeneficiario.name())
//                        .setParameter("numeroIdBeneficiario", numeroIdBeneficiario)
//                        .setParameter("idAfiliado", idAfiliado)
//                        .getResultList();

                if(idAfiliadoSecundario != null && !idAfiliadoSecundario.isEmpty()){
                    
                    List<Object[]> categoriasRespectoAfiSecundario = (List<Object[]>) entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_ACTUAL_BENEFICIARIO_RESPECTO_AFI_SEC)
                            .setParameter("idBenDet", idBenDetalle)
                            .setParameter("idAfiliadoSecundario", idAfiliadoSecundario.get(0).toString())
                            .getResultList();
                    
                    CategoriaDTO categoriaHeredadasAfiSecundario = new CategoriaDTO();
                    if(categoriasRespectoAfiSecundario != null && !categoriasRespectoAfiSecundario.isEmpty()){
                        for (Object[] obj : categoriasRespectoAfiSecundario) {
                            if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                                categoriaHeredadasAfiSecundario.setCategoriaPensionado(obj[1] != null ? obj[1].toString() : null);
                            }
                            else if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                                categoriaHeredadasAfiSecundario.setCategoriaDependiente(obj[1] != null ? obj[1].toString() : null);
                            }
                            else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                                categoriaHeredadasAfiSecundario.setCategoriaIndependiente(obj[1] != null ? obj[1].toString() : null);
                            }
                        }
                        
                        List<Object[]> fechaMotivoCambioCatAfiSec = (List<Object[]>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA)
                                .setParameter("idAfiliado", idAfiliadoSecundario.get(0).toString())
                                .getResultList();
                        
                        if(fechaMotivoCambioCatAfiSec != null && !fechaMotivoCambioCatAfiSec.isEmpty()){
                            for (Object[] motivoYfecha : fechaMotivoCambioCatAfiSec) {
                                categoriaHeredadasAfiSecundario.setFechaCambioCategoria(motivoYfecha[0] != null ? motivoYfecha[0].toString() : null);
                                categoriaHeredadasAfiSecundario.setMotivoCambioCategoria(motivoYfecha[1] != null ? MotivoCambioCategoriaEnum.valueOf(motivoYfecha[1].toString()) : null);
                                break;
                            }
                        }
                    }

                    categorias.add(categoriaHeredadasAfiSecundario);
                    return categorias;
                }
            }
            logger.debug("Finaliza servicio " + firma);
            return categorias;
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * @param categoriaActual
     */
    private void actualizarEstadoAfiliadoReportes(CategoriaAfiliadoDTO categoriaActual) {
        
        entityManagerReportes.createNamedQuery(NamedQueriesConstants.INSERTAR_DATO_ACTUALIZADO_ESTADO_AFILIADO)
        .setParameter("afiliado", categoriaActual.getAfiliado())
        .setParameter("tipoAfiliado", categoriaActual.getTipoCotizante().name())
        .setParameter("clasificacion", categoriaActual.getClasificacion().name())
        .setParameter("totalIngresoMesada", categoriaActual.getSalario().toString())
        .setParameter("estadoAfiliacion", categoriaActual.getEstadoAfiliacion().name())
        .setParameter("fechaFinServiciosSinAfiliacion", categoriaActual.getFechaFinServicioSinAfiliacion()!=null ? categoriaActual.getFechaFinServicioSinAfiliacion().toString() : null)
        .setParameter("categoria", categoriaActual.getCategoria())
        .setParameter("motivoCambioCategoria", null).executeUpdate();
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.business.interfaces.IConsultasModeloReportes#obtenerCategoriasHeredadas(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            Boolean isAfiliadoPrincipal, List<String> idsBenDetalle, List<Object[]> infoAfiPrincipal) {
        String firma = "obtenerCategoriasHeredadas(TipoIdentificacionEnum, String, Boolean, List<String>)";
        logger.debug("Inicia servicio " + firma);
        try {
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
                    //si solo es beneficiario de un afiliado y el servicio se invocó
                    //buscando con respecto al afiliado principal se toma esté como tal,
                    //de lo contrario se devuelve un DTO vacío que se interpreta como que no
                    //tiene afiliado secundario (isAfiliadoPrincipal == false)
                    if(isAfiliadoPrincipal){
                        
                        obtenerCategoriaAfiliadoPrincipal(categoriasHeredadas, infoAfiPrincipal, afiliadosPpales, isAfiliadoPrincipal);
                        
                        obtenerHistoricoCategoriasRespectoAfiliado(tipoIdAfiliado, numeroIdAfiliado, categoriasHeredadas,
                                historicoCatHeredadas, idsBenDetalle, afiliadosPpales.get(0).get(0));

                        categoriasHeredadas.setCategorias(historicoCatHeredadas);
                        return categoriasHeredadas;
                    }else{
                        categoriasHeredadas.setCategorias(historicoCatHeredadas);
                        return categoriasHeredadas;
                    }
                }
                //si es beneficiario activo de más de un afiliado
                else if(afiliadosPpales.size()>1){
                       obtenerCategoriaAfiliadoPrincipal(categoriasHeredadas, infoAfiPrincipal, afiliadosPpales, isAfiliadoPrincipal);
                       
                       obtenerHistoricoCategoriasRespectoAfiliado(tipoIdAfiliado, numeroIdAfiliado, categoriasHeredadas,
                            historicoCatHeredadas, idsBenDetalle, afiliadosPpales.get(1) != null ? afiliadosPpales.get(1).get(0) : afiliadosPpales.get(0).get(0));
                       
                       categoriasHeredadas.setCategorias(historicoCatHeredadas);
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
        int contador = 0;
           List<Object[]> objetos = (List<Object[]>) entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_CATEGORIAS_HEREDADAS_AFI_PRINCIPAL)
                   .setParameter("idsBenDetalle", idsBenDetalle)
                   .setParameter("idAfiliado", idAfiliado)
                   .getResultList();
                //   .setParameter("tipoIdBeneficiario", tipoIdAfiliado.toString())
                //   .setParameter("numeroIdBeneficiario", numeroIdAfiliado)
                //   .setParameter("tipoIdAfiliado", categoriasHeredadas.getTipoIdentificacion().name())
                //   .setParameter("numeroIdAfiliado", categoriasHeredadas.getNumeroIdentificacion())
                //   .getResultList();

           if(objetos != null && !objetos.isEmpty()){
               for (Object[] obj : objetos) {
                   CategoriaDTO cat = new CategoriaDTO();
                   if(contador != 0){
                       cat.setCategoriaDependiente(historicoCatHeredadas.get(contador-1).getCategoriaDependiente());
                       cat.setCategoriaIndependiente(historicoCatHeredadas.get(contador-1).getCategoriaIndependiente());
                       cat.setCategoriaPensionado(historicoCatHeredadas.get(contador-1).getCategoriaPensionado());
                       cat.setFechaCambioCategoria(historicoCatHeredadas.get(contador-1).getFechaCambioCategoria());
                       cat.setMotivoCambioCategoria(historicoCatHeredadas.get(contador-1).getMotivoCambioCategoria());
                   }
                   
                   if(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                       cat.setCategoriaDependiente(obj[1].toString());
                   }
                   else if(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                       cat.setCategoriaIndependiente(obj[1].toString());
                   }
                   else if(TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[0].toString()))){
                       cat.setCategoriaPensionado(obj[1].toString());
                   }
                   cat.setFechaCambioCategoria(obj[2].toString());
                   cat.setMotivoCambioCategoria(obj[3] != null ? MotivoCambioCategoriaEnum.valueOf(obj[3].toString()) : null);
                   
                   historicoCatHeredadas.add(cat);
                   contador++;
               }
           }
           
           if(!historicoCatHeredadas.isEmpty()){
               Collections.reverse(historicoCatHeredadas);
           }
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

        for (Object[] infoAfiPpal : infoAfiPrincipal) {
            if(Long.valueOf(afiliadosPpales.get(posicionAfiliado).get(0)).equals(Long.valueOf(infoAfiPpal[0].toString()))){
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

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarCategoriasAfiliadoYBeneficiarios(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            List<CategoriaAfiliadoDTO> categoriasActuales, List<DetalleBeneficiarioDTO> idBeneficiarios) {
        
        List<Object[]> categoriasReportes = new ArrayList<>();
        
        if(categoriasActuales != null && !categoriasActuales.isEmpty())
        {
            //verifica las categorias actuales del afiliado en reportes
            categoriasReportes = (List<Object[]>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CATEGORIA_ACTUAL_AFILIADO_REPORTES)
                    .setParameter("idAfiliado", categoriasActuales.get(0).getAfiliado())
                    //.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                    //.setParameter("numeroIdAfiliado", numeroIdAfiliado)
                    .getResultList();            
        }


        // crea listas para: tipoCotizanteReportes: registro de los tipos de afiliacion para el afiliado que serán actualizados en reportes
        //tipoCotizanteParaBeneficiario: registro de los tipos de afiliacion para el afiliado que serán actualizados en reportes y que deben actualizar los beneficiaros
        List<String> tipoCotizanteReportes = new ArrayList<>();
        List<String> tiposCotizanteParaBeneficiario = new ArrayList<>();
        
        //verifica que actualmente en core y reportes tenga registros de estados
        if(categoriasReportes != null && !categoriasReportes.isEmpty() && categoriasActuales != null && !categoriasActuales.isEmpty()){
            //recorre la lista de categorias para el afiliado en core
            for (CategoriaAfiliadoDTO categoriaActual : categoriasActuales){
              //recorre la lista de categorias para el afiliado en reportes
                for (Object[] obj : categoriasReportes) {
                    //llena la lista de control (para el caso en que es una categoria para un tipo de afiliación no registrado en reportes)
                    if(!tipoCotizanteReportes.contains(obj[2].toString())){
                        tipoCotizanteReportes.add(obj[2].toString());
                    }
                    //verifica si el estado actual para un tipo de afiliación es igual en core y reportes
                    if(((TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(categoriaActual.getTipoCotizante())
                       && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[2].toString())))
                       || (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(categoriaActual.getTipoCotizante()) 
                            && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(TipoAfiliadoEnum.valueOf(obj[2].toString())))
                            || (TipoAfiliadoEnum.PENSIONADO.equals(categoriaActual.getTipoCotizante())
                                && TipoAfiliadoEnum.PENSIONADO.equals(TipoAfiliadoEnum.valueOf(obj[2].toString()))))){
                        
                        if(!categoriaActual.getCategoria().equals(obj[7].toString())){
                            //si no es así lo persiste y añade el registro en la lista de control para beneficiarios
                            actualizarEstadoAfiliadoReportes(categoriaActual);
                            tiposCotizanteParaBeneficiario.add(categoriaActual.getTipoCotizante().name());
                        }
                        else{
                            break;
                        }
                    }
                }
            }
            //recorre la lista de categorias actuales para el afiliado en core y verifica si alguno no fue registrada alguna de ellas
            for (CategoriaAfiliadoDTO categoriaActual : categoriasActuales){
                if(!tipoCotizanteReportes.contains(categoriaActual.getTipoCotizante().name())){
                    //si no es así lo persiste y añade el registro en la lista de control para beneficiarios
                    actualizarEstadoAfiliadoReportes(categoriaActual);
                    tiposCotizanteParaBeneficiario.add(categoriaActual.getTipoCotizante().name());
                }
            }
        }
        //si no hay registros en reportes, recorre el arreglo de categorias para el afliado en core y lo persiste en reportes
        else if(categoriasActuales != null && !categoriasActuales.isEmpty()){
            
            for (CategoriaAfiliadoDTO categoriaActual : categoriasActuales){
              //si no es así lo persiste y añade el registro en la lista de control para beneficiarios
                actualizarEstadoAfiliadoReportes(categoriaActual);
                tiposCotizanteParaBeneficiario.add(categoriaActual.getTipoCotizante().name());
            }
        }
        
        //crea una lista para los ids de categoriaAfiliado (necesarios para persistir las categorias de beneficiario)
        List<BigInteger> idsCategoriaAfiliado = new ArrayList<>(); 
        
        //consulta los id de categorias de afiliado 
        if(categoriasActuales!= null && !categoriasActuales.isEmpty() && tiposCotizanteParaBeneficiario != null && !tiposCotizanteParaBeneficiario.isEmpty()){
            for (String tipoAfiliado : tiposCotizanteParaBeneficiario) {
                List<BigInteger> idCategoria = (List<BigInteger>)entityManagerReportes.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_CATEGORIA_AFILIADO)
                .setParameter("idAfiliado", categoriasActuales.get(0).getAfiliado())
                .setParameter("tipoAfiliado", tipoAfiliado)
                .getResultList();
                
                if(idCategoria != null && !idCategoria.isEmpty()){
                    idsCategoriaAfiliado.add(idCategoria.get(0));
                }
            }
        }
        
        if(idsCategoriaAfiliado!= null && !idsCategoriaAfiliado.isEmpty()){
            if(idBeneficiarios!= null && !idBeneficiarios.isEmpty()){
                for (DetalleBeneficiarioDTO beneficiario : idBeneficiarios) {
                    for(BigInteger idCatAfi : idsCategoriaAfiliado){
                        actualizarEstadoBeneficiarioReportes(beneficiario.getIdDetalleBeneficiario(), beneficiario.getTipoBeneficiario(), idCatAfi);
                    }
                }
            }
        }
    }
    
    /**
     * @param categoriaActual
     */
    private void actualizarEstadoBeneficiarioReportes(Long idBeneficiarioDetalle, ClasificacionEnum tipoBeneficiario, BigInteger categoriaAfiliado) {
        
        entityManagerReportes.createNamedQuery(NamedQueriesConstants.INSERTAR_DATO_ACTUALIZADO_ESTADO_BENEFICIARIO)
        .setParameter("beneficiarioDetalle", idBeneficiarioDetalle)
        .setParameter("tipoBeneficiario", tipoBeneficiario.name())
        .setParameter("categoriaAfiliado", categoriaAfiliado)
        .executeUpdate();
    }
}
