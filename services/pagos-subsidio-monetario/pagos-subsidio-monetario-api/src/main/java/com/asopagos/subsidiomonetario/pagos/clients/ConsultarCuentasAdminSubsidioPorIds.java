package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/consultarCuentasAdminSubsidioPorIds
 */
public class ConsultarCuentasAdminSubsidioPorIds extends ServiceClient {
 
	private List<Long> ltsIdsCuentaAdministradorSubsidio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidioDTO> result;
  
 	public ConsultarCuentasAdminSubsidioPorIds (List<Long> ltsIdsCuentaAdministradorSubsidio){
 		super();
		this.ltsIdsCuentaAdministradorSubsidio = ltsIdsCuentaAdministradorSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON)
									.post(ltsIdsCuentaAdministradorSubsidio == null ? null : Entity.json(ltsIdsCuentaAdministradorSubsidio));


									
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuentaAdministradorSubsidioDTO>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidioDTO> getResult() {
		return result;
	}

	public void setLtsIdsCuentaAdministradorSubsidio (List<Long> ltsIdsCuentaAdministradorSubsidio){
 		this.ltsIdsCuentaAdministradorSubsidio=ltsIdsCuentaAdministradorSubsidio;
 	}
 	
 	public List<Long> getLtsIdsCuentaAdministradorSubsidio (){
 		return ltsIdsCuentaAdministradorSubsidio;
 	}
  
}