package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.GestionCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarGestionFiscalizacion360
 */
public class ConsultarGestionFiscalizacion360 extends ServiceClient { 
    	private GestionCarteraDTO gestionCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private GestionCarteraDTO result;
  
 	public ConsultarGestionFiscalizacion360 (GestionCarteraDTO gestionCarteraDTO){
 		super();
		this.gestionCarteraDTO=gestionCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(gestionCarteraDTO == null ? null : Entity.json(gestionCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (GestionCarteraDTO) response.readEntity(GestionCarteraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public GestionCarteraDTO getResult() {
		return result;
	}

 
  
  	public void setGestionCarteraDTO (GestionCarteraDTO gestionCarteraDTO){
 		this.gestionCarteraDTO=gestionCarteraDTO;
 	}
 	
 	public GestionCarteraDTO getGestionCarteraDTO (){
 		return gestionCarteraDTO;
 	}
  
}