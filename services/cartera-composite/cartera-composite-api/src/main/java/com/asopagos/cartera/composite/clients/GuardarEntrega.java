package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/{numeroRadicacion}/guardarEntrega
 */
public class GuardarEntrega extends ServiceClient { 
  	private String numeroRadicacion;
    	private AportanteRemisionComunicadoDTO aportanteRemision;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoSolicitudGestionCobroEnum result;
  
 	public GuardarEntrega (String numeroRadicacion,AportanteRemisionComunicadoDTO aportanteRemision){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.aportanteRemision=aportanteRemision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteRemision == null ? null : Entity.json(aportanteRemision));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (EstadoSolicitudGestionCobroEnum) response.readEntity(EstadoSolicitudGestionCobroEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public EstadoSolicitudGestionCobroEnum getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
  	public void setAportanteRemision (AportanteRemisionComunicadoDTO aportanteRemision){
 		this.aportanteRemision=aportanteRemision;
 	}
 	
 	public AportanteRemisionComunicadoDTO getAportanteRemision (){
 		return aportanteRemision;
 	}
  
}