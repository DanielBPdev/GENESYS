package com.asopagos.subsidiomonetario.clients;

import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarSPLiquidacionEspecifica
 */
public class EjecutarSPLiquidacionEspecifica extends ServiceClient { 
   	private TipoLiquidacionEspecificaEnum tipoLiquidacion;
  	private String numeroRadicado;
   
  
 	public EjecutarSPLiquidacionEspecifica (TipoLiquidacionEspecificaEnum tipoLiquidacion,String numeroRadicado){
 		super();
		this.tipoLiquidacion=tipoLiquidacion;
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoLiquidacion", tipoLiquidacion)
			.queryParam("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTipoLiquidacion (TipoLiquidacionEspecificaEnum tipoLiquidacion){
 		this.tipoLiquidacion=tipoLiquidacion;
 	}
 	
 	public TipoLiquidacionEspecificaEnum getTipoLiquidacion (){
 		return tipoLiquidacion;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
}