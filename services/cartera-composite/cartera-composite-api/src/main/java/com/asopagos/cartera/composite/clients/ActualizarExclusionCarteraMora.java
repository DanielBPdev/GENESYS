package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/actualizarExclusionCarteraMora
 */
public class ActualizarExclusionCarteraMora extends ServiceClient {
    private List<ExclusionCarteraDTO> exclusionCarteraDTO;

    public ActualizarExclusionCarteraMora(List<ExclusionCarteraDTO> exclusionCarteraDTO) {
        super();
        this.exclusionCarteraDTO = exclusionCarteraDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(exclusionCarteraDTO == null ? null : Entity.json(exclusionCarteraDTO));
    }

    @Override
    protected void getResultData(Response response) {
    }


    public List<ExclusionCarteraDTO> getExclusionCarteraDTO() {
        return exclusionCarteraDTO;
    }

    public void setExclusionCarteraDTO(List<ExclusionCarteraDTO> exclusionCarteraDTO) {
        this.exclusionCarteraDTO = exclusionCarteraDTO;
    }
}