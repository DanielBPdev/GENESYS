package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class AdministradorSubsidios extends DestinatarioNotificacion{


    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AdministradorSubsidios.class);
    
    /** (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        // TODO Auto-generated method stub
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager):No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        } else {
        	List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            //TODO Falta: realizar consulta para obtener la persona que Administra el subsidio
            return correo;
        }
    }

}
