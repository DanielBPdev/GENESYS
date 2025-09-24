package com.asopagos.fovis.composite.clients;

import java.lang.Long;
import com.asopagos.dto.fovis.EjecucionAsignacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/asignacionFovisComposite/obtenerResultadosAsignacion
 */
public class ObtenerResultadosAsignacion extends ServiceClient {
 
  
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EjecucionAsignacionDTO result;
  
 	public ObtenerResultadosAsignacion (Long idCicloAsignacion){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EjecucionAsignacionDTO) response.readEntity(EjecucionAsignacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EjecucionAsignacionDTO getResult() {
		return result;
	}

 
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}