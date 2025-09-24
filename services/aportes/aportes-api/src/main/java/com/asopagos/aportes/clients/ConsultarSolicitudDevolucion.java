package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.DevolucionVistasDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarSolicitudDevolucion
 */
public class ConsultarSolicitudDevolucion extends ServiceClient { 
    	private List<Long> idsAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DevolucionVistasDTO> result;
  
 	public ConsultarSolicitudDevolucion (List<Long> idsAporteGeneral){
 		super();
		this.idsAporteGeneral=idsAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsAporteGeneral == null ? null : Entity.json(idsAporteGeneral));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DevolucionVistasDTO>) response.readEntity(new GenericType<List<DevolucionVistasDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DevolucionVistasDTO> getResult() {
		return result;
	}

 
  
  	public void setIdsAporteGeneral (List<Long> idsAporteGeneral){
 		this.idsAporteGeneral=idsAporteGeneral;
 	}
 	
 	public List<Long> getIdsAporteGeneral (){
 		return idsAporteGeneral;
 	}
  
}