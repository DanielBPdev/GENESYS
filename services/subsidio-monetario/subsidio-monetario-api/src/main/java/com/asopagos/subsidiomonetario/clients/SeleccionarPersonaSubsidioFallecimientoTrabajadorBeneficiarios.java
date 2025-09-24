package com.asopagos.subsidiomonetario.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/seleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios
 */
public class SeleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios extends ServiceClient {
 
  
  	private TipoLiquidacionEspecificaEnum tipoLiquidacion;
  	private String numeroLiquidacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PersonaFallecidaTrabajadorDTO result;
  
 	public SeleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios (TipoLiquidacionEspecificaEnum tipoLiquidacion,String numeroLiquidacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.tipoLiquidacion=tipoLiquidacion;
		this.numeroLiquidacion=numeroLiquidacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoLiquidacion", tipoLiquidacion)
						.queryParam("numeroLiquidacion", numeroLiquidacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (PersonaFallecidaTrabajadorDTO) response.readEntity(PersonaFallecidaTrabajadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public PersonaFallecidaTrabajadorDTO getResult() {
		return result;
	}

 
  	public void setTipoLiquidacion (TipoLiquidacionEspecificaEnum tipoLiquidacion){
 		this.tipoLiquidacion=tipoLiquidacion;
 	}
 	
 	public TipoLiquidacionEspecificaEnum getTipoLiquidacion (){
 		return tipoLiquidacion;
 	}
  	public void setNumeroLiquidacion (String numeroLiquidacion){
 		this.numeroLiquidacion=numeroLiquidacion;
 	}
 	
 	public String getNumeroLiquidacion (){
 		return numeroLiquidacion;
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
  
}