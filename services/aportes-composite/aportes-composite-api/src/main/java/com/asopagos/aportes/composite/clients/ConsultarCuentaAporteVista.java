package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/consultarCuentaAporteVista
 */
public class ConsultarCuentaAporteVista extends ServiceClient { 
   	private Long idPersonaCotizante;
  	private TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte;
  	private Long idSolicitudDevolucion;
   	private List<AnalisisDevolucionDTO> analisisDevolucionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarCuentaAporteVista (Long idPersonaCotizante,TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,Long idSolicitudDevolucion,List<AnalisisDevolucionDTO> analisisDevolucionDTO){
 		super();
		this.idPersonaCotizante=idPersonaCotizante;
		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
		this.idSolicitudDevolucion=idSolicitudDevolucion;
		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPersonaCotizante", idPersonaCotizante)
			.queryParam("tipoMovimientoRecaudoAporte", tipoMovimientoRecaudoAporte)
			.queryParam("idSolicitudDevolucion", idSolicitudDevolucion)
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
  	public void setTipoMovimientoRecaudoAporte (TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte){
 		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
 	}
 	
 	public TipoMovimientoRecaudoAporteEnum getTipoMovimientoRecaudoAporte (){
 		return tipoMovimientoRecaudoAporte;
 	}
  	public void setIdSolicitudDevolucion (Long idSolicitudDevolucion){
 		this.idSolicitudDevolucion=idSolicitudDevolucion;
 	}
 	
 	public Long getIdSolicitudDevolucion (){
 		return idSolicitudDevolucion;
 	}
  
  	public void setAnalisisDevolucionDTO (List<AnalisisDevolucionDTO> analisisDevolucionDTO){
 		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 	
 	public List<AnalisisDevolucionDTO> getAnalisisDevolucionDTO (){
 		return analisisDevolucionDTO;
 	}
  
}