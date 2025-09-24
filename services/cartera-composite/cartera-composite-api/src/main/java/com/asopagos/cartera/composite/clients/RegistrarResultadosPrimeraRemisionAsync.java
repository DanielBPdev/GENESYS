package com.asopagos.cartera.composite.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraAsincronoComposite/{numeroRadicacion}/{idTarea}/registrarResultadosPrimeraRemision
 */
public class RegistrarResultadosPrimeraRemisionAsync extends ServiceClient { 
  	private Long idTarea;
  	private String numeroRadicacion;
   	private TipoAccionCobroEnum accionCobro;
   	private List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO;
  
  
 	public RegistrarResultadosPrimeraRemisionAsync (Long idTarea,String numeroRadicacion,TipoAccionCobroEnum accionCobro,List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO){
 		super();
		this.idTarea=idTarea;
		this.numeroRadicacion=numeroRadicacion;
		this.accionCobro=accionCobro;
		this.aportanteRemisionDTO=aportanteRemisionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("accionCobro", accionCobro)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteRemisionDTO == null ? null : Entity.json(aportanteRemisionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
  	public void setAportanteRemisionDTO (List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO){
 		this.aportanteRemisionDTO=aportanteRemisionDTO;
 	}
 	
 	public List<AportanteRemisionComunicadoDTO> getAportanteRemisionDTO (){
 		return aportanteRemisionDTO;
 	}
  
}