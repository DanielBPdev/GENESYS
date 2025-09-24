package com.asopagos.entidaddescuento.clients;

import java.lang.String;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/consultarEntidadDescuento/{codigoEntidadDescuento}
 */
public class ConsultarEntidadDescuento extends ServiceClient {
 
  	private String codigoEntidadDescuento;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EntidadDescuentoModeloDTO result;
  
 	public ConsultarEntidadDescuento (String codigoEntidadDescuento){
 		super();
		this.codigoEntidadDescuento=codigoEntidadDescuento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("codigoEntidadDescuento", codigoEntidadDescuento)
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

 	public void setCodigoEntidadDescuento (String codigoEntidadDescuento){
 		this.codigoEntidadDescuento=codigoEntidadDescuento;
 	}
 	
 	public String getCodigoEntidadDescuento (){
 		return codigoEntidadDescuento;
 	}
  
  
}