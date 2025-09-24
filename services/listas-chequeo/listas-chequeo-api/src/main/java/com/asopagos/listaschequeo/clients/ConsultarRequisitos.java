package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import com.asopagos.listaschequeo.dto.RequisitoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/requisitos
 */
public class ConsultarRequisitos extends ServiceClient {
 
  
  	private String nombre;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RequisitoDTO> result;
  
 	public ConsultarRequisitos (String nombre){
 		super();
		this.nombre=nombre;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombre", nombre)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RequisitoDTO>) response.readEntity(new GenericType<List<RequisitoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RequisitoDTO> getResult() {
		return result;
	}

 
  	public void setNombre (String nombre){
 		this.nombre=nombre;
 	}
 	
 	public String getNombre (){
 		return nombre;
 	}
  
}