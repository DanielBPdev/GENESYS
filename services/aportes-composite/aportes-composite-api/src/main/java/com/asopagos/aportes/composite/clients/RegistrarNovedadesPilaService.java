package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.composite.dto.RegistrarNovedadesPilaServiceDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/registrarNovedadesPilaService
 */
public class RegistrarNovedadesPilaService extends ServiceClient { 
    	private RegistrarNovedadesPilaServiceDTO regNovDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public RegistrarNovedadesPilaService (RegistrarNovedadesPilaServiceDTO regNovDTO){
 		super();
		this.regNovDTO=regNovDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(regNovDTO == null ? null : Entity.json(regNovDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setRegNovDTO (RegistrarNovedadesPilaServiceDTO regNovDTO){
 		this.regNovDTO=regNovDTO;
 	}
 	
 	public RegistrarNovedadesPilaServiceDTO getRegNovDTO (){
 		return regNovDTO;
 	}
  
}