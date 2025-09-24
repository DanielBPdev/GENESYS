package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/generarExcelListado/subsidiosAnular
 */
public class GenerarExcelListadoSubsidiosAnular extends ServiceClient {
 
  
  	private String tipo;
  	private Integer limit;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private byte[] result;
  
 	public GenerarExcelListadoSubsidiosAnular (String tipo,Integer limit){
 		super();
		this.tipo=tipo;
		this.limit=limit;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipo", tipo)
						.queryParam("limit", limit)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (byte[]) response.readEntity(byte[].class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public byte[] getResult() {
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