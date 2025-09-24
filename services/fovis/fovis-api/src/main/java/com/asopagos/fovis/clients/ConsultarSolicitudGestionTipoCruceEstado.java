package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarSolicitudGestionTipoCruceEstado
 */
public class ConsultarSolicitudGestionTipoCruceEstado extends ServiceClient {
 
  
  	private TipoCruceEnum tipoCruce;
  	private EstadoCruceHogarEnum estadoCruceHogar;
  	private EstadoSolicitudGestionCruceEnum estadoSolicitudGestion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudGestionCruceDTO> result;
  
 	public ConsultarSolicitudGestionTipoCruceEstado (TipoCruceEnum tipoCruce,EstadoCruceHogarEnum estadoCruceHogar,EstadoSolicitudGestionCruceEnum estadoSolicitudGestion){
 		super();
		this.tipoCruce=tipoCruce;
		this.estadoCruceHogar=estadoCruceHogar;
		this.estadoSolicitudGestion=estadoSolicitudGestion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoCruce", tipoCruce)
						.queryParam("estadoCruceHogar", estadoCruceHogar)
						.queryParam("estadoSolicitudGestion", estadoSolicitudGestion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudGestionCruceDTO>) response.readEntity(new GenericType<List<SolicitudGestionCruceDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudGestionCruceDTO> getResult() {
		return result;
	}

 
  	public void setTipoCruce (TipoCruceEnum tipoCruce){
 		this.tipoCruce=tipoCruce;
 	}
 	
 	public TipoCruceEnum getTipoCruce (){
 		return tipoCruce;
 	}
  	public void setEstadoCruceHogar (EstadoCruceHogarEnum estadoCruceHogar){
 		this.estadoCruceHogar=estadoCruceHogar;
 	}
 	
 	public EstadoCruceHogarEnum getEstadoCruceHogar (){
 		return estadoCruceHogar;
 	}
  	public void setEstadoSolicitudGestion (EstadoSolicitudGestionCruceEnum estadoSolicitudGestion){
 		this.estadoSolicitudGestion=estadoSolicitudGestion;
 	}
 	
 	public EstadoSolicitudGestionCruceEnum getEstadoSolicitudGestion (){
 		return estadoSolicitudGestion;
 	}
  
}