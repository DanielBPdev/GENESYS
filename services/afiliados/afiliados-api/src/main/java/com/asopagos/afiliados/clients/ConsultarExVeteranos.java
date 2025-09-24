package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;

import com.asopagos.services.common.ServiceClient;

public class ConsultarExVeteranos extends ServiceClient{
    private List<RolafiliadoNovedadAutomaticaDTO> result;

    public ConsultarExVeteranos (){
        super();
    }

    @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path).request(MediaType.APPLICATION_JSON).get();
		return response;
	}

    @Override
	protected void getResultData(Response response) {
		this.result = (List<RolafiliadoNovedadAutomaticaDTO>) response.readEntity(new GenericType<List<RolafiliadoNovedadAutomaticaDTO>>(){});
	}

    public List<RolafiliadoNovedadAutomaticaDTO> getResult() {
        return result;
    }

}

