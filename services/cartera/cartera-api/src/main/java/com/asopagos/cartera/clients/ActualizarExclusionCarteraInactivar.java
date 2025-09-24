package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/actualizarExclusionCarteraInactivar
 */
public class ActualizarExclusionCarteraInactivar extends ServiceClient {
    private Long idPersona;

    public ActualizarExclusionCarteraInactivar(Long idPersona) {
        super();
        this.idPersona = idPersona;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idPersona", idPersona)
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }
}
