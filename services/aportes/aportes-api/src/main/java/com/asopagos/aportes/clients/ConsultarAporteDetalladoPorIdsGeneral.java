package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarAporteDetalladoPorIdsGeneral
 */
public class ConsultarAporteDetalladoPorIdsGeneral extends ServiceClient { 
   	private EstadoAporteEnum estadoAporteAportante;
  	private EstadoRegistroAporteEnum estadoRegistroAporte;
   	private List<Long> listaIdAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDetalladoModeloDTO> result;
  
 	public ConsultarAporteDetalladoPorIdsGeneral (EstadoAporteEnum estadoAporteAportante,EstadoRegistroAporteEnum estadoRegistroAporte,List<Long> listaIdAporteGeneral){
 		super();
		this.estadoAporteAportante=estadoAporteAportante;
		this.estadoRegistroAporte=estadoRegistroAporte;
		this.listaIdAporteGeneral=listaIdAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("estadoAporteAportante", estadoAporteAportante)
			.queryParam("estadoRegistroAporte", estadoRegistroAporte)
			.request(MediaType.APPLICATION_JSON)
			.post(listaIdAporteGeneral == null ? null : Entity.json(listaIdAporteGeneral));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AporteDetalladoModeloDTO>) response.readEntity(new GenericType<List<AporteDetalladoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AporteDetalladoModeloDTO> getResult() {
		return result;
	}

 
  	public void setEstadoAporteAportante (EstadoAporteEnum estadoAporteAportante){
 		this.estadoAporteAportante=estadoAporteAportante;
 	}
 	
 	public EstadoAporteEnum getEstadoAporteAportante (){
 		return estadoAporteAportante;
 	}
  	public void setEstadoRegistroAporte (EstadoRegistroAporteEnum estadoRegistroAporte){
 		this.estadoRegistroAporte=estadoRegistroAporte;
 	}
 	
 	public EstadoRegistroAporteEnum getEstadoRegistroAporte (){
 		return estadoRegistroAporte;
 	}
  
  	public void setListaIdAporteGeneral (List<Long> listaIdAporteGeneral){
 		this.listaIdAporteGeneral=listaIdAporteGeneral;
 	}
 	
 	public List<Long> getListaIdAporteGeneral (){
 		return listaIdAporteGeneral;
 	}
  
}