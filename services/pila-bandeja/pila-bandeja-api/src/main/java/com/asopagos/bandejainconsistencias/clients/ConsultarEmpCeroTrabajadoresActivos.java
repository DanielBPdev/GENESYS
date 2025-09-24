package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.bandejainconsistencias.dto.BandejaEmpleadorCeroTrabajadoresDTO;
import java.lang.Long;
import java.lang.Short;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/consultarEmpCeroTrabajadoresActivos
 */
public class ConsultarEmpCeroTrabajadoresActivos extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private Long fechaFinIngresoBandeja;
  	private Long fechaInicioIngresoBandeja;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String nombreEmpresa;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BandejaEmpleadorCeroTrabajadoresDTO> result;
  
 	public ConsultarEmpCeroTrabajadoresActivos (Short digitoVerificacion,Long fechaFinIngresoBandeja,Long fechaInicioIngresoBandeja,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String nombreEmpresa){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.fechaFinIngresoBandeja=fechaFinIngresoBandeja;
		this.fechaInicioIngresoBandeja=fechaInicioIngresoBandeja;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.nombreEmpresa=nombreEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("fechaFinIngresoBandeja", fechaFinIngresoBandeja)
						.queryParam("fechaInicioIngresoBandeja", fechaInicioIngresoBandeja)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("nombreEmpresa", nombreEmpresa)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BandejaEmpleadorCeroTrabajadoresDTO>) response.readEntity(new GenericType<List<BandejaEmpleadorCeroTrabajadoresDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BandejaEmpleadorCeroTrabajadoresDTO> getResult() {
		return result;
	}

 
  	public void setDigitoVerificacion (Short digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Short getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setFechaFinIngresoBandeja (Long fechaFinIngresoBandeja){
 		this.fechaFinIngresoBandeja=fechaFinIngresoBandeja;
 	}
 	
 	public Long getFechaFinIngresoBandeja (){
 		return fechaFinIngresoBandeja;
 	}
  	public void setFechaInicioIngresoBandeja (Long fechaInicioIngresoBandeja){
 		this.fechaInicioIngresoBandeja=fechaInicioIngresoBandeja;
 	}
 	
 	public Long getFechaInicioIngresoBandeja (){
 		return fechaInicioIngresoBandeja;
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
  	public void setNombreEmpresa (String nombreEmpresa){
 		this.nombreEmpresa=nombreEmpresa;
 	}
 	
 	public String getNombreEmpresa (){
 		return nombreEmpresa;
 	}
  
}