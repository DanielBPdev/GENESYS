package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/ConsultarCarteraLineaDeCobro2C
 */
public class ConsultarCarteraLineaDeCobro2C extends ServiceClient {

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private List<Object[]> result;

    public ConsultarCarteraLineaDeCobro2C() {
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
        this.result = (List<Object[]>) response.readEntity(new GenericType<List<Object[]>>() {
        });
    }


    public List<Object[]> getResult() {
        return result;
    }

    public void setResult(List<Object[]> result) {
        this.result = result;
    }
}
