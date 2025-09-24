package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.ResultadoRecaudoCotizanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarResultadoRecaudo
 */
public class ConsultarResultadoRecaudo extends ServiceClient {
 
  
  	private Long idAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoRecaudoCotizanteDTO> result;
  
 	public ConsultarResultadoRecaudo (Long idAporteGeneral){
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
		this.result = (List<ResultadoRecaudoCotizanteDTO>) response.readEntity(new GenericType<List<ResultadoRecaudoCotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoRecaudoCotizanteDTO> getResult() {
		return result;
	}

 
  	public void setIdAporteGeneral (Long idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public Long getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  
}