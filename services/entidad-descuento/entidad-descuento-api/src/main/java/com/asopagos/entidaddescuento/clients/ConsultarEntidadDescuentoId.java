package com.asopagos.entidaddescuento.clients;

import java.lang.Long;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/consultarEntidadDescuentoId/{idEntidadDescuento}
 */
public class ConsultarEntidadDescuentoId extends ServiceClient {
 
  	private Long idEntidadDescuento;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EntidadDescuentoModeloDTO result;
  
 	public ConsultarEntidadDescuentoId (Long idEntidadDescuento){
 		super();
		this.idEntidadDescuento=idEntidadDescuento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEntidadDescuento", idEntidadDescuento)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EntidadDescuentoModeloDTO) response.readEntity(EntidadDescuentoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EntidadDescuentoModeloDTO getResult() {
		return result;
	}

 	public void setIdEntidadDescuento (Long idEntidadDescuento){
 		this.idEntidadDescuento=idEntidadDescuento;
 	}
 	
 	public Long getIdEntidadDescuento (){
 		return idEntidadDescuento;
 	}
  
  
}