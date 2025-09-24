package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.UsuarioDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class SupervisorCartera extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SupervisorCartera.class);

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
    		
            List<UsuarioDTO> usuarios = obtenerMiembrosGrupo("SupCar", null, EstadoUsuarioEnum.ACTIVO);
            if (usuarios!=null){
                for (UsuarioDTO usuarioDTO : usuarios) {
                    if(usuarioDTO.getEmail()!=null){
                        CorreoPrioridadPersonaDTO correoRequest = new CorreoPrioridadPersonaDTO();
                        correoRequest.setEmail(usuarioDTO.getEmail());
                        correo.add(correoRequest);    
                    }
                }
            }
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
            return correo;
        }
    }

    private List<UsuarioDTO> obtenerMiembrosGrupo(String idGrupo, String sede, EstadoUsuarioEnum estado) {
        logger.debug("Inicia obtenerMiembrosGrupo(String, String, EstadoUsuarioEnum)");
        try {
            ObtenerMiembrosGrupo obtenerUsuarios = new ObtenerMiembrosGrupo(idGrupo, sede, estado);
            obtenerUsuarios.execute();
            logger.debug("Finaliza obtenerMiembrosGrupo(String, String, EstadoUsuarioEnum)");
            return obtenerUsuarios.getResult();
        } catch (TechnicalException te) {
            logger.debug("Finaliza obtenerMiembrosGrupo(String, String, EstadoUsuarioEnum): error inesperado");
        }
        return null;
    }
}
