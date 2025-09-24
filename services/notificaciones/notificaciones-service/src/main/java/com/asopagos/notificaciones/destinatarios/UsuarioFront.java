package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.afiliaciones.clients.BuscarSolicitudPorId;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class UsuarioFront extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(UsuarioFront.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     *      java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
        //Persona obteniendola de una Entidad Pagadora, Persona por solicitud de novedad de persona verificando que sea aportante : Pendiente
        //persona obteniendola de una solicitud de afiliacion de empleador,    
        //Persona por solicitud de novedad de empleador

        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager):No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
            List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            Solicitud solicitud = buscarSolicitudPorId((Long) parametros.get(ID_SOLICITUD));
            UsuarioCCF usuarios = obtenerDatoUsuario(solicitud.getUsuarioRadicacion());
            if (usuarios != null && usuarios.getEmail() != null) {
                CorreoPrioridadPersonaDTO correoRequest = new CorreoPrioridadPersonaDTO();
                correoRequest.setEmail(usuarios.getEmail());
                correo.add(correoRequest);
            }
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
            return correo;
        }
    }

    /**
     * <b>Descripción</b>Método encargado de recuperar los datos del usuario<br/>
     * @param clave,
     *        clave que indica que dato del usuario recuperar
     * @param nombreUsuario,
     *        nombre del usuario al que se le recuperarán datos
     * @return String, dato del usuario a retornar
     */
    private UsuarioCCF obtenerDatoUsuario(String nombreUsuario) {
        logger.debug("Inicia obtenerDatoUsuario(String, String)");
        try {
            UsuarioCCF usuarioDTO = new UsuarioCCF();
            ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacion = new ObtenerDatosUsuarioCajaCompensacion(
                    nombreUsuario, null, null, false);
            obtenerDatosUsuariosCajaCompensacion.execute();
            usuarioDTO = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacion.getResult();
            logger.debug("Finaliza obtenerDatoUsuario(String, String)");
            return usuarioDTO;
        } catch (TechnicalException te) {
            logger.debug("Finaliza obtenerDatoUsuario(String, String): error inesperado");
        }
        return null;
    }
    
    /**
     * 
     * @param nombreUsuario
     * @return
     */
    private Solicitud buscarSolicitudPorId(Long idSolicitud) {
        logger.debug("Inicia buscarSolicitudPorId(Long idSolicitud)");
        try {
            BuscarSolicitudPorId buscarSolicitudPorId = new BuscarSolicitudPorId(idSolicitud);
            buscarSolicitudPorId.execute();
            logger.debug("Finaliza buscarSolicitudPorId(Long idSolicitud)");
            return buscarSolicitudPorId.getResult();
        } catch (TechnicalException te) {
            logger.debug("Finaliza buscarSolicitudPorId(Long idSolicitud)");
        }
        return null;
    }
}
