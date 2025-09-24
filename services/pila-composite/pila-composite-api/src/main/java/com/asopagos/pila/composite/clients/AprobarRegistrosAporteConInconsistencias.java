package com.asopagos.pila.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/aprobarRegistrosAporteConInconsistencias
 */
public class AprobarRegistrosAporteConInconsistencias extends ServiceClient { 
    	private List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InconsistenciaRegistroAporteDTO> result;
  
 	public AprobarRegistrosAporteConInconsistencias (List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO){
 		super();
		this.lstInconsistenciaRegistroAporteDTO=lstInconsistenciaRegistroAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lstInconsistenciaRegistroAporteDTO == null ? null : Entity.json(lstInconsistenciaRegistroAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<InconsistenciaRegistroAporteDTO>) response.readEntity(new GenericType<List<InconsistenciaRegistroAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<InconsistenciaRegistroAporteDTO> getResult() {
		return result;
	}

 
  
  	public void setLstInconsistenciaRegistroAporteDTO (List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO){
 		this.lstInconsistenciaRegistroAporteDTO=lstInconsistenciaRegistroAporteDTO;
 	}
 	
 	public List<InconsistenciaRegistroAporteDTO> getLstInconsistenciaRegistroAporteDTO (){
 		return lstInconsistenciaRegistroAporteDTO;
 	}
  
}