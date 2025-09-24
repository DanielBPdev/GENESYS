package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idRolAfiliado}/consultarRolAfiliado
 */
public class ConsultarRolAfiliado extends ServiceClient {
 
  	private Long idRolAfiliado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RolAfiliadoModeloDTO result;
  
 	public ConsultarRolAfiliado (Long idRolAfiliado){
 		super();
		this.idRolAfiliado=idRolAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idRolAfiliado", idRolAfiliado)
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

 	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  
  
}