package com.asopagos.subsidiomonetario.clients;

import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/enviarResultadoLiquidacionAPagosFallecimiento
 */
public class EnviarResultadoLiquidacionAPagosFallecimiento extends ServiceClient {
 
  
  	private String numeroRadicado;
  	private String nombreUsuario;
  	private ModoDesembolsoEnum tipoDesembolso;
  
  
 	public EnviarResultadoLiquidacionAPagosFallecimiento (String numeroRadicado,String nombreUsuario,ModoDesembolsoEnum tipoDesembolso){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.nombreUsuario=nombreUsuario;
		this.tipoDesembolso=tipoDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicado", numeroRadicado)
						.queryParam("nombreUsuario", nombreUsuario)
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
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  	public void setTipoDesembolso (ModoDesembolsoEnum tipoDesembolso){
 		this.tipoDesembolso=tipoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getTipoDesembolso (){
 		return tipoDesembolso;
 	}
  
}