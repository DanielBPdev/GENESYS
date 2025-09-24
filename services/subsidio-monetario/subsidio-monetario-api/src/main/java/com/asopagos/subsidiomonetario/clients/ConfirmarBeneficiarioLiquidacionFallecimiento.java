package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/subsidioMonetario/liquidacionFallecimiento/resultados/confirmarBeneficiario/{numeroRadicacion}
 */
public class ConfirmarBeneficiarioLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long idCondicionBeneficiario;
   
  
 	public ConfirmarBeneficiarioLiquidacionFallecimiento (String numeroRadicacion,Long idCondicionBeneficiario){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idCondicionBeneficiario=idCondicionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("idCondicionBeneficiario", idCondicionBeneficiario)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
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