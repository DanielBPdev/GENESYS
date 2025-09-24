package com.asopagos.afiliaciones.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Short;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.empleadores.dto.RespuestaConsultaSolicitudDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador/consultarSolicitudAfiliacionEmpleador
 */
public class ConsultarSolicitud extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private String numeroIdentificacion;
  	private String numeroRadicado;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RespuestaConsultaSolicitudDTO> result;
  
 	public ConsultarSolicitud (Short digitoVerificacion,String numeroIdentificacion,String numeroRadicado,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.numeroRadicado=numeroRadicado;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("numeroRadicado", numeroRadicado)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RespuestaConsultaSolicitudDTO>) response.readEntity(new GenericType<List<RespuestaConsultaSolicitudDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RespuestaConsultaSolicitudDTO> getResult() {
		return result;
	}

 
  	public void setDigitoVerificacion (Short digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Short getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}