package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/generar/InformesRetiros/SubsidioMonetario
 */
public class GenerarInformesRetirosSubsidioMonetario extends ServiceClient { 
    	private SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidioDTO> result;
  
 	public GenerarInformesRetirosSubsidioMonetario (SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO){
 		super();
		this.suConsumoDTO=suConsumoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(suConsumoDTO == null ? null : Entity.json(suConsumoDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (List<CuentaAdministradorSubsidioDTO>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidioDTO> getResult() {
		return result;
	}

 
  
  	public void setSuConsumoDTO (SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO){
 		this.suConsumoDTO=suConsumoDTO;
 	}
 	
 	public SubsidioPerdidaDerechoInformesConsumoDTO getSuConsumoDTO (){
 		return suConsumoDTO;
 	}
  
}