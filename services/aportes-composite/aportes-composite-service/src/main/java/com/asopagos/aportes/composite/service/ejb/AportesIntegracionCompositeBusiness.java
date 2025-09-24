package com.asopagos.aportes.composite.service.ejb;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.asopagos.aportes.clients.ConsultarHistoricoAporte;
import com.asopagos.aportes.clients.ConsultarHistoricoAporteCotizantes;
import com.asopagos.aportes.clients.ConsultarUltimoAporte;
import com.asopagos.aportes.clients.ConsultarUltimoAporteCotizantes;
import com.asopagos.aportes.composite.clients.ConsultarDatosCotizante;
import com.asopagos.aportes.composite.dto.DatosCotizanteDTO;
import com.asopagos.aportes.composite.service.AportesIntegracionCompositeService;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.aportes.dto.DetalleDatosCotizanteDTO;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: EJB que contiene la lógica de negocio para el proceso
 * 2.1.2</b>
 * 
 * @author Claudia Milena Marín Hincapié<clmarin@heinsohn.com.co>
 */
@Stateless
public class AportesIntegracionCompositeBusiness implements AportesIntegracionCompositeService {
    
     
    @PersistenceContext(unitName = "core_PU_APORTE")
    private EntityManager entityManager;

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(AportesIntegracionCompositeBusiness.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.composite.service.AportesIntegracionCompositeService#obtenerUltimoAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response obtenerUltimoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroIdentificacionCotizante,HttpServletRequest requestContext, UserDTO userDTO) {
        logger.debug( "Inicio de método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
         String firmaServicio = "AportesIntegracionCompositeBusiness.obtenerUltimoAporte(TipoIdentificacionEnum, String)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        parametrosMetodo.put("numeroIdentificacionCotizante", numeroIdentificacionCotizante);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        ConsultarUltimoAporte aportante= new ConsultarUltimoAporte(numeroIdentificacion, tipoIdentificacion, numeroIdentificacionCotizante);
        try{
        aportante.execute();
        logger.debug("Finaliza de método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,aportante.getResult(),entityManager,auditoriaIntegracionServicios);
    }


    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.composite.service.AportesIntegracionCompositeService#obtenerHistoricoAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response obtenerHistoricoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroIdentificacionCotizante,
            String periodoInicio, String periodoFin, HttpServletRequest requestContext, UserDTO userDTO) {
        logger.debug("Inicio de método obtenerobtenerUltimoAporteCotizanteUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        String firmaServicio = "AportesIntegracionCompositeBusiness.obtenerHistoricoAporte(TipoIdentificacionEnum, String)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        parametrosMetodo.put("numeroIdentificacionCotizante", numeroIdentificacionCotizante);
        parametrosMetodo.put("periodoInicio", periodoInicio);
        parametrosMetodo.put("periodoFin", periodoFin);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        ConsultarHistoricoAporte cotizantes = new ConsultarHistoricoAporte(periodoInicio, periodoFin, numeroIdentificacion, tipoIdentificacion, numeroIdentificacionCotizante);
        try{
        cotizantes.execute();
        logger.debug("Finaliza de método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,cotizantes.getResult(),entityManager,auditoriaIntegracionServicios);
    }


    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.composite.service.AportesIntegracionCompositeService#obtenerUltimoAporteCotizantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroIdentificacionAportante,HttpServletRequest requestContext, UserDTO userDTO) {
        logger.debug( "Inicio de método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        String firmaServicio = "AportesIntegracionCompositeBusiness.obtenerUltimoAporteCotizantes(TipoIdentificacionEnum, String)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        parametrosMetodo.put("numeroIdentificacionAportante", numeroIdentificacionAportante);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        ConsultarUltimoAporteCotizantes cotizantes = new ConsultarUltimoAporteCotizantes(numeroIdentificacionAportante, numeroIdentificacion, tipoIdentificacion);
        try{
        cotizantes.execute();
        logger.debug("Finaliza de método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,cotizantes.getResult(),entityManager,auditoriaIntegracionServicios);
       }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.composite.service.AportesIntegracionCompositeService#obtenerHistoricoAporteCotizantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion,
        String numeroIdentificacion, String numeroIdentificacionAportante, String periodoInicio, String periodoFin, HttpServletRequest requestContext, UserDTO userDTO) {
        logger.debug("Inicio de método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
        String firmaServicio = "AportesIntegracionCompositeBusiness.obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        parametrosMetodo.put("numeroIdentificacionAportante", numeroIdentificacionAportante);
        parametrosMetodo.put("periodoInicio", periodoInicio);
        parametrosMetodo.put("periodoFin", periodoFin);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        ConsultarHistoricoAporteCotizantes cotizanteService = new ConsultarHistoricoAporteCotizantes(periodoInicio, numeroIdentificacionAportante, periodoFin, numeroIdentificacion, tipoIdentificacion);
        try{
        cotizanteService.execute();
        logger.debug("Finaliza de método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,cotizanteService.getResult(),entityManager,auditoriaIntegracionServicios);
    }

    /**
     * Datos 
     * 
     * @param cotizante
     * @return
     */
    private DatosCotizanteIntegracionDTO obtenerAporteCotizantes(DatosCotizanteIntegracionDTO cotizante) {
        try {
            logger.debug("Inicio método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
            if (cotizante !=null){            
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            	
                DatosCotizanteDTO cotizanteEstado = new DatosCotizanteDTO();
                cotizanteEstado.setTipoIdentificacion(cotizante.getTipoIdentificacionCotizante());
                cotizanteEstado.setNumeroIdentificacion(cotizante.getNumeroIdentificacionCotizante());

                if(cotizante.getDatosAportesCotizante() != null && !cotizante.getDatosAportesCotizante().isEmpty()){
                	for (DetalleDatosCotizanteDTO detalleCotizante : cotizante.getDatosAportesCotizante()) {
                        cotizanteEstado.setTipoIdenficacionEmpleador(detalleCotizante.getAportante().getTipoIdentificacionAportante());
                        cotizanteEstado.setNumeroIdentificacionEmpleador(detalleCotizante.getAportante().getNumeroIdentificacionAportante());
                        cotizanteEstado = consultarDatosCotizante(cotizanteEstado,
                                detalleCotizante.getTipoAportante());
                        detalleCotizante.setEstado(cotizanteEstado.getEstadoAfiliado());
                        
                        if(cotizanteEstado.getFechaIngreso() != null){
                        	Date fechaIngreso = new Date(cotizanteEstado.getFechaIngreso());
                        	detalleCotizante.setFechaIngreso(fechaIngreso != null ? format.format(fechaIngreso) : null);
                        }
                        if(cotizanteEstado.getFechaRetiro() != null){
                        	Date fechaRetiro = new Date(cotizanteEstado.getFechaRetiro());
                        	detalleCotizante.setFechaRetiro(fechaRetiro != null ? format.format(fechaRetiro) : null);
                        }
                    }
                }
            }
            
            logger.debug("Finaliza método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
            return cotizante;
        } catch (Exception e) {
            logger.error("Finaliza método obtenerHistoricoAporteCotizantes: Ocurrio un error obteniendo los registros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método privado que consulta los datos del cotizante
     * 
     * @param cotizanteEstado
     * @param tipoAportante
     * @return
     */
    private DatosCotizanteDTO consultarDatosCotizante(DatosCotizanteDTO cotizanteEstado,
            TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        logger.debug("Inicio de método actualizarDatosPersona(PersonaModeloDTO personaDTO)");
        ConsultarDatosCotizante cotizanteService = new ConsultarDatosCotizante(tipoAportante, cotizanteEstado);
        cotizanteService.execute();
        logger.debug("Finaliza de método actualizarDatosPersona(PersonaModeloDTO personaDTO)");
        return cotizanteService.getResult();
    }

}
