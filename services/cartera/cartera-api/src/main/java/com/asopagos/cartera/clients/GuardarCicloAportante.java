package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CicloAportanteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCicloAportante
 */
public class GuardarCicloAportante extends ServiceClient { 
    	private CicloAportanteModeloDTO ciclo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloAportanteModeloDTO result;
  
 	public GuardarCicloAportante (CicloAportanteModeloDTO ciclo){
 		super();
		this.ciclo=ciclo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ciclo == null ? null : Entity.json(ciclo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CicloAportanteModeloDTO) response.readEntity(CicloAportanteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CicloAportanteModeloDTO getResult() {
		return result;
	}

 
  
  	public void setCiclo (CicloAportanteModeloDTO ciclo){
 		this.ciclo=ciclo;
 	}
 	
 	public CicloAportanteModeloDTO getCiclo (){
 		return ciclo;
 	}
  
}