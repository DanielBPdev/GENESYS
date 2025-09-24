package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.InfoCiudadOutDTO;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerInfoCiudad
 */
public class ObtenerInfoCiudad extends ServiceClient {
 
  
  	private Integer departamentoID;
  	private Integer ciudadID;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoCiudadOutDTO result;
  
 	public ObtenerInfoCiudad (Integer departamentoID,Integer ciudadID){
 		super();
		this.departamentoID=departamentoID;
		this.ciudadID=ciudadID;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("departamentoID", departamentoID)
						.queryParam("ciudadID", ciudadID)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoCiudadOutDTO) response.readEntity(InfoCiudadOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoCiudadOutDTO getResult() {
		return result;
	}

 
  	public void setDepartamentoID (Integer departamentoID){
 		this.departamentoID=departamentoID;
 	}
 	
 	public Integer getDepartamentoID (){
 		return departamentoID;
 	}
  	public void setCiudadID (Integer ciudadID){
 		this.ciudadID=ciudadID;
 	}
 	
 	public Integer getCiudadID (){
 		return ciudadID;
 	}
  
}