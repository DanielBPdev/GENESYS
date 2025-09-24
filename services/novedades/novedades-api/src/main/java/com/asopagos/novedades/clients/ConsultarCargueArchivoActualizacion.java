package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesCargueMultiple/consultarCargueArchivoActualizacion
 */
public class ConsultarCargueArchivoActualizacion extends ServiceClient {
 
  
  	private Long idCargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CargueArchivoActualizacionDTO result;
  
 	public ConsultarCargueArchivoActualizacion (Long idCargue){
 		super();
		this.idCargue=idCargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCargue", idCargue)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CargueArchivoActualizacionDTO) response.readEntity(CargueArchivoActualizacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CargueArchivoActualizacionDTO getResult() {
		return result;
	}

 
  	public void setIdCargue (Long idCargue){
 		this.idCargue=idCargue;
 	}
 	
 	public Long getIdCargue (){
 		return idCargue;
 	}
  
}