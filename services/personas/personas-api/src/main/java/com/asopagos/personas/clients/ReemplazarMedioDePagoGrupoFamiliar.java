package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.Long;
import java.util.List;

import com.asopagos.services.common.ServiceClient;

public class ReemplazarMedioDePagoGrupoFamiliar extends ServiceClient{

    private List<Long> idsGruposFamiliar;

    private Long idMedioDePagoTarjeta;

    public ReemplazarMedioDePagoGrupoFamiliar (List<Long> idsGruposFamiliar,Long idMedioDePagoTarjeta){
        super();
        this.idsGruposFamiliar=idsGruposFamiliar;
        this.idMedioDePagoTarjeta = idMedioDePagoTarjeta;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
    Response response = webTarget.path(path)
        .queryParam("idMedioPagoTarjeta", idMedioDePagoTarjeta)
        .request(MediaType.APPLICATION_JSON)
        .post(idsGruposFamiliar == null ? null : Entity.json(idsGruposFamiliar));
    return response;
    }

    @Override
	protected void getResultData(Response response) {
	}
    
}
