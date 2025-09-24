package com.asopagos.fovis.clients;

import com.asopagos.dto.CruceDetalleDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCrucesPorProcesoAsincrono
 */
public class ConsultarCrucesPorProcesoAsincrono extends ServiceClient {
 
  
  	private Long idProcesoAsincrono;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDetalleDTO> result;
  
 	public ConsultarCrucesPorProcesoAsincrono (Long idProcesoAsincrono){
 		super();
		this.idProcesoAsincrono=idProcesoAsincrono;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProcesoAsincrono", idProcesoAsincrono)
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

 
  	public void setIdProcesoAsincrono (Long idProcesoAsincrono){
 		this.idProcesoAsincrono=idProcesoAsincrono;
 	}
 	
 	public Long getIdProcesoAsincrono (){
 		return idProcesoAsincrono;
 	}
  
}