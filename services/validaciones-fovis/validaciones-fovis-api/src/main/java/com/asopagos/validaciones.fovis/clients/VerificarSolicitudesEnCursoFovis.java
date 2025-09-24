package com.asopagos.validaciones.fovis.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/validacionesAPI/verificarSolicitudesEnCurso
 */
public class VerificarSolicitudesEnCursoFovis extends ServiceClient {
 
  
  	private Boolean esNovedad;
  	private String numeroIdentificacion;
  	private Long idSolicitud;
  	private TipoIdentificacionEnum tipoIdentificacion;
	private TipoTransaccionEnum tipoNovedad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ValidacionDTO result;
  
 	public VerificarSolicitudesEnCursoFovis(Boolean esNovedad, String numeroIdentificacion, Long idSolicitud, TipoIdentificacionEnum tipoIdentificacion, TipoTransaccionEnum tipoNovedad){
 		super();
		this.esNovedad=esNovedad;
		this.numeroIdentificacion=numeroIdentificacion;
		this.idSolicitud=idSolicitud;
		this.tipoIdentificacion=tipoIdentificacion;
		this.tipoNovedad=tipoNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("esNovedad", esNovedad)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("idSolicitud", idSolicitud)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ValidacionDTO) response.readEntity(ValidacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ValidacionDTO getResult() {
		return result;
	}

 
  	public void setEsNovedad (Boolean esNovedad){
 		this.esNovedad=esNovedad;
 	}
 	
 	public Boolean getEsNovedad (){
 		return esNovedad;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}

	public TipoTransaccionEnum getTipoNovedad() {
		return this.tipoNovedad;
	}

	public void setTipoNovedad(TipoTransaccionEnum tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}
  
}