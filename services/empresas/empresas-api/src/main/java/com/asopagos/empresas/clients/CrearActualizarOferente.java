package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.OferenteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/crearActualizarOferente
 */
public class CrearActualizarOferente extends ServiceClient { 
    	private OferenteModeloDTO oferenteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private OferenteModeloDTO result;
  
 	public CrearActualizarOferente (OferenteModeloDTO oferenteDTO){
 		super();
		this.oferenteDTO=oferenteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(oferenteDTO == null ? null : Entity.json(oferenteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (OferenteModeloDTO) response.readEntity(OferenteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public OferenteModeloDTO getResult() {
		return result;
	}

 
  
  	public void setOferenteDTO (OferenteModeloDTO oferenteDTO){
 		this.oferenteDTO=oferenteDTO;
 	}
 	
 	public OferenteModeloDTO getOferenteDTO (){
 		return oferenteDTO;
 	}
  
}