package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCicloSucesor
 */
public class ConsultarCicloSucesor extends ServiceClient {
 
  
  	private Long idCicloPredecesor;
  	private Long fecha;
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloAsignacionModeloDTO result;
  
 	public ConsultarCicloSucesor (Long idCicloPredecesor,Long fecha,Long idCicloAsignacion){
 		super();
		this.idCicloPredecesor=idCicloPredecesor;
		this.fecha=fecha;
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloPredecesor", idCicloPredecesor)
						.queryParam("fecha", fecha)
						.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CicloAsignacionModeloDTO) response.readEntity(CicloAsignacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CicloAsignacionModeloDTO getResult() {
		return result;
	}

 
  	public void setIdCicloPredecesor (Long idCicloPredecesor){
 		this.idCicloPredecesor=idCicloPredecesor;
 	}
 	
 	public Long getIdCicloPredecesor (){
 		return idCicloPredecesor;
 	}
  	public void setFecha (Long fecha){
 		this.fecha=fecha;
 	}
 	
 	public Long getFecha (){
 		return fecha;
 	}
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}