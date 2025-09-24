package com.asopagos.sat.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.sat.dto.TerminacionRelacionLaboralDTO;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/consultarTerminacionRelacionLaboral
 */
public class ConsultarTerminacionRelacionLaboral extends ServiceClient {
 
  
  	private String numeroDocumentoTrabajador;
  	private String estado;
  	private Long fechaFin;
  	private String numeroDocumentoEmpleador;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TerminacionRelacionLaboralDTO> result;
  
 	public ConsultarTerminacionRelacionLaboral (String numeroDocumentoTrabajador,String estado,Long fechaFin,String numeroDocumentoEmpleador,Long fechaInicio){
 		super();
		this.numeroDocumentoTrabajador=numeroDocumentoTrabajador;
		this.estado=estado;
		this.fechaFin=fechaFin;
		this.numeroDocumentoEmpleador=numeroDocumentoEmpleador;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroDocumentoTrabajador", numeroDocumentoTrabajador)
						.queryParam("estado", estado)
						.queryParam("fechaFin", fechaFin)
						.queryParam("numeroDocumentoEmpleador", numeroDocumentoEmpleador)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TerminacionRelacionLaboralDTO>) response.readEntity(new GenericType<List<TerminacionRelacionLaboralDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TerminacionRelacionLaboralDTO> getResult() {
		return result;
	}

 
  	public void setNumeroDocumentoTrabajador (String numeroDocumentoTrabajador){
 		this.numeroDocumentoTrabajador=numeroDocumentoTrabajador;
 	}
 	
 	public String getNumeroDocumentoTrabajador (){
 		return numeroDocumentoTrabajador;
 	}
  	public void setEstado (String estado){
 		this.estado=estado;
 	}
 	
 	public String getEstado (){
 		return estado;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setNumeroDocumentoEmpleador (String numeroDocumentoEmpleador){
 		this.numeroDocumentoEmpleador=numeroDocumentoEmpleador;
 	}
 	
 	public String getNumeroDocumentoEmpleador (){
 		return numeroDocumentoEmpleador;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}