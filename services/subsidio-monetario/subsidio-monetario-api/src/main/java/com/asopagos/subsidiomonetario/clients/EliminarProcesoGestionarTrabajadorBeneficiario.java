package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/subsidioMonetario/eliminarProceso/gestionarTrabajadorBeneficiario
 */
public class EliminarProcesoGestionarTrabajadorBeneficiario extends ServiceClient {
 
  
  	private Boolean cumple;
  	private String numeroRadicacion;
  	private Long condicionPersona;
  	private TipoLiquidacionEspecificaEnum tipoLiquidacion;
  	private Boolean esTrabajadorFallecido;
  
  
 	public EliminarProcesoGestionarTrabajadorBeneficiario (Boolean cumple,String numeroRadicacion,Long condicionPersona,TipoLiquidacionEspecificaEnum tipoLiquidacion,Boolean esTrabajadorFallecido){
 		super();
		this.cumple=cumple;
		this.numeroRadicacion=numeroRadicacion;
		this.condicionPersona=condicionPersona;
		this.tipoLiquidacion=tipoLiquidacion;
		this.esTrabajadorFallecido=esTrabajadorFallecido;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("cumple", cumple)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("condicionPersona", condicionPersona)
						.queryParam("tipoLiquidacion", tipoLiquidacion)
						.queryParam("esTrabajadorFallecido", esTrabajadorFallecido)
						.request(MediaType.APPLICATION_JSON).delete();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setCumple (Boolean cumple){
 		this.cumple=cumple;
 	}
 	
 	public Boolean getCumple (){
 		return cumple;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setCondicionPersona (Long condicionPersona){
 		this.condicionPersona=condicionPersona;
 	}
 	
 	public Long getCondicionPersona (){
 		return condicionPersona;
 	}
  	public void setTipoLiquidacion (TipoLiquidacionEspecificaEnum tipoLiquidacion){
 		this.tipoLiquidacion=tipoLiquidacion;
 	}
 	
 	public TipoLiquidacionEspecificaEnum getTipoLiquidacion (){
 		return tipoLiquidacion;
 	}
  	public void setEsTrabajadorFallecido (Boolean esTrabajadorFallecido){
 		this.esTrabajadorFallecido=esTrabajadorFallecido;
 	}
 	
 	public Boolean getEsTrabajadorFallecido (){
 		return esTrabajadorFallecido;
 	}
  
}