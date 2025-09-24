package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Object;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarInstanciaSolicitudAsociadaPostulacion
 */
public class ConsultarInstanciaSolicitudAsociadaPostulacion extends ServiceClient {
 
  
  	private Long idPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Object> result;
  
 	public ConsultarInstanciaSolicitudAsociadaPostulacion (Long idPostulacion){
 		super();
		this.idPostulacion=idPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPostulacion", idPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Object>) response.readEntity(new GenericType<List<Object>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Object> getResult() {
		return result;
	}

 
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
}