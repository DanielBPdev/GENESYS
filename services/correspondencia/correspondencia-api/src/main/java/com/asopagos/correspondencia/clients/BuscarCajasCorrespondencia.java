package com.asopagos.correspondencia.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.entidades.ccf.core.CajaCorrespondencia;
import java.util.List;
import com.asopagos.enumeraciones.core.EstadoCajaCorrespondenciaEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest//cajasCorrespondencia/buscar
 */
public class BuscarCajasCorrespondencia extends ServiceClient {
 
  
  	private String codigoEtiqueta;
  	private String codigoSede;
  	private EstadoCajaCorrespondenciaEnum estado;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CajaCorrespondencia> result;
  
 	public BuscarCajasCorrespondencia (String codigoEtiqueta,String codigoSede,EstadoCajaCorrespondenciaEnum estado,Long fechaFin,Long fechaInicio){
 		super();
		this.codigoEtiqueta=codigoEtiqueta;
		this.codigoSede=codigoSede;
		this.estado=estado;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoEtiqueta", codigoEtiqueta)
						.queryParam("codigoSede", codigoSede)
						.queryParam("estado", estado)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CajaCorrespondencia>) response.readEntity(new GenericType<List<CajaCorrespondencia>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CajaCorrespondencia> getResult() {
		return result;
	}

 
  	public void setCodigoEtiqueta (String codigoEtiqueta){
 		this.codigoEtiqueta=codigoEtiqueta;
 	}
 	
 	public String getCodigoEtiqueta (){
 		return codigoEtiqueta;
 	}
  	public void setCodigoSede (String codigoSede){
 		this.codigoSede=codigoSede;
 	}
 	
 	public String getCodigoSede (){
 		return codigoSede;
 	}
  	public void setEstado (EstadoCajaCorrespondenciaEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoCajaCorrespondenciaEnum getEstado (){
 		return estado;
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