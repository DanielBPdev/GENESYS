package com.asopagos.novedades.clients;

import com.asopagos.novedades.dto.DatosNovedadVista360DTO;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarNovedadesPersonaVista360
 */
public class ConsultarNovedadesPersonaVista360 extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Boolean esBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosNovedadVista360DTO result;
  
 	public ConsultarNovedadesPersonaVista360 (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Boolean esBeneficiario){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.esBeneficiario=esBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("esBeneficiario", esBeneficiario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatosNovedadVista360DTO) response.readEntity(DatosNovedadVista360DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosNovedadVista360DTO getResult() {
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
  	public void setEsBeneficiario (Boolean esBeneficiario){
 		this.esBeneficiario=esBeneficiario;
 	}
 	
 	public Boolean getEsBeneficiario (){
 		return esBeneficiario;
 	}
  
}