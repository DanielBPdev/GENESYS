package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/simularAporteCorreccion
 */
public class SimularAporteCorreccion extends ServiceClient { 
  	private Long idSolicitud;
    	private CorreccionAportanteDTO correccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CorreccionAportanteDTO result;
  
 	public SimularAporteCorreccion (Long idSolicitud,CorreccionAportanteDTO correccion){
 		super();
		this.idSolicitud=idSolicitud;
		this.correccion=correccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(correccion == null ? null : Entity.json(correccion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CorreccionAportanteDTO) response.readEntity(CorreccionAportanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CorreccionAportanteDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
  	public void setCorreccion (CorreccionAportanteDTO correccion){
 		this.correccion=correccion;
 	}
 	
 	public CorreccionAportanteDTO getCorreccion (){
 		return correccion;
 	}
  
}