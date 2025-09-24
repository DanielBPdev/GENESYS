package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarEstadoAfiliacionRespectoTipoAfiliacion
 */
public class ConsultarEstadoAfiliacionRespectoTipoAfiliacion extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private Long idPersona;
  	private TipoIdentificacionEnum tipoIdPersona;
  	private Long idPerEmpleador;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numIdPersona;
  	private String numIdEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultaEstadoAfiliacionDTO result;
  
 	public ConsultarEstadoAfiliacionRespectoTipoAfiliacion (TipoIdentificacionEnum tipoIdEmpleador,Long idPersona,TipoIdentificacionEnum tipoIdPersona,Long idPerEmpleador,TipoAfiliadoEnum tipoAfiliado,String numIdPersona,String numIdEmpleador){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.idPersona=idPersona;
		this.tipoIdPersona=tipoIdPersona;
		this.idPerEmpleador=idPerEmpleador;
		this.tipoAfiliado=tipoAfiliado;
		this.numIdPersona=numIdPersona;
		this.numIdEmpleador=numIdEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("idPersona", idPersona)
						.queryParam("tipoIdPersona", tipoIdPersona)
						.queryParam("idPerEmpleador", idPerEmpleador)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numIdPersona", numIdPersona)
						.queryParam("numIdEmpleador", numIdEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConsultaEstadoAfiliacionDTO) response.readEntity(ConsultaEstadoAfiliacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultaEstadoAfiliacionDTO getResult() {
		return result;
	}

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setTipoIdPersona (TipoIdentificacionEnum tipoIdPersona){
 		this.tipoIdPersona=tipoIdPersona;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdPersona (){
 		return tipoIdPersona;
 	}
  	public void setIdPerEmpleador (Long idPerEmpleador){
 		this.idPerEmpleador=idPerEmpleador;
 	}
 	
 	public Long getIdPerEmpleador (){
 		return idPerEmpleador;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setNumIdPersona (String numIdPersona){
 		this.numIdPersona=numIdPersona;
 	}
 	
 	public String getNumIdPersona (){
 		return numIdPersona;
 	}
  	public void setNumIdEmpleador (String numIdEmpleador){
 		this.numIdEmpleador=numIdEmpleador;
 	}
 	
 	public String getNumIdEmpleador (){
 		return numIdEmpleador;
 	}
  
}