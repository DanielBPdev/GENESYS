package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/cuentaAdmin/medioTarjeta/{idCuentaAdminSubsidio}
 */
public class ConsultarCuentaAdminMedioTarjeta extends ServiceClient {
 
  	private Long idCuentaAdminSubsidio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CuentaAdministradorSubsidioDTO result;
  
 	public ConsultarCuentaAdminMedioTarjeta (Long idCuentaAdminSubsidio){
 		super();
		this.idCuentaAdminSubsidio=idCuentaAdminSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idCuentaAdminSubsidio", idCuentaAdminSubsidio)
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

 	public void setIdCuentaAdminSubsidio (Long idCuentaAdminSubsidio){
 		this.idCuentaAdminSubsidio=idCuentaAdminSubsidio;
 	}
 	
 	public Long getIdCuentaAdminSubsidio (){
 		return idCuentaAdminSubsidio;
 	}
  
  
}