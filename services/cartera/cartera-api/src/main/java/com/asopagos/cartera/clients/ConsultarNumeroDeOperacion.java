package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/ConsultarNumeroDeOperacion
 */
public class ConsultarNumeroDeOperacion extends ServiceClient {

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private Long result;

    private String numeroIdentificacion;

    public ConsultarNumeroDeOperacion(String numeroIdentificacion) {
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
        this.result = response.readEntity(Long.class);
    }

    public Long getResult() {
        return this.result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
