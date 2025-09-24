package com.asopagos.historicos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import com.asopagos.historicos.dto.PersonaComoAfiPpalGrupoFamiliarDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/obtenerGruposFamiliaresAfiPrincipal
 */
public class ObtenerGruposFamiliaresAfiPrincipal extends ServiceClient {
 
  
  	private String tipoIdentificacion;
  	private String numeroIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaComoAfiPpalGrupoFamiliarDTO> result;
  
 	public ObtenerGruposFamiliaresAfiPrincipal (String tipoIdentificacion,String numeroIdentificacion){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PersonaComoAfiPpalGrupoFamiliarDTO>) response.readEntity(new GenericType<List<PersonaComoAfiPpalGrupoFamiliarDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PersonaComoAfiPpalGrupoFamiliarDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdentificacion (String tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public String getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  
}