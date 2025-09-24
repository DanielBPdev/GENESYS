package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import java.util.List;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/consultarSolicitudPostulacionPorNumeroCedula
 */
public class ConsultarSolicitudPostulacionPorNumeroCedula extends ServiceClient {
 
  
  	private List<String> numeroCedula;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,SolicitudPostulacionModeloDTO> result;
  
 	public ConsultarSolicitudPostulacionPorNumeroCedula (List<String> numeroCedula){
 		super();
		this.numeroCedula=numeroCedula;
 	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.request(MediaType.APPLICATION_JSON)
				.post(numeroCedula == null ? null : Entity.json(numeroCedula));
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,SolicitudPostulacionModeloDTO>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,SolicitudPostulacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setNumeroCedula (List<String> numeroCedula){
 		this.numeroCedula=numeroCedula;
 	}
 	
 	public List<String> getNumeroCedula (){
 		return numeroCedula;
 	}
  
}