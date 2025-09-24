package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.cartera.dto.FiltrosCarteraDTO;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarCartera
 */
public class ConsultarCartera extends ServiceClient { 
    	private FiltrosCarteraDTO filtrosCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CarteraModeloDTO> result;
  
 	public ConsultarCartera (FiltrosCarteraDTO filtrosCartera){
 		super();
		this.filtrosCartera=filtrosCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtrosCartera == null ? null : Entity.json(filtrosCartera));
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

 
  
  	public void setFiltrosCartera (FiltrosCarteraDTO filtrosCartera){
 		this.filtrosCartera=filtrosCartera;
 	}
 	
 	public FiltrosCarteraDTO getFiltrosCartera (){
 		return filtrosCartera;
 	}
  
}