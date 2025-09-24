package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{accionCobro}/consultarEtiquetaPorAccion
 */
public class ConsultarEtiquetaPorAccion extends ServiceClient {
 
  	private TipoAccionCobroEnum accionCobro;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EtiquetaPlantillaComunicadoEnum result;
  
 	public ConsultarEtiquetaPorAccion (TipoAccionCobroEnum accionCobro){
 		super();
		this.accionCobro=accionCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("accionCobro", accionCobro)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EtiquetaPlantillaComunicadoEnum) response.readEntity(EtiquetaPlantillaComunicadoEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EtiquetaPlantillaComunicadoEnum getResult() {
		return result;
	}

 	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
  
}