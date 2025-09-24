package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.ProcesoIngresoUtilitarioDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/procesarNovedadIngresoUtilitario
 */
public class ProcesarNovedadIngresoUtilitario extends ServiceClient { 
    	private ProcesoIngresoUtilitarioDTO datosProcesoIng;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ProcesarNovedadIngresoUtilitario (ProcesoIngresoUtilitarioDTO datosProcesoIng){
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

 
  
  	public void setDatosProcesoIng (ProcesoIngresoUtilitarioDTO datosProcesoIng){
 		this.datosProcesoIng=datosProcesoIng;
 	}
 	
 	public ProcesoIngresoUtilitarioDTO getDatosProcesoIng (){
 		return datosProcesoIng;
 	}
  
}