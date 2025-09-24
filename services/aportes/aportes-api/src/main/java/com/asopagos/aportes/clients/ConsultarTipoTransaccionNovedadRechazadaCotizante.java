package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarTipoTransaccionNovedadRechazadaCotizante
 */
public class ConsultarTipoTransaccionNovedadRechazadaCotizante extends ServiceClient {
 
  
  	private Long idAporteDetallado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public ConsultarTipoTransaccionNovedadRechazadaCotizante (Long idAporteDetallado){
 		super();
		this.idAporteDetallado=idAporteDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAporteDetallado", idAporteDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<String> getResult() {
		return result;
	}

 
  	public void setIdAporteDetallado (Long idAporteDetallado){
 		this.idAporteDetallado=idAporteDetallado;
 	}
 	
 	public Long getIdAporteDetallado (){
 		return idAporteDetallado;
 	}
  
}