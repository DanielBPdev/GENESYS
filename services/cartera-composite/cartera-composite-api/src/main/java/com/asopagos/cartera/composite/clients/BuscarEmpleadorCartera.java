package com.asopagos.cartera.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.EmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/buscarEmpleadorCartera
 */
public class BuscarEmpleadorCartera extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private Boolean aplicaPre;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String razonSocial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpleadorDTO> result;
  
 	public BuscarEmpleadorCartera (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,Boolean aplicaPre,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String razonSocial){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.aplicaPre=aplicaPre;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.razonSocial=razonSocial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("aplicaPre", aplicaPre)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("razonSocial", razonSocial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<EmpleadorDTO>) response.readEntity(new GenericType<List<EmpleadorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EmpleadorDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setAplicaPre (Boolean aplicaPre){
 		this.aplicaPre=aplicaPre;
 	}
 	
 	public Boolean getAplicaPre (){
 		return aplicaPre;
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
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  
}