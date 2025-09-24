package com.asopagos.afiliados.clients;

import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/crearActualizarRolSustitucionPatronal
 */
public class CrearActualizarRolSustitucionPatronal extends ServiceClient { 
    	private List<RolAfiliadoModeloDTO> listRolDTO;
  
  
 	public CrearActualizarRolSustitucionPatronal (List<RolAfiliadoModeloDTO> listRolDTO){
 		super();
		this.listRolDTO=listRolDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listRolDTO == null ? null : Entity.json(listRolDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListRolDTO (List<RolAfiliadoModeloDTO> listRolDTO){
 		this.listRolDTO=listRolDTO;
 	}
 	
 	public List<RolAfiliadoModeloDTO> getListRolDTO (){
 		return listRolDTO;
 	}
  
}