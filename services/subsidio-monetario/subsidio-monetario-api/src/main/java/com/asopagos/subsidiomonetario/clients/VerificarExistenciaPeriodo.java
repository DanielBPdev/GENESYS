package com.asopagos.subsidiomonetario.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/verificarExistenciaPeriodo
 */
public class VerificarExistenciaPeriodo extends ServiceClient { 
    	private List<ValorPeriodoDTO> periodos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public VerificarExistenciaPeriodo (List<ValorPeriodoDTO> periodos){
 		super();
		this.periodos=periodos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(periodos == null ? null : Entity.json(periodos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setPeriodos (List<ValorPeriodoDTO> periodos){
 		this.periodos=periodos;
 	}
 	
 	public List<ValorPeriodoDTO> getPeriodos (){
 		return periodos;
 	}
  
}