package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarTiposAhorroPostulacion
 */
public class ConsultarTiposAhorroPostulacion extends ServiceClient {
 
  
  	private Long idPostulacionFovis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoAhorroPrevioEnum> result;
  
 	public ConsultarTiposAhorroPostulacion (Long idPostulacionFovis){
 		super();
		this.idPostulacionFovis=idPostulacionFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPostulacionFovis", idPostulacionFovis)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TipoAhorroPrevioEnum>) response.readEntity(new GenericType<List<TipoAhorroPrevioEnum>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TipoAhorroPrevioEnum> getResult() {
		return result;
	}

 
  	public void setIdPostulacionFovis (Long idPostulacionFovis){
 		this.idPostulacionFovis=idPostulacionFovis;
 	}
 	
 	public Long getIdPostulacionFovis (){
 		return idPostulacionFovis;
 	}
  
}