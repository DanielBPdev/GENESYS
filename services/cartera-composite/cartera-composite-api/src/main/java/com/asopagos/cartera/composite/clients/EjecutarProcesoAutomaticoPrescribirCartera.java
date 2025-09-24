package com.asopagos.cartera.composite.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/ejecutarProcesoAutomaticoPrescribirCartera
 */
public class EjecutarProcesoAutomaticoPrescribirCartera extends ServiceClient {

    public EjecutarProcesoAutomaticoPrescribirCartera() {
        super();
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

}
