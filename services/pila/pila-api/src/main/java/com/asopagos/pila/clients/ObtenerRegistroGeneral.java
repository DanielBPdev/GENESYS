package com.asopagos.pila.clients;

import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/obtenerRegistroGeneral
 */
public class ObtenerRegistroGeneral extends ServiceClient {
 
  
  	private Long idRegistroGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroGeneralModeloDTO result;
  
 	public ObtenerRegistroGeneral (Long idRegistroGeneral){
 		super();
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegistroGeneral", idRegistroGeneral)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroGeneralModeloDTO) response.readEntity(RegistroGeneralModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroGeneralModeloDTO getResult() {
		return result;
	}

 
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
}