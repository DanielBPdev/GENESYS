package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarCondicionInvalidezVigentePersona
 */
public class ConsultarCondicionInvalidezVigentePersona extends ServiceClient {
 
  
  	private Long idPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CondicionInvalidezModeloDTO result;
  
 	public ConsultarCondicionInvalidezVigentePersona (Long idPersona){
 		super();
		this.idPersona=idPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CondicionInvalidezModeloDTO) response.readEntity(CondicionInvalidezModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CondicionInvalidezModeloDTO getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
}