package com.asopagos.fovis.clients;

import java.lang.Long;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarInformacionArchivoCruces
 */
public class ConsultarInformacionArchivoCruces extends ServiceClient {


    private Long idCargue;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<Object[]> result;

    public ConsultarInformacionArchivoCruces (Long idCargue){
        super();
        this.idCargue=idCargue;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idCargue", idCargue)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<Object[]>) response.readEntity(new GenericType<List<Object[]>>() {
        });
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<Object[]> getResult() {
        return result;
    }


    public void setIdCargue (Long idCargue){
        this.idCargue=idCargue;
    }

    public Long getIdCargue (){
        return idCargue;
    }

}