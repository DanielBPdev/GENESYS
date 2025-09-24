package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarCuentasPorAnularMantis266382
 */
public class ConsultarCuentasPorAnularMantis266382 extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SubsidiosConsultaAnularPerdidaDerechoDTO> result;
  
 	public ConsultarCuentasPorAnularMantis266382 (){
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
		this.result = (List<SubsidiosConsultaAnularPerdidaDerechoDTO>) response.readEntity(new GenericType<List<SubsidiosConsultaAnularPerdidaDerechoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SubsidiosConsultaAnularPerdidaDerechoDTO> getResult() {
		return result;
	}

 
  
}