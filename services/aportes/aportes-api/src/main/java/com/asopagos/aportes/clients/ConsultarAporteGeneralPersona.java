package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarAporteGeneralPersona
 */
public class ConsultarAporteGeneralPersona extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private Long idPersona;
  	private EstadoAporteEnum estadoAporte;
  	private EstadoRegistroAporteEnum estadoRegistroAporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteGeneralModeloDTO> result;
  
 	public ConsultarAporteGeneralPersona (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,Long idPersona,EstadoAporteEnum estadoAporte,EstadoRegistroAporteEnum estadoRegistroAporte){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.idPersona=idPersona;
		this.estadoAporte=estadoAporte;
		this.estadoRegistroAporte=estadoRegistroAporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("idPersona", idPersona)
						.queryParam("estadoAporte", estadoAporte)
						.queryParam("estadoRegistroAporte", estadoRegistroAporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AporteGeneralModeloDTO>) response.readEntity(new GenericType<List<AporteGeneralModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteGeneralModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setEstadoAporte (EstadoAporteEnum estadoAporte){
 		this.estadoAporte=estadoAporte;
 	}
 	
 	public EstadoAporteEnum getEstadoAporte (){
 		return estadoAporte;
 	}
  	public void setEstadoRegistroAporte (EstadoRegistroAporteEnum estadoRegistroAporte){
 		this.estadoRegistroAporte=estadoRegistroAporte;
 	}
 	
 	public EstadoRegistroAporteEnum getEstadoRegistroAporte (){
 		return estadoRegistroAporte;
 	}
  
}