package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultar/validacionesTipoProceso
 */
public class ConsultarValidacionesTipoProceso extends ServiceClient {
 
  
  	private TipoValidacionLiquidacionEspecificaEnum tipoProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConjuntoValidacionSubsidioEnum> result;
  
 	public ConsultarValidacionesTipoProceso (TipoValidacionLiquidacionEspecificaEnum tipoProceso){
 		super();
		this.tipoProceso=tipoProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoProceso", tipoProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConjuntoValidacionSubsidioEnum>) response.readEntity(new GenericType<List<ConjuntoValidacionSubsidioEnum>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConjuntoValidacionSubsidioEnum> getResult() {
		return result;
	}

 
  	public void setTipoProceso (TipoValidacionLiquidacionEspecificaEnum tipoProceso){
 		this.tipoProceso=tipoProceso;
 	}
 	
 	public TipoValidacionLiquidacionEspecificaEnum getTipoProceso (){
 		return tipoProceso;
 	}
  
}