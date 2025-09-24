package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/detalleAportanteCriterios
 */
public class DetalleAportanteCriterios extends ServiceClient {
 
  
  	private Long registroControl;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroDetalladoModeloDTO> result;
  
 	public DetalleAportanteCriterios (Long registroControl){
 		super();
		this.registroControl=registroControl;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("registroControl", registroControl)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RegistroDetalladoModeloDTO>) response.readEntity(new GenericType<List<RegistroDetalladoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroDetalladoModeloDTO> getResult() {
		return result;
	}

 
  	public void setRegistroControl (Long registroControl){
 		this.registroControl=registroControl;
 	}
 	
 	public Long getRegistroControl (){
 		return registroControl;
 	}
  
}