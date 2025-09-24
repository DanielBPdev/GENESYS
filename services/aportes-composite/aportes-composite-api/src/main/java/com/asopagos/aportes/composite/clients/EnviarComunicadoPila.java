package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/enviarComunicadoPila
 */
public class EnviarComunicadoPila extends ServiceClient { 
    	private DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public EnviarComunicadoPila (DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO){
 		super();
		this.datosComunicadoPlanillaDTO=datosComunicadoPlanillaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosComunicadoPlanillaDTO == null ? null : Entity.json(datosComunicadoPlanillaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setDatosComunicadoPlanillaDTO (DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO){
 		this.datosComunicadoPlanillaDTO=datosComunicadoPlanillaDTO;
 	}
 	
 	public DatosComunicadoPlanillaDTO getDatosComunicadoPlanillaDTO (){
 		return datosComunicadoPlanillaDTO;
 	}
  
}