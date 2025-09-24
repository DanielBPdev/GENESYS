package com.asopagos.tareashumanas.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/procesos/enviarSenal/{proceso}/{tipoSenal}
 */
public class EnviarSenal extends ServiceClient { 
  	private ProcesoEnum proceso;
  	private String tipoSenal;
   	private Long idInstanciaProceso;
  	private String event;
   
  
 	public EnviarSenal (ProcesoEnum proceso,String tipoSenal,Long idInstanciaProceso,String event){
 		super();
		this.proceso=proceso;
		this.tipoSenal=tipoSenal;
		this.idInstanciaProceso=idInstanciaProceso;
		this.event=event;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("proceso", proceso)
			.resolveTemplate("tipoSenal", tipoSenal)
			.queryParam("idInstanciaProceso", idInstanciaProceso)
			.queryParam("event", event)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setTipoSenal (String tipoSenal){
 		this.tipoSenal=tipoSenal;
 	}
 	
 	public String getTipoSenal (){
 		return tipoSenal;
 	}
  
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  	public void setEvent (String event){
 		this.event=event;
 	}
 	
 	public String getEvent (){
 		return event;
 	}
  
  
}