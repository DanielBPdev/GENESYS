package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.modelo.OferenteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/registrarEditarOferente
 */
public class RegistrarEditarOferente extends ServiceClient { 
    	private OferenteModeloDTO oferenteModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private OferenteModeloDTO result;
  
 	public RegistrarEditarOferente (OferenteModeloDTO oferenteModeloDTO){
 		super();
		this.oferenteModeloDTO=oferenteModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(oferenteModeloDTO == null ? null : Entity.json(oferenteModeloDTO));
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

 
  
  	public void setOferenteModeloDTO (OferenteModeloDTO oferenteModeloDTO){
 		this.oferenteModeloDTO=oferenteModeloDTO;
 	}
 	
 	public OferenteModeloDTO getOferenteModeloDTO (){
 		return oferenteModeloDTO;
 	}
  
}