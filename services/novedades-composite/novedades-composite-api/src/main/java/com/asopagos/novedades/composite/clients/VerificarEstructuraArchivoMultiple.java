package com.asopagos.novedades.composite.clients;

import java.lang.Long;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/verificarEstructuraArchivoMultiple/{idEmpleador}
 */
public class VerificarEstructuraArchivoMultiple extends ServiceClient { 
  	private Long idEmpleador;
    	private CargueMultipleDTO cargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public VerificarEstructuraArchivoMultiple (Long idEmpleador,CargueMultipleDTO cargue){
 		super();
		this.idEmpleador=idEmpleador;
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoValidacionArchivoDTO) response.readEntity(ResultadoValidacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoDTO getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setCargue (CargueMultipleDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueMultipleDTO getCargue (){
 		return cargue;
 	}
  
}