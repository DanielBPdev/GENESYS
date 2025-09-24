package com.asopagos.zenith.clients;

import java.lang.String;
import com.asopagos.dto.ConfirmacionProcesoArchivoZenithDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/externalAPI/clienteZenith/confirmarProcesamientoArchivo
 */
public class ConfirmarProcesamientoArchivo extends ServiceClient { 
    	private ConfirmacionProcesoArchivoZenithDTO confirmacionProcesoArchivoZenithDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConfirmarProcesamientoArchivo (ConfirmacionProcesoArchivoZenithDTO confirmacionProcesoArchivoZenithDTO){
 		super();
		this.confirmacionProcesoArchivoZenithDTO=confirmacionProcesoArchivoZenithDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(confirmacionProcesoArchivoZenithDTO == null ? null : Entity.json(confirmacionProcesoArchivoZenithDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  
  	public void setConfirmacionProcesoArchivoZenithDTO (ConfirmacionProcesoArchivoZenithDTO confirmacionProcesoArchivoZenithDTO){
 		this.confirmacionProcesoArchivoZenithDTO=confirmacionProcesoArchivoZenithDTO;
 	}
 	
 	public ConfirmacionProcesoArchivoZenithDTO getConfirmacionProcesoArchivoZenithDTO (){
 		return confirmacionProcesoArchivoZenithDTO;
 	}
  
}