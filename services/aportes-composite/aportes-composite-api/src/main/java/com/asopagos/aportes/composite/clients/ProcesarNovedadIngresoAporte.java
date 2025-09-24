package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/procesarNovedadIngresoAporte
 */
public class ProcesarNovedadIngresoAporte extends ServiceClient { 
    	private ProcesoNovedadIngresoDTO datosProcesoIng;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ProcesarNovedadIngresoAporte (ProcesoNovedadIngresoDTO datosProcesoIng){
 		super();
		this.datosProcesoIng=datosProcesoIng;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosProcesoIng == null ? null : Entity.json(datosProcesoIng));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setDatosProcesoIng (ProcesoNovedadIngresoDTO datosProcesoIng){
 		this.datosProcesoIng=datosProcesoIng;
 	}
 	
 	public ProcesoNovedadIngresoDTO getDatosProcesoIng (){
 		return datosProcesoIng;
 	}
  
}