package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarDatosSolicitudCorreccion
 */
public class ConsultarDatosSolicitudCorreccion extends ServiceClient { 
    	private List<Long> idsAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CorreccionVistasDTO> result;
  
 	public ConsultarDatosSolicitudCorreccion (List<Long> idsAporteGeneral){
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
		result = (List<CorreccionVistasDTO>) response.readEntity(new GenericType<List<CorreccionVistasDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CorreccionVistasDTO> getResult() {
		return result;
	}

 
  
  	public void setIdsAporteGeneral (List<Long> idsAporteGeneral){
 		this.idsAporteGeneral=idsAporteGeneral;
 	}
 	
 	public List<Long> getIdsAporteGeneral (){
 		return idsAporteGeneral;
 	}
  
}