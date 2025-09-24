package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarSolicitudPostulacionPorNumeroPostulacion
 */
public class ConsultarSolicitudPostulacionPorNumeroPostulacion extends ServiceClient {
 
  
  	private String numeroPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionModeloDTO result;
  
 	public ConsultarSolicitudPostulacionPorNumeroPostulacion (String numeroPostulacion){
 		super();
		this.numeroPostulacion=numeroPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroPostulacion", numeroPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudPostulacionModeloDTO) response.readEntity(SolicitudPostulacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudPostulacionModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroPostulacion (String numeroPostulacion){
 		this.numeroPostulacion=numeroPostulacion;
 	}
 	
 	public String getNumeroPostulacion (){
 		return numeroPostulacion;
 	}
  
}