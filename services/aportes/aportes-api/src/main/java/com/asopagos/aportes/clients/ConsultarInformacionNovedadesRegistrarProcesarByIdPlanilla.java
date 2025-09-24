package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarInformacionNovedadesRegistrarProcesarByIdPlanilla
 */
public class ConsultarInformacionNovedadesRegistrarProcesarByIdPlanilla extends ServiceClient {
 
  
  	private Boolean omitirMarca;
  	private Long idPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InformacionPlanillasRegistrarProcesarDTO> result;
  
 	public ConsultarInformacionNovedadesRegistrarProcesarByIdPlanilla (Boolean omitirMarca,Long idPlanilla){
 		super();
		this.omitirMarca=omitirMarca;
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("omitirMarca", omitirMarca)
						.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InformacionPlanillasRegistrarProcesarDTO>) response.readEntity(new GenericType<List<InformacionPlanillasRegistrarProcesarDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InformacionPlanillasRegistrarProcesarDTO> getResult() {
		return result;
	}

 
  	public void setOmitirMarca (Boolean omitirMarca){
 		this.omitirMarca=omitirMarca;
 	}
 	
 	public Boolean getOmitirMarca (){
 		return omitirMarca;
 	}
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
}