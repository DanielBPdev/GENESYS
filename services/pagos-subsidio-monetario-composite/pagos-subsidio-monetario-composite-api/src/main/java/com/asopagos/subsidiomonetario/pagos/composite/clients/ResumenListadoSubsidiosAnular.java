package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioAnularDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/resumenListado/subsidiosAnular
 */
public class ResumenListadoSubsidiosAnular extends ServiceClient {
 
  
  	private String tipo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SubsidioAnularDTO> result;
  
 	public ResumenListadoSubsidiosAnular (String tipo){
 		super();
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipo", tipo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SubsidioAnularDTO>) response.readEntity(new GenericType<List<SubsidioAnularDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SubsidioAnularDTO> getResult() {
		return result;
	}

 
  	public void setTipo (String tipo){
 		this.tipo=tipo;
 	}
 	
 	public String getTipo (){
 		return tipo;
 	}
  
}