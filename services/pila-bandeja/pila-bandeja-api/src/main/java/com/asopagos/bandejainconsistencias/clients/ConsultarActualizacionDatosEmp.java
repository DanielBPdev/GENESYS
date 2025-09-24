package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Short;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/consultarActualizacionDatosEmpleador
 */
public class ConsultarActualizacionDatosEmp extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private Long fechaIngresoBandeja;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ActualizacionDatosEmpleadorModeloDTO> result;
  
 	public ConsultarActualizacionDatosEmp (Short digitoVerificacion,Long fechaIngresoBandeja,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.fechaIngresoBandeja=fechaIngresoBandeja;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("fechaIngresoBandeja", fechaIngresoBandeja)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ActualizacionDatosEmpleadorModeloDTO>) response.readEntity(new GenericType<List<ActualizacionDatosEmpleadorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ActualizacionDatosEmpleadorModeloDTO> getResult() {
		return result;
	}

 
  	public void setDigitoVerificacion (Short digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Short getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setFechaIngresoBandeja (Long fechaIngresoBandeja){
 		this.fechaIngresoBandeja=fechaIngresoBandeja;
 	}
 	
 	public Long getFechaIngresoBandeja (){
 		return fechaIngresoBandeja;
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