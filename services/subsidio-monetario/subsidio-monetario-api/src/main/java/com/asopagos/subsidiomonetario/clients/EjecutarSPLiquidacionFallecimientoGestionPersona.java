package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarSPLiquidacionFallecimientoGestionPersona
 */
public class EjecutarSPLiquidacionFallecimientoGestionPersona extends ServiceClient { 
   	private Long periodo;
  	private String numeroRadicacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Boolean beneficiarioFallecido;
   
  
 	public EjecutarSPLiquidacionFallecimientoGestionPersona (Long periodo,String numeroRadicacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Boolean beneficiarioFallecido){
 		super();
		this.periodo=periodo;
		this.numeroRadicacion=numeroRadicacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.beneficiarioFallecido=beneficiarioFallecido;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("periodo", periodo)
			.queryParam("numeroRadicacion", numeroRadicacion)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("beneficiarioFallecido", beneficiarioFallecido)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setBeneficiarioFallecido (Boolean beneficiarioFallecido){
 		this.beneficiarioFallecido=beneficiarioFallecido;
 	}
 	
 	public Boolean getBeneficiarioFallecido (){
 		return beneficiarioFallecido;
 	}
  
  
}