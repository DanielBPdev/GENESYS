package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarDocumentosSoporteOferentePorIdOferente
 */
public class ConsultarDocumentosSoporteOferentePorIdOferente extends ServiceClient {
 
  
  	private Long idOferente;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DocumentoSoporteModeloDTO> result;
  
 	public ConsultarDocumentosSoporteOferentePorIdOferente (Long idOferente){
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
		this.result = (List<DocumentoSoporteModeloDTO>) response.readEntity(new GenericType<List<DocumentoSoporteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DocumentoSoporteModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdOferente (Long idOferente){
 		this.idOferente=idOferente;
 	}
 	
 	public Long getIdOferente (){
 		return idOferente;
 	}
  
}