package com.asopagos.afiliaciones.empleadores.composite.clients;

import java.lang.Long;
import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/afiliacionEmpleadoresPresencial/iniciar
 */
public class IniciarProcesoAfliliacionEmpleadoresPresencial extends ServiceClient { 
    	private ProcesoAfiliacionEmpleadoresPresencialDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public IniciarProcesoAfliliacionEmpleadoresPresencial (ProcesoAfiliacionEmpleadoresPresencialDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setInDTO (ProcesoAfiliacionEmpleadoresPresencialDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public ProcesoAfiliacionEmpleadoresPresencialDTO getInDTO (){
 		return inDTO;
 	}
  
}