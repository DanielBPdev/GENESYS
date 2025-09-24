package com.asopagos.aportes.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.DatosConsultaSolicitudesAporDevCorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarSolicitudesCorreccion
 */
public class ConsultarSolicitudesCorreccion extends ServiceClient { 
    	private List<Long> idsAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosConsultaSolicitudesAporDevCorDTO result;
  
 	public ConsultarSolicitudesCorreccion (List<Long> idsAporteGeneral){
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
		result = (DatosConsultaSolicitudesAporDevCorDTO) response.readEntity(DatosConsultaSolicitudesAporDevCorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatosConsultaSolicitudesAporDevCorDTO getResult() {
		return result;
	}

 
  
  	public void setIdsAporteGeneral (List<Long> idsAporteGeneral){
 		this.idsAporteGeneral=idsAporteGeneral;
 	}
 	
 	public List<Long> getIdsAporteGeneral (){
 		return idsAporteGeneral;
 	}
  
}