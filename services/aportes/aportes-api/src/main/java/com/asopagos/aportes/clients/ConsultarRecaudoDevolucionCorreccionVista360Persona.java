package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRecaudoDevolucionCorreccionVista360Persona
 */
public class ConsultarRecaudoDevolucionCorreccionVista360Persona extends ServiceClient { 
   	private List<Long> idAporteGeneral;
  	private TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte;
   	private ConsultarRecaudoDTO consultaRecaudo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarRecaudoDevolucionCorreccionVista360Persona (List<Long> idAporteGeneral,TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,ConsultarRecaudoDTO consultaRecaudo){
 		super();
		this.idAporteGeneral=idAporteGeneral;
		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
		this.consultaRecaudo=consultaRecaudo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idAporteGeneral", idAporteGeneral)
			.queryParam("tipoMovimientoRecaudoAporte", tipoMovimientoRecaudoAporte)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaRecaudo == null ? null : Entity.json(consultaRecaudo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CuentaAporteDTO>) response.readEntity(new GenericType<List<CuentaAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CuentaAporteDTO> getResult() {
		return result;
	}

 
  	public void setIdAporteGeneral (List<Long> idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public List<Long> getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  	public void setTipoMovimientoRecaudoAporte (TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte){
 		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
 	}
 	
 	public TipoMovimientoRecaudoAporteEnum getTipoMovimientoRecaudoAporte (){
 		return tipoMovimientoRecaudoAporte;
 	}
  
  	public void setConsultaRecaudo (ConsultarRecaudoDTO consultaRecaudo){
 		this.consultaRecaudo=consultaRecaudo;
 	}
 	
 	public ConsultarRecaudoDTO getConsultaRecaudo (){
 		return consultaRecaudo;
 	}
  
}