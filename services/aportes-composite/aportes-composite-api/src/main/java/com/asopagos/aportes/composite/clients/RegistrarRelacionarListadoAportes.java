package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.AporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/registrarRelacionarListadoAportes
 */
public class RegistrarRelacionarListadoAportes extends ServiceClient { 
    	private List<AporteDTO> aportes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public RegistrarRelacionarListadoAportes (List<AporteDTO> aportes){
 		super();
		this.aportes=aportes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportes == null ? null : Entity.json(aportes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setAportes (List<AporteDTO> aportes){
 		this.aportes=aportes;
 	}
 	
 	public List<AporteDTO> getAportes (){
 		return aportes;
 	}
  
}