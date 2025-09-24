package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarParametrizacionLiqEspecifica
 */
public class ConsultarParametrizacionLiqEspecifica extends ServiceClient {
 
  
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LiquidacionEspecificaDTO result;
  
 	public ConsultarParametrizacionLiqEspecifica (String numeroRadicado){
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
		this.result = (LiquidacionEspecificaDTO) response.readEntity(LiquidacionEspecificaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public LiquidacionEspecificaDTO getResult() {
		return result;
	}

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}