package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarActualizacionBeneficiario
 */
public class EjecutarActualizacionBeneficiario extends ServiceClient { 
    	private BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO;
  
  
 	public EjecutarActualizacionBeneficiario (BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO){
 		super();
		this.beneficiarioGrupoAfiliadoDTO=beneficiarioGrupoAfiliadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiarioGrupoAfiliadoDTO == null ? null : Entity.json(beneficiarioGrupoAfiliadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setBeneficiarioGrupoAfiliadoDTO (BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO){
 		this.beneficiarioGrupoAfiliadoDTO=beneficiarioGrupoAfiliadoDTO;
 	}
 	
 	public BeneficiarioGrupoAfiliadoDTO getBeneficiarioGrupoAfiliadoDTO (){
 		return beneficiarioGrupoAfiliadoDTO;
 	}
  
}