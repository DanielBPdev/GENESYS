package com.asopagos.subsidiomonetario.clients;

import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarPorcentajeAvanzadoProcesoLiquidacion/{numeroRadicacion}
 */
public class ConsultarPorcentajeAvanceProcesoLiquidacion extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Integer result;
  
 	public ConsultarPorcentajeAvanceProcesoLiquidacion (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Integer) response.readEntity(Integer.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Integer getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}