package com.asopagos.historicos.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.entidades.transversal.core.EstadisticasGenesys;

import com.asopagos.historicos.dto.HistoricoEstadisticasGenesysDTO;

import com.asopagos.services.common.ServiceClient;

public class ObtenerEstadisticasGenesys extends ServiceClient {

    private HistoricoEstadisticasGenesysDTO result;

    public ObtenerEstadisticasGenesys(){
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
        this.result = (HistoricoEstadisticasGenesysDTO) response.readEntity(HistoricoEstadisticasGenesysDTO.class);
    }

    public HistoricoEstadisticasGenesysDTO getResult() {
        return result;
    }

}