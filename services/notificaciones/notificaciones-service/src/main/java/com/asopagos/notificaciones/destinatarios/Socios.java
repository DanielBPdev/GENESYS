package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.DetalleDestinatarioDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class Socios extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(Socios.class);
    /**
     * Identificador del empleador (en caso de ser objeto del comunicado)
     */
    private Long idEmpleador;
    
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
            List<Persona> correoPersona = new ArrayList<Persona>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
            List<DetalleDestinatarioDTO> detalleDestinatario = new ArrayList<DetalleDestinatarioDTO>();
            
            if(TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)){//No aplica
                try {
                    Boolean autorizacion = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_NOVEDAD_EMPLEADOR, Boolean.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getSingleResult();
                    if(autorizacion.equals(true)){
                    	detalleDestinatario = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOCIOS_POR_SOLICITUD_NOVEDAD, DetalleDestinatarioDTO.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    	for (DetalleDestinatarioDTO detalleDestinatarioDTO : detalleDestinatario) {
							correoPersona.add(detalleDestinatarioDTO.getPersona());
						}
                    	idEmpleador = (detalleDestinatario.get(0) != null ? detalleDestinatario.get(0).getIdEmpleador() : null);
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else {
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) : No se resuelve la consulta para el tipo de solicitante");
                return null;    
            }
            if(correoPersona!=null)
	            correo = correoPersona.stream().map(new Function<Persona, CorreoPrioridadPersonaDTO>() {
	                public CorreoPrioridadPersonaDTO apply(Persona s) {
	                    return new CorreoPrioridadPersonaDTO(s.getUbicacionPrincipal().getEmail(), s.getIdPersona(), idEmpleador);
	                }
	            }).collect(Collectors.toList());
            
            return correo;
        }
    }

}
