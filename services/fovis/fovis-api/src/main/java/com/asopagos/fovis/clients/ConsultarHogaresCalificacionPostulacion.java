package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarHogaresCalificacionPostulacion
 */
public class ConsultarHogaresCalificacionPostulacion extends ServiceClient {
 
  
  	private String nombreCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionFOVISModeloDTO> result;
  
 	public ConsultarHogaresCalificacionPostulacion (String nombreCicloAsignacion){
 		super();
		this.nombreCicloAsignacion=nombreCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreCicloAsignacion", nombreCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PostulacionFOVISModeloDTO>) response.readEntity(new GenericType<List<PostulacionFOVISModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulacionFOVISModeloDTO> getResult() {
		return result;
	}

 
  	public void setNombreCicloAsignacion (String nombreCicloAsignacion){
 		this.nombreCicloAsignacion=nombreCicloAsignacion;
 	}
 	
 	public String getNombreCicloAsignacion (){
 		return nombreCicloAsignacion;
 	}
  
}