package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import com.asopagos.dto.fovis.HistoricoNovedadFovisDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarHistoricoNovedadesFovisHogar
 */
public class ConsultarHistoricoNovedadesFovisHogar extends ServiceClient {
 
  
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoNovedadFovisDTO> result;
  
 	public ConsultarHistoricoNovedadesFovisHogar (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoNovedadFovisDTO>) response.readEntity(new GenericType<List<HistoricoNovedadFovisDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoNovedadFovisDTO> getResult() {
		return result;
	}

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
}