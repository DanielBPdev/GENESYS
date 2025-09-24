package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.NotificacionNiyarakyActualizacionEstadoOutDTO;
import com.asopagos.dto.modelo.NotificacionNiyarakyActualizacionEstadoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Método que hace la petición REST al servicio POST
 * /rest/cartera/niyaraky/notificaciones/actualizarEstado
 */
public class CrearActualizacionEstadoNiyaraky extends ServiceClient {

    private NotificacionNiyarakyActualizacionEstadoInDTO notificacionInDTO;
    private NotificacionNiyarakyActualizacionEstadoOutDTO result;

    public CrearActualizacionEstadoNiyaraky(NotificacionNiyarakyActualizacionEstadoInDTO notificacionInDTO) {
        super();
        this.notificacionInDTO = notificacionInDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(notificacionInDTO == null ? null : Entity.json(notificacionInDTO));
    }

    @Override
    protected void getResultData(Response response) {
        result = response.readEntity(NotificacionNiyarakyActualizacionEstadoOutDTO.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public NotificacionNiyarakyActualizacionEstadoOutDTO getResult() {
        return result;
    }

    public void setNotificacionInDTO(NotificacionNiyarakyActualizacionEstadoInDTO notificacionInDTO) {
        this.notificacionInDTO = notificacionInDTO;
    }

    public NotificacionNiyarakyActualizacionEstadoInDTO getNotificacionInDTO() {
        return notificacionInDTO;
    }
}
