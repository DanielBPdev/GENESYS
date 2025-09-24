package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionMontoLiquidadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/dispersion/montoLiquidacion/{numeroRadicacion}
 */
public class ConsultarDispersionMontoLiquidacion extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DispersionMontoLiquidadoDTO result;
  
 	public ConsultarDispersionMontoLiquidacion (String numeroRadicacion){
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
		this.result = (DispersionMontoLiquidadoDTO) response.readEntity(DispersionMontoLiquidadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DispersionMontoLiquidadoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}