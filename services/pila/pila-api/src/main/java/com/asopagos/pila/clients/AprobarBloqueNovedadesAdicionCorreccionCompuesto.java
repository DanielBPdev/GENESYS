package com.asopagos.pila.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/aprobarBloqueNovedadesAdicionCorreccionCompuesto
 */
public class AprobarBloqueNovedadesAdicionCorreccionCompuesto extends ServiceClient { 
   	private Long idRegistroGeneralAdicionCorreccion;
   	private List<Long> idsRegDetCorA;
  
  
 	public AprobarBloqueNovedadesAdicionCorreccionCompuesto (Long idRegistroGeneralAdicionCorreccion,List<Long> idsRegDetCorA){
 		super();
		this.idRegistroGeneralAdicionCorreccion=idRegistroGeneralAdicionCorreccion;
		this.idsRegDetCorA=idsRegDetCorA;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idRegistroGeneralAdicionCorreccion", idRegistroGeneralAdicionCorreccion)
			.request(MediaType.APPLICATION_JSON)
			.post(idsRegDetCorA == null ? null : Entity.json(idsRegDetCorA));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdRegistroGeneralAdicionCorreccion (Long idRegistroGeneralAdicionCorreccion){
 		this.idRegistroGeneralAdicionCorreccion=idRegistroGeneralAdicionCorreccion;
 	}
 	
 	public Long getIdRegistroGeneralAdicionCorreccion (){
 		return idRegistroGeneralAdicionCorreccion;
 	}
  
  	public void setIdsRegDetCorA (List<Long> idsRegDetCorA){
 		this.idsRegDetCorA=idsRegDetCorA;
 	}
 	
 	public List<Long> getIdsRegDetCorA (){
 		return idsRegDetCorA;
 	}
  
}