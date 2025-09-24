package com.asopagos.cartera.composite.clients;

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
 * /rest/carteraComposite/obtenerCarteraAportante
 */
public class ObtenerCarteraAportante extends ServiceClient { 
    	private AportanteCarteraDTO aportanteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CarteraModeloDTO> result;
  
 	public ObtenerCarteraAportante (AportanteCarteraDTO aportanteDTO){
 		super();
		this.aportanteDTO=aportanteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteDTO == null ? null : Entity.json(aportanteDTO));
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

 
  
  	public void setAportanteDTO (AportanteCarteraDTO aportanteDTO){
 		this.aportanteDTO=aportanteDTO;
 	}
 	
 	public AportanteCarteraDTO getAportanteDTO (){
 		return aportanteDTO;
 	}
  
}