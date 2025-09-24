package com.asopagos.personas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/consultarPersonaRazonSocial
 */
public class ConsultarPersonaRazonSocial extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String razonSocial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaDTO> result;
  
 	public ConsultarPersonaRazonSocial (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String razonSocial){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.razonSocial=razonSocial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("razonSocial", razonSocial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PersonaDTO>) response.readEntity(new GenericType<List<PersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PersonaDTO> getResult() {
		return result;
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
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  
}