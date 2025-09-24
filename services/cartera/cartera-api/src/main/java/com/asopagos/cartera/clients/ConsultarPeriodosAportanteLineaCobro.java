package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarPeriodosAportanteLineaCobro
 */
public class ConsultarPeriodosAportanteLineaCobro extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private TipoLineaCobroEnum tipoLineaCobro;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CarteraModeloDTO> result;
  
 	public ConsultarPeriodosAportanteLineaCobro (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,TipoLineaCobroEnum tipoLineaCobro,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.tipoLineaCobro=tipoLineaCobro;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("tipoLineaCobro", tipoLineaCobro)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CarteraModeloDTO>) response.readEntity(new GenericType<List<CarteraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CarteraModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setTipoLineaCobro (TipoLineaCobroEnum tipoLineaCobro){
 		this.tipoLineaCobro=tipoLineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getTipoLineaCobro (){
 		return tipoLineaCobro;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}