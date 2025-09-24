package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.constants.MensajesGeneralConstants;
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
 * <b>Descripcion:</b> Clase que implementa el responsable del JefeHogar<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:flopez@heinsohn.com.co"> clmarin</a>
 */

public class ResponsableJefeHogar extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ResponsableJefeHogar.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(java.util.Map,
     *      javax.persistence.EntityManager)
     */
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
        
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager):No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
        	List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            List<Persona> correoPersona = new ArrayList<Persona>();            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
            
            if(TipoSolicitudEnum.POSTULACION_FOVIS.equals(solicitante)){
                try{
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACION_FOVIS_ENVIO_CORRESPONDENCIA, Persona.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.NOVEDAD_FOVIS.equals(solicitante)){
                try{
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_FOVIS_ENVIO_CORRESPONDENCIA, Persona.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.LEGALIZACION_FOVIS.equals(solicitante)){
                try{
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_LEGALIZACION_DESEMBOLSO_FOVIS_ENVIO_CORRESPONDENCIA, Persona.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.POSTULACION_FOVIS_VERIFICACION.equals(solicitante)){
                try{
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENVIO_CORRESPONDENCIA_POSTULACION_FOVIS_VERIFICACION, Persona.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            }
            if(correoPersona!=null)
                correo = correoPersona.stream().map(new Function<Persona, CorreoPrioridadPersonaDTO>() {
                    public CorreoPrioridadPersonaDTO apply(Persona s) {
                        return new CorreoPrioridadPersonaDTO(s.getUbicacionPrincipal().getEmail(), s.getIdPersona(),s.getUbicacionPrincipal().getAutorizacionEnvioEmail());
                    }
                }).collect(Collectors.toList());
            
            return correo;
        }

    }
}
