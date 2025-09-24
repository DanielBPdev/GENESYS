package com.asopagos.novedades.composite.clients;

import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import javax.ws.rs.core.GenericType;
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
 * /rest/novedadesComposite/guardarDatosNovedadArchivoMultiple/{idEmpleador}
 */
public class GuardarDatosNovedadArchivoMultiple extends ServiceClient { 
  	private Long idEmpleador;
   	private String nombreArchivo;
  	private Long numeroDiaTemporizador;
  	private Long codigoCargue;
   	private List<TrabajadorCandidatoNovedadDTO> lstSolicitudNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TrabajadorCandidatoNovedadDTO> result;
  
 	public GuardarDatosNovedadArchivoMultiple (Long idEmpleador,String nombreArchivo,Long numeroDiaTemporizador,Long codigoCargue,List<TrabajadorCandidatoNovedadDTO> lstSolicitudNovedadDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.nombreArchivo=nombreArchivo;
		this.numeroDiaTemporizador=numeroDiaTemporizador;
		this.codigoCargue=codigoCargue;
		this.lstSolicitudNovedadDTO=lstSolicitudNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.queryParam("nombreArchivo", nombreArchivo)
			.queryParam("numeroDiaTemporizador", numeroDiaTemporizador)
			.queryParam("codigoCargue", codigoCargue)
			.request(MediaType.APPLICATION_JSON)
			.post(lstSolicitudNovedadDTO == null ? null : Entity.json(lstSolicitudNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<TrabajadorCandidatoNovedadDTO>) response.readEntity(new GenericType<List<TrabajadorCandidatoNovedadDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<TrabajadorCandidatoNovedadDTO> getResult() {
		return result;
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
  	public void setCodigoCargue (Long codigoCargue){
 		this.codigoCargue=codigoCargue;
 	}
 	
 	public Long getCodigoCargue (){
 		return codigoCargue;
 	}
  
  	public void setLstSolicitudNovedadDTO (List<TrabajadorCandidatoNovedadDTO> lstSolicitudNovedadDTO){
 		this.lstSolicitudNovedadDTO=lstSolicitudNovedadDTO;
 	}
 	
 	public List<TrabajadorCandidatoNovedadDTO> getLstSolicitudNovedadDTO (){
 		return lstSolicitudNovedadDTO;
 	}
  
}