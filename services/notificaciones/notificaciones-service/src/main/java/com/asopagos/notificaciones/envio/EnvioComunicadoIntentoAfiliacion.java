package com.asopagos.notificaciones.envio;

import com.asopagos.afiliaciones.clients.AsociarComunicadoIntentoAfiliacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.NotificacionDTO;

public class EnvioComunicadoIntentoAfiliacion implements EnvioComunicado {
    
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(EnvioComunicadoFallidoPila.class);
    
    @Override
    public void procesar(NotificacionDTO notificacion) {
        // TODO Auto-generated method stub
        if (notificacion.getParams() != null && notificacion.getIdComunicado() != null) {
            AsociarComunicadoIntentoAfiliacion service = new AsociarComunicadoIntentoAfiliacion(Long.valueOf(notificacion.getParams().get("idIntentoAfiliacion")), notificacion.getIdComunicado());
            service.execute();
        }
        
    }

}
