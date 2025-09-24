package com.asopagos.notificaciones.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.PrioridadDestinatario;
import com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO; 

/**
 * Servicio de notificaciones con la logica común de persistencia
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
@Stateless
public interface SendNotificacionesService {

    /**
     * Método que se encarga de envíar un correo y persistir el comunicado
     * 
     * @param notificacion
     * @param userDTO
     */
    public NotificacionEnviada enviarEmail(@NotNull @Valid NotificacionDTO notificacion, @Context UserDTO userDTO);
    
    /**
     * Método que se encarga de envíar un correo parametrizado y persistir el comunicado
     * 
     * @param notificacion
     * @param userDTO
     */
    public void enviarEmailParametrizado(@NotNull @Valid NotificacionParametrizadaDTO notificacion, @Context UserDTO userDTO);
    
    /**
     * Método que se encarga de envíar varios correo parametrizado y persistir el comunicado, en una misma conexión, 
     * sin hacer uso de la priorización de los destinatarios parametrizados
     * 
     * @param notificaciones
     * @param userDTO
     */
    public void enviarMultiplesCorreosPorConexion(@NotNull @Valid List<NotificacionParametrizadaDTO> notificaciones, @Context UserDTO userDTO);
    
    public ResultadoEnvioComunicadoCarteraDTO enviarEmailImplementacionCartera(@NotNull @Valid NotificacionDTO notificacion, @Context UserDTO userDTO);

    /**
     * Método que se encarga de envíar un correo y persistir el comunicado, en el caso de tratarse de un comunicado con prioridades de
     * destinatario sobre escribe el destinatario con el destinatario de la prioridad en curso
     * 
     * @param notificacion
     * @param userDTO
     * @param plantilla
     * @param destinatariosPrioridad 
     * @param prioridades
     * @return
     */
    public NotificacionEnviada enviarEmail(@NotNull @Valid NotificacionDTO notificacion, @Context UserDTO userDTO,
            PlantillaComunicado plantilla, List<PrioridadDestinatario> listaPrioridadDestinatario);
}
