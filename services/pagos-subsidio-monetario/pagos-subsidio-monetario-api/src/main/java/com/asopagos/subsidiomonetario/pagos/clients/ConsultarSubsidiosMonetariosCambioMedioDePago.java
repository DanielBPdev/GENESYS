package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioConsultaCambioPagosDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/consultarSubsidiosMonetarios/CambioMedioDePago
 */
public class ConsultarSubsidiosMonetariosCambioMedioDePago extends ServiceClient { 
    	private SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidioDTO> result;
  
 	public ConsultarSubsidiosMonetariosCambioMedioDePago (SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO){
 		super();
		this.cambioPagosDTO=cambioPagosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(cambioPagosDTO == null ? null : Entity.json(cambioPagosDTO));
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

 
  
  	public void setCambioPagosDTO (SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO){
 		this.cambioPagosDTO=cambioPagosDTO;
 	}
 	
 	public SubsidioMonetarioConsultaCambioPagosDTO getCambioPagosDTO (){
 		return cambioPagosDTO;
 	}
  
}