package com.asopagos.pila.clients;

import com.asopagos.enumeraciones.pila.FasePila2Enum;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/definirBloqueAProcesarPilaMundoDos
 */
public class DefinirBloqueAProcesarPilaMundoDos extends ServiceClient {
 
  
  	private Long idRegGeneralAdicionCorreccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private FasePila2Enum result;
  
 	public DefinirBloqueAProcesarPilaMundoDos (Long idRegGeneralAdicionCorreccion){
 		super();
		this.idRegGeneralAdicionCorreccion=idRegGeneralAdicionCorreccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegGeneralAdicionCorreccion", idRegGeneralAdicionCorreccion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (FasePila2Enum) response.readEntity(FasePila2Enum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public FasePila2Enum getResult() {
		return result;
	}

 
  	public void setIdRegGeneralAdicionCorreccion (Long idRegGeneralAdicionCorreccion){
 		this.idRegGeneralAdicionCorreccion=idRegGeneralAdicionCorreccion;
 	}
 	
 	public Long getIdRegGeneralAdicionCorreccion (){
 		return idRegGeneralAdicionCorreccion;
 	}
  
}