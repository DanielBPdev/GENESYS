package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/obtenerTrazabilidadSubsidioEspecifico
 */
public class ObtenerTrazabilidadSubsidioEspecifico extends ServiceClient {
 
  
  	private Long identificadorLiquidacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TrazabilidadSubsidioEspecificoDTO> result;
  
 	public ObtenerTrazabilidadSubsidioEspecifico (Long identificadorLiquidacion){
 		super();
		this.identificadorLiquidacion=identificadorLiquidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificadorLiquidacion", identificadorLiquidacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TrazabilidadSubsidioEspecificoDTO>) response.readEntity(new GenericType<List<TrazabilidadSubsidioEspecificoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TrazabilidadSubsidioEspecificoDTO> getResult() {
		return result;
	}

 
  	public void setIdentificadorLiquidacion (Long identificadorLiquidacion){
 		this.identificadorLiquidacion=identificadorLiquidacion;
 	}
 	
 	public Long getIdentificadorLiquidacion (){
 		return identificadorLiquidacion;
 	}
  
}