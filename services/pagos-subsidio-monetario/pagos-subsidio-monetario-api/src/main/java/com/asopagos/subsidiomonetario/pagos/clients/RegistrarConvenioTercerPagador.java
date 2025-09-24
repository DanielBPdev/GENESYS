package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/registrarConvenioTercerPagador
 */
public class RegistrarConvenioTercerPagador extends ServiceClient { 
    	private ConvenioTercerPagadorDTO convenio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarConvenioTercerPagador (ConvenioTercerPagadorDTO convenio){
 		super();
		this.convenio=convenio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(convenio == null ? null : Entity.json(convenio));
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

 
  
  	public void setConvenio (ConvenioTercerPagadorDTO convenio){
 		this.convenio=convenio;
 	}
 	
 	public ConvenioTercerPagadorDTO getConvenio (){
 		return convenio;
 	}
  
}