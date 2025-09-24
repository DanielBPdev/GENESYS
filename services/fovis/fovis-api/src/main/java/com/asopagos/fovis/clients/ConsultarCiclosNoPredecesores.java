package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCiclosNoPredecesores
 */
public class ConsultarCiclosNoPredecesores extends ServiceClient {
 
  
  	private Long idCicloPredecesor;
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloAsignacionModeloDTO> result;
  
 	public ConsultarCiclosNoPredecesores (Long idCicloPredecesor,Long idCicloAsignacion){
 		super();
		this.idCicloPredecesor=idCicloPredecesor;
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloPredecesor", idCicloPredecesor)
						.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloAsignacionModeloDTO>) response.readEntity(new GenericType<List<CicloAsignacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloAsignacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdCicloPredecesor (Long idCicloPredecesor){
 		this.idCicloPredecesor=idCicloPredecesor;
 	}
 	
 	public Long getIdCicloPredecesor (){
 		return idCicloPredecesor;
 	}
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}