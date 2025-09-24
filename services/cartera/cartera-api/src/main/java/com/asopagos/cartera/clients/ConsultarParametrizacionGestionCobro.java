package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import java.lang.Object;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{tipoParametrizacion}/consultarParametrizacionGestionCobro
 */
public class ConsultarParametrizacionGestionCobro extends ServiceClient {
 
  	private TipoParametrizacionGestionCobroEnum tipoParametrizacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Object> result;
  
 	public ConsultarParametrizacionGestionCobro (TipoParametrizacionGestionCobroEnum tipoParametrizacion){
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
		this.result = (List<Object>) response.readEntity(new GenericType<List<Object>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Object> getResult() {
		return result;
	}

 	public void setTipoParametrizacion (TipoParametrizacionGestionCobroEnum tipoParametrizacion){
 		this.tipoParametrizacion=tipoParametrizacion;
 	}
 	
 	public TipoParametrizacionGestionCobroEnum getTipoParametrizacion (){
 		return tipoParametrizacion;
 	}
  
  
}