package com.asopagos.parametros.clients;

import com.asopagos.parametros.dto.TablaParametroDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/parametros/informacionTablasParametricas
 */
public class InformacionTablasParametricas extends ServiceClient {
 
  
  	private String nombreTabla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TablaParametroDTO result;
  
 	public InformacionTablasParametricas (String nombreTabla){
 		super();
		this.nombreTabla=nombreTabla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreTabla", nombreTabla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TablaParametroDTO) response.readEntity(TablaParametroDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TablaParametroDTO getResult() {
		return result;
	}

 
  	public void setNombreTabla (String nombreTabla){
 		this.nombreTabla=nombreTabla;
 	}
 	
 	public String getNombreTabla (){
 		return nombreTabla;
 	}
  
}