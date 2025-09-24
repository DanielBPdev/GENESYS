package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ActualizarEstadoSolicitudAnalisisNovedadFOVIS;
import com.asopagos.novedades.fovis.clients.ConsultarSolicitudAnalisisNovedad;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudAnalisisNovedadPersonaFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudAnalisisNovedadPersonaFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudAnalisisNovedad consultarSolicitudAnalisisNovedad = new ConsultarSolicitudAnalisisNovedad(
                inDTO.getIdSolicitudGlobal());
        consultarSolicitudAnalisisNovedad.execute();
        SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO = consultarSolicitudAnalisisNovedad.getResult();

        if (solicitudAnalisisNovedadFOVISModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud = EstadoSolicitudAnalisisNovedadFovisEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudAnalisisNovedadFOVIS actualizarEstado = new ActualizarEstadoSolicitudAnalisisNovedadFOVIS(estadoSolicitud,
                inDTO.getIdSolicitudGlobal());
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudAnalisisNovedadFOVIS(EstadoSolicitudAnalisisNovedadFovisEnum.CERRADA,
                inDTO.getIdSolicitudGlobal());
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudAnalisisNovedadFOVISModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
