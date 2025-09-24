package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarCondicionesVisita
 */
public class ConsultarCondicionesVisita extends ServiceClient {
 
  
  	private Long idVisita;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CondicionVisitaModeloDTO> result;
  
 	public ConsultarCondicionesVisita (Long idVisita){
 		super();
		this.idVisita=idVisita;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idVisita", idVisita)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CondicionVisitaModeloDTO>) response.readEntity(new GenericType<List<CondicionVisitaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CondicionVisitaModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdVisita (Long idVisita){
 		this.idVisita=idVisita;
 	}
 	
 	public Long getIdVisita (){
 		return idVisita;
 	}
  
}