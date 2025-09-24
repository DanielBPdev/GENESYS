package com.asopagos.historicos.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/obtenerEstadoTrabajadorPeriodo
 */
public class ObtenerEstadoTrabajador extends ServiceClient {
 
  
  	private Long startDate;
  	private Long idEmpleador;
  	private Long idAfiliado;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private Long endDate;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoAfiliadoEnum result;
  
 	public ObtenerEstadoTrabajador (Long startDate,Long idEmpleador,Long idAfiliado,TipoAfiliadoEnum tipoAfiliado,Long endDate){
 		super();
		this.startDate=startDate;
		this.idEmpleador=idEmpleador;
		this.idAfiliado=idAfiliado;
		this.tipoAfiliado=tipoAfiliado;
		this.endDate=endDate;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("startDate", startDate)
						.queryParam("idEmpleador", idEmpleador)
						.queryParam("idAfiliado", idAfiliado)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("endDate", endDate)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EstadoAfiliadoEnum) response.readEntity(EstadoAfiliadoEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EstadoAfiliadoEnum getResult() {
		return result;
	}

 
  	public void setStartDate (Long startDate){
 		this.startDate=startDate;
 	}
 	
 	public Long getStartDate (){
 		return startDate;
 	}
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setEndDate (Long endDate){
 		this.endDate=endDate;
 	}
 	
 	public Long getEndDate (){
 		return endDate;
 	}
  
}