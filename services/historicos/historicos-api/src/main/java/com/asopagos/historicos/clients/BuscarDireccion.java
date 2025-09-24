package com.asopagos.historicos.clients;

import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/buscarDireccion/{idPersona}
 */
public class BuscarDireccion extends ServiceClient {
 
  	private Long idPersona;
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private TipoUbicacionEnum tipoUbicacion;
  	private Long fechaRevision;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UbicacionDTO result;
  
 	public BuscarDireccion (Long idPersona,TipoSolicitanteMovimientoAporteEnum tipoSolicitante,TipoUbicacionEnum tipoUbicacion,Long fechaRevision){
 		super();
		this.idPersona=idPersona;
		this.tipoSolicitante=tipoSolicitante;
		this.tipoUbicacion=tipoUbicacion;
		this.fechaRevision=fechaRevision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("tipoUbicacion", tipoUbicacion)
						.queryParam("fechaRevision", fechaRevision)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UbicacionDTO) response.readEntity(UbicacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UbicacionDTO getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setTipoUbicacion (TipoUbicacionEnum tipoUbicacion){
 		this.tipoUbicacion=tipoUbicacion;
 	}
 	
 	public TipoUbicacionEnum getTipoUbicacion (){
 		return tipoUbicacion;
 	}
  	public void setFechaRevision (Long fechaRevision){
 		this.fechaRevision=fechaRevision;
 	}
 	
 	public Long getFechaRevision (){
 		return fechaRevision;
 	}
  
}