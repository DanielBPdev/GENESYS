package com.asopagos.afiliaciones.empleadores.clients;

import com.asopagos.afiliaciones.empleadores.dto.SolicitudAfiliacionEmpleadorDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador
 */
public class ConsultarSolicitudAfiliEmpleador extends ServiceClient {
 
  
  	private CanalRecepcionEnum canalRecepcion;
  	private String numeroRadicado;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAfiliacionEmpleadorDTO result;
  
 	public ConsultarSolicitudAfiliEmpleador (CanalRecepcionEnum canalRecepcion,String numeroRadicado,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.canalRecepcion=canalRecepcion;
		this.numeroRadicado=numeroRadicado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("canalRecepcion", canalRecepcion)
						.queryParam("numeroRadicado", numeroRadicado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAfiliacionEmpleadorDTO) response.readEntity(SolicitudAfiliacionEmpleadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAfiliacionEmpleadorDTO getResult() {
		return result;
	}

 
  	public void setCanalRecepcion (CanalRecepcionEnum canalRecepcion){
 		this.canalRecepcion=canalRecepcion;
 	}
 	
 	public CanalRecepcionEnum getCanalRecepcion (){
 		return canalRecepcion;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
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