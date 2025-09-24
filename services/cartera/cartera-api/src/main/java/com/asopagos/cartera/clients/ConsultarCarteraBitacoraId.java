package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarCarteraBitacoraId
 */
public class ConsultarCarteraBitacoraId extends ServiceClient {

    private Long idPersona;

    private Long result;

    public ConsultarCarteraBitacoraId(Long idCartera) {
        super();
        this.idPersona = idCartera;
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
        this.result = (Long) response.readEntity(Long.class);
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
