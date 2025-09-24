package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultaCuentaAdmonSubsidio/{idCuentaAdmonSubsidio}
 */
public class ConsultarCuentaAdmonSubsidioDTO extends ServiceClient {
 
  	private Long idCuentaAdmonSubsidio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CuentaAdministradorSubsidioDTO result;
  
 	public ConsultarCuentaAdmonSubsidioDTO (Long idCuentaAdmonSubsidio){
 		super();
		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CuentaAdministradorSubsidioDTO) response.readEntity(CuentaAdministradorSubsidioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CuentaAdministradorSubsidioDTO getResult() {
		return result;
	}

 	public void setIdCuentaAdmonSubsidio (Long idCuentaAdmonSubsidio){
 		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
 	}
 	
 	public Long getIdCuentaAdmonSubsidio (){
 		return idCuentaAdmonSubsidio;
 	}
  
  
}