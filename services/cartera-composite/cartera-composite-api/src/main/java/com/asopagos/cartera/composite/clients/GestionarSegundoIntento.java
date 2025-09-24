package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
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
 * /rest/carteraComposite/{numeroRadicacion}/gestionarSegundoIntento
 */
public class GestionarSegundoIntento extends ServiceClient { 
  	private String numeroRadicacion;
   	private TipoAccionCobroEnum accionCobro;
   	private AportanteRemisionComunicadoDTO aportanteRemisionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoSolicitudGestionCobroEnum result;
  
 	public GestionarSegundoIntento (String numeroRadicacion,TipoAccionCobroEnum accionCobro,AportanteRemisionComunicadoDTO aportanteRemisionDTO){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.accionCobro=accionCobro;
		this.aportanteRemisionDTO=aportanteRemisionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("accionCobro", accionCobro)
			.request(MediaType.APPLICATION_JSON)
			.post(aportanteRemisionDTO == null ? null : Entity.json(aportanteRemisionDTO));
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
  
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
  	public void setAportanteRemisionDTO (AportanteRemisionComunicadoDTO aportanteRemisionDTO){
 		this.aportanteRemisionDTO=aportanteRemisionDTO;
 	}
 	
 	public AportanteRemisionComunicadoDTO getAportanteRemisionDTO (){
 		return aportanteRemisionDTO;
 	}
  
}