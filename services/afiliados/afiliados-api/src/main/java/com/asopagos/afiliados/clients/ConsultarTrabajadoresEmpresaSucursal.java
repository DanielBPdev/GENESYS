package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.TrabajadorEmpleadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/consultarTrabajadoresSucursales
 */
public class ConsultarTrabajadoresEmpresaSucursal extends ServiceClient { 
   	private Long idEmpleadorDestino;
  	private Long idEmpleadorOrigen;
  	private Long fechaLabores;
   	private List<Long> idSucursales;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TrabajadorEmpleadorDTO> result;
  
 	public ConsultarTrabajadoresEmpresaSucursal (Long idEmpleadorDestino,Long idEmpleadorOrigen,Long fechaLabores,List<Long> idSucursales){
 		super();
		this.idEmpleadorDestino=idEmpleadorDestino;
		this.idEmpleadorOrigen=idEmpleadorOrigen;
		this.fechaLabores=fechaLabores;
		this.idSucursales=idSucursales;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleadorDestino", idEmpleadorDestino)
			.queryParam("idEmpleadorOrigen", idEmpleadorOrigen)
			.queryParam("fechaLabores", fechaLabores)
			.request(MediaType.APPLICATION_JSON)
			.post(idSucursales == null ? null : Entity.json(idSucursales));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<TrabajadorEmpleadorDTO>) response.readEntity(new GenericType<List<TrabajadorEmpleadorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<TrabajadorEmpleadorDTO> getResult() {
		return result;
	}

 
  	public void setIdEmpleadorDestino (Long idEmpleadorDestino){
 		this.idEmpleadorDestino=idEmpleadorDestino;
 	}
 	
 	public Long getIdEmpleadorDestino (){
 		return idEmpleadorDestino;
 	}
  	public void setIdEmpleadorOrigen (Long idEmpleadorOrigen){
 		this.idEmpleadorOrigen=idEmpleadorOrigen;
 	}
 	
 	public Long getIdEmpleadorOrigen (){
 		return idEmpleadorOrigen;
 	}
  	public void setFechaLabores (Long fechaLabores){
 		this.fechaLabores=fechaLabores;
 	}
 	
 	public Long getFechaLabores (){
 		return fechaLabores;
 	}
  
  	public void setIdSucursales (List<Long> idSucursales){
 		this.idSucursales=idSucursales;
 	}
 	
 	public List<Long> getIdSucursales (){
 		return idSucursales;
 	}
  
}