package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarAfiliadoPorId
 */
public class ConsultarAfiliadoPorId extends ServiceClient {
 
  
  	private Long idAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AfiliadoModeloDTO result;
  
 	public ConsultarAfiliadoPorId (Long idAfiliado){
 		super();
		this.idAfiliado=idAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAfiliado", idAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (AfiliadoModeloDTO) response.readEntity(AfiliadoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public AfiliadoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
}