package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.afiliados.dto.InfoRelacionLaboral360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarInformacionRelacionLaboral
 */
public class ConsultarInformacionRelacionLaboral extends ServiceClient {
 
  
  	private Long idRolAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoRelacionLaboral360DTO result;
  
 	public ConsultarInformacionRelacionLaboral (Long idRolAfiliado){
 		super();
		this.idRolAfiliado=idRolAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRolAfiliado", idRolAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoRelacionLaboral360DTO) response.readEntity(InfoRelacionLaboral360DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoRelacionLaboral360DTO getResult() {
		return result;
	}

 
  	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  
}