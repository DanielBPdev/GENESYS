package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.EmpAporPendientesPorAfiliarDTO;
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
 * /rest/pilaBandeja/consultarEmpPendientesPorAfiliar
 */
public class ConsultarEmpPendientesPorAfiliar extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private Long fechaIngresoBandeja;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpAporPendientesPorAfiliarDTO> result;
  
 	public ConsultarEmpPendientesPorAfiliar (Short digitoVerificacion,Long fechaIngresoBandeja,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
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
		this.result = (List<EmpAporPendientesPorAfiliarDTO>) response.readEntity(new GenericType<List<EmpAporPendientesPorAfiliarDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EmpAporPendientesPorAfiliarDTO> getResult() {
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