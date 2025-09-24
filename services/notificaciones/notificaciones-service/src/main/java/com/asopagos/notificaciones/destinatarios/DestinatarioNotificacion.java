package com.asopagos.notificaciones.destinatarios;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public abstract class DestinatarioNotificacion {

    public static String ID_SOLICITUD = "idSolicitud";

    public static String MAPA = "mapa";

    /**
     * Servicio que obtiene un correo de un destintario buscando dependiendo del rol y la solicitud
     * @param parametros
     * @param em
     * @return correo
     */
    public abstract List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em);
}
