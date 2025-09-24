package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionFovisComposite/validarHogar
 */
public class ValidarHogar extends ServiceClient { 
   	private Boolean incluyeIngresos;
  	private Long idCicloAsignacion;
   	private List<SolicitudPostulacionModeloDTO> postulaciones;
  
  
 	public ValidarHogar (Boolean incluyeIngresos,Long idCicloAsignacion,List<SolicitudPostulacionModeloDTO> postulaciones){
 		super();
		this.incluyeIngresos=incluyeIngresos;
		this.idCicloAsignacion=idCicloAsignacion;
		this.postulaciones=postulaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("incluyeIngresos", incluyeIngresos)
			.queryParam("idCicloAsignacion", idCicloAsignacion)
			.request(MediaType.APPLICATION_JSON)
			.post(postulaciones == null ? null : Entity.json(postulaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIncluyeIngresos (Boolean incluyeIngresos){
 		this.incluyeIngresos=incluyeIngresos;
 	}
 	
 	public Boolean getIncluyeIngresos (){
 		return incluyeIngresos;
 	}
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
  	public void setPostulaciones (List<SolicitudPostulacionModeloDTO> postulaciones){
 		this.postulaciones=postulaciones;
 	}
 	
 	public List<SolicitudPostulacionModeloDTO> getPostulaciones (){
 		return postulaciones;
 	}
  
}