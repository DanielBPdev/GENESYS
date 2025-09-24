package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class OficinaPrincipal extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(OficinaPrincipal.class);
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
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) ");
        // Persona obteniendola de una Entidad Pagadora,  
        //persona obteniendola de una solicitud de afiliacion de empleador
        
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
        	List<Object[]> correo = new ArrayList<Object[]>();
            List<CorreoPrioridadPersonaDTO> correoResponse = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);

            if (TipoSolicitudEnum.EMPLEADOR.equals(solicitante)){
                try{
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_AFILIACION,Object[].class)
                                    .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if (TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)){//NO APLICA
                try{
                   correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_NOVEDAD, Object[].class)
                                    .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                   idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if (TipoSolicitudEnum.NOVEDAD_PERSONA.equals(solicitante)) {//Novedades de dependiente
                try{
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_NOVEDAD_PERSONA_DEPWEB, Object[].class)
                                     .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                 } catch (Exception e){
                     logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                     return null;
                 }
            } else if(TipoSolicitudEnum.APORTES.equals(solicitante)){//No aplica
                try {
                    Boolean autorizacion;
                    try {
                        autorizacion = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_APORTE, Boolean.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getSingleResult();
                    } catch (Exception e) {
                        autorizacion=false;
                    }
                    if(autorizacion.equals(true)){
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_OFICINA_PRINCIPAL_EMPLEADOR, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();                        
                    } else {
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_OFICINA_PRINCIPAL_INDP_PENS, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.GESTION_PREVENTIVA.equals(solicitante)){
                try {
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_PREVENTIVA_UBICACION_PRINCIPAL, Object[].class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.CARTERA.equals(solicitante) || TipoSolicitudEnum.GESTION_COBRO_MANUAL.equals(solicitante)){
                try {
                    ParametrosComunicadoDTO parametrosComunicadoDTO = (ParametrosComunicadoDTO) parametros.get(MAPA);
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_CARTERA_PRINCIPAL, Object[].class)
                            .setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion())
                            .setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion()).getResultList();
                    idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if (TipoSolicitudEnum.PERSONA.equals(solicitante)){
                try {
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACION_PERSONA_UBICACION_EMPRESA_UBICACION_PRINCIPAL,
                                    Object[].class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                } catch (Exception e) {
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            }else if (TipoSolicitudEnum.PILA.equals(solicitante)){
                try {
                    ParametrosComunicadoDTO parametrosComunicadoDTO = (ParametrosComunicadoDTO) parametros.get(MAPA);
                    if (TipoTipoSolicitanteEnum.EMPLEADOR.equals(parametrosComunicadoDTO.getTipoSolicitante())){
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PILA_UBICACION_EMPLEADOR, Object[].class)
                                .setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion())
                                .setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion())
                                .setParameter("tipoUbicacion", TipoUbicacionEnum.UBICACION_PRINCIPAL).getResultList();
                        if (correo == null || correo.isEmpty()){
                            return null;
                        }
                        idEmpresa = (correo.get(0)[1] != null ? Long.valueOf(correo.get(0)[1].toString()) : null);
                        autorizaEnvio = (correo.get(0)[2] != null ? (Boolean)correo.get(0)[2] : Boolean.FALSE);
                    }
                    
                } catch (Exception e) {
                    return null;
                }
            }else {
                //Falta Persona obteniendola de una Entidad Pagadora: Pendiente
                return null;
            } 
            correoResponse = correo.stream().map(new Function<Object[], CorreoPrioridadPersonaDTO>() {
                public CorreoPrioridadPersonaDTO apply(Object[] s) {
                	if(s[0]==null)
                		return new CorreoPrioridadPersonaDTO(null, Long.valueOf(s[1].toString()), idEmpleador, idEmpresa, autorizaEnvio);
                	else
                		return new CorreoPrioridadPersonaDTO(s[0].toString(), Long.valueOf(s[1].toString()), idEmpleador, idEmpresa, autorizaEnvio);
                }
            }).collect(Collectors.toList());
            
            return correoResponse;
        }
    }

}
