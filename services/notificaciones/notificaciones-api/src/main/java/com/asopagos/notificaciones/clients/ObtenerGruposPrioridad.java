package com.asopagos.notificaciones.clients;

import com.asopagos.notificaciones.dto.GrupoRolPrioridadDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/enviarCorreo/obtenerGruposPrioridad
 */
public class ObtenerGruposPrioridad extends ServiceClient {
 
  
  	private String proceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GrupoRolPrioridadDTO> result;
  
 	public ObtenerGruposPrioridad (String proceso){
 		super();
		this.proceso=proceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("proceso", proceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<GrupoRolPrioridadDTO>) response.readEntity(new GenericType<List<GrupoRolPrioridadDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<GrupoRolPrioridadDTO> getResult() {
		return result;
	}

 
  	public void setProceso (String proceso){
 		this.proceso=proceso;
 	}
 	
 	public String getProceso (){
 		return proceso;
 	}
  
}