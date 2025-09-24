package com.asopagos.novedades.fovis.clients;

import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarInhabilidadSubsidioFovis
 */
public class CrearActualizarInhabilidadSubsidioFovis extends ServiceClient { 
    	private InhabilidadSubsidioFovisModeloDTO inhabilidadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InhabilidadSubsidioFovisModeloDTO result;
  
 	public CrearActualizarInhabilidadSubsidioFovis (InhabilidadSubsidioFovisModeloDTO inhabilidadDTO){
 		super();
		this.inhabilidadDTO=inhabilidadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inhabilidadDTO == null ? null : Entity.json(inhabilidadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InhabilidadSubsidioFovisModeloDTO) response.readEntity(InhabilidadSubsidioFovisModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InhabilidadSubsidioFovisModeloDTO getResult() {
		return result;
	}

 
  
  	public void setInhabilidadDTO (InhabilidadSubsidioFovisModeloDTO inhabilidadDTO){
 		this.inhabilidadDTO=inhabilidadDTO;
 	}
 	
 	public InhabilidadSubsidioFovisModeloDTO getInhabilidadDTO (){
 		return inhabilidadDTO;
 	}
  
}