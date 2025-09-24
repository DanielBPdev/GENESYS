package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarCuentaAportes
 */
public class ConsultarCuentaAporteConTipoRecaudo extends ServiceClient { 
   	private Long idPersonaCotizante;
   	private List<AnalisisDevolucionDTO> analisisDevolucionDTO;
	private TipoMovimientoRecaudoAporteEnum tipoRecaudo;
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarCuentaAporteConTipoRecaudo (Long idPersonaCotizante,List<AnalisisDevolucionDTO> analisisDevolucionDTO,TipoMovimientoRecaudoAporteEnum tipoRecaudo){
 		super();
		this.idPersonaCotizante=idPersonaCotizante;
		this.analisisDevolucionDTO=analisisDevolucionDTO;
		this.tipoRecaudo=tipoRecaudo;
 	} 
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPersonaCotizante", idPersonaCotizante)
			.queryParam("tipoRecaudo", tipoRecaudo)
			.request(MediaType.APPLICATION_JSON)
			.post(analisisDevolucionDTO == null ? null : Entity.json(analisisDevolucionDTO));
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

 
  	public void setIdPersonaCotizante (Long idPersonaCotizante){
 		this.idPersonaCotizante=idPersonaCotizante;
 	}
 	
 	public Long getIdPersonaCotizante (){
 		return idPersonaCotizante;
 	}
  
  	public void setAnalisisDevolucionDTO (List<AnalisisDevolucionDTO> analisisDevolucionDTO){
 		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 	
 	public List<AnalisisDevolucionDTO> getAnalisisDevolucionDTO (){
 		return analisisDevolucionDTO;
 	}
	public void setTipoRecaudo (TipoMovimientoRecaudoAporteEnum tipoRecaudo){
 		this.tipoRecaudo=tipoRecaudo;
 	}
 	
	public TipoMovimientoRecaudoAporteEnum getTipoRecaudo() {
		return this.tipoRecaudo;
	}
  
}