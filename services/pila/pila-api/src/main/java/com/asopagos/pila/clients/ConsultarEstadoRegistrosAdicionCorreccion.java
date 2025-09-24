package com.asopagos.pila.clients;

import com.asopagos.enumeraciones.pila.FasePila2Enum;
import java.lang.Long;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarEstadoRegistrosAdicionCorreccion
 */
public class ConsultarEstadoRegistrosAdicionCorreccion extends ServiceClient {
 
  
  	private Long idRegistroGeneral;
  	private FasePila2Enum FasePila2Enum;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ConsultarEstadoRegistrosAdicionCorreccion (Long idRegistroGeneral,FasePila2Enum FasePila2Enum){
 		super();
		this.idRegistroGeneral=idRegistroGeneral;
		this.FasePila2Enum=FasePila2Enum;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegistroGeneral", idRegistroGeneral)
						.queryParam("FasePila2Enum", FasePila2Enum)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  	public void setFasePila2Enum (FasePila2Enum FasePila2Enum){
 		this.FasePila2Enum=FasePila2Enum;
 	}
 	
 	public FasePila2Enum getFasePila2Enum (){
 		return FasePila2Enum;
 	}
  
}