package com.asopagos.legalizacionfovis.clients;

import java.lang.String;
import com.asopagos.dto.fovis.CondicionesLegalizacionDesembolsoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarHistoricoDesembolsoPorNumeroRadicado
 */
public class ConsultarHistoricoDesembolsoPorNumeroRadicado extends ServiceClient {
 
  
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CondicionesLegalizacionDesembolsoDTO result;
  
 	public ConsultarHistoricoDesembolsoPorNumeroRadicado (String numeroRadicacion){
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
		this.result = (CondicionesLegalizacionDesembolsoDTO) response.readEntity(CondicionesLegalizacionDesembolsoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CondicionesLegalizacionDesembolsoDTO getResult() {
		return result;
	}

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
}