package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.cartera.AportanteConvenioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarConveniosFiltro
 */
public class ConsultarConveniosFiltro extends ServiceClient { 
    	private AportanteConvenioDTO aportanteConvenioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AportanteConvenioDTO> result;
  
 	public ConsultarConveniosFiltro (AportanteConvenioDTO aportanteConvenioDTO){
 		super();
		this.aportanteConvenioDTO=aportanteConvenioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteConvenioDTO == null ? null : Entity.json(aportanteConvenioDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AportanteConvenioDTO>) response.readEntity(new GenericType<List<AportanteConvenioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AportanteConvenioDTO> getResult() {
		return result;
	}

 
  
  	public void setAportanteConvenioDTO (AportanteConvenioDTO aportanteConvenioDTO){
 		this.aportanteConvenioDTO=aportanteConvenioDTO;
 	}
 	
 	public AportanteConvenioDTO getAportanteConvenioDTO (){
 		return aportanteConvenioDTO;
 	}
  
}