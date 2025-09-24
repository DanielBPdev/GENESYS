package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/consultarRolesAfiliadosEmpleadorMasivo
 */
public class ConsultarRolesAfiliadosEmpleadorMasivo extends ServiceClient { 
   	private EstadoAfiliadoEnum idEmpleador;
   	private List<Long> idEmpleadores;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RolAfiliadoModeloDTO> result;
  
 	public ConsultarRolesAfiliadosEmpleadorMasivo (EstadoAfiliadoEnum idEmpleador,List<Long> idEmpleadores){
 		super();
		this.idEmpleador=idEmpleador;
		this.idEmpleadores=idEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<RolAfiliadoModeloDTO>) response.readEntity(new GenericType<List<RolAfiliadoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<RolAfiliadoModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdEmpleador (EstadoAfiliadoEnum idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public EstadoAfiliadoEnum getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setIdEmpleadores (List<Long> idEmpleadores){
 		this.idEmpleadores=idEmpleadores;
 	}
 	
 	public List<Long> getIdEmpleadores (){
 		return idEmpleadores;
 	}
  
}