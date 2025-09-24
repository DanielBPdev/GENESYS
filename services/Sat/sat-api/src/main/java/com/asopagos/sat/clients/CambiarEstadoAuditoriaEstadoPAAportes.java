package com.asopagos.sat.clients;

import java.lang.Long;
import com.asopagos.sat.dto.RespuestaEstandar;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/cambiarEstadoAuditoriaEstadoPAAportes
 */
public class CambiarEstadoAuditoriaEstadoPAAportes extends ServiceClient {
 
  
  	private Long idAuditoria;
  	private String estado;
  	private String observaciones;
  	private Long id;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaEstandar result;
  
 	public CambiarEstadoAuditoriaEstadoPAAportes (Long idAuditoria,String estado,String observaciones,Long id){
 		super();
		this.idAuditoria=idAuditoria;
		this.estado=estado;
		this.observaciones=observaciones;
		this.id=id;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAuditoria", idAuditoria)
						.queryParam("estado", estado)
						.queryParam("observaciones", observaciones)
						.queryParam("id", id)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaEstandar) response.readEntity(RespuestaEstandar.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaEstandar getResult() {
		return result;
	}

 
  	public void setIdAuditoria (Long idAuditoria){
 		this.idAuditoria=idAuditoria;
 	}
 	
 	public Long getIdAuditoria (){
 		return idAuditoria;
 	}
  	public void setEstado (String estado){
 		this.estado=estado;
 	}
 	
 	public String getEstado (){
 		return estado;
 	}
  	public void setObservaciones (String observaciones){
 		this.observaciones=observaciones;
 	}
 	
 	public String getObservaciones (){
 		return observaciones;
 	}
  	public void setId (Long id){
 		this.id=id;
 	}
 	
 	public Long getId (){
 		return id;
 	}
  
}