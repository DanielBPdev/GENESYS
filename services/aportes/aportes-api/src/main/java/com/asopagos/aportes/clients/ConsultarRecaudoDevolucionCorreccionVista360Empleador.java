package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRecaudoDevolucionCorreccionVista360Empleador
 */
public class ConsultarRecaudoDevolucionCorreccionVista360Empleador extends ServiceClient { 
   	private String vistaEmpleador;
  	private Long idPersonaCotizante;
  	private TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte;
  	private Boolean hayParametros;
   	private ConsultarRecaudoDTO consultaRecaudo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarRecaudoDevolucionCorreccionVista360Empleador (String vistaEmpleador,Long idPersonaCotizante,TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,Boolean hayParametros,ConsultarRecaudoDTO consultaRecaudo){
 		super();
		this.vistaEmpleador=vistaEmpleador;
		this.idPersonaCotizante=idPersonaCotizante;
		this.tipoMovimientoRecaudoAporte=tipoMovimientoRecaudoAporte;
		this.hayParametros=hayParametros;
		this.consultaRecaudo=consultaRecaudo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("vistaEmpleador", vistaEmpleador)
			.queryParam("idPersonaCotizante", idPersonaCotizante)
			.queryParam("tipoMovimientoRecaudoAporte", tipoMovimientoRecaudoAporte)
			.queryParam("hayParametros", hayParametros)
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

 
  	public void setVistaEmpleador (String vistaEmpleador){
 		this.vistaEmpleador=vistaEmpleador;
 	}
 	
 	public String getVistaEmpleador (){
 		return vistaEmpleador;
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