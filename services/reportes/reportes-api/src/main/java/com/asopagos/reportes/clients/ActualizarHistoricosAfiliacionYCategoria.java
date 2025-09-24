package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/actualizarHistoricosAfiliacionYCategoria
 */
public class ActualizarHistoricosAfiliacionYCategoria extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numeroIdAfiliado;
  
  
 	public ActualizarHistoricosAfiliacionYCategoria (TipoIdentificacionEnum tipoIdAfiliado,TipoAfiliadoEnum tipoAfiliado,String numeroIdAfiliado){
 		super();
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.tipoAfiliado=tipoAfiliado;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTipoIdAfiliado (TipoIdentificacionEnum tipoIdAfiliado){
 		this.tipoIdAfiliado=tipoIdAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAfiliado (){
 		return tipoIdAfiliado;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}