package com.asopagos.novedades.composite.clients;

import java.util.List;
import com.asopagos.dto.PersonaDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/verificarPersonaNovedadRegistrarAnalisisFovis
 */
public class VerificarPersonaNovedadRegistrarAnalisisFovis extends ServiceClient { 
   	private Long idSolicitudNovedad;
   	private List<PersonaDTO> listPersonasVerificar;
  
  
 	public VerificarPersonaNovedadRegistrarAnalisisFovis (Long idSolicitudNovedad,List<PersonaDTO> listPersonasVerificar){
 		super();
		this.idSolicitudNovedad=idSolicitudNovedad;
		this.listPersonasVerificar=listPersonasVerificar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudNovedad", idSolicitudNovedad)
			.request(MediaType.APPLICATION_JSON)
			.post(listPersonasVerificar == null ? null : Entity.json(listPersonasVerificar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitudNovedad (Long idSolicitudNovedad){
 		this.idSolicitudNovedad=idSolicitudNovedad;
 	}
 	
 	public Long getIdSolicitudNovedad (){
 		return idSolicitudNovedad;
 	}
  
  	public void setListPersonasVerificar (List<PersonaDTO> listPersonasVerificar){
 		this.listPersonasVerificar=listPersonasVerificar;
 	}
 	
 	public List<PersonaDTO> getListPersonasVerificar (){
 		return listPersonasVerificar;
 	}
  
}