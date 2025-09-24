package com.asopagos.correspondencia.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/asociarCajaCorrespondencias/{sedeCajaCompensacion}
 */
public class AsociarSolicitudesACajaCorrespondencias extends ServiceClient { 
  	private String sedeCajaCompensacion;
    	private List<String> listaRadicados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public AsociarSolicitudesACajaCorrespondencias (String sedeCajaCompensacion,List<String> listaRadicados){
 		super();
		this.sedeCajaCompensacion=sedeCajaCompensacion;
		this.listaRadicados=listaRadicados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("sedeCajaCompensacion", sedeCajaCompensacion)
			.request(MediaType.APPLICATION_JSON)
			.post(listaRadicados == null ? null : Entity.json(listaRadicados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<String> getResult() {
		return result;
	}

 	public void setSedeCajaCompensacion (String sedeCajaCompensacion){
 		this.sedeCajaCompensacion=sedeCajaCompensacion;
 	}
 	
 	public String getSedeCajaCompensacion (){
 		return sedeCajaCompensacion;
 	}
  
  
  	public void setListaRadicados (List<String> listaRadicados){
 		this.listaRadicados=listaRadicados;
 	}
 	
 	public List<String> getListaRadicados (){
 		return listaRadicados;
 	}
  
}