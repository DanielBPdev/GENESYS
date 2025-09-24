package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarResultadosProductoNoConformeDTO;
import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/verificarResultadosProductoNoConformeComunicado/{idTarea}/terminar
 */
public class VerificarResultadosProductoNoConformeComunicado extends ServiceClient { 
  	private Long idTarea;
    	private VerificarResultadosProductoNoConformeDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public VerificarResultadosProductoNoConformeComunicado (Long idTarea,VerificarResultadosProductoNoConformeDTO inDTO){
 		super();
		this.idTarea=idTarea;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Object> getResult() {
		return result;
	}

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
  
  	public void setInDTO (VerificarResultadosProductoNoConformeDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public VerificarResultadosProductoNoConformeDTO getInDTO (){
 		return inDTO;
 	}
  
}