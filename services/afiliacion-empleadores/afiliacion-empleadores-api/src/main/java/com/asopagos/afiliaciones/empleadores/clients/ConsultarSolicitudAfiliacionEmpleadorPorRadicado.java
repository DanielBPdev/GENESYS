package com.asopagos.afiliaciones.empleadores.clients;

import com.asopagos.afiliaciones.empleadores.dto.SolicitudAfiliacionEmpleadorDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador/numeroRadicado/{numeroRadicado}
 */
public class ConsultarSolicitudAfiliacionEmpleadorPorRadicado extends ServiceClient {
 
  	private String numeroRadicado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAfiliacionEmpleadorDTO result;
  
 	public ConsultarSolicitudAfiliacionEmpleadorPorRadicado (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicado", numeroRadicado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAfiliacionEmpleadorDTO) response.readEntity(SolicitudAfiliacionEmpleadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAfiliacionEmpleadorDTO getResult() {
		return result;
	}

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
}