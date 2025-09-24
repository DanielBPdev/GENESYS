package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionCarteraModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionCarteraEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{tipoParametrizacion}/consultarParametrizacionCartera
 */
public class ConsultarParametrizacionCartera extends ServiceClient {
 
  	private TipoParametrizacionCarteraEnum tipoParametrizacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionCarteraModeloDTO result;
  
 	public ConsultarParametrizacionCartera (TipoParametrizacionCarteraEnum tipoParametrizacion){
 		super();
		this.tipoParametrizacion=tipoParametrizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("tipoParametrizacion", tipoParametrizacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionCarteraModeloDTO) response.readEntity(ParametrizacionCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionCarteraModeloDTO getResult() {
		return result;
	}

 	public void setTipoParametrizacion (TipoParametrizacionCarteraEnum tipoParametrizacion){
 		this.tipoParametrizacion=tipoParametrizacion;
 	}
 	
 	public TipoParametrizacionCarteraEnum getTipoParametrizacion (){
 		return tipoParametrizacion;
 	}
  
  
}