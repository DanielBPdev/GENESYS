package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/actualizarDeudaRealPorIdCartera
 */
public class ActualizarDeudaRealPorIdCarteraServices extends ServiceClient {


    private String numeroIdentificacion;

    public ActualizarDeudaRealPorIdCarteraServices(String numeroIdentificacion) {
        super();
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
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
}