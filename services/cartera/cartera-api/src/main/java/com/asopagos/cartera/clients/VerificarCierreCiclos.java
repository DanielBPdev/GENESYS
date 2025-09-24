package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoCicloEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/verificarCierreCiclos
 */
public class VerificarCierreCiclos extends ServiceClient { 
   	private Long idCicloAportante;
  	private TipoCicloEnum tipoCiclo;
   
  
 	public VerificarCierreCiclos (Long idCicloAportante,TipoCicloEnum tipoCiclo){
 		super();
		this.idCicloAportante=idCicloAportante;
		this.tipoCiclo=tipoCiclo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCicloAportante", idCicloAportante)
			.queryParam("tipoCiclo", tipoCiclo)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdCicloAportante (Long idCicloAportante){
 		this.idCicloAportante=idCicloAportante;
 	}
 	
 	public Long getIdCicloAportante (){
 		return idCicloAportante;
 	}
  	public void setTipoCiclo (TipoCicloEnum tipoCiclo){
 		this.tipoCiclo=tipoCiclo;
 	}
 	
 	public TipoCicloEnum getTipoCiclo (){
 		return tipoCiclo;
 	}
  
  
}