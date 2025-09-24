package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarRolAfiliadoCartera
 */
public class ConsultarRolAfiliadoCartera extends ServiceClient {
 
  
  	private Long idCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RolAfiliadoModeloDTO result;
  
 	public ConsultarRolAfiliadoCartera (Long idCartera){
 		super();
		this.idCartera=idCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCartera", idCartera)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RolAfiliadoModeloDTO) response.readEntity(RolAfiliadoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RolAfiliadoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdCartera (Long idCartera){
 		this.idCartera=idCartera;
 	}
 	
 	public Long getIdCartera (){
 		return idCartera;
 	}
  
}