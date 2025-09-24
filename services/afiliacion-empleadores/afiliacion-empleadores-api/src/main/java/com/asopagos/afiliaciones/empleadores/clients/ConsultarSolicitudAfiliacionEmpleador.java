package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador/{idSolicitudAfiliacionEmpleador}
 */
public class ConsultarSolicitudAfiliacionEmpleador extends ServiceClient {
 
  	private Long idSolicitudAfiliacionEmpleador;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAfiliacionEmpleador result;
  
 	public ConsultarSolicitudAfiliacionEmpleador (Long idSolicitudAfiliacionEmpleador){
 		super();
		this.idSolicitudAfiliacionEmpleador=idSolicitudAfiliacionEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudAfiliacionEmpleador", idSolicitudAfiliacionEmpleador)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAfiliacionEmpleador) response.readEntity(SolicitudAfiliacionEmpleador.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAfiliacionEmpleador getResult() {
		return result;
	}

 	public void setIdSolicitudAfiliacionEmpleador (Long idSolicitudAfiliacionEmpleador){
 		this.idSolicitudAfiliacionEmpleador=idSolicitudAfiliacionEmpleador;
 	}
 	
 	public Long getIdSolicitudAfiliacionEmpleador (){
 		return idSolicitudAfiliacionEmpleador;
 	}
  
  
}