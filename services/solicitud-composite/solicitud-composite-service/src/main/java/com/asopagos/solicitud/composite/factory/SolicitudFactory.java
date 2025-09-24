package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.solicitud.composite.ejb.SolicitudCompositeBusiness;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class SolicitudFactory {

    /** Referencia al logger */
    private static ILogger logger = LogManager.getLogger(SolicitudCompositeBusiness.class);
    
    /**
     * Constructor privado
     */
    private SolicitudFactory() {
    }

    /**
     * Método que determina por medio de la transacción que implementación retornar
     * @param tipoTx
     * @return
     */
    public static Solicitud getInstance(TipoTransaccionEnum tipoTx) {
        logger.debug("Inicia getInstance(TipoTransaccionEnum)");
        switch (ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx)) {
		case NOVEDAD_EMPLEADOR:

		case NOVEDAD_PERSONA:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de novedad persona");
            return new SolicitudAfiliacionNovedadFI();    
		case EMPLEADOR:
			logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de empleador");
            return new SolicitudAfiliacionEmpleadorFI();
		case PERSONA:
			logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de persona");
            return new SolicitudAfiliacionPersonaFI();
		case APORTES:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de aportes");
            return new SolicitudAporte();
		case APORTES_CORRECCION:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de aportes correción");
            return new SolicitudCorreccionAporte();
		case APORTES_DEVOLUCION:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de aportes devolución");
            return new SolicitudDevolucionAporte();
        case APORTES_MANUALES_MASIVA:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de aportes masiva");
            return new SolicitudAporte();
        case DEVOLUCION_APORTES_MASIVA:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de aportes devolución masiva");
            return new SolicitudDevolucionAporte();
		case ENTIDAD_PAGADORA:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de entidad pagadora");
            return new SolicitudEntidadPagadora();
		case FISCALIZACION:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de fiscalización");
            return new SolicitudFiscalizacion();
		case GESTION_PREVENTIVA:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de gestión preventiva");
            return new SolicitudGestionPreventiva();
		case POSTULACION_FOVIS:
		    logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de postulación fovis");
            return new SolicitudPostulacionFovis();
		case DESAFILIACION_APORTANTES:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de desafiliación aportantes");
            return new SolicitudDesafiliacion();
		case GESTION_COBRO_ELECTRONICO:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de Solicitud de gestion cobro electrónico");
            return new SolicitudGestionCobroElectronico();
		case GESTION_COBRO_FISICO:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de Solicitud de gestion cobro físico");
            return new SolicitudGestionCobroFisico();
		case GESTION_COBRO_MANUAL:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de Solicitud de gestion cobro manual");
            return new SolicitudGestionCobroManual();
        case POSTULACION_FOVIS_VERIFICACION:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de verificación postulación fovis");
            return new SolicitudVerificacionPostulacionFovis();
        case POSTULACION_FOVIS_GESTION_CRUCE:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de gestion cruces internos postulación fovis");
            return new SolicitudGestionCruceFovis();
        case ASIGNACION_FOVIS:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de asignación fovis");
            return new SolicitudAsignacionFovis();
        case LEGALIZACION_FOVIS:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de legalización y desembolso fovis");
            return new SolicitudLegalizacionFovis();
        case NOVEDAD_FOVIS_ANALISIS:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de analisis de novedad persona afecta postulación fovis");
            return new SolicitudAnalisisNovedadPersonaFovis();
        case NOVEDAD_FOVIS:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de novedad postulación fovis");
            return new SolicitudNovedadFovis();
        case CIERRE_RECAUDO:
            logger.debug("Finaliza getInstance(TipoTransaccionEnum) : implementación de cierre de recaudo de aportes");
            return new SolicitudCierreRecaudo();
		default: // entidad pagadora
			logger.error("Finaliza getInstance(TipoTransaccionEnum): no se encuentra una implementación para la fabrica solicitada");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
    }
}
