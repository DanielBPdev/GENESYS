package com.asopagos.afiliaciones.wsCajasan.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.ResponseDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.GetMunicipiosCalendarioInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.GetMunicipiosCalendarioOutDTO;
import javax.ws.rs.client.Entity;

public class GetMunicipiosCalendario extends ServiceClient{

    private String codigoDepartamento;
    private ResponseDTO result;


    public GetMunicipiosCalendario (String codigoDepartamento){
        super();
        this.codigoDepartamento = codigoDepartamento;
    }

	@Override
    protected Response invoke(WebTarget webTarget, String path) {
        GetMunicipiosCalendarioInDTO input = new GetMunicipiosCalendarioInDTO(codigoDepartamento);

        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON));
    }

	@Override
	protected void getResultData(Response response) {
		this.result = (ResponseDTO) response.readEntity(new GenericType<ResponseDTO>(){});
	}

	public ResponseDTO getResult() {
		return result;
	}

    public String getCodigoDepartamento(){
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }
}
