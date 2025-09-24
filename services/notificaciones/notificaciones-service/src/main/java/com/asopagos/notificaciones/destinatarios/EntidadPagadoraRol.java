package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
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

public class EntidadPagadoraRol extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(EntidadPagadoraRol.class);
    
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
        logger.debug("Inicia obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) ");
        //Persona obteniendola de una Entidad Pagadora, Persona por solicitud de novedad de persona verificando que sea aportante : Pendiente
        //persona obteniendola de una solicitud de afiliacion de empleador, Persona por solicitud de novedad de empleador

        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
            List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
            List<EntidadPagadora> correoPersona = new ArrayList<EntidadPagadora>();

            if(TipoSolicitudEnum.APORTES.equals(solicitante)){
                try {
                    Boolean esEmpresa;
                    try {
                        idEmpresa = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_APORTE, Long.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getSingleResult();
                        esEmpresa = true;
                    } catch (Exception e) {
                        esEmpresa = false;
                    }
                    if(esEmpresa.equals(true)){
                        List<Object[]> result = new ArrayList<>();
                        result = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_ENTIDAD_PAGADORA_EMPLEADOR, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                        if (result != null && !result.isEmpty()) {
                            correoPersona.add((EntidadPagadora)result.get(0)[0]);
                            autorizaEnvio = (Boolean)result.get(0)[1];
                        }
                    } else {
                    	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_ENTIDAD_PAGADORA_INDP_PENS, EntidadPagadora.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();                        
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.APORTES_CORRECCION.equals(solicitante)){
                try {
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_CORRECCION_ENTIDAD_PAGADORA, EntidadPagadora.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.APORTES_DEVOLUCION.equals(solicitante)){
                try {
                	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_DEVOLUCION_ENTIDAD_PAGADORA, EntidadPagadora.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else {
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) : No se resuelve la consulta para el tipo de solicitante");
                return null;
            }
            if(correoPersona!=null)
	            correo = correoPersona.stream().map(new Function<EntidadPagadora, CorreoPrioridadPersonaDTO>() {
	                public CorreoPrioridadPersonaDTO apply(EntidadPagadora s) {
	                    return new CorreoPrioridadPersonaDTO(s.getEmailComunicacion(), s.getEmpresa().getPersona().getIdPersona(),null,idEmpresa,
	                            idEmpresa != null ? autorizaEnvio : s.getEmpresa().getPersona().getUbicacionPrincipal().getAutorizacionEnvioEmail());
	                }
	            }).collect(Collectors.toList());
            
            return correo;
        }
    }
}
