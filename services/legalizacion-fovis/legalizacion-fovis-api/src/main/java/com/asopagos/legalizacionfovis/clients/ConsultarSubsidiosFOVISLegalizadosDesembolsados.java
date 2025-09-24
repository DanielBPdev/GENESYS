package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.fovis.ConsultarSubsidiosFOVISLegalizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/consultarSubsidiosFOVISLegalizadosDesembolsados
 */
public class ConsultarSubsidiosFOVISLegalizadosDesembolsados extends ServiceClient { 
    	private ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultarSubsidiosFOVISLegalizacionDTO> result;
  
 	public ConsultarSubsidiosFOVISLegalizadosDesembolsados (ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO){
 		super();
		this.consultarSubsidiosFOVISLegalizacionDTO=consultarSubsidiosFOVISLegalizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultarSubsidiosFOVISLegalizacionDTO == null ? null : Entity.json(consultarSubsidiosFOVISLegalizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ConsultarSubsidiosFOVISLegalizacionDTO>) response.readEntity(new GenericType<List<ConsultarSubsidiosFOVISLegalizacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ConsultarSubsidiosFOVISLegalizacionDTO> getResult() {
		return result;
	}

 
  
  	public void setConsultarSubsidiosFOVISLegalizacionDTO (ConsultarSubsidiosFOVISLegalizacionDTO consultarSubsidiosFOVISLegalizacionDTO){
 		this.consultarSubsidiosFOVISLegalizacionDTO=consultarSubsidiosFOVISLegalizacionDTO;
 	}
 	
 	public ConsultarSubsidiosFOVISLegalizacionDTO getConsultarSubsidiosFOVISLegalizacionDTO (){
 		return consultarSubsidiosFOVISLegalizacionDTO;
 	}
  
}