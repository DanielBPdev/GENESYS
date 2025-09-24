package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.Response;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/exportarListado/subsidiosAnular
 */
public class ExportarListadoSubsidiosAnular extends ServiceClient { 
   	private String tipo;
  	private Integer limit;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ExportarListadoSubsidiosAnular (String tipo,Integer limit){
 		super();
		this.tipo=tipo;
		this.limit=limit;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipo", tipo)
			.queryParam("limit", limit)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Response getResult() {
		return result;
	}

 
  	public void setTipo (String tipo){
 		this.tipo=tipo;
 	}
 	
 	public String getTipo (){
 		return tipo;
 	}
  	public void setLimit (Integer limit){
 		this.limit=limit;
 	}
 	
 	public Integer getLimit (){
 		return limit;
 	}
  
  
}