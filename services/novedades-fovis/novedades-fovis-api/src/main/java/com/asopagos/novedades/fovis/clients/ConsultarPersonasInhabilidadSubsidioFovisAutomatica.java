package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarPersonasInhabilidadSubsidioFovisAutomatica
 */
public class ConsultarPersonasInhabilidadSubsidioFovisAutomatica extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InhabilidadSubsidioFovisModeloDTO> result;
  
 	public ConsultarPersonasInhabilidadSubsidioFovisAutomatica (){
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
		this.result = (List<InhabilidadSubsidioFovisModeloDTO>) response.readEntity(new GenericType<List<InhabilidadSubsidioFovisModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InhabilidadSubsidioFovisModeloDTO> getResult() {
		return result;
	}

 
  
}