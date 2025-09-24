package com.asopagos.comunicados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.ComunicadoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/comunicados/{idComunicado}/consultarComunicado
 */
public class ConsultarComunicado extends ServiceClient {
 
  	private Long idComunicado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ComunicadoModeloDTO result;
  
 	public ConsultarComunicado (Long idComunicado){
 		super();
		this.idComunicado=idComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idComunicado", idComunicado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ComunicadoModeloDTO) response.readEntity(ComunicadoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ComunicadoModeloDTO getResult() {
		return result;
	}

 	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  
  
}