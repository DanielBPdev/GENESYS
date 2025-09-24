package com.asopagos.fovis.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/inactivarIntegrantesHogarNoRelacionados
 */
public class InactivarIntegrantesHogarNoRelacionados extends ServiceClient { 
   	private Long idPostulacion;
  	private Long idJefeHogar;
   	private List<Long> integrantesPermanecientes;
  
  
 	public InactivarIntegrantesHogarNoRelacionados (Long idPostulacion,Long idJefeHogar,List<Long> integrantesPermanecientes){
 		super();
		this.idPostulacion=idPostulacion;
		this.idJefeHogar=idJefeHogar;
		this.integrantesPermanecientes=integrantesPermanecientes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPostulacion", idPostulacion)
			.queryParam("idJefeHogar", idJefeHogar)
			.request(MediaType.APPLICATION_JSON)
			.post(integrantesPermanecientes == null ? null : Entity.json(integrantesPermanecientes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  	public void setIdJefeHogar (Long idJefeHogar){
 		this.idJefeHogar=idJefeHogar;
 	}
 	
 	public Long getIdJefeHogar (){
 		return idJefeHogar;
 	}
  
  	public void setIntegrantesPermanecientes (List<Long> integrantesPermanecientes){
 		this.integrantesPermanecientes=integrantesPermanecientes;
 	}
 	
 	public List<Long> getIntegrantesPermanecientes (){
 		return integrantesPermanecientes;
 	}
  
}