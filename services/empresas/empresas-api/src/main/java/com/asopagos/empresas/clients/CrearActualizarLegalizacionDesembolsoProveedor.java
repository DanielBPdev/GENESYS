package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/crearActualizarLegalizacionDesembolsoProveedor
 */
public class CrearActualizarLegalizacionDesembolsoProveedor extends ServiceClient { 
    	private LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LegalizacionDesembolosoProveedorModeloDTO result;
  
 	public CrearActualizarLegalizacionDesembolsoProveedor (LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorModeloDTO){
 		super();
		this.legalizacionDesembolosoProveedorModeloDTO=legalizacionDesembolosoProveedorModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(legalizacionDesembolosoProveedorModeloDTO == null ? null : Entity.json(legalizacionDesembolosoProveedorModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (LegalizacionDesembolosoProveedorModeloDTO) response.readEntity(LegalizacionDesembolosoProveedorModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public LegalizacionDesembolosoProveedorModeloDTO getResult() {
		return result;
	}

 
  
  	public void setLegalizacionDesembolosoProveedorModeloDTO (LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorModeloDTO){
 		this.legalizacionDesembolosoProveedorModeloDTO=legalizacionDesembolosoProveedorModeloDTO;
 	}
 	
 	public LegalizacionDesembolosoProveedorModeloDTO getLegalizacionDesembolosoProveedorModeloDTO (){
 		return legalizacionDesembolosoProveedorModeloDTO;
 	}
  
}