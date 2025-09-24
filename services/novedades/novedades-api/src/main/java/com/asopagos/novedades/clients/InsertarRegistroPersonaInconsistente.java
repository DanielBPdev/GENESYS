package com.asopagos.novedades.clients;

import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/InsertarRegistroPersonaInconsistente
 */
public class InsertarRegistroPersonaInconsistente extends ServiceClient {
    private RegistroPersonaInconsistenteDTO registroPersonaInconsistenteDTO;

    public InsertarRegistroPersonaInconsistente(RegistroPersonaInconsistenteDTO registroPersonaInconsistenteDTO) {
        super();
        this.registroPersonaInconsistenteDTO = registroPersonaInconsistenteDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(registroPersonaInconsistenteDTO == null ? null : Entity.json(registroPersonaInconsistenteDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {

    }
}