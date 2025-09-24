package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarDatosComunicadoByIdPlanilla
 */
public class ConsultarDatosComunicadoByIdPlanilla extends ServiceClient {
 
  
  	private Long idPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosComunicadoPlanillaDTO> result;
  
 	public ConsultarDatosComunicadoByIdPlanilla (Long idPlanilla){
 		super();
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosComunicadoPlanillaDTO>) response.readEntity(new GenericType<List<DatosComunicadoPlanillaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosComunicadoPlanillaDTO> getResult() {
		return result;
	}

 
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
}