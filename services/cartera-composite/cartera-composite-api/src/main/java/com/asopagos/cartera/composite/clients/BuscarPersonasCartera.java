package com.asopagos.cartera.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/buscarPersonasCartera
 */
public class BuscarPersonasCartera extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private Long fechaNacimiento;
  	private String primerApellido;
  	private Long idEmpleador;
  	private Boolean esVista360Web;
  	private String primerNombre;
  	private String segundoApellido;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String segundoNombre;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaDTO> result;
  
 	public BuscarPersonasCartera (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,Long fechaNacimiento,String primerApellido,Long idEmpleador,Boolean esVista360Web,String primerNombre,String segundoApellido,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String segundoNombre){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.fechaNacimiento=fechaNacimiento;
		this.primerApellido=primerApellido;
		this.idEmpleador=idEmpleador;
		this.esVista360Web=esVista360Web;
		this.primerNombre=primerNombre;
		this.segundoApellido=segundoApellido;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.segundoNombre=segundoNombre;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("fechaNacimiento", fechaNacimiento)
						.queryParam("primerApellido", primerApellido)
						.queryParam("idEmpleador", idEmpleador)
						.queryParam("esVista360Web", esVista360Web)
						.queryParam("primerNombre", primerNombre)
						.queryParam("segundoApellido", segundoApellido)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("segundoNombre", segundoNombre)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PersonaDTO>) response.readEntity(new GenericType<List<PersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PersonaDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setFechaNacimiento (Long fechaNacimiento){
 		this.fechaNacimiento=fechaNacimiento;
 	}
 	
 	public Long getFechaNacimiento (){
 		return fechaNacimiento;
 	}
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setEsVista360Web (Boolean esVista360Web){
 		this.esVista360Web=esVista360Web;
 	}
 	
 	public Boolean getEsVista360Web (){
 		return esVista360Web;
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
  
}