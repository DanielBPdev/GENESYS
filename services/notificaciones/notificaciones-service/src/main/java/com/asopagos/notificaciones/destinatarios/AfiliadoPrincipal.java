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
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class AfiliadoPrincipal extends DestinatarioNotificacion{
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AfiliadoPrincipal.class);
    
    /**
     * Id del empleador que pueda estar asociado al comunicado
     */
    Long idEmpleador = null;
    
    /**
     * Id de la empresa que pueda estar asociada al comunicado
     */
    Long idEmpresa = null;
    
    /**
     * Indica si se autoriza el envio del comunicado.
     */
    Boolean autorizaEnvio = Boolean.FALSE;
    
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
            
            //NO APLICA
            if(TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)){
                try {
                    detalleDestinatario = em
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL,
                                    DetalleDestinatarioDTO.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    for (DetalleDestinatarioDTO detalleDestinatarioDTO : detalleDestinatario) {
                        correoPersona.add(detalleDestinatarioDTO.getPersona());
                    }
                    idEmpleador = (!detalleDestinatario.isEmpty() ? detalleDestinatario.get(0).getIdEmpleador() : null);
                    autorizaEnvio = (!detalleDestinatario.isEmpty() ? detalleDestinatario.get(0).getAutorizaEnvio() : null);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
                //NO APLICA
            }else if (TipoSolicitudEnum.EMPLEADOR.equals(solicitante)){
                try {
                    detalleDestinatario = em
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_PRINCIPAL,
                                    DetalleDestinatarioDTO.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();

                    for (DetalleDestinatarioDTO detalleDestinatarioDTO : detalleDestinatario) {
                        correoPersona.add(detalleDestinatarioDTO.getPersona());
                    }
                    	idEmpleador = (!detalleDestinatario.isEmpty() ? detalleDestinatario.get(0).getIdEmpleador() : null);
                    	autorizaEnvio = (!detalleDestinatario.isEmpty() ? detalleDestinatario.get(0).getAutorizaEnvio() : null);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.NOVEDAD_PERSONA.equals(solicitante)){
                try {
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL, Persona.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            }else if (TipoSolicitudEnum.PERSONA.equals(solicitante)){
                try {
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_PRINCIPAL, Persona.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.CARTERA.equals(solicitante)){
                try {
                    ParametrosComunicadoDTO parametrosComunicadoDTO = (ParametrosComunicadoDTO) parametros.get(MAPA);
                    correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_CARTERA_AFILIADO_PRINCIPAL, Persona.class)
                            .setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion())
                            .setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion()).getResultList();
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
	                    return new CorreoPrioridadPersonaDTO(s.getUbicacionPrincipal().getEmail(), s.getIdPersona(),idEmpleador, null, idEmpleador != null ? autorizaEnvio : s.getUbicacionPrincipal().getAutorizacionEnvioEmail());
	                }
	            }).collect(Collectors.toList());
            
            return correo;
        }
    }
}
