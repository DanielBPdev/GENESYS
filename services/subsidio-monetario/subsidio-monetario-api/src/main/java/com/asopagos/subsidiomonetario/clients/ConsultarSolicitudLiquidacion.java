package com.asopagos.subsidiomonetario.clients;

import java.lang.String;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/solicitudLiquidacion/consultar/{numeroRadicado}
 */
public class ConsultarSolicitudLiquidacion extends ServiceClient {
 
  	private String numeroRadicado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLiquidacionSubsidioModeloDTO result;
  
 	public ConsultarSolicitudLiquidacion (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicado", numeroRadicado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudLiquidacionSubsidioModeloDTO) response.readEntity(SolicitudLiquidacionSubsidioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudLiquidacionSubsidioModeloDTO getResult() {
		return result;
	}

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
}