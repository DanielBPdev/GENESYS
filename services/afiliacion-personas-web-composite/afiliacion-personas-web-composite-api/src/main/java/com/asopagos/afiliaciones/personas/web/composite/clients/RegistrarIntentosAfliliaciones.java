package com.asopagos.afiliaciones.personas.web.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliaciones.personas.web.composite.dto.IntentoAfiliacionesComunicadoDTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/intentosAfiliaciones
 */
public class RegistrarIntentosAfliliaciones extends ServiceClient { 
    	private IntentoAfiliacionesComunicadoDTO intentoAfiliacionesInDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<IntentoAfiliacionInDTO> result;
  
 	public RegistrarIntentosAfliliaciones (IntentoAfiliacionesComunicadoDTO intentoAfiliacionesInDTO){
 		super();
		this.intentoAfiliacionesInDTO=intentoAfiliacionesInDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoAfiliacionesInDTO == null ? null : Entity.json(intentoAfiliacionesInDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<IntentoAfiliacionInDTO>) response.readEntity(new GenericType<List<IntentoAfiliacionInDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<IntentoAfiliacionInDTO> getResult() {
		return result;
	}

 
  
  	public void setIntentoAfiliacionesInDTO (IntentoAfiliacionesComunicadoDTO intentoAfiliacionesInDTO){
 		this.intentoAfiliacionesInDTO=intentoAfiliacionesInDTO;
 	}
 	
 	public IntentoAfiliacionesComunicadoDTO getIntentoAfiliacionesInDTO (){
 		return intentoAfiliacionesInDTO;
 	}
  
}