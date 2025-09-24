package com.asopagos.legalizacionfovis.composite.clients;

import java.lang.Long;
import com.asopagos.dto.fovis.VisitaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovisComposite/consultarInformacionVisita
 */
public class ConsultarInformacionVisita extends ServiceClient {
 
  
  	private Long idVisita;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private VisitaDTO result;
  
 	public ConsultarInformacionVisita (Long idVisita){
 		super();
		this.idVisita=idVisita;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idVisita", idVisita)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (VisitaDTO) response.readEntity(VisitaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public VisitaDTO getResult() {
		return result;
	}

 
  	public void setIdVisita (Long idVisita){
 		this.idVisita=idVisita;
 	}
 	
 	public Long getIdVisita (){
 		return idVisita;
 	}
  
}