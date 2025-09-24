package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarFirmezaTituloBitacora
 */
public class ConsultarFirmezaTituloBitacora extends ServiceClient {

    private Long idPersona;

    public ConsultarFirmezaTituloBitacora(Long idPersona) {
        super();
        this.idPersona = idPersona;
    }

    private String result;

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
        result = (String) response.readEntity(new GenericType<String>() {
        });
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
