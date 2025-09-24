package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.Response;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/exportarCuotasDispersadasPorTerceroPagador
 */
public class ExportarCuotasDispersadasPorTerceroPagador extends ServiceClient { 
   	private Long idConvenio;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ExportarCuotasDispersadasPorTerceroPagador (Long idConvenio){
 		super();
		this.idConvenio=idConvenio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idConvenio", idConvenio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Response getResult() {
		return result;
	}

 
  	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  
  
}