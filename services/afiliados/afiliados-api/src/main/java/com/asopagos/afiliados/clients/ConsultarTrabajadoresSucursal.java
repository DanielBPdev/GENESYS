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
 * /rest/afiliados/consultarTrabajadoresEmpleadorSucursal
 */
public class ConsultarTrabajadoresSucursal extends ServiceClient { 
   	private Long idEmpleador;
  	private Long idSucursal;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TrabajadorEmpleadorDTO> result;
  
 	public ConsultarTrabajadoresSucursal (Long idEmpleador,Long idSucursal){
 		super();
		this.idEmpleador=idEmpleador;
		this.idSucursal=idSucursal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.queryParam("idSucursal", idSucursal)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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

 
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setIdSucursal (Long idSucursal){
 		this.idSucursal=idSucursal;
 	}
 	
 	public Long getIdSucursal (){
 		return idSucursal;
 	}
  
  
}