package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.ProveedorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/crearActualizarProveedor
 */
public class CrearActualizarProveedor extends ServiceClient { 
    	private ProveedorModeloDTO proveedorDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ProveedorModeloDTO result;
  
 	public CrearActualizarProveedor (ProveedorModeloDTO proveedorDTO){
 		super();
		this.proveedorDTO=proveedorDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(proveedorDTO == null ? null : Entity.json(proveedorDTO));
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

 
  
  	public void setProveedorDTO (ProveedorModeloDTO proveedorDTO){
 		this.proveedorDTO=proveedorDTO;
 	}
 	
 	public ProveedorModeloDTO getProveedorDTO (){
 		return proveedorDTO;
 	}
  
}