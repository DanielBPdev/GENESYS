package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.FiltroIdPersonaDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/ConsultarNumeroTipoIdPersona
 */
public class ConsultarNumeroTipoIdPersona extends ServiceClient {

    private Long idPersona;

    private FiltroIdPersonaDTO result;

    public ConsultarNumeroTipoIdPersona(Long idPersona) {
        super();
        this.idPersona = idPersona;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idPersona", idPersona)
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = (FiltroIdPersonaDTO) response.readEntity(new GenericType<FiltroIdPersonaDTO>() {
        });
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public FiltroIdPersonaDTO getResult() {
        return result;
    }

    public void setResult(FiltroIdPersonaDTO result) {
        this.result = result;
    }
}
