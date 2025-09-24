package com.asopagos.constantes.parametros.clients;

import com.asopagos.constantes.parametros.dto.ParametrizacionGapsDTO;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.Entity;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/parametros/admon/{nombreClase}/{idEntidad}
 */
public class ActualizarParametrizacionGaps extends ServiceClient {
 
    private ParametrizacionGapsDTO inDTO;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Object result;
  
 	public ActualizarParametrizacionGaps (ParametrizacionGapsDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Object) response.readEntity(Object.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Object getResult() {
		return result;
	}


    public void setInDTO (ParametrizacionGapsDTO inDTO){
        this.inDTO=inDTO;
    }

    public ParametrizacionGapsDTO getInDTO (){
        return inDTO;
    }
  
  
}