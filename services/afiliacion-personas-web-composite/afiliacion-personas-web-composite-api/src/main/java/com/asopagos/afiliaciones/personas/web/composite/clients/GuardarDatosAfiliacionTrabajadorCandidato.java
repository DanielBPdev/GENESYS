package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/guardarDatosAfiliacionTrabajadorCandidato/{idEmpleador}
 */
public class GuardarDatosAfiliacionTrabajadorCandidato extends ServiceClient { 
  	private Long idEmpleador;
   	private String nombreArchivo;
  	private Long numeroDiaTemporizador;
   	private List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO;
  
  
 	public GuardarDatosAfiliacionTrabajadorCandidato (Long idEmpleador,String nombreArchivo,Long numeroDiaTemporizador,List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.nombreArchivo=nombreArchivo;
		this.numeroDiaTemporizador=numeroDiaTemporizador;
		this.lstTrabajadorCandidatoDTO=lstTrabajadorCandidatoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.queryParam("nombreArchivo", nombreArchivo)
			.queryParam("numeroDiaTemporizador", numeroDiaTemporizador)
			.request(MediaType.APPLICATION_JSON)
			.post(lstTrabajadorCandidatoDTO == null ? null : Entity.json(lstTrabajadorCandidatoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setNombreArchivo (String nombreArchivo){
 		this.nombreArchivo=nombreArchivo;
 	}
 	
 	public String getNombreArchivo (){
 		return nombreArchivo;
 	}
  	public void setNumeroDiaTemporizador (Long numeroDiaTemporizador){
 		this.numeroDiaTemporizador=numeroDiaTemporizador;
 	}
 	
 	public Long getNumeroDiaTemporizador (){
 		return numeroDiaTemporizador;
 	}
  
  	public void setLstTrabajadorCandidatoDTO (List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO){
 		this.lstTrabajadorCandidatoDTO=lstTrabajadorCandidatoDTO;
 	}
 	
 	public List<AfiliarTrabajadorCandidatoDTO> getLstTrabajadorCandidatoDTO (){
 		return lstTrabajadorCandidatoDTO;
 	}
  
}