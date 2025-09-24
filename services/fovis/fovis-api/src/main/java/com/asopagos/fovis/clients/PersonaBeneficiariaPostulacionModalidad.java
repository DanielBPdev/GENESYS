package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/personaBeneficiariaPostulacionModalidad
 */
public class PersonaBeneficiariaPostulacionModalidad extends ServiceClient {
 
  
  	private ModalidadEnum modalidad;
  	private TipoIdentificacionEnum tipoIdentificacionPersona;
  	private String numeroIdentificacionPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public PersonaBeneficiariaPostulacionModalidad (ModalidadEnum modalidad,TipoIdentificacionEnum tipoIdentificacionPersona,String numeroIdentificacionPersona){
 		super();
		this.modalidad=modalidad;
		this.tipoIdentificacionPersona=tipoIdentificacionPersona;
		this.numeroIdentificacionPersona=numeroIdentificacionPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("modalidad", modalidad)
						.queryParam("tipoIdentificacionPersona", tipoIdentificacionPersona)
						.queryParam("numeroIdentificacionPersona", numeroIdentificacionPersona)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setModalidad (ModalidadEnum modalidad){
 		this.modalidad=modalidad;
 	}
 	
 	public ModalidadEnum getModalidad (){
 		return modalidad;
 	}
  	public void setTipoIdentificacionPersona (TipoIdentificacionEnum tipoIdentificacionPersona){
 		this.tipoIdentificacionPersona=tipoIdentificacionPersona;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionPersona (){
 		return tipoIdentificacionPersona;
 	}
  	public void setNumeroIdentificacionPersona (String numeroIdentificacionPersona){
 		this.numeroIdentificacionPersona=numeroIdentificacionPersona;
 	}
 	
 	public String getNumeroIdentificacionPersona (){
 		return numeroIdentificacionPersona;
 	}
  
}