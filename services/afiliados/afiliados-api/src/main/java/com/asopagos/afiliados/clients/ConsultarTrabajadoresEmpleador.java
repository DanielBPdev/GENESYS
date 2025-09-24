package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.dto.TrabajadorEmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/trabajadores
 */
public class ConsultarTrabajadoresEmpleador extends ServiceClient {
 
  
  	private Long idEmpleador;
  	private EstadoAfiliadoEnum estado;
  	private Long idEmpresa;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TrabajadorEmpleadorDTO> result;
  
 	public ConsultarTrabajadoresEmpleador (Long idEmpleador,EstadoAfiliadoEnum estado,Long idEmpresa){
 		super();
		this.idEmpleador=idEmpleador;
		this.estado=estado;
		this.idEmpresa=idEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEmpleador", idEmpleador)
						.queryParam("estado", estado)
						.queryParam("idEmpresa", idEmpresa)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TrabajadorEmpleadorDTO>) response.readEntity(new GenericType<List<TrabajadorEmpleadorDTO>>(){});
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
  	public void setEstado (EstadoAfiliadoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoAfiliadoEnum getEstado (){
 		return estado;
 	}
  	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
}