package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.CicloModalidadModeloDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarModalidadesPorCiclo
 */
public class ConsultarModalidadesPorCiclo extends ServiceClient {
 
  
  	private Boolean habilitadas;
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloModalidadModeloDTO> result;
  
 	public ConsultarModalidadesPorCiclo (Boolean habilitadas,Long idCicloAsignacion){
 		super();
		this.habilitadas=habilitadas;
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("habilitadas", habilitadas)
						.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloModalidadModeloDTO>) response.readEntity(new GenericType<List<CicloModalidadModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloModalidadModeloDTO> getResult() {
		return result;
	}

 
  	public void setHabilitadas (Boolean habilitadas){
 		this.habilitadas=habilitadas;
 	}
 	
 	public Boolean getHabilitadas (){
 		return habilitadas;
 	}
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}