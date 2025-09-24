package com.asopagos.asignaciones.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/asignacionSolicitud/parametrizacionMetodoAsignacion
 */
public class ConsultarMetodoAsignadoProcesoSede extends ServiceClient {
 
  
  	private ProcesoEnum proceso;
  	private Long sede;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionMetodoAsignacion result;
  
 	public ConsultarMetodoAsignadoProcesoSede (ProcesoEnum proceso,Long sede){
 		super();
		this.proceso=proceso;
		this.sede=sede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("proceso", proceso)
						.queryParam("sede", sede)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionMetodoAsignacion) response.readEntity(ParametrizacionMetodoAsignacion.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionMetodoAsignacion getResult() {
		return result;
	}

 
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setSede (Long sede){
 		this.sede=sede;
 	}
 	
 	public Long getSede (){
 		return sede;
 	}
  
}