package com.asopagos.notificaciones.envio;

import com.asopagos.aportes.composite.clients.ActualizarComunicadoTrazabilidadAporte;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.NotificacionDTO;

public class EnvioComunicadoCierreAporteManual implements EnvioComunicado{
    
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(EnvioComunicadoCierreAporteManual.class);

    @Override
    public void procesar(NotificacionDTO notificacion) {

        if (notificacion.getParams() != null && notificacion.getIdSolicitud() != null && notificacion.getIdComunicado() != null) {
            ActualizarComunicadoTrazabilidadAporte actualizarComunicadoService = new ActualizarComunicadoTrazabilidadAporte(
                    notificacion.getIdSolicitud(), ActividadEnum.GESTIONAR_PAGO_CERRAR, notificacion.getIdComunicado());
            actualizarComunicadoService.execute();
        }

    }

}
