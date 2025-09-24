package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarSPLiquidacionFallecimiento
 */
public class EjecutarSPLiquidacionFallecimiento extends ServiceClient { 
   	private Long periodo;
  	private String numeroRadicado;
  	private Boolean beneficiarioFallecido;
   
  
 	public EjecutarSPLiquidacionFallecimiento (Long periodo,String numeroRadicado,Boolean beneficiarioFallecido){
 		super();
		this.periodo=periodo;
		this.numeroRadicado=numeroRadicado;
		this.beneficiarioFallecido=beneficiarioFallecido;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("periodo", periodo)
			.queryParam("numeroRadicado", numeroRadicado)
			.queryParam("beneficiarioFallecido", beneficiarioFallecido)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  	public void setBeneficiarioFallecido (Boolean beneficiarioFallecido){
 		this.beneficiarioFallecido=beneficiarioFallecido;
 	}
 	
 	public Boolean getBeneficiarioFallecido (){
 		return beneficiarioFallecido;
 	}
  
  
}