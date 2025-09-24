package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarEmpleadorNumero/{numeroIdentificacion}
 */
public class ConsultarEmpleadorNumero extends ServiceClient { 
  	private String numeroIdentificacion;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpleadorModeloDTO> result;
  
 	public ConsultarEmpleadorNumero (String numeroIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroIdentificacion", numeroIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<EmpleadorModeloDTO>) response.readEntity(new GenericType<List<EmpleadorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<EmpleadorModeloDTO> getResult() {
		return result;
	}

 	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  
  
  
}