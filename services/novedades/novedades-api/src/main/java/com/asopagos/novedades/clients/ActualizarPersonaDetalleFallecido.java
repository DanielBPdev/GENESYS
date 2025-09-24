package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/actualizarPersonaDetalleFallecido
 */
public class ActualizarPersonaDetalleFallecido extends ServiceClient {

    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
    private PersonaDetalleModeloDTO personaDetalleModeloDTO;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */

    public ActualizarPersonaDetalleFallecido(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion, PersonaDetalleModeloDTO personaDetalleModeloDTO) {
        super();
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.personaDetalleModeloDTO=personaDetalleModeloDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .request(MediaType.APPLICATION_JSON)
                .put(personaDetalleModeloDTO == null ? null : Entity.json(personaDetalleModeloDTO));
    }


    @Override
    protected void getResultData(Response response) {
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
}