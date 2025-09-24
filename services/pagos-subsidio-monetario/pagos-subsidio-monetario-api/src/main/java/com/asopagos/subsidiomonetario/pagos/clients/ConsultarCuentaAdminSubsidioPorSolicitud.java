package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarCuentaAdminSubsidioPorSolicitud
 */
public class ConsultarCuentaAdminSubsidioPorSolicitud extends ServiceClient {
 
  
  	private Long solicitudLiquidacionSubsidio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidio> result;
  
 	public ConsultarCuentaAdminSubsidioPorSolicitud (Long solicitudLiquidacionSubsidio){
 		super();
		this.solicitudLiquidacionSubsidio=solicitudLiquidacionSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("solicitudLiquidacionSubsidio", solicitudLiquidacionSubsidio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuentaAdministradorSubsidio>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidio>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidio> getResult() {
		return result;
	}

 
  	public void setSolicitudLiquidacionSubsidio (Long solicitudLiquidacionSubsidio){
 		this.solicitudLiquidacionSubsidio=solicitudLiquidacionSubsidio;
 	}
 	
 	public Long getSolicitudLiquidacionSubsidio (){
 		return solicitudLiquidacionSubsidio;
 	}
  
}