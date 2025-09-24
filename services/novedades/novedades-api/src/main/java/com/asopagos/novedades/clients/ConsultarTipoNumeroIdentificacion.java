package com.asopagos.novedades.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarTipoNumeroIdentificacion
 */
public class ConsultarTipoNumeroIdentificacion extends ServiceClient {
    private String numeroIdentificacion;


    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private Long result;

    public ConsultarTipoNumeroIdentificacion(String numeroIdentificacion) {
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
        this.result = (Long) response.readEntity(Long.class);
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
