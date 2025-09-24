package com.asopagos.afiliaciones.clients;

import java.lang.String;
import com.asopagos.afiliaciones.dto.InfoSalarioMinimoOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerSalarioMinimoLegal
 */
public class ObtenerSalarioMinimoLegal extends ServiceClient {
 
  
  	private String periodo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoSalarioMinimoOutDTO result;
  
 	public ObtenerSalarioMinimoLegal (String periodo){
 		super();
		this.periodo=periodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodo", periodo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoSalarioMinimoOutDTO) response.readEntity(InfoSalarioMinimoOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoSalarioMinimoOutDTO getResult() {
		return result;
	}

 
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  
}