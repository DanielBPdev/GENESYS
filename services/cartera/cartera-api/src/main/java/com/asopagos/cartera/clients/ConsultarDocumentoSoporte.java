package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarDocumentoSoporte
 */
public class ConsultarDocumentoSoporte extends ServiceClient {
 
  
  	private String idECM;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoSoporteModeloDTO result;
  
 	public ConsultarDocumentoSoporte (String idECM){
 		super();
		this.idECM=idECM;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idECM", idECM)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DocumentoSoporteModeloDTO) response.readEntity(DocumentoSoporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DocumentoSoporteModeloDTO getResult() {
		return result;
	}

 
  	public void setIdECM (String idECM){
 		this.idECM=idECM;
 	}
 	
 	public String getIdECM (){
 		return idECM;
 	}
  
}