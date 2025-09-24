package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultar/validacionFallida/personaFallecimiento/{numeroRadicacion}/{condicionPersona}
 */
public class ConsultarValidacionFallidaPersonaFallecimiento extends ServiceClient {
 
  	private String numeroRadicacion;
  	private Long condicionPersona;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConjuntoValidacionSubsidioEnum result;
  
 	public ConsultarValidacionFallidaPersonaFallecimiento (String numeroRadicacion,Long condicionPersona){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.condicionPersona=condicionPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
						.resolveTemplate("condicionPersona", condicionPersona)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConjuntoValidacionSubsidioEnum) response.readEntity(ConjuntoValidacionSubsidioEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConjuntoValidacionSubsidioEnum getResult() {
		return result;
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
  
  
}