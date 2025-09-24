

package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import java.lang.Long;
import com.asopagos.novedades.dto.DatosReporteAfiliadosSupervivenciaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import com.asopagos.novedades.dto.GenerarReporteSupervivenciaDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/generarReporteAfiSupervivenciaJson
 */
public class GenerarReporteAfiliadosSupervivenciaJson extends ServiceClient {
 
  
  	//private String estado;
  	private GenerarReporteSupervivenciaDTO gerarReporte;
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosReporteAfiliadosSupervivenciaDTO> result;
  
//	public GenerarReporteAfiliadosSupervivenciaJson (String estado){
//		super();
//		this.estado=estado;
//	}
//
//	@Override
//	protected Response invoke(WebTarget webTarget, String path) {
//		Response response = webTarget.path(path)
//									.queryParam("estado", estado)
//						.request(MediaType.APPLICATION_JSON).get();
//		return response;
//	}
	public GenerarReporteAfiliadosSupervivenciaJson (GenerarReporteSupervivenciaDTO gerarReporte){
		super();
	   this.gerarReporte=gerarReporte;
	}

	@Override
   protected Response invoke(WebTarget webTarget, String path) {
	   Response response = webTarget.path(path)
		   .request(MediaType.APPLICATION_JSON)
		   .post(gerarReporte == null ? null : Entity.json(gerarReporte));
	   return response;
   }
   
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosReporteAfiliadosSupervivenciaDTO>) response.readEntity(new GenericType<List<DatosReporteAfiliadosSupervivenciaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosReporteAfiliadosSupervivenciaDTO> getResult() {
		return result;
	}

	public void setGerarReporte (GenerarReporteSupervivenciaDTO gerarReporte){
		this.gerarReporte=gerarReporte;
	}
	
	public GenerarReporteSupervivenciaDTO getGerarReporte (){
		return gerarReporte;
	}
  //public void setEstado (String estado){
 //	this.estado=estado;
 //}
 //
 //public String getEstado (){
 //	return estado;
 //}

  
}