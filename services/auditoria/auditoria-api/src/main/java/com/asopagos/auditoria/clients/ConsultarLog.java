package com.asopagos.auditoria.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.auditoria.dto.RevisionDTO;
import java.lang.String;
import com.asopagos.entidades.enumeraciones.auditoria.TipoOperacionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/auditoria/consultarLog
 */
public class ConsultarLog extends ServiceClient {
 
  
  	private TipoOperacionEnum tipoOperacion;
  	private List<String> tablas;
  	private String usuario;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RevisionDTO> result;
  
 	public ConsultarLog (TipoOperacionEnum tipoOperacion,List<String> tablas,String usuario,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoOperacion=tipoOperacion;
		this.tablas=tablas;
		this.usuario=usuario;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoOperacion", tipoOperacion)
						.queryParam("tablas", tablas.toArray())
						.queryParam("usuario", usuario)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RevisionDTO>) response.readEntity(new GenericType<List<RevisionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RevisionDTO> getResult() {
		return result;
	}

 
  	public void setTipoOperacion (TipoOperacionEnum tipoOperacion){
 		this.tipoOperacion=tipoOperacion;
 	}
 	
 	public TipoOperacionEnum getTipoOperacion (){
 		return tipoOperacion;
 	}
  	public void setTablas (List<String> tablas){
 		this.tablas=tablas;
 	}
 	
 	public List<String> getTablas (){
 		return tablas;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}