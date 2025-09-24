package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/consultarSolicitudesNovedad
 */
public class ConsultarSolicitudesNovedad extends ServiceClient { 
   	private Long numeroIdentificacion;
  	private TipoTipoSolicitanteEnum tipoSolicitante;
  	private CanalRecepcionEnum canalRecepcion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudNovedadModeloDTO> result;
  
 	public ConsultarSolicitudesNovedad (Long numeroIdentificacion,TipoTipoSolicitanteEnum tipoSolicitante,CanalRecepcionEnum canalRecepcion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoSolicitante=tipoSolicitante;
		this.canalRecepcion=canalRecepcion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoSolicitante", tipoSolicitante)
			.queryParam("canalRecepcion", canalRecepcion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudNovedadModeloDTO>) response.readEntity(new GenericType<List<SolicitudNovedadModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitudNovedadModeloDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (Long numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public Long getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoSolicitante (TipoTipoSolicitanteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoTipoSolicitanteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setCanalRecepcion (CanalRecepcionEnum canalRecepcion){
 		this.canalRecepcion=canalRecepcion;
 	}
 	
 	public CanalRecepcionEnum getCanalRecepcion (){
 		return canalRecepcion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
  
}