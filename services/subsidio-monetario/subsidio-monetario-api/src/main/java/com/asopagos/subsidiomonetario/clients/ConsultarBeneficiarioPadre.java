package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarBeneficiarioPadre
 */
public class ConsultarBeneficiarioPadre extends ServiceClient {
 
  
  	private String numeroRadicacion;
  	private Long idCondicionBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ConsultarBeneficiarioPadre (String numeroRadicacion,Long idCondicionBeneficiario){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idCondicionBeneficiario=idCondicionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("idCondicionBeneficiario", idCondicionBeneficiario)
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

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setIdCondicionBeneficiario (Long idCondicionBeneficiario){
 		this.idCondicionBeneficiario=idCondicionBeneficiario;
 	}
 	
 	public Long getIdCondicionBeneficiario (){
 		return idCondicionBeneficiario;
 	}
  
}