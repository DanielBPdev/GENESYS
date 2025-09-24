package com.asopagos.fovis.clients;

import com.asopagos.dto.CruceDetalleDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCrucesNuevosByNroPostulacionIdCargue
 */
public class ConsultarCrucesNuevosPorNumeroPostulacionIdCargueArchivo extends ServiceClient {
 
  
  	private Long idCargue;
  	private String numeroPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDetalleDTO> result;
  
 	public ConsultarCrucesNuevosPorNumeroPostulacionIdCargueArchivo (Long idCargue,String numeroPostulacion){
 		super();
		this.idCargue=idCargue;
		this.numeroPostulacion=numeroPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCargue", idCargue)
						.queryParam("numeroPostulacion", numeroPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CruceDetalleDTO>) response.readEntity(new GenericType<List<CruceDetalleDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CruceDetalleDTO> getResult() {
		return result;
	}

 
  	public void setIdCargue (Long idCargue){
 		this.idCargue=idCargue;
 	}
 	
 	public Long getIdCargue (){
 		return idCargue;
 	}
  	public void setNumeroPostulacion (String numeroPostulacion){
 		this.numeroPostulacion=numeroPostulacion;
 	}
 	
 	public String getNumeroPostulacion (){
 		return numeroPostulacion;
 	}
  
}