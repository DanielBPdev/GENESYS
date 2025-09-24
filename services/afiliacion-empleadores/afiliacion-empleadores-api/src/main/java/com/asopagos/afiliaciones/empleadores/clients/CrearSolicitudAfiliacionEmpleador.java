package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador
 */
public class CrearSolicitudAfiliacionEmpleador extends ServiceClient { 
    	private SolicitudAfiliacionEmpleador solAfiliacionEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearSolicitudAfiliacionEmpleador (SolicitudAfiliacionEmpleador solAfiliacionEmpleador){
 		super();
		this.solAfiliacionEmpleador=solAfiliacionEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solAfiliacionEmpleador == null ? null : Entity.json(solAfiliacionEmpleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setSolAfiliacionEmpleador (SolicitudAfiliacionEmpleador solAfiliacionEmpleador){
 		this.solAfiliacionEmpleador=solAfiliacionEmpleador;
 	}
 	
 	public SolicitudAfiliacionEmpleador getSolAfiliacionEmpleador (){
 		return solAfiliacionEmpleador;
 	}
  
}