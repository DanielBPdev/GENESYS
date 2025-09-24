package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/consultarDatosActualizacionPlanilla
 */
public class ConsultarDatosActualizacionPlanilla extends ServiceClient { 
   	private Boolean esReproceso;
  	private Boolean esSimulado;
   	private List<Long> idsRegistroGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ActualizacionEstadosPlanillaDTO> result;
  
 	public ConsultarDatosActualizacionPlanilla (Boolean esReproceso,Boolean esSimulado,List<Long> idsRegistroGeneral){
 		super();
		this.esReproceso=esReproceso;
		this.esSimulado=esSimulado;
		this.idsRegistroGeneral=idsRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("esReproceso", esReproceso)
			.queryParam("esSimulado", esSimulado)
			.request(MediaType.APPLICATION_JSON)
			.post(idsRegistroGeneral == null ? null : Entity.json(idsRegistroGeneral));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ActualizacionEstadosPlanillaDTO>) response.readEntity(new GenericType<List<ActualizacionEstadosPlanillaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ActualizacionEstadosPlanillaDTO> getResult() {
		return result;
	}

 
  	public void setEsReproceso (Boolean esReproceso){
 		this.esReproceso=esReproceso;
 	}
 	
 	public Boolean getEsReproceso (){
 		return esReproceso;
 	}
  	public void setEsSimulado (Boolean esSimulado){
 		this.esSimulado=esSimulado;
 	}
 	
 	public Boolean getEsSimulado (){
 		return esSimulado;
 	}
  
  	public void setIdsRegistroGeneral (List<Long> idsRegistroGeneral){
 		this.idsRegistroGeneral=idsRegistroGeneral;
 	}
 	
 	public List<Long> getIdsRegistroGeneral (){
 		return idsRegistroGeneral;
 	}
  
}