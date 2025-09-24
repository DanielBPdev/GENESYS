package com.asopagos.personas.clients;

import java.lang.Long;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/ubicacion/{idUbicacion}
 */
public class ConsultarUbicacion extends ServiceClient {
 
  	private Long idUbicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UbicacionDTO result;
  
 	public ConsultarUbicacion (Long idUbicacion){
 		super();
		this.idUbicacion=idUbicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idUbicacion", idUbicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UbicacionDTO) response.readEntity(UbicacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UbicacionDTO getResult() {
		return result;
	}

 	public void setIdUbicacion (Long idUbicacion){
 		this.idUbicacion=idUbicacion;
 	}
 	
 	public Long getIdUbicacion (){
 		return idUbicacion;
 	}
  
  
}