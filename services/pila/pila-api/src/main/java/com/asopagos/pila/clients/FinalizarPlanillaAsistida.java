package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.pila.dto.ResultadoFinalizacionPlanillaAsistidaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/finalizarPlanillaAsistida
 */
public class FinalizarPlanillaAsistida extends ServiceClient {
 
  
  	private Long idRegistroGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoFinalizacionPlanillaAsistidaDTO result;
  
 	public FinalizarPlanillaAsistida (Long idRegistroGeneral){
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
		this.result = (ResultadoFinalizacionPlanillaAsistidaDTO) response.readEntity(ResultadoFinalizacionPlanillaAsistidaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoFinalizacionPlanillaAsistidaDTO getResult() {
		return result;
	}

 
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
}