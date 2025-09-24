package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarConvenioTercero/{idConvenio}
 */
public class ConsultarConvenioTercero extends ServiceClient {
 
  	private Long idConvenio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConvenioTercerPagadorDTO result;
  
 	public ConsultarConvenioTercero (Long idConvenio){
 		super();
		this.idConvenio=idConvenio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idConvenio", idConvenio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConvenioTercerPagadorDTO) response.readEntity(ConvenioTercerPagadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConvenioTercerPagadorDTO getResult() {
		return result;
	}

 	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  
  
}