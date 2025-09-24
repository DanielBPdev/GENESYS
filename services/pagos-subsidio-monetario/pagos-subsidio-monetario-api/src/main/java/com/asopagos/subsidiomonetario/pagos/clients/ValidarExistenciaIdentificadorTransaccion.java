package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/validarExistenciaIdentificadorTransaccion
 */
public class ValidarExistenciaIdentificadorTransaccion extends ServiceClient {
 
  
  	private Long idConvenio;
  	private String idTransaccionTercerPagador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarExistenciaIdentificadorTransaccion (Long idConvenio,String idTransaccionTercerPagador){
 		super();
		this.idConvenio=idConvenio;
		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idConvenio", idConvenio)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  	public void setIdTransaccionTercerPagador (String idTransaccionTercerPagador){
 		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
 	}
 	
 	public String getIdTransaccionTercerPagador (){
 		return idTransaccionTercerPagador;
 	}
  
}