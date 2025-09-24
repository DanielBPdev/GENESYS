package com.asopagos.subsidiomonetario.service.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionAdminSubsidioDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloCore;
import com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import com.asopagos.subsidiomonetario.service.SubsidioIntegracionService;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import javax.persistence.PersistenceContext;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core/Subsidio para los servicios de
 * integración <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@Stateless
public class SubsidioIntegracionBusiness implements SubsidioIntegracionService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SubsidioMonetarioBusiness.class);


    
   @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;

    /**
     * Inject del EJB para consultas en modelo Core
     */
    @Inject
    private IConsultasModeloCore consultasCore;

    /**
     * inject del EJB para consultas en modelo Subsidio
     */
    @Inject
    private IConsultasModeloSubsidio consultasSubsidio;

    /**
     * Interfaz de generación de archivos mediante Lion Framework
     */
    @Inject
    private FileGenerator fileGenerator;

    @Resource
    TimerService timerService;

    @Override
    public Response obtenerCuotaMonetaria(TipoIdentificacionEnum tipoIdPersona, String numeroIdPersona, String periodo, HttpServletRequest requestContext,UserDTO userDTO) {
        String firmaServicio = "SubsidioMonetarioBusiness.obtenerCuotaMonetaria(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdPersona", tipoIdPersona.name());
        parametrosMetodo.put("numeroIdPersona", numeroIdPersona);
        parametrosMetodo.put("periodo", periodo);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        SubsidioAfiliadoDTO resultado;
        Long periodoInicio = null;
        if (periodo != null && periodo.length() == 7) {
            try {
                periodoInicio = new SimpleDateFormat("yyyy-MM-dd").parse(periodo + "-01").getTime();
            } catch (ParseException e) {
                resultado = new SubsidioAfiliadoDTO();
                resultado.setMensaje("El formato de fecha ingresado para el periodo no es correcto.");
                 return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,resultado,entityManager,auditoriaIntegracionServicios);
            }
        }

        resultado = consultasCore.obtenerCuotaMonetaria(tipoIdPersona, numeroIdPersona, periodoInicio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,resultado,entityManager,auditoriaIntegracionServicios);
    }
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.service.SubsidioIntegracionService#obtenerInfoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
     @Override
    public Response obtenerInfoSubsidio(TipoIdentificacionEnum tipoIdPersona, String numeroIdAfiliado, String numeroIdBeneficiario, HttpServletRequest requestContext,UserDTO userDTO) {
        String firmaServicio = "SubsidioMonetarioBusiness.obtenerInfoSubsidio(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdPersona", tipoIdPersona.name());
        parametrosMetodo.put("numeroIdAfiliado", numeroIdAfiliado);
        parametrosMetodo.put("numeroIdBeneficiario", numeroIdBeneficiario);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        SubsidioAfiliadoDTO resultado = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
        try{
        resultado = consultasCore.obtenerInfoSubsidio(tipoIdPersona, numeroIdAfiliado, numeroIdBeneficiario);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        //return resultado;
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,resultado,entityManager,auditoriaIntegracionServicios);
    }


    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.service.SubsidioIntegracionService#obtenerInfoAdministradorSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    public Response obtenerInfoAdministradorSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio,
        String numeroIdAdminSubsidio, HttpServletRequest requestContext,UserDTO userDTO) {
        String firmaServicio = "SubsidioMonetarioBusiness.obtenerInfoAdministradorSubsidio(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdAdminSubsidio", tipoIdAdminSubsidio.name());
        parametrosMetodo.put("numeroIdAdminSubsidio", numeroIdAdminSubsidio);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        InformacionAdminSubsidioDTO resultado = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
       try{
         resultado = consultasCore.obtenerInfoAdministradorSubsidio(tipoIdAdminSubsidio, numeroIdAdminSubsidio);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        //return resultado;
        } catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,resultado,entityManager,auditoriaIntegracionServicios);
    }

    @Override
    public List<InformacionLiquidacionFallecimientoDTO> consultarLiquidacionFallecimiento(Long fechaInicio, Long fechaFin, String identificacion, Long periodoRegular, String numeroOperacion, String medioPago, String tipoIdentificacion, Long fechaProgramada) {
                String firmaMetodo = "SubsidioMonetarioBusiness.consultarLiquidacionFallecimiento(Long , Long , String , String , Long , String , String , Long";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        logger.info("llego con valor y fuerza " + fechaInicio);

        List<InformacionLiquidacionFallecimientoDTO> resultado = consultasCore.consultarLiquidacionFallecimiento(fechaInicio, fechaFin, tipoIdentificacion, identificacion, periodoRegular, numeroOperacion, medioPago, fechaProgramada);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

}
