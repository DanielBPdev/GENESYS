package com.asopagos.legalizacionfovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarIntentoLegalizacionDesembolsoFOVIS
 */
public class RegistrarIntentoLegalizacionDesembolsoFOVIS extends ServiceClient { 
    	private IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarIntentoLegalizacionDesembolsoFOVIS (IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO){
 		super();
		this.intentoLegalizacionDesembolsoModeloDTO=intentoLegalizacionDesembolsoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoLegalizacionDesembolsoModeloDTO == null ? null : Entity.json(intentoLegalizacionDesembolsoModeloDTO));
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

 
  
  	public void setIntentoLegalizacionDesembolsoModeloDTO (IntentoLegalizacionDesembolsoModeloDTO intentoLegalizacionDesembolsoModeloDTO){
 		this.intentoLegalizacionDesembolsoModeloDTO=intentoLegalizacionDesembolsoModeloDTO;
 	}
 	
 	public IntentoLegalizacionDesembolsoModeloDTO getIntentoLegalizacionDesembolsoModeloDTO (){
 		return intentoLegalizacionDesembolsoModeloDTO;
 	}
  
}