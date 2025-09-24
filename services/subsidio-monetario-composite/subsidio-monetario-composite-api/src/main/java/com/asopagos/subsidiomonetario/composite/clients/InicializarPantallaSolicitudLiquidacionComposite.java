package com.asopagos.subsidiomonetario.composite.clients;

import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetarioComposite/inicializarPantallaSolicitudLiquidacionComposite
 */
public class InicializarPantallaSolicitudLiquidacionComposite extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IniciarSolicitudLiquidacionMasivaDTO result;
  
 	public InicializarPantallaSolicitudLiquidacionComposite (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IniciarSolicitudLiquidacionMasivaDTO) response.readEntity(IniciarSolicitudLiquidacionMasivaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public IniciarSolicitudLiquidacionMasivaDTO getResult() {
		return result;
	}

 
  
}