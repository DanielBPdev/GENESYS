package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/buscarControlResultadosPersona
 */
public class BuscarControlResultadosPersona extends ServiceClient {
 
  
  	private Long periodoIngreso;
  	private Long numeroPlanilla;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RespuestaConsultaEmpleadorDTO> result;
  
 	public BuscarControlResultadosPersona (Long periodoIngreso,Long numeroPlanilla,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.periodoIngreso=periodoIngreso;
		this.numeroPlanilla=numeroPlanilla;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodoIngreso", periodoIngreso)
						.queryParam("numeroPlanilla", numeroPlanilla)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RespuestaConsultaEmpleadorDTO>) response.readEntity(new GenericType<List<RespuestaConsultaEmpleadorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RespuestaConsultaEmpleadorDTO> getResult() {
		return result;
	}

 
  	public void setPeriodoIngreso (Long periodoIngreso){
 		this.periodoIngreso=periodoIngreso;
 	}
 	
 	public Long getPeriodoIngreso (){
 		return periodoIngreso;
 	}
  	public void setNumeroPlanilla (Long numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public Long getNumeroPlanilla (){
 		return numeroPlanilla;
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