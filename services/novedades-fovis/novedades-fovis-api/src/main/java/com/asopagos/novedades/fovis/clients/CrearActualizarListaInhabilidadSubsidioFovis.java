package com.asopagos.novedades.fovis.clients;

import java.util.List;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarListaInhabilidadSubsidioFovis
 */
public class CrearActualizarListaInhabilidadSubsidioFovis extends ServiceClient { 
    	private List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades;
  
  
 	public CrearActualizarListaInhabilidadSubsidioFovis (List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades){
 		super();
		this.listaInhabilidades=listaInhabilidades;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaInhabilidades == null ? null : Entity.json(listaInhabilidades));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaInhabilidades (List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades){
 		this.listaInhabilidades=listaInhabilidades;
 	}
 	
 	public List<InhabilidadSubsidioFovisModeloDTO> getListaInhabilidades (){
 		return listaInhabilidades;
 	}
  
}