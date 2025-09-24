package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarAporteDetalladoPorIdsGeneralPersona
 */
public class ConsultarAporteDetalladoPorIdsGeneralPersona extends ServiceClient { 
   	private Long idPersona;
  	private EstadoAporteEnum estadoAporteAportante;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private EstadoRegistroAporteEnum estadoRegistroAporte;
   	private List<Long> listaIdAporteGeneral;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDetalladoModeloDTO> result;
  
 	public ConsultarAporteDetalladoPorIdsGeneralPersona (Long idPersona,EstadoAporteEnum estadoAporteAportante,TipoAfiliadoEnum tipoAfiliado,EstadoRegistroAporteEnum estadoRegistroAporte,List<Long> listaIdAporteGeneral){
 		super();
		this.idPersona=idPersona;
		this.estadoAporteAportante=estadoAporteAportante;
		this.tipoAfiliado=tipoAfiliado;
		this.estadoRegistroAporte=estadoRegistroAporte;
		this.listaIdAporteGeneral=listaIdAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPersona", idPersona)
			.queryParam("estadoAporteAportante", estadoAporteAportante)
			.queryParam("tipoAfiliado", tipoAfiliado)
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

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setEstadoAporteAportante (EstadoAporteEnum estadoAporteAportante){
 		this.estadoAporteAportante=estadoAporteAportante;
 	}
 	
 	public EstadoAporteEnum getEstadoAporteAportante (){
 		return estadoAporteAportante;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
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