package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.pila.dto.InformacionAporteAdicionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarComparativoAporte
 */
public class ConsultarComparativoAporte extends ServiceClient {
 
  
  	private Long idRegistroDetallado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionAporteAdicionDTO result;
  
 	public ConsultarComparativoAporte (Long idRegistroDetallado){
 		super();
		this.idRegistroDetallado=idRegistroDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegistroDetallado", idRegistroDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionAporteAdicionDTO) response.readEntity(InformacionAporteAdicionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionAporteAdicionDTO getResult() {
		return result;
	}

 
  	public void setIdRegistroDetallado (Long idRegistroDetallado){
 		this.idRegistroDetallado=idRegistroDetallado;
 	}
 	
 	public Long getIdRegistroDetallado (){
 		return idRegistroDetallado;
 	}
  
}