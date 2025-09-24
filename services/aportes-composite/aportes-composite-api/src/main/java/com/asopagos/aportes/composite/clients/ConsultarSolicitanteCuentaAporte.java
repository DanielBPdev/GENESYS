package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.aportes.dto.SolicitanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aporteManual/obtenerSolicitantesCuentaAporte
 */
public class ConsultarSolicitanteCuentaAporte extends ServiceClient {
 
  
  	private Long numeroOperacion;
  	private String primerApellido;
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private String primerNombre;
  	private String segundoApellido;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String segundoNombre;
  	private String razonSocial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitanteDTO> result;
  
 	public ConsultarSolicitanteCuentaAporte (Long numeroOperacion,String primerApellido,TipoSolicitanteMovimientoAporteEnum tipoAportante,String primerNombre,String segundoApellido,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String segundoNombre,String razonSocial){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.primerApellido=primerApellido;
		this.tipoAportante=tipoAportante;
		this.primerNombre=primerNombre;
		this.segundoApellido=segundoApellido;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.segundoNombre=segundoNombre;
		this.razonSocial=razonSocial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("primerApellido", primerApellido)
						.queryParam("tipoAportante", tipoAportante)
						.queryParam("primerNombre", primerNombre)
						.queryParam("segundoApellido", segundoApellido)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("segundoNombre", segundoNombre)
						.queryParam("razonSocial", razonSocial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitanteDTO>) response.readEntity(new GenericType<List<SolicitanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitanteDTO> getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setTipoAportante (TipoSolicitanteMovimientoAporteEnum tipoAportante){
 		this.tipoAportante=tipoAportante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoAportante (){
 		return tipoAportante;
 	}
  	public void setPrimerNombre (String primerNombre){
 		this.primerNombre=primerNombre;
 	}
 	
 	public String getPrimerNombre (){
 		return primerNombre;
 	}
  	public void setSegundoApellido (String segundoApellido){
 		this.segundoApellido=segundoApellido;
 	}
 	
 	public String getSegundoApellido (){
 		return segundoApellido;
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
  	public void setSegundoNombre (String segundoNombre){
 		this.segundoNombre=segundoNombre;
 	}
 	
 	public String getSegundoNombre (){
 		return segundoNombre;
 	}
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  
}