package com.asopagos.solicitud.composite.clients;

import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudComposite/asociarCajaCorrespondencia/{sedeCajaCompensacion}
 */
public class AsociarSolicitudesACajaCorrespondencia extends ServiceClient { 
  	private String sedeCajaCompensacion;
    	private List<String> listaRadicados;
  
  
 	public AsociarSolicitudesACajaCorrespondencia (String sedeCajaCompensacion,List<String> listaRadicados){
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