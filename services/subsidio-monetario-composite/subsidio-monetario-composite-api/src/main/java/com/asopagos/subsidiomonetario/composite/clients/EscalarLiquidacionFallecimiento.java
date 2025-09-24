package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/liquidacionFallecimiento/escalar/{numeroRadicacion}/{idTarea}
 */
public class EscalarLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
  	private String idTarea;
   	private EstadoAporteSubsidioEnum estadoAporte;
  	private Boolean consideracionAportes;
  	private String usernameSupervisor;
  	private ModoDesembolsoEnum tipoDesembolso;
   
  
 	public EscalarLiquidacionFallecimiento (String numeroRadicacion,String idTarea,EstadoAporteSubsidioEnum estadoAporte,Boolean consideracionAportes,String usernameSupervisor,ModoDesembolsoEnum tipoDesembolso){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
		this.estadoAporte=estadoAporte;
		this.consideracionAportes=consideracionAportes;
		this.usernameSupervisor=usernameSupervisor;
		this.tipoDesembolso=tipoDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("estadoAporte", estadoAporte)
			.queryParam("consideracionAportes", consideracionAportes)
			.queryParam("usernameSupervisor", usernameSupervisor)
			.queryParam("tipoDesembolso", tipoDesembolso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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
  	public void setIdTarea (String idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public String getIdTarea (){
 		return idTarea;
 	}
  
  	public void setEstadoAporte (EstadoAporteSubsidioEnum estadoAporte){
 		this.estadoAporte=estadoAporte;
 	}
 	
 	public EstadoAporteSubsidioEnum getEstadoAporte (){
 		return estadoAporte;
 	}
  	public void setConsideracionAportes (Boolean consideracionAportes){
 		this.consideracionAportes=consideracionAportes;
 	}
 	
 	public Boolean getConsideracionAportes (){
 		return consideracionAportes;
 	}
  	public void setUsernameSupervisor (String usernameSupervisor){
 		this.usernameSupervisor=usernameSupervisor;
 	}
 	
 	public String getUsernameSupervisor (){
 		return usernameSupervisor;
 	}
  	public void setTipoDesembolso (ModoDesembolsoEnum tipoDesembolso){
 		this.tipoDesembolso=tipoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getTipoDesembolso (){
 		return tipoDesembolso;
 	}
  
  
}