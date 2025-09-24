package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/consultarFactorCuotaDiscapacidadPeriodos
 */
public class ConsultarFactorCuotaDiscapacidadPeriodos extends ServiceClient { 
    	private List<Long> periodos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ValorPeriodoDTO> result;
  
 	public ConsultarFactorCuotaDiscapacidadPeriodos (List<Long> periodos){
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
		result = (List<ValorPeriodoDTO>) response.readEntity(new GenericType<List<ValorPeriodoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ValorPeriodoDTO> getResult() {
		return result;
	}

 
  
  	public void setPeriodos (List<Long> periodos){
 		this.periodos=periodos;
 	}
 	
 	public List<Long> getPeriodos (){
 		return periodos;
 	}
  
}