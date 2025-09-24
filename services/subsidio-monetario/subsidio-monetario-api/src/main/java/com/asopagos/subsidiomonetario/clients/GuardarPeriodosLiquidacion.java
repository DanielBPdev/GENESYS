package com.asopagos.subsidiomonetario.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/guardarPeriodosLiquidacion
 */
public class GuardarPeriodosLiquidacion extends ServiceClient { 
   	private Long idSolicitudLiquidacion;
  	private TipoLiquidacionEspecificaEnum tipoAjuste;
   	private List<ValorPeriodoDTO> periodos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public GuardarPeriodosLiquidacion (Long idSolicitudLiquidacion,TipoLiquidacionEspecificaEnum tipoAjuste,List<ValorPeriodoDTO> periodos){
 		super();
		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
		this.tipoAjuste=tipoAjuste;
		this.periodos=periodos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudLiquidacion", idSolicitudLiquidacion)
			.queryParam("tipoAjuste", tipoAjuste)
			.request(MediaType.APPLICATION_JSON)
			.post(periodos == null ? null : Entity.json(periodos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setIdSolicitudLiquidacion (Long idSolicitudLiquidacion){
 		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
 	}
 	
 	public Long getIdSolicitudLiquidacion (){
 		return idSolicitudLiquidacion;
 	}
  	public void setTipoAjuste (TipoLiquidacionEspecificaEnum tipoAjuste){
 		this.tipoAjuste=tipoAjuste;
 	}
 	
 	public TipoLiquidacionEspecificaEnum getTipoAjuste (){
 		return tipoAjuste;
 	}
  
  	public void setPeriodos (List<ValorPeriodoDTO> periodos){
 		this.periodos=periodos;
 	}
 	
 	public List<ValorPeriodoDTO> getPeriodos (){
 		return periodos;
 	}
  
}