package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarCarteraCotizantesAportanteLC
 */
public class ConsultarCarteraCotizantesAportanteLC extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private TipoLineaCobroEnum lineaCobro;
  	private Long periodo;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long idCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CarteraDependienteModeloDTO> result;
  
 	public ConsultarCarteraCotizantesAportanteLC (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,TipoLineaCobroEnum lineaCobro,Long periodo,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long idCartera){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.lineaCobro=lineaCobro;
		this.periodo=periodo;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.idCartera=idCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("lineaCobro", lineaCobro)
						.queryParam("periodo", periodo)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("idCartera", idCartera)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CarteraDependienteModeloDTO>) response.readEntity(new GenericType<List<CarteraDependienteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CarteraDependienteModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
 	}
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
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
  	public void setIdCartera (Long idCartera){
 		this.idCartera=idCartera;
 	}
 	
 	public Long getIdCartera (){
 		return idCartera;
 	}
  
}