package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.InfoPadresBiologicosOutDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/obtenerPadresBiologicosPersona
 */
public class ObtenerPadresBiologicosPersona extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoID;
  	private String identificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoPadresBiologicosOutDTO result;
  
 	public ObtenerPadresBiologicosPersona (TipoIdentificacionEnum tipoID,String identificacion){
 		super();
		this.tipoID=tipoID;
		this.identificacion=identificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoID", tipoID)
						.queryParam("identificacion", identificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoPadresBiologicosOutDTO) response.readEntity(InfoPadresBiologicosOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoPadresBiologicosOutDTO getResult() {
		return result;
	}

 
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  
}