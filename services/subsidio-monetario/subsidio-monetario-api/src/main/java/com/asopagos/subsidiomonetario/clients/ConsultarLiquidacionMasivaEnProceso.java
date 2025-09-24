package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/ConsultarLiquidacionMasivaEnProceso
 */
public class ConsultarLiquidacionMasivaEnProceso extends ServiceClient {
 
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLiquidacionSubsidioModeloDTO result;
  
 	public ConsultarLiquidacionMasivaEnProceso (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
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
  
}