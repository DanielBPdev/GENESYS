package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class TrabajadorDependiente extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(TrabajadorDependiente.class);
    
    /** (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum, java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");

        //Persona por solicitud  de afiliacion de una persona, 
        //persona (nombre empleador)solicitud por afiliacion de empleador, 
        //Persona por solicitud de novedad de persona
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
        	List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
			CorreoPrioridadPersonaDTO correoRequest = new CorreoPrioridadPersonaDTO();
            if(TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)){
                try {
                    List<Empleador> empleadores = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO, Empleador.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();

                    for (Empleador empleador : empleadores) {
                        if (empleador.getEmpresa().getPersona().getUbicacionPrincipal().getEmail() != null) {
                        	correoRequest.setEmail(empleador.getEmpresa().getPersona().getUbicacionPrincipal().getEmail());
                        	correoRequest.setPersona(empleador.getEmpresa().getPersona().getIdPersona());
                        	correoRequest.setIdEmpleador(empleador.getIdEmpleador());
                        	correo.add(correoRequest);
                        }
                    }
                    return correo;
                } catch (NoResultException nre) {
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No fue encontrada la solicitud");
                    return null;
                } catch (NullPointerException ne){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :El empleador no contiene la ubicacion");
                    return null;
                }
            }else if (TipoSolicitudEnum.EMPLEADOR.equals(solicitante)){
                try {
                    List<Empleador> empleadores = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_SOLICITUD_AFILIACION_ROL_AFILIADO, Empleador.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();

                    for (Empleador empleador : empleadores) {
                        if (empleador.getEmpresa().getPersona().getUbicacionPrincipal().getEmail() != null) {
                        	correoRequest.setEmail(empleador.getEmpresa().getPersona().getUbicacionPrincipal().getEmail());
                        	correoRequest.setPersona(empleador.getEmpresa().getPersona().getIdPersona());
                        	correoRequest.setIdEmpleador(empleador.getIdEmpleador());
                        	correo.add(correoRequest);
                        }
                    }
                    return correo;
                } catch (NoResultException nre) {
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No fue encontrada la solicitud");
                    return null;
                } catch (NullPointerException ne){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :El empleador no contiene la ubicacion");
                    return null;
                }
            } else if(TipoSolicitudEnum.NOVEDAD_PERSONA.equals(solicitante)){
                try {
                    List<Persona> personas = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_DEPENDIENTE, Persona.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();

                    for (Persona persona : personas) {
                        if (persona.getUbicacionPrincipal().getEmail() != null) {
                        	correoRequest.setEmail(persona.getUbicacionPrincipal().getEmail());
                        	correoRequest.setPersona(persona.getIdPersona());
                            correo.add(correoRequest);
                        }
                    }
                    return correo;
                } catch (NoResultException nre) {
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No fue encontrada la solicitud");
                    return null;
                } catch (NullPointerException ne){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :La persona no contiene la ubicacion");
                    return null;
                }
            }else if (TipoSolicitudEnum.PERSONA.equals(solicitante)){
                try {
                    List<Persona> personas = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_DEPENDIENTE, Persona.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();

                    for (Persona persona : personas) {
                        if (persona.getUbicacionPrincipal().getEmail() != null) {
                        	correoRequest.setEmail(persona.getUbicacionPrincipal().getEmail());
                        	correoRequest.setPersona(persona.getIdPersona());
                            correo.add(correoRequest);
                        }    
                    }
                    return correo;
                } catch (NoResultException nre) {
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No fue encontrada la solicitud");
                    return null;
                } catch (NullPointerException ne){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :La persona no contiene la ubicacion");
                    return null;
                }
            } else {
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) : No se resuelve la consulta para el tipo de solicitante");
                return null;    
            }
        }
    }
}
