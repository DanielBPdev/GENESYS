package com.asopagos.constantes.parametros.clients;

import java.lang.Long;
import com.asopagos.dto.SedeCajaCompensacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/ccf/sedes/{idSede}
 */
public class ConsultarSede extends ServiceClient {
 
  	private Long idSede;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SedeCajaCompensacionDTO result;
  
 	public ConsultarSede (Long idSede){
 		super();
		this.idSede=idSede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSede", idSede)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SedeCajaCompensacionDTO) response.readEntity(SedeCajaCompensacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SedeCajaCompensacionDTO getResult() {
		return result;
	}

 	public void setIdSede (Long idSede){
 		this.idSede=idSede;
 	}
 	
 	public Long getIdSede (){
 		return idSede;
 	}
  
  
}