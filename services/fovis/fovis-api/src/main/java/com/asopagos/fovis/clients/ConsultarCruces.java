package com.asopagos.fovis.clients;

import com.asopagos.dto.CruceDetalleDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCruces
 */
public class ConsultarCruces extends ServiceClient {
 
  
  	private TipoCruceEnum tipoCruce;
  	private Long idSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDetalleDTO> result;
  
 	public ConsultarCruces (TipoCruceEnum tipoCruce,Long idSolicitud){
 		super();
		this.tipoCruce=tipoCruce;
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoCruce", tipoCruce)
						.queryParam("idSolicitud", idSolicitud)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CruceDetalleDTO>) response.readEntity(new GenericType<List<CruceDetalleDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CruceDetalleDTO> getResult() {
		return result;
	}

 
  	public void setTipoCruce (TipoCruceEnum tipoCruce){
 		this.tipoCruce=tipoCruce;
 	}
 	
 	public TipoCruceEnum getTipoCruce (){
 		return tipoCruce;
 	}
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
}