package com.asopagos.legalizacionfovis.clients;

import java.lang.String;
import com.asopagos.dto.fovis.CondicionesLegalizacionDesembolsoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/calcularValorRestituir
 */
public class CalcularValorRestituir extends ServiceClient {
 
  
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CondicionesLegalizacionDesembolsoDTO result;
  
 	public CalcularValorRestituir (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicado", numeroRadicado)
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

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}