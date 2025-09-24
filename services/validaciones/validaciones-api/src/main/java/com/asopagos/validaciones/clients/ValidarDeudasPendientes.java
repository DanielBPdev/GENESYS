package com.asopagos.validaciones.clients;

import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/validarDeudasPendientes
 */
public class ValidarDeudasPendientes extends ServiceClient { 
    	private DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarDeudasPendientes (DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO){
 		super();
		this.datosBasicosIdentificacionDTO=datosBasicosIdentificacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosBasicosIdentificacionDTO == null ? null : Entity.json(datosBasicosIdentificacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setDatosBasicosIdentificacionDTO (DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO){
 		this.datosBasicosIdentificacionDTO=datosBasicosIdentificacionDTO;
 	}
 	
 	public DatosBasicosIdentificacionDTO getDatosBasicosIdentificacionDTO (){
 		return datosBasicosIdentificacionDTO;
 	}
  
}