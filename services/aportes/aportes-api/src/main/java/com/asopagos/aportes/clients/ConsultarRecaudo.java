package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import java.lang.Boolean;
import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRecaudo
 */
public class ConsultarRecaudo extends ServiceClient { 
   	private Boolean vista360;
  	private TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte;
  	private Boolean hayParametros;
   	private ConsultarRecaudoDTO consultaRecaudo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AnalisisDevolucionDTO> result;
  
 	public ConsultarRecaudo (Boolean vista360,TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,Boolean hayParametros,ConsultarRecaudoDTO consultaRecaudo){
 		super();
		this.vista360=vista360;
		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
		this.hayParametros=hayParametros;
		this.consultaRecaudo=consultaRecaudo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("vista360", vista360)
			.queryParam("tipoMovimientoRecaudoAporte", tipoMovimientoRecaudoAporte)
			.queryParam("hayParametros", hayParametros)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaRecaudo == null ? null : Entity.json(consultaRecaudo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AnalisisDevolucionDTO>) response.readEntity(new GenericType<List<AnalisisDevolucionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AnalisisDevolucionDTO> getResult() {
		return result;
	}

 
  	public void setVista360 (Boolean vista360){
 		this.vista360=vista360;
 	}
 	
 	public Boolean getVista360 (){
 		return vista360;
 	}
  	public void setTipoMovimientoRecaudoAporte (TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte){
 		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
 	}
 	
 	public TipoMovimientoRecaudoAporteEnum getTipoMovimientoRecaudoAporte (){
 		return tipoMovimientoRecaudoAporte;
 	}
  	public void setHayParametros (Boolean hayParametros){
 		this.hayParametros=hayParametros;
 	}
 	
 	public Boolean getHayParametros (){
 		return hayParametros;
 	}
  
  	public void setConsultaRecaudo (ConsultarRecaudoDTO consultaRecaudo){
 		this.consultaRecaudo=consultaRecaudo;
 	}
 	
 	public ConsultarRecaudoDTO getConsultaRecaudo (){
 		return consultaRecaudo;
 	}
  
}