package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ResponsableCajaUno extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ResponsableCajaUno.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     *      java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) ");
        //Persona obteniendola de una Entidad Pagadora, Persona por solicitud de novedad de persona verificando que sea aportante : Pendiente
        //persona obteniendola de una solicitud de afiliacion de empleador, Persona por solicitud de novedad de empleador

        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        } else {
        	List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
    		CorreoPrioridadPersonaDTO correoRequest = new CorreoPrioridadPersonaDTO();

            if (TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)) {
                try {
                    List<String> nombreUsuarios = new ArrayList<String>();
                    List<Object[]> result = new ArrayList<Object[]>();
                    result = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_NOVEDAD, Object[].class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    for (Object[] objects : result) {
						UsuarioCCF usuario = obtenerDatoUsuario(objects[0].toString());
						if (usuario!=null) {
                        	correoRequest.setEmail(usuario.getNombreUsuario());
                        	correoRequest.setIdEmpleador((Long)(objects[1]));
                        	correoRequest.setAutorizaEnvio((Boolean)(objects[2]));
                            correo.add(correoRequest);                            
                        }
					}
                    return correo;
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if (TipoSolicitudEnum.EMPLEADOR.equals(solicitante)) {//No aplica
                try {
                    List<String> nombreUsuarios = new ArrayList<String>();
                    List<Object[]> result = new ArrayList<Object[]>();
                    result = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_AFILIACION,Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    for (Object[] objects : result) {
						UsuarioCCF usuario = obtenerDatoUsuario(objects[0].toString());
						if (usuario!=null) {
                        	correoRequest.setEmail(usuario.getEmail());
                        	correoRequest.setIdEmpleador(Long.valueOf(objects[1].toString()));
                            correo.add(correoRequest);                            
                        }
					}
                    return correo;
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else {
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) : No se resuelve la consulta para el tipo de solicitante");
                return null;
            }
        }
    }
    
    /**
     * <b>Descripción</b>Método encargado de recuperar los datos del usuario<br/>
     * @param clave,
     *              clave que indica que dato del usuario recuperar
     * @param nombreUsuario, nombre del usuario al que se le recuperarán datos
     * @return  String, dato del usuario a retornar
     */
    private UsuarioCCF obtenerDatoUsuario(String nombreUsuario){
        logger.debug("Inicia obtenerDatoUsuario(String)");
        try {
            UsuarioCCF usuarioDTO = new UsuarioCCF();
            ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacion = new ObtenerDatosUsuarioCajaCompensacion(nombreUsuario,null,null,false);
            obtenerDatosUsuariosCajaCompensacion.execute();
            usuarioDTO = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacion.getResult();
            logger.debug("Finaliza obtenerDatoUsuario(String)");
            return usuarioDTO;
        }catch(TechnicalException te) {
            logger.debug("Finaliza obtenerDatoUsuario(String): error inesperado");
        }
        return null;
    }
}
