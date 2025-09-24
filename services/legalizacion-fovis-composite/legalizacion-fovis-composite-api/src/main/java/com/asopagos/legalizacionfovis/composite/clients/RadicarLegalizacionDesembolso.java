package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/radicarLegalizacionDesembolso
 */
public class RadicarLegalizacionDesembolso extends ServiceClient { 
   	private Boolean terminarTarea;
   	private SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLegalizacionDesembolsoDTO result;
  
 	public RadicarLegalizacionDesembolso (Boolean terminarTarea,SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO){
 		super();
		this.terminarTarea=terminarTarea;
		this.solicitudLegalizacionDesembolsoDTO=solicitudLegalizacionDesembolsoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("terminarTarea", terminarTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudLegalizacionDesembolsoDTO == null ? null : Entity.json(solicitudLegalizacionDesembolsoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudLegalizacionDesembolsoDTO) response.readEntity(SolicitudLegalizacionDesembolsoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudLegalizacionDesembolsoDTO getResult() {
		return result;
	}

 
  	public void setTerminarTarea (Boolean terminarTarea){
 		this.terminarTarea=terminarTarea;
 	}
 	
 	public Boolean getTerminarTarea (){
 		return terminarTarea;
 	}
  
  	public void setSolicitudLegalizacionDesembolsoDTO (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO){
 		this.solicitudLegalizacionDesembolsoDTO=solicitudLegalizacionDesembolsoDTO;
 	}
 	
 	public SolicitudLegalizacionDesembolsoDTO getSolicitudLegalizacionDesembolsoDTO (){
 		return solicitudLegalizacionDesembolsoDTO;
 	}
  
}