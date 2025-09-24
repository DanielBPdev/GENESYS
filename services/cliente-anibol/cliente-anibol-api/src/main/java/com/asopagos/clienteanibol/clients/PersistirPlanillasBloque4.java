package com.asopagos.clienteanibol.clients;

import java.util.List;
import com.asopagos.dto.pila.azure.ArchivoPilaAzureDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/azure/persistirPlanillasBloque4
 */
public class PersistirPlanillasBloque4 extends ServiceClient { 
    	private List<ArchivoPilaAzureDTO> ArchivosPilaAzureDTO;
  
  
 	public PersistirPlanillasBloque4 (List<ArchivoPilaAzureDTO> ArchivosPilaAzureDTO){
 		super();
		this.ArchivosPilaAzureDTO=ArchivosPilaAzureDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ArchivosPilaAzureDTO == null ? null : Entity.json(ArchivosPilaAzureDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setArchivosPilaAzureDTO (List<ArchivoPilaAzureDTO> ArchivosPilaAzureDTO){
 		this.ArchivosPilaAzureDTO=ArchivosPilaAzureDTO;
 	}
 	
 	public List<ArchivoPilaAzureDTO> getArchivosPilaAzureDTO (){
 		return ArchivosPilaAzureDTO;
 	}
  
}