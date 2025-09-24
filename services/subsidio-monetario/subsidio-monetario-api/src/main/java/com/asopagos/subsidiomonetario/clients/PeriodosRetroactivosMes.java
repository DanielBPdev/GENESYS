package com.asopagos.subsidiomonetario.clients;

import java.lang.String;
import com.asopagos.subsidiomonetario.dto.DetalleCantidadEmpresaTrabajadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/empresas/periodosRetroactivosMes/{numeroSolicitud}
 */
public class PeriodosRetroactivosMes extends ServiceClient {
 
  	private String numeroSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleCantidadEmpresaTrabajadorDTO result;
  
 	public PeriodosRetroactivosMes (String numeroSolicitud){
 		super();
		this.numeroSolicitud=numeroSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroSolicitud", numeroSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleCantidadEmpresaTrabajadorDTO) response.readEntity(DetalleCantidadEmpresaTrabajadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleCantidadEmpresaTrabajadorDTO getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  
}