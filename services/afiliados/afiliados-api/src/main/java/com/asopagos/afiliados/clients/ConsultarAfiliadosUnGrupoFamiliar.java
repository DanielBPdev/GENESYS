package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.AfiliadoSolicitudDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarAfiliadosUnGrupoFamiliar
 */
public class ConsultarAfiliadosUnGrupoFamiliar extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AfiliadoSolicitudDTO> result;
  
 	public ConsultarAfiliadosUnGrupoFamiliar (){
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
		this.result = (List<AfiliadoSolicitudDTO>) response.readEntity(new GenericType<List<AfiliadoSolicitudDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AfiliadoSolicitudDTO> getResult() {
		return result;
	}

 
  
}