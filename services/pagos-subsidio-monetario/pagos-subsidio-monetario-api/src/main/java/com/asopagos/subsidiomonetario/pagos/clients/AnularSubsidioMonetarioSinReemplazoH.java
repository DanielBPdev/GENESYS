package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.SubsidioAnulacionDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/anularSubsidioMonetarioSinReemplazoH
 */
public class AnularSubsidioMonetarioSinReemplazoH extends ServiceClient { 
   	private Boolean resultadoValidacion;
   	private SubsidioAnulacionDTO subsidioAnulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public AnularSubsidioMonetarioSinReemplazoH (Boolean resultadoValidacion,SubsidioAnulacionDTO subsidioAnulacionDTO){
 		super();
		this.resultadoValidacion=resultadoValidacion;
		this.subsidioAnulacionDTO=subsidioAnulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("resultadoValidacion", resultadoValidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(subsidioAnulacionDTO == null ? null : Entity.json(subsidioAnulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  	public void setResultadoValidacion (Boolean resultadoValidacion){
 		this.resultadoValidacion=resultadoValidacion;
 	}
 	
 	public Boolean getResultadoValidacion (){
 		return resultadoValidacion;
 	}
  
  	public void setSubsidioAnulacionDTO (SubsidioAnulacionDTO subsidioAnulacionDTO){
 		this.subsidioAnulacionDTO=subsidioAnulacionDTO;
 	}
 	
 	public SubsidioAnulacionDTO getSubsidioAnulacionDTO (){
 		return subsidioAnulacionDTO;
 	}
  
}