package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicacion}/consultarAportantesPrimeraRemision
 */
public class ConsultarAportantesPrimeraRemision extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private List<Long> municipios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroRemisionAportantesDTO result;
  
 	public ConsultarAportantesPrimeraRemision (String numeroRadicacion,List<Long> municipios){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.municipios=municipios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("municipios", municipios.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroRemisionAportantesDTO) response.readEntity(RegistroRemisionAportantesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroRemisionAportantesDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setMunicipios (List<Long> municipios){
 		this.municipios=municipios;
 	}
 	
 	public List<Long> getMunicipios (){
 		return municipios;
 	}
  
}