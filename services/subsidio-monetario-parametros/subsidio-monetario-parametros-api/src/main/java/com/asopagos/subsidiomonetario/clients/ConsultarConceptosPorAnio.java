package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Integer;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarConceptosPorAnio
 */
public class ConsultarConceptosPorAnio extends ServiceClient {
 
  
  	private Integer anio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionLiquidacionSubsidioModeloDTO> result;
  
 	public ConsultarConceptosPorAnio (Integer anio){
 		super();
		this.anio=anio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("anio", anio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ParametrizacionLiquidacionSubsidioModeloDTO>) response.readEntity(new GenericType<List<ParametrizacionLiquidacionSubsidioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ParametrizacionLiquidacionSubsidioModeloDTO> getResult() {
		return result;
	}

 
  	public void setAnio (Integer anio){
 		this.anio=anio;
 	}
 	
 	public Integer getAnio (){
 		return anio;
 	}
  
}