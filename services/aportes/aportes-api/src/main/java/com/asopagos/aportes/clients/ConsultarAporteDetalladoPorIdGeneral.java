package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarAporteDetalladoPorIdGeneral
 */
public class ConsultarAporteDetalladoPorIdGeneral extends ServiceClient {
 
  
  	private Long idAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDetalladoModeloDTO> result;
  
 	public ConsultarAporteDetalladoPorIdGeneral (Long idAporteGeneral){
 		super();
		this.idAporteGeneral=idAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAporteGeneral", idAporteGeneral)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AporteDetalladoModeloDTO>) response.readEntity(new GenericType<List<AporteDetalladoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteDetalladoModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdAporteGeneral (Long idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public Long getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  
}