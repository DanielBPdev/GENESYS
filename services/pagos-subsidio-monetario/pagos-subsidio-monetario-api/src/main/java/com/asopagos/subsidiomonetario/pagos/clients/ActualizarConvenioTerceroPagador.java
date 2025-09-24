package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/actualizarConvenioTerceroPagador
 */
public class ActualizarConvenioTerceroPagador extends ServiceClient { 
    	private ConvenioTercerPagadorDTO convenioTercerPagadorDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ActualizarConvenioTerceroPagador (ConvenioTercerPagadorDTO convenioTercerPagadorDTO){
 		super();
		this.convenioTercerPagadorDTO=convenioTercerPagadorDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(convenioTercerPagadorDTO == null ? null : Entity.json(convenioTercerPagadorDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  
  	public void setConvenioTercerPagadorDTO (ConvenioTercerPagadorDTO convenioTercerPagadorDTO){
 		this.convenioTercerPagadorDTO=convenioTercerPagadorDTO;
 	}
 	
 	public ConvenioTercerPagadorDTO getConvenioTercerPagadorDTO (){
 		return convenioTercerPagadorDTO;
 	}
  
}