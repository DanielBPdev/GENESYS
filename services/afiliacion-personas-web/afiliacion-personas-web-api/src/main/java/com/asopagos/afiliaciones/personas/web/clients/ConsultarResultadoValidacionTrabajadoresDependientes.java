package com.asopagos.afiliaciones.personas.web.clients;

import java.lang.Long;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliacionesPersonasWeb/consultarResultadoValidacion/{idEmpleador}
 */
public class ConsultarResultadoValidacionTrabajadoresDependientes extends ServiceClient {
 
  	private Long idEmpleador;
  
  	private EstadoCargaMultipleEnum estado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public ConsultarResultadoValidacionTrabajadoresDependientes (Long idEmpleador,EstadoCargaMultipleEnum estado){
 		super();
		this.idEmpleador=idEmpleador;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.queryParam("estado", estado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoValidacionArchivoDTO) response.readEntity(ResultadoValidacionArchivoDTO.class);
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
  
  	public void setEstado (EstadoCargaMultipleEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoCargaMultipleEnum getEstado (){
 		return estado;
 	}
  
}