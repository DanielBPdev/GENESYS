package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.GestionCicloManualDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarAgendasActividadesVisibles
 */
public class ConsultarAgendasActividadesVisibles extends ServiceClient {
 
  
  	private Long numeroOperacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private GestionCicloManualDTO result;
  
 	public ConsultarAgendasActividadesVisibles (Long numeroOperacion){
 		super();
		this.numeroOperacion=numeroOperacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (GestionCicloManualDTO) response.readEntity(GestionCicloManualDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public GestionCicloManualDTO getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  
}