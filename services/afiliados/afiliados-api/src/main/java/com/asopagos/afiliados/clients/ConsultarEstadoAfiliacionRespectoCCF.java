package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarEstadoAfiliacionRespectoCCF
 */
public class ConsultarEstadoAfiliacionRespectoCCF extends ServiceClient {
 
  
  	private Long idPersona;
  	private TipoIdentificacionEnum tipoIdPersona;
  	private String numIdPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultaEstadoAfiliacionDTO result;
  
 	public ConsultarEstadoAfiliacionRespectoCCF (Long idPersona,TipoIdentificacionEnum tipoIdPersona,String numIdPersona){
 		super();
		this.idPersona=idPersona;
		this.tipoIdPersona=tipoIdPersona;
		this.numIdPersona=numIdPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("tipoIdPersona", tipoIdPersona)
						.queryParam("numIdPersona", numIdPersona)
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
  	public void setNumIdPersona (String numIdPersona){
 		this.numIdPersona=numIdPersona;
 	}
 	
 	public String getNumIdPersona (){
 		return numIdPersona;
 	}
  
}