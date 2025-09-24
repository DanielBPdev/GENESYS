package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.AporteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/{idRegistroGeneral}/consultarAporteTemporal
 */
public class ConsultarAporteTemporal extends ServiceClient {
 
  	private Long idRegistroGeneral;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDTO> result;
  
 	public ConsultarAporteTemporal (Long idRegistroGeneral){
 		super();
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idRegistroGeneral", idRegistroGeneral)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AporteDTO>) response.readEntity(new GenericType<List<AporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteDTO> getResult() {
		return result;
	}

 	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
  
}