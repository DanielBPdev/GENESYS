package com.asopagos.aportes.ejb;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.asopagos.aportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.business.interfaces.IConsultasModeloStaging;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.aportes.dto.DetalleDatosAportanteDTO;
import com.asopagos.aportes.service.AportesIntegracionesService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 * con el registro, consultas o relación de aportes
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class AportesIntegracionesBusiness implements AportesIntegracionesService {

    /**
     * Inject del EJB para consultas en modelo Staging entityManagerStPila
     */
    @Inject
    private IConsultasModeloStaging consultasStaging;

    /**
     * Inject del EJB para consultas en modelo Core entityManager
     */
    @Inject
    private IConsultasModeloCore consultasCore;

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(AportesIntegracionesBusiness.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.service.AportesIntegracionesService#obtenerUltimoAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DatosAportanteDTO consultarUltimoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroIdentificacionCotizante) {
        try {
            if (numeroIdentificacion == null && numeroIdentificacionCotizante == null) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }

            logger.info("Inicio método obtenerUltimoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
            DatosAportanteDTO ultimoAporte = consultasCore.consultarDatosAportante(tipoIdentificacion, numeroIdentificacion,
                    numeroIdentificacionCotizante);

            if (ultimoAporte.getDatosAportes() != null) {
                for (DetalleDatosAportanteDTO detalleAportante : ultimoAporte.getDatosAportes()) {
                    RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneralId(detalleAportante.getIdRegistroGeneral());
                    ClaseAportanteEnum claseAportante = ClaseAportanteEnum.obtenerClaseAportanteEnum(registroGeneral.getClaseAportante());
                    detalleAportante.setClaseAportante(claseAportante);
                }
            }

            logger.info("Finaliza método obtenerUltimoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
            return ultimoAporte;
        } catch (Exception e) {
            logger.error("Finaliza método obtenerUltimoAporte: Ocurrio un error obteniendo los registros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.service.AportesIntegracionesService#obtenerHistoricoAporte(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DatosAportanteDTO consultarHistoricoAporte(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroIdentificacionCotizante, String periodoInicio, String periodoFin) {
        try {
            if (numeroIdentificacion == null && numeroIdentificacionCotizante == null) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            
            Long fechaInicio = null;
            Long fechaFin = null;
            if(periodoInicio != null && periodoInicio.length() == 7){
            	fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(periodoInicio+"-01").getTime();
            }
            if(periodoFin != null && periodoFin.length() == 7){
            	fechaFin  = new SimpleDateFormat("yyyy-MM-dd").parse(periodoFin+"-01").getTime();
            }
            
            logger.debug("Inicio método obtenerHistoricoAporte(TipoIdentificacionEnum, String, Long, Long)");
            DatosAportanteDTO historicoAportes = consultasCore.consultarHistoricoDatosAportante(tipoIdentificacion, numeroIdentificacion,
                    numeroIdentificacionCotizante, fechaInicio, fechaFin);
            if (historicoAportes != null && historicoAportes.getDatosAportes() != null) {
                for (DetalleDatosAportanteDTO detalleAportante : historicoAportes.getDatosAportes()) {
                    RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneralId(detalleAportante.getIdRegistroGeneral());
                    ClaseAportanteEnum claseAportante = ClaseAportanteEnum.obtenerClaseAportanteEnum(registroGeneral.getClaseAportante());
                    detalleAportante.setClaseAportante(claseAportante);
                    
                    List<RegistroDetallado> registrosDetallados = consultasStaging.consultarRegistroDetallado(detalleAportante.getIdRegistroGeneral());
                    
                    if(registrosDetallados != null && !registrosDetallados.isEmpty()){
                    	detalleAportante.setDiasCotizados(registrosDetallados.get(0).getDiasCotizados());
                    }
                   
                    
                }
            }
            logger.debug("Finaliza método obtenerHistoricoAporte(TipoIdentificacionEnum, String, Long, Long)");
            
            if(historicoAportes != null){
            	historicoAportes.setDiasCotizados(null);
            }
            return historicoAportes;
        } catch (Exception e) {
            logger.error("Finaliza método obtenerHistoricoAporte: Ocurrio un error obteniendo los registros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.service.AportesIntegracionesService#obtenerUltimoAporteCotizantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @Override
    public DatosCotizanteIntegracionDTO consultarUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroIdentificacionAportante) {
        try {
            if (numeroIdentificacion == null && numeroIdentificacionAportante == null) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            logger.debug(
                    "Inicio método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
            DatosCotizanteIntegracionDTO ultimoCotizante = new DatosCotizanteIntegracionDTO();
                    
            ultimoCotizante = consultasCore.consultarDatosCotizante(tipoIdentificacion, numeroIdentificacion,
                    numeroIdentificacionAportante);
            logger.debug(
                    "Finaliza método obtenerUltimoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
            return ultimoCotizante;
        } catch (Exception e) {
            logger.error("Finaliza método obtenerUltimoAporteCotizantes: Ocurrio un error obteniendo los registros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.service.AportesIntegracionesService#obtenerHistoricoAporteCotizantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long)
     */
    @Override
    public DatosCotizanteIntegracionDTO consultarHistoricoAporteCotizantes(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroIdentificacionAportante, String periodoInicio, String periodoFin) {
    	logger.info("Inicio método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
    	try {
            if (numeroIdentificacion == null && numeroIdentificacionAportante == null) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            
            Long fechaInicio = null;
            Long fechaFin = null;
            if(periodoInicio != null && periodoInicio.length() == 7){
            	fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(periodoInicio+"-01").getTime();
            }
            if(periodoFin != null && periodoFin.length() == 7){
            	fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(periodoFin+"-01").getTime();
            }
  
            logger.info("Finaliza método obtenerHistoricoAporteCotizantes(TipoIdentificacionEnum, String, Long, Long)");
            return consultasCore.consultarHistoricoDatosCotizante(tipoIdentificacion,
                    numeroIdentificacion, numeroIdentificacionAportante, fechaInicio, fechaFin);
        } catch (Exception e) {
            logger.error("Finaliza método obtenerHistoricoAporteCotizantes: Ocurrio un error obteniendo los registros", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

}
