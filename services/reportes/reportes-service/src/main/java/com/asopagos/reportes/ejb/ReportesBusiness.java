package com.asopagos.reportes.ejb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.reportes.business.interfaces.IConsultasModeloReportes;
import com.asopagos.reportes.dto.CategoriaAfiliadoDTO;
import com.asopagos.reportes.dto.CategoriaBeneficiarioDTO;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.reportes.dto.DatosIdentificadorGrupoReporteDTO;
import com.asopagos.reportes.dto.DatosParametrizacionMetaDTO;
import com.asopagos.reportes.dto.DetalleBeneficiarioDTO;
import com.asopagos.reportes.dto.ParametrizacionMetaDTO;
import com.asopagos.reportes.service.ReportesService;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de Reportes<br/>
 * <b>Módulo:</b> Asopagos - Reportes 3.2.1 - 3.2.5
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class ReportesBusiness implements ReportesService {

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasCore;

    /** inject del EJB para consultas en modelo Subsidio */
    @Inject
    private IConsultasModeloReportes consultasReportes;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ReportesBusiness.class);

    @Override
    public List<DatosParametrizacionMetaDTO> consultarParametrizacionMeta(MetaEnum meta, PeriodicidadMetaEnum periodicidad, String periodo,
            FrecuenciaMetaEnum frecuencia) {

        String firmaMetodo = "consultarParametrizacionMeta(MetaEnum, PeriodicidadMetaEnum, String, FrecuenciaMetaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<DatosParametrizacionMetaDTO> result = consultasReportes.consultarParametrizacionMeta(meta, periodicidad, periodo, frecuencia);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    @Override
    public void actualizarParametrizacionMeta(ParametrizacionMetaDTO parametrizacionMetasDTO) {

        String firmaMetodo = "actualizarParametrizacionMeta(ParametrizacionMetaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        consultasReportes.actualizarParametrizacionMeta(parametrizacionMetasDTO);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public List<DatosIdentificadorGrupoReporteDTO> consultarIdentificadorReporte(ReporteKPIEnum nombreReporte,
            FrecuenciaMetaEnum frecuenciaReporte) {
        String firmaMetodo = "consultarIdentificadorReporte(ReporteKPIEnum, FrecuenciaMetaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<DatosIdentificadorGrupoReporteDTO> result = consultasCore.consultarIdentificadorReporte(nombreReporte, frecuenciaReporte);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#consultarEstadoAportanteFecha(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.lang.Long)
     */
    @Override
    public String consultarEstadoAportanteFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante, Long fecha){
        logger.debug("Inicia servicio consultarEstadoAportanteFecha");
        Long idPersona = consultasCore.consultarIdPersona(tipoIdentificacion, numeroIdentificacion);
        if(idPersona != null){
            String result = consultasReportes.consultarEstadoAportanteFecha(idPersona, tipoIdentificacion, numeroIdentificacion, tipoAportante, fecha);
            logger.debug("Finaliza servicio consultarEstadoAportanteFecha");
            return result;
        }
        logger.debug("Finaliza servicio consultarEstadoAportanteFecha");
        return "";
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#consultarEstadoAfiliadoFecha(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Long)
     */
    @Override
    public String consultarEstadoAfiliadoFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacionEmpleador, String numeroIdentificacionEmpleador, Long fecha){
        logger.debug("Inicia servicio consultarEstadoAfiliadoFecha");
        String result = consultasCore.consultarEstadoAfiliadoFecha(tipoIdentificacion, numeroIdentificacion, tipoAfiliado, tipoIdentificacionEmpleador, numeroIdentificacionEmpleador, fecha);
        logger.debug("Finaliza servicio consultarEstadoAfiliadoFecha");
        return result;
        
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#consultarCategoriaBeneficiario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum, java.lang.Long)
     */
    @Override
    public List<CategoriaDTO> consultarCategoriaBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario, TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle) {
        String firma = "consultarCategoriaBeneficiario(TipoIdentificacionEnum, String, TipoBeneficiarioEnum, Long)";
        logger.debug("Inicia servicio " + firma);
        String idAfiliadoConyuge = null;
        List<BigInteger> idAfiliadoSecundario = null;
        if(TipoBeneficiarioEnum.CONYUGE.equals(tipoBeneficiario)){
            idAfiliadoConyuge = consultasCore.obtenerIdAfiliadoConyuge(tipoIdBeneficiario, numeroIdBeneficiario);
        }
        if(TipoBeneficiarioEnum.HIJO.equals(tipoBeneficiario)){
            idAfiliadoSecundario = consultasCore.obtenerIdAfiliadoSecundario(tipoIdBeneficiario, numeroIdBeneficiario, idAfiliado);
        }
        List<CategoriaDTO> result = new ArrayList<>(); 
        /*List<CategoriaDTO> categoriasPropiasAfiliado = consultasCore.consultarCategoriasPropiasAfiliadoBeneficiario(idAfiliado, idBenDetalle);
        result.addAll(categoriasPropiasAfiliado);
        result.addAll(consultasCore.consultarCategoriaBeneficiario(tipoIdBeneficiario, numeroIdBeneficiario, tipoBeneficiario, idAfiliado, idBenDetalle, idAfiliadoSecundario, idAfiliadoConyuge));
        logger.debug("Finaliza servicio" + firma);
*/
        result.addAll(consultasCore.consultarCategoriaBeneficiarioStoredProcedure(tipoIdBeneficiario, numeroIdBeneficiario, tipoBeneficiario, 
        idAfiliado, idBenDetalle, idAfiliadoSecundario, idAfiliadoConyuge));

        return result;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#obtenerCategoriasPropiasAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
        String firma = "obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
        logger.debug("Inicia servicio " + firma);
        /*List<CategoriaAfiliadoDTO> categoriasActuales = consultasCore.obtenerCategoriaPropiaActual(tipoIdAfiliado, numeroIdAfiliado, tipoAfiliado);*/
        CategoriasComoAfiliadoPrincipalDTO result = consultasCore.obtenerCategoriasPropiasAfiliado(tipoIdAfiliado, numeroIdAfiliado, tipoAfiliado);
        logger.debug("Finaliza servicio" + firma);
        return result;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#obtenerCategoriasConyugeAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
        String firma = "obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
        logger.debug("Inicia servicio " + firma);

        CategoriasComoAfiliadoPrincipalDTO result = consultasCore.obtenerCategoriasConyugeAfiliado(tipoIdAfiliado, numeroIdAfiliado, tipoAfiliado);
        
        logger.debug("Finaliza servicio" + firma);
        return result;        
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesService#obtenerCategoriasHeredadas(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            Boolean isAfiliadoPrincipal) {
        String firma = "obtenerCategoriasHeredadas(TipoIdentificacionEnum, String, Boolean)";
        logger.debug("Inicia servicio " + firma);
        logger.debug("Finaliza servicio" + firma);
        return consultasCore.obtenerCategoriasHeredadas(tipoIdAfiliado, numeroIdAfiliado, isAfiliadoPrincipal);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actualizarHistoricosAfiliacionYCategoria(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            TipoAfiliadoEnum tipoAfiliado) {
//        try {
//            List<CategoriaAfiliadoDTO> categoriasActuales = consultasCore.obtenerCategoriaPropiaActual(tipoIdAfiliado, numeroIdAfiliado, tipoAfiliado);
//            //lista de los beneficiarios asociados al afiliado
//            List<DetalleBeneficiarioDTO> idBeneficiarioDetalleAfiliado = consultasCore.consultarIdBeneficiarios(tipoIdAfiliado, numeroIdAfiliado);
//
//            consultasReportes.actualizarCategoriasAfiliadoYBeneficiarios(tipoIdAfiliado, numeroIdAfiliado, categoriasActuales, idBeneficiarioDetalleAfiliado);
//            
//        } catch (Exception e) {
//            logger.error("Ocurrió un error en el servicio actualizarHistoricosAfiliacionYCategoria");
//            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//        }   
    }
}
