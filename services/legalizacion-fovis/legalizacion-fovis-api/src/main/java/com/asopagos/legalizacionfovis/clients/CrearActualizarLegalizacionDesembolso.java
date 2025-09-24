package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.LegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearActualizarLegalizacionDesembolso
 */
public class CrearActualizarLegalizacionDesembolso extends ServiceClient { 
    	private LegalizacionDesembolsoModeloDTO legalizacionDesembolsoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LegalizacionDesembolsoModeloDTO result;
  
 	public CrearActualizarLegalizacionDesembolso (LegalizacionDesembolsoModeloDTO legalizacionDesembolsoDTO){
 		super();
		this.legalizacionDesembolsoDTO=legalizacionDesembolsoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(legalizacionDesembolsoDTO == null ? null : Entity.json(legalizacionDesembolsoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (LegalizacionDesembolsoModeloDTO) response.readEntity(LegalizacionDesembolsoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public LegalizacionDesembolsoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setLegalizacionDesembolsoDTO (LegalizacionDesembolsoModeloDTO legalizacionDesembolsoDTO){
 		this.legalizacionDesembolsoDTO=legalizacionDesembolsoDTO;
 	}
 	
 	public LegalizacionDesembolsoModeloDTO getLegalizacionDesembolsoDTO (){
 		return legalizacionDesembolsoDTO;
 	}
  
}