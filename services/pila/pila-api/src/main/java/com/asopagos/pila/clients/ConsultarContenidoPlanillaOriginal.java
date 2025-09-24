package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarContenidoPlanillaOriginal
 */
public class ConsultarContenidoPlanillaOriginal extends ServiceClient {
 
  
  	private Long idPlanillaOriginal;
  	private Long idPlanillaCorrecion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaConsultaEmpleadorDTO result;
  
 	public ConsultarContenidoPlanillaOriginal (Long idPlanillaOriginal,Long idPlanillaCorrecion){
 		super();
		this.idPlanillaOriginal=idPlanillaOriginal;
		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaOriginal", idPlanillaOriginal)
						.queryParam("idPlanillaCorrecion", idPlanillaCorrecion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaConsultaEmpleadorDTO) response.readEntity(RespuestaConsultaEmpleadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaConsultaEmpleadorDTO getResult() {
		return result;
	}

 
  	public void setIdPlanillaOriginal (Long idPlanillaOriginal){
 		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 	
 	public Long getIdPlanillaOriginal (){
 		return idPlanillaOriginal;
 	}
  	public void setIdPlanillaCorrecion (Long idPlanillaCorrecion){
 		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 	
 	public Long getIdPlanillaCorrecion (){
 		return idPlanillaCorrecion;
 	}
  
}