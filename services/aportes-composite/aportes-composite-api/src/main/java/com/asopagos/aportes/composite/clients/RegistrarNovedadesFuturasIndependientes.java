package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/registrarNovedadesFuturasIndependientes
 */
public class RegistrarNovedadesFuturasIndependientes extends ServiceClient { 
    	private List<NovedadesProcesoAportesDTO> novedadesProcesoAportes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public RegistrarNovedadesFuturasIndependientes (List<NovedadesProcesoAportesDTO> novedadesProcesoAportes){
 		super();
		this.novedadesProcesoAportes=novedadesProcesoAportes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(novedadesProcesoAportes == null ? null : Entity.json(novedadesProcesoAportes));
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

 
  
  	public void setNovedadesProcesoAportes (List<NovedadesProcesoAportesDTO> novedadesProcesoAportes){
 		this.novedadesProcesoAportes=novedadesProcesoAportes;
 	}
 	
 	public List<NovedadesProcesoAportesDTO> getNovedadesProcesoAportes (){
 		return novedadesProcesoAportes;
 	}
  
}