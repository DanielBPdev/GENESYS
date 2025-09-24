package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.cartera.AportanteCarteraDTO;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarCarteraAportante
 */
public class ConsultarCarteraAportante extends ServiceClient { 
    	private AportanteCarteraDTO aportanteCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CarteraModeloDTO> result;
  
 	public ConsultarCarteraAportante (AportanteCarteraDTO aportanteCarteraDTO){
 		super();
		this.aportanteCarteraDTO=aportanteCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteCarteraDTO == null ? null : Entity.json(aportanteCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CarteraModeloDTO>) response.readEntity(new GenericType<List<CarteraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CarteraModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setAportanteCarteraDTO (AportanteCarteraDTO aportanteCarteraDTO){
 		this.aportanteCarteraDTO=aportanteCarteraDTO;
 	}
 	
 	public AportanteCarteraDTO getAportanteCarteraDTO (){
 		return aportanteCarteraDTO;
 	}
  
}