package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.Long;
import java.util.List;

import com.asopagos.services.common.ServiceClient;

public class RegistrarActualizacionTarjetaGrupoFamiliar extends ServiceClient{

    private Long idGrupo;

    private MedioDePagoModeloDTO medioDePagoModeloDTO;

    public RegistrarActualizacionTarjetaGrupoFamiliar(Long idGrupo,MedioDePagoModeloDTO medioDePagoModeloDTO){
        super();
        this.idGrupo=idGrupo;
        this.medioDePagoModeloDTO = medioDePagoModeloDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
    Response response = webTarget.path(path)
        .queryParam("idGrupo", idGrupo)
        .request(MediaType.APPLICATION_JSON)
        .post(medioDePagoModeloDTO == null ? null : Entity.json(medioDePagoModeloDTO));
        return response;
    }

    @Override
	protected void getResultData(Response response) {
	}
    
}
