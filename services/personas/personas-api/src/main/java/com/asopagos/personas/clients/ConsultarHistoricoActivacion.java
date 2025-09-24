package com.asopagos.personas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.HistoricoActivacionAccesoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/consultarHistoricoActivacion
 */
public class ConsultarHistoricoActivacion extends ServiceClient {
 
  
  	private Long id;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoActivacionAccesoModeloDTO> result;
  
 	public ConsultarHistoricoActivacion (Long id){
 		super();
		this.id=id;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoActivacionAccesoModeloDTO>) response.readEntity(new GenericType<List<HistoricoActivacionAccesoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoActivacionAccesoModeloDTO> getResult() {
		return result;
	}

 
  	public void setId (Long id){
 		this.id=id;
 	}
 	
 	public Long getId (){
 		return id;
 	}
  
}