package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.ListadoSolicitudesAfiliacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/solicitudAfiliacionPersona
 */
public class ConsultarSolicitudAfiliacionPersonaAfiliada extends ServiceClient {
 
  
  	private EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud;
  	private String numeroRadicacion;
  	private CanalRecepcionEnum canalRecepcion;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ListadoSolicitudesAfiliacionDTO result;
  
 	public ConsultarSolicitudAfiliacionPersonaAfiliada (EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud,String numeroRadicacion,CanalRecepcionEnum canalRecepcion,TipoAfiliadoEnum tipoAfiliado,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.estadoSolicitud=estadoSolicitud;
		this.numeroRadicacion=numeroRadicacion;
		this.canalRecepcion=canalRecepcion;
		this.tipoAfiliado=tipoAfiliado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoSolicitud", estadoSolicitud)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("canalRecepcion", canalRecepcion)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ListadoSolicitudesAfiliacionDTO) response.readEntity(ListadoSolicitudesAfiliacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ListadoSolicitudesAfiliacionDTO getResult() {
		return result;
	}

 
  	public void setEstadoSolicitud (EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudAfiliacionPersonaEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setCanalRecepcion (CanalRecepcionEnum canalRecepcion){
 		this.canalRecepcion=canalRecepcion;
 	}
 	
 	public CanalRecepcionEnum getCanalRecepcion (){
 		return canalRecepcion;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}