package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizarMarcaProcesoTemporales
 */
public class ActualizarMarcaProcesoTemporales extends ServiceClient { 
   	private Boolean enProceso;
  	private Boolean esAporte;
   	private List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas;
  
  
 	public ActualizarMarcaProcesoTemporales (Boolean enProceso,Boolean esAporte,List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas){
 		super();
		this.enProceso=enProceso;
		this.esAporte=esAporte;
		this.infoPlanillas=infoPlanillas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("enProceso", enProceso)
			.queryParam("esAporte", esAporte)
			.request(MediaType.APPLICATION_JSON)
			.post(infoPlanillas == null ? null : Entity.json(infoPlanillas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEnProceso (Boolean enProceso){
 		this.enProceso=enProceso;
 	}
 	
 	public Boolean getEnProceso (){
 		return enProceso;
 	}
  	public void setEsAporte (Boolean esAporte){
 		this.esAporte=esAporte;
 	}
 	
 	public Boolean getEsAporte (){
 		return esAporte;
 	}
  
  	public void setInfoPlanillas (List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas){
 		this.infoPlanillas=infoPlanillas;
 	}
 	
 	public List<InformacionPlanillasRegistrarProcesarDTO> getInfoPlanillas (){
 		return infoPlanillas;
 	}
  
}