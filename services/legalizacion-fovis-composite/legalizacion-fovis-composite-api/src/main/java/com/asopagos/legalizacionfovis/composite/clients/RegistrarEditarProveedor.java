package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.modelo.ProveedorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/registrarEditarProveedor
 */
public class RegistrarEditarProveedor extends ServiceClient { 
    	private ProveedorModeloDTO proveedorModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ProveedorModeloDTO result;
  
 	public RegistrarEditarProveedor (ProveedorModeloDTO proveedorModeloDTO){
 		super();
		this.proveedorModeloDTO=proveedorModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(proveedorModeloDTO == null ? null : Entity.json(proveedorModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ProveedorModeloDTO) response.readEntity(ProveedorModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ProveedorModeloDTO getResult() {
		return result;
	}

 
  
  	public void setProveedorModeloDTO (ProveedorModeloDTO proveedorModeloDTO){
 		this.proveedorModeloDTO=proveedorModeloDTO;
 	}
 	
 	public ProveedorModeloDTO getProveedorModeloDTO (){
 		return proveedorModeloDTO;
 	}
  
}