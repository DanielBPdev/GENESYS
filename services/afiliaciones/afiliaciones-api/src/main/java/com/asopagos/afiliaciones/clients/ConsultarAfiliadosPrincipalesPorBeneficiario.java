package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.dto.InfoAfiliadosPrincipalesOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/consultarAfiliadosPrincipalesPorBeneficiario
 */
public class ConsultarAfiliadosPrincipalesPorBeneficiario extends ServiceClient {
 
  
  	private String identificacion;
  	private TipoIdentificacionEnum tipoID;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InfoAfiliadosPrincipalesOutDTO> result;
  
 	public ConsultarAfiliadosPrincipalesPorBeneficiario (String identificacion,TipoIdentificacionEnum tipoID){
 		super();
		this.identificacion=identificacion;
		this.tipoID=tipoID;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificacion", identificacion)
						.queryParam("tipoID", tipoID)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InfoAfiliadosPrincipalesOutDTO>) response.readEntity(new GenericType<List<InfoAfiliadosPrincipalesOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InfoAfiliadosPrincipalesOutDTO> getResult() {
		return result;
	}

 
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}

}