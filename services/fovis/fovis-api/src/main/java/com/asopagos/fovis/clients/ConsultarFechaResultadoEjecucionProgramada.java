package com.asopagos.fovis.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarFechaResultadoEjecucionProgramada
 */
public class ConsultarFechaResultadoEjecucionProgramada extends ServiceClient {


    private Long idResultado;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private Object result;

    public ConsultarFechaResultadoEjecucionProgramada(Long idResultado){
        super();
        this.idResultado=idResultado;

    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("id", idResultado)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (Object) response.readEntity(Object.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public Object getResult() {
        return result;
    }

    public Long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Long idResultado) {
        this.idResultado = idResultado;
    }
}