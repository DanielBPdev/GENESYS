package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.comunicados.dto.CertificadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/certificados/consultar
 */
public class ConsultarCertificados extends ServiceClient {
 
  
  	private TipoCertificadoEnum tipoCertificado;
  	private String numeroIdentificacion;
  	private Boolean empleador;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CertificadoDTO> result;
  
 	public ConsultarCertificados (TipoCertificadoEnum tipoCertificado,String numeroIdentificacion,Boolean empleador,TipoIdentificacionEnum tipoIdentificacion,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoCertificado=tipoCertificado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.empleador=empleador;
		this.tipoIdentificacion=tipoIdentificacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoCertificado", tipoCertificado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("empleador", empleador)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CertificadoDTO>) response.readEntity(new GenericType<List<CertificadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CertificadoDTO> getResult() {
		return result;
	}

 
  	public void setTipoCertificado (TipoCertificadoEnum tipoCertificado){
 		this.tipoCertificado=tipoCertificado;
 	}
 	
 	public TipoCertificadoEnum getTipoCertificado (){
 		return tipoCertificado;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setEmpleador (Boolean empleador){
 		this.empleador=empleador;
 	}
 	
 	public Boolean getEmpleador (){
 		return empleador;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
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