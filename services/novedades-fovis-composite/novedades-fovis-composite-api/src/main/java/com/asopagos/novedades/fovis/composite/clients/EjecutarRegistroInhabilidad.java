package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.InhabilidadSubsidioFovisInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesConvertidorFovisComposite/ejecutarRegistroInhabilidad
 */
public class EjecutarRegistroInhabilidad extends ServiceClient { 
    	private InhabilidadSubsidioFovisInDTO inDTO;
  
  
 	public EjecutarRegistroInhabilidad (InhabilidadSubsidioFovisInDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInDTO (InhabilidadSubsidioFovisInDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public InhabilidadSubsidioFovisInDTO getInDTO (){
 		return inDTO;
 	}
  
}