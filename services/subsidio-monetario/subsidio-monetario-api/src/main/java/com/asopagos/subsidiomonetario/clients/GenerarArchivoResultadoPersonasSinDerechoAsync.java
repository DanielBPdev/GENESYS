package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/generarArchivoResultadoPersonasSinDerechoAsync/{idArchivoLiquidacion}/{numeroRadicacion}
 */
public class GenerarArchivoResultadoPersonasSinDerechoAsync extends ServiceClient { 
  	private Long idArchivoLiquidacion;
  	private String numeroRadicacion;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
  
 	public GenerarArchivoResultadoPersonasSinDerechoAsync (Long idArchivoLiquidacion, String numeroRadicacion){
 		super();
		this.idArchivoLiquidacion=idArchivoLiquidacion;
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idArchivoLiquidacion", idArchivoLiquidacion)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}


	public Long getIdArchivoLiquidacion() {
		return this.idArchivoLiquidacion;
	}

	public void setIdArchivoLiquidacion(Long idArchivoLiquidacion) {
		this.idArchivoLiquidacion = idArchivoLiquidacion;
	}


	public String getNumeroRadicacion() {
		return this.numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}




  
}