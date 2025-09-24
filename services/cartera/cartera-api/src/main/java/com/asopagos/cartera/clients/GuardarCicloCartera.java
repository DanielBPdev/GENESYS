package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCicloCartera
 */
public class GuardarCicloCartera extends ServiceClient { 
    	private CicloCarteraModeloDTO ciclo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloCarteraModeloDTO result;
  
 	public GuardarCicloCartera (CicloCarteraModeloDTO ciclo){
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
		result = (CicloCarteraModeloDTO) response.readEntity(CicloCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CicloCarteraModeloDTO getResult() {
		return result;
	}

 
  
  	public void setCiclo (CicloCarteraModeloDTO ciclo){
 		this.ciclo=ciclo;
 	}
 	
 	public CicloCarteraModeloDTO getCiclo (){
 		return ciclo;
 	}
  
}