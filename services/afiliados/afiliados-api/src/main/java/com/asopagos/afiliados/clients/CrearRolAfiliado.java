package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class CrearRolAfiliado extends ServiceClient {

    private RolAfiliadoModeloDTO rolAfiliadoModeloDTO;

	/** Atributo que almacena los datos resultado del llamado al servicio */
	private Long result;
  
  
 	public CrearRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO){
 		super();
		this.rolAfiliadoModeloDTO=rolAfiliadoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(rolAfiliadoModeloDTO == null ? null : Entity.json(rolAfiliadoModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}

	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}
 
  
  	public void setRolAfiliadoModeloDTO (RolAfiliadoModeloDTO rolAfiliadoModeloDTO){
 		this.rolAfiliadoModeloDTO=rolAfiliadoModeloDTO;
 	}
 	
 	public RolAfiliadoModeloDTO getRolAfiliadoModeloDTO (){
 		return rolAfiliadoModeloDTO;
 	}
    
}
