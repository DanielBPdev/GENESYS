package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/buscarListaExclusionCarteraActiva
 */
public class BuscarListaExclusionCarteraActiva extends ServiceClient { 
   	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
   	private List<Long> listIdePersonas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ExclusionCarteraDTO> result;
  
 	public BuscarListaExclusionCarteraActiva (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,List<Long> listIdePersonas){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.listIdePersonas=listIdePersonas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoSolicitante", tipoSolicitante)
			.request(MediaType.APPLICATION_JSON)
			.post(listIdePersonas == null ? null : Entity.json(listIdePersonas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ExclusionCarteraDTO>) response.readEntity(new GenericType<List<ExclusionCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ExclusionCarteraDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
  	public void setListIdePersonas (List<Long> listIdePersonas){
 		this.listIdePersonas=listIdePersonas;
 	}
 	
 	public List<Long> getListIdePersonas (){
 		return listIdePersonas;
 	}
  
}