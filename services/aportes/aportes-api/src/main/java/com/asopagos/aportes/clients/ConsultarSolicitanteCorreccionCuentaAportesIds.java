package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.SolicitanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/obtenerSolicitantesCuentaAportesIds
 */
public class ConsultarSolicitanteCorreccionCuentaAportesIds extends ServiceClient { 
    	private List<Long> idsPersonas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitanteDTO> result;
  
 	public ConsultarSolicitanteCorreccionCuentaAportesIds (List<Long> idsPersonas){
 		super();
		this.idsPersonas=idsPersonas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsPersonas == null ? null : Entity.json(idsPersonas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitanteDTO>) response.readEntity(new GenericType<List<SolicitanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitanteDTO> getResult() {
		return result;
	}

 
  
  	public void setIdsPersonas (List<Long> idsPersonas){
 		this.idsPersonas=idsPersonas;
 	}
 	
 	public List<Long> getIdsPersonas (){
 		return idsPersonas;
 	}
  
}