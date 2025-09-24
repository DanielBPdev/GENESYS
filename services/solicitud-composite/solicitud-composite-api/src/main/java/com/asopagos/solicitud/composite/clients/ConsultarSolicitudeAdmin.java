package com.asopagos.solicitud.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.SolicitudDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudComposite/consultarSolicitudeAdmin
 */
public class ConsultarSolicitudeAdmin extends ServiceClient {
 
  
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudDTO> result;
  
 	public ConsultarSolicitudeAdmin (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicado", numeroRadicado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudDTO>) response.readEntity(new GenericType<List<SolicitudDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudDTO> getResult() {
		return result;
	}

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}