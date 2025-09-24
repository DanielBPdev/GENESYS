package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicacion}/consultarSolicitudFiscalizacion
 */
public class ConsultarSolicitudFiscalizacion extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudFiscalizacionModeloDTO result;
  
 	public ConsultarSolicitudFiscalizacion (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudFiscalizacionModeloDTO) response.readEntity(SolicitudFiscalizacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudFiscalizacionModeloDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}