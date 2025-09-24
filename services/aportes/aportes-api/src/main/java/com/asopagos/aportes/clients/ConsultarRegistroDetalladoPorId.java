package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarRegistroDetalladoPorId/{idRegistroDetallado}
 */
public class ConsultarRegistroDetalladoPorId extends ServiceClient {
 
  	private Long idRegistroDetallado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroDetalladoModeloDTO result;
  
 	public ConsultarRegistroDetalladoPorId (Long idRegistroDetallado){
 		super();
		this.idRegistroDetallado=idRegistroDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idRegistroDetallado", idRegistroDetallado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroDetalladoModeloDTO) response.readEntity(RegistroDetalladoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroDetalladoModeloDTO getResult() {
		return result;
	}

 	public void setIdRegistroDetallado (Long idRegistroDetallado){
 		this.idRegistroDetallado=idRegistroDetallado;
 	}
 	
 	public Long getIdRegistroDetallado (){
 		return idRegistroDetallado;
 	}
  
  
}