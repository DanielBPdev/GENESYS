package com.asopagos.cartera.composite.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/consultarExclusionCarteraActiva
 */
public class ConsultarExclusionCarteraActiva extends ServiceClient { 
    	private ExclusionCarteraDTO exclusionCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ExclusionCarteraDTO> result;
  
 	public ConsultarExclusionCarteraActiva (ExclusionCarteraDTO exclusionCarteraDTO){
 		super();
		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(exclusionCarteraDTO == null ? null : Entity.json(exclusionCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ExclusionCarteraDTO>) response.readEntity(new GenericType<List<ExclusionCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ExclusionCarteraDTO> getResult() {
		return result;
	}

 
  
  	public void setExclusionCarteraDTO (ExclusionCarteraDTO exclusionCarteraDTO){
 		this.exclusionCarteraDTO=exclusionCarteraDTO;
 	}
 	
 	public ExclusionCarteraDTO getExclusionCarteraDTO (){
 		return exclusionCarteraDTO;
 	}
  
}