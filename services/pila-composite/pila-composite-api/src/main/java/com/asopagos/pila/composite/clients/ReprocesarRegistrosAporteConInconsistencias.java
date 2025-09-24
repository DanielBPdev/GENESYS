package com.asopagos.pila.composite.clients;

import java.util.List;
import java.lang.String;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/reprocesarRegistrosAporteConInconsistencias
 */
public class ReprocesarRegistrosAporteConInconsistencias extends ServiceClient { 
   	private String fase;
   	private List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO;
  
  
 	public ReprocesarRegistrosAporteConInconsistencias (String fase,List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO){
 		super();
		this.fase=fase;
		this.lstInconsistenciaRegistroAporteDTO=lstInconsistenciaRegistroAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fase", fase)
			.request(MediaType.APPLICATION_JSON)
			.post(lstInconsistenciaRegistroAporteDTO == null ? null : Entity.json(lstInconsistenciaRegistroAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFase (String fase){
 		this.fase=fase;
 	}
 	
 	public String getFase (){
 		return fase;
 	}
  
  	public void setLstInconsistenciaRegistroAporteDTO (List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO){
 		this.lstInconsistenciaRegistroAporteDTO=lstInconsistenciaRegistroAporteDTO;
 	}
 	
 	public List<InconsistenciaRegistroAporteDTO> getLstInconsistenciaRegistroAporteDTO (){
 		return lstInconsistenciaRegistroAporteDTO;
 	}
  
}