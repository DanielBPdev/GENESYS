package com.asopagos.subsidiomonetario.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoValidacionFallecimientoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetarioComposite/consultar/validacionesTrabajador/gestionFallecimiento/{numeroRadicacion}/{condicionPersona}
 */
public class ConsultarValidacionesTrabajadorGestionFallecimiento extends ServiceClient {
 
  	private String numeroRadicacion;
  	private Long condicionPersona;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoValidacionFallecimientoDTO> result;
  
 	public ConsultarValidacionesTrabajadorGestionFallecimiento (String numeroRadicacion,Long condicionPersona){
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
		this.result = (List<ResultadoValidacionFallecimientoDTO>) response.readEntity(new GenericType<List<ResultadoValidacionFallecimientoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoValidacionFallecimientoDTO> getResult() {
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