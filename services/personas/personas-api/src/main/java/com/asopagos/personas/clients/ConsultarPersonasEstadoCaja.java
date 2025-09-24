package com.asopagos.personas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/consultarPersonasEstadoCaja
 */
public class ConsultarPersonasEstadoCaja extends ServiceClient {
 
  
  	private String primerApellido;
  	private String textoFiltro;
  	private EstadoAfiliadoEnum estadoCaja;
  	private String primerNombre;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaDTO> result;
  
 	public ConsultarPersonasEstadoCaja (String primerApellido,String textoFiltro,EstadoAfiliadoEnum estadoCaja,String primerNombre,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.primerApellido=primerApellido;
		this.textoFiltro=textoFiltro;
		this.estadoCaja=estadoCaja;
		this.primerNombre=primerNombre;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("primerApellido", primerApellido)
						.queryParam("textoFiltro", textoFiltro)
						.queryParam("estadoCaja", estadoCaja)
						.queryParam("primerNombre", primerNombre)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
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

 
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setTextoFiltro (String textoFiltro){
 		this.textoFiltro=textoFiltro;
 	}
 	
 	public String getTextoFiltro (){
 		return textoFiltro;
 	}
  	public void setEstadoCaja (EstadoAfiliadoEnum estadoCaja){
 		this.estadoCaja=estadoCaja;
 	}
 	
 	public EstadoAfiliadoEnum getEstadoCaja (){
 		return estadoCaja;
 	}
  	public void setPrimerNombre (String primerNombre){
 		this.primerNombre=primerNombre;
 	}
 	
 	public String getPrimerNombre (){
 		return primerNombre;
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