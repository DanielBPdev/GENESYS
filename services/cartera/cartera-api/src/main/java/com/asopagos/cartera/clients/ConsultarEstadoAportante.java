package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/ConsultarEstadoAportante
 */
public class ConsultarEstadoAportante extends ServiceClient {
    private Long idPersona;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private String result;

    public ConsultarEstadoAportante(Long idPersona) {
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

    /**
     * Retorna el resultado del llamado al servicio
     */
    @Override
    protected void getResultData(Response response) {
        this.result = (String) response.readEntity(String.class);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdPersona() {
        return idPersona;
    }


}