package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import java.lang.Byte;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/consultarListaEspecialRevision
 */
public class ConsultarListaEspecialRevision extends ServiceClient {
 
  
  	private Byte digitoVerificacion;
  	private Long fechaInicioInclusion;
  	private Long fechaFinInclusion;
  	private String numeroIdentificacion;
  	private String nombreEmpleador;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ListaEspecialRevisionDTO> result;
  
 	public ConsultarListaEspecialRevision (Byte digitoVerificacion,Long fechaInicioInclusion,Long fechaFinInclusion,String numeroIdentificacion,String nombreEmpleador,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.fechaInicioInclusion=fechaInicioInclusion;
		this.fechaFinInclusion=fechaFinInclusion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.nombreEmpleador=nombreEmpleador;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("fechaInicioInclusion", fechaInicioInclusion)
						.queryParam("fechaFinInclusion", fechaFinInclusion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("nombreEmpleador", nombreEmpleador)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ListaEspecialRevisionDTO>) response.readEntity(new GenericType<List<ListaEspecialRevisionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ListaEspecialRevisionDTO> getResult() {
		return result;
	}

 
  	public void setDigitoVerificacion (Byte digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Byte getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setFechaInicioInclusion (Long fechaInicioInclusion){
 		this.fechaInicioInclusion=fechaInicioInclusion;
 	}
 	
 	public Long getFechaInicioInclusion (){
 		return fechaInicioInclusion;
 	}
  	public void setFechaFinInclusion (Long fechaFinInclusion){
 		this.fechaFinInclusion=fechaFinInclusion;
 	}
 	
 	public Long getFechaFinInclusion (){
 		return fechaFinInclusion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setNombreEmpleador (String nombreEmpleador){
 		this.nombreEmpleador=nombreEmpleador;
 	}
 	
 	public String getNombreEmpleador (){
 		return nombreEmpleador;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}