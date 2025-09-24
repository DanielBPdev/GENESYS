package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/crearNuevoCicloGestionManual
 */
public class CrearNuevoCicloGestionManual extends ServiceClient { 
   	private TipoLineaCobroEnum lineaCobro;
  	private Long fechaFin;
  	private Long fechaInicio;
   	private List<SimulacionDTO> aportantes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloCarteraModeloDTO result;
  
 	public CrearNuevoCicloGestionManual (TipoLineaCobroEnum lineaCobro,Long fechaFin,Long fechaInicio,List<SimulacionDTO> aportantes){
 		super();
		this.lineaCobro=lineaCobro;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
		this.aportantes=aportantes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("lineaCobro", lineaCobro)
			.queryParam("fechaFin", fechaFin)
			.queryParam("fechaInicio", fechaInicio)
			.request(MediaType.APPLICATION_JSON)
			.post(aportantes == null ? null : Entity.json(aportantes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CicloCarteraModeloDTO) response.readEntity(CicloCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CicloCarteraModeloDTO getResult() {
		return result;
	}

 
  	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
  	public void setAportantes (List<SimulacionDTO> aportantes){
 		this.aportantes=aportantes;
 	}
 	
 	public List<SimulacionDTO> getAportantes (){
 		return aportantes;
 	}
  
}