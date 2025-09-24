package com.asopagos.listas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ElementoListaDTO;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/listasValores
 */
public class ConsultarListasValores extends ServiceClient {
 
  
  	private List<Integer> idListaValores;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ElementoListaDTO> result;
  
 	public ConsultarListasValores (List<Integer> idListaValores){
 		super();
		this.idListaValores=idListaValores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idListaValores", idListaValores.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ElementoListaDTO>) response.readEntity(new GenericType<List<ElementoListaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ElementoListaDTO> getResult() {
		return result;
	}

 
  	public void setIdListaValores (List<Integer> idListaValores){
 		this.idListaValores=idListaValores;
 	}
 	
 	public List<Integer> getIdListaValores (){
 		return idListaValores;
 	}
  
}