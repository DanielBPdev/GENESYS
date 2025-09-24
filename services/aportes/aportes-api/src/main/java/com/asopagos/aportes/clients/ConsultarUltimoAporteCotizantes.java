package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/aportes/consultarUltimoAporteCotizante
 */
public class ConsultarUltimoAporteCotizantes extends ServiceClient {
 
  
  	private String numeroIdentificacionAportante;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosCotizanteIntegracionDTO result;
  
 	public ConsultarUltimoAporteCotizantes (String numeroIdentificacionAportante,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacionAportante", numeroIdentificacionAportante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatosCotizanteIntegracionDTO) response.readEntity(DatosCotizanteIntegracionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosCotizanteIntegracionDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacionAportante (String numeroIdentificacionAportante){
 		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
 	}
 	
 	public String getNumeroIdentificacionAportante (){
 		return numeroIdentificacionAportante;
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