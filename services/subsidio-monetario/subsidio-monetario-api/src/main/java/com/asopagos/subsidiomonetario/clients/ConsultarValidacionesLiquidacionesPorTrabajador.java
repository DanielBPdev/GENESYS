package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.ConsultaValidacionesLiquidacionSubsidioMonetarioDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarValidacionesLiquidacionesPorTrabajador
 */
public class ConsultarValidacionesLiquidacionesPorTrabajador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionEmpl;
  	private String numeroRadicacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numeroIdentificacionEmpl;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultaValidacionesLiquidacionSubsidioMonetarioDTO result;
  
 	public ConsultarValidacionesLiquidacionesPorTrabajador (TipoIdentificacionEnum tipoIdentificacionEmpl,String numeroRadicacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacionEmpl){
 		super();
		this.tipoIdentificacionEmpl=tipoIdentificacionEmpl;
		this.numeroRadicacion=numeroRadicacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacionEmpl=numeroIdentificacionEmpl;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionEmpl", tipoIdentificacionEmpl)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacionEmpl", numeroIdentificacionEmpl)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConsultaValidacionesLiquidacionSubsidioMonetarioDTO) response.readEntity(ConsultaValidacionesLiquidacionSubsidioMonetarioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultaValidacionesLiquidacionSubsidioMonetarioDTO getResult() {
		return result;
	}

 
  	public void setTipoIdentificacionEmpl (TipoIdentificacionEnum tipoIdentificacionEmpl){
 		this.tipoIdentificacionEmpl=tipoIdentificacionEmpl;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionEmpl (){
 		return tipoIdentificacionEmpl;
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
  	public void setNumeroIdentificacionEmpl (String numeroIdentificacionEmpl){
 		this.numeroIdentificacionEmpl=numeroIdentificacionEmpl;
 	}
 	
 	public String getNumeroIdentificacionEmpl (){
 		return numeroIdentificacionEmpl;
 	}
  
}