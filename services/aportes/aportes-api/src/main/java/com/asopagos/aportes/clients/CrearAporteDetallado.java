package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearAporteDetallado
 */
public class CrearAporteDetallado extends ServiceClient { 
    	private AporteDetalladoModeloDTO aporteDetalladoModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearAporteDetallado (AporteDetalladoModeloDTO aporteDetalladoModeloDTO){
 		super();
		this.aporteDetalladoModeloDTO=aporteDetalladoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aporteDetalladoModeloDTO == null ? null : Entity.json(aporteDetalladoModeloDTO));
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

 
  
  	public void setAporteDetalladoModeloDTO (AporteDetalladoModeloDTO aporteDetalladoModeloDTO){
 		this.aporteDetalladoModeloDTO=aporteDetalladoModeloDTO;
 	}
 	
 	public AporteDetalladoModeloDTO getAporteDetalladoModeloDTO (){
 		return aporteDetalladoModeloDTO;
 	}
  
}