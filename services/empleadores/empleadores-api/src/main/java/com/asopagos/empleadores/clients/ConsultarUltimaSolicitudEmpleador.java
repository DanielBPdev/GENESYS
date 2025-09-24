package com.asopagos.empleadores.clients;

import com.asopagos.dto.SolicitudDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/ultimaSolicitudAfiliacion/{idEmpleador}
 */
public class ConsultarUltimaSolicitudEmpleador extends ServiceClient {
 
  	private Long idEmpleador;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudDTO result;
  
 	public ConsultarUltimaSolicitudEmpleador (Long idEmpleador){
 		super();
		this.idEmpleador=idEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudDTO) response.readEntity(SolicitudDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudDTO getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
}