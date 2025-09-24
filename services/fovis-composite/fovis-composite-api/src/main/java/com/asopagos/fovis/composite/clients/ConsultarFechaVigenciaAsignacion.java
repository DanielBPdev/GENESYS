package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/consultarFechaVigenciaAsignacion
 */
public class ConsultarFechaVigenciaAsignacion extends ServiceClient {
 
  
  	private Long fechaPublicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionDocumentoActaAsignacionDTO result;
  
 	public ConsultarFechaVigenciaAsignacion (Long fechaPublicacion){
 		super();
		this.fechaPublicacion=fechaPublicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaPublicacion", fechaPublicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionDocumentoActaAsignacionDTO) response.readEntity(InformacionDocumentoActaAsignacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionDocumentoActaAsignacionDTO getResult() {
		return result;
	}

 
  	public void setFechaPublicacion (Long fechaPublicacion){
 		this.fechaPublicacion=fechaPublicacion;
 	}
 	
 	public Long getFechaPublicacion (){
 		return fechaPublicacion;
 	}
  
}