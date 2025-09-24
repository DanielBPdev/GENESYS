package com.asopagos.subsidiomonetario.clients;

import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consolidarSubsidiosFallecimiento
 */
public class ConsolidarSubsidiosFallecimiento extends ServiceClient {
 
  
  	private String numeroRadicado;
  	private ModoDesembolsoEnum tipoDesembolso;
  
  
 	public ConsolidarSubsidiosFallecimiento (String numeroRadicado,ModoDesembolsoEnum tipoDesembolso){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.tipoDesembolso=tipoDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicado", numeroRadicado)
						.queryParam("tipoDesembolso", tipoDesembolso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  	public void setTipoDesembolso (ModoDesembolsoEnum tipoDesembolso){
 		this.tipoDesembolso=tipoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getTipoDesembolso (){
 		return tipoDesembolso;
 	}
  
}