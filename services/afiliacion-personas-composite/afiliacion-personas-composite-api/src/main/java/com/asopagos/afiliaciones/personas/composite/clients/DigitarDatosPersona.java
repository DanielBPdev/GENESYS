package com.asopagos.afiliaciones.personas.composite.clients;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import com.asopagos.dto.AfiliadoInDTO;
import java.util.Map;

import com.asopagos.services.common.ServiceClient;

public class DigitarDatosPersona extends ServiceClient{

    private AfiliadoInDTO afiliado;

    private Map<String, Object> result;
  
 	public DigitarDatosPersona (AfiliadoInDTO afiliadoIn){
 		super();
		this.afiliado = afiliadoIn;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliado == null ? null : Entity.json(afiliado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = response.readEntity(new GenericType<Map<String, Object>>() {});
	}

	/** Retorna el resultado del llamado al servicio */
	public Map<String, Object> getResult() {
		return result;
	}
    
}
