package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.DetalleDestinatarioDTO;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class InformacionContacto extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(InformacionContacto.class);
    /**
     * Identificador del empleador (en caso de ser objeto del comunicado)
     */
    private Long idEmpleador;

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum,
     *      java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
    	List<CorreoPrioridadPersonaDTO> correo = new ArrayList<CorreoPrioridadPersonaDTO>();
        List<Persona> correoPersona = new ArrayList<Persona>();        
        TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
        List<DetalleDestinatarioDTO> detalleDestinatario = new ArrayList<DetalleDestinatarioDTO>();

        if (TipoSolicitudEnum.NOVEDAD_PERSONA.equals(solicitante)) {//NO APLICA
            try {
            	correoPersona = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL, Persona.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
            } catch (Exception e){
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                return null;
            }
        }
        else if (TipoSolicitudEnum.NOVEDAD_EMPLEADOR.equals(solicitante)) {//NO APLICA
            try {
            	detalleDestinatario = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL, DetalleDestinatarioDTO.class)
                        .setParameter("idSolicitud", parametros.get(ID_SOLICITUD)).getResultList();
            	for (DetalleDestinatarioDTO detalleDestinatarioDTO : detalleDestinatario) {
					correoPersona.add(detalleDestinatarioDTO.getPersona());
				}
            	idEmpleador = (detalleDestinatario.get(0) != null ? detalleDestinatario.get(0).getIdEmpleador() : null);
            	
            } catch (Exception e){
                logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :Error técnico inesperado:"+e);
                return null;
            }
        } else {
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
