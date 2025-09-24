package com.asopagos.legalizacionfovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarOferentePorId
 */
public class ConsultarOferentePorId extends ServiceClient {
 
  
  	private Long idOferente;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private OferenteModeloDTO result;
  
 	public ConsultarOferentePorId (Long idOferente){
 		super();
		this.idOferente=idOferente;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idOferente", idOferente)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (OferenteModeloDTO) response.readEntity(OferenteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public OferenteModeloDTO getResult() {
		return result;
	}

 
  	public void setIdOferente (Long idOferente){
 		this.idOferente=idOferente;
 	}
 	
 	public Long getIdOferente (){
 		return idOferente;
 	}
  
}