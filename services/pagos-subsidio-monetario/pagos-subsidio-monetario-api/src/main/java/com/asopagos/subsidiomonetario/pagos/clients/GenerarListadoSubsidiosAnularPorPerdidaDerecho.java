package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/generarListado/subsidiosAnular/porPerdidaDeDerecho
 */
public class GenerarListadoSubsidiosAnularPorPerdidaDerecho extends ServiceClient { 
    	private SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SubsidiosConsultaAnularPerdidaDerechoDTO> result;
  
 	public GenerarListadoSubsidiosAnularPorPerdidaDerecho (SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO){
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
		result = (List<SubsidiosConsultaAnularPerdidaDerechoDTO>) response.readEntity(new GenericType<List<SubsidiosConsultaAnularPerdidaDerechoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SubsidiosConsultaAnularPerdidaDerechoDTO> getResult() {
		return result;
	}

 
  
  	public void setSuConsumoDTO (SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO){
 		this.suConsumoDTO=suConsumoDTO;
 	}
 	
 	public SubsidioPerdidaDerechoInformesConsumoDTO getSuConsumoDTO (){
 		return suConsumoDTO;
 	}
  
}