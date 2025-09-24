package com.asopagos.afiliaciones.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador/consultarSolicitudesEnProceso
 */
public class ConsultarSolicitudesEnProceso extends ServiceClient {
 
  
  	private Long idEmpleador;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAfiliacionEmpleador> result;
  
 	public ConsultarSolicitudesEnProceso (Long idEmpleador,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.idEmpleador=idEmpleador;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEmpleador", idEmpleador)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudAfiliacionEmpleador>) response.readEntity(new GenericType<List<SolicitudAfiliacionEmpleador>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudAfiliacionEmpleador> getResult() {
		return result;
	}

 
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
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