package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
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

public class ResponsableSubsidios extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ResponsableSubsidios.class);
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
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager):No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
        	List<Object[]> correo = new ArrayList<Object[]>();
            List<CorreoPrioridadPersonaDTO> correoResponse = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
            
            if(TipoSolicitudEnum.NOVEDAD_PERSONA.equals(solicitante)){//No aplica
                try{
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESPONSABLE_SUBSIDIOS_PERSONA, Object[].class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();                    
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if (TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)){//No aplica
                try {
                    Boolean autorizacion = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_NOVEDAD_EMPLEADOR, Boolean.class)
                            .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getSingleResult();
                    if(autorizacion.equals(true)){
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESPONSABLE_SUBSIDIOS_EMPLEADOR, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                        idEmpleador = (correo.get(0)[2]!= null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.APORTES.equals(solicitante)){
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
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_RESPONSABLE_SUBSIDIOS_EMPLEADOR, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                        autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                    } else {
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTANTE_RESPONSABLE_SUBSIDIOS_INDP_PENS, Object[].class)//No aplica
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.GESTION_PREVENTIVA.equals(solicitante)){
                try {
                    Boolean esEmpleador;
                    try {
                        autorizaEnvio = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_GESTION_PREVENTIVA, Boolean.class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getSingleResult();
                        esEmpleador = true;
                    } catch (Exception e) {
                        esEmpleador = false;
                    }
                    if(esEmpleador.equals(true)){
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_SUBSIDIOS_EMPLEADOR, Object[].class)
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();  
                        idEmpleador = (correo.get(0)[2] != null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    } else {
                        correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_SUBSIDIOS_INDP_PENS, Object[].class)//No aplica
                                .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
                    }
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else if(TipoSolicitudEnum.CARTERA.equals(solicitante)){//No aplica
                try {
                    ParametrosComunicadoDTO parametrosComunicadoDTO = (ParametrosComunicadoDTO) parametros.get(MAPA);
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AUTORIZACION_CARTERA_SUBSIDIO, Object[].class)
                            .setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion())
                            .setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion()).getResultList();
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            }else if(TipoSolicitudEnum.GESTION_COBRO_MANUAL.equals(solicitante)){
                try {
                    ParametrosComunicadoDTO parametrosComunicadoDTO = (ParametrosComunicadoDTO) parametros.get(MAPA);
                    
                    correo = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_COBRO_MANUAL_ROL_EMPLEADOR, Object[].class)
                            .setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion())
                            .setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion())
                            .setParameter("rol", TipoRolContactoEnum.ROL_RESPONSABLE_SUBSIDIOS).getResultList();
                    if (correo == null || correo.isEmpty()){
                        return null;
                    }
                    idEmpleador = (correo.get(0)[2] != null ? Long.valueOf(correo.get(0)[2].toString()) : null);
                    autorizaEnvio = (correo.get(0)[3] != null ? (Boolean)correo.get(0)[3] : Boolean.FALSE);
                } catch (Exception e){
                    logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                    return null;
                }
            } else {
                //Falta Persona obteniendola de una Entidad Pagadora, Persona por solicitud de novedad de persona verificando que sea aportante : Pendiente
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
