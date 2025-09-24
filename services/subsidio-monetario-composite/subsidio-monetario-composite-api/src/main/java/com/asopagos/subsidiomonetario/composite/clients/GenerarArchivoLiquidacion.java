package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetarioComposite/generarArchivoLiquidacion/{archivoLiquidacionSubsidio}/{numeroRadicacion}
 */
public class GenerarArchivoLiquidacion extends ServiceClient {
	
	private Long archivoLiquidacionSubsidio;
	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GenerarArchivoLiquidacion (Long archivoLiquidacionSubsidio, String numeroRadicacion){
 		super();
		this.archivoLiquidacionSubsidio=archivoLiquidacionSubsidio;
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("archivoLiquidacionSubsidio", archivoLiquidacionSubsidio)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}


	public Long getArchivoLiquidacionSubsidio() {
		return this.archivoLiquidacionSubsidio;
	}

	public void setArchivoLiquidacionSubsidio(Long archivoLiquidacionSubsidio) {
		this.archivoLiquidacionSubsidio = archivoLiquidacionSubsidio;
	}


	public String getNumeroRadicacion() {
		return this.numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}
  
}