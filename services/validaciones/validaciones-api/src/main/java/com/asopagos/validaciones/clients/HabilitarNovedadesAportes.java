package com.asopagos.validaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/validacionesAPI/{tipoCotizante}/habilitarNovedadesAportes
 */
public class HabilitarNovedadesAportes extends ServiceClient {
 
  	private TipoAfiliadoEnum tipoCotizante;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoTransaccionEnum> result;
  
 	public HabilitarNovedadesAportes (TipoAfiliadoEnum tipoCotizante){
 		super();
		this.tipoCotizante=tipoCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("tipoCotizante", tipoCotizante)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TipoTransaccionEnum>) response.readEntity(new GenericType<List<TipoTransaccionEnum>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TipoTransaccionEnum> getResult() {
		return result;
	}

 	public void setTipoCotizante (TipoAfiliadoEnum tipoCotizante){
 		this.tipoCotizante=tipoCotizante;
 	}
 	
 	public TipoAfiliadoEnum getTipoCotizante (){
 		return tipoCotizante;
 	}
  
  
}