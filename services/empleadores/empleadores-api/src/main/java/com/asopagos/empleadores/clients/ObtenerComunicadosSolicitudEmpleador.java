package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.Vista360EmpleadorRespuestaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/obtenerComunicadosSolicitudEmpleador/{idSolicitud}
 */
public class ObtenerComunicadosSolicitudEmpleador extends ServiceClient {
 
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Vista360EmpleadorRespuestaDTO> result;
  
 	public ObtenerComunicadosSolicitudEmpleador (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Vista360EmpleadorRespuestaDTO>) response.readEntity(new GenericType<List<Vista360EmpleadorRespuestaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Vista360EmpleadorRespuestaDTO> getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}