package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.pagos.InformacionAdminSubsidioDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/subsidio/obtenerInfoAdministradorSubsidio
 */
public class ObtenerInfoAdministradorSubsidio extends ServiceClient {
 
  
  	private String numeroIdAdminSubsidio;
  	private TipoIdentificacionEnum tipoIdAdminSubsidio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionAdminSubsidioDTO result;
  
 	public ObtenerInfoAdministradorSubsidio (String numeroIdAdminSubsidio,TipoIdentificacionEnum tipoIdAdminSubsidio){
 		super();
		this.numeroIdAdminSubsidio=numeroIdAdminSubsidio;
		this.tipoIdAdminSubsidio=tipoIdAdminSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdAdminSubsidio", numeroIdAdminSubsidio)
						.queryParam("tipoIdAdminSubsidio", tipoIdAdminSubsidio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionAdminSubsidioDTO) response.readEntity(InformacionAdminSubsidioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionAdminSubsidioDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdAdminSubsidio (String numeroIdAdminSubsidio){
 		this.numeroIdAdminSubsidio=numeroIdAdminSubsidio;
 	}
 	
 	public String getNumeroIdAdminSubsidio (){
 		return numeroIdAdminSubsidio;
 	}
  	public void setTipoIdAdminSubsidio (TipoIdentificacionEnum tipoIdAdminSubsidio){
 		this.tipoIdAdminSubsidio=tipoIdAdminSubsidio;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAdminSubsidio (){
 		return tipoIdAdminSubsidio;
 	}
  
}