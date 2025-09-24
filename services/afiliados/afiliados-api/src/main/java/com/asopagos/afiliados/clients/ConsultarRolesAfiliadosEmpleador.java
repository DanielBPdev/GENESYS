package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idEmpleador}/consultarRolesAfiliadosEmpleador
 */
public class ConsultarRolesAfiliadosEmpleador extends ServiceClient {
 
  	private Long idEmpleador;
  
  	private EstadoAfiliadoEnum estadoAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RolAfiliadoModeloDTO> result;
  
 	public ConsultarRolesAfiliadosEmpleador (Long idEmpleador,EstadoAfiliadoEnum estadoAfiliado){
 		super();
		this.idEmpleador=idEmpleador;
		this.estadoAfiliado=estadoAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.queryParam("estadoAfiliado", estadoAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RolAfiliadoModeloDTO>) response.readEntity(new GenericType<List<RolAfiliadoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RolAfiliadoModeloDTO> getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setEstadoAfiliado (EstadoAfiliadoEnum estadoAfiliado){
 		this.estadoAfiliado=estadoAfiliado;
 	}
 	
 	public EstadoAfiliadoEnum getEstadoAfiliado (){
 		return estadoAfiliado;
 	}
  
}