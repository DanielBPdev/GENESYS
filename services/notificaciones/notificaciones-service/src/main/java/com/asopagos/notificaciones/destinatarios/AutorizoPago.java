package com.asopagos.notificaciones.destinatarios;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class AutorizoPago extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AutorizoPago.class);

    /** (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum, java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        // TODO Auto-generated method stub
        return null;
    }
}
