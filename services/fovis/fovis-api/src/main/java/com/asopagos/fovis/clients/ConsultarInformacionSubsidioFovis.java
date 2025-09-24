package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.fovis.InformacionSubsidioFOVISDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarInformacionSubsidioFovis
 */
public class ConsultarInformacionSubsidioFovis extends ServiceClient {
 
  
  	private String numeroRad;
  	private String numeroID;
  	private TipoIdentificacionEnum tipoID;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionSubsidioFOVISDTO result;
  
 	public ConsultarInformacionSubsidioFovis (String numeroRad,String numeroID,TipoIdentificacionEnum tipoID){
 		super();
		this.numeroRad=numeroRad;
		this.numeroID=numeroID;
		this.tipoID=tipoID;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRad", numeroRad)
						.queryParam("numeroID", numeroID)
						.queryParam("tipoID", tipoID)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionSubsidioFOVISDTO) response.readEntity(InformacionSubsidioFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionSubsidioFOVISDTO getResult() {
		return result;
	}

 
  	public void setNumeroRad (String numeroRad){
 		this.numeroRad=numeroRad;
 	}
 	
 	public String getNumeroRad (){
 		return numeroRad;
 	}
  	public void setNumeroID (String numeroID){
 		this.numeroID=numeroID;
 	}
 	
 	public String getNumeroID (){
 		return numeroID;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  
}