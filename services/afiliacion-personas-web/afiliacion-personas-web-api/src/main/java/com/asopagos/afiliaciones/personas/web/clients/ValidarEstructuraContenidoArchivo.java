package com.asopagos.afiliaciones.personas.web.clients;

import java.lang.Long;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWebMultiple/validarEstructuraContenidoArchivo/{idEmpleador}/{idCargueMultiple}
 */
public class ValidarEstructuraContenidoArchivo extends ServiceClient { 
  	private Long idEmpleador;
  	private Long idCargueMultiple;
    	private InformacionArchivoDTO archivoMultiple;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public ValidarEstructuraContenidoArchivo (Long idEmpleador,Long idCargueMultiple,InformacionArchivoDTO archivoMultiple){
 		super();
		this.idEmpleador=idEmpleador;
		this.idCargueMultiple=idCargueMultiple;
		this.archivoMultiple=archivoMultiple;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.resolveTemplate("idCargueMultiple", idCargueMultiple)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoMultiple == null ? null : Entity.json(archivoMultiple));
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
  	public void setIdCargueMultiple (Long idCargueMultiple){
 		this.idCargueMultiple=idCargueMultiple;
 	}
 	
 	public Long getIdCargueMultiple (){
 		return idCargueMultiple;
 	}
  
  
  	public void setArchivoMultiple (InformacionArchivoDTO archivoMultiple){
 		this.archivoMultiple=archivoMultiple;
 	}
 	
 	public InformacionArchivoDTO getArchivoMultiple (){
 		return archivoMultiple;
 	}
  
}