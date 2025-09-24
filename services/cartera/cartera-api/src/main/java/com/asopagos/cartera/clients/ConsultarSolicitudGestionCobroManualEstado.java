package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudGestionCobroManualModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarSolicitudGestionCobroManualEstado
 */
public class ConsultarSolicitudGestionCobroManualEstado extends ServiceClient {
 
  
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroManualModeloDTO result;
  
 	public ConsultarSolicitudGestionCobroManualEstado (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudGestionCobroManualModeloDTO) response.readEntity(SolicitudGestionCobroManualModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudGestionCobroManualModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
}