package com.asopagos.validaciones.clients;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import com.asopagos.dto.ListaDatoValidacionDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/validacionesAPI/validarCargaMultipleAfiliaciones
 */
public class ValidarCargaMultipleAfiliaciones extends ServiceClient { 
   	private String bloque;
  	private ProcesoEnum proceso;
  	private String objetoValidacion;
   	private List<AfiliarTrabajadorCandidatoDTO> candidatosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ListaDatoValidacionDTO result;
  
 	public ValidarCargaMultipleAfiliaciones (String bloque,ProcesoEnum proceso,String objetoValidacion,List<AfiliarTrabajadorCandidatoDTO> candidatosDTO){
 		super();
		this.bloque=bloque;
		this.proceso=proceso;
		this.objetoValidacion=objetoValidacion;
		this.candidatosDTO=candidatosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("bloque", bloque)
			.queryParam("proceso", proceso)
			.queryParam("objetoValidacion", objetoValidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(candidatosDTO == null ? null : Entity.json(candidatosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ListaDatoValidacionDTO) response.readEntity(ListaDatoValidacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ListaDatoValidacionDTO getResult() {
		return result;
	}

 
  	public void setBloque (String bloque){
 		this.bloque=bloque;
 	}
 	
 	public String getBloque (){
 		return bloque;
 	}
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setObjetoValidacion (String objetoValidacion){
 		this.objetoValidacion=objetoValidacion;
 	}
 	
 	public String getObjetoValidacion (){
 		return objetoValidacion;
 	}
  
  	public void setCandidatosDTO (List<AfiliarTrabajadorCandidatoDTO> candidatosDTO){
 		this.candidatosDTO=candidatosDTO;
 	}
 	
 	public List<AfiliarTrabajadorCandidatoDTO> getCandidatosDTO (){
 		return candidatosDTO;
 	}
  
}