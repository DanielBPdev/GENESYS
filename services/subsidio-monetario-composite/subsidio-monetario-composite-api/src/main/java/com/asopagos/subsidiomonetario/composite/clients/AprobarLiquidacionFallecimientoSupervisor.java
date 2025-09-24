package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/liquidacionFallecimiento/aprobar/supervisor/{numeroRadicacion}/{idTarea}
 */
public class AprobarLiquidacionFallecimientoSupervisor extends ServiceClient { 
  	private String numeroRadicacion;
  	private String idTarea;
   	private Boolean consideracionAportes;
  	private ModoDesembolsoEnum tipoDesembolso;
   
  
 	public AprobarLiquidacionFallecimientoSupervisor (String numeroRadicacion,String idTarea,Boolean consideracionAportes,ModoDesembolsoEnum tipoDesembolso){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
		this.consideracionAportes=consideracionAportes;
		this.tipoDesembolso=tipoDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("consideracionAportes", consideracionAportes)
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
  
  	public void setConsideracionAportes (Boolean consideracionAportes){
 		this.consideracionAportes=consideracionAportes;
 	}
 	
 	public Boolean getConsideracionAportes (){
 		return consideracionAportes;
 	}
  	public void setTipoDesembolso (ModoDesembolsoEnum tipoDesembolso){
 		this.tipoDesembolso=tipoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getTipoDesembolso (){
 		return tipoDesembolso;
 	}
  
  
}