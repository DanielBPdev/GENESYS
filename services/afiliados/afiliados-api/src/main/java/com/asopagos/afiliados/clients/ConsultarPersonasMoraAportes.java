package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarPersonasMoraAportes
 */
public class ConsultarPersonasMoraAportes extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaRetiroNovedadAutomaticaDTO> result;
  
 	public ConsultarPersonasMoraAportes (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PersonaRetiroNovedadAutomaticaDTO>) response.readEntity(new GenericType<List<PersonaRetiroNovedadAutomaticaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PersonaRetiroNovedadAutomaticaDTO> getResult() {
		return result;
	}

 
  
}