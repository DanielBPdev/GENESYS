package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/registrarSolicitudAnibol
 */
public class RegistrarSolicitudAnibol extends ServiceClient { 
    	private RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarSolicitudAnibol (RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO){
 		super();
		this.reAnibolModeloDTO=reAnibolModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(reAnibolModeloDTO == null ? null : Entity.json(reAnibolModeloDTO));
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

 
  
  	public void setReAnibolModeloDTO (RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO){
 		this.reAnibolModeloDTO=reAnibolModeloDTO;
 	}
 	
 	public RegistroSolicitudAnibolModeloDTO getReAnibolModeloDTO (){
 		return reAnibolModeloDTO;
 	}
  
}