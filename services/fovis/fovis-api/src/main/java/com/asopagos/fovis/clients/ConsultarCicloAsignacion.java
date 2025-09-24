package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCicloAsignacion
 */
public class ConsultarCicloAsignacion extends ServiceClient {
 
  
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloAsignacionModeloDTO result;
  
 	public ConsultarCicloAsignacion (Long idCicloAsignacion){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CicloAsignacionModeloDTO) response.readEntity(CicloAsignacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CicloAsignacionModeloDTO getResult() {
		return result;
	}

 
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}