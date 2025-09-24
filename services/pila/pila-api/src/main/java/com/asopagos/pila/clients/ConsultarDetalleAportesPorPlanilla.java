package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarDetalleAportesPorPlanilla
 */
public class ConsultarDetalleAportesPorPlanilla extends ServiceClient {
 
  
  	private Long idRegistroGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDetalladoPlanillaDTO> result;
  
 	public ConsultarDetalleAportesPorPlanilla (Long idRegistroGeneral){
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
		this.result = (List<AporteDetalladoPlanillaDTO>) response.readEntity(new GenericType<List<AporteDetalladoPlanillaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteDetalladoPlanillaDTO> getResult() {
		return result;
	}

 
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
}